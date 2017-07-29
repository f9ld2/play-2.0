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
import java.util.List;

import jp.co.necsoft.web_md.core.common.forms.ErrorResponse;
import play.data.validation.Constraints.Required;

/**
*日別店別部門別予算入力のResultFormクラス
*
*/
public class Urjp0010ResultForm {
    /**
     * finalStage
     */
    private boolean finalStage;
    /** extTenCopy */
    private boolean extTenCopy;
    /** insert */
    private boolean insert;
    /**対象年*/
    @Required
    private String yyyy;
    /**月*/
    @Required
    private String mm;
    /**会社*/
    @Required
    private String kaisyaCd;
    /**事業部*/
    @Required
    private String jigyobuCd;
    /**店舗*/
    @Required
    private String tenCd;
    /**自動計算区分取得*/
    private String zidouKeisanKbn;
    /**hdnVal*/
    private String hdnVal;
    /**グリッド１*/
    private List<Urjp001001Dto> ysnHibetsuArea;
    /**グリッド2*/
    private List<Urjp001002Dto> ysnHibetsuSumAreaData;
    /**グリッド3*/
    private List<Urjp001003Dto> uriakeMokuhyouAreaData;
    /**グリッド4*/
    private List<Urjp001004Dto> mokuhyouGoukeiAreaData;
    /**グリッド5*/
    private List<Urjp001005Dto> gekkanUriakeYsnAreaData;
    /**グリッド6*/
    private List<Urjp001006Dto> grid6;
    /**infoRes*/
    private ErrorResponse infoRes;
    /**uriSakuTai*/
    private BigDecimal uriSakuTai;
    /**kyakuSakuTai*/
    private BigDecimal kyakuSakuTai;
    /**wTenCd*/
    private String wTenCd;
    /**dispDayBmn*/
    private boolean dispDayBmn;
    /**yosKinMon*/
    private Long yosKinMon;
    /**yosKyakuMon*/
    private Long yosKyakuMon;
    /**dbYosKyakuMon*/
    private int dbYosKyakuMon;
    /**dbYosKinMon*/
    private Long dbYosKinMon;
    /**dbYosKyakuMon*/
    private Long uriSai;
    /**kyakuSai*/
    private Long kyakuSai;

    /** 
     * Constructor's
     * */
    public Urjp0010ResultForm() {
        uriSakuTai = new BigDecimal(0);
        kyakuSakuTai = new BigDecimal(0);
        yosKinMon = new Long(0);
        yosKyakuMon = new Long(0);
        dbYosKyakuMon = 0;
        dbYosKinMon = new Long(0);
        uriSai = new Long(0);
        kyakuSai = new Long(0);
    }

    /**
     * @return the yyyy
     */
    public String getYyyy() {
        return yyyy;
    }

    /**
     * @param yyyy the yyyy to set
     */
    public void setYyyy(String yyyy) {
        this.yyyy = yyyy;
    }

    /**
     * @return the mm
     */
    public String getMm() {
        return mm;
    }

    /**
     * @param mm the mm to set
     */
    public void setMm(String mm) {
        this.mm = mm;
    }

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
     * @return the infoRes
     */
    public ErrorResponse getInfoRes() {
        return infoRes;
    }

    /**
     * @param infoRes the infoRes to set
     */
    public void setInfoRes(ErrorResponse infoRes) {
        this.infoRes = infoRes;
    }

    /**
     * @return the zidouKeisanKbn
     */
    public String getZidouKeisanKbn() {
        return zidouKeisanKbn;
    }

    /**
     * @param zidouKeisanKbn the zidouKeisanKbn to set
     */
    public void setZidouKeisanKbn(String zidouKeisanKbn) {
        this.zidouKeisanKbn = zidouKeisanKbn;
    }

    /**
     * @return the hdnVal
     */
    public String getHdnVal() {
        return hdnVal;
    }

    /**
     * @param hdnVal the hdnVal to set
     */
    public void setHdnVal(String hdnVal) {
        this.hdnVal = hdnVal;
    }

    /**
     * @return the ysnHibetsuArea
     */
    public List<Urjp001001Dto> getYsnHibetsuArea() {
        return ysnHibetsuArea;
    }

    /**
     * @param ysnHibetsuArea the ysnHibetsuArea to set
     */
    public void setYsnHibetsuArea(List<Urjp001001Dto> ysnHibetsuArea) {
        this.ysnHibetsuArea = ysnHibetsuArea;
    }

    /**
     * @return the ysnHibetsuSumAreaData
     */
    public List<Urjp001002Dto> getYsnHibetsuSumAreaData() {
        return ysnHibetsuSumAreaData;
    }

    /**
     * @param ysnHibetsuSumAreaData the ysnHibetsuSumAreaData to set
     */
    public void setYsnHibetsuSumAreaData(List<Urjp001002Dto> ysnHibetsuSumAreaData) {
        this.ysnHibetsuSumAreaData = ysnHibetsuSumAreaData;
    }

