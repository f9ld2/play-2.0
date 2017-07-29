// /////////////////////////////////////////////////////////////////////
// Copyright(C) 2015 NEC Solution Innovators, Ltd. All Rights Reserved.
// /////////////////////////////////////////////////////////////////////

/*
 * ====================================================================
 * 
 * 機能名称 : ＥＯＳ伝票完納入力
 * 
 * 改版履歴
 * 
 * Rev. 改版年月日 改版者名 内容
 * 
 * 1.0 2014-04-05 TUCTV 新規作成
 * 
 * ===================================================================
 */
var app = angular.module('sijp0170Services', ['ngResource']);
app.factory('SIJP0170Control', function($resource) {
    var sijp0170 = $resource('/core/SIJP0170/:ruteCd/:inpNohinDate',
            { 	ruteCd: '@ruteCd',
        inpNohinDate: '@inpNohinDate'},
        {update: {method: 'PUT'}});
    return sijp0170;
});

app.factory('SIJP0170Init', function($resource) {
    var getName = $resource('/core/SIJP0170',
            {},
            {
                'init': {method: 'GET'}
            });
    return getName;	
});