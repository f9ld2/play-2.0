///////////////////////////////////////////////////////////////////////
//Copyright(C) 2015 NEC Solution Innovators, Ltd. All Rights Reserved.
///////////////////////////////////////////////////////////////////////

/* ====================================================================
 * 機能名称 : 企画情報
 * 改版履歴
 * Rev. 改版年月日   改版者名       内容
 * 1.0   2014.04.29   VuQQ      新規作成
 * =================================================================== */
var app = angular.module('tkjp1060', ['tkjp1060Services', 'ui', 'ui.select2', 'directives']);
app.controller("TKJP1060Ctrl", function($scope, $window, TKJP1060Report, TKJP1060ReportInit,
    $sce, Message, MsgConst, HttpConst, Dialog, DialogInfo, $interval, AppConst, FocusConst, Client) {
    Message.showMessage(MsgConst.MSG_KEY_COMMON_INFO_MESSAGE_REPORT);
    $scope.cond = {};
    $scope.cond.kaisyaCd = AppConst.KAISYA_CODE;
    $scope.result = {};
    $scope.isFocus = FocusConst.COMBOBOX_INIT_FOCUS;
    $scope.hiddenBar = true;

    /**
     * Initialize values on the screen
     *
     * @param
     * @return
     */
    $scope.initCond = function() {
        $scope.init = {};
        $scope.init.sId = "init";
        var control = TKJP1060ReportInit.initCond($scope.init, function() {
            if (control != undefined) {
                $scope.cond.kaisyaCd = control.kaisyaCd;
                $scope.cond.jigyobuCd = control.jigyobuCd;
            }
        }, function(response) {
            Message.showMessage(MsgConst.MSG_KEY_SYSTEM_ERROR);
        });
    }

    /**
     * Export pdf file
     *
     * @param
     * @return
     */
    $scope.doExecute = function() {
        var executeDialog = Dialog.confirm(Message.getMessage(MsgConst.MSG_KEY_CONFIRM_BEFORE_MAKE_PDF));
        if (executeDialog == null) {
            return;
        }
        executeDialog.result.then(function() {
            $scope.enableLoadingBar();
            $scope.error = null;
            Message.showMessage(MsgConst.MSG_KEY_COMMON_INFO_MESSAGE_REPORT);
            $scope.result = {};
            //Search data for report
            var report = TKJP1060Report.query($scope.cond, function() {
                $scope.result = report[0];
                $scope.result.pdfUrl = $sce.getTrustedResourceUrl($scope.result.pdfUrl);
                $window.open($scope.result.pdfUrl, '帳票参照画面',
                    'width=1200,height=680,menubar=no,toolbar=no,location=no,scrollbars=yes');
                Message.showMessage(MsgConst.MSG_KEY_COMMON_REPORT_SUCCESS);
                $scope.disableLoadingBar();
                $scope.copyValue();
            }, function(response) {
                setFocusCondReport($scope, $interval, FocusConst.COMBOBOX_NOT_INIT_FOCUS);
                if (response.status === HttpConst.CODE_NOT_FOUND) {
                    $scope.copyValue();
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
        }, function() {
            setFocusCondReport($scope, $interval, FocusConst.COMBOBOX_NOT_INIT_FOCUS);
        });
    }

    /**
     * Copy values to $scope
     *
     * @param
     * @return
     */
    $scope.copyValue = function() {
        if (isEmpty($scope.cond.nendoEd) || isEmpty($scope.cond.bmnCdEd) || isEmpty($scope.cond.kikakuCdEd)) {
            $scope.copyNendoEd = true;
            $scope.copyBmnCdEd = true;
            $scope.copyKikakuEd = true;
            $scope.cond.nendoEd = $scope.cond.nendoSt;
            $scope.cond.bmnCdEd = $scope.cond.bmnCdSt;
            $scope.cond.kikakuCdEd = $scope.cond.kikakuCdSt;
            $scope.cond.kikakuNmEd = $scope.cond.kikakuNmSt;
        }

        if (!isEmpty($scope.cond.kakuteDaySt) && isEmpty($scope.cond.kakuteDayEd)) {
            $scope.cond.kakuteDayEd = $scope.cond.kakuteDaySt;
        }
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
            $scope.error = Client.clearErrors();
            $scope.form.$setPristine();
            $scope.cond = {};
            $scope.cond.kaisyaCd = AppConst.KAISYA_CODE;
            $scope.cond.bmnCdSt = '';
            $scope.cond.bmnCdEd = '';
            $scope.cond.nendoSt = $scope.getCurrentYear();
            $scope.cond.nendoEd = $scope.getCurrentYear();
            $scope.cond.kakuteDaySt = '';
            $scope.cond.kakuteDayEd = '';
            $scope.cond.kikakuCdSt = '';
            $scope.cond.kikakuCdEd = '';

            Message.showMessage(MsgConst.MSG_KEY_COMMON_INFO_MESSAGE_REPORT);
            $scope.initCond();


            setFocusCondReport($scope, $interval, FocusConst.COMBOBOX_NOT_INIT_FOCUS);
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