// /////////////////////////////////////////////////////////////////////
// Copyright(C) 2015 NEC Solution Innovators, Ltd. All Rights Reserved.
// /////////////////////////////////////////////////////////////////////

/*
 * ====================================================================
 * 
 * 機能名称 : 仕入チェックリスト
 * 
 * 改版履歴
 * 
 * Rev. 改版年月日 改版者名 内容
 * 
 * 1.0 2014.05.10 TuanTQ 新規作成
 * 
 * ===================================================================
 */
package jp.co.necsoft.web_md.core.app.forms.si;

import java.util.List;

import play.data.validation.Constraints.MaxLength;

import jp.co.necsoft.web_md.core.app.dto.si.S001eosds033seiDataResult;

/**
*仕入チェックリストのCondFormクラス
*
*/
public class Sijp1150CondForm {
    /**会社*/
    private String kaisyaCd;

    /**事業部*/
    private String jigyoubuCd;

    /**伝区*/
    @MaxLength(value = 2)
    private String dpyKbn;

    /**伝区Nm*/
    private String dpyKbnNm;
    /**店舗*/
    private String tenCds;

    /**部門*/
    private String bmnCds;

    /**取引先*/
    private String torihikiCds;
    /**店舗*/
    private List<String> lsTenCd;

    /**部門*/
    private List<String> lsBmnCd;

    /**取引先*/
    private List<String> lsTorihikiCd;

    /**納品予定日*/
    private String nhnYMDSt;

    /**登録日*/
    private String nhnYMDEd;

    /**登録日*/
    private String cmnInsddSt;

    /**発注日Ed*/
    private String cmnInsddEd;
    /**買掛計上日*/
    private String keijdateSt;

    /**買掛計上日*/
    private String keijdateEd;

    /**確定区分*/
    @MaxLength(value = 2)
    private String entryKbn;
    /**移動伝票印字区分*/
    @MaxLength(value = 2)
    private String idoDenKbn;
    /**移動伝票印字区分backup*/
    private String idoDenKbnBk;
    /**tenRecCnt*/
    private int tenRecCnt;
    /**tenFront*/
    private String tenFront;
    /**bmnFront*/
    private String bmnFront;
    /**denKFront*/
    private String denKFront;
    /**tenFrontName*/
    private String tenFrontName;
    /**bmnFrontName*/
    private String bmnFrontName;
    /**denKFrontName*/
    private String denKFrontName;
    /**titleCd*/
    private String titleCd;
    /**取引先名称*/
    private String titleName;
    /**toriFront*/
    private String toriFront;
    /**toriFrontName*/
    private String toriFrontName;
    /**pageCngCnt*/
    private int pageCngCnt;
    /**dataBuf*/
    private S001eosds033seiDataResult dataBuf;

    /**
     * Set condition form
     */
    public Sijp1150CondForm() {
        tenRecCnt = 0;
        tenFront = "";
        bmnFront = "";
        denKFront = "";
        titleCd = "";
        titleName = "";
        toriFront = "";
        toriFrontName = "";
        pageCngCnt = 0;
        dataBuf = new S001eosds033seiDataResult();
    }

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
     * @return the dpyKbn
     */
    public String getDpyKbn() {
        return dpyKbn;
    }

    /**
     * @param dpyKbn the dpyKbn to set
     */
    public void setDpyKbn(String dpyKbn) {
        this.dpyKbn = dpyKbn;
    }

    /**
     * @return the tenCds
     */
    public String getTenCds() {
        return tenCds;
    }

    /**
     * @param tenCds the tenCds to set
     */
    public void setTenCds(String tenCds) {
        this.tenCds = tenCds;
    }

    /**
     * @return the bmnCds
     */
    public String getBmnCds() {
        return bmnCds;
    }

    /**
     * @param bmnCds the bmnCds to set
     */
    public void setBmnCds(String bmnCds) {
        this.bmnCds = bmnCds;
    }

