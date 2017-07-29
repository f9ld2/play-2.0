// /////////////////////////////////////////////////////////////////////
// Copyright(C) 2015 NEC Solution Innovators, Ltd. All Rights Reserved.
// /////////////////////////////////////////////////////////////////////

/*
 * ==================================================================== 
 * 機能名称 : HHT基準在庫取込エラーリスト 
 * 改版履歴 
 * Rev. 改版年月日 改版者名 内容
 * 1.0 2015.06.02 LocHV 新規作成
 *  ===================================================================
 */
package jp.co.necsoft.web_md.core.app.controllers.ha;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import jp.co.necsoft.web_md.common.ChaseException;
import jp.co.necsoft.web_md.common.biz.IPluConverter;
import jp.co.necsoft.web_md.common.constants.CodeConst;
import jp.co.necsoft.web_md.common.mybatis.dao.MyBatisSqlMapDao;
import jp.co.necsoft.web_md.core.app.dto.ha.Hajp7040Param;
import jp.co.necsoft.web_md.core.app.file.report.Hapr7040Report;
import jp.co.necsoft.web_md.core.app.forms.ha.Hajp7040CondForm;
import jp.co.necsoft.web_md.core.app.forms.ha.Hajp7040ResultForm;
import jp.co.necsoft.web_md.core.common.CCMessageConst;
import jp.co.necsoft.web_md.core.common.CCSecurityAuthenticator;
import jp.co.necsoft.web_md.core.common.CCSystemContext;
import jp.co.necsoft.web_md.core.common.forms.ErrorResponse;
import jp.co.necsoft.web_md.core.common.mybatis.dto.H701kzin;
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
 * HHT基準在庫取込エラーリスト のControllerクラス
 *
 */
@Security.Authenticated(CCSecurityAuthenticator.class)
public class Hajp7040Ctrl extends Controller {
    /** ten code length */
    private static final int TEN_CD_LENGTH = 3;
    /** sosin nichiji pdf length */
    private static final int SOSIN_NICHIJI_PDF_LENGTH = 12;
    /** 自動発注フラグ */
    private static final String JIDO_HAT_TAISHO_KBN = "N0064";
    /** エラーリスト */
    private static final String TAISHO_ERROR_LIST = "0";
    
    /** Common Mybatis DAO */
    @Inject
    private MyBatisSqlMapDao dao;
    /** Check Exists Utility */
    @Inject
    private CCCheckExistsUtil ccCheckExistsUtil;
    /** System Context */
    @Inject
    private CCSystemContext context;
    /** Error Response Object */
    @Inject
    private ErrorResponse errRes;
    /** Data Util */
    @Inject
    private DataUtil dataUtil;
    /** IPlu Converter */
    @Inject
    private IPluConverter plucnv;

