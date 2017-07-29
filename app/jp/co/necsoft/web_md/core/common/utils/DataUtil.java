// /////////////////////////////////////////////////////////////////////
// Copyright(C) 2015 NEC Solution Innovators, Ltd. All Rights Reserved.
// /////////////////////////////////////////////////////////////////////
/*
 * ====================================================================
 * 
 * 機能名称 : データユーティリティ
 * 
 * 改版履歴
 * 
 * Rev. 改版年月日 改版者名 内容
 * 
 * 1.0 2014.04.03 HaiNP 新規作成
 * 
 * ===================================================================
 */
package jp.co.necsoft.web_md.core.common.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import javax.inject.Inject;

import jp.co.necsoft.web_md.common.ChaseException;
import jp.co.necsoft.web_md.common.mybatis.dao.MyBatisSqlMapDao;
import jp.co.necsoft.web_md.core.common.CCMessageConst;
import jp.co.necsoft.web_md.core.common.forms.ErrorResponse;
import jp.co.necsoft.web_md.core.common.mybatis.dao.M000kaimMapper;
import jp.co.necsoft.web_md.core.common.mybatis.dao.M001jgymMapper;
import jp.co.necsoft.web_md.core.common.mybatis.dao.M003bmnmMapper;
import jp.co.necsoft.web_md.core.common.mybatis.dao.M005bnrmMapper;
import jp.co.necsoft.web_md.core.common.mybatis.dao.M006tenmMapper;
import jp.co.necsoft.web_md.core.common.mybatis.dao.M011trimMapper;
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
import jp.co.necsoft.web_md.core.common.mybatis.dto.M006tenm;
import jp.co.necsoft.web_md.core.common.mybatis.dto.M006tenmExample;
import jp.co.necsoft.web_md.core.common.mybatis.dto.M011trim;
import jp.co.necsoft.web_md.core.common.mybatis.dto.M011trimExample;
import jp.co.necsoft.web_md.core.common.mybatis.dto.X007kbnm;
import jp.co.necsoft.web_md.core.common.mybatis.dto.X007kbnmExample;

import org.apache.commons.lang3.StringUtils;

/**
 * データユーティリティ
 *
 */
public class DataUtil {

    /** Length shex address */
    private static final int LEN_SHEX_ADDR = 8;
    
    /** Length of range ip */
    private static final int MAX_GROUP_IP_RANGING = 4;
    
    /** Length of range ip */
    private static final int THIRD_GROUP_POSITION = 3;

    /** Max ip range value */
    private static final int MAX_VALUE_IP_RANGING = 255;

    /** 削除 */
    private static final String TYPE_ACT_KBN_DELETED = "9";
    
    /** 削除FLG */
    private static final String DELETED_FLAG_NORMAL = "0";
    
    /** 日付のフォーマットエラー */
    public static final int DATE_FORMAT_ERROR = -9999;

    /** 新規 */
    private static final String MSG_INSERT_FUTURE = "新規[1]";

    /** 更新 */
    private static final String MSG_UPDATE_FUTURE = "更新[2]";

    /** 削除 */
    private static final String MSG_DELETE_FUTURE = "削除[9]";

    /** M006tenmMapper */
    @Inject
    private M006tenmMapper m006tenmMapper;
    
    /** M000kaimMapper */
    @Inject
    private M000kaimMapper m000kaimMapper;
    
    /** M001jgymMapper */
    @Inject
    private M001jgymMapper m001jgymMapper;

    /** M003bmnmMapper */
    @Inject
    private M003bmnmMapper m003bmnmMapper;
    
    /** M011trimMapper */
    @Inject
    private M011trimMapper m011trimMapper;
    
    /** X007kbnmMapper */
    @Inject
    private X007kbnmMapper x007kbnmMapper;
    
    /** M005bnrmMapper */
    @Inject
    private M005bnrmMapper m005bnrmMapper;
    
    /** dao */
    @Inject
    private MyBatisSqlMapDao dao;
    
