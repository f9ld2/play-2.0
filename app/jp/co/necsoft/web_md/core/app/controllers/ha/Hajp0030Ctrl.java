// /////////////////////////////////////////////////////////////////////
// Copyright(C) 2015 NEC Solution Innovators, Ltd. All Rights Reserved.
// /////////////////////////////////////////////////////////////////////

/*
 * ====================================================================
 * 
 * 機能名称 : 物流スケジュールメンテ
 * 
 * 改版履歴
 * 
 * Rev. 改版年月日 改版者名 内容
 * 
 * 1.0 2014.04.18 ToanPQ 新規作成
 * 
 * ===================================================================
 */

package jp.co.necsoft.web_md.core.app.controllers.ha;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;

import jp.co.necsoft.web_md.common.ChaseException;
import jp.co.necsoft.web_md.common.mybatis.dao.MyBatisSqlMapDao;
import jp.co.necsoft.web_md.core.app.dto.ha.M011trimExecuteTriName;
import jp.co.necsoft.web_md.core.app.dto.ha.M011trimExecuteTriNameResult;
import jp.co.necsoft.web_md.core.app.forms.ha.Hajp0030CondForm;
import jp.co.necsoft.web_md.core.app.forms.ha.Hajp0030GridData;
import jp.co.necsoft.web_md.core.app.forms.ha.Hajp0030ResultForm;
import jp.co.necsoft.web_md.core.common.CCMessageConst;
import jp.co.necsoft.web_md.core.common.CCSecurityAuthenticator;
import jp.co.necsoft.web_md.core.common.CCSystemContext;
import jp.co.necsoft.web_md.core.common.biz.si.CCSICommon;
import jp.co.necsoft.web_md.core.common.forms.ErrorInfo;
import jp.co.necsoft.web_md.core.common.forms.ErrorResponse;
import jp.co.necsoft.web_md.core.common.mybatis.dao.H040btrsMapper;
import jp.co.necsoft.web_md.core.common.mybatis.dto.H040btrs;
import jp.co.necsoft.web_md.core.common.mybatis.dto.H040btrsExample;
import jp.co.necsoft.web_md.core.common.utils.CCDateUtil;
import jp.co.necsoft.web_md.core.common.utils.CCNumberUtil;
import jp.co.necsoft.web_md.core.common.utils.CCStringUtil;
import jp.co.necsoft.web_md.core.common.utils.DataUtil;
import play.data.Form;
import play.i18n.Messages;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;

/**
 * 物流スケジュールマスタメンテのControllerクラス
 *
 */
@Security.Authenticated(CCSecurityAuthenticator.class)
public class Hajp0030Ctrl extends Controller {
    /** Error Response */
    @Inject
    private ErrorResponse errRes;

    /**H040btrs Mapper*/
    @Inject
    private H040btrsMapper h040btrsMapper;

    /** MyBatis Sql Map Dao */
    @Inject
    private MyBatisSqlMapDao mybatisDao;

    /** CCSICommon control. */
    @Inject
    private CCSICommon cCSICommon;

    /** CCSystemContext */
    @Inject
    private CCSystemContext context;

    /**月の日数*/
    private static final int MAX_DAY_OF_MONTH = 31;

    /**表示するセル*/
    private static final int MAX_CELL = 42;

    /**週の日数*/
    private static final int DAY_OF_WEEK = 7;

    /**便Noチェック1*/
    private static final String FIRST_BIN_VALUE = "1";

    /**便Noチェック2*/
    private static final String SECOND_BIN_VALUE = "2";

    /**便Noチェック3*/
    private static final String THIRD_BIN_VALUE = "3";

