// /////////////////////////////////////////////////////////////////////
// Copyright(C) 2015 NEC Solution Innovators, Ltd. All Rights Reserved.
// /////////////////////////////////////////////////////////////////////

/*
 * ====================================================================
 * 
 * 機能名称 :入荷状況一覧表
 * 
 * 改版履歴
 * 
 * Rev. 改版年月日 改版者名 内容
 * 
 * 1.0 2015.06.08 NECVN 新規作成
 * 
 * ===================================================================
 */

package jp.co.necsoft.web_md.core.app.dto.si;

/**
 * 入荷状況一覧表のDtoクラス
 * 
 */

public class Sijp7020Dto {
    /** 事業部*/
    private String jiyoubuCd;
    /** 事業名*/
    private String jiyoubuNm;
    /** 店舗*/
    private String tenCd;
    /** 店舗名*/
    private String tenNm;
    /** 納品日*/
    private String nhnYmd;
    /** 対象*/
    private String taisho;
    /** 取引先／出荷店*/
    private String torihikiCd;
    /** 取引先名*/
    private String torihikiNm;
    /** 伝票№*/
    private String dpyNo;
    /** 行№*/
    private String lineNo;
    /** ＪＡＮコード*/
    private String janCd;
    /** 中分類*/
    private String cbnrCd;
    /** 小分類*/
    private String sbnrCd;
    /** 自社品番*/
    private String jishaHinban;
    /** 商品名称*/
    private String shnNm;
    /** メーカー品番*/
    private String mkrCd;
    /** サイズ*/
    private String sizeCd;
    /** 色*/
    private String colorCd;
    /** 数量*/
    private String suryo;
    /** 売価金額*/
    private String baikKin;

    /**
     * get jiyoubuCd
     * @return the jiyoubuCd
     */
    public String getJiyoubuCd() {
        return jiyoubuCd;
    }
    /**
     * set jiyoubuCd
     * @param jiyoubuCd the jiyoubuCd to set
     */
    public void setJiyoubuCd(String jiyoubuCd) {
        this.jiyoubuCd = jiyoubuCd;
    }
    /**
     * get jiyoubuNm
     * @return the jiyoubuNm
     */
    public String getJiyoubuNm() {
        return jiyoubuNm;
    }
    /**
     * set jiyoubuNm
     * @param jiyoubuNm the jiyoubuNm to set
     */
    public void setJiyoubuNm(String jiyoubuNm) {
        this.jiyoubuNm = jiyoubuNm;
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
     * get torihikiNm
     * @return the torihikiNm
     */
    public String getTorihikiNm() {
        return torihikiNm;
    }
    /**
     * set torihikiNm
     * @param torihikiNm the torihikiNm to set
     */
    public void setTorihikiNm(String torihikiNm) {
        this.torihikiNm = torihikiNm;
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
     * get lineNo
     * @return the lineNo
     */
    public String getLineNo() {
        return lineNo;
    }
    /**
     * set lineNo
     * @param lineNo the lineNo to set
     */
    public void setLineNo(String lineNo) {
        this.lineNo = lineNo;
    }
    /**
     * get janCd
     * @return the janCd
     */
    public String getJanCd() {
        return janCd;
    }
    /**
     * set janCd
     * @param janCd the janCd to set
     */
    public void setJanCd(String janCd) {
        this.janCd = janCd;
    }
    /**
     * get cbnrCd
     * @return the cbnrCd
     */
    public String getCbnrCd() {
        return cbnrCd;
    }
    /**
     * set cbnrCd
     * @param cbnrCd the cbnrCd to set
     */
    public void setCbnrCd(String cbnrCd) {
        this.cbnrCd = cbnrCd;
    }
    /**
     * get sbnrCd
     * @return the sbnrCd
     */
    public String getSbnrCd() {
        return sbnrCd;
    }
    /**
     * set sbnrCd
     * @param sbnrCd the sbnrCd to set
     */
    public void setSbnrCd(String sbnrCd) {
        this.sbnrCd = sbnrCd;
    }
    /**
     * get jishaHinban
     * @return the jishaHinban
     */
    public String getJishaHinban() {
        return jishaHinban;
    }
    /**
     * set jishaHinban
     * @param jishaHinban the jishaHinban to set
     */
    public void setJishaHinban(String jishaHinban) {
        this.jishaHinban = jishaHinban;
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
     * get sizeCd
     * @return the sizeCd
     */
    public String getSizeCd() {
        return sizeCd;
    }
    /**
     * set sizeCd
     * @param sizeCd the sizeCd to set
     */
    public void setSizeCd(String sizeCd) {
        this.sizeCd = sizeCd;
    }
    /**
     * get colorCd
     * @return the colorCd
     */
    public String getColorCd() {
        return colorCd;
    }
    /**
     * set colorCd
     * @param colorCd the colorCd to set
     */
    public void setColorCd(String colorCd) {
        this.colorCd = colorCd;
    }
    /**
     * get taisho
     * @return the taisho
     */
    public String getTaisho() {
        return taisho;
    }
    /**
     * set taisho
     * @param taisho the taisho to set
     */
    public void setTaisho(String taisho) {
        this.taisho = taisho;
    }

}
