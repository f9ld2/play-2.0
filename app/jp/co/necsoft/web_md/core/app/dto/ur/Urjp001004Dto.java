// /////////////////////////////////////////////////////////////////////
// Copyright(C) 2015 NEC Solution Innovators, Ltd. All Rights Reserved.
// /////////////////////////////////////////////////////////////////////

/*
 * ====================================================================
 * 
 * 機能名称 :日別予算入力画面
 * 
 * 改版履歴
 * 
 * Rev. 改版年月日 改版者名 内容
 * 
 * 1.0 2014.04.10 TuanTQ 新規作成
 * 
 * ===================================================================
 */
package jp.co.necsoft.web_md.core.app.dto.ur;

import java.math.BigDecimal;

/**
 * Urjp001004Dtoクラス
 *
 */
public class Urjp001004Dto {
    /**
     * 月間売上目標
     */
    private Long ysnUriKin;
    /**
     * 月間売上目標
     */
    private BigDecimal kosei;

    /** Urjp001004Dto */
    public Urjp001004Dto() {
        ysnUriKin = new Long(0);
        kosei = new BigDecimal(0);
    }

    /**
     * @return the ysnUriKin
     */
    public Long getYsnUriKin() {
        return ysnUriKin;
    }

    /**
     * @param ysnUriKin the ysnUriKin to set
     */
    public void setYsnUriKin(Long ysnUriKin) {
        this.ysnUriKin = ysnUriKin;
    }

    /**
     * @return the kosei
     */
    public BigDecimal getKosei() {
        return kosei;
    }

    /**
     * @param kosei the kosei to set
     */
    public void setKosei(BigDecimal kosei) {
        this.kosei = kosei;
    }

}
