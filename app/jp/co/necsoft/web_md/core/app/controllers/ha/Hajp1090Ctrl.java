// /////////////////////////////////////////////////////////////////////
// Copyright(C) 2015 NEC Solution Innovators, Ltd. All Rights Reserved.
// /////////////////////////////////////////////////////////////////////

/*
 * ==================================================================== 機能名称 : 物流スケジュールリスト(定番) 改版履歴 Rev. 改版年月日 改版者名 内容
 * 1.0 2014.04.17 VuQQ 新規作成 ===================================================================
 */
package jp.co.necsoft.web_md.core.app.controllers.ha;

import java.io.File;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import jp.co.necsoft.web_md.common.ChaseException;
import jp.co.necsoft.web_md.common.mybatis.dao.MyBatisSqlMapDao;
import jp.co.necsoft.web_md.core.app.file.report.Hapr1090Report;
import jp.co.necsoft.web_md.core.app.forms.ha.Hajp1090ResultForm;
import jp.co.necsoft.web_md.core.common.CCMessageConst;
import jp.co.necsoft.web_md.core.common.CCSecurityAuthenticator;
import jp.co.necsoft.web_md.core.common.CCSystemContext;
import jp.co.necsoft.web_md.core.common.forms.ErrorInfo;
import jp.co.necsoft.web_md.core.common.forms.ErrorResponse;
import jp.co.necsoft.web_md.core.common.mybatis.dao.M011trimMapper;
import jp.co.necsoft.web_md.core.common.mybatis.dto.H040btrs;
import jp.co.necsoft.web_md.core.common.mybatis.dto.M011trim;
import jp.co.necsoft.web_md.core.common.mybatis.dto.M011trimExample;
import jp.co.necsoft.web_md.core.common.utils.CCDateUtil;
import jp.co.necsoft.web_md.core.common.utils.CCReportUtil;
import jp.co.necsoft.web_md.core.common.utils.CCStringUtil;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTimeConstants;

import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;

import com.orangesignal.csv.manager.CsvManager;
import com.orangesignal.csv.manager.CsvManagerFactory;

/**
 * 物流スケジュールリスト(定番)のControllerクラス
 *
 */
@Security.Authenticated(CCSecurityAuthenticator.class)
public class Hajp1090Ctrl extends Controller {
    /** プログラムＩＤ */
    private static final String PROGRAM_ID = "HAPR1090";
    /** バージョン */
    private static final String PROGRAM_VERSION = "V1.00";
    /** M001jgymMapper Object */
    @Inject
    private M011trimMapper m011trimMapper;
    /** MyBatisSqlMapDao Object */
    @Inject
    private MyBatisSqlMapDao dao;
    /** ErrorResponse Object */
    @Inject
    private ErrorResponse errRes;
    /** CCSystemContext Object */
    @Inject
    private CCSystemContext context;

