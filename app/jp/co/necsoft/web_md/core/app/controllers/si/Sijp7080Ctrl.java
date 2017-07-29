// /////////////////////////////////////////////////////////////////////
// Copyright(C) 2015 NEC Solution Innovators, Ltd. All Rights Reserved.
// /////////////////////////////////////////////////////////////////////

/*
 * ====================================================================
 * 
 * 機能名称 :オリジナル商品品振確定指示画面
 * 
 * 改版履歴
 * 
 * Rev. 改版年月日 改版者名 内容
 * 
 * 1.0 2015.06.09 NECVN 新規作成
 * 
 * ===================================================================
 */

package jp.co.necsoft.web_md.core.app.controllers.si;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import javax.inject.Inject;

import jp.co.necsoft.web_md.common.ChaseException;
import jp.co.necsoft.web_md.common.biz.IPluConverter;
import jp.co.necsoft.web_md.common.mybatis.dao.MyBatisSqlMapDao;
import jp.co.necsoft.web_md.core.app.dto.si.Sijp7080DetailDto;
import jp.co.necsoft.web_md.core.app.dto.si.Sijp7080Dto;
import jp.co.necsoft.web_md.core.app.dto.si.Sijp7080Param;
import jp.co.necsoft.web_md.core.app.dto.si.Sijp7080ResultRecord;
import jp.co.necsoft.web_md.core.app.forms.si.Sijp7080CondForm;
import jp.co.necsoft.web_md.core.app.forms.si.Sijp7080ResultForm;
import jp.co.necsoft.web_md.core.app.forms.si.Sijp7080UpdateResultForm;
import jp.co.necsoft.web_md.core.common.CCKubunConst;
import jp.co.necsoft.web_md.core.common.CCMessageConst;
import jp.co.necsoft.web_md.core.common.CCSecurityAuthenticator;
import jp.co.necsoft.web_md.core.common.CCSystemContext;
import jp.co.necsoft.web_md.core.common.forms.ErrorInfo;
import jp.co.necsoft.web_md.core.common.forms.ErrorResponse;
import jp.co.necsoft.web_md.core.common.mybatis.dao.H701ohatMapper;
import jp.co.necsoft.web_md.core.common.mybatis.dao.X007kbnmMapper;
import jp.co.necsoft.web_md.core.common.mybatis.dto.H701ohat;
import jp.co.necsoft.web_md.core.common.mybatis.dto.H701ohatExample;
import jp.co.necsoft.web_md.core.common.mybatis.dto.X007kbnm;
import jp.co.necsoft.web_md.core.common.mybatis.dto.X007kbnmExample;
import jp.co.necsoft.web_md.core.common.utils.CCCheckExistsUtil;
import jp.co.necsoft.web_md.core.common.utils.CCDateUtil;
import jp.co.necsoft.web_md.core.common.utils.CCNumberUtil;
import jp.co.necsoft.web_md.core.common.utils.CCStringUtil;
import jp.co.necsoft.web_md.core.common.utils.DataUtil;

import org.apache.commons.lang3.StringUtils;
import org.mybatis.guice.transactional.Transactional;
import org.springframework.beans.BeanUtils;

import play.data.Form;
import play.i18n.Messages;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;

/**
 * オリジナル商品品振確定指示画面のControllerクラス
 *
 */
@Security.Authenticated(CCSecurityAuthenticator.class)
public class Sijp7080Ctrl extends Controller {
    /** Delete flag normal value  */
    private static final String DELETE_FLG_NORMAL = "0";
    /** Max app hin su bigdecimal  */
    private static final BigDecimal MAX_APP_HIN_SU = BigDecimal.valueOf(99999.9);
    
    /**  コード */
    private static final String X007_CD = "1";
    
    /** 削除 */
    private static final String TYPE_ACT_KBN_DELETED = "9";
    
    /** check exists util */
    @Inject
    private CCCheckExistsUtil ccCheckExistsUtil;
    
    /** System Context */
    @Inject
    private CCSystemContext context;
    
    /** Error Response Object */
    @Inject
    private ErrorResponse errRes;
    
    /** MyBatis Sql Map Dao */
    @Inject
    private MyBatisSqlMapDao dao;
    
    /** Data Util */
    @Inject
    private DataUtil dataUtil;
    
    /** IPlu Converter */
    @Inject
    private IPluConverter plucnv;
    
