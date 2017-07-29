// /////////////////////////////////////////////////////////////////////
// Copyright(C) 2015 NEC Solution Innovators, Ltd. All Rights Reserved.
// /////////////////////////////////////////////////////////////////////

/*
 * ====================================================================
 * 
 * 機能名称 : HHT棚卸データ取込エラーリスト * 
 * 改版履歴
 * 
 * Rev. 改版年月日 改版者名 内容
 * 
 * 1.0 2015.06.19 NECVN 新規作成
 * 
 * ===================================================================
 */

package jp.co.necsoft.web_md.core.app.controllers.zi;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import jp.co.necsoft.web_md.common.ChaseException;
import jp.co.necsoft.web_md.common.biz.IPluConverter;
import jp.co.necsoft.web_md.common.mybatis.dao.MyBatisSqlMapDao;
import jp.co.necsoft.web_md.core.app.dto.zi.Zijp7070Dto;
import jp.co.necsoft.web_md.core.app.dto.zi.Zijp7070Param;
import jp.co.necsoft.web_md.core.app.file.report.Zipr7070Report;
import jp.co.necsoft.web_md.core.app.forms.zi.Zijp7070CondForm;
import jp.co.necsoft.web_md.core.app.forms.zi.Zijp7070ResultForm;
import jp.co.necsoft.web_md.core.common.CCMessageConst;
import jp.co.necsoft.web_md.core.common.CCSecurityAuthenticator;
import jp.co.necsoft.web_md.core.common.CCSystemContext;
import jp.co.necsoft.web_md.core.common.forms.ErrorResponse;
import jp.co.necsoft.web_md.core.common.mybatis.dao.Z703tanaMapper;
import jp.co.necsoft.web_md.core.common.mybatis.dto.Z703tana;
import jp.co.necsoft.web_md.core.common.mybatis.dto.Z703tanaExample;
import jp.co.necsoft.web_md.core.common.utils.CCCheckExistsUtil;
import jp.co.necsoft.web_md.core.common.utils.CCDateUtil;
import jp.co.necsoft.web_md.core.common.utils.CCReportUtil;
import jp.co.necsoft.web_md.core.common.utils.CCStringUtil;
import jp.co.necsoft.web_md.core.common.utils.DataUtil;

import org.springframework.beans.BeanUtils;

import play.data.Form;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;

import com.orangesignal.csv.manager.CsvManager;
import com.orangesignal.csv.manager.CsvManagerFactory;

/**
 * HHT棚卸データ取込エラーリストのControllerクラス
 *
 */
@Security.Authenticated(CCSecurityAuthenticator.class)
public class Zijp7070Ctrl extends Controller {
    /** Program Id */
    private static final String PROGRAM_ID = "ZIPR7070";
    /** Program version */
    private static final String PROGRAM_VERSION = "1.00";
    /** jigyobu code length */
    private static final int JIGYOBU_CD_LENGTH = 2;
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
    /** Data Util */
    @Inject
    private DataUtil dataUtil;
    /** S700knpt Mapper Table */
    @Inject
    private Z703tanaMapper z703tanaMapper;
    /** IPlu Converter */
    @Inject
    private IPluConverter plucnv;
    /**
     * ページを初期化する
     * 
     * @return クライアントへ返却する結果
     */
    public Result init() {
        Zijp7070CondForm form = new Zijp7070CondForm();
        String sUnyoDate = context.getUnyoDate();
        String kaisyaCd = context.getDefaultKaisyaCd();
        String jigyobuCd = dataUtil.getNewestJigyobuCd(kaisyaCd, sUnyoDate);
        // 画面項目[事業部]の設定
        form.setJigyobuCd(jigyobuCd);
        // 画面項目[店舗]の設定
        form.setTenCd(dataUtil.getNewestTenCd(kaisyaCd, jigyobuCd, sUnyoDate));

        List<Zijp7070CondForm> result = new ArrayList<Zijp7070CondForm>();
        result.add(form);
        return ok(Json.toJson(result));
    }
    
