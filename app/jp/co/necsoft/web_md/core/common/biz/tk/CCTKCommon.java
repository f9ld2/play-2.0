// /////////////////////////////////////////////////////////////////////
// Copyright(C) 2015 NEC Solution Innovators, Ltd. All Rights Reserved.
// /////////////////////////////////////////////////////////////////////

/*
 * ====================================================================
 * 
 * 機能名称 : CCTKCommonのcommmonクラス
 * 
 * 改版履歴
 * 
 * Rev. 改版年月日 改版者名 内容
 * 
 * 1.0 2014/03/10 PhatTT 新規作成
 * 
 * ===================================================================
 */
package jp.co.necsoft.web_md.core.common.biz.tk;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.springframework.beans.BeanUtils;

import jp.co.necsoft.web_md.core.common.biz.si.CCSICommon;
import jp.co.necsoft.web_md.core.common.mybatis.dao.M001jgymMapper;
import jp.co.necsoft.web_md.core.common.mybatis.dao.M007kijmMapper;
import jp.co.necsoft.web_md.core.common.mybatis.dao.M009msymMapper;
import jp.co.necsoft.web_md.core.common.mybatis.dao.M017meimMapper;
import jp.co.necsoft.web_md.core.common.mybatis.dao.M020ctlmMapper;
import jp.co.necsoft.web_md.core.common.mybatis.dao.T000kkkmMapper;
import jp.co.necsoft.web_md.core.common.mybatis.dao.T001kksyMapper;
import jp.co.necsoft.web_md.core.common.mybatis.dao.T004kkmmMapper;
import jp.co.necsoft.web_md.core.common.mybatis.dto.M001jgym;
import jp.co.necsoft.web_md.core.common.mybatis.dto.M001jgymExample;
import jp.co.necsoft.web_md.core.common.mybatis.dto.M007kijm;
import jp.co.necsoft.web_md.core.common.mybatis.dto.M007kijmExample;
import jp.co.necsoft.web_md.core.common.mybatis.dto.M009msym;
import jp.co.necsoft.web_md.core.common.mybatis.dto.M009msymExample;
import jp.co.necsoft.web_md.core.common.mybatis.dto.M017meim;
import jp.co.necsoft.web_md.core.common.mybatis.dto.M017meimExample;
import jp.co.necsoft.web_md.core.common.mybatis.dto.M020ctlm;
import jp.co.necsoft.web_md.core.common.mybatis.dto.M020ctlmExample;
import jp.co.necsoft.web_md.core.common.mybatis.dto.T000kkkm;
import jp.co.necsoft.web_md.core.common.mybatis.dto.T000kkkmExample;
import jp.co.necsoft.web_md.core.common.mybatis.dto.T001kksy;
import jp.co.necsoft.web_md.core.common.mybatis.dto.T001kksyExample;
import jp.co.necsoft.web_md.core.common.mybatis.dto.T004kkmm;
import jp.co.necsoft.web_md.core.common.mybatis.dto.T004kkmmExample;

/**
 * 
 *  CCTKCommonのcommmonクラス
 *
 */
public class CCTKCommon {
    /**
     * 削除
     */
    private static final String TYPE_ACT_KBN_DELETED = "9";

    /**T000kkkmMapper*/
    @Inject
    private T000kkkmMapper t000kkkmMapper;

    /**T001kksyMapper*/
    @Inject
    private T001kksyMapper t001kksyMapper;

    /**M001jgymMapper*/
    @Inject
    private M001jgymMapper m001jgymMapper;

    /**M017meimMapper*/
    @Inject
    private M017meimMapper m017meimMapper;

    /**M007kijmMapper*/
    @Inject
    private M007kijmMapper m007kijmMapper;

    /**T004kkmmMapper*/
    @Inject
    private T004kkmmMapper t004kkmmMapper;

    /**M09msymMapper*/
    @Inject
    private M009msymMapper m009msymMapper;

    /**M020ctlmMapper*/
    @Inject
    private M020ctlmMapper m020ctlmMapper;

    /**
     * cmJSICommon
     */
    @Inject
    private CCSICommon ccJSICommon;

    /**
     * 
     * @param tani 単位コード
     * @param date HatDd
     * @return name 単位名称
     */
    public String getTaniName(String tani, String date) {

        if (tani.trim().length() == 1) {
            tani = "0" + tani.trim();
        }

        M017meim m017meim = this.getMeimInfo(tani, date);
        if (m017meim != null) {
            return m017meim.getNm();
        } else {
            return "＊＊＊＊＊＊";
        }
    }

