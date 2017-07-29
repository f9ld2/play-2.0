// /////////////////////////////////////////////////////////////////////
// Copyright(C) 2015 NEC Solution Innovators, Ltd. All Rights Reserved.
// /////////////////////////////////////////////////////////////////////

/*
 * ====================================================================
 * 
 * 機能名称 : CCSICommonのcommmonクラス
 * 
 * 改版履歴
 * 
 * Rev. 改版年月日 改版者名 内容
 * 
 * 1.0 2014.04.07 Hungtb 新規作成
 * 
 * ===================================================================
 */
package jp.co.necsoft.web_md.core.common.biz.si;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import jp.co.necsoft.web_md.common.ChaseException;
import jp.co.necsoft.web_md.common.mybatis.dao.MyBatisSqlMapDao;
import jp.co.necsoft.web_md.core.app.dto.cm.T001kksyChkTeiTok;
import jp.co.necsoft.web_md.core.app.dto.cm.T001kksyChkTeiTokResult;
import jp.co.necsoft.web_md.core.common.CCMessageConst;
import jp.co.necsoft.web_md.core.common.mybatis.dao.M000kaimMapper;
import jp.co.necsoft.web_md.core.common.mybatis.dao.M001jgymMapper;
import jp.co.necsoft.web_md.core.common.mybatis.dao.M003bmnmMapper;
import jp.co.necsoft.web_md.core.common.mybatis.dao.M004tbumMapper;
import jp.co.necsoft.web_md.core.common.mybatis.dao.M006tenmMapper;
import jp.co.necsoft.web_md.core.common.mybatis.dao.M007kijmMapper;
import jp.co.necsoft.web_md.core.common.mybatis.dao.M009msymMapper;
import jp.co.necsoft.web_md.core.common.mybatis.dao.M011trimMapper;
import jp.co.necsoft.web_md.core.common.mybatis.dao.M016tanmMapper;
import jp.co.necsoft.web_md.core.common.mybatis.dao.M017meimMapper;
import jp.co.necsoft.web_md.core.common.mybatis.dao.M020ctlmMapper;
import jp.co.necsoft.web_md.core.common.mybatis.dao.M022sibnMapper;
import jp.co.necsoft.web_md.core.common.mybatis.dto.M000kaim;
import jp.co.necsoft.web_md.core.common.mybatis.dto.M000kaimExample;
import jp.co.necsoft.web_md.core.common.mybatis.dto.M001jgym;
import jp.co.necsoft.web_md.core.common.mybatis.dto.M001jgymExample;
import jp.co.necsoft.web_md.core.common.mybatis.dto.M003bmnm;
import jp.co.necsoft.web_md.core.common.mybatis.dto.M003bmnmExample;
import jp.co.necsoft.web_md.core.common.mybatis.dto.M004tbum;
import jp.co.necsoft.web_md.core.common.mybatis.dto.M004tbumExample;
import jp.co.necsoft.web_md.core.common.mybatis.dto.M006tenm;
import jp.co.necsoft.web_md.core.common.mybatis.dto.M006tenmExample;
import jp.co.necsoft.web_md.core.common.mybatis.dto.M007kijm;
import jp.co.necsoft.web_md.core.common.mybatis.dto.M007kijmExample;
import jp.co.necsoft.web_md.core.common.mybatis.dto.M009msym;
import jp.co.necsoft.web_md.core.common.mybatis.dto.M009msymExample;
import jp.co.necsoft.web_md.core.common.mybatis.dto.M011trim;
import jp.co.necsoft.web_md.core.common.mybatis.dto.M011trimExample;
import jp.co.necsoft.web_md.core.common.mybatis.dto.M016tanm;
import jp.co.necsoft.web_md.core.common.mybatis.dto.M016tanmExample;
import jp.co.necsoft.web_md.core.common.mybatis.dto.M017meim;
import jp.co.necsoft.web_md.core.common.mybatis.dto.M017meimExample;
import jp.co.necsoft.web_md.core.common.mybatis.dto.M020ctlm;
import jp.co.necsoft.web_md.core.common.mybatis.dto.M020ctlmExample;
import jp.co.necsoft.web_md.core.common.mybatis.dto.M022sibn;
import jp.co.necsoft.web_md.core.common.mybatis.dto.M022sibnExample;
import jp.co.necsoft.web_md.core.common.utils.CCDateUtil;
import jp.co.necsoft.web_md.core.common.utils.CCDigitUtil;
import jp.co.necsoft.web_md.core.common.utils.CCNumberUtil;
import jp.co.necsoft.web_md.core.common.utils.CCStringUtil;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.mybatis.guice.transactional.Transactional;

import play.i18n.Messages;

/**
 * @author hungtbz
 * @since 2014/03/20
 */
public class CCSICommon {
    /** EMPTY = "" */
    private static final String EMPTY = "";
    /** BLANK = " " */
    private static final String BLANK = " ";
    /** Const from cmJKKBeans: CON_SAIBAN_KBN = "01" */
    private static final String CON_SAIBAN_KBN = "01";
    /** MAPPER */
    /** M000kaimMapper */
    @Inject
    private M000kaimMapper m000kaimMapper;
    /** M001jgymMapper */
    @Inject
    private M001jgymMapper m001jgymMapper;
    /** M006tenmMapper */
    @Inject
    private M006tenmMapper m006tenmMapper;
    /** M003bmnmMapper */
    @Inject
    private M003bmnmMapper m003bmnmMapper;
    /** M020ctlmMapper */
    @Inject
    private M020ctlmMapper m020ctlmMapper;
    /** M016tanmMapper */
    @Inject
    private M016tanmMapper m016tanmMapper;
    /** M007kijmMapper */
    @Inject
    private M007kijmMapper m007kijmMapper;
    /** M009msymMapper */
    @Inject
    private M009msymMapper m009msymMapper;
    /** M004tbumMapper */
    @Inject
    private M004tbumMapper m004tbumMapper;
    /** M017meimMapper */
    @Inject
    private M017meimMapper m017meimMapper;
    /** M011trimMapper */
    @Inject
    private M011trimMapper m011trimMapper;
    /** M022SIBN Mappper */
    @Inject
    private M022sibnMapper m022sibnMapper;
    /** dao: DAO for pure sql mapping. */
    @Inject
    private MyBatisSqlMapDao dao;
    /* CONSTANT */
    /**
    *店舗区分
    **/
    /** CN_TENKBN_TENPO = "1" */
    public static final String CN_TENKBN_TENPO = "1"; // 店舗区分(店)
    /** CN_TENKBN_FCTEN = "2" */
    public static final String CN_TENKBN_FCTEN = "2"; // 店舗区分(FC店)
    /** CN_TENKBN_CENTER = "3" */
    public static final String CN_TENKBN_CENTER = "3"; // 店舗区分(ｾﾝﾀｰ)
    /** CN_TENKBN_ZIKCEN = "4" */
    public static final String CN_TENKBN_ZIKCEN = "4"; // 店舗区分(在庫ｾﾝﾀｰ)
    /** CN_TENKBN_SHNCEN = "5" */
    public static final String CN_TENKBN_SHNCEN = "5"; // 店舗区分(商品ｾﾝﾀｰ)
    /** CN_TENKBN_HONBU = "9" */
    public static final String CN_TENKBN_HONBU = "9"; // 店舗区分(本部)

    /**
    *入力トラン用
    **/
    public static final String CN_UPDKBN_I = "I";
    /**
     * 更新区分(削除)
     */
    public static final String CN_UPDKBN_D = "D";
    /**
     * 前回ﾚｺｰﾄﾞ作成
     */
    public static final String CN_REC_ZEN = "0";
    /**
     * 前回ﾚｺｰﾄﾞ作成
     */
    public static final short CN_REC_ZEN_0 = 0;
    /**
     *  今回ﾚｺｰﾄﾞ作成
     */
    public static final String CN_REC_KON = "1";
    /**
     *  今回ﾚｺｰﾄﾞ作成
     */
    public static final short CN_REC_KON_1 = 1;

