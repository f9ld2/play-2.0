// /////////////////////////////////////////////////////////////////////
// Copyright(C) 2015 NEC Solution Innovators, Ltd. All Rights Reserved.
// /////////////////////////////////////////////////////////////////////

/*
 * ====================================================================
 * 
 * 機能名称 :買掛金元帳
 * 
 * 改版履歴
 * 
 * Rev. 改版年月日 改版者名 内容
 * 
 * 1.0 2014-05-22 TUCTVZ 新規作成
 * 
 * ===================================================================
 */
package jp.co.necsoft.web_md.core.app.forms.kk;


/**
*買掛金元帳のKkjp1110CondFormクラス
*
*/
public class Kkjp1110CondForm {

    /**会社コード*/
    private String kaisyaCd;

    /**月次処理対象月*/
    private String taisyoY;

    /***/
    private String taisyoM;

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
     * @return the taisyoY
     */
    public String getTaisyoY() {
        return taisyoY;
    }

    /**
     * @param taisyoY the taisyoY to set
     */
    public void setTaisyoY(String taisyoY) {
        this.taisyoY = taisyoY;
    }

    /**
     * @return the taisyoM
     */
    public String getTaisyoM() {
        return taisyoM;
    }

    /**
     * @param taisyoM the taisyoM to set
     */
    public void setTaisyoM(String taisyoM) {
        this.taisyoM = taisyoM;
    }

}
