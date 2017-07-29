// /////////////////////////////////////////////////////////////////////
// Copyright(C) 2015 NEC Solution Innovators, Ltd. All Rights Reserved.
// /////////////////////////////////////////////////////////////////////
/*
 * ====================================================================
 * 
 * 機能名称 :checkutil
 * 
 * 改版履歴
 * 
 * Rev. 改版年月日 改版者名 内容
 * 
 * 1.0 2014-04-01 PhatTT 新規作成
 * 
 * ===================================================================
 */
package jp.co.necsoft.web_md.core.common.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import jp.co.necsoft.web_md.common.mybatis.dao.MyBatisSqlMapDao;
import jp.co.necsoft.web_md.core.common.CCMessageConst;
import jp.co.necsoft.web_md.core.common.forms.ErrorResponse;
import jp.co.necsoft.web_md.core.common.mybatis.dao.M000kaimMapper;
import jp.co.necsoft.web_md.core.common.mybatis.dao.M001jgymMapper;
import jp.co.necsoft.web_md.core.common.mybatis.dao.M003bmnmMapper;
import jp.co.necsoft.web_md.core.common.mybatis.dao.M005bnrmMapper;
import jp.co.necsoft.web_md.core.common.mybatis.dao.M007kijmMapper;
import jp.co.necsoft.web_md.core.common.mybatis.dao.M011trimMapper;
import jp.co.necsoft.web_md.core.common.mybatis.dao.M012tngmMapper;
import jp.co.necsoft.web_md.core.common.mybatis.dao.M016tanmMapper;
import jp.co.necsoft.web_md.core.common.mybatis.dao.M017meimMapper;
import jp.co.necsoft.web_md.core.common.mybatis.dao.M708dpkmMapper;
import jp.co.necsoft.web_md.core.common.mybatis.dao.X007kbnmMapper;
import jp.co.necsoft.web_md.core.common.mybatis.dto.CodeMaster;
import jp.co.necsoft.web_md.core.common.mybatis.dto.M000kaim;
import jp.co.necsoft.web_md.core.common.mybatis.dto.M000kaimExample;
import jp.co.necsoft.web_md.core.common.mybatis.dto.M001jgym;
import jp.co.necsoft.web_md.core.common.mybatis.dto.M001jgymExample;
import jp.co.necsoft.web_md.core.common.mybatis.dto.M003bmnm;
import jp.co.necsoft.web_md.core.common.mybatis.dto.M003bmnmExample;
import jp.co.necsoft.web_md.core.common.mybatis.dto.M005bnrm;
import jp.co.necsoft.web_md.core.common.mybatis.dto.M005bnrmExample;
import jp.co.necsoft.web_md.core.common.mybatis.dto.M007kijm;
import jp.co.necsoft.web_md.core.common.mybatis.dto.M007kijmExample;
import jp.co.necsoft.web_md.core.common.mybatis.dto.M011trim;
import jp.co.necsoft.web_md.core.common.mybatis.dto.M011trimExample;
import jp.co.necsoft.web_md.core.common.mybatis.dto.M012tngm;
import jp.co.necsoft.web_md.core.common.mybatis.dto.M012tngmExample;
import jp.co.necsoft.web_md.core.common.mybatis.dto.M016tanm;
import jp.co.necsoft.web_md.core.common.mybatis.dto.M016tanmExample;
import jp.co.necsoft.web_md.core.common.mybatis.dto.M017meim;
import jp.co.necsoft.web_md.core.common.mybatis.dto.M017meimExample;
import jp.co.necsoft.web_md.core.common.mybatis.dto.M708dpkm;
import jp.co.necsoft.web_md.core.common.mybatis.dto.M708dpkmExample;
import jp.co.necsoft.web_md.core.common.mybatis.dto.X007kbnm;
import jp.co.necsoft.web_md.core.common.mybatis.dto.X007kbnmExample;

import org.apache.commons.lang3.StringUtils;

/**
 * Check exist utility
 */
public class CCCheckExistsUtil {
    /** 削除  */
    private static final String TYPE_ACT_KBN_DELETED = "9";
    /**
     * SEPARATE CHAR
     */
    private static final String SEPARATE_CHAR = "、";
    
    
    /** 削除FLG */
    private static final String DELETED_FLAG_NORMAL = "0";
    
