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
*U002ybmnM003bmnmM003bmnmM004ResのDtoクラス
*
*/
public class U002ybmnM003bmnmM003bmnmM004Res {
    /**部門コード*/
    private String bmncd;

    /**部門名称漢字*/
    private String bmnnm;

    /**売上*/
    private Long ysnurikin;

    /**
     * @return the bmncd
     */
    public String getBmncd() {
        return bmncd;
    }

    /**
     * @param bmncd the bmncd to set
     */
    public void setBmncd(String bmncd) {
        this.bmncd = bmncd;
    }

    /**
     * @return the bmnnm
     */
    public String getBmnnm() {
        return bmnnm;
    }

    /**
     * @param bmnnm the bmnnm to set
     */
    public void setBmnnm(String bmnnm) {
        this.bmnnm = bmnnm;
    }

    /**
     * @return the ysnurikin
     */
    public Long getYsnurikin() {
        return ysnurikin;
    }

    /**
     * @param ysnurikin the ysnurikin to set
     */
    public void setYsnurikin(Long ysnurikin) {
        this.ysnurikin = ysnurikin;
    }

}
