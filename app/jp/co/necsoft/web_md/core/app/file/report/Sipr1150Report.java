// /////////////////////////////////////////////////////////////////////
// Copyright(C) 2015 NEC Solution Innovators, Ltd. All Rights Reserved.
// /////////////////////////////////////////////////////////////////////

/*
 * ==================================================================== 機能名称 : 酒類元帳. 改版履歴 Rev. 改版年月日 改版者名 内容 1.0
 * 2014.04.17 TUANVT 新規作成 ===================================================================
 */

package jp.co.necsoft.web_md.core.app.file.report;

import com.orangesignal.csv.annotation.CsvColumn;
import com.orangesignal.csv.annotation.CsvEntity;

@CsvEntity
public class Sipr1150Report {
    @CsvColumn(name = "H00_PROGID")
    public String h00_progid;

    @CsvColumn(name = "H00_VERSION")
    public String h00_version;

    @CsvColumn(name = "H00_SDATE")
    public String h00_sdate;

    @CsvColumn(name = "H00_STIME")
    public String h00_stime;

    @CsvColumn(name = "H00_PAGE")
    public String h00_page;

    @CsvColumn(name = "H01_TANTO_CODE")
    public String h01_tanto_code;

    @CsvColumn(name = "H01_KAISYA_CODE")
    public String h01_kaisya_code;

    @CsvColumn(name = "H01_KAISYA_NAME")
    public String h01_kaisya_name;

    @CsvColumn(name = "H01_JIGYOBU_CODE")
    public String h01_jigyobu_code;

    @CsvColumn(name = "H01_JIGYOBU_NAME")
    public String h01_jigyobu_name;

    @CsvColumn(name = "H01_TEN_CD")
    public String h01_ten_cd;

    @CsvColumn(name = "H01_TEN_NAME")
    public String h01_ten_name;

    @CsvColumn(name = "H01_BMN_CD")
    public String h01_bmn_cd;

    @CsvColumn(name = "H01_BMN_NAME")
    public String h01_bmn_name;

    @CsvColumn(name = "H01_DENKBN")
    public String h01_denkbn;

    @CsvColumn(name = "H01_DENKBN_NAME")
    public String h01_denkbn_name;

    @CsvColumn(name = "H01_TEN_CD_HD")
    public String h01_ten_cd_hd;

    @CsvColumn(name = "H01_BMN_CD_HD")
    public String h01_bmn_cd_hd;

    @CsvColumn(name = "H02_SIR_CD")
    public String h02_sir_cd;

    @CsvColumn(name = "H02_SIR_NAME")
    public String h02_sir_name;

    @CsvColumn(name = "M01_HAT_DATE")
    public String m01_hat_date;

    @CsvColumn(name = "M01_DENNO")
    public String m01_denno;

    @CsvColumn(name = "M01_SIR_CD")
    public String m01_sir_cd;

    @CsvColumn(name = "M01_SIR_NAME")
    public String m01_sir_name;

    @CsvColumn(name = "M01_NHN_DATE")
    public String m01_nhn_date;

    @CsvColumn(name = "M01_GEN_KINGK")
    public String m01_gen_kingk;

    @CsvColumn(name = "M01_BAI_KINGK")
    public String m01_bai_kingk;

    @CsvColumn(name = "H01_DENKBN_HD")
    public String h01_denkbn_hd;

    @CsvColumn(name = "M01_PAGE_CNG")
    public String m01_page_cng;

}
