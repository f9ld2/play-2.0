// /////////////////////////////////////////////////////////////////////
// Copyright(C) 2015 NEC Solution Innovators, Ltd. All Rights Reserved.
// /////////////////////////////////////////////////////////////////////

/*
 * ====================================================================
 * 
 * 機能名称 : 仕入エラーリスト（明細）
 * 
 * 改版履歴
 * 
 * Rev. 改版年月日 改版者名 内容
 * 
 * 1.0 2014.19.05 TuanTQ 新規作成
 * 
 * ===================================================================
 */

package jp.co.necsoft.web_md.core.app.controllers.si;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import jp.co.necsoft.web_md.common.ChaseException;
import jp.co.necsoft.web_md.common.mybatis.dao.MyBatisSqlMapDao;
import jp.co.necsoft.web_md.core.app.dto.si.S001eosds033seiDataResult;
import jp.co.necsoft.web_md.core.app.dto.si.Sijp1150Dto;
import jp.co.necsoft.web_md.core.app.file.report.Sipr1150Report;
import jp.co.necsoft.web_md.core.app.forms.si.Sijp1140CondForm;
import jp.co.necsoft.web_md.core.app.forms.si.Sijp1150CondForm;
import jp.co.necsoft.web_md.core.app.forms.si.Sijp1150ResultForm;
import jp.co.necsoft.web_md.core.common.CCKubunConst;
import jp.co.necsoft.web_md.core.common.CCMessageConst;
import jp.co.necsoft.web_md.core.common.CCSecurityAuthenticator;
import jp.co.necsoft.web_md.core.common.CCSystemContext;
import jp.co.necsoft.web_md.core.common.biz.kk.CCKKCommon;
import jp.co.necsoft.web_md.core.common.biz.si.CCSIConst;
import jp.co.necsoft.web_md.core.common.forms.ErrorResponse;
import jp.co.necsoft.web_md.core.common.mybatis.dao.M000kaimMapper;
import jp.co.necsoft.web_md.core.common.mybatis.dao.M001jgymMapper;
import jp.co.necsoft.web_md.core.common.mybatis.dao.M003bmnmMapper;
import jp.co.necsoft.web_md.core.common.mybatis.dao.M006tenmMapper;
import jp.co.necsoft.web_md.core.common.mybatis.dao.M017meimMapper;
import jp.co.necsoft.web_md.core.common.mybatis.dto.M000kaim;
import jp.co.necsoft.web_md.core.common.mybatis.dto.M000kaimExample;
import jp.co.necsoft.web_md.core.common.mybatis.dto.M001jgym;
import jp.co.necsoft.web_md.core.common.mybatis.dto.M001jgymExample;
import jp.co.necsoft.web_md.core.common.mybatis.dto.M003bmnm;
import jp.co.necsoft.web_md.core.common.mybatis.dto.M003bmnmExample;
import jp.co.necsoft.web_md.core.common.mybatis.dto.M006tenm;
import jp.co.necsoft.web_md.core.common.mybatis.dto.M006tenmExample;
import jp.co.necsoft.web_md.core.common.mybatis.dto.M017meim;
import jp.co.necsoft.web_md.core.common.mybatis.dto.M017meimExample;
import jp.co.necsoft.web_md.core.common.utils.CCDateUtil;
import jp.co.necsoft.web_md.core.common.utils.CCNumberUtil;
import jp.co.necsoft.web_md.core.common.utils.CCReportUtil;
import jp.co.necsoft.web_md.core.common.utils.CCStringUtil;

import org.joda.time.DateTime;
import org.springframework.beans.BeanUtils;

import play.data.Form;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;

import com.orangesignal.csv.manager.CsvManager;
import com.orangesignal.csv.manager.CsvManagerFactory;

/**
*生鮮納品書のControllerクラス
*
*/
@Security.Authenticated(CCSecurityAuthenticator.class)
public class Sijp1150Ctrl extends Controller {
    /**
     * 削除
     */
    private static final String TYPE_ACT_KBN_DELETED = "9";
    /**
     * CN_ENTRY_KBN
     */
    private static final String CN_ENTRY_KBN = "ENTRY_KBN_";
    /**
     * CN_DPY_KBN
     */
    private static final String CN_DPY_KBN = "DPY_KBN_";

    /**プログラムID*/
    private static final String PROGRAM_ID = "SIPR1150";

    /**プログラムのバージョン*/
    private static final String PROGRAM_VERSION = "V1.00";
    /**帳票出力数*/
    private static final int MAX_MEISAI = 41;
    /**EOS伝票累積F*/
    private static final String DB_EOS = "1";
    /**手書伝票累積F*/
    private static final String DB_TGK = "2";
    /**移動伝票累積F*/
    private static final String DB_IDO = "3";
    /**値上下廃棄伝票F*/
    private static final String DB_NHK = "4";
    /**入荷店のみ*/
    private static final String KBN_IN = CCKubunConst.KBN_VAL_S_COND_TRANSFER_STORE_IN;
    /**出荷店のみ*/
    private static final String KBN_OUT = CCKubunConst.KBN_VAL_S_COND_TRANSFER_STORE_OUT;
    /**両方*/
    private static final String KBN_BOTH = CCKubunConst.KBN_VAL_S_COND_TRANSFER_STORE_BOTH;
    /**
     * 伝票区分リスト
     **/
    private static final String[] DEN_KBN_LIST = { CCSIConst.DEN_KBN.KBN_EOSSIR, CCSIConst.DEN_KBN.KBN_TGKSIR,
            CCSIConst.DEN_KBN.KBN_TGKSIR_D, CCSIConst.DEN_KBN.KBN_HPN, CCSIConst.DEN_KBN.KBN_HPN_D,
            CCSIConst.DEN_KBN.KBN_NBK, CCSIConst.DEN_KBN.KBN_IDO, CCSIConst.DEN_KBN.KBN_ONLINE_UP,
            CCSIConst.DEN_KBN.KBN_ONLINE_REG_DW, CCSIConst.DEN_KBN.KBN_HIK, CCSIConst.DEN_KBN.KBN_HIK_MDF };

    /**取引先コード*/
    private static final String SIR_CD = "取引先コード";
    /**取引先名称*/
    private static final String SIR_NM = "取引先名称";
    /**入荷店コード*/
    private static final String IN_TEN_CD = "入荷店コード";
    /**入荷店名称*/
    private static final String IN_TEN_NM = "入荷店名称";
    /**出荷店コード*/
    private static final String OUT_TEN_CD = "出荷店コード";
    /**出荷店名称*/
    private static final String OUT_TEN_NM = "出荷店名称";
    /**
     * システムコンテキスト
     * */
    @Inject
    private CCSystemContext context;
    /**
     * MyBatisSqlMapDao
     */
    @Inject
    private MyBatisSqlMapDao dao;
    /**
     * m000kaimMapper
     */
    @Inject
    private M000kaimMapper m000kaimMapper;
    /**
     * m001jgymMapper
     */

    @Inject
    private M001jgymMapper m001jgymMapper;
    /**
     * m017meimMapper
     */
    @Inject
    private M017meimMapper m017meimMapper;
    /**
     * m006tenmMapper
     */
    @Inject
    private M006tenmMapper m006tenmMapper;
    /**
     * m003bmnmMapper
     */
    @Inject
    private M003bmnmMapper m003bmnmMapper;
    /**
     * kkCommom
     */
    @Inject
    private CCKKCommon kkCommom;
    /**
     * ErrorResponse
     */
    @Inject
    private ErrorResponse errRes;

