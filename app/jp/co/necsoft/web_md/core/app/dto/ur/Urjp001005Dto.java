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
 * Urjp001005Dtoクラス
 *
 */
public class Urjp001005Dto {
    /** yosKinMon */
    private String yosKinMon;
    /** yosKyakuMon */
    private Long yosKyakuMon;
    /** uriSakuTai */
    private String uriSakuTai;
    /** kyakuSakuTai */
    private BigDecimal kyakuSakuTai;
    /** uriSai */
    private String uriSai;
    /** kyakuSai */
    private Long kyakuSai;

    /** Urjp001005Dto */
    public Urjp001005Dto() {
        yosKinMon = null;
        yosKyakuMon = new Long(0);
        uriSakuTai = null;
        kyakuSakuTai = new BigDecimal(0);
        uriSai = null;
        kyakuSai = new Long(0);
    }

    /**
     * @return the yosKinMon
     */
    public String getYosKinMon() {
        return yosKinMon;
    }

    /**
     * @param yosKinMon the yosKinMon to set
     */
    public void setYosKinMon(String yosKinMon) {
        this.yosKinMon = yosKinMon;
    }

    /**
     * @return the yosKyakuMon
     */
    public Long getYosKyakuMon() {
        return yosKyakuMon;
    }

    /**
     * @param yosKyakuMon the yosKyakuMon to set
     */
    public void setYosKyakuMon(Long yosKyakuMon) {
        this.yosKyakuMon = yosKyakuMon;
    }

    /**
     * @return the uriSakuTai
     */
    public String getUriSakuTai() {
        return uriSakuTai;
    }

    /**
     * @param uriSakuTai the uriSakuTai to set
     */
    public void setUriSakuTai(String uriSakuTai) {
        this.uriSakuTai = uriSakuTai;
    }

    /**
     * @return the kyakuSakuTai
     */
    public BigDecimal getKyakuSakuTai() {
        return kyakuSakuTai;
    }

    /**
     * @param kyakuSakuTai the kyakuSakuTai to set
     */
    public void setKyakuSakuTai(BigDecimal kyakuSakuTai) {
        this.kyakuSakuTai = kyakuSakuTai;
    }

    /**
     * @return the uriSai
     */
    public String getUriSai() {
        return uriSai;
    }

    /**
     * @param uriSai the uriSai to set
     */
    public void setUriSai(String uriSai) {
        this.uriSai = uriSai;
    }

    /**
     * @return the kyakuSai
     */
    public Long getKyakuSai() {
        return kyakuSai;
    }

    /**
     * @param kyakuSai the kyakuSai to set
     */
    public void setKyakuSai(Long kyakuSai) {
        this.kyakuSai = kyakuSai;
    }

}
