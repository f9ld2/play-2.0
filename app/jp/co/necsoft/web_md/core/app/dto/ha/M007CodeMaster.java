// /////////////////////////////////////////////////////////////////////
// Copyright(C) 2015 NEC Solution Innovators, Ltd. All Rights Reserved.
// /////////////////////////////////////////////////////////////////////

/*
 * ====================================================================
 * 
 * 機能名称 : 発注一覧表出力
 * 
 * 
 * 改版履歴
 * 
 * Rev. 改版年月日 改版者名 内容
 * 
 * 1.0 2014-04-22 TUCTVZ 新規作成
 * 
 * ===================================================================
 */
package jp.co.necsoft.web_md.core.app.dto.ha;

/**
 *M007CodeMasterクラス
 *
 */
public class M007CodeMaster {
    /**商品コード*/
    private String shnCd;

    /**商品名*/
    private String shnNm;

    /**規格名称*/
    private String kikakuNm;

    /**ActKbn*/
    private String actKbn;

    /**発効日*/
    private String hakkoDay;

    /**
     * 初期値に設定する。
     */
    public M007CodeMaster() {

    }

    /**
     * 初期値に設定する。
     * 
     * @param shnCd 商品コード
     * @param shnNm 商品名
     * @param kikakuNm 規格名称
     */
    public M007CodeMaster(String shnCd, String shnNm, String kikakuNm) {
        super();
        this.shnCd = shnCd;
        this.shnNm = shnNm;
        this.kikakuNm = kikakuNm;
    }

    /**
     * @return the shnCd
     */
    public String getShnCd() {
        return shnCd;
    }

    /**
     * @param shnCd the shnCd to set
     */
    public void setShnCd(String shnCd) {
        this.shnCd = shnCd;
    }

    /**
     * @return the shnNm
     */
    public String getShnNm() {
        return shnNm;
    }

    /**
     * @param shnNm the shnNm to set
     */
    public void setShnNm(String shnNm) {
        this.shnNm = shnNm;
    }

    /**
     * @return the kikakuNm
     */
    public String getKikakuNm() {
        return kikakuNm;
    }

    /**
     * @param kikakuNm the kikakuNm to set
     */
    public void setKikakuNm(String kikakuNm) {
        this.kikakuNm = kikakuNm;
    }

    /**
     * @return the actKbn
     */
    public String getActKbn() {
        return actKbn;
    }

    /**
     * @param actKbn the actKbn to set
     */
    public void setActKbn(String actKbn) {
        this.actKbn = actKbn;
    }

    /**
     * @return the hakkoDay
     */
    public String getHakkoDay() {
        return hakkoDay;
    }

    /**
     * @param hakkoDay the hakkoDay to set
     */
    public void setHakkoDay(String hakkoDay) {
        this.hakkoDay = hakkoDay;
    }

}
