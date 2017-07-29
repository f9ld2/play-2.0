///////////////////////////////////////////////////////////////////////
// Copyright(C) 2015 NEC Solution Innovators, Ltd. All Rights Reserved.
///////////////////////////////////////////////////////////////////////

/* ====================================================================
 * 機能名称 : 店別納品明細書
 * 改版履歴
 * Rev. 改版年月日   改版者名       内容
 * 1.0   2014.04.11   VuQQ      新規作成
 * =================================================================== */
package jp.co.necsoft.web_md.core.app.file.report;

import com.orangesignal.csv.annotation.CsvColumn;
import com.orangesignal.csv.annotation.CsvEntity;

@CsvEntity
public class Tkpr1060Report {
    @CsvColumn(name = "H00_PROGID")
    public String h00_progid;
    
    @CsvColumn(name = "H00_VERSION")
    public String h00_version;
    
    @CsvColumn(name = "H01_TANTO_CODE")
    public String h01_tanto_code;
    
    @CsvColumn(name = "H01_KAISYA_CD")
    public String h01_kaisya_cd;
    
    @CsvColumn(name = "H01_KAISYA_NM")
    public String h01_kaisya_nm;
    
    @CsvColumn(name = "H01_JIGYOBU_CD")
    public String h01_jigyobu_cd;
    
    @CsvColumn(name = "H01_JIGYOBU_NM")
    public String h01_jigyobu_nm;
    
    @CsvColumn(name = "H01_BMN_CD")
    public String h01_bmn_cd;
    
    @CsvColumn(name = "H01_BMN_NM")
    public String h01_bmn_nm;
    
    @CsvColumn(name = "H01_KIKAKU_CD")
    public String h01_kikaku_cd;
    
    @CsvColumn(name = "H01_KIKAKU_NM")
    public String h01_kikaku_nm;
    
    @CsvColumn(name = "H01_KIKAKU_SYU")
    public String h01_kikaku_syu;
    
    @CsvColumn(name = "H01_KIKAKU_SYU_NM")
    public String h01_kikaku_syu_nm;
    
    @CsvColumn(name = "H01_NENDO")
    public String h01_nendo;
    
    @CsvColumn(name = "H01_TOKKIJIKO")
    public String h01_tokkijiko;
    
    @CsvColumn(name = "H01_HANBAI_FRDD")
    public String h01_hanbai_frdd;
    
    @CsvColumn(name = "H01_HANBAI_TODD")
    public String h01_hanbai_todd;
    
    @CsvColumn(name = "H01_SIIRE_FRDD")
    public String h01_siire_frdd;
    
    @CsvColumn(name = "H01_SIIRE_TODD")
    public String h01_siire_todd;
    
    @CsvColumn(name = "H01_TIRASI_KBN")
    public String h01_tirasi_kbn;
    
    @CsvColumn(name = "H01_KAKUTEI_DAY")
    public String h01_kakutei_day;
    
    @CsvColumn(name = "H01_HANSOKU_DAY")
    public String h01_hansoku_day;
    
    @CsvColumn(name = "H01_URI_YOSAN")
    public String h01_uri_yosan;
    
    @CsvColumn(name = "H01_ARA_YOSAN")
    public String h01_ara_yosan;
    
    @CsvColumn(name = "M01_TEN_CD")
    public String m01_ten_cd;
    
    @CsvColumn(name = "M01_TEN_NM")
    public String m01_ten_nm;
    
    @CsvColumn(name = "M01_URI_YOSAN")
    public String m01_uri_yosan;
    
    @CsvColumn(name = "M01_ARA_YOSAN")
    public String m01_ara_yosan;
    
    @CsvColumn(name = "M01_URI_BAIK_KIN")
    public String m01_uri_baik_kin;
    
    @CsvColumn(name = "M01_SIR_GENK_KIN")
    public String m01_sir_genk_kin;
    
    @CsvColumn(name = "M01_MAE_ZIK_GENK_KIN")
    public String m01_mae_zik_genk_kin;
    
    @CsvColumn(name = "M01_ATO_ZIK_GENK_KIN")
    public String m01_ato_zik_genk_kin;
    
    @CsvColumn(name = "M01_MAE_ZIK_BAIK_KIN")
    public String m01_mae_zik_baik_kin;
    
    @CsvColumn(name = "M01_ATO_ZIK_BAIK_KIN")
    public String m01_ato_zik_baik_kin;
    
    @CsvColumn(name = "M01_URI_HPN_KIN")
    public String m01_uri_hpn_kin;
    
    @CsvColumn(name = "M01_NEAG_KIN")
    public String m01_neag_kin;
    
    @CsvColumn(name = "M01_NESG_KIN")
    public String m01_nesg_kin;
}
