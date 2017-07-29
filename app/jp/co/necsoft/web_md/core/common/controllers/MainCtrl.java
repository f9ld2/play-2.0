///////////////////////////////////////////////////////////////////////
// Copyright(C) 2015 NEC Solution Innovators, Ltd. All Rights Reserved.
///////////////////////////////////////////////////////////////////////

/* ====================================================================
 * 機能名称 : メインコントローラ
 * 改版履歴
 * Rev.  改版年月日   改版者名       内容
 * 1.0   2014.02.04   H.Okuhara      新規作成
 * =================================================================== */
package jp.co.necsoft.web_md.core.common.controllers;

import jp.co.necsoft.web_md.core.common.forms.UserBean;
import jp.co.necsoft.web_md.core.common.views.html.Main;
import jp.co.necsoft.web_md.core.common.views.html.login;
import play.mvc.Controller;
import play.mvc.Result;

/**
 * メインコントローラ
 * 
 */
public class MainCtrl extends Controller {

    /**
     * メインインデックス
     * 
     * @return 結果
     */
    public static Result index() {
        if (session(UserBean.TANTO_CD) == null) {
            return ok(login.render());
        }
        return ok(Main.render());
    }
}
