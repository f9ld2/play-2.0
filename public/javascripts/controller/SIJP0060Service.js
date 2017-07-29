///////////////////////////////////////////////////////////////////////
//Copyright(C) 2015 NEC Solution Innovators, Ltd. All Rights Reserved.
///////////////////////////////////////////////////////////////////////

/* ====================================================================
 * 機能名称 : 廃棄伝票入力
 * 改版履歴 
 * Rev.  改版年月日   改版者名       内容
 * 1.0   2014.04.11   ToanPQ      新規作成
 * =================================================================== */
var app = angular.module('sijp0060Services', ['ngResource']);
app.factory('SIJP0060Control', function($resource) {
    var control = $resource('/core/SIJP0060/:dpyKbn/:kaisyaCd/:jigyobuCd/:dpyNo/:tenCd',
            {
        dpyKbn : '@dpyKbn',
        kaisyaCd : '@kaisyaCd',
        jigyobuCd : '@jigyobuCd',
        dpyNo : '@dpyNo',
        tenCd : '@tenCd'
            },
            {
                update: {method: 'PUT'} ,
                save: {method: 'POST'}
            });
    return control;
});

app.factory('SIJP0060Init', function($resource) {
    var control = $resource('/core/SIJP0060/initCond',
            {},
            {      
                init: {method: 'GET'}
            });
    return control;
});

app.factory('SIJP0060Ext', function($resource) {
    var control = $resource('/core/SIJP0060/EXT/:dpyKbn/:kaisyaCd/:jigyobuCd/:dpyNo/:tenCd',
            {
        dpyKbn : '@dpyKbn',
        kaisyaCd : '@kaisyaCd',
        jigyobuCd : '@jigyobuCd',
        dpyNo : '@dpyNo',
        tenCd : '@tenCd'
            },
            {      
                delete : {method : 'PUT'}
            });
    return control;
});

app.factory('SIJP0060GetBaitanAndTani', function($resource) {
    var maker = $resource('/core/SIJP0060/:kaisyaCd/:jigyobuCd/:tenCd/:keijoYmd/:shnCd', {
        kaisyaCd : '@kaisyaCd',
        jigyobuCd : '@jigyobuCd',
        tenCd : '@tenCd',
        keijoYmd : '@keijoYmd',
        shnCd : '@shnCd'
    }, {
        getBaiTanka : {method : 'PUT', isArray:false},
        getKikakuTani : {method : 'POST', isArray:false}
    });
    return maker;
});