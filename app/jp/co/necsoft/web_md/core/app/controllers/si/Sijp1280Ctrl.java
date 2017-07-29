// /////////////////////////////////////////////////////////////////////
// Copyright(C) 2015 NEC Solution Innovators, Ltd. All Rights Reserved.
// /////////////////////////////////////////////////////////////////////

/*
 * ====================================================================
 * 
 * 機能名称 :店間移動リスト
 * 
 * 改版履歴
 * 
 * Rev. 改版年月日 改版者名 内容
 * 
 * 1.0 20140505 Tinnc 新規作成
 * 
 * ===================================================================
 */

package jp.co.necsoft.web_md.core.app.controllers.si;

import java.io.File;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import jp.co.necsoft.web_md.common.ChaseException;
import jp.co.necsoft.web_md.common.mybatis.dao.MyBatisSqlMapDao;
import jp.co.necsoft.web_md.core.app.dto.si.Sijp1280Result;
import jp.co.necsoft.web_md.core.app.file.report.Sipr1280Report;
import jp.co.necsoft.web_md.core.app.forms.si.Sijp1280CondForm;
import jp.co.necsoft.web_md.core.app.forms.si.Sijp1280ResultForm;
import jp.co.necsoft.web_md.core.common.CCMessageConst;
import jp.co.necsoft.web_md.core.common.CCSecurityAuthenticator;
import jp.co.necsoft.web_md.core.common.CCSystemContext;
import jp.co.necsoft.web_md.core.common.biz.si.CCSICommon;
import jp.co.necsoft.web_md.core.common.forms.ErrorInfo;
import jp.co.necsoft.web_md.core.common.forms.ErrorResponse;
import jp.co.necsoft.web_md.core.common.mybatis.dao.M006tenmMapper;
import jp.co.necsoft.web_md.core.common.mybatis.dto.M006tenm;
import jp.co.necsoft.web_md.core.common.mybatis.dto.M006tenmExample;
import jp.co.necsoft.web_md.core.common.utils.CCDateUtil;
import jp.co.necsoft.web_md.core.common.utils.CCNumberUtil;
import jp.co.necsoft.web_md.core.common.utils.CCReportUtil;
import jp.co.necsoft.web_md.core.common.utils.CCStringUtil;
import play.data.Form;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;

import com.orangesignal.csv.manager.CsvManager;
import com.orangesignal.csv.manager.CsvManagerFactory;

/**
*店間移動リストのControllerクラス
*
*/
@Security.Authenticated(CCSecurityAuthenticator.class)
public class Sijp1280Ctrl extends Controller {
    /**エラー応答*/
    @Inject
    private ErrorResponse errRes;
    /**SQLマップ*/
    @Inject
    private MyBatisSqlMapDao dao;
    /**システムコンテキスト*/
    @Inject
    private CCSystemContext context;
    /**SIの共通*/
    @Inject
    private CCSICommon siCommon;
    /**M006tenmMapper*/
    @Inject
    private M006tenmMapper m006tenmMapper;

    /**プログラムID*/
    private static final String PROGRAM_ID = "SIPR1280";
    /**プログラムのバージョン*/
    private static final String PROGRAM_VERSION = "V1.00";
    /**出力帳票：出荷（名称）*/
    private static final String CON_PRINTTYPE_OUT_NM = "出荷";
    /**出力帳票：入荷（名称）*/
    private static final String CON_PRINTTYPE_IN_NM = "入荷";

