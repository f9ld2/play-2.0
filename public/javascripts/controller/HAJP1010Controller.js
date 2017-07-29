///////////////////////////////////////////////////////////////////////
//Copyright(C) 2015 NEC Solution Innovators, Ltd. All Rights Reserved.
///////////////////////////////////////////////////////////////////////

/* ====================================================================
 * 機能名称 : 発注エラーリスト
 * 改版履歴
 * Rev. 改版年月日   改版者名       内容
 * 1.0   20140416   LocHV      新規作成
 * =================================================================== */

var app = angular.module('hajp1010', [ 'hajp1010Services', 'ui', 'ui.select2', 'directives', 'ui.bootstrap' ]);
app.controller("HAJP1010Ctrl", function($scope, $window, HAJP1010Report, $sce, Message, MsgConst, HttpConst, Dialog,
        DialogInfo, $interval, AppConst, FocusConst, Client, UserInfo) {
    Message.showMessage(MsgConst.MSG_KEY_COMMON_INFO_MESSAGE_REPORT);
    $scope.cond = {};
    $scope.result = {};
    $scope.isFocus = FocusConst.TEXTINPUT_FOCUS;
    $scope.hiddenBar = true;
    $scope.cond.kaisyaCd = AppConst.KAISYA_CODE;
    
    /**
     * Export pdf file
     * 
     * @param 
     * @return 
     */
    $scope.doExecute = function() {
        var confirmDialog = Dialog.confirm(Message.getMessage(MsgConst.MSG_KEY_CONFIRM_BEFORE_MAKE_PDF));

        confirmDialog.result.then(function () {
            $scope.enableLoadingBar();
            $scope.error = null;
            $scope.result = {};
            //Search data for report
            var report = HAJP1010Report.query($scope.cond, function() {
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
        }, function () {
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
        clearDialog.result.then(function () {
            $scope.error = Client.clearErrors();
            $scope.cond = {};
            $scope.cond.kaisyaCd = AppConst.KAISYA_CODE;
            $scope.cond.hatDdSt = '';
            $scope.cond.hatDdEd = '';
            $scope.form.$setPristine();
            Message.showMessage(MsgConst.MSG_KEY_COMMON_INFO_MESSAGE_REPORT);
            
            setFocusCondReport($scope, $interval, FocusConst.TEXTINPUT_FOCUS);
        }, function () {
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
    
    //Client check
    /**
     * Validate hatDdSt: hatDdSt is greater than hatUnyoDate
     */
    $scope.$on("hatDdStBlur", function(event) {
        var hatUnyoDate = UserInfo.hatUnyoDate;
        if ($scope.cond.hatDdSt > hatUnyoDate) {
            $scope.error = Client.showErrorClient($scope.error, 'hatDdSt', MsgConst.MSG_KEY_NOT_MATCH_TYPE_FROM_GREATER);
        } else {
            $scope.error = Client.removeErrorClient($scope.error, 'hatDdSt', MsgConst.MSG_KEY_NOT_MATCH_TYPE_FROM_GREATER);
        }
    });

    /**
     * control reset error
     */
    $scope.$on('ccResetServerClientError', function(event, controlName) {
        $scope.error = Client.removeAllErrorByName($scope.error, controlName);
    });
});