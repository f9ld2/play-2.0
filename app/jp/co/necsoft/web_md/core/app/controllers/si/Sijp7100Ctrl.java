// /////////////////////////////////////////////////////////////////////
// Copyright(C) 2015 NEC Solution Innovators, Ltd. All Rights Reserved.
// /////////////////////////////////////////////////////////////////////
/*
 * ====================================================================
 * 
 * 機能名称 :委託精算確認
 * 
 * 改版履歴
 * 
 * Rev. 改版年月日 改版者名 内容
 * 
 * 1.0 2015-06-04 NECVN 新規作成
 * 
 * ===================================================================
 */

package jp.co.necsoft.web_md.core.app.controllers.si;

import java.io.File;
import java.math.BigDecimal;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import jp.co.necsoft.web_md.common.ChaseException;
import jp.co.necsoft.web_md.common.biz.IPluConverter;
import jp.co.necsoft.web_md.common.mybatis.dao.MyBatisSqlMapDao;
import jp.co.necsoft.web_md.core.app.dto.si.Sijp7100Dto;
import jp.co.necsoft.web_md.core.app.dto.si.Sijp7100Param;
import jp.co.necsoft.web_md.core.app.file.report.Sipr7100Report;
import jp.co.necsoft.web_md.core.app.forms.si.Sijp7100CondForm;
import jp.co.necsoft.web_md.core.app.forms.si.Sijp7100ResultForm1;
import jp.co.necsoft.web_md.core.app.forms.si.Sijp7100ResultForm2;
import jp.co.necsoft.web_md.core.common.CCMessageConst;
import jp.co.necsoft.web_md.core.common.CCSecurityAuthenticator;
import jp.co.necsoft.web_md.core.common.CCSystemContext;
import jp.co.necsoft.web_md.core.common.forms.ErrorResponse;
import jp.co.necsoft.web_md.core.common.utils.CCCheckExistsUtil;
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
*委託精算確認のControllerクラス
*
*/
@Security.Authenticated(CCSecurityAuthenticator.class)
public class Sijp7100Ctrl extends Controller {
    /**mybatis Dao*/
    @Inject
    private MyBatisSqlMapDao mybatisDao;
    
    /**Error Response*/
    @Inject
    private ErrorResponse errRes;
    
    /** ten code length */
    private static final int TEN_CD_LENGTH = 3;
    
    /** year length */
    private static final int YEAR_LENGTH = 4;
    
    /** month length */
    private static final int MONTH_LENGTH = 2;
    
    /** システムコンテキスト */
    @Inject
    private CCSystemContext context;
    
    /** Check Exists Util */
    @Inject
    private CCCheckExistsUtil ccCheckExistsUtil;
    
    /** Data Util */
    @Inject
    private DataUtil dataUtil;
    /** dao */
    @Inject
    private MyBatisSqlMapDao dao;
    /** IPlu Converter */
    @Inject
    private IPluConverter plucnv;
    
    /**
     * ページを初期化する
     * 
     * @return クライアントへ返却する結果
     */
    public Result init() {
        Sijp7100CondForm form = new Sijp7100CondForm();
        String sUnyoDate = context.getUnyoDate();
        String kaisyaCd = context.getDefaultKaisyaCd();
        String jiqyoubuCd = dataUtil.getNewestJigyobuCd(kaisyaCd, sUnyoDate);
        // 画面項目[事業部]の設定
        form.setJigyoubuCd(jiqyoubuCd);
        // 画面項目[店舗]の設定
        if (jiqyoubuCd != null && !jiqyoubuCd.isEmpty()) {
            form.setTenCd(dataUtil.getNewestTenCd(kaisyaCd, jiqyoubuCd, sUnyoDate));
        }
        // 対象月に運用日の年月を表示する
        form.setTaisyoY(sUnyoDate.substring(0, YEAR_LENGTH));
        form.setTaisyoM(sUnyoDate.substring(YEAR_LENGTH, YEAR_LENGTH + MONTH_LENGTH));
        
        List<Sijp7100CondForm> result = new ArrayList<Sijp7100CondForm>();
        result.add(form);
        return ok(Json.toJson(result));
    }
    
