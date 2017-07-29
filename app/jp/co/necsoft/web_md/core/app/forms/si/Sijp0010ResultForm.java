///////////////////////////////////////////////////////////////////////
// Copyright(C) 2015 NEC Solution Innovators, Ltd. All Rights Reserved.
///////////////////////////////////////////////////////////////////////

/* ====================================================================
 * 機能名称 : ＥＯＳ伝票入力
 * 改版履歴
 * Rev. 改版年月日   改版者名       内容
 * 1.0   2014.04.07   Hungtb      新規作成
 * =================================================================== */
package jp.co.necsoft.web_md.core.app.forms.si;

import java.math.BigDecimal;
import java.util.List;

import jp.co.necsoft.web_md.core.app.dto.si.Sijp0010Eosd;

import play.data.validation.Constraints.MaxLength;
import play.data.validation.Constraints.MinLength;
import play.data.validation.Constraints.Required;

/**
*ＥＯＳ伝票入力のResultFormクラス
*
*/
public class Sijp0010ResultForm {
    /**伝区*/
    @Required
    @MaxLength(value = 2)
    private String dpyKbn;

    /**会社*/
    @Required
    @MinLength(value = 2)
    @MaxLength(value = 2)
    private String kaisyaCd;

    /**事業部*/
    @Required
    @MinLength(value = 2)
    @MaxLength(value = 2)
    private String jigyobuCd;

    /**伝票NO*/
    @Required
    @MinLength(value = 9)
    @MaxLength(value = 9)
    private String dpyNo;

    /**店舗*/
    @Required
    @MinLength(value = 3)
    @MaxLength(value = 3)
    private String tenCd;

    /**部門*/
    @Required
    @MinLength(value = 3)
    @MaxLength(value = 3)
    private String bmnCd;

    /**納品ルート*/
    @Required
    @MaxLength(value = 1)
    private String dctcKbn;

    /**納品日*/
    @Required
    @MinLength(value = 8)
    @MaxLength(value = 8)
    private String nhnYmd;

    /**納品予定日*/
    @Required
    @MinLength(value = 8)
    @MaxLength(value = 8)
    private String nhnYoteiYmd;

    /**発注日*/
    @Required
    @MinLength(value = 8)
    @MaxLength(value = 8)
    private String hatYmd;

    /**取引先*/
    @Required
    @MinLength(value = 9)
    @MaxLength(value = 9)
    private String triCd;

    /** 名称（漢字） */
    private String triNm;

    /**処理状態区分*/
    @Required
    @MaxLength(value = 1)
    private String syoriStsKbn;

    /**処理状態*/
    @Required
    private String syoriStsKbnNm;

    /**伝票明細*/
    private List<Sijp0010Eosd> dataArea;

    /**原価金額合計*/
    @Required
    private Long sumKenGenkKin;

    /**売価金額合計*/
    @Required
    private Long sumKenBaikKin;

    /**(入力)原価金額合計*/
    private Long inpGenInp;

    /* For action */
    /**総量発注区分*/
    private String ikatuFlg;

    /**税区分変数*/
    private String taxKbn;

    /**税率変数*/
    private BigDecimal taxRitu;

    /**合計原価税額変数*/
    private Long sumKenGenkZei;

    /**合計売価税額変数*/
    private Long sumKenBaikZei;

    /**加算区分変数*/
    private String kasanKbn;

    /**センターコード変数*/
    private String centerCd;

    /**売価還元区分*/
    private String baikanKbn;

    /**確定区分*/
    private String entryKbn;

    /** 初回確定日*/
    private String firstKakuteiYmd;

    /** 初回確定担当者コード*/
    private String firstTantoCd;

    /** 初回確定端末ＩＤ*/
    private String firstTermId;

    /**
    * @return 伝区 the dpyKbn
    */
    public String getDpyKbn() {
        return dpyKbn;
    }

    /**
    * @param dpyKbn 伝区
    */
    public void setDpyKbn(String dpyKbn) {
        this.dpyKbn = dpyKbn;
    }

    /**
    * @return 会社 the kaisyaCd
    */
    public String getKaisyaCd() {
        return kaisyaCd;
    }

    /**
    * @param kaisyaCd 会社
    */
    public void setKaisyaCd(String kaisyaCd) {
        this.kaisyaCd = kaisyaCd;
    }

