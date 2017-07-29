// /////////////////////////////////////////////////////////////////////
// Copyright(C) 2015 NEC Solution Innovators, Ltd. All Rights Reserved.
// /////////////////////////////////////////////////////////////////////

/*
 * ====================================================================
 * 
 * 機能名称 :日別予算入力画面
 * 
 * 改版履歴
 * 
 * Rev. 改版年月日 改版者名 内容
 * 
 * 1.0 2014.04.10 TuanTQ 新規作成
 * 
 * ===================================================================
 */

package jp.co.necsoft.web_md.core.app.controllers.ur;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import jp.co.necsoft.web_md.common.ChaseException;
import jp.co.necsoft.web_md.common.mybatis.dao.MyBatisSqlMapDao;
import jp.co.necsoft.web_md.core.app.dto.ur.M006tenmM016tanmResult;
import jp.co.necsoft.web_md.core.app.dto.ur.M019ymdmU001ybmnU012bmnjParam;
import jp.co.necsoft.web_md.core.app.dto.ur.M019ymdmU001ybmnU012bmnjResult;
import jp.co.necsoft.web_md.core.app.dto.ur.U001ybmnResult;
import jp.co.necsoft.web_md.core.app.dto.ur.U002ybmnM003bmnmM003bmnmM004Res;
import jp.co.necsoft.web_md.core.app.dto.ur.U002ybmnM003bmnmM004tbumResult;
import jp.co.necsoft.web_md.core.app.dto.ur.U011tenjM019ymdmU012bmnjResult;
import jp.co.necsoft.web_md.core.app.dto.ur.Urjp001001Dto;
import jp.co.necsoft.web_md.core.app.dto.ur.Urjp001002Dto;
import jp.co.necsoft.web_md.core.app.dto.ur.Urjp001003Dto;
import jp.co.necsoft.web_md.core.app.dto.ur.Urjp001004Dto;
import jp.co.necsoft.web_md.core.app.dto.ur.Urjp001005Dto;
import jp.co.necsoft.web_md.core.app.dto.ur.Urjp001006Dto;
import jp.co.necsoft.web_md.core.app.dto.ur.Urjp0010ResultForm;
import jp.co.necsoft.web_md.core.app.forms.ur.Urjp0010CondForm;
import jp.co.necsoft.web_md.core.common.CCMessageConst;
import jp.co.necsoft.web_md.core.common.CCSecurityAuthenticator;
import jp.co.necsoft.web_md.core.common.CCSystemContext;
import jp.co.necsoft.web_md.core.common.forms.ErrorResponse;
import jp.co.necsoft.web_md.core.common.mybatis.dao.M000kaimMapper;
import jp.co.necsoft.web_md.core.common.mybatis.dao.M001jgymMapper;
import jp.co.necsoft.web_md.core.common.mybatis.dao.M006tenmMapper;
import jp.co.necsoft.web_md.core.common.mybatis.dao.M019ymdmMapper;
import jp.co.necsoft.web_md.core.common.mybatis.dao.M020ctlmMapper;
import jp.co.necsoft.web_md.core.common.mybatis.dao.U001ybmnMapper;
import jp.co.necsoft.web_md.core.common.mybatis.dto.M000kaim;
import jp.co.necsoft.web_md.core.common.mybatis.dto.M000kaimExample;
import jp.co.necsoft.web_md.core.common.mybatis.dto.M001jgym;
import jp.co.necsoft.web_md.core.common.mybatis.dto.M001jgymExample;
import jp.co.necsoft.web_md.core.common.mybatis.dto.M006tenm;
import jp.co.necsoft.web_md.core.common.mybatis.dto.M006tenmExample;
import jp.co.necsoft.web_md.core.common.mybatis.dto.M019ymdm;
import jp.co.necsoft.web_md.core.common.mybatis.dto.M019ymdmExample;
import jp.co.necsoft.web_md.core.common.mybatis.dto.M020ctlm;
import jp.co.necsoft.web_md.core.common.mybatis.dto.M020ctlmExample;
import jp.co.necsoft.web_md.core.common.mybatis.dto.U001ybmn;
import jp.co.necsoft.web_md.core.common.mybatis.dto.U001ybmnExample;
import jp.co.necsoft.web_md.core.common.utils.CCDateUtil;
import jp.co.necsoft.web_md.core.common.utils.CCNumberUtil;
import jp.co.necsoft.web_md.core.common.utils.CCStringUtil;
import jp.co.necsoft.web_md.core.common.utils.DataUtil;

import org.joda.time.DateTime;
import org.mybatis.guice.transactional.Transactional;
import org.springframework.beans.BeanUtils;

import play.data.Form;
import play.i18n.Messages;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;

/**
*日別店別部門別予算入力のControllerクラス
*
*/
@Security.Authenticated(CCSecurityAuthenticator.class)
public class Urjp0010Ctrl extends Controller {
    /** BTN_NEXT_COPY */
    private static final String BTN_NEXT_COPY = "3";
    /** BTN_CALC */
    private static final String BTN_CALC = "2";
    /** BTN_COPY */
    private static final String BTN_COPY = "1";
    /**
     * 削除
     */
    private static final String TYPE_ACT_KBN_DELETED = "9";
    /**
     * 日別予算合計グリッド grid2 行数
     */
    private static final int GRD2_ROW = 2;
    /**
     * 部門別予算合計グリッド grdDetail4 行数  
     */
    private static final int GRD4_ROW = 1;
    /**
     * 日別部門別予算入力グリッド　見出し行数 
     */
    private static final int GRD6_HEAD_ROW = 4;
    /**
     * 日別部門別予算入力グリッド grdDetail6 行数
     */
    private static final int GRD6_ROW = 16;
    /**
     * DATETIME_FORMAT_DATE
     */
    private static final String DATETIME_FORMAT_DATE = "yyyyMMdd";
    /**
     * DATETIME_FORMAT_TIME
     */
    private static final String DATETIME_FORMAT_TIME = "HHmmss";
    /**
     * 対象事業部
     */
    private static final String JGY_CODE = "cc.app.ur.urjp0010_jgy";
    /**
     * ダミー店舗コード取得
     */
    private static final String DUMMY_CODE = "cc.app.ur.urjp0010_dummy_ten_cd";
    /**
     * プロパティファイル用キー定義
     */
    private static final String KEY_KAISYA_JISYA = "cc.app.ur.kaisya_cd_jissa";
    /**
     * プロパティファイル用キー定義
     */
    private static final String KEY_JIGYO_OA = "cc.app.ur.jigyobu_cd_oa";
    /**
     * プロパティファイル事業部
     */
    private static final String KEY_JIGYO_FS = "cc.app.ur.jigyobu_cd_fs";
    /**
     * プロパティファイル事業部
     */
    private static final String KEY_JIGYO_HDS = "cc.app.ur.jigyobu_cd_hds";
    /**
     * プロパティファイル事業部
     */
    private static final String KEY_JIGYO_KY = "cc.app.ur.jigyobu_cd_ky";
    /**
     * プロパティファイル事業部
     */
    private static final String KEY_JIGYO_FY = "cc.app.ur.jigyobu_cd_fy";
    /**
     * プロパティファイル事業部
     */
    private static final String KEY_JIGYO_NS = "cc.app.ur.jigyobu_cd_ns";
    /**
     * 対象外部門コード用キー
     */
    private static final String KEY_EXCEPT_BMN = "cc.app.ur.urjp0010_except_bmn";
    /**
     * 売上管理レコード
     **/
    private static final String KEY_CTLREC = "UR001";

    /** auto calculation */
    private static final String AUTOCAL = "1";
    /**
     * CCSystemContext
     */
    @Inject
    private CCSystemContext context;
    /**
     * MyBatisSqlMapDao
     */
    @Inject
    private MyBatisSqlMapDao dao;
    /**
     * ErrorResponse
     */
    @Inject
    private ErrorResponse errRes;
    /**
     * m000kaimMapper
     */
    @Inject
    private M000kaimMapper m000kaimMapper;
    /**
     * m001jgymMapper
     */
    @Inject
    private M001jgymMapper m001jgymMapper;
    /**
     * m006tenmMapper
     */
    @Inject
    private M006tenmMapper m006tenmMapper;
    /**
     * m020ctlmMapper
     */
    @Inject
    private M020ctlmMapper m020ctlmMapper;
    /**
     * m019ymdmMapper
     */
    @Inject
    private M019ymdmMapper m019ymdmMapper;
    /**
     * u001ybmnMapper
     */
    @Inject
    private U001ybmnMapper u001ybmnMapper;

    /**
     * Function getKaisyaName
     * 
     * @param kaisyaCd input kaisya code
     * @param hatDd  input hatDd
     * @return String
     */
    private String getKaisyaName(String kaisyaCd, String hatDd) {
        M000kaimExample example = new M000kaimExample();
        example.createCriteria().andKaisyaCdEqualTo(kaisyaCd);
        example.setSearchDate(hatDd);

        List<M000kaim> list = this.m000kaimMapper.selectByExample(example);
        if (list.size() > 0) {
            if (TYPE_ACT_KBN_DELETED.equals(list.get(0).getActKbn())) {
                return "";
            }
            return list.get(0).getKaiNm();
        } else {
            return "";
        }
    }

    /**
     * Function getJigyobuName
     * 
     * @param kaisyaCd input kaisya code
     * @param jigyobuCd  input jigyobuCd
     * @param hatDd  input hatDd
     * @return String
     */
    private String getJigyobuName(String kaisyaCd, String jigyobuCd, String hatDd) {
        M001jgymExample example = new M001jgymExample();
        example.createCriteria().andKaisyaCdEqualTo(kaisyaCd).andJigyobuCdEqualTo(jigyobuCd);
        example.setSearchDate(hatDd);
        List<M001jgym> list = this.m001jgymMapper.selectByExample(example);
        if (list.size() > 0) {
            if (TYPE_ACT_KBN_DELETED.equals(list.get(0).getActKbn())) {
                return "";
            }
            return list.get(0).getJgyNm();
        } else {
            return "";
        }
    }

    /**
     * Function getTenName
     * @param tenCd  input tenCd
     * @param hatDd  input hatDd
     * @return string
     */
    private String getTenName(String tenCd, String hatDd) {
        M006tenmExample example = new M006tenmExample();
        example.createCriteria().andTenCdEqualTo(tenCd);
        example.setSearchDate(hatDd);
        List<M006tenm> list = this.m006tenmMapper.selectByExample(example);
        if (list.size() > 0) {
            if (TYPE_ACT_KBN_DELETED.equals(list.get(0).getActKbn())) {
                return "";
            }
            return list.get(0).getTenNm();
        } else {
            return "";
        }
    }

    /**
     * 初期画面処理.
     * @param なし
     * @return true:処理成功 false:処理失敗
     */
    public Result initCond() {
        String hakkoDay = context.getUnyoDate();
        String tantoCd = context.getTantoCode();
        Urjp0010CondForm form = new Urjp0010CondForm();
        // 担当者の店舗コード・店舗区分を取得
        String sTenCode = "";
        String sTenKbn = "";
        String kaisyaCd = "";
        String jigyobuCd = "";
        String tenCd = "";
        Map<String, String> param = new HashMap<String, String>();
        param.put("hakkoDay", hakkoDay);
        param.put("tantoCd", tantoCd);
        List<M006tenmM016tanmResult> ls =
                dao.selectMany("selectM006tenmM016tanm01", param, M006tenmM016tanmResult.class);
        if (ls != null && ls.size() > 0) {
            sTenCode = ls.get(0).getTenCd();
            sTenKbn = ls.get(0).getTenKbn();
        } else {
            errRes.addErrorInfo(CCMessageConst.MSG_KEY_INPUT_TANTO_DATA_INVALID);
            return badRequest(Json.toJson(errRes));
        }
        if ("".equals(sTenCode)) {
            errRes.addErrorInfo(CCMessageConst.MSG_KEY_INPUT_TANTO_DATA_EMPTY);
            return badRequest(Json.toJson(errRes));
        }
        // 担当者が本部以外は店舗コードを初期値にセット
        if (!TYPE_ACT_KBN_DELETED.equals(sTenKbn)) {
            kaisyaCd = sTenCode.substring(0, 2);
            jigyobuCd = sTenCode.substring(2, 4);
            tenCd = sTenCode.substring(4, 7);
            form.setKaisyaCd(kaisyaCd);
            form.setJigyobuCd(jigyobuCd);
            form.setTenCd(tenCd);
        }
        return ok(Json.toJson(form));
    }