    /** x007kbnm Mapper */
    @Inject
    private X007kbnmMapper x007kbnmMapper;
    
    /** H701ohat table's Mapper */
    @Inject
    private H701ohatMapper h701ohatMapper;
    
    /**
     * ページを初期化する
     * 
     * @return クライアントへ返却する結果
     */
    public Result init() {
        Sijp7080CondForm form = new Sijp7080CondForm();
        String sUnyoDate = context.getUnyoDate();
        String kaisyaCd = context.getDefaultKaisyaCd();
        
        // 画面項目[事業部]の設定
        form.setJigyobuCd(dataUtil.getNewestJigyobuCd(kaisyaCd, sUnyoDate));
        // 画面項目[承認区分]の設定
        form.setShouninKbn(getX007Value());
        
        List<Sijp7080CondForm> result = new ArrayList<Sijp7080CondForm>();
        result.add(form);
        return ok(Json.toJson(result));
    }
    
    /**
    * 引数とリクエスト(パラメータ)を条件にDBからデータ(複数件)を検索し返却する。
    * @param hatDayFr 発注日開始
    * @param hatDayTo 発注日終了
    * @param shouninKbn 品振承認区分
    * @param jigyobuCd 事業部コード
    * @return クライアントへ返却する結果
    */
    public Result query(String hatDayFr, String hatDayTo, String shouninKbn, String jigyobuCd) {
        Form<Sijp7080CondForm> emptyForm = new Form<Sijp7080CondForm>(Sijp7080CondForm.class);
        Form<Sijp7080CondForm> reqForm = emptyForm.bindFromRequest();
        if (reqForm.hasErrors()) {
            errRes.addErrorInfo(CCMessageConst.MSG_KEY_VALIDATION_ERROR);
            errRes.addErrorInfo(reqForm.errors());
            return badRequest(Json.toJson(errRes));
        } else {
            Sijp7080CondForm req = reqForm.get();
            req.setHatDayFr(hatDayFr);
            req.setHatDayTo(hatDayTo);
            req.setShouninKbn(shouninKbn);
            req.setJigyobuCd(jigyobuCd);
            
            String sUnyoDate = context.getUnyoDate();
            
            // 入力データチェック
            if (!checkCond(req)) {
                ErrorInfo error = this.errRes.getErrorInfo(CCMessageConst.MSG_KEY_VALIDATION_ERROR);
                this.errRes.getErrors().add(0, error);
                return badRequest(Json.toJson(errRes));
            }
            
            // 画面最大表示明細数の取得
            if (!CCCheckExistsUtil.existMaxRecordNumber(req.getMaxRecordNumber(), errRes)) {
                // Return error to client
                return badRequest(Json.toJson(errRes));
            }
            
            Integer maxRecordNumber = Integer.parseInt(req.getMaxRecordNumber());
            // オリジナル商品発注データから検索条件に沿ったデータを抽出する。
            Sijp7080Param sijp7080Param = new Sijp7080Param();
            BeanUtils.copyProperties(req, sijp7080Param);
            sijp7080Param.setUnyoDate(sUnyoDate);
            sijp7080Param.setMaxRecordNumber(maxRecordNumber + 1 + StringUtils.EMPTY);
            
            List<Sijp7080DetailDto> sijp7080DetailDtos =
                    dao.selectMany("selectSijp7080Data", sijp7080Param, Sijp7080DetailDto.class);
            if (sijp7080DetailDtos.size() == 0) {
                return notFound();
            }
            // データが存在する場合
            // 画面最大表示明細数のチェックを行う。
            if (sijp7080DetailDtos.size() > maxRecordNumber) {
                // 最大表示数を超えたので、条件を追加し、再実行してください。
                errRes.addErrorInfo(CCMessageConst.MSG_KEY_OVER_MAX_NUMBER_RECORDS);
                return badRequest(Json.toJson(errRes));
            }
            
            List<Sijp7080ResultForm> result = new ArrayList<Sijp7080ResultForm>();
            TreeMap<String, Sijp7080Dto> dataMap = new TreeMap<String, Sijp7080Dto>();
            for (Sijp7080DetailDto sijp7080DetailDto : sijp7080DetailDtos) {
                String janCd = sijp7080DetailDto.getJanCd();
                String mkrCd = sijp7080DetailDto.getMkdCd();
                String key = janCd + mkrCd;
                Sijp7080Dto sijp7080Obj = dataMap.get(key);
                
                if (sijp7080Obj == null) {
                    sijp7080Obj = new Sijp7080Dto();
                    sijp7080Obj.setHatSu(BigDecimal.ZERO);
                    sijp7080Obj.setAppHinSu(BigDecimal.ZERO);
                    sijp7080Obj.setZikSu(BigDecimal.ZERO);
                    sijp7080Obj.setGenk(BigDecimal.ZERO);
                    sijp7080Obj.setBaik(0);
                    sijp7080Obj.setDetailList(new ArrayList<Sijp7080DetailDto>());
                }
                
                sijp7080Obj.setJanCd(sijp7080DetailDto.getJanCd());
                sijp7080Obj.setMkdCd(sijp7080DetailDto.getMkdCd());
                sijp7080Obj.setShnNm(sijp7080DetailDto.getShnNm());
                
                sijp7080Obj.setHatSu(sijp7080Obj.getHatSu().add(sijp7080DetailDto.getHatSu()));
                sijp7080Obj.setAppHinSu(sijp7080Obj.getAppHinSu().add(sijp7080DetailDto.getAppHinSu()));
                sijp7080Obj.setZikSu(sijp7080Obj.getZikSu().add(sijp7080DetailDto.getZikSu()));
                sijp7080Obj.setGenk(sijp7080Obj.getGenk().add(sijp7080DetailDto.getGenk()));
                sijp7080Obj.setBaik(sijp7080Obj.getBaik() + sijp7080DetailDto.getBaik());
                sijp7080Obj.getDetailList().add(sijp7080DetailDto);
                dataMap.put(key, sijp7080Obj);
            }
            
            for (Map.Entry<String, Sijp7080Dto> entry : dataMap.entrySet()) {
                Sijp7080ResultForm sijp7080ResultForm = new Sijp7080ResultForm();
                BeanUtils.copyProperties(entry.getValue(), sijp7080ResultForm);
                sijp7080ResultForm.setJanCd(plucnv.toDispCode(sijp7080ResultForm.getJanCd(), null));
                result.add(sijp7080ResultForm);
            }
            
            return ok(Json.toJson(result));
        }
    }
    
