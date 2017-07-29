// /////////////////////////////////////////////////////////////////////
// Copyright(C) 2015 NEC Solution Innovators, Ltd. All Rights Reserved.
// /////////////////////////////////////////////////////////////////////

/*
 * ====================================================================
 * 
 * 機能名称 :伝票入力プルーフリスト
 * 
 * 改版履歴
 * 
 * Rev. 改版年月日 改版者名 内容
 * 
 * 1.0 20140429 Tinnc 新規作成
 * 
 * ===================================================================
 */
package jp.co.necsoft.web_md.core.app.controllers.si;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import jp.co.necsoft.web_md.common.ChaseException;
import jp.co.necsoft.web_md.common.mybatis.dao.MyBatisSqlMapDao;
import jp.co.necsoft.web_md.core.app.dto.si.Sijp1230Result;
import jp.co.necsoft.web_md.core.app.file.report.Sipr1230Report;
import jp.co.necsoft.web_md.core.app.forms.si.Sijp1230CondForm;
import jp.co.necsoft.web_md.core.app.forms.si.Sijp1230ResultForm;
import jp.co.necsoft.web_md.core.common.CCMessageConst;
import jp.co.necsoft.web_md.core.common.CCSecurityAuthenticator;
import jp.co.necsoft.web_md.core.common.CCSystemContext;
import jp.co.necsoft.web_md.core.common.biz.si.CCSICommon;
import jp.co.necsoft.web_md.core.common.biz.si.CCSIConst;
import jp.co.necsoft.web_md.core.common.forms.ErrorInfo;
import jp.co.necsoft.web_md.core.common.forms.ErrorResponse;
import jp.co.necsoft.web_md.core.common.mybatis.dao.M003bmnmMapper;
import jp.co.necsoft.web_md.core.common.mybatis.dao.M011trimMapper;
import jp.co.necsoft.web_md.core.common.mybatis.dao.M016tanmMapper;
import jp.co.necsoft.web_md.core.common.mybatis.dto.M003bmnm;
import jp.co.necsoft.web_md.core.common.mybatis.dto.M003bmnmExample;
import jp.co.necsoft.web_md.core.common.mybatis.dto.M011trim;
import jp.co.necsoft.web_md.core.common.mybatis.dto.M011trimExample;
import jp.co.necsoft.web_md.core.common.mybatis.dto.M016tanm;
import jp.co.necsoft.web_md.core.common.mybatis.dto.M016tanmExample;
import jp.co.necsoft.web_md.core.common.utils.CCDateUtil;
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
*伝票入力プルーフリストのControllerクラス
*
*/
@Security.Authenticated(CCSecurityAuthenticator.class)
public class Sijp1230Ctrl extends Controller {
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

    /** M016tanmMapper */
    @Inject
    private M016tanmMapper m016tanmMapper;
    /**M003bmnmMapper*/
    @Inject
    private M003bmnmMapper m003bmnmMapper;
    /**M011trimMapper*/
    @Inject
    private M011trimMapper m011trimMapper;

    /**プログラムID*/
    private static final String PROGRAM_ID = "SIPR1230";
    /**バージョン*/
    private static final String PROGRAM_VERSION = "V1.00";
    /**削除*/
    private static final String TYPE_ACT_KBN_DELETED = "9";

