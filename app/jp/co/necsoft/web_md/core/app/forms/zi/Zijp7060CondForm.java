// /////////////////////////////////////////////////////////////////////
// Copyright(C) 2015 NEC Solution Innovators, Ltd. All Rights Reserved.
// /////////////////////////////////////////////////////////////////////

/*
 * ==================================================================== 
 * 機能名称 : 棚卸入力画面 * Rev. 改版年月日 改版者名 内容 
 * 1.0 2015.06.24 NECVN 新規作成 
 * ===================================================================
 */

package jp.co.necsoft.web_md.core.app.forms.zi;

import jp.co.necsoft.web_md.core.app.forms.cm.BaseCondForm;
import play.data.validation.Constraints.MaxLength;
import play.data.validation.Constraints.MinLength;

/**
 * 棚卸入力画面のConditionFormクラス
 * 
 */
public class Zijp7060CondForm extends BaseCondForm {
    /** 事業部コード */
    private String jigyobuCd;
    /** 店舗コード */
    private String tenCd;
    /** 棚卸実施年月日 */
    private String tnaUnyDd;
    /** 棚№(Fr) */
    @MinLength(value=6)
    @MaxLength(value=6)
    private String tanaNoFr;
    /** 棚№(To) */
    @MinLength(value=6)
    @MaxLength(value=6)
    private String tanaNoTo;
    /** 循環棚卸区分 */
    private String tnaKbn;
    
    /**
     * get jigyobuCd
     * @return the jigyobuCd
     */
    public String getJigyobuCd() {
        return jigyobuCd;
    }
    
    /**
     * set jigyobuCd
     * @param jigyobuCd the jigyobuCd to set
     */
    public void setJigyobuCd(String jigyobuCd) {
        this.jigyobuCd = jigyobuCd;
    }

    /**
     * get tenCd
     * @return the tenCd
     */
    public String getTenCd() {
        return tenCd;
    }
    
    /**
     * set tenCd
     * @param tenCd the tenCd to set
     */
    public void setTenCd(String tenCd) {
        this.tenCd = tenCd;
    }

    /**
     * get tnaUnyDd
     * @return the tnaUnyDd
     */
    public String getTnaUnyDd() {
        return tnaUnyDd;
    }
    
    /**
     * set tnaUnyDd
     * @param tnaUnyDd the tnaUnyDd to set
     */
    public void setTnaUnyDd(String tnaUnyDd) {
        this.tnaUnyDd = tnaUnyDd;
    }

    /**
     * get tanaNoFr
     * @return the tanaNoFr
     */
    public String getTanaNoFr() {
        return tanaNoFr;
    }
    
    /**
     * set tanaNoFr
     * @param tanaNoFr the tanaNoFr to set
     */
    public void setTanaNoFr(String tanaNoFr) {
        this.tanaNoFr = tanaNoFr;
    }

    /**
     * get tanaNoTo
     * @return the tanaNoTo
     */
    public String getTanaNoTo() {
        return tanaNoTo;
    }
    
    /**
     * set tanaNoTo
     * @param tanaNoTo the tanaNoTo to set
     */
    public void setTanaNoTo(String tanaNoTo) {
        this.tanaNoTo = tanaNoTo;
    }

    /**
     * get tnaKbn
     * @return the tnaKbn
     */
    public String getTnaKbn() {
        return tnaKbn;
    }
    
    /**
     * set tnaKbn
     * @param tnaKbn the tnaKbn to set
     */
    public void setTnaKbn(String tnaKbn) {
        this.tnaKbn = tnaKbn;
    }
}
