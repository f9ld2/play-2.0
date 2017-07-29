// /////////////////////////////////////////////////////////////////////
// Copyright(C) 2015 NEC Solution Innovators, Ltd. All Rights Reserved.
// /////////////////////////////////////////////////////////////////////

/*
 * ====================================================================
 * 
 * 機能名称 : 仕入先別納品率ﾘｽﾄ. 改版履歴 Rev. 改版年月日 改版者名 内容 1.0
 * 
 * 2014.04.17 TUANVT 新規作成
 * 
 * ===================================================================
 */

package jp.co.necsoft.web_md.core.app.controllers.si;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import jp.co.necsoft.web_md.common.ChaseException;
import jp.co.necsoft.web_md.core.app.file.report.Sipr1180Report;
import jp.co.necsoft.web_md.core.app.forms.si.Sijp1180ResultForm;
import jp.co.necsoft.web_md.core.common.CCMessageConst;
import jp.co.necsoft.web_md.core.common.CCSecurityAuthenticator;
import jp.co.necsoft.web_md.core.common.CCSystemContext;
import jp.co.necsoft.web_md.core.common.forms.ErrorResponse;
import jp.co.necsoft.web_md.core.common.utils.CCFileEdit;
import jp.co.necsoft.web_md.core.common.utils.CCReportUtil;
import jp.co.necsoft.web_md.core.common.utils.CCStringUtil;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;

import com.orangesignal.csv.manager.CsvManager;
import com.orangesignal.csv.manager.CsvManagerFactory;

/**
*仕入先別納品率ﾘｽﾄのControllerクラス
*
*/
@Security.Authenticated(CCSecurityAuthenticator.class)
public class Sijp1180Ctrl extends Controller {

    /** システムコンテキスト */
    @Inject
    private CCSystemContext context;
    /**COBOLが出力するCSV型ファイルID*/
    private static final String FILE_NAME = "S725SNRT";
    /**JSPファイル名*/
    private static final String JSP_FILE = "SIJP1180";
    /**プログラムＩＤ*/
    private static final String PROGRAM_ID = "SIPR1180";
    /**バージョン**/
    private static final String PROGRAM_VERSION = "V1.00";
    /**connectXのJOBID*/
    private static final String JOB_ID = "SI118";
    /**エラー応答*/
    @Inject
    private ErrorResponse errRes;

