// /////////////////////////////////////////////////////////////////////
// Copyright(C) 2015 NEC Solution Innovators, Ltd. All Rights Reserved.
// /////////////////////////////////////////////////////////////////////

/*
 * ====================================================================
 * 
 * 機能名称 : 伝票明細履歴照会
 * 
 * 改版履歴
 * 
 * Rev. 改版年月日 改版者名 内容
 * 
 * 1.0 2014-03-28 TUCTV 新規作成
 * 
 * ===================================================================
 */

package jp.co.necsoft.web_md.core.app.controllers.si;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.springframework.beans.BeanUtils;

import jp.co.necsoft.web_md.common.mybatis.dao.MyBatisSqlMapDao;
import jp.co.necsoft.web_md.core.app.dto.si.S014eomrExecute;
import jp.co.necsoft.web_md.core.app.dto.si.S014eomrExecuteResult;
import jp.co.necsoft.web_md.core.app.forms.si.Sijp0130CondForm;
import jp.co.necsoft.web_md.core.app.forms.si.Sijp0130ResultForm;
import jp.co.necsoft.web_md.core.common.CCMessageConst;
import jp.co.necsoft.web_md.core.common.CCSecurityAuthenticator;
import jp.co.necsoft.web_md.core.common.CCSystemContext;
import jp.co.necsoft.web_md.core.common.biz.si.CCSICommon;
import jp.co.necsoft.web_md.core.common.biz.si.CCSIConst;
import jp.co.necsoft.web_md.core.common.forms.ErrorInfo;
import jp.co.necsoft.web_md.core.common.forms.ErrorResponse;
import jp.co.necsoft.web_md.core.common.mybatis.dao.M017meimMapper;
import jp.co.necsoft.web_md.core.common.mybatis.dto.M017meim;
import jp.co.necsoft.web_md.core.common.mybatis.dto.M017meimExample;
import jp.co.necsoft.web_md.core.common.utils.CCDateUtil;
import jp.co.necsoft.web_md.core.common.utils.CCNumberUtil;
import jp.co.necsoft.web_md.core.common.utils.CCStringUtil;
import play.data.Form;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;

/**
*伝票明細履歴照会のControllerクラス
*
*/
@Security.Authenticated(CCSecurityAuthenticator.class)
public class Sijp0130Ctrl extends Controller {
    /**
     * RECORD_MAX
     **/
    private static final int RECORD_MAX = 200;
    /** yyyyMMdd */
    private static final String DATETIME_FORMAT_DATE_YYYYMMDD = "yyyyMMdd";
    /** yyyy/MM/dd */
    private static final String DATETIME_FORMAT_DATE = "yyyy/MM/dd";

    /**
     * EMPTY_STRING
     **/
    private static final String EMPTY_STRING = "";

    /**mybatisDao*/
    @Inject
    private MyBatisSqlMapDao mybatisDao;
    /**errRes*/
    @Inject
    private ErrorResponse errRes;

    /**CCSystemContext*/
    @Inject
    private CCSystemContext context;

    /**CCSICommonn*/
    @Inject
    private CCSICommon cmJSICommon;

    /** m017meimMapper */
    @Inject
    private M017meimMapper m017meimMapper;

    /**
     * ACT_KBN_DELETE
     */
    private static final String ACT_KBN_DELETE = "9";
    /**
     * 初期表示
     * @return クライアントへ返却する結果  
     */
    public Result init() {
        Sijp0130CondForm cForm = new Sijp0130CondForm();
        String sTanto = context.getTantoCode();
        String tenCd = cmJSICommon.getDefaultTenpo(sTanto);
        if (tenCd.length() > 4) {
            cForm.setKaisyaCd(tenCd.substring(0, 2));
        }
        return ok(Json.toJson(cForm));
    }

