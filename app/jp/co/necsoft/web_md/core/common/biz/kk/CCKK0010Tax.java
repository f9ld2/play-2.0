///////////////////////////////////////////////////////////////////////
// Copyright(C) 2015 NEC Solution Innovators, Ltd. All Rights Reserved.
///////////////////////////////////////////////////////////////////////

/* ====================================================================
 * 機能名称 : 移動伝票入力
 * 改版履歴
 * Rev. 改版年月日   改版者名       内容
 * 1.0   2014.04.05   Hungtb      新規作成
 * =================================================================== */
package jp.co.necsoft.web_md.core.common.biz.kk;

import java.text.DecimalFormat;
import java.util.List;

import javax.inject.Inject;

import jp.co.necsoft.web_md.core.common.mybatis.dao.M020ctlmMapper;
import jp.co.necsoft.web_md.core.common.mybatis.dto.M020ctlm;
import jp.co.necsoft.web_md.core.common.mybatis.dto.M020ctlmExample;
import jp.co.necsoft.web_md.core.common.utils.CCNumberUtil;
import jp.co.necsoft.web_md.core.common.utils.CCStringUtil;

/**
 * 消費税算出JavaBeansクラス<BR>
 * コントロールマスタから消費税情報を取得し、消費税を算出します。
 */
public class CCKK0010Tax {

    /** Mybatis mapper */
    @Inject
    M020ctlmMapper m020ctlm;

    // IN
    /** 税区分*/
    private String taxKbn = "";
    /** 端数区分*/
    private String hasuKbn = "";
    /** 対象金額*/
    private String kingkIn = "";
    /** 内税計算フラグ*/
    private String uchiStaxFlg = "";
    // OT
    /** 税種別*/
    private String taxSyu = "";
    /** 税率*/
    private String ritu = "";
    /** 消費税額*/
    private String taxKingk = "";
    /** 対象金額*/
    private String kingkOt = "";
    /** 税込金額*/
    private String taxResult = "";

    /**
    * M020_COL = 2
    **/
    public static final int M020_COL = 2;

    /**
    * M020_KEY
    **/
    private static final class M020_KEY {
        /**
        * KEY_TAX = "TAX"
        **/
        private static final String KEY_TAX = "TAX";
        /**
        * KEY_ZEI_1 = "0"
        **/
        private static final String KEY_ZEI_1 = "0";

    }

    /**
    * 税区分
    **/
    public static final class ZEI_KBN {
        /** 税収_1 */
        public static final String ZEI_SYU_1 = "1";
        /** 税収_2 */
        public static final String ZEI_SYU_2 = "2";
        /** 税収_3 */
        public static final String ZEI_SYU_3 = "3";
        /** 税収_4 */
        public static final String ZEI_SYU_4 = "4";

        /** 税収_5 */
        public static final String ZEI_SYU_5 = "5";
        /** 税収_6 */
        public static final String ZEI_SYU_6 = "6";
        /** 税収_7 */
        public static final String ZEI_SYU_7 = "7";
        /** 税収_8 */
        public static final String ZEI_SYU_8 = "8";

        /** 税収_9 */
        public static final String ZEI_SYU_9 = "9";

    }

    /**
    * 端数区分
    **/
    public static final class HASU_KBN {
        /** 小数点切り捨て */
        public static final String KBN_SUTE = "0";

        /** 小数点四捨五入*/
        public static final String KBN_GONYU = "1";

        /** 小数点切り上げ*/
        public static final String KBN_AGE = "2";
    }

    /**
    * 端数区分設定
    **/
    public static final String CON_HASU_KBN = HASU_KBN.KBN_SUTE;

    /**
    * Beanオブジェクトを構築します.
    * <p>
    * 概要：<br>
    * Beanオブジェクトを構築します。<br>
    * <p>
    * @author hungtb 
    * @version 1.00
    * @param なし
    **/
    public CCKK0010Tax() {
        clear();
    }

    /**
    * メンバの初期化処理.
    * <p>
    * 機能概要：<br>
    * メンバの初期化処理を行います。（実装必須メソッド）
    * <p> 
    * @param なし
    **/
    public void clear() {
        taxKbn = "";
        hasuKbn = "";
        kingkIn = "";
        uchiStaxFlg = "";
        taxSyu = "";
        ritu = "";
        taxKingk = "";
        kingkOt = "";
        taxResult = "";
    }

