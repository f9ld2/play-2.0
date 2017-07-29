// /////////////////////////////////////////////////////////////////////
// Copyright(C) 2015 NEC Solution Innovators, Ltd. All Rights Reserved.
// /////////////////////////////////////////////////////////////////////

/*
 * ====================================================================
 * 
 * 機能名称 : 発注プルーフリスト出力
 * 
 * 改版履歴
 * 
 * Rev. 改版年月日 改版者名 内容
 * 
 * 1.0 2014.04.20 Tinnc 新規作成
 * 
 * ===================================================================
 */
package jp.co.necsoft.web_md.core.app.file.report;

import com.orangesignal.csv.annotation.CsvColumn;
import com.orangesignal.csv.annotation.CsvEntity;

@CsvEntity
public class Hapr1020Report {
    @CsvColumn(name = "H00_PROGID")
    public String h00_progid; 
    
    @CsvColumn(name = "H00_VERSION")
    public String h00_version;
    
    @CsvColumn(name = "H01_TANTO_CODE")
    public String h01_tanto_code;
    
    @CsvColumn(name = "H01_HAT_DD")
    public String h01_hat_dd;
    
    @CsvColumn(name = "M01_BMN_CD")
    public String m01_bmn_cd;
    
    @CsvColumn(name = "M01_BMN_NM")
    public String m01_bmn_nm;
    
    @CsvColumn(name = "M01_SYOHIN_CD")
    public String m01_syohin_cd;
    
    @CsvColumn(name = "M01_SYOHIN_NM")
    public String m01_syohin_nm;
    
    @CsvColumn(name = "M01_KIKAKU_NM")
    public String m01_kikaku_nm;
    
    @CsvColumn(name = "M01_TEN_CD")
    public String m01_ten_cd;
    
    @CsvColumn(name = "M01_TEN_NM")
    public String m01_ten_nm;
    
    @CsvColumn(name = "M01_HAT_SYURUI")
    public String m01_hat_syurui;
    
    @CsvColumn(name = "M01_HAT_SU")
    public String m01_hat_su;
    
    @CsvColumn(name = "M01_NHN_DD")
    public String m01_nhn_dd;
    
    @CsvColumn(name = "M01_TRI_CD")
    public String m01_tri_cd;
    
    @CsvColumn(name = "M01_TRI_NM")
    public String m01_tri_nm;
    
    @CsvColumn(name = "M01_GENK")
    public String m01_genk;
    
    @CsvColumn(name = "M01_BAIK")
    public String m01_baik;
    
    @CsvColumn(name = "M01_HAT_JOKYO")
    public String m01_hat_jokyo;
    
    @CsvColumn(name = "M01_HATTYU_IRISU")
    public String m01_hattyu_irisu;
}
