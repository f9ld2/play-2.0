// /////////////////////////////////////////////////////////////////////
// Copyright(C) 2015 NEC Solution Innovators, Ltd. All Rights Reserved.
// /////////////////////////////////////////////////////////////////////

/*
 * ====================================================================
 * 
 * 機能名称 : 入荷状況一覧表
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
import jp.co.necsoft.web_md.core.app.dto.si.Sijp7020Dto;
import jp.co.necsoft.web_md.core.app.dto.si.Sijp7020Param;
import jp.co.necsoft.web_md.core.app.file.report.Sipr7020Report;
import jp.co.necsoft.web_md.core.app.forms.si.Sijp7020CondForm;
import jp.co.necsoft.web_md.core.app.forms.si.Sijp7020ResultForm;
import jp.co.necsoft.web_md.core.common.CCMessageConst;
import jp.co.necsoft.web_md.core.common.CCSecurityAuthenticator;
import jp.co.necsoft.web_md.core.common.CCSystemContext;
import jp.co.necsoft.web_md.core.common.forms.ErrorResponse;
import jp.co.necsoft.web_md.core.common.mybatis.dao.S001eosdMapper;
import jp.co.necsoft.web_md.core.common.mybatis.dao.S002tgkdMapper;
import jp.co.necsoft.web_md.core.common.mybatis.dto.S001eosd;
import jp.co.necsoft.web_md.core.common.mybatis.dto.S001eosdExample;
import jp.co.necsoft.web_md.core.common.mybatis.dto.S001eosdExample.Criteria;
import jp.co.necsoft.web_md.core.common.mybatis.dto.S002tgkd;
import jp.co.necsoft.web_md.core.common.mybatis.dto.S002tgkdExample;
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
 * 入荷状況一覧表のControllerクラス
 *
 */
@Security.Authenticated(CCSecurityAuthenticator.class)
public class Sijp7020Ctrl extends Controller {
    /** ten code length */
    private static final int TEN_CD_LENGTH = 3;
    /** 削除FLG */
    private static final String DELETED_FLAG_NORMAL = "0";
    /** date convert unit */
    private static final String DATE_CONVERT_UNIT = "DAY";
    /** out taiso all value */
    private static final String OUT_TAISO_ALL = "0";
    /** out taiso 仕入 value */
    private static final String OUT_TAISO_ALL_SHIIRE = "1";
    /** out taiso 仕入 name */
    private static final String OUT_TAISO_ALL_SHIIRE_NAME = "仕入";
    /** out taiso 品振 value */
    private static final String OUT_TAISO_ALL_SHOP = "2";
    /** out taiso 品振 name */
    private static final String OUT_TAISO_ALL_SHOP_NAME = "品振";
    
    /** dao */
    @Inject
    private MyBatisSqlMapDao dao;
    /** errRes */
    @Inject
    private ErrorResponse errRes;
    /** context */
    @Inject
    private CCSystemContext context;
    /** Check Exists Util */
    @Inject
    private CCCheckExistsUtil ccCheckExistsUtil;
    /** Data Util */
    @Inject
    private DataUtil dataUtil;
    /** IPlu Converter */
    @Inject
    private IPluConverter plucnv;
    /** S001eosd Mapper */
    @Inject
    private S001eosdMapper s001eosdMapper;
    
    /** S002TGKD Mapper */
    @Inject
    private S002tgkdMapper s002tgkdMapper;
    
    /**
     * ページを初期化する
     * 
     * @return クライアントへ返却する結果
     */
    public Result init() {
        Sijp7020CondForm form = new Sijp7020CondForm();
        String sUnyoDate = context.getUnyoDate();
        String kaisyaCd = context.getDefaultKaisyaCd();
        String jigyobuCd = dataUtil.getNewestJigyobuCd(kaisyaCd, sUnyoDate);
        if (!jigyobuCd.isEmpty()) {
            // 画面項目[事業部]の設定
            form.setJigyobuCd(jigyobuCd);
            // 画面項目[店舗]の設定
            form.setTenCds(dataUtil.getNewestTenCd(kaisyaCd, jigyobuCd, sUnyoDate));
        }
        // 画面項目[取引先]の設定
        form.setTriCd(dataUtil.getNewestTriCd(sUnyoDate));
        
        List<Sijp7020CondForm> result = new ArrayList<Sijp7020CondForm>();
        result.add(form);
        
        return ok(Json.toJson(result));
    }
    
    /**
     * 引数とリクエスト(パラメータ)を条件にDBからデータ(複数件)を検索し返却する。
     * @param nhnDayFr 納品日
     * @param nhnDayTo 納品日終了
     * @param outTaisyo 出力対象
     * @return クライアントへ返却する結果
     */
    