    /**
    * 処理区分
    **/
    /** CN_KAKUTEI = 1 */
    public static final int CN_KAKUTEI = 1; // 確定
    /** CN_INSERT = 1 */
    public static final int CN_INSERT = 1; // 登録
    /** CN_UPDATE = 2 */
    public static final int CN_UPDATE = 2; // 更新
    /** CN_CONTINUE = 2 */
    public static final int CN_CONTINUE = 2; // 続行
    /** CN_DISPLAY = 3 */
    public static final int CN_DISPLAY = 3; // 参照
    /** CN_MIKAKUTEI = 5 */
    public static final int CN_MIKAKUTEI = 5; // 未確定
    /** CN_DELETE = 9 */
    public static final int CN_DELETE = 9; // 削除
    /** CN_DISPLAY_EDIT = 7 */
    public static final int CN_DISPLAY_EDIT = 7; // 検索してから編集
    /**
     * 名称Ｍ検索キー
     **/
    public static final String CN_DPY_KBN = "DPY_KBN_";
    /**
     * 処理区分
     */
    public static final String CN_SYORI_STS_KBN = "SYORI_STS_KBN_";
    /**
     * 確定区分
     */
    public static final String CN_ENTRY_KBN = "ENTRY_KBN_";
    /**
     * DC/TC区分
     */
    public static final String CN_DCTC_KBN = "DCTC_KBN_";
    /**
     * 理由区分
     */
    public static final String CN_RIYU_KBN = "RIYU_KBN_";

    /**
     * 理由区分
     */
    public static final String CN_CD = "SIR00";

    /**
     * 新規
     */
    public static final String TYPE_ACT_KBN_CREATED = "1";

    /**
     * 更新
     */
    public static final String TYPE_ACT_KBN_UPDATED = "2";

    /**
     * 削除
     */
    public static final String TYPE_ACT_KBN_DELETED = "9";

    /**
    * 稼動区分(削除)
    **/
    public static final String CN_SHN_KYUSI = "9";

    /**
    *事業部コード
    **/
    public static final String CN_JIGYOBU_90 = "90"; // 事業部ｺｰﾄﾞ(90)

    /**
    * 稼動区分(削除)
    **/
    public static final String CN_CD_SIR = "SIR";

    /**
    * 数量,単価,金額のMax値
    **/
    /** 数量最大値 */
    public static final long CN_SURYO_MAX = 1000000; // 数量最大値
    /** 単価最大値 */
    public static final long CN_TANKA_MAX = 10000000; // 単価最大値
    /** 金額最大値 */
    public static final long CN_MEIKIN_MAX = 1000000000L; // 金額最大値
    /** 金額最大値 */
    public static final long CN_KINGAK_MAX = 10000000000L; // 金額最大値
    /** 金額合計最大値 */
    public static final long CN_KINALL_MAX = 100000000000L; // 金額合計最大値

    /**
     * 処理状態区分定数定義内部クラス
     **/
    /**
     * 未確定
     **/
    public static final String SYORISTS_KBN_KBN_MIKAKTEI = "1";
    /**
    * 確定済
    **/
    public static final String SYORISTS_KBN_KBN_KAKTEI = "2";
    /**
    * 支払済
    **/
    public static final String SYORISTS_KBN_KBN_SHR_END = "3";
    /**
    * 買掛済（未使用）
    **/
    public static final String SYORISTS_KBN_KBN_KAK_END = "4";
    /**
    * 月次済
    **/
    public static final String SYORISTS_KBN_KBN_GETJI_END = "5";
    /**
    * 削除済
    **/
    public static final String SYORISTS_KBN_KBN_DELETE = "9";

    /**
     * Constructor.
     */
    public CCSICommon() {
    }

    /**
     * 会社マスタからの会社名(カナ)取得処理.
     * @param sKaisyaCode 会社コード
     * @return 会社名(カナ)
     */
    public String getComKaisyaNameA(String sKaisyaCode) {
        String sRet = "";
        String sSysDate = CCDateUtil.getSysDateFormat(CCDateUtil.DATETIME_FORMAT_DATE_YYYYMMDD);

        M000kaimExample example = new M000kaimExample();
        example.createCriteria().andKaisyaCdEqualTo(sKaisyaCode);
        example.setSearchDate(sSysDate);

        List<M000kaim> result = m000kaimMapper.selectByExample(example);
        if (result.size() > 0) {
            if (!CCSICommon.TYPE_ACT_KBN_DELETED.equals(result.get(0).getActKbn())) {
                sRet = CCStringUtil.trimBoth(result.get(0).getKaiNmA());
            }
        }
        return sRet;
    }

    /**
     * 会社マスタからの会社名(漢字)取得処理.
     * 
     * @param sKaisyaCode 会社コード
      * @param sDate 日付
     * @return 会社名(漢字)
     */
    public String getComKaisyaName(String sKaisyaCode, String sDate) {

        String sRet = "";
        if (sKaisyaCode.trim().length() == 0) {
            return "";
        }

        if (sDate.trim().length() == 0) {
            sDate = CCDateUtil.getSysDateFormat(CCDateUtil.DATETIME_FORMAT_DATE_YYYYMMDD);
        }

        M000kaimExample example = new M000kaimExample();
        example.createCriteria().andKaisyaCdEqualTo(sKaisyaCode);
        example.setSearchDate(sDate);

        List<M000kaim> result = m000kaimMapper.selectByExample(example);
        if (result.size() > 0) {
            if (!CCSICommon.TYPE_ACT_KBN_DELETED.equals(result.get(0).getActKbn())) {
                sRet = CCStringUtil.trimBoth(result.get(0).getKaiNm());
            }
        }
        return sRet;
    }

    /**
     * 事業部マスタからの事業部名(ｶﾅ)取得処理.
     * 
     * @param sKaisyaCode 会社コード
     * @param sJigyobuCode 事業部コード
     * @return 事業部名 (ｶﾅ)
     */
    public String getComJigyobuNameA(String sKaisyaCode, String sJigyobuCode) {
        String sRet = "";
        String sSysDate = CCDateUtil.getSysDateFormat(CCDateUtil.DATETIME_FORMAT_DATE_YYYYMMDD);
        M001jgymExample example = new M001jgymExample();
        example.createCriteria().andKaisyaCdEqualTo(sKaisyaCode).andJigyobuCdEqualTo(sJigyobuCode);
        example.setSearchDate(sSysDate);
        List<M001jgym> result = m001jgymMapper.selectByExample(example);
        if (result.size() > 0) {
            if (!CCSICommon.TYPE_ACT_KBN_DELETED.equals(result.get(0).getActKbn())) {
                sRet = CCStringUtil.trimBoth(result.get(0).getJgyNmA());
            }
        }
        return sRet;
    }

    /**
     * 事業部マスタからの事業部名(漢字)取得処理.
     * 
     * @param sKaisya 会社コード
     * @param sJigyo 事業部コード
     * @param sDate 日付
     * @return 事業部名 (漢字)
     */
    public String getComJigyobuName(String sKaisya, String sJigyo, String sDate) {

        String sRet = "";
        if (sKaisya.trim().length() == 0) {
            return sRet;
        }

        if (sJigyo.trim().length() == 0) {
            return sRet;
        }

        if (sDate.trim().length() == 0) {
            sDate = CCDateUtil.getSysDateFormat(CCDateUtil.DATETIME_FORMAT_DATE_YYYYMMDD);
        }

        M001jgymExample example = new M001jgymExample();
        example.createCriteria().andKaisyaCdEqualTo(sKaisya).andJigyobuCdEqualTo(sJigyo);
        example.setSearchDate(sDate);
        List<M001jgym> result = m001jgymMapper.selectByExample(example);
        if (result.size() > 0) {
            if (!CCSICommon.TYPE_ACT_KBN_DELETED.equals(result.get(0).getActKbn())) {
                sRet = CCStringUtil.trimBoth(result.get(0).getJgyNm());
            }
        }
        return sRet;

    }

    /**
     * 店舗マスタからの店舗名(ｶﾅ)取得処理.
     * 
     * @param sTencd 店舗コード
     * @param sDate 日付
     * @return 店舗名 (ｶﾅ)
     */
    public String getComTenpoNameA(String sTencd, String sDate) {
        String sRet = "";
        if (sTencd.trim().length() == 0) {
            return sRet;
        }

        if (sDate.trim().length() == 0) {
            return sRet;
        }

        // 店舗マスタ検索オブジェクト作成
        M006tenmExample example = new M006tenmExample();
        example.createCriteria().andTenCdEqualTo(sTencd);
        example.setSearchDate(sDate);

        List<M006tenm> result = m006tenmMapper.selectByExample(example);
        if (result.size() > 0) {
            if (!CCSICommon.TYPE_ACT_KBN_DELETED.equals(result.get(0).getActKbn())) {
                // 店舗名(カナ)
                sRet = CCStringUtil.trimBoth(result.get(0).getTenNmA());
            }
        }
        return sRet;
    }

