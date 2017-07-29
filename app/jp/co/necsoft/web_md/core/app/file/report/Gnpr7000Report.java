///////////////////////////////////////////////////////////////////////
// Copyright(C) 2015 NEC Solution Innovators, Ltd. All Rights Reserved.
///////////////////////////////////////////////////////////////////////

/* ====================================================================
 * 機能名称 : セット品設定リスト
 * 改版履歴
 * Rev. 改版年月日   改版者名       内容
 * 1.0   2015-06-25 chiennt 新規作成
 * =================================================================== */
package jp.co.necsoft.web_md.core.app.file.report;

import com.orangesignal.csv.annotation.CsvColumn;
import com.orangesignal.csv.annotation.CsvEntity;

@CsvEntity
public class Gnpr7000Report {
    @CsvColumn(name = "H00_PROGID")
    public String h00_progid;
    @CsvColumn(name = "H00_VERSION")
    public String h00_version;
    @CsvColumn(name = "H01_TANTO_CODE")
    public String h01_tanto_code;
    @CsvColumn(name = "H01_HAKKO_DAY")
    public String m00_hakko_day;
    @CsvColumn(name = "M00_JAN_CD")
    public String m00_jan_cd;
    @CsvColumn(name = "M00_SHN_NM")
    public String m00_shn_nm;
    @CsvColumn(name = "M00_STD_GENK")
    public String m00_std_genk;
    @CsvColumn(name = "M00_STD_BAIK")
    public String m00_std_baik;
    @CsvColumn(name = "M00_GENK_RATE")
    public String m00_genk_rate;
    @CsvColumn(name = "M01_HAKKO_DAY")
    public String m01_hakko_day;
    @CsvColumn(name = "M01_JAN_CD")
    public String m01_jan_cd;
    @CsvColumn(name = "M01_SHN_NM")
    public String m01_shn_nm;
    @CsvColumn(name = "M01_SURYO")
    public String m01_suryo;
    @CsvColumn(name = "M01_STD_GENK")
    public String m01_std_genk;
    @CsvColumn(name = "M01_STD_BAIK")
    public String m01_std_baik;
    @CsvColumn(name = "M01_MODEL_TRI_NM")
    public String m01_model_tri_nm;
    @CsvColumn(name = "M01_MAKER_HIN_CD")
    public String m01_maker_hin_cd;
}
