// /////////////////////////////////////////////////////////////////////
// Copyright(C) 2015 NEC Solution Innovators, Ltd. All Rights Reserved.
// /////////////////////////////////////////////////////////////////////
/*
 * ====================================================================
 * 
 * 機能名称 :自動発注予定データ一覧（店舗別）
 * 
 * 改版履歴
 * 
 * Rev. 改版年月日 改版者名 内容
 * 
 * 1.0 2015-06-04 NECVN 新規作成
 * 
 * ===================================================================
 */
var app = angular.module('hajp7020Services', ['ngResource']);

app.factory('HAJP7020Control', function($resource) {
    var hajp7020 = $resource('/core/HAJP7020/:jigyobuCd/:hyojiKbn',
            { 	jigyobuCd: '@jigyobuCd',
        hyojiKbn: '@hyojiKbn'});
    return hajp7020;
});

app.factory('HAJP7020Init', function($resource) {
    var hajp7020 = $resource('/core/HAJP7020Init',
            {});
    return hajp7020;
});

