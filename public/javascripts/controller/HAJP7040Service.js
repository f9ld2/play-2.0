///////////////////////////////////////////////////////////////////////
// Copyright(C) 2015 NEC Solution Innovators, Ltd. All Rights Reserved.
///////////////////////////////////////////////////////////////////////

/* ====================================================================
 * 機能名称 : HHT基準在庫取込エラーリスト
 * 改版履歴
 * Rev. 改版年月日   改版者名       内容
 * 1.0   2015.06.02   LocHV      新規作成
 * =================================================================== */
var app = angular.module('hajp7040Services', ['ngResource']);

app.factory('HAJP7040Init', function($resource) {
    var HAJP7040Init = $resource('/core/HAJP7040Init',
            {}, {
            query : {method : 'GET'}
        });
    return HAJP7040Init;
});
app.factory('HAJP7040Report', function($resource) {
    var HAJP7040Report = $resource('/core/HAJP7040/:jigyobuCd/:tenCd/:sendDay/:outTaisho',
            {jigyobuCd: '@jigyobuCd',
            tenCd: '@tenCd',
            hakkoDay: '@sendDay',
            taisho: '@outTaisho'});
    return HAJP7040Report;
});
