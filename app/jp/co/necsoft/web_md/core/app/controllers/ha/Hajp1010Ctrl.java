// /////////////////////////////////////////////////////////////////////
// Copyright(C) 2015 NEC Solution Innovators, Ltd. All Rights Reserved.
// /////////////////////////////////////////////////////////////////////

/*
 * ====================================================================
 * 
 * 機能名称 : 発注エラーリスト
 * 
 * 改版履歴
 * 
 * Rev. 改版年月日 改版者名 内容
 * 
 * 1.0 2014.04.04 LocHV 新規作成
 * 
 * ===================================================================
 */

package jp.co.necsoft.web_md.core.app.controllers.ha;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import jp.co.necsoft.web_md.common.ChaseException;
import jp.co.necsoft.web_md.common.mybatis.dao.MyBatisSqlMapDao;
import jp.co.necsoft.web_md.core.app.file.report.Hapr1010Report;
import jp.co.necsoft.web_md.core.app.forms.ha.Hajp1010CondForm;
import jp.co.necsoft.web_md.core.app.forms.ha.Hajp1010ResultForm;
import jp.co.necsoft.web_md.core.common.CCMessageConst;
import jp.co.necsoft.web_md.core.common.CCSecurityAuthenticator;
import jp.co.necsoft.web_md.core.common.CCSystemContext;
import jp.co.necsoft.web_md.core.common.forms.ErrorResponse;
import jp.co.necsoft.web_md.core.common.mybatis.dto.H130herr;
import jp.co.necsoft.web_md.core.common.utils.CCCheckExistsUtil;
import jp.co.necsoft.web_md.core.common.utils.CCDateUtil;
import jp.co.necsoft.web_md.core.common.utils.CCReportUtil;
import jp.co.necsoft.web_md.core.common.utils.CCStringUtil;

import org.apache.commons.lang3.StringUtils;

import play.data.Form;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;

import com.orangesignal.csv.manager.CsvManager;
import com.orangesignal.csv.manager.CsvManagerFactory;

/**
 * 発注エラーリストのControllerクラス
 *
 */
@Security.Authenticated(CCSecurityAuthenticator.class)
public class Hajp1010Ctrl extends Controller {
    /** dao */
    @Inject
    private MyBatisSqlMapDao dao;
    /** errRes */
    @Inject
    private ErrorResponse errRes;
    /** context */
    @Inject
    private CCSystemContext context;
    /** ccCheckExistsUtil */
    @Inject
    private CCCheckExistsUtil ccCheckExistsUtil;

