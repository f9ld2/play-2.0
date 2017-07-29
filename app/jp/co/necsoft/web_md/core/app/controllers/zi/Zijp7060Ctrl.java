// /////////////////////////////////////////////////////////////////////
// Copyright(C) 2015 NEC Solution Innovators, Ltd. All Rights Reserved.
// /////////////////////////////////////////////////////////////////////

/*
 * ====================================================================
 * 
 * 機能名称 : 棚卸入力画面 * 
 * 改版履歴
 * 
 * Rev. 改版年月日 改版者名 内容
 * 
 * 1.0 2015.06.24 NECVN 新規作成
 * 
 * ===================================================================
 */

package jp.co.necsoft.web_md.core.app.controllers.zi;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import jp.co.necsoft.web_md.common.ChaseException;
import jp.co.necsoft.web_md.common.biz.IPluConverter;
import jp.co.necsoft.web_md.common.mybatis.dao.MyBatisSqlMapDao;
import jp.co.necsoft.web_md.core.app.dto.zi.Zijp7060Dto;
import jp.co.necsoft.web_md.core.app.dto.zi.Zijp7060Param;
import jp.co.necsoft.web_md.core.app.forms.zi.Zijp7060CondForm;
import jp.co.necsoft.web_md.core.app.forms.zi.Zijp7060ResultForm;
import jp.co.necsoft.web_md.core.common.CCMessageConst;
import jp.co.necsoft.web_md.core.common.CCSecurityAuthenticator;
import jp.co.necsoft.web_md.core.common.CCSystemContext;
import jp.co.necsoft.web_md.core.common.forms.ErrorResponse;
import jp.co.necsoft.web_md.core.common.mybatis.dao.Z000shntMapper;
import jp.co.necsoft.web_md.core.common.mybatis.dao.Z001chutMapper;
import jp.co.necsoft.web_md.core.common.mybatis.dao.Z002bmntMapper;
import jp.co.necsoft.web_md.core.common.mybatis.dto.Z000shnt;
import jp.co.necsoft.web_md.core.common.mybatis.dto.Z000shntExample;
import jp.co.necsoft.web_md.core.common.mybatis.dto.Z001chut;
import jp.co.necsoft.web_md.core.common.mybatis.dto.Z001chutExample;
import jp.co.necsoft.web_md.core.common.mybatis.dto.Z002bmnt;
import jp.co.necsoft.web_md.core.common.mybatis.dto.Z002bmntExample;
import jp.co.necsoft.web_md.core.common.utils.CCCheckExistsUtil;
import jp.co.necsoft.web_md.core.common.utils.CCDateUtil;
import jp.co.necsoft.web_md.core.common.utils.CCNumberUtil;
import jp.co.necsoft.web_md.core.common.utils.CCStringUtil;
import jp.co.necsoft.web_md.core.common.utils.DataUtil;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.mybatis.guice.transactional.Transactional;
import org.springframework.beans.BeanUtils;

import play.data.Form;
import play.i18n.Messages;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;

/**
 * 棚卸入力画面のControllerクラス
 *
 */
@Security.Authenticated(CCSecurityAuthenticator.class)
public class Zijp7060Ctrl extends Controller {
    /** tna yyyymm length */
    private static final int TNA_YYYYMM_LENGTH = 6;
    /** jigyobu code length */
    private static final int JIGYOBU_CD_LENGTH = 2;
    /** ten code length */
    private static final int TEN_CD_LENGTH = 3;
    /** Delete Flg value  */
    private static final String DELETE_FLG_ONE = "1";
    /** Delete Flg value  */
    private static final String DELETE_FLG_ZERO = "0";
    /** tna eos kbn value  */
    private static final String TNA_EOS_KBN_ZERO = "0";
    
    /** dao */
    @Inject
    private MyBatisSqlMapDao dao;
    /** errRes */
    @Inject
    private ErrorResponse errRes;
    /** context */
    @Inject
    private CCSystemContext context;
    /** Data Util */
    @Inject
    private DataUtil dataUtil;
    /** Check exist utility */
    @Inject
    private CCCheckExistsUtil ccCheckExistsUtil;
    /** IPlu Converter */
    @Inject
    private IPluConverter plucnv;
    /** Z000shnt table's Mapper */
    @Inject
    private Z000shntMapper z000shntMapper;
    /** Z001chut table's Mapper */
    @Inject
    private Z001chutMapper z001chutMapper;
    /** Z002bmnt table's Mapper */
    @Inject
    private Z002bmntMapper z002bmntMapper;
    
