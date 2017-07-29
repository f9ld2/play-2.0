// /////////////////////////////////////////////////////////////////////
// Copyright(C) 2015 NEC Solution Innovators, Ltd. All Rights Reserved.
// /////////////////////////////////////////////////////////////////////

/*
 * ====================================================================
 * 
 * 機能名称 : ＥＯＳ発注明細リスト出力
 * 
 * 改版履歴
 * 
 * Rev. 改版年月日 改版者名 内容
 * 
 * 1.0 2014.04.25 Tinnc 新規作成
 * 
 * ===================================================================
 */

package jp.co.necsoft.web_md.core.app.controllers.ha;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import jp.co.necsoft.web_md.common.ChaseException;
import jp.co.necsoft.web_md.common.mybatis.dao.MyBatisSqlMapDao;
import jp.co.necsoft.web_md.core.app.dto.ha.Hajp1040ExecuteInfo;
import jp.co.necsoft.web_md.core.app.dto.ha.Hajp1040Result;
import jp.co.necsoft.web_md.core.app.dto.ha.M007CodeMaster;
import jp.co.necsoft.web_md.core.app.dto.ha.M011CodeMaster;
import jp.co.necsoft.web_md.core.app.file.report.Hapr1040Report;
import jp.co.necsoft.web_md.core.app.forms.ha.Hajp1040CondForm;
import jp.co.necsoft.web_md.core.app.forms.ha.Hajp1040ResultForm;
import jp.co.necsoft.web_md.core.common.CCMessageConst;
import jp.co.necsoft.web_md.core.common.CCSecurityAuthenticator;
import jp.co.necsoft.web_md.core.common.CCSystemContext;
import jp.co.necsoft.web_md.core.common.forms.ErrorInfo;
import jp.co.necsoft.web_md.core.common.forms.ErrorResponse;
import jp.co.necsoft.web_md.core.common.mybatis.dao.M017meimMapper;
import jp.co.necsoft.web_md.core.common.mybatis.dto.M017meim;
import jp.co.necsoft.web_md.core.common.mybatis.dto.M017meimExample;
import jp.co.necsoft.web_md.core.common.utils.CCCheckExistsUtil;
import jp.co.necsoft.web_md.core.common.utils.CCDateUtil;
import jp.co.necsoft.web_md.core.common.utils.CCNumberUtil;
import jp.co.necsoft.web_md.core.common.utils.CCReportUtil;
import jp.co.necsoft.web_md.core.common.utils.CCStringUtil;
import jp.co.necsoft.web_md.core.common.utils.DataUtil;
import play.data.Form;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;

import com.orangesignal.csv.manager.CsvManager;
import com.orangesignal.csv.manager.CsvManagerFactory;

/**
*ＥＯＳ発注明細リスト出力

のControllerクラス
*
*/

@Security.Authenticated(CCSecurityAuthenticator.class)
public class Hajp1040Ctrl extends Controller {
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

    /**M017meim Mapper*/
    @Inject
    private M017meimMapper m017meimMapper;

    /**
     * 削除
     */
    private static final String TYPE_ACT_KBN_DELETED = "9";