    /**
     *  引数とリクエスト(パラメータ)を条件にDBからデータ(複数件)を検索し返却する。
     * @param jigyobuCd 事業部コード
     * @param tenCd 店舗コード
     * @param taisyoY 対象年
     * @param taisyoM 対象月
     * @return クライアントへ返却する結果
     */
    public Result query(String jigyobuCd, String tenCd, String taisyoY, String taisyoM) {
        Form<Sijp7100CondForm> emptyForm = new Form<Sijp7100CondForm>(Sijp7100CondForm.class);
        Form<Sijp7100CondForm> reqForm = emptyForm.bindFromRequest();
        if (reqForm.hasErrors()) {
            errRes.addErrorInfo(CCMessageConst.MSG_KEY_VALIDATION_ERROR);
            errRes.addErrorInfo(reqForm.errors());
            return badRequest(Json.toJson(errRes));
        } else {
            Sijp7100CondForm req = reqForm.get();
            
            req.setJigyoubuCd(jigyobuCd);
            req.setTenCd(tenCd);
            req.setTaisyoY(taisyoY);
            req.setTaisyoM(taisyoM);
            
            if (req.getTriCd() == null) {
                req.setTriCd(StringUtils.EMPTY);
            }
            
            // 入力データチェック
            if (!checkCond(req)) {
                return badRequest(Json.toJson(errRes));
            }
            // 画面最大表示明細数の取得
            if (!CCCheckExistsUtil.existMaxRecordNumber(req.getMaxRecordNumber(), errRes)) {
                // Return error to client
                return badRequest(Json.toJson(errRes));
            }
            
            Integer maxRecordNumber = Integer.parseInt(req.getMaxRecordNumber());
            Integer selectRecordNumber = maxRecordNumber + 1;
            Sijp7100Param sijp7100Param = new Sijp7100Param();
            BeanUtils.copyProperties(req, sijp7100Param);
            sijp7100Param.setJigyobuCd(jigyobuCd);
            sijp7100Param.setUnyoDate(context.getUnyoDate());
            sijp7100Param.setMaxRecordNumber(selectRecordNumber.toString());
            
            // 表示区分="店舗別"の場合
            List<Sijp7100Dto> dtolist = mybatisDao.selectMany("selectSijp7100", sijp7100Param, Sijp7100Dto.class);
            
            // データが存在しない場合
            if (dtolist.isEmpty()) {
                return notFound();
            }
            
            // データが存在する場合
            // 画面最大表示明細数のチェックを行う。
            if (dtolist.size() > maxRecordNumber) {
                // 最大表示数を超えたので、条件を追加し、再実行してください。
                errRes.addErrorInfo(CCMessageConst.MSG_KEY_OVER_MAX_NUMBER_RECORDS);
                return badRequest(Json.toJson(errRes));
            }
            
            List<Sijp7100ResultForm1> resultList = new ArrayList<Sijp7100ResultForm1>();
            Sijp7100ResultForm1 result = new Sijp7100ResultForm1();
            BigDecimal total = BigDecimal.ZERO;
            for (Sijp7100Dto sijp7100Dto : dtolist) {
                total = total.add(sijp7100Dto.getSeisanKin());
                sijp7100Dto.setJanCd(plucnv.toDispCode(sijp7100Dto.getJanCd(), null));
            }
            result.setSum(total);
            result.setDtoList(dtolist);
            resultList.add(result);
            return ok(Json.toJson(resultList));
        }
        
    }
    
    /**
     *  Export PDF
     * @param jigyobuCd 事業部コード
     * @param tenCd 店舗コード
     * @param taisyoY 対象年
     * @param taisyoM 対象月
     * @return クライアントへ返却する結果
     */
    
