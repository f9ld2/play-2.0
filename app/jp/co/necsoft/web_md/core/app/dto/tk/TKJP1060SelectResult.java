///////////////////////////////////////////////////////////////////////
// Copyright(C) 2015 NEC Solution Innovators, Ltd. All Rights Reserved.
///////////////////////////////////////////////////////////////////////

/* ====================================================================
 * 機能名称 : 企画情報
 * 改版履歴
 * Rev. 改版年月日   改版者名       内容
 * 1.0   2014.04.29   VuQQ      新規作成
 * =================================================================== */
package jp.co.necsoft.web_md.core.app.dto.tk;

/**
 * TKJP1060SelectResultのDtoクラス
 * 
 */
public class TKJP1060SelectResult {
    /** 会社コード */
    private String kaisyaCd;

    /** 事業部コード */
    private String jigyobuCd;

    /** 年度 */
    private String nenDo;

    /** 企画コード */
    private String kikakuCd;

    /** 部門コード */
    private String bmnCd;

    /** 店舗コード */
    private String tenCd;

    /** 企画名 */
    private String kikakuNm;

    /** 企画種類 */
    private String kikakuSyu;

    /** 特記事項 */
    private String tokkijiko;

    /** チラシ区分 */
    private String tirasikbn;

    /** 店舗確定日 */
    private String kakuteiDay;

    /** 販促提出期限日 */
    private String hansokuDay;

    /** 販売期間（開始日） */
    private String hanbaiFrdd;

    /** 販売期間（終了日） */
    private String hanbaiTodd;

    /** 仕入期間(開始日) */
    private String siireFrdd;

    /** 仕入期間(終了日) */
    private String siireTodd;

    /** 予算金額（企画全体） */
    private String uriyosan01;

    /** 荒利予算（企画全体） */
    private String arayosan01;

    /** 予算金額（店舗別） */
    private String uriyosan02;

    /** 荒利予算（店舗別） */
    private String arayosan02;

    /** 売上金額 */
    private String uriBaikKin;

    /** 売上返品金額 */
    private String uriHpnKin;

    /** 値上金額 */
    private String neagKin;

    /** 値下金額 */
    private String nesgKin;

    /** 仕入原価金額 */
    private String sirGenkKin;

    /** 特売前在庫原価金額 */
    private String maeZikGenkKin;

    /** 特売前在庫売価金額 */
    private String maeZikBaikKin;

    /** 特売後在庫原価金額 */
    private String atoZikGenkKin;

    /** 特売後在庫売価金額 */
    private String atoZikBaikKin;

    /**
     * @return the kaisyaCd
     */
    public String getKaisyaCd() {
        return kaisyaCd;
    }

    /**
     * @param kaisyaCd the kaisyaCd to set
     */
    public void setKaisyaCd(String kaisyaCd) {
        this.kaisyaCd = kaisyaCd;
    }

    /**
     * @return the jigyobuCd
     */
    public String getJigyobuCd() {
        return jigyobuCd;
    }

    /**
     * @param jigyobuCd the jigyobuCd to set
     */
    public void setJigyobuCd(String jigyobuCd) {
        this.jigyobuCd = jigyobuCd;
    }

    /**
     * @return the nenDo
     */
    public String getNenDo() {
        return nenDo;
    }

    /**
     * @param nenDo the nenDo to set
     */
    public void setNenDo(String nenDo) {
        this.nenDo = nenDo;
    }

    /**
     * @return the kikakuCd
     */
    public String getKikakuCd() {
        return kikakuCd;
    }

