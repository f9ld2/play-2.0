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
 * M006tenmM016tanmResultのDtoクラス
 * 
 */
public class U002ybmnM003bmnmM004tbumResult {
    /** 発行日 */
    private String hakkoDay;
    /** 部門コード */
    private String lsBmnCd;
    /** 店コード */
    private String tenCd;
    /** 部門コード */
    private String bmnCd;
    /**ysnYyyymm*/
    private String ysnYyyymm;

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

    /**
     * @return the lsBmnCd
     */
    public String getLsBmnCd() {
        return lsBmnCd;
    }

    /**
     * @param lsBmnCd the lsBmnCd to set
     */
    public void setLsBmnCd(String lsBmnCd) {
        this.lsBmnCd = lsBmnCd;
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

}
