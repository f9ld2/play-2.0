///////////////////////////////////////////////////////////////////////
// Copyright(C) 2015 NEC Solution Innovators, Ltd. All Rights Reserved.
///////////////////////////////////////////////////////////////////////

/* ====================================================================
 * 機能名称 : 移動伝票入力
 * 改版履歴
 * Rev. 改版年月日   改版者名       内容
 * 1.0   2014.04.05   Hungtb      新規作成
 * =================================================================== */
package jp.co.necsoft.web_md.core.common.biz.si;

/**
 * SI定数
 */
public class CCSIConst {
    
    /**
    * 確定区分定数定義内部クラス
    **/
    public static final class ENTRY_KBN {
        /**
        * ＷＥＢ確定（Ｆエール（本部））
        **/
        public static final String ENTRY_KBN_01 = "01";
        /**
        * ＷＥＢ確定（オアシス（本部））
        **/
        public static final String ENTRY_KBN_02 = "02";
        /**
        * ＷＥＢ確定（ファミリーストア（本部））
        **/
        public static final String ENTRY_KBN_03 = "03";
        /**
        * ＷＥＢ確定（百貨店（本部））
        **/
        public static final String ENTRY_KBN_04 = "04";
        /**
        * ＷＥＢ確定（キッチンエール（本部））
        **/
        public static final String ENTRY_KBN_05 = "05";
        /**
        * ＷＥＢ確定（Ｆエール（店舗））
        **/
        public static final String ENTRY_KBN_11 = "11";
        /**
        * ＷＥＢ確定（オアシス（店舗））
        **/
        public static final String ENTRY_KBN_12 = "12";
        /**
        * ＷＥＢ確定（ファミリーストア（店舗））
        **/
        public static final String ENTRY_KBN_13 = "13";
        /**
        * ＷＥＢ確定（百貨店（店舗））
        **/
        public static final String ENTRY_KBN_14 = "14";
        /**
        * ＷＥＢ確定（キッチンエール（店舗））
        **/
        public static final String ENTRY_KBN_15 = "15";
        /**
        * ＷＥＢ確定（入力センター（ＥＯＳ））
        **/
        public static final String ENTRY_KBN_51 = "51";
        /**
        * ＷＥＢ確定（入力センター（手書））
        **/
        public static final String ENTRY_KBN_52 = "52";
        /**
        * ＷＥＢ確定（ＡＳＮ（ＥＯＳ））
        **/
        public static final String ENTRY_KBN_61 = "61";
        /**
        * ＷＥＢ確定（ＡＳＮ（手書））
        **/
        public static final String ENTRY_KBN_62 = "62";
        /**
        * ＷＥＢ確定（統合基幹一括確定）
        **/
        public static final String ENTRY_KBN_80 = "80";
        
        /**
        * ＷＥＢ確定（ニッショー（本部））
        **/
        public static final String ENTRY_KBN_06 = "06";
        /**
        * ＷＥＢ確定（ニッショー（店舗））
        **/
        public static final String ENTRY_KBN_16 = "16";
    }
    /**
    * 処理状態区分定数定義内部クラス
    **/
    public static final class SYORISTS_KBN {
        /**
        * 未確定
        **/
        public static final String KBN_MIKAKTEI = "1";
        /**
        * 確定済
        **/
        public static final String KBN_KAKTEI = "2";
        /**
        * 支払済
        **/
        public static final String KBN_SHR_END = "3";
        /**
        * 買掛済（未使用）
        **/
        public static final String KBN_KAK_END = "4";
        /**
        * 月次済
        **/
        public static final String KBN_GETJI_END = "5";
        /**
        * 削除済
        **/
        public static final String KBN_DELETE = "9";
    }
    /**
    * ＋/－区分定数定義内部クラス
    **/
    public static final class KASAN_KBN {
        /**
        * 数量・金額プラス
        **/
        public static final String KBN_SUKIN_A = "1";
    }
    /**
    * 伝区定数定義内部クラス
    **/
    public static final class DEN_KBN {
        /**
        * ＥＯＳ仕入伝票（定番）
        **/
        public static final String KBN_EOSSIR = "11";
        /**
        * ＥＯＳ仕入伝票（奉仕）
        **/
        public static final String KBN_EOSSIR_D = "16";
        /**
        * 手書仕入伝票（定番）
        **/
        public static final String KBN_TGKSIR = "14";
        /**
        * 手書仕入伝票（奉仕）
        **/
        public static final String KBN_TGKSIR_D = "19";
        /**
        * 生鮮ＥＯＳ仕入伝票
        **/
        public static final String KBN_SSNEOS = "13";
        /**
        * 売仕伝票
        **/
        public static final String KBN_URISHI = "15";
        /**
        * 売仕伝票（マイナス）
        **/
        public static final String KBN_URISHI_M = "18";
        /**
        * 返品伝票（手書定番）
        **/
        public static final String KBN_HPN = "24";
        /**
        * 返品伝票（手書奉仕）
        **/
        public static final String KBN_HPN_D = "29";
        /**
        * 値引伝票（手書）
        **/
        public static final String KBN_NBK = "25";
        /**
        * 値引修正伝票（手書）
        **/
        public static final String KBN_SUBMDF = "28";
        /**
        * 移動伝票（EOS定番）
        **/
        public static final String KBN_IDO = "31";
        /**
        * 移動伝票（EOS奉仕）
        **/
        public static final String KBN_IDO_D = "36";
        /**
        * 移動伝票（手書定番）
        **/
        public static final String KBN_TGKIDO = "34";
        /**
        * 移動伝票（手書奉仕）
        **/
        public static final String KBN_TGKIDO_D = "39";
        /**
        * 移動伝票（生鮮）
        **/
        public static final String KBN_SSNIDO = "33";
        /**
        * ONLINE値上伝票
        **/
        public static final String KBN_ONLINE_UP = "02";
        /**
        * ONLINE値下伝票
        **/
        public static final String KBN_ONLINE_DW = "03";
        /**
        * ONLINE破損伝票
        **/
        public static final String KBN_ONLINE_HIK = "04";
        /**
        * ONLINEレジ値下伝票
        **/
        public static final String KBN_ONLINE_REG_DW = "05";
        /**
        * 手書値上伝票
        **/
        public static final String KBN_TGK_UP = "06";
        /**
        * 手書値下伝票
        **/
        public static final String KBN_TGK_DW = "07";
        /**
        * 手書レジ値下伝票
        **/
        public static final String KBN_TGK_REG_DW = "08";
        /**
        * 手書破損伝票
        **/
        public static final String KBN_HIK = "09";
        /**
        * 手書破損修正伝票
        **/
        public static final String KBN_HIK_MDF = "10";
    }
    /**
    * 会社コード
    **/
    public static final class KAISYA_CD {
        /**
        * 阪急グループ事業サポートシステム
        **/
        public static final String CD_JISYA = "00";
    }
    /**
    * 事業部コード
    **/
    public static final class JIGYOBU_CD {
        /**
        * Fエール
        **/
        public static final String CD_FYELL = "90";
        
