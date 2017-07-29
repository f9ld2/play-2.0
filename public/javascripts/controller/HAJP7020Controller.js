// /////////////////////////////////////////////////////////////////////
// Copyright(C) 2015 NEC Solution Innovators, Ltd. All Rights Reserved.
// /////////////////////////////////////////////////////////////////////
/*
 * ====================================================================
 *
 * 機能名称 :自動発注予定データ一覧（店舗別）
 *
 * 改版履歴
 *
 * Rev. 改版年月日 改版者名 内容
 *
 * 1.0 2015-06-04 NECVN 新規作成
 *
 * ===================================================================
 */
var app = angular.module('hajp7020', ['hajp7020Services', 'ui', 'ui.select2', 'directives']);
app.controller("HAJP7020Ctrl", function($scope, Message, MsgConst, HttpConst, HAJP7020Control,
        HAJP7020Init, Dialog, DialogInfo, $interval, AppConst, FocusConst, Client) {
    Message.showMessage(MsgConst.MSG_KEY_COMMON_INFO_MESSAGE_F10);
    $scope.cond = {};
    $scope.queryResult = [];
    $scope.isEditable = false;
    $scope.hasResult = false;
    $scope.totalServerItems = 0;
    $scope.isNewData = false;
    $scope.cond.kaisyaCd = AppConst.KAISYA_CODE;
    $scope.focusCond = FocusConst.COMBOBOX_INIT_FOCUS;
    $scope.focusResult = FocusConst.NOT_FOCUS;
    $scope.Const = {
        N0023_ANOTHER_SHOP: '1',
        N0054_MAX_RECORD_NUMBER: '02'
    };
    /**
     * do set focus condition.
     *
     * @param
     * @return
     */
    $scope.getFocusCond = function() {
        return $scope.focusCond;
    }

    /**
     * do set focus result.
     *
     * @param
     * @return
     */
    $scope.getFocusResult = function() {
        return $scope.focusResult;
    }

    /**
     * do set focus condition or result.
     *
     * @param
     * @return
     */
    $scope.setFocus = function() {
        if ($scope.isEditable) {
            setFocusResult($scope, $interval, FocusConst.NOT_FOCUS);
        } else {
            setFocusCond($scope, $interval, FocusConst.COMBOBOX_NOT_INIT_FOCUS);
        }
    }

    var rowTemplate = '<div ng-repeat="col in renderedColumns" ng-class="col.colIndex()" class="gridStyleSijp7080Padding0 ngCell {{col.cellClass}}"><div class="ngVerticalBar" ng-style="{height: rowHeight}" ng-class="{ ngVerticalBarVisible: !$last }">&nbsp;</div><div ng-cell-custom></div></div>';

    /**
     * set disable.
     *
     * @param
     * @return
     */
    $scope.changeDisable = function() {
        return $scope.isEditable;
    }

    /**
     * gridOptionsHAJP7020KikakuInfArea is generated.
     *
     * @param
     * @return
     */

    /**
     * Initialize the grid anothe shop options.
     */
    $scope.gridOptionsHAJP7020AreaAnotherShop = {
        data: 'queryResult',
        enablePaging: false,
        headerRowHeight: 20.0,
        rowHeight: 20.0,
        enableRowSelection: false,
        enableSorting: false,
        enableColumnResize: false,
        enableCellSelection: true,
        rowTemplate: rowTemplate,
        columnDefs: [{
            field: 'no',
            displayName: '№',
            width: 40,
            cellTemplate: '<div class="cs-center ngCellText cellText">{{row.rowIndex + 1}}</div>'
        }, {
            field: 'tenCd',
            displayName: '店舗',
            width: 85,
            cellTemplate: '<div class="ngCellText cellText"><span ng-cell-text>{{COL_FIELD}}</span></div>'
        }, {
            field: 'tenNm',
            displayName: '店舗名',
            width: 403,
            cellTemplate: '<div class="ngCellText cellText"><span ng-cell-text>{{COL_FIELD}}</span></div>'
        }, {
            field: 'yoteSuryo',
            displayName: '予定数量',
            width: 200,
            cellTemplate: '<div class="cs-right ngCellText cellText"><span ng-cell-text>{{COL_FIELD | number:2}}</span></div>'
        }, {
            field: 'yoteKingaku',
            displayName: '予定金額',
            width: 200,
            cellTemplate: '<div class="cs-right ngCellText cellText"><span ng-cell-text>{{COL_FIELD | number:0}}</span></div>'
        }]
    };

    /**
     * Initialize the grid options.
     */
    $scope.gridOptionsHAJP7020Area = {
        data: 'queryResult',
        enablePaging: false,
        headerRowHeight: 20.0,
        rowHeight: 20.0,
        enableRowSelection: false,
        enableSorting: false,
        enableColumnResize: false,
        enableCellSelection: true,
        rowTemplate: rowTemplate,
        columnDefs: [{
            field: 'no',
            displayName: '№',
            width: 40,
            cellTemplate: '<div class="cs-center ngCellText cellText">{{row.rowIndex + 1}}</div>'
        }, {
            field: 'tenCd',
            displayName: '店舗',
            width: 65,
            cellTemplate: '<div class="ngCellText cellText"><span ng-cell-text>{{COL_FIELD}}</span></div>'
        }, {
            field: 'tenNm',
            displayName: '店舗名',
            width: 250,
            cellTemplate: '<div class="ngCellText cellText"><span ng-cell-text>{{COL_FIELD}}</span></div>'
        }, {
            field: 'triCd',
            displayName: '取引先',
            width: 83,
            cellTemplate: '<div class="ngCellText cellText"><span ng-cell-text>{{COL_FIELD | ccTriCdFilter}}</span></div>'
        }, {
            field: 'triNm',
            displayName: '取引先名',
            width: 230,
            cellTemplate: '<div class="ngCellText cellText"><span ng-cell-text>{{COL_FIELD}}</span></div>'
        }, {
            field: 'yoteSuryo',
            displayName: '予定数量',
            width: 100,
            cellTemplate: '<div class="cs-right ngCellText cellText"><span ng-cell-text>{{COL_FIELD | number:2}}</span></div>'
        }, {
            field: 'yoteKingaku',
            displayName: '予定金額',
            width: 160,
            cellTemplate: '<div class="cs-right ngCellText cellText">{{COL_FIELD | number:0}}</div>'
        }]
    };

    /**
     * Action button clear.
     *
     * @param
     * @return
     */
    $scope.doClear = function() {
        var clearDialog = Dialog.confirm(Message.getMessage(MsgConst.MSG_KEY_CONFIRM_BEFORE_CLEAR));
        if (clearDialog == null) {
            return;
        }
        clearDialog.result.then(function() {
            if ($scope.hasResult == false && $scope.isEditable == false) {
                $scope.cond = {};
                $scope.initCond();
            }
            $scope.form.$setPristine();
            $scope.error = Client.clearErrors();
            $scope.queryResult = [];
            $scope.hasResult = false;
            $scope.isEditable = false;

            $scope.cond.kaisyaCd = AppConst.KAISYA_CODE;
            $scope.isNewData = false;
            $scope.setFocus();
            Message.showMessage(MsgConst.MSG_KEY_COMMON_INFO_MESSAGE_F10);
        }, function() {
            $scope.setFocus();
        });
    }

    /**
     * Action button search.
     *
     * @param
     * @return
     */
    $scope.doSearch = function() {
        $scope.queryResult = [];
        $scope.hasResult = false;
        $scope.error = Client.clearErrors();

        var control = HAJP7020Control.query($scope.cond, function() {
            $scope.queryResult = control;
            $scope.isEditable = false;
            $scope.hasResult = true;
            $scope.hasTriNm = control[0].hyojiKbn != $scope.Const.N0023_ANOTHER_SHOP;

            Message.showMessage(MsgConst.MSG_KEY_PROCESS_SUCCESS);

            $scope.setFocus();
        }, function(response) {
            if (response.status === HttpConst.CODE_NOT_FOUND) {
                $scope.hasResult = false;
                Message.showMessage(MsgConst.MSG_KEY_NO_FOUND_DATA);
            } else if (response.status === HttpConst.CODE_BAD_REQUEST) {
                // エラー処理
                Message.showMessageWithContent(response.data.errors[0].level, response.data.errors[0].message);
                Client.showErrorFromServer($scope.error, response.data.errors);
            } else if (response.status === HttpConst.CODE_UNAUTHORIZED) {
                Message.showMessage(MsgConst.MSG_KEY_TIMEOUT_ERROR);
            } else {
                Message.showMessage(MsgConst.MSG_KEY_SYSTEM_ERROR);
            }
            $scope.setFocus();
        });
    }

    /**
     * Validate button search.
     *
     * @param
     * @return
     */
    $scope.canSearch = function() {
        if ($scope.form.condForm == null) {
            return;
        }

        if ($scope.isEditable || $scope.form.condForm.$invalid) {
            return false;
        } else {
            return true;
        }
    }
    /**
     * Initial screen
     */
    $scope.initCond = function() {
        $scope.cond.maxRecordNumber = $scope.getMaxRecordNumber($scope.Const.N0054_MAX_RECORD_NUMBER);
        var maker = HAJP7020Init.query({}, function() {
            if (maker != undefined) {
                $scope.cond.jigyobuCd = maker[0].jigyobuCd;
                $scope.cond.hyojiKbn = maker[0].hyojiKbn;
            }
        }, function(response) {
            if (response.status === HttpConst.CODE_UNAUTHORIZED) {
                Message.showMessage(MsgConst.MSG_KEY_TIMEOUT_ERROR);
            } else {
                Message.showMessage(MsgConst.MSG_KEY_SYSTEM_ERROR);
            }
        });
    }
});