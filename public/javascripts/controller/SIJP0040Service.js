///////////////////////////////////////////////////////////////////////
// Copyright(C) 2015 NEC Solution Innovators, Ltd. All Rights Reserved.
///////////////////////////////////////////////////////////////////////

/* ====================================================================
 * 機能名称 : 移動伝票入力
 * 改版履歴
 * Rev. 改版年月日   改版者名       内容
 * 1.0   2014.04.05   Hungtb      新規作成
 * =================================================================== */
var app = angular.module('sijp0040Services', ['ngResource']);

app.factory('SIJP0040Resource', function($resource) {
    var resultForm = $resource('/core/SIJP0040/:dpyKbn/:dpyNo/:kaisyaCd/:jigyobuCd/:tenCd/:bmnCd/:subKaisyaCd/:subJigyobuCd/:subTenCd/:subBmnCd', 
    {
        dpyKbn : '@dpyKbn',
        dpyNo : '@dpyNo',
        kaisyaCd : '@kaisyaCd',
        jigyobuCd : '@jigyobuCd',
        tenCd : '@tenCd',
        bmnCd : '@bmnCd',
        subKaisyaCd : '@subKaisyaCd',
        subJigyobuCd : '@subJigyobuCd',
        subTenCd : '@subTenCd',
        subBmnCd : '@subBmnCd'
    }, {
        update : {
            method : 'PUT'
        },
    });
    return resultForm;
});

app.factory('SIJP0040ResourceInit', function($resource) {
    var resultForm = $resource('/core/SIJP0040/initCond', { }, {
        initCond : {
            method : 'GET'
        }
    });
    return resultForm;
});
app.factory('SIJP0040GetTanka', function($resource) {
    var maker = $resource('/core/SIJP0040/GETTANKA/:kaisyaCd/:jigyobuCd/:tenCd/:keijoYmd/:shnCd', {
        kaisyaCd : '@kaisyaCd',
        jigyobuCd : '@jigyobuCd',
        tenCd : '@tenCd',
        keijoYmd : '@keijoYmd',
        shnCd : '@shnCd'
    }, {
        getTanka : {method : 'PUT', isArray:false},
    });
    return maker;
});