// /////////////////////////////////////////////////////////////////////
// Copyright(C) 2015 NEC Solution Innovators, Ltd. All Rights Reserved.
// /////////////////////////////////////////////////////////////////////

/*
 * ==================================================================== 機能名称 : CCANConst 改版履歴 Rev. 改版年月日 改版者名 内容 1.0
 * 2014.05.07 Hungtb 新規作成 ===================================================================
 */
package jp.co.necsoft.web_md.core.common.biz.an;

/**
 * 分析管理共通定数定義クラス<BR>
 * 分析管理共通定数を設定します。
 * @author hungtb
 * @version 1.00
 */
public class CCANConst {

    /**
     * 初期表示年月取得用設定日
     */
    public static final String PARA_DAY = "cc.app.an.day";

    /**
    * 全社合計
    * @return "全社合計"
    **/
    public String getAllKaisya() {
        return "全社合計";
    }

    /**
    * 会社合計
    * @return "会社合計"
    **/
    public String getKaisyaTotal() {
        return "会社合計";
    }

    /**
    * 全社計
    * @return "全社計"
    **/
    public String getZensyaTotal() {
        return "全社計";
    }

    /**
    * 事業部合計
    * @return "事業部合計"
    **/
    public String getJigyobuTotal() {
        return "事業部合計";
    }

    /**
    * 販社計
    * @return "販社計"
    **/
    public String getHansyaTotal() {
        return "販社計";
    }

    /**
    * 店舗計
    * @return "店舗計"
    **/
    public String getTenpoTotal() {
        return "店舗計";
    }

    /**
    * 特定部門を除く部門の合計
    * @return "小計"
    **/
    public String getBumonTotal() {
        return "小計";
    }

}
