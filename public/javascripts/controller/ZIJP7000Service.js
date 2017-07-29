///////////////////////////////////////////////////////////////////////
// Copyright(C) 2014 NEC Solution Innovators, Ltd. All Rights Reserved.
///////////////////////////////////////////////////////////////////////

/* ====================================================================
 * 機能名称 : 棚卸実施チェックリスト
 * 改版履歴
 * Rev. 改版年月日   改版者名       内容
 * 1.0   2015.06.09   NECVN      新規作成
 * =================================================================== */
var app = angular.module('zijp7000Services', ['ngResource']);
app.factory('ZIJP7000Report', function($resource) {
    var data = $resource('/core/ZIJP7000/:tnaUnyDd/:jigyobuCd', {
    	tnaUnyDd : '@tnaUnyDd',
    	jigyobuCd : '@jigyobuCd'
    }, {});
    return data;
});

app.factory('Zijp7000Init', function($resource) {
    var zijp7000 = $resource('/core/ZIJP7000Init',{});
    return zijp7000;
});