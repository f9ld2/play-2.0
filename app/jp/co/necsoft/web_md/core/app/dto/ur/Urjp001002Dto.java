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

import java.math.BigDecimal;

/**
 * Urjp001002Dtoクラス 
 *
 */
public class Urjp001002Dto {
    /** 合計 */
    private String gouKei2;
    /** 目標売上高合計 */
    private Long ysnUriKin;
    /** 昨年同曜売上合計*/
    private Long uriKin;
    /** 構成比合計*/
    private BigDecimal kosei;
    /** 目標客数合計*/
    private Long ysnKyaksu;
    /** 昨年同曜客数合計*/
    private Long tenKyaku;

    /** Urjp001002Dto */
    public Urjp001002Dto() {
        gouKei2 = "";
        ysnUriKin = new Long(0);
        uriKin = new Long(0);
        kosei = new BigDecimal(0);
        ysnKyaksu = new Long(0);
        tenKyaku = new Long(0);
    }

    /**
     * @return the gouKei2
     */
    public String getGouKei2() {
        return gouKei2;
    }

    /**
     * @param gouKei2 the gouKei2 to set
     */
    public void setGouKei2(String gouKei2) {
        this.gouKei2 = gouKei2;
    }

    /**
     * @return the ysnUriKin
     */
    public Long getYsnUriKin() {
        return ysnUriKin;
    }

    /**
     * @param ysnUriKin the ysnUriKin to set
     */
    public void setYsnUriKin(Long ysnUriKin) {
        this.ysnUriKin = ysnUriKin;
    }

    /**
     * @return the uriKin
     */
    public Long getUriKin() {
        return uriKin;
    }

    /**
     * @param uriKin the uriKin to set
     */
    public void setUriKin(Long uriKin) {
        this.uriKin = uriKin;
    }

    /**
     * @return the kosei
     */
    public BigDecimal getKosei() {
        return kosei;
    }

    /**
     * @param kosei the kosei to set
     */
    public void setKosei(BigDecimal kosei) {
        this.kosei = kosei;
    }

    /**
     * @return the ysnKyaksu
     */
    public Long getYsnKyaksu() {
        return ysnKyaksu;
    }

    /**
     * @param ysnKyaksu the ysnKyaksu to set
     */
    public void setYsnKyaksu(Long ysnKyaksu) {
        this.ysnKyaksu = ysnKyaksu;
    }

    /**
     * @return the tenKyaku
     */
    public Long getTenKyaku() {
        return tenKyaku;
    }

    /**
     * @param tenKyaku the tenKyaku to set
     */
    public void setTenKyaku(Long tenKyaku) {
        this.tenKyaku = tenKyaku;
    }

}
