// /////////////////////////////////////////////////////////////////////
// Copyright(C) 2015 NEC Solution Innovators, Ltd. All Rights Reserved.
// /////////////////////////////////////////////////////////////////////

/*
 * ====================================================================
 * 
 * 機能名称 : 日割予算リスト
 * 
 * 改版履歴
 * 
 * Rev. 改版年月日 改版者名 内容
 * 
 * 1.0 2014/05/20 Taivd 新規作成
 * 
 * ===================================================================
 */

package jp.co.necsoft.web_md.core.app.controllers.ur;

import java.io.File;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import jp.co.necsoft.web_md.common.ChaseException;
import jp.co.necsoft.web_md.common.mybatis.dao.MyBatisSqlMapDao;
import jp.co.necsoft.web_md.core.app.dto.ur.Urjp1140Dto01;
import jp.co.necsoft.web_md.core.app.dto.ur.Urjp1140Dto02;
import jp.co.necsoft.web_md.core.app.file.report.Urpr1140Report;
import jp.co.necsoft.web_md.core.app.forms.ur.Urjp1140CondForm;
import jp.co.necsoft.web_md.core.app.forms.ur.Urjp1140ResultForm;
import jp.co.necsoft.web_md.core.common.CCMessageConst;
import jp.co.necsoft.web_md.core.common.CCSecurityAuthenticator;
import jp.co.necsoft.web_md.core.common.CCSystemContext;
import jp.co.necsoft.web_md.core.common.biz.an.CCANCommon;
import jp.co.necsoft.web_md.core.common.biz.si.CCSICommon;
import jp.co.necsoft.web_md.core.common.forms.ErrorInfo;
import jp.co.necsoft.web_md.core.common.forms.ErrorResponse;
import jp.co.necsoft.web_md.core.common.mybatis.dao.M000kaimMapper;
import jp.co.necsoft.web_md.core.common.mybatis.dao.M001jgymMapper;
import jp.co.necsoft.web_md.core.common.mybatis.dao.M006tenmMapper;
import jp.co.necsoft.web_md.core.common.mybatis.dto.M000kaim;
import jp.co.necsoft.web_md.core.common.mybatis.dto.M000kaimExample;
import jp.co.necsoft.web_md.core.common.mybatis.dto.M001jgym;
import jp.co.necsoft.web_md.core.common.mybatis.dto.M001jgymExample;
import jp.co.necsoft.web_md.core.common.mybatis.dto.M006tenm;
import jp.co.necsoft.web_md.core.common.mybatis.dto.M006tenmExample;
import jp.co.necsoft.web_md.core.common.utils.CCDateUtil;
import jp.co.necsoft.web_md.core.common.utils.CCReportUtil;
import jp.co.necsoft.web_md.core.common.utils.CCStringUtil;

import org.springframework.beans.BeanUtils;

import play.data.Form;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;

import com.google.inject.Inject;
import com.orangesignal.csv.manager.CsvManager;
import com.orangesignal.csv.manager.CsvManagerFactory;

/**
*日割予算リストのControllerクラス
*
*/
@Security.Authenticated(CCSecurityAuthenticator.class)
public class Urjp1140Ctrl extends Controller {
    /** CCSystemContext */
    @Inject
    private CCSystemContext context;
    /** errRes */
    @Inject
    private ErrorResponse errRes;
    /** dao: DAO for pure sql mapping. */
    @Inject
    private MyBatisSqlMapDao dao;
    /** M006tenmMapper */
    @Inject
    private M006tenmMapper m006tenmMapper;
    /** M000kaimMapper */
    @Inject
    private M000kaimMapper m000kaimMapper;
    /** M001jgymMapper */
    @Inject
    private M001jgymMapper m001jgymMapper;
    /** CCSICommon. */
    @Inject
    private CCSICommon ccSICommon;

    /** CCANCommon */
    @Inject
    private CCANCommon ccANCommon;
    /** PROGRAM_ID */
    private static final String PROGRAM_ID = "URPR1140";
    /** PROGRAM_VERSION */
    private static final String PROGRAM_VERSION = "1.00";
    /** EMPTY */
    private static final String EMPTY = "";
    /** 店舗区分（本部） */
    private static final String CN_TEN = "9";
    /** 1ページに出力する部門数取得 */
    private static final int BMN_CNT = 15;

