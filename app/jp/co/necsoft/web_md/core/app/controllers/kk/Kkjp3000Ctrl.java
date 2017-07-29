///////////////////////////////////////////////////////////////////////
//Copyright(C) 2014 NEC Soft, Ltd. All Rights Reserved.
///////////////////////////////////////////////////////////////////////

/* ====================================================================
 * 機能名称 : 仕入本締後処理
 * 改版履歴
 * Rev. 改版年月日   改版者名       内容
 * 1.0   2014.09.29   NES田中      新規作成
 * =================================================================== */
package jp.co.necsoft.web_md.core.app.controllers.kk;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import jp.co.necsoft.web_md.core.app.forms.kk.Kkjp3000ResultForm;
import jp.co.necsoft.web_md.core.common.CCMessageConst;
import jp.co.necsoft.web_md.core.common.CCSecurityAuthenticator;
import jp.co.necsoft.web_md.core.common.CCSystemContext;
import jp.co.necsoft.web_md.core.common.forms.ErrorResponse;
import jp.co.necsoft.web_md.core.common.utils.CCCheckExistsUtil;
import jp.co.necsoft.web_md.core.common.utils.CCDateUtil;
import jp.co.necsoft.web_md.core.common.utils.CCJobLauncher;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;

/**
 * 仕入本締後処理のControllerクラス
 *
 */
@Security.Authenticated(CCSecurityAuthenticator.class)
public class Kkjp3000Ctrl extends Controller {
    /** Space String  */
    private static final String SPACE = " ";
    /** Job path */
    private static final String JOB_PATH = "cc.app.cm.job_path";
    /** 会社コード */
    private static final String INPUT_KAISYA_CD = "kaisyaCd";
    /** 対象年 */
    private static final String INPUT_TAISYO_NEN ="taisyodateY";
    /** 対象月 */
    private static final String INPUT_TAISYO_TSUKI ="taisyodateM";
    /** バッチＩＤ */
    private static final String BATCH_ID = "JKK010";
    /** エラー応答 */
    @Inject
    private ErrorResponse errRes;
    /** システムコンテキスト */
    @Inject
    private CCSystemContext context;

    @Inject
    private CCCheckExistsUtil ccCheckExistsUtil;

    /**
     * 引数とリクエスト(パラメータ)を条件にDBからデータ(複数件)を検索し返却する。*
     * @return クライアントへ返却する結果
     */
    public Result query(String kaisyaCd, String taisyodateY, String taisyodateM) {

        if (CheckError(kaisyaCd, taisyodateY, taisyodateM)) {
            return badRequest(Json.toJson(errRes));
        }
        CCJobLauncher launch = new CCJobLauncher();

        // 指定JOBの実行、戻り値取得
        int intRet = launch.exec(createCommandText(kaisyaCd ,taisyodateY, taisyodateM));
        if (intRet == 0) {
            // 正常終了メッセージ表示
            List<Kkjp3000ResultForm> resultList = new ArrayList<Kkjp3000ResultForm>();
            Kkjp3000ResultForm resultForm = new Kkjp3000ResultForm();
            errRes.addErrorInfo(CCMessageConst.MSG_KEY_PROCESS_JOB_SUCCESS);
            resultForm.setInfoRes(errRes);

            resultList.add(resultForm);

            return ok(Json.toJson(resultList));

        } else {
            // 異常終了メッセージ表示
            errRes.addErrorInfo(CCMessageConst.MSG_KEY_PROCESS_JOB_FAIL);
            return badRequest(Json.toJson(errRes));
        }
    }


    /**
     * データのエラーチェック.
     *
     * @param kaisyaCd 会社コード
     * @param yyyy 月次処理対象年
     * @param mm 月次処理対象月
     * @return boolean true(正常) / false(異常)
     */

    private boolean CheckError(String kaisyaCd, String yyyy, String mm) {
        boolean isError = false;

        //月次処理年月初日
        String sDate = yyyy + mm + "01";

        // 会社存在チェック
        if (!ccCheckExistsUtil.existsKaisyaCd(sDate, kaisyaCd)) {
            errRes.addErrorInfo(INPUT_KAISYA_CD, CCMessageConst.MSG_KEY_NOT_REGISTERED_NO_PARAM);
            isError = true;
            }

        // 月次処理対象年月妥当性チェック
        if (!CCDateUtil.isDate(sDate)) {
            errRes.addErrorInfo(INPUT_TAISYO_NEN, CCMessageConst.MSG_KEY_DATE_ERROR);
            errRes.addErrorInfo(INPUT_TAISYO_TSUKI, CCMessageConst.MSG_KEY_DATE_ERROR);
            isError = true;
            }

            return isError;

    }



    /**
     * コマンドテキスト作成.
     * @return コマンドテキスト
     */
    private String createCommandText(String kaisyaCd, String yyyy, String mm) {
        String command = context.getContextProperty(JOB_PATH);

        command += SPACE;
        command += BATCH_ID;

        // 第一引数端末ＩＤ(8桁)
        command += SPACE;
        command += context.getTermId();    
        // 第二引数 バッチＩＤ(6桁)
        command += SPACE;
        command += BATCH_ID;
        // 担当者コード
        command += SPACE;
        command += context.getTantoCode();
        // 第三引数 会社コード+月次処理対象年月
        command += SPACE;
        command += kaisyaCd;
        command += yyyy;
        command += mm;

        return command;
    }
}
