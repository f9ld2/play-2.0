// /////////////////////////////////////////////////////////////////////
// Copyright(C) 2015 NEC Solution Innovators, Ltd. All Rights Reserved.
// /////////////////////////////////////////////////////////////////////

/*
 * ====================================================================
 * 
 * 機能名称 : オリジナル商品品振エラーリスト
 * 
 * 改版履歴
 * 
 * Rev. 改版年月日 改版者名 内容
 * 
 * 1.0 2015.06.10 NECVN 新規作成
 * 
 * ===================================================================
 */

package jp.co.necsoft.web_md.core.app.dto.si;

/**
 * オリジナル商品品振エラーリストのDtoクラス
 */
public class Sijp7090Dto {
    /** 事業部コード */
    private String jigyobuCd;
    /** 事業部名漢字*/
    private String jgyNm;
    /** 店舗コード*/
    private String tenCd;
    /** 店舗名漢字*/
    private String tenNm;
    /** 発注日*/
    private String hatDd;
    /** 商品コード*/
    private String janCd;
    /** メーカーコード*/
    private String mkrCd;
    /** 商品名漢字*/
    private String shnNm;
    /** 発注数*/
    private String hatSu;
    /** 承認品振数*/
    private String appHinSu;
    /** 品振結果区分*/
    private String hinKkKbn;
    /** エラー内容*/
    private String errMsg;
    
    /**
     * get JigyobuCd
     * @return the jigyobuCd
     */
    public String getJigyobuCd() {
        return jigyobuCd;
    }
    
    /**
     * set JigyobuCd
     * @param jigyobuCd the jigyobuCd to set
     */
    public void setJigyobuCd(String jigyobuCd) {
        this.jigyobuCd = jigyobuCd;
    }
    
    /**
     * get JgyNm
     * @return the jgyNm
     */
    public String getJgyNm() {
        return jgyNm;
    }
    
    /**
     * set JgyNm
     * @param jgyNm the jgyNm to set
     */
    public void setJgyNm(String jgyNm) {
        this.jgyNm = jgyNm;
    }
    
    /**
     * get TenCd
     * @return the tenCd
     */
    public String getTenCd() {
        return tenCd;
    }
    
    /**
     * set TenCd
     * @param tenCd the tenCd to set
     */
    public void setTenCd(String tenCd) {
        this.tenCd = tenCd;
    }
    /**
     * get HatDd
     * @return the hatDd
     */
    public String getHatDd() {
        return hatDd;
    }
    
    /**
     * set HatDd
     * @param hatDd the hatDd to set
     */
    public void setHatDd(String hatDd) {
        this.hatDd = hatDd;
    }
    
    /**
     * get TenNm
     * @return the tenNm
     */
    public String getTenNm() {
        return tenNm;
    }
    
    /**
     * set TenNm
     * @param tenNm the tenNm to set
     */
    public void setTenNm(String tenNm) {
        this.tenNm = tenNm;
    }
    /**
     * get JanCd
     * @return the janCd
     */
    public String getJanCd() {
        return janCd;
    }
    
    /**
     * set JanCd
     * @param janCd the janCd to set
     */
    public void setJanCd(String janCd) {
        this.janCd = janCd;
    }
    
    /**
     * get MkrCd
     * @return the mkrCd
     */
    public String getMkrCd() {
        return mkrCd;
    }
    
    /**
     * set MkrCd
     * @param mkrCd the mkrCd to set
     */
    public void setMkrCd(String mkrCd) {
        this.mkrCd = mkrCd;
    }
    
    /**
     * get ShnNm
     * @return the shnNm
     */
    public String getShnNm() {
        return shnNm;
    }
    
    /**
     * set ShnNm
     * @param shnNm the shnNm to set
     */
    public void setShnNm(String shnNm) {
        this.shnNm = shnNm;
    }
    
    /**
     * get HatSu
     * @return the hatSu
     */
    public String getHatSu() {
        return hatSu;
    }
    /**
     * set HatSu
     * @param hatSu the hatSu to set
     */
    public void setHatSu(String hatSu) {
        this.hatSu = hatSu;
    }
    
    /**
     * get AppHinSu
     * @return the appHinSu
     */
    public String getAppHinSu() {
        return appHinSu;
    }
    
    /**
     * set AppHinSu
     * @param appHinSu the appHinSu to set
     */
    public void setAppHinSu(String appHinSu) {
        this.appHinSu = appHinSu;
    }
    
    /**
     * get HinKkKbn
     * @return the hinKkKbn
     */
    public String getHinKkKbn() {
        return hinKkKbn;
    }
    
    /**
     * set HinKkKbn
     * @param hinKkKbn the hinKkKbn to set
     */
    public void setHinKkKbn(String hinKkKbn) {
        this.hinKkKbn = hinKkKbn;
    }
    
    /**
     * get ErrMsg
     * @return the errMsg
     */
    public String getErrMsg() {
        return errMsg;
    }
    
    /**
     * set ErrMsg
     * @param errMsg the errMsg to set
     */
    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }
}
