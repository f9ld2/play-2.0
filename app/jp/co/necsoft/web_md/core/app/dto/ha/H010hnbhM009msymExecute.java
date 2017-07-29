// /////////////////////////////////////////////////////////////////////
// Copyright(C) 2015 NEC Solution Innovators, Ltd. All Rights Reserved.
// /////////////////////////////////////////////////////////////////////

/*
 * ====================================================================
 * 
 * 機能名称 :H010hnbhM009msymExecute
 * 
 * 改版履歴
 * 
 * Rev. 改版年月日 改版者名 内容
 * 
 * 1.0 2014.04.16 TuanTQ 新規作成
 * 
 * ===================================================================
 */
package jp.co.necsoft.web_md.core.app.dto.ha;

/**
*H010hnbhM009msymExecuteのDtoクラス
*
*/
public class H010hnbhM009msymExecute {
    /**会社コード*/
    private String kaiCd;

    /**事業部コード*/
    private String jgbCd;

    /**店舗コード*/
    private String tenCd;

    /**店舗コード for 7 char*/
    private String tenCd7;

    /**発注日*/
    private String hatDd;

    /**納品日*/
    private String nhnDd;

    /**発注種類種別*/
    private String hatSruiKbn;

    /**部門コード*/
    private String bmnCd;

    /**発注数*/
    private String hatSu;

    /**
     * @return the kaiCd
     */
    public String getKaiCd() {
        return kaiCd;
    }

    /**
     * @param kaiCd the kaiCd to set
     */
    public void setKaiCd(String kaiCd) {
        this.kaiCd = kaiCd;
    }

    /**
     * @return the jgbCd
     */
    public String getJgbCd() {
        return jgbCd;
    }

    /**
     * @param jgbCd the jgbCd to set
     */
    public void setJgbCd(String jgbCd) {
        this.jgbCd = jgbCd;
    }

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
     * @return the tenCd7
     */
    public String getTenCd7() {
        return tenCd7;
    }

    /**
     * @param tenCd7 the tenCd7 to set
     */
    public void setTenCd7(String tenCd7) {
        this.tenCd7 = tenCd7;
    }

    /**
     * @return the hatDd
     */
    public String getHatDd() {
        return hatDd;
    }

    /**
     * @param hatDd the hatDd to set
     */
    public void setHatDd(String hatDd) {
        this.hatDd = hatDd;
    }

    /**
     * @return the nhnDd
     */
    public String getNhnDd() {
        return nhnDd;
    }

    /**
     * @param nhnDd the nhnDd to set
     */
    public void setNhnDd(String nhnDd) {
        this.nhnDd = nhnDd;
    }

    /**
     * @return the hatSruiKbn
     */
    public String getHatSruiKbn() {
        return hatSruiKbn;
    }

    /**
     * @param hatSruiKbn the hatSruiKbn to set
     */
    public void setHatSruiKbn(String hatSruiKbn) {
        this.hatSruiKbn = hatSruiKbn;
    }

    /**
     * @return the bmnCd
     */
    public String getBmnCd() {
        return bmnCd;
    }

    /**
     * @param bmnCd the bmnCd to set
     */
    public void setBmnCd(String bmnCd) {
        this.bmnCd = bmnCd;
    }

    /**
     * @return the hatSu
     */
    public String getHatSu() {
        return hatSu;
    }

    /**
     * @param hatSu the hatSu to set
     */
    public void setHatSu(String hatSu) {
        this.hatSu = hatSu;
    }

}
