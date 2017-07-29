///////////////////////////////////////////////////////////////////////
// Copyright(C) 2015 NEC Solution Innovators, Ltd. All Rights Reserved.
///////////////////////////////////////////////////////////////////////

/* ====================================================================
 * 機能名称 : 買掛金支払チェックリスト出力
 * 改版履歴
 * Rev. 改版年月日   改版者名       内容
 * 1.0   2014.04.05   VuQQ      新規作成
 * =================================================================== */
package jp.co.necsoft.web_md.core.app.forms.kk;

import java.net.URL;


/**
*買掛金支払チェックリスト出力のResultFormクラス
*
*/
public class Kkjp1180ResultForm {
    /**
     * PDFURL
     */
    private URL pdfUrl;

    /**
    * @@return pdfUrl the pdfUrl to set
    */
    public URL getPdfUrl() {
        return this.pdfUrl;
    }
    /**
    * @param  pdfUrl URL 
    */
    public void setPdfUrl(URL pdfUrl) {
        this.pdfUrl = pdfUrl;
    }

}