    /**
     * get condition when initial screen
     * 
     * @return クライアントへ返却する結果
     */
    public Result init() {
        Urjp1140CondForm condForm = new Urjp1140CondForm();
        // 運用日を取得
        if (!this.setUnyoDate()) {
            errRes.addErrorInfo(CCMessageConst.MSG_KEY_NO_UNYODATE_DATA_RESULT);
            return badRequest(Json.toJson(errRes));
        }

        // 担当者の店舗情報をBeanにセット
        if (!this.setTantoTenCode(condForm)) {
            errRes.addErrorInfo(CCMessageConst.MSG_KEY_FAIL_CONTACT_INFO_PROCESS);
            return badRequest(Json.toJson(errRes));
        }

        // 担当者の店舗コード・店舗区分を取得
        String sTenCode = context.getTenpoCode();
        String sTenKbn = condForm.getLoginKbn();

        if (CCStringUtil.isEmpty(sTenCode)) {
            errRes.addErrorInfo(CCMessageConst.MSG_KEY_FAIL_CONTACT_INFO_PROCESS);
            return badRequest(Json.toJson(errRes));
        }

        // 担当者が本部以外は店舗コードを初期値にセット
        if (!CN_TEN.equals(sTenKbn)) {
            condForm.setKaisyaCd(sTenCode.substring(0, 2));
            condForm.setJigyobuCd(sTenCode.substring(2, 4));
            condForm.setTenCd(sTenCode.substring(4, 7));
        } else {
            condForm.setKaisyaCd(EMPTY);
            condForm.setJigyobuCd(EMPTY);
            condForm.setTenCd(EMPTY);
        }
        return ok(Json.toJson(condForm));
    }

