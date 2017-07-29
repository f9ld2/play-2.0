// /////////////////////////////////////////////////////////////////////
// Copyright(C) 2015 NEC Solution Innovators, Ltd. All Rights Reserved.
// /////////////////////////////////////////////////////////////////////

/*
 * ====================================================================
 * 
 * 機能名称 : ＴＡ伝票出力
 * 
 * 
 * 改版履歴
 * 
 * Rev. 改版年月日 改版者名 内容
 * 
 * 1.0 2014-05-05 TUCTVZ 新規作成
 * 
 * ===================================================================
 */
package jp.co.necsoft.web_md.core.app.file.report;

import com.orangesignal.csv.annotation.CsvColumn;
import com.orangesignal.csv.annotation.CsvEntity;

/**
 * 
 *  Hapr1170Report
 *
 */
@CsvEntity
public class Hapr1170Report {
    @CsvColumn(name = "H00_PROGID")
    public String h00_progid;
    @CsvColumn(name = "H00_VERSION")
    public String h00_version;
    @CsvColumn(name = "H01_TANTO_CODE")
    public String h01_tanto_code;
    @CsvColumn(name = "H01_D2")
    public String h01_d2;
    @CsvColumn(name = "H01_SHA_NM")
    public String h01_sha_nm;
    @CsvColumn(name = "H01_TRI_NM")
    public String h01_tri_nm;
    @CsvColumn(name = "H01_TEN_NM")
    public String h01_ten_nm;
    @CsvColumn(name = "H01_JIGYO_CD")
    public String h01_jigyo_cd;
    @CsvColumn(name = "H01_TEN_CD")
    public String h01_ten_cd;
    @CsvColumn(name = "H01_BMN_CD")
    public String h01_bmn_cd;
    @CsvColumn(name = "H01_DPY_KBN")
    public String h01_dpy_kbn;
    @CsvColumn(name = "H01_DPY_NO")
    public String h01_dpy_no;
    @CsvColumn(name = "H01_TRI_CD")
    public String h01_tri_cd;
    @CsvColumn(name = "H01_HAT_DD")
    public String h01_hat_dd;
    @CsvColumn(name = "H01_NHN_DD")
    public String h01_nhn_dd;
    @CsvColumn(name = "H01_BIN")
    public String h01_bin;
    @CsvColumn(name = "M01_SHN_NM_1")
    public String m01_shn_nm_1;
    @CsvColumn(name = "M01_SHN_NM_2")
    public String m01_shn_nm_2;
    @CsvColumn(name = "M01_SHN_NM_3")
    public String m01_shn_nm_3;
    @CsvColumn(name = "M01_SHUSEI_GENK")
    public String m01_shusei_genk;
    @CsvColumn(name = "M01_SHN_CD")
    public String m01_shn_cd;
    @CsvColumn(name = "M01_IRI_SU_1")
    public String m01_iri_su_1;
    @CsvColumn(name = "M01_IRI_SU_2")
    public String m01_iri_su_2;
    @CsvColumn(name = "M01_CASE_1")
    public String m01_case_1;
    @CsvColumn(name = "M01_CASE_2")
    public String m01_case_2;
    @CsvColumn(name = "M01_TANI")
    public String m01_tani;
    @CsvColumn(name = "M01_HAT_SU")
    public String m01_hat_su;
    @CsvColumn(name = "M01_GENK")
    public String m01_genk;
    @CsvColumn(name = "M01_GENK_KIN")
    public String m01_genk_kin;
    @CsvColumn(name = "M01_BAIK")
    public String m01_baik;
    @CsvColumn(name = "M01_BAIK_KIN")
    public String m01_baik_kin;
    @CsvColumn(name = "M01_KEN_GENK")
    public String m01_ken_genk;
    @CsvColumn(name = "M01_GYOU")
    public String m01_gyou;

}
