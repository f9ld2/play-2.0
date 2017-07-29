// /////////////////////////////////////////////////////////////////////
// Copyright(C) 2015 NEC Solution Innovators, Ltd. All Rights Reserved.
// /////////////////////////////////////////////////////////////////////

/*
 * ====================================================================
 * 
 * 機能名称 :日別予算入力画面
 * 
 * 改版履歴
 * 
 * Rev. 改版年月日 改版者名 内容
 * 
 * 1.0 2014.04.10 TuanTQ 新規作成
 * 
 * ===================================================================
 */
package jp.co.necsoft.web_md.core.app.forms.ur;

/**
*日別店別部門別予算入力のCondFormクラス
*
*/
public class Urjp0010CondForm {
    /**対象年*/
    private String yyyy;
    /**月*/
    private String mm;
    /**会社*/
    private String kaisyaCd;
    /**事業部*/
    private String jigyobuCd;
    /**店舗*/
    private String tenCd;

    /**
     * @return the yyyy
     */
    public String getYyyy() {
        return yyyy;
    }

    /**
     * @param yyyy the yyyy to set
     */
    public void setYyyy(String yyyy) {
        this.yyyy = yyyy;
    }

    /**
     * @return the mm
     */
    public String getMm() {
        return mm;
    }

    /**
     * @param mm the mm to set
     */
    public void setMm(String mm) {
        this.mm = mm;
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

}