    public Result query(String nhnDayFr, String nhnDayTo, String outTaisyo) {
        Form<Sijp7020CondForm> emptyForm = new Form<Sijp7020CondForm>(Sijp7020CondForm.class);
        Form<Sijp7020CondForm> reqForm = emptyForm.bindFromRequest();
        if (reqForm.hasErrors()) {
            errRes.addErrorInfo(CCMessageConst.MSG_KEY_VALIDATION_ERROR);
            errRes.addErrorInfo(reqForm.errors());
            return badRequest(Json.toJson(errRes));
        } else {
            Sijp7020CondForm req = reqForm.get();
            
            req.setNhnDayFr(nhnDayFr);
            req.setNhnDayTo(nhnDayTo);
            req.setOutTaisyo(outTaisyo);
            
            List<String> lsTenCd = new ArrayList<String>();
            if (req.getTenCds() == null) {
                req.setTenCds(StringUtils.EMPTY);
            } else {
                if (req.getTenCds() != null && !req.getTenCds().isEmpty()) {
                    String[] tmp = req.getTenCds().split(",");
                    for (int i = 0; i < tmp.length; i++) {
                        if (!CCStringUtil.isEmpty(tmp[i])) {
                            lsTenCd.add(tmp[i]);
                        }
                    }
                    
                }
            }
            req.setLsTenCd(lsTenCd);
            if (req.getJigyobuCd() == null) {
                req.setJigyobuCd(StringUtils.EMPTY);
            }
            if (req.getTriCd() == null) {
                req.setTriCd(StringUtils.EMPTY);
            }
            
            // 入力データチェック
            if (!checkCond(req)) {
                return badRequest(Json.toJson(errRes));
            }
            
            Sijp7020Param sijp7020Param = new Sijp7020Param();
            BeanUtils.copyProperties(req, sijp7020Param);
            sijp7020Param.setUnyoDate(context.getUnyoDate());
            
            // ＣＳＶ・ＰＤＦのパスを取得
            List<Sijp7020Dto> sijp7020Dtos1 = new ArrayList<Sijp7020Dto>();
            
            if (OUT_TAISO_ALL.equals(req.getOutTaisyo()) || OUT_TAISO_ALL_SHIIRE.equals(req.getOutTaisyo())) {
                sijp7020Param.setOutTaisyo(OUT_TAISO_ALL_SHIIRE);
                sijp7020Dtos1 = dao.selectMany("selectSijp7020ReportDataS001S002", sijp7020Param, Sijp7020Dto.class);
            }
            List<Sijp7020Dto> sijp7020Dtos2 = new ArrayList<Sijp7020Dto>();
            if (OUT_TAISO_ALL.equals(req.getOutTaisyo()) || OUT_TAISO_ALL_SHOP.equals(req.getOutTaisyo())) {
                sijp7020Param.setOutTaisyo(OUT_TAISO_ALL_SHOP);
                sijp7020Dtos2 = dao.selectMany("selectSijp7020ReportDataS003", sijp7020Param, Sijp7020Dto.class);
            }
            
            if (OUT_TAISO_ALL.equals(req.getOutTaisyo()) && sijp7020Dtos1.size() == 0 && sijp7020Dtos2.size() == 0) {
                return notFound();
            }
            if (OUT_TAISO_ALL_SHIIRE.equals(req.getOutTaisyo()) && sijp7020Dtos1.size() == 0) {
                return notFound();
            }
            if (OUT_TAISO_ALL_SHOP.equals(req.getOutTaisyo()) && sijp7020Dtos2.size() == 0) {
                return notFound();
            }
            
            // ＣＳＶ作成（データ取得）
            List<Sijp7020ResultForm> reportList = new ArrayList<Sijp7020ResultForm>();
            if (sijp7020Dtos1.size() != 0) {
                reportList.add(this.createReport(sijp7020Dtos1));
            }
            if (sijp7020Dtos2.size() != 0) {
                reportList.add(this.createReport(sijp7020Dtos2));
            }
            
            return ok(Json.toJson(reportList));
        }
    }
    