    /**
    * 引数とリクエスト(パラメータ)を条件にDBからデータ(複数件)を検索し返却する。
    * @param year String
    * @param mon String
    * @param kaisyaCd String
    * @param jigyobuCd String
    * @param tenCd String
    * @return クライアントへ返却する結果
    */
    public Result query(String year, String mon, String kaisyaCd, String jigyobuCd, String tenCd) {
        Form<Urjp0010CondForm> emptyForm = new Form<Urjp0010CondForm>(Urjp0010CondForm.class);
        Form<Urjp0010CondForm> reqForm = emptyForm.bindFromRequest();
        if (reqForm.hasErrors()) {
            errRes.addErrorInfo(CCMessageConst.MSG_KEY_VALIDATION_ERROR);
            errRes.addErrorInfo(reqForm.errors());
            return badRequest(Json.toJson(errRes));
        }

        Urjp0010ResultForm resultForm = new Urjp0010ResultForm();
        resultForm.setZidouKeisanKbn(BTN_COPY);
        Map<String, String> param = new HashMap<String, String>();
        String hakkoDay = context.getUnyoDate();
        String tantoCd = context.getTantoCode();
        param.put("hakkoDay", hakkoDay);
        param.put("tantoCd", tantoCd);
        String sTenCode = "";
        String sTenKbn = "";

        List<M006tenmM016tanmResult> tantoList =
                dao.selectMany("selectM006tenmM016tanm01", param, M006tenmM016tanmResult.class);
        if (tantoList != null && tantoList.size() > 0) {
            sTenCode = tantoList.get(0).getTenCd();
            sTenKbn = tantoList.get(0).getTenKbn();
        } else {
            errRes.addErrorInfo(CCMessageConst.MSG_KEY_INPUT_TANTO_DATA_INVALID);
        }

        // キー項目チェック処理
        String sJigyo = context.getContextProperty(JGY_CODE);
        if (sJigyo == null || "".equals(sJigyo)) {
            errRes.addErrorInfo(CCMessageConst.MSG_KEY_DATA_CHECK_PROPERTY_ERROR);
            return badRequest(Json.toJson(errRes));
        }

        // 対象事業部コード取得
        List<String> sJigyoArray = this.getPropertieArray(sJigyo);
        if (sJigyoArray == null) {
            errRes.addErrorInfo(CCMessageConst.MSG_KEY_DATA_CHECK_PROPERTY_ERROR);
            return badRequest(Json.toJson(errRes));
        }

        // ダミー店舗コード取得
        String sDummyTenCode = context.getContextProperty(DUMMY_CODE);
        if (sDummyTenCode == null || "".equals(sDummyTenCode)) {
            errRes.addErrorInfo(CCMessageConst.MSG_KEY_DATA_CHECK_PROPERTY_TRICD_ERROR);
            return badRequest(Json.toJson(errRes));
        }

        // 対象事業部チェック
        String sCopBmn = kaisyaCd + jigyobuCd;
        boolean bCheck = true;
        for (int i = 0; i < sJigyoArray.size(); i++) {
            if (sCopBmn.equals(sJigyoArray.get(i))) {
                bCheck = true;
                break;
            } else {
                bCheck = false;
            }
        }

        // 対象事業部なし
        if (!bCheck) {
            errRes.addErrorInfo(CCMessageConst.MSG_KEY_VALIDATION_ERROR);
            errRes.addErrorInfo("kaisyaCd", CCMessageConst.MSG_KEY_NOT_INPUT_DEPART_CODE);
            errRes.addErrorInfo("jigyobuCd", CCMessageConst.MSG_KEY_NOT_INPUT_DEPART_CODE);
            return badRequest(Json.toJson(errRes));
        }

        // 会社存在チェック
        if ("".equals(getKaisyaName(kaisyaCd, hakkoDay))) {
            errRes.addErrorInfo(CCMessageConst.MSG_KEY_VALIDATION_ERROR);
            errRes.addErrorInfo("kaisyaCd", CCMessageConst.MSG_KEY_NOT_EXIST_REGISTERED);
            return badRequest(Json.toJson(errRes));
        }

        // 事業部名取得
        if ("".equals(getJigyobuName(kaisyaCd, jigyobuCd, hakkoDay))) {
            errRes.addErrorInfo(CCMessageConst.MSG_KEY_VALIDATION_ERROR);
            errRes.addErrorInfo("jigyobuCd", CCMessageConst.MSG_KEY_NOT_EXIST_REGISTERED);
            return badRequest(Json.toJson(errRes));
        }

        // 店舗名取得
        String sTenCd = kaisyaCd + jigyobuCd + tenCd;
        if ("".equals(getTenName(sTenCd, hakkoDay))) {
            errRes.addErrorInfo(CCMessageConst.MSG_KEY_VALIDATION_ERROR);
            errRes.addErrorInfo("tenCd", CCMessageConst.MSG_KEY_NOT_EXIST_REGISTERED);
            return badRequest(Json.toJson(errRes));
        }

        // ダミー店舗チェック
        if (Integer.parseInt(tenCd) >= Integer.parseInt(sDummyTenCode)) {
            errRes.addErrorInfo(CCMessageConst.MSG_KEY_VALIDATION_ERROR);
            errRes.addErrorInfo("tenCd", CCMessageConst.MSG_KEY_NOT_INPUT_STORE_CODE);
            return badRequest(Json.toJson(errRes));
        }

        // 予算確定月を取得
        String yosKakuteiMon = getYosKakuteiMonth(kaisyaCd, jigyobuCd, tenCd);
        // 入力年月の有効チェック
        if ("-1".equals(yosKakuteiMon)) {
            errRes.addErrorInfo(CCMessageConst.MSG_KEY_DATA_CHECK_KAKUTE_ERROR);
            return badRequest(Json.toJson(errRes));
        }

        // 予算確定月をセット
        if ("".equals(yosKakuteiMon) || yosKakuteiMon == null) {
            errRes.addErrorInfo(CCMessageConst.MSG_KEY_DATA_CHECK_KAKUTE_ERROR);
            return badRequest(Json.toJson(errRes));
        }

        // 本部担当者以外は予算確定月のチェック
        if (!TYPE_ACT_KBN_DELETED.equals(sTenKbn)) {
            if (Integer.parseInt(year + mon) <= Integer.parseInt(yosKakuteiMon)) {
                errRes.addErrorInfo(CCMessageConst.MSG_KEY_VALIDATION_ERROR);
                errRes.addErrorInfo("yyyy", CCMessageConst.MSG_KEY_NOT_INPUT_DATE_BUDGET_PRE_MON);
                errRes.addErrorInfo("mm", CCMessageConst.MSG_KEY_NOT_INPUT_DATE_BUDGET_PRE_MON);
                return badRequest(Json.toJson(errRes));
            }
        }

        // 月初日・月末日の年月日マスタ存在チェック
        String maxDay = "";
        maxDay = Integer.toString(getMaxDay(Integer.parseInt(year), Integer.parseInt(mon)));
        maxDay = "00" + maxDay;
        maxDay = maxDay.substring(maxDay.length() - 2);
        // 表示初日日付
        String[] sYWWSArray = new String[3];
        // 表示終了日付
        String[] sYWWEArray = new String[3];
        // 表示年度、週、曜日を取得
        // 表示初日日付取得
        String pYear = year + mon + "01";
        sYWWSArray = (String[]) this.getYWW(pYear, sTenCd);
        // 表示終了日付取得
        pYear = year + mon + maxDay;
        sYWWEArray = (String[]) this.getYWW(pYear, sTenCd);

        if (CCStringUtil.isEmpty(sYWWSArray[0]) || CCStringUtil.isEmpty(sYWWSArray[1])
                || CCStringUtil.isEmpty(sYWWSArray[2])) {
            // 「指定された年月は年月日マスタに登録されてません。」出力
            errRes.addErrorInfo(CCMessageConst.MSG_KEY_VALIDATION_ERROR);
            errRes.addErrorInfo("yyyy", CCMessageConst.MSG_KEY_DATE_NOT_REGISTER_DATE_MASTER);
            errRes.addErrorInfo("mm", CCMessageConst.MSG_KEY_DATE_NOT_REGISTER_DATE_MASTER);
            return badRequest(Json.toJson(errRes));
        }

        if (CCStringUtil.isEmpty(sYWWEArray[0]) || CCStringUtil.isEmpty(sYWWEArray[1])
                || CCStringUtil.isEmpty(sYWWEArray[2])) {
            // 「指定された年月は年月日マスタに登録されてません。」出力
            errRes.addErrorInfo(CCMessageConst.MSG_KEY_VALIDATION_ERROR);
            errRes.addErrorInfo("yyyy", CCMessageConst.MSG_KEY_DATE_NOT_REGISTER_DATE_MASTER);
            errRes.addErrorInfo("mm", CCMessageConst.MSG_KEY_DATE_NOT_REGISTER_DATE_MASTER);
            return badRequest(Json.toJson(errRes));
        }

        // 本部担当者以外チェック
        if (!TYPE_ACT_KBN_DELETED.equals(sTenKbn)) {
            if (!sTenCode.equals(sTenCd)) {
                errRes.addErrorInfo(CCMessageConst.MSG_KEY_VALIDATION_ERROR);
                errRes.addErrorInfo("kaisyaCd", CCMessageConst.MSG_KEY_NOT_STORE_INPUT);
                errRes.addErrorInfo("jigyobuCd", CCMessageConst.MSG_KEY_NOT_STORE_INPUT);
                errRes.addErrorInfo("tenCd", CCMessageConst.MSG_KEY_NOT_STORE_INPUT);
                return badRequest(Json.toJson(errRes));
            }
        }

        // 登録データ存在チェック
        Map<String, String> parameter = new HashMap<String, String>();
        parameter.put("sTenCd", sTenCd);
        parameter.put("ysnDate", year + mon);
        parameter.put("bmnCd", jigyobuCd);
        int yosanRecCnt = dao.selectOne("selectU001ybmn02", parameter);
        if (yosanRecCnt != 0) {
            resultForm.setInsert(false);
        } else {
            resultForm.setInsert(true);
        }

        // 月間予算取得処理
        long dbYosKinMon = this.getMonYosan(year, mon, sTenCd, jigyobuCd);
        int dbYosKyakuMon = this.getKyakuMon(year, mon, sTenCd);
        if (dbYosKinMon == -1 || dbYosKyakuMon == -1) {
            errRes.addErrorInfo(CCMessageConst.MSG_KEY_INPUT_YOKINMON_DATA_INVALID);
            return badRequest(Json.toJson(errRes));
        }

        resultForm.setDbYosKinMon(dbYosKinMon);
        resultForm.setDbYosKyakuMon(dbYosKyakuMon);
        if (!this.chkMonYosan(0, resultForm)) {
            errRes.addErrorInfo(CCMessageConst.MSG_KEY_MONTHLY_DEPAR_NOT_REGISTER);
            return badRequest(Json.toJson(errRes));
        }

        // 初期表示データセット
        // 末日（グリッド行数）の取得
        int iMaxDay = getMaxDay(Integer.parseInt(year), Integer.parseInt(mon));
        if (iMaxDay < 28) {
            errRes.addErrorInfo(CCMessageConst.MSG_KEY_INPUT_MAXDAY_DATA_INVALID);
            return badRequest(Json.toJson(errRes));
        }

        // 対象外部門コード取得
        M019ymdmU001ybmnU012bmnjParam parameter2 = new M019ymdmU001ybmnU012bmnjParam();
        String exceptBmnParam2 = context.getContextProperty(KEY_EXCEPT_BMN);

        parameter2.setTenCd(sTenCd);
        parameter2.setMsExceptBmn(exceptBmnParam2);
        parameter2.setYsnDate(year + mon);
        parameter2.setBmnCd(jigyobuCd);
        List<M019ymdmU001ybmnU012bmnjResult> resultYsnHibetsuArea = new ArrayList<M019ymdmU001ybmnU012bmnjResult>();
        resultYsnHibetsuArea =
                dao.selectMany("selectM019ymdmU001ybmnU012bmnjU011tenjM019ymdm05", parameter2,
                        M019ymdmU001ybmnU012bmnjResult.class);

        if (resultYsnHibetsuArea.size() != iMaxDay) {
            errRes.addErrorInfo(CCMessageConst.MSG_KEY_INPUT_MAXDAY_DATA_INVALID);
            return badRequest(Json.toJson(errRes));
        }

        // 取得されたレコードの該当グリッド領域生成
        List<Urjp001001Dto> ysnHibetsuArea = new ArrayList<Urjp001001Dto>();
        for (int i = 0; i < iMaxDay; i++) {
            Urjp001001Dto dto = new Urjp001001Dto();
            ysnHibetsuArea.add(dto);
        }

        // データセット
        Urjp001001Dto urjp001001Dto = null;
        M019ymdmU001ybmnU012bmnjResult data = null;
        for (int i = 0; i < iMaxDay; i++) {
            urjp001001Dto = new Urjp001001Dto();
            data = resultYsnHibetsuArea.get(i);
            // 日付取得
            urjp001001Dto.setCalDate(data.getDt());
            // 曜日取得
            urjp001001Dto.setWeekDay(data.getWeekDay());
            // 目標売上高取得
            urjp001001Dto.setTenKyaku(data.getSakKyaku());
            // 昨年同曜売上取得
            urjp001001Dto.setUriKin(data.getSakKin());
            // 目標客数取得
            urjp001001Dto.setYsnKyaksu(data.getYsnKyaku());
            // 昨年同曜客数取得
            urjp001001Dto.setYsnUriKin(data.getYsnKin());
            ysnHibetsuArea.set(i, urjp001001Dto);
        }

        // 新規登録初期表示？
        if (yosanRecCnt == 0) {
            long lCalcUriKin = 0;
            int iCalcKyaku = 0;

            // 日単位の売上高セット
            double dValue = 0;
            dValue =
                    CCNumberUtil.divideDouble((double) resultForm.getYosKinMon(), (double) iMaxDay,
                            CCNumberUtil.ROUND_HALF_UP);
            long lUriKin = (long) dRound(dValue, 0);

            dValue =
                    CCNumberUtil.divideDouble((double) resultForm.getYosKyakuMon(), (double) iMaxDay,
                            CCNumberUtil.ROUND_HALF_UP);
            long iKyaku = (long) dRound(dValue, 0);
            for (int i = 1; i < iMaxDay; i++) {
                // 目標売上額セット
                ysnHibetsuArea.get(i).setYsnUriKin(lUriKin);
                lCalcUriKin += lUriKin;

                // 目標客数セット
                ysnHibetsuArea.get(i).setYsnKyaksu(iKyaku);
                iCalcKyaku += iKyaku;
            }
            // 自動調整
            ysnHibetsuArea.get(0).setYsnUriKin(resultForm.getYosKinMon() - lCalcUriKin);
            ysnHibetsuArea.get(0).setYsnKyaksu(resultForm.getYosKyakuMon() - iCalcKyaku);
        }

        // 取得されたレコードの該当グリッド領域生成
        List<Urjp001002Dto> ysnHibetsuSumAreaData = new ArrayList<Urjp001002Dto>();
        Urjp001002Dto urjp001002Dto = null;
        for (int i = 0; i < GRD2_ROW; i++) {
            urjp001002Dto = new Urjp001002Dto();
            ysnHibetsuSumAreaData.add(urjp001002Dto);
        }

        // addグリッド1
        resultForm.setYsnHibetsuArea(ysnHibetsuArea);
        // addグリッド2
        resultForm.setYsnHibetsuSumAreaData(ysnHibetsuSumAreaData);

        // 部門別予算（Grid3 , Grid4）セット
        M019ymdmU001ybmnU012bmnjParam parameter3 = new M019ymdmU001ybmnU012bmnjParam();
        String sGetumatuDay = year + mon + Integer.toString(iMaxDay);
        // 対象外部門コード取得
        parameter3.setHakkoDay(sGetumatuDay);
        parameter3.setTenCd(sTenCd);
        parameter3.setMsExceptBmn(exceptBmnParam2);
        parameter3.setYsnYyyymm(year + mon);
        parameter3.setBmnCd(jigyobuCd);
        List<U002ybmnM003bmnmM003bmnmM004Res> resultUriakeMokuhyouAreaData =
                new ArrayList<U002ybmnM003bmnmM003bmnmM004Res>();
        resultUriakeMokuhyouAreaData =
                dao.selectMany("selectU002ybmnM003bmnmM003bmnmM004tbum06", parameter3,
                        U002ybmnM003bmnmM003bmnmM004Res.class);

        List<Urjp001003Dto> uriakeMokuhyouAreaData = new ArrayList<Urjp001003Dto>();

        for (int i = 0; i < resultUriakeMokuhyouAreaData.size(); i++) {
            Urjp001003Dto dto = new Urjp001003Dto();
            U002ybmnM003bmnmM003bmnmM004Res re = resultUriakeMokuhyouAreaData.get(i);
            dto.setBmnCdNm(re.getBmncd() + "(" + CCStringUtil.trimBoth(re.getBmnnm()) + ")");
            dto.setYsnUriKin(re.getYsnurikin());

            uriakeMokuhyouAreaData.add(dto);
        }

        // addグリッド3
        resultForm.setUriakeMokuhyouAreaData(uriakeMokuhyouAreaData);

        // グリッド４
        List<Urjp001004Dto> mokuhyouGoukeiAreaData = new ArrayList<Urjp001004Dto>();
        for (int i = 0; i < GRD4_ROW; i++) {
            Urjp001004Dto dto = new Urjp001004Dto();
            mokuhyouGoukeiAreaData.add(dto);
        }
        resultForm.setMokuhyouGoukeiAreaData(mokuhyouGoukeiAreaData);

        // 計算処理コール
        boolean bCopyFlg = false;

        calc(bCopyFlg, resultForm, resultForm.getDbYosKinMon(), resultForm.getDbYosKyakuMon());

        // 昨年売上実績合計が０の時は他店舗参照表示フラグをセット
        if (resultForm.isInsert() && resultForm.getYsnHibetsuSumAreaData().get(0).getUriKin().longValue() == 0) {
            resultForm.setExtTenCopy(true);
        } else {
            resultForm.setExtTenCopy(false);
        }

        // ページデータ存在チェック
        if (resultForm.getYsnHibetsuArea().size() == 0 || resultForm.getUriakeMokuhyouAreaData().size() == 0) {
            errRes.addErrorInfo(CCMessageConst.MSG_KEY_NO_FOUND_DATA);
            return badRequest(Json.toJson(errRes));
        }

        // Grid2
        List<Urjp001002Dto> ysnHibetsuSumAreaDataFinal = this.getGrd2(resultForm.getYsnHibetsuSumAreaData());
        resultForm.setYsnHibetsuSumAreaData(ysnHibetsuSumAreaDataFinal);

        // Grid5
        List<Urjp001005Dto> gekkanUriakeYsnAreaData = this.getGrd5(resultForm);
        if (gekkanUriakeYsnAreaData == null) {
            gekkanUriakeYsnAreaData = new ArrayList<Urjp001005Dto>();
            Urjp001005Dto dto = new Urjp001005Dto();
            gekkanUriakeYsnAreaData.add(dto);
            gekkanUriakeYsnAreaData.add(dto);
        }
        resultForm.setGekkanUriakeYsnAreaData(gekkanUriakeYsnAreaData);

        // 日別部門別予算グリッドデータエリア初期化
        List<Urjp001006Dto> grid6 = new ArrayList<Urjp001006Dto>();
        List<String> colName = new ArrayList<String>();
        for (int i = 0; i < resultForm.getYsnHibetsuArea().size() + GRD6_HEAD_ROW; i++) {
            Urjp001006Dto dto = new Urjp001006Dto();
            for (int j = 0; j < resultForm.getUriakeMokuhyouAreaData().size() + 3; j++) {
                DataUtil.setValueByMethodName(dto, "setField" + j, "0");
                if (i == 0) {
                    colName.add("field" + j);
                }
            }
            if (i == 0) {
                dto.setColName(colName);
            }
            grid6.add(dto);
        }

        resultForm.setGrid6(grid6);
        this.setDayBmnPage(false, resultForm, year, mon, kaisyaCd, jigyobuCd, tenCd);
        return ok(Json.toJson(resultForm));
    }

