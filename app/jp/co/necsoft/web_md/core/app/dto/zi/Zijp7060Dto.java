// /////////////////////////////////////////////////////////////////////
// Copyright(C) 2015 NEC Solution Innovators, Ltd. All Rights Reserved.
// /////////////////////////////////////////////////////////////////////

/*
 * ====================================================================
 * 
 * 機能名称 : 棚卸入力画面 * 
 * 改版履歴
 * 
 * Rev. 改版年月日 改版者名 内容
 * 
 * 1.0 2015.06.24 NECVN 新規作成
 * 
 * ===================================================================
 */

package jp.co.necsoft.web_md.core.app.dto.zi;

import java.math.BigDecimal;

/**
 * 棚卸入力画面のDtoクラス
 * 
 */

public class Zijp7060Dto {
    /** 行 */
    private String rowNo;
    /** 削除 */
    private boolean chkFlg;
    /** 棚№ */
    private String tanaNo;
    /** ＪＡＮコード */
    private String shnCd;
    /** 商品名漢字 */
    private String shnNm;
    /** 数量 */
    private BigDecimal uriZaiSu;
    /** 売場在庫金額 */
    private BigDecimal uriZaiKin;
    /** Update Flag */
    private boolean updateFlag;

    /**
     * get rowNo
     * @return the rowNo
     */
    public String getRowNo() {
        return rowNo;
    }
    
    /**
     * set rowNo
     * @param rowNo the rowNo to set
     */
    public void setRowNo(String rowNo) {
        this.rowNo = rowNo;
    }

    /**
     * get chkFlg
     * @return the chkFlg
     */
    public boolean isChkFlg() {
        return chkFlg;
    }
    
    /**
     * set chkFlg
     * @param chkFlg the chkFlg to set
     */
    public void setChkFlg(boolean chkFlg) {
        this.chkFlg = chkFlg;
    }

    /**
     * get tanaNo
     * @return the tanaNo
     */
    public String getTanaNo() {
        return tanaNo;
    }
    
    /**
     * set tanaNo
     * @param tanaNo the tanaNo to set
     */
    public void setTanaNo(String tanaNo) {
        this.tanaNo = tanaNo;
    }

    /**
     * get shnCd
     * @return the shnCd
     */
    public String getShnCd() {
        return shnCd;
    }
    
    /**
     * set shnCd
     * @param shnCd the shnCd to set
     */
    public void setShnCd(String shnCd) {
        this.shnCd = shnCd;
    }

    /**
     * get shnNm
     * @return the shnNm
     */
    public String getShnNm() {
        return shnNm;
    }
    
    /**
     * set shnNm
     * @param shnNm the shnNm to set
     */
    public void setShnNm(String shnNm) {
        this.shnNm = shnNm;
    }
    
    /**
     * get uriZaiSu
     * @return the uriZaiSu
     */
    public BigDecimal getUriZaiSu() {
        if (null == uriZaiSu) {
            return BigDecimal.ZERO;
        }
        
        return uriZaiSu;
    }
    
    /**
     * set uriZaiSu
     * @param uriZaiSu the uriZaiSu to set
     */
    public void setUriZaiSu(BigDecimal uriZaiSu) {
        this.uriZaiSu = uriZaiSu;
    }
    

    /**
     * @return the updateFlag
     */
    public boolean isUpdateFlag() {
        return updateFlag;
    }

    /**
     * @param updateFlag the updateFlag to set
     */
    public void setUpdateFlag(boolean updateFlag) {
        this.updateFlag = updateFlag;
    }

    /**
     * @return the uriZaiKin
     */
    public BigDecimal getUriZaiKin() {
        if (null == uriZaiKin) {
            return BigDecimal.ZERO;
        }
        
        return uriZaiKin;
    }

    /**
     * @param uriZaiKin the uriZaiKin to set
     */
    public void setUriZaiKin(BigDecimal uriZaiKin) {
        this.uriZaiKin = uriZaiKin;
    }
}
