// /////////////////////////////////////////////////////////////////////
// Copyright(C) 2015 NEC Solution Innovators, Ltd. All Rights Reserved.
// /////////////////////////////////////////////////////////////////////

/*
 * ====================================================================
 * 
 * 機能名称 : 月次・日割予算チェックリスト
 * 
 * 改版履歴
 * 
 * Rev. 改版年月日 改版者名 内容
 * 
 * 1.0 2014.07.01 NES石井 新規作成
 * 
 * ===================================================================
 */

var app = angular.module('urjp1180Services', ['ngResource']);
app.factory('Urjp1180Report', function($resource) {
    
    var report = $resource('/core/URJP1180/:kaisyaCd/:jigyobuCd/:taisyodateY/:taisyodateM', {
        kaisyaCd : '@kaisyaCd',
        jigyobuCd : '@jigyobuCd',
        taisyodateY :'@taisyodateY',
        taisyodateM : '@taisyodateM',
    },{
    });
    return report;
});