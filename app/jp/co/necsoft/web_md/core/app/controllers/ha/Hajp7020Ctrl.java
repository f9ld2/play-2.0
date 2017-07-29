// /////////////////////////////////////////////////////////////////////
// Copyright(C) 2015 NEC Solution Innovators, Ltd. All Rights Reserved.
// /////////////////////////////////////////////////////////////////////
/*
 * ====================================================================
 * 
 * 機能名称 :自動発注予定データ一覧（店舗別）
 * 
 * 改版履歴
 * 
 * Rev. 改版年月日 改版者名 内容
 * 
 * 1.0 2015-06-04 NECVN 新規作成
 * 
 * ===================================================================
 */

package jp.co.necsoft.web_md.core.app.controllers.ha;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import jp.co.necsoft.web_md.common.mybatis.dao.MyBatisSqlMapDao;
import jp.co.necsoft.web_md.core.app.dto.ha.Hajp7020Dto;
import jp.co.necsoft.web_md.core.app.dto.ha.Hajp7020Param;
import jp.co.necsoft.web_md.core.app.forms.ha.Hajp7020CondForm;
import jp.co.necsoft.web_md.core.app.forms.ha.Hajp7020ResultForm;
import jp.co.necsoft.web_md.core.common.CCMessageConst;
import jp.co.necsoft.web_md.core.common.CCSecurityAuthenticator;
import jp.co.necsoft.web_md.core.common.CCSystemContext;
import jp.co.necsoft.web_md.core.common.forms.ErrorResponse;
import jp.co.necsoft.web_md.core.common.utils.CCCheckExistsUtil;
import jp.co.necsoft.web_md.core.common.utils.CCStringUtil;
import jp.co.necsoft.web_md.core.common.utils.DataUtil;

import org.springframework.beans.BeanUtils;

import play.data.Form;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;

/**
*自動発注予定データ一覧（店舗別）のControllerクラス
*
*/
@Security.Authenticated(CCSecurityAuthenticator.class)
public class Hajp7020Ctrl extends Controller {

    /**mybatis Dao*/
    @Inject
    private MyBatisSqlMapDao mybatisDao;

    /**Error Response*/
    @Inject
    private ErrorResponse errRes;

    /**  表示区分 */
    private static final String X007_HYOJI_KBN = "N0023";

    /**  店舗別 */
    private static final String N0023_ANOTHER_SHOP = "1";

    /** システムコンテキスト */
    @Inject
    private CCSystemContext context;

    /** ccCheckExistsUtil */
    @Inject
    private CCCheckExistsUtil ccCheckExistsUtil;

    /** Data Util */
    @Inject
    private DataUtil dataUtil;

    /**
     * ページを初期化する
     * 
     * @return クライアントへ返却する結果
     */
    public Result init() {
        Hajp7020CondForm form = new Hajp7020CondForm();
        String sUnyoDate = context.getUnyoDate();
        String kaisyaCd = context.getDefaultKaisyaCd();

        // ■M001事業部マスタから事業部コードの直近の1レコードのみ取得処理を行う。
        form.setJigyobuCd(dataUtil.getNewestJigyobuCd(kaisyaCd, sUnyoDate));
        // デフォルトでは「店舗別」を表示する
        form.setHyojiKbn(N0023_ANOTHER_SHOP);

        List<Hajp7020CondForm> result = new ArrayList<Hajp7020CondForm>();
        result.add(form);
        return ok(Json.toJson(result));
    }

