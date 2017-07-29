///////////////////////////////////////////////////////////////////////
//Copyright(C) 2015 NEC Solution Innovators, Ltd. All Rights Reserved.
///////////////////////////////////////////////////////////////////////

/* ====================================================================
 * 機能名称 : 発注エラーリスト
 * 改版履歴
 * Rev. 改版年月日   改版者名       内容
 * 1.0   20140416   LocHV      新規作成
 * =================================================================== */

var app = angular.module('hajp1010Services', ['ngResource']);

app.factory('HAJP1010Report', function($resource) {
  var report = $resource('/core/HAJP1010/:hatDdSt/:hatDdEd',
                          {hatDdSt: '@hatDdSt', hatDdEd: '@hatDdEd'},
                          {update: {method: 'PUT'}});
  return report;
});