    /**
    * 引数とリクエスト(パラメータ)を条件にDBからデータ(複数件)を検索し返却する。*
    * @param yyyy 発注年チェック
    * @param mm 発注月チェック
    * @param triCd 取引先コードチェック
    * @param bin 便Noチェック
    * @return クライアントへ返却する結果
    */
    public Result query(String yyyy, String mm, String triCd, String bin) {
        Form<Hajp0030CondForm> emptyForm = new Form<Hajp0030CondForm>(Hajp0030CondForm.class);
        Form<Hajp0030CondForm> reqForm = emptyForm.bindFromRequest();
        if (reqForm.hasErrors()) {
            errRes.addErrorInfo(CCMessageConst.MSG_KEY_VALIDATION_ERROR);
            errRes.addErrorInfo(reqForm.errors());
            return badRequest(Json.toJson(errRes));
        } else {
            // ヘッダデータを取得する
            Hajp0030CondForm condForm = reqForm.get();

            // 発注年月チェック
            String hachu = yyyy + mm;

            if (!doChkHead(yyyy, mm, triCd, bin)) {
                return badRequest(Json.toJson(errRes));
            }

            // 発注年月チェック
            String[] hatFromToArr = editHatLimit();
            if (hatFromToArr == null) {
                errRes.addErrorInfo(CCMessageConst.MSG_KEY_SYSTEM_DAY_NOT_REGISTERED);
                return badRequest(Json.toJson(errRes));
            }

            if (hachu.compareTo(hatFromToArr[0]) < 0 || hachu.compareTo(hatFromToArr[1]) > 0) {
                errRes.addErrorInfo("yyyy", CCMessageConst.MSG_KEY_HACHU_FROM_TO_INPUT_NOT_MATCH_TYPE);
                errRes.addErrorInfo("mm", CCMessageConst.MSG_KEY_HACHU_FROM_TO_INPUT_NOT_MATCH_TYPE);
                errRes.getErrors().add(0, errRes.getErrorInfo(CCMessageConst.MSG_KEY_VALIDATION_ERROR));
                return badRequest(Json.toJson(errRes));
            }

            // 検索条件をセット
            H040btrsExample h040btrsExample = new H040btrsExample();
            h040btrsExample.createCriteria().andYyyymmEqualTo(hachu).andTriCdEqualTo(triCd).andBinEqualTo(bin);

            // クライアントへ返却する結果
            List<Hajp0030ResultForm> resultList = new ArrayList<Hajp0030ResultForm>();

            // 結果フォーム
            Hajp0030ResultForm hajp0030ResultForm = new Hajp0030ResultForm();
            hajp0030ResultForm.setTriCd(triCd);
            hajp0030ResultForm.setHakkoDay(hachu);
            hajp0030ResultForm.setBin(bin);
            hajp0030ResultForm.setYyyy(yyyy);
            hajp0030ResultForm.setMm(mm);

            int iYear = Integer.parseInt(yyyy);
            int iMonth = Integer.parseInt(mm);
            int iLastDay = CCDateUtil.getLastDay(iYear, iMonth);
            int iDow = CCDateUtil.getSysDowInt(hachu + "01");
            int iDay = 1;

            // 登録データ取得処理を行う。
            int j = 1;

            List<H040btrs> h040btrsList = h040btrsMapper.selectByExample(h040btrsExample);

            // 定番発注と納品日グリッド
            List<Hajp0030GridData> gridData = new ArrayList<Hajp0030GridData>();
            Hajp0030GridData recordData = new Hajp0030GridData();

            // 検索結果のレコードを取得する。（1レコード）
            H040btrs firstRecord = null;

            if (h040btrsList == null || h040btrsList.size() == 0) {
                if (!condForm.isSearchEditMode()) {
                    // 「C0253」メッセージが表示される。
                    errRes.addErrorInfo(CCMessageConst.MSG_KEY_NOT_EXISTS);
                    return badRequest(Json.toJson(errRes));
                } else {
                    hajp0030ResultForm.setFlgNewRecord(true);
                }
            } else {
                firstRecord = h040btrsList.get(0);
                hajp0030ResultForm.setCmnTantoCd(firstRecord.getCmnTantoCd());
                hajp0030ResultForm.setCmnTermId(firstRecord.getCmnTermId());
                hajp0030ResultForm.setCmnInsdd(firstRecord.getCmnInsdd());
                hajp0030ResultForm.setCmnUpddd(firstRecord.getCmnUpddd());
                hajp0030ResultForm.setCmnUpdtime(firstRecord.getCmnUpdtime());
            }

            String funcNum = StringUtils.EMPTY;
            Boolean isFocus = false;
            for (int i = 1; i <= MAX_CELL; i++) {
                if (iDay < 10) {
                    funcNum = "0" + iDay;
                } else {
                    funcNum = String.valueOf(iDay);
                }

                if ((iDay <= iLastDay) && (i >= iDow)) {
                    if (!hajp0030ResultForm.isFlgNewRecord()) {
                        // 納品日をセット
                        String teiHatDd =
                                (String) DataUtil.getValueByMethodName(firstRecord, "getTeiHatDd" + funcNum);
                        if (CCStringUtil.isEmpty(teiHatDd)) {
                            teiHatDd = StringUtils.EMPTY;
                        }
                        
                        DataUtil.setValueByMethodName(recordData, "setTeiHatDd" + j, teiHatDd);

                        String tokHatDd =
                                (String) DataUtil.getValueByMethodName(firstRecord, "getTokHatDd" + funcNum);
                        if (CCStringUtil.isEmpty(tokHatDd)) {
                            tokHatDd = StringUtils.EMPTY;
                        }
                        DataUtil.setValueByMethodName(recordData, "setTokHatDd" + j, tokHatDd);
                    }

                    // Focusをセット
                    if (!isFocus) {
                        isFocus = true;
                        DataUtil.setValueByMethodName(recordData, "setFocus" + j, new Boolean(isFocus));
                    }

                    // 日付をセット
                    DataUtil.setValueByMethodName(recordData, "setDd" + j, String.valueOf(iDay));

                    // teiNhnDd1とtokNhnDd1の表示フラグをセット
                    DataUtil.setValueByMethodName(recordData, "setReadOnly" + j, new Boolean(false));

                    iDay++;
                } else {
                    // 納品日をセット
                    DataUtil.setValueByMethodName(recordData, "setTeiHatDd" + j, StringUtils.EMPTY);
                    DataUtil.setValueByMethodName(recordData, "setTokHatDd" + j, StringUtils.EMPTY);

                    // 日付をセット
                    DataUtil.setValueByMethodName(recordData, "setDd" + j, StringUtils.EMPTY);

                    // teiNhnDd1とtokNhnDd1の表示フラグをセット
                    DataUtil.setValueByMethodName(recordData, "setReadOnly" + j, new Boolean(true));
                }

                if (i % DAY_OF_WEEK == 0) {
                    j = 1;
                    gridData.add(recordData);
                    recordData = new Hajp0030GridData();
                } else {
                    j++;
                }
            }

            // 定番発注と納品日グリッドをセット
            hajp0030ResultForm.setGridData(gridData);

            if (!executeTriName(hajp0030ResultForm, hachu, triCd, bin)) {
                errRes.addErrorInfo("triCd", CCMessageConst.MSG_KEY_NOT_REGISTERED_NO_PARAM);
                return badRequest(Json.toJson(errRes));
            }
            resultList.add(hajp0030ResultForm);

            return ok(Json.toJson(resultList));
        }
    }

