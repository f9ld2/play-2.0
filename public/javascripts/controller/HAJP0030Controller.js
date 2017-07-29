///////////////////////////////////////////////////////////////////////
//Copyright(C) 2015 NEC Solution Innovators, Ltd. All Rights Reserved.
///////////////////////////////////////////////////////////////////////

/*
 * ====================================================================
 *
 * 機能名称 : 物流スケジュールメンテ
 *
 * 改版履歴
 *
 * Rev. 改版年月日 改版者名 内容
 *
 * 1.0 2014.04.18 ToanPQ 新規作成
 *
 * ===================================================================
 */

var app = angular.module('hajp0030', ['hajp0030Services', 'ui', 'ui.select2', 'directives']);
app.controller("HAJP0030Ctrl", function($scope, Message, MsgConst, HttpConst, HAJP0030Control, HAJP0030Ext, $filter, Dialog, DialogInfo, $interval, GetTabError, TabValidator, FocusConst, Client) {
    $scope.cond = {};
    $scope.result = {};
    $scope.isEditable = false;
    $scope.hasResult = false;
    $scope.isNewData = false;
    $scope.tabs = ['定番発注', '特売発注'];
    $scope.index = 0;
    $scope.originalResult = {};
    Message.showMessage(MsgConst.MSG_KEY_COMMON_INFO_MESSAGE);
    $scope.sysdate = "";
    $scope.tabIsLoaded = false;
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
    $scope.getFocusResult = function(focusFlg) {
        if (!$scope.isEditable || !focusFlg) {
            return 0;
        }
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
     * Insert/Update data to database.
     */
    $scope.doInsertUpdate = function() {
        if ($scope.isNewData) {
            $scope.doInsert();
        } else {
            $scope.doUpdate();
        }
    }

    /**
     * Validate input controls.
     */
    $scope.isValid = false;
    $scope.$on('valid',
            function(data) {
        return TabValidator($scope, $scope.isEditable);
    });

    /**
     * Set system date to 'yyyyMM'+ '00'.
     */
    $scope.$watch('cond.mm', function(value) {
        if (!isEmpty($scope.cond.mm)) {
            $scope.sysdate = $scope.cond.yyyy + $scope.cond.mm + "00";
        } else {
            $scope.sysdate = "";
        }
    });

    /**
     * Return the editable status.
     * 
     * @return isEditable flag
     */
    $scope.changeDisable = function() {
        return $scope.isEditable;
    }

    /**
     * Check controls of the condForm area to Enable/Disable Search button.
     * 
     * @return true:enable false:disable
     */
    $scope.canSearch = function() {
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
        if ($scope.isEditable || $scope.form.condForm.$invalid) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * Check having new data to Enable/Disable Delete button.
     * 
     * @return true:enable false:disable
     */
    $scope.canDelete = function() {
        if ($scope.isEditable && !$scope.isNewData) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Set the tab loading status is finish.
     */
    $scope.initTab = function() {
        $scope.tabIsLoaded = true;
    }

    /**
     * Highlight tab header when inputs exits cs-error class.
     */
    $scope.hasError = function(index) {
        if ($scope.tabIsLoaded) {
            return GetTabError(index);
        }
        return false;
    }

    /**
     * Check if have changing data on screen to Enable/Disable Save button.
     * 
     * @return true:enable false:disable
     */
    $scope.canSave = function() {
        if ($scope.isEditable && $scope.isValid && !angular.equals(cloneData($scope.result[0].gridData), cloneData($scope.originalResult[0].gridData))) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Search data from database.
     */
    $scope.doSearch = function() {
        $scope.result = {};
        $scope.totalServerItems = 0;
        $scope.error = null;
        $scope.hasResult = false;
        $scope.cond.searchEditMode = false;


        var control = HAJP0030Control.query($scope.cond, function() {
            $scope.result = control;
            $scope.isEditable = false;
            $scope.isNewData = false;
            $scope.hasResult = true;
            $scope.originalResult = angular.copy($scope.result);

            Message.showMessage(MsgConst.MSG_KEY_DISPLAY_CORRECT_DATA);

            $scope.setFocus();
        }, function(response) {
            if (response.status === HttpConst.CODE_NOT_FOUND) {
                Message.showMessageWithContent(response.data.errors[0].level, response.data.errors[0].message);
            } else if (response.status === HttpConst.CODE_BAD_REQUEST) {
                // エラー処理
                Message.showMessageWithContent(response.data.errors[0].level, response.data.errors[0].message);
                Client.showErrorFromServer($scope.error, response.data.errors);
            } else {
                Message.showMessage(MsgConst.MSG_KEY_SYSTEM_ERROR);
            }

            // データを削除
            $scope.result = {};
            $scope.totalServerItems = 0;

            $scope.setFocus();
        });
    }

    /**
     * Search/Edit data from database.
     */
    $scope.doSearchEdit = function() {
        $scope.result = {};
        $scope.totalServerItems = 0;
        $scope.error = null;
        $scope.cond.searchEditMode = true;


        var control = HAJP0030Control.query($scope.cond, function() {
            if (control[0].flgNewRecord) {
                var confirmDialog = Dialog.confirm(Message.getMessage(MsgConst.MSG_KEY_CONFIRM_NEW_REGISTERED));
                if (confirmDialog == null) {
                    return;
                }
                confirmDialog.result.then(function() {
                    $scope.result = control;
                    $scope.originalResult = angular.copy($scope.result);
                    $scope.isNewData = true;
                    $scope.isEditable = true;
                    $scope.hasResult = true;
                    Message.showMessage(MsgConst.MSG_KEY_INFO_BUTTON_SAVE);
                    $scope.setFocus();
                }, function() {
                    $scope.result = {};
                    $scope.originalResult = {};
                    $scope.hasResult = false;
                    $scope.isEditable = false;
                    Message.showMessage(MsgConst.MSG_KEY_COMMON_INFO_MESSAGE);
                    $scope.setFocus();
                });
            } else {
                $scope.result = control;
                $scope.originalResult = angular.copy($scope.result);
                $scope.isNewData = $scope.result[0].flgNewRecord;
                $scope.isEditable = true;
                $scope.hasResult = true;
                Message.showMessage(MsgConst.MSG_KEY_INFO_BUTTON_DELETE_SAVE);
            }

            $scope.setFocus();
        }, function(response) {
            if (response.status === HttpConst.CODE_NOT_FOUND) {
                $scope.hasResult = false;
                Message.showMessageWithContent(response.data.errors[0].level, response.data.errors[0].message);
            } else if (response.status === HttpConst.CODE_BAD_REQUEST) {
                // エラー処理
                $scope.hasResult = false;
                Message.showMessageWithContent(response.data.errors[0].level, response.data.errors[0].message);
                Client.showErrorFromServer($scope.error, response.data.errors);
            } else {
                Message.showMessage(MsgConst.MSG_KEY_SYSTEM_ERROR);
            }

            // データを削除
            $scope.result = {};
            $scope.totalServerItems = 0;

            $scope.setFocus();
        });
    }

    /**
     * Insert/Update data to database.
     */
    $scope.doInsert = function() {
        var confirmDialog = Dialog.confirm(Message.getMessage(MsgConst.MSG_KEY_CONFIRM_BEFORE_INSERT));
        if (confirmDialog == null) {
            return;
        }
        confirmDialog.result.then(function() {
            $scope.error = null;
            var control = HAJP0030Control.save($scope.result[0], function() {
                // success                  
                $scope.isEditable = false;
                Message.showMessage(MsgConst.MSG_KEY_INSERT_SUCCESS);

                // 新しい支払合計金額のリストを更新
                $scope.originalResult = angular.copy($scope.result);

                $scope.setFocus();
            }, function(response) {
                if (response.status === HttpConst.CODE_NOT_FOUND) {
                    Message.showMessageWithContent(response.data.errors[0].level, response.data.errors[0].message);
                } else if (response.status === HttpConst.CODE_BAD_REQUEST) {
                    // エラー処理
                    Message.showMessageWithContent(response.data.errors[0].level, response.data.errors[0].message);
                    Client.showErrorFromServer($scope.error, response.data.errors);
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
     * Update data to database.
     */
    $scope.doUpdate = function() {
        var confirmDialog = Dialog.confirm(Message.getMessage(MsgConst.MSG_KEY_CONFIRM_BEFORE_UPDATE));
        if (confirmDialog == null) {
            return;
        }
        confirmDialog.result.then(function() {
            $scope.error = null;
            var control = HAJP0030Control.update($scope.result[0], function() {
                // success                  
                $scope.isEditable = false;
                Message.showMessage(MsgConst.MSG_KEY_UPDATE_SUCCESS);

                // 新しい支払合計金額のリストを更新
                $scope.originalResult = angular.copy($scope.result);

                $scope.setFocus();
            }, function(response) {
                if (response.status === HttpConst.CODE_NOT_FOUND) {
                    Message.showMessageWithContent(response.data.errors[0].level, response.data.errors[0].message);
                } else if (response.status === HttpConst.CODE_BAD_REQUEST) {
                    // エラー処理
                    Message.showMessageWithContent(response.data.errors[0].level, response.data.errors[0].message);
                    Client.showErrorFromServer($scope.error, response.data.errors);
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
     * Delete data from database.
     */
    $scope.doDelete = function() {
        var confirmDialog = Dialog.confirm(Message.getMessage(MsgConst.MSG_KEY_CONFIRM_BEFORE_DELETE));
        if (confirmDialog == null) {
            return;
        }
        confirmDialog.result.then(function() {
            $scope.error = null;
            HAJP0030Ext.delete($scope.result[0], function() {
                // 再度検索可能な状態にする
                $scope.isEditable = false;
                Message.showMessage(MsgConst.MSG_KEY_DELETE_SUCCESS);
                $scope.error = null;

                $scope.setFocus();
            }, function(response) {
                if (response.status === HttpConst.CODE_NOT_FOUND) {
                    Message.showMessageWithContent(response.data.errors[0].level, response.data.errors[0].message);
                } else if (response.status === HttpConst.CODE_BAD_REQUEST) {
                    // エラー処理      
                    Message.showMessageWithContent(response.data.errors[0].level, response.data.errors[0].message);
                    Client.showErrorFromServer($scope.error, response.data.errors);
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
        var confirmDialog = Dialog.confirm(Message.getMessage(MsgConst.MSG_KEY_CONFIRM_BEFORE_CLEAR));
        if (confirmDialog == null) {
            return;
        }
        confirmDialog.result.then(function() {
            $scope.form.$setPristine();
            $scope.result = {};
            $scope.originalResult = {};
            if ($scope.hasResult == false && $scope.isEditable == false) {
                

                // データをリセットする
                $scope.cond.yyyy = $scope.getCurrentYear();
                $scope.cond.mm = $scope.getCurrentMonth();
                $scope.cond.triCd = '';
                $scope.cond.bin = '';
            } else {
                $scope.hasResult = false;
            }

            $scope.isEditable = false;
            $scope.tabIsLoaded = false;

            $scope.isNewData = false;
            $scope.totalServerItems = 0;
            $scope.error = Client.clearErrors();

            Message.showMessage(MsgConst.MSG_KEY_COMMON_INFO_MESSAGE);

            $scope.setFocus();
        }, function() {
            $scope.setFocus();
        });
    }

    /**
     * Change read-only status of controls.
     * 
     * @return true:read-only false:not read-only 
     */
    $scope.isReadonly = function(readonlyFlg) {
        if (!$scope.isEditable) {
            return true;
        }
        return readonlyFlg;
    }

    /**
     * Define rowTemplate.
     */
    var rowTemplate = '<div ng-repeat="col in renderedColumns" ng-class="col.colIndex()" class="ngCell {{col.cellClass}}"><div class="ngVerticalBar" ng-style="{height: rowHeight}" ng-class="{ ngVerticalBarVisible: !$last }">&nbsp;</div><div ng-cell-custom></div></div>';

    /**
     * Initialize the grid options.
     */
    $scope.gridOptionsHAJP0030TeiNhnDdArea = {
            data: 'result[0].gridData',
            enablePaging: false,
            showFooter: false,
            footerRowHeight: 0,
            headerRowHeight: 19,
            rowHeight: 20,
            enableCellSelection: true,
            rowTemplate: rowTemplate,
            enableSorting: false,
            enableColumnResize: false,
            columnDefs: [{
                field: 'dd1',
                displayName: '月',
                width: 53,
                cellTemplate: '<div class ="cs-headerCell-middle">{{result[0].gridData[row.rowIndex].dd1}}</div>'
            }, {
                field: 'teiHatDd1',
                displayName: '',
                width: 80,
                cellTemplate: '<div class="cs-bor-b"><cc-date-input cc-label="" cc-focus="getFocusResult(result[0].gridData[row.rowIndex].focus1)" cc-partition="" id="teiHatDd1_{{row.rowIndex}}" name="teiHatDd1_{{row.rowIndex}}" cc-readonly="isReadonly(result[0].gridData[row.rowIndex].readOnly1)" cc-error="error" ng-model="result[0].gridData[row.rowIndex].teiHatDd1" cc-required="false"></cc-date-input></div>'
            }, {
                field: 'dd2',
                displayName: '火',
                width: 53,
                cellTemplate: '<div class ="cs-headerCell-middle">{{result[0].gridData[row.rowIndex].dd2}}</div>'
            }, {
                field: 'teiHatDd2',
                displayName: '',
                width: 80,
                cellTemplate: '<div class="cs-bor-b"><cc-date-input cc-label="" cc-focus="getFocusResult(result[0].gridData[row.rowIndex].focus2)" cc-partition="" id="teiHatDd2_{{row.rowIndex}}" name="teiHatDd2_{{row.rowIndex}}" cc-readonly="isReadonly(result[0].gridData[row.rowIndex].readOnly2)" cc-error="error" ng-model="result[0].gridData[row.rowIndex].teiHatDd2" cc-required="false"></cc-date-input></div>'
            }, {
                field: 'dd3',
                displayName: '水',
                width: 53,
                cellTemplate: '<div class ="cs-headerCell-middle">{{result[0].gridData[row.rowIndex].dd3}}</div>'
            }, {
                field: 'teiHatDd3',
                displayName: '',
                width: 80,
                cellTemplate: '<div class="cs-bor-b"><cc-date-input cc-label="" cc-focus="getFocusResult(result[0].gridData[row.rowIndex].focus3)" cc-partition="" id="teiHatDd3_{{row.rowIndex}}" name="teiHatDd3_{{row.rowIndex}}" cc-readonly="isReadonly(result[0].gridData[row.rowIndex].readOnly3)" cc-error="error" ng-model="result[0].gridData[row.rowIndex].teiHatDd3" cc-required="false"></cc-date-input></div>'
            }, {
                field: 'dd4',
                displayName: '木',
                width: 53,
                cellTemplate: '<div class ="cs-headerCell-middle">{{result[0].gridData[row.rowIndex].dd4}}</div>'
            }, {
                field: 'teiHatDd4',
                displayName: '',
                width: 80,
                cellTemplate: '<div class="cs-bor-b"><cc-date-input cc-label="" cc-focus="getFocusResult(result[0].gridData[row.rowIndex].focus4)" cc-partition="" id="teiHatDd4_{{row.rowIndex}}" name="teiHatDd4_{{row.rowIndex}}" cc-readonly="isReadonly(result[0].gridData[row.rowIndex].readOnly4)" cc-error="error" ng-model="result[0].gridData[row.rowIndex].teiHatDd4" cc-required="false"></cc-date-input></div>'
            }, {
                field: 'dd5',
                displayName: '金',
                width: 54,
                cellTemplate: '<div class ="cs-headerCell-middle">{{result[0].gridData[row.rowIndex].dd5}}</div>'
            }, {
                field: 'teiHatDd5',
                displayName: '',
                width: 80,
                cellTemplate: '<div class="cs-bor-b"><cc-date-input cc-label="" cc-focus="getFocusResult(result[0].gridData[row.rowIndex].focus5)" cc-partition="" id="teiHatDd5_{{row.rowIndex}}" name="teiHatDd5_{{row.rowIndex}}" cc-readonly="isReadonly(result[0].gridData[row.rowIndex].readOnly5)" cc-error="error" ng-model="result[0].gridData[row.rowIndex].teiHatDd5" cc-required="false"></cc-date-input></div>'
            }, {
                field: 'dd6',
                displayName: '',
                headerCellTemplate: '<div class="cs-sat-day">土</div>',
                width: 54,
                cellTemplate: '<div class ="cs-headerCell-middle">{{result[0].gridData[row.rowIndex].dd6}}</div>'
            }, {
                field: 'teiHatDd6',
                displayName: '',
                width: 80,
                cellTemplate: '<div class="cs-bor-b"><cc-date-input cc-label="" cc-focus="getFocusResult(result[0].gridData[row.rowIndex].focus6)" cc-partition="" id="teiHatDd6_{{row.rowIndex}}" name="teiHatDd6_{{row.rowIndex}}" cc-readonly="isReadonly(result[0].gridData[row.rowIndex].readOnly6)" cc-error="error" ng-model="result[0].gridData[row.rowIndex].teiHatDd6" cc-required="false"></cc-date-input></div>'
            }, {
                field: 'dd7',
                displayName: '',
                headerCellTemplate: '<div class="cs-sat-sun">日</div>',
                width: 54,
                cellTemplate: '<div class ="cs-headerCell-middle">{{result[0].gridData[row.rowIndex].dd7}}</div>'
            }, {
                field: 'teiHatDd7',
                displayName: '',
                width: 80,
                cellTemplate: '<div class="cs-bor-b"><cc-date-input cc-label="" cc-focus="getFocusResult(result[0].gridData[row.rowIndex].focus7)" cc-partition="" id="teiHatDd7_{{row.rowIndex}}" name="teiHatDd7_{{row.rowIndex}}" cc-readonly="isReadonly(result[0].gridData[row.rowIndex].readOnly7)" cc-error="error" ng-model="result[0].gridData[row.rowIndex].teiHatDd7" cc-required="false"></cc-date-input></div>'
            }]
    };

    /**
     * Initialize the grid options.
     */
    $scope.gridOptionsHAJP0030TeiLeadTimeArea = {
            data: 'result',
            enablePaging: false,
            showFooter: false,
            footerRowHeight: 0,
            headerRowHeight: 19,
            rowHeight: 20,
            enableRowSelection: false,
            enableSorting: false,
            enableColumnResize: false,
            columnDefs: [{
                field: 'listTimeTable',
                displayName: 'リードタイムテーブル',
                width: 180,
                cellTemplate: '<div class ="cs-headerCell-topHAJP0030">リードタイム</div>'
            }, {
                field: 'hatpMon1',
                displayName: '月',
                width: 34,
                cellClass: "cell text-center",
                cellTemplate: '<div ng-class="col.colIndex()"><span ng-cell-text class="cs-HAJP0030-item">{{result[0].hatpMon1}}</span></div>'
            }, {
                field: 'hatpTue1',
                displayName: '火',
                width: 34,
                cellClass: "cell text-center",
                cellTemplate: '<div ng-class="col.colIndex()"><span ng-cell-text class="cs-HAJP0030-item">{{result[0].hatpTue1}}</span></div>'
            }, {
                field: 'hatpWed1',
                displayName: '水',
                width: 34,
                cellClass: "cell text-center",
                cellTemplate: '<div ng-class="col.colIndex()"><span ng-cell-text class="cs-HAJP0030-item">{{result[0].hatpWed1}}</span></div>'
            }, {
                field: 'hatpThu1',
                displayName: '木',
                width: 34,
                cellClass: "cell text-center",
                cellTemplate: '<div ng-class="col.colIndex()"><span ng-cell-text class="cs-HAJP0030-item">{{result[0].hatpThu1}}</span></div>'
            }, {
                field: 'hatpFri1',
                displayName: '金',
                width: 34,
                cellClass: "cell text-center",
                cellTemplate: '<div ng-class="col.colIndex()"><span ng-cell-text class="cs-HAJP0030-item">{{result[0].hatpFri1}}</span></div>'
            }, {
                field: 'hatpSat1',
                displayName: '土',
                width: 34,
                cellClass: "cell text-center",
                cellTemplate: '<div ng-class="col.colIndex()"><span ng-cell-text class="cs-HAJP0030-item">{{result[0].hatpSat1}}</span></div>'
            }, {
                field: 'hatpSun1',
                displayName: '日',
                width: 34,
                cellClass: "cell text-center",
                cellTemplate: '<div ng-class="col.colIndex()"><span ng-cell-text class="cs-HAJP0030-item">{{result[0].hatpSun1}}</span></div>'
            }]
    };

    /**
     * Initialize the grid options.
     */
    $scope.gridOptionsHAJP0030TokNhnDdArea = {
            data: 'result[0].gridData',
            enablePaging: false,
            showFooter: false,
            footerRowHeight: 0,
            headerRowHeight: 19,
            rowHeight: 20,
            enableCellSelection: true,
            rowTemplate: rowTemplate,
            enableSorting: false,
            enableColumnResize: false,
            columnDefs: [{
                field: 'dd1',
                displayName: '月',
                width: 53,
                cellTemplate: '<div class ="cs-headerCell-middle">{{result[0].gridData[row.rowIndex].dd1}}</div>'
            }, {
                field: 'tokHatDd1',
                displayName: '',
                width: 80,
                cellTemplate: '<div class="cs-bor-b"><cc-date-input cc-label="" cc-focus="getFocusResult(result[0].gridData[row.rowIndex].focus1)" cc-partition="" id="tokHatDd1_{{row.rowIndex}}" name="tokHatDd1_{{row.rowIndex}}" cc-readonly="isReadonly(result[0].gridData[row.rowIndex].readOnly1)" cc-error="error" ng-model="result[0].gridData[row.rowIndex].tokHatDd1" cc-required="false"></cc-date-input></div>'
            }, {
                field: 'dd2',
                displayName: '火',
                width: 53,
                cellTemplate: '<div class ="cs-headerCell-middle">{{result[0].gridData[row.rowIndex].dd2}}</div>'
            }, {
                field: 'tokHatDd2',
                displayName: '',
                width: 80,
                cellTemplate: '<div class="cs-bor-b"><cc-date-input cc-label="" cc-focus="getFocusResult(result[0].gridData[row.rowIndex].focus2)" cc-partition="" id="tokHatDd2_{{row.rowIndex}}" name="tokHatDd2_{{row.rowIndex}}" cc-readonly="isReadonly(result[0].gridData[row.rowIndex].readOnly2)" cc-error="error" ng-model="result[0].gridData[row.rowIndex].tokHatDd2" cc-required="false"></cc-date-input></div>'
            }, {
                field: 'dd3',
                displayName: '水',
                width: 53,
                cellTemplate: '<div class ="cs-headerCell-middle">{{result[0].gridData[row.rowIndex].dd3}}</div>'
            }, {
                field: 'tokHatDd3',
                displayName: '',
                width: 80,
                cellTemplate: '<div class="cs-bor-b"><cc-date-input cc-label="" cc-focus="getFocusResult(result[0].gridData[row.rowIndex].focus3)" cc-partition="" id="tokHatDd3_{{row.rowIndex}}" name="tokHatDd3_{{row.rowIndex}}" cc-readonly="isReadonly(result[0].gridData[row.rowIndex].readOnly3)" cc-error="error" ng-model="result[0].gridData[row.rowIndex].tokHatDd3" cc-required="false"></cc-date-input></div>'
            }, {
                field: 'dd4',
                displayName: '木',
                width: 53,
                cellTemplate: '<div class ="cs-headerCell-middle">{{result[0].gridData[row.rowIndex].dd4}}</div>'
            }, {
                field: 'tokHatDd4',
                displayName: '',
                width: 80,
                cellTemplate: '<div class="cs-bor-b"><cc-date-input cc-label="" cc-focus="getFocusResult(result[0].gridData[row.rowIndex].focus4)" cc-partition="" id="tokHatDd4_{{row.rowIndex}}" name="tokHatDd4_{{row.rowIndex}}" cc-readonly="isReadonly(result[0].gridData[row.rowIndex].readOnly4)" cc-error="error" ng-model="result[0].gridData[row.rowIndex].tokHatDd4" cc-required="false"></cc-date-input></div>'
            }, {
                field: 'dd5',
                displayName: '金',
                width: 54,
                cellTemplate: '<div class ="cs-headerCell-middle">{{result[0].gridData[row.rowIndex].dd5}}</div>'
            }, {
                field: 'tokHatDd5',
                displayName: '',
                width: 80,
                cellTemplate: '<div class="cs-bor-b"><cc-date-input cc-label="" cc-focus="getFocusResult(result[0].gridData[row.rowIndex].focus5)" cc-partition="" id="tokHatDd5_{{row.rowIndex}}" name="tokHatDd5_{{row.rowIndex}}" cc-readonly="isReadonly(result[0].gridData[row.rowIndex].readOnly5)" cc-error="error" ng-model="result[0].gridData[row.rowIndex].tokHatDd5" cc-required="false"></cc-date-input></div>'
            }, {
                field: 'dd6',
                displayName: '',
                headerCellTemplate: '<div class="cs-sat-day">土</div>',
                width: 54,
                cellTemplate: '<div class ="cs-headerCell-middle">{{result[0].gridData[row.rowIndex].dd6}}</div>'
            }, {
                field: 'tokHatDd6',
                displayName: '',
                width: 80,
                cellTemplate: '<div class="cs-bor-b"><cc-date-input cc-label="" cc-focus="getFocusResult(result[0].gridData[row.rowIndex].focus6)" cc-partition="" id="tokHatDd6_{{row.rowIndex}}" name="tokHatDd6_{{row.rowIndex}}" cc-readonly="isReadonly(result[0].gridData[row.rowIndex].readOnly6)" cc-error="error" ng-model="result[0].gridData[row.rowIndex].tokHatDd6" cc-required="false"></cc-date-input></div>'
            }, {
                field: 'dd7',
                displayName: '',
                headerCellTemplate: '<div class="cs-sat-sun">日</div>',
                width: 54,
                cellTemplate: '<div class ="cs-headerCell-middle">{{result[0].gridData[row.rowIndex].dd7}}</div>'
            }, {
                field: 'tokHatDd7',
                displayName: '',
                width: 80,
                cellTemplate: '<div class="cs-bor-b"><cc-date-input cc-label="" cc-focus="getFocusResult(result[0].gridData[row.rowIndex].focus7)" cc-partition="" id="tokHatDd7_{{row.rowIndex}}" name="tokHatDd7_{{row.rowIndex}}" cc-readonly="isReadonly(result[0].gridData[row.rowIndex].readOnly7)" cc-error="error" ng-model="result[0].gridData[row.rowIndex].tokHatDd7" cc-required="false"></cc-date-input></div>'
            }]
    };

    /**
     * Initialize the grid options.
     */
    $scope.gridOptionsHAJP0030TokLeadTimeArea = {
            data: 'result',
            enablePaging: false,
            showFooter: false,
            footerRowHeight: 0,
            headerRowHeight: 19,
            rowHeight: 20,
            enableRowSelection: false,
            enableSorting: false,
            enableColumnResize: false,
            columnDefs: [{
                field: 'listTimeTable',
                displayName: 'リードタイムテーブル',
                width: 180,
                cellTemplate: '<div class ="cs-headerCell-topHAJP0030">リードタイム</div>'
            }, {
                field: 'thatpMon1',
                displayName: '月',
                width: 34,
                cellClass: "cell text-center",
                cellTemplate: '<div ng-class="col.colIndex()"><span ng-cell-text class="cs-HAJP0030-item">{{result[0].thatpMon1}}</span></div>'
            }, {
                field: 'thatpTue1',
                displayName: '火',
                width: 34,
                cellClass: "cell text-center",
                cellTemplate: '<div ng-class="col.colIndex()"><span ng-cell-text class="cs-HAJP0030-item">{{result[0].thatpTue1}}</span></div>'
            }, {
                field: 'thatpWed1',
                displayName: '水',
                width: 34,
                cellClass: "cell text-center",
                cellTemplate: '<div ng-class="col.colIndex()"><span ng-cell-text class="cs-HAJP0030-item">{{result[0].thatpWed1}}</span></div>'
            }, {
                field: 'thatpThu1',
                displayName: '木',
                width: 34,
                cellClass: "cell text-center",
                cellTemplate: '<div ng-class="col.colIndex()"><span ng-cell-text class="cs-HAJP0030-item">{{result[0].thatpThu1}}</span></div>'
            }, {
                field: 'thatpFri1',
                displayName: '金',
                width: 34,
                cellClass: "cell text-center",
                cellTemplate: '<div ng-class="col.colIndex()"><span ng-cell-text class="cs-HAJP0030-item">{{result[0].thatpFri1}}</span></div>'
            }, {
                field: 'thatpSat1',
                displayName: '土',
                width: 34,
                cellClass: "cell text-center",
                cellTemplate: '<div ng-class="col.colIndex()"><span ng-cell-text class="cs-HAJP0030-item">{{result[0].thatpSat1}}</span></div>'
            }, {
                field: 'thatpSun1',
                displayName: '日',
                width: 34,
                cellClass: "cell text-center",
                cellTemplate: '<div ng-class="col.colIndex()"><span ng-cell-text class="cs-HAJP0030-item">{{result[0].thatpSun1}}</span></div>'
            }, ]
    };
});

/**
 * Clone object.
 * 
 * @param d object
 * @return new object
 */
var cloneData = function(d) {
    if (isEmpty(d)) {
        return '';
    }
    switch (d.constructor) {
    case Object:
        var o = {};
        for (var i in d) {
            o[i] = cloneData(d[i]);
        }
        return o;
    case Array:
        var a = [];
        for (var i = 0, l = d.length; i < l; ++i) {
            a[i] = cloneData(d[i]);
        }
        return a;
    case String:
        if (isEmpty(d)) {
            return '';
        } else {
            return d;
        }
    }
    return d;
};