    /**
     * 引数とリクエスト(ボディ)からレコードを編集しDBの該当データを更新(論理)する。
     * 
     * @param hatDayFr 発注日開始
     * @param hatDayTo 発注日終了
     * @param shouninKbn 品振承認区分
     * @param jigyobuCd 事業部コード
     * @return クライアントへ返却する結果
     */
    @Transactional
    public Result update(String hatDayFr, String hatDayTo, String shouninKbn, String jigyobuCd) {
        Form<Sijp7080UpdateResultForm> emptyForm = new Form<Sijp7080UpdateResultForm>(Sijp7080UpdateResultForm.class);
        Form<Sijp7080UpdateResultForm> reqForm = emptyForm.bindFromRequest();
        if (reqForm.hasErrors()) {
            errRes.addErrorInfo(CCMessageConst.MSG_KEY_VALIDATION_ERROR);
            errRes.addErrorInfo(reqForm.errors());
            return badRequest(Json.toJson(errRes));
        } else {
            Sijp7080UpdateResultForm req = reqForm.get();
            // Check data
            if (!processCheckData(req)) {
                return badRequest(Json.toJson(errRes));
            }
            List<Sijp7080ResultRecord> rows = req.getRecords();
            for (Sijp7080ResultRecord sijp7080ResultRecord : rows) {
                if (sijp7080ResultRecord.getDetailList() != null && !sijp7080ResultRecord.getDetailList().isEmpty()) {
                    for (Entry<String, Sijp7080DetailDto> entry : sijp7080ResultRecord.getDetailList().entrySet()) {
                        Sijp7080DetailDto sijp7080DetailDto = entry.getValue();
                        H701ohat h701ohat = new H701ohat();
                        BigDecimal appHinSuValue = sijp7080DetailDto.getAppHinSu();
                        h701ohat.setAppHinSu(appHinSuValue);
                        if (appHinSuValue.equals(BigDecimal.ZERO)) {
                            h701ohat.setHinAppFlg(CCKubunConst.KBN_VAL_N_HIN_APP_FLG_DENY);
                        } else {
                            if (CCKubunConst.KBN_VAL_N_HIN_APP_FLG_APPROVE.equals(sijp7080DetailDto.getHinAppFlg())) {
                                h701ohat.setHinAppFlg(CCKubunConst.KBN_VAL_N_HIN_APP_FLG_NOT_CONFIRM);
                            } else {
                                h701ohat.setHinAppFlg(CCKubunConst.KBN_VAL_N_HIN_APP_FLG_APPROVE);
                            }
                        }
                        h701ohat.setCmnTantoCd(context.getTantoCode());
                        h701ohat.setCmnTermId(context.getTermName());
                        h701ohat.setCmnUpddd(CCDateUtil.getSysDateFormat(CCDateUtil.DATETIME_FORMAT_DATE_YYYYMMDD));
                        h701ohat.setCmnUpdtime(CCDateUtil.getSysDateFormat(CCDateUtil.DATETIME_FORMAT_TIME));
                        
                        H701ohatExample example = new H701ohatExample();
                        // update data
                        example.createCriteria()
                                .andJanCdEqualTo(plucnv.toDbCode(sijp7080ResultRecord.getJanCd()))
                                .andKaiCdEqualTo(sijp7080DetailDto.getKaiCd())
                                .andJigyobuCdEqualTo(jigyobuCd)
                                .andTenCdEqualTo(
                                        sijp7080DetailDto.getKaiCd() + jigyobuCd + sijp7080DetailDto.getTenCd())
                                .andHatDdEqualTo(sijp7080DetailDto.getHatDD())
                                .andNhnDdEqualTo(sijp7080DetailDto.getNhnDd())
                                .andHatSruiKbnEqualTo(sijp7080DetailDto.getHatSruiKbn())
                                .andBinEqualTo(sijp7080DetailDto.getBin());
                        
                        // Execute Update
                        if (this.h701ohatMapper.updateByExampleSelective(h701ohat, example) == 0) {
                            throw new ChaseException(Messages.get(CCMessageConst.MSG_KEY_EXCEPTION_UPDATE_NO_RECORD));
                        }
                    }
                }
            }
        }
        return ok();
    }
    
