///////////////////////////////////////////////////////////////////////
// Copyright(C) 2014 NEC Solution Innovators, Ltd. All Rights Reserved.
///////////////////////////////////////////////////////////////////////

/* ====================================================================
 * 機能名称 : 棚卸カテゴリー別合計表
 * 改版履歴
 * Rev. 改版年月日   改版者名       内容
 * 1.0   2015.05.08   TrieuVN      新規作成
 * =================================================================== */
var app = angular.module('zijp7040', ['zijp7040Services', 'ui', 'ui.select2', 'directives']);
app.controller("ZIJP7040Ctrl", function($scope, $window, $sce, ZIJP7040Report, Zijp7040Init, Message, MsgConst,
        $interval, HttpConst, Dialog, DialogInfo, AppConst, FocusConst, Client, UserInfo) {
    $scope.cond = {};
    $scope.result = {};
    $scope.hasResult = false;
    $scope.isFocus = FocusConst.COMBOBOX_INIT_FOCUS;
    $scope.cond.kaisyaCd = AppConst.KAISYA_CODE;
    $scope.cond.tnaUnyDd = UserInfo.unyoDate;
    $scope.hiddenBar = true;
    Message.showMessage(MsgConst.MSG_KEY_COMMON_INFO_MESSAGE_REPORT);

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
            var report = ZIJP7040Report.query($scope.cond, function() {
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
        }, function() {
            setFocusCondReport($scope, $interval, FocusConst.COMBOBOX_NOT_INIT_FOCUS);
            Message.showMessage(MsgConst.MSG_KEY_COMMON_INFO_MESSAGE_REPORT);
        });
    };

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
            $scope.result = {};
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
        $scope.error = Client.clearErrors();
        $scope.cond = {};
        $scope.cond.kaisyaCd = AppConst.KAISYA_CODE;
        $scope.cond.tnaUnyDd = UserInfo.unyoDate;
        $scope.cond.jigyobuCd = '';
        $scope.cond.tenCd = '';
        
        $scope.initCond();
    };

    /**
     * Determine when enable/disable Execute button
     * 
     * @param 
     * @return true:enable, false:disable
     */
    $scope.canExecute = function() {
        if ($scope.form.condForm.$invalid) {
            return false;
        }
        return true;
    };
    
    /**
     * Initial screen
     */
    $scope.initCond = function() {
        var maker = Zijp7040Init.query({}, function() {
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
});