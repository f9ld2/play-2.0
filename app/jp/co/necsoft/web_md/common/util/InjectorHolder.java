///////////////////////////////////////////////////////////////////////
// Copyright(C) 2015 NEC Solution Innovators, Ltd. All Rights Reserved.
///////////////////////////////////////////////////////////////////////

/* ====================================================================
 * システム名称 : EOB
 * 機能名称 : インジェクタ保持部品
 * 改版履歴
 * Rev.  改版年月日   改版者名       内容
 * 1.0   2014.09.23   H.Okuhara      新規作成
 * =================================================================== */
package jp.co.necsoft.web_md.common.util;
import com.google.inject.Injector;

/**
 * 任意のクラスのインスタンスを生成するクラス
 * @author okuhara
 *
 */
public class InjectorHolder {
	  private Injector injector;  
	  
	  public InjectorHolder() {
	  }
	  
	/**
	 * Injectorをセットする。
	 * @param injector 
	 */
	public void setInjector(Injector injector) {
	    assert this.injector == null && injector != null;
	    this.injector = injector;
	  }
	
	/**
	 * 指定クラスのインスタンスを取得する。
	 * @param type 
	 * @return
	 */
	public <T> T getInstance(Class<T>type) {
	    return injector.getInstance(type);
	  }
}
