///////////////////////////////////////////////////////////////////////
//Copyright(C) 2015 NEC Solution Innovators, Ltd. All Rights Reserved.
///////////////////////////////////////////////////////////////////////
/*
 * ====================================================================
 * 機能名称 : 日別予算入力
 * 改版履歴 Rev. 改版年月日 改版者名 内容
 * 1.0 2014.04.16 TuanTQ 新規作成
 * ===================================================================
 */
var app = angular.module('urjp0010Services', ['ngResource']);
app.factory('Urjp0010', function($resource) {
    var ten = $resource('/core/URJP0010/:yyyy/:mm/:kaisyaCd/:jigyobuCd/:tenCd', {
        yyyy: '@yyyy',
        mm: '@mm',
        kaisyaCd: '@kaisyaCd',
        jigyobuCd: '@jigyobuCd',
        tenCd: '@tenCd'
    }, {
        'query': {
            method: 'GET'
        },
        'update': {
            method: 'PUT'
        }
    });
    return ten;
});
app.factory('Urjp0010ResourceInit', function($resource) {
    var resultForm = $resource('/core/URJP0010/initCond', {}, {
        initCond: {
            method: 'GET'
        }
    });
    return resultForm;
});
app.factory('Urjp0010CalCopy', function($resource) {
    var result = $resource('/core/URJP0010CALCOPY/:yyyy/:mm/:kaisyaCd/:jigyobuCd/:tenCd', {
        yyyy: '@yyyy',
        mm: '@mm',
        kaisyaCd: '@kaisyaCd',
        jigyobuCd: '@jigyobuCd',
        tenCd: '@tenCd'
    }, {
        'post': {
            method: 'POST'
        }
    });
    return result;
});