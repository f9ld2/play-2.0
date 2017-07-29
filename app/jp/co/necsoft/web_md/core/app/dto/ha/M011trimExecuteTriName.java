// /////////////////////////////////////////////////////////////////////
// Copyright(C) 2015 NEC Solution Innovators, Ltd. All Rights Reserved.
// /////////////////////////////////////////////////////////////////////

/*
 * ====================================================================
 * 
 * 機能名称 : 物流スケジュールメンテ
 * 
 * 改版履歴
 * 
 * Rev. 改版年月日 改版者名 内容
 * 
 * 1.0 2014.04.18 ToanPQ 新規作成
 * 
 * ===================================================================
 */
package jp.co.necsoft.web_md.core.app.dto.ha;

/**
*M011trimExecuteTriNameのDtoクラス
*
*/
public class M011trimExecuteTriName {
    /**取引先*/
    private String triCd;

    /**発効日*/
    private String hakkoDay;

    public String getTriCd() {
        return triCd;
    }

    public void setTriCd(String triCd) {
        this.triCd = triCd;
    }

    public String getHakkoDay() {
        return hakkoDay;
    }

    public void setHakkoDay(String hakkoDay) {
        this.hakkoDay = hakkoDay;
    }

}
