///////////////////////////////////////////////////////////////////////
// Copyright(C) 2015 NEC Solution Innovators, Ltd. All Rights Reserved.
///////////////////////////////////////////////////////////////////////

/* ====================================================================
 * 機能名称 : _セット品設定リスト
 * 改版履歴
 * Rev. 改版年月日   改版者名       内容
 * 1.0   2015-06-23 chiennt 新規作成
 * =================================================================== */
package jp.co.necsoft.web_md.core.app.dto.gn;


/**
*Gnjp7000ResultのDtoクラス
*
*/
public class Gnjp7000Result {
    
    /**発効日のHeader*/
    private String hakkoDayHeader;
    
    /**発効日ToのHeader*/
    private String hakkoDayToHeader;
    
    /**商品[GTIN]のHeader*/
    private String janCdHeader;
    
    /**商品名のHeader*/
    private String shnNmHeader;
    
    /**個数のHeader*/
    private String suryoHeader;
    
    /**原単価のHeader*/
    private String stdGenkHeader;
    
    /**売単価のHeader*/
    private String stdBaikHeader;
    
    /**発効日のDetail*/
    private String hakkoDayDetail;
    
    /**発効日ToのDetail*/
    private String hakkoDayToDetail;
    
    /**商品[GTIN]のDetail*/
    private String janCdDetail;
    
    /**商品名のDetail*/
    private String shnNmDetail;
    
    /**個数のDetail*/
    private String suryoDetail;
    
    /**原単価のDetail*/
    private String stdGenkDetail;
    
    /**売単価のDetail*/
    private String stdBaikDetail;
    
    /**取引先名のDetail*/
    private String triNmDetail;
    
    /**メーカ品番のDetail*/
    private String makerHinCdDetail;

    /**
     * @return the hakkoDayHeader
     */
    public String getHakkoDayHeader() {
        return hakkoDayHeader;
    }

    /**
     * @param hakkoDayHeader the hakkoDayHeader to set
     */
    public void setHakkoDayHeader(String hakkoDayHeader) {
        this.hakkoDayHeader = hakkoDayHeader;
    }

    /**
     * @return the hakkoDayToHeader
     */
    public String getHakkoDayToHeader() {
        return hakkoDayToHeader;
    }

    /**
     * @param hakkoDayToHeader the hakkoDayToHeader to set
     */
    public void setHakkoDayToHeader(String hakkoDayToHeader) {
        this.hakkoDayToHeader = hakkoDayToHeader;
    }

    /**
     * @return the janCdHeader
     */
    public String getJanCdHeader() {
        return janCdHeader;
    }

    /**
     * @param janCdHeader the janCdHeader to set
     */
    public void setJanCdHeader(String janCdHeader) {
        this.janCdHeader = janCdHeader;
    }

    /**
     * @return the shnNmHeader
     */
    public String getShnNmHeader() {
        return shnNmHeader;
    }

    /**
     * @param shnNmHeader the shnNmHeader to set
     */
    public void setShnNmHeader(String shnNmHeader) {
        this.shnNmHeader = shnNmHeader;
    }

    /**
     * @return the suryoHeader
     */
    public String getSuryoHeader() {
        return suryoHeader;
    }

    /**
     * @param suryoHeader the suryoHeader to set
     */
    public void setSuryoHeader(String suryoHeader) {
        this.suryoHeader = suryoHeader;
    }

    /**
     * @return the stdGenkHeader
     */
    public String getStdGenkHeader() {
        return stdGenkHeader;
    }

    /**
     * @param stdGenkHeader the stdGenkHeader to set
     */
    public void setStdGenkHeader(String stdGenkHeader) {
        this.stdGenkHeader = stdGenkHeader;
    }

    /**
     * @return the stdBaikHeader
     */
    public String getStdBaikHeader() {
        return stdBaikHeader;
    }

    /**
     * @param stdBaikHeader the stdBaikHeader to set
     */
    public void setStdBaikHeader(String stdBaikHeader) {
        this.stdBaikHeader = stdBaikHeader;
    }

    /**
     * @return the hakkoDayDetail
     */
    public String getHakkoDayDetail() {
        return hakkoDayDetail;
    }

    /**
     * @param hakkoDayDetail the hakkoDayDetail to set
     */
    public void setHakkoDayDetail(String hakkoDayDetail) {
        this.hakkoDayDetail = hakkoDayDetail;
    }

    /**
     * @return the hakkoDayToDetail
     */
    public String getHakkoDayToDetail() {
        return hakkoDayToDetail;
    }

    /**
     * @param hakkoDayToDetail the hakkoDayToDetail to set
     */
    public void setHakkoDayToDetail(String hakkoDayToDetail) {
        this.hakkoDayToDetail = hakkoDayToDetail;
    }

    /**
     * @return the janCdDetail
     */
    public String getJanCdDetail() {
        return janCdDetail;
    }

    /**
     * @param janCdDetail the janCdDetail to set
     */
    public void setJanCdDetail(String janCdDetail) {
        this.janCdDetail = janCdDetail;
    }

    /**
     * @return the shnNmDetail
     */
    public String getShnNmDetail() {
        return shnNmDetail;
    }

    /**
     * @param shnNmDetail the shnNmDetail to set
     */
    public void setShnNmDetail(String shnNmDetail) {
        this.shnNmDetail = shnNmDetail;
    }

    /**
     * @return the suryoDetail
     */
    public String getSuryoDetail() {
        return suryoDetail;
    }

    /**
     * @param suryoDetail the suryoDetail to set
     */
    public void setSuryoDetail(String suryoDetail) {
        this.suryoDetail = suryoDetail;
    }

    /**
     * @return the stdGenkDetail
     */
    public String getStdGenkDetail() {
        return stdGenkDetail;
    }

    /**
     * @param stdGenkDetail the stdGenkDetail to set
     */
    public void setStdGenkDetail(String stdGenkDetail) {
        this.stdGenkDetail = stdGenkDetail;
    }

    /**
     * @return the stdBaikDetail
     */
    public String getStdBaikDetail() {
        return stdBaikDetail;
    }

    /**
     * @param stdBaikDetail the stdBaikDetail to set
     */
    public void setStdBaikDetail(String stdBaikDetail) {
        this.stdBaikDetail = stdBaikDetail;
    }

    /**
     * @return the triNmDetail
     */
    public String getTriNmDetail() {
        return triNmDetail;
    }

    /**
     * @param triNmDetail the triNmDetail to set
     */
    public void setTriNmDetail(String triNmDetail) {
        this.triNmDetail = triNmDetail;
    }

    /**
     * @return the makerHinCdDetail
     */
    public String getMakerHinCdDetail() {
        return makerHinCdDetail;
    }

    /**
     * @param makerHinCdDetail the makerHinCdDetail to set
     */
    public void setMakerHinCdDetail(String makerHinCdDetail) {
        this.makerHinCdDetail = makerHinCdDetail;
    }
    
    
}
