///////////////////////////////////////////////////////////////////////
// Copyright(C) 2015 NEC Solution Innovators, Ltd. All Rights Reserved.
///////////////////////////////////////////////////////////////////////

/* ====================================================================
 * 機能名称 : 企画検索
 * 改版履歴
 * Rev. 改版年月日   改版者名       内容
 * 1.0   2014.08.20   phuclt      新規作成
 * =================================================================== */
var app = angular.module('kkkSearch', ['kkkSearchServices', 'ui', 'ui.select2', 'directives', 'ngGrid']);
app.controller("KKKSEARCHCtrl", function($scope, $attrs, $interval, KKKSEARCH, AppConst, FocusConst, Message, MsgConst) {
    $scope.result = [];
    $scope.selectedItems = [];
    $scope.search = {};
    $scope.search.kaisyaCd = AppConst.KAISYA_CODE;
    $scope.sysDate = $scope.getCurrentDate();
    $scope.focusCond = FocusConst.COMBOBOX_INIT_FOCUS;
    $scope.title = '企画検索';

    /**
     * load data to grid
     */
    $scope.loadData = function() {
        $scope.level = undefined;
        $scope.message = undefined;
        $scope.result = [];
        $scope.$broadcast('spinnerResume');
        result = KKKSEARCH.query(
            $scope.search, function() {
                $scope.result = result;
                
                var messageInfo = Message.getMessageInfoWithParams(MsgConst.MSG_KEY_SEARCHED_RECORDS_NUMBER, [$scope.result.length]);
                $scope.level = messageInfo.lvl;
                $scope.message = messageInfo.msg;

                $scope.setFocus();
                $scope.$broadcast('spinnerStop');
            }, function(response) {
                $scope.level = response.data.errors[0].level;
                $scope.message = response.data.errors[0].message;
                $scope.setFocus();
                $scope.$broadcast('spinnerStop');
            });
    }

    /**
     * focus getter for condition area
     *
     * @param
     * @return integer 0: not focus, 1, 2: focus
     */
    $scope.getFocusCond = function() {
        return $scope.focusCond;
    }

    /**
     * set focus for condition or result area
     *
     * @param
     * @return
     */
    $scope.setFocus = function() {
        setFocusCond($scope, $interval, FocusConst.COMBOBOX_NOT_INIT_FOCUS);
    }

    $scope.$watch('selectedItems', function() {
        if ($scope.selectedItems.length != 0) {
            $scope.$emit($attrs.parentId + "kkkSearchClicked", $scope.selectedItems[0].kikakuCd);
        }
    }, true);

    /**
     * load data to grid
     */
    $scope.gridSearch = function() {
        $scope.loadData();
    }

    var rowTemplate = '<div ng-repeat="col in renderedColumns" ng-class="col.colIndex()" class="ngCell {{col.cellClass}}"><div class="ngVerticalBar" ng-style="{height: rowHeight}" ng-class="{ ngVerticalBarVisible: !$last }">&nbsp;</div><div ng-cell-custom></div></div>';

    $scope.gridOptions = {
        data: 'result',
        enablePaging: false,
        showFooter: false,
        headerRowHeight: 19.0,
        rowHeight: 19.0,
        rowTemplate: rowTemplate,
        enableRowSelection: true,
        enableCellSelection: true,
        selectedItems: $scope.selectedItems,
        enableSorting: false,
        enableColumnResize: false,
        multiSelect: false,
        columnDefs: [{
            field: 'jigyobuCd',
            headerClass: '',
            displayName: '事業部コード',
            cellClass: "text-left",
            width: '80'
        }, {
            field: 'nendo',
            headerClass: '',
            displayName: '年度',
            cellClass: "text-center",
            width: '48'
        }, {
            field: 'kikakuCd',
            headerClass: 'like-search-cell',
            displayName: '企画コード',
            cellClass: "text-left",
            width: '80'
        }, {
            field: 'kikakuNm',
            headerClass: 'like-search-cell',
            displayName: '企画名',
            cellClass: "text-left",
            width: '150'
        }, {
            field: 'kikakuSyu',
            headerClass: '',
            displayName: '企画種類',
            cellClass: "text-left",
            width: '70'
        }, {
            field: 'bmnCd',
            headerClass: '',
            displayName: '部門コード',
            cellClass: "text-left",
            width: '70'
        }, {
            field: 'kakuteiDay',
            headerClass: '',
            displayName: '発注日',
            cellClass: "text-center",
            cellTemplate: '<div class="">{{row.getProperty(col.field) | ccStrDateFilter}}</div>',
            width: '80'
        }, {
            field: 'hanbaiFrdd',
            headerClass: '',
            displayName: '販売可能日',
            cellClass: "text-center",
            cellTemplate: '<div class="">{{row.getProperty(col.field) | ccStrDateFilter}}</div>',
            width: '80'
        }, {
            field: 'siireFrdd',
            headerClass: '',
            displayName: '発注可能日',
            cellClass: "text-center",
            cellTemplate: '<div class="">{{row.getProperty(col.field) | ccStrDateFilter}}</div>',
            width: '80'
        }, {
            field: 'tirasiKbn',
            headerClass: '',
            displayName: 'ﾁﾗｼ区分',
            cellClass: "text-left"
        }]
    };
});