///////////////////////////////////////////////////////////////////////
//Copyright(C) 2015 NEC Solution Innovators, Ltd. All Rights Reserved.
///////////////////////////////////////////////////////////////////////
/*
 * ====================================================================
 * 機能名称 : オリジナル商品品振確定指示画面
 * 改版履歴 Rev. 改版年月日 改版者名 内容
 * 1.0 2015.06.29 NECVN 新規作成
 * ===================================================================
 */
var app = angular.module('sijp7080Services', [ 'ngResource' ]);
app.factory('SIJP7080Init', function($resource) {
    var sijp7080 = $resource('/core/SIJP7080Init', {});
    return sijp7080;
});
app.factory('SIJP7080', function($resource) {
    var sijp7080 = $resource('/core/SIJP7080/:hatDayFr/:hatDayTo/:shouninKbn/:jigyobuCd', {
        hatDayFr : '@hatDayFr',
        hatDayTo : '@hatDayTo',
        shouninKbn : '@shouninKbn',
        jigyobuCd : '@jigyobuCd'
    }, {
        update : {
            method : 'PUT'
        }
    });
    return sijp7080;
});