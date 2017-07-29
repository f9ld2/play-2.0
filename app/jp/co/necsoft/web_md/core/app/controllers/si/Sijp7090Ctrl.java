// /////////////////////////////////////////////////////////////////////
// Copyright(C) 2015 NEC Solution Innovators, Ltd. All Rights Reserved.
// /////////////////////////////////////////////////////////////////////

/*
 * ====================================================================
 * 
 * 機能名称 : オリジナル商品品振エラーリスト
 * 
 * 改版履歴
 * 
 * Rev. 改版年月日 改版者名 内容
 * 
 * 1.0 2015.06.10 NECVN 新規作成
 * 
 * ===================================================================
 */

package jp.co.necsoft.web_md.core.app.controllers.si;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import jp.co.necsoft.web_md.common.ChaseException;
import jp.co.necsoft.web_md.common.biz.IPluConverter;
import jp.co.necsoft.web_md.common.mybatis.dao.MyBatisSqlMapDao;
import jp.co.necsoft.web_md.core.app.dto.si.Sijp7090Dto;
import jp.co.necsoft.web_md.core.app.dto.si.Sijp7090Param;
import jp.co.necsoft.web_md.core.app.file.report.Sijp7090Report;
import jp.co.necsoft.web_md.core.app.forms.si.Sijp7090CondForm;
import jp.co.necsoft.web_md.core.app.forms.si.Sijp7090ResultForm;
import jp.co.necsoft.web_md.core.common.CCMessageConst;
import jp.co.necsoft.web_md.core.common.CCSecurityAuthenticator;
import jp.co.necsoft.web_md.core.common.CCSystemContext;
import jp.co.necsoft.web_md.core.common.forms.ErrorResponse;
import jp.co.necsoft.web_md.core.common.utils.CCCheckExistsUtil;
import jp.co.necsoft.web_md.core.common.utils.CCDateUtil;
import jp.co.necsoft.web_md.core.common.utils.CCReportUtil;
import jp.co.necsoft.web_md.core.common.utils.CCStringUtil;
import jp.co.necsoft.web_md.core.common.utils.DataUtil;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;

import play.data.Form;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;

import com.orangesignal.csv.manager.CsvManager;
import com.orangesignal.csv.manager.CsvManagerFactory;

/**
 * 仕入状況一覧表のControllerクラス
 *
 */
@Security.Authenticated(CCSecurityAuthenticator.class)
public class Sijp7090Ctrl extends Controller {
    /** hat dd pdf length */
    private static final int HAT_DD_PDF_LENGTH = 8;
    /** Program Id */
    private static final String PROGRAM_ID = "SIPR7090";
    /** Program version */
    private static final String PROGRAM_VERSION = "1.00";
    /** jigyobu code length */
    private static final int JIGYOBU_CD_LENGTH = 2;
    /** hinKkKbn length*/
    private static final int HINKKKBN_LENGTH = 1;
    /**  コード区分 */
    private static final String X007_KBN = "N0063";
    
    /** dao */
    @Inject
    private MyBatisSqlMapDao dao;
    /** Error Response */
    @Inject
    private ErrorResponse errRes;
    /** System Context */
    @Inject
    private CCSystemContext context;
    /** Data Util */
    @Inject
    private DataUtil dataUtil;
    /** Check exist utility */
    @Inject
    private CCCheckExistsUtil ccCheckExistsUtil;
    /** IPlu Converter */
    @Inject
    private IPluConverter plucnv;

    /**
     * ページを初期化する
     * 
     * @return クライアントへ返却する結果
     */
    public Result init() {
        Sijp7090CondForm form = new Sijp7090CondForm();
        String sUnyoDate = context.getUnyoDate();
        String kaisyaCd = context.getDefaultKaisyaCd();
        String jigyobuCd = dataUtil.getNewestJigyobuCd(kaisyaCd, sUnyoDate);
        // 画面項目[事業部]の設定
        form.setJigyobuCd(jigyobuCd);
        // 画面項目[店舗]の設定
        form.setTenCd(dataUtil.getNewestTenCd(kaisyaCd, jigyobuCd, sUnyoDate));
        
        List<Sijp7090CondForm> result = new ArrayList<Sijp7090CondForm>();
        result.add(form);
        return ok(Json.toJson(result));
    }
    
