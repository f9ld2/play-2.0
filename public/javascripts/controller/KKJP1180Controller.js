///////////////////////////////////////////////////////////////////////
// Copyright(C) 2015 NEC Solution Innovators, Ltd. All Rights Reserved.
///////////////////////////////////////////////////////////////////////

/* ====================================================================
 * 機能名称 : 買掛金支払チェックリスト出力
 * 改版履歴
 * Rev. 改版年月日   改版者名       内容
 * 1.0   2014.04.05   VuQQ      新規作成
 * =================================================================== */
var app = angular.module('kkjp1180', ['kkjp1180Services', 'ui','ui.select2','directives']);
app.controller("KKJP1180Ctrl",function($scope, $window, ComInfo, KKJP1180Report, $sce, $translate, Message, MsgConst, 
        HttpConst, Dialog, DialogInfo, $interval, FocusConst, Client) {
	Message.showMessage(MsgConst.MSG_KEY_COMMON_INFO_MESSAGE_REPORT);
	$scope.cond = {};
	$scope.result = {};
	$scope.isFocus = FocusConst.COMBOBOX_INIT_FOCUS;
    $scope.hiddenBar = true;
	
	$scope.doExecute = function() {
		var executeDialog = Dialog.confirm($translate('CONFIRM_BEFORE_MAKE_PDF'));
    	if (executeDialog == null) {
            return;
        }
    	executeDialog.result.then(function () {
            $scope.enableLoadingBar();
            $scope.error = null;
            Message.showMessage(MsgConst.MSG_KEY_COMMON_INFO_MESSAGE_REPORT);
            $scope.result = {};
            var report = KKJP1180Report.query($scope.cond, function() {
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
                    Client.showErrorFromServer($scope.error, response.data.errors);
                } else {
                    Message.showMessage(MsgConst.MSG_KEY_SYSTEM_ERROR);
                }
                $scope.disableLoadingBar();
                setFocusCondReport($scope, $interval, FocusConst.COMBOBOX_NOT_INIT_FOCUS);
            });
        }, function () {
            setFocusCondReport($scope, $interval, FocusConst.COMBOBOX_NOT_INIT_FOCUS);
        });
	}
	
	$scope.reFreshCond = function() {
        return $scope.form.$pristine;
    }
	
    $scope.disableLoadingBar = function() {
        $scope.hiddenBar = true;
    }

    $scope.enableLoadingBar = function() {
        $scope.hiddenBar = false;
    }

    $scope.reFreshCond = function() {
    	return $scope.isFocus;
    }
    
    $scope.doClear = function() {
        var clearDialog = Dialog.confirm($translate('CONFIRM_BEFORE_CLEAR'));
        if (clearDialog == null) {
            return;
        }
        clearDialog.result.then(function () {
            $scope.error = Client.clearErrors();
            $scope.form.$setPristine();
            $scope.cond = {};
            $scope.cond.taisyodateY = $scope.getCurrentYear();
            $scope.cond.taisyodateM = $scope.getCurrentMonth();
            setFocusCondReport($scope, $interval, FocusConst.COMBOBOX_NOT_INIT_FOCUS);
            Message.showMessage(MsgConst.MSG_KEY_COMMON_INFO_MESSAGE_REPORT);
        }, function () {
            setFocusCondReport($scope, $interval, FocusConst.COMBOBOX_NOT_INIT_FOCUS);
        });
    }
    
	 $scope.canExecute = function() {
	     if ($scope.form.$invalid ) {
	             return false;
	         } else {
	             return true;
	         }
	     }

});