    /** 
    * 引数とリクエスト(パラメータ)を条件にDBからデータ(複数件)を検索し返却する。*
    * @param kaisyaCd 会社コード
    * @param jigyoubuCd 事業部コード
    * @return クライアントへ返却する結果
    */
    public Result query(String kaisyaCd, String jigyoubuCd) {
        Form<Sijp1150CondForm> emptyForm = new Form<Sijp1150CondForm>(Sijp1150CondForm.class);
        Form<Sijp1150CondForm> reqForm = emptyForm.bindFromRequest();
        List<Sijp1150ResultForm> reportList = new ArrayList<Sijp1150ResultForm>();
        Sijp1140CondForm req40 = new Sijp1140CondForm();
        if (reqForm.hasErrors()) {
            errRes.addErrorInfo(CCMessageConst.MSG_KEY_VALIDATION_ERROR);
            errRes.addErrorInfo(reqForm.errors());
            return badRequest(Json.toJson(errRes));
        } else {
            Sijp1150CondForm req = reqForm.get();
            req.setKaisyaCd(kaisyaCd);

            req.setJigyoubuCd(jigyoubuCd);
            String sCd = CN_DPY_KBN + req.getDpyKbn();
            String sDenKbnNM = this.getSirName(sCd);
            req.setDpyKbnNm(sDenKbnNM);
            Sijp1150ResultForm resultForm = new Sijp1150ResultForm();
            if (!chkInputData(req, resultForm, req40)) {
                return badRequest(Json.toJson(errRes));
            }
            // 伝区未指定の場合は全伝区分ループ
            boolean bExitFlg = false;
            // 伝区指定有り
            if (!CCStringUtil.isEmpty(req.getDpyKbn()) && req.getDpyKbn().length() != 0) {
                bExitFlg = true;
            }
            // 移動伝票印字区分指定無しで、デフォルト値（両方）をセット
            if ((CCStringUtil.isEmpty(req.getIdoDenKbn()))
                    || (!CCStringUtil.isEmpty(req.getIdoDenKbn()) && req.getIdoDenKbn().length() == 0)) {
                req.setIdoDenKbn(KBN_BOTH);
            }
            List<Sipr1150Report> csvBean = new ArrayList<Sipr1150Report>();

            boolean bWriteFlg = false;
            for (int i = 0; i < DEN_KBN_LIST.length; i++) {
                // 伝区指定無しの場合は、全伝区をセットする
                if (!bExitFlg) {
                    req.setDpyKbn(DEN_KBN_LIST[i]);

                }

                // ------------------------------
                // 伝票累積ファイル取得処理
                // ------------------------------
                boolean bBothFlg = false;

                // 伝区DB種別の取得
                String sDenKDB = this.getDenKbnDB(req.getDpyKbn());

                // 伝区が移動伝票(30-39)の場合
                if (DB_IDO.equals(sDenKDB)) {

                    // 移動伝票印刷区分が3（両方）の場合
                    if (KBN_BOTH.equals(req.getIdoDenKbn())) {

                        // 移動伝票印刷区分のバックアップ
                        req.setIdoDenKbnBk(KBN_BOTH);

                        // 入荷店のみ出力
                        req.setIdoDenKbn(KBN_IN);
                        bBothFlg = true;

                    }

                }
                List<S001eosds033seiDataResult> lsAll = new ArrayList<S001eosds033seiDataResult>();
                List<S001eosds033seiDataResult> lsAllResult = new ArrayList<S001eosds033seiDataResult>();
                List<S001eosds033seiDataResult> ls = new ArrayList<S001eosds033seiDataResult>();
                // 伝区DB種別の取得
                Sijp1150Dto param = new Sijp1150Dto();
                param.setDenKbnDb("0" + sDenKDB);
                String idoDenKbn = "";
                if (!CCStringUtil.isEmpty(req.getIdoDenKbn())) {
                    idoDenKbn = "0" + req.getIdoDenKbn();
                } else {
                    idoDenKbn = req.getIdoDenKbn();
                }
                param.setIdoDenKbn(idoDenKbn);
                param.setKaisyaCd(req.getKaisyaCd());
                param.setJigyobuCd(req.getJigyoubuCd());
                param.setDenKbn(req.getDpyKbn());
                String tenSql = "";
                String bmnSql = "";

                if (req.getLsTenCd() != null && req.getLsTenCd().size() > 0) {
                    for (int j = 0; j < req.getLsTenCd().size(); j++) {
                        if (!CCStringUtil.isEmpty(req.getLsTenCd().get(j))) {
                            if (CCStringUtil.isEmpty(tenSql)) {
                                // 伝区が30～39(移動伝票)
                                if (sDenKDB.equals(DB_IDO)) {
                                    // 店舗=会社+事業者+店
                                    tenSql = req.getKaisyaCd() + req.getJigyoubuCd() + req.getLsTenCd().get(j);
                                    // 移動伝票印刷区分が3（両方）の場合
                                    if (KBN_BOTH.equals(req.getIdoDenKbnBk())) {
                                        req.getLsTenCd().set(j, tenSql);
                                        break;
                                    }

                                } else {
                                    tenSql = req.getLsTenCd().get(j);
                                }

                            } else if (sDenKDB.equals(DB_IDO)) {
                                // 店舗=会社+事業者+店
                                tenSql = req.getKaisyaCd() + req.getJigyoubuCd() + req.getLsTenCd().get(j);
                            } else {
                                tenSql = req.getLsTenCd().get(j);
                            }
                        }
                        req.getLsTenCd().set(j, tenSql);
                    }
                }
                if (req.getLsTenCd() != null && req.getLsTenCd().size() > 0) {
                    param.setTenCds(req.getLsTenCd());
                } else {
                    param.setTenCds(null);
                }
                if (i == 0) {
                    if (req.getLsBmnCd() != null && req.getLsBmnCd().size() > 0) {
                        for (int j = 0; j < req.getLsBmnCd().size(); j++) {
                            if (!CCStringUtil.isEmpty(req.getLsBmnCd().get(j))) {
                                // 伝区が30～39(移動伝票)
                                // 店舗=会社+事業者+店
                                bmnSql = req.getJigyoubuCd() + req.getLsBmnCd().get(j);
                                // 移動伝票印刷区分が3（両方）の場合
                                req.getLsBmnCd().set(j, bmnSql);

                            }
                        }
                    }
                }
                if (req.getLsBmnCd() != null && req.getLsBmnCd().size() > 0) {
                    param.setBmnCds(req.getLsBmnCd());
                } else {
                    param.setBmnCds(null);
                }
                if (req.getLsTorihikiCd() != null && req.getLsTorihikiCd().size() > 0) {
                    param.setTorisakis(req.getLsTorihikiCd());
                } else {
                    param.setTorisakis(null);
                }
                param.setNhnDateSt(req.getNhnYMDSt());
                param.setNhnDateEd(req.getNhnYMDEd());
                param.setNhnYDateSt(req.getCmnInsddSt());
                param.setNhnYDateEd(req.getCmnInsddEd());
                param.setKeijDateSt(req.getKeijdateSt());
                param.setKeijDateEd(req.getKeijdateEd());
                String kakuKbn = req.getEntryKbn();
                String flag = "";
                if (!CCStringUtil.isEmpty(kakuKbn)) {
                    if (kakuKbn.length() == 2 && "*".equals(kakuKbn.substring(1, 2))) {
                        kakuKbn = kakuKbn.substring(0, 1);
                        flag = "1";
                    }
                }
                param.setKakuKbn(kakuKbn);
                param.setFlag(flag);
                ls = dao.selectMany("selectS001eosds033seidSijp1150data", param, S001eosds033seiDataResult.class);
                lsAll.addAll(ls);
                if (bBothFlg) {
                    // 出荷店のみ出力

                    req.setIdoDenKbn(KBN_OUT);
                    if (!CCStringUtil.isEmpty(req.getIdoDenKbn())) {
                        idoDenKbn = "0" + req.getIdoDenKbn();
                    } else {
                        idoDenKbn = req.getIdoDenKbn();
                    }
                    param.setIdoDenKbn(idoDenKbn);
                    // 改頁条件の挿入位置の取得
                    req.setTenRecCnt(ls.size());

                    // DBより取得
                    ls =
                            dao.selectMany("selectS001eosds033seidSijp1150data", param,
                                    S001eosds033seiDataResult.class);

                    lsAll.addAll(ls);
                    // 移動伝票印刷区分を元に戻す
                    req.setIdoDenKbn(KBN_BOTH);

                }
                if (lsAll != null && lsAll.size() > 0) {
                    lsAllResult = this.setPageCngInfo(lsAll, bWriteFlg, req);
                    bWriteFlg = true;
                    this.doCreateCsv(req, csvBean, lsAllResult);
                }
                // 伝区指定ありの場合は、処理を抜ける
                if (bExitFlg) {
                    break;
                }

            }

            // 伝区指定なしの場合は、設定をクリアする
            if (!bExitFlg) {
                req.setDpyKbn("");
            }
            if (csvBean.isEmpty()) {
                // 対象データ０件
                errRes.addErrorInfo(CCMessageConst.MSG_KEY_NO_FOUND_DATA);
                return badRequest(Json.toJson(errRes));
            }
            CCReportUtil cru = new CCReportUtil("SISV1150", context);
            CsvManager csvManager = CsvManagerFactory.newCsvManager();
            // ＣＳＶ作成（データ出力）
            try {
                csvManager.save(csvBean, Sipr1150Report.class).to(new File(cru.getCsvFilePath()),
                        CCReportUtil.CSV_OUTPUT_ENCODING);
            } catch (Exception e) {
                throw new ChaseException(e);
            }
            cru.makePDF();
            try {
                URL uPdfUrl = new URL(cru.getPdfUrl());
                resultForm.setPdfUrl(uPdfUrl);
            } catch (Exception e) {
                throw new ChaseException(e);
            }
            reportList.add(resultForm);
            // gc対応
            cru = null;
            System.gc();
        }
        return ok(Json.toJson(reportList));
    }

