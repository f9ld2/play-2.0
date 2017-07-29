// /////////////////////////////////////////////////////////////////////
// Copyright(C) 2015 NEC Solution Innovators, Ltd. All Rights Reserved.
// /////////////////////////////////////////////////////////////////////

/*
 * ====================================================================
 *
 * 機能名称 : ＥＯＳ発注明細リスト出力
 *
 * 改版履歴
 *
 * Rev. 改版年月日 改版者名 内容
 *
 * 1.0 2014.04.25 Tinnc 新規作成
 *
 * ===================================================================
 */
var app = angular.module('hajp1040Services', ['ngResource']);

app.factory('Hajp1040Report', function($resource) {
    var report = $resource('/core/HAJP1040/:kaisyaCd/:hatYmdSt/:hatYmdEd', {
        kaisyaCd: '@kaisyaCd',
        hatYmdSt: '@hatYmdSt',
        hatYmdEd: '@hatYmdEd'
    }, {});
    return report;
});