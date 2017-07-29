// /////////////////////////////////////////////////////////////////////
// Copyright(C) 2015 NEC Solution Innovators, Ltd. All Rights Reserved.
// /////////////////////////////////////////////////////////////////////

/*
 * ==================================================================== 
 * 機能名称 : デモ棚リスト
 * 改版履歴 
 * Rev. 改版年月日 改版者名 内容 
 * 1.0 2015.06.03 NECVN 新規作成 
 * ===================================================================
 */

package jp.co.necsoft.web_md.core.app.dto.ur;

/**
 * デモ棚リストのParamクラス
 * 
 */
public class Urjp7030Param {
    
    /**事業部*/
    private String jigyobuCd;

    /**店舗*/
    private String tenCd;

    /**対象日開始*/
    private String calDateSt;

    /**対象日終了*/
    private String calDateEd;

    /**伝票区分*/
    private String dpyKbn;
    
    /**手書入力区分*/
    private String idoDenKbn;
    
    /** 運用日 */
    private String unyoDate;
    
    /**商品コード*/
    private String shnCd;
    
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
    /**
     * @return the calDateSt
     */
    public String getCalDateSt() {
        return calDateSt;
    }
    /**
     * @param calDateSt the calDateSt to set
     */
    public void setCalDateSt(String calDateSt) {
        this.calDateSt = calDateSt;
    }
    /**
     * @return the calDateEd
     */
    public String getCalDateEd() {
        return calDateEd;
    }
    /**
     * @param calDateEd the calDateEd to set
     */
    public void setCalDateEd(String calDateEd) {
        this.calDateEd = calDateEd;
    }
    /**
     * @return the dpyKbn
     */
    public String getDpyKbn() {
        return dpyKbn;
    }
    /**
     * @param dpyKbn the dpyKbn to set
     */
    public void setDpyKbn(String dpyKbn) {
        this.dpyKbn = dpyKbn;
    }
    /**
     * @return the idoDenKbn
     */
    public String getIdoDenKbn() {
        return idoDenKbn;
    }
    /**
     * @param idoDenKbn the idoDenKbn to set
     */
    public void setIdoDenKbn(String idoDenKbn) {
        this.idoDenKbn = idoDenKbn;
    }
    /**
     * @return the unyoDate
     */
    public String getUnyoDate() {
        return unyoDate;
    }
    /**
     * @param unyoDate the unyoDate to set
     */
    public void setUnyoDate(String unyoDate) {
        this.unyoDate = unyoDate;
    }
    /**
     * @return the shnCd
     */
    public String getShnCd() {
        return shnCd;
    }
    /**
     * @param shnCd the shnCd to set
     */
    public void setShnCd(String shnCd) {
        this.shnCd = shnCd;
    }
    
}
