// /////////////////////////////////////////////////////////////////////
// Copyright(C) 2015 NEC Solution Innovators, Ltd. All Rights Reserved.
// /////////////////////////////////////////////////////////////////////

/*
 * ====================================================================
 * 
 * 機能名称 : デモ棚リスト
 * 
 * 
 * 改版履歴
 * 
 * Rev. 改版年月日 改版者名 内容
 * 
 * 1.0 2015-06-24 chiennt 新規作成
 * 
 * ===================================================================
 */

package jp.co.necsoft.web_md.core.app.controllers.gn;

import java.io.File;
import java.math.BigDecimal;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import jp.co.necsoft.web_md.common.ChaseException;
import jp.co.necsoft.web_md.common.biz.IPluConverter;
import jp.co.necsoft.web_md.common.mybatis.dao.MyBatisSqlMapDao;
import jp.co.necsoft.web_md.core.app.dto.gn.Gnjp7030Param;
import jp.co.necsoft.web_md.core.app.dto.gn.Gnjp7030Result;
import jp.co.necsoft.web_md.core.app.file.report.Gnpr7030Report;
import jp.co.necsoft.web_md.core.app.forms.gn.Gnjp7030CondForm;
import jp.co.necsoft.web_md.core.app.forms.gn.Gnjp7030ResultForm;
import jp.co.necsoft.web_md.core.common.CCMessageConst;
import jp.co.necsoft.web_md.core.common.CCSecurityAuthenticator;
import jp.co.necsoft.web_md.core.common.CCSystemContext;
import jp.co.necsoft.web_md.core.common.forms.ErrorInfo;
import jp.co.necsoft.web_md.core.common.forms.ErrorResponse;
import jp.co.necsoft.web_md.core.common.utils.CCCheckExistsUtil;
import jp.co.necsoft.web_md.core.common.utils.CCDateUtil;
import jp.co.necsoft.web_md.core.common.utils.CCReportUtil;
import jp.co.necsoft.web_md.core.common.utils.CCStringUtil;
import jp.co.necsoft.web_md.core.common.utils.DataUtil;
import play.data.Form;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;

import com.orangesignal.csv.manager.CsvManager;
import com.orangesignal.csv.manager.CsvManagerFactory;

/**
 * デモ棚リストのControllerクラス
 * 
 */
@Security.Authenticated(CCSecurityAuthenticator.class)
public class Gnjp7030Ctrl extends Controller {    

    /** プログラムＩＤ */
    private static final String PROGRAM_ID = "GNPR7030";

    /** バージョン */
    private static final String PROGRAM_VERSION = "1.0";
    
    /** KETA */
    private static final int KETA = 0;
    
    /** Blank */
    private static final String BLANK = "";
    
    /** Promotion Space*/
    private static final String SHN_NM_PROMO = "プロモーションスペース";
    
    /** 更新区分名称_新規*/
    private static final String UPD_KBN_NM_NEW = "新規";
    /** 更新区分名称_継続*/
    private static final String UPD_KBN_NM_CONTINUATION = "継続";
    /** 更新区分名称_棚落*/
    private static final String UPD_KBN_NM_TANA = "棚落";
    
    /** Promotion Space*/
    private static final String JAN_CD_ALL_NINE = "9999999999999";
    
    /** 共通エラーレスポンスクラス */
    @Inject
    private ErrorResponse errRes;

    /** 共通システム・コンテキストクラス */
    @Inject
    private CCSystemContext context;

    /** ccCheckExistsUtil */
    @Inject
    private CCCheckExistsUtil ccCheckExistsUtil;
    
    /** MyBatisSqlMapDaoクラス */
    @Inject
    private MyBatisSqlMapDao dao;
    
    /** IPlu Converter */
    @Inject
    private IPluConverter plucnv;

    /** Data Util */
    @Inject
    private DataUtil dataUtil;
    
