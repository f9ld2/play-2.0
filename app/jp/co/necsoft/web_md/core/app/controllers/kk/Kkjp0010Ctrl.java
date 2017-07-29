// /////////////////////////////////////////////////////////////////////
// Copyright(C) 2015 NEC Solution Innovators, Ltd. All Rights Reserved.
// /////////////////////////////////////////////////////////////////////

/*
 * ==================================================================== 機能名称 : 支払金額入力 改版履歴 Rev. 改版年月日 改版者名 内容 1.0
 * 2014.04.07 Hungtb 新規作成 ===================================================================
 */
package jp.co.necsoft.web_md.core.app.controllers.kk;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import jp.co.necsoft.web_md.common.ChaseException;
import jp.co.necsoft.web_md.common.mybatis.dao.MyBatisSqlMapDao;
import jp.co.necsoft.web_md.core.app.dto.kk.K003rshrExecuteResult;
import jp.co.necsoft.web_md.core.app.dto.kk.Kkjp0010Tri;
import jp.co.necsoft.web_md.core.app.forms.kk.Kkjp0010CondForm;
import jp.co.necsoft.web_md.core.app.forms.kk.Kkjp0010ResultForm;
import jp.co.necsoft.web_md.core.common.CCMessageConst;
import jp.co.necsoft.web_md.core.common.CCSecurityAuthenticator;
import jp.co.necsoft.web_md.core.common.CCSystemContext;
import jp.co.necsoft.web_md.core.common.biz.kk.CCKK0010Tax;
import jp.co.necsoft.web_md.core.common.biz.kk.CCKKBeans;
import jp.co.necsoft.web_md.core.common.biz.kk.CCKKCommon;
import jp.co.necsoft.web_md.core.common.biz.kk.CCKKConst;
import jp.co.necsoft.web_md.core.common.biz.kk.CCKKEditDB;
import jp.co.necsoft.web_md.core.common.forms.ErrorInfo;
import jp.co.necsoft.web_md.core.common.forms.ErrorResponse;
import jp.co.necsoft.web_md.core.common.mybatis.dao.K002kruiMapper;
import jp.co.necsoft.web_md.core.common.mybatis.dao.K003rshrMapper;
import jp.co.necsoft.web_md.core.common.mybatis.dto.K002krui;
import jp.co.necsoft.web_md.core.common.mybatis.dto.K002kruiExample;
import jp.co.necsoft.web_md.core.common.mybatis.dto.K003rshr;
import jp.co.necsoft.web_md.core.common.mybatis.dto.K003rshrExample;
import jp.co.necsoft.web_md.core.common.mybatis.dto.K008trhk;
import jp.co.necsoft.web_md.core.common.mybatis.dto.M011trim;
import jp.co.necsoft.web_md.core.common.utils.CCDateUtil;
import jp.co.necsoft.web_md.core.common.utils.CCNumberUtil;
import jp.co.necsoft.web_md.core.common.utils.CCStringUtil;

import org.mybatis.guice.transactional.Transactional;

import play.data.Form;
import play.i18n.Messages;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;

/**
*支払金額入力
*のControllerクラス
*
*/
@Security.Authenticated(CCSecurityAuthenticator.class)
public class Kkjp0010Ctrl extends Controller {

    /** COM_INPUT_TYPE = "1" */
    private static final String COM_INPUT_TYPE = "1";

    /** KANJYO_KBN.SHR_KANJYO = "1" */
    private static final String KANJYO_KBN_SHR_KANJYO = "1";

    /** AUTO_NO_DEN_KBN = "K1" */
    private static final String AUTO_NO_DEN_KBN = "K1";

    /** Empty string */
    private static final String EMPTY = "";

    /** SHR_KBN_INSERT = 1 */
    private static final int SHR_KBN_INSERT = 1;
    /** SHR_KBN_UPDATE = 2 */
    private static final int SHR_KBN_UPDATE = 2;
    /** SHR_KBN_DELETE = 9 */
    private static final int SHR_KBN_DELETE = 9;

    /** TAX_KBN_DELETE = "9" */
    private static final String TAX_KBN_DELETE = "9";
    /** TAX_KBN_5 = "5" */
    private static final String TAX_KBN_5 = "5";
    /** ACT_KBN_INSERT = 1 */
    private static final int ACT_KBN_INSERT = 1;
    /** ACT_KBN_UPDATE = 2 */
    private static final int ACT_KBN_UPDATE = 2;

    /** cmJKKCommon: common control. */
    @Inject
    private CCKKCommon cmJKKCommon;
    /** cmJKKEditDB: common control. */
    @Inject
    private CCKKEditDB cmJKKEditDB;
    /** errRes: error response. */
    @Inject
    private ErrorResponse errRes;
    /** errRes: error response. */
    @Inject
    private ErrorResponse warnRes;
    /** context */
    @Inject
    private CCSystemContext context;
    /** dao: DAO for pure sql mapping. */
    @Inject
    private MyBatisSqlMapDao dao;
    /** k008trhkMapper: dao mapper for table k008trhk. */
    @Inject
    private K003rshrMapper k003rshrMapper;
    /** K002kruiMapper: dao mapper for table K002krui. */
    @Inject
    private K002kruiMapper k002kruiMapper;

