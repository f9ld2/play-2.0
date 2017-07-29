// /////////////////////////////////////////////////////////////////////
// Copyright(C) 2015 NEC Solution Innovators, Ltd. All Rights Reserved.
// /////////////////////////////////////////////////////////////////////

/*
 * ==================================================================== 
 * 機能名称 : HHT基準在庫取込エラーリスト 
 * 改版履歴 
 * Rev. 改版年月日 改版者名 内容
 * 1.0 2015.06.02 LocHV 新規作成
 *  ===================================================================
 */

package jp.co.necsoft.web_md.core.app.dto.ha;


/**
 * HHT基準在庫取込エラーリスト のParamクラス
 * 
 */
public class Hajp7040Param {
        
    /** 事業部 */
    private String jigyobuCd;

    /** 店舗 */
    private String tenCd;

    /** 作業（送信）日 */
    private String sendDay;

    /** 担当者*/
    private String tantoCd;

    /** 対象 */
    private String outTaisho;

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
     * @return the sendDay
     */
    public String getSendDay() {
        return sendDay;
    }

    /**
     * @param sendDay the sendDay to set
     */
    public void setSendDay(String sendDay) {
        this.sendDay = sendDay;
    }

    /**
     * @return the tantoCd
     */
    public String getTantoCd() {
        return tantoCd;
    }

    /**
     * @param tantoCd the tantoCd to set
     */
    public void setTantoCd(String tantoCd) {
        this.tantoCd = tantoCd;
    }

    /**
     * @return the outTaisho
     */
    public String getOutTaisho() {
        return outTaisho;
    }

    /**
     * @param outTaisho the outTaisho to set
     */
    public void setOutTaisho(String outTaisho) {
        this.outTaisho = outTaisho;
    }
    
}