    /**
     * ページを初期化する
     * 
     * @return クライアントへ返却する結果
     */
    public Result init() {
        Zijp7060CondForm form = new Zijp7060CondForm();
        String sUnyoDate = context.getUnyoDate();
        String kaisyaCd = context.getDefaultKaisyaCd();
        String jigyobuCd = dataUtil.getNewestJigyobuCd(kaisyaCd, sUnyoDate);
        // 画面項目[事業部]の設定
        form.setJigyobuCd(jigyobuCd);
        // 画面項目[店舗]の設定
        form.setTenCd(dataUtil.getNewestTenCd(kaisyaCd, jigyobuCd, sUnyoDate));

        List<Zijp7060CondForm> result = new ArrayList<Zijp7060CondForm>();
        result.add(form);
        return ok(Json.toJson(result));
    }
    
    /**
     * 引数とリクエスト(パラメータ)を条件にDBからデータ(複数件)を検索し返却する。
     * @param jigyobuCd 事業部
     * @param tenCd 店舗
     * @param tnaUnyDd 棚卸実施年月日
     * @param tnaKbn 循環棚卸区分
     * @return クライアントへ返却する結果
     */
    public Result query(String jigyobuCd, String tenCd, String tnaUnyDd, String tnaKbn) {
        Form<Zijp7060CondForm> emptyForm = new Form<Zijp7060CondForm>(Zijp7060CondForm.class);
        Form<Zijp7060CondForm> reqForm = emptyForm.bindFromRequest();
        if (reqForm.hasErrors()) {
            errRes.addErrorInfo(CCMessageConst.MSG_KEY_VALIDATION_ERROR);
            errRes.addErrorInfo(reqForm.errors());
            return badRequest(Json.toJson(errRes));
        } else {
            Zijp7060CondForm req = reqForm.get();
            req.setJigyobuCd(jigyobuCd);
            req.setTnaUnyDd(tnaUnyDd);
            req.setTnaKbn(tnaKbn);
            req.setTenCd(tenCd);
            
            // 入力データチェック
            if (!checkCond(req)) {
                return badRequest(Json.toJson(errRes));
            }
            // 画面最大表示明細数の取得
            if (!CCCheckExistsUtil.existMaxRecordNumber(req.getMaxRecordNumber(), errRes)) {
                // Return error to client
                return badRequest(Json.toJson(errRes));
            }
             
            Integer maxRecordNumber = Integer.parseInt(req.getMaxRecordNumber());
            Integer selectRecordNumber = maxRecordNumber + 1;
            Zijp7060Param zijp7060Param = new Zijp7060Param();
            BeanUtils.copyProperties(req, zijp7060Param);
            zijp7060Param.setUnyoDate(context.getUnyoDate());
            zijp7060Param.setMaxRecordNumber(selectRecordNumber.toString());
            
            List<Zijp7060Dto> dtolist = dao.selectMany("selectZijp7060Data",
                    zijp7060Param, Zijp7060Dto.class);
            
            if (dtolist.size() == 0) {
                return notFound();
            }
            
            // データが存在する場合
            // 画面最大表示明細数のチェックを行う。
            if (dtolist.size() > maxRecordNumber) {
                // 最大表示数を超えたので、条件を追加し、再実行してください。
                errRes.addErrorInfo(CCMessageConst.MSG_KEY_OVER_MAX_NUMBER_RECORDS);
                return badRequest(Json.toJson(errRes));
            }
            
            int iLoopCnt = dtolist.size();
            for (int i = 0; i < iLoopCnt; i++) {
                Zijp7060Dto dto = dtolist.get(i);
                dto.setShnCd(plucnv.toDispCode(dto.getShnCd(), null));
                dto.setUpdateFlag(false);
            }
            
            List<Zijp7060ResultForm> resultList = new ArrayList<Zijp7060ResultForm>();
            Zijp7060ResultForm result = new Zijp7060ResultForm();
            
            result.setDtoList(dtolist);
            resultList.add(result);
            return ok(Json.toJson(resultList));
        }
    }
     
