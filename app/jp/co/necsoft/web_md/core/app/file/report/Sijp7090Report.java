///////////////////////////////////////////////////////////////////////
//Copyright(C) 2015 NEC Solution Innovators, Ltd. All Rights Reserved.
///////////////////////////////////////////////////////////////////////

/* ====================================================================
 * 機能名称 : オリジナル商品品振エラーリスト
 * 改版履歴
 * Rev. 改版年月日   改版者名       内容
 * 1.0   2015.06.10   NECVN      新規作成
 * =================================================================== */

package jp.co.necsoft.web_md.core.app.file.report;

import com.orangesignal.csv.annotation.CsvColumn;
import com.orangesignal.csv.annotation.CsvEntity;
/**
 * オリジナル商品品振エラーリストのReportクラス
 * @author NECVN
 */
@CsvEntity
public class Sijp7090Report {
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
    @CsvColumn(name = "H01_HAT_DD_FR")
    public String h01_hat_dd_fr;
    @CsvColumn(name = "H01_HAT_DD_TO")
    public String h01_hat_dd_to;
    
    @CsvColumn(name = "M01_HAT_DD")
    public String m01_hat_dd;
    @CsvColumn(name = "M01_JAN_CD")
    public String m01_jan_cd;
    @CsvColumn(name = "M01_MKR_CD")
    public String m01_mkr_cd;
    @CsvColumn(name = "M01_SHN_NM")
    public String m01_shn_nm;
    @CsvColumn(name = "M01_HAT_SU")
    public String m01_hat_su;
    @CsvColumn(name = "M01_APP_HIN_SU")
    public String m01_app_hin_su;
    @CsvColumn(name = "M01_HIN_KK_KBN")
    public String m01_hin_kk_kbn;
    @CsvColumn(name = "M01_ERR_MSG")
    public String m01_err_msg;
}
