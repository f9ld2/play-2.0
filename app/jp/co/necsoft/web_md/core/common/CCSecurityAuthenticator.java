///////////////////////////////////////////////////////////////////////
// Copyright(C) 2015 NEC Solution Innovators, Ltd. All Rights Reserved.
///////////////////////////////////////////////////////////////////////

/* ====================================================================
 * 機能名称 : ログイン
 * 改版履歴
 * Rev.  改版年月日   改版者名       内容
 * 1.0   2014.03.26   HaiNP      新規作成
 * =================================================================== */
package jp.co.necsoft.web_md.core.common;

import java.util.Date;

import jp.co.necsoft.web_md.core.common.forms.UserBean;
import play.Play;
import play.mvc.Http.Context;
import play.mvc.Http.Session;
import play.mvc.Result;
import play.mvc.Security;

/**
 * ログインのCCSecurityAuthenticatorクラス
 *
 */
public class CCSecurityAuthenticator extends Security.Authenticator {
    
    /**
     * 認証をチェックする
     * @param ctx システムコンテキスト
     * @return ユーザー
     */
    @Override
    public String getUsername(Context ctx) {
        Session session = ctx.session();
        if (session.get(UserBean.TANTO_CD) == null) {
            return null;
        }
        // see if the session is expired
        String previousTick = session.get("userTime");
        if (previousTick != null && !"".equals(previousTick)) {
            long previousT = Long.valueOf(previousTick);
            long currentT = new Date().getTime();
            long timeout = Long.valueOf(Play.application().configuration().getString("cc.session.timeout")) * 1000 * 60;
            if ((currentT - previousT) > timeout) {
                // session expired
                session.clear();
                return null;
            }
        }
 
        // update time in session
        String tickString = Long.toString(new Date().getTime());
        session.put("userTime", tickString);
        return ctx.session().get(UserBean.TANTO_CD);
    }
    
    /**
     * ユーザーは、特権を持っていません
     * @param ctx システムコンテキスト
     * @return ログインページ
     */
    @Override
    public Result onUnauthorized(Context ctx) {
        return unauthorized();
    }
}
