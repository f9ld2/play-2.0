// /////////////////////////////////////////////////////////////////////
// Copyright(C) 2015 NEC Solution Innovators, Ltd. All Rights Reserved.
// /////////////////////////////////////////////////////////////////////

/*
 * ====================================================================
 * 
 * 機能名称 : 手書伝票(仕入プラス)入力
 * 
 * 改版履歴
 * 
 * Rev. 改版年月日 改版者名 内容
 * 
 * 1.0 20140328 tuctv 新規作成
 * 
 * ===================================================================
 */
package jp.co.necsoft.web_md.core.app.forms.si;

import play.data.validation.Constraints.MaxLength;
import play.data.validation.Constraints.MinLength;

/**
 * Sijp0130CondForm
 * @author tuctvz
 * @since 2014/03/28
 */
public class Sijp0130CondForm {

    /**伝票区分**/
    @MaxLength(value = 2)
    private String dpyKbn;
    /**事業部コード**/
    private String kaisyaCd;
    /**店舗コード**/
    private String jigyobuCd;
    /**伝票番号**/
    private String tenCd;

    /**会社コード**/
    private String outKaisyaCd;
    /**事業部コード**/
    private String outJigyobuCd;
    /**店舗コード**/
    private String outTenCd;

    /**部門コード**/
    @MinLength(value = 9)
    @MaxLength(value = 9)
    private String dpyNo;
    /**取引先コード**/
    private String bmnCd;
    /**名称（漢字）**/
    private String triCd;
    /**納品日開始**/
    private String triNm;
    /**納品日終了**/
    private String nhmYmdSt;
    /**発注日開始**/
    private String nhmYmdEd;
    /**発注日終了**/
    private String hatYmdSt;
    /**納品予定日開始**/
    private String hatYmdEd;
    /**納品予定日終了**/
    private String nhnYoteiYmdSt;
    /**累積計上日開始**/
    private String nhnYoteiYmdEd;
    /**累積計上日終了**/
    private String ruiKejoYmdSt;
    /**検索用処理状態区分**/
    private String ruiKejoYmdEd;
    /**検索用処理状態区分**/
    @MaxLength(value = 1)
    private String syoriSts;
    /**確定区分**/
    @MaxLength(value = 2)
    private String entryKbn;
    /**確定場所**/
    @MinLength(value = 7)
    @MaxLength(value = 7)
    private String entryPlace;
    /**確定日開始**/
    private String kakuteiYmdSt;
    /**確定日終了**/
    private String kakuteiYmdEd;
    /**担当者コード**/
    private String tantoCd;
    /**担当者名称（漢字）**/
    private String tantoNm;

    /**
     * @return the dpyKbn
     */
    public String getDpyKbn() {
        return dpyKbn;
    }