    /**
     * 引数とリクエスト(パラメータ)を条件にDBからデータ(複数件)を検索し返却する。*
     * @param yyyy 年
     * @param mm 月
     * @return クライアントへ返却する結果
     * @throws Exception
     */
    public Result query(String yyyy, String mm) {
        List<Hajp1090ResultForm> resultList = new ArrayList<Hajp1090ResultForm>();
        Hajp1090ResultForm resultForm = new Hajp1090ResultForm();
        CCReportUtil cru = new CCReportUtil("HASV1090", context);
        CsvManager csvManager = CsvManagerFactory.newCsvManager();
        List<Hapr1090Report> csvBean = new ArrayList<Hapr1090Report>();
        List<M011trim> listM011trim;
        String tantoCode;

        String[] nhnDdArray = new String[31];
        String[] dayArray = new String[31];
        String[] weekArray = new String[31];
        boolean[] flgArray = new boolean[36];

        if (!doCheckData(yyyy, mm)) {
            return badRequest(Json.toJson(errRes));
        }

        // 入力された条件に該当するを取得する
        Map<String, String> hashMap = new HashMap<String, String>();
        hashMap.put("yyyyMm", yyyy + mm);
        List<H040btrs> listH040btrs = dao.selectMany("selectHajp1090H040btrs", hashMap, H040btrs.class);

        int iCnt = listH040btrs.size();
        if (iCnt == 0) {
            errRes.addErrorInfo(CCMessageConst.MSG_KEY_NO_FOUND_DATA);
            return badRequest(Json.toJson(errRes));
        }
        setArrayData(yyyy, mm, dayArray, weekArray, flgArray);
        tantoCode = context.getTantoCode();
        String dateEnd = CCDateUtil.getDateEndOfMonth(yyyy, mm);
        try {
            for (int iCount = 0; iCount < iCnt; iCount++) {
                Hapr1090Report objHapr1090Report = new Hapr1090Report();
                Class<?> clsHapr1090Report = objHapr1090Report.getClass();
                setNhnDdArray(listH040btrs.get(iCount), nhnDdArray);

                objHapr1090Report.h00_progid = PROGRAM_ID;
                objHapr1090Report.h00_version = PROGRAM_VERSION;
                objHapr1090Report.h01_tanto_code = tantoCode;
                objHapr1090Report.h01_yyyy = yyyy;
                objHapr1090Report.h01_mm = mm;

                // 曜日を編集
                int iWriteCnt = 1; // 出力データカウンタクリア
                int k = 0; // 曜日編集フラグカウンタクリア
                for (int j = 0; j < 31; j++) {
                    Field h02HatW =
                            clsHapr1090Report.getDeclaredField("h02_hatwk_"
                                    + CCStringUtil.suppZero(String.valueOf(iWriteCnt), 2));
                    if (flgArray[k]) {
                        h02HatW.set(objHapr1090Report, weekArray[j]);
                        iWriteCnt++;
                    } else {
                        h02HatW.set(objHapr1090Report, StringUtils.EMPTY);
                        Field h02HatWNext =
                                clsHapr1090Report.getDeclaredField("h02_hatwk_"
                                        + CCStringUtil.suppZero(String.valueOf(iWriteCnt + 1), 2));
                        h02HatWNext.set(objHapr1090Report, weekArray[j]);

                        iWriteCnt = iWriteCnt + 2;
                        k++;
                    }
                    k++;
                }
                if (iWriteCnt <= 36) {
                    Field h02HatW =
                            clsHapr1090Report.getDeclaredField("h02_hatwk_"
                                    + CCStringUtil.suppZero(String.valueOf(iWriteCnt), 2));
                    for (int j = iWriteCnt; j < 36; j++) {
                        h02HatW.set(objHapr1090Report, StringUtils.EMPTY);
                    }
                }

                objHapr1090Report.m01_tri_cd = listH040btrs.get(iCount).getTriCd();
                M011trimExample m011trimExample = new M011trimExample();
                m011trimExample.createCriteria().andTriCdEqualTo(objHapr1090Report.m01_tri_cd);
                m011trimExample.setSearchDate(dateEnd);
                listM011trim = this.m011trimMapper.selectByExample(m011trimExample);
                if (listM011trim != null && listM011trim.size() > 0) {
                    objHapr1090Report.m01_tri_nm = listM011trim.get(0).getTriNm();
                    objHapr1090Report.m01_tri_tel = listM011trim.get(0).getTelNo1();
                } else {
                    objHapr1090Report.m01_tri_nm = "＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊";
                    objHapr1090Report.m01_tri_tel = "********************";
                }
                objHapr1090Report.m01_bin = listH040btrs.get(iCount).getBin();

                // 納品日を編集
                iWriteCnt = 1; // 出力データカウンタクリア
                k = 0; // 曜日編集フラグカウンタクリア
                for (int j = 0; j < 31; j++) {
                    Field h02NhnD =
                            clsHapr1090Report.getDeclaredField("m01_nhndd_"
                                    + CCStringUtil.suppZero(String.valueOf(iWriteCnt), 2));
                    if (flgArray[k]) {
                        h02NhnD.set(objHapr1090Report, dayArray[j]);
                        iWriteCnt++;
                    } else {
                        h02NhnD.set(objHapr1090Report, StringUtils.EMPTY);
                        Field h02NhnWNext =
                                clsHapr1090Report.getDeclaredField("m01_nhndd_"
                                        + CCStringUtil.suppZero(String.valueOf(iWriteCnt + 1), 2));
                        h02NhnWNext.set(objHapr1090Report, dayArray[j]);

                        iWriteCnt = iWriteCnt + 2;
                        k++;
                    }
                    k++;
                }
                if (iWriteCnt <= 36) {
                    Field h02NhnD =
                            clsHapr1090Report.getDeclaredField("m01_nhndd_"
                                    + CCStringUtil.suppZero(String.valueOf(iWriteCnt), 2));
                    for (int j = iWriteCnt; j < 36; j++) {
                        h02NhnD.set(objHapr1090Report, StringUtils.EMPTY);
                    }
                }

                // 発注日を編集
                iWriteCnt = 1; // 出力データカウンタクリア
                k = 0; // 曜日編集フラグカウンタクリア
                for (int j = 0; j < 31; j++) {
                    Field h02HatND =
                            clsHapr1090Report.getDeclaredField("m01_hatdd_"
                                    + CCStringUtil.suppZero(String.valueOf(iWriteCnt), 2));
                    if (flgArray[k]) {
                        h02HatND.set(objHapr1090Report, nhnDdArray[j]);
                        iWriteCnt++;
                    } else {
                        h02HatND.set(objHapr1090Report, StringUtils.EMPTY);
                        Field h02HatNDNext =
                                clsHapr1090Report.getDeclaredField("m01_hatdd_"
                                        + CCStringUtil.suppZero(String.valueOf(iWriteCnt + 1), 2));
                        h02HatNDNext.set(objHapr1090Report, nhnDdArray[j]);

                        iWriteCnt = iWriteCnt + 2;
                        k++;
                    }
                    k++;
                }
                if (iWriteCnt <= 36) {
                    Field h02HatNDNext =
                            clsHapr1090Report.getDeclaredField("m01_hatdd_"
                                    + CCStringUtil.suppZero(String.valueOf(iWriteCnt), 2));
                    for (int j = iWriteCnt; j < 36; j++) {
                        h02HatNDNext.set(objHapr1090Report, StringUtils.EMPTY);
                    }
                }

                csvBean.add(objHapr1090Report);
            }
        } catch (Exception e) {
            throw new ChaseException(e);
        }
        // ＣＳＶ作成（データ出力）
        try {
            csvManager.save(csvBean, Hapr1090Report.class).to(new File(cru.getCsvFilePath()),
                    CCReportUtil.CSV_OUTPUT_ENCODING);
        } catch (Exception e) {
            throw new ChaseException(e);
        }

        cru.makePDF();
        try {
            URL uPdfUrl = new URL(cru.getPdfUrl());
            resultForm.setPdfUrl(uPdfUrl);
        } catch (Exception e) {
            throw new ChaseException(e);
        }

        resultList.add(resultForm);

        // gc対応
        cru = null;
        System.gc();

        return ok(Json.toJson(resultList));
    }

