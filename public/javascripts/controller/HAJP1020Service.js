// /////////////////////////////////////////////////////////////////////
// Copyright(C) 2014 NEC Solution Innovators, Ltd. All Rights Reserved.
// /////////////////////////////////////////////////////////////////////

/*
 * ====================================================================
 * 
 * 機能名称 : 発注プルーフリスト出力
 * 
 * 改版履歴
 * 
 * Rev. 改版年月日 改版者名 内容
 * 
 * 1.0 2014.04.20 Tinnc 新規作成
 * 
 * ===================================================================
 */
var app = angular.module('hajp1020Services', [ 'ngResource' ]);

app.factory('Hajp1020Report', function($resource) {
    var report = $resource('/core/HAJP1020/:kaisyaCd/:hachuSt/:hachuEd', {
    	kaisyaCd: '@kaisyaCd', hachuSt: '@hachuSt', hachuEd: '@hachuEd'
    }, {});
    return report;
});