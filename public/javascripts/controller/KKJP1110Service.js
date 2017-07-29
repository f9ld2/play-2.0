// /////////////////////////////////////////////////////////////////////
// Copyright(C) 2015 NEC Solution Innovators, Ltd. All Rights Reserved.
// /////////////////////////////////////////////////////////////////////

/*
 * ====================================================================
 * 
 * 機能名称 : 買掛金元帳
 * 
 * 改版履歴
 * 
 * Rev. 改版年月日 改版者名 内容
 * 
 * 1.0 2014-05-22 TUCTVZ 新規作成
 * 
 * ===================================================================
 */
var app = angular.module('kkjp1110Services', ['ngResource']);
app.factory('KKJP1110Job', function($resource) {
    var job = $resource('/core/KKJP1110/:taisyoY/:taisyoM', {
        taisyoY: '@taisyoY', taisyoM: '@taisyoM'
    }, {});
    return job;
});