    /**
     * 発注運用日の先月～来々月を取得します。
     * @param sHatUnyobi 発注運用日
     * @param hatYyyyMm 先年月
     */
    public void editHatLimit(String sHatUnyobi, String[] hatYyyyMm) {
        String sHatFrom;
        String sHatTo;
        String sYear;
        String sMonth;
        String sYearFrom;
        String sYearTo;
        String sMonthFrom;
        String sMonthTo;
        // 年月を取得
        sYear = sHatUnyobi.substring(0, 4);
        sMonth = sHatUnyobi.substring(4, 6);

        String january = String.valueOf(DateTimeConstants.JANUARY);
        String december = String.valueOf(DateTimeConstants.DECEMBER);
        String november = String.valueOf(DateTimeConstants.NOVEMBER);
        String february = String.valueOf(DateTimeConstants.FEBRUARY);
        // 先月を取得する
        if (january.equals(sMonth)) {
            sYearFrom = String.valueOf(Integer.parseInt(sYear) - 1);
            sMonthFrom = december;
        } else {
            sYearFrom = sYear;
            sMonthFrom = CCStringUtil.suppZero(String.valueOf(CCStringUtil.cnvStrToInt(sMonth) - 1), 2);
        }

        sHatFrom = sYearFrom + sMonthFrom;
        hatYyyyMm[0] = sHatFrom; // 先月を設定

        // 来々月を取得する
        if (november.equals(sMonth)) {
            sYearTo = String.valueOf(Integer.parseInt(sYear) + 1);
            sMonthTo = january;
        } else if (december.equals(sMonth)) {
            sYearTo = String.valueOf(Integer.parseInt(sYear) + 1);
            sMonthTo = february;
        } else {
            sYearTo = sYear;
            sMonthTo = CCStringUtil.suppZero(String.valueOf(CCStringUtil.cnvStrToInt(sMonth) + 2), 2);
        }
        sHatTo = sYearTo + sMonthTo;
        hatYyyyMm[1] = sHatTo; // 来々月を設定
    }

