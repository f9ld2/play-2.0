// /////////////////////////////////////////////////////////////////////
// Copyright(C) 2015 NEC Solution Innovators, Ltd. All Rights Reserved.
// /////////////////////////////////////////////////////////////////////

/*
 * ==================================================================== 機能名称 : 帳票出力ユーティリティ 改版履歴 Rev. 改版年月日 改版者名 内容 1.0
 * 2014.02.04 T.Matsuda 新規作成 ===================================================================
 */
package jp.co.necsoft.web_md.core.common.utils;

import java.util.HashMap;
import java.util.Map;

import jp.co.fit.UCXSingle.UCXSingle;
import jp.co.necsoft.web_md.common.ChaseException;
import jp.co.necsoft.web_md.core.common.CCSystemContext;

import org.joda.time.DateTime;

import play.Play;

import com.google.inject.Inject;

/**
 * 帳票出力ユーティリティクラス
 * 
 */
public class CCReportUtil {
    /** Encoding of output csv */
    public static final String CSV_OUTPUT_ENCODING = "UTF8";
    /** mUcx Data initialize */
    private Map<String, String[]> mUcxIniData = new HashMap<String, String[]>();
    /** Program ID */
    private String sProgramId = "";
    /** Pdf File Path */
    private String strPdfFilePath;
    /** Csv File Path */
    private String strCsvFilePath;
    /** Pdf Url */
    private String strPdfUrl;
    /** Csv Url */
    private String strCsvUrl;
    /** SvfSvr */
    private String strSvfSvr;

    /** context */
    @Inject
    private CCSystemContext context;

    /**
     * コンストラクタ
     * 
     * @param sParam1 プログラムID
     * @param sParam2 CCSystemContext
     **/
    public CCReportUtil(String sParam1, CCSystemContext sParam2) {
        this.createUcxIniData();
        sProgramId = sParam1;
        context = sParam2;
        strSvfSvr = Play.application().configuration().getString("cc.app.cm.svf_svr");
        this.createReportFilePath();

    }

    /**
     * 帳票出力に必要なファイルパスを返却する。
     * 
     * @param プログラムID
     * @param UniversalConnectXのJOBID
     * @param 帳票ID
     * @return PDF出力ファイルパス, CSV出力ファイルパス, PDFURL, CSVURL
     **/
    private String createReportFilePath() {
        String pdfPath = Play.application().configuration().getString("cc.app.cm.pdf_path");
        String csvPath = Play.application().configuration().getString("cc.app.cm.csv_path");
        String svPdfPath = Play.application().configuration().getString("cc.app.cm.pdf_url");
        String svCsvPath = Play.application().configuration().getString("cc.app.cm.csv_url");

        DateTime dt = new DateTime();
        int year = dt.getYear();
        String afterYear = String.valueOf(year);

        int month = dt.getMonthOfYear();
        String aftermonth = String.valueOf(month);
        if (month < 10) {
            aftermonth = "0" + aftermonth;
        }

        int date = dt.getDayOfMonth();
        String afterdate = String.valueOf(date);
        if (date < 10) {
            afterdate = "0" + afterdate;
        }

        int hour = dt.getHourOfDay();
        String afterhour = String.valueOf(hour);
        if (hour < 10) {
            afterhour = "0" + afterhour;
        }

        int minute = dt.getMinuteOfHour();
        String afterminute = String.valueOf(minute);
        if (minute < 10) {
            afterminute = "0" + afterminute;
        }

        int second = dt.getSecondOfMinute();
        String aftersecond = String.valueOf(second);
        if (second < 10) {
            aftersecond = "0" + aftersecond;
        }

        String strdate = afterYear + aftermonth + afterdate;
        String strtime = afterhour + afterminute + aftersecond;

        String strFileName =
                strdate + strtime + "_" + CCStringUtil.trimRight(context.getTantoCode()) + "_"
                        + sProgramId.substring(0, 2) + "JP" + sProgramId.substring(4, 8) + "_" + getReportFileId();

        strCsvFilePath = csvPath + strFileName + ".csv";
        strPdfFilePath = pdfPath + strFileName + ".pdf";
        strCsvUrl = svCsvPath + strFileName + ".csv";
        strPdfUrl = svPdfPath + strFileName + ".pdf";

        return strPdfFilePath;
    }

