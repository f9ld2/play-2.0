// /////////////////////////////////////////////////////////////////////
// Copyright(C) 2015 NEC Solution Innovators, Ltd. All Rights Reserved.
// /////////////////////////////////////////////////////////////////////

/*
 * ====================================================================
 * 
 * 機能名称 : 棚卸情報入力 改版履歴
 * 
 * Rev. 改版年月日 改版者名 内容
 * 
 * 1.0 2014.03.14 CuongPV 新規作成
 * 
 * ===================================================================
 */

package jp.co.necsoft.web_md.core.app.controllers.zi;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

import jp.co.necsoft.web_md.common.ChaseException;
import jp.co.necsoft.web_md.common.mybatis.dao.MyBatisSqlMapDao;
import jp.co.necsoft.web_md.core.app.dto.zi.Zijp0030BmnDto;
import jp.co.necsoft.web_md.core.app.dto.zi.Zijp0030GaiDto;
import jp.co.necsoft.web_md.core.app.dto.zi.Zijp0030ParamExecute01;
import jp.co.necsoft.web_md.core.app.dto.zi.Zijp0030TenDto;
import jp.co.necsoft.web_md.core.app.forms.zi.Zijp0030CondForm;
import jp.co.necsoft.web_md.core.app.forms.zi.Zijp0030ResultForm;
import jp.co.necsoft.web_md.core.common.CCKubunConst;
import jp.co.necsoft.web_md.core.common.CCMessageConst;
import jp.co.necsoft.web_md.core.common.CCSecurityAuthenticator;
import jp.co.necsoft.web_md.core.common.CCSystemContext;
import jp.co.necsoft.web_md.core.common.forms.ErrorInfo;
import jp.co.necsoft.web_md.core.common.forms.ErrorResponse;
import jp.co.necsoft.web_md.core.common.mybatis.dao.M000kaimMapper;
import jp.co.necsoft.web_md.core.common.mybatis.dao.M001jgymMapper;
import jp.co.necsoft.web_md.core.common.mybatis.dao.M003bmnmMapper;
import jp.co.necsoft.web_md.core.common.mybatis.dao.M006tenmMapper;
import jp.co.necsoft.web_md.core.common.mybatis.dao.Z004tbmnMapper;
import jp.co.necsoft.web_md.core.common.mybatis.dao.Z031tbmnMapper;
import jp.co.necsoft.web_md.core.common.mybatis.dto.M000kaim;
import jp.co.necsoft.web_md.core.common.mybatis.dto.M000kaimExample;
import jp.co.necsoft.web_md.core.common.mybatis.dto.M001jgym;
import jp.co.necsoft.web_md.core.common.mybatis.dto.M001jgymExample;
import jp.co.necsoft.web_md.core.common.mybatis.dto.M003bmnm;
import jp.co.necsoft.web_md.core.common.mybatis.dto.M003bmnmExample;
import jp.co.necsoft.web_md.core.common.mybatis.dto.M006tenm;
import jp.co.necsoft.web_md.core.common.mybatis.dto.M006tenmExample;
import jp.co.necsoft.web_md.core.common.mybatis.dto.X001shmg;
import jp.co.necsoft.web_md.core.common.mybatis.dto.Z004tbmn;
import jp.co.necsoft.web_md.core.common.mybatis.dto.Z004tbmnExample;
import jp.co.necsoft.web_md.core.common.mybatis.dto.Z004tbmnExample.Criteria;
import jp.co.necsoft.web_md.core.common.mybatis.dto.Z031tbmn;
import jp.co.necsoft.web_md.core.common.utils.CCStringUtil;

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
*棚卸情報入力のControllerクラス
*
*/
@Security.Authenticated(CCSecurityAuthenticator.class)
public class Zijp0030Ctrl extends Controller {

    /** Max day in Feb  */
    private static final int MAX_DAY_IN_FEB = 29;
    /** Max days in others month */
    private static final int MAX_DAY_IN_OTHER_MONTH = 31;
    /** Max days in april, june, september, november month  */
    private static final int MAX_DAY_IN_APR_JUN_SEP_NOV = 30;
    /** 削除 */
    private static final String TYPE_ACT_KBN_DELETED = "9";
    /** Formatter for Date */
    private static final String DATETIME_FORMAT_DATE = "yyyyMMdd";
    /** Formatter for Time */
    private static final String DATETIME_FORMAT_TIME = "HHmmss";
    /** KEY_DUMMY_TEN_CD */
    private static final String KEY_DUMMY_TEN_CD = "cc.app.zi.zijp0030.dummy_ten_cd";
    /** KEY_MAX */
    private static final String KEY_MAX = "999";

    /** M000kaim Mapper */
    @Inject
    private M000kaimMapper m000kaimMapper;
    /** M001jgym Mapper */
    @Inject
    private M001jgymMapper m001jgymMapper;
    /** M006tenmm Mapper */
    @Inject
    private M006tenmMapper m006tenmMapper;
    /** M003bmnm Mapper */
    @Inject
    private M003bmnmMapper m003bmnmMapper;
    /** Z004tbmn Mapper */
    @Inject
    private Z004tbmnMapper z004tbmnMapper;
    /** Z031tbm Mapper */
    @Inject
    private Z031tbmnMapper z031tbmnMapper;
    /** errRes ERROR */
    @Inject
    private ErrorResponse errRes;
    /** context */
    @Inject
    private CCSystemContext context;
    /** mybatisDao Object */
    @Inject
    private MyBatisSqlMapDao mybatisDao;

