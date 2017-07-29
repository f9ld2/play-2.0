// /////////////////////////////////////////////////////////////////////
// Copyright(C) 2015 NEC Solution Innovators, Ltd. All Rights Reserved.
// /////////////////////////////////////////////////////////////////////

/*
 * ====================================================================
 * 
 * 機能名称 :仕入状況一覧表
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
 * 仕入状況一覧表のDtoクラス
 * 
 */

public class Sijp7000Dto1 {
    /** 店舗 */
    private String tenCd;
    /** 店舗名 */
    private String tenNm;
    /** 取引先コード */
    private String trihikiCd;
    /** 取引先名称 */
    private String trihikiNm;
    /** 中分類 */
    private String cbnrCd;
    /** 部門コード */
    private String bmnCd;

    /** 発注種類区分1*/
    private String hatSruiKbn1;
    /** 発注数量1 */
    private BigDecimal hatBaraSu1;
    /** 仕入数量1 */
    private BigDecimal kenBaraSu1;
    /** 発注原価金額1 */
    private Long hatGenkKin1;
    /** 仕入原価金額1 */
    private Long kenGenkKin1;
    /** 発注売価金額1 */
    private Long hatBaikKin1;
    /** 仕入売価金額1 */
    private Long kenBaikKin1;
    
    /** 発注種類区分2*/
    private String hatSruiKbn2;
    /** 発注数量2 */
    private BigDecimal hatBaraSu2;
    /** 仕入数量2 */
    private BigDecimal kenBaraSu2;
    /** 発注原価金額2 */
    private Long hatGenkKin2;
    /** 仕入原価金額2 */
    private Long kenGenkKin2;
    /** 発注売価金額2 */
    private Long hatBaikKin2;
    /** 仕入売価金額2 */
    private Long kenBaikKin2;
    
