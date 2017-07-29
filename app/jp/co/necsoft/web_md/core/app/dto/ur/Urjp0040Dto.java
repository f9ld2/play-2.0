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

import play.data.validation.Constraints.Max;
import play.data.validation.Constraints.Min;

/**
 * @author phuclt
 *
 */
public class Urjp0040Dto {
    /**部門コード*/
    private String bmnCd;

    /**部門名称*/
    private String bmnNm;

    /**売上高*/
    @Max(value = 999999999L)
    @Min(value = 0L)
    private Long uriKin;
    
    /**外税額*/
    @Max(value = 999999999L)
    @Min(value = 0L)
    private Long tax;
    
    /**内税額*/
    @Max(value = 999999999L)
    @Min(value = 0L)
    private Long utiTax;

    /**客数*/
    @Max(value = 999999)
    @Min(value = 0)
    private Integer kyaksu;

    /**点数*/
    @Max(value = 999999)
    @Min(value = 0)
    private Integer uriSu;

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