    /**
     * 店舗マスタからの店舗名(漢字)取得処理.
     * 
     * @param sTencd 店舗コード（7桁）
     * @param sDate 日付
     * @return 店舗名 (漢字)
     */
    public String getComTenpoName(String sTencd, String sDate) {
        String sRet = "";
        if (sTencd.trim().length() == 0) {
            return sRet;
        }
        if (sDate.trim().length() == 0) {
            return sRet;
        }

        // 店舗マスタ検索オブジェクト作成
        M006tenmExample example = new M006tenmExample();
        example.createCriteria().andTenCdEqualTo(sTencd);
        example.setSearchDate(sDate);

        List<M006tenm> result = m006tenmMapper.selectByExample(example);

        if (result.size() > 0) {
            if (!CCSICommon.TYPE_ACT_KBN_DELETED.equals(result.get(0).getActKbn())) {
                // 店舗名(カナ)
                sRet = CCStringUtil.trimBoth(result.get(0).getTenNm());
            }
        }
        return sRet;
    }

    /**
     * 部門マスタからの部門名(ｶﾅ)取得処理.
     * 
     * @param sBmncd 部門コード（５桁）
     * @param sDate 日付
     * @return 部門名 (ｶﾅ)
     */
    public String getComBumonNameA(String sBmncd, String sDate) {
        String sRet = "";
        if (sBmncd.trim().length() == 0) {
            return sRet;
        }

        if (sDate.trim().length() == 0) {
            return sRet;
        }

        // 部門マスタ検索オブジェクト作成
        M003bmnmExample example = new M003bmnmExample();
        example.createCriteria().andBmnCdEqualTo(sBmncd);
        example.setSearchDate(sDate);

        List<M003bmnm> result = m003bmnmMapper.selectByExample(example);

        if (result.size() > 0) {
            if (!CCSICommon.TYPE_ACT_KBN_DELETED.equals(result.get(0).getActKbn())) {
                // 部門名（ｶﾅ）
                sRet = CCStringUtil.trimBoth(result.get(0).getBmnNmA());
            }
        }
        return sRet;
    }

    /**
     * 本部・店舗納品日取得処理.
     * 
     * @param sTenCd7 店舗７桁
     * @param sDate 日付
     * @return 納品日
     */
    public String getComNhnYmd(String sTenCd7, String sDate) {
        String sRet = "";
        String sHnbNhnymd = "";
        String sTenNhnymd = "";

        // コントロールマスタ検索オブジェクト作成
        M020ctlmExample example = new M020ctlmExample();
        example.createCriteria().andCdEqualTo(CN_CD);

        List<M020ctlm> result = m020ctlmMapper.selectByExample(example);
        if (result.size() > 0 && result.get(0).getData().length() >= 55) {
            sHnbNhnymd = CCStringUtil.trimBoth(result.get(0).getData()).substring(39, 47);
            sTenNhnymd = CCStringUtil.trimBoth(result.get(0).getData()).substring(47, 55);
        }

        // 店舗区分を取得
        String sTenKbn = getComTenpoKubun(sTenCd7, sDate);

        // 店舗区分が本部
        if (sTenKbn.equals(CN_TENKBN_HONBU)) { // 店舗区分(本部)
            // 本部納品日を返却
            sRet = sHnbNhnymd;
        } else {
            // 店舗納品日を返却
            sRet = sTenNhnymd;
        }

        return sRet;
    }

    /**
     * 店舗マスタからの店舗区分取得処理.
     * 
     * @param sTencd 店舗コード（７桁）
     * @param sDate 日付
     * @return 店舗区分
     */
    public String getComTenpoKubun(String sTencd, String sDate) {
        String sRet = "";
        if (sTencd.trim().length() == 0) {
            return sRet;
        }
        if (sDate.trim().length() == 0) {
            return sRet;
        }

        // 店舗マスタ検索オブジェクト作成
        M006tenmExample example = new M006tenmExample();
        example.createCriteria().andTenCdEqualTo(sTencd);
        example.setSearchDate(sDate);

        List<M006tenm> result = m006tenmMapper.selectByExample(example);

        if (result.size() > 0) {
            if (!CCSICommon.TYPE_ACT_KBN_DELETED.equals(result.get(0).getActKbn())) {
                // 店舗区分
                sRet = CCStringUtil.trimBoth(result.get(0).getTenKbn());
            }
        }
        return sRet;
    }

    /**
     * 担当者マスタからの店舗コード取得処理.
     * 
     * @param sTanto 担当者コード
     * @return 店舗コード
     */
    public String getDefaultTenpo(String sTanto) {
        String sRet = "";
        // 担当者マスタ検索オブジェクト作成
        String sSysDate = CCDateUtil.getSysDateFormat(CCDateUtil.DATETIME_FORMAT_DATE_YYYYMMDD);
        M016tanmExample example = new M016tanmExample();
        example.createCriteria().andTantoCdEqualTo(sTanto);
        example.setSearchDate(sSysDate);

        List<M016tanm> result = m016tanmMapper.selectByExample(example);
        if (result.size() > 0) {
            if (!CCSICommon.TYPE_ACT_KBN_DELETED.equals(result.get(0).getActKbn())) {
                // 店舗コード
                sRet = CCStringUtil.trimBoth(result.get(0).getTenCd());
            }
        }
        return sRet;
    }

    /**
     * 基本情報マスタからのPLU区分取得処理.
     * 
     * @param sJanCode 商品コード
     * @param sDate 日付
     * @return PLU区分
     */
    public String getPlukbn(String sJanCode, String sDate) {
        String sRet = "";
        // 14桁JANｺｰﾄﾞに変換
        sJanCode = cnvJanToJan13(sJanCode);

        if (sJanCode.trim().length() == 0) {
            return sRet;
        }

        if (sDate.trim().length() == 0) {
            return sRet;
        }

        // 基本情報マスタ検索オブジェクト作成
        M007kijmExample example = new M007kijmExample();
        example.createCriteria().andJanCdEqualTo(sJanCode);
        example.setSearchDate(sDate);

        List<M007kijm> result = m007kijmMapper.selectByExample(example);

        if (result.size() > 0) {
            // Plu区分
            sRet = CCStringUtil.trimBoth(result.get(0).getShnCdPluKbn());
        }
        return sRet;
    }

    /**
     * ＪＡＮコード変換処理.
     * 
     * @param sStrVal JANｺｰﾄﾞ
     * @return 変換後JANｺｰﾄﾞ
     */
    public String cnvJanToJan13(String sStrVal) {
        // 入力値取得
        sStrVal = sStrVal.trim();

        // 6,8,9,11,12,13,14桁以外はエラー
        if (sStrVal.length() != 6 && sStrVal.length() != 7 && sStrVal.length() != 8 && sStrVal.length() != 9
                && sStrVal.length() != 11 && sStrVal.length() != 12 && sStrVal.length() != 13
                && sStrVal.length() != 14) {
            return "";
        }

        String sJanCd = sStrVal;

        // NON-PLU対応
        // 先頭が"26"の時は、後ろの6桁を"0"にする
        if (sJanCd.substring(0, 2).equals("26")) {
            String sNonPlu = "";
            sNonPlu = sJanCd.substring(0, 7);
            sJanCd = sNonPlu + "000000";
        }

        // 13桁に満たない場合は、前ZEROを付加して13桁に変換
        for (int i = sJanCd.length(); i < 13; i++) {
            sJanCd = "0" + sJanCd;
        }
        // 13桁の場合は、スペースを先頭に付加する
        if (sJanCd.length() == 13) {
            sJanCd = " " + sJanCd;
        }
        return sJanCd;
    }

    /**
     * 伝票番号のチェックディジットチェック.
     * 
     * @param sDenNo 伝票№（9桁）
     * @return true：正常 false：異常コード
     */
    public boolean chkDenpyoNo(String sDenNo) {

        CCDigitUtil objCd = new CCDigitUtil();

        // 桁数チェック
        if (sDenNo.length() < 9) {
            return false;
        }
        // チェックディジット付きの伝票Noを取得
        String sRetDenNo = objCd.getCdDenNo(sDenNo.substring(1, 8));
        sDenNo = sDenNo.substring(1, 9);
        return sDenNo.equals(sRetDenNo);
    }

    /**
     * 商品コード妥当性チェック処理.
     * 
     * @param sShnCd 商品コード
     * @param sTenCd 店舗コード
     * @param sDate 日付
     * @return 0:ＯＫ 1:M007未存在 2:M009未存在 3:未稼動 9:異常終了
     */
    public int chkShohinCode(String sShnCd, String sTenCd, String sDate) {
        // 商品ｺｰﾄﾞ未入力時はﾁｪｯｸ処理なし（正常終了）
        if (sShnCd.trim().length() == 0) {
            return 0;
        }

        if (sTenCd.trim().length() == 0) {
            return 0;
        }
        if (sDate.trim().length() == 0) {
            return 0;
        }

        // 基本情報Ｍ未存在
        String sShnnm = getComKihonName(sShnCd, sDate);
        if (sShnnm.trim().length() == 0) {
            return 1;
        }

        // 店別商品Ｍ未存在
        String sActkbn = getComActkbn(sShnCd, sTenCd, sDate);
        if (sActkbn.trim().length() == 0) {
            return 2;
        }

        // 未稼動
        if (CN_SHN_KYUSI.equals(sActkbn)) {
            return 3;
        }
        return 0;
    }

