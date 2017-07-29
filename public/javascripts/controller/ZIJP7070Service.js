// /////////////////////////////////////////////////////////////////////
// Copyright(C) 2014 NEC Solution Innovators, Ltd. All Rights Reserved.
// /////////////////////////////////////////////////////////////////////

/*
 * ====================================================================
 * 
 * 機能名称 : HHT棚卸データ取込エラーリスト * 
 * 改版履歴
 * 
 * Rev. 改版年月日 改版者名 内容
 * 
 * 1.0 2015.06.19 NECVN 新規作成
 * 
 * ===================================================================
 */
var app = angular.module('zijp7070Services', [ 'ngResource' ]);

app.factory('Zijp7070Report', function($resource) {
    var report = $resource('/core/ZIJP7070/:jigyobuCd/:tenCd/:outTaisyo', {
    	jigyobuCd: '@jigyobuCd', tenCd: '@tenCd', outTaisyo: '@outTaisyo'}, {});
    return report;
});

app.factory('Zijp7070Init', function($resource) {
    var zijp7070 = $resource('/core/ZIJP7070Init',
            {});
    return zijp7070;
});