// /////////////////////////////////////////////////////////////////////
// Copyright(C) 2015 NEC Solution Innovators, Ltd. All Rights Reserved.
// /////////////////////////////////////////////////////////////////////

/*
 * ====================================================================
 * 
 * 機能名称 :仕入伝票未回収一覧（ＥＯＳ）
 * 
 * 改版履歴
 * 
 * Rev. 改版年月日 改版者名 内容
 * 
 * 1.0 2014.05.10 TuanTQ 新規作成
 * 
 * ===================================================================
 */
package jp.co.necsoft.web_md.core.app.forms.si;

import java.net.URL;

/**
*生鮮納品書のResultFormクラス
*
*/
public class Sijp1140ResultForm {

    /**
     * PDFURL
     */
    private URL pdfUrl;

    /**
     * @return the pdfUrl
     */
    public URL getPdfUrl() {
        return pdfUrl;
    }

    /**
     * @param pdfUrl the pdfUrl to set
     */
    public void setPdfUrl(URL pdfUrl) {
        this.pdfUrl = pdfUrl;
    }

}
