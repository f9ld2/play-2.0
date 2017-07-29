// /////////////////////////////////////////////////////////////////////
// Copyright(C) 2015 NEC Solution Innovators, Ltd. All Rights Reserved.
// /////////////////////////////////////////////////////////////////////

/*
 * ====================================================================
 * 
 * 機能名称 :CCANCommon
 * 
 * 改版履歴
 * 
 * Rev. 改版年月日 改版者名 内容
 * 
 * 1.0 20140510 Tinnc 新規作成
 * 
 * ===================================================================
 */
package jp.co.necsoft.web_md.core.common.biz.an;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import jp.co.necsoft.web_md.core.common.CCSystemContext;
import jp.co.necsoft.web_md.core.common.mybatis.dao.M019ymdmMapper;
import jp.co.necsoft.web_md.core.common.mybatis.dao.M020ctlmMapper;
import jp.co.necsoft.web_md.core.common.mybatis.dto.M019ymdm;
import jp.co.necsoft.web_md.core.common.mybatis.dto.M019ymdmExample;
import jp.co.necsoft.web_md.core.common.mybatis.dto.M020ctlm;
import jp.co.necsoft.web_md.core.common.mybatis.dto.M020ctlmExample;
import jp.co.necsoft.web_md.core.common.utils.CCDateUtil;
import jp.co.necsoft.web_md.core.common.utils.CCNumberUtil;
import jp.co.necsoft.web_md.core.common.utils.CCStringUtil;

/**
 * 
 * cmJANCommon クラス
 *
 */
public class CCANCommon {

    /** ZI002 */
    private static final String ZI002 = "ZI002";

    /** M020ctlmのマッパー  */
    @Inject
    private M020ctlmMapper m020ctlmMapper;

    /** M019ymdmのマッパー  */
    @Inject
    private M019ymdmMapper m019ymdmMapper;
    
    /** System Context */
    @Inject
    private CCSystemContext context;

    /**
     * プロパティ値取得（配列分割）.
     * <p>
     * 機能概要：<br>
     * プロパティファイルから取得した値を返します。（配列分割）<br>
     * 
     * @param propertyValue プロパティ取得値
     * @return String[] or null if value empty
     */
    public String[] getPropertiesArray(String propertyValue) {
        ArrayList<String> vecValue = new ArrayList<String>();
        String sValue = "";
        int iIdx = 0;
        int iSep = 0;

        if (CCStringUtil.isEmpty(propertyValue)) {
            return null;
        }

        String sKeyValue = propertyValue;

        // 値の分割処理
        while (iSep != -1) {

            iSep = sKeyValue.indexOf(',', iIdx);

            if (iSep != -1) {
                sValue = sKeyValue.substring(iIdx, iSep);
                iIdx = iSep + 1;
            } else {
                sValue = sKeyValue.substring(iIdx);
            }

            vecValue.add(sValue);
        }

        String[] sValueArray = new String[vecValue.size()];
        sValueArray = vecValue.toArray(sValueArray);

        return sValueArray;
    }

    /**
     * キー項目（数値項目）チェック.
     * <p>
     * 機能概要：<br>
     * 指定された内容についての妥当性チェックを行う。<br>
     *
     * @param sVal 値
     * @param sChkMask チェック内容<br>
     *    1文字目：桁数チェック（0.チェックなし 1.チェックあり）<br>
     *    2文字目：整数値チェック（0.チェックなし 1.チェックあり）<br>
     *    3文字目：正数値チェック（0.チェックなし 1.チェックあり）<br>
     * @param iValLen 桁数（桁数チェックありの時のみ有効）
     * @return int チェック結果<br>
     *                0 : チェックＯＫ<br>
     *               -1 : 設定値エラー<br>
     *               -2 : 処理異常<br>
     *                1 : 数値チェックエラー<br>
     *                2 : 桁数チェックエラー<br>
     *                3 : 正数値チェックエラー<br>
     *                4 : 整数値チェックエラー<br>
     */
    public int validateNumValue(String sVal, String sChkMask, int iValLen) {
        // チェック内容設定チェック
        if (sChkMask.length() != 3) {
            return -1;
        }
        // 桁数指定チェック
        if (sChkMask.substring(0, 1).equals("1")) {
            if (iValLen <= 0) {
                return -1;
            }
        }

        // スペース入力チェック
        if (sVal.indexOf(" ") != -1) {
            return 1;
        }
        // 数値チェック
        if (!CCNumberUtil.isNumeric(sVal)) {
            return 1;
        }

        // 指定項目チェック
        // '1**' : 桁数チェック
        if (sChkMask.substring(0, 1).equals("1")) {
            if (sVal.length() != iValLen) {
                return 2;
            }
        }

        // '*1*' : 整数値チェック
        if (sChkMask.substring(1, 2).equals("1")) {
            if (sVal.indexOf(".") != -1) {
                return 4;
            }
        }

        // '**1' : 正数値チェック
        if (sChkMask.substring(2, 3).equals("1")) {
            if (Integer.parseInt(sVal) < 0) {
                return 3;
            }
        }

        return 0;
    }

