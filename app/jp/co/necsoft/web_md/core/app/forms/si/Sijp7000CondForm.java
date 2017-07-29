// /////////////////////////////////////////////////////////////////////
// Copyright(C) 2015 NEC Solution Innovators, Ltd. All Rights Reserved.
// /////////////////////////////////////////////////////////////////////

/*
 * ==================================================================== 
 * 機能名称 : 仕入状況一覧表 
 * Rev. 改版年月日 改版者名 内容 
 * 1.0 2015.06.03 NECVN 新規作成 
 * ===================================================================
 */

package jp.co.necsoft.web_md.core.app.forms.si;

import play.data.validation.Constraints.MaxLength;
import play.data.validation.Constraints.MinLength;
import play.data.validation.Constraints.Required;

/**
 * 仕入状況一覧表のConditionFormクラス
 * 
 */
public class Sijp7000CondForm {
    /** 会社 */
    @Required
    private String kaisyaCd;
        
    /** 事業部 */
    @MaxLength(value = 2)
    @MinLength(value = 2)
    private String jigyobuCd;
    
    /** 店舗 */
    @MaxLength(value = 3)
    @MinLength(value = 3)
    private String tenCd;

    /** 納品日 */
    private String nhnYoteiYmdFr;

    /** 納品日終了 */
    private String nhnYoteiYmdTo;
    
    /** 取引先コード */
    @MaxLength(value = 9)
    @MinLength(value = 9)
    private String torihikiCd;
    
    /**
     * get Kaisya Code
     * @return the kaisyaCd
     */
    public String getKaisyaCd() {
        return kaisyaCd;
    }

    /**
     * set Kaisya Code
     * @param kaisyaCd the kaisyaCd to set
     */
    public void setKaisyaCd(String kaisyaCd) {
        this.kaisyaCd = kaisyaCd;
    }

    /**
     * get Jigyobu Code
     * @return the jigyobuCd
     */
    public String getJigyobuCd() {
        return jigyobuCd;
    }

    /**
     * set Jigyobu Code
     * @param jigyobuCd the jigyobuCd to set
     */
    public void setJigyobuCd(String jigyobuCd) {
        this.jigyobuCd = jigyobuCd;
    }

    /**
     * get Ten Code
     * @return the tenCd
     */
    public String getTenCd() {
        return tenCd;
    }

    /**
     * set Ten Code
     * @param tenCd the tenCd to set
     */
    public void setTenCd(String tenCd) {
        this.tenCd = tenCd;
    }

    /**
     * get NhnDay From
     * @return the nhnDayFr
     */
    public String getNhnYoteiYmdFr() {
        return nhnYoteiYmdFr;
    }

    /**
     * set NhnDay From
     * @param nhnYoteiYmdFr the nhnYoteiYmdFr to set
     */
    public void setNhnYoteiYmdFr(String nhnYoteiYmdFr) {
        this.nhnYoteiYmdFr = nhnYoteiYmdFr;
    }

    /**
     * get NhnDay To
     * @return the nhnDayTo
     */
    public String getNhnYoteiYmdTo() {
        return nhnYoteiYmdTo;
    }

    /**
     * set NhnDay To
     * @param nhnYoteiYmdTo the nhnYoteiYmdTo to set
     */
    public void setNhnYoteiYmdTo(String nhnYoteiYmdTo) {
        this.nhnYoteiYmdTo = nhnYoteiYmdTo;
    }

    /**
     * get Tri Code
     * @return the triCd
     */
    public String getTorihikiCd() {
        return torihikiCd;
    }

    /**
     * set Tri Code
     * @param torihikiCd the torihikiCd to set
     */
    public void setTorihikiCd(String torihikiCd) {
        this.torihikiCd = torihikiCd;
    }
}
