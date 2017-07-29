///////////////////////////////////////////////////////////////////////
//Copyright(C) 2015 NEC Solution Innovators, Ltd. All Rights Reserved.
///////////////////////////////////////////////////////////////////////

/* ====================================================================
 * 機能名称 : 発注エラーリスト
 * 改版履歴
 * Rev. 改版年月日   改版者名       内容
 * 1.0   2014.16.04   LocHV      新規作成
 * =================================================================== */

package jp.co.necsoft.web_md.core.app.file.report;

import com.orangesignal.csv.annotation.CsvColumn;
import com.orangesignal.csv.annotation.CsvEntity;

@CsvEntity
public class Hapr1010Report {
	@CsvColumn(name = "H00_PROGID")
	public String h00_progid;
	
	@CsvColumn(name = "H00_VERSION")
	public String h00_version;
	
	@CsvColumn(name = "H01_TANTO_CODE")
	public String h01_tanto_code;
	
	@CsvColumn(name = "M01_TEN_CD")
	public String m01_ten_cd;
	
	@CsvColumn(name = "M01_TEN_NM")
	public String m01_ten_nm;
	
	@CsvColumn(name = "M01_BMN_CD")
	public String m01_bmn_cd;
	
	@CsvColumn(name = "M01_TOKUBAI_KBN")
	public String m01_tokubai_kbn;
	
	@CsvColumn(name = "M01_KIKAKU_CD")
	public String m01_kikaku_cd;
	
	@CsvColumn(name = "M01_SYOHIN_CD")
	public String m01_syohin_cd;
	
	@CsvColumn(name = "M01_SYOHIN_NM")
	public String m01_syohin_nm;
	
	@CsvColumn(name = "M01_MKR_NM")
	public String m01_mkr_nm;
	
	@CsvColumn(name = "M01_KIKAKU_NM")
	public String m01_kikaku_nm;
	
	@CsvColumn(name = "M01_HAT_SU")
	public String m01_hat_su;
	
	@CsvColumn(name = "M01_HAT_DD")
	public String m01_hat_dd;
	
	@CsvColumn(name = "M01_NHN_DD")
	public String m01_nhn_dd;
	
	@CsvColumn(name = "M01_POT_DD")
	public String m01_pot_dd;
	
	@CsvColumn(name = "M01_POT_TIME")
	public String m01_pot_time;
	
	@CsvColumn(name = "M01_HAT_SRUI_KBN")
	public String m01_hat_srui_kbn;
	
	@CsvColumn(name = "M01_HAT_SRUI")
	public String m01_hat_srui;
	
	@CsvColumn(name = "M01_TRI_CD")
	public String m01_tri_cd;
	
	@CsvColumn(name = "M01_TRI_NM")
	public String m01_tri_nm;
	
	@CsvColumn(name = "M01_ERR_CD")
	public String m01_err_cd;
	
	@CsvColumn(name = "M01_ERRMSG")
	public String m01_errmsg;
	
	@CsvColumn(name = "M01_FILLER")
	public String m01_filler;
}
