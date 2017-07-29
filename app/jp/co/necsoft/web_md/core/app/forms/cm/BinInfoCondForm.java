// /////////////////////////////////////////////////////////////////////
// Copyright(C) 2015 NEC Solution Innovators, Ltd. All Rights Reserved.
// /////////////////////////////////////////////////////////////////////

/*
 * ====================================================================
 * 
 * 機能名称 : 商品マスタレイアウト変更
 * 
 * Rev.改版年月日 改版者名 内容
 * 
 * 1.0 2014/09/05 hainp 新規作成
 * 
 * ===================================================================
 */
package jp.co.necsoft.web_md.core.app.forms.cm;

import play.data.validation.Constraints.MaxLength;
import play.data.validation.Constraints.MinLength;
import play.data.validation.Constraints.Required;

/**
 * 商品マスタレイアウト変更のCondFormクラス
 */
public class BinInfoCondForm {

    /** 発効日 */
    @Required
    @MaxLength(value = 8)
    @MinLength(value = 8)
    private String hakkoDay;

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
