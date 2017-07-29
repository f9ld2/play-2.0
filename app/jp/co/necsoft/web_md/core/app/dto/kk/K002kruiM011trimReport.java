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
 *K002kruiM011trimReportクラス
 *
 */
public class K002kruiM011trimReport {

    /**取引先コード*/
    private String sirCd;

    /**前回繰越高*/
    private String zenZan;

    /**仕入金額*/
    private String sirKingk;

    /**返品金額*/
    private String hpnKingk;

    /**値引金額*/
    private String nbkKingk;

    /**純仕入*/
    private String junSir;

    /**支払金額*/
    private String shrKingk;

    /**繰越残高*/
    private String zan;

    /**月次月*/
    private String getuj;

    /**支払（買掛精算区分）*/
    private String seisanKbn;

    /**照合区分*/
    private String syogoKbn;

    /**サイト（支払方法）*/
    private String sirHoho;

    /** @return the sirCd*/
    public String getSirCd() {
        return sirCd;
    }

    /** @param sirCd the sirCd to set*/
    public void setSirCd(String sirCd) {
        this.sirCd = sirCd;
    }

    /** @return the zenZan*/
    public String getZenZan() {
        return zenZan;
    }

    /** @param zenZan the zenZan to set*/
    public void setZenZan(String zenZan) {
        this.zenZan = zenZan;
    }

    /** @return the sirKingk*/
    public String getSirKingk() {
        return sirKingk;
    }

    /** @param sirKingk the sirKingk to set*/
    public void setSirKingk(String sirKingk) {
        this.sirKingk = sirKingk;
    }

    /** @return the hpnKingk*/
    public String getHpnKingk() {
        return hpnKingk;
    }

    /** @param hpnKingk the hpnKingk to set*/
    public void setHpnKingk(String hpnKingk) {
        this.hpnKingk = hpnKingk;
    }

    /** @return the nbkKingk*/
    public String getNbkKingk() {
        return nbkKingk;
    }

    /** @param nbkKingk the nbkKingk to set*/
    public void setNbkKingk(String nbkKingk) {
        this.nbkKingk = nbkKingk;
    }

    /** @return the junSir*/
    public String getJunSir() {
        return junSir;
    }

    /** @param junSir the junSir to set*/
    public void setJunSir(String junSir) {
        this.junSir = junSir;
    }

    /** @return the shrKingk*/
    public String getShrKingk() {
        return shrKingk;
    }

    /** @param shrKingk the shrKingk to set*/
    public void setShrKingk(String shrKingk) {
        this.shrKingk = shrKingk;
    }

    /** @return the zan*/
    public String getZan() {
        return zan;
    }

    /** @param zan the zan to set*/
    public void setZan(String zan) {
        this.zan = zan;
    }

    /** @return the getuj*/
    public String getGetuj() {
        return getuj;
    }

    /** @param getuj the getuj to set*/
    public void setGetuj(String getuj) {
        this.getuj = getuj;
    }

    /** @return the seisanKbn*/
    public String getSeisanKbn() {
        return seisanKbn;
    }

    /** @param seisanKbn the seisanKbn to set*/
    public void setSeisanKbn(String seisanKbn) {
        this.seisanKbn = seisanKbn;
    }

    /** @return the syogoKbn*/
    public String getSyogoKbn() {
        return syogoKbn;
    }

    /** @param syogoKbn the syogoKbn to set*/
    public void setSyogoKbn(String syogoKbn) {
        this.syogoKbn = syogoKbn;
    }

    /** @return the sirHoho*/
    public String getSirHoho() {
        return sirHoho;
    }

    /** @param sirHoho the sirHoho to set*/
    public void setSirHoho(String sirHoho) {
        this.sirHoho = sirHoho;
    }

}
