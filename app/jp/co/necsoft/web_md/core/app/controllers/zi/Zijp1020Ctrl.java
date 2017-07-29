// /////////////////////////////////////////////////////////////////////
// Copyright(C) 2015 NEC Solution Innovators, Ltd. All Rights Reserved.
// /////////////////////////////////////////////////////////////////////

/*
 * ====================================================================
 * 
 * 機能名称 : 棚卸取扱い部門リスト
 * 
 * 改版履歴
 * 
 * Rev. 改版年月日 改版者名 内容
 * 
 * 1.0 2014/05/07 Taivd 新規作成
 * 
 * ===================================================================
 */
package jp.co.necsoft.web_md.core.app.controllers.zi;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

import jp.co.necsoft.web_md.common.ChaseException;
import jp.co.necsoft.web_md.common.mybatis.dao.MyBatisSqlMapDao;
import jp.co.necsoft.web_md.core.app.dto.zi.Zijp1020MsaiDto;
import jp.co.necsoft.web_md.core.app.file.report.Zipr1020Report1;
import jp.co.necsoft.web_md.core.app.file.report.Zipr1020Report2;
import jp.co.necsoft.web_md.core.app.forms.zi.Zijp1020CondForm;
import jp.co.necsoft.web_md.core.app.forms.zi.Zijp1020ResultForm;
import jp.co.necsoft.web_md.core.common.CCKubunConst;
import jp.co.necsoft.web_md.core.common.CCMessageConst;
import jp.co.necsoft.web_md.core.common.CCSecurityAuthenticator;
import jp.co.necsoft.web_md.core.common.CCSystemContext;
import jp.co.necsoft.web_md.core.common.biz.si.CCSICommon;
import jp.co.necsoft.web_md.core.common.forms.ErrorInfo;
import jp.co.necsoft.web_md.core.common.forms.ErrorResponse;
import jp.co.necsoft.web_md.core.common.mybatis.dao.M000kaimMapper;
import jp.co.necsoft.web_md.core.common.mybatis.dao.M001jgymMapper;
import jp.co.necsoft.web_md.core.common.mybatis.dao.M003bmnmMapper;
import jp.co.necsoft.web_md.core.common.mybatis.dao.M006tenmMapper;
import jp.co.necsoft.web_md.core.common.mybatis.dto.M000kaim;
import jp.co.necsoft.web_md.core.common.mybatis.dto.M000kaimExample;
import jp.co.necsoft.web_md.core.common.mybatis.dto.M001jgym;
import jp.co.necsoft.web_md.core.common.mybatis.dto.M001jgymExample;
import jp.co.necsoft.web_md.core.common.mybatis.dto.M003bmnm;
import jp.co.necsoft.web_md.core.common.mybatis.dto.M003bmnmExample;
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

import com.orangesignal.csv.manager.CsvManager;
import com.orangesignal.csv.manager.CsvManagerFactory;

/**
*棚卸取扱い部門リストのControllerクラス
*
*/
@Security.Authenticated(CCSecurityAuthenticator.class)
public class Zijp1020Ctrl extends Controller {
    /** CCSystemContext */
    @Inject
    private CCSystemContext context;
    /** errRes */
    @Inject
    private ErrorResponse errRes;
    /** dao: DAO for pure sql mapping. */
    @Inject
    private MyBatisSqlMapDao dao;
    /** PROGRAM_ID */
    private static final String PROGRAM_ID = "ZIPR1020";
    /** PROGRAM_VERSION */
    private static final String PROGRAM_VERSION = "V1.01";
    /** EMPTY */
    private static final String EMPTY = "";
    /** ONE */
    private static final String ONE = CCKubunConst.KBN_VAL_Z_SUM_TYPE_TEN;
    /** TWO */
    private static final String TWO = CCKubunConst.KBN_VAL_Z_SUM_TYPE_BMN;
    /** プロパティファイル用キー定義  ダミー店舗コード*/
    public static final String KEY_DUMMY_TEN_CD = "zijp1020.dummy.ten_cd";
    /** M000kaimMapper */
    @Inject
    private M000kaimMapper m000kaimMapper;
    /** M001jgymMapper */
    @Inject
    private M001jgymMapper m001jgymMapper;
    /** M006tenmMapper */
    @Inject
    private M006tenmMapper m006tenmMapper;
    /** M003bmnmMapper */
    @Inject
    private M003bmnmMapper m003bmnmMapper;

