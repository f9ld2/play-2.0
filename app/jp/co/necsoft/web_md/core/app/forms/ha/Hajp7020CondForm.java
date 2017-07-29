// /////////////////////////////////////////////////////////////////////
// Copyright(C) 2015 NEC Solution Innovators, Ltd. All Rights Reserved.
// /////////////////////////////////////////////////////////////////////
/*
 * ====================================================================
 * 
 * 機能名称 :自動発注予定データ一覧（店舗別）
 * 
 * 改版履歴
 * 
 * Rev. 改版年月日 改版者名 内容
 * 
 * 1.0 2015-06-04 NECVN 新規作成
 * 
 * ===================================================================
 */
package jp.co.necsoft.web_md.core.app.forms.ha;

import jp.co.necsoft.web_md.core.app.forms.cm.BaseCondForm;

/**
 * 自動発注予定データ一覧（店舗別）のResultFormクラス
 *
 */
public class Hajp7020CondForm extends BaseCondForm {
    /**事業部コード**/
    private String jigyobuCd;
    /**表示区分**/
    private String hyojiKbn;

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
     * @return the hyojiKbn
     */
    public String getHyojiKbn() {
        return hyojiKbn;
    }

    /**
     * @param hyojiKbn the hyojiKbn to set
     */
    public void setHyojiKbn(String hyojiKbn) {
        this.hyojiKbn = hyojiKbn;
    }

}
