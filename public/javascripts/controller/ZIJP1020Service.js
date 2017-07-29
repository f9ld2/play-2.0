///////////////////////////////////////////////////////////////////////
// Copyright(C) 2015 NEC Solution Innovators, Ltd. All Rights Reserved.
///////////////////////////////////////////////////////////////////////

/* ====================================================================
 * 機能名称 : 棚卸取扱い部門リスト
 * 改版履歴
 * Rev. 改版年月日   改版者名       内容
 * 1.0   2014.05.07   Taivd      新規作成
 * =================================================================== */
var app = angular.module('zijp1020Services', ['ngResource']);
app.factory('ZIJP1020Report', function($resource) {
    
    var resultForm = $resource('/core/ZIJP1020/:kbn/:kaisyaCd/:jigyobuCd', {
        kbn : '@kbn',
        kaisyaCd : '@kaisyaCd',
        jigyobuCd : '@jigyobuCd',
    },    {
        query : {
            method : 'GET'
        },
    });
    return resultForm;
});

