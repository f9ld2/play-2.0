///////////////////////////////////////////////////////////////////////
// Copyright(C) 2015 NEC Solution Innovators, Ltd. All Rights Reserved.
///////////////////////////////////////////////////////////////////////

/* ====================================================================
 * 機能名称 : 修理品明細一覧
 * 改版履歴
 * Rev. 改版年月日   改版者名       内容
 * 1.0   2015-06-22 chiennt 新規作成
 * =================================================================== */
package jp.co.necsoft.web_md.core.app.dto.ur;


/**
*URJP7030SelectResultのDtoクラス
*
*/
public class Urjp7030Result {
    
    /** 事業部コード*/
    private String jigyoubuCd;
    
    /**事業部名漢字*/
    private String jgyNm;
    
    /**店舗コード*/
    private String tenCd;
    
    /**店舗名漢字*/
    private String tenNm;
    
    /**伝票サブ区分名称*/
    private String dnpSubKbnNm;
    
    /**伝票区分名称*/
    private String denKbnNm;
    
    /**担当*/
    private String tanto;
    
    /**納品日*/
    private String nhnYmd;
    
    /**伝票番号*/
    private String dpyNo;
    
    /**摘要*/
    private String tekiyo;
    
    /**取引先コード*/
    private String torihikiCd;
    
    /**取引先名称*/
    private String triNm;
    
    /**商品コード０１*/
    private String shnCd01;
    
    /**商品コード０２*/
    private String shnCd02;
    
    /**商品コード０３*/
    private String shnCd03;
    
    /**商品コード０４*/
    private String shnCd04;
    
    /**商品コード０５*/
    private String shnCd05;
    
    /**商品コード０６*/
    private String shnCd06;
    
    /**商品コード０７*/
    private String shnCd07;
    
    /**商品コード０８*/
    private String shnCd08;
    
    /**商品コード０９*/
    private String shnCd09;
    
    /**商品コード１０*/
    private String shnCd10;
    
    
    /**検収原価金額０１*/
    private String kenGenkKin01;
    
    /**検収原価金額０２*/
    private String kenGenkKin02;
    
    /**検収原価金額０３*/
    private String kenGenkKin03;
    
    /**検収原価金額０４*/
    private String kenGenkKin04;
    
    /**検収原価金額０５*/
    private String kenGenkKin05;
    
    /**検収原価金額０６*/
    private String kenGenkKin06;
    
    /**検収原価金額０７*/
    private String kenGenkKin07;
    
    /**検収原価金額０８*/
    private String kenGenkKin08;
    
    /**検収原価金額０９*/
    private String kenGenkKin09;
    
    /**検収原価金額１０*/
    private String kenGenkKin10;
    
   /** 検収売価金額０１*/
    private Long kenBaikKin01;
    
    /** 検収売価金額０２*/
    private Long kenBaikKin02;
    
    /** 検収売価金額０３*/
    private Long kenBaikKin03;
    
    /** 検収売価金額０４*/
    private Long kenBaikKin04;
    
    /** 検収売価金額０５*/
    private Long kenBaikKin05;
    
    /** 検収売価金額０６*/
    private Long kenBaikKin06;
    
    /** 検収売価金額０７*/
    private Long kenBaikKin07;
    
    /** 検収売価金額０８*/
    private Long kenBaikKin08;
    
    /** 検収売価金額０９*/
    private Long kenBaikKin09;
    
    /** 検収売価金額１０*/
    private Long kenBaikKin10;

    /**
     * @return the jigyoubuCd
     */
    public String getJigyoubuCd() {
        return jigyoubuCd;
    }

    /**
     * @param jigyoubuCd the jigyoubuCd to set
     */
    public void setJigyoubuCd(String jigyoubuCd) {
        this.jigyoubuCd = jigyoubuCd;
    }

    /**
     * @return the jgyNm
     */
    public String getJgyNm() {
        return jgyNm;
    }

