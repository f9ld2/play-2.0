// /////////////////////////////////////////////////////////////////////
// Copyright(C) 2015 NEC Solution Innovators, Ltd. All Rights Reserved.
// /////////////////////////////////////////////////////////////////////

/*
 * ====================================================================
 * 
 * 機能名称 : ＴＡ伝票出力
 * 
 * 改版履歴
 * 
 * Rev. 改版年月日 改版者名 内容
 * 
 * 1.0 2014-05-05 TUCTVZ 新規作成
 * 
 * ===================================================================
 */
package jp.co.necsoft.web_md.core.app.controllers.ha;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

import jp.co.necsoft.web_md.common.ChaseException;
import jp.co.necsoft.web_md.common.biz.IPluConverter;
import jp.co.necsoft.web_md.core.app.dto.ha.Hajp1170Dtocsv;
import jp.co.necsoft.web_md.core.app.file.report.Hapr1170Report;
import jp.co.necsoft.web_md.core.app.forms.ha.Hajp1170CondForm;
import jp.co.necsoft.web_md.core.app.forms.ha.Hajp1170ResultForm;
import jp.co.necsoft.web_md.core.common.CCMessageConst;
import jp.co.necsoft.web_md.core.common.CCSecurityAuthenticator;
import jp.co.necsoft.web_md.core.common.CCSystemContext;
import jp.co.necsoft.web_md.core.common.forms.ErrorInfo;
import jp.co.necsoft.web_md.core.common.forms.ErrorResponse;
import jp.co.necsoft.web_md.core.common.mybatis.dao.M000kaimMapper;
import jp.co.necsoft.web_md.core.common.mybatis.dao.M001jgymMapper;
import jp.co.necsoft.web_md.core.common.mybatis.dao.M006tenmMapper;
import jp.co.necsoft.web_md.core.common.mybatis.dao.M007kijmMapper;
import jp.co.necsoft.web_md.core.common.mybatis.dao.M011trimMapper;
import jp.co.necsoft.web_md.core.common.mybatis.dto.M000kaim;
import jp.co.necsoft.web_md.core.common.mybatis.dto.M000kaimExample;
import jp.co.necsoft.web_md.core.common.mybatis.dto.M001jgym;
import jp.co.necsoft.web_md.core.common.mybatis.dto.M001jgymExample;
import jp.co.necsoft.web_md.core.common.mybatis.dto.M006tenm;
import jp.co.necsoft.web_md.core.common.mybatis.dto.M006tenmExample;
import jp.co.necsoft.web_md.core.common.mybatis.dto.M007kijm;
import jp.co.necsoft.web_md.core.common.mybatis.dto.M007kijmExample;
import jp.co.necsoft.web_md.core.common.mybatis.dto.M011trim;
import jp.co.necsoft.web_md.core.common.mybatis.dto.M011trimExample;
import jp.co.necsoft.web_md.core.common.utils.CCFileEdit;
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
 * ＴＡ伝票出力 のControllerクラス
 * 
 */
@Security.Authenticated(CCSecurityAuthenticator.class)
public class Hajp1170Ctrl extends Controller {

    /** ACT_KBN_DELETE */
    private static final String ACT_KBN_DELETE = "9";
    /** エラー応答 */
    @Inject
    private ErrorResponse errRes;
    /** エラー応答 */
    @Inject
    private CCSystemContext context;
    /** プログラムＩＤ */
    private static final String PROGRAM_ID = "HAPR1170";
    /** バージョン **/
    private static final String PROGRAM_VERSION = "1.00";
    /** M000kaimのマッパー */
    @Inject
    private M000kaimMapper m000kaimMapper;
    /** M001jgymのマッパー */
    @Inject
    private M001jgymMapper m001jgymMapper;
    /** M006tenmのマッパー */
    @Inject
    private M006tenmMapper m006tenmMapper;
    /** M007kijmのマッパー */
    @Inject
    private M007kijmMapper m007kijmMapper;
    /** M011trimのマッパー */
    @Inject
    private M011trimMapper m011trimMapper;
    /** IPlu Converter */
    @Inject
    private IPluConverter plucnv;

