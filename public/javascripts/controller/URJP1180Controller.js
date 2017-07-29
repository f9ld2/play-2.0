// /////////////////////////////////////////////////////////////////////
// Copyright(C) 2015 NEC Solution Innovators, Ltd. All Rights Reserved.
// /////////////////////////////////////////////////////////////////////

/*
 * ====================================================================
 * 
 * 機能名称 : 月次・日割予算チェックリスト
 * 
 * 改版履歴
 * 
 * Rev. 改版年月日 改版者名 内容
 * 
 * 1.0 2014.07.01 NES石井 新規作成
 * 
 * ===================================================================
 */

var app = angular.module('urjp1180', [ 'urjp1180Services', 'ui', 'ui.select2', 'directives' ]);
app.controller("URJP1180Ctrl", function($scope, $window, $interval, FocusConst, Message, MsgConst, HttpConst, Urjp1180Report, $sce, Dialog, DialogInfo, AppConst, Client) {
	Message.showMessage(MsgConst.MSG_KEY_COMMON_INFO_MESSAGE_REPORT);
    $scope.cond = {};
    $scope.result = {};
    $scope.isFocus = FocusConst.TEXTINPUT_FOCUS;
    $scope.hiddenBar = true;
    $scope.cond.kaisyaCd = AppConst.KAISYA_CODE;

    $scope.doExecute = function() {
    	var doExecuteDialog = Dialog.confirm(Message.getMessage(MsgConst.MSG_KEY_CONFIRM_BEFORE_MAKE_PDF));
        if (doExecuteDialog == null) {
            return;
        }
            doExecuteDialog.result.then(function () {
            Message.showMessage(MsgConst.MSG_KEY_COMMON_INFO_MESSAGE_REPORT);
            $scope.enableLoadingBar();
            $scope.error = null;
            $scope.result = {};

            var report = Urjp1180Report.get($scope.cond, function() {
                $scope.result = report;
                $scope.result.pdfUrl = $sce.getTrustedResourceUrl($scope.result.pdfUrl);
                $window.open($scope.result.pdfUrl, '帳票参照画面',
                        'width=1200,height=680,menubar=no,toolbar=no,location=no,scrollbars=yes');
                Message.showMessage(MsgConst.MSG_KEY_COMMON_REPORT_SUCCESS);
                $scope.disableLoadingBar();
                setFocusCondReport($scope, $interval, FocusConst.TEXTINPUT_FOCUS);
            }, function(response) {
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
                setFocusCondReport($scope, $interval, FocusConst.TEXTINPUT_FOCUS);
            });
        }, function () {
        	setFocusCondReport($scope, $interval, FocusConst.TEXTINPUT_FOCUS);
        });
    }

    $scope.disableLoadingBar = function() {
        $scope.hiddenBar = true;
    }

    $scope.enableLoadingBar = function() {
        $scope.hiddenBar = false;
    }

    $scope.doClear = function() {
    	var clearDialog = Dialog.confirm(Message.getMessage(MsgConst.MSG_KEY_CONFIRM_BEFORE_CLEAR));
        if (clearDialog == null) {
            return;
        }
        clearDialog.result.then(function () {
        	$scope.error = Client.clearErrors();
            $scope.form.$setPristine();
            $scope.cond = {};
            $scope.cond.kaisyaCd = AppConst.KAISYA_CODE;
            $scope.cond.jigyobuCd = '';
            $scope.cond.taisyodateY = $scope.getCurrentYear();
            $scope.cond.taisyodateM = $scope.getCurrentMonth();

            Message.showMessage(MsgConst.MSG_KEY_COMMON_INFO_MESSAGE_REPORT);
            setFocusCondReport($scope, $interval, FocusConst.TEXTINPUT_FOCUS);
            
        }, function () {
        	setFocusCondReport($scope, $interval, FocusConst.TEXTINPUT_FOCUS);
        });

    }

    $scope.reFreshCond = function() {
        return $scope.form.$pristine;
    }

    $scope.canExecute = function() {
        if ($scope.form.$invalid) {
            return false;
        } else {
            return true;
        }
    }
});