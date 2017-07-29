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
public class Gnpr7030Report {
    @CsvColumn(name = "H00_PROGID")
    public String h00_progid;
    @CsvColumn(name = "H00_VERSION")
    public String h00_version;
    @CsvColumn(name = "H01_TANTO_CODE")
    public String h01_tanto_code;
    @CsvColumn(name = "H01_JIGYOBU_CD")
    public String h01_jigyobu_cd;
    @CsvColumn(name = "H01_JIGYOBU_NM")
    public String h01_jigyobu_nm;
    @CsvColumn(name = "H01_TEN_CD")
    public String h01_ten_cd;
    @CsvColumn(name = "H01_TEN_NM")
    public String h01_ten_nm;
    @CsvColumn(name = "H01_BMN_CD")
    public String h01_bmn_cd;
    @CsvColumn(name = "H01_BMN_NM")
    public String h01_bmn_nm;
    @CsvColumn(name = "H01_CHU_BNR_CD")
    public String h01_chu_bnr_cd;
    @CsvColumn(name = "H01_CHU_BNR_NM")
    public String h01_chu_bnr_nm;
    @CsvColumn(name = "H01_SHO_BNR_CD")
    public String h01_sho_bnr_cd;
    @CsvColumn(name = "H01_SHO_BNR_NM")
    public String h01_sho_bnr_nm;
    @CsvColumn(name = "M00_DEMO_TANA_NO")
    public String m00_demo_tana_no;
    @CsvColumn(name = "M00_DEMO_TANA_DAN")
    public String m00_demo_tana_dan;
    @CsvColumn(name = "M00_DEMO_TANA_SEQ")
    public String m00_demo_tana_seq;
    @CsvColumn(name = "M00_UPD_KBN_NM")
    public String m00_upd_kbn_nm;
    @CsvColumn(name = "M00_JAN_CD")
    public String m00_jan_cd;
    @CsvColumn(name = "M00_SHN_NM")
    public String m00_shn_nm;
    @CsvColumn(name = "M00_KIKAKU_NM")
    public String m00_kikaku_nm;
    @CsvColumn(name = "M00_TRI_NM")
    public String m00_tri_nm;
    @CsvColumn(name = "M00_RRN_SU")
    public String m00_rrn_su;
    @CsvColumn(name = "M00_DEMO_SIJI_SU")
    public String m00_demo_siji_su;
    @CsvColumn(name = "M00_BAIK")
    public String m00_baik;
    @CsvColumn(name = "M00_GENK")
    public String m00_genk;
    @CsvColumn(name = "M00_BAIK_PRICE_UNIT")
    public String m00_baik_price_unit;
    @CsvColumn(name = "M00_GENK_PRICE_UNIT")
    public String m00_genk_price_unit;
    @CsvColumn(name = "M00_JAN_CD_BCODE")
    public String m00_jan_cd_bcode;
    @CsvColumn(name = "M00_DEMO_TANA_NO_PREV")
    public String m00_demo_tana_no_prev;
    @CsvColumn(name = "M00_DEMO_TANA_DAN_PREV")
    public String m00_demo_tana_dan_prev;
    @CsvColumn(name = "M00_DEMO_TANA_SEQ_PREV")
    public String m00_demo_tana_seq_prev;
}