    /**
    * 引数とリクエスト(パラメータ)を条件にDBからデータ(複数件)を検索し返却する。*
    * @return クライアントへ返却する結果
    */
    public Result query() {
        Form<Kkjp0010CondForm> emptyForm = new Form<Kkjp0010CondForm>(Kkjp0010CondForm.class);
        Form<Kkjp0010CondForm> reqForm = emptyForm.bindFromRequest();
        if (reqForm.hasErrors()) {
            errRes.addErrorInfo(CCMessageConst.MSG_KEY_VALIDATION_ERROR);
            errRes.addErrorInfo(reqForm.errors());
            return badRequest(Json.toJson(errRes));
        } else {
            Kkjp0010CondForm req = reqForm.get();

            // 2．データ取得
            int shoriKbn = SHR_KBN_INSERT;
            List<K003rshrExecuteResult> queryData = new ArrayList<K003rshrExecuteResult>();

            if (!isEmpty(req.getShrSyoriNo())) {
                queryData = execute(req.getKaisyaCd(), req.getShrSyoriNo());

                if (queryData.size() == 0) {
                    // 2-1．取得結果がない場合：
                    // 処理モード変数に"1"(新規登録)をセット
                    shoriKbn = SHR_KBN_INSERT;
                } else {
                    // 2-2．取得した結果がある場合：
                    // 取得したデータを「支払データ変数」に格納して置く。
                    // 処理モード変数に"2"(更新)をセット
                    shoriKbn = SHR_KBN_UPDATE;
                }
            }

            // 3．[排他キーコード]
            if (shoriKbn == SHR_KBN_INSERT) {
                // 3-1．入力されている処理No.がDBに存在するかチェック
                String keyCode = "";
                keyCode = cmJKKCommon.getPresentNoAndUpdSaiban(AUTO_NO_DEN_KBN);

                if (EMPTY.equals(keyCode)) {
                    errRes.addErrorInfo(CCMessageConst.MSG_KEY_PROCESS_GET_PRESENT_NO_FAIL);
                    return badRequest(Json.toJson(errRes));
                } else if (cmJKKEditDB.isK003RSHR(keyCode)) {
                    keyCode = cmJKKCommon.getPresentNoAndUpdSaiban(AUTO_NO_DEN_KBN);
                    if (EMPTY.equals(keyCode)) {
                        errRes.addErrorInfo(CCMessageConst.MSG_KEY_PROCESS_GET_PRESENT_NO_FAIL);
                        return badRequest(Json.toJson(errRes));
                    }
                }
                req.setShrSyoriNo(keyCode);

                // 1．項目の入力チェック
                // 取引項目コードチェック：
                boolean isValid = validateForm(req, SHR_KBN_INSERT);

                // Return data
                Kkjp0010ResultForm form = new Kkjp0010ResultForm();
                form.setShrSyoriNo(req.getShrSyoriNo());
                form.setShrDate(req.getShrDate());
                form.setSimeDate(req.getSimeDate());
                form.setKaisyaCd(req.getKaisyaCd());
                form.setKakToriKmk(req.getKakToriKmk());
                form.setTekiyo(req.getTekiyo());
                form.setToriNm(req.getToriNm());

                if (!isValid) {
                    ErrorInfo err = errRes.getErrorInfo(CCMessageConst.MSG_KEY_VALIDATION_ERROR);
                    errRes.getErrors().add(0, err);
                    form.setErrRes(errRes);
                }
                List<Kkjp0010ResultForm> resultList = new ArrayList<Kkjp0010ResultForm>();
                resultList.add(form);
                return ok(Json.toJson(resultList));
            } else {
                // Return data
                Kkjp0010ResultForm form = new Kkjp0010ResultForm();
                // for header data
                if (queryData.size() > 0) {
                    K003rshrExecuteResult record = queryData.get(0);

                    form.setShrSyoriNo(CCStringUtil.trimBoth(record.getShrSyoriNo()));
                    form.setShrDate(CCStringUtil.trimBoth(record.getShrDate()));
                    form.setSimeDate(CCStringUtil.trimBoth(record.getSimeDate()));
                    form.setKaisyaCd(CCStringUtil.trimBoth(record.getKaisyaCd()));
                    form.setKakToriKmk(CCStringUtil.trimBoth(record.getKakToriKmk()));
                    form.setTekiyo(CCStringUtil.trimBoth(record.getTekiyo()));
                    form.setToriNm(CCStringUtil.trimBoth(record.getToriNm()));
                }

                List<String> mainToriCds = new ArrayList<String>();
                form.setTriArea(convertData(queryData, mainToriCds));

                List<Kkjp0010ResultForm> resultList = new ArrayList<Kkjp0010ResultForm>();
                resultList.add(form);
                return ok(Json.toJson(resultList));
            }
        }
    }

    /**
    * 引数とリクエスト(ボディ)からレコードを編集しDBに登録(論理)する。*
    * @return クライアントへ返却する結果
    */
    public Result save() {
        Form<Kkjp0010ResultForm> emptyForm = new Form<Kkjp0010ResultForm>(Kkjp0010ResultForm.class);
        Form<Kkjp0010ResultForm> reqForm = emptyForm.bindFromRequest();
        if (reqForm.hasErrors()) {
            errRes.addErrorInfo(CCMessageConst.MSG_KEY_VALIDATION_ERROR);
            errRes.addErrorInfo(reqForm.errors());
            return badRequest(Json.toJson(errRes));
        } else {
            Kkjp0010ResultForm req = reqForm.get();

            // 1．項目の入力チェック
            // 取引項目コードチェック：
            Kkjp0010CondForm cond = getCondForm(req);
            boolean isValid = validateForm(cond, SHR_KBN_INSERT);
            if (!isValid) {
                ErrorInfo err = errRes.getErrorInfo(CCMessageConst.MSG_KEY_VALIDATION_ERROR);
                errRes.getErrors().add(0, err);
                return badRequest(Json.toJson(errRes));
            } else {
                req.setTaxKbn(cond.getTaxKbn());
            }

            if (cmJKKEditDB.isK003RSHR(req.getShrSyoriNo())) {
                // [S5132]
                errRes.addErrorInfo("shrSyoriNo", CCMessageConst.MSG_KEY_INPUT_DATA_IS_EXIST);
                ErrorInfo err = errRes.getErrorInfo(CCMessageConst.MSG_KEY_VALIDATION_ERROR);
                errRes.getErrors().add(0, err);
                return badRequest(Json.toJson(errRes));
            }

            List<HashMap<String, Boolean>> errorArray = new ArrayList<HashMap<String, Boolean>>();
            List<String> mainToriCds = new ArrayList<String>();
            for (int i = 0; i < req.getTriArea().size(); i++) {
                errorArray.add(new HashMap<String, Boolean>());
                errorArray.get(i).put("rowError", false);
                mainToriCds.add(req.getTriArea().get(i).getMainToriCd() + CCKKBeans.CON_TORIHIKISAKI_CODE);
            }
            // 1．グリット入力データのチェック
            // 処理③：グリット入力データのチェックを行う
            boolean flag = validateGridData(req, errorArray, mainToriCds);
            // チェックフラグがfalse場合：
            if (!flag) {
                // 保存ボタン処理を終了する。
                errRes.addErrorInfo(CCMessageConst.MSG_KEY_VALIDATION_ERROR);
                for (HashMap<String, Boolean> map : errorArray) {
                    // Clear warning
                    map.put("mainToriCd", false);
                    map.put("sotoTaxKin", false);
                }
                errRes.setGridErrors(errorArray);
                return badRequest(Json.toJson(errRes));
            } else if (warnRes != null && warnRes.getErrors() != null && warnRes.getErrors().size() > 0) {
                // Show warning
                warnRes.setGridErrors(errorArray);
                return ok(Json.toJson(warnRes));
            }

            // 2．新規登録処理、 クライアントからサーバにデータをPOSTする。
            // 2-1．トランザクション初期化。

            insertData(cond, req.getTriArea());

            // 2-3．トランザクションコミットロールバック実行

            return created();
        }
    }

