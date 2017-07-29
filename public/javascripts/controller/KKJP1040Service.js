///////////////////////////////////////////////////////////////////////
// Copyright(C) 2015 NEC Solution Innovators, Ltd. All Rights Reserved.
///////////////////////////////////////////////////////////////////////

/* ====================================================================
 * 機能名称 : 買掛金残高一覧表
 * 改版履歴
 * Rev. 改版年月日   改版者名       内容
 * 1.0   2014.04.01   TUANVT      新規作成
 * =================================================================== */


var app = angular.module('kkjp1040Services', ['ngResource']);

app.factory('KKJP1040Report', function($resource) {
	  var ten = $resource('/core/KKJP1040/:taisyoYSt/:taisyoMSt',
	                     {taisyoYSt: '@taisyoYSt', taisyoMSt: '@taisyoMSt'},
	                     {update: {method: 'PUT'}});
	  return ten;
	});

	app.factory('ComInfo', function() {
	  return {
	    sid: '',
	    title: '',
	    message: ''
	  };
	});