    /**
     * init condition
     * @return クライアントへ返却する結果
     */
    public Result initCond() {
        Hajp7040CondForm cond = new Hajp7040CondForm();
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
     * @param sendDay 作業（送信）日
     * @param outTaisho 対象
     * @return クライアントへ返却する結果
     */
    public Result query(String jigyobuCd, String tenCd, String sendDay, String outTaisho) {
        Form<Hajp7040CondForm> emptyForm = new Form<Hajp7040CondForm>(Hajp7040CondForm.class);
        Form<Hajp7040CondForm> reqForm = emptyForm.bindFromRequest();
        if (reqForm.hasErrors()) {
            errRes.addErrorInfo(CCMessageConst.MSG_KEY_VALIDATION_ERROR);
            errRes.addErrorInfo(reqForm.errors());
            return badRequest(Json.toJson(errRes));
        } else {
            Hajp7040CondForm req = reqForm.get();
            req.setJigyobuCd(jigyobuCd);
            req.setTenCd(tenCd);
            req.setSendDay(sendDay);
            req.setOutTaisho(outTaisho);

            // 「処理仕様(その他関数)」シートの【入力条件のチェック】を呼び出す。
            if (!checkCond(req)) {
                return badRequest(Json.toJson(errRes));
            }

            Hajp7040Param param = new Hajp7040Param();
            param.setJigyobuCd(jigyobuCd);
            param.setTenCd(tenCd);
            param.setSendDay(sendDay);
            param.setTantoCd(CCStringUtil.isEmpty(req.getTantoCd()) ? StringUtils.EMPTY : req.getTantoCd());
            param.setOutTaisho(outTaisho);
            
            // HHT基準在庫取込データから検索条件に沿ったデータを抽出する。
            List<H701kzin> h701kzins = dao.selectMany("selectReportDataHajp7040", param, H701kzin.class);
            
            // データが存在しない場合
            if (h701kzins.isEmpty()) {
                return notFound();
            }

            CCReportUtil cru = new CCReportUtil("HASV7040", context);
            CsvManager csvManager = CsvManagerFactory.newCsvManager();
            List<Hapr7040Report> csvBean = new ArrayList<Hapr7040Report>();
            String sTantoCode = context.getTantoCode();
            for (H701kzin h701kzin : h701kzins) {
                Hapr7040Report hapr7040Report = new Hapr7040Report();
                hapr7040Report.h00_progid = "HAPR7040";
                hapr7040Report.h00_version = "1.00";
                hapr7040Report.h01_tanto_code = sTantoCode;
                hapr7040Report.h01_jigyobu_cd = h701kzin.getJigyobuCd();
                hapr7040Report.h01_jgy_nm = h701kzin.getJgyNm();
                hapr7040Report.h01_ten_cd = h701kzin.getTenCd();
                hapr7040Report.h01_ten_nm = h701kzin.getTenNm();
                hapr7040Report.h01_term_id = h701kzin.getTermId();
                hapr7040Report.m01_shn_cd = plucnv.toDispCode(h701kzin.getShnCd(), null);
                hapr7040Report.m01_suryo = h701kzin.getSuryo().toString();
                hapr7040Report.m01_sosin_nichiji = h701kzin.getSosinNichiji().substring(0, SOSIN_NICHIJI_PDF_LENGTH);
                hapr7040Report.m01_shn_nm = h701kzin.getShnNm();
                hapr7040Report.m01_maker_hin_cd = h701kzin.getMakerHinCd();
                hapr7040Report.m01_auto_hat_flg =
                        h701kzin.getAutoHatFlg() + ":"
                                + dataUtil.getNameFromX007KBNM(JIDO_HAT_TAISHO_KBN, h701kzin.getAutoHatFlg());
                hapr7040Report.m01_hachu_teisi_from = h701kzin.getHachuTeisiFrom();
                hapr7040Report.m01_hachu_teisi_to = h701kzin.getHachuTeisiTo();
                hapr7040Report.m01_msg = h701kzin.getMsg();

                csvBean.add(hapr7040Report);
            }

            // ＣＳＶ作成（データ出力）
            try {
                csvManager.save(csvBean, Hapr7040Report.class).to(new File(cru.getCsvFilePath()),
                        CCReportUtil.CSV_OUTPUT_ENCODING);
            } catch (Exception e) {
                throw new ChaseException(e);
            }

            cru.makePDF();
            List<Hajp7040ResultForm> reportList = new ArrayList<Hajp7040ResultForm>();
            Hajp7040ResultForm resultForm = new Hajp7040ResultForm();
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
     * @param condForm Hajp7040CondForm
     * @return boolean
     */
    private boolean checkCond(Hajp7040CondForm condForm) {
        String unyoDate = context.getUnyoDate();
        String kaisyaCd = condForm.getKaisyaCd();
        
        /** basic check */
        // 桁数チェック
        // 事業部コードチェック
        if (!CCStringUtil.checkLength("jigyobuCd", condForm.getJigyobuCd(), CodeConst.LEN_JIGYOBU_CD, errRes)) {
            return false;
        }
        // 店舗コードチェック
        if (!CCStringUtil.checkLength("tenCd", condForm.getTenCd(), TEN_CD_LENGTH, errRes)) {
            return false;
        }
        // 作業（送信）日のチェック処理を行う。
        if (!CCDateUtil.checkFormatDate("sendDay", condForm.getSendDay(), errRes)) {
            return false;
        }
        
        /** logic check */
        if (!ccCheckExistsUtil.existsJigyobuCd("jigyobuCd", unyoDate, kaisyaCd, condForm.getJigyobuCd(), errRes)) {
            return false;
        }
        
        if (!ccCheckExistsUtil.existsTenCd("tenCd", unyoDate,
                kaisyaCd + condForm.getJigyobuCd() + condForm.getTenCd(), errRes)) {
            return false;
        }
        
        // 担当者コードチェック
        if (!CCStringUtil.isEmpty(condForm.getTantoCd())
                && !ccCheckExistsUtil.existsTantoCd("tantoCd", condForm.getTantoCd(), unyoDate, errRes)) {
            return false;
        }
        
        return true;
    }
}
