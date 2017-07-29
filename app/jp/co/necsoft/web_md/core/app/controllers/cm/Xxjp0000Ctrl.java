///////////////////////////////////////////////////////////////////////
// Copyright(C) 2015 NEC Solution Innovators, Ltd. All Rights Reserved.
///////////////////////////////////////////////////////////////////////

/* ====================================================================
 * 機能名称 : ログイン
 * 改版履歴
 * Rev.  改版年月日   改版者名       内容
 * 1.0   2014.03.26   HaiNP      新規作成
 * =================================================================== */
package jp.co.necsoft.web_md.core.app.controllers.cm;

import java.util.List;

import javax.inject.Inject;

import jp.co.necsoft.web_md.core.app.forms.cm.Xxjp0000CondForm;
import jp.co.necsoft.web_md.core.common.CCMessageConst;
import jp.co.necsoft.web_md.core.common.forms.ErrorResponse;
import jp.co.necsoft.web_md.core.common.forms.UserBean;
import jp.co.necsoft.web_md.core.common.mybatis.dao.M006tenmMapper;
import jp.co.necsoft.web_md.core.common.mybatis.dao.M016tanmMapper;
import jp.co.necsoft.web_md.core.common.mybatis.dao.M020ctlmMapper;
import jp.co.necsoft.web_md.core.common.mybatis.dto.M006tenm;
import jp.co.necsoft.web_md.core.common.mybatis.dto.M006tenmExample;
import jp.co.necsoft.web_md.core.common.mybatis.dto.M016tanm;
import jp.co.necsoft.web_md.core.common.mybatis.dto.M016tanmExample;
import jp.co.necsoft.web_md.core.common.mybatis.dto.M020ctlm;
import jp.co.necsoft.web_md.core.common.mybatis.dto.M020ctlmExample;
import jp.co.necsoft.web_md.core.common.utils.CCDateUtil;
import jp.co.necsoft.web_md.core.common.utils.CCStringUtil;
import jp.co.necsoft.web_md.core.common.views.html.login;
import play.data.Form;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

/**
 * ログインのControllerクラス
 *
 */
public class Xxjp0000Ctrl extends Controller {
    
    /** Format Date yyyyMMdd */
    private static final String DATETIME_FORMAT_DATE = "yyyyMMdd";
    
    /**  削除 */
    private static final String TYPE_ACT_KBN_DELETED = "9";
    
    /** ErrorResponse */
    @Inject
    private ErrorResponse errRes;
    
    /** M016tanmMapper */
    @Inject
    private M016tanmMapper m016tanmMapper;
    
    /** M006tenmMapper */
    @Inject
    private M006tenmMapper m006tenmMapper;
    
    /** M020ctlmのマッパー  */
    @Inject
    private M020ctlmMapper m020ctlmMapper;
    
    /**
     * Login
     * @return html
     */
    public static Result login() {
        return ok(login.render());
    }
    
    /**
     * Logout Action
     * @return result
     */
    public static Result logout() {
        session().clear();
        response().discardCookie(UserBean.AUTH_TOKEN);
        play.cache.Cache.remove(UserBean.USER_BEAN);
        return ok();
    }
    
