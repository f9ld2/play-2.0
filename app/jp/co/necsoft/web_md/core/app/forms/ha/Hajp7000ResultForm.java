///////////////////////////////////////////////////////////////////////
//Copyright(C) 2015 NEC Solution Innovators, Ltd. All Rights Reserved.
///////////////////////////////////////////////////////////////////////

/* ====================================================================
 * 機能名称 : HHT発注取込エラーリスト
 * 改版履歴
 * Rev. 改版年月日   改版者名       内容
 * 1.0   2015.06.03   NECVN      新規作成
 * =================================================================== */

package jp.co.necsoft.web_md.core.app.forms.ha;

import java.net.URL;

/**
 * HHT発注取込エラーリストのResultFormクラス
 * 
 */
public class Hajp7000ResultForm {
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
