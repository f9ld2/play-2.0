// /////////////////////////////////////////////////////////////////////
// Copyright(C) 2015 NEC Solution Innovators, Ltd. All Rights Reserved.
// /////////////////////////////////////////////////////////////////////

/*
 * ====================================================================
 * 
 * 機能名称 : 発注プルーフリスト出力
 * 
 * 改版履歴
 * 
 * Rev. 改版年月日 改版者名 内容
 * 
 * 1.0 2014.04.20 Tinnc 新規作成
 * 
 * ===================================================================
 */

package jp.co.necsoft.web_md.core.app.forms.ha;

import play.data.validation.Constraints.MaxLength;

/**
*発注プルーフリスト出力のCondFormクラス
*
*/
public class Hajp1020CondForm {
    /**会社*/
    private String kaisyaCd;

    /**事業部*/
    @MaxLength(value = 2)
    private String jigyobuCd;

    /**店舗(FR)*/
    @MaxLength(value = 3)
    private String tenCdSt;

    /**店舗(TO)*/
    @MaxLength(value = 3)
    private String tenCdEd;

    /**発注日*/
    private String hachuSt;

    /***/
    private String hachuEd;

    /**発注種類種別*/
    @MaxLength(value = 2)
    private String hatSruiKbn;

    /**出力順指定*/
    @MaxLength(value = 1)
    private String sort;

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
    * @return the tenCdSt
    */
    public String getTenCdSt() {
        return tenCdSt;
    }

    /**
    * @param tenCdSt the tenCdSt to set
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
    * @param tenCdEd the tenCdEd to set
    */
    public void setTenCdEd(String tenCdEd) {
        this.tenCdEd = tenCdEd;
    }

    /**
    * @return the hachuSt
    */
    public String getHachuSt() {
        return hachuSt;
    }

    /**
    * @param hachuSt the hachuSt to set
    */
    public void setHachuSt(String hachuSt) {
        this.hachuSt = hachuSt;
    }

    /**
    * @return the hachuEd
    */
    public String getHachuEd() {
        return hachuEd;
    }

    /**
    * @param hachuEd the hachuEd to set
    */
    public void setHachuEd(String hachuEd) {
        this.hachuEd = hachuEd;
    }

    /**
    * @return the hatSruiKbn
    */
    public String getHatSruiKbn() {
        return hatSruiKbn;
    }

    /**
    * @param hatSruiKbn the hatSruiKbn to set
    */
    public void setHatSruiKbn(String hatSruiKbn) {
        this.hatSruiKbn = hatSruiKbn;
    }

    /**
    * @return the sort
    */
    public String getSort() {
        return sort;
    }

    /**
    * @param sort the sort to set
    */
    public void setSort(String sort) {
        this.sort = sort;
    }

}