    /**
    * 引数とリクエスト(パラメータ)を条件にDBからデータ(複数件)を検索し返却する。*
    *  @param kaisyaCd kaisyaCd
    *  @param jigyobuCd jigyobuCd
    *  @return クライアントへ返却する結果
     */
    public Result query(String kaisyaCd, String jigyobuCd) {
        Form<Zijp0030CondForm> emptyForm = new Form<Zijp0030CondForm>(Zijp0030CondForm.class);
        Form<Zijp0030CondForm> reqForm = emptyForm.bindFromRequest();

        if (reqForm.hasErrors()) {
            errRes.addErrorInfo(CCMessageConst.MSG_KEY_VALIDATION_ERROR);
            errRes.addErrorInfo(reqForm.errors());
            return badRequest(Json.toJson(errRes));
        }
        Zijp0030CondForm req = reqForm.get();
        req.setKaisyaCd(kaisyaCd);
        req.setJigyobuCd(jigyobuCd);

        if (!CCStringUtil.isEmpty(req.getTenCd()) && !CCStringUtil.isEmpty(req.getBmnCd())) {
            errRes.addErrorInfo(CCMessageConst.MSG_KEY_NOT_BELONG_TO_DEPART);
            return badRequest(Json.toJson(errRes));
        }

        if (CCStringUtil.isEmpty(req.getTenCd())) {
            req.setTenCd(KEY_MAX);
        }

        if (CCStringUtil.isEmpty(req.getBmnCd())) {
            req.setBmnCd(KEY_MAX);
        }

        String unyoDate = req.getUnyoDate();
        M000kaimExample kaimExample = new M000kaimExample();
        kaimExample.createCriteria().andKaisyaCdEqualTo(req.getKaisyaCd());
        kaimExample.setSearchDate(unyoDate);

        List<M000kaim> m000kaims = m000kaimMapper.selectByExample(kaimExample);
        if (m000kaims.size() == 0 || TYPE_ACT_KBN_DELETED.equals(m000kaims.get(0).getActKbn())) {
            errRes.addErrorInfo(CCMessageConst.MSG_KEY_RESULT_NOT_EXISTS);
            return badRequest(Json.toJson(errRes));
        }

        M001jgymExample jgymExample = new M001jgymExample();
        jgymExample.createCriteria().andKaisyaCdEqualTo(req.getKaisyaCd()).andJigyobuCdEqualTo(req.getJigyobuCd());
        jgymExample.setSearchDate(unyoDate);
        List<M001jgym> m001jgyms = m001jgymMapper.selectByExample(jgymExample);

        if (m001jgyms.size() == 0 || TYPE_ACT_KBN_DELETED.equals(m001jgyms.get(0).getActKbn())) {
            errRes.addErrorInfo(CCMessageConst.MSG_KEY_RESULT_NOT_EXISTS);
            return badRequest(Json.toJson(errRes));
        }

        if (!KEY_MAX.equals(req.getTenCd())) {
            M006tenmExample tenmExample = new M006tenmExample();
            tenmExample.createCriteria().andTenCdEqualTo(req.getKaisyaCd() + req.getJigyobuCd() + req.getTenCd());
            tenmExample.setSearchDate(unyoDate);
            List<M006tenm> m006tenms = m006tenmMapper.selectByExample(tenmExample);
            if (m006tenms.size() == 0 || TYPE_ACT_KBN_DELETED.equals(m006tenms.get(0).getActKbn())) {
                errRes.addErrorInfo(CCMessageConst.MSG_KEY_RESULT_NOT_EXISTS);
                return badRequest(Json.toJson(errRes));
            }

            String dammyTenpo = context.getContextProperty(KEY_DUMMY_TEN_CD);
            if (dammyTenpo == null) {
                errRes.addErrorInfo(CCMessageConst.MSG_KEY_DUMMY_TEN_CD_NULL);
                return badRequest(Json.toJson(errRes));
            }

            int tenCdVal = Integer.parseInt(req.getTenCd());
            int dammyTenpoVal = Integer.parseInt(dammyTenpo);
            if (tenCdVal >= dammyTenpoVal) {
                errRes.addErrorInfo(CCMessageConst.MSG_KEY_TEN_CD_GREATER_OR_EQUAL_DAMMY_TENPO);
                return badRequest(Json.toJson(errRes));
            }
        }
        if (!KEY_MAX.equals(req.getBmnCd())) {
            M003bmnmExample bmnmExample = new M003bmnmExample();
            bmnmExample.createCriteria().andBmnCdEqualTo(req.getJigyobuCd() + req.getBmnCd());
            bmnmExample.setSearchDate(unyoDate);
            List<M003bmnm> m003bmnms = m003bmnmMapper.selectByExample(bmnmExample);

            if (m003bmnms.size() == 0 || TYPE_ACT_KBN_DELETED.equals(m003bmnms.get(0).getActKbn())) {
                errRes.addErrorInfo(CCMessageConst.MSG_KEY_RESULT_NOT_EXISTS);
                return badRequest(Json.toJson(errRes));
            }
        }
        String tantoIP = req.getTantoCd() + context.getIPAddress();
        List<X001shmg> list = mybatisDao.selectMany("selectX001shmg", tantoIP, X001shmg.class);

        if (list.size() > 0) {
            if (list.get(0).getKeyCd().substring(0, 4).equals(req.getKaisyaCd() + req.getJigyobuCd())) {
                errRes.addErrorInfo(CCMessageConst.MSG_KEY_SHMG_NOT_EXISTS);
                return badRequest(Json.toJson(errRes));
            }
        }

        List<Zijp0030BmnDto> list1 = null;
        List<Zijp0030GaiDto> list2 = null;
        List<Zijp0030TenDto> list3 = null;

        if (KEY_MAX.equals(req.getBmnCd())) {
            String dammyTenpo = context.getContextProperty(KEY_DUMMY_TEN_CD);

            if (dammyTenpo == null) {
                errRes.addErrorInfo(CCMessageConst.MSG_KEY_IS_NULL_OR_EMPTY_DATA);
                return badRequest(Json.toJson(errRes));
            }
            Zijp0030ParamExecute01 paramExecute01 = new Zijp0030ParamExecute01();
            paramExecute01.setKaisyaCd(req.getKaisyaCd());
            paramExecute01.setJigyobuCd(req.getJigyobuCd());
            paramExecute01.setTenCd(req.getTenCd());
            paramExecute01.setDammytenpoCd(dammyTenpo);
            paramExecute01.setHakkoDay(unyoDate);
            list1 = mybatisDao.selectMany("selectz004tbmn01", paramExecute01, Zijp0030BmnDto.class);

            if (list1.size() == 0) {
                errRes.addErrorInfo(CCMessageConst.MSG_KEY_BUMON_NO_DATA);
                return badRequest(Json.toJson(errRes));
            }
            list2 = mybatisDao.selectMany("selectz004tbmn02", paramExecute01, Zijp0030GaiDto.class);

            if (list2.size() == 0) {
                errRes.addErrorInfo(CCMessageConst.MSG_KEY_BUMON_NO_DATA);
                return badRequest(Json.toJson(errRes));
            }

            list1 = prepareDataForZijp0030BmnGrid(list1, unyoDate);
//            list2 = prepareDataForZijp0030GaiGrid(list2);
        }
        
        if (!KEY_MAX.equals(req.getBmnCd())) {
            String dammyTenpo = context.getContextProperty(KEY_DUMMY_TEN_CD);

            if (dammyTenpo == null) {
                errRes.addErrorInfo(CCMessageConst.MSG_KEY_IS_NULL_OR_EMPTY_DATA);
                return badRequest(Json.toJson(errRes));
            }

            Zijp0030ParamExecute01 paramExecute01 = new Zijp0030ParamExecute01();
            paramExecute01.setKaisyaCd(req.getKaisyaCd());
            paramExecute01.setJigyobuCd(req.getJigyobuCd());
            paramExecute01.setTenCd(req.getTenCd());
            paramExecute01.setDammytenpoCd(dammyTenpo);
            paramExecute01.setHakkoDay(req.getUnyoDate());
            paramExecute01.setBmnCd(req.getBmnCd());
            list3 = mybatisDao.selectMany("selectz004tbmn03", paramExecute01, Zijp0030TenDto.class);
            if (list3.size() == 0) {
                errRes.addErrorInfo(CCMessageConst.MSG_KEY_BUMON_NO_DATA);
                return badRequest(Json.toJson(errRes));
            }
            list3 = prepareDataForZijp0030TenGrid(list3, unyoDate);
        }
        
        List<Zijp0030ResultForm> resultList = new ArrayList<Zijp0030ResultForm>();
        Zijp0030ResultForm resultForm = new Zijp0030ResultForm();
        if (!KEY_MAX.equals(req.getBmnCd())) {
            resultForm.setBmnCd(req.getBmnCd());
        }
        resultForm.setJigyobuCd(req.getJigyobuCd());
        resultForm.setKaisyaCd(req.getKaisyaCd());
        resultForm.setTenCd(req.getTenCd());
        resultForm.setZijp0030BmnDtos(list1);
        resultForm.setZijp0030GaiDtos(list2);
        resultForm.setZijp0030TenDtos(list3);
        resultForm.setInfoRes(errRes);
        resultList.add(resultForm);
        return ok(Json.toJson(resultList));
    }