    /**
          * 　入力項目チェック処理
    * <p>
    * 機能概要：<br>
    * ヘッダの入力データをチェックします。
    * <p> 
     * @param yyyy 発注年チェック
     * @param mm 発注月チェック
     * @param triCd 取引先コードチェック
     * @param bin 便Noチェック
    * @return true：処理成功、false：処理失敗
    */
    private boolean doChkHead(String yyyy, String mm, String triCd, String bin) {
        boolean blFlg = true;
        String hachu = yyyy + mm;

        // 発注年月チェック
        if (!CCNumberUtil.isNumeric(hachu)) {
            errRes.addErrorInfo("yyyy", CCMessageConst.MSG_KEY_INPUT_FOUR_DIGITS);
            errRes.addErrorInfo("mm", CCMessageConst.MSG_KEY_LIMIT_MONTH_DAY);
            blFlg = false;

        } else {
            int iMonth = Integer.parseInt(mm);
            if (iMonth < 1 || iMonth > 12) {
                errRes.addErrorInfo("mm", CCMessageConst.MSG_KEY_INPUT_INVALID);
                blFlg = false;
            }
        }

        // 便Noチェック
        if (!FIRST_BIN_VALUE.equals(bin) && !SECOND_BIN_VALUE.equals(bin) && !THIRD_BIN_VALUE.equals(bin)) {
            errRes.addErrorInfo("bin", CCMessageConst.MSG_KEY_ERROR_STOOLS);
            blFlg = false;
        }

        if (!blFlg) {
            errRes.getErrors().add(0, errRes.getErrorInfo(CCMessageConst.MSG_KEY_VALIDATION_ERROR));
        }
        return blFlg;
    }

