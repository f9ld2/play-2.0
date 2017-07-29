///////////////////////////////////////////////////////////////////////
// Copyright(C) 2015 NEC Solution Innovators, Ltd. All Rights Reserved.
///////////////////////////////////////////////////////////////////////

/* ====================================================================
 * 機能名称 : ＥＯＳ伝票入力
 * 改版履歴
 * Rev. 改版年月日   改版者名       内容
 * 1.0   2014.04.07   Hungtb      新規作成
 * =================================================================== */
package jp.co.necsoft.web_md.core.app.controllers.si;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import jp.co.necsoft.web_md.common.ChaseException;
import jp.co.necsoft.web_md.common.biz.IPluConverter;
import jp.co.necsoft.web_md.common.mybatis.dao.MyBatisSqlMapDao;
import jp.co.necsoft.web_md.core.app.dto.si.Sijp0010Eosd;
import jp.co.necsoft.web_md.core.app.forms.si.Sijp0010CondForm;
import jp.co.necsoft.web_md.core.app.forms.si.Sijp0010ResultForm;
import jp.co.necsoft.web_md.core.common.CCMessageConst;
import jp.co.necsoft.web_md.core.common.CCSecurityAuthenticator;
import jp.co.necsoft.web_md.core.common.CCSystemContext;
import jp.co.necsoft.web_md.core.common.biz.si.CCSICommon;
import jp.co.necsoft.web_md.core.common.biz.si.CCSIConst;
import jp.co.necsoft.web_md.core.common.forms.ErrorInfo;
import jp.co.necsoft.web_md.core.common.forms.ErrorResponse;
import jp.co.necsoft.web_md.core.common.mybatis.dao.M007kijmMapper;
import jp.co.necsoft.web_md.core.common.mybatis.dao.S001eosdMapper;
import jp.co.necsoft.web_md.core.common.mybatis.dao.S003fridMapper;
import jp.co.necsoft.web_md.core.common.mybatis.dao.S005eostMapper;
import jp.co.necsoft.web_md.core.common.mybatis.dao.S007fritMapper;
import jp.co.necsoft.web_md.core.common.mybatis.dao.S014eomrMapper;
import jp.co.necsoft.web_md.core.common.mybatis.dao.S027eospMapper;
import jp.co.necsoft.web_md.core.common.mybatis.dao.S029fripMapper;
import jp.co.necsoft.web_md.core.common.mybatis.dao.S047eoshMapper;
import jp.co.necsoft.web_md.core.common.mybatis.dao.S048frihMapper;
import jp.co.necsoft.web_md.core.common.mybatis.dto.M007kijm;
import jp.co.necsoft.web_md.core.common.mybatis.dto.M007kijmExample;
import jp.co.necsoft.web_md.core.common.mybatis.dto.S001eosd;
import jp.co.necsoft.web_md.core.common.mybatis.dto.S001eosdExample;
import jp.co.necsoft.web_md.core.common.mybatis.dto.S003frid;
import jp.co.necsoft.web_md.core.common.mybatis.dto.S005eost;
import jp.co.necsoft.web_md.core.common.mybatis.dto.S007frit;
import jp.co.necsoft.web_md.core.common.mybatis.dto.S014eomr;
import jp.co.necsoft.web_md.core.common.mybatis.dto.S014eomrExample;
import jp.co.necsoft.web_md.core.common.mybatis.dto.S027eosp;
import jp.co.necsoft.web_md.core.common.mybatis.dto.S029frip;
import jp.co.necsoft.web_md.core.common.mybatis.dto.S047eosh;
import jp.co.necsoft.web_md.core.common.mybatis.dto.S047eoshExample;
import jp.co.necsoft.web_md.core.common.mybatis.dto.S048frih;
import jp.co.necsoft.web_md.core.common.mybatis.dto.S048frihExample;
import jp.co.necsoft.web_md.core.common.utils.CCDateUtil;
import jp.co.necsoft.web_md.core.common.utils.CCNumberUtil;
import jp.co.necsoft.web_md.core.common.utils.CCStringUtil;
import jp.co.necsoft.web_md.core.common.utils.DataUtil;

import org.mybatis.guice.transactional.Transactional;
import org.springframework.beans.BeanUtils;

import play.data.Form;
import play.i18n.Messages;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;

/**
 * ＥＯＳ伝票入力のControllerクラス
 *
 */
@Security.Authenticated(CCSecurityAuthenticator.class)
public class Sijp0010Ctrl extends Controller {
    /** Empty String */
    private static final String EMPTY = "";

    /** SIJP0110 params: {"dpyKbn", "kaisyaCd", "jigyobuCd", "dpyNo", "nhnYmd"} */
    private static final String[] KEY_PARAMS = {"dpyKbn", "kaisyaCd", "jigyobuCd", "dpyNo", "nhnYmd" };

    /** QUERY_DISPLAY = 0 */
    private static final int QUERY_DISPLAY = 0;

    /** QUERY_EDIT = 1 */
    private static final int QUERY_EDIT = 1;

    /** TORI_KBN_2 = "2" */
    private static final String TORI_KBN_2 = "2";

    /** SYORI_STS_9 = "9" */
    private static final String SYORI_STS_9 = "9";

    /** errRes: error response. */
    @Inject
    private ErrorResponse errRes;

    /** context */
    @Inject
    private CCSystemContext context;

    /** Common file. */
    @Inject
    private CCSICommon ccSICommon;

    /** dao: DAO for pure sql mapping. */
    @Inject
    private MyBatisSqlMapDao dao;

    /** Mapper table S001eosd */
    @Inject
    private S001eosdMapper s001eosdMapper;

    /** Mapper table S047eosh */
    @Inject
    private S047eoshMapper s047eoshMapper;

    /** Mapper table S048frih */
    @Inject
    private S048frihMapper s048frihMapper;

    /** Mapper table S005eostr */
    @Inject
    private S005eostMapper s005eostMapper;

    /** Mapper table S027eosp */
    @Inject
    private S027eospMapper s027eospMapper;

    /** Mapper table S014eomr */
    @Inject
    private S014eomrMapper s014eomrMapper;

    /** Mapper table S003frid */
    @Inject
    private S003fridMapper s003fridMapper;

    /** Mapper table S007frit */
    @Inject
    private S007fritMapper s007fritMapper;

    /** Mapper table S029frip */
    @Inject
    private S029fripMapper s029fripMapper;

    /** Mapper table M007kijm */
    @Inject
    private M007kijmMapper m007kijmMapper;
    
    /** IPlu Converter */
    @Inject
    private IPluConverter plucnv;

    /**
     * ページを初期化する
     * 
     * @return クライアントへ返却する結果
     */
    public Result init() {
        Sijp0010CondForm form = new Sijp0010CondForm();
        form.setDpyKbn(EMPTY);
        form.setKaisyaCd(EMPTY);
        form.setJigyobuCd(EMPTY);

        Map<String, String[]> query = request().queryString();
        if (query.size() >= KEY_PARAMS.length) {
            // 伝区
            if (query.containsKey(KEY_PARAMS[0])) {
                form.setDpyKbn(query.get(KEY_PARAMS[0])[0]);
            }
            // 会社
            if (query.containsKey(KEY_PARAMS[1])) {
                form.setKaisyaCd(query.get(KEY_PARAMS[1])[0]);
            }
            // 事業部
            if (query.containsKey(KEY_PARAMS[2])) {
                form.setJigyobuCd(query.get(KEY_PARAMS[2])[0]);
            }
            // 伝票№
            if (query.containsKey(KEY_PARAMS[3])) {
                form.setDpyNo(query.get(KEY_PARAMS[3])[0]);
            }
            // 納品日
            if (query.containsKey(KEY_PARAMS[4])) {
                String nhnYmd = query.get(KEY_PARAMS[4])[0];
                if (CCStringUtil.isEmpty(nhnYmd)) {
                    form.setNhnYmd(EMPTY);
                } else {
                    form.setNhnYmd(nhnYmd);
                }
            }
        }
        return ok(Json.toJson(form));
    }

    /**
     * 引数とリクエスト(パラメータ)を条件にDBからデータ(複数件)を検索し返却する。*
     * 
     * @param dpyNo 伝票NO
     * @return クライアントへ返却する結果
     */
    public Result query(String dpyNo) {
        Form<Sijp0010CondForm> emptyForm = new Form<Sijp0010CondForm>(Sijp0010CondForm.class);
        Form<Sijp0010CondForm> reqForm = emptyForm.bindFromRequest();
        if (reqForm.hasErrors()) {
            errRes.addErrorInfo(CCMessageConst.MSG_KEY_VALIDATION_ERROR);
            errRes.addErrorInfo(reqForm.errors());
            return badRequest(Json.toJson(errRes));
        } else {
            Sijp0010CondForm cond = reqForm.get();
            cond.setDpyNo(dpyNo);

            // setHead()
            if (cond.getDctcKbn() == null) {
                cond.setDctcKbn(EMPTY);
            }
            if (cond.getNhnYmd() == null) {
                cond.setNhnYmd(EMPTY);
            }
            if (cond.getNhnYoteiYmd() == null) {
                cond.setNhnYoteiYmd(EMPTY);
            }

            // 1．入力項目チェック処理：
            // 処理仕様(その他関数)シートの処理②を参照。
            int shoriKbn = cond.getSearchFlg() == QUERY_EDIT ? CCSICommon.CN_UPDATE : CCSICommon.CN_DISPLAY;
            boolean flag = checkCond(cond, shoriKbn);

            if (!flag) {
                ErrorInfo err = errRes.getErrorInfo(CCMessageConst.MSG_KEY_VALIDATION_ERROR);
                errRes.getErrors().add(0, err);
                // チェック結果はエラーの場合、検索・編集処理を終了する
                return badRequest(Json.toJson(errRes));
            }

            // 2．データ取得処理：
            // 「処理仕様(その他関数)」シートの[処理①：EOS伝票入力データ取得処理]を参照。
            // 1．処理状態区分取得：
            // Sijp0010MapperのselectS001eosd02を使用
            // 画面項目[伝票NO」をセット
            String syoriStsKbn = dao.selectOne("sijp0010SelectS001eosd02", cond.getDpyNo());
            // 取得データが存在しない場合
            if (CCStringUtil.isEmpty(syoriStsKbn)) {
                // 「C0018」メッセージが表示される。
                // 検索・編集処理を終了する
                errRes.addErrorInfo("dpyNo", CCMessageConst.MSG_KEY_SLIP_NOT_REGISTER);
                ErrorInfo err = errRes.getErrorInfo(CCMessageConst.MSG_KEY_VALIDATION_ERROR);
                errRes.getErrors().add(0, err);
                return badRequest(Json.toJson(errRes));
            }

            // 2．データ取得処理：
            // Sijp0010MapperのselectS001eosd01を使用
            // 処理状態区分
            // 伝票番号
            Map<String, String> params = new HashMap<String, String>();
            params.put("sStsKbn", syoriStsKbn);
            params.put("sDenno", cond.getDpyNo());

            S001eosd s001eosd = dao.selectOne("selectS001eosd01", params);
            // 取得データが存在しない場合
            if (s001eosd == null) {
                // 「C0018」メッセージが表示される。
                // 検索・編集処理を終了する
                errRes.addErrorInfo("dpyNo", CCMessageConst.MSG_KEY_SLIP_NOT_REGISTER);
                ErrorInfo err = errRes.getErrorInfo(CCMessageConst.MSG_KEY_VALIDATION_ERROR);
                errRes.getErrors().add(0, err);
                return badRequest(Json.toJson(errRes));
            }

            S047eoshExample s047eoshExample = new S047eoshExample();
            s047eoshExample.createCriteria().andDpyNoEqualTo(dpyNo);

            List<S047eosh> s047eoshList = s047eoshMapper.selectByExample(s047eoshExample);

            Sijp0010ResultForm result = new Sijp0010ResultForm();
            BeanUtils.copyProperties(s001eosd, result, new String[] {"dctcKbn", "nhnYmd" });
            // for input
            result.setDpyNo(cond.getDpyNo());
            result.setDctcKbn(cond.getDctcKbn());
            result.setNhnYmd(cond.getNhnYmd());
            // for header
            result.setSyoriStsKbnNm(getStatusNm(s001eosd.getSyoriStsKbn()));
            result.setJigyobuCd(s001eosd.getJigyoubuCd());
            result.setBmnCd(s001eosd.getBmnCd().substring(2, 5));
            result.setTriCd(s001eosd.getTorihikiCd());
            String toriNm = ccSICommon.getComToriName(s001eosd.getTorihikiCd(), s001eosd.getNhnYmd());
            result.setTriNm(toriNm);

            // 処理アクションタイプ ==「cmJSICommon.CN_DISPLAY」(参照)または「cmJSICommon.CN_MIKAKUTEI」(未確定)の場合：
            if (cond.getSearchFlg() == QUERY_DISPLAY || (CCSICommon.CN_MIKAKUTEI + EMPTY).equals(syoriStsKbn)) {
                result.setDctcKbn(CCStringUtil.trimBoth(s001eosd.getDctcKbn()));
                result.setNhnYmd(CCStringUtil.trimBoth(s001eosd.getNhnYmd()));
            }
            result.setNhnYoteiYmd(CCStringUtil.trimBoth(s001eosd.getNhnYoteiYmd()));
            result.setHatYmd(CCStringUtil.trimBoth(s001eosd.getHatYmd()));

            // 処理状態変数 == cmJSIConst.SYORISTS_KBN.KBN_MIKAKTEIの場合
            if (CCSIConst.SYORISTS_KBN.KBN_MIKAKTEI.equals(syoriStsKbn)) {
                result.setSumKenGenkKin(s001eosd.getsHatGenkKin());
                result.setSumKenBaikKin(s001eosd.getsHatBaikKin());
            } else {
                result.setSumKenGenkKin(s001eosd.getsKenGenkKin());
                result.setSumKenBaikKin(s001eosd.getsKenBaikKin());
            }
            // 合計原価税額変数 = 取得結果レコード[S_KEN_GENK_ZEI]
            result.setSumKenGenkZei(s001eosd.getsKenGenkZei());
            // 合計売価税額変数 = 取得結果レコード[S_KEN_BAIK_ZEI]
            result.setSumKenBaikZei(s001eosd.getsKenBaikZei());
            // 原価合計集計変数 = "0"
            Long iGenGokei = new Long(0);
            // 売価合計集計変数 = "0"
            Long iBaiGokei = new Long(0);

            List<Sijp0010Eosd> dataArea = new ArrayList<Sijp0010Eosd>();
            // 0から9までの[i]インデックスでループ処理：
            for (int i = 0; i < 10; i++) {
                Sijp0010Eosd row = new Sijp0010Eosd();

                String inx = ((i < 9) ? "0" : "") + (i + 1);
                row.setMesaiNo(String.valueOf(i + 1));
                // グリッド変数[i][SHNCD_COL] = cmJSICommonのcnvJan13ToDispの計算した結果
                String shnCd = (String) DataUtil.getValueByMethodName(s001eosd, "getShnCd" + inx);
                String janDisp = plucnv.toDispCode(shnCd, result.getNhnYmd());

                row.setShnCd(janDisp);
                // グリッド変数[i][HATTANI_COL] = 取得結果レコード[HATTYU_IRISU_01..10]
                BigDecimal hattyuIrisu =
                        (BigDecimal) DataUtil.getValueByMethodName(s001eosd, "getHattyuIrisu" + inx);
                row.setTani(hattyuIrisu.toPlainString());

                // グリッド変数[i][HATSU_COL] = 取得結果レコード[HAT_BARA_SU_01..10]
                BigDecimal hatBaraSu = (BigDecimal) DataUtil.getValueByMethodName(s001eosd, "getHatBaraSu" + inx);
                row.setHatBaraSu(hatBaraSu);
                // 処理状態変数 == [cmJSIConst.SYORISTS_KBN.KBN_MIKAKTEI]の場合
                if (CCSIConst.SYORISTS_KBN.KBN_MIKAKTEI.equals(syoriStsKbn)) {
                    // グリッド変数[i][NHNSU_COL] = 以下の値をセット：
                    // 取得結果レコード[HAT_BARA_SU_01..10]をセット
                    row.setKenBaraSu(hatBaraSu);

                    // ※原単価
                    BigDecimal hatGenk = (BigDecimal) DataUtil.getValueByMethodName(s001eosd, "getHatGenk" + inx);
                    row.setHatGenk(hatGenk);

                    // ※原価金額
                    Long hatGenkKin = (Long) DataUtil.getValueByMethodName(s001eosd, "getHatGenkKin" + inx);
                    row.setHatGenkKin(hatGenkKin);

                    // ※売単価
                    Integer hatBaik = (Integer) DataUtil.getValueByMethodName(s001eosd, "getHatBaik" + inx);
                    row.setHatBaik(hatBaik);

                    // ※売価金額
                    Long hatBaikKin = (Long) DataUtil.getValueByMethodName(s001eosd, "getHatBaikKin" + inx);
                    row.setHatBaikKin(hatBaikKin);
                } else {
                    // グリッド変数[i][NHNSU_COL] = 以下の値をセット：
                    // 取得結果レコード[KEN_BARA_SU_01..10]をセット
                    BigDecimal kenBaraSu =
                            (BigDecimal) DataUtil.getValueByMethodName(s001eosd, "getKenBaraSu" + inx);
                    row.setKenBaraSu(kenBaraSu);

                    // ※原単価
                    BigDecimal kenGenk = (BigDecimal) DataUtil.getValueByMethodName(s001eosd, "getKenGenk" + inx);
                    row.setHatGenk(kenGenk);

                    // ※原価金額
                    Long kenGenkKin = (Long) DataUtil.getValueByMethodName(s001eosd, "getKenGenkKin" + inx);
                    row.setHatGenkKin(kenGenkKin);

                    // ※売単価
                    Integer kenBaik = (Integer) DataUtil.getValueByMethodName(s001eosd, "getKenBaik" + inx);
                    row.setHatBaik(kenBaik);

                    // ※売価金額
                    Long kenBaikKin = (Long) DataUtil.getValueByMethodName(s001eosd, "getKenBaikKin" + inx);
                    row.setHatBaikKin(kenBaikKin);
                }

                row.setKppinRiyuKbn(s047eoshList.isEmpty() ? "" : (String) DataUtil.getValueByMethodName(
                        s047eoshList.get(0), "getKppinRiyuKbn" + inx));

                if (!CCStringUtil.isEmpty(shnCd)) {
                    row.setDisplayFlag(true);
                    row.setShnNm(getShnNm(shnCd, result.getNhnYmd()));
                    iGenGokei += row.getHatGenkKin().longValue();
                    iBaiGokei += row.getHatBaikKin().longValue();
                } else {
                    row.setDisplayFlag(false);
                    row.setShnNm(EMPTY);
                }

                dataArea.add(row);
            }

            result.setDataArea(dataArea);
            // 合計金額セット
            result.setSumKenGenkKin(iGenGokei);
            result.setSumKenBaikKin(iBaiGokei);

            // finish if only search, continue if search edit
            if (cond.getSearchFlg() == QUERY_DISPLAY) {
                List<Sijp0010ResultForm> resultList = new ArrayList<Sijp0010ResultForm>();
                resultList.add(result);
                return ok(Json.toJson(resultList));
            } else {
                // 3．「処理状態区分変数」== cmJSIConst.SYORISTS_KBN.KBN_MIKAKTEI の時: Wrong FD
                // 3-1．ログイン店舗、入力店舗チェック
                if (!getUpdChk(result.getNhnYmd(), result.getKaisyaCd(), result.getJigyobuCd(), result.getTenCd())) {
                    // 「S1058」メッセージが表示される。
                    // 検索・編集処理を終了する
                    errRes.addErrorInfo(CCMessageConst.MSG_KEY_VALIDATION_ERROR);
                    errRes.addErrorInfo("dpyNo", CCMessageConst.MSG_KEY_CANT_UPDATE_STORE);
                    return badRequest(Json.toJson(errRes));
                }

                // 処理状態
                boolean isForbidden = isForbidden(syoriStsKbn);
                if (isForbidden) {
                    return badRequest(Json.toJson(errRes));
                }

                if (CCSIConst.SYORISTS_KBN.KBN_MIKAKTEI.equals(result.getSyoriStsKbn())) {
                    // 3-4．納品予定日チェック：
                    // 画面項目[納品日]　>　運用日の場合
                    String nhnYoteiYmd = result.getNhnYoteiYmd();
                    if (!checkNhnYoteiYmd(nhnYoteiYmd)) {
                        // 「S1084」メッセージが表示される。
                        // ※メッセージ埋め込み文字：画面項目[納品日]
                        ArrayList<String> msgParams = new ArrayList<String>();
                        msgParams.add(CCDateUtil.getDateFormat(nhnYoteiYmd, CCDateUtil.DATETIME_FORMAT_DATE));
                        errRes.addErrorInfoWithParam("nhnYoteiYmd", CCMessageConst.MSG_KEY_DATE_OVER_UNYODATE,
                                msgParams);
                        return badRequest(Json.toJson(errRes));
                    }
                }

                // 3-2．総量発注区分チェック
                // 総量発注区分変数 == cmJSIConst.SOURYOU_KBN.KBN_1の場合
                if (CCSIConst.SOURYOU_KBN.KBN_1.equals(s001eosd.getIkatuFlg())) {
                    // 「S1201」メッセージが表示される。
                    errRes.addErrorInfo(CCMessageConst.MSG_KEY_VALIDATION_ERROR);
                    errRes.addErrorInfo("dpyNo", CCMessageConst.MSG_KEY_SORYO_HATSU_NOT_UPDATE);
                    return badRequest(Json.toJson(errRes));
                }

                // 5．[排他キーコード]
                // 画面項目[伝票NO] ※ 整形：右スペースパディング（9桁数）
                List<Sijp0010ResultForm> resultList = new ArrayList<Sijp0010ResultForm>();
                resultList.add(result);
                return ok(Json.toJson(resultList));
            }
        }
    }

