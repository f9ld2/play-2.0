///////////////////////////////////////////////////////////////////////
// Copyright(C) 2015 NEC Solution Innovators, Ltd. All Rights Reserved.
///////////////////////////////////////////////////////////////////////

/* ====================================================================
 * 機能名称 : 日割予算リスト
 * 改版履歴
 * Rev. 改版年月日   改版者名       内容
 * 1.0   2014.05.20   Taivd      新規作成
 * =================================================================== */
var app = angular.module('urjp1140Services', ['ngResource']);
app.factory('URJP1140Report', function($resource) {
    
    var resultForm = $resource('/core/URJP1140/:kaisyaCd/:jigyobuCd/:tenCd/:taisyodateY/:taisyodateM', {
    },{
        query : {
            method : 'GET'
        },
    });
    return resultForm;
});
app.factory('URJP1140Init', function($resource) {
    var resultForm = $resource('/core/URJP1140init',
                            {},
                            {'init': {method: 'GET'}
                        });
    return resultForm;
});