// /////////////////////////////////////////////////////////////////////
// Copyright(C) 2014 NEC Solution Innovators, Ltd. All Rights Reserved.
// /////////////////////////////////////////////////////////////////////

/*
 * ====================================================================
 * 
 * 機能名称 : 棚卸プルーフ
 * 
 * 改版履歴
 * 
 * Rev. 改版年月日 改版者名 内容
 * 
 * 1.0 2015/05/07 NECVN 新規作成
 * 
 * ===================================================================
 */
package jp.co.necsoft.web_md.core.app.controllers.zi;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.springframework.beans.BeanUtils;

import jp.co.necsoft.web_md.common.ChaseException;
import jp.co.necsoft.web_md.common.biz.IPluConverter;
import jp.co.necsoft.web_md.common.mybatis.dao.MyBatisSqlMapDao;
import jp.co.necsoft.web_md.core.app.dto.zi.Zijp7000Dto;
import jp.co.necsoft.web_md.core.app.dto.zi.Zijp7000Param;
import jp.co.necsoft.web_md.core.app.file.report.Zipr7000Report;
import jp.co.necsoft.web_md.core.app.forms.zi.Zijp7000CondForm;
import jp.co.necsoft.web_md.core.app.forms.zi.Zijp7000ResultForm;
import jp.co.necsoft.web_md.core.common.CCMessageConst;
import jp.co.necsoft.web_md.core.common.CCSecurityAuthenticator;
import jp.co.necsoft.web_md.core.common.CCSystemContext;
import jp.co.necsoft.web_md.core.common.forms.ErrorResponse;
import jp.co.necsoft.web_md.core.common.utils.CCCheckExistsUtil;
import jp.co.necsoft.web_md.core.common.utils.CCDateUtil;
import jp.co.necsoft.web_md.core.common.utils.CCNumberUtil;
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
 * 棚卸実施チェックリストのControllerクラス
 *
 */
@Security.Authenticated(CCSecurityAuthenticator.class)
public class Zijp7000Ctrl extends Controller {
    /** Program Id */
    private static final String PROGRAM_ID = "ZIPR7000";
    /** Program version */
    private static final String PROGRAM_VERSION = "1.00";
    /** jigyobu code length */
    private static final int JIGYOBU_CD_LENGTH = 2;
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
        Zijp7000CondForm form = new Zijp7000CondForm();
        String sUnyoDate = context.getUnyoDate();
        String kaisyaCd = context.getDefaultKaisyaCd();
        String jigyobuCd = dataUtil.getNewestJigyobuCd(kaisyaCd, sUnyoDate);
        // 画面項目[事業部]の設定
        form.setJigyobuCd(jigyobuCd);
        // 画面項目[店舗]の設定
        form.setTenCd(dataUtil.getNewestTenCd(kaisyaCd, jigyobuCd, sUnyoDate));
        // 画面項目[店舗]の設定
        form.setBmnCd(dataUtil.getNewestBmnCd(jigyobuCd, sUnyoDate));

