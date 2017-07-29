// /////////////////////////////////////////////////////////////////////
// Copyright(C) 2015 NEC Solution Innovators, Ltd. All Rights Reserved.
// /////////////////////////////////////////////////////////////////////

/*
 * ====================================================================
 * 
 * 機能名称 : T001kksyChkTeiTokResultのDtoクラス
 * 
 * 改版履歴
 * 
 * Rev. 改版年月日 改版者名 内容
 * 
 * 1.0 2014/03/10 新規作成
 * 
 * ===================================================================
 */
package jp.co.necsoft.web_md.core.app.dto.cm;

import java.math.BigDecimal;

/**
*T001kksyChkTeiTokResultのDtoクラス
*
*/
public class T001kksyChkTeiTokResult {
    /**年度*/
    private String nendo;

    /**企画コード*/
    private String kikakuCd;

    /**部門コード*/
    private String bmnCd;

    /**仕入期間（開始日）*/
    private String siireFrdd;

    /**仕入期間（終了日）*/
    private String siireTodd;

    /**特売原単価*/
    private BigDecimal tokuGenk;

    /**特売売単価*/
    private Integer tokuBaik;

    /**リピート発注区分*/
    private String repeatKbn;

    /**特売取引先コード*/
    private String torihikiCd;

    /**
     * @return the nendo
     */
    public String getNendo() {
        return nendo;
    }

    /**
     * @param nendo the nendo to set
     */
    public void setNendo(String nendo) {
        this.nendo = nendo;
    }

    /**
     * @return the kikakuCd
     */
    public String getKikakuCd() {
        return kikakuCd;
    }

    /**
     * @param kikakuCd the kikakuCd to set
     */
    public void setKikakuCd(String kikakuCd) {
        this.kikakuCd = kikakuCd;
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
     * @return the siireFrdd
     */
    public String getSiireFrdd() {
        return siireFrdd;
    }

    /**
     * @param siireFrdd the siireFrdd to set
     */
    public void setSiireFrdd(String siireFrdd) {
        this.siireFrdd = siireFrdd;
    }

    /**
     * @return the siireTodd
     */
    public String getSiireTodd() {
        return siireTodd;
    }

    /**
     * @param siireTodd the siireTodd to set
     */
    public void setSiireTodd(String siireTodd) {
        this.siireTodd = siireTodd;
    }

    /**
     * @return the tokuGenk
     */
    public BigDecimal getTokuGenk() {
        return tokuGenk;
    }

    /**
     * @param tokuGenk the tokuGenk to set
     */
    public void setTokuGenk(BigDecimal tokuGenk) {
        this.tokuGenk = tokuGenk;
    }

    /**
     * @return the tokuBaik
     */
    public Integer getTokuBaik() {
        return tokuBaik;
    }

    /**
     * @param tokuBaik the tokuBaik to set
     */
    public void setTokuBaik(Integer tokuBaik) {
        this.tokuBaik = tokuBaik;
    }

    /**
     * @return the repeatKbn
     */
    public String getRepeatKbn() {
        return repeatKbn;
    }

    /**
     * @param repeatKbn the repeatKbn to set
     */
    public void setRepeatKbn(String repeatKbn) {
        this.repeatKbn = repeatKbn;
    }

    /**
     * @return the torihikiCd
     */
    public String getTorihikiCd() {
        return torihikiCd;
    }

    /**
     * @param torihikiCd the torihikiCd to set
     */
    public void setTorihikiCd(String torihikiCd) {
        this.torihikiCd = torihikiCd;
    }
}
