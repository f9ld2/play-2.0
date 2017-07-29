// /////////////////////////////////////////////////////////////////////
// Copyright(C) 2015 NEC Solution Innovators, Ltd. All Rights Reserved.
// /////////////////////////////////////////////////////////////////////

/*
 * ====================================================================
 * 
 * 機能名称 : ＥＯＳ発注明細リスト出力
 * 
 * 改版履歴
 * 
 * Rev. 改版年月日 改版者名 内容
 * 
 * 1.0 2014.04.25 Tinnc 新規作成
 * 
 * ===================================================================
 */

package jp.co.necsoft.web_md.core.app.forms.ha;

import play.data.validation.Constraints.MaxLength;

/**
*ＥＯＳ発注明細リスト出力のCondFormクラス
*
*/
public class Hajp1040CondForm {
    /**会社*/
    private String kaisyaCd;

    /**事業部*/
    @MaxLength(value = 2)
    private String jigyoubuCd;

    /**店舗(FR)*/
    @MaxLength(value = 3)
    private String tenCdSt;

    /**店舗(TO)*/
    @MaxLength(value = 3)
    private String tenCdEd;

    /**発注日(FR)*/
    private String hatYmdSt;

    /***/
    private String hatYmdEd;

    /**取引先(FR)*/
    @MaxLength(value = 9)
    private String torihikiCdSt;

    /***/
    private String torihikiNmSt;

    /**取引先(TO)*/
    @MaxLength(value = 9)
    private String torihikiCdEd;

    /***/
    private String torihikiNmEd;

    /**発注処理種別*/
    @MaxLength(value = 2)
    private String hatShoriKbn;

    /**
    * @return the kaisyaCd
    */
    public String getKaisyaCd() {
        return kaisyaCd;
    }

    /**
    * @param kaisyaCd the kaisyaCd to set
    */
    public void setKaisyaCd(String kaisyaCd) {
        this.kaisyaCd = kaisyaCd;
    }

    /**
    * @return the jigyoubuCd
    */
    public String getJigyoubuCd() {
        return jigyoubuCd;
    }

    /**
    * @param jigyoubuCd the jigyoubuCd to set
    */
    public void setJigyoubuCd(String jigyoubuCd) {
        this.jigyoubuCd = jigyoubuCd;
    }

    /**
    * @return the tenCdSt
    */
    public String getTenCdSt() {
        return tenCdSt;
    }

    /**
    * @param tenCdSt the tenCdSt to set
    */
    public void setTenCdSt(String tenCdSt) {
        this.tenCdSt = tenCdSt;
    }

    /**
    * @return the tenCdEd
    */
    public String getTenCdEd() {
        return tenCdEd;
    }

    /**
    * @param tenCdEd the tenCdEd to set
    */
    public void setTenCdEd(String tenCdEd) {
        this.tenCdEd = tenCdEd;
    }

    /**
    * @return the hatYmdSt
    */
    public String getHatYmdSt() {
        return hatYmdSt;
    }

    /**
    * @param hatYmdSt the hatYmdSt to set
    */
    public void setHatYmdSt(String hatYmdSt) {
        this.hatYmdSt = hatYmdSt;
    }

    /**
    * @return the hatYmdEd
    */
    public String getHatYmdEd() {
        return hatYmdEd;
    }

    /**
    * @param hatYmdEd the hatYmdEd to set
    */
    public void setHatYmdEd(String hatYmdEd) {
        this.hatYmdEd = hatYmdEd;
    }

    /**
    * @return the torihikiCdSt
    */
    public String getTorihikiCdSt() {
        return torihikiCdSt;
    }

    /**
    * @param torihikiCdSt the torihikiCdSt to set
    */
    public void setTorihikiCdSt(String torihikiCdSt) {
        this.torihikiCdSt = torihikiCdSt;
    }

    /**
    * @return the torihikiNmSt
    */
    public String getTorihikiNmSt() {
        return torihikiNmSt;
    }

    /**
    * @param torihikiNmSt the torihikiNmSt to set
    */
    public void setTorihikiNmSt(String torihikiNmSt) {
        this.torihikiNmSt = torihikiNmSt;
    }

    /**
    * @return the torihikiCdEd
    */
    public String getTorihikiCdEd() {
        return torihikiCdEd;
    }

    /**
    * @param torihikiCdEd the torihikiCdEd to set
    */
    public void setTorihikiCdEd(String torihikiCdEd) {
        this.torihikiCdEd = torihikiCdEd;
    }

    /**
    * @return the torihikiNmEd
    */
    public String getTorihikiNmEd() {
        return torihikiNmEd;
    }

    /**
    * @param torihikiNmEd the torihikiNmEd to set
    */
    public void setTorihikiNmEd(String torihikiNmEd) {
        this.torihikiNmEd = torihikiNmEd;
    }

    /**
    * @return the hatShoriKbn
    */
    public String getHatShoriKbn() {
        return hatShoriKbn;
    }

    /**
    * @param hatShoriKbn the hatShoriKbn to set
    */
    public void setHatShoriKbn(String hatShoriKbn) {
        this.hatShoriKbn = hatShoriKbn;
    }
}
