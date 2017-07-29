// /////////////////////////////////////////////////////////////////////
// Copyright(C) 2015 NEC Solution Innovators, Ltd. All Rights Reserved.
// /////////////////////////////////////////////////////////////////////

/* ====================================================================
 * 機能名称 : 移動伝票入力 
 * 改版履歴
 * Rev. 改版年月日   改版者名       内容
 * 1.0   2014.04.05   Hungtb      新規作成
 * =================================================================== */
package jp.co.necsoft.web_md.core.app.controllers.si;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import jp.co.necsoft.web_md.common.ChaseException;
import jp.co.necsoft.web_md.common.biz.IPluConverter;
import jp.co.necsoft.web_md.common.mybatis.dao.MyBatisSqlMapDao;
import jp.co.necsoft.web_md.core.app.dto.si.Sijp0040Msai;
import jp.co.necsoft.web_md.core.app.forms.si.Sijp0040CondForm;
import jp.co.necsoft.web_md.core.app.forms.si.Sijp0040ResultForm;
import jp.co.necsoft.web_md.core.common.CCMessageConst;
import jp.co.necsoft.web_md.core.common.CCSecurityAuthenticator;
import jp.co.necsoft.web_md.core.common.CCSystemContext;
import jp.co.necsoft.web_md.core.common.biz.si.CCSICommon;
import jp.co.necsoft.web_md.core.common.biz.si.CCSIConst;
import jp.co.necsoft.web_md.core.common.forms.ErrorInfo;
import jp.co.necsoft.web_md.core.common.forms.ErrorResponse;
import jp.co.necsoft.web_md.core.common.mybatis.dao.M003bmnmMapper;
import jp.co.necsoft.web_md.core.common.mybatis.dao.M004tbumMapper;
import jp.co.necsoft.web_md.core.common.mybatis.dao.M007kijmMapper;
import jp.co.necsoft.web_md.core.common.mybatis.dao.M009msymMapper;
import jp.co.necsoft.web_md.core.common.mybatis.dao.S003fridMapper;
import jp.co.necsoft.web_md.core.common.mybatis.dao.S007fritMapper;
import jp.co.necsoft.web_md.core.common.mybatis.dao.S016frmrMapper;
import jp.co.necsoft.web_md.core.common.mybatis.dao.S029fripMapper;
import jp.co.necsoft.web_md.core.common.mybatis.dao.S048frihMapper;
import jp.co.necsoft.web_md.core.common.mybatis.dto.M003bmnm;
import jp.co.necsoft.web_md.core.common.mybatis.dto.M003bmnmExample;
import jp.co.necsoft.web_md.core.common.mybatis.dto.M004tbum;
import jp.co.necsoft.web_md.core.common.mybatis.dto.M004tbumExample;
import jp.co.necsoft.web_md.core.common.mybatis.dto.M007kijm;
import jp.co.necsoft.web_md.core.common.mybatis.dto.M007kijmExample;
import jp.co.necsoft.web_md.core.common.mybatis.dto.M009msym;
import jp.co.necsoft.web_md.core.common.mybatis.dto.M009msymExample;
import jp.co.necsoft.web_md.core.common.mybatis.dto.S003frid;
import jp.co.necsoft.web_md.core.common.mybatis.dto.S003fridExample;
import jp.co.necsoft.web_md.core.common.mybatis.dto.S007frit;
import jp.co.necsoft.web_md.core.common.mybatis.dto.S016frmr;
import jp.co.necsoft.web_md.core.common.mybatis.dto.S016frmrExample;
import jp.co.necsoft.web_md.core.common.mybatis.dto.S029frip;
import jp.co.necsoft.web_md.core.common.mybatis.dto.S048frih;
import jp.co.necsoft.web_md.core.common.mybatis.dto.S048frihExample;
import jp.co.necsoft.web_md.core.common.utils.CCDateUtil;
import jp.co.necsoft.web_md.core.common.utils.CCNumberUtil;
import jp.co.necsoft.web_md.core.common.utils.CCStringUtil;
import jp.co.necsoft.web_md.core.common.utils.DataUtil;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.mybatis.guice.transactional.Transactional;
import org.springframework.beans.BeanUtils;

import play.data.Form;
import play.i18n.Messages;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;

/**
*移動伝票入力
*のControllerクラス
*
*/
@Security.Authenticated(CCSecurityAuthenticator.class)
public class Sijp0040Ctrl extends Controller {

    /** EMPTY = "" */
    private static final String EMPTY = "";
    /** YODOKBN_1 = "1" */
    private static final String YODOKBN_1 = "1";
    /** YODOKBN_9 = "9" */
    private static final String YODOKBN_9 = "9";
    /** YODOKBN_9 = "9" */
    private static final String KBN_VAL_M_TENANT_KBN_BMNMST_TENANT = "1";

    /** errRes: error response. */
    @Inject
    private ErrorResponse errRes;
    /** context */
    @Inject
    private CCSystemContext context;
    /** dao: DAO for pure sql mapping. */
    @Inject
    private MyBatisSqlMapDao dao;
    /** m004tbumMapper: dao for table m004tbum */
    @Inject
    private M004tbumMapper m004tbumMapper;
    /** M003bmnmMapper: dao for table m003bmn */
    @Inject
    private M003bmnmMapper m003bmnmMapper;
    /** S003fridMapper: dao for table s003frid */
    @Inject
    private S003fridMapper s003fridMapper;
    /** M007kijmMapper */
    @Inject
    private M007kijmMapper m007kijmMapper;
    /** S007fritMapper */
    @Inject
    private S007fritMapper s007fritMapper;
    /** S029fripMapper */
    @Inject
    private S029fripMapper s029fripMapper;
    /** S016frmrMapper */
    @Inject
    private S016frmrMapper s016frmrMapper;
    /** s048frihMapper */
    @Inject
    private S048frihMapper s048frihMapper;
    /** M009msymMapper */
    @Inject
    private M009msymMapper m009msymMapper;

    /** Common file. */
    @Inject
    private CCSICommon cmJSICommon;
    
    /** IPlu Converter */
    @Inject
    private IPluConverter plucnv;

    /*
     * SIJP0040?dpyKbn=34&kaisyaCd=00&jigyobuCd=03&dpyNo=015276538&tenCd=0003000&bmnCd=03350&torihikiCd=0003024
     * &nhnYmd=20080314&outBmnCd=03350
     */
    /** SIJP0110 params: {"dpyKbn", "kaisyaCd", "jigyobuCd", "dpyNo", "tenCd", "bmnCd", "torihikiCd", 
     * "nhnYmd", "outBmnCd" } */
    private static final String[] KEY_PARAMS = {"dpyKbn", "kaisyaCd", "jigyobuCd", "dpyNo", "tenCd", "bmnCd",
            "torihikiCd", "nhnYmd", "outBmnCd" };

    /**
     * 規定値とか伝票ヘッダ照会処理遷移した値で初期画面処理を行います。.
     * 
     * @return Result: レスポンス
     */
    public Result initCond() {
        // 初期表示
        // 画面項目[出荷日]に""で初期とする。
        String tantoCode = context.getTantoCode();
        String tenpo = cmJSICommon.getDefaultTenpo(tantoCode);

        Sijp0040CondForm form = new Sijp0040CondForm();
        // 画面項目[伝区] = cmJSIConst.DEN_KBN.KBN_IDO
        form.setDpyKbn(CCSIConst.DEN_KBN.KBN_IDO);
        // 画面項目[出荷店.会社] = 変数.ログイン店舗のsubstring(0,2)
        form.setKaisyaCd(tenpo.substring(0, 2));
        // 画面項目[入荷店.会社] = 変数.ログイン店舗のsubstring(0,2)
        form.setSubKaisyaCd(tenpo.substring(0, 2));

        Map<String, String[]> query = request().queryString();
        if (query.size() >= 9) {
            if (query.containsKey(KEY_PARAMS[0])) {
                form.setDpyKbn(query.get(KEY_PARAMS[0])[0]);
            }
            if (query.containsKey(KEY_PARAMS[1])) {
                form.setSubKaisyaCd(query.get(KEY_PARAMS[1])[0]);
            }
            if (query.containsKey(KEY_PARAMS[3])) {
                String dpyNo = query.get(KEY_PARAMS[3])[0];
                form.setDpyNo(dpyNo);
            }
            if (query.containsKey(KEY_PARAMS[4])) {
                String tenCd = query.get(KEY_PARAMS[4])[0];
                form.setSubTenCd(tenCd);
            }
            if (query.containsKey(KEY_PARAMS[5])) {
                String bmnCd = query.get(KEY_PARAMS[5])[0];
                form.setSubJigyobuCd(bmnCd.substring(0, 2));
                form.setSubBmnCd(bmnCd.substring(2, 5));
            }
            if (query.containsKey(KEY_PARAMS[6])) {
                String torihikiCd = query.get(KEY_PARAMS[6])[0];
                form.setKaisyaCd(torihikiCd.substring(0, 2));
                form.setJigyobuCd(torihikiCd.substring(3, 5));
                form.setTenCd(torihikiCd.substring(6, 9));
            }
            if (query.containsKey(KEY_PARAMS[7])) {
                String nhnYmd = query.get(KEY_PARAMS[7])[0];
                form.setSyukaYmd(nhnYmd);
            }
            if (query.containsKey(KEY_PARAMS[8])) {
                String outBmnCd = query.get(KEY_PARAMS[8])[0];

                form.setBmnCd(outBmnCd.substring(2, 5));
            }
        }

        return ok(Json.toJson(form));
    }

    /**
     * 引数とリクエスト(パラメータ)を条件にDBからデータ(複数件)を検索し返却する。*
     * 
     * @param dpyKbn 伝区
     * @param dpyNo 伝票NO
     * @param outKaisyaCd 出荷店.会社
     * @param outJigyobuCd 出荷店.事業部
     * @param outTenCd 出荷店.店舗
     * @param outBmnCd 出荷店.部門
     * @param inKaisyaCd 入荷店.会社
     * @param inJigyobuCd 入荷店.事業部
     * @param inTenCd 入荷店.店舗
     * @param inBmnCd 入荷店.部門
     * @return クライアントへ返却する結果
     */
    public Result query(String dpyKbn, String dpyNo, String outKaisyaCd, String outJigyobuCd, String outTenCd,
            String outBmnCd, String inKaisyaCd, String inJigyobuCd, String inTenCd, String inBmnCd) {
        Form<Sijp0040CondForm> emptyForm = new Form<Sijp0040CondForm>(Sijp0040CondForm.class);
        Form<Sijp0040CondForm> reqForm = emptyForm.bindFromRequest();
        if (reqForm.hasErrors()) {
            errRes.addErrorInfo(CCMessageConst.MSG_KEY_VALIDATION_ERROR);
            errRes.addErrorInfo(reqForm.errors());
            return badRequest(Json.toJson(errRes));
        } else {
            // 画面項目[出荷日]に""で初期とする。
            String tantoCode = context.getTantoCode();
            String tenpo = cmJSICommon.getDefaultTenpo(tantoCode);

            Sijp0040CondForm form = reqForm.get();
            form.setDpyKbn(dpyKbn);
            form.setDpyNo(dpyNo);
            form.setKaisyaCd(outKaisyaCd);
            form.setJigyobuCd(outJigyobuCd);
            form.setTenCd(outTenCd);
            form.setBmnCd(outBmnCd);
            form.setSubKaisyaCd(inKaisyaCd);
            form.setSubJigyobuCd(inJigyobuCd);
            form.setSubTenCd(inTenCd);
            form.setSubBmnCd(inBmnCd);

            // 1．ヘッダ情報の入力チェック
            boolean flag = true;
            // Match old system: if (flag = 0) search(3) else (flag = 1) edit(1)
            flag =
                    checkConditionForm(form, tenpo, form.getSearchFlg() == 0 ? CCSICommon.CN_DISPLAY
                            : CCSICommon.CN_INSERT);

            // 処理の戻る値が(=false)の場合
            if (!flag) {
                ErrorInfo err = errRes.getErrorInfo(CCMessageConst.MSG_KEY_VALIDATION_ERROR);
                errRes.getErrors().add(0, err);
                // 検索/編集処理を終了する。
                return badRequest(Json.toJson(errRes));
            }
            String inTenpoCd = form.getSubKaisyaCd() + form.getSubJigyobuCd() + form.getSubTenCd();
            String inBmnCd5 = form.getSubJigyobuCd() + form.getSubBmnCd();
            String outTenpoCd = form.getKaisyaCd() + form.getJigyobuCd() + form.getTenCd();
            String outBmnCd5 = form.getJigyobuCd() + form.getBmnCd();
            // 2．データ取得処理
            List<S003frid> s003frids = queryData(dpyNo, dpyKbn, inTenpoCd, inBmnCd5, outTenpoCd, outBmnCd5);
            // 処理の戻る値が(=false)の場合
            if (s003frids.size() == 0) {
                // ==========
                // データ取得(未存在時はエラー)
                // if (!sibn0040.execute(cmJSICommon.CN_DISPLAY)) {
                // ==========
                if (form.getSearchFlg() == 0) {
                    flag = false;
                    return notFound(Json.toJson(errRes));
                } else {
                    // [C0201] = Mode INSERT
                    flag = true;
                    return notFound(Json.toJson(errRes));
                }
            }

            // 3．[排他キーコード]
            // 検索結果表示
            S003frid frid = s003frids.get(0);

            if (form.getSearchFlg() == 1) {
                String sStatus = frid.getSyoriStsKbn();
                // sStatus.equals(cmJSIConst.SYORISTS_KBN.KBN_DELETE)
                if (CCSIConst.SYORISTS_KBN.KBN_DELETE.equals(sStatus)) {
                    flag = true;
                    return notFound(Json.toJson(errRes));
                }
            }

            Sijp0040ResultForm result = getData(frid, form.getSearchFlg());

            S048frihExample s048frihExample = new S048frihExample();
            s048frihExample.createCriteria().andDpyNoEqualTo(dpyNo);

            List<S048frih> s048frihList = s048frihMapper.selectByExample(s048frihExample);

            this.addDataS048(result, s048frihList);

            List<Sijp0040ResultForm> resultList = new ArrayList<Sijp0040ResultForm>();
            resultList.add(result);
            return ok(Json.toJson(resultList));
        }
    }

    /**
     * @param result result
     * @param s048frihList s048frihList
     */
    private void addDataS048(Sijp0040ResultForm result, List<S048frih> s048frihList) {
        List<Sijp0040Msai> dataArea = result.getDataArea();

        for (int i = 0; i < 10; i++) {
            String inx = ((i < 9) ? "0" : "") + (i + 1);
            dataArea.get(i).setKppinRiyuKbn(
                    s048frihList.isEmpty() ? "" : (String) DataUtil.getValueByMethodName(s048frihList.get(0),
                            "getKppinRiyuKbn" + inx));
        }
    }

    /**
     * 引数とリクエスト(ボディ)からレコードを編集しDBに登録(論理)する。*
     * 
     * @param dpyKbn 伝区
     * @param dpyNo 伝票NO
     * @param outKaisyaCd 出荷店.会社
     * @param outJigyobuCd 出荷店.事業部
     * @param outTenCd 出荷店.店舗
     * @param outBmnCd 出荷店.部門
     * @param inKaisyaCd 入荷店.会社
     * @param inJigyobuCd 入荷店.事業部
     * @param inTenCd 入荷店.店舗
     * @param inBmnCd 入荷店.部門
     * @return クライアントへ返却する結果
     */
    public Result save(String dpyKbn, String dpyNo, String outKaisyaCd, String outJigyobuCd, String outTenCd,
            String outBmnCd, String inKaisyaCd, String inJigyobuCd, String inTenCd, String inBmnCd) {
        Form<Sijp0040ResultForm> emptyForm = new Form<Sijp0040ResultForm>(Sijp0040ResultForm.class);
        Form<Sijp0040ResultForm> reqForm = emptyForm.bindFromRequest();
        if (reqForm.hasErrors()) {
            errRes.addErrorInfo(CCMessageConst.MSG_KEY_VALIDATION_ERROR);
            errRes.addErrorInfo(reqForm.errors());
            return badRequest(Json.toJson(errRes));
        } else {
            Sijp0040ResultForm form = reqForm.get();

            String tantoCode = context.getTantoCode();
            String tenpo = cmJSICommon.getDefaultTenpo(tantoCode);

            Sijp0040CondForm cond = new Sijp0040CondForm();
            cond.setSyukaYmd(form.getSyukaYmd());
            cond.setDpyKbn(dpyKbn);
            cond.setDpyNo(dpyNo);
            cond.setKaisyaCd(outKaisyaCd);
            cond.setJigyobuCd(outJigyobuCd);
            cond.setTenCd(outTenCd);
            cond.setBmnCd(outBmnCd);
            cond.setSubKaisyaCd(inKaisyaCd);
            cond.setSubJigyobuCd(inJigyobuCd);
            cond.setSubTenCd(inTenCd);
            cond.setSubBmnCd(inBmnCd);

            String syoriStsKkbn = form.getSyoriStsKbn();
            // 処理状態 <> cmJSIConst.SYORISTS_KBN.KBN_KAKTEI場合
            if (!CCSIConst.SYORISTS_KBN.KBN_MIKAKTEI.equals(syoriStsKkbn)) {
                // 「S1020」メッセージが表示される。
                // ※メッセージ埋め込み文字："更新"
                // 削除処理を終了する。
                errRes.addErrorInfo(CCMessageConst.MSG_KEY_CHECK_PROCESS_DATA);
                return badRequest(Json.toJson(errRes));
            }

            boolean flag = checkConditionForm(cond, tenpo, CCSICommon.CN_INSERT);

            // 処理の戻る値が(=false)の場合
            if (!flag) {
                ErrorInfo err = errRes.getErrorInfo(CCMessageConst.MSG_KEY_VALIDATION_ERROR);
                errRes.getErrors().add(0, err);
                // 検索/編集処理を終了する。
                return badRequest(Json.toJson(errRes));
            }

            // 1．データのエラーチェック処理
            // 処理仕様(その他関数)シートの処理④を参照。
            flag = checkData(form);
            // 保存処理を終了する。
            if (!flag) {
                ErrorInfo err = errRes.getErrorInfo(CCMessageConst.MSG_KEY_VALIDATION_ERROR);
                errRes.getErrors().add(0, err);
                return badRequest(Json.toJson(errRes));
            }

            // 2-2．S003fritチェックフラグがtrue場合
            saveData(form);
            // 2-6．正常終了後

            // 初期表示を行う。 -- Not reload init display
            // 「C0203」メッセージが表示される。
            // ※メッセージ埋め込み文字："登録"
            return created();
        }
    }

