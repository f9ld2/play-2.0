// /////////////////////////////////////////////////////////////////////
// Copyright(C) 2015 NEC Solution Innovators, Ltd. All Rights Reserved.
// /////////////////////////////////////////////////////////////////////

/*
 * ==================================================================== 
 * 機能名称 : HHT委託精算取込エラーリスト
 * 改版履歴 
 * Rev. 改版年月日 改版者名 内容
 * 1.0 2015.06.04 LocHV 新規作成
 *  ===================================================================
 */
var app = angular.module('sijp7110Services', ['ngResource']);

app.factory('SIJP7110Init', function($resource) {
    var SIJP7110Init = $resource('/core/SIJP7110Init',
            {}, {
		        query : {method : 'GET'}
		    });
    return SIJP7110Init;
});

app.factory('SIJP7110Report', function($resource) {
    var SIJP7110Report = $resource('/core/SIJP7110/:jigyobuCd/:tenCd/:outTaisho',
            {jigyobuCd: '@jigyobuCd',
            tenCd: '@tenCd',
            taisho: '@outTaisho'});
    return SIJP7110Report;
});
