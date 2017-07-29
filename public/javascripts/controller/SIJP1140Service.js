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
var app = angular.module('sijp1140Services', ['ngResource']);
app.factory('Sijp1140Report', function($resource) {
    var report = $resource('/core/SIJP1140/:kaisyaCd/:jigyoubuCd/:dpyKbn', {
        kaisyaCd:'@kaisyaCd',jigyoubuCd:'@jigyoubuCd',dpyKbn:'@dpyKbn'
    }, {});
    return report;
});