// /////////////////////////////////////////////////////////////////////
// Copyright(C) 2014 NEC Solution Innovators, Ltd. All Rights Reserved.
// /////////////////////////////////////////////////////////////////////

/*
 * ====================================================================
 * 
 * 機能名称 : 棚卸実施チェックリスト
 * 
 * 改版履歴
 * 
 * Rev. 改版年月日 改版者名 内容
 * 
 * 1.0 2015/05/07 TrieuVN 新規作成
 * 
 * ===================================================================
 */
package jp.co.necsoft.web_md.core.app.controllers.zi;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;

import jp.co.necsoft.web_md.common.ChaseException;
import jp.co.necsoft.web_md.common.mybatis.dao.MyBatisSqlMapDao;
import jp.co.necsoft.web_md.core.app.dto.zi.Zijp7010Dto;
import jp.co.necsoft.web_md.core.app.dto.zi.Zijp7010Param;
import jp.co.necsoft.web_md.core.app.file.report.Zipr7010Report;
import jp.co.necsoft.web_md.core.app.forms.si.Sijp7040CondForm;
import jp.co.necsoft.web_md.core.app.forms.si.Sijp7040ResultForm;
import jp.co.necsoft.web_md.core.app.forms.zi.Zijp7010CondForm;
import jp.co.necsoft.web_md.core.common.CCMessageConst;
import jp.co.necsoft.web_md.core.common.CCSecurityAuthenticator;
import jp.co.necsoft.web_md.core.common.CCSystemContext;
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
 * 棚卸実施チェックリストのControllerクラス
 *
 */
@Security.Authenticated(CCSecurityAuthenticator.class)
public class Zijp7010Ctrl extends Controller {
    /** Program Id */
    private static final String PROGRAM_ID = "ZIPR7010";
    /** Program version */
    private static final String PROGRAM_VERSION = "1.00";
    /** jigyobu code length */
    private static final int JIGYOBU_CD_LENGTH = 2;
    /** System Context */
    @Inject
    private CCSystemContext context;
    /** Error response */
    @Inject
    private ErrorResponse errRes;
    /** Mybatis Common Dao */
    @Inject
    private MyBatisSqlMapDao dao;
    /** DataUtil */
    @Inject
    private DataUtil dataUtil;
    /** Check exist utility */
    @Inject
    private CCCheckExistsUtil ccCheckExistsUtil;
    
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
    * 引数とリクエスト(パラメータ)を条件にDBからデータ(複数件)を検索し返却する。*
    * @param jigyobuCd 事業部コード
    * @param tnaUnyDd 棚卸実施日
    * @return クライアントへ返却する結果
    */
    public Result query(String jigyobuCd, String tnaUnyDd) {
        Form<Zijp7010CondForm> emptyForm = new Form<Zijp7010CondForm>(Zijp7010CondForm.class);
        Form<Zijp7010CondForm> reqForm = emptyForm.bindFromRequest();
        if (reqForm.hasErrors()) {
            errRes.addErrorInfo(CCMessageConst.MSG_KEY_VALIDATION_ERROR);
            errRes.addErrorInfo(reqForm.errors());
            return badRequest(Json.toJson(errRes));
        } else {
            Zijp7010CondForm req = reqForm.get();
            req.setJigyobuCd(jigyobuCd);
            req.setTnaUnyDd(tnaUnyDd);
            req.setUnyoDate(context.getUnyoDate());
            
            if (req.getTenCd() == null) {
                req.setTenCd(StringUtils.EMPTY);
            }
            
            // 担当者コードを取得します。
            String tantoCode = context.getTantoCode();
            
            // 入力データチェック
            if (!checkCond(req)) {
                return badRequest(Json.toJson(errRes));
            }
            
            Zijp7010Param zijp7010Param = new Zijp7010Param();
            BeanUtils.copyProperties(req, zijp7010Param);
            
            // ＣＳＶ・ＰＤＦのパスを取得
            List<Zijp7010Dto> zijp7010Dtos = dao.selectMany("selectZijp7010ReportData",
                    zijp7010Param, Zijp7010Dto.class);
            
            if (zijp7010Dtos.size() == 0) {
                return notFound();
            }
            
            // ＣＳＶ作成（データ取得）
            CCReportUtil cru = new CCReportUtil("ZISV7010", context);
            CsvManager csvManager = CsvManagerFactory.newCsvManager();
            List<Zipr7010Report> csvBean = new ArrayList<Zipr7010Report>();
            for (Zijp7010Dto zijp7010Dto : zijp7010Dtos) {
                Zipr7010Report zipr7010Report = new Zipr7010Report();
                zipr7010Report.h00_progid = PROGRAM_ID;
                zipr7010Report.h00_version = PROGRAM_VERSION;
                zipr7010Report.h01_tanto_code = tantoCode;
                
                zipr7010Report.h01_jigyobu_cd =  zijp7010Dto.getJigyobuCd();
                zipr7010Report.h01_jgy_nm = zijp7010Dto.getJgyNm();
                zipr7010Report.h01_ten_cd = zijp7010Dto.getTenCd();
                zipr7010Report.h01_ten_nm = zijp7010Dto.getTenNm();
                zipr7010Report.h01_tna_uny_dd = zijp7010Dto.getTnaUnyDd();
                zipr7010Report.m01_tana_no = zijp7010Dto.getTanaNo();
                zipr7010Report.m01_meisai_kensu = zijp7010Dto.getMeisaiKensu();
                zipr7010Report.m01_suryo = zijp7010Dto.getSuryo();
                zipr7010Report.m01_genk_kin = zijp7010Dto.getGenkKin();
                zipr7010Report.m01_baik_kin = zijp7010Dto.getBaikKin();
                
                csvBean.add(zipr7010Report);
            }
            // ＣＳＶ作成（データ出力）
            try {
                csvManager.save(csvBean, Zipr7010Report.class).to(new File(cru.getCsvFilePath()),
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
    private boolean checkCond(Zijp7010CondForm req) {
        String unyoDate = context.getUnyoDate();
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
        
        //棚卸実施日のチェック処理を行う。
        if (!CCDateUtil.checkFormatDate("tnaUnyDd", req.getTnaUnyDd(), errRes)) {
            return false;
        }
        
        return true;
    }
}
