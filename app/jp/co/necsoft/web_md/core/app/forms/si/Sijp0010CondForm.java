///////////////////////////////////////////////////////////////////////
// Copyright(C) 2015 NEC Solution Innovators, Ltd. All Rights Reserved.
///////////////////////////////////////////////////////////////////////

/* ====================================================================
 * 機能名称 : ＥＯＳ伝票入力
 * 改版履歴
 * Rev. 改版年月日   改版者名       内容
 * 1.0   2014.04.07   Hungtb      新規作成
 * =================================================================== */
package jp.co.necsoft.web_md.core.app.forms.si;

import play.data.validation.Constraints.MaxLength;
import play.data.validation.Constraints.MinLength;
import play.data.validation.Constraints.Required;

/**
*ＥＯＳ伝票入力のConditionFormクラス
*
*/
public class Sijp0010CondForm {
    /**伝区*/
    @MaxLength(value = 2)
    private String dpyKbn;

    /**会社*/
    @MinLength(value = 2)
    @MaxLength(value = 2)
    private String kaisyaCd;

    /**事業部*/
    @MinLength(value = 2)
    @MaxLength(value = 2)
    private String jigyobuCd;

    /**伝票NO*/
    @MinLength(value = 9)
    @MaxLength(value = 9)
    private String dpyNo;

    /**店舗*/
    @MinLength(value = 3)
    @MaxLength(value = 3)
    private String tenCd;

    /**部門*/
    @MinLength(value = 3)
    @MaxLength(value = 3)
    private String bmnCd;

    /**納品ルート*/
    @MaxLength(value = 1)
    private String dctcKbn;

    /**納品日*/
    @MinLength(value = 8)
    @MaxLength(value = 8)
    private String nhnYmd;

    /**納品予定日*/
    @MinLength(value = 8)
    @MaxLength(value = 8)
    private String nhnYoteiYmd;

    /**発注日*/
    @MinLength(value = 8)
    @MaxLength(value = 8)
    private String hatYmd;

    /**取引先*/
    @MinLength(value = 9)
    @MaxLength(value = 9)
    private String triCd;

    /** 名称（漢字） */
    private String triNm;

    /** 検索 フラグ  */
    @Required
    private int searchFlg;

    /**
    * @return 伝区 the dpyKbn
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
    * @return 納品ルート the dctcKbn
    */
    public String getDctcKbn() {
        return dctcKbn;
    }

    /**
    * @param dctcKbn 納品ルート
    */
    public void setDctcKbn(String dctcKbn) {
        this.dctcKbn = dctcKbn;
    }

    /**
    * @return 納品日 the nhnYmd
    */
    public String getNhnYmd() {
        return nhnYmd;
    }

    /**
    * @param nhnYmd 納品日
    */
    public void setNhnYmd(String nhnYmd) {
        this.nhnYmd = nhnYmd;
    }

    /**
    * @return 納品予定日 the nhnYoteiYmd
    */
    public String getNhnYoteiYmd() {
        return nhnYoteiYmd;
    }

    /**
    * @param nhnYoteiYmd 納品予定日
    */
    public void setNhnYoteiYmd(String nhnYoteiYmd) {
        this.nhnYoteiYmd = nhnYoteiYmd;
    }

    /**
    * @return 発注日 the hatYmd
    */
    public String getHatYmd() {
        return hatYmd;
    }

    /**
    * @param hatYmd 発注日
    */
    public void setHatYmd(String hatYmd) {
        this.hatYmd = hatYmd;
    }

    /**
    * @return 取引先 the triCd
    */
    public String getTriCd() {
        return triCd;
    }

    /**
    * @param triCd 取引先
    */
    public void setTriCd(String triCd) {
        this.triCd = triCd;
    }

    /**
    * @return 名称（漢字） he triNm
    */
    public String getTriNm() {
        return triNm;
    }

    /**
    * @param triNm 名称（漢字）
    */
    public void setTriNm(String triNm) {
        this.triNm = triNm;
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