        List<Zijp7000CondForm> result = new ArrayList<Zijp7000CondForm>();
        result.add(form);
        return ok(Json.toJson(result));
    }
    
    /**
     * 引数とリクエスト(パラメータ)を条件にDBからデータ(複数件)を検索し返却する。
     * @param tnaUnyDd 棚卸日
     * @param jigyobuCd 事業部コード
     * @return クライアントへ返却する結果
     */
    public Result query(String tnaUnyDd, String jigyobuCd) {
        Form<Zijp7000CondForm> emptyForm = new Form<Zijp7000CondForm>(Zijp7000CondForm.class);
        Form<Zijp7000CondForm> reqForm = emptyForm.bindFromRequest();
        if (reqForm.hasErrors()) {
            errRes.addErrorInfo(CCMessageConst.MSG_KEY_VALIDATION_ERROR);
            errRes.addErrorInfo(reqForm.errors());
            return badRequest(Json.toJson(errRes));
        } else {
            Zijp7000CondForm req = reqForm.get();
            
            req.setTnaUnyDd(tnaUnyDd);
            req.setJigyobuCd(jigyobuCd);
            req.setUnyoDate(context.getUnyoDate());
            
            Zijp7000Param zijp7000Param = new Zijp7000Param();
            BeanUtils.copyProperties(req, zijp7000Param);
            
            // 入力データチェック
            if (!checkCond(req, zijp7000Param)) {
                return badRequest(Json.toJson(errRes));
            }
            
            // 担当者コードを取得します。
            String tantoCode = context.getTantoCode();
            
            // ＣＳＶ・ＰＤＦのパスを取得
            List<Zijp7000Dto> zijp7000Dtos = dao.selectMany("selectZijp7000ReportData", 
                    zijp7000Param, Zijp7000Dto.class);
            
            if (zijp7000Dtos.size() == 0) {
                return notFound();
            }
            
            // ＣＳＶ作成（データ取得）
            CCReportUtil cru = new CCReportUtil("SISV7000", context);
            CsvManager csvManager = CsvManagerFactory.newCsvManager();
            List<Zipr7000Report> csvBean = new ArrayList<Zipr7000Report>();
            for (Zijp7000Dto zijp7000Dto : zijp7000Dtos) {
                Zipr7000Report zipr7000Report = new Zipr7000Report();
                zipr7000Report.h00_progid = PROGRAM_ID;
                zipr7000Report.h00_version = PROGRAM_VERSION;
                zipr7000Report.h01_tanto_code = tantoCode;
                
                zipr7000Report.h01_jigyobu_cd    = zijp7000Dto.getJigyobuCd();
                zipr7000Report.h01_jgy_nm        = zijp7000Dto.getJgyNm();
                zipr7000Report.h01_ten_cd        = zijp7000Dto.getTenCd();
                zipr7000Report.h01_ten_nm        = zijp7000Dto.getTenNm();
                zipr7000Report.h01_tna_uny_dd    = zijp7000Dto.getTnaUnyDd();
                zipr7000Report.h01_tana_no       = zijp7000Dto.getTanaNo();
                zipr7000Report.h01_tanto_nm      = zijp7000Dto.getTantoNm();
                zipr7000Report.m01_shn_cd        = plucnv.toDispCode(zijp7000Dto.getShnCd(), context.getUnyoDate());
                zipr7000Report.m01_shn_nm        = zijp7000Dto.getShnNm();
                zipr7000Report.m01_suryo         = zijp7000Dto.getSuryo();
                zipr7000Report.m01_genk          = zijp7000Dto.getGenk();
                zipr7000Report.m01_genk_kin      = zijp7000Dto.getGenkKin();
                zipr7000Report.m01_baik          = zijp7000Dto.getBaik();
                zipr7000Report.m01_baik_kin      = zijp7000Dto.getBaikKin();
                
                csvBean.add(zipr7000Report);
            }
            // ＣＳＶ作成（データ出力）
            try {
                csvManager.save(csvBean, Zipr7000Report.class).to(new File(cru.getCsvFilePath()),
                        CCReportUtil.CSV_OUTPUT_ENCODING);
            } catch (Exception e) {
                throw new ChaseException(e);
            }
            
            cru.makePDF();
            List<Zijp7000ResultForm> reportList = new ArrayList<Zijp7000ResultForm>();
            Zijp7000ResultForm resultForm = new Zijp7000ResultForm();
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
     * @param req Zijp7000CondForm
     * @param params Zijp7000Param
     * @return true : validate success; false : validate fail
     */
    private boolean checkCond(Zijp7000CondForm req, Zijp7000Param params) {
        String unyoDate = context.getUnyoDate();
        
        //棚卸日のチェック処理を行う。
        if (!CCDateUtil.checkFormatDate("tnaUnyDd", req.getTnaUnyDd(), errRes)) {
            return false;
        }
        
        //事業部コードチェック
        if (!CCStringUtil.checkLength("jigyobuCd", req.getJigyobuCd(), JIGYOBU_CD_LENGTH, errRes)) {
            return false;
        }
        
        // 存在チェック
        if (!ccCheckExistsUtil.existsJigyobuCd("jigyobuCd", unyoDate, req.getKaisyaCd(),
                req.getJigyobuCd(), errRes)) {
            return false;
        }
        
        //店舗コードチェック
        if (!CCStringUtil.isEmpty(req.getTenCd()) && !ccCheckExistsUtil.existsTenCd("tenCd", unyoDate, req.getKaisyaCd()
                + req.getJigyobuCd() + req.getTenCd(), errRes)) {
            return false;
        }
        
        //部門コードチェック
        if (!CCStringUtil.isEmpty(req.getBmnCd()) 
                && !ccCheckExistsUtil.existsBmnCd("bmnCd", unyoDate, req.getJigyobuCd() + req.getBmnCd(), errRes)) {
            return false;
        }
        
        //担当者コードチェック
        if (!CCStringUtil.isEmpty(req.getTantoCd())) {
            if (!ccCheckExistsUtil.existsTantoCd("tantoCd", req.getTantoCd(), unyoDate, errRes)) {
                return false;
            }
            
            // HHT棚卸取込データから担当者コードの検索処理を行う。
            int cnt = dao.selectOne("selectZijp7000Z703Tana", params);
            if (cnt <= 0) {
                errRes.addErrorInfo(CCMessageConst.MSG_KEY_VALIDATION_ERROR);
                errRes.addErrorInfo("tantoCd", CCMessageConst.MSG_KEY_Z703TANA_TANTOCD_NOT_REGISTERED);
                return false;
            }
        }
        
        //棚Noチェック
        if (!CCStringUtil.isEmpty(req.getTanaNoFr())
                && !CCNumberUtil.checkNumber("tanaNoFr", req.getTanaNoFr(), errRes)) {
            return false;
        }
        
        if (!CCStringUtil.isEmpty(req.getTanaNoTo()) 
                && !CCNumberUtil.checkNumber("tanaNoTo", req.getTanaNoTo(), errRes)) {
            return false;
        }
        
        if (!CCStringUtil.isEmpty(req.getTanaNoFr()) && !CCStringUtil.isEmpty(req.getTanaNoTo())) {
            Integer tanaNoFr = Integer.valueOf(req.getTanaNoFr());
            Integer tanaNoTo = Integer.valueOf(req.getTanaNoTo());
            
            if (!CCNumberUtil.checkNumberFromTo("tanaNoFr", tanaNoFr, tanaNoTo, errRes)) {
                return false;
            }
        }
        
        return true;
    }
}
