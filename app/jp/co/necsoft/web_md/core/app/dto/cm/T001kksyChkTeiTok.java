// /////////////////////////////////////////////////////////////////////
// Copyright(C) 2015 NEC Solution Innovators, Ltd. All Rights Reserved.
// /////////////////////////////////////////////////////////////////////

/*
 * ====================================================================
 * 
 * 機能名称 : T001kksyChkTeiTokのDtoクラス
 * 
 * 改版履歴
 * 
 * Rev. 改版年月日 改版者名 内容
 * 
 * 1.0 2014/03/10 新規作成
 * 
 * ===================================================================
 */
package jp.co.necsoft.web_md.core.app.dto.cm;

/**
*T001kksyChkTeiTokのDtoクラス
*
*/
public class T001kksyChkTeiTok {
    /**発注日*/
    private String hatDd;

    /**商品[GTIN]*/
    private String janCd;

    /**会社コード*/
    private String kaisyaCd;

    /**事業部コード*/
    private String jigyobuCd;

    /**店舗コード*/
    private String tenCd;

    /**
     * @return the hatDd
     */
    public String getHatDd() {
        return hatDd;
    }

    /**
     * @param hatDd the hatDd to set
     */
    public void setHatDd(String hatDd) {
        this.hatDd = hatDd;
    }

    /**
     * @return the janCd
     */
    public String getJanCd() {
        return janCd;
    }

    /**
     * @param janCd the janCd to set
     */
    public void setJanCd(String janCd) {
        this.janCd = janCd;
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