    /**
     * @param dpyKbn the dpyKbn to set
     */
    public void setDpyKbn(String dpyKbn) {
        this.dpyKbn = dpyKbn;
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
     * @return the dpyNo
     */
    public String getDpyNo() {
        return dpyNo;
    }

    /**
     * @param dpyNo the dpyNo to set
     */
    public void setDpyNo(String dpyNo) {
        this.dpyNo = dpyNo;
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
     * @return the nhmYmdSt
     */
    public String getNhmYmdSt() {
        return nhmYmdSt;
    }

    /**
     * @param nhmYmdSt the nhmYmdSt to set
     */
    public void setNhmYmdSt(String nhmYmdSt) {
        this.nhmYmdSt = nhmYmdSt;
    }

    /**
     * @return the nhmYmdEd
     */
    public String getNhmYmdEd() {
        return nhmYmdEd;
    }

    /**
     * @param nhmYmdEd the nhmYmdEd to set
     */
    public void setNhmYmdEd(String nhmYmdEd) {
        this.nhmYmdEd = nhmYmdEd;
    }

    /**
     * @return the hatYmdSt
     */
    public String getHatYmdSt() {
        return hatYmdSt;
    }

    /**
     * @param hatYmdSt the hatYmdSt to set
     */
    public void setHatYmdSt(String hatYmdSt) {
        this.hatYmdSt = hatYmdSt;
    }

    /**
     * @return the hatYmdEd
     */
    public String getHatYmdEd() {
        return hatYmdEd;
    }

    /**
     * @param hatYmdEd the hatYmdEd to set
     */
    public void setHatYmdEd(String hatYmdEd) {
        this.hatYmdEd = hatYmdEd;
    }

    /**
     * @return the nhnYoteiYmdSt
     */
    public String getNhnYoteiYmdSt() {
        return nhnYoteiYmdSt;
    }

    /**
     * @param nhnYoteiYmdSt the nhnYoteiYmdSt to set
     */
    public void setNhnYoteiYmdSt(String nhnYoteiYmdSt) {
        this.nhnYoteiYmdSt = nhnYoteiYmdSt;
    }

    /**
     * @return the nhnYoteiYmdEd
     */
    public String getNhnYoteiYmdEd() {
        return nhnYoteiYmdEd;
    }

    /**
     * @param nhnYoteiYmdEd the nhnYoteiYmdEd to set
     */
    public void setNhnYoteiYmdEd(String nhnYoteiYmdEd) {
        this.nhnYoteiYmdEd = nhnYoteiYmdEd;
    }

    /**
     * @return the ruiKejoYmdSt
     */
    public String getRuiKejoYmdSt() {
        return ruiKejoYmdSt;
    }

    /**
     * @param ruiKejoYmdSt the ruiKejoYmdSt to set
     */
    public void setRuiKejoYmdSt(String ruiKejoYmdSt) {
        this.ruiKejoYmdSt = ruiKejoYmdSt;
    }

    /**
     * @return the ruiKejoYmdEd
     */
    public String getRuiKejoYmdEd() {
        return ruiKejoYmdEd;
    }

    /**
     * @param ruiKejoYmdEd the ruiKejoYmdEd to set
     */
    public void setRuiKejoYmdEd(String ruiKejoYmdEd) {
        this.ruiKejoYmdEd = ruiKejoYmdEd;
    }

    /**
     * @return the syoriSts
     */
    public String getSyoriSts() {
        return syoriSts;
    }

    /**
     * @param syoriSts the syoriSts to set
     */
    public void setSyoriSts(String syoriSts) {
        this.syoriSts = syoriSts;
    }

    /**
     * @return the entryKbn
     */
    public String getEntryKbn() {
        return entryKbn;
    }

    /**
     * @param entryKbn the entryKbn to set
     */
    public void setEntryKbn(String entryKbn) {
        this.entryKbn = entryKbn;
    }

    /**
     * @return the entryPlace
     */
    public String getEntryPlace() {
        return entryPlace;
    }

    /**
     * @param entryPlace the entryPlace to set
     */
    public void setEntryPlace(String entryPlace) {
        this.entryPlace = entryPlace;
    }

    /**
     * @return the kakuteiYmdSt
     */
    public String getKakuteiYmdSt() {
        return kakuteiYmdSt;
    }

    /**
     * @param kakuteiYmdSt the kakuteiYmdSt to set
     */
    public void setKakuteiYmdSt(String kakuteiYmdSt) {
        this.kakuteiYmdSt = kakuteiYmdSt;
    }

    /**
     * @return the kakuteiYmdEd
     */
    public String getKakuteiYmdEd() {
        return kakuteiYmdEd;
    }

    /**
     * @param kakuteiYmdEd the kakuteiYmdEd to set
     */
    public void setKakuteiYmdEd(String kakuteiYmdEd) {
        this.kakuteiYmdEd = kakuteiYmdEd;
    }

    /**
     * @return the tantoCd
     */
    public String getTantoCd() {
        return tantoCd;
    }

    /**
     * @param tantoCd the tantoCd to set
     */
    public void setTantoCd(String tantoCd) {
        this.tantoCd = tantoCd;
    }

    /**
     * @return the tantoNm
     */
    public String getTantoNm() {
        return tantoNm;
    }

    /**
     * @param tantoNm the tantoNm to set
     */
    public void setTantoNm(String tantoNm) {
        this.tantoNm = tantoNm;
    }

    /**
     * @return the outKaisyaCd
     */
    public String getOutKaisyaCd() {
        return outKaisyaCd;
    }

    /**
     * @param outKaisyaCd the outKaisyaCd to set
     */
    public void setOutKaisyaCd(String outKaisyaCd) {
        this.outKaisyaCd = outKaisyaCd;

    }

    /**
     * @return the outJigyobuCd
     */
    public String getOutJigyobuCd() {
        return outJigyobuCd;
    }

    /**
     * @param outJigyobuCd the outJigyobuCd to set
     */
    public void setOutJigyobuCd(String outJigyobuCd) {
        this.outJigyobuCd = outJigyobuCd;
    }

    /**
     * @return the outtenCd
     */
    public String getOutTenCd() {
        return outTenCd;
    }

    /**
     * @param outTenCd the outTenCd to set
     */
    public void setOutTenCd(String outTenCd) {
        this.outTenCd = outTenCd;
    }

}