    /**
     * @return the torihikiCds
     */
    public String getTorihikiCds() {
        return torihikiCds;
    }

    /**
     * @param torihikiCds the torihikiCds to set
     */
    public void setTorihikiCds(String torihikiCds) {
        this.torihikiCds = torihikiCds;
    }

    /**
     * @return the lsTenCd
     */
    public List<String> getLsTenCd() {
        return lsTenCd;
    }

    /**
     * @param lsTenCd the lsTenCd to set
     */
    public void setLsTenCd(List<String> lsTenCd) {
        this.lsTenCd = lsTenCd;
    }

    /**
     * @return the lsBmnCd
     */
    public List<String> getLsBmnCd() {
        return lsBmnCd;
    }

    /**
     * @param lsBmnCd the lsBmnCd to set
     */
    public void setLsBmnCd(List<String> lsBmnCd) {
        this.lsBmnCd = lsBmnCd;
    }

    /**
     * @return the lsTorihikiCd
     */
    public List<String> getLsTorihikiCd() {
        return lsTorihikiCd;
    }

    /**
     * @param lsTorihikiCd the lsTorihikiCd to set
     */
    public void setLsTorihikiCd(List<String> lsTorihikiCd) {
        this.lsTorihikiCd = lsTorihikiCd;
    }

    /**
     * @return the dpyKbnNm
     */
    public String getDpyKbnNm() {
        return dpyKbnNm;
    }

    /**
     * @param dpyKbnNm the dpyKbnNm to set
     */
    public void setDpyKbnNm(String dpyKbnNm) {
        this.dpyKbnNm = dpyKbnNm;
    }

    /**
     * @return the nhnYMDSt
     */
    public String getNhnYMDSt() {
        return nhnYMDSt;
    }

    /**
     * @param nhnYMDSt the nhnYMDSt to set
     */
    public void setNhnYMDSt(String nhnYMDSt) {
        this.nhnYMDSt = nhnYMDSt;
    }

    /**
     * @return the nhnYMDEd
     */
    public String getNhnYMDEd() {
        return nhnYMDEd;
    }

    /**
     * @param nhnYMDEd the nhnYMDEd to set
     */
    public void setNhnYMDEd(String nhnYMDEd) {
        this.nhnYMDEd = nhnYMDEd;
    }

    /**
     * @return the cmnInsddSt
     */
    public String getCmnInsddSt() {
        return cmnInsddSt;
    }

    /**
     * @param cmnInsddSt the cmnInsddSt to set
     */
    public void setCmnInsddSt(String cmnInsddSt) {
        this.cmnInsddSt = cmnInsddSt;
    }

    /**
     * @return the cmnInsddEd
     */
    public String getCmnInsddEd() {
        return cmnInsddEd;
    }

    /**
     * @param cmnInsddEd the cmnInsddEd to set
     */
    public void setCmnInsddEd(String cmnInsddEd) {
        this.cmnInsddEd = cmnInsddEd;
    }

    /**
     * @return the keijdateSt
     */
    public String getKeijdateSt() {
        return keijdateSt;
    }

    /**
     * @param keijdateSt the keijdateSt to set
     */
    public void setKeijdateSt(String keijdateSt) {
        this.keijdateSt = keijdateSt;
    }

    /**
     * @return the keijdateEd
     */
    public String getKeijdateEd() {
        return keijdateEd;
    }

    /**
     * @param keijdateEd the keijdateEd to set
     */
    public void setKeijdateEd(String keijdateEd) {
        this.keijdateEd = keijdateEd;
    }

    /**
     * @return the entryKbn
     */
    public String getEntryKbn() {
        return entryKbn;
    }

