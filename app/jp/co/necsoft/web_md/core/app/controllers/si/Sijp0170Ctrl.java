// /////////////////////////////////////////////////////////////////////
// Copyright(C) 2015 NEC Solution Innovators, Ltd. All Rights Reserved.
// /////////////////////////////////////////////////////////////////////

/*
 * ====================================================================
 * 
 * 機能名称 : ＥＯＳ伝票完納入力
 * 
 * 改版履歴
 * 
 * Rev. 改版年月日 改版者名 内容
 * 
 * 1.0 2014-04-05 TUCTV 新規作成
 * 
 * ===================================================================
 */

package jp.co.necsoft.web_md.core.app.controllers.si;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import jp.co.necsoft.web_md.common.ChaseException;
import jp.co.necsoft.web_md.common.mybatis.dao.MyBatisSqlMapDao;
import jp.co.necsoft.web_md.core.app.dto.si.Sijp0170Execute;
import jp.co.necsoft.web_md.core.app.dto.si.Sijp0170ResultDto;
import jp.co.necsoft.web_md.core.app.forms.si.Sijp0170CondForm;
import jp.co.necsoft.web_md.core.app.forms.si.Sijp0170ResultForm;
import jp.co.necsoft.web_md.core.common.CCMessageConst;
import jp.co.necsoft.web_md.core.common.CCSecurityAuthenticator;
import jp.co.necsoft.web_md.core.common.CCSystemContext;
import jp.co.necsoft.web_md.core.common.biz.si.CCSICommon;
import jp.co.necsoft.web_md.core.common.biz.si.CCSIConst;
import jp.co.necsoft.web_md.core.common.forms.ErrorResponse;
import jp.co.necsoft.web_md.core.common.mybatis.dao.S001eosdMapper;
import jp.co.necsoft.web_md.core.common.mybatis.dao.S005eostMapper;
import jp.co.necsoft.web_md.core.common.mybatis.dao.S027eospMapper;
import jp.co.necsoft.web_md.core.common.mybatis.dto.S001eosd;
import jp.co.necsoft.web_md.core.common.mybatis.dto.S001eosdExample;
import jp.co.necsoft.web_md.core.common.mybatis.dto.S001eosdExample.Criteria;
import jp.co.necsoft.web_md.core.common.mybatis.dto.S005eost;
import jp.co.necsoft.web_md.core.common.mybatis.dto.S027eosp;
import jp.co.necsoft.web_md.core.common.utils.CCDateUtil;
import jp.co.necsoft.web_md.core.common.utils.CCNumberUtil;
import jp.co.necsoft.web_md.core.common.utils.CCStringUtil;

import org.mybatis.guice.transactional.Transactional;
import org.springframework.beans.BeanUtils;

import play.data.Form;
import play.i18n.Messages;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;

/**
*ＥＯＳ伝票完納入力のControllerクラス
*
*/
@Security.Authenticated(CCSecurityAuthenticator.class)
public class Sijp0170Ctrl extends Controller {
    /**
     * EMPTY_STRING
     **/
    private static final String EMPTY_STRING = "";

    /**
     * UNDEFINED
     **/
    private static final String UNDEFINED = "undefined";

    /** グリッド行数(15行×1)*/
    private static final int ROW_CNT = 15;

    /**グリッド行数(15行×2)**/
    private static final int SCD_CNT = 30;

    /**グリッド総行数(15行×4)**/
    private static final int ALL_CNT = 45;

    /**mybatisDao*/
    @Inject
    private MyBatisSqlMapDao mybatisDao;

    /**S001eosdMapper*/
    @Inject
    private S001eosdMapper s001eosdMapper;

    /**S005eostMapper*/
    @Inject
    private S005eostMapper s005eostMapper;

    /**S027eospMapper*/
    @Inject
    private S027eospMapper s027eospMapper;

    /**errRes*/
    @Inject
    private ErrorResponse errRes;

    /**CCSystemContext*/
    @Inject
    private CCSystemContext context;

    /**CCSICommonn*/
    @Inject
    private CCSICommon cmJSICommon;

    /**
     * 初期表示
     * @return クライアントへ返却する結果  
     */
    public Result init() {
        return ok();
    }

    /**
    * 引数とリクエスト(パラメータ)を条件にDBからデータ(複数件)を検索し返却する。
    * @param ruteCd 納品ルート
    * @param inpNohinDate 納品日
    * @return クライアントへ返却する結果
    */
    public Result query(String ruteCd, String inpNohinDate) {
        Form<Sijp0170CondForm> emptyForm = new Form<Sijp0170CondForm>(Sijp0170CondForm.class);
        Form<Sijp0170CondForm> reqForm = emptyForm.bindFromRequest();
        if (reqForm.hasErrors()) {
            errRes.addErrorInfo(CCMessageConst.MSG_KEY_VALIDATION_ERROR);
            errRes.addErrorInfo(reqForm.errors());
            return badRequest(Json.toJson(errRes));
        } else {
            Sijp0170CondForm req = reqForm.get();

            // doChkHead()
            boolean blErrflg = doChkHead(ruteCd, inpNohinDate, req.getKaisyaCd(), req.getJigyobuCd(), req.getTenCd());
            if (!blErrflg) {
                return badRequest(Json.toJson(errRes));
            }
            return ok();
        }
    }

    /** 引数とリクエスト(ボディ)からレコードを編集しDBに登録(論理)する。*
     * @param ruteCd 納品ルート 
     * @param inpNohinDate 納品日 
     * @return クライアントへ返却する結果
     */
    public Result save(String ruteCd, String inpNohinDate) {

        Form<Sijp0170ResultForm> emptyForm = new Form<Sijp0170ResultForm>(Sijp0170ResultForm.class);
        Form<Sijp0170ResultForm> reqForm = emptyForm.bindFromRequest();
        if (reqForm.hasErrors()) {
            errRes.addErrorInfo(CCMessageConst.MSG_KEY_VALIDATION_ERROR);
            errRes.addErrorInfo(reqForm.errors());
            return badRequest(Json.toJson(errRes));
        } else {
            Sijp0170ResultForm req = reqForm.get();

            List<Sijp0170ResultDto> lst1 = req.getMsaiInfArea1();
            List<Sijp0170ResultDto> lst2 = req.getMsaiInfArea2();
            List<Sijp0170ResultDto> lst3 = req.getMsaiInfArea3();

            // [1.入力チェック]を呼びます doChkHead()
            boolean blErrflg = doChkHead(ruteCd, inpNohinDate, req.getKaisyaCd(), req.getJigyobuCd(), req.getTenCd());
            if (!blErrflg) {
                return badRequest(Json.toJson(errRes));
            }

            // GRID = 明細情報１ + 明細情報2 + 明細情報3
            List<Sijp0170ResultDto> gridList = new ArrayList<Sijp0170ResultDto>();
            for (int i = 0; i < ROW_CNT; i++) {
                gridList.add(lst1.get(i));
            }
            for (int i = 0; i < ROW_CNT; i++) {
                gridList.add(lst2.get(i));
            }
            for (int i = 0; i < ROW_CNT; i++) {
                gridList.add(lst3.get(i));
            }
            // [2.GRIDのチェック]を呼びます
            boolean gridLlag = doChkGrid(gridList, inpNohinDate, req.getKaisyaCd(), req.getJigyobuCd(), req.getTenCd());
            if (!gridLlag) {
                return badRequest(Json.toJson(errRes));
            }

            // 更新の場合
            return executeUpdateLogic(gridList, ruteCd, inpNohinDate, req.getKaisyaCd(), req.getJigyobuCd(), req.getTenCd());
        }
    }

