///////////////////////////////////////////////////////////////////////
// Copyright(C) 2015 NEC Solution Innovators, Ltd. All Rights Reserved.
///////////////////////////////////////////////////////////////////////

/* ====================================================================
 * 機能名称 : 商品検索
 * 改版履歴
 * Rev. 改版年月日   改版者名       内容
 * 1.0   2014.08.20   phuclt      新規作成
 * =================================================================== */
package jp.co.necsoft.web_md.core.app.controllers.cm;

import java.util.List;

import javax.inject.Inject;

import jp.co.necsoft.web_md.common.mybatis.dao.MyBatisSqlMapDao;
import jp.co.necsoft.web_md.core.app.dto.cm.JanSearchResult;
import jp.co.necsoft.web_md.core.app.forms.cm.JanSearchCondForm;
import jp.co.necsoft.web_md.core.common.CCMessageConst;
import jp.co.necsoft.web_md.core.common.CCSystemContext;
import jp.co.necsoft.web_md.core.common.forms.ErrorResponse;
import play.data.Form;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

/**
 * 商品検索のControllerクラス
 *
 */
public class JanSearchCtrl extends Controller {
    /** context */
    @Inject
    private CCSystemContext context;

    /*** dao  */
    @Inject
    private MyBatisSqlMapDao dao;

    /** errRes */
    @Inject
    private ErrorResponse errRes;

    /** MAX_RECORD **/
    private static final String MAX_RECORD = "cc.app.cm.maxrecord";

    /**
     * 引数とリクエスト(パラメータ)を条件にDBからデータ(複数件)を検索し返却する。
     * @return クライアントへ返却する結果
     */
    public Result query() {
        Form<JanSearchCondForm> form = new Form<JanSearchCondForm>(JanSearchCondForm.class);
        form = form.bindFromRequest();

        JanSearchCondForm condForm = form.get();

        List<JanSearchResult> resultList = dao.selectMany("janSearch", condForm, JanSearchResult.class);

        if (resultList.isEmpty()) {
            errRes.addErrorInfo(CCMessageConst.MSG_KEY_SEARCH_NO_RECORDS);
            return badRequest(Json.toJson(errRes));
        } else if (resultList.size() > getMaxRecord()) {
            errRes.addErrorInfo(CCMessageConst.MSG_KEY_SEARCH_NUMBER_TOO_MANY);
            return badRequest(Json.toJson(errRes));
        } else {
            return ok(Json.toJson(resultList));
        }
    }

    /**
     * getMaxRecord
     * @return String
     */
    private int getMaxRecord() {
        return Integer.parseInt(context.getContextProperty(MAX_RECORD));
    }
}
