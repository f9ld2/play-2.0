///////////////////////////////////////////////////////////////////////
// Copyright(C) 2015 NEC Solution Innovators, Ltd. All Rights Reserved.
///////////////////////////////////////////////////////////////////////

/* ====================================================================
 * 機能名称 : 買掛金残高一覧表
 * 改版履歴
 * Rev. 改版年月日   改版者名       内容
 * 1.0   2014.04.01   TUANVT      新規作成
 * =================================================================== */

package jp.co.necsoft.web_md.core.app.controllers.kk;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import jp.co.necsoft.web_md.common.ChaseException;
import jp.co.necsoft.web_md.common.mybatis.dao.MyBatisSqlMapDao;
import jp.co.necsoft.web_md.core.app.dto.kk.K002kruiM011trimReport;
import jp.co.necsoft.web_md.core.app.dto.kk.K002kruiM011trimResult;
import jp.co.necsoft.web_md.core.app.file.report.Kkpr1040Report;
import jp.co.necsoft.web_md.core.app.forms.kk.Kkjp1040CondForm;
import jp.co.necsoft.web_md.core.app.forms.kk.Kkjp1040ResultForm;
import jp.co.necsoft.web_md.core.common.CCMessageConst;
import jp.co.necsoft.web_md.core.common.CCSecurityAuthenticator;
import jp.co.necsoft.web_md.core.common.CCSystemContext;
import jp.co.necsoft.web_md.core.common.biz.kk.CCKKBeans;
import jp.co.necsoft.web_md.core.common.biz.kk.CCKKCommon;
import jp.co.necsoft.web_md.core.common.biz.kk.CCKKConst;
import jp.co.necsoft.web_md.core.common.forms.ErrorInfo;
import jp.co.necsoft.web_md.core.common.forms.ErrorResponse;
import jp.co.necsoft.web_md.core.common.mybatis.dto.M011trim;
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
 * 買掛金残高一覧表のControllerクラス
 * 
 */
@Security.Authenticated(CCSecurityAuthenticator.class)
public class Kkjp1040Ctrl extends Controller {
    /**SQLマップ*/
    @Inject
    private MyBatisSqlMapDao dao;
    /**エラー応答*/
    @Inject
    private ErrorResponse errRes;
    /**エラー応答*/
    @Inject
    private CCSystemContext context;
    /**KKの共通*/
    @Inject
    private CCKKCommon kkCommon;
    /**プログラムＩＤ*/
    private static final String PROGRAM_ID = "KKPR1040";
    /**バージョン**/
    private static final String PROGRAM_VERSION = "V1.00";