    /**
     * 引数とリクエスト(ボディ)からレコードを編集しDBに登録(論理)する。*
     * 
     * @param dpyNo 伝票NO
     * @return クライアントへ返却する結果
     */
    public Result save(String dpyNo) {
        Form<Sijp0010ResultForm> emptyForm = new Form<Sijp0010ResultForm>(Sijp0010ResultForm.class);
        Form<Sijp0010ResultForm> reqForm = emptyForm.bindFromRequest();
        if (reqForm.hasErrors()) {
            errRes.addErrorInfo(CCMessageConst.MSG_KEY_VALIDATION_ERROR);
            errRes.addErrorInfo(reqForm.errors());
            return badRequest(Json.toJson(errRes));
        } else {
            Sijp0010ResultForm form = reqForm.get();
            form.setDpyNo(dpyNo);

            // Default set
            setCond(form);
            Sijp0010CondForm cond = createCondForm(form);

            // 1．データのエラーチェック処理
            // 1.1
            boolean flag = checkCond(cond, CCSICommon.CN_KAKUTEI);
            if (!flag) {
                ErrorInfo err = errRes.getErrorInfo(CCMessageConst.MSG_KEY_VALIDATION_ERROR);
                errRes.getErrors().add(0, err);
                // チェック結果はエラーの場合、検索・編集処理を終了する
                return badRequest(Json.toJson(errRes));
            }

            // ======================
            String syoriStsKbn = dao.selectOne("sijp0010SelectS001eosd02", cond.getDpyNo());
            if (CCStringUtil.isEmpty(syoriStsKbn)) {
                // 「C0018」メッセージが表示される。
                errRes.addErrorInfo("dpyNo", CCMessageConst.MSG_KEY_SLIP_NOT_REGISTER);
                ErrorInfo err = errRes.getErrorInfo(CCMessageConst.MSG_KEY_VALIDATION_ERROR);
                errRes.getErrors().add(0, err);
                return badRequest(Json.toJson(errRes));
            }

            if (CCSIConst.SYORISTS_KBN.KBN_KAKTEI.equals(syoriStsKbn)) {
                // S1004
                errRes.addErrorInfo(CCMessageConst.MSG_KEY_VOUCHER_HAS_BEEN_IDENTIFIED);
                return badRequest(Json.toJson(errRes));
            }

            boolean isForbidden = isForbidden(syoriStsKbn);
            if (isForbidden) {
                return badRequest(Json.toJson(errRes));
            }
            // ======================
            // 1.5:6:7
            flag = checkData(form, CCSICommon.CN_KAKUTEI);
            if (!flag) {
                ErrorInfo err = errRes.getErrorInfo(CCMessageConst.MSG_KEY_VALIDATION_ERROR);
                errRes.getErrors().add(0, err);
                return badRequest(Json.toJson(errRes));
            }

            // 1.2．ログイン店舗、入力店舗チェック
            if (!getUpdChk(form.getNhnYmd(), form.getKaisyaCd(), form.getJigyobuCd(), form.getTenCd())) {
                // 「S1058」メッセージが表示される。
                // 検索・編集処理を終了する
                errRes.addErrorInfo(CCMessageConst.MSG_KEY_VALIDATION_ERROR);
                errRes.addErrorInfo("dpyNo", CCMessageConst.MSG_KEY_CANT_UPDATE_STORE);
                return badRequest(Json.toJson(errRes));
            }

            // 総量発注区分変数 == cmJSIConst.SOURYOU_KBN.KBN_1の場合
            if (CCSIConst.SOURYOU_KBN.KBN_1.equals(form.getIkatuFlg())) {
                // 「S1201」メッセージが表示される。
                errRes.addErrorInfo(CCMessageConst.MSG_KEY_VALIDATION_ERROR);
                errRes.addErrorInfo("dpyNo", CCMessageConst.MSG_KEY_SORYO_HATSU_NOT_UPDATE);
                return badRequest(Json.toJson(errRes));
            }

            String nhnYoteiYmd = cond.getNhnYoteiYmd();
            if (!checkNhnYoteiYmd(nhnYoteiYmd)) {
                // 「S1084」メッセージが表示される。
                // ※メッセージ埋め込み文字：画面項目[納品日]
                ArrayList<String> msgParams = new ArrayList<String>();
                msgParams.add(CCDateUtil.getDateFormat(nhnYoteiYmd, CCDateUtil.DATETIME_FORMAT_DATE));
                errRes.addErrorInfoWithParam("nhnYoteiYmd", CCMessageConst.MSG_KEY_DATE_OVER_UNYODATE, msgParams);
                return badRequest(Json.toJson(errRes));
            }
            // 2．新規登録の場合、クライアントからサーバにデータをPOSTする。
            insertData(form);

            // 2-5．正常終了後
            return created();
        }
    }

    /**
     * 引数とリクエスト(ボディ)からレコードを編集しDBの該当データを更新(論理)する。*
     * 
     * @param dpyNo 伝票NO
     * @return クライアントへ返却する結果
     */
    public Result update(String dpyNo) {
        Form<Sijp0010ResultForm> emptyForm = new Form<Sijp0010ResultForm>(Sijp0010ResultForm.class);
        Form<Sijp0010ResultForm> reqForm = emptyForm.bindFromRequest();
        if (reqForm.hasErrors()) {
            errRes.addErrorInfo(CCMessageConst.MSG_KEY_VALIDATION_ERROR);
            errRes.addErrorInfo(reqForm.errors());
            return badRequest(Json.toJson(errRes));
        } else {
            Sijp0010ResultForm form = reqForm.get();
            form.setDpyNo(dpyNo);

            // Default set
            setCond(form);
            Sijp0010CondForm cond = createCondForm(form);

            // 1．データのエラーチェック処理
            // 1.1
            boolean flag = checkCond(cond, CCSICommon.CN_UPDATE);
            if (!flag) {
                // チェック結果はエラーの場合、検索・編集処理を終了する
                return badRequest(Json.toJson(errRes));
            }
            // ======================
            String syoriStsKbn = dao.selectOne("sijp0010SelectS001eosd02", cond.getDpyNo());
            if (CCStringUtil.isEmpty(syoriStsKbn)) {
                // 「C0018」メッセージが表示される。
                errRes.addErrorInfo("dpyNo", CCMessageConst.MSG_KEY_SLIP_NOT_REGISTER);
                ErrorInfo err = errRes.getErrorInfo(CCMessageConst.MSG_KEY_VALIDATION_ERROR);
                errRes.getErrors().add(0, err);
                return badRequest(Json.toJson(errRes));
            }

            if (CCSIConst.SYORISTS_KBN.KBN_MIKAKTEI.equals(syoriStsKbn)) {
                // S1019
                errRes.addErrorInfo(CCMessageConst.MSG_KEY_CANT_UPDATE_STORE_S1019);
                return badRequest(Json.toJson(errRes));
            }

            boolean isForbidden = isForbidden(syoriStsKbn);
            if (isForbidden) {
                return badRequest(Json.toJson(errRes));
            }

            flag = checkData(form, CCSICommon.CN_UPDATE);
            if (!flag) {
                ErrorInfo err = errRes.getErrorInfo(CCMessageConst.MSG_KEY_VALIDATION_ERROR);
                errRes.getErrors().add(0, err);
                return badRequest(Json.toJson(errRes));
            }

            // 1.2．ログイン店舗、入力店舗チェック
            if (!getUpdChk(form.getNhnYmd(), form.getKaisyaCd(), form.getJigyobuCd(), form.getTenCd())) {
                // 「S1058」メッセージが表示される。
                // 検索・編集処理を終了する
                errRes.addErrorInfo(CCMessageConst.MSG_KEY_VALIDATION_ERROR);
                errRes.addErrorInfo("dpyNo", CCMessageConst.MSG_KEY_CANT_UPDATE_STORE);
                return badRequest(Json.toJson(errRes));
            }

            // 総量発注区分変数 == cmJSIConst.SOURYOU_KBN.KBN_1の場合
            if (CCSIConst.SOURYOU_KBN.KBN_1.equals(form.getIkatuFlg())) {
                // 「S1201」メッセージが表示される。
                errRes.addErrorInfo(CCMessageConst.MSG_KEY_VALIDATION_ERROR);
                errRes.addErrorInfo("dpyNo", CCMessageConst.MSG_KEY_SORYO_HATSU_NOT_UPDATE);
                return badRequest(Json.toJson(errRes));
            }
            // 3．更新の場合、クライアントからサーバにデータをPUTする。
            updateData(form);

            // 3-10．正常終了後
            return ok();
        }
    }

    /**
     * 引数とリクエスト(パラメータ)を条件にDBの該当データを削除(論理)する*
     * 
     * @param dpyNo 伝票NO
     * @return クライアントへ返却する結果
     */
    public Result delete(String dpyNo) {
        Form<Sijp0010ResultForm> emptyForm = new Form<Sijp0010ResultForm>(Sijp0010ResultForm.class);
        Form<Sijp0010ResultForm> reqForm = emptyForm.bindFromRequest();
        if (reqForm.hasErrors()) {
            errRes.addErrorInfo(CCMessageConst.MSG_KEY_VALIDATION_ERROR);
            errRes.addErrorInfo(reqForm.errors());
            return badRequest(Json.toJson(errRes));
        } else {
            Sijp0010ResultForm form = reqForm.get();

            // Default set
            setCond(form);
            Sijp0010CondForm cond = createCondForm(form);

            // 1．データのエラーチェック処理
            boolean flag = checkCond(cond, CCSICommon.CN_MIKAKUTEI);
            if (!flag) {
                ErrorInfo err = errRes.getErrorInfo(CCMessageConst.MSG_KEY_VALIDATION_ERROR);
                errRes.getErrors().add(0, err);
                // チェック結果はエラーの場合、検索・編集処理を終了する
                return badRequest(Json.toJson(errRes));
            }

            String syoriStsKbn = dao.selectOne("sijp0010SelectS001eosd02", cond.getDpyNo());
            if (CCStringUtil.isEmpty(syoriStsKbn)) {
                // 「C0018」メッセージが表示される。
                errRes.addErrorInfo("dpyNo", CCMessageConst.MSG_KEY_SLIP_NOT_REGISTER);
                ErrorInfo err = errRes.getErrorInfo(CCMessageConst.MSG_KEY_VALIDATION_ERROR);
                errRes.getErrors().add(0, err);
                return badRequest(Json.toJson(errRes));
            }

            if (CCSIConst.SYORISTS_KBN.KBN_MIKAKTEI.equals(syoriStsKbn)) {
                // S1019
                errRes.addErrorInfo(CCMessageConst.MSG_KEY_CANT_UPDATE_STORE_S1019);
                return badRequest(Json.toJson(errRes));
            }

            boolean isForbidden = isForbidden(syoriStsKbn);
            if (isForbidden) {
                return badRequest(Json.toJson(errRes));
            }
            // ======================

            // 1．ログイン店舗、入力店舗チェック
            if (!getUpdChk(form.getNhnYmd(), form.getKaisyaCd(), form.getJigyobuCd(), form.getTenCd())) {
                // 「S1058」メッセージが表示される。
                // 検索・編集処理を終了する
                errRes.addErrorInfo(CCMessageConst.MSG_KEY_VALIDATION_ERROR);
                errRes.addErrorInfo("dpyNo", CCMessageConst.MSG_KEY_CANT_UPDATE_STORE);
                return badRequest(Json.toJson(errRes));
            }

            // 2-1．処理状態チェック
            String sSts = form.getSyoriStsKbn();
            if (!CCSIConst.SYORISTS_KBN.KBN_KAKTEI.equals(sSts)) {
                // 処理状態を確認して下さい。
                errRes.addErrorInfo(CCMessageConst.MSG_KEY_CHECK_PROCESS_DATA);
                return badRequest(Json.toJson(errRes));
            }

            deleteData(form);
            return ok();
        }
    }

