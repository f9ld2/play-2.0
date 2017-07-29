// /////////////////////////////////////////////////////////////////////
// Copyright(C) 2015 NEC Solution Innovators, Ltd. All Rights Reserved.
// /////////////////////////////////////////////////////////////////////

/*
 * ====================================================================
 * 
 * 機能名称 : 日別店別部門別予算入力
 * 
 * 改版履歴
 * 
 * Rev. 改版年月日 改版者名 内容
 * 
 * 1.0 2014-04-16 TuanTQ新規作成
 * 
 * ===================================================================
 */
package jp.co.necsoft.web_md.core.app.dto.ur;

/**
 * U002ybmnSetPageDataResultのDtoクラス
 *
 */
public class U002ybmnSetPageDataResult {
    /**事業部コード*/
    private String bmnCd;

    /**事業部名漢字*/
    private String bmnNm;
    
    /**売上*/
    private Long ysnUriKin;

    /**荒利*/
    private Long ysnAraKin;

    /**仕入原価*/
    private Long ysnSirGenkKin;

    /**仕入売価*/
    private Long ysnSirBaikKin;

    /**在庫原価*/
    private Long ysnZikGenkKin;

    /**在庫売価*/
    private Long ysnZikBaikKin;

    /**ロス（売変）*/
    private Long ysnBaihenKin;

    /**ロス（廃棄）*/
    private Long ysnHikKin;

    /**ロス（不明）*/
    private Long ysnFumeiKin;

    /**月末原価在庫*/
    private Long ysnLstZikGenk;

    /**月末売価在庫*/
    private Long ysnLstZikBaik;

    /**値入率*/
    private String neireritu;

    /**売上占有率*/
    private String uriSenyuritu;

    /**荒利率*/
    private String arariritu;

    /**値下率*/
    private String nesageritu;

