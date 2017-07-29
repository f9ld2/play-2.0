// /////////////////////////////////////////////////////////////////////
// Copyright(C) 2015 NEC Solution Innovators, Ltd. All Rights Reserved.
// /////////////////////////////////////////////////////////////////////

/*
 * ====================================================================
 * 
 * 機能名称 :買掛金元帳
 * 
 * 改版履歴
 * 
 * Rev. 改版年月日 改版者名 内容
 * 
 * 1.0 2014-05-22 TUCTVZ 新規作成
 * 
 * ===================================================================
 */
package jp.co.necsoft.web_md.core.app.forms.kk;

import jp.co.necsoft.web_md.core.common.forms.ErrorResponse;

/**
*買掛金元帳のResultFormクラス
*
*/
public class Kkjp1110ResultForm {
    /** エラー応答 */
    private ErrorResponse infoRes;

    /**
     * @return the infoRes
     */
    public ErrorResponse getInfoRes() {
        return infoRes;
    }

    /**
     * @param infoRes the infoRes to set
     */
    public void setInfoRes(ErrorResponse infoRes) {
        this.infoRes = infoRes;
    }

}

