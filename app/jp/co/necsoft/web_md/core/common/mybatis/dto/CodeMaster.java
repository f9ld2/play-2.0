///////////////////////////////////////////////////////////////////////
// Copyright(C) 2015 NEC Solution Innovators, Ltd. All Rights Reserved.
///////////////////////////////////////////////////////////////////////

/* ====================================================================
 * 機能名称 : メーカマスタメンテ
 * 改版履歴
 * Rev. 改版年月日   改版者名       内容
 * 1.0   2014.02.07   HaiNP      新規作成
 * =================================================================== */
package jp.co.necsoft.web_md.core.common.mybatis.dto;

/**
 * コードマスタ
 *
 */
public class CodeMaster {
    /** Code */
    private String code;
    /** Full name */
    private String name;
    /** Short Name */
    private String shortName;
    /** Kubun */
    private String kubun;
    /** Katakana name */
    private String nameA;
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
     * @return the nameA
     */
    public String getNameA() {
        return nameA;
    }
    /**
     * @param nameA the nameA to set
     */
    public void setNameA(String nameA) {
        this.nameA = nameA;
    }
}