    /**
     * @return the uriakeMokuhyouAreaData
     */
    public List<Urjp001003Dto> getUriakeMokuhyouAreaData() {
        return uriakeMokuhyouAreaData;
    }

    /**
     * @param uriakeMokuhyouAreaData the uriakeMokuhyouAreaData to set
     */
    public void setUriakeMokuhyouAreaData(List<Urjp001003Dto> uriakeMokuhyouAreaData) {
        this.uriakeMokuhyouAreaData = uriakeMokuhyouAreaData;
    }

    /**
     * @return the mokuhyouGoukeiAreaData
     */
    public List<Urjp001004Dto> getMokuhyouGoukeiAreaData() {
        return mokuhyouGoukeiAreaData;
    }

    /**
     * @param mokuhyouGoukeiAreaData the mokuhyouGoukeiAreaData to set
     */
    public void setMokuhyouGoukeiAreaData(List<Urjp001004Dto> mokuhyouGoukeiAreaData) {
        this.mokuhyouGoukeiAreaData = mokuhyouGoukeiAreaData;
    }

    /**
     * @return the gekkanUriakeYsnAreaData
     */
    public List<Urjp001005Dto> getGekkanUriakeYsnAreaData() {
        return gekkanUriakeYsnAreaData;
    }

    /**
     * @param gekkanUriakeYsnAreaData the gekkanUriakeYsnAreaData to set
     */
    public void setGekkanUriakeYsnAreaData(List<Urjp001005Dto> gekkanUriakeYsnAreaData) {
        this.gekkanUriakeYsnAreaData = gekkanUriakeYsnAreaData;
    }

    /**
     * @return the grid6
     */
    public List<Urjp001006Dto> getGrid6() {
        return grid6;
    }

    /**
     * @param grid6 the grid6 to set
     */
    public void setGrid6(List<Urjp001006Dto> grid6) {
        this.grid6 = grid6;
    }

    /**
     * @return the wTenCd
     */
    public String getwTenCd() {
        return wTenCd;
    }

    /**
     * @param wTenCd the wTenCd to set
     */
    public void setwTenCd(String wTenCd) {
        this.wTenCd = wTenCd;
    }

    /**
     * @return the dispDayBmn
     */
    public boolean isDispDayBmn() {
        return dispDayBmn;
    }

    /**
     * @param dispDayBmn the dispDayBmn to set
     */
    public void setDispDayBmn(boolean dispDayBmn) {
        this.dispDayBmn = dispDayBmn;
    }

    /**
     * @return the uriSakuTai
     */
    public BigDecimal getUriSakuTai() {
        return uriSakuTai;
    }

    /**
     * @param uriSakuTai the uriSakuTai to set
     */
    public void setUriSakuTai(BigDecimal uriSakuTai) {
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
     * @return the yosKinMon
     */
    public Long getYosKinMon() {
        return yosKinMon;
    }

    /**
     * @param yosKinMon the yosKinMon to set
     */
    public void setYosKinMon(Long yosKinMon) {
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
     * @return the dbYosKyakuMon
     */
    public int getDbYosKyakuMon() {
        return dbYosKyakuMon;
    }

    /**
     * @param dbYosKyakuMon the dbYosKyakuMon to set
     */
    public void setDbYosKyakuMon(int dbYosKyakuMon) {
        this.dbYosKyakuMon = dbYosKyakuMon;
    }

    /**
     * @return the dbYosKinMon
     */
    public Long getDbYosKinMon() {
        return dbYosKinMon;
    }

    /**
     * @param dbYosKinMon the dbYosKinMon to set
     */
    public void setDbYosKinMon(Long dbYosKinMon) {
        this.dbYosKinMon = dbYosKinMon;
    }

    /**
     * @return the uriSai
     */
    public Long getUriSai() {
        return uriSai;
    }

    /**
     * @param uriSai the uriSai to set
     */
    public void setUriSai(Long uriSai) {
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

    /**
     * @return the insert
     */
    public boolean isInsert() {
        return insert;
    }

    /**
     * @param insert the insert to set
     */
    public void setInsert(boolean insert) {
        this.insert = insert;
    }

    /**
     * @return the extTenCopy
     */
    public boolean isExtTenCopy() {
        return extTenCopy;
    }

    /**
     * @param extTenCopy the extTenCopy to set
     */
    public void setExtTenCopy(boolean extTenCopy) {
        this.extTenCopy = extTenCopy;
    }

    /**
     * @return the finalStage
     */
    public boolean isFinalStage() {
        return finalStage;
    }

    /**
     * @param finalStage the finalStage to set
     */
    public void setFinalStage(boolean finalStage) {
        this.finalStage = finalStage;
    }
}
