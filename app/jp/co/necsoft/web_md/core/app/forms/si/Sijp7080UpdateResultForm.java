// /////////////////////////////////////////////////////////////////////
// Copyright(C) 2015 NEC Solution Innovators, Ltd. All Rights Reserved.
// /////////////////////////////////////////////////////////////////////

/*
 * ====================================================================
 * 
 * 機能名称 : オリジナル商品品振確定指示画面
 * 
 * 改版履歴
 * 
 * Rev. 改版年月日 改版者名 内容
 * 
 * 1.0 2014-04-05 NECVN 新規作成
 * 
 * ===================================================================
 */

package jp.co.necsoft.web_md.core.app.forms.si;

import java.util.List;

import jp.co.necsoft.web_md.core.app.dto.si.Sijp7080ResultRecord;

/**
*オリジナル商品品振確定指示画面のResultFormクラス
*
*/
public class Sijp7080UpdateResultForm {
    /**会社コード**/
    private String kaisyaCd;
    
    /**発注日**/
    private String hatDayFr;
    
    /**発注日終了**/
    private String hatDayTo;
    
    /**事業部コード**/
    private String jigyobuCd;
    
    /**承認区分**/
    private String shouninKbn;
    /**店舗行*/
    private List<Sijp7080ResultRecord> records;
    
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
     * get records
     * @return the records
     */
    public List<Sijp7080ResultRecord> getRecords() {
        return records;
    }
    
    /**
     * set records
     * @param records the records to set
     */
    public void setRecords(List<Sijp7080ResultRecord> records) {
        this.records = records;
    }
    
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
    
}
