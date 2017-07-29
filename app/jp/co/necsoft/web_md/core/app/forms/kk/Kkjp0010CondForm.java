///////////////////////////////////////////////////////////////////////
// Copyright(C) 2015 NEC Solution Innovators, Ltd. All Rights Reserved.
///////////////////////////////////////////////////////////////////////

/* ====================================================================
 * 機能名称 : ＥＯＳ伝票入力
 * 改版履歴
 * Rev. 改版年月日   改版者名       内容
 * 1.0   2014.04.07   Hungtb      新規作成
 * =================================================================== */
package jp.co.necsoft.web_md.core.app.forms.kk;

import play.data.validation.Constraints.MaxLength;
import play.data.validation.Constraints.MinLength;
import play.data.validation.Constraints.Required;

/**
*支払金額入力
*のResultFormクラス
*
*/
public class Kkjp0010CondForm {
    /**処理NO*/
    @MinLength(value = 6)
    @MaxLength(value = 6)
    private String shrSyoriNo;

    /**支払予定日*/
    @MinLength(value = 8)
    @MaxLength(value = 8)
    private String shrDate;

    /**締日*/
    @MinLength(value = 8)
    @MaxLength(value = 8)
    private String simeDate;

    /**会社*/
    @Required
    @MinLength(value = 2)
    @MaxLength(value = 2)
    private String kaisyaCd;

    /**取引項目*/
    @MinLength(value = 6)
    @MaxLength(value = 6)
    private String kakToriKmk;

    /** 状態 */
    @MaxLength(value = 40)
    private String toriNm;

    /**摘要*/
    @MaxLength(value = 30)
    private String tekiyo;

    // for save data
    /** 税区分 */
    private String taxKbn;

    /**
    * @return 処理NO
    */
    public String getShrSyoriNo() {
        return shrSyoriNo;
    }

    /**
    * @param shrSyoriNo 処理NO
    */
    public void setShrSyoriNo(String shrSyoriNo) {
        this.shrSyoriNo = shrSyoriNo;
    }

    /**
    * @return 支払予定日
    */
    public String getShrDate() {
        return shrDate;
    }

    /**
    * @param shrDate 支払予定日
    */
    public void setShrDate(String shrDate) {
        this.shrDate = shrDate;
    }

    /**
    * @return 締日
    */
    public String getSimeDate() {
        return simeDate;
    }

    /**
    * @param simeDate 締日
    */
    public void setSimeDate(String simeDate) {
        this.simeDate = simeDate;
    }

    /**
    * @return 会社
    */
    public String getKaisyaCd() {
        return kaisyaCd;
    }

    /**
    * @param kaisyaCd 会社
    */
    public void setKaisyaCd(String kaisyaCd) {
        this.kaisyaCd = kaisyaCd;
    }

    /**
    * @return 取引項目
    */
    public String getKakToriKmk() {
        return kakToriKmk;
    }

    /**
    * @param kakToriKmk 取引項目
    */
    public void setKakToriKmk(String kakToriKmk) {
        this.kakToriKmk = kakToriKmk;
    }

    /**
    * @return 状態
    */
    public String getToriNm() {
        return toriNm;
    }

    /**
    * @param toriNm 状態
    */
    public void setToriNm(String toriNm) {
        this.toriNm = toriNm;
    }

    /**
    * @return 摘要
    */
    public String getTekiyo() {
        return tekiyo;
    }

    /**
    * @param tekiyo 摘要
    */
    public void setTekiyo(String tekiyo) {
        this.tekiyo = tekiyo;
    }

    /**
     * @return 税区分
     */
    public String getTaxKbn() {
        return taxKbn;
    }

    /**
     * @param taxKbn 税区分
     */
    public void setTaxKbn(String taxKbn) {
        this.taxKbn = taxKbn;
    }

}