    /**
    * 引数とリクエスト(パラメータ)を条件にDBからデータ(複数件)を検索し返却する。*
    * @param kaisyaCd 会社
    * @param jigyobuCd 事業部
    * @param tenCd 店舗
    * @param taisyodateY 対象年
    * @param taisyodateM 対象月
    * @return クライアントへ返却する結果
    */
    @SuppressWarnings({"unchecked", "rawtypes" })
    public Result query(String kaisyaCd, String jigyobuCd, String tenCd, String taisyodateY, String taisyodateM) {
        Form<Urjp1140CondForm> emptyForm = new Form(Urjp1140CondForm.class);
        Form<Urjp1140CondForm> reqForm = emptyForm.bindFromRequest();
        Urjp1140CondForm condForm = new Urjp1140CondForm();
        Urjp1140ResultForm resultForm = new Urjp1140ResultForm();

        if (reqForm.hasErrors()) {
            errRes.addErrorInfo(CCMessageConst.MSG_KEY_VALIDATION_ERROR);
            errRes.addErrorInfo(reqForm.errors());
            return badRequest(Json.toJson(errRes));
        }

        condForm = reqForm.get();
        if (condForm == null) {
            errRes.addErrorInfo(CCMessageConst.MSG_KEY_VALIDATION_ERROR);
            return badRequest(Json.toJson(errRes));
        }

        String sKaisyaCode = kaisyaCd;
        String sJigyobuCode = jigyobuCd;
        String sTenpoCode = tenCd;
        String sYear = taisyodateY;
        String sMonth = taisyodateM;
        String unyobi = context.getUnyoDate();
        int iMonth = 0;

        condForm.setKaisyaCd(sKaisyaCode);
        condForm.setJigyobuCd(sJigyobuCode);
        condForm.setTenCd(sTenpoCode);
        condForm.setTaisyodateY(sYear);
        condForm.setTaisyodateM(sMonth);

        // 会社存在チェック
        String sKaisyaName = ccSICommon.getComKaisyaName(kaisyaCd, unyobi);
        if (CCStringUtil.isEmpty(sKaisyaName)) {
            errRes.addErrorInfo("kaisyaCd", CCMessageConst.MSG_KEY_NOT_EXIST_REGISTERED);
            ErrorInfo error = errRes.getErrorInfo(CCMessageConst.MSG_KEY_VALIDATION_ERROR);
            errRes.getErrors().add(0, error);
            return badRequest(Json.toJson(errRes));
        }

        // 事業部存在チェック
        String sJigyoName = ccSICommon.getComJigyobuName(kaisyaCd, jigyobuCd, unyobi);
        if (CCStringUtil.isEmpty(sJigyoName)) {
            errRes.addErrorInfo("jigyobuCd", CCMessageConst.MSG_KEY_NOT_EXIST_REGISTERED);
            ErrorInfo error = errRes.getErrorInfo(CCMessageConst.MSG_KEY_VALIDATION_ERROR);
            errRes.getErrors().add(0, error);
            return badRequest(Json.toJson(errRes));
        }

        // 店舗存在チェック
        String sTenpoName = this.getTenName(unyobi, kaisyaCd + jigyobuCd + tenCd);
        if (CCStringUtil.isEmpty(sTenpoName)) {
            errRes.addErrorInfo("tenCd", CCMessageConst.MSG_KEY_NOT_EXIST_REGISTERED);
            ErrorInfo error = errRes.getErrorInfo(CCMessageConst.MSG_KEY_VALIDATION_ERROR);
            errRes.getErrors().add(0, error);
            return badRequest(Json.toJson(errRes));
        }

        // 入力値
        String sInpValue = EMPTY;
        // チェック内容パラメータ
        String sChkMask = EMPTY;
        // チェックするコントロール名
        String sCtlName = EMPTY;

        // 文字数
        int lChkLen = 0;
        // チェック結果
        int iChkRet = 0;

        // 出力年の妥当性チェック
        // 入力値セット
        sInpValue = sYear;
        // コントロール名セット
        sCtlName = "taisyodateY";
        // 桁数、正数、整数値チェックセット
        sChkMask = "111";
        // 文字数セット
        lChkLen = 4;

        // チェック処理実行
        iChkRet = ccANCommon.validateNumValue(sInpValue, sChkMask, lChkLen);

        // 戻り値判断
        switch (iChkRet) {
        case 0: // チェックＯＫ
            break;
        case -1: // 設定値パラメータ異常
        case -2: // 処理異常
        default: // 想定外値
            errRes.addErrorInfo(CCMessageConst.MSG_KEY_PROCESS_TAISYODATE_Y_FAIL);
            return badRequest(Json.toJson(errRes));
        case 1: // 数値チェックエラー
        case 2: // 桁数チェックエラー
        case 3: // 正数チェックエラー
        case 4: // 整数値チェックエラー
            // 「入力された項目に誤りがあります。」
            errRes.addErrorInfo(sCtlName, CCMessageConst.MSG_KEY_ITEM_INPUT_IS_ERROR);
            ErrorInfo error = errRes.getErrorInfo(CCMessageConst.MSG_KEY_VALIDATION_ERROR);
            errRes.getErrors().add(0, error);
            return badRequest(Json.toJson(errRes));
        }

        // 出力月の妥当性チェック
        // 入力値セット
        sInpValue = sMonth;
        // コントロール名セット
        sCtlName = "taisyodateY";
        // 桁数、正数、整数値チェックセット
        sChkMask = "111";
        // 文字数セット
        lChkLen = 2;

        // チェック処理実行
        iChkRet = ccANCommon.validateNumValue(sInpValue, sChkMask, lChkLen);

        // 戻り値判断
        switch (iChkRet) {
        case 0: // チェックＯＫ
            break;
        case -1: // 設定値パラメータ異常
        case -2: // 処理異常
        default: // 想定外値
            errRes.addErrorInfo(CCMessageConst.MSG_KEY_PROCESS_TAISYODATE_M_FAIL);
            return badRequest(Json.toJson(errRes));
        case 1: // 数値チェックエラー
        case 2: // 桁数チェックエラー
        case 3: // 正数チェックエラー
        case 4: // 整数値チェックエラー
            errRes.addErrorInfo(sCtlName, CCMessageConst.MSG_KEY_ITEM_INPUT_IS_ERROR);
            ErrorInfo error = errRes.getErrorInfo(CCMessageConst.MSG_KEY_VALIDATION_ERROR);
            errRes.getErrors().add(0, error);
            return badRequest(Json.toJson(errRes));
        }

        // 上限下・下限値のチェック
        iMonth = Integer.parseInt(sMonth);
        if (iMonth < 1 || iMonth > 12) {
            // 「入力された項目に誤りがあります。」
            errRes.addErrorInfo(sCtlName, CCMessageConst.MSG_KEY_ITEM_INPUT_IS_ERROR);
            ErrorInfo error = errRes.getErrorInfo(CCMessageConst.MSG_KEY_VALIDATION_ERROR);
            errRes.getErrors().add(0, error);
            return badRequest(Json.toJson(errRes));
        }

        // ------------------------------
        // 出力データ取得
        // ------------------------------
        if (!this.execute(condForm)) {
            // メッセージ出力
            errRes.addErrorInfo(CCMessageConst.MSG_KEY_DATA_PROCESSING_ERROR);
            return badRequest(Json.toJson(errRes));
        }
        if (!this.execute2(condForm)) {
            // メッセージ出力
            errRes.addErrorInfo(CCMessageConst.MSG_KEY_DATA_PROCESSING_ERROR);
            return badRequest(Json.toJson(errRes));
        }

        List<Urjp1140Dto01> lstYosanData = condForm.getmLstYosanList();
        List<Urjp1140Dto02> lstYosanSumData = condForm.getmLstYosanSumList();

        if (null == lstYosanData || lstYosanData.size() == 0) {
            // 対象データ０件
            errRes.addErrorInfo(CCMessageConst.MSG_KEY_NO_FOUND_DATA);
            return badRequest(Json.toJson(errRes));
        }

        // ------------------------------
        // CSVファイル作成
        // ------------------------------

        CCReportUtil cru = new CCReportUtil("URSV1140", context);
        // ファイル名設定
        CsvManager csvManager = CsvManagerFactory.newCsvManager();
        List<Urpr1140Report> csvBean = new ArrayList<Urpr1140Report>();
        Urpr1140Report row = null;
        Urpr1140Report rowCopy = null;

        // ページ数取得
        int iPageCnt = condForm.getmPageCnt();

        String sDate = EMPTY;
        String sDateWork = EMPTY;
        String sOutDate = EMPTY;

        // 部門１の配列位置
        int intIndex = 12;

        // 予算合計金額、客数取得用
        int iSum = 0;
        // ページ数
        int iPage = 0;
        // ページ数ワーク
        int iPageWork = 0;
        // 明細数
        int iMeisaiCnt = 0;

        // execute()により取得した、予算データ(lstYosanData)をセットします。
        // ＣＳＶ１レコードに15部門分(M00_BMN_CD1～M00_BMN_CD15)のデータをセット
        // 15部門ない場合は空文字をセット
        // 1ページに出力する部門は15、日(明細数)は31行を必ずセット
        // M01_BMN_CD_1がBreak時に改ページする設定(svf)
        // 最終ページのデータセット時に、execute2()により取得した合計予算金額、客数(lstYosanSumData)をセット
        String tantoCd = context.getTantoCode();
        String kaisyaName = this.getKaisyaName(getGetumatuDate(condForm), kaisyaCd);
        String jigyobuName = this.getJigyobuName(getGetumatuDate(condForm), kaisyaCd, jigyobuCd);
        String tenpoName = this.getTenName(getGetumatuDate(condForm), kaisyaCd + jigyobuCd + tenCd);
        Urjp1140Dto01 dto01 = null;
        Urjp1140Dto02 dto02 = null;

        row = new Urpr1140Report();

        // 共通ヘッダ部（プログラムＩＤ、バージョン、担当者名）
        row.h00_progid = '"' + PROGRAM_ID + '"';
        row.h00_version = '"' + PROGRAM_VERSION + '"';
        row.h01_tanto_code = '"' + tantoCd + '"';
        // 会社
        row.h01_kaisya_code = '"' + sKaisyaCode + '"';
        row.h01_kaisya_name = '"' + kaisyaName + '"';
        // 事業部
        row.h02_jigyobu_code = '"' + sJigyobuCode + '"';
        row.h02_jigyobu_name = '"' + jigyobuName + '"';
        // 出力年
        row.h02_nen = '"' + sYear + '"';
        // 出力月
        row.h02_tuki = '"' + sMonth + '"';
        // 店舗
        row.h03_str_code = '"' + sTenpoCode + '"';
        row.h03_str_name = '"' + tenpoName + '"';

        for (int i = 0; i < lstYosanData.size(); i++) {
            dto01 = lstYosanData.get(i);
            sDate = dto01.getCalDate();

            if (!sDate.equals(sDateWork)) {
                if (!CCStringUtil.isEmpty(sDateWork)) {
                    // １ページに出力を行う部門数(15)になるまでセット
                    if (intIndex != 42) {
                        int j = (intIndex - 12) / 2;
                        for (; j < 15; j++) {
                            setValueByMethodName(row, "m00_bmn_cd_" + (j + 1), EMPTY);
                            setValueByMethodName(row, "m01_ysn_" + (j + 1), EMPTY);
                        }
                    }
                    // 最終ページの場合、合計・客数に値をセット
                    if (iPage == iPageCnt) {
                        dto02 = lstYosanSumData.get(iSum);

                        // 予算合計
                        row.m01_ysn_sum = Long.toString(Long.parseLong(dto02.getYsnUriKin()) / 1000);
                        // 客数
                        row.m01_kyaku = dto02.getYsnKyaKsu();
                        // 最終ページフラグ
                        row.m01_last = "1";

                        iSum = iSum + 1;

                    }

                    // 明細行の追加
                    rowCopy = new Urpr1140Report();
                    BeanUtils.copyProperties(row, rowCopy);
                    csvBean.add(rowCopy);

                    // 明細数カウント
                    iMeisaiCnt = iMeisaiCnt + 1;

                    // ページNo.取得
                    iPage = Integer.parseInt(dto01.getIrow());

                    // 改ページする場合、明細数をMAX31になるまでセット
                    if (iPage != iPageWork) {
                        if (iPageWork != 0) {
                            if (iMeisaiCnt != 31) {
                                int index = 0;
                                // 部門コードはクリアしないように、金額のみクリア
                                row.m01_date = EMPTY;
                                row.m01_week = EMPTY;
                                for (int j = 0; j < 15; j++) {
                                    setValueByMethodName(row, "m01_ysn_" + (j + 1), EMPTY);
                                }

                                row.m01_ysn_sum = EMPTY;
                                row.m01_kyaku = EMPTY;
                                // 最終ページ項目にゼロ(False)をセット
                                row.m01_last = "0";

                                for (index = iMeisaiCnt + 1; index <= 31; index++) {
                                    rowCopy = new Urpr1140Report();
                                    BeanUtils.copyProperties(row, rowCopy);
                                    csvBean.add(rowCopy);
                                }
                            }
                            // 明細数初期化
                            iMeisaiCnt = 0;
                        }
                        iPageWork = iPage;
                    }
                    //
                    intIndex = 12;
                }
                sDateWork = sDate;
            }
            // ページNo.取得
            iPage = Integer.parseInt(dto01.getIrow());
            // 日付
            sOutDate = dto01.getCalDate().substring(4, 8);
            sOutDate =
                    String.valueOf(Integer.parseInt(sOutDate.substring(0, 2))) + "月"
                            + String.valueOf(Integer.parseInt(sOutDate.substring(2, 4))) + "日";
            row.m01_date = sOutDate;
            // 曜日
            row.m01_week = dto01.getWeekDay();

            // 部門コード1～15
            intIndex = intIndex + 2;
            int index = (intIndex - 12) / 2;
            if (index > 15) {
                errRes.addErrorInfo(CCMessageConst.MSG_KEY_ERROR_F12_PROCESS);
                return badRequest(Json.toJson(errRes));
            }

            String bmnCd = String.valueOf(Integer.parseInt(dto01.getBmnCd().substring(2, 5))) + "部門";
            String ysn = Long.toString(Long.parseLong(dto01.getYsnUriKin()) / 1000);
            setValueByMethodName(row, "m00_bmn_cd_" + index, bmnCd);
            setValueByMethodName(row, "m01_ysn_" + index, ysn);

            // 予算合計
            row.m01_ysn_sum = EMPTY;
            // 客数
            row.m01_kyaku = EMPTY;
            // 最終ページフラグ
            row.m01_last = "0";
        }

        // １ページに出力を行う部門数(15)になるまでセット
        if (intIndex != 42) {
            int j = (intIndex - 12) / 2;
            for (; j < 15; j++) {
                setValueByMethodName(row, "m00_bmn_cd_" + (j + 1), EMPTY);
                setValueByMethodName(row, "m01_ysn_" + (j + 1), EMPTY);
            }
        }
        // 合計・客数セット
        dto02 = lstYosanSumData.get(iSum);
        // 予算合計
        row.m01_ysn_sum = Long.toString(Long.parseLong(dto02.getYsnUriKin()) / 1000);
        // 客数
        row.m01_kyaku = dto02.getYsnKyaKsu();
        // 最終ページフラグ
        row.m01_last = "1";

        // 明細行の追加
        rowCopy = new Urpr1140Report();
        BeanUtils.copyProperties(row, rowCopy);
        csvBean.add(rowCopy);

        // 明細数カウント
        iMeisaiCnt = iMeisaiCnt + 1;

        // 明細数がMAX31になるまで追加
        if (iMeisaiCnt != 31) {

            int index = 0;
            row.m01_date = EMPTY;
            row.m01_week = EMPTY;

            for (int j = 0; j < 15; j++) {
                setValueByMethodName(row, "m01_ysn_" + (j + 1), EMPTY);
            }
            row.m01_ysn_sum = EMPTY;
            row.m01_kyaku = EMPTY;
            row.m01_last = "1";

            for (index = iMeisaiCnt + 1; index <= 31; index++) {
                rowCopy = new Urpr1140Report();
                BeanUtils.copyProperties(row, rowCopy);
                csvBean.add(rowCopy);
            }
        }

        // ------------------------------
        // PDFファイル生成
        // ------------------------------
        if (csvBean.isEmpty()) {
            // 対象データ０件
            errRes.addErrorInfo(CCMessageConst.MSG_KEY_NO_FOUND_DATA);
            return notFound();
        }

        // ＣＳＶ作成（データ出力）
        try {
            csvManager.save(csvBean, Urpr1140Report.class).to(new File(cru.getCsvFilePath()),
                    CCReportUtil.CSV_OUTPUT_ENCODING);
        } catch (Exception e) {
            throw new ChaseException(e);
        }

        cru.makePDF();
        try {
            URL uPdfUrl = new URL(cru.getPdfUrl());
            resultForm.setPdfUrl(uPdfUrl);
        } catch (Exception e) {
            throw new ChaseException(e);
        }
        // gc対応
        cru = null;
        System.gc();

        return ok(Json.toJson(resultForm));
    }

