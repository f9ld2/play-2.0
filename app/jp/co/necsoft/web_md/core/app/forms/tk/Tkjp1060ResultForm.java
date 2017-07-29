///////////////////////////////////////////////////////////////////////
// Copyright(C) 2015 NEC Solution Innovators, Ltd. All Rights Reserved.
///////////////////////////////////////////////////////////////////////

/* ====================================================================
 * 機能名称 : 企画情報
 * 改版履歴
 * Rev. 改版年月日   改版者名       内容
 * 1.0   2014.04.29   VuQQ      新規作成
 * =================================================================== */
package jp.co.necsoft.web_md.core.app.forms.tk;

import java.net.URL;

/**
*企画情報のResultFormクラス
*
*/
public class Tkjp1060ResultForm {

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

