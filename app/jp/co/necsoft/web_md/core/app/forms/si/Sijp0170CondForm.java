// /////////////////////////////////////////////////////////////////////
// Copyright(C) 2015 NEC Solution Innovators, Ltd. All Rights Reserved.
// /////////////////////////////////////////////////////////////////////

/*
 * ====================================================================
 * 
 * 機能名称 : ＥＯＳ伝票完納入力
 * 
 * 改版履歴
 * 
 * Rev. 改版年月日 改版者名 内容
 * 
 * 1.0 2014-04-05 TUCTV 新規作成
 * 
 * ===================================================================
 */

package jp.co.necsoft.web_md.core.app.forms.si;

import play.data.validation.Constraints.MaxLength;
import play.data.validation.Constraints.MinLength;

/**
 * 
 * Sijp0170CondForm
 *
 */
public class Sijp0170CondForm {
    /**納品ルート**/
    @MinLength(value = 1)
    @MaxLength(value = 1)
    private String ruteCd;
    
    /**納品日**/
    @MinLength(value = 8)
    @MaxLength(value = 8)
    private String inpNohinDate;
    
    /**会社コード**/
    @MinLength(value = 2)
    @MaxLength(value = 2)
    private String kaisyaCd;
    
    /**事業部コード**/
    @MinLength(value = 2)
    @MaxLength(value = 2)
    private String jigyobuCd;
    
    /**店舗コード**/
    @MinLength(value = 3)
    @MaxLength(value = 3)
    private String tenCd;

    /**
     * @return the ruteCd
     */
    public String getRuteCd() {
        return ruteCd;
    }

    /**
     * @param ruteCd the ruteCd to set
     */
    public void setRuteCd(String ruteCd) {
        this.ruteCd = ruteCd;
    }

    /**
     * @return the inpNohinDate
     */
    public String getInpNohinDate() {
        return inpNohinDate;
    }

    /**
     * @param inpNohinDate the inpNohinDate to set
     */
    public void setInpNohinDate(String inpNohinDate) {
        this.inpNohinDate = inpNohinDate;
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
}
