// /////////////////////////////////////////////////////////////////////
// Copyright(C) 2015 NEC Solution Innovators, Ltd. All Rights Reserved.
// /////////////////////////////////////////////////////////////////////

/*
 * ====================================================================
 * 
 * 機能名称 : 物流スケジュールメンテ
 * 
 * 改版履歴
 * 
 * Rev. 改版年月日 改版者名 内容
 * 
 * 1.0 2014.04.18 ToanPQ 新規作成
 * 
 * ===================================================================
 */
package jp.co.necsoft.web_md.core.app.forms.ha;

/**
*物流スケジュールマスタメンテのGridDataクラス
*
*/
public class Hajp0030GridData {
    /**月*/
    private String dd1;

    /**火*/
    private String dd2;

    /**水*/
    private String dd3;

    /**木*/
    private String dd4;

    /**金*/
    private String dd5;

    /**土*/
    private String dd6;

    /**日*/
    private String dd7;

    /**納品日*/
    private String teiHatDd1;

    /**(書かない)*/
    private String teiHatDd2;

    /**(書かない)*/
    private String teiHatDd3;

    /**(書かない)*/
    private String teiHatDd4;

    /**(書かない)*/
    private String teiHatDd5;

    /**(書かない)*/
    private String teiHatDd6;

    /**(書かない)*/
    private String teiHatDd7;

    /**納品日*/
    private String tokHatDd1;

    /**(書かない)*/
    private String tokHatDd2;

    /**(書かない)*/
    private String tokHatDd3;

    /**(書かない)*/
    private String tokHatDd4;

    /**(書かない)*/
    private String tokHatDd5;

    /**(書かない)*/
    private String tokHatDd6;

    /**(書かない)*/
    private String tokHatDd7;

    /**teiHatDd1とtokHatDd1の表示フラグ*/
    private Boolean readOnly1;

    /**teiHatDd2とtokHatDd2の表示フラグ*/
    private Boolean readOnly2;

    /**teiHatDd3とtokHatDd3の表示フラグ*/
    private Boolean readOnly3;

    /**teiHatDd4とtokHatDd4の表示フラグ*/
    private Boolean readOnly4;

    /**teiHatDd5とtokHatDd5の表示フラグ*/
    private Boolean readOnly5;

    /**teiHatDd6とtokHatDd6の表示フラグ*/
    private Boolean readOnly6;

    /**teiHatDd7とtokHatDd7の表示フラグ*/
    private Boolean readOnly7;

    /**teiHatDd1とtokHatDd1のFocus*/
    private Boolean focus1;

    /**teiHatDd2とtokHatDd2のFocus*/
    private Boolean focus2;

    /**teiHatDd3とtokHatDd3のFocus*/
    private Boolean focus3;

    /**teiHatDd4とtokHatDd4のFocus*/
    private Boolean focus4;

    /**teiHatDd5とtokHatDd5のFocus*/
    private Boolean focus5;

    /**teiHatDd6とtokHatDd6のFocus*/
    private Boolean focus6;

    /**teiHatDd7とtokHatDd7のFocus*/
    private Boolean focus7;

    /**
     * @return the dd1
     */
    public String getDd1() {
        return dd1;
    }

    /**
     * @param dd1 the dd1 to set
     */
    public void setDd1(String dd1) {
        this.dd1 = dd1;
    }

    /**
     * @return the dd2
     */
    public String getDd2() {
        return dd2;
    }

    /**
     * @param dd2 the dd2 to set
     */
    public void setDd2(String dd2) {
        this.dd2 = dd2;
    }

    /**
     * @return the dd3
     */
    public String getDd3() {
        return dd3;
    }

    /**
     * @param dd3 the dd3 to set
     */
    public void setDd3(String dd3) {
        this.dd3 = dd3;
    }

    /**
     * @return the dd4
     */
    public String getDd4() {
        return dd4;
    }

    /**
     * @param dd4 the dd4 to set
     */
    public void setDd4(String dd4) {
        this.dd4 = dd4;
    }

    /**
     * @return the dd5
     */
    public String getDd5() {
        return dd5;
    }

    /**
     * @param dd5 the dd5 to set
     */
    public void setDd5(String dd5) {
        this.dd5 = dd5;
    }

    /**
     * @return the dd6
     */
    public String getDd6() {
        return dd6;
    }

