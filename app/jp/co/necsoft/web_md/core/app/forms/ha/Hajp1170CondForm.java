// /////////////////////////////////////////////////////////////////////
// Copyright(C) 2015 NEC Solution Innovators, Ltd. All Rights Reserved.
// /////////////////////////////////////////////////////////////////////

/*
 * ====================================================================
 * 
 * 機能名称 : ＴＡ伝票出力
 * 
 * 
 * 改版履歴
 * 
 * Rev. 改版年月日 改版者名 内容
 * 
 * 1.0 2014-05-05 TUCTVZ 新規作成
 * 
 * ===================================================================
 */
package jp.co.necsoft.web_md.core.app.forms.ha;

import play.data.validation.Constraints.MaxLength;

/**
 * 
 * Hajp1170CondForm
 * 
 */
public class Hajp1170CondForm {
    /** 会社 */
    private String kaisyaCd;

    /** 取引先(FR) */
    @MaxLength(value = 9)
    private String triCdSt;

    /***/
    private String triNmSt;

    /** 取引先(TO) */
    @MaxLength(value = 9)
    private String triCdEd;

    /***/
    private String triNmEd;

    /** 事業部 */
    @MaxLength(value = 2)
    private String jigyobuCd;

    /** 店舗(FR) */
    @MaxLength(value = 3)
    private String tenCdSt;

    /** 店舗(TO) */
    @MaxLength(value = 3)
    private String tenCdEd;

    /** システム日付 */
    private String sysdate;

    /**
     * @return the kaisyaCd
     */
    public String getKaisyaCd() {
        return kaisyaCd;
    }

    /**
     * @param kaisyaCd
     *            the kaisyaCd to set
     */
    public void setKaisyaCd(String kaisyaCd) {
        this.kaisyaCd = kaisyaCd;
    }

    /**
     * @return the triCdSt
     */
    public String getTriCdSt() {
        return triCdSt;
    }

    /**
     * @param triCdSt
     *            the triCdSt to set
     */
    public void setTriCdSt(String triCdSt) {
        this.triCdSt = triCdSt;
    }

    /**
     * @return the triNmSt
     */
    public String getTriNmSt() {
        return triNmSt;
    }

    /**
     * @param triNmSt
     *            the triNmSt to set
     */
    public void setTriNmSt(String triNmSt) {
        this.triNmSt = triNmSt;
    }

    /**
     * @return the triCdEd
     */
    public String getTriCdEd() {
        return triCdEd;
    }

    /**
     * @param triCdEd
     *            the triCdEd to set
     */
    public void setTriCdEd(String triCdEd) {
        this.triCdEd = triCdEd;
    }

    /**
     * @return the triNmEd
     */
    public String getTriNmEd() {
        return triNmEd;
    }

    /**
     * @param triNmEd
     *            the triNmEd to set
     */
    public void setTriNmEd(String triNmEd) {
        this.triNmEd = triNmEd;
    }

    /**
     * @return the jigyobuCd
     */
    public String getJigyobuCd() {
        return jigyobuCd;
    }

    /**
     * @param jigyobuCd
     *            the jigyobuCd to set
     */
    public void setJigyobuCd(String jigyobuCd) {
        this.jigyobuCd = jigyobuCd;
    }

    /**
     * @return the tenCdSt
     */
    public String getTenCdSt() {
        return tenCdSt;
    }

    /**
     * @param tenCdSt
     *            the tenCdSt to set
     */
    public void setTenCdSt(String tenCdSt) {
        this.tenCdSt = tenCdSt;
    }

    /**
     * @return the tenCdEd
     */
    public String getTenCdEd() {
        return tenCdEd;
    }

    /**
     * @param tenCdEd
     *            the tenCdEd to set
     */
    public void setTenCdEd(String tenCdEd) {
        this.tenCdEd = tenCdEd;
    }

    /**
     * @return sysdate
     */
    public String getSysdate() {
        return sysdate;
    }

    /**
     * @param sysdate the sysdate to set
     */
    public void setSysdate(String sysdate) {
        this.sysdate = sysdate;
    }

}
