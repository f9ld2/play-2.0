// /////////////////////////////////////////////////////////////////////
// Copyright(C) 2015 NEC Solution Innovators, Ltd. All Rights Reserved.
// /////////////////////////////////////////////////////////////////////

/*
 * ==================================================================== 
 * 機能名称 : 入荷状況一覧表 
 * Rev. 改版年月日 改版者名 内容 
 * 1.0 2015.06.03 NECVN 新規作成 
 * ===================================================================
 */

package jp.co.necsoft.web_md.core.app.forms.si;

import java.util.List;

import play.data.validation.Constraints.MaxLength;
import play.data.validation.Constraints.MinLength;
import play.data.validation.Constraints.Required;

/**
 * 入荷状況一覧表のConditionFormクラス
 * 
 */
public class Sijp7020CondForm {
    /** 会社 */
    @Required
    private String kaisyaCd;
        
    /** 事業部 */
    @MaxLength(value = 2)
    @MinLength(value = 2)
    private String jigyobuCd;
    
    /** 店舗 */
    private String tenCds;
    
    /**店舗*/
    private List<String> lsTenCd;

    /** 納品日 */
    private String nhnDayFr;

    /** 納品日終了 */
    private String nhnDayTo;
    
    /** 取引先コード */
    @MaxLength(value = 9)
    @MinLength(value = 9)
    private String triCd;
    
    /** 出力対象 */
    private String outTaisyo;
    
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
     * get tenCds
     * @return the tenCds
     */
    public String getTenCds() {
        return tenCds;
    }

    /**
     * set tenCds
     * @param tenCds the tenCds to set
     */
    public void setTenCds(String tenCds) {
        this.tenCds = tenCds;
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
