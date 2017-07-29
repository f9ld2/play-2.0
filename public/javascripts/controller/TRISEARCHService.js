///////////////////////////////////////////////////////////////////////
// Copyright(C) 2015 NEC Solution Innovators, Ltd. All Rights Reserved.
///////////////////////////////////////////////////////////////////////

/* ====================================================================
 * 機能名称 : 店別実績計算書
 * 改版履歴
 * Rev. 改版年月日   改版者名       内容
 * 1.0   2014.05.07   phuclt      新規作成
 * =================================================================== */
var app = angular.module('triSearchServices', ['ngResource']);

app.factory('TRISEARCH', function($resource) {
    var control = $resource('/core/TRISEARCH', {}, {
        'query': {
            method: 'GET', isArray: true
        },
        'save': {
            method: 'POST'
        },
        'update': {
            method: 'PUT'
        },
        'delete': {
            method: 'DELETE'
        }
    });
    return control;
});