    /**
     * @param dd6 the dd6 to set
     */
    public void setDd6(String dd6) {
        this.dd6 = dd6;
    }

    /**
     * @return the dd7
     */
    public String getDd7() {
        return dd7;
    }

    /**
     * @param dd7 the dd7 to set
     */
    public void setDd7(String dd7) {
        this.dd7 = dd7;
    }

    /**
     * @return the teiHatDd1
     */
    public String getTeiHatDd1() {
        return teiHatDd1;
    }

    /**
     * @param teiHatDd1 the teiHatDd1 to set
     */
    public void setTeiHatDd1(String teiHatDd1) {
        this.teiHatDd1 = teiHatDd1;
    }

    /**
     * @return the teiHatDd2
     */
    public String getTeiHatDd2() {
        return teiHatDd2;
    }

    /**
     * @param teiHatDd2 the teiHatDd2 to set
     */
    public void setTeiHatDd2(String teiHatDd2) {
        this.teiHatDd2 = teiHatDd2;
    }

    /**
     * @return the teiHatDd3
     */
    public String getTeiHatDd3() {
        return teiHatDd3;
    }

    /**
     * @param teiHatDd3 the teiHatDd3 to set
     */
    public void setTeiHatDd3(String teiHatDd3) {
        this.teiHatDd3 = teiHatDd3;
    }

    /**
     * @return the teiHatDd4
     */
    public String getTeiHatDd4() {
        return teiHatDd4;
    }

    /**
     * @param teiHatDd4 the teiHatDd4 to set
     */
    public void setTeiHatDd4(String teiHatDd4) {
        this.teiHatDd4 = teiHatDd4;
    }

    /**
     * @return the teiHatDd5
     */
    public String getTeiHatDd5() {
        return teiHatDd5;
    }

    /**
     * @param teiHatDd5 the teiHatDd5 to set
     */
    public void setTeiHatDd5(String teiHatDd5) {
        this.teiHatDd5 = teiHatDd5;
    }

    /**
     * @return the teiHatDd6
     */
    public String getTeiHatDd6() {
        return teiHatDd6;
    }

    /**
     * @param teiHatDd6 the teiHatDd6 to set
     */
    public void setTeiHatDd6(String teiHatDd6) {
        this.teiHatDd6 = teiHatDd6;
    }

    /**
     * @return the teiHatDd7
     */
    public String getTeiHatDd7() {
        return teiHatDd7;
    }

    /**
     * @param teiHatDd7 the teiHatDd7 to set
     */
    public void setTeiHatDd7(String teiHatDd7) {
        this.teiHatDd7 = teiHatDd7;
    }

    /**
     * @return the tokHatDd1
     */
    public String getTokHatDd1() {
        return tokHatDd1;
    }

    /**
     * @param tokHatDd1 the tokHatDd1 to set
     */
    public void setTokHatDd1(String tokHatDd1) {
        this.tokHatDd1 = tokHatDd1;
    }

    /**
     * @return the tokHatDd2
     */
    public String getTokHatDd2() {
        return tokHatDd2;
    }

    /**
     * @param tokHatDd2 the tokHatDd2 to set
     */
    public void setTokHatDd2(String tokHatDd2) {
        this.tokHatDd2 = tokHatDd2;
    }

    /**
     * @return the tokHatDd3
     */
    public String getTokHatDd3() {
        return tokHatDd3;
    }

    /**
     * @param tokHatDd3 the tokHatDd3 to set
     */
    public void setTokHatDd3(String tokHatDd3) {
        this.tokHatDd3 = tokHatDd3;
    }

    /**
     * @return the tokHatDd4
     */
    public String getTokHatDd4() {
        return tokHatDd4;
    }

    /**
     * @param tokHatDd4 the tokHatDd4 to set
     */
    public void setTokHatDd4(String tokHatDd4) {
        this.tokHatDd4 = tokHatDd4;
    }

    /**
     * @return the tokHatDd5
     */
    public String getTokHatDd5() {
        return tokHatDd5;
    }

    /**
     * @param tokHatDd5 the tokHatDd5 to set
     */
    public void setTokHatDd5(String tokHatDd5) {
        this.tokHatDd5 = tokHatDd5;
    }

    /**
     * @return the tokHatDd6
     */
    public String getTokHatDd6() {
        return tokHatDd6;
    }

