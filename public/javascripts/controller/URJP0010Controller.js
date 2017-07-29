///////////////////////////////////////////////////////////////////////
//Copyright(C) 2015 NEC Solution Innovators, Ltd. All Rights Reserved.
///////////////////////////////////////////////////////////////////////
/*
 * ====================================================================
 * 機能名称 : 日別予算入力
 * 改版履歴 Rev. 改版年月日 改版者名 内容
 * 1.0 2014.04.16 TuanTQ 新規作成
 * ===================================================================
 */
var app = angular.module('urjp0010', ['urjp0010Services', 'ui', 'ui.select2', 'directives']);
app.controller("URJP0010Ctrl", function($scope, Message, MsgConst, $interval, HttpConst, $routeParams, Urjp0010,
    Urjp0010CalCopy, Urjp0010ResourceInit, $timeout, Dialog, DialogInfo, AppConst,
    FocusConst, GridValidator, Client) {
    Message.showMessage(MsgConst.MSG_KEY_COMMON_INFO_MESSAGE_F9);
    $scope.cond = {
        kaisyaCd: AppConst.KAISYA_CODE
    };
    $scope.result = {};

    $scope.initMode = 1;
    $scope.searchMode = 2;
    $scope.editMode = 3;
    $scope.postEditMode = 4;
    $scope.mode = 1;

    $scope.disCon = false;
    $scope.isSaveFinal = false;
    $scope.resultMode = 1;

    $scope.grid1 = [];
    $scope.grid2 = [];
    $scope.grid3 = [];
    $scope.grid4 = [];
    $scope.grid5 = [];
    $scope.grid6 = [];

    $scope.columnDefines = [];
    $scope.queryResult = [];

    $scope.grd6Row = 0;
    $scope.gsindex = 0;
    $scope.iIdx = 0;
    $scope.GRD6_HEAD_ROW = 4;
    $scope.GRD6_ROW = 16;

    $scope.errorsTable = [];
    $scope.isValid = false;

    $scope.focusCond = FocusConst.COMBOBOX_INIT_FOCUS;
    $scope.focusResult = FocusConst.NOT_FOCUS;
    $scope.result.extTenCopy = false;

    /**
     * Get focus of condition form.
     */
    $scope.getFocusCond = function() {
        return $scope.focusCond;
    }

    /**
     * Get focus of result form.
     */
    $scope.getFocusResult = function(index) {
        if ((index == 0 && $scope.resultMode == 1) || (index == 4 && $scope.resultMode == 2)) {
            return $scope.focusResult;
        } else {
            return 0;
        }
    }

    /**
     * Switch focus between condition and result form.
     */
    $scope.setFocus = function() {
        if ($scope.disCon) {
            setFocusResult($scope, $interval, FocusConst.TEXTINPUT_FOCUS);
        } else {
            setFocusCond($scope, $interval, FocusConst.COMBOBOX_NOT_INIT_FOCUS);
        }
    }

    /**
     * Initialize values on the screen.
     */
    $scope.initCond = function() {
        var control = Urjp0010ResourceInit.initCond($routeParams, function() {
            if (control != undefined) {
                if (!isEmpty(control.kaisyaCd)) {
                    $scope.cond.kaisyaCd = control.kaisyaCd;
                }
                if (!isEmpty(control.jigyobuCd)) {
                    $scope.cond.jigyobuCd = control.jigyobuCd;
                }
                if (!isEmpty(control.tenCd)) {
                    $scope.cond.tenCd = control.tenCd;
                }
            }
        }, function(response) {

        });
    }
    /**
     * Reset condition form.
     */
    $scope.resetCondForm = function() {
        if ($scope.cond != undefined) {
            $scope.cond.yyyy = $scope.getCurrentYear();
            $scope.cond.mm = $scope.getCurrentMonth();
            $scope.cond.kaisyaCd = AppConst.KAISYA_CODE;
            $scope.cond.jigyobuCd = "";
            $scope.cond.tenCd = "";
        }
    };

    /**
     * Check controls of the condForm area to Enable/Disable Search button.
     *
     * @return true:enable false:disable
     */
    $scope.canSearch = function() {
        return ($scope.mode != $scope.editMode && $scope.form.condForm.$valid);
    };

    /**
     * Transit to the other screen.
     */
    $scope.transit = function() {
        $scope.resultMode = 1;

        $scope.mode = $scope.editMode;
        if ($scope.isInsert) {
            Message.showMessage(MsgConst.MSG_KEY_INFO_BUTTON_SAVE);
        } else {
            Message.showMessage(MsgConst.MSG_KEY_INFO_BUTTON_DELETE_SAVE);
        }
    };

    /**
     * Check controls of the condForm area to Enable/Disable SearchEdit button.
     *
     * @return true:enable false:disable
     */
    $scope.canSearchEdit = function() {
        return ($scope.mode != $scope.editMode && $scope.form.condForm.$valid);
    };

    /**
     * Change read-only status of controls.
     *
     * @return true:read-only false:not read-only
     */
    $scope.disableResult = function() {
        return !$scope.disCon;
    }

    $scope.disableCond = function() {
        return $scope.disCon;
    }

    $scope.isDisable = function() {
        return $scope.isSaveFinal;
    }

    /**
     * Check if have changing data on screen to Enable/Disable Save button.
     *
     * @return true:enable false:disable
     */
    $scope.canSave = function() {
        return ($scope.editMode == $scope.mode && $scope.isValid);
    }

    /**
     * Check if have changing data on screen to Enable/Disable Save button.
     *
     * @return true:enable false:disable
     */
    $scope.canSaveFinal = function() {
        if ($scope.editMode == $scope.mode && $scope.isValid) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Check having new data to Enable/Disable Delete button.
     *
     * @return true:enable false:disable
     */
    $scope.canDelete = function() {
        return ($scope.editMode == $scope.mode);
    }

    /**
     * Validate all input controls of grid.
     *
     * @return true:valid false:invalid
     */
    $scope.$on('valid', function(data) {
        if ($scope.disCon) {
            return GridValidator($scope, $scope.disCon);
        }
        return $scope.isValid;
    });

    /**
     * Prepare saving data to update.
     *
     * @return saving data
     */
    $scope.prepareSaveList = function(data) {
        var modifiedData = {};
        modifiedData.yyyy = $scope.cond.yyyy;
        modifiedData.mm = $scope.cond.mm;
        modifiedData.kaisyaCd = $scope.cond.kaisyaCd;
        modifiedData.jigyobuCd = $scope.cond.jigyobuCd;
        modifiedData.tenCd = $scope.cond.tenCd;
        modifiedData.zidouKeisanKbn = $scope.result.zidouKeisanKbn;
        modifiedData.hdnVal = data;
        modifiedData.wTenCd = $scope.result.wTenCd;
        modifiedData.ysnHibetsuArea = $scope.queryResult.ysnHibetsuArea;
        modifiedData.ysnHibetsuSumAreaData = $scope.queryResult.ysnHibetsuSumAreaData;
        modifiedData.uriakeMokuhyouAreaData = $scope.queryResult.uriakeMokuhyouAreaData;
        modifiedData.mokuhyouGoukeiAreaData = $scope.queryResult.mokuhyouGoukeiAreaData;
        modifiedData.gekkanUriakeYsnAreaData = $scope.queryResult.gekkanUriakeYsnAreaData;
        modifiedData.grid6 = $scope.queryResult.grid6;
        modifiedData.insert = $scope.isInsert;
        modifiedData.extTenCopy = $scope.result.extTenCopy;

        return modifiedData;
    }

    var responseHandler = function(response) {
        if (response.status === HttpConst.CODE_NOT_FOUND) {
            Message.showMessage(MsgConst.MSG_KEY_DATA_NOT_EXIST);
        } else if (response.status === HttpConst.CODE_BAD_REQUEST) {
            // エラー処理
            Message.showMessageWithContent(response.data.errors[0].level, response.data.errors[0].message);
            Client.showErrorFromServer($scope.error, response.data.errors);
        } else {
            Message.showMessage(MsgConst.MSG_KEY_SYSTEM_ERROR);
        }
    }

    var successfulResponseHandler = function(control, response) {
        //success
        $scope.queryResult = control;
        $scope.grid1 = $scope.queryResult.ysnHibetsuArea;
        $scope.grid2 = $scope.queryResult.ysnHibetsuSumAreaData;
        $scope.grid3 = $scope.queryResult.uriakeMokuhyouAreaData;
        $scope.grid4 = $scope.queryResult.mokuhyouGoukeiAreaData;
        $scope.grid5 = $scope.queryResult.gekkanUriakeYsnAreaData;
        $scope.grid6 = $scope.queryResult.grid6;

        $scope.toggleCol($scope.grid6[0].colName);
        $scope.result.zidouKeisanKbn = $scope.queryResult.zidouKeisanKbn;

        $scope.errorsTable = [];
        $scope.error = null;

        if (!isEmpty(control.infoRes.errors)) {
            Message.showMessageWithContent(control.infoRes.errors[0].level, control.infoRes.errors[0].message);
            Client.showErrorFromServer($scope.error, control.infoRes.errors);
        }
    }

    /**
     * Process data when 昨対反映 button is clicked.
     *
     */
    $scope.btncopy = function() {
        var control = Urjp0010CalCopy.post($scope.prepareSaveList(1), function(response) {
            successfulResponseHandler(control, response);
        }, responseHandler);
    }

    /**
     * Process data when 差異計算 button is clicked.
     *
     */
    $scope.btncalc = function() {
        var control = Urjp0010CalCopy.post($scope.prepareSaveList(2), function(response) {
            successfulResponseHandler(control, response);
        }, responseHandler);
    }

    /**
     * Process data when 昨対参照 button is clicked.
     *
     */
    $scope.btnextcopy = function() {
        var control = Urjp0010CalCopy.post($scope.prepareSaveList(3), function(response) {
            successfulResponseHandler(control, response);
        }, responseHandler);
    }

    /**
     * Transit to the next screen when Save button is 1st clicked.
     */
    $scope.doSaveFirst = function() {
        var modifiedData = $scope.prepareSaveList(0);
        modifiedData.finalStage = false;
        var control = Urjp0010.update(modifiedData, function(response) {
            //success
            $scope.queryResult = control;
            $scope.grid1 = $scope.queryResult.ysnHibetsuArea;
            $scope.grid2 = $scope.queryResult.ysnHibetsuSumAreaData;
            $scope.grid3 = $scope.queryResult.uriakeMokuhyouAreaData;
            $scope.grid4 = $scope.queryResult.mokuhyouGoukeiAreaData;
            $scope.grid5 = $scope.queryResult.gekkanUriakeYsnAreaData;
            $scope.grid6 = $scope.queryResult.grid6;

            if ($scope.grid6[0].colName.length > 100) {
                Message.showMessage(MsgConst.MSG_KEY_SYSTEM_ERROR);
                return;
            }

            $scope.toggleCol($scope.grid6[0].colName);

            if (!isEmpty(control.infoRes.errors)) {
                Message.showMessageWithContent(control.infoRes.errors[0].level, control.infoRes.errors[0].message);
                Client.showErrorFromServer($scope.error, control.infoRes.errors);
                $scope.errorsTable = control.infoRes.gridErrors;
            } else {
                Message.showMessage(MsgConst.MSG_KEY_INFO_BUTTON_UPDATE);
                $scope.resultMode = 2;
                $scope.errorsTable = [];
                $scope.error = null;
            }

            $scope.setFocus();
        }, function(response) {
            responseHandler(response);
            $scope.setFocus();
        });
    }

    /**
     * Update data to database.
     */
    $scope.doSaveFinal = function() {
        if ($scope.isInsert) {
            var diag = Dialog.confirm(Message.getMessage(MsgConst.MSG_KEY_CONFIRM_BEFORE_INSERT));
            if (diag == null) {
                return;
            }
            diag.result.then(function() {
                var modifiedData = $scope.prepareSaveList(4);
                modifiedData.finalStage = true;
                var control = Urjp0010.update(modifiedData, function(response) {
                    //success
                    $scope.queryResult = control;
                    if (!isEmpty(control.infoRes.errors)) {
                        $scope.grid6 = $scope.queryResult.grid6;
                        $scope.toggleCol($scope.grid6[0].colName);

                        Message.showMessageWithContent(control.infoRes.errors[0].level, control.infoRes.errors[0].message);
                        Client.showErrorFromServer($scope.error, control.infoRes.errors);

                        $scope.errorsTable = control.infoRes.gridErrors;

                        $scope.setFocus();
                    } else {
                        var showResult = Urjp0010.query($scope.cond, function() {
                            $scope.queryResult = showResult;
                            $scope.grid1 = $scope.queryResult.ysnHibetsuArea;
                            $scope.grid2 = $scope.queryResult.ysnHibetsuSumAreaData;
                            $scope.grid3 = $scope.queryResult.uriakeMokuhyouAreaData;
                            $scope.grid4 = $scope.queryResult.mokuhyouGoukeiAreaData;
                            $scope.grid5 = $scope.queryResult.gekkanUriakeYsnAreaData;
                            $scope.grid6 = $scope.queryResult.grid6;
                            $scope.result.zidouKeisanKbn = $scope.queryResult.zidouKeisanKbn;
                            $scope.toggleCol($scope.grid6[0].colName);

                            $scope.mode = $scope.postEditMode;

                            $scope.disCon = false;
                            $scope.errorsTable = [];
                            $scope.error = null;

                            $scope.resultMode = 1;
                            $scope.isSaveFinal = true;

                            $scope.setFocus();
                        });

                        if ($scope.isInsert) {
                            Message.showMessage(MsgConst.MSG_KEY_INSERT_SUCCESS);
                        } else {
                            Message.showMessage(MsgConst.MSG_KEY_UPDATE_SUCCESS);
                        }
                    }
                }, function(response) {
                    if (response.status === HttpConst.CODE_NOT_FOUND) {
                        Message.showMessage(MsgConst.MSG_KEY_DATA_NOT_EXIST);

                    } else if (response.status === HttpConst.CODE_BAD_REQUEST) {
                        // エラー処理
                        Message.showMessageWithContent(response.data.errors[0].level, response.data.errors[0].message);
                        Client.showErrorFromServer($scope.error, response.data.errors);

                        $scope.resultMode = 2;

                        $scope.errorsTable = response.data.gridErrors;
                    } else {
                        Message.showMessage(MsgConst.MSG_KEY_SYSTEM_ERROR);
                    }
                    $scope.setFocus();
                });
            }, function() {
                $scope.setFocus();
            });
        } else {
            var diag = Dialog.confirm(Message.getMessage(MsgConst.MSG_KEY_CONFIRM_BEFORE_UPDATE));
            if (diag == null) {
                return;
            }
            diag.result.then(function() {
                var modifiedData = $scope.prepareSaveList(4);
                modifiedData.finalStage = true;
                var control = Urjp0010.update(modifiedData, function(response) {
                    //success
                    $scope.queryResult = control;
                    if (!isEmpty(control.infoRes.errors)) {
                        $scope.grid6 = $scope.queryResult.grid6;
                        $scope.toggleCol($scope.grid6[0].colName);

                        Message.showMessageWithContent(control.infoRes.errors[0].level, control.infoRes.errors[0].message);
                        Client.showErrorFromServer($scope.error, control.infoRes.errors);

                        $scope.errorsTable = control.infoRes.gridErrors;
                        $scope.resultMode = 2;
                    } else {
                        var showResult = Urjp0010.query($scope.cond, function() {
                            $scope.queryResult = showResult;
                            $scope.grid1 = $scope.queryResult.ysnHibetsuArea;
                            $scope.grid2 = $scope.queryResult.ysnHibetsuSumAreaData;
                            $scope.grid3 = $scope.queryResult.uriakeMokuhyouAreaData;
                            $scope.grid4 = $scope.queryResult.mokuhyouGoukeiAreaData;
                            $scope.grid5 = $scope.queryResult.gekkanUriakeYsnAreaData;
                            $scope.grid6 = $scope.queryResult.grid6;
                            $scope.result.zidouKeisanKbn = $scope.queryResult.zidouKeisanKbn;
                            $scope.toggleCol($scope.grid6[0].colName);

                            $scope.mode = $scope.postEditMode;

                            $scope.disCon = false;
                            $scope.errorsTable = [];
                            $scope.error = null;

                            $scope.resultMode = 1;
                            $scope.isSaveFinal = true;

                            $scope.setFocus();
                        });
                        if ($scope.isInsert) {
                            Message.showMessage(MsgConst.MSG_KEY_INSERT_SUCCESS);
                        } else {
                            Message.showMessage(MsgConst.MSG_KEY_UPDATE_SUCCESS);
                        }
                    }
                    $scope.setFocus();
                }, function(response) {
                    if (response.status === HttpConst.CODE_NOT_FOUND) {
                        Message.showMessage(MsgConst.MSG_KEY_DATA_NOT_EXIST);

                    } else if (response.status === HttpConst.CODE_BAD_REQUEST) {
                        // エラー処理
                        Message.showMessageWithContent(response.data.errors[0].level, response.data.errors[0].message);
                        Client.showErrorFromServer($scope.error, response.data.errors);
                        $scope.resultMode = 2;
                        $scope.errorsTable = response.data.gridErrors;
                    } else {
                        Message.showMessage(MsgConst.MSG_KEY_SYSTEM_ERROR);
                    }
                    $scope.setFocus();
                });
            }, function() {
                $scope.setFocus();
            });
        }
    }

    /**
     * Search/Edit data from database.
     */
    $scope.doSearchEdit = function() {
        $scope.error = null;
        $scope.info = null;
        $scope.errorsTable = [];

        $scope.isInsert = false;
        $scope.grid1 = [];
        $scope.grid2 = [];
        $scope.grid3 = [];
        $scope.grid4 = [];
        $scope.grid5 = [];
        $scope.grid6 = [];
        $scope.columnDefines = [];
        var showResult = Urjp0010.query($scope.cond, function() {
            $scope.queryResult = showResult;
            $scope.grid1 = $scope.queryResult.ysnHibetsuArea;
            $scope.grid2 = $scope.queryResult.ysnHibetsuSumAreaData;
            $scope.grid3 = $scope.queryResult.uriakeMokuhyouAreaData;
            $scope.grid4 = $scope.queryResult.mokuhyouGoukeiAreaData;
            $scope.grid5 = $scope.queryResult.gekkanUriakeYsnAreaData;
            $scope.grid6 = $scope.queryResult.grid6;
            $scope.result.zidouKeisanKbn = $scope.queryResult.zidouKeisanKbn;
            $scope.result.extTenCopy = showResult.extTenCopy;

            if ($scope.grid2.length > 0 && $scope.grid2[0].sYsnUriKin == "0" && $scope.grid2[0].sYsnKyaksu == "0") {
                var diag = Dialog.confirm(Message.getMessage(MsgConst.MSG_KEY_CONFIRM_NEW_REGISTERED));
                if (diag == null) {
                    return;
                }
                diag.result.then(function() {
                    $scope.mode = $scope.editMode;
                    $scope.isInsert = true;
                    $scope.disCon = true;

                    $scope.resultMode = 1;

                    $scope.isSaveFinal = false;
                    Message.showMessage(MsgConst.MSG_KEY_INFO_BUTTON_SAVE);
                    $scope.setFocus();

                }, function() {
                    $scope.mode = $scope.initMode;
                    $scope.setFocus();
                });
            } else {
                $scope.mode = $scope.editMode;
                $scope.isInsert = false;

                $scope.disCon = true;

                $scope.resultMode = 1;

                $scope.isSaveFinal = false;
                Message.showMessage(MsgConst.MSG_KEY_INFO_BUTTON_DELETE_SAVE);
                $scope.setFocus();
            }
        }, function(response) {
            if (response.status === HttpConst.CODE_NOT_FOUND) {
                var diag = Dialog.confirm(Message.getMessage(MsgConst.MSG_KEY_CONFIRM_NEW_REGISTERED));
                if (diag == null) {
                    return;
                }
                diag.result.then(function() {
                    $scope.isInsert = true;
                    $scope.mode = $scope.editMode;
                    $scope.isSaveFinal = false;
                    Message.showMessage(MsgConst.MSG_KEY_INFO_BUTTON_SAVE);
                    $scope.setFocus();
                }, function() {
                    Message.showMessage(MsgConst.MSG_KEY_COMMON_INFO_MESSAGE);
                    $scope.setFocus();
                });

            } else if (response.status === HttpConst.CODE_BAD_REQUEST) {
                Message.showMessageWithContent(response.data.errors[0].level, response.data.errors[0].message);
                Client.showErrorFromServer($scope.error, response.data.errors);
            } else {
                Message.showMessage(MsgConst.MSG_KEY_SYSTEM_ERROR);
            }
            $scope.setFocus();
        });
    }

    /**
     * Clear input data of controls.
     */
    $scope.doClear = function() {
        var clearDialog = Dialog.confirm(Message.getMessage(MsgConst.MSG_KEY_CONFIRM_BEFORE_CLEAR));
        if (clearDialog == null) {
            return;
        }
        clearDialog.result.then(function() {
            if ($scope.mode == $scope.initMode) {
                $scope.resetCondForm();
            }

            $scope.form.$setPristine();

            $scope.resultMode = 1;

            $scope.cloneQueryResult = [];
            $scope.queryResult = [];

            $scope.info = null;
            $scope.errorsTable = [];
            $scope.error = Client.clearErrors();

            $scope.isInsert = false;
            Message.showMessage(MsgConst.MSG_KEY_COMMON_INFO_MESSAGE_F9);

            $scope.mode = $scope.initMode;

            $scope.disCon = false;
            $scope.isSaveFinal = false;

            $scope.setFocus();
        }, function() {
            $scope.setFocus();
        });
    }

    /**
     * Delete data from database.
     */
    $scope.doDelete = function() {
        var diag = Dialog.confirm(Message.getMessage(MsgConst.MSG_KEY_CONFIRM_BEFORE_DELETE));
        if (diag == null) {
            return;
        }
        diag.result.then(function() {
            Urjp0010.update($scope.prepareSaveList(5), function() {
                $scope.mode = $scope.postEditMode;

                $scope.isSaveFinal = true;

                $scope.disCon = false;
                $scope.errorsTable = [];
                $scope.error = null;

                $scope.resultMode = 1;

                Message.showMessage(MsgConst.MSG_KEY_DELETE_SUCCESS);
                $scope.setFocus();
            }, function(response) {
                if (response.status === HttpConst.CODE_BAD_REQUEST) {
                    Message.showMessageWithContent(response.data.errors[0].level, response.data.errors[0].message);
                    Client.showErrorFromServer($scope.error, response.data.errors);
                } else {
                    Message.showMessage(MsgConst.MSG_KEY_SYSTEM_ERROR);
                }
                $scope.setFocus();
            })
        }, function() {
            $scope.setFocus();
        })
    }

    /**
     * Define rowTemplate.
     */
    var rowTemplate = '<div ng-repeat="col in renderedColumns" ng-class="col.colIndex()" class="ngCell {{col.cellClass}}">' + '<div class="ngVerticalBar" ng-style="{height: rowHeight}" ng-class="{ ngVerticalBarVisible: !$last }">&nbsp;</div>' + '<div ng-cell-custom></div></div>';

    /**
     * Define paging options.
     */
    $scope.pagingOptionsURJP0010YsnHibetsuArea = {
        pageSizes: 31,
        pagesize: 31,
        currentPage: 1
    };

    /**
     * Initialize the grid options.
     */
    $scope.gridOptionsURJP0010YsnHibetsuArea = {
        data: 'grid1',
        enablePaging: false,
        showFooter: false,
        footerRowHeight: 0,
        headerRowHeight: 19,
        rowHeight: 20,
        enableRowSelection: false,
        enableSorting: false,
        enableCellSelection: true,
        rowTemplate: rowTemplate,
        enableColumnResize: false,
        columnDefs: [{
            field: 'calDate',
            displayName: '日付',
            width: 30,
            cellClass: "cell text-center text-middle",
            cellTemplate: '<div>{{row.entity.calDate}}</div>'
        }, {
            field: 'weekDay',
            displayName: '曜日',
            width: 30,
            cellClass: "cell text-center text-middle",
            cellTemplate: '<div>{{row.entity.weekDay}}</div>'
        }, {
            field: 'ysnUriKin',
            displayName: '目標売上高',
            width: 90,
            cellClass: "cell text-center",
            cellTemplate: '<cc-number-input cc-label="" cc-partition="" id="ysnUriKin" name="ysnUriKin" ng-model="COL_FIELD" cc-required="true" cc-readonly="isDisable()" cc-min="0" cc-max="999999999" cc-width="88"></cc-number-input>'
        }, {
            field: 'uriKin',
            displayName: '昨年同曜売上',
            width: 80,
            cellClass: "text-right",
            cellTemplate: '<div>{{row.entity.uriKin}}</div>'
        }, {
            field: 'kosei',
            displayName: '構成比',
            width: 64,
            cellClass: "text-right",
            cellTemplate: '<div>{{row.entity.kosei | ccNumberFilter:2}}</div>'
        }, {
            field: 'ysnKyaksu',
            displayName: '目標客数',
            width: 80,
            cellClass: "cell text-center",
            cellTemplate: '<cc-number-input cc-label="" cc-partition="" id="ysnKyaksu" name="ysnKyaksu" ng-model="COL_FIELD" cc-required="true" cc-readonly="isDisable()" cc-min="0" cc-max="999999" ></cc-number-input>'
        }, {
            field: 'tenKyaku',
            displayName: '昨年同曜客数',
            cellClass: "text-right",
            cellTemplate: '<div>{{row.entity.tenKyaku | ccNumberFilter:0}}</div>'
        }]
    };

    /**
     * Define paging options.
     */
    $scope.pagingOptionsURJP0010YsnHibetsuSumArea = {
        pageSizes: 2,
        pagesize: 2,
        currentPage: 1
    };

    /**
     * Initialize the grid options.
     */
    $scope.gridOptionsURJP0010YsnHibetsuSumArea = {
        data: 'grid2',
        enablePaging: false,
        showFooter: false,
        footerRowHeight: 0,
        headerRowHeight: 0,
        rowHeight: 22,
        enableRowSelection: false,
        enableSorting: false,
        enableCellSelection: true,
        rowTemplate: rowTemplate,
        enableColumnResize: false,
        columnDefs: [{
            field: 'gouKei2',
            displayName: '合　　計',
            width: 60,
            cellClass: "ngHeaderText",
            cellTemplate: ''
        }, {
            field: 'ysnUriKin',
            displayName: '目標売上高合計',
            width: 90,
            cellClass: "text-right",
            cellTemplate: '<div class="ngCellText" ng-class="{errorCell18: (errorsTable.length > 0 && errorsTable[row.rowIndex].ysnUriKin == true)}"><span ng-cell-text>{{row.getProperty(col.field) | ccNumberFilter:0}}</span></div>'
        }, {
            field: 'uriKin',
            displayName: '昨年同曜売上合計',
            width: 80,
            cellClass: "text-right",
            cellTemplate: '<div class="ngCellText" >{{row.getProperty(col.field) | ccNumberFilter:0}}</span></div>'
        }, {
            field: 'kosei',
            displayName: '構成比合計',
            width: 64,
            cellClass: "text-right",
            cellTemplate: '<div class="ngCellText" >{{row.getProperty(col.field) | ccNumberFilter:2}}</span></div>'
        }, {
            field: 'ysnKyaksu',
            displayName: '目標客数合計',
            width: 80,
            cellClass: "text-right",
            cellTemplate: '<div class="ngCellText" ng-class="{errorCell18: (errorsTable.length > 0 && errorsTable[row.rowIndex].ysnKyaksu == true)}"><span ng-cell-text>{{row.getProperty(col.field) | ccNumberFilter:0}}</span></div>'
        }, {
            field: 'tenKyaku',
            displayName: '昨年同曜客数合計',
            cellClass: "text-right",
            cellTemplate: '<div class="ngCellText" ><span ng-cell-text>{{row.getProperty(col.field) | ccNumberFilter:0}}&nbsp;&nbsp;&nbsp;</span></div>'
        }]
    };

    /**
     * Define paging options.
     */
    $scope.pagingOptionsURJP0010GekkanUriakeYsnArea = {
        pageSizes: 2,
        pagesize: 2,
        currentPage: 1
    };

    /**
     * Initialize the grid options.
     */
    $scope.gridOptionsURJP0010GekkanUriakeYsnArea = {
        data: 'grid5',
        enablePaging: false,
        showFooter: false,
        footerRowHeight: 0,
        headerRowHeight: 0,
        rowHeight: 20,
        enableRowSelection: false,
        enableSorting: false,
        enableCellSelection: true,
        rowTemplate: rowTemplate,
        enableColumnResize: false,
        columnDefs: [{
            field: 'yosKinMon',
            displayName: '月間売上予算(千円)',
            cellClass: "cs-headerCell-top",
            cellTemplate: '<div>{{row.entity.yosKinMon}}</div>'
        }, {
            field: 'yosKyakuMon',
            displayName: '月間客数予算(人)',
            width: 110,
            cellClass: "cell text-center",
            cellTemplate: '<cc-number-input cc-label="" cc-partition="" id="yosKyakuMon_{{row.rowIndex}}" name="yosKyakuMon_{{row.rowIndex}}" ng-model="COL_FIELD" cc-required="true" cc-readonly="isDisable()" cc-min="0" cc-max="9999999999" cc-width ="110"cc-error="error"  cc-focus="getFocusResult(row.rowIndex)"/>></cc-number-input>'
        }, {
            field: 'uriSakuTai',
            displayName: '昨対',
            width: 32,
            cellClass: "cs-headerCell-top",
            cellTemplate: '<div>{{row.entity.uriSakuTai}}</div>'
        }, {
            field: 'kyakuSakuTai',
            displayName: '昨対',
            width: 66,
            cellClass: "text-right",
            cellTemplate: '<div>{{row.entity.kyakuSakuTai | ccNumberFilter:1}}</div>'
        }, {
            field: 'uriSai',
            displayName: '月予差異',
            width: 55,
            cellClass: "cs-headerCell-top",
            cellTemplate: '<div>{{row.entity.uriSai}}</div>'
        }, {
            field: 'kyakuSai',
            displayName: '月予差異',
            width: 65,
            cellClass: "text-right",
            cellTemplate: '<div>{{row.entity.kyakuSai | ccNumberFilter:0}}</div>'
        }]
    };

    /**
     * Define paging options.
     */
    $scope.pagingOptionsURJP0010BmnUriakeMokuhyouArea = {
        pageSizes: 999,
        pagesize: 999,
        currentPage: 1
    };

    /**
     * Initialize the grid options.
     */
    $scope.gridOptionsURJP0010BmnUriakeMokuhyouArea = {
        data: 'grid3',
        enablePaging: false,
        showFooter: false,
        footerRowHeight: 0,
        headerRowHeight: 19,
        rowHeight: 20,
        enableRowSelection: false,
        enableSorting: false,
        enableCellSelection: true,
        rowTemplate: rowTemplate,
        enableColumnResize: false,
        columnDefs: [{
            field: 'bmnCdNm',
            displayName: '部門',
            width: 250,
            cellClass: "text-left",
            cellTemplate: '<div>{{row.entity.bmnCdNm}}</div>'
        }, {
            field: 'ysnUriKin',
            displayName: '月間売上目標',
            width: 118,
            cellClass: "text-right",
            cellTemplate: '<div>{{row.entity.ysnUriKin}}</div>'
        }, {
            field: 'kosei',
            displayName: '構成比',
            cellClass: "text-right",
            cellTemplate: '<div>{{row.entity.kosei | ccNumberFilter:1}}</div>'
        }]
    };

    /**
     * Define paging options.
     */
    $scope.pagingOptionsURJP0010BmnMokuhyouGoukeiArea = {
        pageSizes: 1,
        pagesize: 1,
        currentPage: 1
    };

    /**
     * Initialize the grid options.
     */
    $scope.gridOptionsURJP0010BmnMokuhyouGoukeiArea = {
        data: 'grid4',
        enablePaging: false,
        showFooter: false,
        footerRowHeight: 0,
        headerRowHeight: 0,
        rowHeight: 20,
        enableRowSelection: false,
        enableSorting: false,
        enableCellSelection: true,
        rowTemplate: rowTemplate,
        enableColumnResize: true,
        columnDefs: [{
            field: 'gouKei4',
            displayName: '合　　計',
            width: 250,
            cellTemplate: '<div class="ngHeaderText">合　　計</div>'
        }, {
            field: 'ysnUriKin',
            displayName: '月間売上目標合計',
            width: 118,
            cellClass: "text-right",
            cellTemplate: '<div>{{row.entity.ysnUriKin}}</div>'
        }, {
            field: 'kosei',
            displayName: '構成比合計',
            cellClass: "text-right",
            cellTemplate: '<div>{{row.entity.kosei | ccNumberFilter:1}}</div>'
        }]
    };

    /**
     * Transit to gridOptionsURJP0010GouKeiYsnBmnArea grid.
     */
    $scope.toggleCol = function(data) {
        $scope.grid = $scope.gridOptionsURJP0010GouKeiYsnBmnArea;
        $scope.len = 0;
        if (!isEmpty($scope.grid)) {
            if (data.length < $scope.grid.columnDefs.length) {
                $scope.len = $scope.grid.columnDefs.length - data.length;
                for (var i = data.length; i < $scope.grid.columnDefs.length; i++) {
                    $scope.grid.columnDefs[i].visible = false;
                }
            }
            if (($scope.gsindex + $scope.GRD6_ROW) > $scope.grid1.length) {
                $scope.grd6Row = $scope.GRD6_HEAD_ROW + $scope.grid1.length - $scope.gsindex;
            } else {
                $scope.grd6Row = $scope.GRD6_HEAD_ROW + $scope.grid1.length;
            }

            $scope.pagingResult = $scope.grid6.slice(0, (($scope.grd6Row - $scope.GRD6_HEAD_ROW) + 4));
            if (data.length > 0) {
                var len = data.length - 2;
                for (var j = 1; j < data.length; j++) {
                    var name = "field" + j;
                    if (j == 1) {
                        $scope.grid.columnDefs[j].cellTemplate = '<div ng-if="row.rowIndex > 3 && ' + j + ' != ' + len + '"><cc-number-input cc-label="" cc-partition="" id="field1 ' + j + '__{{row.rowIndex}}" name="field1' + j + '_{{row.rowIndex}}" ng-model="COL_FIELD" cc-required="true" cc-min="0" cc-max="9999999" cc-error="error" cc-focus="getFocusResult(row.rowIndex)"></cc-number-input></div><div ng-if="row.rowIndex <= 3"><div  ng-class="{errorCell18: (errorsTable.length > 0 && errorsTable[row.rowIndex].' + name + ' == true), urjp0010Cell:{{row.rowIndex}} == 3}"><span >{{row.getProperty(col.field)}}</span></div></div><div ng-if="' + j + ' === ' + len + '"><div  ng-class="{errorCell18: (errorsTable.length > 0 && errorsTable[row.rowIndex].' + name + ' == true), urjp0010Cell:{{row.rowIndex}} == 3}"><span >{{row.getProperty(col.field)}}</span></div></div>';
                    } else {
                        $scope.grid.columnDefs[j].cellTemplate = '<div ng-if="row.rowIndex > 3 && ' + j + ' != ' + len + '"><cc-number-input cc-label="" cc-partition="" id="field1 ' + j + '__{{row.rowIndex}}" name="field1' + j + '_{{row.rowIndex}}" ng-model="COL_FIELD" cc-required="true" cc-min="0" cc-max="9999999" cc-error="error"></cc-number-input></div><div ng-if="row.rowIndex <= 3"><div  ng-class="{errorCell18: (errorsTable.length > 0 && errorsTable[row.rowIndex].' + name + ' == true), urjp0010Cell:{{row.rowIndex}} == 3}"><span >{{row.getProperty(col.field)}}</span></div></div><div ng-if="' + j + ' === ' + len + '"><div  ng-class="{errorCell18: (errorsTable.length > 0 && errorsTable[row.rowIndex].' + name + ' == true), urjp0010Cell:{{row.rowIndex}} == 3}"><span >{{row.getProperty(col.field)}}</span></div></div>';
                    }
                }
            }
            if (!$scope.$$phase) {
                $scope.$apply();
            }
        }
    }

    /**
     * Define columnDefs.
     */
    $scope.myDefs = [{
        field: 'field0',
        displayName: '',
        width: 80,
        cellClass: "hajp0010-text-padding cell text-center text-middle urjp0010Cell"
    }, {
        field: 'field1',
        displayName: 'field1',
        width: 80,
        cellClass: "cell  text-right"
    }, {
        field: 'field2',
        displayName: 'field2',
        width: 80,
        cellClass: "cell text-right"
    }, {
        field: 'field3',
        displayName: 'field3',
        width: 80,
        cellClass: "cell text-right"
    }, {
        field: 'field4',
        displayName: 'field4',
        width: 80,
        cellClass: "cell  text-right"
    }, {
        field: 'field5',
        displayName: 'field5',
        width: 80,
        cellClass: "cell  text-right"
    }, {
        field: 'field6',
        displayName: 'field6',
        width: 80,
        cellClass: "cell  text-right"
    }, {
        field: 'field7',
        displayName: 'field7',
        width: 80,
        cellClass: "cell  text-right"
    }, {
        field: 'field8',
        displayName: 'field8',
        width: 80,
        cellClass: "cell  text-right"
    }, {
        field: 'field9',
        displayName: 'field9',
        width: 80,
        cellClass: "cell  text-right"
    }, {
        field: 'field10',
        displayName: 'field10',
        width: 80,
        cellClass: "cell  text-right"
    }, {
        field: 'field11',
        displayName: 'field11',
        width: 80,
        cellClass: "cell  text-right"
    }, {
        field: 'field12',
        displayName: 'field12',
        width: 80,
        cellClass: "cell  text-right"
    }, {
        field: 'field13',
        displayName: 'field13',
        width: 80,
        cellClass: "cell  text-right"
    }, {
        field: 'field14',
        displayName: 'field14',
        width: 80,
        cellClass: "cell  text-right"
    }, {
        field: 'field15',
        displayName: 'field15',
        width: 80,
        cellClass: "cell  text-right"
    }, {
        field: 'field16',
        displayName: 'field16',
        width: 80,
        cellClass: "cell  text-right"
    }, {
        field: 'field17',
        displayName: 'field17',
        width: 80,
        cellClass: "cell  text-right"
    }, {
        field: 'field18',
        displayName: 'field18',
        width: 80,
        cellClass: "cell  text-right"
    }, {
        field: 'field19',
        displayName: 'field19',
        width: 80,
        cellClass: "cell  text-right"
    }, {
        field: 'field20',
        displayName: 'field20',
        width: 80,
        cellClass: "cell  text-right"
    }, {
        field: 'field21',
        displayName: 'field21',
        width: 80,
        cellClass: "cell  text-right"
    }, {
        field: 'field22',
        displayName: 'field22',
        width: 80,
        cellClass: "cell  text-right"
    }, {
        field: 'field23',
        displayName: 'field23',
        width: 80,
        cellClass: "cell  text-right"
    }, {
        field: 'field24',
        displayName: 'field24',
        width: 80,
        cellClass: "cell  text-right"
    }, {
        field: 'field25',
        displayName: 'field25',
        width: 80,
        cellClass: "cell  text-right"
    }, {
        field: 'field26',
        displayName: 'field1',
        width: 80,
        cellClass: "cell  text-right"
    }, {
        field: 'field27',
        displayName: 'field2',
        width: 80,
        cellClass: "cell  text-right"
    }, {
        field: 'field28',
        displayName: 'field3',
        width: 80,
        cellClass: "cell  text-right"
    }, {
        field: 'field29',
        displayName: 'field4',
        width: 80,
        cellClass: "cell  text-right"
    }, {
        field: 'field30',
        displayName: 'field5',
        width: 80,
        cellClass: "cell  text-right"
    }, {
        field: 'field31',
        displayName: 'field6',
        width: 80,
        cellClass: "cell  text-right"
    }, {
        field: 'field32',
        displayName: 'field7',
        width: 80,
        cellClass: "cell  text-right"
    }, {
        field: 'field33',
        displayName: 'field8',
        width: 80,
        cellClass: "cell  text-right"
    }, {
        field: 'field34',
        displayName: 'field9',
        width: 80,
        cellClass: "cell  text-right"
    }, {
        field: 'field35',
        displayName: 'field10',
        width: 80,
        cellClass: "cell  text-right"
    }, {
        field: 'field36',
        displayName: 'field11',
        width: 80,
        cellClass: "cell  text-right"
    }, {
        field: 'field37',
        displayName: 'field12',
        width: 80,
        cellClass: "cell  text-right"
    }, {
        field: 'field38',
        displayName: 'field13',
        width: 80,
        cellClass: "cell  text-right"
    }, {
        field: 'field39',
        displayName: 'field14',
        width: 80,
        cellClass: "cell  text-right"
    }, {
        field: 'field40',
        displayName: 'field15',
        width: 80,
        cellClass: "cell  text-right"
    }, {
        field: 'field41',
        displayName: 'field16',
        width: 80,
        cellClass: "cell  text-right"
    }, {
        field: 'field42',
        displayName: 'field17',
        width: 80,
        cellClass: "cell  text-right"
    }, {
        field: 'field43',
        displayName: 'field18',
        width: 80,
        cellClass: "cell  text-right"
    }, {
        field: 'field44',
        displayName: 'field19',
        width: 80,
        cellClass: "cell  text-right"
    }, {
        field: 'field45',
        displayName: 'field20',
        width: 80,
        cellClass: "cell  text-right"
    }, {
        field: 'field46',
        displayName: 'field21',
        width: 80,
        cellClass: "cell  text-right"
    }, {
        field: 'field47',
        displayName: 'field22',
        width: 80,
        cellClass: "cell  text-right"
    }, {
        field: 'field48',
        displayName: 'field23',
        width: 80,
        cellClass: "cell  text-right"
    }, {
        field: 'field49',
        displayName: 'field24',
        width: 80,
        cellClass: "cell  text-right"
    }, {
        field: 'field50',
        displayName: 'field25',
        width: 80,
        cellClass: "cell  text-right"
    }, {
        field: 'field51',
        displayName: 'field51',
        width: 80,
        cellClass: "cell  text-right"
    }, {
        field: 'field52',
        displayName: 'field52',
        width: 80,
        cellClass: "cell  text-right"
    }, {
        field: 'field53',
        displayName: 'field53',
        width: 80,
        cellClass: "cell  text-right"
    }, {
        field: 'field54',
        displayName: 'field54',
        width: 80,
        cellClass: "cell  text-right"
    }, {
        field: 'field55',
        displayName: 'field55',
        width: 80,
        cellClass: "cell t text-right"
    }, {
        field: 'field56',
        displayName: 'field56',
        width: 80,
        cellClass: "cell  text-right"
    }, {
        field: 'field57',
        displayName: 'field57',
        width: 80,
        cellClass: "cell  text-right"
    }, {
        field: 'field58',
        displayName: 'field58',
        width: 80,
        cellClass: "cell  text-right"
    }, {
        field: 'field59',
        displayName: 'field59',
        width: 80,
        cellClass: "cell  text-right"
    }, {
        field: 'field60',
        displayName: 'field60',
        width: 80,
        cellClass: "cell  text-right"
    }, {
        field: 'field61',
        displayName: 'field61',
        width: 80,
        cellClass: "cell  text-right"
    }, {
        field: 'field62',
        displayName: 'field62',
        width: 80,
        cellClass: "cell  text-right"
    }, {
        field: 'field63',
        displayName: 'field63',
        width: 80,
        cellClass: "cell  text-right"
    }, {
        field: 'field64',
        displayName: 'field64',
        width: 80,
        cellClass: "cell  text-right"
    }, {
        field: 'field65',
        displayName: 'field65',
        width: 80,
        cellClass: "cell  text-right"
    }, {
        field: 'field66',
        displayName: 'field66',
        width: 80,
        cellClass: "cell  text-right"
    }, {
        field: 'field67',
        displayName: 'field67',
        width: 80,
        cellClass: "cell  text-right"
    }, {
        field: 'field68',
        displayName: 'field68',
        width: 80,
        cellClass: "cell  text-right"
    }, {
        field: 'field69',
        displayName: 'field69',
        width: 80,
        cellClass: "cell  text-right"
    }, {
        field: 'field70',
        displayName: 'field70',
        width: 80,
        cellClass: "cell  text-right"
    }, {
        field: 'field71',
        displayName: 'field71',
        width: 80,
        cellClass: "cell  text-right"
    }, {
        field: 'field72',
        displayName: 'field72',
        width: 80,
        cellClass: "cell  text-right"
    }, {
        field: 'field73',
        displayName: 'field73',
        width: 80,
        cellClass: "cell  text-right"
    }, {
        field: 'field74',
        displayName: 'field74',
        width: 80,
        cellClass: "cell  text-right"
    }, {
        field: 'field75',
        displayName: 'field75',
        width: 80,
        cellClass: "cell  text-right"
    }, {
        field: 'field76',
        displayName: 'field76',
        width: 80,
        cellClass: "cell  text-right"
    }, {
        field: 'field77',
        displayName: 'field77',
        width: 80,
        cellClass: "cell  text-right"
    }, {
        field: 'field78',
        displayName: 'field78',
        width: 80,
        cellClass: "cell  text-right"
    }, {
        field: 'field79',
        displayName: 'field79',
        width: 80,
        cellClass: "cell  text-right"
    }, {
        field: 'field80',
        displayName: 'field80',
        width: 80,
        cellClass: "cell  text-right"
    }, {
        field: 'field81',
        displayName: 'field81',
        width: 80,
        cellClass: "cell  text-right"
    }, {
        field: 'field82',
        displayName: 'field82',
        width: 80,
        cellClass: "cell  text-right"
    }, {
        field: 'field83',
        displayName: 'field83',
        width: 80,
        cellClass: "cell  text-right"
    }, {
        field: 'field84',
        displayName: 'field84',
        width: 80,
        cellClass: "cell  text-right"
    }, {
        field: 'field85',
        displayName: 'field85',
        width: 80,
        cellClass: "cell  text-right"
    }, {
        field: 'field86',
        displayName: 'field86',
        width: 80,
        cellClass: "cell  text-right"
    }, {
        field: 'field87',
        displayName: 'field87',
        width: 80,
        cellClass: "cell  text-right"
    }, {
        field: 'field88',
        displayName: 'field88',
        width: 80,
        cellClass: "cell  text-right"
    }, {
        field: 'field89',
        displayName: 'field89',
        width: 80,
        cellClass: "cell  text-right"
    }, {
        field: 'field90',
        displayName: 'field90',
        width: 80,
        cellClass: "cell  text-right"
    }, {
        field: 'field91',
        displayName: 'field91',
        width: 80,
        cellClass: "cell  text-right"
    }, {
        field: 'field92',
        displayName: 'field92',
        width: 80,
        cellClass: "cell  text-right"
    }, {
        field: 'field93',
        displayName: 'field93',
        width: 80,
        cellClass: "cell  text-right"
    }, {
        field: 'field94',
        displayName: 'field94',
        width: 80,
        cellClass: "cell  text-right"
    }, {
        field: 'field95',
        displayName: 'field95',
        width: 80,
        cellClass: "cell  text-right"
    }, {
        field: 'field96',
        displayName: 'field96',
        width: 80,
        cellClass: "cell  text-right"
    }, {
        field: 'field97',
        displayName: 'field97',
        width: 80,
        cellClass: "cell  text-right"
    }, {
        field: 'field98',
        displayName: 'field98',
        width: 80,
        cellClass: "cell  text-right"
    }, {
        field: 'field99',
        displayName: 'field99',
        width: 80,
        cellClass: "cell  text-right"
    }, {
        field: 'field100',
        displayName: 'field100',
        width: 80,
        cellClass: "cell  text-right"
    }];

    /**
     * Initialize the grid options.
     */
    $scope.gridOptionsURJP0010GouKeiYsnBmnArea = {
        data: 'pagingResult',
        enablePaging: false,
        showFooter: false,
        footerRowHeight: 0,
        headerRowHeight: 0,
        rowHeight: 20,
        enableRowSelection: false,
        enableSorting: false,
        enableCellSelection: true,
        rowTemplate: rowTemplate,
        enableColumnResize: true,
        columnDefs: $scope.myDefs
    };
});