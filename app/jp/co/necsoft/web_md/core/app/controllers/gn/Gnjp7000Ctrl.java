// /////////////////////////////////////////////////////////////////////
// Copyright(C) 2015 NEC Solution Innovators, Ltd. All Rights Reserved.
// /////////////////////////////////////////////////////////////////////

/*
 * ====================================================================
 * 
 * 機能名称 : セット品設定リスト
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

import org.apache.commons.lang3.StringUtils;

import jp.co.necsoft.web_md.common.ChaseException;
import jp.co.necsoft.web_md.common.biz.IPluConverter;
import jp.co.necsoft.web_md.common.mybatis.dao.MyBatisSqlMapDao;
import jp.co.necsoft.web_md.core.app.dto.gn.Gnjp7000Param;
import jp.co.necsoft.web_md.core.app.dto.gn.Gnjp7000Result;
import jp.co.necsoft.web_md.core.app.file.report.Gnpr7000Report;
import jp.co.necsoft.web_md.core.app.forms.gn.Gnjp7000CondForm;
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
 * セット品設定リストのControllerクラス
 * 
 */
@Security.Authenticated(CCSecurityAuthenticator.class)
public class Gnjp7000Ctrl extends Controller {    

    /** プログラムＩＤ */
    private static final String PROGRAM_ID = "GNPR7000";

    /** バージョン */
    private static final String PROGRAM_VERSION = "1.0";
    
    /** tilde symbol*/
    private static final String TILDE_SYMBOL = "～";
    
    /**KETA*/
    private static final int KETA = 1;
    
    /**Zero percent */
    private static final String ZERO_PERCENT = "0.0";
    
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
     * ページを初期化する
     * 
     * @return クライアントへ返却する結果
     */
    public Result init() {
        
        Gnjp7000CondForm form = new Gnjp7000CondForm();
        // 発効日
        String sUnyoDate = context.getUnyoDate();
        String kaisyaCd = context.getDefaultKaisyaCd();
        String bmnCd = StringUtils.EMPTY;
        String chuBnrCd = StringUtils.EMPTY;
        String shoBnrCd = StringUtils.EMPTY;
        // 取得するのは直近の1レコードのみ
        String jigyobuCd = dataUtil.getNewestJigyobuCd(kaisyaCd, sUnyoDate);
        // 事業部コード が保存する場合
        if (!CCStringUtil.isEmpty(jigyobuCd)) {
            bmnCd = dataUtil.getNewestBmnCd(jigyobuCd, sUnyoDate);
        }
        // 部門コード が保存する場合
        if (!CCStringUtil.isEmpty(bmnCd)) {
            chuBnrCd = dataUtil.getNewestChuBnrCd(sUnyoDate, jigyobuCd, bmnCd);
            shoBnrCd = dataUtil.getNewestShoBnrCd(sUnyoDate, jigyobuCd, bmnCd, chuBnrCd);
        }
        
        // 画面項目[事業部]の設定
        form.setJigyobuCd(jigyobuCd);
        // 画面項目[部門]の設定
        form.setBmnCd(bmnCd);
        // 画面項目[中分類]の設定
        form.setChubnrCd(chuBnrCd);
        // 画面項目[小分類]の設定
        form.setShobnrCd(shoBnrCd);
        
        List<Gnjp7000CondForm> result = new ArrayList<Gnjp7000CondForm>();
        result.add(form);
        return ok(Json.toJson(result));
    }
    
