// /////////////////////////////////////////////////////////////////////
// Copyright(C) 2015 NEC Solution Innovators, Ltd. All Rights Reserved.
// /////////////////////////////////////////////////////////////////////

/*
 * ====================================================================
 * 
 * 機能名称 : ＴＡ伝票出力
 * 
 * 
 * 改版履歴
 * 
 * Rev. 改版年月日 改版者名 内容
 * 
 * 1.0 2014-05-05 TUCTVZ 新規作成
 * 
 * ===================================================================
 */
package jp.co.necsoft.web_md.core.app.forms.ha;

import java.net.URL;

/**
*発注一覧表出力のResultFormクラス
*
*/
public class Hajp1170ResultForm {
    /**
     * PDFURL
     */
    private URL pdfUrl;

    /**
     * @return  pdfUrl
     */
    public URL getPdfUrl() {
        return pdfUrl;
    }

    /**
     * @param pdfUrl pdfUrl to set
     */
    public void setPdfUrl(URL pdfUrl) {
        this.pdfUrl = pdfUrl;
    }
}

