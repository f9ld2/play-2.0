// /////////////////////////////////////////////////////////////////////
// Copyright(C) 2015 NEC Solution Innovators, Ltd. All Rights Reserved.
// /////////////////////////////////////////////////////////////////////

/*
 * ====================================================================
 * 
 * 機能名称 : 伝票明細履歴照会
 * 
 * 改版履歴
 * 
 * Rev. 改版年月日 改版者名 内容
 * 
 * 1.0 2014-03-28 TUCTV 新規作成
 * 
 * ===================================================================
 */
var app = angular.module('sijp0130Services', ['ngResource']);
app.factory('SIJP0130Control', function($resource) {
	var sijp0130 = $resource('/core/SIJP0130/:dpyKbn/:kaisyaCd/:jigyobuCd',
			{ 	dpyKbn: '@dpyKbn',
				kaisyaCd: '@kaisyaCd',
				jigyobuCd: '@jigyobuCd'});
	return sijp0130;
});

app.factory('SIJP0130Init', function($resource) {
	var getName = $resource('/core/SIJP0130',
			{},
				 {
        			'init': {method: 'GET'}
				});
	
	return getName;	
});

app.factory('ComInfo', function() {
	return {
		sid: '',
	    title: '',
	    message: '',
	    level: ''
	};
});