    /**
     * 基本情報マスタからの商品名称取得処理.
     * 
     * @param sJanCode 商品コード
     * @param sDate 日付
     * @return 商品名称 （漢字）
     */
    public String getComKihonName(String sJanCode, String sDate) {
        String sRet = "";
        // 14桁JANｺｰﾄﾞに変換
        sJanCode = cnvJanToJan13(sJanCode);

        if (sJanCode.trim().length() == 0) {
            return sRet;
        }

        if (sDate.trim().length() == 0) {
            return sRet;
        }

        // 基本情報マスタ検索オブジェクト作成
        M007kijmExample example = new M007kijmExample();

        example.createCriteria().andJanCdEqualTo(sJanCode);

        example.setSearchDate(sDate);

        List<M007kijm> list = m007kijmMapper.selectByExample(example);
        if (list.size() > 0) {
            if (!CCSICommon.TYPE_ACT_KBN_DELETED.equals(list.get(0).getActKbn())) {
                sRet = CCStringUtil.trimBoth(list.get(0).getShnNm());
            }
        }

        return sRet;
    }

    /**
     * 店舗稼動取得処理.
     * 
     * @param sShnCd 商品コード
     * @param sTenCd 店舗コード
     * @param sDate 日付
     * @return 稼動区分
     */
    public String getComActkbn(String sShnCd, String sTenCd, String sDate) {

        String sRet = "";

        // 13桁JANｺｰﾄﾞに変換
        sShnCd = cnvJanToJan13(sShnCd);

        if (sShnCd.trim().length() == 0) {
            return sRet;
        }

        if (sTenCd.trim().length() == 0) {
            return sRet;
        }
        if (sDate.trim().length() == 0) {
            return sRet;
        }

        M009msymExample example = new M009msymExample();

        example.createCriteria().andJanCdEqualTo(sShnCd).andTenCdEqualTo(sTenCd);
        example.setSearchDate(sDate);

        List<M009msym> list = m009msymMapper.selectByExample(example);

        if (list.size() > 0) {
            sRet = CCStringUtil.trimBoth(list.get(0).getActKbn());
        }

        return sRet;
    }

    /**
     * 部門チェック処理.
     * 
     * @param sBmnCd 部門コード（５桁）
     * @param sShnCd 商品コード
     * @param sTenCd 店舗コード（７桁）
     * @param sDate 日付
     * @return true:ＯＫ false:ＮＧ
     */
    public boolean chkBumonCode(String sBmnCd, String sShnCd, String sTenCd, String sDate) {

        // 14桁JANｺｰﾄﾞに変換
        sShnCd = cnvJanToJan13(sShnCd);

        if (sBmnCd.trim().length() == 0) {
            return false;
        }

        if (sShnCd.trim().length() == 0) {
            return false;
        }
        if (sTenCd.trim().length() == 0) {
            return false;
        }
        if (sDate.trim().length() == 0) {
            return false;
        }

        M009msymExample example = new M009msymExample();

        example.createCriteria().andJanCdEqualTo(sShnCd).andTenCdEqualTo(sTenCd);

        example.setSearchDate(sDate);

        List<M009msym> list = m009msymMapper.selectByExample(example);

        String sMstBmnCd = "";
        if (list.size() > 0) {
            sMstBmnCd = CCStringUtil.trimBoth(list.get(0).getBmnCd());
        }

        // 入力部門と店別商品Ｍ部門チェック
        if (!sBmnCd.equals(sMstBmnCd)) {
            return false;
        }

        return true;
    }

    /**
     * 税区分の取得.
     * 
     * @param sJan ＪＡＮコード
     * @param sDate 日付
     * @param sTenCd 店舗コード(7桁)
     * @param sBmnCd 部門コード(5桁)
     * @return 検索 した税区分
     */
    public String getTaxKbn(String sJan, String sDate, String sTenCd, String sBmnCd) {

        String sRet = "";
        String sWhereDate = "";

        sWhereDate = CCDateUtil.getDateFormat(sDate, CCDateUtil.DATETIME_FORMAT_DATE_YYYYMMDD);

        // 基本情報Ｍから税区分の取得
        if (!CCStringUtil.isEmpty(sJan)) {
            M007kijmExample example7 = new M007kijmExample();
            example7.createCriteria().andJanCdEqualTo(sJan);
            example7.setSearchDate(sWhereDate);

            List<M007kijm> list7 = m007kijmMapper.selectByExample(example7);
            if (list7.size() > 0) {
                if (!CCSICommon.TYPE_ACT_KBN_DELETED.equals(list7.get(0).getActKbn())) {
                    // 税区分の取得
                    sRet = CCStringUtil.trimBoth(list7.get(0).getTaxKbn());
                    // 税区分がゼロ以外ならExit
                    if (!sRet.equals("0")) {
                        return sRet;
                    }
                }
            }
        }

        M004tbumExample example4 = new M004tbumExample();

        // 店部門Ｍから税区分の取得
        example4.createCriteria().andTenCdEqualTo(sTenCd).andBmnCdEqualTo(sBmnCd);
        example4.setSearchDate(sWhereDate);
        List<M004tbum> list4 = m004tbumMapper.selectByExample(example4);

        if (list4.size() > 0) {
            if (!CCSICommon.TYPE_ACT_KBN_DELETED.equals(list4.get(0).getActKbn())) {
                // 税区分の格納
                sRet = CCStringUtil.trimBoth(list4.get(0).getTaxKbn());
                // 税区分がゼロ以外ならExit
                if (!sRet.equals("0")) {
                    return sRet;
                }
            }
        }

        // 部門Ｍから税区分の取得
        M003bmnmExample example3 = new M003bmnmExample();
        example3.createCriteria().andBmnCdEqualTo(sBmnCd);
        example3.setSearchDate(sWhereDate);

        List<M003bmnm> list3 = m003bmnmMapper.selectByExample(example3);

        if (list3.size() > 0) {
            if (!CCSICommon.TYPE_ACT_KBN_DELETED.equals(list3.get(0).getActKbn())) {
                // 税区分の格納
                sRet = CCStringUtil.trimBoth(list3.get(0).getTaxKbn());
                if (!sRet.equals("0")) {
                    return sRet;
                }
            }
        }
        // 上記のマスタから税率が取得出来ない場合は異常終了
        return null;
    }

    /**
     * 外税と内税の判断.
     * 
     * @param sZeiKbn 税区分
     * @return 内税か外税
     */
    public String getExcOrIndTax(String sZeiKbn) {

        String sRet = "";

        // 渡された税区分の確認
        sZeiKbn = sZeiKbn.trim();
        if (sZeiKbn.equals("")) {
            return null;
        }
        // 税区分が内税扱いか確認する
        if (sZeiKbn.equals(CCSIConst.TAX_IN_KBN.TAX_IN_KBN_1) || sZeiKbn.equals(CCSIConst.TAX_IN_KBN.TAX_IN_KBN_2)
                || sZeiKbn.equals(CCSIConst.TAX_IN_KBN.TAX_IN_KBN_3)
                || sZeiKbn.equals(CCSIConst.TAX_IN_KBN.TAX_IN_KBN_4)) {
            // 内税のセット
            sRet = CCSIConst.TAX_IO_KBN.TAX_IN_KBN;
        }
        // 税区分が外税扱いか確認する
        if (sZeiKbn.equals(CCSIConst.TAX_EX_KBN.TAX_EX_KBN_5) || sZeiKbn.equals(CCSIConst.TAX_EX_KBN.TAX_EX_KBN_6)
                || sZeiKbn.equals(CCSIConst.TAX_EX_KBN.TAX_EX_KBN_7)
                || sZeiKbn.equals(CCSIConst.TAX_EX_KBN.TAX_EX_KBN_8)) {
            // 外税のセット
            sRet = CCSIConst.TAX_IO_KBN.TAX_EX_KBN;
        }
        return sRet;
    }

    /**
     * 売価還元区分の取得.
     * 
     * @param sTenCd 店舗コード(7桁)
     * @param sBmnCd 部門コード(5桁)
     * @param strbasedate 日付
     * @return 売価還元区分
     */
    public String getBaikankbn(String sTenCd, String sBmnCd, String strbasedate) {

        String sRet = "";

        // 還元区分取得用Sqlの作成と取得
        M004tbumExample example = new M004tbumExample();

        example.createCriteria().andTenCdEqualTo(sTenCd.trim()).andBmnCdEqualTo(sBmnCd.trim());

        example.setSearchDate(strbasedate);

        List<M004tbum> list = m004tbumMapper.selectByExample(example);

        if (list.size() > 0) {
            if (!CCSICommon.TYPE_ACT_KBN_DELETED.equals(list.get(0).getActKbn())) {
                // 店部門マスタから売価還元区分を取得
                sRet = list.get(0).getBaiKanKbn();
            }
        }

        return sRet;
    }