    public Result calAndCopyBtnHandler(String year, String month, String kaisyaCd, String jigyobuCd, String tenCd) {
        Form<Urjp0010ResultForm> emptyForm = new Form<Urjp0010ResultForm>(Urjp0010ResultForm.class);
        Form<Urjp0010ResultForm> reqForm = emptyForm.bindFromRequest();
        if (reqForm.hasErrors()) {
            errRes.addErrorInfo(CCMessageConst.MSG_KEY_VALIDATION_ERROR);
            errRes.addErrorInfo(reqForm.errors());
            return badRequest(Json.toJson(errRes));
        }

        // コピーボタン押下フラグ
        List<HashMap<String, Boolean>> errorArray = new ArrayList<HashMap<String, Boolean>>();
        boolean bCopy = false;

        // 登録データ存在チェック
        // 店舗名取得
        Urjp0010ResultForm resultForm = reqForm.get();

        String hdnVal = resultForm.getHdnVal();
        List<Urjp001001Dto> grid1 = resultForm.getYsnHibetsuArea();
        List<Urjp001002Dto> grid2 = resultForm.getYsnHibetsuSumAreaData();
        List<Urjp001005Dto> grid5 = resultForm.getGekkanUriakeYsnAreaData();

        String fullTenCd = kaisyaCd + jigyobuCd + tenCd;

        if (!checkGrid(grid1, grid5)) {
            return badRequest(Json.toJson(errRes));
        }

        resultForm.setYosKinMon(grid5.get(0).getYosKyakuMon() == null ? 0 : grid5.get(0).getYosKyakuMon());
        resultForm.setYosKyakuMon(grid5.get(1).getYosKyakuMon() == null ? 0 : grid5.get(1).getYosKyakuMon());

        this.setGrid6(resultForm);

        // 月間予算入力チェック
        long dbYosKinMon = this.getMonYosan(year, month, fullTenCd, jigyobuCd);
        int dbYosKyakuMon = this.getKyakuMon(year, month, fullTenCd);
        resultForm.setDbYosKinMon(dbYosKinMon);
        resultForm.setDbYosKyakuMon(dbYosKyakuMon);
        if (!this.chkMonYosan(1, resultForm)) {
            errRes.addErrorInfo(CCMessageConst.MSG_KEY_VALIDATION_ERROR);
            errRes.addErrorInfo("yosKyakuMon_0", CCMessageConst.MSG_KEY_NOT_MATCH_BUGGET);
            errRes.addErrorInfo("yosKyakuMon_1", CCMessageConst.MSG_KEY_NOT_MATCH_BUGGET);
            return badRequest(Json.toJson(errRes));
        }

        // コピーボタン押下？
        if (BTN_COPY.equals(hdnVal)) {
            if (grid2.get(0).getUriKin().longValue() > 0) {
                bCopy = true;
                // グリッドデータ計算処理
                this.calc(bCopy, resultForm, resultForm.getDbYosKinMon(), resultForm.getDbYosKyakuMon());

                // Grid2
                List<Urjp001002Dto> ysnHibetsuSumAreaDataFinal = this.getGrd2(resultForm.getYsnHibetsuSumAreaData());
                resultForm.setYsnHibetsuSumAreaData(ysnHibetsuSumAreaDataFinal);

                // Grid5
                List<Urjp001005Dto> gekkanUriakeYsnAreaData = this.getGrd5(resultForm);
                resultForm.setGekkanUriakeYsnAreaData(gekkanUriakeYsnAreaData);
                errRes.addErrorInfo(CCMessageConst.MSG_KEY_URJP0010_01_DATA_INVALID);
                resultForm.setInfoRes(errRes);
                return ok(Json.toJson(resultForm));
            } else {
                errRes.addErrorInfo(CCMessageConst.MSG_KEY_NOT_EXIST_DAY);
                return badRequest(Json.toJson(errRes));
            }
        }

        // 差異計算ボタン押下？
        if (BTN_CALC.equals(hdnVal)) {
            bCopy = false;
            // グリッドデータ計算処理
            this.calc(bCopy, resultForm, resultForm.getDbYosKinMon(), resultForm.getDbYosKyakuMon());

            // Grid2
            List<Urjp001002Dto> ysnHibetsuSumAreaDataFinal = this.getGrd2(resultForm.getYsnHibetsuSumAreaData());
            resultForm.setYsnHibetsuSumAreaData(ysnHibetsuSumAreaDataFinal);

            // Grid5
            List<Urjp001005Dto> gekkanUriakeYsnAreaData = this.getGrd5(resultForm);
            resultForm.setGekkanUriakeYsnAreaData(gekkanUriakeYsnAreaData);

            errRes.addErrorInfo(CCMessageConst.MSG_KEY_URJP0010_02_DATA_INVALID);
            resultForm.setInfoRes(errRes);
            return ok(Json.toJson(resultForm));
        }

        if (BTN_NEXT_COPY.equals(hdnVal)) {
            bCopy = false;

            // 他店舗実績取得
            this.executeExtTenCopy(resultForm, year, month, kaisyaCd, jigyobuCd, resultForm.getwTenCd(),
                    resultForm.getDbYosKinMon(), resultForm.getDbYosKyakuMon());

            // 昨年実績存在チェック
            if (grid2.get(0).getYsnUriKin().longValue() > 0) {
                errRes.addErrorInfo(CCMessageConst.MSG_KEY_URJP0010_03_DATA_INVALID);
            } else {
                errRes.addErrorInfo(CCMessageConst.MSG_KEY_NOT_EXIST_DAY);
                return badRequest(Json.toJson(errRes));
            }

            // Grid2
            List<Urjp001002Dto> ysnHibetsuSumAreaDataFinal = this.getGrd2(resultForm.getYsnHibetsuSumAreaData());
            resultForm.setYsnHibetsuSumAreaData(ysnHibetsuSumAreaDataFinal);

            // Grid5
            List<Urjp001005Dto> gekkanUriakeYsnAreaData = this.getGrd5(resultForm);
            resultForm.setGekkanUriakeYsnAreaData(gekkanUriakeYsnAreaData);
            resultForm.setInfoRes(errRes);
            return ok(Json.toJson(resultForm));
        }

        // グリッドデータ計算処理
        this.calc(bCopy, resultForm, resultForm.getDbYosKinMon(), resultForm.getDbYosKyakuMon());

        // 月間予算入力チェック
        if (!this.chkMonYosan(1, resultForm)) {
            errRes.addErrorInfo(CCMessageConst.MSG_KEY_VALIDATION_ERROR);
            errRes.addErrorInfo("yosKyakuMon", CCMessageConst.MSG_KEY_NOT_MATCH_BUGGET);
            return badRequest(Json.toJson(errRes));
        }

        if (!this.checkData(resultForm, grid2, errorArray)) {
            return ok(Json.toJson(resultForm));
        }

        String autoCalc =
                CCStringUtil.isEmpty(resultForm.getZidouKeisanKbn()) ? AUTOCAL : resultForm.getZidouKeisanKbn();

        if (AUTOCAL.equals(autoCalc)) {
            // 日別部門別予算値セット
            this.setDayBmnPage(true, resultForm, year, month, kaisyaCd, jigyobuCd, tenCd);
        }

        // 日別部門別予算計算処理
        this.calcDayBmn(resultForm);
        this.getGrd6(resultForm, month);

        resultForm.setInfoRes(errRes);
        return ok(Json.toJson(resultForm));
    }