        /**
        * オアシス
        **/
        public static final String CD_OASIS = "01";

        /**
        * ファミリーストア
        **/
        public static final String CD_FSTORE = "02";

        /**
        * 百貨店
        **/
        public static final String CD_DEPART = "10";

        /**
        * キッチンエール
        **/
        public static final String CD_KYELL = "20";
        
        /**
        * ニッショー
        **/
        public static final String CD_NS = "03";
    }

    /**
    * 総量発注区分
    **/
    public static final class SOURYOU_KBN {
        /**
        * 区分"1"
        **/
        public static final String KBN_1 = "1";
        /**
        * 区分"0"
        **/
        public static final String KBN_0 = "0";
    }
    /**
    * 理由区分定数定義内部クラス
    **/
    public static final class RIYU_KBN {
        /**
        * 市価の変動
        **/
        public static final String RIYU_KBN_10 = "10";
        /**
        * 半端物
        **/
        public static final String RIYU_KBN_11 = "11";
        /**
        * 特売終了（値上）
        **/
        public static final String RIYU_KBN_12 = "12";
        /**
        * 売行き不良
        **/
        public static final String RIYU_KBN_13 = "13";
        /**
        * 競合店との比較
        **/
        public static final String RIYU_KBN_14 = "14";
        /**
        * 特注値引き
        **/
        public static final String RIYU_KBN_15 = "15";
        /**
        * 定番仕入在庫品の奉仕販売
        **/
        public static final String RIYU_KBN_16 = "16";
        /**
        * 値付違い
        **/
        public static final String RIYU_KBN_17 = "17";
        /**
        * 特付提供
        **/
        public static final String RIYU_KBN_18 = "18";
        /**
        * 難物
        **/
        public static final String RIYU_KBN_19 = "19";
        /**
        * 鮮度・期限
        **/
        public static final String RIYU_KBN_25 = "25";
        /**
        * 試食・その他 
        **/
        public static final String RIYU_KBN_27 = "27";
        /**
        * 奉仕値下
        **/
        public static final String RIYU_KBN_40 = "40";
        /**
        * レジ値引き
        **/
        public static final String RIYU_KBN_50 = "50";
        /**
        * 定番売価変更による
        **/
        public static final String RIYU_KBN_60 = "60";
        /**
        * その他
        **/
        public static final String RIYU_KBN_90 = "90";
    }

    /**
    * 税区分（内税）定義内部クラス
    **/
    public static final class TAX_IO_KBN {
        /**
        * 内税
        **/
        public static final String TAX_IN_KBN = "0";

        /**
        * 外税
        **/
        public static final String TAX_EX_KBN = "1";
    }

    /**
    * 税区分（内税）定義内部クラス
    **/
    public static final class TAX_IN_KBN {
        /**
        * 税区分１
        **/
        public static final String TAX_IN_KBN_1 = "1";

        /**
        * 税区分２
        **/
        public static final String TAX_IN_KBN_2 = "2";

        /**
        * 税区分３
        **/
        public static final String TAX_IN_KBN_3 = "3";

        /**
        * 税区分４
        **/
        public static final String TAX_IN_KBN_4 = "4";
    }

    /**
    * 税区分（外税）定義内部クラス
    **/
    public static final class TAX_EX_KBN {
        /**
        * 税区分５
        **/
        public static final String TAX_EX_KBN_5 = "5";

        /**
        * 税区分６
        **/
        public static final String TAX_EX_KBN_6 = "6";

        /**
        * 税区分７
        **/
        public static final String TAX_EX_KBN_7 = "7";

        /**
        * 税区分８
        **/
        public static final String TAX_EX_KBN_8 = "8";
    }

    /**
    * 税区分９（非課税）
    **/
    public static final String TAX_FREE_KBN_9 = "9";

    /**
    * 税区分０（上位参照）
    **/
    public static final String TAX_KBN_0 = "0";

    /**
    * 売価還元区分
    **/
    public static final class BAIKA_KANRI {
        /**
        * 売価還元法
        **/
        public static final String BAIKA_KANGEN = "1";

        /**
        * 移動平均原価
        **/
        public static final String IDO_HEIKIN_GENKA = "2";

        /**
        * 原価在庫
        **/
        public static final String GENKA_ZAIKO = "3";

    }
}