    /**
     * Get the first record from M017meim table.
     * 
     * @param tani 特売単位
     * @param date 日付
     * @return record 特売単位
     */
    public M017meim getMeimInfo(String tani, String date) {
        M017meimExample m017meimExample = new M017meimExample();

        tani = "TANI" + tani;
        m017meimExample.createCriteria().andCdEqualTo(tani).andCdKbnEqualTo("MAS");

        m017meimExample.setSearchDate(date);

        List<M017meim> list = this.m017meimMapper.selectByExample(m017meimExample);

        if (list.isEmpty() || TYPE_ACT_KBN_DELETED.equals(list.get(0).getActKbn())) {
            return null;
        }

        return list.get(0);
    }

    /**
     * Get the first record from T004kkmm table.
     * 
     * @param kaisyaCd 会社コード
     * @param jigyobuCd 事業部コード
     * @param nendo 年度
     * @param kikakuCd 企画コード
     * @param bmnCd bmnCd
     * @param mmCd mmCd
     * @return record table T004kkmm
     */
    public T004kkmm getMMKikakuShohinInfo(String kaisyaCd, String jigyobuCd, String nendo,
            String kikakuCd, String bmnCd, String mmCd) {

        T004kkmmExample t004kkmmExample = new T004kkmmExample();

        t004kkmmExample.createCriteria().andKaisyaCdEqualTo(kaisyaCd).andJigyobuCdEqualTo(jigyobuCd)
                .andNendoEqualTo(nendo).andKikakuCdEqualTo(kikakuCd).andBmnCdEqualTo(jigyobuCd + bmnCd)
                .andMmCdEqualTo(mmCd).andActKbnNotEqualTo(TYPE_ACT_KBN_DELETED);
        List<T004kkmm> list = this.t004kkmmMapper.selectByExample(t004kkmmExample);

        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    /**
     * Get the first record from M007kijm table.
     * 
     * @param janCd janコード
     * @param date 日付
     * @param m007kijm m007kijm
     * @return true or false
     */
    public boolean getM007kijm(String janCd, String date, M007kijm m007kijm) {
        M007kijmExample m007kijmExample = new M007kijmExample();
        m007kijmExample.createCriteria().andJanCdEqualTo(ccJSICommon.cnvJanToJan13(janCd));
        m007kijmExample.setSearchDate(date);

        List<M007kijm> m007kijmList = m007kijmMapper.selectByExample(m007kijmExample);
        if (m007kijmList.isEmpty()) {
            return false;
        }

        this.cloneM007(m007kijmList.get(0), m007kijm);
        if (TYPE_ACT_KBN_DELETED.equals(m007kijmList.get(0).getActKbn())) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * Get the first record from M009msym table.
     * 
     * @param kaisyaCd 会社コード
     * @param jigyobuCd 事業部コード
     * @param janCd janコード
     * @param date 日付
     * @param m009msym m009msym
     * @return true or false
     */
    public boolean getM009msym(String kaisyaCd, String jigyobuCd, String janCd, String date, M009msym m009msym) {
        M009msymExample m009msymExample = new M009msymExample();
        m009msymExample.createCriteria().andJanCdEqualTo(ccJSICommon.cnvJanToJan13(janCd))
                .andTenCdLike(kaisyaCd + jigyobuCd + "%");
        m009msymExample.setSearchDate(date);

        List<M009msym> m009msymList = m009msymMapper.selectByExample(m009msymExample);

        if (m009msymList.isEmpty()) {
            return false;
        }

        for (int i = 0; i < m009msymList.size(); i++) {
            if (!TYPE_ACT_KBN_DELETED.equals(m009msymList.get(i).getActKbn())) {
                this.cloneM009(m009msymList.get(i), m009msym);
                return true;
            }
        }
        this.cloneM009(m009msymList.get(m009msymList.size() - 1), m009msym);
        return false;
    }

    /**
     * Copy properties from source object to target object.
     * 
     * @param m007kijmIn M007kijm
     * @param m007kijmOut M007kijm
     */
    private void cloneM007(M007kijm m007kijmIn, M007kijm m007kijmOut) {
        BeanUtils.copyProperties(m007kijmIn, m007kijmOut);
    }

    /**
     * Copy properties from source object to target object.
     * 
     * @param m009msymIn M009msym
     * @param m009msymOut M009msym
     */
    private void cloneM009(M009msym m009msymIn, M009msym m009msymOut) {
        BeanUtils.copyProperties(m009msymIn, m009msymOut);
    }

    /**
     * 企画マスタ登録のデータ取得処理を行います。(初期値データのセットを行なわない）<BR>
     * @param kaisyaCd  会社コード
     * @param jigyobuCd 事業部コード
     * @param nendo      年度
     * @param kikakuCd  企画コード
     * @param bmnCd     部門コード
     * @return list T000kkkm
     **/
    public T000kkkm getT000(String kaisyaCd, String jigyobuCd, String nendo, String kikakuCd, String bmnCd) {
        T000kkkmExample example = new T000kkkmExample();

        example.createCriteria().andKaisyaCdEqualTo(kaisyaCd).andJigyobuCdEqualTo(jigyobuCd).andNendoEqualTo(nendo)
                .andKikakuCdEqualTo(kikakuCd).andBmnCdEqualTo(jigyobuCd + bmnCd)
                .andActKbnNotEqualTo(TYPE_ACT_KBN_DELETED);

        List<T000kkkm> t000kkkmList = this.t000kkkmMapper.selectByExample(example);

        if (!t000kkkmList.isEmpty()) {
            return t000kkkmList.get(0);
        }

        return null;
    }

    /**
     * Get the first record from T001kksy.
     * 
     * @param kaisyaCd  会社コード
     * @param jigyobuCd 事業部コード
     * @param nendo      年度
     * @param kikakuCd  企画コード
     * @param bmnCd     部門コード
     * @param janCd     コード入力
     * @return exists T001kksy
     **/
    public T001kksy getT001ByJanCd(String kaisyaCd, String jigyobuCd, String nendo, String kikakuCd, String bmnCd,
            String janCd) {
        T001kksyExample example = new T001kksyExample();

        example.createCriteria().andKaisyaCdEqualTo(kaisyaCd).andJigyobuCdEqualTo(jigyobuCd).andNendoEqualTo(nendo)
                .andKikakuCdEqualTo(kikakuCd).andBmnCdEqualTo(jigyobuCd + bmnCd).andJanCdEqualTo(janCd)
                .andActKbnNotEqualTo(TYPE_ACT_KBN_DELETED);

        List<T001kksy> m020ctlmList = this.t001kksyMapper.selectByExample(example);

        if (!m020ctlmList.isEmpty()) {
            return m020ctlmList.get(0);
        }

        return null;
    }

    /**
     * Function get TK flag follow kaisyaCd and jigyobuCd
     * 
     * @param kaisyaCd 会社コード
     * @param jigyobuCd 事業部コード
     * @return string tkフラグ
     */
    public String getTkFlg(String kaisyaCd, String jigyobuCd) {

        M020ctlmExample example = new M020ctlmExample();

        example.createCriteria().andCdEqualTo(kaisyaCd + jigyobuCd);

        List<M020ctlm> m020ctlmList = this.m020ctlmMapper.selectByExample(example);

        if (!m020ctlmList.isEmpty()) {
            return m020ctlmList.get(0).getData().substring(6, 7);
        }

        return null;
    }

    /**
     * Check the jigyobuCd is available in M001jgym table.
     * 
     * @param jigyobuCd 事業部コード
     * @param date 日付
     * @return exist 事業部コード
     */
    public boolean checkExistJigyobuCd(String jigyobuCd, String date) {
        M001jgymExample m001jgymExample = new M001jgymExample();

        m001jgymExample.createCriteria().andJigyobuCdEqualTo(jigyobuCd);
        m001jgymExample.setSearchDate(date);

        List<M001jgym> m001jgymList1 = this.m001jgymMapper.selectByExample(m001jgymExample);
        List<M001jgym> m001jgymList2 = new ArrayList<M001jgym>();
        for (M001jgym m001jgym : m001jgymList1) {
            if (!"9".equals(m001jgym.getExtractFlg())) {
                m001jgymList2.add(m001jgym);
            }
        }
        if (!m001jgymList2.isEmpty()) {
            if (!TYPE_ACT_KBN_DELETED.equals(m001jgymList2.get(0).getActKbn())) {
                return true;
            }
        }

        return false;
    }
}
