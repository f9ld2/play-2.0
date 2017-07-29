// /////////////////////////////////////////////////////////////////////
// Copyright(C) 2014 NEC Solution Innovators, Ltd. All Rights Reserved.
// /////////////////////////////////////////////////////////////////////
/*
 * ====================================================================
 * 
 * 機能名称 :自動発注予定データ一覧（店舗商品別）
 * 
 * 改版履歴
 * 
 * Rev. 改版年月日 改版者名 内容
 * 
 * 1.0 2015-06-02 NECVN 新規作成
 * 
 * ===================================================================
 */
var app = angular.module('hajp7030Services', ['ngResource']);

app.factory('HAJP7030Control', function($resource) {
	var hajp7030 = $resource('/core/HAJP7030/:jigyobuCd/:tenCd',
			{ 	jigyobuCd: '@jigyobuCd',
				tenCd: '@tenCd'});
	return hajp7030;
});


app.factory('HAJP7030getHatp', function($resource) {
    var hajp7030 = $resource('/core/HAJP7030/:triCd',
            {   triCd: '@triCd'});
    return hajp7030;
});

app.factory('HAJP7030Init', function($resource) {
    var hajp7020 = $resource('/core/HAJP7030Init',
            {});
    return hajp7020;
});
