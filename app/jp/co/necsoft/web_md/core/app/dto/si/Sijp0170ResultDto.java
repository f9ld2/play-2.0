// /////////////////////////////////////////////////////////////////////
// Copyright(C) 2015 NEC Solution Innovators, Ltd. All Rights Reserved.
// /////////////////////////////////////////////////////////////////////

/*
 * ====================================================================
 * 
 * 機能名称 : ＥＯＳ伝票完納入力
 * 
 * 改版履歴
 * 
 * Rev. 改版年月日 改版者名 内容
 * 
 * 1.0 2014-04-05 TUCTV 新規作成
 * 
 * ===================================================================
 */
package jp.co.necsoft.web_md.core.app.dto.si;

import play.data.validation.Constraints.MaxLength;
import play.data.validation.Constraints.MinLength;

/**
 *  ＥＯＳ伝票完納入力のResultDtoクラス
 *
 */
public class Sijp0170ResultDto {

    /**伝票NO*/
    @MinLength(value = 9)
    @MaxLength(value = 9)
    private String dpyNo;

    /**原価合計金額*/
    private String sKenGenKin;

    /**
     * @return the dpyNo
     */
    public String getDpyNo() {
        return dpyNo;
    }

    /**
     * @param dpyNo the dpyNo to set
     */
    public void setDpyNo(String dpyNo) {
        this.dpyNo = dpyNo;
    }

    /**
     * @return the sKenGenKin
     */
    public String getsKenGenKin() {
        return sKenGenKin;
    }

    /**
     * @param sKenGenKin the sKenGenKin to set
     */
    public void setsKenGenKin(String sKenGenKin) {
        this.sKenGenKin = sKenGenKin;
    }

}