    /**
     * Default Constructor
     */
    public U002ybmnSetPageDataResult() {
        this.bmnCd = "";
        this.bmnNm = "";
        this.ysnUriKin = new Long("0");
        this.ysnAraKin = new Long("0");
        this.ysnSirGenkKin = new Long("0");
        this.ysnSirBaikKin = new Long("0");
        this.ysnZikGenkKin = new Long("0");
        this.ysnZikBaikKin = new Long("0");
        this.ysnBaihenKin = new Long("0");
        this.ysnHikKin = new Long("0");
        this.ysnFumeiKin = new Long("0");
        this.ysnLstZikGenk = new Long("0");
        this.ysnLstZikBaik = new Long("0");
        this.neireritu = "0";
        this.uriSenyuritu = "0";
        this.arariritu = "0";
        this.nesageritu = "0";
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
     * @return the bmnNm
     */
    public String getBmnNm() {
        return bmnNm;
    }

    /**
     * @param bmnNm the bmnNm to set
     */
    public void setBmnNm(String bmnNm) {
        this.bmnNm = bmnNm;
    }

    /**
     * @return the ysnUriKin
     */
    public Long getYsnUriKin() {
        return ysnUriKin;
    }

    /**
     * @param ysnUriKin the ysnUriKin to set
     */
    public void setYsnUriKin(Long ysnUriKin) {
        this.ysnUriKin = ysnUriKin;
    }

    /**
     * @return the ysnAraKin
     */
    public Long getYsnAraKin() {
        return ysnAraKin;
    }

    /**
     * @param ysnAraKin the ysnAraKin to set
     */
    public void setYsnAraKin(Long ysnAraKin) {
        this.ysnAraKin = ysnAraKin;
    }

    /**
     * @return the ysnSirGenkKin
     */
    public Long getYsnSirGenkKin() {
        return ysnSirGenkKin;
    }

    /**
     * @param ysnSirGenkKin the ysnSirGenkKin to set
     */
    public void setYsnSirGenkKin(Long ysnSirGenkKin) {
        this.ysnSirGenkKin = ysnSirGenkKin;
    }

    /**
     * @return the ysnSirBaikKin
     */
    public Long getYsnSirBaikKin() {
        return ysnSirBaikKin;
    }

    /**
     * @param ysnSirBaikKin the ysnSirBaikKin to set
     */
    public void setYsnSirBaikKin(Long ysnSirBaikKin) {
        this.ysnSirBaikKin = ysnSirBaikKin;
    }

    /**
     * @return the ysnZikGenkKin
     */
    public Long getYsnZikGenkKin() {
        return ysnZikGenkKin;
    }

    /**
     * @param ysnZikGenkKin the ysnZikGenkKin to set
     */
    public void setYsnZikGenkKin(Long ysnZikGenkKin) {
        this.ysnZikGenkKin = ysnZikGenkKin;
    }

    /**
     * @return the ysnZikBaikKin
     */
    public Long getYsnZikBaikKin() {
        return ysnZikBaikKin;
    }

    /**
     * @param ysnZikBaikKin the ysnZikBaikKin to set
     */
    public void setYsnZikBaikKin(Long ysnZikBaikKin) {
        this.ysnZikBaikKin = ysnZikBaikKin;
    }

    /**
     * @return the ysnBaihenKin
     */
    public Long getYsnBaihenKin() {
        return ysnBaihenKin;
    }

    /**
     * @param ysnBaihenKin the ysnBaihenKin to set
     */
    public void setYsnBaihenKin(Long ysnBaihenKin) {
        this.ysnBaihenKin = ysnBaihenKin;
    }

    /**
     * @return the ysnHikKin
     */
    public Long getYsnHikKin() {
        return ysnHikKin;
    }

    /**
     * @param ysnHikKin the ysnHikKin to set
     */
    public void setYsnHikKin(Long ysnHikKin) {
        this.ysnHikKin = ysnHikKin;
    }

    /**
     * @return the ysnFumeiKin
     */
    public Long getYsnFumeiKin() {
        return ysnFumeiKin;
    }

    /**
     * @param ysnFumeiKin the ysnFumeiKin to set
     */
    public void setYsnFumeiKin(Long ysnFumeiKin) {
        this.ysnFumeiKin = ysnFumeiKin;
    }

    /**
     * @return the ysnLstZikGenk
     */
    public Long getYsnLstZikGenk() {
        return ysnLstZikGenk;
    }

    /**
     * @param ysnLstZikGenk the ysnLstZikGenk to set
     */
    public void setYsnLstZikGenk(Long ysnLstZikGenk) {
        this.ysnLstZikGenk = ysnLstZikGenk;
    }

    /**
     * @return the ysnLstZikBaik
     */
    public Long getYsnLstZikBaik() {
        return ysnLstZikBaik;
    }

    /**
     * @param ysnLstZikBaik the ysnLstZikBaik to set
     */
    public void setYsnLstZikBaik(Long ysnLstZikBaik) {
        this.ysnLstZikBaik = ysnLstZikBaik;
    }

    /**
     * @return the neireritu
     */
    public String getNeireritu() {
        return neireritu;
    }

    /**
     * @param neireritu the neireritu to set
     */
    public void setNeireritu(String neireritu) {
        this.neireritu = neireritu;
    }

    /**
     * @return the uriSenyuritu
     */
    public String getUriSenyuritu() {
        return uriSenyuritu;
    }

    /**
     * @param uriSenyuritu the uriSenyuritu to set
     */
    public void setUriSenyuritu(String uriSenyuritu) {
        this.uriSenyuritu = uriSenyuritu;
    }

    /**
     * @return the arariritu
     */
    public String getArariritu() {
        return arariritu;
    }

    /**
     * @param arariritu the arariritu to set
     */
    public void setArariritu(String arariritu) {
        this.arariritu = arariritu;
    }

    /**
     * @return the nesageritu
     */
    public String getNesageritu() {
        return nesageritu;
    }

    /**
     * @param nesageritu the nesageritu to set
     */
    public void setNesageritu(String nesageritu) {
        this.nesageritu = nesageritu;
    }

}
