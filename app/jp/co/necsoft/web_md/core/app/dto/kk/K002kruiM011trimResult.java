///////////////////////////////////////////////////////////////////////
// Copyright(C) 2015 NEC Solution Innovators, Ltd. All Rights Reserved.
///////////////////////////////////////////////////////////////////////

/* ====================================================================
 * 機能名称 : 買掛金残高一覧表
 * 改版履歴
 * Rev. 改版年月日   改版者名       内容
 * 1.0   2014.04.01   TUANVT      新規作成
 * =================================================================== */

package jp.co.necsoft.web_md.core.app.dto.kk;

/**
 *K002kruiM011trimResultクラス
 *
 */
public class K002kruiM011trimResult {

    /**取引先コード**/
    private String mainToriCd;

    /**前回繰越高**/
    private String zenKuriKin;

    /**仕入金額**/
    private String sirKin;

    /**返品金額**/
    private String henKin;

    /**値引金額**/
    private String nebikiKin;

    /**純仕入**/
    private String junSirKin;

    /**支払金額**/
    private String shrKin;

    /**繰越残高**/
    private String kuriKin;

    /**月次月**/
    private String getujYm;

    /**発効日**/
    private String hakkoDay;

    /**買掛精算区分**/
    private String kaiSeisanKbn;

    /**締め区分**/
    private String simeKbn;

    /**支払区分**/
    private String payKbn;

    /** @return the mainToriCd*/
    public String getMainToriCd() {
        return mainToriCd;
    }

    /** @param mainToriCd the mainToriCd to set*/
    public void setMainToriCd(String mainToriCd) {
        this.mainToriCd = mainToriCd;
    }

    /** @return the zenKuriKin*/
    public String getZenKuriKin() {
        return zenKuriKin;
    }

    /** @param zenKuriKin the zenKuriKin to set*/
    public void setZenKuriKin(String zenKuriKin) {
        this.zenKuriKin = zenKuriKin;
    }

    /** @return the sirKin*/
    public String getSirKin() {
        return sirKin;
    }

    /** @param sirKin the sirKin to set*/
    public void setSirKin(String sirKin) {
        this.sirKin = sirKin;
    }

    /** @return the henKin*/
    public String getHenKin() {
        return henKin;
    }

    /** @param henKin the henKin to set*/
    public void setHenKin(String henKin) {
        this.henKin = henKin;
    }

    /** @return the nebikiKin*/
    public String getNebikiKin() {
        return nebikiKin;
    }

    /** @param nebikiKin the nebikiKin to set*/
    public void setNebikiKin(String nebikiKin) {
        this.nebikiKin = nebikiKin;
    }

    /** @return the junSirKin*/
    public String getJunSirKin() {
        return junSirKin;
    }

    /** @param junSirKin the junSirKin to set*/
    public void setJunSirKin(String junSirKin) {
        this.junSirKin = junSirKin;
    }

    /** @return the shrKin*/
    public String getShrKin() {
        return shrKin;
    }

    /** @param shrKin the shrKin to set*/
    public void setShrKin(String shrKin) {
        this.shrKin = shrKin;
    }

    /** @return the kuriKin*/
    public String getKuriKin() {
        return kuriKin;
    }

    /** @param kuriKin the kuriKin to set*/
    public void setKuriKin(String kuriKin) {
        this.kuriKin = kuriKin;
    }

    /** @return the getujYm*/
    public String getGetujYm() {
        return getujYm;
    }

    /** @param getujYm the getujYm to set*/
    public void setGetujYm(String getujYm) {
        this.getujYm = getujYm;
    }

    /** @return the hakkoDay*/
    public String getHakkoDay() {
        return hakkoDay;
    }

    /** @param hakkoDay the hakkoDay to set*/
    public void setHakkoDay(String hakkoDay) {
        this.hakkoDay = hakkoDay;
    }

    /** @return the kaiSeisanKbn*/
    public String getKaiSeisanKbn() {
        return kaiSeisanKbn;
    }

    /** @param kaiSeisanKbn the kaiSeisanKbn to set*/
    public void setKaiSeisanKbn(String kaiSeisanKbn) {
        this.kaiSeisanKbn = kaiSeisanKbn;
    }

    /** @return the simeKbn*/
    public String getSimeKbn() {
        return simeKbn;
    }

    /** @param simeKbn the simeKbn to set*/
    public void setSimeKbn(String simeKbn) {
        this.simeKbn = simeKbn;
    }

    /** @return the payKbn*/
    public String getPayKbn() {
        return payKbn;
    }

    /** @param payKbn the payKbn to set*/
    public void setPayKbn(String payKbn) {
        this.payKbn = payKbn;
    }

}