    /**
     * 引数とリクエスト(ボディ)からレコードを編集しDBの該当データを更新(論理)する。*
     * 
     * @param dpyKbn 伝区
     * @param dpyNo 伝票NO
     * @param outKaisyaCd 出荷店.会社
     * @param outJigyobuCd 出荷店.事業部
     * @param outTenCd 出荷店.店舗
     * @param outBmnCd 出荷店.部門
     * @param inKaisyaCd 入荷店.会社
     * @param inJigyobuCd 入荷店.事業部
     * @param inTenCd 入荷店.店舗
     * @param inBmnCd 入荷店.部門
     * @return クライアントへ返却する結果
     */
    public Result update(String dpyKbn, String dpyNo, String outKaisyaCd, String outJigyobuCd, String outTenCd,
            String outBmnCd, String inKaisyaCd, String inJigyobuCd, String inTenCd, String inBmnCd) {
        Form<Sijp0040ResultForm> emptyForm = new Form<Sijp0040ResultForm>(Sijp0040ResultForm.class);
        Form<Sijp0040ResultForm> reqForm = emptyForm.bindFromRequest();
        if (reqForm.hasErrors()) {
            errRes.addErrorInfo(CCMessageConst.MSG_KEY_VALIDATION_ERROR);
            errRes.addErrorInfo(reqForm.errors());
            return badRequest(Json.toJson(errRes));
        } else {
            Sijp0040ResultForm form = reqForm.get();

            String tantoCode = context.getTantoCode();
            String tenpo = cmJSICommon.getDefaultTenpo(tantoCode);

            Sijp0040CondForm cond = new Sijp0040CondForm();
            cond.setSyukaYmd(form.getSyukaYmd());
            cond.setDpyKbn(dpyKbn);
            cond.setDpyNo(dpyNo);
            cond.setKaisyaCd(outKaisyaCd);
            cond.setJigyobuCd(outJigyobuCd);
            cond.setTenCd(outTenCd);
            cond.setBmnCd(outBmnCd);
            cond.setSubKaisyaCd(inKaisyaCd);
            cond.setSubJigyobuCd(inJigyobuCd);
            cond.setSubTenCd(inTenCd);
            cond.setSubBmnCd(inBmnCd);

            // =============
            String inTenpoCd7 = inKaisyaCd + inJigyobuCd + inTenCd;
            String inBmnCd5 = inJigyobuCd + inBmnCd;
            String outTenpoCd7 = outKaisyaCd + outJigyobuCd + outTenCd;
            String outBmnCd5 = outJigyobuCd + outBmnCd;
            String syoriStsKkbn = getSyoriStsKbn(dpyKbn, dpyNo, inTenpoCd7, inBmnCd5, outTenpoCd7, outBmnCd5);
            // 処理状態 <> cmJSIConst.SYORISTS_KBN.KBN_KAKTEI場合
            if (!CCSIConst.SYORISTS_KBN.KBN_KAKTEI.equals(syoriStsKkbn)) {
                // 「S1020」メッセージが表示される。
                // ※メッセージ埋め込み文字："更新"
                // 削除処理を終了する。
                errRes.addErrorInfo(CCMessageConst.MSG_KEY_CHECK_PROCESS_DATA);
                return badRequest(Json.toJson(errRes));
            }
            // =============

            boolean flag = checkConditionForm(cond, tenpo, 2);

            // 処理の戻る値が(=false)の場合
            if (!flag) {
                ErrorInfo err = errRes.getErrorInfo(CCMessageConst.MSG_KEY_VALIDATION_ERROR);
                errRes.getErrors().add(0, err);
                // 検索/編集処理を終了する。
                return badRequest(Json.toJson(errRes));
            }

            // 1．データのエラーチェック処理
            // 処理仕様(その他関数)シートの処理④を参照。
            flag = checkData(form);
            // 処理の戻る値が(=false)の場合
            // 保存処理を終了する。
            if (!flag) {
                ErrorInfo err = errRes.getErrorInfo(CCMessageConst.MSG_KEY_VALIDATION_ERROR);
                errRes.getErrors().add(0, err);
                return badRequest(Json.toJson(errRes));
            }
            updateData(form);
            // 3-7．正常終了後

            // 初期表示を行う。-- Not reload display
            // 正常終了メッセージを表示する
            // 「C0203」メッセージが表示される。
            // ※メッセージ埋め込み文字："更新"
            return ok();
        }
    }

    /**
     * 引数とリクエスト(パラメータ)を条件にDBの該当データを削除(論理)する*
     * 
     * @param dpyKbn 伝区
     * @param dpyNo 伝票NO
     * @param outKaisyaCd 出荷店.会社
     * @param outJigyobuCd 出荷店.事業部
     * @param outTenCd 出荷店.店舗
     * @param outBmnCd 出荷店.部門
     * @param inKaisyaCd 入荷店.会社
     * @param inJigyobuCd 入荷店.事業部
     * @param inTenCd 入荷店.店舗
     * @param inBmnCd 入荷店.部門
     * @return クライアントへ返却する結果
     */
    public Result delete(String dpyKbn, String dpyNo, String outKaisyaCd, String outJigyobuCd, String outTenCd,
            String outBmnCd, String inKaisyaCd, String inJigyobuCd, String inTenCd, String inBmnCd) {
        Form<Sijp0040CondForm> emptyForm = new Form<Sijp0040CondForm>(Sijp0040CondForm.class);
        Form<Sijp0040CondForm> reqForm = emptyForm.bindFromRequest();
        if (reqForm.hasErrors()) {
            errRes.addErrorInfo(CCMessageConst.MSG_KEY_VALIDATION_ERROR);
            errRes.addErrorInfo(reqForm.errors());
            return badRequest(Json.toJson(errRes));
        } else {
            Sijp0040CondForm form = reqForm.get();
            form.setDpyKbn(dpyKbn);
            form.setDpyNo(dpyNo);
            form.setKaisyaCd(outKaisyaCd);
            form.setJigyobuCd(outJigyobuCd);
            form.setTenCd(outTenCd);
            form.setBmnCd(outBmnCd);
            form.setSubKaisyaCd(inKaisyaCd);
            form.setSubJigyobuCd(inJigyobuCd);
            form.setSubTenCd(inTenCd);
            form.setSubBmnCd(inBmnCd);

            String tantoCode = context.getTantoCode();
            String tenpo = cmJSICommon.getDefaultTenpo(tantoCode);

            boolean flag = checkConditionForm(form, tenpo, CCSICommon.CN_DELETE);

            // 処理の戻る値が(=false)の場合
            if (!flag) {
                ErrorInfo err = errRes.getErrorInfo(CCMessageConst.MSG_KEY_VALIDATION_ERROR);
                errRes.getErrors().add(0, err);
                // 検索/編集処理を終了する。
                return badRequest(Json.toJson(errRes));
            }

            // 削除ボタン
            // 1．処理状態チェック
            // 処理状態 = 画面項目[処理状態]
            String inTenpoCd7 = inKaisyaCd + inJigyobuCd + inTenCd;
            String inBmnCd5 = inJigyobuCd + inBmnCd;
            String outTenpoCd7 = outKaisyaCd + outJigyobuCd + outTenCd;
            String outBmnCd5 = outJigyobuCd + outBmnCd;

            String syoriStsKkbn = getSyoriStsKbn(dpyKbn, dpyNo, inTenpoCd7, inBmnCd5, outTenpoCd7, outBmnCd5);
            // 処理状態 <> cmJSIConst.SYORISTS_KBN.KBN_KAKTEI場合
            if (!CCSIConst.SYORISTS_KBN.KBN_KAKTEI.equals(syoriStsKkbn)) {
                // 「S1020」メッセージが表示される。
                // ※メッセージ埋め込み文字："更新"
                // 削除処理を終了する。
                errRes.addErrorInfo(CCMessageConst.MSG_KEY_CHECK_PROCESS_DATA);
                return badRequest(Json.toJson(errRes));
            }

            // 2．削除処理
            // 2-1．移動伝票入力Ｔ（前日）
            // 2.1-2．メンテトラン用伝票累積Ｆデータ取得処理
            deleteData(form);
            // 2-7．正常終了後
            // 初期表示を行う。
            // 「C0203」メッセージが表示される。
            // ※メッセージ埋め込み文字："削除"
            return ok();
        }
    }

    /**
     * 
     * @param form 資料
     */
    @Transactional
    public void saveData(Sijp0040ResultForm form) {
        // 2．新規登録の場合、クライアントからサーバにデータをPOSTする。
        // 2-1． 伝票累積Ｆ存在チェック処理
        String dpyNo = form.getDpyNo();
        String dpyKbn = form.getDpyKbn();
        String inTenpoCd = form.getSubKaisyaCd() + form.getSubJigyobuCd() + form.getSubTenCd();
        String inBmnCd = form.getSubJigyobuCd() + form.getSubBmnCd();
        String outTenpoCd = form.getKaisyaCd() + form.getJigyobuCd() + form.getTenCd();
        String outBmnCd = form.getJigyobuCd() + form.getBmnCd();

        S003fridExample s003Example = new S003fridExample();
        s003Example.createCriteria().andDpyNoEqualTo(dpyNo).andDenKbnEqualTo(dpyKbn).andInTenpoCdEqualTo(inTenpoCd)
                .andInBmnCdEqualTo(inBmnCd).andOutTenpoCdEqualTo(outTenpoCd).andOutBmnCdEqualTo(outBmnCd);

        int count = s003fridMapper.countByExample(s003Example);
        boolean s003fridChk = false;
        if (count <= 0) {
            // S003fritチェックフラグにfalseセット
            s003fridChk = false;
        } else {
            s003fridChk = true;
        }

        if (s003fridChk) {
            // 2-2-1．メンテトラン内のデータ件数取得処理
            int maxSeqNm = getSeqNoData(dpyNo, dpyKbn, inTenpoCd, inBmnCd, outTenpoCd, outBmnCd);

            // 2-2-2．移動伝票入力Ｔ（前日） ---
            List<S003frid> s003frids = queryData(dpyNo, dpyKbn, inTenpoCd, inBmnCd, outTenpoCd, outBmnCd);
            S003frid frid = s003frids.get(0);

            // 2-2-2．移動伝票入力Ｔ（前日）
            // メンテトランチェックがfalse場合
            if (maxSeqNm <= 0) {
                // 処理仕様(その他関数)シートの処理⑥を参照。
                // cmJSICommon.CN_INSERT
                // cmJSICommon.CN_REC_ZEN
                S007frit s007Ins =
                        insertS007fritFromS003frid(frid, CCSICommon.CN_INSERT, CCSICommon.CN_REC_ZEN, maxSeqNm);
                int result = s007fritMapper.insertSelective(s007Ins);
                if (result <= 0) {
                    throw new ChaseException(Messages.get(CCMessageConst.MSG_KEY_EXCEPTION_INSERT_NO_RECORD));
                }

                S029frip s029Ins = new S029frip();
                BeanUtils.copyProperties(s007Ins, s029Ins);
                result = s029fripMapper.insertSelective(s029Ins);
                if (result <= 0) {
                    throw new ChaseException(Messages.get(CCMessageConst.MSG_KEY_EXCEPTION_INSERT_NO_RECORD));
                }
            }

            // 2-2-3．移動伝票削除
            // 処理仕様(その他関数)シートの処理⑦を参照。
            S003fridExample s003DelExample = deleteS003frid(dpyNo, dpyKbn, inTenpoCd, inBmnCd, outTenpoCd, outBmnCd);
            int result = s003fridMapper.deleteByExample(s003DelExample);
            if (result <= 0) {
                throw new ChaseException(Messages.get(CCMessageConst.MSG_KEY_EXCEPTION_DELETE_NO_RECORD));
            }

            // 2-3-4．移動明細履歴Ｆ
            // 処理仕様(その他関数)シートの処理⑧を参照。
            S016frmr s016Ins = insertS016frmr(form, CCSICommon.CN_INSERT, inTenpoCd, inBmnCd, outTenpoCd, outBmnCd);
            result = s016frmrMapper.insertSelective(s016Ins);
            if (result <= 0) {
                throw new ChaseException(Messages.get(CCMessageConst.MSG_KEY_EXCEPTION_INSERT_NO_RECORD));
            }

            // 2-3-5．移動明細履歴Ｆ更新
            // 処理仕様(その他関数)シートの処理⑨を参照。
            S016frmr s016Upd = updateS016frmr(CCSIConst.SYORISTS_KBN.KBN_KAKTEI);
            S016frmrExample s016UpdExample = new S016frmrExample();
            s016UpdExample.createCriteria().andDpyNoEqualTo(dpyNo).andDenKbnEqualTo(dpyKbn)
                    .andInTenpoCdEqualTo(inTenpoCd).andInBmnCdEqualTo(inBmnCd).andOutTenpoCdEqualTo(outTenpoCd)
                    .andOutBmnCdEqualTo(outBmnCd);
            result = s016frmrMapper.updateByExampleSelective(s016Upd, s016UpdExample);
            if (result <= 0) {
                throw new ChaseException(Messages.get(CCMessageConst.MSG_KEY_EXCEPTION_UPDATE_NO_RECORD));
            }
        }

        // 2-3．移動伝票累積Ｆ
        // 処理仕様(その他関数)シートの処理⑩を参照。
        S003frid s003Ins = insertS003frid(form);
        int result = s003fridMapper.insertSelective(s003Ins);
        if (result <= 0) {
            throw new ChaseException(Messages.get(CCMessageConst.MSG_KEY_EXCEPTION_INSERT_NO_RECORD));
        }

        // 2-3．移動伝票入力Ｔ
        // 処理仕様(その他関数)シートの処理⑪を参照。
        // cmJSICommon.CN_DELETE
        // cmJSICommon.CN_REC_KON
        // 最大[ＳＥＱＮＯ]
        int maxSeqNo = getSeqNoData(dpyNo, dpyKbn, inTenpoCd, inBmnCd, outTenpoCd, outBmnCd);
        // 取得結果は[null]の場合
        // SEQNO変数に0をセット。

        // 2-4．移動伝票入力Ｔ
        // 処理仕様(その他関数)シートの処理⑪を参照。
        // cmJSICommon.CN_INSERT
        // cmJSICommon.CN_REC_KON
        S007frit s007InsNext = insertS007frit(form, CCSICommon.CN_INSERT, CCSICommon.CN_REC_KON, maxSeqNo);
        result = s007fritMapper.insertSelective(s007InsNext);
        if (result <= 0) {
            throw new ChaseException(Messages.get(CCMessageConst.MSG_KEY_EXCEPTION_INSERT_NO_RECORD));
        }

        S029frip s029InsNext = new S029frip();
        BeanUtils.copyProperties(s007InsNext, s029InsNext);
        result = s029fripMapper.insertSelective(s029InsNext);
        if (result <= 0) {
            throw new ChaseException(Messages.get(CCMessageConst.MSG_KEY_EXCEPTION_INSERT_NO_RECORD));
        }

        S048frihExample s048frihExample = new S048frihExample();
        s048frihExample.createCriteria().andDpyNoEqualTo(dpyNo);

        List<S048frih> s048frihList = s048frihMapper.selectByExample(s048frihExample);

        if (s048frihList.isEmpty()) {
            S048frih s048frih = this.setDataS048(form, true);
            s048frih.setDpyNo(dpyNo);
            result = s048frihMapper.insertSelective(s048frih);
            if (result <= 0) {
                throw new ChaseException(Messages.get(CCMessageConst.MSG_KEY_EXCEPTION_INSERT_NO_RECORD));
            }
        } else {
            S048frih s048frih = this.setDataS048(form, false);
            result = s048frihMapper.updateByExampleSelective(s048frih, s048frihExample);
            if (result <= 0) {
                throw new ChaseException(Messages.get(CCMessageConst.MSG_KEY_EXCEPTION_UPDATE_NO_RECORD));
            }
        }
    }

    /**
     * Update data to database.
     * 
     * @param form 資料
     */
    @Transactional
    public void updateData(Sijp0040ResultForm form) {
        String dpyNo = form.getDpyNo();
        String dpyKbn = form.getDpyKbn();
        String inTenpoCd = form.getSubKaisyaCd() + form.getSubJigyobuCd() + form.getSubTenCd();
        String inBmnCd = form.getSubJigyobuCd() + form.getSubBmnCd();
        String outTenpoCd = form.getKaisyaCd() + form.getJigyobuCd() + form.getTenCd();
        String outBmnCd = form.getJigyobuCd() + form.getBmnCd();
        // 3．更新の場合、クライアントからサーバにデータをPUTする。
        // 3-1．メンテトラン内のデータ件数取得処理
        // メンテトランチェック = 処理仕様(その他関数)シートの処理⑤の戻る値
        int maxSeqNm = getSeqNoData(dpyNo, dpyKbn, inTenpoCd, inBmnCd, outTenpoCd, outBmnCd);

        // 3-2-2．メンテトラン用伝票累積Ｆデータ取得処理
        List<S003frid> s003frids = queryData(dpyNo, dpyKbn, inTenpoCd, inBmnCd, outTenpoCd, outBmnCd);
        S003frid frid = s003frids.get(0);

        // TRANSACTION
        // 3-2．移動伝票入力Ｔ（前日）
        // メンテトランチェックがfalse場合
        if (maxSeqNm <= 0) {
            // 処理仕様(その他関数)シートの処理⑥を参照。
            // cmJSICommon.CN_UPDATE
            // cmJSICommon.CN_REC_ZEN
            S007frit s007Ins =
                    insertS007fritFromS003frid(frid, CCSICommon.CN_UPDATE, CCSICommon.CN_REC_ZEN, maxSeqNm);
            int result = s007fritMapper.insertSelective(s007Ins);
            if (result <= 0) {
                throw new ChaseException(Messages.get(CCMessageConst.MSG_KEY_EXCEPTION_INSERT_NO_RECORD));
            }

            S029frip s029Ins = new S029frip();
            BeanUtils.copyProperties(s007Ins, s029Ins);
            result = s029fripMapper.insertSelective(s029Ins);
            if (result <= 0) {
                throw new ChaseException(Messages.get(CCMessageConst.MSG_KEY_EXCEPTION_INSERT_NO_RECORD));
            }
        }

        // 3-3．移動伝票累積Ｆ
        // 処理仕様(その他関数)シートの処理⑫を参照。
        S003frid s003Del = updateS003frid(form);
        S003fridExample s003fridExample = new S003fridExample();
        S003fridExample.Criteria criteria = s003fridExample.createCriteria();

        criteria.andDpyNoEqualTo(dpyNo).andDenKbnEqualTo(dpyKbn).andInTenpoCdEqualTo(inTenpoCd)
                .andInBmnCdEqualTo(inBmnCd).andOutTenpoCdEqualTo(outTenpoCd).andOutBmnCdEqualTo(outBmnCd);

        // 3．S003fridMapperのupdateByExampleSelectiveを使用する。
        int result = s003fridMapper.updateByExampleSelective(s003Del, s003fridExample);
        if (result <= 0) {
            throw new ChaseException(Messages.get(CCMessageConst.MSG_KEY_EXCEPTION_UPDATE_NO_RECORD));
        }

        maxSeqNm = getSeqNoData(dpyNo, dpyKbn, inTenpoCd, inBmnCd, outTenpoCd, outBmnCd);
        // 3-4．移動伝票入力Ｔ
        // 処理仕様(その他関数)シートの処理⑪を参照。
        // cmJSICommon.CN_UPDATE
        // cmJSICommon.CN_REC_KON
        S007frit s007InsNext = insertS007frit(form, CCSICommon.CN_UPDATE, CCSICommon.CN_REC_KON, maxSeqNm);
        result = s007fritMapper.insertSelective(s007InsNext);
        if (result <= 0) {
            throw new ChaseException(Messages.get(CCMessageConst.MSG_KEY_EXCEPTION_INSERT_NO_RECORD));
        }

        S029frip s029InsNext = new S029frip();
        BeanUtils.copyProperties(s007InsNext, s029InsNext);
        result = s029fripMapper.insertSelective(s029InsNext);
        if (result <= 0) {
            throw new ChaseException(Messages.get(CCMessageConst.MSG_KEY_EXCEPTION_INSERT_NO_RECORD));
        }

        // 3-5．移動明細履歴Ｆ
        // 処理仕様(その他関数)シートの処理⑧を参照。
        // cmJSICommon.CN_UPDATE
        S016frmr s016Ins = insertS016frmr(form, CCSICommon.CN_UPDATE, inTenpoCd, inBmnCd, outTenpoCd, outBmnCd);
        result = s016frmrMapper.insertSelective(s016Ins);
        if (result <= 0) {
            throw new ChaseException(Messages.get(CCMessageConst.MSG_KEY_EXCEPTION_INSERT_NO_RECORD));
        }

        S048frihExample s048frihExample = new S048frihExample();
        s048frihExample.createCriteria().andDpyNoEqualTo(dpyNo);

        List<S048frih> s048frihList = s048frihMapper.selectByExample(s048frihExample);

        if (s048frihList.isEmpty()) {
            S048frih s048frih = this.setDataS048(form, true);
            s048frih.setDpyNo(dpyNo);
            result = s048frihMapper.insertSelective(s048frih);
            if (result <= 0) {
                throw new ChaseException(Messages.get(CCMessageConst.MSG_KEY_EXCEPTION_INSERT_NO_RECORD));
            }
        } else {
            S048frih s048frih = this.setDataS048(form, false);
            result = s048frihMapper.updateByExampleSelective(s048frih, s048frihExample);
            if (result <= 0) {
                throw new ChaseException(Messages.get(CCMessageConst.MSG_KEY_EXCEPTION_UPDATE_NO_RECORD));
            }
        }
    }

