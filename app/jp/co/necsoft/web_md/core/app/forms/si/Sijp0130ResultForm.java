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

/**
*伝票明細履歴照会のResultFormクラス
*
*/
public class Sijp0130ResultForm {
    /**伝区*/
    private String dpyKbn;

    /**会社*/
    private String kaisyaCd;

    /**事業部*/
    private String jigyobuCd;

    /**店舗*/
    private String tenCd;

    /**伝票NO*/
    private String dpyNo;

    /**部門*/
    private String bmnCd;

    /**取引先*/
    private String triCd;

    /***/
    private String triNm;

    /**納品日*/
    private String nhmYmdSt;

    /***/
    private String nhmYmdEd;

    /**発注日*/
    private String hatYmdSt;

    /***/
    private String hatYmdEd;

    /**納品予定日*/
    private String nhnYoteiYmdSt;

    /***/
    private String nhnYoteiYmdEd;

    /**計上日*/
    private String ruiKejoYmdSt;

    /***/
    private String ruiKejoYmdEd;

    /**状態*/
    private String syoriSts;

    /**確定区分*/
    private String entryKbn;

    /**確定場所*/
    private String entryPlace;

    /**確定日*/
    private String kakuteiYmdSt;

    /***/
    private String kakuteiYmdEd;

    /**担当者*/
    private String tantoCd;

    /***/
    private String tantoNm;

    /**伝票*/
    private String denpyoArea;

    /**入力日*/
    private String insdd;

    /**時刻*/
    private String instime;

    /**計上日*/
    private String ruiKeijoYmd;

    /**店*/
    private String subTenCd;

    /**店名称*/
    private String subTenNmR1;

    /**部門*/
    private String subBmnCd;

    /**部門名称*/
    private String subBmnNmR;

    /**取引先*/
    private String subTriCd;

    /**取引先名称*/
    private String subTriNmR;

    /**納品日*/
    private String nhnYoteiYmd;

    /**処理区分*/
    private String dispSyoriSts;

    /**原価金額*/
    private Long atoGenkgk;

    /**変更前原価*/
    private Long maeGenkgk;

    /**売価金額*/
    private Long atoBaikgk;

    /**変更前売価*/
    private Long maeBakgk;

    /**担当者*/
    private String cmnTantoCd;

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
     * @return the denpyoArea
     */
    public String getDenpyoArea() {
        return denpyoArea;
    }

    /**
     * @param denpyoArea the denpyoArea to set
     */
    public void setDenpyoArea(String denpyoArea) {
        this.denpyoArea = denpyoArea;
    }

    /**
     * @return the insdd
     */
    public String getInsdd() {
        return insdd;
    }

    /**
     * @param insdd the insdd to set
     */
    public void setInsdd(String insdd) {
        this.insdd = insdd;
    }

    /**
     * @return the instime
     */
    public String getInstime() {
        return instime;
    }

    /**
     * @param instime the instime to set
     */
    public void setInstime(String instime) {
        this.instime = instime;
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
     * @return the subTenCd
     */
    public String getSubTenCd() {
        return subTenCd;
    }

    /**
     * @param subTenCd the subTenCd to set
     */
    public void setSubTenCd(String subTenCd) {
        this.subTenCd = subTenCd;
    }

    /**
     * @return the subTenNmR1
     */
    public String getSubTenNmR1() {
        return subTenNmR1;
    }

    /**
     * @param subTenNmR1 the subTenNmR1 to set
     */
    public void setSubTenNmR1(String subTenNmR1) {
        this.subTenNmR1 = subTenNmR1;
    }

    /**
     * @return the subBmnCd
     */
    public String getSubBmnCd() {
        return subBmnCd;
    }

    /**
     * @param subBmnCd the subBmnCd to set
     */
    public void setSubBmnCd(String subBmnCd) {
        this.subBmnCd = subBmnCd;
    }

    /**
     * @return the subBmnNmR
     */
    public String getSubBmnNmR() {
        return subBmnNmR;
    }

    /**
     * @param subBmnNmR the subBmnNmR to set
     */
    public void setSubBmnNmR(String subBmnNmR) {
        this.subBmnNmR = subBmnNmR;
    }

    /**
     * @return the subTriCd
     */
    public String getSubTriCd() {
        return subTriCd;
    }

    /**
     * @param subTriCd the subTriCd to set
     */
    public void setSubTriCd(String subTriCd) {
        this.subTriCd = subTriCd;
    }

    /**
     * @return the subTriNmR
     */
    public String getSubTriNmR() {
        return subTriNmR;
    }

    /**
     * @param subTriNmR the subTriNmR to set
     */
    public void setSubTriNmR(String subTriNmR) {
        this.subTriNmR = subTriNmR;
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
     * @return the maeBakgk
     */
    public Long getMaeBakgk() {
        return maeBakgk;
    }

    /**
     * @param maeBakgk the maeBakgk to set
     */
    public void setMaeBakgk(Long maeBakgk) {
        this.maeBakgk = maeBakgk;
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
}
