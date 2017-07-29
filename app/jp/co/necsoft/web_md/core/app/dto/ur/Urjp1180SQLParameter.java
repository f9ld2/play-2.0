// /////////////////////////////////////////////////////////////////////
// Copyright(C) 2015 NEC Solution Innovators, Ltd. All Rights Reserved.
// /////////////////////////////////////////////////////////////////////

/*
 * ====================================================================
 *
 * 機能名称 : 月次・日割予算チェックリスト
 *
 * 改版履歴
 *
 * Rev. 改版年月日 改版者名 内容
 *
 * 1.0 2014.07.01 NES石井 新規作成
 *
 * ===================================================================
 */

package jp.co.necsoft.web_md.core.app.dto.ur;

/**
*月次・日割予算チェックリストのSQLParameterクラス
*
*/
public class Urjp1180SQLParameter {

    private String kaisyaCd;

	private String jigyobuCd;

    private String taisyodateY;

    private String taisyodateM;

    private String tenpoFrom;

    private String tenpoTo;

    private String ruiDateFrom;

    private String ruiMonFrom;

    private String ruiDateTo;

    private String dayDateFrom;

    private String dayDateTo;

	/**
	 * @return kaisyaCd
	 */
	public String getKaisyaCd() {
		return kaisyaCd;
	}

	/**
	 * @param kaisyaCd セットする kaisyaCd
	 */
	public void setKaisyaCd(String kaisyaCd) {
		this.kaisyaCd = kaisyaCd;
	}

	/**
	 * @return jigyobuCd
	 */
	public String getJigyobuCd() {
		return jigyobuCd;
	}

	/**
	 * @param jigyobuCd セットする jigyobuCd
	 */
	public void setJigyobuCd(String jigyobuCd) {
		this.jigyobuCd = jigyobuCd;
	}

	/**
	 * @return taisyodateY
	 */
	public String getTaisyodateY() {
		return taisyodateY;
	}

	/**
	 * @param taisyodateY セットする taisyodateY
	 */
	public void setTaisyodateY(String taisyodateY) {
		this.taisyodateY = taisyodateY;
	}

	/**
	 * @return taisyodateM
	 */
	public String getTaisyodateM() {
		return taisyodateM;
	}

	/**
	 * @param taisyodateM セットする taisyodateM
	 */
	public void setTaisyodateM(String taisyodateM) {
		this.taisyodateM = taisyodateM;
	}

	/**
	 * @return tenpoFrom
	 */
	public String getTenpoFrom() {
		return tenpoFrom;
	}

	/**
	 * @param tenpoFrom セットする tenpoFrom
	 */
	public void setTenpoFrom(String tenpoFrom) {
		this.tenpoFrom = tenpoFrom;
	}

	/**
	 * @return tenpoTo
	 */
	public String getTenpoTo() {
		return tenpoTo;
	}

	/**
	 * @param tenpoTo セットする tenpoTo
	 */
	public void setTenpoTo(String tenpoTo) {
		this.tenpoTo = tenpoTo;
	}

	/**
	 * @return ruiDateFrom
	 */
	public String getRuiDateFrom() {
		return ruiDateFrom;
	}

	/**
	 * @param ruiDateFrom セットする ruiDateFrom
	 */
	public void setRuiDateFrom(String ruiDateFrom) {
		this.ruiDateFrom = ruiDateFrom;
	}

	/**
	 * @return ruiMonFrom
	 */
	public String getRuiMonFrom() {
		return ruiMonFrom;
	}

	/**
	 * @param ruiMonFrom セットする ruiMonFrom
	 */
	public void setRuiMonFrom(String ruiMonFrom) {
		this.ruiMonFrom = ruiMonFrom;
	}

	/**
	 * @return ruiDateTo
	 */
	public String getRuiDateTo() {
		return ruiDateTo;
	}

	/**
	 * @param ruiDateTo セットする ruiDateTo
	 */
	public void setRuiDateTo(String ruiDateTo) {
		this.ruiDateTo = ruiDateTo;
	}

	/**
	 * @return dayDateFrom
	 */
	public String getDayDateFrom() {
		return dayDateFrom;
	}

	/**
	 * @param dayDateFrom セットする dayDateFrom
	 */
	public void setDayDateFrom(String dayDateFrom) {
		this.dayDateFrom = dayDateFrom;
	}

	/**
	 * @return dayDateTo
	 */
	public String getDayDateTo() {
		return dayDateTo;
	}

	/**
	 * @param dayDateTo セットする dayDateTo
	 */
	public void setDayDateTo(String dayDateTo) {
		this.dayDateTo = dayDateTo;
	}

}
