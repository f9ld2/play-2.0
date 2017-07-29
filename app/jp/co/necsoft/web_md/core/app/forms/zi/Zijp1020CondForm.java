// /////////////////////////////////////////////////////////////////////
// Copyright(C) 2015 NEC Solution Innovators, Ltd. All Rights Reserved.
// /////////////////////////////////////////////////////////////////////

/*
 * ====================================================================
 * 
 * 機能名称 : 棚卸取扱い部門リスト
 * 
 * 改版履歴
 * 
 * Rev. 改版年月日 改版者名 内容
 * 
 * 1.0 2014/05/07 Taivd 新規作成
 * 
 * ===================================================================
 */
package jp.co.necsoft.web_md.core.app.forms.zi;

import java.util.List;

import jp.co.necsoft.web_md.core.app.dto.zi.Zijp1020MsaiDto;
import play.data.validation.Constraints.MaxLength;
import play.data.validation.Constraints.MinLength;

/**
*棚卸取扱い部門リストのResultFormクラス
*
*/
public class Zijp1020CondForm {
    /**店舗*/
    @MinLength(value = 3)
    @MaxLength(value = 3)
    private String tenCd;

    /**部門*/
    @MinLength(value = 3)
    @MaxLength(value = 3)
    private String bmnCd;

    /**listData*/
    private List<Zijp1020MsaiDto> listData;

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
    * @return the bmnCd
    */
    public String getBmnCd() {
        return bmnCd;
    }

    /**
    * @param bmnCd the bmnCd to set
    */
    public void setBmnCd(String bmnCd) {
        this.bmnCd = bmnCd;
    }

    /**
     * @return the listData
     */
    public List<Zijp1020MsaiDto> getListData() {
        return listData;
    }

    /**
     * @param listData the listData to set
     */
    public void setListData(List<Zijp1020MsaiDto> listData) {
        this.listData = listData;
    }

}
