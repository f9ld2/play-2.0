// /////////////////////////////////////////////////////////////////////
// Copyright(C) 2015 NEC Solution Innovators, Ltd. All Rights Reserved.
// /////////////////////////////////////////////////////////////////////
/*
 * ====================================================================
 * 
 * 機能名称 :委託精算確認
 * 
 * 改版履歴
 * 
 * Rev. 改版年月日 改版者名 内容
 * 
 * 1.0 2015-06-04 NECVN 新規作成
 * 
 * ===================================================================
 */
package jp.co.necsoft.web_md.core.app.forms.si;

import java.math.BigDecimal;
import java.util.List;

import jp.co.necsoft.web_md.core.app.dto.si.Sijp7100Dto;

/**
* 委託精算確認のResultFormクラス
*
*/
public class Sijp7100ResultForm1 {
    /** dtoList */
    private List<Sijp7100Dto> dtoList;
    /**sum*/
    private BigDecimal sum;
    
    /**
     * get dtoList
     * @return the dtoList
     */
    public List<Sijp7100Dto> getDtoList() {
        return dtoList;
    }
    
    /**
     * set dtoList
     * @param dtoList the dtoList to set
     */
    public void setDtoList(List<Sijp7100Dto> dtoList) {
        this.dtoList = dtoList;
    }
    
    /**
     * get sum
     * @return the sum
     */
    public BigDecimal getSum() {
        return sum;
    }
    
    /**
     * set sum
     * @param sum the sum to set
     */
    public void setSum(BigDecimal sum) {
        this.sum = sum;
    }
    
}
