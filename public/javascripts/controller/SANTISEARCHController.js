///////////////////////////////////////////////////////////////////////
// Copyright(C) 2015 NEC Solution Innovators, Ltd. All Rights Reserved.
///////////////////////////////////////////////////////////////////////

/* ====================================================================
 * 機能名称 : 産地検索
 * 改版履歴
 * Rev. 改版年月日   改版者名       内容
 * 1.0   2014.10.14   phuclt      新規作成
 * =================================================================== */
var app = angular.module('santiSearch', ['santiSearchServices', 'ui', 'ui.select2', 'directives', 'ngGrid']);
app.controller("SANTISEARCHCtrl", function($scope, $attrs, $interval, SANTISEARCH, AppConst, FocusConst, Message, MsgConst) {
    $scope.result = [];
    $scope.selectedItems = [];
    $scope.search = {};
    $scope.search.kaisyaCd = AppConst.KAISYA_CODE;
    $scope.sysDate = $scope.getCurrentDate();
    $scope.focusCond = FocusConst.TEXTINPUT_FOCUS;
    $scope.title = '産地検索';

    /**
     * load data to grid
     */
    $scope.loadData = function() {
        $scope.level = undefined;
        $scope.message = undefined;
        $scope.result = [];
        $scope.$broadcast('spinnerResume');
        result = SANTISEARCH.query(
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
        setFocusCond($scope, $interval, FocusConst.TEXTINPUT_FOCUS);
    }


    $scope.$watch('selectedItems', function() {
        if ($scope.selectedItems.length != 0) {
            $scope.$emit($attrs.parentId + "santiSearchClicked", $scope.selectedItems[0].santiCd.trim());
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
            field: "santiCd",
            headerClass: 'like-search-cell',
            displayName: "産地コード",
            cellClass: "text-left",
            width: '70'
        }, {
            field: "santiNm",
            headerClass: 'like-search-cell',
            displayName: "産地名",
            cellClass: "text-left",
            width: '165'
        }, {
            field: "santiNmR",
            headerClass: 'like-search-cell',
            displayName: "産地名略称（漢字）",
            cellClass: "text-left",
            width: '175'
        }, {
            field: "santiNmA",
            headerClass: 'like-search-cell',
            displayName: "産地名（カナ）",
            cellClass: "text-left",
        }]
    };
});