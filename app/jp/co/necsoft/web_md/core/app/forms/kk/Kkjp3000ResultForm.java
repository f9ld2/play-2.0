///////////////////////////////////////////////////////////////////////
//Copyright(C) 2014 NEC Soft, Ltd. All Rights Reserved.
///////////////////////////////////////////////////////////////////////
/* ====================================================================
* 機能名称  : 仕入本締後処理
* 改版履歴
* Rev. 改版年月日   改版者名       内容
* 1.0  2014	.09.16  NES田中      新規作成
* =================================================================== */
package jp.co.necsoft.web_md.core.app.forms.kk;

import jp.co.necsoft.web_md.core.common.forms.ErrorResponse;


/**
 * 仕入本締後処理のResultFormクラス
 *
 */
public class Kkjp3000ResultForm {
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
