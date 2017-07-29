// /////////////////////////////////////////////////////////////////////
// Copyright(C) 2015 NEC Solution Innovators, Ltd. All Rights Reserved.
// /////////////////////////////////////////////////////////////////////

/*
 * ====================================================================
 * 
 * 機能名称 : 伝票明細履歴照会
 * 
 * 改版履歴
 * 
 * Rev. 改版年月日 改版者名 内容
 * 
 * 1.0 2014-03-28 TUCTV 新規作成
 * 
 * ===================================================================
 */
package jp.co.necsoft.web_md.core.app.dto.si;

/**
*S014eomrExecuteのDtoクラス
*
*/
public class S014eomrExecute {
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
    /**取引先コード*/
    private String triCd;

    /**代表取引先コード*/
    private String torihikiCd;

    /**納品日(FR)*/
    private String nhmYmdSt;

    /**納品日(TO)*/
    private String nhmYmdEd;

    /**発注日(FR)*/
    private String hatYmdSt;

    /**発注日(TO)*/
    private String hatYmdEd;

    /**納品予定日(FR)*/
    private String nhnYoteiYmdSt;

    /**納品予定日(TO)*/
    private String nhnYoteiYmdEd;

    /**累積計上日(FR)*/
    private String ruiKejoYmdSt;

    /**累積計上日(TO)*/
    private String ruiKejoYmdEd;

    /**確定場所*/
    private String entryPlace;

    /**確定日*/
    private String kakuteiYmdSt;

    /**確定日*/
    private String kakuteiYmdEd;

    /**担当者コード*/
    private String cmnTantoCd;

    /**検索用処理状態区分*/
    private String syoriSts;

    /**確定区分*/
    private String entryKbn;

    /**likeFlag**/
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
     * @return the cmnTantoCd
     */
    public String getCmnTantoCd() {
        return cmnTantoCd;
    }

    /**
     * @param cmnTantoCd the cmnTantoCd to set
     */
    public void setCmnTantoCd(String cmnTantoCd) {
        this.cmnTantoCd = cmnTantoCd;
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
}