    /**
    * メイン処理.
    * <p>
    * 機能概要：<br>
    * メイン処理を行います。
    * <p> 
    * @param なし
    * @return true：処理成功、false：処理失敗
    **/
    public boolean doCheckTaxKingk() {
        if (!checkCtlMstTax()) {
            return false;
        }
        if (!checkTaxKingk()) {
            return false;
        }
        return true;
    }

    /**
    * 消費税情報編集処理.
    * <p>
    * 機能概要：<br>
    * 消費税情報の編集を行います。
    * <p> 
    * @param なし
    * @return true：処理成功、false：処理失敗
    **/
    public boolean checkTaxKingk() {
        double dRitu = 0;
        long lTaxAll = 0;

        long lKingk = CCStringUtil.cnvStrToLong(kingkIn);
        double dKingk = CCStringUtil.cnvStrToDbl(kingkIn);

        if (CCNumberUtil.isNumeric(taxKbn)) {
            if (taxKbn.equals(ZEI_KBN.ZEI_SYU_1) || taxKbn.equals(ZEI_KBN.ZEI_SYU_2)
                    || taxKbn.equals(ZEI_KBN.ZEI_SYU_3) || taxKbn.equals(ZEI_KBN.ZEI_SYU_4)
                    || taxKbn.equals(ZEI_KBN.ZEI_SYU_5) || taxKbn.equals(ZEI_KBN.ZEI_SYU_6)
                    || taxKbn.equals(ZEI_KBN.ZEI_SYU_7) || taxKbn.equals(ZEI_KBN.ZEI_SYU_8)
                    || taxKbn.equals(ZEI_KBN.ZEI_SYU_9)) {

                String sRitu = ritu.substring(0, 3) + "." + ritu.substring(3);
                dRitu = Double.parseDouble(sRitu);
            } else {
                return false;
            }
        } else {
            return false;
        }

        // ---------
        // 消費税計算
        // ---------
        DecimalFormat df = new DecimalFormat();
        df.applyPattern("0");
        df.setMaximumFractionDigits(0);

        if (taxKbn.equals(ZEI_KBN.ZEI_SYU_5) || taxKbn.equals(ZEI_KBN.ZEI_SYU_6)
                || taxKbn.equals(ZEI_KBN.ZEI_SYU_7) || taxKbn.equals(ZEI_KBN.ZEI_SYU_8)) {

            if (dRitu > 0) {
                // 税率がゼロ以上の場合
                double dTax = CCNumberUtil.multiplyDouble(dKingk, dRitu);

                if (hasuKbn.equals(HASU_KBN.KBN_SUTE)) {
                    lTaxAll = (long) dTax / 100;
                } else if (hasuKbn.equals(HASU_KBN.KBN_GONYU)) {
                    double dTaxAll = dTax / 100;
                    String sTaxAll = df.format(dTaxAll);
                    lTaxAll = CCStringUtil.cnvStrToLong(sTaxAll);

                } else if (hasuKbn.equals(HASU_KBN.KBN_AGE)) {
                    lTaxAll = (long) dTax / 100;
                    if (100 % dTax != 0) {
                        lTaxAll += 1;
                    }
                }
            }
            taxKingk = String.valueOf(lTaxAll);
            kingkOt = kingkIn;
            taxResult = String.valueOf(lKingk + lTaxAll);

        } else if (taxKbn.equals(ZEI_KBN.ZEI_SYU_1) || taxKbn.equals(ZEI_KBN.ZEI_SYU_2)
                || taxKbn.equals(ZEI_KBN.ZEI_SYU_3) || taxKbn.equals(ZEI_KBN.ZEI_SYU_4)) {
            // 内税

            // 税率がゼロ以上の場合
            if (dRitu > 0) {

                // 税率算出
                double dTax1 = (double) dRitu / 100;
                double dTax2 = dTax1 + 1;

                // 端数区分"0"(小数点切り捨て)
                if (hasuKbn.equals(HASU_KBN.KBN_SUTE)) {

                    // 対象金額に税率+100を掛ける
                    dTax1 = (double) dRitu + 100;
                    double dTax = CCNumberUtil.divideDouble((double) lKingk, dTax1, CCNumberUtil.ROUND_HALF_EVEN);
                    double dTaxAll = CCNumberUtil.multiplyDouble(dTax, dRitu);
                    lTaxAll = (long) dTaxAll;

                } else if (hasuKbn.equals(HASU_KBN.KBN_GONYU)) {
                    // 対象金額に税率/100を掛ける
                    double dTax = CCNumberUtil.multiplyDouble(dKingk, dTax1);

                    // 上記で算出した金額に税率+1を除算
                    double dTaxAll = CCNumberUtil.divideDouble(dTax, dTax2, CCNumberUtil.ROUND_HALF_UP);
                    lTaxAll = (long) dTaxAll;
                    // 小数点四捨五入
                } else if (hasuKbn.equals(HASU_KBN.KBN_AGE)) {
                    // 端数区分"2"(小数点切り上げ)
                    // 対象金額に税率/100を掛ける
                    double dTax = CCNumberUtil.multiplyDouble(dKingk, dTax1);

                    // 上記で算出した金額に税率+1を除算
                    double dTaxAll = CCNumberUtil.divideDouble(dTax, dTax2, CCNumberUtil.ROUND_DOWN);
                    lTaxAll = (long) dTaxAll;
                    // 小数点以下が存在する場合は１加算する
                    if (100 % dTax != 0) {
                        lTaxAll += 1;
                    }
                }
                // 消費税額のセット
                taxKingk = String.valueOf(lTaxAll);

                // 対象金額より税額減算
                kingkOt = String.valueOf(lKingk - lTaxAll);

                // 税込金額へ対象金額をセット
                taxResult = kingkIn;
            } else {
                // 消費税額のセット
                taxKingk = "0";

                // 対象金額
                kingkOt = kingkIn;

                // 税込金額へ対象金額をセット
                taxResult = kingkIn;
            }
        } else if (taxKbn.equals(ZEI_KBN.ZEI_SYU_9)) {
            // 対象金額の消費税額
            taxKingk = "0";
            // 対象金額をそのまま税込金額へセット
            taxResult = kingkIn;
        }
        return true;
    }