    /**
     * 引数とリクエスト(ボディ)からレコードを編集しDBの該当データを更新(論理)する。
     * @param jigyobuCd 事業部
     * @param tenCd 店舗
     * @param tnaUnyDd 棚卸実施年月日
     * @param tnaKbn 循環棚卸区分
     * @return クライアントへ返却する結果
     */
    public Result update(String jigyobuCd, String tenCd, String tnaUnyDd, String tnaKbn) {
        @SuppressWarnings({"unchecked", "rawtypes" })
        Form<Zijp7060ResultForm> emptyForm = new Form(Zijp7060ResultForm.class);
        Form<Zijp7060ResultForm> reqForm = emptyForm.bindFromRequest();
        if (reqForm.hasErrors()) {
            errRes.addErrorInfo(CCMessageConst.MSG_KEY_VALIDATION_ERROR);
            errRes.addErrorInfo(reqForm.errors());
            return badRequest(Json.toJson(errRes));
        }
        
        Zijp7060ResultForm req = reqForm.get();
        List<Zijp7060Dto> list =  req.getDtoList();
        
        // 画面最大表示明細数の取得
        if (!CCCheckExistsUtil.existMaxRecordNumber(req.getMaxRecordNumber(), errRes)) {
            // Return error to client
            return badRequest(Json.toJson(errRes));
        }
        
        execute(list, req);
        
        return ok(Json.toJson(errRes));
    }
    
    /**
     * Function save list data.
     * @param list List<Zijp7060Dto>
     * @param req Zijp7060ResultForm
     */
    @Transactional
    private void execute(List<Zijp7060Dto> list, Zijp7060ResultForm req) {
        int maxRecordNumber = Integer.valueOf(req.getMaxRecordNumber());
        int iLoopCnt = 0;
        Zijp7060Dto dto = null;
        if (list != null && list.size() > 0) {
            iLoopCnt = list.size() > maxRecordNumber ? maxRecordNumber : list.size();
        }
        
        for (int i = 0; i < iLoopCnt; i++) {
            dto = list.get(i);
            dto.setShnCd(plucnv.toDbCode(dto.getShnCd()));
            Z000shnt z000shnt = getZ000shnt(req, dto);
            
            if (dto.isChkFlg()) {
                if (null != z000shnt) {
                    z000shnt.setDeleteFlg(DELETE_FLG_ONE);
                    dto.setUriZaiSu(z000shnt.getUriZaiSu());
                    dto.setUriZaiKin(z000shnt.getUriZaiKin());
                    updateZ000shnt(z000shnt, req, dto);
                }
            } else {
                if (!dto.getUriZaiSu().equals(BigDecimal.ZERO)) {
                    BigDecimal genk = getGenk(req, dto.getShnCd());
                    dto.setUriZaiKin(dto.getUriZaiSu().multiply(genk));
                }
                
                //Z000商品別棚卸データに保存をチェックする。
                if (null == z000shnt) {
                    insertZ000shnt(z000shnt, req, dto);
                } else {
                    updateZ000shnt(z000shnt, req, dto);
                }
                
                //Z001中分類別棚卸データを登録する。
                Z001chut z001chut = getZ001chut(req);
                if (null == z001chut) {
                    //insertZ001chut(z001chut, req, dto);
                } else {
                    //updateZ001chut(z001chut, req, dto);
                }
                
                //Z002部門別棚卸データを登録する。
                Z002bmnt z002bmnt = getZ002bmnt(req);
                if (null == z002bmnt) {
                    //insertZ002bmnt(z002bmnt, req, dto);
                } else {
                    //updateZ002bmnt(z002bmnt, req, dto);
                }
            }
        }
    }
    
