// /////////////////////////////////////////////////////////////////////
// Copyright(C) 2015 NEC Solution Innovators, Ltd. All Rights Reserved.
// /////////////////////////////////////////////////////////////////////

/* ====================================================================
 * 機能名称 : 移動伝票入力 
 * 改版履歴
 * Rev. 改版年月日   改版者名       内容
 * 1.0   2014.04.05   Hungtb      新規作成
 * =================================================================== */
package jp.co.necsoft.web_md.core.app.dto.si;

import java.math.BigDecimal;

import play.data.validation.Constraints.MaxLength;
import play.data.validation.Constraints.MinLength;

/**
 * @author hungtbz
 * @since 2014/03/21
 */
public class Sijp0040Msai {

    /**行*/
    private String mesaiNo;

    /**出荷店商品ｺｰﾄﾞ*/
    @MinLength(value = 13)
    @MaxLength(value = 13)
    private String outShnCd;

    /**入荷店商品ｺｰﾄﾞ*/
    @MinLength(value = 13)
    @MaxLength(value = 13)
    private String inShnCd;

    /**出荷店商品名称*/
    @MaxLength(value = 40)
    private String outShnNm;

    /**入荷店商品名称*/
    @MaxLength(value = 40)
    private String inShnNm;

    /**数量*/
    private BigDecimal kenBarasu;

    /**原単価*/
    private BigDecimal kenGenk;

    /**原価金額*/
    private Long kenGenkKin;

    /**売単価*/
    private Integer kenBaik;

    /**売価金額*/
    private Long kenBaikKin;

    /**Base on outShnCd: false if empty; true if not. */
    private boolean displayFlag;

    /** kppinRiyuKbn */
    private String kppinRiyuKbn;

    /**
    * @return 行
    */
    public String getMesaiNo() {
        return mesaiNo;
    }

    /**
    * @param mesaiNo 行
    */
    public void setMesaiNo(String mesaiNo) {
        this.mesaiNo = mesaiNo;
    }

    /**
    * @return 出荷店商品ｺｰﾄﾞ
    */
    public String getOutShnCd() {
        return outShnCd;
    }

    /**
    * @param outShnCd 出荷店商品ｺｰﾄﾞ
    */
    public void setOutShnCd(String outShnCd) {
        this.outShnCd = outShnCd;
    }

    /**
    * @return 入荷店商品ｺｰﾄﾞ
    */
    public String getInShnCd() {
        return inShnCd;
    }

    /**
    * @param inShnCd 入荷店商品ｺｰﾄﾞ
    */
    public void setInShnCd(String inShnCd) {
        this.inShnCd = inShnCd;
    }

    /**
    * @return 出荷店商品名称
    */
    public String getOutShnNm() {
        return outShnNm;
    }

    /**
    * @param outShnNm 出荷店商品名称
    */
    public void setOutShnNm(String outShnNm) {
        this.outShnNm = outShnNm;
    }

    /**
    * @return 入荷店商品名称
    */
    public String getInShnNm() {
        return inShnNm;
    }

    /**
    * @param shnNm 入荷店商品名称
    */
    public void setInShnNm(String shnNm) {
        this.inShnNm = shnNm;
    }

    /**
    * @return 数量
    */
    public BigDecimal getKenBarasu() {
        return kenBarasu;
    }

    /**
    * @param kenBarasu 数量
    */
    public void setKenBarasu(BigDecimal kenBarasu) {
        this.kenBarasu = kenBarasu;
    }

    /**
    * @return 原単価
    */
    public BigDecimal getKenGenk() {
        return kenGenk;
    }

    /**
    * @param kenGenk 原単価
    */
    public void setKenGenk(BigDecimal kenGenk) {
        this.kenGenk = kenGenk;
    }

    /**
    * @return 原価金額
    */
    public Long getKenGenkKin() {
        return kenGenkKin;
    }

    /**
    * @param kenGenkKin 原価金額
    */
    public void setKenGenkKin(Long kenGenkKin) {
        this.kenGenkKin = kenGenkKin;
    }

    /**
    * @return 売単価
    */
    public Integer getKenBaik() {
        return kenBaik;
    }

    /**
    * @param kenBaik 売単価
    */
    public void setKenBaik(Integer kenBaik) {
        this.kenBaik = kenBaik;
    }

    /**
    * @return 売価金額
    */
    public Long getKenBaikKin() {
        return kenBaikKin;
    }

    /**
    * @param kenBaikKin 売価金額
    */
    public void setKenBaikKin(Long kenBaikKin) {
        this.kenBaikKin = kenBaikKin;
    }

    /**
     * This flag use to check row is valid or not, data will be skip in compute if flag is false.
     * Value will be set when validate data in grid.
     * @return the displayFlag: false if empty; true if not
     */
    public boolean getDisplayFlag() {
        return displayFlag;
    }

    /**
     * This flag use to check row is valid or not, data will be skip in compute if flag is false.
     * Value will be set when validate data in grid.
     * @param displayFlag true if valid; false if not
     */
    public void setDisplayFlag(boolean displayFlag) {
        this.displayFlag = displayFlag;
    }

    /**
     * @return the kppinRiyuKbn
     */
    public String getKppinRiyuKbn() {
        return kppinRiyuKbn;
    }

    /**
     * @param kppinRiyuKbn the kppinRiyuKbn to set
     */
    public void setKppinRiyuKbn(String kppinRiyuKbn) {
        this.kppinRiyuKbn = kppinRiyuKbn;
    }
}
