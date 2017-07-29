///////////////////////////////////////////////////////////////////////
// Copyright(C) 2015 NEC Solution Innovators, Ltd. All Rights Reserved.
///////////////////////////////////////////////////////////////////////

/* ====================================================================
 * 機能名称 : セット品設定リスト
 * 改版履歴
 * Rev. 改版年月日   改版者名       内容
 * 1.0   205.06.25   chiennt      新規作成
 * =================================================================== */
var app = angular.module('gnjp7000Services', ['ngResource']);
app.factory('GNJP7000Report', function($resource) {
    var report = $resource('/core/GNJP7000/:hatYmd/:jigyobuCd', {
    	hatYmd : '@hatYmd',
    	jigyobuCd: '@jigyobuCd'
    }, {});
    return report;
});

app.factory('GNJP7000Init', function($resource) {
    var gnjp7000 = $resource('/core/GNJP7000/initCond',
            {});
    return gnjp7000;
});