    /**
     * データのエラーチェック処理を行います。<BR>
     * @param yyyy 発注年チェック
     * @param mm 発注月チェック
     * @param triCd 取引先コードチェック
     * @param bin 便Noチェック
     * @param hajp0030ResultForm 結果フォーム
     * @return true：処理成功、false：処理失敗
    */
    private boolean doCheckData(String yyyy, String mm, String triCd, String bin,
            Hajp0030ResultForm hajp0030ResultForm) {
        // 定番納品日チェックする
        boolean blFlg = true;

        // ヘッダの入力データをチェックする
        if (!doChkHead(yyyy, mm, triCd, bin)) {
            return false;
        }

        int iYear = Integer.parseInt(yyyy);
        int iMonth = Integer.parseInt(mm);
        int iLastDay = CCDateUtil.getLastDay(iYear, iMonth);
        int iDow = CCDateUtil.getSysDowInt(yyyy + mm + "01");

        boolean bLtTeiOk = false;
        if (hajp0030ResultForm.getWorkLtTei() > 0) {
            bLtTeiOk = true;
        }

        // 特売納品日チェック
        boolean bLtTokOk = false;
        if (hajp0030ResultForm.getWorkLtTok() > 0) {
            bLtTokOk = true;
        }

        List<Hajp0030GridData> gridData = hajp0030ResultForm.getGridData();
        int iInpYear = 0;
        int iInpMonth = 0;
        int iInpDay = 0;

        int i = 1;
        int iDay = 1;
        int recordNum = 0;
        for (Hajp0030GridData record : gridData) {
            // 納品日をセット
            for (int j = 0; j < DAY_OF_WEEK; j++) {
                if ((iDay <= iLastDay) && (i >= iDow)) {
                    // 定番発注を取得
                    String teiHatDd = (String) DataUtil.getValueByMethodName(record, "getTeiHatDd" + (j + 1));

                    if (!CCStringUtil.isEmpty(teiHatDd)) {
                        if (!bLtTeiOk) {
                            // 発注パターン情報（定番）が取引先マスターに登録されていません。
                            errRes.addErrorInfo("teiHatDd" + (j + 1) + "_" + recordNum,
                                    CCMessageConst.MSG_KEY_TRICD_NOT_REGISTER);
                            blFlg = false;
                        } else {
                            iInpYear = Integer.parseInt(teiHatDd.substring(0, 4));
                            iInpMonth = Integer.parseInt(teiHatDd.substring(4, 6));
                            iInpDay = Integer.parseInt(teiHatDd.substring(6, 8));
                            if (CCDateUtil.getDateBetween(iInpYear, iInpMonth, iInpDay, iYear, iMonth, iDay) <= 0) {
                                // 定番納品日は発注日以後の日付を入力して下さい。
                                errRes.addErrorInfo("teiHatDd" + (j + 1) + "_" + recordNum,
                                        CCMessageConst.MSG_KEY_INPUT_DATE_AFTER_HACHU);
                                blFlg = false;
                            } else {
                                if (CCDateUtil.getDateBetween(iInpYear, iInpMonth, iInpDay, iYear, iMonth, iDay) > 7) {
                                    // 定番納品日は発注日から7日以内の日付を入力して下さい。
                                    errRes.addErrorInfo("teiHatDd" + (j + 1) + "_" + recordNum,
                                            CCMessageConst.MSG_KEY_INPUT_DATE_WITHIN_SEVEN_DAYS);
                                    blFlg = false;
                                }
                            }
                        }
                    }

                    // 特売発注を取得
                    String tokHatDd = (String) DataUtil.getValueByMethodName(record, "getTokHatDd" + (j + 1));
                    if (!CCStringUtil.isEmpty(tokHatDd)) {
                        if (!bLtTokOk) {
                            // 発注パターン情報（定番）が取引先マスターに登録されていません。
                            errRes.addErrorInfo("tokHatDd" + (j + 1) + "_" + recordNum,
                                    CCMessageConst.MSG_KEY_TRICD_NOT_REGISTER);
                            blFlg = false;
                        } else {
                            iInpYear = Integer.parseInt(tokHatDd.substring(0, 4));
                            iInpMonth = Integer.parseInt(tokHatDd.substring(4, 6));
                            iInpDay = Integer.parseInt(tokHatDd.substring(6, 8));
                            if (CCDateUtil.getDateBetween(iInpYear, iInpMonth, iInpDay, iYear, iMonth, iDay) <= 0) {
                                // 特売納品日は発注日以後の日付を入力して下さい。
                                errRes.addErrorInfo("tokHatDd" + (j + 1) + "_" + recordNum,
                                        CCMessageConst.MSG_KEY_INPUT_DATE_AFTER_HACHU);
                                blFlg = false;
                            } else {
                                if (CCDateUtil.getDateBetween(iInpYear, iInpMonth, iInpDay, iYear, iMonth, iDay) > 7) {
                                    // 特売納品日は発注日から7日以内の日付を入力して下さい。
                                    errRes.addErrorInfo("tokHatDd" + (j + 1) + "_" + recordNum,
                                            CCMessageConst.MSG_KEY_INPUT_DATE_WITHIN_SEVEN_DAYS);
                                    blFlg = false;
                                }
                            }
                        }
                    }
                    iDay++;
                }
                i++;
            }
            recordNum++;
        }
        if (!blFlg) {
            ErrorInfo error = errRes.getErrorInfo(CCMessageConst.MSG_KEY_VALIDATION_ERROR);
            errRes.getErrors().add(0, error);
        }

        return blFlg;
    }

