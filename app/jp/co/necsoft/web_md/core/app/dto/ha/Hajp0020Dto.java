// /////////////////////////////////////////////////////////////////////
// Copyright(C) 2015 NEC Solution Innovators, Ltd. All Rights Reserved.
// /////////////////////////////////////////////////////////////////////

/*
 * ====================================================================
 * 
 * 機能名称 :Hajp0020Dto
 * 
 * 改版履歴
 * 
 * Rev. 改版年月日 改版者名 内容
 * 
 * 1.0 2014.04.16 TuanTQ 新規作成
 * 
 * ===================================================================
 */
package jp.co.necsoft.web_md.core.app.dto.ha;

import java.math.BigDecimal;

import play.data.validation.Constraints.MaxLength;
import play.data.validation.Constraints.MinLength;
import play.data.validation.Constraints.Required;

import jp.co.necsoft.web_md.core.common.forms.ErrorResponse;

/**
 * 店舗グループマスタメンテのTenDtoクラス
 */
public class Hajp0020Dto {
    private String rowNo; 
    
    /**削*/
    private boolean chkFlg;

    /** 商品[GTIN] */
    private String shnCd;

    /** 商品名称 */
    private String shnNm;

    /** 規格名称 */
    private String kikakuNmA;

    /** メーカー名称*/
    private String mkrNmR;

    /** 便NO */
    private String bin;

    /**発注数 */
    private BigDecimal hatSu;

    /** 便取引先コード */
    private String triCd;

    /** 取引先名称 */
    private String triNmR;

    /** マスタ人数 */
    private BigDecimal mstJinSu;

    /** 入数 */
    private Short hattyuIrisu;

    /** 原単価 */
    private BigDecimal genk;

    /** 売単価 */
    private Integer baik;

    /** u */
    private boolean updateFlag;

    /** 発注締めフラグ */
    private String hatSimeFlg;
    
    /** kikakuCd */
    private String kikakuCd;
    
    /** nendo */
    private String nendo;
    
    /** closeKbn */
    private String closeKbn;
    
    /** bmnCd */
    private String bmnCd;
    
    /** hatDd*/
    @Required
    @MaxLength(value = 8)
    @MinLength(value = 8)
    private String hatDd;
    
    /** teiseiKbn*/
    @Required
    private String teiseiKbn;
    //[2015/06/16 WebMD_SS_V000.001対応 INS START]
    /** 備考*/
    @MaxLength(value = 40)
    private String biko;
    //[2015/06/16 WebMD_SS_V000.001対応 INS END]
    /** 結果情報 */
    private ErrorResponse infoRes;
    
    /**
     * @return the chkFlg
     */
    public boolean isChkFlg() {
        return chkFlg;
    }

    /**
     * @param chkFlg the chkFlg to set
     */
    public void setChkFlg(boolean chkFlg) {
        this.chkFlg = chkFlg;
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
     * @return the kikakuNmA
     */
    public String getKikakuNmA() {
        return kikakuNmA;
    }

    /**
     * @param kikakuNmA the kikakuNmA to set
     */
    public void setKikakuNmA(String kikakuNmA) {
        this.kikakuNmA = kikakuNmA;
    }

    /**
     * @return the mkrNmR
     */
    public String getMkrNmR() {
        return mkrNmR;
    }

    /**
     * @param mkrNmR the mkrNmR to set
     */
    public void setMkrNmR(String mkrNmR) {
        this.mkrNmR = mkrNmR;
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
    public BigDecimal getHatSu() {
        return hatSu;
    }

    /**
     * @param hatSu the hatSu to set
     */
    public void setHatSu(BigDecimal hatSu) {
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
     * @return the triNmR
     */
    public String getTriNmR() {
        return triNmR;
    }

    /**
     * @param triNmR the triNmR to set
     */
    public void setTriNmR(String triNmR) {
        this.triNmR = triNmR;
    }

    /**
     * @return the mstJinSu
     */
    public BigDecimal getMstJinSu() {
        return mstJinSu;
    }

    /**
     * @param mstJinSu the mstJinSu to set
     */
    public void setMstJinSu(BigDecimal mstJinSu) {
        this.mstJinSu = mstJinSu;
    }

    /**
     * @return the hattyuIrisu
     */
    public Short getHattyuIrisu() {
        return hattyuIrisu;
    }

    /**
     * @param hattyuIrisu the hattyuIrisu to set
     */
    public void setHattyuIrisu(Short hattyuIrisu) {
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
     * @return the updateFlag
     */
    public boolean isUpdateFlag() {
        return updateFlag;
    }

    /**
     * @param updateFlag the updateFlag to set
     */
    public void setUpdateFlag(boolean updateFlag) {
        this.updateFlag = updateFlag;
    }

    /**
     * @return the hatSimeFlg
     */
    public String getHatSimeFlg() {
        return hatSimeFlg;
    }

    /**
     * @param hatSimeFlg the hatSimeFlg to set
     */
    public void setHatSimeFlg(String hatSimeFlg) {
        this.hatSimeFlg = hatSimeFlg;
    }

    /**
     * @return the infoRes
     */
    public ErrorResponse getInfoRes() {
        return infoRes;
    }

    /**
     * @param infoRes the infoRes to set
     */
    public void setInfoRes(ErrorResponse infoRes) {
        this.infoRes = infoRes;
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
     * @return the hatDd
     */
    public String getHatDd() {
        return hatDd;
    }

    /**
     * @param hatDd the hatDd to set
     */
    public void setHatDd(String hatDd) {
        this.hatDd = hatDd;
    }

    /**
     * @return the teiseiKbn
     */
    public String getTeiseiKbn() {
        return teiseiKbn;
    }

    /**
     * @param teiseiKbn the teiseiKbn to set
     */
    public void setTeiseiKbn(String teiseiKbn) {
        this.teiseiKbn = teiseiKbn;
    }

    /**
     * @return the kikakuCd
     */
    public String getKikakuCd() {
        return kikakuCd;
    }

    /**
     * @param kikakuCd the kikakuCd to set
     */
    public void setKikakuCd(String kikakuCd) {
        this.kikakuCd = kikakuCd;
    }

    /**
     * @return the nendo
     */
    public String getNendo() {
        return nendo;
    }

    /**
     * @param nendo the nendo to set
     */
    public void setNendo(String nendo) {
        this.nendo = nendo;
    }

    /**
     * @return the closeKbn
     */
    public String getCloseKbn() {
        return closeKbn;
    }

    /**
     * @param closeKbn the closeKbn to set
     */
    public void setCloseKbn(String closeKbn) {
        this.closeKbn = closeKbn;
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
