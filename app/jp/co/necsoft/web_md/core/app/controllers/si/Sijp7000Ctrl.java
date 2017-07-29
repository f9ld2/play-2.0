// /////////////////////////////////////////////////////////////////////
// Copyright(C) 2015 NEC Solution Innovators, Ltd. All Rights Reserved.
// /////////////////////////////////////////////////////////////////////

/*
 * ====================================================================
 * 
 * 機能名称 : 仕入状況一覧表
 * 
 * 改版履歴
 * 
 * Rev. 改版年月日 改版者名 内容
 * 
 * 1.0 2015.06.04 NECVN 新規作成
 * 
 * ===================================================================
 */

package jp.co.necsoft.web_md.core.app.controllers.si;

import java.io.File;
import java.math.BigDecimal;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.inject.Inject;

import jp.co.necsoft.web_md.common.ChaseException;
import jp.co.necsoft.web_md.common.mybatis.dao.MyBatisSqlMapDao;
import jp.co.necsoft.web_md.core.app.dto.si.Sijp7000Dto;
import jp.co.necsoft.web_md.core.app.dto.si.Sijp7000Dto1;
import jp.co.necsoft.web_md.core.app.dto.si.Sijp7000Param;
import jp.co.necsoft.web_md.core.app.file.report.Sipr7000Report;
import jp.co.necsoft.web_md.core.app.forms.si.Sijp7000CondForm;
import jp.co.necsoft.web_md.core.app.forms.si.Sijp7000ResultForm;
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
 * 仕入状況一覧表のControllerクラス
 *
 */
@Security.Authenticated(CCSecurityAuthenticator.class)
public class Sijp7000Ctrl extends Controller {
     /** length of BmnCd  */
    private static final Integer BMN_CD_LENGTH = 5;
    /** REPORT ROW NUMBER */
    private static final Integer REPORT_ROW_NUMBER = 10;
    
    /** 発注種類区分本部 */
    private static final String HATCHU_SHURUI_KUBUN_HONBU = "13";
    /** 発注種類区分 店舗 */
    private static final String HATCHU_SHURUI_KUBUN_TENPO = "12";
    /** dao */
    @Inject
    private MyBatisSqlMapDao dao;
    /** errRes */
    @Inject
    private ErrorResponse errRes;
    /** context */
    @Inject
    private CCSystemContext context;
    /** check exist util */
    @Inject
    private CCCheckExistsUtil ccCheckExistsUtil;
    /** Data Util */
    @Inject
    private DataUtil dataUtil;
    
    /** M005bnrm Mapper */
    
    /**
     * ページを初期化する
     * 
     * @return クライアントへ返却する結果
     */
    public Result init() {
        Sijp7000CondForm form = new Sijp7000CondForm();
        String sUnyoDate = context.getUnyoDate();
        String kaisyaCd = context.getDefaultKaisyaCd();
        String jigyobuCd = dataUtil.getNewestJigyobuCd(kaisyaCd, sUnyoDate);
        if (!jigyobuCd.isEmpty()) {
            // 画面項目[事業部]の設定
            form.setJigyobuCd(jigyobuCd);
            // 画面項目[店舗]の設定
            form.setTenCd(dataUtil.getNewestTenCd(kaisyaCd, jigyobuCd, sUnyoDate));
        }
        // 画面項目[取引先]の設定
        form.setTorihikiCd(dataUtil.getNewestTriCd(sUnyoDate));
        
        List<Sijp7000CondForm> result = new ArrayList<Sijp7000CondForm>();
        result.add(form);
        
        return ok(Json.toJson(result));
    }
    
    /**
     * 引数とリクエスト(パラメータ)を条件にDBからデータ(複数件)を検索し返却する。
     * @param nhnYoteiYmdFr 納品日
     * @param nhnYoteiYmdTo 納品日終了
     * @return クライアントへ返却する結果
     */
    
