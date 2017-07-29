// /////////////////////////////////////////////////////////////////////
// Copyright(C) 2015 NEC Solution Innovators, Ltd. All Rights Reserved.
// /////////////////////////////////////////////////////////////////////

/*
 * ====================================================================
 * 
 * 機能名称 :日別予算入力画面
 * 
 * 改版履歴
 * 
 * Rev. 改版年月日 改版者名 内容
 * 
 * 1.0 2014.04.10 TuanTQ 新規作成
 * 
 * ===================================================================
 */

package jp.co.necsoft.web_md.core.app.dto.ur;

/**
 * M019ymdmU001ybmnU012bmnjParamのDtoクラス
 * 
 */
public class M019ymdmU001ybmnU012bmnjParam {
    /**ysnDate*/
    private String ysnDate;

    /**店舗コード*/
    private String tenCd;

    /**部門コード*/
    private String bmnCd;

    /**対象外部門コード*/
    private String msExceptBmn;

    /**日付*/
    private String calDate;

    /**ysnYyyymm*/
    private String ysnYyyymm;

    /**発行日*/
    private String hakkoDay;

    /**
     * @return the ysnDate
     */
    public String getYsnDate() {
        return ysnDate;
    }

    /**
     * @param ysnDate the ysnDate to set
     */
    public void setYsnDate(String ysnDate) {
        this.ysnDate = ysnDate;
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
     * @return the bmnCd
     */
    public String getBmnCd() {
        return bmnCd;
    }

    /**
     * @param bmnCd the bmnCd to set
     */
    public void setBmnCd(String bmnCd) {
        this.bmnCd = bmnCd;
    }

    /**
     * @return the calDate
     */
    public String getCalDate() {
        return calDate;
    }

    /**
     * @param calDate the calDate to set
     */
    public void setCalDate(String calDate) {
        this.calDate = calDate;
    }

    /**
     * @return the msExceptBmn
     */
    public String getMsExceptBmn() {
        return msExceptBmn;
    }

    /**
     * @param msExceptBmn the msExceptBmn to set
     */
    public void setMsExceptBmn(String msExceptBmn) {
        this.msExceptBmn = msExceptBmn;
    }

    /**
     * @return the ysnYyyymm
     */
    public String getYsnYyyymm() {
        return ysnYyyymm;
    }

    /**
     * @param ysnYyyymm the ysnYyyymm to set
     */
    public void setYsnYyyymm(String ysnYyyymm) {
        this.ysnYyyymm = ysnYyyymm;
    }

    /**
     * @return the hakkoDay
     */
    public String getHakkoDay() {
        return hakkoDay;
    }

    /**
     * @param hakkoDay the hakkoDay to set
     */
    public void setHakkoDay(String hakkoDay) {
        this.hakkoDay = hakkoDay;
    }

}
