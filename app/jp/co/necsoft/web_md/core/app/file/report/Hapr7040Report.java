// /////////////////////////////////////////////////////////////////////
// Copyright(C) 2015 NEC Solution Innovators, Ltd. All Rights Reserved.
// /////////////////////////////////////////////////////////////////////

/*
 * ==================================================================== 
 * 機能名称 : HHT基準在庫取込エラーリスト 
 * 改版履歴 
 * Rev. 改版年月日 改版者名 内容
 * 1.0 2015.06.02 LocHV 新規作成
 *  ===================================================================
 */

package jp.co.necsoft.web_md.core.app.file.report;

import com.orangesignal.csv.annotation.CsvColumn;
import com.orangesignal.csv.annotation.CsvEntity;

@CsvEntity
public class Hapr7040Report {
    @CsvColumn(name = "H00_PROGID")
    public String h00_progid;

    @CsvColumn(name = "H00_VERSION")
    public String h00_version;

    @CsvColumn(name = "H01_TANTO_CODE")
    public String h01_tanto_code;

    @CsvColumn(name = "H01_JIGYOBU_CD")
    public String h01_jigyobu_cd;
    
    @CsvColumn(name = "H01_JGY_NM")
    public String h01_jgy_nm;

    @CsvColumn(name = "H01_TEN_CD")
    public String h01_ten_cd;
    
    @CsvColumn(name = "H01_TEN_NM")
    public String h01_ten_nm;
    
    @CsvColumn(name = "H01_TERM_ID")
    public String h01_term_id;
    
    @CsvColumn(name = "M01_SHN_CD")
    public String m01_shn_cd;
    
    @CsvColumn(name = "M01_SURYO")
    public String m01_suryo;
    
    @CsvColumn(name = "M01_SOSIN_NICHIJI")
    public String m01_sosin_nichiji;
    
    @CsvColumn(name = "M01_SHN_NM")
    public String m01_shn_nm;
    
    @CsvColumn(name = "M01_MAKER_HIN_CD")
    public String m01_maker_hin_cd;
    
    @CsvColumn(name = "M01_AUTO_HAT_FLG")
    public String m01_auto_hat_flg;
    
    @CsvColumn(name = "M01_HACHU_TEISI_FROM")
    public String m01_hachu_teisi_from;
    
    @CsvColumn(name = "M01_HACHU_TEISI_TO")
    public String m01_hachu_teisi_to;
    
    @CsvColumn(name = "M01_MSG")
    public String m01_msg;
}