    /**
    * 引数とリクエスト(パラメータ)を条件にDBからデータ(複数件)を検索し返却する。*
    *  @param kaisyaCd 会社コード
    *  @param hatYmdSt 発注日開始
    *  @param hatYmdEd 発注日終了
    *  @return クライアントへ返却する結果
    *  @throws Exception 
    */
    public Result query(String kaisyaCd, String hatYmdSt, String hatYmdEd) throws Exception {
        Form<Hajp1040CondForm> emptyForm = new Form<Hajp1040CondForm>(Hajp1040CondForm.class);
        Form<Hajp1040CondForm> reqForm = emptyForm.bindFromRequest();
        if (reqForm.hasErrors()) {
            errRes.addErrorInfo(CCMessageConst.MSG_KEY_VALIDATION_ERROR);
            errRes.addErrorInfo(reqForm.errors());
            return badRequest(Json.toJson(errRes));
        } else {
            Hajp1040CondForm req = reqForm.get();
            req.setKaisyaCd(kaisyaCd);
            req.setHatYmdSt(hatYmdSt);
            req.setHatYmdEd(hatYmdEd);

            // 入力データチェック
            if (!doCheckData(req)) {
                ErrorInfo error = errRes.getErrorInfo(CCMessageConst.MSG_KEY_VALIDATION_ERROR);
                errRes.getErrors().add(0, error);
                return badRequest(Json.toJson(errRes));
            }

            Hajp1040ExecuteInfo parrams = new Hajp1040ExecuteInfo();
            parrams.setKaisyaCd(req.getKaisyaCd());
            parrams.setHatYmdSt(req.getHatYmdSt());
            parrams.setHatYmdEd(req.getHatYmdEd());

            if (req.getJigyoubuCd() != null) {
                parrams.setJigyoubuCd(req.getJigyoubuCd());
            } else {
                parrams.setJigyoubuCd("");
            }

            if (req.getTenCdSt() != null) {
                parrams.setTenCdSt(req.getTenCdSt());
            } else {
                parrams.setTenCdSt("");
            }

            if (req.getTenCdEd() != null) {
                parrams.setTenCdEd(req.getTenCdEd());
            } else {
                parrams.setTenCdEd("");
            }

            parrams.setTorihikiCdSt("");
            if (!CCStringUtil.isEmpty(req.getTorihikiCdSt())) {
                parrams.setTorihikiCdSt(req.getTorihikiCdSt());
            }

            parrams.setTorihikiCdEd("");
            if (!CCStringUtil.isEmpty(req.getTorihikiCdEd())) {
                parrams.setTorihikiCdEd(req.getTorihikiCdEd());
            }

            if (req.getHatShoriKbn() != null) {
                parrams.setHatShoriKbn(req.getHatShoriKbn());
            } else {
                parrams.setHatShoriKbn("");
            }

            List<Hajp1040Result> list = dao.selectMany("selectHajp1040", parrams, Hajp1040Result.class);

            if (list.size() == 0) {
                errRes.addErrorInfo(CCMessageConst.MSG_KEY_NO_FOUND_DATA);
                return notFound();
            }

            CCReportUtil cru = new CCReportUtil("HASV1040", context);
            CsvManager csvManager = CsvManagerFactory.newCsvManager();
            List<Hapr1040Report> csvBean = new ArrayList<Hapr1040Report>();
            Map<String, M011CodeMaster> triNmMap = new HashMap<String, M011CodeMaster>();
            Map<String, String[]> hatNmMap = new HashMap<String, String[]>();
            Map<String, String> param = new HashMap<String, String>();
            Map<String, String> shnNmMap = new HashMap<String, String>();

            // 担当者コードを取得します。
            String tantoCode = context.getTantoCode();

            for (Hajp1040Result record : list) {

                for (int iMesaiSu = 1; iMesaiSu <= 10; iMesaiSu++) {
                    String inx = CCStringUtil.suppZero(String.valueOf(iMesaiSu), 2);
                    Object shnCd = DataUtil.getValueByMethodName(record, "getShnCd" + inx);
                    // 商品ｺｰﾄﾞ格納
                    String sShnCd = String.valueOf(shnCd);

                    // 商品ｺｰﾄﾞ未入力ならば処理しない
                    if (!CCStringUtil.isEmpty(sShnCd.trim()) && !" 0000000000000".equals(sShnCd)
                            && !"00000000000000".equals(sShnCd)) {
                        Hapr1040Report row = new Hapr1040Report();

                        row.h00_progid = "HAPR1040";
                        row.h00_version = "1.00";
                        row.h01_tanto_code = tantoCode;

                        // 取引先名・電話番号・ＦＡＸ番号を取得する
                        String triCd = record.getTorihikiCd();
                        M011CodeMaster m011 = triNmMap.get(triCd);
                        if (m011 == null) {
                            param = new HashMap<String, String>();
                            param.put("triCd", triCd);
                            param.put("hakkoDay", hatYmdSt);
                            List<M011CodeMaster> listM011 =
                                    dao.selectMany("selectHajp1040M011", param, M011CodeMaster.class);
                            if (!listM011.isEmpty() && !TYPE_ACT_KBN_DELETED.equals(listM011.get(0).getKubun())) {
                                m011 = listM011.get(0);
                            } else {
                                m011 = new M011CodeMaster();
                                m011.setCode("**********");
                                m011.setName("＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊");
                                m011.setTelNo1("********************");
                                m011.setFaxNo1("********************");
                            }
                            triNmMap.put(triCd, m011);
                        }

                        // 取引先コード
                        row.h01_sir_cd = m011.getCode();
                        // 取引先名
                        row.h01_sir_nm = m011.getName();
                        // 電話番号
                        row.h02_tel_no = m011.getTelNo1();
                        // ＦＡＸ番号
                        row.h02_fax_no = m011.getFaxNo1();

                        // 発注種類区分名を取得する
                        String hatKbn = record.getHatSyoriKbn();
                        String[] hatNmData = hatNmMap.get(hatKbn);
                        if (hatNmData == null) {
                            M017meimExample m017meimExample = new M017meimExample();
                            m017meimExample.createCriteria().andCdKbnEqualTo("HAT")
                                    .andCdEqualTo("HATSRUIKBN0000" + hatKbn);
                            m017meimExample.setSearchDate(hatYmdSt);

                            List<M017meim> listM017 = this.m017meimMapper.selectByExample(m017meimExample);

                            if (!listM017.isEmpty() && !TYPE_ACT_KBN_DELETED.equals(listM017.get(0).getActKbn())) {
                                hatNmData = new String[] {hatKbn, listM017.get(0).getNm() };
                            } else {
                                hatNmData = new String[] {"**", "＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊" };
                            }
                            hatNmMap.put(hatKbn, hatNmData);
                        }

                        // 発注種類区分
                        row.h01_hachu_syori_kbn = hatNmData[0];
                        // 発注種類区分
                        row.h01_hachu_syori_nm = hatNmData[1];

                        // 店舗コード
                        row.m01_ten_cd = record.getKaisyaCd() + record.getJigyoubuCd() + record.getTenCd();
                        // 店舗名
                        row.m01_ten_nm = record.getTenNmA();

                        // 部門コード
                        row.m01_bmn_cd = "";
                        if (!CCStringUtil.isEmpty(record.getBmnCd())) {
                            if ((String.valueOf(record.getBmnCd())).length() == 5) {
                                // 部門コード
                                row.m01_bmn_cd = String.valueOf(record.getBmnCd()).substring(2, 5);
                            } else {
                                // 部門コード
                                row.m01_bmn_cd = record.getBmnCd();
                            }
                        }

                        // 伝票番号
                        row.m01_den_no = record.getDpyNo();
                        // 特売区分
                        row.m01_tok_kbn = record.getTokKbn();
                        // 企画コード
                        row.m01_kikaku_cd = record.getKikakuCd();
                        // 発注日
                        row.m01_hat_dd = record.getHatYmd();
                        // 納品日
                        row.m01_nhn_cd = record.getNhnYoteiYmd();

                        Object mesaiNo = DataUtil.getValueByMethodName(record, "getMesaiNo" + inx);
                        // 行№
                        row.m01_gyo = String.valueOf(mesaiNo);
                        // 商品コード
                        row.m01_syohin_cd = sShnCd;

                        // 商品名・規格名を取得する

                        String shnNm = shnNmMap.get(sShnCd);
                        if (shnNm == null) {
                            List<String> shnCdList = new ArrayList<String>();
                            shnCdList.add(sShnCd);
                            Map<String, Object> m007param = new HashMap<String, Object>();
                            m007param.put("searchDate", hatYmdSt);
                            m007param.put("shnCdList", shnCdList);

                            List<M007CodeMaster> m007List =
                                    dao.selectMany("selectHajp1040M007", m007param, M007CodeMaster.class);

                            if (!m007List.isEmpty() && !TYPE_ACT_KBN_DELETED.equals(m007List.get(0).getActKbn())) {
                                shnNm = m007List.get(0).getShnNm();
                                if (shnNm.length() > 25) {
                                    shnNm = shnNm.substring(0, 25);
                                }
                            } else {
                                shnNm = "*************************";
                            }
                            shnNmMap.put(sShnCd, shnNm);
                        }
                        // 商品名（カナ）
                        row.m01_syohin_name = shnNm;

                        // 規格数値
                        String kikakuSu = (String) DataUtil.getValueByMethodName(record, "getKikakuSu" + inx);
                        // 規格単位
                        String kikakuTani = (String) DataUtil.getValueByMethodName(record, "getKikakuTani" + inx);
                        // 企画名
                        row.m01_kikaku_nm = kikakuSu + kikakuTani;

                        // 入数
                        Object hattyuIrisu = DataUtil.getValueByMethodName(record, "getHattyuIrisu" + inx);
                        row.m01_irisu = String.valueOf(hattyuIrisu);

                        // ケース
                        Object hatCaseSu = DataUtil.getValueByMethodName(record, "getHatCaseSu" + inx);
                        row.m01_case = String.valueOf(hatCaseSu);

                        // 発注数
                        Object hatBaraSu = DataUtil.getValueByMethodName(record, "getHatBaraSu" + inx);
                        row.m01_hat_su = String.valueOf(hatBaraSu);

                        row.m01_teisei_su = "(     )";

                        // 原単価
                        Object hatGenk = DataUtil.getValueByMethodName(record, "getHatGenk" + inx);
                        row.m01_genk = String.valueOf(hatGenk);

                        // 原価金額
                        Object hatGenkKin = DataUtil.getValueByMethodName(record, "getHatGenkKin" + inx);
                        row.m01_genk_kin = String.valueOf(hatGenkKin);

                        // 売単価
                        Object hatBaik = DataUtil.getValueByMethodName(record, "getHatBaik" + inx);
                        row.m01_baik = String.valueOf(hatBaik);

                        // //売価金額
                        Object hatBaikKin = DataUtil.getValueByMethodName(record, "getHatBaikKin" + inx);
                        row.m01_baik_kin = String.valueOf(hatBaikKin);

                        // 合計原価金額
                        row.m01_s_genk_kin = String.valueOf(record.getsHatGenkKin());

                        // 合計売価金額
                        row.m01_s_baik_kin = String.valueOf(record.getsHatBaikKin());

                        csvBean.add(row);
                    }
                }
            }

            if (csvBean.isEmpty()) {
                // 対象データ０件
                errRes.addErrorInfo(CCMessageConst.MSG_KEY_VALIDATION_ERROR);
                errRes.addErrorInfo("", CCMessageConst.MSG_KEY_NO_FOUND_DATA);
                return notFound();
            }

            // ＣＳＶ作成（データ出力）
            try {
                csvManager.save(csvBean, Hapr1040Report.class).to(new File(cru.getCsvFilePath()),
                        CCReportUtil.CSV_OUTPUT_ENCODING);
            } catch (Exception e) {
                throw new ChaseException(e);
            }

            cru.makePDF();
            List<Hajp1040ResultForm> reportList = new ArrayList<Hajp1040ResultForm>();
            Hajp1040ResultForm resultForm = new Hajp1040ResultForm();
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

    /**
     * 入力データの整合性チェック処理
     * <p>
     * 機能概要：<br>
     * 入力データの整合性をチェックします。
     * <p> 
     * 作成日：<br> 
     * 2014/04/08（Mars）<br> 
     * 新規作成<br>
     * <p> 
     * @param req なし
     * @return true：処理成功、false：処理失敗
     **/
    private boolean doCheckData(Hajp1040CondForm req) {
        boolean bChkData = true;
        String sTriFrCd = "";
        String sTriToCd = "";
        String sShiFrCd = "";
        String sShiToCd = "";
        // 会社チェック
        if (CCStringUtil.getByteLen(req.getKaisyaCd()) != req.getKaisyaCd().length()) {
            errRes.addErrorInfo("kaisyaCd", CCMessageConst.MSG_KEY_ENTER_INPUT_TWO_DIGITS);
            bChkData = false;

        }

        // 事業部チェック
        if (!CCStringUtil.isEmpty(req.getJigyoubuCd())) {
            if ((req.getJigyoubuCd().length() != 2)
                    || (CCStringUtil.getByteLen(req.getJigyoubuCd()) != req.getJigyoubuCd().length())) {
                errRes.addErrorInfo("jigyoubuCd", CCMessageConst.MSG_KEY_ENTER_INPUT_TWO_DIGITS);
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
                errRes.addErrorInfo("tenCdSt", CCMessageConst.MSG_KEY_INPUT_THREE_DIGITS_LONG);
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
                errRes.addErrorInfo("tenCdEd", CCMessageConst.MSG_KEY_INPUT_THREE_DIGITS_LONG);
                bChkData = false;
            }
            if (!CCNumberUtil.isNumeric(req.getTenCdEd())) {
                errRes.addErrorInfo("tenCdEd", CCMessageConst.MSG_KEY_INPUT_THREE_DIGITS);
                bChkData = false;
            }
        }

        // 店舗整合性チェック
        if (CCStringUtil.isEmpty(req.getJigyoubuCd()) && !CCStringUtil.isEmpty(req.getTenCdSt())) {
            errRes.addErrorInfo("jigyoubuCd", CCMessageConst.MSG_KEY_INPUT_REQUIRED_ENTER);
            bChkData = false;
        }
        if (CCStringUtil.isEmpty(req.getJigyoubuCd()) && !CCStringUtil.isEmpty(req.getTenCdEd())) {
            errRes.addErrorInfo("jigyoubuCd", CCMessageConst.MSG_KEY_INPUT_REQUIRED_ENTER);
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
        if (CCStringUtil.getByteLen(req.getHatYmdSt()) != req.getHatYmdSt().length()) {
            errRes.addErrorInfo("hatYmdSt", CCMessageConst.MSG_KEY_INPUT_EIGHT_DIGITS);
            bChkData = false;

        } else {
            if (!CCDateUtil.isDate(req.getHatYmdSt())) {
                errRes.addErrorInfo("hatYmdSt", CCMessageConst.MSG_KEY_RE_ENTER_NO_PARAM);
                bChkData = false;

            } else {
                if (req.getHatYmdSt().compareTo(context.getUnyoDate()) > 0) {
                    errRes.addErrorInfo("hatYmdSt", CCMessageConst.MSG_KEY_INPUT_NOT_MATCH_DATE);
                    bChkData = false;

                }
            }
        }

        // 発注日ＴＯチェック
        if (CCStringUtil.getByteLen(req.getHatYmdEd()) != req.getHatYmdEd().length()) {
            errRes.addErrorInfo("hatYmdEd", CCMessageConst.MSG_KEY_INPUT_EIGHT_DIGITS);
            bChkData = false;

        } else {
            if (!CCDateUtil.isDate(req.getHatYmdEd())) {
                errRes.addErrorInfo("hatYmdEd", CCMessageConst.MSG_KEY_RE_ENTER_NO_PARAM);
                bChkData = false;

            }
        }

        // 発注日整合性チェック
        if (req.getHatYmdSt().compareTo(req.getHatYmdEd()) > 0) {
            errRes.addErrorInfo("hatYmdSt", CCMessageConst.MSG_KEY_ERROR_ORDER_DATE_FR_GREATER);
            errRes.addErrorInfo("hatYmdEd", CCMessageConst.MSG_KEY_ERROR_ORDER_DATE_FR_GREATER);
            bChkData = false;

        }

        // 取引先ＦＲチェック
        if (!CCStringUtil.isEmpty(req.getTorihikiCdSt())) {
            if ((req.getTorihikiCdSt().length() != 9)
                    || (CCStringUtil.getByteLen(req.getTorihikiCdSt()) != req.getTorihikiCdSt().length())) {
                errRes.addErrorInfo("torihikiCdSt", CCMessageConst.MSG_KEY_ENTER_LENGTH_NINE_DIGITS);
                bChkData = false;

            } else {
                sTriFrCd = req.getTorihikiCdSt().substring(0, 6);
                sShiFrCd = req.getTorihikiCdSt().substring(6, 9);
            }
        }

        // 取引先ＴＯチェック
        if (!CCStringUtil.isEmpty(req.getTorihikiCdEd())) {
            if ((req.getTorihikiCdEd().length() != 9)
                    || (CCStringUtil.getByteLen(req.getTorihikiCdEd()) != req.getTorihikiCdEd().length())) {
                errRes.addErrorInfo("torihikiCdEd", CCMessageConst.MSG_KEY_ENTER_LENGTH_NINE_DIGITS);
                bChkData = false;

            } else {
                sTriToCd = req.getTorihikiCdEd().substring(0, 6);
                sShiToCd = req.getTorihikiCdEd().substring(6, 9);
            }
        }

        // 取引先がFROMとTO両方入力されて、FROMの方がTOより大きい場合
        if (!CCStringUtil.isEmpty(sTriFrCd) && !CCStringUtil.isEmpty(sTriToCd)) {
            if (sTriFrCd.compareTo(sTriToCd) > 0) {
                errRes.addErrorInfo("torihikiCdSt", CCMessageConst.MSG_KEY_ERROR_TORIHIKI_FR_GREATER);
                errRes.addErrorInfo("torihikiCdEd", CCMessageConst.MSG_KEY_ERROR_TORIHIKI_FR_GREATER);
                bChkData = false;

            }
        }

        // 取引先、支店ともにFROM･TOが入力された場合に、取引先コードの値が異なる場合
        if (!CCStringUtil.isEmpty(sTriFrCd) && !CCStringUtil.isEmpty(sTriToCd)) {
            if (!sTriFrCd.equals(sTriToCd)) {
                errRes.addErrorInfo("torihikiCdSt", CCMessageConst.MSG_KEY_REQUIRED_TORIHIKI_SAME_ENTER);
                errRes.addErrorInfo("torihikiCdEd", CCMessageConst.MSG_KEY_REQUIRED_TORIHIKI_SAME_ENTER);
                bChkData = false;
            }
        }

        // 取引先･支店(TO)が入力されて、取引先･支店(FR)が未入力の場合
        if (CCStringUtil.isEmpty(sTriFrCd) && CCStringUtil.isEmpty(sShiFrCd) && !CCStringUtil.isEmpty(sTriToCd)
                && !CCStringUtil.isEmpty(sShiToCd)) {
            errRes.addErrorInfo("torihikiCdSt", CCMessageConst.MSG_KEY_INPUT_REQUIRED_ENTER);
            bChkData = false;
        }

        // 取引先がFROMとTO両方同じ値が入力されていて、さらに支店もFROMとTO両方入力されていた場合に
        // 支店FROMの方が支店TOよりも大きい場合
        if (!"".equals(sTriFrCd) && !"".equals(sTriToCd)) {
            if (sTriFrCd.equals(sTriToCd)) {
                if (!"".equals(sShiFrCd) && !"".equals(sShiToCd)) {
                    if (sShiFrCd.compareTo(sShiToCd) > 0) {
                        errRes.addErrorInfo("torihikiCdSt", CCMessageConst.MSG_KEY_ERROR_BRANCH_FR_GREATER);
                        errRes.addErrorInfo("torihikiCdEd", CCMessageConst.MSG_KEY_ERROR_BRANCH_FR_GREATER);
                        bChkData = false;

                    }
                }
            }
        }

        // 会社存在チェック
        if (!ccCheckExistsUtil.existsKaisyaCd(req.getHatYmdSt(), req.getKaisyaCd())) {
            errRes.addErrorInfo("kaisyaCd", CCMessageConst.MSG_KEY_NOT_REGISTERED_NO_PARAM);
            bChkData = false;

        }

        // 事業部存在チェック
        if (!CCStringUtil.isEmpty(req.getJigyoubuCd())) {
            if (!ccCheckExistsUtil.existsKaisyaCdAndJigyobuCd(req.getHatYmdSt(), req.getKaisyaCd(),
                    req.getJigyoubuCd())) {
                errRes.addErrorInfo("jigyoubuCd", CCMessageConst.MSG_KEY_NOT_REGISTERED_NO_PARAM);
                bChkData = false;
            }
        }

        // 店舗ＦＲ存在チェック
        if (!CCStringUtil.isEmpty(req.getJigyoubuCd()) && !CCStringUtil.isEmpty(req.getTenCdSt())) {
            if (!ccCheckExistsUtil.existsTenCd(req.getHatYmdSt(),
                    req.getKaisyaCd() + req.getJigyoubuCd() + req.getTenCdSt())) {
                errRes.addErrorInfo("tenCdSt", CCMessageConst.MSG_KEY_NOT_REGISTERED_NO_PARAM);
                bChkData = false;
            }
        }

        // 店舗ＴＯ存在チェック
        if (!CCStringUtil.isEmpty(req.getJigyoubuCd()) && !CCStringUtil.isEmpty(req.getTenCdEd())) {
            if (!ccCheckExistsUtil.existsTenCd(req.getHatYmdSt(),
                    req.getKaisyaCd() + req.getJigyoubuCd() + req.getTenCdEd())) {
                errRes.addErrorInfo("tenCdEd", CCMessageConst.MSG_KEY_NOT_REGISTERED_NO_PARAM);
                bChkData = false;
            }
        }

        // 取引先ＦＲ存在チェック
        if (!CCStringUtil.isEmpty(sTriFrCd)) {
            if (!ccCheckExistsUtil.existTriCd(req.getHatYmdSt(), req.getTorihikiCdSt())) {
                errRes.addErrorInfo("torihikiCdSt", CCMessageConst.MSG_KEY_NOT_REGISTERED_NO_PARAM);
                bChkData = false;
            }
        }

        // 取引先ＴＯ存在チェック
        if (!CCStringUtil.isEmpty(sTriToCd) && !CCStringUtil.isEmpty(sShiToCd)) {
            if (!ccCheckExistsUtil.existTriCd(req.getHatYmdSt(), sTriToCd + sShiToCd)) {
                errRes.addErrorInfo("torihikiCdEd", CCMessageConst.MSG_KEY_NOT_REGISTERED_NO_PARAM);
                bChkData = false;
            }
        }

        // 発注処理種別存在チェック
        if (!CCStringUtil.isEmpty(req.getHatShoriKbn())) {
            if (!existsHatSyoriKbn(req.getHatYmdSt(), req.getHatShoriKbn())) {
                errRes.addErrorInfo("hatShoriKbn", CCMessageConst.MSG_KEY_NOT_REGISTERED_NO_PARAM);
                bChkData = false;
            }
        }
        return bChkData;
    }

    /**
     * Function check exists hatSruiKBN
     * 
     * @param date input date
     * @param hatSruiKbn input 
     * @return boolean return exists or not
     */
    private boolean existsHatSyoriKbn(String date, String hatSruiKbn) {
        M017meimExample example = new M017meimExample();
        example.createCriteria().andCdKbnEqualTo("HAT").andCdEqualTo("HATSRUIKBN0000" + hatSruiKbn);
        example.setSearchDate(date);

        List<M017meim> list = this.m017meimMapper.selectByExample(example);
        if (list.size() > 0) {
            if (!TYPE_ACT_KBN_DELETED.equals(list.get(0).getActKbn())) {
                return true;
            }
        }
        return false;
    }
}
