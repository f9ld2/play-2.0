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

/**
 * オリジナル商品品振確定指示画面のDtoクラス
 * 
 */

public class Sijp7080DetailDto {
    /**JANコード*/
    private String janCd;
    /**メーカ品番*/
    private String mkdCd;
    /**商品名*/
    private String shnNm;
    /**発注日*/
    private String hatDD;
    /**店舗*/
    private String tenCd;
    /**店名*/
    private String tenNm;
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
    /**会社コード*/
    private String kaiCd;
    /**納品日*/
    private String nhnDd;
    /**発注種類区分*/
    private String hatSruiKbn;
    /**便*/
    private String bin;
    /**品振承認区分*/
    private String hinAppFlg;
    /**発注抽出フラグ*/
    private String hatChuFlg;
    /**及び発注締めフラグs*/
    private String hatSimeFlg;
    /**line no*/
    private String rowNo;
    
    /**
     * get hatDD
     * @return the hatDD
     */
    public String getHatDD() {
        return hatDD;
    }
    
    /**
     * get hinAppFlg
     * @return the hinAppFlg
     */
    public String getHinAppFlg() {
        return hinAppFlg;
    }
    
    /**
     * set hinAppFlg
     * @param hinAppFlg the hinAppFlg to set
     */
    public void setHinAppFlg(String hinAppFlg) {
        this.hinAppFlg = hinAppFlg;
    }
    
    /**
     * get hatChuFlg
     * @return the hatChuFlg
     */
    public String getHatChuFlg() {
        return hatChuFlg;
    }
    
    /**
     * set hatChuFlg
     * @param hatChuFlg the hatChuFlg to set
     */
    public void setHatChuFlg(String hatChuFlg) {
        this.hatChuFlg = hatChuFlg;
    }
    
    /**
     * get hatSimeFlg
     * @return the hatSimeFlg
     */
    public String getHatSimeFlg() {
        return hatSimeFlg;
    }
    
    /**
     * set hatSimeFlg
     * @param hatSimeFlg the hatSimeFlg to set
     */
    public void setHatSimeFlg(String hatSimeFlg) {
        this.hatSimeFlg = hatSimeFlg;
    }
    
    /**
     * set hatDD
     * @param hatDD the hatDD to set
     */
    public void setHatDD(String hatDD) {
        this.hatDD = hatDD;
    }
    
    /**
     * get tenCd
     * @return the tenCd
     */
    public String getTenCd() {
        return tenCd;
    }
    
    /**
     * set tenCd
     * @param tenCd the tenCd to set
     */
    public void setTenCd(String tenCd) {
        this.tenCd = tenCd;
    }
    
    /**
     * get tenNm
     * @return the tenNm
     */
    public String getTenNm() {
        return tenNm;
    }
    
    /**
     * set tenNm
     * @param tenNm the tenNm to set
     */
    public void setTenNm(String tenNm) {
        this.tenNm = tenNm;
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
     * get kaiCd
     * @return the kaiCd
     */
    public String getKaiCd() {
        return kaiCd;
    }
    
    /**
     * set kaiCd
     * @param kaiCd the kaiCd to set
     */
    public void setKaiCd(String kaiCd) {
        this.kaiCd = kaiCd;
    }
    
    /**
     * get nhnDd
     * @return the nhnDd
     */
    public String getNhnDd() {
        return nhnDd;
    }
    
    /**
     * set nhnDd
     * @param nhnDd the nhnDd to set
     */
    public void setNhnDd(String nhnDd) {
        this.nhnDd = nhnDd;
    }
    
    /**
     * get hatSruiKbn
     * @return the hatSruiKbn
     */
    public String getHatSruiKbn() {
        return hatSruiKbn;
    }
    
    /**
     * set hatSruiKbn
     * @param hatSruiKbn the hatSruiKbn to set
     */
    public void setHatSruiKbn(String hatSruiKbn) {
        this.hatSruiKbn = hatSruiKbn;
    }
    
    /**
     * get bin
     * @return the bin
     */
    public String getBin() {
        return bin;
    }
    
    /**
     * set bin
     * @param bin the bin to set
     */
    public void setBin(String bin) {
        this.bin = bin;
    }
    
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