    /**
     * コントロールマスタより運用日を取得する.
     * @param なし
     * @return boolean true:正常  false:異常
     */
    private boolean setUnyoDate() {
        String unyobi = context.getUnyoDate();
        if (CCStringUtil.isEmpty(unyobi)) {
            return false;
        }
        return true;
    }

    /**
     * ログイン店舗コード、店舗区分を取得します.
     * 
     * @param condForm Urjp1140CondForm
     * @return なし
     */
    private boolean setTantoTenCode(Urjp1140CondForm condForm) {
        String loginKbn = EMPTY;
        // 店舗コード取得
        String loginTen = context.getTenpoCode();
        String unyoDate = context.getUnyoDate();

        HashMap<String, String> hashMap = new HashMap<String, String>();
        hashMap.put("loginTen", loginTen);
        hashMap.put("unyoDate", unyoDate);
        // 店部門マスタより部門取得
        List<String> tenKbns = dao.selectMany("selectM006tenmUrjp1140", hashMap, String.class);

        if (null != tenKbns && tenKbns.size() > 0) {
            loginKbn = tenKbns.get(0);
            condForm.setLoginKbn(CCStringUtil.trimRight(loginKbn));
        } else {
            return false;
        }
        return true;
    }

    /**
    * 会社名称取得処理.
    * 
    * @param date 検索日
    * @param kaisyaCd 会社
    * @return String 会社名
    */
    private String getKaisyaName(String date, String kaisyaCd) {
        M000kaimExample example = new M000kaimExample();
        example.createCriteria().andKaisyaCdEqualTo(kaisyaCd);
        example.setSearchDate(date);

        List<M000kaim> m000kaims = m000kaimMapper.selectByExample(example);

        // 取得データ数ゼロの場合、未登録
        if (null == m000kaims || m000kaims.size() == 0
                || CCSICommon.TYPE_ACT_KBN_DELETED.equals(m000kaims.get(0).getActKbn())) {
            return EMPTY;
        }

        // 会社名を取得
        return CCStringUtil.trimRight(m000kaims.get(0).getKaiNm());
    }

