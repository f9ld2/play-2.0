// /////////////////////////////////////////////////////////////////////
// Copyright(C) 2015 NEC Solution Innovators, Ltd. All Rights Reserved.
// /////////////////////////////////////////////////////////////////////

/*
 * ====================================================================
 * 
 * 機能名称 :本部発注入力（店舗）
 * 
 * 改版履歴
 * 
 * Rev. 改版年月日 改版者名 内容
 * 
 * 1.0 2014/03/25 TuanTQ 新規作成
 * 
 * ===================================================================
 */
package jp.co.necsoft.web_md.core.app.forms.ha;

import play.data.validation.Constraints.MaxLength;
import play.data.validation.Constraints.MinLength;
import play.data.validation.Constraints.Required;

/**
*本部発注入力（店舗）のResultFormクラス
*
*/
public class Hajp0020CondForm {
    /**発注種類種別*/
    private String hatSruiKbn;
    
    /** JANコード */
    private String janCd;
    
    /** 便 */
    private String bin;

    /**納品日*/
    @Required
    @MinLength(value = 8)
    @MaxLength(value = 8)
    private String nhnDd;

    /**会社*/
    private String kaisyaCd;

    /**事業部*/
    private String jigyobuCd;

    /**店舗*/
    private String tenCd;

    /**部門*/
    private String bmnCd;

    /**
    * @param hatSruiKbn the hatSruiKbn to set
    */
    public void setHatSruiKbn(String hatSruiKbn) {
        this.hatSruiKbn = hatSruiKbn;
    }

    /**
     * @return the hatSruiKbn
     */
    public String getHatSruiKbn() {
        return hatSruiKbn;
    }

    /**
    * @return the nhnDd
    */
    public String getNhnDd() {
        return nhnDd;
    }

    /**
    * @param nhnDd the nhnDd to set
    */
    public void setNhnDd(String nhnDd) {
        this.nhnDd = nhnDd;
    }

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
     * @return the janCd
     */
    public String getJanCd() {
        return janCd;
    }

    /**
     * @param janCd the janCd to set
     */
    public void setJanCd(String janCd) {
        this.janCd = janCd;
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

}
