///////////////////////////////////////////////////////////////////////
// Copyright(C) 2015 NEC Solution Innovators, Ltd. All Rights Reserved.
///////////////////////////////////////////////////////////////////////

/* ====================================================================
 * 機能名称 : 移動実績リスト
 * 改版履歴
 * Rev. 改版年月日   改版者名       内容
 * 1.0   2014.04.18   TUANVT      新規作成
 * =================================================================== */

package jp.co.necsoft.web_md.core.app.file.report;

import com.orangesignal.csv.annotation.CsvColumn;
import com.orangesignal.csv.annotation.CsvEntity;

@CsvEntity
public class Sipr1190Report {
    @CsvColumn(name = "H00_PROGID")
    public String h00_progid;

    @CsvColumn(name = "H00_VERSION")
    public String h00_version;
    
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
    
    @CsvColumn(name = "H01_YM")
    public String h01_ym;
    
    @CsvColumn(name = "M01_IN_TEN_CD")
    public String m01_in_ten_cd;
    
    @CsvColumn(name = "M01_IN_TEN_NM")
    public String m01_in_ten_nm;
    
    @CsvColumn(name = "M01_IN_BMN_CD")
    public String m01_in_bmn_cd;
    
    @CsvColumn(name = "M01_IN_BMN_NM")
    public String m01_in_bmn_nm;
    
    @CsvColumn(name = "M01_OUT_TEN_CD")
    public String m01_out_ten_cd;
    
    @CsvColumn(name = "M01_OUT_TEN_NM")
    public String m01_out_ten_nm;
    
    @CsvColumn(name = "M01_OUT_BMN_CD")
    public String m01_out_bmn_cd;
    
    @CsvColumn(name = "M01_OUT_BMN_NM")
    public String m01_out_bmn_nm;
    
    @CsvColumn(name = "M01_IDO_DATE")
    public String m01_ido_date;
    
    @CsvColumn(name = "M01_GEN_KINGAK")
    public String m01_gen_kingak;
    
    @CsvColumn(name = "M01_BAI_KINGAK")
    public String m01_bai_kingak;
    
    @CsvColumn(name = "M01_GEN_ALL")
    public String m01_gen_all;
    
    @CsvColumn(name = "M01_BAI_ALL")
    public String m01_bai_all;
}