    /**
    * 引数とリクエスト(ボディ)からレコードを編集しDBに登録(論理)する。*
    * @param kaisyaCd kaisyaCd
    * @param jigyobuCd jigyobuCd
    * @return クライアントへ返却する結果
    */
    public Result save(String kaisyaCd, String jigyobuCd) {
        return executeSaveLogic(kaisyaCd, jigyobuCd);
    }

    /**
     * 引数とリクエスト(ボディ)からレコードを編集しDBに登録する。
     * 
     * @param kaisyaCd kaisyaCd
     * @param jigyobuCd jigyobuCd
     * @return クライアントへ返却する結 果
     */
    @Transactional
    public Result executeSaveLogic(String kaisyaCd, String jigyobuCd) {
        DateTime dt = new DateTime();
        Form<Zijp0030ResultForm> emptyForm = new Form<Zijp0030ResultForm>(Zijp0030ResultForm.class);
        Form<Zijp0030ResultForm> reqForm = emptyForm.bindFromRequest();
        int rowEffect = 0;
        if (reqForm.hasErrors()) {
            errRes.addErrorInfo(CCMessageConst.MSG_KEY_VALIDATION_ERROR);
            errRes.addErrorInfo(reqForm.errors());
            return badRequest(Json.toJson(errRes));
        } else {
            Zijp0030ResultForm req = reqForm.get();
            req.setKaisyaCd(kaisyaCd);
            req.setJigyobuCd(jigyobuCd);

            if (CCStringUtil.isEmpty(req.getTenCd())) {
                req.setTenCd(KEY_MAX);
            }

            if (CCStringUtil.isEmpty(req.getBmnCd())) {
                req.setBmnCd(KEY_MAX);
            }
            String tantoIP = req.getTantoCd() + context.getIPAddress();
            List<X001shmg> list = mybatisDao.selectMany("selectX001shmg", tantoIP, X001shmg.class);
            if (list.size() > 0) {
                if (list.get(0).getKeyCd().substring(0, 4).equals(req.getKaisyaCd() + req.getJigyobuCd())) {
                    errRes.addErrorInfo(CCMessageConst.MSG_KEY_SHMG_NOT_EXISTS);
                    return badRequest(Json.toJson(errRes));
                }
            }
            
            List<Zijp0030BmnDto> listBmn = req.getZijp0030BmnDtos();
            List<Zijp0030TenDto> listTenCd = req.getZijp0030TenDtos();
            List<Zijp0030GaiDto> listGai = req.getZijp0030GaiDtos();
            boolean checkFlag = true;
            if (KEY_MAX.equals(req.getBmnCd())) {
                for (int i = 0; i < listBmn.size(); i++) {
                    Zijp0030BmnDto bmnDto = listBmn.get(i);
                    if ("".equals(bmnDto.getZeroZai())
                            || !("0".equals(bmnDto.getZeroZai()) || "1".equals(bmnDto.getZeroZai()))) {
                        checkFlag = false;
                        break;
                    }
                }
                if (!checkFlag) {
                    errRes.addErrorInfo(CCMessageConst.MSG_KEY_VALIDATION_ERROR);
                    errRes.addErrorInfo(CCMessageConst.MSG_KEY_INPUT_DATA_INVALID_0_1);
                    return badRequest(Json.toJson(errRes));
                }
                
                checkFlag = validateMonthWithBmnList(listBmn);
                if (!checkFlag) {
                    return badRequest(Json.toJson(errRes));
                }
                
                checkFlag = validateMonthWithGaiList(listGai);
                if (!checkFlag) {
                    return badRequest(Json.toJson(errRes));
                }
            } else if (!KEY_MAX.equals(req.getBmnCd())) {
                for (int i = 0; i < listTenCd.size(); i++) {
                    Zijp0030TenDto tenDto = listTenCd.get(i);
                    if ("".equals(tenDto.getZeroZai())
                            || !("0".equals(tenDto.getZeroZai()) || "1".equals(tenDto.getZeroZai()))) {
                        checkFlag = false;
                        break;
                    }
                }
                
                if (!checkFlag) {
                    errRes.addErrorInfo(CCMessageConst.MSG_KEY_INPUT_DATA_INVALID_0_1);
                    return badRequest(Json.toJson(errRes));
                }
                
                checkFlag = validateMonthWithTenCdList(listTenCd);
                if (!checkFlag) {
                    return badRequest(Json.toJson(errRes));
                }
            }

            String dammyTenpo = context.getContextProperty(KEY_DUMMY_TEN_CD);
            if (dammyTenpo == null) {
                errRes.addErrorInfo(CCMessageConst.MSG_KEY_IS_NULL_OR_EMPTY_INVENTORIES);
                return badRequest(Json.toJson(errRes));
            }
            
            Zijp0030ParamExecute01 paramExecute01 = new Zijp0030ParamExecute01();
            paramExecute01.setKaisyaCd(req.getKaisyaCd());
            paramExecute01.setJigyobuCd(req.getJigyobuCd());
            paramExecute01.setTenCd(req.getTenCd());
            paramExecute01.setDammytenpoCd(dammyTenpo);
            paramExecute01.setBmnCd(req.getBmnCd());
            
            List<Z004tbmn> z004tbmnList =
                    mybatisDao.selectMany("selectZ004tbmn04", paramExecute01, Z004tbmn.class);
            if (z004tbmnList.size() > 0) {
                Z004tbmnExample z004tbmnExample = new Z004tbmnExample();
                Criteria criteria = z004tbmnExample.createCriteria();
                if (KEY_MAX.equals(req.getTenCd())) {
                    String str = req.getKaisyaCd() + req.getJigyobuCd() + "000";
                    criteria.andTenCdGreaterThanOrEqualTo(str);
                    if (!"".equals(dammyTenpo)) {
                        criteria.andTenCdLessThan(req.getKaisyaCd() + req.getJigyobuCd() + dammyTenpo);
                    } else {
                        criteria.andTenCdLessThanOrEqualTo(req.getKaisyaCd() + req.getJigyobuCd() + KEY_MAX);
                    }
                } else {
                    criteria.andTenCdEqualTo(req.getKaisyaCd() + req.getJigyobuCd() + req.getTenCd());
                }

                if (!KEY_MAX.equals(req.getBmnCd())) {
                    criteria.andBmnCdEqualTo(req.getJigyobuCd() + req.getBmnCd());
                }
                
                rowEffect = z004tbmnMapper.deleteByExample(z004tbmnExample);
                if (rowEffect == 0) {
                    throw new ChaseException(Messages.get(CCMessageConst.MSG_KEY_EXCEPTION_DELETE_NO_RECORD));
                }
            }
            // BEGIN INSERT
            if (listBmn != null && listBmn.size() > 0) {
                for (int i = 0; i < listBmn.size(); i++) {
                    Zijp0030BmnDto dto = listBmn.get(i);
                    if (KEY_MAX.equals(req.getTenCd()) && KEY_MAX.equals(req.getBmnCd())) {
                        Zijp0030ParamExecute01 z004tbmn = new Zijp0030ParamExecute01();
                        
                        BeanUtils.copyProperties(dto, z004tbmn);
                        z004tbmn.setKaisyaCd(req.getKaisyaCd());
                        z004tbmn.setJigyobuCd(req.getJigyobuCd());
                        z004tbmn.setZeroZai("0");
                        z004tbmn.setCmnTantoCd(req.getTantoCd());
                        z004tbmn.setCmnTermId(context.getTermName());
                        z004tbmn.setCmnInsdd(dt.toString(DATETIME_FORMAT_DATE));
                        z004tbmn.setCmnUpddd(dt.toString(DATETIME_FORMAT_DATE));
                        z004tbmn.setCmnUpdtime(dt.toString(DATETIME_FORMAT_TIME));
                        z004tbmn.setHakkoDay(req.getUnyoDate());
                        z004tbmn.setDammytenpoCd(dammyTenpo);
                        
                        rowEffect = mybatisDao.insertOne("insertM004tbum05", z004tbmn);
                        if (rowEffect == 0) {
                            throw new ChaseException(Messages.get(CCMessageConst.MSG_KEY_EXCEPTION_INSERT_NO_RECORD));
                        }
                    } else if (!KEY_MAX.equals(req.getTenCd()) && KEY_MAX.equals(req.getBmnCd())) {
                        Z004tbmn z004tbmn = new Z004tbmn();
                        
                        BeanUtils.copyProperties(dto, z004tbmn);
                        z004tbmn.setTenCd(req.getKaisyaCd() + req.getJigyobuCd() + req.getTenCd());
                        z004tbmn.setKaisyaCd(req.getKaisyaCd());
                        z004tbmn.setJigyobuCd(req.getJigyobuCd());
                        z004tbmn.setCmnTantoCd(req.getTantoCd());
                        z004tbmn.setZeroZai("0");
                        z004tbmn.setCmnTermId(context.getTermName());
                        z004tbmn.setCmnInsdd(dt.toString(DATETIME_FORMAT_DATE));
                        z004tbmn.setCmnUpddd(dt.toString(DATETIME_FORMAT_DATE));
                        z004tbmn.setCmnUpdtime(dt.toString(DATETIME_FORMAT_TIME));
                        rowEffect = z004tbmnMapper.insertSelective(z004tbmn);
                        if (rowEffect == 0) {
                            throw new ChaseException(Messages.get(CCMessageConst.MSG_KEY_EXCEPTION_INSERT_NO_RECORD));
                        }
                    }
                }
            } else if (listTenCd != null && listTenCd.size() > 0) {
                // Z004tbmnMapperのinsertSelectiveを使用する。
                for (int i = 0; i < listTenCd.size(); i++) {
                    Zijp0030TenDto dto = listTenCd.get(i);
                    Z004tbmn z004tbmn = new Z004tbmn();
                    BeanUtils.copyProperties(dto, z004tbmn);
                    
                    z004tbmn.setBmnCd(req.getJigyobuCd() + req.getBmnCd());
                    z004tbmn.setKaisyaCd(req.getKaisyaCd());
                    z004tbmn.setJigyobuCd(req.getJigyobuCd());
                    z004tbmn.setZeroZai("0");
                    z004tbmn.setCmnTantoCd(req.getTantoCd());
                    z004tbmn.setCmnTermId(context.getTermName());
                    z004tbmn.setCmnInsdd(dt.toString(DATETIME_FORMAT_DATE));
                    z004tbmn.setCmnUpddd(dt.toString(DATETIME_FORMAT_DATE));
                    z004tbmn.setCmnUpdtime(dt.toString(DATETIME_FORMAT_TIME));
                    rowEffect = z004tbmnMapper.insertSelective(z004tbmn);
                    if (rowEffect == 0) {
                        throw new ChaseException(Messages.get(CCMessageConst.MSG_KEY_EXCEPTION_INSERT_NO_RECORD));
                    }
                }
            }

            if (KEY_MAX.equals(req.getBmnCd())) {
                paramExecute01 = new Zijp0030ParamExecute01();
                paramExecute01.setKaisyaCd(req.getKaisyaCd());
                paramExecute01.setJigyobuCd(req.getJigyobuCd());
                paramExecute01.setTenCd(req.getTenCd());
                paramExecute01.setDammytenpoCd(dammyTenpo);
                List<Z031tbmn> listTenCdGai =
                        mybatisDao.selectMany("selectZ031tbmn06", paramExecute01, Z031tbmn.class);
                if (listTenCdGai.size() > 0) {
                    rowEffect = mybatisDao.deleteOne("deleteZ031tbmn08", paramExecute01);
                    if (rowEffect == 0) {
                        throw new ChaseException(Messages.get(CCMessageConst.MSG_KEY_EXCEPTION_DELETE_NO_RECORD));
                    }
                }
                if (KEY_MAX.equals(req.getTenCd())) {
                    Zijp0030ParamExecute01 z031tbmn = new Zijp0030ParamExecute01();
                    Zijp0030GaiDto dto = listGai.get(0);
                    BeanUtils.copyProperties(dto, z031tbmn);
                    
                    z031tbmn.setBmnCd("00000");
                    z031tbmn.setKaisyaCd(req.getKaisyaCd());
                    z031tbmn.setJigyobuCd(req.getJigyobuCd());
                    z031tbmn.setCmnTantoCd(req.getTantoCd());
                    z031tbmn.setCmnTermId(context.getTermName());
                    z031tbmn.setCmnInsdd(dt.toString(DATETIME_FORMAT_DATE));
                    z031tbmn.setCmnUpddd(dt.toString(DATETIME_FORMAT_DATE));
                    z031tbmn.setCmnUpdtime(dt.toString(DATETIME_FORMAT_TIME));
                    z031tbmn.setHakkoDay(req.getUnyoDate());
                    z031tbmn.setDammytenpoCd(dammyTenpo);
                    rowEffect = mybatisDao.insertOne("insertM006tenm07", z031tbmn);
                   if (rowEffect == 0) {
                      throw new ChaseException(Messages.get(CCMessageConst.MSG_KEY_EXCEPTION_INSERT_NO_RECORD));
                   }
                } else if (!KEY_MAX.equals(req.getTenCd())) {
                    Z031tbmn z031tbmn = new Z031tbmn();
                    Zijp0030GaiDto dto = listGai.get(0);
                    BeanUtils.copyProperties(dto, z031tbmn);
                    z031tbmn.setTenCd(req.getKaisyaCd() + req.getJigyobuCd() + req.getTenCd());
                    z031tbmn.setBmnCd("00000");
                    z031tbmn.setKaisyaCd(req.getKaisyaCd());
                    z031tbmn.setJigyobuCd(req.getJigyobuCd());
                    z031tbmn.setCmnTantoCd(req.getTantoCd());
                    z031tbmn.setCmnTermId(context.getTermName());
                    z031tbmn.setCmnInsdd(dt.toString(DATETIME_FORMAT_DATE));
                    z031tbmn.setCmnUpddd(dt.toString(DATETIME_FORMAT_DATE));
                    z031tbmn.setCmnUpdtime(dt.toString(DATETIME_FORMAT_TIME));

                    rowEffect = z031tbmnMapper.insertSelective(z031tbmn);
                    if (rowEffect == 0) {
                        throw new ChaseException(Messages.get(CCMessageConst.MSG_KEY_EXCEPTION_INSERT_NO_RECORD));
                    }
                }
            }
            return created();
        }
    }

