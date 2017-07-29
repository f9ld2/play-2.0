// /////////////////////////////////////////////////////////////////////
// Copyright(C) 2015 NEC Solution Innovators, Ltd. All Rights Reserved.
// /////////////////////////////////////////////////////////////////////

/* ====================================================================
 * 機能名称 : ＥＯＳ伝票入力
 * 改版履歴
 * Rev. 改版年月日   改版者名       内容
 * 1.0   2014.04.05   Hungtb      新規作成
 * =================================================================== */
var app = angular.module('sijp0010Services', ['ngResource']);

app.factory('SIJP0010Resource', function($resource) {
    var resultForm = $resource('/core/SIJP0010/:dpyNo', {
        dpyNo : '@dpyNo'
    }, {
        update : {
            method : 'PUT'
        },
    });
    return resultForm;
});
app.factory('SIJP0010DEL', function($resource) {
    var resultForm = $resource('/core/SIJP0010DEL/:dpyNo', {
        dpyNo : '@dpyNo'
    }, {
        delete : {
            method : 'PUT'
        },
    });
    return resultForm;
});
app.factory('SIJP0010ResourceExt', function($resource) {
    var resultForm = $resource('/core/SIJP0010/EXT', { }, {
        init : {
            method : 'GET'
        }
    });
    return resultForm;
});