    /**
     * Check 処理状態区分
     * 
     * @param syoriStsKbn 処理状態区分
     * @return true：入力チェックＯＫ、false：入力チェックＮＧ
     */
    private boolean isForbidden(String syoriStsKbn) {
        if (CCSIConst.SYORISTS_KBN.KBN_SHR_END.equals(syoriStsKbn)) {
            // S1021
            errRes.addErrorInfo(CCMessageConst.MSG_KEY_CAN_NOT_MODIFY);
            return true;
        } else if (CCSIConst.SYORISTS_KBN.KBN_KAK_END.equals(syoriStsKbn)
                || CCSIConst.SYORISTS_KBN.KBN_GETJI_END.equals(syoriStsKbn)) {
            // S1014
            errRes.addErrorInfo(CCMessageConst.MSG_KEY_CAN_NOT_MODIFY_DOCUMENT);
            return true;
        } else if (CCSIConst.SYORISTS_KBN.KBN_DELETE.equals(syoriStsKbn)) {
            // C0017
            errRes.addErrorInfo(CCMessageConst.MSG_KEY_SPEC_HAS_CANCEL);
            return true;
        }

        return false;
    }

    /**
     * Delete action
     * @param form 資料 エラー例外
     */
    @Transactional
    public void deleteData(Sijp0010ResultForm form) {
        String loginTen7 = ccSICommon.getDefaultTenpo(context.getTantoCode());

        // 3．履歴用伝票累積Ｆデータ取得処理：
        // S001eosdMapperのselectByExampleを使用する。
        S001eosdExample example = new S001eosdExample();
        example.createCriteria().andDpyNoEqualTo(form.getDpyNo());
        List<S001eosd> s001List = s001eosdMapper.selectByExample(example);
        S001eosd eosd = s001List.get(0);

        // 2-2．EOS伝票入力トラン内のデータ件数取得処理
        // 処理仕様(その他関数)シートの処理21を参照。
        // getZenData()
        int maxSeqno = getMaxSeqno(form.getDpyNo());
        if (maxSeqno <= 0) {
            // 2-3-1．伝票入力トラン(前日)登録：
            // 処理仕様(その他関数)シートの処理22を参照。
            // 固定値[cmJSICommon.CN_MIKAKUTEI]をセット
            // 固定値[cmJSICommon.CN_REC_ZEN]をセット
            S005eost s005Ins =
                    insertS005TrnZen(eosd, form, CCSICommon.CN_MIKAKUTEI, CCSICommon.CN_REC_ZEN, maxSeqno);
            int result = s005eostMapper.insertSelective(s005Ins);
            if (result <= 0) {
                throw new ChaseException(Messages.get(CCMessageConst.MSG_KEY_EXCEPTION_INSERT_NO_RECORD));
            }

            // 2-3-2．伝票入力Ｐトラン(前日)登録
            // 処理仕様(その他関数)シートの処理23を参照。
            // 固定値[cmJSICommon.CN_MIKAKUTEI]をセット
            // 固定値[cmJSICommon.CN_REC_ZEN]をセット
            S027eosp s027Ins = new S027eosp();
            BeanUtils.copyProperties(s005Ins, s027Ins);
            result = s027eospMapper.insertSelective(s027Ins);
            if (result <= 0) {
                throw new ChaseException(Messages.get(CCMessageConst.MSG_KEY_EXCEPTION_INSERT_NO_RECORD));
            }
        }

        // 2-4．ＥＯＳ伝票累積Ｆ更新
        // 処理仕様(その他関数)シートの処理24を参照。
        /* Note : SIBN0010.getUpdDelSQL */
        S001eosd s001UpdDel = updateDeleteS001(form);
        S001eosdExample s001UpdDelExample = new S001eosdExample();
        s001UpdDelExample.createCriteria().andDpyNoEqualTo(form.getDpyNo());

        // S001eosdMapperのupdateByExampleSelectiveを使用する
        int result = s001eosdMapper.updateByExampleSelective(s001UpdDel, s001UpdDelExample);
        if (result <= 0) {
            throw new ChaseException(Messages.get(CCMessageConst.MSG_KEY_EXCEPTION_UPDATE_NO_RECORD));
        }

        maxSeqno = getMaxSeqno(form.getDpyNo());
        // 2-5．ＥＯＳ伝票入力Ｔ
        // 処理仕様(その他関数)シートの処理⑨を参照。
        // 固定値[cmJSICommon.CN_MIKAKUTEI]をセット
        // 固定値[cmJSICommon.CN_REC_KON]をセット
        S005eost s005Ins = insertS005(form, eosd, CCSICommon.CN_MIKAKUTEI, CCSICommon.CN_REC_KON, maxSeqno);
        result = s005eostMapper.insertSelective(s005Ins);
        if (result <= 0) {
            throw new ChaseException(Messages.get(CCMessageConst.MSG_KEY_EXCEPTION_INSERT_NO_RECORD));
        }

        // 2-6．ＥＯＳ伝票入力Ｐ
        // 処理仕様(その他関数)シートの処理⑩を参照。
        S027eosp s027Ins = new S027eosp();
        BeanUtils.copyProperties(s005Ins, s027Ins);
        result = s027eospMapper.insertSelective(s027Ins);
        if (result <= 0) {
            throw new ChaseException(Messages.get(CCMessageConst.MSG_KEY_EXCEPTION_INSERT_NO_RECORD));
        }

        // 2-7．ＥＯＳ明細履歴Ｆ登録
        // 処理仕様(その他関数)シートの処理⑪を参照。
        // 固定値[cmJSICommon.CN_MIKAKUTEI]をセット
        S014eomr s014Ins = insertS014(form, eosd, loginTen7, CCSICommon.CN_MIKAKUTEI);
        result = s014eomrMapper.insertSelective(s014Ins);
        if (result <= 0) {
            throw new ChaseException(Messages.get(CCMessageConst.MSG_KEY_EXCEPTION_INSERT_NO_RECORD));
        }

        // 2-8．ＥＯＳ明細履歴Ｆ処理状態区分更新
        // 処理仕様(その他関数)シートの処理⑫を参照。
        // 固定値[cmJSICommon.CN_MIKAKUTEI]をセット
        S014eomr s014Upd = updateS014(form, CCSICommon.CN_MIKAKUTEI);
        S014eomrExample s014UpdExample = new S014eomrExample();
        s014UpdExample.createCriteria().andDpyNoEqualTo(form.getDpyNo());

        // S014eomrMapperのupdateByExampleSelectiveを使用する。
        result = s014eomrMapper.updateByExampleSelective(s014Upd, s014UpdExample);
        if (result <= 0) {
            throw new ChaseException(Messages.get(CCMessageConst.MSG_KEY_EXCEPTION_INSERT_NO_RECORD));
        }
    }

    /**
     * Update action
     * @param form 資料 エラー例外
     */
    @Transactional
    public void updateData(Sijp0010ResultForm form) {
        String loginTen7 = ccSICommon.getDefaultTenpo(context.getTantoCode());

        // 3-1．確定、更新用のパラメータ設定処理：処理仕様(その他関数)シートの処理⑥を呼び出し。
        setPreUpdParam(form);

        // 3-2．確定区分取得
        // 処理仕様(その他関数)シートの処理⑦を参照。
        String entryKbn = ccSICommon.getKakuteiKbn(loginTen7, form.getNhnYmd());
        form.setEntryKbn(entryKbn);

        // 3．履歴用伝票累積Ｆデータ取得処理：
        // S001eosdMapperのselectByExampleを使用する。
        // IMPORTANT: move out from step: 3-4-1; get old data before update
        S001eosdExample example = new S001eosdExample();
        example.createCriteria().andDpyNoEqualTo(form.getDpyNo());
        List<S001eosd> s001List = s001eosdMapper.selectByExample(example);
        S001eosd eosd = s001List.get(0);

        // 3-3．EOS伝票入力トラン内のデータ件数取得処理
        // 処理仕様(その他関数)シートの処理21を参照。
        // getZenData()
        int maxSeqno = getMaxSeqno(form.getDpyNo());
        if (maxSeqno <= 0) {

            // 3-4．(3-3)の取得結果がない「前日のトランデータが無い」場合：
            // 3-4-1．伝票入力トラン(前日)登録：
            // 処理仕様(その他関数)シートの処理22を参照。
            // 固定値[cmJSICommon.CN_UPDATE]をセット
            // 固定値[cmJSICommon.CN_REC_ZEN]をセット
            S005eost s005Ins = insertS005TrnZen(eosd, form, CCSICommon.CN_UPDATE, CCSICommon.CN_REC_ZEN, maxSeqno);
            int result = s005eostMapper.insertSelective(s005Ins);
            if (result <= 0) {
                throw new ChaseException(Messages.get(CCMessageConst.MSG_KEY_EXCEPTION_INSERT_NO_RECORD));
            }

            // 3-4-2．伝票入力Ｐトラン(前日)登録
            // 処理仕様(その他関数)シートの処理23を参照。
            // 固定値[cmJSICommon.CN_UPDATE]をセット
            // 固定値[cmJSICommon.CN_REC_ZEN]をセット
            S027eosp s027Ins = new S027eosp();
            BeanUtils.copyProperties(s005Ins, s027Ins);
            result = s027eospMapper.insertSelective(s027Ins);
            if (result <= 0) {
                throw new ChaseException(Messages.get(CCMessageConst.MSG_KEY_EXCEPTION_INSERT_NO_RECORD));
            }
        }

        // 3-5．ＥＯＳ伝票累積Ｆ更新
        // 処理仕様(その他関数)シートの処理⑧を参照。
        S001eosd s001Upd = updateS001eosd(form, loginTen7, CCSICommon.CN_UPDATE);
        S001eosdExample s001UpdExample = new S001eosdExample();
        s001UpdExample.createCriteria().andDpyNoEqualTo(form.getDpyNo());
        int result = s001eosdMapper.updateByExampleSelective(s001Upd, s001UpdExample);
        if (result <= 0) {
            throw new ChaseException(Messages.get(CCMessageConst.MSG_KEY_EXCEPTION_UPDATE_NO_RECORD));
        }

        // 1．EOS伝票入力トラン内のデータ件数取得処理
        // Sijp0010MapperのselectS005eost01を使用
        maxSeqno = getMaxSeqno(form.getDpyNo());
        // 3-6．ＥＯＳ伝票入力Ｔ
        // 処理仕様(その他関数)シートの処理⑨を参照。
        // 固定値[cmJSICommon.CN_UPDATE]をセット
        // 固定値[cmJSICommon.CN_REC_KON]をセット
        S005eost s005Ins = insertS005(form, eosd, CCSICommon.CN_UPDATE, CCSICommon.CN_REC_KON, maxSeqno);
        result = s005eostMapper.insertSelective(s005Ins);
        if (result <= 0) {
            throw new ChaseException(Messages.get(CCMessageConst.MSG_KEY_EXCEPTION_INSERT_NO_RECORD));
        }

        // 3-7．ＥＯＳ伝票入力Ｔ
        // 処理仕様(その他関数)シートの処理⑩を参照。
        // 固定値[cmJSICommon.CN_UPDATE]をセット
        // 固定値[cmJSICommon.CN_REC_KON]をセット
        S027eosp s027Ins = new S027eosp();
        BeanUtils.copyProperties(s005Ins, s027Ins);
        result = s027eospMapper.insertSelective(s027Ins);
        if (result <= 0) {
            throw new ChaseException(Messages.get(CCMessageConst.MSG_KEY_EXCEPTION_INSERT_NO_RECORD));
        }

        // 3-8．ＥＯＳ明細履歴Ｆ登録
        // 処理仕様(その他関数)シートの処理⑪を参照。
        S014eomr s014Ins = insertS014(form, eosd, loginTen7, CCSICommon.CN_UPDATE);
        result = s014eomrMapper.insertSelective(s014Ins);
        if (result <= 0) {
            throw new ChaseException(Messages.get(CCMessageConst.MSG_KEY_EXCEPTION_INSERT_NO_RECORD));
        }

        S047eoshExample s047eoshExample = new S047eoshExample();
        s047eoshExample.createCriteria().andDpyNoEqualTo(form.getDpyNo());

        List<S047eosh> s047eoshList = s047eoshMapper.selectByExample(s047eoshExample);

        if (s047eoshList.isEmpty()) {
            S047eosh s047eosh = this.setDataS047(form, true);
            s047eosh.setDpyNo(form.getDpyNo());
            result = s047eoshMapper.insertSelective(s047eosh);
            if (result <= 0) {
                throw new ChaseException(Messages.get(CCMessageConst.MSG_KEY_EXCEPTION_INSERT_NO_RECORD));
            }
        } else {
            S047eosh s047eosh = this.setDataS047(form, false);

            result = s047eoshMapper.updateByExampleSelective(s047eosh, s047eoshExample);
            if (result <= 0) {
                throw new ChaseException(Messages.get(CCMessageConst.MSG_KEY_EXCEPTION_UPDATE_NO_RECORD));
            }
        }
    }