    /**
    * 引数とリクエスト(パラメータ)を条件にDBからデータ(複数件)を検索し返却する。*
    *  @return クライアントへ返却する結果
    *  @param tantoCd 担当者コード
    */
    public Result query(String tantoCd) {
        @SuppressWarnings({"unchecked", "rawtypes" })
        Form<Sijp1230CondForm> emptyForm = new Form(Sijp1230CondForm.class);
        Form<Sijp1230CondForm> reqForm = emptyForm.bindFromRequest();
        if (reqForm.hasErrors()) {
            errRes.addErrorInfo(CCMessageConst.MSG_KEY_VALIDATION_ERROR);
            errRes.addErrorInfo(reqForm.errors());
            return badRequest(Json.toJson(errRes));
        } else {

            Sijp1230CondForm req = reqForm.get();
            req.setTantoCd(tantoCd);

            if (req.getNhmYmd() == null) {
                req.setNhmYmd("");
            }

            if (req.getCmnUpddd() == null) {
                req.setCmnUpddd("");
            }

            if (req.getInsTimeSt() == null) {
                req.setInsTimeSt("");
            }

            if (req.getInsTimeEd() == null) {
                req.setInsTimeEd("");
            }

            if (req.getDpyKbn() == null) {
                req.setDpyKbn("");
            }

            // 入力データチェック
            if (!doCheckData(req)) {
                ErrorInfo error = errRes.getErrorInfo(CCMessageConst.MSG_KEY_VALIDATION_ERROR);
                errRes.getErrors().add(0, error);
                return badRequest(Json.toJson(errRes));
            }

            Map<String, String> params = new HashMap<String, String>();
            params.put("cmnUpddd", req.getCmnUpddd());
            String sTanto = req.getTantoCd();
            params.put("tantoCd", sTanto);

            if (CCStringUtil.isEmpty(req.getInsTimeSt())) {
                params.put("insTimeSt", "");
            } else {
                params.put("insTimeSt", req.getInsTimeSt() + "00");
            }

            if (CCStringUtil.isEmpty(req.getInsTimeEd())) {
                params.put("insTimeEd", "");
            } else {
                params.put("insTimeEd", req.getInsTimeEd() + "99");
            }

            params.put("dpyKbn", req.getDpyKbn());

            params.put("nhmYmd", req.getNhmYmd());

            // 台帳に出力するデータを抽出する
            List<Sijp1230Result> list = dao.selectMany("selectSijp1230", params, Sijp1230Result.class);
            if (list.size() == 0) {
                errRes.addErrorInfo(CCMessageConst.MSG_KEY_NO_FOUND_DATA);
                return notFound(Json.toJson(errRes));
            }

            CCReportUtil cru = new CCReportUtil("SISV1230", context);
            CsvManager csvManager = CsvManagerFactory.newCsvManager();
            List<Sipr1230Report> csvBean = new ArrayList<Sipr1230Report>();

            // ----------
            // 共通ヘッダ
            // ----------
            // 日付,時刻取得
            String sSysDate = CCDateUtil.getSysYear() + CCDateUtil.getSysMonth() + CCDateUtil.getSysDate();
            String sSysTime = CCDateUtil.getSysHour() + CCDateUtil.getSysMinute();

            for (Sijp1230Result sijp1230Result : list) {
                Sipr1230Report row = new Sipr1230Report();

                // 共通ヘッダ部
                // プログラムＩＤ
                row.h00_progid = '"' + PROGRAM_ID + '"';
                // バージョン
                row.h00_version = '"' + PROGRAM_VERSION + '"';
                // システム日付
                row.h00_sdate = '"' + sSysDate + '"';
                // システム時間
                row.h00_stime = '"' + sSysTime + '"';
                // ページ番号
                row.h00_page = '"' + "" + '"';
                // 担当者コード
                row.h01_tanto_code = '"' + sTanto + '"';

                // ----------------------------
                // 明細部(ヘッダ情報＋商品情報)
                // ----------------------------

                // 基準日付
                String sDate = sijp1230Result.getNhnYmd();
                if (sDate == null || sDate.trim().length() == 0) {
                    sDate = sSysDate;
                }

                // -----------
                // ヘッダ情報
                // -----------
                String sKaisyaCd = sijp1230Result.getKaisyaCd();

                String sJigyobuCd = sijp1230Result.getJigyoubuCd();

                String sDenKbn = sijp1230Result.getDpyKbn();

                String sTantoCd = tantoCd;
                String sTantoCdNM = getComTantoName(sTantoCd, sSysDate);

                // 入力担当者
                row.h01_tanto = '"' + sTantoCd + '"';
                // 入力担当者名
                row.h01_tanto_name = '"' + sTantoCdNM + '"';
                // 入力日付
                row.h01_input_date = sijp1230Result.getInsdd();

                // ---------
                // 明細情報
                // ---------

                // 入力区分
                String sDelKbn = "";

                if (sDenKbn.equals(CCSIConst.DEN_KBN.KBN_EOSSIR)) {

                    String sStsKbn = sijp1230Result.getSyoriStsKbn();
                    if (sStsKbn.equals("9")) {
                        sDelKbn = "D";
                    } else {
                        String sUpdKbn = sijp1230Result.getUpdateKbn();
                        if (sUpdKbn.equals("D")) {
                            sDelKbn = "D";
                        }
                        // 今回SEQ№
                        String sSeqNo = String.valueOf(sijp1230Result.getSeqno());
                        if (!sSeqNo.equals("1") && sUpdKbn.equals("I")) {
                            sDelKbn = "U";
                        }
                    }
                } else {
                    String sUpdKbn = sijp1230Result.getUpdateKbn();
                    if (sUpdKbn.equals("D")) {
                        sDelKbn = "D";
                    } else {
                        // 今回SEQ№
                        String sSeqNo = String.valueOf(sijp1230Result.getSeqno());
                        if (!sSeqNo.equals("1") && sUpdKbn.equals("I")) {
                            sDelKbn = "U";
                        }
                    }
                }

                // 店
                String sTenCd = sijp1230Result.getTenCd();
                String sTenNm = siCommon.getComTenpoNameR(sKaisyaCd + sJigyobuCd + sTenCd, sDate);

                // 部門
                String sBmnCd = sijp1230Result.getBmnCd();
                if (!CCStringUtil.isEmpty(sBmnCd) && sBmnCd.trim().length() >= 5) {
                    sBmnCd = sBmnCd.substring(2, 5);
                } else {
                    sBmnCd = "";
                }
                String sBmnNm = getComBumonNameR(sJigyobuCd + sBmnCd, sDate);

                // 取引先
                String sToriCd = sijp1230Result.getTorihikiCd();

                if (sToriCd == null) {
                    sToriCd = "";
                }

                String sToriNm = "";
                // 取引先が設定時のみ編集を行う。
                if (sToriCd.trim().length() != 0) {
                 // 取引先ｺｰﾄﾞを６桁表示
                	if (sToriCd.length() > 6) {
                        sToriCd = sToriCd.substring(0, 6);
                	}
                    // 代表取引先ｺｰﾄﾞで取引先名を取得
                    sToriNm = getComToriNameR(sToriCd + "000", sDate);
                }

                String sSeqNo = String.valueOf(sijp1230Result.getSeqno());

                row.m01_input_time = '"' + sijp1230Result.getInstime() + '"'; // 入力時刻
                row.m01_seqno = '"' + sSeqNo + '"'; // SEQNO
                row.m01_del_kbn = '"' + sDelKbn + '"'; // 削除区分

                row.m01_denkbn = '"' + sijp1230Result.getDpyKbn() + '"'; // 伝区
                row.m01_noh_date = '"' + sijp1230Result.getNhnYmd() + '"'; // 納品日

                row.m01_ten_code = '"' + sJigyobuCd + sTenCd + '"'; // 店

                row.m01_ten_name = '"' + sTenNm + '"'; // 店名
                row.m01_bmn_code = '"' + sBmnCd + '"'; // 部門
                row.m01_bmn_name = '"' + sBmnNm + '"'; // 部門名
                row.m01_den_no = '"' + sijp1230Result.getDpyNo() + '"'; // 伝票№

                row.m01_tori_code = '"' + sToriCd + '"'; // 取引先
                row.m01_tori_name = '"' + sToriNm + '"'; // 取引先名

                String sHatDate = String.valueOf(sijp1230Result.getHatYmd());
                if (sHatDate == null) {
                    sHatDate = "";
                }
                row.m01_hat_date = '"' + sHatDate + '"'; // 発注日

                row.m01_gen_king = '"' + String.valueOf(sijp1230Result.getsKenGenkKin()) + '"'; // 原価金額
                row.m01_bai_king = '"' + String.valueOf(sijp1230Result.getsKenBaikKin()) + '"'; // 原価金額
                row.m01_nounin_r = '"' + String.valueOf(sijp1230Result.getDctcKbn()) + '"'; // 納品ルート
                row.m01_out_bmn = '"' + String.valueOf(sijp1230Result.getOutBmn()) + '"'; // 出荷店部門コード

                csvBean.add(row);
            }

            if (csvBean.isEmpty()) {
                // 対象データ０件
                errRes.addErrorInfo(CCMessageConst.MSG_KEY_NO_FOUND_DATA);
                return notFound();
            }

            try {
                csvManager.save(csvBean, Sipr1230Report.class).to(new File(cru.getCsvFilePath()),
                        CCReportUtil.CSV_OUTPUT_ENCODING);
            } catch (Exception e) {
                throw new ChaseException(e);
            }

            cru.makePDF();
            List<Sijp1230ResultForm> reportList = new ArrayList<Sijp1230ResultForm>();
            Sijp1230ResultForm resultForm = new Sijp1230ResultForm();
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
    private boolean doCheckData(Sijp1230CondForm req) {
        boolean bChkData = true;

        // 伝区;
        if (req.getDpyKbn().trim().length() != 0) {
            String sDenKbn = CCStringUtil.suppZero(req.getDpyKbn(), 2);
            // 伝区名
            String sDenKbnNM = siCommon.getSirName(CCSICommon.CN_DPY_KBN + sDenKbn);
            if (sDenKbnNM.trim().length() == 0) {
                // 入力された項目に誤りがあります。
                errRes.addErrorInfo("dpyKbn", CCMessageConst.MSG_KEY_ITEM_INPUT_IS_ERROR);
                bChkData = false;
            }
        }

        // 納品日
        if (!CCStringUtil.isEmpty(req.getNhmYmd())) {
            // 納品日の日付妥当性チェック
            if (!CCDateUtil.isDate(req.getNhmYmd())) {
                errRes.addErrorInfo("nhmYmd", CCMessageConst.MSG_KEY_DATE_ERROR);
                bChkData = false;
            }
        }

        // 納品日
        if (!CCStringUtil.isEmpty(req.getCmnUpddd())) {
            // 納品日の日付妥当性チェック
            if (!CCDateUtil.isDate(req.getCmnUpddd())) {
                errRes.addErrorInfo("nhmYmd", CCMessageConst.MSG_KEY_DATE_ERROR);
                bChkData = false;
            }
        }

        // 納品日、入力日付が共に未入力の場合
        if (CCStringUtil.isEmpty(req.getNhmYmd()) && CCStringUtil.isEmpty(req.getCmnUpddd())) {
            errRes.addErrorInfo("nhmYmd", CCMessageConst.MSG_KEY_ENTER_INPUT_DATE_OR_DELIVERY_DATE);
            bChkData = false;
        }

        return bChkData;
    }

    /*****************************************************************
     * 担当者マスタからの担当者名取得処理.
     * <p>
     * 機能概要：<br>
     *　共通部品を使用して、担当者マスタからの担当者名を取得し<br>
     *　ます。<br>
     * <p> 
     * 作成日：<br> 
     *　2014/04/29<br> 
     *　　新規作成<br>
     * <p> 
     * @param sTanto 担当者コード
     * @param sDate 基準日付
     * @return 担当者名
     *****************************************************************/
    public String getComTantoName(String sTanto, String sDate) {
        String sRet = "";
        // 担当者マスタ検索オブジェクト作成
        M016tanmExample m016tanmExample = new M016tanmExample();
        m016tanmExample.createCriteria().andTantoCdEqualTo(sTanto);
        m016tanmExample.setSearchDate(sDate);

        List<M016tanm> listM016tanm = this.m016tanmMapper.selectByExample(m016tanmExample);

        if (listM016tanm.size() > 0) {
            if (!TYPE_ACT_KBN_DELETED.equals(listM016tanm.get(0).getActKbn())) {
                sRet = CCStringUtil.trimBoth(listM016tanm.get(0).getTantoNm());
            }
        }
        return sRet;

    }

    /*****************************************************************
     * 部門マスタからの部門略称取得処理.
     * <p>
     * 機能概要：<br>
            * 　共通部品を使用して、部門コードより部門略称を取得します。<br>
     * <p> 
     * 作成日：<br> 
            * 　2014/04/29<br> 
            * 　　新規作成<br>
     * <p> 
     * @param sBmncd 部門コード（５桁）
     * @param sDate 日付
     * @return 部門略称
     *****************************************************************/
    public String getComBumonNameR(String sBmncd, String sDate) {
        String sRet = "";

        if (sBmncd == null || sBmncd.trim().length() == 0) {
            return sRet;
        }
        if (sDate == null || sDate.trim().length() == 0) {
            return sRet;
        }

        // 部門マスタ検索オブジェクト作成
        M003bmnmExample m003bmnmExample = new M003bmnmExample();

        m003bmnmExample.createCriteria().andBmnCdEqualTo(sBmncd);

        m003bmnmExample.setSearchDate(sDate);

        List<M003bmnm> listM003bmnm = this.m003bmnmMapper.selectByExample(m003bmnmExample);

        if (listM003bmnm.size() > 0) {
            if (!TYPE_ACT_KBN_DELETED.equals(listM003bmnm.get(0).getActKbn())) {
                // 部門名（ｶﾅ）
                sRet = CCStringUtil.trimBoth(listM003bmnm.get(0).getBmnNmR());
            }
        }

        return sRet;

    }

    /*****************************************************************
     * 取引先マスタからの取引先名(略称)取得処理.
     * <p>
     * 機能概要：<br>
            * 　共通部品を使用して、取引先コードより取引先名(漢字)を<br>
            * 　取得します。<br>
     * <p> 
     * 作成日：<br> 
            * 　2014/04/29（<br> 
            * 　　新規作成<br>
     * <p> 
     * @param sToricd 取引先コード
     * @param sDate DATE
     * @return 取引先名(略称)
     *****************************************************************/
    public String getComToriNameR(String sToricd, String sDate) {
        String sRet = "";
        if (sToricd == null || sToricd.trim().length() == 0) {
            return sRet;
        }
        if (sDate == null || sDate.trim().length() == 0) {
            return sRet;
        }

        // 取引先マスタ検索オブジェクト作成
        M011trimExample example = new M011trimExample();

        example.createCriteria().andTriCdEqualTo(sToricd);

        example.setSearchDate(sDate);

        List<M011trim> list = this.m011trimMapper.selectByExample(example);

        if (list.size() > 0) {
            if (!TYPE_ACT_KBN_DELETED.equals(list.get(0).getActKbn())) {
                // 取引先名(略称)
                sRet = CCStringUtil.trimBoth(list.get(0).getTriNmR());
            }
        }
        return sRet;

    }

    /**
     * 初期値を設定する
     * @return CondFormクラス 
     */
    public Result initCond() {
        Sijp1230CondForm sijp1230CondForm = new Sijp1230CondForm();

        // 入力担当者
        sijp1230CondForm.setTantoCd(context.getTantoCode());
        sijp1230CondForm.setCmnUpddd(CCDateUtil.getSysDateFormat("yyyyMMdd"));
        sijp1230CondForm.setLastTantoNm(context.getTantoName());
        List<Sijp1230CondForm> condForms = new ArrayList<Sijp1230CondForm>();
        condForms.add(sijp1230CondForm);
        return ok(Json.toJson(condForms));
    }

}
