///////////////////////////////////////////////////////////////////////
//Copyright(C) 2015 NEC Solution Innovators, Ltd. All Rights Reserved.
///////////////////////////////////////////////////////////////////////

/* ====================================================================
 * 機能名称 : 入荷状況一覧表
 * 改版履歴
 * Rev. 改版年月日   改版者名       内容
 * 1.0   2015.06.05   NECVN      新規作成
 * =================================================================== */

var app = angular.module('sijp7020', [ 'sijp7020Services', 'ui', 'ui.select2', 'directives', 'ui.bootstrap' ]);
app.controller("SIJP7020Ctrl", function($scope, $window, SIJP7020Init, SIJP7020Report, $sce, Message, MsgConst, HttpConst, Dialog,
        $timeout,$interval, AppConst, FocusConst, Client) {
    Message.showMessage(MsgConst.MSG_KEY_COMMON_INFO_MESSAGE_REPORT);
    $scope.cond = {};
    $scope.result = {};
    $scope.isFocus = FocusConst.COMBOBOX_INIT_FOCUS;
    $scope.hiddenBar = true;
    $scope.cond.kaisyaCd = AppConst.KAISYA_CODE;
    $scope.cond.outTaisyo = 1;
    $scope.cond.tenCds="";
    
    /**
     * Export pdf file
     * 
     * @param 
     * @return 
     */
    $scope.doExecute = function() {
        var confirmDialog = Dialog.confirm(Message.getMessage(MsgConst.MSG_KEY_CONFIRM_BEFORE_MAKE_PDF));

        confirmDialog.result.then(function () {
            $scope.enableLoadingBar();
            $scope.error = Client.clearErrors();
            $scope.result = {};
            $scope.cond.tenCds = "";
            if(  !isEmpty($scope.cond.tenCd) && $scope.cond.tenCd.length >0){
                for(var k = 0; k < $scope.cond.tenCd.length; k++) {
                    if(k == 0 || $scope.cond.tenCd[k].length == 1){
                        $scope.cond.tenCds = $scope.cond.tenCds + $scope.cond.tenCd[k];
                    }else{
                        $scope.cond.tenCds = $scope.cond.tenCds +","+ $scope.cond.tenCd[k];

                    }
                }
            } 
            //Search data for report
            var report = SIJP7020Report.query($scope.cond, function() {
                $scope.result = report[0];
                $scope.result.pdfUrl = $sce.getTrustedResourceUrl($scope.result.pdfUrl);
                $window.open($scope.result.pdfUrl, '帳票参照画面（'+$scope.result.type+'）',
                        'width=1200,height=680,menubar=no,toolbar=no,location=no,scrollbars=yes');
                
                if (report[1] != null){
                	report[1].pdfUrl = $sce.getTrustedResourceUrl(report[1].pdfUrl);
                    $window.open(report[1].pdfUrl, '帳票参照画面（'+report[1].type+'）',
                             'width=1200,height=680,menubar=no,toolbar=no,location=no,scrollbars=yes');
                }
                Message.showMessage(MsgConst.MSG_KEY_COMMON_REPORT_SUCCESS);
                $scope.disableLoadingBar();
            }, function(response) {
                if (response.status === HttpConst.CODE_NOT_FOUND) {
                    Message.showMessage(MsgConst.MSG_KEY_NO_FOUND_DATA);
                } else if (response.status === HttpConst.CODE_BAD_REQUEST) {
                    // エラー処理
                    Message.showMessageWithContent(response.data.errors[0].level, response.data.errors[0].message);
                    $scope.error = Client.showErrorFromServer($scope.error, response.data.errors);
                } else {
                    Message.showMessage(MsgConst.MSG_KEY_SYSTEM_ERROR);
                }
                $scope.disableLoadingBar();
                setFocusCondReport($scope, $interval, FocusConst.COMBOBOX_NOT_INIT_FOCUS);
            });
        }, function () {
            setFocusCondReport($scope, $interval, FocusConst.COMBOBOX_NOT_INIT_FOCUS);
        });
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
     * Clear input data of controls
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
            $scope.cond = {};
            $scope.cond.kaisyaCd = AppConst.KAISYA_CODE;
            $scope.cond.nhnDayFr = '';
            $scope.cond.nhnDayTo = '';
            $scope.cond.outTaisyo = 1;
            $scope.initCond();
            
            $scope.form.$setPristine();
            Message.showMessage(MsgConst.MSG_KEY_COMMON_INFO_MESSAGE_REPORT);
            
            setFocusCondReport($scope, $interval, FocusConst.COMBOBOX_NOT_INIT_FOCUS);
        }, function () {
            setFocusCondReport($scope, $interval, FocusConst.COMBOBOX_NOT_INIT_FOCUS);
        });
    }

    /**
     * Get focus flag used to focus on first control
     * 
     * @param 
     * @return isFocus flag
     */
    $scope.reFreshCond = function() {
        return  $scope.isFocus;
    }

    /**
     * Determine when enable/disable Execute button
     * 
     * @param 
     * @return true:enable, false:disable
     */
    $scope.canExecute = function() {
        if ($scope.form.$invalid) {
            return false;
        } else {
            return true;
        }
    }
    
    /**
     * Initial screen
     */
    $scope.initCond = function() {
        $timeout(function() {
            var maker = SIJP7020Init.query({}, function() {
                if (maker != undefined) {
                    $scope.cond.jigyobuCd = maker[0].jigyobuCd;
                    $scope.cond.tenCd = maker[0].tenCds;
                    $scope.cond.triCd = maker[0].triCd;
                }
            }, function(response) {
                if (response.status === HttpConst.CODE_UNAUTHORIZED) {
                    Message.showMessage(MsgConst.MSG_KEY_TIMEOUT_ERROR);
                } else {
                    Message.showMessage(MsgConst.MSG_KEY_SYSTEM_ERROR);
                }
            });

        }, 0);
    }
    
});