    /** 取引先休止区分 */
    private static final String TORI_STOP_KBN_NORMAL = "9";

    /** 発注種類種別 */
    private static final String HAT_SRUI_KBN_PRIX = "HATSRUIKBN0000";

    /** Hat kbn value  */
    private static final String HAT_KBN_VALUE = "HAT";

    /** M017meim's Mapper */
    @Inject
    private M017meimMapper m017meimMapper;

    /** M005bnrm's Mapper mapper of table M005bnrm */
    @Inject
    private M005bnrmMapper m005bnrmMapper;

    /** M011trim's Mapper */
    @Inject
    private M011trimMapper m011trimMapper;

    /** m012tngm's Mapper */
    @Inject
    private M012tngmMapper m012tngmMapper;

    /** M000kaim's Mapper */
    @Inject
    private M000kaimMapper m000kaimMapper;

    /** M001jgym's Mapper */
    @Inject
    private M001jgymMapper m001jgymMapper;

    /** M003bmnm's Mapper */
    @Inject
    private M003bmnmMapper m003bmnmMapper;
    
    /** X007kbnm's Mapper */
    @Inject
    private X007kbnmMapper x007kbnmMapper;
    
    /** M016tanm's Mapper */
    @Inject
    private M016tanmMapper m016tanmMapper;
    
    /** M016tanm's Mapper */
    @Inject
    private M708dpkmMapper m708dpkmMapper;
    
    /** m007kijmMapper */
    @Inject
    private M007kijmMapper m007kijmMapper;
    
    /** Mybatis common dao */
    @Inject
    private MyBatisSqlMapDao dao;

    /**
     * Function check exists hatSruiKBN
     * 
     * @param date input date
     * @param hatSruiKbn input 
     * @return boolean return exists or not
     */
    public boolean existsHatSruiKbn(String date, String hatSruiKbn) {
        M017meimExample example = new M017meimExample();

        example.createCriteria().andCdKbnEqualTo(HAT_KBN_VALUE).andCdEqualTo(HAT_SRUI_KBN_PRIX + hatSruiKbn);
        example.setSearchDate(date);

        List<M017meim> list = this.m017meimMapper.selectByExample(example);
        if (list.size() > 0) {
            if (!TYPE_ACT_KBN_DELETED.equals(list.get(0).getActKbn())) {
                return true;
            }
        }

        return false;
    }

    /**
     * Function check exist kaisyaCd
     * 
     * @param date hatDd
     * @param kaisyaCd kaisyaCd
     * @return boolean exists or not
     */
    public boolean existsKaisyaCd(String date, String kaisyaCd) {
        M000kaimExample example = new M000kaimExample();

        example.createCriteria().andKaisyaCdEqualTo(kaisyaCd);
        example.setSearchDate(date);

        List<M000kaim> list = this.m000kaimMapper.selectByExample(example);
        if (list.size() > 0) {
            if (!TYPE_ACT_KBN_DELETED.equals(list.get(0).getActKbn())) {
                return true;
            }
        }

        return false;
    }

    /**
     * Function check exists kaisyaCd and jigyobuCd
     * 
     * @param date date
     * @param kaisyaCd kaisyaCd
     * @param jigyobuCd jigyobuCd
     * @return boolean exits or not
     */
    public boolean existsKaisyaCdAndJigyobuCd(String date, String kaisyaCd, String jigyobuCd) {
        M001jgymExample example = new M001jgymExample();

        example.createCriteria().andKaisyaCdEqualTo(kaisyaCd).andJigyobuCdEqualTo(jigyobuCd);
        example.setSearchDate(date);

        List<M001jgym> list = this.m001jgymMapper.selectByExample(example);
        if (list.size() > 0) {
            if (!TYPE_ACT_KBN_DELETED.equals(list.get(0).getActKbn())) {
                return true;
            }
        }

        return false;
    }
    
    /**
     * Function check exists dpykbn
     * 
     * @param control Control Id
     * @param dpyKbn dpyKbn
     * @param errRes Error Response
     * @return boolean exits or not
     */
    public boolean existsDpyKbn(String control, String dpyKbn, ErrorResponse errRes) {
        M708dpkmExample example = new M708dpkmExample();

        example.createCriteria().andDenKbnEqualTo(dpyKbn).andDeleteFlgEqualTo(DELETED_FLAG_NORMAL);
        List<M708dpkm> list = this.m708dpkmMapper.selectByExample(example);

        if (list.size() > 0) {
            return true;
        }

        errRes.addErrorInfo(CCMessageConst.MSG_KEY_VALIDATION_ERROR);
        errRes.addErrorInfo(control, CCMessageConst.MSG_KEY_CODE_NOT_EXIST);

        return false;
    }

