///////////////////////////////////////////////////////////////////////
//Copyright(C) 2015 NEC Solution Innovators, Ltd. All Rights Reserved.
///////////////////////////////////////////////////////////////////////
/*
 * ====================================================================
 * 機能名称 : オリジナル商品品振確定指示画面
 * 改版履歴 Rev. 改版年月日 改版者名 内容
 * 1.0 2015-06-08 NECVN 新規作成
 * ===================================================================
 */
var app = angular.module('sijp7080', ['sijp7080Services', 'ui', 'ui.select2', 'directives']);
app.controller("SIJP7080Ctrl", function($scope, Message, $interval, MsgConst, HttpConst, SIJP7080, $http, $filter,
        Dialog, DialogInfo, AppConst, FocusConst, Client, UserInfo, SIJP7080Init ) {
    Message.showMessage(MsgConst.MSG_KEY_COMMON_INFO_MESSAGE_F9);
    $scope.cond = {
        kaisyaCd: AppConst.KAISYA_CODE
    };
    $scope.result = {};
    $scope.queryResult = [];
    $scope.originalQueryResult = [];
    $scope.cloneQueryResult = [];
    $scope.mode = 1;
    $scope.initMode = 1;
    $scope.searchMode = 2;
    $scope.postSaveMode = 3;
    $scope.errorsTable = [];
    $scope.focusCond = FocusConst.TEXTINPUT_FOCUS;
    $scope.focusResult = FocusConst.NOT_FOCUS;
    $scope.isEditable = false;
    $scope.hasResult = false;
    $scope.Const = {
            N0054_MAX_RECORD_NUMBER: '01'
        };
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
        return $scope.focusResult;
    }

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
     * Process data of grid whenever the sijp0170MsaiInfArea1Data changes.
     */
    $scope.$watch('queryResult', function() {
        
        $scope.getDataGridChange($scope.queryResult);
    }, true);
    
    /**
     * getDataGridChange
     * @param resultlist data of grid
     */
    $scope.getDataGridChange = function(resultlist) {
        if ($scope.mode != $scope.searchMode) return;
        
        // do update
        for (var i = 0; i < resultlist.length; i++) {
            if (resultlist[i].appHinSu != $scope.cloneQueryResult[i].appHinSu){
                // if total is zero, reset all detail row
                if (resultlist[i].appHinSu == 0){
                    for(var j=0; j< resultlist[i].detailList.length; j++)
                    {
                        if (resultlist[i].detailList[j].hatChuFlg != '2' || resultlist[i].detailList[j].hatSimeFlg != '2'){
                            resultlist[i].detailList[j].appHinSu = 0;
                            $scope.cloneQueryResult[i].detailList[j].appHinSu = 0;
                            resultlist[i].detailList[j].saSu = resultlist[i].detailList[j].hatSu - resultlist[i].detailList[j].appHinSu;
                        }
                    }
                }
                resultlist[i].saSu = resultlist[i].hatSu - resultlist[i].appHinSu;
                $scope.cloneQueryResult[i].appHinSu = resultlist[i].appHinSu;
                break;
            }
            for(var j=0; j< resultlist[i].detailList.length; j++)
            {
                if (resultlist[i].detailList[j].appHinSu != $scope.cloneQueryResult[i].detailList[j].appHinSu){
                    $scope.cloneQueryResult[i].detailList[j].appHinSu = resultlist[i].detailList[j].appHinSu;
                    // sum all detail row
                    var total = 0;
                    for(var k=0; k< resultlist[i].detailList.length; k++){
                        if (resultlist[i].detailList[k].appHinSu != "") {
                            total =  total + resultlist[i].detailList[k].appHinSu;
                        }
                    }
                    resultlist[i].appHinSu = total;
                    $scope.cloneQueryResult[i].appHinSu = resultlist[i].appHinSu;
                    // subtract value 差 
                    resultlist[i].detailList[j].saSu = resultlist[i].detailList[j].hatSu - resultlist[i].detailList[j].appHinSu;
                    resultlist[i].saSu = resultlist[i].hatSu -resultlist[i].appHinSu;
                    break;
                }
            }
        }
    }
    
    /**
     * Reset error 
     *
     */
    $scope.resetError = function() {
        $scope.errorsTable = [];
        $scope.error = Client.clearErrors();
    }
    
    
    /**
     * Check box cell change 
     *
     */
    $scope.checkBoxCell = function(index) {
        if ($scope.errorsTable != undefined && index < $scope.errorsTable.length) {
            $scope.errorsTable[i] == {};
        }
    }

    $scope.checkBoxAllClickHandler = checkBoxAllClickHandler;
    $scope.getCheckAllValue = getCheckAllValue;

    /**
     * Define cell template.
     *
     */
    var headerCellTemplate =
        '<div class="ngHeaderText cc-header-text">{{col.displayName}}</div>';

    var customdHeaderCellTemplate1 =
        '<div class="headerTopSijp7080 ngHeaderText cc-header-text">JANコード</div><div class="ngHeaderText cc-header-text">発注日</div>';
    var customdHeaderCellTemplate2 =
        '<div class="headerTopSijp7080 ngHeaderText cc-header-text">メーカ品番</div><div class="ngHeaderText cc-header-text">店舗</div>';
    var customdHeaderCellTemplate3 =
        '<div class="headerTopSijp7080 ngHeaderText cc-header-text">商品名</div><div class="ngHeaderText cc-header-text">店名</div>';
    /**
     * Define cell template.
     *
     */
    var checkboxCellTemplate = '<br/><div  ng-class="{errorCellCheckbox: (errorsTable.length > 0 && errorsTable[row.rowIndex].chkFlg == true)}">' + '<input  name="cellCheckBoxInGrid"  id="chkFlg_{{row.rowIndex}}"  class="gridSijp7080Checkbox checkbox" type="checkbox" ng-disabled="!changeDisable()" ng-click="checkBoxCell(row.rowIndex)" ' + ' ng-model="queryResult[row.rowIndex].chkFlg"   style="cursor: default;"  cc-error="error"/> </div>';
    var rowTemplate = '<div ng-repeat="col in renderedColumns" ng-class="col.colIndex()" class="gridStyleSijp7080Padding0 ngCell {{col.cellClass}}"><div class="ngVerticalBar" ng-style="{height: rowHeight}" ng-class="{ ngVerticalBarVisible: !$last }">&nbsp;</div><div ng-cell-custom class="gridSijp7080Cell"></div></div>';

    var appHinSuCellTemplate = '<div class="ngCellSijp7080Input ngCellSijp7080Number" ng-if="row.rowIndex==0"> \
        <cc-number-input cc-label="" cc-partition="" \
            id="appHinSu_{{row.rowIndex}}" name="appHinSu_{{row.rowIndex}}" \
            ng-model="row.entity.appHinSu" cc-readonly="!changeDisable()" cc-focus="getFocusResult()" cc-required="false" cc-min="0.0" \
            cc-width="77" ></cc-number-input> \
    </div> \
    <div class="ngCellSijp7080Input ngCellSijp7080Number" ng-if="row.rowIndex!=0"> \
        <cc-number-input cc-label="" cc-partition="" \
            id="appHinSu_{{row.rowIndex}}" name="appHinSu_{{row.rowIndex}}" \
            ng-model="row.entity.appHinSu" cc-readonly="!changeDisable()" cc-required="false" cc-min="0.0" \
            cc-width="77" ></cc-number-input> \
    </div> \
    <div class="ngCellSijp7080DetailInput ngCellSijp7080Number" \
        ng-repeat="item in row.entity.detailList"> \
        <div ng-if="item.hatChuFlg == \'2\' && item.hatSimeFlg == \'2\'"> \
        <cc-number-input cc-readonly="true" cc-label="" \
            cc-partition="" id="appHinSu_{{row.rowIndex}}_{{$index}}" \
            name="appHinSu_{{row.rowIndex}}_{{$index}}" \
            ng-model="item.appHinSu" cc-required="false" cc-min="0" cc-max="99999.9" cc-width="77"></cc-number-input> \
        </div>\
        <div ng-if="item.hatChuFlg != \'2\' || item.hatSimeFlg != \'2\'"> \
        <cc-number-input cc-readonly="!changeDisable()" cc-label="" \
            cc-partition="" id="appHinSu_{{row.rowIndex}}_{{$index}}" \
            name="appHinSu_{{row.rowIndex}}_{{$index}}" \
            ng-model="item.appHinSu" cc-required="false" cc-min="0" cc-max="99999.9" cc-width="77"></cc-number-input> \
        </div> \
    </div>';
    /**
     * Initialize the grid options.
     */
    $scope.gridOptionsSIJP7080Area = {
        data: 'queryResult',
        enableCellSelection: true,
        rowTemplate: rowTemplate,
        headerRowHeight: 40.0,
        rowHeight: 19.0,
        enableRowSelection: false,
        enableColumnResize: false,
        enableSorting: false,
        useExternalSorting: false,
        columnDefs: [{
            field: 'chkFlg',
            displayName: '確定',
            width: 34,
            headerCellTemplate: headerCellTemplate,
            cellTemplate: checkboxCellTemplate,
            sortable: false
        }, {
            displayName: '',
            width: 112,
            headerCellTemplate: customdHeaderCellTemplate1,
            cellClass: "cell",
            cellTemplate: '<div class="ngCellText ngCellSijp7080"><span ng-cell-text>{{row.entity.janCd}}&nbsp;</span></div><div class="ngCellSijp7080Date" ng-repeat="item in row.entity.detailList">{{item.hatDD|ccStrDateFilter}}</div>'
        }, {
            displayName: '',
            width: 66,
            headerCellTemplate: customdHeaderCellTemplate2,
            cellClass: "cell",
            cellTemplate: '<div class="ngCellSijp7080">{{row.entity.mkdCd}}&nbsp;</div><div class="ngCellSijp7080" ng-repeat="item in row.entity.detailList">{{item.tenCd}}</div>'
        }, {
            displayName: '',
            width: 246,
            headerCellTemplate: customdHeaderCellTemplate3,
            cellClass: "cell",
            cellTemplate: '<div class="ngCellSijp7080">{{row.entity.shnNm}}&nbsp;</div><div class="ngCellSijp7080" ng-repeat="item in row.entity.detailList">{{item.tenNm}}</div>'
        }, {
            displayName: '発注数',
            width: 66,
            headerCellTemplate: headerCellTemplate,
            cellClass: "cell",
            cellTemplate: '<div class="hatSuWidth ngCellText ngCellSijp7080Number">{{row.entity.hatSu|number:1}}</div><div class="ngCellSijp7080Number" ng-repeat="item in row.entity.detailList">{{item.hatSu|number:1}}</div>'
        }, {
            displayName: '品振数',
            width: 80,
            headerCellTemplate: headerCellTemplate,
            cellClass: "cell",
            cellTemplate: appHinSuCellTemplate
        }, {
            displayName: '差',
            width: 70,
            headerCellTemplate: headerCellTemplate,
            cellClass: "cell",
            cellTemplate: '<div class="saSuWidth ngCellText ngCellSijp7080Number">{{row.entity.saSu|number:1}}</div><div class="ngCellSijp7080Number" ng-repeat="item in row.entity.detailList">{{item.saSu|number:1}}</div>'
        }, {
            displayName: '前日在庫',
            width: 90,
            headerCellTemplate: headerCellTemplate,
            cellClass: "cell",
            cellTemplate: '<div class="genkWidth ngCellText ngCellSijp7080Number">{{row.entity.zikSu|number:2}}</div><div class="ngCellSijp7080Number" ng-repeat="item in row.entity.detailList">{{item.zikSu|number:2}}</div>'
        }, {
            displayName: '原単価',
            width: 90,
            headerCellTemplate: headerCellTemplate,
            cellClass: "cell",
            cellTemplate: '<div class="genkWidth ngCellText ngCellSijp7080Number">{{row.entity.genk|number:2}}</div><div class="ngCellSijp7080Number" ng-repeat="item in row.entity.detailList">{{item.genk|number:2}}</div>'
        }, {
            displayName: '売単価',
            width: 70,
            headerCellTemplate: headerCellTemplate,
            cellClass: "cell",
            cellTemplate: '<div class="baikWidth ngCellText ngCellSijp7080Number"><span ng-cell-text>{{row.entity.baik|number}}</span></div><div class="ngCellSijp7080Number" ng-repeat="item in row.entity.detailList"><span ng-cell-text>{{item.baik|number}}</span></div>'
        }]
    };


    /**
     * function to check whether a record is modified or not
     *
     * @param index of the record to check
     * @return boolean, true: the record is modified, false: the record is not modified
     */
    var isModified = function(index, indexDetail) {
        var isModified = false
        
        if ($scope.queryResult[index].detailList[indexDetail].appHinSu 
                != $scope.originalQueryResult[index].detailList[indexDetail].appHinSu){
            isModified = true;
        }
        
        return isModified && $scope.queryResult[index].chkFlg;
    }

    /**
     * Reset condition form.
     *
     */
    $scope.resetCondForm = function() {
        if ($scope.cond != undefined) {
            $scope.cond.hatDayFr = "";
            $scope.cond.hatDayTo = "";
            $scope.initCond();
        }
    };

    /**
     * Reset result form.
     *
     */
    $scope.resetResultForm = function() {
        $scope.queryResult = [];
    }

    /**
     * Check controls of the condForm area to Enable/Disable SearchEdit button.
     *
     * @return true:enable false:disable
     */
    $scope.canSearchEdit = function() {
        return ($scope.form.condForm.$valid && $scope.mode != $scope.searchMode);
    };

    /**
     * Check if have changing data on screen to Enable/Disable Save button.
     *
     * @return true:enable false:disable
     */
    $scope.canSave = function() {
        var modified = false;
        for (var i = 0; i < $scope.queryResult.length; i++) {
            for (var j = 0; j < $scope.queryResult[i].detailList.length; j++) {
                if (isModified(i,j)) {
                    modified = true;
                }
            }
        }

        return ($scope.isEditable && $scope.mode == $scope.searchMode && modified);
    }

    /**
     * Search/Edit data from database.
     */
    $scope.doSearchEdit = function() {
        var showResult = SIJP7080.query($scope.cond, function() {
            $scope.resetResultForm();
            $scope.resetError();
            $scope.queryResult = showResult;
            $scope.cloneQueryResult = angular.copy($scope.queryResult);
            $scope.originalQueryResult = angular.copy($scope.queryResult);
            $scope.hasResult = true;
            $scope.isEditable = true;
            $scope.mode = $scope.searchMode;
            Message.showMessage(MsgConst.MSG_KEY_INFO_BUTTON_UPDATE);
            $scope.setFocus();
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
        });
    }

    /**
     * Prepare grid data to update.
     *
     * @return updating data
     */
    $scope.prepareSaveList = function() {
        var modifiedData = {};
        modifiedData.hatDayFr = $scope.cond.hatDayFr;
        modifiedData.hatDayTo = $scope.cond.hatDayTo;
        modifiedData.shouninKbn = $scope.cond.shouninKbn;
        modifiedData.jigyobuCd = $scope.cond.jigyobuCd;
        modifiedData.records = [];
        
        for (var i = 0; i < $scope.queryResult.length; i++) {
            var record = {};
            record.rowNo = i;
            record.hatSu = $scope.queryResult[i].hatSu;
            record.zikSu = $scope.queryResult[i].zikSu;
            record.appHinSu = $scope.queryResult[i].appHinSu;
            record.janCd = $scope.queryResult[i].janCd;
            record.detailList = [];
            for (var j = 0; j < $scope.queryResult[i].detailList.length; j++) {
                if (isModified(i,j)){
                    var detail = {};
                    detail.rowNo = j;
                    detail.hatSu = $scope.queryResult[i].detailList[j].hatSu;
                    detail.zikSu = $scope.queryResult[i].detailList[j].zikSu;
                    detail.appHinSu = $scope.queryResult[i].detailList[j].appHinSu;
                    
                    detail.hinAppFlg = $scope.queryResult[i].detailList[j].hinAppFlg;
                    detail.kaiCd = $scope.queryResult[i].detailList[j].kaiCd;
                    detail.hatDD = $scope.queryResult[i].detailList[j].hatDD;
                    detail.nhnDd = $scope.queryResult[i].detailList[j].nhnDd;
                    detail.hatSruiKbn = $scope.queryResult[i].detailList[j].hatSruiKbn;
                    detail.bin = $scope.queryResult[i].detailList[j].bin;
                    detail.tenCd = $scope.queryResult[i].detailList[j].tenCd;
                    
                    record.detailList[j] = detail;
                }
            }
            if (record.detailList.length > 0) {
                modifiedData.records[i] = record;
            }
        }

        return modifiedData;
    }
    /**
     * Update data to database.
     */
    $scope.doSave = function() {
        var updateConfirmDialog = Dialog.confirm(Message.getMessage(MsgConst.MSG_KEY_CONFIRM_BEFORE_UPDATE));
        if (updateConfirmDialog == null) {
            return;
        }
        updateConfirmDialog.result.then(function () {
            var control = SIJP7080.update($scope.prepareSaveList(), function(response) {
                //success
                $scope.resetError();
                $scope.isEditable = false;
                $scope.mode = $scope.postSaveMode;

                Message.showMessage(MsgConst.MSG_KEY_UPDATE_SUCCESS);
                if (!isEmpty(control.errors)) {
                    Message.showMessageWithContent(control.errors[0].level, control.errors[0].message);
                    Message.showMessageWithContent(control.errors.errors[0].level, control.errors.errors[0].message);
                    $scope.error = Client.showErrorFromServer($scope.error, control.errors.errors);

                    $scope.errorsTable = control.gridErrors;
                }
                $scope.setFocus();
                $scope.form.$setPristine();
            }, responseHandling);
        }, function() {
            $scope.setFocus();
        })
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
     * error reponse process
     * 
     */
    var responseHandling = function(response) {
        if (response.status === HttpConst.CODE_NOT_FOUND) {
            Message.showMessage(MsgConst.MSG_KEY_DATA_NOT_EXIST);
        } else if (response.status === HttpConst.CODE_BAD_REQUEST) {
            // エラー処理
            Message.showMessageWithContent(response.data.errors[0].level, response.data.errors[0].message);
            $scope.error = Client.showErrorFromServer($scope.error, response.data.errors);
            $scope.errorsTable = response.data.gridErrors;
        } else {
            Message.showMessage(MsgConst.MSG_KEY_SYSTEM_ERROR);
        }
        $scope.setFocus();
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

            $scope.resetError();
            $scope.resetResultForm();
            $scope.form.$setPristine();
            $scope.info = null;
            $scope.isInsert = false;
            $scope.hasResult = false;
            $scope.isEditable = false;
            $scope.error = Client.clearErrors();

            Message.showMessage(MsgConst.MSG_KEY_COMMON_INFO_MESSAGE_F9);
            $scope.mode = $scope.initMode;

            $scope.setFocus();
        }, function() {
            $scope.setFocus();
        })
    }

    /**
     * control reset error
     */
    $scope.$on('ccResetServerClientError', function(event, controlName) {
        $scope.error = Client.removeAllErrorByName($scope.error, controlName);
    });
    
    /**
     * Initial screen
     */
    $scope.initCond = function() {
        $scope.cond.maxRecordNumber = $scope.getMaxRecordNumber($scope.Const.N0054_MAX_RECORD_NUMBER);
        var maker = SIJP7080Init.query({}, function() {
            if (maker != undefined) {
                $scope.cond.jigyobuCd = maker[0].jigyobuCd;
                $scope.cond.shouninKbn = maker[0].shouninKbn;
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