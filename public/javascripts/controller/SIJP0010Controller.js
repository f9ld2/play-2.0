///////////////////////////////////////////////////////////////////////
// Copyright(C) 2015 NEC Solution Innovators, Ltd. All Rights Reserved.
///////////////////////////////////////////////////////////////////////

/* ====================================================================
 * 機能名称 : ＥＯＳ伝票入力
 * 改版履歴
 * Rev. 改版年月日   改版者名       内容
 * 1.0   2014.04.07   Hungtb      新規作成
 * =================================================================== */
var app = angular.module('sijp0010', ['sijp0010Services', 'ui', 'ui.select2', 'directives', 'ngGrid']);
app.controller("SIJP0010Ctrl", function($scope, Message, MsgConst, HttpConst, SIJP0010DEL, SIJP0010Resource,
    SIJP0010ResourceExt, $filter, $routeParams, Dialog, DialogInfo, $timeout, $rootScope, $interval,
    AppConst, FocusConst, Client) {
    $scope.cond = {
        kaisyaCd: AppConst.KAISYA_CODE
    };
    $scope.result = {};
    $scope.isEditable = false;
    $scope.hasResult = false;
    $scope.isNewData = false;
    Message.showMessage(MsgConst.MSG_KEY_COMMON_INFO_MESSAGE);

    $scope.focusCond = FocusConst.TEXTINPUT_FOCUS;
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
            setFocusCond($scope, $interval, FocusConst.TEXTINPUT_FOCUS);
        }
    }

    /**
     * Change read-only status of controls.
     *
     * @return true:read-only false:not read-only
     */
    $scope.changeDisable = function() {
        return $scope.isEditable;
    }

    /**
     * Init condition area with params.
     */
    $scope.initCond = function() {
        var control = SIJP0010ResourceExt.init($routeParams, function() {
            if (control != undefined) {
                $scope.cond.dpyKbn = control.dpyKbn;
                if (!isEmpty(control.kaisyaCd)) {
                    $scope.cond.kaisyaCd = control.kaisyaCd;
                }
                $scope.cond.jigyobuCd = control.jigyobuCd;

                if (!isEmpty(control.dpyNo)) {
                    $scope.cond.dpyNo = control.dpyNo;
                }
                if (!isEmpty(control.nhnYmd)) {
                    $scope.cond.nhnYmd = control.nhnYmd;
                }
            }
        }, function(response) {});
    }

    /**
     * Search data for view.
     */
    $scope.doSearch = function() {
        $scope.result = {};
        $scope.result.dataArea = [];
        $scope.error = null;
        $scope.cond.searchFlg = 0;
        $scope.isEditable = false;
        $scope.hasResult = false;
        $scope.isNewData = false;

        var control = SIJP0010Resource.query($scope.cond, function() {
            var resultForm = control[0];
            // format grid
            if (resultForm.dataArea == undefined) {
                resultForm.dataArea = [];
            }
            resultForm = $scope.formatData(resultForm);

            // return data
            $scope.cond.dpyKbn = resultForm.dpyKbn;
            $scope.cond.kaisyaCd = resultForm.kaisyaCd;
            $scope.cond.jigyobuCd = resultForm.jigyobuCd;

            $scope.cond.tenCd = resultForm.tenCd;
            $scope.initBmnCd = true;
            $scope.cond.bmnCd = resultForm.bmnCd;
            $scope.cond.nhnYmd = resultForm.nhnYmd;
            $scope.cond.nhnYoteiYmd = resultForm.nhnYoteiYmd;
            $scope.cond.hatYmd = resultForm.hatYmd;
            $scope.cond.triCd = resultForm.triCd;
            $scope.cond.triNm = resultForm.triNm;
            // set after nhnYmd
            var stopToken1 = $interval(function() {
                if ($rootScope.pendingRequests == 0) {
                    $timeout(function() {
                        $scope.cond.dctcKbn = resultForm.dctcKbn;
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
     * Search for edit.
     */
    $scope.doSearchEdit = function() {
        $scope.result = {};
        $scope.originalResult = {};
        $scope.result.dataArea = [];
        $scope.error = null;
        $scope.cond.searchFlg = 1;

        var control = SIJP0010Resource.query($scope.cond, function() {
            var resultForm = control[0];
            // format grid
            if (resultForm.dataArea == undefined) {
                resultForm.dataArea = [];
            }
            resultForm = $scope.formatData(resultForm);

            // return data
            $scope.cond.dpyKbn = resultForm.dpyKbn;
            $scope.cond.kaisyaCd = resultForm.kaisyaCd;
            $scope.cond.jigyobuCd = resultForm.jigyobuCd;

            $scope.cond.tenCd = resultForm.tenCd;
            $scope.initBmnCd = true;
            $scope.cond.bmnCd = resultForm.bmnCd;

            $scope.cond.nhnYoteiYmd = resultForm.nhnYoteiYmd;
            $scope.cond.hatYmd = resultForm.hatYmd;
            $scope.cond.triCd = resultForm.triCd;
            $scope.cond.triNm = resultForm.triNm;

            $scope.result = angular.copy(resultForm);
            $scope.originalResult = angular.copy(resultForm);

            // screen
            if ($scope.result.dataArea.length > 0) {
                if ($scope.result.syoriStsKbn == '1') {
                    $scope.isEditable = true;
                    $scope.hasResult = true;
                    $scope.isNewData = true;
                    Message.showMessage(MsgConst.MSG_KEY_INFO_BUTTON_SAVE);
                } else if ($scope.result.syoriStsKbn == '2') {
                    $scope.isEditable = true;
                    $scope.hasResult = true;
                    $scope.isNewData = false;
                    Message.showMessage(MsgConst.MSG_KEY_INFO_BUTTON_UNDO_SAVE);
                } else {
                    // other status
                    $scope.isEditable = false;
                    $scope.hasResult = true;
                    $scope.isNewData = false;
                    Message.showMessage(MsgConst.MSG_KEY_COMMON_INFO_MESSAGE);
                }
            }
            $scope.setFocus();
        }, function(response) {
            $scope.isEditable = false;
            $scope.hasResult = false;
            $scope.isNewData = false;

            if (response.status === HttpConst.CODE_NOT_FOUND) {
                if (response.data.errors != null && response.data.errors.length > 0) {
                    Message.showMessageWithContent(response.data.errors[0].level, response.data.errors[0].message);
                } else {
                    Message.showMessage(MsgConst.MSG_KEY_DATA_NOT_EXIST);
                }
            } else if (response.status === HttpConst.CODE_BAD_REQUEST || response.status === HttpConst.CODE_FORBIDDEN) {
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
     * Insert or Update data.
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
        /* Copy for pass validate */
        $scope.result.dctcKbn = $scope.cond.dctcKbn;
        $scope.result.nhnYmd = $scope.cond.nhnYmd;

        SIJP0010Resource.save($scope.result, function(response) {
            $scope.isEditable = false;
            $scope.hasResult = true;
            $scope.isNewData = false;

            Message.showMessage(MsgConst.MSG_KEY_INSERT_SUCCESS);
            if (response.data != undefined) {
                Client.showErrorFromServer($scope.error, response.data.errors);
            }
            $scope.setFocus();
        }, function(response) {
            if (response.status === HttpConst.CODE_BAD_REQUEST || response.status === HttpConst.CODE_FORBIDDEN) {
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
     * Update data to database.
     */
    $scope.doUpdate = function() {
        $scope.error = null;
        /* Copy for pass validate */
        $scope.result.dctcKbn = $scope.cond.dctcKbn;
        $scope.result.nhnYmd = $scope.cond.nhnYmd;

        SIJP0010Resource.update($scope.result, function(response) {
            $scope.isEditable = false;
            $scope.hasResult = true;
            $scope.isNewData = false;

            Message.showMessage(MsgConst.MSG_KEY_UPDATE_SUCCESS);
            if (response.data != undefined) {
                Client.showErrorFromServer($scope.error, response.data.errors);
            }
            $scope.setFocus();
        }, function(response) {
            if (response.status === HttpConst.CODE_BAD_REQUEST || response.status === HttpConst.CODE_FORBIDDEN) {
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
     * Delete data.
     */
    $scope.doDelete = function() {
        var diag = Dialog.confirm(Message.getMessage(MsgConst.MSG_KEY_CONFIRM_BEFORE_UNDO));
        if (diag == null) {
            return;
        }
        diag.result.then(
            function() {
                $scope.error = null;
                /* Copy for pass validate */

                SIJP0010DEL.delete($scope.result, function(response) {
                    $scope.isEditable = false;
                    $scope.hasResult = true;
                    $scope.isNewData = false;

                    $scope.originalResult = {};

                    Message.showMessage(MsgConst.MSG_KEY_UNDO_SUCCESS);
                    if (response.data != undefined) {
                        Client.showErrorFromServer($scope.error, response.data.errors);
                    }
                    $scope.setFocus();
                }, function(response) {
                    if (response.status === HttpConst.CODE_BAD_REQUEST || response.status === HttpConst.CODE_FORBIDDEN) {
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
            }
        );
    }

    /**
     * Clear result or condtion area.
     */
    $scope.doClear = function() {
        var diag = Dialog.confirm(Message.getMessage(MsgConst.MSG_KEY_CONFIRM_BEFORE_CLEAR));
        if (diag == null) {
            return;
        }
        diag.result.then(
            function() {
                if ($scope.hasResult == false && $scope.isEditable == false) {
                    $scope.cond.dpyKbn = "";
                    $scope.cond.kaisyaCd = AppConst.KAISYA_CODE;
                    $scope.cond.jigyobuCd = "";
                    $scope.cond.dpyNo = "";
                    $scope.cond.tenCd = "";
                    $scope.cond.bmnCd = "";
                    $scope.cond.nhnYoteiYmd = "";
                    $scope.cond.hatYmd = "";
                    $scope.cond.triCd = "";
                    $scope.cond.triNm = "";
                    $scope.initCond();
                }

                $scope.result = {};
                $scope.result.dataArea = [];
                $scope.originalResult = {};
                $scope.error = Client.clearErrors();
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

    /**
     * Check controls of the scope to Enable/Disable Save button.
     *
     * @return true:enable false:disable
     */
    $scope.canSave = function() {
        if ($scope.isEditable && $scope.result != undefined && $scope.originalResult != undefined) {
            if ($scope.result.dataArea != undefined && $scope.originalResult.dataArea != undefined) {
                if (isEmpty($scope.result.inpGenInp)) {
                    return false;
                }
                for (var i = 0; i < $scope.result.dataArea.length; i++) {
                    if (!angular.equals($scope.result.dataArea[i].kenBaraSu, $scope.originalResult.dataArea[i].kenBaraSu)) {
                        return true;
                    }
                }
                if (!angular.equals($scope.result.inpGenInp, $scope.originalResult.inpGenInp)) {
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
     * Format data before input in grid and controls.
     *
     * @return data after format
     */
    $scope.formatData = function(resultForm) {
        if (resultForm == undefined || resultForm == null) {
            return resultForm;
        }
        // format data
        resultForm.sumKenBaikKin = resultForm.sumKenBaikKin;
        resultForm.sumKenGenkKin = resultForm.sumKenGenkKin;
        resultForm.inpGenInp = "";
        if (resultForm.tekiyo == null) {
            resultForm.tekiyo = "";
        }
        // format grid
        if (resultForm.dataArea == undefined) {
            resultForm.dataArea = [];
        }
        var n = resultForm.dataArea.length;
        for (var i = 0; i < n; i++) {
            var row = resultForm.dataArea[i];
            if (row.displayFlag) {
                row.hatBaraSu = $filter('number')(row.hatBaraSu, 2).replace(/,/g, '');
                row.kenBaraSu = $filter('number')(row.kenBaraSu, 2).replace(/,/g, '');
                row.hatGenk = $filter('number')(row.hatGenk, 2).replace(/,/g, '');
            } else {
                row.hatBaraSu = 0;
                row.kenBaraSu = $filter('number')(0, 2).replace(/,/g, '');
                row.hatGenk = 0;
                row.hatGenkKin = 0;
                row.hatBaik = 0;
                row.hatBaikKin = 0;

            }
        }
        return resultForm;
    }

    /**
     * Auto trace to calculate grid values.
     */
    $scope.$watch('result.dataArea', function() {
        if ($scope.isEditable && $scope.result.dataArea != undefined && $scope.originalResult != undefined && $scope.originalResult
            .dataArea != undefined) {
            if ($scope.result.dataArea.length > 0) {
                var hatGenkKin = [10];
                var hatBaikKin = [10];
                var sumKenGenkKin = 0;
                var sumKenBaikKin = 0;
                for (var i = 0; i < 10; i++) {
                    var kenBaraSu = $scope.result.dataArea[i].kenBaraSu;
                    if (kenBaraSu == null || kenBaraSu == undefined) {
                        kenBaraSu = 0;
                    }
                    hatGenkKin[i] = Math.floor(kenBaraSu * $scope.result.dataArea[i].hatGenk);
                    hatBaikKin[i] = Math.floor(kenBaraSu * $scope.result.dataArea[i].hatBaik);

                    $scope.result.dataArea[i].hatGenkKin = hatGenkKin[i];
                    $scope.result.dataArea[i].hatBaikKin = hatBaikKin[i];

                    sumKenGenkKin = sumKenGenkKin + hatGenkKin[i];
                    sumKenBaikKin = sumKenBaikKin + hatBaikKin[i];
                }
                $scope.result.sumKenGenkKin = sumKenGenkKin;
                $scope.result.sumKenBaikKin = sumKenBaikKin;

            }
        }
    }, true);

    /**
     * Grid defined
     */
    var rowTemplate = '<div ng-repeat="col in renderedColumns" ng-class="col.colIndex()" class="ngCell {{col.cellClass}}">' +
        '<div class="ngVerticalBar" ng-style="{height: rowHeight}" ng-class="{ ngVerticalBarVisible: !$last }">&nbsp;</div>' +
        '<div ng-cell-custom></div></div>';
    $scope.gridOptionsSIJP0010DenpyouInfArea = {
        data: 'result.dataArea',
        enablePaging: false,
        showFooter: false,
        footerRowHeight: 0,
        headerRowHeight: 19,
        rowHeight: 19,
        enableRowSelection: false,
        enableCellSelection: true,
        rowTemplate: rowTemplate,
        enableSorting: false,
        enableColumnResize: false,

        columnDefs: [{
            field: 'mesaiNo',
            displayName: '行',
            width: 30,
            cellClass: "cell text-center",
            cellTemplate: '<span ng-cell-text ng-show="{{result.dataArea[row.rowIndex].displayFlag}}">{{result.dataArea[row.rowIndex].mesaiNo}}</span>'
        }, {
            field: 'shnCd',
            displayName: '商品[GTIN]',
            width: 100,
            cellClass: "cell text-left",
            cellTemplate: '<span ng-cell-text>{{result.dataArea[row.rowIndex].shnCd}}</span>'
        }, {
            field: 'shnNm',
            displayName: '商品名称',
            width: 147,
            cellClass: "cell text-left",
            cellTemplate: '<span ng-cell-text>{{result.dataArea[row.rowIndex].shnNm}}</span>'
        }, {
            field: 'tani',
            displayName: '発注入数',
            width: 60,
            cellClass: "cell text-center",
            cellTemplate: '<span ng-cell-text ng-show="{{result.dataArea[row.rowIndex].displayFlag}}">{{result.dataArea[row.rowIndex].tani}}</span>'
        }, {
            field: 'hatBaraSu',
            displayName: '発注数量',
            width: 65,
            cellClass: "cell text-right",
            cellTemplate: '<span id="hatBaraSu_{{row.rowIndex}}" name="hatBaraSu_{{row.rowIndex}}" ' +
                'ng-cell-text>{{result.dataArea[row.rowIndex].displayFlag? (result.dataArea[row.rowIndex].hatBaraSu | number:2) : ""}}</span>'
        }, {
            field: 'kenBaraSu',
            displayName: '納品数量',
            width: 80,
            cellTemplate: '<div ng-show="result.dataArea[row.rowIndex].displayFlag"><cc-number-input cc-label="" cc-partition="" ' +
                'id="kenBaraSu_{{row.rowIndex}}" name="kenBaraSu_{{row.rowIndex}}" cc-readonly="!changeDisable()" cc-error="error" ' +
                'ng-model="result.dataArea[row.rowIndex].kenBaraSu" cc-required="true" cc-min="0.01" cc-max="999999.99" ' +
                ' cc-focus="getFocusResult(row.rowIndex)"></cc-number-input></div>'
        }, {
            field: 'hatGenk',
            displayName: '原単価',
            width: 90,
            cellClass: "cell text-right",
            cellTemplate: '<span id="hatGenk_{{row.rowIndex}}" name="hatGenk_{{row.rowIndex}}" ' +
                'ng-cell-text>{{result.dataArea[row.rowIndex].displayFlag? (result.dataArea[row.rowIndex].hatGenk | number:2) : ""}}</span>'
        }, {
            field: 'hatGenkKin',
            displayName: '原価金額',
            width: 90,
            cellClass: "cell text-right",
            cellTemplate: '<span id="hatGenkKin_{{row.rowIndex}}" name="hatGenkKin_{{row.rowIndex}}" ' +
                'ng-cell-text>{{result.dataArea[row.rowIndex].displayFlag? (result.dataArea[row.rowIndex].hatGenkKin | number:0) : ""}}</span>'
        }, {
            field: 'hatBaik',
            displayName: '売単価',
            width: 70,
            cellClass: "cell text-right",
            cellTemplate: '<span id="hatBaik_{{row.rowIndex}}" name="hatBaik_{{row.rowIndex}}" ' +
                'ng-cell-text>{{result.dataArea[row.rowIndex].displayFlag? (result.dataArea[row.rowIndex].hatBaik | number:0) : ""}}</span>'
        }, {
            field: 'hatBaikKin',
            displayName: '売価金額',
            width: 95,
            cellClass: "cell text-right",
            cellTemplate: '<span id="hatBaikKin_{{row.rowIndex}}" name="hatBaikKin_{{row.rowIndex}}" ' +
                'ng-cell-text>{{result.dataArea[row.rowIndex].displayFlag? (result.dataArea[row.rowIndex].hatBaikKin | number:0) : ""}}</span>'
        }, {
            field: 'kppinRiyuKbn',
            displayName: '欠品理由',
            cellTemplate: '<cc-kbn-combobox cc-label="" cc-partition="3" id="kppinRiyuKbn_{{row.rowIndex}}" name="kppinRiyuKbn_{{row.rowIndex}}" ng-model="result.dataArea[row.rowIndex].kppinRiyuKbn" cc-required="false" ng-maxlength="2" cc-key1="S0005" cc-shortname=false cc-readonly="!changeDisable()" cc-error="error" cc-width="118"/>'
        }]
    }
});