    /**
     * データのエラーチェック処理
     * 
     * @param yyyy 年
     * @param mm 月
     * @return boolean チェック結果
     */
    private boolean doCheckData(String yyyy, String mm) {
        String yyyyMm;
        boolean bChkData;

        // 発注運用日を取得
        String hatUnyobi = context.getHatUnyoDate();
        if (CCStringUtil.isEmpty(hatUnyobi)) {
            errRes.addErrorInfo(CCMessageConst.MSG_KEY_UNYOUBI_NOT_REGISTERED);
            return false;
        }

        yyyyMm = yyyy + mm;
        bChkData = true;
        // 年チェック
        if (CCStringUtil.getByteLen(yyyy) != yyyy.length()) {
            errRes.addErrorInfo("yyyy", CCMessageConst.MSG_KEY_ENTER_INPUT_FOUR_CHARACTERS);
            bChkData = false;
        }
        // 月チェック
        if (CCStringUtil.getByteLen(mm) != mm.length()) {
            errRes.addErrorInfo("mm", CCMessageConst.MSG_KEY_ENTER_INPUT_TWO_DIGITS);
            bChkData = false;
        }

        int iMonth = CCStringUtil.cnvStrToInt(mm);
        String[] hatYyyyMm = new String[2];
        if (iMonth < 1 || iMonth > 12) {
            errRes.addErrorInfo("mm", CCMessageConst.MSG_KEY_INPUT_DATE_INVALID);
            bChkData = false;
        } else {
            // 先年月の取得
            editHatLimit(hatUnyobi, hatYyyyMm);

            String sHatFrom = hatYyyyMm[0];
            String sHatTo = hatYyyyMm[1];
            if (yyyyMm.compareTo(sHatFrom) < 0 || yyyyMm.compareTo(sHatTo) > 0) {
                errRes.addErrorInfo("yyyy", CCMessageConst.MSG_KEY_INPUT_YEAR_MONTH_NOT_MATCH_TYPE);
                errRes.addErrorInfo("mm", CCMessageConst.MSG_KEY_INPUT_YEAR_MONTH_NOT_MATCH_TYPE);
                bChkData = false;
            }
        }

        if (!bChkData) {
            ErrorInfo error = errRes.getErrorInfo(CCMessageConst.MSG_KEY_VALIDATION_ERROR);
            errRes.getErrors().add(0, error);
            return false;
        }
        return true;
    }

    /**
     * Arrayのデータをセット
     * @param yyyy 年
     * @param mm 月
     * @param dayArray Day Array
     * @param weekArray Week Array
     * @param flgArray Flag Array
     */
    private void setArrayData(String yyyy, String mm, String[] dayArray, String[] weekArray, boolean[] flgArray) {

        // 月末日を取得する
        int iLastDay = CCDateUtil.getLastDay(CCStringUtil.cnvStrToInt(yyyy), CCStringUtil.cnvStrToInt(mm));

        // 該当月の日付を配列に格納する
        for (int i = 0; i < 31; i++) {
            if (i < iLastDay) {
                dayArray[i] = String.valueOf(i + 1);
                if (dayArray[i].length() == 1) {
                    dayArray[i] = "0" + dayArray[i];
                }
            } else {
                dayArray[i] = StringUtils.EMPTY;
            }
        }

        // 各日付の曜日を取得する
        for (int i = 0; i < 31; i++) {
            if (!StringUtils.EMPTY.equals(dayArray[i])) {
                weekArray[i] =
                        CCDateUtil.getSysDow(CCStringUtil.cnvStrToInt(yyyy), CCStringUtil.cnvStrToInt(mm),
                                CCStringUtil.cnvStrToInt(dayArray[i]));
            } else {
                weekArray[i] = StringUtils.EMPTY;
            }
        }

        // 週の間を分けるためのフラグを編集する
        int j = 0;
        for (int i = 0; i < 31; i++) {
            flgArray[j] = true;
            if ("日".equals(weekArray[i])) {
                if (i != (iLastDay - 1)) {
                    flgArray[j + 1] = false;
                    j++;
                }
            }
            j++;
        }
    }