    /**
    * 引数とリクエスト(ボディ)からレコードを編集しDBの該当データを更新(論理)する。*
    * @return クライアントへ返却する結果
    */
    public Result update() {
        Form<Kkjp0010ResultForm> emptyForm = new Form<Kkjp0010ResultForm>(Kkjp0010ResultForm.class);
        Form<Kkjp0010ResultForm> reqForm = emptyForm.bindFromRequest();
        if (reqForm.hasErrors()) {
            errRes.addErrorInfo(CCMessageConst.MSG_KEY_VALIDATION_ERROR);
            errRes.addErrorInfo(reqForm.errors());
            return badRequest(Json.toJson(errRes));
        } else {
            Kkjp0010ResultForm req = reqForm.get();

            // 1．項目の入力チェック
            // 取引項目コードチェック：
            Kkjp0010CondForm cond = getCondForm(req);
            boolean isValid = validateForm(cond, SHR_KBN_UPDATE);
            if (!isValid) {
                ErrorInfo err = errRes.getErrorInfo(CCMessageConst.MSG_KEY_VALIDATION_ERROR);
                errRes.getErrors().add(0, err);
                return badRequest(Json.toJson(errRes));
            } else {
                req.setTaxKbn(cond.getTaxKbn());
            }

            // 支払ファイルより対象データを取得する // doUpdateFase1() - 973
            List<K003rshrExecuteResult> queryData = execute(req.getKaisyaCd(), req.getShrSyoriNo());
            if (queryData.size() <= 0) {
                // 対象データがありません。
                // putErrMsg("C0105", "inpSyoriNo");
                errRes.addErrorInfo("shrSyoriNo", CCMessageConst.MSG_KEY_NO_FOUND_DATA);
                ErrorInfo err = errRes.getErrorInfo(CCMessageConst.MSG_KEY_VALIDATION_ERROR);
                errRes.getErrors().add(0, err);
                return badRequest(Json.toJson(errRes));
            }

            // Merge data
            List<String> mainToriCds = new ArrayList<String>();
            List<Kkjp0010Tri> triArea = convertData(queryData, mainToriCds);
            List<Kkjp0010Tri> updateTriArea = req.getTriArea();
            List<Kkjp0010Tri> mergeArea = new ArrayList<Kkjp0010Tri>();

            int insCount = 0;
            for (Kkjp0010Tri row : updateTriArea) {
                if (row.getActKbn() == ACT_KBN_INSERT) {
                    mergeArea.add(row);
                    mainToriCds.add(row.getMainToriCd() + CCKKBeans.CON_TORIHIKISAKI_CODE);
                    insCount += 1;
                }
            }
            mergeArea.addAll(triArea);
            for (Kkjp0010Tri row : updateTriArea) {
                if (row.getActKbn() == ACT_KBN_UPDATE) {
                    mergeArea.set(row.getRowNo() - 1 + insCount, row);
                }
            }
            req.setTriArea(mergeArea);

            List<HashMap<String, Boolean>> errorArray = new ArrayList<HashMap<String, Boolean>>();
            int size = mergeArea.size();
            for (int i = 0; i < size; i++) {
                errorArray.add(new HashMap<String, Boolean>());
                errorArray.get(i).put("rowError", false);
            }
            // 1．グリット入力データのチェック
            // 処理③：グリット入力データのチェックを行う
            boolean flag = validateGridData(req, errorArray, mainToriCds);
            // チェックフラグがfalse場合：
            if (!flag) {
                // 保存ボタン処理を終了する。
                errRes.addErrorInfo(CCMessageConst.MSG_KEY_VALIDATION_ERROR);
                for (HashMap<String, Boolean> map : errorArray) {
                    // Clear warning
                    map.put("mainToriCd", false);
                    map.put("sotoTaxKin", false);
                }
                errRes.setGridErrors(errorArray);
                return badRequest(Json.toJson(errRes));
            } else if (warnRes != null && warnRes.getErrors() != null && warnRes.getErrors().size() > 0) {
                // Show warning
                warnRes.setGridErrors(errorArray);
                return ok(Json.toJson(warnRes));
            }
            // 3．更新処理、クライアントからサーバにデータをPUTする。
            // 3-1．トランザクション初期化。
            updateData(cond, req.getTriArea());
        }
        return ok();
    }

    /**
    * 引数とリクエスト(パラメータ)を条件にDBの該当データを削除(論理)する*
    * @return クライアントへ返却する結果
    */
    public Result delete() {
        Form<Kkjp0010CondForm> emptyForm = new Form<Kkjp0010CondForm>(Kkjp0010CondForm.class);
        Form<Kkjp0010CondForm> reqForm = emptyForm.bindFromRequest();

        if (reqForm.hasErrors()) {
            errRes.addErrorInfo(CCMessageConst.MSG_KEY_VALIDATION_ERROR);
            errRes.addErrorInfo(reqForm.errors());
            return badRequest(Json.toJson(errRes));
        } else {
            // 1．トランザクション初期化。
            Kkjp0010CondForm req = reqForm.get();

            // 1．項目の入力チェック
            // 取引項目コードチェック：
            boolean isValid = validateForm(req, SHR_KBN_DELETE);
            if (!isValid) {
                ErrorInfo err = errRes.getErrorInfo(CCMessageConst.MSG_KEY_VALIDATION_ERROR);
                errRes.getErrors().add(0, err);
                return badRequest(Json.toJson(errRes));
            }

            // 支払ファイルより対象データを取得する // doUpdateFase1() - 973
            Map<String, String> params = new HashMap<String, String>();
            params.put("shrSyoriNo", req.getShrSyoriNo());
            params.put("kaisyaCd", req.getKaisyaCd());

            Integer count = dao.selectOne("selectK003rshrK008trhk02", params);
            if (count == null || count < 0) {
                // 対象データがありません。
                // putErrMsg("C0105", "inpSyoriNo");
                errRes.addErrorInfo("shrSyoriNo", CCMessageConst.MSG_KEY_NO_FOUND_DATA);
                ErrorInfo err = errRes.getErrorInfo(CCMessageConst.MSG_KEY_VALIDATION_ERROR);
                errRes.getErrors().add(0, err);
                return badRequest(Json.toJson(errRes));
            }

            deleteData(req.getKaisyaCd(), req.getShrSyoriNo());

            // 3．トランザクションコミットロールバック実行
            return ok();
        }
    }