    /**
     * Function get error message info when search data future is exists
     * 
     * @param hakkoDay hokkDay
     * @param typeActKbn Action Kbn
     * @param info info
     * @return ErrorResponse
     * @author phatttz
     */
    public static ErrorResponse getFutureMessage(String hakkoDay, String typeActKbn, ErrorResponse info) {
        ArrayList<String> arrMessage = new ArrayList<String>();
        if ("1".equals(typeActKbn)) {
            arrMessage.add(MSG_INSERT_FUTURE);
        }

        if ("2".equals(typeActKbn)) {
            arrMessage.add(MSG_UPDATE_FUTURE);
        }

        if ("9".equals(typeActKbn)) {
            arrMessage.add(MSG_DELETE_FUTURE);
        }

        arrMessage.add(hakkoDay);

        info.addErrorInfoWithParam(CCMessageConst.MSG_KEY_STATUS_EXISTS_FUTURE_DATA, arrMessage);
        return info;
    }

    /**
    * 指定したIPアドレスを16進数化した文字列します。
    * IPアドレスとして変換できない場合は引数を最大桁数(右側)でカットした文字列を返します。
    * @param inipaddr 対象IPアドレス
    * @param iketamax 最大桁数
    * @return 16進数化した文字列
    **/
    public static String convIPAddr2Hex(String inipaddr, int iketamax) {
        int[] iipaddrs = null;
        String shexval;
        String shexaddr = "";
        try {
            iipaddrs = splitIPAddr(inipaddr);
            for (int i = 0; i < MAX_GROUP_IP_RANGING; i++) {
                if (iipaddrs[i] > MAX_VALUE_IP_RANGING || iipaddrs[i] < 0) {
                    inipaddr = "." + iipaddrs[2] + "." + iipaddrs[THIRD_GROUP_POSITION];
                    throw new Exception();
                }
                shexval = Integer.toHexString(iipaddrs[i]);
                if (shexval.length() == 1) {
                    shexval = "0" + shexval;
                }
                shexaddr += shexval.toUpperCase();
            }
        } catch (Exception e) {
            shexaddr = inipaddr;
            if (shexaddr.length() > LEN_SHEX_ADDR) {
                shexaddr = CCStringUtil.getRightStr(shexaddr, LEN_SHEX_ADDR);
            } else {
                shexaddr = CCStringUtil.fixString(shexaddr, LEN_SHEX_ADDR);
            }
        }
        return shexaddr;
    }

    /**
     * IPアドレス文字列をカンマで分割する。
     * @param sipaddr Ip Address
     * @return int array
     */
    private static int[] splitIPAddr(String sipaddr) {
        StringTokenizer st = new StringTokenizer(sipaddr, ".");
        int[] strarray = new int[st.countTokens()];
        int i = 0;
        while (st.hasMoreTokens()) {
            strarray[i] = Integer.parseInt(st.nextToken());
            i++;
        }
        return strarray;
    }


    /**
     * 関数名により値を取得処理.
     * @param obj Object
     * @param methodName get関数名
     * @return get関数の値
     */
    public static Object getValueByMethodName(Object obj, String methodName) {
        try {
            Method m = obj.getClass().getMethod(methodName);
            Object returnValue = m.invoke(obj, new Object[] {});
            return returnValue;
        } catch (Exception e) {
            throw new ChaseException(e);
        }
    }

    /**
     * 関数名により値を取得処理.
     * @param obj Object
     * @param methodName get関数名
     * @param inputValue 入力値
     */
    public static void setValueByMethodName(Object obj, String methodName, Object inputValue) {
        try {
            Method m = obj.getClass().getMethod(methodName, inputValue.getClass());
            m.invoke(obj, inputValue);
        } catch (Exception e) {
            throw new ChaseException(e);
        }
    }
    
    /**
     * Set null for the property
     * @param obj Class
     * @param methodName Method Name
     * @param cls  Class<?>
     */
    public static void setNullValueByMethodName(Object obj, String methodName, Class<?> cls) {
        try {
            Method m = obj.getClass().getMethod(methodName, cls);
            m.invoke(obj, new Object[] {null });
        } catch (Exception e) {
            throw new ChaseException(e);
        }
    }
    
    /**
     * Trim all value of object
     * Using in case of selectOne, selectMany
     * @param obj input object
     * @return object after trim
     */
    public static Object trimObjectValue(Object obj) {
        if (obj == null) {
            return null;
        }
        
        if (obj instanceof String) {
            return CCStringUtil.trimRight((String) obj);
        }
        
        Field[] allFields = obj.getClass().getDeclaredFields();
        for (Field f : allFields) {
            if (f.getType() != String.class) {
                continue;
            }

            String field = Character.toUpperCase(f.getName().charAt(0)) + f.getName().substring(1);
            String methodValue = null;
            try {
                methodValue = (String) getValueByMethodName(obj, "get" + field);
            } catch (Exception e) {
                methodValue = null;
            }
            if (methodValue == null) {
                continue;
            }
            setValueByMethodName(obj, "set" + field, CCStringUtil.trimRight(methodValue));
        }
        return obj;
    }




