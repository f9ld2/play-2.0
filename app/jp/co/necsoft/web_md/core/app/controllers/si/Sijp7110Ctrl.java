// /////////////////////////////////////////////////////////////////////
// Copyright(C) 2015 NEC Solution Innovators, Ltd. All Rights Reserved.
// /////////////////////////////////////////////////////////////////////

/*
 * ==================================================================== 
 * 機能名称 : HHT委託精算取込エラーリスト
 * 改版履歴 
 * Rev. 改版年月日 改版者名 内容
 * 1.0 2015.06.04 LocHV 新規作成
 *  ===================================================================
 */
package jp.co.necsoft.web_md.core.app.controllers.si;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import jp.co.necsoft.web_md.common.ChaseException;
import jp.co.necsoft.web_md.common.biz.IPluConverter;
import jp.co.necsoft.web_md.common.constants.CodeConst;
import jp.co.necsoft.web_md.common.mybatis.dao.MyBatisSqlMapDao;
import jp.co.necsoft.web_md.core.app.dto.si.Sijp7110Param;
import jp.co.necsoft.web_md.core.app.file.report.Sipr7110Report;
import jp.co.necsoft.web_md.core.app.forms.si.Sijp7110CondForm;
import jp.co.necsoft.web_md.core.app.forms.si.Sijp7110ResultForm;
import jp.co.necsoft.web_md.core.common.CCMessageConst;
import jp.co.necsoft.web_md.core.common.CCSecurityAuthenticator;
import jp.co.necsoft.web_md.core.common.CCSystemContext;
import jp.co.necsoft.web_md.core.common.forms.ErrorResponse;
import jp.co.necsoft.web_md.core.common.mybatis.dao.S703itssMapper;
import jp.co.necsoft.web_md.core.common.mybatis.dto.S703itss;
import jp.co.necsoft.web_md.core.common.mybatis.dto.S703itssExample;
import jp.co.necsoft.web_md.core.common.utils.CCCheckExistsUtil;
import jp.co.necsoft.web_md.core.common.utils.CCDateUtil;
import jp.co.necsoft.web_md.core.common.utils.CCReportUtil;
import jp.co.necsoft.web_md.core.common.utils.CCStringUtil;
import jp.co.necsoft.web_md.core.common.utils.DataUtil;

import org.apache.commons.lang3.StringUtils;

import play.data.Form;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;

import com.orangesignal.csv.manager.CsvManager;
import com.orangesignal.csv.manager.CsvManagerFactory;

/**
 * HHT委託精算取込エラーリストのControllerクラス
 *
 */
@Security.Authenticated(CCSecurityAuthenticator.class)
public class Sijp7110Ctrl extends Controller {
    /** エラーリスト */
    private static final String TAISHO_ERROR_LIST = "0";
    /** ten code length */
    private static final int TEN_CD_LENGTH = 3;
    /** date length */
    private static final int DATE_LENGTH = 8;
    /** date time length */
    private static final int DATE_TIME_LENGTH = 12;
    
    /** S703itss's Mapper */
    @Inject
    private S703itssMapper s703itssMapper;
    
    /** Check exist utility */
    @Inject
    private CCCheckExistsUtil ccCheckExistsUtil;
    /** System Context */
    @Inject
    private CCSystemContext context;
    /** Error Response Object */
    @Inject
    private ErrorResponse errRes;
    /** Data utility */
    @Inject
    private DataUtil dataUtil;
    /** Mybatis common dao */
    @Inject
    private MyBatisSqlMapDao dao;
    /** IPlu Converter */
    @Inject
    private IPluConverter plucnv;
    
    /**
     * init condition
     * @return クライアントへ返却する結果
     */
    public Result initCond() {
        Sijp7110CondForm cond = new Sijp7110CondForm();
        String unyoDate = context.getUnyoDate();
        String kaisyaCd = context.getDefaultKaisyaCd();
        cond.setSendDay(unyoDate);
        cond.setOutTaisho(TAISHO_ERROR_LIST);
        String jigyobuCd = dataUtil.getNewestJigyobuCd(kaisyaCd, unyoDate);
        cond.setJigyobuCd(jigyobuCd);
        cond.setTenCd(dataUtil.getNewestTenCd(kaisyaCd, jigyobuCd, unyoDate));

        return ok(Json.toJson(cond));
    }

