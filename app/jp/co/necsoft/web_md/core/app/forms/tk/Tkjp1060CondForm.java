///////////////////////////////////////////////////////////////////////
// Copyright(C) 2015 NEC Solution Innovators, Ltd. All Rights Reserved.
///////////////////////////////////////////////////////////////////////

/* ====================================================================
 * 機能名称 : 企画情報
 * 改版履歴
 * Rev. 改版年月日   改版者名       内容
 * 1.0   2014.04.29   VuQQ      新規作成
 * =================================================================== */
package jp.co.necsoft.web_md.core.app.forms.tk;


import play.data.validation.Constraints.MaxLength;
import play.data.validation.Constraints.MinLength;


/**
 * 企画情報のResultFormクラス
 * 
 */
public class Tkjp1060CondForm {
    /** 会社 */
    private String kaisyaCd;

    /** 事業部 */
    private String jigyobuCd;

    /** 年度(FR) */
    @MinLength(value = 4)
    @MaxLength(value = 4)
    private String nendoSt;

    /** 企画(FR) */
    @MinLength(value = 9)
    @MaxLength(value = 9)
    private String kikakuCdSt;

    /***/
    private String kikakuNmSt;

    /** 部門(FR) */
    @MinLength(value = 3)
    @MaxLength(value = 3)
    private String bmnCdSt;

    /** 年度(TO) */
    @MinLength(value = 4)
    @MaxLength(value = 4)
    private String nendoEd;

    /** 企画(TO) */
    @MinLength(value = 9)
    @MaxLength(value = 9)
    private String kikakuCdEd;

    /***/
    private String kikakuNmEd;

    /** 部門(TO) */
    @MinLength(value = 3)
    @MaxLength(value = 3)
    private String bmnCdEd;

    /** 確定日 */
    @MinLength(value = 8)
    @MaxLength(value = 8)
    private String kakuteDaySt;

    /** 確定日 */
    @MinLength(value = 8)
    @MaxLength(value = 8)
    private String kakuteDayEd;


    /**
     * @return the kaisyaCd
     */
    public String getKaisyaCd() {
        return kaisyaCd;
    }

    /**
     * @param kaisyaCd the
     *            kaisyaCd to set
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
     * @param jigyobuCd the
     *            jigyobuCd to set
     */
    public void setJigyobuCd(String jigyobuCd) {
        this.jigyobuCd = jigyobuCd;
    }

    /**
     * @return the nenDoSt
     */
    public String getNendoSt() {
        return nendoSt;
    }

    /**
     * @param nendoSt the
     *            nenDoSt to set
     */
    public void setNendoSt(String nendoSt) {
        this.nendoSt = nendoSt;
    }

    /**
     * @return the kikakuCdSt
     */
    public String getkikakuCdSt() {
        return kikakuCdSt;
    }

    /**
     * @param kikakuCdSt the
     *            kikakuCdSt to set
     */
    public void setkikakuCdSt(String kikakuCdSt) {
        this.kikakuCdSt = kikakuCdSt;
    }

    /**
     * @return the kikakuNmSt
     */
    public String getkikakuNmSt() {
        return kikakuNmSt;
    }

    /**
     * @param kikakuNmSt the
     *            kikakuNmSt to set
     */
    public void setkikakuNmSt(String kikakuNmSt) {
        this.kikakuNmSt = kikakuNmSt;
    }

    /**
     * @return the bmnSt
     */
    public String getBmnCdSt() {
        return bmnCdSt;
    }

    /**
     * @param bmnCdSt the
     *            bmnSt to set
     */
    public void setBmnCdSt(String bmnCdSt) {
        this.bmnCdSt = bmnCdSt;
    }

    /**
     * @return the nenDoTo
     */
    public String getNendoEd() {
        return nendoEd;
    }

    /**
     * @param nendoEd the
     *            nenDoTo to set
     */
    public void setNendoEd(String nendoEd) {
        this.nendoEd = nendoEd;
    }

    /**
     * @return the kikakuCdEd
     */
    public String getKikakuCdEd() {
        return kikakuCdEd;
    }

    /**
     * @param kikakuCdEd the
     *            kikakuCdEd to set
     */
    public void setKikakuCdEd(String kikakuCdEd) {
        this.kikakuCdEd = kikakuCdEd;
    }

    /**
     * @return the kikakuNmTo
     */
    public String getkikakuNmEd() {
        return kikakuNmEd;
    }

    /**
     * @param kikakuNmEd the
     *            kikakuNmTo to set
     */
    public void setkikakuNmEd(String kikakuNmEd) {
        this.kikakuNmEd = kikakuNmEd;
    }

    /**
     * @return the bmnCdEd
     */
    public String getBmnCdEd() {
        return bmnCdEd;
    }

    /**
     * @param bmnCdEd
     *            bmnCdEd to set
     */
    public void setBmnCdEd(String bmnCdEd) {
        this.bmnCdEd = bmnCdEd;
    }

    /**
     * @return the kakuteDaySt
     */
    public String getKakuteDaySt() {
        return kakuteDaySt;
    }

    /**
     * @param kakuteDaySt the
     *            kakuteDaySt to set
     */
    public void setKakuteDaySt(String kakuteDaySt) {
        this.kakuteDaySt = kakuteDaySt;
    }

    /**
     * @return the kakuteDayEd
     */
    public String getKakuteDayEd() {
        return kakuteDayEd;
    }

    /**
     * @param kakuteDayEd the
     *            kakuteDayEd to set
     */
    public void setKakuteDayEd(String kakuteDayEd) {
        this.kakuteDayEd = kakuteDayEd;
    }

}
