// /////////////////////////////////////////////////////////////////////
// Copyright(C) 2015 NEC Solution Innovators, Ltd. All Rights Reserved.
// /////////////////////////////////////////////////////////////////////

/*
 * ====================================================================
 * 
 * 機能名称 : 取引先別在庫金額一覧
 * 
 * 改版履歴
 * 
 * Rev. 改版年月日 改版者名 内容
 * 
 * 1.0 2014-04-05 TUCTV 新規作成
 * 
 * ===================================================================
 */
var app = angular.module('zijp1080Services', [ 'ngResource' ]);
app.factory('Zijp1080Job', function($resource) {
    var job = $resource(
            '/core/ZIJP1080/:kaisyaCd/:jigyobuCd/:outputKbn/:tenCd/:bmnCd', {
                kaisyaCd : '@kaisyaCd',
                jigyobuCd : '@jigyobuCd',
                outputKbn : '@outputKbn',
                tenCd : '@tenCd',
                bmnCd : '@bmnCd'
            }, {});
    return job;
});