    /**
     * DBサーバのWEBMD_HOME/temp/配下(NFS共有)のファイルの正規の共有パスを取得します。
     * 
     * @param filename
     *            ファイル名
     * @return DBサーバのtemp/配下のファイルの正規のパス
     **/
    public final String getDBServTempPath(String filename) {
        String tempdir = Play.application().configuration().getString("cc.app.cm.dbsv_tempdir");
        String filepath = tempdir + filename;
        return filepath;
    }

    /**
     * DBサーバのWEBMD_HOME/file/配下(NFS共有)のファイルの正規の共有パスを取得します。
     * 
     * @param filename
     *            ファイル名
     * @return DBサーバのfile/配下のファイルの正規のパス
     **/
    public final String getDBServFilePath(String filename) {
        String dbServerFilePath = Play.application().configuration().getString("cc.app.cm.dbsv_filedir");
        String filepath = dbServerFilePath + filename;
        return filepath;
    }

    /**
     * PDFファイルのフルパスを取得する。
     * 
     * @return PDFファイルパス
     **/
    public String getPdfFilePath() {
        return strPdfFilePath;

    }

    /**
     * CSVファイルのフルパスを取得する。
     * 
     * @return CSVファイルパス
     **/
    public String getCsvFilePath() {
        return strCsvFilePath;

    }

    /**
     * PDFファイルのURLを取得する。
     * 
     * @return PDFファイルURL
     **/
    public String getPdfUrl() {
        return strPdfUrl;

    }

    /**
     * CSVファイルのURLを取得する。
     * 
     * @return CSVファイルURL
     **/
    public String getCsvUrl() {
        return strCsvUrl;

    }

    /**
     * Svfサーバのアドレスを取得する。
     * 
     * @return SvfサーバIPアドレス
     **/
    public String getSvfSvr() {
        return strSvfSvr;

    }

    /**
     * Unicon.iniに登録したJobIDを取得する。
     * 
     * @return 帳票JobID
     **/
    private String getReportJobId() {
        String[] strUniconData = mUcxIniData.get(sProgramId);
        return strUniconData[0];

    }

    /**
     * Unicon.iniに登録した帳票ファイル名を取得する。
     * 
     * @return 帳票ファイルID
     **/
    private String getReportFileId() {
        String[] strUniconData = mUcxIniData.get(sProgramId);

        return strUniconData[1];

    }

    /**
     * PDFファイルを生成します。
     * 
     * @param なし
     * @return true：PDFファイル生成成功、false：PDFファイル生成失敗
     **/
    public boolean makePDF() {
        return makePDF(strCsvFilePath, strPdfFilePath);

    }

    /**
     * PDFファイルを生成します。
     * 
     * @param csvfilepath
     *            CSVファイルパス
     * @param pdffilepath
     *            PDFファイルパス
     * @return true：PDFファイル生成成功、false：PDFファイル生成失敗
     **/
    private boolean makePDF(String csvfilepath, String pdffilepath) {
        return makePDF(getReportJobId(), csvfilepath, pdffilepath);

    }