    /**
    * 税レコード取得処理.
    * <p>
    * 機能概要：<br>
    * コントロールマスタから税レコードを取得します。
    * <p> 
    * @param なし
    * @return true：処理成功、false：処理失敗
    **/
    public boolean checkCtlMstTax() {
        String sRet = "";

        // 税区分の取得
        String sTaxKbn = taxKbn;
        // 税区分のチェック
        if (CCNumberUtil.isNumeric(sTaxKbn)) {
            if (sTaxKbn.equals(ZEI_KBN.ZEI_SYU_1) || sTaxKbn.equals(ZEI_KBN.ZEI_SYU_2)
                    || sTaxKbn.equals(ZEI_KBN.ZEI_SYU_3) || sTaxKbn.equals(ZEI_KBN.ZEI_SYU_4)
                    || sTaxKbn.equals(ZEI_KBN.ZEI_SYU_5) || sTaxKbn.equals(ZEI_KBN.ZEI_SYU_6)
                    || sTaxKbn.equals(ZEI_KBN.ZEI_SYU_7) || sTaxKbn.equals(ZEI_KBN.ZEI_SYU_8)
                    || sTaxKbn.equals(ZEI_KBN.ZEI_SYU_9)) {

            } else {
                return false;
            }
        } else {
            return false;
        }

        M020ctlmExample example = new M020ctlmExample();
        example.createCriteria().andCdEqualTo(M020_KEY.KEY_TAX + M020_KEY.KEY_ZEI_1 + sTaxKbn);

        List<M020ctlm> result = m020ctlm.selectByExample(example);
        if (result.size() > 0) {
            sRet = CCStringUtil.trimBoth(result.get(0).getData());
            taxSyu = sRet.substring(0, 1);
            ritu = sRet.substring(1, 5);
        } else {
            return false;
        }
        return true;
    }

    /**
    * 税区分のセット処理.
    * <p>
    * 機能概要：<br>
    * 税区分をセットします。
    * <p> 
    * @param strval 税区分
    **/
    public void setTaxKbn(String strval) {
        taxKbn = strval;
    }

    /**
    * 税区分取得処理.
    * <p>
    * 機能概要：<br>
    * メンバ変数にセットされている税区分を取得します。
    * <p> 
    * @param なし
    * @return 税区分
    **/
    public String getTaxKbn() {
        return taxKbn;
    }

