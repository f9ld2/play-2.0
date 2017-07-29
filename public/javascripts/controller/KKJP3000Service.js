// /////////////////////////////////////////////////////////////////////
// Copyright(C) 2014 NEC Soft, Ltd. All Rights Reserved.
// /////////////////////////////////////////////////////////////////////

/*
 * ====================================================================
 *
 * 機能名称 : 仕入本締後処理
 *
 * 改版履歴
 *
 * Rev. 改版年月日 改版者名 内容
 *
 * 1.0 2014.9.30   NES田中 新規作成
 *
 * ===================================================================
 */
var app = angular.module('kkjp3000Services', ['ngResource']);
app.factory('Kkjp3000Job', function($resource) {
    var job = $resource('/core/KKJP3000/:kaisyaCd/:taisyodateY/:taisyodateM', {
    	kaisyaCd: '@kaisyaCd', taisyodateY: '@taisyodateY', taisyodateM: '@taisyodateM'
    }, {});
    return job;
});