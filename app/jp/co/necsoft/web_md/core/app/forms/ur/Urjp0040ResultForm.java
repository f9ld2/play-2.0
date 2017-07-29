///////////////////////////////////////////////////////////////////////
// Copyright(C) 2015 NEC Solution Innovators, Ltd. All Rights Reserved.
///////////////////////////////////////////////////////////////////////

/* ====================================================================
 * 機能名称 : 日別店別部門別売上修正
 * 改版履歴
 * Rev. 改版年月日   改版者名       内容
 * 1.0   2014.04.6   PhucLT      新規作成
 * =================================================================== */

package jp.co.necsoft.web_md.core.app.forms.ur;

import java.util.List;

import jp.co.necsoft.web_md.core.app.dto.ur.Urjp0040Dto;
import jp.co.necsoft.web_md.core.app.dto.ur.Urjp0040Grid2Dto;
import play.data.validation.Constraints.Max;
import play.data.validation.Constraints.MaxLength;
import play.data.validation.Constraints.Min;
import play.data.validation.Constraints.MinLength;

/**
*日別店別部門別売上修正のResultFormクラス
*
*/
public class Urjp0040ResultForm {
    /** insert */
    private boolean insert;

    /**kakuteiFlag*/
    private boolean kakuteiFlag;

    /**部門売上*/
    private List<Urjp0040Dto> bmnBaiyoArea;

    /** grid2 */
    private List<Urjp0040Grid2Dto> grid2Area;

    /**売上高*/
    @Max(value = 9999999999999L)
    @Min(value = 0L)
    private Long uriKin;
    
    /**外税額*/
    @Max(value = 9999999999999L)
    @Min(value = 0L)
    private Long tax;
    
    /**内税額*/
    @Max(value = 9999999999999L)
    @Min(value = 0L)
    private Long utiTax;

    /**客数*/
    @Max(value = 999999)
    @Min(value = -99999)
    private Integer tenKyakSu;

    /**天気*/
    @MaxLength(value = 2)
    @MinLength(value = 1)
    private String weather;

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
     * @return the kakuteiFlag
     */
    public boolean isKakuteiFlag() {
        return kakuteiFlag;
    }

    /**
     * @param kakuteiFlag the kakuteiFlag to set
     */
    public void setKakuteiFlag(boolean kakuteiFlag) {
        this.kakuteiFlag = kakuteiFlag;
    }

    /**
     * @return the bmnBaiyoArea
     */
    public List<Urjp0040Dto> getBmnBaiyoArea() {
        return bmnBaiyoArea;
    }

    /**
     * @param bmnBaiyoArea the bmnBaiyoArea to set
     */
    public void setBmnBaiyoArea(List<Urjp0040Dto> bmnBaiyoArea) {
        this.bmnBaiyoArea = bmnBaiyoArea;
    }

    /**
     * @return the grid2Area
     */
    public List<Urjp0040Grid2Dto> getGrid2Area() {
        return grid2Area;
    }

    /**
     * @param grid2Area the grid2Area to set
     */
    public void setGrid2Area(List<Urjp0040Grid2Dto> grid2Area) {
        this.grid2Area = grid2Area;
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
     * @return the tenKyakSu
     */
    public Integer getTenKyakSu() {
        return tenKyakSu;
    }

    /**
     * @param tenKyakSu the tenKyakSu to set
     */
    public void setTenKyakSu(Integer tenKyakSu) {
        this.tenKyakSu = tenKyakSu;
    }

    /**
     * @return the weather
     */
    public String getWeather() {
        return weather;
    }

    /**
     * @param weather the weather to set
     */
    public void setWeather(String weather) {
        this.weather = weather;
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
