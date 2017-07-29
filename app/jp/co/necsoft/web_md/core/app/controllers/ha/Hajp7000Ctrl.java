// /////////////////////////////////////////////////////////////////////
// Copyright(C) 2015 NEC Solution Innovators, Ltd. All Rights Reserved.
// /////////////////////////////////////////////////////////////////////

/*
 * ====================================================================
 * 
 * 機能名称 : HHT発注取込エラーリスト
 * 
 * 改版履歴
 * 
 * Rev. 改版年月日 改版者名 内容
 * 
 * 1.0 2015.06.03 NECVN 新規作成
 * 
 * ===================================================================
 */

package jp.co.necsoft.web_md.core.app.controllers.ha;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import jp.co.necsoft.web_md.common.ChaseException;
import jp.co.necsoft.web_md.common.biz.IPluConverter;
import jp.co.necsoft.web_md.common.mybatis.dao.MyBatisSqlMapDao;
import jp.co.necsoft.web_md.core.app.dto.ha.Hajp7000Dto;
import jp.co.necsoft.web_md.core.app.dto.ha.Hajp7000Param;
import jp.co.necsoft.web_md.core.app.file.report.Hapr7000Report;
import jp.co.necsoft.web_md.core.app.forms.ha.Hajp7000CondForm;
import jp.co.necsoft.web_md.core.app.forms.ha.Hajp7000ResultForm;
import jp.co.necsoft.web_md.core.common.CCMessageConst;
import jp.co.necsoft.web_md.core.common.CCSecurityAuthenticator;
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
 * HHT発注取込エラーリストのControllerクラス
 *
 */
@Security.Authenticated(CCSecurityAuthenticator.class)
public class Hajp7000Ctrl extends Controller {
    /** ten code length */
    private static final int TEN_CD_LENGTH = 3;
    /** sosin nichiji pdf length */
    private static final int SOSIN_NICHIJI_PDF_LENGTH = 12;
    /** Common Mybatis DAO */
    @Inject
    private MyBatisSqlMapDao dao;
    /** Error Response */
    @Inject
    private ErrorResponse errRes;
    /** System Context */
    @Inject
    private CCSystemContext context;
    /** Check Exists Utility */
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
        Hajp7000CondForm form = new Hajp7000CondForm();
        String sUnyoDate = context.getUnyoDate();
        String kaisyaCd = context.getDefaultKaisyaCd();
        String jigyobuCd = dataUtil.getNewestJigyobuCd(kaisyaCd, sUnyoDate);
        // 画面項目[事業部]の設定
        form.setJigyobuCd(jigyobuCd);
        // 画面項目[店舗]の設定
        form.setTenCd(dataUtil.getNewestTenCd(kaisyaCd, jigyobuCd, sUnyoDate));