    /**
     * check validate 
     * @param req Request parameter
     * @return true : validate success; false : validate fail
     */
    private Boolean checkCond(Sijp7020CondForm req) {
        String unyoDate = context.getUnyoDate();
        /** basic check */
        // 桁数チェック
        // 店舗コードチェック
        if (req.getLsTenCd().size() > 0) {
            if (!CCStringUtil.checkLengthMultiple("tenCd", req.getLsTenCd(), TEN_CD_LENGTH, errRes)) {
                return false;
            }
        }
        // 発注日チェック
        if (!CCDateUtil.checkFormatDate("nhnDayFr", req.getNhnDayFr(), errRes)) {
            // 日付型チェック
            return false;
        }
        if (!CCDateUtil.checkFormatDate("nhnDayTo", req.getNhnDayTo(), errRes)) {
            // 日付型チェック
            return false;
        }
        
        /** logic check */
        // 事業部コードチェック
        if (!CCStringUtil.isEmpty(req.getJigyobuCd())) {
            // 存在チェック
            if (!ccCheckExistsUtil
                    .existsJigyobuCd("jigyobuCd", unyoDate, req.getKaisyaCd(), req.getJigyobuCd(), errRes)) {
                return false;
            }
        }
        
        // 店舗コードチェック
        if (req.getLsTenCd().size() > 0 && !CCStringUtil.isEmpty(req.getJigyobuCd())) {
            // 存在チェック
            if (!ccCheckExistsUtil.existsTenCdMultiple("tenCd", unyoDate, req.getKaisyaCd(), req.getJigyobuCd(),
                    req.getLsTenCd(), errRes)) {
                return false;
            }
        }
        
        // 納品予定日チェック
        // 画面項目[発注日終了] < 画面項目[発注日]　の場合
        if (!CCDateUtil.checkDateFromTo("nhnDayFr", req.getNhnDayFr(), req.getNhnDayTo(), errRes)) {
            return false;
        }
        
        // 取引先コード存在チェック
        String taisho = req.getOutTaisyo();
        if (!CCStringUtil.isEmpty(req.getTriCd())) {
            if (!ccCheckExistsUtil.existTriCd("triCd", unyoDate, req.getTriCd(), errRes)) {
                return false;
            }
            if ((OUT_TAISO_ALL.equals(taisho) || OUT_TAISO_ALL_SHIIRE.equals(taisho))) {
                if (!this.existTriCdS001(req.getKaisyaCd(), req.getJigyobuCd(), req.getLsTenCd(), req.getTriCd())
                        && !this.existTriCdS002(req.getKaisyaCd(), 
                                req.getJigyobuCd(), req.getLsTenCd(), req.getTriCd())) {
                    errRes.addErrorInfo(CCMessageConst.MSG_KEY_VALIDATION_ERROR);
                    errRes.addErrorInfo("triCd", CCMessageConst.MSG_KEY_CUSTOMER_NOT_REGISTERED);
                    return false;
                }
            }
        }
        
        return true;
    }
    