    /**
    * Get Report Data from DB
    * @param hatYmd 発注日
    * @return クライアントへ返却する結果
    */
    public Result query(String hatYmd) {

        Form<Gnjp7030CondForm> emptyForm = new Form<Gnjp7030CondForm>(Gnjp7030CondForm.class);
        Form<Gnjp7030CondForm> reqForm = emptyForm.bindFromRequest();

        if (reqForm.hasErrors()) {

            errRes.addErrorInfo(CCMessageConst.MSG_KEY_VALIDATION_ERROR);
            errRes.addErrorInfo(reqForm.errors());
            return badRequest(Json.toJson(errRes));

        } else {

            Gnjp7030CondForm req = reqForm.get();
            req.setHatYmd(hatYmd);
            boolean flag = doCheckData(req);

            // case: input data invalid
            if (!flag) {
                ErrorInfo err = errRes.getErrorInfo(CCMessageConst.MSG_KEY_VALIDATION_ERROR);
                errRes.getErrors().add(0, err);
                return badRequest(Json.toJson(errRes));
            }
            
            Gnjp7030Param param = new Gnjp7030Param();
            param.setDemoTanaCd(req.getDemoTanaCd());
            //Todo:
            param.setDemoTanaCd("1");
            List<Gnjp7030Result> resultList = dao.selectMany("selectGnjp7030", param, Gnjp7030Result.class);

            // case: not data find
            if (resultList.isEmpty()) {
                return notFound();
            }

            CCReportUtil cru = new CCReportUtil("GNSV7030", context);
            CsvManager csvManager = CsvManagerFactory.newCsvManager();
            List<Gnpr7030Report> csvBean = new ArrayList<Gnpr7030Report>();

            // 担当者コード
            String tantoCode = context.getTantoCode();
            // 会社
            String kaisyaCd = req.getKaisyaCd();
            // 事業部コード
            String jigyobuCd = req.getJigyobuCd();
            // 部門コード
            String bmnCd = req.getBmnCd();
            // 中分類コード
            String chuBnrCd = req.getChubnrCd();
            // 小分類コード
            String shoBnrCd = req.getShobnrCd();
            // 更新区分
            String updateKbn = req.getUpdateKbn();
            // TODO: 
            updateKbn = "1";
            // 原単価
            BigDecimal baik = BigDecimal.ZERO;
            // 売単価
            Integer genk = new Integer(0);
            // 指示数

            String demoSijiSu = null;
            String sBaikPriceUnit = null;
            String sGenkPriceUnit = null;
            String jigyobuName = dataUtil.getJigyobuName(kaisyaCd, jigyobuCd, hatYmd);
            String tenpoName = dataUtil.getTenpoName(kaisyaCd, jigyobuCd, req.getTenCd(), hatYmd);
            String bmnName = dataUtil.getBmnName(bmnCd, hatYmd);
            String chuBnrName = dataUtil.getChuBnrName(hatYmd, jigyobuCd, bmnCd, chuBnrCd);
            String shoBnrName = dataUtil.getShoBnrName(hatYmd, jigyobuCd, bmnCd, chuBnrCd, shoBnrCd);
            for (Gnjp7030Result resultData : resultList) {

                Gnpr7030Report row = new Gnpr7030Report();
                baik = resultData.getBaik();
                genk = resultData.getGenk();
                demoSijiSu = resultData.getDemoSijiSu();
                sBaikPriceUnit = "";
                sGenkPriceUnit = "";

                row.h00_progid = '"' + PROGRAM_ID + '"';
                row.h00_version = '"' + PROGRAM_VERSION + '"';
                row.h01_tanto_code = '"' + tantoCode + '"';
                row.h01_jigyobu_cd = '"' + jigyobuCd + '"';
                row.h01_jigyobu_nm = '"' + jigyobuName + '"';
                row.h01_ten_cd = '"' + req.getTenCd() + '"';
                row.h01_ten_nm = '"' + tenpoName + '"';
                row.h01_bmn_cd = '"' + bmnCd + '"';
                row.h01_bmn_nm = '"' + bmnName + '"';
                row.h01_chu_bnr_cd = '"' + chuBnrCd + '"';
                row.h01_chu_bnr_nm = '"' + chuBnrName + '"';
                row.h01_sho_bnr_cd = '"' + shoBnrCd + '"';
                row.h01_bmn_nm = '"' + shoBnrName + '"';
                row.m00_demo_tana_no = '"' + resultData.getDemoTanaNo() + '"';
                row.m00_demo_tana_dan = '"' + resultData.getDemoTanaDan() + '"';
                row.m00_demo_tana_seq = '"' + resultData.getDemoTanaSeq() + '"';
                row.m00_jan_cd = '"' + plucnv.toDispCode(resultData.getJanCd(), hatYmd) + '"';
                row.m00_shn_nm = '"' + resultData.getShnNm() + '"';
                row.m00_kikaku_nm = '"' + resultData.getKikakuNm() + '"';
                row.m00_tri_nm = '"' + resultData.getTriNm() + '"';
                row.m00_rrn_su = '"' + resultData.getRrnSu() + '"';
                row.m00_demo_siji_su = '"' + demoSijiSu.toString() + '"';
                row.m00_baik = '"' + baik.toPlainString() + '"';
                row.m00_genk = '"' + genk.toString() + '"';
                row.m00_demo_tana_no_prev = '"' + resultData.getDemoTanaNo() + '"';
                row.m00_demo_tana_dan_prev = '"' + resultData.getDemoTanaDan() + '"';
                row.m00_demo_tana_seq_prev = '"' + resultData.getDemoTanaSeq() + '"';

                if ("0".equals(updateKbn) || "1".equals(updateKbn)) {
                    // case quality more than zero
                    if (!CCStringUtil.isEmpty(demoSijiSu) && Integer.parseInt(demoSijiSu) > 0) {
                        sBaikPriceUnit =
                                resultData
                                        .getBaik()
                                        .divide(new BigDecimal(resultData.getDemoSijiSu()), KETA,
                                                BigDecimal.ROUND_HALF_UP).toPlainString();
                        sGenkPriceUnit =
                                new BigDecimal(genk).divide(new BigDecimal(demoSijiSu), KETA,
                                        BigDecimal.ROUND_HALF_UP).toPlainString();
                    }

                    row.m00_baik_price_unit = '"' + sBaikPriceUnit + '"';
                    row.m00_genk_price_unit = '"' + sGenkPriceUnit + '"';
                    row.m00_jan_cd_bcode = '"' + resultData.getJanCd() + '"';

                    if ("0".equals(updateKbn)) {
                        row.m00_upd_kbn_nm = '"' + UPD_KBN_NM_NEW + '"';
                        row.m00_demo_tana_no_prev = '"' + BLANK + '"';
                        row.m00_demo_tana_dan_prev = '"' + BLANK + '"';
                        row.m00_demo_tana_seq_prev = '"' + BLANK + '"';

                    } else {
                        row.m00_upd_kbn_nm = '"' + UPD_KBN_NM_CONTINUATION + '"';
                    }
                } else {

                    row.m00_demo_siji_su = '"' + BLANK + '"';
                    row.m00_baik = '"' + BLANK + '"';
                    row.m00_genk = '"' + BLANK + '"';
                    row.m00_baik_price_unit = '"' + BLANK + '"';
                    row.m00_genk_price_unit = '"' + BLANK + '"';
                    row.m00_jan_cd_bcode = '"' + BLANK + '"';

                    if ("2".equals(updateKbn)) {
                        row.m00_upd_kbn_nm = '"' + UPD_KBN_NM_TANA + '"';
                    } else {

                        row.m00_kikaku_nm = '"' + BLANK + '"';
                        row.m00_tri_nm = '"' + BLANK + '"';
                        row.m00_upd_kbn_nm = BLANK;
                        row.m00_jan_cd = '"' + JAN_CD_ALL_NINE + '"';
                        row.m00_shn_nm = '"' + SHN_NM_PROMO + '"';
                    }
                }

                csvBean.add(row);

            }

            // ＣＳＶ作成（データ出力）
            try {
                csvManager.save(csvBean, Gnpr7030Report.class).to(new File(cru.getCsvFilePath()),
                        CCReportUtil.CSV_OUTPUT_ENCODING);
            } catch (Exception e) {
                throw new ChaseException(e);
            }

            // create pdf
            cru.makePDF();

            List<Gnjp7030ResultForm> reportList = new ArrayList<Gnjp7030ResultForm>();
            Gnjp7030ResultForm resultForm = new Gnjp7030ResultForm();

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

    /**
     * 
     * @param req req
     * @return true or false
     */
    private boolean doCheckData(Gnjp7030CondForm req) {

        String kaisyaCd = req.getKaisyaCd();
        String hatYmd = req.getHatYmd();
        String bmnCd = req.getBmnCd();
        String chubnrCd = req.getChubnrCd();
        String shoBnrCd = req.getShobnrCd();
        String jigyobuCd = req.getJigyobuCd();
        String tenCd = req.getTenCd();
        // 発注日チェック
        if (!CCDateUtil.checkFormatDate("hatYmd", hatYmd, errRes)) {
            return false;
        }

        // 店舗チェック
        if (!CCStringUtil.isEmpty(tenCd)
                && !ccCheckExistsUtil.existsTenCd("tenCd", hatYmd, kaisyaCd + jigyobuCd + tenCd, errRes)) {
            return false;
        }

        // 事業部チェック
        if (!CCStringUtil.isEmpty(jigyobuCd)
                && !ccCheckExistsUtil.existsJigyobuCd("jigyobuCd", hatYmd, kaisyaCd, jigyobuCd, errRes)) {
            return false;
        }

        // 部門チェック
        if (!CCStringUtil.isEmpty(bmnCd)
                && !ccCheckExistsUtil.existsBmnCd("bmnCd", hatYmd, jigyobuCd + bmnCd, errRes)) {
            return false;
        }

        // 中分類チェック
        if (!CCStringUtil.isEmpty(chubnrCd)
                && !ccCheckExistsUtil.existChuBnrCd("chubnrCd", hatYmd, jigyobuCd, bmnCd, chubnrCd, errRes)) {
            return false;
        }

        // 小分類チェック
        if (!CCStringUtil.isEmpty(shoBnrCd)
                && !ccCheckExistsUtil
                        .existShoBnrCd("shobnrCd", hatYmd, jigyobuCd, bmnCd, chubnrCd, shoBnrCd, errRes)) {
            return false;
        }

        return true;
    }
    
}
