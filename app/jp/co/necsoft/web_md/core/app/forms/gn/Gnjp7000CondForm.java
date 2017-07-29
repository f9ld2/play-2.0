///////////////////////////////////////////////////////////////////////
// Copyright(C) 2015 NEC Solution Innovators, Ltd. All Rights Reserved.
///////////////////////////////////////////////////////////////////////

/* ====================================================================
 * 機能名称 : セット品設定リスト
 * 改版履歴
 * Rev. 改版年月日   改版者名       内容
 * 1.0 2015-06-24 chiennt 新規作成
 * =================================================================== */
package jp.co.necsoft.web_md.core.app.forms.gn;

import play.data.validation.Constraints.MaxLength;
import play.data.validation.Constraints.MinLength;

/**
* セット品設定リストのResultFormクラス
*
*/
public class Gnjp7000CondForm {
    
    /**会社*/
    private String kaisyaCd;
    
    /**発注日*/
    private String hatYmd;
    
    /**部門*/
    @MinLength(value = 3)
    @MaxLength(value = 3)
    private String bmnCd;

    /**中分類*/
    @MinLength(value = 4)
    @MaxLength(value = 4)
    private String chubnrCd;
    
    /**小分類*/
    @MinLength(value = 4)
    @MaxLength(value = 4)
    private String shobnrCd;
    
    /**ＪＡＮコード*/
    @MaxLength(value = 13)
    private String janCd;

    /**事業部*/
    @MinLength(value = 2)
    @MaxLength(value = 2)
    private String jigyobuCd;

    /**店舗*/
    @MinLength(value = 3)
    @MaxLength(value = 3)
    private String tenCd;

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
     * @return the hatYmd
     */
    public String getHatYmd() {
        return hatYmd;
    }

    /**
     * @param hatYmd the hatYmd to set
     */
    public void setHatYmd(String hatYmd) {
        this.hatYmd = hatYmd;
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
     * @return the chubnrCd
     */
    public String getChubnrCd() {
        return chubnrCd;
    }

    /**
     * @param chubnrCd the chubnrCd to set
     */
    public void setChubnrCd(String chubnrCd) {
        this.chubnrCd = chubnrCd;
    }

    /**
     * @return the shobnrCd
     */
    public String getShobnrCd() {
        return shobnrCd;
    }

    /**
     * @param shobnrCd the shobnrCd to set
     */
    public void setShobnrCd(String shobnrCd) {
        this.shobnrCd = shobnrCd;
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
    
}