    /**
     * 引数とリクエスト(ボディ)からレコードを編集しDBの該当データを更新(論理)する。
     * @param gridList List<Sijp0170ResultDto>
     * @param sDpyNo 伝票番号
     * @param sNhnDate 納品日
     * @param sKaisya  会社コード
     * @param sJigyobu  事業部コード
     * @param sTen 店舗コード
     * @return クライアントへ返却する結 果
     */
    @Transactional
    public Result executeUpdateLogic(List<Sijp0170ResultDto> gridList, String sDpyNo, String sNhnDate,
            String sKaisya, String sJigyobu, String sTen) {
        // SEQ No 登録用変数
        int iSeqNo = 0;
        for (int i = 0; i < ALL_CNT; i++) {
            // 画面入力値取得
            String sDenNo = gridList.get(i).getDpyNo().trim();
            String sGenka = gridList.get(i).getsKenGenKin().trim();
            // GRIDの伝票NO != null AND　GRIDの原価合計金額 != null　の場合
            if (!EMPTY_STRING.equals(sDenNo) && !EMPTY_STRING.equals(sGenka)) {
                // SeqNoのインクリメント
                iSeqNo++;
                // TableEOS伝票累積ﾌｧｲﾙの更新
                // ＥＯＳ伝票累積Ｆデータ取得処理.
                // S001eosdMapperのselectByExampleで使用
                // 伝票累積[]① = S001EOSDの全項目のDPY_NOをセット
                S001eosd s001eosd = getDenpyoData(sDenNo, sKaisya, sJigyobu);

                // SEQ No
                String sSeqNo = String.valueOf(iSeqNo);
                sSeqNo = CCStringUtil.suppZero(sSeqNo, 6);

                // 累計計上日をセット
                // cmJSICommonnのgetKeijoDateを呼びます
                String sKeijoDate = "";
                if (sNhnDate.length() > 0) {
                    sKeijoDate = cmJSICommon.getKeijoDate(sNhnDate);
                } else {
                    sKeijoDate = cmJSICommon.getKeijoDate(s001eosd.getNhnYoteiYmd());
                }

                // 初回確定情報をセット
                // 共通情報取得
                String sCmnInsdd = CCDateUtil.getSysYear() + CCDateUtil.getSysMonth() + CCDateUtil.getSysDate();
                String sCmnTantoCd = context.getTantoCode();
                String sCmnTermId = context.getTermId();

                // 初回確定日③ = 伝票累積 `S001eosd`[cmJSICommon.COLNO_JS001EOSD.FIRST_KAKUTEI_YMD]①
                // 初回確定日③ == null の場合は、　初回確定日③ =　現在日付をセット
                // 初回確定情報
                String sFstInsdd = s001eosd.getFirstKakuteiYmd();
                if (sFstInsdd.trim().length() == 0) {
                    sFstInsdd = sCmnInsdd;
                }

                // 初回担当者④ = 伝票累積 `S001eosd`[cmJSICommon.COLNO_JS001EOSD.FIRST_TANTO_CD]①
                // 初回担当者④ == null の場合は、　初回確定日④ =　Request[担当者コード]
                String sFstTantoCd = s001eosd.getFirstTantoCd();
                if (sFstTantoCd.trim().length() == 0) {
                    sFstTantoCd = sCmnTantoCd;
                }

                // 初回端末ID⑤ = 伝票累積 `S001eosd`[cmJSICommon.COLNO_JS001EOSD.FIRST_TERM_ID]①
                // 初回端末ID⑤ == null の場合は、　初回端末ID⑤ =　Request[端末ID取得ロジック]の先頭の8バイトをセット
                String sFstTermId = s001eosd.getFirstTermId();
                if (sFstTermId.trim().length() == 0) {
                    sFstTermId = sCmnTermId;
                }

                // 最終確定情報
                String sLstInsdd = sCmnInsdd;
                String sLstTantoCd = sCmnTantoCd;
                String sLstTermId = sCmnTermId;

                // 確定区分、確定場所
                // cmJSICommonnのgetDefaultTenpoを呼びます
                String stenpoCd = cmJSICommon.getDefaultTenpo(context.getTantoCode());

                // // 確定場所(担当者所属店舗コード)から確定区分を取得
                // cmJSICommonnのgetKakuteiKbnを呼びます
                String sKakKbn = cmJSICommon.getKakuteiKbn(stenpoCd, sNhnDate);

                // 加算区分
                String sKasanKbn = CCSIConst.KASAN_KBN.KBN_SUKIN_A;

                // DC/TC区分（納品ルートをセット）
                String sDcTcKbn = sDpyNo;

                // 仕入月次月
                String sGetuji = sKeijoDate.substring(0, 6);

                // 処理状態区分("2")
                String sStsKbn = CCSIConst.SYORISTS_KBN.KBN_KAKTEI;
                
                // 検収合計原価金額
                Long sGenAll = s001eosd.getsHatGenkKin();

                // 検収合計売価金額
                Long sBaiAll = s001eosd.getsHatBaikKin();

                // 税計算用に税区分を取得
                String taxKbn = s001eosd.getTaxKbn();
                BigDecimal taxRitu = s001eosd.getTaxRitu();

                Long sGenAllZei = getTaxGaku(sGenAll, taxRitu, taxKbn, 1);
                Long sBaiAllZei = getTaxGaku(sBaiAll, taxRitu, taxKbn, 1);

                // 検収原価税額
                Long sGenkTax01 = getTaxGaku(s001eosd.getHatGenkKin01(), taxRitu, taxKbn, 0);
                Long sGenkTax02 = getTaxGaku(s001eosd.getHatGenkKin02(), taxRitu, taxKbn, 0);
                Long sGenkTax03 = getTaxGaku(s001eosd.getHatGenkKin03(), taxRitu, taxKbn, 0);
                Long sGenkTax04 = getTaxGaku(s001eosd.getHatGenkKin04(), taxRitu, taxKbn, 0);
                Long sGenkTax05 = getTaxGaku(s001eosd.getHatGenkKin05(), taxRitu, taxKbn, 0);
                Long sGenkTax06 = getTaxGaku(s001eosd.getHatGenkKin06(), taxRitu, taxKbn, 0);
                Long sGenkTax07 = getTaxGaku(s001eosd.getHatGenkKin07(), taxRitu, taxKbn, 0);
                Long sGenkTax08 = getTaxGaku(s001eosd.getHatGenkKin08(), taxRitu, taxKbn, 0);
                Long sGenkTax09 = getTaxGaku(s001eosd.getHatGenkKin09(), taxRitu, taxKbn, 0);
                Long sGenkTax10 = getTaxGaku(s001eosd.getHatGenkKin10(), taxRitu, taxKbn, 0);
                // 検収売価税額
                Long sBaikTax01 = getTaxGaku(s001eosd.getHatBaikKin01(), taxRitu, taxKbn, 0);
                Long sBaikTax02 = getTaxGaku(s001eosd.getHatBaikKin02(), taxRitu, taxKbn, 0);
                Long sBaikTax03 = getTaxGaku(s001eosd.getHatBaikKin03(), taxRitu, taxKbn, 0);
                Long sBaikTax04 = getTaxGaku(s001eosd.getHatBaikKin04(), taxRitu, taxKbn, 0);
                Long sBaikTax05 = getTaxGaku(s001eosd.getHatBaikKin05(), taxRitu, taxKbn, 0);
                Long sBaikTax06 = getTaxGaku(s001eosd.getHatBaikKin06(), taxRitu, taxKbn, 0);
                Long sBaikTax07 = getTaxGaku(s001eosd.getHatBaikKin07(), taxRitu, taxKbn, 0);
                Long sBaikTax08 = getTaxGaku(s001eosd.getHatBaikKin08(), taxRitu, taxKbn, 0);
                Long sBaikTax09 = getTaxGaku(s001eosd.getHatBaikKin09(), taxRitu, taxKbn, 0);
                Long sBaikTax10 = getTaxGaku(s001eosd.getHatBaikKin10(), taxRitu, taxKbn, 0);

                // EOS伝票累積 `S001eosd`ﾌｧｲﾙに更新する。
                // S001eosdMapperのupdateByExampleSelectiveで使用
                S001eosd s001eosdUpdate = new S001eosd();
                BeanUtils.copyProperties(s001eosd, s001eosdUpdate);

                if (sNhnDate.trim().length() != 0) {
                    s001eosdUpdate.setNhnYmd(sNhnDate);
                } else {
                    s001eosdUpdate.setNhnYmd(s001eosd.getNhnYoteiYmd());
                }

                s001eosdUpdate.setRuiKeijoYmd(sKeijoDate);
                s001eosdUpdate.setFirstKakuteiYmd(sFstInsdd);
                s001eosdUpdate.setFirstTantoCd(sFstTantoCd);
                s001eosdUpdate.setFirstTermId(sFstTermId);
                s001eosdUpdate.setLastKakuteiYmd(sCmnInsdd);
                s001eosdUpdate.setLastTantoCd(sCmnTantoCd);
                s001eosdUpdate.setLastTermId(sCmnTermId);
                s001eosdUpdate.setEntryKbn(sKakKbn);
                s001eosdUpdate.setEntryPlace(stenpoCd);
                s001eosdUpdate.setKasanKbn(sKasanKbn);
                s001eosdUpdate.setDctcKbn(sDcTcKbn);
                s001eosdUpdate.setSirGetujiYm(sGetuji);
                s001eosdUpdate.setSyoriStsKbn(sStsKbn);

                s001eosdUpdate.setSeqNo(sSeqNo);
                s001eosdUpdate.setsKenGenkKin(sGenAll);
                s001eosdUpdate.setsKenBaikKin(sBaiAll);
                s001eosdUpdate.setsKenGenkZei(sGenAllZei);
                s001eosdUpdate.setsKenBaikZei(sBaiAllZei);

                // 商品情報１
                s001eosdUpdate.setKenBaraSu01(s001eosd.getHatBaraSu01());
                s001eosdUpdate.setKenGenk01(s001eosd.getHatGenk01());
                s001eosdUpdate.setKenGenkKin01(s001eosd.getHatGenkKin01());
                s001eosdUpdate.setKenBaik01(s001eosd.getHatBaik01());
                s001eosdUpdate.setKenBaikKin01(s001eosd.getHatBaikKin01());
                s001eosdUpdate.setKenGenkZei01(Integer.valueOf(sGenkTax01.toString()));
                s001eosdUpdate.setKenBaikZei01(Integer.valueOf(sBaikTax01.toString()));

                // 商品情報２
                s001eosdUpdate.setKenBaraSu02(s001eosd.getHatBaraSu02());
                s001eosdUpdate.setKenGenk02(s001eosd.getHatGenk02());
                s001eosdUpdate.setKenGenkKin02(s001eosd.getHatGenkKin02());
                s001eosdUpdate.setKenBaik02(s001eosd.getHatBaik02());
                s001eosdUpdate.setKenBaikKin02(s001eosd.getHatBaikKin02());
                s001eosdUpdate.setKenGenkZei02(Integer.valueOf(sGenkTax02.toString()));
                s001eosdUpdate.setKenBaikZei02(Integer.valueOf(sBaikTax02.toString()));

                // 商品情報３
                s001eosdUpdate.setKenBaraSu03(s001eosd.getHatBaraSu03());
                s001eosdUpdate.setKenGenk03(s001eosd.getHatGenk03());
                s001eosdUpdate.setKenGenkKin03(s001eosd.getHatGenkKin03());
                s001eosdUpdate.setKenBaik03(s001eosd.getHatBaik03());
                s001eosdUpdate.setKenBaikKin03(s001eosd.getHatBaikKin03());
                s001eosdUpdate.setKenGenkZei03(Integer.valueOf(sGenkTax03.toString()));
                s001eosdUpdate.setKenBaikZei03(Integer.valueOf(sBaikTax03.toString()));

                // 商品情報４
                s001eosdUpdate.setKenBaraSu04(s001eosd.getHatBaraSu04());
                s001eosdUpdate.setKenGenk04(s001eosd.getHatGenk04());
                s001eosdUpdate.setKenGenkKin04(s001eosd.getHatGenkKin04());
                s001eosdUpdate.setKenBaik04(s001eosd.getHatBaik04());
                s001eosdUpdate.setKenBaikKin04(s001eosd.getHatBaikKin04());
                s001eosdUpdate.setKenGenkZei04(Integer.valueOf(sGenkTax04.toString()));
                s001eosdUpdate.setKenBaikZei04(Integer.valueOf(sBaikTax04.toString()));

                // 商品情報５
                s001eosdUpdate.setKenBaraSu05(s001eosd.getHatBaraSu05());
                s001eosdUpdate.setKenGenk05(s001eosd.getHatGenk05());
                s001eosdUpdate.setKenGenkKin05(s001eosd.getHatGenkKin05());
                s001eosdUpdate.setKenBaik05(s001eosd.getHatBaik05());
                s001eosdUpdate.setKenBaikKin05(s001eosd.getHatBaikKin05());
                s001eosdUpdate.setKenGenkZei05(Integer.valueOf(sGenkTax05.toString()));
                s001eosdUpdate.setKenBaikZei05(Integer.valueOf(sBaikTax05.toString()));

                // 商品情報６
                s001eosdUpdate.setKenBaraSu06(s001eosd.getHatBaraSu06());
                s001eosdUpdate.setKenGenk06(s001eosd.getHatGenk06());
                s001eosdUpdate.setKenGenkKin06(s001eosd.getHatGenkKin06());
                s001eosdUpdate.setKenBaik06(s001eosd.getHatBaik06());
                s001eosdUpdate.setKenBaikKin06(s001eosd.getHatBaikKin06());
                s001eosdUpdate.setKenGenkZei06(Integer.valueOf(sGenkTax06.toString()));
                s001eosdUpdate.setKenBaikZei06(Integer.valueOf(sBaikTax06.toString()));

                // 商品情報７
                s001eosdUpdate.setKenBaraSu07(s001eosd.getHatBaraSu07());
                s001eosdUpdate.setKenGenk07(s001eosd.getHatGenk07());
                s001eosdUpdate.setKenGenkKin07(s001eosd.getHatGenkKin07());
                s001eosdUpdate.setKenBaik07(s001eosd.getHatBaik07());
                s001eosdUpdate.setKenBaikKin07(s001eosd.getHatBaikKin07());
                s001eosdUpdate.setKenGenkZei07(Integer.valueOf(sGenkTax07.toString()));
                s001eosdUpdate.setKenBaikZei07(Integer.valueOf(sBaikTax07.toString()));

                // 商品情報８
                s001eosdUpdate.setKenBaraSu08(s001eosd.getHatBaraSu08());
                s001eosdUpdate.setKenGenk08(s001eosd.getHatGenk08());
                s001eosdUpdate.setKenGenkKin08(s001eosd.getHatGenkKin08());
                s001eosdUpdate.setKenBaik08(s001eosd.getHatBaik08());
                s001eosdUpdate.setKenBaikKin08(s001eosd.getHatBaikKin08());
                s001eosdUpdate.setKenGenkZei08(Integer.valueOf(sGenkTax08.toString()));
                s001eosdUpdate.setKenBaikZei08(Integer.valueOf(sBaikTax08.toString()));

                // 商品情報９
                s001eosdUpdate.setKenBaraSu09(s001eosd.getHatBaraSu09());
                s001eosdUpdate.setKenGenk09(s001eosd.getHatGenk09());
                s001eosdUpdate.setKenGenkKin09(s001eosd.getHatGenkKin09());
                s001eosdUpdate.setKenBaik09(s001eosd.getHatBaik09());
                s001eosdUpdate.setKenBaikKin09(s001eosd.getHatBaikKin09());
                s001eosdUpdate.setKenGenkZei09(Integer.valueOf(sGenkTax09.toString()));
                s001eosdUpdate.setKenBaikZei09(Integer.valueOf(sBaikTax09.toString()));

                // 商品情報１０
                s001eosdUpdate.setKenBaraSu10(s001eosd.getHatBaraSu10());
                s001eosdUpdate.setKenGenk10(s001eosd.getHatGenk10());
                s001eosdUpdate.setKenGenkKin10(s001eosd.getHatGenkKin10());
                s001eosdUpdate.setKenBaik10(s001eosd.getHatBaik10());
                s001eosdUpdate.setKenBaikKin10(s001eosd.getHatBaikKin10());
                s001eosdUpdate.setKenGenkZei10(Integer.valueOf(sGenkTax10.toString()));
                s001eosdUpdate.setKenBaikZei10(Integer.valueOf(sBaikTax10.toString()));

                // getUpdCommon()
                String cmnTantoCd = context.getTantoCode();
                String cmnTermId = context.getTermId();
                String sCmnUpddd = CCDateUtil.getSysYear() + CCDateUtil.getSysMonth() + CCDateUtil.getSysDate();
                String sCmnUpdtime =
                        CCDateUtil.getSysHour() + CCDateUtil.getSysMinute() + CCDateUtil.getSysSecond();
                s001eosdUpdate.setCmnTantoCd(cmnTantoCd);
                s001eosdUpdate.setCmnTermId(cmnTermId);
                s001eosdUpdate.setCmnUpddd(sCmnUpddd);
                s001eosdUpdate.setCmnUpdtime(sCmnUpdtime);

                // getwhereSQL()
                S001eosdExample s001eosdWhere = getWhereMapper(sDenNo, sKaisya, sJigyobu, null);
                // S001eosdMapperのupdateByExampleSelectiveで使用
                int execFlag = this.s001eosdMapper.updateByExampleSelective(s001eosdUpdate, s001eosdWhere);
                if (execFlag == 0) {
                    throw new ChaseException(Messages.get(CCMessageConst.MSG_KEY_EXCEPTION_UPDATE_NO_RECORD));
                }

                // EOS伝票入力トランの新規登録
                // Sijp0170MapperのselectS005eostで使用
                S005eost s005eostInsert = new S005eost();
                BeanUtils.copyProperties(s001eosd, s005eostInsert);
                
                String sInsDD = CCDateUtil.getSysYear() + CCDateUtil.getSysMonth() + CCDateUtil.getSysDate();
                String sInsTime =
                        CCDateUtil.getSysHour() + CCDateUtil.getSysMinute() + CCDateUtil.getSysSecond();
                String sUndKbn = "I";
                String sSeq = String.valueOf(getSeqNo(sDenNo, sKaisya, sJigyobu));

                s005eostInsert.setInsdd(sInsDD);
                s005eostInsert.setSeqno(Short.parseShort(sSeq));
                s005eostInsert.setSeqNo(sSeqNo);

                s005eostInsert.setInstime(sInsTime);
                s005eostInsert.setUpdateKbn(sUndKbn);

                s005eostInsert.setDpyNo(CCStringUtil.suppZero(sDenNo, 9));
                s005eostInsert.setRuiFlg("1");

                if (sNhnDate.trim().length() != 0) {
                    s005eostInsert.setNhnYmd(sNhnDate);
                } else {
                    s005eostInsert.setNhnYmd(s001eosd.getNhnYoteiYmd());
                }

                s005eostInsert.setRuiKeijoYmd(sKeijoDate);
                s005eostInsert.setFirstKakuteiYmd(sFstInsdd);
                s005eostInsert.setFirstTantoCd(sFstTantoCd);
                s005eostInsert.setFirstTermId(sFstTermId);
                s005eostInsert.setLastKakuteiYmd(sLstInsdd);
                s005eostInsert.setLastTantoCd(sLstTantoCd);
                s005eostInsert.setLastTermId(sLstTermId);

                s005eostInsert.setEntryKbn(sKakKbn);
                s005eostInsert.setEntryPlace(stenpoCd);
                s005eostInsert.setKasanKbn(sKasanKbn);
                s005eostInsert.setDctcKbn(sDcTcKbn);
                s005eostInsert.setSirGetujiYm(sGetuji);
                s005eostInsert.setSyoriStsKbn(sStsKbn);

                s005eostInsert.setsKenGenkKin(s001eosd.getsHatGenkKin());
                s005eostInsert.setsKenBaikKin(s001eosd.getsHatBaikKin());
                s005eostInsert.setsKenGenkZei(sGenAllZei);
                s005eostInsert.setsKenBaikZei(sBaiAllZei);

                s005eostInsert.setKenGenkZei01(Integer.valueOf(sGenkTax01.toString()));
                s005eostInsert.setKenGenkZei02(Integer.valueOf(sGenkTax02.toString()));
                s005eostInsert.setKenGenkZei03(Integer.valueOf(sGenkTax03.toString()));
                s005eostInsert.setKenGenkZei04(Integer.valueOf(sGenkTax04.toString()));
                s005eostInsert.setKenGenkZei05(Integer.valueOf(sGenkTax05.toString()));
                s005eostInsert.setKenGenkZei06(Integer.valueOf(sGenkTax06.toString()));
                s005eostInsert.setKenGenkZei07(Integer.valueOf(sGenkTax07.toString()));
                s005eostInsert.setKenGenkZei08(Integer.valueOf(sGenkTax08.toString()));
                s005eostInsert.setKenGenkZei09(Integer.valueOf(sGenkTax09.toString()));
                s005eostInsert.setKenGenkZei10(Integer.valueOf(sGenkTax10.toString()));

                s005eostInsert.setKenBaikZei01(Integer.valueOf(sBaikTax01.toString()));
                s005eostInsert.setKenBaikZei02(Integer.valueOf(sBaikTax02.toString()));
                s005eostInsert.setKenBaikZei03(Integer.valueOf(sBaikTax03.toString()));
                s005eostInsert.setKenBaikZei04(Integer.valueOf(sBaikTax04.toString()));
                s005eostInsert.setKenBaikZei05(Integer.valueOf(sBaikTax05.toString()));
                s005eostInsert.setKenBaikZei06(Integer.valueOf(sBaikTax06.toString()));
                s005eostInsert.setKenBaikZei07(Integer.valueOf(sBaikTax07.toString()));
                s005eostInsert.setKenBaikZei08(Integer.valueOf(sBaikTax08.toString()));
                s005eostInsert.setKenBaikZei09(Integer.valueOf(sBaikTax09.toString()));
                s005eostInsert.setKenBaikZei10(Integer.valueOf(sBaikTax10.toString()));

                // getCommon()
                s005eostInsert.setCmnTantoCd(sCmnTantoCd);
                s005eostInsert.setCmnTermId(sCmnTermId);
                s005eostInsert.setCmnInsdd(sCmnInsdd);
                s005eostInsert.setCmnUpddd(sCmnUpddd);
                s005eostInsert.setCmnUpdtime(sCmnUpdtime);

                // S005eostMapperのinsertで使用
                execFlag = this.s005eostMapper.insertSelective(s005eostInsert);
                if (execFlag == 0) {
                    throw new ChaseException(Messages.get(CCMessageConst.MSG_KEY_EXCEPTION_INSERT_NO_RECORD));
                }

                // S027eospMapperのinsertで使用
                S027eosp s027eospInsert = new S027eosp();
                BeanUtils.copyProperties(s005eostInsert, s027eospInsert);
                execFlag = this.s027eospMapper.insertSelective(s027eospInsert);
                if (execFlag == 0) {
                    throw new ChaseException(Messages.get(CCMessageConst.MSG_KEY_EXCEPTION_INSERT_NO_RECORD));
                }

            }

        }

        return created();
    }

