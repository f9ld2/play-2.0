///////////////////////////////////////////////////////////////////////
// Copyright(C) 2015 NEC Solution Innovators, Ltd. All Rights Reserved.
///////////////////////////////////////////////////////////////////////

/* ====================================================================
 * 機能名称 : CCSICommon
 * 改版履歴
 * Rev. 改版年月日   改版者名       内容
 * 1.0   2014.04.07   Hungtb      新規作成
 * ====================================================================
 */package jp.co.necsoft.web_md.core.common.biz.kk;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import jp.co.necsoft.web_md.common.ChaseException;
import jp.co.necsoft.web_md.core.common.CCMessageConst;
import jp.co.necsoft.web_md.core.common.mybatis.dao.K008trhkMapper;
import jp.co.necsoft.web_md.core.common.mybatis.dao.M000kaimMapper;
import jp.co.necsoft.web_md.core.common.mybatis.dao.M011trimMapper;
import jp.co.necsoft.web_md.core.common.mybatis.dao.M017meimMapper;
import jp.co.necsoft.web_md.core.common.mybatis.dao.M018bnkmMapper;
import jp.co.necsoft.web_md.core.common.mybatis.dao.M022sibnMapper;
import jp.co.necsoft.web_md.core.common.mybatis.dto.CodeMaster;
import jp.co.necsoft.web_md.core.common.mybatis.dto.K008trhk;
import jp.co.necsoft.web_md.core.common.mybatis.dto.K008trhkExample;
import jp.co.necsoft.web_md.core.common.mybatis.dto.M000kaim;
import jp.co.necsoft.web_md.core.common.mybatis.dto.M000kaimExample;
import jp.co.necsoft.web_md.core.common.mybatis.dto.M011trim;
import jp.co.necsoft.web_md.core.common.mybatis.dto.M011trimExample;
import jp.co.necsoft.web_md.core.common.mybatis.dto.M017meim;
import jp.co.necsoft.web_md.core.common.mybatis.dto.M017meimExample;
import jp.co.necsoft.web_md.core.common.mybatis.dto.M017meimExample.Criteria;
import jp.co.necsoft.web_md.core.common.mybatis.dto.M018bnkm;
import jp.co.necsoft.web_md.core.common.mybatis.dto.M018bnkmExample;
import jp.co.necsoft.web_md.core.common.mybatis.dto.M022sibn;
import jp.co.necsoft.web_md.core.common.mybatis.dto.M022sibnExample;
import jp.co.necsoft.web_md.core.common.utils.CCDateUtil;
import jp.co.necsoft.web_md.core.common.utils.CCNumberUtil;
import jp.co.necsoft.web_md.core.common.utils.CCStringUtil;

import org.mybatis.guice.transactional.Transactional;

import play.i18n.Messages;
import play.mvc.Controller;

/**
 * 
 * cmJKKCommon クラス
 *
 */
public class CCKKCommon extends Controller {
    /** M017MEIM Mappper */
    @Inject
    private M017meimMapper m017meimMapper;
    /** K008TRHK Mappper */
    @Inject
    private K008trhkMapper k008trhkMapper;
    /** M022SIBN Mappper */
    @Inject
    private M022sibnMapper m022sibnMapper;
    /** M011trimMapper */
    @Inject
    private M011trimMapper m011trimMapper;
    /** M000kaim Mapper */
    @Inject
    private M000kaimMapper m000kaimMapper;
    /** M018bnkmMapper Mapper */
    @Inject
    private M018bnkmMapper m018bnkmMapper;

    /** cmKKTax: use in getKingakuToTax() method */
    @Inject
    private CCKK0010Tax cmKKTax;

    /* Const from cmJKKBeans file */
    /** Const from cmJKKBeans: CON_KAISHA_CODE = "00" */
    public static final String CON_KAISHA_CODE = "00";
    /** Const from cmJKKBeans: CON_TORIHIKISAKI_CODE = "000" */
    public static final String CON_TORIHIKISAKI_CODE = "000";
    /** Const from cmJKKBeans: CON_SAIBAN_KBN = "01" */
    public static final String CON_SAIBAN_KBN = "01";
    /** BLANK = " " */
    private static final String BLANK = " ";
    /** CON_TBLCOL_JM017MEIM = 13 */
    public static final int CON_TBLCOL_JM017MEIM = 13;
    /**
     * m_sKaisyaCode
     */
    private String kaisyaCode = "";
    /**
     * m_sTorihikisakiCode
     */
    private String torihikisakiCode = "";
    /**
     * m_sTCodeEosSyori
     */
    private String tCodeEosSyori = "";
    /**
     * m_sTCodeShrOnline
     */
    private String tCodeShrOnline = "";
    /**
     * m_sTCodeShrMeisai
     */
    private String tCodeShrMeisai = "";

    /**
     * コンストラクタ
     */
    public CCKKCommon() {
        super();
        clear();
    }

    /**
    * 名称M検索用キー定数
    *  ※名称Mを検索する際のキーに使用する
    */
    public static final class CON_JK008TRHK_KEY {

        /**
         * コード区分（買掛）
         */
        private static final String CODE_KBN = "KAI";

        /**
         * 取引項目M:科目コード
         */
        private static final String KMK_CD = "KMK_CD";

        /** 
         * 取引先M：締め区分、支払区分
         */
        private static final class SIME_PAY {
            /** ５日毎 */
            private static final String KBN0505 = "SIME_PAY_KBN0505";
            /** １０日毎 */
            private static final String KBN1010 = "SIME_PAY_KBN1010";
            /** １５日毎 */
            private static final String KBN1515 = "SIME_PAY_KBN1515";
            /** １５日毎締１０日後払 */
            private static final String KBN1510 = "SIME_PAY_KBN1510";
            /** １０日払い */
            private static final String KBN3010 = "SIME_PAY_KBN3010";
            /** １５日払い */
            private static final String KBN3015 = "SIME_PAY_KBN3015";
            /** 翌月末払い */
            private static final String KBN3030 = "SIME_PAY_KBN3030";
            /** 翌々月末払い */
            private static final String KBN3060 = "SIME_PAY_KBN3060";
            /** 手形 */
            private static final String KBN3090 = "SIME_PAY_KBN3090";
            /** 小口・その他 */
            private static final String KBN9901 = "SIME_PAY_KBN9901";
        }

        /**
         *  取引先M：買掛精算区分
         */
        private static final class SEISAN_KBN {
            /** FB */
            private static final String KBN1 = "SEISAN_KBN1";
            /** 手払 */
            private static final String KBN2 = "SEISAN_KBN2";
            /** 現金 */
            private static final String KBN3 = "SEISAN_KBN3";
            /** 小口 */
            private static final String KBN4 = "SEISAN_KBN4";
            /** 手形 */
            private static final String KBN5 = "SEISAN_KBN5";
            /** 自振 */
            private static final String KBN6 = "SEISAN_KBN6";
        }

        /**
         *  支払F：支払伝票区分
         */
        private static final class SHR_DEN {
            /** オンライン振込 */
            private static final String KBN90 = "SHR_DEN_KBN90";
            /** 銀行振込（手書） */
            private static final String KBN91 = "SHR_DEN_KBN91";
            /** 赤残入力 */
            private static final String KBN92 = "SHR_DEN_KBN92";
            /** 店小口 */
            private static final String KBN93 = "SHR_DEN_KBN93";
            /** 本部現金 */
            private static final String KBN94 = "SHR_DEN_KBN94";
            /** 手形 */
            private static final String KBN95 = "SHR_DEN_KBN95";
        }