    /**
     * @param tokHatDd6 the tokHatDd6 to set
     */
    public void setTokHatDd6(String tokHatDd6) {
        this.tokHatDd6 = tokHatDd6;
    }

    /**
     * @return the tokHatDd7
     */
    public String getTokHatDd7() {
        return tokHatDd7;
    }

    /**
     * @param tokHatDd7 the tokHatDd7 to set
     */
    public void setTokHatDd7(String tokHatDd7) {
        this.tokHatDd7 = tokHatDd7;
    }

    /**
     * @return the readOnly1
     */
    public Boolean getReadOnly1() {
        return readOnly1;
    }

    /**
     * @param readOnly1 the readOnly1 to set
     */
    public void setReadOnly1(Boolean readOnly1) {
        this.readOnly1 = readOnly1;
    }

    /**
     * @return the readOnly2
     */
    public Boolean getReadOnly2() {
        return readOnly2;
    }

    /**
     * @param readOnly2 the readOnly2 to set
     */
    public void setReadOnly2(Boolean readOnly2) {
        this.readOnly2 = readOnly2;
    }

    /**
     * @return the readOnly3
     */
    public Boolean getReadOnly3() {
        return readOnly3;
    }

    /**
     * @param readOnly3 the readOnly3 to set
     */
    public void setReadOnly3(Boolean readOnly3) {
        this.readOnly3 = readOnly3;
    }

    /**
     * @return the readOnly4
     */
    public Boolean getReadOnly4() {
        return readOnly4;
    }

    /**
     * @param readOnly4 the readOnly4 to set
     */
    public void setReadOnly4(Boolean readOnly4) {
        this.readOnly4 = readOnly4;
    }

    /**
     * @return the readOnly5
     */
    public Boolean getReadOnly5() {
        return readOnly5;
    }

    /**
     * @param readOnly5 the readOnly5 to set
     */
    public void setReadOnly5(Boolean readOnly5) {
        this.readOnly5 = readOnly5;
    }

    /**
     * @return the readOnly6
     */
    public Boolean getReadOnly6() {
        return readOnly6;
    }

    /**
     * @param readOnly6 the readOnly6 to set
     */
    public void setReadOnly6(Boolean readOnly6) {
        this.readOnly6 = readOnly6;
    }

    /**
     * @return the readOnly7
     */
    public Boolean getReadOnly7() {
        return readOnly7;
    }

    /**
     * @param readOnly7 the readOnly7 to set
     */
    public void setReadOnly7(Boolean readOnly7) {
        this.readOnly7 = readOnly7;
    }

    /**
     * @return the focus1
     */
    public Boolean getFocus1() {
        return focus1;
    }

    /**
     * @param focus1 the focus1 to set
     */
    public void setFocus1(Boolean focus1) {
        this.focus1 = focus1;
    }

    /**
     * @return the focus2
     */
    public Boolean getFocus2() {
        return focus2;
    }

    /**
     * @param focus2 the focus2 to set
     */
    public void setFocus2(Boolean focus2) {
        this.focus2 = focus2;
    }

    /**
     * @return the focus3
     */
    public Boolean getFocus3() {
        return focus3;
    }

    /**
     * @param focus3 the focus3 to set
     */
    public void setFocus3(Boolean focus3) {
        this.focus3 = focus3;
    }

    /**
     * @return the focus4
     */
    public Boolean getFocus4() {
        return focus4;
    }

    /**
     * @param focus4 the focus4 to set
     */
    public void setFocus4(Boolean focus4) {
        this.focus4 = focus4;
    }

    /**
     * @return the focus5
     */
    public Boolean getFocus5() {
        return focus5;
    }

    /**
     * @param focus5 the focus5 to set
     */
    public void setFocus5(Boolean focus5) {
        this.focus5 = focus5;
    }

    /**
     * @return the focus6
     */
    public Boolean getFocus6() {
        return focus6;
    }

    /**
     * @param focus6 the focus6 to set
     */
    public void setFocus6(Boolean focus6) {
        this.focus6 = focus6;
    }

    /**
     * @return the focus7
     */
    public Boolean getFocus7() {
        return focus7;
    }

    /**
     * @param focus7 the focus7 to set
     */
    public void setFocus7(Boolean focus7) {
        this.focus7 = focus7;
    }

}
