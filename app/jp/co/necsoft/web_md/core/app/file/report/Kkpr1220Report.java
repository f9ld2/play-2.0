///////////////////////////////////////////////////////////////////////
// Copyright(C) 2015 NEC Solution Innovators, Ltd. All Rights Reserved.
///////////////////////////////////////////////////////////////////////

/* ====================================================================
 * 機能名称 : 買掛金支払チェックリスト出力
 * 改版履歴
 * Rev. 改版年月日   改版者名       内容
 * 1.0   2014.04.05   VuQQ      新規作成
 * =================================================================== */
package jp.co.necsoft.web_md.core.app.file.report;

import com.orangesignal.csv.annotation.CsvColumn;
import com.orangesignal.csv.annotation.CsvEntity;

@CsvEntity
public class Kkpr1220Report {
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

    @CsvColumn(name = "H01_BUFFER_01")
    public String h01_buffer_01;

    @CsvColumn(name = "H01_BUFFER_02")
    public String h01_buffer_02;

    @CsvColumn(name = "M01_SYORI_NO")
    public String m01_syori_no;

    @CsvColumn(name = "M01_SHR_DATE")
    public String m01_shr_date;

    @CsvColumn(name = "M01_SIR_CD")
    public String m01_sir_cd;

    @CsvColumn(name = "M01_SIR_NAME")
    public String m01_sir_name;

    @CsvColumn(name = "M01_KOMOKU")
    public String m01_komoku;

    @CsvColumn(name = "M01_TEKIYO")
    public String m01_tekiyo;

    @CsvColumn(name = "M01_KINGK")
    public String m01_kingk;

    @CsvColumn(name = "M01_STAX")
    public String m01_stax;

    @CsvColumn(name = "M01_GOKEI")
    public String m01_gokei;

}
