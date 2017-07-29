///////////////////////////////////////////////////////////////////////
// Copyright(C) 2015 NEC Solution Innovators, Ltd. All Rights Reserved.
///////////////////////////////////////////////////////////////////////

/* ====================================================================
 * 機能名称 : 企画情報
 * 改版履歴
 * Rev. 改版年月日   改版者名       内容
 * 1.0   2014.04.29   VuQQ      新規作成
 * =================================================================== */

package jp.co.necsoft.web_md.core.app.controllers.tk;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;

import jp.co.necsoft.web_md.common.ChaseException;
import jp.co.necsoft.web_md.common.mybatis.dao.MyBatisSqlMapDao;
import jp.co.necsoft.web_md.core.app.dto.tk.TKJP1060SelectResult;
import jp.co.necsoft.web_md.core.app.file.report.Tkpr1060Report;
import jp.co.necsoft.web_md.core.app.forms.tk.Tkjp1060CondForm;
import jp.co.necsoft.web_md.core.app.forms.tk.Tkjp1060ResultForm;
import jp.co.necsoft.web_md.core.common.CCMessageConst;
import jp.co.necsoft.web_md.core.common.CCSecurityAuthenticator;
import jp.co.necsoft.web_md.core.common.CCSystemContext;
import jp.co.necsoft.web_md.core.common.biz.kk.CCKKCommon;
import jp.co.necsoft.web_md.core.common.forms.ErrorInfo;
import jp.co.necsoft.web_md.core.common.forms.ErrorResponse;
import jp.co.necsoft.web_md.core.common.mybatis.dao.M000kaimMapper;
import jp.co.necsoft.web_md.core.common.mybatis.dao.M001jgymMapper;
import jp.co.necsoft.web_md.core.common.mybatis.dao.M003bmnmMapper;
import jp.co.necsoft.web_md.core.common.mybatis.dao.M006tenmMapper;
import jp.co.necsoft.web_md.core.common.mybatis.dao.M016tanmMapper;
import jp.co.necsoft.web_md.core.common.mybatis.dao.M017meimMapper;
import jp.co.necsoft.web_md.core.common.mybatis.dto.M000kaim;
import jp.co.necsoft.web_md.core.common.mybatis.dto.M000kaimExample;
import jp.co.necsoft.web_md.core.common.mybatis.dto.M001jgym;
import jp.co.necsoft.web_md.core.common.mybatis.dto.M001jgymExample;
import jp.co.necsoft.web_md.core.common.mybatis.dto.M003bmnm;
import jp.co.necsoft.web_md.core.common.mybatis.dto.M003bmnmExample;
import jp.co.necsoft.web_md.core.common.mybatis.dto.M006tenm;
import jp.co.necsoft.web_md.core.common.mybatis.dto.M006tenmExample;
import jp.co.necsoft.web_md.core.common.mybatis.dto.M016tanm;
import jp.co.necsoft.web_md.core.common.mybatis.dto.M016tanmExample;
import jp.co.necsoft.web_md.core.common.mybatis.dto.M017meim;
import jp.co.necsoft.web_md.core.common.mybatis.dto.M017meimExample;
import jp.co.necsoft.web_md.core.common.utils.CCDateUtil;
import jp.co.necsoft.web_md.core.common.utils.CCReportUtil;
import jp.co.necsoft.web_md.core.common.utils.CCStringUtil;
import play.data.Form;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;

import com.orangesignal.csv.manager.CsvManager;
import com.orangesignal.csv.manager.CsvManagerFactory;

/**
 * 企画情報のControllerクラス
 * 
 */
@Security.Authenticated(CCSecurityAuthenticator.class)
public class Tkjp1060Ctrl extends Controller {

    /** PROGRAM_ID */
    private static final String PROGRAM_ID = "TKJP1060";
    /** PROGRAM_VERSION */
    private static final String PROGRAM_VERSION = "1.00";

