// /////////////////////////////////////////////////////////////////////
// Copyright(C) 2015 NEC Solution Innovators, Ltd. All Rights Reserved.
// /////////////////////////////////////////////////////////////////////

/* ====================================================================
 * 機能名称 : ＥＯＳ伝票入力
 * 改版履歴
 * Rev. 改版年月日   改版者名       内容
 * 1.0   2014.04.05   Hungtb      新規作成
 * =================================================================== */
package jp.co.necsoft.web_md.core.app.dto.si;

import java.math.BigDecimal;

import play.data.validation.Constraints.MaxLength;
import play.data.validation.Constraints.MinLength;

/**
 * ＥＯＳ伝票入力のDtoクラス
 */
public class Sijp0010Eosd {
    /**明細連番*/
    private String mesaiNo;

    /**商品[GTIN]*/
    @MinLength(value = 6)
    @MaxLength(value = 13)
    private String shnCd;

    /**商品名称*/
    @MaxLength(value = 40)
    private String shnNm;

    /**発注単位*/
    private String tani;

    /**発注数量*/
    private BigDecimal hatBaraSu;

    /**納品数量*/
    private BigDecimal kenBaraSu;

    /**原単価*/
    private BigDecimal hatGenk;

    /**原価金額*/
    private Long hatGenkKin;

    /**売単価*/
    private Integer hatBaik;

    /**売価金額*/
    private Long hatBaikKin;

    // Base on old code
    /**原価税額*/
    private Integer kenGenkZei;

    /**売価税額*/
    private Integer kenBaikZei;

    /** kppinRiyuKbn */
    private String kppinRiyuKbn;

    /**Base on outShnCd: false if empty; true if not. */
    private boolean displayFlag;

    /**
     * @return the mesaiNo
     */
    public String getMesaiNo() {
        return mesaiNo;
    }

    /**
     * @param mesaiNo the mesaiNo to set
     */
    public void setMesaiNo(String mesaiNo) {
        this.mesaiNo = mesaiNo;
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
     * @return the tani
     */
    public String getTani() {
        return tani;
    }

    /**
     * @param tani the tani to set
     */
    public void setTani(String tani) {
        this.tani = tani;
    }

    /**
     * @return the hatBaraSu
     */
    public BigDecimal getHatBaraSu() {
        return hatBaraSu;
    }

    /**
     * @param hatBaraSu the hatBaraSu to set
     */
    public void setHatBaraSu(BigDecimal hatBaraSu) {
        this.hatBaraSu = hatBaraSu;
    }

    /**
     * @return the kenBaraSu
     */
    public BigDecimal getKenBaraSu() {
        return kenBaraSu;
    }

    /**
     * @param kenBaraSu the kenBaraSu to set
     */
    public void setKenBaraSu(BigDecimal kenBaraSu) {
        this.kenBaraSu = kenBaraSu;
    }

    /**
     * @return the hatGenk
     */
    public BigDecimal getHatGenk() {
        return hatGenk;
    }

    /**
     * @param hatGenk the hatGenk to set
     */
    public void setHatGenk(BigDecimal hatGenk) {
        this.hatGenk = hatGenk;
    }

    /**
     * @return the hatGenkKin
     */
    public Long getHatGenkKin() {
        return hatGenkKin;
    }

    /**
     * @param hatGenkKin the hatGenkKin to set
     */
    public void setHatGenkKin(Long hatGenkKin) {
        this.hatGenkKin = hatGenkKin;
    }

    /**
     * @return the hatBaik
     */
    public Integer getHatBaik() {
        return hatBaik;
    }

    /**
     * @param hatBaik the hatBaik to set
     */
    public void setHatBaik(Integer hatBaik) {
        this.hatBaik = hatBaik;
    }

    /**
     * @return the hatBaikKin
     */
    public Long getHatBaikKin() {
        return hatBaikKin;
    }

    /**
     * @param hatBaikKin the hatBaikKin to set
     */
    public void setHatBaikKin(Long hatBaikKin) {
        this.hatBaikKin = hatBaikKin;
    }

    /**
     * @return the kenGenkZei
     */
    public Integer getKenGenkZei() {
        return kenGenkZei;
    }

    /**
     * @param kenGenkZei the kenGenkZei to set
     */
    public void setKenGenkZei(Integer kenGenkZei) {
        this.kenGenkZei = kenGenkZei;
    }

    /**
     * @return the kenBaikZei
     */
    public Integer getKenBaikZei() {
        return kenBaikZei;
    }

    /**
     * @param kenBaikZei the kenBaikZei to set
     */
    public void setKenBaikZei(Integer kenBaikZei) {
        this.kenBaikZei = kenBaikZei;
    }

    /**
     * @return the kppinRiyuKbn
     */
    public String getKppinRiyuKbn() {
        return kppinRiyuKbn;
    }

    /**
     * @param kppinRiyuKbn the kppinRiyuKbn to set
     */
    public void setKppinRiyuKbn(String kppinRiyuKbn) {
        this.kppinRiyuKbn = kppinRiyuKbn;
    }

    /**
     * @return the displayFlag
     */
    public boolean isDisplayFlag() {
        return displayFlag;
    }

    /**
     * @param displayFlag the displayFlag to set
     */
    public void setDisplayFlag(boolean displayFlag) {
        this.displayFlag = displayFlag;
    }
}