    /**
     * Function getJigyobuNameA
     * @param kaisyaCd 会社コード 
     * @param jigyobuCd  事業部コード
     * @return String
     */
    private String getJigyobuNameA(String kaisyaCd, String jigyobuCd) {
        String jgbNm = "";
        DateTime dt = new DateTime();
        String sysDate = dt.toString(CCDateUtil.DATETIME_FORMAT_DATE_YYYYMMDD);
        M001jgymExample example = new M001jgymExample();
        example.createCriteria().andKaisyaCdEqualTo(kaisyaCd).andJigyobuCdEqualTo(jigyobuCd);
        if (!CCStringUtil.isEmpty(sysDate)) {
            example.setSearchDate(sysDate);
        }

        List<M001jgym> list = this.m001jgymMapper.selectByExample(example);
        if (list != null && list.size() > 0 && !TYPE_ACT_KBN_DELETED.equals(list.get(0).getActKbn())) {
            jgbNm = (String) list.get(0).getJgyNmA();
        } else {
            return jgbNm;
        }
        return jgbNm;
    }

    /*****************************************************************
     * 事業部マスタからの事業部名称取得処理.
     * @param sDate 基準日付
     * @param sKaisyaCode 会社コード
     * @param sJigyobuCode 事業部コード
     * @return String 事業部名称
     *****************************************************************/
    private String getJigyobuName(String sDate, String sKaisyaCode, String sJigyobuCode) {

        String sRet = "";

        // 検索条件が未設定の場合、処理を終了する
        if (CCStringUtil.isEmpty(sKaisyaCode) || CCStringUtil.isEmpty(sJigyobuCode)) {
            return sRet;
        }

        // 発効日
        String sBaseDate = "";
        if (CCStringUtil.isEmpty(sDate)) {
            // 日付が指定されていない場合、システム日付をセットする
            sBaseDate = CCDateUtil.getSysDateFormat(CCDateUtil.DATETIME_FORMAT_DATE_YYYYMMDD);
        } else {
            sBaseDate = CCDateUtil.getDateFormat(sDate, CCDateUtil.DATETIME_FORMAT_DATE_YYYYMMDD);
        }
        M001jgymExample example = new M001jgymExample();
        example.createCriteria().andKaisyaCdEqualTo(sKaisyaCode).andJigyobuCdEqualTo(sJigyobuCode);
        if (!CCStringUtil.isEmpty(sBaseDate)) {
            example.setSearchDate(sBaseDate);
        }
        List<M001jgym> list = this.m001jgymMapper.selectByExample(example);

        // 検索件数の取得
        if (list == null || list.size() < 1) {
            return sRet;
        }

        // 事業部名
        sRet = CCStringUtil.trimRight(list.get(0).getJgyNm());
        return sRet;

    }

    /**
     * Function getKaisyaNameA
     * @param kaisyaCd 会社コード
     * @return String
     */
    private String getKaisyaNameA(String kaisyaCd) {
        String kaisyaNmA = "";
        DateTime dt = new DateTime();
        String sysDate = dt.toString(CCDateUtil.DATETIME_FORMAT_DATE_YYYYMMDD);
        M000kaimExample example = new M000kaimExample();

        example.createCriteria().andKaisyaCdEqualTo(kaisyaCd);

        if (!CCStringUtil.isEmpty(sysDate)) {
            example.setSearchDate(sysDate);
        }

        List<M000kaim> list = this.m000kaimMapper.selectByExample(example);
        if (list != null && list.size() > 0 && !TYPE_ACT_KBN_DELETED.equals(list.get(0).getActKbn())) {
            kaisyaNmA = CCStringUtil.trimBoth(list.get(0).getKaiNmA());
        }
        return kaisyaNmA;
    }

