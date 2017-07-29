///////////////////////////////////////////////////////////////////////
//Copyright(C) 2015 NEC Solution Innovators, Ltd. All Rights Reserved.
// /////////////////////////////////////////////////////////////////////
/*
 * ====================================================================
 *
 * 機能名称 :ＴＡ伝票出力
 *
 * 改版履歴
 * Rev. 改版年月日 改版者名 内容
 * 1.0 2014-05-05 TUCTVZ 新規作成
 * ===================================================================
 */
var app = angular.module('hajp1170', ['hajp1170Services', 'ui', 'ui.select2', 'directives']);
app.controller("HAJP1170Ctrl", function($scope, $window, $interval, FocusConst, Message, MsgConst, HttpConst, HAJP1170Report,
    $sce, Dialog, DialogInfo, $interval, AppConst, Client) {
    Message.showMessage(MsgConst.MSG_KEY_COMMON_INFO_MESSAGE_REPORT);
    $scope.cond = {};
    $scope.isFocus = FocusConst.TEXTINPUT_FOCUS;
    $scope.hiddenBar = true;
    $scope.sysdate = $scope.getCurrentDate();
    $scope.cond.kaisyaCd = AppConst.KAISYA_CODE;

    /**
     * Export pdf file
     *
     * @param
     * @return
     */
    $scope.doExecute = function() {
        var diaLog = Dialog.confirm(Message.getMessage(MsgConst.MSG_KEY_CONFIRM_BEFORE_MAKE_PDF));
        if (diaLog == null) {
            return;
        }

        diaLog.result.then(function() {
            $scope.enableLoadingBar();
            $scope.error = null;
            Message.showMessage(MsgConst.MSG_KEY_COMMON_INFO_MESSAGE_REPORT);
            $scope.result = {};
            $scope.cond.sysdate = $scope.sysdate;
            //Search data for report
            var report = HAJP1170Report.query($scope.cond, function() {
                $scope.result = report[0];
                $scope.result.pdfUrl = $sce.getTrustedResourceUrl($scope.result.pdfUrl);
                $window.open($scope.result.pdfUrl, '帳票参照画面',
                    'width=1200,height=680,menubar=no,toolbar=no,location=no,scrollbars=yes');
                Message.showMessage(MsgConst.MSG_KEY_COMMON_REPORT_SUCCESS);
                $scope.disableLoadingBar();
            }, function(response) {
                if (response.status === HttpConst.CODE_NOT_FOUND) {
                    Message.showMessageWithContent(response.data.errors[0].level, response.data.errors[0].message);
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
            $scope.error = Client.clearErrors();
            $scope.form.$setPristine();
            $scope.cond = {};
            $scope.cond.kaisyaCd = AppConst.KAISYA_CODE;
            $scope.cond.nhnYmdSt = "";
            $scope.cond.nhnYmdEd = "";
            $scope.cond.triCdSt = "";
            $scope.cond.triNmSt = "";
            $scope.cond.triCdEd = "";
            $scope.cond.triNmEd = "";
            $scope.cond.jigyobuCd = "";
            $scope.cond.tenCdSt = "";
            $scope.cond.tenCdEd = "";

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
        if ($scope.form.$invalid) {
            return false;
        } else {
            return true;
        }
    }

    $scope.$on('triCdStBlur', function() {
        if (isEmpty($scope.cond.triCdSt)) {
            $scope.error = Client.removeAllErrorBroadcast($scope.error, 'triCdSt');
            return;
        }
        compareTriCd();
    });
    $scope.$on('triCdEdBlur', function() {
        if (isEmpty($scope.cond.triCdEd)) {
            $scope.error = Client.removeAllErrorBroadcast($scope.error, 'triCdEd');
            return;
        }
        compareTriCd();
    });

    var compareTriCd = function() {
        if (!isEmpty($scope.cond.triCdSt) && !isEmpty($scope.cond.triCdEd)) {
            var first6digit1 = $scope.cond.triCdSt.substr(0, 6);
            var first6digit2 = $scope.cond.triCdEd.substr(0, 6);
            var last3digit1 = $scope.cond.triCdSt.substr(6, 9);
            var last3digit2 = $scope.cond.triCdEd.substr(6, 9);

            if (first6digit1 > first6digit2) {
                Message.showMessage(MsgConst.MSG_KEY_VALIDATION_ERROR);
                $scope.error = Client.showErrorClient($scope.error, 'triCdSt', MsgConst.MSG_KEY_ERROR_TORIHIKI_FR_GREATER);
                $scope.error = Client.showErrorClient($scope.error, 'triCdEd', MsgConst.MSG_KEY_ERROR_TORIHIKI_FR_GREATER);
            } else {
                $scope.error = Client.removeErrorClient($scope.error, 'triCdSt', MsgConst.MSG_KEY_ERROR_TORIHIKI_FR_GREATER);
                $scope.error = Client.removeErrorClient($scope.error, 'triCdEd', MsgConst.MSG_KEY_ERROR_TORIHIKI_FR_GREATER);
            }

            if (first6digit1 != first6digit2) {
                Message.showMessage(MsgConst.MSG_KEY_VALIDATION_ERROR);
                $scope.error = Client.showErrorClient($scope.error, 'triCdSt', MsgConst.MSG_KEY_REQUIRED_TORIHIKI_SAME_ENTER);
                $scope.error = Client.showErrorClient($scope.error, 'triCdEd', MsgConst.MSG_KEY_REQUIRED_TORIHIKI_SAME_ENTER);
            } else {
                $scope.error = Client.removeErrorClient($scope.error, 'triCdSt', MsgConst.MSG_KEY_REQUIRED_TORIHIKI_SAME_ENTER);
                $scope.error = Client.removeErrorClient($scope.error, 'triCdEd', MsgConst.MSG_KEY_REQUIRED_TORIHIKI_SAME_ENTER);
            }

            if (first6digit1 == first6digit2) {
                if (last3digit1 > last3digit2) {
                    Message.showMessage(MsgConst.MSG_KEY_VALIDATION_ERROR);
                    $scope.error = Client.showErrorClient($scope.error, 'triCdSt', MsgConst.MSG_KEY_ERROR_BRANCH_FR_GREATER);
                    $scope.error = Client.showErrorClient($scope.error, 'triCdEd', MsgConst.MSG_KEY_ERROR_BRANCH_FR_GREATER);
                } else {
                    $scope.error = Client.removeErrorClient($scope.error, 'triCdSt', MsgConst.MSG_KEY_ERROR_BRANCH_FR_GREATER);
                    $scope.error = Client.removeErrorClient($scope.error, 'triCdEd', MsgConst.MSG_KEY_ERROR_BRANCH_FR_GREATER);
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