        List<Hajp7000CondForm> result = new ArrayList<Hajp7000CondForm>();
        result.add(form);
        return ok(Json.toJson(result));
    }

    /**
     * 引数とリクエスト(パラメータ)を条件にDBからデータ(複数件)を検索し返却する。
     * @param jigyobuCd 事業部コード
     * @param tenCd 店舗コード
     * @param sendDay 送信日
     * @param outTaisyo 出力対象
     * @return クライアントへ返却する結果
     */

    public Result query(String jigyobuCd, String tenCd, String sendDay, String outTaisyo) {
        Form<Hajp7000CondForm> emptyForm = new Form<Hajp7000CondForm>(Hajp7000CondForm.class);
        Form<Hajp7000CondForm> reqForm = emptyForm.bindFromRequest();
        if (reqForm.hasErrors()) {
            errRes.addErrorInfo(CCMessageConst.MSG_KEY_VALIDATION_ERROR);
            errRes.addErrorInfo(reqForm.errors());
            return badRequest(Json.toJson(errRes));
        } else {
            Hajp7000CondForm req = reqForm.get();

            req.setJigyobuCd(jigyobuCd);
            req.setTenCd(tenCd);
            req.setOutTaisyo(outTaisyo);
            req.setSendDay(sendDay);

            if (req.getTantoCd() == null) {
                req.setTantoCd(StringUtils.EMPTY);
            }
            if (req.getHatDayFr() == null) {
                req.setHatDayFr(StringUtils.EMPTY);
            }
            if (req.getHatDayTo() == null) {
                req.setHatDayTo(StringUtils.EMPTY);
            }

            // 担当者コードを取得します。
            String tantoCode = context.getTantoCode();

            // 入力データチェック
            if (!this.checkCond(req)) {
                return badRequest(Json.toJson(errRes));
            }

            Hajp7000Param hajp7000Param = new Hajp7000Param();
            BeanUtils.copyProperties(req, hajp7000Param);

            // ＣＳＶ・ＰＤＦのパスを取得
            List<Hajp7000Dto> hajp7000Dtos =
                    dao.selectMany("selectHajp7000ReportData", hajp7000Param, Hajp7000Dto.class);
            
            // データが存在しない場合
            if (hajp7000Dtos.size() == 0) {
                return notFound();
            }
            
            // ＣＳＶ作成（データ取得）
            CCReportUtil cru = new CCReportUtil("HASV7000", context);
            CsvManager csvManager = CsvManagerFactory.newCsvManager();
            List<Hapr7000Report> csvBean = new ArrayList<Hapr7000Report>();
            for (Hajp7000Dto hajp7000Dto : hajp7000Dtos) {
                Hapr7000Report hapr7000Report = new Hapr7000Report();
                hapr7000Report.h00_progid = "HAPR7000";
                hapr7000Report.h00_version = "1.00";
                hapr7000Report.h01_tanto_code = tantoCode;
                
                hapr7000Report.h01_jigyobu_cd = hajp7000Dto.getJigyobuCd();
                hapr7000Report.h01_jgy_nm = hajp7000Dto.getJgyNm();
                hapr7000Report.h01_ten_cd = hajp7000Dto.getTenCd();
                hapr7000Report.h01_ten_nm = hajp7000Dto.getTenNm();
                hapr7000Report.h01_term_id = hajp7000Dto.getTermId();
                hapr7000Report.h01_tanto_nm = hajp7000Dto.getTantoNm();
                
                hapr7000Report.m01_shn_cd = plucnv.toDispCode(hajp7000Dto.getShnCd(), null);
                hapr7000Report.m01_suryo = String.valueOf(hajp7000Dto.getSuryo());
                hapr7000Report.m01_hat_dd = hajp7000Dto.getHatDd();
                hapr7000Report.m01_sosin_nichiji = hajp7000Dto.getSendDd().substring(0, SOSIN_NICHIJI_PDF_LENGTH);
                hapr7000Report.m01_shn_nm = hajp7000Dto.getShnNm();
                hapr7000Report.m01_maker_hin_cd = hajp7000Dto.getMakerHinCd();
                hapr7000Report.m01_tri_cd = hajp7000Dto.getTriCd();
                hapr7000Report.m01_tri_nm = hajp7000Dto.getTriNm();
                hapr7000Report.m01_msg = hajp7000Dto.getErrMsg();
                
                csvBean.add(hapr7000Report);
            }
            
            // ＣＳＶ作成（データ出力）
            try {
                csvManager.save(csvBean, Hapr7000Report.class).to(new File(cru.getCsvFilePath()),
                        CCReportUtil.CSV_OUTPUT_ENCODING);
            } catch (Exception e) {
                throw new ChaseException(e);
            }
            
            cru.makePDF();
            List<Hajp7000ResultForm> reportList = new ArrayList<Hajp7000ResultForm>();
            Hajp7000ResultForm resultForm = new Hajp7000ResultForm();
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
    private Boolean checkCond(Hajp7000CondForm req) {
        String unyoDate = context.getUnyoDate();
        /** basic check */
        // 桁数チェック
        // 事業部コードチェック
        if (!CCStringUtil.checkLength("jigyobuCd", req.getJigyobuCd(), 2, errRes)) {
            return false;
        }
        // 店舗コードチェック
        if (!CCStringUtil.checkLength("tenCd", req.getTenCd(), TEN_CD_LENGTH, errRes)) {
            return false;
        }
        // 発注日チェック
        if (!CCStringUtil.isEmpty(req.getHatDayFr())
                && !CCDateUtil.checkFormatDate("hatDayFr", req.getHatDayFr(), errRes)) {
            return false;
        } else if (!CCStringUtil.isEmpty(req.getHatDayTo())
                && !CCDateUtil.checkFormatDate("hatDayTo", req.getHatDayTo(), errRes)) {
            return false;
        }
        // 作業（送信）日のチェック処理を行う。
        if (!CCDateUtil.checkFormatDate("sendDay", req.getSendDay(), errRes)) {
            return false;
        }

        /** logic check */
        // 事業部コードチェック
        // 存在チェック
        if (!ccCheckExistsUtil.existsJigyobuCd("jigyobuCd", unyoDate, req.getKaisyaCd(),
                req.getJigyobuCd(), errRes)) {
            return false;
        }

        // 店舗コードチェック
        if (!ccCheckExistsUtil.existsTenCd("tenCd", unyoDate, req.getKaisyaCd() + req.getJigyobuCd()
                + req.getTenCd(), errRes)) {
            return false;
        }

        // 発注日チェック
        if (!CCStringUtil.isEmpty(req.getHatDayFr()) && !CCStringUtil.isEmpty(req.getHatDayTo())
                && !CCDateUtil.checkDateFromTo("hatDayFr", req.getHatDayFr(), req.getHatDayTo(), errRes)) {
            // 画面項目[発注日終了] < 画面項目[発注日]　の場合
            return false;
        }
        
        // 担当者コードチェック
        if (!CCStringUtil.isEmpty(req.getTantoCd())
                && !ccCheckExistsUtil.existsTantoCd("tantoCd", req.getTantoCd(), unyoDate, errRes)) {
            return false;
        }

        return true;
    }
}
