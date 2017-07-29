// /////////////////////////////////////////////////////////////////////
// Copyright(C) 2015 NEC Solution Innovators, Ltd. All Rights Reserved.
// /////////////////////////////////////////////////////////////////////

/*
 * ====================================================================
 * 
 * 機能名称 : 伝票ヘッダ照会
 * 
 * 改版履歴
 * 
 * Rev. 改版年月日 改版者名 内容
 * 
 * 1.0 2014-03-25 tuctv 新規作成
 * 
 * ===================================================================
 */

package jp.co.necsoft.web_md.core.app.controllers.si;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.springframework.beans.BeanUtils;

import jp.co.necsoft.web_md.common.mybatis.dao.MyBatisSqlMapDao;
import jp.co.necsoft.web_md.core.app.dto.si.S001eosdExecute;
import jp.co.necsoft.web_md.core.app.dto.si.S001eosdExecuteResult;
import jp.co.necsoft.web_md.core.app.forms.si.Sijp0110CondForm;
import jp.co.necsoft.web_md.core.app.forms.si.Sijp0110ResultForm;
import jp.co.necsoft.web_md.core.common.CCMessageConst;
import jp.co.necsoft.web_md.core.common.CCSecurityAuthenticator;
import jp.co.necsoft.web_md.core.common.CCSystemContext;
import jp.co.necsoft.web_md.core.common.biz.si.CCSICommon;
import jp.co.necsoft.web_md.core.common.biz.si.CCSIConst;
import jp.co.necsoft.web_md.core.common.forms.ErrorInfo;
import jp.co.necsoft.web_md.core.common.forms.ErrorResponse;
import jp.co.necsoft.web_md.core.common.utils.CCDateUtil;
import jp.co.necsoft.web_md.core.common.utils.CCNumberUtil;
import jp.co.necsoft.web_md.core.common.utils.CCStringUtil;
import play.data.Form;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;

/**
*伝票ヘッダ照会のControllerクラス
*
*/
@Security.Authenticated(CCSecurityAuthenticator.class)
public class Sijp0110Ctrl extends Controller {

    /**
     * RECORD_MAX
     **/
    private static final int RECORD_MAX = 200;
    /** yyyyMMdd */
    private static final String DATETIME_FORMAT_DATE_YYYYMMDD = "yyyyMMdd";
    /** yyyy/MM/dd */
    private static final String DATETIME_FORMAT_DATE = "yyyy/MM/dd";

    /**mybatisDao*/
    @Inject
    private MyBatisSqlMapDao mybatisDao;
    /**errRes*/
    @Inject
    private ErrorResponse errRes;

    /**errRes*/
    @Inject
    private CCSystemContext context;

    /**CCSICommonn*/
    @Inject
    private CCSICommon cmJSICommon;

