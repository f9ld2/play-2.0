///////////////////////////////////////////////////////////////////////
//Copyright(C) 2015 NEC Solution Innovators, Ltd. All Rights Reserved.
///////////////////////////////////////////////////////////////////////

/* ====================================================================
 * 機能名称 : 企画情報
 * 改版履歴
 * Rev. 改版年月日   改版者名       内容
 * 1.0   2014.04.29   VuQQ      新規作成
 * =================================================================== */
var app = angular.module('tkjp1060Services', ['ngResource']);

app.factory('TKJP1060Report', function($resource) {
    var ButsuryuReport = $resource('/core/TKJP1060/:kaisyaCd/:jigyobuCd',
            {kaisyaCd: '@kaisyaCd', jigyobuCd: '@jigyobuCd'});
    return ButsuryuReport;
});

app.factory('TKJP1060ReportInit', function($resource) {
    var maker = $resource('/core/TKJP1060/:sId', 
            {sId : '@sId'},
            {initCond: {method: 'GET'}});

    return maker;	
});