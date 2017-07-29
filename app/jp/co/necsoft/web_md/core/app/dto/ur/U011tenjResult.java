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
*U011tenjResultのDtoクラス
*
*/
public class U011tenjResult {
    /** 客数*/
    private Integer kyaksu;

    /**天気*/
    private String weather;

    /**売上合計（税抜き）*/
    private Long uriKin;
    
    /**
     * @return the gnkUriKin
     */
    public Long getGnkUriKin() {
        return gnkUriKin;
    }

    /**
     * @param gnkUriKin the gnkUriKin to set
     */
    public void setGnkUriKin(Long gnkUriKin) {
        this.gnkUriKin = gnkUriKin;
    }

    /**
     * @return the creditUtiwake06
     */
    public Long getCreditUtiwake06() {
        return creditUtiwake06;
    }

    /**
     * @param creditUtiwake06 the creditUtiwake06 to set
     */
    public void setCreditUtiwake06(Long creditUtiwake06) {
        this.creditUtiwake06 = creditUtiwake06;
    }

    /**
     * @return the creditUtiwake05
     */
    public Long getCreditUtiwake05() {
        return creditUtiwake05;
    }

    /**
     * @param creditUtiwake05 the creditUtiwake05 to set
     */
    public void setCreditUtiwake05(Long creditUtiwake05) {
        this.creditUtiwake05 = creditUtiwake05;
    }

    /**
     * @return the creditUtiwake09
     */
    public Long getCreditUtiwake09() {
        return creditUtiwake09;
    }

    /**
     * @param creditUtiwake09 the creditUtiwake09 to set
     */
    public void setCreditUtiwake09(Long creditUtiwake09) {
        this.creditUtiwake09 = creditUtiwake09;
    }

    /**
     * @return the creditUtiwake07
     */
    public Long getCreditUtiwake07() {
        return creditUtiwake07;
    }

    /**
     * @param creditUtiwake07 the creditUtiwake07 to set
     */
    public void setCreditUtiwake07(Long creditUtiwake07) {
        this.creditUtiwake07 = creditUtiwake07;
    }

    /**
     * @return the creditUtiwake08
     */
    public Long getCreditUtiwake08() {
        return creditUtiwake08;
    }

    /**
     * @param creditUtiwake08 the creditUtiwake08 to set
     */
    public void setCreditUtiwake08(Long creditUtiwake08) {
        this.creditUtiwake08 = creditUtiwake08;
    }

    /**
     * @return the creditUtiwake14
     */
    public Long getCreditUtiwake14() {
        return creditUtiwake14;
    }

    /**
     * @param creditUtiwake14 the creditUtiwake14 to set
     */
    public void setCreditUtiwake14(Long creditUtiwake14) {
        this.creditUtiwake14 = creditUtiwake14;
    }

    /**
     * @return the creditUtiwakeSu06
     */
    public Integer getCreditUtiwakeSu06() {
        return creditUtiwakeSu06;
    }

    /**
     * @param creditUtiwakeSu06 the creditUtiwakeSu06 to set
     */
    public void setCreditUtiwakeSu06(Integer creditUtiwakeSu06) {
        this.creditUtiwakeSu06 = creditUtiwakeSu06;
    }

    /**
     * @return the creditUtiwakeSu05
     */
    public Integer getCreditUtiwakeSu05() {
        return creditUtiwakeSu05;
    }

    /**
     * @param creditUtiwakeSu05 the creditUtiwakeSu05 to set
     */
    public void setCreditUtiwakeSu05(Integer creditUtiwakeSu05) {
        this.creditUtiwakeSu05 = creditUtiwakeSu05;
    }

    /**
     * @return the creditUtiwakeSu09
     */
    public Integer getCreditUtiwakeSu09() {
        return creditUtiwakeSu09;
    }

    /**
     * @param creditUtiwakeSu09 the creditUtiwakeSu09 to set
     */
    public void setCreditUtiwakeSu09(Integer creditUtiwakeSu09) {
        this.creditUtiwakeSu09 = creditUtiwakeSu09;
    }

