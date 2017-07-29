///////////////////////////////////////////////////////////////////////
// Copyright(C) 2015 NEC Solution Innovators, Ltd. All Rights Reserved.
///////////////////////////////////////////////////////////////////////

/* ====================================================================
 * 機能名称 : 仕入先別納品率ﾘｽﾄ.
 * 改版履歴
 * Rev. 改版年月日   改版者名       内容
 * 1.0   2014.04.17   TUANVT      新規作成
 * =================================================================== */

package jp.co.necsoft.web_md.core.app.forms.si;

import java.net.URL;

/**
*仕入先別納品率ﾘｽﾄのResultFormクラス
*
*/
public class Sijp1180ResultForm {
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

