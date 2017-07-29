///////////////////////////////////////////////////////////////////////
// Copyright(C) 2015 NEC Solution Innovators, Ltd. All Rights Reserved.
///////////////////////////////////////////////////////////////////////

/* ====================================================================
 * 機能名称 : 産地検索
 * 改版履歴
 * Rev. 改版年月日   改版者名       内容
 * 1.0   2014.10.14   phuclt      新規作成
 * =================================================================== */

package jp.co.necsoft.web_md.core.app.forms.cm;

/**
 * 産地検索のCondFormクラス
 *
 */
public class SantiSearchCondForm {
    /** 産地コード */
    private String santiCd;
    /** 産地名 */
    private String santiNm;
    /** 産地名略称（漢字） */
    private String santiNmR;
    /** 産地名（カナ） */
    private String santiNmA;

    /**
     * @return the santiCd
     */
    public String getSantiCd() {
        return santiCd;
    }

    /**
     * @param santiCd the santiCd to set
     */
    public void setSantiCd(String santiCd) {
        this.santiCd = santiCd;
    }

    /**
     * @return the santiNm
     */
    public String getSantiNm() {
        return santiNm;
    }

    /**
     * @param santiNm the santiNm to set
     */
    public void setSantiNm(String santiNm) {
        this.santiNm = santiNm;
    }

    /**
     * @return the santiNmR
     */
    public String getSantiNmR() {
        return santiNmR;
    }

    /**
     * @param santiNmR the santiNmR to set
     */
    public void setSantiNmR(String santiNmR) {
        this.santiNmR = santiNmR;
    }

    /**
     * @return the santiNmA
     */
    public String getSantiNmA() {
        return santiNmA;
    }

    /**
     * @param santiNmA the santiNmA to set
     */
    public void setSantiNmA(String santiNmA) {
        this.santiNmA = santiNmA;
    }
}
