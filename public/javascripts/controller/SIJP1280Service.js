///////////////////////////////////////////////////////////////////////
//Copyright(C) 2015 NEC Solution Innovators, Ltd. All Rights Reserved.
///////////////////////////////////////////////////////////////////////

/*
 * ====================================================================
 * 
 * 機能名称 :店間移動リスト
 * 
 * 改版履歴
 * 
 * Rev. 改版年月日 改版者名 内容
 * 
 * 1.0 20140505 Tinnc 新規作成
 * 
 * ===================================================================
 */
var app = angular.module('sijp1280Services', ['ngResource']);

app.factory('Sijp1280Report', function($resource) {
    var report = $resource('/core/SIJP1280/:syukaYmdSt/:syukaYmdEd', {
        syukaYmdSt: '@syukaYmdSt', syukaYmdEd: '@syukaYmdEd'
    });
    return report;
});