///////////////////////////////////////////////////////////////////////
// Copyright(C) 2015 NEC Solution Innovators, Ltd. All Rights Reserved.
///////////////////////////////////////////////////////////////////////

/* ====================================================================
 * 機能名称 : 移動伝票入力
 * 改版履歴
 * Rev. 改版年月日   改版者名       内容
 * 1.0   2014.04.05   Hungtb      新規作成
 * =================================================================== */
var app = angular.module('sijp0040', ['sijp0040Services', 'ui', 'ui.select2', 'directives', 'ngGrid']);
app.controller("SIJP0040Ctrl", function($scope, ComInfo, SIJP0040Resource, SIJP0040ResourceInit, Message, MsgConst, HttpConst,
    $routeParams, $location, $filter, $timeout, $rootScope, $interval, Dialog, DialogInfo, SIJP0040GetTanka,
    AppConst, FocusConst, GetNgModelController, Client) {
    Message.showMessage(MsgConst.MSG_KEY_COMMON_INFO_MESSAGE);

    $scope.cond = {
        kaisyaCd: AppConst.KAISYA_CODE,
        subKaisyaCd: AppConst.KAISYA_CODE
    };
    $scope.result = {};
    $scope.isEditable = false;
    $scope.hasResult = false;
    $scope.isNewData = false;
    $scope.result.dataArea = [];
    $scope.sysdate = $scope.getCurrentDate();
    $scope.hakkoDay = $scope.getCurrentDate();

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
    $scope.getFocusResult = function(index) {
        if (index == 0) {
            return $scope.focusResult;
        } else {
            return 0;
        }
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
     * Auto trace to set value of syukaYmd.
     */
    $scope.$watch('cond.syukaYmd', function(value) {
        if (!isEmpty($scope.cond.syukaYmd)) {
            $scope.hakkoDay = angular.copy($scope.cond.syukaYmd);
        } else {
            $scope.hakkoDay = $scope.getCurrentDate();
        }
    });

    /**
     * Auto trace to set value of tenCd.
     */
    $scope.changeDisable = function() {
        return $scope.isEditable;
    }

    /**
     * Init screen with params in url.
     */
    $scope.initCond = function() {
        var control = SIJP0040ResourceInit.initCond($routeParams, function() {
            if (control != undefined) {
                $scope.cond.dpyKbn = control.dpyKbn;
                $scope.cond.kaisyaCd = control.kaisyaCd;
                $scope.cond.subKaisyaCd = control.subKaisyaCd;

                if (!isEmpty(control.dpyNo)) {
                    $scope.cond.dpyNo = control.dpyNo;
                }
                if (!isEmpty(control.syukaYmd)) {
                    $scope.cond.syukaYmd = control.syukaYmd;
                }
                if (!isEmpty(control.jigyobuCd)) {
                    $scope.cond.jigyobuCd = control.jigyobuCd;
                }
                if (!isEmpty(control.tenCd)) {
                    $scope.cond.tenCd = control.tenCd;
                }
                if (!isEmpty(control.bmnCd)) {
                    $timeout(function() {
                        $scope.initBmnCd = true;
                        $scope.cond.bmnCd = control.bmnCd;
                    });
                }
                if (!isEmpty(control.subJigyobuCd)) {
                    $scope.cond.subJigyobuCd = control.subJigyobuCd;
                }
                if (!isEmpty(control.subTenCd)) {
                    $scope.cond.subTenCd = control.subTenCd;
                }
                if (!isEmpty(control.subBmnCd)) {
                    $timeout(function() {
                        $scope.initSubBmnCd = true;
                        $scope.cond.subBmnCd = control.subBmnCd;
                    });
                }
            }
        }, function(response) {

        });
    }

    /**
     * Search data for view.
     */
    $scope.doSearch = function() {
        $scope.result = {};
        $scope.result.dataArea = [];
        $scope.error = null;
        $scope.errorsTable = [];
        $scope.cond.searchFlg = 0;

        var control = SIJP0040Resource.query($scope.cond, function() {
            var resultForm = control[0];
            // format grid
            if (resultForm.dataArea == undefined) {
                resultForm.dataArea = [];
            }
            resultForm = $scope.formatData(resultForm);

            // only for search
            $scope.cond.syukaYmd = resultForm.syukaYmd;

            var stopToken1 = $interval(function() {
                if ($rootScope.pendingRequests == 0) {
                    $timeout(function() {
                        $scope.cond.tenCd = resultForm.tenCd;
                        $scope.initBmnCd = true;
                        $scope.cond.bmnCd = resultForm.bmnCd;
                        $scope.cond.subTenCd = resultForm.subTenCd;
                        $scope.initSubBmnCd = true;
                        $scope.cond.subBmnCd = resultForm.subBmnCd;
                        $rootScope.pendingRequests = -1;
                    }, 200);
                    $interval.cancel(stopToken1);
                }
            }, 250, 0, false);

            $scope.result = angular.copy(resultForm);

            // screen
            if ($scope.result.dataArea.length > 0) {
                $scope.isEditable = false;
                $scope.hasResult = true;
                $scope.isNewData = false;
            }

            Message.showMessage(MsgConst.MSG_KEY_DISPLAY_CORRECT_DATA);
            $scope.setFocus();
        }, function(response) {
            $scope.isEditable = false;
            $scope.hasResult = false;
            $scope.isNewData = false;

            if (response.status === HttpConst.CODE_NOT_FOUND) {
                $scope.hasResult = false;

                Message.showMessage(MsgConst.MSG_KEY_DATA_NOT_EXIST);
            } else if (response.status === HttpConst.CODE_BAD_REQUEST) {
                // エラー処理
                Message.showMessageWithContent(response.data.errors[0].level, response.data.errors[0].message);
                Client.showErrorFromServer($scope.error, response.data.errors);
            } else {
                Message.showMessage(MsgConst.MSG_KEY_SYSTEM_ERROR);
            }
            $scope.setFocus();
        });
    }

    /**
     * Search data for edit.
     */
    $scope.doSearchEdit = function() {
        $scope.result = {};
        $scope.originalResult = [];
        $scope.result.dataArea = [];
        $scope.error = null;
        $scope.errorsTable = [];
        $scope.cond.searchFlg = 1;

        var control = SIJP0040Resource.query($scope.cond, function() {
            var resultForm = control[0];
            // format grid
            if (resultForm.dataArea == undefined) {
                resultForm.dataArea = [];
            }
            resultForm = $scope.formatData(resultForm);

            // not reset in edit

            $scope.result = angular.copy(resultForm);
            $scope.originalResult = angular.copy(resultForm);

            // screen
            if ($scope.result.dataArea.length > 0) {
                if ($scope.result.syoriStsKbn == '1') {
                    $scope.isEditable = true;
                    $scope.hasResult = true;
                    $scope.isNewData = true;
                } else if ($scope.result.syoriStsKbn == '2') {
                    $scope.isEditable = true;
                    $scope.hasResult = true;
                    $scope.isNewData = false;
                } else {
                    // other status
                    $scope.isEditable = false;
                    $scope.hasResult = true;
                    $scope.isNewData = false;
                }
            }

            Message.showMessage(MsgConst.MSG_KEY_INFO_BUTTON_DELETE_SAVE);
            $scope.setFocus();
        }, function(response) {
            $scope.isEditable = false;
            $scope.hasResult = false;
            $scope.isNewData = false;

            if (response.status === HttpConst.CODE_NOT_FOUND) {
                var diag = Dialog.confirm(Message.getMessage(MsgConst.MSG_KEY_CONFIRM_NEW_REGISTERED));
                if (diag == null) {
                    return;
                }
                diag.result.then(
                    function() {
                        $scope.result = angular.copy($scope.cond);
                        $scope.result.syoriStsKbn = '1';
                        $scope.result.dataArea = [];
                        for (var i = 0; i < 10; i++) {
                            var row = {};
                            row.mesaiNo = (i + 1).toString();
                            row.outShnCd = "";
                            row.inShnCd = "";
                            row.outShnNm = "";
                            row.inShnNm = "";
                            row.kenBarasu = "";
                            row.kenGenk = "";
                            row.kenGenkKin = "";
                            row.kenBaik = "";
                            row.kenBaikKin = "";

                            $scope.result.dataArea.push(row);
                        }

                        $scope.originalResult = angular.copy($scope.result);

                        $scope.isEditable = true;
                        $scope.hasResult = false;
                        $scope.isNewData = true;

                        Message.showMessage(MsgConst.MSG_KEY_INFO_BUTTON_SAVE);
                        $scope.setFocus();
                    }, function() {
                        Message.showMessage(MsgConst.MSG_KEY_COMMON_INFO_MESSAGE);
                        $scope.setFocus();
                    }
                );
            } else if (response.status === HttpConst.CODE_BAD_REQUEST) {
                // エラー処理
                Message.showMessageWithContent(response.data.errors[0].level, response.data.errors[0].message);
                Client.showErrorFromServer($scope.error, response.data.errors);
                $scope.setFocus();
            } else {
                Message.showMessage(MsgConst.MSG_KEY_SYSTEM_ERROR);
                $scope.setFocus();
            }
        });
    }

    /**
     * Do insert/update data.
     */
    $scope.doInsertUpdate = function() {
        if ($scope.isNewData) {
            $scope.doInsert();
        } else {
            $scope.doUpdate();
        }
    }

    /**
     * Insert data to database.
     */
    $scope.doInsert = function() {
        $scope.error = null;
        $scope.errorsTable = [];

        /* Copy for pass validate */
        $scope.result.dpyKbn = $scope.cond.dpyKbn;
        $scope.result.dpyNo = $scope.cond.dpyNo;
        $scope.result.syukaYmd = $scope.cond.syukaYmd;
        $scope.result.kaisyaCd = $scope.cond.kaisyaCd;
        $scope.result.jigyobuCd = $scope.cond.jigyobuCd;
        $scope.result.tenCd = $scope.cond.tenCd;
        $scope.result.bmnCd = $scope.cond.bmnCd;
        $scope.result.subKaisyaCd = $scope.cond.subKaisyaCd;
        $scope.result.subJigyobuCd = $scope.cond.subJigyobuCd;
        $scope.result.subTenCd = $scope.cond.subTenCd;
        $scope.result.subBmnCd = $scope.cond.subBmnCd;
        /* end copy */
        // check required
        $scope.result.dataArea = $scope.validGridData($scope.result.dataArea);

        SIJP0040Resource.save($scope.result, function(response) {
            $scope.isEditable = false;
            $scope.hasResult = true;
            $scope.isNewData = false;

            Message.showMessage(MsgConst.MSG_KEY_INSERT_SUCCESS);
            if (response.data != undefined) {
                Client.showErrorFromServer($scope.error, response.data.errors);
            }
            $scope.setFocus();
        }, function(response) {
            if (response.status === HttpConst.CODE_BAD_REQUEST) {
                // エラー処理
                Message.showMessageWithContent(response.data.errors[0].level, response.data.errors[0].message);
                Client.showErrorFromServer($scope.error, response.data.errors);
                $scope.errorsTable = response.data.gridErrors;
            } else {
                Message.showMessage(MsgConst.MSG_KEY_SYSTEM_ERROR);
            }
            $scope.setFocus();
        });
    }

    /**
     * Update data to database.
     */
    $scope.doUpdate = function() {
        $scope.error = null;
        $scope.errorsTable = [];
        /* Copy for pass validate */
        $scope.result.dpyKbn = $scope.cond.dpyKbn;
        $scope.result.dpyNo = $scope.cond.dpyNo;
        $scope.result.syukaYmd = $scope.cond.syukaYmd;
        $scope.result.kaisyaCd = $scope.cond.kaisyaCd;
        $scope.result.jigyobuCd = $scope.cond.jigyobuCd;
        $scope.result.tenCd = $scope.cond.tenCd;
        $scope.result.bmnCd = $scope.cond.bmnCd;
        $scope.result.subKaisyaCd = $scope.cond.subKaisyaCd;
        $scope.result.subJigyobuCd = $scope.cond.subJigyobuCd;
        $scope.result.subTenCd = $scope.cond.subTenCd;
        $scope.result.subBmnCd = $scope.cond.subBmnCd;
        /* end copy */
        // check required
        $scope.result.dataArea = $scope.validGridData($scope.result.dataArea);

        SIJP0040Resource.update($scope.result, function(response) {
            $scope.isEditable = false;
            $scope.hasResult = true;
            $scope.isNewData = false;

            Message.showMessage(MsgConst.MSG_KEY_UPDATE_SUCCESS);
            $scope.setFocus();
        }, function(response) {
            if (response.status === HttpConst.CODE_BAD_REQUEST) {
                // エラー処理
                Message.showMessageWithContent(response.data.errors[0].level, response.data.errors[0].message);
                Client.showErrorFromServer($scope.error, response.data.errors);
                $scope.errorsTable = response.data.gridErrors;
            } else {
                Message.showMessage(MsgConst.MSG_KEY_SYSTEM_ERROR);
            }
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
        diag.result.then(
            function() {
                $scope.error = null;
                $scope.errorsTable = [];

                SIJP0040Resource.delete($scope.cond, function(response) {
                    $scope.isEditable = false;
                    $scope.hasResult = true;
                    $scope.isNewData = false;

                    $scope.originalResult = [];

                    Message.showMessage(MsgConst.MSG_KEY_DELETE_SUCCESS);
                    if (response.data != undefined) {
                        Client.showErrorFromServer($scope.error, response.data.errors);
                    }
                    $scope.setFocus();
                }, function(response) {
                    if (response.status === HttpConst.CODE_BAD_REQUEST) {
                        // エラー処理
                        Message.showMessageWithContent(response.data.errors[0].level, response.data.errors[0].message);
                        Client.showErrorFromServer($scope.error, response.data.errors);
                        $scope.errorsTable = response.data.gridErrors;
                    } else {
                        Message.showMessage(MsgConst.MSG_KEY_SYSTEM_ERROR);
                    }
                    $scope.setFocus();
                });
            }, function() {
                $scope.setFocus();
            }
        );
    }

    /**
     * Clear data on result or condition area.
     */
    $scope.doClear = function() {
        var diag = Dialog.confirm(Message.getMessage(MsgConst.MSG_KEY_CONFIRM_BEFORE_CLEAR));
        if (diag == null) {
            return;
        }
        diag.result.then(
            function() {
                if ($scope.hasResult == false && $scope.isEditable == false) {
                    $scope.cond.dpyKbn = '';
                    $scope.cond.dpyNo = '';
                    $scope.cond.kaisyaCd = AppConst.KAISYA_CODE;
                    $scope.cond.jigyobuCd = '';
                    $scope.cond.tenCd = '';
                    $scope.cond.bmnCd = '';
                    $scope.cond.subKaisyaCd = AppConst.KAISYA_CODE;
                    $scope.cond.subJigyobuCd = '';
                    $scope.cond.subTenCd = '';
                    $scope.cond.subBmnCd = '';
                    $scope.initCond();
                }

                $scope.result = {};
                $scope.result.dataArea = [];
                $scope.originalResult = {};
                $scope.error = Client.clearErrors();
                $scope.errorsTable = [];
                $scope.hasResult = false;
                $scope.isEditable = false;
                $scope.isNewData = false;
                $scope.form.$setPristine();
                Message.showMessage(MsgConst.MSG_KEY_COMMON_INFO_MESSAGE);
                $scope.setFocus();
            }, function() {
                $scope.setFocus();
            }
        );
    }

    /**
     * Check controls of the scope to Enable/Disable Delete button.
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

    var isvalid = false;
    $scope.$on('valid', function(evt, agr) {
        isvalid = agr;
    });

    /**
     * Check controls of the scope to Enable/Disable Save button.
     *
     * @return true:enable false:disable
     */
    $scope.canSave = function() {
        if ($scope.isEditable && isvalid && $scope.result != undefined && $scope.originalResult != undefined) {
            if ($scope.result.dataArea != undefined && $scope.originalResult.dataArea != undefined) {
                if (isEmpty($scope.result.inpGenInp)) {
                    return false;
                }
                for (var i = 0; i < $scope.result.dataArea.length; i++) {
                    if (!angular.equals($scope.result.dataArea[i].outShnCd, $scope.originalResult.dataArea[i].outShnCd) || !angular.equals(
                        $scope.result.dataArea[i].inShnCd, $scope.originalResult.dataArea[i].inShnCd) || !angular.equals($scope.result
                        .dataArea[i].kenBarasu, $scope.originalResult.dataArea[i].kenBarasu) || !angular.equals($scope.result.dataArea[
                        i].kenGenk, $scope.originalResult.dataArea[i].kenGenk) || !angular.equals($scope.result.dataArea[i].kenBaik,
                        $scope.originalResult.dataArea[i].kenBaik)) {
                        return true;
                    }
                }
                if (!angular.equals($scope.result.inpGenInp, $scope.originalResult.inpGenInp) || !angular.equals($scope.result.tekiyo,
                    $scope.originalResult.tekiyo)) {
                    return true;
                }
            }
            return false;
        } else {
            return false;
        }
    }

    /**
     * Check controls of the scope to Enable/Disable Search button.
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
     * Check controls of the scope to Enable/Disable SearchEdit button.
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

    /**
     * Format data before view.
     */
    $scope.formatData = function(resultForm) {
        if (resultForm == undefined || resultForm == null) {
            return resultForm;
        }

        // format data
        resultForm.inpGenInp = toString(resultForm.inpGenInp);
        resultForm.sumKenBaikKin = toString(resultForm.sumKenBaikKin).replace(/,/g, '');
        resultForm.sumKenGenkIn = toString(resultForm.sumKenGenkIn).replace(/,/g, '');
        resultForm.syoriStsKbnNm = toString(resultForm.syoriStsKbnNm);
        resultForm.inpGenInp = "";
        if (resultForm.tekiyo == null)
            resultForm.tekiyo = "";
        // format grid
        if (resultForm.dataArea == undefined) {
            resultForm.dataArea = [];
        }

        var n = resultForm.dataArea.length;
        for (var i = 0; i < n; i++) {
            var row = resultForm.dataArea[i];
            if (row.displayFlag) {
                row.kenBaik = toString(row.kenBaik);
                row.kenBaikKin = toString(row.kenBaikKin);
                row.kenBarasu = $filter('number')(toString(row.kenBarasu), 2).replace(/,/g, '');
                row.kenGenk = $filter('number')(toString(row.kenGenk), 2).replace(/,/g, '');
                row.kenGenkKin = toString(row.kenGenkKin);
            } else {
                row.kenBaik = "";
                row.kenBaikKin = "";
                row.kenBarasu = "";
                row.kenGenk = "";
                row.kenGenkKin = "";
            }
        }
        return resultForm;
    }

    /**
     * Valid data on grid before insert or update.
     */
    $scope.validGridData = function(gridData) {
        if (gridData == undefined) {
            return gridData;
        }

        for (var i = 0; i < gridData.length; i++) {
            if (!isEmpty(gridData[i].outShnCd) && isEmpty(gridData[i].inShnCd)) {
                gridData[i].inShnCd = gridData[i].outShnCd;
            }
        }
        return gridData;
    }


    /**
     * Get default tanka value from server.
     */
    $scope.getTanka = function(shnCd, index) {
        $scope.data = {};
        $scope.data.kaisyaCd = $scope.cond.kaisyaCd;
        $scope.data.jigyobuCd = $scope.cond.jigyobuCd;
        $scope.data.tenCd = $scope.cond.tenCd;
        $scope.data.keijoYmd = $scope.cond.syukaYmd;
        $scope.data.shnCd = shnCd;

        var control = SIJP0040GetTanka.getTanka($scope.data, function() {
            if (control != null && control != undefined) {
                if (isEmpty($scope.result.dataArea[index].kenBaik)) {
                    $scope.result.dataArea[index].kenBaik = control.defaultBaiTan;
                }
                if (isEmpty($scope.result.dataArea[index].kenGenk)) {
                    $scope.result.dataArea[index].kenGenk = control.defaultGenTan;
                }
            }
        }, function(response) {});
    }

    /**
     * Auto trace to do calculate data in grid.
     */
    $scope.$watch('result.dataArea', function() {
        if ($scope.isEditable && $scope.result.dataArea != undefined && $scope.originalResult != undefined && $scope.originalResult
            .dataArea != undefined) {
            if ($scope.result.dataArea.length > 0) {
                var kenGenkKin = [10];
                var kenBaikKin = [10];
                var sumKenGenkIn = 0;
                var sumKenBaikKin = 0;
                var gridData = $scope.result.dataArea;
                for (var i = 0; i < 10; i++) {
                    kenGenkKin[i] = Math.floor(gridData[i].kenBarasu * gridData[i].kenGenk);
                    kenBaikKin[i] = Math.floor(gridData[i].kenBarasu * gridData[i].kenBaik);

                    if (isEmpty(gridData[i].kenBarasu) && isEmpty(gridData[i].kenGenk) && isEmpty(gridData[i].kenBaik)) {
                        gridData[i].kenGenkKin = "";
                        gridData[i].kenBaikKin = "";
                    } else {
                        gridData[i].kenGenkKin = toString(kenGenkKin[i]);
                        gridData[i].kenBaikKin = toString(kenBaikKin[i]);
                    }
                    sumKenGenkIn = sumKenGenkIn + kenGenkKin[i];
                    sumKenBaikKin = sumKenBaikKin + kenBaikKin[i];

                    if (!isEmpty(gridData[i].outShnCd)) {
                        var modelCtrl = GetNgModelController("#outShnCd_" + i);
                        if (modelCtrl != null && modelCtrl != undefined) {
                            if (!modelCtrl.$invalid && !isEmpty($scope.cond.syukaYmd) &&
                                gridData[i].outShnCd.trim().length == 13 &&
                                gridData[i].outShnCd.trim() != $scope.originalResult.dataArea[i].outShnCd.trim() && (
                                    isEmpty(gridData[i].kenBaik) || isEmpty(gridData[i].kenGenk))) {
                                $scope.getTanka(gridData[i].outShnCd, i);
                            }
                        }
                    }
                }
                $scope.result.sumKenGenkIn = toString(sumKenGenkIn);
                $scope.result.sumKenBaikKin = toString(sumKenBaikKin);
            }
        }
    }, true);

    var rowTemplate = '<div ng-repeat="col in renderedColumns" ng-class="col.colIndex()" class="ngCell {{col.cellClass}}">' +
        '<div class="ngVerticalBar" ng-style="{height: rowHeight}" ng-class="{ ngVerticalBarVisible: !$last }">&nbsp;</div>' +
        '<div ng-cell-custom></div></div>';
    /**
     * Grid definition.
     */
    $scope.gridOptionsSIJP0040MsaiDenpyoArea = {
        data: 'result.dataArea',
        enablePaging: false,
        showFooter: false,
        footerRowHeight: 0,
        headerRowHeight: 38,
        rowHeight: 40,
        enableRowSelection: false,
        enableCellSelection: true,
        rowTemplate: rowTemplate,
        enableSorting: false,
        enableColumnResize: false,

        columnDefs: [{
            field: 'mesaiNo',
            displayName: '行',
            width: 25,
            cellClass: "cell text-center",
            cellTemplate: ''
        }, {
            field: 'outShnCd',
            displayName: '出荷店商品ｺｰﾄﾞ',
            width: 303,
            headerCellTemplate: '<div class="ngHeaderText cs-bor-b"><span>出荷店商品[GTIN]</span></div><div class="ngHeaderText cs-bor-b"><span>入荷店商品[GTIN]</span></div>',
            cellClass: "cell text-left",
            cellTemplate: '<div><cc-jancd-input cc-jan-search cc-label="" cc-partition="3" id="outShnCd_{{row.rowIndex}}" name="outShnCd_{{row.rowIndex}}" ' +
                'ng-model="result.dataArea[row.rowIndex].outShnCd" cc-required="false" cc-width1="103" cc-width2="193" ' +
                'cc-notexist=false cc-delexist=true cc-shortname=false cc-parameter01="" ' +
                'id2="outShnNm_{{row.rowIndex}}" name2="outShnNm_{{row.rowIndex}}" ng-model2="result.dataArea[row.rowIndex].outShnNm" ' +
                'cc-readonly="!changeDisable() || row.rowIndex === 9" cc-error="error" cc-focus="getFocusResult(row.rowIndex)"/></div>' +
                '<div><cc-jancd-input cc-jan-search cc-label="" cc-partition="3" id="inShnCd_{{row.rowIndex}}" name="inShnCd_{{row.rowIndex}}" ' +
                'ng-model="result.dataArea[row.rowIndex].inShnCd" cc-required="false" cc-width1="103" cc-width2="193" ' +
                'cc-notexist=false cc-delexist=true cc-shortname=false cc-parameter01="" ' +
                'id2="inShnNm_{{row.rowIndex}}" name2="inShnNm_{{row.rowIndex}}" ng-model2="result.dataArea[row.rowIndex].inShnNm" ' +
                'cc-readonly="!changeDisable() || row.rowIndex === 9" cc-error="error"/></div'
        }, {
            field: 'kenBarasu',
            displayName: '数量',
            width: 80,
            cellTemplate: '<div><cc-number-input cc-label="" cc-partition="" id="kenBarasu_{{row.rowIndex}}" name="kenBarasu_{{row.rowIndex}}" ' +
                'ng-model="result.dataArea[row.rowIndex].kenBarasu" cc-required="false" cc-min="0.01" cc-max="999999.99" ' +
                'cc-readonly="!changeDisable() || row.rowIndex === 9" cc-error="error"/></div>'
        }, {
            field: 'kenGenk',
            displayName: '原単価',
            width: 90,
            cellTemplate: '<div><cc-number-input cc-label="" cc-partition="" id="kenGenk_{{row.rowIndex}}" name="kenGenk_{{row.rowIndex}}" ' +
                'ng-model="result.dataArea[row.rowIndex].kenGenk" cc-required="false" cc-min="-9999999.99" cc-max="9999999.99" ' +
                'cc-readonly="!changeDisable() || row.rowIndex === 9" cc-error="error" cc-width="88"/></div>'
        }, {
            field: 'kenGenkKin',
            displayName: '原価金額',
            width: 113,
            cellTemplate: '<div><cc-number-input cc-label="" cc-partition="" id="kenGenkKin_{{row.rowIndex}}" name="kenGenkKin_{{row.rowIndex}}" ' +
                'ng-model="result.dataArea[row.rowIndex].kenGenkKin" cc-required="false" cc-readonly="true" cc-error="error" cc-width="110"></cc-number-input></div>'
        }, {
            field: 'kenBaik',
            displayName: '売単価',
            width: 80,
            cellTemplate: '<div><cc-number-input cc-label="" cc-partition="" ' +
                'id="kenBaik_{{row.rowIndex}}" name="kenBaik_{{row.rowIndex}}" ng-model="result.dataArea[row.rowIndex].kenBaik" cc-required="false" ' +
                'cc-min="-9999999" cc-max="9999999" cc-readonly="!changeDisable() || row.rowIndex === 9" cc-error="error"/></div>'
        }, {
            field: 'kenBaikKin',
            width: 118,
            displayName: '売価金額',
            cellTemplate: '<div><cc-number-input cc-label="" cc-partition="" id="kenBaikKin_{{row.rowIndex}}" name="kenBaikKin_{{row.rowIndex}}" ' +
                'ng-model="result.dataArea[row.rowIndex].kenBaikKin" cc-required="false" cc-readonly="true" cc-error="error" cc-width="115"></cc-number-input></div>'
        }, {
            field: 'kppinRiyuKbn',
            displayName: '欠品理由',
            cellTemplate: '<cc-kbn-combobox cc-label="" cc-partition="3" id="kppinRiyuKbn_{{row.rowIndex}}" name="kppinRiyuKbn_{{row.rowIndex}}" ng-model="result.dataArea[row.rowIndex].kppinRiyuKbn" cc-required="false" ng-maxlength="2" cc-key1="S0005" cc-shortname=false cc-readonly="!changeDisable() || row.rowIndex === 9" cc-error="error" cc-width="118"/>'
        }]
    };
});