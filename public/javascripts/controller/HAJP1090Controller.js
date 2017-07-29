///////////////////////////////////////////////////////////////////////
// Copyright(C) 2015 NEC Solution Innovators, Ltd. All Rights Reserved.
///////////////////////////////////////////////////////////////////////

/* ====================================================================
 * 機能名称 : 物流スケジュールリスト(定番)
 * 改版履歴
 * Rev. 改版年月日   改版者名       内容
 * 1.0   2014.04.17   VuQQ      新規作成
 * =================================================================== */
var app = angular.module('hajp1090', ['hajp1090Services', 'ui','ui.select2','directives']);
app.controller("HAJP1090Ctrl",function($scope, $window, HAJP1090Report, $sce, Message, MsgConst, HttpConst, 
        Dialog, DialogInfo, $interval, FocusConst, Client) {
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
		var executeDialog = Dialog.confirm(Message.getMessage(MsgConst.MSG_KEY_CONFIRM_BEFORE_MAKE_PDF));
    	if (executeDialog == null) {
            return;
        }
    	executeDialog.result.then(function () {
            $scope.enableLoadingBar();
            $scope.error = null;
            Message.showMessage(MsgConst.MSG_KEY_COMMON_INFO_MESSAGE_REPORT);
            $scope.result = {};
            //Search data for report
            var report = HAJP1090Report.query($scope.cond, function() {
                $scope.result = report[0];
                $scope.result.pdfUrl = $sce.getTrustedResourceUrl($scope.result.pdfUrl);
                $window.open($scope.result.pdfUrl, '帳票参照画面',
                        'width=1200,height=680,menubar=no,toolbar=no,location=no,scrollbars=yes');
                Message.showMessage(MsgConst.MSG_KEY_COMMON_REPORT_SUCCESS);
                $scope.disableLoadingBar();
                setFocusCondReport($scope, $interval, FocusConst.COMBOBOX_NOT_INIT_FOCUS);
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
     * Get focus flag used to focus on first control
     * 
     * @param 
     * @return isFocus flag
     */
    $scope.reFreshCond = function() {
    	return $scope.isFocus;
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
            $scope.form.$setPristine();
            $scope.cond = {};
            $scope.cond.yyyy = $scope.getCurrentYear();
            $scope.cond.mm = $scope.getCurrentMonth();
            Message.showMessage(MsgConst.MSG_KEY_COMMON_INFO_MESSAGE_REPORT);
            
            setFocusCondReport($scope, $interval, FocusConst.COMBOBOX_NOT_INIT_FOCUS);
        }, function () {
        	setFocusCondReport($scope, $interval, FocusConst.COMBOBOX_NOT_INIT_FOCUS);
        });
    }
    
    /**
     * Determine when enable/disable Execute button
     * 
     * @param 
     * @return true:enable, false:disable
     */
	 $scope.canExecute = function() {
     if ($scope.form.$invalid ) {
             return false;
         } else {
             return true;
         }
     }
});