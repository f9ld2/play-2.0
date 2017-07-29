// /////////////////////////////////////////////////////////////////////
// Copyright(C) 2015 NEC Solution Innovators, Ltd. All Rights Reserved.
// /////////////////////////////////////////////////////////////////////

/*
 * ====================================================================
 * 
 * 機能名称 : 修理品明細一覧
 * 
 * 
 * 改版履歴
 * 
 * Rev. 改版年月日 改版者名 内容
 * 
 * 1.0 2015-06-22 chiennt 新規作成
 * 
 * ===================================================================
 */

package jp.co.necsoft.web_md.core.app.controllers.ur;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import jp.co.necsoft.web_md.common.ChaseException;
import jp.co.necsoft.web_md.common.biz.IPluConverter;
import jp.co.necsoft.web_md.common.mybatis.dao.MyBatisSqlMapDao;
import jp.co.necsoft.web_md.core.app.dto.ha.M007CodeMaster;
import jp.co.necsoft.web_md.core.app.dto.ur.Urjp7030Param;
import jp.co.necsoft.web_md.core.app.dto.ur.Urjp7030Result;
import jp.co.necsoft.web_md.core.app.file.report.Urpr7030Report;
import jp.co.necsoft.web_md.core.app.forms.ur.Urjp7030CondForm;
import jp.co.necsoft.web_md.core.app.forms.ur.Urjp7030ResultForm;
import jp.co.necsoft.web_md.core.common.CCMessageConst;
import jp.co.necsoft.web_md.core.common.CCSecurityAuthenticator;
import jp.co.necsoft.web_md.core.common.CCSystemContext;
import jp.co.necsoft.web_md.core.common.forms.ErrorInfo;
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
 * 修理品明細一覧のControllerクラス
 * 
 */
@Security.Authenticated(CCSecurityAuthenticator.class)
public class Urjp7030Ctrl extends Controller {    

    /** プログラムＩＤ */
    private static final String PROGRAM_ID = "URPR7030";

    /** バージョン */
    private static final String PROGRAM_VERSION = "1.0";
    
    /** format date*/
    private static final String FORMAT_DATE = "yyyy/MM/dd";
    /** max snhCd record*/
    private static final int MAX_SNHCD_RECORD = 10;
    /** 共通エラーレスポンスクラス */
    @Inject
    private ErrorResponse errRes;

    /** 共通システム・コンテキストクラス */
    @Inject
    private CCSystemContext context;

    /** ccCheckExistsUtil */
    @Inject
    private CCCheckExistsUtil ccCheckExistsUtil;
    
    /** MyBatisSqlMapDaoクラス */
    @Inject
    private MyBatisSqlMapDao dao;
    
    /** IPlu Converter */
    @Inject
    private IPluConverter plucnv;

    /** Data Util */
    @Inject
    private DataUtil dataUtil;
    
    /**
     * ページを初期化する
     * 
     * @return クライアントへ返却する結果
     */
    public Result init() {
        
        Urjp7030CondForm form = new Urjp7030CondForm();
        String sUnyoDate = context.getUnyoDate();
        String kaisyaCd = context.getDefaultKaisyaCd();
        String jigyobuCd = dataUtil.getNewestJigyobuCd(kaisyaCd, sUnyoDate);
        // 画面項目[事業部]の設定
        form.setJigyobuCd(jigyobuCd);
        // 画面項目[店舗]の設定
        form.setTenCd(dataUtil.getNewestTenCd(kaisyaCd, jigyobuCd, sUnyoDate));

        List<Urjp7030CondForm> result = new ArrayList<Urjp7030CondForm>();
        result.add(form);
        return ok(Json.toJson(result));
    }
    
