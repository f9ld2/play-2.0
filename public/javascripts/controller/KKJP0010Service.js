// /////////////////////////////////////////////////////////////////////
// Copyright(C) 2015 NEC Solution Innovators, Ltd. All Rights Reserved.
// /////////////////////////////////////////////////////////////////////

/* ====================================================================
 * 機能名称 : KKJP0010
 * 改版履歴
 * Rev. 改版年月日   改版者名       内容
 * 1.0   2014.04.05   Hungtb      新規作成
 * =================================================================== */
var app = angular.module('kkjp0010Services', ['ngResource']);

app.factory('KKJP0010Resource', function($resource) {
    var resultForm = $resource('/core/KKJP0010', {}, {
        update: {
            method: 'PUT'
        }
    });
    return resultForm;
});
app.factory('KKJP0010ResourceExt', function($resource) {
    var resultForm = $resource('/core/KKJP0010/EXT', {}, {
        delete: {
            method: 'POST'
        },
        valid: {
            method: 'PUT'
        }
    });
    return resultForm;
});

app.factory('GetJotaiNm', function() {
    return function() {
        var elem = angular.element('#s2id_jotaiKbn');
        if (elem != undefined) {
            if (!isEmpty(elem.select2('data').id)) {
                var text = elem.select2('data').text;
                var inx = text.indexOf(':');
                if (inx > 0) {
                    return text.substring(inx + 1);
                }
            }
        }
        return "";
    }
})