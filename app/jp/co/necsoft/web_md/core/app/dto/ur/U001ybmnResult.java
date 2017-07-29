package jp.co.necsoft.web_md.core.app.dto.ur;

/**
 * U001ybmnResultのDtoクラス
 * 
 */
public class U001ybmnResult {
    /**予算年月日*/
    private String ysnDate;

    /**部門コード*/
    private String bmnCd;

    /**売上*/
    private Long ysnUriKin;

    /**客数*/
    private Long ysnKyaksu;

    /**
     * @return the ysnDate
     */
    public String getYsnDate() {
        return ysnDate;
    }

    /**
     * @param ysnDate the ysnDate to set
     */
    public void setYsnDate(String ysnDate) {
        this.ysnDate = ysnDate;
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

    /**
     * @return the ysnUriKin
     */
    public Long getYsnUriKin() {
        return ysnUriKin;
    }

    /**
     * @param ysnUriKin the ysnUriKin to set
     */
    public void setYsnUriKin(Long ysnUriKin) {
        this.ysnUriKin = ysnUriKin;
    }

    /**
     * @return the ysnKyaksu
     */
    public Long getYsnKyaksu() {
        return ysnKyaksu;
    }

    /**
     * @param ysnKyaksu the ysnKyaksu to set
     */
    public void setYsnKyaksu(Long ysnKyaksu) {
        this.ysnKyaksu = ysnKyaksu;
    }

}