    /**
     * check validate 
     * @param req Request parameter
     * @return true : validate success; false : validate fail
     */
    private boolean checkCond(Sijp7080CondForm req) {
        /** basic check */
        // 桁数チェック
        // 事業部コードチェック
        if (!CCStringUtil.checkLength("jigyobuCd", req.getJigyobuCd(), 2, errRes)) {
            return false;
        }
        // 発注日チェック
        if (!CCDateUtil.checkFormatDate("hatDayFr", req.getHatDayFr(), errRes)) {
            return false;
        }
        if (!CCDateUtil.checkFormatDate("hatDayTo", req.getHatDayTo(), errRes)) {
            return false;
        }
        // 承認区分チェック
        if (!CCStringUtil.checkLength("shouninKbn", req.getShouninKbn(), 1, errRes)) {
            return false;
        }
        
        /** logic check */
        // 発注日チェック
        if (!CCDateUtil.checkDateFromTo("hatDayFr", req.getHatDayFr(), req.getHatDayTo(), errRes)) {
            // 画面項目[発注日終了] < 画面項目[発注日]　の場合
            return false;
        }
        // 承認区分チェック
        if (!ccCheckExistsUtil.existsKbn("shouninKbn", CCKubunConst.KBN_KEY_N_X007, req.getShouninKbn(), errRes)) {
            return false;
        }
        
        // 事業部コードチェック
        // 存在チェック
        if (!ccCheckExistsUtil.existsJigyobuCd("jigyobuCd", context.getUnyoDate(), req.getKaisyaCd(),
                req.getJigyobuCd(), errRes)) {
            return false;
        }
        
        return true;
    }
    
