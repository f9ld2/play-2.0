// /////////////////////////////////////////////////////////////////////
// Copyright(C) 2015 NEC Solution Innovators, Ltd. All Rights Reserved.
// /////////////////////////////////////////////////////////////////////

/*
 * ==================================================================== 
 * 機能名称 : 委託精算確認
 * 改版履歴 
 * Rev. 改版年月日 改版者名 内容 
 * 1.0 2015-06-04 trieuvn 新規作成
 * ===================================================================
 */

package jp.co.necsoft.web_md.core.app.dto.si;



/**
 * 委託精算確認のParamクラス
 * 
 */
public class Sijp7100Param {
    /**会社コード**/
    private String kaisyaCd;
    /**事業部コード**/
    private String jigyobuCd;
    /**店舗コード**/
    private String tenCd;
    /**対象年**/
    private String taisyoY;
    /**対象月**/
    private String taisyoM;
    /**取引先コード**/
    private String triCd;
    /** 運用日付 */
    private String unyoDate;
    /**最大表示明細数**/
    private String maxRecordNumber;
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
     * @param jigyobuCd the jigyoubuCd to set
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
     * get taisyoY
     * @return the taisyoY
     */
    public String getTaisyoY() {
        return taisyoY;
    }
    /**
     * set taisyoY
     * @param taisyoY the taisyoY to set
     */
    public void setTaisyoY(String taisyoY) {
        this.taisyoY = taisyoY;
    }
    /**
     * get taisyoM
     * @return the taisyoM
     */
    public String getTaisyoM() {
        return taisyoM;
    }
    /**
     * set taisyoM
     * @param taisyoM the taisyoM to set
     */
    public void setTaisyoM(String taisyoM) {
        this.taisyoM = taisyoM;
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
     * get maxRecordNumber
     * @return the maxRecordNumber
     */
    public String getMaxRecordNumber() {
        return maxRecordNumber;
    }
    /**
     * set maxRecordNumber
     * @param maxRecordNumber the maxRecordNumber to set
     */
    public void setMaxRecordNumber(String maxRecordNumber) {
        this.maxRecordNumber = maxRecordNumber;
    }

}
