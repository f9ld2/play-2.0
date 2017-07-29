///////////////////////////////////////////////////////////////////////
//Copyright(C) 2015 NEC Solution Innovators, Ltd. All Rights Reserved.
///////////////////////////////////////////////////////////////////////

/* ====================================================================
 * 機能名称 : 入荷状況一覧表
 * 改版履歴
 * Rev. 改版年月日   改版者名       内容
 * 1.0   2015.06.05   NECVN      新規作成
 * =================================================================== */

var app = angular.module('sijp7020Services', ['ngResource']);

app.factory('SIJP7020Report', function($resource) {
  var report = $resource('/core/SIJP7020/:nhnDayFr/:nhnDayTo/:outTaisyo',
                          {nhnDayFr: '@nhnDayFr', nhnDayTo: '@nhnDayTo', outTaisyo: '@outTaisyo'},
                          {send: {method: 'GET'}});
  return report;
});

app.factory('SIJP7020Init', function($resource) {
	    var sijp7020 = $resource('/core/SIJP7020Init',
            {});
    return sijp7020;
});