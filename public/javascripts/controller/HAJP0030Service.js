///////////////////////////////////////////////////////////////////////
//Copyright(C) 2015 NEC Solution Innovators, Ltd. All Rights Reserved.
///////////////////////////////////////////////////////////////////////

/*
 * ====================================================================
 * 
 * 機能名称 : 物流スケジュールメンテ
 * 
 * 改版履歴
 * 
 * Rev. 改版年月日 改版者名 内容
 * 
 * 1.0 2014.04.18 ToanPQ 新規作成
 * 
 * ===================================================================
 */

var app = angular.module('hajp0030Services', ['ngResource']);
app.factory('HAJP0030Control', function($resource) {
    var control = $resource('/core/HAJP0030/:yyyy/:mm/:triCd/:bin',
            {
        yyyy : '@yyyy',
        mm : '@mm',
        triCd : '@triCd',
        bin : '@bin'
            },
            {      
                update: {method: 'PUT'}    							
            });
    return control;
});

app.factory('HAJP0030Ext', function($resource) {
    var control = $resource('/core/HAJP0030/EXT/:yyyy/:mm/:triCd/:bin',
            {
        yyyy : '@yyyy',
        mm : '@mm',
        triCd : '@triCd',
        bin : '@bin'},
        {      
            delete : {method : 'PUT'}
        });
    return control;
});