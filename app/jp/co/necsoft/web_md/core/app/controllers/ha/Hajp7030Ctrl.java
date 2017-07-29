// /////////////////////////////////////////////////////////////////////
// Copyright(C) 2015 NEC Solution Innovators, Ltd. All Rights Reserved.
// /////////////////////////////////////////////////////////////////////
/*
 * ====================================================================
 * 
 * 機能名称 :自動発注予定データ一覧（店舗商品別）
 * 
 * 改版履歴
 * 
 * Rev. 改版年月日 改版者名 内容
 * 
 * 1.0 2015-06-02 NECVN 新規作成
 * 
 * ===================================================================
 */

package jp.co.necsoft.web_md.core.app.controllers.ha;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import jp.co.necsoft.web_md.common.biz.IPluConverter;
import jp.co.necsoft.web_md.common.mybatis.dao.MyBatisSqlMapDao;
import jp.co.necsoft.web_md.core.app.dto.ha.Hajp7030Dto;
import jp.co.necsoft.web_md.core.app.dto.ha.Hajp7030Param;
import jp.co.necsoft.web_md.core.app.forms.ha.Hajp7030CondForm;
import jp.co.necsoft.web_md.core.app.forms.ha.Hajp7030ResultForm;
import jp.co.necsoft.web_md.core.common.CCMessageConst;
import jp.co.necsoft.web_md.core.common.CCSecurityAuthenticator;
import jp.co.necsoft.web_md.core.common.CCSystemContext;
import jp.co.necsoft.web_md.core.common.forms.ErrorResponse;
import jp.co.necsoft.web_md.core.common.mybatis.dao.M011trimMapper;
import jp.co.necsoft.web_md.core.common.mybatis.dto.M011trim;
import jp.co.necsoft.web_md.core.common.mybatis.dto.M011trimExample;
import jp.co.necsoft.web_md.core.common.utils.CCCheckExistsUtil;
import jp.co.necsoft.web_md.core.common.utils.CCStringUtil;
import jp.co.necsoft.web_md.core.common.utils.DataUtil;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;

import play.data.Form;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;

/**
*自動発注予定データ一覧（店舗商品別）のControllerクラス
*
*/
@Security.Authenticated(CCSecurityAuthenticator.class)
public class Hajp7030Ctrl extends Controller {

    /**  削除 */
    private static final String TYPE_ACT_KBN_DELETED = "9";

    /**  削除Flag */
    private static final String DELETED_FLAG_NORMAL = "0";

    /** ten code length */
    private static final int TEN_CD_LENGTH = 3;

    /**mybatis Dao*/
    @Inject
    private MyBatisSqlMapDao mybatisDao;

    /** M011trim Mapper */
    @Inject
    private M011trimMapper m011trimMapper;

    /**Error Response*/
    @Inject
    private ErrorResponse errRes;
    /** システムコンテキスト */
    @Inject
    private CCSystemContext context;

    /** ccCheckExistsUtil */
    @Inject
    private CCCheckExistsUtil ccCheckExistsUtil;

    /** Data Util */
    @Inject
    private DataUtil dataUtil;

    /** IPlu Converter */
    @Inject
    private IPluConverter plucnv;

    /**
     * ページを初期化する
     * 
     * @return クライアントへ返却する結果
     */
    public Result init() {
        Hajp7030CondForm form = new Hajp7030CondForm();
        String sUnyoDate = context.getUnyoDate();
        String kaisyaCd = context.getDefaultKaisyaCd();
        String jigyobuCd = dataUtil.getNewestJigyobuCd(kaisyaCd, sUnyoDate);
        String tenCd = dataUtil.getNewestTenCd(kaisyaCd, jigyobuCd, sUnyoDate);

        // M001事業部マスタから事業部コードの直近の1レコードのみ取得処理を行う。
        form.setJigyobuCd(jigyobuCd);
        // M006店舗マスタから店舗コードの直近の1レコードのみ取得処理を行う。
        form.setTenCd(tenCd);

        List<Hajp7030CondForm> result = new ArrayList<Hajp7030CondForm>();
        result.add(form);
        return ok(Json.toJson(result));
    }