    private boolean checkData(Urjp0010ResultForm resultForm, List<Urjp001002Dto> grid2,
            List<HashMap<String, Boolean>> errorArray) {
        // データ整合性チェック処理
        boolean flag1 = false;
        boolean flag2 = false;
        boolean flag3 = false;
        boolean flag4 = false;
        // 入力された月間予算と月別部門別予算テーブルの月間予算を比較
        if (!resultForm.getDbYosKinMon().equals(resultForm.getYosKinMon())) {
            flag1 = true;
        }

        if (resultForm.getDbYosKyakuMon() != resultForm.getYosKyakuMon()) {
            flag2 = true;
        }

        // 日別合計チェック
        if (grid2.get(1).getYsnUriKin().longValue() != 0) {
            flag3 = true;
        }
        // 目標客数合計（差異０）チェック
        if (grid2.get(1).getYsnKyaksu().longValue() != 0) {
            flag4 = true;
        }

        if (flag1 || flag2 || flag3 || flag4) {
            errorArray.add(new HashMap<String, Boolean>());
            errorArray.add(new HashMap<String, Boolean>());

            errRes.addErrorInfo(CCMessageConst.MSG_KEY_NOT_DIFF_ZERO);
            if (flag1) {
                errRes.addErrorInfo("yosKyakuMon_0", CCMessageConst.MSG_KEY_NOT_MATCH_BUGGET);
            }
            if (flag2) {
                errRes.addErrorInfo("yosKyakuMon_1", CCMessageConst.MSG_KEY_NOT_MATCH_BUGGET);
            }
            if (flag3) {
                errorArray.get(1).put("sYsnUriKin", true);
            }
            if (flag4) {
                errorArray.get(1).put("sYsnKyaksu", true);
            }

            errRes.setGridErrors(errorArray);
            resultForm.setInfoRes(errRes);

            return false;
        } else {
            return true;
        }
    }

    /**
    * 引数とリクエスト(ボディ)からレコードを編集しDBに登録(論理)する。*
    * @param year String
    * @param month String
    * @param kaisyaCd String
    * @param jigyobuCd String
    * @param tenCd String
    * @return クライアントへ返却する結果
    */
    public Result update(String year, String month, String kaisyaCd, String jigyobuCd, String tenCd) {
        Form<Urjp0010ResultForm> emptyForm = new Form<Urjp0010ResultForm>(Urjp0010ResultForm.class);
        Form<Urjp0010ResultForm> reqForm = emptyForm.bindFromRequest();
        if (reqForm.hasErrors()) {
            errRes.addErrorInfo(CCMessageConst.MSG_KEY_VALIDATION_ERROR);
            errRes.addErrorInfo(reqForm.errors());
            return badRequest(Json.toJson(errRes));
        }

        // コピーボタン押下フラグ
        List<HashMap<String, Boolean>> errorArray = new ArrayList<HashMap<String, Boolean>>();

        // 登録データ存在チェック
        // 店舗名取得
        Urjp0010ResultForm resultForm = reqForm.get();
        boolean isInsert = resultForm.isInsert();

        String hdnVal = resultForm.getHdnVal();
        List<Urjp001001Dto> grid1 = resultForm.getYsnHibetsuArea();
        List<Urjp001002Dto> grid2 = resultForm.getYsnHibetsuSumAreaData();
        List<Urjp001003Dto> grid3 = resultForm.getUriakeMokuhyouAreaData();
        List<Urjp001005Dto> grid5 = resultForm.getGekkanUriakeYsnAreaData();
        List<Urjp001006Dto> grid6 = resultForm.getGrid6();

        String fullTenCd = kaisyaCd + jigyobuCd + tenCd;

        if (!checkGrid(grid1, grid5)) {
            return badRequest(Json.toJson(errRes));
        }

        resultForm.setYosKinMon(grid5.get(0).getYosKyakuMon() == null ? 0 : grid5.get(0).getYosKyakuMon());
        resultForm.setYosKyakuMon(grid5.get(1).getYosKyakuMon() == null ? 0 : grid5.get(1).getYosKyakuMon());

        this.setGrid6(resultForm);

        // 月間予算入力チェック
        long dbYosKinMon = this.getMonYosan(year, month, fullTenCd, jigyobuCd);
        int dbYosKyakuMon = this.getKyakuMon(year, month, fullTenCd);
        resultForm.setDbYosKinMon(dbYosKinMon);
        resultForm.setDbYosKyakuMon(dbYosKyakuMon);
        if (!this.chkMonYosan(1, resultForm)) {
            errRes.addErrorInfo(CCMessageConst.MSG_KEY_VALIDATION_ERROR);
            errRes.addErrorInfo("yosKyakuMon_0", CCMessageConst.MSG_KEY_NOT_MATCH_BUGGET);
            errRes.addErrorInfo("yosKyakuMon_1", CCMessageConst.MSG_KEY_NOT_MATCH_BUGGET);
            return badRequest(Json.toJson(errRes));
        }

        // グリッドデータ計算処理
        this.calc(false, resultForm, resultForm.getDbYosKinMon(), resultForm.getDbYosKyakuMon());

        // 月間予算入力チェック
        if (!this.chkMonYosan(1, resultForm)) {
            errRes.addErrorInfo(CCMessageConst.MSG_KEY_VALIDATION_ERROR);
            errRes.addErrorInfo("yosKyakuMon", CCMessageConst.MSG_KEY_NOT_MATCH_BUGGET);
            return badRequest(Json.toJson(errRes));
        }

        if (!this.checkData(resultForm, grid2, errorArray)) {
            return ok(Json.toJson(resultForm));
        }

        String autoCalc =
                CCStringUtil.isEmpty(resultForm.getZidouKeisanKbn()) ? AUTOCAL : resultForm.getZidouKeisanKbn();

        if (AUTOCAL.equals(autoCalc) && !resultForm.isFinalStage()) {
            // 日別部門別予算値セット
            this.setDayBmnPage(true, resultForm, year, month, kaisyaCd, jigyobuCd, tenCd);
        }

        // 日別部門別予算計算処理
        this.calcDayBmn(resultForm);
        this.getGrd6(resultForm, month);

        if ("4".equals(hdnVal)) {
            if (!doCheckGrd6(resultForm)) {
                errRes.addErrorInfo(CCMessageConst.MSG_KEY_VALIDATION_ERROR);
                return badRequest(Json.toJson(errRes));
            }

            boolean flag = false;
            grid6 = resultForm.getGrid6();
            Urjp001006Dto dto = grid6.get(2);
            errorArray.add(new HashMap<String, Boolean>());
            errorArray.add(new HashMap<String, Boolean>());
            errorArray.add(new HashMap<String, Boolean>());

            for (int j = 0; j < grid3.size() + 2; j++) {
                String fieldContent = (String) DataUtil.getValueByMethodName(dto, "getField" + (j + 1));
                if (Long.parseLong(fieldContent) != 0) {
                    errorArray.get(2).put("field" + (j + 1), true);
                    flag = true;
                }
            }

            if (flag) {
                errRes.addErrorInfo(CCMessageConst.MSG_KEY_NOT_DIFF_ZERO);
                errRes.setGridErrors(errorArray);
                resultForm.setInfoRes(errRes);
                return ok(Json.toJson(resultForm));
            }
        }

        // 日別部門別予算データインサート処理
        if (isInsert) {
            // 差異計算ボタン押下？
            if ("4".equals(hdnVal)) {
                this.doInsert(resultForm, year, month, kaisyaCd, jigyobuCd, tenCd);
            }
        } else {
            // 日別部門別予算データアップデート処理
            if ("4".equals(hdnVal)) {
                this.doUpdate(resultForm, year, month, kaisyaCd, jigyobuCd, tenCd);
            } else {
                if ("5".equals(hdnVal)) {
                    this.doDelete(year, month, kaisyaCd, jigyobuCd, tenCd);
                    errRes.addErrorInfo(CCMessageConst.MSG_KEY_SUCCESS_DELETE_DATA);
                }
            }
        }

        resultForm.setInfoRes(errRes);
        return ok(Json.toJson(resultForm));
    }

    private boolean checkGrid(List<Urjp001001Dto> grid1, List<Urjp001005Dto> grid5) {
        boolean checkGrid = true;
        for (int i = 0; i < grid1.size(); i++) {
            if (grid1.get(i).getYsnUriKin().longValue() < 0) {
                errRes.addErrorInfo(CCMessageConst.MSG_KEY_VALIDATION_ERROR);
                errRes.addErrorInfo("ysnUriKin_" + i, CCMessageConst.MSG_KEY_DATA_PARSE_INT_ERROR);
                checkGrid = false;
            }
            if (grid1.get(i).getYsnUriKin().longValue() < 0) {
                errRes.addErrorInfo(CCMessageConst.MSG_KEY_VALIDATION_ERROR);
                errRes.addErrorInfo("ysnKyaksu_" + i, CCMessageConst.MSG_KEY_DATA_PARSE_INT_ERROR);
                checkGrid = false;
            }
        }

        for (int i = 0; i < 2; i++) {
            if (grid5.get(i).getYosKyakuMon().longValue() < 0) {
                errRes.addErrorInfo(CCMessageConst.MSG_KEY_VALIDATION_ERROR);
                errRes.addErrorInfo("yosKyakuMon_" + i, CCMessageConst.MSG_KEY_DATA_PARSE_INT_ERROR);
                checkGrid = false;
            }
        }
        return checkGrid;
    }

    /**
    * 引数とリクエスト(パラメータ)を条件にDBの該当データを削除(論理)する*
    * @param year String
    * @param mon String
    * @param kaisyaCd String
    * @param jigyobuCd String
    * @param tenCd String
    *  @return クライアントへ返却する結果
    */
    public Result delete(String year, String mon, String kaisyaCd, String jigyobuCd, String tenCd) {
        this.doDelete(year, mon, kaisyaCd, jigyobuCd, tenCd);
        errRes.addErrorInfo(CCMessageConst.MSG_KEY_SUCCESS_DELETE_DATA);
        return ok(Json.toJson(errRes));
    }

