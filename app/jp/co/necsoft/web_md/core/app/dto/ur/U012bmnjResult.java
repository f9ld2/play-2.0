///////////////////////////////////////////////////////////////////////
// Copyright(C) 2015 NEC Solution Innovators, Ltd. All Rights Reserved.
///////////////////////////////////////////////////////////////////////

/* ====================================================================
 * 機能名称 : 日別店別部門別売上修正
 * 改版履歴
 * Rev. 改版年月日   改版者名       内容
 * 1.0   2014.04.6   PhucLT      新規作成
 * =================================================================== */

package jp.co.necsoft.web_md.core.app.dto.ur;

/**
*U012bmnjResultのDtoクラス
*
*/
public class U012bmnjResult {
    /**売上高*/
    private Long uriKin;
    
    /** 消費税（外）*/
    private Long tax;
    
    /** 消費税（内）*/
    private Long utiTax;

    /**客数*/
    private Integer kyaksu;

    /**点数*/
    private Integer uriSu;

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
     * @return the kyaksu
     */
    public Integer getKyaksu() {
        return kyaksu;
    }

    /**
     * @param kyaksu the kyaksu to set
     */
    public void setKyaksu(Integer kyaksu) {
        this.kyaksu = kyaksu;
    }

    /**
     * @return the uriSu
     */
    public Integer getUriSu() {
        return uriSu;
    }

    /**
     * @param uriSu the uriSu to set
     */
    public void setUriSu(Integer uriSu) {
        this.uriSu = uriSu;
    }

    /**
     * @return the tax
     */
    public Long getTax() {
        return tax;
    }

    /**
     * @param tax the tax to set
     */
    public void setTax(Long tax) {
        this.tax = tax;
    }

    /**
     * @return the utiTax
     */
    public Long getUtiTax() {
        return utiTax;
    }

    /**
     * @param utiTax the utiTax to set
     */
    public void setUtiTax(Long utiTax) {
        this.utiTax = utiTax;
    }
}