    public Result exportPDF(String jigyobuCd, String tenCd, String taisyoY, String taisyoM) {
        Form<Sijp7100CondForm> emptyForm = new Form<Sijp7100CondForm>(Sijp7100CondForm.class);
        Form<Sijp7100CondForm> reqForm = emptyForm.bindFromRequest();
        if (reqForm.hasErrors()) {
            errRes.addErrorInfo(CCMessageConst.MSG_KEY_VALIDATION_ERROR);
            errRes.addErrorInfo(reqForm.errors());
            return badRequest(Json.toJson(errRes));
        } else {
            Sijp7100CondForm req = reqForm.get();
            
            req.setJigyoubuCd(jigyobuCd);
            req.setTenCd(tenCd);
            req.setTaisyoY(taisyoY);
            req.setTaisyoM(taisyoM);
            
            if (req.getTriCd() == null) {
                req.setTriCd(StringUtils.EMPTY);
            }
            
            // 入力データチェック
            if (!checkCond(req)) {
                return badRequest(Json.toJson(errRes));
            }
            // 担当者コードを取得します。
            String tantoCode = context.getTantoCode();
            
            Sijp7100Param sijp7100Param = new Sijp7100Param();
            BeanUtils.copyProperties(req, sijp7100Param);
            sijp7100Param.setUnyoDate(context.getUnyoDate());
            
            // ＣＳＶ・ＰＤＦのパスを取得
            List<Sijp7100Dto> sijp7100Dtos =
                    dao.selectMany("selectSijp7100ReportData", sijp7100Param, Sijp7100Dto.class);
            if (sijp7100Dtos.size() == 0) {
                return notFound();
            }
            
            // ＣＳＶ作成（データ取得）
            CCReportUtil cru = new CCReportUtil("SISV7100", context);
            CsvManager csvManager = CsvManagerFactory.newCsvManager();
            List<Sipr7100Report> csvBean = new ArrayList<Sipr7100Report>();
            for (Sijp7100Dto sijp7100Dto : sijp7100Dtos) {
                Sipr7100Report sipr7100Report = new Sipr7100Report();
                sipr7100Report.h00_progid = "SIPR7100";
                sipr7100Report.h00_version = "1.00";
                sipr7100Report.h01_tanto_code = tantoCode;
                
                sipr7100Report.h01_jgy_cd = sijp7100Dto.getJigyobuCd();
                sipr7100Report.h01_jgy_nm = sijp7100Dto.getJgyNm();
                sipr7100Report.h01_ten_cd = sijp7100Dto.getTenCd();
                sipr7100Report.h01_ten_nm = sijp7100Dto.getTenNm();
                sipr7100Report.h01_tri_cd = sijp7100Dto.getTriCd();
                sipr7100Report.h01_tri_nm = sijp7100Dto.getTriNm();
                sipr7100Report.h01_taisyo_ym = sijp7100Dto.getTaisyoYm();
                sipr7100Report.m01_jan_cd = plucnv.toDispCode(sijp7100Dto.getJanCd(), null);
                sipr7100Report.m01_shn_nm = sijp7100Dto.getShnNm();
                sipr7100Report.m01_genk = sijp7100Dto.getGenk().toPlainString();
                sipr7100Report.m01_baik = sijp7100Dto.getBaik().toString();
                sipr7100Report.m01_gesho_zaiko = sijp7100Dto.getGeshoZaiko().toPlainString();
                sipr7100Report.m01_sir_su = sijp7100Dto.getSirSu().toPlainString();
                sipr7100Report.m01_uri_tensu = sijp7100Dto.getUriTensu().toPlainString();
                sipr7100Report.m01_fumei_su = sijp7100Dto.getFumeiSu().toPlainString();
                sipr7100Report.m01_tna_su = sijp7100Dto.getTnaSu().toPlainString();
                sipr7100Report.m01_seisan_su = sijp7100Dto.getSeisanSu().toPlainString();
                sipr7100Report.m01_seisan_kin = sijp7100Dto.getSeisanKin().toPlainString();
                csvBean.add(sipr7100Report);
            }
            // ＣＳＶ作成（データ出力）
            try {
                csvManager.save(csvBean, Sipr7100Report.class).to(new File(cru.getCsvFilePath()),
                        CCReportUtil.CSV_OUTPUT_ENCODING);
            } catch (Exception e) {
                throw new ChaseException(e);
            }
            
            cru.makePDF();
            List<Sijp7100ResultForm2> reportList = new ArrayList<Sijp7100ResultForm2>();
            Sijp7100ResultForm2 resultForm = new Sijp7100ResultForm2();
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
     * Function check param input from form
     * 
     * @param req Request parameter
     * @return error or not
     */
    private boolean checkCond(Sijp7100CondForm req) {
        /** basic check */
        // 桁数チェック
        // 事業部コードチェック
        if (!CCStringUtil.checkLength("jigyobuCd", req.getJigyoubuCd(), 2, errRes)) {
            return false;
        }
        // 店舗コードチェック
        if (!CCStringUtil.checkLength("tenCd", req.getTenCd(), TEN_CD_LENGTH, errRes)) {
            return false;
        }
        
        /** logic check */
        // 事業部コードチェック
        // 存在チェック
        if (!ccCheckExistsUtil.existsJigyobuCd("jigyoubuCd", context.getUnyoDate(), req.getKaisyaCd(),
                req.getJigyoubuCd(), errRes)) {
            return false;
        }
        
        // 店舗コードチェック
        // 存在チェック
        if (!ccCheckExistsUtil.existsTenCd("tenCd", context.getUnyoDate(), req.getKaisyaCd() + req.getJigyoubuCd()
                + req.getTenCd(), errRes)) {
            return false;
        }
        
        // 取引先コードチェック
        if (!req.getTriCd().isEmpty()
                && !ccCheckExistsUtil.existTriCd("triCd", context.getUnyoDate(), req.getTriCd(), errRes)) {
            return false;
        }
        
        return true;
    }
}