    /**
     * Validation data for ZIJP0030ResultForm form 
     * @param list1 list Zijp0030BmnDto
     * @param unyoDate unyoDate
     * @return list1 list Zijp0030BmnDto
     */
    private List<Zijp0030BmnDto> prepareDataForZijp0030BmnGrid(List<Zijp0030BmnDto> list1, String unyoDate) {
        List<String> bmnCdList = new ArrayList<String>();
        for (int i = 0; i < list1.size(); i++) {
            Zijp0030BmnDto dto = list1.get(i);
            bmnCdList.add(dto.getBmnCd());

            list1.get(i).setBmnCd(dto.getBmnCd());
            list1.get(i).setBmnNm("＊＊＊＊＊＊＊＊＊");

            if (CCKubunConst.KBN_VAL_M_BAI_KAN_KBN_RETAIL_METHOD.equals(dto.getBaiKanKbn())) {
                dto.setBaiKanKbn("売価還元法");
            } else if (CCKubunConst.KBN_VAL_M_BAI_KAN_KBN_MOVING_AVERAGE_METHOD.equals(dto.getBaiKanKbn())) {
                dto.setBaiKanKbn("移動平均原価法");
            } else if (CCKubunConst.KBN_VAL_M_BAI_KAN_KBN_COST_STOCK.equals(dto.getBaiKanKbn())) {
                dto.setBaiKanKbn("原価在庫");
            } else if (CCKubunConst.KBN_VAL_M_BAI_KAN_KBN_ZERO_COST.equals(dto.getBaiKanKbn())) {
                dto.setBaiKanKbn("０在庫");
            } else if (CCKubunConst.KBN_VAL_M_BAI_KAN_KBN_ZERO_COST_WITHOUT_PROFIT.equals(dto.getBaiKanKbn())) {
                dto.setBaiKanKbn("０在庫（荒利無し）");
            } else {
                dto.setBaiKanKbn("");
            }
        }

        // 部門名リストを取得する
        HashMap<String, Object> queryParam = new HashMap<String, Object>();
        queryParam.put("searchDate", unyoDate);
        queryParam.put("bmnCdList", bmnCdList);

        List<M003bmnm> bmnNmList = this.mybatisDao.selectMany("selectM003BmnmBmnNm01", queryParam, M003bmnm.class);
        if (bmnNmList != null && !bmnNmList.isEmpty()) {
            for (int i = 0; i < list1.size(); i++) {
                Zijp0030BmnDto dto = list1.get(i);
                for (M003bmnm m003bmnm : bmnNmList) {
                    if (m003bmnm.getBmnCd().equals(dto.getBmnCd())) {
                        if (!CCStringUtil.isEmpty(m003bmnm.getBmnNm())
                                && !TYPE_ACT_KBN_DELETED.equals(m003bmnm.getActKbn())) {
                            dto.setBmnNm(m003bmnm.getBmnNm());
                        }
                        break;
                    }
                }
            }
        }

        return list1;
    }

