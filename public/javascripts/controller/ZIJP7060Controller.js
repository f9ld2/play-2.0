// /////////////////////////////////////////////////////////////////////
// Copyright(C) 2014 NEC Solution Innovators, Ltd. All Rights Reserved.
// /////////////////////////////////////////////////////////////////////

/*
 * ====================================================================
 *
 * 機能名称 : 棚卸入力画面 *
 * 改版履歴
 *
 * Rev. 改版年月日 改版者名 内容
 *
 * 1.0 2015.06.24 NECVN 新規作成
 *
 * ===================================================================
 */
var app = angular.module('zijp7060', ['zijp7060Services', 'ui', 'ui.select2', 'directives']);
app.controller("ZIJP7060Ctrl", function($scope, $window, $interval, FocusConst, Message, MsgConst, HttpConst, 
        Zijp7060Control, Zijp7060Init, Zijp7060Info, $sce, Dialog, DialogInfo, AppConst, Client, UserInfo) {
	Message.showMessage(MsgConst.MSG_KEY_COMMON_INFO_MESSAGE);
    $scope.cond = {
        kaisyaCd: AppConst.KAISYA_CODE,
        tnaUnyDd: UserInfo.unyoDate,
        tnaKbn: 2,
        tanaNoTo: '',
        tanaNoFr: ''
    };
    $scope.result = {};
    $scope.queryResult = [];
    $scope.cloneQueryResult = [];
    $scope.focusCond = FocusConst.COMBOBOX_INIT_FOCUS;
    $scope.focusResult = FocusConst.NOT_FOCUS;
    $scope.hiddenBar = true;
    $scope.Const = {
        N0054_MAX_RECORD_NUMBER: '07'
    };
    
    $scope.mode = 1;
    $scope.initMode = 1;
    $scope.editMode = 3;
    $scope.searchMode = 2;
    $scope.postEditMode = 4;
    $scope.disCon = false;
    $scope.isInsert = false;
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
        if ($scope.disCon) {
            setFocusCond($scope, $interval, FocusConst.NOT_FOCUS);
            setFocusResult($scope, $interval, FocusConst.TEXTINPUT_FOCUS);
        } else {
            setFocusResult($scope, $interval, FocusConst.NOT_FOCUS);
            setFocusCond($scope, $interval, FocusConst.COMBOBOX_INIT_FOCUS);
        }
    }
    
    /**
     * Change read-only status of controls.
     *
     * @return true:read-only false:not read-only
     */
    $scope.disableResult = function() {
        return !$scope.disCon;
    }
    
    /**
     * Change read-only status of controls.
     *
     * @return true:read-only false:not read-only
     */
    $scope.disableCond = function() {
        return $scope.disCon;
    }
    
    /**
     * Reset result form.
     *
     */
    $scope.resetResultForm = function() {
        if ($scope.result != undefined) {
        	$scope.result.uriZaiSu = "";
            $scope.result.tanaNo = "";
            $scope.result.shnCd = undefined;
            $scope.form.$setPristine();
        }
    }
    
    $scope.resetError = function() {
        $scope.error = Client.clearErrors();
    }
    
    $scope.checkBoxAllClickHandler = checkBoxAllClickHandler;
    $scope.getCheckAllValue = getCheckAllValue;
    
    /**
     * Define paging options.
     */
    $scope.pagingOptions = {
        pageSize: 20,
        currentPage: 1
    };
    
    /**
     * Define cell template.
     *
     */
    var headerCellTemplate ='<div>{{col.displayName}}</div><input name="headerCheckBoxInGrid" class="checkbox" type="checkbox" ng-checked="getCheckAllValue(queryResult, pagingOptions, \'chkFlg\')" ng-click="checkBoxAllClickHandler(queryResult, pagingOptions, \'chkFlg\')" ng-disabled="disableResult()||pagingResult.dtoList.length == 0"/>';
    var checkboxCellTemplate = '<div>' + '<input  name="cellCheckBoxInGrid"  id="chkFlg_{{row.rowIndex}}"  class="checkbox" type="checkbox" ng-disabled="disableResult()" ' + ' ng-model="queryResult[row.rowIndex].chkFlg"  style="cursor: default;"  cc-error="error"/> </div>';
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
    $scope.gridOptionsZIJP7060Area = {
        data: 'queryResult',
        enablePaging: false,
        headerRowHeight: 40,
        rowHeight: 25,
        enableRowSelection: false,
        enableSorting: false,
        enableColumnResize: true,
        enableCellSelection: false,
        rowTemplate: rowTemplate,
        columnDefs: [{
            field: 'no',
            displayName: '№',
            width: 55,
            cellTemplate: '<div class="cs-center ngCellText cellText">{{row.rowIndex + 1}}</div>'
        }, {
            field: 'chkFlg',
            displayName: '削除',
            width: 30,
            headerCellTemplate: headerCellTemplate,
            cellTemplate: checkboxCellTemplate
        }, {
            field: 'tanaNo',
            displayName: '棚№',
            width: 60,
            cellTemplate: '<div class="ngCellText cellText"><span ng-cell-text>{{COL_FIELD}}</span></div>'
        }, {
            field: 'shnCd',
            displayName: 'ＪＡＮコード',
            width: 180,
            cellTemplate: '<div class="ngCellText cellText"><span ng-cell-text>{{COL_FIELD}}</span></div>'
        }, {
            field: 'shnNm',
            displayName: '商品名漢字',
            width: 470,
            cellTemplate: '<div class="ngCellText cellText"><span ng-cell-text>{{COL_FIELD}}</span></div>'
        }, {
            field: 'uriZaiSu',
            displayName: '数量',
            width: 140,
            cellClass: "text-right",
            cellTemplate: '<div class="ngCellText cellText"><span ng-cell-text>{{COL_FIELD | number:2}}</span></div>'
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
            if ($scope.mode == $scope.initMode) {
                $scope.resetCondForm();
            }
            
            $scope.resetError();
            $scope.queryResult = [];
            $scope.resetResultForm();
            $scope.form.$setPristine();
            
            Message.showMessage(MsgConst.MSG_KEY_COMMON_INFO_MESSAGE);
            $scope.mode = $scope.initMode;
            $scope.disCon = false;
            $scope.isInsert = false;
            
            $scope.setFocus();
        }, function() {
            $scope.setFocus();
        })
    }
    
    /**
     * Reset condition form.
     *
     */
    $scope.resetCondForm = function() {
        if ($scope.cond != undefined) {
            $scope.cond.kaisyaCd = AppConst.KAISYA_CODE;
            $scope.cond.tnaUnyDd = UserInfo.unyoDate;
            $scope.cond.tnaKbn = 2;
            $scope.cond.tanaNoTo = '';
            $scope.cond.tanaNoFr = '';
        }
        
        $scope.initCond();
    };
    
    /**
     * Action button search.
     *
     * @param
     * @return
     */
    $scope.doSearch = function() {
        $scope.resetResultForm();
        $scope.resetError();
        
        var showResult = Zijp7060Control.query($scope.cond, function() {
            $scope.disCon = false;
            $scope.mode = $scope.searchMode;
            $scope.queryResult = showResult[0].dtoList;
            $scope.pagingOptions.pageSize = $scope.queryResult.length;
            
            Message.showMessage(MsgConst.MSG_KEY_COMMON_INFO_MESSAGE_REPORT);
            $scope.setFocus();
        }, function(response) {
            if (response.status === HttpConst.CODE_NOT_FOUND) {
            	$scope.disCon = false;
                $scope.mode = $scope.initMode;
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
     * Search/Edit data from database.
     */
    $scope.doSearchEdit = function() {
    	$scope.resetResultForm();
        $scope.resetError();
        
        var showResult = Zijp7060Control.query($scope.cond, function() {
            $scope.disCon = true;
            $scope.isInsert = false;
            $scope.mode = $scope.editMode;
            $scope.queryResult = showResult[0].dtoList;
            $scope.cloneQueryResult = angular.copy($scope.queryResult);
            $scope.pagingOptions.pageSize = $scope.queryResult.length;
            
            Message.showMessage(MsgConst.MSG_KEY_COMMON_INFO_MESSAGE_REPORT);
            $scope.setFocus();
        }, function(response) {
            if (response.status === HttpConst.CODE_NOT_FOUND) {
            	$scope.disCon = true;
            	$scope.isInsert = true;
                $scope.mode = $scope.editMode;
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
     * Check controls of the condForm area to Enable/Disable Search button.
     *
     * @return true:enable false:disable
     */
    $scope.canSearch = function() {
        return ($scope.form.condForm.$valid && $scope.mode != $scope.editMode);
    };
    
    /**
     * Check controls of the condForm area to Enable/Disable SearchEdit button.
     *
     * @return true:enable false:disable
     */
    $scope.canSearchEdit = function() {
        return ($scope.form.condForm.$valid && $scope.mode != $scope.editMode);
    };
    
    /**
     * Check controls of the filter area to Enable/Disable Save button.
     *
     * @return true:enable false:disable
     */
    $scope.canGridSave = function() {
        return ($scope.form.resultForm != undefined && $scope.form.resultForm.$valid);
    }
    
    /**
     * Save data at grid area.
     *
     */
    $scope.gridSave = function() {
    	var i = 0;
        var isExists = false;
        
        var param = {   
            'shnCd': $scope.result.shnCd,
            'tanaNo': $scope.result.tanaNo,
            'uriZaiSu': $scope.result.uriZaiSu,
            'jigyobuCd': $scope.cond.jigyobuCd
        };
        
        for (i = 0; i < $scope.queryResult.length; i++) {
            if (param.tanaNo == $scope.queryResult[i].tanaNo && param.shnCd == $scope.queryResult[i].shnCd) {
                isExists = true;
                break;
            }
        }
        
        if (isExists) {
            if ($scope.queryResult[i].chkFlg) {
               $scope.queryResult[i].chkFlg = false;
                $scope.queryResult[i].uriZaiSu = param.uriZaiSu;
                
                $scope.resetResultForm();
                $scope.resetError();
                $scope.setFocus();
            } else {
                Message.showMessage(MsgConst.MSG_KEY_ALREADY_REGISTERED);
                $scope.setFocus();
            }
        } else {
            $scope.disCon = false;
            
            var finder = Zijp7060Info.query(param, function() {
                $scope.disCon = true;
                if (finder != undefined) {
                    $scope.queryResult.unshift({});
                    $scope.queryResult[0].chkFlg = false;
                    $scope.queryResult[0].shnCd = $scope.result.shnCd;
                    $scope.queryResult[0].shnNm = finder[0].shnNm;
                    $scope.queryResult[0].tanaNo = $scope.result.tanaNo;
                    $scope.queryResult[0].uriZaiSu = $scope.result.uriZaiSu;
                    
                    $scope.resetResultForm();
                    $scope.resetError();
                    
                    $scope.pagingOptions.pageSize = $scope.queryResult.length;
                    Message.showMessage(MsgConst.MSG_KEY_INFO_BUTTON_SAVE);
                    $scope.setFocus();
                }
            }, function(response) {
            	$scope.disCon = true;
            	if (response.status === HttpConst.CODE_NOT_FOUND) {
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
    }
    
    /**
     * Validate button save
     *
     * @param
     * @return
     */
    $scope.canSave = function() {
    	var modified = false;
        for (var i = 0; i < $scope.queryResult.length; i++) {
            if (isModified($scope.queryResult[i]) != -1) {
                modified = true;
            }
        }
        return ($scope.mode == $scope.editMode && modified);
    }
    
    /**
     * Prepare grid data to update.
     *
     * @return updating data
     */
    $scope.prepareSaveList = function() {
        var modifiedData = {};
        modifiedData.kaisyaCd = $scope.cond.kaisyaCd;
        modifiedData.jigyobuCd = $scope.cond.jigyobuCd;
        modifiedData.tnaUnyDd = $scope.cond.tnaUnyDd;
        modifiedData.tanaNoFr = $scope.cond.tanaNoFr;
        modifiedData.tanaNoTo = $scope.cond.tanaNoTo;
        modifiedData.tnaKbn = $scope.cond.tnaKbn;
        modifiedData.tenCd = $scope.cond.tenCd;
        modifiedData.maxRecordNumber = $scope.getMaxRecordNumber($scope.Const.N0054_MAX_RECORD_NUMBER);
        
        modifiedData.dtoList = [];
        
        for (var i = 0; i < $scope.queryResult.length; i++) {
            if (isModified($scope.queryResult[i]) == 1) {
                $scope.queryResult[i].updateFlag = true;
                $scope.queryResult[i].rowNo = i;
                modifiedData.dtoList.push($scope.queryResult[i]);
            }
            if (isModified($scope.queryResult[i]) == 2) {
                $scope.queryResult[i].updateFlag = false;
                $scope.queryResult[i].rowNo = i;
                modifiedData.dtoList.push($scope.queryResult[i]);
            }
        }
        
        return modifiedData;
    }
    
    /**
     * Insert data to database.
     */
    $scope.doSave = function() {
        Dialog.confirm(Message.getMessage(($scope.isInsert) ? MsgConst.MSG_KEY_CONFIRM_BEFORE_INSERT : MsgConst.MSG_KEY_CONFIRM_BEFORE_UPDATE)).result.then(function() {
        	var control = Zijp7060Control.update($scope.prepareSaveList(), function(response) {
                //success
                $scope.mode = $scope.postEditMode;
                $scope.disCon = false;
                $scope.resetError();
                
                Message.showMessage(($scope.isInsert) ? MsgConst.MSG_KEY_INSERT_SUCCESS : MsgConst.MSG_KEY_UPDATE_SUCCESS);
                if (!isEmpty(control.errors)) {
                    Message.showMessageWithContent(control.errors[0].level, control.errors[0].message);
                    Message.showMessageWithContent(control.errors.errors[0].level, control.errors.errors[0].message);
                    $scope.error = Client.showErrorFromServer($scope.error, control.errors.errors);
                }
                
                $scope.setFocus();
                $scope.form.$setPristine();
            }, function(response) {
                if (response.status === HttpConst.CODE_NOT_FOUND) {
                    Message.showMessage(MsgConst.MSG_KEY_DATA_NOT_EXIST);
                } else if (response.status === HttpConst.CODE_BAD_REQUEST) {
                    Message.showMessageWithContent(response.data.errors[0].level, response.data.errors[0].message);
                    $scope.error = Client.showErrorFromServer($scope.error, response.data.errors);
                } else {
                    Message.showMessage(MsgConst.MSG_KEY_SYSTEM_ERROR);
                }
                $scope.setFocus();
            });
        }, function() {
            $scope.setFocus();
        })
    }
    
    /**
     * function to check whether a record is modified or not
     *
     * @param record the record to check
     * @return boolean, true: the record is modified, false: the record is not modified
     */
    var isModified = function(record) {
        if (isEmpty(record)) {
            return -1;
        }
        
        var i = 0;
        var cloneRecord = undefined;
        
        for (i = 0; i < $scope.cloneQueryResult.length; i++) {
            if (record.tanaNo == $scope.cloneQueryResult[i].tanaNo && record.shnCd == $scope.cloneQueryResult[i].shnCd) {
                cloneRecord = $scope.cloneQueryResult[i];
                break;
            }
        }
        
        // new record
        if (cloneRecord == undefined) {
            return 2;
        }
        
        if (record.chkFlg == $scope.cloneQueryResult[i].chkFlg && record.tanaNo == $scope.cloneQueryResult[i].tanaNo && record.shnCd == $scope.cloneQueryResult[i].shnCd && record.uriZaiSu == $scope.cloneQueryResult[i].uriZaiSu) {
        	return -1;
        } else {
            return 1;
        }
    }
    
    /**
     * Initial screen
     */
    $scope.initCond = function() {
    	$scope.cond.maxRecordNumber = $scope.getMaxRecordNumber($scope.Const.N0054_MAX_RECORD_NUMBER);
        var maker = Zijp7060Init.query({}, function() {
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
});