    /**
    * 引数とリクエスト(パラメータ)を条件にDBからデータ(複数件)を検索し返却する。*
    *  @return クライアントへ返却する結果
    */
    public Result query() {
        // 帳票出力
        CCReportUtil cru = new CCReportUtil("SISV1180", context);
        // 入力担当者ＩＤ取得
        String sInpTantoID = context.getTantoCode();
        String csvdir = context.getContextProperty("cc.app.cm.csv_path");
        String csvfilepath =
                csvdir + "--------------" + "_" + sInpTantoID + "_" + JSP_FILE + "_" + FILE_NAME + ".csv";
        String pdffilepath = cru.getPdfFilePath();
        String motocsvfilepath = csvdir + FILE_NAME;

        if (!CCFileEdit.isExist(cru.getDBServTempPath(FILE_NAME))) {
            errRes.addErrorInfo(CCMessageConst.MSG_KEY_CSV_FILE_NOT_EXIST);
            return badRequest(Json.toJson(errRes));
        } else {
            CCFileEdit.copyFile(cru.getDBServTempPath(FILE_NAME), motocsvfilepath);
        }
        /** CSVファイルより取得した仕入データをセット*/
        ArrayList<String[]> lstSirData = new ArrayList<String[]>();
        // 出力するデータを抽出する
        execute(motocsvfilepath, lstSirData);

        // ＣＳＶ作成（データ取得）
        List<Sipr1180Report> csvBean = new ArrayList<Sipr1180Report>();
        CsvManager csvManager = CsvManagerFactory.newCsvManager();

        if (lstSirData.size() == 0) {
            lstSirData = null;
            errRes.addErrorInfo(CCMessageConst.MSG_KEY_NO_FOUND_DATA);
            return badRequest(Json.toJson(errRes));
        }
        // 明細部
        for (int i = 1; i < lstSirData.size(); i++) {

            String[] sCsvData = (String[]) lstSirData.get(i);
            // ヘッダカラム数にあわせる
            Sipr1180Report sipr1180Report = new Sipr1180Report();
            String[] sWriteData = new String[sipr1180Report.getClass().getDeclaredFields().length];
            // 共通ヘッダ部（プログラムＩＤ、バージョン、担当者名）
            sWriteData[0] = '"' + PROGRAM_ID + '"';
            sWriteData[1] = '"' + PROGRAM_VERSION + '"';
            sWriteData[2] = sInpTantoID;
            for (int j = 3; j < sWriteData.length; j++) {
                if (j < sCsvData.length) {
                    sWriteData[j] = sCsvData[j];
                }
            }
            sipr1180Report.h00_progid = sWriteData[0];
            sipr1180Report.h00_version = sWriteData[1];
            sipr1180Report.h01_tanto_code = sWriteData[2];
            sipr1180Report.h01_kaisya_code = sWriteData[3];
            sipr1180Report.h01_kaisya_name = sWriteData[4];
            sipr1180Report.h01_jigyobu_code = sWriteData[5];
            sipr1180Report.h01_jigyobu_name = sWriteData[6];
            sipr1180Report.h01_ym = sWriteData[7];
            sipr1180Report.m01_tori_cd = sWriteData[8];
            sipr1180Report.m01_tori_nm = sWriteData[9];
            sipr1180Report.m01_to_hat = sWriteData[10];
            sipr1180Report.m01_to_nou = sWriteData[11];
            sipr1180Report.m01_to_ritu = sWriteData[12];
            sipr1180Report.m01_rui_hat = sWriteData[13];
            sipr1180Report.m01_rui_nou = sWriteData[14];
            sipr1180Report.m01_rui_ritu = sWriteData[15];
            sCsvData = null;
            sWriteData = null;
            csvBean.add(sipr1180Report);
        }
        lstSirData = null;
        // ＣＳＶ作成（データ出力）
        try {
            csvManager.save(csvBean, Sipr1180Report.class).to(new File(csvfilepath), CCReportUtil.CSV_OUTPUT_ENCODING);
        } catch (Exception e) {
            throw new ChaseException(e);
        }

        cru.makePDF(JOB_ID, csvfilepath, pdffilepath);
        List<Sijp1180ResultForm> reportList = new ArrayList<Sijp1180ResultForm>();
        Sijp1180ResultForm resultForm = new Sijp1180ResultForm();
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

    /**
     * ＣＳＶ出力用データを取得します。
     * @param sCsvFilePath  ＣＳＶファイルパス
     * @param  lstSirData  CSVファイルより取得した仕入データをセット
     */
    public void execute(String sCsvFilePath, ArrayList<String[]> lstSirData) {
        File file = new File(sCsvFilePath);
        // 指定されたファイルの内容を読み込む
        FileReader fr;
        BufferedReader br;

        if (!file.exists()) {
            file = null;
            fr = null;
            br = null;
            throw new ChaseException("ファイルが存在しない");
        }
        if (!file.isFile()) {
            file = null;
            fr = null;
            br = null;
            throw new ChaseException("ファイルではない");
        }
        if (!file.canRead()) {
            file = null;
            fr = null;
            br = null;
            throw new ChaseException("読むことはできません");
        }

        try {
            fr = new FileReader(file);
            br = new BufferedReader(fr);

            // １行単位で入力
            String sLine = "";
            while ((sLine = br.readLine()) != null) {
                String[] sRecArray = doSplit(sLine, ",");
                // スペースの削除
                for (int i = 0; i < sRecArray.length; i++) {
                    sRecArray[i] = '"' + CCStringUtil.trimBoth(sRecArray[i]) + '"';
                }
                lstSirData.add(sRecArray);
                sRecArray = null;
            }

            fr.close();
            file = null;
            fr = null;
        } catch (IOException e) {
            br = null;
            throw new ChaseException(e);
        }
    }

    /**
     * 配列への分割処理
     * @param  sData     対象となる文字列
     * @param  sSepa   セパレータ
     * @return String    分割された文字列配列
     */
    public String[] doSplit(String sData, String sSepa) {
        int iCnt;
        int iPos;
        int iFindPos;
        String[] sOne = new String[1];

        // データなし
        if (sData.length() == 0) {

            // 文字列配列の個数を１にする
            sOne[0] = "";
            return sOne;

        }
        // セパレータなし
        if (sSepa.length() == 0) {

            // 文字列配列の個数を１にする
            sOne[0] = sData;
            return sOne;

        }

        // セパレータの個数（ iCnt ）を数えて、個数分の文字列配列を宣言する
        iCnt = 0;
        iPos = 0;
        iFindPos = sData.indexOf(sSepa);
        while (iFindPos != -1) {
            // ポインタをずらす
            iPos = sData.indexOf(sSepa, iPos) + sSepa.length();
            // 次の文字列を検索
            iFindPos = sData.indexOf(sSepa, iPos);
            iCnt++;
        }
        String[] sTemp = new String[iCnt + 1];

        // 分割なし
        if (iCnt == 0) {
            // 文字列配列の個数を１にする
            sOne[0] = sData;
            return sOne;
        }

        // セパレータで分割して個数分の文字列配列に格納する
        iCnt = 0;
        iPos = 0;
        iFindPos = sData.indexOf(sSepa);

        while (iFindPos != -1) {
            // セパレータが連続している時
            if (iPos == iFindPos) {
                // ""の要素を生成
                sTemp[iCnt] = "";
            } else {
                sTemp[iCnt] = sData.substring(iPos, iFindPos);
            }

            // ポインタをずらす
            iPos = sData.indexOf(sSepa, iPos) + sSepa.length();

            // 次の文字列を検索
            iFindPos = sData.indexOf(sSepa, iPos);
            iCnt++;

        }
        sTemp[iCnt] = sData.substring(iPos);

        return sTemp;
    }
}
