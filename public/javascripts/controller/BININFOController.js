///////////////////////////////////////////////////////////////////////
// Copyright(C) 2015 NEC Solution Innovators, Ltd. All Rights Reserved.
///////////////////////////////////////////////////////////////////////

/* ====================================================================
 * 機能名称 : 商品マスタレイアウト変更
 * 改版履歴
 * Rev. 改版年月日   改版者名       内容
 * 1.0   2014.09.05   hainp      新規作成
 * =================================================================== */
var app = angular.module('binInfo', ['binInfoServices', 'ui', 'ui.select2', 'directives', 'ngGrid']);
app.controller("BININFOCtrl", function($scope, $attrs, BININFO, AppConst) {
    $scope.result = [];
    $scope.cond = {};
    
    /**
     * Load data 
     */
    $scope.loadData = function() {
        $scope.result = [];
        result = BININFO.query(
                $scope.cond, function() {
                    $scope.result = result;
                }, function(response) {
                });
    }

    $scope.doSearch = function(triCd, hakkoDay) {
        $scope.cond.hakkoDay = hakkoDay;
        $scope.cond.triCd = triCd;
        $scope.loadData();
    }

    var rowTemplate = '<div ng-repeat="col in renderedColumns" ng-class="col.colIndex()" class="ngCell {{col.cellClass}}"><div class="ngVerticalBar" ng-style="{height: rowHeight}" ng-class="{ ngVerticalBarVisible: !$last }">&nbsp;</div><div ng-cell-custom></div></div>';

    /**
     * Declare hacchyu grid
     */
    $scope.gridOptionsOfHacchyuArea1 = {
        data: 'result.hacchyuArea1',
        enableCellSelection: true,
        rowTemplate: rowTemplate,
        showFooter: false,
        footerRowHeight: 0,
        headerRowHeight: 19,
        rowHeight: 19.0,
        enableRowSelection: false,
        enableSorting: false,
        enableColumnResize: false,
        columnDefs: [{
            field: 'hatpMon',
            displayName: '月',
            cellClass: 'cs-center',
            width: 30
        }, {
            field: 'hatpTue',
            displayName: '火',
            cellClass: 'cs-center',
            width: 30
        }, {
            field: 'hatpWed',
            displayName: '水',
            cellClass: 'cs-center',
            width: 30
        }, {
            field: 'hatpThu',
            displayName: '木',
            cellClass: 'cs-center',
            width: 30
        }, {
            field: 'hatpFri',
            displayName: '金',
            cellClass: 'cs-center',
            width: 30
        }, {
            field: 'hatpSat',
            displayName: '土',
            cellClass: 'cs-center',
            width: 30
        }, {
            field: 'hatpSun',
            displayName: '日',
            cellClass: 'cs-center',
            width: 30
        }]
    };

    /**
     * Declare grid
     */
    $scope.gridOptionsOfHacchyuArea2 = {
        data: 'result.hacchyuArea2',
        enableCellSelection: true,
        rowTemplate: rowTemplate,
        headerRowHeight: 19,
        rowHeight: 19,
        enableRowSelection: false,
        enableSorting: false,
        enableColumnResize: false,
        columnDefs: [{
            field: 'hatpMon',
            displayName: '月',
            width: 30
        }, {
            field: 'hatpTue',
            displayName: '火',
            width: 30
        }, {
            field: 'hatpWed',
            displayName: '水',
            width: 30
        }, {
            field: 'hatpThu',
            displayName: '木',
            width: 30
        }, {
            field: 'hatpFri',
            displayName: '金',
            width: 30
        }, {
            field: 'hatpSat',
            displayName: '土',
            width: 30
        }, {
            field: 'hatpSun',
            displayName: '日',
            width: 30
        }]
    };

    /**
     * Declare grid
     */
    $scope.gridOptionsOfHacchyuArea3 = {
        data: 'result.hacchyuArea3',
        enableCellSelection: true,
        rowTemplate: rowTemplate,
        headerRowHeight: 19,
        rowHeight: 19,
        enableRowSelection: false,
        enableSorting: false,
        enableColumnResize: false,
        columnDefs: [{
            field: 'hatpMon',
            displayName: '月',
            width: 30
        }, {
            field: 'hatpTue',
            displayName: '火',
            width: 30
        }, {
            field: 'hatpWed',
            displayName: '水',
            width: 30
        }, {
            field: 'hatpThu',
            displayName: '木',
            width: 30
        }, {
            field: 'hatpFri',
            displayName: '金',
            width: 30
        }, {
            field: 'hatpSat',
            displayName: '土',
            width: 30
        }, {
            field: 'hatpSun',
            displayName: '日',
            width: 30
        }]
    };
});