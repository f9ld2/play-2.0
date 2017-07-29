///////////////////////////////////////////////////////////////////////
// Copyright(C) 2015 NEC Solution Innovators, Ltd. All Rights Reserved.
///////////////////////////////////////////////////////////////////////

/* ====================================================================
 * 機能名称 : 店舗マスタメンテナンス
 * 改版履歴
 * Rev. 改版年月日   改版者名       内容
 * 1.0   2014.02.18   HaiNP      新規作成
 * =================================================================== */
package jp.co.necsoft.web_md.core.common.forms;


/**
 * Common Code Name Condition Form 
 */
public class CodeNameCondForm {

    /** 発効日 */
    private String hakkoDay;
    
    /** 事業部コード */
    private String jigyobuCd;
    
    /** 部門コード */
    private String bmnCd;

    /**
     * @return the hakkoDay
     */
    public String getHakkoDay() {
        return hakkoDay;
    }

    /**
     * @param hakkoDay the hakkoDay to set
     */
    public void setHakkoDay(String hakkoDay) {
        this.hakkoDay = hakkoDay;
    }

    /**
     * @return the jigyobuCd
     */
    public String getJigyobuCd() {
        return jigyobuCd;
    }

    /**
     * @param jigyobuCd the jigyobuCd to set
     */
    public void setJigyobuCd(String jigyobuCd) {
        this.jigyobuCd = jigyobuCd;
    }

    /**
     * @return the bmnCd
     */
    public String getBmnCd() {
        return bmnCd;
    }

    /**
     * @param bmnCd the bmnCd to set
     */
    public void setBmnCd(String bmnCd) {
        this.bmnCd = bmnCd;
    }
    
    
}
