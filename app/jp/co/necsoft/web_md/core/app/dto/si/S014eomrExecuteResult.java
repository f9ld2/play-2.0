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
*S014eomrExecuteResultのDtoクラス
*
*/
public class S014eomrExecuteResult {
    /**入力日*/
    private String insDd;

    /**入力時刻*/
    private String insTime;

    /**伝票番号*/
    private String dpyNo;

    /**伝区*/
    private String dpyKbn;

    /**会社コード*/
    private String kaisyaCd;

    /**事業部コード*/
    private String jigyoubuCd;

    /**店舗コード*/
    private String tenCd;

    /**部門コード*/
    private String bmnCd;

    /**取引先コード*/
    private String torihikiCd;

    /**納品日*/
    private String nhnYmd;

    /**発注日*/
    private String hatYmd;

    /**納品予定日*/
    private String nhnYoteiYmd;

    /**累積計上日*/
    private String ruiKeijoYmd;

    /**確定日*/
    private String kakuteiYmd;

    /**確定区分*/
    private String entryKbn;

    /**確定場所*/
    private String entryPlace;

    /**表示用処理状態区分*/
    private String syoriSts;

    /**検索用処理状態区分*/
    private String dispSyoriSts;

    /**変更前原価金額*/
    private Long maeGenkgk;

    /**変更後原価金額*/
    private Long atoGenkgk;

    /**変更前売価金額*/
    private Long maeBaikgk;

    /**変更後売価金額*/
    private Long atoBaikgk;

    /**担当者コード*/
    private String cmnTantoCd;

    /**店舗略称名１（漢字）*/
    private String tenNmr1;

    /**部門略称漢字*/
    private String bmnNmr;

    /**略称（漢字）*/
    private String triNmr;

    /**名称（漢字）*/
    private String nm;

    /**発効日*/
    private String tHakkoDay;

    /**発効日*/
    private String ttenCd;

    /**発効日*/
    private String bHakkoDay;

    /**発効日*/
    private String rHakkoday;

    /**取引先コード*/
    private String triCd;

    /**発効日*/
    private String mHakkoday;

    /**コード区分*/
    private String cdKbn;

    /**コード*/
    private String cd;

    /**
     * @return the insDd
     */
    public String getInsDd() {
        return insDd;
    }

    /**
     * @param insDd the insDd to set
     */
    public void setInsDd(String insDd) {
        this.insDd = insDd;
    }

    /**
     * @return the insTime
     */
    public String getInsTime() {
        return insTime;
    }

