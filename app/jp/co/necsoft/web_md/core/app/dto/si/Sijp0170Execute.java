// /////////////////////////////////////////////////////////////////////
// Copyright(C) 2015 NEC Solution Innovators, Ltd. All Rights Reserved.
// /////////////////////////////////////////////////////////////////////
/*
 * ====================================================================
 * 
 * 機能名称 :Sijp0170Execute
 * 
 * 改版履歴
 * 
 * Rev. 改版年月日 改版者名 内容
 * 
 * 1.0 2014-04-05 tuctv 新規作成
 * 
 * ===================================================================
 */
package jp.co.necsoft.web_md.core.app.dto.si;

/**
*Sijp0170ExecuteのDtoクラス
*
*/
public class Sijp0170Execute {

    /**伝票番号**/
    private String dpyNo;
    /**会社コード**/
    private String kaisyaCd;
    /**事業部コード**/
    private String jigyobuCd;

    /**
     * @return the dpyNo
     */
    public String getDpyNo() {
        return dpyNo;
    }

    /**
     * @param dpyNo the dpyNo to set
     */
    public void setDpyNo(String dpyNo) {
        this.dpyNo = dpyNo;
    }

    /**
     * @return the kaisyaCd
     */
    public String getKaisyaCd() {
        return kaisyaCd;
    }

    /**
     * @param kaisyaCd the kaisyaCd to set
     */
    public void setKaisyaCd(String kaisyaCd) {
        this.kaisyaCd = kaisyaCd;
    }

    /**
     * @return the jigyobuCd
     */
    public String getJigyobuCd() {
        return jigyobuCd;
    }

    /**
     * @param jigyobuCd the jigyobuCd to set
     */
    public void setJigyobuCd(String jigyobuCd) {
        this.jigyobuCd = jigyobuCd;
    }

}
