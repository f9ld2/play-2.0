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
*S001eosdExecuteResultのDtoクラス
*
*/
public class S001eosdExecuteResult {
    /**伝票番号*/
    private String dpyNo;

    /**伝票区分*/
    private String dpyKbn;

    /**会社コード*/
    private String kaisyaCd;

    /**事業部コード*/
    private String jigyoubuCd;

    /**店舗コード*/
    private String sTenCd;

    /**部門コード*/
    private String sBmnCd;

    /**部門コード*/
    private String outBmnCd;

    /**部門コード*/
    private String sBmnDummyCd;

    /**納品日*/
    private String nhnYmd;

    /**代表取引先コード*/
    private String torihikiCd;

    /**発注日*/
    private String hatYmd;

    /**納品予定日*/
    private String nhnYoteiYmd;

    /**累積計上日*/
    private String ruiKeijoYmd;

    /**最終確定日*/
    private String lastKakuteiYmd;

    /**最終担当者コード*/
    private String lastTantoCd;

    /**確定区分*/
    private String entryKbn;

    /**確定場所*/
    private String entryPlace;

    /**＋／－区分*/
    private String kasanKbn;

    /**処理状態区分*/
    private String syoriStsKbn;

    /**合計原価金額*/
    private Long sKenGenkKin;

    /**合計売価金額*/
    private Long sKenBaikKin;

    /**店舗略称名１（漢字）*/
    private String tenNmR1;

    /**部門略称漢字*/
    private String bmnNmR;

    /**略称（漢字）*/
    private String triNmR;

    /**名称（漢字）*/
    private String nm;

    /**発効日*/
    private String tHakkoDay;

    /**店舗コード*/
    private String tTenCd;

    /**発効日*/
    private String bHakkoDay;

    /**部門コード*/
    private String bBmnCd;

    /**発効日*/
    private String rHakkoDay;

    /**取引先コード*/
    private String triCd;

    /**発効日*/
    private String mHakkoDay;

    /**コード区分*/
    private String cdKbn;

    /**コード*/
    private String cd;

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
     * @return the sTenCd
     */
    public String getsTenCd() {
        return sTenCd;
    }

    /**
     * @param sTenCd the sTenCd to set
     */
    public void setsTenCd(String sTenCd) {
        this.sTenCd = sTenCd;
    }

    /**
     * @return the sBmnCd
     */
    public String getsBmnCd() {
        return sBmnCd;
    }

    /**
     * @param sBmnCd the sBmnCd to set
     */
    public void setsBmnCd(String sBmnCd) {
        this.sBmnCd = sBmnCd;
    }

    /**
     * @return the outBmnCd
     */
    public String getOutBmnCd() {
        return outBmnCd;
    }

    /**
     * @param outBmnCd the outBmnCd to set
     */
    public void setOutBmnCd(String outBmnCd) {
        this.outBmnCd = outBmnCd;
    }

    /**
     * @return the sBmnDummyCd
     */
    public String getsBmnDummyCd() {
        return sBmnDummyCd;
    }

    /**
     * @param sBmnDummyCd the sBmnDummyCd to set
     */
    public void setsBmnDummyCd(String sBmnDummyCd) {
        this.sBmnDummyCd = sBmnDummyCd;
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
     * @return the lastKakuteiYmd
     */
    public String getLastKakuteiYmd() {
        return lastKakuteiYmd;
    }

    /**
     * @param lastKakuteiYmd the lastKakuteiYmd to set
     */
    public void setLastKakuteiYmd(String lastKakuteiYmd) {
        this.lastKakuteiYmd = lastKakuteiYmd;
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
     * @return the kasanKbn
     */
    public String getKasanKbn() {
        return kasanKbn;
    }

    /**
     * @param kasanKbn the kasanKbn to set
     */
    public void setKasanKbn(String kasanKbn) {
        this.kasanKbn = kasanKbn;
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
     * @return the sKenGenkKin
     */
    public Long getsKenGenkKin() {
        return sKenGenkKin;
    }

    /**
     * @param sKenGenkKin the sKenGenkKin to set
     */
    public void setsKenGenkKin(Long sKenGenkKin) {
        this.sKenGenkKin = sKenGenkKin;
    }

    /**
     * @return the sKenBaikKin
     */
    public Long getsKenBaikKin() {
        return sKenBaikKin;
    }

    /**
     * @param sKenBaikKin the sKenBaikKin to set
     */
    public void setsKenBaikKin(Long sKenBaikKin) {
        this.sKenBaikKin = sKenBaikKin;
    }

    /**
     * @return the tenNmR1
     */
    public String getTenNmR1() {
        return tenNmR1;
    }

    /**
     * @param tenNmR1 the tenNmR1 to set
     */
    public void setTenNmR1(String tenNmR1) {
        this.tenNmR1 = tenNmR1;
    }

    /**
     * @return the bmnNmR
     */
    public String getBmnNmR() {
        return bmnNmR;
    }

    /**
     * @param bmnNmR the bmnNmR to set
     */
    public void setBmnNmR(String bmnNmR) {
        this.bmnNmR = bmnNmR;
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
     * @return the tTenCd
     */
    public String gettTenCd() {
        return tTenCd;
    }

    /**
     * @param tTenCd the tTenCd to set
     */
    public void settTenCd(String tTenCd) {
        this.tTenCd = tTenCd;
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
     * @return the bBmnCd
     */
    public String getbBmnCd() {
        return bBmnCd;
    }

    /**
     * @param bBmnCd the bBmnCd to set
     */
    public void setbBmnCd(String bBmnCd) {
        this.bBmnCd = bBmnCd;
    }

    /**
     * @return the rHakkoDay
     */
    public String getrHakkoDay() {
        return rHakkoDay;
    }

    /**
     * @param rHakkoDay the rHakkoDay to set
     */
    public void setrHakkoDay(String rHakkoDay) {
        this.rHakkoDay = rHakkoDay;
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
     * @return the mHakkoDay
     */
    public String getmHakkoDay() {
        return mHakkoDay;
    }

    /**
     * @param mHakkoDay the mHakkoDay to set
     */
    public void setmHakkoDay(String mHakkoDay) {
        this.mHakkoDay = mHakkoDay;
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
