// /////////////////////////////////////////////////////////////////////
// Copyright(C) 2015 NEC Solution Innovators, Ltd. All Rights Reserved.
// /////////////////////////////////////////////////////////////////////

/*
 * ====================================================================
 * 
 * 機能名称 :自動発注予定データ一覧（店舗別）
 * 
 * 改版履歴
 * 
 * Rev. 改版年月日 改版者名 内容
 * 
 * 1.0 2015-06-04 NECVN 新規作成
 * 
 * ===================================================================
 */

package jp.co.necsoft.web_md.core.app.dto.ha;

import java.math.BigDecimal;

import play.data.validation.Constraints.Required;


/**
 * 自動発注予定データ一覧（店舗別）のDtoクラス
 * 
 */

public class Hajp7020Dto {
    /**店舗*/
    @Required
    private String tenCd;

    /**店舗名*/
    @Required
    private String tenNm;

    /**取引先*/
    @Required
    private String triCd;

    /**取引先名*/
    @Required
    private String triNm;

    /**予定数量**/
    private BigDecimal yoteSuryo;

    /**予定金額**/
    private BigDecimal yoteKingaku;

    /**表示区分**/
    private String hyojiKbn;

    /**
     * @return the triCd
     */
    public String getTriCd() {
        return triCd;
    }

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
     * @return the tenNm
     */
    public String getTenNm() {
        return tenNm;
    }

    /**
     * @param tenNm the tenNm to set
     */
    public void setTenNm(String tenNm) {
        this.tenNm = tenNm;
    }

    /**
     * @return the triNm
     */
    public String getTriNm() {
        return triNm;
    }

    /**
     * @param triNm the triNm to set
     */
    public void setTriNm(String triNm) {
        this.triNm = triNm;
    }

    /**
     * @return the yoteSuryo
     */
    public BigDecimal getYoteSuryo() {
        return yoteSuryo;
    }

    /**
     * @param yoteSuryo the yoteSuryo to set
     */
    public void setYoteSuryo(BigDecimal yoteSuryo) {
        this.yoteSuryo = yoteSuryo;
    }

    /**
     * @return the yoteKingaku
     */
    public BigDecimal getYoteKingaku() {
        return yoteKingaku;
    }

    /**
     * @param yoteKingaku the yoteKingaku to set
     */
    public void setYoteKingaku(BigDecimal yoteKingaku) {
        this.yoteKingaku = yoteKingaku;
    }

    /**
     * @param triCd the triCd to set
     */
    public void setTriCd(String triCd) {
        this.triCd = triCd;
    }

    /**
     * @return the hyojiKbn
     */
    public String getHyojiKbn() {
        return hyojiKbn;
    }

    /**
     * @param hyojiKbn the hyojiKbn to set
     */
    public void setHyojiKbn(String hyojiKbn) {
        this.hyojiKbn = hyojiKbn;
    }
}