    /**
     * Get error response when execute logic fail
     * 
     * @return param
     */
    public static ArrayList<String> getErrorMessageUpdateFail() {
        ArrayList<String> param = new ArrayList<String>();
        param.add("更新");
        return param;
    }

    /**
     * Get error response when execute save fail
     * 
     * @return param
     */
    public static ArrayList<String> getErrorMessageSaveFail() {
        ArrayList<String> param = new ArrayList<String>();
        param.add("登録");
        return param;
    }

    /**
     * Get error response when execute delete fail
     * 
     * @return param
     */
    public static ArrayList<String> getErrorMessageDeleteFail() {
        ArrayList<String> param = new ArrayList<String>();
        param.add("削除");
        return param;
    }

    /**
     * Get All Ten
     * 
     * @param date date
     * @param tenCd tenCd
     * @return boolean exists or not
     */
    public List<M006tenm> getListTenCd(String date, String tenCd) {
        M006tenmExample example = new M006tenmExample();
        example.createCriteria().andTenCdEqualTo(tenCd);
        example.setSearchDate(date);

        List<M006tenm> list = this.m006tenmMapper.selectByExample(example);

        return list;
    }
    
    /**
     * 会社コード検索処理を行います。<BR>
     *  @param sHakkoDay 発効日
     * @param sKaisyaCd 会社コード
     * @return M000kaim
     */
    public M000kaim executeKaisyaCd(String sHakkoDay, String sKaisyaCd) {
        M000kaimExample example = new M000kaimExample();
        example.createCriteria().andKaisyaCdEqualTo(sKaisyaCd);
        example.setSearchDate(sHakkoDay);
        List<M000kaim> lstData = this.m000kaimMapper.selectByExample(example);
        if (lstData.size() <= 0 || TYPE_ACT_KBN_DELETED.equals(lstData.get(0).getActKbn())) {
            return null;
        }
        return lstData.get(0);
    }

    /**
     * 事業部コード検索処理を行います。
     * @param sHakkoDay  発効日
     * @param sKaisyaCd  会社コード
     * @param sJigyobuCd  事業部コード
     * @return String
     */
    public M001jgym executeJigyobuCd(String sHakkoDay, String sKaisyaCd, String sJigyobuCd) {
        M001jgymExample example = new M001jgymExample();
        example.setSearchDate(sHakkoDay);
        example.createCriteria().andKaisyaCdEqualTo(sKaisyaCd).andJigyobuCdEqualTo(sJigyobuCd);
        List<M001jgym> lstData = this.m001jgymMapper.selectByExample(example);
        if (lstData.size() <= 0 || TYPE_ACT_KBN_DELETED.equals(lstData.get(0).getActKbn())) {
            return null;
        }
        return lstData.get(0);
    }

    /**
     * 部門コード検索処理を行います。<BR>
     * @param sHakkoDay 発注日
     * @param sBmnCd 部門コード
     * @return String
     */
    public M003bmnm executeBmnCd(String sHakkoDay, String sBmnCd) {
        M003bmnmExample example = new M003bmnmExample();
        example.setSearchDate(sHakkoDay);
        example.createCriteria().andBmnCdEqualTo(sBmnCd);
        List<M003bmnm> lstData = this.m003bmnmMapper.selectByExample(example);
        if (lstData.size() <= 0 || TYPE_ACT_KBN_DELETED.equals(lstData.get(0).getActKbn())) {
            return null;
        }
        return lstData.get(0);
    }

    /**
     * 店舗コード検索処理を行います。<BR>
     * @param sHakkoDay  発効日
     * @param sTenCd 店舗コード
    
     * @return M006tenm
     */
    public M006tenm executeTenCd(String sHakkoDay, String sTenCd) {
        M006tenmExample example = new M006tenmExample();
        example.setSearchDate(sHakkoDay);
        example.createCriteria().andTenCdEqualTo(sTenCd);
        List<M006tenm> lstData = this.m006tenmMapper.selectByExample(example);
        if (lstData.size() <= 0 || TYPE_ACT_KBN_DELETED.equals(lstData.get(0).getActKbn())) {
            return null;
        }
        return lstData.get(0);
    }

