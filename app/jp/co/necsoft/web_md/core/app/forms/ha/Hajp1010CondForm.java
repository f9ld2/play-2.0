// /////////////////////////////////////////////////////////////////////
// Copyright(C) 2015 NEC Solution Innovators, Ltd. All Rights Reserved.
// /////////////////////////////////////////////////////////////////////

/*
 * ==================================================================== 
 * 機能名称 : 発注エラーリスト 改版履歴 
 * Rev. 改版年月日 改版者名 内容 
 * 1.0 2014.16.04 LocHV 新規作成 
 * ===================================================================
 */

package jp.co.necsoft.web_md.core.app.forms.ha;

import play.data.validation.Constraints.MaxLength;
import play.data.validation.Constraints.Required;

/**
 * 発注エラーリストのConditionFormクラス
 * 
 */
public class Hajp1010CondForm {
    /** 会社 */
    @Required
    private String kaisyaCd;

    /** 事業部 */
    private String jigyobuCd;

    /** 発注日 */
    private String hatDdSt;

    /** 発注日 */
    private String hatDdEd;

    /** 店舗(FR) */
    private String tenCdSt;

    /** 店舗(TO) */
    private String tenCdEd;

    /** 部門(FR) */
    private String bmnSt;

    /** 部門(TO) */
    private String bmnEd;

    /** 発注処理種別 */
    @MaxLength(value = 2)
    private String hatSruiKbn;

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
     * @return the hatDtSt
     */
    public String getHatDdSt() {
        return hatDdSt;
    }

    /**
     * @param hatDtSt the
     *            hatDtSt to set
     */
    public void setHatDdSt(String hatDtSt) {
        this.hatDdSt = hatDtSt;
    }

    /**
     * @return the hatDtEd
     */
    public String getHatDdEd() {
        return hatDdEd;
    }

    /**
     * @param hatDtEd the
     *            hatDtEd to set
     */
    public void setHatDdEd(String hatDtEd) {
        this.hatDdEd = hatDtEd;
    }

    /**
     * @return the tenCdSt
     */
    public String getTenCdSt() {
        return tenCdSt;
    }

    /**
     * @param tenCdSt the
     *            tenCdSt to set
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
     * @param tenCdEd the
     *            tenCdEd to set
     */
    public void setTenCdEd(String tenCdEd) {
        this.tenCdEd = tenCdEd;
    }

    /**
     * @return the bmnSt
     */
    public String getBmnSt() {
        return bmnSt;
    }

    /**
     * @param bmnSt the
     *            bmnSt to set
     */
    public void setBmnSt(String bmnSt) {
        this.bmnSt = bmnSt;
    }

    /**
     * @return the bmnEd
     */
    public String getBmnEd() {
        return bmnEd;
    }

    /**
     * @param bmnEd the
     *            bmnEd to set
     */
    public void setBmnEd(String bmnEd) {
        this.bmnEd = bmnEd;
    }

    /**
     * @return the hatSruiKbn
     */
    public String getHatSruiKbn() {
        return hatSruiKbn;
    }

    /**
     * @param hatSruiKbn the
     *            hatSruiKbn to set
     */
    public void setHatSruiKbn(String hatSruiKbn) {
        this.hatSruiKbn = hatSruiKbn;
    }

}