    /**
     * 引数とリクエスト(パラメータ)を条件にDBからデータ(複数件)を検索し返却する。
     * @param hatDdSt 発注日開始
     * @param hatDdEd 発注日完了
     * @return クライアントへ返却する結果
     */
    public Result query(String hatDdSt, String hatDdEd) {
        Form<Hajp1010CondForm> emptyForm = new Form<Hajp1010CondForm>(Hajp1010CondForm.class);
        Form<Hajp1010CondForm> reqForm = emptyForm.bindFromRequest();
        if (reqForm.hasErrors()) {
            errRes.addErrorInfo(CCMessageConst.MSG_KEY_VALIDATION_ERROR);
            errRes.addErrorInfo(reqForm.errors());
            return badRequest(Json.toJson(errRes));
        } else {
            Hajp1010CondForm req = reqForm.get();
            boolean bChkData = true;
            boolean bChkKaishaCd = true;
            boolean bChkJigyobuCd = true;
            String sUnyoDate;

            req.setHatDdSt(hatDdSt);
            req.setHatDdEd(hatDdEd);

            // １バイト文字、２バイト文字の桁数チェック
            // 日付型チェック
            if (!CCDateUtil.isDate(req.getHatDdSt())) {
                errRes.addErrorInfo(CCMessageConst.MSG_KEY_VALIDATION_ERROR);
                errRes.addErrorInfo("hatDdSt", CCMessageConst.MSG_KEY_RE_ENTER_NO_PARAM);
                bChkData = false;
            } else {
                // 発注運用日取得
                sUnyoDate = context.getHatUnyoDate();

                if (req.getHatDdSt().compareTo(sUnyoDate) > 0) {
                    errRes.addErrorInfo(CCMessageConst.MSG_KEY_VALIDATION_ERROR);
                    errRes.addErrorInfo("hatDdSt", CCMessageConst.MSG_KEY_NOT_MATCH_TYPE_FROM_GREATER);
                    bChkData = false;
                }
            }

            // 発注日Toチェック
            // １バイト文字、２バイト文字の桁数チェック
            // 日付型チェック
            if (!CCDateUtil.isDate(req.getHatDdEd())) {
                errRes.addErrorInfo(CCMessageConst.MSG_KEY_VALIDATION_ERROR);
                errRes.addErrorInfo("hatDdEd", CCMessageConst.MSG_KEY_INPUT_NOT_MATCH_TYPE);
                bChkData = false;
            } else {
                // 発注日FO>TOはエラー
                if (req.getHatDdSt().compareTo(req.getHatDdEd()) > 0) {
                    errRes.addErrorInfo(CCMessageConst.MSG_KEY_VALIDATION_ERROR);
                    errRes.addErrorInfo("hatDdSt", CCMessageConst.MSG_KEY_ERROR_ORDER_DATE_FR_GREATER);
                    bChkData = false;
                }
            }

            // 会社コードチェック
            // 存在チェック
            if (!ccCheckExistsUtil.existsKaisyaCd(req.getHatDdSt(), req.getKaisyaCd())) {
                errRes.addErrorInfo(CCMessageConst.MSG_KEY_VALIDATION_ERROR);
                errRes.addErrorInfo("kaisyaCd", CCMessageConst.MSG_KEY_NOT_REGISTERED_NO_PARAM);
                bChkKaishaCd = false;
                bChkData = false;
            }

            // 事業部コードチェック
            if (!CCStringUtil.isEmpty(req.getJigyobuCd())) {
                // 桁数チェック
                if (req.getJigyobuCd().getBytes().length != 2
                        || CCStringUtil.getByteLen(req.getJigyobuCd()) != req.getJigyobuCd().length()) {
                    errRes.addErrorInfo(CCMessageConst.MSG_KEY_VALIDATION_ERROR);
                    errRes.addErrorInfo("jigyobuCd", CCMessageConst.MSG_KEY_ENTER_INPUT_TWO_DIGITS);
                    bChkJigyobuCd = false;
                    bChkData = false;
                } else {
                    // 存在チェック
                    if (!ccCheckExistsUtil.existsKaisyaCdAndJigyobuCd(req.getHatDdSt(), req.getKaisyaCd(),
                            req.getJigyobuCd())) {
                        errRes.addErrorInfo(CCMessageConst.MSG_KEY_VALIDATION_ERROR);
                        errRes.addErrorInfo("jigyobuCd", CCMessageConst.MSG_KEY_NOT_REGISTERED_NO_PARAM);
                        bChkJigyobuCd = false;
                        bChkData = false;
                    }
                    // 部門コード(FR)チェック
                    if (!CCStringUtil.isEmpty(req.getBmnSt())) {
                        // 桁数チェック
                        if (req.getBmnSt().getBytes().length != 3
                                || CCStringUtil.getByteLen(req.getBmnSt()) != req.getBmnSt().length()) {
                            errRes.addErrorInfo(CCMessageConst.MSG_KEY_VALIDATION_ERROR);
                            errRes.addErrorInfo("bmnSt", CCMessageConst.MSG_KEY_INPUT_THREE_DIGITS_LONG);
                            bChkData = false;
                        } else {
                            // 存在チェック
                            if (!ccCheckExistsUtil
                                    .existsBmnCd(req.getHatDdSt(), req.getJigyobuCd() + req.getBmnSt())) {
                                errRes.addErrorInfo(CCMessageConst.MSG_KEY_VALIDATION_ERROR);
                                errRes.addErrorInfo("bmnSt", CCMessageConst.MSG_KEY_NOT_REGISTERED_NO_PARAM);
                                bChkData = false;
                            }
                        }
                    }

                    // 部門コード(TO)チェック
                    if (!CCStringUtil.isEmpty(req.getBmnEd())) {
                        // 桁数チェック
                        if (req.getBmnEd().getBytes().length != 3
                                || CCStringUtil.getByteLen(req.getBmnEd()) != req.getBmnEd().length()) {
                            errRes.addErrorInfo(CCMessageConst.MSG_KEY_VALIDATION_ERROR);
                            errRes.addErrorInfo("bmnEd", CCMessageConst.MSG_KEY_INPUT_THREE_DIGITS_LONG);
                            bChkData = false;
                        } else {
                            // 存在チェック
                            if (!ccCheckExistsUtil
                                    .existsBmnCd(req.getHatDdSt(), req.getJigyobuCd() + req.getBmnEd())) {
                                errRes.addErrorInfo(CCMessageConst.MSG_KEY_VALIDATION_ERROR);
                                errRes.addErrorInfo("bmnEd", CCMessageConst.MSG_KEY_NOT_REGISTERED_NO_PARAM);
                                bChkData = false;
                            }
                            // FR＜TOチェック
                            if (!CCStringUtil.isEmpty(req.getBmnSt())) {
                                if (req.getBmnSt().compareTo(req.getBmnEd()) > 0) {
                                    errRes.addErrorInfo(CCMessageConst.MSG_KEY_VALIDATION_ERROR);
                                    errRes.addErrorInfo("bmnSt", CCMessageConst.MSG_KEY_BMN_ERROR_COMPARE);
                                    bChkData = false;
                                }
                            }
                        }
                    }
                }
            } else {
                bChkJigyobuCd = false;
            }

            // 店舗コード(FR)チェック
            if (bChkKaishaCd) {
                if (bChkJigyobuCd) {
                    if (!CCStringUtil.isEmpty(req.getTenCdSt())) {
                        // 桁数チェック
                        if (req.getTenCdSt().getBytes().length != 3
                                || CCStringUtil.getByteLen(req.getTenCdSt()) != req.getTenCdSt().length()) {
                            errRes.addErrorInfo(CCMessageConst.MSG_KEY_VALIDATION_ERROR);
                            errRes.addErrorInfo("tenCdSt", CCMessageConst.MSG_KEY_INPUT_THREE_DIGITS_LONG);
                            bChkData = false;
                        } else {
                            // 存在チェック
                            if (!ccCheckExistsUtil.existsTenCd(req.getHatDdSt(),
                                    req.getKaisyaCd() + req.getJigyobuCd() + req.getTenCdSt())) {
                                errRes.addErrorInfo(CCMessageConst.MSG_KEY_VALIDATION_ERROR);
                                errRes.addErrorInfo("tenCdSt", CCMessageConst.MSG_KEY_NOT_REGISTERED_NO_PARAM);
                                bChkData = false;
                            }
                            // FR＜TOチェック
                            if (!CCStringUtil.isEmpty(req.getTenCdSt()) && !CCStringUtil.isEmpty(req.getTenCdEd())) {
                                if (req.getTenCdSt().compareTo(req.getTenCdEd()) > 0) {
                                    errRes.addErrorInfo(CCMessageConst.MSG_KEY_VALIDATION_ERROR);
                                    errRes.addErrorInfo("tenCdSt",
                                            CCMessageConst.MSG_KEY_TENCD_ERROR_COMPARE);
                                    bChkData = false;
                                }
                            }
                        }
                    }
                }
            }
            // 店舗コード(TO)チェック
            if (bChkKaishaCd) {
                if (bChkJigyobuCd) {
                    if (!CCStringUtil.isEmpty(req.getTenCdEd())) {
                        // 桁数チェック
                        if (req.getTenCdEd().getBytes().length != 3
                                || CCStringUtil.getByteLen(req.getTenCdEd()) != req.getTenCdEd().length()) {
                            errRes.addErrorInfo(CCMessageConst.MSG_KEY_VALIDATION_ERROR);
                            errRes.addErrorInfo("tenCdEd", CCMessageConst.MSG_KEY_INPUT_THREE_DIGITS_LONG);
                            bChkData = false;
                        } else {
                            // 存在チェック
                            if (!ccCheckExistsUtil.existsTenCd(req.getHatDdSt(),
                                    req.getKaisyaCd() + req.getJigyobuCd() + req.getTenCdEd())) {
                                errRes.addErrorInfo(CCMessageConst.MSG_KEY_VALIDATION_ERROR);
                                errRes.addErrorInfo("tenCdEd", CCMessageConst.MSG_KEY_NOT_REGISTERED_NO_PARAM);
                                bChkData = false;
                            }
                            // FR＜TOチェック
                        }
                    }
                }
            }

            // 発注処理種別チェック
            if (!CCStringUtil.isEmpty(req.getHatSruiKbn())) {
                // 桁数チェック
                if (req.getHatSruiKbn().length() != 2) {
                    errRes.addErrorInfo(CCMessageConst.MSG_KEY_VALIDATION_ERROR);
                    errRes.addErrorInfo("hatSruiKbn", CCMessageConst.MSG_KEY_INPUT_TWO_DIGITS);
                    bChkData = false;
                }
                // 半角のみチェック
                if (CCStringUtil.getByteLen(req.getHatSruiKbn()) != req.getHatSruiKbn().length()) {
                    errRes.addErrorInfo(CCMessageConst.MSG_KEY_VALIDATION_ERROR);
                    errRes.addErrorInfo("hatSruiKbn", CCMessageConst.MSG_KEY_INPUT_TWO_DIGITS);
                    bChkData = false;
                }
                // 存在チェック
                if (!ccCheckExistsUtil.existsHatSruiKbn(req.getHatDdSt(), req.getHatSruiKbn())) {
                    errRes.addErrorInfo(CCMessageConst.MSG_KEY_VALIDATION_ERROR);
                    errRes.addErrorInfo("hatSruiKbn", CCMessageConst.MSG_KEY_MEIM_NOT_EXIST);
                    bChkData = false;
                }
            }

            // 入力データチェック
            if (!bChkData) {
                return badRequest(Json.toJson(errRes));
            }

            // ＣＳＶ・ＰＤＦのパスを取得
            if (req.getKaisyaCd() == null) {
                req.setKaisyaCd(StringUtils.EMPTY);
            }
            if (req.getHatDdSt() == null) {
                req.setHatDdSt(StringUtils.EMPTY);
            }
            if (req.getHatDdEd() == null) {
                req.setHatDdEd(StringUtils.EMPTY);
            }
            if (req.getJigyobuCd() == null) {
                req.setJigyobuCd(StringUtils.EMPTY);
            }
            if (req.getBmnSt() == null) {
                req.setBmnSt(StringUtils.EMPTY);
            }
            if (req.getBmnEd() == null) {
                req.setBmnEd(StringUtils.EMPTY);
            }
            if (req.getTenCdSt() == null) {
                req.setTenCdSt(StringUtils.EMPTY);
            }
            if (req.getTenCdEd() == null) {
                req.setTenCdEd(StringUtils.EMPTY);
            }
            if (req.getHatSruiKbn() == null) {
                req.setHatSruiKbn(StringUtils.EMPTY);
            }
            List<H130herr> h130herrs = dao.selectMany("selectH130herr", req, H130herr.class);
            if (h130herrs.size() == 0) {
                return notFound();
            } else {
                // ＣＳＶ作成（データ取得）
                CCReportUtil cru = new CCReportUtil("HASV1010", context);
                CsvManager csvManager = CsvManagerFactory.newCsvManager();
                List<Hapr1010Report> csvBean = new ArrayList<Hapr1010Report>();
                String sTantoCode = context.getTantoCode();
                for (H130herr h130herr : h130herrs) {
                    Hapr1010Report hapr1010Report = new Hapr1010Report();
                    hapr1010Report.h00_progid = "HAPR1010";
                    hapr1010Report.h00_version = "1.00";
                    hapr1010Report.h01_tanto_code = sTantoCode;
                    hapr1010Report.m01_ten_cd = h130herr.getTenCd();
                    hapr1010Report.m01_ten_nm = h130herr.getTenNm();
                    hapr1010Report.m01_bmn_cd = h130herr.getBmnCd();
                    hapr1010Report.m01_tokubai_kbn = h130herr.getTokKbn();
                    hapr1010Report.m01_kikaku_cd = h130herr.getTokCd();
                    hapr1010Report.m01_syohin_cd = h130herr.getShnCd();
                    hapr1010Report.m01_syohin_nm = h130herr.getShnNm();
                    hapr1010Report.m01_mkr_nm = h130herr.getMkrNm();
                    hapr1010Report.m01_kikaku_nm = h130herr.getKikakuNm();
                    hapr1010Report.m01_hat_su = String.valueOf(h130herr.getHatSu());
                    hapr1010Report.m01_hat_dd = h130herr.getHatDd();
                    hapr1010Report.m01_nhn_dd = h130herr.getNhnDd();
                    hapr1010Report.m01_pot_dd = h130herr.getSendDd();
                    hapr1010Report.m01_pot_time = h130herr.getSendTime();
                    hapr1010Report.m01_hat_srui_kbn = h130herr.getHatSruiKbn();
                    hapr1010Report.m01_hat_srui = h130herr.getHatSrui();
                    hapr1010Report.m01_tri_cd = h130herr.getTriCd();
                    hapr1010Report.m01_tri_nm = h130herr.getTriNm();
                    hapr1010Report.m01_err_cd = h130herr.getErrCd();
                    hapr1010Report.m01_errmsg = h130herr.getErrMsg();
                    hapr1010Report.m01_filler = h130herr.getFiller();

                    csvBean.add(hapr1010Report);
                }
                // ＣＳＶ作成（データ出力）
                try {
                    csvManager.save(csvBean, Hapr1010Report.class).to(new File(cru.getCsvFilePath()),
                            CCReportUtil.CSV_OUTPUT_ENCODING);
                } catch (Exception e) {
                    throw new ChaseException(e);
                }

                cru.makePDF();
                List<Hajp1010ResultForm> reportList = new ArrayList<Hajp1010ResultForm>();
                Hajp1010ResultForm resultForm = new Hajp1010ResultForm();
                try {
                    URL uPdfUrl = new URL(cru.getPdfUrl());
                    resultForm.setPdfUrl(uPdfUrl);
                } catch (Exception e) {
                    throw new ChaseException(e);
                }
                reportList.add(resultForm);
                // gc対応
                cru = null;
                System.gc();

                return ok(Json.toJson(reportList));
            }
        }
    }
}