    /**
    * @return 事業部 the jigyobuCd
    */
    public String getJigyobuCd() {
        return jigyobuCd;
    }

    /**
    * @param jigyobuCd 事業部
    */
    public void setJigyobuCd(String jigyobuCd) {
        this.jigyobuCd = jigyobuCd;
    }

    /**
    * @return 伝票NO the dpyNo
    */
    public String getDpyNo() {
        return dpyNo;
    }

    /**
    * @param dpyNo 伝票NO
    */
    public void setDpyNo(String dpyNo) {
        this.dpyNo = dpyNo;
    }

    /**
    * @return 店舗 the tenCd
    */
    public String getTenCd() {
        return tenCd;
    }

    /**
    * @param tenCd 店舗
    */
    public void setTenCd(String tenCd) {
        this.tenCd = tenCd;
    }

    /**
    * @return 部門 the bmnCd
    */
    public String getBmnCd() {
        return bmnCd;
    }

    /**
    * @param bmnCd 部門
    */
    public void setBmnCd(String bmnCd) {
        this.bmnCd = bmnCd;
    }

    /**
    * @return 納品ルート the dctcKbn
    */
    public String getDctcKbn() {
        return dctcKbn;
    }

    /**
    * @param dctcKbn 納品ルート
    */
    public void setDctcKbn(String dctcKbn) {
        this.dctcKbn = dctcKbn;
    }

    /**
    * @return 納品日 the nhnYmd
    */
    public String getNhnYmd() {
        return nhnYmd;
    }

    /**
    * @param nhnYmd 納品日
    */
    public void setNhnYmd(String nhnYmd) {
        this.nhnYmd = nhnYmd;
    }

    /**
    * @return 納品予定日 the nhnYoteiYmd
    */
    public String getNhnYoteiYmd() {
        return nhnYoteiYmd;
    }

    /**
    * @param nhnYoteiYmd 納品予定日
    */
    public void setNhnYoteiYmd(String nhnYoteiYmd) {
        this.nhnYoteiYmd = nhnYoteiYmd;
    }

    /**
    * @return 発注日 the hatYmd
    */
    public String getHatYmd() {
        return hatYmd;
    }

    /**
    * @param hatYmd 発注日
    */
    public void setHatYmd(String hatYmd) {
        this.hatYmd = hatYmd;
    }

    /**
    * @return 取引先 the triCd
    */
    public String getTriCd() {
        return triCd;
    }

    /**
    * @param triCd 取引先
    */
    public void setTriCd(String triCd) {
        this.triCd = triCd;
    }

    /**
    * @return 名称（漢字） the triNm
    */
    public String getTriNm() {
        return triNm;
    }

    /**
    * @param triNm 名称（漢字）
    */
    public void setTriNm(String triNm) {
        this.triNm = triNm;
    }

    /**
    * @return 処理状態区分 the SyoriStsKbn
    */
    public String getSyoriStsKbn() {
        return syoriStsKbn;
    }

    /**
    * @param syoriStsKbn 処理状態区分
    */
    public void setSyoriStsKbn(String syoriStsKbn) {
        this.syoriStsKbn = syoriStsKbn;
    }

    /**
    * @return 処理状態 the syoriStsKbnNm
    */
    public String getSyoriStsKbnNm() {
        return syoriStsKbnNm;
    }

    /**
    * @param syoriStsKbnNm 処理状態
    */
    public void setSyoriStsKbnNm(String syoriStsKbnNm) {
        this.syoriStsKbnNm = syoriStsKbnNm;
    }

    /**
    * @return 伝票明細 the denpyouInfArea
    */
    public List<Sijp0010Eosd> getDataArea() {
        return dataArea;
    }

    /**
    * @param denpyouInfArea 伝票明細
    */
    public void setDataArea(List<Sijp0010Eosd> denpyouInfArea) {
        this.dataArea = denpyouInfArea;
    }

    /**
    * @return 原価金額合計 the sumKenGenkKin
    */
    public Long getSumKenGenkKin() {
        return sumKenGenkKin;
    }

    /**
    * @param sumKenGenkKin 原価金額合計
    */
    public void setSumKenGenkKin(Long sumKenGenkKin) {
        this.sumKenGenkKin = sumKenGenkKin;
    }

    /**
    * @return 売価金額合計 the sumKenBaikKin
    */
    public Long getSumKenBaikKin() {
        return sumKenBaikKin;
    }