    /**
    * Get Report Data from DB
    * @param jigyobuCd 事業部
    * @param tenCd 店舗
    * @param calDateSt 伝票日付開始
    * @param calDateEd 伝票日付終了
    * @return クライアントへ返却する結果
    */
    public Result query(String jigyobuCd, String tenCd, String calDateSt,
            String calDateEd) {
        
        Form<Urjp7030CondForm> emptyForm = new Form<Urjp7030CondForm>(
                Urjp7030CondForm.class);
        Form<Urjp7030CondForm> reqForm = emptyForm.bindFromRequest();
        
        if (reqForm.hasErrors()) {

            errRes.addErrorInfo(CCMessageConst.MSG_KEY_VALIDATION_ERROR);
            errRes.addErrorInfo(reqForm.errors());
            return badRequest(Json.toJson(errRes));

        } else {

            Urjp7030CondForm req = reqForm.get();
            req.setCalDateSt(calDateSt);
            req.setCalDateEd(calDateEd);
            req.setJigyobuCd(jigyobuCd);
            req.setTenCd(tenCd);

            boolean flag = doCheckData(req);

            // case of input data invalid
            if (!flag) {
                ErrorInfo err = errRes
                        .getErrorInfo(CCMessageConst.MSG_KEY_VALIDATION_ERROR);
                errRes.getErrors().add(0, err);
                return badRequest(Json.toJson(errRes));
            }

            Urjp7030Param param1 = new Urjp7030Param();
            
            param1.setJigyobuCd(jigyobuCd);
            param1.setTenCd(tenCd);
            param1.setCalDateSt(calDateSt);
            param1.setCalDateEd(calDateEd);
            param1.setDpyKbn(req.getDpyKbn());
            param1.setIdoDenKbn(req.getIdoDenKbn());
            
            List<Urjp7030Result> resultList = dao.selectMany("selectUrjp7030",
                    param1, Urjp7030Result.class);

            if (resultList.size() == 0) {
                return notFound();
            }

            CCReportUtil cru = new CCReportUtil("URSV7030", context);
            CsvManager csvManager = CsvManagerFactory.newCsvManager();
            List<Urpr7030Report> csvBean = new ArrayList<Urpr7030Report>();

            String tantoCode = context.getTantoCode();
            String unyoDate = context.getUnyoDate();
            
            for (Urjp7030Result resultData : resultList) {
                // get data records base column [商品コード０１] -> [商品コード１０]
                for (int i = 1; i <= MAX_SNHCD_RECORD; i++) {

                    String inx = CCStringUtil.suppZero(String.valueOf(i), 2);
                    Object shnCd = DataUtil.getValueByMethodName(resultData,
                            "getShnCd" + inx);

                    if (shnCd == null || CCStringUtil.isEmpty(String.valueOf(shnCd))) {
                        continue;
                    }

                    Object genkKin = DataUtil.getValueByMethodName(resultData,
                            "getKenGenkKin" + inx);
                    Object baikKin = DataUtil.getValueByMethodName(resultData,
                            "getKenBaikKin" + inx);

                    // 商品ｺｰﾄﾞ格納
                    String sShnCd = String.valueOf(shnCd);
                    // 検収原価金額
                    String sGenkKin = String.valueOf(genkKin);
                    // 検収売価金額
                    String sBaikKin = String.valueOf(baikKin);
                    // 商品名漢字
                    String sShnNm = "";

                    Urpr7030Report row = new Urpr7030Report();
                    row.h00_progid = '"' + PROGRAM_ID + '"';
                    row.h00_version = '"' + PROGRAM_VERSION + '"';
                    row.h01_tanto_code = '"' + tantoCode + '"';
                    row.h01_jigyobu_cd = '"' + resultData.getJigyoubuCd() + '"';
                    row.h01_jigyobu_nm = '"' + resultData.getJgyNm() + '"';
                    row.h01_ten_cd = '"' + resultData.getTenCd() + '"';
                    row.h01_ten_nm = '"' + resultData.getTenNm() + '"';
                    row.h01_den_kbn = '"' + req.getDpyKbn() + '"';
                    row.h01_den_kbn_nm = '"' + resultData.getDenKbnNm() + '"';
                    row.h01_dnp_sub_kbn = '"' + req.getIdoDenKbn() + '"';
                    row.h01_dnp_sub_kbn_nm = '"' + resultData.getDnpSubKbnNm() + '"';
                    row.m00_nhn_ymd = '"' + CCDateUtil.getDateFormat(
                            resultData.getNhnYmd(), FORMAT_DATE) + '"';
                    row.m00_dpy_no = '"' + resultData.getDpyNo() + '"';
                    row.m00_tekiyo = '"' + resultData.getTekiyo() + '"';
                    row.m00_torihiki_cd = '"' + resultData.getTorihikiCd() + '"';
                    row.m00_tri_nm = '"' + resultData.getTriNm() + '"';
                    row.m00_shn_cd = '"' + plucnv.toDispCode(sShnCd, null) + '"';
                    row.m00_ken_genk_kin = '"' + sGenkKin + '"';
                    row.m00_ken_baik_kin = '"' + sBaikKin + '"';
                    
                    Urjp7030Param param2 = new Urjp7030Param();
                    param2.setShnCd(plucnv.toDbCode(sShnCd));
                    param2.setUnyoDate(unyoDate);
                    
                    // get [M007基本情報マスタ]リスト
                    List<M007CodeMaster> m007List = dao.selectMany(
                            "selectURJP7030M007", param2,
                            M007CodeMaster.class);
                    // case of exists [M007基本情報]
                    if (!m007List.isEmpty()) {
                        sShnNm = m007List.get(0).getShnNm();
                    }

                    row.m00_shn_nm = '"' + sShnNm + '"';

                    csvBean.add(row);
                }

            }

            // ＣＳＶ作成（データ出力）
            try {
                csvManager.save(csvBean, Urpr7030Report.class).to(
                        new File(cru.getCsvFilePath()),
                        CCReportUtil.CSV_OUTPUT_ENCODING);
            } catch (Exception e) {
                throw new ChaseException(e);
            }

            cru.makePDF();
            List<Urjp7030ResultForm> reportList = new ArrayList<Urjp7030ResultForm>();
            Urjp7030ResultForm resultForm = new Urjp7030ResultForm();
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
     * 
     * @param req req
     * @return true or false
     */
    private boolean doCheckData(Urjp7030CondForm req) {
        
        String unyoDate = context.getUnyoDate();
        String kaisyCd = req.getKaisyaCd();
        String jigyobuCd = req.getJigyobuCd();
        String tenCd = req.getTenCd();
        String calDateSt = req.getCalDateSt();
        String calDateEd = req.getCalDateEd();
        
        // 修理品登録日Fromチェック
        if (!CCDateUtil.checkFormatDate("calDateSt", calDateSt, errRes)) {
            return false;
        }
        
        // 修理品登録日Toチェック
        if (!CCDateUtil.checkFormatDate("calDateEd", calDateEd, errRes)) {
            return false;
        }
        
        if (!CCDateUtil.checkDateFromTo("calDateSt", calDateSt, calDateEd, errRes)) {
            return false;
        }
        
        // 事業部チェック
        if (!ccCheckExistsUtil.existsJigyobuCd("jigyobuCd", unyoDate, kaisyCd, jigyobuCd, errRes)) {
            return false;
        }

        // 店舗チェック
        if (!ccCheckExistsUtil.existsTenCd("tenCd", unyoDate, kaisyCd + jigyobuCd + tenCd, errRes)) {
            return false;
        }

        // 伝票区分チェック
        if (!CCStringUtil.isEmpty(req.getDpyKbn())
                && !ccCheckExistsUtil.existsDpyKbn("dpyKbn", req.getDpyKbn(), errRes)) {
            return false;
        }

        // 手書入力区分チェック
        if (!CCStringUtil.isEmpty(req.getIdoDenKbn())
                && !ccCheckExistsUtil.existsSubDpyKbn("idoDenKbn", req.getDpyKbn(), req.getIdoDenKbn(), errRes)) {
            return false;
        }
        
        return true;
    }

}
