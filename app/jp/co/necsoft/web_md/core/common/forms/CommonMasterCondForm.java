///////////////////////////////////////////////////////////////////////
// Copyright(C) 2015 NEC Solution Innovators, Ltd. All Rights Reserved.
///////////////////////////////////////////////////////////////////////

/* ====================================================================
 * 機能名称 : 店舗マスタメンテナンス
 * 改版履歴
 * Rev. 改版年月日   改版者名       内容
 * 1.0   2014.02.18   HaiNP      新規作成
 * =================================================================== */
package jp.co.necsoft.web_md.core.common.forms;

import play.data.validation.Constraints.MaxLength;
import play.data.validation.Constraints.MinLength;
import play.data.validation.Constraints.Required;

/** CommonMasterCondForm */
public class CommonMasterCondForm {

    /** 発効日 */
    @Required
    @MaxLength(value = 8)
    @MinLength(value = 8)
    private String hakkoDay;
    
    /** Del Exist */
    private String ccDelexist;
    
    /** 検索 flg */
    private String searchFlg;

    /**
     * @return the hakkoDay
     */
    public String getHakkoDay() {
        return hakkoDay;
    }

    /**
     * @param hakkoDay
     *            the hakkoDay to set
     */
    public void setHakkoDay(String hakkoDay) {
        this.hakkoDay = hakkoDay;
    }

    /**
     * @return the ccDelexist
     */
    public String getCcDelexist() {
        return ccDelexist;
    }

    /**
     * @param ccDelexist
     *            the ccDelexist to set
     */
    public void setCcDelexist(String ccDelexist) {
        this.ccDelexist = ccDelexist;
    }

    /**
     * @return the searchFlg
     */
    public String getSearchFlg() {
        return searchFlg;
    }

    /**
     * @param searchFlg the searchFlg to set
     */
    public void setSearchFlg(String searchFlg) {
        this.searchFlg = searchFlg;
    }
    
}
