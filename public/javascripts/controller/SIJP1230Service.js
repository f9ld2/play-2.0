///////////////////////////////////////////////////////////////////////
//Copyright(C) 2015 NEC Solution Innovators, Ltd. All Rights Reserved.
///////////////////////////////////////////////////////////////////////

/*
 * ====================================================================
 * 
 * 機能名称 :伝票入力プルーフリスト
 * 
 * 改版履歴
 * 
 * Rev. 改版年月日 改版者名 内容
 * 
 * 1.0 20140429 Tinnc 新規作成
 * 
 * ===================================================================
 */
var app = angular.module('sijp1230Services', ['ngResource']);

app.factory('Sijp1230Report', function($resource) {
    var report = $resource('/core/SIJP1230/:tantoCd', {
        tantoCd: '@tantoCd'
    });
    return report;
});

app.factory('Sijp1230Init', function($resource) {
    var  initReport = $resource('/core/SIJP1230/initCond',{});
    return  initReport;
});