    /**
     * PDFファイルを生成します。
     * 
     * @param jobid
     *            ジョブID
     * @param csvfilepath
     *            CSVファイルパス
     * @param pdffilepath
     *            PDFファイルパス
     * @return true：PDFファイル生成成功、false：PDFファイル生成失敗
     **/
    public boolean makePDF(String jobid, String csvfilepath, String pdffilepath) {
        int iTimeout = 18000000; // タイムアウト時間(秒)
        try {
            UCXSingle ucxsgl =
                    new UCXSingle(this.getSvfSvr(), UCXSingle.DEFAULT_PORT, jobid, csvfilepath, pdffilepath);
            ucxsgl.setResultFileName(pdffilepath);
            ucxsgl.setSourceName(csvfilepath);
            ucxsgl.setUndeleteSourceFile(true);
            ucxsgl.setUndeleteSourceFileIfError(true);
            ucxsgl.setTimeoutValue(iTimeout);
            ucxsgl.setCsvDataFormatUTF8(1);
            ucxsgl.doTransaction();
            int istat = 0;

            istat = ucxsgl.getUCXSingleResult();
            if (istat == 0) {
            	istat = ucxsgl.getUniConXResult();
                if (istat == 0) {

                    return true;
                }
                throw new ChaseException("UniversalConnectX:Error:[" + String.valueOf(istat) + "]");
            }
        } catch (Exception e) {
            throw new ChaseException("UniversalConnectX:Error:[" + e + "]");
        }
        return false;
    }