    /** m000kaimMapper Object */
    @Inject
    private M000kaimMapper m000kaimMapper;
    /** M001jgymMapper Object */
    @Inject
    private M001jgymMapper m001jgymMapper;
    /** M003bmnmMapper Object */
    @Inject
    private M003bmnmMapper m003bmnmMapper;
    /** M006tenmMapper Object */
    @Inject
    private M006tenmMapper m006tenmMapper;
    /** M016tanmのマッパー */
    @Inject
    private M016tanmMapper m016tanmMapper;
    /** M017meimのマッパー */
    @Inject
    private M017meimMapper m017meimMapper;
    /** MyBatisSqlMapDao Object */
    @Inject
    private MyBatisSqlMapDao dao;
    /** ErrorResponse Object */
    @Inject
    private ErrorResponse errRes;
    /** CCSystemContext Object */
    @Inject
    private CCSystemContext context;
    /**
     * 削除
     */
    private static final String TYPE_ACT_KBN_DELETED = "9";

    /**
     * 初期値を設定する
     * @param sId functionのId
     * @return CondFormクラス 
     */
    public Result initCond(String sId) {
        Tkjp1060CondForm tkjp1060CondForm = new Tkjp1060CondForm();

        M016tanmExample example = new M016tanmExample();
        example.setSearchDate(context.getUnyoDate());
        example.createCriteria().andTantoCdEqualTo(context.getTantoCode());
        List<M016tanm> listM016tanms = this.m016tanmMapper.selectByExample(example);
        if (listM016tanms != null && listM016tanms.size() != 0) {
            if (!TYPE_ACT_KBN_DELETED.equals(listM016tanms.get(0).getActKbn())) {
                String tenCd = listM016tanms.get(0).getTenCd();
                tkjp1060CondForm.setKaisyaCd(tenCd.substring(0, 2));
                tkjp1060CondForm.setJigyobuCd(tenCd.substring(2, 4));
            }
        }

        return ok(Json.toJson(tkjp1060CondForm));
    }

