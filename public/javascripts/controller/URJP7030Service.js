///////////////////////////////////////////////////////////////////////
// Copyright(C) 2015 NEC Solution Innovators, Ltd. All Rights Reserved.
///////////////////////////////////////////////////////////////////////

/* ====================================================================
 * 機能名称 : 修理品明細一覧
 * 改版履歴
 * Rev. 改版年月日   改版者名       内容
 * 1.0   205.06.23   chiennt      新規作成
 * =================================================================== */
var app = angular.module('urjp7030Services', ['ngResource']);
app.factory('URJP7030Report', function($resource) {
    var report = $resource('/core/URJP7030/:jigyobuCd/:tenCd/:calDateSt/:calDateEd', {
        jigyobuCd : '@jigyobuCd',
        tenCd     : '@tenCd',
        calDateSt : '@calDateSt',
        calDateEd : '@calDateEd'
    }, {});
    return report;
});

app.factory('URJP7030Init', function($resource) {
    var urjp7030 = $resource('/core/URJP7030/initCond',
            {});
    return urjp7030;
});