// /////////////////////////////////////////////////////////////////////
// Copyright(C) 2015 NEC Solution Innovators, Ltd. All Rights Reserved.
// /////////////////////////////////////////////////////////////////////
/*
 * ====================================================================
 * 
 * 機能名称 :Base Condition Form
 * 
 * 改版履歴
 * 
 * Rev. 改版年月日 改版者名 内容
 * 
 * 1.0 2015-06-16 NECVN 新規作成
 * 
 * ===================================================================
 */
package jp.co.necsoft.web_md.core.app.forms.cm;

/**
 * Base Condition Form
 *
 */
public abstract class BaseCondForm {
    /**会社コード**/
    private String kaisyaCd;
    /**最大表示明細数**/
    private String maxRecordNumber;

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
     * @return the maxRecordNumber
     */
    public String getMaxRecordNumber() {
        return maxRecordNumber;
    }

    /**
     * @param maxRecordNumber the maxRecordNumber to set
     */
    public void setMaxRecordNumber(String maxRecordNumber) {
        this.maxRecordNumber = maxRecordNumber;
    }

}
