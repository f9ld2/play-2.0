// /////////////////////////////////////////////////////////////////////
// Copyright(C) 2015 NEC Solution Innovators, Ltd. All Rights Reserved.
// /////////////////////////////////////////////////////////////////////

/*
 * ====================================================================
 * 
 * 機能名称 :本部発注入力（店舗）
 * 
 * 改版履歴
 * 
 * Rev. 改版年月日 改版者名 内容
 * 
 * 1.0 2014/03/25 TuanTQ 新規作成
 * 
 * ===================================================================
 */

package jp.co.necsoft.web_md.core.app.controllers.ha;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

import jp.co.necsoft.web_md.common.ChaseException;
import jp.co.necsoft.web_md.common.biz.IPluConverter;
import jp.co.necsoft.web_md.common.mybatis.dao.MyBatisSqlMapDao;
import jp.co.necsoft.web_md.core.app.dto.cm.T001kksyChkTeiTok;
import jp.co.necsoft.web_md.core.app.dto.cm.T001kksyChkTeiTokResult;
import jp.co.necsoft.web_md.core.app.dto.ha.H010hnbhExecuteHNBH;
import jp.co.necsoft.web_md.core.app.dto.ha.H010hnbhM009msymExecute;
import jp.co.necsoft.web_md.core.app.dto.ha.Hajp0020Dto;
import jp.co.necsoft.web_md.core.app.forms.ha.Hajp0020CondForm;
import jp.co.necsoft.web_md.core.app.forms.ha.Hajp0020GridData;
import jp.co.necsoft.web_md.core.app.forms.ha.Hajp0020ResultForm;
import jp.co.necsoft.web_md.core.common.CCKubunConst;
import jp.co.necsoft.web_md.core.common.CCMessageConst;
import jp.co.necsoft.web_md.core.common.CCSecurityAuthenticator;
import jp.co.necsoft.web_md.core.common.CCSystemContext;
import jp.co.necsoft.web_md.core.common.forms.ErrorResponse;
import jp.co.necsoft.web_md.core.common.mybatis.dao.H010hnbhMapper;
import jp.co.necsoft.web_md.core.common.mybatis.dao.H040btrsMapper;
import jp.co.necsoft.web_md.core.common.mybatis.dao.H610hclsMapper;
import jp.co.necsoft.web_md.core.common.mybatis.dao.H620clctMapper;
import jp.co.necsoft.web_md.core.common.mybatis.dao.M000kaimMapper;
import jp.co.necsoft.web_md.core.common.mybatis.dao.M001jgymMapper;
import jp.co.necsoft.web_md.core.common.mybatis.dao.M003bmnmMapper;
import jp.co.necsoft.web_md.core.common.mybatis.dao.M006tenmMapper;
import jp.co.necsoft.web_md.core.common.mybatis.dao.M007kijmMapper;
import jp.co.necsoft.web_md.core.common.mybatis.dao.M009msymMapper;
import jp.co.necsoft.web_md.core.common.mybatis.dao.M011trimMapper;
import jp.co.necsoft.web_md.core.common.mybatis.dao.M013makmMapper;
import jp.co.necsoft.web_md.core.common.mybatis.dao.M017meimMapper;
import jp.co.necsoft.web_md.core.common.mybatis.dao.T001kksyMapper;
import jp.co.necsoft.web_md.core.common.mybatis.dao.T002kkmsMapper;
import jp.co.necsoft.web_md.core.common.mybatis.dto.H010hnbh;
import jp.co.necsoft.web_md.core.common.mybatis.dto.H010hnbhExample;
import jp.co.necsoft.web_md.core.common.mybatis.dto.H040btrs;
import jp.co.necsoft.web_md.core.common.mybatis.dto.H040btrsExample;
import jp.co.necsoft.web_md.core.common.mybatis.dto.H610hcls;
import jp.co.necsoft.web_md.core.common.mybatis.dto.H610hclsExample;
import jp.co.necsoft.web_md.core.common.mybatis.dto.H620clct;
import jp.co.necsoft.web_md.core.common.mybatis.dto.H620clctExample;
import jp.co.necsoft.web_md.core.common.mybatis.dto.M000kaim;
import jp.co.necsoft.web_md.core.common.mybatis.dto.M000kaimExample;
import jp.co.necsoft.web_md.core.common.mybatis.dto.M001jgym;
import jp.co.necsoft.web_md.core.common.mybatis.dto.M001jgymExample;
import jp.co.necsoft.web_md.core.common.mybatis.dto.M003bmnm;
import jp.co.necsoft.web_md.core.common.mybatis.dto.M003bmnmExample;
import jp.co.necsoft.web_md.core.common.mybatis.dto.M006tenm;
import jp.co.necsoft.web_md.core.common.mybatis.dto.M006tenmExample;
import jp.co.necsoft.web_md.core.common.mybatis.dto.M007kijm;
import jp.co.necsoft.web_md.core.common.mybatis.dto.M007kijmExample;
import jp.co.necsoft.web_md.core.common.mybatis.dto.M009msym;
import jp.co.necsoft.web_md.core.common.mybatis.dto.M009msymExample;
import jp.co.necsoft.web_md.core.common.mybatis.dto.M011trim;
import jp.co.necsoft.web_md.core.common.mybatis.dto.M011trimExample;
import jp.co.necsoft.web_md.core.common.mybatis.dto.M013makm;
import jp.co.necsoft.web_md.core.common.mybatis.dto.M013makmExample;
import jp.co.necsoft.web_md.core.common.mybatis.dto.T001kksy;
import jp.co.necsoft.web_md.core.common.mybatis.dto.T001kksyExample;
import jp.co.necsoft.web_md.core.common.mybatis.dto.T002kkms;
import jp.co.necsoft.web_md.core.common.mybatis.dto.T002kkmsExample;
import jp.co.necsoft.web_md.core.common.utils.CCCheckExistsUtil;
import jp.co.necsoft.web_md.core.common.utils.CCDateUtil;
import jp.co.necsoft.web_md.core.common.utils.CCStringUtil;
import jp.co.necsoft.web_md.core.common.utils.DataUtil;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.mybatis.guice.transactional.Transactional;
import org.springframework.beans.BeanUtils;

import play.data.DynamicForm;
import play.data.Form;
import play.i18n.Messages;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;

/**
 * 本部発注入力（店舗）のControllerクラス
 *
 */
@Security.Authenticated(CCSecurityAuthenticator.class)
public class Hajp0020Ctrl extends Controller {
    /** First bin value  */
    private static final String FIRST_BIN_VALUE = "1";

    /** Second bin value  */
    private static final String SECOND_BIN_VALUE = "2";

    /** Second bin value  */
    private static final String THIRD_BIN_VALUE = "3";
    
    /** Default hatchu flag  */
    private static final String DEFAULT_HATCHU_FLG = "1";
    
    /** Default sime flag  */
    private static final String DEFAULT_SIME_FLG = "1";
    
    /** Max record  */
    private static final int RECORD_MAX = 500;

    /** 削除 */
    private static final String TYPE_ACT_KBN_DELETED = "9";
    
    /** 発注種類区分 */
    private static final String HAT_SRUI_KBN_CD_KBN = "H0008";
    
    /** STRING_DUMY*/
    private static final String STRING_DUMY = "DUMY_STRING";

    /** Blank character */
    private static final String BLANK = " ";
    
    /** KBN_VAL_H_HATSUYRUI_KBN_TEN_TENPO*/
    static final String KBN_VAL_H_HATSUYRUI_KBN_TEN_TENPO = "01";
    /** KBN_VAL_H_HATSUYRUI_KBN_TEN_HONBU*/
    static final String KBN_VAL_H_HATSUYRUI_KBN_TEN_HONBU = "99";
    /** 全部門 */
    private static final String ALL_BMN_CD = "00000";
    /** 全中分類 */
    private static final String ALL_CHU_BMN_CD = "0000";
    /** 全取引先 */
    private static final String ALL_TRI_CD = "000000000";
    // [2015/06/16 WebMD_SS_V000.001対応 INS START]
    /** 全取引先 */
    private static final String HAT_IF_KBN_WEBMD = "5";
    // [2015/06/16 WebMD_SS_V000.001対応 INS END]
    /** CCCheckExistsUtil */
    @Inject
    private CCCheckExistsUtil ccCheckExistsUtil;

    /** System Context */
    @Inject
    private CCSystemContext context;

    /** Error Response Object */
    @Inject
    private ErrorResponse errRes;

    /** M000kaim table's Mapper */
    @Inject
    private M000kaimMapper m000kaimMapper;

    /** M001jgym table's Mapper */
    @Inject
    private M001jgymMapper m001jgymMapper;

    /** M006tenm table's Mapper */
    @Inject
    private M006tenmMapper m006tenmMapper;
    
    /** M017meim table's Mapper */
    @Inject
    private M017meimMapper m017meimMapper;

    /** M003bmnm table's Mapper */
    @Inject
    private M003bmnmMapper m003bmnmMapper;

    /** M007kijm table's Mapper */
    @Inject
    private M007kijmMapper m007kijmMapper;

    /** M013makm table's Mapper */
    @Inject
    private M013makmMapper m013makmMapper;

