// /////////////////////////////////////////////////////////////////////
// Copyright(C) 2015 NEC Solution Innovators, Ltd. All Rights Reserved.
// /////////////////////////////////////////////////////////////////////

/*
 * ====================================================================
 * 
 * 機能名称 : 取引先別在庫金額一覧
 * 
 * 改版履歴
 * 
 * Rev. 改版年月日 改版者名 内容
 * 
 * 1.0 2014-05-26 TUCTVZ 新規作成
 * 
 * ===================================================================
 */
package jp.co.necsoft.web_md.core.app.forms.zi;

/**
* 取引先別在庫金額一覧のKkjp1110CondFormクラス
*
*/
public class Zijp1080CondForm {
    /**会社コード */
    private String kaisyaCd;
    /**事業部コード */
    private String jigyobuCd;
    /**出力区分 */
    private String outputKbn;
    /**店舗コード*/
    private String tenCd;
    /**部門コード */
    private String bmnCd;

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

    /**
     * @return the outputKbn
     */
    public String getOutputKbn() {
        return outputKbn;
    }

    /**
     * @param outputKbn the outputKbn to set
     */
    public void setOutputKbn(String outputKbn) {
        this.outputKbn = outputKbn;
    }

    /**
     * @return the tenCd
     */
    public String getTenCd() {
        return tenCd;
    }

    /**
     * @param tenCd the tenCd to set
     */
    public void setTenCd(String tenCd) {
        this.tenCd = tenCd;
    }

    /**
     * @return the bmnCd
     */
    public String getBmnCd() {
        return bmnCd;
    }

    /**
     * @param bmnCd the bmnCd to set
     */
    public void setBmnCd(String bmnCd) {
        this.bmnCd = bmnCd;
    }

}
