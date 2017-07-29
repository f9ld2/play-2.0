///////////////////////////////////////////////////////////////////////
// Copyright(C) 2015 NEC Solution Innovators, Ltd. All Rights Reserved.
///////////////////////////////////////////////////////////////////////

/* ====================================================================
 * 機能名称 : 物流スケジュールリスト(定番)
 * 改版履歴
 * Rev. 改版年月日   改版者名       内容
 * 1.0   2014.04.17   VuQQ      新規作成
 * =================================================================== */
var app = angular.module('hajp1090Services', ['ngResource']);

app.factory('HAJP1090Report', function($resource) {
  var HAJP1090Report = $resource('/core/HAJP1090/:yyyy/:mm/',
                          {yyyy: '@yyyy', mm: '@mm' });
  return HAJP1090Report;
});

