// /////////////////////////////////////////////////////////////////////
// Copyright(C) 2014 NEC Solution Innovators, Ltd. All Rights Reserved.
// /////////////////////////////////////////////////////////////////////

/*
 * ====================================================================
 * 
 * 機能名称 : HHT検品取込エラーリスト * 
 * 改版履歴
 * 
 * Rev. 改版年月日 改版者名 内容
 * 
 * 1.0 2015.06.15 NECVN 新規作成
 * 
 * ===================================================================
 */
var app = angular.module('sijp7040Services', [ 'ngResource' ]);

app.factory('Sijp7040Report', function($resource) {
    var report = $resource('/core/SIJP7040/:jigyobuCd/:tenCd/:outTaisyo', {
    	jigyobuCd: '@jigyobuCd', tenCd: '@tenCd', outTaisyo: '@outTaisyo'    }, {});
    return report;
});

app.factory('Sijp7040Init', function($resource) {
    var sijp7040 = $resource('/core/SIJP7040Init',
            {});
    return sijp7040;
});