    /**
    * Get Report Data from DB
    * @param hatYmd 発注日
    * @return クライアントへ返却する結果
    */
    public Result query(String hatYmd, String jigyobuCd) {

        Form<Gnjp7000CondForm> emptyForm = new Form<Gnjp7000CondForm>(Gnjp7000CondForm.class);
        Form<Gnjp7000CondForm> reqForm = emptyForm.bindFromRequest();

        if (reqForm.hasErrors()) {

            errRes.addErrorInfo(CCMessageConst.MSG_KEY_VALIDATION_ERROR);
            errRes.addErrorInfo(reqForm.errors());
            return badRequest(Json.toJson(errRes));

        } else {

            Gnjp7000CondForm req = reqForm.get();
            req.setHatYmd(hatYmd);
            req.setJigyobuCd(jigyobuCd);
            boolean flag = doCheckData(req);

            // case of input data invalid
            if (!flag) {
                ErrorInfo err = errRes.getErrorInfo(CCMessageConst.MSG_KEY_VALIDATION_ERROR);
                errRes.getErrors().add(0, err);
                return badRequest(Json.toJson(errRes));
            }

            // セット品設定リストのParamクラス
            Gnjp7000Param param = new Gnjp7000Param();
            param.setBmnCd(req.getJigyobuCd() + req.getBmnCd());
            param.setChubnrCd(req.getChubnrCd());
            param.setShobnrCd(req.getShobnrCd());
            param.setJanCd(plucnv.toDbCode(req.getJanCd()));
            param.setUnyoDate(context.getUnyoDate());
            param.setHatYmd(req.getHatYmd());
            param.setJigyobuCd(req.getJigyobuCd());
            
            // get data for report
            List<Gnjp7000Result> resultList = dao.selectMany("selectGnjp7000", param, Gnjp7000Result.class);
            // データが存在しない場合
            if (resultList.size() == 0) {
                return notFound();
            }

            CCReportUtil cru = new CCReportUtil("GNSV7000", context);
            CsvManager csvManager = CsvManagerFactory.newCsvManager();
            List<Gnpr7000Report> csvBean = new ArrayList<Gnpr7000Report>();
            String tantoCode = context.getTantoCode();

            for (Gnjp7000Result resultData : resultList) {
                
                Gnpr7000Report row = new Gnpr7000Report();
                row.h00_progid = '"' + PROGRAM_ID + '"';
                row.h00_version = '"' + PROGRAM_VERSION + '"';
                row.h01_tanto_code = tantoCode;
                
                // 発効日(FROM)
                row.m00_hakko_day = '"' + resultData.getHakkoDayHeader() + '"';
                // ＪＡＮコード
                row.m00_jan_cd = '"' + resultData.getJanCdHeader() + '"';
                // 商品名漢字
                row.m00_shn_nm = '"' + resultData.getShnNmHeader() + '"';
                // 基準原価
                row.m00_std_genk = '"' + resultData.getStdGenkDetail() + '"';
                // 基準売価
                row.m00_std_baik = '"' + resultData.getStdBaikHeader() + '"';
                // 基準原価/基準売価*100(最後に%を付ける)
                row.m00_genk_rate = getPercent(resultData.getStdGenkDetail(), resultData.getStdBaikHeader());
                // 発効日(FROM) 発効日(TO)
                row.m01_hakko_day = '"' + resultData.getHakkoDayDetail() + TILDE_SYMBOL + resultData.getHakkoDayToDetail() + '"';
                // ＪＡＮコード
                row.m01_jan_cd = '"' + resultData.getJanCdDetail() + '"';
                // 商品名漢字
                row.m01_shn_nm = '"' + resultData.getShnNmDetail() + '"';
                
                row.m01_suryo = '"' + resultData.getSuryoDetail() + '"';
                // 基準原価
                row.m01_std_genk = '"' + resultData.getStdGenkDetail() + '"';
                // 基準売価
                row.m01_std_baik = '"' + resultData.getStdBaikDetail() + '"';
                // 取引先名漢字
                row.m01_model_tri_nm = '"' + resultData.getTriNmDetail() + '"';
                // メーカ品番
                row.m01_maker_hin_cd = '"' + resultData.getMakerHinCdDetail() + '"';
                
                csvBean.add(row);

            }

            // ＣＳＶ作成（データ出力）
            try {
                // UTF8EncodingでCSVファイル作成を作成する
                csvManager.save(csvBean, Gnpr7000Report.class).to(new File(cru.getCsvFilePath()),
                        CCReportUtil.CSV_OUTPUT_ENCODING);
            } catch (Exception e) {
                throw new ChaseException(e);
            }

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
    private boolean doCheckData(Gnjp7000CondForm req) {

        String kaisyaCd = req.getKaisyaCd();
        String hatYmd = req.getHatYmd();
        String bmnCd = req.getBmnCd();
        String chubnrCd = req.getChubnrCd();
        String shoBnrCd = req.getShobnrCd();
        String jigyobuCd = req.getJigyobuCd();
        String tenCd = req.getTenCd();
        String janCd = req.getJanCd();
        // 事業部チェック
        if (!CCStringUtil.isEmpty(jigyobuCd)
                && !ccCheckExistsUtil.existsJigyobuCd("jigyobuCd", hatYmd, kaisyaCd, jigyobuCd, errRes)) {
            return false;
        }

        // 店舗チェック
        if (!CCStringUtil.isEmpty(tenCd)
                && !ccCheckExistsUtil.existsTenCd("tenCd", hatYmd, kaisyaCd + jigyobuCd + tenCd, errRes)) {
            return false;
        }

        // 発注日チェック
        if (!CCDateUtil.checkFormatDate("hatYmd", hatYmd, errRes)) {
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

        // janCd
        if (!CCStringUtil.isEmpty(janCd)
                && !ccCheckExistsUtil.existsJanCd("janCd", hatYmd, plucnv.toDbCode(janCd), errRes)) {
            return false;
        }

        return true;
    }
    
    /**
     * get percent rate
     * @param param1 divided
     * @param param2 divisor
     * @return
     */
    private String getPercent(String param1, String param2) {
        if (CCStringUtil.isEmpty(param1) || CCStringUtil.isEmpty(param2) || (Integer.parseInt(param2) == 0)) {
            return ZERO_PERCENT;
        }
        return new BigDecimal(param1).divide(new BigDecimal(param2), KETA, BigDecimal.ROUND_HALF_UP)
                .multiply(new BigDecimal(100)).toPlainString();

    }
}