    /**
     * Function check exists subdpykbn
     * 
     * @param control Control Id
     * @param dpyKbn dpyKbn
     * @param subDpyKbn subDpyKbn
     * @param errRes Error Response
     * @return boolean exits or not
     */
    public boolean existsSubDpyKbn(String control, String dpyKbn, String subDpyKbn, ErrorResponse errRes) {
        M708dpkmExample example = new M708dpkmExample();

        example.createCriteria().andDenKbnEqualTo(dpyKbn).andDnpSubKbnEqualTo(subDpyKbn)
                .andDeleteFlgEqualTo(DELETED_FLAG_NORMAL);
        List<M708dpkm> list = this.m708dpkmMapper.selectByExample(example);

        if (list.size() > 0) {
            return true;
        }

        errRes.addErrorInfo(CCMessageConst.MSG_KEY_VALIDATION_ERROR);
        errRes.addErrorInfo(control, CCMessageConst.MSG_KEY_CODE_NOT_EXIST);
        return false;
    }

    /**
     * Function check exists kaisyaCd and jigyobuCd
     * 
     * @param control Control Id
     * @param date date
     * @param kaisyaCd kaisyaCd
     * @param jigyobuCd jigyobuCd
     * @param errRes Error Response
     * @return boolean exits or not
     */
    public boolean existsJigyobuCd(String control, String date, String kaisyaCd, String jigyobuCd,
            ErrorResponse errRes) {
        if (!existsKaisyaCdAndJigyobuCd(date, kaisyaCd, jigyobuCd)) {
            errRes.addErrorInfo(CCMessageConst.MSG_KEY_VALIDATION_ERROR);
            errRes.addErrorInfo(control, CCMessageConst.MSG_KEY_CODE_NOT_EXIST);
            return false;
        }

        return true;
    }

    /**
     * Function check exists bmnCd
     * 
     * @param date input date
     * @param bmnCd bmnCd
     * @return boolean exists or not
     */
    public boolean existsBmnCd(String date, String bmnCd) {
        M003bmnmExample example = new M003bmnmExample();
        example.createCriteria().andBmnCdEqualTo(bmnCd);
        example.setSearchDate(date);

        List<M003bmnm> list = this.m003bmnmMapper.selectByExample(example);
        if (list.size() > 0) {
            if (!TYPE_ACT_KBN_DELETED.equals(list.get(0).getActKbn())) {
                return true;
            }
        }

        return false;
    }
    
    /**
     * Function check exists bmnCd
     * 
     * @param control Control ID
     * @param date input date
     * @param bmnCd bmnCd
     * @param errRes ErrorResponse
     * @return boolean exists or not
     */
    public boolean existsBmnCd(String control, String date, String bmnCd, ErrorResponse errRes) {
        if (!existsBmnCd(date, bmnCd)) {
            errRes.addErrorInfo(CCMessageConst.MSG_KEY_VALIDATION_ERROR);
            errRes.addErrorInfo(control, CCMessageConst.MSG_KEY_CODE_NOT_EXIST);
            return false;
        }
        
        return true;
    }

    /**
     * Function chech tenCd is exists
     * 
     * @param date date
     * @param tenCd tenCd
     * @return boolean exists or not
     */
    public boolean existsTenCd(String date, String tenCd) {
        Map<String, String> param = new HashMap<String, String>();
        param.put("unyoDate", date);
        param.put("tenCd", tenCd);
        List<CodeMaster> codeMasters = dao.selectMany("M006CheckTenCd", param, CodeMaster.class);
        if (codeMasters.size() > 0) {
            return true;
        }
        return false;
    }
    
    /**
     * Function chech tenCd is exists
     * 
     * @param control Control ID
     * @param date date
     * @param tenCd tenCd
     * @param errRes Error Response
     * @return boolean exists or not
     */
    public boolean existsTenCd(String control, String date, String tenCd, ErrorResponse errRes) {
        if (!existsTenCd(date, tenCd)) {
            errRes.addErrorInfo(CCMessageConst.MSG_KEY_VALIDATION_ERROR);
            errRes.addErrorInfo(control, CCMessageConst.MSG_KEY_CODE_NOT_EXIST);
            return false;
        }
        
        return true;
    }
    
