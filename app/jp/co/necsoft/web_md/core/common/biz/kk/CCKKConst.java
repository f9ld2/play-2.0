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

/**
 * Copy from cmJKKConst.
 * 
 * @author hungtbz
 * @since 2014/03/13
 */
public class CCKKConst {

    /**
     * SEISAN_KBN
     */
    public static final class SEISAN_KBN {
        /**
        * KBN_FURI_OL = 1
        **/
        public static final int KBN_FURI_OL = 1;
        /**
        * KBN_FURI_TGK = 2
        **/
        public static final int KBN_FURI_TGK = 2;
        /**
        * KBN_GENKIN = 3
        **/
        public static final int KBN_GENKIN = 3;
        /**
        * KBN_KOGUCHI = 4
        **/
        public static final int KBN_KOGUCHI = 4;
        /**
        * KBN_TEGATA= 5
        **/
        public static final int KBN_TEGATA = 5;
        /**
        * KBN_JIFURI = 6
        **/
        public static final int KBN_JIFURI = 6;
    }

    /**
     * SHIHARAI_KBN
     */
    public static final class SHIHARAI_KBN {
        /**
        * KBN_YOKU = "01"
        **/
        public static final String KBN_YOKU = "01";
        /**
        * KBN_AFTER_10 = "10"
        **/
        public static final String KBN_AFTER_10 = "10";
        /**
        * KBN_AFTER_15 = "15"
        **/
        public static final String KBN_AFTER_15 = "15";
        /**
        * KBN_AFTER_30 = "30"
        **/
        public static final String KBN_AFTER_30 = "30";
        /**
        * KBN_AFTER_60 = "60"
        **/
        public static final String KBN_AFTER_60 = "60";
        /**
        * KBN_AFTER_90 = "90"
        **/
        public static final String KBN_AFTER_90 = "90";
        /**
        * KBN_THIS_25 = "25"
        **/
        public static final String KBN_THIS_25 = "25";
    }

    /**
     * SHIME_KBN
     */
    public static final class SHIME_KBN {
        /**
        * KBN_05DAY = "05"
        **/
        public static final String KBN_05DAY = "05";
        /**
        * KBN_10DAY = "10"
        **/
        public static final String KBN_10DAY = "10";
        /**
        * KBN_15DAY = "15"
        **/
        public static final String KBN_15DAY = "15";
        /**
        * KBN_GETUJI = "30"
        **/
        public static final String KBN_GETUJI = "30";
        /**
        * KBN_KOG_ETC = "99"
        **/
        public static final String KBN_KOG_ETC = "99";
    }

    /**
     * SYOGO_KBN
     */
    public static final class SYOGO_KBN {
        /**
        * 相手先照合・支払明細発行
        **/
        public static final int KBN_AITE_MEISAI = 1;
        /**
        * 相手先照合・支払オンライン
        **/
        public static final int KBN_AITE_OL = 2;
        /**
        * 相手先照合・支払明細書＋支払オンライン
        **/
        public static final int KBN_AITE_MEISAIOL = 3;
        /**
        * 自社照合
        **/
        public static final int KBN_JIFURI = 4;
    }
}
