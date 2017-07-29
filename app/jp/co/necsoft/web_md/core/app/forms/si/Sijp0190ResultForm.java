///////////////////////////////////////////////////////////////////////
// Copyright(C) 2015 NEC Solution Innovators, Ltd. All Rights Reserved.
///////////////////////////////////////////////////////////////////////

/* ====================================================================
 * 機能名称 : 仮締設定入力
 * 改版履歴
 * Rev. 改版年月日   改版者名       内容
 * 1.0   2014.03.28   PhucLT      新規作成
 * =================================================================== */
package jp.co.necsoft.web_md.core.app.forms.si;

import play.data.validation.Constraints.MaxLength;
import play.data.validation.Constraints.MinLength;

/**
 * 仮締設定入力のResultFormクラス
 * 
 */
public class Sijp0190ResultForm {
	/** 本部 */
	@MinLength(value = 8)
	@MaxLength(value = 8)
	private String hnhnymd;

	/** 店舗 */
	@MinLength(value = 8)
	@MaxLength(value = 8)
	private String tnhnymd;

	/**
	 * @return the hnhnymd
	 */
	public String getHnhnymd() {
		return hnhnymd;
	}

	/**
	 * @param hnhnymd
	 *            hnhnymd
	 */
	public void setHnhnymd(String hnhnymd) {
		this.hnhnymd = hnhnymd;
	}

	/**
	 * @return tnhnymd
	 */
	public String getTnhnymd() {
		return tnhnymd;
	}

	/**
	 * @param tnhnymd
	 *            hnhnymd
	 */
	public void setTnhnymd(String tnhnymd) {
		this.tnhnymd = tnhnymd;
	}

}