    /** M011trim table's Mapper */
    @Inject
    private M011trimMapper m011trimMapper;

    /** M009msym table's Mapper */
    @Inject
    private M009msymMapper m009msymMapper;

    /** H010hnbh table's Mapper */
    @Inject
    private H010hnbhMapper h010hnbhMapper;
    
    /** T001kksyMapper */
    @Inject
    private T001kksyMapper t001kksyMapper;
    /** T002kkmsMapper */
    @Inject
    private T002kkmsMapper t002kkmsMapper;
    /** H040btrsMapper */
    @Inject
    private H040btrsMapper h040btrsMapper;
    /** H620clctMapper */
    @Inject
    private H620clctMapper h620clctMapper;
    /** H610hclsMapper */
    @Inject
    private H610hclsMapper h610hclsMapper;

    /** MyBatis Sql Map Dao */
    @Inject
    private MyBatisSqlMapDao dao;
    
    /** IPlu Converter */
    @Inject
    private IPluConverter plucnv;


    /**
     * Fucntion get full code 14byte of shnCd
     * 
     * @param shnCd
     *            shnCd
     * @return shnCd full string code
     */
    private String getFullShnCd(String shnCd) {
        if (!StringUtils.EMPTY.equals(shnCd) && !shnCd.startsWith(BLANK)) {
            shnCd = plucnv.toDbCode(shnCd);
        }
        return shnCd;
    }

    /**
     * Function check exists kaisyaCd
     * 
     * @param kaisyaCd input kaisya code
     * @param hatDd  input hatDd
     * @return boolean return exists or not
     */
    private boolean existsKaisyaCd(String kaisyaCd, String hatDd) {
        M000kaimExample example = new M000kaimExample();
        example.createCriteria().andKaisyaCdEqualTo(kaisyaCd);
        example.setSearchDate(hatDd);

        List<M000kaim> list = this.m000kaimMapper.selectByExample(example);
        return !(list.isEmpty() || TYPE_ACT_KBN_DELETED.equals(list.get(0).getActKbn()));
    }

    /**
     * Function check exists jigyobuCd
     * 
     * @param kaisyaCd input kaisya code
     * @param jigyobuCd  input jigyobuCd
     * @param hatDd  input hatDd
     * @return boolean return exists or not
     */
    private boolean existsJigyobuCd(String kaisyaCd, String jigyobuCd, String hatDd) {
        M001jgymExample example = new M001jgymExample();
        example.createCriteria().andKaisyaCdEqualTo(kaisyaCd).andJigyobuCdEqualTo(jigyobuCd);
        example.setSearchDate(hatDd);

        List<M001jgym> list = this.m001jgymMapper.selectByExample(example);
        return !(list.isEmpty() || TYPE_ACT_KBN_DELETED.equals(list.get(0).getActKbn()));
    }

    /**
     * Function check exists jigyobuCd
     * @param kaisyaCd input kaisya code
     * @param jigyobuCd  input jigyobuCd
     * @param tenCd  input tenCd
     * @param hatDd  input hatDd
     * @return boolean return exists or not
     */
    private boolean existsTenCd(String kaisyaCd, String jigyobuCd, String tenCd, String hatDd) {
        M006tenmExample example = new M006tenmExample();
        String param = kaisyaCd + jigyobuCd + tenCd;
        example.createCriteria().andTenCdEqualTo(param);
        example.setSearchDate(hatDd);
        List<M006tenm> list = this.m006tenmMapper.selectByExample(example);
        return !(list.isEmpty() || TYPE_ACT_KBN_DELETED.equals(list.get(0).getActKbn()));
    }

    /**
     * Function select M007kijm
     * 
     * @param hatDd  input hatDd
     * @param shnCd  input shnCd
     * @return List<M007kijm>
     */
    private List<M007kijm> selectM007kijm(String hatDd, String shnCd) {
        M007kijmExample example = new M007kijmExample();
        example.createCriteria().andJanCdEqualTo(shnCd);
        example.setSearchDate(hatDd);
        List<M007kijm> list = this.m007kijmMapper.selectByExample(example);
        return list;
    }

    /**
     * Function select M013makm
     * 
     * @param hatDd  input hatDd
     * @param mkrCd  input mkrCd
     * @return List<M013makm>
     */
    private List<M013makm> selectM013makm(String hatDd, String mkrCd) {
        M013makmExample example = new M013makmExample();
        example.createCriteria().andMkrCdEqualTo(mkrCd);
        example.setSearchDate(hatDd);
        List<M013makm> list = this.m013makmMapper.selectByExample(example);
        return list;
    }

    /**
     * Function select M011trim
     * 
     * @param hatDd  input hatDd
     * @param triCd  input triCd
     * @return List<M011trim>
     */
    private List<M011trim> selectM011trim(String hatDd, String triCd) {
        M011trimExample example = new M011trimExample();
        example.createCriteria().andTriCdEqualTo(triCd);
        example.setSearchDate(hatDd);
        List<M011trim> list = this.m011trimMapper.selectByExample(example);
        return list;
    }

    /**
     * Function select M009msym
     * 
     * @param hatDd  input hatDd
     * @param shnCd  input shnCd
     * @param tenCd  input tenCd
     * @param bmnCd  input bmnCd
     * @return List<M009msym>
     */
    private List<M009msym> selectM009msym(String hatDd, String shnCd, String tenCd, String bmnCd) {
        M009msymExample m009msymExample = new M009msymExample();
        if (!CCStringUtil.isEmpty(bmnCd)) {
            m009msymExample.createCriteria().andJanCdEqualTo(shnCd).andTenCdEqualTo(tenCd).andBmnCdEqualTo(bmnCd);
        } else {
            m009msymExample.createCriteria().andJanCdEqualTo(shnCd).andTenCdEqualTo(tenCd);
        }
        m009msymExample.setSearchDate(hatDd);
        List<M009msym> m009msymList = this.m009msymMapper.selectByExample(m009msymExample);
        return m009msymList;
    }

    /**
     * Function check exists bmnCd
     * 
     * @param date input date
     * @param bmnCd bmnCd
     * @return boolean exists or not
     */
    private boolean existsBmnCd(String date, String bmnCd) {
        M003bmnmExample example = new M003bmnmExample();
        example.createCriteria().andBmnCdEqualTo(bmnCd);
        example.setSearchDate(date);

        List<M003bmnm> list = this.m003bmnmMapper.selectByExample(example);
        return !(list.isEmpty() || TYPE_ACT_KBN_DELETED.equals(list.get(0).getActKbn()));
    }

