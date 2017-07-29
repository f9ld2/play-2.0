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

import java.util.List;

/**
*Sijp1140DtoのDtoクラス
*
*/
public class Sijp1140Dto {
    /**会社コード */
    private String kaisyaCd;

    /**事業部コード*/
    private String jigyobuCd;

    /**伝票区分*/
    private String denKbn;

    /**店舗コード*/
    private String tenCds;

    /**店舗コードリスト*/
    private List<String> lsTenCds;

    /**部門コード*/
    private String bmnCds;

    /**部門コードリスト*/
    private List<String> lsBmnCds;

    /**取引先*/
    private String torisakis;

    /**取引先リスト*/
    private List<String> lsTorisakis;

    /**納品日付（開始日）*/
    private String nhnDateSt;

    /**納品日付（終了日）*/
    private String nhnDateEd;

    /**発注日付（開始日）*/
    private String hatDateSt;

    /**
     * @return the lsBmnCds
     */
    public List<String> getLsBmnCds() {
        return lsBmnCds;
    }

    /**
     * @param lsBmnCds the lsBmnCds to set
     */
    public void setLsBmnCds(List<String> lsBmnCds) {
        this.lsBmnCds = lsBmnCds;
    }

    /**
     * @return the lsTorisakis
     */
    public List<String> getLsTorisakis() {
        return lsTorisakis;
    }

    /**
     * @param lsTorisakis the lsTorisakis to set
     */
    public void setLsTorisakis(List<String> lsTorisakis) {
        this.lsTorisakis = lsTorisakis;
    }

    /**
     * @return the lsTenCds
     */
    public List<String> getLsTenCds() {
        return lsTenCds;
    }

    /**
     * @param lsTenCds the lsTenCds to set
     */
    public void setLsTenCds(List<String> lsTenCds) {
        this.lsTenCds = lsTenCds;
    }

    /**hatDateEd*/
    private String hatDateEd;
    /**ymd*/
    private String ymd;

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
     * @return the denKbn
     */
    public String getDenKbn() {
        return denKbn;
    }

    /**
     * @param denKbn the denKbn to set
     */
    public void setDenKbn(String denKbn) {
        this.denKbn = denKbn;
    }

    /**
     * @return the nhnDateSt
     */
    public String getNhnDateSt() {
        return nhnDateSt;
    }

    /**
     * @param nhnDateSt the nhnDateSt to set
     */
    public void setNhnDateSt(String nhnDateSt) {
        this.nhnDateSt = nhnDateSt;
    }

    /**
     * @return the nhnDateEd
     */
    public String getNhnDateEd() {
        return nhnDateEd;
    }

    /**
     * @param nhnDateEd the nhnDateEd to set
     */
    public void setNhnDateEd(String nhnDateEd) {
        this.nhnDateEd = nhnDateEd;
    }

    /**
     * @return the hatDateSt
     */
    public String getHatDateSt() {
        return hatDateSt;
    }

    /**
     * @param hatDateSt the hatDateSt to set
     */
    public void setHatDateSt(String hatDateSt) {
        this.hatDateSt = hatDateSt;
    }

    /**
     * @return the hatDateEd
     */
    public String getHatDateEd() {
        return hatDateEd;
    }

    /**
     * @param hatDateEd the hatDateEd to set
     */
    public void setHatDateEd(String hatDateEd) {
        this.hatDateEd = hatDateEd;
    }

    /**
     * @return the ymd
     */
    public String getYmd() {
        return ymd;
    }

    /**
     * @param ymd the ymd to set
     */
    public void setYmd(String ymd) {
        this.ymd = ymd;
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
     * @return the torisakis
     */
    public String getTorisakis() {
        return torisakis;
    }

    /**
     * @param torisakis the torisakis to set
     */
    public void setTorisakis(String torisakis) {
        this.torisakis = torisakis;
    }

}
