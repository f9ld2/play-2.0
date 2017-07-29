// /////////////////////////////////////////////////////////////////////
// Copyright(C) 2015 NEC Solution Innovators, Ltd. All Rights Reserved.
// /////////////////////////////////////////////////////////////////////

/*
 * ==================================================================== 機能名称 : 移動伝票入力 改版履歴 Rev. 改版年月日 改版者名 内容 1.0
 * 2014.04.05 Hungtb 新規作成 ===================================================================
 */
package jp.co.necsoft.web_md.core.app.dto.kk;

/**
*K003rshrExecuteResultのDtoクラス
*
*/
public class K003rshrExecuteResult {
    /**会社コード*/
    private String kaisyaCd;

    /**支払処理№*/
    private String shrSyoriNo;

    /**代表取引先コード*/
    private String mainToriCd;

    /**取引項目コード*/
    private String kakToriKmk;

    /**支払予定日*/
    private String shrDate;

    /**締め日*/
    private String simeDate;

    /**外税原価合計*/
    private Long sotoGenkAll;

    /**内税原価合計*/
    private Long uchiGenkAll;

    /**非課税原価合計*/
    private Long noGenkAll;

    /**税抜金額*/
    private Long shrKin;

    /**外税金額*/
    private Long sotoTaxKin;

    /**税込金額*/
    private Long shrKinAll;

    /**状態区分*/
    private String jotaiKbn;

    /**摘要*/
    private String tekiyo;

    /**代表取引先名称*/
    private String toriNm;

    /**税区分*/
    private String taxKbn;

    /**勘定区分*/
    private String kanjoKbn;

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
     * @return the shrSyoriNo
     */
    public String getShrSyoriNo() {
        return shrSyoriNo;
    }

    /**
     * @param shrSyoriNo the shrSyoriNo to set
     */
    public void setShrSyoriNo(String shrSyoriNo) {
        this.shrSyoriNo = shrSyoriNo;
    }

    /**
     * @return the mainToriCd
     */
    public String getMainToriCd() {
        return mainToriCd;
    }

    /**
     * @param mainToriCd the mainToriCd to set
     */
    public void setMainToriCd(String mainToriCd) {
        this.mainToriCd = mainToriCd;
    }

    /**
     * @return the kakToriKmk
     */
    public String getKakToriKmk() {
        return kakToriKmk;
    }

    /**
     * @param kakToriKmk the kakToriKmk to set
     */
    public void setKakToriKmk(String kakToriKmk) {
        this.kakToriKmk = kakToriKmk;
    }

    /**
     * @return the shrDate
     */
    public String getShrDate() {
        return shrDate;
    }

    /**
     * @param shrDate the shrDate to set
     */
    public void setShrDate(String shrDate) {
        this.shrDate = shrDate;
    }

    /**
     * @return the simeDate
     */
    public String getSimeDate() {
        return simeDate;
    }

    /**
     * @param simeDate the simeDate to set
     */
    public void setSimeDate(String simeDate) {
        this.simeDate = simeDate;
    }

    /**
     * @return the sotoGenkAll
     */
    public Long getSotoGenkAll() {
        return sotoGenkAll;
    }

    /**
     * @param sotoGenkAll the sotoGenkAll to set
     */
    public void setSotoGenkAll(Long sotoGenkAll) {
        this.sotoGenkAll = sotoGenkAll;
    }

    /**
     * @return the uchiGenkAll
     */
    public Long getUchiGenkAll() {
        return uchiGenkAll;
    }

    /**
     * @param uchiGenkAll the uchiGenkAll to set
     */
    public void setUchiGenkAll(Long uchiGenkAll) {
        this.uchiGenkAll = uchiGenkAll;
    }

    /**
     * @return the noGenkAll
     */
    public Long getNoGenkAll() {
        return noGenkAll;
    }

    /**
     * @param noGenkAll the noGenkAll to set
     */
    public void setNoGenkAll(Long noGenkAll) {
        this.noGenkAll = noGenkAll;
    }

    /**
     * @return the shrKin
     */
    public Long getShrKin() {
        return shrKin;
    }

    /**
     * @param shrKin the shrKin to set
     */
    public void setShrKin(Long shrKin) {
        this.shrKin = shrKin;
    }

    /**
     * @return the sotoTaxKin
     */
    public Long getSotoTaxKin() {
        return sotoTaxKin;
    }

    /**
     * @param sotoTaxKin the sotoTaxKin to set
     */
    public void setSotoTaxKin(Long sotoTaxKin) {
        this.sotoTaxKin = sotoTaxKin;
    }

    /**
     * @return the shrKinAll
     */
    public Long getShrKinAll() {
        return shrKinAll;
    }

    /**
     * @param shrKinAll the shrKinAll to set
     */
    public void setShrKinAll(Long shrKinAll) {
        this.shrKinAll = shrKinAll;
    }

    /**
     * @return the jotaiKbn
     */
    public String getJotaiKbn() {
        return jotaiKbn;
    }

    /**
     * @param jotaiKbn the jotaiKbn to set
     */
    public void setJotaiKbn(String jotaiKbn) {
        this.jotaiKbn = jotaiKbn;
    }

    /**
     * @return the tekiyo
     */
    public String getTekiyo() {
        return tekiyo;
    }

    /**
     * @param tekiyo the tekiyo to set
     */
    public void setTekiyo(String tekiyo) {
        this.tekiyo = tekiyo;
    }

    /**
     * @return the toriNm
     */
    public String getToriNm() {
        return toriNm;
    }

    /**
     * @param toriNm the toriNm to set
     */
    public void setToriNm(String toriNm) {
        this.toriNm = toriNm;
    }

    /**
     * @return the taxKbn
     */
    public String getTaxKbn() {
        return taxKbn;
    }

    /**
     * @param taxKbn the taxKbn to set
     */
    public void setTaxKbn(String taxKbn) {
        this.taxKbn = taxKbn;
    }

    /**
     * @return the kanjoKbn
     */
    public String getKanjoKbn() {
        return kanjoKbn;
    }

    /**
     * @param kanjoKbn the kanjoKbn to set
     */
    public void setKanjoKbn(String kanjoKbn) {
        this.kanjoKbn = kanjoKbn;
    }
}