    /**
     * 納品日編集処理を行います。
     * @param hatDd 納品日
     * @return 納品日がない場合は空文字、ある場合は日付を切り取って返す
     */
    public String editHatDd(String hatDd) {
        if ((StringUtils.EMPTY.equals(hatDd)) || (hatDd.length() < 8)) {
            return StringUtils.EMPTY;
        }

        return hatDd.substring(6, 8);
    }

    /**
     * Arrayのデータをセット
     * @param h040btrs H040btrs
     * @param nhnDdArray NhnDd Array
     */
    private void setNhnDdArray(H040btrs h040btrs, String[] nhnDdArray) {
        nhnDdArray[0] = editHatDd(h040btrs.getTeiHatDd01());
        nhnDdArray[1] = editHatDd(h040btrs.getTeiHatDd02());
        nhnDdArray[2] = editHatDd(h040btrs.getTeiHatDd03());
        nhnDdArray[3] = editHatDd(h040btrs.getTeiHatDd04());
        nhnDdArray[4] = editHatDd(h040btrs.getTeiHatDd05());
        nhnDdArray[5] = editHatDd(h040btrs.getTeiHatDd06());
        nhnDdArray[6] = editHatDd(h040btrs.getTeiHatDd07());
        nhnDdArray[7] = editHatDd(h040btrs.getTeiHatDd08());
        nhnDdArray[8] = editHatDd(h040btrs.getTeiHatDd09());
        nhnDdArray[9] = editHatDd(h040btrs.getTeiHatDd10());
        nhnDdArray[10] = editHatDd(h040btrs.getTeiHatDd11());
        nhnDdArray[11] = editHatDd(h040btrs.getTeiHatDd12());
        nhnDdArray[12] = editHatDd(h040btrs.getTeiHatDd13());
        nhnDdArray[13] = editHatDd(h040btrs.getTeiHatDd14());
        nhnDdArray[14] = editHatDd(h040btrs.getTeiHatDd15());
        nhnDdArray[15] = editHatDd(h040btrs.getTeiHatDd16());
        nhnDdArray[16] = editHatDd(h040btrs.getTeiHatDd17());
        nhnDdArray[17] = editHatDd(h040btrs.getTeiHatDd18());
        nhnDdArray[18] = editHatDd(h040btrs.getTeiHatDd19());
        nhnDdArray[19] = editHatDd(h040btrs.getTeiHatDd20());
        nhnDdArray[20] = editHatDd(h040btrs.getTeiHatDd21());
        nhnDdArray[21] = editHatDd(h040btrs.getTeiHatDd22());
        nhnDdArray[22] = editHatDd(h040btrs.getTeiHatDd23());
        nhnDdArray[23] = editHatDd(h040btrs.getTeiHatDd24());
        nhnDdArray[24] = editHatDd(h040btrs.getTeiHatDd25());
        nhnDdArray[25] = editHatDd(h040btrs.getTeiHatDd26());
        nhnDdArray[26] = editHatDd(h040btrs.getTeiHatDd27());
        nhnDdArray[27] = editHatDd(h040btrs.getTeiHatDd28());
        nhnDdArray[28] = editHatDd(h040btrs.getTeiHatDd29());
        nhnDdArray[29] = editHatDd(h040btrs.getTeiHatDd30());
        nhnDdArray[30] = editHatDd(h040btrs.getTeiHatDd31());
    }
}