    /**
     * 取引先コード検索処理を行います。<BR>
     * @param sHakkoDay 発効日
     * @param sTriCd 取引先コード
     * @return true：処理成功、false：処理失敗
     */
    public M011trim executeTriCd(String sHakkoDay, String sTriCd) {
        M011trimExample example = new M011trimExample();
        example.createCriteria().andTriCdEqualTo(sTriCd);
        example.setSearchDate(sHakkoDay);
        List<M011trim> lstData = this.m011trimMapper.selectByExample(example);
        if (lstData.size() <= 0 || TYPE_ACT_KBN_DELETED.equals(lstData.get(0).getActKbn())) {
            return null;
        }
        return lstData.get(0);
    }
    
    /**
     * 部門名称取得処理.
     * <p>
     * 概要：<br>
     * 部門マスタより部門名を取得します。<br>
     * 
     * @param bmnCd 部門コード
     * @param searchDate :<br>
     *          "UnyoDate":キー項目の存在チェック<br>
     *          "GetumatuDate":帳票出力時の名称取得<br>
     * @return String 部門名
     */
    public String getBmnName(String bmnCd, String searchDate) {
        M003bmnmExample example = new M003bmnmExample();
        example.createCriteria().andBmnCdEqualTo(bmnCd);
        example.setSearchDate(searchDate);

        List<M003bmnm> list = m003bmnmMapper.selectByExample(example);
        if (list.isEmpty()) {
            return StringUtils.EMPTY;
        }
        
        return list.get(0).getBmnNm();
    }

    /**
     * 店舗名称取得処理.
     * <p>
     * 概要：<br>
     * 店舗マスタより店舗名を取得します。<br>
     * 
     * @param kaisyaCd 会社コード
     * @param jigyobuCd 事業部コード
     * @param tenCd 店舗コード
     * @param searchDate :<br>
     *          "UnyoDate":キー項目の存在チェック<br>
     *          "GetumatuDate":帳票出力時の名称取得<br>
     * @return String 店舗名
     */
    public String getTenpoName(String kaisyaCd, String jigyobuCd, String tenCd, String searchDate) {
        String ten7 = kaisyaCd + jigyobuCd + tenCd;
        M006tenmExample example = new M006tenmExample();
        example.createCriteria().andTenCdEqualTo(ten7);

        example.setSearchDate(searchDate);

        List<M006tenm> list = m006tenmMapper.selectByExample(example);
        if (!list.isEmpty()) {
            return CCStringUtil.trimBoth(list.get(0).getTenNm());
        }
        
        return StringUtils.EMPTY;
    }

    /**
     * 事業部名称取得処理.
     * <p>
     * 概要：<br>
     * 事業部マスタより事業部名を取得します。<br>
     * 
     * @param kaisyaCd 会社コード
     * @param jigyobuCd 事業部コード
     * @param searchDate :<br>
     *          "UnyoDate":キー項目の存在チェック<br>
     *          "GetumatuDate":帳票出力時の名称取得<br>
     * @return String 事業部名
     */
    public String getJigyobuName(String kaisyaCd, String jigyobuCd, String searchDate) {
        M001jgymExample example = new M001jgymExample();
        example.createCriteria().andKaisyaCdEqualTo(kaisyaCd).andJigyobuCdEqualTo(jigyobuCd);

        example.setSearchDate(searchDate);

        List<M001jgym> list = m001jgymMapper.selectByExample(example);

        if (list.isEmpty()) {
            return StringUtils.EMPTY;
        }
        
        return CCStringUtil.trimBoth(list.get(0).getJgyNm());
    }

    /**
     * 会社名称取得処理.
     * <p>
     * 概要：<br>
     * 会社マスタより会社名を取得します。<br>
     * 
     * @param kaisyaCd 会社コード
     * @param searchDate :<br>
     *          "UnyoDate":キー項目の存在チェック<br>
     *          "GetumatuDate":帳票出力時の名称取得<br>
     * @return String 会社名 or null if error
     */
    public String getKaisyaName(String kaisyaCd, String searchDate) {
        M000kaimExample example = new M000kaimExample();
        example.createCriteria().andKaisyaCdEqualTo(kaisyaCd);
        example.setSearchDate(searchDate);

        List<M000kaim> list = m000kaimMapper.selectByExample(example);
        if (!list.isEmpty()) {
            return CCStringUtil.trimBoth(list.get(0).getKaiNm());
        }
        
        return StringUtils.EMPTY;
    }
    