    /**
     * 引数とリクエスト(パラメータ)を条件にDBからデータ(複数件)を検索し返却する。*
     * @param dpyKbn 伝区
     * @param kaisyaCd 会社コード
     * @param jigyobuCd 事業部コード
      *  @return クライアントへ返却する結果
     */
    public Result query(String dpyKbn, String kaisyaCd, String jigyobuCd) {
        @SuppressWarnings({"unchecked", "rawtypes" })
        Form<Sijp0130CondForm> emptyForm = new Form(Sijp0130CondForm.class);
        Form<Sijp0130CondForm> reqForm = emptyForm.bindFromRequest();
        if (reqForm.hasErrors()) {
            errRes.addErrorInfo(CCMessageConst.MSG_KEY_VALIDATION_ERROR);
            errRes.addErrorInfo(reqForm.errors());
            return badRequest(Json.toJson(errRes));
        } else {
            Sijp0130CondForm req = reqForm.get();

            // dpyKbn = 34 or dpyKbn = 39
            if (CCSIConst.DEN_KBN.KBN_IDO.equals(dpyKbn)) {
                boolean flag = check3Input(req);
                if (!flag) {
                    return badRequest(Json.toJson(errRes));
                }
            }

            // doChkHead()
            boolean blErrflg = doCheckHead(req, dpyKbn, kaisyaCd, jigyobuCd);
            if (!blErrflg) {
                return badRequest(Json.toJson(errRes));
            }

            S014eomrExecute paramExec = addParameterExecute(req, dpyKbn, kaisyaCd, jigyobuCd);
            // sijp0130MapperのselectS014eomrS015tgmrS016frmrS017bhmr01を使用する。
            List<S014eomrExecuteResult> resultDatalist = new ArrayList<S014eomrExecuteResult>();

            resultDatalist =
                    mybatisDao.selectMany("selectS014eomrS015tgmrS016frmrS017bhmr01", paramExec,
                            S014eomrExecuteResult.class);

            // 対象データがありません。
            if (resultDatalist.size() == 0) {
                // 「C0105」メッセージが表示される。
                errRes.addErrorInfo(CCMessageConst.MSG_KEY_BUMON_NO_DATA);
                return badRequest(Json.toJson(errRes));
            } else if (resultDatalist.size() > RECORD_MAX) {
                errRes.addErrorInfo(CCMessageConst.MSG_KEY_PLEASE_NARROW_CONDITION);
                return badRequest(Json.toJson(errRes));
            }
            List<Sijp0130ResultForm> resultList = new ArrayList<Sijp0130ResultForm>();
            for (int i = 0; i < resultDatalist.size(); i++) {
                S014eomrExecuteResult rs = resultDatalist.get(i);
                Sijp0130ResultForm resultForm = new Sijp0130ResultForm();
                resultForm.setInsdd(CCDateUtil.getDateFormat(rs.getInsDd(), DATETIME_FORMAT_DATE));

                String dt = rs.getInsTime();
                if (!EMPTY_STRING.equals(dt)) {
                    String insTime = dt.substring(0, 2) + ":" + dt.substring(2, 4) + ":" + dt.substring(4, 6);
                    resultForm.setInstime(insTime);
                } else {
                    resultForm.setInstime("");
                }

                resultForm.setDpyNo(rs.getDpyNo());

                resultForm.setRuiKeijoYmd(CCDateUtil.getDateFormat(rs.getRuiKeijoYmd(), DATETIME_FORMAT_DATE));

                resultForm.setTenCd(rs.getTenCd());
                resultForm.setSubTenNmR1(rs.getTenNmr1());
                resultForm.setSubBmnCd(rs.getBmnCd());
                resultForm.setSubBmnNmR(rs.getBmnNmr());

                String sEdit = rs.getTorihikiCd();
                String sToriNm = rs.getTriNmr();
                resultForm.setSubTriNmR(sToriNm);

                if (sEdit.trim().length() == 9) {
                    resultForm.setSubTriCd(sEdit.substring(0, 6) + "-" + sEdit.substring(6, 9));
                } else if (sEdit.trim().length() == 7) { // 出荷店の場合
                    resultForm.setSubTriCd(sEdit.substring(0, 2) + "-" + sEdit.substring(2, 4) + "-"
                            + sEdit.substring(4, 7));
                    String sUnyoDate = context.getUnyoDate();
                    sToriNm = cmJSICommon.getComTenpoNameR(sEdit, sUnyoDate);
                }
                // 画面項目[代表取引先]の名称のトリムのLength > 0の場合、
                if (sToriNm.trim().length() > 0) { // 出荷店の場合
                    resultForm.setSubTriNmR(sToriNm);
                }

                resultForm.setNhnYoteiYmd(CCDateUtil.getDateFormat(rs.getNhnYmd(), DATETIME_FORMAT_DATE));
                resultForm.setEntryKbn(rs.getEntryKbn());
                resultForm.setDispSyoriSts(rs.getDispSyoriSts() + ':'
                        + getDispSyoriStsName(rs.getDispSyoriSts(), context.getUnyoDate()));

                resultForm.setAtoGenkgk(rs.getAtoGenkgk());
                resultForm.setMaeGenkgk(rs.getMaeGenkgk());
                resultForm.setAtoBaikgk(rs.getAtoBaikgk());
                resultForm.setMaeBakgk(rs.getMaeBaikgk());

                resultForm.setCmnTantoCd(rs.getCmnTantoCd());
                resultList.add(resultForm);

            }
            return ok(Json.toJson(resultList));
        }
    }
    
