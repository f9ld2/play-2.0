package jp.co.necsoft.web_md.core.common.forms;


/**
 * 共通マスター結果フォーム
 *
 */
public class CommonMasterResultForm {

	/** マスター結果フォームクラス */
	private ErrorResponse infoRes;

	/**
	 * @return infoRes 結果情報
	 */
	public ErrorResponse getInfoRes() {
		return infoRes;
	}

	/**
	 * @param infoRes 結果情報
	 */
	public void setInfoRes(ErrorResponse infoRes) {
		this.infoRes = infoRes;
	}
}