    /**
    * 引数とリクエスト(パラメータ)を条件にDBからデータ(複数件)を検索し返却する。
    * 
    * @param kaisyaCd kaisyaCd
    * @param jigyobuCd jigyobuCd
    * @param tenCd tenCd
    * @param hatSruiKbn hatSruiKbn
    * @return クライアントへ返却する結果
    */
    public Result query(String kaisyaCd, String jigyobuCd, String tenCd, String hatSruiKbn) {
        Form<Hajp0020CondForm> emptyForm = new Form<Hajp0020CondForm>(Hajp0020CondForm.class);
        Form<Hajp0020CondForm> reqForm = emptyForm.bindFromRequest();
        DateTimeFormatter fmt = DateTimeFormat.forPattern(CCDateUtil.DATETIME_FORMAT_DATE_YYYYMMDD);
        if (reqForm.hasErrors()) {
            errRes.addErrorInfo(reqForm.errors());
            return badRequest(Json.toJson(errRes));
        } else {
            Hajp0020CondForm req = reqForm.get();
            String nhnDd = req.getNhnDd();
            // 会社コードチェック
            if (!existsKaisyaCd(kaisyaCd, nhnDd)) {
                errRes.addErrorInfo(CCMessageConst.MSG_KEY_VALIDATION_ERROR);
                errRes.addErrorInfo("kaisyaCd", CCMessageConst.MSG_KEY_NOT_REGISTERED_NO_PARAM);
                return badRequest(Json.toJson(errRes));
            }
            // 事業部コードチェック
            if (!existsJigyobuCd(kaisyaCd, jigyobuCd, nhnDd)) {
                errRes.addErrorInfo(CCMessageConst.MSG_KEY_VALIDATION_ERROR);
                errRes.addErrorInfo("jigyobuCd", CCMessageConst.MSG_KEY_NOT_REGISTERED_NO_PARAM);
                return badRequest(Json.toJson(errRes));
            }
            // 店舗コードチェック
            if (!existsTenCd(kaisyaCd, jigyobuCd, tenCd, nhnDd)) {
                errRes.addErrorInfo(CCMessageConst.MSG_KEY_VALIDATION_ERROR);
                errRes.addErrorInfo("tenCd", CCMessageConst.MSG_KEY_NOT_REGISTERED_NO_PARAM);
                return badRequest(Json.toJson(errRes));
            }

            // 発注種類区分チェック
            if (!ccCheckExistsUtil.existsKbn(HAT_SRUI_KBN_CD_KBN, hatSruiKbn)) {
                errRes.addErrorInfo(CCMessageConst.MSG_KEY_VALIDATION_ERROR);
                errRes.addErrorInfo("hatSruiKbn", CCMessageConst.MSG_KEY_EOSKBN_NOT_EXIST);
                return badRequest(Json.toJson(errRes));
            }

            // 部門コードチェック
            if (!CCStringUtil.isEmpty(req.getBmnCd()) &&!existsBmnCd(nhnDd, jigyobuCd + req.getBmnCd())) {
                errRes.addErrorInfo(CCMessageConst.MSG_KEY_VALIDATION_ERROR);
                errRes.addErrorInfo("bmnCd", CCMessageConst.MSG_KEY_NOT_REGISTERED_NO_PARAM);
                return badRequest(Json.toJson(errRes));
            }

            // 登録データ取得処理を行なう
            H010hnbhM009msymExecute h010hnbhM009msymExecute = new H010hnbhM009msymExecute();
            List<Hajp0020Dto> result = new ArrayList<Hajp0020Dto>();
            h010hnbhM009msymExecute.setHatDd(nhnDd);
            h010hnbhM009msymExecute.setTenCd7(kaisyaCd + jigyobuCd + tenCd);
            if (req.getBmnCd() != null && !StringUtils.EMPTY.equals(req.getBmnCd())) {
                h010hnbhM009msymExecute.setBmnCd(jigyobuCd + req.getBmnCd());
            } else {
                h010hnbhM009msymExecute.setBmnCd(StringUtils.EMPTY);
            }
            h010hnbhM009msymExecute.setKaiCd(kaisyaCd);
            h010hnbhM009msymExecute.setJgbCd(jigyobuCd);
            h010hnbhM009msymExecute.setTenCd(tenCd);
            h010hnbhM009msymExecute.setHatSruiKbn(hatSruiKbn);
            h010hnbhM009msymExecute.setNhnDd(req.getNhnDd());
            h010hnbhM009msymExecute.setHatSu("0");
            
            List<H010hnbh> list =
                    dao.selectMany("selectH010hnbhM009msym01", h010hnbhM009msymExecute, H010hnbh.class);
            // Noを編集する
            int iLoopCnt = 0;
            if (list != null && list.size() > 0) {
                iLoopCnt = list.size() > RECORD_MAX ? RECORD_MAX : list.size();
            }

            // 本部発注ファイルから取得したデータで入力された店舗グループコードに属するものをすべて格納
            for (int i = 0; i < iLoopCnt; i++) {
                Hajp0020Dto hajp0020Dto = new Hajp0020Dto();
                H010hnbh h010hnbh = list.get(i);
                BeanUtils.copyProperties(h010hnbh, hajp0020Dto);
                hajp0020Dto.setShnCd(plucnv.toDispCode(hajp0020Dto.getShnCd(), null));
                hajp0020Dto.setUpdateFlag(false);

                // 表示項目の内容をゲット
                List<M007kijm> lsM007kijm = selectM007kijm(h010hnbh.getHatDd(), getFullShnCd(h010hnbh.getShnCd()));
                if (lsM007kijm.isEmpty() || TYPE_ACT_KBN_DELETED.equals(lsM007kijm.get(0).getActKbn())) {
                    hajp0020Dto.setMkrNmR("＊＊＊＊＊");
                    hajp0020Dto.setShnNm("＊＊＊＊＊＊＊＊＊＊＊＊＊＊");
                    hajp0020Dto.setKikakuNmA("**********");
                } else {
                    M007kijm m007kijm = lsM007kijm.get(0);
                    String shNm = m007kijm.getShnNm();
                    if (shNm.length() > 14) {
                        shNm = m007kijm.getShnNm().substring(0, 14);
                    }
                    hajp0020Dto.setShnNm(shNm);
                    hajp0020Dto.setKikakuNmA(m007kijm.getKikakuNmA());
                    // 取引先名称を取得する
                    List<M013makm> lsM013makm = this.selectM013makm(nhnDd, m007kijm.getMkrCd());
                    if (!lsM013makm.isEmpty()) {
                        hajp0020Dto.setMkrNmR(lsM013makm.get(0).getMkrNmR());
                    } else {
                        hajp0020Dto.setMkrNmR("＊＊＊＊＊");
                    }
                }

                if (StringUtils.EMPTY.equals(h010hnbh.getTriCd())) {
                    hajp0020Dto.setTriNmR("＊＊＊＊＊");
                } else {
                    // 取引先名称を取得する
                    List<M011trim> lsM011trim =
                            this.selectM011trim(nhnDd, CCStringUtil.frmtToriCode(h010hnbh.getTriCd(), false));
                    if (lsM011trim.isEmpty() || TYPE_ACT_KBN_DELETED.equals(lsM011trim.get(0).getActKbn())) {
                        hajp0020Dto.setTriNmR("＊＊＊＊＊");
                    } else {
                        hajp0020Dto.setTriNmR(lsM011trim.get(0).getTriNmR());
                    }
                }
                // マスタ入数を取得する
                String tenCdSv = kaisyaCd + jigyobuCd + tenCd;
                String bmnCd = "";
                if (!CCStringUtil.isEmpty(req.getBmnCd())) {
                    bmnCd = jigyobuCd + req.getBmnCd();
                }
                List<M009msym> m009msymList =
                        this.selectM009msym(nhnDd, getFullShnCd(h010hnbh.getShnCd()), tenCdSv, bmnCd);

                if (m009msymList.isEmpty() || TYPE_ACT_KBN_DELETED.equals(m009msymList.get(0).getActKbn())) {
                    hajp0020Dto.setMstJinSu(null);
                } else {
                    hajp0020Dto.setMstJinSu(m009msymList.get(0).getHattyuIrisu());
                }

                result.add(hajp0020Dto);
            }

            DateTime d1 = fmt.parseDateTime(nhnDd);
            DateTime d2 = fmt.parseDateTime(context.getHatUnyoDate());
            // 発注日と発注運用日の差が60日以上の場合はワーニングメッセージ表示
            if (Days.daysBetween(d2.withTimeAtStartOfDay(), d1.withTimeAtStartOfDay()).getDays() > 60) {
                errRes.addErrorInfo(CCMessageConst.MSG_KEY_HATUDAY_UNYOUDAY_LATER_60);
            }

            DateTime d3 = fmt.parseDateTime(req.getNhnDd());
            if (Days.daysBetween(d1.withTimeAtStartOfDay(), d3.withTimeAtStartOfDay()).getDays() > 30) {
                errRes.addErrorInfo(CCMessageConst.MSG_KEY_HATUDAY_LATER_30);
            }

            if (result.size() > 0) {
                result.get(0).setInfoRes(errRes);
            } else {
                Hajp0020Dto rs = new Hajp0020Dto();
                rs.setInfoRes(errRes);
                result.add(rs);
            }

            // Return result
            return ok(Json.toJson(result));
        }
    }
    
