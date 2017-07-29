// /////////////////////////////////////////////////////////////////////
// Copyright(C) 2015 NEC Solution Innovators, Ltd. All Rights Reserved.
// /////////////////////////////////////////////////////////////////////

/*
 * ====================================================================
 * 
 * 機能名称 : 取引先別在庫金額一覧
 * 
 * 改版履歴
 * 
 * Rev. 改版年月日 改版者名 内容
 * 
 * 1.0 2014-04-05 TUCTV 新規作成
 * 
 * ===================================================================
 */
var app = angular.module('zijp1080', [ 'zijp1080Services', 'ui', 'ui.select2', 'directives' ]);
app.controller("ZIJP1080Ctrl", function($scope, $window, Zijp1080Job, Message, MsgConst, 
		HttpConst, $sce, Dialog, DialogInfo, $interval,AppConst, FocusConst, Client) {
    Message.showMessage(MsgConst.MSG_KEY_COMMON_INFO_MESSAGE_REPORT);
    $scope.cond = {};
    $scope.result = {};
    $scope.isFocus = FocusConst.COMBOBOX_INIT_FOCUS;
    $scope.hiddenBar = true;
    $scope.cond.kaisyaCd = AppConst.KAISYA_CODE;
    
    /**
     * Export pdf file
     * 
     * @param 
     * @return 
     */
    $scope.doExecute = function() {
        var doExecuteDialog = Dialog.confirm(Message.getMessage(MsgConst.MSG_KEY_CONFIRM_BEFORE_START_JOB));
        if (doExecuteDialog == null) {
            return;
        }
        doExecuteDialog.result.then(function () {
            Message.showMessage(MsgConst.MSG_KEY_COMMON_INFO_MESSAGE_REPORT);
            $scope.enableLoadingBar();
            $scope.error = null;
            $scope.result = {};
            //Search data for report
            var report = Zijp1080Job.query($scope.cond, function() {
                $scope.result = report[0];
                Message.showMessageWithContent($scope.result.infoRes.errors[0].level, $scope.result.infoRes.errors[0].message);
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
            
            setFocusCondReport($scope, $interval, FocusConst.COMBOBOX_NOT_INIT_FOCUS);
            $scope.cond.kaisyaCd = AppConst.KAISYA_CODE;
            
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
        return $scope.isFocus;
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