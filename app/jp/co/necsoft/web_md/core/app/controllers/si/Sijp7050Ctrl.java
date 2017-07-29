// /////////////////////////////////////////////////////////////////////
// Copyright(C) 2015 NEC Solution Innovators, Ltd. All Rights Reserved.
// /////////////////////////////////////////////////////////////////////

/*
 * ====================================================================
 * 
 * 機能名称 : HHT仕入返品取込エラーリスト * 
 * 改版履歴
 * 
 * Rev. 改版年月日 改版者名 内容
 * 
 * 1.0 2015.06.15 NECVN 新規作成
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
import jp.co.necsoft.web_md.core.app.dto.si.Sijp7050Dto;
import jp.co.necsoft.web_md.core.app.dto.si.Sijp7050Param;
import jp.co.necsoft.web_md.core.app.file.report.Sipr7050Report;
import jp.co.necsoft.web_md.core.app.forms.si.Sijp7050CondForm;
import jp.co.necsoft.web_md.core.app.forms.si.Sijp7050ResultForm;
import jp.co.necsoft.web_md.core.common.CCMessageConst;
import jp.co.necsoft.web_md.core.common.CCSecurityAuthenticator;
import jp.co.necsoft.web_md.core.common.CCSystemContext;
import jp.co.necsoft.web_md.core.common.forms.ErrorResponse;
import jp.co.necsoft.web_md.core.common.mybatis.dao.S701sihpMapper;
import jp.co.necsoft.web_md.core.common.mybatis.dto.S701sihp;
import jp.co.necsoft.web_md.core.common.mybatis.dto.S701sihpExample;
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
 * HHT仕入返品取込エラーリストのControllerクラス
 *
 */
@Security.Authenticated(CCSecurityAuthenticator.class)
public class Sijp7050Ctrl extends Controller {
    /** sosin nichiji pdf length */
    private static final int SOSIN_NICHIJI_PDF_LENGTH = 12;
    /** Program Id */
    private static final String PROGRAM_ID = "SIPR7050";
    /** Program version */
    private static final String PROGRAM_VERSION = "1.00";
    /** jigyobu code length */
    private static final int JIGYOBU_CD_LENGTH = 2;
    /** ten code length */
    private static final int TEN_CD_LENGTH = 3;
    /** Common mybatis dao */
    @Inject
    private MyBatisSqlMapDao dao;
    /** Error Response */
    @Inject
    private ErrorResponse errRes;
    /** System Context */
    @Inject
    private CCSystemContext context;
    /** Check exist utility */
    @Inject
    private CCCheckExistsUtil ccCheckExistsUtil;
    /** Data Util */
    @Inject
    private DataUtil dataUtil;
    /** S701sihp Mapper Table */
    @Inject
    private S701sihpMapper s701sihpMapper;
    /** IPlu Converter */
    @Inject
    private IPluConverter plucnv;
    
    /**
     * ページを初期化する
     * 
     * @return クライアントへ返却する結果
     */
    public Result init() {
        Sijp7050CondForm form = new Sijp7050CondForm();
        String sUnyoDate = context.getUnyoDate();
        String kaisyaCd = context.getDefaultKaisyaCd();
        String jigyobuCd = dataUtil.getNewestJigyobuCd(kaisyaCd, sUnyoDate);
        // 画面項目[事業部]の設定
        form.setJigyobuCd(jigyobuCd);
        // 画面項目[店舗]の設定
        form.setTenCd(dataUtil.getNewestTenCd(kaisyaCd, jigyobuCd, sUnyoDate));

        List<Sijp7050CondForm> result = new ArrayList<Sijp7050CondForm>();
        result.add(form);
        return ok(Json.toJson(result));
    }
    
    /**
     * 引数とリクエスト(パラメータ)を条件にDBからデータ(複数件)を検索し返却する。
     * @param jigyobuCd 事業部コード
     * @param tenCd 店舗コード
     * @param outTaisyo 対象
     * @return クライアントへ返却する結果
     */

