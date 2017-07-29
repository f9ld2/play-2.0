///////////////////////////////////////////////////////////////////////
// Copyright(C) 2015 NEC Solution Innovators, Ltd. All Rights Reserved.
///////////////////////////////////////////////////////////////////////

/* ====================================================================
 * 機能名称 : 移動実績リスト
 * 改版履歴
 * Rev. 改版年月日   改版者名       内容
 * 1.0   2014.04.18   TUANVT      新規作成
 * =================================================================== */

var app = angular.module('sijp1190Services', ['ngResource']);

app.factory('SIJP1190Report', function($resource) {
    var report = $resource('/core/SIJP1190', {}, {}
    );
    return report;
});

