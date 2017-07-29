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
 * 1.0 2015/06/16 NECVN 新規作成
 * 
 * ===================================================================
 */
package jp.co.necsoft.web_md.core.app.forms.ha;

import play.data.validation.Constraints.MaxLength;

/**
*本部発注入力（店舗）のGridDataクラス
*
*/
public class Hajp0020GridData {
    /** 備考*/
    @MaxLength(value = 40)
    private String remarks;

    /**
     * @return the remarks
     */
    public String getRemarks() {
        return remarks;
    }

    /**
     * @param remarks the remarks to set
     */
    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
}
