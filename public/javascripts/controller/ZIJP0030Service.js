// /////////////////////////////////////////////////////////////////////
// Copyright(C) 2015 NEC Solution Innovators, Ltd. All Rights Reserved.
// /////////////////////////////////////////////////////////////////////

/*
 * ====================================================================
 * 機能名称 : 棚卸情報入力
 * 改版履歴
 * Rev. 改版年月日 改版者名 内容
 * 1.0 2014.03.14 CuongPV 新規作成
 * ===================================================================
 */
var app = angular.module('zijp0030Services', ['ngResource']);

app.factory('Zijp0030', function($resource) {
    var zijp0030 = $resource('/core/ZIJP0030/:kaisyaCd/:jigyobuCd',
            {
        kaisyaCd : '@kaisyaCd',
        jigyobuCd : '@jigyobuCd'
            });
    return zijp0030;
});
