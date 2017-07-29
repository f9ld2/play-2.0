// /////////////////////////////////////////////////////////////////////
// Copyright(C) 2015 NEC Solution Innovators, Ltd. All Rights Reserved.
// /////////////////////////////////////////////////////////////////////

/*
 * ====================================================================
 * 
 * 機能名称 : 月次・日割予算チェックリスト
 * 
 * 改版履歴
 * 
 * Rev. 改版年月日 改版者名 内容
 * 
 * 1.0 2014.07 石井千晶 新規作成
 * 
 * ===================================================================
 */

package jp.co.necsoft.web_md.core.app.forms.ur;

/**
*月次・日割予算チェックリストのCondFormクラス
*
*/
public class Urjp1180CondForm {	
	
    private String kaisyaCd;
    
	private String jigyobuCd;
	
    private String taisyodateY;

    private String taisyodateM;
	
    private String sTenpoFrom;

    private String sTenpoTo;
    
    private String sRuiDateFrom;
    
    private String sRuiDateFromM;
    
    private String sRuiDateTo;
    
    private String sDayDateFrom;
    
    private String sDayDateTo;

    
    public String getKaisyaCd() {
        return kaisyaCd;
    }

    public void setKaisyaCd(String kaisyaCd) {
        this.kaisyaCd = kaisyaCd;
    }

    public String getJigyobuCd() {
        return jigyobuCd;
    }

    public void setJigyobuCd(String jigyobuCd) {
        this.jigyobuCd = jigyobuCd;
    }

    public String getSTenpoFrom() {
        return sTenpoFrom;
    }

    public void setSTenpoFrom(String sTenpoFrom) {
        this.sTenpoFrom = sTenpoFrom;
    }

    public String getSTenpoTo() {
        return sTenpoTo;
    }

    public void setSTenpoTo(String sTenpoTo) {
        this.sTenpoTo = sTenpoTo;
    }

    public String getTaisyodateY() {
        return taisyodateY;
    }

    public void setTaisyodateY(String taisyodateY) {
        this.taisyodateY = taisyodateY;
    }

    public String getTaisyodateM() {
        return taisyodateM;
    }

    public void setTaisyodateM(String taisyodateM) {
        this.taisyodateM = taisyodateM;
    } 


    public String getSRuiDateFrom() {
        return sRuiDateFrom;
    }

    public void setSRuiDateFrom(String sRuiDateFrom) {
        this.sRuiDateFrom = sRuiDateFrom;
    }
    
    public String getSRuiDateFromM() {
        return sRuiDateFromM;
    }

    public void setSRuiDateFromM(String sRuiDateFromM) {
        this.sRuiDateFromM = sRuiDateFromM;
    }

    public String getSRuiDateTo() {
        return sRuiDateTo;
    }

    public void setSRuiDateTo(String sRuiDateTo) {
        this.sRuiDateTo = sRuiDateTo;
    }

    public String getSDayDateFrom() {
        return sDayDateFrom;
    }

    public void setSDayDateFrom(String sDayDateFrom) {
        this.sDayDateFrom = sDayDateFrom;
    }

    public String getSDayDateTo() {
        return sDayDateTo;
    }

    public void setSDayDateTo(String sDayDateTo) {
        this.sDayDateTo = sDayDateTo;
    } 

}
