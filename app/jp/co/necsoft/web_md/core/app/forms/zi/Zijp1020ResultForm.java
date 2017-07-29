// /////////////////////////////////////////////////////////////////////
// Copyright(C) 2015 NEC Solution Innovators, Ltd. All Rights Reserved.
// /////////////////////////////////////////////////////////////////////

/*
 * ====================================================================
 * 
 * 機能名称 : 棚卸取扱い部門リスト
 * 
 * 改版履歴
 * 
 * Rev. 改版年月日 改版者名 内容
 * 
 * 1.0 2014/05/07 Taivd 新規作成
 * 
 * ===================================================================
 */
package jp.co.necsoft.web_md.core.app.forms.zi;

import java.net.URL;

/**
*棚卸取扱い部門リストのResultFormクラス
*
*/
public class Zijp1020ResultForm {
    /**帳票URL*/
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
