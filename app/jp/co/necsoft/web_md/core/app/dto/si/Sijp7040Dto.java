// /////////////////////////////////////////////////////////////////////
// Copyright(C) 2015 NEC Solution Innovators, Ltd. All Rights Reserved.
// /////////////////////////////////////////////////////////////////////

/*
 * ====================================================================
 * 
 * 機能名称 : HHT検品取込エラーリスト * 
 * 改版履歴
 * 
 * Rev. 改版年月日 改版者名 内容
 * 
 * 1.0 2015.06.15 NECVN 新規作成
 * 
 * ===================================================================
 */

package jp.co.necsoft.web_md.core.app.dto.si;

/**
 * HHT検品取込エラーリストのDtoクラス
 * 
 */

public class Sijp7040Dto {
    /** 事業部 */
    private String jigyobuCd;
    /** 事業部名漢字 */
    private String jgyNm;
    /** 店舗 */
    private String tenCd;
    /** 店舗名漢字 */
    private String tenNm;
    /** HT番号 */
    private String termId;
    /** 納品日 */
    private String nhnYmd;
    /** 担当者コード */
    private String tantoCd;
    /** 伝票番号 */
    private String dpyNo;
    /** 商品コード */
    private String shnCd;
    /** 検品数量 */
    private String suryo;
    /** 送信日時 */
    private String sosinNichiji;
    /** メーカー品番 */
    private String mkrCd;
    /** 商品名 */
    private String shnNm;
    /** 取引先 */
    private String torihikiCd;
    /** 取引先名漢字 */
    private String triNm;
    /** 納品予定数量 */
    private String hatCaseSu01;
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
     * get JgyNm
     * @return the JgyNm
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
     * get nhnYmd
     * @return the nhnYmd
     */
    public String getNhnYmd() {
        return nhnYmd;
    }
    
    /**
     * set nhnYmd
     * @param nhnYmd the nhnYmd to set
     */
    public void setNhnYmd(String nhnYmd) {
        this.nhnYmd = nhnYmd;
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
     * get dpyNo
     * @return the dpyNo
     */
    public String getDpyNo() {
        return dpyNo;
    }
    
    /**
     * set dpyNo
     * @param dpyNo the dpyNo to set
     */
    public void setDpyNo(String dpyNo) {
        this.dpyNo = dpyNo;
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
     * get mkrCd
     * @return the mkrCd
     */
    public String getMkrCd() {
        return mkrCd;
    }
    
    /**
     * set mkrCd
     * @param mkrCd the mkrCd to set
     */
    public void setMkrCd(String mkrCd) {
        this.mkrCd = mkrCd;
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
     * get torihikiCd
     * @return the torihikiCd
     */
    public String getTorihikiCd() {
        return torihikiCd;
    }
    
    /**
     * set torihikiCd
     * @param torihikiCd the torihikiCd to set
     */
    public void setTorihikiCd(String torihikiCd) {
        this.torihikiCd = torihikiCd;
    }

    /**
     * get triNm
     * @return the triNm
     */
    public String getTriNm() {
        return triNm;
    }
    
    /**
     * set triNm
     * @param triNm the triNm to set
     */
    public void setTriNm(String triNm) {
        this.triNm = triNm;
    }

    /**
     * get hatCaseSu01
     * @return the hatCaseSu01
     */
    public String getHatCaseSu01() {
        return hatCaseSu01;
    }
    
    /**
     * set hatCaseSu01
     * @param hatCaseSu01 the hatCaseSu01 to set
     */
    public void setHatCaseSu01(String hatCaseSu01) {
        this.hatCaseSu01 = hatCaseSu01;
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
