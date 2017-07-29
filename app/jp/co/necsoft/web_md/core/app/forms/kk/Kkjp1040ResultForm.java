///////////////////////////////////////////////////////////////////////
// Copyright(C) 2015 NEC Solution Innovators, Ltd. All Rights Reserved.
///////////////////////////////////////////////////////////////////////

/* ====================================================================
 * 機能名称 : 買掛金残高一覧表
 * 改版履歴
 * Rev. 改版年月日   改版者名       内容
 * 1.0   2014.04.01   TUANVT      新規作成
 * =================================================================== */


package jp.co.necsoft.web_md.core.app.forms.kk;

import java.net.URL;

/**
*買掛金残高一覧表のResultFormクラス
*
*/
public class Kkjp1040ResultForm {
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

