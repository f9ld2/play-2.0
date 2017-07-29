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
 * M006tenmM016tanmResultのDtoクラス
 * 
 */
public class M006tenmM016tanmResult {
    /**店舗コード*/
    private String tenCd;

    /**店舗区分*/
    private String tenKbn;

    /**
     * @return the tenCd
     */
    public String getTenCd() {
        return tenCd;
    }

    /**
     * @param tenCd the tenCd to set
     */
    public void setTenCd(String tenCd) {
        this.tenCd = tenCd;
    }

    /**
     * @return the tenKbn
     */
    public String getTenKbn() {
        return tenKbn;
    }

    /**
     * @param tenKbn the tenKbn to set
     */
    public void setTenKbn(String tenKbn) {
        this.tenKbn = tenKbn;
    }

}
