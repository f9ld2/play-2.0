/**
 * 
 */
// /////////////////////////////////////////////////////////////////////
// Copyright(C) 2015 NEC Solution Innovators, Ltd. All Rights Reserved.
// /////////////////////////////////////////////////////////////////////

/*
 * ====================================================================
 * 
 * 機能名称 : 月次・日割予算チェックリスト
 * 
 * 改版履歴
 * 
 * Rev. 改版年月日 改版者名 内容
 * 
 * 1.0 2014.07.01 NES石井 新規作成
 * 
 * ===================================================================
 */
package jp.co.necsoft.web_md.core.app.file.report;

import com.orangesignal.csv.annotation.CsvColumn;
import com.orangesignal.csv.annotation.CsvEntity;

@CsvEntity
public class Urpr1180Report {
    /** プログラムＩＤ */
    @CsvColumn(name = "H00_PROGID")
    public String h00_progid;
    /** H00_VERSION */
    @CsvColumn(name = "H00_VERSION")
    public String h00_version;
    /** H01_TANTO_CODE */
    @CsvColumn(name = "H01_TANTO_CODE")
    public String h01_tanto_code;
    /** H01_KAISYA_CODE */
    @CsvColumn(name = "H01_KAISYA_CODE")
    public String h01_kaisya_code;
    /** H01_KAISYA_NAME */
    @CsvColumn(name = "H01_KAISYA_NAME")
    public String h01_kaisya_name;
    /** H02_JIGYOBU_CODE */
    @CsvColumn(name = "H02_JIGYOBU_CODE")
    public String h02_jigyobu_code;
    /** H02_JIGYOBU_NAME */
    @CsvColumn(name = "H02_JIGYOBU_NAME")
    public String h02_jigyobu_name;
    /** M00_YSN_YEAR */
    @CsvColumn(name = "M00_YSN_YEAR")
    public String m00_ysn_year;
    /** M00_YSN_MONTH */
    @CsvColumn(name = "M00_YSN_MONTH")
    public String m00_ysn_month;
    /** M00_STR_CODE */
    @CsvColumn(name = "M00_STR_CODE")
    public String m00_str_code;
    /** M00_STR_NAME */
    @CsvColumn(name = "M00_STR_NAME")
    public String m00_str_name;
    /** M00_YSN_URI_KIN_D */
    @CsvColumn(name = "M00_YSN_URI_KIN_D")
    public String m00_ysn_uri_kin_d;
    /** M00_YSN_URI_KIN_M */
    @CsvColumn(name = "M00_YSN_URI_KIN_M")
    public String m00_ysn_uri_kin_m;
    /** M00_YSN_KYAKU_D */
    @CsvColumn(name = "M00_YSN_KYAKU_D")
    public String m00_ysn_kyaku_d;
    /** M00_YSN_KYAKU_M */
    @CsvColumn(name = "M00_YSN_KYAKU_M")
    public String m00_ysn_kyaku_m;
    /** M00_YSN_URI_KIN_D_R */
    @CsvColumn(name = "M00_YSN_URI_KIN_D_R")
    public String m00_ysn_uri_kin_d_r;
    /** M00_YSN_URI_KIN_M_R */
    @CsvColumn(name = "M00_YSN_URI_KIN_M_R")
    public String m00_ysn_uri_kin_m_r;
}
