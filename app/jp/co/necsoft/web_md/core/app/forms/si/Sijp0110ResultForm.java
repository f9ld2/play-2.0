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
*伝票ヘッダ照会のResultFormクラス
*
*/
public class Sijp0110ResultForm {
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

    /**代表取引先*/
    private String mainToriCd;

    /***/
    private String triNm;

    /**納品日*/
    private String nhnYmdSt;

    /***/
    private String nhnYmdEd;

    /**発注日*/
    private String hatYmdSt;

    /***/
    private String hatYmdEd;

    /**納品予定日*/
    private String nhnYoteiYmdSt;

    /***/
    private String nhnYoteiYmdEd;

    /**計上日*/
    private String ruiKeijoYmdSt;

    /***/
    private String ruiKeijoYmdEd;

    /**状態*/
    private String syoriStsKbn;

    /**確定区分*/
    private String entryKbn;

    /**確定場所*/
    private String entryPlace;

    /**確定日*/
    private String lastKakuteiYmdSt;

    /***/
    private String lastKakuteiYmdEd;

    /**担当者*/
    private String lastTantoCd;

    /***/
    private String lastTantoNm;

    /**伝票*/
    private String denInfArea;

    /**No*/
    private int no;

    /**伝票番号*/
    private String gDpyNo;

    /**計上日*/
    private String ruiKeijoYmd;

    /**店*/
    private String gTenCd;

    /**店名称*/
    private String tenNm;

    /**部門*/
    private String inBmnCd;

    /**部門名称*/
    private String bmnNm;

    /**取引先*/
    private String torihikiCd;

    /**取引先名称*/
    private String gTriNm;

    /**発注日*/
    private String hatYmd;

    /**納品予定日*/
    private String nhnYoteiYmd;

    /**納品日*/
    private String nhnYmd;

    /**確定日*/
    private String subLastKakuteiYmd;

    /**原価金額*/
    private Long sKenGenkKin;

    /**売価金額*/
    private Long sKenBaikKin;

    /**担当者*/
    private String tanto;

    /**状態*/
    private String gSyoriStsKbn;

    /**確定区分*/
    private String subEntryKbn;

    /**link*/
    private String link;

    /**
    * @return  the dpyKbn
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
    * @return  the kaisyaCd
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
    * @return  the jigyobuCd
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
    * @return  the tenCd
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
    * @return  the dpyNo
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
    * @return  the bmnCd
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
    * @return  the mainToriCd
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
    * @return  the triNm
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
    * @return  the nhnYmdSt
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
    * @return  the nhnYmdEd
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
    * @return  the hatYmdSt
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
    * @return  the hatYmdEd
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
    * @return  the nhnYoteiYmdSt
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
    * @return  the nhnYoteiYmdEd
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
    * @return  the ruiKeijoYmdSt
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
    * @return  the ruiKeijoYmdEd
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
    * @return  the syoriStsKbn
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
    * @return  the entryKbn
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
    * @return  the entryPlace
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
    * @return  the lastKakuteiYmdSt
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
    * @return  the lastKakuteiYmdEd
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
    * @return  the lastTantoCd
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
    * @return  the lastTantoNm
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
    * @return  the denInfArea
    */
    public String getDenInfArea() {
        return denInfArea;
    }

    /**
    * @param denInfArea the denInfArea to set
    */
    public void setDenInfArea(String denInfArea) {
        this.denInfArea = denInfArea;
    }

    /**
    * @return  the no
    */
    public int getNo() {
        return no;
    }

    /**
    * @param no the no to set
    */
    public void setNo(int no) {
        this.no = no;
    }

    /**
    * @return  the gDpyNo
    */
    public String getGDpyNo() {
        return gDpyNo;
    }

    /**
    * @param gDpyNo the gDpyNo to set
    */
    public void setGDpyNo(String gDpyNo) {
        this.gDpyNo = gDpyNo;
    }

    /**
    * @return  the ruiKeijoYmd
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
    * @return  the gTenCd
    */
    public String getGTenCd() {
        return gTenCd;
    }

    /**
    * @param gTenCd the gTenCd to set
    */
    public void setGTenCd(String gTenCd) {
        this.gTenCd = gTenCd;
    }

    /**
    * @return  the tenNm
    */
    public String getTenNm() {
        return tenNm;
    }

    /**
    * @param tenNm the tenNm to set
    */
    public void setTenNm(String tenNm) {
        this.tenNm = tenNm;
    }

    /**
    * @return  the inBmnCd
    */
    public String getInBmnCd() {
        return inBmnCd;
    }

    /**
    * @param inBmnCd the inBmnCd to set
    */
    public void setInBmnCd(String inBmnCd) {
        this.inBmnCd = inBmnCd;
    }

    /**
    * @return  the bmnNm
    */
    public String getBmnNm() {
        return bmnNm;
    }

    /**
    * @param bmnNm the bmnNm to set
    */
    public void setBmnNm(String bmnNm) {
        this.bmnNm = bmnNm;
    }

    /**
    * @return  the torihikiCd
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
    * @return  the gTriNm
    */
    public String getGTriNm() {
        return gTriNm;
    }

    /**
    * @param gTriNm the gTriNm to set
    */
    public void setGTriNm(String gTriNm) {
        this.gTriNm = gTriNm;
    }

    /**
    * @return  the hatYmd
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
    * @return  the nhnYoteiYmd
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
    * @return  the nhnYmd
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
    * @return  the wLastKakuteiYmd
    */
    public String getSubLastKakuteiYmd() {
        return subLastKakuteiYmd;
    }

    /**
    * @param subLastKakuteiYmd the wLastKakuteiYmd to set
    */
    public void setSubLastKakuteiYmd(String subLastKakuteiYmd) {
        this.subLastKakuteiYmd = subLastKakuteiYmd;
    }

    /**
    * @return  the sKenGenkKin
    */
    public Long getSKenGenkKin() {
        return sKenGenkKin;
    }

    /**
    * @param sKenGenkKin the sKenGenkKin to set
    */
    public void setSKenGenkKin(Long sKenGenkKin) {
        this.sKenGenkKin = sKenGenkKin;
    }

    /**
    * @return  the sKenBaikKin
    */
    public Long getSKenBaikKin() {
        return sKenBaikKin;
    }

    /**
    * @param sKenBaikKin  the sKenBaikKin to set
    */
    public void setSKenBaikKin(Long sKenBaikKin) {
        this.sKenBaikKin = sKenBaikKin;
    }

    /**
    * @return  the tanto
    */
    public String getTanto() {
        return tanto;
    }

    /**
    * @param tanto the tanto to set
    */
    public void setTanto(String tanto) {
        this.tanto = tanto;
    }

    /**
    * @return  the gSyoriStsKbn
    */
    public String getGSyoriStsKbn() {
        return gSyoriStsKbn;
    }

    /**
    * @param gSyoriStsKbn the gSyoriStsKbn to set
    */
    public void setGSyoriStsKbn(String gSyoriStsKbn) {
        this.gSyoriStsKbn = gSyoriStsKbn;
    }

    /**
    * @return  the subEntryKbn
    */
    public String getsubEntryKbn() {
        return subEntryKbn;
    }

    /**
    * @param subEntryKbn  the subEntryKbn to set
    */
    public void setSubEntryKbn(String subEntryKbn) {
        this.subEntryKbn = subEntryKbn;
    }

    /**
     * @return the link
     */
    public String getLink() {
        return link;
    }

    /**
     * @param link the link to set
     */
    public void setLink(String link) {
        this.link = link;
    }

}
