///////////////////////////////////////////////////////////////////////
//Copyright(C) 2015 NEC Solution Innovators, Ltd. All Rights Reserved.
///////////////////////////////////////////////////////////////////////

/* ====================================================================
 * 機能名称 : 仕入状況一覧表
 * 改版履歴
 * Rev. 改版年月日   改版者名       内容
 * 1.0   2015.06.04   NECVN      新規作成
 * =================================================================== */

var app = angular.module('sijp7000Services', ['ngResource']);

app.factory('SIJP7000Report', function($resource) {
  var report = $resource('/core/SIJP7000/:nhnYoteiYmdFr/:nhnYoteiYmdTo',
                          {nhnYoteiYmdFr: '@nhnYoteiYmdFr', nhnYoteiYmdTo: '@nhnYoteiYmdTo'},
                          {send: {method: 'GET'}});
  return report;
});

app.factory('SIJP7000Init', function($resource) {
    var SIJP7000 = $resource('/core/SIJP7000Init',
        {});
return SIJP7000;
});