    /**
     * Unicon.iniに登録した情報
     **/
    private void createUcxIniData() {
        mUcxIniData.put("FKBT1110", new String[] {"KK111", "********" });
        mUcxIniData.put("FKBT1340", new String[] {"KK134", "********" });
        mUcxIniData.put("HABT1170", new String[] {"HA117", "********" });
        mUcxIniData.put("HABT2050", new String[] {"HA110", "********" });
        mUcxIniData.put("HABT4140", new String[] {"HA115", "********" });
        mUcxIniData.put("KKBT1110", new String[] {"KK111", "KKPR1110" });
        mUcxIniData.put("KKBT1340", new String[] {"KK134", "KKPR1340" });
        mUcxIniData.put("MABT1050", new String[] {"MA105", "********" });
        mUcxIniData.put("MABT1051", new String[] {"MA105", "********" });
        mUcxIniData.put("MABT5150", new String[] {"MA106", "********" });
        mUcxIniData.put("MABT5160", new String[] {"MA106", "********" });
        mUcxIniData.put("MABT5170", new String[] {"MA105", "********" });
        mUcxIniData.put("MABT5180", new String[] {"MA105", "********" });
        mUcxIniData.put("MABT5270", new String[] {"MA106", "********" });
        mUcxIniData.put("MABT5280", new String[] {"MA105", "********" });
        mUcxIniData.put("NKBT1110", new String[] {"KK111", "********" });
        mUcxIniData.put("NKBT1340", new String[] {"KK134", "********" });
        mUcxIniData.put("OKBT1110", new String[] {"KK111", "********" });
        mUcxIniData.put("OKBT1340", new String[] {"KK134", "********" });
        mUcxIniData.put("SIBT1120", new String[] {"SI112", "********" });
        mUcxIniData.put("SIBT1130", new String[] {"SI113", "********" });
        mUcxIniData.put("ZIBT4000", new String[] {"ZI003_1", "********" });
        mUcxIniData.put("ZIBT4000", new String[] {"ZI003_2", "********" });
        mUcxIniData.put("ANSV1010", new String[] {"AN001", "ANPR1010" });
        mUcxIniData.put("ANSV1020", new String[] {"AN002", "ANPR1020" });
        mUcxIniData.put("ANSV1030", new String[] {"AN003", "ANPR1030" });
        mUcxIniData.put("ANSV1040", new String[] {"AN004", "ANPR1040" });
        mUcxIniData.put("ANSV1050", new String[] {"AN005", "ANPR1050" });
        mUcxIniData.put("ANSV1060", new String[] {"AN006", "ANPR1060" });
        mUcxIniData.put("ANSV1620", new String[] {"AN162", "ANPR1620" });
        mUcxIniData.put("ANSV1630", new String[] {"AN163", "ANPR1630" });
        mUcxIniData.put("ANSV1640", new String[] {"AN164", "ANPR1640" });
        mUcxIniData.put("ANSV1650", new String[] {"AN165", "ANPR1650" });
        mUcxIniData.put("ANSV1660", new String[] {"AN166", "ANPR1660" });
        mUcxIniData.put("ANSV1670", new String[] {"AN167", "ANPR1670" });
        mUcxIniData.put("ANSV1710", new String[] {"AN171", "ANPR1710" });
        mUcxIniData.put("ANSV1720", new String[] {"AN172", "ANPR1720" });
        mUcxIniData.put("ANSV1730", new String[] {"AN173", "ANPR1730" });
        mUcxIniData.put("ANSV1740", new String[] {"AN174", "ANPR1740" });
        mUcxIniData.put("FKSV1010", new String[] {"KK101", "********" });
        mUcxIniData.put("FKSV1040", new String[] {"KK104", "********" });
        mUcxIniData.put("FKSV1180", new String[] {"KK118", "********" });
        mUcxIniData.put("FKSV1190", new String[] {"KK119", "********" });
        mUcxIniData.put("FKSV1200", new String[] {"KK120", "********" });
        mUcxIniData.put("FKSV1210", new String[] {"KK121", "********" });
        mUcxIniData.put("FKSV1230", new String[] {"KK123", "********" });
        mUcxIniData.put("FKSV1240", new String[] {"KK124", "********" });
        mUcxIniData.put("FKSV1250", new String[] {"KK125", "********" });
        mUcxIniData.put("FKSV1330", new String[] {"OK133", "********" });
        mUcxIniData.put("HASV1010", new String[] {"HA101", "HAPR1010" });
        mUcxIniData.put("HASV1020A", new String[] {"HA111", "H503HONLA" });
        mUcxIniData.put("HASV1020B", new String[] {"HA112", "H503HONLB" });
        mUcxIniData.put("HASV1020C", new String[] {"HA113", "H503HONLC" });
        mUcxIniData.put("HASV1040", new String[] {"HA104", "H604EOSL" });
        mUcxIniData.put("HASV1050", new String[] {"HA105", "H602ICHL" });
        mUcxIniData.put("HASV1060", new String[] {"HA106", "HAPR1060" });
        mUcxIniData.put("HASV1070", new String[] {"HA107", "HAPR1070" });
        mUcxIniData.put("HASV1090", new String[] {"HA109", "HAPR1090" });
        mUcxIniData.put("HASV1100", new String[] {"HA110", "H232OBCV" });
        mUcxIniData.put("HASV1130", new String[] {"HA126", "********" });
        mUcxIniData.put("HASV1140", new String[] {"HA114", "HAPR1140" });
        mUcxIniData.put("HASV1150", new String[] {"HA115", "HAPR1150" });
        mUcxIniData.put("HASV1160", new String[] {"HA116", "HAPR1160" });
        mUcxIniData.put("HASV1170", new String[] {"HA117", "H710TAPR" });
        mUcxIniData.put("HASV1180", new String[] {"HA118", "HAPR1180" });
        mUcxIniData.put("HASV1181", new String[] {"HA1181", "HAPR1181" });
        mUcxIniData.put("HASV1190", new String[] {"HA119", "HAPR1190" });
        mUcxIniData.put("HASV1200", new String[] {"HA120", "HAPR1200" });
        mUcxIniData.put("HASV1210", new String[] {"HA121", "HAPR1210" });
        mUcxIniData.put("HASV1220", new String[] {"HA122", "HAPR1220" });
        mUcxIniData.put("HASV1230", new String[] {"HA123", "HAPR1230" });
        mUcxIniData.put("HASV1240", new String[] {"HA124", "HAPR1240" });
        mUcxIniData.put("HASV1250", new String[] {"HA125", "HAPR1250" });
        mUcxIniData.put("HASV1251", new String[] {"HA125", "HAPR1251" });
        mUcxIniData.put("KKSV1010", new String[] {"KK101", "KKPR1010" });
        mUcxIniData.put("KKSV1060", new String[] {"KK101", "KKPR1060" });
        mUcxIniData.put("KKSV1070", new String[] {"KK101", "KKPR1070" });
        mUcxIniData.put("KKSV1040", new String[] {"KK104", "KKPR1040" });
        mUcxIniData.put("KKSV1180", new String[] {"KK118", "KKPR1180" });
        mUcxIniData.put("KKSV1130", new String[] {"KK113", "KKPR1130" });
        mUcxIniData.put("KKSV1190", new String[] {"KK119", "KKPR1190" });
        mUcxIniData.put("KKSV1200", new String[] {"KK120", "KKPR1200" });
        mUcxIniData.put("KKSV1210", new String[] {"KK121", "KKPR1210" });
        mUcxIniData.put("KKJP1220", new String[] {"KK122", "KKPR1220" });
        mUcxIniData.put("KKSV1230", new String[] {"KK123", "KKPR1230" });
        mUcxIniData.put("KKSV1240", new String[] {"KK124", "KKPR1240" });
        mUcxIniData.put("KKSV1250", new String[] {"KK125", "KKPR1250" });
        mUcxIniData.put("KKSV1311", new String[] {"KK131", "KKPR1311" });
        mUcxIniData.put("LGSV1010", new String[] {"LG101", "LGPR1010" });
        mUcxIniData.put("LGSV1020", new String[] {"LG102", "LGPR1020" });
        mUcxIniData.put("MASV1010", new String[] {"MA101", "MAPR1010" });
        mUcxIniData.put("MASV1020", new String[] {"MA102", "M3910OT1" });
        mUcxIniData.put("MASV1030", new String[] {"MA103", "M5070OT1" });
        mUcxIniData.put("MASV1040", new String[] {"MA104", "M5060OT1" });
        mUcxIniData.put("MASV1050", new String[] {"MA105", "MAPR1050" });
        mUcxIniData.put("MASV1060", new String[] {"MA106", "MAPR1060" });
        mUcxIniData.put("MASV2240", new String[] {"MA224", "MAPR2240" });
        mUcxIniData.put("MASV2250", new String[] {"MA225", "MCSV2250" });
        mUcxIniData.put("MASV3610", new String[] {"MA361", "MAPR3610" });
        mUcxIniData.put("NKSV1010", new String[] {"KK101", "********" });
        mUcxIniData.put("NKSV1040", new String[] {"KK104", "********" });
        mUcxIniData.put("NKSV1180", new String[] {"KK118", "********" });
        mUcxIniData.put("NKSV1190", new String[] {"KK119", "********" });
        mUcxIniData.put("NKSV1200", new String[] {"KK120", "********" });
        mUcxIniData.put("NKSV1210", new String[] {"KK121", "********" });
        mUcxIniData.put("NKSV1230", new String[] {"KK123", "********" });
        mUcxIniData.put("NKSV1240", new String[] {"KK124", "********" });
        mUcxIniData.put("NKSV1250", new String[] {"KK125", "********" });
        mUcxIniData.put("NKSV1330", new String[] {"OK133", "********" });
        mUcxIniData.put("OKSV1010", new String[] {"KK101", "********" });
        mUcxIniData.put("OKSV1040", new String[] {"KK104", "********" });
        mUcxIniData.put("OKSV1180", new String[] {"KK118", "********" });
        mUcxIniData.put("OKSV1190", new String[] {"KK119", "********" });
        mUcxIniData.put("OKSV1200", new String[] {"KK120", "********" });
        mUcxIniData.put("OKSV1210", new String[] {"KK121", "********" });
        mUcxIniData.put("OKSV1230", new String[] {"KK123", "********" });
        mUcxIniData.put("OKSV1240", new String[] {"KK124", "********" });
        mUcxIniData.put("OKSV1250", new String[] {"KK125", "********" });
        mUcxIniData.put("OKSV1330", new String[] {"OK133", "OKPR1330" });
        mUcxIniData.put("SISV1010", new String[] {"SI101", "SIPR1010" });
        mUcxIniData.put("SISV1020", new String[] {"SI102", "SIPR1020" });
        mUcxIniData.put("SISV1120", new String[] {"SI112", "SIPR1120" });
        mUcxIniData.put("SISV1130", new String[] {"SI113", "SIPR1130" });
        mUcxIniData.put("SISV1140", new String[] {"SI114", "SIPR1140" });
        mUcxIniData.put("SISV1150", new String[] {"SI115", "SIPR1150" });
        mUcxIniData.put("SISV1180", new String[] {"SI118", "S725SNRT" });
        mUcxIniData.put("SISV1190", new String[] {"SI119", "S724IDOU" });
        mUcxIniData.put("SISV1210", new String[] {"SI121", "SIPR1210" });
        mUcxIniData.put("SISV1220", new String[] {"SI122", "SIPR1220" });
        mUcxIniData.put("SISV1230", new String[] {"SI123", "SIPR1230" });
        mUcxIniData.put("SISV1280", new String[] {"SI128", "SIPR1280" });
        mUcxIniData.put("SISV1290", new String[] {"SI129", "SIPR1290" });
        mUcxIniData.put("SISV1300", new String[] {"SI130", "SIPR1300" });
        mUcxIniData.put("SISV1310", new String[] {"SI131", "SIPR1310" });
        mUcxIniData.put("TKSV1010", new String[] {"TK101", "T501KSHY" });
        mUcxIniData.put("TKSV1020", new String[] {"TK102", "T502KSHK" });
        mUcxIniData.put("TKSV1030", new String[] {"TK103", "T503TKSR" });
        mUcxIniData.put("TKSV1040", new String[] {"TK104", "T504KSMY" });
        mUcxIniData.put("TKSV1050", new String[] {"TK105", "T505KSMK" });
        mUcxIniData.put("TKSV1060", new String[] {"TK106", "T506KKKJ" });
        mUcxIniData.put("TKSV1070", new String[] {"TK107", "T507OKKJ" });
        mUcxIniData.put("TKSV1080", new String[] {"TK108", "T500KKER" });
        mUcxIniData.put("TKSV1090", new String[] {"TK109", "TKPR1090" });
        mUcxIniData.put("TKSV1110", new String[] {"TK111", "TKPR1110" });
        mUcxIniData.put("TKSV1120", new String[] {"TK112", "TKPR1120" });
        mUcxIniData.put("TKSV1130", new String[] {"TK113", "TKPR1130" });
        mUcxIniData.put("URSV1010", new String[] {"UR001", "URPR1010" });
        mUcxIniData.put("URSV1020", new String[] {"UR002", "URPR1020" });
        mUcxIniData.put("URSV1030", new String[] {"UR003", "URPR1030" });
        mUcxIniData.put("URSV1040", new String[] {"UR004", "URPR1040" });
        mUcxIniData.put("URSV1050", new String[] {"UR005", "URPR1050" });
        mUcxIniData.put("URSV1060", new String[] {"UR006", "URPR1060" });
        mUcxIniData.put("URSV1070", new String[] {"UR007", "URPR1070" });
        mUcxIniData.put("URSV1080", new String[] {"UR008", "URPR1080" });
        mUcxIniData.put("URSV1090", new String[] {"UR009", "URPR1090" });
        mUcxIniData.put("URSV1100", new String[] {"UR010", "URPR1100" });
        mUcxIniData.put("URSV1110", new String[] {"UR011", "URPR1110" });
        mUcxIniData.put("URSV1120", new String[] {"UR012", "URPR1120" });
        mUcxIniData.put("URSV1130", new String[] {"UR013", "URPR1130" });
        mUcxIniData.put("URSV1140", new String[] {"UR014", "URPR1140" });
        mUcxIniData.put("URSV1150", new String[] {"UR015", "URPR1150" });
        mUcxIniData.put("URSV1160", new String[] {"UR016", "URPR1160" });
        mUcxIniData.put("URSV1170", new String[] {"UR017", "URPR1170" });
        mUcxIniData.put("URSV1180", new String[] {"UR018", "URPR1180" });
        mUcxIniData.put("URSV1190", new String[] {"UR019", "URPR1190" });
        mUcxIniData.put("URSV1310", new String[] {"UR031", "URPR1310" });
        mUcxIniData.put("URSV1320", new String[] {"UR032", "URPR1320" });
        mUcxIniData.put("URSV1330", new String[] {"UR033", "URPR1330" });
        mUcxIniData.put("URSV1340", new String[] {"UR034", "URPR1340" });
        mUcxIniData.put("URSV1600", new String[] {"UR160", "URPR1600" });
        mUcxIniData.put("URSV1610", new String[] {"UR161", "URPR1610" });
        mUcxIniData.put("URSV7030", new String[] {"UR703", "URPR7030" });
        mUcxIniData.put("ZISV1010", new String[] {"ZI001", "ZIPR1010" });
        mUcxIniData.put("ZISV1020A", new String[] {"ZI002_1", "ZIPR1020_1" });
        mUcxIniData.put("ZISV1020B", new String[] {"ZI002_2", "ZIPR1020_2" });
        mUcxIniData.put("ZISV1040", new String[] {"ZI004", "ZIPR1040" });
        mUcxIniData.put("ZISV1050", new String[] {"ZI005", "ZIPR1050" });
        mUcxIniData.put("ZISV1060", new String[] {"ZI006", "ZIPR1060" });
        mUcxIniData.put("ZISV1070", new String[] {"ZI007", "ZIPR1070" });
        mUcxIniData.put("ZISV1100", new String[] {"ZI010", "ZIPR1100" });
        mUcxIniData.put("ZISV1120A", new String[] {"ZI120", "ZIPR1120" });
        mUcxIniData.put("ZISV1120B", new String[] {"ZI121", "ZIPR1121" });
        mUcxIniData.put("ZISV3010", new String[] {"ZIA01", "ZIPR3010" });
        mUcxIniData.put("ZISV7010", new String[] {"ZIA01", "ZIPR7010" });
        mUcxIniData.put("ZISV7040", new String[] {"ZIA01", "ZIPR7040" });
        mUcxIniData.put("ZISV7070", new String[] {"ZIA01", "ZIPR7070" });
        mUcxIniData.put("ZISV7000", new String[] {"ZI700", "ZIJP7000" });
        mUcxIniData.put("KKSV1310", new String[] {"KK131", "KKPR1310" });
        mUcxIniData.put("ZISV3020", new String[] {"ZIA02", "ZIPR3020" });
        mUcxIniData.put("SESV0110", new String[] {"SE011", "SEPR0110" });
        mUcxIniData.put("SESV0210", new String[] {"SE021", "SEPR0210" });
        mUcxIniData.put("SESV0330", new String[] {"SE033", "SEPR0330" });
        mUcxIniData.put("SESV0360", new String[] {"SE036", "SEPR0360" });
        mUcxIniData.put("HASV7000", new String[] {"HA700", "HAPR7000" });
        mUcxIniData.put("HASV7040", new String[] {"HA704", "HAPR7040" });
        mUcxIniData.put("SISV7060", new String[] {"SI706", "SIPR7060" });
        mUcxIniData.put("SISV7040", new String[] {"SI704", "SIPR7040" });
        mUcxIniData.put("SISV7050", new String[] {"SI705", "SIPR7050" });
        mUcxIniData.put("SISV7110", new String[] {"SI711", "SIPR7110" });
        mUcxIniData.put("SISV7090", new String[] {"SI709", "SIPR7090" });
        mUcxIniData.put("SISV7000", new String[] {"SI700", "SIPR7000" });
        mUcxIniData.put("SISV7020", new String[] {"SI720", "SIPR7020" });
        mUcxIniData.put("SISV7100", new String[] {"SI710", "SIPR7100" });
        mUcxIniData.put("GNSV7030", new String[] {"GN730", "GNPR7030" });
        mUcxIniData.put("GNSV7000", new String[] {"GN700", "GNPR7000" });
        
        
    }
}