    /**
     * method to get dispSyoriSts name
     * @param dispSyoriSts dispSyoriSts
     * @param unyoDate unyoDate
     * @return boolean
     */
    private String getDispSyoriStsName(String dispSyoriSts, String unyoDate) {
        M017meimExample m017meimExample = new M017meimExample();
        m017meimExample.createCriteria()
            .andCdKbnEqualTo("SIR")
            .andCdEqualTo("SYORI_STS_KBN_" + dispSyoriSts)
            .andActKbnNotEqualTo(ACT_KBN_DELETE);

        m017meimExample.setSearchDate(unyoDate);

        List<M017meim> m017meimList = m017meimMapper.selectByExample(m017meimExample);
        if (m017meimList.size() == 0) {
            return "";
        }

        return m017meimList.get(0).getNm();
    }

    /**
     * check3Input
     * @param req  Sijp0130CondForm
     * @return boolean
     */
    private boolean check3Input(Sijp0130CondForm req) {
        boolean flag = true;

        String soutKaisyaCd = req.getOutKaisyaCd();
        String soutJigyobuCd = req.getOutJigyobuCd();
        String soutTenCd = req.getOutTenCd();
        // (出荷店)会社 ="", (出荷店)事業部=="", (出荷店)店舗=""
        if (CCStringUtil.isEmpty(soutKaisyaCd) && CCStringUtil.isEmpty(soutJigyobuCd)
                && CCStringUtil.isEmpty(soutTenCd)) {
            return true;
        }
        /*
         * // (出荷店)会社 !="", (出荷店)事業部=="", (出荷店)店舗=""
         */
        // (出荷店)会社 ="", (出荷店)事業部 !="", (出荷店)店舗=""
        if (!CCStringUtil.isEmpty(soutJigyobuCd)) {
            if (CCStringUtil.isEmpty(soutTenCd)) {
                flag = showErrorsoutTenCd();
            }

        }
        // (出荷店)会社 ="", (出荷店)事業部 ="", (出荷店)店舗 !=""
        if (!CCStringUtil.isEmpty(soutTenCd)) {
            if (CCStringUtil.isEmpty(soutJigyobuCd)) {
                flag = showErrorsoutJigyobuCd();
            }
        }

        return flag;
    }

    /**
     * showErrorsoutJigyobuCd
     * @return boolean
     */
    private boolean showErrorsoutJigyobuCd() {
        errRes.addErrorInfo(CCMessageConst.MSG_KEY_VALIDATION_ERROR);
        // (出荷店)事業部"
        errRes.addErrorInfo("outJigyobuCd", CCMessageConst.MSG_KEY_INPUT_REQUIRED_ENTER);
        return false;
    }

    /**
     * showErrorsoutTenCd
     * @return boolean
     */
    private boolean showErrorsoutTenCd() {
        errRes.addErrorInfo(CCMessageConst.MSG_KEY_VALIDATION_ERROR);
        // (出荷店)店舗"
        errRes.addErrorInfo("outTenCd", CCMessageConst.MSG_KEY_INPUT_REQUIRED_ENTER);
        return false;
    }