    /**
     * @param form form
     * @return S048frih
     */
    private S048frih setDataS048(Sijp0040ResultForm form, boolean isInsert) {
        S048frih s048frih = new S048frih();

        List<Sijp0040Msai> dataArea = form.getDataArea();
        for (int i = 0; i < 10; i++) {
            String inx = ((i < 9) ? "0" : "") + (i + 1);

            String value =
                    CCStringUtil.isEmpty(dataArea.get(i).getKppinRiyuKbn()) ? " " : dataArea.get(i)
                            .getKppinRiyuKbn();
            DataUtil.setValueByMethodName(s048frih, "setKppinRiyuKbn" + inx, value);

            Short messaiNo =
                    CCStringUtil.isEmpty(dataArea.get(i).getMesaiNo()) ? (short) 0 : Short.parseShort(dataArea
                            .get(i).getMesaiNo());
            DataUtil.setValueByMethodName(s048frih, "setMesaiNo" + inx, messaiNo);
        }

        String cmnDd = CCDateUtil.getSysDateFormat("yyyyMMdd");
        String cmnUpdtime = CCDateUtil.getSysDateFormat("HHmmss");
        if (isInsert) {
            s048frih.setCmnInsdd(cmnDd);
        }
        s048frih.setCmnUpddd(cmnDd);
        s048frih.setCmnUpdtime(cmnUpdtime);
        s048frih.setCmnTantoCd(this.context.getTantoCode());
        s048frih.setCmnTermId(this.context.getTermId());

        return s048frih;
    }

    /**
     * Delete data from database.
     * 
     * @param form 資料
     */
    @Transactional
    public void deleteData(Sijp0040CondForm form) {
        String dpyNo = form.getDpyNo();
        String dpyKbn = form.getDpyKbn();
        String inTenpoCd = form.getSubKaisyaCd() + form.getSubJigyobuCd() + form.getSubTenCd();
        String inBmnCd = form.getSubJigyobuCd() + form.getSubBmnCd();
        String outTenpoCd = form.getKaisyaCd() + form.getJigyobuCd() + form.getTenCd();
        String outBmnCd = form.getJigyobuCd() + form.getBmnCd();

        List<S003frid> s003frids = queryData(dpyNo, dpyKbn, inTenpoCd, inBmnCd, outTenpoCd, outBmnCd);
        S003frid frid = s003frids.get(0);

        Sijp0040ResultForm oldForm = getData(frid, 0);
        // Sijp0040MapperのselectS007frit01を使用
        // 1．SEQNO取得
        // SEQNO変数
        int seqno = -1;
        // 最大[ＳＥＱＮＯ]
        Integer maxSeqNo = getSeqNoData(dpyNo, dpyKbn, inTenpoCd, inBmnCd, outTenpoCd, outBmnCd);
        // 取得結果は[null]の場合
        // SEQNO変数に0をセット。
        if (maxSeqNo == null) {
            seqno = 0;
            // 処理仕様(その他関数)シートの処理⑥を参照。
            // cmJSICommon.CN_DELETE
            // cmJSICommon.CN_REC_ZEN
            S007frit s007Ins = insertS007fritFromS003frid(frid, CCSICommon.CN_DELETE, CCSICommon.CN_REC_ZEN, seqno);
            int result = s007fritMapper.insertSelective(s007Ins);
            if (result <= 0) {
                throw new ChaseException(Messages.get(CCMessageConst.MSG_KEY_EXCEPTION_INSERT_NO_RECORD));
            }

            S029frip s029Ins = new S029frip();
            BeanUtils.copyProperties(s007Ins, s029Ins);
            result = s029fripMapper.insertSelective(s029Ins);
            if (result <= 0) {
                throw new ChaseException(Messages.get(CCMessageConst.MSG_KEY_EXCEPTION_INSERT_NO_RECORD));
            }
        }

        // 2-2．移動伝票累積Ｆ（処理状態ﾌﾗｸﾞ更新）
        // 処理仕様(その他関数)シートの処理⑬を参照。
        S003frid s003Del = deleteS003frid();
        S003fridExample s003DelExample = new S003fridExample();
        S003fridExample.Criteria criteria = s003DelExample.createCriteria();

        criteria.andDpyNoEqualTo(dpyNo).andDenKbnEqualTo(dpyKbn).andInTenpoCdEqualTo(inTenpoCd)
                .andInBmnCdEqualTo(inBmnCd).andOutTenpoCdEqualTo(outTenpoCd).andOutBmnCdEqualTo(outBmnCd);
        int result = s003fridMapper.updateByExampleSelective(s003Del, s003DelExample);
        if (result <= 0) {
            throw new ChaseException(Messages.get(CCMessageConst.MSG_KEY_EXCEPTION_UPDATE_NO_RECORD));
        }

        // 2-3．移動伝票入力Ｔ
        // 処理仕様(その他関数)シートの処理⑪を参照。
        // cmJSICommon.CN_DELETE
        // cmJSICommon.CN_REC_KON
        // 最大[ＳＥＱＮＯ]
        maxSeqNo = getSeqNoData(dpyNo, dpyKbn, inTenpoCd, inBmnCd, outTenpoCd, outBmnCd);
        // 取得結果は[null]の場合
        // SEQNO変数に0をセット。
        if (maxSeqNo == null) {
            seqno = 0;
        } else {
            seqno = maxSeqNo;
        }
        S007frit s007InsNext = insertS007frit(oldForm, CCSICommon.CN_DELETE, CCSICommon.CN_REC_KON, seqno);
        result = s007fritMapper.insertSelective(s007InsNext);
        if (result <= 0) {
            throw new ChaseException(Messages.get(CCMessageConst.MSG_KEY_EXCEPTION_INSERT_NO_RECORD));
        }

        S029frip s029InsNext = new S029frip();
        BeanUtils.copyProperties(s007InsNext, s029InsNext);
        result = s029fripMapper.insertSelective(s029InsNext);
        if (result <= 0) {
            throw new ChaseException(Messages.get(CCMessageConst.MSG_KEY_EXCEPTION_INSERT_NO_RECORD));
        }
        // 2-4．移動明細履歴Ｆ
        // 処理仕様(その他関数)シートの処理⑧を参照。
        // cmJSICommon.CN_DELETE
        S016frmr s016Ins = insertS016frmr(oldForm, CCSICommon.CN_DELETE, inTenpoCd, inBmnCd, outTenpoCd, outBmnCd);
        result = s016frmrMapper.insertSelective(s016Ins);
        if (result <= 0) {
            throw new ChaseException(Messages.get(CCMessageConst.MSG_KEY_EXCEPTION_INSERT_NO_RECORD));
        }

        // 2-5．移動明細履歴Ｆ
        // 処理仕様(その他関数)シートの処理⑨を参照。
        // cmJSIConst.SYORISTS_KBN.KBN_DELETE
        S016frmr s016Upd = updateS016frmr(CCSIConst.SYORISTS_KBN.KBN_DELETE);
        S016frmrExample s016UpdExample = new S016frmrExample();
        s016UpdExample.createCriteria().andDpyNoEqualTo(dpyNo).andDenKbnEqualTo(dpyKbn)
                .andInTenpoCdEqualTo(inTenpoCd).andInBmnCdEqualTo(inBmnCd).andOutTenpoCdEqualTo(outTenpoCd)
                .andOutBmnCdEqualTo(outBmnCd);

        result = s016frmrMapper.updateByExampleSelective(s016Upd, s016UpdExample);
        if (result <= 0) {
            throw new ChaseException(Messages.get(CCMessageConst.MSG_KEY_EXCEPTION_INSERT_NO_RECORD));
        }

        S048frihExample s048frihExample = new S048frihExample();
        s048frihExample.createCriteria().andDpyNoEqualTo(dpyNo);

        List<S048frih> s048frihList = s048frihMapper.selectByExample(s048frihExample);

        if (!s048frihList.isEmpty()) {
            result = s048frihMapper.deleteByExample(s048frihExample);
            if (result <= 0) {
                throw new ChaseException(Messages.get(CCMessageConst.MSG_KEY_EXCEPTION_DELETE_NO_RECORD));
            }
        }
    }

    /**
     * 売価金額取得.
     * key: defaultBaiTan: 売単価<br>
     * key: defaultGenTan: 原単価<br>
     * <p>
     * 
     * @param kaisyaCd 会社コード
     * @param jigyobuCd 事業部
     * @param tenCd 店舗コード
     * @param keijoYmd 計上日
     * @param shnCd 商品コード
     * @return Map 売単価 and 原単価
     */
    public Result getTanka(String kaisyaCd, String jigyobuCd, String tenCd, String keijoYmd, String shnCd) {
        String baiTan = "";
        String genTan = "";
        // 店舗コード（７桁）
        String tenCd7 = kaisyaCd + jigyobuCd + tenCd;
        baiTan = cmJSICommon.getBaiTanka(shnCd, tenCd7, keijoYmd, false);
        genTan = cmJSICommon.getGenTanka(shnCd, tenCd7, keijoYmd, false);

        Map<String, String> data = new HashMap<String, String>();
        data.put("defaultBaiTan", baiTan);
        data.put("defaultGenTan", genTan);
        return ok(Json.toJson(data));
    }

    /**
     * 処理⑬：移動伝票累積Ｆ（処理状態ﾌﾗｸﾞ更新）<br>
     * 3．S003fridMapperのupdateByExampleSelectiveを使用する。
     * 
     * @return データベースレコード
     */
    private S003frid deleteS003frid() {
        // 編集仕様(項目セット仕様)
        S003frid frid = new S003frid();
        frid.setSyoriStsKbn(CCSIConst.SYORISTS_KBN.KBN_DELETE);
        frid.setCmnTantoCd(context.getTantoCode());
        frid.setCmnUpddd(CCDateUtil.getSysDateFormat(CCDateUtil.DATETIME_FORMAT_DATE_YYYYMMDD));
        frid.setCmnUpdtime(CCDateUtil.getSysDateFormat(CCDateUtil.DATETIME_FORMAT_TIME));

        return frid;
    }

    /**
     * Convert query data to view data.
     * 
     * @param frid data
     * @param searchFlg flag
     * @return Sijp0040ResultForm
     */
    private Sijp0040ResultForm getData(S003frid frid, int searchFlg) {
        //
        Sijp0040ResultForm result = new Sijp0040ResultForm();
        // ConditionFormClassにデータを設定する。
        result.setSyoriStsKbn(frid.getSyoriStsKbn());
        result.setSyoriStsKbnNm(getStatusNm(frid.getSyoriStsKbn()));
        result.setDpyKbn(frid.getDenKbn());
        result.setDpyNo(frid.getDpyNo());
        // SIBN0040: 388-392
        if (searchFlg == 0) {
            result.setSyukaYmd(frid.getSyukaYmd());
        }
        result.setKaisyaCd(frid.getOutTenpoCd().substring(0, 2));
        result.setJigyobuCd(frid.getOutTenpoCd().substring(2, 4));
        result.setTenCd(frid.getOutTenpoCd().substring(4, 7));
        result.setBmnCd(frid.getOutBmnCd().substring(2, 5));

        result.setSubKaisyaCd(frid.getInTenpoCd().substring(0, 2));
        result.setSubJigyobuCd(frid.getInTenpoCd().substring(2, 4));
        result.setSubTenCd(frid.getInTenpoCd().substring(4, 7));
        result.setSubBmnCd(frid.getInBmnCd().substring(2, 5));
        // 取得データがある場合、取得したデータを画面データ変数に格納して置く。

        List<Sijp0040Msai> dataArea = new ArrayList<Sijp0040Msai>();
        long sumKenGenkIn = 0;
        Long sumKenBaikKin = 0L;

        for (int i = 0; i < 10; i++) {
            Sijp0040Msai row = new Sijp0040Msai();
            row.setMesaiNo((i + 1) + EMPTY);

            String outShnCd = CCStringUtil.trimBoth(getShnCd(frid, i));
            String outJanCdDisp = plucnv.toDispCode(outShnCd, frid.getSyukaYmd());
            row.setOutShnCd(new String(outJanCdDisp).trim());
            String inShnCd = CCStringUtil.trimBoth(getInShnCd(frid, i));
            String inJanCdDisp = plucnv.toDispCode(inShnCd, frid.getSyukaYmd());
            row.setInShnCd(new String(inJanCdDisp).trim());
            if (!EMPTY.equals(outShnCd)) {
                row.setDisplayFlag(true);
                row.setOutShnNm(CCStringUtil.trimBoth(getShnNm(outJanCdDisp, frid.getSyukaYmd())));
                row.setInShnNm(CCStringUtil.trimBoth(getShnNm(inJanCdDisp, frid.getSyukaYmd())));

                sumKenGenkIn = sumKenGenkIn + getKenGenkKin(frid, i);
                sumKenBaikKin = sumKenBaikKin + getKenBaikKin(frid, i);
            } else {
                row.setDisplayFlag(false);
                row.setOutShnNm(EMPTY);
                row.setInShnNm(EMPTY);
            }

            row.setKenBarasu(getKenbarasu(frid, i));
            row.setKenGenk(getKenGenk(frid, i));
            row.setKenGenkKin(getKenGenkKin(frid, i));
            row.setKenBaik(getKenBaik(frid, i));
            row.setKenBaikKin(getKenBaikKin(frid, i));

            dataArea.add(row);
        }
        result.setDataArea(dataArea);
        // For footer item
        result.setSumKenGenkIn(sumKenGenkIn);
        result.setSumKenBaikKin(sumKenBaikKin);

        result.setTekiyo(CCStringUtil.trimBoth(frid.getTekiyo()));
        return result;
    }

    /**
     * Update table S003frid.
     * 
     * @param form 資料
     * @return データベースレコード
     */
    private S003frid updateS003frid(Sijp0040ResultForm form) {
        String syukaYmd = form.getSyukaYmd();
        String inTenpoCd = form.getSubKaisyaCd() + form.getSubJigyobuCd() + form.getSubTenCd();
        String outTenpoCd = form.getKaisyaCd() + form.getJigyobuCd() + form.getTenCd();

        S003frid frid = new S003frid();

        // 編集仕様(項目セット仕様)
        frid.setSyukaYmd(syukaYmd);
        frid.setHatYmd(syukaYmd);
        frid.setNhnYoteiYmd(syukaYmd);
        String keijoDate = cmJSICommon.getKeijoDate(syukaYmd);
        frid.setRuiKeijoYmd(isEmpty(keijoDate) ? EMPTY : keijoDate);

        String termName = context.getTermName();
        if (termName.getBytes().length > 8) {
            termName = new String(Arrays.copyOf(termName.getBytes(), 8));
        }

        frid.setLastKakuteiYmd(CCDateUtil.getSysDateFormat(CCDateUtil.DATETIME_FORMAT_DATE_YYYYMMDD));
        frid.setLastTantoCd(context.getTantoCode());
        frid.setLastTermId(termName);

        // 確定場所 = cmJSICommon.getDefaultTenpo
        // 引数: Requestの担当者コードをセット
        String defaultTenpo = cmJSICommon.getDefaultTenpo(context.getTantoCode());
        // 確定区分 = cmJSICommon.getKakuteiKbn
        String kakuteiKbn = cmJSICommon.getKakuteiKbn(defaultTenpo, syukaYmd);

        // ※1に確定区分をセット ----- ※右スペースパディング（2桁数）
        frid.setEntryKbn(isEmpty(kakuteiKbn) ? EMPTY : kakuteiKbn);
        // ※2に確定場所をセット------ ※右スペースパディング（7桁数）
        frid.setEntryPlace(isEmpty(defaultTenpo) ? EMPTY : defaultTenpo);

        // cmJSICommon.getKeijoDate(画面項目[出荷日]) - ※substring(0, 6)
        frid.setSirGetujiYm(keijoDate.substring(0, 6));
        // cmJSIConst.SYORISTS_KBN.KBN_KAKTEI
        frid.setSyoriStsKbn(CCSIConst.SYORISTS_KBN.KBN_KAKTEI);
        frid.setTekiyo(isEmpty(form.getTekiyo()) ? EMPTY : form.getTekiyo());

        // 画面項目[原価金額合計]をセット
        frid.setsKenGenkKin(form.getSumKenGenkIn());
        // 画面項目[売価金額合計]をセット
        frid.setsKenBaikKin(form.getSumKenBaikKin());
        // 画面項目[原価金額合計]をセット
        frid.setsHatGenkKin(form.getSumKenGenkIn());
        // 画面項目[売価金額合計]をセット
        frid.setsHatBaikKin(form.getSumKenBaikKin());

        // 明細数取得
        int msaiSu = 0;

        for (int index = 0; index < form.getDataArea().size(); index++) {
            Sijp0040Msai msai = form.getDataArea().get(index);

            String inx = ((index < 9) ? "0" : "") + (index + 1);

            // "" --- ※右スペースパディング（5桁数）
            DataUtil.setValueByMethodName(frid, "setKikakuSu" + inx, EMPTY);
            // "" --- ※右スペースパディング（5桁数）
            DataUtil.setValueByMethodName(frid, "setKikakuTani" + inx, EMPTY);
            // "0"
            DataUtil.setValueByMethodName(frid, "setHatCaseSu" + inx, 0);

            if (msai.getDisplayFlag()) {
                setInfoS003fridByOutShrCd(frid, index, 2, msai.getOutShnCd(), syukaYmd, outTenpoCd);

                setInfoS003fridByInShrCd(frid, index, 2, msai.getInShnCd(), syukaYmd, inTenpoCd);

                DataUtil.setValueByMethodName(frid, "setHatBaraSu" + inx, msai.getKenBarasu());
                DataUtil.setValueByMethodName(frid, "setKenBaraSu" + inx, msai.getKenBarasu());
                DataUtil.setValueByMethodName(frid, "setKenGenk" + inx, msai.getKenGenk());
                DataUtil.setValueByMethodName(frid, "setKenBaik" + inx, msai.getKenBaik());
                DataUtil.setValueByMethodName(frid, "setKenGenkKin" + inx, msai.getKenGenkKin());
                DataUtil.setValueByMethodName(frid, "setKenBaikKin" + inx, msai.getKenBaikKin().longValue());

                // 明細数ｶｳﾝﾄｱｯﾌﾟ
                msaiSu++;
            } else {
                DataUtil.setValueByMethodName(frid, "setMkrCd" + inx, EMPTY);
                // cmJSICommon.cnvJanToJan13(グリッド[1..10][出荷店商品ｺｰﾄﾞ])
                DataUtil.setValueByMethodName(frid, "setShnCd" + inx, EMPTY);
                DataUtil.setValueByMethodName(frid, "setPluKbn" + inx, EMPTY);
                DataUtil.setValueByMethodName(frid, "setCbnrCd" + inx, EMPTY);
                DataUtil.setValueByMethodName(frid, "setSbnrCd" + inx, EMPTY);
                DataUtil.setValueByMethodName(frid, "setIriSu" + inx, new Integer(0));
                DataUtil.setValueByMethodName(frid, "setHattyuIrisu" + inx, new BigDecimal(0));
                DataUtil.setValueByMethodName(frid, "setTani" + inx, EMPTY);

                DataUtil.setValueByMethodName(frid, "setInShnCd" + inx, EMPTY);
                DataUtil.setValueByMethodName(frid, "setInPluKbn" + inx, EMPTY);
                DataUtil.setValueByMethodName(frid, "setInCbnrCd" + inx, EMPTY);
                DataUtil.setValueByMethodName(frid, "setInSbnrCd" + inx, EMPTY);

                DataUtil.setValueByMethodName(frid, "setHatBaraSu" + inx, new BigDecimal(0));
                DataUtil.setValueByMethodName(frid, "setKenBaraSu" + inx, new BigDecimal(0));
                DataUtil.setValueByMethodName(frid, "setKenGenk" + inx, new BigDecimal(0));
                DataUtil.setValueByMethodName(frid, "setKenBaik" + inx, new Integer(0));
                DataUtil.setValueByMethodName(frid, "setKenGenkKin" + inx, new Long(0));
                DataUtil.setValueByMethodName(frid, "setKenBaikKin" + inx, new Long(0));
            }
        }

        // 明細数
        frid.setMsaiSu((short) msaiSu);

        frid.setCmnTantoCd(context.getTantoCode());
        frid.setCmnTermId(termName);
        frid.setCmnUpddd(CCDateUtil.getSysDateFormat(CCDateUtil.DATETIME_FORMAT_DATE_YYYYMMDD));
        frid.setCmnUpdtime(CCDateUtil.getSysDateFormat(CCDateUtil.DATETIME_FORMAT_TIME));

        return frid;
    }

