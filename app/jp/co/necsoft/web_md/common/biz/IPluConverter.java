///////////////////////////////////////////////////////////////////////
// Copyright(C) 2015 NEC Solution Innovators, Ltd. All Rights Reserved.
///////////////////////////////////////////////////////////////////////

/* ====================================================================
 * 機能名称 : PLUコード変換インタフェース
 * 改版履歴
 * Rev.  改版年月日   改版者名       内容
 * 1.0   2014.11.13   H.Okuhara      新規作成
 * =================================================================== */
package jp.co.necsoft.web_md.common.biz;

/**
 * @author 1134140119159
 *
 */
public interface IPluConverter {	
    /**
     * テーブル格納コードを表示コードに変換する
     * 
     * @param sDbCode 
     * @param sDate 
     * @return
     */
    public String toDispCode(String sDbCode, String sDate);

    /**
     * 基本情報マスタからのPLU区分取得処理.
     * 
     * @param sJanCode 商品コード
     * @param sDate 日付
     * @return PLU区分
     */
    public String getPluKbn(String sDbCode, String sDate);

    /**
     * 表示コードをテーブル格納コードに変換する
     * 
     * @param sDispCode 
     * @return
     */
    public String toDbCode(String sDispCode);

    /**
     * NON-PLUコードかを判定する
     * 
     * @param code 
     * @return
     */
    public boolean isNonPlu(String code);
    
    /**
     * NON-PLUコードから売価を取得する
     * 
     * @param code 
     * @return
     */
    public int getNonPluPrice(String code);
}
