///////////////////////////////////////////////////////////////////////
// Copyright(C) 2015 NEC Solution Innovators, Ltd. All Rights Reserved.
///////////////////////////////////////////////////////////////////////

/* ====================================================================
 * 機能名称 : 移動伝票入力
 * 改版履歴
 * Rev. 改版年月日   改版者名       内容
 * 1.0   2014.04.05   Hungtb      新規作成
 * =================================================================== */
package jp.co.necsoft.web_md.core.app.forms.si;

import play.data.validation.Constraints.MaxLength;
import play.data.validation.Constraints.MinLength;
import play.data.validation.Constraints.Required;

/**
 * @author hungtb
 * @since 2014/03/18
 */
public class Sijp0040CondForm {
    /**伝区*/
    @MaxLength(value = 2)
    private String dpyKbn;

    /**伝票NO*/
    @MinLength(value = 9)
    @MaxLength(value = 9)
    private String dpyNo;

    /**出荷日*/
    @MinLength(value = 8)
    @MaxLength(value = 8)
    private String syukaYmd;

    /**会社*/
    @MinLength(value = 2)
    @MaxLength(value = 2)
    private String kaisyaCd;

    /**事業部*/
    @MinLength(value = 2)
    @MaxLength(value = 2)
    private String jigyobuCd;

    /**店舗*/
    @MinLength(value = 3)
    @MaxLength(value = 3)
    private String tenCd;

    /**部門*/
    @MinLength(value = 3)
    @MaxLength(value = 3)
    private String bmnCd;

    /**入荷店.会社*/
    @MinLength(value = 2)
    @MaxLength(value = 2)
    private String subKaisyaCd;

    /**入荷店.事業部*/
    @MinLength(value = 2)
    @MaxLength(value = 2)
    private String subJigyobuCd;

    /**入荷店.店舗*/
    @MinLength(value = 3)
    @MaxLength(value = 3)
    private String subTenCd;

    /**入荷店.部門*/
    @MinLength(value = 3)
    @MaxLength(value = 3)
    private String subBmnCd;

    /** 検索 フラグ  */
    @Required
    private int searchFlg;

    /**
     * @return 伝区
     */
    public String getDpyKbn() {
        return dpyKbn;
    }

    /**
     * @param dpyKbn 伝区
     */
    public void setDpyKbn(String dpyKbn) {
        this.dpyKbn = dpyKbn;
    }

    /**
     * @return 伝票NO the dpyNo
     */
    public String getDpyNo() {
        return dpyNo;
    }

    /**
     * @param dpyNo 伝票NO
     */
    public void setDpyNo(String dpyNo) {
        this.dpyNo = dpyNo;
    }

    /**
     * @return 出荷日 the syukaYmd
     */
    public String getSyukaYmd() {
        return syukaYmd;
    }

    /**
     * @param syukaYmd 出荷日
     */
    public void setSyukaYmd(String syukaYmd) {
        this.syukaYmd = syukaYmd;
    }

    /**
     * @return 会社 the kaisyaCd 
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
     * @return 事業部 the jigyobuCd
     */
    public String getJigyobuCd() {
        return jigyobuCd;
    }

    /**
     * @param jigyobuCd 事業部
     */
    public void setJigyobuCd(String jigyobuCd) {
        this.jigyobuCd = jigyobuCd;
    }

    /**
     * @return 店舗 the tenCd
     */
    public String getTenCd() {
        return tenCd;
    }

    /**
     * @param tenCd 店舗
     */
    public void setTenCd(String tenCd) {
        this.tenCd = tenCd;
    }

    /**
     * @return 部門 the bmnCd
     */
    public String getBmnCd() {
        return bmnCd;
    }

    /**
     * @param bmnCd 部門
     */
    public void setBmnCd(String bmnCd) {
        this.bmnCd = bmnCd;
    }

    /**
     * @return 入荷店.会社 the subKaisyaCd
     */
    public String getSubKaisyaCd() {
        return subKaisyaCd;
    }

    /**
     * @param subKaisyaCd 入荷店.会社
     */
    public void setSubKaisyaCd(String subKaisyaCd) {
        this.subKaisyaCd = subKaisyaCd;
    }

    /**
     * @return 入荷店.事業部 the subJigyobuCd
     */
    public String getSubJigyobuCd() {
        return subJigyobuCd;
    }

    /**
     * @param subJigyobuCd 入荷店.事業部
     */
    public void setSubJigyobuCd(String subJigyobuCd) {
        this.subJigyobuCd = subJigyobuCd;
    }

    /**
     * @return 入荷店.店舗 the subTenCd
     */
    public String getSubTenCd() {
        return subTenCd;
    }

    /**
     * @param subTenCd 入荷店.店舗
     */
    public void setSubTenCd(String subTenCd) {
        this.subTenCd = subTenCd;
    }

    /**
     * @return 入荷店.部門 the subBmnCd
     */
    public String getSubBmnCd() {
        return subBmnCd;
    }

    /**
     * @param subBmnCd 入荷店.部門
     */
    public void setSubBmnCd(String subBmnCd) {
        this.subBmnCd = subBmnCd;
    }

    /**
     * @return 検索 フラグ the searchFlg
     */
    public int getSearchFlg() {
        return searchFlg;
    }

    /**
     * @param searchFlg 検索 フラグ
     */
    public void setSearchFlg(int searchFlg) {
        this.searchFlg = searchFlg;
    }
}