    /**
     * 
     * @return クライアントへ返却する結果
     */
    public Result valid() {
        Form<Kkjp0010ResultForm> emptyForm = new Form<Kkjp0010ResultForm>(Kkjp0010ResultForm.class);
        Form<Kkjp0010ResultForm> reqForm = emptyForm.bindFromRequest();

        if (reqForm.hasErrors()) {
            errRes.addErrorInfo(CCMessageConst.MSG_KEY_VALIDATION_ERROR);
            errRes.addErrorInfo(reqForm.errors());
            return badRequest(Json.toJson(errRes));
        } else {
            // 1．トランザクション初期化。
            Kkjp0010ResultForm req = reqForm.get();
            if (isEmpty(req.getSimeDate()) || req.getTriArea() == null || req.getTriArea().size() == 0) {
                return ok();
            } else {
                Kkjp0010Tri row = req.getTriArea().get(0);
                Long shrKin = row.getShrKin();
                Long sotoTaxKin = row.getSotoTaxKin();
                String kakToriKmk = req.getKakToriKmk();

                if (!CCStringUtil.isEmpty(kakToriKmk) && sotoTaxKin == null && shrKin != null) {
                    K008trhk comK008TRHK = cmJKKCommon.getKakToriByCodeWithKaisya(kakToriKmk, req.getKaisyaCd());
                    if (comK008TRHK != null && KANJYO_KBN_SHR_KANJYO.equals(comK008TRHK.getKanjoKbn())) {
                        String taxKbn = comK008TRHK.getTaxKbn();
                        if (isEmpty(taxKbn)) {
                            taxKbn = TAX_KBN_5;
                        }
                        long kingakuToTax = cmJKKCommon.getKingakuToTax(shrKin, taxKbn);
                        row.setSotoTaxKin(kingakuToTax);
                        if (isInternalTax(taxKbn)) {
                            row.setShrKin(shrKin - kingakuToTax);
                            row.setShrKinAll(shrKin);
                        } else {
                            row.setShrKinAll(shrKin + kingakuToTax);
                        }
                        return ok(Json.toJson(req));
                    }
                }
                return ok();
            }
        }
    }

    /**
     * @param kaisyaCd 会社
     * @param shrSyoriNo 処理NO
     */
    @Transactional
    public void deleteData(String kaisyaCd, String shrSyoriNo) {
        // 2．K003rshrMapperのdeleteByExampleを使用する。
        K003rshrExample example = new K003rshrExample();
        example.createCriteria().andShrSyoriNoEqualTo(shrSyoriNo).andKaisyaCdEqualTo(kaisyaCd)
                .andJotaiKbnNotEqualTo(CCKKCommon.JYOTAI_KBN.KBN_SHRZUMI + EMPTY);
        int result = k003rshrMapper.deleteByExample(example);
        if (result <= 0) {
            throw new ChaseException(Messages.get(CCMessageConst.MSG_KEY_EXCEPTION_DELETE_NO_RECORD));
        }
    }

    /**
     * 
     * @param cond 資料
     * @param data data
     */
    @Transactional
    public void updateData(Kkjp0010CondForm cond, List<Kkjp0010Tri> data) {
        String kaisyaCd = cond.getKaisyaCd();
        String shrSyoriNo = cond.getShrSyoriNo();

        String cmnTantoCd = context.getTantoCode();
        String termName = context.getTermName();
        if (termName.getBytes().length > 8) {
            termName = new String(Arrays.copyOf(termName.getBytes(), 8));
        }
        String cmnInsdd = CCDateUtil.getSysDateFormat(CCDateUtil.DATETIME_FORMAT_DATE_YYYYMMDD);
        String cmnUpdtime = CCDateUtil.getSysDateFormat(CCDateUtil.DATETIME_FORMAT_TIME);

        // Delete data
        deleteData(kaisyaCd, shrSyoriNo);

        for (Kkjp0010Tri row : data) {
            insertRecord(cond, row, cmnTantoCd, termName, cmnInsdd, cmnUpdtime);
        }
    }

    /**
     * Process 4: 処理④：支払ファイルデータ登録
     * @param form 資料
     * @param data data
     */
    @Transactional
    public void insertData(Kkjp0010CondForm form, List<Kkjp0010Tri> data) {
        // 処理④：支払ファイルデータ登録
        // 「支払データ変数」の件数でのループ：
        String cmnTantoCd = context.getTantoCode();
        String termName = context.getTermName();
        if (termName.getBytes().length > 8) {
            termName = new String(Arrays.copyOf(termName.getBytes(), 8));
        }
        String cmnInsdd = CCDateUtil.getSysDateFormat(CCDateUtil.DATETIME_FORMAT_DATE_YYYYMMDD);
        String cmnUpdtime = CCDateUtil.getSysDateFormat(CCDateUtil.DATETIME_FORMAT_TIME);
        for (Kkjp0010Tri row : data) {
            insertRecord(form, row, cmnTantoCd, termName, cmnInsdd, cmnUpdtime);
        }
    }

