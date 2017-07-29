// /////////////////////////////////////////////////////////////////////
// Copyright(C) 2015 NEC Solution Innovators, Ltd. All Rights Reserved.
// /////////////////////////////////////////////////////////////////////

/*
 * ====================================================================
 * 
 * 機能名称 : 発注プルーフリスト出力
 * 
 * 改版履歴
 * 
 * Rev. 改版年月日 改版者名 内容
 * 
 * 1.0 2014.04.20 Tinnc 新規作成
 * 
 * ===================================================================
 */

package jp.co.necsoft.web_md.core.app.controllers.ha;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;

import jp.co.necsoft.web_md.common.ChaseException;
import jp.co.necsoft.web_md.common.mybatis.dao.MyBatisSqlMapDao;
import jp.co.necsoft.web_md.core.app.dto.ha.H010hnbhHajp1020Result;
import jp.co.necsoft.web_md.core.app.dto.ha.Hajp1020ExecuteInfo;
import jp.co.necsoft.web_md.core.app.file.report.Hapr1020Report;
import jp.co.necsoft.web_md.core.app.forms.ha.Hajp1020CondForm;
import jp.co.necsoft.web_md.core.app.forms.ha.Hajp1020ResultForm;
import jp.co.necsoft.web_md.core.common.CCKubunConst;
import jp.co.necsoft.web_md.core.common.CCMessageConst;
import jp.co.necsoft.web_md.core.common.CCSecurityAuthenticator;
import jp.co.necsoft.web_md.core.common.CCSystemContext;
import jp.co.necsoft.web_md.core.common.forms.ErrorInfo;
import jp.co.necsoft.web_md.core.common.forms.ErrorResponse;
import jp.co.necsoft.web_md.core.common.mybatis.dao.M003bmnmMapper;
import jp.co.necsoft.web_md.core.common.mybatis.dao.M006tenmMapper;
import jp.co.necsoft.web_md.core.common.mybatis.dao.M007kijmMapper;
import jp.co.necsoft.web_md.core.common.mybatis.dao.M009msymMapper;
import jp.co.necsoft.web_md.core.common.mybatis.dao.M011trimMapper;
import jp.co.necsoft.web_md.core.common.mybatis.dao.M017meimMapper;
import jp.co.necsoft.web_md.core.common.mybatis.dto.M003bmnm;
import jp.co.necsoft.web_md.core.common.mybatis.dto.M003bmnmExample;
import jp.co.necsoft.web_md.core.common.mybatis.dto.M006tenm;
import jp.co.necsoft.web_md.core.common.mybatis.dto.M006tenmExample;
import jp.co.necsoft.web_md.core.common.mybatis.dto.M007kijm;
import jp.co.necsoft.web_md.core.common.mybatis.dto.M007kijmExample;
import jp.co.necsoft.web_md.core.common.mybatis.dto.M009msym;
import jp.co.necsoft.web_md.core.common.mybatis.dto.M009msymExample;
import jp.co.necsoft.web_md.core.common.mybatis.dto.M011trim;
import jp.co.necsoft.web_md.core.common.mybatis.dto.M011trimExample;
import jp.co.necsoft.web_md.core.common.mybatis.dto.M017meim;
import jp.co.necsoft.web_md.core.common.mybatis.dto.M017meimExample;
import jp.co.necsoft.web_md.core.common.utils.CCCheckExistsUtil;
import jp.co.necsoft.web_md.core.common.utils.CCDateUtil;
import jp.co.necsoft.web_md.core.common.utils.CCNumberUtil;
import jp.co.necsoft.web_md.core.common.utils.CCReportUtil;
import jp.co.necsoft.web_md.core.common.utils.CCStringUtil;
import play.data.Form;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;

import com.orangesignal.csv.manager.CsvManager;
import com.orangesignal.csv.manager.CsvManagerFactory;

