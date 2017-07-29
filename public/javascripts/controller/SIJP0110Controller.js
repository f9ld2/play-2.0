// /////////////////////////////////////////////////////////////////////
// Copyright(C) 2015 NEC Solution Innovators, Ltd. All Rights Reserved.
// /////////////////////////////////////////////////////////////////////

/*
 * ====================================================================
 * 
 * 機能名称 : 伝票ヘッダ照会
 * 
 * 改版履歴
 * 
 * Rev. 改版年月日 改版者名 内容
 * 
 * 1.0 2014-03-25 tuctv 新規作成
 * 
 * ===================================================================
 */
var app = angular.module('sijp0110', ['sijp0110Services', 'ui','ui.select2','directives']);
app.controller("SIJP0110Ctrl",function($scope, $window, Message,$timeout, MsgConst,HttpConst,SIJP0110Control,SIJP0110Init,
    $translate,Dialog, DialogInfo,$interval,AppConst, FocusConst, Client) {
    Message.showMessage(MsgConst.MSG_KEY_COMMON_INFO_MESSAGE_F10);
    $scope.cond = {};
    $scope.cond.kaisyaCd = AppConst.KAISYA_CODE;
    $scope.cond.outKaisyaCd = AppConst.KAISYA_CODE;
    $scope.queryResult = {};
    $scope.pagingResult = {};
    $scope.totalServerItems = 0;
    $scope.isEditable = false;
    $scope.hasResult = false;
    $scope.isNewData = false;
    $scope.disableInit = true;
    $scope.focusCond = 1;
    $scope.focusResult = 0;
    
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
     * set info paging.
     * 
     * @param 
     * @return 
     */
    $scope.pagingOptionsSIJP0110DenInfArea = {
            pageSizes : [ 10 ],
            pageSize : 10,
            currentPage : 1
    };
     
    /**
     * set info paging.
     * 
     * @param 
     * @return 
     */
    $scope.setPagingData = function() {
        if (isEmptyObject($scope.queryResult)) {
            $scope.pagingResult = $scope.queryResult;
        }
        else {
            var page = $scope.pagingOptionsSIJP0110DenInfArea.currentPage;
            var pageSize = $scope.pagingOptionsSIJP0110DenInfArea.pageSize;
            $scope.pagingResult = $scope.queryResult.slice((page - 1) * pageSize, page * pageSize);
        }
        // if there is no apply or digest going on.
        if (!$scope.$$phase) {
            $scope.$apply();
        }
    };

    /**
     * listener pagingOptionsSIJP0110DenInfArea.currentPage is changed.
     * 
     * @param 
     * @return 
     */
    $scope.$watchCollection('[pagingOptionsSIJP0110DenInfArea.currentPage, queryResult]', function (){
        $scope.setPagingData();
    });

    /**
     * gridOptionsSIJP0110DenInfArea is generated.
     * 
     * @param 
     * @return 
     */
    $scope.gridOptionsSIJP0110DenInfArea = {
         data:'pagingResult',
         enablePaging : true,
         showFooter: true,
         footerRowHeight: 40,
         headerRowHeight: 39,
         rowHeight: 40,
         enableRowSelection: false,
         enableCellSelection: true,
         rowTemplate: rowTemplate,
         enableSorting: false,
         enableColumnResize: false,
         totalServerItems: 'totalServerItems',
         footerTemplate: '/assets/templates/footerTemplate.html',
         pagingOptions: $scope.pagingOptionsSIJP0110DenInfArea,
         columnDefs: [{field:'no', displayName:'No', width :40,
                   cellTemplate: '<div class="cs-center"><button class="cs-grid-button-center" ng-click="openLink(row.rowIndex)">{{row.entity.no }}</button></div>'},
                  
                   {field:'gDpyNo', width :75,
                   headerCellTemplate: '<div class="cs-bor-b">伝票番号</div><div>計上日</div>',
                   cellTemplate: '<div class="cs-bor-b">{{queryResult[row.rowIndex + (pagingOptionsSIJP0110DenInfArea.currentPage-1)* 10 ].dpyNo }}&nbsp</div><div class="cs-center">{{queryResult[row.rowIndex + (pagingOptionsSIJP0110DenInfArea.currentPage-1)*10 ].ruiKeijoYmd }}&nbsp</div>'},
                 
                   {field:'gTenCd', width :115,
                   headerCellTemplate: '<div class="cs-bor-b">店</div><div>店名称</div>',
                   cellTemplate: '<div class="cs-bor-b">{{queryResult[row.rowIndex + (pagingOptionsSIJP0110DenInfArea.currentPage-1)*10 ].tenCd }}&nbsp</div>'
                       +'<div>{{queryResult[row.rowIndex + (pagingOptionsSIJP0110DenInfArea.currentPage-1)*10 ].tenNm }}&nbsp</div>'},
                
                   {field:'inBmnCd', width :115,
                   headerCellTemplate: '<div class="cs-bor-b">部門</div><div>部門名称</div>',
                   cellTemplate: '<div class="cs-bor-b">{{queryResult[row.rowIndex + (pagingOptionsSIJP0110DenInfArea.currentPage-1)*10 ].inBmnCd }}&nbsp</div><div>{{queryResult[row.rowIndex + (pagingOptionsSIJP0110DenInfArea.currentPage-1)*10 ].bmnNm }}&nbsp</div>'},
                  
                   {field:'torihikiCd', width :100,
                   headerCellTemplate: '<div class="cs-bor-b">取引先</div><div>取引先名称</div>',
                   cellTemplate: '<div class="cs-bor-b">{{row.getProperty(col.field)}}&nbsp;</div><div>{{queryResult[row.rowIndex + (pagingOptionsSIJP0110DenInfArea.currentPage-1)*10 ].gtriNm }}&nbsp</div>'},
                  
                   {field:'hatYmd', width :80,
                   headerCellTemplate: '<div class="cs-bor-b">発注日</div><div>納品予定日</div>',
                   cellTemplate: '<div class="cs-bor-b cs-center">{{queryResult[row.rowIndex + (pagingOptionsSIJP0110DenInfArea.currentPage-1)*10 ].hatYmd }}&nbsp</div><div  class="cs-center">{{queryResult[row.rowIndex + (pagingOptionsSIJP0110DenInfArea.currentPage-1)*10 ].nhnYoteiYmd }}&nbsp</div>'},
                  
                   {field:'nhnYmd', width :80,
                   headerCellTemplate: '<div class="cs-bor-b">納品日</div><div>確定日</div>',
                   cellTemplate: '<div class="cs-bor-b cs-center">{{queryResult[row.rowIndex + (pagingOptionsSIJP0110DenInfArea.currentPage-1)*10 ].nhnYmd }}&nbsp</div><div class="cs-center">{{queryResult[row.rowIndex + (pagingOptionsSIJP0110DenInfArea.currentPage-1)*10 ].subLastKakuteiYmd}}&nbsp</div>'},
                  
                   {field:'skenGenkKin', displayName:'原価金額', width :92,
                   cellTemplate: '<div class = "text-right">{{row.getProperty(col.field) | number:0}}&nbsp;</div>'},
                 
                   {field:'skenBaikKin', displayName:'売価金額', width :92,
                   cellTemplate: '<div class = "text-right">{{row.getProperty(col.field) | number:0}}&nbsp;</div>'},
                 
                   {field:'tanto', width :80,
                   headerCellTemplate: '<div class="cs-bor-b">担当者</div><div>状態</div>',
                   cellTemplate: '<div class="cs-bor-b">{{queryResult[row.rowIndex + (pagingOptionsSIJP0110DenInfArea.currentPage-1)*10 ].tanto }}&nbsp</div><div>{{queryResult[row.rowIndex + (pagingOptionsSIJP0110DenInfArea.currentPage-1)*10 ].gsyoriStsKbn }}&nbsp</div>'},
                
                   {field:'subEntryKbn', displayName:'確定区分', width :60,
                   cellTemplate: '<div class="cs-center">{{queryResult[row.rowIndex + (pagingOptionsSIJP0110DenInfArea.currentPage-1)*10 ].subEntryKbn }}&nbsp</div>'}]
    };
    
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
     * init page.
     * 
     * @param 
     * @return 
     */
    $scope.initCond = function() {
        var getName = SIJP0110Init.init($scope.cond, function() {    
            $scope.cond=getName;
        }, function(response) {
            if (response.status === HttpConst.CODE_NOT_FOUND) {

                $scope.hasResult = false;
                Message.showMessage(MsgConst.MSG_KEY_DATA_NOT_EXIST);
                Client.showErrorFromServer($scope.error, response.data.errors);
            } else if (response.status === HttpConst.CODE_BAD_REQUEST) {
                // エラー処理
                $scope.hasResult = false;
                Message.showMessageWithContent(response.data.errors[0].level, response.data.errors[0].message);
                Client.showErrorFromServer($scope.error, response.data.errors);
            } else {
                Message.showMessage(MsgConst.MSG_KEY_SYSTEM_ERROR);
            }
        });
    }

    /**
     * set disable.
     * 
     * @param 
     * @return 
     */
    $scope.disable = function () {
        return $scope.disableInit;
    }

    // execute disable
    $scope.disable();

    /**
     * listener cond.dpyKbn is changed.
     * 
     * @param 
     * @return 
     */
    $scope.$watch('cond.dpyKbn', function(value) {
        if (!isEmpty(value) && value == $scope.defaultSearchHatSruiKbn) {
            $scope.disableInit = false;
        } else {
            if(value =='31'){
                $scope.disableInit = false;
                $scope.cond.mainToriCd = "";
            }
            else{
                $scope.disableInit = true;
                $scope.cond.outKaisyaCd = AppConst.KAISYA_CODE;
                $scope.cond.outJigyobuCd = "";
                $scope.cond.outTenCd = "";
            }
        }
    });

    /**
     * Action button search.
     * 
     * @param 
     * @return 
     */
    $scope.doSearch = function() {
        $scope.queryResult = {};
        $scope.totalServerItems = 0;
        var control = SIJP0110Control.query($scope.cond, function() {
            $scope.queryResult = control;
            $scope.isEditable = false;
            $scope.hasResult = true;
            $scope.totalServerItems = $scope.queryResult.length;
            $scope.pagingOptionsSIJP0110DenInfArea.currentPage = 1;
            Message.showMessage(MsgConst.MSG_KEY_PROCESS_SUCCESS);
            $scope.error = null;
            $scope.setFocus();
        },function(response) {
            if (response.status === HttpConst.CODE_NOT_FOUND) {
                $scope.hasResult = false;
                Message.showMessage(MsgConst.MSG_KEY_DATA_NOT_EXIST);
                Client.showErrorFromServer($scope.error, response.data.errors);
            } else if (response.status === HttpConst.CODE_BAD_REQUEST) {
                $scope.hasResult = false;
                // エラー処理
                Message.showMessageWithContent(response.data.errors[0].level, response.data.errors[0].message);
                Client.showErrorFromServer($scope.error, response.data.errors);
            } else {
                $scope.hasResult = false;
                Message.showMessage(MsgConst.MSG_KEY_SYSTEM_ERROR);
            }
            $scope.setFocus();
        });
    }

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
        clearDialog.result.then(function () {
            $scope.error = Client.clearErrors();

            if ($scope.hasResult == false && $scope.isEditable == false) {
                $scope.cond = {};
                $scope.form.$setPristine(); 
                $scope.initCond();
            }

            $scope.queryResult = {};
            $scope.hasResult = false;
            $scope.isEditable = false;
            $scope.cond.kaisyaCd = AppConst.KAISYA_CODE;
            $scope.cond.outKaisyaCd = AppConst.KAISYA_CODE;
            $scope.isNewData = false;
            $scope.setFocus();
            Message.showMessage(MsgConst.MSG_KEY_COMMON_INFO_MESSAGE_F10);
        },  function () {
            $scope.setFocus();
        });
    }

    /**
     * open a new link.
     * 
     * @param 
     * @return 
     */
    $scope.openLink=function(index){
        $scope.popitup($scope.queryResult[index].link);
    }

    /**
     * Validate button search.
     * 
     * @param 
     * @return 
     */
    $scope.canSearch = function() {
        if($scope.form.condForm == null) {
            return;
        }
        if ($scope.isEditable || $scope.form.condForm.$invalid) {
            return false;
        } else {
            return true;
        }
    }
});