    /**
     * 初期表示
     * @return クライアントへ返却する結果  
     */
    public Result init() {
        Sijp0110CondForm cForm = new Sijp0110CondForm();
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
        Form<Sijp0110CondForm> emptyForm = new Form(Sijp0110CondForm.class);
        Form<Sijp0110CondForm> reqForm = emptyForm.bindFromRequest();
        if (reqForm.hasErrors()) {
            errRes.addErrorInfo(CCMessageConst.MSG_KEY_VALIDATION_ERROR);
            errRes.addErrorInfo(reqForm.errors());
            return badRequest(Json.toJson(errRes));
        } else {
            Sijp0110CondForm req = reqForm.get();

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

            S001eosdExecute paramExec = addParameterExecute(req, dpyKbn, kaisyaCd, jigyobuCd);
            boolean gridFlag = getGirdFlag(dpyKbn, jigyobuCd);

            // Sijp0110MapperのselectS001eosdS002tgkdS003fridS004bhhkS033seid01を使用する。
            List<S001eosdExecuteResult> resultDatalist;

            resultDatalist =
                    mybatisDao.selectMany("selectS001eosdS002tgkdS003fridS004bhhkS033seid01", paramExec,
                            S001eosdExecuteResult.class);

            // 対象データがありません。
            if (resultDatalist.size() == 0) {
                // 「C0105」メッセージが表示される。
                errRes.addErrorInfo(CCMessageConst.MSG_KEY_BUMON_NO_DATA);
                return badRequest(Json.toJson(errRes));
            } else if (resultDatalist.size() > RECORD_MAX) {
                errRes.addErrorInfo(CCMessageConst.MSG_KEY_PLEASE_NARROW_CONDITION);
                return badRequest(Json.toJson(errRes));
            }

            // 検索結果表示
            List<Sijp0110ResultForm> resultList = new ArrayList<Sijp0110ResultForm>();
            for (int i = 0; i < resultDatalist.size(); i++) {
                S001eosdExecuteResult rs = resultDatalist.get(i);
                Sijp0110ResultForm resultForm = new Sijp0110ResultForm();

                resultForm.setNo(i + 1);

                resultForm.setDpyNo(rs.getDpyNo());

                resultForm.setRuiKeijoYmd(CCDateUtil.getDateFormat(rs.getRuiKeijoYmd(), DATETIME_FORMAT_DATE));
                resultForm.setTenCd(rs.getsTenCd());
                resultForm.setTenNm(rs.getTenNmR1());
                resultForm.setInBmnCd(rs.getbBmnCd());
                resultForm.setBmnNm(rs.getBmnNmR());
                String sEdit = rs.getTorihikiCd();
                String sToriNm = rs.getTriNmR();
                resultForm.setGTriNm(sToriNm);

                if (sEdit.trim().length() == 9) {
                    resultForm.setTorihikiCd(sEdit.substring(0, 6) + "-" + sEdit.substring(6, 9));
                } else if (sEdit.trim().length() == 7) { // 出荷店の場合
                    resultForm.setTorihikiCd(sEdit.substring(0, 2) + "-" + sEdit.substring(2, 4) + "-"
                            + sEdit.substring(4, 7));
                    String sUnyoDate = context.getUnyoDate();
                    sToriNm = cmJSICommon.getComTenpoNameR(sEdit, sUnyoDate);
                }

                // 画面項目[代表取引先]の名称のトリムのLength > 0の場合、
                if (sToriNm.trim().length() > 0) { // 出荷店の場合
                    resultForm.setGTriNm(sToriNm);
                }
                resultForm.setHatYmd(CCDateUtil.getDateFormat(rs.getHatYmd(), DATETIME_FORMAT_DATE));
                resultForm.setNhnYoteiYmd(CCDateUtil.getDateFormat(rs.getNhnYoteiYmd(), DATETIME_FORMAT_DATE));
                resultForm.setNhnYmd(CCDateUtil.getDateFormat(rs.getNhnYmd(), DATETIME_FORMAT_DATE));
                resultForm.setSubLastKakuteiYmd(CCDateUtil.getDateFormat(rs.getLastKakuteiYmd(),
                        DATETIME_FORMAT_DATE));
                resultForm.setSKenGenkKin(rs.getsKenGenkKin());
                resultForm.setSKenBaikKin(rs.getsKenBaikKin());
                resultForm.setTanto(rs.getLastTantoCd());

                // 検索結果データ[STATUS_NM]
                String sCd = "SYORI_STS_KBN_" + rs.getSyoriStsKbn();
                resultForm.setGSyoriStsKbn(cmJSICommon.getSirName(sCd));
                resultForm.setSubEntryKbn(rs.getEntryKbn());

                if (gridFlag) {
                    resultForm.setLink(addLinkForRow(paramExec.getDpyKbn(), paramExec.getKaisyaCd(),
                            paramExec.getJigyobuCd(), resultForm, rs));
                } else {
                    resultForm.setLink("#");
                }

                resultList.add(resultForm);
            }
            return ok(Json.toJson(resultList));

        }
    }

