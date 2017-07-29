// /////////////////////////////////////////////////////////////////////
// Copyright(C) 2015 NEC Solution Innovators, Ltd. All Rights Reserved.
// /////////////////////////////////////////////////////////////////////
/*
 * ====================================================================
 * 
 * 機能名称 :委託精算確認
 * 
 * 改版履歴
 * 
 * Rev. 改版年月日 改版者名 内容
 * 
 * 1.0 2015-06-04 NECVN 新規作成
 * 
 * ===================================================================
 */
var app = angular.module('sijp7100Services', ['ngResource']);

app.factory('SIJP7100Control', function($resource) {
    var sijp7100 = $resource('/core/SIJP7100/:jigyoubuCd/:tenCd/:taisyoY/:taisyoM',
            { jigyoubuCd: '@jigyoubuCd',tenCd: '@tenCd',taisyoY: '@taisyoY',taisyoM: '@taisyoM'});
    return sijp7100;
});

app.factory('SIJP7100Export', function($resource) {
    var sijp7100 = $resource('/core/SIJP7100Export/:jigyoubuCd/:tenCd/:taisyoY/:taisyoM',
            { jigyoubuCd: '@jigyoubuCd',tenCd: '@tenCd',taisyoY: '@taisyoY',taisyoM: '@taisyoM'});
    return sijp7100;
});

app.factory('SIJP7100Init', function($resource) {
    var sijp7100 = $resource('/core/SIJP7100Init',
            {});
    return sijp7100;
});