    /**
     *  ＥＯＳ伝票累積Ｆデータ取得処理.
     * 機能概要：ＥＯＳ伝票累積ファイル内の伝票データを取得します。
     * @param sDenNo 伝票番号
     * @param kaisyaCd 会社コード
     * @param jigyobuCd 事業部コード
     * @return S001eosd 伝票累積データ
     */
    private S001eosd getDenpyoData(String sDenNo, String kaisyaCd, String jigyobuCd) {
        // 伝票番号9桁対応
        sDenNo = CCStringUtil.suppZero(sDenNo, 9);
        S001eosdExample s001eosdExample = getWhereMapper(sDenNo, kaisyaCd, jigyobuCd, null);
        List<S001eosd> s001eosdList = this.s001eosdMapper.selectByExample(s001eosdExample);

        if (s001eosdList.size() == 0) {
            return new S001eosd();
        }
        return s001eosdList.get(0);
    }

    /**
     * skengenkin取得
     * @param sDenNo 伝票番号
     * @param kaisyaCd 会社コード
     * @param jigyobuCd 事業部コード
     * @return 結果の文字列
     */
    public Result getsKenGenKin(String sDenNo, String kaisyaCd, String jigyobuCd) {
        sDenNo = CCStringUtil.suppZero(sDenNo, 9);
        List<String> dpyKbReason = new ArrayList<String>();
        dpyKbReason.add(CCSIConst.DEN_KBN.KBN_EOSSIR);
        S001eosdExample s001eosdExample = new S001eosdExample();

        Criteria criteria = s001eosdExample.createCriteria();
        criteria.andDpyNoEqualTo(sDenNo).andDenKbnIn(dpyKbReason);

        if (!UNDEFINED.equals(kaisyaCd)) {
            criteria.andKaisyaCdEqualTo(kaisyaCd);
        }

        if (!UNDEFINED.equals(jigyobuCd)) {
            criteria.andJigyoubuCdEqualTo(jigyobuCd);
        }

        List<S001eosd> datalist = this.s001eosdMapper.selectByExample(s001eosdExample);
        List<String> rs = new ArrayList<String>();
        if (datalist == null || datalist.size() == 0) {
            rs.add(EMPTY_STRING);
        } else {
            rs.add(datalist.get(0).getsHatGenkKin().toString());
        }
        return ok(Json.toJson(rs));
    }