    /**
     * 引数とリクエスト(パラメータ)を条件にDBからデータ(複数件)を検索し返却する。
     * @param jigyobuCd 事業部コード
     * @param tenCd 店舗コード
     * @param outTaisho 対象
     * @return クライアントへ返却する結果
     */
    public Result query(String jigyobuCd, String tenCd, String outTaisho) {
        Form<Sijp7110CondForm> emptyForm = new Form<Sijp7110CondForm>(Sijp7110CondForm.class);
        Form<Sijp7110CondForm> reqForm = emptyForm.bindFromRequest();
        if (reqForm.hasErrors()) {
            errRes.addErrorInfo(CCMessageConst.MSG_KEY_VALIDATION_ERROR);
            errRes.addErrorInfo(reqForm.errors());
            return badRequest(Json.toJson(errRes));
        } else {
            Sijp7110CondForm req = reqForm.get();
            req.setJigyobuCd(jigyobuCd);
            req.setTenCd(tenCd);
            req.setOutTaisho(outTaisho);

            if (!this.checkCond(req)) {
                return badRequest(Json.toJson(errRes));
            }

            // HHT委託精算取込データから検索条件に沿ったデータを抽出する
            Sijp7110Param param = new Sijp7110Param();
            param.setJigyobuCd(jigyobuCd);
            param.setTenCd(tenCd);
            param.setSendDay(CCStringUtil.isEmpty(req.getSendDay()) ? StringUtils.EMPTY : req.getSendDay());
            param.setTantoCd(CCStringUtil.isEmpty(req.getTantoCd()) ? StringUtils.EMPTY : req.getTantoCd());
            param.setOutTaisho(outTaisho);
            
            List<S703itss> s703itsss = dao.selectMany("selectSijp7110ReportData", param, S703itss.class);
            
            // データが存在しない場合
            if (s703itsss.isEmpty()) {
                return notFound();
            }

            CCReportUtil cru = new CCReportUtil("SISV7110", context);
            CsvManager csvManager = CsvManagerFactory.newCsvManager();
            List<Sipr7110Report> csvBean = new ArrayList<Sipr7110Report>();
            String tantoCode = context.getTantoCode();
            
            for (S703itss s703itss : s703itsss) {
                Sipr7110Report sipr7110Report = new Sipr7110Report();
                // common
                sipr7110Report.h00_progid = "SIPR7110";
                sipr7110Report.h00_version = "1.00";
                sipr7110Report.h01_tanto_code = tantoCode;

                // detail
                sipr7110Report.h01_jigyobu_cd = s703itss.getJigyobuCd();
                sipr7110Report.h01_jgy_nm = s703itss.getJgyNm();
                sipr7110Report.h01_ten_cd = s703itss.getTenCd();
                sipr7110Report.h01_ten_nm = s703itss.getTenNm();
                sipr7110Report.h01_term_id = s703itss.getTermId();
                sipr7110Report.h01_sosin_nichi = s703itss.getSosinNichiji().substring(0, DATE_LENGTH);
                sipr7110Report.m01_tanto_cd = s703itss.getTantoCd();
                sipr7110Report.m01_tanto_nm = s703itss.getTantoNm();
                sipr7110Report.m01_shn_cd = plucnv.toDispCode(s703itss.getShnCd(), null);
                sipr7110Report.m01_suryo = s703itss.getSuryo().toString();
                sipr7110Report.m01_genk_kin = s703itss.getGenkKin().toString();
                sipr7110Report.m01_baik_kin = s703itss.getBaikKin().toString();
                sipr7110Report.m01_sosin_nichiji = s703itss.getSosinNichiji().substring(0, DATE_TIME_LENGTH);
                sipr7110Report.m01_maker_hin_cd = s703itss.getMakerHinCd();
                sipr7110Report.m01_shn_nm = s703itss.getShnNm();
                sipr7110Report.m01_torihiki_cd = s703itss.getTorihikiCd();
                sipr7110Report.m01_tri_nm = s703itss.getTriNm();
                sipr7110Report.m01_msg = s703itss.getMsg();
                csvBean.add(sipr7110Report);
            }

            // ＣＳＶ作成（データ出力）
            try {
                csvManager.save(csvBean, Sipr7110Report.class).to(new File(cru.getCsvFilePath()),
                        CCReportUtil.CSV_OUTPUT_ENCODING);
            } catch (Exception e) {
                throw new ChaseException(e);
            }

            cru.makePDF();
            List<Sijp7110ResultForm> reportList = new ArrayList<Sijp7110ResultForm>();
            Sijp7110ResultForm resultForm = new Sijp7110ResultForm();
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
     * check parameter condition
     * @param req Sijp7110CondForm
     * @return boolean
     */
    private boolean checkCond(Sijp7110CondForm req) {
        String unyoDate = context.getUnyoDate();
        String kaisyaCd = req.getKaisyaCd();

        /** basic check */
        // 桁数チェック
        // 事業部コードチェック
        if (!CCStringUtil.checkLength("jigyobuCd", req.getJigyobuCd(), CodeConst.LEN_JIGYOBU_CD, errRes)) {
            return false;
        }
        // 店舗コードチェック
        if (!CCStringUtil.checkLength("tenCd", req.getTenCd(), TEN_CD_LENGTH, errRes)) {
            return false;
        }
        // 作業（送信）日のチェック処理を行う。
        if (!CCStringUtil.isEmpty(req.getSendDay())
                && !CCDateUtil.checkFormatDate("sendDay", req.getSendDay(), errRes)) {
            return false;
        }

        /** logic check */
        // 事業部コードチェック
        if (!ccCheckExistsUtil.existsJigyobuCd("jigyobuCd", unyoDate, kaisyaCd, req.getJigyobuCd(), errRes)) {
            return false;
        }

        // 店舗コードチェック
        if (!ccCheckExistsUtil
                .existsTenCd("tenCd", unyoDate, kaisyaCd + req.getJigyobuCd() + req.getTenCd(), errRes)) {
            return false;
        }
        
        // 担当者コードチェック
        if (!CCStringUtil.isEmpty(req.getTantoCd())
                && !ccCheckExistsUtil.existsTantoCd("tantoCd", req.getTantoCd(), unyoDate, errRes)) {
            return false;
        }

        // 担当者コードチェック
        if (!CCStringUtil.isEmpty(req.getTantoCd())) {
            S703itssExample example = new S703itssExample();
            example.createCriteria().andTantoCdEqualTo(req.getTantoCd());
            int count = s703itssMapper.countByExample(example);
            if (count <= 0) {
                errRes.addErrorInfo(CCMessageConst.MSG_KEY_VALIDATION_ERROR);
                errRes.addErrorInfo("tantoCd", CCMessageConst.MSG_KEY_S703ITSS_TANTOCD_NOT_REGISTERED);
                return false;
            }
        }
        
        return true;
    }
}
