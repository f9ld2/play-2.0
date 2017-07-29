// /////////////////////////////////////////////////////////////////////
// Copyright(C) 2015 NEC Solution Innovators, Ltd. All Rights Reserved.
// /////////////////////////////////////////////////////////////////////

/*
 * ====================================================================
 * 
 * 機能名称 :本部発注入力（店舗）
 * 
 * 改版履歴
 * 
 * Rev. 改版年月日 改版者名 内容
 * 
 * 1.0 2014/03/25 TuanTQ 新規作成
 * 
 * ===================================================================
 */
package jp.co.necsoft.web_md.core.app.forms.ha;

import java.math.BigDecimal;
import java.util.List;

import javax.validation.Valid;

import jp.co.necsoft.web_md.core.app.dto.ha.Hajp0020Dto;
import play.data.validation.Constraints.MaxLength;
import play.data.validation.Constraints.MinLength;
import play.data.validation.Constraints.Required;

/**
*本部発注入力（店舗）のResultFormクラス
*
*/
public class Hajp0020ResultForm {

    /**
     * List<Hajp0010Dto>
     */
    @Valid
    private List<Hajp0020Dto> hachuArea;

    /**発注種類種別*/
    @Required
    @MaxLength(value = 2)
    private String hatSruiKbn;

    /**納品日*/
    @MinLength(value = 8)
    @MaxLength(value = 8)
    private String nhnDd;

    /**会社*/
    @Required
    private String kaisyaCd;

    /**事業部*/
    @Required
    private String jigyobuCd;

    /**店舗*/
    @Required
    private String tenCd;

    /**部門*/
    private String bmnCd;

    /**商品[GTIN]*/
    private String shnCd;

    /***/
    private String shnNm;

    /**規格名称*/
    private String kikakuNm;

    /**メーカー*/
    private String mkrCd;

    /***/
    private String mkrNm;

    /**マスタ入数*/
    private BigDecimal mstIriSu;

    /**便NO*/
    private String bin;

    /**発注数*/
    private String hatSu;

    /**取引先*/
    private String triCd;

    /***/
    private String triNm;

    /**入数*/
    private BigDecimal hattyuIrisu;

    /**原単価*/
    private BigDecimal genk;

    /**売単価*/
    private Integer baik;

    /**No*/
    private String rowNo;

    /**削*/
    private String delFlag;
    //[2015/06/16 WebMD_SS_V000.001対応 INS START]
    /** 備考*/
    @MaxLength(value = 40)
    private String biko;
    //[2015/06/16 WebMD_SS_V000.001対応 INS END]

    /**
     * @return the hachuArea
     */
    public List<Hajp0020Dto> getHachuArea() {
        return hachuArea;
    }

    /**
     * @param hachuArea the hachuArea to set
     */
    public void setHachuArea(List<Hajp0020Dto> hachuArea) {
        this.hachuArea = hachuArea;
    }

    /**
     * @return the hatSruiKbn
     */
    public String getHatSruiKbn() {
        return hatSruiKbn;
    }

    /**
     * @param hatSruiKbn the hatSruiKbn to set
     */
    public void setHatSruiKbn(String hatSruiKbn) {
        this.hatSruiKbn = hatSruiKbn;
    }

    /**
     * @return the nhnDd
     */
    public String getNhnDd() {
        return nhnDd;
    }

    /**
     * @param nhnDd the nhnDd to set
     */
    public void setNhnDd(String nhnDd) {
        this.nhnDd = nhnDd;
    }

    /**
     * @return the kaisyaCd
     */
    public String getKaisyaCd() {
        return kaisyaCd;
    }

