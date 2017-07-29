// /////////////////////////////////////////////////////////////////////
// Copyright(C) 2015 NEC Solution Innovators, Ltd. All Rights Reserved.
// /////////////////////////////////////////////////////////////////////

/*
 * ====================================================================
 * 
 * 機能名称 :伝票入力プルーフリスト
 * 
 * 改版履歴
 * 
 * Rev. 改版年月日 改版者名 内容
 * 
 * 1.0 20140429 Tinnc 新規作成
 * 
 * ===================================================================
 */
package jp.co.necsoft.web_md.core.app.forms.si;

import play.data.validation.Constraints.MaxLength;

/**
*伝票入力プルーフリストのCondFormクラス
*
*/
public class Sijp1230CondForm {
    /**入力担当者*/
    private String tantoCd;

    /***/
    private String lastTantoNm;

    /**納品日*/
    private String nhmYmd;

    /**入力日付*/
    private String cmnUpddd;

    /**入力時刻*/
    private String insTimeSt;

    /***/
    private String insTimeEd;

    /**伝区*/
    @MaxLength(value = 2)
    private String dpyKbn;

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
    * @return the nhmYmd
    */
    public String getNhmYmd() {
        return nhmYmd;
    }

    /**
    * @param nhmYmd the nhmYmd to set
    */
    public void setNhmYmd(String nhmYmd) {
        this.nhmYmd = nhmYmd;
    }

    /**
    * @return the cmnUpddd
    */
    public String getCmnUpddd() {
        return cmnUpddd;
    }

    /**
    * @param cmnUpddd the cmnUpddd to set
    */
    public void setCmnUpddd(String cmnUpddd) {
        this.cmnUpddd = cmnUpddd;
    }

    /**
    * @return the insTimeSt
    */
    public String getInsTimeSt() {
        return insTimeSt;
    }

    /**
    * @param insTimeSt the insTimeSt to set
    */
    public void setInsTimeSt(String insTimeSt) {
        this.insTimeSt = insTimeSt;
    }

    /**
    * @return the insTimeEd
    */
    public String getInsTimeEd() {
        return insTimeEd;
    }

    /**
    * @param insTimeEd the insTimeEd to set
    */
    public void setInsTimeEd(String insTimeEd) {
        this.insTimeEd = insTimeEd;
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

}
