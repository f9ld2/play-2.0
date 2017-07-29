///////////////////////////////////////////////////////////////////////
// Copyright(C) 2015 NEC Solution Innovators, Ltd. All Rights Reserved.
///////////////////////////////////////////////////////////////////////

/* ====================================================================
 * 機能名称 : デモ棚リスト
 * 改版履歴
 * Rev. 改版年月日   改版者名       内容
 * 1.0   205.06.24   chiennt      新規作成
 * =================================================================== */
var app = angular.module('gnjp7030', ['gnjp7030Services', 'ui', 'ui.select2', 'directives']);
app.controller("GNJP7030Ctrl", function($scope, $window, GNJP7030Report, GNJP7030Init, $sce, Message, MsgConst,
    HttpConst, Dialog, DialogInfo, $interval, AppConst, FocusConst, Client, UserInfo) {
    Message.showMessage(MsgConst.MSG_KEY_COMMON_INFO_MESSAGE_REPORT);
    $scope.cond = {};
    $scope.result = {};
    $scope.isFocus = FocusConst.TEXTINPUT_FOCUS;
    $scope.hiddenBar = true;
    $scope.cond.kaisyaCd = AppConst.KAISYA_CODE;
    $scope.cond.hatYmd = UserInfo.unyoDate;
    
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
            $scope.enableLoadingBar();
            $scope.error = null;
            Message.showMessage(MsgConst.MSG_KEY_COMMON_INFO_MESSAGE_REPORT);
            $scope.result = {};
            //Search data for report
            var report = GNJP7030Report.query($scope.cond, function() {
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
                } else if (response.status === HttpConst.CODE_UNAUTHORIZED) {
                    Message.showMessage(MsgConst.MSG_KEY_TIMEOUT_ERROR);
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
            $scope.cond.hatYmd = UserInfo.unyoDate;
            Message.showMessage(MsgConst.MSG_KEY_COMMON_INFO_MESSAGE_REPORT);
            $scope.cond.kaisyaCd = AppConst.KAISYA_CODE;
            setFocusCondReport($scope, $interval, FocusConst.TEXTINPUT_FOCUS);
        }, function() {
        	setFocusCondReport($scope, $interval, FocusConst.TEXTINPUT_FOCUS);
        });
    }

    /**
     * Initial screen
     */
    $scope.initCond = function() {
        var maker = GNJP7000Init.query({}, function() {
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
     * Determine when enable/disable Execute button
     *
     * @param
     * @return true:enable, false:disable
     */
    $scope.canExecute = function() {
        return ($scope.hiddenBar && $scope.form.condForm.$valid);
    }

    /**
     * control reset error event
     */
    $scope.$on('ccResetServerClientError', function(event, controlName) {
        $scope.error = Client.removeAllErrorByName($scope.error, controlName);
    });
});