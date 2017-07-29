///////////////////////////////////////////////////////////////////////
// Copyright(C) 2014 NEC Solution Innovators, Ltd. All Rights Reserved.
///////////////////////////////////////////////////////////////////////

/* ====================================================================
 * 機能名称 : 棚卸実施チェックリスト
 * 改版履歴
 * Rev. 改版年月日   改版者名       内容
 * 1.0   2015.05.07   TrieuVN      新規作成
 * =================================================================== */
var app = angular.module('zijp7010Services', ['ngResource']);
app.factory('ZIJP7010Report', function($resource) {
    var data = $resource('/core/ZIJP7010/:jigyobuCd/:tnaUnyDd', {
        jigyobuCd : '@jigyobuCd',
        tnaUnyDd : '@tnaUnyDd'
    }, {});
    return data;
});



app.factory('Zijp7010Init', function($resource) {
    var sijp7040 = $resource('/core/ZIJP7010Init', {});
    return sijp7040;
});