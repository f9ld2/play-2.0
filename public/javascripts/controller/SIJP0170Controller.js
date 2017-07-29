// /////////////////////////////////////////////////////////////////////
// Copyright(C) 2015 NEC Solution Innovators, Ltd. All Rights Reserved.
// /////////////////////////////////////////////////////////////////////

/*
 * ====================================================================
 *
 * 機能名称 : ＥＯＳ伝票完納入力
 *
 * 改版履歴
 *
 * Rev. 改版年月日 改版者名 内容
 *
 * 1.0 2014-04-05 TUCTV 新規作成
 *
 * ===================================================================
 */
var app = angular.module('sijp0170', ['sijp0170Services', 'ui', 'ui.select2', 'directives']);
app.controller("SIJP0170Ctrl", function($scope, $window, $interval, Message, MsgConst, HttpConst,
    SIJP0170Control, SIJP0170Init, $http, Dialog, DialogInfo, AppConst, FocusConst, Client) {
    Message.showMessage(MsgConst.MSG_KEY_COMMON_INFO_MESSAGE_F9);

    $scope.cond = {};
    $scope.result = {};
    $scope.cond.kaisyaCd = AppConst.KAISYA_CODE;

    $scope.isEditable = false;
    $scope.hasResult = false;
    $scope.isNewData = false;
    $scope.isSave = false;

    $scope.sijp0170MsaiInfArea1Data = {};
    $scope.sijp0170MsaiInfArea2Data = {};
    $scope.sijp0170MsaiInfArea3Data = {};

    $scope.original1Result = {};
    $scope.original2Result = {};
    $scope.original3Result = {};
    $scope.focusCond = FocusConst.TEXTINPUT_FOCUS;
    $scope.focusResult = FocusConst.NOT_FOCUS;

    $scope.sysdate = '';

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
          setFocusCond($scope, $interval, FocusConst.TEXTINPUT_FOCUS);
        }
    }

    /**
     * Process data of grid whenever the cond.inpNohinDate changes.
     */
    $scope.$watch('cond.inpNohinDate', function(value) {
        if (!isEmpty($scope.cond.inpNohinDate)) {
            $scope.sysdate = angular.copy($scope.cond.inpNohinDate);
        } else {
            $scope.sysdate = $scope.getCurrentDate();
        }
    });


    /**
    * Set empty grid
    */
    $scope.doAddGirdEmpty = function() {
        $scope.sijp0170MsaiInfArea1Data = [{
            dpyNo: '',
            sKenGenKin: ''
        }, {
            dpyNo: '',
            sKenGenKin: ''
        }, {
            dpyNo: '',
            sKenGenKin: ''
        }, {
            dpyNo: '',
            sKenGenKin: ''
        }, {
            dpyNo: '',
            sKenGenKin: ''
        }, {
            dpyNo: '',
            sKenGenKin: ''
        }, {
            dpyNo: '',
            sKenGenKin: ''
        }, {
            dpyNo: '',
            sKenGenKin: ''
        }, {
            dpyNo: '',
            sKenGenKin: ''
        }, {
            dpyNo: '',
            sKenGenKin: ''
        }, {
            dpyNo: '',
            sKenGenKin: ''
        }, {
            dpyNo: '',
            sKenGenKin: ''
        }, {
            dpyNo: '',
            sKenGenKin: ''
        }, {
            dpyNo: '',
            sKenGenKin: ''
        }, {
            dpyNo: '',
            sKenGenKin: ''
        }];

        $scope.sijp0170MsaiInfArea2Data = [{
            dpyNo: '',
            sKenGenKin: ''
        }, {
            dpyNo: '',
            sKenGenKin: ''
        }, {
            dpyNo: '',
            sKenGenKin: ''
        }, {
            dpyNo: '',
            sKenGenKin: ''
        }, {
            dpyNo: '',
            sKenGenKin: ''
        }, {
            dpyNo: '',
            sKenGenKin: ''
        }, {
            dpyNo: '',
            sKenGenKin: ''
        }, {
            dpyNo: '',
            sKenGenKin: ''
        }, {
            dpyNo: '',
            sKenGenKin: ''
        }, {
            dpyNo: '',
            sKenGenKin: ''
        }, {
            dpyNo: '',
            sKenGenKin: ''
        }, {
            dpyNo: '',
            sKenGenKin: ''
        }, {
            dpyNo: '',
            sKenGenKin: ''
        }, {
            dpyNo: '',
            sKenGenKin: ''
        }, {
            dpyNo: '',
            sKenGenKin: ''
        }];

        $scope.sijp0170MsaiInfArea3Data = [{
            dpyNo: '',
            sKenGenKin: ''
        }, {
            dpyNo: '',
            sKenGenKin: ''
        }, {
            dpyNo: '',
            sKenGenKin: ''
        }, {
            dpyNo: '',
            sKenGenKin: ''
        }, {
            dpyNo: '',
            sKenGenKin: ''
        }, {
            dpyNo: '',
            sKenGenKin: ''
        }, {
            dpyNo: '',
            sKenGenKin: ''
        }, {
            dpyNo: '',
            sKenGenKin: ''
        }, {
            dpyNo: '',
            sKenGenKin: ''
        }, {
            dpyNo: '',
            sKenGenKin: ''
        }, {
            dpyNo: '',
            sKenGenKin: ''
        }, {
            dpyNo: '',
            sKenGenKin: ''
        }, {
            dpyNo: '',
            sKenGenKin: ''
        }, {
            dpyNo: '',
            sKenGenKin: ''
        }, {
            dpyNo: '',
            sKenGenKin: ''
        }];

        $scope.original1Result = angular.copy($scope.sijp0170MsaiInfArea1Data);
        $scope.original2Result = angular.copy($scope.sijp0170MsaiInfArea2Data);
        $scope.original3Result = angular.copy($scope.sijp0170MsaiInfArea3Data);
    }

    /**
     * Process data of grid whenever the sijp0170MsaiInfArea1Data changes.
     */
    $scope.$watch('sijp0170MsaiInfArea1Data', function() {

        $scope.getDataGridChange($scope.sijp0170MsaiInfArea1Data);
    }, true);

    /**
     * Process data of grid whenever the sijp0170MsaiInfArea2Data changes.
     */
    $scope.$watch('sijp0170MsaiInfArea2Data', function() {
        $scope.getDataGridChange($scope.sijp0170MsaiInfArea2Data);
    }, true);

    /**
     * Process data of grid whenever the sijp0170MsaiInfArea3Data changes.
     */
    $scope.$watch('sijp0170MsaiInfArea3Data', function() {
        $scope.getDataGridChange($scope.sijp0170MsaiInfArea3Data);
    }, true);

    /**
     * getDataGridChange
     * @param resultlist data of grid
     */
    $scope.getDataGridChange = function(resultlist) {
        $scope.getdataKenGenKin(0, resultlist);
        for (var j = 0; j < 15; j++) {
            if (resultlist[j].dpyNo == undefined) {
                resultlist[j].sKenGenKin = '';
                $scope.isSave = true;
            } else {
                if (resultlist[j].dpyNo.length < 9 && resultlist[j].sKenGenKin.length > 0) {
                    resultlist[j].sKenGenKin = '';
                    $scope.isSave = true;
                } else {
                    $scope.isSave = false;
                }
            }
        }
    }

    /**
     * getdataKenGenKin
     * @param resultlist data of grid
     * @param i index
     */
    $scope.getdataKenGenKin = function(i, resultlist) {
        if (i >= 15) {
            return;
        }

        if (resultlist[i].dpyNo != undefined && resultlist[i].dpyNo != null && resultlist[i].dpyNo != '') {
            if (resultlist[i].dpyNo.length == 9 && resultlist[i].sKenGenKin.length == 0) {
                var dpyNo = resultlist[i].dpyNo;
                var kaisya = $scope.cond.kaisyaCd;
                var jigyobu = $scope.cond.jigyobuCd;
                if (kaisya == '') {
                    kaisya = 'undefined';
                }

                if (jigyobu == '') {
                    jigyobu = 'undefined';
                }
                $http({
                    method: 'GET',
                    url: '/core/SIJP0170/' + dpyNo + '/' + kaisya + '/' + jigyobu
                }).success(function(data) {
                    resultlist[i].sKenGenKin = data[0];
                    $scope.getdataKenGenKin(++i, resultlist);
                    $scope.isEditable = true;
                    $scope.isSave = false;
                }).error(function(data, status, headers, config) {
                    resultlist[i].sKenGenKin = '';
                });
            } else {
                $scope.getdataKenGenKin(++i, resultlist);
            }
        } else {
            $scope.getdataKenGenKin(++i, resultlist);
        }
    }

    /**
     * Define rowTemplate.
     */
    var rowTemplate =
        '<div ng-repeat="col in renderedColumns" ng-class="col.colIndex()" class="ngCell {{col.cellClass}}"><div class="ngVerticalBar" ng-style="{height: rowHeight}" ng-class="{ ngVerticalBarVisible: !$last }">&nbsp;</div><div ng-cell-custom></div></div>';

    /**
     * Initialize the grid options.
     */
    $scope.gridOptionsSIJP0170MsaiInfArea1 = {
        data: 'sijp0170MsaiInfArea1Data',
        enablePaging: false,
        showFooter: false,
        footerRowHeight: 0,
        headerRowHeight: 19,
        rowHeight: 20,
        enableRowSelection: false,
        enableCellSelection: true,
        rowTemplate: rowTemplate,
        enableSorting: false,
        enableColumnResize: true,
        columnDefs: [{
            field: 'dpyNo',
            displayName: '伝票NO',
            width: 80,
            cellTemplate: '<cc-code-input cc-label="" cc-partition="" id="dpyNo1_{{row.rowIndex}}" name="dpyNo1_{{row.rowIndex}}" ' +
                'ng-model="row.entity.dpyNo" cc-readonly="!changeDisable()" cc-required="false" ng-minlength="9" ng-maxlength="9" ' +
                'cc-focus="getFocusResult() && row.rowIndex===0" cc-chartype="C1" cc-error="error" ></cc-code-input>'
        }, {
            field: 'sKenGenKin',
            displayName: '原価合計金額',
            width: 218,
            cellTemplate: '<div id="sKenGenKin1_{{row.rowIndex}}" class = "text-right">{{row.getProperty(col.field) | number}}&nbsp;</div>'
        }]
    };

    /**
     * Initialize the grid options.
     */
    $scope.gridOptionsSIJP0170MsaiInfArea2 = {
        data: 'sijp0170MsaiInfArea2Data',
        enablePaging: false,
        showFooter: false,
        footerRowHeight: 0,
        headerRowHeight: 19,
        rowHeight: 20,
        enableRowSelection: false,
        enableCellSelection: true,
        rowTemplate: rowTemplate,
        enableSorting: false,
        enableColumnResize: true,
        // pagingOptions: $scope.pagingOptionsSIJP0170MsaiInfArea2,
        columnDefs: [{
            field: 'dpyNo',
            displayName: '伝票NO',
            width: 80,
            cellTemplate: '<cc-code-input cc-label="" cc-partition="" id="dpyNo2_{{row.rowIndex}}" name="dpyNo2_{{row.rowIndex}}" ' +
                'ng-model="row.entity.dpyNo" cc-readonly="!changeDisable()" cc-required="false" ng-minlength="9" ng-maxlength="9" cc-chartype="C1" cc-error="error" ></cc-code-input>'
        }, {
            field: 'sKenGenKin',
            displayName: '原価合計金額',
            width: 218,
            cellTemplate: '<div id="sKenGenKin2_{{row.rowIndex}}" class = "text-right">{{row.getProperty(col.field) | number}}&nbsp;</div>'
        }]
    };

    /**
     * Initialize the grid options.
     */
    $scope.gridOptionsSIJP0170MsaiInfArea3 = {
        data: 'sijp0170MsaiInfArea3Data',
        enablePaging: false,
        showFooter: false,
        footerRowHeight: 0,
        headerRowHeight: 19,
        rowHeight: 20,
        enableRowSelection: false,
        enableCellSelection: true,
        rowTemplate: rowTemplate,
        enableSorting: false,
        enableColumnResize: true,
        columnDefs: [{
            field: 'dpyNo',
            displayName: '伝票NO',
            width: 80,
            cellTemplate: '<cc-code-input cc-label="" cc-partition="" id="dpyNo3_{{row.rowIndex}}" name="dpyNo3_{{row.rowIndex}}" ng-model="row.entity.dpyNo" cc-readonly="!changeDisable()" cc-required="false" ng-minlength="9" ng-maxlength="9" cc-chartype="C1" cc-error="error" ></cc-code-input>'
        }, {
            field: 'sKenGenKin',
            displayName: '原価合計金額',
            width: 218,
            cellTemplate: '<div id="sKenGenKin3_{{row.rowIndex}}" class = "text-right">{{row.getProperty(col.field) | number}}&nbsp;</div>'
        }]
    };

    /**
     * Change read-only status of controls.
     * 
     * @return true:read-only false:not read-only 
     */
    $scope.changeDisable = function() {
        return $scope.isEditable;
    }

    $scope.doAddGirdEmpty();

    /** 
     * 初期表示
     */
    $scope.initCond = function() {
        var getName = SIJP0170Init.init($scope.cond, function() {
            $scope.cond = getName;
            $scope.cond.kaisyaCd = AppConst.KAISYA_CODE;
        }, function(response) {});
    }

    /**
     * Search/Edit data from database.
     */
    $scope.doSearchEdit = function() {
        // set mode search edit
        $scope.cond.searchEdit = true;

        $scope.result = {};
        var control = SIJP0170Control.query($scope.cond, function() {
            $scope.doAddGirdEmpty();
            $scope.hasResult = true;
            $scope.isEditable = true;
            $scope.isSave = true;
            Message.showMessage(MsgConst.MSG_KEY_INFO_BUTTON_UPDATE);
            $scope.setFocus();
        }, function(response) {
            if (response.status === HttpConst.CODE_NOT_FOUND) {
                $scope.hasResult = false;
                Message.showMessage(MsgConst.MSG_KEY_DATA_NOT_EXIST);
                Client.showErrorFromServer($scope.error, response.data.errors);
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
     * Insert/Update data to database.
     */
    $scope.doInsertUpdate = function() {
        $scope.doUpdate();
    }

    /**
     * Update data to database.
     */
    $scope.doUpdate = function() {
        var updateDialog = Dialog.confirm(Message.getMessage(MsgConst.MSG_KEY_CONFIRM_BEFORE_UPDATE));
        if (updateDialog == null) {
            return;
        }
        updateDialog.result.then(function() {
            $scope.result.ruteCd = $scope.cond.ruteCd;
            $scope.result.inpNohinDate = $scope.cond.inpNohinDate;
            $scope.result.kaisyaCd = $scope.cond.kaisyaCd;
            $scope.result.jigyobuCd = $scope.cond.jigyobuCd;
            $scope.result.tenCd = $scope.cond.tenCd;

            $scope.result.msaiInfArea1 = $scope.sijp0170MsaiInfArea1Data;
            $scope.result.msaiInfArea2 = $scope.sijp0170MsaiInfArea2Data;
            $scope.result.msaiInfArea3 = $scope.sijp0170MsaiInfArea3Data;

            SIJP0170Control.save($scope.result, function() {
                // 再度検索可能な状態にする
                $scope.isNewData = false;
                $scope.isEditable = false;
                $scope.hasResult = true;
                $scope.isSave = true;
                Message.showMessage(MsgConst.MSG_KEY_UPDATE_SUCCESS);
                $scope.error = null;
                $scope.setFocus();
            }, function(response) {
                if (response.status === 400) {
                    $scope.isSave = true;
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
     * Check if have changing data on screen to Enable/Disable Save button.
     * 
     * @return true:enable false:disable
     */
    $scope.canSave = function() {

        for (var j = 0; j < 15; j++) {
            if ($scope.sijp0170MsaiInfArea1Data[j].dpyNo == undefined || ($scope.sijp0170MsaiInfArea1Data[j].dpyNo.length > 0 && $scope
                .sijp0170MsaiInfArea1Data[j].dpyNo.length < 9)) {
                $scope.isSave = true;
            }
            if ($scope.sijp0170MsaiInfArea2Data[j].dpyNo == undefined || ($scope.sijp0170MsaiInfArea2Data[j].dpyNo.length > 0 && $scope
                .sijp0170MsaiInfArea2Data[j].dpyNo.length < 9)) {
                $scope.isSave = true;
            }
            if ($scope.sijp0170MsaiInfArea3Data[j].dpyNo == undefined || ($scope.sijp0170MsaiInfArea3Data[j].dpyNo.length > 0 && $scope
                .sijp0170MsaiInfArea3Data[j].dpyNo.length < 9)) {
                $scope.isSave = true;
            }
        }

        if ($scope.isEditable && !$scope.form.$invalid && !$scope.isSave && (!angular.equals($scope.sijp0170MsaiInfArea1Data, $scope.original1Result) || !
            angular.equals($scope.sijp0170MsaiInfArea2Data, $scope.original2Result) || !angular.equals($scope.sijp0170MsaiInfArea3Data,
                $scope.original3Result))) {
            return true;
        } else {
            return false;
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

    /**
     * Clear input data of controls.
     */
    $scope.doClear = function() {
        var clearDialog = Dialog.confirm(Message.getMessage(MsgConst.MSG_KEY_CONFIRM_BEFORE_CLEAR));
        if (clearDialog == null) {
            return;
        }
        clearDialog.result.then(function() {
            if ($scope.hasResult == false && $scope.isEditable == false) {
                $scope.cond = {};
                $scope.form.$setPristine();
                $scope.initCond();
            }
            $scope.queryResult = {};
            $scope.hasResult = false;
            $scope.doAddGirdEmpty();
            $scope.isEditable = false;
            $scope.isNewData = false;
            $scope.cond.kaisyaCd = AppConst.KAISYA_CODE;
            
            $scope.error = Client.clearErrors();
            Message.showMessage(MsgConst.MSG_KEY_COMMON_INFO_MESSAGE_F9);
            $scope.setFocus();
        }, function() {
            $scope.setFocus();
        });
    }
});