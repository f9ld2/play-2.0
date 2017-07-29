// /////////////////////////////////////////////////////////////////////
// Copyright(C) 2014 NEC Solution Innovators, Ltd. All Rights Reserved.
// /////////////////////////////////////////////////////////////////////

/*
 * ====================================================================
 * 
 * 機能名称 : 棚卸入力画面 * 
 * 改版履歴
 * 
 * Rev. 改版年月日 改版者名 内容
 * 
 * 1.0 2015.06.24 NECVN 新規作成
 * 
 * ===================================================================
 */
var app = angular.module('zijp7060Services', [ 'ngResource' ]);

app.factory('Zijp7060Control', function($resource) {
    var report = $resource('/core/ZIJP7060/:jigyobuCd/:tenCd/:tnaUnyDd/:tnaKbn', {
        jigyobuCd: '@jigyobuCd', 
        tenCd: '@tenCd', 
        tnaUnyDd: '@tnaUnyDd', 
        tnaKbn: '@tnaKbn'}, {
        	update: {
    			method: 'PUT'
    		}
        });
    return report;
});
app.factory('Zijp7060Init', function($resource) {
    var zijp7060 = $resource('/core/ZIJP7060Init', {});
    return zijp7060;
});
app.factory('Zijp7060Info', function($resource) {
    var zijp7060 = $resource('/core/ZIJP7060Info/:jigyobuCd/:shnCd', {
    	jigyobuCd: '@jigyobuCd',
    	shnCd: '@shnCd'
    });
    return zijp7060;
});