    /**
     * Function chech tenCd is exists for multiple select control
     * @param control Control ID
     * @param date 発効日
     * @param kaisyaCd 会社コード
     * @param jigyobuCd 事業部コード
     * @param lsTenCd List of TenCd
     * @param errRes Error Response
     * @return boolean exists or not
     */
    public boolean existsTenCdMultiple(String control, String date, String kaisyaCd, String jigyobuCd,
            List<String> lsTenCd, ErrorResponse errRes) {
        if (lsTenCd != null && lsTenCd.size() > 0) {
            String errorString = StringUtils.EMPTY;
            Boolean isError = false;
            for (String tenCd : lsTenCd) {
                if (!existsTenCd(date, kaisyaCd + jigyobuCd + tenCd)) {
                    isError = true;
                    if (errorString == StringUtils.EMPTY) {
                        errorString = tenCd;
                    } else {
                        errorString = errorString + SEPARATE_CHAR + tenCd;
                    }
                }
            }
            if (isError) {
                errRes.addErrorInfo(CCMessageConst.MSG_KEY_VALIDATION_ERROR);
                ArrayList<String> param = new ArrayList<String>();
                param.add(errorString);
                errRes.addErrorInfoWithParam(control, CCMessageConst.MSG_KEY_MULTICODE_NOT_REGISTERD, param);
                return false;
            }
        }
        return true;
    }

    /**
     * Function check exists triCd
     * 
     * @param date date
     * @param triCd triCd
     * @return boolean exists or not
     */
    public boolean existTriCd(String date, String triCd) {
        M011trimExample example = new M011trimExample();
        example.createCriteria().andTriCdEqualTo(triCd).andToriStopKbnEqualTo(TORI_STOP_KBN_NORMAL);

        example.setSearchDate(date);

        List<M011trim> list = this.m011trimMapper.selectByExample(example);
        if (list.size() > 0) {
            if (!TYPE_ACT_KBN_DELETED.equals(list.get(0).getActKbn())) {
                return true;
            }
        }

        return false;
    }
    
    /**
     * Function check exists triCd
     * 
     * @param control Control ID
     * @param date date
     * @param triCd triCd
     * @param errRes Error Response
     * @return boolean exists or not
     */
    public boolean existTriCd(String control, String date, String triCd, ErrorResponse errRes) {
        if (!existTriCd(date, triCd)) {
            errRes.addErrorInfo(CCMessageConst.MSG_KEY_VALIDATION_ERROR);
            errRes.addErrorInfo(control, CCMessageConst.MSG_KEY_CODE_NOT_EXIST);
            return false;
        }
        
        return true;
    }

