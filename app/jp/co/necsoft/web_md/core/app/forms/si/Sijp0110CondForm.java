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
 * 
 * @author tuctvz
 * @since 2014/03/25
 */
public class Sijp0110CondForm {
    /**伝区**/
    private String dpyKbn;
    /**会社コード**/
    private String kaisyaCd;
    /**事業部コード**/
    private String jigyobuCd;
    /**店舗コード**/
    private String tenCd;

    /**会社コード**/
    private String outKaisyaCd;
    /**事業部コード**/
    private String outJigyobuCd;
    /**店舗コード**/
    private String outTenCd;

    /**伝票No**/
    @MinLength(value = 9)
    @MaxLength(value = 9)
    private String dpyNo;
    /**部門コード**/
    private String bmnCd;
    /**代表取引先コード**/
    private String mainToriCd;
    /**名称（漢字）**/
    private String triNm;
    /**納品日開始**/
    private String nhnYmdSt;
    /**納品日終了**/
    private String nhnYmdEd;
    /**発注日開始**/
    private String hatYmdSt;
    /**発注日終了**/
    private String hatYmdEd;
    /**納品予定日開始**/
    private String nhnYoteiYmdSt;
    /**納品予定日終了**/
    private String nhnYoteiYmdEd;
    /**計上日開始**/
    private String ruiKeijoYmdSt;
    /**計上日終了**/
    private String ruiKeijoYmdEd;
    /**状態**/
    @MaxLength(value = 1)
    private String syoriStsKbn;
    /**確定区分**/
    @MaxLength(value = 2)
    private String entryKbn;
    /**確定場所**/
    @MinLength(value = 1)
    @MaxLength(value = 7)
    private String entryPlace;
    /**確定日開始**/
    private String lastKakuteiYmdSt;
    /**確定日終了**/
    private String lastKakuteiYmdEd;
    /**担当者コード**/
    private String lastTantoCd;
    /**名称（漢字）**/
    private String lastTantoNm;

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
     * @return the mainToriCd
     */
    public String getMainToriCd() {
        return mainToriCd;
    }

    /**
     * @param mainToriCd the mainToriCd to set
     */
    public void setMainToriCd(String mainToriCd) {
        this.mainToriCd = mainToriCd;
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
     * @return the nhnYmdSt
     */
    public String getNhnYmdSt() {
        return nhnYmdSt;
    }

    /**
     * @param nhnYmdSt the nhnYmdSt to set
     */
    public void setNhnYmdSt(String nhnYmdSt) {
        this.nhnYmdSt = nhnYmdSt;
    }

    /**
     * @return the nhnYmdEd
     */
    public String getNhnYmdEd() {
        return nhnYmdEd;
    }

    /**
     * @param nhnYmdEd the nhnYmdEd to set
     */
    public void setNhnYmdEd(String nhnYmdEd) {
        this.nhnYmdEd = nhnYmdEd;
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
     * @return the ruiKeijoYmdSt
     */
    public String getRuiKeijoYmdSt() {
        return ruiKeijoYmdSt;
    }

    /**
     * @param ruiKeijoYmdSt the ruiKeijoYmdSt to set
     */
    public void setRuiKeijoYmdSt(String ruiKeijoYmdSt) {
        this.ruiKeijoYmdSt = ruiKeijoYmdSt;
    }

    /**
     * @return the ruiKeijoYmdEd
     */
    public String getRuiKeijoYmdEd() {
        return ruiKeijoYmdEd;
    }

    /**
     * @param ruiKeijoYmdEd the ruiKeijoYmdEd to set
     */
    public void setRuiKeijoYmdEd(String ruiKeijoYmdEd) {
        this.ruiKeijoYmdEd = ruiKeijoYmdEd;
    }

    /**
     * @return the syoriStsKbn
     */
    public String getSyoriStsKbn() {
        return syoriStsKbn;
    }

    /**
     * @param syoriStsKbn the syoriStsKbn to set
     */
    public void setSyoriStsKbn(String syoriStsKbn) {
        this.syoriStsKbn = syoriStsKbn;
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
     * @return the lastKakuteiYmdSt
     */
    public String getLastKakuteiYmdSt() {
        return lastKakuteiYmdSt;
    }

    /**
     * @param lastKakuteiYmdSt the lastKakuteiYmdSt to set
     */
    public void setLastKakuteiYmdSt(String lastKakuteiYmdSt) {
        this.lastKakuteiYmdSt = lastKakuteiYmdSt;
    }

    /**
     * @return the lastKakuteiYmdEd
     */
    public String getLastKakuteiYmdEd() {
        return lastKakuteiYmdEd;
    }

    /**
     * @param lastKakuteiYmdEd the lastKakuteiYmdEd to set
     */
    public void setLastKakuteiYmdEd(String lastKakuteiYmdEd) {
        this.lastKakuteiYmdEd = lastKakuteiYmdEd;
    }

    /**
     * @return the lastTantoCd
     */
    public String getLastTantoCd() {
        return lastTantoCd;
    }

    /**
     * @param lastTantoCd the lastTantoCd to set
     */
    public void setLastTantoCd(String lastTantoCd) {
        this.lastTantoCd = lastTantoCd;
    }

    /**
     * @return the lastTantoNm
     */
    public String getLastTantoNm() {
        return lastTantoNm;
    }

    /**
     * @param lastTantoNm the lastTantoNm to set
     */
    public void setLastTantoNm(String lastTantoNm) {
        this.lastTantoNm = lastTantoNm;
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