/**
*発注プルーフリスト出力のControllerクラス
*
*/
@Security.Authenticated(CCSecurityAuthenticator.class)
public class Hajp1020Ctrl extends Controller {
    /**エラー応答*/
    @Inject
    private ErrorResponse errRes;
    /**チェックはUtilの存在*/
    @Inject
    private CCCheckExistsUtil ccCheckExistsUtil;
    /**SQLマップ*/
    @Inject
    private MyBatisSqlMapDao dao;
    /**システムコンテキスト*/
    @Inject
    private CCSystemContext context;
    /** M009msymMapper */
    @Inject
    private M009msymMapper m009msymMapper;
    /** M003bmnmMapper */
    @Inject
    private M003bmnmMapper m003bmnmMapper;
    /**M007kijmMapper*/
    @Inject
    private M007kijmMapper m007kijmMapper;
    /**M006tenmMapper*/
    @Inject
    private M006tenmMapper m006tenmMapper;
    /**M017meimMapper*/
    @Inject
    private M017meimMapper m017meimMapper;
    /**M011trimMapper*/
    @Inject
    private M011trimMapper m011trimMapper;
    /**
     * 削除
     */
    private static final String TYPE_ACT_KBN_DELETED = "9";

    /**
    * 引数とリクエスト(パラメータ)を条件にDBからデータ(複数件)を検索し返却する。*
    *  @param kaisyaCd 会社コード
    *  @param hachuSt 発注日開始
    *  @param hachuEd 発注日終了
    *  @return クライアントへ返却する結果
    */
    public Result query(String kaisyaCd, String hachuSt, String hachuEd) {
        Form<Hajp1020CondForm> emptyForm = new Form<Hajp1020CondForm>(Hajp1020CondForm.class);
        Form<Hajp1020CondForm> reqForm = emptyForm.bindFromRequest();
        if (reqForm.hasErrors()) {
            errRes.addErrorInfo(CCMessageConst.MSG_KEY_VALIDATION_ERROR);
            errRes.addErrorInfo(reqForm.errors());
            return badRequest(Json.toJson(errRes));
        } else {
            Hajp1020CondForm req = reqForm.get();
            req.setKaisyaCd(kaisyaCd);
            req.setHachuSt(hachuSt);
            req.setHachuEd(hachuEd);

            // 入力データチェック
            if (!doCheckData(req)) {
                ErrorInfo error = errRes.getErrorInfo(CCMessageConst.MSG_KEY_VALIDATION_ERROR);
                errRes.getErrors().add(0, error);
                return badRequest(Json.toJson(errRes));
            }

            Hajp1020ExecuteInfo parram = new Hajp1020ExecuteInfo();
            parram.setKaisyaCd(kaisyaCd);
            parram.setHachuSt(hachuSt);
            parram.setHachuEd(hachuEd);

            parram.setJigyobuCd(req.getJigyobuCd());
            if (parram.getJigyobuCd() == null) {
                parram.setJigyobuCd(StringUtils.EMPTY);
            }

            parram.setTenCdSt(req.getTenCdSt());
            if (parram.getTenCdSt() == null) {
                parram.setTenCdSt(StringUtils.EMPTY);
            }

            parram.setTenCdEd(req.getTenCdEd());
            if (parram.getTenCdEd() == null) {
                parram.setTenCdEd(StringUtils.EMPTY);
            }

            parram.setHatSruiKbn(req.getHatSruiKbn());
            if (parram.getHatSruiKbn() == null) {
                parram.setHatSruiKbn(StringUtils.EMPTY);
            }

            parram.setSort(req.getSort());
            if (parram.getSort() == null) {
                parram.setSort(StringUtils.EMPTY);
            }

            List<H010hnbhHajp1020Result> list =
                    dao.selectMany("selectH010hnbhHajp1020", parram, H010hnbhHajp1020Result.class);

            if (list.size() == 0) {
                errRes.addErrorInfo(CCMessageConst.MSG_KEY_NO_FOUND_DATA);
                return notFound();
            }

            // ＣＳＶのヘッダーを出力
            CCReportUtil cru;
            if (CCKubunConst.KBN_VAL_H_SORT_ORDER_ITEM.equals(req.getSort())) {
                cru = new CCReportUtil("HASV1020B", context);
            } else if (CCKubunConst.KBN_VAL_H_SORT_ORDER_STORE.equals(req.getSort())) {
                cru = new CCReportUtil("HASV1020C", context);
            } else {
                cru = new CCReportUtil("HASV1020A", context);
            }
            CsvManager csvManager = CsvManagerFactory.newCsvManager();
            List<Hapr1020Report> csvBean = new ArrayList<Hapr1020Report>();

            // 担当者コードを取得します。
            String tantoCode = context.getTantoCode();

            for (H010hnbhHajp1020Result hajp1020Result : list) {
                Hapr1020Report hapr1020Report = new Hapr1020Report();
                hapr1020Report.h00_progid = "HAPR1020";
                hapr1020Report.h00_version = "1.00";
                hapr1020Report.h01_tanto_code = tantoCode;

                // 発注日
                hapr1020Report.h01_hat_dd = hajp1020Result.getHatDd();

                // 部門コード・部門名を取得する
                M009msymExample m009msymExample = new M009msymExample();
                // 店舗コード
                String sTenCdSv = hajp1020Result.getKaiCd() + hajp1020Result.getJgbCd() + hajp1020Result.getTenCd();
                m009msymExample.createCriteria().andJanCdEqualTo(hajp1020Result.getShnCd())
                        .andTenCdEqualTo(sTenCdSv);
                m009msymExample.setSearchDate(req.getHachuSt());
                List<M009msym> listM009msym = m009msymMapper.selectByExample(m009msymExample);
                // 部門コード・部門名を取得する
                if (listM009msym.size() == 0) {
                    hapr1020Report.m01_bmn_cd = "*****";
                    hapr1020Report.m01_bmn_nm = "＊＊＊＊＊";
                } else {
                    if (TYPE_ACT_KBN_DELETED.equals(listM009msym.get(0).getActKbn())) {
                        hapr1020Report.m01_bmn_cd = "*****";
                        hapr1020Report.m01_bmn_nm = "＊＊＊＊＊";
                    } else {
                        hapr1020Report.m01_bmn_cd = listM009msym.get(0).getBmnCd();

                        M003bmnmExample m003bmnmExample = new M003bmnmExample();
                        m003bmnmExample.createCriteria().andBmnCdEqualTo(listM009msym.get(0).getBmnCd());
                        m003bmnmExample.setSearchDate(req.getHachuSt());
                        List<M003bmnm> listM003bmnm = m003bmnmMapper.selectByExample(m003bmnmExample);
                        if (listM003bmnm.size() > 0) {
                            if (!TYPE_ACT_KBN_DELETED.equals(listM003bmnm.get(0))) {
                                // 部門名略称
                                hapr1020Report.m01_bmn_nm = listM003bmnm.get(0).getBmnNmR();
                            } else {
                                hapr1020Report.m01_bmn_nm = "＊＊＊＊＊";
                            }
                        } else {
                            hapr1020Report.m01_bmn_nm = "＊＊＊＊＊";
                        }
                    }
                }
                // 商品コード
                hapr1020Report.m01_syohin_cd = hajp1020Result.getShnCd();

                // 商品名・規格名を取得する
                M007kijmExample m007kijmExample = new M007kijmExample();

                m007kijmExample.createCriteria().andJanCdEqualTo(hajp1020Result.getShnCd())
                        .andHakkoDayToGreaterThanOrEqualTo(req.getHachuSt());

                m007kijmExample.setSearchDate(req.getHachuSt());

                List<M007kijm> listM007kijm = m007kijmMapper.selectByExample(m007kijmExample);

                if (listM007kijm.size() == 0) {
                    hapr1020Report.m01_syohin_nm = "＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊";
                    hapr1020Report.m01_kikaku_nm = "*******";
                } else {
                    if (TYPE_ACT_KBN_DELETED.equals(listM007kijm.get(0).getActKbn())) {
                        hapr1020Report.m01_syohin_nm = "＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊";
                        hapr1020Report.m01_kikaku_nm = "*******";
                    } else {
                        String sShnNm = listM007kijm.get(0).getShnNm();
                        if (sShnNm.length() > 15) {
                            sShnNm = sShnNm.substring(0, 15);
                        }
                        hapr1020Report.m01_syohin_nm = sShnNm;

                        String sKikakuNm = listM007kijm.get(0).getKikakuNmA();
                        if (sKikakuNm.length() > 7) {
                            sKikakuNm = sKikakuNm.substring(0, 7);
                        }
                        hapr1020Report.m01_kikaku_nm = sKikakuNm;
                    }
                }

                //
                hapr1020Report.m01_ten_cd =
                        hajp1020Result.getKaiCd() + hajp1020Result.getJgbCd() + hajp1020Result.getTenCd();

                // 店舗名称を取得する
                M006tenmExample m006tenmExample = new M006tenmExample();

                m006tenmExample.createCriteria().andTenCdEqualTo(sTenCdSv);

                m006tenmExample.setSearchDate(req.getHachuSt());

                List<M006tenm> listM006tenm = m006tenmMapper.selectByExample(m006tenmExample);

                if (listM006tenm.size() == 0) {
                    hapr1020Report.m01_ten_nm = "＊＊＊＊＊";
                } else {
                    if (TYPE_ACT_KBN_DELETED.equals(listM006tenm.get(0).getActKbn())) {
                        hapr1020Report.m01_ten_nm = "＊＊＊＊＊";
                    } else {
                        hapr1020Report.m01_ten_nm = listM006tenm.get(0).getTenNmR1();
                    }
                }

                // 発注種類名称を取得する
                M017meimExample m017meimExample = new M017meimExample();

                m017meimExample.createCriteria().andCdKbnEqualTo("HAT")
                        .andCdEqualTo("HATSRUIKBN0000" + hajp1020Result.getHatSruiKbn());
                m017meimExample.setSearchDate(req.getHachuSt());
                List<M017meim> listM017meim = m017meimMapper.selectByExample(m017meimExample);
                if (listM017meim.size() == 0) {
                    hapr1020Report.m01_hat_syurui = "＊＊＊";
                } else {
                    if (TYPE_ACT_KBN_DELETED.equals(listM017meim.get(0).getActKbn())) {
                        hapr1020Report.m01_hat_syurui = "＊＊＊";
                    } else {
                        String sHatSruiNm = listM017meim.get(0).getNm();
                        if (sHatSruiNm.length() > 5) {
                            sHatSruiNm = sHatSruiNm.substring(0, 5);
                        }
                        hapr1020Report.m01_hat_syurui = sHatSruiNm;
                    }
                }

                hapr1020Report.m01_hat_su = String.valueOf(hajp1020Result.getHatSu());

                hapr1020Report.m01_nhn_dd = hajp1020Result.getNhnDd();

                hapr1020Report.m01_tri_cd = hajp1020Result.getTriCd();

                // 取引先名称を取得する
                if (CCStringUtil.isEmpty(CCStringUtil.trimBoth(hajp1020Result.getTriCd()))) {
                    hapr1020Report.m01_tri_nm = "　　　　　";
                } else {
                    M011trimExample m011trimExample = new M011trimExample();
                    m011trimExample.createCriteria().andTriCdEqualTo(hajp1020Result.getTriCd());
                    m011trimExample.setSearchDate(req.getHachuSt());
                    List<M011trim> listM011trim = m011trimMapper.selectByExample(m011trimExample);
                    if (listM011trim.size() == 0) {
                        hapr1020Report.m01_tri_nm = "＊＊＊＊＊";
                    } else {
                        if (TYPE_ACT_KBN_DELETED.equals(listM011trim.get(0).getActKbn())) {
                            hapr1020Report.m01_tri_nm = "＊＊＊＊＊";
                        } else {
                            hapr1020Report.m01_tri_nm = listM011trim.get(0).getTriNmR();
                        }
                    }
                }

                hapr1020Report.m01_genk = String.valueOf(hajp1020Result.getGenk());

                hapr1020Report.m01_baik = String.valueOf(hajp1020Result.getBaik());

                M017meimExample m017meimExample1 = new M017meimExample();

                m017meimExample1.createCriteria().andCdKbnEqualTo("HAT")
                        .andCdEqualTo("HATSIMEFLG00000" + hajp1020Result.getHatSimeFlg());
                m017meimExample1.setSearchDate(req.getHachuSt());
                List<M017meim> listM017meim1 = m017meimMapper.selectByExample(m017meimExample1);
                if (listM017meim1.size() == 0) {
                    hapr1020Report.m01_hat_jokyo = "＊＊＊";
                } else {
                    if (TYPE_ACT_KBN_DELETED.equals(listM017meim1.get(0).getActKbn())) {
                        hapr1020Report.m01_hat_jokyo = "＊＊＊";
                    } else {
                        String sShoriJokyo = listM017meim1.get(0).getNm();
                        if (sShoriJokyo.length() > 3) {
                            sShoriJokyo = sShoriJokyo.substring(0, 3);
                        }
                        hapr1020Report.m01_hat_jokyo = sShoriJokyo;
                    }
                }

                hapr1020Report.m01_hattyu_irisu = String.valueOf(hajp1020Result.getHattyuIrisu());

                csvBean.add(hapr1020Report);
            }

            if (csvBean.isEmpty()) {
                // 対象データ０件
                errRes.addErrorInfo(CCMessageConst.MSG_KEY_VALIDATION_ERROR);
                errRes.addErrorInfo("", CCMessageConst.MSG_KEY_NO_FOUND_DATA);
                return notFound();
            }

            // ＣＳＶ作成（データ出力）
            try {
                csvManager.save(csvBean, Hapr1020Report.class).to(new File(cru.getCsvFilePath()),
                        CCReportUtil.CSV_OUTPUT_ENCODING);
            } catch (Exception e) {
                throw new ChaseException(e);
            }

            cru.makePDF();
            List<Hajp1020ResultForm> reportList = new ArrayList<Hajp1020ResultForm>();
            Hajp1020ResultForm resultForm = new Hajp1020ResultForm();
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

    /*****************************************************************
     * 　入力データの整合性チェック処理
    * <p>
    * 機能概要：<br>
    * 入力データの整合性をチェックします。
    * <p> 
    * 作成日：<br> 
    *　2014/04/08（Mars）<br> 
    *　　新規作成<br>
    * <p> 
    * @param req なし
    * @return true：処理成功、false：処理失敗
    *****************************************************************/
    private boolean doCheckData(Hajp1020CondForm req) {
        boolean bChkData = true;
        // 会社チェック
        if (CCStringUtil.getByteLen(req.getKaisyaCd()) != req.getKaisyaCd().length()) {
            errRes.addErrorInfo("kaisyaCd", CCMessageConst.MSG_KEY_INPUT_TWO_DIGITS);
            bChkData = false;

        }

        // 事業部チェック
        if (!CCStringUtil.isEmpty(req.getJigyobuCd())) {
            if (req.getJigyobuCd().length() != 2) {
                errRes.addErrorInfo("jigyobuCd", CCMessageConst.MSG_KEY_INPUT_TWO_DIGITS);
                bChkData = false;

            }
            if (CCStringUtil.getByteLen(req.getJigyobuCd()) != req.getJigyobuCd().length()) {
                errRes.addErrorInfo("jigyobuCd", CCMessageConst.MSG_KEY_INPUT_TWO_DIGITS);
                bChkData = false;

            }
        }

        // 店舗ＦＲチェック
        if (!CCStringUtil.isEmpty(req.getTenCdSt())) {
            if (req.getTenCdSt().length() != 3) {
                errRes.addErrorInfo("tenCdSt", CCMessageConst.MSG_KEY_ENTER_INPUT_THREE_DIGITS);
                bChkData = false;

            }
            if (CCStringUtil.getByteLen(req.getTenCdSt()) != req.getTenCdSt().length()) {
                errRes.addErrorInfo("tenCdSt", CCMessageConst.MSG_KEY_ENTER_INPUT_THREE_DIGITS);
                bChkData = false;

            }
            if (!CCNumberUtil.isNumeric(req.getTenCdSt())) {
                errRes.addErrorInfo("tenCdSt", CCMessageConst.MSG_KEY_INPUT_THREE_DIGITS);
                bChkData = false;

            }
        }

        // 店舗ＴＯチェック
        if (!CCStringUtil.isEmpty(req.getTenCdEd())) {
            if (req.getTenCdEd().length() != 3) {
                errRes.addErrorInfo("tenCdEd", CCMessageConst.MSG_KEY_ENTER_INPUT_THREE_DIGITS);
                bChkData = false;

            }
            if (CCStringUtil.getByteLen(req.getTenCdEd()) != req.getTenCdEd().length()) {
                errRes.addErrorInfo("tenCdEd", CCMessageConst.MSG_KEY_ENTER_INPUT_THREE_DIGITS);
                bChkData = false;

            }
            if (!CCNumberUtil.isNumeric(req.getTenCdEd())) {
                errRes.addErrorInfo("tenCdEd", CCMessageConst.MSG_KEY_INPUT_THREE_DIGITS);
                bChkData = false;

            }
        }

        // 店舗整合性チェック
        if (CCStringUtil.isEmpty(req.getJigyobuCd()) && !CCStringUtil.isEmpty(req.getTenCdSt())) {
            errRes.addErrorInfo("jigyobuCd", CCMessageConst.MSG_KEY_INPUT_REQUIRED_ENTER);
            bChkData = false;

        }
        if (CCStringUtil.isEmpty(req.getJigyobuCd()) && !CCStringUtil.isEmpty(req.getTenCdEd())) {
            errRes.addErrorInfo("jigyobuCd", CCMessageConst.MSG_KEY_INPUT_REQUIRED_ENTER);
            bChkData = false;

        }
        if (!CCStringUtil.isEmpty(req.getTenCdSt()) && !CCStringUtil.isEmpty(req.getTenCdEd())) {
            if (Integer.parseInt(req.getTenCdSt()) > Integer.parseInt(req.getTenCdEd())) {
                errRes.addErrorInfo("tenCdSt", CCMessageConst.MSG_KEY_TENCD_ERROR_COMPARE);
                errRes.addErrorInfo("tenCdEd", CCMessageConst.MSG_KEY_TENCD_ERROR_COMPARE);
                bChkData = false;

            }
        }

        // 発注日ＦＲチェック
        if (CCStringUtil.getByteLen(req.getHachuSt()) != req.getHachuSt().length()) {
            errRes.addErrorInfo("hachuSt", CCMessageConst.MSG_KEY_INPUT_EIGHT_DIGITS);
            bChkData = false;

        } else {
            if (!CCDateUtil.isDate(req.getHachuSt())) {
                errRes.addErrorInfo("hachuSt", CCMessageConst.MSG_KEY_RE_ENTER_NO_PARAM);
                bChkData = false;

            }
        }

        // 発注日ＴＯチェック
        if (CCStringUtil.getByteLen(req.getHachuEd()) != req.getHachuEd().length()) {
            errRes.addErrorInfo("hachuEd", CCMessageConst.MSG_KEY_INPUT_EIGHT_DIGITS);
            bChkData = false;

        } else {
            if (!CCDateUtil.isDate(req.getHachuEd())) {
                errRes.addErrorInfo("hachuEd", CCMessageConst.MSG_KEY_RE_ENTER_NO_PARAM);
                bChkData = false;

            }
        }

        // 発注日整合性チェック
        if (req.getHachuSt().compareTo(req.getHachuEd()) > 0) {
            errRes.addErrorInfo("hachuEd", CCMessageConst.MSG_KEY_ERROR_ORDER_DATE_FR_GREATER);
            errRes.addErrorInfo("hachuSt", CCMessageConst.MSG_KEY_ERROR_ORDER_DATE_FR_GREATER);
            bChkData = false;
        }

        // 発注種類種別チェック
        if (!CCStringUtil.isEmpty(req.getHatSruiKbn())) {
            if (req.getHatSruiKbn().length() != 2) {
                errRes.addErrorInfo("hachuSt", CCMessageConst.MSG_KEY_INPUT_TWO_DIGITS);
                bChkData = false;

            }
            if (CCStringUtil.getByteLen(req.getHatSruiKbn()) != req.getHatSruiKbn().length()) {
                errRes.addErrorInfo("hachuSt", CCMessageConst.MSG_KEY_INPUT_TWO_DIGITS);
                bChkData = false;

            }
        }

        // 出力順指定チェック
        if (!CCStringUtil.isEmpty(req.getSort())) {
            if (req.getSort().length() != 1) {
                errRes.addErrorInfo("sort", CCMessageConst.MSG_KEY_SORT_INPUT_NOT_MATCH_TYPE);
                bChkData = false;
            }
            if (!req.getSort().equals(CCKubunConst.KBN_VAL_H_SORT_ORDER_REGISTRATION)
                    && !req.getSort().equals(CCKubunConst.KBN_VAL_H_SORT_ORDER_ITEM)
                    && !req.getSort().equals(CCKubunConst.KBN_VAL_H_SORT_ORDER_STORE)) {
                errRes.addErrorInfo("sort", CCMessageConst.MSG_KEY_SORT_INPUT_NOT_MATCH_TYPE);
                bChkData = false;
            }
        }

        // 会社存在チェック
        if (!ccCheckExistsUtil.existsKaisyaCd(req.getHachuSt(), req.getKaisyaCd())) {
            errRes.addErrorInfo("kaisyaCd", CCMessageConst.MSG_KEY_NOT_REGISTERED_NO_PARAM);
            bChkData = false;

        }

        // 事業部存在チェック
        if (!CCStringUtil.isEmpty(req.getJigyobuCd()) && !CCStringUtil.isEmpty(req.getHachuSt())) {
            if (!ccCheckExistsUtil.existsKaisyaCdAndJigyobuCd(req.getHachuSt(), req.getKaisyaCd(),
                    req.getJigyobuCd())) {
                errRes.addErrorInfo("jigyobuCd", CCMessageConst.MSG_KEY_NOT_REGISTERED_NO_PARAM);
                bChkData = false;

            }
        }

        // 店舗ＦＲ存在チェック
        if (!CCStringUtil.isEmpty(req.getJigyobuCd()) && !CCStringUtil.isEmpty(req.getTenCdSt())) {
            if (!ccCheckExistsUtil.existsTenCd(req.getHachuSt(),
                    req.getKaisyaCd() + req.getJigyobuCd() + req.getTenCdSt())) {
                errRes.addErrorInfo("tenCdSt", CCMessageConst.MSG_KEY_NOT_REGISTERED_NO_PARAM);
                bChkData = false;
            }
        }

        // 店舗ＴＯ存在チェック
        if (!CCStringUtil.isEmpty(req.getJigyobuCd()) && !CCStringUtil.isEmpty(req.getTenCdEd())) {
            if (!ccCheckExistsUtil.existsTenCd(req.getHachuSt(),
                    req.getKaisyaCd() + req.getJigyobuCd() + req.getTenCdEd())) {
                errRes.addErrorInfo("tenCdEd", CCMessageConst.MSG_KEY_NOT_REGISTERED_NO_PARAM);
                bChkData = false;

            }
        }

        // 発注種類種別存在チェック
        if (!CCStringUtil.isEmpty(req.getHatSruiKbn())) {
            if (!ccCheckExistsUtil.existsHatSruiKbn(req.getHachuSt(), req.getHatSruiKbn())) {
                errRes.addErrorInfo("hatSruiKbn", CCMessageConst.MSG_KEY_MEIM_NOT_EXIST);
                bChkData = false;

            }
        }
        return bChkData;
    }
}
