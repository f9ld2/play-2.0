///////////////////////////////////////////////////////////////////////
// Copyright(C) 2015 NEC Solution Innovators, Ltd. All Rights Reserved.
///////////////////////////////////////////////////////////////////////

/* ====================================================================
 * 機能名称 : 日別店別部門別売上修正
 * 改版履歴
 * Rev. 改版年月日   改版者名       内容
 * 1.0   2014.04.6   PhucLT      新規作成
 * =================================================================== */

var app = angular.module('urjp0040Services', ['ngResource']);

app.factory('URJP0040', function($resource) {
  var control = $resource('/core/URJP0040/:kaisyaCd/:jigyobuCd/:tenCd/:uriDate', {
    uriDate: '@uriDate',
    kaisyaCd: '@kaisyaCd',
    jigyobuCd: '@jigyobuCd',
    tenCd: '@tenCd'
  }, {
    'query': {
      method: 'GET'
    },
    'save': {
      method: 'POST'
    },
    'update': {
      method: 'PUT'
    },
  });
  return control;
});

app.factory('URJP0040DEL', function($resource) {
  var control = $resource('/core/URJP0040/del/:kaisyaCd/:jigyobuCd/:tenCd/:uriDate', {
    uriDate: '@uriDate',
    kaisyaCd: '@kaisyaCd',
    jigyobuCd: '@jigyobuCd',
    tenCd: '@tenCd'
  }, {
    'delete': {
      method: 'PUT'
    }
  });
  return control;
});

app.factory('ComInfo', function() {
  return {
    sid: '',
    title: '',
    message: '',
    level: ''
  };
});