    /**
     * システム運用日の先月～来々月を取得します。<BR>
     * @return String[] 先月を設定、来々月を設定
     */
    private String[] editHatLimit() {
        String sHatFrom;
        String sHatTo;
        String sSysUnyobi;
        String sYear;
        String sMonth;
        String sYearFrom;
        String sYearTo;
        String sMonthFrom;
        String sMonthTo;

        // システム運用日を取得
        sSysUnyobi = cCSICommon.getSysUnyobi();
        if (CCStringUtil.isEmpty(sSysUnyobi)) {
            return null;
        }

        // 年月を取得
        sYear = sSysUnyobi.substring(0, 4);
        sMonth = sSysUnyobi.substring(4, 6);

        // 先月を取得する
        if (sMonth.equals("01")) {
            sYearFrom = String.valueOf(Integer.parseInt(sYear) - 1);
            sMonthFrom = "12";
        } else {
            sYearFrom = sYear;
            sMonthFrom = CCStringUtil.suppZero(String.valueOf(Integer.parseInt(sMonth) - 1), 2);
        }

        // 先月を設定
        sHatFrom = sYearFrom + sMonthFrom;

        // 来々月を取得する
        if (sMonth.equals("11")) {
            sYearTo = String.valueOf(Integer.parseInt(sYear) + 1);
            sMonthTo = "01";
        } else if (sMonth.equals("12")) {
            sYearTo = String.valueOf(Integer.parseInt(sYear) + 1);
            sMonthTo = "02";
        } else {
            sYearTo = sYear;
            sMonthTo = CCStringUtil.suppZero(String.valueOf(Integer.parseInt(sMonth) + 2), 2);
        }

        // 来々月を設定
        sHatTo = sYearTo + sMonthTo;

        String[] hatFromToArr = {sHatFrom, sHatTo };
        return hatFromToArr;
    }

    /**
     * 登録データ取得処理＆取引先マスタの存在チェック<BR>
     * 
     * 機能概要：<br>
     * 発注月、取引先コード、便を元に<br> 
     * 有効なデータが存在する場合にtrueを返却<br>
     * <p>
     * @param hajp0030ResultForm 結果フォーム
     * @param hachu 発注年月
     * @param triCd 取引先コード
     * @param bin 便
     * @return true：データ存在、false：データ未存在
     */
    private boolean executeTriName(Hajp0030ResultForm hajp0030ResultForm, String hachu, String triCd, String bin) {
        // 登録データ取得処理＆取引先マスタの存在チェックを行う。
        M011trimExecuteTriName m011trimExecuteTriName = new M011trimExecuteTriName();
        m011trimExecuteTriName.setTriCd(triCd);
        m011trimExecuteTriName.setHakkoDay(hachu);

        List<M011trimExecuteTriNameResult> m011trimList =
                this.mybatisDao.selectMany("selectM011trim", m011trimExecuteTriName,
                        M011trimExecuteTriNameResult.class);
        // 抽出件数取得 Zero件時はエラー
        if (m011trimList == null || m011trimList.isEmpty()) {
            return false;
        }

        // 対象レコードのリードタイム集計(定番)
        long workLtTei = 0;

        // 対象レコードのリードタイム集計(特売)
        long workLtTok = 0;
        for (int iCnt = 0; iCnt < m011trimList.size(); iCnt++) {
            M011trimExecuteTriNameResult item = m011trimList.get(iCnt);
            if (iCnt == 0) {
                // 取引先名
                hajp0030ResultForm.setTriNm(item.getTriNm());

                // 発効日
                hajp0030ResultForm.setHakkoDay(item.getHakkoDay());
            }
            if (FIRST_BIN_VALUE.equals(bin)) {
                workLtTei +=
                        item.getHatpMon1() + item.getHatpTue1() + item.getHatpWed1() + item.getHatpThu1()
                                + item.getHatpFri1() + item.getHatpSat1() + item.getHatpSun1();

                workLtTok +=
                        item.getThatpMon1() + item.getThatpTue1() + item.getThatpWed1() + item.getThatpThu1()
                                + item.getThatpFri1() + item.getThatpSat1() + item.getThatpSun1();
            } else if (SECOND_BIN_VALUE.equals(bin)) {
                workLtTei +=
                        item.getHatpMon2() + item.getHatpTue2() + item.getHatpWed2() + item.getHatpThu2()
                                + item.getHatpFri2() + item.getHatpSat2() + item.getHatpSun2();
                workLtTok +=
                        item.getThatpMon2() + item.getThatpTue2() + item.getThatpWed2() + item.getThatpThu2()
                                + item.getThatpFri2() + item.getThatpSat2() + item.getThatpSun2();
            } else if (THIRD_BIN_VALUE.equals(bin)) {
                workLtTei +=
                        item.getHatpMon3() + item.getHatpTue3() + item.getHatpWed3() + item.getHatpThu3()
                                + item.getHatpFri3() + item.getHatpSat3() + item.getHatpSun3();
                workLtTok +=
                        item.getThatpMon3() + item.getThatpTue3() + item.getThatpWed3() + item.getThatpThu3()
                                + item.getThatpFri3() + item.getThatpSat3() + item.getThatpSun3();
            }
        }

        // 集計値格納
        hajp0030ResultForm.setWorkLtTei(workLtTei);
        hajp0030ResultForm.setWorkLtTok(workLtTok);

        // 表示用リードタイム取得、設定処理
        getDisplayLT(hajp0030ResultForm, triCd, bin);

        return true;
    }