    /**
     * check validate for update action
     * @param req Request parameter
     * @return true : validate success; false : validate fail
     */
    private boolean processCheckData(Sijp7080UpdateResultForm req) {
        Boolean hasError = false;
        // 最小値/最大値チェック
        List<Sijp7080ResultRecord> rows = req.getRecords();
        if (rows != null && rows.size() > 0) {
            // check number in range
            for (Sijp7080ResultRecord sijp7080ResultRecord : rows) {
                // check number in range detail
                Map<String, Sijp7080DetailDto> sijp7080DetailDtos = sijp7080ResultRecord.getDetailList();
                if (sijp7080DetailDtos != null && sijp7080DetailDtos.size() > 0) {
                    for (Entry<String, Sijp7080DetailDto> entry : sijp7080DetailDtos.entrySet()) {
                        Sijp7080DetailDto sijp7080DetailDto = entry.getValue();
                        if (!CCNumberUtil.checkNumberInRange("appHinSu_" + sijp7080ResultRecord.getRowNo() + "_"
                                + sijp7080DetailDto.getRowNo(), sijp7080DetailDto.getAppHinSu(), BigDecimal.ZERO,
                                MAX_APP_HIN_SU, errRes)) {
                            return false;
                        }
                    }
                }
            }
            // check value
            for (Sijp7080ResultRecord sijp7080ResultRecord : rows) {
                // compare with hatsu for header
                if (sijp7080ResultRecord.getAppHinSu().compareTo(sijp7080ResultRecord.getHatSu()) > 0) {
                    errRes.addErrorInfo(CCMessageConst.MSG_KEY_VALIDATION_ERROR);
                    errRes.addErrorInfo("appHinSu_" + sijp7080ResultRecord.getRowNo(),
                            CCMessageConst.MSG_KEY_ERROR_SINAFUSU_HACCHUSU_GREATER);
                    hasError = true;
                } else // compare with ziksu for header
                if (sijp7080ResultRecord.getAppHinSu().compareTo(sijp7080ResultRecord.getZikSu()) > 0) {
                    errRes.addErrorInfo(CCMessageConst.MSG_KEY_VALIDATION_ERROR);
                    errRes.addErrorInfo("appHinSu_" + sijp7080ResultRecord.getRowNo(),
                            CCMessageConst.MSG_KEY_ERROR_SINAFUSU_ZAIKOSU_GREATER);
                    hasError = true;
                }
                
                // compare with hatsu for detail
                Map<String, Sijp7080DetailDto> sijp7080DetailDtos = sijp7080ResultRecord.getDetailList();
                if (sijp7080DetailDtos != null && sijp7080DetailDtos.size() > 0) {
                    for (Entry<String, Sijp7080DetailDto> entry : sijp7080DetailDtos.entrySet()) {
                        Sijp7080DetailDto sijp7080DetailDto = entry.getValue();
                        if (sijp7080DetailDto.getAppHinSu().compareTo(sijp7080DetailDto.getHatSu()) > 0) {
                            errRes.addErrorInfo(CCMessageConst.MSG_KEY_VALIDATION_ERROR);
                            errRes.addErrorInfo(
                                    "appHinSu_" + sijp7080ResultRecord.getRowNo() + "_" + sijp7080DetailDto.getRowNo(),
                                    CCMessageConst.MSG_KEY_ERROR_SINAFUSU_HACCHUSU_GREATER);
                            hasError = true;
                        } else // compare with ziksu for detail
                        if (sijp7080DetailDto.getAppHinSu().compareTo(sijp7080DetailDto.getZikSu()) > 0) {
                            errRes.addErrorInfo(CCMessageConst.MSG_KEY_VALIDATION_ERROR);
                            errRes.addErrorInfo(
                                    "appHinSu_" + sijp7080ResultRecord.getRowNo() + "_" + sijp7080DetailDto.getRowNo(),
                                    CCMessageConst.MSG_KEY_ERROR_SINAFUSU_ZAIKOSU_GREATER);
                            hasError = true;
                        }
                    }
                }
            }
        }
        return !hasError;
    }
    
    /**
     * get value of cd in x007
     * @return X007kbnm object has cd or null if not exist cd in x007
     */
    private String getX007Value() {
        X007kbnmExample x007kbnmExample = new X007kbnmExample();
        x007kbnmExample.createCriteria().andDeleteFlgEqualTo(DELETE_FLG_NORMAL)
                .andCdKbnEqualTo(CCKubunConst.KBN_KEY_N_X007).andActKbnNotEqualTo(TYPE_ACT_KBN_DELETED)
                .andCdEqualTo(X007_CD);
        List<X007kbnm> kbns = x007kbnmMapper.selectByExample(x007kbnmExample);
        if (kbns.isEmpty()) {
            return null;
        }
        return kbns.get(0).getCd();
    }
}
