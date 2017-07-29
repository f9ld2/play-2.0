// /////////////////////////////////////////////////////////////////////
// Copyright(C) 2015 NEC Solution Innovators, Ltd. All Rights Reserved.
// /////////////////////////////////////////////////////////////////////

/*
 * ====================================================================
 * 
 * 機能名称 : 日割予算リスト
 * 
 * 改版履歴
 * 
 * Rev. 改版年月日 改版者名 内容
 * 
 * 1.0 2014/05/20 Taivd 新規作成
 * 
 * ===================================================================
 */
package jp.co.necsoft.web_md.core.app.dto.ur;

/**
 * @author phuclt
 *
 */
public class Urjp1140Dto01 {
    /** irow */
    private String irow;
    /** caldate */
    private String calDate;
    /** weekday */
    private String weekDay;
    /** bmncd */
    private String bmnCd;
    /** ysnurikin */
    private String ysnUriKin;

    /**
     * @return the irow
     */
    public String getIrow() {
        return irow;
    }

    /**
     * @param irow the irow to set
     */
    public void setIrow(String irow) {
        this.irow = irow;
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
     * @return the ysnUriKin
     */
    public String getYsnUriKin() {
        return ysnUriKin;
    }

    /**
     * @param ysnUriKin the ysnUriKin to set
     */
    public void setYsnUriKin(String ysnUriKin) {
        this.ysnUriKin = ysnUriKin;
    }

}