    /**
     * 【「JANコード」・「便」入力時のデータ抽出】
     * @param kaisyaCd
     * @param jigyobuCd
     * @param tenCd
     * @param nhnDd
     * @param hatSruiKbn
     * @param janCd
     * @param bin
     * @param bmnCd
     * @return クライアントへ返却する結果
     */
    public Result queryDetail(String kaisyaCd, String jigyobuCd, String tenCd, String nhnDd, String hatSruiKbn,
            String janCd, String bin, String bmnCd) {
        Hajp0020Dto result = new Hajp0020Dto();
        String mkrNmR = "＊＊＊＊＊";
        String shnNm = "＊＊＊＊＊";
        String kikakuNmA = "＊＊＊＊＊";
        String hatDd = StringUtils.EMPTY;
        if (STRING_DUMY.equals(bmnCd)) {
            bmnCd = "";
        }
        // 2.1 企画商品マスタからデータを抽出する。
        T001kksy t001kksy = this.getT001Kksy(kaisyaCd, jigyobuCd, janCd, nhnDd, bmnCd);
        T002kkms t002kkms = null;
        if (t001kksy != null) {
            // 2.2 企画店別商品マスタからデータを抽出する。
            t002kkms = this.getT002kkms(kaisyaCd, jigyobuCd, t001kksy.getBmnCd(), kaisyaCd + jigyobuCd + tenCd,
                    janCd, t001kksy.getKikakuCd(), t001kksy.getNendo());
        }
        if (t001kksy != null && t002kkms != null) {
            // 2.3 物流スケジュールマスタからデータを抽出する。
            H040btrs h040btrs = this.getH040btrs(t002kkms.getTorihikiCd(), nhnDd.substring(0, 6), bin);
            if (h040btrs == null && KBN_VAL_H_HATSUYRUI_KBN_TEN_TENPO.equals(hatSruiKbn)) {
                errRes.addErrorInfo(CCMessageConst.MSG_KEY_VALIDATION_ERROR);
                errRes.addErrorInfo("shnCd", CCMessageConst.MSG_KEY_PRODUCT_MASTER_SALE_NOT_REGISTER);
                errRes.addErrorInfo("bin", CCMessageConst.MSG_KEY_PRODUCT_MASTER_SALE_NOT_REGISTER);
                return badRequest(Json.toJson(errRes));
            }
            if (h040btrs == null && KBN_VAL_H_HATSUYRUI_KBN_TEN_HONBU.equals(hatSruiKbn)) {
                hatDd = StringUtils.EMPTY;
            } else {
                hatDd = String
                        .valueOf(DataUtil.getValueByMethodName(h040btrs, "getTokHatDd" + nhnDd.substring(6, 8)));
            }
            result.setTriCd(t002kkms.getTorihikiCd());
            result.setHattyuIrisu(t002kkms.getTokuIrisu().shortValue());
            result.setGenk(t002kkms.getTokuGenk());
            result.setBaik(t002kkms.getTokuBaik());
            result.setKikakuCd(t002kkms.getKikakuCd());
            result.setNendo(t002kkms.getNendo());
            result.setBmnCd(t002kkms.getBmnCd());
        } else {
            // 2.4 店別商品マスタからデータを抽出する。
            M009msym m009msym = this.getM009msym(getFullShnCd(janCd), kaisyaCd + jigyobuCd + tenCd, nhnDd);
            if (m009msym == null) {
                errRes.addErrorInfo(CCMessageConst.MSG_KEY_NO_FOUND_DATA);
                return badRequest(Json.toJson(errRes));
            }
            // 2.5 物流スケジュールマスタからデータを抽出する。
            H040btrs h040btrs = this.getH040btrs(m009msym.getTriCd(), nhnDd.substring(0, 6), bin);
            if (h040btrs == null && KBN_VAL_H_HATSUYRUI_KBN_TEN_TENPO.equals(hatSruiKbn)) {
                errRes.addErrorInfo(CCMessageConst.MSG_KEY_VALIDATION_ERROR);
                errRes.addErrorInfo("shnCd", CCMessageConst.MSG_KEY_TRICD_NOT_REGISTER);
                errRes.addErrorInfo("bin", CCMessageConst.MSG_KEY_TRICD_NOT_REGISTER);
                return badRequest(Json.toJson(errRes));
            }
            if (h040btrs == null && KBN_VAL_H_HATSUYRUI_KBN_TEN_HONBU.equals(hatSruiKbn)) {
                hatDd = StringUtils.EMPTY;
            } else {
                hatDd = String
                        .valueOf(DataUtil.getValueByMethodName(h040btrs, "getTeiHatDd" + nhnDd.substring(6, 8)));
            }
            result.setTriCd(m009msym.getTriCd());
            result.setHattyuIrisu(m009msym.getHattyuIrisu().shortValue());
            result.setGenk(m009msym.getGenk());
            result.setBaik(m009msym.getBaik());
            result.setBmnCd(m009msym.getBmnCd());
        }
        
        // 2.6 基本情報マスタからデータを抽出する。
        M007kijm m007kijm = this.getM007kijm(getFullShnCd(janCd), nhnDd);
        if (m007kijm != null) {
            shnNm = m007kijm.getShnNm();
            kikakuNmA = m007kijm.getKikakuNmA();
            if (kikakuNmA.length() > 7) {
                kikakuNmA = kikakuNmA.substring(0, 7);
            }
            // 2.7 メーカーマスタからデータを抽出する。
            mkrNmR = this.getMkrNmR(m007kijm.getMkrCd(), nhnDd);
        }

        // 3 取得したデータをレスポンスにセットし、返却する。
        result.setMkrNmR(mkrNmR);
        result.setShnNm(shnNm);
        result.setKikakuNmA(kikakuNmA);
        result.setHatDd(hatDd);
        return ok(Json.toJson(result));
    }
    
    /**
     * check data when press button 反映
     * @param tenCd String
     * @param janCd String
     * @param bin String
     * @param hatDd String
     * @param nhnDd String
     * @param hatSruiKbn String
     * @param triCd String
     * @return クライアントへ返却する結果
     */
    public Result checkDetailData(String tenCd, String janCd, String bin, String hatDd, String nhnDd,
            String hatSruiKbn, String triCd) {
        String closeKbn = null;
        //[2015/06/16 WebMD_SS_V000.001対応 INS START]
        Form<Hajp0020GridData> emptyForm = new Form<Hajp0020GridData>(Hajp0020GridData.class);
        Form<Hajp0020GridData> reqForm = emptyForm.bindFromRequest();
        if (reqForm.hasErrors()) {
            errRes.addErrorInfo(reqForm.errors());
            return badRequest(Json.toJson(errRes));
        }
        //[2015/06/16 WebMD_SS_V000.001対応 INS END]
        if (KBN_VAL_H_HATSUYRUI_KBN_TEN_TENPO.equals(hatSruiKbn)) {
            String unyoDate = context.getUnyoDate();
            // 2.2 発注が締め切られているかチェックを行う。
            if (unyoDate.compareTo(hatDd) > 0) {
                errRes.addErrorInfo(CCMessageConst.MSG_KEY_VALIDATION_ERROR);
                errRes.addErrorInfo("bin", CCMessageConst.MSG_KEY_ORDER_ENTRY_HAS_BEEN_CLOSE);
                return badRequest(Json.toJson(errRes));
            } else if (unyoDate.compareTo(hatDd) == 0) {
                // 2.1 店別商品マスタからデータを抽出する。
                M009msym m009msym =  this.getM009msym(getFullShnCd(janCd), tenCd, nhnDd);
                H620clct h620clct = this.getH620clct(m009msym.getBmnCd(), m009msym.getChuBnrCd(), bin, triCd, nhnDd);
                if (h620clct != null) {
                    closeKbn = h620clct.getCloseKbn();
                }
                String closeTime = this.getCloseTime(h620clct, nhnDd);
                String sysDate = CCDateUtil.getSysDateFormat(CCDateUtil.DATETIME_FORMAT_HHMM);
                if (sysDate.compareTo(closeTime) > 0) {
                    errRes.addErrorInfo(CCMessageConst.MSG_KEY_VALIDATION_ERROR);
                    errRes.addErrorInfo("bin", CCMessageConst.MSG_KEY_ORDER_ENTRY_HAS_BEEN_CLOSE);
                    return badRequest(Json.toJson(errRes));
                }
            }
        } else if (KBN_VAL_H_HATSUYRUI_KBN_TEN_HONBU.equals(hatSruiKbn)) {
            String hatUnyoDate = context.getHatUnyoDate();
            if (hatUnyoDate.compareTo(hatDd) > 0) {
                errRes.addErrorInfo(CCMessageConst.MSG_KEY_VALIDATION_ERROR);
                errRes.addErrorInfo("hatDd", CCMessageConst.MSG_KEY_ORDER_ENTRY_HAS_BEEN_CLOSE);
                return badRequest(Json.toJson(errRes));
            }
        }

        HashMap<String, String> map = new HashMap<String, String>();
        map.put("closeKbn", closeKbn);
        return ok(Json.toJson(map));
    }


    /**
    * 引数とリクエスト(ボディ)からレコードを編集しDBの該当データを更新(論理)する。
    * 
    * @param kaisyaCd kaisyaCd
    * @param jigyobuCd jigyobuCd
    * @param tenCd tenCd
    * @param hatSruiKbn hatSruiKbn
    * @return クライアントへ返却する結果
    */
    public Result update(String kaisyaCd, String jigyobuCd, String tenCd, String hatSruiKbn) {
        @SuppressWarnings({"unchecked", "rawtypes" })
        Form<Hajp0020ResultForm> emptyForm = new Form(Hajp0020ResultForm.class);
        Form<Hajp0020ResultForm> reqForm = emptyForm.bindFromRequest();
        if (reqForm.hasErrors()) {
            errRes.addErrorInfo(CCMessageConst.MSG_KEY_VALIDATION_ERROR);
            errRes.addErrorInfo(reqForm.errors());
            return badRequest(Json.toJson(errRes));
        } else {

            Hajp0020ResultForm req = reqForm.get();
//            String hatDd = req.getHatDd();
            String nhnDd = req.getNhnDd();
            List<Hajp0020Dto> list = req.getHachuArea();

            // Check data
            list = processCheckData(kaisyaCd, nhnDd, jigyobuCd, tenCd, hatSruiKbn, req, list);

            // check if error then out error to client
            if (list == null) {
                errRes.addErrorInfo(CCMessageConst.MSG_KEY_VALIDATION_ERROR);
                return badRequest(Json.toJson(errRes));
            }
            execute(kaisyaCd, nhnDd, jigyobuCd, tenCd, hatSruiKbn, list, req);
        }
        return ok(Json.toJson(errRes));
    }
    
    /**
     * get closeTimeHbn from H610 
     * @param h620clct H620clct
     * @param date String
     * @return closeTimeHbn
     */
    private String getCloseTime(H620clct h620clct, String date) {
        String result = "";
        if (h620clct != null) {
            H610hclsExample h610hclsExample = new H610hclsExample();
            h610hclsExample.createCriteria().andCloseKbnEqualTo(h620clct.getCloseKbn());
            h610hclsExample.setSearchDate(date);
            List<H610hcls> h610hclsList = h610hclsMapper.selectByExample(h610hclsExample);
            if (!h610hclsList.isEmpty()) {
                result = h610hclsList.get(0).getCloseTimeHnb();
            }
        }
        return result;
    }
    