    /**
     * 処理⑪：移動伝票累積Ｆ<br>
     * 3．S007fritMapperのinsertSelectiveを使用する。<br>
     * 
     * @param form 資料
     * @param shoriTaipu 処理タイプ
     * @param koshinKbn 更新区分
     * @param seqno SEQNO変数
     * @return データベースレコード
     */
    private S007frit insertS007frit(Sijp0040ResultForm form, int shoriTaipu, String koshinKbn, int seqno) {
        String dpyNo = form.getDpyNo();
        String dpyKbn = form.getDpyKbn();
        String syukaYmd = form.getSyukaYmd();
        String inTenpoCd = form.getSubKaisyaCd() + form.getSubJigyobuCd() + form.getSubTenCd();
        String inBmnCd = form.getSubJigyobuCd() + form.getSubBmnCd();
        String outTenpoCd = form.getKaisyaCd() + form.getJigyobuCd() + form.getTenCd();
        String outBmnCd = form.getJigyobuCd() + form.getBmnCd();
        // 3．S007fritMapperのinsertSelectiveを使用する。
        S007frit frit = new S007frit();

        // ※1..※2
        setInfoS007frit(frit, shoriTaipu, koshinKbn, seqno);

        // ※3..※9
        setHeadS007frit(dpyNo, dpyKbn, inTenpoCd, inBmnCd, outTenpoCd, outBmnCd, syukaYmd, frit);

        // update data from form
        setDataS007frit(form, frit, shoriTaipu);

        return frit;
    }

    /**
     * 処理⑪：移動伝票累積Ｆ<br>
     * ※3..※9
     * 
     * @param dpyKbn 伝区
     * @param dpyNo 伝票NO
     * @param inTenpoCd 入荷店店舗コード
     * @param inBmnCd 入荷店部門コード
     * @param outTenpoCd 出荷店店舗コード
     * @param outBmnCd 出荷店部門コード
     * @param syukaYmdIn 出荷日
     * @param frit データベースレコード
     */
    private void setHeadS007frit(String dpyNo, String dpyKbn, String inTenpoCd, String inBmnCd, String outTenpoCd,
            String outBmnCd, String syukaYmdIn, S007frit frit) {
        if (frit == null) {
            return;
        }

        // 確定場所 = cmJSICommon.getDefaultTenpo
        // 引数: Requestの担当者コードをセット
        String defaultTenpo = cmJSICommon.getDefaultTenpo(context.getTantoCode());
        // 確定区分 = cmJSICommon.getKakuteiKbn
        String kakuteiKbn = cmJSICommon.getKakuteiKbn(defaultTenpo, syukaYmdIn);

        // ※3
        frit.setEntryKbn(kakuteiKbn);
        // ※4
        frit.setEntryPlace(defaultTenpo);

        // 累計計上日 = cmJSICommon.getKeijoDate
        String keijoDate = cmJSICommon.getKeijoDate(syukaYmdIn);
        // ※5に累計計上日をセット - ※substring(0, 6)
        if (!CCStringUtil.isEmpty(keijoDate) && keijoDate.length() >= 6) {
            frit.setSirGetujiYm(keijoDate.substring(0, 6));
        }

        // ※6に取得したレコード[FY_KBN]をセット
        String faKubun = cmJSICommon.getFAKubun(outTenpoCd, outBmnCd, syukaYmdIn);
        frit.setAtukaiKbn(faKubun);

        // cmJSICommon.getComTenpoNameAを使用
        String syukaYmd = syukaYmdIn;

        if (CCStringUtil.isEmpty(syukaYmd)) {
            // ※""の場合は現在日付"yyyyMMdd"をセット
            syukaYmd = new DateTime().toString(CCDateUtil.DATETIME_FORMAT_DATE_YYYYMMDD);
        }

        String tenpoNameAIn = cmJSICommon.getComTenpoNameA(inTenpoCd, syukaYmd);
        // ※右スペースパディング（20桁数）
        tenpoNameAIn = isEmpty(tenpoNameAIn) ? EMPTY : tenpoNameAIn;
        // トリムした入荷店舗名のサイズ > 20の場合
        // 入荷店舗名 = 入荷店舗名.substring(0, 20)
        if (tenpoNameAIn.length() > 20) {
            tenpoNameAIn = tenpoNameAIn.substring(0, 20);
        }

        // ※8に入荷店舗名をセット
        frit.setInTenNmA(tenpoNameAIn);

        // cmJSICommon.getComTenpoNameAを使用
        String tenpoNameAOut = cmJSICommon.getComTenpoNameA(outTenpoCd, syukaYmd);
        // ※右スペースパディング（20桁数）
        tenpoNameAOut = isEmpty(tenpoNameAIn) ? EMPTY : tenpoNameAIn;
        // トリムした入荷店舗名のサイズ > 20の場合
        // 入荷店舗名 = 入荷店舗名.substring(0, 20)
        if (tenpoNameAOut.length() > 20) {
            tenpoNameAOut = tenpoNameAOut.substring(0, 20);
        }

        // ※9に出荷店舗名をセット
        frit.setOutTenNmA(tenpoNameAOut);
    }

    /**
     * 処理⑪：移動伝票累積Ｆ
     * 
     * @param form 資料
     * @param frit データベースレコード
     * @param shoriKbn 処理区分
     */
    private void setDataS007frit(Sijp0040ResultForm form, S007frit frit, int shoriKbn) {
        String dpyNo = form.getDpyNo();
        String dpyKbn = form.getDpyKbn();
        String syukaYmd = form.getSyukaYmd();
        String inTenpoCd = form.getSubKaisyaCd() + form.getSubJigyobuCd() + form.getSubTenCd();
        String inBmnCd = form.getSubJigyobuCd() + form.getSubBmnCd();
        String outTenpoCd = form.getKaisyaCd() + form.getJigyobuCd() + form.getTenCd();
        String outBmnCd = form.getJigyobuCd() + form.getBmnCd();

        String insdd = CCDateUtil.getSysDateFormat(CCDateUtil.DATETIME_FORMAT_DATE_YYYYMMDD);
        String insTime = CCDateUtil.getSysDateFormat(CCDateUtil.DATETIME_FORMAT_TIME);

        frit.setInsdd(insdd);
        frit.setInstime(insTime);

        frit.setDpyNo(dpyNo);
        frit.setDenKbn(dpyKbn);
        frit.setInTenpoCd(inTenpoCd);
        frit.setInBmnCd(inBmnCd);
        frit.setOutTenpoCd(outTenpoCd);
        frit.setOutBmnCd(outBmnCd);

        frit.setSyukaYmd(syukaYmd);
        frit.setRuiFlg("1"); // 固定値['1']をセット
        frit.setHatYmd(syukaYmd);
        frit.setNhnYoteiYmd(syukaYmd);
        String keijoDate = cmJSICommon.getKeijoDate(syukaYmd);
        frit.setRuiKeijoYmd(keijoDate);

        frit.setFirstKakuteiYmd(insdd);
        frit.setFirstTantoCd(context.getTantoCode());
        String termName = context.getTermName();
        if (termName.getBytes().length > 8) {
            termName = new String(Arrays.copyOf(termName.getBytes(), 8));
        }
        frit.setFirstTermId(termName);
        frit.setLastKakuteiYmd(insdd);
        frit.setLastTantoCd(context.getTantoCode());
        frit.setLastTermId(termName);

        frit.setKasanKbn(CCSIConst.KASAN_KBN.KBN_SUKIN_A);
        frit.setSyoriStsKbn(CCSIConst.SYORISTS_KBN.KBN_KAKTEI);
        frit.setTekiyo(form.getTekiyo());

        // frit.setAtukaiKbn(atukaiKbn); // ※4
        // 画面項目[原価金額合計]をセット
        frit.setsHatGenkKin(form.getSumKenGenkIn());
        // 画面項目[売価金額合計]をセット
        frit.setsHatBaikKin(form.getSumKenBaikKin());
        // 画面項目[原価金額合計]をセット
        frit.setsKenGenkKin(form.getSumKenGenkIn());
        // 画面項目[売価金額合計]をセット
        frit.setsKenBaikKin(form.getSumKenBaikKin());
        // frit.setMsaiSu(msaiSu); // ※5
        // 明細数 = 0
        short mesaiSu = 0;

        for (int index = 0; index < form.getDataArea().size(); index++) {
            String inx = ((index < 9) ? "0" : "") + (index + 1);

            // 順番で[1..10]をセット
            DataUtil.setValueByMethodName(frit, "setMesaiNo" + inx, new Short((short) (index + 1)));

            Sijp0040Msai msai = form.getDataArea().get(index);

            if (msai.getDisplayFlag()) {
                // グリッド(index).出荷店商品ｺｰﾄﾞ <> ""場合
                // 明細数 = 明細数 + 1
                mesaiSu = (short) (mesaiSu + 1);

                setInfoS007fritByOutShrCd(frit, index, shoriKbn, msai.getOutShnCd(), syukaYmd, outTenpoCd);

                setInfoS007fritByInShrCd(frit, index, shoriKbn, msai.getInShnCd(), syukaYmd, inTenpoCd);

                DataUtil.setValueByMethodName(frit, "setHatBaraSu" + inx, msai.getKenBarasu());
                DataUtil.setValueByMethodName(frit, "setKenBaraSu" + inx, msai.getKenBarasu());
                DataUtil.setValueByMethodName(frit, "setKenGenk" + inx, msai.getKenGenk());
                DataUtil.setValueByMethodName(frit, "setKenBaik" + inx, msai.getKenBaik());
                DataUtil.setValueByMethodName(frit, "setKenGenkKin" + inx, msai.getKenGenkKin());
                DataUtil.setValueByMethodName(frit, "setKenBaikKin" + inx, msai.getKenBaikKin());
            }
        }
        // ※7に明細数をセット
        frit.setMsaiSu(new Short(mesaiSu));

        // frit.setInTenNmA(inTenNmA) // ※6
        // frit.setOutTenNmA(outTenNmA) // ※7

        frit.setCmnTantoCd(context.getTantoCode());
        frit.setCmnTermId(termName);
        frit.setCmnInsdd(insdd);
        frit.setCmnUpddd(insdd);
        frit.setCmnUpdtime(insTime);
    }

    /**
     * Sub process of 処理⑪：移動伝票累積Ｆ<br>
     * 
     * @param frit データベースレコード
     * @param index index
     * @param shoriKbn 処理区分
     * @param outShnCd 出荷店商品ｺｰﾄﾞ
     * @param syukaYmd 出荷日
     * @param outTenpoCd 出荷店店舗コード
     */
    private void setInfoS007fritByOutShrCd(S007frit frit, int index, int shoriKbn, String outShnCd, String syukaYmd,
            String outTenpoCd) {
        // 1．商品情報取得
        // 商品13 = clSICom.cnvJanToJan13
        String jan13 = cmJSICommon.cnvJanToJan13(outShnCd);
        String[] shohin = new String[10];
        for (int i = 0; i < shohin.length; i++) {
            shohin[i] = EMPTY;
        }
        BigDecimal caseSu = new BigDecimal(0);
        BigDecimal hattyuIrisu = new BigDecimal(0);

        // M007kijmMapperのSelectByExampleを使用
        M007kijmExample m007jijmExample = new M007kijmExample();
        m007jijmExample.createCriteria().andJanCdEqualTo(jan13).andHakkoDayToGreaterThanOrEqualTo(syukaYmd);
        m007jijmExample.setSearchDate(syukaYmd);
        // 直近のレコード（1レコード）

        List<M007kijm> m007kijms = m007kijmMapper.selectByExample(m007jijmExample);

        if (m007kijms.size() > 0) {
            M007kijm m007kijm = m007kijms.get(0);
            shohin[0] = CCStringUtil.trimBoth(m007kijm.getShnCdPluKbn());
            shohin[1] = CCStringUtil.trimBoth(m007kijm.getBinKbn());
            // / 商品情報[2] = 取得したレコード[CASE_SU]
            caseSu = m007kijm.getCaseSu();
            shohin[3] = CCStringUtil.trimBoth(m007kijm.getTaxKbn());
            shohin[9] = CCStringUtil.trimBoth(m007kijm.getMkrCd());
        }

        // M009msymMapperのSelectByExampleを使用
        M009msymExample m009msymExample = new M009msymExample();
        m009msymExample.createCriteria().andJanCdEqualTo(jan13).andTenCdEqualTo(outTenpoCd)
                .andHakkoDayToGreaterThanOrEqualTo(syukaYmd);
        m009msymExample.setSearchDate(syukaYmd);
        // 直近のレコード（1レコード）

        List<M009msym> m009msyms = m009msymMapper.selectByExample(m009msymExample);

        if (m009msyms.size() > 0) {
            M009msym m009msym = m009msyms.get(0);
            shohin[4] = CCStringUtil.trimBoth(m009msym.getChuBnrCd());
            shohin[5] = CCStringUtil.trimBoth(m009msym.getShoBnrCd());
            shohin[6] = CCStringUtil.trimBoth(m009msym.getCenterCd());
            hattyuIrisu = m009msym.getHattyuIrisu();
            shohin[8] = CCStringUtil.trimBoth(m009msym.getTani());
        }

        int fixLen = (shoriKbn == CCSICommon.CN_UPDATE ? 14 : 13);
        String inx = ((index < 9) ? "0" : "") + (index + 1);
        DataUtil.setValueByMethodName(frit, "setMkrCd" + inx, shohin[9]);
        // cmJSICommon.cnvJanToJan13(グリッド[1..10][出荷店商品ｺｰﾄﾞ])
        DataUtil.setValueByMethodName(frit, "setShnCd" + inx, CCStringUtil.fixString(jan13, fixLen));
        DataUtil.setValueByMethodName(frit, "setPluKbn" + inx, shohin[0]);
        DataUtil.setValueByMethodName(frit, "setCbnrCd" + inx, shohin[4]);
        DataUtil.setValueByMethodName(frit, "setSbnrCd" + inx, shohin[5]);

        DataUtil.setValueByMethodName(frit, "setIriSu" + inx, caseSu.intValue());
        DataUtil.setValueByMethodName(frit, "setHattyuIrisu" + inx, hattyuIrisu);
        DataUtil.setValueByMethodName(frit, "setTani" + inx, shohin[8]);
    }

    /**
     * Sub process of 処理⑪：移動伝票累積Ｆ<br>
     * 
     * @param frit データベースレコード
     * @param index index
     * @param shoriKbn 処理区分
     * @param inShnCd 入荷店商品コード
     * @param syukaYmd 出荷日
     * @param inTenpoCd 入荷店店舗コード
     */
    private void setInfoS007fritByInShrCd(S007frit frit, int index, int shoriKbn, String inShnCd, String syukaYmd,
            String inTenpoCd) {
        // 1．商品情報取得
        // 商品13 = clSICom.cnvJanToJan13
        String jan13 = cmJSICommon.cnvJanToJan13(inShnCd);
        String[] shohin = new String[10];
        for (int i = 0; i < shohin.length; i++) {
            shohin[i] = EMPTY;
        }

        // M007kijmMapperのSelectByExampleを使用
        M007kijmExample m007jijmExample = new M007kijmExample();
        m007jijmExample.createCriteria().andJanCdEqualTo(jan13).andHakkoDayToGreaterThanOrEqualTo(syukaYmd);
        m007jijmExample.setSearchDate(syukaYmd);
        // 直近のレコード（1レコード）

        List<M007kijm> m007kijms = m007kijmMapper.selectByExample(m007jijmExample);
        if (m007kijms.size() > 0) {
            M007kijm m007kijm = m007kijms.get(0);
            shohin[0] = CCStringUtil.trimBoth(m007kijm.getShnCdPluKbn());
            shohin[1] = CCStringUtil.trimBoth(m007kijm.getBinKbn());
            // / 商品情報[2] = 取得したレコード[CASE_SU]
            // caseSu = m007kijm.getCaseSu();
            shohin[3] = CCStringUtil.trimBoth(m007kijm.getTaxKbn());
            shohin[9] = CCStringUtil.trimBoth(m007kijm.getMkrCd());
        }

        // M009msymMapperのSelectByExampleを使用
        M009msymExample m009msymExample = new M009msymExample();
        m009msymExample.createCriteria().andJanCdEqualTo(jan13).andTenCdEqualTo(inTenpoCd)
                .andHakkoDayToGreaterThanOrEqualTo(syukaYmd);
        m009msymExample.setSearchDate(syukaYmd);
        // 直近のレコード（1レコード）

        List<M009msym> m009msyms = m009msymMapper.selectByExample(m009msymExample);
        if (m009msyms.size() > 0) {
            M009msym m009msym = m009msyms.get(0);
            shohin[4] = CCStringUtil.trimBoth(m009msym.getChuBnrCd());
            shohin[5] = CCStringUtil.trimBoth(m009msym.getShoBnrCd());
            shohin[6] = CCStringUtil.trimBoth(m009msym.getCenterCd());
            // hattyuIrisu = m009msym.getHattyuIrisu();
            shohin[8] = CCStringUtil.trimBoth(m009msym.getTani());
        }

        int fixLen = (shoriKbn == CCSICommon.CN_UPDATE ? 14 : 13);
        String inx = ((index < 9) ? "0" : "") + (index + 1);
        DataUtil.setValueByMethodName(frit, "setInShnCd" + inx, CCStringUtil.fixString(jan13, fixLen));
        DataUtil.setValueByMethodName(frit, "setInPluKbn" + inx, shohin[0]);
        DataUtil.setValueByMethodName(frit, "setInCbnrCd" + inx, shohin[4]);
        DataUtil.setValueByMethodName(frit, "setInSbnrCd" + inx, shohin[5]);
    }

