// /////////////////////////////////////////////////////////////////////
// Copyright(C) 2015 NEC Solution Innovators, Ltd. All Rights Reserved.
// /////////////////////////////////////////////////////////////////////

/*
 * ==================================================================== 
 * 機能名称 : 入荷状況一覧表 
 * Rev. 改版年月日 改版者名 内容 
 * 1.0 2015.06.03 NECVN 新規作成 
 * ===================================================================
 */

package jp.co.necsoft.web_md.core.app.forms.si;

import play.data.validation.Constraints.MaxLength;

/**
 * 入荷状況一覧表のConditionFormクラス
 * 
 */
public class Sijp7020InitResultForm {
    /** 事業部 */
    @MaxLength(value = 2)
    private String jigyobuCd;
    
    /** 店舗 */
    @MaxLength(value = 3)
    private String tenCd;

    /** 取引先コード */
    @MaxLength(value = 9)
    private String triCd;

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
     * get triCd
     * @return the triCd
     */
    public String getTriCd() {
        return triCd;
    }

    /**
     * set triCd
     * @param triCd the triCd to set
     */
    public void setTriCd(String triCd) {
        this.triCd = triCd;
    }
    
   
}
