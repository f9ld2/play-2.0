// /////////////////////////////////////////////////////////////////////
// Copyright(C) 2015 NEC Solution Innovators, Ltd. All Rights Reserved.
// /////////////////////////////////////////////////////////////////////

/*
 * ====================================================================
 * 機能名称 : 棚卸情報入力
 * 改版履歴
 * Rev. 改版年月日 改版者名 内容
 * 1.0 2014.03.14 CuongPV 新規作成
 * ===================================================================
 */
package jp.co.necsoft.web_md.core.app.dto.zi;

import play.data.validation.Constraints.MaxLength;
import play.data.validation.Constraints.MinLength;
import play.data.validation.Constraints.Required;

/** 
 * 棚卸情報入力のDTOクラス
 */
public class Zijp0030BmnDto {

    /**部門コード*/
    @Required
    @MinLength(value = 5)
    @MaxLength(value = 5)
    private String bmnCd;

    /**部門名称*/
    @Required
    @MaxLength(value = 20)
    private String bmnNm;

    /**在庫計算方式*/
    @Required
    @MaxLength(value = 10)
    private String baiKanKbn;

    /**強制０在庫*/
    @Required
    private String zeroZai;

    /**４月*/
    private Short april;

    /**５月*/
    private Short may;

    /**６月*/
    private Short june;

    /**７月*/
    private Short july;

    /**８月*/
    private Short august;

    /**９月*/
    private Short september;

    /**10月*/
    private Short october;

    /**11月*/
    private Short november;

    /**12月*/
    private Short december;

    /**１月*/
    private Short january;

    /**２月*/
    private Short february;

    /**３月*/
    private Short march;

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
    * @return the baiKanKbn
    */
    public String getBaiKanKbn() {
        return baiKanKbn;
    }

    /**
    * @param baiKanKbn the baiKanKbn to set
    */
    public void setBaiKanKbn(String baiKanKbn) {
        this.baiKanKbn = baiKanKbn;
    }

    /**
    * @return the zeroZai
    */
    public String getZeroZai() {
        return zeroZai;
    }

    /**
    * @param zeroZai the zeroZai to set
    */
    public void setZeroZai(String zeroZai) {
        this.zeroZai = zeroZai;
    }

    /**
     * @return the april
     */
    public Short getApril() {
        return april;
    }

    /**
     * @param april the april to set
     */
    public void setApril(Short april) {
        this.april = april;
    }

    /**
     * @return the may
     */
    public Short getMay() {
        return may;
    }

    /**
     * @param may the may to set
     */
    public void setMay(Short may) {
        this.may = may;
    }

    /**
     * @return the june
     */
    public Short getJune() {
        return june;
    }

    /**
     * @param june the june to set
     */
    public void setJune(Short june) {
        this.june = june;
    }

    /**
     * @return the july
     */
    public Short getJuly() {
        return july;
    }

    /**
     * @param july the july to set
     */
    public void setJuly(Short july) {
        this.july = july;
    }

    /**
     * @return the august
     */
    public Short getAugust() {
        return august;
    }

    /**
     * @param august the august to set
     */
    public void setAugust(Short august) {
        this.august = august;
    }

    /**
     * @return the september
     */
    public Short getSeptember() {
        return september;
    }

    /**
     * @param september the september to set
     */
    public void setSeptember(Short september) {
        this.september = september;
    }

    /**
     * @return the october
     */
    public Short getOctober() {
        return october;
    }

    /**
     * @param october the october to set
     */
    public void setOctober(Short october) {
        this.october = october;
    }

    /**
     * @return the november
     */
    public Short getNovember() {
        return november;
    }

    /**
     * @param november the november to set
     */
    public void setNovember(Short november) {
        this.november = november;
    }

    /**
     * @return the december
     */
    public Short getDecember() {
        return december;
    }

    /**
     * @param december the december to set
     */
    public void setDecember(Short december) {
        this.december = december;
    }

    /**
     * @return the january
     */
    public Short getJanuary() {
        return january;
    }

    /**
     * @param january the january to set
     */
    public void setJanuary(Short january) {
        this.january = january;
    }

    /**
     * @return the february
     */
    public Short getFebruary() {
        return february;
    }

    /**
     * @param february the february to set
     */
    public void setFebruary(Short february) {
        this.february = february;
    }

    /**
     * @return the march
     */
    public Short getMarch() {
        return march;
    }

    /**
     * @param march the march to set
     */
    public void setMarch(Short march) {
        this.march = march;
    }
}