    /**
     * doCheckHead
     * @param req Sijp0130CondForm
     * @param dpyKbn String
     * @param kaisyaCd String 
     * @param jigyobuCd String 
     * @return boolean
     */
    private boolean doCheckHead(Sijp0130CondForm req, String dpyKbn, String kaisyaCd, String jigyobuCd) {

        boolean blErrflg = true;
        // 運用日　==　運用日を共通部品を利用して取得する。
        String sUnyoDate = context.getUnyoDate();

        // 伝区（入力必須）
        if (!CCNumberUtil.isNumeric(dpyKbn)) {
            errRes.addErrorInfo("dpyKbn", CCMessageConst.MSG_KEY_INPUT_NUMBER_CHARACTER);
            blErrflg = false;
        }

        // 会社（入力必須）
        if (!CCNumberUtil.isNumeric(kaisyaCd)) {
            errRes.addErrorInfo("kaisyaCd", CCMessageConst.MSG_KEY_INPUT_NUMBER_CHARACTER);
            blErrflg = false;

        } else {
            // で会社コードの存在をチェックする
            String sKaishaNm = cmJSICommon.getComKaisyaNameA(kaisyaCd);
            if (sKaishaNm.length() == 0) {
                // "会社コード"
                errRes.addErrorInfo("kaisyaCd", CCMessageConst.MSG_KEY_ITEM_INPUT_IS_ERROR);
                blErrflg = false;
            }
        }

        if (!CCNumberUtil.isNumeric(jigyobuCd)) {
            errRes.addErrorInfo("jigyobuCd", CCMessageConst.MSG_KEY_INPUT_NUMBER_CHARACTER);
            blErrflg = false;
        } else {
            // 事業部名　=　共通部品（cmJSICommonのgetComJigyobuNameA関数（画面項目[会社]、画面項目[事業部 ]））
            String sJigyobuNm = cmJSICommon.getComJigyobuNameA(kaisyaCd, jigyobuCd);
            if (sJigyobuNm.length() == 0) {
                // "事業部コード"
                errRes.addErrorInfo("jigyobuCd", CCMessageConst.MSG_KEY_NOT_EXIST_REGISTERED);
                blErrflg = false;
            }
        }

        // 店
        if (!CCStringUtil.isEmpty(req.getTenCd())) {
            if (!CCNumberUtil.isNumeric(req.getTenCd())) {
                errRes.addErrorInfo("tenCd", CCMessageConst.MSG_KEY_INPUT_NUMBER_CHARACTER);
                blErrflg = false;
            } else {
                String sTenNm = cmJSICommon.getComTenpoNameA(kaisyaCd + jigyobuCd + req.getTenCd(), sUnyoDate);
                if (sTenNm.length() == 0) {
                    // 店舗コード
                    errRes.addErrorInfo("tenCd", CCMessageConst.MSG_KEY_NOT_EXIST_REGISTERED);
                    blErrflg = false;
                }
            }
        }
        // 部門
        if (!CCStringUtil.isEmpty(req.getBmnCd())) {
            if (!CCNumberUtil.isNumeric(req.getBmnCd())) {
                errRes.addErrorInfo("bmnCd", CCMessageConst.MSG_KEY_INPUT_NUMBER_CHARACTER);
                blErrflg = false;
            } else {
                String sBmnNm = cmJSICommon.getComBumonNameA(jigyobuCd + req.getBmnCd(), sUnyoDate);
                if (sBmnNm.length() == 0) {
                    // 部門コード
                    errRes.addErrorInfo("bmnCd", CCMessageConst.MSG_KEY_NOT_EXIST_REGISTERED);
                    blErrflg = false;
                }
            }
        }

        // 取引先
        if (!dpyKbn.equals(CCSIConst.DEN_KBN.KBN_IDO)) {
            if (!CCStringUtil.isEmpty(req.getTriCd())) {
                if (!CCNumberUtil.isNumeric(req.getTriCd())) {
                    errRes.addErrorInfo("triCd", CCMessageConst.MSG_KEY_INPUT_NUMBER_CHARACTER);
                    blErrflg = false;
                }
            }
        } else {
            if (!CCStringUtil.isEmpty(req.getOutKaisyaCd()) && !CCStringUtil.isEmpty(req.getOutJigyobuCd())
                    && !CCStringUtil.isEmpty(req.getOutTenCd())) {
                String soutKaisyaCd = req.getOutKaisyaCd().trim();
                String soutJigyobuCd = req.getOutJigyobuCd().trim();
                String soutTenCd = req.getOutTenCd().trim();
                boolean check3data = true;
                if (!CCNumberUtil.isNumeric(soutKaisyaCd)) {
                    errRes.addErrorInfo("outKaisyaCd", CCMessageConst.MSG_KEY_INPUT_NUMBER_CHARACTER);
                    blErrflg = false;
                    check3data = false;
                }

                if (!CCNumberUtil.isNumeric(soutJigyobuCd)) {
                    errRes.addErrorInfo("outJigyobuCd", CCMessageConst.MSG_KEY_INPUT_NUMBER_CHARACTER);
                    blErrflg = false;
                    check3data = false;
                }

                if (!CCNumberUtil.isNumeric(soutTenCd)) {
                    errRes.addErrorInfo("outTenCd", CCMessageConst.MSG_KEY_INPUT_NUMBER_CHARACTER);
                    blErrflg = false;
                    check3data = false;
                }

                if (check3data) {
                    String sToriNm = "";
                    sToriNm = cmJSICommon.getComTenpoNameR(soutKaisyaCd + soutJigyobuCd + soutTenCd, sUnyoDate);
                    if (sToriNm.length() == 0) {
                        // 取引先コード
                        errRes.addErrorInfo("outKaisyaCd", CCMessageConst.MSG_KEY_NOT_EXIST_REGISTERED);
                        errRes.addErrorInfo("outJigyobuCd", CCMessageConst.MSG_KEY_NOT_EXIST_REGISTERED);
                        errRes.addErrorInfo("outTenCd", CCMessageConst.MSG_KEY_NOT_EXIST_REGISTERED);
                        blErrflg = false;
                    }
                }
            }
        }

        // 納品日（ＦＲＯＭ）
        if (!CCStringUtil.isEmpty(req.getNhmYmdSt())) {
            if (!CCDateUtil.isDate(req.getNhmYmdSt())) {
                errRes.addErrorInfo("nhmYmdSt", CCMessageConst.MSG_KEY_DATE_ERROR);
                blErrflg = false;
            }
        }

        // 納品日（ＴＯ）
        if (!CCStringUtil.isEmpty(req.getNhmYmdEd())) {
            if (!CCDateUtil.isDate(req.getNhmYmdEd())) {
                errRes.addErrorInfo("nhmYmdEd", CCMessageConst.MSG_KEY_DATE_ERROR);
                blErrflg = false;
            }
        }

        // 発注日（ＦＲＯＭ）
        if (!CCStringUtil.isEmpty(req.getHatYmdSt())) {
            if (!CCDateUtil.isDate(req.getHatYmdSt())) {
                errRes.addErrorInfo("hatYmdSt", CCMessageConst.MSG_KEY_DATE_ERROR);
                blErrflg = false;
            }
        }

        // 発注日（ＴＯ）
        if (!CCStringUtil.isEmpty(req.getHatYmdEd())) {
            if (!CCDateUtil.isDate(req.getHatYmdEd())) {
                errRes.addErrorInfo("hatYmdEd", CCMessageConst.MSG_KEY_DATE_ERROR);
                blErrflg = false;
            }
        }

        // 納品予定日（ＦＲＯＭ）
        if (!CCStringUtil.isEmpty(req.getNhnYoteiYmdSt())) {
            if (!CCDateUtil.isDate(req.getNhnYoteiYmdSt())) {
                errRes.addErrorInfo("nhnYoteiYmdSt", CCMessageConst.MSG_KEY_DATE_ERROR);
                blErrflg = false;
            }
        }

        // 納品予定日（ＴＯ）
        if (!CCStringUtil.isEmpty(req.getNhnYoteiYmdEd())) {
            if (!CCDateUtil.isDate(req.getNhnYoteiYmdEd())) {
                errRes.addErrorInfo("nhnYoteiYmdEd", CCMessageConst.MSG_KEY_DATE_ERROR);
                blErrflg = false;
            }
        }

        // 計上日（ＦＲＯＭ）
        if (!CCStringUtil.isEmpty(req.getRuiKejoYmdSt())) {
            if (!CCDateUtil.isDate(req.getRuiKejoYmdSt())) {
                errRes.addErrorInfo("ruiKejoYmdSt", CCMessageConst.MSG_KEY_DATE_ERROR);
                blErrflg = false;
            }
        }

        // 計上日（ＴＯ）
        if (!CCStringUtil.isEmpty(req.getRuiKejoYmdEd())) {
            if (!CCDateUtil.isDate(req.getRuiKejoYmdEd())) {
                errRes.addErrorInfo("ruiKejoYmdEd", CCMessageConst.MSG_KEY_DATE_ERROR);
                blErrflg = false;
            }
        }

        // 確定場所（店舗Ｍへ存在チェック）
        if (!CCStringUtil.isEmpty(req.getEntryPlace())) {

            if (!CCNumberUtil.isNumeric(req.getEntryPlace())) {
                errRes.addErrorInfo("entryPlace", CCMessageConst.MSG_KEY_INPUT_NUMBER_CHARACTER);
                blErrflg = false;
            } else {
                String sPlcNm = cmJSICommon.getComTenpoNameA(req.getEntryPlace(), sUnyoDate);
                if (sPlcNm.length() == 0) {
                    // 確定場所
                    errRes.addErrorInfo("entryPlace", CCMessageConst.MSG_KEY_NOT_EXIST_REGISTERED);
                    blErrflg = false;
                }
            }
        }

        // 確定日（ ＦＲＯＭ）
        if (!CCStringUtil.isEmpty(req.getKakuteiYmdSt())) {
            if (!CCDateUtil.isDate(req.getKakuteiYmdSt())) {
                errRes.addErrorInfo("kakuteiYmdSt", CCMessageConst.MSG_KEY_DATE_ERROR);
                blErrflg = false;
            }
        }

        // 確定日（ＴＯ）
        if (!CCStringUtil.isEmpty(req.getKakuteiYmdEd())) {
            if (!CCDateUtil.isDate(req.getKakuteiYmdEd())) {
                errRes.addErrorInfo("kakuteiYmdEd", CCMessageConst.MSG_KEY_DATE_ERROR);
                blErrflg = false;
            }
        }

        // 担当者

        if (!CCStringUtil.isEmpty(req.getTantoCd())) {
            if (!CCStringUtil.isEmpty(req.getTantoNm())) {
                if (!blErrflg) {
                    errRes.addErrorInfo("tantoCd", CCMessageConst.MSG_KEY_RESULT_NOT_EXISTS);
                    blErrflg = false;
                }
            }
        }

        if (!blErrflg) {
            ErrorInfo error = errRes.getErrorInfo(CCMessageConst.MSG_KEY_VALIDATION_ERROR);
            errRes.getErrors().add(0, error);
        }

        return blErrflg;
    }

