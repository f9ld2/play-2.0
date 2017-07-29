///////////////////////////////////////////////////////////////////////
// Copyright(C) 2015 NEC Solution Innovators, Ltd. All Rights Reserved.
///////////////////////////////////////////////////////////////////////

/* ====================================================================
 * 機能名称 : デモ棚リスト
 * 改版履歴
 * Rev. 改版年月日   改版者名       内容
 * 1.0 2015-06-23 chiennt 新規作成
 * =================================================================== */
package jp.co.necsoft.web_md.core.app.forms.gn;

import java.net.URL;


/**
* デモ棚リストのResultFormクラス
*
*/
public class Gnjp7030ResultForm {

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

