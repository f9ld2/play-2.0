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
 * 1.0 2014-05-26 TuanTQ 新規作成
 * 
 * ===================================================================
 */
package jp.co.necsoft.web_md.core.app.dto.si;

/**
*S001eosds033seiDataResultのDtoクラス
*
*/
public class S001eosds033seiDataResult {
    /**伝票番号*/
    private String dpyNo;

    /**伝票区分*/
    private String dpyKbn;

    /**会社コード*/
    private String kaisyaCd;

    /**事業部コード*/
    private String jigyoubuCd;

    /**店舗コード*/
    private String tenCd;

    /**部門コード*/
    private String bmnCd;

    /**納品日*/
    private String nhnYmd;

    /**代表取引先コード*/
    private String torihikiCd;

    /**発注日*/
    private String hatYmd;

    /**納品予定日*/
    private String nhnYoteiYmd;

    /**合計原価金額*/
    private String sKenGenkKin;

    /**合計売価金額*/
    private String sKenBaikKin;

    /**kasanKbn*/
    private String kasanKbn;
    /**intenpoCd*/
    private String intenpoCd;
    /**entryPlace*/
    private String entryPlace;
    /**entryKbn*/
    private String entryKbn;

    /**
     * @return the dpyNo
     */
    public String getDpyNo() {
        return dpyNo;
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
     * @return the sKenGenkKin
     */
    public String getsKenGenkKin() {
        return sKenGenkKin;
    }

    /**
     * @param sKenGenkKin the sKenGenkKin to set
     */
    public void setsKenGenkKin(String sKenGenkKin) {
        this.sKenGenkKin = sKenGenkKin;
    }

    /**
     * @return the sKenBaikKin
     */
    public String getsKenBaikKin() {
        return sKenBaikKin;
    }

    /**
     * @param sKenBaikKin the sKenBaikKin to set
     */
    public void setsKenBaikKin(String sKenBaikKin) {
        this.sKenBaikKin = sKenBaikKin;
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
     * @return the intenpoCd
     */
    public String getIntenpoCd() {
        return intenpoCd;
    }

    /**
     * @param intenpoCd the intenpoCd to set
     */
    public void setIntenpoCd(String intenpoCd) {
        this.intenpoCd = intenpoCd;
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

}
