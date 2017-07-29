// /////////////////////////////////////////////////////////////////////
// Copyright(C) 2015 NEC Solution Innovators, Ltd. All Rights Reserved.
// /////////////////////////////////////////////////////////////////////

/*
 * ====================================================================
 * 
 * 機能名称 : 物流スケジュールメンテ
 * 
 * 改版履歴
 * 
 * Rev. 改版年月日 改版者名 内容
 * 
 * 1.0 2014.04.18 ToanPQ 新規作成
 * 
 * ===================================================================
 */
package jp.co.necsoft.web_md.core.app.forms.ha;

import java.util.List;

import play.data.validation.Constraints.MaxLength;
import play.data.validation.Constraints.MinLength;
import play.data.validation.Constraints.Required;

/**
*物流スケジュールマスタメンテのResultFormクラス
*
*/
public class Hajp0030ResultForm {
    /**発注年月*/
    @Required
    @MinLength(value = 4)
    @MaxLength(value = 4)
    private String yyyy;

    /**mm*/
    @Required
    @MinLength(value = 2)
    @MaxLength(value = 2)
    private String mm;

    /**取引先*/
    @Required
    @MinLength(value = 9)
    @MaxLength(value = 9)
    private String triCd;

    /**triNm*/
    @MaxLength(value = 20)
    private String triNm;

    /**便NO*/
    @Required
    @MinLength(value = 1)
    @MaxLength(value = 1)
    private String bin;

    /**定番発注と納品日グリッド*/
    private List<Hajp0030GridData> gridData;

    /**月*/
    private Integer hatpMon1;

    /**火*/
    private Integer hatpTue1;

    /**水*/
    private Integer hatpWed1;

    /**木*/
    private Integer hatpThu1;

    /**金*/
    private Integer hatpFri1;

    /**土*/
    private Integer hatpSat1;

    /**日*/
    private Integer hatpSun1;

    /**月*/
    private Integer thatpMon1;

    /**火*/
    private Integer thatpTue1;

    /**水*/
    private Integer thatpWed1;

    /**木*/
    private Integer thatpThu1;

    /**金*/
    private Integer thatpFri1;

    /**土*/
    private Integer thatpSat1;

    /**日*/
    private Integer thatpSun1;

    /**発効日*/
    private String hakkoDay;

    /**対象レコードのリードタイム集計(定番)*/
    private long workLtTei;

    /**対象レコードのリードタイム集計(特売)*/
    private long workLtTok;

    /**担当者コード*/
    private String cmnTantoCd;

    /**端末ＩＤ*/
    private String cmnTermId;

    /**登録日付*/
    private String cmnInsdd;

    /**更新日付*/
    private String cmnUpddd;

    /**更新時刻*/
    private String cmnUpdtime;

    /**存在レコード*/
    private boolean flgNewRecord;

    /**隠れる リスト*/
    private List<String> hiddenList;

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
     * @return the triCd
     */
    public String getTriCd() {
        return triCd;
    }

    /**
     * @param triCd the triCd to set
     */
    public void setTriCd(String triCd) {
        this.triCd = triCd;
    }

    /**
     * @return the triNm
     */
    public String getTriNm() {
        return triNm;
    }

