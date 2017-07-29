// /////////////////////////////////////////////////////////////////////
// Copyright(C) 2015 NEC Solution Innovators, Ltd. All Rights Reserved.
// /////////////////////////////////////////////////////////////////////

/*
 * ====================================================================
 * 
 * 機能名称 : ＥＯＳ発注明細リスト出力
 * 
 * 改版履歴
 * 
 * Rev. 改版年月日 改版者名 内容
 * 
 * 1.0 2014.04.25 Tinnc 新規作成
 * 
 * ===================================================================
 */
package jp.co.necsoft.web_md.core.app.dto.ha;

/**
 * M011CodeMasterクラス.
 */
public class M011CodeMaster {
    /** 取引先コード*/
    private String code;

    /** 取引先名*/
    private String name;

    /** Short Name*/
    private String shortName;

    /** 区分*/
    private String kubun;

    /** 電話番号*/
    private String telNo1;

    /** ＦＡＸ番号*/
    private String faxNo1;

    /**
     * 初期値に設定する。
     */
    public M011CodeMaster() {
    }

    /**
     * 初期値に設定する。
     * 
     * @param code triCd
     * @param name triNm
     * @param telNo1 telNo1
     * @param faxNo1 faxNo1
     */
    public M011CodeMaster(String code, String name, String telNo1, String faxNo1) {
        super();
        this.code = code;
        this.name = name;
        this.telNo1 = telNo1;
        this.faxNo1 = faxNo1;
    }

    /**
     * 
     * @param code triCd
     * @param name triNm
     * @param shortName triNmR
     * @param kubun actKbn
     * @param telNo1 telNo1
     * @param faxNo1 faxNo1
     */
    public M011CodeMaster(String code, String name, String shortName, String kubun, String telNo1, String faxNo1) {
        super();
        this.code = code;
        this.name = name;
        this.shortName = shortName;
        this.kubun = kubun;
        this.telNo1 = telNo1;
        this.faxNo1 = faxNo1;
    }

    /**
     * @return the code
     */
    public String getCode() {
        return code;
    }

    /**
     * @param code the code to set
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the shortName
     */
    public String getShortName() {
        return shortName;
    }

    /**
     * @param shortName the shortName to set
     */
    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    /**
     * @return the kubun
     */
    public String getKubun() {
        return kubun;
    }

    /**
     * @param kubun the kubun to set
     */
    public void setKubun(String kubun) {
        this.kubun = kubun;
    }

    /**
     * @return the telNo1
     */
    public String getTelNo1() {
        return telNo1;
    }

    /**
     * @param telNo1 the telNo1 to set
     */
    public void setTelNo1(String telNo1) {
        this.telNo1 = telNo1;
    }

    /**
     * @return the faxNo1
     */
    public String getFaxNo1() {
        return faxNo1;
    }

    /**
     * @param faxNo1 the faxNo1 to set
     */
    public void setFaxNo1(String faxNo1) {
        this.faxNo1 = faxNo1;
    }

}
