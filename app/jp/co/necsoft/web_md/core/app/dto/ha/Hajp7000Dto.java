// /////////////////////////////////////////////////////////////////////
// Copyright(C) 2015 NEC Solution Innovators, Ltd. All Rights Reserved.
// /////////////////////////////////////////////////////////////////////

/*
 * ====================================================================
 * 
 * 機能名称 :HHT発注取込エラーリスト
 * 
 * 改版履歴
 * 
 * Rev. 改版年月日 改版者名 内容
 * 
 * 1.0 2015.06.03 NECVN 新規作成
 * 
 * ===================================================================
 */

package jp.co.necsoft.web_md.core.app.dto.ha;

import java.math.BigDecimal;


/**
 * HHT発注取込エラーリストのDtoクラス
 * 
 */

public class Hajp7000Dto {
    /** 事業部 */
    private String jigyobuCd;
    /** 事業部名 */
    private String jgyNm;
    /** 店舗 */
    private String tenCd;
    /** 店舗名 */
    private String tenNm;
    /** 担当名 */
    private String tantoNm;
    /** HT番号 */
    private String termId;
    /** 商品コード */
    private String shnCd;
    /** 数量 */
    private BigDecimal suryo;
    /** 発注日 */
    private String hatDd;
    /** 送信日時 */
    private String sendDd;
    /** 商品名 */
    private String shnNm;
    /** メーカー品番 */
    private String makerHinCd;
    /** 取引先 */
    private String triCd;
    /** 取引先名 */
    private String triNm;
    /** エラー内容 */
    private String errMsg;
    /**
     * get Jigyobu Code
     * @return the jigyobuCd
     */
    public String getJigyobuCd() {
        return jigyobuCd;
    }
    /**
     * set Jigyobu Code
     * @param jigyobuCd the jigyobuCd to set
     */
    public void setJigyobuCd(String jigyobuCd) {
        this.jigyobuCd = jigyobuCd;
    }
    /**
     * get Jigyobu name
     * @return the jgyNm
     */
    public String getJgyNm() {
        return jgyNm;
    }
    /**
     * set Jigyobu name
     * @param jgyNm the jgyNm to set
     */
    public void setJgyNm(String jgyNm) {
        this.jgyNm = jgyNm;
    }
    /**
     * get Ten Cd
     * @return the tenCd
     */
    public String getTenCd() {
        return tenCd;
    }
    /**
     * set Ten Cd
     * @param tenCd the tenCd to set
     */
    public void setTenCd(String tenCd) {
        this.tenCd = tenCd;
    }
    /**
     * get Ten Name
     * @return the tenNm
     */
    public String getTenNm() {
        return tenNm;
    }
    /**
     * set Ten Name
     * @param tenNm the tenNm to set
     */
    public void setTenNm(String tenNm) {
        this.tenNm = tenNm;
    }
    /**
     * get Tanto Name
     * @return the tantoNm
     */
    public String getTantoNm() {
        return tantoNm;
    }
    /**
     * set Tanto Name
     * @param tantoNm the tantoNm to set
     */
    public void setTantoNm(String tantoNm) {
        this.tantoNm = tantoNm;
    }
    /**
     * get Term Id
     * @return the termId
     */
    public String getTermId() {
        return termId;
    }
    /**
     * set Term Id
     * @param termId the termId to set
     */
    public void setTermId(String termId) {
        this.termId = termId;
    }
    /**
     * get 商品コード
     * @return the shnCd
     */
    public String getShnCd() {
        return shnCd;
    }
    /**
     * set 商品コード
     * @param shnCd the shnCd to set
     */
    public void setShnCd(String shnCd) {
        this.shnCd = shnCd;
    }
    /**
     * get Suryo
     * @return the suryo
     */
    public BigDecimal getSuryo() {
        return suryo;
    }
    /**
     * set Suryo
     * @param suryo the suryo to set
     */
    public void setSuryo(BigDecimal suryo) {
        this.suryo = suryo;
    }
    /**
     * get Hatchu Date
     * @return the hatDd
     */
    public String getHatDd() {
        return hatDd;
    }
    /**
     * set Hatchu Date
     * @param hatDd the hatDd to set
     */
    public void setHatDd(String hatDd) {
        this.hatDd = hatDd;
    }
    /**
     * get Send Date
     * @return the sendDd
     */
    public String getSendDd() {
        return sendDd;
    }
    /**
     * set Send Date
     * @param sendDd the sendDd to set
     */
    public void setSendDd(String sendDd) {
        this.sendDd = sendDd;
    }
    /**
     * get 商品名
     * @return the shnNm
     */
    public String getShnNm() {
        return shnNm;
    }
    /**
     * set 商品名
     * @param shnNm the shnNm to set
     */
    public void setShnNm(String shnNm) {
        this.shnNm = shnNm;
    }
    /**
     * get Maker Hin Code
     * @return the makerHinCd
     */
    public String getMakerHinCd() {
        return makerHinCd;
    }
    /**
     * set Maker Hin Code
     * @param makerHinCd the makerHinCd to set
     */
    public void setMakerHinCd(String makerHinCd) {
        this.makerHinCd = makerHinCd;
    }
    /**
     * get Tri Code
     * @return the triCd
     */
    public String getTriCd() {
        return triCd;
    }
    /**
     * set Tri Code
     * @param triCd the triCd to set
     */
    public void setTriCd(String triCd) {
        this.triCd = triCd;
    }
    /**
     * get Tri Name
     * @return the triNm
     */
    public String getTriNm() {
        return triNm;
    }
    /**
     * set Tri Name
     * @param triNm the triNm to set
     */
    public void setTriNm(String triNm) {
        this.triNm = triNm;
    }
    /**
     * get Error Message
     * @return the errMsg
     */
    public String getErrMsg() {
        return errMsg;
    }
    /**
     * set Error Message
     * @param errMsg the errMsg to set
     */
    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }
}