    /**
     * 引数とリクエスト(パラメータ)を条件にDBからデータ(複数件)を検索し返却する。
     * @param hatDayFr 発注日
     * @param hatDayTo 発注日終了
     * @param jigyobuCd 事業部コード
     * @param hinKkKbn 処理結果
     * @return クライアントへ返却する結果
     */
    public Result query(String hatDayFr, String hatDayTo, String jigyobuCd, String hinKkKbn) {
        Form<Sijp7090CondForm> emptyForm = new Form<Sijp7090CondForm>(Sijp7090CondForm.class);
        Form<Sijp7090CondForm> reqForm = emptyForm.bindFromRequest();
        if (reqForm.hasErrors()) {
            errRes.addErrorInfo(CCMessageConst.MSG_KEY_VALIDATION_ERROR);
            errRes.addErrorInfo(reqForm.errors());
            return badRequest(Json.toJson(errRes));
        } else {
            Sijp7090CondForm req = reqForm.get();
            req.setUnyoDate(context.getUnyoDate());
            req.setJigyobuCd(jigyobuCd);
            req.setHatDayFr(hatDayFr);
            req.setHatDayTo(hatDayTo);
            req.setHinKkKbn(hinKkKbn);
            
            // 入力データチェック
            if (!checkCond(req)) {
                return badRequest(Json.toJson(errRes));
            }
            
            // 担当者コードを取得します。
            String tantoCode = context.getTantoCode();
            
            Sijp7090Param sijp7090Param = new Sijp7090Param();
            BeanUtils.copyProperties(req, sijp7090Param);
            
            // ＣＳＶ・ＰＤＦのパスを取得
            List<Sijp7090Dto> sijp7090Dtos =
                    dao.selectMany("selectSijp7090ReportData", sijp7090Param, Sijp7090Dto.class);
            
            // データが存在しない場合
            if (sijp7090Dtos.size() == 0) {
                return notFound();
            }
            
            // ＣＳＶ作成（データ取得）
            CCReportUtil cru = new CCReportUtil("SISV7090", context);
            CsvManager csvManager = CsvManagerFactory.newCsvManager();
            List<Sijp7090Report> csvBean = new ArrayList<Sijp7090Report>();
            for (Sijp7090Dto sijp7090Dto : sijp7090Dtos) {
                Sijp7090Report sijp7090Report = new Sijp7090Report();
                sijp7090Report.h00_progid = PROGRAM_ID;
                sijp7090Report.h00_version = PROGRAM_VERSION;
                sijp7090Report.h01_tanto_code = tantoCode;
                
                sijp7090Report.h01_jigyobu_cd  = sijp7090Dto.getJigyobuCd();
                sijp7090Report.h01_jgy_nm      = sijp7090Dto.getJgyNm();
                sijp7090Report.h01_ten_cd      = sijp7090Dto.getTenCd();
                sijp7090Report.h01_ten_nm      = sijp7090Dto.getTenNm();
                sijp7090Report.h01_hat_dd_fr   = req.getHatDayFr();
                sijp7090Report.h01_hat_dd_to   = req.getHatDayTo();
                sijp7090Report.m01_hat_dd = StringUtils.substring(sijp7090Dto.getHatDd(), 0, HAT_DD_PDF_LENGTH);
                sijp7090Report.m01_jan_cd      = plucnv.toDispCode(sijp7090Dto.getJanCd(), context.getUnyoDate());
                sijp7090Report.m01_mkr_cd      = sijp7090Dto.getMkrCd();
                sijp7090Report.m01_shn_nm      = sijp7090Dto.getShnNm();
                sijp7090Report.m01_hat_su      = sijp7090Dto.getHatSu();
                sijp7090Report.m01_app_hin_su  = sijp7090Dto.getAppHinSu();
                sijp7090Report.m01_hin_kk_kbn  = sijp7090Dto.getHinKkKbn();
                sijp7090Report.m01_err_msg     = sijp7090Dto.getErrMsg();
                
                csvBean.add(sijp7090Report);
            }
            // ＣＳＶ作成（データ出力）
            try {
                csvManager.save(csvBean, Sijp7090Report.class).to(new File(cru.getCsvFilePath()),
                        CCReportUtil.CSV_OUTPUT_ENCODING);
            } catch (Exception e) {
                throw new ChaseException(e);
            }
            
            cru.makePDF();
            List<Sijp7090ResultForm> reportList = new ArrayList<Sijp7090ResultForm>();
            Sijp7090ResultForm resultForm = new Sijp7090ResultForm();
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
     * check validate 
     * @param req Request parameter
     * @return true : validate success; false : validate fail
     */
    private boolean checkCond(Sijp7090CondForm req) {
        String unyoDate = context.getUnyoDate();
        
        //発注日のチェック処理を行う。
        if (!CCDateUtil.checkFormatDate("hatDayFr", req.getHatDayFr(), errRes)) {
            return false;
        }
        
        if (!CCDateUtil.checkFormatDate("hatDayTo", req.getHatDayTo(), errRes)) {
            return false;
        }
        
        if (!CCDateUtil.checkDateFromTo("hatDayTo", req.getHatDayFr(), req.getHatDayTo(), errRes)) {
            return false;
        }
        
        //事業部コードチェック
        if (!CCStringUtil.checkLength("jigyobuCd", req.getJigyobuCd(), JIGYOBU_CD_LENGTH, errRes)) {
            return false;
        }
        
        if (!ccCheckExistsUtil.existsJigyobuCd("jigyobuCd", unyoDate, req.getKaisyaCd(),
                req.getJigyobuCd(), errRes)) {
            return false;
        }
        
        //店舗コードチェック
        if (!CCStringUtil.isEmpty(req.getTenCd()) && !ccCheckExistsUtil.existsTenCd("tenCd", unyoDate, req.getKaisyaCd()
                + req.getJigyobuCd()  + req.getTenCd(), errRes)) {
            return false;
        }
        
        //処理結果チェック
        if (!CCStringUtil.checkLength("hinKkKbn", req.getHinKkKbn(), HINKKKBN_LENGTH, errRes)) {
            return false;
        }
        
        if (!ccCheckExistsUtil.existsKbn("hinKkKbn", X007_KBN, req.getHinKkKbn(), errRes)) {
            return false;
        }
        
        return true;
    }
}
