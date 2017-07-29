///////////////////////////////////////////////////////////////////////
// Copyright(C) 2015 NEC Solution Innovators, Ltd. All Rights Reserved.
///////////////////////////////////////////////////////////////////////

/* ====================================================================
 * 機能名称 : 仮締設定入力
 * 改版履歴
 * Rev. 改版年月日   改版者名       内容
 * 1.0   2014.03.28   PhucLT      新規作成
 * =================================================================== */

var app = angular.module('sijp0190Services', ['ngResource']);

app.factory('SIJP0190', function($resource) {
    var control = $resource('/core/SIJP0190', {}, {
        'show': {
            method: 'GET'
        },
        'save': {
            method: 'POST'
        },
    });
    return control;
});