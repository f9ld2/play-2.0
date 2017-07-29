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

import java.util.ArrayList;
import java.util.List;

import jp.co.necsoft.web_md.core.app.dto.cm.BinInfoHacchyuDto;

/**
 * 商品マスタレイアウト変更のResultFormクラス
 */
public class BinInfoResultForm {
    /**発注パターン１*/
    private List<BinInfoHacchyuDto> hacchyuArea1;

    /**発注パターン２*/
    private List<BinInfoHacchyuDto> hacchyuArea2;

    /**発注パターン３*/
    private List<BinInfoHacchyuDto> hacchyuArea3;
    /**
     * 発注パターン
     */
    public void setHacchyuDtoIsNull() {
        List<BinInfoHacchyuDto> hacchyuDtos = new ArrayList<BinInfoHacchyuDto>();
        BinInfoHacchyuDto hacchyuDto = new BinInfoHacchyuDto();
        hacchyuDto.setHatpMon("");
        hacchyuDto.setHatpTue("");
        hacchyuDto.setHatpWed("");
        hacchyuDto.setHatpThu("");
        hacchyuDto.setHatpFri("");
        hacchyuDto.setHatpSat("");
        hacchyuDto.setHatpSun("");
        hacchyuDtos.add(hacchyuDto);
        this.setHacchyuArea1(hacchyuDtos);
        this.setHacchyuArea2(hacchyuDtos);
        this.setHacchyuArea3(hacchyuDtos);
    }
    /**
     * @return the hacchyuArea1
     */
    public List<BinInfoHacchyuDto> getHacchyuArea1() {
        return hacchyuArea1;
    }
    /**
     * @param hacchyuArea1 the hacchyuArea1 to set
     */
    public void setHacchyuArea1(List<BinInfoHacchyuDto> hacchyuArea1) {
        this.hacchyuArea1 = hacchyuArea1;
    }
    /**
     * @return the hacchyuArea2
     */
    public List<BinInfoHacchyuDto> getHacchyuArea2() {
        return hacchyuArea2;
    }
    /**
     * @param hacchyuArea2 the hacchyuArea2 to set
     */
    public void setHacchyuArea2(List<BinInfoHacchyuDto> hacchyuArea2) {
        this.hacchyuArea2 = hacchyuArea2;
    }
    /**
     * @return the hacchyuArea3
     */
    public List<BinInfoHacchyuDto> getHacchyuArea3() {
        return hacchyuArea3;
    }
    /**
     * @param hacchyuArea3 the hacchyuArea3 to set
     */
    public void setHacchyuArea3(List<BinInfoHacchyuDto> hacchyuArea3) {
        this.hacchyuArea3 = hacchyuArea3;
    }
    
}