        /**
         *  支払F：状態区分
         */
        private static final class JOTAI {
            /** 登録 */
            private static final String KBN1 = "JOTAI_KBN1";
            /** 確定 */
            private static final String KBN2 = "JOTAI_KBN2";
            /** 保留 */
            private static final String KBN3 = "JOTAI_KBN3";
            /** 支払済 */
            private static final String KBN4 = "JOTAI_KBN4";
        }

        /**
         *  取引項目M:税区分
         */
        private static final class TAX_KBN {
            /** 外税 */
            private static final String KBN1 = "TAX_KBN1";
            /** 内税 */
            private static final String KBN2 = "TAX_KBN2";
            /** 非課税 */
            private static final String KBN3 = "TAX_KBN3";
        }

        /**
         *  取引項目M:勘定区分
         */
        private static final class KANJO_KBN {
            /** 支払勘定 */
            private static final String KBN1 = "KANJO_KBN1";
            /** 相殺勘定 */
            private static final String KBN2 = "KANJO_KBN2";
        }

        /**
         *  仕入戻しF:種別
         */
        @SuppressWarnings("unused")
        private static final class SYUBETU {
            /** 仕入戻し */
            private static final String KBN1 = "SYUBETU1";
            /** 調整金額 */
            private static final String KBN2 = "SYUBETU2";
        }

        /**
         *  仕入戻しF:入力区分
         */
        @SuppressWarnings("unused")
        private static final class INP_KBN {
            /** 画面エントリー */
            private static final String KBN1 = "INP_KBN1";
            /** 会計IF */
            private static final String KBN2 = "INP_KBN2";
        }

        /**
         *  ｾﾝﾀｰ集計ﾌｧｲﾙ:DC/TC区分
         */
        private static final class DCTC_KBN {
            /** 店着 */
            private static final String KBN0 = "DCTC_KBN0";
            /** 予備 */
            private static final String KBN6 = "DCTC_KBN6";
            /** 片岡運送 */
            private static final String KBN7 = "DCTC_KBN7";
            /** 北部市場 */
            private static final String KBN8 = "DCTC_KBN8";
            /** 鳴尾浜市場 */
            private static final String KBN9 = "DCTC_KBN9";
        }

        /** ｾﾝﾀｰ集計ﾌｧｲﾙ:地区ｺｰﾄﾞ */
        private static final class TIKU_CD {
            /** 多治見地区 */
            private static final String KBN1 = "TIKU_CD01";
            /** 北陸地区 */
            private static final String KBN2 = "TIKU_CD02";
        }

        /**
         *  ｾﾝﾀｰ集計ﾌｧｲﾙ:品番
         */
        @SuppressWarnings("unused")
        private static final class HINBAN {
            /** 物流費 */
            private static final String KBN1 = "HINBAN001";
            /** Vプラコン使用料 */
            private static final String KBN2 = "HINBAN003";
            /** 部門シール */
            private static final String KBN3 = "HINBAN101";
            /** 納品伝票 */
            private static final String KBN4 = "HINBAN102";
            /** その他 */
            private static final String KBN5 = "HINBAN103";
        }
    }

    /**
     * 
     */
    public static final class COLNO_JK008TRHK {
        /** KAISYA_CD = 0 */
        public static final int KAISYA_CD = 0;
        /** KAK_TORI_KMK = 1 */
        public static final int KAK_TORI_KMK = 1;
        /** TORI_NM = 2 */
        public static final int TORI_NM = 2;
        /** KANJO_KBN = 3 */
        public static final int KANJO_KBN = 3;
        /** KMK_CD = 4 */
        public static final int KMK_CD = 4;
        /** TAX_KBN = 5 */
        public static final int TAX_KBN = 5;

        /**
         *
         */
        public static final class CMN_INF {
            /** CMN_TANTO_CD = 6 */
            public static final int CMN_TANTO_CD = 6;
            /** CMN_TERM_ID = 7 */
            public static final int CMN_TERM_ID = 7;
            /** CMN_INSDD = 8 */
            public static final int CMN_INSDD = 8;
            /** CMN_UPDDD = 9 */
            public static final int CMN_UPDDD = 9;
            /** CMN_UPDTIME = 10 */
            public static final int CMN_UPDTIME = 10;
        }
    }

    /**
     * TORIHIKI_CODE
     */
    public static final class TORIHIKI_CODE {
        /**
        * EOS_SYORIRYO = "140001"
        **/
        public static final String EOS_SYORIRYO = "140001";
        /**
        * SHR_ONLINERYO = "220001"
        **/
        public static final String SHR_ONLINERYO = "220001";
        /**
        * SHR_MEISAIRYO = "210001"
        **/
        public static final String SHR_MEISAIRYO = "210001";
        /**
        * BTR_CENTERTESURYO = "150001"
        **/
        public static final String BTR_CENTERTESURYO = "150001";

    }

    /**
    * 地区コード定義内部クラス
    **/
    public static final class TIKU_CODE {
        /**
        * 多治見地区
        **/
        public static final String TAJIMI = "01";
        /**
        * 北陸地区
        **/
        public static final String HOKURIKU = "02";
        /**
        * その他
        **/
        public static final String ETC = "99";
    }

    /**
     * Clear and set default value.
     */
    public void clear() {
        kaisyaCode = CCKKBeans.CON_KAISHA_CODE;

        torihikisakiCode = CCKKBeans.CON_TORIHIKISAKI_CODE;

        tCodeEosSyori = TORIHIKI_CODE.EOS_SYORIRYO;
        tCodeShrOnline = TORIHIKI_CODE.SHR_ONLINERYO;
        tCodeShrMeisai = TORIHIKI_CODE.SHR_MEISAIRYO;

    }

    /**
     * 
     * @param sKaisyaCode sKaisyaCode
     */
    public void setKaisyaCode(String sKaisyaCode) {
        kaisyaCode = sKaisyaCode;
    }

    /**
     * 
     * @return sKaisyaCode
     */
    public String getKaisyaCode() {
        return kaisyaCode;
    }

    /**
     * 
     * @param sTorihikisakiCode sTorihikisakiCode
     */
    public void setTorihikisakiCode(String sTorihikisakiCode) {
        torihikisakiCode = sTorihikisakiCode;
    }

    /**
     * 
     * @return sTorihikisakiCode
     */
    public String getTorihikisakiCode() {
        return torihikisakiCode;
    }

    /**
     * 
     * @param sTCodeEosSyori sTCodeEosSyori
     */
    public void setTCodeEosSyori(String sTCodeEosSyori) {
        tCodeEosSyori = sTCodeEosSyori;
    }

    /**
     * 
     * @return sTCodeEosSyori
     */
    public String getTCodeEosSyori() {
        return tCodeEosSyori;
    }

    /**
     * 
     * @param sTCodeShrOnline sTCodeShrOnline
     */
    public void setTCodeShrOnline(String sTCodeShrOnline) {
        tCodeShrOnline = sTCodeShrOnline;
    }

    /**
     * 
     * @return sTCodeShrOnline
     */
    public String getTCodeShrOnline() {
        return tCodeShrOnline;
    }

    /**
     * 
     * @param sTCodeShrMeisai sTCodeShrMeisai
     */
    public void setTCodeShrMeisai(String sTCodeShrMeisai) {
        tCodeShrMeisai = sTCodeShrMeisai;
    }

    /**
     * 
     * @return sTCodeShrMeisai
     */
    public String getTCodeShrMeisai() {
        return tCodeShrMeisai;
    }

