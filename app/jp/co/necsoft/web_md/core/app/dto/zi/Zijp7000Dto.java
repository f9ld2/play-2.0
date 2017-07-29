///////////////////////////////////////////////////////////////////////
// Copyright(C) 2014 NEC Solution Innovators, Ltd. All Rights Reserved.
///////////////////////////////////////////////////////////////////////

/* ====================================================================
 * 機能名称 :  棚卸実施チェックリスト
 * 改版履歴
 * Rev. 改版年月日   改版者名       内容
 * 1.0   2015.05.07   TrieuVN     新規作成
 * =================================================================== */
package jp.co.necsoft.web_md.core.app.dto.zi;

/**
 * 棚卸実施チェックリストのDtoクラス
 */
public class Zijp7000Dto {
    /** 事業部 */
    private String jigyobuCd;
    
    /** 事業部名漢字 */
    private String jgyNm;
    
    /** 店舗 */
    private String tenCd;
    
    /** 店舗名漢字 */
    private String tenNm;
    
    /** 棚卸日 */
    private String tnaUnyDd;
    
    /** 棚ＮＯ */
    private String tanaNo;
    
    /** 棚卸担当者 */
    private String tantoNm;
    
    /** 商品コード */
    private String shnCd;
    
    /** 商品名 */
    private String shnNm;
    
    /** 数量 */
    private String suryo;
    
    /** 原価 */
    private String genk;
    
    /** 原価金額 */
    private String genkKin;
    
    /** 売価 */
    private String baik;
    
    /** 売価金額 */
    private String baikKin;
    
    /**
     * get JigyobuCd
     * @return the jigyobuCd
     */
    public String getJigyobuCd() {
        return jigyobuCd;
    }
    
    /**
     * set JigyobuCd
     * @param jigyobuCd the jigyobuCd to set
     */
    public void setJigyobuCd(String jigyobuCd) {
        this.jigyobuCd = jigyobuCd;
    }
    
    
    /**
     * get JgyNm
     * @return the jgyNm
     */
    public String getJgyNm() {
        return jgyNm;
    }
    
    /**
     * set JgyNm
     * @param jgyNm the jgyNm to set
     */
    public void setJgyNm(String jgyNm) {
        this.jgyNm = jgyNm;
    }
    
    
    /**
     * get TenCd
     * @return the tenCd
     */
    public String getTenCd() {
        return tenCd;
    }
    
    /**
     * set TenCd
     * @param tenCd the tenCd to set
     */
    public void setTenCd(String tenCd) {
        this.tenCd = tenCd;
    }
    
    
    /**
     * get TenNm
     * @return the tenNm
     */
    public String getTenNm() {
        return tenNm;
    }
    
    /**
     * set TenNm
     * @param tenNm the tenNm to set
     */
    public void setTenNm(String tenNm) {
        this.tenNm = tenNm;
    }
    
    
    /**
     * get TnaUnyDd
     * @return the tnaUnyDd
     */
    public String getTnaUnyDd() {
        return tnaUnyDd;
    }
    
    /**
     * set TnaUnyDd
     * @param tnaUnyDd the tnaUnyDd to set
     */
    public void setTnaUnyDd(String tnaUnyDd) {
        this.tnaUnyDd = tnaUnyDd;
    }
    
    
    /**
     * get TanaNo
     * @return the tanaNo
     */
    public String getTanaNo() {
        return tanaNo;
    }
    
    /**
     * set TanaNo
     * @param tanaNo the tanaNo to set
     */
    public void setTanaNo(String tanaNo) {
        this.tanaNo = tanaNo;
    }
    
    
    /**
     * get TantoNm
     * @return the tantoNm
     */
    public String getTantoNm() {
        return tantoNm;
    }
    
    /**
     * set TantoNm
     * @param tantoNm the tantoNm to set
     */
    public void setTantoNm(String tantoNm) {
        this.tantoNm = tantoNm;
    }
    
    
    /**
     * get ShnCd
     * @return the shnCd
     */
    public String getShnCd() {
        return shnCd;
    }
    
    /**
     * set ShnCd
     * @param shnCd the shnCd to set
     */
    public void setShnCd(String shnCd) {
        this.shnCd = shnCd;
    }
    
    
    /**
     * get ShnNm
     * @return the shnNm
     */
    public String getShnNm() {
        return shnNm;
    }
    
    /**
     * set ShnNm
     * @param shnNm the shnNm to set
     */
    public void setShnNm(String shnNm) {
        this.shnNm = shnNm;
    }
    
    
    /**
     * get Suryo
     * @return the suryo
     */
    public String getSuryo() {
        return suryo;
    }
    
    /**
     * set Suryo
     * @param suryo the suryo to set
     */
    public void setSuryo(String suryo) {
        this.suryo = suryo;
    }
    
    
    /**
     * get Genk
     * @return the genk
     */
    public String getGenk() {
        return genk;
    }
    
    /**
     * set Genk
     * @param genk the genk to set
     */
    public void setGenk(String genk) {
        this.genk = genk;
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
     * get Baik
     * @return the baik
     */
    public String getBaik() {
        return baik;
    }
    
    /**
     * set Baik
     * @param baik the baik to set
     */
    public void setBaik(String baik) {
        this.baik = baik;
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
}