    /**
     * 原単価取得処理.
     * 
     * @param sShnCd 商品コード
     * @param sTenCd7 店舗コード（７桁）
     * @param sDate 日付
     * @return 原単価
     */
    public String getGenTanka(String sShnCd, String sTenCd7, String sDate) {

        String sRet = "";

        // 14桁JANｺｰﾄﾞに変換
        sShnCd = cnvJanToJan13(sShnCd);

        if (sShnCd.trim().length() == 0) {
            return "0";
        }
        if (sTenCd7.trim().length() == 0) {
            return "0";
        }
        if (!CCDateUtil.isDate(sDate)) {
            return "0";
        }

        T001kksyChkTeiTok param = new T001kksyChkTeiTok();
        param.setHatDd(sDate);
        param.setTenCd(sTenCd7);
        param.setJanCd(sShnCd);
        param.setKaisyaCd(sTenCd7.substring(0, 2));
        param.setJigyobuCd(sTenCd7.substring(2, 4));

        List<T001kksyChkTeiTokResult> ls =
                dao.selectMany("selectT001kksyT002kkms01", param, T001kksyChkTeiTokResult.class);

        double dblMstGen = 0;
        // 特売チェック
        if (ls.size() > 0) {
            dblMstGen = ls.get(0).getTokuGenk().doubleValue();
        }

        M009msymExample example = new M009msymExample();

        // 特売でない場合は店別商品別Ｍより取得
        if (dblMstGen == 0) {
            example.createCriteria().andJanCdEqualTo(sShnCd).andTenCdEqualTo(sTenCd7);

            example.setSearchDate(sDate);

            List<M009msym> list = m009msymMapper.selectByExample(example);

            if (list.size() > 0) {
                dblMstGen = CCStringUtil.cnvStrToDbl(list.get(0).getGenk() + "");
            }
        }

        sRet = String.valueOf(dblMstGen);
        return sRet;
    }

    /**
     * 仕入月次月取得処理.
     * 
     * @return 仕入月次月
     */
    public String getComGetuji() {
        String sRet = "";

        // コントロールマスタ検索オブジェクト作成
        M020ctlmExample example = new M020ctlmExample();
        example.createCriteria().andCdEqualTo("DATE");

        List<M020ctlm> list = m020ctlmMapper.selectByExample(example);
        if (list.size() > 0) {
            sRet = list.get(0).getData().substring(24, 30);
        }
        return sRet;

    }

    /**
     * 仕入月次月取得処理.
     * 
     * @return 仕入月次月
     */
    public String getSysUnyobi() {
        String sysUnyobi = "";

        // コントロールマスタ検索オブジェクト作成
        M020ctlmExample example = new M020ctlmExample();
        example.createCriteria().andCdEqualTo("DATE");

        List<M020ctlm> list = m020ctlmMapper.selectByExample(example);
        if (list.size() > 0) {
            sysUnyobi = list.get(0).getData();
            if (sysUnyobi.length() < 8) {
                return "";
            }
            sysUnyobi = sysUnyobi.substring(0, 8);
        }
        return sysUnyobi;

    }

    /**
     * Get Getuji field from M020ctlm table.
     * 
     * @return date month and year
     */
    public String getComGetujiFull() {
        M020ctlmExample example = new M020ctlmExample();
        example.createCriteria().andCdEqualTo("DATE");

        List<M020ctlm> list = m020ctlmMapper.selectByExample(example);
        String date = "";
        if (list.size() > 0) {
            date = list.get(0).getData().substring(24, 30);
        }
        return getComGetujiFullDate(date);
    }

    /**
     * Process the Getuji date from a date.
     * 
     * @param date date
     * @return full date
     */
    public static String getComGetujiFullDate(String date) {
        String fullDate = "";

        DateTimeFormatter fmt = DateTimeFormat.forPattern("yyyyMM");

        DateTime d1 = fmt.parseDateTime(date);
        d1 = d1.plusMonths(1);
        int d = d1.dayOfMonth().getMaximumValue();

        fullDate = fmt.print(d1);

        fullDate += d;

        return fullDate;
    }

    /**
     * Get NRuteNm field from M017meim table
     * 
     * @param date date
     * @param ruteCd ruteCd
     * @return return name rute
     */
    public String getComNRuteNm(String date, String ruteCd) {

        M017meimExample example = new M017meimExample();

        String sCD = "NHN_RUTE_" + ruteCd;
        String ruteNm = "";

        example.createCriteria().andCdKbnEqualTo("SIR").andCdEqualTo(sCD);

        example.setSearchDate(date);

        List<M017meim> list = m017meimMapper.selectByExample(example);
        if (list.size() > 0) {
            if (!CCSICommon.TYPE_ACT_KBN_DELETED.equals(list.get(0).getActKbn())) {
                return CCStringUtil.trimBoth(list.get(0).getNm());
            }
        }
        return ruteNm;
    }

    /**
     * 店部門マスタへの店舗、部門の存在確認.
     * 
     * @param sTenpoCd 店舗コード（7桁：(会社コード + 事業部コード + 店舗コード)）
     * @param sBumonCd 部門コード（5桁：(事業部コード + 部門コード)）
     * @param sDate 日付
     * @return Boolean true：正常 false：存在しないorエラー
     */
    public boolean existTenBumon(String sTenpoCd, String sBumonCd, String sDate) {
        M004tbumExample example = new M004tbumExample(); // 店部門Ｍ
        String sBaseDate = CCDateUtil.getDateFormat(sDate, CCDateUtil.DATETIME_FORMAT_DATE_YYYYMMDD);
        String sTenpo = sTenpoCd;
        String sBumon = sBumonCd;

        example.createCriteria().andTenCdEqualTo(sTenpo).andBmnCdEqualTo(sBumon);
        example.setSearchDate(sBaseDate);

        List<M004tbum> list = m004tbumMapper.selectByExample(example);
        if (list.size() > 0) {
            if (!CCSICommon.TYPE_ACT_KBN_DELETED.equals(list.get(0).getActKbn())) {
                // 店部門Ｍからレコード数の取得
                return true;
            }
        }
        return false;
    }

    /**
     * 取引先マスタからの取引先区分取得処理.
     * 
     * @param sToricd 取引先コード
     * @param sDate 日付
     * @return 取引先区分
     */
    public String getComToriKbn(String sToricd, String sDate) {
        String sRet = "";
        if (sToricd.trim().length() == 0) {
            return sRet;
        }
        if (sDate.trim().length() == 0) {
            return sRet;
        }

        // 取引先マスタ検索オブジェクト作成
        M011trimExample example = new M011trimExample();
        example.createCriteria().andTriCdEqualTo(sToricd);

        example.setSearchDate(sDate);
        List<M011trim> list = m011trimMapper.selectByExample(example);
        if (list.size() > 0) {
            if (!CCSICommon.TYPE_ACT_KBN_DELETED.equals(list.get(0).getActKbn())) {
                // 取引先区分
                sRet = CCStringUtil.trimBoth(list.get(0).getHatTriKbn());
            }
        }
        return sRet;
    }

