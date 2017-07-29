///////////////////////////////////////////////////////////////////////
//Copyright(C) 2015 NEC Solution Innovators, Ltd. All Rights Reserved.
///////////////////////////////////////////////////////////////////////
/*
 * ====================================================================
 * 機能名称 : 本部発注入力（店舗）
 * 改版履歴 Rev. 改版年月日 改版者名 内容
 * 1.0 2014.04.16 TuanTQ 新規作成
 * ===================================================================
 */
var app = angular.module('hajp0020Services', ['ngResource']);
app.factory('Hajp0020', function($resource) {
	var ten = $resource('/core/HAJP0020/:kaisyaCd/:jigyobuCd/:tenCd/:hatSruiKbn', {
		kaisyaCd: '@kaisyaCd',
		jigyobuCd: '@jigyobuCd',
		tenCd: '@tenCd',
		hatSruiKbn: '@hatSruiKbn'
	}, {
		update: {
			method: 'PUT'
		}
	});
	return ten;
});

app.factory('Hajp0020Info', function($resource) {
	var info = $resource('/core/HAJP0020Info/:kaisyaCd/:jigyobuCd/:tenCd/:nhnDd/:hatSruiKbn/:janCd/:bin/:bmnCd', {
		kaisyaCd: '@kaisyaCd',
		jigyobuCd: '@jigyobuCd',
		tenCd: '@tenCd',
		nhnDd: '@nhnDd',
		hatSruiKbn: '@hatSruiKbn',
		janCd: '@janCd',
		bin: '@bin',
		bmnCd: '@bmnCd'
	}, {
		query: {
			method: 'GET'
		}
	});
	return info;
});

app.factory('Hajp0020CheckInfo', function($resource) {
	var info = $resource('/core/Hajp0020CheckInfo/:tenCd/:janCd/:bin/:hatDd/:nhnDd/:hatSruiKbn/:triCd', {
		tenCd: '@tenCd',
		janCd: '@janCd',
		bin: '@bin',
		hatDd: '@hatDd',
		nhnDd: '@nhnDd',
		hatSruiKbn: '@hatSruiKbn',
		triCd: '@triCd'
	}, {
		query: {
			method: 'GET'
		}
	});
	return info;
});