    /**
     * @param triNm the triNm to set
     */
    public void setTriNm(String triNm) {
        this.triNm = triNm;
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
     * @return the gridData
     */
    public List<Hajp0030GridData> getGridData() {
        return gridData;
    }

    /**
     * @param gridData the gridData to set
     */
    public void setGridData(List<Hajp0030GridData> gridData) {
        this.gridData = gridData;
    }

    /**
     * @return the hatpMon1
     */
    public Integer getHatpMon1() {
        return hatpMon1;
    }

    /**
     * @param hatpMon1 the hatpMon1 to set
     */
    public void setHatpMon1(Integer hatpMon1) {
        this.hatpMon1 = hatpMon1;
    }

    /**
     * @return the hatpTue1
     */
    public Integer getHatpTue1() {
        return hatpTue1;
    }

    /**
     * @param hatpTue1 the hatpTue1 to set
     */
    public void setHatpTue1(Integer hatpTue1) {
        this.hatpTue1 = hatpTue1;
    }

    /**
     * @return the hatpWed1
     */
    public Integer getHatpWed1() {
        return hatpWed1;
    }

    /**
     * @param hatpWed1 the hatpWed1 to set
     */
    public void setHatpWed1(Integer hatpWed1) {
        this.hatpWed1 = hatpWed1;
    }

    /**
     * @return the hatpThu1
     */
    public Integer getHatpThu1() {
        return hatpThu1;
    }

    /**
     * @param hatpThu1 the hatpThu1 to set
     */
    public void setHatpThu1(Integer hatpThu1) {
        this.hatpThu1 = hatpThu1;
    }

    /**
     * @return the hatpFri1
     */
    public Integer getHatpFri1() {
        return hatpFri1;
    }

    /**
     * @param hatpFri1 the hatpFri1 to set
     */
    public void setHatpFri1(Integer hatpFri1) {
        this.hatpFri1 = hatpFri1;
    }

    /**
     * @return the hatpSat1
     */
    public Integer getHatpSat1() {
        return hatpSat1;
    }

    /**
     * @param hatpSat1 the hatpSat1 to set
     */
    public void setHatpSat1(Integer hatpSat1) {
        this.hatpSat1 = hatpSat1;
    }

    /**
     * @return the hatpSun1
     */
    public Integer getHatpSun1() {
        return hatpSun1;
    }

    /**
     * @param hatpSun1 the hatpSun1 to set
     */
    public void setHatpSun1(Integer hatpSun1) {
        this.hatpSun1 = hatpSun1;
    }

    /**
     * @return the thatpMon1
     */
    public Integer getThatpMon1() {
        return thatpMon1;
    }

    /**
     * @param thatpMon1 the thatpMon1 to set
     */
    public void setThatpMon1(Integer thatpMon1) {
        this.thatpMon1 = thatpMon1;
    }

    /**
     * @return the thatpTue1
     */
    public Integer getThatpTue1() {
        return thatpTue1;
    }

    /**
     * @param thatpTue1 the thatpTue1 to set
     */
    public void setThatpTue1(Integer thatpTue1) {
        this.thatpTue1 = thatpTue1;
    }

    /**
     * @return the thatpWed1
     */
    public Integer getThatpWed1() {
        return thatpWed1;
    }

    /**
     * @param thatpWed1 the thatpWed1 to set
     */
    public void setThatpWed1(Integer thatpWed1) {
        this.thatpWed1 = thatpWed1;
    }

    /**
     * @return the thatpThu1
     */
    public Integer getThatpThu1() {
        return thatpThu1;
    }

    /**
     * @param thatpThu1 the thatpThu1 to set
     */
    public void setThatpThu1(Integer thatpThu1) {
        this.thatpThu1 = thatpThu1;
    }

    /**
     * @return the thatpFri1
     */
    public Integer getThatpFri1() {
        return thatpFri1;
    }

    /**
     * @param thatpFri1 the thatpFri1 to set
     */
    public void setThatpFri1(Integer thatpFri1) {
        this.thatpFri1 = thatpFri1;
    }

    /**
     * @return the thatpSat1
     */
    public Integer getThatpSat1() {
        return thatpSat1;
    }

    /**
     * @param thatpSat1 the thatpSat1 to set
     */
    public void setThatpSat1(Integer thatpSat1) {
        this.thatpSat1 = thatpSat1;
    }

    /**
     * @return the thatpSun1
     */
    public Integer getThatpSun1() {
        return thatpSun1;
    }

    /**
     * @param thatpSun1 the thatpSun1 to set
     */
    public void setThatpSun1(Integer thatpSun1) {
        this.thatpSun1 = thatpSun1;
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

    /**
     * @return the workLtTei
     */
    public long getWorkLtTei() {
        return workLtTei;
    }

    /**
     * @param workLtTei the workLtTei to set
     */
    public void setWorkLtTei(long workLtTei) {
        this.workLtTei = workLtTei;
    }

    /**
     * @return the workLtTok
     */
    public long getWorkLtTok() {
        return workLtTok;
    }

    /**
     * @param workLtTok the workLtTok to set
     */
    public void setWorkLtTok(long workLtTok) {
        this.workLtTok = workLtTok;
    }

    /**
     * @return the cmnTantoCd
     */
    public String getCmnTantoCd() {
        return cmnTantoCd;
    }

    /**
     * @param cmnTantoCd the cmnTantoCd to set
     */
    public void setCmnTantoCd(String cmnTantoCd) {
        this.cmnTantoCd = cmnTantoCd;
    }

    /**
     * @return the cmnTermId
     */
    public String getCmnTermId() {
        return cmnTermId;
    }

    /**
     * @param cmnTermId the cmnTermId to set
     */
    public void setCmnTermId(String cmnTermId) {
        this.cmnTermId = cmnTermId;
    }

    /**
     * @return the cmnInsdd
     */
    public String getCmnInsdd() {
        return cmnInsdd;
    }

    /**
     * @param cmnInsdd the cmnInsdd to set
     */
    public void setCmnInsdd(String cmnInsdd) {
        this.cmnInsdd = cmnInsdd;
    }

    /**
     * @return the cmnUpddd
     */
    public String getCmnUpddd() {
        return cmnUpddd;
    }

    /**
     * @param cmnUpddd the cmnUpddd to set
     */
    public void setCmnUpddd(String cmnUpddd) {
        this.cmnUpddd = cmnUpddd;
    }

    /**
     * @return the cmnUpdtime
     */
    public String getCmnUpdtime() {
        return cmnUpdtime;
    }

    /**
     * @param cmnUpdtime the cmnUpdtime to set
     */
    public void setCmnUpdtime(String cmnUpdtime) {
        this.cmnUpdtime = cmnUpdtime;
    }

    /**
     * @return the flgNewRecord
     */
    public boolean isFlgNewRecord() {
        return flgNewRecord;
    }

    /**
     * @param flgNewRecord the flgNewRecord to set
     */
    public void setFlgNewRecord(boolean flgNewRecord) {
        this.flgNewRecord = flgNewRecord;
    }

    /**
     * @return the hiddenList
     */
    public List<String> getHiddenList() {
        return hiddenList;
    }

    /**
     * @param hiddenList the hiddenList to set
     */
    public void setHiddenList(List<String> hiddenList) {
        this.hiddenList = hiddenList;
    }
}