    /**
     * @return the creditUtiwakeSu07
     */
    public Integer getCreditUtiwakeSu07() {
        return creditUtiwakeSu07;
    }

    /**
     * @param creditUtiwakeSu07 the creditUtiwakeSu07 to set
     */
    public void setCreditUtiwakeSu07(Integer creditUtiwakeSu07) {
        this.creditUtiwakeSu07 = creditUtiwakeSu07;
    }

    /**
     * @return the creditUtiwakeSu08
     */
    public Integer getCreditUtiwakeSu08() {
        return creditUtiwakeSu08;
    }

    /**
     * @param creditUtiwakeSu08 the creditUtiwakeSu08 to set
     */
    public void setCreditUtiwakeSu08(Integer creditUtiwakeSu08) {
        this.creditUtiwakeSu08 = creditUtiwakeSu08;
    }

    /**
     * @return the creditUtiwakeSu14
     */
    public Integer getCreditUtiwakeSu14() {
        return creditUtiwakeSu14;
    }

    /**
     * @param creditUtiwakeSu14 the creditUtiwakeSu14 to set
     */
    public void setCreditUtiwakeSu14(Integer creditUtiwakeSu14) {
        this.creditUtiwakeSu14 = creditUtiwakeSu14;
    }

    /** 現金在高（金額） */
    private Long gnkUriKin;
    /** 商品券500 */
    private Long creditUtiwake06;
    /** 商品券1000 */
    private Long creditUtiwake05;
    /** 社内売上伝票 */
    private Long creditUtiwake09;
    /** 売掛伝票 */
    private Long creditUtiwake02;
    /** クレジット */
    private Long creditUtiwake01;
    /** ギフト券 */
    private Long creditUtiwake07;
    /** その他 */
    private Long creditUtiwake08;
    /** 消費税（金額） */
    private Long creditUtiwake14;
    /** 商品券500 */
    private Integer creditUtiwakeSu06;
    /** 商品券1000 */
    private Integer creditUtiwakeSu05;
    /** 社内売上伝票 */
    private Integer creditUtiwakeSu09;
    /** 売掛伝票 */
    private Integer creditUtiwakeSu02;
    /** クレジット */
    private Integer creditUtiwakeSu01;
    /** ギフト券 */
    private Integer creditUtiwakeSu07;
    /** 値引券 */
    private Integer creditUtiwakeSu08;
    /** その他 */
    private Integer creditUtiwakeSu14;
    /**消費税*/
    private Long tax;

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
     * @return the creditUtiwake01
     */
    public Long getCreditUtiwake01() {
        return creditUtiwake01;
    }

    /**
     * @param creditUtiwake01 the creditUtiwake01 to set
     */
    public void setCreditUtiwake01(Long creditUtiwake01) {
        this.creditUtiwake01 = creditUtiwake01;
    }

    /**
     * @return the creditUtiwake02
     */
    public Long getCreditUtiwake02() {
        return creditUtiwake02;
    }

    /**
     * @param creditUtiwake02 the creditUtiwake02 to set
     */
    public void setCreditUtiwake02(Long creditUtiwake02) {
        this.creditUtiwake02 = creditUtiwake02;
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
     * @return the creditUtiwakeSu01
     */
    public Integer getCreditUtiwakeSu01() {
        return creditUtiwakeSu01;
    }

    /**
     * @param creditUtiwakeSu01 the creditUtiwakeSu01 to set
     */
    public void setCreditUtiwakeSu01(Integer creditUtiwakeSu01) {
        this.creditUtiwakeSu01 = creditUtiwakeSu01;
    }

    /**
     * @return the creditUtiwakeSu02
     */
    public Integer getCreditUtiwakeSu02() {
        return creditUtiwakeSu02;
    }

    /**
     * @param creditUtiwakeSu02 the creditUtiwakeSu02 to set
     */
    public void setCreditUtiwakeSu02(Integer creditUtiwakeSu02) {
        this.creditUtiwakeSu02 = creditUtiwakeSu02;
    }
}
