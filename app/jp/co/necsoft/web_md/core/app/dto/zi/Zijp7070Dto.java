// /////////////////////////////////////////////////////////////////////
// Copyright(C) 2015 NEC Solution Innovators, Ltd. All Rights Reserved.
// /////////////////////////////////////////////////////////////////////

/*
 * ====================================================================
 * 
 * 機能名称 : HHT棚卸データ取込エラーリスト * 
 * 改版履歴
 * 
 * Rev. 改版年月日 改版者名 内容
 * 
 * 1.0 2015.06.19 NECVN 新規作成
 * 
 * ===================================================================
 */

package jp.co.necsoft.web_md.core.app.dto.zi;

/**
 * HHT棚卸データ取込エラーリストのDtoクラス
 * 
 */

public class Zijp7070Dto {
    /** 事業部コード */
    private String jigyobuCd;
    /** 事業部名漢字 */
    private String jgyNm;
    /** 店舗コード */
    private String tenCd;
    /** 店舗名漢字 */
    private String tenNm;
    /** HT番号 */
    private String termId;
    /** 棚卸担当者 */
    private String tantoNm;
    /** 棚ＮＯ */
    private String tanaNo;
    /** 商品コード */
    private String shnCd;
    /** 数量 */
    private String suryo;
    /** 送信日時 */
    private String sosinNichiji;
    /** 商品名 */
    private String shnNm;
    /** 原単価 */
    private String genk;
    /** 売単価 */
    private String baik;
    /** 原価金額 */
    private String genkin;
    /** 売価金額 */
    private String baikin;
    /** エラー内容 */
    private String msg;


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
     * get termId
     * @return the termId
     */
    public String getTermId() {
        return termId;
    }
    
    /**
     * set termId
     * @param termId the termId to set
     */
    public void setTermId(String termId) {
        this.termId = termId;
    }

    /**
     * get tantoNm
     * @return the tantoNm
     */
    public String getTantoNm() {
        return tantoNm;
    }
    
    /**
     * set tantoNm
     * @param tantoNm the tantoNm to set
     */
    public void setTantoNm(String tantoNm) {
        this.tantoNm = tantoNm;
    }

    /**
     * get tanaNo
     * @return the tanaNo
     */
    public String getTanaNo() {
        return tanaNo;
    }
    
    /**
     * set tanaNo
     * @param tanaNo the tanaNo to set
     */
    public void setTanaNo(String tanaNo) {
        this.tanaNo = tanaNo;
    }

    /**
     * get shnCd
     * @return the shnCd
     */
    public String getShnCd() {
        return shnCd;
    }
    
    /**
     * set shnCd
     * @param shnCd the shnCd to set
     */
    public void setShnCd(String shnCd) {
        this.shnCd = shnCd;
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
     * get sosinNichiji
     * @return the sosinNichiji
     */
    public String getSosinNichiji() {
        return sosinNichiji;
    }
    
    /**
     * set sosinNichiji
     * @param sosinNichiji the sosinNichiji to set
     */
    public void setSosinNichiji(String sosinNichiji) {
        this.sosinNichiji = sosinNichiji;
    }

    /**
     * get shnNm
     * @return the shnNm
     */
    public String getShnNm() {
        return shnNm;
    }
    
    /**
     * set shnNm
     * @param shnNm the shnNm to set
     */
    public void setShnNm(String shnNm) {
        this.shnNm = shnNm;
    }

    /**
     * get genk
     * @return the genk
     */
    public String getGenk() {
        return genk;
    }
    
    /**
     * set genk
     * @param genk the genk to set
     */
    public void setGenk(String genk) {
        this.genk = genk;
    }

    /**
     * get baik
     * @return the baik
     */
    public String getBaik() {
        return baik;
    }
    
    /**
     * set baik
     * @param baik the baik to set
     */
    public void setBaik(String baik) {
        this.baik = baik;
    }

    /**
     * get genkin
     * @return the genkin
     */
    public String getGenkin() {
        return genkin;
    }
    
    /**
     * set genkin
     * @param genkin the genkin to set
     */
    public void setGenkin(String genkin) {
        this.genkin = genkin;
    }

    /**
     * get baikin
     * @return the baikin
     */
    public String getBaikin() {
        return baikin;
    }
    
    /**
     * set baikin
     * @param baikin the baikin to set
     */
    public void setBaikin(String baikin) {
        this.baikin = baikin;
    }

    /**
     * get msg
     * @return the msg
     */
    public String getMsg() {
        return msg;
    }
    
    /**
     * set msg
     * @param msg the msg to set
     */
    public void setMsg(String msg) {
        this.msg = msg;
    }
}