    /**
     * 引数とリクエスト(パラメータ)を条件にDBからデータ(複数件)を検索し返却する。*
     * 
     * @param taisyoYSt 年開始
     * @param taisyoMSt 月開始
     * @return クライアントへ返却する結果
     */
    public Result query(String taisyoYSt, String taisyoMSt) {
        Form<Kkjp1040CondForm> emptyForm = new Form<Kkjp1040CondForm>(Kkjp1040CondForm.class);
        Form<Kkjp1040CondForm> reqForm = emptyForm.bindFromRequest();
        if (reqForm.hasErrors()) {
            errRes.addErrorInfo(CCMessageConst.MSG_KEY_VALIDATION_ERROR);
            errRes.addErrorInfo(reqForm.errors());
            return badRequest(Json.toJson(errRes));
        } else {
            Kkjp1040CondForm req = reqForm.get();
            boolean bRetFlg = true;
            req.setTaisyoYSt(taisyoYSt);
            req.setTaisyoMSt(taisyoMSt);

            if (CCStringUtil.isEmpty(req.getTaisyoYEd())) {
                req.setTaisyoYEd(req.getTaisyoYSt());
            }
            if (CCStringUtil.isEmpty(req.getTaisyoMEd())) {
                req.setTaisyoMEd(req.getTaisyoMSt());
            }

            // ゼロサプレス
            req.setTaisyoMEd(CCStringUtil.suppZero(req.getTaisyoMEd(), 2));

            if (!CCStringUtil.isEmpty(req.getShrKin())) {
                req.setShrKin(CCStringUtil.suppZero(req.getShrKin(), 4));
            }
            if (!CCStringUtil.isEmpty(req.getMainToriCdSt())) {
                req.setMainToriCdSt(CCStringUtil.suppZero(req.getMainToriCdSt(), 6));
            }
            if (!CCStringUtil.isEmpty(req.getMainToriCdEd())) {
                req.setMainToriCdEd(CCStringUtil.suppZero(req.getMainToriCdEd(), 6));
            }

            req.setKaisya(CCKKBeans.CON_KAISHA_CODE);
            req.setTaisyoSt(taisyoYSt + taisyoMSt);
            req.setTaisyoEd(req.getTaisyoYEd() + req.getTaisyoMEd());

            // 対象月Fromの日付妥当性チェック
            if (!CCDateUtil.isDate(taisyoYSt + taisyoMSt + "01")) {
                // エラーメッセージを表示し、フォーカスをセットする。
                errRes.addErrorInfo("taisyoYSt", CCMessageConst.MSG_KEY_DATE_ERROR);
                bRetFlg = false;
            }

            // 対象月Toの日付妥当性チェック
            if (!CCDateUtil.isDate(req.getTaisyoYEd() + req.getTaisyoMEd() + "01")) {
                // エラーメッセージを表示し、フォーカスをセットする。
                errRes.addErrorInfo("taisyoYEd", CCMessageConst.MSG_KEY_DATE_ERROR);
                bRetFlg = false;
            }

            // 対象月のFrom > To はエラー
            if (!CCStringUtil.isEmpty(req.getTaisyoSt()) && !CCStringUtil.isEmpty(req.getTaisyoEd())) {
                if (Integer.parseInt(req.getTaisyoSt()) > Integer.parseInt(req.getTaisyoEd())) {
                    errRes.addErrorInfo("taisyoYSt", CCMessageConst.MSG_KEY_DATE_ERROR);
                    bRetFlg = false;
                }
            }
            // 支払区分
            if (!CCStringUtil.isEmpty(req.getShrKin())) {
                if (!CCNumberUtil.isNumeric(req.getShrKin())) {
                    // 数字以外はエラー
                    errRes.addErrorInfo("taisyoYSt", CCMessageConst.MSG_KEY_NOT_A_NUMBER);
                    bRetFlg = false;
                }
            }

            // 取引先コードのFrom未入力、Toのみ入力はエラー
            if (CCStringUtil.isEmpty(req.getMainToriCdSt()) && !CCStringUtil.isEmpty(req.getMainToriCdEd())) {
                errRes.addErrorInfo("mainToriCdSt", CCMessageConst.MSG_KEY_NOT_KIP_ITEM);
                bRetFlg = false;
            }

            // 取引先コードのFrom > To はエラー
            if (!CCStringUtil.isEmpty(req.getMainToriCdSt()) && !CCStringUtil.isEmpty(req.getMainToriCdEd())) {
                if (Integer.parseInt(req.getMainToriCdSt()) > Integer.parseInt(req.getMainToriCdEd())) {
                    errRes.addErrorInfo("mainToriCdSt", CCMessageConst.MSG_KEY_RANGE_ERROR);
                    bRetFlg = false;
                }
            }

            // 入力データチェック
            if (!bRetFlg) {
                ErrorInfo error = errRes.getErrorInfo(CCMessageConst.MSG_KEY_VALIDATION_ERROR);
                errRes.getErrors().add(0, error);
                return badRequest(Json.toJson(errRes));
            }

            // データ取得
            Map<String, String> params = new HashMap<String, String>();
            if (req.getMainToriCdSt() != null) {
                params.put("mainToriCdSt", req.getMainToriCdSt());
            } else {
                params.put("mainToriCdSt", "");
            }
            params.put("conTorihikisakiCode", CCKKBeans.CON_TORIHIKISAKI_CODE);
            if (req.getMainToriCdEd() != null) {
                params.put("mainToriCdEd", req.getMainToriCdEd());
            } else {
                params.put("mainToriCdEd", "");
            }
            params.put("taisyoDateSt", req.getTaisyoSt());
            params.put("taisyoDateEd", req.getTaisyoEd());
            params.put("sBaseDate", CCDateUtil.getSysDateFormat("yyyyMMdd")); // システム日付

            List<K002kruiM011trimResult> k002kruiM011trimResults =
                    dao.selectMany("selectK002kruiM011trim", params, K002kruiM011trimResult.class);
            if (k002kruiM011trimResults.size() == 0) {
                errRes.addErrorInfo(CCMessageConst.MSG_KEY_NO_FOUND_DATA);
                return notFound(Json.toJson(errRes));
            } else {
                List<K002kruiM011trimReport> k002kruiM011trimReports = new ArrayList<K002kruiM011trimReport>();
                k002kruiM011trimReports = cnvKaikkData(k002kruiM011trimResults, req);

                if (k002kruiM011trimReports.size() == 0) {
                    errRes.addErrorInfo(CCMessageConst.MSG_KEY_NO_FOUND_DATA);
                    return notFound(Json.toJson(errRes));
                } else {
                    // ＣＳＶ作成（データ取得）
                    CCReportUtil cru = new CCReportUtil("KKSV1040", context);
                    CsvManager csvManager = CsvManagerFactory.newCsvManager();
                    List<Kkpr1040Report> csvBean = new ArrayList<Kkpr1040Report>();

                    // 共通ヘッダ部
                    // 会社名をマスタより取得する基準日付を設定する
                    String sDate = "";
                    if (!CCStringUtil.isEmpty(req.getTaisyoSt()) && !CCStringUtil.isEmpty(req.getTaisyoEd())
                            && !req.getTaisyoSt().equals(req.getTaisyoEd())) {
                        // 累計（From～Toが入力されていてFrom=Toではない）の場合、Toの月末日を指定
                        sDate = CCDateUtil.doCalcDate(req.getTaisyoEd() + "01", "DAY_OF_MONTH", 1);
                    } else {
                        // 月期（Fromのみ入力かFrom=To）の場合、Fromの月末日を指定
                        sDate = CCDateUtil.doCalcDate(req.getTaisyoSt() + "01", "DAY_OF_MONTH", 1);
                    }

                    for (K002kruiM011trimReport k002kruiM011trimReport : k002kruiM011trimReports) {
                        // CSVデータを作成する
                        Kkpr1040Report kkpr1040Report = new Kkpr1040Report();

                        setCSVHeaderData(kkpr1040Report, PROGRAM_ID, PROGRAM_VERSION, sDate);

                        // 固有ヘッダ部
                        setCSVHeaderDataLocal(kkpr1040Report, req);

                        setCsvData(kkpr1040Report, k002kruiM011trimReport, req);

                        csvBean.add(kkpr1040Report);

                    }
                    // ＣＳＶ作成（データ出力）
                    try {
                        csvManager.save(csvBean, Kkpr1040Report.class).to(new File(cru.getCsvFilePath()),
                                CCReportUtil.CSV_OUTPUT_ENCODING);
                    } catch (Exception e) {
                        throw new ChaseException(e);
                    }

                    cru.makePDF();
                    List<Kkjp1040ResultForm> reportList = new ArrayList<Kkjp1040ResultForm>();
                    Kkjp1040ResultForm resultForm = new Kkjp1040ResultForm();
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

    /**
     * 買掛累計データ編集処理
     * <p>
     * 機能概要：<br>
     * 買掛累計データを編集します。
     * 
     * @param queryData List<K002kruiM011trimResult>
     * @param req Kkjp1040CondForm
     * @return List<K002kruiM011trimReport> lK002kruiM011trimReportBuf
     */
    private List<K002kruiM011trimReport> cnvKaikkData(List<K002kruiM011trimResult> queryData, Kkjp1040CondForm req) {
        long iCalcBuf = 0;
        int iExecuteCnt = 0;
        boolean bFlag = false;
        // ワーク用配列の要素数を確保する
        List<K002kruiM011trimReport> k002kruiM011trimReports = new ArrayList<K002kruiM011trimReport>();

        // 指定対象月From、対象月Toを取得する
        String taisyoDateSt = req.getTaisyoSt();
        String taisyoDateEd = req.getTaisyoEd();

        K002kruiM011trimReport reportRow = new K002kruiM011trimReport();
        for (iExecuteCnt = 0; iExecuteCnt < queryData.size(); iExecuteCnt++) {

            String shrKin = req.getShrKin();

            // 指定取引先の支払区分を取得する
            String date = queryData.get(iExecuteCnt).getGetujYm();
            if (!CCStringUtil.isEmpty(date)) {
                date = CCDateUtil.doCalcDate(date + "01", "DAY_OF_MONTH", 1);

            }
            String toriSimeKbn = "";
            String toriShrKbn = "";
            String seisanKbn = "";
            String syogoKbn = "";

            M011trim dataBuf = new M011trim();
            dataBuf = kkCommon.getComM011trim(date, queryData.get(iExecuteCnt).getMainToriCd());
            if (dataBuf != null) {
                toriSimeKbn = dataBuf.getSimeKbn();
                toriShrKbn = dataBuf.getPayKbn();
                seisanKbn = dataBuf.getKaiSeisanKbn();
                syogoKbn = dataBuf.getChkKbn();
            }
            // 支払方法
            reportRow.setSirHoho(toriSimeKbn + toriShrKbn);
            // 支払区分が入力されている場合、支払区分がマッチングするレコードのみ抽出する
            if (CCStringUtil.isEmpty(shrKin) || shrKin.equals(reportRow.getSirHoho())) {
                // 支払（買掛精算区分）
                reportRow.setSeisanKbn(seisanKbn);

                // 照合区分
                reportRow.setSyogoKbn(syogoKbn);

                // 月次月
                reportRow.setGetuj(reportRow.getGetuj());

                // 取引先コード
                reportRow.setSirCd(queryData.get(iExecuteCnt).getMainToriCd());

                if (reportRow.getZenZan() == null) {
                    // 対象月Fromと同じ月次月のレコードの前回繰越高を設定する
                    if (queryData.get(iExecuteCnt).getGetujYm().equals(taisyoDateSt)) {
                        reportRow.setZenZan(queryData.get(iExecuteCnt).getZenKuriKin());
                    }
                }

                if (reportRow.getSirKingk() == null) {
                    iCalcBuf = Long.parseLong(queryData.get(iExecuteCnt).getSirKin());
                } else {
                    iCalcBuf =
                            Long.parseLong(reportRow.getSirKingk())
                                    + Long.parseLong(queryData.get(iExecuteCnt).getSirKin());
                }

                reportRow.setSirKingk(String.valueOf(iCalcBuf));
                // 返品金額
                if (reportRow.getHpnKingk() == null) {
                    iCalcBuf = Long.parseLong(queryData.get(iExecuteCnt).getHenKin());
                } else {
                    iCalcBuf =
                            Long.parseLong(reportRow.getHpnKingk())
                                    + Long.parseLong(queryData.get(iExecuteCnt).getHenKin());
                }

                reportRow.setHpnKingk(String.valueOf(iCalcBuf));

                // 値引金額
                if (reportRow.getNbkKingk() == null) {
                    iCalcBuf = Long.parseLong(queryData.get(iExecuteCnt).getNebikiKin());
                } else {
                    iCalcBuf =
                            Long.parseLong(reportRow.getNbkKingk())
                                    + Long.parseLong(queryData.get(iExecuteCnt).getNebikiKin());
                }

                reportRow.setNbkKingk(String.valueOf(iCalcBuf));

                // 純仕入
                if (reportRow.getJunSir() == null) {
                    iCalcBuf = Long.parseLong(queryData.get(iExecuteCnt).getJunSirKin());
                } else {
                    iCalcBuf =
                            Long.parseLong(reportRow.getJunSir())
                                    + Long.parseLong(queryData.get(iExecuteCnt).getJunSirKin());
                }

                reportRow.setJunSir(String.valueOf(iCalcBuf));

                // 支払金額
                if (reportRow.getShrKingk() == null) {
                    iCalcBuf = Long.parseLong(queryData.get(iExecuteCnt).getShrKin());
                } else {
                    iCalcBuf =
                            Long.parseLong(reportRow.getShrKingk())
                                    + Long.parseLong(queryData.get(iExecuteCnt).getShrKin());
                }

                reportRow.setShrKingk(String.valueOf(iCalcBuf));

                // 繰越残高
                if (reportRow.getZan() == null) {
                    // 対象月Toと同じ月次月のレコードの繰越残高を設定する
                    if (queryData.get(iExecuteCnt).getGetujYm().equals(taisyoDateEd)) {
                        reportRow.setZan(queryData.get(iExecuteCnt).getKuriKin());
                    }
                }

                if (!CCStringUtil.isEmpty(taisyoDateSt) && !CCStringUtil.isEmpty(taisyoDateEd)
                        && !taisyoDateSt.equals(taisyoDateEd) && iExecuteCnt < queryData.size() - 1) {
                    // 累計表示で最終レコード以外の場合
                    if (!reportRow.getSirCd().equals(queryData.get(iExecuteCnt + 1).getMainToriCd())) {

                        bFlag = true;
                    }
                } else {
                    bFlag = true;
                }

                // 配列の要素数を確保する
                if (bFlag) {
                    k002kruiM011trimReports.add(reportRow);
                    reportRow = new K002kruiM011trimReport();
                }
            }
        }
        return k002kruiM011trimReports;
    }

    /**
     * 帳票出力ヘッダ部の設定.
     * <p>
     * 機能概要：<br>
     * 帳票出力のヘッダ部（共通部分）を設定する。<br>
     * 
     * @param kkpr1040Report CSVデータ
     * @param sProgId プログラムID
     * @param sProgVer プログラムVersion　
     * @param sDate 基準日付
     */
    public void setCSVHeaderData(Kkpr1040Report kkpr1040Report, String sProgId, String sProgVer, String sDate) {
        // プログラムＩＤ
        kkpr1040Report.h00_progid = '"' + sProgId + '"';
        // バージョン
        kkpr1040Report.h00_version = '"' + sProgVer + '"';
        // システム日付
        kkpr1040Report.h00_sdate =
                '"' + CCDateUtil.getSysYear() + CCDateUtil.getSysMonth() + CCDateUtil.getSysDate() + '"';

        // システム時間
        kkpr1040Report.h00_stime = '"' + CCDateUtil.getSysHour() + CCDateUtil.getSysMinute() + '"';

        // ページ番号
        kkpr1040Report.h00_page = '"' + "" + '"';

        // 担当者コード
        kkpr1040Report.h01_tanto_code = '"' + context.getTantoCode() + '"';

        // 会社コード
        kkpr1040Report.h01_kaisya_code = '"' + kkCommon.getKaisyaCode() + '"';
        // 会社名(共通部品より取得)
        kkpr1040Report.h01_kaisya_name = '"' + kkCommon.getComKaisyaName(sDate, kkCommon.getKaisyaCode()) + '"';

        // 予備01
        kkpr1040Report.h01_buffer_01 = '"' + "" + '"';
        // 予備02
        kkpr1040Report.h01_buffer_02 = '"' + "" + '"';

    }

    /**
     * 帳票出力ヘッダ部の設定（固有ヘッダ部）.
     * <p>
     * 機能概要：<br>
     * 帳票出力のヘッダ部（固有部分）を設定する。<br>
     * 
     * @param kkpr1040Report Kkpr1040Report
     * @param req Kkjp1040CondForm
     */
    private void setCSVHeaderDataLocal(Kkpr1040Report kkpr1040Report, Kkjp1040CondForm req) {
        String taisyoYSt = req.getTaisyoYSt();
        String taisyoMSt = req.getTaisyoMSt();
        String taisyoYEd = req.getTaisyoYEd();
        String taisyoMEd = req.getTaisyoMEd();
        String taisyoDateSt = req.getTaisyoSt();
        String taisyoDateEd = req.getTaisyoEd();

        // タイトル
        if (!CCStringUtil.isEmpty(taisyoDateSt) && !CCStringUtil.isEmpty(taisyoDateEd)
                && !taisyoDateSt.equals(taisyoDateEd)) {
            // From～Toが入力されていてFrom=Toではない
            kkpr1040Report.h01_title = "累計";
        } else {
            kkpr1040Report.h01_title = "月期";
        }

        // 対象月From
        String sKikanFrom = taisyoYSt + taisyoMSt + "01";
        String sKikanTo = taisyoYEd + taisyoMEd + "01";

        kkpr1040Report.h02_kikan_from = '"' + sKikanFrom + '"';
        // 対象月To
        if (!CCStringUtil.isEmpty(taisyoDateSt)) {
            kkpr1040Report.h02_kikan_to = '"' + CCDateUtil.doCalcDate(sKikanTo, "DAY_OF_MONTH", 1) + '"';
        } else {
            // From指定のみの場合、ToにはFrom月の月末日を設定する
            kkpr1040Report.h02_kikan_to = '"' + CCDateUtil.doCalcDate(sKikanFrom, "DAY_OF_MONTH", 1) + '"';
        }
    }

    /**
     * CSVデータ作成処理
     * <p>
     * 機能概要：<br>
     * CSVデータを作成します。
     * <p>
     * 
     * @param kkpr1040Report Kkpr1040Report
     * @param k002kruiM011trimReport K002kruiM011trimReport
     * @param req Kkjp1040CondForm
     */
    private void setCsvData(Kkpr1040Report kkpr1040Report, K002kruiM011trimReport k002kruiM011trimReport,
            Kkjp1040CondForm req) {

        // 名称M取得用基準日付
        String sDate = "";
        if (!CCStringUtil.isEmpty(req.getTaisyoSt()) && !CCStringUtil.isEmpty(req.getTaisyoEd())
                && !req.getTaisyoSt().equals(req.getTaisyoEd())) {
            // 累計（From～Toが入力されていてFrom=Toではない）の場合、Toの月末日を指定
            sDate = CCDateUtil.doCalcDate(req.getTaisyoEd() + "01", "DAY_OF_MONTH", 1);
        } else {
            // 月期（Fromのみ入力かFrom=To）の場合、Fromの月末日を指定
            sDate = CCDateUtil.doCalcDate(req.getTaisyoSt() + "01", "DAY_OF_MONTH", 1);
        }

        // 支払（買掛精算区分）
        kkpr1040Report.m01_siharai = '"' + kkCommon.getSeisanKbnNm(sDate, k002kruiM011trimReport.getSeisanKbn()) + '"';

        // 照合区分
        String sSyogoKbn = k002kruiM011trimReport.getSyogoKbn();

        if (String.valueOf(CCKKConst.SYOGO_KBN.KBN_JIFURI).equals(sSyogoKbn)) {
            // 自振の場合、「*」を表示する
            sSyogoKbn = "*";
        } else {
            sSyogoKbn = "";
        }
        kkpr1040Report.m01_syogo_kbn = '"' + sSyogoKbn + '"';

        // 支払方法
        kkpr1040Report.m01_sir_hoho = '"' + kkCommon.getShiharaiPt(sDate, k002kruiM011trimReport.getSirHoho()) + '"';
        // 取引先コード
        kkpr1040Report.m01_sir_cd = '"' + k002kruiM011trimReport.getSirCd() + '"';
        // 取引先名
        String sDateTori = k002kruiM011trimReport.getGetuj();
        if (!CCStringUtil.isEmpty(sDateTori)) {
            // 月次月の末日を設定する
            sDateTori = CCDateUtil.doCalcDate(sDateTori + "01", "DAY_OF_MONTH", 1);
            kkpr1040Report.m01_sir_name =
                    '"' + kkCommon.getComToriName(sDateTori, k002kruiM011trimReport.getSirCd()) + '"';
        } else {
            kkpr1040Report.m01_sir_name = '"' + kkCommon.getComToriName(k002kruiM011trimReport.getSirCd()) + '"';
        }

        // 前回繰越高
        kkpr1040Report.m01_zen_zan = '"' + k002kruiM011trimReport.getZenZan() + '"';

        // 仕入金額
        kkpr1040Report.m01_sir_kingk = '"' + k002kruiM011trimReport.getSirKingk() + '"';

        // 返品金額
        kkpr1040Report.m01_hpn_kingk = '"' + k002kruiM011trimReport.getHpnKingk() + '"';

        // 値引金額
        kkpr1040Report.m01_nbk_kingk = '"' + k002kruiM011trimReport.getNbkKingk() + '"';

        // 純仕入
        kkpr1040Report.m01_jun_sir = '"' + k002kruiM011trimReport.getJunSir() + '"';

        // 支払金額
        kkpr1040Report.m01_shr_kingk = '"' + k002kruiM011trimReport.getShrKingk() + '"';

        // 繰越残高
        kkpr1040Report.m01_zan = k002kruiM011trimReport.getZan();

    }
}
