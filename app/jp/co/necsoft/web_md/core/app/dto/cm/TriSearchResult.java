///////////////////////////////////////////////////////////////////////
// Copyright(C) 2015 NEC Solution Innovators, Ltd. All Rights Reserved.
///////////////////////////////////////////////////////////////////////

/* ====================================================================
 * 機能名称 : 取引先検索
 * 改版履歴
 * Rev. 改版年月日   改版者名       内容
 * 1.0   2014.08.20   phuclt      新規作成
 * =================================================================== */
package jp.co.necsoft.web_md.core.app.dto.cm;

/**
 * 取引先検索のDtoクラス
 */
public class TriSearchResult {
    /**
     * triCd
     */
    private String triCd;
    /**
     * triNm
     */
    private String triNm;
    /**
     * hakkoDay
     */
    private String hakkoDay;

    /**
     * @return the triCd
     */
    public String getTriCd() {
        return triCd;
    }

    /**
     * @param triCd the triCd to set
     */
    public void setTriCd(String triCd) {
        this.triCd = triCd;
    }

    /**
     * @return the triNm
     */
    public String getTriNm() {
        return triNm;
    }

    /**
     * @param triNm the triNm to set
     */
    public void setTriNm(String triNm) {
        this.triNm = triNm;
    }

    /**
     * @return the hakkoDay
     */
    public String getHakkoDay() {
        return hakkoDay;
    }

    /**
     * @param hakkoDay the hakkoDay to set
     */
    public void setHakkoDay(String hakkoDay) {
        this.hakkoDay = hakkoDay;
    }

}
