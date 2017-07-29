// /////////////////////////////////////////////////////////////////////
// Copyright(C) 2015 NEC Solution Innovators, Ltd. All Rights Reserved.
// /////////////////////////////////////////////////////////////////////

/*
 * ==================================================================== 
 * 機能名称 : オリジナル商品品振確定指示画面
 * 改版履歴 
 * Rev. 改版年月日 改版者名 内容 
 * 1.0 2015.06.04 NECVN 新規作成
 * ===================================================================
 */

package jp.co.necsoft.web_md.core.app.dto.si;


/**
 * オリジナル商品品振確定指示画面のParamクラス
 * 
 */
public class Sijp7080Param  {
    /** 会社 */
    private String kaisyaCd;
        
    /**発注日**/
    private String hatDayFr;
    
    /**発注日終了**/
    private String hatDayTo;
    
    /**事業部コード**/
    private String jigyobuCd;
    
    /**承認区分**/
    private String shouninKbn;

    /** 運用日付 */
    private String unyoDate;
    
    /** 最大表示明細数 */
    private String maxRecordNumber;
    
    /** JANコード */
    private String janCd;

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

    /**
     * get hatDayFr
     * @return the hatDayFr
     */
    public String getHatDayFr() {
        return hatDayFr;
    }

    /**
     * set hatDayFr
     * @param hatDayFr the hatDayFr to set
     */
    public void setHatDayFr(String hatDayFr) {
        this.hatDayFr = hatDayFr;
    }

    /**
     * get hatDayTo
     * @return the hatDayTo
     */
    public String getHatDayTo() {
        return hatDayTo;
    }

    /**
     * set hatDayTo
     * @param hatDayTo the hatDayTo to set
     */
    public void setHatDayTo(String hatDayTo) {
        this.hatDayTo = hatDayTo;
    }

    /**
     * get shouninKbn
     * @return the shouninKbn
     */
    public String getShouninKbn() {
        return shouninKbn;
    }

    /**
     * set shouninKbn
     * @param shouninKbn the shouninKbn to set
     */
    public void setShouninKbn(String shouninKbn) {
        this.shouninKbn = shouninKbn;
    }

    /**
     * get janCd
     * @return the janCd
     */
    public String getJanCd() {
        return janCd;
    }

    /**
     * set janCd
     * @param janCd the janCd to set
     */
    public void setJanCd(String janCd) {
        this.janCd = janCd;
    }

}
