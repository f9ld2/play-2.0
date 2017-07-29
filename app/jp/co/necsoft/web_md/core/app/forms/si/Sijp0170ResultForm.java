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

package jp.co.necsoft.web_md.core.app.forms.si;

import java.util.List;

import play.data.validation.Constraints.MaxLength;
import play.data.validation.Constraints.MinLength;
import play.data.validation.Constraints.Required;
import jp.co.necsoft.web_md.core.app.dto.si.Sijp0170ResultDto;

/**
*ＥＯＳ伝票完納入力のResultFormクラス
*
*/
public class Sijp0170ResultForm {
    /**納品ルート*/
    @Required
    @MinLength(value = 1)
    @MaxLength(value = 1)
    private String ruteCd;

    /**納品日*/
    @Required
    @MinLength(value = 8)
    @MaxLength(value = 8)
    private String inpNohinDate;

    /**会社*/
    @MinLength(value = 2)
    @MaxLength(value = 2)
    private String kaisyaCd;

    /**事業部*/
    @MinLength(value = 2)
    @MaxLength(value = 2)
    private String jigyobuCd;

    /**店舗*/
    @MinLength(value = 3)
    @MaxLength(value = 3)
    private String tenCd;

    /**明細情報１*/
    private List<Sijp0170ResultDto> msaiInfArea1;

    /**明細情報２*/
    private List<Sijp0170ResultDto> msaiInfArea2;
    /**明細情報3*/
    private List<Sijp0170ResultDto> msaiInfArea3;

    /**
     * @return the ruteCd
     */
    public String getRuteCd() {
        return ruteCd;
    }

    /**
     * @param ruteCd the ruteCd to set
     */
    public void setRuteCd(String ruteCd) {
        this.ruteCd = ruteCd;
    }

    /**
     * @return the inpNohinDate
     */
    public String getInpNohinDate() {
        return inpNohinDate;
    }

    /**
     * @param inpNohinDate the inpNohinDate to set
     */
    public void setInpNohinDate(String inpNohinDate) {
        this.inpNohinDate = inpNohinDate;
    }

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
     * @return the msaiInfArea1
     */
    public List<Sijp0170ResultDto> getMsaiInfArea1() {
        return msaiInfArea1;
    }

    /**
     * @param msaiInfArea1 the msaiInfArea1 to set
     */
    public void setMsaiInfArea1(List<Sijp0170ResultDto> msaiInfArea1) {
        this.msaiInfArea1 = msaiInfArea1;
    }

    /**
     * @return the msaiInfArea2
     */
    public List<Sijp0170ResultDto> getMsaiInfArea2() {
        return msaiInfArea2;
    }

    /**
     * @param msaiInfArea2 the msaiInfArea2 to set
     */
    public void setMsaiInfArea2(List<Sijp0170ResultDto> msaiInfArea2) {
        this.msaiInfArea2 = msaiInfArea2;
    }

    /**
     * @return the msaiInfArea3
     */
    public List<Sijp0170ResultDto> getMsaiInfArea3() {
        return msaiInfArea3;
    }

    /**
     * @param msaiInfArea3 the msaiInfArea3 to set
     */
    public void setMsaiInfArea3(List<Sijp0170ResultDto> msaiInfArea3) {
        this.msaiInfArea3 = msaiInfArea3;
    }

}