    /**
     * 発注締コントロールを取得する。
     * @param bmnCd 部門コード 
     * @param lineCd ラインコード
     * @param binKbn 便区分
     * @param triCd 取引先コード
     * @param orderDate 発注日
     * @return H620clct 発注締コントロール
     */
    private H620clct getH620clct(String bmnCd, String lineCd, String binKbn, String triCd, String orderDate) {
        // １.取引先＋部門＋中分類＋便
        H620clctExample h620clctExample = new H620clctExample();
        h620clctExample.createCriteria().andBmnCdEqualTo(bmnCd).andChuBnrCdEqualTo(lineCd).andBinEqualTo(binKbn)
                .andTorihikiCdEqualTo(triCd);
        h620clctExample.setSearchDate(orderDate);
        List<H620clct> h620clctList = h620clctMapper.selectByExample(h620clctExample);

        // ２.取引先＋部門＋全中分類＋便
        if (h620clctList.isEmpty()) {
            h620clctExample.clear();
            h620clctExample.createCriteria().andBmnCdEqualTo(bmnCd).andChuBnrCdEqualTo(ALL_CHU_BMN_CD)
                    .andBinEqualTo(binKbn).andTorihikiCdEqualTo(triCd);
            h620clctList = h620clctMapper.selectByExample(h620clctExample);
        }

        // ３.取引先＋全部門＋全中分類＋便
        if (h620clctList.isEmpty()) {
            h620clctExample.clear();
            h620clctExample.createCriteria().andBmnCdEqualTo(ALL_BMN_CD).andChuBnrCdEqualTo(ALL_CHU_BMN_CD)
                    .andBinEqualTo(binKbn).andTorihikiCdEqualTo(triCd);
            h620clctList = h620clctMapper.selectByExample(h620clctExample);
        }

        // ４.全取引先＋部門＋中分類＋便
        if (h620clctList.isEmpty()) {
            h620clctExample.clear();
            h620clctExample.createCriteria().andBmnCdEqualTo(bmnCd).andChuBnrCdEqualTo(lineCd).andBinEqualTo(binKbn)
                    .andTorihikiCdEqualTo(ALL_TRI_CD);
            h620clctList = h620clctMapper.selectByExample(h620clctExample);
        }

        // ５.全取引先＋部門＋全中分類＋便
        if (h620clctList.isEmpty()) {
            h620clctExample.clear();
            h620clctExample.createCriteria().andBmnCdEqualTo(bmnCd).andChuBnrCdEqualTo(ALL_CHU_BMN_CD)
                    .andBinEqualTo(binKbn).andTorihikiCdEqualTo(ALL_TRI_CD);
            h620clctList = h620clctMapper.selectByExample(h620clctExample);
        }

        if (!h620clctList.isEmpty()) {
            return h620clctList.get(0);
        }
        return null;
    }
    
    /**
     * get メーカー名略称漢字
     * @param mkrCd String
     * @param nhnDd String
     * @return メーカー名略称漢字
     */
    private String getMkrNmR(String mkrCd, String nhnDd) {
        M013makmExample example = new M013makmExample();
        example.createCriteria().andMkrCdEqualTo(mkrCd);
        example.setSearchDate(nhnDd);
        List<M013makm> list = m013makmMapper.selectByExample(example);
        if (!list.isEmpty() && !TYPE_ACT_KBN_DELETED.equals(list.get(0).getActKbn())) {
            return list.get(0).getMkrNmR();
        }
        return "＊＊＊＊＊";
    }
    
    /**
     * get M007Kijm object
     * @param janCd String
     * @param nhnDd String
     * @return M007Kijm object
     */
    private M007kijm getM007kijm(String janCd, String nhnDd) {
        M007kijmExample example = new M007kijmExample();
        example.createCriteria().andJanCdEqualTo(janCd);
        example.setSearchDate(nhnDd);
        List<M007kijm> list = m007kijmMapper.selectByExample(example);
        if (!list.isEmpty() && !TYPE_ACT_KBN_DELETED.equals(list.get(0).getActKbn())) {
            return list.get(0);
        }
        return null;
    }
    
    /**
     * get M009msym object
     * @param janCd String
     * @param tenCd String
     * @param nhnDd String
     * @return M009msym object
     */
    private M009msym getM009msym(String janCd, String tenCd, String nhnDd) {
        M009msymExample example = new M009msymExample();
        example.createCriteria().andJanCdEqualTo(getFullShnCd(janCd)).andTenCdEqualTo(tenCd);
        example.setSearchDate(nhnDd);
        List<M009msym> list = m009msymMapper.selectByExample(example);
        if (!list.isEmpty() && !TYPE_ACT_KBN_DELETED.equals(list.get(0).getActKbn())) {
            return list.get(0);
        }
        return null;
    }
    
    /**
     * get list H040btrs
     * @param triCd String
     * @param yymm String
     * @param bin String
     * @return list H040btrs object
     */
    private H040btrs getH040btrs(String triCd, String yymm, String bin) {
        H040btrsExample example = new H040btrsExample();
        example.createCriteria().andTriCdEqualTo(triCd).andYyyymmEqualTo(yymm).andBinEqualTo(bin);
        List<H040btrs> list = h040btrsMapper.selectByExample(example);
        return list.isEmpty() ?  null : list.get(0);
    }
    
    /**
     * get T002Kkms object
     * @param kaisyaCd String
     * @param jigyobuCd String
     * @param bmnCd String
     * @param tenCd String
     * @param janCd String
     * @param kikakuCd String
     * @param nendo String
     * @return T002Kkms object
     */
    private T002kkms getT002kkms(String kaisyaCd, String jigyobuCd, String bmnCd, String tenCd, String janCd,
            String kikakuCd, String nendo) {
        T002kkmsExample example = new T002kkmsExample();
        example.createCriteria().andKaisyaCdEqualTo(kaisyaCd)
        .andJigyobuCdEqualTo(jigyobuCd)
        .andBmnCdEqualTo(bmnCd)
        .andTenCdEqualTo(tenCd)
        .andJanCdEqualTo(getFullShnCd(janCd))
        .andNendoEqualTo(nendo)
        .andKikakuCdEqualTo(kikakuCd)
        .andActKbnNotEqualTo(TYPE_ACT_KBN_DELETED);
        example.setOrderByClause("KIKAKU_CD");
        List<T002kkms> list = t002kkmsMapper.selectByExample(example);
        return list.isEmpty() ? null : list.get(0); 
    }
    
    /**
     * get t001kksy object
     * @param kaisyaCd String
     * @param jigyobuCd String
     * @param janCd String
     * @param nhnDd String
     * @param bmnCd String
     * @return T001kksy object
     */
    private T001kksy getT001Kksy(String kaisyaCd, String jigyobuCd, String janCd, String nhnDd, String bmnCd) {
        T001kksyExample example = new T001kksyExample();
        example.createCriteria().andKaisyaCdEqualTo(kaisyaCd).andJigyobuCdEqualTo(jigyobuCd)
        .andJanCdEqualTo(getFullShnCd(janCd))
        .andSiireFrddLessThanOrEqualTo(nhnDd).andSiireToddGreaterThanOrEqualTo(nhnDd)
        .andActKbnNotEqualTo(TYPE_ACT_KBN_DELETED);
        if (!CCStringUtil.isEmpty(bmnCd)) {
            example.createCriteria().andBmnCdEqualTo(jigyobuCd + bmnCd);
        }
        example.setOrderByClause("TOKU_GENK, TOKU_BAIK, NENDO || KIKAKU_CD");
        List<T001kksy> list = t001kksyMapper.selectByExample(example);
        return list.isEmpty() ? null : list.get(0);
        
    }

