///////////////////////////////////////////////////////////////////////
// Copyright(C) 2015 NEC Solution Innovators, Ltd. All Rights Reserved.
///////////////////////////////////////////////////////////////////////

/* ====================================================================
 * 機能名称 : 買掛金残高一覧表
 * 改版履歴
 * Rev. 改版年月日   改版者名       内容
 * 1.0   2014.04.01   TUANVT      新規作成
 * =================================================================== */

package jp.co.necsoft.web_md.core.app.forms.kk;

import play.data.validation.Constraints.MaxLength;
import play.data.validation.Constraints.MinLength;

/**
 * 買掛金残高一覧表のResultFormクラス
 * 
 */
public class Kkjp1040CondForm {
    /** 対象月From（年） */
    private String taisyoYSt;

    /**対象月From （月）**/
    private String taisyoMSt;

    /**対象月To（年）*/
    @MaxLength(value = 4)
    private String taisyoYEd;

    /**対象月To（月）**/
    @MaxLength(value = 2)
    private String taisyoMEd;

    /** 支払区分 */
    @MaxLength(value = 4)
    private String shrKin;

    /** 代表取引先(FR) */
    @MinLength(value = 6)
    @MaxLength(value = 6)
    private String mainToriCdSt;

    /***/
    private String toriNmSt;

    /** 代表取引先(TO) */

    @MinLength(value = 6)
    @MaxLength(value = 6)
    private String mainToriCdEd;

    /***/
    private String toriNmEd;

    /**会社コード**/
    private String kaisya;

    /**対象月From**/
    private String taisyoSt;

    /**対象月To**/
    private String taisyoEd;

    /**
     * @return the taisyoYSt
     */
    public String getTaisyoYSt() {
        return taisyoYSt;
    }

    /**
     * @param taisyoYSt the
     *            taisyoYSt to set
     */
    public void setTaisyoYSt(String taisyoYSt) {
        this.taisyoYSt = taisyoYSt;
    }

    /**
     * @return the taisyoMSt
     */
    public String getTaisyoMSt() {
        return taisyoMSt;
    }

    /**
     * @param taisyoMSt the
     *            taisyoMSt to set
     */
    public void setTaisyoMSt(String taisyoMSt) {
        this.taisyoMSt = taisyoMSt;
    }

    /**
     * @return the taisyoYEd
     */
    public String getTaisyoYEd() {
        return taisyoYEd;
    }

    /**
     * @param taisyoYEd the
     *            taisyoYEd to set
     */
    public void setTaisyoYEd(String taisyoYEd) {
        this.taisyoYEd = taisyoYEd;
    }

    /**
     * @return the taisyoMEd
     */
    public String getTaisyoMEd() {
        return taisyoMEd;
    }

    /**
     * @param taisyoMEd the
     *            taisyoMEd to set
     */
    public void setTaisyoMEd(String taisyoMEd) {
        this.taisyoMEd = taisyoMEd;
    }

    /**
     * @return the shrKin
     */
    public String getShrKin() {
        return shrKin;
    }

    /**
     * @param shrKin the
     *            shrKin to set
     */
    public void setShrKin(String shrKin) {
        this.shrKin = shrKin;
    }

    /**
     * @return the mainToriCdSt
     */
    public String getMainToriCdSt() {
        return mainToriCdSt;
    }

    /**
     * @param mainToriCdSt the
     *            mainToriCdSt to set
     */
    public void setMainToriCdSt(String mainToriCdSt) {
        this.mainToriCdSt = mainToriCdSt;
    }

    /**
     * @return the toriNmSt
     */
    public String getToriNmSt() {
        return toriNmSt;
    }

    /**
     * @param toriNmSt the
     *            toriNmSt to set
     */
    public void setToriNmSt(String toriNmSt) {
        this.toriNmSt = toriNmSt;
    }

    /**
     * @return the mainToriCdEd
     */
    public String getMainToriCdEd() {
        return mainToriCdEd;
    }

    /**
     * @param mainToriCdEd the
     *            mainToriCdEd to set
     */
    public void setMainToriCdEd(String mainToriCdEd) {
        this.mainToriCdEd = mainToriCdEd;
    }

    /**
     * @return the toriNmEd
     */
    public String getToriNmEd() {
        return toriNmEd;
    }

    /**
     * @param toriNmEd the
     *            toriNmEd to set
     */
    public void setToriNmEd(String toriNmEd) {
        this.toriNmEd = toriNmEd;
    }

    /**
     * @return the kaisya
     */
    public String getKaisya() {
        return kaisya;
    }

    /**
     * @param kaisya
     *            kaisya to set
     */
    public void setKaisya(String kaisya) {
        this.kaisya = kaisya;
    }

    /**
     * @return the taisyoSt
     */
    public String getTaisyoSt() {
        return taisyoSt;
    }

    /**
     * @param taisyoSt
     *            taisyoSt to set
     */
    public void setTaisyoSt(String taisyoSt) {
        this.taisyoSt = taisyoSt;
    }

    /**
     * @return the taisyoEd
     */
    public String getTaisyoEd() {
        return taisyoEd;
    }

    /**
     * @param taisyoEd
     *            taisyoEd to set
     */
    public void setTaisyoEd(String taisyoEd) {
        this.taisyoEd = taisyoEd;
    }

}
