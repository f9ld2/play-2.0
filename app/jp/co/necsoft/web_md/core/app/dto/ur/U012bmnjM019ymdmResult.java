///////////////////////////////////////////////////////////////////////
//Copyright(C) 2015 NEC Solution Innovators, Ltd. All Rights Reserved.
///////////////////////////////////////////////////////////////////////

/*
 * ====================================================================
 * 
 * 機能名称 : 売上日報
 * 
 * 改版履歴
 * 
 * Rev. 改版年月日 改版者名 内容
 * 
 * 1.0 20140301 CanhLT 新規作成
 * 
 * ===================================================================
 */
package jp.co.necsoft.web_md.core.app.dto.ur;
/**
 * 売上日報のDTOクラス
 *
 */
public class U012bmnjM019ymdmResult {
    /**店舗コード*/
    private String tenCd;
    /**年月日*/
    private String calDate;
    /**部門コード*/
    private String bmnCd;
    /**金額*/
    private String uriKin;
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
     * @return the calDate
     */
    public String getCalDate() {
        return calDate;
    }
    /**
     * @param calDate the calDate to set
     */
    public void setCalDate(String calDate) {
        this.calDate = calDate;
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
     * @return the uriKin
     */
    public String getUriKin() {
        return uriKin;
    }
    /**
     * @param uriKin the uriKin to set
     */
    public void setUriKin(String uriKin) {
        this.uriKin = uriKin;
    }
}
