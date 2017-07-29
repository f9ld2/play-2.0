// /////////////////////////////////////////////////////////////////////
// Copyright(C) 2015 NEC Solution Innovators, Ltd. All Rights Reserved.
// /////////////////////////////////////////////////////////////////////

/*
 * ====================================================================
 * 
 * 機能名称 : 日割予算リスト
 * 
 * 改版履歴
 * 
 * Rev. 改版年月日 改版者名 内容
 * 
 * 1.0 2014/05/14 Taivd 新規作成
 * 
 * ===================================================================
 */
package jp.co.necsoft.web_md.core.app.file.report;

import com.orangesignal.csv.annotation.CsvColumn;
import com.orangesignal.csv.annotation.CsvEntity;

@CsvEntity
public class Urpr1140Report {
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
    /** H02_NEN */
    @CsvColumn(name = "H02_NEN")
    public String h02_nen;
    /** H02_TUKI */
    @CsvColumn(name = "H02_TUKI")
    public String h02_tuki;
    /** H03_STR_CODE" */
    @CsvColumn(name = "H03_STR_CODE")
    public String h03_str_code;
    /** H03_STR_NAME */
    @CsvColumn(name = "H03_STR_NAME")
    public String h03_str_name;
    /** M01_DATE */
    @CsvColumn(name = "M01_DATE")
    public String m01_date;
    /** M01_WEEK */
    @CsvColumn(name = "M01_WEEK")
    public String m01_week;
    /** M00_BMN_CD_1 */
    @CsvColumn(name = "M00_BMN_CD_1")
    public String m00_bmn_cd_1;
    /** M01_YSN_1 */
    @CsvColumn(name = "M01_YSN_1")
    public String m01_ysn_1;
    /** M00_BMN_CD_2 */
    @CsvColumn(name = "M00_BMN_CD_2")
    public String m00_bmn_cd_2;
    /** M01_YSN_2 */
    @CsvColumn(name = "M01_YSN_2")
    public String m01_ysn_2;
    /** M00_BMN_CD_3 */
    @CsvColumn(name = "M00_BMN_CD_3")
    public String m00_bmn_cd_3;
    /** M01_YSN_3 */
    @CsvColumn(name = "M01_YSN_3")
    public String m01_ysn_3;
    /** M00_BMN_CD_4 */
    @CsvColumn(name = "M00_BMN_CD_4")
    public String m00_bmn_cd_4;
    /** M01_YSN_4 */
    @CsvColumn(name = "M01_YSN_4")
    public String m01_ysn_4;
    /** M00_BMN_CD_5 */
    @CsvColumn(name = "M00_BMN_CD_5")
    public String m00_bmn_cd_5;
    /** M01_YSN_5 */
    @CsvColumn(name = "M01_YSN_5")
    public String m01_ysn_5;
    /** M00_BMN_CD_6 */
    @CsvColumn(name = "M00_BMN_CD_6")
    public String m00_bmn_cd_6;
    /** M01_YSN_6 */
    @CsvColumn(name = "M01_YSN_6")
    public String m01_ysn_6;
    /** M00_BMN_CD_7 */
    @CsvColumn(name = "M00_BMN_CD_7")
    public String m00_bmn_cd_7;
    /** M01_YSN_7 */
    @CsvColumn(name = "M01_YSN_7")
    public String m01_ysn_7;
    /** M00_BMN_CD_8 */
    @CsvColumn(name = "M00_BMN_CD_8")
    public String m00_bmn_cd_8;
    /** M01_YSN_8 */
    @CsvColumn(name = "M01_YSN_8")
    public String m01_ysn_8;
    /** M00_BMN_CD_9 */
    @CsvColumn(name = "M00_BMN_CD_9")
    public String m00_bmn_cd_9;
    /** M01_YSN_9 */
    @CsvColumn(name = "M01_YSN_9")
    public String m01_ysn_9;
    /** M00_BMN_CD_10 */
    @CsvColumn(name = "M00_BMN_CD_10")
    public String m00_bmn_cd_10;
    /** M01_YSN_10 */
    @CsvColumn(name = "M01_YSN_10")
    public String m01_ysn_10;
    /** M00_BMN_CD_11 */
    @CsvColumn(name = "M00_BMN_CD_11")
    public String m00_bmn_cd_11;
    /** M01_YSN_11 */
    @CsvColumn(name = "M01_YSN_11")
    public String m01_ysn_11;
    /** M00_BMN_CD_12 */
    @CsvColumn(name = "M00_BMN_CD_12")
    public String m00_bmn_cd_12;
    /** M01_YSN_12 */
    @CsvColumn(name = "M01_YSN_12")
    public String m01_ysn_12;
    /** M00_BMN_CD_13 */
    @CsvColumn(name = "M00_BMN_CD_13")
    public String m00_bmn_cd_13;
    /** M01_YSN_13 */
    @CsvColumn(name = "M01_YSN_13")
    public String m01_ysn_13;
    /** M00_BMN_CD_14 */
    @CsvColumn(name = "M00_BMN_CD_14")
    public String m00_bmn_cd_14;
    /** M01_YSN_14 */
    @CsvColumn(name = "M01_YSN_14")
    public String m01_ysn_14;
    /** M00_BMN_CD_15 */
    @CsvColumn(name = "M00_BMN_CD_15")
    public String m00_bmn_cd_15;
    /** M01_YSN_15 */
    @CsvColumn(name = "M01_YSN_15")
    public String m01_ysn_15;
    /** M01_YSN_SUM */
    @CsvColumn(name = "M01_YSN_SUM")
    public String m01_ysn_sum;
    /** M01_KYAKU */
    @CsvColumn(name = "M01_KYAKU")
    public String m01_kyaku;
    /** M01_LAST */
    @CsvColumn(name = "M01_LAST")
    public String m01_last;

}
