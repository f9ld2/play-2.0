// /////////////////////////////////////////////////////////////////////
// Copyright(C) 2015 NEC Solution Innovators, Ltd. All Rights Reserved.
// /////////////////////////////////////////////////////////////////////
/*
 * ====================================================================
 * 
 * 機能名称 :自動発注予定データ一覧（店舗商品別）
 * 
 * 改版履歴
 * 
 * Rev. 改版年月日 改版者名 内容
 * 
 * 1.0 2015-06-02 trieuvn 新規作成
 * 
 * ===================================================================
 */
package jp.co.necsoft.web_md.core.app.dto.ha;

import java.math.BigDecimal;

import play.data.validation.Constraints.Required;

/**
*自動発注予定データ一覧（店舗商品別）のDtoクラス
*
*/
public class Hajp7030Dto {
    /**取引先*/
    @Required
    private String triCd;

    /**JAN*/
    @Required
    private String shnCd;

    /**商品名称*/
    @Required
    private String shnNm;

    /**原価金額**/
    private BigDecimal genk;

    /**基準在庫*/
    private BigDecimal jidoHatKijunsu;

    /**入数*/
    private BigDecimal hattyuIrisu;

    /**在庫数*/
    @Required
    private BigDecimal rrnSu;

    /**入庫予定数*/
    @Required
    private BigDecimal nyukoYoteiSu;

    /**納品数*/
    private String deliverNumber;

    /**定番１便月曜日発注ＬＴ*/
    private Short hatpMon1;

    /**定番１便火曜日発注ＬＴ*/
    private Short hatpTue1;

    /**定番１便水曜日発注ＬＴ*/
    private Short hatpWed1;

    /**定番１便木曜日発注ＬＴ*/
    private Short hatpThu1;

    /**定番１便金曜日発注ＬＴ*/
    private Short hatpFri1;

    /**定番１便土曜日発注ＬＴ*/
    private Short hatpSat1;

    /**定番１便日曜日発注ＬＴ*/
    private Short hatpSun1;

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
     * @return the jidoHatKijunsu
     */
    public BigDecimal getJidoHatKijunsu() {
        return jidoHatKijunsu;
    }

    /**
     * @param jidoHatKijunsu the jidoHatKijunsu to set
     */
    public void setJidoHatKijunsu(BigDecimal jidoHatKijunsu) {
        this.jidoHatKijunsu = jidoHatKijunsu;
    }

    /**
     * @return the rrnSu
     */
    public BigDecimal getRrnSu() {
        return rrnSu;
    }

    /**
     * @param rrnSu the rrnSu to set
     */
    public void setRrnSu(BigDecimal rrnSu) {
        this.rrnSu = rrnSu;
    }

    /**
     * @return the 入庫予定数
     */
    public BigDecimal getNyukoYoteiSu() {
        return nyukoYoteiSu;
    }

    /**
     * @param nyukoYoteiSu the 入庫予定数 to set
     */
    public void setNyukoYoteiSu(BigDecimal nyukoYoteiSu) {
        this.nyukoYoteiSu = nyukoYoteiSu;
    }

    /**
     * @return the 納品数
     */
    public String getDeliverNumber() {
        return deliverNumber;
    }

    /**
     * @param deliverNumber the 納品数 to set
     */
    public void setDeliverNumber(String deliverNumber) {
        this.deliverNumber = deliverNumber;
    }

    /**
     * @return the hatpMon1
     */
    public Short getHatpMon1() {
        return hatpMon1;
    }

    /**
     * @param hatpMon1 the hatpMon1 to set
     */
    public void setHatpMon1(Short hatpMon1) {
        this.hatpMon1 = hatpMon1;
    }

    /**
     * @return the hatpTue1
     */
    public Short getHatpTue1() {
        return hatpTue1;
    }

    /**
     * @param hatpTue1 the hatpTue1 to set
     */
    public void setHatpTue1(Short hatpTue1) {
        this.hatpTue1 = hatpTue1;
    }

    /**
     * @return the hatpWed1
     */
    public Short getHatpWed1() {
        return hatpWed1;
    }

    /**
     * @param hatpWed1 the hatpWed1 to set
     */
    public void setHatpWed1(Short hatpWed1) {
        this.hatpWed1 = hatpWed1;
    }

    /**
     * @return the hatpThu1
     */
    public Short getHatpThu1() {
        return hatpThu1;
    }

    /**
     * @param hatpThu1 the hatpThu1 to set
     */
    public void setHatpThu1(Short hatpThu1) {
        this.hatpThu1 = hatpThu1;
    }

    /**
     * @return the hatpFri1
     */
    public Short getHatpFri1() {
        return hatpFri1;
    }

    /**
     * @param hatpFri1 the hatpFri1 to set
     */
    public void setHatpFri1(Short hatpFri1) {
        this.hatpFri1 = hatpFri1;
    }

    /**
     * @return the hatpSat1
     */
    public Short getHatpSat1() {
        return hatpSat1;
    }

    /**
     * @param hatpSat1 the hatpSat1 to set
     */
    public void setHatpSat1(Short hatpSat1) {
        this.hatpSat1 = hatpSat1;
    }

    /**
     * @return the hatpSun1
     */
    public Short getHatpSun1() {
        return hatpSun1;
    }

    /**
     * @param hatpSun1 the hatpSun1 to set
     */
    public void setHatpSun1(Short hatpSun1) {
        this.hatpSun1 = hatpSun1;
    }

}