    /**
     * 店舗名称取得処理.
     * 
     * @param date 検索日
     * @param strval 店舗コード
     * @return String 店舗名
     */
    private String getTenName(String date, String strval) {
        M006tenmExample example = new M006tenmExample();
        example.createCriteria().andTenCdEqualTo(strval);
        example.setSearchDate(date);

        List<M006tenm> m006tenms = m006tenmMapper.selectByExample(example);

        // 取得データ数ゼロの場合、未登録
        if (null == m006tenms || m006tenms.size() == 0
                || CCSICommon.TYPE_ACT_KBN_DELETED.equals(m006tenms.get(0).getActKbn())) {
            return EMPTY;
        }
        return m006tenms.get(0).getTenNm();
    }

    /**
    * 日別店別部門別予算より、日別店別部門別予算データを取得します.
    * 
    * @param condForm Urjp1140CondForm
    * @return boolean true(正常) / false(異常)
    */
    private boolean execute(Urjp1140CondForm condForm) {
        List<Urjp1140Dto01> lstYosanList = new ArrayList<Urjp1140Dto01>();
        List<String[]> mLstBmnList = new ArrayList<String[]>();

        int iCount = 0;
        String sTenCode = condForm.getKaisyaCd() + condForm.getJigyobuCd() + condForm.getTenCd();
        String sDateFrom = condForm.getTaisyodateY() + condForm.getTaisyodateM() + "01";
        String sDateTo = getGetumatuDate(condForm);

        HashMap<String, String> hashMap = new HashMap<String, String>();
        hashMap.put("tenCd", sTenCode);
        hashMap.put("dateTo", sDateTo);
        // 店部門マスタより部門取得
        List<String> bmnCds = dao.selectMany("selectM004tbumUrjp1140", hashMap, String.class);
        // データ格納
        int iAmari = 0;
        int iPageCnt = 0;

        if (bmnCds.size() != 0) {
            // 部門数取得
            iCount = bmnCds.size();
            // ぺージ数取得(取得した部門数/1ページに出力する部門数)
            if (iCount > BMN_CNT) {
                iAmari = iCount % BMN_CNT;
                iPageCnt = (iCount - iAmari) / BMN_CNT;
                if (iAmari > 0) {
                    iPageCnt = iPageCnt + 1;
                }
            } else {
                iPageCnt = 1;
            }

            condForm.setmPageCnt(iPageCnt);
            int iRec = 0;

            // 各ページに出力する最初(M00_BMN_CD_1)と最後(M00_BMN_CD_15)の部門コードをセット
            for (int i = 0; i < iPageCnt; i++) {

                String[] bmnDataArray = new String[2];

                // 各ページの最初の部門コード取得
                iRec = i * 15;
                bmnDataArray[0] = bmnCds.get(iRec);

                // 各ページの最後の部門コード取得
                iRec = ((i + 1) * 15) - 1;
                if (iRec > iCount - 1) {
                    iRec = iCount - 1;
                }
                bmnDataArray[1] = bmnCds.get(iRec);

                mLstBmnList.add(bmnDataArray);
            }
        } else {
            return true;
        }

        // 部門の範囲指定を行い、1ページに出力するデータを順に取得
        int iIndex = mLstBmnList.size();

        for (int i = 0; i < iIndex; i++) {
            String[] bmnDataArray = (String[]) mLstBmnList.get(i);
            int iPage = i + 1;

            hashMap = new HashMap<String, String>();
            hashMap.put("iPage", String.valueOf(iPage));
            hashMap.put("tenCd", sTenCode);
            hashMap.put("bmnCd01", bmnDataArray[0]);
            hashMap.put("bmnCd02", bmnDataArray[1]);
            hashMap.put("dateFrom", sDateFrom);
            hashMap.put("dateTo", sDateTo);
            List<Urjp1140Dto01> yosanList = dao.selectMany("selectU001ybmnUrjp1140", hashMap, Urjp1140Dto01.class);

            // データ格納
            if (null != yosanList && yosanList.size() > 0) {
                lstYosanList.addAll(yosanList);
            }
        }
        condForm.setmLstYosanList(lstYosanList);
        return true;
    }

