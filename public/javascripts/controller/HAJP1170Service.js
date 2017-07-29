///////////////////////////////////////////////////////////////////////
//Copyright(C) 2015 NEC Solution Innovators, Ltd. All Rights Reserved.
// /////////////////////////////////////////////////////////////////////
/*
 * ====================================================================
 *
 * 機能名称 :ＴＡ伝票出力
 *
 * 改版履歴
 * Rev. 改版年月日 改版者名 内容
 * 1.0 2014-05-05 TUCTVZ 新規作成
 * ===================================================================
 */
var app = angular.module('hajp1170Services', ['ngResource']);
app.factory('HAJP1170Report', function($resource) {
    var hajp1170 = $resource('/core/HAJP1170/:kaisyaCd', {
        kaisyaCd: '@kaisyaCd'
    });
    return hajp1170;
});