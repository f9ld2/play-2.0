///////////////////////////////////////////////////////////////////////
//Copyright(C) 2015 NEC Solution Innovators, Ltd. All Rights Reserved.
///////////////////////////////////////////////////////////////////////

/* ====================================================================
 * 機能名称 : 日割予算リスト
 * 改版履歴
 * Rev. 改版年月日   改版者名       内容
 * 1.0   2014.05.20   Taivd      新規作成
 * =================================================================== */
var app = angular.module('urjp1140', ['urjp1140Services', 'ui', 'ui.select2', 'directives']);
app.controller("URJP1140Ctrl", function($scope, $window, $sce, $interval, URJP1140Report, URJP1140Init, Message, MsgConst,
        HttpConst, Dialog, DialogInfo, AppConst, FocusConst, Client) {
    $scope.cond = {};
    $scope.result = {};
    $scope.hasResult = false;
    $scope.isFocus = FocusConst.COMBOBOX_INIT_FOCUS;
    $scope.hiddenBar = true;
    $scope.cond.kaisyaCd = AppConst.KAISYA_CODE;
    Message.showMessage(MsgConst.MSG_KEY_COMMON_INFO_MESSAGE_REPORT);

    /**
     * Initialize values on the screen
     * 
     * @param 
     * @return 
     */
    $scope.initCond = function() {
        var getCond = URJP1140Init.init($scope.cond, function() {
//            $scope.cond = getCond;
            $scope.cond.jigyobuCd = getCond.jigyobuCd;
            $scope.cond.tenCd = getCond.tenCd;
            $scope.cond.kaisyaCd = AppConst.KAISYA_CODE;
        }, function(response) {});
    }

    /**
     * Export pdf file
     * 
     * @param 
     * @return 
     */
    $scope.doExecute = function() {
        var confirmDialog = Dialog.confirm(Message.getMessage(MsgConst.MSG_KEY_CONFIRM_BEFORE_MAKE_PDF));
        if (confirmDialog == null) {
            return;
        }
        confirmDialog.result.then(function() {
            Message.showMessage(MsgConst.MSG_KEY_COMMON_INFO_MESSAGE_REPORT);
            $scope.enableLoadingBar();
            $scope.error = null;
            $scope.result = {};
            //Search data for report
            var report = URJP1140Report.query($scope.cond, function() {
                $scope.result = report;
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

        }, function() {
            setFocusCondReport($scope, $interval, FocusConst.COMBOBOX_NOT_INIT_FOCUS);
            Message.showMessage(MsgConst.MSG_KEY_COMMON_INFO_MESSAGE_REPORT);
        });
    };

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
            $scope.resetCondForm();
            $scope.initCond();
            $scope.cond.kaisyaCd = AppConst.KAISYA_CODE;
            $scope.cond.taisyodateY = $scope.getCurrentYear();
            $scope.cond.taisyodateM = $scope.getCurrentMonth();
            $scope.result = {};
            $scope.totalServerItems = 0;
            $scope.error = Client.clearErrors();
            $scope.errorsTable = [];
            $scope.hasResult = false;

            setFocusCondReport($scope, $interval, FocusConst.COMBOBOX_NOT_INIT_FOCUS);
            Message.showMessage(MsgConst.MSG_KEY_COMMON_INFO_MESSAGE_REPORT);
            

        }, function() {
            setFocusCondReport($scope, $interval, FocusConst.COMBOBOX_NOT_INIT_FOCUS);
        });
    };

    /**
     * reset value of condition area.
     * 
     * @param 
     * @return
     */
    $scope.resetCondForm = function() {
        $scope.error = null;
        $scope.cond = {};
        
    };

    /**
     * Determine when enable/disable Execute button
     * 
     * @param 
     * @return true:enable, false:disable
     */
    $scope.canExecute = function() {
        if ($scope.form.condForm == null) {
            return;
        }
        if ($scope.form.condForm.$invalid) {
            return false;
        } else {
            return true;
        }
    }
});