    /**
     * Function check data when insert or update
     * 
     * @param kaisyaCd kaisyaCd
     * @param nhnDd nhnDd
     * @param jigyobuCd jigyobuCd
     * @param tenCd tenCd
     * @param hatSruiKbn hatSruiKbn
     * @param req Hajp0020ResultForm
     * @param list List<Hajp0020Dto>
     * @return List<Hajp0020Dto>
     */
    public List<Hajp0020Dto> processCheckData(String kaisyaCd, String nhnDd, String jigyobuCd, String tenCd,
            String hatSruiKbn, Hajp0020ResultForm req, List<Hajp0020Dto> list) {
        Hajp0020Dto dto = null;
        List<Hajp0020Dto> listTmp = new ArrayList<Hajp0020Dto>();
        int iLoopCnt = 0;
        if (list != null && list.size() > 0) {
            iLoopCnt = list.size() > RECORD_MAX ? RECORD_MAX : list.size();
        }

        for (int i = 0; i < iLoopCnt; i++) {
            dto = list.get(i);
            if (!dto.isUpdateFlag() && !dto.isChkFlg()) {
                if (StringUtils.EMPTY.equals(hatSruiKbn)) {
                    hatSruiKbn = "20";
                }
                // 該当行のJANコードを存在チェック.
                List<M007kijm> lsm007kijm = this.selectM007kijm(nhnDd, getFullShnCd(dto.getShnCd()));
                if (lsm007kijm == null || lsm007kijm.size() <= 0) {
                    errRes.addErrorInfo(CCMessageConst.MSG_KEY_VALIDATION_ERROR);
                    errRes.addErrorInfo("shnCd", CCMessageConst.MSG_KEY_NOT_REGISTERED_NO_PARAM);
                    errRes.addMessageInfo("indexRowError", dto.getRowNo());
                    return null;
                } else {
                    H010hnbhExecuteHNBH parameter = new H010hnbhExecuteHNBH();
                    parameter.setShnCd(getFullShnCd(dto.getShnCd()));
                    parameter.setKaiCd(kaisyaCd);
                    parameter.setJgbCd(jigyobuCd);
                    parameter.setTenCd(tenCd);
                    parameter.setHatSruiKbn(hatSruiKbn);
                    parameter.setHatDd(dto.getHatDd());
                    parameter.setNhnDd(req.getNhnDd());
                    parameter.setBin(dto.getBin().trim());
                    parameter.setbHatSu(false);
                    // 本部発注ファイル検索処理を行う

                    List<H010hnbh> lsH010hnbh = dao.selectMany("selectH010hnbh02", parameter, H010hnbh.class);
                    if (lsH010hnbh.size() > 0) {
                        errRes.addErrorInfo(CCMessageConst.MSG_KEY_VALIDATION_ERROR);
                        errRes.addErrorInfo("shnCd", CCMessageConst.MSG_KEY_INPUT_NOT_EDIT);
                        errRes.addErrorInfo("bin", CCMessageConst.MSG_KEY_INPUT_NOT_EDIT);
                        errRes.addMessageInfo("indexRowError", dto.getRowNo());
                        return null;
                    }
                }
            }
            // グリッド[削]が未選択（削除=="0"）の場合
            if (!dto.isChkFlg()) {
                if (!CCKubunConst.KBN_VAL_H_HAT_SRUI_KBN_HEADQUARTERS.equals(hatSruiKbn) 
                        && !StringUtils.EMPTY.equals(dto.getShnCd())) {
                    // 該当行のマスタ入数チェック
                    String tenCdSv = kaisyaCd + jigyobuCd + tenCd;
                    String bmnCd = "";
                    if (!CCStringUtil.isEmpty(req.getBmnCd())) {
                        bmnCd = jigyobuCd + req.getBmnCd();
                    }
                    List<M009msym> lsM009msym =
                            this.selectM009msym(nhnDd, getFullShnCd(dto.getShnCd()), tenCdSv, bmnCd);
                    if (lsM009msym.isEmpty() || TYPE_ACT_KBN_DELETED.equals(lsM009msym.get(0).getActKbn())) {
                        errRes.addErrorInfo(CCMessageConst.MSG_KEY_VALIDATION_ERROR);
                        errRes.addErrorInfo("shnCd", CCMessageConst.MSG_KEY_NOT_REGISTERED_NO_PARAM);
                        errRes.addMessageInfo("indexRowError", dto.getRowNo());
                        return null;
                    }
                }
                // 該当行の取引先コードチェック
                if (!CCStringUtil.isEmpty(dto.getTriCd())) {
                    List<M011trim> lsM011trim =
                            this.selectM011trim(nhnDd, CCStringUtil.frmtToriCode(dto.getTriCd(), false));
                    if (lsM011trim.isEmpty() || TYPE_ACT_KBN_DELETED.equals(lsM011trim.get(0).getActKbn())) {
                        errRes.addErrorInfo(CCMessageConst.MSG_KEY_VALIDATION_ERROR);
                        errRes.addErrorInfo("triCd", CCMessageConst.MSG_KEY_NOT_REGISTERED_NO_PARAM);
                        errRes.addMessageInfo("indexRowError", dto.getRowNo());
                        return null;
                    }
                }

                // 該当行の便Noチェック
                if (!dto.isChkFlg() && !dto.isUpdateFlag() && CCStringUtil.isEmpty(dto.getBin())) {
                    dto.setBin(FIRST_BIN_VALUE);
                }
                if (!(FIRST_BIN_VALUE.equals(dto.getBin()) || SECOND_BIN_VALUE.equals(dto.getBin()) || THIRD_BIN_VALUE.equals(dto.getBin()))) {
                    errRes.addErrorInfo(CCMessageConst.MSG_KEY_VALIDATION_ERROR);
                    errRes.addErrorInfo("bin", CCMessageConst.MSG_KEY_ERROR_STOOLS);
                    errRes.addMessageInfo("indexRowError", dto.getRowNo());
                    return null;
                } else {
                    // それ以外
                    // 定番商品/特売商品かどうかのチェックを行う
                    T001kksyChkTeiTok parameter = new T001kksyChkTeiTok();
                    parameter.setHatDd(nhnDd);
                    parameter.setJanCd(getFullShnCd(dto.getShnCd()));
                    parameter.setKaisyaCd(kaisyaCd);
                    parameter.setJigyobuCd(jigyobuCd);
                    String tenCdSv = kaisyaCd + jigyobuCd + tenCd;
                    parameter.setTenCd(tenCdSv);

                    List<T001kksyChkTeiTokResult> ls =
                            dao.selectMany("selectT001kksyT002kkms01", parameter, T001kksyChkTeiTokResult.class);
                    if (!CCStringUtil.isEmpty(dto.getTriCd())) {
                        List<M011trim> lsM011trim2 =
                                this.selectM011trim(nhnDd, CCStringUtil.frmtToriCode(dto.getTriCd(), false));
                        if (lsM011trim2 == null || lsM011trim2.isEmpty()) {
                            errRes.addErrorInfo(CCMessageConst.MSG_KEY_VALIDATION_ERROR);
                            if (ls.size() == 0) {
                                // 発注パターン情報（定番）が取引先マスター
                                errRes.addErrorInfo("shnCd", CCMessageConst.MSG_KEY_TRICD_NOT_REGISTER);
                                errRes.addErrorInfo("bin", CCMessageConst.MSG_KEY_TRICD_NOT_REGISTER);
                            } else {
                                errRes.addErrorInfo("shnCd", CCMessageConst.MSG_KEY_PRODUCT_MASTER_SALE_NOT_REGISTER);
                                errRes.addErrorInfo("bin", CCMessageConst.MSG_KEY_PRODUCT_MASTER_SALE_NOT_REGISTER);
                            }
                            errRes.addMessageInfo("indexRowError", dto.getRowNo());
                            return null;

                        } else {
                            boolean bRet = false;
                            String[] tmp = getHatPat(lsM011trim2, lsM011trim2.size(), dto.getBin());

                            for (int j = 0; j < 7; j++) {

                                String wHtPt = tmp[j];
                                if (!"0".equals(wHtPt)) {
                                    bRet = true;
                                    break;
                                }
                            }
                            if (!bRet) {
                                errRes.addErrorInfo(CCMessageConst.MSG_KEY_VALIDATION_ERROR);
                                if (ls.isEmpty()) {
                                    // 発注パターン情報（定番）が取引先マスター
                                    errRes.addErrorInfo("shnCd", CCMessageConst.MSG_KEY_TRICD_NOT_REGISTER);
                                    errRes.addErrorInfo("bin", CCMessageConst.MSG_KEY_TRICD_NOT_REGISTER);
                                } else {
                                    errRes.addErrorInfo("shnCd",
                                            CCMessageConst.MSG_KEY_PRODUCT_MASTER_SALE_NOT_REGISTER);
                                    errRes.addErrorInfo("bin",
                                            CCMessageConst.MSG_KEY_PRODUCT_MASTER_SALE_NOT_REGISTER);
                                }
                                errRes.addMessageInfo("indexRowError", dto.getRowNo());
                                return null;
                            }
                        }
                    }
                }
            }
            for (int h = i + 1; h < iLoopCnt; h++) {
                Hajp0020Dto record2 = list.get(h);
                if (!record2.isUpdateFlag() && record2.getShnCd().trim().equals(dto.getShnCd().trim())
                        && record2.getBin().equals(dto.getBin())) {
                    errRes.addErrorInfo(CCMessageConst.MSG_KEY_VALIDATION_ERROR);
                    errRes.addErrorInfo("shnCd", CCMessageConst.MSG_KEY_ERROR_STOOLS);
                    errRes.addErrorInfo("bin", CCMessageConst.MSG_KEY_ERROR_STOOLS);
                    if (!dto.isUpdateFlag()) {
                        errRes.addMessageInfo("indexRowError", dto.getRowNo());
                    }
                    return null;
                }
            }
            if (!StringUtils.EMPTY.equals(dto.getShnCd())) {
                // 表示項目の内容をゲット
                List<M007kijm> lsM007kijm = selectM007kijm(nhnDd, getFullShnCd(dto.getShnCd()));
                if (lsM007kijm.isEmpty() || TYPE_ACT_KBN_DELETED.equals(lsM007kijm.get(0).getActKbn())) {
                    dto.setMkrNmR("＊＊＊＊＊");
                    dto.setShnNm("＊＊＊＊＊＊＊＊＊＊＊＊＊＊");
                    dto.setKikakuNmA("**********");
                } else {
                    M007kijm m007kijm = lsM007kijm.get(0);
                    String shNm = m007kijm.getShnNm();
                    if (shNm.length() > 14) {
                        shNm = m007kijm.getShnNm().substring(0, 14);
                    }
                    dto.setShnNm(shNm);
                    dto.setKikakuNmA(m007kijm.getKikakuNmA());
                    // 取引先名称を取得する
                    List<M013makm> lsM013makm = this.selectM013makm(nhnDd, m007kijm.getMkrCd());
                    if (lsM013makm != null && lsM013makm.size() > 0) {
                        dto.setMkrNmR(lsM013makm.get(0).getMkrNmR());
                    } else {
                        dto.setMkrNmR("＊＊＊＊＊");
                    }
                }
            } else {
                dto.setMkrNmR("＊＊＊＊＊");
                dto.setShnNm("＊＊＊＊＊＊＊＊＊＊＊＊＊＊");
                dto.setKikakuNmA("**********");
            }

            if (StringUtils.EMPTY.equals(dto.getTriCd())) {
                dto.setTriNmR("＊＊＊＊＊");
            } else {
                // 取引先名称を取得する
                List<M011trim> lsM011trim =
                        this.selectM011trim(nhnDd, CCStringUtil.frmtToriCode(dto.getTriCd(), false));
                if (lsM011trim.isEmpty() || TYPE_ACT_KBN_DELETED.equals(lsM011trim.get(0).getActKbn())) {
                    dto.setTriNmR("＊＊＊＊＊");
                } else {
                    dto.setTriNmR(lsM011trim.get(0).getTriNmR());
                }
            }
            listTmp.add(dto);
        }
        return listTmp;
    }