    /**
     * @param entryKbn the entryKbn to set
     */
    public void setEntryKbn(String entryKbn) {
        this.entryKbn = entryKbn;
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

    /**
     * @return the idoDenKbnBk
     */
    public String getIdoDenKbnBk() {
        return idoDenKbnBk;
    }

    /**
     * @param idoDenKbnBk the idoDenKbnBk to set
     */
    public void setIdoDenKbnBk(String idoDenKbnBk) {
        this.idoDenKbnBk = idoDenKbnBk;
    }

    /**
     * @return the tenRecCnt
     */
    public int getTenRecCnt() {
        return tenRecCnt;
    }

    /**
     * @param tenRecCnt the tenRecCnt to set
     */
    public void setTenRecCnt(int tenRecCnt) {
        this.tenRecCnt = tenRecCnt;
    }

    /**
     * @return the tenFront
     */
    public String getTenFront() {
        return tenFront;
    }

    /**
     * @param tenFront the tenFront to set
     */
    public void setTenFront(String tenFront) {
        this.tenFront = tenFront;
    }

    /**
     * @return the bmnFront
     */
    public String getBmnFront() {
        return bmnFront;
    }

    /**
     * @param bmnFront the bmnFront to set
     */
    public void setBmnFront(String bmnFront) {
        this.bmnFront = bmnFront;
    }

    /**
     * @return the denKFront
     */
    public String getDenKFront() {
        return denKFront;
    }

    /**
     * @param denKFront the denKFront to set
     */
    public void setDenKFront(String denKFront) {
        this.denKFront = denKFront;
    }

    /**
     * @return the tenFrontName
     */
    public String getTenFrontName() {
        return tenFrontName;
    }

    /**
     * @param tenFrontName the tenFrontName to set
     */
    public void setTenFrontName(String tenFrontName) {
        this.tenFrontName = tenFrontName;
    }

    /**
     * @return the bmnFrontName
     */
    public String getBmnFrontName() {
        return bmnFrontName;
    }

    /**
     * @param bmnFrontName the bmnFrontName to set
     */
    public void setBmnFrontName(String bmnFrontName) {
        this.bmnFrontName = bmnFrontName;
    }

    /**
     * @return the denKFrontName
     */
    public String getDenKFrontName() {
        return denKFrontName;
    }

    /**
     * @param denKFrontName the denKFrontName to set
     */
    public void setDenKFrontName(String denKFrontName) {
        this.denKFrontName = denKFrontName;
    }

    /**
     * @return the titleCd
     */
    public String getTitleCd() {
        return titleCd;
    }

    /**
     * @param titleCd the titleCd to set
     */
    public void setTitleCd(String titleCd) {
        this.titleCd = titleCd;
    }

    /**
     * @return the titleName
     */
    public String getTitleName() {
        return titleName;
    }

    /**
     * @param titleName the titleName to set
     */
    public void setTitleName(String titleName) {
        this.titleName = titleName;
    }

    /**
     * @return the toriFront
     */
    public String getToriFront() {
        return toriFront;
    }

    /**
     * @param toriFront the toriFront to set
     */
    public void setToriFront(String toriFront) {
        this.toriFront = toriFront;
    }

    /**
     * @return the toriFrontName
     */
    public String getToriFrontName() {
        return toriFrontName;
    }

    /**
     * @param toriFrontName the toriFrontName to set
     */
    public void setToriFrontName(String toriFrontName) {
        this.toriFrontName = toriFrontName;
    }

    /**
     * @return the pageCngCnt
     */
    public int getPageCngCnt() {
        return pageCngCnt;
    }

    /**
     * @param pageCngCnt the pageCngCnt to set
     */
    public void setPageCngCnt(int pageCngCnt) {
        this.pageCngCnt = pageCngCnt;
    }

    /**
     * @return the dataBuf
     */
    public S001eosds033seiDataResult getDataBuf() {
        return dataBuf;
    }

    /**
     * @param dataBuf the dataBuf to set
     */
    public void setDataBuf(S001eosds033seiDataResult dataBuf) {
        this.dataBuf = dataBuf;
    }

}