    /**
     * コントロールマスタより予算確定月を取得する.
    * @param kaisyaCd String
    * @param jigyobuCd String
    * @param tenCd String
     * @return boolean true:正常  false:異常
     **/
    private String getYosKakuteiMonth(String kaisyaCd, String jigyobuCd, String tenCd) {
        String sKaisya = context.getContextProperty(KEY_KAISYA_JISYA);
        if ("".equals(sKaisya)) {
            return "";
        }
        List<String> sKaisyaArray = this.getPropertieArray(sKaisya);
        if (sKaisyaArray == null) {
            return "";
        }
        // ＯＡ事業部コード取得
        String sOACode = context.getContextProperty(KEY_JIGYO_OA);
        if ("".equals(sOACode)) {
            return "";
        }
        List<String> sOACodeArray = this.getPropertieArray(sOACode);
        if (sOACodeArray == null) {
            return "";
        }
        // ＦＳ事業部コード取得
        String sFSCode = context.getContextProperty(KEY_JIGYO_FS);
        if ("".equals(sFSCode)) {
            return "";
        }
        List<String> sFSCodeArray = this.getPropertieArray(sFSCode);
        if (sFSCodeArray == null) {
            return "";
        }
        // プロパティファイルよりＨＤＳ事業部コード読み込み
        String sHDSCode = context.getContextProperty(KEY_JIGYO_HDS);
        if ("".equals(sHDSCode)) {
            return "";
        }
        List<String> sHDSCodeArray = this.getPropertieArray(sHDSCode);
        if (sHDSCodeArray == null) {
            return "";
        }
        // ＨＤＳ事業部コード取得
        String sKYCode = context.getContextProperty(KEY_JIGYO_KY);
        if ("".equals(sKYCode)) {
            return "";
        }
        List<String> sKYCodeArray = this.getPropertieArray(sKYCode);
        if (sKYCodeArray == null) {
            return "";
        }
        // ＦＹ事業部コード取得
        String sFYCode = context.getContextProperty(KEY_JIGYO_FY);
        if ("".equals(sFYCode)) {
            return "";
        }
        List<String> sFYCodeArray = this.getPropertieArray(sHDSCode);
        if (sFYCodeArray == null) {
            return "";
        }
        // ＮＳ事業部コード取得
        String sNSCode = context.getContextProperty(KEY_JIGYO_NS);
        if ("".equals(sNSCode)) {
            return "";
        }
        List<String> sNSCodeArray = this.getPropertieArray(sNSCode);
        if (sNSCodeArray == null) {
            return "";
        }

        M020ctlmExample example = new M020ctlmExample();
        example.createCriteria().andCdEqualTo(KEY_CTLREC);
        List<M020ctlm> lsM020ctml = m020ctlmMapper.selectByExample(example);

        // DATAエリア取得
        String sData = lsM020ctml.get(0).getData();
        if (sData.length() <= 0) {
            return "-1";
        }
        // ＯＡ・ＦＳ・ＨＤＳ確定月を取得
        String sKakuteiMonOA = sData.substring(0, 6);
        String sKakuteiMonFS = sData.substring(6, 12);
        String sKakuteiMonHDS = sData.substring(172, 178);
        String sKakuteiMonNS = sData.substring(178, 184);

        // 会社コードサンベルクス以外はＮＧ
        for (int i = 0; i < sKaisyaArray.size(); i++) {
            if (!kaisyaCd.equals(sKaisyaArray.get(i))) {
                return "";
            }
        }

        // 事業部コード判断
        for (int i = 0; i < sOACodeArray.size(); i++) {
            if (jigyobuCd.equals(sOACodeArray.get(i))) {
                return sKakuteiMonOA;
            }
        }

        for (int i = 0; i < sFSCodeArray.size(); i++) {
            if (jigyobuCd.equals(sFSCodeArray.get(i))) {
                return sKakuteiMonFS;
            }
        }

        for (int i = 0; i < sHDSCodeArray.size(); i++) {
            if (jigyobuCd.equals(sHDSCodeArray.get(i))) {
                return sKakuteiMonHDS;
            }
        }
        for (int i = 0; i < sNSCodeArray.size(); i++) {
            if (jigyobuCd.equals(sNSCodeArray.get(i))) {
                return sKakuteiMonNS;
            }
        }
        return "";
    }

    /**
     * 指定年月より末日を取得.
     * @param iYear 年
     * @param iMonth 月
     * @return int 末日
     **/
    private int getMaxDay(int iYear, int iMonth) {
        int iDay = 31;
        while (!CCDateUtil.isDate(iYear, iMonth, iDay)) {
            iDay--;
        }
        return iDay;

    }

    /**
    * 指定年月日の年度、週番号、曜日を取得する
    * @param date String
    * @param tencd String
    * @return String[] { 年度 , 週No , 曜日 }
    **/
    private String[] getYWW(String date, String tencd) {

        String[] sDataArray = new String[3];
        for (int i = 0; i < 3; i++) {
            sDataArray[i] = "";
        }

        if ("".equals(date) || date == null) {
            return sDataArray;
        }

        M019ymdmExample example = new M019ymdmExample();
        example.createCriteria().andCalDateEqualTo(date).andTenCdEqualTo(tencd);
        List<M019ymdm> lsM019ymdm = this.m019ymdmMapper.selectByExample(example);

        // 存在チェック
        if (lsM019ymdm == null || lsM019ymdm.size() <= 0) {
            return sDataArray;
        }

        // 年度の取得
        sDataArray[0] = lsM019ymdm.get(0).getNendo();

        // 週番号の取得
        sDataArray[1] = lsM019ymdm.get(0).getWeekNo();

        // 曜日の取得
        sDataArray[2] = lsM019ymdm.get(0).getWeekDay();

        // 年度、週番号、曜日を返す
        return sDataArray;

    }

    /**
    * 月間予算取得処理
    * @param year String
    * @param mon String
    * @param tenCd String
    * @param jigyobuCd String
    * @return boolean true:正常  false:異常
    */
    private long getMonYosan(String year, String mon, String tenCd, String jigyobuCd) {
        // 対象外部門コード取得
        String exceptBmn = "";
        exceptBmn = context.getContextProperty(KEY_EXCEPT_BMN);
        // 末日（グリッド行数）の取得
        int iMaxDay = getMaxDay(Integer.parseInt(year), Integer.parseInt(mon));
        if (iMaxDay < 28) {
            return -1;
        }
        String sGetumatuDay = year + mon + Integer.toString(iMaxDay);
        // 月間売上予算取得
        U002ybmnM003bmnmM004tbumResult parameter = new U002ybmnM003bmnmM004tbumResult();
        parameter.setHakkoDay(sGetumatuDay);
        parameter.setLsBmnCd(exceptBmn);
        parameter.setBmnCd(jigyobuCd);
        parameter.setTenCd(tenCd);
        parameter.setYsnYyyymm(year + mon);
        int num = dao.selectOne("selectU002ybmnM003bmnmM004tbum03", parameter);
        return Long.parseLong(String.valueOf(num));
    }

    /**
    * 月間予算取得処理
    * @param year String
    * @param mon String
    * @param tenCd String
    * @return boolean true:正常  false:異常
    */
    private int getKyakuMon(String year, String mon, String tenCd) {
        // 末日（グリッド行数）の取得
        int iMaxDay = getMaxDay(Integer.parseInt(year), Integer.parseInt(mon));
        if (iMaxDay < 28) {
            return -1;
        }
        // 月間売上予算取得
        Map<String, String> parameter = new HashMap<String, String>();
        parameter.put("tenCd", tenCd);
        parameter.put("ysnYyyymm", year + mon);
        int num = dao.selectOne("selectU002ybmn04", parameter);
        return num;
    }

    /**
    * 四捨五入
    * @param dVal double 四捨五入したい値
    * @param iKeta int 四捨五入をしたい桁(0.456 → 0.46 ： 2)   -値は小数点以上桁数指定(12345 → 12350 : -2)
    * @return double 四捨五入後の値
    */
    private double dRound(double dVal, int iKeta) {
        double dAnsVal = 0;
        if (iKeta >= 0) {
            String sFStyle = "#";
            if (iKeta > 0) {
                sFStyle += ".";
                for (int i = 0; i <= iKeta; i++) {
                    sFStyle += "0";
                }
            }
            String sValue = String.valueOf(dVal);
            String sValueAns = CCNumberUtil.frmtNumber(sValue, sFStyle);
            dAnsVal = Double.parseDouble(sValueAns);
        } else {
            double dCalVal = Math.pow(10.0, (double) iKeta);
            dAnsVal = Math.round(dVal * dCalVal) / dCalVal;
        }

        return dAnsVal;
    }

    /**
    * 予算データ計算処理.
    * @param bCopyFlg boolean コピーフラグ（コピーボタン押下時用）
    * @param resultFom Urjp0010ResultForm
    * @param yosKinMon long
    * @param yosKyakuMon int
    * @return true：処理成功、false：処理失敗
    */
    private boolean calc(boolean bCopyFlg, Urjp0010ResultForm resultFom, long yosKinMon, int yosKyakuMon) {
        List<Urjp001001Dto> grid1 = resultFom.getYsnHibetsuArea();
        List<Urjp001002Dto> grid2 = resultFom.getYsnHibetsuSumAreaData();
        List<Urjp001003Dto> grid3 = resultFom.getUriakeMokuhyouAreaData();
        List<Urjp001004Dto> grid4 = resultFom.getMokuhyouGoukeiAreaData();
        List<Urjp001005Dto> grid5 = resultFom.getGekkanUriakeYsnAreaData();
        double dValue = 0;
        Long uriKinMon = null;
        Long kyakuMon = null;
        if (grid5 != null && grid5.size() > 0) {
            uriKinMon = grid5.get(0).getYosKyakuMon();
            kyakuMon = grid5.get(1).getYosKyakuMon();
        }
        if (uriKinMon == null) {
            uriKinMon = new Long(0);
        }

        if (kyakuMon == null) {
            kyakuMon = new Long(0);
        }

        resultFom.setYosKinMon(uriKinMon);
        resultFom.setYosKyakuMon(kyakuMon);

        /*
         * 月間予算取得処理 合計グリッドクリア
         */
        for (int i = 0; i < GRD2_ROW; i++) {
            Urjp001002Dto dto = new Urjp001002Dto();
            resultFom.getYsnHibetsuSumAreaData().set(i, dto);
        }

        for (int i = 0; i < GRD4_ROW; i++) {
            Urjp001004Dto dto = new Urjp001004Dto();
            resultFom.getMokuhyouGoukeiAreaData().set(i, dto);
        }

        /*
         * 日別グリッド計算 合計を取得
         */
        for (int i = 0; i < grid1.size(); i++) {
            // 目標売上高
            grid2.get(0).setYsnUriKin(
                    grid2.get(0).getYsnUriKin().longValue() + grid1.get(i).getYsnUriKin().longValue());
            // 昨年同曜売上実績
            grid2.get(0).setUriKin(grid2.get(0).getUriKin().longValue() + grid1.get(i).getUriKin().longValue());
            // 目標客数
            grid2.get(0)
                    .setTenKyaku(grid2.get(0).getTenKyaku().longValue() + grid1.get(i).getTenKyaku().longValue());
            // // 昨年同曜客数
            grid2.get(0).setYsnKyaksu(
                    grid2.get(0).getYsnKyaksu().longValue() + grid1.get(i).getYsnKyaksu().longValue());
        }

        // 構成比の取得
        if (grid2.get(0).getYsnUriKin().intValue() != 0) {

            // 明細構成比を取得
            for (int i = 0; i < grid1.size(); i++) {
                dValue =
                        CCNumberUtil.divideDouble(grid1.get(i).getYsnUriKin().doubleValue(), grid2.get(0)
                                .getYsnUriKin().doubleValue(), CCNumberUtil.ROUND_HALF_UP);
                grid1.get(i).setKosei(BigDecimal.valueOf(dRound(CCNumberUtil.multiplyDouble(dValue, 100), 2)));
            }

            dValue =
                    CCNumberUtil.divideDouble(grid2.get(0).getYsnUriKin().doubleValue(), grid2.get(0).getYsnUriKin()
                            .doubleValue(), CCNumberUtil.ROUND_HALF_UP);
            grid2.get(0).setKosei(BigDecimal.valueOf(dRound(CCNumberUtil.multiplyDouble(dValue, 100), 2)));
        }

        // 差異をセット（グリッド２）
        // 目標売上高差異
        grid2.get(1).setYsnUriKin((grid2.get(0).getYsnUriKin().longValue() - resultFom.getYosKinMon()));
        // 目標客数差異
        grid2.get(1).setYsnKyaksu(grid2.get(0).getYsnKyaksu().longValue() - resultFom.getYosKyakuMon());

        // 空白セルセット
        grid2.get(0).setGouKei2("合　　計");
        grid2.get(1).setGouKei2("差　　異");
        grid2.get(1).setUriKin(new Long(0));
        grid2.get(1).setTenKyaku(new Long(0));
        grid2.get(1).setKosei(new BigDecimal(0));

        // *** 部門別グリッド計算 ***
        if (grid3 != null) {
            // 合計を取得
            for (int i = 0; i < grid3.size(); i++) {
                grid4.get(0).setYsnUriKin(
                        grid4.get(0).getYsnUriKin().longValue() + grid3.get(i).getYsnUriKin().longValue());
            }
            // 構成比の取得
            for (int i = 0; i < grid3.size(); i++) {
                dValue =
                        CCNumberUtil.divideDouble(grid3.get(i).getYsnUriKin().doubleValue(), grid4.get(0)
                                .getYsnUriKin().doubleValue(), CCNumberUtil.ROUND_HALF_UP);
                grid3.get(i).setKosei(BigDecimal.valueOf(dRound(CCNumberUtil.multiplyDouble(dValue, 100), 1)));
            }
            dValue =
                    CCNumberUtil.divideDouble(grid4.get(0).getYsnUriKin().doubleValue(), grid4.get(0).getYsnUriKin()
                            .doubleValue(), CCNumberUtil.ROUND_HALF_UP);
            grid4.get(0).setKosei(BigDecimal.valueOf(dRound(CCNumberUtil.multiplyDouble(dValue, 100), 1)));

        }

        // *** 月間予算グリッド計算 ***
        // 月間売上金額昨対
        BigDecimal uriSakuTai = new BigDecimal(0);
        BigDecimal kyakuSakuTai = new BigDecimal(0);
        if (grid2.get(0).getUriKin().intValue() != 0) {
            dValue =
                    CCNumberUtil.divideDouble((double) resultFom.getYosKinMon(), grid2.get(0).getUriKin()
                            .doubleValue(), CCNumberUtil.ROUND_HALF_UP);
            uriSakuTai = BigDecimal.valueOf(dRound(CCNumberUtil.multiplyDouble(dValue, 100), 1));
        }
        // 月間客数昨対
        if (grid2.get(0).getTenKyaku().intValue() != 0) {
            dValue =
                    CCNumberUtil.divideDouble((double) resultFom.getYosKyakuMon(), grid2.get(0).getTenKyaku()
                            .doubleValue(), CCNumberUtil.ROUND_HALF_UP);
            kyakuSakuTai = BigDecimal.valueOf(dRound(CCNumberUtil.multiplyDouble(dValue, 100), 1));
        }
        resultFom.setUriSakuTai(uriSakuTai);
        resultFom.setKyakuSakuTai(kyakuSakuTai);

        // 月別売上金額差異
        long uriSai = yosKinMon - resultFom.getYosKinMon();
        // 月別客数差異
        long kyakuSai = yosKyakuMon - resultFom.getYosKyakuMon();
        resultFom.setUriSai(uriSai);
        resultFom.setKyakuSai(kyakuSai);
        // *** Ｃｏｐｙボタン押下時の計算
        if (bCopyFlg) {
            // 昨年同曜実績あり？
            if (grid2.get(0).getUriKin().longValue() > 0) {
                // 日別売上高セット
                long lDayUriKin = 0;
                long iDayKyaku = 0;

                for (int i = 0; i < grid1.size(); i++) {
                    dValue =
                            CCNumberUtil.multiplyDouble(grid1.get(i).getUriKin().doubleValue(), resultFom
                                    .getUriSakuTai().doubleValue());

                    lDayUriKin =
                            (long) dRound(CCNumberUtil.divideDouble(dValue, 100, CCNumberUtil.ROUND_HALF_UP), -1);
                    grid1.get(i).setYsnUriKin(lDayUriKin);
                    dValue =
                            CCNumberUtil.multiplyDouble(grid1.get(i).getTenKyaku().doubleValue(), resultFom
                                    .getKyakuSakuTai().doubleValue());
                    iDayKyaku = (long) dRound(CCNumberUtil.divideDouble(dValue, 100, CCNumberUtil.ROUND_HALF_UP), 0);

                    grid1.get(i).setYsnKyaksu(iDayKyaku);
                }

                // 計算処理再コール
                boolean bCopyFlgReTry = false;
                resultFom.setYsnHibetsuArea(grid1);
                resultFom.setYsnHibetsuSumAreaData(grid2);
                resultFom.setUriakeMokuhyouAreaData(grid3);
                resultFom.setMokuhyouGoukeiAreaData(grid4);
                calc(bCopyFlgReTry, resultFom, yosKinMon, yosKyakuMon);

            }
        }
        resultFom.setYsnHibetsuArea(grid1);
        resultFom.setYsnHibetsuSumAreaData(grid2);
        resultFom.setUriakeMokuhyouAreaData(grid3);
        resultFom.setMokuhyouGoukeiAreaData(grid4);

        return true;
    }

