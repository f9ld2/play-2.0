// /////////////////////////////////////////////////////////////////////
// Copyright(C) 2015 NEC Solution Innovators, Ltd. All Rights Reserved.
// /////////////////////////////////////////////////////////////////////
/*
 * ====================================================================
 *
 * 機能名称 :委託精算確認
 *
 * 改版履歴
 *
 * Rev. 改版年月日 改版者名 内容
 *
 * 1.0 2015-06-04 NECVN 新規作成
 *
 * ===================================================================
 */
var app = angular.module('sijp7100', ['sijp7100Services', 'ui', 'ui.select2', 'directives']);
app.controller("SIJP7100Ctrl", function($scope, Message, MsgConst, HttpConst, SIJP7100Control,
        SIJP7100Export, SIJP7100Init, Dialog, DialogInfo, $interval, AppConst, FocusConst, Client) {
    Message.showMessage(MsgConst.MSG_KEY_COMMON_INFO_MESSAGE_F10);
    $scope.cond = {};
    $scope.queryResult = [];
    $scope.hasResult = false;
    $scope.totalServerItems = 0;
    $scope.isNewData = false;
    $scope.cond.kaisyaCd = AppConst.KAISYA_CODE;
    $scope.focusCond = FocusConst.COMBOBOX_INIT_FOCUS;
    $scope.focusResult = FocusConst.NOT_FOCUS;
    $scope.hiddenBar = true;
    $scope.Const = {
            N0054_MAX_RECORD_NUMBER: '06'
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
     * Disable Loading Bar
     * 
     * @param 
     * @return 
     */
    $scope.disableLoadingBar = function() {
        $scope.hiddenBar = true;
    }

    /**
     * Enable Loading Bar
     * 
     * @param 
     * @return 
     */
    $scope.enableLoadingBar = function() {
        $scope.hiddenBar = false;
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

    var rowTemplate = '<div ng-repeat="col in renderedColumns" ng-class="col.colIndex()" class="ngCell {{col.cellClass}}"><div class="ngVerticalBar" ng-style="{height: rowHeight}" ng-class="{ ngVerticalBarVisible: !$last }">&nbsp;</div><div ng-cell-custom></div></div>';

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
     * Initialize the grid options.
     */
    $scope.gridOptionsSIJP7100Area = {
        data: 'queryResult.dtoList',
        enablePaging: false,
        headerRowHeight: 20.0,
        rowHeight: 20.0,
        enableRowSelection: false,
        enableSorting: false,
        enableColumnResize: true,
        enableCellSelection: false,
        rowTemplate: rowTemplate,
        columnDefs: [{
            field: 'no',
            displayName: '№',
            width: 40,
            cellTemplate: '<div class="cs-center ngCellText cellText">{{row.rowIndex + 1}}</div>'
        }, {
            field: 'triCd',
            displayName: '取引先',
            width: 85,
            cellTemplate: '<div class="ngCellText cellText"><span ng-cell-text>{{COL_FIELD|ccTriCdFilter}}</span></div>'
        }, {
            field: 'triNm',
            displayName: '取引先名',
            width: 90,
            cellTemplate: '<div class="ngCellText cellText"><span ng-cell-text>{{COL_FIELD}}</span></div>'
        }, {
            field: 'janCd',
            displayName: '商品[GTIN]',
            width: 110,
            cellTemplate: '<div class="ngCellText cellText"><span ng-cell-text>{{COL_FIELD}}</span></div>'
        }, {
            field: 'shnNm',
            displayName: '商品名',
            width: 90,
            cellTemplate: '<div class="ngCellText cellText"><span ng-cell-text>{{COL_FIELD}}</span></div>'
        }, {
            field: 'genk',
            displayName: '原単価',
            width: 73,
            cellTemplate: '<div class="cs-right ngCellText cellText"><span ng-cell-text>{{COL_FIELD | number:2}}</span></div>'
        }, {
            field: 'baik',
            displayName: '売単価',
            width: 73,
            cellTemplate: '<div class="cs-right ngCellText cellText">{{COL_FIELD | number:0}}</div>'
        }, {
            field: 'geshoZaiko',
            displayName: '月初',
            width: 49,
            cellTemplate: '<div class="cs-right ngCellText cellText">{{COL_FIELD | number:2}}</div>'
        }, {
            field: 'sirSu',
            displayName: '仕入',
            width: 49,
            cellTemplate: '<div class="cs-right ngCellText cellText">{{COL_FIELD | number:2}}</div>'
        }, {
            field: 'uriTensu',
            displayName: '売上',
            width: 49,
            cellTemplate: '<div class="cs-right ngCellText cellText">{{COL_FIELD | number:2}}</div>'
        }, {
            field: 'fumeiSu',
            displayName: '不明',
            width: 49,
            cellTemplate: '<div class="cs-right ngCellText cellText">{{COL_FIELD | number:2}}</div>'
        }, {
            field: 'tnaSu',
            displayName: '棚卸',
            width: 49,
            cellTemplate: '<div class="cs-right ngCellText cellText">{{COL_FIELD | number:2}}</div>'
        }, {
            field: 'seisanSu',
            displayName: '精算数',
            width: 49,
            cellTemplate: '<div class="cs-right ngCellText cellText">{{COL_FIELD | number:2}}</div>'
        }, {
            field: 'seisanKin',
            displayName: '精算金額',
            width: 73,
            cellTemplate: '<div class="cs-right ngCellText cellText">{{COL_FIELD | number:2}}</div>'
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
                $scope.cond.triCd = '';
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

        var control = SIJP7100Control.query($scope.cond, function() {
            $scope.queryResult = control[0];
            $scope.hasResult = true;
            Message.showMessage(MsgConst.MSG_KEY_COMMON_INFO_MESSAGE_REPORT);

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
     * Export pdf file
     * 
     * @param 
     * @return 
     */
    $scope.doSave = function() {
        var confirmDialog = Dialog.confirm(Message.getMessage(MsgConst.MSG_KEY_CONFIRM_BEFORE_MAKE_PDF));

        confirmDialog.result.then(function () {
            $scope.enableLoadingBar();
            $scope.error = null;
            $scope.result = {};
            //Search data for report
            var report = SIJP7100Export.query($scope.cond, function() {
                $scope.result = report[0];
                $scope.result.pdfUrl = $sce.getTrustedResourceUrl($scope.result.pdfUrl);
                $window.open($scope.result.pdfUrl, '帳票参照画面',
                        'width=1200,height=680,menubar=no,toolbar=no,location=no,scrollbars=yes');
                Message.showMessage(MsgConst.MSG_KEY_COMMON_REPORT_SUCCESS);
                $scope.disableLoadingBar();
            }, function(response) {
                if (response.status === HttpConst.CODE_NOT_FOUND) {
                    Message.showMessage(MsgConst.MSG_KEY_NO_FOUND_DATA);
                } else if (response.status === HttpConst.CODE_BAD_REQUEST) {
                    // エラー処理
                    Message.showMessageWithContent(response.data.errors[0].level, response.data.errors[0].message);
                    $scope.error = Client.showErrorFromServer($scope.error, response.data.errors);
                } else {
                    Message.showMessage(MsgConst.MSG_KEY_SYSTEM_ERROR);
                }
                $scope.disableLoadingBar();
                $scope.setFocus();
            });
        }, function () {
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

        if ($scope.form.condForm.$invalid) {
            return false;
        } else {
            return true;
        }
    }
    
    /**
     * Validate button export PDF
     *
     * @param
     * @return
     */
    $scope.canSave = function() {
        if ($scope.form.condForm == null) {
            return;
        }

        if (!$scope.hasResult || $scope.form.condForm.$invalid) {
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
        var maker = SIJP7100Init.query({}, function() {
            if (maker != undefined) {
                $scope.cond.jigyoubuCd = maker[0].jigyoubuCd;
                $scope.cond.tenCd = maker[0].tenCd;
                $scope.cond.taisyoY = maker[0].taisyoY;
                $scope.cond.taisyoM = maker[0].taisyoM;
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