// /////////////////////////////////////////////////////////////////////
// Copyright(C) 2015 NEC Solution Innovators, Ltd. All Rights Reserved.
// /////////////////////////////////////////////////////////////////////

/*
 * ==================================================================== 
 * 機能名称 : 自動発注予定データ一覧（店舗商品別）
 * 改版履歴 
 * Rev. 改版年月日 改版者名 内容 
 * 1.0 2015-06-04 trieuvn 新規作成
 * ===================================================================
 */

package jp.co.necsoft.web_md.core.app.dto.ha;


/**
 * 自動発注予定データ一覧（店舗商品別）のParamクラス
 * 
 */
public class Hajp7030Param {
    /** 会社 */
    private String kaisyaCd;
        
    /** 事業部 */
    private String jigyobuCd;

    /** 店舗 */
    private String tenCd;

    /** 取引先 */
    private String triCd;

    /** 運用日付 */
    private String unyoDate;

    /** 最大表示明細数 */
    private Integer maxRecordNumber;

    /**
     * get Kaisya Code
     * 
     * @return the kaisyaCd
     */
    public String getKaisyaCd() {
        return kaisyaCd;
    }

    /**
     * set Kaisya Code
     * @param kaisyaCd the
     *            kaisyaCd to set
     */
    public void setKaisyaCd(String kaisyaCd) {
        this.kaisyaCd = kaisyaCd;
    }
    
    /**
     * get Jigyobu Code
     * 
     * @return the jigyobuCd
     */
    public String getJigyobuCd() {
        return jigyobuCd;
    }

    /**
     * set Jigyobu Code
     * 
     * @param jigyobuCd the jigyobuCd to set
     */
    public void setJigyobuCd(String jigyobuCd) {
        this.jigyobuCd = jigyobuCd;
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
     * @return the triCd
     */
    public String getTriCd() {
        return triCd;
    }

    /**
     * @param triCd the triCd to set
     */
    public void setTriCd(String triCd) {
        this.triCd = triCd;
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
     * @return the maxRecordNumber
     */
    public Integer getMaxRecordNumber() {
        return maxRecordNumber;
    }

    /**
     * @param maxRecordNumber the maxRecordNumber to set
     */
    public void setMaxRecordNumber(Integer maxRecordNumber) {
        this.maxRecordNumber = maxRecordNumber;
    }

}
