///////////////////////////////////////////////////////////////////////
//Copyright(C) 2015 NEC Solution Innovators, Ltd. All Rights Reserved.
///////////////////////////////////////////////////////////////////////

/* ====================================================================
 * 機能名称 : 棚卸実施チェックリスト * 改版履歴
 * Rev. 改版年月日   改版者名       内容
 * 1.0   2015.05.07 NECVN      新規作成
 * =================================================================== */

package jp.co.necsoft.web_md.core.app.file.report;

import com.orangesignal.csv.annotation.CsvColumn;
import com.orangesignal.csv.annotation.CsvEntity;
/**
 * 棚卸実施チェックリストのReportクラス
 * @author NECVN
 */
@CsvEntity
public class Zipr7010Report {
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
    @CsvColumn(name = "H01_TNA_UNY_DD")
    public String h01_tna_uny_dd;
    @CsvColumn(name = "M01_TANA_NO")
    public String m01_tana_no;
    @CsvColumn(name = "M01_MEISAI_KENSU")
    public String m01_meisai_kensu;
    @CsvColumn(name = "M01_SURYO")
    public String m01_suryo;
    @CsvColumn(name = "M01_GENK_KIN")
    public String m01_genk_kin;
    @CsvColumn(name = "M01_BAIK_KIN")
    public String m01_baik_kin;
}