    /**
     * get newest jigyobuCd
     * @param kaisyaCd 会社
     * @param searchDate String
     * @return newest jigyobuCd
     */
    public String getNewestJigyobuCd(String kaisyaCd, String searchDate) {
        String result = StringUtils.EMPTY;
        if (CCStringUtil.isEmpty(kaisyaCd)) {
            return result;
        }
        M001jgymExample example = new M001jgymExample();
        example.createCriteria().andKaisyaCdEqualTo(kaisyaCd);
        example.setOrderByClause("HAKKO_DAY DESC");
        example.setSearchDate(searchDate);
        List<CodeMaster> list = this.m001jgymMapper.selectCodeMasterByExample(example);
        for (int i = 0; i < list.size(); i++) {
            if (!TYPE_ACT_KBN_DELETED.equals(list.get(i).getKubun())) {
                result = list.get(i).getCode();
                break;
            }
        }
        return result;
    }
    
    /**
     * get newest tenCd
     * @param kaisyaCd 会社
     * @param jigyobuCd 事業部
     * @param searchDate String
     * @return newest tenCd
     */
    public String getNewestTenCd(String kaisyaCd, String jigyobuCd, String searchDate) {
        Map<String, String> param = new HashMap<String, String>();
        param.put("kaisyaCd", kaisyaCd);
        param.put("jigyobuCd", jigyobuCd);
        param.put("searchDate", searchDate);

        List<CodeMaster> codeMasters = dao.selectMany("selectM006Lastest", param, CodeMaster.class);
        if (codeMasters.size() > 0) {
            return codeMasters.get(0).getCode();
        }

        return StringUtils.EMPTY;
    }
    
    /**
     * get newest tri code
     * @param searchDate string
     * @return triCd 
     */
    public String getNewestTriCd(String searchDate) {
        List<CodeMaster> codeMasters = dao.selectMany("selectM011Lastest", searchDate, CodeMaster.class);
        if (codeMasters.size() > 0) {
            return codeMasters.get(0).getCode();
        }
        return StringUtils.EMPTY;
    }
    
    /**
     * get newest bmn code
     * @param jigyobuCd 事業部
     * @param searchDate string
     * @return triCd 
     */
    public String getNewestBmnCd(String jigyobuCd, String searchDate) {
        Map<String, String> param = new HashMap<String, String>();
        param.put("jigyobuCd", jigyobuCd);
        param.put("searchDate", searchDate);

        List<CodeMaster> codeMasters = dao.selectMany("selectM003Lastest", param, CodeMaster.class);
        if (codeMasters.size() > 0) {
            return codeMasters.get(0).getCode();
        }
        return StringUtils.EMPTY;
    }
    
    /**
     * find name by code and kubun of x007 table
     * @param kbn Kubun to find
     * @param cd cd to find
     * @return string
     */
    public String getNameFromX007KBNM(String kbn, String cd) {
        X007kbnmExample x007kbnmExample = new X007kbnmExample();
        x007kbnmExample.createCriteria().andDeleteFlgEqualTo(DELETED_FLAG_NORMAL).andCdKbnEqualTo(kbn)
                .andActKbnNotEqualTo(TYPE_ACT_KBN_DELETED).andCdEqualTo(cd);
        List<X007kbnm> kbns = x007kbnmMapper.selectByExample(x007kbnmExample);
        if (kbns != null && !kbns.isEmpty()) {
            return kbns.get(0).getNm();
        }
        return StringUtils.EMPTY;
    }
    