    /**
     * Function insert data.
     *
     * @param z000shnt Z000shnt
     * @param req Zijp7060ResultForm
     * @param dto Zijp7060Dto
     */
    private void insertZ000shnt(Z000shnt z000shnt, Zijp7060ResultForm req, Zijp7060Dto dto) {
        int effectUpdate = 0;
        DateTime dt = new DateTime();
        
        z000shnt = new Z000shnt();
        z000shnt.setTnaKbn(req.getTnaKbn());
        z000shnt.setTenCd(req.getKaisyaCd() + req.getJigyobuCd() + req.getTenCd());
        z000shnt.setShnCd(dto.getShnCd());
        z000shnt.setTanaNo(dto.getTanaNo());
        z000shnt.setKaisyaCd(req.getKaisyaCd());
        z000shnt.setJigyobuCd(req.getJigyobuCd());
        z000shnt.setUriZaiSu(dto.getUriZaiSu());
        z000shnt.setUriZaiKin(dto.getUriZaiKin());
        z000shnt.setDeleteFlg(DELETE_FLG_ZERO);
        
        if (!StringUtils.EMPTY.equals(req.getTnaUnyDd())) {
            z000shnt.setTnaUnyDd(req.getTnaUnyDd());
        }
        
        z000shnt.setCmnTantoCd(context.getTantoCode());
        z000shnt.setCmnTermId(context.getTermName());
        z000shnt.setCmnInsdd(dt.toString(CCDateUtil.DATETIME_FORMAT_DATE_YYYYMMDD));
        z000shnt.setCmnUpddd(dt.toString(CCDateUtil.DATETIME_FORMAT_DATE_YYYYMMDD));
        z000shnt.setCmnUpdtime(dt.toString(CCDateUtil.DATETIME_FORMAT_TIME));
        
        effectUpdate = z000shntMapper.insertSelective(z000shnt);
        if (effectUpdate == 0) {
            throw new ChaseException(Messages.get(CCMessageConst.MSG_KEY_EXCEPTION_INSERT_NO_RECORD));
        }
    }
    
    /**
     * Function insert data.
     *
     * @param z001chut Z001chut
     * @param req Zijp7060ResultForm
     * @param dto Zijp7060Dto
     */
    private void insertZ001chut(Z001chut z001chut, Zijp7060ResultForm req, Zijp7060Dto dto) {
        int effectUpdate = 0;
        DateTime dt = new DateTime();
        
        //QA: BMN_CD, CHU_BNR_CD
        z001chut = new Z001chut();
        z001chut.setTenCd(req.getKaisyaCd() + req.getJigyobuCd() + req.getTenCd());
        z001chut.setTnaEosKbn(req.getTnaKbn());
        z001chut.setKaisyaCd(req.getKaisyaCd());
        z001chut.setJigyobuCd(req.getJigyobuCd());
        
        if (!StringUtils.EMPTY.equals(req.getTnaUnyDd())) {
            z001chut.setTnaYyyymm(StringUtils.substring(req.getTnaUnyDd(), 0, TNA_YYYYMM_LENGTH));
        }
        
        z001chut.setZtnaUnyDd(req.getTanaNoFr());
        z001chut.setZtnaSu(dto.getUriZaiSu());
        z001chut.setZtnaKin(dto.getUriZaiKin());
        z001chut.setUriZaiSu(dto.getUriZaiSu());
        z001chut.setUriZaiKin(dto.getUriZaiKin());
        z001chut.setTnaSu(dto.getUriZaiSu());
        z001chut.setTnaKin(dto.getUriZaiKin());
        z001chut.setDeleteFlg(DELETE_FLG_ONE);
        
        z001chut.setCmnTantoCd(context.getTantoCode());
        z001chut.setCmnTermId(context.getTermName());
        z001chut.setCmnInsdd(dt.toString(CCDateUtil.DATETIME_FORMAT_DATE_YYYYMMDD));
        z001chut.setCmnUpddd(dt.toString(CCDateUtil.DATETIME_FORMAT_DATE_YYYYMMDD));
        z001chut.setCmnUpdtime(dt.toString(CCDateUtil.DATETIME_FORMAT_TIME));
        
        effectUpdate = z001chutMapper.insertSelective(z001chut);
        if (effectUpdate == 0) {
            throw new ChaseException(Messages.get(CCMessageConst.MSG_KEY_EXCEPTION_INSERT_NO_RECORD));
        }
    }
    
