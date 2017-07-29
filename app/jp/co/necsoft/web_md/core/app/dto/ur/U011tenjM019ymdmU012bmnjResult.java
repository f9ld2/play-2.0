// /////////////////////////////////////////////////////////////////////
// Copyright(C) 2015 NEC Solution Innovators, Ltd. All Rights Reserved.
// /////////////////////////////////////////////////////////////////////

/*
 * ====================================================================
 * 
 * 機能名称 :日別予算入力画面
 * 
 * 改版履歴
 * 
 * Rev. 改版年月日 改版者名 内容
 * 
 * 1.0 2014.04.10 TuanTQ 新規作成
 * 
 * ===================================================================
 */
package jp.co.necsoft.web_md.core.app.dto.ur;

/**
*U011tenjM019ymdmU012bmnjResultのDtoクラス
*
*/
public class U011tenjM019ymdmU012bmnjResult {
    /**年月日*/
    private String dt;
    
    /**金額*/
    private Long sakKin;

    /**客数*/
    private Long sakKyaku;

    /**
     * @return the dt
     */
    public String getDt() {
        return dt;
    }

    /**
     * @param dt the dt to set
     */
    public void setDt(String dt) {
        this.dt = dt;
    }

    /**
     * @return the sakKin
     */
    public Long getSakKin() {
        return sakKin;
    }

    /**
     * @param sakKin the sakKin to set
     */
    public void setSakKin(Long sakKin) {
        this.sakKin = sakKin;
    }

    /**
     * @return the sakKyaku
     */
    public Long getSakKyaku() {
        return sakKyaku;
    }

    /**
     * @param sakKyaku the sakKyaku to set
     */
    public void setSakKyaku(Long sakKyaku) {
        this.sakKyaku = sakKyaku;
    }

}
