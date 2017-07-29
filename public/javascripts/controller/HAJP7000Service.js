///////////////////////////////////////////////////////////////////////
//Copyright(C) 2015 NEC Solution Innovators, Ltd. All Rights Reserved.
///////////////////////////////////////////////////////////////////////

/* ====================================================================
 * 機能名称 : HHT発注取込エラーリスト
 * 改版履歴
 * Rev. 改版年月日   改版者名       内容
 * 1.0   2015.06.03   NECVN      新規作成
 * =================================================================== */

var app = angular.module('hajp7000Services', ['ngResource']);

app.factory('HAJP7000Report', function($resource) {
  var report = $resource('/core/HAJP7000/:jigyobuCd/:tenCd/:sendDay/:outTaisyo',
                          {jigyobuCd: '@jigyobuCd', tenCd: '@tenCd',sendDay: '@sendDay',outTaisyo: '@outTaisyo'},
                          {send: {method: 'GET'}});
  return report;
});

app.factory('HAJP7000Init', function($resource) {
    var hajp7000 = $resource('/core/HAJP7000Init',
            {});
    return hajp7000;
});