    /**
     * 引数とリクエスト(パラメータ)を条件にDBからデータ(複数件)を検索し返却する。
     * @param jigyobuCd 事業部コード
     * @param tenCd 店舗
     * @param outTaisyo 対象
     * @return クライアントへ返却する結果
     */
    public Result query(String jigyobuCd, String tenCd, String outTaisyo) {
        Form<Zijp7070CondForm> emptyForm = new Form<Zijp7070CondForm>(Zijp7070CondForm.class);
        Form<Zijp7070CondForm> reqForm = emptyForm.bindFromRequest();
        if (reqForm.hasErrors()) {
            errRes.addErrorInfo(CCMessageConst.MSG_KEY_VALIDATION_ERROR);
            errRes.addErrorInfo(reqForm.errors());
            return badRequest(Json.toJson(errRes));
        } else {
            Zijp7070CondForm req = reqForm.get();
            req.setOutTaisyo(outTaisyo);
            req.setJigyobuCd(jigyobuCd);
            req.setTenCd(tenCd);
            
            // 担当者コードを取得します。
            String tantoCode = context.getTantoCode();
            
            // 入力データチェック
            if (!checkCond(req)) {
                return badRequest(Json.toJson(errRes));
            }
            
            Zijp7070Param zijp7070Param = new Zijp7070Param();
            BeanUtils.copyProperties(req, zijp7070Param);
            
            // ＣＳＶ・ＰＤＦのパスを取得
            List<Zijp7070Dto> zijp7070Dtos = dao.selectMany("selectZijp7070ReportData", 
                    zijp7070Param, Zijp7070Dto.class);
            
            if (zijp7070Dtos.size() == 0) {
                return notFound();
            }
            
            // ＣＳＶ作成（データ取得）
            CCReportUtil cru = new CCReportUtil("ZISV7070", context);
            CsvManager csvManager = CsvManagerFactory.newCsvManager();
            List<Zipr7070Report> csvBean = new ArrayList<Zipr7070Report>();
            for (Zijp7070Dto zijp7070Dto : zijp7070Dtos) {
                Zipr7070Report zipr7070Report = new Zipr7070Report();
                zipr7070Report.h00_progid = PROGRAM_ID;
                zipr7070Report.h00_version = PROGRAM_VERSION;
                zipr7070Report.h01_tanto_code = tantoCode;
                
                zipr7070Report.h01_jigyobu_cd = zijp7070Dto.getJigyobuCd();
                zipr7070Report.h01_jgy_nm = zijp7070Dto.getJgyNm();
                zipr7070Report.h01_ten_cd = zijp7070Dto.getTenCd();
                zipr7070Report.h01_ten_nm = zijp7070Dto.getTenNm();
                zipr7070Report.m01_term_id = zijp7070Dto.getTermId();
                zipr7070Report.m01_tanto_nm = zijp7070Dto.getTantoNm();
                zipr7070Report.m01_tana_no = zijp7070Dto.getTanaNo();
                zipr7070Report.m01_shn_cd = plucnv.toDispCode(zijp7070Dto.getShnCd(), context.getUnyoDate());
                zipr7070Report.m01_suryo = zijp7070Dto.getSuryo();
                zipr7070Report.m01_sosin_nichiji = zijp7070Dto.getSosinNichiji();
                zipr7070Report.m01_shn_nm = zijp7070Dto.getShnNm();
                zipr7070Report.m01_genk = zijp7070Dto.getGenk();
                zipr7070Report.m01_baik = zijp7070Dto.getBaik();
                zipr7070Report.m01_genkin = zijp7070Dto.getGenkin();
                zipr7070Report.m01_baikin = zijp7070Dto.getBaikin();
                zipr7070Report.m01_msg = zijp7070Dto.getMsg();
                
                csvBean.add(zipr7070Report);
            }
            // ＣＳＶ作成（データ出力）
            try {
                csvManager.save(csvBean, Zipr7070Report.class).to(new File(cru.getCsvFilePath()),
                        CCReportUtil.CSV_OUTPUT_ENCODING);
            } catch (Exception e) {
                throw new ChaseException(e);
            }
            
            cru.makePDF();
            List<Zijp7070ResultForm> reportList = new ArrayList<Zijp7070ResultForm>();
            Zijp7070ResultForm resultForm = new Zijp7070ResultForm();
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
    private boolean checkCond(Zijp7070CondForm req) {
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
        
        //作業（送信）日チェック
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
            Z703tanaExample example = new Z703tanaExample();
            example.createCriteria().andTantoCdEqualTo(req.getTantoCd());
            
            List<Z703tana> list = this.z703tanaMapper.selectByExample(example);
            if (list.size() <= 0) {
                errRes.addErrorInfo(CCMessageConst.MSG_KEY_VALIDATION_ERROR);
                errRes.addErrorInfo("tantoCd", CCMessageConst.MSG_KEY_NO_FOUND_DATA);
                return false;
            }
        }
        
        return true;
    }
}
