///////////////////////////////////////////////////////////////////////
// Copyright(C) 2015 NEC Solution Innovators, Ltd. All Rights Reserved.
///////////////////////////////////////////////////////////////////////

/* ====================================================================
 * 機能名称 : ログイン
 * 改版履歴
 * Rev.  改版年月日   改版者名       内容
 * 1.0   2014.02.11   HaiNP      新規作成
 * =================================================================== */
package jp.co.necsoft.web_md.core.common.forms;

/**
 * ユーザ情報格納Beansクラス<BR>
 * ユーザ情報を格納します。<BR>
 *
 */
public class UserBean {
    
    /** 権限トークンヘッダー */
    public static final String AUTH_TOKEN_HEADER = "X-AUTH-TOKEN";
    
    /** 権限トークン */
    public static final String AUTH_TOKEN = "authToken";
    
    /** 担当者コード */
    public static final String USER_BEAN = "userBean";
    
    /** 担当者コード */
    public static final String TANTO_CD = "tantoCd";
    
    /** 担当者名 */
    public static final String TANTO_NM = "tantoNm";
    
    /** 権限レベル */
    public static final String TANTO_LVL = "tantoLvl";
    
    /** 店舗コード(7桁) */
    public static final String TENPO_CD = "tenpoCd";
    
    /** 店舗名 */
    public static final String TENPO_NM = "tenpoNm";
    
    /** 運用日 */
    public static final String UNYO_DATE = "unyoDate";
    
    /** 発注運用日 */
    public static final String HAT_UNYO_DATE = "hatUnyoDate";
    
    /** 前回中間精算締日 */
    public static final String CHUKAN_SIME_DATE = "chukanSimeDate";
    
    /** 前回月末精算締日 */
    public static final String GETSU_MATSU_SIME_DATE = "getsuMatsuSimeDate";
    
    /** System Date */
    public static final String SYS_CURRENT_YEAR = "sysCurrentYear";
    
    /** System month */
    public static final String SYS_CURRENT_MONTH = "sysCurrentMonth";
    
    /** System date */
    public static final String SYS_CURRENT_DATE = "sysCurrentDate";
    
    
    /** 担当者コード */
    private String tantoCd;
    
    /** 担当者名 */
    private String tantoNm;
    
    /** 権限レベル */
    private String tantoLvl;
    
    /** 店舗コード(7桁) */
    private String tenpoCd;
    
    /** 店舗名 */
    private String tenpoNm;
    
    /** 会社コード(2桁) */
    private String kaisyaCd;
    
    /** 事業部コード(2桁) */
    private String jigyobuCd;
    
    /** 部署コード(4桁)  */
    private String bshoCd;
    
    /**
     * UserBeanオブジェクトを構築します。
     * @param tantoCd 店舗コード(7桁)
     * @param tantoNm 店舗名称
     * @param tantoLvl 担当者コード
     * @param tenpoCd 担当者名
     * @param tenpoNm 担当者レベル
     */
    public UserBean(String tantoCd, String tantoNm, String tantoLvl, String tenpoCd, String tenpoNm) {
        super();
        this.tantoCd = tantoCd;
        this.tantoNm = tantoNm;
        this.tantoLvl = tantoLvl;
        this.tenpoCd = tenpoCd;
        this.tenpoNm = tenpoNm;
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
     * @return the tantoNm
     */
    public String getTantoNm() {
        return tantoNm;
    }

    /**
     * @param tantoNm the tantoNm to set
     */
    public void setTantoNm(String tantoNm) {
        this.tantoNm = tantoNm;
    }

    /**
     * @return the tantoLvl
     */
    public String getTantoLvl() {
        return tantoLvl;
    }

    /**
     * @param tantoLvl the tantoLvl to set
     */
    public void setTantoLvl(String tantoLvl) {
        this.tantoLvl = tantoLvl;
    }

    /**
     * @return the tenpoCd
     */
    public String getTenpoCd() {
        return tenpoCd;
    }

    /**
     * @param tenpoCd the tenpoCd to set
     */
    public void setTenpoCd(String tenpoCd) {
        this.tenpoCd = tenpoCd;
    }

    /**
     * @return the tenpoNm
     */
    public String getTenpoNm() {
        return tenpoNm;
    }

    /**
     * @param tenpoNm the tenpoNm to set
     */
    public void setTenpoNm(String tenpoNm) {
        this.tenpoNm = tenpoNm;
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
     * @return the bshoCd
     */
    public String getBshoCd() {
        return bshoCd;
    }

    /**
     * @param bshoCd the bshoCd to set
     */
    public void setBshoCd(String bshoCd) {
        this.bshoCd = bshoCd;
    }
    
}