    /**
    * 表示用リードタイム取得処理<BR>
    * 
    * 機能概要：<br>
    * 発注月、取引先コード、便を元に<br> 
    * 表示用リードタイムを設定します。<br>
    * <p>
    * @param hajp0030ResultForm 結果フォーム
    * @param triCd 取引先コード
    * @param bin 便
    * @return true：正常終了、false：異常終了
    */
    private boolean getDisplayLT(Hajp0030ResultForm hajp0030ResultForm, String triCd, String bin) {
        // システム運用日を取得
        String hakkoDay = cCSICommon.getSysUnyobi();
        if (CCStringUtil.isEmpty(hakkoDay)) {
            return false;
        }

        // 登録データ取得処理＆取引先マスタの存在チェックを行う。
        M011trimExecuteTriName m011trimExecuteTriName = new M011trimExecuteTriName();
        m011trimExecuteTriName.setTriCd(triCd);
        m011trimExecuteTriName.setHakkoDay(hakkoDay);

        List<M011trimExecuteTriNameResult> m011trimList =
                this.mybatisDao.selectMany("selectM011trim02", m011trimExecuteTriName,
                        M011trimExecuteTriNameResult.class);

        // 抽出件数取得 Zero件時は設定を行わず正常終了とする
        if (m011trimList == null || m011trimList.isEmpty()) {
            return true;
        }

        // 一件目を取得
        M011trimExecuteTriNameResult m011trim = m011trimList.get(0);

        // 表示用リードタイムを設定する
        // リードタイム(定番)
        if (FIRST_BIN_VALUE.equals(bin)) {
            // 定番発注のリードタイム
            hajp0030ResultForm.setHatpMon1(m011trim.getHatpMon1());
            hajp0030ResultForm.setHatpTue1(m011trim.getHatpTue1());
            hajp0030ResultForm.setHatpWed1(m011trim.getHatpWed1());
            hajp0030ResultForm.setHatpThu1(m011trim.getHatpThu1());
            hajp0030ResultForm.setHatpFri1(m011trim.getHatpFri1());
            hajp0030ResultForm.setHatpSat1(m011trim.getHatpSat1());
            hajp0030ResultForm.setHatpSun1(m011trim.getHatpSun1());

            // 特売発注のリードタイム
            hajp0030ResultForm.setThatpMon1(m011trim.getThatpMon1());
            hajp0030ResultForm.setThatpTue1(m011trim.getThatpTue1());
            hajp0030ResultForm.setThatpWed1(m011trim.getThatpWed1());
            hajp0030ResultForm.setThatpThu1(m011trim.getThatpThu1());
            hajp0030ResultForm.setThatpFri1(m011trim.getThatpFri1());
            hajp0030ResultForm.setThatpSat1(m011trim.getThatpSat1());
            hajp0030ResultForm.setThatpSun1(m011trim.getThatpSun1());
        } else if (SECOND_BIN_VALUE.equals(bin)) {
            // 定番発注のリードタイム
            hajp0030ResultForm.setHatpMon1(m011trim.getHatpMon2());
            hajp0030ResultForm.setHatpTue1(m011trim.getHatpTue2());
            hajp0030ResultForm.setHatpWed1(m011trim.getHatpWed2());
            hajp0030ResultForm.setHatpThu1(m011trim.getHatpThu2());
            hajp0030ResultForm.setHatpFri1(m011trim.getHatpFri2());
            hajp0030ResultForm.setHatpSat1(m011trim.getHatpSat2());
            hajp0030ResultForm.setHatpSun1(m011trim.getHatpSun2());

            // 特売発注のリードタイム
            hajp0030ResultForm.setThatpMon1(m011trim.getThatpMon2());
            hajp0030ResultForm.setThatpTue1(m011trim.getThatpTue2());
            hajp0030ResultForm.setThatpWed1(m011trim.getThatpWed2());
            hajp0030ResultForm.setThatpThu1(m011trim.getThatpThu2());
            hajp0030ResultForm.setThatpFri1(m011trim.getThatpFri2());
            hajp0030ResultForm.setThatpSat1(m011trim.getThatpSat2());
            hajp0030ResultForm.setThatpSun1(m011trim.getThatpSun2());
        } else if (THIRD_BIN_VALUE.equals(bin)) {
            // 定番発注のリードタイム
            hajp0030ResultForm.setHatpMon1(m011trim.getHatpMon3());
            hajp0030ResultForm.setHatpTue1(m011trim.getHatpTue3());
            hajp0030ResultForm.setHatpWed1(m011trim.getHatpWed3());
            hajp0030ResultForm.setHatpThu1(m011trim.getHatpThu3());
            hajp0030ResultForm.setHatpFri1(m011trim.getHatpFri3());
            hajp0030ResultForm.setHatpSat1(m011trim.getHatpSat3());
            hajp0030ResultForm.setHatpSun1(m011trim.getHatpSun3());

            // 特売発注のリードタイム
            hajp0030ResultForm.setThatpMon1(m011trim.getThatpMon3());
            hajp0030ResultForm.setThatpTue1(m011trim.getThatpTue3());
            hajp0030ResultForm.setThatpWed1(m011trim.getThatpWed3());
            hajp0030ResultForm.setThatpThu1(m011trim.getThatpThu3());
            hajp0030ResultForm.setThatpFri1(m011trim.getThatpFri3());
            hajp0030ResultForm.setThatpSat1(m011trim.getThatpSat3());
            hajp0030ResultForm.setThatpSun1(m011trim.getThatpSun3());
        }
        return true;
    }