    /**
    * DC/TC区分の取得.
    * <p>
    * 機能概要：<br>
    *　DC/TC区分より、DC/TC区分名称を取得する。<br>
    * <p> 
    * @param sDate 基準日付
    * @param sDcTcKbn DC/TC区分
    * @return DC/TC区分名称（エラーの場合は""を返す）
    **/
    public String getDcTcKbnNm(String sDate, String sDcTcKbn) {
        String kbn;
        String cd;
        kbn = "";
        cd = "";

        // DC/TC区分コードが指定されていない場合、処理を終了する
        if (sDcTcKbn.equals("")) {
            return "";
        }

        // 検索条件項目の設定
        // コード区分
        kbn = CON_JK008TRHK_KEY.CODE_KBN;
        // コード
        if (sDcTcKbn.equals("6")) {
            // 予備
            cd = CON_JK008TRHK_KEY.DCTC_KBN.KBN6;
        } else if (sDcTcKbn.equals("7")) {
            // 片岡運送
            cd = CON_JK008TRHK_KEY.DCTC_KBN.KBN7;
        } else if (sDcTcKbn.equals("8")) {
            // 北部市場
            cd = CON_JK008TRHK_KEY.DCTC_KBN.KBN8;
        } else if (sDcTcKbn.equals("9")) {
            // 鳴尾浜市場
            cd = CON_JK008TRHK_KEY.DCTC_KBN.KBN9;
        } else if (sDcTcKbn.equals("0")) {
            // 店着
            cd = CON_JK008TRHK_KEY.DCTC_KBN.KBN0;
        } else {
            return "";
        }

        // 発効日
        String sBaseDate = "";
        if (sDate.equals("")) {
            // 日付が指定されていない場合、システム日付をセットする
            sBaseDate = CCDateUtil.getSysDateFormat(CCDateUtil.DATETIME_FORMAT_DATE_YYYYMMDD);
        } else {
            sBaseDate = CCDateUtil.getDateFormat(sDate, CCDateUtil.DATETIME_FORMAT_DATE_YYYYMMDD);
        }

        // 名称マスタ検索オブジェクトの作成
        M017meimExample m017meimExample = new M017meimExample();
        m017meimExample.createCriteria().andCdKbnEqualTo(kbn).andCdEqualTo(cd);
        m017meimExample.setSearchDate(sBaseDate);
        List<M017meim> listM017meim = this.m017meimMapper.selectByExample(m017meimExample);
        if (listM017meim.size() == 0) {
            return "";
        }

        // 共通部品より種別名称を取得
        return listM017meim.get(0).getNm();
    }

    /**
    * 地区名称の取得.
    * <p>
    * 機能概要：<br>
    *　地区ｺｰﾄﾞより、地区名称を取得する。<br>
    * <p> 
    * @param sDate 基準日付
    * @param sTikuCode 地区コードﾞ
    * @return 名称（エラーの場合は""を返す）
    **/
    public String getTikuNm(String sDate, String sTikuCode) {
        String kbn;
        String cd;
        kbn = "";
        cd = "";

        // 地区コードが指定されていない場合、処理を終了する
        if (sTikuCode.equals("")) {
            return "";
        }

        // 検索条件項目の設定
        // コード区分
        kbn = CON_JK008TRHK_KEY.CODE_KBN;
        // コード
        if (sTikuCode.equals(TIKU_CODE.TAJIMI)) {
            // 多治見地区
            cd = CON_JK008TRHK_KEY.TIKU_CD.KBN1;
        } else if (sTikuCode.equals(TIKU_CODE.HOKURIKU)) {
            // 北陸地区
            cd = CON_JK008TRHK_KEY.TIKU_CD.KBN2;
        } else {
            return "";
        }

        // 発効日
        String sBaseDate = "";
        if (sDate.equals("")) {
            // 日付が指定されていない場合、システム日付をセットする
            sBaseDate = CCDateUtil.getSysDateFormat(CCDateUtil.DATETIME_FORMAT_DATE_YYYYMMDD);
        } else {
            sBaseDate = CCDateUtil.getDateFormat(sDate, CCDateUtil.DATETIME_FORMAT_DATE_YYYYMMDD);
        }

        // 名称マスタ検索オブジェクトの作成
        M017meimExample m017meimExample = new M017meimExample();
        m017meimExample.createCriteria().andCdKbnEqualTo(kbn).andCdEqualTo(cd);
        m017meimExample.setSearchDate(sBaseDate);
        List<M017meim> listM017meim = this.m017meimMapper.selectByExample(m017meimExample);
        if (listM017meim.size() == 0) {
            return "";
        }

        // 共通部品より種別名称を取得
        return listM017meim.get(0).getNm();

    }

    /**
    * 勘定区分名称の取得.
    * <p>
    * 機能概要：<br>
    * 勘定区分より、勘定区分名称を取得する。<br>
    * <p> 
    * @param sDate 基準日付
    * @param sKanjoKubun 勘定区分
    * @return 状態（エラーの場合は""を返す）
    */
    public String getKnajoKbnNm(String sDate, String sKanjoKubun) {
        // 勘定区分が指定されていない場合、処理を終了する
        if (sKanjoKubun.equals("")) {
            return "";
        }

        // コード
        String sCd;
        if (sKanjoKubun.equals("1")) {
            // 支払勘定
            sCd = CON_JK008TRHK_KEY.KANJO_KBN.KBN1;
        } else if (sKanjoKubun.equals("2")) {
            // 相殺勘定
            sCd = CON_JK008TRHK_KEY.KANJO_KBN.KBN2;
        } else {
            return "";
        }

        // 発効日
        String sBaseDate = "";
        if (sDate.equals("")) {
            // 日付が指定されていない場合、システム日付をセットする
            sBaseDate = CCDateUtil.getSysDateFormat(CCDateUtil.DATETIME_FORMAT_DATE_YYYYMMDD);
        } else {
            sBaseDate = CCDateUtil.getDateFormat(sDate, CCDateUtil.DATETIME_FORMAT_DATE_YYYYMMDD);
        }

        // 検索条件項目の設定
        M017meimExample example = new M017meimExample();
        example.createCriteria().andCdKbnEqualTo(CON_JK008TRHK_KEY.CODE_KBN).andCdEqualTo(sCd);
        example.setSearchDate(sBaseDate);
        List<M017meim> list = m017meimMapper.selectByExample(example);

        // 取得レコード数が「０」の場合、処理を終了
        if (list.size() < 1) {
            return "";
        }

        // 共通部品より勘定区分名称を取得
        return list.get(0).getNm();
    }

    /**
     * JYOTAI_KBN
     */
    public static final class JYOTAI_KBN {
        /**
        * KBN_TOUROKU = 1
        **/
        public static final int KBN_TOUROKU = 1;
        /**
        * KBN_KAKUTEI = 2
        **/
        public static final int KBN_KAKUTEI = 2;
        /**
        * KBN_HORYU = 3
        **/
        public static final int KBN_HORYU = 3;
        /**
        * KBN_SHRZUMI = 4
        **/
        public static final int KBN_SHRZUMI = 4;

    }

    /**
     * CON_SHRDEN
     */
    public static final class CON_SHRDEN {
        /**
        * KBN_FURI_OL = "90"
        **/
        public static final String KBN_FURI_OL = "90";
        /**
        * KBN_FURI_TGK = "91"
        **/
        public static final String KBN_FURI_TGK = "91";
        /**
        * KBN_AKAZAN = "92"
        **/
        public static final String KBN_AKAZAN = "92";
        /**
        * KBN_KOGUCHI = "93"
        **/
        public static final String KBN_KOGUCHI = "93";
        /**
        * KBN_GENKIN = "94"
        **/
        public static final String KBN_GENKIN = "94";
        /**
        * KBN_TEGATA = "95"
        **/
        public static final String KBN_TEGATA = "95";
    }

    /**
     * HAT_TRI_KBN
     **/
    public static final class HAT_TRI_KBN {
        /**
        * TRI_TUJYO = "1"
        **/
        public static final String TRI_TUJYO = "1";
        /**
        * TRI_SYOCNT = "2"
        **/
        public static final String TRI_SYOCNT = "2";
        /**
        * TRI_NAIBU = "3"
        **/
        public static final String TRI_NAIBU = "3";

    }

