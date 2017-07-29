// /////////////////////////////////////////////////////////////////////
// Copyright(C) 2015 NEC Solution Innovators, Ltd. All Rights Reserved.
// /////////////////////////////////////////////////////////////////////

/*
 * ====================================================================
 * 
 * 機能名称 : 共通システム・コンテキストクラス
 * 
 * 改版履歴
 * 
 * Rev. 改版年月日 改版者名 内容
 * 
 * 1.0 2014.02.04 H.Okuhara 新規作成
 * 
 * ===================================================================
 */
package jp.co.necsoft.web_md.core.common;

import java.util.List;

import javax.inject.Inject;

import jp.co.necsoft.web_md.core.common.forms.UserBean;
import jp.co.necsoft.web_md.core.common.mybatis.dao.M020ctlmMapper;
import jp.co.necsoft.web_md.core.common.mybatis.dto.M020ctlm;
import jp.co.necsoft.web_md.core.common.mybatis.dto.M020ctlmExample;
import jp.co.necsoft.web_md.core.common.utils.CCStringUtil;
import jp.co.necsoft.web_md.core.common.utils.DataUtil;
import play.Play;

/**
 * 共通システム・コンテキストクラス
 * 
 */
public class CCSystemContext {

    /** M020ctlmのマッパー  */
    @Inject
    private M020ctlmMapper m020ctlmMapper;

    /**
     * ログインユーザの所属する店舗の店舗コードを取得する。
     * 
     * @return 店舗コード
     **/
    public String getTenpoCode() {
        // ログイン機能と一緒に実装する。
        String tenpoCode = play.mvc.Http.Context.current().session().get(UserBean.TENPO_CD);
        if (tenpoCode != null) {
            return tenpoCode;
        }
        return " ";
    }

    /**
     * ログインユーザの所属する店舗の店舗名を取得する。
     * 
     * @return 店舗名
     **/
    public String getTenpoName() {
        // ログイン機能と一緒に実装する。
        String tenpoName = play.mvc.Http.Context.current().session().get(UserBean.TENPO_NM);
        if (tenpoName != null) {
            return tenpoName;
        }
        return "";
    }

    /**
     * ログインユーザの担当者コードを取得する。
     * 
     * @return 担当者コード
     **/
    public String getTantoCode() {
        // ログイン機能と一緒に実装する。
        String tantoCd = play.mvc.Http.Context.current().session().get(UserBean.TANTO_CD);
        if (tantoCd != null) {
            return tantoCd;
        }

        return "---------";
    }

    /**
     * ログインユーザの担当者名を取得する。
     * 
     * @return 担当者名
     **/
    public String getTantoName() {
        // ログイン機能と一緒に実装する。
        String tantoName = play.mvc.Http.Context.current().session().get(UserBean.TANTO_NM);
        if (tantoName != null) {
            return tantoName;
        }
        return "---------";
    }

    /**
     * ログインユーザの担当者レベルを取得する。
     * 
     * @return 担当者レベル
     **/
    public int getTantoLevel() {
        // ログイン機能と一緒に実装する。
        int tantoLevel = -1;
        String tantoLvl = play.mvc.Http.Context.current().session().get(UserBean.TANTO_LVL);
        if (tantoLvl != null) {
            tantoLevel = Integer.parseInt(tantoLvl);
            return tantoLevel;
        }
        return -1;
    }

    /**
     * クライアントのIPアドレスを取得する。
     * 
     * @return クライアントのIPアドレス
     **/
    public String getIPAddress() {
        return play.mvc.Http.Context.current().request().remoteAddress();
    }

    /**
     * クライアントの端末名を取得する。
     * 
     * @return クライアントの端末名
     **/
    public String getTermName() {
        // ログイン機能と一緒に実装する。
        String termnm = play.mvc.Http.Context.current().request().remoteAddress();
        termnm = DataUtil.convIPAddr2Hex(termnm, 8);
        if (termnm != null) {
            return termnm;
        }
        return "--------";
    }

    /*****************************************************************
     * 端末ＩＤ取得処理.
     * <p>
     * 機能概要：<br>
     * 　端末ＩＤの取得を行います。<br>
     * 
     * @param なし
     * @return 端末ＩＤ
     *****************************************************************/
    public String getTermId() {
        String sTermId = getTermName().trim();

        if (sTermId.length() > 8) {
            sTermId = sTermId.substring(0, 8);
        }
        return sTermId;
    }