    /**
    * グリッド５の表示データ取得処理.
    * @param resultFom Urjp0010ResultForm
    * @return  List<Urjp001005Dto>
    */
    private List<Urjp001005Dto> getGrd5(Urjp0010ResultForm resultFom) {
        List<Urjp001005Dto> lst = new ArrayList<Urjp001005Dto>();
        // 売上金額予算
        Urjp001005Dto dto1 = new Urjp001005Dto();
        dto1.setYosKinMon("月間売上予算(千円)");
        dto1.setYosKyakuMon(resultFom.getYosKinMon());
        dto1.setUriSakuTai("昨対");
        dto1.setKyakuSakuTai(resultFom.getUriSakuTai());
        dto1.setUriSai("月予差異");
        dto1.setKyakuSai(Long.valueOf(resultFom.getUriSai()));
        lst.add(dto1);
        // 客数予算
        Urjp001005Dto dto2 = new Urjp001005Dto();
        dto2.setYosKinMon("月間客数予算(人)");
        dto2.setYosKyakuMon(resultFom.getYosKyakuMon());
        dto2.setUriSakuTai("昨対");
        dto2.setKyakuSakuTai(resultFom.getKyakuSakuTai());
        dto2.setUriSai("月予差異");
        dto2.setKyakuSai(resultFom.getKyakuSai());
        lst.add(dto2);
        return lst;
    }

    /**
     * グリッド２の表示データ取得処理.
     * 
     * @param lst List<Urjp001002Dto>
     * @return List<Urjp001002Dto>
     */
    private List<Urjp001002Dto> getGrd2(List<Urjp001002Dto> lst) {
        List<Urjp001002Dto> result = new ArrayList<Urjp001002Dto>();
        if (lst != null && lst.size() >= 2) {
            Urjp001002Dto dto1 = new Urjp001002Dto();
            BeanUtils.copyProperties(lst.get(0), dto1);
            dto1.setGouKei2("合　　計");
            result.add(dto1);

            Urjp001002Dto dto2 = new Urjp001002Dto();
            BeanUtils.copyProperties(lst.get(1), dto2);
            dto2.setGouKei2("差　　異");
            dto2.setUriKin(null);
            dto2.setKosei(null);
            dto2.setTenKyaku(null);
            result.add(dto2);
        } else {
            Urjp001002Dto dto = new Urjp001002Dto();
            result.add(dto);
            result.add(dto);
        }
        return result;
    }

    /**
     * グリッド２の表示データ取得処理.
     * @param resultFom Urjp0010ResultForm
     * @param mon String 
     */
    private void getGrd6(Urjp0010ResultForm resultFom, String mon) {
        // 日別部門別予算入力グリッド grdDetail6 行数セット
        List<Urjp001001Dto> grid1 = resultFom.getYsnHibetsuArea();
        List<Urjp001003Dto> grid3 = resultFom.getUriakeMokuhyouAreaData();
        List<Urjp001006Dto> grid6 = resultFom.getGrid6();
        int grd6Row = 0;
        int gsindex = 0;
        int iIdx = 0;
        if ((gsindex + GRD6_ROW) > grid1.size()) {
            grd6Row = GRD6_HEAD_ROW + grid1.size() - gsindex;
        } else {
            grd6Row = GRD6_HEAD_ROW + GRD6_ROW;
        }
        grd6Row = GRD6_HEAD_ROW + grid1.size();
        int grd6Col = grid3.size() + 2;
        grid6.get(0).setField0("予　算");
        grid6.get(1).setField0("合　計");
        grid6.get(2).setField0("差　異");
        grid6.get(3).setField0("日付(曜日)");

        for (int i = 0; i < (grd6Row - GRD6_HEAD_ROW); i++) {
            iIdx = gsindex + i;
            grid6.get(i + GRD6_HEAD_ROW).setField0(
                    mon + "/" + grid1.get(iIdx).getCalDate() + "(" + grid1.get(iIdx).getWeekDay() + ")");
        }
        for (int i = 0; i < grd6Col; i++) {
            Urjp001006Dto dto = grid6.get(3);
            if (i == grd6Col - 1) {
                DataUtil.setValueByMethodName(dto, "setField" + (i + 1), "客数(人)");
            } else if (i == grd6Col - 2) {
                DataUtil.setValueByMethodName(dto, "setField" + (i + 1), "合　計");
            } else {
                // 部門コード取得
                int iChrIndex = grid3.get(i).getBmnCdNm().indexOf('(');
                String sBmnCode = grid3.get(i).getBmnCdNm().substring(0, iChrIndex);
                sBmnCode = sBmnCode.substring(2);
                DataUtil.setValueByMethodName(dto, "setField" + (i + 1), sBmnCode + "部門");
            }
            grid6.set(3, dto);
        }
        resultFom.setGrid6(grid6);
    }

    /**
     * グリッド２の表示データ取得処理.
     * @param resultFom Urjp0010ResultForm
     */
    private void setGrid6(Urjp0010ResultForm resultFom) {
        // 日別部門別予算入力グリッド grdDetail6 行数セット
        List<Urjp001001Dto> grid1 = resultFom.getYsnHibetsuArea();
        List<Urjp001003Dto> grid3 = resultFom.getUriakeMokuhyouAreaData();
        List<Urjp001006Dto> grid6 = resultFom.getGrid6();
        int iIdx = 0;

        // 日別予算明細グリッド(Grid1)のデータをBeanにセット。
        for (int i = 0; i < grid1.size(); i++) {
            iIdx = GRD6_HEAD_ROW + i;
            Urjp001006Dto dto = grid6.get(iIdx);
            DataUtil.setValueByMethodName(dto, "setField" + (grid3.size() + 2), grid1.get(i).getYsnKyaksu()
                    .toString());
            grid6.set(iIdx, dto);
        }
        resultFom.setGrid6(grid6);
    }

    /**
     * 日別部門別予算データ取得処理.
     * @param bAutoCalc boolean 自動計算フラグ
     * @param resultFom Urjp0010ResultForm
     * @param year String
     * @param mon String
     * @param kaisyaCd String
     * @param jigyobuCd String
     * @param tenCd String
     * @return true：処理成功、false：処理失敗
     */
    private boolean setDayBmnPage(boolean bAutoCalc, Urjp0010ResultForm resultFom, String year, String mon,
            String kaisyaCd, String jigyobuCd, String tenCd) {
        List<Urjp001001Dto> grid1 = resultFom.getYsnHibetsuArea();
        List<Urjp001003Dto> grid3 = resultFom.getUriakeMokuhyouAreaData();
        List<Urjp001006Dto> grid6 = resultFom.getGrid6();
        long check = 0;
        long choseiKin = 0;
        String sBmnCode = "";
        String sDay = "";
        int iChrIndex = 0;
        int i = 0;
        int j = 0;
        double value = 0;

        // 自動計算？
        if (bAutoCalc) {
            // 日別金額を計算
            for (i = 0; i < grid1.size(); i++) {
                check = 0;
                Urjp001006Dto dto = grid6.get(GRD6_HEAD_ROW + i);

                // 日別売上金額をセット
                for (j = 1; j < grid3.size(); j++) {
                    // 日別予算×部門別の構成比より取得
                    value =
                            CCNumberUtil.multiplyDouble(grid1.get(i).getYsnUriKin().doubleValue(), grid3.get(j)
                                    .getKosei().doubleValue());

                    long temp = (long) dRound(CCNumberUtil.divideDouble(value, 100, CCNumberUtil.ROUND_HALF_UP), -1);
                    DataUtil.setValueByMethodName(dto, "setField" + (j + 1), String.valueOf(temp));

                    // 自動調整用に合計取得
                    check += temp;
                }
                // 自動調整処理
                choseiKin = grid1.get(i).getYsnUriKin() - check;
                grid6.get(GRD6_HEAD_ROW + i).setField1(String.valueOf(choseiKin));

            }
            // 客数をセット
            for (i = 0; i < grid1.size(); i++) {
                Urjp001006Dto dto = grid6.get(GRD6_HEAD_ROW + i);
                DataUtil.setValueByMethodName(dto, "setField" + (grid3.size() + 2), grid1.get(i).getYsnKyaksu()
                        .toString());
            }
        } else {
            // *** DBよりデータ取得 ***
            // 日別店別部門別予算テーブルより売上データ取得
            Map<String, String> param = new HashMap<String, String>();
            param.put("tenpo", kaisyaCd + jigyobuCd + tenCd);
            param.put("ysnDate", year + mon);
            param.put("jigyo", jigyobuCd);
            param.put("exceptBmn", context.getContextProperty(KEY_EXCEPT_BMN));
            List<U001ybmnResult> list = dao.selectMany("selectu001ybmn08", param, U001ybmnResult.class);

            HashMap<String, U001ybmnResult> u001ybmnResultHashMap = new HashMap<String, U001ybmnResult>();
            for (U001ybmnResult u001ybmnResult : list) {
                u001ybmnResultHashMap.put(u001ybmnResult.getBmnCd() + u001ybmnResult.getYsnDate(), u001ybmnResult);
            }

            // 日別店別部門別予算テーブルより客数データ取得
            Map<String, String> param2 = new HashMap<String, String>();
            param2.put("tenpo", kaisyaCd + jigyobuCd + tenCd);
            param2.put("ysnDate", year + mon);
            List<U001ybmnResult> list2 = dao.selectMany("selectU001ybmn09", param2, U001ybmnResult.class);

            HashMap<String, U001ybmnResult> u001ybmnResultYsnDateMap = new HashMap<String, U001ybmnResult>();
            for (U001ybmnResult u001ybmnResult : list2) {
                u001ybmnResultYsnDateMap.put(u001ybmnResult.getYsnDate(), u001ybmnResult);
            }

            // グリッドデータ配列へセット
            for (i = 0; i < grid1.size(); i++) {
                // 予算年月取得
                sDay = "00" + (i + 1);
                sDay = sDay.substring(sDay.length() - 2);
                sDay = year + mon + sDay;

                for (j = 0; j < grid3.size(); j++) {
                    // 部門コード取得
                    iChrIndex = grid3.get(j).getBmnCdNm().indexOf('(');
                    sBmnCode = grid3.get(j).getBmnCdNm().substring(0, iChrIndex);

                    // 予算金額をセット
                    if (!list.isEmpty()) {
                        U001ybmnResult u001ybmn = u001ybmnResultHashMap.get(sBmnCode + sDay);
                        if (u001ybmn != null) {
                            Urjp001006Dto dto = grid6.get(GRD6_HEAD_ROW + i);
                            DataUtil.setValueByMethodName(dto, "setField" + j, u001ybmn.getYsnUriKin().toString());
                        }
                    }
                }

                // 予算客数をセット
                if (!list2.isEmpty()) {
                    U001ybmnResult u001 = u001ybmnResultYsnDateMap.get(sDay);
                    if (u001 != null) {
                        Urjp001006Dto dto = grid6.get(GRD6_HEAD_ROW + i);
                        DataUtil.setValueByMethodName(dto, "setField" + (grid3.size() + 1), u001.getYsnKyaksu()
                                .toString());
                    }
                }
            }
        }
        resultFom.setGrid6(grid6);
        return true;
    }