    public Result query(String jigyobuCd, String tenCd, String outTaisyo) {
        Form<Sijp7050CondForm> emptyForm = new Form<Sijp7050CondForm>(Sijp7050CondForm.class);
        Form<Sijp7050CondForm> reqForm = emptyForm.bindFromRequest();
        if (reqForm.hasErrors()) {
            errRes.addErrorInfo(CCMessageConst.MSG_KEY_VALIDATION_ERROR);
            errRes.addErrorInfo(reqForm.errors());
            return badRequest(Json.toJson(errRes));
        } else {
            Sijp7050CondForm req = reqForm.get();
            req.setOutTaisyo(outTaisyo);
            req.setJigyobuCd(jigyobuCd);
            req.setTenCd(tenCd);
            
            // 担当者コードを取得します。
            String tantoCode = context.getTantoCode();
            
            // 入力データチェック
            if (!checkCond(req)) {
                return badRequest(Json.toJson(errRes));
            }
            
            Sijp7050Param sijp7050Param = new Sijp7050Param();
            BeanUtils.copyProperties(req, sijp7050Param);
            
            // ＣＳＶ・ＰＤＦのパスを取得
            List<Sijp7050Dto> sijp7050Dtos = dao.selectMany("selectSijp7050ReportData", 
                    sijp7050Param, Sijp7050Dto.class);
            
            if (sijp7050Dtos.size() == 0) {
                return notFound();
            } else {
                // ＣＳＶ作成（データ取得）
                CCReportUtil cru = new CCReportUtil("SISV7050", context);
                CsvManager csvManager = CsvManagerFactory.newCsvManager();
                List<Sipr7050Report> csvBean = new ArrayList<Sipr7050Report>();
                for (Sijp7050Dto sijp7050Dto : sijp7050Dtos) {
                    Sipr7050Report sipr7050Report = new Sipr7050Report();
                    sipr7050Report.h00_progid = PROGRAM_ID;
                    sipr7050Report.h00_version = PROGRAM_VERSION;
                    sipr7050Report.h01_tanto_code = tantoCode;
                    
                    sipr7050Report.h01_jigyobu_cd = sijp7050Dto.getJigyobuCd();
                    sipr7050Report.h01_jgy_nm = sijp7050Dto.getJgyNm();
                    sipr7050Report.h01_ten_cd = sijp7050Dto.getTenCd();
                    sipr7050Report.h01_ten_nm = sijp7050Dto.getTenNm();
                    sipr7050Report.h01_term_id = sijp7050Dto.getTermId();
                    sipr7050Report.h01_hen_date = sijp7050Dto.getHenDate();
                    sipr7050Report.h01_tanto_cd = sijp7050Dto.getTantoCd();
                    sipr7050Report.m01_shn_cd = plucnv.toDispCode(sijp7050Dto.getShnCd(), context.getUnyoDate());
                    sipr7050Report.m01_suryo = sijp7050Dto.getSuryo();
                    sipr7050Report.m01_sosin_nichiji =
                            StringUtils.substring(sijp7050Dto.getSosinNichiji(), 0, SOSIN_NICHIJI_PDF_LENGTH); 
                    sipr7050Report.m01_mkr_cd = sijp7050Dto.getMkrCd();
                    sipr7050Report.m01_shn_nm = sijp7050Dto.getShnNm();
                    sipr7050Report.m01_torihiki_cd = sijp7050Dto.getTorihikiCd();
                    sipr7050Report.m01_tri_nm = sijp7050Dto.getTriNm();
                    sipr7050Report.m01_msg = sijp7050Dto.getMsg();
                    
                    csvBean.add(sipr7050Report);
                }
                // ＣＳＶ作成（データ出力）
                try {
                    csvManager.save(csvBean, Sipr7050Report.class).to(new File(cru.getCsvFilePath()),
                            CCReportUtil.CSV_OUTPUT_ENCODING);
                } catch (Exception e) {
                    throw new ChaseException(e);
                }
                
                cru.makePDF();
                List<Sijp7050ResultForm> reportList = new ArrayList<Sijp7050ResultForm>();
                Sijp7050ResultForm resultForm = new Sijp7050ResultForm();
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
    }
    
    /**
     * check validate 
     * @param req Request parameter
     * @return true : validate success; false : validate fail
     */
    private boolean checkCond(Sijp7050CondForm req) {
        String unyoDate = context.getUnyoDate();
        
        // 事業部コードチェック
        if (!CCStringUtil.checkLength("jigyobuCd", req.getJigyobuCd(), JIGYOBU_CD_LENGTH, errRes)) {
            return false;
        }
        
        // 存在チェック
        if (!ccCheckExistsUtil.existsJigyobuCd("jigyobuCd", unyoDate, req.getKaisyaCd(),
                req.getJigyobuCd(), errRes)) {
            return false;
        }
        
        // 店舗コードチェック
        if (!CCStringUtil.checkLength("tenCd", req.getTenCd(), TEN_CD_LENGTH, errRes)) {
            return false;
        }
        
        // 存在チェック
        if (!ccCheckExistsUtil.existsTenCd("tenCd",  unyoDate, req.getKaisyaCd() 
                + req.getJigyobuCd()  + req.getTenCd(), errRes)) {
            return false;
        }
        
        // 作業（送信）日チェック
        if (!CCStringUtil.isEmpty(req.getSosinNichiji()) 
                && !CCDateUtil.checkFormatDate("sosinNichiji", req.getSosinNichiji(), errRes)) {
            return false;
        }
        
        //担当者コードチェック
        if (!CCStringUtil.isEmpty(req.getTantoCd())) {
            if (!ccCheckExistsUtil.existsTantoCd("tantoCd", req.getTantoCd(), unyoDate, errRes)) {
                return false;
            }
            
            // 桁数チェック s700knptMapper
            S701sihpExample example = new S701sihpExample();
            example.createCriteria().andTantoCdEqualTo(req.getTantoCd());
            
            List<S701sihp> list = this.s701sihpMapper.selectByExample(example);
            if (list.size() <= 0) {
                errRes.addErrorInfo(CCMessageConst.MSG_KEY_VALIDATION_ERROR);
                errRes.addErrorInfo("tantoCd", CCMessageConst.MSG_KEY_HENHINTORIKOMI_NOT_REGISTERD);
                return false;
            }
        }
        
        return true;
    }
}
