///////////////////////////////////////////////////////////////////////
// Copyright(C) 2015 NEC Solution Innovators, Ltd. All Rights Reserved.
///////////////////////////////////////////////////////////////////////

/* ====================================================================
 * 機能名称 : ログイン
 * 改版履歴
 * Rev.  改版年月日   改版者名       内容
 * 1.0   2014.03.26   HaiNP      新規作成
 * =================================================================== */
var app = angular.module('xxjp0000Services', ['ngResource']);

app.factory('Gamen', function($resource) {
    var gamen = $resource('/core/gamen/:gamenId', {gamenId: '@gamenId'}, {});
    return gamen;
});

app.factory('ComInfo', function() {
    return {
        sid: '',
        title: '',
        message: '',
        level: ''
    };
});