    /**
     * 税率の取得.
     * 
     * @param sJan ＪＡＮコード
     * @param sDate 日付
     * @param sTenCd 店舗コード(7桁)
     * @param sBmnCd 部門コード(5桁)
     * @return 検索した税率
     */
    public String getTaxRate(String sJan, String sDate, String sTenCd, String sBmnCd) {

        String sRet = "";
        String sRetRate = "0";
        String sWhereDate = "";

        sWhereDate = CCDateUtil.getDateFormat(sDate, CCDateUtil.DATETIME_FORMAT_DATE_YYYYMMDD);

        // マスタレコード取得用
        // 基本情報Ｍ
        M007kijmExample example7 = new M007kijmExample();
        // 店部門Ｍ
        M004tbumExample example4 = new M004tbumExample();
        // 部門Ｍ
        M003bmnmExample example3 = new M003bmnmExample();

        if (!CCStringUtil.isEmpty(sJan)) {
            // 基本情報Ｍから税区分と税率の取得
            example7.createCriteria().andJanCdEqualTo(sJan);
            example7.setSearchDate(sWhereDate);
            List<M007kijm> list7 = m007kijmMapper.selectByExample(example7);

            if (list7.size() > 0) {
                if (!CCSICommon.TYPE_ACT_KBN_DELETED.equals(list7.get(0).getActKbn())) {
                    sRet = CCStringUtil.trimBoth(list7.get(0).getTaxKbn());
                    sRetRate = CCStringUtil.trimBoth(list7.get(0).getTaxRitu() + "");
                    // 税区分がゼロ以外ならExit
                    if (!sRet.equals("0")) {
                        return sRetRate;
                    }
                }
            }
        }

        // 店部門Ｍから税区分と税率の取得
        example4.createCriteria().andTenCdEqualTo(sTenCd).andBmnCdEqualTo(sBmnCd);
        example4.setSearchDate(sWhereDate);

        List<M004tbum> list4 = m004tbumMapper.selectByExample(example4);

        if (list4.size() > 0) {
            if (!CCSICommon.TYPE_ACT_KBN_DELETED.equals(list4.get(0).getActKbn())) {
                // 税区分と税率の格納
                sRet = CCStringUtil.trimBoth(list4.get(0).getTaxKbn());
                sRetRate = CCStringUtil.trimBoth(list4.get(0).getTaxRitu() + ""); // 税区分がゼロ以外ならExit
                if (!sRet.equals("0")) {
                    return sRetRate;
                }
            }
        }

        // 部門Ｍから税区分と税率の取得
        example3.createCriteria().andBmnCdEqualTo(sBmnCd);
        example3.setSearchDate(sWhereDate);

        List<M003bmnm> list3 = m003bmnmMapper.selectByExample(example3);

        if (list3.size() > 0) {
            if (!CCSICommon.TYPE_ACT_KBN_DELETED.equals(list3.get(0).getActKbn())) {
                // 税区分と税率の格納
                sRet = CCStringUtil.trimBoth(list3.get(0).getTaxKbn());
                sRetRate = CCStringUtil.trimBoth(list3.get(0).getTaxRitu() + "");
                if (!sRet.equals("0")) {
                    return sRetRate;
                }
            }
        }

        // 上記のマスタから税率が取得出来ない場合は異常終了
        return null;
    }

    /**
     * 名称取得処理.
     * 
     * @param sCd コード
     * @return 名称
     */
    public String getSirName(String sCd) {

        String sRet = "";

        M017meimExample example = new M017meimExample();

        String sSysDate = CCDateUtil.getSysDateFormat(CCDateUtil.DATETIME_FORMAT_DATE_YYYYMMDD);
        example.createCriteria().andCdKbnEqualTo(CN_CD_SIR).andCdEqualTo(sCd);
        example.setSearchDate(sSysDate);

        List<M017meim> list = m017meimMapper.selectByExample(example);
        if (list.size() == 0) {
            return sRet;
        }

        if (!CCSICommon.TYPE_ACT_KBN_DELETED.equals(list.get(0).getActKbn())) {
            sRet = CCStringUtil.trimBoth(list.get(0).getNm());
        }

        return sRet;
    }

    /**
     * 名称取得処理.
     * 
     * @param sCd コード
     * @param sDate 日付
     * @return 名称
     */
    public String getSirName(String sCd, String sDate) {

        String sRet = "";

        M017meimExample example = new M017meimExample();
        example.createCriteria().andCdKbnEqualTo(CN_CD_SIR).andCdEqualTo(sCd);
        example.setSearchDate(sDate);
        List<M017meim> list = m017meimMapper.selectByExample(example);
        if (list.size() == 0) {
            return sRet;
        }
        if (!CCSICommon.TYPE_ACT_KBN_DELETED.equals(list.get(0).getActKbn())) {
            sRet = CCStringUtil.trimBoth(list.get(0).getNm());
        }
        return sRet;
    }

    /**
     * 取引先マスタからの取引先名(ｶﾅ)取得処理.
     * 
     * @param sToricd 取引先コード
     * @param sDate 日付
     * @return 取引先名(ｶﾅ)
     */
    public String getComToriNameA(String sToricd, String sDate) {

        String sRet = "";
        if (sToricd.trim().length() == 0) {
            return sRet;
        }
        if (sDate.trim().length() == 0) {
            return sRet;
        }

        // 取引先マスタ検索オブジェクト作成
        M011trimExample example = new M011trimExample();
        example.createCriteria().andTriCdEqualTo(sToricd);
        example.setSearchDate(sDate);

        List<M011trim> list = m011trimMapper.selectByExample(example);

        if (list.size() > 0) {
            if (!CCSICommon.TYPE_ACT_KBN_DELETED.equals(list.get(0).getActKbn())) {
                // 取引先名(カナ)
                sRet = CCStringUtil.trimBoth(list.get(0).getTriNmA());
            }
        }

        return sRet;

    }

    /**
     * 取引先マスタからの取引先名(ｶﾅ)取得処理.
     * 
     * @param sToricd 取引先コード
     * @param sDate 日付
     * @return  取引先名(ｶﾅ)
     */
    public String getComToriName(String sToricd, String sDate) {

        String sRet = "";
        if (sToricd.trim().length() == 0) {
            return sRet;
        }
        if (sDate.trim().length() == 0) {
            return sRet;
        }

        // 取引先マスタ検索オブジェクト作成
        M011trimExample example = new M011trimExample();
        example.createCriteria().andTriCdEqualTo(sToricd);
        example.setSearchDate(sDate);

        List<M011trim> list = m011trimMapper.selectByExample(example);

        if (list.size() > 0) {
            if (!CCSICommon.TYPE_ACT_KBN_DELETED.equals(list.get(0).getActKbn())) {
                // 取引先名(カナ)
                sRet = CCStringUtil.trimBoth(list.get(0).getTriNm());
            }
        }

        return sRet;

    }

    /**
     * 累計計上日処理.
     * 
     * @param sNhnDate 納品日
     * @return 累計計上日
     */
    public String getKeijoDate(String sNhnDate) {

        String sRet = "";

        if (!CCDateUtil.isDate(sNhnDate)) {
            return "";
        }

        int iNhnYM = CCStringUtil.cnvStrToInt(sNhnDate.substring(0, 6));
        String sGetuji = getComGetuji();
        int iGetuji = CCStringUtil.cnvStrToInt(sGetuji);

        if (iGetuji < iNhnYM) {
            // 月次月以降の納品日(年月)は「実納品日」をセット
            sRet = sNhnDate;
        } else {
            // 月次月≧納品日(年月)は「月次月の翌月の１日」をセット
            String sDate = CCDateUtil.doCalcDate(sGetuji + "01", "MONTH", 1);
            sRet = sDate;
        }
        return sRet;
    }

    /**
     * 確定場所(担当者所属店舗コード)から確定区分を取得.
     * 
     * @param sKakuteiPlc 所属店舗コード（確定場所）
     * @param sDate 日付
     * @return String 確定区分
     */
    public String getKakuteiKbn(String sKakuteiPlc, String sDate) {

        String sKakuteiKbn = "";

        // 日付の正規化
        String sBaseDate = CCDateUtil.getDateFormat(sDate, CCDateUtil.DATETIME_FORMAT_DATE_YYYYMMDD);
        // 確定場所より事業部コードを抜き取る
        String sJigyoCD = sKakuteiPlc.substring(2, 4);
        // 7桁店舗コード（確定場所）より店舗区分を取得する
        String sTenpoKbn = getComTenpoKubun(sKakuteiPlc, sBaseDate);


        // 確定区分の抽出
        if (sJigyoCD.equals(CCSIConst.JIGYOBU_CD.CD_FYELL)
                && sTenpoKbn.equals(CN_TENKBN_HONBU)) {
            // 事情部コード="90"かつ店舗区分="9"(本部)の場合、確定区分="01"とする。
            sKakuteiKbn = CCSIConst.ENTRY_KBN.ENTRY_KBN_01;
        } else if (sJigyoCD.equals(CCSIConst.JIGYOBU_CD.CD_FYELL)
                && sTenpoKbn.equals(CN_TENKBN_TENPO)) {
            // 事業部コード="90"かつ店舗区分="1"(店舗)の場合、確定区分="11"とする。
            sKakuteiKbn = CCSIConst.ENTRY_KBN.ENTRY_KBN_11;
        } else if (!sJigyoCD.equals(CCSIConst.JIGYOBU_CD.CD_FYELL)
                && sTenpoKbn.equals(CN_TENKBN_HONBU)) {
            // 事業部コード≠"90"かつ店舗区分="9"(本部)の場合、確定区分=事業部コード+01とする。
            int iJigyoCD = Integer.parseInt(sJigyoCD);
            int entryKbn = Integer.parseInt(CCSIConst.ENTRY_KBN.ENTRY_KBN_01);
            int iKakuteiKbn = iJigyoCD + entryKbn;
            sKakuteiKbn = CCStringUtil.puddLeft(String.valueOf(iKakuteiKbn), 2, '0');
        } else if (!sJigyoCD.equals(CCSIConst.JIGYOBU_CD.CD_FYELL)
                && sTenpoKbn.equals(CN_TENKBN_TENPO)) {
            // 事業部コード≠"90"かつ店舗区分="1"(店舗)の場合、確定区分=事業部コード+11とする。
            int iJigyoCD = Integer.parseInt(sJigyoCD);
            int entryKbn = Integer.parseInt(CCSIConst.ENTRY_KBN.ENTRY_KBN_11);
            int iKakuteiKbn = iJigyoCD + entryKbn;
            sKakuteiKbn = CCStringUtil.puddLeft(String.valueOf(iKakuteiKbn), 2, '0');
        }
        return sKakuteiKbn;
    }