    /**
     * Function insert data.
     *
     * @param z002bmnt Z002bmnt
     * @param req Zijp7060ResultForm
     * @param dto Zijp7060Dto
     */
    private void insertZ002bmnt(Z002bmnt z002bmnt, Zijp7060ResultForm req, Zijp7060Dto dto) {
        int effectUpdate = 0;
        DateTime dt = new DateTime();
        
        //QA: BMN_CD, CHU_BNR_CD
        z002bmnt = new Z002bmnt();
        if (!StringUtils.EMPTY.equals(req.getTnaUnyDd())) {
            z002bmnt.setTnaYyyymm(StringUtils.substring(req.getTnaUnyDd(), 0, TNA_YYYYMM_LENGTH));
        }
        
        z002bmnt.setTnaEosKbn(DELETE_FLG_ZERO);
        z002bmnt.setKaisyaCd(req.getKaisyaCd());
        z002bmnt.setJigyobuCd(req.getJigyobuCd());
        z002bmnt.setTnaUnyDd(req.getTnaUnyDd());
        z002bmnt.setUriZaiSu(dto.getUriZaiSu());
        z002bmnt.setUriZaiGenk(dto.getUriZaiKin());
        z002bmnt.setDeleteFlg(DELETE_FLG_ZERO);
        
        z002bmnt.setCmnTantoCd(context.getTantoCode());
        z002bmnt.setCmnTermId(context.getTermName());
        z002bmnt.setCmnInsdd(dt.toString(CCDateUtil.DATETIME_FORMAT_DATE_YYYYMMDD));
        z002bmnt.setCmnUpddd(dt.toString(CCDateUtil.DATETIME_FORMAT_DATE_YYYYMMDD));
        z002bmnt.setCmnUpdtime(dt.toString(CCDateUtil.DATETIME_FORMAT_TIME));
        
        effectUpdate = z002bmntMapper.insertSelective(z002bmnt);
        if (effectUpdate == 0) {
            throw new ChaseException(Messages.get(CCMessageConst.MSG_KEY_EXCEPTION_INSERT_NO_RECORD));
        }
    }
    
    /**
     * Function update data.
     * 
     * @param z000shnt Z000shnt
     * @param req Zijp7060ResultForm
     * @param dto Zijp7060Dto
     */
    private void updateZ000shnt(Z000shnt z000shnt, Zijp7060ResultForm req, Zijp7060Dto dto) {
        z000shnt.setUriZaiSu(dto.getUriZaiSu());
        z000shnt.setUriZaiKin(dto.getUriZaiKin());
        Z000shntExample example = getZ000shntExample(req, dto);
        
        // Execute Update
        int effectUpdate = z000shntMapper.updateByExampleSelective(z000shnt, example);
        if (effectUpdate == 0) {
            throw new ChaseException(Messages.get(CCMessageConst.MSG_KEY_EXCEPTION_UPDATE_NO_RECORD));
        }
    }
    
    /**
     * Function update data.
     * 
     * @param z001chut Z001chut
     * @param req Zijp7060ResultForm
     * @param dto Zijp7060Dto
     */
    private void updateZ001chut(Z001chut z001chut, Zijp7060ResultForm req, Zijp7060Dto dto) {
        z001chut.setUriZaiSu(dto.getUriZaiSu());
        z001chut.setUriZaiKin(dto.getUriZaiKin());
        Z001chutExample example = getZ001chutExample(req);
        
        // Execute Update
        int effectUpdate = z001chutMapper.updateByExampleSelective(z001chut, example);
        if (effectUpdate == 0) {
            throw new ChaseException(Messages.get(CCMessageConst.MSG_KEY_EXCEPTION_UPDATE_NO_RECORD));
        }
    }
    
    /**
     * Function update data.
     * 
     * @param z002bmnt Z002bmnt
     * @param req Zijp7060ResultForm
     * @param dto Zijp7060Dto
     */
    private void updateZ002bmnt(Z002bmnt z002bmnt, Zijp7060ResultForm req, Zijp7060Dto dto) {
        z002bmnt.setUriZaiSu(dto.getUriZaiSu());
        z002bmnt.setUriZaiGenk(dto.getUriZaiKin());
        Z002bmntExample example = getZ002bmntExample(req);
        
        // Execute Update
        int effectUpdate = z002bmntMapper.updateByExampleSelective(z002bmnt, example);
        if (effectUpdate == 0) {
            throw new ChaseException(Messages.get(CCMessageConst.MSG_KEY_EXCEPTION_UPDATE_NO_RECORD));
        }
    }
    
