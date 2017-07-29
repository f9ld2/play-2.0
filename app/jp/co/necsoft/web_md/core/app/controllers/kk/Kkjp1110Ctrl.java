// /////////////////////////////////////////////////////////////////////
// Copyright(C) 2015 NEC Solution Innovators, Ltd. All Rights Reserved.
// /////////////////////////////////////////////////////////////////////

/*
 * ====================================================================
 * 
 * 機能名称 : 買掛金元帳
 * 
 * 改版履歴
 * 
 * Rev. 改版年月日 改版者名 内容
 * 
 * 1.0 2014-05-22 TUCTVZ 新規作成
 * 
 * ===================================================================
 */
package jp.co.necsoft.web_md.core.app.controllers.kk;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import jp.co.necsoft.web_md.core.app.forms.kk.Kkjp1110CondForm;
import jp.co.necsoft.web_md.core.app.forms.kk.Kkjp1110ResultForm;
import jp.co.necsoft.web_md.core.common.CCMessageConst;
import jp.co.necsoft.web_md.core.common.CCSecurityAuthenticator;
import jp.co.necsoft.web_md.core.common.CCSystemContext;
import jp.co.necsoft.web_md.core.common.biz.kk.CCKKCommon;
import jp.co.necsoft.web_md.core.common.forms.ErrorInfo;
import jp.co.necsoft.web_md.core.common.forms.ErrorResponse;
import jp.co.necsoft.web_md.core.common.mybatis.dao.K002kruiMapper;
import jp.co.necsoft.web_md.core.common.mybatis.dto.K002kruiExample;
import jp.co.necsoft.web_md.core.common.utils.CCDateUtil;
import jp.co.necsoft.web_md.core.common.utils.CCJobLauncher;
import play.data.Form;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;

/**
*買掛金元帳のControllerクラス
*
*/
@Security.Authenticated(CCSecurityAuthenticator.class)
public class Kkjp1110Ctrl extends Controller {
    /** Space String  */
    private static final String SPACE = " ";
    /** Job path */
    private static final String JOB_PATH = "cc.app.cm.job_path";
    /** バッチＩＤ */
    private static final String BATCH_ID = "KKBT1110";
    /** エラー応答 */
    @Inject
    private ErrorResponse errRes;
    /** システムコンテキスト */
    @Inject
    private CCSystemContext context;
    /** CCKKCommon */
    @Inject
    private CCKKCommon ccKKCommon;
    /**  K002kruiのマッパー */
    @Inject
    private K002kruiMapper k002kruiMapper;

    /**
     * データのエラーチェック.
     * @param req  在庫月次実行指示のCondFormクラス
     * @return boolean true(正常) / false(異常)
     */
    private boolean doCheckData(Kkjp1110CondForm req) {

        String sTaisyoDateFY = req.getTaisyoY();
        String sTaisyoDateFM = req.getTaisyoM();

        // 対象月Fromの日付妥当性チェック
        if (!CCDateUtil.isDate(sTaisyoDateFY + sTaisyoDateFM + "01")) {
            // エラーメッセージを表示し、フォーカスをセットする。
            errRes.addErrorInfo("taisyoY", CCMessageConst.MSG_KEY_DATE_ERROR);
            return false;
        }

        return true;
    }

    /**
     *　買掛累積ファイルのデータ有無を取得.
     * @param req  無し
     * @return true：データ有り、false：データ無し
     * */
    public boolean isK002KRUI(Kkjp1110CondForm req) {

        K002kruiExample example = new K002kruiExample();
        example.createCriteria().andKaisyaCdEqualTo(req.getKaisyaCd())
                .andGetujYmEqualTo(req.getTaisyoY() + req.getTaisyoM());
        int iRecCnt = this.k002kruiMapper.countByExample(example);
        if (iRecCnt == 0) {
            return false;
        }
        return true;
    }

    /**
     *  引数とリクエスト(パラメータ)を条件にDBからデータ(複数件)を検索し返却する。*
     * @param taisyoY 対象年
     * @param taisyoM 月
     *  @return クライアントへ返却する結果
     */
    public Result query(String taisyoY, String taisyoM) {
        @SuppressWarnings({"unchecked", "rawtypes" })
        Form<Kkjp1110CondForm> emptyForm = new Form(Kkjp1110CondForm.class);
        Form<Kkjp1110CondForm> reqForm = emptyForm.bindFromRequest();
        if (reqForm.hasErrors()) {
            errRes.addErrorInfo(reqForm.errors());
            return badRequest(Json.toJson(errRes));
        } else {
            Kkjp1110CondForm req = reqForm.get();
            req.setKaisyaCd(ccKKCommon.getKaisyaCode());
            req.setTaisyoY(taisyoY);
            req.setTaisyoM(taisyoM);
            if (!doCheckData(req)) {
                ErrorInfo error = errRes.getErrorInfo(CCMessageConst.MSG_KEY_VALIDATION_ERROR);
                errRes.getErrors().add(0, error);
                return badRequest(Json.toJson(errRes));
            }

            if (!isK002KRUI(req)) {
                return notFound();
            }
            CCJobLauncher launch = new CCJobLauncher();

            // 指定JOBの実行、戻り値取得
            int intRet = launch.exec(createCommandText(req));

            if (intRet == 0) {
                // 正常終了メッセージ表示
                List<Kkjp1110ResultForm> resultList = new ArrayList<Kkjp1110ResultForm>();
                Kkjp1110ResultForm resultForm = new Kkjp1110ResultForm();
                errRes.addErrorInfo(CCMessageConst.MSG_KEY_PROCESS_ACCOUNTS_PAYABLE_CREATION_START);
                resultForm.setInfoRes(errRes);

                resultList.add(resultForm);
                return ok(Json.toJson(resultList));
            } else {
                // 異常終了メッセージ表示
                errRes.addErrorInfo(CCMessageConst.MSG_KEY_PROCESS_ACCOUNTS_PAYABLE_CREATION_FAIL);
                return badRequest(Json.toJson(errRes));
            }
        }
    }

    /**
     * コマンドテキスト作成.
     * @param req  在庫月次実行指示のCondFormクラス
     * @return コマンドテキスト
     */
    private String createCommandText(Kkjp1110CondForm req) {
        String sDateFrom = req.getTaisyoY() + req.getTaisyoM() + "01";
        String sDateTo = CCDateUtil.doCalcDate(sDateFrom, "DAY_OF_MONTH", 1);
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
        // 第一引数 会社コード＋対象月(年)＋対象月(月)＋対象月の月初日＋対象月の月末日＋担当者コード
        command += SPACE;
        command += req.getKaisyaCd();
        command += req.getTaisyoY();
        command += req.getTaisyoM();
        command += sDateFrom;
        command += sDateTo;
        command += context.getTantoCode();

        return command;
    }

}
