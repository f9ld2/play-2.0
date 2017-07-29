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
import jp.co.necsoft.web_md.core.app.dto.si.Sijp1140Dto;
import jp.co.necsoft.web_md.core.app.file.report.Sipr1140Report;
import jp.co.necsoft.web_md.core.app.forms.si.Sijp1140CondForm;
import jp.co.necsoft.web_md.core.app.forms.si.Sijp1140ResultForm;
import jp.co.necsoft.web_md.core.common.CCMessageConst;
import jp.co.necsoft.web_md.core.common.CCSecurityAuthenticator;
import jp.co.necsoft.web_md.core.common.CCSystemContext;
import jp.co.necsoft.web_md.core.common.biz.si.CCSIConst;
import jp.co.necsoft.web_md.core.common.forms.ErrorInfo;
import jp.co.necsoft.web_md.core.common.forms.ErrorResponse;
import jp.co.necsoft.web_md.core.common.mybatis.dao.M000kaimMapper;
import jp.co.necsoft.web_md.core.common.mybatis.dao.M001jgymMapper;
import jp.co.necsoft.web_md.core.common.mybatis.dao.M003bmnmMapper;
import jp.co.necsoft.web_md.core.common.mybatis.dao.M006tenmMapper;
import jp.co.necsoft.web_md.core.common.mybatis.dao.M011trimMapper;
import jp.co.necsoft.web_md.core.common.mybatis.dao.M017meimMapper;
import jp.co.necsoft.web_md.core.common.mybatis.dto.M000kaim;
import jp.co.necsoft.web_md.core.common.mybatis.dto.M000kaimExample;
import jp.co.necsoft.web_md.core.common.mybatis.dto.M001jgym;
import jp.co.necsoft.web_md.core.common.mybatis.dto.M001jgymExample;
import jp.co.necsoft.web_md.core.common.mybatis.dto.M003bmnm;
import jp.co.necsoft.web_md.core.common.mybatis.dto.M003bmnmExample;
import jp.co.necsoft.web_md.core.common.mybatis.dto.M006tenm;
import jp.co.necsoft.web_md.core.common.mybatis.dto.M006tenmExample;
import jp.co.necsoft.web_md.core.common.mybatis.dto.M011trim;
import jp.co.necsoft.web_md.core.common.mybatis.dto.M011trimExample;
import jp.co.necsoft.web_md.core.common.mybatis.dto.M017meim;
import jp.co.necsoft.web_md.core.common.mybatis.dto.M017meimExample;
import jp.co.necsoft.web_md.core.common.utils.CCDateUtil;
import jp.co.necsoft.web_md.core.common.utils.CCNumberUtil;
import jp.co.necsoft.web_md.core.common.utils.CCReportUtil;
import jp.co.necsoft.web_md.core.common.utils.CCStringUtil;

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
public class Sijp1140Ctrl extends Controller {
    /**
     * 削除
     */
    private static final String TYPE_ACT_KBN_DELETED = "9";
    /**
     * CN_DPY_KBN
     */
    private static final String CN_DPY_KBN = "DPY_KBN_";

    /**プログラムID*/
    private static final String PROGRAM_ID = "SIPR1140";

    /**プログラムのバージョン*/
    private static final String PROGRAM_VERSION = "V1.00";
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
     * m011trimMapper
     */
    @Inject
    private M011trimMapper m011trimMapper;
    /**
     * ErrorResponse
     */
    @Inject
    private ErrorResponse errRes;

