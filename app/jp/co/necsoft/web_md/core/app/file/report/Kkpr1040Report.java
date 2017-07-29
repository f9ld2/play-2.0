///////////////////////////////////////////////////////////////////////
// Copyright(C) 2015 NEC Solution Innovators, Ltd. All Rights Reserved.
///////////////////////////////////////////////////////////////////////

/* ====================================================================
 * 機能名称 : 買掛金残高一覧表
 * 改版履歴
 * Rev. 改版年月日   改版者名       内容
 * 1.0   2014.04.01   TUANVT      新規作成
 * =================================================================== */

package jp.co.necsoft.web_md.core.app.file.report;

import com.orangesignal.csv.annotation.CsvColumn;
import com.orangesignal.csv.annotation.CsvEntity;

@CsvEntity
public class Kkpr1040Report {

    /** プログラムＩＤ **/
    @CsvColumn(name = "H00_PROGID")
    public String h00_progid;

    /** バージョン **/
    @CsvColumn(name = "H00_VERSION")
    public String h00_version;

    /** システム日付 **/
    @CsvColumn(name = "H00_SDATE")
    public String h00_sdate;

    /** システム時間 **/
    @CsvColumn(name = "H00_STIME")
    public String h00_stime;

    /** ページ番号 **/
    @CsvColumn(name = "H00_PAGE")
    public String h00_page;

    /** 担当者コード **/
    @CsvColumn(name = "H01_TANTO_CODE")
    public String h01_tanto_code;

    /** 会社コード **/
    @CsvColumn(name = "H01_KAISYA_CODE")
    public String h01_kaisya_code;

    /** 会社名 **/
    @CsvColumn(name = "H01_KAISYA_NAME")
    public String h01_kaisya_name;

    /** 予備01 **/
    @CsvColumn(name = "H01_BUFFER_01")
    public String h01_buffer_01;

    /** 予備02 **/
    @CsvColumn(name = "H01_BUFFER_02")
    public String h01_buffer_02;

    /** タイトル **/
    @CsvColumn(name = "H01_TITLE")
    public String h01_title;

    /** 期間（FROM） **/
    @CsvColumn(name = "H02_KIKAN_FROM")
    public String h02_kikan_from;

    /** 期間（TO） **/
    @CsvColumn(name = "H02_KIKAN_TO")
    public String h02_kikan_to;

    /** 支払（買掛精算区分） **/
    @CsvColumn(name = "M01_SIHARAI")
    public String m01_siharai;

    /** 支払方法 **/
    @CsvColumn(name = "M01_SIR_HOHO")
    public String m01_sir_hoho;

    /** 取引先コード **/
    @CsvColumn(name = "M01_SIR_CD")
    public String m01_sir_cd;

    /** 照合区分 **/
    @CsvColumn(name = "M01_SYOGO_KBN")
    public String m01_syogo_kbn;

    /** 取引先名 **/
    @CsvColumn(name = "M01_SIR_NAME")
    public String m01_sir_name;

    /** 前回繰越高 **/
    @CsvColumn(name = "M01_ZEN_ZAN")
    public String m01_zen_zan;

    /** 仕入金額 **/
    @CsvColumn(name = "M01_SIR_KINGK")
    public String m01_sir_kingk;

    /** 返品金額 **/
    @CsvColumn(name = "M01_HPN_KINGK")
    public String m01_hpn_kingk;

    /** 返品金額 **/
    @CsvColumn(name = "M01_NBK_KINGK")
    public String m01_nbk_kingk;

    /** 純仕入 **/
    @CsvColumn(name = "M01_JUN_SIR")
    public String m01_jun_sir;

    /** 支払金額 **/
    @CsvColumn(name = "M01_SHR_KINGK")
    public String m01_shr_kingk;

    /** 繰越残高 **/
    @CsvColumn(name = "M01_ZAN")
    public String m01_zan;

}