    /**
     * 
     * @param cond cond
     * @param row row
     * @param cmnTantoCd cmnTantoCd
     * @param cmnTermName cmnTermName
     * @param cmnInsdd cmnInsdd
     * @param cmnUpdtime cmnUpdtime
     */
    private void insertRecord(Kkjp0010CondForm cond, Kkjp0010Tri row, String cmnTantoCd, String cmnTermName,
            String cmnInsdd, String cmnUpdtime) {
        // 1-1．合計金額の算出処理

        // 税区分
        String taxkbn = cond.getTaxKbn();

        // 1-3．レコードの該当データをデータベースに登録：
        K003rshr record = new K003rshr();
        record.setKaisyaCd(cond.getKaisyaCd());
        record.setShrSyoriNo(cond.getShrSyoriNo());
        record.setMainToriCd(row.getMainToriCd() + CCKKBeans.CON_TORIHIKISAKI_CODE);
        record.setKakToriKmk(cond.getKakToriKmk());
        if (!isEmpty(cond.getShrDate())) {
            record.setShrDate(cond.getShrDate());
        }
        if (!isEmpty(cond.getSimeDate())) {
            record.setSimeDate(cond.getSimeDate());
        }
        record.setFriEndDate(CCKKBeans.CON_INSERT_NULL);
        record.setShrDenKbn(row.getShrDenKbn());

        record.setSotoGenkAll(row.getShrKin());
        record.setNoGenkAll(TAX_KBN_DELETE.equals(taxkbn) ? row.getShrKin() : 0L);
        record.setShrKin(row.getShrKin());
        record.setSotoTaxKin(row.getSotoTaxKin());
        record.setShrKinAll(row.getShrKin() + row.getSotoTaxKin());

        record.setJotaiKbn(row.getJotaiKbn());
        record.setGetujYm(CCKKBeans.CON_INSERT_NULL);
        record.setTekiyo(cond.getTekiyo());
        record.setInputType(COM_INPUT_TYPE);

        record.setCmnTantoCd(cmnTantoCd);

        record.setCmnTermId(cmnTermName);

        record.setCmnInsdd(cmnInsdd);
        record.setCmnUpddd(cmnInsdd);
        record.setCmnUpdtime(cmnUpdtime);

        int result = k003rshrMapper.insertSelective(record);
        if (result <= 0) {
            throw new ChaseException(Messages.get(CCMessageConst.MSG_KEY_EXCEPTION_INSERT_NO_RECORD));
        }
    }

    /**
     * @param req request form
     * @param shoriKbn process type
     * @return true or false
     */
    private boolean validateForm(Kkjp0010CondForm req, int shoriKbn) {

        boolean flag = true;

        if (req.getShrSyoriNo() == null) {
            req.setShrSyoriNo(EMPTY);
        }

        // 入力データチェック：
        // INSERT || UPDATE || DELETE
        if (shoriKbn == SHR_KBN_INSERT || shoriKbn == SHR_KBN_UPDATE || shoriKbn == SHR_KBN_DELETE) {
            //
            if (isEmpty(req.getShrSyoriNo())) {
                // 「C0012」メッセージが表示される。
                // ※メッセージ埋め込み文字：なし
                errRes.addErrorInfo("shrSyoriNo", CCMessageConst.MSG_KEY_DATA_IS_EMPTY);
                flag = false;
            }
            // ヘッダエリア.支払予定日 == ""の場合
            if (isEmpty(req.getShrDate())) {
                // 「C0012」メッセージが表示される。
                // ※メッセージ埋め込み文字：なし
                errRes.addErrorInfo("shrDate", CCMessageConst.MSG_KEY_DATA_IS_EMPTY);
                flag = false;
            } else {
                String shrDate = CCStringUtil.suppZero(req.getShrDate(), 8);
                if (!CCDateUtil.isDate(shrDate)) {
                    // [C0011] エラーメッセージ表示、及びログ出力を行う
                    // 日付に誤りがあります。
                    errRes.addErrorInfo("shrDate", CCMessageConst.MSG_KEY_DATE_ERROR);
                    flag = false;
                }
            }

            // ヘッダエリア.締日 == ""の場合
            if (isEmpty(req.getSimeDate())) {
                // 「C0012」メッセージが表示される。
                // ※メッセージ埋め込み文字：なし
                errRes.addErrorInfo("simeDate", CCMessageConst.MSG_KEY_DATA_IS_EMPTY);
                flag = false;
            } else {
                String simeDate = CCStringUtil.suppZero(req.getSimeDate(), 8);
                if (!CCDateUtil.isDate(simeDate)) {
                    // [C0011] エラーメッセージ表示、及びログ出力を行う
                    // 日付に誤りがあります。
                    errRes.addErrorInfo("simeDate", CCMessageConst.MSG_KEY_DATE_ERROR);
                    flag = false;
                }
            }

            // ヘッダエリア.摘要 == ""の場合
            if (isEmpty(req.getTekiyo())) {
                // 「C0012」メッセージが表示される。
                // ※メッセージ埋め込み文字：なし
                errRes.addErrorInfo("tekiyo", CCMessageConst.MSG_KEY_DATA_IS_EMPTY);
                flag = false;
            } else {
                int byteLen = -1;
                byteLen = CCStringUtil.getByteLen(req.getTekiyo());
                int strLen = req.getTekiyo().length();
                if (byteLen != strLen) {
                    // addMessageInfo( "C0255", sMsg_array, "parent.frames.tagframe.document.all.inpTekiyo" ) => C9053
                    errRes.addErrorInfo("tekiyo", CCMessageConst.MSG_KEY_INPUT_TEXT_NOT_VALID);
                    return false;
                }
            }

            if (!CCStringUtil.isEmpty(req.getKakToriKmk())) {
                K008trhk comK008TRHK =
                        cmJKKCommon.getKakToriByCodeWithKaisya(req.getKakToriKmk(), req.getKaisyaCd());
                // 「取得レコードの配列」が[null]場合
                if (comK008TRHK == null) {
                    // 「K1003」メッセージが表示される。
                    // ※メッセージ埋め込み文字：なし
                    errRes.addErrorInfo("kakToriKmk", CCMessageConst.MSG_KEY_TORIHIKI_NOT_EXISTS);
                    flag = false;
                } else if (!KANJYO_KBN_SHR_KANJYO.equals(comK008TRHK.getKanjoKbn())) {
                    // 「取得レコードの配列」[cmJKKCommon.COLNO_JK008TRHK.KANJO_KBN]が「KANJYO_KBN.SHR_KANJYO」以外場合
                    // 「K1014」メッセージが表示される。
                    // ※メッセージ埋め込み文字：なし
                    errRes.addErrorInfo("kakToriKmk", CCMessageConst.MSG_KEY_KANJO_KBN_NOT_EXISTS);
                    flag = false;
                } else {
                    // update taxKbn
                    String taxKbn = comK008TRHK.getTaxKbn();
                    req.setTaxKbn(taxKbn);
                }
            } else {
                errRes.addErrorInfo("kakToriKmk", CCMessageConst.MSG_KEY_DATA_IS_EMPTY);
                flag = false;
            }
        }
        return flag;
    }

