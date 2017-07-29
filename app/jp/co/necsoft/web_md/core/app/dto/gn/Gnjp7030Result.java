///////////////////////////////////////////////////////////////////////
// Copyright(C) 2015 NEC Solution Innovators, Ltd. All Rights Reserved.
///////////////////////////////////////////////////////////////////////

/* ====================================================================
 * 機能名称 : デモ棚リスト
 * 改版履歴
 * Rev. 改版年月日   改版者名       内容
 * 1.0   2015-06-26 chiennt 新規作成
 * =================================================================== */
package jp.co.necsoft.web_md.core.app.dto.gn;

import java.math.BigDecimal;


/**
*Gnjp7030ResultのDtoクラス
*
*/
public class Gnjp7030Result {
    
    
    /**事業部名漢字*/
    private String jgyNm;
    
    /**店舗コード*/
    private String tenCd;
    
    /**店舗名漢字*/
    private String tenNm;
    
    /**部門名漢字*/
    private String bmnNm;
    
    /**中分類名漢字*/
    private String chuBurNm;
    
    /**小分類名漢字*/
    private String shoBurNm;
    
    /**デモ棚No*/
    private String demoTanaNo;
    
    /**デモ棚段*/
    private String demoTanaDan;
    
    /**デモ棚行No*/
    private String demoTanaSeq;
    
    /**ＪＡＮコード*/
    private String janCd;
    
    /**商品名漢字*/
    private String shnNm;
    
    /**規格漢字*/
    private String kikakuNm;
    
    /**取引先名漢字*/
    private String triNm;
    
    /**理論在庫数量*/
    private String rrnSu;
    
    /**指示数*/
    private String demoSijiSu;
    
    /**原単価*/
    private BigDecimal baik;
    
    /**売単価*/
    private Integer genk;

    /**
     * @return the jgyNm
     */
    public String getJgyNm() {
        return jgyNm;
    }

    /**
     * @param jgyNm the jgyNm to set
     */
    public void setJgyNm(String jgyNm) {
        this.jgyNm = jgyNm;
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
     * @return the tenNm
     */
    public String getTenNm() {
        return tenNm;
    }

    /**
     * @param tenNm the tenNm to set
     */
    public void setTenNm(String tenNm) {
        this.tenNm = tenNm;
    }

    /**
     * @return the bmnNm
     */
    public String getBmnNm() {
        return bmnNm;
    }

    /**
     * @param bmnNm the bmnNm to set
     */
    public void setBmnNm(String bmnNm) {
        this.bmnNm = bmnNm;
    }

    /**
     * @return the chuBurNm
     */
    public String getChuBurNm() {
        return chuBurNm;
    }

    /**
     * @param chuBurNm the chuBurNm to set
     */
    public void setChuBurNm(String chuBurNm) {
        this.chuBurNm = chuBurNm;
    }

    /**
     * @return the shoBurNm
     */
    public String getShoBurNm() {
        return shoBurNm;
    }

    /**
     * @param shoBurNm the shoBurNm to set
     */
    public void setShoBurNm(String shoBurNm) {
        this.shoBurNm = shoBurNm;
    }

    /**
     * @return the demoTanaNo
     */
    public String getDemoTanaNo() {
        return demoTanaNo;
    }

    /**
     * @param demoTanaNo the demoTanaNo to set
     */
    public void setDemoTanaNo(String demoTanaNo) {
        this.demoTanaNo = demoTanaNo;
    }

    /**
     * @return the demoTanaDan
     */
    public String getDemoTanaDan() {
        return demoTanaDan;
    }

    /**
     * @param demoTanaDan the demoTanaDan to set
     */
    public void setDemoTanaDan(String demoTanaDan) {
        this.demoTanaDan = demoTanaDan;
    }

    /**
     * @return the demoTanaSeq
     */
    public String getDemoTanaSeq() {
        return demoTanaSeq;
    }

    /**
     * @param demoTanaSeq the demoTanaSeq to set
     */
    public void setDemoTanaSeq(String demoTanaSeq) {
        this.demoTanaSeq = demoTanaSeq;
    }

    /**
     * @return the janCd
     */
    public String getJanCd() {
        return janCd;
    }

    /**
     * @param janCd the janCd to set
     */
    public void setJanCd(String janCd) {
        this.janCd = janCd;
    }

    /**
     * @return the shnNm
     */
    public String getShnNm() {
        return shnNm;
    }

    /**
     * @param shnNm the shnNm to set
     */
    public void setShnNm(String shnNm) {
        this.shnNm = shnNm;
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
     * @return the rrnSu
     */
    public String getRrnSu() {
        return rrnSu;
    }

    /**
     * @param rrnSu the rrnSu to set
     */
    public void setRrnSu(String rrnSu) {
        this.rrnSu = rrnSu;
    }

    /**
     * @return the demoSijiSu
     */
    public String getDemoSijiSu() {
        return demoSijiSu;
    }

    /**
     * @param demoSijiSu the demoSijiSu to set
     */
    public void setDemoSijiSu(String demoSijiSu) {
        this.demoSijiSu = demoSijiSu;
    }

    /**
     * @return the baik
     */
    public BigDecimal getBaik() {
        return baik;
    }

    /**
     * @param baik the baik to set
     */
    public void setBaik(BigDecimal baik) {
        this.baik = baik;
    }

    /**
     * @return the genk
     */
    public Integer getGenk() {
        return genk;
    }

    /**
     * @param genk the genk to set
     */
    public void setGenk(Integer genk) {
        this.genk = genk;
    }
    
}