    /**
     * 中分類名称取得処理.
     * 
     * @param date 運用日
     * @param jigyobuCd 事業部コード
     * @param bmnCd 部門コード[length = 3]
     * @param chuBnrCd 中分類コード
     * @return String 中分類名
     */
    public String getChuBnrName(String date, String jigyobuCd, String bmnCd, String chuBnrCd) {
        M005bnrmExample example = new M005bnrmExample();
        example.createCriteria().andBmnCdEqualTo(jigyobuCd + bmnCd).andRejiCdEqualTo("0" + bmnCd)
                .andChuBnrCdEqualTo(chuBnrCd).andYotoKbnEqualTo("0000").andShoBnrCdEqualTo("0000");
        example.setSearchDate(date);

        List<M005bnrm> list = this.m005bnrmMapper.selectByExample(example);
        // 取得したデータが存在しない場合
        if (list.isEmpty() || TYPE_ACT_KBN_DELETED.equals(list.get(0).getActKbn())) {
            return StringUtils.EMPTY;
        } 
        return CCStringUtil.trimBoth(list.get(0).getBnrNm());
    }
    
    /**
     * 小分類名称取得処理.
     * 
     * @param date 運用日
     * @param jigyobuCd 事業部コード
     * @param bmnCd 部門コード
     * @param chuBnrCd 中分類コード
     * @param shoBnrCd 小分類コード
     * @return String 小分類名
     */
    public String getShoBnrName(String date, String jigyobuCd, String bmnCd, String chuBnrCd, String shoBnrCd) {
        M005bnrmExample example = new M005bnrmExample();
        example.createCriteria().andBmnCdEqualTo(jigyobuCd + bmnCd).andRejiCdEqualTo("0" + bmnCd)
                .andChuBnrCdEqualTo(chuBnrCd).andYotoKbnEqualTo("0000").andShoBnrCdEqualTo(shoBnrCd);
        example.setSearchDate(date);

        List<M005bnrm> list = this.m005bnrmMapper.selectByExample(example);
        
        if (list.isEmpty()  || TYPE_ACT_KBN_DELETED.equals(list.get(0).getActKbn())) {
            return StringUtils.EMPTY;
        }
        return CCStringUtil.trimBoth(list.get(0).getBnrNm());
    }

    /**
     * get newest jigyobuCd
     * @param date date
     * @param jigyobuCd jigyobuCd
     * @param bmnCd bmnCd
     * @return newest chuBnrCd
     */
    public String getNewestChuBnrCd(String date, String jigyobuCd, String bmnCd) {
        String result = StringUtils.EMPTY;
        
        if (CCStringUtil.isEmpty(jigyobuCd) || CCStringUtil.isEmpty(bmnCd)) {
            return result;
        }
        
        M005bnrmExample example = new M005bnrmExample();
        example.createCriteria().andBmnCdEqualTo(jigyobuCd + bmnCd).andRejiCdEqualTo("0" + bmnCd)
                .andYotoKbnEqualTo("0000").andShoBnrCdEqualTo("0000");
        example.setOrderByClause("HAKKO_DAY DESC");
        example.setSearchDate(date);
        
        List<M005bnrm> list = this.m005bnrmMapper.selectByExample(example);
        
        for (int i = 0; i < list.size(); i++) {
            if (!TYPE_ACT_KBN_DELETED.equals(list.get(i).getActKbn())) {
                result = list.get(i).getChuBnrCd();
                break;
            }
        }
        
        return result;
    }
    
    /**
     * get newest shoBnrCd
     * @param date date
     * @param jigyobuCd jigyobuCd
     * @param bmnCd bmnCd
     * @param chuBnrCd chuBnrCd
     * @return newest shoBnrCd
     */
    public String getNewestShoBnrCd(String date, String jigyobuCd, String bmnCd, String chuBnrCd) {

        String result = StringUtils.EMPTY;

        if (CCStringUtil.isEmpty(jigyobuCd) || CCStringUtil.isEmpty(bmnCd) || CCStringUtil.isEmpty(chuBnrCd)) {
            return result;
        }

        M005bnrmExample example = new M005bnrmExample();
        example.createCriteria().andBmnCdEqualTo(jigyobuCd + bmnCd).andRejiCdEqualTo("0" + bmnCd)
                .andChuBnrCdEqualTo(chuBnrCd).andYotoKbnEqualTo("0000");
        example.setOrderByClause("HAKKO_DAY DESC");
        example.setSearchDate(date);

        List<M005bnrm> list = this.m005bnrmMapper.selectByExample(example);

        for (int i = 0; i < list.size(); i++) {
            if (!TYPE_ACT_KBN_DELETED.equals(list.get(i).getActKbn())) {
                result = list.get(i).getShoBnrCd();
                break;
            }
        }
        
        return result;
    }
    
}