    /**
     * get Z000shnt object
     * @param req Zijp7060ResultForm
     * @param dto Zijp7060Dto
     * @return Z000shnt
     */
    private Z000shnt getZ000shnt(Zijp7060ResultForm req, Zijp7060Dto dto) {
        Z000shntExample example = getZ000shntExample(req, dto);
        List<Z000shnt> list = z000shntMapper.selectByExample(example);
        return list.isEmpty() ? null : list.get(0);
    }
    
    /**
     * get Z001chut object
     * @param req Zijp7060ResultForm
     * @return Z001chut
     */
    private Z001chut getZ001chut(Zijp7060ResultForm req) {
        Z001chutExample example = getZ001chutExample(req);
        List<Z001chut> list = z001chutMapper.selectByExample(example);
        return list.isEmpty() ? null : list.get(0);
    }
    
    /**
     * get Z002bmnt object
     * @param req Zijp7060ResultForm
     * @return Z002bmnt
     */
    private Z002bmnt getZ002bmnt(Zijp7060ResultForm req) {
        Z002bmntExample example = getZ002bmntExample(req);
        List<Z002bmnt> list = z002bmntMapper.selectByExample(example);
        return list.isEmpty() ? null : list.get(0);
    }
    
    /**
     * get Z000shntExample
     * @param req Zijp7060ResultForm
     * @param dto Zijp7060Dto
     * @return Z000shntExample
     */
    private Z000shntExample getZ000shntExample(Zijp7060ResultForm req, Zijp7060Dto dto) {
        Z000shntExample example = new Z000shntExample();
        example.createCriteria()
            .andJigyobuCdEqualTo(req.getJigyobuCd())
            .andDeleteFlgEqualTo(DELETE_FLG_ZERO)
            .andKaisyaCdEqualTo(req.getKaisyaCd())
            .andTnaKbnEqualTo(req.getTnaKbn())
            .andTanaNoEqualTo(dto.getTanaNo())
            .andShnCdEqualTo(dto.getShnCd())
            .andTenCdEqualTo(req.getKaisyaCd() + req.getJigyobuCd() + req.getTenCd());
            
        if (!StringUtils.EMPTY.equals(req.getTnaUnyDd())) {
            example.createCriteria()
                .andTnaUnyDdEqualTo(req.getTnaUnyDd());
        }
        return example;
    }
    
    /**
     * get Z001chutExample
     * @param req Zijp7060ResultForm
     * @return Z001chutExample
     */
    private Z001chutExample getZ001chutExample(Zijp7060ResultForm req) {
        Z001chutExample example = new Z001chutExample();
        //QA : BMN_CD, CHU_BNR_CD 
        example.createCriteria()
            .andTenCdEqualTo(req.getKaisyaCd() + req.getJigyobuCd() + req.getTenCd())
            .andDeleteFlgEqualTo(DELETE_FLG_ZERO)
            .andTnaEosKbnEqualTo(TNA_EOS_KBN_ZERO)
            .andKaisyaCdEqualTo(req.getKaisyaCd())
            .andJigyobuCdEqualTo(req.getJigyobuCd());
        
        if (!StringUtils.EMPTY.equals(req.getTnaUnyDd())) {
            example.createCriteria()
                .andTnaYyyymmEqualTo(StringUtils.substring(req.getTnaUnyDd(), 0, TNA_YYYYMM_LENGTH));
        }
        return example;
    }
    
    /**
     * get Z002bmntExample
     * @param req Zijp7060ResultForm
     * @return Z002bmntExample
     */
    private Z002bmntExample getZ002bmntExample(Zijp7060ResultForm req) {
        Z002bmntExample example = new Z002bmntExample();
        //QA : BMN_CD, CHU_BNR_CD
        example.createCriteria()
            .andKaisyaCdEqualTo(req.getKaisyaCd())
            .andJigyobuCdEqualTo(req.getJigyobuCd())
            .andDeleteFlgEqualTo(DELETE_FLG_ZERO)
            .andTnaEosKbnEqualTo(TNA_EOS_KBN_ZERO);
        
        if (!StringUtils.EMPTY.equals(req.getTnaUnyDd())) {
            example.createCriteria()
                .andTnaYyyymmEqualTo(StringUtils.substring(req.getTnaUnyDd(), 0, TNA_YYYYMM_LENGTH));
        }
        return example;
    }
    