    /**
     * 処理⑩：移動伝票累積Ｆ
     * 8．S003fridMapperのinsertSelectiveを使用する。
     * 
     * @param form 資料
     * @return データベースレコード
     */
    private S003frid insertS003frid(Sijp0040ResultForm form) {

        String inTenpoCd = form.getSubKaisyaCd() + form.getSubJigyobuCd() + form.getSubTenCd();
        String inBmnCd = form.getSubJigyobuCd() + form.getSubBmnCd();
        String outTenpoCd = form.getKaisyaCd() + form.getJigyobuCd() + form.getTenCd();
        String outBmnCd = form.getJigyobuCd() + form.getBmnCd();

        String insddd = CCDateUtil.getSysDateFormat(CCDateUtil.DATETIME_FORMAT_DATE_YYYYMMDD);
        String insTime = CCDateUtil.getSysDateFormat(CCDateUtil.DATETIME_FORMAT_TIME);

        // 8．S003fridMapperのinsertSelectiveを使用する。
        S003frid frid = new S003frid();
        // ※1..※7
        setInforS003frid(form, frid);

        frid.setDpyNo(form.getDpyNo());
        frid.setDenKbn(form.getDpyKbn());
        frid.setInTenpoCd(inTenpoCd);
        frid.setInBmnCd(inBmnCd);
        frid.setOutTenpoCd(outTenpoCd);
        frid.setOutBmnCd(outBmnCd);

        frid.setSyukaYmd(form.getSyukaYmd());
        frid.setHatYmd(form.getSyukaYmd());
        frid.setNhnYoteiYmd(form.getSyukaYmd());
        String keijoDate = cmJSICommon.getKeijoDate(form.getSyukaYmd());
        frid.setRuiKeijoYmd(keijoDate);

        frid.setFirstKakuteiYmd(insddd);
        frid.setFirstTantoCd(context.getTantoCode());
        String termName = context.getTermName();
        if (termName.getBytes().length > 8) {
            termName = new String(Arrays.copyOf(termName.getBytes(), 8));
        }
        frid.setFirstTermId(termName);
        frid.setLastKakuteiYmd(insddd);
        frid.setLastTantoCd(context.getTantoCode());
        frid.setLastTermId(termName);

        frid.setKasanKbn(CCSIConst.KASAN_KBN.KBN_SUKIN_A);
        frid.setSyoriStsKbn(CCSIConst.SYORISTS_KBN.KBN_KAKTEI);
        frid.setTekiyo(form.getTekiyo());

        // 画面項目[原価金額合計]をセット
        frid.setsHatGenkKin(form.getSumKenGenkIn());
        // 画面項目[売価金額合計]をセット
        frid.setsHatBaikKin(form.getSumKenBaikKin());
        // 画面項目[原価金額合計]をセット
        frid.setsKenGenkKin(form.getSumKenGenkIn());
        // 画面項目[売価金額合計]をセット
        frid.setsKenBaikKin(form.getSumKenBaikKin());
        for (int index = 0; index < form.getDataArea().size(); index++) {
            String inx = ((index < 9) ? "0" : "") + (index + 1);

            // 順番で[1..10]をセット
            DataUtil.setValueByMethodName(frid, "setMesaiNo" + inx, new Short((short) (index + 1)));
            Sijp0040Msai msai = form.getDataArea().get(index);
            if (msai.getDisplayFlag()) {
                setInfoS003fridByOutShrCd(frid, index, 1, msai.getOutShnCd(), form.getSyukaYmd(), outTenpoCd);

                setInfoS003fridByInShrCd(frid, index, 2, msai.getInShnCd(), form.getSyukaYmd(), inTenpoCd);

                DataUtil.setValueByMethodName(frid, "setHatBaraSu" + inx, msai.getKenBarasu());
                DataUtil.setValueByMethodName(frid, "setKenBaraSu" + inx, msai.getKenBarasu());
                DataUtil.setValueByMethodName(frid, "setKenGenk" + inx, msai.getKenGenk());
                DataUtil.setValueByMethodName(frid, "setKenBaik" + inx, msai.getKenBaik());
                DataUtil.setValueByMethodName(frid, "setKenGenkKin" + inx, msai.getKenGenkKin());
                DataUtil.setValueByMethodName(frid, "setKenBaikKin" + inx, msai.getKenBaikKin());
            }
        }

        frid.setCmnTantoCd(context.getTantoCode());
        frid.setCmnTermId(termName);
        frid.setCmnInsdd(insddd);
        frid.setCmnUpddd(insddd);
        frid.setCmnUpdtime(insTime);

        // 8．S003fridMapperのinsertSelectiveを使用する。--- End
        return frid;
    }

    /**
     * Sub process of 処理⑩：移動伝票累積Ｆ<br>
     * 
     * @param frid データベースレコード
     * @param index index
     * @param shoriKbn 処理区分
     * @param inShnCd 入荷店商品コード
     * @param syukaYmd 出荷日
     * @param inTenpoCd 入荷店店舗コード
     */
    private void setInfoS003fridByInShrCd(S003frid frid, int index, int shoriKbn, String inShnCd, String syukaYmd,
            String inTenpoCd) {
        // 1．商品情報取得
        // 商品13 = clSICom.cnvJanToJan13
        String jan13 = cmJSICommon.cnvJanToJan13(inShnCd);
        String[] shohin = new String[10];
        for (int i = 0; i < shohin.length; i++) {
            shohin[i] = EMPTY;
        }

        // M007kijmMapperのSelectByExampleを使用
        M007kijmExample m007jijmExample = new M007kijmExample();
        m007jijmExample.createCriteria().andJanCdEqualTo(jan13).andHakkoDayToGreaterThanOrEqualTo(syukaYmd);
        m007jijmExample.setSearchDate(syukaYmd);
        // 直近のレコード（1レコード）

        List<M007kijm> m007kijms = m007kijmMapper.selectByExample(m007jijmExample);

        if (m007kijms.size() > 0) {
            M007kijm m007kijm = m007kijms.get(0);
            shohin[0] = CCStringUtil.trimBoth(m007kijm.getShnCdPluKbn());
            shohin[1] = CCStringUtil.trimBoth(m007kijm.getBinKbn());
            // / 商品情報[2] = 取得したレコード[CASE_SU]
            shohin[3] = CCStringUtil.trimBoth(m007kijm.getTaxKbn());
            shohin[9] = CCStringUtil.trimBoth(m007kijm.getMkrCd());
        }
        // M009msymMapperのSelectByExampleを使用
        M009msymExample m009msymExample = new M009msymExample();
        m009msymExample.createCriteria().andJanCdEqualTo(jan13).andTenCdEqualTo(inTenpoCd)
                .andHakkoDayToGreaterThanOrEqualTo(syukaYmd);
        m009msymExample.setSearchDate(syukaYmd);
        // 直近のレコード（1レコード）

        List<M009msym> m009msyms = m009msymMapper.selectByExample(m009msymExample);

        if (m009msyms.size() > 0) {
            M009msym m009msym = m009msyms.get(0);
            shohin[4] = CCStringUtil.trimBoth(m009msym.getChuBnrCd());
            shohin[5] = CCStringUtil.trimBoth(m009msym.getShoBnrCd());
            shohin[6] = CCStringUtil.trimBoth(m009msym.getCenterCd());
            shohin[8] = CCStringUtil.trimBoth(m009msym.getTani());
        }

        // Insert: 13/ Other: 14
        int fixLen = (shoriKbn == CCSICommon.CN_UPDATE ? 14 : 13);
        String inx = ((index < 9) ? "0" : "") + (index + 1);
        DataUtil.setValueByMethodName(frid, "setInShnCd" + inx, CCStringUtil.fixString(jan13, fixLen));
        DataUtil.setValueByMethodName(frid, "setInPluKbn" + inx, shohin[0]);
        DataUtil.setValueByMethodName(frid, "setInCbnrCd" + inx, shohin[4]);
        DataUtil.setValueByMethodName(frid, "setInSbnrCd" + inx, shohin[5]);
    }

    /**
     * Sub process of 処理⑩：移動伝票累積Ｆ<br>
     * 
     * @param frid データベースレコード
     * @param index index
     * @param shoriKbn mode: insert or update
     * @param outShnCd 出荷店商品ｺｰﾄﾞ
     * @param syukaYmd 出荷日
     * @param outTenpoCd 出荷店店舗コード
     */
    private void setInfoS003fridByOutShrCd(S003frid frid, int index, int shoriKbn, String outShnCd, String syukaYmd,
            String outTenpoCd) {
        // 1．商品情報取得
        // 商品13 = clSICom.cnvJanToJan13
        String jan13 = cmJSICommon.cnvJanToJan13(outShnCd);
        String[] shohin = new String[10];
        for (int i = 0; i < shohin.length; i++) {
            shohin[i] = EMPTY;
        }
        BigDecimal caseSu = new BigDecimal(0);
        BigDecimal hattyuIrisu = new BigDecimal(0);

        // M007kijmMapperのSelectByExampleを使用
        M007kijmExample m007kijmExample = new M007kijmExample();
        // bug FD
        m007kijmExample.createCriteria().andJanCdEqualTo(jan13).andHakkoDayToGreaterThanOrEqualTo(syukaYmd);
        m007kijmExample.setSearchDate(syukaYmd);
        // 直近のレコード（1レコード）

        List<M007kijm> m007kijms = m007kijmMapper.selectByExample(m007kijmExample);

        if (m007kijms.size() > 0) {
            M007kijm m007kijm = m007kijms.get(0);
            shohin[0] = CCStringUtil.trimBoth(m007kijm.getShnCdPluKbn());
            shohin[1] = CCStringUtil.trimBoth(m007kijm.getBinKbn());
            // / 商品情報[2] = 取得したレコード[CASE_SU]
            caseSu = m007kijm.getCaseSu();
            shohin[3] = CCStringUtil.trimBoth(m007kijm.getTaxKbn());
            shohin[9] = CCStringUtil.trimBoth(m007kijm.getMkrCd());
        }

        // M009msymMapperのSelectByExampleを使用
        M009msymExample m009msymExample = new M009msymExample();
        m009msymExample.createCriteria().andJanCdEqualTo(jan13).andTenCdEqualTo(outTenpoCd)
                .andHakkoDayToGreaterThanOrEqualTo(syukaYmd);
        m009msymExample.setSearchDate(syukaYmd);
        // 直近のレコード（1レコード）

        List<M009msym> m009msyms = m009msymMapper.selectByExample(m009msymExample);

        if (m009msyms.size() > 0) {
            M009msym m009msym = m009msyms.get(0);
            shohin[4] = CCStringUtil.trimBoth(m009msym.getChuBnrCd());
            shohin[5] = CCStringUtil.trimBoth(m009msym.getShoBnrCd());
            shohin[6] = CCStringUtil.trimBoth(m009msym.getCenterCd());
            hattyuIrisu = m009msym.getHattyuIrisu();
            shohin[8] = CCStringUtil.trimBoth(m009msym.getTani());
        }

        // Insert: 13/ Update: 14
        int fixLen = (shoriKbn == CCSICommon.CN_UPDATE ? 14 : 13);

        String inx = ((index < 9) ? "0" : "") + (index + 1);
        DataUtil.setValueByMethodName(frid, "setMkrCd" + inx, shohin[9]);
        // cmJSICommon.cnvJanToJan13(グリッド[1..10][出荷店商品ｺｰﾄﾞ])
        DataUtil.setValueByMethodName(frid, "setShnCd" + inx, CCStringUtil.fixString(jan13, fixLen));
        DataUtil.setValueByMethodName(frid, "setPluKbn" + inx, shohin[0]);
        DataUtil.setValueByMethodName(frid, "setCbnrCd" + inx, shohin[4]);
        DataUtil.setValueByMethodName(frid, "setSbnrCd" + inx, shohin[5]);

        DataUtil.setValueByMethodName(frid, "setIriSu" + inx, caseSu.intValue());
        DataUtil.setValueByMethodName(frid, "setHattyuIrisu" + inx, hattyuIrisu);
        DataUtil.setValueByMethodName(frid, "setTani" + inx, shohin[8]);
    }

    /**
     * 処理⑩：移動伝票累積Ｆ<br>
     * ※1..※7
     * 
     * @param form 資料
     * @param frid データベースレコード
     */
    private void setInforS003frid(Sijp0040ResultForm form, S003frid frid) {
        if (frid == null) {
            return;
        }

        // 確定場所 = cmJSICommon.getDefaultTenpo
        // 引数: Requestの担当者コードをセット
        String defaultTenpo = cmJSICommon.getDefaultTenpo(context.getTantoCode());
        // 確定区分 = cmJSICommon.getKakuteiKbn
        String kakuteiKbn = EMPTY;
        kakuteiKbn = cmJSICommon.getKakuteiKbn(defaultTenpo, form.getSyukaYmd());
        // ※1
        frid.setEntryKbn(kakuteiKbn);
        // ※2
        frid.setEntryPlace(defaultTenpo);

        // 累計計上日 = cmJSICommon.getKeijoDate
        String keijoDate = cmJSICommon.getKeijoDate(form.getSyukaYmd());
        // ※3に累計計上日をセット - ※substring(0, 6)
        if (!CCStringUtil.isEmpty(keijoDate) && keijoDate.length() > 6) {
            frid.setSirGetujiYm(keijoDate.substring(0, 6));
        }

        // Ｆエール扱い区分取得処理
        // M004tbumMapperのselectByExampleを使用する。
        String outTenpoCd = form.getKaisyaCd() + form.getJigyobuCd() + form.getTenCd();
        // 店舗 = 画面項目[出荷店.会社 + 出荷店.事業部 + 出荷店.店舗] ---- ※substring(2,4)
        // 部門 = 店舗 + 画面項目[出荷店.部門]
        String jigyobuCd = outTenpoCd.substring(2, 4);
        String outBmnCd = jigyobuCd + form.getBmnCd();

        // ※4に取得したレコード[FY_KBN]をセット
        String faKubun = cmJSICommon.getFAKubun(outTenpoCd, outBmnCd, form.getSyukaYmd());
        frid.setAtukaiKbn(faKubun);

        // 明細数 = 0
        short mesaiSu = 0;
        // グリッドの件数でループして、以下の処理を行う：
        for (Sijp0040Msai msai : form.getDataArea()) {
            // グリッド(index).出荷店商品ｺｰﾄﾞ <> ""場合
            if (msai.getDisplayFlag()) {
                // 明細数 = 明細数 + 1
                mesaiSu = (short) (mesaiSu + 1);
            }
        }
        // ※5に明細数をセット
        frid.setMsaiSu(new Short(mesaiSu));

        // cmJSICommon.getComTenpoNameAを使用
        String inTenpoCd = form.getSubKaisyaCd() + form.getSubJigyobuCd() + form.getSubTenCd();
        // String inBmnCd = form.getSubJigyobuCd() + form.getSubBmnCd();
        String syukaYmd = form.getSyukaYmd();

        if (syukaYmd == null || EMPTY.equals(syukaYmd)) {
            // ※""の場合は現在日付"yyyyMMdd"をセット
            syukaYmd = new DateTime().toString(CCDateUtil.DATETIME_FORMAT_DATE_YYYYMMDD);
        }

        String tenpoNameAIn = cmJSICommon.getComTenpoNameA(inTenpoCd, syukaYmd);
        // ※右スペースパディング（20桁数）
        tenpoNameAIn = isEmpty(tenpoNameAIn) ? EMPTY : tenpoNameAIn;
        // トリムした入荷店舗名のサイズ > 20の場合
        // 入荷店舗名 = 入荷店舗名.substring(0, 20)
        if (tenpoNameAIn.length() > 20) {
            tenpoNameAIn = tenpoNameAIn.substring(0, 20);
        }

        // ※6に入荷店舗名をセット
        frid.setInTenNmA(tenpoNameAIn);

        // cmJSICommon.getComTenpoNameAを使用
        String tenpoNameAOut = cmJSICommon.getComTenpoNameA(outTenpoCd, syukaYmd);
        // ※右スペースパディング（20桁数）
        tenpoNameAOut = isEmpty(tenpoNameAIn) ? EMPTY : tenpoNameAIn;
        // トリムした入荷店舗名のサイズ > 20の場合
        // 入荷店舗名 = 入荷店舗名.substring(0, 20)
        if (tenpoNameAOut.length() > 20) {
            tenpoNameAOut = tenpoNameAOut.substring(0, 20);
        }

        // ※7に出荷店舗名をセット
        frid.setOutTenNmA(tenpoNameAOut);
    }

    /**
     * 処理⑨：移動明細履歴Ｆ<br>
     * S016frmrMapperのupdateByExampleSelectiveを使用する。
     * 
     * @param shoriTaipu 処理タイプ
     * @return データベースレコード
     */
    private S016frmr updateS016frmr(String shoriTaipu) {

        S016frmr frmr = new S016frmr();
        frmr.setSyoriSts(shoriTaipu);
        frmr.setCmnUpddd(CCDateUtil.getSysDateFormat(CCDateUtil.DATETIME_FORMAT_DATE_YYYYMMDD));
        frmr.setCmnUpdtime(CCDateUtil.getSysDateFormat(CCDateUtil.DATETIME_FORMAT_TIME));

        return frmr;
    }

    /**
     * 処理⑧：移動明細履歴Ｆ<br>
     * 4. S016frmrMapperのinsertSelectiveを使用する。
     * 
     * @param form 資料
     * @param shoriTaipu 処理タイプ
     * @param inTenpoCd 入荷店店舗コード
     * @param inBmnCd 入荷店部門コード
     * @param outTenpoCd 出荷店店舗コード
     * @param outBmnCd 出荷店部門コード
     * @return データベースレコード
     */
    private S016frmr insertS016frmr(Sijp0040ResultForm form, int shoriTaipu, String inTenpoCd, String inBmnCd,
            String outTenpoCd, String outBmnCd) {
        S016frmr frmr = new S016frmr();

        String insddd = CCDateUtil.getSysDateFormat(CCDateUtil.DATETIME_FORMAT_DATE_YYYYMMDD);
        String insTime = CCDateUtil.getSysDateFormat(CCDateUtil.DATETIME_FORMAT_TIME);

        frmr.setInsdd(insddd);
        frmr.setInstime(insTime);
        // ※右スペースパディング
        frmr.setDpyNo(form.getDpyNo());
        frmr.setDenKbn(form.getDpyKbn());
        frmr.setInTenpoCd(inTenpoCd);
        frmr.setInBmnCd(inBmnCd);
        frmr.setOutTenpoCd(outTenpoCd);
        frmr.setOutBmnCd(outBmnCd);

        // ※右スペースパディング（8桁数）
        frmr.setSyukaYmd(form.getSyukaYmd());
        frmr.setHatYmd(form.getSyukaYmd());
        frmr.setNhnYoteiYmd(form.getSyukaYmd());

        frmr.setRuiKeijoYmd(cmJSICommon.getKeijoDate(form.getSyukaYmd()));
        frmr.setKakuteiYmd(insddd);

        // 確定場所 = cmJSICommon.getDefaultTenpo
        // 引数: Requestの担当者コードをセット
        String defaultTenpo = cmJSICommon.getDefaultTenpo(context.getTantoCode());
        // 確定区分 = cmJSICommon.getKakuteiKbn
        String kakuteiKbn = cmJSICommon.getKakuteiKbn(defaultTenpo, form.getSyukaYmd());

        // ※1に確定区分をセット ----- ※右スペースパディング（2桁数）
        frmr.setEntryKbn(kakuteiKbn);
        // ※2に確定場所をセット------ ※右スペースパディング（7桁数）
        frmr.setEntryPlace(defaultTenpo);

        frmr.setKasanKbn(CCSIConst.KASAN_KBN.KBN_SUKIN_A);
        // 処理⑧の引数の処理タイプ == cmJSICommon.CN_DELETEの場合
        if (shoriTaipu == CCSICommon.CN_DELETE) {
            // ※3にcmJSIConst.SYORISTS_KBN.KBN_DELETEをセット
            frmr.setDispSyoriSts(CCSIConst.SYORISTS_KBN.KBN_DELETE);
            frmr.setSyoriSts(CCSIConst.SYORISTS_KBN.KBN_DELETE);
        } else {
            // ※3にcmJSIConst.SYORISTS_KBN.KBN_KAKTEIをセット
            frmr.setDispSyoriSts(CCSIConst.SYORISTS_KBN.KBN_KAKTEI);
            frmr.setSyoriSts(CCSIConst.SYORISTS_KBN.KBN_KAKTEI);
        }
        frmr.setMaeGenkgk(form.getSumKenGenkIn());
        frmr.setAtoGenkgk(form.getInpGenInp());

        frmr.setMaeBaikgk(form.getSumKenBaikKin());
        frmr.setAtoBaikgk(form.getSumKenBaikKin());
        frmr.setCmnTantoCd(context.getTantoCode());
        String termName = context.getTermName();
        if (termName.getBytes().length > 8) {
            termName = new String(Arrays.copyOf(termName.getBytes(), 8));
        }
        frmr.setCmnTermId(termName);
        frmr.setCmnInsdd(insddd);
        frmr.setCmnUpddd(insddd);
        frmr.setCmnUpdtime(insTime);

        // 4．S016frmrMapperのinsertSelectiveを使用する。
        return frmr;
    }

    /**
     * 処理仕様(その他関数)シートの処理⑦を参照。<br>
     * S003fridMapperのdeleteByExampleを使用する。
     * 
     * @param dpyNo 伝票番号
     * @param dpyKbn 伝票区分
     * @param inTenpoCd 入荷店店舗コード
     * @param inBmnCd 入荷店部門コード
     * @param outTenpoCd 出荷店店舗コード
     * @param outBmnCd 出荷店部門コード
     * @return データベースレコード
     */
    private S003fridExample deleteS003frid(String dpyNo, String dpyKbn, String inTenpoCd, String inBmnCd,
            String outTenpoCd, String outBmnCd) {
        S003fridExample s003Example = new S003fridExample();
        s003Example.createCriteria().andDpyNoEqualTo(dpyNo).andDenKbnEqualTo(dpyKbn).andInTenpoCdEqualTo(inTenpoCd)
                .andInBmnCdEqualTo(inBmnCd).andOutTenpoCdEqualTo(outTenpoCd).andOutBmnCdEqualTo(outBmnCd);

        return s003Example;
    }