    /**
     * Validation data for ZIJP0030ResultForm form 
     * @param list1 list Zijp0030TenDto
     * @param unyoDate unyoDate
     * @return list1 list Zijp0030TenDto
     */
    private List<Zijp0030TenDto> prepareDataForZijp0030TenGrid(List<Zijp0030TenDto> list1, String unyoDate) {
        HashMap<String, Object> queryParam = new HashMap<String, Object>();
        List<String> tenCdList = new ArrayList<String>();
        queryParam.put("searchDate", unyoDate);
        queryParam.put("tenCdList", tenCdList);

        for (int i = 0; i < list1.size(); i++) {
            Zijp0030TenDto dto = list1.get(i);
            tenCdList.add(dto.getTenCd());
            list1.get(i).setTenCd(dto.getTenCd());
            list1.get(i).setTenNm("＊＊＊＊＊＊＊＊＊");

            if (CCKubunConst.KBN_VAL_M_BAI_KAN_KBN_RETAIL_METHOD.equals(dto.getBaiKanKbn())) {
                dto.setBaiKanKbn("売価還元法");
            } else if (CCKubunConst.KBN_VAL_M_BAI_KAN_KBN_MOVING_AVERAGE_METHOD.equals(dto.getBaiKanKbn())) {
                dto.setBaiKanKbn("移動平均原価法");
            } else if (CCKubunConst.KBN_VAL_M_BAI_KAN_KBN_COST_STOCK.equals(dto.getBaiKanKbn())) {
                dto.setBaiKanKbn("原価在庫");
            } else if (CCKubunConst.KBN_VAL_M_BAI_KAN_KBN_ZERO_COST.equals(dto.getBaiKanKbn())) {
                dto.setBaiKanKbn("０在庫");
            } else if (CCKubunConst.KBN_VAL_M_BAI_KAN_KBN_ZERO_COST_WITHOUT_PROFIT.equals(dto.getBaiKanKbn())) {
                dto.setBaiKanKbn("０在庫（荒利無し）");
            } else {
                dto.setBaiKanKbn("");
            }
        }

        // 店舗名リストを取得する
        List<M006tenm> tenNmList = this.mybatisDao.selectMany("selectM006TenmTenNm01", queryParam, M006tenm.class);
        if (tenNmList != null && !tenNmList.isEmpty()) {
            for (int i = 0; i < list1.size(); i++) {
                Zijp0030TenDto dto = list1.get(i);
                for (M006tenm m006tenm : tenNmList) {
                    if (m006tenm.getTenCd().equals(dto.getTenCd())) {
                        if (!CCStringUtil.isEmpty(m006tenm.getTenNm())
                                && !TYPE_ACT_KBN_DELETED.equals(m006tenm.getActKbn())) {
                            dto.setTenNm(m006tenm.getTenNm());
                        }
                        break;
                    }
                }
            }
        }

        return list1;
    }