    /**
     * check3Input
     * @param req Sijp0110CondForm
     * @return boolean
     */
    private boolean check3Input(Sijp0110CondForm req) {
        boolean flag = true;
        String soutKaisyaCd = req.getOutKaisyaCd();
        String soutJigyobuCd = req.getOutJigyobuCd();
        String soutTenCd = req.getOutTenCd();
        // (出荷店)会社 ="", (出荷店)事業部=="", (出荷店)店舗=""
        if (CCStringUtil.isEmpty(soutKaisyaCd) && CCStringUtil.isEmpty(soutJigyobuCd)
                && CCStringUtil.isEmpty(soutTenCd)) {
            return true;
        }
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
        // msgs.add("(出荷店)事業部");
        errRes.addErrorInfo("outJigyobuCd", CCMessageConst.MSG_KEY_INPUT_REQUIRED_ENTER);
        return false;
    }

    /**
     * showErrorsoutTenCd
     * @return boolean
     */
    private boolean showErrorsoutTenCd() {
        errRes.addErrorInfo(CCMessageConst.MSG_KEY_VALIDATION_ERROR);
        // msgs.add("(出荷店)店舗");
        errRes.addErrorInfo("outTenCd", CCMessageConst.MSG_KEY_INPUT_REQUIRED_ENTER);
        return false;
    }

    /**
     * addParameterExecute
     * @param req 
     * @param dpyKbn [伝区
     * @param kaisyaCd 会社コード
     * @param jigyobuCd 事業部
     * @return S001eosdExecute
     */
    private S001eosdExecute addParameterExecute(Sijp0110CondForm req, String dpyKbn, String kaisyaCd,
            String jigyobuCd) {
        S001eosdExecute param = new S001eosdExecute();
        String[] ignoreList =
                {"currentDay", "dpyKbn", "entryKbn", "jigyobuCd", "kaisyaCd", "likeFlag", "torihikiCd" };
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
            param.setTorihikiCd(req.getMainToriCd());
        }

        param.setCurrentDay(CCDateUtil.getSysDateFormat(DATETIME_FORMAT_DATE_YYYYMMDD));