    /**
    * 名称マスタフィールドNo.
    **/
    public static final class COLNO_JM017MEIM {
        /** コード区分*/
        public static final int CD_KBN = 0;
        /** // コード */
        public static final int CD = 1;
        /** 発効日*/
        public static final int HAKKO_DAY = 2;
        /** 名称（漢字） */
        public static final int NM = 3;
        /** 名称（カナ） */
        public static final int NM_A = 4;
        /** フラグ情報 */
        public static final int FLG_INFO = 5;
        /** 稼動区分 */
        public static final int ACT_KBN = 6;

        /** 共通情報 */
        public static final class CMN_INF {
            /** 担当者ｺｰﾄﾞ */
            public static final int CMN_TANTO_CD = 7;
            /** 端末ID */
            public static final int CMN_TERM_ID = 8;
            /** 登録日付 */
            public static final int CMN_INSDD = 9;
            /** 更新日付 */
            public static final int CMN_UPDDD = 10;
            /** 更新時刻 */
            public static final int CMN_UPDTIME = 11;
        }
    }

    /**
    * 科目名称の取得.
    * <p>
    * 機能概要：<br>
    * 科目コードより、科目名称を取得する。<br>
    * <p> 
    * @param sDate 基準日付
    * @param sKmkCd 科目名称
    * @return 状態（エラーの場合は""を返す）
    */
    public String getKmkCdNm(String sDate, String sKmkCd) {

        // 科目コードが指定されていない場合、処理を終了する
        if (sKmkCd.equals("")) {
            return "";
        }

        // 検索条件項目の設定
        M017meimExample example = new M017meimExample();
        Criteria criteria = example.createCriteria();
        // コード区分
        criteria.andCdKbnEqualTo(CON_JK008TRHK_KEY.CODE_KBN);
        // コード
        criteria.andCdEqualTo(CON_JK008TRHK_KEY.KMK_CD + sKmkCd);
        // 発効日
        String sBaseDate = "";
        if (sDate.equals("")) {
            // 日付が指定されていない場合、システム日付をセットする
            sBaseDate = CCDateUtil.getSysDateFormat(CCDateUtil.DATETIME_FORMAT_DATE_YYYYMMDD);
        } else {
            sBaseDate = CCDateUtil.getDateFormat(sDate, CCDateUtil.DATETIME_FORMAT_DATE_YYYYMMDD);
        }
        example.setSearchDate(sBaseDate);

        List<M017meim> list = m017meimMapper.selectByExample(example);

        // 取得レコード数が「０」の場合、処理を終了
        if (list.size() < 1) {
            return "";
        }

        // 共通部品より科目名称を取得
        return list.get(0).getNm();
    }

    /**
    * 税区分名称の取得.
    * <p>
    * 機能概要：<br>
    * 税区分より、税区分名称を取得する。<br>
    * <p> 
    * @param sDate 基準日付
    * @param sTaxKubun 税区分
    * @return 状態（エラーの場合は""を返す）
    */
    public String getTaxKbnNm(String sDate, String sTaxKubun) {

        // 税区分が指定されていない場合、処理を終了する
        if (sTaxKubun.equals("")) {
            return "";
        }
        // 数字以外はエラー
        if (!CCNumberUtil.isNumeric(sTaxKubun)) {
            return "";
        }

        // 検索条件項目の設定
        M017meimExample example = new M017meimExample();
        Criteria criteria = example.createCriteria();
        // コード区分
        criteria.andCdKbnEqualTo(CON_JK008TRHK_KEY.CODE_KBN);

        // コード
        // 税区分変更(外税:5～8、内税:1～4、非課税:9)
        int iTaxKbn = Integer.parseInt(sTaxKubun);
        if (iTaxKbn >= 5 && iTaxKbn <= 8) {
            // 外税
            criteria.andCdEqualTo(CON_JK008TRHK_KEY.TAX_KBN.KBN1);
        } else if (iTaxKbn >= 1 && iTaxKbn <= 4) {
            // 内税
            criteria.andCdEqualTo(CON_JK008TRHK_KEY.TAX_KBN.KBN2);
        } else if (iTaxKbn == 9) {
            // 非課税
            criteria.andCdEqualTo(CON_JK008TRHK_KEY.TAX_KBN.KBN3);
        } else {
            return "";
        }

        // 発効日
        String sBaseDate = "";
        if (sDate.equals("")) {
            // 日付が指定されていない場合、システム日付をセットする
            sBaseDate = CCDateUtil.getSysDateFormat(CCDateUtil.DATETIME_FORMAT_DATE_YYYYMMDD);
        } else {
            sBaseDate = CCDateUtil.getDateFormat(sDate, CCDateUtil.DATETIME_FORMAT_DATE_YYYYMMDD);
        }
        example.setSearchDate(sBaseDate);
        List<M017meim> list = m017meimMapper.selectByExample(example);

        // 取得レコード数が「０」の場合、処理を終了
        if (list.size() < 1) {
            return "";
        }

        // 共通部品より税区分名称を取得
        return list.get(0).getNm();

    }

    /**
    * マスタからの取引項目名称取得処理.
    * <p>
    * 機能概要：<br>
    * 共通部品を使用して、取引項目コードより指定コードのデータを取得します。
    * <p> 
    * @param sToriKoCode 取引項目コード
    * @return K008trhk
    */
    public K008trhk getKakToriByCode(String sToriKoCode) {
        K008trhk sDateBuf = null;

        if (sToriKoCode.equals("")) {
            return sDateBuf;
        }

        return getKakToriByDate("", sToriKoCode);
    }

    /**
    * マスタからの取引項目名称取得処理.
    * <p>
    * 機能概要：<br>
    * 共通部品を使用して、取引項目コードより指定コードのデータを取得します。
    * <p> 
    * @param sToriKoCode 取引項目コード
    * @param kaisyaCd 取引会社
    * @return K008trhk
    */
    public K008trhk getKakToriByCodeWithKaisya(String sToriKoCode, String kaisyaCd) {
        K008trhk sDateBuf = null;

        if (sToriKoCode.equals("")) {
            return sDateBuf;
        }

        return getKakToriByDateWithKaisya("", sToriKoCode, kaisyaCd);
    }

    /**
    * マスタからの取引項目名称取得処理.
    * <p>
    * 機能概要：<br>
    * 共通部品を使用して、取引項目コードより指定コードのデータを取得します。
    * <p> 
    * @param sDate 日付
    * @param sToriKoCode 取引項目コード
    * @return 取得レコードの配列
    */
    public K008trhk getKakToriByDate(String sDate, String sToriKoCode) {
        K008trhk sDateBuf = null;

        if (sToriKoCode.equals("")) {
            return sDateBuf;
        }

        // strParam[ COLNO_JK008TRHK.KAISYA_CD ] = getKaisyaCode(); // Old code
        K008trhkExample example = new K008trhkExample();
        // example.createCriteria().andKakToriKmkEqualTo(sToriKoCode);
        example.createCriteria().andKaisyaCdEqualTo(getKaisyaCode()).andKakToriKmkEqualTo(sToriKoCode);

        List<K008trhk> result = k008trhkMapper.selectByExample(example);

        if (result.size() < 1) {
            return sDateBuf;
        } else {
            sDateBuf = result.get(0);
        }
        return sDateBuf;
    }

