package jp.co.necsoft.web_md.core.app.dto.ur;

/**
*U002ybmnM006tenmResultのDtoクラス
*
*/
public class U002ybmnM006tenmResult {
    /**予算年月*/
    private String bmnCd;

    /**売上*/
    private Long ysnUriKin;

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

}
