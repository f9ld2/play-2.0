///////////////////////////////////////////////////////////////////////
// Copyright(C) 2014 NEC Solution Innovators, Ltd. All Rights Reserved.
///////////////////////////////////////////////////////////////////////

/* ====================================================================
 * 機能名称 : 棚卸カテゴリー別合計表
 * 改版履歴
 * Rev. 改版年月日   改版者名       内容
 * 1.0   2015.05.08   TrieuVN      新規作成
 * =================================================================== */
var app = angular.module('zijp7040Services', ['ngResource']);
app.factory('ZIJP7040Report', function($resource) {
    var data = $resource('/core/ZIJP7040/:jigyobuCd/:tnaUnyDd', {
        jigyobuCd : '@jigyobuCd',
        tnaUnyDd : '@tnaUnyDd'
    }, {});
    return data;
});

app.factory('Zijp7040Init', function($resource) {
    var zijp7040 = $resource('/core/ZIJP7040Init',
            {});
    return zijp7040;
});