    /**
     *  引数とリクエスト(パラメータ)を条件にDBからデータ(複数件)を検索し返却する。
     * @param jigyobuCd 事業部コード
     * @param hyojiKbn 表示区分
     * @return クライアントへ返却する結果
     */
    public Result query(String jigyobuCd, String hyojiKbn) {
        Form<Hajp7020CondForm> emptyForm = new Form<Hajp7020CondForm>(Hajp7020CondForm.class);
        Form<Hajp7020CondForm> reqForm = emptyForm.bindFromRequest();
        if (reqForm.hasErrors()) {
            errRes.addErrorInfo(CCMessageConst.MSG_KEY_VALIDATION_ERROR);
            errRes.addErrorInfo(reqForm.errors());
            return badRequest(Json.toJson(errRes));
        } else {

            Hajp7020CondForm req = reqForm.get();
            String sUnyoDate = context.getUnyoDate();
            String kaisyaCd = req.getKaisyaCd();

            // 検索ボタン
            if (!checkCond(kaisyaCd, jigyobuCd, hyojiKbn)) {
                // Return error to client
                return badRequest(Json.toJson(errRes));
            }

            // 画面最大表示明細数の取得
            if (!CCCheckExistsUtil.existMaxRecordNumber(req.getMaxRecordNumber(), errRes)) {
                // Return error to client
                return badRequest(Json.toJson(errRes));
            }
            
            Integer maxRecordNumber = Integer.parseInt(req.getMaxRecordNumber());

            Hajp7020Param hajp7020Param = new Hajp7020Param();
            hajp7020Param.setKaisyaCd(kaisyaCd);
            hajp7020Param.setJigyobuCd(jigyobuCd);
            hajp7020Param.setHyojiKbn(hyojiKbn);
            hajp7020Param.setUnyoDate(sUnyoDate);
            hajp7020Param.setMaxRecordNumber(maxRecordNumber + 1);
            List<Hajp7020Dto> resultlist;

            // 表示区分="店舗別"の場合
            if (N0023_ANOTHER_SHOP.equals(hyojiKbn)) {
                resultlist = mybatisDao.selectMany("selectHajp70201", hajp7020Param, Hajp7020Dto.class);
            } else {
                // 表示区分="店舗取引先別"の場合
                resultlist = mybatisDao.selectMany("selectHajp70202", hajp7020Param, Hajp7020Dto.class);
            }

            // データが存在しない場合
            if (resultlist.isEmpty()) {
                // ※メッセージ埋め込み文字：自動発注予定
                return notFound();
            }

            // データが存在する場合
            // 画面最大表示明細数のチェックを行う。
            if (resultlist.size() > maxRecordNumber) {
                // 最大表示数を超えたので、条件を追加し、再実行してください。
                errRes.addErrorInfo(CCMessageConst.MSG_KEY_OVER_MAX_NUMBER_RECORDS);
                return badRequest(Json.toJson(errRes));
            }

            resultlist.get(0).setHyojiKbn(hyojiKbn);

            List<Hajp7020ResultForm> reponseList = new ArrayList<Hajp7020ResultForm>();
            for (int i = 0; i < resultlist.size(); i++) {
                Hajp7020ResultForm response = new Hajp7020ResultForm();
                
                BeanUtils.copyProperties(resultlist.get(i), response);
                reponseList.add(response);
           }

            return ok(Json.toJson(reponseList));
        }

    }

    /**
     * Function check param input from form
     * 
     * @param kaisyaCd 会社コード
     * @param jigyobuCd 事業部コード
     * @param hyojiKbn 表示区分
     * @return error or not
     */
    private boolean checkCond(String kaisyaCd, String jigyobuCd, String hyojiKbn) {

        // 事業部コードチェック
        if (!CCStringUtil.checkLength("jigyobuCd", jigyobuCd, 2, errRes)) {
            return false;
        }
        // 表示区分チェック
        if (!CCStringUtil.checkLength("hyojiKbn", hyojiKbn, 1, errRes)) {
            return false;
        }

        // 事業部コードチェック
        // 存在チェック
        if (!ccCheckExistsUtil.existsJigyobuCd("jigyobuCd", context.getUnyoDate(), kaisyaCd, jigyobuCd, errRes)) {
            return false;
        }

        // 表示区分チェック
        // X007区分マスタから表示区分の検索処理を行う
        if (!ccCheckExistsUtil.existsKbn("hyojiKbn", X007_HYOJI_KBN, hyojiKbn, errRes)) {
            return false;
        }

        return true;
    }
}
