// /////////////////////////////////////////////////////////////////////
// Copyright(C) 2015 NEC Solution Innovators, Ltd. All Rights Reserved.
// /////////////////////////////////////////////////////////////////////

/*
 * ==================================================================== 
 * 機能名称 : オリジナル商品品振エラーリスト 
 * Rev. 改版年月日 改版者名 内容 
 * 1.0 2015.06.10 NECVN 新規作成 
 * ===================================================================
 */

package jp.co.necsoft.web_md.core.app.forms.si;

import play.data.validation.Constraints.MaxLength;
import play.data.validation.Constraints.MinLength;
import play.data.validation.Constraints.Required;

/**
 * オリジナル商品品振エラーリスト のConditionFormクラス
 * 
 */
public class Sijp7090CondForm {
    /** 会社 */
    @Required
    private String kaisyaCd;
    
    /** 事業部 */
    private String jigyobuCd;
    
    /** 発注日 */
    private String hatDayFr;

    /** 発注日 */
    private String hatDayTo;
    
    /** 店舗 */
    @MinLength(value = 3)
    @MaxLength(value = 3)
    private String tenCd;
    
    /** 処理結果 */
    private String hinKkKbn;
    
    /** 発注日 */
    private String unyoDate;

    /**
     * get KaisyaCd
     * @return the kaisyaCd
     */
    public String getKaisyaCd() {
        return kaisyaCd;
    }

    /**
     * set KaisyaCd
     * @param kaisyaCd the kaisyaCd to set
     */
    public void setKaisyaCd(String kaisyaCd) {
        this.kaisyaCd = kaisyaCd;
    }

    /**
     * get JigyobuCd
     * @return the jigyobuCd
     */
    public String getJigyobuCd() {
        return jigyobuCd;
    }

    /**
     * set JigyobuCd
     * @param jigyobuCd the jigyobuCd to set
     */
    public void setJigyobuCd(String jigyobuCd) {
        this.jigyobuCd = jigyobuCd;
    }

    /**
     * get hatDayFr
     * @return the hatDayFr
     */
    public String getHatDayFr() {
        return hatDayFr;
    }

    /**
     * set hatDayFr
     * @param hatDayFr the hatDayFr to set
     */
    public void setHatDayFr(String hatDayFr) {
        this.hatDayFr = hatDayFr;
    }

    /**
     * get hatDayTo
     * @return the hatDayTo
     */
    public String getHatDayTo() {
        return hatDayTo;
    }

    /**
     * set hatDayTo
     * @param hatDayTo the hatDayTo to set
     */
    public void setHatDayTo(String hatDayTo) {
        this.hatDayTo = hatDayTo;
    }

    /**
     * get TenCd
     * @return the tenCd
     */
    public String getTenCd() {
        return tenCd;
    }

    /**
     * set TenCd
     * @param tenCd the tenCd to set
     */
    public void setTenCd(String tenCd) {
        this.tenCd = tenCd;
    }

    /**
     * get hinKkKbn
     * @return the hinKkKbn
     */
    public String getHinKkKbn() {
        return hinKkKbn;
    }

    /**
     * set hinKkKbn
     * @param hinKkKbn the hinKkKbn to set
     */
    public void setHinKkKbn(String hinKkKbn) {
        this.hinKkKbn = hinKkKbn;
    }

    /**
     * get UnyoDate
     * @return the unyoDate
     */
    public String getUnyoDate() {
        return unyoDate;
    }

    /**
     * set UnyoDate
     * @param unyoDate the unyoDate to set
     */
    public void setUnyoDate(String unyoDate) {
        this.unyoDate = unyoDate;
    }
}