    /**
     *  入力項目チェック処理
     *  @param msRute 納品ルート
     *  @param msNhndate 納品日
     *  @param msKaisya 会社コード
     *  @param msJigyobu 事業部コード
     *  @param msTen 店舗コード
     * @return true：処理成功、false：処理失敗
     */
    protected boolean doChkHead(String msRute, String msNhndate, String msKaisya, String msJigyobu, String msTen) {
        // エラー取得
        boolean blFlg = true;
        String sTanto = context.getTantoCode();
        String sLoginTen = cmJSICommon.getDefaultTenpo(sTanto);
        // 日付,時刻取得
        String sDate = CCDateUtil.getSysYear() + CCDateUtil.getSysMonth() + CCDateUtil.getSysDate();
        String sRuteNm = "";
        String sCD = "NHN_RUTE_" + msRute;
        int iNhnDate;

        // cmJSICommonnのgetSirNameを呼びます
        // 納品ルートが入力されている場合
        if (!CCNumberUtil.isNumeric(msRute)) {
            // 入力された項目に誤りがあります。
            // addMessageInfo("C0062", "parent.document.all.rutecd");
            errRes.addErrorInfo(CCMessageConst.MSG_KEY_VALIDATION_ERROR);
            errRes.addErrorInfo("ruteCd", CCMessageConst.MSG_KEY_ITEM_INPUT_IS_ERROR);
            blFlg = false;
        } else {
            if (EMPTY_STRING.equals(msNhndate)) {
                sRuteNm = cmJSICommon.getSirName(sCD.trim(), sDate);
            } else {
                sRuteNm = cmJSICommon.getSirName(sCD.trim(), msNhndate);
            }
            if (sRuteNm.trim().length() == 0) {
                // 「C0062」メッセージが表示される。
                errRes.addErrorInfo(CCMessageConst.MSG_KEY_VALIDATION_ERROR);
                errRes.addErrorInfo("ruteCd", CCMessageConst.MSG_KEY_ITEM_INPUT_IS_ERROR);
                blFlg = false;
            }
        }

        // 納品日
        if (!CCDateUtil.isDate(msNhndate)) {
            // 日付に誤りがあります。
            errRes.addErrorInfo(CCMessageConst.MSG_KEY_VALIDATION_ERROR);
            errRes.addErrorInfo("inpNohinDate", CCMessageConst.MSG_KEY_DATE_ERROR);
            blFlg = false;
        } else {
            String sUnyoDate = context.getUnyoDate();
            iNhnDate = CCStringUtil.cnvStrToInt(msNhndate);
            int iChkUnyoDate = CCStringUtil.cnvStrToInt(getMiraibi(sUnyoDate, 7));
            if (iNhnDate > iChkUnyoDate) {
                // 「T0021」メッセージが表示される。
                errRes.addErrorInfo(CCMessageConst.MSG_KEY_VALIDATION_ERROR);
                errRes.addErrorInfo("inpNohinDate", CCMessageConst.MSG_KEY_DATE_OUT_OF_RANGE);
                blFlg = false;
            }
        }

        // 会社
        String sKaisya = "";
        if (msKaisya != null) {
            sKaisya = msKaisya;
        }
        // cmJSICommonnのgetComKaisyaNameを呼びます
        if (!EMPTY_STRING.equals(sKaisya)) {
            if (!CCNumberUtil.isNumeric(sKaisya)) {
                // 入力された項目に誤りがあります。
                // 「C0062」メッセージが表示される。
                errRes.addErrorInfo(CCMessageConst.MSG_KEY_VALIDATION_ERROR);
                errRes.addErrorInfo("kaisyaCd", CCMessageConst.MSG_KEY_ITEM_INPUT_IS_ERROR);
                blFlg = false;
            } else {

                String sKaisyaNm = "";
                if (!EMPTY_STRING.equals(msNhndate)) {
                    sKaisyaNm = cmJSICommon.getComKaisyaName(sKaisya, msNhndate);
                } else {
                    sKaisyaNm = cmJSICommon.getComKaisyaName(sKaisya, sDate);
                }
                if (sKaisyaNm.trim().length() == 0) {
                    // 「C0062」メッセージが表示される。
                    errRes.addErrorInfo(CCMessageConst.MSG_KEY_VALIDATION_ERROR);
                    errRes.addErrorInfo("kaisyaCd", CCMessageConst.MSG_KEY_ITEM_INPUT_IS_ERROR);
                    blFlg = false;
                }
            }
        }

        String sJigyobu = "";
        if (msJigyobu != null) {
            sJigyobu = msJigyobu;
        }
        // cmJSICommonnのgetComJigyobuNameを呼びます
        if (!EMPTY_STRING.equals(sJigyobu)) {

            if (!CCNumberUtil.isNumeric(sJigyobu)) {
                // 入力された項目に誤りがあります。
                errRes.addErrorInfo(CCMessageConst.MSG_KEY_VALIDATION_ERROR);
                errRes.addErrorInfo("jigyobuCd", CCMessageConst.MSG_KEY_ITEM_INPUT_IS_ERROR);
                blFlg = false;
            } else {

                String sJigyobuNm = "";
                if (!EMPTY_STRING.equals(sKaisya)) {
                    if (!EMPTY_STRING.equals(msNhndate)) {
                        sJigyobuNm = cmJSICommon.getComJigyobuName(sKaisya, sJigyobu, msNhndate);
                    } else {
                        sJigyobuNm = cmJSICommon.getComJigyobuName(sKaisya, sJigyobu, sDate);
                    }
                    if (sJigyobuNm.trim().length() == 0) {
                        // 「C0062」メッセージが表示される。
                        errRes.addErrorInfo(CCMessageConst.MSG_KEY_VALIDATION_ERROR);
                        errRes.addErrorInfo("jigyobuCd", CCMessageConst.MSG_KEY_ITEM_INPUT_IS_ERROR);
                        blFlg = false;
                    }
                } else {
                    // 会社コードを入力してください。
                    // addMessageInfo("S1210", "parent.document.all.kaishacd");
                    errRes.addErrorInfo(CCMessageConst.MSG_KEY_VALIDATION_ERROR);
                    errRes.addErrorInfo("kaisyaCd", CCMessageConst.MSG_KEY_INPUT_REQUIRED_ENTER);
                    blFlg = false;

                }
            }
        }

        // cmJSICommonnのgetComTenpoNameを呼びます
        String sTenpo = "";
        if (msTen != null) {
            sTenpo = msTen;
        }

        if (!EMPTY_STRING.equals(sTenpo)) {
            if (!CCNumberUtil.isNumeric(sTenpo)) {
                // 「C0062」メッセージが表示される。
                errRes.addErrorInfo(CCMessageConst.MSG_KEY_VALIDATION_ERROR);
                errRes.addErrorInfo("tenCd", CCMessageConst.MSG_KEY_ITEM_INPUT_IS_ERROR);
                blFlg = false;
            } else {
                if (!EMPTY_STRING.equals(sJigyobu)) {
                    String sTenNm = "";
                    if (!EMPTY_STRING.equals(msNhndate)) {
                        sTenNm = cmJSICommon.getComTenpoName(sKaisya + sJigyobu + sTenpo, msNhndate);
                    } else {
                        sTenNm = cmJSICommon.getComTenpoName(sKaisya + sJigyobu + sTenpo, sDate);
                    }
                    if (sTenNm.trim().length() == 0) {
                        // 「C0062」メッセージが表示される。
                        errRes.addErrorInfo(CCMessageConst.MSG_KEY_VALIDATION_ERROR);
                        errRes.addErrorInfo("tenCd", CCMessageConst.MSG_KEY_ITEM_INPUT_IS_ERROR);
                        blFlg = false;
                    }
                } else {
                    // 事業部コードを入力してください。
                    // addMessageInfo("S1211", "parent.document.all.jigyobucd");
                    errRes.addErrorInfo(CCMessageConst.MSG_KEY_VALIDATION_ERROR);
                    errRes.addErrorInfo("jigyobuCd", CCMessageConst.MSG_KEY_INPUT_REQUIRED_ENTER);
                    blFlg = false;

                }
            }
        }

        // ｺﾝﾄﾛｰﾙMの納品日とﾁｪｯｸ
        // cmJSICommonnのgetComNhnYmdで使用
        if (!EMPTY_STRING.equals(msNhndate)) {
            iNhnDate = CCStringUtil.cnvStrToInt(msNhndate);
            int iCtlNhnDate = CCStringUtil.cnvStrToInt(cmJSICommon.getComNhnYmd(sLoginTen, msNhndate));
            if (iCtlNhnDate > iNhnDate) {
                // 「S1099」メッセージが表示される。
                errRes.addErrorInfo(CCMessageConst.MSG_KEY_VALIDATION_ERROR);
                errRes.addErrorInfo("inpNohinDate", CCMessageConst.MSG_KEY_DATE_OVER);
                blFlg = false;
            }
        }
        return blFlg;
    }

