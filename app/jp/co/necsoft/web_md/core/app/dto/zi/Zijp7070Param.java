// /////////////////////////////////////////////////////////////////////
// Copyright(C) 2015 NEC Solution Innovators, Ltd. All Rights Reserved.
// /////////////////////////////////////////////////////////////////////

/*
 * ==================================================================== 
 * 機能名称 : HHT棚卸データ取込エラーリスト * 改版履歴 
 * Rev. 改版年月日 改版者名 内容 
 * 1.0 2015.06.19 NECVN 新規作成 
 * ===================================================================
 */

package jp.co.necsoft.web_md.core.app.dto.zi;

/**
 * HHT棚卸データ取込エラーリストのParamクラス
 * 
 */
public class Zijp7070Param {
    /** 事業部コード */
    private String jigyobuCd;
    /** 店舗コード */
    private String tenCd;
    /** 作業（送信）日 */
    private String sosinNichiji;
    /** 担当者コード */
    private String tantoCd;
    /** 出力対象 */
    private String outTaisyo;

    /**
     * get jigyobuCd
     * @return the jigyobuCd
     */
    public String getJigyobuCd() {
        return jigyobuCd;
    }
    
    /**
     * set jigyobuCd
     * @param jigyobuCd the jigyobuCd to set
     */
    public void setJigyobuCd(String jigyobuCd) {
        this.jigyobuCd = jigyobuCd;
    }

    /**
     * get tenCd
     * @return the tenCd
     */
    public String getTenCd() {
        return tenCd;
    }
    
    /**
     * set tenCd
     * @param tenCd the tenCd to set
     */
    public void setTenCd(String tenCd) {
        this.tenCd = tenCd;
    }

    /**
     * get sosinNichiji
     * @return the sosinNichiji
     */
    public String getSosinNichiji() {
        return sosinNichiji;
    }
    
    /**
     * set sosinNichiji
     * @param sosinNichiji the sosinNichiji to set
     */
    public void setSosinNichiji(String sosinNichiji) {
        this.sosinNichiji = sosinNichiji;
    }

    /**
     * get tantoCd
     * @return the tantoCd
     */
    public String getTantoCd() {
        return tantoCd;
    }
    
    /**
     * set tantoCd
     * @param tantoCd the tantoCd to set
     */
    public void setTantoCd(String tantoCd) {
        this.tantoCd = tantoCd;
    }

    /**
     * get outTaisyo
     * @return the outTaisyo
     */
    public String getOutTaisyo() {
        return outTaisyo;
    }
    
    /**
     * set outTaisyo
     * @param outTaisyo the outTaisyo to set
     */
    public void setOutTaisyo(String outTaisyo) {
        this.outTaisyo = outTaisyo;
    }
}
