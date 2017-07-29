///////////////////////////////////////////////////////////////////////
// Copyright(C) 2015 NEC Solution Innovators, Ltd. All Rights Reserved.
///////////////////////////////////////////////////////////////////////

/* ====================================================================
 * 機能名称 :  仕入先別納品率ﾘｽﾄ.
 * 改版履歴
 * Rev. 改版年月日   改版者名       内容
 * 1.0   2014.04.17   TUANVT      新規作成
 * =================================================================== */

var app = angular.module('sijp1180Services', ['ngResource']);

app.factory('SIJP1180Report', function($resource) {
    var report = $resource('/core/SIJP1180', {}, {}
    );
    return report;
});

