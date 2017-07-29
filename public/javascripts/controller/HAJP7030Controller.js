// /////////////////////////////////////////////////////////////////////
// Copyright(C) 2015 NEC Solution Innovators, Ltd. All Rights Reserved.
// /////////////////////////////////////////////////////////////////////
/*
 * ====================================================================
 *
 * 機能名称 :自動発注予定データ一覧（店舗商品別）
 *
 * 改版履歴
 *
 * Rev. 改版年月日 改版者名 内容
 *
 * 1.0 2015-06-02 NECVN 新規作成
 *
 * ===================================================================
 */
var app = angular.module('hajp7030', ['hajp7030Services', 'ui', 'ui.select2', 'directives']);
app.controller("HAJP7030Ctrl", function($scope, Message, MsgConst, HttpConst, HAJP7030Control,
        HAJP7030Init, HAJP7030getHatp, Dialog, DialogInfo, $interval, AppConst, FocusConst, Client) {
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
    $scope.cond.hacchyuArea = [{
        "hatpMon": "",
        "hatpTue": "",
        "hatpWed": "",
        "hatpThu": "",
        "hatpFri": "",
        "hatpSat": "",
        "hatpSun": ""
    }];
    $scope.Const = {
            N0054_MAX_RECORD_NUMBER: '04'
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
     * Declare hacchyu grid
     */
    $scope.gridOptionsHAJP7030HacchyuArea = {
        data: 'cond.hacchyuArea',
        rowTemplate: rowTemplate,
        enablePaging: false,
        showFooter: false,
        footerRowHeight: 0,
        headerRowHeight: 19,
        rowHeight: 20,
        enableSorting: false,
        enableColumnResize: false,
        multiSelect: false,
        enableRowSelection: true,
        enableCellSelection: true,
        columnDefs: [{
            field: 'hatpMon',
            displayName: '月',
            width: 30,
            cellTemplate: '<cc-number-input cc-label="" cc-partition="" id="hatpMon1" name="hatpMon1" ng-model="COL_FIELD" cc-required="false" cc-min="1" cc-max="7" cc-width="27" cc-readonly="!changeDisable()" ></cc-number-input>'
        }, {
            field: 'hatpTue',
            displayName: '火',
            width: 30,
            cellTemplate: '<cc-number-input cc-label="" cc-partition="" id="hatpTue1" name="hatpTue1" ng-model="COL_FIELD" cc-required="false" cc-min="1" cc-max="7" cc-width="27" cc-readonly="!changeDisable()" ></cc-number-input>'
        }, {
            field: 'hatpWed',
            displayName: '水',
            width: 30,
            cellTemplate: '<cc-number-input cc-label="" cc-partition="" id="hatpWed1" name="hatpWed1" ng-model="COL_FIELD" cc-required="false" cc-min="1" cc-max="7" cc-width="27" cc-readonly="!changeDisable()" ></cc-number-input>'
        }, {
            field: 'hatpThu',
            displayName: '木',
            width: 30,
            cellTemplate: '<cc-number-input cc-label="" cc-partition="" id="hatpThu1" name="hatpThu1" ng-model="COL_FIELD" cc-required="false" cc-min="1" cc-max="7" cc-width="27" cc-readonly="!changeDisable()" ></cc-number-input>'
        }, {
            field: 'hatpFri',
            displayName: '金',
            width: 30,
            cellTemplate: '<cc-number-input cc-label="" cc-partition="" id="hatpFri1" name="hatpFri1" ng-model="COL_FIELD" cc-required="false" cc-min="1" cc-max="7" cc-width="27" cc-readonly="!changeDisable()" ></cc-number-input>'
        }, {
            field: 'hatpSat',
            displayName: '土',
            width: 30,
            cellTemplate: '<cc-number-input cc-label="" cc-partition="" id="hatpSat1" name="hatpSat1" ng-model="COL_FIELD" cc-required="false" cc-min="1" cc-max="7" cc-width="27" cc-readonly="!changeDisable()" ></cc-number-input>'
        }, {
            field: 'hatpSun',
            displayName: '日',
            width: 30,
            cellTemplate: '<cc-number-input cc-label="" cc-partition="" id="hatpSun1" name="hatpSun1" ng-model="COL_FIELD" cc-required="false" cc-min="1" cc-max="7" cc-width="27" cc-readonly="!changeDisable()" ></cc-number-input>'
        }]
    };

    /**
     * gridOptionsHAJP7030KikakuInfArea is generated.
     *
     * @param
     * @return
     */

    /**
     * Initialize the grid options.
     */
    $scope.gridOptionsHAJP7030KikakuInfArea = {
        data: 'queryResult',
        enablePaging: true,
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
            width: 35,
            cellTemplate: '<div class="cs-center  ngCellText cellText">{{row.rowIndex + 1}}</div>'
        }, {
            field: 'triCd',
            displayName: '取引先',
            width: 85,
            cellTemplate: '<div class=" ngCellText cellText">{{COL_FIELD | ccTriCdFilter}}</div>'
        }, {
            field: 'shnCd',
            displayName: 'JAN',
            width: 110,
            cellTemplate: '<div class=" ngCellText cellText">{{COL_FIELD}}</div>'
        }, {
            field: 'shnNm',
            displayName: '商品名称',
            width: 154,
            cellTemplate: '<div class=" ngCellText cellText">{{COL_FIELD}}</div>'
        }, {
            field: 'genk',
            displayName: '原価金額',
            width: 94,
            cellTemplate: '<div class="cs-right ngCellText cellText">{{COL_FIELD | number:2}}</div>'
        }, {
            field: 'jidoHatKijunsu',
            displayName: '基準在庫',
            width: 94,
            cellTemplate: '<div class="cs-right ngCellText cellText">{{COL_FIELD | number:2}}</div>'
        }, {
            field: 'hattyuIrisu',
            displayName: '人数',
            width: 80,
            cellTemplate: '<div class="cs-right ngCellText cellText">{{COL_FIELD | number:1}}</div>'
        }, {
            field: 'rrnSu',
            displayName: '在庫数',
            width: 94,
            cellTemplate: '<div class="cs-right ngCellText cellText">{{COL_FIELD | number:2}}</div>'
        }, {
            field: 'nyukoYoteiSu',
            displayName: '入庫予定数',
            width: 100,
            cellTemplate: '<div class="cs-right ngCellText cellText">{{COL_FIELD | number:2}}</div>'
        }, {
            field: 'deliverNumber',
            displayName: '納品数',
            width: 82,
            cellTemplate: '<div class="cs-center ngCellText cellText">{{COL_FIELD}}</div>'
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
            $scope.error = Client.clearErrors();
            if ($scope.hasResult == false && $scope.isEditable == false) {
                $scope.cond = {};
                $scope.cond.hacchyuArea = [{
                    "hatpMon": "",
                    "hatpTue": "",
                    "hatpWed": "",
                    "hatpThu": "",
                    "hatpFri": "",
                    "hatpSat": "",
                    "hatpSun": ""
                }];
                $scope.cond.triCd = '';
                $scope.initCond();
            }
            $scope.form.$setPristine();
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

        var control = HAJP7030Control.query($scope.cond, function() {
            $scope.queryResult = control;
            $scope.isEditable = false;
            $scope.hasResult = true;
            
            Message.showMessage(MsgConst.MSG_KEY_PROCESS_SUCCESS);
            $scope.error = Client.clearErrors();
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
        var maker = HAJP7030Init.query({}, function() {
            if (maker != undefined) {
                $scope.cond.jigyobuCd = maker[0].jigyobuCd;
                $scope.cond.tenCd = maker[0].tenCd;
            }
        }, function(response) {
            if (response.status === HttpConst.CODE_UNAUTHORIZED) {
                Message.showMessage(MsgConst.MSG_KEY_TIMEOUT_ERROR);
            } else {
                Message.showMessage(MsgConst.MSG_KEY_SYSTEM_ERROR);
            }
        });
    }

    /**
     * get Hatp
     */
    $scope.$on('triCdBlur', function() {
        if (!isEmpty($scope.cond.triCd) && ($scope.cond.triCd.length == 9) 
                && !isEmpty($scope.cond.jigyobuCd) && !isEmpty($scope.cond.tenCd)) {
            var maker = HAJP7030getHatp.query($scope.cond, function() {
                if (maker != undefined) {
                    $scope.error = Client.clearErrors();
                    $scope.cond.hacchyuArea = maker;
                    $scope.cond.hacchyuArea = [{
                        "hatpMon": maker[0].hatpMon1,
                        "hatpTue": maker[0].hatpTue1,
                        "hatpWed": maker[0].hatpWed1,
                        "hatpThu": maker[0].hatpThu1,
                        "hatpFri": maker[0].hatpFri1,
                        "hatpSat": maker[0].hatpSat1,
                        "hatpSun": maker[0].hatpSun1
                    }];
                    Message.showMessage(MsgConst.MSG_KEY_COMMON_INFO_MESSAGE_F10);
                }
            }, function(response) {
                if (response.status === HttpConst.CODE_NOT_FOUND) {
                    $scope.hasResult = false;
                    Message.showMessage(MsgConst.MSG_KEY_DATA_NOT_EXIST);
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
                $scope.cond.hacchyuArea = [{
                    "hatpMon": "",
                    "hatpTue": "",
                    "hatpWed": "",
                    "hatpThu": "",
                    "hatpFri": "",
                    "hatpSat": "",
                    "hatpSun": ""
                }];
            });
        } else {
            $scope.error = Client.clearErrors();
            $scope.cond.hacchyuArea = [{
                "hatpMon": "",
                "hatpTue": "",
                "hatpWed": "",
                "hatpThu": "",
                "hatpFri": "",
                "hatpSat": "",
                "hatpSun": ""
            }];
        }
    });
});