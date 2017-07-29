// /////////////////////////////////////////////////////////////////////
// Copyright(C) 2015 NEC Solution Innovators, Ltd. All Rights Reserved.
// /////////////////////////////////////////////////////////////////////

/*
 * ====================================================================
 * 
 * 機能名称 : HHT品振取込エラーリスト * 
 * 改版履歴
 * 
 * Rev. 改版年月日 改版者名 内容
 * 
 * 1.0 2015.06.05 NECVN 新規作成
 * 
 * ===================================================================
 */

package jp.co.necsoft.web_md.core.app.dto.si;

/**
 * HHT品振取込エラーリストのDtoクラス
 * 
 */

public class Sijp7060Dto {
    /** 出荷店事業部コード */
    private String outJigyobuCd;
    /** 出荷店事業部名漢字 */
    private String outJigyobuNm;
    /** 出荷店店舗コード */
    private String outTenpoCd;
    /** 出荷店舗名漢字 */
    private String outTenpoNm;
    /** 端末ID */
    private String termId;
    /** 担当者コード */
    private String tantoCd;
    /** 商品コード */
    private String shnCd;
    /** 入荷店店舗コード */
    private String inTenpoCd;
    /** 入荷店舗名漢字 */
    private String inTenpoNm;
    /** 品振数量 */
    private String hinSu;
    /** 送信日時 */
    private String sosinNichiji;
    /** メーカー品番 */
    private String makerHinCd;
    /** 商品名漢字 */
    private String shnNm;
    /** メッセージ */
    private String msg;


    /**
     * get outJigyobuCd
     * @return the outJigyobuCd
     */
    public String getOutJigyobuCd() {
        return outJigyobuCd;
    }
    
    /**
     * set outJigyobuCd
     * @param outJigyobuCd the outJigyobuCd to set
     */
    public void setOutJigyobuCd(String outJigyobuCd) {
        this.outJigyobuCd = outJigyobuCd;
    }

    /**
     * get outJigyobuNm
     * @return the outJigyobuNm
     */
    public String getOutJigyobuNm() {
        return outJigyobuNm;
    }
    
    /**
     * set outJigyobuNm
     * @param outJigyobuNm the outJigyobuNm to set
     */
    public void setOutJigyobuNm(String outJigyobuNm) {
        this.outJigyobuNm = outJigyobuNm;
    }

    /**
     * get outTenpoCd
     * @return the outTenpoCd
     */
    public String getOutTenpoCd() {
        return outTenpoCd;
    }
    
    /**
     * set outTenpoCd
     * @param outTenpoCd the outTenpoCd to set
     */
    public void setOutTenpoCd(String outTenpoCd) {
        this.outTenpoCd = outTenpoCd;
    }

    /**
     * get outTenpoNm
     * @return the outTenpoNm
     */
    public String getOutTenpoNm() {
        return outTenpoNm;
    }
    
    /**
     * set outTenpoNm
     * @param outTenpoNm the outTenpoNm to set
     */
    public void setOutTenpoNm(String outTenpoNm) {
        this.outTenpoNm = outTenpoNm;
    }

    /**
     * get termId
     * @return the termId
     */
    public String getTermId() {
        return termId;
    }
    
    /**
     * set termId
     * @param termId the termId to set
     */
    public void setTermId(String termId) {
        this.termId = termId;
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
     * get shnCd
     * @return the shnCd
     */
    public String getShnCd() {
        return shnCd;
    }
    
    /**
     * set shnCd
     * @param shnCd the shnCd to set
     */
    public void setShnCd(String shnCd) {
        this.shnCd = shnCd;
    }

    /**
     * get inTenpoCd
     * @return the inTenpoCd
     */
    public String getInTenpoCd() {
        return inTenpoCd;
    }
    
    /**
     * set inTenpoCd
     * @param inTenpoCd the inTenpoCd to set
     */
    public void setInTenpoCd(String inTenpoCd) {
        this.inTenpoCd = inTenpoCd;
    }

    /**
     * get inTenpoNm
     * @return the inTenpoNm
     */
    public String getInTenpoNm() {
        return inTenpoNm;
    }
    
    /**
     * set inTenpoNm
     * @param inTenpoNm the inTenpoNm to set
     */
    public void setInTenpoNm(String inTenpoNm) {
        this.inTenpoNm = inTenpoNm;
    }

    /**
     * get hinSu
     * @return the hinSu
     */
    public String getHinSu() {
        return hinSu;
    }
    
    /**
     * set hinSu
     * @param hinSu the hinSu to set
     */
    public void setHinSu(String hinSu) {
        this.hinSu = hinSu;
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
     * get makerHinCd
     * @return the makerHinCd
     */
    public String getMakerHinCd() {
        return makerHinCd;
    }
    
    /**
     * set makerHinCd
     * @param makerHinCd the makerHinCd to set
     */
    public void setMakerHinCd(String makerHinCd) {
        this.makerHinCd = makerHinCd;
    }

    /**
     * get shnNm
     * @return the shnNm
     */
    public String getShnNm() {
        return shnNm;
    }
    
    /**
     * set shnNm
     * @param shnNm the shnNm to set
     */
    public void setShnNm(String shnNm) {
        this.shnNm = shnNm;
    }

    /**
     * get msg
     * @return the msg
     */
    public String getMsg() {
        return msg;
    }
    
    /**
     * set msg
     * @param msg the msg to set
     */
    public void setMsg(String msg) {
        this.msg = msg;
    }
}
