// /////////////////////////////////////////////////////////////////////
// Copyright(C) 2015 NEC Solution Innovators, Ltd. All Rights Reserved.
// /////////////////////////////////////////////////////////////////////

/*
 * ====================================================================
 * 
 * 機能名称 : 伝票明細履歴照会
 * 
 * 改版履歴
 * 
 * Rev. 改版年月日 改版者名 内容
 * 
 * 1.0 2014-03-28 TUCTV 新規作成
 * 
 * ===================================================================
 */
var app = angular.module('sijp0130', ['sijp0130Services', 'ui','ui.select2','directives']);
app.controller("SIJP0130Ctrl",function($scope, $window ,$timeout, Message, MsgConst, HttpConst,
        SIJP0130Control,SIJP0130Init,$translate, Dialog, DialogInfo,$interval,AppConst, FocusConst, Client) {
    Message.showMessage(MsgConst.MSG_KEY_COMMON_INFO_MESSAGE_F10);
    $scope.cond = {};
    $scope.cond.kaisyaCd = AppConst.KAISYA_CODE;
    $scope.cond.outKaisyaCd =AppConst.KAISYA_CODE;
    $scope.queryResult = {};
    $scope.pagingResult = {};
    $scope.totalServerItems = 0;
    $scope.isEditable = false;
    $scope.hasResult = false;
    $scope.isNewData = false;

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

    $scope.disableInit = true;
    var rowTemplate = '<div ng-repeat="col in renderedColumns" ng-class="col.colIndex()" class="ngCell {{col.cellClass}}"><div class="ngVerticalBar" ng-style="{height: rowHeight}" ng-class="{ ngVerticalBarVisible: !$last }">&nbsp;</div><div ng-cell-custom></div></div>';

    /**
     * set info paging.
     * 
     * @param 
     * @return 
     */
    $scope.pagingOptionsSIJP0130DenpyoArea = {
            pageSizes:[ 10 ],
            pageSize : 10,
            currentPage: 1
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
            var page = $scope.pagingOptionsSIJP0130DenpyoArea.currentPage;
            var pageSize = $scope.pagingOptionsSIJP0130DenpyoArea.pageSize;
            $scope.pagingResult = $scope.queryResult.slice((page - 1) * pageSize, page * pageSize);
        }
        // if there is no apply or digest going on.
        if (!$scope.$$phase) {
            $scope.$apply();
        }
    };

    /**
     * listener pagingOptionsSIJP0130DenpyoArea.currentPage is changed.
     * 
     * @param 
     * @return 
     */
    $scope.$watchCollection('[pagingOptionsSIJP0130DenpyoArea.currentPage, queryResult]', function (){
        $scope.setPagingData();
    });

    /**
     * gridOptionsSIJP0130DenpyoArea is generated.
     * 
     * @param 
     * @return 
     */
    $scope.gridOptionsSIJP0130DenpyoArea = {
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
        pagingOptions: $scope.pagingOptionsSIJP0130DenpyoArea,
        columnDefs: [
                   {field:'insdd', width :75,
                   headerCellTemplate: '<div class="cs-bor-b">入力日</div><div>時刻</div>',
                   cellTemplate: '<div class="cs-bor-b">{{queryResult[row.rowIndex + (pagingOptionsSIJP0130DenpyoArea.currentPage-1)*10 ].insdd }}&nbsp;</div>'
                                 +' <div class="text-center">{{queryResult[row.rowIndex + (pagingOptionsSIJP0130DenpyoArea.currentPage-1)*10 ].instime }}&nbsp;</div>'},
                 
                   {field:'gDpyNo',  width :75,
                   headerCellTemplate: '<div class="cs-bor-b">伝票番号</div><div>計上日</div>',
                   cellTemplate: '<div class="cs-bor-b">{{queryResult[row.rowIndex + (pagingOptionsSIJP0130DenpyoArea.currentPage-1)*10 ].dpyNo }}&nbsp;</div>'
                       +'<div>{{queryResult[row.rowIndex + (pagingOptionsSIJP0130DenpyoArea.currentPage-1)*10 ].ruiKeijoYmd }}&nbsp;</div>'},
                   
                 
                   {field:'wTenCd', displayName:'店',  width :110,
                   cellTemplate: '<div class="cs-bor-b">{{queryResult[row.rowIndex + (pagingOptionsSIJP0130DenpyoArea.currentPage-1)*10 ].tenCd }}&nbsp;</div>'
                       +'<div>{{queryResult[row.rowIndex + (pagingOptionsSIJP0130DenpyoArea.currentPage-1)*10 ].subTenNmR1 }}&nbsp;</div>'},
                 
                   {field:'wBmnCd', displayName:'部門',  width :110,
                   cellTemplate: '<div class="cs-bor-b">{{queryResult[row.rowIndex + (pagingOptionsSIJP0130DenpyoArea.currentPage-1)*10 ].subBmnCd }}&nbsp;</div>'
                       +'<div>{{queryResult[row.rowIndex + (pagingOptionsSIJP0130DenpyoArea.currentPage-1)*10 ].subBmnNmR }}&nbsp;</div>'},
                 
                   {field:'wTriCd',displayName:'取引先',  width :110,
                   cellTemplate: '<div class="cs-bor-b">{{queryResult[row.rowIndex + (pagingOptionsSIJP0130DenpyoArea.currentPage-1)*10 ].subTriCd }}&nbsp;</div>'
                       +'<div>{{queryResult[row.rowIndex + (pagingOptionsSIJP0130DenpyoArea.currentPage-1)*10 ].subTriNmR }}&nbsp;</div>'},
                 
                   {field:'nhnYoteiYmd',  width :80,
                   headerCellTemplate: '<div class="cs-bor-b">納品日</div><div>確区</div>',
                   cellTemplate: '<div class="cs-bor-b">{{queryResult[row.rowIndex + (pagingOptionsSIJP0130DenpyoArea.currentPage-1)*10 ].nhnYoteiYmd }}&nbsp;</div>'
                       +'<div>{{queryResult[row.rowIndex + (pagingOptionsSIJP0130DenpyoArea.currentPage-1)*10 ].entryKbn }}&nbsp;</div>'},
                 
                   {field:'dispSyoriSts', displayName:'処理区分', width :100,
                   cellTemplate: '<div>{{queryResult[row.rowIndex + (pagingOptionsSIJP0130DenpyoArea.currentPage-1)*10 ].dispSyoriSts }}&nbsp;</div>'},
                 
                   {field:'atoGenkgk',  width :100,
                   headerCellTemplate: '<div class="cs-bor-b">原価金額</div><div>変更前原価</div>',
                   cellTemplate: '<div class = "cs-bor-b text-right">&nbsp;{{queryResult[row.rowIndex + (pagingOptionsSIJP0130DenpyoArea.currentPage-1)*10 ].atoGenkgk  | number:0}}</div>'
                       +'<div class = "text-right">&nbsp;{{queryResult[row.rowIndex + (pagingOptionsSIJP0130DenpyoArea.currentPage-1)*10 ].maeGenkgk | number:0}}</div>'},
                 
                   {field:'atoBaikgk',  width :100,
                   headerCellTemplate: '<div class="cs-bor-b">売価金額</div><div>変更前売価</div>',
                   cellTemplate: '<div class = "cs-bor-b text-right">&nbsp;{{queryResult[row.rowIndex + (pagingOptionsSIJP0130DenpyoArea.currentPage-1)*10 ].atoBaikgk  | number:0}}</div>'
                       +'<div class = "text-right">&nbsp;{{queryResult[row.rowIndex + (pagingOptionsSIJP0130DenpyoArea.currentPage-1)*10 ].maeBakgk  | number:0}}</div>'},
                 
                   {field:'cmnTantoCd', displayName:'担当者', width :70,
                   cellTemplate: '<div class="text-center">{{queryResult[row.rowIndex + (pagingOptionsSIJP0130DenpyoArea.currentPage-1)*10 ].cmnTantoCd}}&nbsp;</div>'}]
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
        var getName = SIJP0130Init.init($scope.cond, function() {
            $scope.cond=getName;
            $scope.cond.kaisyaCd = AppConst.KAISYA_CODE;
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
            if(value =='31') {
                $scope.disableInit = false;
                $scope.cond.triCd = "";
            } else {
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
        var control = SIJP0130Control.query($scope.cond, function() {
            $scope.queryResult = control;
            $scope.isEditable = false;
            $scope.hasResult = true;
            $scope.totalServerItems = $scope.queryResult.length;
            $scope.pagingOptionsSIJP0130DenpyoArea.currentPage = 1;
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
            if ($scope.hasResult == false && $scope.isEditable == false) {
                $scope.cond = {};
                $scope.form.$setPristine(); 
                $scope.initCond();
            }
            Message.showMessage(MsgConst.MSG_KEY_COMMON_INFO_MESSAGE_F10);
            $scope.queryResult = {};
            $scope.hasResult = false;
            $scope.isEditable = false;
            $scope.cond.kaisyaCd = AppConst.KAISYA_CODE;
            $scope.cond.outKaisyaCd =AppConst.KAISYA_CODE;
            $scope.isNewData = false;

            $scope.error = Client.clearErrors();
            $scope.setFocus();
        },  function () {
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