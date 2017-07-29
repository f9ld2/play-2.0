// /////////////////////////////////////////////////////////////////////
// Copyright(C) 2015 NEC Solution Innovators, Ltd. All Rights Reserved.
// /////////////////////////////////////////////////////////////////////

/*
 * ====================================================================
 * 
 * 機能名称 : 日割予算リスト
 * 
 * 改版履歴
 * 
 * Rev. 改版年月日 改版者名 内容
 * 
 * 1.0 2014/05/20 Taivd 新規作成
 * 
 * ===================================================================
 */
package jp.co.necsoft.web_md.core.app.forms.ur;

import java.net.URL;

/**
*日割予算リストのResultFormクラス
*
*/
public class Urjp1140ResultForm {

    /** 帳票URL */
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