    /** 
    * 引数とリクエスト(パラメータ)を条件にDBからデータ(複数件)を検索し返却する。*
    * @param kaisyaCd 会社コード
    * @param jigyoubuCd 基準日付F
    * @param dpyKbn 伝区
    * @return クライアントへ返却する結果
    */
    public Result query(String kaisyaCd, String jigyoubuCd, String dpyKbn) {
        Form<Sijp1140CondForm> emptyForm = new Form<Sijp1140CondForm>(Sijp1140CondForm.class);
        Form<Sijp1140CondForm> reqForm = emptyForm.bindFromRequest();
        List<Sijp1140ResultForm> reportList = new ArrayList<Sijp1140ResultForm>();
        if (reqForm.hasErrors()) {
            errRes.addErrorInfo(CCMessageConst.MSG_KEY_VALIDATION_ERROR);
            errRes.addErrorInfo(reqForm.errors());
            return badRequest(Json.toJson(errRes));
        } else {
            Sijp1140CondForm req = reqForm.get();
            req.setKaisyaCd(kaisyaCd);

            req.setJigyoubuCd(jigyoubuCd);
            req.setDpyKbn(dpyKbn);
            String sCd = CN_DPY_KBN + req.getDpyKbn();
            String sDenKbnNM = this.getSirName(sCd);
            req.setDpyKbnNm(sDenKbnNM);
            Sijp1140ResultForm resultForm = new Sijp1140ResultForm();
            if (!chkInputData(req)) {
                ErrorInfo error = errRes.getErrorInfo(CCMessageConst.MSG_KEY_ITEM_NOT_INSERT);
                errRes.getErrors().add(0, error);
                return badRequest(Json.toJson(errRes));
            }
            CCReportUtil cru = new CCReportUtil("SISV1140", context);
            CsvManager csvManager = CsvManagerFactory.newCsvManager();
            List<Sipr1140Report> csvBean = new ArrayList<Sipr1140Report>();
            if (!this.doCreateCsv(req, csvBean)) {
                return badRequest(Json.toJson(errRes));
            }
            // ＣＳＶ作成（データ出力）
            try {
                csvManager.save(csvBean, Sipr1140Report.class).to(new File(cru.getCsvFilePath()),
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
        String sysDate = CCDateUtil.getSysDateFormat(CCDateUtil.DATETIME_FORMAT_DATE_YYYYMMDD);
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
        String sysDate = CCDateUtil.getSysDateFormat(CCDateUtil.DATETIME_FORMAT_DATE_YYYYMMDD);
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
        String sysDate = CCDateUtil.getSysDateFormat(CCDateUtil.DATETIME_FORMAT_DATE_YYYYMMDD);
        M017meimExample example = new M017meimExample();
        example.createCriteria().andCdKbnEqualTo("SIR").andCdEqualTo(sDcTcKbn);
        if (!CCStringUtil.isEmpty(sysDate)) {
            example.setSearchDate(sysDate);
        }
        List<M017meim> list = this.m017meimMapper.selectByExample(example);
        if (list != null && list.size() > 0 && !TYPE_ACT_KBN_DELETED.equals(list.get(0).getActKbn())) {
            sRet = list.get(0).getNm();
        } else {
            return sRet;
        }

        return sRet;
    }

    /**
     * Function getTenNm
     * @param tenCd tenCd
     * @param sysDate sysDate
     * @return String
     */
    private String getTenNm(String tenCd, String sysDate) {
        String tenNm = "";
        M006tenmExample example = new M006tenmExample();
        example.createCriteria().andTenCdEqualTo(tenCd);
        if (!CCStringUtil.isEmpty(sysDate)) {
            example.setSearchDate(sysDate);
        }
        List<M006tenm> list = this.m006tenmMapper.selectByExample(example);

        if (list == null || list.size() <= 0) {
            return tenNm;
        }
        String actKbn = (String) list.get(0).getActKbn();
        if (TYPE_ACT_KBN_DELETED.equals(actKbn)) {
            return tenNm;
        }
        tenNm = CCStringUtil.trimBoth(list.get(0).getTenNmA());
        return tenNm;
    }

    /**
     * Function getBmnNm
     * @param sysDate sysDate
     * @param bmnCd bmnCd
     * @return String
     */
    private String getBmnNm(String bmnCd, String sysDate) {
        String bmnNm = "";
        M003bmnmExample example = new M003bmnmExample();

        example.createCriteria().andBmnCdEqualTo(bmnCd);
        if (!CCStringUtil.isEmpty(sysDate)) {
            example.setSearchDate(sysDate);
        }

        List<M003bmnm> list = this.m003bmnmMapper.selectByExample(example);
        if (list == null || list.size() <= 0) {
            return bmnNm;
        }
        String actKbn = (String) list.get(0).getActKbn();
        if (TYPE_ACT_KBN_DELETED.equals(actKbn)) {
            return bmnNm;
        }
        bmnNm = CCStringUtil.trimBoth(list.get(0).getBmnNmA());
        return bmnNm;
    }

    /**
     * 取引先情報を返します。<BR>
     * @param sTriCd     取引先コード
     * @param sKijunYmd      基準日
     * @return List<M011trim>
     **/
    private String getTriInfo(String sTriCd, String sKijunYmd) {
        String sRet = "";
        M011trimExample example = new M011trimExample();
        example.createCriteria().andTriCdEqualTo(sTriCd);
        if (!CCStringUtil.isEmpty(sKijunYmd)) {
            example.setSearchDate(sKijunYmd);
        }
        List<M011trim> list = this.m011trimMapper.selectByExample(example);
        if (list == null || list.size() <= 0) {
            return sRet;
        }
        String actKbn = (String) list.get(0).getActKbn();
        if (TYPE_ACT_KBN_DELETED.equals(actKbn)) {
            return sRet;
        }
        sRet = CCStringUtil.trimBoth(list.get(0).getTriNmA());
        return sRet;
    }

    /*****************************************************************
     * 取引先マスタからの取引先名称取得処理.
     * @param sDate 基準日付
     * @param sToriCode 取引先コード（９桁未満の場合、後３桁を関数内で付加する）
     * @return 取引先名称
     *****************************************************************/
    private String getToriName(String sDate, String sToriCode) {
        String sRet = "";
        if (sToriCode == null || sToriCode.trim().length() == 0) {
            return sRet;
        }
        if (sDate == null || sDate.trim().length() == 0) {
            return sRet;
        }

        // レコード取得
        M011trimExample example = new M011trimExample();
        example.createCriteria().andTriCdEqualTo(sToriCode);
        if (!CCStringUtil.isEmpty(sDate)) {
            example.setSearchDate(sDate);
        }
        List<M011trim> list = this.m011trimMapper.selectByExample(example);

        // 取得レコード数が「０」の場合、処理を終了
        if (list == null || list.size() <= 0) {
            return sRet;
        }
        String actKbn = (String) list.get(0).getActKbn();
        if (TYPE_ACT_KBN_DELETED.equals(actKbn)) {
            return sRet;
        }
        // 共通部品より取引先名称を取得
        sRet = CCStringUtil.trimBoth(list.get(0).getTriNm());
        return sRet;

    }

    /*****************************************************************
     *入力データの整合性チェック処理
     * <p>
     * 機能概要：<br>
     * 入力データの整合性をチェックします。
     * <p> 
     * 作成日：<br> 
     * 新規作成<br>
     * <p> 
     * @param req Sijp1140CondForm
     * @return true：処理成功、false：処理失敗
     *****************************************************************/
    private boolean chkInputData(Sijp1140CondForm req) {

        boolean bRet = true;

        boolean blErrflg = false;
        // 運用日
        String sUnyoDate = context.getUnyoDate();
        // 共通Beanオブジェクト作成

        // 会社
        String sKaisya = req.getKaisyaCd();
        if (!CCStringUtil.isEmpty(sKaisya)) {

            String sKaisyaNm = this.getKaisyaNameA(sKaisya);
            if (sKaisyaNm.trim().length() == 0) {
                blErrflg = true;
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
                blErrflg = true;
                errRes.addErrorInfo("jigyoubuCd", CCMessageConst.MSG_KEY_ITEM_INPUT_IS_ERROR);
            } else {
                String sJigyobuNm = this.getJigyobuNameA(sKaisya, sJigyobu);
                if (sJigyobuNm.trim().length() == 0) {
                    errRes.addErrorInfo("jigyoubuCd", CCMessageConst.MSG_KEY_RESULT_NOT_EXISTS);
                    blErrflg = true;
                }
            }
        }

        // エラー取得時出力
        if (blErrflg) {
            bRet = false;
        }
        blErrflg = false;

        // 確定区分
        String sDenKbn = req.getDpyKbn();

        if (!CCStringUtil.isEmpty(sDenKbn)) {
            if (!CCNumberUtil.isNumeric(sDenKbn)) {
                blErrflg = true;
                errRes.addErrorInfo("dpyKbn", CCMessageConst.MSG_KEY_ITEM_INPUT_IS_ERROR);
            } else {
                if (!CCSIConst.DEN_KBN.KBN_EOSSIR.equals(sDenKbn)) {
                    blErrflg = true;
                    errRes.addErrorInfo("dpyKbn", CCMessageConst.MSG_KEY_ITEM_INPUT_IS_ERROR);
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
                    lsTorihiki.add(tmp[i]);
                }
            }
            req.setLsTorihikiCd(lsTorihiki);
        }

        for (int i = 0; i < lsTenCd.size(); i++) {

            // 店舗
            String sTenpo = lsTenCd.get(i);
            if (!CCStringUtil.isEmpty(sTenpo)) {
                bInpFlg = true;
                if (!CCNumberUtil.isNumeric(sTenpo)) {
                    errRes.addErrorInfo("tenCd", CCMessageConst.MSG_KEY_ITEM_INPUT_IS_ERROR);
                    blErrflg = true;
                }
                String sTenNm = this.getTenNm(sKaisya + sJigyobu + sTenpo, sUnyoDate);
                if (sTenNm.trim().length() == 0) {
                    errRes.addErrorInfo("tenCd", CCMessageConst.MSG_KEY_RESULT_NOT_EXISTS);
                    blErrflg = true;
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
                bInpFlg = true;
                if (!CCNumberUtil.isNumeric(sBumon)) {
                    errRes.addErrorInfo("bmnCd", CCMessageConst.MSG_KEY_ITEM_INPUT_IS_ERROR);
                    blErrflg = true;
                } else {
                    // String sBmnNm = clSICom.getComBumonNameA( sJigyobu + sBumon ) ;
                    String sBmnNm = this.getBmnNm(sJigyobu + sBumon, sUnyoDate);
                    if (sBmnNm.trim().length() == 0) {
                        blErrflg = true;
                        errRes.addErrorInfo("bmnCd", CCMessageConst.MSG_KEY_RESULT_NOT_EXISTS);
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
            if (!CCStringUtil.isEmpty(sTorihiki)) {
                bInpFlg = true;
                if (!CCNumberUtil.isNumeric(sTorihiki)) {
                    errRes.addErrorInfo("torihikiCd", CCMessageConst.MSG_KEY_ITEM_INPUT_IS_ERROR);
                    blErrflg = true;
                } else {
                    // String sToriNm = clSICom.getComToriNameA( sTorihiki ) ;
                    String sToriNm = this.getTriInfo(sTorihiki, sUnyoDate);
                    if (sToriNm.trim().length() == 0) {
                        blErrflg = true;
                        errRes.addErrorInfo("torihikiCd", CCMessageConst.MSG_KEY_RESULT_NOT_EXISTS);
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
        String sNhnDateSt = req.getNhnYoteiYmdSt();
        if (!CCStringUtil.isEmpty(sNhnDateSt)) {
            bInpFlg = true;
            // 納品予定日の日付妥当性チェック
            if (!CCDateUtil.isDate(sNhnDateSt)) {
                errRes.addErrorInfo("nhnYoteiYmdSt", CCMessageConst.MSG_KEY_DATE_ERROR);
                return false;
            }
        }
        String sNhnDateEd = req.getNhnYoteiYmdEd();
        if (!CCStringUtil.isEmpty(sNhnDateEd)) {
            bInpFlg = true;
            // 納品予定日の日付妥当性チェック
            if (!CCDateUtil.isDate(sNhnDateEd)) {
                errRes.addErrorInfo("nhnYoteiYmdEd", CCMessageConst.MSG_KEY_DATE_ERROR);
                return false;
            }
        }

        // エラー取得時出力
        if (blErrflg) {
            bRet = false;
        }
        blErrflg = false;

        // 納品予定日
        String hatYmdSt = req.getHatYmdSt();
        if (!CCStringUtil.isEmpty(hatYmdSt)) {
            bInpFlg = true;
            // 納品予定日の日付妥当性チェック
            if (!CCDateUtil.isDate(hatYmdSt)) {
                errRes.addErrorInfo("hatYmdSt", CCMessageConst.MSG_KEY_DATE_ERROR);
                return false;
            }
        }
        String hatYmdEd = req.getHatYmdEd();
        if (!CCStringUtil.isEmpty(hatYmdEd)) {
            bInpFlg = true;
            // 納品予定日の日付妥当性チェック
            if (!CCDateUtil.isDate(hatYmdEd)) {
                errRes.addErrorInfo("hatYmdEd", CCMessageConst.MSG_KEY_DATE_ERROR);
                return false;
            }
        }

        // エラー取得時出力
        if (blErrflg) {
            bRet = false;
        }
        blErrflg = false;

        // 入力有無フラグがFalseの場合
        if (!bInpFlg) {
            blErrflg = true;
            errRes.addErrorInfo("tenCd", CCMessageConst.MSG_KEY_NOT_KIP_ITEM);

        }

        // エラー取得時出力
        if (blErrflg) {
            bRet = false;
        }

        // return true ;
        return bRet;

    }

    /**
     * ＣＳＶファイルを作成します。<BR>
     * @param req Sijp1020CondForm
     * @param csvBean List<Sipr1020Report>
     * @return 　True:OK　　False:NG
    **/
    private boolean doCreateCsv(Sijp1140CondForm req, List<Sipr1140Report> csvBean) {

        String ymd = CCDateUtil.getSysDateFormat(CCDateUtil.DATETIME_FORMAT_DATE_YYYYMMDD);
        List<S001eosds033seiDataResult> lsResult = new ArrayList<S001eosds033seiDataResult>();
        Sipr1140Report dto2 = new Sipr1140Report();
        Sijp1140Dto param = new Sijp1140Dto();
        param.setKaisyaCd(req.getKaisyaCd());
        param.setJigyobuCd(req.getJigyoubuCd());
        param.setDenKbn(req.getDpyKbn());
        String tenSql = "";
        if (req.getLsTenCd() != null && req.getLsTenCd().size() > 0) {
            for (int i = 0; i < req.getLsTenCd().size(); i++) {
                if (CCStringUtil.isEmpty(tenSql)) {
                    tenSql = req.getLsTenCd().get(i);
                } else {
                    tenSql += "'" + "," + "'" + req.getLsTenCd().get(i);
                }
            }
        }
        if (req.getLsTenCd() != null && req.getLsTenCd().size() > 0) {
            param.setLsTenCds(req.getLsTenCd());
        } else {
            param.setLsTenCds(null);
        }
        String bmnSql = "";
        if (req.getLsBmnCd() != null && req.getLsBmnCd().size() > 0) {
            for (int i = 0; i < req.getLsBmnCd().size(); i++) {
                if (!CCStringUtil.isEmpty(req.getLsBmnCd().get(i))) {
                    bmnSql = req.getJigyoubuCd() + req.getLsBmnCd().get(i);
                    req.getLsBmnCd().set(i, bmnSql);

                }
            }
        }
        if (req.getLsBmnCd() != null && req.getLsBmnCd().size() > 0) {
            param.setLsBmnCds(req.getLsBmnCd());
        } else {
            param.setLsBmnCds(null);
        }

        if (req.getLsTorihikiCd() != null && req.getLsTorihikiCd().size() > 0) {
            param.setLsTorisakis(req.getLsTorihikiCd());
        } else {
            param.setLsTorisakis(null);
        }
        param.setNhnDateSt(req.getNhnYoteiYmdSt());
        param.setNhnDateEd(req.getNhnYoteiYmdEd());
        param.setHatDateSt(req.getHatYmdSt());
        param.setHatDateEd(req.getHatYmdEd());
        param.setYmd(ymd);
        List<S001eosds033seiDataResult> ls =
                dao.selectMany("selectS001eosds033seidSijp1140data", param, S001eosds033seiDataResult.class);
        // データなしの場合メッセージを表示してFalseを返す
        if (ls.size() == 0) {
            errRes.addErrorInfo(CCMessageConst.MSG_KEY_NO_FOUND_DATA);
            return false;
        }
        lsResult = setPageCngInfo(ls);
        this.setCSVHeaderData(req, dto2);

        // ***** 明細データの読み込み & 編集 *****
        for (int i = 0; i < lsResult.size(); i++) {
            Sipr1140Report dto = new Sipr1140Report();
            BeanUtils.copyProperties(dto2, dto);
            S001eosds033seiDataResult data = lsResult.get(i);

            // 納品予定日
            String sNhnDate = data.getNhnYoteiYmd();

            // 経過週の算出　(システム日付-納品予定日)/7
            if (sNhnDate != null && sNhnDate.length() != 0) {
                String sWeek = this.getWeek(sNhnDate);
                dto.h01_week_su = sWeek;
            }

            // 伝票区分名称
            String sDenKbnName = "伝区：" + req.getDpyKbn() + " " + req.getDpyKbnNm();
            dto.h01_denkbn_name = sDenKbnName;
            // 発行日
            dto.m01_hat_date = data.getHatYmd();

            // 伝票番号
            dto.m01_denno = data.getDpyNo();

            // 取引先ｺｰﾄﾞ
            String sToriCode = data.getTorihikiCd();
            dto.m01_sir_cd = sToriCode;

            // 取引先名
            String sToriName = this.getToriName(sNhnDate, sToriCode);
            dto.m01_sir_name = sToriName;

            // 店ｺｰﾄﾞ
            dto.m01_ten_cd = data.getTenCd();

            // 部門ｺｰﾄﾞ
            dto.m01_bmn_cd = data.getBmnCd();

            // 納品予定日
            dto.m01_nhn_ydate = sNhnDate;

            // 改頁条件
            dto.m01_page_cng = data.getKasanKbn();
            csvBean.add(dto);
        }
        if (csvBean.isEmpty()) {
            // 対象データ０件
            errRes.addErrorInfo(CCMessageConst.MSG_KEY_NO_FOUND_DATA);
            return false;
        }

        return true;

    }

    /*****************************************************************
     * 経過週取得処理.
     * <p>
     * 機能概要：<br>
     *　経過週を取得します。
     * <p> 
     * 作成日：<br> 
     * 新規作成<br>
     * <p> 
     * @param sDate String
     * @return 経過週
     *****************************************************************/
    private String getWeek(String sDate) {

        // 引数チェック
        if (sDate == null || sDate.trim().length() == 0) {
            return "";
        }
        if (!CCDateUtil.isDate(sDate)) {
            return "";
        }
        long lngWeek = CCDateUtil.getDateBetween(sDate) * -1;
        lngWeek = (long) lngWeek / 7;

        String sWeek = CCStringUtil.suppZero(String.valueOf(lngWeek), 3);

        return sWeek;

    }

    /*****************************************************************
     * 帳票出力ヘッダ部の設定（会社/事業部可変）.
     * <p>
     * 機能概要：<br>
     *　帳票出力のヘッダ部（共通部分）を設定する。<br>
     *　会社/事業部コードを渡すと、会社/事業部名称をセットする。<br>
     * <p> 
     * 作成日：<br>
     * 新規作成<br>
     * <p> 
     * @param req Sijp1140CondForm
     * @param dto Sipr1140Report
     * @return true：処理成功、false：処理失敗
     *****************************************************************/
    private boolean setCSVHeaderData(Sijp1140CondForm req, Sipr1140Report dto) {
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
     * 改頁条件設定処理
     * <p>
     * 機能概要：<br>
     * SVFの改頁条件を設定します。
     * <p> 
     * 作成日：<br> 
     * 新規作成<br>
     * <p> 
     * @param  lstSirData List<S001eosds033seiDataResult>
     * @return ArrayList 編集後のデータ配列
     *****************************************************************/
    private List<S001eosds033seiDataResult> setPageCngInfo(List<S001eosds033seiDataResult> lstSirData) {

        // データ編集用のバッファを初期化する
        List<S001eosds033seiDataResult> lstRetData = new ArrayList<S001eosds033seiDataResult>();

        // カウンタの初期化を行う
        int iRowCnt = 0; // 行カウント
        int iPageCngCnt = 0; // 改頁用カウント
        // コピー元データをバッファにコピーする
        S001eosds033seiDataResult sExecuteData = lstSirData.get(0);
        // 改頁条件用カラムを設定する
        sExecuteData.setKasanKbn(String.valueOf(iPageCngCnt));

        // 返却リストへ値をコピー
        lstRetData.add(sExecuteData);

        // 改頁条件を調整する
        for (int i = 1; i < lstSirData.size(); i++) {

            // 行カウントをインクリメント
            iRowCnt++;

            // 改頁用データをバッファにコピーする
            S001eosds033seiDataResult sExecuteDataBuf = new S001eosds033seiDataResult();
            BeanUtils.copyProperties(sExecuteData, sExecuteDataBuf);

            // コピー元データをバッファにコピーする
            sExecuteData = new S001eosds033seiDataResult();
            sExecuteData = lstSirData.get(i);

            // 納品予定日をより経過週を取得
            String sWeek = this.getWeek(sExecuteData.getNhnYoteiYmd());
            String sWeekBuf = this.getWeek(sExecuteDataBuf.getNhnYoteiYmd());

            // 経過週が変わった場合、改頁用のダミー行を挿入する
            if (!sWeek.equals(sWeekBuf)) {

                // 偶数ページの場合のみ改行を挿入する
                // 　※最終行の合計行３行はSVFで自動調整される為、計算に含めない）
                // ページカウントをインクリメント
                iPageCngCnt++;
                // 行カウントをクリアする
                iRowCnt = 0;

            }

            // 改頁条件用カラムを設定する
            sExecuteData.setKasanKbn(String.valueOf(iPageCngCnt));

            // 返却リストへ値をコピー
            lstRetData.add(sExecuteData);

            sExecuteDataBuf = new S001eosds033seiDataResult();
        }

        return lstRetData;

    }

}
