// /////////////////////////////////////////////////////////////////////
// Copyright(C) 2015 NEC Solution Innovators, Ltd. All Rights Reserved.
// /////////////////////////////////////////////////////////////////////

/*
 * ====================================================================
 * 
 * 機能名称 : HHT検品取込エラーリスト * 
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
import jp.co.necsoft.web_md.core.app.dto.si.Sijp7040Dto;
import jp.co.necsoft.web_md.core.app.dto.si.Sijp7040Param;
import jp.co.necsoft.web_md.core.app.file.report.Sipr7040Report;
import jp.co.necsoft.web_md.core.app.forms.si.Sijp7040CondForm;
import jp.co.necsoft.web_md.core.app.forms.si.Sijp7040ResultForm;
import jp.co.necsoft.web_md.core.common.CCMessageConst;
import jp.co.necsoft.web_md.core.common.CCSecurityAuthenticator;
import jp.co.necsoft.web_md.core.common.mybatis.dao.S700knptMapper;
import jp.co.necsoft.web_md.core.common.mybatis.dto.S700knpt;
import jp.co.necsoft.web_md.core.common.mybatis.dto.S700knptExample;
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
 * HHT検品取込エラーリストのControllerクラス
 *
 */
@Security.Authenticated(CCSecurityAuthenticator.class)
public class Sijp7040Ctrl extends Controller {
    /** sosin nichiji pdf length */
    private static final int SOSIN_NICHIJI_PDF_LENGTH = 12;
    /** Program Id */
    private static final String PROGRAM_ID = "SIPR7040";
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
    /** S700knpt Mapper Table */
    @Inject
    private S700knptMapper s700knptMapper;
    /** IPlu Converter */
    @Inject
    private IPluConverter plucnv;
    
