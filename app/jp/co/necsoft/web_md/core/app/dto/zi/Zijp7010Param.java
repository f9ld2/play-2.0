// /////////////////////////////////////////////////////////////////////
// Copyright(C) 2015 NEC Solution Innovators, Ltd. All Rights Reserved.
// /////////////////////////////////////////////////////////////////////

/*
 * ==================================================================== 
 * 機能名称 : 棚卸実施チェックリスト * 改版履歴 
 * Rev. 改版年月日 改版者名 内容 
 * 1.0 2015.05.07 NECVN 新規作成 
 * ===================================================================
 */

package jp.co.necsoft.web_md.core.app.dto.zi;

/**
 * 棚卸実施チェックリストのParamクラス
 * 
 */
public class Zijp7010Param {
    /** 会社 */
    private String kaisyaCd;
    /** 事業部コード */
    private String jigyobuCd;
    /** 店舗コード */
    private String tenCd;
    /** 棚卸実施日 */
    private String tnaUnyDd;
    /** 発行日 */
    private String unyoDate;


    /**
     * get kaisyaCd
     * @return the kaisyaCd
     */
    public String getKaisyaCd() {
        return kaisyaCd;
    }
    
    /**
     * set kaisyaCd
     * @param kaisyaCd the kaisyaCd to set
     */
    public void setKaisyaCd(String kaisyaCd) {
        this.kaisyaCd = kaisyaCd;
    }

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
     * get unyoDate
     * @return the unyoDate
     */
    public String getUnyoDate() {
        return unyoDate;
    }
    
    /**
     * set unyoDate
     * @param unyoDate the unyoDate to set
     */
    public void setUnyoDate(String unyoDate) {
        this.unyoDate = unyoDate;
    }
}
