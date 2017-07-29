///////////////////////////////////////////////////////////////////////
// Copyright(C) 2015 NEC Solution Innovators, Ltd. All Rights Reserved.
///////////////////////////////////////////////////////////////////////

/* ====================================================================
 * 機能名称 : 買掛金支払チェックリスト出力
 * 改版履歴
 * Rev. 改版年月日   改版者名       内容
 * 1.0   2014.04.05   VuQQ      新規作成
 * =================================================================== */
var app = angular.module('kkjp1180Services', ['ngResource']);

app.factory('KKJP1180Report', function($resource) {
  var ButsuryuReport = $resource('/core/KKJP1180/:taisyodateY/:taisyodateM/',
                          {taisyodateY: '@taisyodateY', taisyodateM: '@taisyodateM' });
  return ButsuryuReport;
});

