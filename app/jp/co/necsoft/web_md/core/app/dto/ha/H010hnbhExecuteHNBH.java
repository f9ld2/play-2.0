// /////////////////////////////////////////////////////////////////////
// Copyright(C) 2015 NEC Solution Innovators, Ltd. All Rights Reserved.
// /////////////////////////////////////////////////////////////////////

/*
 * ====================================================================
 * 
 * 機能名称 :H010hnbhExecuteHNBH
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
*H010hnbhExecuteHNBHのDtoクラス
*
*/
public class H010hnbhExecuteHNBH {
    /**商品コード*/
    private String shnCd;

    /**会社コード*/
    private String kaiCd;

    /**事業部コード*/
    private String jgbCd;

    /**店舗コード*/
    private String tenCd;

    /**発注日*/
    private String hatDd;

    /**納品日*/
    private String nhnDd;

    /**発注種類種別*/
    private String hatSruiKbn;

    /**便*/
    private String bin;

    /**本部発注*/
    private boolean bHatSu = false;

    /**
     * @return the shinCd
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
     * @return the bin
     */
    public String getBin() {
        return bin;
    }

    /**
     * @param bin the bin to set
     */
    public void setBin(String bin) {
        this.bin = bin;
    }

    /**
     * @return the bHatSu
     */
    public boolean isbHatSu() {
        return bHatSu;
    }

    /**
     * @param bHatSu the bHatSu to set
     */
    public void setbHatSu(boolean bHatSu) {
        this.bHatSu = bHatSu;
    }

}