        int likeFlag = 0;
        String entryKbn = "";
        if (req.getEntryKbn() != null) {
            if (req.getEntryKbn().length() == 2 && req.getEntryKbn().substring(1, 2).equals("*")) {
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

    /**
     * getGirdFlag
     * @param dpyKbn [伝区
     * @param jigyobuCd 事業部
     * @return boolean
     */
    private boolean getGirdFlag(String dpyKbn, String jigyobuCd) {
        boolean gridFlag = false;
        // EOS修正履歴F
        if (CCSIConst.DEN_KBN.KBN_EOSSIR.equals(dpyKbn)) {
            if (jigyobuCd.equals("90")) {
                gridFlag = false;
            } else {
                gridFlag = true;
            }
        }

        // 手書伝票累積F
        if (CCSIConst.DEN_KBN.KBN_TGKSIR.equals(dpyKbn) || CCSIConst.DEN_KBN.KBN_TGKSIR_D.equals(dpyKbn)
                || CCSIConst.DEN_KBN.KBN_HPN.equals(dpyKbn) || CCSIConst.DEN_KBN.KBN_HPN_D.equals(dpyKbn)
                || CCSIConst.DEN_KBN.KBN_NBK.equals(dpyKbn)) {
            gridFlag = true;
        }
        if (CCSIConst.DEN_KBN.KBN_IDO.equals(dpyKbn)) {
            gridFlag = true;
        }
        // 値上下廃棄修正履歴F
        if (CCSIConst.DEN_KBN.KBN_ONLINE_UP.equals(dpyKbn) || CCSIConst.DEN_KBN.KBN_ONLINE_REG_DW.equals(dpyKbn)
                || CCSIConst.DEN_KBN.KBN_HIK.equals(dpyKbn) || CCSIConst.DEN_KBN.KBN_HIK_MDF.equals(dpyKbn)) {
            gridFlag = true;
        }
        return gridFlag;
    }

    /**
     * addLinkForRow
     * @param dpyKbn String
     * @param kaisyaCd String
     * @param jigyobuCd String
     * @param rs S001eosdExecuteResult
     * @param rsexe S001eosdExecuteResult
     * @return String
     */
    private String addLinkForRow(String dpyKbn, String kaisyaCd, String jigyobuCd, Sijp0110ResultForm rs,
            S001eosdExecuteResult rsexe) {
        String link = "#";
        // GridのNolinkをclickする時、SIJP0010画面は新しい画面を開く
        if (CCSIConst.DEN_KBN.KBN_EOSSIR.equals(dpyKbn)) {
            link =
                    "/core#/SIJP0010?" + "dpyKbn=" + dpyKbn + "&kaisyaCd=" + kaisyaCd + "&jigyobuCd=" + jigyobuCd
                            + "&dpyNo=" + rs.getDpyNo() + "&nhnYmd=" + rsexe.getNhnYmd();
        }

        // GridのNolinkをclickする時、SIJP0020画面は新しい画面を開く
        if (CCSIConst.DEN_KBN.KBN_TGKSIR.equals(dpyKbn) || CCSIConst.DEN_KBN.KBN_TGKSIR_D.equals(dpyKbn)) {
            link =
                    "/core#/SIJP0020?" + "dpyKbn=" + dpyKbn + "&kaisyaCd=" + kaisyaCd + "&jigyobuCd=" + jigyobuCd
                            + "&dpyNo=" + rs.getDpyNo() + "&tenCd=" + rs.getTenCd() + "&bmnCd=" + rsexe.getbBmnCd()
                            + "&torihikiCd=" + rs.getTorihikiCd() + "&nhnYmd=" + rsexe.getNhnYmd();
        }
        // GridのNolinkをclickする時、SIJP0030画面は新しい画面を開く
        if (CCSIConst.DEN_KBN.KBN_HPN.equals(dpyKbn) || CCSIConst.DEN_KBN.KBN_HPN_D.equals(dpyKbn)
                || CCSIConst.DEN_KBN.KBN_NBK.equals(dpyKbn)) {
            link =
                    "/core#/SIJP0030?" + "dpyKbn=" + dpyKbn + "&kaisyaCd=" + kaisyaCd + "&jigyobuCd=" + jigyobuCd
                            + "&dpyNo=" + rs.getDpyNo() + "&tenCd=" + rs.getTenCd() + "&bmnCd=" + rsexe.getbBmnCd()
                            + "&torihikiCd=" + rs.getTorihikiCd() + "&nhnYmd=" + rsexe.getNhnYmd();
        }
        // GridのNolinkをclickする時、SIJP0040画面は新しい画面を開く
        if (CCSIConst.DEN_KBN.KBN_IDO.equals(dpyKbn)) {
            link =
                    "/core#/SIJP0040?" + "dpyKbn=" + dpyKbn + "&kaisyaCd=" + kaisyaCd + "&jigyobuCd=" + jigyobuCd
                            + "&dpyNo=" + rs.getDpyNo() + "&tenCd=" + rs.getTenCd() + "&bmnCd=" + rsexe.getbBmnCd()
                            + "&torihikiCd=" + rs.getTorihikiCd() + "&nhnYmd=" + rsexe.getNhnYmd() + "&outBmnCd="
                            + rsexe.getOutBmnCd();
        }
        // GridのNolinkをclickする時、SIJP0050画面は新しい画面を開く
        if (CCSIConst.DEN_KBN.KBN_ONLINE_UP.equals(dpyKbn) || CCSIConst.DEN_KBN.KBN_ONLINE_REG_DW.equals(dpyKbn)) {
            link =
                    "/core#/SIJP0050?" + "dpyKbn=" + dpyKbn + "&kaisyaCd=" + kaisyaCd + "&jigyobuCd=" + jigyobuCd
                            + "&dpyNo=" + rs.getDpyNo() + "&tenCd=" + rs.getTenCd() + "&bmnCd=" + rsexe.getbBmnCd()
                            + "&nhnYmd=" + rsexe.getNhnYmd();
        }

        // GridのNolinkをclickする時、SIJP0060画面は新しい画面を開く
        if (CCSIConst.DEN_KBN.KBN_HIK.equals(dpyKbn) || CCSIConst.DEN_KBN.KBN_HIK_MDF.equals(dpyKbn)) {
            link =
                    "/core#/SIJP0060?" + "dpyKbn=" + dpyKbn + "&kaisyaCd=" + kaisyaCd + "&jigyobuCd=" + jigyobuCd
                            + "&dpyNo=" + rs.getDpyNo() + "&tenCd=" + rs.getTenCd() + "&bmnCd=" + rsexe.getbBmnCd()
                            + "&nhnYmd=" + rsexe.getNhnYmd();
        }
        return link;
    }

    /**
     * doCheckHead
     * @param req Sijp0110CondForm
     * @param dpyKbn String
     * @param kaisyaCd String 
     * @param jigyobuCd String 
     * @return boolean
     */
    private boolean doCheckHead(Sijp0110CondForm req, String dpyKbn, String kaisyaCd, String jigyobuCd) {

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
        if (!CCSIConst.DEN_KBN.KBN_IDO.equals(dpyKbn)) {
            if (!CCStringUtil.isEmpty(req.getMainToriCd())) {
                if (!CCNumberUtil.isNumeric(req.getMainToriCd())) {
                    errRes.addErrorInfo("mainToriCd", CCMessageConst.MSG_KEY_INPUT_NUMBER_CHARACTER);
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
        if (!CCStringUtil.isEmpty(req.getNhnYmdSt())) {
            if (!CCDateUtil.isDate(req.getNhnYmdSt())) {
                errRes.addErrorInfo("nhnYmdSt", CCMessageConst.MSG_KEY_DATE_ERROR);
                blErrflg = false;
            }
        }

        // 納品日（ＴＯ）
        if (!CCStringUtil.isEmpty(req.getNhnYmdEd())) {
            if (!CCDateUtil.isDate(req.getNhnYmdEd())) {
                errRes.addErrorInfo("nhnYmdEd", CCMessageConst.MSG_KEY_DATE_ERROR);
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
        if (!CCStringUtil.isEmpty(req.getRuiKeijoYmdSt())) {
            if (!CCDateUtil.isDate(req.getRuiKeijoYmdSt())) {
                errRes.addErrorInfo("ruiKeijoYmdSt", CCMessageConst.MSG_KEY_DATE_ERROR);
                blErrflg = false;
            }
        }

        // 計上日（ＴＯ）
        if (!CCStringUtil.isEmpty(req.getRuiKeijoYmdEd())) {
            if (!CCDateUtil.isDate(req.getRuiKeijoYmdEd())) {
                errRes.addErrorInfo("ruiKeijoYmdEd", CCMessageConst.MSG_KEY_DATE_ERROR);
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
        if (!CCStringUtil.isEmpty(req.getLastKakuteiYmdSt())) {
            if (!CCDateUtil.isDate(req.getLastKakuteiYmdSt())) {
                errRes.addErrorInfo("lastKakuteiYmdSt", CCMessageConst.MSG_KEY_DATE_ERROR);
                blErrflg = false;
            }
        }

        // 確定日（ＴＯ）
        if (!CCStringUtil.isEmpty(req.getLastKakuteiYmdEd())) {
            if (!CCDateUtil.isDate(req.getLastKakuteiYmdEd())) {
                errRes.addErrorInfo("lastKakuteiYmdEd", CCMessageConst.MSG_KEY_DATE_ERROR);
                blErrflg = false;
            }
        }

        // 担当者

        if (!CCStringUtil.isEmpty(req.getLastTantoCd())) {
            if (!CCStringUtil.isEmpty(req.getLastTantoNm())) {
                if (!blErrflg) {
                    errRes.addErrorInfo("lastTantoCd", CCMessageConst.MSG_KEY_RESULT_NOT_EXISTS);
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

}
