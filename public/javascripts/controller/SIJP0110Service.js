// /////////////////////////////////////////////////////////////////////
// Copyright(C) 2015 NEC Solution Innovators, Ltd. All Rights Reserved.
// /////////////////////////////////////////////////////////////////////

/*
 * ====================================================================
 * 
 * 機能名称 : 伝票ヘッダ照会
 * 
 * 改版履歴
 * 
 * Rev. 改版年月日 改版者名 内容
 * 
 * 1.0 2014-03-25 tuctv 新規作成
 * 
 * ===================================================================
 */
var app = angular.module('sijp0110Services', ['ngResource']);
app.factory('SIJP0110Control', function($resource) {
	var sijp0110 = $resource('/core/SIJP0110/:dpyKbn/:kaisyaCd/:jigyobuCd',
			{ 	dpyKbn: '@dpyKbn',
				kaisyaCd: '@kaisyaCd',
				jigyobuCd: '@jigyobuCd'});
	return sijp0110;
});

app.factory('SIJP0110Init', function($resource) {
	var getName = $resource('/core/SIJP0110',
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

