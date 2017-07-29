// /////////////////////////////////////////////////////////////////////
// Copyright(C) 2015 NEC Solution Innovators, Ltd. All Rights Reserved.
// /////////////////////////////////////////////////////////////////////

/*
 * ==================================================================== 
 * 機能名称 : 棚卸プルーフ * 改版履歴 
 * Rev. 改版年月日 改版者名 内容 
 * 1.0 2015.06.18 NECVN 新規作成 
 * ===================================================================
 */

package jp.co.necsoft.web_md.core.app.dto.zi;

/**
 * 棚卸プルーフのParamクラス
 * 
 */
public class Zijp7000Param {
    /** 会社 */
    private String kaisyaCd;
    /** 棚卸日 */
    private String tnaUnyDd;
    /** 事業部 */
    private String jigyobuCd;
    /** 店舗 */
    private String tenCd;
    /** 部門 */
    private String bmnCd;
    /** 棚卸担当者 */
    private String tantoCd;
    /** 棚No(FR) */
    private String tanaNoFr;
    /** 棚No(TO) */
    private String tanaNoTo;
    /** 発注日 */
    private String unyoDate;


    /**
     * get kaisyaCd
     * @return the kaisyaCd
     */
    public String getKaisyaCd() {
        return kaisyaCd;
    }
    
    /**
     * set kaisyaCd
     * @param kaisyaCd the kaisyaCd to set
     */
    public void setKaisyaCd(String kaisyaCd) {
        this.kaisyaCd = kaisyaCd;
    }

    /**
     * get tnaUnyDd
     * @return the tnaUnyDd
     */
    public String getTnaUnyDd() {
        return tnaUnyDd;
    }
    
    /**
     * set tnaUnyDd
     * @param tnaUnyDd the tnaUnyDd to set
     */
    public void setTnaUnyDd(String tnaUnyDd) {
        this.tnaUnyDd = tnaUnyDd;
    }

    /**
     * get jigyobuCd
     * @return the jigyobuCd
     */
    public String getJigyobuCd() {
        return jigyobuCd;
    }
    
    /**
     * set jigyobuCd
     * @param jigyobuCd the jigyobuCd to set
     */
    public void setJigyobuCd(String jigyobuCd) {
        this.jigyobuCd = jigyobuCd;
    }

    /**
     * get tenCd
     * @return the tenCd
     */
    public String getTenCd() {
        return tenCd;
    }
    
    /**
     * set tenCd
     * @param tenCd the tenCd to set
     */
    public void setTenCd(String tenCd) {
        this.tenCd = tenCd;
    }

    /**
     * get bmnCd
     * @return the bmnCd
     */
    public String getBmnCd() {
        return bmnCd;
    }
    
    /**
     * set bmnCd
     * @param bmnCd the bmnCd to set
     */
    public void setBmnCd(String bmnCd) {
        this.bmnCd = bmnCd;
    }

    /**
     * get tantoCd
     * @return the tantoCd
     */
    public String getTantoCd() {
        return tantoCd;
    }
    
    /**
     * set tantoCd
     * @param tantoCd the tantoCd to set
     */
    public void setTantoCd(String tantoCd) {
        this.tantoCd = tantoCd;
    }

    /**
     * get tanaNoFr
     * @return the tanaNoFr
     */
    public String getTanaNoFr() {
        return tanaNoFr;
    }
    
    /**
     * set tanaNoFr
     * @param tanaNoFr the tanaNoFr to set
     */
    public void setTanaNoFr(String tanaNoFr) {
        this.tanaNoFr = tanaNoFr;
    }

    /**
     * get tanaNoTo
     * @return the tanaNoTo
     */
    public String getTanaNoTo() {
        return tanaNoTo;
    }
    
    /**
     * set tanaNoTo
     * @param tanaNoTo the tanaNoTo to set
     */
    public void setTanaNoTo(String tanaNoTo) {
        this.tanaNoTo = tanaNoTo;
    }
    


    /**
     * get UnyoDate
     * @return the unyoDate
     */
    public String getUnyoDate() {
        return unyoDate;
    }

    /**
     * set UnyoDate
     * @param unyoDate the unyoDate to set
     */
    public void setUnyoDate(String unyoDate) {
        this.unyoDate = unyoDate;
    }
}
