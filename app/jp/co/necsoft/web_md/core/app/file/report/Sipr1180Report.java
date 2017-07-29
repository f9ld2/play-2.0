///////////////////////////////////////////////////////////////////////
// Copyright(C) 2015 NEC Solution Innovators, Ltd. All Rights Reserved.
///////////////////////////////////////////////////////////////////////

/* ====================================================================
 * 機能名称 : 仕入先別納品率ﾘｽﾄ.
 * 改版履歴
 * Rev. 改版年月日   改版者名       内容
 * 1.0   2014.04.17   TUANVT      新規作成
 * =================================================================== */

package jp.co.necsoft.web_md.core.app.file.report;

import com.orangesignal.csv.annotation.CsvColumn;
import com.orangesignal.csv.annotation.CsvEntity;

@CsvEntity
public class Sipr1180Report {
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
    
    @CsvColumn(name = "M01_TORI_CD")
    public String m01_tori_cd;
    
    @CsvColumn(name = "M01_TORI_NM")
    public String m01_tori_nm;
    
    @CsvColumn(name = "M01_TO_HAT")
    public String m01_to_hat;
    
    @CsvColumn(name = "M01_TO_NOU")
    public String m01_to_nou;
    
    @CsvColumn(name = "M01_TO_RITU")
    public String m01_to_ritu;
    
    @CsvColumn(name = "M01_RUI_HAT")
    public String m01_rui_hat;
    
    @CsvColumn(name = "M01_RUI_NOU")
    public String m01_rui_nou;
    
    @CsvColumn(name = "M01_RUI_RITU")
    public String m01_rui_ritu;
}