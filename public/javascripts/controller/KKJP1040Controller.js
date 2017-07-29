///////////////////////////////////////////////////////////////////////
// Copyright(C) 2015 NEC Solution Innovators, Ltd. All Rights Reserved.
///////////////////////////////////////////////////////////////////////

/* ====================================================================
 * 機能名称 : 買掛金残高一覧表
 * 改版履歴
 * Rev. 改版年月日   改版者名       内容
 * 1.0   2014.04.01   TUANVT      新規作成
 * =================================================================== */

var app = angular.module('kkjp1040', ['kkjp1040Services', 'ui', 'ui.select2', 'directives']);
app.controller("KKJP1040Ctrl", function($scope, $window, Message, MsgConst, HttpConst, KKJP1040Report, $sce, Dialog,
    DialogInfo, $interval, FocusConst, Client) {
    Message.showMessage(MsgConst.MSG_KEY_COMMON_INFO_MESSAGE_REPORT);
    $scope.cond = {};
    $scope.result = {};
    $scope.isFocus = FocusConst.COMBOBOX_INIT_FOCUS;
    $scope.hiddenBar = true;

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
            if (isEmpty($scope.cond.taisyoYEd)) {
                $scope.cond.taisyoYEd = $scope.cond.taisyoYSt;
            }
            if (isEmpty($scope.cond.taisyoMEd)) {
                $scope.cond.taisyoMEd = $scope.cond.taisyoMSt;
            }
            //Search data for report
            var report = KKJP1040Report.query($scope.cond, function() {
                $scope.result = report[0];
                $scope.result.pdfUrl = $sce.getTrustedResourceUrl($scope.result.pdfUrl);
                $window.open($scope.result.pdfUrl, '帳票参照画面',
                    'width=1200,height=680,menubar=no,toolbar=no,location=no,scrollbars=yes');
                Message.showMessage(MsgConst.MSG_KEY_COMMON_REPORT_SUCCESS);
                $scope.disableLoadingBar();
                setFocusCondReport($scope, $interval, FocusConst.COMBOBOX_NOT_INIT_FOCUS);
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
                setFocusCondReport($scope, $interval, FocusConst.COMBOBOX_NOT_INIT_FOCUS);
            });
        }, function() {
            setFocusCondReport($scope, $interval, FocusConst.COMBOBOX_NOT_INIT_FOCUS);
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
            $scope.cond = {};
            $scope.cond.mainToriCdSt = '';
            $scope.cond.mainToriCdEd = '';
            $scope.cond.taisyoYSt = $scope.getCurrentYear();
            $scope.cond.taisyoMSt = $scope.getCurrentMonth();
            $scope.cond.taisyoYEd = $scope.getCurrentYear();
            $scope.cond.taisyoMEd = $scope.getCurrentMonth();
            $scope.form.$setPristine();
            setFocusCondReport($scope, $interval, FocusConst.COMBOBOX_NOT_INIT_FOCUS);
            Message.showMessage(MsgConst.MSG_KEY_COMMON_INFO_MESSAGE_REPORT);
        }, function() {
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

    $scope.$on('taisyoYStBlur', function() {
        if (isEmpty($scope.cond.taisyoYSt)) {
            $scope.error = Client.removeAllErrorBroadcast($scope.error, 'taisyoYSt');
            return;
        }
        compareDate();
    });
    $scope.$on('taisyoMStBlur', function() {
        if (isEmpty($scope.cond.taisyoMSt)) {
            $scope.error = Client.removeAllErrorBroadcast($scope.error, 'taisyoMSt');
            return;
        }
        compareDate();
    });
    $scope.$on('taisyoYEdBlur', function() {
        if (isEmpty($scope.cond.taisyoYEd)) {
            $scope.error = Client.removeAllErrorBroadcast($scope.error, 'taisyoYEd');
            return;
        }
        compareDate();
    });
    $scope.$on('taisyoMEdBlur', function() {
        if (isEmpty($scope.cond.taisyoMEd)) {
            $scope.error = Client.removeAllErrorBroadcast($scope.error, 'taisyoMEd');
            return;
        }
        compareDate();
    });

    var compareDate = function() {
        if (!isEmpty($scope.cond.taisyoYSt) && !isEmpty($scope.cond.taisyoMSt) && !isEmpty($scope.cond.taisyoYEd) && !isEmpty($scope.cond.taisyoMEd)) {
            var date1 = $scope.cond.taisyoYSt + $scope.cond.taisyoMSt + '01';
            var date2 = $scope.cond.taisyoYEd + $scope.cond.taisyoMEd + '01';
            if (date1 > date2) {
                Message.showMessage(MsgConst.MSG_KEY_VALIDATION_ERROR);
                $scope.error = Client.showErrorClient($scope.error, 'taisyoYSt', MsgConst.MSG_KEY_DATE_ERROR);
            } else {
                $scope.error = Client.removeErrorClient($scope.error, 'taisyoYSt', MsgConst.MSG_KEY_DATE_ERROR);
            }
        }
    }

    $scope.$on('mainToriCdStBlur', function() {
        if (isEmpty($scope.cond.mainToriCdSt)) {
            $scope.error = Client.removeAllErrorBroadcast($scope.error, 'mainToriCdSt');
            return;
        }
        compareMaintori();
    });
    $scope.$on('mainToriCdEdBlur', function() {
        if (isEmpty($scope.cond.mainToriCdEd)) {
            $scope.error = Client.removeAllErrorBroadcast($scope.error, 'mainToriCdEd');
            return;
        }
        compareMaintori();
    });

    var compareMaintori = function() {
        if (!isEmpty($scope.cond.mainToriCdSt) && !isEmpty($scope.cond.mainToriCdEd)) {
            if ($scope.cond.mainToriCdSt > $scope.cond.mainToriCdEd) {
                Message.showMessage(MsgConst.MSG_KEY_VALIDATION_ERROR);
                $scope.error = Client.showErrorClient($scope.error, 'mainToriCdSt', MsgConst.MSG_KEY_RANGE_ERROR);
            } else {
                $scope.error = Client.removeErrorClient($scope.error, 'mainToriCdSt', MsgConst.MSG_KEY_RANGE_ERROR);
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