    /**
     * Process 3.
     * @param req 取引先
     * @param errorArray error for grid
     * @param mainToriCds list maintoriCd
     * @return true or false
     */
    private boolean validateGridData(Kkjp0010ResultForm req, List<HashMap<String, Boolean>> errorArray,
            List<String> mainToriCds) {
        String kaisyaCd = req.getKaisyaCd();
        String shrDateIn = req.getShrDate();
        String simeDateIn = req.getSimeDate();
        String taxKbn = req.getTaxKbn();
        List<Kkjp0010Tri> data = req.getTriArea();
        Map<String, String> jotaiNmMap = new HashMap<String, String>();
        Map<String, M011trim> trimMap = getComM011trim(shrDateIn, mainToriCds);
        Map<String, Boolean> isK002Map = new HashMap<String, Boolean>();
        if (!req.isSkipWarning()) {
            isK002Map = getExecK002KRUI(kaisyaCd, mainToriCds, simeDateIn);
        }

        boolean hasData = false;
        boolean flag = true;
        boolean flagWarn = true;
        int i = 0, j = 0;
        int rowError = -1;
        int rowWarn = -1;
        for (; i < data.size(); i++) {
            Kkjp0010Tri record = data.get(i);
            if (record == null) {
                continue;
            }
            String jotaiKbn = record.getJotaiKbn();
            Long shrKin = record.getShrKin();
            Long shrKinAll = record.getShrKinAll();
            Long sotoTaxKin = record.getSotoTaxKin();

            if (isEmpty(jotaiKbn) && (shrKin == null) && (shrKinAll == null) && (shrDateIn == null)
                    && sotoTaxKin == null) {
                // 1．グリッドの全てレコードが以下のフィールドにて全て妥当の場合エラーとする：
                continue;
            } else {
                hasData = true;
                // 2．処理1ではエラーがない場合以下の処理を行う：
                // チェックフラグをtrueで初期化。
                // 2-1．確定区分チェック：
                if (isEmpty(jotaiKbn)) {
                    // 「C0012」メッセージが表示される。
                    errRes.addErrorInfo("jotaiKbn", CCMessageConst.MSG_KEY_DATA_IS_EMPTY);
                    flag = false;
                } else {
                    if (jotaiNmMap.containsKey(record.getJotaiKbn())) {
                        record.setNm(jotaiNmMap.get(record.getJotaiKbn()));
                    } else {
                        String jotaiName = getJyotaiKubun(EMPTY, record.getJotaiKbn());
                        jotaiNmMap.put(record.getJotaiKbn(), jotaiName);

                        record.setNm(jotaiName);
                    }

                    if (record.getNm() == null || EMPTY.equals(record.getNm())
                            || (CCKKCommon.JYOTAI_KBN.KBN_SHRZUMI + EMPTY).equals(record.getJotaiKbn())) {
                        // 「K1013」メッセージが表示される。
                        errRes.addErrorInfo("jotaiKbn", CCMessageConst.MSG_KEY_KANJO_KBN_SHR_KANJYO_NOT_EXISTS);
                        flag = false;
                    }
                }
                // 2-2．取引先コードチェック：
                if (record.getMainToriCd() == null || EMPTY.equals(record.getMainToriCd())) {
                    // 2-2-1
                    // 「C0012」メッセージが表示される。
                    errRes.addErrorInfo("mainToriCd", CCMessageConst.MSG_KEY_DATA_IS_EMPTY);
                    flag = false;
                } else {
                    j = i + 1;
                    // 2-2-2
                    for (; j < data.size(); j++) {
                        if (record.getMainToriCd().equals(data.get(j).getMainToriCd())) {
                            // 「C0020」メッセージが表示される。
                            errRes.addErrorInfo("mainToriCd", CCMessageConst.MSG_KEY_DATA_IS_DUPLICATE);
                            flag = false;
                        }
                    }

                    // 2-2-3．取引先マスタより取引先データ存在チェック：
                    M011trim trim = trimMap.get(record.getMainToriCd() + CCKKBeans.CON_TORIHIKISAKI_CODE);
                    if (trim == null) {
                        record.setToriNm(EMPTY);
                        record.setShrDenKbn(EMPTY);
                        // 「K1016」メッセージが表示される。
                        errRes.addErrorInfo("mainToriCd", CCMessageConst.MSG_KEY_DATA_NOT_EXISTS);
                        flag = false;
                    } else {
                        // グリッド(index).取引先名称にデータ配列[mJKKCommon.COLNO_JM011TRIM.TRI_NM]をセット
                        record.setToriNm(trim.getTriNm());

                        String kaiSeisanKbn = trim.getKaiSeisanKbn();
                        if ((String.valueOf(CCKKConst.SEISAN_KBN.KBN_TEGATA)).equals(kaiSeisanKbn)) {
                            record.setShrDenKbn(CCKKCommon.CON_SHRDEN.KBN_TEGATA);
                        } else {
                            record.setShrDenKbn(CCKKCommon.CON_SHRDEN.KBN_FURI_OL);
                        }

                        String hatTriKbn = trim.getHatTriKbn();
                        if (CCKKCommon.HAT_TRI_KBN.TRI_SYOCNT.equals(hatTriKbn)) {
                            // 「K1041」メッセージが表示される。
                            errRes.addErrorInfo("mainToriCd", CCMessageConst.MSG_KEY_HATTRIKBN_INVALID);
                            flag = false;
                        }

                        if (isEmpty(shrDateIn)) {
                            record.setShrDate(cmJKKCommon.cnvSimeDateToShrDate(simeDateIn, trim.getSimeKbn(),
                                    trim.getPayKbn()));
                        }

                        // Kkjp0010Tri row = req.getTriArea().get(i);
                        if (!req.isSkipWarning()) {
                            Boolean isExecK002KRUI =
                                    isK002Map.get(record.getMainToriCd() + CCKKBeans.CON_TORIHIKISAKI_CODE);
                            if (isExecK002KRUI == null || !isExecK002KRUI.booleanValue()) {
                                // [K1048]
                                warnRes.addErrorInfo("mainToriCd", CCMessageConst.MSG_KEY_NO_PREVIOUS_MONTH_DEAL);
                                errorArray.get(i).put("mainToriCd", true);
                                flagWarn = false;
                            }
                        }
                    }
                }

                // 2-3．外税原価合計チェック：
                // 2-3-1．グリッド(index).金額が(="") // Old
                if (shrKin == null) {
                    // 「C0012」メッセージが表示される。
                    errRes.addErrorInfo(CCMessageConst.MSG_KEY_DATA_IS_EMPTY);
                    flag = false;
                }

                // 2-4．消費税チェック：
                // 税区分取得：
                // 税区分 = 処理⑥を参照
                if (sotoTaxKin == null) {
                    // 2-4-1．グリッド(index).消費税が(="")の場合
                    if (shrKin != null && !isEmpty(taxKbn)) {
                        // グリッド(index).金額が(="")以外の場合
                        // グリッド(index).税区分が(="")以外の場合
                        long shrKinValue = shrKin;
                        long kingakuToTax = cmJKKCommon.getKingakuToTax(shrKinValue, taxKbn);
                        record.setSotoTaxKin(kingakuToTax);
                        if (isInternalTax(taxKbn)) {
                            record.setShrKin(shrKin - kingakuToTax);
                            record.setShrKinAll(shrKin);
                        } else {
                            record.setShrKinAll(shrKin + kingakuToTax);
                        }
                    }
                } else {
                    // 2-4-2．グリッド(index).消費税が(<>"")の場合
                    if (shrKin == null) {
                        // 2-4-2-1．グリッド(index).金額が(="")の場合
                        // 「K1017」メッセージが表示される。
                        errRes.addErrorInfo("shrKin", CCMessageConst.MSG_KEY_SHRKIN_NOT_EXISTS);
                        flag = false;
                    } else {
                        // 2-4-2-2．グリッド(index).金額が(<>"")の場合
                        // グリッド(index).消費税が数値場合：
                        if (!req.isSkipWarning()) {
                            long shrKinValue = shrKin;
                            if (isInternalTax(taxKbn)) {
                                shrKinValue += record.getSotoTaxKin();
                            }
                            long kingakuToTax = cmJKKCommon.getKingakuToTax(shrKinValue, taxKbn);

                            if (Math.abs(record.getSotoTaxKin() - kingakuToTax) > 100) {
                                // 「K1047」メッセージが表示される。
                                // ※メッセージ埋め込み文字：なし
                                warnRes.addErrorInfo("sotoTaxKin", CCMessageConst.MSG_KEY_SOTOTAXKIN_INVALID);
                                errorArray.get(i).put("sotoTaxKin", true);
                                flagWarn = false;
                            }
                        }
                    }
                }

                // 2-5．支払金額チェック：
                // グリッド(index).金額 + グリッド(index).消費税のサイズが(>11桁)場合
                Double sum = CCNumberUtil.addDouble(record.getShrKin(), record.getSotoTaxKin());

                if (BigDecimal.valueOf(sum).toPlainString().length() > 11) {
                    // 「C0067」メッセージが表示される。=> K1062
                    // ※メッセージ埋め込み文字：なし
                    errRes.addErrorInfo("shrKin", CCMessageConst.MSG_KEY_TAX_SUM_TOO_LARGE);
                    errRes.addErrorInfo("sotoTaxKin", CCMessageConst.MSG_KEY_TAX_SUM_TOO_LARGE);
                    flag = false;
                }
            }

            if (!flag && rowError < 0) {
                rowError = i;
                errorArray.get(i).put("rowError", true);
            }
            if (!flagWarn && rowWarn < 0) {
                rowWarn = i;
                errorArray.get(i).put("rowWarn", true);
            }
        }

        if (!hasData) {
            // 1．グリッドの全てレコードが以下のフィールドにて全て妥当の場合エラーとする：
            // 「K1015」メッセージが表示される。
            // ※メッセージ埋め込み文字：なし
            errRes.addErrorInfo(CCMessageConst.MSG_KEY_EMPTY_DATA_EXISTS);
            return false;
        } else if (!flag) {
            ErrorInfo err = errRes.getErrorInfo(CCMessageConst.MSG_KEY_VALIDATION_ERROR);
            errRes.getErrors().add(0, err);
        }

        return flag;
    }