    /**
     * 引数とリクエスト(パラメータ)を条件にDBからデータ(複数件)を検索し返却する。*
     * 
     * @param kaisyaCd  会社コード
     * @param jigyobuCd 事業部コード
     * @return クライアントへ返却する結果
     */
    public Result query(String kaisyaCd, String jigyobuCd) {
        List<Tkjp1060ResultForm> resultList = new ArrayList<Tkjp1060ResultForm>();
        Tkjp1060ResultForm resultForm = new Tkjp1060ResultForm();
        @SuppressWarnings({"unchecked", "rawtypes" })
        Form<Tkjp1060CondForm> emptyForm = new Form(Tkjp1060CondForm.class);
        Form<Tkjp1060CondForm> reqForm = emptyForm.bindFromRequest();
        CCReportUtil cru = new CCReportUtil("TKSV1060", context);
        CsvManager csvManager = CsvManagerFactory.newCsvManager();
        List<Tkpr1060Report> csvBean = new ArrayList<Tkpr1060Report>();
        Tkjp1060CondForm req;

        String nendoSt = "";
        String kikakuCdSt = "";
        String bmnCdSt = "";
        String nendoEd = "";
        String kikakuCdEd = "";
        String bmnCdEd = "";
        String kakuteDaySt = "";
        String kakuteDayEd = "";
        String tantoCd = context.getTantoCode();

        // キーブレイク判定領域初期化
        String sKaisyaCdOld = "";
        String sJigyobuCdOld = "";
        String sBmnCdOld = "";
        String sTenCdOld = "";
        String ssKikakuSyuOld = "";
        String sHanbaiFrddOld = "";
        // 名称取得領域初期化
        String sKaisyaNm = "";
        String sJigyobuNm = "";
        String sBmnNm = "";
        String sTenNm = "";
        String sKikakuSyuNm = "";
        String sTirasiNm = "";

        if (reqForm.hasErrors()) {
            errRes.addErrorInfo(CCMessageConst.MSG_KEY_VALIDATION_ERROR);
            errRes.addErrorInfo(reqForm.errors());
            return badRequest(Json.toJson(errRes));
        } else {
            req = reqForm.get();
        }

        if (req.getNendoSt() != null) {
            nendoSt = req.getNendoSt();
        }
        if (req.getkikakuCdSt() != null) {
            kikakuCdSt = req.getkikakuCdSt();
        }
        if (req.getBmnCdSt() != null) {
            bmnCdSt = req.getBmnCdSt();
        }
        if (req.getKakuteDaySt() != null) {
            kakuteDaySt = req.getKakuteDaySt();
        }
        if (req.getNendoEd() != null) {
            nendoEd = req.getNendoEd();
        }
        if (req.getKikakuCdEd() != null) {
            kikakuCdEd = req.getKikakuCdEd();
        }
        if (req.getBmnCdEd() != null) {
            bmnCdEd = req.getBmnCdEd();
        }
        if (req.getKakuteDayEd() != null) {
            kakuteDayEd = req.getKakuteDayEd();
        }
        // 企画コードTO設定
        if (CCStringUtil.isEmpty(nendoEd) || CCStringUtil.isEmpty(kikakuCdEd) || CCStringUtil.isEmpty(bmnCdEd)) {
            nendoEd = nendoSt;
            kikakuCdEd = kikakuCdSt;
            bmnCdEd = bmnCdSt;
        }
        // 確定日TO設定
        if (CCStringUtil.isEmpty(kakuteDayEd)) {
            kakuteDayEd = kakuteDaySt;
        }
        if (!doCheckData(kaisyaCd, jigyobuCd, nendoSt, kikakuCdSt, bmnCdSt, nendoEd, kikakuCdEd, bmnCdEd,
                kakuteDaySt, kakuteDayEd)) {
            return badRequest(Json.toJson(errRes));
        }

        // 入力された条件に該当するを取得する
        Map<String, String> hashMap = new HashMap<String, String>();
        hashMap.put("kaisyaCd", kaisyaCd);
        hashMap.put("jigyobuCd", jigyobuCd);
        hashMap.put("nendoSt", nendoSt);
        hashMap.put("nendoEd", nendoEd);
        hashMap.put("kikakuCdSt", kikakuCdSt);
        hashMap.put("kikakuCdEd", kikakuCdEd);
        hashMap.put("nendoSt_kikakuCdSt_jigyobuCd_bmnCdSt", nendoSt + kikakuCdSt + jigyobuCd + bmnCdSt);
        hashMap.put("nendoEd_kikakuCdEd_jigyobuCd_bmnCdEd", nendoEd + kikakuCdEd + jigyobuCd + bmnCdEd);
        hashMap.put("kakuteiDaySt", kakuteDaySt);
        hashMap.put("kakuteiDayEd", kakuteDayEd);

        List<TKJP1060SelectResult> listTKJP1060SelectResult =
                dao.selectMany("selectTkjp1060T000T008T009", hashMap, TKJP1060SelectResult.class);
        int iResult = listTKJP1060SelectResult.size();
        if (iResult == 0) {
            errRes.addErrorInfo(CCMessageConst.MSG_KEY_NO_FOUND_DATA);
            return notFound(Json.toJson(errRes));
        }

        for (int i = 0; i < iResult; i++) {
            Tkpr1060Report objTkpr1060Report = new Tkpr1060Report();

            // ＣＳＶ出力用データ取得
            String sKaisyaCd = listTKJP1060SelectResult.get(i).getKaisyaCd();
            String sJigyobuCd = listTKJP1060SelectResult.get(i).getJigyobuCd();
            String sBmnCd = listTKJP1060SelectResult.get(i).getBmnCd();
            String sTenCd = listTKJP1060SelectResult.get(i).getTenCd();
            String sKikakuSyu = listTKJP1060SelectResult.get(i).getKikakuSyu();
            String sHanbaiFrdd = listTKJP1060SelectResult.get(i).getHanbaiFrdd();
            String sTirasiKbn = listTKJP1060SelectResult.get(i).getTirasikbn();

            // 会社名称取得
            if (!sKaisyaCdOld.equals(sKaisyaCd) || !sHanbaiFrddOld.equals(sHanbaiFrdd)) {
                sKaisyaNm = getKaisyaNm(kaisyaCd, sHanbaiFrdd);
            }

            // 事業部名称取得
            if (!sJigyobuCdOld.equals(sJigyobuCd) || !sKaisyaCdOld.equals(sKaisyaCd)
                    || !sHanbaiFrddOld.equals(sHanbaiFrdd)) {

                sJigyobuNm = getJigyouNm(sKaisyaCd, sJigyobuCd, sHanbaiFrdd);
            }

            // 部門名称取得
            if (!sBmnCdOld.equals(sBmnCd) || !sHanbaiFrddOld.equals(sHanbaiFrdd)) {
                sBmnNm = getBmnNm(sBmnCd, sHanbaiFrdd);
            }

            // 店舗名称取得
            if (!sTenCdOld.equals(sTenCd) || !sHanbaiFrddOld.equals(sHanbaiFrdd)) {
                sTenNm = getTenNm(sTenCd, sHanbaiFrdd);
            }

            if (!ssKikakuSyuOld.equals(sKikakuSyu) || !sHanbaiFrddOld.equals(sHanbaiFrdd)) {
                sKikakuSyuNm = getKikakuSyuNm(sKikakuSyu, sHanbaiFrdd);
            }

            if (sTirasiKbn.equals("1")) {
                sTirasiNm = "有り";
            } else if (sTirasiKbn.equals("2")) {
                sTirasiNm = "無し";
            } else {
                sTirasiNm = "";
            }

            // キーブレイク判定領域退避
            sKaisyaCdOld = sKaisyaCd;
            sJigyobuCdOld = sJigyobuCd;
            sBmnCdOld = sBmnCd;
            ssKikakuSyuOld = sKikakuSyu;
            sHanbaiFrddOld = sHanbaiFrdd;

            // プログラムＩＤ
            objTkpr1060Report.h00_progid = PROGRAM_ID;
            // バージョン
            objTkpr1060Report.h00_version = PROGRAM_VERSION;
            // 担当コード
            objTkpr1060Report.h01_tanto_code = tantoCd;
            // 会社コード
            objTkpr1060Report.h01_kaisya_cd = sKaisyaCd;
            // 会社名
            objTkpr1060Report.h01_kaisya_nm = sKaisyaNm;
            // 事業部コード
            objTkpr1060Report.h01_jigyobu_cd = sJigyobuCd;
            // 事業部名
            objTkpr1060Report.h01_jigyobu_nm = sJigyobuNm;
            // 部門コード
            objTkpr1060Report.h01_bmn_cd = sBmnCd.substring(2);
            // 部門名
            objTkpr1060Report.h01_bmn_nm = sBmnNm;

            // 企画コード
            objTkpr1060Report.h01_kikaku_cd = listTKJP1060SelectResult.get(i).getKikakuCd();
            // 企画名
            objTkpr1060Report.h01_kikaku_nm = listTKJP1060SelectResult.get(i).getKikakuNm();
            // 企画種類
            objTkpr1060Report.h01_kikaku_syu = listTKJP1060SelectResult.get(i).getKikakuSyu();
            // 企画名
            objTkpr1060Report.h01_kikaku_syu_nm = sKikakuSyuNm;
            // 年度
            objTkpr1060Report.h01_nendo = listTKJP1060SelectResult.get(i).getNenDo();
            // 特記事項
            objTkpr1060Report.h01_tokkijiko = listTKJP1060SelectResult.get(i).getTokkijiko();
            // 販売期間（開始日）
            objTkpr1060Report.h01_hanbai_frdd =
                    CCDateUtil.getDateFormat(listTKJP1060SelectResult.get(i).getHanbaiFrdd(), "yyyy/MM/dd");
            // 販売期間（終了日）
            objTkpr1060Report.h01_hanbai_todd =
                    CCDateUtil.getDateFormat(listTKJP1060SelectResult.get(i).getHanbaiTodd(), "yyyy/MM/dd");
            // 仕入期間（開始日）
            objTkpr1060Report.h01_siire_frdd =
                    CCDateUtil.getDateFormat(listTKJP1060SelectResult.get(i).getSiireFrdd(), "yyyy/MM/dd");
            // 仕入期間（終了日）
            objTkpr1060Report.h01_siire_todd =
                    CCDateUtil.getDateFormat(listTKJP1060SelectResult.get(i).getSiireTodd(), "yyyy/MM/dd");
            // チラシ区分
            objTkpr1060Report.h01_tirasi_kbn = sTirasiNm;
            // 店舗確定日
            objTkpr1060Report.h01_kakutei_day =
                    CCDateUtil.getDateFormat(listTKJP1060SelectResult.get(i).getKakuteiDay(), "yyyy/MM/dd");
            // 販促提出期限日
            objTkpr1060Report.h01_hansoku_day =
                    CCDateUtil.getDateFormat(listTKJP1060SelectResult.get(i).getHansokuDay(), "yyyy/MM/dd");
            // 予算金額（企画全体）
            objTkpr1060Report.h01_uri_yosan = listTKJP1060SelectResult.get(i).getUriyosan01();
            // 荒利予算（企画全体）
            objTkpr1060Report.h01_ara_yosan = listTKJP1060SelectResult.get(i).getArayosan01();

            // 店舗コード
            String subTenCd = StringUtils.substring(sTenCd, 4);
            objTkpr1060Report.m01_ten_cd = subTenCd;
            // 店舗名
            objTkpr1060Report.m01_ten_nm = sTenNm;
            // 予算金額（店舗別）
            objTkpr1060Report.m01_uri_yosan = listTKJP1060SelectResult.get(i).getUriyosan02();
            // 荒利予算（店舗別）
            objTkpr1060Report.m01_ara_yosan = listTKJP1060SelectResult.get(i).getArayosan02();
            // 売上金額
            objTkpr1060Report.m01_uri_baik_kin = listTKJP1060SelectResult.get(i).getUriBaikKin();
            // 売上返品金額
            objTkpr1060Report.m01_sir_genk_kin = listTKJP1060SelectResult.get(i).getSirGenkKin();

            // 特売前在庫原価金額
            objTkpr1060Report.m01_mae_zik_genk_kin = listTKJP1060SelectResult.get(i).getMaeZikGenkKin();
            // 特売後在庫原価金額
            objTkpr1060Report.m01_ato_zik_genk_kin = listTKJP1060SelectResult.get(i).getAtoZikGenkKin();
            // 特売前在庫売価金額
            objTkpr1060Report.m01_mae_zik_baik_kin = listTKJP1060SelectResult.get(i).getMaeZikBaikKin();
            // 特売後在庫売価金額
            objTkpr1060Report.m01_ato_zik_baik_kin = listTKJP1060SelectResult.get(i).getAtoZikBaikKin();
            // 売上返品金額
            objTkpr1060Report.m01_uri_hpn_kin = listTKJP1060SelectResult.get(i).getUriHpnKin();
            // 値上金額
            objTkpr1060Report.m01_neag_kin = listTKJP1060SelectResult.get(i).getNeagKin();
            // 値下金額
            objTkpr1060Report.m01_nesg_kin = listTKJP1060SelectResult.get(i).getNesgKin();

            csvBean.add(objTkpr1060Report);
        }

        // ＣＳＶ作成（データ出力）
        try {
            csvManager.save(csvBean, Tkpr1060Report.class).to(new File(cru.getCsvFilePath()),
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

        resultList.add(resultForm);

        // gc対応
        cru = null;
        System.gc();

        return ok(Json.toJson(resultList));
    }

    /**
     * データのエラーチェック処理
     * 
     * @param kaisyaCd  会社コード
     * @param jigyobuCd 事業部コード
     * @param nendoSt 年度
     * @param kikakuCdSt 企画コード
     * @param bmnCdSt 部門コード
     * @param nendoEd 年度
     * @param kikakuCdEd 企画コード
     * @param bmnCdEd 部門コード
     * @param kakuteDaySt 確定日
     * @param kakuteDayEd 確定日
     * @return boolean true/false
     */
    private boolean doCheckData(String kaisyaCd, String jigyobuCd, String nendoSt, String kikakuCdSt,
            String bmnCdSt, String nendoEd, String kikakuCdEd, String bmnCdEd, String kakuteDaySt, String kakuteDayEd) {

        ErrorInfo error;
        error = null;
        String unyoDate = context.getUnyoDate();
        if (CCStringUtil.isEmpty(unyoDate)) {
            errRes.addErrorInfo(CCMessageConst.MSG_KEY_UNYOUBI_NOT_REGISTERED);
            return false;
        }

        if ("".equals(getKaisyaNm(kaisyaCd, unyoDate))) {
            error = errRes.getErrorInfo(CCMessageConst.MSG_KEY_VALIDATION_ERROR);
            errRes.addErrorInfo("kaisyaCd", CCMessageConst.MSG_KEY_NOT_REGISTERED_NO_PARAM);
            errRes.getErrors().add(0, error);
            return false;
        }
 
        if ("".equals(getJigyouNm(kaisyaCd, jigyobuCd, unyoDate))) {
            error = errRes.getErrorInfo(CCMessageConst.MSG_KEY_VALIDATION_ERROR);
            errRes.addErrorInfo("jigyobuCd", CCMessageConst.MSG_KEY_NOT_REGISTERED_NO_PARAM);
            errRes.getErrors().add(0, error);
            return false;
        }

        // 企画コードと確定日の必須チェック
        if ("".equals(nendoSt) && "".equals(kikakuCdSt) && "".equals(bmnCdSt) && "".equals(kakuteDaySt)) {
            error = errRes.getErrorInfo(CCMessageConst.MSG_KEY_VALIDATION_ERROR);
            errRes.addErrorInfo("nendoSt", CCMessageConst.MSG_KEY_ENTER_ORDER_DATE_PLANNING_CODE);
            errRes.addErrorInfo("kikakuCdSt", CCMessageConst.MSG_KEY_ENTER_ORDER_DATE_PLANNING_CODE);
            errRes.addErrorInfo("bmnCdSt", CCMessageConst.MSG_KEY_ENTER_ORDER_DATE_PLANNING_CODE);
            errRes.addErrorInfo("kakuteDaySt", CCMessageConst.MSG_KEY_ENTER_ORDER_DATE_PLANNING_CODE);
            errRes.getErrors().add(0, error);
            return false;
        }

        // 企画コードチェック
        if (!"".equals(nendoSt) || !"".equals(kikakuCdSt) || !"".equals(bmnCdSt)) {
            if ("".equals(nendoSt)) {
                error = errRes.getErrorInfo(CCMessageConst.MSG_KEY_VALIDATION_ERROR);
                errRes.addErrorInfo("nendoSt", CCMessageConst.MSG_KEY_INPUT_REQUIRED_ENTER);
                errRes.getErrors().add(0, error);
                return false;
            }
            if ("".equals(kikakuCdSt)) {
                error = errRes.getErrorInfo(CCMessageConst.MSG_KEY_VALIDATION_ERROR);
                errRes.addErrorInfo("kikakuCdSt", CCMessageConst.MSG_KEY_INPUT_REQUIRED_ENTER);
                errRes.getErrors().add(0, error);
                return false;
            }

            if ("".equals(bmnCdSt)) {
                error = errRes.getErrorInfo(CCMessageConst.MSG_KEY_VALIDATION_ERROR);
                errRes.addErrorInfo("bmnCdSt", CCMessageConst.MSG_KEY_INPUT_REQUIRED_ENTER);
                errRes.getErrors().add(0, error);
                return false;
            }
        }

        // 確定日FROMチェック
        if (!"".equals(kakuteDaySt)) {
            if (!CCDateUtil.isDate(kakuteDaySt)) {
                error = errRes.getErrorInfo(CCMessageConst.MSG_KEY_VALIDATION_ERROR);
                errRes.addErrorInfo("kakuteDaySt", CCMessageConst.MSG_KEY_RE_ENTER_NO_PARAM);
                errRes.getErrors().add(0, error);
                return false;
            }
        }

        // 確定日TOチェック
        if (!"".equals(kakuteDayEd)) {
            if (!CCDateUtil.isDate(kakuteDayEd)) {
                error = errRes.getErrorInfo(CCMessageConst.MSG_KEY_VALIDATION_ERROR);
                errRes.addErrorInfo("kakuteDayEd", CCMessageConst.MSG_KEY_RE_ENTER_NO_PARAM);
                errRes.getErrors().add(0, error);
                return false;
            }
        }
        return true;
    }

    /**
     * 部門名を取得
     * 
     * @param bmnCd
     *            部門コード
     * @param unyoDate
     *            運用日
     * @return 部門名ト
     */
    private String getBmnNm(String bmnCd, String unyoDate) {
        M003bmnmExample m003bmnmExample = new M003bmnmExample();
        m003bmnmExample.createCriteria().andBmnCdEqualTo(bmnCd);
        m003bmnmExample.setSearchDate(unyoDate);
        List<M003bmnm> listM003bmnm = this.m003bmnmMapper.selectByExample(m003bmnmExample);
        if (listM003bmnm.size() > 0) {
            if (!TYPE_ACT_KBN_DELETED.equals(listM003bmnm.get(0).getActKbn())) {
                return listM003bmnm.get(0).getBmnNm();
            }
        }
        return "";
    }

    /**
     * 事業部名を取得
     * 
     * @param kaisyaCd
     *            会社コード
     * @param jigyobuCd
     *            事業部コード
     * @param unyoDate
     *            運用日
     * @return 事業部名
     */
    private String getJigyouNm(String kaisyaCd, String jigyobuCd, String unyoDate) {
        M001jgymExample m001jgymExample = new M001jgymExample();
        m001jgymExample.createCriteria().andKaisyaCdEqualTo(kaisyaCd).andJigyobuCdEqualTo(jigyobuCd);
        m001jgymExample.setSearchDate(unyoDate);
        List<M001jgym> listM001jgym = this.m001jgymMapper.selectByExample(m001jgymExample);
        if (listM001jgym.size() > 0) {
            if (!TYPE_ACT_KBN_DELETED.equals(listM001jgym.get(0).getActKbn())) {
                return listM001jgym.get(0).getJgyNm();
            }
        }
        return "";
    }

    /**
     * 会社名を取得
     * 
     * @param kaisyaCd
     *            会社コード
     * @param unyoDate
     *            運用日
     * @return 会社名
     */
    private String getKaisyaNm(String kaisyaCd, String unyoDate) {
        M000kaimExample m000kaimExample = new M000kaimExample();
        m000kaimExample.createCriteria().andKaisyaCdEqualTo(CCKKCommon.CON_KAISHA_CODE);
        m000kaimExample.setSearchDate(unyoDate);
        List<M000kaim> listM000kaim = this.m000kaimMapper.selectByExample(m000kaimExample);
        if (listM000kaim.size() > 0) {
            if (!TYPE_ACT_KBN_DELETED.equals(listM000kaim.get(0).getActKbn())) {
                return listM000kaim.get(0).getKaiNm();
            }
        }
        return "";
    }

    /**
     * 企画種類名を取得
     * 
     * @param kikakuSyu
     *            KikakuSyu
     * @param unyoDate
     *            運用日
     * @return 企画種類名
     */
    private String getKikakuSyuNm(String kikakuSyu, String unyoDate) {
        M017meimExample m017meimExample = new M017meimExample();
        m017meimExample.createCriteria().andCdKbnEqualTo("TOK").andCdEqualTo("KIKAKUSYU_" + kikakuSyu);
        m017meimExample.setSearchDate(unyoDate);
        List<M017meim> listM017meim = this.m017meimMapper.selectByExample(m017meimExample);
        if (listM017meim.size() > 0) {
            if (!TYPE_ACT_KBN_DELETED.equals(listM017meim.get(0).getActKbn())) {
                return listM017meim.get(0).getNm();
            }
        }
        return "";
    }

    /**
     * 部門名を取得
     * 
     * @param tenCd
     *            店舗コード
     * @param unyoDate
     *            運用日
     * @return 部門名
     */
    private String getTenNm(String tenCd, String unyoDate) {
        M006tenmExample m006tenmExample = new M006tenmExample();
        m006tenmExample.createCriteria().andTenCdEqualTo(tenCd);
        m006tenmExample.setSearchDate(unyoDate);
        List<M006tenm> listM006tenm = this.m006tenmMapper.selectByExample(m006tenmExample);
        if (listM006tenm.size() > 0) {
            if (!TYPE_ACT_KBN_DELETED.equals(listM006tenm.get(0).getActKbn())) {
                return listM006tenm.get(0).getTenNmR1();
            }
        }
        return "";
    }
}