    /**
     * Validation data for ZIJP0030ResultForm form 
     * @param listBmn list Zijp0030BmnDto
     * @return listBmn list Zijp0030BmnDto
     */
    private boolean validateMonthWithBmnList(List<Zijp0030BmnDto> listBmn) {
        boolean checkFlag = true;
        for (int i = 0; i < listBmn.size(); i++) {
            Zijp0030BmnDto bmnDto = listBmn.get(i);
            if (bmnDto.getApril() == null || StringUtils.EMPTY.equals(bmnDto.getApril().toString())) {
                errRes.addErrorInfo("april_" + i, CCMessageConst.MSG_KEY_INPUT_REQUIRED_ENTER);
                checkFlag = false;
            } else if (bmnDto.getApril().intValue() < 0 || bmnDto.getApril().intValue() > MAX_DAY_IN_APR_JUN_SEP_NOV) {
                errRes.addErrorInfo("april_" + i, CCMessageConst.MSG_KEY_030DAYS_INPUT_ERROR);
                checkFlag = false;
            }

            if (bmnDto.getMay() == null || StringUtils.EMPTY.equals(bmnDto.getMay().toString())) {
                errRes.addErrorInfo("may_" + i, CCMessageConst.MSG_KEY_INPUT_REQUIRED_ENTER);
                checkFlag = false;
            } else if (bmnDto.getMay().intValue() < 0 || bmnDto.getMay().intValue() > MAX_DAY_IN_OTHER_MONTH) {
                errRes.addErrorInfo("may_" + i, CCMessageConst.MSG_KEY_031DAYS_INPUT_ERROR);
                checkFlag = false;
            }

            if (bmnDto.getJune() == null || StringUtils.EMPTY.equals(bmnDto.getJune().toString())) {
                errRes.addErrorInfo("june_" + i, CCMessageConst.MSG_KEY_INPUT_REQUIRED_ENTER);
                checkFlag = false;
            } else if (bmnDto.getJune().intValue() < 0 || bmnDto.getJune().intValue() > MAX_DAY_IN_APR_JUN_SEP_NOV) {
                errRes.addErrorInfo("june_" + i, CCMessageConst.MSG_KEY_030DAYS_INPUT_ERROR);
                checkFlag = false;
            }

            if (bmnDto.getJuly() == null || StringUtils.EMPTY.equals(bmnDto.getJuly().toString())) {
                errRes.addErrorInfo("july_" + i, CCMessageConst.MSG_KEY_INPUT_REQUIRED_ENTER);
                checkFlag = false;
            } else if (bmnDto.getJuly().intValue() < 0 || bmnDto.getJuly().intValue() > MAX_DAY_IN_OTHER_MONTH) {
                errRes.addErrorInfo("july_" + i, CCMessageConst.MSG_KEY_031DAYS_INPUT_ERROR);
                checkFlag = false;
            }

            if (bmnDto.getAugust() == null || StringUtils.EMPTY.equals(bmnDto.getAugust().toString())) {
                errRes.addErrorInfo("august_" + i, CCMessageConst.MSG_KEY_INPUT_REQUIRED_ENTER);
                checkFlag = false;
            } else if (bmnDto.getAugust().intValue() < 0 || bmnDto.getAugust().intValue() > MAX_DAY_IN_OTHER_MONTH) {
                errRes.addErrorInfo("august_" + i, CCMessageConst.MSG_KEY_031DAYS_INPUT_ERROR);
                checkFlag = false;
            }

            if (bmnDto.getSeptember() == null || StringUtils.EMPTY.equals(bmnDto.getSeptember().toString())) {
                errRes.addErrorInfo("september_" + i, CCMessageConst.MSG_KEY_INPUT_REQUIRED_ENTER);
                checkFlag = false;
            } else if (bmnDto.getSeptember().intValue() < 0
                    || bmnDto.getSeptember().intValue() > MAX_DAY_IN_APR_JUN_SEP_NOV) {
                errRes.addErrorInfo("september_" + i, CCMessageConst.MSG_KEY_030DAYS_INPUT_ERROR);
                checkFlag = false;
            }

            if (bmnDto.getOctober() == null || StringUtils.EMPTY.equals(bmnDto.getOctober().toString())) {
                errRes.addErrorInfo("october_" + i, CCMessageConst.MSG_KEY_INPUT_REQUIRED_ENTER);
                checkFlag = false;
            } else if (bmnDto.getOctober().intValue() < 0 || bmnDto.getOctober().intValue() > MAX_DAY_IN_OTHER_MONTH) {
                errRes.addErrorInfo("october_" + i, CCMessageConst.MSG_KEY_031DAYS_INPUT_ERROR);
                checkFlag = false;
            }

            if (bmnDto.getNovember() == null || StringUtils.EMPTY.equals(bmnDto.getNovember().toString())) {
                errRes.addErrorInfo("november_" + i, CCMessageConst.MSG_KEY_INPUT_REQUIRED_ENTER);
                checkFlag = false;
            } else if (bmnDto.getNovember().intValue() < 0
                    || bmnDto.getNovember().intValue() > MAX_DAY_IN_APR_JUN_SEP_NOV) {
                errRes.addErrorInfo("november_" + i, CCMessageConst.MSG_KEY_030DAYS_INPUT_ERROR);
                checkFlag = false;
            }

            if (bmnDto.getDecember() == null || StringUtils.EMPTY.equals(bmnDto.getDecember().toString())) {
                errRes.addErrorInfo("december_" + i, CCMessageConst.MSG_KEY_INPUT_REQUIRED_ENTER);
                checkFlag = false;
            } else if (bmnDto.getDecember().intValue() < 0 || bmnDto.getDecember().intValue() > MAX_DAY_IN_OTHER_MONTH) {
                errRes.addErrorInfo("december_" + i, CCMessageConst.MSG_KEY_031DAYS_INPUT_ERROR);
                checkFlag = false;
            }

            if (bmnDto.getJanuary() == null || StringUtils.EMPTY.equals(bmnDto.getJanuary().toString())) {
                errRes.addErrorInfo("january_" + i, CCMessageConst.MSG_KEY_INPUT_REQUIRED_ENTER);
                checkFlag = false;
            } else if (bmnDto.getJanuary().intValue() < 0 || bmnDto.getJanuary().intValue() > MAX_DAY_IN_OTHER_MONTH) {
                errRes.addErrorInfo("january_" + i, CCMessageConst.MSG_KEY_031DAYS_INPUT_ERROR);
                checkFlag = false;
            }

            if (bmnDto.getFebruary() == null || StringUtils.EMPTY.equals(bmnDto.getFebruary().toString())) {
                errRes.addErrorInfo("february_" + i, CCMessageConst.MSG_KEY_INPUT_REQUIRED_ENTER);
                checkFlag = false;
            } else if (bmnDto.getFebruary().intValue() < 0 || bmnDto.getFebruary().intValue() > MAX_DAY_IN_FEB) {
                errRes.addErrorInfo("february_" + i, CCMessageConst.MSG_KEY_029DAYS_INPUT_ERROR);
                checkFlag = false;
            }

            if (bmnDto.getMarch() == null || StringUtils.EMPTY.equals(bmnDto.getMarch().toString())) {
                errRes.addErrorInfo("march_" + i, CCMessageConst.MSG_KEY_INPUT_REQUIRED_ENTER);
                checkFlag = false;
            } else if (bmnDto.getMarch().intValue() < 0 || bmnDto.getMarch().intValue() > MAX_DAY_IN_OTHER_MONTH) {
                errRes.addErrorInfo("march_" + i, CCMessageConst.MSG_KEY_031DAYS_INPUT_ERROR);
                checkFlag = false;
            }
        }
        
        if (!checkFlag) {
            ErrorInfo error = errRes.getErrorInfo(CCMessageConst.MSG_KEY_VALIDATION_ERROR);
            errRes.getErrors().add(0, error);
        }
        return checkFlag;
    }