    /**
    * 引数とリクエスト(パラメータ)を条件にDBからデータ(複数件)を検索し返却する。*
    *  @param syukaYmdSt 出荷日開始
    *  @param syukaYmdEd 出荷日終了
    *  @return クライアントへ返却する結果
    */
    public Result query(String syukaYmdSt, String syukaYmdEd) {
        @SuppressWarnings({"unchecked", "rawtypes" })
        Form<Sijp1280CondForm> emptyForm = new Form(Sijp1280CondForm.class);
        Form<Sijp1280CondForm> reqForm = emptyForm.bindFromRequest();
        if (reqForm.hasErrors()) {
            errRes.addErrorInfo(CCMessageConst.MSG_KEY_VALIDATION_ERROR);
            errRes.addErrorInfo(reqForm.errors());
            return badRequest(Json.toJson(errRes));
        } else {

            Sijp1280CondForm req = reqForm.get();
            req.setSyukaYmdSt(CCStringUtil.trimBoth(syukaYmdSt));
            req.setSyukaYmdEd(CCStringUtil.trimBoth(syukaYmdEd));

            if (req.getInTenCd() == null) {
                req.setInTenCd("");
            }

            if (req.getOutTenCdSt() == null) {
                req.setOutTenCdSt("");
            }

            if (req.getOutTenCdEd() == null) {
                req.setOutTenCdEd("");
            }

            // 入力データチェック
            if (!doCheckData(req)) {
                ErrorInfo error = errRes.getErrorInfo(CCMessageConst.MSG_KEY_VALIDATION_ERROR);
                errRes.getErrors().add(0, error);
                return badRequest(Json.toJson(errRes));
            }

            Map<String, String> params = new HashMap<String, String>();
            params.put("syukaYmdSt", req.getSyukaYmdSt());
            params.put("syukaYmdEd", req.getSyukaYmdEd());
            params.put("inTenCd", req.getInTenCd());
            params.put("outTenCdSt", req.getOutTenCdSt());
            params.put("outTenCdEd", req.getOutTenCdEd());

            // 台帳に出力するデータを抽出する
            List<Sijp1280Result> list = dao.selectMany("selectSijp1280", params, Sijp1280Result.class);
            if (list.size() == 0) {
                errRes.addErrorInfo(CCMessageConst.MSG_KEY_NOT_EXISTS);
                return notFound(Json.toJson(errRes));
            }

            CCReportUtil cru = new CCReportUtil("SISV1280", context);
            CsvManager csvManager = CsvManagerFactory.newCsvManager();
            List<Sipr1280Report> csvBean = new ArrayList<Sipr1280Report>();

            String tantoCode = context.getTantoCode();
            // 基準日
            String sBaseDate = CCDateUtil.getSysDateFormat("yyyyMMdd");

            for (Sijp1280Result sijp1280Result : list) {
                // 伝票№を取得
                String sDpyNO = sijp1280Result.getDpyNo();

                // 入荷店コード
                String sInTenCode = sijp1280Result.getInTenpoCd();

                // 入荷部門コード
                String sInBmnCd = sijp1280Result.getInBmnCd();

                // 出荷店コード
                String sOutTenCode = sijp1280Result.getOutTenpoCd();

                // 出荷日
                String sSyukaDate = sijp1280Result.getSyukaYmd();

                // 明細エリア定義
                String[][] sMeiData = new String[10][7];

                // 明細エリアへ明細データセット
                try {
                    for (int j = 0; j < sMeiData.length; j++) {
                        Method getMesaiNoMethod =
                                Sijp1280Result.class.getDeclaredMethod("getMesaiNo"
                                        + CCStringUtil.suppZero(String.valueOf(j + 1), 2));
                        sMeiData[j][0] = String.valueOf(getMesaiNoMethod.invoke(sijp1280Result));

                        Method getShnCdMethod =
                                Sijp1280Result.class.getDeclaredMethod("getShnCd"
                                        + CCStringUtil.suppZero(String.valueOf(j + 1), 2));
                        sMeiData[j][1] = String.valueOf(getShnCdMethod.invoke(sijp1280Result));

                        Method getKenBaraSuMethod =
                                Sijp1280Result.class.getDeclaredMethod("getKenBaraSu"
                                        + CCStringUtil.suppZero(String.valueOf(j + 1), 2));
                        sMeiData[j][2] = String.valueOf(getKenBaraSuMethod.invoke(sijp1280Result));

                        Method getKenGenkMethod =
                                Sijp1280Result.class.getDeclaredMethod("getKenGenk"
                                        + CCStringUtil.suppZero(String.valueOf(j + 1), 2));
                        sMeiData[j][3] = String.valueOf(getKenGenkMethod.invoke(sijp1280Result));

                        Method getKenBaikMethod =
                                Sijp1280Result.class.getDeclaredMethod("getKenBaik"
                                        + CCStringUtil.suppZero(String.valueOf(j + 1), 2));
                        sMeiData[j][4] = String.valueOf(getKenBaikMethod.invoke(sijp1280Result));

                        Method getKenGenkKinMethod =
                                Sijp1280Result.class.getDeclaredMethod("getKenGenkKin"
                                        + CCStringUtil.suppZero(String.valueOf(j + 1), 2));
                        sMeiData[j][5] = String.valueOf(getKenGenkKinMethod.invoke(sijp1280Result));

                        Method getKenBaikKinMethod =
                                Sijp1280Result.class.getDeclaredMethod("getKenBaikKin"
                                        + CCStringUtil.suppZero(String.valueOf(j + 1), 2));
                        sMeiData[j][6] = String.valueOf(getKenBaikKinMethod.invoke(sijp1280Result));
                    }
                } catch (Exception e) {
                    throw new ChaseException(e);
                }

                String sysDate =
                        '"' + CCDateUtil.getSysYear() + CCDateUtil.getSysMonth() + CCDateUtil.getSysDate() + '"';
                String sysTime = '"' + CCDateUtil.getSysHour() + CCDateUtil.getSysMinute() + '"';

                for (int l = 0; l < sMeiData.length; l++) {
                    if (!CCStringUtil.isEmpty(sMeiData[l][1])) {
                        Sipr1280Report row = new Sipr1280Report();
                        // プログラムＩＤ
                        row.h00_progid = '"' + PROGRAM_ID + '"';
                        // バージョン
                        row.h00_version = '"' + PROGRAM_VERSION + '"';
                        // システム日付
                        row.h00_sdate = sysDate;

                        // システム時間
                        row.h00_stime = sysTime;
                        // ページ番号
                        row.h00_page = '"' + "" + '"';
                        // 担当者コード
                        row.h01_tanto_code = '"' + tantoCode + '"';

                        // -----------------
                        // CSV作成処理（明細）
                        // -----------------
                        // 出力帳票
                        if (!CCStringUtil.isEmpty(req.getInTenCd()) && CCStringUtil.isEmpty(req.getOutTenCdSt())
                                && CCStringUtil.isEmpty(req.getOutTenCdEd())) {
                            // 入荷店のみ入力の場合→入荷店
                            row.h01_title_name = "（" + CON_PRINTTYPE_IN_NM + "店）";
                        } else {
                            // 出荷店のみ、もしくは両方入力の場合→出荷店
                            row.h01_title_name = "（" + CON_PRINTTYPE_OUT_NM + "店）";
                        }

                        // 出荷店舗
                        // 店舗ｺｰﾄﾞ
                        String sOutTenCd = sOutTenCode;
                        String sOutTenNm = "";

                        // 店舗名
                        sOutTenNm = siCommon.getComTenpoName(sOutTenCd, sBaseDate);

                        row.h01_out_ten_code = '"' + sOutTenCd + '"';
                        row.h01_out_ten_name = '"' + sOutTenNm + '"';

                        // 入荷店舗
                        // 店舗ｺｰﾄﾞ
                        String sInTenCd = sInTenCode;
                        String sInTenNm = "";

                        // 店舗名
                        sInTenNm = siCommon.getComTenpoName(sInTenCd, sBaseDate);

                        row.h01_in_ten_code = '"' + sInTenCd + '"';
                        row.h01_in_ten_name = '"' + sInTenNm + '"';

                        // 部門コード
                        row.m01_bmn_code = sInBmnCd;
                        // 伝票№
                        row.m01_den_no = sDpyNO;
                        // 出荷日
                        row.m01_syuka_date = '"' + sSyukaDate + '"';

                        // 伝票№(dummy)
                        row.m01_break_key = sDpyNO;
                        // 明細行番号
                        row.m01_meisai_no = '"' + sMeiData[l][0] + '"';
                        
                        String shnCode = sMeiData[l][1];
                        // 商品コード
                        row.m01_shn_code = '"' + sMeiData[l][1] + '"';

                        // 商品名取得
                        String sShnName = siCommon.getComKihonName(shnCode, sBaseDate);

                        // 商品名
                        row.m01_shn_name = '"' + sShnName + '"';

                        // 数量
                        row.m01_ken_bara_su = sMeiData[l][2];

                        // 原単価
                        row.m01_ken_genk = sMeiData[l][3];

                        // 原価金額
                        row.m01_ken_genk_kin = sMeiData[l][5];

                        // 売単価
                        row.m01_ken_baik = sMeiData[l][4];

                        // 売価金額
                        row.m01_ken_baik_kin = sMeiData[l][6];

                        csvBean.add(row);
                    }
                }

            }

            if (csvBean.isEmpty()) {
                // 対象データ０件
                errRes.addErrorInfo(CCMessageConst.MSG_KEY_NO_FOUND_DATA);
                return notFound();
            }

            // ＣＳＶ作成（データ出力）
            try {
                csvManager.save(csvBean, Sipr1280Report.class).to(new File(cru.getCsvFilePath()),
                        CCReportUtil.CSV_OUTPUT_ENCODING);
            } catch (Exception e) {
                throw new ChaseException(e);
            }

            cru.makePDF();
            List<Sijp1280ResultForm> reportList = new ArrayList<Sijp1280ResultForm>();
            Sijp1280ResultForm resultForm = new Sijp1280ResultForm();
            try {
                URL uPdfUrl = new URL(cru.getPdfUrl());
                resultForm.setPdfUrl(uPdfUrl);
            } catch (Exception e) {
                throw new ChaseException(e);
            }
            reportList.add(resultForm);
            // gc対応
            cru = null;
            System.gc();

            return ok(Json.toJson(reportList));
        }
    }

