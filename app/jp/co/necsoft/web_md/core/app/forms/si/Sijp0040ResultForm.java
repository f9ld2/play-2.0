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

import java.util.List;

import jp.co.necsoft.web_md.core.app.dto.si.Sijp0040Msai;

import play.data.validation.Constraints.MaxLength;
import play.data.validation.Constraints.MinLength;
import play.data.validation.Constraints.Required;

/**
*移動伝票入力
のResultFormクラス
*
*/
public class Sijp0040ResultForm {
    /* Condtion form. */
    /**伝区*/
    @Required
    @MaxLength(value = 2)
    private String dpyKbn;

    /**伝票NO*/
    @Required
    @MinLength(value = 9)
    @MaxLength(value = 9)
    private String dpyNo;

    /**出荷日*/
    @MinLength(value = 8)
    @MaxLength(value = 8)
    private String syukaYmd;

    /**会社*/
    @Required
    @MinLength(value = 2)
    @MaxLength(value = 2)
    private String kaisyaCd;

    /**事業部*/
    @Required
    @MinLength(value = 2)
    @MaxLength(value = 2)
    private String jigyobuCd;

    /**店舗*/
    @Required
    @MinLength(value = 3)
    @MaxLength(value = 3)
    private String tenCd;

    /**部門*/
    @Required
    @MinLength(value = 3)
    @MaxLength(value = 3)
    private String bmnCd;

    /**会社*/
    @Required
    @MinLength(value = 2)
    @MaxLength(value = 2)
    private String subKaisyaCd;

    /**事業部*/
    @Required
    @MinLength(value = 2)
    @MaxLength(value = 2)
    private String subJigyobuCd;

    /**店舗*/
    @Required
    @MinLength(value = 3)
    @MaxLength(value = 3)
    private String subTenCd;

    /**部門*/
    @Required
    @MinLength(value = 3)
    @MaxLength(value = 3)
    private String subBmnCd;

    /* Result form. */
    /**処理状態*/
    @MinLength(value = 0)
    @MaxLength(value = 1)
    private String syoriStsKbn;

    /**処理状態名*/
    @MinLength(value = 0)
    @MaxLength(value = 10)
    private String syoriStsKbnNm;

    // Grid data
    /**伝票明細*/
    private List<Sijp0040Msai> dataArea;

    // Footer grid
    /**原価金額合計*/
    private Long sumKenGenkIn;

    /**売価金額合計*/
    private Long sumKenBaikKin;

    /**(入力)原価金額合計*/
    @Required
    private Long inpGenInp;

    /**摘要*/
    @MinLength(value = 0)
    @MaxLength(value = 30)
    private String tekiyo;

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
     * @return 処理状態 the syoriStsKbn
     */
    public String getSyoriStsKbn() {
        return syoriStsKbn;
    }

    /**
     * @param syoriStsKbn 処理状態
     */
    public void setSyoriStsKbn(String syoriStsKbn) {
        this.syoriStsKbn = syoriStsKbn;
    }

    /**
     * @return syoriStsKbnNm 処理状態名
     */
    public String getSyoriStsKbnNm() {
        return syoriStsKbnNm;
    }

    /**
    * @param syoriStsKbnNm 処理状態名
    */
    public void setSyoriStsKbnNm(String syoriStsKbnNm) {
        this.syoriStsKbnNm = syoriStsKbnNm;
    }

    /**
    * @return 伝票明細
    */
    public List<Sijp0040Msai> getDataArea() {
        return dataArea;
    }

    /**
    * @param msaiDenpyoArea 伝票明細
    */
    public void setDataArea(List<Sijp0040Msai> msaiDenpyoArea) {
        this.dataArea = msaiDenpyoArea;
    }

    /**
    * @return 原価金額合計
    */
    public Long getSumKenGenkIn() {
        return sumKenGenkIn;
    }

    /**
    * @param sumKenGenkIn 原価金額合計
    */
    public void setSumKenGenkIn(Long sumKenGenkIn) {
        this.sumKenGenkIn = sumKenGenkIn;
    }

    /**
    * @return 売価金額合計
    */
    public Long getSumKenBaikKin() {
        return sumKenBaikKin;
    }

    /**
    * @param sKenBaikKin 売価金額合計
    */
    public void setSumKenBaikKin(Long sKenBaikKin) {
        this.sumKenBaikKin = sKenBaikKin;
    }

    /**
    * @return (入力)原価金額合計
    */
    public Long getInpGenInp() {
        return inpGenInp;
    }

    /**
    * @param inpGenInp (入力)原価金額合計
    */
    public void setInpGenInp(Long inpGenInp) {
        this.inpGenInp = inpGenInp;
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
}
