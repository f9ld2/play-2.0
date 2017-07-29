///////////////////////////////////////////////////////////////////////
// Copyright(C) 2015 NEC Solution Innovators, Ltd. All Rights Reserved.
///////////////////////////////////////////////////////////////////////

/* ====================================================================
 * 機能名称 : 仮締設定入力
 * 改版履歴
 * Rev. 改版年月日   改版者名       内容
 * 1.0   2014.03.28   PhucLT      新規作成
 * =================================================================== */

var app = angular.module('sijp0190', ['sijp0190Services', 'ui', 'ui.select2', 'directives']);
app.controller("SIJP0190Ctrl", function($scope, $interval, FocusConst, Dialog, Message, MsgConst, HttpConst, SIJP0190, Client) {
    Message.showMessage(MsgConst.MSG_KEY_COMMON_INFO_MESSAGE);

    $scope.cond = {};
    $scope.result = {};
    $scope.isEditable = false;
    $scope.hasResult = false;
    $scope.isNewData = false;
    $scope.cloneQueryResult = {};
    $scope.focusCond = FocusConst.TEXTINPUT_FOCUS;
    $scope.focusResult = FocusConst.NOT_FOCUS;

    /**
     * focus getter for condition area
     *
     * @param
     * @return integer 0: not focus, 1, 2: focus
     */
    $scope.getFocusCond = function() {
        return $scope.focusCond;
    }

    /**
     * focus getter for result area
     *
     * @param
     * @return integer 0: not focus, 1, 2: focus
     */
    $scope.getFocusResult = function() {
        return $scope.focusResult;
    }

    /**
     * set focus for condition or result area
     *
     * @param
     * @return
     */
    $scope.setFocus = function() {
        if ($scope.isEditable) {
            setFocusResult($scope, $interval, FocusConst.TEXTINPUT_FOCUS);
        } else {
            setFocusCond($scope, $interval, FocusConst.TEXTINPUT_FOCUS);
        }
    }

    /**
     * getter for disabling result area
     *
     * @param
     * @return boolean, false: not disable result area, true: disable result area
     */
    $scope.disabledResult = function() {
        return !$scope.isEditable;
    }

    /**
     * function to handle response from server
     *
     * @param response: response object of http resource
     * @return
     */
    var responseHandling = function(response) {
        if (response.status === HttpConst.CODE_NOT_FOUND) {
            Message.showMessage(MsgConst.MSG_KEY_DATA_NOT_EXIST);
        } else if (response.status === HttpConst.CODE_BAD_REQUEST) {
            if (response.data.errors != undefined) {
                Message.showMessageWithContent(response.data.errors[0].level, response.data.errors[0].message);
                Client.showErrorFromServer($scope.error, response.data.errors);
            }
        } else {
            Message.showMessage(MsgConst.MSG_KEY_SYSTEM_ERROR);
        }
        $scope.setFocus();
    }

    /**
     * function to enable button search
     *
     * @param
     * @return boolean, true: enable, false: disable
     */
    $scope.canSearch = function() {
        return !$scope.isEditable;
    }

    /**
     * function to enable button save
     *
     * @param
     * @return boolean, true: enable, false: disable
     */
    $scope.canSave = function() {
        var hnhnymd = $scope.result.hnhnymd == undefined ? null : $scope.result.hnhnymd;
        var tnhnymd = $scope.result.tnhnymd == undefined ? null : $scope.result.tnhnymd;

        return ($scope.dateForm.$valid && $scope.isEditable && (hnhnymd != $scope.cloneQueryResult.hnhnymd || tnhnymd != $scope.cloneQueryResult
            .tnhnymd));
    }

    /**
     * function to enable button searchedit
     *
     * @param
     * @return boolean, true: enable, false: disable
     */
    $scope.canSearchEdit = function() {
        return !$scope.isEditable;
    }

    /**
     * function to handle button searchEdit
     *
     * @param
     * @return
     */
    $scope.doSearch = function() {
        if (!$scope.canSearch()) {
            return;
        }

        $scope.isEditable = false;
        $scope.hasResult = false;
        $scope.result = {};
        $scope.cloneQueryResult = {};

        var showResult = SIJP0190.show({}, function() {
            $scope.result = showResult;

            $scope.isEditable = false;
            $scope.hasResult = true;

            Message.showMessage(MsgConst.MSG_KEY_DISPLAY_CORRECT_DATA);
            $scope.setFocus();
        }, responseHandling);
    }

    /**
     * function to handle button searchEdit
     *
     * @param
     * @return
     */
    $scope.doSearchEdit = function() {
        if (!$scope.canSearchEdit()) {
            return;
        }

        $scope.isEditable = false;
        $scope.hasResult = false;
        $scope.result = {};
        $scope.cloneQueryResult = {};

        var showResult = SIJP0190.show({}, function() {
            $scope.result = showResult;
            $scope.cloneQueryResult = angular.copy($scope.result);

            $scope.isEditable = true;
            $scope.hasResult = true;

            Message.showMessage(MsgConst.MSG_KEY_INFO_BUTTON_SAVE);
            $scope.setFocus();
        }, responseHandling);
    }

    /**
     * function to prepare data to post to server
     *
     * @param
     * @return object: data to post to server
     */
    $scope.prepareData = function() {
        var data = angular.copy($scope.result);
        data.hnhnymd = data.hnhnymd == undefined ? null : data.hnhnymd;
        data.tnhnymd = data.tnhnymd == undefined ? null : data.tnhnymd;
        return data;
    }

    /**
     * function to handle button save
     *
     * @param
     * @return
     */
    $scope.doSave = function() {
        if (!$scope.canSave()) {
            return;
        }

        var diag = Dialog.confirm(Message.getMessage(MsgConst.MSG_KEY_CONFIRM_BEFORE_INSERT));
        if (diag == null) {
            return;
        }
        diag.result.then(function() {
            var showResult = SIJP0190.save($scope.prepareData(), function() {
                $scope.isEditable = false;

                Message.showMessage(MsgConst.MSG_KEY_UPDATE_SUCCESS);
                $scope.setFocus();
            }, responseHandling);
        }, function() {
            $scope.setFocus();
        });
    }

    /**
     * function to handle button clear
     *
     * @param
     * @return
     */
    $scope.doClear = function() {
        var diag = Dialog.confirm(Message.getMessage(MsgConst.MSG_KEY_CONFIRM_BEFORE_CLEAR));
        if (diag == null) {
            return;
        }
        diag.result.then(function() {
            $scope.result.hnhnymd = "";
            $scope.result.tnhnymd = "";
            $scope.form.$setPristine();
            $scope.hasResult = false;

            $scope.isEditable = false;
            $scope.isNewData = false;
            $scope.setFocus();
            Message.showMessage(MsgConst.MSG_KEY_COMMON_INFO_MESSAGE);
        }, function() {
            $scope.setFocus();
        });
    }
});