// /////////////////////////////////////////////////////////////////////
// Copyright(C) 2015 NEC Solution Innovators, Ltd. All Rights Reserved.
// /////////////////////////////////////////////////////////////////////

/*
 * ====================================================================
 * 
 * 機能名称 : 日割予算リスト
 * 
 * 改版履歴
 * 
 * Rev. 改版年月日 改版者名 内容
 * 
 * 1.0 2014/05/20 Taivd 新規作成
 * 
 * ===================================================================
 */
package jp.co.necsoft.web_md.core.app.forms.ur;

import java.util.List;

import jp.co.necsoft.web_md.core.app.dto.ur.Urjp1140Dto01;
import jp.co.necsoft.web_md.core.app.dto.ur.Urjp1140Dto02;

/**
*日割予算リストのResultFormクラス
*
*/
public class Urjp1140CondForm {
    /**会社*/
    private String kaisyaCd;

    /**事業部*/
    private String jigyobuCd;

    /**店舗*/
    private String tenCd;

    /**対象年*/
    private String taisyodateY;

    /**月*/
    private String taisyodateM;

    /** loginKbn */
    private String loginKbn;

    /** mLstYosanList */
    private List<Urjp1140Dto01> mLstYosanList;

    /** mLstYosanSumList */
    private List<Urjp1140Dto02> mLstYosanSumList;

    /** mPageCnt */
    private int mPageCnt;

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
    * @return the tenCd
    */
    public String getTenCd() {
        return tenCd;
    }

    /**
    * @param tenCd the tenCd to set
    */
    public void setTenCd(String tenCd) {
        this.tenCd = tenCd;
    }

    /**
    * @return the taisyodateY
    */
    public String getTaisyodateY() {
        return taisyodateY;
    }

    /**
    * @param taisyodateY the taisyodateY to set
    */
    public void setTaisyodateY(String taisyodateY) {
        this.taisyodateY = taisyodateY;
    }

    /**
    * @return the taisyodateM
    */
    public String getTaisyodateM() {
        return taisyodateM;
    }

    /**
    * @param taisyodateM the taisyodateM to set
    */
    public void setTaisyodateM(String taisyodateM) {
        this.taisyodateM = taisyodateM;
    }

    /**
     * @return the loginKbn
     */
    public String getLoginKbn() {
        return loginKbn;
    }

    /**
     * @param loginKbn the loginKbn to set
     */
    public void setLoginKbn(String loginKbn) {
        this.loginKbn = loginKbn;
    }

    /**
     * @return the mLstYosanList
     */
    public List<Urjp1140Dto01> getmLstYosanList() {
        return mLstYosanList;
    }

    /**
     * @param mLstYosanList the mLstYosanList to set
     */
    public void setmLstYosanList(List<Urjp1140Dto01> mLstYosanList) {
        this.mLstYosanList = mLstYosanList;
    }

    /**
     * @return the mLstYosanSumList
     */
    public List<Urjp1140Dto02> getmLstYosanSumList() {
        return mLstYosanSumList;
    }

    /**
     * @param mLstYosanSumList the mLstYosanSumList to set
     */
    public void setmLstYosanSumList(List<Urjp1140Dto02> mLstYosanSumList) {
        this.mLstYosanSumList = mLstYosanSumList;
    }

    /**
     * @return the mPageCnt
     */
    public int getmPageCnt() {
        return mPageCnt;
    }

    /**
     * @param mPageCnt the mPageCnt to set
     */
    public void setmPageCnt(int mPageCnt) {
        this.mPageCnt = mPageCnt;
    }

}
