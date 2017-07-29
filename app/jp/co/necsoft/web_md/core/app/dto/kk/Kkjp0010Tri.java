///////////////////////////////////////////////////////////////////////
// Copyright(C) 2015 NEC Solution Innovators, Ltd. All Rights Reserved.
///////////////////////////////////////////////////////////////////////

/* ====================================================================
 * 機能名称 : KKJP0010Tri
 * 改版履歴
 * Rev. 改版年月日   改版者名       内容
 * 1.0   2014.04.07   Hungtb      新規作成
 * =================================================================== */
package jp.co.necsoft.web_md.core.app.dto.kk;

import play.data.validation.Constraints.MaxLength;
import play.data.validation.Constraints.MinLength;

/**
 * Data return for ResultForm {@link Kkjp0010ResultForm}.
 * 
 * @author hungtbz
 * @since 2014/03/13
 */
public class Kkjp0010Tri {
    /**No*/
    private Integer rowNo;

    /**確定区分*/
    @MinLength(value = 1)
    @MaxLength(value = 1)
    private String jotaiKbn;

    /**代表取引先コード*/
    @MinLength(value = 6)
    @MaxLength(value = 6)
    private String mainToriCd;

    /**代表取引先名称*/
    private String toriNm;

    /**金額*/
    private Long shrKin;

    /**消費税*/
    private Long sotoTaxKin;

    /**支払金額*/
    private Long shrKinAll;

    /**状態*/
    private String nm;

    /**action区分*/
    private Integer actKbn;

    /* New for save data */
    /**支払予定日*/
    private String shrDate;

    /** 支払伝票区分 */
    private String shrDenKbn;

    /**
     * @return the rowNo
     */
    public Integer getRowNo() {
        return rowNo;
    }

    /**
     * @param rowNo the rowNo to set
     */
    public void setRowNo(Integer rowNo) {
        this.rowNo = rowNo;
    }

    /**
     * @return the jotaiKbn
     */
    public String getJotaiKbn() {
        return jotaiKbn;
    }

    /**
     * @param jotaiKbn the jotaiKbn to set
     */
    public void setJotaiKbn(String jotaiKbn) {
        this.jotaiKbn = jotaiKbn;
    }

    /**
     * @return the mainToriCd
     */
    public String getMainToriCd() {
        return mainToriCd;
    }

    /**
     * @param mainToriCd the mainToriCd to set
     */
    public void setMainToriCd(String mainToriCd) {
        this.mainToriCd = mainToriCd;
    }

    /**
     * @return the toriNm
     */
    public String getToriNm() {
        return toriNm;
    }

    /**
     * @param toriNm the toriNm to set
     */
    public void setToriNm(String toriNm) {
        this.toriNm = toriNm;
    }

    /**
     * @return the shrKin
     */
    public Long getShrKin() {
        return shrKin;
    }

    /**
     * @param shrKin the shrKin to set
     */
    public void setShrKin(Long shrKin) {
        this.shrKin = shrKin;
    }

    /**
     * @return the sotoTaxKin
     */
    public Long getSotoTaxKin() {
        return sotoTaxKin;
    }

    /**
     * @param sotoTaxKin the sotoTaxKin to set
     */
    public void setSotoTaxKin(Long sotoTaxKin) {
        this.sotoTaxKin = sotoTaxKin;
    }

    /**
     * @return the shrKinAll
     */
    public Long getShrKinAll() {
        return shrKinAll;
    }

    /**
     * @param shrKinAll the shrKinAll to set
     */
    public void setShrKinAll(Long shrKinAll) {
        this.shrKinAll = shrKinAll;
    }

    /**
     * @return the nm
     */
    public String getNm() {
        return nm;
    }

    /**
     * @param nm the nm to set
     */
    public void setNm(String nm) {
        this.nm = nm;
    }

    /**
     * @return the actKbn
     */
    public Integer getActKbn() {
        return actKbn;
    }

    /**
     * @param actKbn the actKbn to set
     */
    public void setActKbn(Integer actKbn) {
        this.actKbn = actKbn;
    }

    /**
     * @return the shrDate
     */
    public String getShrDate() {
        return shrDate;
    }

    /**
     * @param shrDate the shrDate to set
     */
    public void setShrDate(String shrDate) {
        this.shrDate = shrDate;
    }

    /**
     * @return the shrDenKbn
     */
    public String getShrDenKbn() {
        return shrDenKbn;
    }

    /**
     * @param shrDenKbn the shrDenKbn to set
     */
    public void setShrDenKbn(String shrDenKbn) {
        this.shrDenKbn = shrDenKbn;
    }

}