    /**
     *  月間予算入力チェック.
     * @param iCheckKbn int 0:月間予算登録有無チェック　1:入力月間予算との整合チェック
     * @param resultFom Urjp0010ResultForm
     * @return true：処理成功、false：処理失敗
     */
    private boolean chkMonYosan(int iCheckKbn, Urjp0010ResultForm resultFom) {
        switch (iCheckKbn) {
        case 0:
            // 月間予算金額有無チェック
            if (resultFom.getDbYosKinMon() == 0) {
                return false;
            }
            break;
        case 1:
            // 入力された月間予算と月別部門別予算テーブルの月間予算を比較
            if (resultFom.getDbYosKinMon().compareTo(resultFom.getYosKinMon()) != 0) {
                return false;
            }
            if (resultFom.getDbYosKyakuMon() != resultFom.getYosKyakuMon()) {
                return false;
            }
            break;
        default:
            return true;
        }

        return true;
    }

    /**
    * 日別部門別予算データ計算.
    * @param resultFom Urjp0010ResultForm
    * @return true：処理成功、false：処理失敗
    */
    private boolean calcDayBmn(Urjp0010ResultForm resultFom) {
        List<Urjp001001Dto> grid1 = resultFom.getYsnHibetsuArea();
        List<Urjp001003Dto> grid3 = resultFom.getUriakeMokuhyouAreaData();
        List<Urjp001006Dto> grid6 = resultFom.getGrid6();

        long lCheck = 0;
        int i = 0;
        int j = 0;

        // *** 縦・横合計を計算しセット
        // 縦（部門別）合計、差異セット
        String fieldContent = null;
        for (j = 0; j < grid3.size(); j++) {
            lCheck = 0;
            for (i = GRD6_HEAD_ROW; i < GRD6_HEAD_ROW + grid1.size(); i++) {
                Urjp001006Dto dto = grid6.get(i);
                fieldContent = (String) DataUtil.getValueByMethodName(dto, "getField" + (j + 1));
                lCheck += Long.parseLong(fieldContent);
            }
            Urjp001006Dto dto = grid6.get(0);
            DataUtil.setValueByMethodName(dto, "setField" + (j + 1), grid3.get(j).getYsnUriKin().toString());

            Urjp001006Dto dto2 = grid6.get(1);
            DataUtil.setValueByMethodName(dto2, "setField" + (j + 1), String.valueOf(lCheck));

            Urjp001006Dto dto3 = grid6.get(2);
            DataUtil.setValueByMethodName(dto3, "setField" + (j + 1),
                    String.valueOf(lCheck - grid3.get(j).getYsnUriKin().longValue()));
        }

        // 横（日別）合計セット
        for (i = GRD6_HEAD_ROW; i < GRD6_HEAD_ROW + grid1.size(); i++) {
            lCheck = 0;
            for (j = 0; j < grid3.size(); j++) {
                Urjp001006Dto dto = grid6.get(i);
                fieldContent = (String) DataUtil.getValueByMethodName(dto, "getField" + (j + 1));
                lCheck += Long.parseLong(fieldContent);
            }
            Urjp001006Dto dto = grid6.get(i);
            DataUtil.setValueByMethodName(dto, "setField" + (grid3.size() + 1), String.valueOf(lCheck));
        }

        // 月合計を取得
        lCheck = 0;
        for (i = GRD6_HEAD_ROW; i < GRD6_HEAD_ROW + grid1.size(); i++) {
            Urjp001006Dto dto = grid6.get(i);
            fieldContent = (String) DataUtil.getValueByMethodName(dto, "getField" + (grid3.size() + 1));
            lCheck += Long.parseLong(fieldContent);
        }

        // 月間予算金額セット
        Urjp001006Dto dto = grid6.get(0);
        DataUtil.setValueByMethodName(dto, "setField" + (grid3.size() + 1), resultFom.getYosKinMon().toString());

        // 縦横計をセット
        Urjp001006Dto dto2 = grid6.get(1);
        DataUtil.setValueByMethodName(dto2, "setField" + (grid3.size() + 1), String.valueOf(lCheck));

        // 月間予算金額差異セット
        Urjp001006Dto dto3 = grid6.get(2);
        DataUtil.setValueByMethodName(dto3, "setField" + (grid3.size() + 1),
                String.valueOf(lCheck - resultFom.getYosKinMon().longValue()));

        // 客合計を取得
        lCheck = 0;
        for (i = GRD6_HEAD_ROW; i < GRD6_HEAD_ROW + grid1.size(); i++) {
            Urjp001006Dto dto4 = grid6.get(i);
            fieldContent = (String) DataUtil.getValueByMethodName(dto4, "getField" + (grid3.size() + 2));
            lCheck += Long.parseLong(fieldContent);
        }

        // 月間予算金額セット
        Urjp001006Dto dto5 = grid6.get(0);
        DataUtil.setValueByMethodName(dto5, "setField" + (grid3.size() + 2), resultFom.getYosKyakuMon().toString());

        // 縦横計をセット
        Urjp001006Dto dto6 = grid6.get(1);
        DataUtil.setValueByMethodName(dto6, "setField" + (grid3.size() + 2), String.valueOf(lCheck));

        // 月間予算金額差異セット
        Urjp001006Dto dto7 = grid6.get(2);
        DataUtil.setValueByMethodName(dto7, "setField" + (grid3.size() + 2),
                String.valueOf(lCheck - resultFom.getYosKyakuMon().longValue()));

        resultFom.setGrid6(grid6);

        return true;
    }

    /**
    * 日別店別部門別予算テーブル 新規登録処理.
    * @param resultFom Urjp0010ResultForm
    * @param year String
    * @param mon String
    * @param kaisyaCd String
    * @param jigyobuCd String
    * @param tenCd String
    * @return true：処理成功、false：処理失敗
    */
    @Transactional
    public Result doInsert(Urjp0010ResultForm resultFom, String year, String mon, String kaisyaCd, String jigyobuCd,
            String tenCd) {
        List<Urjp001001Dto> grid1 = resultFom.getYsnHibetsuArea();
        List<Urjp001003Dto> grid3 = resultFom.getUriakeMokuhyouAreaData();
        List<Urjp001006Dto> grid6 = resultFom.getGrid6();
        DateTime dt = new DateTime();
        int effectUpdate = 0;
        // インサート処理
        String sBmnCode = "";
        String sDay = "";
        String[] sYWWDataArray;
        int iChrIndex = 0;

        // 店舗コードの取得
        String sTenCode = kaisyaCd + jigyobuCd + tenCd;

        // 共通情報取得
        String sCmnTantoCd = context.getTantoCode();
        String sCmnTermId = context.getTermName();
        String sCmnInsdd = dt.toString(DATETIME_FORMAT_DATE);
        String sCmnUpddd = sCmnInsdd;
        String sCmnUpdtime = dt.toString(DATETIME_FORMAT_TIME);

        for (int i = 0; i < grid1.size(); i++) {
            // 日付の取得
            sDay = "00" + Integer.toString(i + 1);
            sDay = sDay.substring(sDay.length() - 2);
            sDay = year + mon + sDay;

            // 年度、週番号、曜日を取得
            sYWWDataArray = (String[]) getYWW(sDay, sTenCode);
            for (int j = 0; j < grid3.size(); j++) {
                // 部門コード取得
                iChrIndex = grid3.get(j).getBmnCdNm().indexOf('(');
                sBmnCode = grid3.get(j).getBmnCdNm().substring(0, iChrIndex);
                U001ybmn example = new U001ybmn();
                example.setTenCd(sTenCode);
                example.setBmnCd(sBmnCode);
                example.setYsnDate(sDay);
                example.setYsnYyyy(year);
                example.setYsnMm(mon);
                example.setYsnNendo(sYWWDataArray[0]);
                example.setYsnWeekNo(sYWWDataArray[1]);
                example.setYsnWeekDay(sYWWDataArray[2]);
                Urjp001006Dto dto = grid6.get(GRD6_HEAD_ROW + i);
                String fieldContent = (String) DataUtil.getValueByMethodName(dto, "getField" + (j + 1));
                example.setYsnUriKin(Long.parseLong(fieldContent + "000"));

                example.setYsnKyaksu(0);
                example.setYsnSndFlg("0");
                example.setJskSndFlg("0");
                example.setCmnTantoCd(sCmnTantoCd);
                example.setCmnTermId(sCmnTermId);
                example.setCmnInsdd(sCmnInsdd);
                example.setCmnUpddd(sCmnUpddd);
                example.setCmnUpdtime(sCmnUpdtime);
                effectUpdate = this.u001ybmnMapper.insertSelective(example);
                if (effectUpdate == 0) {
                    throw new ChaseException(Messages.get(CCMessageConst.MSG_KEY_EXCEPTION_INSERT_NO_RECORD));
                }
            }

            // 客数をセット
            U001ybmn example = new U001ybmn();
            example.setTenCd(sTenCode);
            example.setBmnCd("00000");
            example.setYsnDate(sDay);
            example.setYsnYyyy(year);
            example.setYsnMm(mon);
            example.setYsnNendo(sYWWDataArray[0]);
            example.setYsnWeekNo(sYWWDataArray[1]);
            example.setYsnWeekDay(sYWWDataArray[2]);
            Urjp001006Dto dto = grid6.get(GRD6_HEAD_ROW + i);

            String fieldContent1 = (String) DataUtil.getValueByMethodName(dto, "getField" + (grid3.size() + 1));
            example.setYsnUriKin(Long.parseLong(fieldContent1 + "000"));

            String fieldContent2 = (String) DataUtil.getValueByMethodName(dto, "getField" + (grid3.size() + 2));
            example.setYsnKyaksu(Integer.parseInt(fieldContent2));

            example.setYsnSndFlg("0");
            example.setJskSndFlg("0");
            example.setCmnTantoCd(sCmnTantoCd);
            example.setCmnTermId(sCmnTermId);
            example.setCmnInsdd(sCmnInsdd);
            example.setCmnUpddd(sCmnUpddd);
            example.setCmnUpdtime(sCmnUpdtime);
            effectUpdate = this.u001ybmnMapper.insertSelective(example);
            if (effectUpdate == 0) {
                throw new ChaseException(Messages.get(CCMessageConst.MSG_KEY_EXCEPTION_INSERT_NO_RECORD));
            }
        }

        return created();
    }

    /**
     * 日別店別部門別予算テーブル 更新処理.
     * @param year String
     * @param mon String
     * @param kaisyaCd String
     * @param jigyobuCd String
     * @param tenCd String
     * @return true：処理成功、false：処理失敗
     */
    @Transactional
    public Result doDelete(String year, String mon, String kaisyaCd, String jigyobuCd, String tenCd) {
        Map<String, String> paraDel = new HashMap<String, String>();
        paraDel.put("tenCd", kaisyaCd + jigyobuCd + tenCd);
        paraDel.put("ysnDate", year + mon);
        int effectUpdate = 0;
        effectUpdate = dao.deleteOne("deleteU001ybmn", paraDel);
        if (effectUpdate == 0) {
            throw new ChaseException(Messages.get(CCMessageConst.MSG_KEY_EXCEPTION_DELETE_NO_RECORD));
        }

        return ok();
    }

