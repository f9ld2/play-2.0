///////////////////////////////////////////////////////////////////////
//Copyright(C) 2015 NEC Solution Innovators, Ltd. All Rights Reserved.
///////////////////////////////////////////////////////////////////////

/* ====================================================================
 * 機能名称 : HHT品振取込エラーリスト
 * 改版履歴
 * Rev. 改版年月日   改版者名       内容
 * 1.0   2015.06.06   NECVN      新規作成
 * =================================================================== */

package jp.co.necsoft.web_md.core.app.file.report;

import com.orangesignal.csv.annotation.CsvColumn;
import com.orangesignal.csv.annotation.CsvEntity;
/**
 * HHT品振取込エラーリストのReportクラス
 * @author NECVN
 */
@CsvEntity
public class Sipr7060Report {
    @CsvColumn(name = "H00_PROGID")
    public String h00_progid;
    @CsvColumn(name = "H00_VERSION")
    public String h00_version;
    @CsvColumn(name = "H01_TANTO_CODE")
    public String h01_tanto_code;

    @CsvColumn(name = "H01_OUT_JIGYOBU_CD")
    public String h01_out_jigyobu_cd;    
    @CsvColumn(name = "H01_OUT_JIGYOBU_NM")
    public String h01_out_jigyobu_nm;    
    @CsvColumn(name = "H01_OUT_TENPO_CD")
    public String h01_out_tenpo_cd;    
    @CsvColumn(name = "H01_OUT_TENPO_NM")
    public String h01_out_tenpo_nm;    
    @CsvColumn(name = "H01_TERM_ID")
    public String h01_term_id;    
    @CsvColumn(name = "H01_TANTO_CD")
    public String h01_tanto_cd;    
    @CsvColumn(name = "M01_SHN_CD")
    public String m01_shn_cd;    
    @CsvColumn(name = "M01_IN_TENPO_CD")
    public String m01_in_tenpo_cd;    
    @CsvColumn(name = "M01_IN_TENPO_NM")
    public String m01_in_tenpo_nm;    
    @CsvColumn(name = "M01_HIN_SU")
    public String m01_hin_su;    
    @CsvColumn(name = "M01_SOSIN_NICHIJI")
    public String m01_sosin_nichiji;    
    @CsvColumn(name = "M01_MAKER_HIN_CD")
    public String m01_maker_hin_cd;
    @CsvColumn(name = "M01_SHN_NM")
    public String m01_shn_nm; 
    @CsvColumn(name = "M01_MSG")
    public String m01_msg;
}