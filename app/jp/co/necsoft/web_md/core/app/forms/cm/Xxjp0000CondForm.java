///////////////////////////////////////////////////////////////////////
// Copyright(C) 2015 NEC Solution Innovators, Ltd. All Rights Reserved.
///////////////////////////////////////////////////////////////////////

/* ====================================================================
 * 機能名称 : ログイン
 * 改版履歴
 * Rev.  改版年月日   改版者名       内容
 * 1.0   2014.02.11   HaiNP      新規作成
 * =================================================================== */
package jp.co.necsoft.web_md.core.app.forms.cm;

import play.data.validation.Constraints.Required;

/**
 * ログインのCondFormクラス
 *
 */
public class Xxjp0000CondForm {
    /** 担当者コード */
    @Required
    private String tantoCd;
    
    /** パスワード */
    @Required
    private String password;

    /**
     * @return the tantoCd
     */
    public String getTantoCd() {
        return tantoCd;
    }

    /**
     * @param tantoCd the tantoCd to set
     */
    public void setTantoCd(String tantoCd) {
        this.tantoCd = tantoCd;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }
    
    
}
