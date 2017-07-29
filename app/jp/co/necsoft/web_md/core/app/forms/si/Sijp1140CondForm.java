// /////////////////////////////////////////////////////////////////////
// Copyright(C) 2015 NEC Solution Innovators, Ltd. All Rights Reserved.
// /////////////////////////////////////////////////////////////////////

/*
 * ====================================================================
 * 
 * 機能名称 :仕入伝票未回収一覧（ＥＯＳ）
 * 
 * 改版履歴
 * 
 * Rev. 改版年月日 改版者名 内容
 * 
 * 1.0 2014.05.10 TuanTQ 新規作成
 * 
 * ===================================================================
 */
package jp.co.necsoft.web_md.core.app.forms.si;

import java.util.List;

/**
* 仕入伝票未回収一覧（ＥＯＳ）のCondFormクラス
*
*/
public class Sijp1140CondForm {
    /**会社*/
    private String kaisyaCd;

    /**事業部*/
    private String jigyoubuCd;

    /**伝区*/
    private String dpyKbn;

    /**伝区Nm*/
    private String dpyKbnNm;

    /**店舗*/
    private String tenCds;

    /**店舗*/
    private List<String> lsTenCd;

    /**部門*/
    private String bmnCds;

    /**部門*/
    private List<String> lsBmnCd;

    /**取引先*/
    private String torihikiCds;

    /**取引先*/
    private List<String> lsTorihikiCd;

    /**納品予定日（開始日）*/
    private String nhnYoteiYmdSt;

    /**納品予定日（終了日）*/
    private String nhnYoteiYmdEd;

    /**発注日（開始日）*/
    private String hatYmdSt;

    /**発注日（終了日）*/
    private String hatYmdEd;

    /**会社*/
    private String shKaisyaCd;

    /**事業部*/
    private String shJigyobuCd;

    /**entry */
    private String shEntry;

    /**
     * Set condition form
     */
    public Sijp1140CondForm() {
        shKaisyaCd = "";
        shJigyobuCd = "";
        shEntry = "";
    }

    /**
     * @return the tenCds
     */
    public String getTenCds() {
        return tenCds;
    }

    /**
     * @param tenCds the tenCds to set
     */
    public void setTenCds(String tenCds) {
        this.tenCds = tenCds;
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
     * @return the lsTenCd
     */
    public List<String> getLsTenCd() {
        return lsTenCd;
    }

    /**
     * @param lsTenCd the lsTenCd to set
     */
    public void setLsTenCd(List<String> lsTenCd) {
        this.lsTenCd = lsTenCd;
    }

    /**
     * @return the bmnCds
     */
    public String getBmnCds() {
        return bmnCds;
    }

    /**
     * @param bmnCds the bmnCds to set
     */
    public void setBmnCds(String bmnCds) {
        this.bmnCds = bmnCds;
    }

    /**
     * @return the lsBmnCd
     */
    public List<String> getLsBmnCd() {
        return lsBmnCd;
    }

    /**
     * @param lsBmnCd the lsBmnCd to set
     */
    public void setLsBmnCd(List<String> lsBmnCd) {
        this.lsBmnCd = lsBmnCd;
    }

    /**
     * @return the torihikiCds
     */
    public String getTorihikiCds() {
        return torihikiCds;
    }

    /**
     * @param torihikiCds the torihikiCds to set
     */
    public void setTorihikiCds(String torihikiCds) {
        this.torihikiCds = torihikiCds;
    }

    /**
     * @return the lsTorihikiCd
     */
    public List<String> getLsTorihikiCd() {
        return lsTorihikiCd;
    }

    /**
     * @param lsTorihikiCd the lsTorihikiCd to set
     */
    public void setLsTorihikiCd(List<String> lsTorihikiCd) {
        this.lsTorihikiCd = lsTorihikiCd;
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
     * @return the dpyKbnNm
     */
    public String getDpyKbnNm() {
        return dpyKbnNm;
    }

    /**
     * @param dpyKbnNm the dpyKbnNm to set
     */
    public void setDpyKbnNm(String dpyKbnNm) {
        this.dpyKbnNm = dpyKbnNm;
    }

    /**
     * @return the shKaisyaCd
     */
    public String getShKaisyaCd() {
        return shKaisyaCd;
    }

    /**
     * @param shKaisyaCd the shKaisyaCd to set
     */
    public void setShKaisyaCd(String shKaisyaCd) {
        this.shKaisyaCd = shKaisyaCd;
    }

    /**
     * @return the shJigyobuCd
     */
    public String getShJigyobuCd() {
        return shJigyobuCd;
    }

    /**
     * @param shJigyobuCd the shJigyobuCd to set
     */
    public void setShJigyobuCd(String shJigyobuCd) {
        this.shJigyobuCd = shJigyobuCd;
    }

    /**
     * @return the shEntry
     */
    public String getShEntry() {
        return shEntry;
    }

    /**
     * @param shEntry the shEntry to set
     */
    public void setShEntry(String shEntry) {
        this.shEntry = shEntry;
    }

}
