///////////////////////////////////////////////////////////////////////
//Copyright(C) 2015 NEC Solution Innovators, Ltd. All Rights Reserved.
///////////////////////////////////////////////////////////////////////

/*
 * ====================================================================
 * 
 * 機能名称 : 生鮮納品書
 * 
 * 改版履歴
 * 
 * Rev. 改版年月日 改版者名 内容
 * 
 * 1.0 2014.05.03 TuanTQ 新規作成
 * 
 * ===================================================================
 */
var app = angular.module('sijp1150Services', ['ngResource']);
app.factory('Sijp1150Report', function($resource) {
    var report = $resource('/core/SIJP1150/:kaisyaCd/:jigyoubuCd', {
        kaisyaCd:'@kaisyaCd',jigyoubuCd:'@jigyoubuCd'
    }, {});
    return report;
});