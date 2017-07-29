// /////////////////////////////////////////////////////////////////////
// Copyright(C) 2015 NEC Solution Innovators, Ltd. All Rights Reserved.
// /////////////////////////////////////////////////////////////////////

/*
 * ==================================================================== 機能名称 : 仕入状況一覧表 改版履歴 Rev. 改版年月日 改版者名 内容 1.0
 * 2014.16.04 NECVN 新規作成 ===================================================================
 */

package jp.co.necsoft.web_md.core.app.file.report;

import com.orangesignal.csv.annotation.CsvColumn;
import com.orangesignal.csv.annotation.CsvEntity;

/**
 * 仕入状況一覧表のReportクラス
 * @author necvn
 */
@CsvEntity
public class Sipr7100Report {
    @CsvColumn(name = "H00_PROGID")
    public String h00_progid;
    @CsvColumn(name = "H00_VERSION")
    public String h00_version;
    @CsvColumn(name = "H01_TANTO_CODE")
    public String h01_tanto_code;

    @CsvColumn(name = "H01_JGY_CD")
    public String h01_jgy_cd;
    @CsvColumn(name = "H01_JGY_NM")
    public String h01_jgy_nm;
    @CsvColumn(name = "H01_TEN_CD")
    public String h01_ten_cd;
    @CsvColumn(name = "H01_TEN_NM")
    public String h01_ten_nm;
    @CsvColumn(name = "H01_TRI_CD")
    public String h01_tri_cd;
    @CsvColumn(name = "H01_TRI_NM")
    public String h01_tri_nm;
    @CsvColumn(name = "H01_TAISYO_YM")
    public String h01_taisyo_ym;
    @CsvColumn(name = "M01_JAN_CD")
    public String m01_jan_cd;
    @CsvColumn(name = "M01_SHN_NM")
    public String m01_shn_nm;
    @CsvColumn(name = "M01_GENK")
    public String m01_genk;
    @CsvColumn(name = "M01_BAIK")
    public String m01_baik;
    @CsvColumn(name = "M01_GESHO_ZAIKO")
    public String m01_gesho_zaiko;
    @CsvColumn(name = "M01_SIR_SU")
    public String m01_sir_su;
    @CsvColumn(name = "M01_URI_TENSU")
    public String m01_uri_tensu;
    @CsvColumn(name = "M01_FUMEI_SU")
    public String m01_fumei_su;
    @CsvColumn(name = "M01_TNA_SU")
    public String m01_tna_su;
    @CsvColumn(name = "M01_SEISAN_SU")
    public String m01_seisan_su;
    @CsvColumn(name = "M01_SEISAN_KIN")
    public String m01_seisan_kin;
}