    /**
    * 引数とリクエスト(ボディ)からレコードを編集しDBに登録(論理)する。*
    * @param yyyy 発注年チェック
    * @param mm 発注月チェック
    * @param triCd 取引先コードチェック
    * @param bin 便Noチェック
    * @return クライアントへ返却する結果
    */
    public Result save(String yyyy, String mm, String triCd, String bin) {
        Form<Hajp0030ResultForm> emptyForm = new Form<Hajp0030ResultForm>(Hajp0030ResultForm.class);
        Form<Hajp0030ResultForm> reqForm = emptyForm.bindFromRequest();
        if (reqForm.hasErrors()) {
            errRes.addErrorInfo(reqForm.errors());
            return badRequest(Json.toJson(errRes));
        } else {
            // ヘッダデータを取得する
            Hajp0030ResultForm hajp0030ResultForm = reqForm.get();

            // データのエラーチェック処理
            if (!doCheckData(yyyy, mm, triCd, bin, hajp0030ResultForm)) {
                return badRequest(Json.toJson(errRes));
            }

            // 結果フォームを設定
            hajp0030ResultForm.setTriCd(triCd);
            hajp0030ResultForm.setYyyy(yyyy);
            hajp0030ResultForm.setMm(mm);
            hajp0030ResultForm.setBin(bin);

            // データを更新
            return doInsertDB(hajp0030ResultForm);
        }
    }

    /**
    * 引数とリクエスト(ボディ)からレコードを編集しDBの該当データを更新(論理)する。*
    * @param yyyy 発注年チェック
    * @param mm 発注月チェック
    * @param triCd 取引先コードチェック
    * @param bin 便Noチェック
    * @return クライアントへ返却する結果
    */
    public Result update(String yyyy, String mm, String triCd, String bin) {
        Form<Hajp0030ResultForm> emptyForm = new Form<Hajp0030ResultForm>(Hajp0030ResultForm.class);
        Form<Hajp0030ResultForm> reqForm = emptyForm.bindFromRequest();
        if (reqForm.hasErrors()) {
            errRes.addErrorInfo(CCMessageConst.MSG_KEY_VALIDATION_ERROR);
            errRes.addErrorInfo(reqForm.errors());
            return badRequest(Json.toJson(errRes));
        } else {
            // ヘッダデータを取得する
            Hajp0030ResultForm hajp0030ResultForm = reqForm.get();

            // データのエラーチェック処理
            if (!doCheckData(yyyy, mm, triCd, bin, hajp0030ResultForm)) {
                return badRequest(Json.toJson(errRes));
            }

            // 結果フォームを設定
            hajp0030ResultForm.setTriCd(triCd);
            hajp0030ResultForm.setYyyy(yyyy);
            hajp0030ResultForm.setMm(mm);
            hajp0030ResultForm.setBin(bin);

            // データを更新
            return doUpdateDB(hajp0030ResultForm);
        }
    }

    /**
    * 引数とリクエスト(パラメータ)を条件にDBの該当データを削除(論理)する*
    * @param yyyy 発注年チェック
    * @param mm 発注月チェック
    * @param triCd 取引先コードチェック
    * @param bin 便Noチェック
    * @return クライアントへ返却する結果
    */
    public Result delete(String yyyy, String mm, String triCd, String bin) {
        // 削除要求
        H040btrsExample h040btrsExample = new H040btrsExample();
        h040btrsExample.createCriteria().andTriCdEqualTo(triCd).andYyyymmEqualTo(yyyy + mm).andBinEqualTo(bin);
        if (h040btrsMapper.deleteByExample(h040btrsExample) <= 0) {
            throw new ChaseException(Messages.get(CCMessageConst.MSG_KEY_EXCEPTION_DELETE_NO_RECORD));
        }
        return ok();
    }

