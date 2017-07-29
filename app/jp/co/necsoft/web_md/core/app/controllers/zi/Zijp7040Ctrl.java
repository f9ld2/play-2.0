// /////////////////////////////////////////////////////////////////////
// Copyright(C) 2015 NEC Solution Innovators, Ltd. All Rights Reserved.
// /////////////////////////////////////////////////////////////////////

/*
 * ====================================================================
 * 
 * 機能名称 : 棚卸中分類別合計表 * 
 * 改版履歴
 * 
 * Rev. 改版年月日 改版者名 内容
 * 
 * 1.0 2015.05.07 NECVN 新規作成
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
import jp.co.necsoft.web_md.common.mybatis.dao.MyBatisSqlMapDao;
import jp.co.necsoft.web_md.core.app.dto.zi.Zijp7040Dto;
import jp.co.necsoft.web_md.core.app.dto.zi.Zijp7040Param;
import jp.co.necsoft.web_md.core.app.file.report.Zipr7040Report;
import jp.co.necsoft.web_md.core.app.forms.zi.Zijp7040CondForm;
import jp.co.necsoft.web_md.core.app.forms.zi.Zijp7040ResultForm;
import jp.co.necsoft.web_md.core.common.CCMessageConst;
import jp.co.necsoft.web_md.core.common.CCSecurityAuthenticator;
import jp.co.necsoft.web_md.core.common.CCSystemContext;
import jp.co.necsoft.web_md.core.common.forms.ErrorResponse;
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
 * 棚卸中分類別合計表のControllerクラス
 *
 */
@Security.Authenticated(CCSecurityAuthenticator.class)
public class Zijp7040Ctrl extends Controller {
    /** length of BmnCd  */
    private static final Integer BMN_CD_LENGTH = 5;
    /** Program Id */
    private static final String PROGRAM_ID = "ZIPR7040";
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
    /**
     * ページを初期化する
     * 
     * @return クライアントへ返却する結果
     */
    public Result init() {
        Zijp7040CondForm form = new Zijp7040CondForm();
        String sUnyoDate = context.getUnyoDate();
        String kaisyaCd = context.getDefaultKaisyaCd();
        String jigyobuCd = dataUtil.getNewestJigyobuCd(kaisyaCd, sUnyoDate);
        // 画面項目[事業部]の設定
        form.setJigyobuCd(jigyobuCd);
        // 画面項目[店舗]の設定
        form.setTenCd(dataUtil.getNewestTenCd(kaisyaCd, jigyobuCd, sUnyoDate));

        List<Zijp7040CondForm> result = new ArrayList<Zijp7040CondForm>();
        result.add(form);
        return ok(Json.toJson(result));
    }
    
    /**
     * 引数とリクエスト(パラメータ)を条件にDBからデータ(複数件)を検索し返却する。
     * @param jigyobuCd 事業部コード
     * @param tnaUnyDd 棚卸実施日
     * @return クライアントへ返却する結果
     */
    public Result query(String jigyobuCd, String tnaUnyDd) {
        Form<Zijp7040CondForm> emptyForm = new Form<Zijp7040CondForm>(Zijp7040CondForm.class);
        Form<Zijp7040CondForm> reqForm = emptyForm.bindFromRequest();
        if (reqForm.hasErrors()) {
            errRes.addErrorInfo(CCMessageConst.MSG_KEY_VALIDATION_ERROR);
            errRes.addErrorInfo(reqForm.errors());
            return badRequest(Json.toJson(errRes));
        } else {
            String unyoDate = context.getUnyoDate();
            Zijp7040CondForm req = reqForm.get();
            req.setJigyobuCd(jigyobuCd);
            req.setTnaUnyDd(tnaUnyDd);
            req.setUnyoDate(unyoDate);
            
            // 担当者コードを取得します。
            String tantoCode = context.getTantoCode();
            
            // 入力データチェック
            if (!checkCond(req)) {
                return badRequest(Json.toJson(errRes));
            }
            
            Zijp7040Param zijp7040Param = new Zijp7040Param();
            BeanUtils.copyProperties(req, zijp7040Param);
            
            // ＣＳＶ・ＰＤＦのパスを取得
            List<Zijp7040Dto> zijp7040Dtos = dao.selectMany("selectZijp7040ReportData", 
                    zijp7040Param, Zijp7040Dto.class);
            
            if (zijp7040Dtos.size() == 0) {
                return notFound();
            }
            
            // ＣＳＶ作成（データ取得）
            CCReportUtil cru = new CCReportUtil("ZISV7040", context);
            CsvManager csvManager = CsvManagerFactory.newCsvManager();
            List<Zipr7040Report> csvBean = new ArrayList<Zipr7040Report>();
            for (Zijp7040Dto zijp7040Dto : zijp7040Dtos) {
                Zipr7040Report zipr7040Report = new Zipr7040Report();
                zipr7040Report.h00_progid = PROGRAM_ID;
                zipr7040Report.h00_version = PROGRAM_VERSION;
                zipr7040Report.h01_tanto_code = tantoCode;
                
                zipr7040Report.h01_jigyobu_cd = zijp7040Dto.getJigyobuCd();
                zipr7040Report.h01_jgy_nm = zijp7040Dto.getJgyNm();
                zipr7040Report.h01_ten_cd = zijp7040Dto.getTenCd();
                zipr7040Report.h01_ten_nm = zijp7040Dto.getTenNm();
                zipr7040Report.h01_tna_uny_dd = zijp7040Dto.getTnaUnyDd();
                zipr7040Report.m01_chu_bnr_cd = zijp7040Dto.getChuBnrCd();
                zipr7040Report.m01_bnr_nm = dataUtil.getChuBnrName(unyoDate, zijp7040Dto.getBmnCd().substring(0, 2),
                        zijp7040Dto.getBmnCd().substring(2, BMN_CD_LENGTH), zijp7040Dto.getChuBnrCd());
                zipr7040Report.m01_suryo = zijp7040Dto.getSuryo();
                zipr7040Report.m01_genk_kin = zijp7040Dto.getGenkKin();
                zipr7040Report.m01_baik_kin = zijp7040Dto.getBaikKin();
                
                csvBean.add(zipr7040Report);
            }
            // ＣＳＶ作成（データ出力）
            try {
                csvManager.save(csvBean, Zipr7040Report.class).to(new File(cru.getCsvFilePath()),
                        CCReportUtil.CSV_OUTPUT_ENCODING);
            } catch (Exception e) {
                throw new ChaseException(e);
            }
            
            cru.makePDF();
            List<Zijp7040ResultForm> reportList = new ArrayList<Zijp7040ResultForm>();
            Zijp7040ResultForm resultForm = new Zijp7040ResultForm();
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
    private boolean checkCond(Zijp7040CondForm req) {
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
        if (!CCStringUtil.isEmpty(req.getTenCd()) && !ccCheckExistsUtil.existsTenCd(unyoDate, req.getKaisyaCd() 
                + req.getJigyobuCd()  + req.getTenCd())) {
            return false;
        }
        
        //棚卸実施日のチェック処理を行う。
        if (!CCDateUtil.checkFormatDate("tnaUnyDd", req.getTnaUnyDd(), errRes)) {
            return false;
        }
        
        return true;
    }
}
