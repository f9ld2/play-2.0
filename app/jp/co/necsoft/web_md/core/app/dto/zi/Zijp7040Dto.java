// /////////////////////////////////////////////////////////////////////
// Copyright(C) 2015 NEC Solution Innovators, Ltd. All Rights Reserved.
// /////////////////////////////////////////////////////////////////////

/*
 * ====================================================================
 * 
 * 機能名称 : 棚卸中分類別合計表 * 
 * 改版履歴
 * 
 * Rev. 改版年月日 改版者名 内容
 * 
 * 1.0 2015.05.07 NECVN 新規作成
 * 
 * ===================================================================
 */

package jp.co.necsoft.web_md.core.app.dto.zi;

/**
 * 棚卸中分類別合計表のDtoクラス
 * 
 */

public class Zijp7040Dto {
    /** 事業部コード */
    private String jigyobuCd;
    /** 事業部名漢字 */
    private String jgyNm;
    /** 店舗コード */
    private String tenCd;
    /** 店舗名漢字 */
    private String tenNm;
    /** 棚卸日 */
    private String tnaUnyDd;
    /** 中分類コード */
    private String chuBnrCd;
    /** 数量 */
    private String suryo;
    /** 原価金額 */
    private String genkKin;
    /** 売価金額 */
    private String baikKin;
    /** 部門コード */
    private String bmnCd;


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
     * get jgyNm
     * @return the jgyNm
     */
    public String getJgyNm() {
        return jgyNm;
    }
    
    /**
     * set jgyNm
     * @param jgyNm the jgyNm to set
     */
    public void setJgyNm(String jgyNm) {
        this.jgyNm = jgyNm;
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
     * get tenNm
     * @return the tenNm
     */
    public String getTenNm() {
        return tenNm;
    }
    
    /**
     * set tenNm
     * @param tenNm the tenNm to set
     */
    public void setTenNm(String tenNm) {
        this.tenNm = tenNm;
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
     * get chuBnrCd
     * @return the chuBnrCd
     */
    public String getChuBnrCd() {
        return chuBnrCd;
    }
    
    /**
     * set chuBnrCd
     * @param chuBnrCd the chuBnrCd to set
     */
    public void setChuBnrCd(String chuBnrCd) {
        this.chuBnrCd = chuBnrCd;
    }

    /**
     * get suryo
     * @return the suryo
     */
    public String getSuryo() {
        return suryo;
    }
    
    /**
     * set suryo
     * @param suryo the suryo to set
     */
    public void setSuryo(String suryo) {
        this.suryo = suryo;
    }

    /**
     * get genkKin
     * @return the genkKin
     */
    public String getGenkKin() {
        return genkKin;
    }
    
    /**
     * set genkKin
     * @param genkKin the genkKin to set
     */
    public void setGenkKin(String genkKin) {
        this.genkKin = genkKin;
    }

    /**
     * get baikKin
     * @return the baikKin
     */
    public String getBaikKin() {
        return baikKin;
    }
    
    /**
     * set baikKin
     * @param baikKin the baikKin to set
     */
    public void setBaikKin(String baikKin) {
        this.baikKin = baikKin;
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
}
