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
package jp.co.necsoft.web_md.core.app.forms.ha;

import play.data.validation.Constraints.MaxLength;
import play.data.validation.Constraints.MinLength;

/**
* HHT基準在庫取込エラーリスト のCondFormクラス
*
*/
public class Hajp7040CondForm {
    /** 会社コード **/
    private String kaisyaCd;
    
    /** 事業部コード */
    @MaxLength(value = 2)
    @MinLength(value = 2)
    private String jigyobuCd;
    
    /** 店舗コード */
    @MaxLength(value = 3)
    @MinLength(value = 3)
    private String tenCd;
    
    /** 送信日 */
    @MaxLength(value = 8)
    @MinLength(value = 8)
    private String sendDay;
    
    /** 担当者コード */
    @MaxLength(value = 9)
    private String tantoCd;
    
    /** 担当者名漢字 */
    private String tantoNm;
    
    /** 出力対象 */
    private String outTaisho;

    /**
     * @return the kaisyaCd
     */
    public String getKaisyaCd() {
        return kaisyaCd;
    }

    /**
     * @param kaisyaCd the kaisyaCd to set
     */
    public void setKaisyaCd(String kaisyaCd) {
        this.kaisyaCd = kaisyaCd;
    }

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
     * @return the tantoNm
     */
    public String getTantoNm() {
        return tantoNm;
    }

    /**
     * @param tantoNm the tantoNm to set
     */
    public void setTantoNm(String tantoNm) {
        this.tantoNm = tantoNm;
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