    /**
     * 売単価取得処理.
     * 
     * @param sShnCd  商品コード
     * @param sTenCd7 店舗コード（７桁）
     * @param sDate 日付
     * @return 売単価
     */
    public String getBaiTanka(String sShnCd, String sTenCd7, String sDate) {

        String sRet = "";

        // 13桁JANｺｰﾄﾞに変換
        sShnCd = cnvJanToJan13(sShnCd);

        if (sShnCd.trim().length() == 0) {
            return "0";
        }

        if (sTenCd7.trim().length() == 0) {
            return "0";
        }
        if (!CCDateUtil.isDate(sDate)) {
            return "0";
        }

        T001kksyChkTeiTok param = new T001kksyChkTeiTok();
        param.setHatDd(sDate);
        param.setTenCd(sTenCd7);
        param.setJanCd(sShnCd);
        param.setKaisyaCd(sTenCd7.substring(0, 2));
        param.setJigyobuCd(sTenCd7.substring(2, 4));

        List<T001kksyChkTeiTokResult> ls =
                dao.selectMany("selectT001kksyT002kkms01", param, T001kksyChkTeiTokResult.class);

        M009msymExample example = new M009msymExample();

        long lngMstBai = 0;
        // 特売チェック
        if (ls.size() > 0) {
            lngMstBai = ls.get(0).getTokuBaik();
        }

        // 特売でない場合は店別商品別Ｍより取得
        if (lngMstBai == 0) {
            example.createCriteria().andJanCdEqualTo(sShnCd).andTenCdEqualTo(sTenCd7);

            example.setSearchDate(sDate);

            List<M009msym> list = m009msymMapper.selectByExample(example);

            if (list.size() > 0) {
                lngMstBai = CCStringUtil.cnvStrToLong(list.get(0).getBaik() + "");
            }
        }

        sRet = String.valueOf(lngMstBai);

        return sRet;
    }

    /*****************************************************************
    * 原単価取得処理.
    * @param sShnCd 商品コード
    * @param sTenCd7 店舗コード（７桁）
    * @param sDate 日付
    * @param isTokubai 特売フラグ（true：特売）
    * @return 原単価（取得出来なかった場合は、""）
    *****************************************************************/
    public String getGenTanka(String sShnCd, String sTenCd7, String sDate, boolean isTokubai) {
        String sRet = "";

        // 14桁JANｺｰﾄﾞに変換
        sShnCd = cnvJanToJan13(sShnCd);

        if (sShnCd.trim().length() == 0) {
            return "0";
        }
        if (sTenCd7.trim().length() == 0) {
            return "0";
        }
        if (!CCDateUtil.isDate(sDate)) {
            return "0";
        }

        double dblMstGen = 0;

        if (isTokubai) {
            T001kksyChkTeiTok param = new T001kksyChkTeiTok();
            param.setHatDd(sDate);
            param.setTenCd(sTenCd7);
            param.setJanCd(sShnCd);
            param.setKaisyaCd(sTenCd7.substring(0, 2));
            param.setJigyobuCd(sTenCd7.substring(2, 4));

            List<T001kksyChkTeiTokResult> ls =
                    dao.selectMany("selectT001kksyT002kkms01", param, T001kksyChkTeiTokResult.class);
            // 特売の単価が存在するか確認する
            if (ls.size() > 0) {
                // レコード存在時に特売単価セット
                dblMstGen = ls.get(0).getTokuGenk().doubleValue();
                sRet = String.valueOf(dblMstGen);
            }
        } else {
            // 特売以外(定番)の場合は店別商品別Ｍより取得
            if (dblMstGen == 0) {
                M009msymExample example = new M009msymExample();
                // 特売でない場合は店別商品別Ｍより取得
                example.createCriteria().andJanCdEqualTo(sShnCd).andTenCdEqualTo(sTenCd7);
                example.setSearchDate(sDate);

                List<M009msym> list = m009msymMapper.selectByExample(example);

                if (list.size() > 0) {
                    dblMstGen = CCStringUtil.cnvStrToDbl(list.get(0).getGenk() + "");
                    sRet = String.valueOf(dblMstGen);
                }
            }
        }
        return sRet;
    }

    /**
    * 売単価取得処理.
    * 
    * @param sShnCd 商品コード
    * @param sTenCd7 店舗コード（７桁）
    * @param sDate 日付
    * @param isTokubai 特売フラグ（true：特売）
    * @return 売単価（取得出来なかった場合は、""）
    */
    public String getBaiTanka(String sShnCd, String sTenCd7, String sDate, boolean isTokubai) {
        String sRet = "";

        // 14桁JANｺｰﾄﾞに変換
        sShnCd = cnvJanToJan13(sShnCd);

        if (sShnCd.trim().length() == 0) {
            return "0";
        }
        if (sTenCd7.trim().length() == 0) {
            return "0";
        }
        if (!CCDateUtil.isDate(sDate)) {
            return "0";
        }

        long lngMstBai = 0;

        if (isTokubai) {
            T001kksyChkTeiTok param = new T001kksyChkTeiTok();
            param.setHatDd(sDate);
            param.setTenCd(sTenCd7);
            param.setJanCd(sShnCd);
            param.setKaisyaCd(sTenCd7.substring(0, 2));
            param.setJigyobuCd(sTenCd7.substring(2, 4));

            List<T001kksyChkTeiTokResult> ls =
                    dao.selectMany("selectT001kksyT002kkms01", param, T001kksyChkTeiTokResult.class);
            // 特売の単価が存在するか確認する
            if (ls.size() > 0) {
                // レコード存在時に特売単価セット
                lngMstBai = ls.get(0).getTokuBaik();
                sRet = String.valueOf(lngMstBai);
            }
        } else {
            // 特売以外(定番)の場合は店別商品別Ｍより取得
            if (lngMstBai == 0) {
                M009msymExample example = new M009msymExample();
                // 特売でない場合は店別商品別Ｍより取得
                example.createCriteria().andJanCdEqualTo(sShnCd).andTenCdEqualTo(sTenCd7);
                example.setSearchDate(sDate);

                List<M009msym> list = m009msymMapper.selectByExample(example);
                if (list.size() > 0) {
                    lngMstBai = list.get(0).getBaik();
                    sRet = String.valueOf(lngMstBai);
                }
            }
        }
        return sRet;
    }

    /**
    * 特売原単価取得処理.
    * 
    * @param sShnCd 商品コード
    * @param sTenCd7 店舗コード（７桁）
    * @param sDate 日付
    * @return 特売原単価（取得出来なかった場合は、""）
    */
    public String getTokubaiGenTanka(String sShnCd, String sTenCd7, String sDate) {
        String sRet = "";
        double dblMstGen = 0;
        // 14桁JANｺｰﾄﾞに変換
        sShnCd = cnvJanToJan13(sShnCd);

        if (sShnCd.trim().length() == 0) {
            return "0";
        }
        if (sTenCd7.trim().length() == 0) {
            return "0";
        }
        if (!CCDateUtil.isDate(sDate)) {
            return "0";
        }

        sDate = CCDateUtil.getDateFormat(sDate, CCDateUtil.DATETIME_FORMAT_DATE_YYYYMMDD);

        // 特売単価取得用
        T001kksyChkTeiTok param = new T001kksyChkTeiTok();
        param.setHatDd(sDate);
        param.setTenCd(sTenCd7);
        param.setJanCd(sShnCd);
        param.setKaisyaCd(sTenCd7.substring(0, 2));
        param.setJigyobuCd(sTenCd7.substring(2, 4));

        List<T001kksyChkTeiTokResult> ls =
                dao.selectMany("selectT001kksyT002kkms01", param, T001kksyChkTeiTokResult.class);

        // 特売の単価が存在するか確認する
        if (ls.size() > 0) {
            dblMstGen = ls.get(0).getTokuGenk().doubleValue();
            sRet = String.valueOf(dblMstGen);
        }
        return sRet;
    }

