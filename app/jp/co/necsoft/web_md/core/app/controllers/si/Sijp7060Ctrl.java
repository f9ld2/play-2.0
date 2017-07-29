// /////////////////////////////////////////////////////////////////////
// Copyright(C) 2015 NEC Solution Innovators, Ltd. All Rights Reserved.
// /////////////////////////////////////////////////////////////////////

/*
 * ====================================================================
 * 
 * 機能名称 : HHT品振取込エラーリスト
 * 
 * 改版履歴
 * 
 * Rev. 改版年月日 改版者名 内容
 * 
 * 1.0 2015.06.05 NECVN 新規作成
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
import jp.co.necsoft.web_md.core.app.dto.si.Sijp7060Dto;
import jp.co.necsoft.web_md.core.app.dto.si.Sijp7060Param;
import jp.co.necsoft.web_md.core.app.file.report.Sipr7060Report;
import jp.co.necsoft.web_md.core.app.forms.si.Sijp7060CondForm;
import jp.co.necsoft.web_md.core.app.forms.si.Sijp7060ResultForm;
import jp.co.necsoft.web_md.core.common.CCMessageConst;
import jp.co.necsoft.web_md.core.common.CCSecurityAuthenticator;
import jp.co.necsoft.web_md.core.common.CCSystemContext;
import jp.co.necsoft.web_md.core.common.forms.ErrorResponse;
import jp.co.necsoft.web_md.core.common.mybatis.dao.S702hnbrMapper;
import jp.co.necsoft.web_md.core.common.mybatis.dto.S702hnbr;
import jp.co.necsoft.web_md.core.common.mybatis.dto.S702hnbrExample;
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
public class Sijp7060Ctrl extends Controller {
    /** sosin nichiji pdf length */
    private static final int SOSIN_NICHIJI_PDF_LENGTH = 12;
    /** Program Id */
    private static final String PROGRAM_ID = "SIPR7060";
    /** Program version */
    private static final String PROGRAM_VERSION = "1.00";
    /** outjigyobu code length */
    private static final int OUT_JIGYOBU_CD_LENGTH = 2;
    /** ten code length */
    private static final int TEN_CD_LENGTH = 3;
    
    /** dao */
    @Inject
    private MyBatisSqlMapDao dao;
    /** errRes */
    @Inject
    private ErrorResponse errRes;
    /** context */
    @Inject
    private CCSystemContext context;
    /** Check exist utility */
    @Inject
    private CCCheckExistsUtil ccCheckExistsUtil;
    /** S702hnbr Mapper Table */
    @Inject
    private S702hnbrMapper s702hnbrMapper;
    /** Data Util */
    @Inject
    private DataUtil dataUtil;
    /** IPlu Converter */
    @Inject
    private IPluConverter plucnv;
    
    /**
     * ページを初期化する
     * 
     * @return クライアントへ返却する結果
     */
    public Result init() {
        Sijp7060CondForm form = new Sijp7060CondForm();
        String sUnyoDate = context.getUnyoDate();
        String kaisyaCd = context.getDefaultKaisyaCd();
        String outJigyobuCd = dataUtil.getNewestJigyobuCd(kaisyaCd, sUnyoDate);
        // 画面項目[事業部]の設定
        form.setOutJigyobuCd(outJigyobuCd);
        // 画面項目[店舗]の設定
        form.setTenCd(dataUtil.getNewestTenCd(kaisyaCd, outJigyobuCd, sUnyoDate));
        
        List<Sijp7060CondForm> result = new ArrayList<Sijp7060CondForm>();
        result.add(form);
        return ok(Json.toJson(result));
    }
    
    /**
     * 引数とリクエスト(パラメータ)を条件にDBからデータ(複数件)を検索し返却する。
     * @param outJigyobuCd 事業部コード
     * @param tenCd 店舗コード
     * @param outTaisyo 対象
     * @return クライアントへ返却する結果
     */
    
    public Result query(String outJigyobuCd, String tenCd, String outTaisyo) {
        Form<Sijp7060CondForm> emptyForm = new Form<Sijp7060CondForm>(Sijp7060CondForm.class);
        Form<Sijp7060CondForm> reqForm = emptyForm.bindFromRequest();
        if (reqForm.hasErrors()) {
            errRes.addErrorInfo(CCMessageConst.MSG_KEY_VALIDATION_ERROR);
            errRes.addErrorInfo(reqForm.errors());
            return badRequest(Json.toJson(errRes));
        } else {
            Sijp7060CondForm req = reqForm.get();
            
            req.setOutJigyobuCd(outJigyobuCd);
            req.setOutTaisyo(outTaisyo);
            req.setTenCd(tenCd);
            
            if (req.getTantoCd() == null) {
                req.setTantoCd(StringUtils.EMPTY);
            }
            if (req.getSosinNichiji() == null) {
                req.setSosinNichiji(StringUtils.EMPTY);
            }
            
            // 担当者コードを取得します。
            String tantoCode = context.getTantoCode();
            
            // 入力データチェック
            if (!checkCond(req)) {
                return badRequest(Json.toJson(errRes));
            }
            
            Sijp7060Param sijp7060Param = new Sijp7060Param();
            BeanUtils.copyProperties(req, sijp7060Param);
            
            // ＣＳＶ・ＰＤＦのパスを取得
            List<Sijp7060Dto> sijp7060Dtos = dao.selectMany("selectSijp7060ReportData", 
                    sijp7060Param, Sijp7060Dto.class);
            
            // データが存在しない場合
            if (sijp7060Dtos.size() == 0) {
                return notFound();
            }
            
            // ＣＳＶ作成（データ取得）
            CCReportUtil cru = new CCReportUtil("SISV7060", context);
            CsvManager csvManager = CsvManagerFactory.newCsvManager();
            List<Sipr7060Report> csvBean = new ArrayList<Sipr7060Report>();
            String unyoDate = context.getUnyoDate();
            
            for (Sijp7060Dto sijp7060Dto : sijp7060Dtos) {
                Sipr7060Report sipr7060Report = new Sipr7060Report();
                sipr7060Report.h00_progid = PROGRAM_ID;
                sipr7060Report.h00_version = PROGRAM_VERSION;
                sipr7060Report.h01_tanto_code = tantoCode;
                
                sipr7060Report.h01_out_jigyobu_cd   = sijp7060Dto.getOutJigyobuCd();
                sipr7060Report.h01_out_jigyobu_nm   = sijp7060Dto.getOutJigyobuNm();
                sipr7060Report.h01_out_tenpo_cd     = sijp7060Dto.getOutTenpoCd();
                sipr7060Report.h01_out_tenpo_nm     = sijp7060Dto.getOutTenpoNm();
                sipr7060Report.h01_term_id          = sijp7060Dto.getTermId();
                sipr7060Report.h01_tanto_cd         = sijp7060Dto.getTantoCd();
                sipr7060Report.m01_shn_cd           = plucnv.toDispCode(sijp7060Dto.getShnCd(), unyoDate);
                sipr7060Report.m01_in_tenpo_cd      = sijp7060Dto.getInTenpoCd();
                sipr7060Report.m01_in_tenpo_nm      = sijp7060Dto.getInTenpoNm();
                sipr7060Report.m01_hin_su           = sijp7060Dto.getHinSu();
                sipr7060Report.m01_sosin_nichiji =
                        StringUtils.substring(sijp7060Dto.getSosinNichiji(), 0, SOSIN_NICHIJI_PDF_LENGTH);
                
                sipr7060Report.m01_maker_hin_cd     = sijp7060Dto.getMakerHinCd();
                sipr7060Report.m01_shn_nm           = sijp7060Dto.getShnNm();
                sipr7060Report.m01_msg              = sijp7060Dto.getMsg();
                
                csvBean.add(sipr7060Report);
            }
            // ＣＳＶ作成（データ出力）
            try {
                csvManager.save(csvBean, Sipr7060Report.class).to(new File(cru.getCsvFilePath()),
                        CCReportUtil.CSV_OUTPUT_ENCODING);
            } catch (Exception e) {
                throw new ChaseException(e);
            }
            
            cru.makePDF();
            List<Sijp7060ResultForm> reportList = new ArrayList<Sijp7060ResultForm>();
            Sijp7060ResultForm resultForm = new Sijp7060ResultForm();
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
    private boolean checkCond(Sijp7060CondForm req) {
        String unyoDate = context.getUnyoDate();
        
        // 事業部コードチェック
        if (!CCStringUtil.checkLength("outJigyobuCd", req.getOutJigyobuCd(), OUT_JIGYOBU_CD_LENGTH, errRes)) {
            return false;
        }
        
        // 存在チェック
        if (!ccCheckExistsUtil.existsJigyobuCd("outJigyobuCd", unyoDate, req.getKaisyaCd(),
                req.getOutJigyobuCd(), errRes)) {
            return false;
        }
        
        // 店舗コードチェック
        if (!CCStringUtil.checkLength("tenCd", req.getTenCd(), TEN_CD_LENGTH, errRes)) {
            return false;
        }
        
        // 存在チェック
        if (!ccCheckExistsUtil.existsTenCd("tenCd", unyoDate,
                req.getKaisyaCd() + req.getOutJigyobuCd() + req.getTenCd(), errRes)) {
            return false;
        }
        
        // 作業（送信）日のチェック処理を行う。
        if (!CCStringUtil.isEmpty(req.getSosinNichiji()) 
                && !CCDateUtil.checkFormatDate("sosinNichiji", req.getSosinNichiji(), errRes)) {
            return false;
        }
        
        // 担当者コードチェック
        if (!CCStringUtil.isEmpty(req.getTantoCd())) {
            if (!ccCheckExistsUtil.existsTantoCd("tantoCd", req.getTantoCd(), unyoDate, errRes)) {
                return false;
            }
            
            // 桁数チェック s702hnbrMapper
            S702hnbrExample example = new S702hnbrExample();
            example.createCriteria().andTantoCdEqualTo(req.getTantoCd());
            
            List<S702hnbr> list = this.s702hnbrMapper.selectByExample(example);
            if (list.size() <= 0) {
                errRes.addErrorInfo(CCMessageConst.MSG_KEY_VALIDATION_ERROR);
                errRes.addErrorInfo("tantoCd", CCMessageConst.MSG_KEY_S702HNBR_TANTOCD_NOT_REGISTERED);
                return false;
            }
        }
        
        return true;
    }
}
