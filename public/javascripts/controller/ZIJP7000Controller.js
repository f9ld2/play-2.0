// /////////////////////////////////////////////////////////////////////
// Copyright(C) 2014 NEC Solution Innovators, Ltd. All Rights Reserved.
// /////////////////////////////////////////////////////////////////////

/*
 * ====================================================================
 *
 * 機能名称 : 棚卸プルーフ
 *
 * 改版履歴
 *
 * Rev. 改版年月日 改版者名 内容
 *
 * 1.0 2015.06.12 NECVN 新規作成
 *
 * ===================================================================
 */
var app = angular.module('zijp7000', ['zijp7000Services', 'ui', 'ui.select2', 'directives']);
app.controller("ZIJP700Ctrl", function($scope, $window, $interval, FocusConst, Message, MsgConst, HttpConst, 
		ZIJP7000Report, Zijp7000Init, $sce, Dialog, DialogInfo, AppConst, Client, UserInfo) {
    Message.showMessage(MsgConst.MSG_KEY_COMMON_INFO_MESSAGE_REPORT);
    $scope.cond = {};
    $scope.result = {};
    $scope.isFocus = FocusConst.TEXTINPUT_FOCUS;
    $scope.hiddenBar = true;
    $scope.cond.kaisyaCd = AppConst.KAISYA_CODE;
    $scope.cond.tnaUnyDd = UserInfo.unyoDate;
    $scope.cond.tantoCd  = '';
    $scope.cond.tantoNm  = '';
    $scope.cond.tanaNoFr = '';
    $scope.cond.tanaNoTo = '';
    
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
        doExecuteDialog.result.then(function() {
            Message.showMessage(MsgConst.MSG_KEY_COMMON_INFO_MESSAGE_REPORT);
            $scope.enableLoadingBar();
            $scope.error = null;
            $scope.result = {};
            //Search data for report
            var report = ZIJP7000Report.query($scope.cond, function() {
                $scope.result = report[0];
                $scope.result.pdfUrl = $sce.getTrustedResourceUrl($scope.result.pdfUrl);
                $window.open($scope.result.pdfUrl, '帳票参照画面',
                    'width=1200,height=680,menubar=no,toolbar=no,location=no,scrollbars=yes');
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
                setFocusCondReport($scope, $interval, FocusConst.TEXTINPUT_FOCUS);
            });
        }, function() {
            setFocusCondReport($scope, $interval, FocusConst.TEXTINPUT_FOCUS);
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
        clearDialog.result.then(function() {
            $scope.form.$setPristine();
            $scope.error = Client.clearErrors();
            $scope.cond = {};
            $scope.cond.kaisyaCd = AppConst.KAISYA_CODE;
            $scope.cond.tnaUnyDd = UserInfo.unyoDate;
            $scope.cond.jigyobuCd = '';
            $scope.cond.tenCd = '';
            $scope.cond.bmnCd = '';
            $scope.cond.tantoCd  = '';
            $scope.cond.tantoNm  = '';
            $scope.cond.tanaNoFr = '';
            $scope.cond.tanaNoTo = '';
            
            $scope.initCond();
            
            Message.showMessage(MsgConst.MSG_KEY_COMMON_INFO_MESSAGE_REPORT);
            setFocusCondReport($scope, $interval, FocusConst.TEXTINPUT_FOCUS);
        }, function() {
            setFocusCondReport($scope, $interval, FocusConst.TEXTINPUT_FOCUS);
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
        var maker = Zijp7000Init.query({}, function() {
            if (maker != undefined) {
                $scope.cond.jigyobuCd = maker[0].jigyobuCd;
                $scope.cond.tenCd = maker[0].tenCd;
                $scope.cond.bmnCd = maker[0].bmnCd;
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