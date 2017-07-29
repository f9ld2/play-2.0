// /////////////////////////////////////////////////////////////////////
// Copyright(C) 2015 NEC Solution Innovators, Ltd. All Rights Reserved.
// /////////////////////////////////////////////////////////////////////

/*
 * ====================================================================
 * 
 * 機能名称 : 棚卸情報入力 改版履歴
 * 
 * Rev. 改版年月日 改版者名 内容
 * 
 * 1.0 2014.03.14 CuongPV 新規作成
 * 
 * ===================================================================
 */
package jp.co.necsoft.web_md.core.app.forms.zi;

import java.util.List;

import javax.validation.Valid;

import play.data.validation.Constraints.MaxLength;
import play.data.validation.Constraints.MinLength;
import play.data.validation.Constraints.Required;
import jp.co.necsoft.web_md.core.app.dto.zi.Zijp0030BmnDto;
import jp.co.necsoft.web_md.core.app.dto.zi.Zijp0030GaiDto;
import jp.co.necsoft.web_md.core.app.dto.zi.Zijp0030TenDto;
import jp.co.necsoft.web_md.core.common.forms.CommonMasterResultForm;

/**
*棚卸情報入力のResultFormクラス
*
*/
public class Zijp0030ResultForm extends CommonMasterResultForm {

    /**会社*/
    @Required
    @MinLength(value = 2)
    @MaxLength(value = 2)
    private String kaisyaCd;

    /**事業部*/
    @Required
    @MinLength(value = 2)
    @MaxLength(value = 2)
    private String jigyobuCd;

    /**店舗*/
    @MinLength(value = 3)
    @MaxLength(value = 3)
    private String tenCd;

    /**部門*/
    @MinLength(value = 3)
    @MaxLength(value = 3)
    private String bmnCd;

    /** zijp0030BmnDtos*/
    @Valid
    private List<Zijp0030BmnDto> zijp0030BmnDtos;

    /** zijp0030GaiDtos*/
    @Valid
    private List<Zijp0030GaiDto> zijp0030GaiDtos;

    /** zijp0030TenDtos*/
    @Valid
    private List<Zijp0030TenDto> zijp0030TenDtos;

    /** Unyo Date */
    private String unyoDate;
    
    /**
     * @return the zijp0030BmnDtos
     */
    public List<Zijp0030BmnDto> getZijp0030BmnDtos() {
        return zijp0030BmnDtos;
    }

    /**
     * @param zijp0030BmnDtos the zijp0030BmnDtos to set
     */
    public void setZijp0030BmnDtos(List<Zijp0030BmnDto> zijp0030BmnDtos) {
        this.zijp0030BmnDtos = zijp0030BmnDtos;
    }

    /**
     * @return the zijp0030GaiDtos
     */
    public List<Zijp0030GaiDto> getZijp0030GaiDtos() {
        return zijp0030GaiDtos;
    }

    /**
     * @param zijp0030GaiDtos the zijp0030GaiDtos to set
     */
    public void setZijp0030GaiDtos(List<Zijp0030GaiDto> zijp0030GaiDtos) {
        this.zijp0030GaiDtos = zijp0030GaiDtos;
    }

    /**
     * @return the zijp0030TenDtos
     */
    public List<Zijp0030TenDto> getZijp0030TenDtos() {
        return zijp0030TenDtos;
    }

    /**
     * @param zijp0030TenDtos the zijp0030TenDtos to set
     */
    public void setZijp0030TenDtos(List<Zijp0030TenDto> zijp0030TenDtos) {
        this.zijp0030TenDtos = zijp0030TenDtos;
    }

    /** Tanto Code */
    private String tantoCd;
    
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
     * @return the unyoDate
     */
    public String getUnyoDate() {
        return unyoDate;
    }

    /**
     * @param unyoDate the unyoDate to set
     */
    public void setUnyoDate(String unyoDate) {
        this.unyoDate = unyoDate;
    }

    /**
     * @return the tantoCd
     */
    public String getTantoCd() {
        return tantoCd;
    }

    /**
     * @param tantoCd the tantoCd to set
     */
    public void setTantoCd(String tantoCd) {
        this.tantoCd = tantoCd;
    }
}