    /**
    * 引数とリクエスト(パラメータ)を条件にDBからデータ(複数件)を検索し返却する。*
    * @param kbn 出力区分
    * @param kaisyaCd 会社コード
    * @param jigyobuCd 事業部コード
    * @return クライアントへ返却する結果
    */
    @SuppressWarnings({"unchecked", "rawtypes" })
    public Result query(String kbn, String kaisyaCd, String jigyobuCd) {
        Form<Zijp1020CondForm> emptyForm = new Form(Zijp1020CondForm.class);
        Form<Zijp1020CondForm> reqForm = emptyForm.bindFromRequest();
        Zijp1020CondForm condForm = new Zijp1020CondForm();
        Zijp1020ResultForm resultForm = new Zijp1020ResultForm();
        if (reqForm.hasErrors()) {
            errRes.addErrorInfo(CCMessageConst.MSG_KEY_VALIDATION_ERROR);
            errRes.addErrorInfo(reqForm.errors());
            return badRequest(Json.toJson(errRes));
        }

        condForm = reqForm.get();
        if (condForm == null) {
            return badRequest(Json.toJson(errRes));
        }

        String sKubun = kbn;
        String sKaisyaCode = kaisyaCd;
        String sJigyobuCode = jigyobuCd;
        String sTenpoCode = condForm.getTenCd() == null ? EMPTY : condForm.getTenCd();
        String sBumonCode = condForm.getBmnCd() == null ? EMPTY : condForm.getBmnCd();

        // ------------------------------
        // キー項目チェック
        // ------------------------------
        // 区分入力チェック
        if (!ONE.equals(sKubun) && !TWO.equals(sKubun)) {
            // 「入力データが不正です。1または2を入力して下さい。」
            // sMessage_array[0] = "1(店別)または2(部門別)を入力して下さい。";
            errRes.addErrorInfo("kbn", CCMessageConst.MSG_KEY_INPUT_ERROR_DEPART_OR_STORE);
            ErrorInfo error = errRes.getErrorInfo(CCMessageConst.MSG_KEY_VALIDATION_ERROR);
            errRes.getErrors().add(0, error);
            return badRequest(Json.toJson(errRes));
        }

        // 店舗未入力チェック
        if (ONE.equals(sKubun)) {
            if (CCStringUtil.isEmpty(sTenpoCode)) {
                // この項目は省略できません。
                errRes.addErrorInfo("tenCd", CCMessageConst.MSG_KEY_NOT_KIP_ITEM);
                ErrorInfo error = errRes.getErrorInfo(CCMessageConst.MSG_KEY_VALIDATION_ERROR);
                errRes.getErrors().add(0, error);
                return badRequest(Json.toJson(errRes));
            }
            if (!CCStringUtil.isEmpty(sBumonCode)) {
                // この項目は入力できません。
                errRes.addErrorInfo("bmnCd", CCMessageConst.MSG_KEY_INPUT_NOT_ITEM);
                ErrorInfo error = errRes.getErrorInfo(CCMessageConst.MSG_KEY_VALIDATION_ERROR);
                errRes.getErrors().add(0, error);
                return badRequest(Json.toJson(errRes));
            }
        }

        // 部門未入力チェック
        if (TWO.equals(sKubun)) {
            if (CCStringUtil.isEmpty(sBumonCode)) {
                // この項目は省略できません。
                errRes.addErrorInfo("bmnCd", CCMessageConst.MSG_KEY_NOT_KIP_ITEM);
                ErrorInfo error = errRes.getErrorInfo(CCMessageConst.MSG_KEY_VALIDATION_ERROR);
                errRes.getErrors().add(0, error);
                return badRequest(Json.toJson(errRes));
            }
            if (!CCStringUtil.isEmpty(sTenpoCode)) {
                // この項目は入力できません。
                errRes.addErrorInfo("tenCd", CCMessageConst.MSG_KEY_INPUT_NOT_ITEM);
                ErrorInfo error = errRes.getErrorInfo(CCMessageConst.MSG_KEY_VALIDATION_ERROR);
                errRes.getErrors().add(0, error);
                return badRequest(Json.toJson(errRes));
            }
        }

        // ** キー項目の存在チェック **//
        // 「指定されたキーは未登録です。」
        // 会社
        int iCheckKaisya = this.doCheckKaisyaCode(kaisyaCd);
        if (iCheckKaisya == 9) {
            errRes.addErrorInfo(CCMessageConst.MSG_KEY_FAIL_TO_MASTER_EXITS);
            return badRequest(Json.toJson(errRes));
        } else if (iCheckKaisya == 1) {
            errRes.addErrorInfo("kaisyaCd", CCMessageConst.MSG_KEY_RESULT_NOT_EXISTS);
            ErrorInfo error = errRes.getErrorInfo(CCMessageConst.MSG_KEY_VALIDATION_ERROR);
            errRes.getErrors().add(0, error);
            return badRequest(Json.toJson(errRes));
        }

        // 事業部
        int iCheckJigyobu = this.doCheckJigyobuCode(kaisyaCd, jigyobuCd);
        if (iCheckJigyobu == 9) {
            errRes.getErrorInfo(CCMessageConst.MSG_KEY_FAIL_TO_MASTER_EXITS);
            return badRequest(Json.toJson(errRes));
        } else if (iCheckJigyobu == 1) {
            errRes.addErrorInfo("jigyobuCd", CCMessageConst.MSG_KEY_RESULT_NOT_EXISTS);
            ErrorInfo error = errRes.getErrorInfo(CCMessageConst.MSG_KEY_VALIDATION_ERROR);
            errRes.getErrors().add(0, error);
            return badRequest(Json.toJson(errRes));
        }

        // 店
        if (ONE.equals(sKubun)) {
            if (!"999".equals(sTenpoCode)) {
                int iCheckStore = this.doCheckStoreCode(kaisyaCd, jigyobuCd, sTenpoCode);
                if (iCheckStore == 9) {
                    errRes.addErrorInfo(CCMessageConst.MSG_KEY_FAIL_TO_MASTER_EXITS);
                    return badRequest(Json.toJson(errRes));
                } else if (iCheckStore == 1) {
                    errRes.addErrorInfo("tenCd", CCMessageConst.MSG_KEY_RESULT_NOT_EXISTS);
                    ErrorInfo error = errRes.getErrorInfo(CCMessageConst.MSG_KEY_VALIDATION_ERROR);
                    errRes.getErrors().add(0, error);
                    return badRequest(Json.toJson(errRes));
                }
            }
        }

        // 部門存在チェック
        if (TWO.equals(sKubun)) {
            if (!"999".equals(sBumonCode)) {
                int iCheckBumon = this.doCheckBumonCode(jigyobuCd, sBumonCode);
                if (iCheckBumon == 9) {
                    errRes.addErrorInfo(CCMessageConst.MSG_KEY_FAIL_TO_MASTER_EXITS);
                    return badRequest(Json.toJson(errRes));
                } else if (iCheckBumon == 1) {
                    errRes.addErrorInfo("bmnCd", CCMessageConst.MSG_KEY_RESULT_NOT_EXISTS);
                    ErrorInfo error = errRes.getErrorInfo(CCMessageConst.MSG_KEY_VALIDATION_ERROR);
                    errRes.getErrors().add(0, error);
                    return badRequest(Json.toJson(errRes));
                }
            }
        }

        // ダミー店舗チェック
        if (ONE.equals(sKubun)) {
            if (!"999".equals(sTenpoCode)) {
                // ダミー店舗チェック
                String sDammyTenpo = this.getDammyTenpo();
                // エラー判断
                if (sDammyTenpo == null) {
                    // 「プロパティファイルのダミー店舗コード取得処理に失敗しました。」
                    errRes.addErrorInfo(CCMessageConst.MSG_KEY_DUMMY_TEN_CD_NULL);
                    return badRequest(Json.toJson(errRes));
                }
                if (!CCStringUtil.isEmpty(sDammyTenpo)) {
                    if (Integer.parseInt(sTenpoCode) >= Integer.parseInt(sDammyTenpo)) {
                        // 「対象データがありません。」
                        errRes.addErrorInfo(CCMessageConst.MSG_KEY_NO_FOUND_DATA);
                        return badRequest(Json.toJson(errRes));
                    }
                }
            }
        }

        // ------------------------------
        // 在庫データ取得
        // ------------------------------
        if (!this.execute(condForm, kbn, sKaisyaCode, sJigyobuCode, sTenpoCode, sBumonCode)) {
            return badRequest(Json.toJson(errRes));
        }

        List<Zijp1020MsaiDto> lstZaikoData = condForm.getListData();

        if (null == lstZaikoData || lstZaikoData.size() == 0) {
            // 対象データ０件
            errRes.addErrorInfo(CCMessageConst.MSG_KEY_NO_FOUND_DATA);
            return badRequest(Json.toJson(errRes));
        }

        // ------------------------------
        // CSVファイル作成
        // ------------------------------
        char cPadd;
        cPadd = ' ';

        CCReportUtil cru = null;
        if (ONE.equals(sKubun)) {
            cru = new CCReportUtil("ZISV1020A", context);
        } else {
            cru = new CCReportUtil("ZISV1020B", context);
        }
        CsvManager csvManager = CsvManagerFactory.newCsvManager();
        List<Zipr1020Report1> csvBean1 = new ArrayList<Zipr1020Report1>();
        List<Zipr1020Report2> csvBean2 = new ArrayList<Zipr1020Report2>();

        Zijp1020MsaiDto dto = null;
        Zipr1020Report1 row1 = null;
        Zipr1020Report2 row2 = null;

        // 担当者コードを取得します。
        String tantoCode = context.getTantoCode();
        String kaisyaName = this.getKaisyaName(kaisyaCd);
        String jigyobuName = this.getJigyobuName(kaisyaCd, jigyobuCd);
        for (int i = 0; i < lstZaikoData.size(); i++) {
            row1 = new Zipr1020Report1();
            row2 = new Zipr1020Report2();

            dto = lstZaikoData.get(i);
            // 共通ヘッダ部（プログラムＩＤ、バージョン、担当者名）
            row1.h00_progid = '"' + PROGRAM_ID + '"';
            row1.h00_version = '"' + PROGRAM_VERSION + '"';
            row1.h01_tanto_code = '"' + tantoCode + '"';
            // 会社
            row1.h01_kaisya_code = '"' + sKaisyaCode + '"';
            row1.h01_kaisya_name = '"' + kaisyaName + '"';
            // 事業部
            row1.h02_jigyobu_code = '"' + sJigyobuCode + '"';
            row1.h02_jigyobu_name = '"' + jigyobuName + '"';

            // 当月登録可能日付
            row1.m00_tana_date = "当月１０～翌月７";
            // 4月
            row1.m00_4gatu = '"' + dto.getApril() + '"';
            // 5月
            row1.m00_5gatu = '"' + dto.getMay() + '"';
            // 6月
            row1.m00_6gatu = '"' + dto.getJune() + '"';
            // 7月
            row1.m00_7gatu = '"' + dto.getJuly() + '"';
            // 8月
            row1.m00_8gatu = '"' + dto.getAugust() + '"';
            // 9月
            row1.m00_9gatu = '"' + dto.getSeptember() + '"';
            // 10月
            row1.m00_10gatu = '"' + dto.getOctober() + '"';
            // 11月
            row1.m00_11gatu = '"' + dto.getNovember() + '"';
            // 12月
            row1.m00_12gatu = '"' + dto.getDecember() + '"';
            // 1月
            row1.m00_1gatu = '"' + dto.getJanuary() + '"';
            // 2月
            row1.m00_2gatu = '"' + dto.getFebruary() + '"';
            // 3月
            row1.m00_3gatu = '"' + dto.getMarch() + '"';
            // 更新日付
            row1.m00_kou_date =
                    '"' + CCDateUtil.getDateFormat(CCStringUtil.trimRight(dto.getCmnupddd()), "yyyy/MM/dd") + '"';

            BeanUtils.copyProperties(row1, row2);

            if (ONE.equals(sKubun)) {
                // 店舗
                String sTenCode = CCStringUtil.puddLeft(CCStringUtil.trimRight(dto.getTencd()), 7, cPadd);
                row1.m00_str_code = '"' + sTenCode.substring(4, 7) + '"';
                row1.m00_str_name = '"' + CCStringUtil.trimRight(dto.getTennm()) + '"';
                // 部門
                row1.m00_dpt_code = '"' + CCStringUtil.trimRight(dto.getBmncd()) + '"';
                row1.m00_dpt_name = '"' + CCStringUtil.trimRight(dto.getBmnnm()) + '"';

                csvBean1.add(row1);
            } else {
                // 店舗
                String sTenCode = CCStringUtil.puddLeft(CCStringUtil.trimRight(dto.getTencd()), 7, cPadd);
                row2.m00_str_code = '"' + sTenCode.substring(4, 7) + '"';
                row2.m00_str_name = '"' + CCStringUtil.trimRight(dto.getTennm()) + '"';
                // 部門
                row2.m00_dpt_code = '"' + CCStringUtil.trimRight(dto.getBmncd()) + '"';
                row2.m00_dpt_name = '"' + CCStringUtil.trimRight(dto.getBmnnm()) + '"';

                csvBean2.add(row2);
            }
        }

        if ((ONE.equals(sKubun) && csvBean1.isEmpty()) || (TWO.equals(sKubun) && csvBean2.isEmpty())) {
            // 対象データ０件
            errRes.addErrorInfo(CCMessageConst.MSG_KEY_VALIDATION_ERROR);
            errRes.addErrorInfo("", CCMessageConst.MSG_KEY_NO_FOUND_DATA);
            return notFound();
        }

        // ＣＳＶ作成（データ出力）
        try {
            if (ONE.equals(sKubun)) {
                csvManager.save(csvBean1, Zipr1020Report1.class).to(new File(cru.getCsvFilePath()),
                        CCReportUtil.CSV_OUTPUT_ENCODING);
            } else {
                csvManager.save(csvBean2, Zipr1020Report2.class).to(new File(cru.getCsvFilePath()),
                        CCReportUtil.CSV_OUTPUT_ENCODING);
            }
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
     * 会社コード存在チェック処理.
     * 
     * @param kaisyaCd 会社コード
     * @return int 0:データ有り 1:データ無し 9:エラー
     */
    private int doCheckKaisyaCode(String kaisyaCd) {
        M000kaimExample example = new M000kaimExample();
        example.createCriteria().andKaisyaCdEqualTo(kaisyaCd);
        example.setSearchDate(getDate());
        List<M000kaim> m000kaims = m000kaimMapper.selectByExample(example);

        // 取得データ数ゼロの場合、未登録
        if (null == m000kaims || m000kaims.size() == 0
                || CCSICommon.TYPE_ACT_KBN_DELETED.equals(m000kaims.get(0).getActKbn())) {
            return 1;
        }

        return 0;
    }

    /**
    * 運用日付取得処理.
    * @param なし
    * @return String 運用日付
    */
    private String getDate() {
        return context.getUnyoDate();
    }

    /**
     * 事業部コード存在チェック処理.
     * 
     * @param kaisyaCd 会社コード
     * @param jigyobuCd 事業部コード
     * @return int 0:データ有り 1:データ無し 9:エラー
     */
    private int doCheckJigyobuCode(String kaisyaCd, String jigyobuCd) {
        M001jgymExample example = new M001jgymExample();
        example.createCriteria().andKaisyaCdEqualTo(kaisyaCd).andJigyobuCdEqualTo(jigyobuCd);
        example.setSearchDate(getDate());

        List<M001jgym> m001jgyms = m001jgymMapper.selectByExample(example);

        // 取得データ数ゼロの場合、未登録
        if (null == m001jgyms || m001jgyms.size() == 0
                || CCSICommon.TYPE_ACT_KBN_DELETED.equals(m001jgyms.get(0).getActKbn())) {
            return 1;
        }
        return 0;
    }

    /**
    * 店舗コード存在チェック処理.
    * 
    * @param kaisyaCd 会社コード
    * @param jigyobuCd 事業部コード
    * @param tenCd 店舗
    * @return int 0:データ有り 1:データ無し 9:エラー
    */
    private int doCheckStoreCode(String kaisyaCd, String jigyobuCd, String tenCd) {
        M006tenmExample example = new M006tenmExample();
        example.createCriteria().andTenCdEqualTo(kaisyaCd + jigyobuCd + tenCd);
        example.setSearchDate(getDate());

        List<M006tenm> m006tenms = m006tenmMapper.selectByExample(example);

        // 取得データ数ゼロの場合、未登録
        if (null == m006tenms || m006tenms.size() == 0
                || CCSICommon.TYPE_ACT_KBN_DELETED.equals(m006tenms.get(0).getActKbn())) {
            return 1;
        }
        return 0;
    }

    /**
    * 部門コード存在チェック処理.
    * @param jigyobuCd 事業部コード
    * @param bmnCd 部門コード
    * @return int 0:データ有り 1:データ無し 9:エラー
    */
    private int doCheckBumonCode(String jigyobuCd, String bmnCd) {
        M003bmnmExample example = new M003bmnmExample();
        example.createCriteria().andBmnCdEqualTo(jigyobuCd + bmnCd);
        example.setSearchDate(getDate());
        List<M003bmnm> m003bmnms = m003bmnmMapper.selectByExample(example);

        // 取得データ数ゼロの場合、未登録
        if (null == m003bmnms || m003bmnms.size() == 0
                || CCSICommon.TYPE_ACT_KBN_DELETED.equals(m003bmnms.get(0).getActKbn())) {
            return 1;
        }
        return 0;
    }

    /**
    * ダミー店舗コードを取得する.
    * @param なし
    * @return String ダミー店舗コード
    */
    private String getDammyTenpo() {
        String dammyCode = "";
        dammyCode = context.getContextProperty(KEY_DUMMY_TEN_CD);
        if (dammyCode == null) {
            return null;
        }

        return dammyCode;
    }

    /**
     * 棚卸取扱い部門リストの印字データ取得処理.
     * @param condForm Zijp1020CondForm
     * @param kubun 区分
     * @param kaisyaCode 会社コード
     * @param jigyobuCode 事業部コード
     * @param tenpoCode 店舗コード
     * @param bumonCode 部門コード
     * @return true or false
     */
    private boolean execute(Zijp1020CondForm condForm, String kubun, String kaisyaCode, String jigyobuCode,
            String tenpoCode, String bumonCode) {
        // ダミー店舗チェック
        String sDammyTenpo = getDammyTenpo();
        // エラー判断
        if (sDammyTenpo == null) {
            return false;
        }

        String sDate = getDate();
        if (CCStringUtil.isEmpty(sDate)) {
            return false;
        }
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("kaisyaCd", String.valueOf(kaisyaCode));
        params.put("jigyobuCd", String.valueOf(jigyobuCode));
        params.put("tenCd", String.valueOf(tenpoCode));
        params.put("dammyTenpo", String.valueOf(sDammyTenpo));
        params.put("kbn", String.valueOf(kubun));
        params.put("bmnCd", String.valueOf(bumonCode));
        params.put("sDate", String.valueOf(sDate));

        List<Zijp1020MsaiDto> list = dao.selectMany("selectZ004M003M006Result", params, Zijp1020MsaiDto.class);
        condForm.setListData(list);
        return true;
    }

    /**
    * 会社名取得処理.
    * 
    * @param kaisyaCd 会社コード
    * @return String 会社名
    */
    private String getKaisyaName(String kaisyaCd) {
        String strName = "＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊";

        M000kaimExample example = new M000kaimExample();
        example.createCriteria().andKaisyaCdEqualTo(kaisyaCd);
        example.setSearchDate(getDate());

        List<M000kaim> m000kaims = m000kaimMapper.selectByExample(example);

        // 取得データ数ゼロの場合、未登録
        if (null == m000kaims || m000kaims.size() == 0
                || CCSICommon.TYPE_ACT_KBN_DELETED.equals(m000kaims.get(0).getActKbn())) {
            return strName;
        }

        // 会社名を取得
        return CCStringUtil.trimRight(m000kaims.get(0).getKaiNm());

    }

    /**
    * 事業部名取得処理.
    * 
    * @param kaisyaCd 会社コード
    * @param jigyobuCd 事業部コード
    * @return String 事業部名
    */
    private String getJigyobuName(String kaisyaCd, String jigyobuCd) {
        String strName = "＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊";
        M001jgymExample example = new M001jgymExample();
        example.createCriteria().andKaisyaCdEqualTo(kaisyaCd).andJigyobuCdEqualTo(jigyobuCd);
        example.setSearchDate(getDate());

        List<M001jgym> m001jgyms = m001jgymMapper.selectByExample(example);

        // 取得データ数ゼロの場合、未登録
        if (null == m001jgyms || m001jgyms.size() == 0
                || CCSICommon.TYPE_ACT_KBN_DELETED.equals(m001jgyms.get(0).getActKbn())) {
            return strName;
        }
        // 会社名を取得
        return CCStringUtil.trimRight(m001jgyms.get(0).getJgyNm());
    }

}
