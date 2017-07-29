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
 * M019ymdmU001ybmnU012bmnjResultのDtoクラス
 * 
 */
public class M019ymdmU001ybmnU012bmnjResult {
    /**年月日*/
    private String dt;

    /**曜日*/
    private String weekDay;

    /**売上*/
    private Long ysnKin;

    /**金額*/
    private Long sakKin;

    /**客数*/
    private Long ysnKyaku;

    /**客数*/
    private Long sakKyaku;

    /**
     * @return the dt
     */
    public String getDt() {
        return dt;
    }

    /**
     * @param dt the dt to set
     */
    public void setDt(String dt) {
        this.dt = dt;
    }

    /**
     * @return the weekDay
     */
    public String getWeekDay() {
        return weekDay;
    }

    /**
     * @param weekDay the weekDay to set
     */
    public void setWeekDay(String weekDay) {
        this.weekDay = weekDay;
    }

    /**
     * @return the ysnKin
     */
    public Long getYsnKin() {
        return ysnKin;
    }

    /**
     * @param ysnKin the ysnKin to set
     */
    public void setYsnKin(Long ysnKin) {
        this.ysnKin = ysnKin;
    }

    /**
     * @return the sakKin
     */
    public Long getSakKin() {
        return sakKin;
    }

    /**
     * @param sakKin the sakKin to set
     */
    public void setSakKin(Long sakKin) {
        this.sakKin = sakKin;
    }

    /**
     * @return the ysnKyaku
     */
    public Long getYsnKyaku() {
        return ysnKyaku;
    }

    /**
     * @param ysnKyaku the ysnKyaku to set
     */
    public void setYsnKyaku(Long ysnKyaku) {
        this.ysnKyaku = ysnKyaku;
    }

    /**
     * @return the sakKyaku
     */
    public Long getSakKyaku() {
        return sakKyaku;
    }

    /**
     * @param sakKyaku the sakKyaku to set
     */
    public void setSakKyaku(Long sakKyaku) {
        this.sakKyaku = sakKyaku;
    }

}
