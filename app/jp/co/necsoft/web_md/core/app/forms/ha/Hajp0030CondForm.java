// /////////////////////////////////////////////////////////////////////
// Copyright(C) 2015 NEC Solution Innovators, Ltd. All Rights Reserved.
// /////////////////////////////////////////////////////////////////////

/*
 * ====================================================================
 * 
 * 機能名称 : 物流スケジュールメンテ
 * 
 * 改版履歴
 * 
 * Rev. 改版年月日 改版者名 内容
 * 
 * 1.0 2014.04.18 ToanPQ 新規作成
 * 
 * ===================================================================
 */
package jp.co.necsoft.web_md.core.app.forms.ha;

/**
*物流スケジュールマスタメンテのCondFormクラス
*
*/
public class Hajp0030CondForm {
    /**発注年月*/
    private String yyyy;

    /**発注*/
    private String mm;

    /**取引先*/
    private String triCd;

    /**便NO*/
    private String bin;

    /**検索してから編集(F9)のモード*/
    private boolean searchEditMode;

    /**
     * @return the yyyy
     */
    public String getYyyy() {
        return yyyy;
    }

    /**
     * @param yyyy the yyyy to set
     */
    public void setYyyy(String yyyy) {
        this.yyyy = yyyy;
    }

    /**
     * @return the mm
     */
    public String getMm() {
        return mm;
    }

    /**
     * @param mm the mm to set
     */
    public void setMm(String mm) {
        this.mm = mm;
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
     * @return the bin
     */
    public String getBin() {
        return bin;
    }

    /**
     * @param bin the bin to set
     */
    public void setBin(String bin) {
        this.bin = bin;
    }

    /**
     * @return the searchEditMode
     */
    public boolean isSearchEditMode() {
        return searchEditMode;
    }

    /**
     * @param searchEditMode the searchEditMode to set
     */
    public void setSearchEditMode(boolean searchEditMode) {
        this.searchEditMode = searchEditMode;
    }

}
