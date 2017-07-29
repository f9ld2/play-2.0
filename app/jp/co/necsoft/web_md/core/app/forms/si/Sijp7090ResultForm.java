///////////////////////////////////////////////////////////////////////
//Copyright(C) 2015 NEC Solution Innovators, Ltd. All Rights Reserved.
///////////////////////////////////////////////////////////////////////

/* ====================================================================
 * 機能名称 : オリジナル商品品振エラーリスト
 * 改版履歴
 * Rev. 改版年月日   改版者名       内容
 * 1.0   2015.06.10   NECVN      新規作成
 * =================================================================== */

package jp.co.necsoft.web_md.core.app.forms.si;

import java.net.URL;

/**
 * オリジナル商品品振エラーリストのResultFormクラス
 * 
 */
public class Sijp7090ResultForm {
    /** PDF URL */
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
