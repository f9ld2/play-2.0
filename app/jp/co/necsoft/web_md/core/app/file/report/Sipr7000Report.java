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
public class Sipr7000Report {
    @CsvColumn(name = "H00_PROGID")
    public String h00_progid;
    @CsvColumn(name = "H00_VERSION")
    public String h00_version;
    @CsvColumn(name = "H01_TANTO_CODE")
    public String h01_tanto_code;
    @CsvColumn(name = "H01_TEN_CD")
    public String h01_ten_cd;
    @CsvColumn(name = "H01_TEN_NAME")
    public String h01_ten_name;
    @CsvColumn(name = "H01_NHN_YOTEI_YMD_FR")
    public String h01_nhn_yotei_ymd_fr;
    @CsvColumn(name = "H01_NHN_YOTEI_YMD_TO")
    public String h01_nhn_yotei_ymd_to;
    @CsvColumn(name = "H01_TORIHIKI_CD")
    public String h01_torihiki_cd;
    @CsvColumn(name = "H01_TRI_NM")
    public String h01_tri_nm;
    @CsvColumn(name = "M01_CBNRCD")
    public String m01_cbnrCd;
    @CsvColumn(name = "M01_BNRNM")
    public String m01_bnrNm;

    @CsvColumn(name = "M01_HAT_BARA_SU1")
    public String m01_hat_bara_su1;
    @CsvColumn(name = "M01_KEN_BARA_SU1")
    public String m01_ken_bara_su1;
    @CsvColumn(name = "M01_HAT_GENK_KIN1")
    public String m01_hat_genk_kin1;
    @CsvColumn(name = "M01_KEN_GENK_KIN1")
    public String m01_ken_genk_kin1;
    @CsvColumn(name = "M01_HAT_BAIK_KIN1")
    public String m01_hat_baik_kin1;
    @CsvColumn(name = "M01_KEN_BAIK_KIN1")
    public String m01_ken_baik_kin1;
    
    @CsvColumn(name = "M01_HAT_BARA_SU2")
    public String m01_hat_bara_su2;
    @CsvColumn(name = "M01_KEN_BARA_SU2")
    public String m01_ken_bara_su2;
    @CsvColumn(name = "M01_HAT_GENK_KIN2")
    public String m01_hat_genk_kin2;
    @CsvColumn(name = "M01_KEN_GENK_KIN2")
    public String m01_ken_genk_kin2;
    @CsvColumn(name = "M01_HAT_BAIK_KIN2")
    public String m01_hat_baik_kin2;
    @CsvColumn(name = "M01_KEN_BAIK_KIN2")
    public String m01_ken_baik_kin2;
}
