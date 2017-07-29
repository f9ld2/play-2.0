///////////////////////////////////////////////////////////////////////
// Copyright(C) 2015 NEC Solution Innovators, Ltd. All Rights Reserved.
///////////////////////////////////////////////////////////////////////

/* ====================================================================
 * 機能名称 : 支払予定額一覧表
 * 改版履歴
 * Rev. 改版年月日   改版者名       内容
 * 1.0   2014.04.24   VuQQ      新規作成
 * =================================================================== */
package jp.co.necsoft.web_md.core.app.forms.kk;

import java.net.URL;

/**
*支払予定額一覧表
のResultFormクラス
*
*/
public class Kkjp1220ResultForm {
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

