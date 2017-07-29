///////////////////////////////////////////////////////////////////////
// Copyright(C) 2015 NEC Solution Innovators, Ltd. All Rights Reserved.
///////////////////////////////////////////////////////////////////////

/* ====================================================================
 * 機能名称	: メッセージ情報取得
 * 改版履歴
 * Rev.  改版年月日   改版者名       内容
 * 1.0   2014.04.04   H.Okuhara      新規作成
 * =================================================================== */
package jp.co.necsoft.web_md.core.common.controllers;

import java.util.List;

import javax.inject.Inject;

import jp.co.necsoft.web_md.core.common.mybatis.dao.C001msgfMapper;
import jp.co.necsoft.web_md.core.common.mybatis.dto.C001msgf;
import jp.co.necsoft.web_md.core.common.mybatis.dto.C001msgfExample;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

/**
 * メッセージ情報取得Controllerクラス
 *
 */
public class MessageCtrl extends Controller {

    /** C001msgfのマッパー */
    @Inject
    private C001msgfMapper c001msgfMapper;

    /**
     * キーでメッセージが表示されます
     * @param key メッセージ鍵
     * @return 結果
     */
    public Result get(String key) {
        C001msgfExample example = new C001msgfExample();
        example.createCriteria().andKeyEqualTo(key);
        List<C001msgf> list = this.c001msgfMapper.selectByExample(example);
        if (list.size() == 0) {
            return notFound();
        } else {
            return ok(Json.toJson(list.get(0)));
        }
    }
    
    /**
     * キーでメッセージが表示されます
     * @param key メッセージ鍵
     * @return 結果
     */
    public Result search(String key) {
        C001msgfExample example = new C001msgfExample();
        example.createCriteria().andKeyLike(key + "%");
        List<C001msgf> list = this.c001msgfMapper.selectByExample(example);
        if (list.size() == 0) {
            return notFound();
        } else {
            return ok(Json.toJson(list));
        }
    }

    /**
     * メッセージ情報取得を選択
     * @return 結果
     */
    public Result query() {
        C001msgfExample example = new C001msgfExample();
        List<C001msgf> list = this.c001msgfMapper.selectByExample(example);

        return ok(Json.toJson(list));
    }
}