    /**
     * Validation data for ZIJP0030ResultForm form 
     * @param listGai list Zijp0030GaiDto
     * @return listGai list Zijp0030GaiDto
     */
    private boolean validateMonthWithGaiList(List<Zijp0030GaiDto> listGai) {
        boolean checkFlag = true;
        for (int i = 0; i < listGai.size(); i++) {
            Zijp0030GaiDto gaiDto = listGai.get(i);
            if (!("0".equals(gaiDto.getApril().toString()) || "1".equals(gaiDto.getApril().toString()))) {
                errRes.addErrorInfo("april_inventory_" + i, CCMessageConst.MSG_KEY_INPUT_DATA_INVALID_1_0);
                checkFlag = false;
            }
            
            if (!("0".equals(gaiDto.getMay().toString()) || "1".equals(gaiDto.getMay().toString()))) {
                errRes.addErrorInfo("may_inventory_" + i, CCMessageConst.MSG_KEY_INPUT_DATA_INVALID_1_0);
                checkFlag = false;
            }
            
            if (!("0".equals(gaiDto.getJune().toString()) || "1".equals(gaiDto.getJune().toString()))) {
                errRes.addErrorInfo("june_inventory_" + i, CCMessageConst.MSG_KEY_INPUT_DATA_INVALID_1_0);
                checkFlag = false;
            }
            
            if (!("0".equals(gaiDto.getJuly().toString()) || "1".equals(gaiDto.getJuly().toString()))) {
                errRes.addErrorInfo("july_inventory_" + i, CCMessageConst.MSG_KEY_INPUT_DATA_INVALID_1_0);
                checkFlag = false;
            }
            
            if (!("0".equals(gaiDto.getAugust().toString()) || "1".equals(gaiDto.getAugust().toString()))) {
                errRes.addErrorInfo("august_inventory_" + i, CCMessageConst.MSG_KEY_INPUT_DATA_INVALID_1_0);
                checkFlag = false;
            }
            
            if (!("0".equals(gaiDto.getSeptember().toString()) || "1".equals(gaiDto.getSeptember().toString()))) {
                errRes.addErrorInfo("september_inventory_" + i, CCMessageConst.MSG_KEY_INPUT_DATA_INVALID_1_0);
                checkFlag = false;
            }
            
            if (!("0".equals(gaiDto.getOctober().toString()) || "1".equals(gaiDto.getOctober().toString()))) {
                errRes.addErrorInfo("october_inventory_" + i, CCMessageConst.MSG_KEY_INPUT_DATA_INVALID_1_0);
                checkFlag = false;
            }
            
            if (!("0".equals(gaiDto.getNovember().toString()) || "1".equals(gaiDto.getNovember().toString()))) {
                errRes.addErrorInfo("november_inventory_" + i, CCMessageConst.MSG_KEY_INPUT_DATA_INVALID_1_0);
                checkFlag = false;
            }
            
            if (!("0".equals(gaiDto.getDecember().toString()) || "1".equals(gaiDto.getDecember().toString()))) {
                errRes.addErrorInfo("december_inventory_" + i, CCMessageConst.MSG_KEY_INPUT_DATA_INVALID_1_0);
                checkFlag = false;
            }
            
            if (!("0".equals(gaiDto.getJanuary().toString()) || "1".equals(gaiDto.getJanuary().toString()))) {
                errRes.addErrorInfo("january_inventory_" + i, CCMessageConst.MSG_KEY_INPUT_DATA_INVALID_1_0);
                checkFlag = false;
            }
            
            if (!("0".equals(gaiDto.getFebruary().toString()) || "1".equals(gaiDto.getFebruary().toString()))) {
                errRes.addErrorInfo("february_inventory_" + i, CCMessageConst.MSG_KEY_INPUT_DATA_INVALID_1_0);
                checkFlag = false;
            }
            
            if (!("0".equals(gaiDto.getMarch().toString()) || "1".equals(gaiDto.getMarch().toString()))) {
                errRes.addErrorInfo("march_inventory_" + i, CCMessageConst.MSG_KEY_INPUT_DATA_INVALID_1_0);
                checkFlag = false;
            }
        }
        
        if (!checkFlag) {
            ErrorInfo error = errRes.getErrorInfo(CCMessageConst.MSG_KEY_VALIDATION_ERROR);
            errRes.getErrors().add(0, error);
        }
        return checkFlag;
    }

