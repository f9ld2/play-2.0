// /////////////////////////////////////////////////////////////////////
// Copyright(C) 2015 NEC Solution Innovators, Ltd. All Rights Reserved.
// /////////////////////////////////////////////////////////////////////

/*
 * ====================================================================
 * 
 * 機能名称 : 棚卸取扱い部門リスト
 * 
 * 改版履歴
 * 
 * Rev. 改版年月日 改版者名 内容
 * 
 * 1.0 2014/05/07 Taivd 新規作成
 * 
 * ===================================================================
 */

package jp.co.necsoft.web_md.core.app.file.report;

import com.orangesignal.csv.annotation.CsvColumn;
import com.orangesignal.csv.annotation.CsvEntity;

@CsvEntity
public class Zipr1020Report1 {
    @CsvColumn(name = "H00_PROGID")
    public String h00_progid;
    @CsvColumn(name = "H00_VERSION")
    public String h00_version;
    @CsvColumn(name = "H01_TANTO_CODE")
    public String h01_tanto_code;
    @CsvColumn(name = "H01_KAISYA_CODE")
    public String h01_kaisya_code;
    @CsvColumn(name = "H01_KAISYA_NAME")
    public String h01_kaisya_name;
    @CsvColumn(name = "H02_JIGYOBU_CODE")
    public String h02_jigyobu_code;
    @CsvColumn(name = "H02_JIGYOBU_NAME")
    public String h02_jigyobu_name;
    @CsvColumn(name = "M00_STR_CODE")
    public String m00_str_code;
    @CsvColumn(name = "M00_STR_NAME")
    public String m00_str_name;
    @CsvColumn(name = "M00_TANA_DATE")
    public String m00_tana_date;
    @CsvColumn(name = "M00_DPT_CODE")
    public String m00_dpt_code;
    @CsvColumn(name = "M00_DPT_NAME")
    public String m00_dpt_name;
    @CsvColumn(name = "M00_4GATU")
    public String m00_4gatu;
    @CsvColumn(name = "M00_5GATU")
    public String m00_5gatu;
    @CsvColumn(name = "M00_6GATU")
    public String m00_6gatu;
    @CsvColumn(name = "M00_7GATU")
    public String m00_7gatu;
    @CsvColumn(name = "M00_8GATU")
    public String m00_8gatu;
    @CsvColumn(name = "M00_9GATU")
    public String m00_9gatu;
    @CsvColumn(name = "M00_10GATU")
    public String m00_10gatu;
    @CsvColumn(name = "M00_11GATU")
    public String m00_11gatu;
    @CsvColumn(name = "M00_12GATU")
    public String m00_12gatu;
    @CsvColumn(name = "M00_1GATU")
    public String m00_1gatu;
    @CsvColumn(name = "M00_2GATU")
    public String m00_2gatu;
    @CsvColumn(name = "M00_3GATU")
    public String m00_3gatu;
    @CsvColumn(name = "M00_KOU_DATE")
    public String m00_kou_date;
}