    /**
    * 日別店別部門別予算より、店計予算金額、予算客数を取得します.
    * 
    * @param condForm Urjp1140CondForm
    * @return boolean true(正常) / false(異常)
    */
    private boolean execute2(Urjp1140CondForm condForm) {
        String sTenCode = condForm.getKaisyaCd() + condForm.getJigyobuCd() + condForm.getTenCd();
        String sDateFrom = condForm.getTaisyodateY() + condForm.getTaisyodateM() + "01";
        String sDateTo = getGetumatuDate(condForm);

        HashMap<String, String> hashMap = new HashMap<String, String>();
        hashMap.put("tenCd", sTenCode);
        hashMap.put("dateFrom", sDateFrom);
        hashMap.put("dateTo", sDateTo);
        List<Urjp1140Dto02> yosanSumList = dao.selectMany("selectM019ymdmUrjp1140", hashMap, Urjp1140Dto02.class);
        // 予算データ取得
        condForm.setmLstYosanSumList(yosanSumList);
        return true;
    }

    /**
    * 指定年月の月末日を取得する.
    * 
    * @param condForm Urjp1140CondForm
    * @return String 指定年月の月末日
    */
    private String getGetumatuDate(Urjp1140CondForm condForm) {
        int iGetumtuDate =
                CCDateUtil.getLastDay(Integer.parseInt(condForm.getTaisyodateY()),
                        Integer.parseInt(condForm.getTaisyodateM()));
        if (iGetumtuDate == -1) {
            return EMPTY;
        }
        return condForm.getTaisyodateY() + condForm.getTaisyodateM() + String.valueOf(iGetumtuDate);
    }

