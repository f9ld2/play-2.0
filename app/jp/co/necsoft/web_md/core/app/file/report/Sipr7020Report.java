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
public class Sipr7020Report {
	@CsvColumn(name = "H00_PROGID")
	public String h00_progid;
	@CsvColumn(name = "H00_VERSION")
	public String h00_version;
    @CsvColumn(name = "H01_TANTO_CODE")
    public String h01_tanto_code;
    
	@CsvColumn(name = "H01_JIGYOUBU_CD")
	public String h01_jigyobu_cd;
	@CsvColumn(name = "H01_JGY_NM")
	public String h01_jgy_nm;
	@CsvColumn(name = "H01_TEN_CD")
	public String h01_ten_cd;
	@CsvColumn(name = "H01_TEN_NM")
	public String h01_ten_name;
	@CsvColumn(name = "H01_NHN_YOTEI_YMD")
	public String h01_nhn_yotei_ymd;
	@CsvColumn(name = "H01_TAISHO")
	public String h01_taisho;
	
	@CsvColumn(name = "M01_CUS_SHOP_CD")
	public String m01_cus_shop_cd;
	@CsvColumn(name = "M01_TRI_NM")
	public String m01_tri_nm;
	@CsvColumn(name = "M01_DRY_NO")
	public String m01_dry_no;
	@CsvColumn(name = "M01_GYO_NO")
	public String m01_gyo_no;
	@CsvColumn(name = "M01_SHN_CD")
	public String m01_shn_cd;
	@CsvColumn(name = "M01_CBNR_CD")
	public String m01_cbnr_cd;
	@CsvColumn(name = "M01_SBNR_CD")
	public String m01_sbnr_cd;
	@CsvColumn(name = "JISHA_HINBAN")
	public String m01_jisha_hinban;
	@CsvColumn(name = "M01_SHN_NM")
	public String m01_shn_nm;
	@CsvColumn(name = "M01_MKR_CD")
	public String m01_mkr_cd;
	@CsvColumn(name = "M01_SIZE")
	public String m01_size;
	@CsvColumn(name = "M01_COLOR")
	public String m01_color;
	@CsvColumn(name = "M01_SURYO")
	public String m01_suryo;
	@CsvColumn(name = "M01_BAIK_KIN")
	public String m01_baik_kin;

}
