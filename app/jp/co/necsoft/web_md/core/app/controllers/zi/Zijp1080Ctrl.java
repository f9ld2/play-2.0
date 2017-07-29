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
 * 1.0 2014-05-26 TUCTV 新規作成
 * 
 * ===================================================================
 */

package jp.co.necsoft.web_md.core.app.controllers.zi;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

import jp.co.necsoft.web_md.common.mybatis.dao.MyBatisSqlMapDao;
import jp.co.necsoft.web_md.core.app.forms.zi.Zijp1080CondForm;
import jp.co.necsoft.web_md.core.app.forms.zi.Zijp1080ResultForm;
import jp.co.necsoft.web_md.core.common.CCKubunConst;
import jp.co.necsoft.web_md.core.common.CCMessageConst;
import jp.co.necsoft.web_md.core.common.CCSecurityAuthenticator;
import jp.co.necsoft.web_md.core.common.CCSystemContext;
import jp.co.necsoft.web_md.core.common.forms.ErrorResponse;
import jp.co.necsoft.web_md.core.common.utils.CCCheckExistsUtil;
import jp.co.necsoft.web_md.core.common.utils.CCJobLauncher;
import jp.co.necsoft.web_md.core.common.utils.CCStringUtil;
import play.data.Form;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;

/**
 * 取引先別在庫金額一覧のControllerクラス 
 * @author hainp
 *
 */
@Security.Authenticated(CCSecurityAuthenticator.class)
public class Zijp1080Ctrl extends Controller {
    /** Space String  */
    private static final String SPACE = " ";
    /** Job path */
    private static final String JOB_PATH = "cc.app.cm.job_path";
    /** バッチＩＤ */
    private static final String BATCH_ID = "ZIBT1080";
    /** プロパティファイル用キー定義  ダミー店舗コード*/
    private static final String KEY_DUMMY_TEN_CD = "zijp1080.dummy.ten_cd";
    /** エラー応答 */
    @Inject
    private ErrorResponse errRes;
    /** システムコンテキスト */
    @Inject
    private CCSystemContext context;
    /** CCCheckExistsUtil */
    @Inject
    private CCCheckExistsUtil ccCheckExistsUtil;
    /** CCSystemContext */
    @Inject
    private CCSystemContext ccSystemContext;
    /** SQLマップ */
    @Inject
    private MyBatisSqlMapDao myBatisDao;

    /**
     * 引数とリクエスト(パラメータ)を条件にDBからデータ(複数件)を検索し返却する。*
     * @param kaisyaCd 会社コード
     * @param jigyobuCd 事業部コード
     * @param outputKbn 出力区分
     * @param tenCd 店舗コード
     * @param bmnCd 部門コード
    *  @return クライアントへ返却する結果
     */
    public Result query(String kaisyaCd, String jigyobuCd, String outputKbn, String tenCd, String bmnCd) {
        @SuppressWarnings({"unchecked", "rawtypes" })
        Form<Zijp1080CondForm> emptyForm = new Form(Zijp1080CondForm.class);
        Form<Zijp1080CondForm> reqForm = emptyForm.bindFromRequest();
        if (reqForm.hasErrors()) {
            errRes.addErrorInfo(reqForm.errors());
            return badRequest(Json.toJson(errRes));
        } else {
            Zijp1080CondForm req = reqForm.get();
            req.setKaisyaCd(kaisyaCd);
            req.setJigyobuCd(jigyobuCd);
            req.setBmnCd(bmnCd);
            req.setOutputKbn(outputKbn);
            req.setTenCd(tenCd);
            if (!doCheckData(req)) {
                return badRequest(Json.toJson(errRes));
            }

            CCJobLauncher launch = new CCJobLauncher();

            // 指定JOBの実行、戻り値取得
            int intRet = launch.exec(createCommandText(req));
            if (intRet == 0) {
                // 正常終了メッセージ表示
                List<Zijp1080ResultForm> resultList = new ArrayList<Zijp1080ResultForm>();
                Zijp1080ResultForm resultForm = new Zijp1080ResultForm();
                errRes.addErrorInfo(CCMessageConst.MSG_KEY_STRADING_STOCK_DESTINATION_CSV_OUTPUT);
                resultForm.setInfoRes(errRes);
                resultList.add(resultForm);
                return ok(Json.toJson(resultList));
            } else {
                // 異常終了メッセージ表示
                errRes.addErrorInfo(CCMessageConst.MSG_KEY_STRADING_STOCK_DESTINATION_CSV_OUTPUT_FAIL);
                return badRequest(Json.toJson(errRes));
            }

        }
    }