    /**
     *  引数とリクエスト(パラメータ)を条件にDBからデータ(複数件)を検索し返却する。
     * @param jigyobuCd 事業部コード
     * @param tenCd 店舗
     * @return クライアントへ返却する結果
     */
    public Result query(String jigyobuCd, String tenCd) {
        Form<Hajp7030CondForm> emptyForm = new Form<Hajp7030CondForm>(Hajp7030CondForm.class);
        Form<Hajp7030CondForm> reqForm = emptyForm.bindFromRequest();
        if (reqForm.hasErrors()) {
            errRes.addErrorInfo(CCMessageConst.MSG_KEY_VALIDATION_ERROR);
            errRes.addErrorInfo(reqForm.errors());
            return badRequest(Json.toJson(errRes));
        } else {

            Hajp7030CondForm req = reqForm.get();

            String unyoDate = context.getUnyoDate();
            String triCd = StringUtils.EMPTY;
            String kaisyaCd = req.getKaisyaCd();

            if (req.getTriCd() != null) {
                triCd = req.getTriCd();
            }

            // 検索ボタン
            if (!checkCond(kaisyaCd, jigyobuCd, tenCd, triCd)) {
                // Return error to client
                return badRequest(Json.toJson(errRes));
            }

            // 画面最大表示明細数の取得
            if (!CCCheckExistsUtil.existMaxRecordNumber(req.getMaxRecordNumber(), errRes)) {
                // Return error to client
                return badRequest(Json.toJson(errRes));
            }

            Integer maxRecordNumber = Integer.parseInt(req.getMaxRecordNumber());

            // 本部発注ファイルから検索条件に沿ったデータを抽出する。
            Hajp7030Param hajp7030Param = new Hajp7030Param();
            hajp7030Param.setKaisyaCd(kaisyaCd);
            hajp7030Param.setJigyobuCd(jigyobuCd);
            hajp7030Param.setTriCd(triCd);
            hajp7030Param.setTenCd(tenCd);
            hajp7030Param.setUnyoDate(unyoDate);
            hajp7030Param.setMaxRecordNumber(maxRecordNumber + 1);

            List<Hajp7030Dto> resultlist = mybatisDao.selectMany("selectHajp7030", hajp7030Param, Hajp7030Dto.class);

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

            List<Hajp7030ResultForm> reponseList = new ArrayList<Hajp7030ResultForm>();
            for (int i = 0; i < resultlist.size(); i++) {
                Hajp7030ResultForm response = new Hajp7030ResultForm();

                BeanUtils.copyProperties(resultlist.get(i), response);

                response.setShnCd(plucnv.toDispCode(response.getShnCd(), unyoDate));
                reponseList.add(response);
            }
            return ok(Json.toJson(reponseList));
        }

    }

    /**
     *  発注サイクルを表示する。
     * @param triCd 取引先コード
     * @return クライアントへ返却する結果
     */
    public Result getHatp(String triCd) {

        String sUnyoDate = context.getUnyoDate();

        M011trimExample m011trimExample = new M011trimExample();
        m011trimExample.createCriteria().andTriCdEqualTo(triCd).andDeleteFlgEqualTo(DELETED_FLAG_NORMAL);
        m011trimExample.setSearchDate(sUnyoDate);
        List<M011trim> m011trims = m011trimMapper.selectByExample(m011trimExample);

        // データが存在しない場合
        if (m011trims.isEmpty()) {
            // ※メッセージ埋め込み文字：自動発注予定
            errRes.addErrorInfo(CCMessageConst.MSG_KEY_ORDER_CYCLE_NOT_REGISTERD);
            return badRequest(Json.toJson(errRes));
        }

        if (m011trims.size() > 0) {
            if (TYPE_ACT_KBN_DELETED.equals(m011trims.get(0).getActKbn())) {
                // ※メッセージ埋め込み文字：自動発注予定
                errRes.addErrorInfo(CCMessageConst.MSG_KEY_ORDER_CYCLE_NOT_REGISTERD);
                return badRequest(Json.toJson(errRes));
            }
        }

        Hajp7030ResultForm resultForm = new Hajp7030ResultForm();
        BeanUtils.copyProperties(m011trims.get(0), resultForm);

        List<Hajp7030ResultForm> resultlist = new ArrayList<Hajp7030ResultForm>();
        resultlist.add(resultForm);
        return ok(Json.toJson(resultlist));
    }

    /**
     * Function check param input from form
     * 
     * @param kaisyaCd 会社コード
     * @param jigyobuCd 事業部コード
     * @param tenCd 店舗コード
     * @param triCd 取引先
     * @return error or not
     */
    private boolean checkCond(String kaisyaCd, String jigyobuCd, String tenCd, String triCd) {
        String unyoDate = context.getUnyoDate();

        // 事業部コードチェック
        if (!CCStringUtil.checkLength("jigyobuCd", jigyobuCd, 2, errRes)) {
            return false;
        }

        // 事業部コードチェック
        // 存在チェック
        if (!ccCheckExistsUtil.existsJigyobuCd("jigyobuCd", unyoDate, kaisyaCd, jigyobuCd, errRes)) {
            return false;
        }

        // 店舗コードチェック
        if (!CCStringUtil.checkLength("tenCd", tenCd, TEN_CD_LENGTH, errRes)) {
            return false;
        }

        // 店舗コードチェック
        // 存在チェック
        if (!ccCheckExistsUtil.existsTenCd("tenCd", unyoDate, kaisyaCd + jigyobuCd + tenCd, errRes)) {
            return false;
        }
        
        //取引先チェック
        if (!CCStringUtil.isEmpty(triCd) && !ccCheckExistsUtil.existTriCd("triCd", unyoDate, triCd, errRes)) {
            return false;
        }

        return true;
    }
}
