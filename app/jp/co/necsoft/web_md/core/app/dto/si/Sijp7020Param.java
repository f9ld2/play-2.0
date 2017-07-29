// /////////////////////////////////////////////////////////////////////
// Copyright(C) 2015 NEC Solution Innovators, Ltd. All Rights Reserved.
// /////////////////////////////////////////////////////////////////////

/*
 * ==================================================================== 
 * 機能名称 : 入荷状況一覧表
 * 改版履歴 
 * Rev. 改版年月日 改版者名 内容 
 * 1.0 2015.06.03 NECVN 新規作成 
 * ===================================================================
 */

package jp.co.necsoft.web_md.core.app.dto.si;

import java.util.List;



/**
 * 入荷状況一覧表のParamクラス
 * 
 */
public class Sijp7020Param {
    /** 会社 */
    private String kaisyaCd;
    
    /** 運用日 */
    private String unyoDate;
        
    /** 事業部 */
    private String jigyobuCd;
    
    /** 納品日 */
    private String nhnDayFr;

    /** 納品日終了 */
    private String nhnDayTo;
    
    /** 取引先コード */
    private String triCd;
    
    /** 出力対象 */
    private String outTaisyo;
    
    /**店舗*/
    private List<String> lsTenCd;
    
    /**
     * get Kaisya Code
     * @return the kaisyaCd
     */
    public String getKaisyaCd() {
        return kaisyaCd;
    }

    /**
     * set Kaisya Code
     * @param kaisyaCd the kaisyaCd to set
     */
    public void setKaisyaCd(String kaisyaCd) {
        this.kaisyaCd = kaisyaCd;
    }

    /**
     * get Jigyobu Code
     * @return the jigyobuCd
     */
    public String getJigyobuCd() {
        return jigyobuCd;
    }

    /**
     * set Jigyobu Code
     * @param jigyobuCd the jigyobuCd to set
     */
    public void setJigyobuCd(String jigyobuCd) {
        this.jigyobuCd = jigyobuCd;
    }

    /**
     * get NhnDay From
     * @return the nhnDayFr
     */
    public String getNhnDayFr() {
        return nhnDayFr;
    }

    /**
     * set NhnDay From
     * @param nhnDayFr the nhnDayFr to set
     */
    public void setNhnDayFr(String nhnDayFr) {
        this.nhnDayFr = nhnDayFr;
    }

    /**
     * get NhnDay To
     * @return the nhnDayTo
     */
    public String getNhnDayTo() {
        return nhnDayTo;
    }

    /**
     * set NhnDay To
     * @param nhnDayTo the nhnDayTo to set
     */
    public void setNhnDayTo(String nhnDayTo) {
        this.nhnDayTo = nhnDayTo;
    }

    /**
     * get Tri Code
     * @return the triCd
     */
    public String getTriCd() {
        return triCd;
    }

    /**
     * set Tri Code
     * @param triCd the triCd to set
     */
    public void setTriCd(String triCd) {
        this.triCd = triCd;
    }
    
    /**
     * get unyoDate
     * @return the unyoDate
     */
    public String getUnyoDate() {
        return unyoDate;
    }

    /**
     * set unyoDate
     * @param unyoDate the unyoDate to set
     */
    public void setUnyoDate(String unyoDate) {
        this.unyoDate = unyoDate;
    }
    
    /**
     * get Out Taisyo
     * 
     * @return the outTaisyo
     */
    public String getOutTaisyo() {
        return outTaisyo;
    }

    /**
     * set Out Taisyo
     * 
     * @param outTaisyo the outTaisyo to set
     */
    public void setOutTaisyo(String outTaisyo) {
        this.outTaisyo = outTaisyo;
    }

    /**
     * get lsTenCd
     * @return the lsTenCd
     */
    public List<String> getLsTenCd() {
        return lsTenCd;
    }

    /**
     * set lsTenCd
     * @param lsTenCd the lsTenCd to set
     */
    public void setLsTenCd(List<String> lsTenCd) {
        this.lsTenCd = lsTenCd;
    }
}