    /**
    * マスタからの取引項目名称取得処理.
    * <p>
    * 機能概要：<br>
    * 共通部品を使用して、取引項目コードより指定コードのデータを取得します。
    * <p> 
    * @param sDate 日付
    * @param sToriKoCode 取引項目コード
    * @param kaisyaCd 取引会社
    * @return 取得レコードの配列
    */
    public K008trhk getKakToriByDateWithKaisya(String sDate, String sToriKoCode, String kaisyaCd) {
        K008trhk sDateBuf = null;

        if (sToriKoCode.equals("")) {
            return sDateBuf;
        }

        // strParam[ COLNO_JK008TRHK.KAISYA_CD ] = getKaisyaCode(); // Old code
        K008trhkExample example = new K008trhkExample();
        // example.createCriteria().andKakToriKmkEqualTo(sToriKoCode);
        example.createCriteria().andKaisyaCdEqualTo(kaisyaCd).andKakToriKmkEqualTo(sToriKoCode);

        List<K008trhk> result = k008trhkMapper.selectByExample(example);

        if (result.size() < 1) {
            return sDateBuf;
        } else {
            sDateBuf = result.get(0);
        }
        return sDateBuf;
    }

    /**
    * マスタからの伝票番号取得処理.
    * <p>
    * 機能概要：<br>
    * 共通部品を使用して、会社コードより指定コードのデータを取得します。
    * <p> 
    * param sKaisyaCode 会社目コード
    * @param denKbn 支払伝票区分
    * @return 取得レコードの配列
    */
    @Transactional
    public String getPresentNoAndUpdSaiban(String denKbn) {
        String sDateBuf = "";

        String sKaisyaCode = getKaisyaCode();

        if (sKaisyaCode.equals("")) {
            return sDateBuf;
        }

        Map<String, String> params = new HashMap<String, String>();
        params.put("saibanKbn", CON_SAIBAN_KBN);
        params.put("denKbn", denKbn);
        params.put("kaisyaCd", sKaisyaCode);
        params.put("tenCd", BLANK);

        M022sibn m022sibn = m022sibnMapper.selectM022sibnByParams(params);

        if (m022sibn != null) {
            sDateBuf = m022sibn.getPresentNo().substring(4);
            long preNum = Long.valueOf(m022sibn.getPresentNo());
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
            example.createCriteria().andSaibanKbnEqualTo(CON_SAIBAN_KBN).andDenKbnEqualTo(denKbn)
                    .andKaisyaCdEqualTo(sKaisyaCode).andTenCdEqualTo(BLANK);

            if (m022sibnMapper.updateByExampleSelective(m022, example) <= 0) {
                throw new ChaseException(Messages.get(CCMessageConst.MSG_KEY_EXCEPTION_UPDATE_NO_RECORD));
            }

            return sDateBuf;
        } else {
            return "";
        }
    }

    /**
     * 取引先マスタからの取引先名称取得処理.
     * <p>
     * 機能概要：<br>
     * 共通部品を使用して、取引先マスタより指定コードのデータを取得します。
     * <p> 
     * @param sDate 基準日付
     * @param sToriCode 取引先コード（９桁未満の場合、後３桁を関数内で付加する）
     * @return 取得レコードの配列
     */
    public M011trim getComM011trim(String sDate, String sToriCode) {
        M011trim sDataBuf = null;

        if (CCStringUtil.isEmpty(sToriCode)) {
            return sDataBuf;
        }

        M011trimExample example = new M011trimExample();
        M011trimExample.Criteria criteria = example.createCriteria();

        if (sToriCode.length() == 9) {
            criteria.andTriCdEqualTo(sToriCode);
        } else {
            criteria.andTriCdEqualTo(sToriCode + CON_TORIHIKISAKI_CODE);
        }

        String sBaseDate = "";
        if (CCStringUtil.isEmpty(sDate)) {
            sBaseDate = CCDateUtil.getSysDateFormat(CCDateUtil.DATETIME_FORMAT_DATE_YYYYMMDD);
        } else {
            sBaseDate = CCDateUtil.getDateFormat(sDate, CCDateUtil.DATETIME_FORMAT_DATE_YYYYMMDD);
        }
        example.setSearchDate(sBaseDate);

        List<M011trim> trims = m011trimMapper.selectByExample(example);
        if (trims.size() < 1) {
            return sDataBuf;
        } else {
            sDataBuf = trims.get(0);
        }

        return sDataBuf;
    }

    /**
     * 取引先マスタからの取引先名称取得処理.
     * <p>
     * 機能概要：<br>
     * 共通部品を使用して、取引先マスタより指定コードのデータを取得します。
     * <p> 
     * @param sDate 基準日付
     * @param sToriCode 取引先コード（９桁未満の場合、後３桁を関数内で付加する）
     * @return 取得レコードの配列
     */
    public List<M011trim> getComM011trim(String sDate, List<String> sToriCode) {
        if (sToriCode == null || sToriCode.isEmpty()) {
            return new ArrayList<M011trim>();
        }

        M011trimExample example = new M011trimExample();
        M011trimExample.Criteria criteria = example.createCriteria();
        criteria.andTriCdIn(sToriCode);

        String sBaseDate = "";
        if (CCStringUtil.isEmpty(sDate)) {
            sBaseDate = CCDateUtil.getSysDateFormat(CCDateUtil.DATETIME_FORMAT_DATE_YYYYMMDD);
        } else {
            sBaseDate = CCDateUtil.getDateFormat(sDate, CCDateUtil.DATETIME_FORMAT_DATE_YYYYMMDD);
        }
        example.setSearchDate(sBaseDate);

        List<M011trim> trims = m011trimMapper.selectByExample(example);
        return trims;
    }

    /**
     * 状態区分の取得.
     * <p>
     * 機能概要：<br>
     * 確定区分より、状態区分を取得する。<br>
     * <p> 
     * @param sDate 基準日付
     * @param sKakuteiKubun sKakuteiKubun
     * @return JyotaiKubun
     */
    public String getJyotaiKubun(String sDate, String sKakuteiKubun) {
        if (CCStringUtil.isEmpty(sKakuteiKubun)) {
            return "";
        }

        M017meimExample example = new M017meimExample();
        M017meimExample.Criteria criteria = example.createCriteria().andCdKbnEqualTo(CON_JK008TRHK_KEY.CODE_KBN);

        if ("1".equals(sKakuteiKubun)) {
            criteria.andCdEqualTo(CON_JK008TRHK_KEY.JOTAI.KBN1);
        } else if ("2".equals(sKakuteiKubun)) {
            criteria.andCdEqualTo(CON_JK008TRHK_KEY.JOTAI.KBN2);
        } else if ("3".equals(sKakuteiKubun)) {
            criteria.andCdEqualTo(CON_JK008TRHK_KEY.JOTAI.KBN3);
        } else if ("4".equals(sKakuteiKubun)) {
            criteria.andCdEqualTo(CON_JK008TRHK_KEY.JOTAI.KBN4);
        } else {
            return "";
        }

        String sBaseDate = "";
        if (CCStringUtil.isEmpty(sDate)) {
            sBaseDate = CCDateUtil.getSysDateFormat(CCDateUtil.DATETIME_FORMAT_DATE_YYYYMMDD);
        } else {
            sBaseDate = CCDateUtil.getDateFormat(sDate, CCDateUtil.DATETIME_FORMAT_DATE_YYYYMMDD);
        }

        example.setSearchDate(sBaseDate);
        List<M017meim> result = m017meimMapper.selectByExample(example);

        if (result.size() < 1) {
            return "";
        }

        return result.get(0).getNm();
    }

