package  jp.co.necsoft.web_md.core.common.forms;

/**
 * エラー情報クラス
 *
 */
public class ErrorInfo {

	/** 名称 */
	private String name;

	/** コード */
	private String code;

	/** レベル */
	private String level;

	/** メッセージ */
	private String message;

	/**
	 * @return name
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * @param name 名称
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return code
	 */
	public String getCode() {
		return this.code;
	}

	/**
	 * @param code コード
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * @return level
	 */
	public String getLevel() {
		return this.level;
	}

	/**
	 * @param level レベル
	 */
	public void setLevel(String level) {
		this.level = level;
	}

	/**
	 * @return message
	 */
	public String getMessage() {
		return this.message;
	}

	/**
	 * @param message メッセージ
	 */
	public void setMessage(String message) {
		this.message = message;
	}

}
