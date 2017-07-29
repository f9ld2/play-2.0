// /////////////////////////////////////////////////////////////////////
// Copyright(C) 2015 NEC Solution Innovators, Ltd. All Rights Reserved.
// /////////////////////////////////////////////////////////////////////

/*
 * ====================================================================
 * 
 * 機能名称 : 伝票ヘッダ照会
 * 
 * 改版履歴
 * 
 * Rev. 改版年月日 改版者名 内容
 * 
 * 1.0 2014-03-25 tuctv 新規作成
 * 
 * ===================================================================
 */
package jp.co.necsoft.web_md.core.app.dto.si;

/**
*S001eosdExecuteのDtoクラス
*
*/
public class S001eosdExecute {
    /**現在の日付*/
    private String currentDay;

    /**会社コード*/
    private String kaisyaCd;

    /**事業部コード*/
    private String jigyobuCd;

    /**店舗コード*/
    private String tenCd;

    /**伝票番号*/
    private String dpyKbn;

    /**伝票区分*/
    private String dpyNo;

    /**部門コード*/
    private String bmnCd;

    /**代表取引先コード*/
    private String torihikiCd;

    /**納品日(FR)*/
    private String nhnYmdSt;

    /**納品日(TO)*/
    private String nhnYmdEd;

    /**発注日(FR)*/
    private String hatYmdSt;

    /**発注日(TO)*/
    private String hatYmdEd;

    /**納品予定日(FR)*/
    private String nhnYoteiYmdSt;

    /**納品予定日(TO)*/
    private String nhnYoteiYmdEd;

    /**累積計上日(FR)*/
    private String ruiKeijoYmdSt;

    /**累積計上日(TO)*/
    private String ruiKeijoYmdEd;

    /**確定場所*/
    private String entryPlace;

    /**最終確定日(FR)*/
    private String lastKakuteiYmdSt;

    /**最終確定日(TO)*/
    private String lastKakuteiYmdEd;

    /**最終担当者コード*/
    private String lastTantoCd;

    /**処理状態区分*/
    private String syoriStsKbn;

    /**確定区分*/
    private String entryKbn;

    /**likeFlag*/
    private int likeFlag;

    /**
     * @return the currentDay
     */
    public String getCurrentDay() {
        return currentDay;
    }

    /**
     * @param currentDay the currentDay to set
     */
    public void setCurrentDay(String currentDay) {
        this.currentDay = currentDay;
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
     * @return the torihikiCd
     */
    public String getTorihikiCd() {
        return torihikiCd;
    }

    /**
     * @param torihikiCd the torihikiCd to set
     */
    public void setTorihikiCd(String torihikiCd) {
        this.torihikiCd = torihikiCd;
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
     * @return the likeFlag
     */
    public int getLikeFlag() {
        return likeFlag;
    }

    /**
     * @param likeFlag the likeFlag to set
     */
    public void setLikeFlag(int likeFlag) {
        this.likeFlag = likeFlag;
    }
}