    /**
     * 日別店別部門別予算テーブル 更新処理.
     * @param resultFom Urjp0010ResultForm
     * @param year String
     * @param mon String
     * @param kaisyaCd String
     * @param jigyobuCd String
     * @param tenCd String
     * @return true：処理成功、false：処理失敗
     */
    @Transactional
    public Result doUpdate(Urjp0010ResultForm resultFom, String year, String mon, String kaisyaCd, String jigyobuCd,
            String tenCd) {
        List<Urjp001001Dto> grid1 = resultFom.getYsnHibetsuArea();
        List<Urjp001003Dto> grid3 = resultFom.getUriakeMokuhyouAreaData();
        List<Urjp001006Dto> grid6 = resultFom.getGrid6();
        DateTime dt = new DateTime();
        int effectUpdate = 0;
        // インサート処理
        String sBmnCode = "";
        String sDay = "";
        String sTenKyu = "";
        String[] sYWWDataArray;
        int iChrIndex = 0;

        // 店舗コードの取得c
        String sTenCode = kaisyaCd + jigyobuCd + tenCd;

        // 共通情報取得
        String sCmnTantoCd = context.getTantoCode();
        String sCmnTermId = context.getTermName();
        String sCmnInsdd = dt.toString(DATETIME_FORMAT_DATE);
        String sCmnUpddd = sCmnInsdd;
        String sCmnUpdtime = dt.toString(DATETIME_FORMAT_TIME);

        for (int i = 0; i < grid1.size(); i++) {

            // 日付の取得
            sDay = "00" + Integer.toString(i + 1);
            sDay = sDay.substring(sDay.length() - 2);
            sDay = year + mon + sDay;

            // 年度、週番号、曜日を取得
            // 年度、週番号、曜日を取得
            sYWWDataArray = (String[]) getYWW(sDay, sTenCode);
            sTenKyu = " ";
            // 売上金額をセット
            for (int j = 0; j < grid3.size(); j++) {
                // 部門コード取得
                iChrIndex = grid3.get(j).getBmnCdNm().indexOf('(');
                sBmnCode = grid3.get(j).getBmnCdNm().substring(0, iChrIndex);
                // 更新データ存在チェック
                int iYosanCnt = this.count(sDay, sBmnCode, kaisyaCd, jigyobuCd, tenCd);

                if (iYosanCnt > 0) {
                    U001ybmn example = new U001ybmn();
                    example.setTenrFlg(sTenKyu);

                    Urjp001006Dto dto = grid6.get(GRD6_HEAD_ROW + i);
                    String fieldContent = (String) DataUtil.getValueByMethodName(dto, "getField" + (j + 1));
                    example.setYsnUriKin(Long.parseLong(fieldContent + "000"));

                    example.setYsnKyaksu(0);
                    example.setYsnSndFlg("0");
                    example.setCmnTantoCd(sCmnTantoCd);
                    example.setCmnTermId(sCmnTermId);
                    example.setCmnUpddd(sCmnUpddd);
                    example.setCmnUpdtime(sCmnUpdtime);
                    U001ybmnExample where = new U001ybmnExample();
                    where.createCriteria().andTenCdEqualTo(sTenCode).andYsnDateEqualTo(sDay)
                            .andBmnCdEqualTo(sBmnCode);
                    effectUpdate = this.u001ybmnMapper.updateByExampleSelective(example, where);
                    if (effectUpdate == 0) {
                        throw new ChaseException(Messages.get(CCMessageConst.MSG_KEY_EXCEPTION_UPDATE_NO_RECORD));
                    }
                } else {
                    U001ybmn example = new U001ybmn();
                    example.setTenCd(sTenCode);
                    example.setBmnCd(sBmnCode);
                    example.setYsnDate(sDay);
                    example.setYsnYyyy(year);
                    example.setYsnMm(mon);
                    example.setYsnNendo(sYWWDataArray[0]);
                    example.setYsnWeekNo(sYWWDataArray[1]);
                    example.setYsnWeekDay(sYWWDataArray[2]);
                    Urjp001006Dto dto = grid6.get(GRD6_HEAD_ROW + i);

                    String fieldContent = (String) DataUtil.getValueByMethodName(dto, "getField" + (j + 1));
                    example.setYsnUriKin(Long.parseLong(fieldContent + "000"));
                    example.setYsnKyaksu(0);
                    example.setYsnSndFlg("0");
                    example.setJskSndFlg("0");
                    example.setCmnTantoCd(sCmnTantoCd);
                    example.setCmnTermId(sCmnTermId);
                    example.setCmnInsdd(sCmnInsdd);
                    example.setCmnUpddd(sCmnUpddd);
                    example.setCmnUpdtime(sCmnUpdtime);
                    effectUpdate = this.u001ybmnMapper.insertSelective(example);
                    if (effectUpdate == 0) {
                        throw new ChaseException(Messages.get(CCMessageConst.MSG_KEY_EXCEPTION_INSERT_NO_RECORD));
                    }
                }
            }

            // 客数をセット
            // 更新データ存在チェック
            int iYosanCnt2 = (int) this.count(sDay, "00000", kaisyaCd, jigyobuCd, tenCd);

            if (iYosanCnt2 > 0) {
                U001ybmn example = new U001ybmn();
                example.setTenrFlg(sTenKyu);

                Urjp001006Dto dto = grid6.get(GRD6_HEAD_ROW + i);

                String fieldContent1 = (String) DataUtil.getValueByMethodName(dto, "getField" + (grid3.size() + 1));
                example.setYsnUriKin(Long.parseLong(fieldContent1 + "000"));

                String fieldContent2 = (String) DataUtil.getValueByMethodName(dto, "getField" + (grid3.size() + 2));
                example.setYsnKyaksu(Integer.parseInt(fieldContent2));

                example.setYsnSndFlg("0");
                example.setCmnTantoCd(sCmnTantoCd);
                example.setCmnTermId(sCmnTermId);
                example.setCmnUpddd(sCmnUpddd);
                example.setCmnUpdtime(sCmnUpdtime);
                U001ybmnExample where = new U001ybmnExample();
                where.createCriteria().andTenCdEqualTo(sTenCode).andYsnDateEqualTo(sDay).andBmnCdEqualTo("00000");
                effectUpdate = this.u001ybmnMapper.updateByExampleSelective(example, where);
                if (effectUpdate == 0) {
                    throw new ChaseException(Messages.get(CCMessageConst.MSG_KEY_EXCEPTION_UPDATE_NO_RECORD));
                }
            } else {
                U001ybmn example = new U001ybmn();
                example.setTenCd(sTenCode);
                example.setBmnCd("00000");
                example.setYsnDate(sDay);
                example.setYsnYyyy(year);
                example.setYsnMm(mon);
                example.setYsnNendo(sYWWDataArray[0]);
                example.setYsnWeekNo(sYWWDataArray[1]);
                example.setYsnWeekDay(sYWWDataArray[2]);
                Urjp001006Dto dto = grid6.get(GRD6_HEAD_ROW + i);

                String fieldContent1 = (String) DataUtil.getValueByMethodName(dto, "getField" + (grid3.size() + 1));
                example.setYsnUriKin(Long.parseLong(fieldContent1 + "000"));

                String fieldContent2 = (String) DataUtil.getValueByMethodName(dto, "getField" + (grid3.size() + 2));
                example.setYsnKyaksu(Integer.parseInt(fieldContent2));

                example.setYsnSndFlg("0");
                example.setJskSndFlg("0");
                example.setCmnTantoCd(sCmnTantoCd);
                example.setCmnTermId(sCmnTermId);
                example.setCmnInsdd(sCmnInsdd);
                example.setCmnUpddd(sCmnUpddd);
                example.setCmnUpdtime(sCmnUpdtime);
                effectUpdate = this.u001ybmnMapper.insertSelective(example);
                if (effectUpdate == 0) {
                    throw new ChaseException(Messages.get(CCMessageConst.MSG_KEY_EXCEPTION_INSERT_NO_RECORD));
                }
            }
        }

        return ok();
    }

    /**
    * 日別店別部門別予算データ存在チェック.
    * @param sDay String
    * @param sBmnCode String
    * @param kaisyaCd String
    * @param jigyobuCd String
    * @param tenCd String
    * @return true：処理成功、false：処理失敗
    */
    private int count(String sDay, String sBmnCode, String kaisyaCd, String jigyobuCd, String tenCd) {

        // 登録データ存在チェック
        U001ybmnExample example = new U001ybmnExample();
        example.createCriteria().andTenCdEqualTo(kaisyaCd + jigyobuCd + tenCd).andYsnDateEqualTo(sDay)
                .andBmnCdEqualTo(sBmnCode);
        int countValue = this.u001ybmnMapper.countByExample(example);
        return countValue;
    }

    /**
    * 他店舗実績（昨年）取得処理.
    * @param resultFom Urjp0010ResultForm
    * @param year String
    * @param mon String
    * @param kaisyaCd String
    * @param jigyobuCd String
    * @param extTenCd String
    * @param yosKinMon long
    * @param yosKyakuMon int
    * @return true：処理成功、false：処理失敗
    */
    private boolean executeExtTenCopy(Urjp0010ResultForm resultFom, String year, String mon, String kaisyaCd,
            String jigyobuCd, String extTenCd, long yosKinMon, int yosKyakuMon) {
        List<Urjp001001Dto> grid1 = resultFom.getYsnHibetsuArea();
        Map<String, String> param = new HashMap<String, String>();
        param.put("calDate", year + mon + "__");
        param.put("bmnCd", context.getContextProperty(KEY_EXCEPT_BMN));
        param.put("tenCd", kaisyaCd + jigyobuCd + extTenCd);
        List<U011tenjM019ymdmU012bmnjResult> ls =
                dao.selectMany("selectU011tenjM019ymdmU012bmnj07", param, U011tenjM019ymdmU012bmnjResult.class);
        if (!ls.isEmpty()) {
            // グリッドデータ配列へ昨年実績セット
            for (int i = 0; i < grid1.size(); i++) {
                for (int j = 0; j < ls.size(); j++) {
                    if (grid1.get(i).getCalDate().equals(ls.get(j).getDt().substring(6, 8))) {
                        grid1.get(i).setUriKin(ls.get(j).getSakKin());
                        grid1.get(i).setTenKyaku(ls.get(j).getSakKyaku());
                        break;
                    }
                }
            }
        } else {
            for (int i = 0; i < grid1.size(); i++) {
                grid1.get(i).setUriKin(new Long(0));
                grid1.get(i).setTenKyaku(new Long(0));
            }
        }
        resultFom.setYsnHibetsuArea(grid1);
        // 計算処理コール
        calc(true, resultFom, yosKinMon, yosKyakuMon);

        return true;
    }

    /**
    * 日別店別予算グリッド（Grid6)データの入力エラーチェック処理.
    * @param resultFom Urjp0010ResultForm
    * @return true：入力チェックＯＫ、false：入力チェックＮＧ
    */
    private boolean doCheckGrd6(Urjp0010ResultForm resultFom) {
        List<Urjp001001Dto> grid1 = resultFom.getYsnHibetsuArea();
        List<Urjp001003Dto> grid3 = resultFom.getUriakeMokuhyouAreaData();
        List<Urjp001006Dto> grid6 = resultFom.getGrid6();
        boolean bCheck = true;
        String value = null;
        int iPageRow = 0;

        // グリッドデータのチェック
        for (int i = GRD6_HEAD_ROW; i < GRD6_HEAD_ROW + grid1.size(); i++) {
            Urjp001006Dto dto = grid6.get(i);
            for (int j = 0; j < grid3.size() + 2; j++) {

                // 合計列以外をチェック
                if (j != grid3.size()) {
                    // データ取得
                    value = (String) DataUtil.getValueByMethodName(dto, "getField" + (j + 1));

                    if (value == null) {
                        value = "0";
                    }

                    // コントロール名（グリッド位置）セット
                    if (i > (GRD6_HEAD_ROW + GRD6_ROW - 1)) {
                        iPageRow = GRD6_ROW;
                    } else {
                        iPageRow = i;
                    }

                    // レングスチェック
                    if (CCStringUtil.getByteLen(value) > 7) {
                        // 最初にエラーが起きたページと違うときはチェックを終了
                        errRes.addErrorInfo("field" + (j + 1) + iPageRow,
                                CCMessageConst.MSG_KEY_DATA_CHECK_LENTH_ERROR);
                        bCheck = false;
                    }

                    // 数値チェック
                    if (!CCNumberUtil.isNumeric(value)) {
                        // 最初にエラーが起きたページと違うときはチェックを終了
                        errRes.addErrorInfo("field" + (j + 1) + iPageRow,
                                CCMessageConst.MSG_KEY_INPUT_NUMBER_DATA_INVALID);
                        bCheck = false;
                    }

                    // マイナス値チェック
                    if (Integer.parseInt(value) < 0) {

                        // 最初にエラーが起きたページと違うときはチェックを終了
                        errRes.addErrorInfo("field" + (j + 1) + iPageRow,
                                CCMessageConst.MSG_KEY_DATA_PARSE_INT_ERROR);
                        bCheck = false;
                    }

                }
            }
        }

        return bCheck;
    }

    /**
    * プロパティ値取得（配列分割）.
    * @param propertieVal String
    * @return List<String> 取得値
    */
    public List<String> getPropertieArray(String propertieVal) {
        List<String> sValueArray = new ArrayList<String>();
        String sValue = "";
        int iIdx = 0;
        int iSep = 0;

        if (propertieVal.equals("")) {
            return null;
        }

        String sKeyValue = propertieVal;

        // 値の分割処理
        while (iSep != -1) {
            iSep = sKeyValue.indexOf(',', iIdx);
            if (iSep != -1) {
                sValue = sKeyValue.substring(iIdx, iSep);
                iIdx = iSep + 1;
            } else {
                sValue = sKeyValue.substring(iIdx);
            }
            sValueArray.add(sValue);
        }

        return sValueArray;
    }
}