    /**
    * @param sumKenBaikKin 売価金額合計
    */
    public void setSumKenBaikKin(Long sumKenBaikKin) {
        this.sumKenBaikKin = sumKenBaikKin;
    }

    /**
    * @return (入力)原価金額合計
    */
    public Long getInpGenInp() {
        return inpGenInp;
    }

    /**
    * @param inpGenInp (入力)原価金額合計
    */
    public void setInpGenInp(Long inpGenInp) {
        this.inpGenInp = inpGenInp;
    }

    /**
     * @return 総量発注区分 the ikatuFlg
     */
    public String getIkatuFlg() {
        return ikatuFlg;
    }

    /**
     * @param ikatuFlg 総量発注区分
     */
    public void setIkatuFlg(String ikatuFlg) {
        this.ikatuFlg = ikatuFlg;
    }

    /**
     * @return 税区分変数 the taxKbn
     */
    public String getTaxKbn() {
        return taxKbn;
    }

    /**
     * @param taxKbn 税区分変数
     */
    public void setTaxKbn(String taxKbn) {
        this.taxKbn = taxKbn;
    }

    /**
     * @return 税率変数 the taxRitu
     */
    public BigDecimal getTaxRitu() {
        return taxRitu;
    }

    /**
     * @param taxRitu 税率変数
     */
    public void setTaxRitu(BigDecimal taxRitu) {
        this.taxRitu = taxRitu;
    }

    /**
     * @return 合計原価税額変数 the sumKenGenkZei
     */
    public Long getSumKenGenkZei() {
        return sumKenGenkZei;
    }

    /**
     * @param sumKenGenkZei 合計原価税額変数
     */
    public void setSumKenGenkZei(Long sumKenGenkZei) {
        this.sumKenGenkZei = sumKenGenkZei;
    }

    /**
     * @return 合計売価税額変数 the sumKenBaikZei
     */
    public Long getSumKenBaikZei() {
        return sumKenBaikZei;
    }

    /**
     * @param sumKenBaikZei 合計売価税額変数
     */
    public void setSumKenBaikZei(Long sumKenBaikZei) {
        this.sumKenBaikZei = sumKenBaikZei;
    }

    /**
     * @return 加算区分変数 the kasanKbn
     */
    public String getKasanKbn() {
        return kasanKbn;
    }

    /**
     * @param kasanKbn 加算区分変数
     */
    public void setKasanKbn(String kasanKbn) {
        this.kasanKbn = kasanKbn;
    }

    /**
     * @return センターコード変数 the centerCd
     */
    public String getCenterCd() {
        return centerCd;
    }

    /**
     * @param centerCd センターコード変数
     */
    public void setCenterCd(String centerCd) {
        this.centerCd = centerCd;
    }

    /**
     * @return 売価還元区分 the baikanKbn
     */
    public String getBaikanKbn() {
        return baikanKbn;
    }

    /**
     * @param baikanKbn 売価還元区分
     */
    public void setBaikanKbn(String baikanKbn) {
        this.baikanKbn = baikanKbn;
    }

    /**
     * @return 確定区分 the entryKbn
     */
    public String getEntryKbn() {
        return entryKbn;
    }

    /**
     * @param entryKbn 確定区分
     */
    public void setEntryKbn(String entryKbn) {
        this.entryKbn = entryKbn;
    }

    /**
     * @return 初回確定日 the firstKakuteiYmd
     */
    public String getFirstKakuteiYmd() {
        return firstKakuteiYmd;
    }

    /**
     * @param firstKakuteiYmd 初回確定日
     */
    public void setFirstKakuteiYmd(String firstKakuteiYmd) {
        this.firstKakuteiYmd = firstKakuteiYmd;
    }

    /**
     * @return 初回確定担当者コード the firstTantoCd
     */
    public String getFirstTantoCd() {
        return firstTantoCd;
    }

    /**
     * @param firstTantoCd 初回確定担当者コード
     */
    public void setFirstTantoCd(String firstTantoCd) {
        this.firstTantoCd = firstTantoCd;
    }

    /**
     * @return 初回確定端末ＩＤ the firstTermId
     */
    public String getFirstTermId() {
        return firstTermId;
    }

    /**
     * @param firstTermId 初回確定端末ＩＤ
     */
    public void setFirstTermId(String firstTermId) {
        this.firstTermId = firstTermId;
    }

}