    /**
     * @param kikakuCd the kikakuCd to set
     */
    public void setKikakuCd(String kikakuCd) {
        this.kikakuCd = kikakuCd;
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
     * @return the kikakuSyu
     */
    public String getKikakuSyu() {
        return kikakuSyu;
    }

    /**
     * @param kikakuSyu the kikakuSyu to set
     */
    public void setKikakuSyu(String kikakuSyu) {
        this.kikakuSyu = kikakuSyu;
    }

    /**
     * @return the tokkijiko
     */
    public String getTokkijiko() {
        return tokkijiko;
    }

    /**
     * @param tokkijiko the tokkijiko to set
     */
    public void setTokkijiko(String tokkijiko) {
        this.tokkijiko = tokkijiko;
    }

    /**
     * @return the tirasikbn
     */
    public String getTirasikbn() {
        return tirasikbn;
    }

    /**
     * @param tirasikbn the tirasikbn to set
     */
    public void setTirasikbn(String tirasikbn) {
        this.tirasikbn = tirasikbn;
    }

    /**
     * @return the kakuteiDay
     */
    public String getKakuteiDay() {
        return kakuteiDay;
    }

    /**
     * @param kakuteiDay the kakuteiDay to set
     */
    public void setKakuteiDay(String kakuteiDay) {
        this.kakuteiDay = kakuteiDay;
    }

    /**
     * @return the hansokuDay
     */
    public String getHansokuDay() {
        return hansokuDay;
    }

    /**
     * @param hansokuDay the hansokuDay to set
     */
    public void setHansokuDay(String hansokuDay) {
        this.hansokuDay = hansokuDay;
    }

    /**
     * @return the hanbaiFrdd
     */
    public String getHanbaiFrdd() {
        return hanbaiFrdd;
    }

    /**
     * @param hanbaiFrdd the hanbaiFrdd to set
     */
    public void setHanbaiFrdd(String hanbaiFrdd) {
        this.hanbaiFrdd = hanbaiFrdd;
    }

    /**
     * @return the hanbaiTodd
     */
    public String getHanbaiTodd() {
        return hanbaiTodd;
    }

    /**
     * @param hanbaiTodd the hanbaiTodd to set
     */
    public void setHanbaiTodd(String hanbaiTodd) {
        this.hanbaiTodd = hanbaiTodd;
    }

    /**
     * @return the siireFrdd
     */
    public String getSiireFrdd() {
        return siireFrdd;
    }

    /**
     * @param siireFrdd the siireFrdd to set
     */
    public void setSiireFrdd(String siireFrdd) {
        this.siireFrdd = siireFrdd;
    }

    /**
     * @return the siireTodd
     */
    public String getSiireTodd() {
        return siireTodd;
    }

    /**
     * @param siireTodd the siireTodd to set
     */
    public void setSiireTodd(String siireTodd) {
        this.siireTodd = siireTodd;
    }

    /**
     * @return the uriyosan01
     */
    public String getUriyosan01() {
        return uriyosan01;
    }

    /**
     * @param uriyosan01 the uriyosan01 to set
     */
    public void setUriyosan01(String uriyosan01) {
        this.uriyosan01 = uriyosan01;
    }

    /**
     * @return the arayosan01
     */
    public String getArayosan01() {
        return arayosan01;
    }

    /**
     * @param arayosan01 the arayosan01 to set
     */
    public void setArayosan01(String arayosan01) {
        this.arayosan01 = arayosan01;
    }

    /**
     * @return the uriyosan02
     */
    public String getUriyosan02() {
        return uriyosan02;
    }

    /**
     * @param uriyosan02 the uriyosan02 to set
     */
    public void setUriyosan02(String uriyosan02) {
        this.uriyosan02 = uriyosan02;
    }

    /**
     * @return the arayosan02
     */
    public String getArayosan02() {
        return arayosan02;
    }

    /**
     * @param arayosan02 the arayosan02 to set
     */
    public void setArayosan02(String arayosan02) {
        this.arayosan02 = arayosan02;
    }

    /**
     * @return the uriBaikKin
     */
    public String getUriBaikKin() {
        return uriBaikKin;
    }

    /**
     * @param uriBaikKin the uriBaikKin to set
     */
    public void setUriBaikKin(String uriBaikKin) {
        this.uriBaikKin = uriBaikKin;
    }

    /**
     * @return the uriHpnKin
     */
    public String getUriHpnKin() {
        return uriHpnKin;
    }

    /**
     * @param uriHpnKin the uriHpnKin to set
     */
    public void setUriHpnKin(String uriHpnKin) {
        this.uriHpnKin = uriHpnKin;
    }

    /**
     * @return the neagKin
     */
    public String getNeagKin() {
        return neagKin;
    }

    /**
     * @param neagKin the neagKin to set
     */
    public void setNeagKin(String neagKin) {
        this.neagKin = neagKin;
    }

    /**
     * @return the nesgKin
     */
    public String getNesgKin() {
        return nesgKin;
    }

    /**
     * @param nesgKin the nesgKin to set
     */
    public void setNesgKin(String nesgKin) {
        this.nesgKin = nesgKin;
    }

    /**
     * @return the sirGenkKin
     */
    public String getSirGenkKin() {
        return sirGenkKin;
    }

    /**
     * @param sirGenkKin the sirGenkKin to set
     */
    public void setSirGenkKin(String sirGenkKin) {
        this.sirGenkKin = sirGenkKin;
    }

    /**
     * @return the maeZikGenkKin
     */
    public String getMaeZikGenkKin() {
        return maeZikGenkKin;
    }

    /**
     * @param maeZikGenkKin the maeZikGenkKin to set
     */
    public void setMaeZikGenkKin(String maeZikGenkKin) {
        this.maeZikGenkKin = maeZikGenkKin;
    }

    /**
     * @return the maeZikBaikKin
     */
    public String getMaeZikBaikKin() {
        return maeZikBaikKin;
    }

    /**
     * @param maeZikBaikKin the maeZikBaikKin to set
     */
    public void setMaeZikBaikKin(String maeZikBaikKin) {
        this.maeZikBaikKin = maeZikBaikKin;
    }

    /**
     * @return the atoZikGenkKin
     */
    public String getAtoZikGenkKin() {
        return atoZikGenkKin;
    }

    /**
     * @param atoZikGenkKin the atoZikGenkKin to set
     */
    public void setAtoZikGenkKin(String atoZikGenkKin) {
        this.atoZikGenkKin = atoZikGenkKin;
    }

    /**
     * @return the atoZikBaikKin
     */
    public String getAtoZikBaikKin() {
        return atoZikBaikKin;
    }

    /**
     * @param atoZikBaikKin the atoZikBaikKin to set
     */
    public void setAtoZikBaikKin(String atoZikBaikKin) {
        this.atoZikBaikKin = atoZikBaikKin;
    }
}