    /**
     * Sijp0040MapperのselectS007frit01を使用.<br>
     * select max(SEQNO) from table S007FRIT
     * 
     * @param dpyKbn 伝区
     * @param dpyNo 伝票NO
     * @param inTenpoCd 入荷店店舗コード
     * @param inBmnCd 入荷店部門コード
     * @param outTenpoCd 出荷店店舗コード
     * @param outBmnCd 出荷店部門コード
     * @return true or false
     */
    private Integer getSeqNoData(String dpyNo, String dpyKbn, String inTenpoCd, String inBmnCd, String outTenpoCd,
            String outBmnCd) {
        // Sijp0040MapperのselectS007frit01を使用
        Map<String, String> params = new HashMap<String, String>();
        params.put("dpyNo", dpyNo);
        params.put("dpyKbn", dpyKbn);
        params.put("inTenpoCd", inTenpoCd);
        params.put("inBmnCd", inBmnCd);
        params.put("outTenpoCd", outTenpoCd);
        params.put("outBmnCd", outBmnCd);

        Integer maxSeqNo = dao.selectOne("sijp0040SelectS007frit01", params);

        if (maxSeqNo == null) {
            return 0;
        } else {
            return maxSeqNo;
        }
    }

    /**
     * 3．S007fritMapperのinsertSelectiveを使用する。
     * 
     * @param frid データベースレコード
     * @param shoriTaipu 処理タイプ
     * @param koshinKbn 更新区分
     * @param seqno SEQNO変数
     * @return データベースレコード
     */
    private S007frit insertS007fritFromS003frid(S003frid frid, int shoriTaipu, String koshinKbn, int seqno) {
        // ※各項目を両端トリム
        String insdd = CCDateUtil.getSysDateFormat(CCDateUtil.DATETIME_FORMAT_DATE_YYYYMMDD);
        String insTime = CCDateUtil.getSysDateFormat(CCDateUtil.DATETIME_FORMAT_TIME);

        S007frit frit = new S007frit();
        BeanUtils.copyProperties(frid, frit);

        frit.setInsdd(insdd);
        // ※1、※2
        setInfoS007frit(frit, shoriTaipu, koshinKbn, seqno);

        // frit.setSeqno(); // update by ※1、※2
        frit.setInstime(insTime);
        frit.setRuiFlg("1"); // 固定値['1']をセット

        frit.setCmnTantoCd(context.getTantoCode());
        String termName = context.getTermName();
        if (termName.getBytes().length > 8) {
            termName = new String(Arrays.copyOf(termName.getBytes(), 8));
        }
        frit.setCmnTermId(termName);
        frit.setCmnInsdd(insdd);
        frit.setCmnUpddd(insdd);
        frit.setCmnUpdtime(insTime);

        return frit;
    }

    /**
     * 処理⑥：移動伝票入力Ｔ（前日）
     * 3．S007fritMapperのinsertSelectiveを使用する。
     * ※1、※2
     * 
     * @param frit データベースレコード
     * @param shoriTaipu 引数の処理タイプ
     * @param koshinKbn 更新区分
     * @param seqno SEQNO変数
     */
    private void setInfoS007frit(S007frit frit, int shoriTaipu, String koshinKbn, int seqno) {
        if (frit == null) {
            return;
        }

        if (shoriTaipu == CCSICommon.CN_INSERT || shoriTaipu == CCSICommon.CN_UPDATE) {
            // 引数の処理タイプ == cmJSICommon.CN_INSERTまたは引数の処理タイプ == cmJSICommon.CN_UPDATE場合

            if (CCSICommon.CN_REC_ZEN.equals(koshinKbn)) {
                // 引数の更新区分 == cmJSICommon.CN_REC_ZEN場合
                // ※1にcmJSICommon.CN_REC_ZENをセット
                // ※2にcmJSICommon.CN_UPDKBN_Dをセット
                frit.setSeqno(CCSICommon.CN_REC_ZEN_0);
                frit.setUpdateKbn(CCSICommon.CN_UPDKBN_D);
            } else {
                // 引数の更新区分 <> cmJSICommon.CN_REC_ZEN場合
                // ※1にSEQNO変数 + 1をセット
                // ※2にcmJSICommon.CN_UPDKBN_Iをセット
                frit.setSeqno(new Short((short) (seqno + 1)));
                frit.setUpdateKbn(CCSICommon.CN_UPDKBN_I);
            }
        } else if (shoriTaipu == CCSICommon.CN_DELETE) {
            // 引数の処理タイプ == cmJSICommon.CN_DELETE場合
            // ※2にcmJSICommon.CN_UPDKBN_Dをセット
            frit.setUpdateKbn(CCSICommon.CN_UPDKBN_D);

            if (CCSICommon.CN_REC_ZEN.equals(koshinKbn)) {
                // 引数の更新区分 == cmJSICommon.CN_REC_ZEN場合
                // ※1にcmJSICommon.CN_REC_ZENをセット
                frit.setSeqno(CCSICommon.CN_REC_ZEN_0);
            } else {
                // 引数の更新区分 <> cmJSICommon.CN_REC_ZEN場合
                // ※1にSEQNO変数 + 1をセット
                frit.setSeqno(new Short((short) (seqno + 1)));
            }
        }
    }

    /**
     * 処理④：グリッド入力項目チェック処理
     * 
     * @param form 資料
     * @return true if valid
     */
    private boolean checkData(Sijp0040ResultForm form) {
        String ten7Out = form.getKaisyaCd() + form.getJigyobuCd() + form.getTenCd();
        String ten7In = form.getSubKaisyaCd() + form.getSubJigyobuCd() + form.getSubTenCd();
        String bumon5Out = form.getJigyobuCd() + form.getBmnCd();
        String bumon5In = form.getSubJigyobuCd() + form.getSubBmnCd();
        // ブランクレコードに0で初期化
        int blankCount = 0;
        // 原価金集計に0で初期化
        double kenGenkKin = 0d;
        Long genGokei = new Long(0);
        // 売価金集計に0で初期化
        double kenBaikKin = 0d;
        Long baiGokei = new Long(0);
        List<Sijp0040Msai> data = form.getDataArea();

        BigDecimal bdZero = new BigDecimal(0);
        Integer itZero = new Integer(0);
        // チェック用基準日設定 未入力ならとりあえずシステム日付を使ってチェック＆名称表示
        String sChkKijunYmd = form.getSyukaYmd();
        if (isEmpty(sChkKijunYmd)) {
            sChkKijunYmd = CCDateUtil.getSysDateFormat(CCDateUtil.DATETIME_FORMAT_DATE_YYYYMMDD);
        }

        // 1．ＪＡＮコード変換処理
        // 2．チェックフラグをtrueで初期化。
        boolean flag = true;
        int inx = -1;
        // 3．グリッド内で以下のチェック処理をループ
        for (Sijp0040Msai row : data) {
            inx++;
            // // 有効ﾚｺｰﾄﾞｾｯﾄ
            doSetDispFlg(row);

            // グリッド(index).出荷店商品コード未入力場合
            if (!row.getDisplayFlag()) {
                blankCount = blankCount + 1;
                continue;
            }

            // 次レコードチェックを行う。
            // 3-1．商品コードチェック
            // 出荷店商品変数に上記処理1の戻る値セット
            // sShnCd = cnvJanToJan14(sShnCd); // Old code: SISV0040: 1317
            String outShnCd14 = cnvJanToJan14(row.getOutShnCd());

            // グリッド(index).出荷店商品コード未入力場合
            if (isEmpty(row.getOutShnCd())) {
                // グリッド(index).数量が(<>0)またはグリッド(index).原単価が(<>0)
                // またはグリッド(index).売単価が(<>0)またはグリッド(index).入荷店商品コードが(<>"")場合
                if (bdZero.compareTo(row.getKenBarasu()) != 0 || bdZero.compareTo(row.getKenGenk()) != 0
                        || !itZero.equals(row.getKenBaik()) || !isEmpty(row.getInShnCd())) {
                    // 「S1022」メッセージが表示される。
                    // ※メッセージ埋め込み文字：なし
                    errRes.addErrorInfo("outShnCd_" + inx, CCMessageConst.MSG_KEY_DATA_INPUT_INVALID);
                    flag = false;
                }
            }

            // cmJSICommon.chkShohinCodeを使用
            // 出荷店商品変数
            // 画面項目[出荷店.会社 + 出荷店.事業部 + 出荷店.店舗]
            // 画面項目[出荷日] ※""の場合は現在日付"yyyyMMdd"をセット
            int shohinCode = cmJSICommon.chkShohinCode(outShnCd14, ten7Out, sChkKijunYmd);
            if (shohinCode == 1) {
                // 「S1017」メッセージが表示される。
                errRes.addErrorInfo("outShnCd_" + inx, CCMessageConst.MSG_KEY_NOT_REGISTER_IN_MASTER);
                flag = false;
            } else if (shohinCode == 2) {
                // 「S1018」メッセージが表示される。
                errRes.addErrorInfo("outShnCd_" + inx, CCMessageConst.MSG_KEY_NOT_SHOP_REGISTER_IN_MASTER);
                flag = false;
            } else if (shohinCode == 3) {
                // 「S1023」メッセージが表示される。
                errRes.addErrorInfo("outShnCd_" + inx, CCMessageConst.MSG_KEY_SHOHIN_CODE_NOT_VALID_3);
                flag = false;
            } else if (shohinCode == 9) {
                flag = false;
            }

            // 3-2．部門コードチェック
            // cmJSICommon.chkBumonCodeを使用
            // 画面項目[出荷店.事業部 + 出荷店.部門]
            // 出荷店商品変数
            // 画面項目[出荷店.会社 + 出荷店.事業部 + 出荷店.店舗]
            // 画面項目[出荷日]
            boolean bumonCode = cmJSICommon.chkBumonCode(bumon5Out, outShnCd14, ten7Out, sChkKijunYmd);
            if (!bumonCode) {
                // 「S1038」メッセージが表示される。
                errRes.addErrorInfo("outShnCd_" + inx, CCMessageConst.MSG_KEY_PRODUCT_DEPARTMENT_INVALID);
                flag = false;
            }

            // 3-3．数量チェック
            if (row.getKenBarasu() == null || bdZero.compareTo(row.getKenBarasu()) == 0) {
                // 3-3-1．グリッド(index).数量が未入力またはグリッド(index).数量の値が0の場合
                // 「S1027」メッセージが表示される。
                errRes.addErrorInfo("kenBarasu_" + inx, CCMessageConst.MSG_KEY_PRODUCT_QUANTITY_EMPTY);
                flag = false;
            } else {
                // 3-3-2．グリッド(index).数量が未入力またはグリッド(index).数量の値が(<>0)の場合
                // グリッド(index).数量の値 > cmJSICommon.CN_SURYO_MAXの場合
                if (row.getKenBarasu().compareTo(new BigDecimal(CCSICommon.CN_SURYO_MAX)) > 0) {
                    // 「S1036」メッセージが表示される。
                    errRes.addErrorInfo("kenBarasu_" + inx, CCMessageConst.MSG_KEY_NUMBER_TOO_LARGE);
                    flag = false;
                }

                // グリッド(index).数量の値 < 0の場合
                if (row.getKenBarasu().compareTo(bdZero) < 0) {
                    // 「S1024」メッセージが表示される。
                    errRes.addErrorInfo("kenBarasu_" + inx, CCMessageConst.MSG_KEY_NUMBER_NEGATIVE_INVALID);
                    flag = false;
                }
            }

            // 3-4．変数用計算：
            // 変数１ = cmJSICommon.getTaxKbn
            // 出荷店商品変数
            // 画面項目[出荷日]
            // 画面項目[出荷店.会社 + 出荷店.事業部 + 出荷店.店舗]
            // 画面項目[出荷店.事業部 + 出荷店.部門]
            String taxKbn = cmJSICommon.getTaxKbn(outShnCd14, sChkKijunYmd, ten7Out, bumon5Out);

            // 税区分変数 = cmJSICommon.getExcOrIndTaxを使用
            String zeiKubun = cmJSICommon.getExcOrIndTax(taxKbn);

            // 売価還元区分 = cmJSICommon.getBaikankbn
            // 画面項目[出荷店.会社 + 出荷店.事業部 + 出荷店.店舗]
            // 画面項目[出荷店.事業部 + 出荷店.部門]
            // 画面項目[出荷日]
            String baikanKbn = cmJSICommon.getBaikankbn(ten7Out, bumon5Out, sChkKijunYmd);

            // マスタ定番原単価 = cmJSICommonのgetGenTankaを使用
            // 出荷店商品変数
            // 画面項目[出荷店.会社 + 出荷店.事業部 + 出荷店.店舗]
            // 画面項目[出荷日]
            String tanka = EMPTY;
            tanka = cmJSICommon.getGenTanka(outShnCd14, ten7Out, sChkKijunYmd, false);

            // 3-5．原単価のチェック
            // 3-5-1．Whileループ開始
            while (true) {
                // グリッド(index).原単価の値 < 0の場合
                if (bdZero.compareTo(row.getKenGenk()) == 0) {
                    // 「C0012」メッセージが表示される。
                    errRes.addErrorInfo("kenGenk_" + inx, CCMessageConst.MSG_KEY_NOT_KIP_ITEM);
                    flag = false;
                    // ループを中止
                    break;
                }
                if (bdZero.compareTo(row.getKenGenk()) > 0) {
                    // 「S1024」メッセージが表示される。
                    errRes.addErrorInfo("kenGenk_" + inx, CCMessageConst.MSG_KEY_NUMBER_NEGATIVE_INVALID);
                    flag = false;
                    // ループを中止
                    break;
                }
                // グリッド(index).原単価の値 >= cmJSICommon.CN_TANKA_MAXの場合
                if (new BigDecimal(CCSICommon.CN_TANKA_MAX).compareTo(row.getKenGenk()) < 0) {
                    // 「S1036」メッセージが表示される。=> S5153
                    // ※メッセージ埋め込み文字："（７桁＋少数２桁）"
                    errRes.addErrorInfo(CCMessageConst.MSG_KEY_NUMBER_TOO_LARGE);
                    errRes.addErrorInfo("kenGenk_" + inx, CCMessageConst.MSG_KEY_KENGENK_OVER_TANKA_MAX);
                    flag = false;
                    // ループを中止
                    break;
                }

                // マスタ定番原単価が""の場合
                if (EMPTY.equals(tanka)) {
                    // 「S1075」メッセージが表示される。
                    errRes.addErrorInfo("kenGenk_" + inx, CCMessageConst.MSG_KEY_FAIL_GET_DATA_PRODUCT);
                    flag = false;
                    // ループを中止
                    break;
                }
                // ループを中止
                break;
            }

            String masutaTeibanUriTanka = EMPTY;

            // 3-6．売単価のチェック
            // 3-6-1．Whileループ開始
            while (true) {
                // グリッド(index).売単価の値 < 0の場合
                if (row.getKenBaik() < 0) {
                    // 「S1024」メッセージが表示される。
                    errRes.addErrorInfo("kenBaik_" + inx, CCMessageConst.MSG_KEY_NUMBER_NEGATIVE_INVALID);
                    flag = false;
                    // ループを中止
                    break;
                }
                // グリッド(index).売単価の値 >= cmJSICommon.CN_TANKA_MAXの場合
                if (row.getKenBaik() >= CCSICommon.CN_TANKA_MAX) {
                    // 「S1036」メッセージが表示される。=> [S5154]
                    // ※メッセージ埋め込み文字："（７桁）"
                    errRes.addErrorInfo("kenBaik_" + inx, CCMessageConst.MSG_KEY_KENBAIK_OVER_TANKA_MAX);
                    flag = false;
                    // ループを中止
                    break;
                }
                // マスタ定番売単価 = cmJSICommonのgetBaiTankaを使用
                // 出荷店商品変数
                // 画面項目[出荷店.会社 + 出荷店.事業部 + 出荷店.店舗]
                // 画面項目[出荷日]
                // false
                masutaTeibanUriTanka = cmJSICommon.getBaiTanka(outShnCd14, ten7Out, sChkKijunYmd, false);

                // マスタ定番売単価が""の場合
                if (EMPTY.equals(masutaTeibanUriTanka)) {
                    // 「S1075」メッセージが表示される。
                    errRes.addErrorInfo("kenBaik_" + inx, CCMessageConst.MSG_KEY_FAIL_GET_DATA_PRODUCT);
                    flag = false;
                    // ループを中止
                    break;
                }

                // double dblMstTeibanBaiTan = clSICom.cnvStrToDbl(sMstTeibanBaiTanka) ;
                double dblMstTeibanBaiTan = CCStringUtil.cnvStrToDbl(masutaTeibanUriTanka);

                // 売価還元区分 == cmJSIConst.BAIKA_KANRI.BAIKA_KANGENの場合
                if (CCSIConst.BAIKA_KANRI.BAIKA_KANGEN.equals(baikanKbn)) {
                    // マスタ定番売単価の値 <> 0の場合
                    if (dblMstTeibanBaiTan != 0) {
                        // 税区分変数 <> cmJSIConst.TAX_IO_KBN.TAX_IN_KBNの場合
                        if (!CCSIConst.TAX_IO_KBN.TAX_IN_KBN.equals(zeiKubun)) {
                            // マスタ定番売単価の値 <> グリッド(index).売単価の値場合

                            if (!new Double(dblMstTeibanBaiTan).equals(new Double(row.getKenBaik()))) {
                                // 「S1269」メッセージが表示される。
                                errRes.addErrorInfo("kenBaik_" + inx, CCMessageConst.MSG_KEY_PRICE_DIFFERENT_SALES);
                                flag = false;
                                // ループを中止
                                break;
                            }
                        }
                    }
                }

                // ループを中止
                break;
            }
            // 3-6. End

            // 3-7．売価還元区分チェック
            // 売価還元区分 <> cmJSIConst.BAIKA_KANRI.BAIKA_KANGEN場合
            if (!CCSIConst.BAIKA_KANRI.BAIKA_KANGEN.equals(baikanKbn)) {
                // グリッド(index).売単価の値 <> 0場合
                if (!itZero.equals(row.getKenBaik())) {
                    // 「S1243」メッセージが表示される。
                    errRes.addErrorInfo("kenBaik_" + inx, CCMessageConst.MSG_KEY_PRICE_SELL_ZERO);
                    flag = false;
                }
            } else {
                // 売価還元区分 == cmJSIConst.BAIKA_KANRI.BAIKA_KANGEN場合
                // マスタ定番売単価が""場合
                if (EMPTY.equals(masutaTeibanUriTanka)) {
                    // 「S1075」メッセージが表示される。
                    errRes.addErrorInfo("kenBaik_" + inx, CCMessageConst.MSG_KEY_FAIL_GET_DATA_PRODUCT);
                    flag = false;
                } else {
                    // double dblMstTeibanBaiTan = clSICom.cnvStrToDbl(sMstTeibanBaiTanka) ;
                    double dblMstTeibanBaiTan = CCStringUtil.cnvStrToDbl(masutaTeibanUriTanka);
                    // マスタ定番売単価が""以外場合
                    if (dblMstTeibanBaiTan != 0) {
                        // マスタ定番売単価の値 <> 0場合
                        // 税区分変数 <> cmJSIConst.TAX_IO_KBN.TAX_IN_KBNの場合
                        if (!CCSIConst.TAX_IO_KBN.TAX_IN_KBN.equals(zeiKubun)) {
                            // マスタ定番売単価の値 <> グリッド(index).売単価の値場合
                            if (dblMstTeibanBaiTan != row.getKenBaik()) {
                                // 「S1269」メッセージが表示される。
                                errRes.addErrorInfo("kenBaik_" + inx, CCMessageConst.MSG_KEY_PRICE_DIFFERENT_SALES);
                                flag = false;
                            }
                        }
                    } else {
                        // マスタ定番売単価の値 == 0場合
                        // グリッド(index).売単価の値 == 0場合
                        if (itZero.equals(row.getKenBaik())) {
                            // 「S1270」メッセージが表示される。
                            errRes.addErrorInfo("kenBaik_" + inx, CCMessageConst.MSG_KEY_NOT_INPUT_ZERO);
                            flag = false;
                        }
                    }
                }
            }

            // 入荷店商品変数に上記処理1の戻る値セット
            if (isEmpty(row.getInShnCd())) {
                row.setInShnCd(row.getOutShnCd());
            }

            String inShnCd14 = cnvJanToJan14(row.getInShnCd());

            // 3-8．商品ﾁｪｯチェック
            // 商品エラー = cmJSICommon.chkShohinCode
            // 出荷店商品変数
            // 画面項目[出荷店.会社 + 出荷店.事業部 + 出荷店.店舗]
            // 画面項目[出荷日]
            int chkSubShohinCode = cmJSICommon.chkShohinCode(inShnCd14, ten7In, sChkKijunYmd);
            // 商品エラー == 1場合
            if (chkSubShohinCode == 1) {
                // 「S1017」メッセージが表示される。
                errRes.addErrorInfo("inShnCd_" + inx, CCMessageConst.MSG_KEY_NOT_REGISTER_IN_MASTER);
                flag = false;
            } else if (chkSubShohinCode == 2) {
                // 「S1018」メッセージが表示される。
                errRes.addErrorInfo("inShnCd_" + inx, CCMessageConst.MSG_KEY_NOT_SHOP_REGISTER_IN_MASTER);
                flag = false;
            } else if (chkSubShohinCode == 3) {
                // 「S1023」メッセージが表示される。
                errRes.addErrorInfo("inShnCd_" + inx, CCMessageConst.MSG_KEY_PRICE_DIFFERENT_SALES);
                flag = false;
            } else if (chkSubShohinCode == 9) {
                // チェックフラグにfalseをセット
                flag = false;
            }

            // 3-9．部門ﾁｪｯチェック
            // 部門エラー = cmJSICommon.chkBumonCode
            // 画面項目[入荷店.事業部 + 入荷店.部門]
            // 入荷店商品変数
            // 画面項目[入荷店.会社 + 入荷店.事業部 + 入荷店.店舗]
            // 画面項目[出荷日]
            boolean chkSubBumonCode = cmJSICommon.chkBumonCode(bumon5In, inShnCd14, ten7In, sChkKijunYmd);

            // 部門エラーがfalse場合
            if (!chkSubBumonCode) {
                // 「S1038」メッセージが表示される。
                errRes.addErrorInfo("inShnCd_" + inx, CCMessageConst.MSG_KEY_PRODUCT_DEPARTMENT_INVALID);
                flag = false;
            }

            // 3-10．原価金/売価金チェック
            // 原価金額 = xxJNumberEdit.multiplyDouble
            // グリッド(index).数量
            // グリッド(index).原単価
            Double genkaKingaku = CCNumberUtil.multiplyBigDecimal(row.getKenBarasu(), row.getKenGenk());
            // 売価金額 = xxJNumberEdit.multiplyDouble
            // グリッド(index).数量
            // グリッド(index).売単価
            Double baikaKingaku =
                    CCNumberUtil.multiplyBigDecimal(row.getKenBarasu(), new BigDecimal(row.getKenBaik()));
            // Reset auto compute
            row.setKenGenkKin(genkaKingaku.longValue());
            row.setKenBaikKin(baikaKingaku.longValue());

            // 原価金額 >= clSICom.CN_KINGAK_MAX場合
            if (genkaKingaku.compareTo(new Double(CCSICommon.CN_KINGAK_MAX)) >= 0) {
                // 「S1036」メッセージが表示される。 => S5255
                // ※メッセージ埋め込み文字："（原価金額）"
                errRes.addErrorInfo("kenBarasu_" + inx, CCMessageConst.MSG_KEY_KENGENKKIN_OVER_TANKA_MAX);
                flag = false;
            }

            // 売価金額 >= clSICom.CN_KINGAK_MAX場合
            if (baikaKingaku.compareTo(new Double(CCSICommon.CN_KINGAK_MAX)) >= 0) {
                // 「S1036」メッセージが表示される。=> S5256
                // ※メッセージ埋め込み文字："（売価金額）"
                errRes.addErrorInfo("kenBarasu_" + inx, CCMessageConst.MSG_KEY_KENBAIKKIN_OVER_TANKA_MAX);
                flag = false;
            }

            // 原価金集計 = 原価金集計 + 原価金額
            // 売価金集計 = 売価金集計 + 売価金額
            kenGenkKin = kenGenkKin + genkaKingaku;
            kenBaikKin = kenBaikKin + baikaKingaku;
            genGokei = genGokei + row.getKenGenkKin();
            baiGokei = baiGokei + row.getKenBaikKin();

            // 原価金集計 >= clSICom.CN_KINALL_MAX場合
            if (kenGenkKin >= CCSICommon.CN_KINALL_MAX) {
                // 「S1036」メッセージが表示される。=> S5255
                // ※メッセージ埋め込み文字："（原価金額）"
                errRes.addErrorInfo("kenBarasu_" + inx, CCMessageConst.MSG_KEY_KENGENKKIN_OVER_TANKA_MAX);
                flag = false;
            }

            // 売価金集計 >= clSICom.CN_KINALL_MAX場合
            if (kenBaikKin >= CCSICommon.CN_KINALL_MAX) {
                // 「S1036」メッセージが表示される。 => S5256
                // ※メッセージ埋め込み文字："（原価金額）"
                errRes.addErrorInfo("kenBarasu_" + inx, CCMessageConst.MSG_KEY_KENBAIKKIN_OVER_TANKA_MAX);
                flag = false;
            }
        }

        // 4．ブランクレコード == 10の場合
        if (blankCount == 10) {
            // 「C0072」メッセージが表示される。=> C0072
            // ※メッセージ埋め込み文字：
            errRes.addErrorInfo("outShnCd_0", CCMessageConst.MSG_KEY_VALUE_NO_INPUT);
            flag = false;
        }

        // 5．原価金額チェック
        if (form.getInpGenInp() == null) {
            // 5-1．フッタエリア.(入力)原価金額合計が(="")の場合
            // 「C0012」メッセージが表示される。
            errRes.addErrorInfo("inpGenInp", CCMessageConst.MSG_KEY_NOT_KIP_ITEM);
            flag = false;
        } else {
            // 5-2．フッタエリア.(入力)原価金額合計が(<>"")の場合
            // フッタエリア.(入力)原価金額合計 <> フッタエリア.原価金額合計の場合
            // if (!form.getInpGenInp().equals(form.getSumKenGenkIn())) {
            if (!form.getInpGenInp().equals(genGokei.longValue())) {
                // 「S1032」メッセージが表示される。
                errRes.addErrorInfo("inpGenInp", CCMessageConst.MSG_KEY_NOT_MATCH_VALUE_TWO_COLUMN);
                flag = false;
            }
        }

        // Set data
        form.setSumKenGenkIn(genGokei.longValue());
        form.setSumKenBaikKin(baiGokei.longValue());

        // 摘要
        if (isEmpty(form.getTekiyo())) {
            form.setTekiyo(EMPTY);
        } else {
            int byteLen = -1;
            byteLen = CCStringUtil.getByteLen(form.getTekiyo());

            int strLen = form.getTekiyo().length();
            if (byteLen != strLen) {
                // addMessageInfo( "C0255", sMsg_array, "parent.frames.tagframe.document.all.inpTekiyo" ) => C9053
                errRes.addErrorInfo("tekiyo", CCMessageConst.MSG_KEY_INPUT_TEXT_NOT_VALID);
                return false;
            }
        }
        // 6．チェックフラグを戻す。
        return flag;
    }