    /**
     * 更新処理.
     * <p> 
     * @param hajp0030ResultForm 結果フォーム
     * @return クライアントへ返却する結果
     */
    private Result doUpdateDB(Hajp0030ResultForm hajp0030ResultForm) {
        // 編集仕様(項目セット仕様)
        H040btrs record = new H040btrs();

        // 値コピー処理
        copyProperties(hajp0030ResultForm, record);

        String sInsDD = CCDateUtil.getSysDateFormat(CCDateUtil.DATETIME_FORMAT_DATE_YYYYMMDD);
        String sInsTime = CCDateUtil.getSysDateFormat(CCDateUtil.DATETIME_FORMAT_TIME);
        record.setCmnTantoCd(context.getTantoCode()); // 担当者ｺｰﾄﾞ
        record.setCmnTermId(context.getTermId()); // 端末ID
        record.setCmnUpddd(sInsDD); // 更新日付
        record.setCmnUpdtime(sInsTime); // 更新時刻

        // 更新要求
        H040btrsExample h040btrsExample = new H040btrsExample();
        h040btrsExample.createCriteria().andTriCdEqualTo(hajp0030ResultForm.getTriCd())
                .andYyyymmEqualTo(hajp0030ResultForm.getYyyy() + hajp0030ResultForm.getMm())
                .andBinEqualTo(hajp0030ResultForm.getBin());

        if (h040btrsMapper.updateByExampleSelective(record, h040btrsExample) <= 0) {
            throw new ChaseException(Messages.get(CCMessageConst.MSG_KEY_EXCEPTION_UPDATE_NO_RECORD));
        }
        return ok();
    }

    /**
     * 登録処理.
     * @param hajp0030ResultForm 結果フォーム
     * @return クライアントへ返却する結果
     */
    private Result doInsertDB(Hajp0030ResultForm hajp0030ResultForm) {
        // 編集仕様(項目セット仕様)
        H040btrs record = new H040btrs();
        record.setTriCd(hajp0030ResultForm.getTriCd());
        record.setYyyymm(hajp0030ResultForm.getYyyy() + hajp0030ResultForm.getMm());
        record.setBin(hajp0030ResultForm.getBin());

        // 値コピー処理
        copyProperties(hajp0030ResultForm, record);

        String sInsDD = CCDateUtil.getSysDateFormat(CCDateUtil.DATETIME_FORMAT_DATE_YYYYMMDD);
        String sInsTime = CCDateUtil.getSysDateFormat(CCDateUtil.DATETIME_FORMAT_TIME);
        record.setCmnTantoCd(context.getTantoCode()); // 担当者ｺｰﾄﾞ
        record.setCmnTermId(context.getTermId()); // 端末ID
        record.setCmnInsdd(sInsDD); // 登録日付
        record.setCmnUpddd(sInsDD); // 更新日付
        record.setCmnUpdtime(sInsTime); // 更新時刻

        // 　更新要求
        H040btrsExample h040btrsExample = new H040btrsExample();
        h040btrsExample.createCriteria().andTriCdEqualTo(hajp0030ResultForm.getTriCd())
                .andYyyymmEqualTo(hajp0030ResultForm.getYyyy() + hajp0030ResultForm.getMm())
                .andBinEqualTo(hajp0030ResultForm.getBin());

        if (h040btrsMapper.insertSelective(record) <= 0) {
            throw new ChaseException(Messages.get(CCMessageConst.MSG_KEY_EXCEPTION_INSERT_NO_RECORD));
        }
        return created();
    }

    /**
    * 値コピー処理
    * <p> 
    * @param hajp0030ResultForm Hajp0030ResultFormクラス
    * @param record H040btrsクラス
    */
    void copyProperties(Hajp0030ResultForm hajp0030ResultForm, H040btrs record) {
        int iDow = CCDateUtil.getSysDowInt(hajp0030ResultForm.getYyyy() + hajp0030ResultForm.getMm() + "01");

        List<Hajp0030GridData> gridData = hajp0030ResultForm.getGridData();
        String funcNum = "";

        int i = 1;
        int iDay = 1;
        for (Hajp0030GridData item : gridData) {
            // 納品日をセット
            for (int j = 1; j <= DAY_OF_WEEK; j++) {
                if ((iDay <= MAX_DAY_OF_MONTH) && (i >= iDow)) {
                    if (iDay < 10) {
                        funcNum = "0" + iDay;
                    } else {
                        funcNum = String.valueOf(iDay);
                    }

                    // 定番発注を取得
                    String teiHatDd = (String) DataUtil.getValueByMethodName(item, "getTeiHatDd" + j);
                    if (CCStringUtil.isEmpty(teiHatDd)) {
                        teiHatDd = StringUtils.EMPTY;
                    }
                    DataUtil.setValueByMethodName(record, "setTeiHatDd" + funcNum, teiHatDd);

                    // 特売発注を取得
                    String tokHatDd = (String) DataUtil.getValueByMethodName(item, "getTokHatDd" + j);
                    if (CCStringUtil.isEmpty(tokHatDd)) {
                        tokHatDd = StringUtils.EMPTY;
                    }
                    DataUtil.setValueByMethodName(record, "setTokHatDd" + funcNum, tokHatDd);
                    iDay++;
                }
                i++;
            }
        }
    }
}