    /**
     * @param jgyNm the jgyNm to set
     */
    public void setJgyNm(String jgyNm) {
        this.jgyNm = jgyNm;
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
     * @return the tenNm
     */
    public String getTenNm() {
        return tenNm;
    }

    /**
     * @param tenNm the tenNm to set
     */
    public void setTenNm(String tenNm) {
        this.tenNm = tenNm;
    }

    /**
     * @return the dnpSubKbnNm
     */
    public String getDnpSubKbnNm() {
        return dnpSubKbnNm;
    }

    /**
     * @param dnpSubKbnNm the dnpSubKbnNm to set
     */
    public void setDnpSubKbnNm(String dnpSubKbnNm) {
        this.dnpSubKbnNm = dnpSubKbnNm;
    }

    /**
     * @return the denKbnNm
     */
    public String getDenKbnNm() {
        return denKbnNm;
    }

    /**
     * @param denKbnNm the denKbnNm to set
     */
    public void setDenKbnNm(String denKbnNm) {
        this.denKbnNm = denKbnNm;
    }

    /**
     * @return the tanto
     */
    public String getTanto() {
        return tanto;
    }

    /**
     * @param tanto the tanto to set
     */
    public void setTanto(String tanto) {
        this.tanto = tanto;
    }

    /**
     * @return the nhnYmd
     */
    public String getNhnYmd() {
        return nhnYmd;
    }

    /**
     * @param nhnYmd the nhnYmd to set
     */
    public void setNhnYmd(String nhnYmd) {
        this.nhnYmd = nhnYmd;
    }

    /**
     * @return the dpyNo
     */
    public String getDpyNo() {
        return dpyNo;
    }

    /**
     * @param dpyNo the dpyNo to set
     */
    public void setDpyNo(String dpyNo) {
        this.dpyNo = dpyNo;
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
     * @return the torihikiCd
     */
    public String getTorihikiCd() {
        return torihikiCd;
    }

    /**
     * @param torihikiCd the torihikiCd to set
     */
    public void setTorihikiCd(String torihikiCd) {
        this.torihikiCd = torihikiCd;
    }

    /**
     * @return the triNm
     */
    public String getTriNm() {
        return triNm;
    }

    /**
     * @param triNm the triNm to set
     */
    public void setTriNm(String triNm) {
        this.triNm = triNm;
    }

    /**
     * @return the shnCd01
     */
    public String getShnCd01() {
        return shnCd01;
    }

    /**
     * @param shnCd01 the shnCd01 to set
     */
    public void setShnCd01(String shnCd01) {
        this.shnCd01 = shnCd01;
    }

    /**
     * @return the shnCd02
     */
    public String getShnCd02() {
        return shnCd02;
    }

    /**
     * @param shnCd02 the shnCd02 to set
     */
    public void setShnCd02(String shnCd02) {
        this.shnCd02 = shnCd02;
    }

    /**
     * @return the shnCd03
     */
    public String getShnCd03() {
        return shnCd03;
    }

    /**
     * @param shnCd03 the shnCd03 to set
     */
    public void setShnCd03(String shnCd03) {
        this.shnCd03 = shnCd03;
    }

    /**
     * @return the shnCd04
     */
    public String getShnCd04() {
        return shnCd04;
    }

    /**
     * @param shnCd04 the shnCd04 to set
     */
    public void setShnCd04(String shnCd04) {
        this.shnCd04 = shnCd04;
    }

    /**
     * @return the shnCd05
     */
    public String getShnCd05() {
        return shnCd05;
    }

    /**
     * @param shnCd05 the shnCd05 to set
     */
    public void setShnCd05(String shnCd05) {
        this.shnCd05 = shnCd05;
    }

    /**
     * @return the shnCd06
     */
    public String getShnCd06() {
        return shnCd06;
    }

    /**
     * @param shnCd06 the shnCd06 to set
     */
    public void setShnCd06(String shnCd06) {
        this.shnCd06 = shnCd06;
    }

    /**
     * @return the shnCd07
     */
    public String getShnCd07() {
        return shnCd07;
    }

    /**
     * @param shnCd07 the shnCd07 to set
     */
    public void setShnCd07(String shnCd07) {
        this.shnCd07 = shnCd07;
    }

    /**
     * @return the shnCd08
     */
    public String getShnCd08() {
        return shnCd08;
    }

    /**
     * @param shnCd08 the shnCd08 to set
     */
    public void setShnCd08(String shnCd08) {
        this.shnCd08 = shnCd08;
    }

    /**
     * @return the shnCd09
     */
    public String getShnCd09() {
        return shnCd09;
    }

    /**
     * @param shnCd09 the shnCd09 to set
     */
    public void setShnCd09(String shnCd09) {
        this.shnCd09 = shnCd09;
    }

    /**
     * @return the shnCd10
     */
    public String getShnCd10() {
        return shnCd10;
    }

    /**
     * @param shnCd10 the shnCd10 to set
     */
    public void setShnCd10(String shnCd10) {
        this.shnCd10 = shnCd10;
    }

    /**
     * @return the kenGenkKin01
     */
    public String getKenGenkKin01() {
        return kenGenkKin01;
    }

    /**
     * @param kenGenkKin01 the kenGenkKin01 to set
     */
    public void setKenGenkKin01(String kenGenkKin01) {
        this.kenGenkKin01 = kenGenkKin01;
    }

    /**
     * @return the kenGenkKin02
     */
    public String getKenGenkKin02() {
        return kenGenkKin02;
    }

    /**
     * @param kenGenkKin02 the kenGenkKin02 to set
     */
    public void setKenGenkKin02(String kenGenkKin02) {
        this.kenGenkKin02 = kenGenkKin02;
    }

    /**
     * @return the kenGenkKin03
     */
    public String getKenGenkKin03() {
        return kenGenkKin03;
    }

    /**
     * @param kenGenkKin03 the kenGenkKin03 to set
     */
    public void setKenGenkKin03(String kenGenkKin03) {
        this.kenGenkKin03 = kenGenkKin03;
    }

    /**
     * @return the kenGenkKin04
     */
    public String getKenGenkKin04() {
        return kenGenkKin04;
    }

    /**
     * @param kenGenkKin04 the kenGenkKin04 to set
     */
    public void setKenGenkKin04(String kenGenkKin04) {
        this.kenGenkKin04 = kenGenkKin04;
    }

    /**
     * @return the kenGenkKin05
     */
    public String getKenGenkKin05() {
        return kenGenkKin05;
    }

    /**
     * @param kenGenkKin05 the kenGenkKin05 to set
     */
    public void setKenGenkKin05(String kenGenkKin05) {
        this.kenGenkKin05 = kenGenkKin05;
    }

    /**
     * @return the kenGenkKin06
     */
    public String getKenGenkKin06() {
        return kenGenkKin06;
    }

    /**
     * @param kenGenkKin06 the kenGenkKin06 to set
     */
    public void setKenGenkKin06(String kenGenkKin06) {
        this.kenGenkKin06 = kenGenkKin06;
    }

    /**
     * @return the kenGenkKin07
     */
    public String getKenGenkKin07() {
        return kenGenkKin07;
    }

    /**
     * @param kenGenkKin07 the kenGenkKin07 to set
     */
    public void setKenGenkKin07(String kenGenkKin07) {
        this.kenGenkKin07 = kenGenkKin07;
    }

    /**
     * @return the kenGenkKin08
     */
    public String getKenGenkKin08() {
        return kenGenkKin08;
    }

    /**
     * @param kenGenkKin08 the kenGenkKin08 to set
     */
    public void setKenGenkKin08(String kenGenkKin08) {
        this.kenGenkKin08 = kenGenkKin08;
    }

    /**
     * @return the kenGenkKin09
     */
    public String getKenGenkKin09() {
        return kenGenkKin09;
    }

    /**
     * @param kenGenkKin09 the kenGenkKin09 to set
     */
    public void setKenGenkKin09(String kenGenkKin09) {
        this.kenGenkKin09 = kenGenkKin09;
    }

    /**
     * @return the kenGenkKin10
     */
    public String getKenGenkKin10() {
        return kenGenkKin10;
    }

    /**
     * @param kenGenkKin10 the kenGenkKin10 to set
     */
    public void setKenGenkKin10(String kenGenkKin10) {
        this.kenGenkKin10 = kenGenkKin10;
    }

    /**
     * @return the kenBaikKin01
     */
    public Long getKenBaikKin01() {
        return kenBaikKin01;
    }

    /**
     * @param kenBaikKin01 the kenBaikKin01 to set
     */
    public void setKenBaikKin01(Long kenBaikKin01) {
        this.kenBaikKin01 = kenBaikKin01;
    }

    /**
     * @return the kenBaikKin02
     */
    public Long getKenBaikKin02() {
        return kenBaikKin02;
    }

    /**
     * @param kenBaikKin02 the kenBaikKin02 to set
     */
    public void setKenBaikKin02(Long kenBaikKin02) {
        this.kenBaikKin02 = kenBaikKin02;
    }

    /**
     * @return the kenBaikKin03
     */
    public Long getKenBaikKin03() {
        return kenBaikKin03;
    }

    /**
     * @param kenBaikKin03 the kenBaikKin03 to set
     */
    public void setKenBaikKin03(Long kenBaikKin03) {
        this.kenBaikKin03 = kenBaikKin03;
    }

    /**
     * @return the kenBaikKin04
     */
    public Long getKenBaikKin04() {
        return kenBaikKin04;
    }

    /**
     * @param kenBaikKin04 the kenBaikKin04 to set
     */
    public void setKenBaikKin04(Long kenBaikKin04) {
        this.kenBaikKin04 = kenBaikKin04;
    }

    /**
     * @return the kenBaikKin05
     */
    public Long getKenBaikKin05() {
        return kenBaikKin05;
    }

    /**
     * @param kenBaikKin05 the kenBaikKin05 to set
     */
    public void setKenBaikKin05(Long kenBaikKin05) {
        this.kenBaikKin05 = kenBaikKin05;
    }

    /**
     * @return the kenBaikKin06
     */
    public Long getKenBaikKin06() {
        return kenBaikKin06;
    }

    /**
     * @param kenBaikKin06 the kenBaikKin06 to set
     */
    public void setKenBaikKin06(Long kenBaikKin06) {
        this.kenBaikKin06 = kenBaikKin06;
    }

    /**
     * @return the kenBaikKin07
     */
    public Long getKenBaikKin07() {
        return kenBaikKin07;
    }

    /**
     * @param kenBaikKin07 the kenBaikKin07 to set
     */
    public void setKenBaikKin07(Long kenBaikKin07) {
        this.kenBaikKin07 = kenBaikKin07;
    }

    /**
     * @return the kenBaikKin08
     */
    public Long getKenBaikKin08() {
        return kenBaikKin08;
    }

    /**
     * @param kenBaikKin08 the kenBaikKin08 to set
     */
    public void setKenBaikKin08(Long kenBaikKin08) {
        this.kenBaikKin08 = kenBaikKin08;
    }

    /**
     * @return the kenBaikKin09
     */
    public Long getKenBaikKin09() {
        return kenBaikKin09;
    }

    /**
     * @param kenBaikKin09 the kenBaikKin09 to set
     */
    public void setKenBaikKin09(Long kenBaikKin09) {
        this.kenBaikKin09 = kenBaikKin09;
    }

    /**
     * @return the kenBaikKin10
     */
    public Long getKenBaikKin10() {
        return kenBaikKin10;
    }

    /**
     * @param kenBaikKin10 the kenBaikKin10 to set
     */
    public void setKenBaikKin10(Long kenBaikKin10) {
        this.kenBaikKin10 = kenBaikKin10;
    }
  
}