    /**
    * 締め日→支払日変換.
    * <p>
    * 機能概要：<br>
    * 締め日から支払区分により支払日を取得する。<br>
    * <p> 
    * @param sSimeDate 締め日
    * @param sSimeKbn  締め区分
    * @param sShrKbn   支払区分
    * @return 支払日
    */
    public String cnvSimeDateToShrDate(String sSimeDate, String sSimeKbn, String sShrKbn) {
        String sShrDate = new String();

        if (sSimeKbn.equals(CCKKConst.SHIME_KBN.KBN_05DAY)) {
            sShrDate = CCDateUtil.doCalcDate(sSimeDate, "DAY", 1);
        } else if (sSimeKbn.equals(CCKKConst.SHIME_KBN.KBN_10DAY)) {
            int iDay = Integer.parseInt(sSimeDate.substring(6));
            if (iDay <= 10) {
                sShrDate = sSimeDate.substring(0, 6) + "20";
            } else if (10 < iDay && iDay <= 20) {
                sShrDate = CCDateUtil.doCalcDate(sSimeDate, "DAY_OF_MONTH", 0);
            } else {
                String sDate = CCDateUtil.doCalcDate(sSimeDate, "MONTH", 1);
                sShrDate = sDate.substring(0, 6) + "10";
            }
        } else if (sSimeKbn.equals(CCKKConst.SHIME_KBN.KBN_15DAY)) {
            int iDay = Integer.parseInt(sSimeDate.substring(6));
            if (iDay <= 15) {
                // sShrDate = clKKBns.doCalcDate(sSimeDate, "DAY_OF_MONTH", 0) ;

                if (sShrKbn.equals(CCKKConst.SHIHARAI_KBN.KBN_AFTER_10)) {
                    sShrDate = sSimeDate.substring(0, 6) + "25";
                } else {
                    sShrDate = CCDateUtil.doCalcDate(sSimeDate, "DAY_OF_MONTH", 0);
                }
            } else {
                // String sDate = clKKBns.doCalcDate(sSimeDate, "MONTH", 1) ;
                // sShrDate = sDate.substring(0,6) + "15" ;

                String sDate = CCDateUtil.doCalcDate(sSimeDate, "MONTH", 1);
                if (sShrKbn.equals(CCKKConst.SHIHARAI_KBN.KBN_AFTER_10)) {
                    sShrDate = sDate.substring(0, 6) + "10";
                } else {
                    sShrDate = sDate.substring(0, 6) + "15";
                }
            }
        } else if (sSimeKbn.equals(CCKKConst.SHIME_KBN.KBN_GETUJI)) {

            if (sShrKbn.equals(CCKKConst.SHIHARAI_KBN.KBN_AFTER_10)) {
                String sDate = CCDateUtil.doCalcDate(sSimeDate, "MONTH", 1);
                sShrDate = sDate.substring(0, 6) + "10";

            } else if (sShrKbn.equals(CCKKConst.SHIHARAI_KBN.KBN_AFTER_15)) {
                String sDate = CCDateUtil.doCalcDate(sSimeDate, "MONTH", 1);
                sShrDate = sDate.substring(0, 6) + "15";

            } else if (sShrKbn.equals(CCKKConst.SHIHARAI_KBN.KBN_AFTER_30)) {
                String sDate = CCDateUtil.doCalcDate(sSimeDate, "MONTH", 1);
                sShrDate = CCDateUtil.doCalcDate(sDate, "DAY_OF_MONTH", 0);

            } else if (sShrKbn.equals(CCKKConst.SHIHARAI_KBN.KBN_AFTER_60)) {
                String sDate = CCDateUtil.doCalcDate(sSimeDate, "MONTH", 2);
                sShrDate = CCDateUtil.doCalcDate(sDate, "DAY_OF_MONTH", 0);

            } else if (sShrKbn.equals(CCKKConst.SHIHARAI_KBN.KBN_AFTER_90)) {
                String sDate = CCDateUtil.doCalcDate(sSimeDate, "MONTH", 3);
                sShrDate = CCDateUtil.doCalcDate(sDate, "DAY_OF_MONTH", 0);
            } else {
                sShrDate = "";
            }
        } else if (sSimeKbn.equals(CCKKConst.SHIME_KBN.KBN_KOG_ETC)) {
            sShrDate = CCDateUtil.doCalcDate(sSimeDate, "DAY", 1);
        } else {
            sShrDate = "";
        }
        return sShrDate;
    }

    /**
     * 消費税取得処理.
     * <p>
     * 機能概要：<br>
     * 消費税を算出します。
     * <p> 
     * @param lKingaku 対象金額
     * @param sZeiKbn 税区分
     * @return 消費税
     *         -1：エラー
     */
    public long getKingakuToTax(long lKingaku, String sZeiKbn) {
        long lRet = -1;

            cmKKTax.setKingkIn(String.valueOf(lKingaku));

            cmKKTax.setTaxKbn(sZeiKbn);
            cmKKTax.setHasuKbn(CCKK0010Tax.CON_HASU_KBN);

            boolean bRet = cmKKTax.doCheckTaxKingk();

            if (bRet) {
                    lRet = Long.parseLong(cmKKTax.getTaxKingk());
            }
        return lRet;
    }

    /**
    * 支払伝票区分の取得処理.
    * <p>
    * 機能概要：<br>
    * 買掛精算区分より支払伝票区分の取得を行う。<br>
    * <p> 
    * @param sSeisanKbn 買掛精算区分
    * @return 支払伝票区分 ""の場合はエラー
    */
    public String cnvSeiKbnToShrDenKbn(String sSeisanKbn) {
        String sRet = "";

        @SuppressWarnings("unused")
        int iKbn = CCStringUtil.cnvStrToInt(sSeisanKbn);

        if (sSeisanKbn.equals(String.valueOf(CCKKConst.SEISAN_KBN.KBN_FURI_OL))) {
            sRet = CON_SHRDEN.KBN_FURI_OL;
        } else if (sSeisanKbn.equals(String.valueOf(CCKKConst.SEISAN_KBN.KBN_FURI_TGK))) {
            sRet = CON_SHRDEN.KBN_FURI_TGK;
        } else if (sSeisanKbn.equals(String.valueOf(CCKKConst.SEISAN_KBN.KBN_GENKIN))) {
            sRet = CON_SHRDEN.KBN_GENKIN;
        } else if (sSeisanKbn.equals(String.valueOf(CCKKConst.SEISAN_KBN.KBN_KOGUCHI))) {
            sRet = CON_SHRDEN.KBN_KOGUCHI;
        } else if (sSeisanKbn.equals(String.valueOf(CCKKConst.SEISAN_KBN.KBN_TEGATA))) {
            sRet = CON_SHRDEN.KBN_TEGATA;
        }

        return sRet;
    }

    /**
     * 会社マスタからの会社名称取得処理.
     * <p>
     * 機能概要：<br>
     * 共通部品を使用して、基準日付、会社コードより会社名称を取得します。
     * <p> 
     * @param sDate 基準日付
     * @param sKaisyaCode 会社コード
     * @return  会社名称
     */
    public String getComKaisyaName(String sDate, String sKaisyaCode) {

        // 会社コードが指定されていない場合、処理を終了する
        if (sKaisyaCode.equals("")) {
            return "";
        }

        // 会社マスタ検索オブジェクトの作成
        M000kaimExample example = new M000kaimExample();
        // 発効日
        String sBaseDate = "";
        if (sDate.equals("")) {
            // 日付が指定されていない場合、システム日付をセットする
            sBaseDate = CCDateUtil.getSysDateFormat(CCDateUtil.DATETIME_FORMAT_DATE_YYYYMMDD);
        } else {
            sBaseDate = CCDateUtil.getDateFormat(sDate, CCDateUtil.DATETIME_FORMAT_DATE_YYYYMMDD);
        }

        example.setSearchDate(sBaseDate);
        example.createCriteria().andKaisyaCdEqualTo(sKaisyaCode);
        List<M000kaim> list = m000kaimMapper.selectByExample(example);

        if (list.size() == 0) {
            return "";
        }

        return list.get(0).getKaiNm();
    }

