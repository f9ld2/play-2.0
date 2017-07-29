///////////////////////////////////////////////////////////////////////
// Copyright(C) 2015 NEC Solution Innovators, Ltd. All Rights Reserved.
///////////////////////////////////////////////////////////////////////

/* ====================================================================
 * 機能名称 : 産地検索
 * 改版履歴
 * Rev. 改版年月日   改版者名       内容
 * 1.0   2014.10.14   phuclt      新規作成
 * =================================================================== */
var app = angular.module('santiSearchServices', ['ngResource']);

app.factory('SANTISEARCH', function($resource) {
    var control = $resource('/core/SANTISEARCH', {}, {
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