    /**
     * 引数とリクエスト(パラメータ)を条件にDBからデータ(複数件)を検索し返却する。*
     * @param kaisyaCd  会社コード
     * @return クライアントへ返却する結果
     * @throws Exception  error
     */
    public Result query(String kaisyaCd) throws Exception {
        @SuppressWarnings({"unchecked", "rawtypes" })
        Form<Hajp1170CondForm> emptyForm = new Form(Hajp1170CondForm.class);
        Form<Hajp1170CondForm> reqForm = emptyForm.bindFromRequest();
        if (reqForm.hasErrors()) {
            errRes.addErrorInfo(CCMessageConst.MSG_KEY_VALIDATION_ERROR);
            errRes.addErrorInfo(reqForm.errors());
            return badRequest(Json.toJson(errRes));
        } else {
            Hajp1170CondForm req = reqForm.get();
            req.setKaisyaCd(kaisyaCd);
            if (!docheckData(req)) {
                return badRequest(Json.toJson(errRes));
            }
            String sKaisyaCd = req.getKaisyaCd();
            String sTriCdFr = req.getTriCdSt();
            String sTriCdTo = req.getTriCdEd();
            String sJigyobuCd = req.getJigyobuCd();
            String sTenCdFr = req.getTenCdSt();
            String sTenCdTo = req.getTenCdEd();

            // ＣＳＶ作成（データ取得）
            CCReportUtil cru = new CCReportUtil("HASV1170", context);
            CsvManager csvManager = CsvManagerFactory.newCsvManager();
            List<Hapr1170Report> csvBean = new ArrayList<Hapr1170Report>();

            String motocsvfilepath = context.getContextProperty("cc.app.cm.csv_path") + "H710TAPR";
            String tmpFile = cru.getDBServTempPath("H710TAPR");
            if (!CCFileEdit.isExist(tmpFile)) {
                errRes.addErrorInfo(CCMessageConst.MSG_KEY_CSV_FILE_NOT_EXIST);
                return badRequest(Json.toJson(errRes));
            } else {
                CCFileEdit.copyFile(cru.getDBServTempPath("H710TAPR"), motocsvfilepath);
            }
            File file = new File(motocsvfilepath);
            List<String> recList = new ArrayList<String>();
            int iRecCnt = 0;
            int iRow = -1;

            if (!file.exists()) {
                return badRequest(Json.toJson(errRes));
            }
            if (!file.isFile()) {
                return badRequest(Json.toJson(errRes));
            }
            if (!file.canRead()) {
                return badRequest(Json.toJson(errRes));
            }

            recList = CCFileEdit.readLines(file);
            
            // 入力件数を取得
            iRecCnt = recList.size();
            if (iRecCnt < 1) {
                // 対象データがありません。
                errRes.addErrorInfo(CCMessageConst.MSG_KEY_NO_FOUND_DATA);
                return notFound(Json.toJson(errRes));
            }
            String[][] recordFile = new String[iRecCnt][237];
            for (int i = 0; i < iRecCnt; i++) {
                iRow += 1;
                int iSeparator = 0;
                int iIndex = 0;
                int iCol = -1;
                while (iSeparator != -1) {
                    iCol += 1;
                    iSeparator = ((String) recList.get(i)).indexOf(",", iIndex);
                    if (iSeparator != -1) {
                        recordFile[iRow][iCol] = ((String) recList.get(i)).substring(iIndex, iSeparator);
                    } else {
                        recordFile[iRow][iCol] = ((String) recList.get(i)).substring(iIndex);
                    }
                    iIndex = iSeparator;
                    iIndex += 1;
                }
                // リザルトセットの中身にnullをセット
                if (i % 10000 == 0) {
                    System.gc();
                }
            }

            List<Hajp1170Dtocsv> lstCsvTmp = new ArrayList<Hajp1170Dtocsv>();
            for (int i = 0; i < iRecCnt; i++) {
                // ＴＡ伝票データヘッダエリア
                // 伝票番号
                String rDpyNo = recordFile[i][0];
                // 会社コード
                String rKaisyaCd = recordFile[i][1];
                // 事業部コード
                String rJigyoubuCd = recordFile[i][2];
                // 店舗コード
                String rTenCd = recordFile[i][3];
                // 部門コード
                String rBmnCd = recordFile[i][4];
                // 伝票区分
                String rDpyKbn = recordFile[i][5];
                // 取引先コード
                String rTriCd = recordFile[i][6];
                // 取引先名(ｶﾅ)
                String rTriNmA = recordFile[i][7];
                // 発注日
                String rHatYmd = recordFile[i][8];
                // 納品予定日
                String rNhnYoteiYmd = recordFile[i][9];
                // 便
                String rBin = recordFile[i][10];
                // Ｆエール扱い区分
                String rAtukaiKbn = recordFile[i][11];
                // 伝票番号
                String rTaxKbn = recordFile[i][12];
                // 店舗コード
                if (!rKaisyaCd.equals(sKaisyaCd)) {
                    continue;
                }
                // 事業部コード
                if (!CCStringUtil.isEmpty(sJigyobuCd)) {
                    if (!rJigyoubuCd.equals(sJigyobuCd)) {
                        continue;
                    }
                    // 店舗コード
                    if (!CCStringUtil.isEmpty(sTenCdFr) && !CCStringUtil.isEmpty(sTenCdTo)) {
                        if (!(Integer.parseInt(rTenCd) >= Integer.parseInt(sTenCdFr))
                                || !(Integer.parseInt(rTenCd) <= Integer.parseInt(sTenCdTo))) {
                            continue;
                        }
                    } else if (!CCStringUtil.isEmpty(sTenCdFr) && CCStringUtil.isEmpty(sTenCdTo)) {
                        if (!(Integer.parseInt(rTenCd) >= Integer.parseInt(sTenCdFr))) {
                            continue;
                        }
                    } else if (CCStringUtil.isEmpty(sTenCdFr) && !CCStringUtil.isEmpty(sTenCdTo)) {
                        if (!(Integer.parseInt(rTenCd) <= Integer.parseInt(sTenCdTo))) {
                            continue;
                        }
                    }
                }
                // 取引先コードの7桁目を除いて比較
                String swkTriCd = rTriCd.substring(0, 6) + rTriCd.substring(7, 9);
                String swkTriFr = "";
                if (!CCStringUtil.isEmpty(sTriCdFr)) {
                    swkTriFr = sTriCdFr.substring(0, 6) + sTriCdFr.substring(7, 9);
                }
                String sWkTriTo = "";
                if (!CCStringUtil.isEmpty(sTriCdTo)) {
                    sWkTriTo = sTriCdTo.substring(0, 6) + sTriCdTo.substring(7, 9);
                }
                // 取引先コード
                if (!CCStringUtil.isEmpty(sTriCdFr) && !CCStringUtil.isEmpty(sTriCdTo)) {
                    if (!(Integer.parseInt(swkTriCd) >= Integer.parseInt(swkTriFr))
                            || !(Integer.parseInt(swkTriCd) <= Integer.parseInt(sWkTriTo))) {
                        continue;
                    }
                } else if (!CCStringUtil.isEmpty(sTriCdFr) && CCStringUtil.isEmpty(sTriCdTo)) {
                    if (!(Integer.parseInt(swkTriCd) >= Integer.parseInt(swkTriFr))) {
                        continue;
                    }
                } else if (CCStringUtil.isEmpty(sTriCdFr) && !CCStringUtil.isEmpty(sTriCdTo)) {
                    if (!(Integer.parseInt(swkTriCd) <= Integer.parseInt(sWkTriTo))) {
                        continue;
                    }
                }
                // ＴＡ伝票データから検索取得用エリア
                String sJigyoubuNmA = "";
                String sTenNmA = "";
                int iSoeji = 14;
                sJigyoubuNmA = execProjectCd(rKaisyaCd, rJigyoubuCd, rHatYmd);
                if (!"?".equals(sJigyoubuNmA)) {
                    sJigyoubuNmA = CCStringUtil.puddRight(sJigyoubuNmA, 30, ' ');
                } else {
                    sJigyoubuNmA = "                              ";
                }
                // 店舗名(ｶﾅ)取得
                sTenNmA = executeTenCd(rKaisyaCd + rJigyoubuCd + rTenCd, rHatYmd);
                if ("?".equals(sTenNmA)) {
                    sTenNmA = "";
                }
                // ＴＡ伝票に出力前エリア
                String tateD2 = "";
                String tateSHANM = "";
                String tateTRINM = "";
                String tateTENNM = "";
                String tateBMNCD = "";
                String tateTRICD = "";
                String tateHATDD = "";
                String tateNHNDD = "";
                String tateBIN = "";
                // Ｄ－２欄編集
                if ("5".equals(rTaxKbn) || "6".equals(rTaxKbn) || "7".equals(rTaxKbn) || "8".equals(rTaxKbn)) {
                    tateD2 = "ｿﾄ";
                } else if ("1".equals(rTaxKbn) || "2".equals(rTaxKbn) || "3".equals(rTaxKbn) || "4".equals(rTaxKbn)) {
                    tateD2 = "ｳﾁ";
                } else if ("9".equals(rTaxKbn)) {
                    tateD2 = "ﾋｶｾﾞｲ";
                } else {
                    tateD2 = "";
                }
                // 社名編集
                if ("1".equals(rAtukaiKbn)) {
                    tateSHANM = "Fｴｰﾙ(" + CCStringUtil.trimBoth(sJigyoubuNmA.substring(0, 14)) + ")";
                } else {
                    tateSHANM = sJigyoubuNmA.substring(0, 20).trim();
                }
                // 取引先名編集
                if ("1".equals(rAtukaiKbn)) {
                    if (rTriNmA.length() >= 14) {
                        rTriNmA = rTriNmA.substring(0, 14);
                    }
                    tateTRINM = "Fｴｰﾙ(" + CCStringUtil.trimBoth(rTriNmA) + ")";
                } else {
                    tateTRINM = CCStringUtil.trimBoth(rTriNmA);
                }
                // 店名
                tateTENNM = CCStringUtil.trimBoth(sTenNmA);
                // 部門コード
                tateBMNCD = rBmnCd.substring(2, 5);
                // 全桁出力するよう修正
                tateTRICD = rTriCd;
                // 発注日
                tateHATDD = rHatYmd.substring(2, 8);
                // 納品日
                tateNHNDD = rNhnYoteiYmd.substring(2, 8);
                // １桁目から取得するよう修正
                tateBIN = rBin.substring(0, 1);
                // 条件を満たすので、データに追加
                for (int iKai = 0; iKai < 9; iKai++) {
                    // ＴＡ明細エリア
                    // 明細№
                    String rMeisaiNo = recordFile[i][iSoeji];
                    iSoeji += 2;
                    // 検収原単価
                    String rKenGenK = recordFile[i][iSoeji];
                    iSoeji += 1;
                    // 商品コード
                    String rShnCd = recordFile[i][iSoeji];
                    iSoeji += 1;
                    // 発注入数
                    String rHatIrisu = recordFile[i][iSoeji];
                    iSoeji += 1;
                    // 発注ケース数
                    String rHatCaseSu = recordFile[i][iSoeji];
                    iSoeji += 1;
                    // 発注単位
                    String rHatTani = recordFile[i][iSoeji];
                    iSoeji += 1;
                    // 発注原単価
                    String rHatGenk = recordFile[i][iSoeji];
                    iSoeji += 1;
                    // 発注売単価
                    String rHatBaik = recordFile[i][iSoeji];
                    iSoeji += 1;
                    // ＴＡ伝票データから検索取得用エリア
                    String sSHNCD = "";
                    String sShnNmA = "";
                    String sKikakuNmA = "";
                    double dShuSeiGenK = 0;
                    double dIrisu2 = 0;
                    double dCase2 = 0;
                    double dHatSu = 0;
                    double dGenK = 0;
                    long lGenkKin = 0;
                    double dBaiK = 0;
                    long lBaikKin = 0;
                    // ＴＡ伝票に出力前エリア
                    String tateShnNm1 = "";
                    String tateSHNNM2 = "";
                    String tateSHNNM3 = "";
                    String tateSHUSEIGENK = "";
                    String tateSHNCD = "";
                    String tateIRISU1 = "";
                    String tateIRISU2 = "";
                    String tateCASE1 = "";
                    String tateCASE2 = "";
                    String tateHATSU = "";
                    String tateGENK = "";
                    String tateGENKKIN = "";
                    String tateBAIK = "";
                    String tateBAIKKIN = "";
                    String tateKENGENK = "";
                    if (!CCStringUtil.isEmpty(rShnCd)) {
                        // 商品コード
                        sSHNCD = plucnv.toDbCode(CCStringUtil.suppZero(rShnCd, 13).trim());
                        // 商品名・規格名を取得する
                        HashMap<String, String> resultJanCd = executeJanCd(sSHNCD, rHatYmd);
                        sShnNmA = resultJanCd.get("sShnNmA");
                        sKikakuNmA = resultJanCd.get("sKikakuNmA");
                        String flag = resultJanCd.get("fLag");
                        if (flag.equals("1")) {
                            sShnNmA = CCStringUtil.puddRight(sShnNmA, 30, ' ');
                            sKikakuNmA = CCStringUtil.puddRight(sKikakuNmA, 10, ' ');
                        } else {
                            sShnNmA = "                              ";
                            sKikakuNmA = "          ";
                        }
                        // 品名・規格－１
                        tateShnNm1 = CCStringUtil.trimBoth(sShnNmA.substring(0, 25));
                        // 品名・規格－２
                        tateSHNNM2 = CCStringUtil.trimBoth(sShnNmA.substring(25, 30));
                        // 品名・規格－３
                        tateSHNNM3 = CCStringUtil.trimBoth(sKikakuNmA);
                        // 検収原単価
                        dShuSeiGenK = Double.parseDouble(rKenGenK);
                        tateKENGENK = Double.toString(dShuSeiGenK);
                        // 検収原価
                        if (rKenGenK.equals(rHatGenk)) {
                            tateSHUSEIGENK = "0";
                        } else {
                            tateSHUSEIGENK = Double.toString(dShuSeiGenK);
                        }
                        // 商品コード
                        tateSHNCD = sSHNCD;
                        // 入数－２
                        dIrisu2 = Double.parseDouble(rHatIrisu);
                        tateIRISU2 = Long.toString(Math.round(dIrisu2 - 0.5));
                        // ケース数－２
                        dCase2 = Double.parseDouble(rHatCaseSu);
                        tateCASE2 = Long.toString(Math.round(dCase2 - 0.5));
                        // 数量
                        dHatSu = dIrisu2 * dCase2;
                        tateHATSU = CCStringUtil.trimBoth(Double.toString(dHatSu));
                        // 原単価
                        dGenK = Double.parseDouble(rHatGenk);
                        tateGENK =
                                CCStringUtil
                                        .trimBoth(CCStringUtil.getRightStr(CCStringUtil.puddLeft(
                                                CCStringUtil.trimBoth(Double.toString(dGenK)), 8, ' '), 8));
                        long lWkGENK = Math.round(dGenK * 100);
                        long lWkHATSU = Math.round(dHatSu * 10);
                        // 原価金額
                        lGenkKin = Math.round(((lWkGENK * lWkHATSU) / 1000) - 0.5);
                        tateGENKKIN =
                                CCStringUtil.trimBoth(CCStringUtil.getRightStr(CCStringUtil.puddLeft(
                                        CCStringUtil.trimBoth(Long.toString(lGenkKin)), 9, ' '), 9));
                        // 売単価
                        dBaiK = Double.parseDouble(rHatBaik);
                        tateBAIK =
                                CCStringUtil
                                        .trimBoth(CCStringUtil.getRightStr(CCStringUtil.puddLeft(
                                                CCStringUtil.trimBoth(Long.toString(Math.round(dBaiK))), 8, ' '), 8));
                        long lWkBAIK = Math.round(dBaiK);
                        // 売価金額
                        lBaikKin = Math.round(((lWkBAIK * lWkHATSU) / 10) - 0.5);
                        tateBAIKKIN =
                                CCStringUtil.trimBoth(CCStringUtil.getRightStr(CCStringUtil.puddLeft(
                                        CCStringUtil.trimBoth(Long.toString(lBaikKin)), 9, ' '), 9));
                        Hajp1170Dtocsv rowcsv = new Hajp1170Dtocsv();
                        rowcsv.setD2(tateD2);
                        rowcsv.setShaNm(tateSHANM);
                        rowcsv.setTriNm(tateTRINM);
                        rowcsv.setTenNm(tateTENNM);
                        rowcsv.setJigyoubuCd(rJigyoubuCd);
                        rowcsv.setTenCd(rTenCd);
                        rowcsv.setBmnCd(tateBMNCD);
                        rowcsv.setDpyKbn(rDpyKbn);
                        rowcsv.setDpyNo(rDpyNo);
                        rowcsv.setTriCd(tateTRICD);
                        rowcsv.setHatDd(tateHATDD);
                        rowcsv.setNhnDd(tateNHNDD);
                        rowcsv.setBin(tateBIN);
                        rowcsv.setShnNm1(tateShnNm1);
                        rowcsv.setShnNm2(tateSHNNM2);
                        rowcsv.setShnNm3(tateSHNNM3);
                        rowcsv.setShuSeiGenK(tateSHUSEIGENK);
                        rowcsv.setShnCd(tateSHNCD);
                        rowcsv.setIrisu1(tateIRISU1);
                        rowcsv.setIrisu2(tateIRISU2);
                        rowcsv.setCase1(tateCASE1);
                        rowcsv.setCase2(tateCASE2);
                        rowcsv.setTani(rHatTani);
                        rowcsv.setHatsu(tateHATSU);
                        rowcsv.setGenk(tateGENK);
                        rowcsv.setGenKKin(tateGENKKIN);
                        rowcsv.setBaiK(tateBAIK);
                        rowcsv.setBaikKin(tateBAIKKIN);
                        rowcsv.setKenGenK(tateKENGENK);
                        rowcsv.setGyou(rMeisaiNo);
                        lstCsvTmp.add(rowcsv);
                    }
                }
            }

            // 有効なデータが１件もない場合はメッセージを表示して終了
            if (lstCsvTmp.size() < 1) {
                errRes.addErrorInfo(CCMessageConst.MSG_KEY_NO_FOUND_DATA);
                return notFound(Json.toJson(errRes));
            }
            String tantoCd = context.getTantoCode();
            for (int i = 0; i < lstCsvTmp.size(); i++) {
                Hajp1170Dtocsv rowcsv = lstCsvTmp.get(i);
                Hapr1170Report row = new Hapr1170Report();
                row.h00_progid = PROGRAM_ID;
                row.h00_version = PROGRAM_VERSION;
                row.h01_tanto_code = tantoCd;
                row.h01_d2 = rowcsv.getD2();
                row.h01_sha_nm = rowcsv.getShaNm();
                row.h01_tri_nm = rowcsv.getTriNm();
                row.h01_ten_nm = rowcsv.getTenNm();
                row.h01_jigyo_cd = rowcsv.getJigyoubuCd();
                row.h01_ten_cd = rowcsv.getTenCd();
                row.h01_bmn_cd = rowcsv.getBmnCd();
                row.h01_dpy_kbn = rowcsv.getDpyKbn();
                row.h01_dpy_no = CCStringUtil.trimBoth(rowcsv.getDpyNo());
                row.h01_tri_cd = rowcsv.getTriCd();
                row.h01_hat_dd = rowcsv.getHatDd();
                row.h01_nhn_dd = rowcsv.getNhnDd();
                row.h01_bin = rowcsv.getBin();
                row.m01_shn_nm_1 = rowcsv.getShnNm1();
                row.m01_shn_nm_2 = rowcsv.getShnNm2();
                row.m01_shn_nm_3 = rowcsv.getShnNm3();
                row.m01_shusei_genk = rowcsv.getShuSeiGenK();
                row.m01_shn_cd = rowcsv.getShnCd();
                row.m01_iri_su_1 = rowcsv.getIrisu1();
                row.m01_iri_su_2 = rowcsv.getIrisu2();
                row.m01_case_1 = rowcsv.getCase1();
                row.m01_case_2 = rowcsv.getCase2();
                row.m01_tani = rowcsv.getTani();
                row.m01_hat_su = rowcsv.getHatsu();
                row.m01_genk = rowcsv.getGenk();
                row.m01_genk_kin = rowcsv.getGenKKin();
                row.m01_baik = rowcsv.getBaiK();
                row.m01_baik_kin = rowcsv.getBaikKin();
                row.m01_ken_genk = rowcsv.getKenGenK();
                row.m01_gyou = rowcsv.getGyou();
                csvBean.add(row);
            }
            if (csvBean.isEmpty()) {
                // 対象データ０件
                errRes.addErrorInfo(CCMessageConst.MSG_KEY_VALIDATION_ERROR);
                errRes.addErrorInfo("", CCMessageConst.MSG_KEY_NO_FOUND_DATA);
                return notFound();
            }
            // ＣＳＶ作成（データ出力）
            try {
                csvManager.save(csvBean, Hapr1170Report.class).to(new File(cru.getCsvFilePath()),
                        CCReportUtil.CSV_OUTPUT_ENCODING);
            } catch (Exception e) {
                throw new ChaseException(e);
            }
            cru.makePDF();
            List<Hajp1170ResultForm> reportList = new ArrayList<Hajp1170ResultForm>();
            Hajp1170ResultForm resultForm = new Hajp1170ResultForm();
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
     * データのエラーチェック処理を行います。
     * 
     * @param req
     *            Hajp1170CondForm
     * @return boolean
     * @throws Exception
     *             e
     */

    private boolean docheckData(Hajp1170CondForm req) throws Exception {
        boolean bChkData = true;
        boolean bChkKaishaCd = true;
        boolean bChkJigyobuCd = true;
        String sKaisyaCd = req.getKaisyaCd();
        String sTriFrCd = "";
        String sTriToCd = "";
        String sShiFrCd = "";
        String sShiToCd = "";
        String sJigyobuCd = req.getJigyobuCd();
        String sTenCdFr = req.getTenCdSt();
        String sTenCdTo = req.getTenCdEd();
        String sSysDate = req.getSysdate();

        // 半角のみチェック
        if (CCStringUtil.getByteLen(sKaisyaCd) != sKaisyaCd.length()) {
            errRes.addErrorInfo("kaisyaCd", CCMessageConst.MSG_KEY_ENTER_INPUT_TWO_DIGITS);
            bChkData = false;
            bChkKaishaCd = false;
        } else {
            if (!execCoCd(sKaisyaCd, sSysDate)) {
                errRes.addErrorInfo("kaisyaCd", CCMessageConst.MSG_KEY_NOT_REGISTERED_NO_PARAM);
                bChkData = false;
                bChkKaishaCd = false;
            }
        }

        // 取引先ＦＲチェック
        if (!CCStringUtil.isEmpty(req.getTriCdSt())) {
            if ((req.getTriCdSt().length() != 9)
                    || CCStringUtil.getByteLen(req.getTriCdSt()) != req.getTriCdSt().length()) {
                errRes.addErrorInfo("triCdSt", CCMessageConst.MSG_KEY_ENTER_LENGTH_NINE_DIGITS);
                bChkData = false;
            } else {
                sTriFrCd = req.getTriCdSt().substring(0, 6);
                sShiFrCd = req.getTriCdSt().substring(6, 9);
                if (!executeTriCd(req.getTriCdSt(), sSysDate)) {
                    errRes.addErrorInfo("triCdSt", CCMessageConst.MSG_KEY_NOT_REGISTERED_NO_PARAM);
                    bChkData = false;
                }
            }
        }

        // 取引先(TO)のみの入力の場合
        if (CCStringUtil.isEmpty(req.getTriCdSt()) && !CCStringUtil.isEmpty(req.getTriCdEd())) {
            errRes.addErrorInfo("triCdSt", CCMessageConst.MSG_KEY_INPUT_REQUIRED_ENTER);
            bChkData = false;
        }

        // 取引先ＴＯチェック
        if (!CCStringUtil.isEmpty(req.getTriCdEd())) {
            if ((req.getTriCdEd().length() != 9)
                    || (CCStringUtil.getByteLen(req.getTriCdEd()) != req.getTriCdEd().length())) {
                errRes.addErrorInfo("triCdEd", CCMessageConst.MSG_KEY_ENTER_LENGTH_NINE_DIGITS);
                bChkData = false;
            } else {
                sTriToCd = req.getTriCdEd().substring(0, 6);
                sShiToCd = req.getTriCdEd().substring(6, 9);
                if (!executeTriCd(req.getTriCdEd(), sSysDate)) {
                    errRes.addErrorInfo("triCdEd", CCMessageConst.MSG_KEY_NOT_REGISTERED_NO_PARAM);
                    bChkData = false;
                }
            }
        }

        // 取引先がFROMとTO両方入力されて、FROMの方がTOより大きい場合
        if (!CCStringUtil.isEmpty(req.getTriCdSt()) && !CCStringUtil.isEmpty(req.getTriCdEd())) {
            if (req.getTriCdSt().compareTo(req.getTriCdEd()) > 0) {
                errRes.addErrorInfo("triCdSt", CCMessageConst.MSG_KEY_ERROR_TORIHIKI_FR_GREATER);
                errRes.addErrorInfo("triCdEd", CCMessageConst.MSG_KEY_ERROR_TORIHIKI_FR_GREATER);
                bChkData = false;
            }
        }

        // 取引先、支店ともにFROM･TOが入力された場合に、取引先コードの値が異なる場合
        if (!CCStringUtil.isEmpty(sTriFrCd) && !CCStringUtil.isEmpty(sTriToCd)) {
            if (!sTriFrCd.equals(sTriToCd)) {
                errRes.addErrorInfo("triCdSt", CCMessageConst.MSG_KEY_REQUIRED_TORIHIKI_SAME_ENTER);
                errRes.addErrorInfo("triCdEd", CCMessageConst.MSG_KEY_REQUIRED_TORIHIKI_SAME_ENTER);
                bChkData = false;
            }
        }

        // 取引先がFROMとTO両方同じ値が入力されていて、さらに支店もFROMとTO両方入力されていた場合に
        // 支店FROMの方が支店TOよりも大きい場合
        if (!CCStringUtil.isEmpty(sTriFrCd) && !CCStringUtil.isEmpty(sTriToCd)) {
            if (sTriFrCd.equals(sTriToCd)) {
                if (!CCStringUtil.isEmpty(sShiFrCd) && !CCStringUtil.isEmpty(sShiToCd)) {
                    if (sShiFrCd.compareTo(sShiToCd) > 0) {
                        errRes.addErrorInfo("triCdSt", CCMessageConst.MSG_KEY_ERROR_BRANCH_FR_GREATER);
                        errRes.addErrorInfo("triCdEd", CCMessageConst.MSG_KEY_ERROR_BRANCH_FR_GREATER);
                        bChkData = false;

                    }
                }
            }
        }

        // 事業部コードチェック
        if (!CCStringUtil.isEmpty(sJigyobuCd)) {
            // 桁数チェック
            if ((sJigyobuCd.getBytes().length != 2) || (CCStringUtil.getByteLen(sJigyobuCd) != sJigyobuCd.length())) {
                errRes.addErrorInfo("jigyobuCd", CCMessageConst.MSG_KEY_ENTER_INPUT_TWO_DIGITS);
                bChkData = false;
                bChkJigyobuCd = false;
            } else {
                if ("?".equals(execProjectCd(sKaisyaCd, sJigyobuCd, sSysDate))) {
                    errRes.addErrorInfo("jigyobuCd", CCMessageConst.MSG_KEY_NOT_REGISTERED_NO_PARAM);
                    bChkData = false;
                    bChkKaishaCd = false;
                }
            }
        } else {
            bChkJigyobuCd = false;
        }

        // 店舗コード(FR)チェック
        if (bChkKaishaCd) {
            if (bChkJigyobuCd) {
                // 桁数チェック
                if (!CCStringUtil.isEmpty(sTenCdFr)) {
                    if ((sTenCdFr.getBytes().length != 3)
                            || (CCStringUtil.getByteLen(sTenCdFr) != sTenCdFr.length())) {
                        errRes.addErrorInfo("tenCdSt", CCMessageConst.MSG_KEY_INPUT_THREE_DIGITS_LONG);
                        bChkData = false;
                    } else {
                        if ("?".equals(executeTenCd(sKaisyaCd + sJigyobuCd + sTenCdFr, sSysDate))) {
                            errRes.addErrorInfo("tenCdSt", CCMessageConst.MSG_KEY_NOT_REGISTERED_NO_PARAM);
                            bChkData = false;
                        }
                    }
                }
            }
        }

        // 店舗コード(TO)チェック
        if (bChkKaishaCd) {
            if (bChkJigyobuCd) {
                // 桁数チェック
                if (!CCStringUtil.isEmpty(sTenCdTo)) {
                    if ((sTenCdTo.getBytes().length != 3)
                            || (CCStringUtil.getByteLen(sTenCdTo) != sTenCdTo.length())) {
                        errRes.addErrorInfo("tenCdEd", CCMessageConst.MSG_KEY_INPUT_THREE_DIGITS_LONG);
                        bChkData = false;
                    } else {
                        if ("?".equals(executeTenCd(sKaisyaCd + sJigyobuCd + sTenCdTo, sSysDate))) {
                            errRes.addErrorInfo("tenCdEd", CCMessageConst.MSG_KEY_NOT_REGISTERED_NO_PARAM);
                            bChkData = false;
                        }
                    }
                }
            }
        }

        // 店舗コードの整合性チェック
        if (!bChkJigyobuCd) {
            if (!CCStringUtil.isEmpty(sTenCdFr)) {
                errRes.addErrorInfo("jigyobuCd", CCMessageConst.MSG_KEY_NOT_REGISTERED_NO_PARAM);
                bChkData = false;
            }
            if (!CCStringUtil.isEmpty(sTenCdTo)) {
                errRes.addErrorInfo("jigyobuCd", CCMessageConst.MSG_KEY_NOT_REGISTERED_NO_PARAM);
                bChkData = false;
            }
        }

        if (!bChkData) {
            ErrorInfo error = errRes.getErrorInfo(CCMessageConst.MSG_KEY_VALIDATION_ERROR);
            errRes.getErrors().add(0, error);
        }
        return bChkData;

    }

    /**
     * 商品コード検索処理を行います。<BR>
     * 
     * @param sJanCd  商品コード
     * @param sHatDdFr  発注日
     * @return HashMap<String , String>
     */
    private HashMap<String, String> executeJanCd(String sJanCd, String sHatDdFr) {
        M007kijmExample example = new M007kijmExample();
        example.setSearchDate(sHatDdFr);
        example.createCriteria().andJanCdEqualTo(sJanCd);
        List<M007kijm> lstData = this.m007kijmMapper.selectByExample(example);
        HashMap<String, String> result = new HashMap<String, String>();
        if (lstData.size() <= 0 || lstData.get(0).getActKbn().equals(ACT_KBN_DELETE)) {
            result.put("sShnNmA", "");
            result.put("sKikakuNmA", "");
            result.put("fLag", "0");
        } else {
            result.put("sShnNmA", lstData.get(0).getShnNmA());
            result.put("sKikakuNmA", lstData.get(0).getKikakuNmA());
            result.put("fLag", "1");
        }
        return result;
    }

    /**
     * 取引先コード検索処理を行います。<BR>
     * 
     * @param sTriCd  取引先コード
     * @param sHakkoDay   発効日
     * @return true：処理成功、false：処理失敗
     */
    private boolean executeTriCd(String sTriCd, String sHakkoDay) {
        M011trimExample example = new M011trimExample();
        example.createCriteria().andTriCdEqualTo(sTriCd);
        example.setSearchDate(sHakkoDay);
        List<M011trim> lstData = this.m011trimMapper.selectByExample(example);
        if (lstData.size() > 0) {
            return (!ACT_KBN_DELETE.equals(lstData.get(0).getActKbn()));
        }
        return false;
    }

    /**
     * 店舗コード検索処理を行います。<BR>
     * 
     * @param sTenCd  店舗コード
     * @param sHakkoDay  発効日
     * @return true：処理成功、false：処理失敗
     */
    private String executeTenCd(String sTenCd, String sHakkoDay) {
        M006tenmExample example = new M006tenmExample();
        example.setSearchDate(sHakkoDay);
        example.createCriteria().andTenCdEqualTo(sTenCd);
        List<M006tenm> lstData = this.m006tenmMapper.selectByExample(example);
        if ((lstData.size() <= 0) || (lstData.get(0).getActKbn().equals(ACT_KBN_DELETE))) {
            return "?";
        }
        return lstData.get(0).getTenNmA();
    }

    /**
     * execProjectCd
     * 
     * @param sKaisyaCd  会社コード
     * @param sJigyobuCd  事業部コード
     * @param sSysDate  システムの日
     * @return String
     */
    private String execProjectCd(String sKaisyaCd, String sJigyobuCd, String sSysDate) {

        M001jgymExample example = new M001jgymExample();
        example.setSearchDate(sSysDate);
        example.createCriteria().andKaisyaCdEqualTo(sKaisyaCd).andJigyobuCdEqualTo(sJigyobuCd);
        List<M001jgym> lstData = this.m001jgymMapper.selectByExample(example);
        if ((lstData.size() <= 0) || (lstData.get(0).getActKbn().equals(ACT_KBN_DELETE))) {
            return "?";
        }
        return lstData.get(0).getJgyNmA();
    }

    /**
     * execCoCd
     * 
     * @param sKaisyaCd  会社コード
     * @param sSysDate  システムの日
     * @return boolean
     */
    private boolean execCoCd(String sKaisyaCd, String sSysDate) {
        M000kaimExample example = new M000kaimExample();
        example.setSearchDate(sSysDate);
        example.createCriteria().andKaisyaCdEqualTo(sKaisyaCd);
        List<M000kaim> lstData = this.m000kaimMapper.selectByExample(example);
        if (lstData.size() <= 0 || lstData.get(0).getActKbn().equals(ACT_KBN_DELETE)) {
            return false;
        }
        return true;
    }
}
