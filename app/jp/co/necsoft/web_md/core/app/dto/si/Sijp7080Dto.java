// /////////////////////////////////////////////////////////////////////
// Copyright(C) 2015 NEC Solution Innovators, Ltd. All Rights Reserved.
// /////////////////////////////////////////////////////////////////////

/*
 * ====================================================================
 * 
 * 機能名称 :オリジナル商品品振確定指示画面
 * 
 * 改版履歴
 * 
 * Rev. 改版年月日 改版者名 内容
 * 
 * 1.0 2015.06.04 NECVN 新規作成
 * 
 * ===================================================================
 */

package jp.co.necsoft.web_md.core.app.dto.si;

import java.math.BigDecimal;
import java.util.List;

/**
 * オリジナル商品品振確定指示画面のDtoクラス
 * 
 */

public class Sijp7080Dto {
    
    /**JANコード*/
    private String janCd;
    /**メーカ品番*/
    private String mkdCd;
    /**商品名*/
    private String shnNm;
    /**発注数*/
    private BigDecimal hatSu;
    /**品振数*/
    private BigDecimal appHinSu;
    /**差*/
    private BigDecimal saSu;
    /**前日在庫*/
    private BigDecimal zikSu;
    /**原単価*/
    private BigDecimal genk;
    /**売単価*/
    private Integer baik;
    /**店舗行*/
    private List<Sijp7080DetailDto> detailList;
    /**line no*/
    private String rowNo;
    
    /**
     * get janCd
     * @return the janCd
     */
    public String getJanCd() {
        return janCd;
    }
    
    /**
     * set janCd
     * @param janCd the janCd to set
     */
    public void setJanCd(String janCd) {
        this.janCd = janCd;
    }
    
    /**
     * get mkdCd
     * @return the mkdCd
     */
    public String getMkdCd() {
        return mkdCd;
    }
    
    /**
     * set mkdCd
     * @param mkdCd the mkdCd to set
     */
    public void setMkdCd(String mkdCd) {
        this.mkdCd = mkdCd;
    }
    
    /**
     * get shnNm
     * @return the shnNm
     */
    public String getShnNm() {
        return shnNm;
    }
    
    /**
     * set shnNm
     * @param shnNm the shnNm to set
     */
    public void setShnNm(String shnNm) {
        this.shnNm = shnNm;
    }
    
    /**
     * get hatSu
     * @return the hatSu
     */
    public BigDecimal getHatSu() {
        return hatSu;
    }
    
    /**
     * set hatSu
     * @param hatSu the hatSu to set
     */
    public void setHatSu(BigDecimal hatSu) {
        this.hatSu = hatSu;
    }
    
    /**
     * get appHinSu
     * @return the appHinSu
     */
    public BigDecimal getAppHinSu() {
        return appHinSu;
    }
    
    /**
     * set appHinSu
     * @param appHinSu the appHinSu to set
     */
    public void setAppHinSu(BigDecimal appHinSu) {
        this.appHinSu = appHinSu;
    }
    
    /**
     * get saSu
     * @return the saSu
     */
    public BigDecimal getSaSu() {
        return saSu;
    }
    
    /**
     * set saSu
     * @param saSu the saSu to set
     */
    public void setSaSu(BigDecimal saSu) {
        this.saSu = saSu;
    }
    
    /**
     * get zikSu
     * @return the zikSu
     */
    public BigDecimal getZikSu() {
        return zikSu;
    }
    
    /**
     * set zikSu
     * @param zikSu the zikSu to set
     */
    public void setZikSu(BigDecimal zikSu) {
        this.zikSu = zikSu;
    }
    
    /**
     * get genk
     * @return the genk
     */
    public BigDecimal getGenk() {
        return genk;
    }
    
    /**
     * set genk
     * @param genk the genk to set
     */
    public void setGenk(BigDecimal genk) {
        this.genk = genk;
    }
    
    /**
     * get baik
     * @return the baik
     */
    public Integer getBaik() {
        return baik;
    }
    
    /**
     * set baik
     * @param baik the baik to set
     */
    public void setBaik(Integer baik) {
        this.baik = baik;
    }
    
    /**
     * get detailList
     * @return the detailList
     */
    public List<Sijp7080DetailDto> getDetailList() {
        return detailList;
    }
    
    /**
     * set detailList
     * @param detailList the detailList to set
     */
    public void setDetailList(List<Sijp7080DetailDto> detailList) {
        this.detailList = detailList;
    }
    
    /**
     * get rowNo
     * @return the rowNo
     */
    public String getRowNo() {
        return rowNo;
    }
    
    /**
     * set rowNo
     * @param rowNo the rowNo to set
     */
    public void setRowNo(String rowNo) {
        this.rowNo = rowNo;
    }
    
}