    /**
    * 端数区分のセット処理.
    * <p>
    * 機能概要：<br>
    * 端数区分をセットします。
    * <p> 
    * @param strval 端数区分
    **/
    public void setHasuKbn(String strval) {
        hasuKbn = strval;
    }

    /**
    * 端数区分取得処理.
    * <p>
    * 機能概要：<br>
    * メンバ変数にセットされているを端数区分取得します。
    * <p> 
    * @param なし
    * @return 端数区分
    **/
    public String getHasuKbn() {
        return hasuKbn;
    }

    /**
    * 課税対象金額のセット処理.
    * <p>
    * 機能概要：<br>
    * 課税対象金額をセットします。
    * <p> 
    * @param strval 課税対象金額
    **/
    public void setKingkIn(String strval) {
        kingkIn = strval;
    }

    /**
    * 課税対象金額取得処理.
    * <p>
    * 機能概要：<br>
    * メンバ変数にセットされている課税対象金額を取得します。
    * <p> 
    * @param なし
    * @return 課税対象金額
    **/
    public String getKingkIn() {
        return kingkIn;
    }

    /**
    * 課税対象金額のセット処理.
    * <p>
    * 機能概要：<br>
    * 課税対象金額をセットします。
    * <p> 
    * @param strval 課税対象金額
    **/
    public void setKingkOt(String strval) {
        kingkOt = strval;
    }

    /**
    * 課税対象金額取得処理.
    * <p>
    * 機能概要：<br>
    * メンバ変数にセットされている課税対象金額を取得します。
    * <p> 
    * @param なし
    * @return 課税対象金額
    **/
    public String getKingkOt() {
        return kingkOt;
    }

    /**
    * 内税計算フラグのセット処理.
    * <p>
    * 機能概要：<br>
    * 内税計算フラグをセットします。
    * <p> 
    * @param strval 内税計算フラグ
    **/
    public void setUchiStaxFlg(String strval) {
        uchiStaxFlg = strval;
    }

    /**
    * 内税計算フラグ取得処理.
    * <p>
    * 機能概要：<br>
    * メンバ変数にセットされている内税計算フラグを取得します。
    * <p> 
    * @param なし
    * @return 内税計算フラグ
    **/
    public String getUchiStaxFlg() {
        return uchiStaxFlg;
    }

    /**
    * 税種別のセット処理.
    * <p>
    * 機能概要：<br>
    * 税種別をセットします。
    * <p> 
    * @param strval 税種別 1:外税 2:内税 3:非課税
    **/
    public void setTaxSyu(String strval) {
        taxSyu = strval;
    }

    /**
    * 税種別取得処理.
    * <p>
    * 機能概要：<br>
    * メンバ変数にセットされている税種別を取得します。
    * <p> 
    * @param なし
    * @return 税種別 1:外税 2:内税 3:非課税
    **/
    public String getTaxSyu() {
        return taxSyu;
    }

    /**
    * 税率のセット処理.
    * <p>
    * 機能概要：<br>
    * 税率をセットします。
    * <p> 
    * @param strval 税率
    **/
    public void setRitu(String strval) {
        ritu = strval;
    }

    /**
    * 税率取得処理.
    * <p>
    * 機能概要：<br>
    * メンバ変数にセットされている税率を取得します。
    * <p> 
    * @param なし
    * @return 税率
    **/
    public String getRitu() {
        return ritu;
    }

    /**
    * 税額のセット処理.
    * <p>
    * 機能概要：<br>
    * 税額をセットします。
    * <p> 
    * @param strval 税額
    **/
    public void setTaxKingk(String strval) {
        taxKingk = strval;
    }

    /**
    * 税額取得処理.
    * <p>
    * 機能概要：<br>
    * メンバ変数にセットされているを税額取得します。
    * <p> 
    * @param なし
    * @return 税額
    **/
    public String getTaxKingk() {
        return taxKingk;
    }

    /**
    * 税込金額のセット処理.
    * <p>
    * 機能概要：<br>
    * 税込金額をセットします。
    * <p> 
    * @param strval 税込金額
    **/
    public void setTaxResult(String strval) {
        taxResult = strval;
    }

    /**
    * 税込金額取得処理.
    * <p>
    * 機能概要：<br>
    * メンバ変数にセットされているを税込金額取得します。
    * <p> 
    * @param なし
    * @return 税込金額
    **/
    public String getTaxResult() {
        return taxResult;
    }

}
