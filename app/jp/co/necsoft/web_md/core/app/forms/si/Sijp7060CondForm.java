// /////////////////////////////////////////////////////////////////////
// Copyright(C) 2015 NEC Solution Innovators, Ltd. All Rights Reserved.
// /////////////////////////////////////////////////////////////////////

/*
 * ==================================================================== 
 * 機能名称 : HHT品振取込エラーリスト 
 * Rev. 改版年月日 改版者名 内容 
 * 1.0 2015.06.05 NECVN 新規作成 
 * ===================================================================
 */

package jp.co.necsoft.web_md.core.app.forms.si;

import play.data.validation.Constraints.MaxLength;
import play.data.validation.Constraints.MinLength;
import play.data.validation.Constraints.Required;

/**
 * HHT発注取込エラーリストのConditionFormクラス
 * 
 */
public class Sijp7060CondForm {
    /** 会社 */
    @Required
    private String kaisyaCd;
    /** 事業部 */
    private String outJigyobuCd;
    /** 店舗 */
    private String tenCd;
    /** 作業（送信）日 */
    @MinLength(value = 8)
    @MaxLength(value = 8)
    private String sosinNichiji;
    /** 担当者 */
    @MaxLength(value = 9)
    private String tantoCd;
    /** 対象 */
    private String outTaisyo;
    
    /**
     * get KaisyaCd
     * @return the kaisyaCd
     */
    public String getKaisyaCd() {
        return kaisyaCd;
    }
    
    /**
     * set KaisyaCd
     * @param kaisyaCd the kaisyaCd to set
     */
    public void setKaisyaCd(String kaisyaCd) {
        this.kaisyaCd = kaisyaCd;
    }
    
    /**
     * get outJigyobuCd
     * @return the outJigyobuCd
     */
    public String getOutJigyobuCd() {
        return outJigyobuCd;
    }
    
    /**
     * set outJigyobuCd
     * @param outJigyobuCd the outJigyobuCd to set
     */
    public void setOutJigyobuCd(String outJigyobuCd) {
        this.outJigyobuCd = outJigyobuCd;
    }
    
    /**
     * get TenCd
     * @return the tenCd
     */
    public String getTenCd() {
        return tenCd;
    }
    
    /**
     * set TenCd
     * @param tenCd the tenCd to set
     */
    public void setTenCd(String tenCd) {
        this.tenCd = tenCd;
    }
    
    /**
     * get TantoCd
     * @return the tantoCd
     */
    public String getTantoCd() {
        return tantoCd;
    }
    
    /**
     * set TantoCd
     * @param tantoCd the tantoCd to set
     */
    public void setTantoCd(String tantoCd) {
        this.tantoCd = tantoCd;
    }
    
    /**
     * get OutTaisyo
     * @return the outTaisyo
     */
    public String getOutTaisyo() {
        return outTaisyo;
    }
    
    /**
     * set OutTaisyo
     * @param outTaisyo the outTaisyo to set
     */
    public void setOutTaisyo(String outTaisyo) {
        this.outTaisyo = outTaisyo;
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
}