    /**
     * グリッド入力項目チェック処理
     * 機能概要：<br>
     * グリッドの入力データをチェックします。
     * @param gridList List<Sijp0170ResultDto>
     * @param msNhndate 納品日 
     * @param msKaisya 会社コード 
     * @param msJigyobu 事業部コード 
     * @param msTen 店舗コード
     * @return true：処理成功、false：処理失敗
     **/
    protected boolean doChkGrid(List<Sijp0170ResultDto> gridList, String msNhndate, String msKaisya,
            String msJigyobu, String msTen) {
        boolean blFlg = true;

        int iCnt = 0; // ｸﾞﾘｯﾄﾞ入力数
        for (int i = 0; i < ALL_CNT; i++) {
            // 画面入力値取得
            String sDenNo = gridList.get(i).getDpyNo().trim();
            String sGenka = gridList.get(i).getsKenGenKin().trim();

            // 入力チェック（未入力の場合はチェックなし）
            if (sDenNo.length() == 0) {
                // 未入力件数カウント
                iCnt++;
                continue;
            }

            if (!EMPTY_STRING.equals(sDenNo)) {
                // 伝票チェック
                // 0:正常 1:未存在、2:未確定以外、3:原価合計違い、4:入力店違い、5:納品日ｴﾗｰ
                // 6:納品日ｴﾗｰ2、7:取引先ﾁｪｯｸ
                int iStatus = doChkDenNo(sDenNo, sGenka, msNhndate, msKaisya, msJigyobu, msTen);

                switch (iStatus) {
                case 1:// 1:未存在
                       // 該当の伝票情報が存在しません。
                       // 「S1086」メッセージが表示される。
                    addMessageInfotoGrid(i, CCMessageConst.MSG_KEY_DENPYO_NOT_EXISTS);
                    blFlg = false;
                    break;
                case 2:// 2:未確定以外
                       // 指定された伝票は確定されています。
                       // 「S1085」メッセージが表示される。
                    addMessageInfotoGrid(i, CCMessageConst.MSG_KEY_DENPYO_CONFIRMING);
                    blFlg = false;
                    break;
                case 4:// 4:入力店違い
                       // 他店舗の伝票更新は行えません。
                       // 「S1058」メッセージが表示される。
                    addMessageInfotoGrid(i, CCMessageConst.MSG_KEY_CANT_UPDATE_STORE);
                    blFlg = false;
                    break;
                case 5: // 5:運用日＋１＜納品予定日
                    // 納品予定日が範囲外です。
                    // 「S5092」メッセージが表示される。
                    addMessageInfotoGrid(i, CCMessageConst.MSG_KEY_UNYODATE_INVALID);
                    blFlg = false;
                    break;
                case 6:// 6:納品日 <= 発注日
                       // 納品日が発注日以前になっています。
                       // 「S5093」メッセージが表示される。
                    addMessageInfotoGrid(i, CCMessageConst.MSG_KEY_NOHINDATE_HATSUDATE_INVALID);
                    blFlg = false;
                    break;

                case 7: // 7:取引先が内部取引先
                        // 取引先が商品センターの伝票は確定できません。
                        // 「S1097」メッセージが表示される。
                    addMessageInfotoGrid(i, CCMessageConst.MSG_KEY_TORIHIKI_NOT_CONFIRM);
                    blFlg = false;
                    break;

                case 8:
                    // 総量発注データは更新できません。
                    // 「S1201」メッセージが表示される。
                    addMessageInfotoGrid(i, CCMessageConst.MSG_KEY_SORYO_HATSU_NOT_UPDATE);
                    blFlg = false;
                    break;

                default:
                    break;
                }

            }
        }

        // 明細入力チェック
        if (iCnt >= ALL_CNT) {
            // 明細が入力されていません。
            addMessageInfotoGrid(0, CCMessageConst.MSG_KEY_VALUE_NO_INPUT);
            blFlg = false;
        }
        // 同一伝票入力チェック
        for (int i = 0; i < ALL_CNT; i++) {
            String sDenNo = gridList.get(i).getDpyNo().trim();
            if (sDenNo.trim().length() == 0) {
                continue;
            }
            for (int j = i + 1; j < ALL_CNT; j++) {
                if (i == j) {
                    continue;
                }
                String sDenNo2 = gridList.get(j).getDpyNo().trim();
                if (sDenNo.equals(sDenNo2)) {
                    // 同一コードが既に入力されています。
                    // 「C0020」メッセージが表示される。
                    addMessageInfotoGrid(j, CCMessageConst.MSG_KEY_DATA_IS_DUPLICATE);
                    blFlg = false;
                }
            }
        }
        return blFlg;
    }

