///////////////////////////////////////////////////////////////////////
// Copyright(C) 2015 NEC Solution Innovators, Ltd. All Rights Reserved.
///////////////////////////////////////////////////////////////////////

/* ====================================================================
 * 機能名称 : 支払予定額一覧表
 * 改版履歴
 * Rev. 改版年月日   改版者名       内容
 * 1.0   2014.04.24   VuQQ      新規作成
 * =================================================================== */
var app = angular.module('kkjp1220Services', ['ngResource']);

app.factory('KKJP1220Report', function($resource) {
  var ButsuryuReport = $resource('/core/KKJP1220/:taisyodateY/:taisyodateM/',
                          {taisyodateY: '@taisyodateY', taisyodateM: '@taisyodateM'});
  return ButsuryuReport;
});
