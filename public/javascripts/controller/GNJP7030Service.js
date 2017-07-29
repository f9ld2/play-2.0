///////////////////////////////////////////////////////////////////////
// Copyright(C) 2015 NEC Solution Innovators, Ltd. All Rights Reserved.
///////////////////////////////////////////////////////////////////////

/* ====================================================================
 * 機能名称 : デモ棚リスト
 * 改版履歴
 * Rev. 改版年月日   改版者名       内容
 * 1.0   205.06.25   chiennt      新規作成
 * =================================================================== */
var app = angular.module('gnjp7030Services', ['ngResource']);
app.factory('GNJP7030Report', function($resource) {
    var report = $resource('/core/GNJP7030/:hatYmd', {
    	hatYmd : '@hatYmd'
    }, {});
    return report;
});

app.factory('GNJP7030Init', function($resource) {
    var urjp7030 = $resource('/core/GNJP7030/initCond',
            {});
    return urjp7030;
});