    /**
     * 
     * @param taxKbn taxKbn
     * @return true if internal tax
     */
    private boolean isInternalTax(String taxKbn) {
        return CCKK0010Tax.ZEI_KBN.ZEI_SYU_1.equals(taxKbn) || CCKK0010Tax.ZEI_KBN.ZEI_SYU_2.equals(taxKbn)
                || CCKK0010Tax.ZEI_KBN.ZEI_SYU_3.equals(taxKbn) || CCKK0010Tax.ZEI_KBN.ZEI_SYU_4.equals(taxKbn);
    }

    /**
     * 
     * @param dataIn query data
     * @param mainToriCds list mainToriCd return
     * @return data
     */
    private List<Kkjp0010Tri> convertData(List<K003rshrExecuteResult> dataIn, List<String> mainToriCds) {
        List<Kkjp0010Tri> triArea = new ArrayList<Kkjp0010Tri>();

        if (dataIn == null || dataIn.size() == 0) {
            return triArea;
        }

        Map<String, String> jyotaiKbnNm = new HashMap<String, String>();

        int i = 0;
        for (K003rshrExecuteResult record : dataIn) {
            // for grid data
            Kkjp0010Tri tri = new Kkjp0010Tri();

            // for show
            tri.setRowNo(++i);
            tri.setActKbn(0);
            tri.setShrDate(EMPTY);
            tri.setShrDate(EMPTY);
            tri.setJotaiKbn(record.getJotaiKbn() != null ? record.getJotaiKbn().trim() : EMPTY);
            String mToriCd = record.getMainToriCd().substring(0, 6);
            mainToriCds.add(mToriCd + CCKKCommon.CON_TORIHIKISAKI_CODE);
            tri.setMainToriCd(mToriCd);

            tri.setShrKin(record.getSotoGenkAll());
            tri.setSotoTaxKin(record.getSotoTaxKin());
            tri.setShrKinAll(record.getShrKinAll());
            String jotaiKbn = record.getJotaiKbn();
            if (jyotaiKbnNm.containsKey(jotaiKbn)) {
                tri.setNm(jyotaiKbnNm.get(jotaiKbn));
            } else {
                String jotaiNm = getJyotaiKubun(record.getShrDate(), record.getJotaiKbn());
                jyotaiKbnNm.put(jotaiKbn, jotaiNm);
                tri.setNm(jotaiNm);
            }

            triArea.add(tri);
        }

        Map<String, String> toriNms = getToriNm(dataIn.get(0).getShrDate(), mainToriCds);
        for (Kkjp0010Tri tri : triArea) {
            String triNm = toriNms.get(tri.getMainToriCd() + CCKKCommon.CON_TORIHIKISAKI_CODE);
            if (CCStringUtil.isEmpty(triNm)) {
                triNm = EMPTY;
            }
            tri.setToriNm(triNm);
        }
        return triArea;
    }

