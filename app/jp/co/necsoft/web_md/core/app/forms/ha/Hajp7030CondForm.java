// /////////////////////////////////////////////////////////////////////
// Copyright(C) 2015 NEC Solution Innovators, Ltd. All Rights Reserved.
// /////////////////////////////////////////////////////////////////////
/*
 * ====================================================================
 * 
 * 機能名称 :自動発注予定データ一覧（店舗商品別）
 * 
 * 改版履歴
 * 
 * Rev. 改版年月日 改版者名 内容
 * 
 * 1.0 2015-06-02 trieuvn 新規作成
 * 
 * ===================================================================
 */
package jp.co.necsoft.web_md.core.app.forms.ha;

import jp.co.necsoft.web_md.core.app.forms.cm.BaseCondForm;
import play.data.validation.Constraints.MaxLength;
import play.data.validation.Constraints.MinLength;

/**
 * 自動発注予定データ一覧（店舗商品別）のResultFormクラス
 *
 */
public class Hajp7030CondForm extends BaseCondForm {
    /**事業部コード**/
    private String jigyobuCd;
    /**店舗**/
    private String tenCd;
    /**取引先**/
    @MaxLength(value = 9)
    @MinLength(value = 9)
    private String triCd;

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
     * @return the triCd
     */
    public String getTriCd() {
        return triCd;
    }

    /**
     * @param triCd the triCd to set
     */
    public void setTriCd(String triCd) {
        this.triCd = triCd;
    }

}