    /*****************************************************************
     * 　入力データの整合性チェック処理
    * <p>
    * 機能概要：<br>
    * 入力データの整合性をチェックします。
    * <p> 
    * 作成日：<br> 
    *　2014/04/08（Mars）<br> 
    *　　新規作成<br>
    * <p> 
    * @param req なし
    * @return true：処理成功、false：処理失敗
    *****************************************************************/
    private boolean doCheckData(Sijp1280CondForm req) {
        boolean bChkData = true;
        // 日付が指定されていない場合、システム日付をセットする
        String sBaseDate = CCDateUtil.getSysDateFormat("yyyyMMdd");

        // 入荷店　店舗コードチェック
        if (!CCStringUtil.isEmpty(req.getInTenCd())) {
            if (!CCNumberUtil.isNumeric(req.getInTenCd())) {
                // 入力された項目に誤りがあります。
                errRes.addErrorInfo("inTenCd", CCMessageConst.MSG_KEY_ITEM_INPUT_IS_ERROR);
                bChkData = false;
            } else {
                req.setInTenCd(req.getInKaisyaCd() + req.getInJigyobuCd() + req.getInTenCd());
                if (CCStringUtil.trimBoth(getTenInf(req.getInTenCd(), sBaseDate)).length() == 0) {
                    // 指定されたキーは未登録です。
                    errRes.addErrorInfo("inTenCd", CCMessageConst.MSG_KEY_NOT_EXIST_REGISTERED);
                    bChkData = false;
                }
            }
        }

        boolean outC = false;

        // 出荷店　店舗コードチェック
        if (!CCStringUtil.isEmpty(req.getOutTenCdSt())) {
            outC = true;
            if (!CCNumberUtil.isNumeric(req.getOutTenCdSt())) {
                // 入力された項目に誤りがあります。
                errRes.addErrorInfo("outTenCdSt", CCMessageConst.MSG_KEY_ITEM_INPUT_IS_ERROR);
                bChkData = false;
            } else {
                req.setOutTenCdSt(req.getOutKaisyaCd() + req.getOutJigyobuCd() + req.getOutTenCdSt());
                if (CCStringUtil.trimBoth(getTenInf(req.getOutTenCdSt(), sBaseDate)).length() == 0) {
                    // 指定されたキーは未登録です。
                    errRes.addErrorInfo("outTenCdSt", CCMessageConst.MSG_KEY_NOT_EXIST_REGISTERED);
                    bChkData = false;
                }
            }
        }

        // 出荷店　店舗コードチェック
        if (!CCStringUtil.isEmpty(req.getOutTenCdEd())) {
            outC = true;
            if (!CCNumberUtil.isNumeric(req.getOutTenCdEd())) {
                // 入力された項目に誤りがあります。
                errRes.addErrorInfo("outTenCdEd", CCMessageConst.MSG_KEY_ITEM_INPUT_IS_ERROR);
                bChkData = false;
            } else {
                req.setOutTenCdEd(req.getOutKaisyaCd() + req.getOutJigyobuCd() + req.getOutTenCdEd());
                if (CCStringUtil.trimBoth(getTenInf(req.getOutTenCdEd(), sBaseDate)).length() == 0) {
                    // 指定されたキーは未登録です。
                    errRes.addErrorInfo("outTenCdEd", CCMessageConst.MSG_KEY_NOT_EXIST_REGISTERED);
                    bChkData = false;
                }
            }
        }
        if (!outC) {
            // 入荷店、出荷店両方が未入力の場合（いずれか必須）
            if (CCStringUtil.isEmpty(req.getInTenCd())) {
                // この項目は省略できません。
                errRes.addErrorInfo("inTenCd", CCMessageConst.MSG_KEY_DATA_IS_EMPTY);
                errRes.addErrorInfo("outTenCdSt", CCMessageConst.MSG_KEY_DATA_IS_EMPTY);
                errRes.addErrorInfo("outTenCdEd", CCMessageConst.MSG_KEY_DATA_IS_EMPTY);
                bChkData = false;

            }
        }

        if (!bChkData) {
            return false;
        }

        // 出荷日Fromの日付妥当性チェック
        if (!CCDateUtil.isDate(req.getSyukaYmdSt())) {
            // エラーメッセージを表示し、フォーカスをセットする。
            // 指定されたキーは未登録です。
            errRes.addErrorInfo("syukaYmdSt", CCMessageConst.MSG_KEY_DATE_ERROR);
            bChkData = false;
        }

        // 出荷日Toの日付妥当性チェック
        if (!CCDateUtil.isDate(req.getSyukaYmdEd())) {
            // エラーメッセージを表示し、フォーカスをセットする。
            // 指定されたキーは未登録です。
            errRes.addErrorInfo("syukaYmdEd", CCMessageConst.MSG_KEY_DATE_ERROR);
            bChkData = false;
        }

        return bChkData;
    }

    /**
     * Function chech tenCd is exists
     * 
     * @param date date
     * @param tenCd tenCd
     * @return boolean exists or not
     */
    private String getTenInf(String tenCd, String date) {
        String sRet = "";
        M006tenmExample example = new M006tenmExample();

        example.createCriteria().andTenCdEqualTo(tenCd);

        example.setSearchDate(date);

        List<M006tenm> list = this.m006tenmMapper.selectByExample(example);

        if (list.size() > 0) {
            // 店舗名
            sRet = CCStringUtil.trimBoth(list.get(0).getTenNm());
        }

        return sRet;
    }

}