    /**
     * グリッドデータ有効行取得処理
     * <p>
     * 機能概要：<br>
     * グリッドのデータの有効行の取得を行います。
     * <p>
     * 作成日：<br>
     * <p>
     * 
     * @param sibn0040 グリッド行
     * @return true：有効、false：無効
     */
    private boolean doSetDispFlg(Sijp0040Msai sibn0040) {
        // 画面の行を取得
        // 有効ﾚｺｰﾄﾞﾁｪｯｸ
        String outShnCd = sibn0040.getOutShnCd();
        String inShnCd = sibn0040.getInShnCd();

        // doSetGrid
        if (sibn0040.getKenBarasu() == null) {
            sibn0040.setKenBarasu(new BigDecimal(0));
        }

        if (sibn0040.getKenGenk() == null) {
            sibn0040.setKenGenk(new BigDecimal(0));
        }

        if (sibn0040.getKenBaik() == null) {
            sibn0040.setKenBaik(new Integer(0));
        }

        if (sibn0040.getKenGenkKin() == null) {
            sibn0040.setKenGenkKin(new Long(0));
        }

        if (sibn0040.getKenBaikKin() == null) {
            sibn0040.setKenBaikKin(new Long(0));
        }
        // doSetGrid end

        if (!isEmpty(outShnCd) || !isEmpty(inShnCd)) { // 商品コード
            sibn0040.setDisplayFlag(true);
            return true;
        }

        // 数量
        if (sibn0040.getKenBarasu() != null && (sibn0040.getKenBarasu().compareTo(new BigDecimal(0)) != 0)) {
            sibn0040.setDisplayFlag(true);
            return true;
        }

        // 原単価
        if (sibn0040.getKenBaik() != null && !sibn0040.getKenBaik().equals(new Integer(0))) {
            sibn0040.setDisplayFlag(true);
            return true;
        }

        // 売単価
        if (sibn0040.getKenGenk() != null && sibn0040.getKenGenk().compareTo(new BigDecimal(0)) != 0) {
            sibn0040.setDisplayFlag(true);
            return true;
        }

        sibn0040.setDisplayFlag(false);
        return false;
    }

    /**
     * 1．ＪＡＮコード変換処理
     * 
     * @param janCd 商品コード
     * @return 商品コード
     */
    private String cnvJanToJan14(String janCd) {
        if (janCd == null || EMPTY.equals(janCd.trim())) {
            return EMPTY;
        } else if (janCd.length() <= 13) {
            // String temp = " " + CCStringUtil.fixString(janCd, 13);
            String temp = plucnv.toDbCode(CCStringUtil.suppZero(janCd, 13));
            return temp;
        } else {
            String temp = plucnv.toDbCode(janCd.substring(0, 13));
            return temp;
        }
    }

    /**
     * 処理①：ヘッダ情報の入力チェック
     * 
     * @param cond 条件フォーム
     * @param tenpo 変数.ログイン店舗
     * @param iKbn 処理区分
     * @return true：入力チェックＯＫ、false：入力チェックＮＧ
     */
    private boolean checkConditionForm(Sijp0040CondForm cond, String tenpo, int iKbn) {
        // 1．チェックフラグをtrueで初期化
        boolean flag = true;

        // 2．伝区チェック
        // 画面項目[伝区]がcmJSIConst.DEN_KBN.KBN_IDO以外場合
        if (!CCSIConst.DEN_KBN.KBN_IDO.equals(cond.getDpyKbn())) {
            // 「C0062」メッセージが表示される。
            // ※メッセージ埋め込み文字：なし
            errRes.addErrorInfo("dpyKbn", CCMessageConst.MSG_KEY_ITEM_INPUT_IS_ERROR);
            flag = false;
        }

//        // 3．伝票№チェック
//        if ("0".equals(cond.getDpyNo().substring(0, 1))) {
//            // 画面項目[伝票NO]のsubstring(0,1)が(="0")場合
//            // cmJSICommon.chkDenpyoNoを使用
//            boolean chkDenpyoNo = cmJSICommon.chkDenpyoNo(cond.getDpyNo());
//
//            // 戻る値が(=false)の場合
//            if (!chkDenpyoNo) {
//                // 「C0062」メッセージが表示される。
//                // ※メッセージ埋め込み文字：なし
//                errRes.addErrorInfo("dpyNo", CCMessageConst.MSG_KEY_ITEM_INPUT_IS_ERROR);
//                flag = false;
//            }
//        }

        // 4．出荷日チェック
        // 運用日付 = clSICom.getUnyoDate
        String unyoDate = context.getUnyoDate();
        String syukaDate = cond.getSyukaYmd();

        if (iKbn != CCSICommon.CN_DISPLAY && iKbn != CCSICommon.CN_DELETE) {
            if (!isEmpty(cond.getSyukaYmd())) {
                if (!CCDateUtil.isDate(syukaDate)) {
                    // 「C0011」メッセージが表示される。
                    errRes.addErrorInfo("syukaYmd", CCMessageConst.MSG_KEY_DATE_ERROR);
                    flag = false;
                } else {
                    if (syukaDate.compareTo(unyoDate) > 0) {
                        // 「U0001」メッセージが表示される。
                        // ※メッセージ埋め込み文字：なし
                        errRes.addErrorInfo("syukaYmd", CCMessageConst.MSG_KEY_DATE_BEFORE);
                        flag = false;
                    }
                }
            } else {
                // 「C0012」メッセージが表示される。
                errRes.addErrorInfo("syukaYmd", CCMessageConst.MSG_KEY_NOT_KIP_ITEM);
                flag = false;
            }
        }

        // 5．出荷店.会社チェック
        // cmJSICommon.getComKaisyaNameAを使用
        if (!CCNumberUtil.isNumeric(cond.getKaisyaCd())) {
            // 「C0062」メッセージが表示される。
            // ※メッセージ埋め込み文字：なし
            errRes.addErrorInfo("kaisyaCd", CCMessageConst.MSG_KEY_ITEM_INPUT_IS_ERROR);
            flag = false;
        } else {
            String kaisyaNameA = cmJSICommon.getComKaisyaNameA(cond.getKaisyaCd());
            if (kaisyaNameA.trim().length() == 0) {
                // トリムした戻る値.会社名(ｶﾅ)のサイズが(=0)の場合
                // 「C0062」メッセージが表示される。
                // ※メッセージ埋め込み文字：なし
                errRes.addErrorInfo("kaisyaCd", CCMessageConst.MSG_KEY_ITEM_INPUT_IS_ERROR);
                flag = false;
            }
        }

        // 6．出荷店.事業部チェック
        // cmJSICommon.getComJigyobuNameAを使用
        // 画面項目[出荷店.会社]
        // 画面項目[出荷店.事業部]
        if (!CCNumberUtil.isNumeric(cond.getJigyobuCd())) {
            // 「C0062」メッセージが表示される。
            // ※メッセージ埋め込み文字：なし
            errRes.addErrorInfo("jigyobuCd", CCMessageConst.MSG_KEY_ITEM_INPUT_IS_ERROR);
            flag = false;
        } else {
            String jigyobuNameA = cmJSICommon.getComJigyobuNameA(cond.getKaisyaCd(), cond.getJigyobuCd());
            if (jigyobuNameA.trim().length() == 0) {
                // トリムした戻る値.事業部名(ｶﾅ)のサイズが(=0)の場合
                // 「C0062」メッセージが表示される。
                // ※メッセージ埋め込み文字：なし
                errRes.addErrorInfo("jigyobuCd", CCMessageConst.MSG_KEY_ITEM_INPUT_IS_ERROR);
                flag = false;
            }
        }

        // 7．出荷店.店舗チェック
        String chkSyukaYmd = cond.getSyukaYmd();
        if (isEmpty(cond.getSyukaYmd())) {
            chkSyukaYmd = CCDateUtil.getSysDateFormat(CCDateUtil.DATETIME_FORMAT_DATE_YYYYMMDD);
        }
        // cmJSICommon.getComTenpoNameAを使用
        // 画面項目[出荷店.会社 + 出荷店.事業部 + 出荷店.店舗]
        // 画面項目[出荷日]
        if (!CCNumberUtil.isNumeric(cond.getTenCd())) {
            // 「C0062」メッセージが表示される。
            // ※メッセージ埋め込み文字：なし
            errRes.addErrorInfo("tenCd", CCMessageConst.MSG_KEY_ITEM_INPUT_IS_ERROR);
            flag = false;
        } else {
            String temp1 = cond.getKaisyaCd() + cond.getJigyobuCd() + cond.getTenCd();
            String tenpoNameA = cmJSICommon.getComTenpoNameA(temp1, chkSyukaYmd);

            if (tenpoNameA.trim().length() == 0) {
                // トリムした戻る値.店舗名(ｶﾅ)のサイズが(=0)の場合
                // 「C0062」メッセージが表示される。
                // ※メッセージ埋め込み文字：なし
                errRes.addErrorInfo("tenCd", CCMessageConst.MSG_KEY_ITEM_INPUT_IS_ERROR);
                flag = false;
            }
        }

        // 8．出荷店.部門チェック
        // cmJSICommon.getComBumonNameAを使用
        // 画面項目[出荷店.事業部 + 出荷店.部門]
        // 画面項目[出荷日] ※""の場合は現在日付"yyyyMMdd"をセット
        if (!CCNumberUtil.isNumeric(cond.getBmnCd())) {
            // 「C0062」メッセージが表示される。
            // ※メッセージ埋め込み文字：なし
            errRes.addErrorInfo("bmnCd", CCMessageConst.MSG_KEY_ITEM_INPUT_IS_ERROR);
            flag = false;
        } else {
            String bumonNameA = cmJSICommon.getComBumonNameA(cond.getJigyobuCd() + cond.getBmnCd(), chkSyukaYmd);

            if (bumonNameA.trim().length() == 0) {
                if (iKbn != CCSICommon.CN_DISPLAY && iKbn != CCSICommon.CN_DELETE) {
                    // トリムした戻る値.店舗名(ｶﾅ)のサイズが(=0)の場合
                    // 「C0062」メッセージが表示される。
                    // ※メッセージ埋め込み文字：なし
                    errRes.addErrorInfo("bmnCd", CCMessageConst.MSG_KEY_ITEM_INPUT_IS_ERROR);
                    flag = false;
                }
            }
        }

        // 9．入荷店.会社チェック
        // cmJSICommon.getComKaisyaNameAを使用
        // 画面項目[入荷店.会社]
        if (!CCNumberUtil.isNumeric(cond.getSubKaisyaCd())) {
            // 「C0062」メッセージが表示される。
            // ※メッセージ埋め込み文字：なし
            errRes.addErrorInfo("subKaisyaCd", CCMessageConst.MSG_KEY_ITEM_INPUT_IS_ERROR);
            flag = false;
        } else {
            String sKaisyaNameA = cmJSICommon.getComKaisyaNameA(cond.getSubKaisyaCd());

            if (sKaisyaNameA.trim().length() == 0) {
                // トリムした戻る値.会社名(ｶﾅ)のサイズが(=0)の場合
                // 「C0062」メッセージが表示される。
                // ※メッセージ埋め込み文字：なし
                errRes.addErrorInfo("subKaisyaCd", CCMessageConst.MSG_KEY_ITEM_INPUT_IS_ERROR);
                flag = false;
            }
        }

        // 10．入荷店.事業部チェック
        // cmJSICommon.getComJigyobuNameAを使用
        // 画面項目[入荷店.会社]
        // 画面項目[入荷店.事業部]
        if (!CCNumberUtil.isNumeric(cond.getSubJigyobuCd())) {
            // 「C0062」メッセージが表示される。
            // ※メッセージ埋め込み文字：なし
            errRes.addErrorInfo("subJigyobuCd", CCMessageConst.MSG_KEY_ITEM_INPUT_IS_ERROR);
            flag = false;
        } else {
            String sJigyobuNameA = cmJSICommon.getComJigyobuNameA(cond.getSubKaisyaCd(), cond.getSubJigyobuCd());
            if (sJigyobuNameA.trim().length() == 0) {
                // トリムした戻る値.事業部名(ｶﾅ)のサイズが(=0)の場合
                // 「C0062」メッセージが表示される。
                // ※メッセージ埋め込み文字：なし
                errRes.addErrorInfo("subJigyobuCd", CCMessageConst.MSG_KEY_ITEM_INPUT_IS_ERROR);
                flag = false;
            }
        }

        // 11．入荷店.店舗チェック
        // cmJSICommon.getComTenpoNameAを使用
        // 画面項目[入荷店.会社 + 入荷店.事業部 + 入荷店.店舗]
        // 画面項目[出荷日] ※""の場合は現在日付"yyyyMMdd"をセット
        if (!CCNumberUtil.isNumeric(cond.getSubTenCd())) {
            // 「C0062」メッセージが表示される。
            // ※メッセージ埋め込み文字：なし
            errRes.addErrorInfo("subTenCd", CCMessageConst.MSG_KEY_ITEM_INPUT_IS_ERROR);
            flag = false;
        } else {
            String temp2 = cond.getSubKaisyaCd() + cond.getSubJigyobuCd() + cond.getSubTenCd();
            String sTenpoNameA = cmJSICommon.getComTenpoNameA(temp2, chkSyukaYmd);
            if (sTenpoNameA.trim().length() == 0) {
                if (iKbn != CCSICommon.CN_DISPLAY && iKbn != CCSICommon.CN_DELETE) {
                    // トリムした戻る値.店舗名(ｶﾅ)のサイズが(=0)の場合
                    // 「C0062」メッセージが表示される。
                    // ※メッセージ埋め込み文字：なし
                    errRes.addErrorInfo("subTenCd", CCMessageConst.MSG_KEY_ITEM_INPUT_IS_ERROR);
                    flag = false;
                }
            }
        }

        // 12．入荷店.部門チェック
        // cmJSICommon.getComBumonNameAを使用
        // 画面項目[入荷店.事業部 + 入荷店.部門]
        // 画面項目[出荷日]
        if (!CCNumberUtil.isNumeric(cond.getSubBmnCd())) {
            // 「C0062」メッセージが表示される。
            // ※メッセージ埋め込み文字：なし
            errRes.addErrorInfo("subBmnCd", CCMessageConst.MSG_KEY_ITEM_INPUT_IS_ERROR);
            flag = false;
        } else {
            String sBumonNameA =
                    cmJSICommon.getComBumonNameA(cond.getSubJigyobuCd() + cond.getSubBmnCd(), chkSyukaYmd);
            if (sBumonNameA.trim().length() == 0) {
                if (iKbn != CCSICommon.CN_DISPLAY && iKbn != CCSICommon.CN_DELETE) {
                    // トリムした戻る値.店舗名(ｶﾅ)のサイズが(=0)の場合
                    // 「C0062」メッセージが表示される。
                    // ※メッセージ埋め込み文字：なし
                    errRes.addErrorInfo("subBmnCd", CCMessageConst.MSG_KEY_ITEM_INPUT_IS_ERROR);
                    flag = false;
                }
            }
        }

        // Old code
        if (iKbn == CCSICommon.CN_DISPLAY || iKbn == CCSICommon.CN_DELETE) {
            return flag;
        }

        // 14．cmJSICommon.getComNhnYmdを呼び出し
        // 変数.ログイン店舗
        // 画面項目[出荷日]
        String nhnYmd = cmJSICommon.getComNhnYmd(tenpo, chkSyukaYmd);
        // 戻る値[納品日] > 画面項目[出荷日]の場合
        if (nhnYmd.compareTo(chkSyukaYmd) > 0) {
            // 「S1099」メッセージが表示される。
            // ※メッセージ埋め込み文字：なし
            errRes.addErrorInfo("syukaYmd", CCMessageConst.MSG_KEY_DATE_OVER);
            flag = false;
        }

        // 15．チェックフラグがfalse場合
        // チェックフラグを戻す。
        if (!flag) {
            return flag;
        }

        // 16．出荷・入荷 会社コードが違うチェック
        // 画面項目[入荷店.会社] <> 画面項目[出荷店.会社]
        if (!cond.getSubKaisyaCd().equals(cond.getKaisyaCd())) {
            // 「S1215」メッセージが表示される。
            // ※メッセージ埋め込み文字：なし
            errRes.addErrorInfo("kaisyaCd", CCMessageConst.MSG_KEY_TWO_VALUE_NOT_EQUAL);
            errRes.addErrorInfo("subKaisyaCd", CCMessageConst.MSG_KEY_TWO_VALUE_NOT_EQUAL);
            flag = false;
        }

        // 17．出荷店チェック
        // M004tbumMapper.selectByExampleを使用
        // 画面項目[出荷日] ※""の場合は現在日付"yyyyMMdd"をセット
        // 店舗コード = 画面項目[出荷店.会社 + 出荷店.事業部 + 出荷店.店舗]
        // 部門コード = 画面項目[出荷店.事業部] + 画面項目[出荷店.部門]
        String uriKbn =
                getUriSiKubun(cond.getKaisyaCd() + cond.getJigyobuCd() + cond.getTenCd(), cond.getBmnCd(),
                        chkSyukaYmd);
        // 指定した日付で有効な直近のレコードを取得する。（1レコード）

        if (CCStringUtil.isEmpty(uriKbn)) {
            // 「C0062」メッセージが表示される。
            // ※メッセージ埋め込み文字：なし
            errRes.addErrorInfo("bmnCd", CCMessageConst.MSG_KEY_ITEM_INPUT_IS_ERROR);
            flag = false;
        } else if (!CCSICommon.TYPE_ACT_KBN_DELETED.equals(uriKbn)) {
            // 取得したレコード[URI_KBN]が(<>"9")の場合
            // 「S1213」メッセージが表示される。
            // ※メッセージ埋め込み文字：なし
            errRes.addErrorInfo("bmnCd", CCMessageConst.MSG_KEY_ENTERED_UTSUKAMATSU);
            flag = false;
        }

        // 18．入荷店チェック
        // M004tbumMapper.selectByExampleを使用
        // 画面項目[出荷日] ※""の場合は現在日付"yyyyMMdd"をセット
        // 画面項目[入荷店.会社 + 入荷店.事業部 + 入荷店.店舗]
        // 画面項目[入荷店.事業部] + 画面項目[入荷店.部門]
        uriKbn =
                getUriSiKubun(cond.getSubKaisyaCd() + cond.getSubJigyobuCd() + cond.getSubTenCd(),
                        cond.getSubBmnCd(), chkSyukaYmd);

        if (CCStringUtil.isEmpty(uriKbn)) {
            // 「C0062」メッセージが表示される。
            // ※メッセージ埋め込み文字：なし
            errRes.addErrorInfo("subBmnCd", CCMessageConst.MSG_KEY_ITEM_INPUT_IS_ERROR);
            flag = false;
        } else if (!CCSICommon.TYPE_ACT_KBN_DELETED.equals(uriKbn)) {
            // 取得したレコード[URI_KBN]が(<>"9")の場合
            // 「S1213」メッセージが表示される。
            // ※メッセージ埋め込み文字：なし
            errRes.addErrorInfo("subBmnCd", CCMessageConst.MSG_KEY_ENTERED_UTSUKAMATSU);
            flag = false;
        }

        // 19．部門 用度区分のチェック
        /* 出荷用度区分取得 */
        String shuYodoKbn = EMPTY;
        M003bmnmExample m003Example = new M003bmnmExample();
        m003Example.setSearchDate(chkSyukaYmd);
        // 部門コード = 画面項目[出荷店.事業部 + 出荷店.部門]
        m003Example.createCriteria().andBmnCdEqualTo(cond.getJigyobuCd() + cond.getBmnCd());

        List<M003bmnm> m003bmnms = m003bmnmMapper.selectByExample(m003Example);
        String hatpKbn = StringUtils.EMPTY;
        String subHatpKbn = StringUtils.EMPTY;
        // 取得したデータがない場合
        if (m003bmnms.size() == 0) {
            // 出荷用度区分に""をセット
            shuYodoKbn = EMPTY;
        } else {
            if (!CCSICommon.TYPE_ACT_KBN_DELETED.equals(m003bmnms.get(0).getActKbn())) {
                shuYodoKbn = m003bmnms.get(0).getYodoKbn();
                hatpKbn = m003bmnms.get(0).getHatpKbn();
            } else {
                shuYodoKbn = EMPTY;
            }
        }

        /* 入荷用度区分取得 */
        String nyuYodoKbn = EMPTY;
        m003Example = new M003bmnmExample();
        m003Example.setSearchDate(chkSyukaYmd);
        // 部門コード = 画面項目[入荷店.事業部] + 画面項目[入荷店.部門]
        m003Example.createCriteria().andBmnCdEqualTo(cond.getSubJigyobuCd() + cond.getSubBmnCd());

        m003bmnms = m003bmnmMapper.selectByExample(m003Example);

        if (m003bmnms.size() == 0) {
            // 入荷用度区分に""をセット
            nyuYodoKbn = EMPTY;
        } else {
            if (!CCSICommon.TYPE_ACT_KBN_DELETED.equals(m003bmnms.get(0).getActKbn())) {
                nyuYodoKbn = m003bmnms.get(0).getYodoKbn();
                subHatpKbn = m003bmnms.get(0).getHatpKbn();
            } else {
                nyuYodoKbn = EMPTY;
            }
        }

        if (!YODOKBN_1.equals(shuYodoKbn)) {
            shuYodoKbn = YODOKBN_9;
        }
        if (!YODOKBN_1.equals(nyuYodoKbn)) {
            nyuYodoKbn = YODOKBN_9;
        }
        // 出荷用度区分 <> 入荷用度区分がの場合
        if (!shuYodoKbn.equals(nyuYodoKbn)) {
            String errorId = EMPTY;
            if (YODOKBN_1.equals(shuYodoKbn)) {
                // 「S1202」メッセージが表示される。
                // ※メッセージ埋め込み文字：なし
                errorId = CCMessageConst.MSG_KEY_TWO_YODOKBN_NOT_VALID_1;
            }
            if (!YODOKBN_1.equals(shuYodoKbn)) {
                // 「S1203」メッセージが表示される。
                // ※メッセージ埋め込み文字：なし
                errorId = CCMessageConst.MSG_KEY_TWO_YODOKBN_NOT_VALID_2;
            }
            errRes.addErrorInfo("bmnCd", errorId);
            errRes.addErrorInfo("subBmnCd", errorId);
            flag = false;
        }
        
        // テナント部門をチェックする。
        if (KBN_VAL_M_TENANT_KBN_BMNMST_TENANT.equals(hatpKbn)) {
            errRes.addErrorInfo("bmnCd", CCMessageConst.MSG_KEY_TENANTBMN_NOT_IDOU);
            flag = false;
        }
        if (KBN_VAL_M_TENANT_KBN_BMNMST_TENANT.equals(subHatpKbn)) {
            errRes.addErrorInfo("subBmnCd", CCMessageConst.MSG_KEY_TENANTBMN_NOT_IDOU);
            flag = false;
        }
        
        // 20．チェックフラグを戻す。
        return flag;
    }

