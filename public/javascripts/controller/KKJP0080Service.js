// /////////////////////////////////////////////////////////////////////
// Copyright(C) 2015 NEC Solution Innovators, Ltd. All Rights Reserved.
// /////////////////////////////////////////////////////////////////////

/* ====================================================================
 * 機能名称 : KKJP0010
 * 改版履歴
 * Rev. 改版年月日   改版者名       内容
 * 1.0   2014.04.05   Hungtb      新規作成
 * =================================================================== */
var app = angular.module('kkjp0080Services', ['ngResource']);
app.factory('K008TRHK', function($resource) {
    var result = $resource('/core/KKJP0080/:kaisyaCd/:kakToriKmk',
            {kaisyaCd: '@kaisyaCd', kakToriKmk: '@kakToriKmk'},
            {update: {method: 'PUT'}});
    return result;
});

