///////////////////////////////////////////////////////////////////////
// Copyright(C) 2015 NEC Solution Innovators, Ltd. All Rights Reserved.
///////////////////////////////////////////////////////////////////////

/* ====================================================================
 * 機能名称 : 買掛金支払チェックリスト出力
 * 改版履歴
 * Rev. 改版年月日   改版者名       内容
 * 1.0   2014.04.05   VuQQ      新規作成
 * =================================================================== */
package jp.co.necsoft.web_md.core.app.forms.kk;

import play.data.validation.Constraints.MaxLength;
import play.data.validation.Constraints.Required;


/**
*買掛金支払チェックリスト出力のResultFormクラス
*
*/
public class Kkjp1180CondForm {

    /**対象年*/
    @Required
    @MaxLength(value = 4)
    private String taisyodateY;

    /**月*/
    @Required
    @MaxLength(value = 2)
    private String taisyodateM;

    /**
    * @return the taisyodateY 
    */
    public String getTaisyodateY() {
        return taisyodateY;
    }

    /**
    * @param taisyodateY the taisyodateY  to set
    */
    public void setTaisyodateY(String taisyodateY) {
        this.taisyodateY  = taisyodateY;
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

}