    /**
     * 店舗マスタからの店舗略称取得処理.
     * 
     * @param sTencd 店舗コード（７桁）
     * @param sDate 日付
     * @return 店舗略称
     */
    public String getComTenpoNameR(String sTencd, String sDate) {
        String sRet = "";
        if (sTencd.trim().length() == 0) {
            return sRet;
        }
        if (sDate.trim().length() == 0) {
            return sRet;
        }

        // 取引先マスタ検索オブジェクト作成
        M006tenmExample example = new M006tenmExample();

        example.createCriteria().andTenCdEqualTo(sTencd);
        example.setSearchDate(sDate);

        List<M006tenm> list = m006tenmMapper.selectByExample(example);

        if (list.size() > 0) {
            if (!CCSICommon.TYPE_ACT_KBN_DELETED.equals(list.get(0).getActKbn())) {
                // 取引先名(カナ)
                sRet = CCStringUtil.trimBoth(list.get(0).getTenNmR1());
            }
        }

        return sRet;
    }

    /**
    * 伝票取得処理.
    * <p>
    * 機能概要：<br>
    * 自動符番される伝票を取得します。<br>
    * <p> 
    * @param sDenKbn 伝区
    * @param sKaiCd2 会社コード（２桁）
    * @param sTenpoCd7 店舗コード（７桁：会社＋事業部＋店舗）
    * @return 伝票番号（処理番号７桁） エラーの場合は、""
    */
    @Transactional
    public String getDenpyoNo(String sDenKbn, String sKaiCd2, String sTenpoCd7) {
        String sRet = "";
        // 採番区分
        String saiBankKbn = CON_SAIBAN_KBN;
        // 伝票区分
        String denKbn = CCStringUtil.isEmpty(sDenKbn) ? BLANK : sDenKbn;
        // 会社コード
        String kaisyaCd = CCStringUtil.isEmpty(sKaiCd2) ? BLANK : sKaiCd2;
        // 店舗コード
//        String tenpoCd = CCStringUtil.isEmpty(sTenpoCd7) ? "       " : sTenpoCd7;

        Map<String, String> params = new HashMap<String, String>();
        params.put("saibanKbn", saiBankKbn);
        params.put("denKbn", denKbn);
        params.put("kaisyaCd", kaisyaCd);
// 伝票番号の採番には店舗コードを使わない。7桁のスペースを指定したいが検索時に
// 1桁に変わってしまい検索できないため、仕方なく店舗コードを条件から外す。
//        params.put("tenCd", tenpoCd);

        M022sibn m022sibn = m022sibnMapper.selectM022sibnByParams(params);

        if (m022sibn != null) {
            sRet = CCStringUtil.trimBoth(m022sibn.getPresentNo());
            long preNum = Long.valueOf(sRet);
            long startNum = Long.valueOf(m022sibn.getStartNo());
            long endNum = Long.valueOf(m022sibn.getEndNo());
            preNum = preNum + 1;
            if (preNum >= endNum) {
                preNum = startNum;
            }
            String preNo = new String(CCStringUtil.suppZero(Long.toString(preNum), 10));
            M022sibn m022 = new M022sibn();
            m022.setPresentNo(preNo);
            m022.setCmnUpddd(CCDateUtil.getSysDateFormat(CCDateUtil.DATETIME_FORMAT_DATE_YYYYMMDD));
            m022.setCmnUpdtime(CCDateUtil.getSysDateFormat(CCDateUtil.DATETIME_FORMAT_TIME));

            M022sibnExample example = new M022sibnExample();
            example.createCriteria().andSaibanKbnEqualTo(saiBankKbn).andDenKbnEqualTo(denKbn)
//                    .andKaisyaCdEqualTo(kaisyaCd).andTenCdEqualTo(tenpoCd);
                    .andKaisyaCdEqualTo(kaisyaCd);

            if (m022sibnMapper.updateByExampleSelective(m022, example) <= 0) {
                throw new ChaseException(Messages.get(CCMessageConst.MSG_KEY_EXCEPTION_UPDATE_NO_RECORD));
            }

            return sRet;
        } else {
            return "";
        }
    }

    /**
    * 取引先マスタからの発注店舗コード取得処理.
    * <p>
    * 機能概要：<br>
    * 共通部品を使用して、取引先コードより発注店舗コード<br>
    * を取得します。<br>
    * <p> 
    * @param sToricd 取引先コード
    * @param sDate 運用日
    * @return 発注店舗コード
    */
    public String getComHatTenCd(String sToricd, String sDate) {
        String sRet = "";
        if (sToricd.trim().length() == 0) {
            return sRet;
        }
        if (sDate.trim().length() == 0) {
            return sRet;
        }

        // 取引先マスタ検索オブジェクト作成
        M011trimExample example = new M011trimExample();
        example.createCriteria().andTriCdEqualTo(sToricd);
        example.setSearchDate(sDate);

        // int retval = m011trim.custumexecute(sDate, strwhere);
        List<M011trim> list = m011trimMapper.selectByExample(example);

        if (list.size() > 0) {
            if (!CCSICommon.TYPE_ACT_KBN_DELETED.equals(list.get(0).getActKbn())) {
                // 発注店舗コード
                sRet = CCStringUtil.trimBoth(list.get(0).getHatTenCd());
            }
        }
        return sRet;
    }

    /*****************************************************************
    * 部門マスタからの部門名(漢字)取得処理.
    * <p>
    * 機能概要：<br>
    * 共通部品を使用して、部門コードより部門名(漢字)を取得<br>
    * します。<br>
    * @param sBmncd 部門コード（５桁）
    * @param sDate 日付
    * @return 部門名(漢字)
    *****************************************************************/
    public String getComBumonName(String sBmncd, String sDate) {
        String sRet = "";
        if (sBmncd.trim().length() == 0) {
            return sRet;
        }

        if (sDate.trim().length() == 0) {
            return sRet;
        }

        // 部門マスタ検索オブジェクト作成
        M003bmnmExample example = new M003bmnmExample();
        example.createCriteria().andBmnCdEqualTo(sBmncd);
        example.setSearchDate(sDate);

        List<M003bmnm> result = m003bmnmMapper.selectByExample(example);

        if (result.size() > 0) {
            if (!CCSICommon.TYPE_ACT_KBN_DELETED.equals(result.get(0).getActKbn())) {
                // 部門名（ｶﾅ）
                sRet = CCStringUtil.trimBoth(result.get(0).getBmnNm());
            }
        }

        return sRet;
    }

    /**
     * Ｆエール扱い区分取得処理<BR>
     * @param sTenCd7     店舗コード（7桁）
     * @param sBmnCd     部門コード（5桁）
     * @param sHatDd     発行日
     * @return String    Ｆエール扱い区分
     **/
    public String getFAKubun(String sTenCd7, String sBmnCd, String sHatDd) {
        String sFAkbn = BLANK;
        M004tbumExample m004tbumExample = new M004tbumExample();
        m004tbumExample.createCriteria().andTenCdEqualTo(sTenCd7).andBmnCdEqualTo(sBmnCd);
        m004tbumExample.setSearchDate(sHatDd);

        List<M004tbum> m004tbums = m004tbumMapper.selectByExample(m004tbumExample);
        if (m004tbums.size() == 0) {
            return sFAkbn;
        }
        if (!CCSICommon.TYPE_ACT_KBN_DELETED.equals(m004tbums.get(0).getActKbn())) {
            sFAkbn = m004tbums.get(0).getFyKbn();
        }
        return sFAkbn;
    }

    /*****************************************************************
     * 金額計算処理.
     * 機能概要：<br>
    *　グリッド内の数量と単価より金額を取得します。<br>
     * @param sSuryo
     * 数量
     * @param sTanka
     *  単価
     * @return
     * String 数量×単価(小数点以下切捨て)
     *****************************************************************/
    public String getKingak(String sSuryo, String sTanka) {
        String sKingak = "";
        double dblSuryo = CCStringUtil.cnvStrToDbl(sSuryo);
        double dblTanka = CCStringUtil.cnvStrToDbl(sTanka);

        if (dblSuryo == 0 || dblTanka == 0) {
            return "0";
        }

        // 金額＝数量×単価
        double dblKingak = CCNumberUtil.multiplyDouble(dblSuryo, dblTanka);

        // 少数点以下を切捨て
        long lngKingak = (long) dblKingak;
        sKingak = String.valueOf(lngKingak);
        return sKingak;

    }

    /**
     * 1．ＪＡＮコード変換処理
     * @param janCd 商品コード
     * @return 商品コード
     */
    public String cnvJanToJan14(String janCd) {
        if (janCd == null || EMPTY.equals(janCd.trim())) {
            return EMPTY;
        } else if (janCd.length() <= 13) {
            String temp = " " + CCStringUtil.suppZero(janCd, 13);
            return temp;
        } else {
            String temp = " " + janCd.substring(0, 13);
            return temp;
        }
    }
}