    /**
     * コントロールにメッセージを追加します。
     * @param index :コラム
     * @param ccMessage :メッセージ
     */
    private void addMessageInfotoGrid(int index, String ccMessage) {
        errRes.addErrorInfo(CCMessageConst.MSG_KEY_VALIDATION_ERROR);
        if (index < ROW_CNT) {
            errRes.addErrorInfo("dpyNo1_" + index, ccMessage);
        } else if (index >= ROW_CNT && index < SCD_CNT) {

            int i = index - ROW_CNT;
            errRes.addErrorInfo("dpyNo2_" + i, ccMessage);

        } else if (index >= SCD_CNT && index < ALL_CNT) {
            int i = index - SCD_CNT;
            errRes.addErrorInfo("dpyNo3_" + i, ccMessage);
        }
    }

    /**
     * 指定された日付と日数で指定日＋日数の日付を返します。<BR>
     * @param kijunDay   基準日
     * @param nissu       日数
     * @return 日付（基準日に日数を加算した結果）
    **/
    private String getMiraibi(String kijunDay, int nissu) {
        int y = Integer.parseInt(kijunDay.substring(0, 4));
        int m = Integer.parseInt(kijunDay.substring(4, 6));
        int d = Integer.parseInt(kijunDay.substring(6, 8));
        int x = nissu;
        String date;

        if (x >= 0) {
            while ((d + x) > CCDateUtil.getLastDay(y, m)) {
                x = x - CCDateUtil.getLastDay(y, m);
                m = m + 1;
                if (m > 12) {
                    m = m - 12;
                    y = y + 1;
                }
            }
            d = d + x;
            date =
                    CCStringUtil.suppZero(Integer.toString(y), 4) + CCStringUtil.suppZero(Integer.toString(m), 2)
                            + CCStringUtil.suppZero(Integer.toString(d), 2);
        } else {
            while ((d + x) <= 0) {
                m = m - 1;
                if (m <= 0) {
                    m = m + 12;
                    y = y - 1;
                }
                x = x + CCDateUtil.getLastDay(y, m);
            }
            d = d + x;
            date =
                    CCStringUtil.suppZero(Integer.toString(y), 4) + CCStringUtil.suppZero(Integer.toString(m), 2)
                            + CCStringUtil.suppZero(Integer.toString(d), 2);
        }
        return date;
    }

