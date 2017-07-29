///////////////////////////////////////////////////////////////////////
// Copyright(C) 2015 NEC Solution Innovators, Ltd. All Rights Reserved.
///////////////////////////////////////////////////////////////////////

/*
 * ====================================================================
 * 
 * 機能名称 : 区分コンスタント 改版履歴 Rev. 改版年月日 改版者名 内容 1.0
 * 
 * 2014.04.11 HaiNP 新規作成
 * 
 * ===================================================================
 */
package jp.co.necsoft.web_md.core.common;

/**
 * 区分コンスタント
 * 
 */
public class CCKubunConst {
    /** 年 */
    public static final String KBN_KEY_X_YEAR = "X0001";
    /** 月 */
    public static final String KBN_KEY_X_MONTH = "X0002";
    /** 日 */
    public static final String KBN_KEY_X_DAY = "X0003";
    /** 時 */
    public static final String KBN_KEY_X_HOUR = "X0004";
    /** 分 */
    public static final String KBN_KEY_X_MIN = "X0005";
    /** 秒 */
    public static final String KBN_KEY_X_SEC = "X0006";
    /** 税区分 */
    public static final String KBN_KEY_M_TAX_KBN = "M0001";
    /** POSタイプ区分 */
    public static final String KBN_KEY_M_POS_TYP_KBN = "M0002";
    /** TEC */
    public static final String KBN_VAL_M_POS_TYP_KBN_TEC = "2";
    /** 発注休止区分 */
    public static final String KBN_KEY_M_KYUSI_KBN = "M0003";
    /** 休止 */
    public static final String KBN_VAL_M_KYUSI_KBN_YES = "1";
    /** 通常 */
    public static final String KBN_VAL_M_KYUSI_KBN_NO = "9";
    /** 出力条件 */
    public static final String KBN_KEY_M_COND_GROUP = "M0004";
    /** 鳴尾浜 */
    public static final String KBN_VAL_M_COND_GROUP_1 = "1";
    /** 北部市場 */
    public static final String KBN_VAL_M_COND_GROUP_2 = "2";
    /** 片岡運送 */
    public static final String KBN_VAL_M_COND_GROUP_3 = "3";
    /** 予備 */
    public static final String KBN_VAL_M_COND_GROUP_4 = "4";
    /** 売価還元区分 */
    public static final String KBN_KEY_M_BAI_KAN_KBN = "M0005";
    /** 売価還元法 */
    public static final String KBN_VAL_M_BAI_KAN_KBN_RETAIL_METHOD = "1";
    /** 移動平均原価法 */
    public static final String KBN_VAL_M_BAI_KAN_KBN_MOVING_AVERAGE_METHOD = "2";
    /** 原価在庫 */
    public static final String KBN_VAL_M_BAI_KAN_KBN_COST_STOCK = "3";
    /** ０原価 */
    public static final String KBN_VAL_M_BAI_KAN_KBN_ZERO_COST = "4";
    /** ０原価（荒利無し） */
    public static final String KBN_VAL_M_BAI_KAN_KBN_ZERO_COST_WITHOUT_PROFIT = "5";
    /** 売仕区分 */
    public static final String KBN_KEY_M_URI_KBN = "M0006";
    /** 売仕対象 */
    public static final String KBN_VAL_M_URI_KBN_YES = "1";
    /** 売仕（手書伝票） */
    public static final String KBN_VAL_M_URI_KBN_HANDWRITTEN_SLIP = "2";
    /** 対象外 */
    public static final String KBN_VAL_M_URI_KBN_NO = "9";
    /** 酒税区分 */
    public static final String KBN_KEY_M_SAKETAX_KBN = "M0007";
    /** 酒税対象 */
    public static final String KBN_VAL_M_SAKETAX_KBN_YES = "1";
    /** 対象外 */
    public static final String KBN_VAL_M_SAKETAX_KBN_NO = "9";
    /** Fエール扱区分 */
    public static final String KBN_KEY_M_FY_KBN = "M0008";
    /** Fエール扱 */
    public static final String KBN_VAL_M_FY_KBN_YES = "1";
    /** 対象外 */
    public static final String KBN_VAL_M_FY_KBN_NO = "9";
    /** 曜日 */
    public static final String KBN_KEY_M_WEEK_DAY = "M0009";
    /** 月曜日 */
    public static final String KBN_VAL_M_WEEK_DAY_MON = "1";
    /** 火曜日 */
    public static final String KBN_VAL_M_WEEK_DAY_TUE = "2";
    /** 水曜日 */
    public static final String KBN_VAL_M_WEEK_DAY_WED = "3";
    /** 木曜日 */
    public static final String KBN_VAL_M_WEEK_DAY_THU = "4";
    /** 金曜日 */
    public static final String KBN_VAL_M_WEEK_DAY_FRI = "5";
    /** 土曜日 */
    public static final String KBN_VAL_M_WEEK_DAY_SAT = "6";
    /** 日曜日 */
    public static final String KBN_VAL_M_WEEK_DAY_SUN = "7";
    /** 週No */
    public static final String KBN_KEY_M_WEEK_NO = "M0010";
    /** 祝祭日区分 */
    public static final String KBN_KEY_M_NHLDAY_KBN = "M0011";
    /** 平日 */
    public static final String KBN_VAL_M_NHLDAY_KBN_NO = "1";
    /** 祝祭日 */
    public static final String KBN_VAL_M_NHLDAY_KBN_YES = "2";
    /** 店舗規模区分 */
    public static final String KBN_KEY_M_KIBO_KBN = "M0012";
    /** 大型店 */
    public static final String KBN_VAL_M_KIBO_KBN_GMS = "1";
    /** スーパーマーケット */
    public static final String KBN_VAL_M_KIBO_KBN_SM = "2";
    /** 専門店 */
    public static final String KBN_VAL_M_KIBO_KBN_SPECIALTY = "3";
    /** 店休日区分 */
    public static final String KBN_KEY_M_HOLIDAY_KBN = "M0013";
    /** 店休日 */
    public static final String KBN_VAL_M_HOLIDAY_KBN_YES = "1";
    /** その他 */
    public static final String KBN_VAL_M_HOLIDAY_KBN_NO = "0";
    /** コード */
    public static final String KBN_KEY_M_CTRL_UR = "M0014";
    /** 売上管理レコード */
    public static final String KBN_VAL_M_CTRL_UR_UR001 = "UR001";
    /** 売上実績保存期間 */
    public static final String KBN_VAL_M_CTRL_UR_UR003 = "UR003";
    /** 発注種類区分 */
    public static final String KBN_KEY_H_HAT_SRUI_KBN = "H0001";
    /** 店舗 */
    public static final String KBN_VAL_H_HAT_SRUI_KBN_STORE = "11";
    /** 本部 */
    public static final String KBN_VAL_H_HAT_SRUI_KBN_HEADQUARTERS = "20";
    /** 特売 */
    public static final String KBN_VAL_H_HAT_SRUI_KBN_PROMOTION = "40";
    /** 新店 */
    public static final String KBN_VAL_H_HAT_SRUI_KBN_NEW_STORE = "50";
    /** 生鮮 */
    public static final String KBN_VAL_H_HAT_SRUI_KBN_FRESH = "70";
    /** */
    public static final String KBN_VAL_H_HAT_SRUI_KBN_SUPPLIES = "78";
    /** */
    public static final String KBN_VAL_H_HAT_SRUI_KBN_PRE_ORDER = "79";
    /** 用度品 */
    public static final String KBN_VAL_H_HAT_SRUI_KBN_PRODUCT_ORDER = "90";
    /** 出力順指定区分 */
    public static final String KBN_KEY_H_SORT_ORDER = "H0002";
    /** 登録順 */
    public static final String KBN_VAL_H_SORT_ORDER_REGISTRATION = "1";
    /** 商品順 */
    public static final String KBN_VAL_H_SORT_ORDER_ITEM = "2";
    /** 店舗順 */
    public static final String KBN_VAL_H_SORT_ORDER_STORE = "3";
    /** 印刷区分 */
    public static final String KBN_KEY_H_COND_JOTAI_KBN = "H0003";
    /** 全件 */
    public static final String KBN_VAL_H_COND_JOTAI_KBN_BOTH = "0";
    /** 未確定分 */
    public static final String KBN_VAL_H_COND_JOTAI_KBN_NOT_YET = "1";
    /** 確定分 */
    public static final String KBN_VAL_H_COND_JOTAI_KBN_DONE = "2";
    /** 出力対象 */
    public static final String KBN_KEY_H = "H0004";
    /** 便No */
    public static final String KBN_KEY_H_BIN = "H0005";
    /** 出力区分 */
    public static final String KBN_KEY_Z_COND_PERIOD = "Z0001";
    /** 前期 */
    public static final String KBN_VAL_Z_COND_PERIOD_PREVIOUS = "1";
    /** 当期 */
    public static final String KBN_VAL_Z_COND_PERIOD_CURRENT = "2";
    /** 出力区分 */
    public static final String KBN_KEY_Z_SUM_TYPE = "Z0002";
    /** 店別 */
    public static final String KBN_VAL_Z_SUM_TYPE_TEN = "1";
    /** 部門別 */
    public static final String KBN_VAL_Z_SUM_TYPE_BMN = "2";
    /** 出力店舗区分 */
    public static final String KBN_KEY_Z_COND_STORE = "Z0003";
    /** 通常営業店舗のみ出力 */
    public static final String KBN_VAL_Z_COND_STORE_OPENED = "1";
    /** 全店舗出力 */
    public static final String KBN_VAL_Z_COND_STORE_ALL = "2";
    /** 移動伝票印字区分 */
    public static final String KBN_KEY_S_COND_TRANSFER_STORE = "S0001";
    /**入荷店のみ*/
    public static final String KBN_VAL_S_COND_TRANSFER_STORE_IN = "1";
    /**出荷店のみ*/
    public static final String KBN_VAL_S_COND_TRANSFER_STORE_OUT = "2";
    /** 両方 */
    public static final String KBN_VAL_S_COND_TRANSFER_STORE_BOTH = "3";
    /** 勘定区分 */
    public static final String KBN_KEY_K_KANJO_KBN = "K0001";
    /** 支払勘定 */
    public static final String KBN_VAL_K_KANJO_KBN_PAYABLE = "1";
    /** 相殺勘定 */
    public static final String KBN_VAL_K_KANJO_KBN_OFFSET = "2";
    /** 税区分 */
    public static final String KBN_KEY_K_TAX_KBN = "K0002";
    /** 外税 */
    public static final String KBN_VAL_K_TAX_KBN_EXCLUDED = "1";
    /** 内税 */
    public static final String KBN_VAL_K_TAX_KBN_INCLUDED = "2";
    /** 非課税 */
    public static final String KBN_VAL_K_TAX_KBN_EXEMPT = "3";
    /** ＥＯＳ区分 */
    public static final String KBN_KEY_M_EOS_KBN = "M0015";
    /** EOS(FIP) */
    public static final String KBN_VAL_M_EOS_KBN_EOS_FIP = "1";
    /** EOS（自社）【未使用】 */
    public static final String KBN_VAL_M_EOS_KBN_EOS_COMPANY = "2";
    /** EOS（その他）【未使用】 */
    public static final String KBN_VAL_M_EOS_KBN_EOS_OTHER = "3";
    /** 自動FAX */
    public static final String KBN_VAL_M_EOS_KBN_AUTO_FAX = "4";
    /** 伝票出力 */
    public static final String KBN_VAL_M_EOS_KBN_SLIP = "5";
    /** EOS(FIP&FAX)【未使用】 */
    public static final String KBN_VAL_M_EOS_KBN_EOS_FIP_AND_FAX = "6";
    /** EOS対象外 */
    public static final String KBN_VAL_M_EOS_KBN_NONE = "9";
    /** 締区分 */
    public static final String KBN_KEY_M_SIME_KBN = "M0016";
    /** １５日毎締め */
    public static final String KBN_VAL_M_SIME_KBN_EVERY_15_DAYS = "15";
    /** ３０日末締め */
    public static final String KBN_VAL_M_SIME_KBN_END_OF_MONTH = "30";
    /** 支払区分 */
    public static final String KBN_KEY_M_PAY_KBN = "M0017";
    /** １０日後 */
    public static final String KBN_VAL_M_PAY_KBN_AFTER_10_DAYS = "10";
    /** １５日後 */
    public static final String KBN_VAL_M_PAY_KBN_AFTER_15_DAYS = "15";
    /** 翌月末 */
    public static final String KBN_VAL_M_PAY_KBN_END_OF_NEXT_MONTH = "30";
    /** 総量発注区分 */
    public static final String KBN_KEY_M_IKATU_FLG = "M0018";
    /** 総量対象 */
    public static final String KBN_VAL_M_IKATU_FLG_YES = "1";
    /** 対象外 */
    public static final String KBN_VAL_M_IKATU_FLG_NO = "9";
    /** 買掛精算区分 */
    public static final String KBN_KEY_M_KAI_SEISAN_KBN = "M0019";
    /** オンライン振込 */
    public static final String KBN_VAL_M_KAI_SEISAN_KBN_TRANSFER_ONLINE = "1";
    /** 振込（手書） */
    public static final String KBN_VAL_M_KAI_SEISAN_KBN_TRANSFER = "2";
    /** 本部現金 */
    public static final String KBN_VAL_M_KAI_SEISAN_KBN_HEADQUARTERS_CASH = "3";
    /** 小口精算 */
    public static final String KBN_VAL_M_KAI_SEISAN_KBN_CASH_ON_HAND = "4";
    /** 手形 */
    public static final String KBN_VAL_M_KAI_SEISAN_KBN_BILL = "5";
    /** 手数料負担区分 */
    public static final String KBN_KEY_M_SIH_DATA_SAKUSEI_KBN = "M0020";
    /** 取引先負担 */
    public static final String KBN_VAL_M_SIH_DATA_SAKUSEI_KBN_SUPPLIER = "1";
    /** 自社負担 */
    public static final String KBN_VAL_M_SIH_DATA_SAKUSEI_KBN_COMPANY = "2";
    /** 配送区分 */
    public static final String KBN_KEY_M_DELIV_KBN = "M0021";
    /** 店直 */
    public static final String KBN_VAL_M_DELIV_KBN_DIRECT_TO_STORE = "0";
    /** 予備 */
    public static final String KBN_VAL_M_DELIV_KBN_RESERVE = "6";
    /** 生鮮便 */
    public static final String KBN_VAL_M_DELIV_KBN_FRESH_DELIVERY = "7";
    /** チルドセンター */
    public static final String KBN_VAL_M_DELIV_KBN_CHILLED_CENTER = "8";
    /** 常温センター */
    public static final String KBN_VAL_M_DELIV_KBN_ROOM_TEMPERATURE_CENTER = "9";
    /** 取引先区分 */
    public static final String KBN_KEY_M_HAT_TRI_KBN = "M0022";
    /** 通常 */
    public static final String KBN_VAL_M_HAT_TRI_KBN_GENERAL = "1";
    /** 内部取引先 */
    public static final String KBN_VAL_M_HAT_TRI_KBN_INTERNAL = "3";
    /** 取引先休止区分 */
    public static final String KBN_KEY_M_TORI_STOP_KBN = "M0023";
    /** 休止 */
    public static final String KBN_VAL_M_TORI_STOP_KBN_YES = "1";
    /** 通常 */
    public static final String KBN_VAL_M_TORI_STOP_KBN_NO = "9";
    /** 支払明細ｵﾝﾗｲﾝ区分 */
    public static final String KBN_KEY_M_PAY_DTL_ONL_KBN = "M0024";
    /** × */
    public static final String KBN_VAL_M_PAY_DTL_ONL_KBN_NONE = "1";
    /** 月中 */
    public static final String KBN_VAL_M_PAY_DTL_ONL_KBN_MIDDLE = "2";
    /** 月末 */
    public static final String KBN_VAL_M_PAY_DTL_ONL_KBN_END = "3";
    /** 支払明細印字区分 */
    public static final String KBN_KEY_M_PAY_DTL_PRT_KBN = "M0025";
    /** × */
    public static final String KBN_VAL_M_PAY_DTL_PRT_KBN_NONE = "1";
    /** 月中 */
    public static final String KBN_VAL_M_PAY_DTL_PRT_KBN_MIDDLE = "2";
    /** 月末 */
    public static final String KBN_VAL_M_PAY_DTL_PRT_KBN_END = "3";
    /** 支払案内データ作成区分 */
    public static final String KBN_KEY_M_SHRAN_OUT_KBN = "M0026";
    /** 作成する */
    public static final String KBN_VAL_M_SHRAN_OUT_KBN_YES = "1";
    /** 作成しない */
    public static final String KBN_VAL_M_SHRAN_OUT_KBN_NO = "9";
    /** 受領データレイアウト区分 */
    public static final String KBN_KEY_M_JYURYO_DT_KBN = "M0027";
    /** 作成しない */
    public static final String KBN_VAL_M_JYURYO_DT_KBN_NONE = "0";
    /** ＦＩＰフォーマット */
    public static final String KBN_VAL_M_JYURYO_DT_KBN_2 = "2";
    /** バローフォーマット */
    public static final String KBN_VAL_M_JYURYO_DT_KBN_3 = "3";
    /** デリカフォーマット */
    public static final String KBN_VAL_M_JYURYO_DT_KBN_4 = "4";
    /** スギ薬局フォーマット */
    public static final String KBN_VAL_M_JYURYO_DT_KBN_5 = "5";
    /** 生鮮ＥＯＳ区分 */
    public static final String KBN_KEY_M_SEI_EOS_KBN = "M0028";
    /** 生鮮Ｗｅｂ */
    public static final String KBN_VAL_M_SEI_EOS_KBN_FRESH_WEB = "1";
    /** オンラインＥＯＳ */
    public static final String KBN_VAL_M_SEI_EOS_KBN_ONLINE = "2";
    /** 生鮮Ｗｅｂ＆オンラインＥＯＳ */
    public static final String KBN_VAL_M_SEI_EOS_KBN_FRESH_WEB_AND_ONLINE = "3";
    /** ＥＯＳ対象外 */
    public static final String KBN_VAL_M_SEI_EOS_KBN_NONE = "9";
    /** 発注レイアウト区分 */
    public static final String KBN_KEY_M_HAT_LAY_KBN = "M0029";
    /** ＦＩＰフォーマット */
    public static final String KBN_VAL_M_HAT_LAY_KBN_01 = "01";
    /** バローフォーマット */
    public static final String KBN_VAL_M_HAT_LAY_KBN_02 = "02";
    /** デリカフォーマット */
    public static final String KBN_VAL_M_HAT_LAY_KBN_03 = "03";
    /** 阪急ベーカリーフォーマット */
    public static final String KBN_VAL_M_HAT_LAY_KBN_04 = "04";
    /** 阪急フーズフォーマット */
    public static final String KBN_VAL_M_HAT_LAY_KBN_05 = "05";
    /** スギ薬局フォーマット */
    public static final String KBN_VAL_M_HAT_LAY_KBN_06 = "06";
    /** ＦＹＤＣ取引先区分 */
    public static final String KBN_KEY_M_FYDC_TRI_KBN = "M0030";
    /** 通常取引先 */
    public static final String KBN_VAL_M_FYDC_TRI_KBN_NO = "0";
    /** ＦＹＤＣ取引先 */
    public static final String KBN_VAL_M_FYDC_TRI_KBN_YES = "1";
    /** 生鮮EOS検品区分 */
    public static final String KBN_KEY_M_SEI_EOS_CHK_KBN = "M0031";
    /** 生鮮Ｗｅｂ */
    public static final String KBN_VAL_M_SEI_EOS_CHK_KBN_FRESH_WEB = "1";
    /** ＴＬＣＳ */
    public static final String KBN_VAL_M_SEI_EOS_CHK_KBN_TLCS = "2";
    /** 直接 */
    public static final String KBN_VAL_M_SEI_EOS_CHK_KBN_DIRECT = "3";
    /** 画面 */
    public static final String KBN_VAL_M_SEI_EOS_CHK_KBN_SCREEN = "4";
    /** 生鮮なし */
    public static final String KBN_VAL_M_SEI_EOS_CHK_KBN_NONE = "9";
    /** 支払データレイアウト区分 */
    public static final String KBN_KEY_M_SIDT_LAY_KBN = "M0032";
    /** ＦＩＰフォーマット */
    public static final String KBN_VAL_M_SIDT_LAY_KBN_1 = "1";
    /** バローフォーマット */
    public static final String KBN_VAL_M_SIDT_LAY_KBN_2 = "2";
    /** 預金種別 */
    public static final String KBN_KEY_M_YOKIN_SYU = "M0033";
    /** 普通 */
    public static final String KBN_VAL_M_YOKIN_SYU_ORDINARY = "1";
    /** 当座 */
    public static final String KBN_VAL_M_YOKIN_SYU_CURRENT = "2";
    /** 貯蓄 */
    public static final String KBN_VAL_M_YOKIN_SYU_SAVING = "4";
    /** その他 */
    public static final String KBN_VAL_M_YOKIN_SYU_OTHER = "9";
    /** 支払取込区分 */
    public static final String KBN_KEY_M_PAY_IMP_KBN = "M0034";
    /** 取込 */
    public static final String KBN_VAL_M_PAY_IMP_KBN_YES = "1";
    /** 取り込まない */
    public static final String KBN_VAL_M_PAY_IMP_KBN_NO = "9";
    /** 物流契約率区分 */
    public static final String KBN_KEY_M_BT_KY_KBN = "M0035";
    /** ＴＣ */
    public static final String KBN_VAL_M_BT_KY_KBN_TC = "1";
    /** ＤＣ */
    public static final String KBN_VAL_M_BT_KY_KBN_DC = "2";
    /** 対象外 */
    public static final String KBN_VAL_M_BT_KY_KBN_NONE = "9";
    /** ASNフラグ */
    public static final String KBN_KEY_M_ASN_FLG = "M0036";
    /** ASNオンライン対象 */
    public static final String KBN_VAL_M_ASN_FLG_YES = "1";
    /** ASNオンライン対象外 */
    public static final String KBN_VAL_M_ASN_FLG_NO = "2";
    /** 仕入確定データ送信区分 */
    public static final String KBN_KEY_M_FEE_KBN = "M0037";
    /** 印刷対象 */
    public static final String KBN_VAL_M_FEE_KBN_YES = "1";
    /** 対象外 */
    public static final String KBN_VAL_M_FEE_KBN_NO = "9";
    /** 複写区分 */
    public static final String KBN_KEY_T_COPY_SOURCE = "T0001";
    /** 親企画 */
    public static final String KBN_VAL_T_COPY_SOURCE_UPPER_PROM = "1";
    /** 企画 */
    public static final String KBN_VAL_T_COPY_SOURCE_PROM = "2";
    /** 理由 */
    public static final String KBN_KEY_S_PRICE_REASON = "S0002";
    /** 対象データ */
    public static final String KBN_KEY_T_COND_OUTPUT = "T0002";
    /** 未出力分のみ */
    public static final String KBN_VAL_T_COND_OUTPUT_NOT_YET = "0";
    /** 全件 */
    public static final String KBN_VAL_T_COND_OUTPUT_BOTH = "1";
    /** 仕入明細表印刷区分 */
    public static final String KBN_KEY_M_PRINT_REPORT = "M0038";
    /** 印刷対象 */
    public static final String KBN_VAL_M_PRINT_REPORT_YES = "1";
    /** 対象外 */
    public static final String KBN_VAL_M_PRINT_REPORT_NO = "9";
    /** ＥＯＳ区分 */
    public static final String KBN_KEY_M_KJ_EOS_KBN = "M0039";
    /** 取引先マスタ参照 */
    public static final String KBN_VAL_M_KJ_EOS_KBN_TRIM = "0";
    /** 発注対象外 */
    public static final String KBN_VAL_M_KJ_EOS_KBN_NONE = "9";
    /** 販売区分 */
    public static final String KBN_KEY_M_HANBAI_KBN = "M0040";
    /** 通常商品 */
    public static final String KBN_VAL_M_HANBAI_KBN_REGULAR = "1";
    /** 便区分 */
    public static final String KBN_KEY_M_BIN_KBN = "M0041";
    /** 1便対応可 */
    public static final String KBN_VAL_M_BIN_KBN_1ST = "01";
    /** 2便対応可 */
    public static final String KBN_VAL_M_BIN_KBN_2ND = "02";
    /** 3便対応可 */
    public static final String KBN_VAL_M_BIN_KBN_3RD = "03";
    /** 4便対応可 */
    public static final String KBN_VAL_M_BIN_KBN_4TH = "04";
    /** 便対応不可 */
    public static final String KBN_VAL_M_BIN_KBN_NONE = "09";
    /** ユニット計算 */
    public static final String KBN_KEY_M_UP_KBN = "M0042";
    /** 計算する */
    public static final String KBN_VAL_M_UP_KBN_YES = "1";
    /** 計算しない */
    public static final String KBN_VAL_M_UP_KBN_NO = "2";
    /** Ｐカード区分 */
    public static final String KBN_KEY_M_PC_HAKO_KBN = "M0043";
    /** Ｌサイズ */
    public static final String KBN_VAL_M_PC_HAKO_KBN_LARGE_SIZE = "1";
    /** Ｓサイズ */
    public static final String KBN_VAL_M_PC_HAKO_KBN_SMALL_SIZE = "2";
    /** 生鮮 */
    public static final String KBN_VAL_M_PC_HAKO_KBN_FRESH = "4";
    /** 発行しない */
    public static final String KBN_VAL_M_PC_HAKO_KBN_NONE = "9";
    /** 発注区分 */
    public static final String KBN_KEY_M_HAT_KT_KBN = "M0044";
    /** Ｐカード発注 */
    public static final String KBN_VAL_M_HAT_KT_KBN_PRICE_CARD = "1";
    /** 日配ＯＢ */
    public static final String KBN_VAL_M_HAT_KT_KBN_DAILY = "3";
    /** 生鮮ＯＢ */
    public static final String KBN_VAL_M_HAT_KT_KBN_FRESH = "5";
    /** 販売のみ */
    public static final String KBN_VAL_M_HAT_KT_KBN_ONLY_SALES = "9";
    /** 不定貫区分 */
    public static final String KBN_KEY_M_HUTEIKAN_KBN = "M0045";
    /** 不定貫商品 */
    public static final String KBN_VAL_M_HUTEIKAN_KBN_YES = "1";
    /** 対象外 */
    public static final String KBN_VAL_M_HUTEIKAN_KBN_NO = "9";
    /** ユニット単位 */
    public static final String KBN_KEY_M_UP_TANI = "M0046";
    /** 業務種別 */
    public static final String KBN_KEY_X_BIZ_TYPE = "X0007";
    /** ﾁﾗｼ区分 */
    public static final String KBN_KEY_T_TIRASI_KBN = "T0003";
    /** 有 */
    public static final String KBN_VAL_T_TIRASI_KBN_YES = "1";
    /** 無 */
    public static final String KBN_VAL_T_TIRASI_KBN_NO = "2";
    /** リピート発注区分 */
    public static final String KBN_KEY_T_REPEAT_KBN = "T0004";
    /** 可 */
    public static final String KBN_VAL_T_REPEAT_KBN_YES = "1";
    /** 不可 */
    public static final String KBN_VAL_T_REPEAT_KBN_NO = "2";
    /** 店舗訂正不可区分 */
    public static final String KBN_KEY_T_TEISEI_KBN = "T0005";
    /** 可 */
    public static final String KBN_VAL_T_TEISEI_KBN_YES = "1";
    /** 不可 */
    public static final String KBN_VAL_T_TEISEI_KBN_NO = "2";
    /** POP発行区分 */
    public static final String KBN_KEY_T_POP_KBN = "T0006";
    /** POP+Pｶｰﾄﾞ */
    public static final String KBN_VAL_T_POP_KBN_POP_AND_PRICE_CARD = "1";
    /** POPのみ */
    public static final String KBN_VAL_T_POP_KBN_POP = "2";
    /** Pｶｰﾄﾞのみ */
    public static final String KBN_VAL_T_POP_KBN_PRICE_CARD = "3";
    /** 不要 */
    public static final String KBN_VAL_T_POP_KBN_NONE = "4";
    /** ｽﾄｺﾝ送信区分 */
    public static final String KBN_KEY_T_STCON_KBN = "T0007";
    /** 要 */
    public static final String KBN_VAL_T_STCON_KBN_YES = "1";
    /** 不要 */
    public static final String KBN_VAL_T_STCON_KBN_NO = "2";
    /** 定番プライスカード発行区分 */
    public static final String KBN_KEY_T_TPCATD_KBN = "T0008";
    /** 作成要 */
    public static final String KBN_VAL_T_TPCATD_KBN_YES = "1";
    /** 不要 */
    public static final String KBN_VAL_T_TPCATD_KBN_NO = "2";
    /** 業務種別 */
    public static final String KBN_KEY_M_CTRL_JIGYOBU = "M0047";
    /** 阪急オアシス */
    public static final String KBN_VAL_M_CTRL_JIGYOBU_0001 = "0001";
    /** 阪急ファミリーストア */
    public static final String KBN_VAL_M_CTRL_JIGYOBU_0002 = "0002";
    /** 阪急ニッショー */
    public static final String KBN_VAL_M_CTRL_JIGYOBU_0003 = "0003";
    /** 阪急百貨店 */
    public static final String KBN_VAL_M_CTRL_JIGYOBU_0010 = "0010";
    /** 阪急キッチンエール */
    public static final String KBN_VAL_M_CTRL_JIGYOBU_0020 = "0020";
    /** 阪急フレッシュエール */
    public static final String KBN_VAL_M_CTRL_JIGYOBU_0090 = "0090";
    /** 保留区分 */
    public static final String KBN_KEY_K_JOTAI_KBN = "K0003";
    /** 確定 */
    public static final String KBN_VAL_K_JOTAI_KBN_DONE = "2";
    /** 保留 */
    public static final String KBN_VAL_K_JOTAI_KBN_HOLD = "3";
    /** ＥＯＢ対象区分 */
    public static final String KBN_KEY_M_EOB_KBN = "M0048";
    /** EOB対象 */
    public static final String KBN_VAL_M_EOB_KBN_YES = "1";
    /** EOB対象外 */
    public static final String KBN_VAL_M_EOB_KBN_NO = "2";
    /** 自動計算区分 */
    public static final String KBN_KEY_U_AUTO_CALC = "U0001";
    /** 自動計算しない */
    public static final String KBN_VAL_U_AUTO_CALC_NO = "0";
    /** 自動計算する */
    public static final String KBN_VAL_U_AUTO_CALC_YES = "1";
    /** コントロールコード区分 */
    public static final String KBN_KEY_M_CTRL_KK = "M0049";
    /** 仕向先銀行レコード(販社)０１ */
    public static final String KBN_VAL_M_CTRL_KK_BNO01 = "BNO01";
    /** 仕向先銀行レコード(販社)０２ */
    public static final String KBN_VAL_M_CTRL_KK_BNO02 = "BNO02";
    /** 仕向先銀行レコード(販社)０３ */
    public static final String KBN_VAL_M_CTRL_KK_BNO03 = "BNO03";
    /** 仕向先銀行レコード(阪食)０１ */
    public static final String KBN_VAL_M_CTRL_KK_BNK01 = "BNK01";
    /** 仕向先銀行レコード(阪食)０２ */
    public static final String KBN_VAL_M_CTRL_KK_BNK02 = "BNK02";
    /** 仕向先銀行レコード(阪食)０３ */
    public static final String KBN_VAL_M_CTRL_KK_BNK03 = "BNK03";
    /** 仕向先銀行レコード(デリカ)０１ */
    public static final String KBN_VAL_M_CTRL_KK_BNN01 = "BNN01";
    /** 仕向先銀行レコード(デリカ)０２ */
    public static final String KBN_VAL_M_CTRL_KK_BNN02 = "BNN02";
    /** 仕向先銀行レコード(デリカ)０３ */
    public static final String KBN_VAL_M_CTRL_KK_BNN03 = "BNN03";
    /** 振込手数料(同行同支店)０１ */
    public static final String KBN_VAL_M_CTRL_KK_FE101 = "FE101";
    /** 振込手数料(同行同支店)０２ */
    public static final String KBN_VAL_M_CTRL_KK_FE102 = "FE102";
    /** 振込手数料(同行同支店)０３ */
    public static final String KBN_VAL_M_CTRL_KK_FE103 = "FE103";
    /** 振込手数料(同行他店)０１ */
    public static final String KBN_VAL_M_CTRL_KK_FE201 = "FE201";
    /** 振込手数料(同行他店)０２ */
    public static final String KBN_VAL_M_CTRL_KK_FE202 = "FE202";
    /** 振込手数料(同行他店)０３ */
    public static final String KBN_VAL_M_CTRL_KK_FE203 = "FE203";
    /** 振込手数料(他行)０１ */
    public static final String KBN_VAL_M_CTRL_KK_FE301 = "FE301";
    /** 振込手数料(他行)０２ */
    public static final String KBN_VAL_M_CTRL_KK_FE302 = "FE302";
    /** 振込手数料(他行)０３ */
    public static final String KBN_VAL_M_CTRL_KK_FE303 = "FE303";
    /** 買掛管理制御レコード(販社) */
    public static final String KBN_VAL_M_CTRL_KK_KAIOK = "KAIOK";
    /** 買掛管理制御レコード(阪食) */
    public static final String KBN_VAL_M_CTRL_KK_KAIKK = "KAIKK";
    /** 買掛管理制御レコード(デリカ) */
    public static final String KBN_VAL_M_CTRL_KK_KAINK = "KAINK";
    /** 仕入管理共通レコード */
    public static final String KBN_KEY_M_M0050 = "M0050";
    /** 仕入管理貯金センター */
    public static final String KBN_VAL_M_CTRL_SI_SIR00 = "SIR00";
    /** 仕入管理貯金センター */
    public static final String KBN_VAL_M_CTRL_SI_SIR01 = "SIR01";
    /** 総量店舗設定 */
    public static final String KBN_VAL_M_CTRL_SI_SIR02 = "SIR02";
    /** 出力区分 */
    public static final String KBN_KEY_X_REPORT_TYPE = "X1000";
    /** PDF */
    public static final String KBN_VAL_X_REPORT_TYPE_PDF = "1";
    /** CSV */
    public static final String KBN_VAL_X_REPORT_TYPE_CSV = "2";    
    /**店休日区分*/
    public static final String KBN_KEY_M_TENKYU_DAY_KBN = "M0051";
    /**その他*/
    public static final String KBN_VAL_M_TENKYU_DAY_KBN_NO = "0";
    /**店休日*/
    public static final String KBN_VAL_M_TENKYU_DAY_KBN_YES = "9";
    /**天気*/
    public static final String KBN_KEY_U_TENKYU_KBN = "U0002";
    /**晴れ*/
    public static final String KBN_VAL_U_TENKYU_KBN_SUNNY = "01";
    /**曇り*/
    public static final String KBN_VAL_U_TENKYU_KBN_CLOUD = "02";
    /**雨*/
    public static final String KBN_VAL_U_TENKYU_KBN_RAIN = "03";
    /**雪*/
    public static final String KBN_VAL_U_TENKYU_KBN_SNOW = "04";
    /**台風*/
    public static final String KBN_VAL_U_TENKYU_KBN_TYPHOON = "05";
    /**その他*/
    public static final String KBN_VAL_U_TENKYU_KBN_OTHER = "06";
    /**処理区分*/
    public static final String KBN_KEY_Z_SHORI_KBN = "Z0004";
    /**新規登録*/
    public static final String KBN_VAL_Z_SHORI_KBN_NEW = "1";
    /**更新*/
    public static final String KBN_VAL_Z_SHORI_KBN_UPDATE = "2";
    /**削除*/
    public static final String KBN_VAL_Z_SHORI_KBN_DELETE = "9";
    /**廃棄伝票入力用の理由*/
    public static final String KBN_KEY_S_RIYU_KBN = "S0003";
    /**鮮度・期限*/
    public static final String KBN_VAL_S_RIYU_KBN_DEADLINE = "25";
    /**試食・その他*/
    public static final String KBN_VAL_S_RIYU_KBN_TASTING = "27";
    /**奉仕値下*/
    public static final String KBN_VAL_S_RIYU_KBN_SERVICE = "40";
    /**レジ値引き*/
    public static final String KBN_VAL_S_RIYU_KBN_DISCOUNT = "50";
    /**定番売価変更による*/
    public static final String KBN_VAL_S_RIYU_KBN_PRICE_CHANGE = "60";
    /**廃棄伝票入力用の伝票区分*/
    public static final String KBN_KEY_S_HAKI_DPY_KBN = "S0004";
    /**ONLINE(破損)*/
    public static final String KBN_VAL_S_HAKI_DPY_KBN_ONLINE = "04";
    /**手書(破損)*/
    public static final String KBN_VAL_S_HAKI_DPY_KBN_DAMAGE = "09";
    /**手書(破損修正)*/
    public static final String KBN_VAL_S_HAKI_DPY_KBN_DAMAGE_MOD = "10";
    /**店舗区分*/
    public static final String KBN_KEY_M_TENPO_KBN = "M0052";
    /**店舗*/
    public static final String KBN_VAL_M_TENPO_KBN_STORE = "1";
    /**センター*/
    public static final String KBN_VAL_M_TENPO_KBN_CENTER = "3";
    /**総量店舗*/
    public static final String KBN_VAL_M_TENPO_KBN_TOTAL_STORE = "8";
    /**本部*/
    public static final String KBN_VAL_M_TENPO_KBN_HEADQUARTERS = "9";
    /**出力区分*/
    public static final String KBN_KEY_Z_OUTPUT_KBN = "Z0005";
    /**バイヤー用*/
    public static final String KBN_VAL_Z_OUTPUT_KBN_BUYERS = "1";
    /**課長用*/
    public static final String KBN_VAL_Z_OUTPUT_KBN_CHIEF = "2";
    /**倒産用*/
    public static final String KBN_VAL_Z_OUTPUT_KBN_BANKRUPTCY = "3";
    /**発注種類区分*/
    public static final String KBN_KEY_H_HATSUYRUI_KBN = "H0006";
    /**本日配発注*/
    public static final String KBN_VAL_H_HATSUYRUI_KBN_SHOP_GROS = "11";
    /**店グロ発注*/
    public static final String KBN_VAL_H_HATSUYRUI_KBN_DAILY = "12";
    /**店日配発注*/
    public static final String KBN_VAL_H_HATSUYRUI_KBN_TODAY = "13";
    /**本グロ発注*/
    public static final String KBN_VAL_H_HATSUYRUI_KBN_GROS = "16";
    /**用度品発注*/
    public static final String KBN_VAL_H_HATSUYRUI_KBN_PRODUCTS = "90";
    /**  コード区分 */
    public static final String KBN_KEY_N_X007 = "N0057";
    /** 否認 */
    public static final String KBN_VAL_N_HIN_APP_FLG_DENY = "3";
    /** 承認  */
    public static final String KBN_VAL_N_HIN_APP_FLG_APPROVE = "2";
    /** 未確認  */
    public static final String KBN_VAL_N_HIN_APP_FLG_NOT_CONFIRM = "1";

}