    /**
     * 買掛精算区分名称取得処理.
     * <p>
     * 機能概要：<br>
     *  買掛精算区分名称を取得します。
     * <p>
     * 
     * @param sDate ：基準日付
     * @param sSeisanKB 買掛精算区分
     * @return 買掛精算区分名称
     */
    public String getSeisanKbnNm(String sDate, String sSeisanKB) {

        // 買掛精算区分が指定されていない場合、処理を終了する
        if (CCStringUtil.isEmpty(sSeisanKB)) {
            return "";
        }

        // 発効日
        String sBaseDate = "";
        if (CCStringUtil.isEmpty(sDate)) {
            // 日付が指定されていない場合、システム日付をセットする
            sBaseDate = CCDateUtil.getSysDateFormat(CCDateUtil.DATETIME_FORMAT_DATE_YYYYMMDD);
        } else {
            sBaseDate = CCDateUtil.getDateFormat(sDate, CCDateUtil.DATETIME_FORMAT_DATE_YYYYMMDD);
        }

        String cdKbn = CON_JK008TRHK_KEY.CODE_KBN;
        String cd = null;

        // コード
        if (sSeisanKB.equals(String.valueOf(CCKKConst.SEISAN_KBN.KBN_FURI_OL))) {
            // FB
            cd = CON_JK008TRHK_KEY.SEISAN_KBN.KBN1;

        } else if (sSeisanKB.equals(String.valueOf(CCKKConst.SEISAN_KBN.KBN_FURI_TGK))) {
            // 手払
            cd = CON_JK008TRHK_KEY.SEISAN_KBN.KBN2;

        } else if (sSeisanKB.equals(String.valueOf(CCKKConst.SEISAN_KBN.KBN_GENKIN))) {
            // 現金
            cd = CON_JK008TRHK_KEY.SEISAN_KBN.KBN3;

        } else if (sSeisanKB.equals(String.valueOf(CCKKConst.SEISAN_KBN.KBN_KOGUCHI))) {
            // 小口
            cd = CON_JK008TRHK_KEY.SEISAN_KBN.KBN4;

        } else if (sSeisanKB.equals(String.valueOf(CCKKConst.SEISAN_KBN.KBN_TEGATA))) {
            // 手形
            cd = CON_JK008TRHK_KEY.SEISAN_KBN.KBN5;

        } else if (sSeisanKB.equals(String.valueOf(CCKKConst.SEISAN_KBN.KBN_JIFURI))) {
            // 自振
            cd = CON_JK008TRHK_KEY.SEISAN_KBN.KBN6;

        } else {
            return "";
        }

        M017meimExample example = new M017meimExample();
        example.setSearchDate(sBaseDate);
        example.createCriteria().andCdKbnEqualTo(cdKbn).andCdEqualTo(cd);

        List<M017meim> m017meimList = this.m017meimMapper.selectByExample(example);

        if (m017meimList.size() == 0) {
            return "";
        }

        // 共通部品より支払区分名称を取得
        return m017meimList.get(0).getNm();
    }

    /**
     * 支払パターン取得処理.
     * <p>
     * 機能概要：<br>
     * 支払パターンを取得します。
     * @param sDate 基準日付
     * @param sShiharaiKB 支払区分（締日区分 + 支払日区分）
     * @return 支払パターン
     */
    public String getShiharaiPt(String sDate, String sShiharaiKB) {

        // 支払区分が指定されていない場合、処理を終了する
        if (CCStringUtil.isEmpty(sShiharaiKB)) {
            return "";
        }

        // 発効日
        String sBaseDate = "";
        if (CCStringUtil.isEmpty(sDate)) {
            // 日付が指定されていない場合、システム日付をセットする
            sBaseDate = CCDateUtil.getSysDateFormat(CCDateUtil.DATETIME_FORMAT_DATE_YYYYMMDD);
        } else {
            sBaseDate = CCDateUtil.getDateFormat(sDate, CCDateUtil.DATETIME_FORMAT_DATE_YYYYMMDD);
        }

        // 検索条件項目の設定
        // コード区分
        String cdKbn = CON_JK008TRHK_KEY.CODE_KBN;
        String cd = null;

        // コード
        if (sShiharaiKB.equals(CCKKConst.SHIME_KBN.KBN_05DAY + CCKKConst.SHIHARAI_KBN.KBN_YOKU)) {
            // ５日毎 0501
            cd = CON_JK008TRHK_KEY.SIME_PAY.KBN0505;
        } else if (sShiharaiKB.equals(CCKKConst.SHIME_KBN.KBN_10DAY + CCKKConst.SHIHARAI_KBN.KBN_AFTER_10)) {
            // １０日毎 1010
            cd = CON_JK008TRHK_KEY.SIME_PAY.KBN1010;
        } else if (sShiharaiKB.equals(CCKKConst.SHIME_KBN.KBN_15DAY + CCKKConst.SHIHARAI_KBN.KBN_AFTER_15)) {
            // １５日毎 1515
            cd = CON_JK008TRHK_KEY.SIME_PAY.KBN1515;
        } else if (sShiharaiKB.equals(CCKKConst.SHIME_KBN.KBN_15DAY + CCKKConst.SHIHARAI_KBN.KBN_AFTER_10)) {
            // １５日毎締１０日後払 1510
            cd = CON_JK008TRHK_KEY.SIME_PAY.KBN1510;
        } else if (sShiharaiKB.equals(CCKKConst.SHIME_KBN.KBN_GETUJI + CCKKConst.SHIHARAI_KBN.KBN_AFTER_10)) {
            cd = CON_JK008TRHK_KEY.SIME_PAY.KBN3010;
        } else if (sShiharaiKB.equals(CCKKConst.SHIME_KBN.KBN_GETUJI + CCKKConst.SHIHARAI_KBN.KBN_AFTER_15)) {
            // １５日払い 3015
            cd = CON_JK008TRHK_KEY.SIME_PAY.KBN3015;
        } else if (sShiharaiKB.equals(CCKKConst.SHIME_KBN.KBN_GETUJI + CCKKConst.SHIHARAI_KBN.KBN_AFTER_30)) {
            // 翌月末払い 3030
            cd = CON_JK008TRHK_KEY.SIME_PAY.KBN3030;
        } else if (sShiharaiKB.equals(CCKKConst.SHIME_KBN.KBN_GETUJI + CCKKConst.SHIHARAI_KBN.KBN_AFTER_60)) {
            // 翌々月末払い 3060
            cd = CON_JK008TRHK_KEY.SIME_PAY.KBN3060;
        } else if (sShiharaiKB.equals(CCKKConst.SHIME_KBN.KBN_GETUJI + CCKKConst.SHIHARAI_KBN.KBN_AFTER_90)) {
            // 手形 3090
            cd = CON_JK008TRHK_KEY.SIME_PAY.KBN3090;
        } else if (sShiharaiKB.equals(CCKKConst.SHIME_KBN.KBN_KOG_ETC + CCKKConst.SHIHARAI_KBN.KBN_YOKU)) {
            // 小口・その他 9901
            cd = CON_JK008TRHK_KEY.SIME_PAY.KBN9901;
        } else {
            return "";
        }
        M017meimExample example = new M017meimExample();
        example.setSearchDate(sBaseDate);
        example.createCriteria().andCdKbnEqualTo(cdKbn).andCdEqualTo(cd);

        List<M017meim> m017meimList = this.m017meimMapper.selectByExample(example);
        // 取得レコード数が「０」の場合、処理を終了
        if (m017meimList.size() == 0) {
            return "";
        }

        // 共通部品より支払区分名称を取得
        return m017meimList.get(0).getNm();
    }