    public Result query(String nhnYoteiYmdFr, String nhnYoteiYmdTo) {
        Form<Sijp7000CondForm> emptyForm = new Form<Sijp7000CondForm>(Sijp7000CondForm.class);
        Form<Sijp7000CondForm> reqForm = emptyForm.bindFromRequest();
        if (reqForm.hasErrors()) {
            errRes.addErrorInfo(CCMessageConst.MSG_KEY_VALIDATION_ERROR);
            errRes.addErrorInfo(reqForm.errors());
            return badRequest(Json.toJson(errRes));
        } else {
            Sijp7000CondForm req = reqForm.get();
            
            req.setNhnYoteiYmdFr(nhnYoteiYmdFr);
            req.setNhnYoteiYmdTo(nhnYoteiYmdTo);
            
            if (req.getTenCd() == null) {
                req.setTenCd(StringUtils.EMPTY);
            }
            
            // 担当者コードを取得します。
            String tantoCode = context.getTantoCode();
            
            // 入力データチェック
            if (!this.checkCond(req)) {
                return badRequest(Json.toJson(errRes));
            }
            
            Sijp7000Param sijp7000Param = new Sijp7000Param();
            BeanUtils.copyProperties(req, sijp7000Param);
            sijp7000Param.setUnyoDate(context.getUnyoDate());
            
            // S001ＥＯＳ伝票累積Ｆから検索条件に沿ったデータを抽出する。
            List<Sijp7000Dto> sijp7000Dtos =
                    dao.selectMany("selectSijp7000ReportData", sijp7000Param, Sijp7000Dto.class);
            
            // データが存在しない場合
            if (sijp7000Dtos.size() == 0) {
                return notFound();
            }
            
            // ＣＳＶ作成（データ取得）
            CCReportUtil cru = new CCReportUtil("SISV7000", context);
            CsvManager csvManager = CsvManagerFactory.newCsvManager();
            String unyoDate = context.getUnyoDate();
            
            TreeMap<String, Sijp7000Dto1> chuBnrMap = new TreeMap<String, Sijp7000Dto1>();
            for (Sijp7000Dto sijp7000Dto : sijp7000Dtos) {
                String pTenCd = sijp7000Dto.getTenCd();
                String pTenNm = sijp7000Dto.getTenNm();
                String pTrihikiCd = sijp7000Dto.getTrihikiCd();
                String pTrihikiNm = sijp7000Dto.getTrihikiNm();
                
                for (int i = 1; i <= REPORT_ROW_NUMBER; i++) {
                    String cbnrCd = DataUtil.getValueByMethodName(sijp7000Dto, "getCbnrCd" + i).toString();
                    if (CCStringUtil.isEmpty(cbnrCd)) {
                        continue;
                    }
                    String key = pTenCd + pTrihikiCd + cbnrCd;
                    Sijp7000Dto1 chuBnrObj = chuBnrMap.get(key);
                    
                    if (chuBnrObj == null) {
                        chuBnrObj = new Sijp7000Dto1();
                    }
                    
                    chuBnrObj.setTenCd(pTenCd);
                    chuBnrObj.setTenNm(pTenNm);
                    chuBnrObj.setTrihikiCd(pTrihikiCd);
                    chuBnrObj.setTrihikiNm(pTrihikiNm);
                    chuBnrObj.setCbnrCd(cbnrCd);
                    chuBnrObj.setBmnCd(sijp7000Dto.getBmnCd());
                    String kbn = DataUtil.getValueByMethodName(sijp7000Dto, "getHatSruiKbn" + i).toString();
                    
                    if (HATCHU_SHURUI_KUBUN_HONBU.equals(kbn)) {
                        BigDecimal hatBaraSu1 =
                                CCNumberUtil.addBigDecimal(chuBnrObj.getHatBaraSu1(),
                                        (BigDecimal) DataUtil.getValueByMethodName(sijp7000Dto, "getHatBaraSu" + i));
                        chuBnrObj.setHatBaraSu1(hatBaraSu1);
                        
                        BigDecimal kenBaraSu1 =
                                CCNumberUtil.addBigDecimal(chuBnrObj.getKenBaraSu1(),
                                        (BigDecimal) DataUtil.getValueByMethodName(sijp7000Dto, "getKenBaraSu" + i));
                        chuBnrObj.setKenBaraSu1(kenBaraSu1);
                        
                        Long hatGenkKin1 =
                                chuBnrObj.getHatGenkKin1().longValue()
                                        + ((Long) DataUtil.getValueByMethodName(sijp7000Dto, "getHatGenkKin" + i))
                                                .longValue();
                        chuBnrObj.setHatGenkKin1(hatGenkKin1);
                        
                        Long kenGenkKin1 =
                                chuBnrObj.getKenGenkKin1().longValue()
                                        + ((Long) DataUtil.getValueByMethodName(sijp7000Dto, "getKenGenkKin" + i))
                                                .longValue();
                        chuBnrObj.setKenGenkKin1(kenGenkKin1);
                        
                        Long hatBaikKin1 =
                                chuBnrObj.getHatBaikKin1().longValue()
                                        + ((Long) DataUtil.getValueByMethodName(sijp7000Dto, "getHatBaikKin" + i))
                                                .longValue();
                        chuBnrObj.setHatBaikKin1(hatBaikKin1);
                        
                        Long kenBaikKin1 =
                                chuBnrObj.getKenBaikKin1().longValue()
                                        + ((Long) DataUtil.getValueByMethodName(sijp7000Dto, "getKenBaikKin" + i))
                                                .longValue();
                        chuBnrObj.setKenBaikKin1(kenBaikKin1);
                    } else if (HATCHU_SHURUI_KUBUN_TENPO.equals(kbn)) {
                        BigDecimal hatBaraSu2 =
                                CCNumberUtil.addBigDecimal(chuBnrObj.getHatBaraSu2(),
                                        (BigDecimal) DataUtil.getValueByMethodName(sijp7000Dto, "getHatBaraSu" + i));
                        chuBnrObj.setHatBaraSu2(hatBaraSu2);
                        
                        BigDecimal kenBaraSu2 =
                                CCNumberUtil.addBigDecimal(chuBnrObj.getKenBaraSu2(),
                                        (BigDecimal) DataUtil.getValueByMethodName(sijp7000Dto, "getKenBaraSu" + i));
                        chuBnrObj.setKenBaraSu2(kenBaraSu2);
                        
                        Long hatGenkKin2 =
                                chuBnrObj.getHatGenkKin2().longValue()
                                        + ((Long) DataUtil.getValueByMethodName(sijp7000Dto, "getHatGenkKin" + i))
                                                .longValue();
                        chuBnrObj.setHatGenkKin2(hatGenkKin2);
                        
                        Long kenGenkKin2 =
                                chuBnrObj.getKenGenkKin2().longValue()
                                        + ((Long) DataUtil.getValueByMethodName(sijp7000Dto, "getKenGenkKin" + i))
                                                .longValue();
                        chuBnrObj.setKenGenkKin2(kenGenkKin2);
                        
                        Long hatBaikKin2 =
                                chuBnrObj.getHatBaikKin2().longValue()
                                        + ((Long) DataUtil.getValueByMethodName(sijp7000Dto, "getHatBaikKin" + i))
                                                .longValue();
                        chuBnrObj.setHatBaikKin2(hatBaikKin2);
                        
                        Long kenBaikKin2 =
                                chuBnrObj.getKenBaikKin2().longValue()
                                        + ((Long) DataUtil.getValueByMethodName(sijp7000Dto, "getKenBaikKin" + i))
                                                .longValue();
                        chuBnrObj.setKenBaikKin2(kenBaikKin2);
                    }
                    
                    chuBnrMap.put(key, chuBnrObj);
                }
            }
            
            // データが存在する場合
            List<Sipr7000Report> csvBean = new ArrayList<Sipr7000Report>();
            for (Map.Entry<String, Sijp7000Dto1> entry : chuBnrMap.entrySet()) {
                Sijp7000Dto1 dto = entry.getValue();
                if (this.isEmptyObject(dto)) {
                    continue;
                }
                Sipr7000Report sipr7000Report = new Sipr7000Report();
                
                sipr7000Report.h00_progid = "SIPR7000";
                sipr7000Report.h00_version = "1.00";
                sipr7000Report.h01_tanto_code = tantoCode;
                
                sipr7000Report.h01_ten_cd = dto.getTenCd();
                sipr7000Report.h01_ten_name = dto.getTenNm();
                sipr7000Report.h01_nhn_yotei_ymd_fr = sijp7000Param.getNhnYoteiYmdFr();
                sipr7000Report.h01_nhn_yotei_ymd_to = sijp7000Param.getNhnYoteiYmdTo();
                sipr7000Report.h01_torihiki_cd = dto.getTrihikiCd();
                sipr7000Report.h01_tri_nm = dto.getTrihikiNm();
                
                sipr7000Report.m01_cbnrCd = dto.getCbnrCd();
                sipr7000Report.m01_bnrNm =
                        dataUtil.getChuBnrName(unyoDate, dto.getBmnCd().substring(0, 2),
                                dto.getBmnCd().substring(2, BMN_CD_LENGTH), dto.getCbnrCd());
                
                sipr7000Report.m01_hat_bara_su1 = dto.getHatBaraSu1().toPlainString();
                sipr7000Report.m01_ken_bara_su1 = dto.getKenBaraSu1().toPlainString();
                sipr7000Report.m01_hat_genk_kin1 = dto.getHatGenkKin1().toString();
                sipr7000Report.m01_ken_genk_kin1 = dto.getKenGenkKin1().toString();
                sipr7000Report.m01_hat_baik_kin1 = dto.getHatBaikKin1().toString();
                sipr7000Report.m01_ken_baik_kin1 = dto.getKenBaikKin1().toString();
                
                sipr7000Report.m01_hat_bara_su2 = dto.getHatBaraSu2().toPlainString();
                sipr7000Report.m01_ken_bara_su2 = dto.getKenBaraSu2().toPlainString();
                sipr7000Report.m01_hat_genk_kin2 = dto.getHatGenkKin2().toString();
                sipr7000Report.m01_ken_genk_kin2 = dto.getKenGenkKin2().toString();
                sipr7000Report.m01_hat_baik_kin2 = dto.getHatBaikKin2().toString();
                sipr7000Report.m01_ken_baik_kin2 = dto.getKenBaikKin2().toString();
                
                csvBean.add(sipr7000Report);
            }
            
            // ＣＳＶ作成（データ出力）
            try {
                csvManager.save(csvBean, Sipr7000Report.class).to(new File(cru.getCsvFilePath()),
                        CCReportUtil.CSV_OUTPUT_ENCODING);
            } catch (Exception e) {
                throw new ChaseException(e);
            }
            
            cru.makePDF();
            List<Sijp7000ResultForm> reportList = new ArrayList<Sijp7000ResultForm>();
            Sijp7000ResultForm resultForm = new Sijp7000ResultForm();
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
    private Boolean checkCond(Sijp7000CondForm req) {
        /** basic check */
        // 発注日チェック
        if (!CCDateUtil.checkFormatDate("nhnYoteiYmdFr", req.getNhnYoteiYmdFr(), errRes)) {
            return false;
        }
        if (!CCDateUtil.checkFormatDate("nhnYoteiYmdTo", req.getNhnYoteiYmdTo(), errRes)) {
            return false;
        }
        
        /** logic check */
        // 事業部コードチェック
        if (!CCStringUtil.isEmpty(req.getJigyobuCd())) {
            // 存在チェック
            if (!ccCheckExistsUtil.existsJigyobuCd("jigyobuCd", context.getUnyoDate(), req.getKaisyaCd(),
                    req.getJigyobuCd(), errRes)) {
                return false;
            }
        }
        // 店舗コードチェック
        if (!CCStringUtil.isEmpty(req.getTenCd())) {
            // 存在チェック
            if (!ccCheckExistsUtil.existsTenCd("tenCd", context.getUnyoDate(), req.getKaisyaCd() + req.getJigyobuCd()
                    + req.getTenCd(), errRes)) {
                return false;
            }
        }
        // 発注日チェック
        if (!CCDateUtil.checkDateFromTo("nhnYoteiYmdFr", req.getNhnYoteiYmdFr(), req.getNhnYoteiYmdTo(), errRes)) {
            return false;
        }
        // 取引先コード
        if (!CCStringUtil.isEmpty(req.getTorihikiCd())) {
            // 存在チェック
            if (!ccCheckExistsUtil.existTriCd("torihikiCd", context.getUnyoDate(), req.getTorihikiCd(), errRes)) {
                return false;
            }
        }
        
        return true;
    }
    
    /**
     * check object Sijp7000Dto1 is empty or not
     *
     * @param dto object to check
     * @return value true if object is empty.
     */
    private Boolean isEmptyObject(Sijp7000Dto1 dto) {
        if (dto == null) {
            return true;
        }
        
        if (dto.getHatBaikKin1() == 0 && dto.getHatBaikKin2() == 0 && dto.getHatBaraSu1() == BigDecimal.ZERO
                && dto.getHatBaraSu2() == BigDecimal.ZERO && dto.getHatGenkKin1() == 0 && dto.getHatGenkKin2() == 0
                && dto.getKenBaikKin1() == 0 && dto.getKenBaikKin2() == 0 && dto.getKenBaraSu1() == BigDecimal.ZERO
                && dto.getKenBaraSu2() == BigDecimal.ZERO && dto.getKenGenkKin1() == 0
                && dto.getKenGenkKin2() == Long.valueOf(0)) {
            return true;
        }
        return false;
    }
}
