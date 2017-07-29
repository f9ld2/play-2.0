///////////////////////////////////////////////////////////////////////
// Copyright(C) 2015 NEC Solution Innovators, Ltd. All Rights Reserved.
///////////////////////////////////////////////////////////////////////

/* ====================================================================
 * 機能名称 : 修理品明細一覧
 * 改版履歴
 * Rev. 改版年月日   改版者名       内容
 * 1.0   2015-06-23 chiennt 新規作成
 * =================================================================== */
package jp.co.necsoft.web_md.core.app.file.report;

import com.orangesignal.csv.annotation.CsvColumn;
import com.orangesignal.csv.annotation.CsvEntity;

@CsvEntity
public class Urpr7030Report {
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
    @CsvColumn(name = "H01_DEN_KBN")
    public String h01_den_kbn;
    @CsvColumn(name = "H01_DEN_KBN_NM")
    public String h01_den_kbn_nm;
    @CsvColumn(name = "DNP_SUB_KBN")
    public String h01_dnp_sub_kbn;
    @CsvColumn(name = "DNP_SUB_KBN_NM")
    public String h01_dnp_sub_kbn_nm;
    @CsvColumn(name = "M00_NHN_YMD")
    public String m00_nhn_ymd;
    @CsvColumn(name = "M00_DPY_NO")
    public String m00_dpy_no;
    @CsvColumn(name = "M00_TEKIYO")
    public String m00_tekiyo;
    @CsvColumn(name = "M00_TORIHIKI_CD")
    public String m00_torihiki_cd;
    @CsvColumn(name = "M00_TRI_NM")
    public String m00_tri_nm;
    @CsvColumn(name = "M00_SHN_CD")
    public String m00_shn_cd;
    @CsvColumn(name = "M00_SHN_NM")
    public String m00_shn_nm;
    @CsvColumn(name = "M00_KEN_GENK_KIN")
    public String m00_ken_genk_kin;
    @CsvColumn(name = "M00_KEN_BAIK_KIN")
    public String m00_ken_baik_kin;

}
