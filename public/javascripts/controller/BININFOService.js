///////////////////////////////////////////////////////////////////////
// Copyright(C) 2015 NEC Solution Innovators, Ltd. All Rights Reserved.
///////////////////////////////////////////////////////////////////////

/* ====================================================================
 * 機能名称 : 商品マスタレイアウト変更
 * 改版履歴
 * Rev. 改版年月日   改版者名       内容
 * 1.0   2014.09.05   hainp      新規作成
 * =================================================================== */
var app = angular.module('binInfoServices', ['ngResource']);

app.factory('BININFO', function($resource) {
    var control = $resource('/core/BININFO/:triCd', 
        {triCd: '@triCd'}, {
        'query': {
            method: 'GET', isArray: false
        }
    });
    return control;
});