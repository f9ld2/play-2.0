// /////////////////////////////////////////////////////////////////////
// Copyright(C) 2015 NEC Solution Innovators, Ltd. All Rights Reserved.
// /////////////////////////////////////////////////////////////////////
/*
 * ====================================================================
 * 
 * 機能名称 :委託精算確認
 * 
 * 改版履歴
 * 
 * Rev. 改版年月日 改版者名 内容
 * 
 * 1.0 2015-06-04 NECVN 新規作成
 * 
 * ===================================================================
 */
package jp.co.necsoft.web_md.core.app.forms.si;

import jp.co.necsoft.web_md.core.app.forms.cm.BaseCondForm;
import play.data.validation.Constraints.MaxLength;
import play.data.validation.Constraints.MinLength;

/**
 * 委託精算確認のCondFormクラス
 *
 */
public class Sijp7100CondForm extends BaseCondForm {
    /**事業部コード**/
    private String jigyoubuCd;
    /**店舗コード**/
    private String tenCd;
    /**対象年**/
    private String taisyoY;
    /**対象月**/
    private String taisyoM;
    /**取引先コード**/
    @MaxLength(value = 9)
    @MinLength(value = 9)
    private String triCd;
    /**
     * get jigyoubuCd
     * @return the jigyoubuCd
     */
    public String getJigyoubuCd() {
        return jigyoubuCd;
    }
    /**
     * set jigyoubuCd
     * @param jigyoubuCd the jigyoubuCd to set
     */
    public void setJigyoubuCd(String jigyoubuCd) {
        this.jigyoubuCd = jigyoubuCd;
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
     * get taisyoY
     * @return the taisyoY
     */
    public String getTaisyoY() {
        return taisyoY;
    }
    /**
     * set taisyoY
     * @param taisyoY the taisyoY to set
     */
    public void setTaisyoY(String taisyoY) {
        this.taisyoY = taisyoY;
    }
    /**
     * get taisyoM
     * @return the taisyoM
     */
    public String getTaisyoM() {
        return taisyoM;
    }
    /**
     * set taisyoM
     * @param taisyoM the taisyoM to set
     */
    public void setTaisyoM(String taisyoM) {
        this.taisyoM = taisyoM;
    }
    /**
     * get triCd
     * @return the triCd
     */
    public String getTriCd() {
        return triCd;
    }
    /**
     * set triCd
     * @param triCd the triCd to set
     */
    public void setTriCd(String triCd) {
        this.triCd = triCd;
    }
}
