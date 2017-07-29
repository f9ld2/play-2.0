// /////////////////////////////////////////////////////////////////////
// Copyright(C) 2015 NEC Solution Innovators, Ltd. All Rights Reserved.
// /////////////////////////////////////////////////////////////////////

/*
 * ==================================================================== 
 * 機能名称 : セット品設定リスト
 * 改版履歴 
 * Rev. 改版年月日 改版者名 内容 
 * 1.0 2015.06.03 NECVN 新規作成 
 * ===================================================================
 */

package jp.co.necsoft.web_md.core.app.dto.gn;

/**
 * セット品設定リストのParamクラス
 * 
 */
public class Gnjp7000Param {
    
    /**発注日*/
    private String hatYmd;
    
    /** 運用日 */
    private String unyoDate;
    
    /**部門*/
    private String bmnCd;

    /**中分類*/
    private String chubnrCd;
    
    /**小分類*/
    private String shobnrCd;
    
    /**ＪＡＮコード*/
    private String janCd;

    /**事業部*/
    private String jigyobuCd;
    
    /**
     * @return the hatYmd
     */
    public String getHatYmd() {
        return hatYmd;
    }

    /**
     * @param hatYmd the hatYmd to set
     */
    public void setHatYmd(String hatYmd) {
        this.hatYmd = hatYmd;
    }

    /**
     * @return the unyoDate
     */
    public String getUnyoDate() {
        return unyoDate;
    }

    /**
     * @param unyoDate the unyoDate to set
     */
    public void setUnyoDate(String unyoDate) {
        this.unyoDate = unyoDate;
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

    /**
     * @return the chubnrCd
     */
    public String getChubnrCd() {
        return chubnrCd;
    }

    /**
     * @param chubnrCd the chubnrCd to set
     */
    public void setChubnrCd(String chubnrCd) {
        this.chubnrCd = chubnrCd;
    }

    /**
     * @return the shobnrCd
     */
    public String getShobnrCd() {
        return shobnrCd;
    }

    /**
     * @param shobnrCd the shobnrCd to set
     */
    public void setShobnrCd(String shobnrCd) {
        this.shobnrCd = shobnrCd;
    }

    /**
     * @return the janCd
     */
    public String getJanCd() {
        return janCd;
    }

    /**
     * @param janCd the janCd to set
     */
    public void setJanCd(String janCd) {
        this.janCd = janCd;
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
    
}
