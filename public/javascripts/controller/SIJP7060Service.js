// /////////////////////////////////////////////////////////////////////
// Copyright(C) 2014 NEC Solution Innovators, Ltd. All Rights Reserved.
// /////////////////////////////////////////////////////////////////////

/*
 * ====================================================================
 * 
 * 機能名称 : HHT品振取込エラーリスト
 * 
 * 改版履歴
 * 
 * Rev. 改版年月日 改版者名 内容
 * 
 * 1.0 2015.06.05 NECVN 新規作成
 * 
 * ===================================================================
 */
var app = angular.module('sijp7060Services', [ 'ngResource' ]);

app.factory('Sijp7060Report', function($resource) {
    var report = $resource('/core/SIJP7060/:outJigyobuCd/:tenCd/:outTaisyo', {
    	outJigyobuCd: '@outJigyobuCd', tenCd: '@tenCd', outTaisyo: '@outTaisyo'
    }, {});
    return report;
});

app.factory('Sijp7060Init', function($resource) {
    var sijp7060 = $resource('/core/SIJP7060Init',
            {});
    return sijp7060;
});