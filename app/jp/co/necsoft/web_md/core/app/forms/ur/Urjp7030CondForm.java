///////////////////////////////////////////////////////////////////////
// Copyright(C) 2015 NEC Solution Innovators, Ltd. All Rights Reserved.
///////////////////////////////////////////////////////////////////////

/* ====================================================================
 * 機能名称 : 修理品明細一覧
 * 改版履歴
 * Rev. 改版年月日   改版者名       内容
 * 1.0 2015-06-23 chiennt 新規作成
 * =================================================================== */
package jp.co.necsoft.web_md.core.app.forms.ur;

import play.data.validation.Constraints.MaxLength;
import play.data.validation.Constraints.MinLength;

/**
* 修理品明細一覧のResultFormクラス
*
*/
public class Urjp7030CondForm {
    
    /**会社*/
    private String kaisyaCd;

    /**事業部*/
    @MinLength(value = 2)
    @MaxLength(value = 2)
    private String jigyobuCd;

    /**店舗*/
    @MinLength(value = 3)
    @MaxLength(value = 3)
    private String tenCd;

    /**対象日開始*/
    private String calDateSt;

    /**対象日終了*/
    private String calDateEd;

    /**伝票区分*/
    @MinLength(value = 2)
    @MaxLength(value = 2)
    private String dpyKbn;
    
    /**手書入力区分*/
    @MinLength(value = 2)
    @MaxLength(value = 2)
    private String idoDenKbn;

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
    * @return the calDateSt
    */
    public String getCalDateSt() {
        return calDateSt;
    }

    /**
    * @param calDateSt the calDateSt to set
    */
    public void setCalDateSt(String calDateSt) {
        this.calDateSt = calDateSt;
    }

    /**
    * @return the calDateEd
    */
    public String getCalDateEd() {
        return calDateEd;
    }

    /**
    * @param calDateEd the calDateEd to set
    */
    public void setCalDateEd(String calDateEd) {
        this.calDateEd = calDateEd;
    }

    /**
     * @return the dpyKbn
     */
    public String getDpyKbn() {
        return dpyKbn;
    }

    /**
     * @param dpyKbn the dpyKbn to set
     */
    public void setDpyKbn(String dpyKbn) {
        this.dpyKbn = dpyKbn;
    }

    /**
     * @return the idoDenKbn
     */
    public String getIdoDenKbn() {
        return idoDenKbn;
    }

    /**
     * @param idoDenKbn the idoDenKbn to set
     */
    public void setIdoDenKbn(String idoDenKbn) {
        this.idoDenKbn = idoDenKbn;
    }

}
