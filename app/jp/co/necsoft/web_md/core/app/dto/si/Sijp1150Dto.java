// /////////////////////////////////////////////////////////////////////
// Copyright(C) 2015 NEC Solution Innovators, Ltd. All Rights Reserved.
// /////////////////////////////////////////////////////////////////////

/*
 * ====================================================================
 * 
 * 機能名称 : 伝票ヘッダ照会
 * 
 * 改版履歴
 * 
 * Rev. 改版年月日 改版者名 内容
 * 
 * 1.0 2014-05-26 TuanTQ 新規作成
 * 
 * ===================================================================
 */
package jp.co.necsoft.web_md.core.app.dto.si;

import java.util.List;

/**
*Sijp1140DtoのDtoクラス
*
*/
public class Sijp1150Dto {
    /**会社コード*/
    private String kaisyaCd;

    /**事業部コード*/
    private String jigyobuCd;

    /**伝区DB種別*/
    private String denKbnDb;

    /**伝票区分*/
    private String denKbn;

    /**店舗コードリスト*/
    private List<String> tenCds;

    /**部門コードリスト*/
    private List<String> bmnCds;

    /**取引先リスト*/
    private List<String> torisakis;

    /**納品日（開始日）*/
    private String nhnDateSt;

    /**納品日（終了日）*/
    private String nhnDateEd;

    /**納品予定日（開始日）*/
    private String nhnYDateSt;

    /**納品予定日（終了日）*/
    private String nhnYDateEd;

    /**計時日付（開始日）*/
    private String keijDateSt;

    /**計時日付（終了日）*/
    private String keijDateEd;

    /**移動伝票区分*/
    private String idoDenKbn;

    /**各区分*/
    private String kakuKbn;

    /**フラグ*/
    private String flag;

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
     * @return the jigyobuCd
     */
    public String getJigyobuCd() {
        return jigyobuCd;
    }

    /**
     * @param jigyobuCd the jigyobuCd to set
     */
    public void setJigyobuCd(String jigyobuCd) {
        this.jigyobuCd = jigyobuCd;
    }

    /**
     * @return the denKbn
     */
    public String getDenKbn() {
        return denKbn;
    }

    /**
     * @param denKbn the denKbn to set
     */
    public void setDenKbn(String denKbn) {
        this.denKbn = denKbn;
    }

    /**
     * @return the tenCds
     */
    public List<String> getTenCds() {
        return tenCds;
    }

    /**
     * @param tenCds the tenCds to set
     */
    public void setTenCds(List<String> tenCds) {
        this.tenCds = tenCds;
    }

    /**
     * @return the bmnCds
     */
    public List<String> getBmnCds() {
        return bmnCds;
    }

    /**
     * @param bmnCds the bmnCds to set
     */
    public void setBmnCds(List<String> bmnCds) {
        this.bmnCds = bmnCds;
    }

    /**
     * @return the torisakis
     */
    public List<String> getTorisakis() {
        return torisakis;
    }

    /**
     * @param torisakis the torisakis to set
     */
    public void setTorisakis(List<String> torisakis) {
        this.torisakis = torisakis;
    }

    /**
     * @return the nhnDateSt
     */
    public String getNhnDateSt() {
        return nhnDateSt;
    }

    /**
     * @param nhnDateSt the nhnDateSt to set
     */
    public void setNhnDateSt(String nhnDateSt) {
        this.nhnDateSt = nhnDateSt;
    }

    /**
     * @return the nhnDateEd
     */
    public String getNhnDateEd() {
        return nhnDateEd;
    }

    /**
     * @param nhnDateEd the nhnDateEd to set
     */
    public void setNhnDateEd(String nhnDateEd) {
        this.nhnDateEd = nhnDateEd;
    }

    /**
     * @return the denKbnDb
     */
    public String getDenKbnDb() {
        return denKbnDb;
    }

    /**
     * @param denKbnDb the denKbnDb to set
     */
    public void setDenKbnDb(String denKbnDb) {
        this.denKbnDb = denKbnDb;
    }

    /**
     * @return the nhnYDateSt
     */
    public String getNhnYDateSt() {
        return nhnYDateSt;
    }

    /**
     * @param nhnYDateSt the nhnYDateSt to set
     */
    public void setNhnYDateSt(String nhnYDateSt) {
        this.nhnYDateSt = nhnYDateSt;
    }

    /**
     * @return the nhnYDateEd
     */
    public String getNhnYDateEd() {
        return nhnYDateEd;
    }

    /**
     * @param nhnYDateEd the nhnYDateEd to set
     */
    public void setNhnYDateEd(String nhnYDateEd) {
        this.nhnYDateEd = nhnYDateEd;
    }

    /**
     * @return the keijDateSt
     */
    public String getKeijDateSt() {
        return keijDateSt;
    }

    /**
     * @param keijDateSt the keijDateSt to set
     */
    public void setKeijDateSt(String keijDateSt) {
        this.keijDateSt = keijDateSt;
    }

    /**
     * @return the keijDateEd
     */
    public String getKeijDateEd() {
        return keijDateEd;
    }

    /**
     * @param keijDateEd the keijDateEd to set
     */
    public void setKeijDateEd(String keijDateEd) {
        this.keijDateEd = keijDateEd;
    }

    /**
     * @return the kakuKbn
     */
    public String getKakuKbn() {
        return kakuKbn;
    }

    /**
     * @param kakuKbn the kakuKbn to set
     */
    public void setKakuKbn(String kakuKbn) {
        this.kakuKbn = kakuKbn;
    }

    /**
     * @return the flag
     */
    public String getFlag() {
        return flag;
    }

    /**
     * @param flag the flag to set
     */
    public void setFlag(String flag) {
        this.flag = flag;
    }

    /**
     * @return the idoDenKbn
     */
    public String getIdoDenKbn() {
        return idoDenKbn;
    }

    /**
     * @param idoDenKbn the idoDenKbn to set
     */
    public void setIdoDenKbn(String idoDenKbn) {
        this.idoDenKbn = idoDenKbn;
    }

}
