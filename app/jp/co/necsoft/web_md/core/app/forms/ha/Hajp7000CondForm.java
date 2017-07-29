// /////////////////////////////////////////////////////////////////////
// Copyright(C) 2015 NEC Solution Innovators, Ltd. All Rights Reserved.
// /////////////////////////////////////////////////////////////////////

/*
 * ==================================================================== 
 * 機能名称 : HHT発注取込エラーリスト 
 * Rev. 改版年月日 改版者名 内容 
 * 1.0 2015.06.03 NECVN 新規作成 
 * ===================================================================
 */

package jp.co.necsoft.web_md.core.app.forms.ha;

import play.data.validation.Constraints.MaxLength;
import play.data.validation.Constraints.MinLength;
import play.data.validation.Constraints.Required;

/**
 * HHT発注取込エラーリストのConditionFormクラス
 * 
 */
public class Hajp7000CondForm {
    /** 会社 */
    @Required
    private String kaisyaCd;
        
    /** 事業部 */
    private String jigyobuCd;
    
    /** 店舗 */
    private String tenCd;

    /** 発注日 */
    @MinLength(value = 8)
    @MaxLength(value = 8)
    private String hatDayFr;

    /** 発注日終了 */
    @MinLength(value = 8)
    @MaxLength(value = 8)
    private String hatDayTo;
    
    /** 送信日 */
    private String sendDay;
    
    /** 担当者コード */
    @MaxLength(value = 9)
    private String tantoCd;
    
    /** 担当者名漢字 */
    @MaxLength(value = 40)
    private String tantoNm;

    /** 出力対象 */
    private String outTaisyo;

    /**
     * get Kaisya Code
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
     * get Ten Code
     * 
     * @return the tenCd
     */
    public String getTenCd() {
        return tenCd;
    }

    /**
     * set Ten Cd
     * 
     * @param tenCd the tenCd to set
     */
    public void setTenCd(String tenCd) {
        this.tenCd = tenCd;
    }

    /**
     * get hatDay From
     * 
     * @return the hatDayFr
     */
    public String getHatDayFr() {
        return hatDayFr;
    }

    /**
     * set HatDay From
     * 
     * @param hatDayFr the hatDayFr to set
     */
    public void setHatDayFr(String hatDayFr) {
        this.hatDayFr = hatDayFr;
    }

    /**
     * get HatDay To
     * 
     * @return the hatDayTo
     */
    public String getHatDayTo() {
        return hatDayTo;
    }

    /**
     * set HatDay To
     * 
     * @param hatDayTo the hatDayTo to set
     */
    public void setHatDayTo(String hatDayTo) {
        this.hatDayTo = hatDayTo;
    }

    /**
     * get Send Day
     * 
     * @return the sendDay
     */
    public String getSendDay() {
        return sendDay;
    }

    /**
     * set Send Day
     * 
     * @param sendDay the sendDay to set
     */
    public void setSendDay(String sendDay) {
        this.sendDay = sendDay;
    }

    /**
     * get Tanto Code
     * 
     * @return the tantoCd
     */
    public String getTantoCd() {
        return tantoCd;
    }

    /**
     * set Tanto Code
     * 
     * @param tantoCd the tantoCd to set
     */
    public void setTantoCd(String tantoCd) {
        this.tantoCd = tantoCd;
    }

    /**
     * get Tanto Name
     * 
     * @return the tantoNm
     */
    public String getTantoNm() {
        return tantoNm;
    }

    /**
     * set Tanto Name
     * 
     * @param tantoNm the tantoNm to set
     */
    public void setTantoNm(String tantoNm) {
        this.tantoNm = tantoNm;
    }

    /**
     * get Out Taisyo
     * 
     * @return the outTaisyo
     */
    public String getOutTaisyo() {
        return outTaisyo;
    }

    /**
     * set Out Taisyo
     * 
     * @param outTaisyo the outTaisyo to set
     */
    public void setOutTaisyo(String outTaisyo) {
        this.outTaisyo = outTaisyo;
    }

    

}