    /**
    * 事業部名称取得処理.
    * 
    * @param date 検索日
    * @param kaisyaCd 会社
    * @param jigyobuCd 事業部
    * @return String 事業部名
    */
    private String getJigyobuName(String date, String kaisyaCd, String jigyobuCd) {
        M001jgymExample example = new M001jgymExample();
        example.createCriteria().andKaisyaCdEqualTo(kaisyaCd).andJigyobuCdEqualTo(jigyobuCd);
        example.setSearchDate(date);

        List<M001jgym> m001jgyms = m001jgymMapper.selectByExample(example);

        // 取得データ数ゼロの場合、未登録
        if (null == m001jgyms || m001jgyms.size() == 0
                || CCSICommon.TYPE_ACT_KBN_DELETED.equals(m001jgyms.get(0).getActKbn())) {
            return EMPTY;
        }
        return m001jgyms.get(0).getJgyNm();
    }

    /**
     * 関数名により値を取得処理.
     * @param obj Object
     * @param propertyName get関数名
     * @param inputValue 入力値
     */
    public static void setValueByMethodName(Object obj, String propertyName, Object inputValue) {
        try {
            Field field = obj.getClass().getField(propertyName);
            field.set(obj, inputValue);
        } catch (Exception e) {
            throw new ChaseException(e);
        }
    }
}