    /**
     * @param insTime the insTime to set
     */
    public void setInsTime(String insTime) {
        this.insTime = insTime;
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
     * @return the jigyoubuCd
     */
    public String getJigyoubuCd() {
        return jigyoubuCd;
    }

    /**
     * @param jigyoubuCd the jigyoubuCd to set
     */
    public void setJigyoubuCd(String jigyoubuCd) {
        this.jigyoubuCd = jigyoubuCd;
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
     * @return the nhnYmd
     */
    public String getNhnYmd() {
        return nhnYmd;
    }

    /**
     * @param nhnYmd the nhnYmd to set
     */
    public void setNhnYmd(String nhnYmd) {
        this.nhnYmd = nhnYmd;
    }

    /**
     * @return the hatYmd
     */
    public String getHatYmd() {
        return hatYmd;
    }

    /**
     * @param hatYmd the hatYmd to set
     */
    public void setHatYmd(String hatYmd) {
        this.hatYmd = hatYmd;
    }

    /**
     * @return the nhnYoteiYmd
     */
    public String getNhnYoteiYmd() {
        return nhnYoteiYmd;
    }

    /**
     * @param nhnYoteiYmd the nhnYoteiYmd to set
     */
    public void setNhnYoteiYmd(String nhnYoteiYmd) {
        this.nhnYoteiYmd = nhnYoteiYmd;
    }

    /**
     * @return the ruiKeijoYmd
     */
    public String getRuiKeijoYmd() {
        return ruiKeijoYmd;
    }

    /**
     * @param ruiKeijoYmd the ruiKeijoYmd to set
     */
    public void setRuiKeijoYmd(String ruiKeijoYmd) {
        this.ruiKeijoYmd = ruiKeijoYmd;
    }

    /**
     * @return the kakuteiYmd
     */
    public String getKakuteiYmd() {
        return kakuteiYmd;
    }

    /**
     * @param kakuteiYmd the kakuteiYmd to set
     */
    public void setKakuteiYmd(String kakuteiYmd) {
        this.kakuteiYmd = kakuteiYmd;
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
     * @return the dispSyoriSts
     */
    public String getDispSyoriSts() {
        return dispSyoriSts;
    }

    /**
     * @param dispSyoriSts the dispSyoriSts to set
     */
    public void setDispSyoriSts(String dispSyoriSts) {
        this.dispSyoriSts = dispSyoriSts;
    }

    /**
     * @return the maeGenkgk
     */
    public Long getMaeGenkgk() {
        return maeGenkgk;
    }

    /**
     * @param maeGenkgk the maeGenkgk to set
     */
    public void setMaeGenkgk(Long maeGenkgk) {
        this.maeGenkgk = maeGenkgk;
    }

    /**
     * @return the atoGenkgk
     */
    public Long getAtoGenkgk() {
        return atoGenkgk;
    }

    /**
     * @param atoGenkgk the atoGenkgk to set
     */
    public void setAtoGenkgk(Long atoGenkgk) {
        this.atoGenkgk = atoGenkgk;
    }

    /**
     * @return the maeBaikgk
     */
    public Long getMaeBaikgk() {
        return maeBaikgk;
    }

    /**
     * @param maeBaikgk the maeBaikgk to set
     */
    public void setMaeBaikgk(Long maeBaikgk) {
        this.maeBaikgk = maeBaikgk;
    }

    /**
     * @return the atoBaikgk
     */
    public Long getAtoBaikgk() {
        return atoBaikgk;
    }

    /**
     * @param atoBaikgk the atoBaikgk to set
     */
    public void setAtoBaikgk(Long atoBaikgk) {
        this.atoBaikgk = atoBaikgk;
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
     * @return the tenNmr1
     */
    public String getTenNmr1() {
        return tenNmr1;
    }

    /**
     * @param tenNmr1 the tenNmr1 to set
     */
    public void setTenNmr1(String tenNmr1) {
        this.tenNmr1 = tenNmr1;
    }

    /**
     * @return the bmnNmr
     */
    public String getBmnNmr() {
        return bmnNmr;
    }

    /**
     * @param bmnNmr the bmnNmr to set
     */
    public void setBmnNmr(String bmnNmr) {
        this.bmnNmr = bmnNmr;
    }

    /**
     * @return the triNmr
     */
    public String getTriNmr() {
        return triNmr;
    }

    /**
     * @param triNmr the triNmr to set
     */
    public void setTriNmr(String triNmr) {
        this.triNmr = triNmr;
    }

    /**
     * @return the nm
     */
    public String getNm() {
        return nm;
    }

    /**
     * @param nm the nm to set
     */
    public void setNm(String nm) {
        this.nm = nm;
    }

    /**
     * @return the tHakkoDay
     */
    public String gettHakkoDay() {
        return tHakkoDay;
    }

    /**
     * @param tHakkoDay the tHakkoDay to set
     */
    public void settHakkoDay(String tHakkoDay) {
        this.tHakkoDay = tHakkoDay;
    }

    /**
     * @return the ttenCd
     */
    public String getTtenCd() {
        return ttenCd;
    }

    /**
     * @param ttenCd the ttenCd to set
     */
    public void setTtenCd(String ttenCd) {
        this.ttenCd = ttenCd;
    }

    /**
     * @return the bHakkoDay
     */
    public String getbHakkoDay() {
        return bHakkoDay;
    }

    /**
     * @param bHakkoDay the bHakkoDay to set
     */
    public void setbHakkoDay(String bHakkoDay) {
        this.bHakkoDay = bHakkoDay;
    }

    /**
     * @return the rHakkoday
     */
    public String getrHakkoday() {
        return rHakkoday;
    }

    /**
     * @param rHakkoday the rHakkoday to set
     */
    public void setrHakkoday(String rHakkoday) {
        this.rHakkoday = rHakkoday;
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
     * @return the mHakkoday
     */
    public String getmHakkoday() {
        return mHakkoday;
    }

    /**
     * @param mHakkoday the mHakkoday to set
     */
    public void setmHakkoday(String mHakkoday) {
        this.mHakkoday = mHakkoday;
    }

    /**
     * @return the cdKbn
     */
    public String getCdKbn() {
        return cdKbn;
    }

    /**
     * @param cdKbn the cdKbn to set
     */
    public void setCdKbn(String cdKbn) {
        this.cdKbn = cdKbn;
    }

    /**
     * @return the cd
     */
    public String getCd() {
        return cd;
    }

    /**
     * @param cd the cd to set
     */
    public void setCd(String cd) {
        this.cd = cd;
    }
}
