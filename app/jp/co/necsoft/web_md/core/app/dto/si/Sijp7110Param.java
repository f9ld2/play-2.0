// /////////////////////////////////////////////////////////////////////
// Copyright(C) 2015 NEC Solution Innovators, Ltd. All Rights Reserved.
// /////////////////////////////////////////////////////////////////////

/*
 * ==================================================================== 
 * 機能名称 : HHT検品取込エラーリスト * 改版履歴 
 * Rev. 改版年月日 改版者名 内容 
 * 1.0 2015.06.15 NECVN 新規作成 
 * ===================================================================
 */

package jp.co.necsoft.web_md.core.app.dto.si;

/**
 * HHT委託精算取込エラーリストのParamクラス
 * 
 */
public class Sijp7110Param {
    /** 事業部コード */
    private String jigyobuCd;
    /** 店舗コード */
    private String tenCd;
    /** 送信日 */
    private String sendDay;
    /** 担当者コード */
    private String tantoCd;
    /** 出力対象 */
    private String outTaisho;

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
     * @return the sendDay
     */
    public String getSendDay() {
        return sendDay;
    }

    /**
     * @param sendDay the sendDay to set
     */
    public void setSendDay(String sendDay) {
        this.sendDay = sendDay;
    }

    /**
     * @return the tantoCd
     */
    public String getTantoCd() {
        return tantoCd;
    }

    /**
     * @param tantoCd the tantoCd to set
     */
    public void setTantoCd(String tantoCd) {
        this.tantoCd = tantoCd;
    }

    /**
     * @return the outTaisho
     */
    public String getOutTaisho() {
        return outTaisho;
    }

    /**
     * @param outTaisho the outTaisho to set
     */
    public void setOutTaisho(String outTaisho) {
        this.outTaisho = outTaisho;
    }
}