    /**
     * 取引先マスタからの取引先名称取得処理.
     * <p>
     * 機能概要：<br>
     * 共通部品を使用して、取引先マスタより取引先名称を取得します。
     * @param sToriCode 取引先コード（９桁未満の場合、後３桁を関数内で付加する）
     * @return 取引先名称
     */
    public String getComToriName(String sToriCode) {

        // 取引先コードが指定されていない場合、処理を終了する
        if (CCStringUtil.isEmpty(sToriCode)) {
            return "";
        }

        // 共通部品より取引先名称を取得
        return getComToriName("", sToriCode);
    }

    /**
     * 取引先マスタからの取引先名称取得処理.
     * <p>
     * 機能概要：<br>
     * 共通部品を使用して、取引先マスタより取引先名称を取得します。
     * @param sDate 基準日付
     * @param sToriCode 取引先コード（９桁未満の場合、後３桁を関数内で付加する）
     * @return 取引先名称
    */
    public String getComToriName(String sDate, String sToriCode) {

        // 取引先コードが指定されていない場合、処理を終了する
        if (CCStringUtil.isEmpty(sToriCode)) {
            return "";
        }

        // 発効日
        String sBaseDate = "";
        if (CCStringUtil.isEmpty(sDate)) {
            // 日付が指定されていない場合、システム日付をセットする
            sBaseDate = CCDateUtil.getSysDateFormat(CCDateUtil.DATETIME_FORMAT_DATE_YYYYMMDD);
        } else {
            sBaseDate = CCDateUtil.getDateFormat(sDate, CCDateUtil.DATETIME_FORMAT_DATE_YYYYMMDD);
        }

        String triCd = null;

        if (sToriCode.length() == 9) {
            triCd = sToriCode;
        } else {
            triCd = sToriCode + torihikisakiCode;
        }

        M011trimExample example = new M011trimExample();
        example.setSearchDate(sBaseDate);
        example.createCriteria().andTriCdEqualTo(triCd);

        List<CodeMaster> m011trimList = this.m011trimMapper.selectCodeMasterByExample(example);
        // 取得レコード数が「０」の場合、処理を終了
        if (m011trimList.size() == 0) {
            return "";
        }

        // 共通部品より取引先名称を取得
        return m011trimList.get(0).getName();
    }
    
    /**
     * 取引先マスタからの取引先名称取得処理.
     * <p>
     * 機能概要：<br>
     * 共通部品を使用して、取引先マスタより取引先名称を取得します。
     * @param sDate 基準日付
     * @param sToriCode 取引先コード（９桁未満の場合、後３桁を関数内で付加する）
     * @return 取引先名称
     */
    public Map<String, String> getComToriName(String sDate, List<String> sToriCode) {

        // 取引先コードが指定されていない場合、処理を終了する
        if (sToriCode == null || sToriCode.isEmpty()) {
            return new HashMap<String, String>();
        }

        // 発効日
        String sBaseDate = "";
        if (CCStringUtil.isEmpty(sDate)) {
            // 日付が指定されていない場合、システム日付をセットする
            sBaseDate = CCDateUtil.getSysDateFormat(CCDateUtil.DATETIME_FORMAT_DATE_YYYYMMDD);
        } else {
            sBaseDate = CCDateUtil.getDateFormat(sDate, CCDateUtil.DATETIME_FORMAT_DATE_YYYYMMDD);
        }

        M011trimExample example = new M011trimExample();
        example.setSearchDate(sBaseDate);
        example.createCriteria().andTriCdIn(sToriCode);

        List<CodeMaster> m011trimList = this.m011trimMapper.selectCodeMasterByExample(example);
        Map<String, String> result = new HashMap<String, String>();
        // 取得レコード数が「０」の場合、処理を終了
        if (m011trimList.size() == 0) {
            return result;
        }

        // 共通部品より取引先名称を取得
        for (CodeMaster row : m011trimList) {
            String code = row.getCode();
            String name = row.getName();
            result.put(code, name);
        }
        return result;
    }

    /**
     * 銀行マスタからの銀行マスタ情報取得処理.
     * <p>
     * 機能概要：<br>
     *　共通部品を使用して、銀行コード、銀行支店コードより指定コードのデータを取得します。
     * <p> 
     * 作成日：<br> 
     *　2014/04/08<br> 
     *　　新規作成<br>
     * <p> 
     * @param sDate 基準日付
     * @param sBancCode 銀行コード
     * @param sBancStnCode 銀行支店コード
     * @return 取得レコードの配列
     */
    public M018bnkm getComM018bnkm(String sDate, String sBancCode, String sBancStnCode) {
        // 銀行コード、銀行支店コードが指定されていない場合、処理を終了する
        if (sBancCode.equals("") || sBancStnCode.equals("")) {
            return null;
        }

        // 会社マスタ検索オブジェクトの作成
        M018bnkmExample m018bnkmExample = new M018bnkmExample();

        // 検索条件項目の設定
        m018bnkmExample.createCriteria().andBankCdEqualTo(sBancCode).andBankBranchCdEqualTo(sBancStnCode);

        List<M018bnkm> m018bnkmList = m018bnkmMapper.selectByExample(m018bnkmExample);
        if (m018bnkmList == null || m018bnkmList.isEmpty()) {
            return null;
        }

        return m018bnkmList.get(0);
    }

    /**
     * 支払伝票区分名称の取得.
     * <p>
     * 機能概要：<br>
     * 新規作成<br>
     * <p> 
     * @param sDate ：基準日付
     * @param sShrDenKubun ：支払伝票区分
     * @return 状態（エラーの場合は""を返す）
     */
    public String getShrDenKbnNm(String sDate, String sShrDenKubun) {
        // 支払伝票区分が指定されていない場合、処理を終了する
        if (CCStringUtil.isEmpty(sShrDenKubun)) {
            return "";
        }

        String cdKbn = CON_JK008TRHK_KEY.CODE_KBN;
        String cd = null;

        // 検索条件項目の設定
        // コード区分
        cdKbn = CON_JK008TRHK_KEY.CODE_KBN;
        // コード
        if (sShrDenKubun.equals("90")) {
            // オンライン振込
            cd = CON_JK008TRHK_KEY.SHR_DEN.KBN90;
        } else if (sShrDenKubun.equals("91")) {
            // 銀行振込（手書）
            cd = CON_JK008TRHK_KEY.SHR_DEN.KBN91;
        } else if (sShrDenKubun.equals("92")) {
            // 赤残入力
            cd = CON_JK008TRHK_KEY.SHR_DEN.KBN92;
        } else if (sShrDenKubun.equals("93")) {
            // 店小口
            cd = CON_JK008TRHK_KEY.SHR_DEN.KBN93;
        } else if (sShrDenKubun.equals("94")) {
            // 本部現金
            cd = CON_JK008TRHK_KEY.SHR_DEN.KBN94;
        } else if (sShrDenKubun.equals("95")) {
            // 手形
            cd = CON_JK008TRHK_KEY.SHR_DEN.KBN95;
        } else {
            return "";
        }
        // 発効日
        String sBaseDate = "";
        if (CCStringUtil.isEmpty(sDate)) {
            // 日付が指定されていない場合、システム日付をセットする
            sBaseDate = CCDateUtil.getSysDateFormat(CCDateUtil.DATETIME_FORMAT_DATE_YYYYMMDD);
        } else {
            sBaseDate = CCDateUtil.getDateFormat(sDate, CCDateUtil.DATETIME_FORMAT_DATE_YYYYMMDD);
        }

        // 名称マスタ検索オブジェクトの作成
        M017meimExample m017meimExample = new M017meimExample();

        m017meimExample.createCriteria().andCdKbnEqualTo(cdKbn).andCdEqualTo(cd);
        m017meimExample.setSearchDate(sBaseDate);

        List<M017meim> listM017meim = this.m017meimMapper.selectByExample(m017meimExample);

        if (listM017meim.size() == 0) {
            return "";
        }
        // 共通部品より種別名称を取得
        return listM017meim.get(0).getNm();
    }
}
