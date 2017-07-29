// /////////////////////////////////////////////////////////////////////
// Copyright(C) 2015 NEC Solution Innovators, Ltd. All Rights Reserved.
// /////////////////////////////////////////////////////////////////////

/*
 * ====================================================================
 * 
 * 機能名称 : 取引項目ﾏｽﾀﾒﾝﾃﾅﾝｽのResultFormクラス
 * 
 * 改版履歴
 * 
 * Rev. 改版年月日 改版者名 内容
 * 
 * 1.0 20140310 PhongTQ 新規作成
 * 
 * ===================================================================
 */
package jp.co.necsoft.web_md.core.app.forms.kk;

import play.data.validation.Constraints.MaxLength;
import play.data.validation.Constraints.MinLength;
import play.data.validation.Constraints.Required;

/**
*取引項目ﾏｽﾀﾒﾝﾃﾅﾝｽ
のResultFormクラス
*
*/
public class Kkjp0080ResultForm {
    /**会社*/
    @Required
    private String kaisyaCd;

    /**取引項目*/
    @Required
    @MinLength(value = 6)
    @MaxLength(value = 6)
    private String kakToriKmk;

    /**取引項目名称*/
    @Required
    @MaxLength(value = 40)
    private String toriNm;

    /**勘定区分*/
    @Required
    @MaxLength(value = 1)
    private String kanjoKbn;

    /**科目*/
    @Required
    @MaxLength(value = 4)
    private String kmkCd;

    /**税区分*/
    @Required
    @MaxLength(value = 1)
    private String taxKbn;

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
    * @return the kakToriKmk
    */
    public String getKakToriKmk() {
        return kakToriKmk;
    }

    /**
    * @param kakToriKmk the kakToriKmk to set
    */
    public void setKakToriKmk(String kakToriKmk) {
        this.kakToriKmk = kakToriKmk;
    }

    /**
    * @return the toriNm
    */
    public String getToriNm() {
        return toriNm;
    }

    /**
    * @param toriNm the toriNm to set
    */
    public void setToriNm(String toriNm) {
        this.toriNm = toriNm;
    }

    /**
    * @return the kanjoKbn
    */
    public String getKanjoKbn() {
        return kanjoKbn;
    }

    /**
    * @param kanjoKbn the kanjoKbn to set
    */
    public void setKanjoKbn(String kanjoKbn) {
        this.kanjoKbn = kanjoKbn;
    }

    /**
    * @return the kmkCd
    */
    public String getKmkCd() {
        return kmkCd;
    }

    /**
    * @param kmkCd the kmkCd to set
    */
    public void setKmkCd(String kmkCd) {
        this.kmkCd = kmkCd;
    }

    /**
    * @return the taxKbn
    */
    public String getTaxKbn() {
        return taxKbn;
    }

    /**
    * @param taxKbn the taxKbn to set
    */
    public void setTaxKbn(String taxKbn) {
        this.taxKbn = taxKbn;
    }

}
