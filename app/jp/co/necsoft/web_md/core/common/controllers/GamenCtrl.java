///////////////////////////////////////////////////////////////////////
// Copyright(C) 2015 NEC Solution Innovators, Ltd. All Rights Reserved.
///////////////////////////////////////////////////////////////////////

/* ====================================================================
 * 機能名称	: 画面情報取得
 * 改版履歴
 * Rev.  改版年月日   改版者名       内容
 * 1.0   2014.02.04   H.Okuhara      新規作成
 * =================================================================== */
package jp.co.necsoft.web_md.core.common.controllers;

import java.util.List;

import javax.inject.Inject;

import jp.co.necsoft.web_md.core.common.mybatis.dao.X003gmnmMapper;
import jp.co.necsoft.web_md.core.common.mybatis.dto.X003gmnm;
import jp.co.necsoft.web_md.core.common.mybatis.dto.X003gmnmExample;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

/**
 * 画面情報取得Controllerクラス
 *
 */
public class GamenCtrl extends Controller {

    /**  X003gmnmのマッパー */
    @Inject
    private X003gmnmMapper x003gmnmMapper;

    /**
     * 画面が表示されます
     * @param gamenId 画面ID
     * @return 結果
     */
    public Result get(String gamenId) {
        X003gmnmExample example = new X003gmnmExample();
        example.createCriteria().andGamenIdEqualTo(gamenId);
        List<X003gmnm> list = this.x003gmnmMapper.selectByExample(example);
        if (list.size() == 0) {
            return notFound();
        } else {
            return ok(Json.toJson(list.get(0)));
        }
    }
    
    /**
     * 画面照会
     * @return 結果
     */
    public Result query() {
        X003gmnmExample example = new X003gmnmExample();
        List<X003gmnm> list = this.x003gmnmMapper.selectByExample(example);

        return ok(Json.toJson(list));
    }
}
