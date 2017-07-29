///////////////////////////////////////////////////////////////////////
// Copyright(C) 2015 NEC Solution Innovators, Ltd. All Rights Reserved.
///////////////////////////////////////////////////////////////////////

/* ====================================================================
 * 機能名称	: メニュー情報取得
 * 改版履歴
 * Rev.  改版年月日   改版者名       内容
 * 1.0   2014.02.04   H.Okuhara      新規作成
 * =================================================================== */
package jp.co.necsoft.web_md.core.common.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

import jp.co.necsoft.web_md.core.common.mybatis.dao.X002menuMapper;
import jp.co.necsoft.web_md.core.common.mybatis.dto.X002menu;
import jp.co.necsoft.web_md.core.common.mybatis.dto.X002menuExample;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

/**
 * メニュー情報取得Controllerクラス
 *
 */
public class MenuCtrl extends Controller {

    /** X002menuのマッパー */
    @Inject
    private X002menuMapper x002menuMapper;

    /**
     * メニュー項目クラス
     */
    private class MenuItem {
        /**  メニューID */
        private String sid;
        /** メニュータイトル  */
        private String title;

        /**
         *  メニューIDﾞ取得処理を行います。
         * @return メニューID
         */
        public String getSid() {
            return sid;
        }

        /**
         * メニューIDの設定
         * @param sid メニューID
         */
        public void setSid(String sid) {
            this.sid = sid;
        }

        /**
         * メニュータイトルﾞ取得処理を行います。
         * @return メニュータイトル
         */
        public String getTitle() {
            return title;
        }

        /**
         * メニュータイトルの設定
         * @param title メニュータイトル
         */
        public void setTitle(String title) {
            this.title = title;
        }
    }

    /**
     * メニューグループ 
     *
     */
    private class MenuGroup {
        /** メニューグループ 名 */
        private String name;
        /**  メニュー項目のリスト */
        private List<MenuItem> items;

        /**
         * メニューグループ 名ﾞ取得処理を行います。
         * @return メニューグループ 名
         */
        public String getName() {
            return name;
        }

        /**
         * メニューグループ 名の設定
         * @param name メニューグループ 名
         */
        public void setName(String name) {
            this.name = name;
        }

        /**
         * メニュー項目のリストﾞ取得処理を行います。
         * @return メニュー項目のリスト
         */
        public List<MenuItem> getItems() {
            return items;
        }

        /**
         * メニュー項目のリストの設定
         * @param items メニュー項目のリスト
         */
        public void setItems(List<MenuItem> items) {
            this.items = items;
        }
    }

    /**
     * メニュー項目を選択
     * @return 結果
     */
    public Result query() {
        X002menuExample example = new X002menuExample();
        example.createCriteria().andMenuEnblEqualTo((short) 1);
        example.setOrderByClause("MENU_ID");
        List<X002menu> list = this.x002menuMapper.selectByExample(example);
        HashMap<String, List<MenuItem>> map = new HashMap<String, List<MenuItem>>();
        for (X002menu rec : list) {
            // 画面の場合
            if (rec.getMoveType() == 1) {
                List<MenuItem> items = map.get(rec.getGamenId());
                if (items == null) {
                    items = new ArrayList<MenuItem>();
                }
                MenuItem item = new MenuItem();
                item.setSid(rec.getNextGid());
                item.setTitle(rec.getMenuCptn());
                items.add(item);
                map.put(rec.getGamenId(), items);
            }
        }

        List<MenuGroup> groups = new ArrayList<MenuGroup>();
        for (X002menu rec : list) {
            // メニューグループの場合
            if (rec.getMoveType() == 0) {
                MenuGroup group = new MenuGroup();
                List<MenuItem> items = map.get(rec.getNextGid());
                if (items != null) {
                    group.setItems(items);
                }
                group.setName(rec.getMenuCptn());

                groups.add(group);
            }
        }

        return ok(Json.toJson(groups));
    }
}