    /**
     * 売仕区分取得処理<BR>
     * 
     * @param sTenCd7 店舗コード（７桁）
     * @param sBmnCd 部門コード（３桁）
     * @param sHatDd 発行日
     * @return String 売仕区分
     **/
    private String getUriSiKubun(String sTenCd7, String sBmnCd, String sHatDd) {
        // M004tbumMapper.selectByExampleを使用
        M004tbumExample m004example = new M004tbumExample();
        m004example.setSearchDate(sHatDd);
        m004example.createCriteria().andTenCdEqualTo(sTenCd7).andBmnCdEqualTo(sTenCd7.substring(2, 4) + sBmnCd);

        List<M004tbum> list = m004tbumMapper.selectByExample(m004example);
        if (!list.isEmpty()) {
            if (!CCSICommon.TYPE_ACT_KBN_DELETED.equals(list.get(0).getActKbn())) {
                return list.get(0).getUriKbn();
            }
        }
        return EMPTY;
    }

    /**
     * 処理②：グリッドの表示データ取得処理<br>
     * 1．S003fridMapper.SelectByExampleを使用
     * 
     * @param dpyKbn 伝区
     * @param dpyNo 伝票NO
     * @param inTenpoCd 入荷店店舗コード
     * @param inBmnCd 入荷店部門コード
     * @param outTenpoCd 出荷店店舗コード
     * @param outBmnCd 出荷店部門コード
     * @return data
     */
    private List<S003frid> queryData(String dpyNo, String dpyKbn, String inTenpoCd, String inBmnCd,
            String outTenpoCd, String outBmnCd) {

        // 1．S003fridMapper.SelectByExampleを使用
        S003fridExample s003Example = new S003fridExample();
        s003Example.createCriteria().andDpyNoEqualTo(dpyNo).andDenKbnEqualTo(dpyKbn).andInTenpoCdEqualTo(inTenpoCd)
                .andInBmnCdEqualTo(inBmnCd).andOutTenpoCdEqualTo(outTenpoCd).andOutBmnCdEqualTo(outBmnCd);

        return s003fridMapper.selectByExample(s003Example);
    }

    /**
     * 処理③：商品情報データ取得処理
     * 
     * @param janCdDisp 商品コード
     * @param syukaYmd 出荷日
     * @return ShnNm 商品名（漢字）
     */
    private String getShnNm(String janCdDisp, String syukaYmd) {
        String shnNm = EMPTY;
        // cmJSICommon.cnvJanToJan13の戻る値をセット
        String janCd13 = cmJSICommon.cnvJanToJan13(janCdDisp);
        // M007kijmMapper.selectByExampleを使用
        M007kijmExample example = new M007kijmExample();
        example.setSearchDate(syukaYmd);
        example.createCriteria().andJanCdEqualTo(janCd13);

        List<M007kijm> result = m007kijmMapper.selectByExample(example);
        if (result.size() == 0) {
            return shnNm;
        } else {
            shnNm = result.get(0).getShnNm();
            return shnNm;
        }
    }

    /**
     * 
     * @param frid データベースレコード
     * @param inx index from 0 to 9
     * @return value of ShnCd at index
     */
    private String getShnCd(S003frid frid, int inx) {
        switch (inx) {
        case 0:
            return frid.getShnCd01();
        case 1:
            return frid.getShnCd02();
        case 2:
            return frid.getShnCd03();
        case 3:
            return frid.getShnCd04();
        case 4:
            return frid.getShnCd05();
        case 5:
            return frid.getShnCd06();
        case 6:
            return frid.getShnCd07();
        case 7:
            return frid.getShnCd08();
        case 8:
            return frid.getShnCd09();
        case 9:
            return frid.getShnCd10();
        default:
            return EMPTY;
        }
    }

    /**
     * 
     * @param frid データベースレコード
     * @param inx index from 0 to 9
     * @return value of InShnCd at index
     */
    private String getInShnCd(S003frid frid, int inx) {
        switch (inx) {
        case 0:
            return frid.getInShnCd01();
        case 1:
            return frid.getInShnCd02();
        case 2:
            return frid.getInShnCd03();
        case 3:
            return frid.getInShnCd04();
        case 4:
            return frid.getInShnCd05();
        case 5:
            return frid.getInShnCd06();
        case 6:
            return frid.getInShnCd07();
        case 7:
            return frid.getInShnCd08();
        case 8:
            return frid.getInShnCd09();
        case 9:
            return frid.getInShnCd10();
        default:
            return EMPTY;
        }
    }

    /**
     * 
     * @param dpyKbn 伝区
     * @param dpyNo 伝票NO
     * @param inTenpoCd 入荷店店舗コード
     * @param inBmnCd 入荷店部門コード
     * @param outTenpoCd 出荷店店舗コード
     * @param outBmnCd 出荷店部門コード
     * @return syoriStsKbn
     */
    private String getSyoriStsKbn(String dpyKbn, String dpyNo, String inTenpoCd, String inBmnCd, String outTenpoCd,
            String outBmnCd) {

        Map<String, String> params = new HashMap<String, String>();
        params.put("dpyNo", dpyNo);
        params.put("dpyKbn", dpyKbn);
        params.put("inTenpoCd", inTenpoCd);
        params.put("inBmnCd", inBmnCd);
        params.put("outTenpoCd", outTenpoCd);
        params.put("outBmnCd", outBmnCd);

        return dao.selectOne("sijp0040SelectS007frit02", params);
    }

    /**
     * 
     * @param frid データベースレコード
     * @param inx index from 0 to 9
     * @return value of Kenbarasu at index
     */
    private BigDecimal getKenbarasu(S003frid frid, int inx) {
        switch (inx) {
        case 0:
            return frid.getKenBaraSu01();
        case 1:
            return frid.getKenBaraSu02();
        case 2:
            return frid.getKenBaraSu03();
        case 3:
            return frid.getKenBaraSu04();
        case 4:
            return frid.getKenBaraSu05();
        case 5:
            return frid.getKenBaraSu06();
        case 6:
            return frid.getKenBaraSu07();
        case 7:
            return frid.getKenBaraSu08();
        case 8:
            return frid.getKenBaraSu09();
        case 9:
            return frid.getKenBaraSu10();
        default:
            return new BigDecimal(0);
        }
    }

    /**
     * 
     * @param frid データベースレコード
     * @param inx index from 0 to 9
     * @return value of KenGenk at index
     */
    private BigDecimal getKenGenk(S003frid frid, int inx) {
        switch (inx) {
        case 0:
            return frid.getKenGenk01();
        case 1:
            return frid.getKenGenk02();
        case 2:
            return frid.getKenGenk03();
        case 3:
            return frid.getKenGenk04();
        case 4:
            return frid.getKenGenk05();
        case 5:
            return frid.getKenGenk06();
        case 6:
            return frid.getKenGenk07();
        case 7:
            return frid.getKenGenk08();
        case 8:
            return frid.getKenGenk09();
        case 9:
            return frid.getKenGenk10();
        default:
            return new BigDecimal(0);
        }
    }

    /**
     * 
     * @param frid データベースレコード
     * @param inx index from 0 to 9
     * @return value of KenGenkKin at index
     */
    private Long getKenGenkKin(S003frid frid, int inx) {
        switch (inx) {
        case 0:
            return frid.getKenGenkKin01();
        case 1:
            return frid.getKenGenkKin02();
        case 2:
            return frid.getKenGenkKin03();
        case 3:
            return frid.getKenGenkKin04();
        case 4:
            return frid.getKenGenkKin05();
        case 5:
            return frid.getKenGenkKin06();
        case 6:
            return frid.getKenGenkKin07();
        case 7:
            return frid.getKenGenkKin08();
        case 8:
            return frid.getKenGenkKin09();
        case 9:
            return frid.getKenGenkKin10();
        default:
            return new Long(0);
        }
    }

    /**
     * 
     * @param frid データベースレコード
     * @param inx index from 0 to 9
     * @return value of KenBaik at index
     */
    private Integer getKenBaik(S003frid frid, int inx) {
        switch (inx) {
        case 0:
            return frid.getKenBaik01();
        case 1:
            return frid.getKenBaik02();
        case 2:
            return frid.getKenBaik03();
        case 3:
            return frid.getKenBaik04();
        case 4:
            return frid.getKenBaik05();
        case 5:
            return frid.getKenBaik06();
        case 6:
            return frid.getKenBaik07();
        case 7:
            return frid.getKenBaik08();
        case 8:
            return frid.getKenBaik09();
        case 9:
            return frid.getKenBaik10();
        default:
            return new Integer(0);
        }
    }

    /**
     * 
     * @param frid データベースレコード
     * @param inx index from 0 to 9
     * @return value of KenBaikKin at index
     */
    private Long getKenBaikKin(S003frid frid, int inx) {
        switch (inx) {
        case 0:
            return frid.getKenBaikKin01();
        case 1:
            return frid.getKenBaikKin02();
        case 2:
            return frid.getKenBaikKin03();
        case 3:
            return frid.getKenBaikKin04();
        case 4:
            return frid.getKenBaikKin05();
        case 5:
            return frid.getKenBaikKin06();
        case 6:
            return frid.getKenBaikKin07();
        case 7:
            return frid.getKenBaikKin08();
        case 8:
            return frid.getKenBaikKin09();
        case 9:
            return frid.getKenBaikKin10();
        default:
            return new Long(0);
        }
    }

    /**
     * Check null or empty string.
     * 
     * @param value String
     * @return true or falses
     */
    private boolean isEmpty(String value) {
        if (value == null) {
            return true;
        } else {
            String temp = CCStringUtil.trimBoth(value);
            return EMPTY.equals(temp);
        }
    }

    /**
     * 処理状態(日本語)取得処理.
     * <p>
     * 機能概要：<br>
     * 処理状態(日本語)を取得します。
     * <p>
     * 作成日：<br>
     * <p>
     * 
     * @param status なし
     * @return 処理状態(日本語)
     */
    private String getStatusNm(String status) {
        if (status.trim().length() == 0) {
            return EMPTY;
        }
        String sCd = CCSICommon.CN_SYORI_STS_KBN + status;
        String sStatusNm = cmJSICommon.getSirName(sCd);

        return sStatusNm;
    }
}
