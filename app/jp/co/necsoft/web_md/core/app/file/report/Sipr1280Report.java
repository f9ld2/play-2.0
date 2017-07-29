// /////////////////////////////////////////////////////////////////////
// Copyright(C) 2015 NEC Solution Innovators, Ltd. All Rights Reserved.
// /////////////////////////////////////////////////////////////////////

/*
 * ====================================================================
 * 
 * 機能名称 :店間移動リスト
 * 
 * 改版履歴
 * 
 * Rev. 改版年月日 改版者名 内容
 * 
 * 1.0 20140505 Tinnc 新規作成
 * 
 * ===================================================================
 */
package jp.co.necsoft.web_md.core.app.file.report;

import com.orangesignal.csv.annotation.CsvColumn;
import com.orangesignal.csv.annotation.CsvEntity;

@CsvEntity
public class Sipr1280Report {
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
    @CsvColumn(name = "H01_TITLE_NAME")
    public String h01_title_name;
    @CsvColumn(name = "H01_OUT_TEN_CODE")
    public String h01_out_ten_code;
    @CsvColumn(name = "H01_OUT_TEN_NAME")
    public String h01_out_ten_name;
    @CsvColumn(name = "H01_IN_TEN_CODE")
    public String h01_in_ten_code;
    @CsvColumn(name = "H01_IN_TEN_NAME")
    public String h01_in_ten_name;
    @CsvColumn(name = "M01_BMN_CODE")
    public String m01_bmn_code;
    @CsvColumn(name = "M01_BREAK_KEY")
    public String m01_break_key;
    @CsvColumn(name = "M01_DEN_NO")
    public String m01_den_no;
    @CsvColumn(name = "M01_SYUKA_DATE")
    public String m01_syuka_date;
    @CsvColumn(name = "M01_MEISAI_NO")
    public String m01_meisai_no;
    @CsvColumn(name = "M01_SHN_CODE")
    public String m01_shn_code;
    @CsvColumn(name = "M01_SHN_NAME")
    public String m01_shn_name;
    @CsvColumn(name = "M01_KEN_BARA_SU")
    public String m01_ken_bara_su;
    @CsvColumn(name = "M01_KEN_GENK")
    public String m01_ken_genk;
    @CsvColumn(name = "M01_KEN_GENK_KIN")
    public String m01_ken_genk_kin;
    @CsvColumn(name = "M01_KEN_BAIK")
    public String m01_ken_baik;
    @CsvColumn(name = "M01_KEN_BAIK_KIN")
    public String m01_ken_baik_kin;

}
