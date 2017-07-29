///////////////////////////////////////////////////////////////////////
// Copyright(C) 2015 NEC Solution Innovators, Ltd. All Rights Reserved.
///////////////////////////////////////////////////////////////////////

/* ====================================================================
 * 機能名称 : 移動実績リスト
 * 改版履歴
 * Rev. 改版年月日   改版者名       内容
 * 1.0   2014.04.18   TUANVT      新規作成
 * =================================================================== */

package jp.co.necsoft.web_md.core.app.forms.si;

import java.net.URL;

/**
*移動実績ﾘｽﾄのResultFormクラス
*
*/
public class Sijp1190ResultForm {
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

