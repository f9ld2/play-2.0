// /////////////////////////////////////////////////////////////////////
// Copyright(C) 2015 NEC Solution Innovators, Ltd. All Rights Reserved.
// /////////////////////////////////////////////////////////////////////

/*
 * ====================================================================
 *
 * 機能名称 : ＥＯＳ発注明細リスト出力
 *
 * 改版履歴
 *
 * Rev. 改版年月日 改版者名 内容
 *
 * 1.0 2014.04.25 Tinnc 新規作成
 *
 * ===================================================================
 */
var app = angular.module('hajp1040', ['hajp1040Services', 'ui', 'ui.select2', 'directives']);
app.controller("HAJP1040Ctrl", function($scope, $window, $interval, FocusConst, ComInfo, Message, MsgConst,
    HttpConst, Hajp1040Report, $sce, Dialog, DialogInfo, $interval, AppConst, Client) {
    Message.showMessage(MsgConst.MSG_KEY_COMMON_INFO_MESSAGE_REPORT);
    $scope.cond = {};
    $scope.result = {};
    $scope.isFocus = FocusConst.TEXTINPUT_FOCUS;
    $scope.hiddenBar = true;
    $scope.cond.kaisyaCd = AppConst.KAISYA_CODE;
    $scope.sysdate = $scope.getCurrentDate();

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
            var report = Hajp1040Report.query($scope.cond, function() {
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
            $scope.cond.hatYmdSt = null;
            $scope.cond.hatYmdEd = null;
            $scope.cond.jigyobuCd = null;
            $scope.cond.tenCdSt = null;
            $scope.cond.tenCdEd = null;
            $scope.cond.torihikiCdSt = null;
            $scope.cond.torihikiCdEd = null;
            $scope.cond.hatShoriKbn = null;

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


    $scope.$on('tenCdStBlur', function() {
        if (isEmpty($scope.cond.tenCdSt)) {
            $scope.error = Client.removeAllErrorBroadcast($scope.error, 'tenCdSt');
            return;
        }
        compareTenCd();
    });
    $scope.$on('tenCdEdBlur', function() {
        if (isEmpty($scope.cond.tenCdEd)) {
            $scope.error = Client.removeAllErrorBroadcast($scope.error, 'tenCdEd');
            return;
        }
        compareTenCd();
    });

    var compareTenCd = function() {
        if (!isEmpty($scope.cond.tenCdSt) && !isEmpty($scope.cond.tenCdEd)) {
            if ($scope.cond.tenCdSt > $scope.cond.tenCdEd) {
                Message.showMessage(MsgConst.MSG_KEY_VALIDATION_ERROR);
                $scope.error = Client.showErrorClient($scope.error, 'tenCdSt', MsgConst.MSG_KEY_TENCD_ERROR_COMPARE);
                $scope.error = Client.showErrorClient($scope.error, 'tenCdEd', MsgConst.MSG_KEY_TENCD_ERROR_COMPARE);
            } else {
                $scope.error = Client.removeErrorClient($scope.error, 'tenCdSt', MsgConst.MSG_KEY_TENCD_ERROR_COMPARE);
                $scope.error = Client.removeErrorClient($scope.error, 'tenCdEd', MsgConst.MSG_KEY_TENCD_ERROR_COMPARE);
            }
        }
    }

    $scope.$on('torihikiCdStBlur', function() {
        if (isEmpty($scope.cond.torihikiCdSt)) {
            $scope.error = Client.removeAllErrorBroadcast($scope.error, 'torihikiCdSt');
            return;
        }
        compareTori();
    });
    $scope.$on('torihikiCdEdBlur', function() {
        if (isEmpty($scope.cond.torihikiCdEd)) {
            $scope.error = Client.removeAllErrorBroadcast($scope.error, 'torihikiCdEd');
            return;
        }
        compareTori();
    });

    var compareTori = function() {
        if (!isEmpty($scope.cond.torihikiCdSt) && !isEmpty($scope.cond.torihikiCdEd)) {
            var first6digit1 = $scope.cond.torihikiCdSt.substr(0, 6);
            var first6digit2 = $scope.cond.torihikiCdEd.substr(0, 6);
            var last3digit1 = $scope.cond.torihikiCdSt.substr(6, 9);
            var last3digit2 = $scope.cond.torihikiCdEd.substr(6, 9);

            if (first6digit1 > first6digit2) {
                Message.showMessage(MsgConst.MSG_KEY_VALIDATION_ERROR);
                $scope.error = Client.showErrorClient($scope.error, 'torihikiCdSt', MsgConst.MSG_KEY_ERROR_TORIHIKI_FR_GREATER);
                $scope.error = Client.showErrorClient($scope.error, 'torihikiCdEd', MsgConst.MSG_KEY_ERROR_TORIHIKI_FR_GREATER);
            } else {
                $scope.error = Client.removeErrorClient($scope.error, 'torihikiCdSt', MsgConst.MSG_KEY_ERROR_TORIHIKI_FR_GREATER);
                $scope.error = Client.removeErrorClient($scope.error, 'torihikiCdEd', MsgConst.MSG_KEY_ERROR_TORIHIKI_FR_GREATER);
            }

            if (first6digit1 != first6digit2) {
                Message.showMessage(MsgConst.MSG_KEY_VALIDATION_ERROR);
                $scope.error = Client.showErrorClient($scope.error, 'torihikiCdSt', MsgConst.MSG_KEY_REQUIRED_TORIHIKI_SAME_ENTER);
                $scope.error = Client.showErrorClient($scope.error, 'torihikiCdEd', MsgConst.MSG_KEY_REQUIRED_TORIHIKI_SAME_ENTER);
            } else {
                $scope.error = Client.removeErrorClient($scope.error, 'torihikiCdSt', MsgConst.MSG_KEY_REQUIRED_TORIHIKI_SAME_ENTER);
                $scope.error = Client.removeErrorClient($scope.error, 'torihikiCdEd', MsgConst.MSG_KEY_REQUIRED_TORIHIKI_SAME_ENTER);
            }

            if (first6digit1 == first6digit2) {
                if (last3digit1 > last3digit2) {
                    Message.showMessage(MsgConst.MSG_KEY_VALIDATION_ERROR);
                    $scope.error = Client.showErrorClient($scope.error, 'torihikiCdSt', MsgConst.MSG_KEY_ERROR_BRANCH_FR_GREATER);
                    $scope.error = Client.showErrorClient($scope.error, 'torihikiCdEd', MsgConst.MSG_KEY_ERROR_BRANCH_FR_GREATER);
                } else {
                    $scope.error = Client.removeErrorClient($scope.error, 'torihikiCdSt', MsgConst.MSG_KEY_ERROR_BRANCH_FR_GREATER);
                    $scope.error = Client.removeErrorClient($scope.error, 'torihikiCdEd', MsgConst.MSG_KEY_ERROR_BRANCH_FR_GREATER);
                }
            }
        }
    }

    /**
     * control reset error event
     */
    $scope.$on('ccResetServerClientError', function(event, controlName) {
        $scope.error = Client.removeAllErrorByName($scope.error, controlName);
    });
});