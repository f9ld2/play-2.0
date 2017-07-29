// /////////////////////////////////////////////////////////////////////
// Copyright(C) 2015 NEC Solution Innovators, Ltd. All Rights Reserved.
// /////////////////////////////////////////////////////////////////////

/*
 * ====================================================================
 * 
 * 機能名称 :店間移動リスト
 * 
 * 改版履歴
 * 
 * Rev. 改版年月日 改版者名 内容
 * 
 * 1.0 20140505 Tinnc 新規作成
 * 
 * ===================================================================
 */
package jp.co.necsoft.web_md.core.app.forms.si;

/**
*店間移動リストのCondFormクラス
*
*/
public class Sijp1280CondForm {
    /**出荷日*/
    private String syukaYmdSt;

    /***/
    private String syukaYmdEd;

    /**入荷会社*/
    private String inKaisyaCd;

    /**入荷事業部*/
    private String inJigyobuCd;

    /**入荷店*/
    private String inTenCd;

    /**出荷会社*/
    private String outKaisyaCd;

    /**出荷事業部*/
    private String outJigyobuCd;

    /**出荷店(FR)*/
    private String outTenCdSt;

    /**出荷店(TO)*/
    private String outTenCdEd;

    /**
    * @return the syukaYmdSt
    */
    public String getSyukaYmdSt() {
        return syukaYmdSt;
    }

    /**
    * @param syukaYmdSt the syukaYmdSt to set
    */
    public void setSyukaYmdSt(String syukaYmdSt) {
        this.syukaYmdSt = syukaYmdSt;
    }

    /**
    * @return the syukaYmdEd
    */
    public String getSyukaYmdEd() {
        return syukaYmdEd;
    }

    /**
    * @param syukaYmdEd the syukaYmdEd to set
    */
    public void setSyukaYmdEd(String syukaYmdEd) {
        this.syukaYmdEd = syukaYmdEd;
    }

    /**
    * @return the inKaisyaCd
    */
    public String getInKaisyaCd() {
        return inKaisyaCd;
    }

    /**
    * @param inKaisyaCd the inKaisyaCd to set
    */
    public void setInKaisyaCd(String inKaisyaCd) {
        this.inKaisyaCd = inKaisyaCd;
    }

    /**
    * @return the inJigyobuCd
    */
    public String getInJigyobuCd() {
        return inJigyobuCd;
    }

    /**
    * @param inJigyobuCd the inJigyobuCd to set
    */
    public void setInJigyobuCd(String inJigyobuCd) {
        this.inJigyobuCd = inJigyobuCd;
    }

    /**
    * @return the inTenCd
    */
    public String getInTenCd() {
        return inTenCd;
    }

    /**
    * @param inTenCd the inTenCd to set
    */
    public void setInTenCd(String inTenCd) {
        this.inTenCd = inTenCd;
    }

    /**
    * @return the outKaisyaCd
    */
    public String getOutKaisyaCd() {
        return outKaisyaCd;
    }

    /**
    * @param outKaisyaCd the outKaisyaCd to set
    */
    public void setOutKaisyaCd(String outKaisyaCd) {
        this.outKaisyaCd = outKaisyaCd;
    }

    /**
    * @return the outJigyobuCd
    */
    public String getOutJigyobuCd() {
        return outJigyobuCd;
    }

    /**
    * @param outJigyobuCd the outJigyobuCd to set
    */
    public void setOutJigyobuCd(String outJigyobuCd) {
        this.outJigyobuCd = outJigyobuCd;
    }

    /**
    * @return the outTenCdSt
    */
    public String getOutTenCdSt() {
        return outTenCdSt;
    }

    /**
    * @param outTenCdSt the outTenCdSt to set
    */
    public void setOutTenCdSt(String outTenCdSt) {
        this.outTenCdSt = outTenCdSt;
    }

    /**
    * @return the outTenCdEd
    */
    public String getOutTenCdEd() {
        return outTenCdEd;
    }

    /**
    * @param outTenCdEd the outTenCdEd to set
    */
    public void setOutTenCdEd(String outTenCdEd) {
        this.outTenCdEd = outTenCdEd;
    }

}