    public Sijp7000Dto1() {
        hatBaraSu1 = BigDecimal.ZERO;
        kenBaraSu1 = BigDecimal.ZERO;
        hatGenkKin1 = new Long(0);
        kenGenkKin1 = new Long(0);
        hatBaikKin1 = new Long(0);
        kenBaikKin1 = new Long(0);
        hatBaraSu2 = BigDecimal.ZERO;
        kenBaraSu2 = BigDecimal.ZERO;
        hatGenkKin2 = new Long(0);
        kenGenkKin2 = new Long(0);
        hatBaikKin2 = new Long(0);
        kenBaikKin2 = new Long(0);
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
     * @return the trihikiCd
     */
    public String getTrihikiCd() {
        return trihikiCd;
    }
    /**
     * @param trihikiCd the trihikiCd to set
     */
    public void setTrihikiCd(String trihikiCd) {
        this.trihikiCd = trihikiCd;
    }
    /**
     * @return the trihikiNm
     */
    public String getTrihikiNm() {
        return trihikiNm;
    }
    /**
     * @param trihikiNm the trihikiNm to set
     */
    public void setTrihikiNm(String trihikiNm) {
        this.trihikiNm = trihikiNm;
    }
    /**
     * @return the cbnrCd
     */
    public String getCbnrCd() {
        return cbnrCd;
    }
    /**
     * @param cbnrCd the cbnrCd to set
     */
    public void setCbnrCd(String cbnrCd) {
        this.cbnrCd = cbnrCd;
    }
    /**
     * @return the hatSruiKbn1
     */
    public String getHatSruiKbn1() {
        return hatSruiKbn1;
    }
    /**
     * @param hatSruiKbn1 the hatSruiKbn1 to set
     */
    public void setHatSruiKbn1(String hatSruiKbn1) {
        this.hatSruiKbn1 = hatSruiKbn1;
    }
    /**
     * @return the hatBaraSu1
     */
    public BigDecimal getHatBaraSu1() {
        return hatBaraSu1;
    }
    /**
     * @param hatBaraSu1 the hatBaraSu1 to set
     */
    public void setHatBaraSu1(BigDecimal hatBaraSu1) {
        this.hatBaraSu1 = hatBaraSu1;
    }
    /**
     * @return the kenBaraSu1
     */
    public BigDecimal getKenBaraSu1() {
        return kenBaraSu1;
    }
    /**
     * @param kenBaraSu1 the kenBaraSu1 to set
     */
    public void setKenBaraSu1(BigDecimal kenBaraSu1) {
        this.kenBaraSu1 = kenBaraSu1;
    }
    /**
     * @return the hatGenkKin1
     */
    public Long getHatGenkKin1() {
        return hatGenkKin1;
    }
    /**
     * @param hatGenkKin1 the hatGenkKin1 to set
     */
    public void setHatGenkKin1(Long hatGenkKin1) {
        this.hatGenkKin1 = hatGenkKin1;
    }
    /**
     * @return the kenGenkKin1
     */
    public Long getKenGenkKin1() {
        return kenGenkKin1;
    }
    /**
     * @param kenGenkKin1 the kenGenkKin1 to set
     */
    public void setKenGenkKin1(Long kenGenkKin1) {
        this.kenGenkKin1 = kenGenkKin1;
    }
    /**
     * @return the hatBaikKin1
     */
    public Long getHatBaikKin1() {
        return hatBaikKin1;
    }
    /**
     * @param hatBaikKin1 the hatBaikKin1 to set
     */
    public void setHatBaikKin1(Long hatBaikKin1) {
        this.hatBaikKin1 = hatBaikKin1;
    }
    /**
     * @return the kenBaikKin1
     */
    public Long getKenBaikKin1() {
        return kenBaikKin1;
    }
    /**
     * @param kenBaikKin1 the kenBaikKin1 to set
     */
    public void setKenBaikKin1(Long kenBaikKin1) {
        this.kenBaikKin1 = kenBaikKin1;
    }
    /**
     * @return the hatSruiKbn2
     */
    public String getHatSruiKbn2() {
        return hatSruiKbn2;
    }
    /**
     * @param hatSruiKbn2 the hatSruiKbn2 to set
     */
    public void setHatSruiKbn2(String hatSruiKbn2) {
        this.hatSruiKbn2 = hatSruiKbn2;
    }
    /**
     * @return the hatBaraSu2
     */
    public BigDecimal getHatBaraSu2() {
        return hatBaraSu2;
    }
    /**
     * @param hatBaraSu2 the hatBaraSu2 to set
     */
    public void setHatBaraSu2(BigDecimal hatBaraSu2) {
        this.hatBaraSu2 = hatBaraSu2;
    }
    /**
     * @return the kenBaraSu2
     */
    public BigDecimal getKenBaraSu2() {
        return kenBaraSu2;
    }
    /**
     * @param kenBaraSu2 the kenBaraSu2 to set
     */
    public void setKenBaraSu2(BigDecimal kenBaraSu2) {
        this.kenBaraSu2 = kenBaraSu2;
    }
    /**
     * @return the hatGenkKin2
     */
    public Long getHatGenkKin2() {
        return hatGenkKin2;
    }
    /**
     * @param hatGenkKin2 the hatGenkKin2 to set
     */
    public void setHatGenkKin2(Long hatGenkKin2) {
        this.hatGenkKin2 = hatGenkKin2;
    }
    /**
     * @return the kenGenkKin2
     */
    public Long getKenGenkKin2() {
        return kenGenkKin2;
    }
    /**
     * @param kenGenkKin2 the kenGenkKin2 to set
     */
    public void setKenGenkKin2(Long kenGenkKin2) {
        this.kenGenkKin2 = kenGenkKin2;
    }
    /**
     * @return the hatBaikKin2
     */
    public Long getHatBaikKin2() {
        return hatBaikKin2;
    }
    /**
     * @param hatBaikKin2 the hatBaikKin2 to set
     */
    public void setHatBaikKin2(Long hatBaikKin2) {
        this.hatBaikKin2 = hatBaikKin2;
    }
    /**
     * @return the kenBaikKin2
     */
    public Long getKenBaikKin2() {
        return kenBaikKin2;
    }
    /**
     * @param kenBaikKin2 the kenBaikKin2 to set
     */
    public void setKenBaikKin2(Long kenBaikKin2) {
        this.kenBaikKin2 = kenBaikKin2;
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
}