    /**
     * ログインボタン
     * @return result
     * @throws Exception  Exception
     */
    public Result authenticate() throws Exception {
        @SuppressWarnings({ "unchecked", "rawtypes" })
        Form<Xxjp0000CondForm> emptyForm = new Form(Xxjp0000CondForm.class);
        Form<Xxjp0000CondForm> reqForm = emptyForm.bindFromRequest();
        if (reqForm.hasErrors()) {
            errRes.addErrorInfo(CCMessageConst.MSG_KEY_VALIDATION_ERROR);
            errRes.addErrorInfo(reqForm.errors());
            return badRequest(Json.toJson(errRes));
        } else {
            
            Xxjp0000CondForm req = reqForm.get();
            String tantoCd = req.getTantoCd();
            String password = req.getPassword();
            String systemDate = CCDateUtil.getSysDateFormat(DATETIME_FORMAT_DATE);
            
            M016tanmExample m016tanmExample = new M016tanmExample();
            m016tanmExample.setSearchDate(systemDate);
            m016tanmExample.createCriteria().andTantoCdEqualTo(tantoCd);
            
            List<M016tanm> userList = m016tanmMapper.selectByExample(m016tanmExample);
            //担当者コードが存在しない
            if (userList.size() == 0) {
                errRes.addErrorInfo(CCMessageConst.MSG_KEY_TANTOCD_NOT_EXIST);
                return badRequest(Json.toJson(errRes));
            }
            
            M016tanm m016tanm = userList.get(0);
            ///担当者コードの稼動区分が９
            if (TYPE_ACT_KBN_DELETED.equals(m016tanm.getActKbn())) {
                errRes.addErrorInfo(CCMessageConst.MSG_KEY_TANTOCD_WAS_DELETE);
                return badRequest(Json.toJson(errRes));
            }
            
            if (!password.equals(m016tanm.getPassword())) {
                errRes.addErrorInfo(CCMessageConst.MSG_KEY_PASSWORD_NOT_MATCH);
                return badRequest(Json.toJson(errRes));
            }
            
            //
            String tantoNm = m016tanm.getTantoNm();
            String tantoLvl = String.valueOf(m016tanm.getAtr());
            String tenpoCd = m016tanm.getTenCd();
            
            M006tenmExample m006tenmExample = new M006tenmExample();
            m006tenmExample.setSearchDate(systemDate);
            m006tenmExample.createCriteria().andTenCdEqualTo(tenpoCd);
            List<M006tenm> tenpoList = m006tenmMapper.selectByExample(m006tenmExample);
            if (tenpoList.size() == 0) {
                errRes.addErrorInfo(CCMessageConst.MSG_KEY_TANTOCD_NOT_EXIST);
                return badRequest(Json.toJson(errRes));
            }
            
            String tenpoNm = tenpoList.get(0).getTenNm();
            String unyoDate = this.getUnyoDate();
            String hatUnyoDate = this.getHatUnyoDate();
            String chukanSimeDate = this.getChukanSimeDate();
            String getsuMatsuSimeDate = this.getGetsuMatsuSimeDate();
            
            session().clear();
            session(UserBean.TANTO_CD, CCStringUtil.trimRight(tantoCd));
            session(UserBean.TANTO_NM, CCStringUtil.trimRight(tantoNm));
            session(UserBean.TANTO_LVL, CCStringUtil.trimRight(tantoLvl));
            session(UserBean.TENPO_CD, CCStringUtil.trimRight(tenpoCd));
            session(UserBean.TENPO_NM, CCStringUtil.trimRight(tenpoNm));
            session(UserBean.UNYO_DATE, unyoDate);
            session(UserBean.HAT_UNYO_DATE, hatUnyoDate);
            session(UserBean.CHUKAN_SIME_DATE, chukanSimeDate);
            session(UserBean.GETSU_MATSU_SIME_DATE, getsuMatsuSimeDate);
            
            session(UserBean.SYS_CURRENT_YEAR, CCDateUtil.getSysYear());
            session(UserBean.SYS_CURRENT_MONTH, CCDateUtil.getSysMonth());
            session(UserBean.SYS_CURRENT_DATE, CCDateUtil.getSysDateFormat(CCDateUtil.DATETIME_FORMAT_DATE_YYYYMMDD));
            
            return ok();
        }
    }
    
    /**
     * 運用日を取得する。
     * 
     * @return 運用日
     */
    public String getUnyoDate() {
        M020ctlmExample example = new M020ctlmExample();
        example.createCriteria().andCdEqualTo("DATE");

        String unyoDate;
        List<M020ctlm> list = m020ctlmMapper.selectByExample(example);
        if (list.size() == 1) {
            unyoDate = CCStringUtil.subString(list.get(0).getData(), 0, 8);
            // 日付の妥当性もチェックした方がよいかも
            return unyoDate;
        }
        return "";
    }

    /**
     * 発注運用日を取得する。
     * 
     * @return 発注運用日
     */
    public String getHatUnyoDate() {
        M020ctlmExample example = new M020ctlmExample();
        example.createCriteria().andCdEqualTo("DATE");

        String hatUnyoDate;
        List<M020ctlm> list = m020ctlmMapper.selectByExample(example);
        if (list.size() == 1) {
            hatUnyoDate = list.get(0).getData().substring(8, 16);
            // 日付の妥当性もチェックした方がよいかも
            return hatUnyoDate;
        }
        return "";
    }
    
    /**
     * 前回中間精算締日を取得する。
     * 
     * @return 前回中間精算締日
     */
    public String getChukanSimeDate() {
        M020ctlmExample example = new M020ctlmExample();
        example.createCriteria().andCdEqualTo("YT020");

        String chukanSimeDate;
        List<M020ctlm> list = m020ctlmMapper.selectByExample(example);
        if (list.size() == 1) {
            chukanSimeDate = CCStringUtil.subString(list.get(0).getData(), 0, 8);
            // 日付の妥当性もチェックした方がよいかも
            return chukanSimeDate;
        }
        return "";
    }
    
    /**
     * 前回月末精算締日を取得する。
     * 
     * @return 前回月末精算締日
     */
    public String getGetsuMatsuSimeDate() {
        M020ctlmExample example = new M020ctlmExample();
        example.createCriteria().andCdEqualTo("YT030");

        String getsuMatsuSimeDate;
        List<M020ctlm> list = m020ctlmMapper.selectByExample(example);
        if (list.size() == 1) {
            getsuMatsuSimeDate = list.get(0).getData().substring(0, 8);
            // 日付の妥当性もチェックした方がよいかも
            return getsuMatsuSimeDate;
        }
        return "";
    }
    
}