    /*****************************************************************
     * 会社マスタからの会社名称取得処理.
     * @param sDate 基準日付
     * @param sKaisyaCode 会社コード
     * @return 会社名称
     *****************************************************************/
    private String getKaisyaName(String sDate, String sKaisyaCode) {

        // 会社コードが指定されていない場合、処理を終了する
        if (CCStringUtil.isEmpty(sKaisyaCode)) {
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

        M000kaimExample example = new M000kaimExample();

        example.createCriteria().andKaisyaCdEqualTo(sKaisyaCode);

        if (!CCStringUtil.isEmpty(sBaseDate)) {
            example.setSearchDate(sBaseDate);
        }

        List<M000kaim> list = this.m000kaimMapper.selectByExample(example);

        // 取得レコード数が「０」の場合、処理を終了
        if (list == null || list.size() < 1) {
            return "";
        }

        // 共通部品より会社名称を取得
        return list.get(0).getKaiNm();

    }

    /**
     * 納品元名 検索処理を行います。<BR>
     * @param sDcTcKbn       ＤＣ/ＴＣ区分
     * @return 納品元名      NotFoundなら"?"
     **/
    private String getSirName(String sDcTcKbn) {
        String sRet = "";
        DateTime dt = new DateTime();
        String sysDate = dt.toString(CCDateUtil.DATETIME_FORMAT_DATE_YYYYMMDD);
        M017meimExample example = new M017meimExample();
        example.createCriteria().andCdKbnEqualTo("SIR").andCdEqualTo(sDcTcKbn);
        if (!CCStringUtil.isEmpty(sysDate)) {
            example.setSearchDate(sysDate);
        }
        List<M017meim> list = this.m017meimMapper.selectByExample(example);
        if (list != null && list.size() > 0 && !TYPE_ACT_KBN_DELETED.equals(list.get(0).getActKbn())) {
            sRet = list.get(0).getNm();
        }

        return sRet;
    }

    /**
     * 納品元名 検索処理を行います。<BR>
     * @param sDcTcKbn  ＤＣ/ＴＣ区分
     * @param sysDate sysDate
     * @return 納品元名      NotFoundなら"?"
     **/
    private String getSirNameForDen(String sDcTcKbn, String sysDate) {
        String sRet = "";
        M017meimExample example = new M017meimExample();
        example.createCriteria().andCdKbnEqualTo("SIR").andCdEqualTo(sDcTcKbn);
        if (!CCStringUtil.isEmpty(sysDate)) {
            example.setSearchDate(sysDate);
        }
        List<M017meim> list = this.m017meimMapper.selectByExample(example);
        if (list != null && list.size() > 0 && !TYPE_ACT_KBN_DELETED.equals(list.get(0).getActKbn())) {
            sRet = list.get(0).getNm();
        }

        return sRet;
    }

    /*****************************************************************
     * 　入力データの整合性チェック処理
     * @param req Sijp1150CondForm
     * @param resultForm Sijp1150ResultForm
     * @param req40 Sijp1140CondForm
     * @return true：処理成功、false：処理失敗
     *****************************************************************/
    private boolean chkInputData(Sijp1150CondForm req, Sijp1150ResultForm resultForm, Sijp1140CondForm req40) {

        boolean bRet = true;

        boolean blErrflg = false;

        // 会社
        String sKaisya = req.getKaisyaCd();

        String sKaisyaNm = this.getKaisyaNameA(sKaisya);
        if (sKaisyaNm.trim().length() == 0) {
            if (!blErrflg) {
                blErrflg = true;
                errRes.addErrorInfo(CCMessageConst.MSG_KEY_VALIDATION_ERROR);
                errRes.addErrorInfo("kaisyaCd", CCMessageConst.MSG_KEY_RESULT_NOT_EXISTS);
            }
        }

        // エラー取得時出力
        if (blErrflg) {
            bRet = false;
        }
        blErrflg = false;
        // 事業部
        String sJigyobu = req.getJigyoubuCd();

        if (!CCStringUtil.isEmpty(sJigyobu)) {
            if (!CCNumberUtil.isNumeric(sJigyobu)) {
                if (!blErrflg) {
                    blErrflg = true;
                    errRes.addErrorInfo(CCMessageConst.MSG_KEY_VALIDATION_ERROR);
                    errRes.addErrorInfo("jigyoubuCd", CCMessageConst.MSG_KEY_ITEM_INPUT_IS_ERROR);
                }
            } else {
                String sJigyobuNm = this.getJigyobuNameA(sKaisya, sJigyobu);
                if (sJigyobuNm.trim().length() == 0) {
                    if (!blErrflg) {
                        errRes.addErrorInfo(CCMessageConst.MSG_KEY_VALIDATION_ERROR);
                        errRes.addErrorInfo("jigyoubuCd", CCMessageConst.MSG_KEY_RESULT_NOT_EXISTS);
                        blErrflg = true;
                    }
                }
            }
        }

        // エラー取得時出力
        if (blErrflg) {
            bRet = false;
        }
        blErrflg = false;
        String sKakKbn = req.getEntryKbn();
        // 入力有り
        if (!CCStringUtil.isEmpty(sKakKbn)) {
            // 確定区分名称の取得
            String sKakuNM = this.getSirName(CN_ENTRY_KBN + sKakKbn);
            if (CCStringUtil.isEmpty(sKakuNM)) {
                if (!blErrflg) {
                    blErrflg = true;
                    errRes.addErrorInfo(CCMessageConst.MSG_KEY_VALIDATION_ERROR);
                    errRes.addErrorInfo("entryKbn", CCMessageConst.MSG_KEY_ITEM_INPUT_IS_ERROR);
                }
            }
        }
        // 確定区分
        String sDenKbn = req.getDpyKbn();

        if (!CCStringUtil.isEmpty(sDenKbn)) {
            if (!CCNumberUtil.isNumeric(sDenKbn)) {
                if (!blErrflg) {
                    blErrflg = true;
                    errRes.addErrorInfo(CCMessageConst.MSG_KEY_VALIDATION_ERROR);
                    errRes.addErrorInfo("dpyKbn", CCMessageConst.MSG_KEY_ITEM_INPUT_IS_ERROR);
                }
            } else {
                String sDenKbnNm = req.getDpyKbnNm();
                if (sDenKbnNm.length() == 0) {
                    if (!blErrflg) {
                        blErrflg = true;
                        errRes.addErrorInfo(CCMessageConst.MSG_KEY_VALIDATION_ERROR);
                        errRes.addErrorInfo("dpyKbn", CCMessageConst.MSG_KEY_ITEM_INPUT_IS_ERROR);
                    }
                }
            }
        }
        // 伝区DB種別の取得
        String sDenKDB = this.getDenKbnDB(req.getDpyKbn());
        // 移動伝票印字区分
        String sIdoDenKbn = req.getIdoDenKbn();

        // 入力有り
        if (!CCStringUtil.isEmpty(sIdoDenKbn)) {
            if (!KBN_IN.equals(sIdoDenKbn) && !KBN_OUT.equals(sIdoDenKbn) && !KBN_BOTH.equals(sIdoDenKbn)) {
                if (!blErrflg) {
                    blErrflg = true;
                    errRes.addErrorInfo(CCMessageConst.MSG_KEY_VALIDATION_ERROR);
                    errRes.addErrorInfo("idoDenKbn", CCMessageConst.MSG_KEY_ITEM_INPUT_IS_ERROR);
                }
            }
        } else {
            // 移動伝票の場合
            if (DB_IDO.equals(sDenKDB)) {
                if (!blErrflg) {
                    blErrflg = true;
                    errRes.addErrorInfo(CCMessageConst.MSG_KEY_VALIDATION_ERROR);
                    errRes.addErrorInfo("idoDenKbn", CCMessageConst.MSG_KEY_NOT_KIP_ITEM);

                }
            }
        }

        // エラー取得時出力
        if (blErrflg) {
            bRet = false;
        }
        blErrflg = false;

        // 入力有無フラグ
        boolean bInpFlg = false;
        List<String> lsTenCd = new ArrayList<String>();
        List<String> lsBumon = new ArrayList<String>();
        List<String> lsTorihiki = new ArrayList<String>();
        if (req.getTenCds() != null && req.getTenCds() != "") {
            String[] tmp = req.getTenCds().split(",");
            for (int i = 0; i < tmp.length; i++) {
                if (!CCStringUtil.isEmpty(tmp[i])) {
                    lsTenCd.add(tmp[i]);
                }
            }
            req.setLsTenCd(lsTenCd);
        }
        if (req.getBmnCds() != null && req.getBmnCds() != "") {
            String[] tmp = req.getBmnCds().split(",");
            for (int i = 0; i < tmp.length; i++) {
                if (!CCStringUtil.isEmpty(tmp[i])) {
                    lsBumon.add(tmp[i]);
                }
            }
            req.setLsBmnCd(lsBumon);
        }
        if (req.getTorihikiCds() != null && req.getTorihikiCds() != "") {
            String[] tmp = req.getTorihikiCds().split(",");
            for (int i = 0; i < tmp.length; i++) {
                if (!CCStringUtil.isEmpty(tmp[i])) {
                    lsTorihiki.add(tmp[i] + "000");
                }
            }
            req.setLsTorihikiCd(lsTorihiki);
        }

        for (int i = 0; i < lsTenCd.size(); i++) {

            // 店舗
            String sTenpo = lsTenCd.get(i);
            if (!CCStringUtil.isEmpty(sTenpo)) {
                if (!CCNumberUtil.isNumeric(sTenpo)) {
                    if (!blErrflg) {
                        errRes.addErrorInfo(CCMessageConst.MSG_KEY_VALIDATION_ERROR);
                        errRes.addErrorInfo("tenCd", CCMessageConst.MSG_KEY_ITEM_INPUT_IS_ERROR);
                        blErrflg = true;
                    }
                }

            }
        }
        // エラー取得時出力
        if (blErrflg) {
            bRet = false;
        }
        blErrflg = false;
        for (int i = 0; i < lsBumon.size(); i++) {
            // 部門
            String sBumon = lsBumon.get(i);
            if (!CCStringUtil.isEmpty(sBumon)) {
                if (!CCNumberUtil.isNumeric(sBumon)) {
                    if (!blErrflg) {
                        errRes.addErrorInfo(CCMessageConst.MSG_KEY_VALIDATION_ERROR);
                        errRes.addErrorInfo("bmnCd", CCMessageConst.MSG_KEY_ITEM_INPUT_IS_ERROR);
                        blErrflg = true;
                    }
                }
            }
        }
        // エラー取得時出力
        if (blErrflg) {
            bRet = false;
        }
        blErrflg = false;
        for (int i = 0; i < lsTorihiki.size(); i++) {
            // 取引先
            String sTorihiki = lsTorihiki.get(i);
            if (!DB_IDO.equals(sDenKDB) && !DB_NHK.equals(sDenKDB)) {
                if (!CCStringUtil.isEmpty(sTorihiki)) {
                    if (!CCNumberUtil.isNumeric(sTorihiki)) {
                        if (!blErrflg) {
                            errRes.addErrorInfo(CCMessageConst.MSG_KEY_VALIDATION_ERROR);
                            errRes.addErrorInfo("torihikiCd", CCMessageConst.MSG_KEY_ITEM_INPUT_IS_ERROR);
                            blErrflg = true;
                        }
                    }
                }
            }
        }
        // エラー取得時出力
        if (blErrflg) {
            bRet = false;
        }
        blErrflg = false;
        // 日付 From To
        // 納品予定日
        String sNhnDateSt = req.getNhnYMDSt();
        if (!CCStringUtil.isEmpty(sNhnDateSt)) {
            bInpFlg = true;
            // 納品予定日の日付妥当性チェック
            if (!CCDateUtil.isDate(sNhnDateSt)) {
                errRes.addErrorInfo(CCMessageConst.MSG_KEY_VALIDATION_ERROR);
                errRes.addErrorInfo("nhnYMDSt", CCMessageConst.MSG_KEY_DATE_ERROR);
                return false;
            }
        }
        String sNhnDateEd = req.getNhnYMDEd();
        if (!CCStringUtil.isEmpty(sNhnDateEd)) {
            bInpFlg = true;
            // 納品予定日の日付妥当性チェック
            if (!CCDateUtil.isDate(sNhnDateEd)) {
                errRes.addErrorInfo(CCMessageConst.MSG_KEY_VALIDATION_ERROR);
                errRes.addErrorInfo("nhnYMDEd", CCMessageConst.MSG_KEY_DATE_ERROR);
                return false;
            }
        }

        // エラー取得時出力
        if (blErrflg) {
            bRet = false;
        }
        blErrflg = false;

        // 納品予定日
        String cmnInsddSt = req.getCmnInsddSt();
        if (!CCStringUtil.isEmpty(cmnInsddSt)) {
            bInpFlg = true;
            // 納品予定日の日付妥当性チェック
            if (!CCDateUtil.isDate(cmnInsddSt)) {
                errRes.addErrorInfo(CCMessageConst.MSG_KEY_VALIDATION_ERROR);
                errRes.addErrorInfo("cmnInsddSt", CCMessageConst.MSG_KEY_DATE_ERROR);
                return false;
            }
        }
        String cmnInsddEd = req.getCmnInsddEd();
        if (!CCStringUtil.isEmpty(cmnInsddEd)) {
            bInpFlg = true;
            // 納品予定日の日付妥当性チェック
            if (!CCDateUtil.isDate(cmnInsddEd)) {
                errRes.addErrorInfo(CCMessageConst.MSG_KEY_VALIDATION_ERROR);
                errRes.addErrorInfo("cmnInsddEd", CCMessageConst.MSG_KEY_DATE_ERROR);
                return false;
            }
        }

        // エラー取得時出力
        if (blErrflg) {
            bRet = false;
        }
        blErrflg = false;
        // 納品予定日
        String keijdateSt = req.getKeijdateSt();
        if (!CCStringUtil.isEmpty(keijdateSt)) {
            bInpFlg = true;
            // 納品予定日の日付妥当性チェック
            if (!CCDateUtil.isDate(keijdateSt)) {
                errRes.addErrorInfo(CCMessageConst.MSG_KEY_VALIDATION_ERROR);
                errRes.addErrorInfo("keijdateSt", CCMessageConst.MSG_KEY_DATE_ERROR);
                return false;
            }
        }
        String keijdateEd = req.getKeijdateEd();
        if (!CCStringUtil.isEmpty(keijdateEd)) {
            bInpFlg = true;
            // 納品予定日の日付妥当性チェック
            if (!CCDateUtil.isDate(keijdateEd)) {
                errRes.addErrorInfo(CCMessageConst.MSG_KEY_VALIDATION_ERROR);
                errRes.addErrorInfo("keijdateEd", CCMessageConst.MSG_KEY_DATE_ERROR);
                return false;
            }
        }
        // 入力有無フラグがFalseの場合
        if (!bInpFlg) {
            if (!blErrflg) {
                blErrflg = true;
                errRes.addErrorInfo(CCMessageConst.MSG_KEY_VALIDATION_ERROR);
                errRes.addErrorInfo("nhnYMDSt", CCMessageConst.MSG_KEY_NOT_KIP_ITEM);
            }

        }

        // エラー取得時出力
        if (blErrflg) {
            bRet = false;
        }

        return bRet;

    }

    /**
     * ＣＳＶファイルを作成します。<BR>
     * @param req Sijp1020CondForm
     * @param lstRetData List<S001eosds033seiDataResult>
     * @param csvBean List<Sipr1020Report>
     * @return 　True:OK　　False:NG
    **/
    private boolean doCreateCsv(Sijp1150CondForm req, List<Sipr1150Report> csvBean,
            List<S001eosds033seiDataResult> lstRetData) {
        // 伝区DB種別の取得
        String sDenKDB = this.getDenKbnDB(req.getDpyKbn());
        int iLen = 0;
        String sHatDate = "";
        // 取引先
        String sToriCode = "";
        // 取引先名称
        String sToriName = "";
        String sKasanKbn = "";
        Sipr1150Report dto2 = new Sipr1150Report();
        setCSVHeaderData(req, dto2);
        // ***** 明細データの読み込み & 編集 *****
        for (int i = 0; i < lstRetData.size(); i++) {
            Sipr1150Report dto = new Sipr1150Report();
            BeanUtils.copyProperties(dto2, dto);
            S001eosds033seiDataResult data = lstRetData.get(i);

            // 納品予定日
            String sTenCd = "";
            if (data.getTenCd() != null && data.getTenCd().length() == 0) {
                sTenCd = req.getTenFront();
            } else {
                iLen = data.getTenCd().length() - 3;
                sTenCd = data.getTenCd().substring(iLen);
            }
            dto.h01_ten_cd = sTenCd;

            // 部門コード
            String sBmnCd = "";
            if (data.getBmnCd() != null && data.getBmnCd().length() == 0) {
                sBmnCd = req.getBmnFront();
            } else {
                iLen = data.getBmnCd().length() - 3;
                sBmnCd = data.getBmnCd().substring(iLen);

            }
            dto.h01_bmn_cd = sBmnCd;

            // 伝区
            String sDenKbn = "";
            if (data.getDpyKbn() != null && data.getDpyKbn().length() == 0) {
                sDenKbn = req.getDenKFront();
            } else {
                sDenKbn = data.getDpyKbn();

            }
            dto.h01_denkbn = sDenKbn;

            // 名称取得
            String sTenNm = "";
            String sBmnNm = "";
            String sDenKNM = "";
            // 改頁用のレコードの場合
            if ((data.getNhnYmd() == null) || (data.getNhnYmd() != null && data.getNhnYmd().length() == 0)) {

                // 前頁のコードと名称をセットする（ヘッダ表示用）
                dto.h01_ten_cd_hd = req.getTenFront();
                dto.h01_bmn_cd_hd = req.getBmnFront();
                dto.h01_denkbn_hd = req.getDenKFront();

                // 名称の取得
                sTenNm = getTenFrontNM(req, req.getTenFront());
                sBmnNm = getBmnFrontNM(req, req.getBmnFront());
                sDenKNM = getDenKFrontNM(req, req.getDenKFront());

            } else {

                // 前頁のコードと名称をセットする（ヘッダ表示用）
                dto.h01_ten_cd_hd = sTenCd;
                dto.h01_bmn_cd_hd = sBmnCd;
                dto.h01_denkbn_hd = sDenKbn;

                // 名称の取得
                sTenNm = getTenFrontNM(req, sTenCd);
                sBmnNm = getBmnFrontNM(req, sBmnCd);
                sDenKNM = getDenKFrontNM(req, sDenKbn);

            }
            dto.h01_ten_name = sTenNm;
            dto.h01_bmn_name = sBmnNm;
            dto.h01_denkbn_name = sDenKNM;

            // -----------------
            // 明細ヘッダタイトル
            // -----------------
            String sTitleTenCd = "";
            String sTitleTenNm = "";

            // 移動伝票累積Fの場合
            if (DB_IDO.equals(sDenKDB)) {

                // 明細タイトル
                // 入荷店のみ
                if (KBN_IN.equals(req.getIdoDenKbn())) {
                    // 入荷店コード
                    sTitleTenCd = IN_TEN_CD;
                    // 入荷店名称
                    sTitleTenNm = IN_TEN_NM;

                } else if (KBN_OUT.equals(req.getIdoDenKbn())) {
                    // 出荷店コード
                    sTitleTenCd = OUT_TEN_CD;
                    // 出荷店名称
                    sTitleTenNm = OUT_TEN_NM;

                } else if (KBN_BOTH.equals(req.getIdoDenKbn())) {

                    // 入荷店取得時のレコードの場合
                    if (i < req.getTenRecCnt()) {
                        // 入荷店コード
                        sTitleTenCd = IN_TEN_CD;
                        // 入荷店名称
                        sTitleTenNm = IN_TEN_NM;
                    } else {
                        // 出荷店コード
                        sTitleTenCd = OUT_TEN_CD;
                        // 出荷店名称
                        sTitleTenNm = OUT_TEN_NM;
                    }

                }

            } else {
                // 取引先コード
                sTitleTenCd = SIR_CD;
                // 取引先名称
                sTitleTenNm = SIR_NM;
            }

            // 改頁用のレコードの場合
            if ((data.getNhnYmd() == null) || (data.getNhnYmd() != null && data.getNhnYmd().length() == 0)) {
                // 取引先コード
                sTitleTenCd = req.getTitleCd();
                // 取引先名称
                sTitleTenNm = req.getTitleName();
            } else {
                req.setTitleCd(sTitleTenCd);
                req.setTitleName(sTitleTenNm);
            }

            dto.h02_sir_cd = sTitleTenCd;
            dto.h02_sir_name = sTitleTenNm;

            // 移動伝票累積F
            if (sDenKDB.equals(DB_IDO)) {

                // 発注日
                sHatDate = data.getHatYmd();

                // 店舗ｺｰﾄﾞ
                if (data.getTorihikiCd() != null && data.getTorihikiCd().length() != 0) {
                    // 下3桁を取得する
                    iLen = data.getTorihikiCd().length() - 3;
                    if (iLen < 0) {
                        sToriCode = data.getTorihikiCd();
                    } else {
                        sToriCode = CCStringUtil.trimBoth(data.getTorihikiCd());
                    }
                    sToriName = this.getToriFrontNM(req, sToriCode, sDenKDB);

                } else {
                    sToriCode = "";
                    sToriName = "";

                }

            } else if (sDenKDB.equals(DB_NHK)) {
                if (!CCStringUtil.isEmpty(data.getHatYmd())) {
                    sKasanKbn = data.getHatYmd();
                }
            } else {
                // 発注日
                sHatDate = data.getHatYmd();

                // 取引先ｺｰﾄﾞ
                sToriCode = data.getTorihikiCd();

                // 取引先名
                sToriName = getToriFrontNM(req, sToriCode, sDenKDB);

            }

            if (!CCStringUtil.isEmpty(sToriCode) && sToriCode.trim().length() == 7) {
                sToriCode =
                        sToriCode.substring(0, 2) + "-" + sToriCode.substring(2, 4) + "-"
                                + sToriCode.substring(4, 7);
            } else if (!CCStringUtil.isEmpty(sToriCode) && sToriCode.trim().length() == 9) {
                sToriCode = sToriCode.substring(0, 6) + "-" + sToriCode.substring(6, 9);
            }
            // -----------------
            // 明細
            // -----------------
            // 発注日
            dto.m01_hat_date = sHatDate;

            // 伝票番号
            dto.m01_denno = data.getDpyNo();
            // 取引先ｺｰﾄﾞ
            dto.m01_sir_cd = sToriCode;
            // 取引先名
            dto.m01_sir_name = "'" + sToriName + "'";

            // 納品日
            dto.m01_nhn_date = data.getNhnYmd();

            // 原価金額
            dto.m01_gen_kingk = sKasanKbn + data.getsKenGenkKin();
            // 売価金額
            dto.m01_bai_kingk = sKasanKbn + data.getsKenBaikKin();
            // 改頁条件
            dto.m01_page_cng = data.getKasanKbn();

            csvBean.add(dto);
        }

        return true;

    }

    /*****************************************************************
     * 帳票出力ヘッダ部の設定（会社/事業部可変）.
     * @param req Sijp1150CondForm
     * @param dto Sipr1150Report
     * @return true：処理成功、false：処理失敗
     *****************************************************************/
    private boolean setCSVHeaderData(Sijp1150CondForm req, Sipr1150Report dto) {
        String sDate = "";

        // プログラムＩＤ
        dto.h00_progid = PROGRAM_ID;

        // バージョン
        dto.h00_version = PROGRAM_VERSION;

        // システム日付
        dto.h00_sdate = CCDateUtil.getSysYear() + CCDateUtil.getSysMonth() + CCDateUtil.getSysDate();

        // システム時間
        dto.h00_stime = CCDateUtil.getSysHour() + CCDateUtil.getSysMinute();

        // ページ番号
        dto.h00_page = "";

        // 担当者コード
        dto.h01_tanto_code = context.getTantoCode();

        // 会社コード
        dto.h01_kaisya_code = req.getKaisyaCd();

        // 会社名(共通部品より取得)
        dto.h01_kaisya_name = this.getKaisyaName(sDate, req.getKaisyaCd());
        // 事業部コード
        dto.h01_jigyobu_code = req.getJigyoubuCd();

        // 事業部名称
        dto.h01_jigyobu_name = this.getJigyobuName(sDate, req.getKaisyaCd(), req.getJigyoubuCd());

        return true;

    }

    /*****************************************************************
     * 伝区DB種別取得処理.
     * @param denKbn String
     * @return 伝区DB種別
     *****************************************************************/
    public String getDenKbnDB(String denKbn) {

        String sRet = "";

        if (CCStringUtil.isEmpty(denKbn)) {
            return sRet;
        }

        // EOS伝票累積F
        if (CCSIConst.DEN_KBN.KBN_EOSSIR.equals(denKbn)) {
            sRet = DB_EOS;
        } else if (CCSIConst.DEN_KBN.KBN_TGKSIR.equals(denKbn) || CCSIConst.DEN_KBN.KBN_TGKSIR_D.equals(denKbn)
                || CCSIConst.DEN_KBN.KBN_HPN.equals(denKbn) || CCSIConst.DEN_KBN.KBN_HPN_D.equals(denKbn)
                || CCSIConst.DEN_KBN.KBN_NBK.equals(denKbn)) {
            sRet = DB_TGK;
        } else if (CCSIConst.DEN_KBN.KBN_IDO.equals(denKbn)) {
            sRet = DB_IDO;

        } else if (CCSIConst.DEN_KBN.KBN_ONLINE_UP.equals(denKbn) || CCSIConst.DEN_KBN.KBN_ONLINE_REG_DW.equals(denKbn)
                || CCSIConst.DEN_KBN.KBN_HIK.equals(denKbn) || CCSIConst.DEN_KBN.KBN_HIK_MDF.equals(denKbn)) {
            sRet = DB_NHK;
        }
        return sRet;

    }

    /*****************************************************************
     * 改頁条件設定処理
     * @param  lstSirData List<S001eosds033seiDataResult>
     * @param bWriteFlg boolean
     * @param form Sijp1150CondForm
     * @return List<S001eosds033seiDataResult>
     *****************************************************************/
    private List<S001eosds033seiDataResult> setPageCngInfo(List<S001eosds033seiDataResult> lstSirData,
            boolean bWriteFlg, Sijp1150CondForm form) {

        S001eosds033seiDataResult data = form.getDataBuf();
        // データ編集用のバッファを初期化する
        List<S001eosds033seiDataResult> lstRetData = new ArrayList<S001eosds033seiDataResult>();

        // カウンタの初期化を行う
        int iRowCnt = 0; // 行カウント
        int iTenCnt = 0;
        // コピー元データをバッファにコピーする
        S001eosds033seiDataResult sExecuteData = lstSirData.get(0);

        if (bWriteFlg) {

            if (!CCStringUtil.isEmpty(data.getKasanKbn())) {
                data.setTenCd(sExecuteData.getTenCd());
                data.setBmnCd(sExecuteData.getBmnCd());
                data.setDpyKbn(sExecuteData.getDpyKbn());

                // 返却リストへ値をコピー
                lstRetData.add(data);

                // 入荷店取得時のレコードの場合
                if (form.getTenRecCnt() > 0) {
                    ++iTenCnt;
                }
                data = new S001eosds033seiDataResult();

            }

        }

        // 改頁条件用カラムを設定する
        sExecuteData.setKasanKbn(String.valueOf(form.getPageCngCnt()));

        // 返却リストへ値をコピー
        lstRetData.add(sExecuteData);

        String saveKey = sExecuteData.getTenCd() + sExecuteData.getBmnCd() + sExecuteData.getDpyKbn();
        // 改頁条件を調整する
        for (int i = 1; i < lstSirData.size(); i++) {

            // 行カウントをインクリメント
            iRowCnt++;

            // 改頁用データをバッファにコピーする
            S001eosds033seiDataResult sExecuteDataBuf = new S001eosds033seiDataResult();
            BeanUtils.copyProperties(sExecuteData, sExecuteDataBuf);

            // コピー元データをバッファにコピーする
            sExecuteData = lstSirData.get(i);

        	String key = sExecuteData.getTenCd() + sExecuteData.getBmnCd() + sExecuteData.getDpyKbn();
        	if (!key.equals(saveKey) || form.getTenRecCnt() == i) {
        		saveKey = key;

                // ページカウントをインクリメント
                form.setPageCngCnt(form.getPageCngCnt() + 1);

                // 入荷店取得時のレコードの場合
                if (form.getTenRecCnt() > 0) {
                    ++iTenCnt;
                }
        		iRowCnt = 0;
        	}

            // 改頁条件用カラムを設定する
            sExecuteData.setKasanKbn(String.valueOf(form.getPageCngCnt()));

            // 返却リストへ値をコピー
            lstRetData.add(sExecuteData);

            sExecuteDataBuf = new S001eosds033seiDataResult();

        }

        // ページカウントをインクリメント
        form.setPageCngCnt(form.getPageCngCnt() + 1);
        form.setTenRecCnt(form.getTenRecCnt() + iTenCnt);
        sExecuteData = new S001eosds033seiDataResult();
        form.setDataBuf(data);
        return lstRetData;

    }

    /*****************************************************************
     * 前レコードの店名称取得.
     * @param req Sijp1150CondForm
     * @param sTenCd 店舗コード
     * @return 店舗名称
     *****************************************************************/
    private String getTenFrontNM(Sijp1150CondForm req, String sTenCd) {

        String tenNm = "";
        // 会社ｺｰﾄﾞの取得
        String sKaisyaCd = req.getKaisyaCd();
        // 事業所ｺｰﾄﾞの取得
        String sJigyoCd = req.getJigyoubuCd();

        // 店コードをチェックする
        if (!sTenCd.equals(req.getTenFront())) {

            // 店コードが保持している値と異なる場合
            // 名称の取得
            tenNm = this.getComTenName("", sKaisyaCd, sJigyoCd, sTenCd);
            req.setTenFrontName(tenNm);
            // 前レコードの保持
            req.setTenFront(sTenCd);

        }

        return req.getTenFrontName();

    }

    /*****************************************************************
     * 店舗マスタからの店舗名称取得処理.
     * @param sDate String
     * @param sKaisyaCode 会社コード
     * @param sJigyobuCode 事業部コード
     * @param sTenCode 店舗コード
     * @return 店舗名称
     *****************************************************************/
    private String getComTenName(String sDate, String sKaisyaCode, String sJigyobuCode, String sTenCode) {

        String sRet = "";

        // 検索条件が未設定の場合、処理を終了する
        if (CCStringUtil.isEmpty(sKaisyaCode) || CCStringUtil.isEmpty(sJigyobuCode) || CCStringUtil.isEmpty(sTenCode)) {
            return sRet;
        }

        // 抽出条件:会社コード,事業部コード、店舗コード
        String sCodeBuf =
                CCStringUtil.suppZero(sKaisyaCode, 2) + CCStringUtil.suppZero(sJigyobuCode, 2)
                        + CCStringUtil.suppZero(sTenCode, 3);
        // 発効日
        String sBaseDate = "";
        if (sDate.equals("")) {
            // 日付が指定されていない場合、システム日付をセットする
            sBaseDate = CCDateUtil.getSysDateFormat(CCDateUtil.DATETIME_FORMAT_DATE_YYYYMMDD);
        } else {
            sBaseDate = CCDateUtil.getDateFormat(sDate, CCDateUtil.DATETIME_FORMAT_DATE_YYYYMMDD);
        }

        M006tenmExample example = new M006tenmExample();
        example.createCriteria().andTenCdEqualTo(sCodeBuf);
        if (!CCStringUtil.isEmpty(sBaseDate)) {
            example.setSearchDate(sBaseDate);
        }
        // 検索処理実行（第一引数：発効日≦日付で検索,第二引数：指定した項目=渡したデータで検索
        List<M006tenm> list = this.m006tenmMapper.selectByExample(example);

        if (list == null || list.size() <= 0) {
            return sRet;
        }
        // 店舗名称
        sRet = CCStringUtil.trimRight(list.get(0).getTenNm());
        return sRet;

    }

    /*****************************************************************
     * 前レコードの部門名称取得.
     * @param req Sijp1150CondForm
     * @param sBmnCd String
     * @return 部門名称
     *****************************************************************/
    private String getBmnFrontNM(Sijp1150CondForm req, String sBmnCd) {
        String bmnNm = "";

        // 事業所ｺｰﾄﾞの取得
        String sJigyoCd = req.getJigyoubuCd();

        // 部門コードをチェックする
        if (sBmnCd != null && !sBmnCd.equals(req.getBmnFront())) {

            // 名称の取得
            bmnNm = this.getComBumonName("", sJigyoCd, sBmnCd);
            req.setBmnFrontName(bmnNm);
            // 前レコードの保持
            req.setBmnFront(sBmnCd);

        }

        return req.getBmnFrontName();

    }

    /*****************************************************************
     * 部門マスタからの部門名称取得処理.
     * @param sDate String
     * @param sKaisyaCode 会社コード
     * @param sBumonCode 部門コード
     * @return 部門名称
     *****************************************************************/
    private String getComBumonName(String sDate, String sKaisyaCode, String sBumonCode) {

        String sRet = "";

        // 検索条件が未設定の場合、処理を終了する
        if (CCStringUtil.isEmpty(sKaisyaCode) || CCStringUtil.isEmpty(sBumonCode)) {
            return sRet;
        }

        // 抽出条件:会社コード＋部門コード
        String sCodeBuf = CCStringUtil.suppZero(sKaisyaCode, 2) + CCStringUtil.suppZero(sBumonCode, 3);

        M003bmnmExample example = new M003bmnmExample();

        example.createCriteria().andBmnCdEqualTo(sCodeBuf);
        // 発効日
        String sBaseDate = "";
        if (sDate.equals("")) {
            // 日付が指定されていない場合、システム日付をセットする
            sBaseDate = CCDateUtil.getSysDateFormat(CCDateUtil.DATETIME_FORMAT_DATE_YYYYMMDD);
        } else {
            sBaseDate = CCDateUtil.getDateFormat(sDate, CCDateUtil.DATETIME_FORMAT_DATE_YYYYMMDD);
        }

        if (!CCStringUtil.isEmpty(sBaseDate)) {
            example.setSearchDate(sBaseDate);
        }

        List<M003bmnm> list = this.m003bmnmMapper.selectByExample(example);
        if (list == null || list.size() <= 0) {
            return sRet;
        }

        // 部門名称
        sRet = CCStringUtil.trimBoth(list.get(0).getBmnNm());
        return sRet;

    }

    /*****************************************************************
     * 前レコードの伝区名称取得.
     * @param req Sijp1150CondForm
     * @param sDenKbn 伝区コード
     * @return 伝区名称
     *****************************************************************/
    private String getDenKFrontNM(Sijp1150CondForm req, String sDenKbn) {

        // システム日付
        String sSysDate = CCDateUtil.getSysYear() + CCDateUtil.getSysMonth() + CCDateUtil.getSysDate();
        String denKFrontNM = "";

        // 伝区コードをチェックする
        if (sDenKbn != null && !sDenKbn.equals(req.getDenKFront())) {

            // 伝区コードが保持している値と異なる場合
            String sCd = CN_DPY_KBN + sDenKbn;
            denKFrontNM = this.getSirNameForDen(sCd, sSysDate);
            req.setDenKFrontName(denKFrontNM);
            // 前レコードの保持
            req.setDenKFront(sDenKbn);

        }

        return req.getDenKFrontName();

    }

    /*****************************************************************
     * 前レコードの取引先名称取得.
     * @param req Sijp1150CondForm
     * @param sToriCd 取引先コード
     * @param sDenKDB 伝票区分
     * @return 取引先名称
     *****************************************************************/
    private String getToriFrontNM(Sijp1150CondForm req, String sToriCd, String sDenKDB) {
        String toriNm = "";

        // 取引先コードをチェックする
        if (sToriCd != null && !sToriCd.equals(req.getToriFront())) {

            // 取引先コードが保持している値と異なる場合
            if (sDenKDB.equals(DB_IDO)) {
                toriNm =
                        this.getComTenName("", sToriCd.substring(0, 2), sToriCd.substring(2, 4),
                                sToriCd.substring(4, 7));
            } else {
                toriNm = kkCommom.getComToriName("", sToriCd);
            }
            req.setToriFrontName(toriNm);
            req.setToriFront(sToriCd);

        }
        return req.getToriFrontName();

    }

}