    /**
     * 取引項目名称の取得.
     * 
     * @param shrDate 支払予定日
     * @param mToriCd 代表取引先コード
     * @return 代表取引先名称
     */
    private Map<String, String> getToriNm(String shrDate, List<String> mToriCd) {
        Map<String, String> triNms = cmJKKCommon.getComToriName(shrDate, mToriCd);

        return triNms;
    }

    /**
     * 
     * @param shrDate 支払予定日
     * @param jotaiKbn jotaiKbn
     * @return jyotaiKubun jyotaiKubun value
     */
    private String getJyotaiKubun(String shrDate, String jotaiKbn) {
        String jyotaiKubun = cmJKKCommon.getJyotaiKubun(shrDate, (jotaiKbn != null) ? jotaiKbn.trim() : EMPTY);
        if (jyotaiKubun != null) {
            jyotaiKubun = CCStringUtil.trimBoth(jyotaiKubun);
            return jyotaiKubun;
        } else {
            return EMPTY;
        }
    }

    /**
     * 買掛累計Fデータ取得処理.
     * <p>
     * 機能概要：<br>
     *  買掛累積Ｆより前月取引が無い取引先、残高が０の取引先はFALSE<br>
     *  を返す<br>
     * @param sKaisyaCd 会社
     * @param sToriCode 代表取引先コード
     * @param sSimeDate 締日
     * @return true or false
     */
    private Map<String, Boolean> getExecK002KRUI(String sKaisyaCd, List<String> sToriCode, String sSimeDate) {
        // 共通Beanオブジェクト作成
        String simeDate = isEmpty(sSimeDate) ? EMPTY : sSimeDate.substring(0, 6);

        K002kruiExample example = new K002kruiExample();
        example.createCriteria().andKaisyaCdEqualTo(sKaisyaCd).andMainToriCdIn(sToriCode)
                .andGetujYmEqualTo(simeDate);

        List<K002krui> k002List = k002kruiMapper.selectByExample(example);
        Map<String, Boolean> result = new HashMap<String, Boolean>();

        // EOFの場合はfalseを返す
        if (k002List.size() > 0) {
            for (K002krui row : k002List) {
                // 前回繰越金額
                Long sKingk = k002List.get(0).getZenKuriKin();
                if (sKingk != null) {
                    result.put(row.getMainToriCd(), new Boolean(true));
                }
            }
        }
        return result;
    }

    /**
     * Check null or empty string.
     * @param value String
     * @return true or falses
     */
    private boolean isEmpty(String value) {
        if (value == null) {
            return true;
        } else {
            String temp = CCStringUtil.trimBoth(value);
            return "".equals(temp);
        }
    }

    /**
     * 
     * @param form resultForm
     * @return condition form
     */
    private Kkjp0010CondForm getCondForm(Kkjp0010ResultForm form) {
        Kkjp0010CondForm cond = new Kkjp0010CondForm();
        cond.setShrSyoriNo(form.getShrSyoriNo());
        cond.setShrDate(form.getShrDate());
        cond.setSimeDate(form.getSimeDate());
        cond.setKaisyaCd(form.getKaisyaCd());
        cond.setKakToriKmk(form.getKakToriKmk());
        cond.setTekiyo(form.getTekiyo());
        cond.setToriNm(form.getToriNm());

        return cond;
    }

    /**
     * 
     * @param kaisyaCd 会社
     * @param shrSyoriNo 処理NO
     * @return List data
     */
    private List<K003rshrExecuteResult> execute(String kaisyaCd, String shrSyoriNo) {
        List<K003rshrExecuteResult> queryData = new ArrayList<K003rshrExecuteResult>();
        Map<String, String> params = new HashMap<String, String>();
        params.put("shrSyoriNo", shrSyoriNo);
        params.put("kaisyaCd", kaisyaCd);

        queryData = dao.selectMany("selectK003rshrK008trhk01", params, K003rshrExecuteResult.class);
        return queryData;
    }

    /**
     * 取引先マスタからの取引先名称取得処理.
     * <p>
     * 機能概要：<br>
     * 共通部品を使用して、取引先マスタより指定コードのデータを取得します。
     * <p> 
     * @param sDate 基準日付
     * @param sToriCode 取引先コード（９桁未満の場合、後３桁を関数内で付加する）
     * @return 取得レコードの配列
     */
    private Map<String, M011trim> getComM011trim(String sDate, List<String> sToriCode) {
        Map<String, M011trim> result = new HashMap<String, M011trim>();
        List<M011trim> data = cmJKKCommon.getComM011trim(sDate, sToriCode);

        for (M011trim row : data) {
            result.put(row.getTriCd(), row);
        }
        return result;
    }
}