    /**
     * 消費税計算処理.
     * <p>
     * 機能概要：<br>
     *　金額と税率より消費税額を取得します。
     * @param kingaku 金額
     * @param taxRitu 税率
     * @param taxKbn 税区分（1-4:内税計算、5-8:外税計算）
     * @param iKubun 税計算区分（0:内税計算、1:外税計算）
     * @return String 消費税額(小数点以下切捨て)
     */
    private Long getTaxGaku(Long kingaku, BigDecimal taxRitu, String taxKbn, int iKubun) {

        double dblTax = 0;
        Long taxGaku = new Long(0);
        // 数値に変換
//        double dblKingaku = CCStringUtil.cnvStrToDbl(sKingaku);
//        double dblTaxRitu = CCStringUtil.cnvStrToDbl(sTaxRitu);
        double dblKingaku = Double.parseDouble(kingaku.toString());
        double dblTaxRitu = Double.parseDouble(taxRitu.toString());

        if (dblKingaku == 0) {
            return new Long(0);
        }
        if (dblTaxRitu == 0) {
            return new Long(0);
        }

        if (iKubun == 0) {
            // 内税計算
            if ("1".equals(taxKbn) || "2".equals(taxKbn) || "3".equals(taxKbn) || "4".equals(taxKbn)) {
                // 税区分も内税なので計算
                dblTax = dblKingaku - (dblKingaku / (dblTaxRitu + 100)) * 100;
            } else {
                // 税区分が内税以外の場合は０
                dblTax = 0;
            }
        } else {
            // 外税計算
            if ("5".equals(taxKbn) || "6".equals(taxKbn) || "7".equals(taxKbn) || "8".equals(taxKbn)) {
                // 税区分も外税なので計算
                dblTax = dblKingaku * dblTaxRitu / 100;
            } else {
                // 税区分が外税以外の場合は０
                dblTax = 0;
            }
        }
        // 少数点以下を切捨て
        long lngTax = (long) dblTax;
        taxGaku = Long.valueOf(lngTax);

        return taxGaku;
    }

