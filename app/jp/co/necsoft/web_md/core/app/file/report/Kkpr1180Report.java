///////////////////////////////////////////////////////////////////////
// Copyright(C) 2015 NEC Solution Innovators, Ltd. All Rights Reserved.
///////////////////////////////////////////////////////////////////////

/* ====================================================================
 * 機能名称 : 買掛金支払チェックリスト出力
 * 改版履歴
 * Rev. 改版年月日   改版者名       内容
 * 1.0   2014.04.05   VuQQ      新規作成
 * =================================================================== */
package jp.co.necsoft.web_md.core.app.file.report;

import com.orangesignal.csv.annotation.CsvColumn;
import com.orangesignal.csv.annotation.CsvEntity;

@CsvEntity
public class Kkpr1180Report {
	@CsvColumn(name = "H00_PROGID")         //1
    public String h00_progid;
    
    @CsvColumn(name = "H00_VERSION")        //2
    public String h00_version;
    
    @CsvColumn(name = "H00_SDATE")     //3
    public String h00_sdate;
    
    @CsvColumn(name = "H00_STIME")     //3
    public String h00_stime;
    
    @CsvColumn(name = "H00_PAGE")     //5
    public String h00_page;
    
    @CsvColumn(name = "H01_TANTO_CODE")       //6
    public String h01_tanto_code;
    
    @CsvColumn(name = "H01_KAISYA_CODE")     //7
    public String h01_kaisya_code;
    
    @CsvColumn(name = "H01_KAISYA_NAME")        //8
    public String h01_kaisya_name;
    
    @CsvColumn(name = "H01_BUFFER_01")        //9
    public String h01_buffer_01;
    
    @CsvColumn(name = "H01_BUFFER_02")       //10
    public String h01_buffer_02;
    
    @CsvColumn(name = "H02_KIKAN_FROM")       //11
    public String h02_kikan_from;
    
    @CsvColumn(name = "H02_KIKAN_TO")       //12
    public String h02_kikan_to;
    
    @CsvColumn(name = "M01_SIR_CD")       //13
    public String m01_sir_cd;
    
    @CsvColumn(name = "M01_SIR_NAME")            //14
    public String m01_sir_name;
    
    @CsvColumn(name = "M01_SHR_KINGK")       //15
    public String m01_shr_kingk;
    
    @CsvColumn(name = "M01_SHR_DATE")    //16
    public String m01_shr_date;
    
    @CsvColumn(name = "M01_SIME_DATE")      //17
    public String m01_sime_date;
    
    @CsvColumn(name = "M02_SHR_KINGK")        //18
    public String m02_shr_kingk;
}