    /**
     * Function save list data.
     * 
     * @param kaisyaCd kaisyaCd
     * @param nhnDd nhnDd
     * @param jigyobuCd jigyobuCd
     * @param tenCd tenCd
     * @param hatSruiKbn hatSruiKbn
     * @param list List<Hajp0020Dto>
     * @param req Hajp0020ResultForm
     */
    @Transactional
    public void execute(String kaisyaCd, String nhnDd, String jigyobuCd, String tenCd, String hatSruiKbn,
            List<Hajp0020Dto> list, Hajp0020ResultForm req) {
        for (int i = 0; i < list.size(); i++) {
            // Get record
            Hajp0020Dto record = new Hajp0020Dto();
            record = list.get(i);
            int iShKubun = 0;
            H010hnbhExecuteHNBH parameter = new H010hnbhExecuteHNBH();
            parameter.setShnCd(getFullShnCd(record.getShnCd()));
            parameter.setKaiCd(kaisyaCd);
            parameter.setJgbCd(jigyobuCd);
            parameter.setTenCd(tenCd);
            parameter.setHatSruiKbn(hatSruiKbn);
            parameter.setHatDd(record.getHatDd());

            parameter.setNhnDd(StringUtils.EMPTY);
            if (!CCStringUtil.isEmpty(nhnDd)) {
                parameter.setNhnDd(req.getNhnDd());
            }
            
            parameter.setBin(StringUtils.EMPTY);
            if (!CCStringUtil.isEmpty(record.getBin())) {
                parameter.setBin(record.getBin().trim());
            }

            parameter.setbHatSu(true);

            List<H010hnbh> h010hnbhList = dao.selectMany("selectH010hnbh02", parameter, H010hnbh.class);
            if (h010hnbhList.isEmpty()) {
                iShKubun = 1;
            } else {
                iShKubun = 2;
            }

            if (iShKubun == 1 && record.isChkFlg()) {
                iShKubun = 0;
            } else {
                if (iShKubun == 2 && record.isChkFlg()) {
                    iShKubun = 9;
                }
            }

            switch (iShKubun) {
            case 1:
                this.save(kaisyaCd, record.getHatDd(), jigyobuCd, tenCd, hatSruiKbn, record, req);
                break;
            case 2:
                this.update(kaisyaCd, record.getHatDd(), jigyobuCd, tenCd, hatSruiKbn, record, req);
                break;
            case 9:
                this.delete(kaisyaCd, record.getHatDd(), jigyobuCd, tenCd, hatSruiKbn, record, req);
                break;
            default:
                break;
            }
        }
    }

    /**
    * Function save list data.
    * 
    * @param kaisyaCd kaisyaCd
    * @param hatDd hatDd
    * @param jigyobuCd jigyobuCd
    * @param tenCd tenCd
    * @param hatSruiKbn hatSruiKbn
    * @param record Hajp0020Dto
    * @param req Hajp0020ResultForm
    * @return クライアントへ返却する結果
    */
    public Result save(String kaisyaCd, String hatDd, String jigyobuCd, String tenCd, String hatSruiKbn,
            Hajp0020Dto record, Hajp0020ResultForm req) {
        int effectUpdate = 0;
        DateTime dt = new DateTime();
        H010hnbh h010hnbh = new H010hnbh();
        BeanUtils.copyProperties(record, h010hnbh);
        h010hnbh.setShnCd(plucnv.toDbCode(record.getShnCd()));
        h010hnbh.setKaiCd(StringUtils.EMPTY.equals(kaisyaCd) ? BLANK : kaisyaCd);
        h010hnbh.setJgbCd(StringUtils.EMPTY.equals(jigyobuCd) ? BLANK : jigyobuCd);
        h010hnbh.setTenCd(StringUtils.EMPTY.equals(tenCd) ? BLANK : tenCd);
        h010hnbh.setHatDd(hatDd);
        h010hnbh.setNhnDd(req.getNhnDd());
        h010hnbh.setHatSruiKbn(hatSruiKbn);

        if (record.getBin() != null && !StringUtils.EMPTY.equals(record.getBin())) {
            h010hnbh.setBin(record.getBin());
        } else {
            h010hnbh.setBin(FIRST_BIN_VALUE);
        }

        h010hnbh.setHatTriDd(dt.toString(CCDateUtil.DATETIME_FORMAT_DATE_YYYYMMDD));
        h010hnbh.setHatTriTime(dt.toString(CCDateUtil.DATETIME_FORMAT_TIME));
        h010hnbh.setHatChuFlg(DEFAULT_HATCHU_FLG);
        h010hnbh.setHatSimeFlg(DEFAULT_SIME_FLG);
        h010hnbh.setCmnTantoCd(context.getTantoCode());
        h010hnbh.setCmnTermId(context.getTermName());
        h010hnbh.setCmnInsdd(dt.toString(CCDateUtil.DATETIME_FORMAT_DATE_YYYYMMDD));
        h010hnbh.setCmnUpddd(dt.toString(CCDateUtil.DATETIME_FORMAT_DATE_YYYYMMDD));
        h010hnbh.setCmnUpdtime(dt.toString(CCDateUtil.DATETIME_FORMAT_TIME));
        // [2015/06/16 WebMD_SS_V000.001対応 INS START]
        h010hnbh.setHatIfKbn(HAT_IF_KBN_WEBMD);
        // [2015/06/16 WebMD_SS_V000.001対応 INS END]
        // Insert record
        effectUpdate = this.h010hnbhMapper.insertSelective(h010hnbh);
        if (effectUpdate == 0) {
            throw new ChaseException(Messages.get(CCMessageConst.MSG_KEY_EXCEPTION_INSERT_NO_RECORD));
        }
        return created();

    }

    /**
     * Function update list data.
     * 
     * @param kaisyaCd kaisyaCd
     * @param hatDd hatDd
     * @param jigyobuCd jigyobuCd
     * @param tenCd tenCd
     * @param hatSruiKbn hatSruiKbn
     * @param record Hajp0020Dto
     * @param req Hajp0020ResultForm
     * @return クライアントへ返却する結果
     */
    public Result update(String kaisyaCd, String hatDd, String jigyobuCd, String tenCd, String hatSruiKbn,
            Hajp0020Dto record, Hajp0020ResultForm req) {
        int effectUpdate = 0;
        DateTime dt = new DateTime();
        H010hnbh h010hnbh = new H010hnbh();
        H010hnbhExample example = new H010hnbhExample();
        BeanUtils.copyProperties(record, h010hnbh);
        h010hnbh.setShnCd(plucnv.toDbCode(record.getShnCd()));
        h010hnbh.setHatTriDd(dt.toString(CCDateUtil.DATETIME_FORMAT_DATE_YYYYMMDD));
        h010hnbh.setHatTriTime(dt.toString(CCDateUtil.DATETIME_FORMAT_TIME));
        h010hnbh.setHatChuFlg(DEFAULT_HATCHU_FLG);
        h010hnbh.setHatSimeFlg(DEFAULT_SIME_FLG);
        h010hnbh.setCmnTantoCd(context.getTantoCode());
        h010hnbh.setCmnTermId(context.getTermName());
        h010hnbh.setCmnUpddd(dt.toString(CCDateUtil.DATETIME_FORMAT_DATE_YYYYMMDD));
        h010hnbh.setCmnUpdtime(dt.toString(CCDateUtil.DATETIME_FORMAT_TIME));

        // Add condition when update
        if (!StringUtils.EMPTY.equals(req.getNhnDd())) {
            example.createCriteria().andShnCdEqualTo(getFullShnCd(record.getShnCd())).andKaiCdEqualTo(kaisyaCd)
                    .andJgbCdEqualTo(jigyobuCd).andTenCdEqualTo(tenCd).andHatDdEqualTo(hatDd)
                    .andHatSruiKbnEqualTo(hatSruiKbn).andNhnDdEqualTo(req.getNhnDd()).andBinEqualTo(record.getBin());
        } else {
            example.createCriteria().andShnCdEqualTo(getFullShnCd(record.getShnCd())).andKaiCdEqualTo(kaisyaCd)
                    .andJgbCdEqualTo(jigyobuCd).andTenCdEqualTo(tenCd).andHatDdEqualTo(hatDd)
                    .andHatSruiKbnEqualTo(hatSruiKbn).andBinEqualTo(record.getBin());
        }
        // Execute Update
        effectUpdate = this.h010hnbhMapper.updateByExampleSelective(h010hnbh, example);
        if (effectUpdate == 0) {
            throw new ChaseException(Messages.get(CCMessageConst.MSG_KEY_EXCEPTION_UPDATE_NO_RECORD));
        }
        return ok();
    }