    /**
     * get genk object
     * @param req Zijp7060ResultForm
     * @param shnCd String
     * @return BigDecimal object
     */
    private BigDecimal getGenk(Zijp7060ResultForm req, String shnCd) {
        Map<String, String> params = new HashMap<String, String>();
        params.put("unyoDate", context.getUnyoDate());
        params.put("jigyobuCd", req.getJigyobuCd());
        params.put("kaisyaCd", req.getKaisyaCd());
        params.put("tenCd", req.getTenCd());
        params.put("shnCd", shnCd);
        
        BigDecimal genk = dao.selectOne("selectZijp7060Genk", params);
        
        return (null == genk) ? BigDecimal.ZERO : genk;
    }
    
    /**
     * 【「JANコード」・「便」入力時のデータ抽出】
     * @param shnCd ＪＡＮコード
     * @param jigyobuCd 事業部コード
     * @return クライアントへ返却する結果
     */
    public Result queryDetail(String jigyobuCd, String shnCd) {
        Map<String, String> params = new HashMap<String, String>();
        params.put("unyoDate", context.getUnyoDate());
        params.put("shnCd", plucnv.toDbCode(shnCd));
        params.put("jigyobuCd", jigyobuCd);
        
        String shnNm = dao.selectOne("selectZijp7060ShnNm", params);
        
        if (null == shnNm) {
            errRes.addErrorInfo(CCMessageConst.MSG_KEY_VALIDATION_ERROR);
            errRes.addErrorInfo("shnCd", CCMessageConst.MSG_KEY_CODE_NOT_EXIST);
            return badRequest(Json.toJson(errRes));
        }
        
        List<Zijp7060ResultForm> resultList = new ArrayList<Zijp7060ResultForm>();
        Zijp7060ResultForm result = new Zijp7060ResultForm();
        
        result.setShnNm(shnNm);
        resultList.add(result);
        return ok(Json.toJson(resultList));
    }
    
    /**
     * check validate 
     * @param req Request parameter
     * @return true : validate success; false : validate fail
     */
    private boolean checkCond(Zijp7060CondForm req) {
        String unyoDate = context.getUnyoDate();
        // 事業部コードチェック
        if (!CCStringUtil.checkLength("jigyobuCd", req.getJigyobuCd(), JIGYOBU_CD_LENGTH, errRes)) {
            return false;
        }
        
        // 存在チェック
        if (!ccCheckExistsUtil.existsJigyobuCd("jigyobuCd", unyoDate, req.getKaisyaCd(),
                req.getJigyobuCd(), errRes)) {
            return false;
        }
        
        // 店舗コードチェック
        if (!CCStringUtil.checkLength("tenCd", req.getTenCd(), TEN_CD_LENGTH, errRes)) {
            return false;
        }
        
        // 存在チェック
        if (!ccCheckExistsUtil.existsTenCd("tenCd", unyoDate,
                req.getKaisyaCd() + req.getJigyobuCd() + req.getTenCd(), errRes)) {
            return false;
        }
        
        // 棚卸実施日のチェック処理を行う。
        if (!CCStringUtil.isEmpty(req.getTnaUnyDd()) 
                && !CCDateUtil.checkFormatDate("tnaUnyDd", req.getTnaUnyDd(), errRes)) {
            return false;
        }
        
        //棚Noチェック
        if (!CCStringUtil.isEmpty(req.getTanaNoFr())
                && !CCNumberUtil.checkNumber("tanaNoFr", req.getTanaNoFr(), errRes)) {
            return false;
        }
        
        if (!CCStringUtil.isEmpty(req.getTanaNoTo()) 
                && !CCNumberUtil.checkNumber("tanaNoTo", req.getTanaNoTo(), errRes)) {
            return false;
        }
        
        if (!CCStringUtil.isEmpty(req.getTanaNoFr()) && !CCStringUtil.isEmpty(req.getTanaNoTo())) {
            Integer tanaNoFr = Integer.valueOf(req.getTanaNoFr());
            Integer tanaNoTo = Integer.valueOf(req.getTanaNoTo());
            
            if (!CCNumberUtil.checkNumberFromTo("tanaNoFr", tanaNoFr, tanaNoTo, errRes)) {
                return false;
            }
        }
        
        return true;
    }
}