    /**
     * Insert action
     * @param form 資料 エラー例外
     */
    @Transactional
    public void insertData(Sijp0010ResultForm form) {
        String loginTen7 = ccSICommon.getDefaultTenpo(context.getTantoCode());
        // 2-1．取引先区分取得処理
        // cmJSICommonのgetComToriKbnを使用：
        // 取引先チェック
        // ※ 整形：右スペースパディング（9桁数）
        String torihiki = getMaintori(form.getTriCd());
        // ※ 整形：右スペースパディング（8桁数）
        String nhnYmd = form.getNhnYmd();
        String toriKbn = ccSICommon.getComToriKbn(torihiki, nhnYmd);

        // ===
        // 加算区分
        // m_sKasanKbn = cmJSIConst.KASAN_KBN.KBN_SUKIN_A;
        form.setKasanKbn(CCSIConst.KASAN_KBN.KBN_SUKIN_A);
        form.setCenterCd(EMPTY);
        // ===

        // 3．履歴用伝票累積Ｆデータ取得処理：
        // S001eosdMapperのselectByExampleを使用する。
        // IMPORTANT: move out from step: 2-2-4; get old data before update
        S001eosdExample example = new S001eosdExample();
        example.createCriteria().andDpyNoEqualTo(form.getDpyNo());
        List<S001eosd> s001List = s001eosdMapper.selectByExample(example);
        S001eosd s001Old = s001List.get(0);
        // 3．履歴用伝票累積Ｆデータ取得処理： End

        // 2-2．取引先区分変数 <> "2"場合：
        if (!TORI_KBN_2.equals(toriKbn)) {
            // 2-2-1．確定、更新用のパラメータ設定処理：処理仕様(その他関数)シートの処理⑥を呼び出し。
            setPreUpdParam(form);

            // 2-2-2．確定区分取得
            String entryKbn = ccSICommon.getKakuteiKbn(loginTen7, nhnYmd);
            form.setEntryKbn(entryKbn);

            // 2-2-3．ＥＯＳ伝票累積Ｆ更新
            // 処理仕様(その他関数)シートの処理⑧を参照。
            S001eosd s001Upd = updateS001eosd(form, loginTen7, CCSICommon.CN_INSERT);
            // 3．S001eosdMapperのupdateByExampleSelectiveを使用する。
            S001eosdExample s001Example = new S001eosdExample();
            s001Example.createCriteria().andDpyNoEqualTo(form.getDpyNo());

            int result = s001eosdMapper.updateByExampleSelective(s001Upd, s001Example);
            if (result <= 0) {
                throw new ChaseException(Messages.get(CCMessageConst.MSG_KEY_EXCEPTION_UPDATE_NO_RECORD));
            }

            int maxSeqno = getMaxSeqno(form.getDpyNo());
            // 2-2-4．ＥＯＳ伝票入力Ｔ登録
            // 処理仕様(その他関数)シートの処理⑨を参照。
            // 固定値[cmJSICommon.CN_KAKUTEI]をセット
            // 固定値["cmJSICommon.CN_REC_KON"]をセット
            S005eost s005Ins = insertS005(form, s001Old, CCSICommon.CN_KAKUTEI, CCSICommon.CN_REC_KON, maxSeqno);
            result = s005eostMapper.insertSelective(s005Ins);
            if (result <= 0) {
                throw new ChaseException(Messages.get(CCMessageConst.MSG_KEY_EXCEPTION_INSERT_NO_RECORD));
            }

            S027eosp s027Ins = new S027eosp();
            BeanUtils.copyProperties(s005Ins, s027Ins);
            result = s027eospMapper.insertSelective(s027Ins);
            if (result <= 0) {
                throw new ChaseException(Messages.get(CCMessageConst.MSG_KEY_EXCEPTION_INSERT_NO_RECORD));
            }
            // 2-2-5．ＥＯＳ明細履歴Ｆ
            // 2-2-5-1．明細履歴内のデータ件数取得処理：
            // Sijp0010MapperのselectS014eomr01を使用する。
            List<String> s014DpyNoList = dao.selectMany("sijp0010SelectS014eomr01", form.getDpyNo(), String.class);

            if (s014DpyNoList.size() > 0) {
                // 2-2-5-2．(1-2-5-1)で取得結果がある場合：
                // 処理仕様(その他関数)シートの処理⑪を参照。
                // 固定値[cmJSICommon.CN_KAKUTEI]をセット
                S014eomr s014 = insertS014(form, s001Old, loginTen7, CCSICommon.CN_KAKUTEI);
                result = s014eomrMapper.insertSelective(s014);
                if (result <= 0) {
                    throw new ChaseException(Messages.get(CCMessageConst.MSG_KEY_EXCEPTION_INSERT_NO_RECORD));
                }

                // ＥＯＳ明細履歴Ｆ処理状態区分更新
                // 処理仕様(その他関数)シートの処理⑫を参照。
                // 固定値[cmJSICommon.CN_KAKUTEI]をセット
                S014eomr s014Upd = updateS014(form, CCSICommon.CN_KAKUTEI);
                S014eomrExample s014example = new S014eomrExample();
                s014example.createCriteria().andDpyNoEqualTo(form.getDpyNo());

                // S014eomrMapperのupdateByExampleSelectiveを使用する。
                result = s014eomrMapper.updateByExampleSelective(s014Upd, s014example);
                if (result <= 0) {
                    throw new ChaseException(Messages.get(CCMessageConst.MSG_KEY_EXCEPTION_UPDATE_NO_RECORD));
                }
            }

            S047eoshExample s047eoshExample = new S047eoshExample();
            s047eoshExample.createCriteria().andDpyNoEqualTo(form.getDpyNo());

            List<S047eosh> s047eoshList = s047eoshMapper.selectByExample(s047eoshExample);

            if (s047eoshList.isEmpty()) {
                S047eosh s047eosh = this.setDataS047(form, true);
                s047eosh.setDpyNo(form.getDpyNo());
                result = s047eoshMapper.insertSelective(s047eosh);
                if (result <= 0) {
                    throw new ChaseException(Messages.get(CCMessageConst.MSG_KEY_EXCEPTION_INSERT_NO_RECORD));
                }
            } else {
                S047eosh s047eosh = this.setDataS047(form, false);

                result = s047eoshMapper.updateByExampleSelective(s047eosh, s047eoshExample);
                if (result <= 0) {
                    throw new ChaseException(Messages.get(CCMessageConst.MSG_KEY_EXCEPTION_UPDATE_NO_RECORD));
                }
            }
        } else {

            // 2-3-1．ＥＯＳ伝票累積削除
            // 処理仕様(その他関数)シートの処理⑬を参照。
            S001eosd s001Del = deleteS001(form);
            S001eosdExample s001DelExample = new S001eosdExample();
            s001DelExample.createCriteria().andDpyNoEqualTo(form.getDpyNo());

            // S001eosdMapperのupdateByExampleSelectiveを使用する。
            int result = s001eosdMapper.updateByExampleSelective(s001Del, s001DelExample);
            if (result <= 0) {
                throw new ChaseException(Messages.get(CCMessageConst.MSG_KEY_EXCEPTION_INSERT_NO_RECORD));
            }

            // 1．EOS伝票入力トラン内のデータ件数取得処理
            // 処理仕様(その他関数)シートの処理21を参照。
            int maxSeqNo = getMaxSeqno(form.getDpyNo());
            // 2-3-2．ＥＯＳ伝票入力トラン作成
            // 処理仕様(その他関数)シートの処理⑭を参照。
            S005eost s005Ins = insertS005FromS001(s001Old, form, maxSeqNo);
            result = s005eostMapper.insertSelective(s005Ins);
            if (result <= 0) {
                throw new ChaseException(Messages.get(CCMessageConst.MSG_KEY_EXCEPTION_INSERT_NO_RECORD));
            }

            // 2-3-4．ＥＯＳ伝票入力Ｐトラン作成
            // 処理仕様(その他関数)シートの処理⑮を参照。
            S027eosp s027Ins = new S027eosp();
            BeanUtils.copyProperties(s005Ins, s027Ins);
            result = s027eospMapper.insertSelective(s027Ins);
            if (result <= 0) {
                throw new ChaseException(Messages.get(CCMessageConst.MSG_KEY_EXCEPTION_INSERT_NO_RECORD));
            }

            // 2-3-5．ＥＯＳ明細履歴作成
            // 処理仕様(その他関数)シートの処理⑯を参照。
            S014eomr s014Ins = insertS014FromS001(s001Old, form);
            result = s014eomrMapper.insertSelective(s014Ins);
            if (result <= 0) {
                throw new ChaseException(Messages.get(CCMessageConst.MSG_KEY_EXCEPTION_INSERT_NO_RECORD));
            }

            // 2-3-6．移動伝票用の伝票№を取得
            // 処理仕様(その他関数)シートの処理⑰を参照。
            // 移動伝票用の伝票№を取得
            String sDenno = ccSICommon.getDenpyoNo("31", form.getKaisyaCd(), "");
            if (CCStringUtil.isEmpty(sDenno)) {
                sDenno = EMPTY;
            } else {
                sDenno = sDenno.substring(1, 10);
            }
            // 2-3-7．移動伝票作成
            // 処理仕様(その他関数)シートの処理⑱を参照。
            S003frid s003Ins = insertS003FromS001(s001Old, form, sDenno);
            result = s003fridMapper.insertSelective(s003Ins);
            if (result <= 0) {
                throw new ChaseException(Messages.get(CCMessageConst.MSG_KEY_EXCEPTION_INSERT_NO_RECORD));
            }

            // 2-3-8．移動伝票入力トラン作成
            // 処理仕様(その他関数)シートの処理⑲を参照。
            S007frit s007Ins = insertS007FromS001(s001Old, form, sDenno);
            result = s007fritMapper.insertSelective(s007Ins);
            if (result <= 0) {
                throw new ChaseException(Messages.get(CCMessageConst.MSG_KEY_EXCEPTION_INSERT_NO_RECORD));
            }

            // 2-3-9．移動伝票入力Ｐトラン作成
            // 処理仕様(その他関数)シートの処理⑳を参照。
            S029frip s029Ins = new S029frip();
            BeanUtils.copyProperties(s007Ins, s029Ins);
            result = s029fripMapper.insertSelective(s029Ins);
            if (result <= 0) {
                throw new ChaseException(Messages.get(CCMessageConst.MSG_KEY_EXCEPTION_INSERT_NO_RECORD));
            }

            S048frihExample s048frihExample = new S048frihExample();
            s048frihExample.createCriteria().andDpyNoEqualTo(form.getDpyNo());

            List<S048frih> s048frihList = s048frihMapper.selectByExample(s048frihExample);

            if (s048frihList.isEmpty()) {
                S048frih s048frih = this.setDataS048(form, true);
                s048frih.setDpyNo(form.getDpyNo());
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
    }

    /**
     * @param form form
     * @return S047eosh
     */
    private S047eosh setDataS047(Sijp0010ResultForm form, boolean isInsert) {
        S047eosh s047eosh = new S047eosh();

        List<Sijp0010Eosd> dataArea = form.getDataArea();
        for (int i = 0; i < 10; i++) {
            String inx = ((i < 9) ? "0" : "") + (i + 1);

            String value =
                    CCStringUtil.isEmpty(dataArea.get(i).getKppinRiyuKbn()) ? " " : dataArea.get(i)
                            .getKppinRiyuKbn();
            DataUtil.setValueByMethodName(s047eosh, "setKppinRiyuKbn" + inx, value);

            Short messaiNo =
                    CCStringUtil.isEmpty(dataArea.get(i).getMesaiNo()) ? (short) 0 : Short.parseShort(dataArea
                            .get(i).getMesaiNo());
            DataUtil.setValueByMethodName(s047eosh, "setMesaiNo" + inx, messaiNo);
        }

        String cmnDd = CCDateUtil.getSysDateFormat("yyyyMMdd");
        String cmnUpdtime = CCDateUtil.getSysDateFormat("HHmmss");
        if (isInsert) {
            s047eosh.setCmnInsdd(cmnDd);
        }
        s047eosh.setCmnUpddd(cmnDd);
        s047eosh.setCmnUpdtime(cmnUpdtime);
        s047eosh.setCmnTantoCd(this.context.getTantoCode());
        s047eosh.setCmnTermId(this.context.getTermId());

        return s047eosh;
    }

    /**
     * @param form form
     * @return S048frih
     */
    private S048frih setDataS048(Sijp0010ResultForm form, boolean isInsert) {
        S048frih s048frih = new S048frih();

        List<Sijp0010Eosd> dataArea = form.getDataArea();
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
     * Update in case of delete S001EOSD table
     * @param form 資料
     * @return データベースレコード
     */
    private S001eosd updateDeleteS001(Sijp0010ResultForm form) {
        BigDecimal bdZero = new BigDecimal(0);
        Integer itZero = new Integer(0);
        Long lgZero = new Long(0);

        S001eosd s001 = new S001eosd();

        s001.setNhnYmd(EMPTY);
        s001.setRuiKeijoYmd(EMPTY);
        s001.setLastKakuteiYmd(EMPTY);
        s001.setLastTantoCd(EMPTY);
        s001.setLastTermId(EMPTY);
        s001.setEntryKbn(EMPTY);
        s001.setEntryPlace(EMPTY);
        s001.setSirGetujiYm(EMPTY);
        s001.setSyoriStsKbn(CCSIConst.SYORISTS_KBN.KBN_MIKAKTEI);
        s001.setsKenGenkKin(lgZero);
        s001.setsKenBaikKin(lgZero);
        s001.setsKenGenkZei(lgZero);
        s001.setsKenBaikZei(lgZero);
        if (CCSIConst.DEN_KBN.KBN_EOSSIR.equals(form.getDpyKbn())) {
            s001.setMsaiSu(new Short((short) 0));
        }
        for (int i = 0; i < 10; i++) {
            String inx = ((i < 9) ? "0" : "") + (i + 1);
            DataUtil.setValueByMethodName(s001, "setKenBaraSu" + inx, bdZero);
            DataUtil.setValueByMethodName(s001, "setKenGenk" + inx, bdZero);
            DataUtil.setValueByMethodName(s001, "setKenBaik" + inx, itZero);
            DataUtil.setValueByMethodName(s001, "setKenGenkKin" + inx, lgZero);
            DataUtil.setValueByMethodName(s001, "setKenBaikKin" + inx, lgZero);
            DataUtil.setValueByMethodName(s001, "setKenGenkZei" + inx, itZero);
            DataUtil.setValueByMethodName(s001, "setKenBaikZei" + inx, itZero);
        }
        s001.setCmnUpddd(CCDateUtil.getSysDateFormat(CCDateUtil.DATETIME_FORMAT_DATE_YYYYMMDD));
        s001.setCmnUpdtime(CCDateUtil.getSysDateFormat(CCDateUtil.DATETIME_FORMAT_TIME));

        return s001;
    }

    /**
     * Prepare data for S005EOST table
     * @param s001 データベースレコード
     * @param form 資料
     * @param shoriKbn 処理状態区分
     * @param koshinKbn 更新区分
     * @param maxSeqNo SEQNO変数
     * 
     * @return データベースレコード
     */
    private S005eost insertS005TrnZen(S001eosd s001, Sijp0010ResultForm form, int shoriKbn, String koshinKbn,
            int maxSeqNo) {
        S005eost s005 = new S005eost();

        setInfoS005eost(s005, form, shoriKbn, koshinKbn, maxSeqNo);
        // String getZenInfo()
        setZenInfoS005(s005, s001);

        // getCommon()
        setCommon(s005);

        return s005;
    }

    /**
     * 伝票累積Insert SQL文作成処理
     * <p>
     * 機能概要：<br>
     * 伝票累積更新に使用するSQL文を作成します。<br>
     * 
     * @param s005 データベースレコード
     */
    private void setCommon(S005eost s005) {
        s005.setCmnTantoCd(context.getTantoCode());
        String cmnTermName = context.getTermName();
        if (cmnTermName.length() > 8) {
            cmnTermName = cmnTermName.substring(0, 8);
        }
        s005.setCmnTermId(cmnTermName);
        s005.setCmnInsdd(CCDateUtil.getSysDateFormat(CCDateUtil.DATETIME_FORMAT_DATE_YYYYMMDD));
        s005.setCmnUpddd(s005.getCmnInsdd());
        s005.setCmnUpdtime(CCDateUtil.getSysDateFormat(CCDateUtil.DATETIME_FORMAT_TIME));
    }

    /**
     * Copy data from s001 to s005.
     * 
     * @param s005 データベースレコード
     * @param s001 データベースレコード
     */
    private void setZenInfoS005(S005eost s005, S001eosd s001) {
        String[] ignoreProperties =
                {"dpyNo", "dpyKbn", "kaisyaCd", "jigyoubuCd", "cmnTantoCd", "cmnTermId", "cmnInsdd", "cmnUpddd",
                        "cmnUpdtime" };
        BeanUtils.copyProperties(s001, s005, ignoreProperties);
    }

    /**
     * 処理⑲：移動伝票入力トラン作成
     * 
     * @param s001 データベースレコード
     * @param form 資料
     * @param sDenno 伝票№ エラー例外
     * @return データベースレコード
     */
    private S007frit insertS007FromS001(S001eosd s001, Sijp0010ResultForm form, String sDenno) {
        // 共通情報取得
        String insDD = CCDateUtil.getSysDateFormat(CCDateUtil.DATETIME_FORMAT_DATE_YYYYMMDD);
        String insTime = CCDateUtil.getSysDateFormat(CCDateUtil.DATETIME_FORMAT_TIME);
        String undKbn = "I";

        // 伝票№
        String denno = CCStringUtil.isEmpty(sDenno) ? EMPTY : sDenno;

        // 出荷日（＝納品予定日）
        String shkDate = form.getNhnYmd();

        // 店
        String tenIn = form.getKaisyaCd() + form.getJigyobuCd() + form.getTenCd();
        String tenOut = ccSICommon.getComHatTenCd(form.getTriCd(), shkDate);
        if (tenOut.trim().length() == 0) {
            tenOut = ccSICommon.getComHatTenCd(getMaintori(form.getTriCd()), shkDate);
        }

        tenIn = CCStringUtil.isEmpty(tenIn) ? EMPTY : tenIn;
        tenOut = CCStringUtil.isEmpty(tenOut) ? EMPTY : tenOut.substring(0, 7);

        // 部門
        String bmnIn = form.getJigyobuCd() + form.getBmnCd();
//        String bmnOut = "90" + form.getBmnCd();
        String bmnOut = form.getJigyobuCd() + form.getBmnCd();
        bmnIn = CCStringUtil.isEmpty(bmnIn) ? EMPTY : bmnIn;
        bmnOut = CCStringUtil.isEmpty(bmnOut) ? EMPTY : bmnOut;

        // SEQ№
        // 3．S007FRITテーブルのSEQ№取得
        // Sijp0010MapperのselectS007frit01を使用する。
        Map<String, String> params = new HashMap<String, String>();
        params.put("dpyNo", denno);
        params.put("dpyKbn", CCSIConst.DEN_KBN.KBN_IDO);
        params.put("inTenpoCd", tenIn);
        params.put("inBmnCd", bmnIn);
        params.put("outTenpoCd", tenOut);
        params.put("outBmnCd", bmnOut);
        params.put("syukaYmd", shkDate);
        Integer maxSeqNo = dao.selectOne("sijp0010SelectS007frit01", params);

        if (maxSeqNo == null) {
            maxSeqNo = 0;
        }
        Short seqAto = new Short((short) (maxSeqNo + 1));
        // 累計計上日
        String keijoDate = ccSICommon.getKeijoDate(shkDate);

        // 確定情報
        String cmnInsdd = CCDateUtil.getSysDateFormat(CCDateUtil.DATETIME_FORMAT_DATE_YYYYMMDD);
        String cmnInsTime = CCDateUtil.getSysDateFormat(CCDateUtil.DATETIME_FORMAT_TIME);
        String cmnTantoCd = context.getTantoCode();
        String cmnTermName = context.getTermName();
        if (cmnTermName.length() > 8) {
            cmnTermName = cmnTermName.substring(0, 8);
        }
        String cmnTermId = cmnTermName;

        // 確定区分、確定場所
        String kakPlc = ccSICommon.getDefaultTenpo(context.getTantoCode());

        // 仕入月次月
        String getuji = keijoDate.substring(0, 6);
        // 処理状態区分
        String stsKbn = CCSIConst.SYORISTS_KBN.KBN_KAKTEI;
        String atukaiKbn = ccSICommon.getFAKubun(tenIn, bmnIn, shkDate);

        // 合計情報取得
        Long inpGenAll = form.getSumKenGenkKin();
        Long inpBaiAll = form.getSumKenBaikKin();

        // 入荷店
        String tenNmAIn = ccSICommon.getComTenpoNameA(tenIn, shkDate);
        if (tenNmAIn.trim().length() > 20) {
            tenNmAIn = tenNmAIn.substring(0, 20);
        }
        // 出荷店
        String tenNmAOut = ccSICommon.getComTenpoNameA(tenOut, shkDate);
        if (tenNmAOut.trim().length() > 20) {
            tenNmAOut = tenNmAOut.substring(0, 20);
        }

        S007frit s007 = new S007frit();

        s007.setInsdd(insDD);
        s007.setSeqno(seqAto);
        s007.setInstime(insTime);
        s007.setUpdateKbn(undKbn);

        s007.setDpyNo(denno);
        s007.setDenKbn(CCSIConst.DEN_KBN.KBN_IDO);
        s007.setInTenpoCd(tenIn);
        s007.setInBmnCd(bmnIn);
        s007.setOutTenpoCd(tenOut);
        s007.setOutBmnCd(bmnOut);
        s007.setRuiFlg("1");

        s007.setSyukaYmd(shkDate);
        s007.setHatYmd(shkDate);
        s007.setNhnYoteiYmd(shkDate);
        s007.setRuiKeijoYmd(keijoDate);
        s007.setFirstKakuteiYmd(cmnInsdd);
        s007.setFirstTantoCd(cmnTantoCd);
        s007.setFirstTermId(cmnTermId);
        s007.setLastKakuteiYmd(cmnInsdd);
        s007.setLastTantoCd(cmnTantoCd);
        s007.setLastTermId(cmnTermId);
        s007.setEntryPlace(kakPlc);
        s007.setDctcKbn(form.getDctcKbn());
        s007.setSirGetujiYm(getuji);
        s007.setSyoriStsKbn(stsKbn);
        s007.setMotoDpyNo(form.getDpyNo());
        s007.setAtukaiKbn(atukaiKbn);
        s007.setsHatGenkKin(inpGenAll);
        s007.setsHatBaikKin(inpBaiAll);
        s007.setsKenGenkKin(inpGenAll);
        s007.setsKenBaikKin(inpBaiAll);

        // 明細
        s007.setMsaiSu(s001.getMsaiSu());
        for (int i = 0; i < form.getDataArea().size(); i++) {
            Sijp0010Eosd row = form.getDataArea().get(i);
            String inx = ((i < 9) ? "0" : "") + (i + 1);
            DataUtil.setValueByMethodName(s007, "setMesaiNo" + inx,
                    DataUtil.getValueByMethodName(s001, "getMesaiNo" + inx));
            String shnCd = (String) DataUtil.getValueByMethodName(s001, "getShnCd" + inx);
            String mkrCd = getMkrCd(shnCd, shkDate);
            DataUtil.setValueByMethodName(s007, "setMkrCd" + inx, mkrCd);
            DataUtil.setValueByMethodName(s007, "setShnCd" + inx, shnCd);
            DataUtil.setValueByMethodName(s007, "setPluKbn" + inx,
                    DataUtil.getValueByMethodName(s001, "getPluKbn" + inx));
            DataUtil.setValueByMethodName(s007, "setCbnrCd" + inx,
                    DataUtil.getValueByMethodName(s001, "getCbnrCd" + inx));
            DataUtil.setValueByMethodName(s007, "setSbnrCd" + inx,
                    DataUtil.getValueByMethodName(s001, "getSbnrCd" + inx));
            DataUtil.setValueByMethodName(s007, "setInShnCd" + inx, shnCd);
            DataUtil.setValueByMethodName(s007, "setInPluKbn" + inx,
                    DataUtil.getValueByMethodName(s001, "getPluKbn" + inx));
            DataUtil.setValueByMethodName(s007, "setInCbnrCd" + inx,
                    DataUtil.getValueByMethodName(s001, "getCbnrCd" + inx));
            DataUtil.setValueByMethodName(s007, "setInSbnrCd" + inx,
                    DataUtil.getValueByMethodName(s001, "getSbnrCd" + inx));

            DataUtil.setValueByMethodName(s007, "setKikakuSu" + inx,
                    DataUtil.getValueByMethodName(s001, "getKikakuSu" + inx));
            DataUtil.setValueByMethodName(s007, "setKikakuTani" + inx,
                    DataUtil.getValueByMethodName(s001, "getKikakuTani" + inx));
            DataUtil.setValueByMethodName(s007, "setIriSu" + inx,
                    DataUtil.getValueByMethodName(s001, "getIriSu" + inx));
            DataUtil.setValueByMethodName(s007, "setHattyuIrisu" + inx,
                    DataUtil.getValueByMethodName(s001, "getHattyuIrisu" + inx));
            DataUtil.setValueByMethodName(s007, "setTani" + inx,
                    DataUtil.getValueByMethodName(s001, "getTani" + inx));
            DataUtil.setValueByMethodName(s007, "setHatCaseSu" + inx,
                    DataUtil.getValueByMethodName(s001, "getHatCaseSu" + inx));
            DataUtil.setValueByMethodName(s007, "setHatBaraSu" + inx,
                    DataUtil.getValueByMethodName(s001, "getHatBaraSu" + inx));

            DataUtil.setValueByMethodName(s007, "setKenBaraSu" + inx, row.getKenBaraSu());
            DataUtil.setValueByMethodName(s007, "setKenGenk" + inx, row.getHatGenk());
            DataUtil.setValueByMethodName(s007, "setKenBaik" + inx, row.getHatBaik());
            DataUtil.setValueByMethodName(s007, "setKenGenkKin" + inx, row.getHatGenkKin());
            DataUtil.setValueByMethodName(s007, "setKenBaikKin" + inx, row.getHatBaikKin());
        }
        // 入荷店舗名(ｶﾅ)
        s007.setInTenNmA(tenNmAIn);
        // 出荷店舗名(ｶﾅ)
        s007.setOutTenNmA(tenNmAOut);
        // Common
        s007.setCmnTantoCd(cmnTantoCd);
        s007.setCmnTermId(cmnTermId);
        s007.setCmnInsdd(cmnInsdd);
        s007.setCmnUpddd(cmnInsdd);
        s007.setCmnUpdtime(cmnInsTime);

        return s007;
    }

    /**
     * Prepare data to insert S003 table
     * @param s001 データベースレコード
     * @param form 資料
     * @param sDenno 伝票№
     *        エラー例外
     * @return データベースレコード
     */
    private S003frid insertS003FromS001(S001eosd s001, Sijp0010ResultForm form, String sDenno) {
        // 伝票№
        String denno = CCStringUtil.isEmpty(sDenno) ? EMPTY : sDenno;
        String shkDate = form.getNhnYmd();

        // 店
        String tenIn = form.getKaisyaCd() + form.getJigyobuCd() + form.getTenCd();
        String tenOut = ccSICommon.getComHatTenCd(form.getTriCd(), shkDate);
        if (tenOut.trim().length() == 0) {
            tenOut = ccSICommon.getComHatTenCd(getMaintori(form.getTriCd()), shkDate);
        }

        tenOut = tenOut.substring(0, 7);

        // 部門
        String bmnIn = form.getJigyobuCd() + form.getBmnCd();
//        String bmnOut = "90" + form.getBmnCd();
        String bmnOut = form.getJigyobuCd() + form.getBmnCd();

        // 累計計上日
        String keijoDate = ccSICommon.getKeijoDate(shkDate);

        // 確定情報
        String cmnInsdd = CCDateUtil.getSysDateFormat(CCDateUtil.DATETIME_FORMAT_DATE_YYYYMMDD);
        String cmnInsTime = CCDateUtil.getSysDateFormat(CCDateUtil.DATETIME_FORMAT_TIME);
        String cmnTantoCd = context.getTantoCode();
        String cmnTermName = context.getTermName();
        if (cmnTermName.length() > 8) {
            cmnTermName = cmnTermName.substring(0, 8);
        }
        String cmnTermId = cmnTermName;

        // 確定区分、確定場所
        String kakPlc = ccSICommon.getDefaultTenpo(context.getTantoCode());
        // 納品ルート
        String dcTcKbn = form.getDctcKbn();
        // 仕入月次月
        String getuji = keijoDate.substring(0, 6);
        // 処理状態区分
        String stsKbn = CCSIConst.SYORISTS_KBN.KBN_KAKTEI;
        // 元伝票
        String sOldDen = form.getDpyNo();
        // 合計情報取得
        Long inpGenAll = form.getSumKenGenkKin();
        Long inpBaiAll = form.getSumKenBaikKin();

        // 入荷店
        String tenNmAIn = ccSICommon.getComTenpoNameA(tenIn, shkDate);
        if (tenNmAIn.trim().length() > 20) {
            tenNmAIn = tenNmAIn.substring(0, 20);
        }
        // 出荷店
        String tenNmAOut = ccSICommon.getComTenpoNameA(tenOut, shkDate);
        if (tenNmAOut.trim().length() > 20) {
            tenNmAOut = tenNmAOut.substring(0, 20);
        }

        // 納品数、原価金額、売価金額取得
        for (Sijp0010Eosd row : form.getDataArea()) {
            if (!row.isDisplayFlag()) {
                row.setKenBaraSu(new BigDecimal(0));
                row.setHatGenk(new BigDecimal(0));
                row.setHatBaik(new Integer(0));
                row.setHatGenkKin(new Long(0));
                row.setHatBaikKin(new Long(0));
            }
        }

        S003frid s003 = new S003frid();
        // 伝票番号
        s003.setDpyNo(denno);
        // 伝区
        s003.setDenKbn(CCSIConst.DEN_KBN.KBN_IDO);
        // 入荷店ｺｰﾄﾞ（7桁）
        s003.setInTenpoCd(tenIn);
        // 入荷部門ｺｰﾄﾞ（5桁）
        s003.setInBmnCd(bmnIn);
        // 出荷店ｺｰﾄﾞ（7桁）
        s003.setOutTenpoCd(tenOut);
        // 出荷部門ｺｰﾄﾞ（5桁）
        s003.setOutBmnCd(bmnOut);
        // 出荷日
        s003.setSyukaYmd(shkDate);

        // データ
        s003.setHatYmd(shkDate);
        s003.setNhnYoteiYmd(shkDate);
        s003.setRuiKeijoYmd(keijoDate);
        s003.setFirstKakuteiYmd(cmnInsdd);
        s003.setFirstTantoCd(cmnTantoCd);
        s003.setFirstTermId(cmnTermId);
        s003.setLastKakuteiYmd(cmnInsdd);
        s003.setLastTantoCd(cmnTantoCd);
        s003.setLastTermId(cmnTermId);
        s003.setEntryPlace(kakPlc);
        s003.setDctcKbn(dcTcKbn);
        s003.setSirGetujiYm(getuji);
        s003.setSyoriStsKbn(stsKbn);
        s003.setMotoDpyNo(sOldDen);
        String atukaiKbn = ccSICommon.getFAKubun(tenIn, bmnIn, shkDate);
        s003.setAtukaiKbn(atukaiKbn);
        s003.setsHatGenkKin(inpGenAll);
        s003.setsHatBaikKin(inpBaiAll);
        s003.setsKenGenkKin(inpGenAll);
        s003.setsKenBaikKin(inpBaiAll);

        // 明細
        s003.setMsaiSu(s001.getMsaiSu());
        for (int i = 0; i < form.getDataArea().size(); i++) {
            Sijp0010Eosd row = form.getDataArea().get(i);
            String inx = ((i < 9) ? "0" : "") + (i + 1);
            DataUtil.setValueByMethodName(s003, "setMesaiNo" + inx,
                    DataUtil.getValueByMethodName(s001, "getMesaiNo" + inx));
            String shnCd = (String) DataUtil.getValueByMethodName(s001, "getShnCd" + inx);
            String mkrCd = getMkrCd(shnCd, shkDate);
            DataUtil.setValueByMethodName(s003, "setMkrCd" + inx, mkrCd);
            DataUtil.setValueByMethodName(s003, "setShnCd" + inx, shnCd);
            DataUtil.setValueByMethodName(s003, "setPluKbn" + inx,
                    DataUtil.getValueByMethodName(s001, "getPluKbn" + inx));
            DataUtil.setValueByMethodName(s003, "setCbnrCd" + inx,
                    DataUtil.getValueByMethodName(s001, "getCbnrCd" + inx));
            DataUtil.setValueByMethodName(s003, "setSbnrCd" + inx,
                    DataUtil.getValueByMethodName(s001, "getSbnrCd" + inx));
            DataUtil.setValueByMethodName(s003, "setInShnCd" + inx, shnCd);
            DataUtil.setValueByMethodName(s003, "setInPluKbn" + inx,
                    DataUtil.getValueByMethodName(s001, "getPluKbn" + inx));
            DataUtil.setValueByMethodName(s003, "setInCbnrCd" + inx,
                    DataUtil.getValueByMethodName(s001, "getCbnrCd" + inx));
            DataUtil.setValueByMethodName(s003, "setInSbnrCd" + inx,
                    DataUtil.getValueByMethodName(s001, "getSbnrCd" + inx));

            DataUtil.setValueByMethodName(s003, "setKikakuSu" + inx,
                    DataUtil.getValueByMethodName(s001, "getKikakuSu" + inx));
            DataUtil.setValueByMethodName(s003, "setKikakuTani" + inx,
                    DataUtil.getValueByMethodName(s001, "getKikakuTani" + inx));
            DataUtil.setValueByMethodName(s003, "setIriSu" + inx,
                    DataUtil.getValueByMethodName(s001, "getIriSu" + inx));
            DataUtil.setValueByMethodName(s003, "setHattyuIrisu" + inx,
                    DataUtil.getValueByMethodName(s001, "getHattyuIrisu" + inx));
            DataUtil.setValueByMethodName(s003, "setTani" + inx,
                    DataUtil.getValueByMethodName(s001, "getTani" + inx));
            DataUtil.setValueByMethodName(s003, "setHatCaseSu" + inx,
                    DataUtil.getValueByMethodName(s001, "getHatCaseSu" + inx));
            DataUtil.setValueByMethodName(s003, "setHatBaraSu" + inx,
                    DataUtil.getValueByMethodName(s001, "getHatBaraSu" + inx));

            DataUtil.setValueByMethodName(s003, "setKenBaraSu" + inx, row.getKenBaraSu());
            DataUtil.setValueByMethodName(s003, "setKenGenk" + inx, row.getHatGenk());
            DataUtil.setValueByMethodName(s003, "setKenBaik" + inx, row.getHatBaik());
            DataUtil.setValueByMethodName(s003, "setKenGenkKin" + inx, row.getHatGenkKin());
            DataUtil.setValueByMethodName(s003, "setKenBaikKin" + inx, row.getHatBaikKin());
        }
        // 入荷店舗名(ｶﾅ)
        s003.setInTenNmA(tenNmAIn);
        // 出荷店舗名(ｶﾅ)
        s003.setOutTenNmA(tenNmAOut);
        // Common
        s003.setCmnTantoCd(cmnTantoCd);
        s003.setCmnTermId(cmnTermId);
        s003.setCmnInsdd(cmnInsdd);
        s003.setCmnUpddd(cmnInsdd);
        s003.setCmnUpdtime(cmnInsTime);

        return s003;
    }

    /**
     * 処理⑯：ＥＯＳ明細履歴作成
     * 
     * @param s001 データベースレコード
     * @param form 資料
     * @return データベースレコード
     */
    private S014eomr insertS014FromS001(S001eosd s001, Sijp0010ResultForm form) {
        String insDdd = CCDateUtil.getSysDateFormat(CCDateUtil.DATETIME_FORMAT_DATE_YYYYMMDD);
        String insTime = CCDateUtil.getSysDateFormat(CCDateUtil.DATETIME_FORMAT_TIME);

        String cmnTermName = context.getTermName();
        if (cmnTermName.length() > 8) {
            cmnTermName = cmnTermName.substring(0, 8);
        }

        S014eomr s014 = new S014eomr();
        BeanUtils.copyProperties(s001, s014);
        // ヘッダ
        s014.setInsdd(insDdd);
        s014.setInstime(insTime);
        s014.setDpyNo(form.getDpyNo());
        s014.setDpyKbn(form.getDpyKbn());
        s014.setKaisyaCd(form.getKaisyaCd());
        s014.setJigyoubuCd(form.getJigyobuCd());
        s014.setDispSyoriSts(SYORI_STS_9);
        s014.setSyoriSts(SYORI_STS_9);

        s014.setCmnTantoCd(context.getTantoCode());
        s014.setCmnTermId(cmnTermName);
        s014.setCmnInsdd(insDdd);
        s014.setCmnUpddd(insDdd);
        s014.setCmnUpdtime(insTime);

        return s014;
    }

    /**
     * Match: getTrnDelSQL();
     * 
     * @param form 資料
     * @param s001 データベースレコード
     * @param maxSeqNo SEQNO変数
     * @return データベースレコード
     */
    private S005eost insertS005FromS001(S001eosd s001, Sijp0010ResultForm form, int maxSeqNo) {
        S005eost s005 = new S005eost();

        // ＥＯＳ伝票情報取得（店～センター名称）
        BeanUtils.copyProperties(s001, s005);

        setInfoS005eost(s005, form, CCSICommon.CN_MIKAKUTEI, CCSICommon.CN_REC_KON, maxSeqNo);

        // 共通情報
        s005.setCmnTantoCd(context.getTantoCode());
        String cmnTermName = context.getTermName();
        if (cmnTermName.length() > 8) {
            cmnTermName = cmnTermName.substring(0, 8);
        }
        s005.setCmnTermId(cmnTermName);
        s005.setCmnInsdd(CCDateUtil.getSysDateFormat(CCDateUtil.DATETIME_FORMAT_DATE_YYYYMMDD));
        s005.setCmnUpddd(s005.getCmnInsdd());
        s005.setCmnUpdtime(CCDateUtil.getSysDateFormat(CCDateUtil.DATETIME_FORMAT_TIME));

        return s005;
    }

    /**
     * 処理⑬：ＥＯＳ伝票累積削除<br>
     * S001eosdMapperのupdateByExampleSelectiveを使用する。
     * 
     * @param form 資料
     * @return データベースレコード
     */
    private S001eosd deleteS001(Sijp0010ResultForm form) {
        String cmnUpddd = CCDateUtil.getSysDateFormat(CCDateUtil.DATETIME_FORMAT_DATE_YYYYMMDD);
        String cmnUpdTime = CCDateUtil.getSysDateFormat(CCDateUtil.DATETIME_FORMAT_TIME);

        String cmnTantoCd = context.getTantoCode();
        String cmnTermName = context.getTermName();
        if (cmnTermName.length() > 8) {
            cmnTermName = cmnTermName.substring(0, 8);
        }

        S001eosd s001 = new S001eosd();
        s001.setSyoriStsKbn(CCSIConst.SYORISTS_KBN.KBN_DELETE);
        // common
        s001.setCmnTantoCd(cmnTantoCd);
        s001.setCmnTermId(cmnTermName);
        s001.setCmnUpddd(cmnUpddd);
        s001.setCmnUpdtime(cmnUpdTime);

        return s001;
    }

    /**
     * 
     * @param form 資料
     * @param shoriKbn 処理状態区分
     * @return データベースレコード
     */
    private S014eomr updateS014(Sijp0010ResultForm form, int shoriKbn) {
        S014eomr s014 = new S014eomr();
        String cmnUpddd = CCDateUtil.getSysDateFormat(CCDateUtil.DATETIME_FORMAT_DATE_YYYYMMDD);
        String cmnUpdTime = CCDateUtil.getSysDateFormat(CCDateUtil.DATETIME_FORMAT_TIME);

        // 処理状態区分
        String sSts = EMPTY;
        if (shoriKbn != CCSICommon.CN_KAKUTEI) {
            sSts = CCSIConst.SYORISTS_KBN.KBN_MIKAKTEI;
        } else {
            sSts = CCSIConst.SYORISTS_KBN.KBN_KAKTEI;
        }

        s014.setSyoriSts(sSts);
        s014.setCmnUpddd(cmnUpddd);
        s014.setCmnUpdtime(cmnUpdTime);

        return s014;
    }

    /**
     * 処理⑪：ＥＯＳ明細履歴Ｆ出力
     * 
     * @param form 資料
     * @param s001 データベースレコード
     * @param loginTen7 店舗
     * @param shoriKbn 処理状態区分
     * 
     * @return データベースレコード
     */
    private S014eomr insertS014(Sijp0010ResultForm form, S001eosd s001, String loginTen7, int shoriKbn) {
        S014eomr s014 = new S014eomr();

        String insDdd = CCDateUtil.getSysDateFormat(CCDateUtil.DATETIME_FORMAT_DATE_YYYYMMDD);
        String insTime = CCDateUtil.getSysDateFormat(CCDateUtil.DATETIME_FORMAT_TIME);
        String nhnYmd = form.getNhnYmd();
        String keijoDate = ccSICommon.getKeijoDate(nhnYmd);

        String entryKbn = ccSICommon.getKakuteiKbn(loginTen7, form.getNhnYmd());
        form.setEntryKbn(entryKbn);
        String cmnTermName = context.getTermName();
        if (cmnTermName.length() > 8) {
            cmnTermName = cmnTermName.substring(0, 8);
        }
        // 処理状態区分
        String syoristsKbn = "";
        if (shoriKbn != CCSICommon.CN_MIKAKUTEI) {
            syoristsKbn = CCSIConst.SYORISTS_KBN.KBN_KAKTEI;
        } else {
            syoristsKbn = CCSIConst.SYORISTS_KBN.KBN_MIKAKTEI;
        }

        // 金額
        Long sGenMae = s001.getsKenGenkKin();
        Long sBaiMae = s001.getsKenBaikKin();
        if (CCSIConst.SYORISTS_KBN.KBN_MIKAKTEI.equals(s001.getSyoriStsKbn())) {
            sGenMae = s001.getsHatGenkKin();
            sBaiMae = s001.getsHatBaikKin();
        }

        Long sGenAto = new Long(0);
        Long sBaiAto = new Long(0);
        if (shoriKbn == CCSICommon.CN_MIKAKUTEI) {
            sGenAto = new Long(0);
            sBaiAto = new Long(0);
        } else {
            sGenAto = form.getSumKenGenkKin();
            sBaiAto = form.getSumKenBaikKin();
        }

        // ヘッダ
        s014.setInsdd(insDdd);
        s014.setInstime(insTime);
        s014.setDpyNo(form.getDpyNo());
        s014.setDpyKbn(form.getDpyKbn());
        s014.setKaisyaCd(form.getKaisyaCd());
        s014.setJigyoubuCd(form.getJigyobuCd());

        // データ
        s014.setTenCd(s001.getTenCd());
        s014.setBmnCd(s001.getBmnCd());
        s014.setTorihikiCd(s001.getTorihikiCd());
        // form
        s014.setNhnYmd(nhnYmd);

        s014.setHatYmd(s001.getHatYmd());
        s014.setNhnYoteiYmd(s001.getNhnYoteiYmd());
        s014.setRuiKeijoYmd(keijoDate);
        s014.setKakuteiYmd(CCDateUtil.getSysDateFormat(CCDateUtil.DATETIME_FORMAT_DATE_YYYYMMDD));
        s014.setEntryKbn(entryKbn);
        s014.setEntryPlace(loginTen7);
        s014.setKasanKbn(CCSIConst.KASAN_KBN.KBN_SUKIN_A);
        s014.setDispSyoriSts(syoristsKbn);
        s014.setSyoriSts(syoristsKbn);
        s014.setMaeGenkgk(sGenMae);
        s014.setAtoGenkgk(sGenAto);
        s014.setMaeBaikgk(sBaiMae);
        s014.setAtoBaikgk(sBaiAto);
        s014.setCmnTantoCd(context.getTantoCode());
        s014.setCmnTermId(cmnTermName);
        s014.setCmnInsdd(insDdd);
        s014.setCmnUpddd(insDdd);
        s014.setCmnUpdtime(insTime);

        return s014;
    }

    /**
     * Copy old data from S001 to S005 and S027.
     * 
     * 処理⑨：ＥＯＳ伝票入力Ｔ登録
     * 
     * @param form 資料
     * @param eosd データベースレコード
     * @param shoriKbn 処理状態区分
     * @param koshinKbn 更新区分
     * @param maxSeqNo SEQNO変数
     * 
     * @return データベースレコード
     */
    private S005eost insertS005(Sijp0010ResultForm form, S001eosd eosd, int shoriKbn, String koshinKbn, int maxSeqNo) {
        // 2．ＳＥＱＮＯ取得
        S005eost eost = new S005eost();
        setInfoS005eost(eost, form, shoriKbn, koshinKbn, maxSeqNo);

        if (CCSICommon.CN_MIKAKUTEI == shoriKbn) {
            setDenDelInfoS005(eost, eosd);
        } else {
            setDenInfoS005(eost, eosd, form, shoriKbn);
        }

        return eost;
    }

    /**
     * EOS伝票入力トラン内のデータ件数取得処理.
     * <p>
     * 機能概要：<br>
     * EOS伝票入力トラン(メンテトラン)内の伝票データの存<br>
     * 在を確認します。<br>
     * 入力条件に該当する伝票データの内容を表示します。<br>
     * 
     * @param dpyNo 伝票NO
     * @return SeqNoのMax値を取得 or 0
     */
    private int getMaxSeqno(String dpyNo) {
        // getZenData()
        // 1．EOS伝票入力トラン内のデータ件数取得処理
        // Sijp0010MapperのselectS005eost01を使用
        Integer maxSeqNo = dao.selectOne("sijp0010SelectS005eost01", dpyNo);
        if (maxSeqNo == null) {
            return 0;
        }
        return maxSeqNo;
    }

    /**
     * 
     * @param s005 データベースレコード
     * @param s001 データベースレコード
     * @param form 資料
     * @param shoriKbn 処理状態区分
     */
    private void setDenInfoS005(S005eost s005, S001eosd s001, Sijp0010ResultForm form, int shoriKbn) {
        String nhnYmd = form.getNhnYmd();
        String keijoDate = ccSICommon.getKeijoDate(nhnYmd);
        String getuji = keijoDate.substring(0, 6);
        String cmnInsdd = CCDateUtil.getSysDateFormat(CCDateUtil.DATETIME_FORMAT_DATE_YYYYMMDD);
        String cmnTantoCd = context.getTantoCode();
        //
        String cmnTermName = context.getTermName();
        if (cmnTermName.length() > 8) {
            cmnTermName = cmnTermName.substring(0, 8);
        }

        BeanUtils.copyProperties(s001, s005);

        if (CCSICommon.CN_KAKUTEI == shoriKbn) {
            s005.setFirstKakuteiYmd(form.getFirstKakuteiYmd());
            s005.setFirstTantoCd(form.getFirstTantoCd());
            s005.setFirstTermId(form.getFirstTermId());
        }

        s005.setNhnYmd(nhnYmd);
        s005.setRuiKeijoYmd(keijoDate);
        s005.setLastKakuteiYmd(cmnInsdd);
        s005.setLastTantoCd(cmnTantoCd);
        s005.setLastTermId(cmnTermName);
        s005.setTaxKbn(form.getTaxKbn());
        s005.setTaxRitu(form.getTaxRitu());
        s005.setEntryKbn(form.getEntryKbn());
        s005.setEntryPlace(context.getTenpoCode());
        s005.setKasanKbn(form.getKasanKbn());
        s005.setDctcKbn(form.getDctcKbn());
        // 仕入月次月（累計計上日の年月）
        s005.setSirGetujiYm(getuji);
        // 処理状態区分
        s005.setSyoriStsKbn(CCSIConst.SYORISTS_KBN.KBN_KAKTEI);
        s005.setIkatuFlg(form.getIkatuFlg());
        s005.setsKenGenkKin(form.getSumKenGenkKin());
        s005.setsKenBaikKin(form.getSumKenBaikKin());
        s005.setsKenGenkZei(form.getSumKenGenkZei());
        s005.setsKenBaikZei(form.getSumKenBaikZei());

        for (int i = 0; i < 10; i++) {
            String inx = ((i < 9) ? "0" : "") + (i + 1);
            Sijp0010Eosd row = form.getDataArea().get(i);

            DataUtil.setValueByMethodName(s005, "setKenBaraSu" + inx, row.getKenBaraSu());
            DataUtil.setValueByMethodName(s005, "setKenGenk" + inx, row.getHatGenk());
            DataUtil.setValueByMethodName(s005, "setKenBaik" + inx, row.getHatBaik());
            DataUtil.setValueByMethodName(s005, "setKenGenkKin" + inx, row.getHatGenkKin());
            DataUtil.setValueByMethodName(s005, "setKenBaikKin" + inx, row.getHatBaikKin());
            DataUtil.setValueByMethodName(s005, "setKenGenkZei" + inx, row.getKenGenkZei());
            DataUtil.setValueByMethodName(s005, "setKenBaikZei" + inx, row.getKenBaikZei());
        }

        s005.setCmnTantoCd(cmnTantoCd);
        s005.setCmnTermId(cmnTermName);
        s005.setCmnInsdd(cmnInsdd);
        s005.setCmnUpddd(cmnInsdd);
        s005.setCmnUpdtime(CCDateUtil.getSysDateFormat(CCDateUtil.DATETIME_FORMAT_TIME));
    }

    /**
     * 
     * @param s005 データベースレコード
     * @param s001 データベースレコード
     */
    private void setDenDelInfoS005(S005eost s005, S001eosd s001) {
        BeanUtils.copyProperties(s001, s005);

        s005.setSyoriStsKbn(CCSIConst.SYORISTS_KBN.KBN_MIKAKTEI);
        s005.setCmnTantoCd(context.getTantoCode());
        String cmnTermName = context.getTermName();
        if (cmnTermName.length() > 8) {
            cmnTermName = cmnTermName.substring(0, 8);
        }
        s005.setCmnTermId(cmnTermName);
        s005.setCmnInsdd(CCDateUtil.getSysDateFormat(CCDateUtil.DATETIME_FORMAT_DATE_YYYYMMDD));
        s005.setCmnUpddd(s005.getCmnInsdd());
        s005.setCmnUpdtime(CCDateUtil.getSysDateFormat(CCDateUtil.DATETIME_FORMAT_TIME));
    }

    /**
     * 
     * @param s005 データベースレコード
     * @param form 資料
     * @param shoriKbn 処理状態区分
     * @param koshinKbn 更新区分
     * @param maxSeqNo SEQNO変数
     */
    private void setInfoS005eost(S005eost s005, Sijp0010ResultForm form, int shoriKbn, String koshinKbn, int maxSeqNo) {
        int seqNo = 0;
        String updateKbn = "";
        if (CCSICommon.CN_KAKUTEI == shoriKbn) {
            updateKbn = CCSICommon.CN_UPDKBN_I;
            if (maxSeqNo <= 0) {
                seqNo = CCSICommon.CN_REC_KON_1;
            } else {
                seqNo = maxSeqNo + 1;
            }
        } else if (CCSICommon.CN_UPDATE == shoriKbn) {
            if (CCSICommon.CN_REC_ZEN.equals(koshinKbn)) {
                seqNo = CCSICommon.CN_REC_ZEN_0;
                updateKbn = CCSICommon.CN_UPDKBN_D;
            } else {
                seqNo = maxSeqNo + 1;
                updateKbn = CCSICommon.CN_UPDKBN_I;
            }
        } else if (CCSICommon.CN_MIKAKUTEI == shoriKbn) {
            updateKbn = CCSICommon.CN_UPDKBN_D;
            if (CCSICommon.CN_REC_ZEN.equals(koshinKbn)) {
                seqNo = CCSICommon.CN_REC_ZEN_0;
            } else {
                seqNo = maxSeqNo + 1;
            }
        }

        // set info
        s005.setInsdd(CCDateUtil.getSysDateFormat(CCDateUtil.DATETIME_FORMAT_DATE_YYYYMMDD));
        s005.setSeqno((short) seqNo);
        s005.setInstime(CCDateUtil.getSysDateFormat(CCDateUtil.DATETIME_FORMAT_TIME));
        s005.setUpdateKbn(updateKbn);
        s005.setDpyNo(form.getDpyNo());
        s005.setRuiFlg("1");
        s005.setDenKbn(form.getDpyKbn());
        s005.setKaisyaCd(form.getKaisyaCd());
        s005.setJigyoubuCd(form.getJigyobuCd());
    }

    /**
     * 処理⑧：ＥＯＳ伝票累積Ｆ更新
     * 3．S001eosdMapperのupdateByExampleSelectiveを使用する。
     * 
     * @param form 資料
     * @param loginTen7 店舗
     * @param shoriKbn 処理状態区分
     * 
     * @return データベースレコード
     */
    private S001eosd updateS001eosd(Sijp0010ResultForm form, String loginTen7, int shoriKbn) {
        // データを設定
        String nhnYmd = form.getNhnYmd();
        String keijoDate = ccSICommon.getKeijoDate(nhnYmd);
        String getuJi = keijoDate.substring(0, 6);
        String cmnInsdd = CCDateUtil.getSysDateFormat(CCDateUtil.DATETIME_FORMAT_DATE_YYYYMMDD);
        String cmnTantoCd = context.getTantoCode();
        //
        String cmnTermName = context.getTermName();
        if (cmnTermName.length() > 8) {
            cmnTermName = cmnTermName.substring(0, 8);
        }

        if (CCStringUtil.isEmpty(form.getFirstKakuteiYmd()) && CCSICommon.CN_INSERT == shoriKbn) {
            // 初回確定時
            form.setFirstKakuteiYmd(cmnInsdd);
            form.setFirstTantoCd(cmnTantoCd);
            form.setFirstTermId(cmnTermName);
        }

        S001eosd s001 = new S001eosd();
        BeanUtils.copyProperties(form, s001, new String[] {"bmnCd" });
        // 累計計上日
        s001.setRuiKeijoYmd(keijoDate);
        // 最終確定日
        s001.setLastKakuteiYmd(cmnInsdd);
        // 最終担当者コード
        s001.setLastTantoCd(cmnTantoCd);
        // 最終端末ID
        s001.setLastTermId(cmnTermName);
        s001.setEntryPlace(loginTen7);
        // 仕入月次月
        s001.setSirGetujiYm(getuJi);
        // 処理状態区分
        s001.setSyoriStsKbn(CCSIConst.SYORISTS_KBN.KBN_KAKTEI);
        // 原価金額合計
        s001.setsKenGenkKin(form.getSumKenGenkKin());
        // 売価金額合計
        s001.setsKenBaikKin(form.getSumKenBaikKin());
        // 検収合計原価税額
        s001.setsKenGenkZei(form.getSumKenGenkZei());
        // 検収合計売価税額
        s001.setsKenBaikZei(form.getSumKenBaikZei());

        // 生鮮用明細数
        short mesaiSu = 0;
        // Clear invalid row
        BigDecimal bdZero = new BigDecimal(0);
        Long lgZero = new Long(0);
        Integer itZero = new Integer(0);

        for (Sijp0010Eosd row : form.getDataArea()) {
            if (!row.isDisplayFlag()) {
                row.setKenBaraSu(bdZero);
                row.setHatGenk(bdZero);
                row.setHatGenkKin(lgZero);
                row.setHatBaik(itZero);
                row.setHatBaikKin(lgZero);
                row.setKenGenkZei(itZero);
                row.setKenBaikZei(itZero);
            } else {
                // 生鮮用明細数（現在は未使用）
                if (bdZero.compareTo(row.getKenBaraSu()) != 0) {
                    mesaiSu++;
                }
                // 売価還元区分
                if (!CCSIConst.BAIKA_KANRI.BAIKA_KANGEN.equals(form.getBaikanKbn())) {
                    // 検収売単価
                    row.setHatBaik(itZero);
                    // 検収売価金額
                    row.setHatBaikKin(lgZero);
                    // 検収売価税額
                    row.setKenBaikZei(itZero);
                }
            }
        }

        if (CCSIConst.DEN_KBN.KBN_EOSSIR.equals(form.getDpyKbn())) {
            s001.setMsaiSu(mesaiSu);
        }

        for (int i = 0; i < 10; i++) {
            String inx = ((i < 9) ? "0" : "") + (i + 1);
            Sijp0010Eosd row = form.getDataArea().get(i);

            DataUtil.setValueByMethodName(s001, "setKenBaraSu" + inx, row.getKenBaraSu());
            DataUtil.setValueByMethodName(s001, "setKenGenk" + inx, row.getHatGenk());
            DataUtil.setValueByMethodName(s001, "setKenBaik" + inx, row.getHatBaik());
            DataUtil.setValueByMethodName(s001, "setKenGenkKin" + inx, row.getHatGenkKin());
            DataUtil.setValueByMethodName(s001, "setKenBaikKin" + inx, row.getHatBaikKin());
            DataUtil.setValueByMethodName(s001, "setKenGenkZei" + inx, row.getKenGenkZei());
            DataUtil.setValueByMethodName(s001, "setKenBaikZei" + inx, row.getKenBaikZei());
        }

        // getUpdCommon()
        s001.setCmnTantoCd(context.getTantoCode());
        s001.setCmnTermId(cmnTermName);
        s001.setCmnUpddd(CCDateUtil.getSysDateFormat(CCDateUtil.DATETIME_FORMAT_DATE_YYYYMMDD));
        s001.setCmnUpdtime(CCDateUtil.getSysDateFormat(CCDateUtil.DATETIME_FORMAT_TIME));

        return s001;
    }

    /**
     * 確定、更新用パラメータ作成.
     * <p>
     * 機能概要：<br>
     * 確定、更新用共通パラメータを作成します。<br>
     * 
     * @param form 資料
     * @return 成功：0
     */
    private int setPreUpdParam(Sijp0010ResultForm form) {
        BigDecimal dTaxRate = new BigDecimal(0); // 税率（テンポラリ）
        BigDecimal dKingaku = new BigDecimal(0); // 金額（テンポラリ）
        double dZeigaku = 0; // 税額（テンポラリ）
        int iZeigaku = 0; // 税額（テンポラリ小数点無し）
        String sBumon5 = form.getJigyobuCd() + form.getBmnCd();
        String sTen7 = form.getKaisyaCd() + form.getJigyobuCd() + form.getTenCd();

        // (データ取得)処理で計算した[税区分変数」は"0"の場合
        if (CCSIConst.TAX_KBN_0.equals(form.getTaxKbn())) {
            return -1;
        } else {
            dTaxRate = form.getTaxRitu();
        }

        if (CCSIConst.TAX_FREE_KBN_9.equals(form.getTaxKbn())) {
            // (データ取得)処理で計算した[税区分変数」は"9"の場合
            // 合計原価税額変数に"0"を格納。
            // 合計売価税額変数に"0"を格納。
            form.setSumKenGenkZei(new Long(0));
            form.setSumKenBaikZei(new Long(0));

            for (Sijp0010Eosd row : form.getDataArea()) {
                row.setKenGenkZei(0);
                row.setKenBaikZei(0);
            }
        } else {
            // (データ取得)処理で計算した[税区分変数」は"9"以外の場合
            String excOrIndTax = ccSICommon.getExcOrIndTax(form.getTaxKbn());

            if (excOrIndTax == null) {
                return -1;
            }

            if (CCSIConst.TAX_IO_KBN.TAX_IN_KBN.equals(excOrIndTax)) {
                // 合計原価税額変数に"0"を格納。
                form.setSumKenGenkZei(new Long(0));
                // 合計売価税額変数に"0"を格納。
                form.setSumKenBaikZei(new Long(0));

                for (Sijp0010Eosd row : form.getDataArea()) {
                    dKingaku = dTaxRate.multiply(new BigDecimal(row.getHatGenkKin()));
                    dZeigaku =
                            CCNumberUtil.divideBigDecimal(dKingaku, dTaxRate.add(new BigDecimal(100)),
                                    CCNumberUtil.ROUND_DOWN);

                    iZeigaku = new Double(dZeigaku).intValue();
                    row.setKenGenkZei(iZeigaku);

                    dKingaku = dTaxRate.multiply(new BigDecimal(row.getHatBaikKin()));
                    dZeigaku =
                            CCNumberUtil.divideBigDecimal(dKingaku, dTaxRate.add(new BigDecimal(100)),
                                    CCNumberUtil.ROUND_DOWN);

                    iZeigaku = new Double(dZeigaku).intValue();
                    row.setKenBaikZei(iZeigaku);
                }
            } else {
                // 合計原価税額変数に"0"を格納。
                form.setSumKenGenkZei(new Long(0));
                // 合計売価税額変数に"0"を格納。
                form.setSumKenBaikZei(new Long(0));

                for (Sijp0010Eosd row : form.getDataArea()) {
                    row.setKenGenkZei(0);
                    row.setKenBaikZei(0);
                }

                dKingaku = dTaxRate.multiply(new BigDecimal(form.getSumKenGenkKin()));
                dZeigaku =
                        CCNumberUtil.divideBigDecimal(dKingaku, dTaxRate.add(new BigDecimal(100)),
                                CCNumberUtil.ROUND_DOWN);

                form.setSumKenGenkZei(new Double(dZeigaku).longValue());

                dKingaku = dTaxRate.multiply(new BigDecimal(form.getSumKenBaikKin()));
                dZeigaku =
                        CCNumberUtil.divideBigDecimal(dKingaku, dTaxRate.add(new BigDecimal(100)),
                                CCNumberUtil.ROUND_DOWN);

                form.setSumKenBaikZei(new Double(dZeigaku).longValue());
            }

            // 売価還元区分の取得（発注日で検索することにより、発注伝票作成時の還元区分を取得する）
            String baikanKbn = ccSICommon.getBaikankbn(sTen7, sBumon5, form.getNhnYmd());
            form.setBaikanKbn(baikanKbn);
        }

        return 0;
    }

    /**
     * グリッドデータのBeansセット処理
     * <p>
     * 機能概要：<br>
     * ヘッダのデータをBeansにセットします。<br>
     * 
     * @param form 資料
     * @return true：処理成功、false：処理失敗
     */
    private boolean setCond(Sijp0010ResultForm form) {
        if (form == null) {
            return false;
        }

        if (form.getDpyNo() == null) {
            form.setDpyNo(EMPTY);
        }
        if (form.getKaisyaCd() == null) {
            form.setKaisyaCd(EMPTY);
        }
        if (form.getJigyobuCd() == null) {
            form.setJigyobuCd(EMPTY);
        }
        if (form.getNhnYmd() == null) {
            form.setNhnYmd(EMPTY);
        }
        if (form.getNhnYoteiYmd() == null) {
            form.setNhnYoteiYmd(EMPTY);
        }
        if (form.getHatYmd() == null) {
            form.setHatYmd(EMPTY);
        }
        if (!CCStringUtil.isEmpty(form.getDpyNo())) {
            form.setDpyNo(CCStringUtil.suppZero(form.getDpyNo(), 9));
        }
        if (!CCStringUtil.isEmpty(form.getKaisyaCd())) {
            form.setKaisyaCd(CCStringUtil.suppZero(form.getKaisyaCd(), 2));
        }
        if (!CCStringUtil.isEmpty(form.getJigyobuCd())) {
            form.setJigyobuCd(CCStringUtil.suppZero(form.getJigyobuCd(), 2));
        }
        if (form.getTaxKbn() == null) {
            form.setTaxKbn(EMPTY);
        }
        if (form.getTaxRitu() == null) {
            form.setTaxRitu(new BigDecimal(0));
        }
        return true;
    }

    /**
     * 処理②：入力項目チェック処理
     * 
     * @param cond 資料
     * @param shoriKbn 処理状態区分 処理区分
     * @return true：処理成功、false：処理失敗
     */
    private boolean checkCond(Sijp0010CondForm cond, int shoriKbn) {
        // チェックフラグ変数にtrueとする。
        boolean flag = true;

        // 納品日チェック：
        // 画面項目[納品日]　>　運用日の場合
        String unyoDate = context.getUnyoDate();
        if (shoriKbn != CCSICommon.CN_MIKAKUTEI && shoriKbn != CCSICommon.CN_DISPLAY) {
            String nhnYmd = cond.getNhnYmd();
            boolean validDate = true;
            String errCode = "";

            if (CCStringUtil.isEmpty(nhnYmd)) {
                // [C0012]
                errCode = CCMessageConst.MSG_KEY_NOT_KIP_ITEM;
                flag = false;
                validDate = false;
            } else {
                if (!CCDateUtil.isDate(nhnYmd)) {
                    // [C0011]
                    errCode = CCMessageConst.MSG_KEY_DATE_ERROR;
                    flag = false;
                    validDate = false;
                } else {
                    if (nhnYmd.compareTo(unyoDate) > 0) {
                        // 「U0001」メッセージが表示される。
                        errCode = CCMessageConst.MSG_KEY_DATE_BEFORE;
                        flag = false;
                        validDate = false;
                    }
                }
            }

            // 配送区分名取得処理：
            if (CCStringUtil.isEmpty(cond.getDctcKbn())) {
                // [C0012]
                errRes.addErrorInfo("dctcKbn", CCMessageConst.MSG_KEY_NOT_KIP_ITEM);
                flag = false;
            } else {
                if (!CCNumberUtil.isNumeric(cond.getDctcKbn())) {
                    // [C0062]
                    errRes.addErrorInfo("dctcKbn", CCMessageConst.MSG_KEY_ITEM_INPUT_IS_ERROR);
                    flag = false;
                } else {
                    String searchDate = nhnYmd;
                    if (!validDate) {
                        searchDate = CCDateUtil.getSysDateFormat(CCDateUtil.DATETIME_FORMAT_DATE_YYYYMMDD);
                    }

                    // sNRuteNm = clSICom.getComNRuteNm( sNhnRoute, sKeyDate )
                    String sNRuteNm = ccSICommon.getComNRuteNm(searchDate, cond.getDctcKbn());
                    if (CCStringUtil.isEmpty(sNRuteNm)) {
                        // 「C0062」メッセージが表示される。
                        errRes.addErrorInfo("dctcKbn", CCMessageConst.MSG_KEY_ITEM_INPUT_IS_ERROR);
                        flag = false;
                    }
                }
            }

            // 日付のエラーは納品ルートのエラーの次に表示 (old logic)
            if (!validDate) {
                errRes.addErrorInfo("nhnYmd", errCode);
            }

            // 納品予定日チェック：
            // 画面項目[納品予定日]　>　運用日の場合
            String nhnYoteiYmd = CCStringUtil.isEmpty(cond.getNhnYoteiYmd()) ? EMPTY : cond.getNhnYoteiYmd();

            if (!checkNhnYoteiYmd(nhnYoteiYmd)) {
                // 「S1084」メッセージが表示される。
                // ※メッセージ埋め込み文字：画面項目[納品日]
                ArrayList<String> msgParams = new ArrayList<String>();
                msgParams.add(CCDateUtil.getDateFormat(nhnYoteiYmd, CCDateUtil.DATETIME_FORMAT_DATE));
                errRes.addErrorInfoWithParam("nhnYoteiYmd", CCMessageConst.MSG_KEY_DATE_OVER_UNYODATE, msgParams);
                flag = false;
            }

            // コントロールマスタの納品日と比較
            String tantoCode = context.getTantoCode();
            String ten7 = ccSICommon.getDefaultTenpo(tantoCode);
            String ctlNhnYmd = ccSICommon.getComNhnYmd(ten7, nhnYmd);
            // コントロールマスタ納品日変数の値　>　画面項目[納品日]の場合
            if (ctlNhnYmd.compareTo(nhnYmd) > 0) {
                // 「S1099」メッセージが表示される。
                errRes.addErrorInfo("nhnYmd", CCMessageConst.MSG_KEY_DATE_OVER);
                flag = false;
            }
        }

        return flag;
    }

    /**
     * 処理25：データのエラーチェック処理<br>
     * グリッド入力項目チェック処理
     * <p>
     * 機能概要：<br>
     * グリッドの入力データをチェックします。<br>
     * 
     * @param form 資料
     * @param shoriKbn 処理状態区分
     * @return true：処理成功、false：処理失敗
     */
    private boolean checkData(Sijp0010ResultForm form, int shoriKbn) {
        // 5．チェックフラグにtrueで初期化
        boolean flag = true;
        BigDecimal bdZero = new BigDecimal(0);

        // Sum
        BigDecimal genkinAll = new BigDecimal(0);
        BigDecimal baikinAll = new BigDecimal(0);
        Long genGokei = new Long(0);
        Long baiGokei = new Long(0);
        // 6．グリッドチェック
        for (int i = 0; i < form.getDataArea().size(); i++) {
            Sijp0010Eosd row = form.getDataArea().get(i);

            // doSetGrid();
            doCheckDispFlg(row);

            if (row.isDisplayFlag()) {
                // グリッド(index).商品[GTIN] <> ""場合
                if (!CCStringUtil.isEmpty(row.getShnCd())) {
                    if (row.getKenBaraSu() == null) {
                        // グリッド(index).納品数量のサイズ == 0の場合
                        // 「C0012」メッセージが表示される。
                        errRes.addErrorInfo("kenBaraSu_" + i, CCMessageConst.MSG_KEY_DATA_IS_EMPTY);
                        flag = false;
                    } else if (bdZero.compareTo(row.getKenBaraSu()) > 0) {
                        // グリッド(index).納品数量 < 0の場合
                        // 「S1024」メッセージが表示される。
                        errRes.addErrorInfo("kenBaraSu_" + i, CCMessageConst.MSG_KEY_NUMBER_NEGATIVE_INVALID);
                        flag = false;
                    } else {
                        // グリッド(index).納品数量 >= cmJSICommon.CN_SURYO_MAXの場合
                        if (new BigDecimal(CCSICommon.CN_SURYO_MAX).compareTo(row.getKenBaraSu()) <= 0) {
                            // 「S1036」メッセージが表示される。 => S5157
                            // ※メッセージ埋め込み文字："（６桁＋少数２桁）"
                            errRes.addErrorInfo("kenBaraSu_" + i, CCMessageConst.MSG_KEY_VALUE_OVER_SURYO_MAX);
                            flag = false;
                        }

                        BigDecimal gokeiGenk = row.getKenBaraSu().multiply(row.getHatGenk());
                        BigDecimal gokeiBaik = row.getKenBaraSu().multiply(new BigDecimal(row.getHatBaik()));

                        row.setHatGenkKin(gokeiGenk.longValue());
                        row.setHatBaikKin(gokeiBaik.longValue());
                        if (new BigDecimal(CCSICommon.CN_KINGAK_MAX).compareTo(gokeiBaik) <= 0) {
                            // [S1036] : "（売価金額）" => S5156
                            errRes.addErrorInfo("kenBaraSu_" + i, CCMessageConst.MSG_KEY_KENBAIKKIN_OVER_TANKA_MAX);
                            flag = false;
                        }

                        if (new BigDecimal(CCSICommon.CN_KINGAK_MAX).compareTo(gokeiGenk) <= 0) {
                            // [S1036] : "（原価金額）" => S5155
                            errRes.addErrorInfo("kenBaraSu_" + i, CCMessageConst.MSG_KEY_KENGENKKIN_OVER_TANKA_MAX);
                            flag = false;
                        }

                        baikinAll = baikinAll.add(gokeiBaik);
                        baiGokei = baiGokei + row.getHatBaikKin();
                        if (new BigDecimal(CCSICommon.CN_KINALL_MAX).compareTo(baikinAll) <= 0) {
                            // [S1036] : "（売価金額）" => S5156
                            errRes.addErrorInfo("kenBaraSu_", CCMessageConst.MSG_KEY_KENBAIKKIN_OVER_TANKA_MAX);
                            flag = false;
                        }

                        genkinAll = genkinAll.add(gokeiGenk);
                        genGokei = genGokei + row.getHatGenkKin();
                        if (new BigDecimal(CCSICommon.CN_KINALL_MAX).compareTo(genkinAll) <= 0) {
                            // [S1036] : "（原価金額）" => S5156
                            errRes.addErrorInfo("kenBaraSu_", CCMessageConst.MSG_KEY_KENGENKKIN_OVER_TANKA_MAX);
                            flag = false;
                        }
                    }
                } else {
                    // グリッド(index).商品[GTIN] == ""場合
                    // グリッド(index).納品数量 <> 0の場合
                    if (bdZero.compareTo(row.getKenBaraSu()) != 0) {
                        // 「C0015」メッセージが表示される。
                        // ※メッセージ埋め込み文字：なし
                        errRes.addErrorInfo("kenBaraSu_" + i, CCMessageConst.MSG_KEY_INPUT_NOT_ITEM);
                        flag = false;
                    }
                }
            }
        }
        form.setSumKenGenkKin(genGokei.longValue());
        form.setSumKenBaikKin(baiGokei.longValue());

        // 7．フッタチェック
        // フッタエリア.(入力)原価金額合計 <> ""場合
        if (form.getInpGenInp() != null) {
            if (form.getInpGenInp().compareTo(genGokei.longValue()) != 0) {
                // 「C0062」メッセージが表示される。
                // ※メッセージ埋め込み文字：なし
                errRes.addErrorInfo("inpGenInp", CCMessageConst.MSG_KEY_ITEM_INPUT_IS_ERROR);
                flag = false;
            }
        } else {
            // 「C0012」メッセージが表示される。
            // ※メッセージ埋め込み文字：なし
            errRes.addErrorInfo("inpGenInp", CCMessageConst.MSG_KEY_NOT_KIP_ITEM);
            flag = false;
        }

        // 8．チェックフラグを戻す。
        return flag;
    }

    /**
     * グリッドデータ有効行取得処理
     * <p>
     * 機能概要：<br>
     * グリッドのデータの有効行の取得を行います。<br>
     * <p>
     * 
     * @param row グリッド行
     * @return true：有効、false：無効
     */
    private boolean doCheckDispFlg(Sijp0010Eosd row) {
        if (row == null) {
            return false;
        }
        if (row.getHatBaraSu() == null) {
            row.setHatBaraSu(new BigDecimal(0));
        }
        if (row.getKenBaraSu() == null) {
            row.setKenBaraSu(new BigDecimal(0));
        }
        if (row.getHatGenk() == null) {
            row.setHatGenk(new BigDecimal(0));
        }
        if (row.getHatGenkKin() == null) {
            row.setHatGenkKin(new Long(0));
        }
        if (row.getHatBaik() == null) {
            row.setHatBaik(new Integer(0));
        }
        if (row.getHatBaikKin() == null) {
            row.setHatBaikKin(new Long(0));
        }

        // 商品コード
        if (!CCStringUtil.isEmpty(row.getShnCd())) {
            row.setDisplayFlag(true);
            return true;
        }

        // 数量
        if (row.getKenBaraSu() != null && new BigDecimal(0).compareTo(row.getKenBaraSu()) != 0) {
            row.setDisplayFlag(true);
            return true;
        }

        row.setDisplayFlag(false);
        return false;
    }

    /**
     * 納品予定日チェック処理.
     * <p>
     * 機能概要：<br>
     * 納品予定日＜運用日の場合はエラー<br>
     * 
     * @param nhnYoteiYmd 納品予定日
     * @return true：入力チェックＯＫ、false：入力チェックＮＧ
     */
    private boolean checkNhnYoteiYmd(String nhnYoteiYmd) {
        String unyoDate = context.getUnyoDate();

        String nhnYoteiYmdIn = (nhnYoteiYmd == null) ? EMPTY : nhnYoteiYmd;
        if (nhnYoteiYmdIn.compareTo(unyoDate) > 0) {
            return false;
        }
        return true;
    }

    /**
     * ログイン店舗チェック処理.
     * <p>
     * 機能概要：<br>
     * 店舗の時の他店舗の更新、本部の時の他事業部の更新を不可<br>
     * とする。<br>
     * 
     * @param nhnYmd 納品日
     * @param dbKaisyaCd 会社
     * @param dbJigyobuCd 事業部
     * @param dbTenCd 店舗
     * @return true：入力チェックＯＫ、false：入力チェックＮＧ
     */
    private boolean getUpdChk(String nhnYmd, String dbKaisyaCd, String dbJigyobuCd, String dbTenCd) {
        String loginTenCd = context.getTenpoCode();
        // 店舗区分 = cmJSICommon.getComTenpoKubunの戻る値
        // ログイン店舗コード変数
        // 画面項目[納品日] ※ 整形：右スペースパディング（8桁数）
        String inDate = EMPTY;
        inDate = CCStringUtil.isEmpty(nhnYmd) ? EMPTY : nhnYmd;

        String tenKbn = ccSICommon.getComTenpoKubun(loginTenCd, inDate);
        // 3-1．ログイン店舗、入力店舗チェック
        // ログイン店舗コード変数のsubstring(2, 4) <> cmJSICommon.CN_JIGYOBU_90 OR 店舗区分 <> cmJSICommon.CN_TENKBN_HONBUの場合
//        if (!CCSICommon.CN_JIGYOBU_90.equals(jigyobuTemp) || !CCSICommon.CN_TENKBN_HONBU.equals(tenKbn)) {
        if (!CCSICommon.CN_TENKBN_HONBU.equals(tenKbn)) {
            // 店舗区分 == cmJSICommon.CN_TENKBN_HONBUの場合
            String ten7 = dbKaisyaCd + dbJigyobuCd + dbTenCd;
            if (CCSICommon.CN_TENKBN_HONBU.equals(tenKbn)) {
                // 店舗 = 画面項目[会社+事業部+店舗]
                String ten4 = ten7.substring(0, 4);
                return loginTenCd.substring(0, 4).equals(ten4);
            } else if (CCSICommon.CN_TENKBN_TENPO.equals(tenKbn)) {
                return loginTenCd.equals(ten7);
            }
//        } else if (CCSICommon.CN_JIGYOBU_90.equals(jigyobuTemp) && CCSICommon.CN_TENKBN_HONBU.equals(tenKbn)) {
        } else if (CCSICommon.CN_TENKBN_HONBU.equals(tenKbn)) {
            return true;
        }
        return true;
    }

    /**
     * 処理状態(日本語)取得処理.
     * <p>
     * 機能概要：<br>
     * 処理状態(日本語)を取得します。<br>
     * <p>
     * 
     * @param syoriStsKbn なし
     * @return 処理状態(日本語)
     */
    private String getStatusNm(String syoriStsKbn) {
        if (syoriStsKbn == null || syoriStsKbn.trim().length() == 0) {
            return EMPTY;
        }

        String sCd = CCSICommon.CN_SYORI_STS_KBN + syoriStsKbn;
        String sStatusNm = ccSICommon.getSirName(sCd);

        return sStatusNm;
    }

    /**
     * 商品名取得処理
     * <p>
     * 機能概要：<br>
     * 商品名の取得を行います。<br>
     * <p>
     * 
     * @param sShnCd 商品コード
     * @param sNhnDate ：納品日
     * @return 商品名
     */
    private String getShnNm(String sShnCd, String sNhnDate) {
        String sRet = EMPTY;
        if (sShnCd.trim().length() == 0) {
            return EMPTY;
        }
        if (sNhnDate.trim().length() == 0) {
            // 納品日が空白ならシステム日付を使用する
            String sSysDay = CCDateUtil.getSysDateFormat(CCDateUtil.DATETIME_FORMAT_DATE_YYYYMMDD);
            // 基本情報Ｍよりセット
            sRet = ccSICommon.getComKihonName(sShnCd, sSysDay).trim();
        } else {
            // 基本情報Ｍよりセット
            sRet = ccSICommon.getComKihonName(sShnCd, sNhnDate).trim();
        }
        return sRet;
    }

    /**
     * 代表取引先コード取得処理.
     * <p>
     * 機能概要：<br>
     * 代表取引先コードを取得します。<br>
     * <p>
     * 
     * @param torihiki 取引先コード
     * @return 代表取引先コード
     */

    private String getMaintori(String torihiki) {
        return torihiki.substring(0, 6) + "000";
    }

    /**
     * メーカーコード取得処理<BR>
     * 
     * @param janCd 商品コード
     * @param sHatDd 発行日
     * @return String メーカーコード
     **/
    private String getMkrCd(String janCd, String sHatDd) {
        if (CCStringUtil.isEmpty(janCd) || CCStringUtil.isEmpty(sHatDd)) {
            return EMPTY;
        }

        M007kijmExample m007kijmExample = new M007kijmExample();
        m007kijmExample.setSearchDate(sHatDd);
        m007kijmExample.createCriteria().andJanCdEqualTo(ccSICommon.cnvJanToJan13(janCd));

        List<M007kijm> m007kijmList = new ArrayList<M007kijm>();
        m007kijmList = m007kijmMapper.selectByExample(m007kijmExample);
        if (m007kijmList.size() > 0) {
            return m007kijmList.get(0).getMkrCd();
        } else {
            return EMPTY;
        }
    }

    /**
     * Create condition form from result form.
     * 
     * @param form 資料 Sijp0010ResultForm
     * @return Sijp0010CondForm
     */
    private Sijp0010CondForm createCondForm(Sijp0010ResultForm form) {
        Sijp0010CondForm cond = new Sijp0010CondForm();
        BeanUtils.copyProperties(form, cond);
        return cond;
    }
}
