// /////////////////////////////////////////////////////////////////////
// Copyright(C) 2014 NEC Solution Innovators, Ltd. All Rights Reserved.
// /////////////////////////////////////////////////////////////////////

/*
 * ====================================================================
 * 
 * 機能名称 : オリジナル商品品振エラーリスト
 * 
 * 改版履歴
 * 
 * Rev. 改版年月日 改版者名 内容
 * 
 * 1.0 2015.06.10 NECVN 新規作成
 * 
 * ===================================================================
 */
var app = angular.module('sijp7090Services', [ 'ngResource' ]);

app.factory('Sijp7090Report', function($resource) {
    var report = $resource('/core/SIJP7090/:hatDayFr/:hatDayTo/:jigyobuCd/:hinKkKbn', {
    	hatDayFr: '@hatDayFr', hatDayTo: '@hatDayTo', jigyobuCd: '@jigyobuCd', hinKkKbn: '@hinKkKbn'
    }, {});
    return report;
});

app.factory('Sijp7090Init', function($resource) {
    var sijp7090 = $resource('/core/SIJP7090Init',
            {});
    return sijp7090;
});