    /**
     * ページを初期化する
     * 
     * @return クライアントへ返却する結果
     */
    public Result init() {
        Sijp7040CondForm form = new Sijp7040CondForm();
        String sUnyoDate = context.getUnyoDate();
        String kaisyaCd = context.getDefaultKaisyaCd();
        String jigyobuCd = dataUtil.getNewestJigyobuCd(kaisyaCd, sUnyoDate);
        // 画面項目[事業部]の設定
        form.setJigyobuCd(jigyobuCd);
        // 画面項目[店舗]の設定
        form.setTenCd(dataUtil.getNewestTenCd(kaisyaCd, jigyobuCd, sUnyoDate));

        List<Sijp7040CondForm> result = new ArrayList<Sijp7040CondForm>();
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
        Form<Sijp7040CondForm> emptyForm = new Form<Sijp7040CondForm>(Sijp7040CondForm.class);
        Form<Sijp7040CondForm> reqForm = emptyForm.bindFromRequest();
        if (reqForm.hasErrors()) {
            errRes.addErrorInfo(CCMessageConst.MSG_KEY_VALIDATION_ERROR);
            errRes.addErrorInfo(reqForm.errors());
            return badRequest(Json.toJson(errRes));
        } else {
            Sijp7040CondForm req = reqForm.get();
            req.setJigyobuCd(jigyobuCd);
            req.setOutTaisyo(outTaisyo);
            req.setTenCd(tenCd);
            
            if (req.getSosinNichiji() == null) {
                req.setSosinNichiji(StringUtils.EMPTY);
            }
            
            if (req.getTantoCd() == null) {
                req.setTantoCd(StringUtils.EMPTY);
            }
            
            // 担当者コードを取得します。
            String tantoCode = context.getTantoCode();
            
            // 入力データチェック
            if (!checkCond(req)) {
                return badRequest(Json.toJson(errRes));
            }
            
            Sijp7040Param sijp7040Param = new Sijp7040Param();
            BeanUtils.copyProperties(req, sijp7040Param);
            
            // ＣＳＶ・ＰＤＦのパスを取得
            List<Sijp7040Dto> sijp7040Dtos = dao.selectMany("selectSijp7040ReportData", 
                    sijp7040Param, Sijp7040Dto.class);
            
            // データが存在しない場合
            if (sijp7040Dtos.size() == 0) {
                return notFound();
            }
            
            // ＣＳＶ作成（データ取得）
            CCReportUtil cru = new CCReportUtil("SISV7040", context);
            CsvManager csvManager = CsvManagerFactory.newCsvManager();
            List<Sipr7040Report> csvBean = new ArrayList<Sipr7040Report>();
            for (Sijp7040Dto sijp7040Dto : sijp7040Dtos) {
                Sipr7040Report sipr7040Report = new Sipr7040Report();
                sipr7040Report.h00_progid = PROGRAM_ID;
                sipr7040Report.h00_version = PROGRAM_VERSION;
                sipr7040Report.h01_tanto_code = tantoCode;
                
                sipr7040Report.h01_jigyobu_cd = sijp7040Dto.getJigyobuCd();
                sipr7040Report.h01_jgy_nm = sijp7040Dto.getJgyNm();
                sipr7040Report.h01_ten_cd = sijp7040Dto.getTenCd();
                sipr7040Report.h01_ten_nm = sijp7040Dto.getTenNm();
                sipr7040Report.h01_term_id = sijp7040Dto.getTermId();
                sipr7040Report.h01_nhn_ymd = sijp7040Dto.getNhnYmd();
                sipr7040Report.h01_tanto_cd = sijp7040Dto.getTantoCd();
                sipr7040Report.m01_dpy_no = sijp7040Dto.getDpyNo();
                sipr7040Report.m01_shn_cd = plucnv.toDispCode(sijp7040Dto.getShnCd(), context.getUnyoDate());
                sipr7040Report.m01_suryo = sijp7040Dto.getSuryo();
                sipr7040Report.m01_sosin_nichiji =
                        StringUtils.substring(sijp7040Dto.getSosinNichiji(), 0, SOSIN_NICHIJI_PDF_LENGTH); 
                sipr7040Report.m01_mkr_cd = sijp7040Dto.getMkrCd();
                sipr7040Report.m01_shn_nm = sijp7040Dto.getShnNm();
                sipr7040Report.m01_torihiki_cd = sijp7040Dto.getTorihikiCd();
                sipr7040Report.m01_tri_nm = sijp7040Dto.getTriNm();
                sipr7040Report.m01_hat_case_su_01 = sijp7040Dto.getHatCaseSu01();
                sipr7040Report.m01_msg = sijp7040Dto.getMsg();
                
                csvBean.add(sipr7040Report);
            }
            // ＣＳＶ作成（データ出力）
            try {
                csvManager.save(csvBean, Sipr7040Report.class).to(new File(cru.getCsvFilePath()),
                        CCReportUtil.CSV_OUTPUT_ENCODING);
            } catch (Exception e) {
                throw new ChaseException(e);
            }
            
            cru.makePDF();
            List<Sijp7040ResultForm> reportList = new ArrayList<Sijp7040ResultForm>();
            Sijp7040ResultForm resultForm = new Sijp7040ResultForm();
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
    private boolean checkCond(Sijp7040CondForm req) {
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
        if (!ccCheckExistsUtil.existsTenCd("tenCd", unyoDate, req.getKaisyaCd() 
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
            S700knptExample example = new S700knptExample();
            example.createCriteria().andTantoCdEqualTo(req.getTantoCd());

            List<S700knpt> list = this.s700knptMapper.selectByExample(example);
            if (list.size() <= 0) {
                errRes.addErrorInfo(CCMessageConst.MSG_KEY_VALIDATION_ERROR);
                errRes.addErrorInfo("tantoCd", CCMessageConst.MSG_KEY_HTT_KENPIN_TORIKOMI_NOT_REGISTERD);
                return false;
            }
        }
        
        return true;
    }
}