    /**
     * データをチェックする。
     * @param req Zijp1080CondForm
     * @return boolean
     */
    private boolean doCheckData(Zijp1080CondForm req) {

        String sKaisyaCode = req.getKaisyaCd();
        String sJigyobuCode = req.getJigyobuCd();
        String sOutputKbn = req.getOutputKbn();
        String sTenpoCode = req.getTenCd();
        String sBumonCode = req.getBmnCd();
        String unyoDate = context.getUnyoDate();
        // ダミー店舗チェック
        String sDammyTenpo = ccSystemContext.getContextProperty(KEY_DUMMY_TEN_CD);
        boolean bKeyCheck = true;

        // 会社存在チェック
        if (!ccCheckExistsUtil.existsKaisyaCd(unyoDate, sKaisyaCode)) {
            errRes.addErrorInfo(CCMessageConst.MSG_KEY_RESULT_NOT_EXISTS);
            return false;
        }
        // 事業部存在チェック
        if (!ccCheckExistsUtil.existsKaisyaCdAndJigyobuCd(unyoDate, sKaisyaCode, sJigyobuCode)) {
            errRes.addErrorInfo(CCMessageConst.MSG_KEY_VALIDATION_ERROR);
            errRes.addErrorInfo("jigyobuCd", CCMessageConst.MSG_KEY_RESULT_NOT_EXISTS);
            return false;
        }
        // 店舗存在チェック
        if (!"999".equals(sTenpoCode)) {
            if (!ccCheckExistsUtil.existsTenCd(unyoDate, sKaisyaCode + sJigyobuCode + sTenpoCode)) {
                errRes.addErrorInfo(CCMessageConst.MSG_KEY_VALIDATION_ERROR);
                errRes.addErrorInfo("tenCd", CCMessageConst.MSG_KEY_RESULT_NOT_EXISTS);
                return false;
            }
        }
        // ダミー店舗チェック
        if (!"999".equals(sTenpoCode)) {
            if (CCStringUtil.isEmpty(sDammyTenpo)) {
                errRes.addErrorInfo(CCMessageConst.MSG_KEY_VALIDATION_ERROR);
                errRes.addErrorInfo("tenCd", CCMessageConst.MSG_KEY_DUMMY_TEN_CD_NULL);
                return false;
            }
            if (!CCStringUtil.isEmpty(sDammyTenpo)) {
                if (Integer.parseInt(sTenpoCode) >= Integer.parseInt(sDammyTenpo)) {
                    // 「指定された店舗コードは入力できません。」
                    errRes.addErrorInfo(CCMessageConst.MSG_KEY_VALIDATION_ERROR);
                    errRes.addErrorInfo("tenCd", CCMessageConst.MSG_KEY_TEN_CD_GREATER_OR_EQUAL_DAMMY_TENPO);
                    return false;
                }
            }
        }
        // 部門存在チェック
        if (!"999".equals(sBumonCode)) {
            if (!ccCheckExistsUtil.existsBmnCd(unyoDate, sJigyobuCode + sBumonCode)) {
                errRes.addErrorInfo(CCMessageConst.MSG_KEY_VALIDATION_ERROR);
                errRes.addErrorInfo("bmnCd", CCMessageConst.MSG_KEY_RESULT_NOT_EXISTS);
                return false;
            }
        }
        // 出力区分入力チェック
        if (!CCKubunConst.KBN_VAL_Z_OUTPUT_KBN_BUYERS.equals(sOutputKbn)
                && !CCKubunConst.KBN_VAL_Z_OUTPUT_KBN_CHIEF.equals(sOutputKbn)
                && !CCKubunConst.KBN_VAL_Z_OUTPUT_KBN_BANKRUPTCY.equals(sOutputKbn)) {
            errRes.addErrorInfo(CCMessageConst.MSG_KEY_VALIDATION_ERROR);
            errRes.addErrorInfo("bmnCd", CCMessageConst.MSG_KEY_INPUT_VALUE_INCORRECT);
            return false;
        }

        // ------------------------------
        // 在庫データ存在チェック
        // ------------------------------
        HashMap<String, String> param = new HashMap<String, String>();
        param.put("tenpoCode", sTenpoCode);
        param.put("kaisyaCd", sKaisyaCode);
        param.put("jigyobuCd", sJigyobuCode);
        param.put("tenCd", sTenpoCode);
        param.put("bmnCd", sBumonCode);
        param.put("dammyTenpo", sDammyTenpo);
        List<String> lstData = this.myBatisDao.selectMany("selectZ006shnzMaxTenCd", param, String.class);
        if (lstData.size() == 0 || CCStringUtil.isEmpty(lstData.get(0))) {
            // 対象データ０件
            errRes.addErrorInfo(CCMessageConst.MSG_KEY_NO_FOUND_DATA);
            return false;
        }
        return bKeyCheck;
    }

    /**
     * コマンドテキスト作成.
     * @param req 取引先別在庫金額一覧のCondFormクラス
     * @return コマンドテキスト
     */
    private String createCommandText(Zijp1080CondForm req) {
        String command = context.getContextProperty(JOB_PATH);
        command += SPACE;
        command += BATCH_ID;
        
        // 端末ID
        command += SPACE;
        command += context.getTermId();
        // バッチID
        command += SPACE;
        command += BATCH_ID;
        // 担当者コード
        command += SPACE;
        command += context.getTantoCode();

        // 第一引数 会社コード＋事業部＋出力区分＋店舗ｺｰﾄﾞ＋部門ｺｰﾄﾞ＋担当者コード
        command += SPACE;
        command += req.getKaisyaCd();
        command += "/";
        command += req.getJigyobuCd();
        command += "/";
        command += req.getOutputKbn();
        command += "/";
        command += req.getTenCd();
        command += "/";
        command += req.getBmnCd();
        command += "/";
        command += context.getTantoCode();
        return command;
    }

}