    /**
     * @param kaisyaCd the kaisyaCd to set
     */
    public void setKaisyaCd(String kaisyaCd) {
        this.kaisyaCd = kaisyaCd;
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

    /**
     * @return the tenCd
     */
    public String getTenCd() {
        return tenCd;
    }

    /**
     * @param tenCd the tenCd to set
     */
    public void setTenCd(String tenCd) {
        this.tenCd = tenCd;
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
     * @return the shnCd
     */
    public String getShnCd() {
        return shnCd;
    }

    /**
     * @param shnCd the shnCd to set
     */
    public void setShnCd(String shnCd) {
        this.shnCd = shnCd;
    }

    /**
     * @return the shnNm
     */
    public String getShnNm() {
        return shnNm;
    }

    /**
     * @param shnNm the shnNm to set
     */
    public void setShnNm(String shnNm) {
        this.shnNm = shnNm;
    }

    /**
     * @return the kikakuNm
     */
    public String getKikakuNm() {
        return kikakuNm;
    }

    /**
     * @param kikakuNm the kikakuNm to set
     */
    public void setKikakuNm(String kikakuNm) {
        this.kikakuNm = kikakuNm;
    }

    /**
     * @return the mkrCd
     */
    public String getMkrCd() {
        return mkrCd;
    }

    /**
     * @param mkrCd the mkrCd to set
     */
    public void setMkrCd(String mkrCd) {
        this.mkrCd = mkrCd;
    }

    /**
     * @return the mkrNm
     */
    public String getMkrNm() {
        return mkrNm;
    }

    /**
     * @param mkrNm the mkrNm to set
     */
    public void setMkrNm(String mkrNm) {
        this.mkrNm = mkrNm;
    }

    /**
     * @return the mstIriSu
     */
    public BigDecimal getMstIriSu() {
        return mstIriSu;
    }

    /**
     * @param mstIriSu the mstIriSu to set
     */
    public void setMstIriSu(BigDecimal mstIriSu) {
        this.mstIriSu = mstIriSu;
    }

    /**
     * @return the bin
     */
    public String getBin() {
        return bin;
    }

    /**
     * @param bin the bin to set
     */
    public void setBin(String bin) {
        this.bin = bin;
    }

    /**
     * @return the hatSu
     */
    public String getHatSu() {
        return hatSu;
    }

    /**
     * @param hatSu the hatSu to set
     */
    public void setHatSu(String hatSu) {
        this.hatSu = hatSu;
    }

    /**
     * @return the triCd
     */
    public String getTriCd() {
        return triCd;
    }

    /**
     * @param triCd the triCd to set
     */
    public void setTriCd(String triCd) {
        this.triCd = triCd;
    }

    /**
     * @return the triNm
     */
    public String getTriNm() {
        return triNm;
    }

    /**
     * @param triNm the triNm to set
     */
    public void setTriNm(String triNm) {
        this.triNm = triNm;
    }

    /**
     * @return the hattyuIrisu
     */
    public BigDecimal getHattyuIrisu() {
        return hattyuIrisu;
    }

    /**
     * @param hattyuIrisu the hattyuIrisu to set
     */
    public void setHattyuIrisu(BigDecimal hattyuIrisu) {
        this.hattyuIrisu = hattyuIrisu;
    }

    /**
     * @return the genk
     */
    public BigDecimal getGenk() {
        return genk;
    }

    /**
     * @param genk the genk to set
     */
    public void setGenk(BigDecimal genk) {
        this.genk = genk;
    }

    /**
     * @return the baik
     */
    public Integer getBaik() {
        return baik;
    }

    /**
     * @param baik the baik to set
     */
    public void setBaik(Integer baik) {
        this.baik = baik;
    }

    /**
     * @return the rowNo
     */
    public String getRowNo() {
        return rowNo;
    }

    /**
     * @param rowNo the rowNo to set
     */
    public void setRowNo(String rowNo) {
        this.rowNo = rowNo;
    }

    /**
     * @return the delFlag
     */
    public String getDelFlag() {
        return delFlag;
    }

    /**
     * @param delFlag the delFlag to set
     */
    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }
    //[2015/06/16 WebMD_SS_V000.001対応 INS START]
    /**
     * @return the biko
     */
    public String getBiko() {
        return biko;
    }

    /**
     * @param biko the biko to set
     */
    public void setBiko(String biko) {
        this.biko = biko;
    }
    //[2015/06/16 WebMD_SS_V000.001対応 INS END]
}