    /**
     *  伝票チェック処理
     * @param sDenNo ：伝票番号
     * @param sGenka : 原価金額
     * @param sNhnDate ：日付
     * @param sKaisya :会社
     * @param sJigyobu :事業部
     * @param sTen : 店舗
     * @return ケース値 0:正常 1:未存在、2:未確定以外、3:原価合計違い、4:入力店違い、
     *              5:納品日ｴﾗｰ 6:納品日ｴﾗｰ2、7:取引先ﾁｪｯｸ、8:総量発注区分が１
    */
    private int doChkDenNo(String sDenNo, String sGenka, String sNhnDate, String sKaisya, 
                                                            String sJigyobu, String sTen) {

        sDenNo = CCStringUtil.suppZero(sDenNo, 9);
        List<String> reason = new ArrayList<String>();
        reason.add(CCSIConst.DEN_KBN.KBN_EOSSIR);


        S001eosdExample example = getWhereMapper(sDenNo, sKaisya, sJigyobu, sTen);
        List<S001eosd> s001eosdList = this.s001eosdMapper.selectByExample(example);

        if (s001eosdList.size() == 0) {
            return 1;
        }
        S001eosd rec = s001eosdList.get(0);

        // 会社コード
        String sKaisyaDB = rec.getKaisyaCd();
        // 事業部コード
        String sJigyobuDB = rec.getJigyoubuCd();
        // 店コード
        String sTenDB = rec.getTenCd();

        // 納品予定日
        String sNhnYDB = rec.getNhnYoteiYmd();
        // 処理状態区分
        String sStsDB = rec.getSyoriStsKbn();
        // 総量発注区分
        String sSouKbnDB = rec.getIkatuFlg();

        // 発注日
        String sHatDate = rec.getHatYmd();

        // 店舗区分、事業部による更新可能チェック
        String sKakPlc = cmJSICommon.getDefaultTenpo(context.getTantoCode());
        String sTenKbn = "";
        sTenKbn = cmJSICommon.getComTenpoKubun(sKakPlc, sNhnDate);
        String sTanJigyobu = sKakPlc.substring(0, 4);
        String sTanTenpo = sKakPlc.substring(0, 7);
        String sTanJigyoCD = sKakPlc.substring(2, 4);

        if ("1".equals(sTenKbn)) {
            // 同一店舗のみ更新可能
            if (!sTanTenpo.equals(sKaisyaDB + sJigyobuDB + sTenDB)) {
                rec = null;
                return 4;
            }
        }

        if ("9".equals(sTenKbn)) {
            // 事業部コードにより異なる
            if (!"90".equals(sTanJigyoCD)) {
                // 同一事業部のみ更新可能
                if (!sTanJigyobu.equals(sKaisyaDB + sJigyobuDB)) {
                    rec = null;
                    return 4;
                }
            }

        } else {
            // そのほかの場合も同一店舗のみ可能
            if (!sTanTenpo.equals(sKaisyaDB + sJigyobuDB + sTenDB)) {
                rec = null;
                return 4;
            }
        }

        // 伝票状態チェック
        if (!sStsDB.equals(CCSIConst.SYORISTS_KBN.KBN_MIKAKTEI)) {
            rec = null;
            return 2;
        }

        // 納品予定日チェック（納品予定日が運用日＋１以降の伝票のみ対象）
        String sUnyoDate = context.getUnyoDate();
        int iUnyoDate = CCStringUtil.cnvStrToInt(getMiraibi(sUnyoDate, 7)); // 運用日＋７
        int iNhnYDate = CCStringUtil.cnvStrToInt(sNhnYDB); // 納品予定日
        if (iUnyoDate < iNhnYDate) {
            rec = null;
            return 5;
        }

        // 納品日 ＞ 発注日チェック
        int iHatDate = CCStringUtil.cnvStrToInt(sHatDate); // 発注日
        if (!EMPTY_STRING.equals(sNhnDate)) {
            int iNhnDate = CCStringUtil.cnvStrToInt(sNhnDate); // 納品日
            if (iNhnDate <= iHatDate) {
                rec = null;
                return 6;
            }
        }

        // 総量発注区分のチェック
        if ("1".equals(sSouKbnDB)) {
            rec = null;
            return 8;
        }
        // 正常終了
        return 0;
    }

    /**
     * メンテトラン内のデータ件数取得処理.
     * @param sDenNo  伝票番号  
     * @param kaisyaCd  会社コード  
     * @param jigyoubuCd 事業部コード 
     * @return Number
     */
    private int getSeqNo(String sDenNo, String kaisyaCd, String jigyoubuCd) {
        String sSeq = "";
        sDenNo = CCStringUtil.suppZero(sDenNo, 9);
        Sijp0170Execute sijp0170Execute = new Sijp0170Execute();
        sijp0170Execute.setDpyNo(sDenNo);
        sijp0170Execute.setKaisyaCd(kaisyaCd);
        sijp0170Execute.setJigyobuCd(jigyoubuCd);

        sSeq = mybatisDao.selectOne("selectS005eost", sijp0170Execute);

        // sSeq = resultDatalst.get(0);

        if (sSeq == null) {
            return 1;
        }
        int iSeq = CCStringUtil.cnvStrToInt(sSeq);
        iSeq++;
        return iSeq;
    }

    /**
      * 抽出条件
     * @param sDenNo 伝票番号 
     * @param sKaisyaCd 会社コード 
     * @param sJigyobuCd 事業部コード 
     * @param sTenCd 店舗コード
     * @return S001eosdExample
     */
    private S001eosdExample getWhereMapper(String sDenNo, String sKaisyaCd, String sJigyobuCd, String sTenCd) {

        sDenNo = CCStringUtil.suppZero(sDenNo, 9);
        List<String> reason = new ArrayList<String>();
        reason.add(CCSIConst.DEN_KBN.KBN_EOSSIR);


        S001eosdExample example = new S001eosdExample();

        Criteria criteria = example.createCriteria();
        criteria.andDpyNoEqualTo(sDenNo).andDenKbnIn(reason);

        if (!CCStringUtil.isEmpty(sKaisyaCd)) {
            criteria.andKaisyaCdEqualTo(sKaisyaCd);
        }

        if (!CCStringUtil.isEmpty(sJigyobuCd)) {
            criteria.andJigyoubuCdEqualTo(sJigyobuCd);
        }

        if (!CCStringUtil.isEmpty(sTenCd)) {
            criteria.andTenCdEqualTo(sTenCd);
        }
        return example;
    }
}