    /**
     * 運用日を取得する。
     * 
     * @return 運用日
     */
    public String getUnyoDate() {
        String unyoDate = play.mvc.Http.Context.current().session().get(UserBean.UNYO_DATE);
        if (CCStringUtil.isEmpty(unyoDate)) {
            M020ctlmExample example = new M020ctlmExample();
            example.createCriteria().andCdEqualTo("DATE");
    
            List<M020ctlm> list = m020ctlmMapper.selectByExample(example);
            if (list.size() == 1) {
                unyoDate = CCStringUtil.subString(list.get(0).getData(), 0, 8);
                // 日付の妥当性もチェックした方がよいかも
                return unyoDate;
            }
        }
        return unyoDate;
    }

    /**
     * 発注運用日を取得する。
     * 
     * @return 発注運用日
     */
    public String getHatUnyoDate() {
        String hatUnyoDate = play.mvc.Http.Context.current().session().get(UserBean.HAT_UNYO_DATE);
        
        if (CCStringUtil.isEmpty(hatUnyoDate)) {
            M020ctlmExample example = new M020ctlmExample();
            example.createCriteria().andCdEqualTo("DATE");
    
            List<M020ctlm> list = m020ctlmMapper.selectByExample(example);
            if (list.size() == 1) {
                hatUnyoDate = list.get(0).getData().substring(8, 16);
                // 日付の妥当性もチェックした方がよいかも
                return hatUnyoDate;
            }
        }
        return hatUnyoDate;
    }

    /**
     * 仕入実績確定日付を取得しセット
     * 
     * @return 仕入実績確定日
     */
    public String getShiireKakuDate() {
        M020ctlmExample example = new M020ctlmExample();
        example.createCriteria().andCdEqualTo("DATE");

        String hatUnyoDate;
        List<M020ctlm> list = m020ctlmMapper.selectByExample(example);
        if (list.size() == 1) {
            hatUnyoDate = list.get(0).getData().substring(16, 24);
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
        String chukanSimeDate = play.mvc.Http.Context.current().session().get(UserBean.CHUKAN_SIME_DATE);
        if (CCStringUtil.isEmpty(chukanSimeDate)) {
            M020ctlmExample example = new M020ctlmExample();
            example.createCriteria().andCdEqualTo("YT020");
    
            List<M020ctlm> list = m020ctlmMapper.selectByExample(example);
            if (list.size() == 1) {
                chukanSimeDate = CCStringUtil.subString(list.get(0).getData(), 0, 8);
                // 日付の妥当性もチェックした方がよいかも
                return chukanSimeDate;
            }
        }
        return chukanSimeDate;
    }
    
    /**
     * 前回月末精算締日を取得する。
     * 
     * @return 前回月末精算締日
     */
    public String getGetsuMatsuSimeDate() {
        String getsuMatsuSimeDate = play.mvc.Http.Context.current().session().get(UserBean.GETSU_MATSU_SIME_DATE);
        
        if (CCStringUtil.isEmpty(getsuMatsuSimeDate)) {
            M020ctlmExample example = new M020ctlmExample();
            example.createCriteria().andCdEqualTo("YT030");
    
            List<M020ctlm> list = m020ctlmMapper.selectByExample(example);
            if (list.size() == 1) {
                getsuMatsuSimeDate = list.get(0).getData().substring(0, 8);
                // 日付の妥当性もチェックした方がよいかも
                return getsuMatsuSimeDate;
            }
        }
        return getsuMatsuSimeDate;
    }
    
    /**
     * 中間精算設定日付を取得する。
     * 
     * @return 中間精算設定日付
     */
    public String getChuKanSeisanDate() {
        String chukanseisandate = "";
        M020ctlmExample example = new M020ctlmExample();
        example.createCriteria().andCdEqualTo("YT010");
        List<M020ctlm> list = m020ctlmMapper.selectByExample(example);
        if (list.size() == 1) {
            chukanseisandate = list.get(0).getData().substring(0, 2);
            // 日付の妥当性もチェックした方がよいかも
            return chukanseisandate;
        }
        return chukanseisandate;
    }
    
    /**
     * get default kaisyaCd
     * @return kaisyaCd
     */
    public String getDefaultKaisyaCd() {
        return getContextProperty("cc.app.cm.kaisyaCd");
    }

    /**
     * コンテキストプロパティを取得します。
     * @param key プロパティキー
     * @return プロパティ値
     */
    public String getContextProperty(String key) {
        if (key == null) {
            return "";
        }

        String propertyValue = Play.application().configuration().getString(key);
        if (propertyValue == null) {
            return "";
        }
        return propertyValue;
    }
}
