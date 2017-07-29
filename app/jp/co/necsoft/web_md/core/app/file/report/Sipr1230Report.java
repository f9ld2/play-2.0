// /////////////////////////////////////////////////////////////////////
// Copyright(C) 2015 NEC Solution Innovators, Ltd. All Rights Reserved.
// /////////////////////////////////////////////////////////////////////

/*
 * ====================================================================
 * 
 * 機能名称 :伝票入力プルーフリスト
 * 
 * 改版履歴
 * 
 * Rev. 改版年月日 改版者名 内容
 * 
 * 1.0 20140429 Tinnc 新規作成
 * 
 * ===================================================================
 */
package jp.co.necsoft.web_md.core.app.file.report;

import com.orangesignal.csv.annotation.CsvColumn;
import com.orangesignal.csv.annotation.CsvEntity;

@CsvEntity
public class Sipr1230Report {
    @CsvColumn(name = "H00_PROGID")
    public String h00_progid;
    @CsvColumn(name = "H00_VERSION")
    public String h00_version;
    @CsvColumn(name = "H00_SDATE")
    public String h00_sdate;
    @CsvColumn(name = "H00_STIME")
    public String h00_stime;
    @CsvColumn(name = "H00_PAGE")
    public String h00_page;
    @CsvColumn(name = "H01_TANTO_CODE")
    public String h01_tanto_code;
    @CsvColumn(name = "H01_TANTO")
    public String h01_tanto;
    @CsvColumn(name = "H01_TANTO_NAME")
    public String h01_tanto_name;
    @CsvColumn(name = "H01_INPUT_DATE")
    public String h01_input_date;
    @CsvColumn(name = "M01_INPUT_TIME")
    public String m01_input_time;
    @CsvColumn(name = "M01_SEQNO")
    public String m01_seqno;
    @CsvColumn(name = "M01_DEL_KBN")
    public String m01_del_kbn;
    @CsvColumn(name = "M01_DENKBN")
    public String m01_denkbn;
    @CsvColumn(name = "M01_TEN_CODE")
    public String m01_ten_code;
    @CsvColumn(name = "M01_TEN_NAME")
    public String m01_ten_name;
    @CsvColumn(name = "M01_BMN_CODE")
    public String m01_bmn_code;
    @CsvColumn(name = "M01_BMN_NAME")
    public String m01_bmn_name;
    @CsvColumn(name = "M01_DEN_NO")
    public String m01_den_no;
    @CsvColumn(name = "M01_TORI_CODE")
    public String m01_tori_code;
    @CsvColumn(name = "M01_TORI_NAME")
    public String m01_tori_name;
    @CsvColumn(name = "M01_NOH_DATE")
    public String m01_noh_date;
    @CsvColumn(name = "M01_HAT_DATE")
    public String m01_hat_date;
    @CsvColumn(name = "M01_GEN_KING")
    public String m01_gen_king;
    @CsvColumn(name = "M01_BAI_KING")
    public String m01_bai_king;
    @CsvColumn(name = "M01_NOUNIN_R")
    public String m01_nounin_r;
    @CsvColumn(name = "M01_OUT_BMN")
    public String m01_out_bmn;

}
