// /////////////////////////////////////////////////////////////////////
// Copyright(C) 2015 NEC Solution Innovators, Ltd. All Rights Reserved.
// /////////////////////////////////////////////////////////////////////

/*
 * ====================================================================
 * 
 * 機能名称 :委託精算確認
 * 
 * 改版履歴
 * 
 * Rev. 改版年月日 改版者名 内容
 * 
 * 1.0 2015-06-04 NECVN 新規作成
 * 
 * ===================================================================
 */

package jp.co.necsoft.web_md.core.app.dto.si;

import java.math.BigDecimal;


/**
 * 委託精算確認のDtoクラス
 * 
 */

public class Sijp7100Dto {
    /**事業部コード*/
    private String jigyobuCd;
    /**事業部名漢字*/
    private String jgyNm;
    /**店舗コード*/
    private String tenCd;
    /**店舗名漢字*/
    private String tenNm;
    /**取引先*/
    private String triCd;
    /**取引先名*/
    private String triNm;
    /**対象年月*/
    private String taisyoYm;
    /**商品[GTIN]*/
    private String janCd;
    /**商品名*/
    private String shnNm;
    /**原単価*/
    private BigDecimal genk;
    /**売単価*/
    private Integer baik;
    /**月初*/
    private BigDecimal geshoZaiko;
    /**仕入*/
    private BigDecimal sirSu;
    /**売上*/
    private BigDecimal uriTensu;
    /**不明*/
    private BigDecimal fumeiSu;
    /**棚卸*/
    private BigDecimal tnaSu;
    /**精算数*/
    private BigDecimal seisanSu;
    /**精算金額*/
    private BigDecimal seisanKin;
    
    /**
     * get triCd
     * @return the triCd
     */
    public String getTriCd() {
        return triCd;
    }
    /**
     * set triCd
     * @param triCd the triCd to set
     */
    public void setTriCd(String triCd) {
        this.triCd = triCd;
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
    public BigDecimal getGenk() {
        return genk;
    }
    /**
     * set genk
     * @param genk the genk to set
     */
    public void setGenk(BigDecimal genk) {
        this.genk = genk;
    }
    /**
     * get baik
     * @return the baik
     */
    public Integer getBaik() {
        return baik;
    }
    /**
     * set baik
     * @param baik the baik to set
     */
    public void setBaik(Integer baik) {
        this.baik = baik;
    }
    /**
     * get geshoZaiko
     * @return the geshoZaiko
     */
    public BigDecimal getGeshoZaiko() {
        return geshoZaiko;
    }
    /**
     * set geshoZaiko
     * @param geshoZaiko the geshoZaiko to set
     */
    public void setGeshoZaiko(BigDecimal geshoZaiko) {
        this.geshoZaiko = geshoZaiko;
    }
    /**
     * get sirSu
     * @return the sirSu
     */
    public BigDecimal getSirSu() {
        return sirSu;
    }
    /**
     * set sirSu
     * @param sirSu the sirSu to set
     */
    public void setSirSu(BigDecimal sirSu) {
        this.sirSu = sirSu;
    }
    /**
     * get uriTensu
     * @return the uriTensu
     */
    public BigDecimal getUriTensu() {
        return uriTensu;
    }
    /**
     * set uriTensu
     * @param uriTensu the uriTensu to set
     */
    public void setUriTensu(BigDecimal uriTensu) {
        this.uriTensu = uriTensu;
    }
    /**
     * get fumeiSu
     * @return the fumeiSu
     */
    public BigDecimal getFumeiSu() {
        return fumeiSu;
    }
    /**
     * set fumeiSu
     * @param fumeiSu the fumeiSu to set
     */
    public void setFumeiSu(BigDecimal fumeiSu) {
        this.fumeiSu = fumeiSu;
    }
    /**
     * get tnaSu
     * @return the tnaSu
     */
    public BigDecimal getTnaSu() {
        return tnaSu;
    }
    /**
     * set tnaSu
     * @param tnaSu the tnaSu to set
     */
    public void setTnaSu(BigDecimal tnaSu) {
        this.tnaSu = tnaSu;
    }
    /**
     * get seisanSu
     * @return the seisanSu
     */
    public BigDecimal getSeisanSu() {
        return seisanSu;
    }
    /**
     * set seisanSu
     * @param seisanSu the seisanSu to set
     */
    public void setSeisanSu(BigDecimal seisanSu) {
        this.seisanSu = seisanSu;
    }
    /**
     * get seisanKin
     * @return the seisanKin
     */
    public BigDecimal getSeisanKin() {
        return seisanKin;
    }
    /**
     * set seisanKin
     * @param seisanKin the seisanKin to set
     */
    public void setSeisanKin(BigDecimal seisanKin) {
        this.seisanKin = seisanKin;
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
     * get taisyoYm
     * @return the taisyoYm
     */
    public String getTaisyoYm() {
        return taisyoYm;
    }
    /**
     * set taisyoYm
     * @param taisyoYm the taisyoYm to set
     */
    public void setTaisyoYm(String taisyoYm) {
        this.taisyoYm = taisyoYm;
    }
    
}