    /**
     * 引数とリクエスト(パラメータ)を条件にDBの該当データを削除(論理)する。
     * 
     * @param kaisyaCd kaisyaCd
     * @param hatDd hatDd
     * @param jigyobuCd jigyobuCd
     * @param tenCd tenCd
     * @param hatSruiKbn hatSruiKbn
     * @param record Hajp0020Dto
     * @param resultForm Hajp0020ResultForm
     * @return クライアントへ返却する結果
     */
    public Result delete(String kaisyaCd, String hatDd, String jigyobuCd, String tenCd, String hatSruiKbn,
            Hajp0020Dto record, Hajp0020ResultForm resultForm) {
        int effectUpdate = 0;
        DateTime dt = new DateTime();
        H010hnbh h010hnbh = new H010hnbh();
        
        h010hnbh.setHatSu(BigDecimal.ZERO);
        h010hnbh.setHatChuFlg(DEFAULT_HATCHU_FLG);
        h010hnbh.setHatSimeFlg(DEFAULT_SIME_FLG);
        h010hnbh.setCmnTermId(context.getTermName());
        h010hnbh.setCmnUpddd(dt.toString(CCDateUtil.DATETIME_FORMAT_DATE_YYYYMMDD));
        h010hnbh.setCmnUpdtime(dt.toString(CCDateUtil.DATETIME_FORMAT_TIME));

        H010hnbhExample example = new H010hnbhExample();
        // Add condition when update
        if (!StringUtils.EMPTY.equals(resultForm.getNhnDd())) {
            example.createCriteria().andShnCdEqualTo(getFullShnCd(record.getShnCd())).andKaiCdEqualTo(kaisyaCd)
                    .andJgbCdEqualTo(jigyobuCd).andTenCdEqualTo(tenCd).andHatDdEqualTo(hatDd)
                    .andHatSruiKbnEqualTo(hatSruiKbn).andBinEqualTo(record.getBin())
                    .andNhnDdEqualTo(resultForm.getNhnDd());
        } else {
            example.createCriteria().andShnCdEqualTo(getFullShnCd(record.getShnCd())).andKaiCdEqualTo(kaisyaCd)
                    .andJgbCdEqualTo(jigyobuCd).andTenCdEqualTo(tenCd).andHatDdEqualTo(hatDd)
                    .andHatSruiKbnEqualTo(hatSruiKbn).andBinEqualTo(record.getBin());
        }
        // Execute Update
        effectUpdate = this.h010hnbhMapper.updateByExampleSelective(h010hnbh, example);
        if (effectUpdate == 0) {
            throw new ChaseException(Messages.get(CCMessageConst.MSG_KEY_EXCEPTION_UPDATE_NO_RECORD));
        }
        return ok();
    }

    /**
     * Get kikaku name.
     * @param hatDd hatDd
     * @param janCd janCd
     * @param tenCd tenCd
     * @return Result
     */
    public Result getKikakuNm(String hatDd, String janCd, String tenCd) {
        DynamicForm dynamicForm = Form.form().bindFromRequest();
        String bmnCd = dynamicForm.get("bmnCd");

        Hajp0020ResultForm result = new Hajp0020ResultForm();

        M007kijmExample m007kijmExample = new M007kijmExample();
        M013makmExample m013makmExample = new M013makmExample();
        m007kijmExample.createCriteria().andJanCdEqualTo(getFullShnCd(janCd));
        m007kijmExample.setSearchDate(hatDd);
        List<M007kijm> m007kijmList = this.m007kijmMapper.selectByExample(m007kijmExample);

        if (m007kijmList.size() > 0 && !TYPE_ACT_KBN_DELETED.equals(m007kijmList.get(0).getActKbn())) {
            M007kijm m007kijm = m007kijmList.get(0);
            
            String kikaNm = m007kijm.getKikakuNmA();
            if (kikaNm.length() > 7) {
                m007kijm.setKikakuNmA(kikaNm.substring(0, 7));
            }

            result.setKikakuNm(m007kijm.getKikakuNmA());
            result.setShnNm(m007kijm.getShnNm());

            m013makmExample.createCriteria().andMkrCdEqualTo(m007kijm.getMkrCd());
            m013makmExample.setSearchDate(hatDd);
            List<M013makm> listMakm = this.m013makmMapper.selectByExample(m013makmExample);
            if (listMakm.size() > 0 && !TYPE_ACT_KBN_DELETED.equals(listMakm.get(0).getActKbn())) {
                result.setMkrCd(listMakm.get(0).getMkrCd());
                result.setMkrNm(listMakm.get(0).getMkrNmR());
            }

            List<M009msym> m009msymList = this.selectM009msym(hatDd, getFullShnCd(janCd), tenCd, bmnCd);

            if (m009msymList.isEmpty() || TYPE_ACT_KBN_DELETED.equals(m009msymList.get(0).getActKbn())) {
                result.setMstIriSu(null);
            } else {
                result.setMstIriSu(m009msymList.get(0).getHattyuIrisu());
            }

            m009msymList = this.selectM009msym(hatDd, getFullShnCd(janCd), tenCd, null);
            if (m009msymList.isEmpty() || TYPE_ACT_KBN_DELETED.equals(m009msymList.get(0).getActKbn())) {
                result.setTriCd(null);
                result.setHattyuIrisu(null);
                result.setGenk(null);
                result.setBaik(null);
            } else {
                result.setTriCd(m009msymList.get(0).getTriCd());
                result.setHattyuIrisu(m009msymList.get(0).getHattyuIrisu());
                result.setGenk(m009msymList.get(0).getGenk());
                result.setBaik(m009msymList.get(0).getBaik());
            }
        }

        return ok(Json.toJson(result));
    }

    /**
     * １週間の発注パターンを返します<BR>
     * @param m011trimList lsM011trim2
     * @param teiTokCd  iTeiTokCd
     * @param bin Bin
     * @return String[]
     */
    private String[] getHatPat(List<M011trim> m011trimList, int teiTokCd, String bin) {
        String[] hatPatArr = new String[7];
        for (int i = 0; i < 7; i++) {
            hatPatArr[i] = StringUtils.EMPTY;
        }

        M011trim m011trim = m011trimList.get(0);
        if (teiTokCd == 1 || teiTokCd == 2) {
            if (FIRST_BIN_VALUE.equals(bin)) {
                hatPatArr[0] = String.valueOf((Short) m011trim.getHatpMon1());
                hatPatArr[1] = String.valueOf((Short) m011trim.getHatpTue1());
                hatPatArr[2] = String.valueOf((Short) m011trim.getHatpWed1());
                hatPatArr[3] = String.valueOf((Short) m011trim.getHatpThu1());
                hatPatArr[4] = String.valueOf((Short) m011trim.getHatpFri1());
                hatPatArr[5] = String.valueOf((Short) m011trim.getHatpSat1());
                hatPatArr[6] = String.valueOf((Short) m011trim.getHatpSun1());
            } else if (SECOND_BIN_VALUE.equals(bin)) {
                hatPatArr[0] = String.valueOf((Short) m011trim.getHatpMon2());
                hatPatArr[1] = String.valueOf((Short) m011trim.getHatpTue2());
                hatPatArr[2] = String.valueOf((Short) m011trim.getHatpWed2());
                hatPatArr[3] = String.valueOf((Short) m011trim.getHatpThu2());
                hatPatArr[4] = String.valueOf((Short) m011trim.getHatpFri2());
                hatPatArr[5] = String.valueOf((Short) m011trim.getHatpSat2());
                hatPatArr[6] = String.valueOf((Short) m011trim.getHatpSun2());
            } else if (THIRD_BIN_VALUE.equals(bin)) {
                hatPatArr[0] = String.valueOf((Short) m011trim.getHatpMon3());
                hatPatArr[1] = String.valueOf((Short) m011trim.getHatpTue3());
                hatPatArr[2] = String.valueOf((Short) m011trim.getHatpWed3());
                hatPatArr[3] = String.valueOf((Short) m011trim.getHatpThu3());
                hatPatArr[4] = String.valueOf((Short) m011trim.getHatpFri3());
                hatPatArr[5] = String.valueOf((Short) m011trim.getHatpSat3());
                hatPatArr[6] = String.valueOf((Short) m011trim.getHatpSun3());
            }
        }
        return hatPatArr;
    }
}
