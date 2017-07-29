///////////////////////////////////////////////////////////////////////
//Copyright(C) 2015 NEC Solution Innovators, Ltd. All Rights Reserved.
///////////////////////////////////////////////////////////////////////

/*
 * ====================================================================
 * 
 * 機能名称 : 生鮮納品書
 * 
 * 改版履歴
 * 
 * Rev. 改版年月日 改版者名 内容
 * 
 * 1.0 2014.05.03 TuanTQ 新規作成
 * 
 * ===================================================================
 */
var app = angular.module('sijp1140', [ 'sijp1140Services', 'ui', 'ui.select2', 'directives' ]);
app.controller("SIJP1140Ctrl", function($scope, $window, Message, MsgConst, FocusConst, HttpConst, $interval, 
        Sijp1140Report, $sce, Dialog, DialogInfo, AppConst, $timeout, Client) {
    Message.showMessage(MsgConst.MSG_KEY_COMMON_INFO_MESSAGE_REPORT);
    $scope.cond = {};
    $scope.result = {};
    $scope.cond.tenCds = "";
    $scope.cond.bmnCds = "";
    $scope.cond.torihikiCds = "";
    $scope.isFocus = FocusConst.COMBOBOX_INIT_FOCUS;
    $scope.hiddenBar = true;
    $scope.cond.kaisyaCd = AppConst.KAISYA_CODE;
    var now = new Date(); 
    var month = (now.getMonth() + 1).toString();
    var day = now.getDate().toString();
    if(month.length == 1) {
        month = "0" + month;
    }
    if(day.length == 1) {
        day = "0" + day;
    }

    $scope.sysdate = now.getFullYear().toString() + month + day.toString();

    /**
     * Export pdf file
     * 
     * @param 
     * @return 
     */
    $scope.doExecute = function() {
        var doExecuteDialog = Dialog.confirm(Message.getMessage(MsgConst.MSG_KEY_CONFIRM_BEFORE_MAKE_PDF));
        if (doExecuteDialog == null) {
            return;
        }
        doExecuteDialog.result.then(function () {
            Message.showMessage(MsgConst.MSG_KEY_COMMON_INFO_MESSAGE_REPORT);
            $scope.enableLoadingBar();
            $scope.error = null;
            $scope.result = {};
            $scope.cond.tenCds = "";
            $scope.cond.bmnCds = "";
            $scope.cond.torihikiCds = "";
            if(  !isEmpty($scope.cond.bmnCd) && $scope.cond.bmnCd.length >0){
                for(var k = 0; k < $scope.cond.bmnCd.length; k++) {
                    if(k == 0){
                        $scope.cond.bmnCds = $scope.cond.bmnCds + $scope.cond.bmnCd[k];
                    }else{
                        $scope.cond.bmnCds = $scope.cond.bmnCds + ","+ $scope.cond.bmnCd[k];
                    }
                }

            }
            if(  !isEmpty($scope.cond.tenCd) && $scope.cond.tenCd.length >0){
                for(var k = 0; k < $scope.cond.tenCd.length; k++) {
                    if(k == 0){
                        $scope.cond.tenCds = $scope.cond.tenCds + $scope.cond.tenCd[k];
                    }else{
                        $scope.cond.tenCds = $scope.cond.tenCds +","+ $scope.cond.tenCd[k];

                    }
                }
            } 
            if(  !isEmpty($scope.cond.torihikiCd) && $scope.cond.torihikiCd.length >0){
                for(var k = 0; k < $scope.cond.torihikiCd.length; k++) {
                    if(k == 0){
                        $scope.cond.torihikiCds = $scope.cond.torihikiCds + $scope.cond.torihikiCd[k];

                    }else{
                        $scope.cond.torihikiCds = $scope.cond.torihikiCds +","+ $scope.cond.torihikiCd[k];
                    }
                }

            }
            //Search data for report
            var report = Sijp1140Report.query($scope.cond, function() {
                $scope.result = report[0];
                $scope.result.pdfUrl = $sce.getTrustedResourceUrl($scope.result.pdfUrl);
                $window.open($scope.result.pdfUrl, '帳票参照画面',
                'width=1200,height=680,menubar=no,toolbar=no,location=no,scrollbars=yes');
                Message.showMessage(MsgConst.MSG_KEY_COMMON_REPORT_SUCCESS);
                $scope.disableLoadingBar();
            }, function(response) {
                setFocusCondReport($scope, $interval, FocusConst.COMBOBOX_NOT_INIT_FOCUS);
                if (response.status === HttpConst.CODE_NOT_FOUND) {
                    Message.showMessage(MsgConst.MSG_KEY_NO_FOUND_DATA);
                } else if (response.status === HttpConst.CODE_BAD_REQUEST) {
                    // エラー処理
                    Message.showMessageWithContent(response.data.errors[0].level, response.data.errors[0].message);
                    Client.showErrorFromServer($scope.error, response.data.errors);
                } else {
                    Message.showMessage(MsgConst.MSG_KEY_SYSTEM_ERROR);
                }
                $scope.disableLoadingBar();
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
            $scope.form.$setPristine();
            $scope.error = Client.clearErrors();
            $scope.cond = {};
            $scope.cond.kaisyaCd = AppConst.KAISYA_CODE;
            $scope.cond.jigyoubuCd = "";
            $scope.cond.dpyKbn = "";
            $scope.cond.tenCd = "";
            $scope.cond.bmnCd = "";
            $scope.cond.torihikiCd = "";
            $scope.cond.nhnYoteiYmdSt = "";
            $scope.cond.nhnYoteiYmdEd = "";
            $scope.cond.hatYmdSt = "";
            $scope.cond.hatYmdEd = "";
            $scope.cond.tenCds = "";
            $scope.cond.bmnCds = "";
            $scope.cond.torihikiCds = "";

            setFocusCondReport($scope, $interval, FocusConst.COMBOBOX_NOT_INIT_FOCUS);
            Message.showMessage(MsgConst.MSG_KEY_COMMON_INFO_MESSAGE_REPORT);
            

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
});