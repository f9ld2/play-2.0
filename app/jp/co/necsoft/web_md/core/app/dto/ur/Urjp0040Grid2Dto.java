///////////////////////////////////////////////////////////////////////
// Copyright(C) 2015 NEC Solution Innovators, Ltd. All Rights Reserved.
///////////////////////////////////////////////////////////////////////

/* ====================================================================
 * 機能名称 : 日別店別部門別売上修正
 * 改版履歴
 * Rev. 改版年月日   改版者名       内容
 * 1.0   2014.04.6   PhucLT      新規作成
 * =================================================================== */

package jp.co.necsoft.web_md.core.app.dto.ur;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

/**
 * @author phuclt
 *
 */
public class Urjp0040Grid2Dto {
    /**金額*/
    @Max(value = 999999999)
    @Min(value = -99999999)
    private Long kingaku;

    /**枚数*/
    @Max(value = 999999)
    @Min(value = -99999)
    private Integer maiSu;

    /**
     * @return the kingaku
     */
    public Long getKingaku() {
        return kingaku;
    }

    /**
     * @param kingaku the kingaku to set
     */
    public void setKingaku(Long kingaku) {
        this.kingaku = kingaku;
    }

    /**
     * @return the maiSu
     */
    public Integer getMaiSu() {
        return maiSu;
    }

    /**
     * @param maiSu the maiSu to set
     */
    public void setMaiSu(Integer maiSu) {
        this.maiSu = maiSu;
    }
}
