// /////////////////////////////////////////////////////////////////////
// Copyright(C) 2015 NEC Solution Innovators, Ltd. All Rights Reserved.
// /////////////////////////////////////////////////////////////////////

/*
 * ==================================================================== 
 * 機能名称 : HHT検品取込エラーリスト * Rev. 改版年月日 改版者名 内容 
 * 1.0 2015.06.15 NECVN 新規作成 
 * ===================================================================
 */

package jp.co.necsoft.web_md.core.app.forms.si;

import play.data.validation.Constraints.MaxLength;
import play.data.validation.Constraints.MinLength;
import play.data.validation.Constraints.Required;

/**
 * HHT検品取込エラーリストのConditionFormクラス
 * 
 */
public class Sijp7040CondForm {
    /** 会社 */
    @Required
    private String kaisyaCd;
    /** 事業部コード */
    private String jigyobuCd;
    /** 店舗コード */
    private String tenCd;
    /** 送信日 */
    @MinLength(value = 8)
    @MaxLength(value = 8)
    private String sosinNichiji;
    /** 担当者コード */
    @MaxLength(value = 9)
    private String tantoCd;
    /** 担当者名漢字 */
    @MaxLength(value = 40)
    private String tantoNm;
    /** 出力対象 */
    private String outTaisyo;

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
     * get tenCd
     * @return the tenCd
     */
    public String getTenCd() {
        return tenCd;
    }
    
    /**
     * set tenCd
     * @param tenCd the tenCd to set
     */
    public void setTenCd(String tenCd) {
        this.tenCd = tenCd;
    }

    /**
     * get sosinNichiji
     * @return the sosinNichiji
     */
    public String getSosinNichiji() {
        return sosinNichiji;
    }
    
    /**
     * set sosinNichiji
     * @param sosinNichiji the sosinNichiji to set
     */
    public void setSosinNichiji(String sosinNichiji) {
        this.sosinNichiji = sosinNichiji;
    }

    /**
     * get tantoCd
     * @return the tantoCd
     */
    public String getTantoCd() {
        return tantoCd;
    }
    
    /**
     * set tantoCd
     * @param tantoCd the tantoCd to set
     */
    public void setTantoCd(String tantoCd) {
        this.tantoCd = tantoCd;
    }

    /**
     * get tantoNm
     * @return the tantoNm
     */
    public String getTantoNm() {
        return tantoNm;
    }
    
    /**
     * set tantoNm
     * @param tantoNm the tantoNm to set
     */
    public void setTantoNm(String tantoNm) {
        this.tantoNm = tantoNm;
    }

    /**
     * get outTaisyo
     * @return the outTaisyo
     */
    public String getOutTaisyo() {
        return outTaisyo;
    }
    
    /**
     * set outTaisyo
     * @param outTaisyo the outTaisyo to set
     */
    public void setOutTaisyo(String outTaisyo) {
        this.outTaisyo = outTaisyo;
    }
}