    /**
     * Function check exists triCd
     * 
     * @param date date
     * @param jigyobuCd 事業部コード
     * @param bmnCd Bumon Code
     * @param grpCd Group Code
     * @return boolean exists or not
     */
    public boolean existGrpCd(String date, String jigyobuCd, String bmnCd, String grpCd) {
        M012tngmExample m012tngmExample = new M012tngmExample();

        boolean flag = false;
        String key = "";
        if (CCStringUtil.isEmpty(jigyobuCd)) {
            flag = true;
            key += "%";
        } else {
            key += jigyobuCd;
        }

        if (CCStringUtil.isEmpty(bmnCd)) {
            if (!"%".equals(key)) {
                flag = true;
                key += "%";
            }
        } else {
            key += bmnCd;
        }

        key += grpCd;

        if (flag) {
            m012tngmExample.createCriteria().andGrpCdLike(key);
        } else {
            m012tngmExample.createCriteria().andGrpCdEqualTo(key);
        }

        m012tngmExample.setSearchDate(date);

        List<M012tngm> list = this.m012tngmMapper.selectByExample(m012tngmExample);
        if (list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                if (!TYPE_ACT_KBN_DELETED.equals(list.get(i).getActKbn())) {
                    return true;
                }
            }
            return false;
        } else {
            return false;
        }
    }
    
    /**
     * check exist grpCd
     * @param key String
     * @param grpCd String
     * @param searchDate String
     * @return true : exist, false : not exist
     */
    public boolean existGrpCd(String key, String grpCd, String searchDate) {
        M012tngmExample example = new M012tngmExample();
        example.createCriteria().andGrpKbnEqualTo(key).andGrpCdEqualTo(grpCd);
        example.setSearchDate(searchDate);
        List<M012tngm> list = m012tngmMapper.selectByExample(example);
        if (list.size() > 0 && !TYPE_ACT_KBN_DELETED.equals(list.get(0).getActKbn())) {
            return true;
        }
        return false;
    }
    
    /**
     * check exist grpCd
     * 
     * @param control item id
     * @param key String
     * @param grpCd String
     * @param searchDate String
     * @param errRes Error Response 
     * @return true : exist, false : not exist
     */
    public boolean existGrpCd(String control, String key, String grpCd, String searchDate, ErrorResponse errRes) {
        if (!existGrpCd(key, grpCd, searchDate)) {
            errRes.addErrorInfo(CCMessageConst.MSG_KEY_VALIDATION_ERROR);
            errRes.addErrorInfo(control, CCMessageConst.MSG_KEY_CODE_NOT_EXIST);
            return false;
        }
        
        return true;
    }

    /**
     * Function check exists chuBnrCd
     * 
     * @param date date
     * @param jigyobuCd 事業部コード
     * @param bmnCd bmnCd
     * @param chuBnrCd chuBnrCd
     * @return boolean exists or not
     */
    public boolean existChuBnrCd(String date, String jigyobuCd, String bmnCd, String chuBnrCd) {
        M005bnrmExample example = new M005bnrmExample();
        example.createCriteria().andBmnCdEqualTo(jigyobuCd + bmnCd).andRejiCdEqualTo("0" + bmnCd)
                .andChuBnrCdEqualTo(chuBnrCd).andYotoKbnEqualTo("0000").andShoBnrCdEqualTo("0000");
        example.setSearchDate(date);

        List<M005bnrm> list = this.m005bnrmMapper.selectByExample(example);
        if (list.size() > 0) {
            if (!TYPE_ACT_KBN_DELETED.equals(list.get(0).getActKbn())) {
                return true;
            }
        }

        return false;
    }
    

    /**
     * Function check exists chuBnrCd
     * 
     * @param control Control ID
     * @param date date
     * @param jigyobuCd jigyobuCd
     * @param bmnCd bmnCd
     * @param chuBnrCd chuBnrCd
     * @param errRes Error Response
     * @return boolean exists or not
     */
    public boolean existChuBnrCd(String control, String date, String jigyobuCd, String bmnCd, String chuBnrCd,
            ErrorResponse errRes) {
        if (!existChuBnrCd(date, jigyobuCd, bmnCd, chuBnrCd)) {
            errRes.addErrorInfo(CCMessageConst.MSG_KEY_VALIDATION_ERROR);
            errRes.addErrorInfo(control, CCMessageConst.MSG_KEY_CODE_NOT_EXIST);
            return false;
        }

        return true;
    }
    
    /**
     * Function check exists shoBnrCd
     * 
     * @param control control
     * @param date date
     * @param jigyobuCd jigyobuCd
     * @param bmnCd bmnCd
     * @param chuBnrCd chuBnrCd
     * @param shoBnrCd shoBnrCd
     * @param errRes Error Response
     * @return boolean exists or not
     */
    public boolean existShoBnrCd(String control, String date, String jigyobuCd, String bmnCd, String chuBnrCd,
            String shoBnrCd, ErrorResponse errRes) {
        M005bnrmExample example = new M005bnrmExample();
        example.createCriteria().andBmnCdEqualTo(jigyobuCd + bmnCd).andRejiCdEqualTo("0" + bmnCd)
                .andChuBnrCdEqualTo(chuBnrCd).andYotoKbnEqualTo("0000").andShoBnrCdEqualTo(shoBnrCd);
        example.setSearchDate(date);

        List<M005bnrm> list = this.m005bnrmMapper.selectByExample(example);
        if (list.size() > 0) {
            if (!TYPE_ACT_KBN_DELETED.equals(list.get(0).getActKbn())) {
                return true;
            }
        }
        
        errRes.addErrorInfo(CCMessageConst.MSG_KEY_VALIDATION_ERROR);
        errRes.addErrorInfo(control, CCMessageConst.MSG_KEY_CODE_NOT_EXIST);
        
        return false;
    }
    
    /**
     * check cd exist in x007
     * @param cdKbn String
     * @param cd String
     * @return boolean
     */
    public boolean existsKbn(String cdKbn, String cd) {
        boolean flg = false;
        X007kbnmExample x007kbnmExample = new X007kbnmExample();
        x007kbnmExample.createCriteria().andDeleteFlgEqualTo(DELETED_FLAG_NORMAL).andCdKbnEqualTo(cdKbn)
                .andCdEqualTo(cd).andActKbnNotEqualTo(TYPE_ACT_KBN_DELETED);
        List<X007kbnm> kbns = x007kbnmMapper.selectByExample(x007kbnmExample);
        if (kbns != null && kbns.size() > 0) {
            flg = true;
        }
        return flg;
    }
    
    /**
     * check cd exist in x007
     * @param control Control ID
     * @param cdKbn String
     * @param cd String
     * @param errRes Error Response
     * @return boolean
     */
    public boolean existsKbn(String control, String cdKbn, String cd, ErrorResponse errRes) {
        if (!existsKbn(cdKbn, cd)) {
            errRes.addErrorInfo(CCMessageConst.MSG_KEY_VALIDATION_ERROR);
            errRes.addErrorInfo(control, CCMessageConst.MSG_KEY_CODE_NOT_EXIST);
            return false;
        }
        return true;
    }

    /**
     * method to valid max record Number
     * @param maxRecordNumber max record number
     * @param errRes Error Response
     * @return  boolean
     */
    public static boolean existMaxRecordNumber(String maxRecordNumber, ErrorResponse errRes) {
        if (CCStringUtil.isEmpty(maxRecordNumber) || !CCNumberUtil.isNumeric(maxRecordNumber)) {
            errRes.addErrorInfo(CCMessageConst.MSG_KEY_GRID_NUMBER_REACH_LIMIT);
            return false;
        }
        return true;
    }
    
    /**
     * check exist tanto code
     * @param tantoCd tanto code to check
     * @param date date to search
     * @return boolean exists or not
     */
    public boolean existsTantoCd(String tantoCd, String date) {
        M016tanmExample example = new M016tanmExample();
        example.createCriteria().andTantoCdEqualTo(tantoCd);
        example.setSearchDate(date);
        List<M016tanm> results = m016tanmMapper.selectByExample(example);
        if (results.isEmpty()) {
            return false;
        }

        if (!TYPE_ACT_KBN_DELETED.equals(results.get(0).getActKbn())) {
            return true;
        }

        return false;
    }
    
    /**
     * check exist tanto code
     * @param control Control ID
     * @param tantoCd tanto code to check
     * @param date date to search
     * @param errRes Error Response
     * @return boolean exists or not
     */
    public boolean existsTantoCd(String control, String tantoCd, String date, ErrorResponse errRes) {
        if (!existsTantoCd(tantoCd, date)) {
            errRes.addErrorInfo(CCMessageConst.MSG_KEY_VALIDATION_ERROR);
            errRes.addErrorInfo(control, CCMessageConst.MSG_KEY_CODE_NOT_EXIST);
            return false;
        }
        return true;
    }
    
    /**
     * check exist janCd
     * @param control Control ID
     * @param date date
     * @param janCd janCd
     * @param errRes Error Response
     * @return boolean exists or not
     */
    public boolean existsJanCd(String control, String date, String janCd, ErrorResponse errRes) {
        // 商品名・規格名を取得する
        M007kijmExample m007kijmExample = new M007kijmExample();
        m007kijmExample.createCriteria().andJanCdEqualTo(janCd).andHakkoDayToGreaterThanOrEqualTo(date);
        m007kijmExample.setSearchDate(date);

        List<M007kijm> list = m007kijmMapper.selectByExample(m007kijmExample);

        if (list.size() > 0) {
            if (!TYPE_ACT_KBN_DELETED.equals(list.get(0).getActKbn())) {
                return true;
            }
        }

        errRes.addErrorInfo(CCMessageConst.MSG_KEY_VALIDATION_ERROR);
        errRes.addErrorInfo(control, CCMessageConst.MSG_KEY_CODE_NOT_EXIST);

        return false;

    }
}
