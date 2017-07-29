// /////////////////////////////////////////////////////////////////////
// Copyright(C) 2014 NEC Soft, Ltd. All Rights Reserved.
// /////////////////////////////////////////////////////////////////////

/*
 * ====================================================================
 *
 * 機能名称 : 月次・日割予算チェックリスト
 *
 * 改版履歴
 *
 * Rev. 改版年月日 改版者名 内容
 *
 * 1.0 2014.07.01 NES石井 新規作成
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
import jp.co.necsoft.web_md.common.mybatis.dao.MyBatisSqlMapDao;
import jp.co.necsoft.web_md.core.app.dto.ur.Urjp1180Dto;
import jp.co.necsoft.web_md.core.app.dto.ur.Urjp1180SQLParameter;
import jp.co.necsoft.web_md.core.app.file.report.Urpr1180Report;
import jp.co.necsoft.web_md.core.app.forms.ur.Urjp1180ResultForm;
import jp.co.necsoft.web_md.core.common.CCMessageConst;
import jp.co.necsoft.web_md.core.common.CCSecurityAuthenticator;
import jp.co.necsoft.web_md.core.common.CCSystemContext;
import jp.co.necsoft.web_md.core.common.biz.si.CCSICommon;
import jp.co.necsoft.web_md.core.common.forms.ErrorInfo;
import jp.co.necsoft.web_md.core.common.forms.ErrorResponse;
import jp.co.necsoft.web_md.core.common.mybatis.dao.M000kaimMapper;
import jp.co.necsoft.web_md.core.common.mybatis.dao.M001jgymMapper;
import jp.co.necsoft.web_md.core.common.mybatis.dto.M000kaim;
import jp.co.necsoft.web_md.core.common.mybatis.dto.M000kaimExample;
import jp.co.necsoft.web_md.core.common.mybatis.dto.M001jgym;
import jp.co.necsoft.web_md.core.common.mybatis.dto.M001jgymExample;
import jp.co.necsoft.web_md.core.common.utils.CCCheckExistsUtil;
import jp.co.necsoft.web_md.core.common.utils.CCDateUtil;
import jp.co.necsoft.web_md.core.common.utils.CCNumberUtil;
import jp.co.necsoft.web_md.core.common.utils.CCReportUtil;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;

import com.orangesignal.csv.manager.CsvManager;
import com.orangesignal.csv.manager.CsvManagerFactory;

@Security.Authenticated(CCSecurityAuthenticator.class)
public class Urjp1180Ctrl extends Controller {
    @Inject
    private MyBatisSqlMapDao dao;
    @Inject
    private ErrorResponse errRes;
    @Inject
    private CCSystemContext context;
    @Inject
    private CCCheckExistsUtil ccCheckExistsUtil;
    @Inject
    private M000kaimMapper m000kaimMapper;
    @Inject
    private M001jgymMapper m001jgymMapper;
    /** INPUT_KAISYA_CD */
    private static final String INPUT_KAISYA_CD = "kaisyaCd";
    /** INPUT_JIGYOBU_CD */
    private static final String INPUT_JIGYOBU_CD = "jigyobuCd";
    /** INPUT_TAISYODATE_Y */
    private static final String INPUT_TAISYODATE_Y = "taisyodateY";
    /** INPUT_TAISYODATE_M */
    private static final String INPUT_TAISYODATE_M = "taisyodateM";
    /** 店舗コード開始値 */
    private static final String TEN_CD_FR = "000";
    /** 店舗コード終了値 */
    private static final String TEN_CD_TO = "999";
    /** 月 開始日 */
    private static final int MONTH_FR = 1;
    /** 月 終了日 */
    private static final int MONTH_TO = 12;
    /** 日 開始日 */
    private static final String DAY_FR = "01";
    /** 日 終了日 */
    private static final String DAY_TO = "31";
    /** 年 桁数 */
    private static final int YEAR_LENGTH = 4;
    /** 月 桁数 */
    private static final int MONTH_LENGTH = 2;
    /** EMPTY_STRING */
    private static final String EMPTY_STRING = "＊＊＊＊＊";
    /** PROG_ID */
    private static final String PROG_ID = "URJP1180";
    /** PROG_ID_PDF */
    private static final String PROG_ID_PDF = "URSV1180";
    /** VERSION */
    private static final String VERSION = "1.00";
    /** ダミー店舗コード */
    private static final String KEY_DUMMY_TEN_CD = "urjp1180.dummy.ten_cd";

    /**
     * 引数とリクエスト(パラメータ)を条件にDBからデータ(複数件)を検索し返却する。
     * @param kaisyaCd 会社名
     * @param jigyobuCd 事業部名
     * @param taisyodateY 年
     * @param taisyodateM 月
     * @return クライアントへ返却する結果
     */
    public Result query(String kaisyaCd, String jigyobuCd, String taisyodateY, String taisyodateM) {

        /**
         * キー項目チェック.
         **/
        boolean isError = false;
        String unyobi = context.getUnyoDate();

        // 会社存在チェック
        if (!ccCheckExistsUtil.existsKaisyaCd(unyobi, kaisyaCd)) {
            errRes.addErrorInfo(INPUT_KAISYA_CD, CCMessageConst.MSG_KEY_NOT_EXIST_REGISTERED);
            isError = true;
        } else {
            // 事業部存在チェック
            if (!ccCheckExistsUtil.existsKaisyaCdAndJigyobuCd(unyobi, kaisyaCd, jigyobuCd)) {
                errRes.addErrorInfo(INPUT_JIGYOBU_CD, CCMessageConst.MSG_KEY_NOT_EXIST_REGISTERED);
                isError = true;
            }
        }

        // 対象年チェック
        if (!isYear(taisyodateY)) {
            errRes.addErrorInfo(INPUT_TAISYODATE_Y, CCMessageConst.MSG_KEY_DATE_ERROR);
            isError = true;
        }

        // 対象月チェック
        if (!isMonth(taisyodateM)) {
            errRes.addErrorInfo(INPUT_TAISYODATE_M, CCMessageConst.MSG_KEY_DATE_ERROR);
            isError = true;
        }

        // 入力データチェック
        if (isError) {
            ErrorInfo error = errRes.getErrorInfo(CCMessageConst.MSG_KEY_VALIDATION_ERROR);
            errRes.getErrors().add(0, error);
            return badRequest(Json.toJson(errRes));
        }

        // 店舗コード範囲取得
        String tenpoFrom = kaisyaCd + jigyobuCd + TEN_CD_FR;
        String tenpoTo   = kaisyaCd + jigyobuCd + getTenpoTo();

        // 当月の開始終了日を取得
        String dayDateFrom = taisyodateY + taisyodateM + DAY_FR;
        String dayDateTo = taisyodateY + taisyodateM + DAY_TO;

        // 累計開始日（年度開始日）取得
        String ruiDateFromYear = null;
        String ruiDateFromMonth = getRuiMonthFrom();
        // 対象月が、累計開始日(年度開始日)より前だった場合、前年を取得
        if (Integer.parseInt(taisyodateM) < Integer.parseInt(ruiDateFromMonth)){
            ruiDateFromYear = String.valueOf((Integer.parseInt(taisyodateY)-1)) ;
        } else {
            ruiDateFromYear = taisyodateY;
        }
        String ruiMonFrom = ruiDateFromYear + ruiDateFromMonth;
        String ruiDateFrom = ruiDateFromYear + ruiDateFromMonth + DAY_FR;

        // 累計終了日（指定年月の月末日）を取得
        String ruiDateTo = taisyodateY + taisyodateM + DAY_TO;

        // SQLParameterにセット
        Urjp1180SQLParameter parameter = new Urjp1180SQLParameter();
        parameter.setKaisyaCd(kaisyaCd);
        parameter.setJigyobuCd(jigyobuCd);
        parameter.setTaisyodateY(taisyodateY);
        parameter.setTaisyodateM(taisyodateM);
        parameter.setTenpoFrom(tenpoFrom);
        parameter.setTenpoTo(tenpoTo);
        parameter.setRuiMonFrom(ruiMonFrom);
        parameter.setRuiDateFrom(ruiDateFrom);
        parameter.setRuiDateTo(ruiDateTo);
        parameter.setDayDateFrom(dayDateFrom);
        parameter.setDayDateTo(dayDateTo);

        List<Urjp1180Dto> urjp1180dtos = dao.selectMany("selectUrjp1180", parameter, Urjp1180Dto.class);
        if (urjp1180dtos.size() == 0) {
            return notFound();
        } else {
            // CSV作成（データ取得）
            CCReportUtil cru = new CCReportUtil(PROG_ID_PDF, context);
            CsvManager csvManager = CsvManagerFactory.newCsvManager();
            List<Urpr1180Report> csvBean = new ArrayList<Urpr1180Report>();

            String tantoCode = context.getTantoCode();
            String kaisyaName = getKaisyaName(getGetumatuDate(taisyodateY, taisyodateM), kaisyaCd);
            String jigyobuName = getJigyobuName(getGetumatuDate(taisyodateY, taisyodateM), kaisyaCd, jigyobuCd);

            for (Urjp1180Dto urjp1180dto : urjp1180dtos) {
                Urpr1180Report urpr1180Report = new Urpr1180Report();
                urpr1180Report.h00_progid = PROG_ID;
                urpr1180Report.h00_version = VERSION;
                urpr1180Report.h01_tanto_code = tantoCode;
                urpr1180Report.h01_kaisya_code = kaisyaCd;
                urpr1180Report.h01_kaisya_name = kaisyaName;
                urpr1180Report.h02_jigyobu_code = jigyobuCd;
                urpr1180Report.h02_jigyobu_name = jigyobuName;
                urpr1180Report.m00_ysn_year = taisyodateY;
                urpr1180Report.m00_ysn_month = taisyodateM;
                urpr1180Report.m00_str_code = urjp1180dto.getTenCd();
                urpr1180Report.m00_str_name = urjp1180dto.getTenNm();
                urpr1180Report.m00_ysn_uri_kin_d = urjp1180dto.getYsnUriKinD();
                urpr1180Report.m00_ysn_uri_kin_m = urjp1180dto.getYsnUriKinM();
                urpr1180Report.m00_ysn_kyaku_d = urjp1180dto.getYsnKyaksuD();
                urpr1180Report.m00_ysn_kyaku_m = urjp1180dto.getYsnKyaksuM();
                urpr1180Report.m00_ysn_uri_kin_d_r = urjp1180dto.getYsnUriKinDR();
                urpr1180Report.m00_ysn_uri_kin_m_r = urjp1180dto.getYsnUriKinMR();

                csvBean.add(urpr1180Report);
            }

            // CSV作成（データ出力）
            try {
                csvManager.save(csvBean, Urpr1180Report.class).to(new File(cru.getCsvFilePath()),
                        CCReportUtil.CSV_OUTPUT_ENCODING);
            } catch (Exception e) {
                throw new ChaseException(e);
            }

            cru.makePDF();
            Urjp1180ResultForm resultForm = new Urjp1180ResultForm();
            try {
                URL uPdfUrl = new URL(cru.getPdfUrl());
                resultForm.setPdfUrl(uPdfUrl);
            } catch (Exception e) {
                throw new ChaseException(e);
            }
            // gc対応
            cru = null;
            System.gc();

            return ok(Json.toJson(resultForm));
        }
    }


    /**
     * 会社名称取得処理.
     *
     * @param date 検索日
     * @param kaisyaCd 会社
     * @return String 会社名
     **/
    private String getKaisyaName(String date, String kaisyaCd) {
        M000kaimExample example = new M000kaimExample();
        example.createCriteria().andKaisyaCdEqualTo(kaisyaCd);
        example.setSearchDate(date);

        List<M000kaim> m000kaims = m000kaimMapper.selectByExample(example);

        // 取得データ数ゼロの場合、未登録
        if (m000kaims.size() == 0 || CCSICommon.TYPE_ACT_KBN_DELETED.equals(m000kaims.get(0).getActKbn())) {
            return EMPTY_STRING;
        }

        // 会社名を取得
        return m000kaims.get(0).getKaiNm();
    }


    /**
     * 事業部名称取得処理.
     *
     * @param date 検索日
     * @param kaisyaCd 会社
     * @param jigyobuCd 事業部
     * @return String 事業部名
     **/
    private String getJigyobuName(String date, String kaisyaCd, String jigyobuCd) {
        M001jgymExample example = new M001jgymExample();
        example.createCriteria().andKaisyaCdEqualTo(kaisyaCd).andJigyobuCdEqualTo(jigyobuCd);
        example.setSearchDate(date);

        List<M001jgym> m001jgyms = m001jgymMapper.selectByExample(example);

        // 取得データ数ゼロの場合、未登録
        if (m001jgyms.size() == 0 || CCSICommon.TYPE_ACT_KBN_DELETED.equals(m001jgyms.get(0).getActKbn())) {
            return EMPTY_STRING;
        }

        // 事業部名を取得
        return m001jgyms.get(0).getJgyNm();
    }

    /**
     * 指定年月の月末日を取得する.
     *
     * @param taisyodateY 年
     * @param taisyodateM 月
     * @return String 指定年月の月末日
     **/
    private String getGetumatuDate(String taisyodateY, String taisyodateM) {
        int getumatuDate = CCDateUtil.getLastDay(Integer.parseInt(taisyodateY), Integer.parseInt(taisyodateM));
        return taisyodateY + taisyodateM + String.valueOf(getumatuDate);
    }


    /**
     * 店舗コード 開始値を取得する.
     * @param なし
     * @return String 店舗コード 開始値
     **/
    private String getTenpoTo() {
        String tenpoTo = null;

        String dammyTenpo = context.getContextProperty(KEY_DUMMY_TEN_CD);
        if (dammyTenpo == "") {
            tenpoTo = TEN_CD_TO;
        } else {
            tenpoTo = dammyTenpo;
        }

        return tenpoTo;
    }

    /**
     * 累計開始月（年度開始月）取得処理.
     * @param なし
     * @return String "04"
     */
    private String getRuiMonthFrom() {
        return "04";
    }

    /**
     * 対象年チェック
     * @param taisyodateY 年
     * @return true:正常 false:異常
     */
    private boolean isYear(String taisyodateY) {

        // 桁数チェック
        if(taisyodateY.length() != YEAR_LENGTH){
            return false;
        }

        // 数値チェック
        if (!CCNumberUtil.isNumeric(taisyodateY)) {
            return false;
        }

        return true;
    }

    /**
     * 対象月チェック
     * @param taisyodateM 月
     * @return true:正常 false:異常
     */
    private boolean isMonth(String taisyodateM) {

        // 桁数チェック
        if(taisyodateM.length() != MONTH_LENGTH){
            return false;
        }

        // 数値チェック
        if (!CCNumberUtil.isNumeric(taisyodateM)) {
            return false;
        }

        // 月範囲チェック
        int intMonth = Integer.parseInt(taisyodateM);
        if (intMonth <= MONTH_FR || intMonth > MONTH_TO) {
            return false;
        }

        return true;
    }
}