    /**
     * 初期表示年月取得処理.
     * <p>
     * 機能概要：<br>
     *　分析帳票で初期表示を行う年月を取得します。<br>
     *
     * @param sDate 年月日(運用日)
     * @return String 年月
     */
    public String getYearMonth(String sDate) {
        // プロパティファイルより設定日を取得
        // 設定日取得
        String sDay = context.getContextProperty(CCANConst.PARA_DAY);
        if (sDay.equals("")) {
            return "";
        }
        int iDay = 0 - Integer.parseInt(sDay);
        // 年月計算
        String sYYYYMM = CCDateUtil.doCalcDate(sDate, iDay);
        if (sYYYYMM == null) {
            return "";
        }

        return sYYYYMM;
    }

    /**
     * 指定年月の月末日を取得する.
     * <p>
     * 機能概要：<br>
     * 指定年月の月末日を取得します。
     *
     * @param year 抽出条件・出力年
     * @param month 抽出条件・出力月
     * @return String 指定年月の月末日
     */
    public String getLastDayOfMonth(String year, String month) {
        int iGetumtuDate = CCDateUtil.getLastDay(Integer.parseInt(year), Integer.parseInt(month));
        if (iGetumtuDate == -1) {
            return "";
        } else {
            return year + month + String.valueOf(iGetumtuDate);
        }
    }

    /**
     * コントロールマスタより在庫実績保存期間(月別部門別在庫実績)を取得する.
     * <p>
     * 機能概要：<br>
     * コントロールマスタより在庫実績保存期間(月別部門別在庫実績)を取得します。
     * <p> 
     * @return String 在庫実績保存期間(月別部門別在庫実績)
     */
    public String getHozonKikan() {
        M020ctlmExample example = new M020ctlmExample();
        example.createCriteria().andCdEqualTo(ZI002);

        List<M020ctlm> list = m020ctlmMapper.selectByExample(example);
        if (list.isEmpty()) {
            return "";
        } else {
            String data = list.get(0).getData();
            if (CCStringUtil.isEmpty(data) || data.length() < 30) {
                return "";
            } else {
                return data.substring(27, 30);
            }
        }
    }

    /**
     * 運用日取得処理.
     * <p>
     * 概要：<br>
     * 運用日を取得します。<br>
     * 
     * @return String 運用日
     */
    public String getUnyoDateData() {
        M020ctlmExample example = new M020ctlmExample();
        example.createCriteria().andCdEqualTo("DATE");

        List<M020ctlm> list = m020ctlmMapper.selectByExample(example);
        if (list.size() == 1) {
            return list.get(0).getData();
        } else {
            return "";
        }

    }

    /**
     * 期首月を取得しセット
     * 
     * @return 期首月を取得しセット
     */
    public String getKisyuMm() {

        String sRet = "";
            // コントロールマスタ検索オブジェクト作成
            M020ctlmExample example = new M020ctlmExample();

            example.createCriteria().andCdEqualTo("DATE");

            List<M020ctlm> list = m020ctlmMapper.selectByExample(example);
            if (list.size() > 0) {
                String kisyuMn = list.get(0).getData();
                if (kisyuMn.length() >= 61) {
                    sRet = kisyuMn.substring(59, 61);
                }
            }
        return sRet;

    }

    /**
     * 　年月日マスタより年度を取得する.
     * @param sUnyoDate 運用日
     * @return String 年度
     */
    public String getNendo(String sUnyoDate) {
        if (!sUnyoDate.trim().equals("")) {
            M019ymdmExample m019ymdmExample = new M019ymdmExample();
            m019ymdmExample.createCriteria().andCalDateEqualTo(sUnyoDate);

            List<M019ymdm> listM019ymdm = m019ymdmMapper.selectByExample(m019ymdmExample);
            if (listM019ymdm.size() == 0) {
                return null;
            } else {
                return CCStringUtil.trimRight(listM019ymdm.get(0).getNendo());
            }
        }
        return "";
    }
}