    /**
     * addParameterExecute
     * @param req 
     * @param dpyKbn [伝区
     * @param kaisyaCd 会社コード
     * @param jigyobuCd 事業部
     * @return S014eomrExecute
     */
    private S014eomrExecute addParameterExecute(Sijp0130CondForm req, String dpyKbn, String kaisyaCd,
            String jigyobuCd) {
        S014eomrExecute param = new S014eomrExecute();
        String[] ignoreList =
                {"cmnTantoCd", "currentDay", "dpyKbn", "entryKbn", "jigyobuCd", "kaisyaCd", "likeFlag",
                        "torihikiCd", "triCd" };
        BeanUtils.copyProperties(req, param, ignoreList);

        param.setDpyKbn(dpyKbn);
        param.setKaisyaCd(kaisyaCd);
        param.setJigyobuCd(jigyobuCd);

        if (CCSIConst.DEN_KBN.KBN_IDO.equals(dpyKbn)) {
            String soutKaisyaCd = req.getOutKaisyaCd();
            String soutJigyobuCd = req.getOutJigyobuCd();
            String soutTenCd = req.getOutTenCd();
            if (!CCStringUtil.isEmpty(soutKaisyaCd) && !CCStringUtil.isEmpty(soutJigyobuCd)
                    && !CCStringUtil.isEmpty(soutTenCd)) {
                param.setTorihikiCd(soutKaisyaCd + soutJigyobuCd + soutTenCd);
            } else {
                param.setTorihikiCd("");
            }

        } else {
            param.setTorihikiCd(req.getTriCd());
        }

        param.setCmnTantoCd(req.getTantoCd());

        param.setCurrentDay(CCDateUtil.getSysDateFormat(DATETIME_FORMAT_DATE_YYYYMMDD));

        int likeFlag = 0;
        String entryKbn = "";
        if (req.getEntryKbn() != null) {
            if (req.getEntryKbn().length() == 2 && "*".equals(req.getEntryKbn().substring(1, 2))) {
                entryKbn = req.getEntryKbn().substring(0, 1);
                likeFlag = 1;
            } else {
                entryKbn = req.getEntryKbn();
                likeFlag = 0;
            }
        }
        param.setEntryKbn(entryKbn);
        param.setLikeFlag(likeFlag);
        return param;
    }
}
