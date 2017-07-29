// /////////////////////////////////////////////////////////////////////
// Copyright(C) 2015 NEC Solution Innovators, Ltd. All Rights Reserved.
// /////////////////////////////////////////////////////////////////////

/*
 * ====================================================================
 * 
 * 機能名称 : 移動伝票入力
 * 
 * 改版履歴
 * 
 * Rev. 改版年月日 改版者名 内容
 * 
 * 1.0 2014.04.05 Hungtb 新規作成
 * 
 * ===================================================================
 */
package jp.co.necsoft.web_md.core.common.biz.kk;

import javax.inject.Inject;

import jp.co.necsoft.web_md.core.common.mybatis.dao.K003rshrMapper;
import jp.co.necsoft.web_md.core.common.mybatis.dao.K004rssiMapper;
import jp.co.necsoft.web_md.core.common.mybatis.dto.K003rshrExample;
import jp.co.necsoft.web_md.core.common.mybatis.dto.K004rssiExample;
import play.mvc.Controller;

/**
 * 
 * @author hungtbz
 * @since 2014/03/11
 */
public class CCKKEditDB extends Controller {

    /** Mybatis mapper. */
    @Inject
    private K003rshrMapper k003rshrMapper;

    /** Mybatis mapper. */
    @Inject
    private K004rssiMapper k004rssiMapper;

    /**
    * 支払ファイルのデータ有無をチェック.
    * <p>
    * 機能概要：<br>
    * キー項目で指定された支払ファイルのデータ有無を取得する。<br>
    * <p> 
    * @param sSyoriNo ：処理NO.
    * @return true：データ有り、false：データ無し
    */
    public boolean isK003RSHR(String sSyoriNo) {
        K003rshrExample example = new K003rshrExample();
        example.createCriteria().andShrSyoriNoEqualTo(sSyoriNo);
        int count = k003rshrMapper.countByExample(example);

        return count > 0;
    }

    /**
    * 相殺ファイルのデータ有無をチェック.
    * <p>
    * 機能概要：<br>
    *　キー項目で指定された相殺ファイルのデータ有無を取得する。<br>
    * <p> 
    * @param sSyoriNo ：処理NO.
    * @return true：データ有り、false：データ無し
    */
    public boolean isK004RSSI(String sSyoriNo) {
        K004rssiExample example = new K004rssiExample();
        example.createCriteria().andShrSyoriNoEqualTo(sSyoriNo);
        int count = k004rssiMapper.countByExample(example);

        return count > 0;
    }
}