    /**
     * Validation data for ZIJP0030ResultForm form 
     * @param listTen list Zijp0030TenDto
     * @return listTen list Zijp0030TenDto
     */
    private boolean validateMonthWithTenCdList(List<Zijp0030TenDto> listTen) {
        boolean checkFlag = true;
        for (int i = 0; i < listTen.size(); i++) {
            Zijp0030TenDto tenDto = listTen.get(i);
            if (tenDto.getApril() == null || StringUtils.EMPTY.equals(tenDto.getApril().toString())) {
                errRes.addErrorInfo("april_" + i, CCMessageConst.MSG_KEY_INPUT_REQUIRED_ENTER);
                checkFlag = false;
            } else if (tenDto.getApril().intValue() < 0 || tenDto.getApril().intValue() > MAX_DAY_IN_APR_JUN_SEP_NOV) {
                errRes.addErrorInfo("april_" + i, CCMessageConst.MSG_KEY_030DAYS_INPUT_ERROR);
                checkFlag = false;
            }

            if (tenDto.getMay() == null || StringUtils.EMPTY.equals(tenDto.getMay().toString())) {
                errRes.addErrorInfo("may_" + i, CCMessageConst.MSG_KEY_INPUT_REQUIRED_ENTER);
                checkFlag = false;
            } else if (tenDto.getMay().intValue() < 0 || tenDto.getMay().intValue() > MAX_DAY_IN_OTHER_MONTH) {
                errRes.addErrorInfo("may_" + i, CCMessageConst.MSG_KEY_031DAYS_INPUT_ERROR);
                checkFlag = false;
            }

            if (tenDto.getJune() == null || StringUtils.EMPTY.equals(tenDto.getJune().toString())) {
                errRes.addErrorInfo("june_" + i, CCMessageConst.MSG_KEY_INPUT_REQUIRED_ENTER);
                checkFlag = false;
            } else if (tenDto.getJune().intValue() < 0 || tenDto.getJune().intValue() > MAX_DAY_IN_APR_JUN_SEP_NOV) {
                errRes.addErrorInfo("june_" + i, CCMessageConst.MSG_KEY_030DAYS_INPUT_ERROR);
                checkFlag = false;
            }

            if (tenDto.getJuly() == null || StringUtils.EMPTY.equals(tenDto.getJuly().toString())) {
                errRes.addErrorInfo("july_" + i, CCMessageConst.MSG_KEY_INPUT_REQUIRED_ENTER);
                checkFlag = false;
            } else if (tenDto.getJuly().intValue() < 0 || tenDto.getJuly().intValue() > MAX_DAY_IN_OTHER_MONTH) {
                errRes.addErrorInfo("july_" + i, CCMessageConst.MSG_KEY_031DAYS_INPUT_ERROR);
                checkFlag = false;
            }

            if (tenDto.getAugust() == null || StringUtils.EMPTY.equals(tenDto.getAugust().toString())) {
                errRes.addErrorInfo("august_" + i, CCMessageConst.MSG_KEY_INPUT_REQUIRED_ENTER);
                checkFlag = false;
            } else if (tenDto.getAugust().intValue() < 0 || tenDto.getAugust().intValue() > MAX_DAY_IN_OTHER_MONTH) {
                errRes.addErrorInfo("august_" + i, CCMessageConst.MSG_KEY_031DAYS_INPUT_ERROR);
                checkFlag = false;
            }

            if (tenDto.getSeptember() == null || StringUtils.EMPTY.equals(tenDto.getSeptember().toString())) {
                errRes.addErrorInfo("september_" + i, CCMessageConst.MSG_KEY_INPUT_REQUIRED_ENTER);
                checkFlag = false;
            } else if (tenDto.getSeptember().intValue() < 0
                    || tenDto.getSeptember().intValue() > MAX_DAY_IN_APR_JUN_SEP_NOV) {
                errRes.addErrorInfo("september_" + i, CCMessageConst.MSG_KEY_030DAYS_INPUT_ERROR);
                checkFlag = false;
            }

            if (tenDto.getOctober() == null || StringUtils.EMPTY.equals(tenDto.getOctober().toString())) {
                errRes.addErrorInfo("october_" + i, CCMessageConst.MSG_KEY_INPUT_REQUIRED_ENTER);
                checkFlag = false;
            } else if (tenDto.getOctober().intValue() < 0 || tenDto.getOctober().intValue() > MAX_DAY_IN_OTHER_MONTH) {
                errRes.addErrorInfo("october_" + i, CCMessageConst.MSG_KEY_031DAYS_INPUT_ERROR);
                checkFlag = false;
            }

            if (tenDto.getNovember() == null || StringUtils.EMPTY.equals(tenDto.getNovember().toString())) {
                errRes.addErrorInfo("november_" + i, CCMessageConst.MSG_KEY_INPUT_REQUIRED_ENTER);
                checkFlag = false;
            } else if (tenDto.getNovember().intValue() < 0
                    || tenDto.getNovember().intValue() > MAX_DAY_IN_APR_JUN_SEP_NOV) {
                errRes.addErrorInfo("november_" + i, CCMessageConst.MSG_KEY_030DAYS_INPUT_ERROR);
                checkFlag = false;
            }

            if (tenDto.getDecember() == null || StringUtils.EMPTY.equals(tenDto.getDecember().toString())) {
                errRes.addErrorInfo("december_" + i, CCMessageConst.MSG_KEY_INPUT_REQUIRED_ENTER);
                checkFlag = false;
            } else if (tenDto.getDecember().intValue() < 0 || tenDto.getDecember().intValue() > MAX_DAY_IN_OTHER_MONTH) {
                errRes.addErrorInfo("december_" + i, CCMessageConst.MSG_KEY_031DAYS_INPUT_ERROR);
                checkFlag = false;
            }

            if (tenDto.getJanuary() == null || StringUtils.EMPTY.equals(tenDto.getJanuary().toString())) {
                errRes.addErrorInfo("january_" + i, CCMessageConst.MSG_KEY_INPUT_REQUIRED_ENTER);
                checkFlag = false;
            } else if (tenDto.getJanuary().intValue() < 0 || tenDto.getJanuary().intValue() > MAX_DAY_IN_OTHER_MONTH) {
                errRes.addErrorInfo("january_" + i, CCMessageConst.MSG_KEY_031DAYS_INPUT_ERROR);
                checkFlag = false;
            }

            if (tenDto.getFebruary() == null || StringUtils.EMPTY.equals(tenDto.getFebruary().toString())) {
                errRes.addErrorInfo("february_" + i, CCMessageConst.MSG_KEY_INPUT_REQUIRED_ENTER);
                checkFlag = false;
            } else if (tenDto.getFebruary().intValue() < 0 || tenDto.getFebruary().intValue() > MAX_DAY_IN_FEB) {
                errRes.addErrorInfo("february_" + i, CCMessageConst.MSG_KEY_029DAYS_INPUT_ERROR);
                checkFlag = false;
            }

            if (tenDto.getMarch() == null || StringUtils.EMPTY.equals(tenDto.getMarch().toString())) {
                errRes.addErrorInfo("march_" + i, CCMessageConst.MSG_KEY_INPUT_REQUIRED_ENTER);
                checkFlag = false;
            } else if (tenDto.getMarch().intValue() < 0 || tenDto.getMarch().intValue() > MAX_DAY_IN_OTHER_MONTH) {
                errRes.addErrorInfo("march_" + i, CCMessageConst.MSG_KEY_031DAYS_INPUT_ERROR);
                checkFlag = false;
            }
        }

        if (!checkFlag) {
            ErrorInfo error = errRes.getErrorInfo(CCMessageConst.MSG_KEY_VALIDATION_ERROR);
            errRes.getErrors().add(0, error);
        }
        return checkFlag;
    }
}
