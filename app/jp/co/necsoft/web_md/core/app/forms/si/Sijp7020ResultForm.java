///////////////////////////////////////////////////////////////////////
//Copyright(C) 2015 NEC Solution Innovators, Ltd. All Rights Reserved.
///////////////////////////////////////////////////////////////////////

/* ====================================================================
 * 機能名称 : 入荷状況一覧表
 * 改版履歴
 * Rev. 改版年月日   改版者名       内容
 * 1.0   2015.06.04   NECVN      新規作成
 * =================================================================== */

package jp.co.necsoft.web_md.core.app.forms.si;

import java.net.URL;

/**
 * 入荷状況一覧表のResultFormクラス
 * 
 */
public class Sijp7020ResultForm {
    /** PDF URL */
    private URL pdfUrl;
    /** type of result */
    private String type;

    /**
     * get type
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * set type
     * @param type the type to set
     */
    public void setType(String type) {
        this.type = type;
    }

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
