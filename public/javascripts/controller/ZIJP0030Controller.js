// /////////////////////////////////////////////////////////////////////
// Copyright(C) 2015 NEC Solution Innovators, Ltd. All Rights Reserved.
// /////////////////////////////////////////////////////////////////////

/*
 * ====================================================================
 * 機能名称 : 棚卸情報入力
 * 改版履歴
 * Rev. 改版年月日 改版者名 内容
 * 1.0 2014.03.14 CuongPV 新規作成
 * ===================================================================
 */
var app = angular.module('zijp0030', ['zijp0030Services', 'ui', 'ui.select2', 'directives', 'ngGrid']);
app.controller("ZIJP0030Ctrl", function($scope, ComInfo, Zijp0030, Message, MsgConst, HttpConst, Dialog, DialogInfo, $interval,
    AppConst, UserInfo, FocusConst, GridValidator, Client) {
    Message.showMessage(MsgConst.MSG_KEY_COMMON_INFO_MESSAGE);
    $scope.cond = {
        kaisyaCd: AppConst.KAISYA_CODE
    };
    $scope.result = {};
    $scope.isEditable = false;
    $scope.hasResult = false;
    $scope.isNewData = false;
    $scope.isBmnGridEditable = false;
    $scope.isGaiGridEditable = false;
    $scope.isTenGridEditable = false;
    $scope.focusCond = FocusConst.COMBOBOX_INIT_FOCUS;
    $scope.focusResult = FocusConst.NOT_FOCUS;

    /**
     * Get focus of condition form.
     */
    $scope.getFocusCond = function() {
        return $scope.focusCond;
    }
    
    /**
     * Get focus of result form.
     */
    $scope.getFocusResult = function() {
        return $scope.focusResult;
    }
    
    /**
     * Switch focus between condition and result form.
     */
    $scope.setFocus = function() {
        if ($scope.isEditable) {
          setFocusResult($scope, $interval, FocusConst.TEXTINPUT_FOCUS);
        } else {
          setFocusCond($scope, $interval, FocusConst.COMBOBOX_NOT_INIT_FOCUS);
        }
    }

    /**
     * Define rowTemplate.
     */
    var rowTemplate =
        '<div ng-repeat="col in renderedColumns" ng-class="col.colIndex()" class="ngCell {{col.cellClass}}"><div class="ngVerticalBar" ng-style="{height: rowHeight}" ng-class="{ ngVerticalBarVisible: !$last }">&nbsp;</div><div ng-cell-custom></div></div>';

    /**
     * Change read-only status of controls.
     * 
     * @return true:read-only false:not read-only 
     */
    $scope.changeDisable = function() {
        return $scope.isEditable;
    }

    /**
     * Row is fist row in grid
     * 
     * @return true:row is first row false:else
     */
    $scope.isFirstRow = function(id) {
        if (id == 0) {
            return true;
        }
        return false;
    }

    /**
     * Initialize the grid options.
     */
    $scope.gridOptionsZIJP0030BmnTnJiArea = {
        data: 'result.zijp0030BmnDtos',
        enablePaging: false,
        showFooter: false,
        rowTemplate: rowTemplate,
        footerRowHeight: 0,
        headerRowHeight: 19,
        rowHeight: 20,
        enableCellSelection: true,
        enableRowSelection: false,
        enableSorting: false,
        enableColumnResize: false,
        columnDefs: [{
            field: 'bmnCd',
            displayName: '部門コード',
            width: 100.0,
            cellTemplate: '<div>&nbsp;{{row.entity.bmnCd}}</div>'
        }, {
            field: 'bmnNm',
            displayName: '部門名称',
            width: 190.0,
            cellTemplate: '<div>&nbsp;{{row.entity.bmnNm}}</div>'
        }, {
            field: 'baiKanKbn',
            displayName: '在庫計算方式',
            width: 171.0,
            cellTemplate: '<div>&nbsp;{{row.entity.baiKanKbn}}</div>'
        }, {
            field: 'april',
            displayName: '４月',
            width: 40.0,
            cellTemplate: '<cc-number-input cc-label="" cc-partition="" id="april_{{row.rowIndex}}" name="april_{{row.rowIndex}}" ng-model="COL_FIELD" cc-readonly="!changeDisable()" cc-error="error" cc-required="false" cc-min="0" cc-max="99" cc-focus="getFocusResult() && isFirstRow(row.rowIndex)" cc-width="37" ></cc-number-input>'
        }, {
            field: 'may',
            displayName: '５月',
            width: 40.0,
            cellTemplate: '<cc-number-input cc-label="" cc-partition="" id="may_{{row.rowIndex}}" name="may_{{row.rowIndex}}" ng-model="COL_FIELD" cc-readonly="!changeDisable()" cc-error="error" cc-required="false" cc-min="0" cc-max="99" cc-width="37"></cc-number-input>'
        }, {
            field: 'june',
            displayName: '６月',
            width: 40.0,
            cellTemplate: '<cc-number-input cc-label="" cc-partition="" id="june_{{row.rowIndex}}" name="june_{{row.rowIndex}}" ng-model="COL_FIELD" cc-readonly="!changeDisable()" cc-error="error" cc-required="false" cc-min="0" cc-max="99" cc-width="37"></cc-number-input>'
        }, {
            field: 'july',
            displayName: '７月',
            width: 40.0,
            cellTemplate: '<cc-number-input cc-label="" cc-partition="" id="july_{{row.rowIndex}}" name="july_{{row.rowIndex}}" ng-model="COL_FIELD" cc-readonly="!changeDisable()" cc-error="error" cc-required="false" cc-min="0" cc-max="99" cc-width="37"></cc-number-input>'
        }, {
            field: 'august',
            displayName: '８月',
            width: 40.0,
            cellTemplate: '<cc-number-input cc-label="" cc-partition="" id="august_{{row.rowIndex}}" name="august_{{row.rowIndex}}" ng-model="COL_FIELD" cc-readonly="!changeDisable()" cc-error="error" cc-required="false" cc-min="0" cc-max="99" cc-width="37"></cc-number-input>'
        }, {
            field: 'september',
            displayName: '９月',
            width: 40.0,
            cellTemplate: '<cc-number-input cc-label="" cc-partition="" id="september_{{row.rowIndex}}" name="september_{{row.rowIndex}}" ng-model="COL_FIELD" cc-readonly="!changeDisable()" cc-error="error" cc-required="false" cc-min="0" cc-max="99" cc-width="37" ></cc-number-input>'
        }, {
            field: 'october',
            displayName: '10月',
            width: 40.0,
            cellTemplate: '<cc-number-input cc-label="" cc-partition="" id="october_{{row.rowIndex}}" name="october_{{row.rowIndex}}" ng-model="COL_FIELD" cc-readonly="!changeDisable()" cc-error="error" cc-required="false" cc-min="0" cc-max="99" cc-width="37"></cc-number-input>'
        }, {
            field: 'november',
            displayName: '11月',
            width: 40.0,
            cellTemplate: '<cc-number-input cc-label="" cc-partition="" id="november_{{row.rowIndex}}" name="november_{{row.rowIndex}}" ng-model="COL_FIELD" cc-readonly="!changeDisable()" cc-error="error" cc-required="false" cc-min="0" cc-max="99" cc-width="37"></cc-number-input>'
        }, {
            field: 'december',
            displayName: '12月',
            width: 40.0,
            cellTemplate: '<cc-number-input cc-label="" cc-partition="" id="december_{{row.rowIndex}}" name="december_{{row.rowIndex}}" ng-model="COL_FIELD" cc-readonly="!changeDisable()" cc-error="error" cc-required="false" cc-min="0" cc-max="99" cc-width="37"></cc-number-input>'
        }, {
            field: 'january',
            displayName: '１月',
            width: 40.0,
            cellTemplate: '<cc-number-input cc-label="" cc-partition="" id="january_{{row.rowIndex}}" name="january_{{row.rowIndex}}" ng-model="COL_FIELD" cc-readonly="!changeDisable()" cc-error="error" cc-required="false" cc-min="0" cc-max="99" cc-width="37"></cc-number-input>'
        }, {
            field: 'february',
            displayName: '２月',
            width: 40.0,
            cellTemplate: '<cc-number-input cc-label="" cc-partition="" id="february_{{row.rowIndex}}" name="february_{{row.rowIndex}}" ng-model="COL_FIELD" cc-readonly="!changeDisable()" cc-error="error" cc-required="false" cc-min="0" cc-max="99" cc-width="37"></cc-number-input>'
        }, {
            field: 'march',
            displayName: '３月',
            width: 40.0,
            cellTemplate: '<cc-number-input cc-label="" cc-partition="" id="march_{{row.rowIndex}}" name="march_{{row.rowIndex}}" ng-model="COL_FIELD" cc-readonly="!changeDisable()" cc-error="error" cc-required="false" cc-min="0" cc-max="99" cc-width="37"></cc-number-input>'
        }]
    };

    /**
     * Initialize the grid options.
     */
    $scope.gridOptionsZIJP0030TenTnJiArea = {
        data: 'result.zijp0030TenDtos',
        enablePaging: false,
        showFooter: false,
        rowTemplate: rowTemplate,
        footerRowHeight: 0,
        headerRowHeight: 19,
        rowHeight: 20,
        enableCellSelection: true,
        enableRowSelection: false,
        enableSorting: false,
        enableColumnResize: false,
        columnDefs: [{
            field: 'tenCd',
            displayName: '店舗コード',
            width: 100.0,
            cellTemplate: '<div>&nbsp;{{row.entity.tenCd}}</div>'
        }, {
            field: 'tenNm',
            displayName: '店舗名称',
            width: 190.0,
            cellTemplate: '<div>&nbsp;{{row.entity.tenNm}}</div>'
        }, {
            field: 'baiKanKbn',
            displayName: '在庫計算方式',
            width: 171.0,
            cellTemplate: '<div>&nbsp;{{row.entity.baiKanKbn}}</div>'
        }, {
            field: 'april',
            displayName: '４月',
            width: 40.0,
            cellTemplate: '<cc-number-input cc-label="" cc-partition="" id="april_{{row.rowIndex}}" name="april_{{row.rowIndex}}" ng-model="COL_FIELD" cc-readonly="!changeDisable()" cc-error="error" cc-required="true" cc-min="0" cc-max="99" cc-width="37" cc-focus="getFocusResult() && isFirstRow(row.rowIndex)"></cc-number-input>'
        }, {
            field: 'may',
            displayName: '５月',
            width: 40.0,
            cellTemplate: '<cc-number-input cc-label="" cc-partition="" id="may_{{row.rowIndex}}" name="may_{{row.rowIndex}}" ng-model="COL_FIELD" cc-readonly="!changeDisable()" cc-error="error" cc-required="true" cc-min="0" cc-max="99" cc-width="37"></cc-number-input>'
        }, {
            field: 'june',
            displayName: '６月',
            width: 40.0,
            cellTemplate: '<cc-number-input cc-label="" cc-partition="" id="june_{{row.rowIndex}}" name="june_{{row.rowIndex}}" ng-model="COL_FIELD" cc-readonly="!changeDisable()" cc-error="error" cc-required="true" cc-min="0" cc-max="99" cc-width="37"></cc-number-input>'
        }, {
            field: 'july',
            displayName: '７月',
            width: 40.0,
            cellTemplate: '<cc-number-input cc-label="" cc-partition="" id="july_{{row.rowIndex}}" name="july_{{row.rowIndex}}" ng-model="COL_FIELD" cc-readonly="!changeDisable()" cc-error="error" cc-required="true" cc-min="0" cc-max="99" cc-width="37"></cc-number-input>'
        }, {
            field: 'august',
            displayName: '８月',
            width: 40.0,
            cellTemplate: '<cc-number-input cc-label="" cc-partition="" id="august_{{row.rowIndex}}" name="august_{{row.rowIndex}}" ng-model="COL_FIELD" cc-readonly="!changeDisable()" cc-error="error" cc-required="true" cc-min="0" cc-max="99" cc-width="37"></cc-number-input>'
        }, {
            field: 'september',
            displayName: '９月',
            width: 40.0,
            cellTemplate: '<cc-number-input cc-label="" cc-partition="" id="september_{{row.rowIndex}}" name="september_{{row.rowIndex}}" ng-model="COL_FIELD" cc-readonly="!changeDisable()" cc-error="error" cc-required="true" cc-min="0" cc-max="99" cc-width="37"></cc-number-input>'
        }, {
            field: 'october',
            displayName: '10月',
            width: 40.0,
            cellTemplate: '<cc-number-input cc-label="" cc-partition="" id="october_{{row.rowIndex}}" name="october_{{row.rowIndex}}" ng-model="COL_FIELD" cc-readonly="!changeDisable()" cc-error="error" cc-required="true" cc-min="0" cc-max="99" cc-width="37"></cc-number-input>'
        }, {
            field: 'november',
            displayName: '11月',
            width: 40.0,
            cellTemplate: '<cc-number-input cc-label="" cc-partition="" id="november_{{row.rowIndex}}" name="november_{{row.rowIndex}}" ng-model="COL_FIELD" cc-readonly="!changeDisable()" cc-error="error" cc-required="true" cc-min="0" cc-max="99" cc-width="37"></cc-number-input>'
        }, {
            field: 'december',
            displayName: '12月',
            width: 40.0,
            cellTemplate: '<cc-number-input cc-label="" cc-partition="" id="december_{{row.rowIndex}}" name="december_{{row.rowIndex}}" ng-model="COL_FIELD" cc-readonly="!changeDisable()" cc-error="error" cc-required="true" cc-min="0" cc-max="99" cc-width="37"></cc-number-input>'
        }, {
            field: 'january',
            displayName: '１月',
            width: 40.0,
            cellTemplate: '<cc-number-input cc-label="" cc-partition="" id="january_{{row.rowIndex}}" name="january_{{row.rowIndex}}" ng-model="COL_FIELD" cc-readonly="!changeDisable()" cc-error="error" cc-required="true" cc-min="0" cc-max="99" cc-width="37"></cc-number-input>'
        }, {
            field: 'february',
            displayName: '２月',
            width: 40.0,
            cellTemplate: '<cc-number-input cc-label="" cc-partition="" id="february_{{row.rowIndex}}" name="february_{{row.rowIndex}}" ng-model="COL_FIELD" cc-readonly="!changeDisable()" cc-error="error" cc-required="true" cc-min="0" cc-max="99" cc-width="37"></cc-number-input>'
        }, {
            field: 'march',
            displayName: '３月',
            width: 40.0,
            cellTemplate: '<cc-number-input cc-label="" cc-partition="" id="march_{{row.rowIndex}}" name="march_{{row.rowIndex}}" ng-model="COL_FIELD" cc-readonly="!changeDisable()" cc-error="error" cc-required="true" cc-min="0" cc-max="99" cc-width="37"></cc-number-input>'
        }]
    };

    /**
     * Initialize the grid options.
     */
    $scope.gridOptionsZIJP0030GaichuTnJiArea = {
        data: 'result.zijp0030GaiDtos',
        enablePaging: false,
        showFooter: false,
        rowTemplate: rowTemplate,
        footerRowHeight: 0,
        headerRowHeight: 19,
        rowHeight: 20,
        enableCellSelection: true,
        enableRowSelection: false,
        enableSorting: false,
        enableColumnResize: false,
        columnDefs: [{
            field: 'gaiTanaOrosi',
            displayName: '',
            width: 128.0,
            cellTemplate: '<div>&nbsp;外注棚卸月</div>'
        }, {
            field: 'april',
            displayName: '４月',
            width: 40.0,
            cellTemplate: '<cc-number-input cc-label="" cc-partition="" id="april_inventory_{{row.rowIndex}}" name="april_inventory_{{row.rowIndex}}" ng-model="COL_FIELD" cc-readonly="!changeDisable()" cc-error="error" cc-required="true" cc-min="0" cc-max="1" cc-width="37"></cc-number-input>'
        }, {
            field: 'may',
            displayName: '５月',
            width: 40.0,
            cellTemplate: '<cc-number-input cc-label="" cc-partition="" id="may_inventory_{{row.rowIndex}}" name="may_inventory_{{row.rowIndex}}" ng-model="COL_FIELD" cc-readonly="!changeDisable()" cc-error="error" cc-required="true" cc-min="0" cc-max="1" cc-width="37"></cc-number-input>'
        }, {
            field: 'june',
            displayName: '６月',
            width: 40.0,
            cellTemplate: '<cc-number-input cc-label="" cc-partition="" id="june_inventory_{{row.rowIndex}}" name="june_inventory_{{row.rowIndex}}" ng-model="COL_FIELD" cc-readonly="!changeDisable()" cc-error="error" cc-required="true" cc-min="0" cc-max="1" cc-width="37"></cc-number-input>'
        }, {
            field: 'july',
            displayName: '７月',
            width: 40.0,
            cellTemplate: '<cc-number-input cc-label="" cc-partition="" id="july_inventory_{{row.rowIndex}}" name="july_inventory_{{row.rowIndex}}" ng-model="COL_FIELD" cc-readonly="!changeDisable()" cc-error="error" cc-required="true" cc-min="0" cc-max="1" cc-width="37"></cc-number-input>'
        }, {
            field: 'august',
            displayName: '８月',
            width: 40.0,
            cellTemplate: '<cc-number-input cc-label="" cc-partition="" id="august_inventory_{{row.rowIndex}}" name="august_inventory_{{row.rowIndex}}" ng-model="COL_FIELD" cc-readonly="!changeDisable()" cc-error="error" cc-required="true" cc-min="0" cc-max="1" cc-width="37"></cc-number-input>'
        }, {
            field: 'september',
            displayName: '９月',
            width: 40.0,
            cellTemplate: '<cc-number-input cc-label="" cc-partition="" id="september_inventory_{{row.rowIndex}}" name="september_inventory_{{row.rowIndex}}" ng-model="COL_FIELD" cc-readonly="!changeDisable()" cc-error="error" cc-required="true" cc-min="0" cc-max="1" cc-width="37"></cc-number-input>'
        }, {
            field: 'october',
            displayName: '10月',
            width: 40.0,
            cellTemplate: '<cc-number-input cc-label="" cc-partition="" id="october_inventory_{{row.rowIndex}}" name="october_inventory_{{row.rowIndex}}" ng-model="COL_FIELD" cc-readonly="!changeDisable()" cc-error="error" cc-required="true" cc-min="0" cc-max="1" cc-width="37"></cc-number-input>'
        }, {
            field: 'november',
            displayName: '11月',
            width: 40.0,
            cellTemplate: '<cc-number-input cc-label="" cc-partition="" id="november_inventory_{{row.rowIndex}}" name="november_inventory_{{row.rowIndex}}" ng-model="COL_FIELD" cc-readonly="!changeDisable()" cc-error="error" cc-required="true" cc-min="0" cc-max="1" cc-width="37"></cc-number-input>'
        }, {
            field: 'december',
            displayName: '12月',
            width: 40.0,
            cellTemplate: '<cc-number-input cc-label="" cc-partition="" id="december_inventory_{{row.rowIndex}}" name="december_inventory_{{row.rowIndex}}" ng-model="COL_FIELD" cc-readonly="!changeDisable()" cc-error="error" cc-required="true" cc-min="0" cc-max="1" cc-width="37"></cc-number-input>'
        }, {
            field: 'january',
            displayName: '１月',
            width: 40.0,
            cellTemplate: '<cc-number-input cc-label="" cc-partition="" id="january_inventory_{{row.rowIndex}}" name="january_inventory_{{row.rowIndex}}" ng-model="COL_FIELD" cc-readonly="!changeDisable()" cc-error="error" cc-required="true" cc-min="0" cc-max="1" cc-width="37"></cc-number-input>'
        }, {
            field: 'february',
            displayName: '２月',
            width: 40.0,
            cellTemplate: '<cc-number-input cc-label="" cc-partition="" id="february_inventory_{{row.rowIndex}}" name="february_inventory_{{row.rowIndex}}" ng-model="COL_FIELD" cc-readonly="!changeDisable()" cc-error="error" cc-required="true" cc-min="0" cc-max="1" cc-width="37"></cc-number-input>'
        }, {
            field: 'march',
            displayName: '３月',
            width: 40.0,
            cellTemplate: '<cc-number-input cc-label="" cc-partition="" id="march_inventory_{{row.rowIndex}}" name="march_inventory_{{row.rowIndex}}" ng-model="COL_FIELD" cc-readonly="!changeDisable()" cc-error="error" cc-required="true" cc-min="0" cc-max="1" cc-width="37"></cc-number-input>'
        }]
    };

    /**
     * Search/Edit data from database.
     */
    $scope.doSearch = function() {
        $scope.error = null;
        $scope.result = {};
        $scope.result.bmnCd = $scope.cond.bmnCd;
        $scope.cond.unyoDate = UserInfo.unyoDate;
        $scope.cond.tantoCd = UserInfo.tantoCd;
        var zijp0030 = Zijp0030
            .query(
                $scope.cond,
                function() {
                    $scope.result = zijp0030[0];
                    $scope.originalResult = angular.copy($scope.result);
                    $scope.isEditable = false;
                    $scope.hasResult = true;
                    $scope.isNewData = false;
                    Message.showMessage(MsgConst.MSG_KEY_DISPLAY_CORRECT_DATA);
                    $scope.error = null;
                    $scope.setFocus();
                }, function(response) {
                    if (response.status === HttpConst.CODE_NOT_FOUND) {
                        // エラー処理
                        Message.showMessageWithContent(response.data.errors[0].level, response.data.errors[0].message);
                        $scope.error = Client.showErrorFromServer($scope.error, response.data.errors);
                    } else if (response.status === HttpConst.CODE_BAD_REQUEST) {
                        // エラー処理
                        $scope.hasResult = false;
                        Message.showMessageWithContent(response.data.errors[0].level, response.data.errors[0].message);
                        $scope.error = Client.showErrorFromServer($scope.error, response.data.errors);
                    } else {
                        Message.showMessage(MsgConst.MSG_KEY_SYSTEM_ERROR);
                    }
                    $scope.setFocus();
                });
    }

    /**
     * Search/Edit data from database.
     */
    $scope.doSearchEdit = function() {
        $scope.error = null;
        Message.showMessage(MsgConst.MSG_KEY_COMMON_INFO_MESSAGE);
        $scope.hasResult = false;
        $scope.isNewData = false;
        $scope.result = {};
        $scope.result.bmnCd = $scope.cond.bmnCd;
        $scope.cond.unyoDate = UserInfo.unyoDate;
        $scope.cond.tantoCd = UserInfo.tantoCd;
        var zijp0030 = Zijp0030
            .query(
                $scope.cond,
                function() {
                    $scope.result = zijp0030[0];
                    $scope.originalResult = angular.copy($scope.result);
                    $scope.isEditable = true;
                    $scope.hasResult = true;
                    $scope.isNewData = false;
                    Message.showMessage(MsgConst.MSG_KEY_INFO_BUTTON_SAVE);
                    $scope.error = null;
                    $scope.setFocus();
                }, function(response) {
                    if (response.status === 404) {
                        Message.showMessage(MsgConst.MSG_KEY_SYSTEM_ERROR);
                    } else if (response.status === HttpConst.CODE_BAD_REQUEST) {
                        // エラー処理
                        $scope.hasResult = false;
                        Message.showMessageWithContent(response.data.errors[0].level, response.data.errors[0].message);
                        $scope.error = Client.showErrorFromServer($scope.error, response.data.errors);
                    } else {
                        // エラー処理
                        Message.showMessage(MsgConst.MSG_KEY_SYSTEM_ERROR);
                    }
                    $scope.setFocus();
                });
    }

    /**
     * Insert/Update data to database.
     */
    $scope.doInsertUpdate = function() {
        $scope.doInsert();
    }

    /**
     * Insert data to database.
     */
    $scope.doInsert = function() {
        $scope.error = null;
        var insertConfirmDialog = Dialog.confirm(Message.getMessage(MsgConst.MSG_KEY_CONFIRM_BEFORE_INSERT));
        if (insertConfirmDialog == null) {
            return;
        }
        insertConfirmDialog.result.then(function() {
            $scope.result.tenCd = $scope.cond.tenCd;
            $scope.result.kaisyaCd = $scope.cond.kaisyaCd;
            $scope.result.jigyobuCd = $scope.cond.jigyobuCd;
            $scope.result.bmnCd = $scope.cond.bmnCd;
            $scope.result.unyoDate = UserInfo.unyoDate;
            $scope.result.tantoCd = UserInfo.tantoCd;
            Zijp0030.save($scope.result, function() {
                // 再度検索可能な状態にする
                $scope.isNewData = false;
                $scope.isEditable = false;
                Message.showMessage(MsgConst.MSG_KEY_INSERT_SUCCESS);
                $scope.error = null;
                $scope.setFocus();
            }, function(response) {
                if (response.status === HttpConst.CODE_BAD_REQUEST) {
                    // エラー処理
                    Message.showMessageWithContent(response.data.errors[0].level, response.data.errors[0].message);
                    $scope.error = Client.showErrorFromServer($scope.error, response.data.errors);
                } else {
                    Message.showMessage(MsgConst.MSG_KEY_SYSTEM_ERROR);
                }
                $scope.setFocus();
            });
        }, function() {
            $scope.setFocus();
        });
    }

    /**
     * Clear input data of controls.
     */
    $scope.doClear = function() {
        $scope.form.$setPristine();
        var clearDialog = Dialog.confirm(Message.getMessage(MsgConst.MSG_KEY_CONFIRM_BEFORE_CLEAR));
        if (clearDialog == null) {
            return;
        }
        clearDialog.result.then(function() {
            if ($scope.hasResult == false && $scope.isEditable == false) {
                $scope.cond.kaisyaCd = AppConst.KAISYA_CODE;
                $scope.cond.jigyobuCd = '';
                $scope.cond.tenCd = '';
                $scope.cond.bmnCd = '';
            }
            $scope.result = {};
            $scope.hasResult = false;
            $scope.isEditable = false;
            $scope.isNewData = false;
            Message.showMessage(MsgConst.MSG_KEY_COMMON_INFO_MESSAGE);
            $scope.error = Client.clearErrors();
            $scope.setFocus();
        }, function() {
            $scope.setFocus();
        });
    }

    /**
     * Validate all input controls of grid.
     * 
     * @return true:valid false:invalid
     */
    $scope.isValid = false;
    $scope.$on('valid', function(data) {
        if ($scope.hasResult || $scope.isNewData) {
            return GridValidator($scope, $scope.isEditable);
        }
        return $scope.isValid;
    });

    /**
     * Check if have changing data on screen to Enable/Disable Save button.
     * 
     * @return true:enable false:disable
     */
    $scope.canSave = function() {
        if ($scope.isEditable && $scope.isValid && !angular.equals($scope.result, $scope.originalResult)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Check controls of the condForm area to Enable/Disable Search button.
     * 
     * @return true:enable false:disable
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
     * Check controls of the condForm area to Enable/Disable SearchEdit button.
     * 
     * @return true:enable false:disable
     */
    $scope.canSearchEdit = function() {
        if ($scope.form.condForm == null) {
            return;
        }

        if ($scope.isEditable || $scope.form.condForm.$invalid) {
            return false;
        } else {
            return true;
        }
    }


    $scope.$on('ngGridEventFinishScroll', function() {
        var errorArr = angular.copy($scope.error);
        $scope.error = Client.showErrorFromServer($scope.error, errorArr);
    });
    $scope.$on('ccResetServerClientError', function(event, controlName) {
        $scope.error = Client.removeAllErrorByName($scope.error, controlName);
    });
});