    /**
     * create report
     * @param sijp7020Dtos data to export report
     * @return Sijp7020ResultForm form contain report url
     */
    private Sijp7020ResultForm createReport(List<Sijp7020Dto> sijp7020Dtos) {
        // 担当者コードを取得します。
        String tantoCode = context.getTantoCode();
        // ＣＳＶ作成（データ取得）
        CCReportUtil cru = new CCReportUtil("SISV7020", context);
        CsvManager csvManager = CsvManagerFactory.newCsvManager();
        List<Sipr7020Report> csvBean = new ArrayList<Sipr7020Report>();
        Integer pLineNo = 0;
        for (Sijp7020Dto sijp7020Dto : sijp7020Dtos) {
            pLineNo = pLineNo + 1;
            Sipr7020Report sipr7020Report = new Sipr7020Report();
            sipr7020Report.h00_progid = "SIPR7020";
            sipr7020Report.h00_version = "1.00";
            sipr7020Report.h01_tanto_code = tantoCode;
            
            if (OUT_TAISO_ALL_SHIIRE.equals(sijp7020Dto.getTaisho())) {
                sipr7020Report.h01_jigyobu_cd = sijp7020Dto.getJiyoubuCd();
                sipr7020Report.h01_jgy_nm = sijp7020Dto.getJiyoubuNm();
            }
            sipr7020Report.h01_ten_cd = sijp7020Dto.getTenCd();
            sipr7020Report.h01_ten_name = sijp7020Dto.getTenNm();
            if (OUT_TAISO_ALL_SHOP.equals(sijp7020Dto.getTaisho())) {
                sipr7020Report.h01_nhn_yotei_ymd = CCDateUtil.doCalcDate(sijp7020Dto.getNhnYmd(), DATE_CONVERT_UNIT, 1);
            } else {
                sipr7020Report.h01_nhn_yotei_ymd = sijp7020Dto.getNhnYmd();
            }
            if (OUT_TAISO_ALL_SHIIRE.equals(sijp7020Dto.getTaisho())) {
                sipr7020Report.h01_taisho = OUT_TAISO_ALL_SHIIRE_NAME;
            } else {
                sipr7020Report.h01_taisho = OUT_TAISO_ALL_SHOP_NAME;
            }
            
            sipr7020Report.m01_cus_shop_cd = sijp7020Dto.getTorihikiCd();
            sipr7020Report.m01_tri_nm = sijp7020Dto.getTorihikiNm();
            sipr7020Report.m01_dry_no = sijp7020Dto.getDpyNo();
            sipr7020Report.m01_gyo_no = pLineNo.toString();
            sipr7020Report.m01_shn_cd = plucnv.toDispCode(sijp7020Dto.getJanCd(), null);
            sipr7020Report.m01_cbnr_cd = sijp7020Dto.getCbnrCd();
            sipr7020Report.m01_sbnr_cd = sijp7020Dto.getSbnrCd();
            sipr7020Report.m01_jisha_hinban = sijp7020Dto.getJishaHinban();
            sipr7020Report.m01_shn_nm = sijp7020Dto.getShnNm();
            sipr7020Report.m01_mkr_cd = sijp7020Dto.getMkrCd();
            sipr7020Report.m01_size = sijp7020Dto.getSizeCd();
            sipr7020Report.m01_color = sijp7020Dto.getColorCd();
            sipr7020Report.m01_suryo = sijp7020Dto.getSuryo();
            sipr7020Report.m01_baik_kin = sijp7020Dto.getBaikKin();
            
            csvBean.add(sipr7020Report);
        }
        // ＣＳＶ作成（データ出力）
        try {
            csvManager.save(csvBean, Sipr7020Report.class).to(new File(cru.getCsvFilePath()),
                    CCReportUtil.CSV_OUTPUT_ENCODING);
        } catch (Exception e) {
            throw new ChaseException(e);
        }
        
        cru.makePDF();
        Sijp7020ResultForm resultForm = new Sijp7020ResultForm();
        try {
            URL uPdfUrl = new URL(cru.getPdfUrl());
            resultForm.setType(csvBean.get(0).h01_taisho);
            resultForm.setPdfUrl(uPdfUrl);
        } catch (Exception e) {
            throw new ChaseException(e);
        }
        
        // gc対応
        cru = null;
        System.gc();
        return resultForm;
    }
    
    /**
     *  check exist input triCd in S001
     * @param kaisya 会社コード
     * @param jigyoubu 事業部コード
     * @param tenCds 店舗コード
     * @param triCd 取引先コード
     * @return true if exist data
     */
    private Boolean existTriCdS001(String kaisya, String jigyoubu, List<String> tenCds, String triCd) {
        S001eosdExample example = new S001eosdExample();
        Criteria criteria =
                example.createCriteria().andDeleteFlgEqualTo(DELETED_FLAG_NORMAL).andKaisyaCdEqualTo(kaisya);
        if (jigyoubu != null && !CCStringUtil.isEmpty(jigyoubu)) {
            criteria.andJigyoubuCdEqualTo(jigyoubu);
        }
        if (triCd != null && !CCStringUtil.isEmpty(triCd)) {
            criteria.andTorihikiCdEqualTo(triCd);
        }
        if (tenCds != null && tenCds.size() > 0) {
            criteria.andTenCdIn(tenCds);
        }
        
        List<S001eosd> list = this.s001eosdMapper.selectByExample(example);
        if (list.size() > 0) {
            return true;
        }
        return false;
    }
    
    /**
     * check exist input triCd in S002
     * @param kaisya 会社コード
     * @param jigyoubu 事業部コード
     * @param tenCds 店舗コード
     * @param triCd 取引先コード
     * @return true if exist data
     */
    private Boolean existTriCdS002(String kaisya, String jigyoubu, List<String> tenCds, String triCd) {
        S002tgkdExample example = new S002tgkdExample();
        jp.co.necsoft.web_md.core.common.mybatis.dto.S002tgkdExample.Criteria criteria =
                example.createCriteria().andDeleteFlgEqualTo(DELETED_FLAG_NORMAL).andKaisyaCdEqualTo(kaisya);
        if (jigyoubu != null && !CCStringUtil.isEmpty(jigyoubu)) {
            criteria.andJigyoubuCdEqualTo(jigyoubu);
        }
        if (triCd != null && !CCStringUtil.isEmpty(triCd)) {
            criteria.andTorihikiCdEqualTo(triCd);
        }
        if (tenCds != null && tenCds.size() > 0) {
            criteria.andTenCdIn(tenCds);
        }
        
        List<S002tgkd> list = this.s002tgkdMapper.selectByExample(example);
        if (list.size() > 0) {
            return true;
        }
        return false;
    }
}
