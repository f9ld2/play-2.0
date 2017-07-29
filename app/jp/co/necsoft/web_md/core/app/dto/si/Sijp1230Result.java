// /////////////////////////////////////////////////////////////////////
// Copyright(C) 2015 NEC Solution Innovators, Ltd. All Rights Reserved.
// /////////////////////////////////////////////////////////////////////

/*
 * ====================================================================
 * 
 * 機能名称 :伝票入力プルーフリスト
 * 
 * 改版履歴
 * 
 * Rev. 改版年月日 改版者名 内容
 * 
 * 1.0 20140429 Tinnc 新規作成
 * 
 * ===================================================================
 */
package jp.co.necsoft.web_md.core.app.dto.si;

/**
*Sijp1230 ResultのDtoクラス
*/
public class Sijp1230Result {
    /**会社コード*/
    private String kaisyaCd;
    
    /**事業部コード*/
    private String jigyoubuCd;
    
    /**伝票区分*/
    private String dpyKbn;
    
    /**担当コード*/
    private String lastTantoCd;
    
    /**登録日*/
    private String insdd;
    
    /**登録時間*/
    private String instime;
    
    /**納品日付*/
    private String nhnYmd;
    
    /**店舗コード*/
    private String tenCd;
    
    /**部門コード*/
    private String bmnCd;
    
    /**伝票No*/
    private String dpyNo;
    
    /**取引先コード*/
    private String torihikiCd;
    
    /**発注日付*/
    private String hatYmd;
    
    /**sKenGenkKin*/
    private Long sKenGenkKin;
    
    /**sKenBaikKin*/
    private Long sKenBaikKin;
    
    /**更新区分*/
    private String updateKbn;
    
    /**seqno*/
    private Short seqno;
    
    /**処理状況区分*/
    private String syoriStsKbn;
    
    /**dctcKbn*/
    private String dctcKbn;
    
    /**outBmn*/
    private String outBmn;
    
    /**seqNo*/
    private String seqNo;

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
     * @return the lastTantoCd
     */
    public String getLastTantoCd() {
        return lastTantoCd;
    }

    /**
     * @param lastTantoCd the lastTantoCd to set
     */
    public void setLastTantoCd(String lastTantoCd) {
        this.lastTantoCd = lastTantoCd;
    }

    /**
     * @return the insdd
     */
    public String getInsdd() {
        return insdd;
    }

    /**
     * @param insdd the insdd to set
     */
    public void setInsdd(String insdd) {
        this.insdd = insdd;
    }

    /**
     * @return the instime
     */
    public String getInstime() {
        return instime;
    }

    /**
     * @param instime the instime to set
     */
    public void setInstime(String instime) {
        this.instime = instime;
    }

    /**
     * @return the nhnYmd
     */
    public String getNhnYmd() {
        return nhnYmd;
    }

    /**
     * @param nhnYmd the nhnYmd to set
     */
    public void setNhnYmd(String nhnYmd) {
        this.nhnYmd = nhnYmd;
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
     * @return the dpyNo
     */
    public String getDpyNo() {
        return dpyNo;
    }

    /**
     * @param dpyNo the dpyNo to set
     */
    public void setDpyNo(String dpyNo) {
        this.dpyNo = dpyNo;
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

    /**
     * @return the hatYmd
     */
    public String getHatYmd() {
        return hatYmd;
    }

    /**
     * @param hatYmd the hatYmd to set
     */
    public void setHatYmd(String hatYmd) {
        this.hatYmd = hatYmd;
    }

    /**
     * @return the sKenGenkKin
     */
    public Long getsKenGenkKin() {
        return sKenGenkKin;
    }

    /**
     * @param sKenGenkKin the sKenGenkKin to set
     */
    public void setsKenGenkKin(Long sKenGenkKin) {
        this.sKenGenkKin = sKenGenkKin;
    }

    /**
     * @return the sKenBaikKin
     */
    public Long getsKenBaikKin() {
        return sKenBaikKin;
    }

    /**
     * @param sKenBaikKin the sKenBaikKin to set
     */
    public void setsKenBaikKin(Long sKenBaikKin) {
        this.sKenBaikKin = sKenBaikKin;
    }

    /**
     * @return the updateKbn
     */
    public String getUpdateKbn() {
        return updateKbn;
    }

    /**
     * @param updateKbn the updateKbn to set
     */
    public void setUpdateKbn(String updateKbn) {
        this.updateKbn = updateKbn;
    }

    /**
     * @return the seqno
     */
    public Short getSeqno() {
        return seqno;
    }

    /**
     * @param seqno the seqno to set
     */
    public void setSeqno(Short seqno) {
        this.seqno = seqno;
    }

    /**
     * @return the syoriStsKbn
     */
    public String getSyoriStsKbn() {
        return syoriStsKbn;
    }

    /**
     * @param syoriStsKbn the syoriStsKbn to set
     */
    public void setSyoriStsKbn(String syoriStsKbn) {
        this.syoriStsKbn = syoriStsKbn;
    }

    /**
     * @return the dctcKbn
     */
    public String getDctcKbn() {
        return dctcKbn;
    }

    /**
     * @param dctcKbn the dctcKbn to set
     */
    public void setDctcKbn(String dctcKbn) {
        this.dctcKbn = dctcKbn;
    }

    /**
     * @return the outBmn
     */
    public String getOutBmn() {
        return outBmn;
    }

    /**
     * @param outBmn the outBmn to set
     */
    public void setOutBmn(String outBmn) {
        this.outBmn = outBmn;
    }

    /**
     * @return the seqNo
     */
    public String getSeqNo() {
        return seqNo;
    }

    /**
     * @param seqNo the seqNo to set
     */
    public void setSeqNo(String seqNo) {
        this.seqNo = seqNo;
    }
    
}
