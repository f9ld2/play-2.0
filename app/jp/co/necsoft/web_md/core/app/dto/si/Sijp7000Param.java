// /////////////////////////////////////////////////////////////////////
// Copyright(C) 2015 NEC Solution Innovators, Ltd. All Rights Reserved.
// /////////////////////////////////////////////////////////////////////

/*
 * ==================================================================== 
 * 機能名称 : 仕入状況一覧表
 * 改版履歴 
 * Rev. 改版年月日 改版者名 内容 
 * 1.0 2015.06.03 NECVN 新規作成 
 * ===================================================================
 */

package jp.co.necsoft.web_md.core.app.dto.si;



/**
 * 仕入状況一覧表のParamクラス
 * 
 */
public class Sijp7000Param {
    /** 会社 */
    private String kaisyaCd;
        
    /** 事業部 */
    private String jigyobuCd;
    
    /** 店舗 */
    private String tenCd;

    /** 納品日 */
    private String nhnYoteiYmdFr;

    /** 納品日終了 */
    private String nhnYoteiYmdTo;
    
    /** 取引先コード */
    private String torihikiCd;
    
    /** 運用日 */
    private String unyoDate;
    
    
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

    /**
     * get nhnYoteiYmdFr
     * @return the nhnYoteiYmdFr
     */
    public String getNhnYoteiYmdFr() {
        return nhnYoteiYmdFr;
    }

    /**
     * set nhnYoteiYmdFr
     * @param nhnYoteiYmdFr the nhnYoteiYmdFr to set
     */
    public void setNhnYoteiYmdFr(String nhnYoteiYmdFr) {
        this.nhnYoteiYmdFr = nhnYoteiYmdFr;
    }

    /**
     * get nhnYoteiYmdTo
     * @return the nhnYoteiYmdTo
     */
    public String getNhnYoteiYmdTo() {
        return nhnYoteiYmdTo;
    }

    /**
     * set nhnYoteiYmdTo
     * @param nhnYoteiYmdTo the nhnYoteiYmdTo to set
     */
    public void setNhnYoteiYmdTo(String nhnYoteiYmdTo) {
        this.nhnYoteiYmdTo = nhnYoteiYmdTo;
    }

    /**
     * get torihikiCd
     * @return the torihikiCd
     */
    public String getTorihikiCd() {
        return torihikiCd;
    }

    /**
     * set torihikiCd
     * @param torihikiCd the torihikiCd to set
     */
    public void setTorihikiCd(String torihikiCd) {
        this.torihikiCd = torihikiCd;
    }
}
