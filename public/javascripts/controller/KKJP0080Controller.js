// /////////////////////////////////////////////////////////////////////
// Copyright(C) 2015 NEC Solution Innovators, Ltd. All Rights Reserved.
// /////////////////////////////////////////////////////////////////////

/* ====================================================================
 * 機能名称 : KKJP0010
 * 改版履歴
 * Rev. 改版年月日   改版者名       内容
 * 1.0   2014.04.05   Hungtb      新規作成
 * =================================================================== */
var app = angular.module('kkjp0080', ['kkjp0080Services', 'ui', 'ui.select2', 'directives']);
app.controller("KKJP0080Ctrl", function($scope, $interval, K008TRHK, Message, MsgConst, HttpConst,
    Dialog, DialogInfo, AppConst, FocusConst, Client) {
    Message.showMessage(MsgConst.MSG_KEY_COMMON_INFO_MESSAGE_F9);
    $scope.hakkoDay = $scope.getCurrentDate();
    $scope.cond = {
        kaisyaCd: AppConst.KAISYA_CODE
    };
    $scope.result = {};
    $scope.isEditable = false;
    $scope.hasResult = false;
    $scope.isNewData = false;
    $scope.error = null;
    $scope.focusCond = FocusConst.TEXTINPUT_FOCUS;
    $scope.focusResult = FocusConst.NOT_FOCUS;
    /**
     * Get focus of condition form.
     */
    $scope.getFocusCond = function() {
        return $scope.focusCond;
    }

    /**
     * Get focus of result form.
     */
    $scope.getFocusResult = function(index) {
        return $scope.focusResult;
    }

    /**
     * Switch focus between condition and result form.
     */
    $scope.setFocus = function() {
        if ($scope.isEditable) {
            setFocusResult($scope, $interval, FocusConst.TEXTINPUT_FOCUS);
        } else {
            setFocusCond($scope, $interval, FocusConst.TEXTINPUT_FOCUS);
        }
    }

    /**
     * Get status of screen: can edit or not.
     * @return true or false
     */
    $scope.changeDisable = function() {
        return $scope.isEditable;
    }

    /**
     * Search for edit old data.
     */
    $scope.doSearchEdit = function() {
        $scope.result = {};
        $scope.error = null;
        $scope.cond.searchFlg = "1";
        $scope.isEditable = false;
        $scope.hasResult = false;
        $scope.isNewData = false;

        var records = K008TRHK.query($scope.cond, function() {
            $scope.result = records[0];
            $scope.originalResult = angular.copy($scope.result);
            // reset cond data
            $scope.cond.kaisyaCd = $scope.result.kaisyaCd;
            $scope.cond.kakToriKmk = $scope.result.kakToriKmk;
            $scope.isEditable = true;
            $scope.hasResult = true;
            $scope.isNewData = false;
            Message.showMessage(MsgConst.MSG_KEY_INFO_BUTTON_DELETE_SAVE);
            $scope.setFocus();
        }, function(response) {
            if (response.status === HttpConst.CODE_NOT_FOUND) {
                $scope.isEditable = false;
                $scope.hasResult = false;
                $scope.isNewData = false;

                var diag = Dialog.confirm(Message.getMessage(MsgConst.MSG_KEY_CONFIRM_NEW_REGISTERED));
                if (diag == null) {
                    return;
                }
                diag.result.then(
                    function() {
                        $scope.result = angular.copy($scope.cond);
                        $scope.isEditable = true;
                        $scope.hasResult = true;
                        $scope.isNewData = true;

                        Message.showMessage(MsgConst.MSG_KEY_INFO_BUTTON_SAVE);
                        $scope.setFocus();
                    }, function() {
                        Message.showMessage(MsgConst.MSG_KEY_COMMON_INFO_MESSAGE_F9);
                        $scope.setFocus();
                    }
                );
            } else if (response.status === HttpConst.CODE_BAD_REQUEST) {
                // エラー処理
                Message.showMessageWithContent(response.data.errors[0].level, response.data.errors[0].message);
                Client.showErrorFromServer($scope.error, response.data.errors);
                $scope.setFocus();
            } else {
                // エラー処理
                Message.showMessage(MsgConst.MSG_KEY_SYSTEM_ERROR);
                $scope.setFocus();
            }
        });
    }

    /**
     * Insert/Update data to database.
     */
    $scope.doInsertUpdate = function() {
        if ($scope.isNewData) {
            $scope.doInsert();
        } else {
            $scope.doUpdate();
        }
    }

    /**
     * Insert data to database.
     */
    $scope.doInsert = function() {
        var diag = Dialog.confirm(Message.getMessage(MsgConst.MSG_KEY_CONFIRM_BEFORE_INSERT));
        if (diag == null) {
            return;
        }
        diag.result.then(
            function() {
                $scope.error = null;
                K008TRHK.save($scope.result, function() {
                    // 再度検索可能な状態にする
                    $scope.isNewData = false;
                    $scope.isEditable = false;
                    Message.showMessage(MsgConst.MSG_KEY_INSERT_SUCCESS);
                    $scope.setFocus();
                }, function(response) {
                    if (response.status === HttpConst.CODE_BAD_REQUEST) {
                        // エラー処理
                        Message.showMessageWithContent(response.data.errors[0].level, response.data.errors[0].message);
                        Client.showErrorFromServer($scope.error, response.data.errors);
                    } else {
                        // エラー処理
                        Message.showMessage(MsgConst.MSG_KEY_SYSTEM_ERROR);
                    }
                    $scope.setFocus();
                });
            }, function() {
                $scope.setFocus();
            }
        );
    }

    /**
     * Update data to database.
     */
    $scope.doUpdate = function() {
        var diag = Dialog.confirm(Message.getMessage(MsgConst.MSG_KEY_CONFIRM_BEFORE_UPDATE));
        if (diag == null) {
            return;
        }
        diag.result.then(
            function() {
                $scope.error = null;
                K008TRHK.update($scope.result, function() {
                    // 再度検索可能な状態にする
                    $scope.isEditable = false;

                    Message.showMessage(MsgConst.MSG_KEY_UPDATE_SUCCESS);
                    $scope.setFocus();
                }, function(response) {
                    if (response.status === HttpConst.CODE_BAD_REQUEST) {
                        // エラー処理
                        Message.showMessageWithContent(response.data.errors[0].level, response.data.errors[0].message);
                        Client.showErrorFromServer($scope.error, response.data.errors);
                    } else {
                        // エラー処理
                        Message.showMessage(MsgConst.MSG_KEY_SYSTEM_ERROR);
                    }
                    $scope.setFocus();
                });
            }, function() {
                $scope.setFocus();
            }
        );
    }

    /**
     * Delete data from database.
     */
    $scope.doDelete = function() {
        var diag = Dialog.confirm(Message.getMessage(MsgConst.MSG_KEY_CONFIRM_BEFORE_DELETE));
        if (diag == null) {
            return;
        }
        diag.result.then(
            function() {
                $scope.error = null;
                K008TRHK.delete($scope.cond, function() {
                    // 再度検索可能な状態にする
                    $scope.isEditable = false;
                    $scope.originalResult = {}; // lochv edit 15/3/2014
                    Message.showMessage(MsgConst.MSG_KEY_DELETE_SUCCESS);
                    $scope.setFocus();
                }, function(response) {
                    if (response.status === HttpConst.CODE_BAD_REQUEST) {
                        // エラー処理
                        Message.showMessageWithContent(response.data.errors[0].level, response.data.errors[0].message);
                        Client.showErrorFromServer($scope.error, response.data.errors);
                    } else {
                        Message.showMessage(MsgConst.MSG_KEY_SYSTEM_ERROR);
                    }
                    $scope.setFocus();
                });
            }, function() {
                $scope.setFocus();
            }
        );
    }

    /**
     * Clear input data of controls.
     */
    $scope.doClear = function() {
        var diag = Dialog.confirm(Message.getMessage(MsgConst.MSG_KEY_CONFIRM_BEFORE_CLEAR));
        if (diag == null) {
            return;
        }
        diag.result.then(
            function() {
                if ($scope.hasResult == false && $scope.isEditable == false) {
                    $scope.cond = {
                        kaisyaCd: AppConst.KAISYA_CODE
                    };
                }
                $scope.result = {};
                $scope.error = Client.clearErrors();
                $scope.originalResult = {};
                $scope.hasResult = false;
                $scope.isEditable = false;
                $scope.isNewData = false;
                $scope.form.$setPristine();
                Message.showMessage(MsgConst.MSG_KEY_COMMON_INFO_MESSAGE_F9);
                $scope.setFocus();
            }, function() {
                $scope.setFocus();
            }
        );
    }

    /**
     * Check having new data to Enable/Disable Delete button.
     * 
     * @return true:enable false:disable
     */
    $scope.canDelete = function() {
        if ($scope.isEditable && !$scope.isNewData) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Check if have changing data on screen to Enable/Disable Save button.
     * 
     * @return true:enable false:disable
     */
    $scope.canSave = function() {
        if ($scope.isEditable && !$scope.form.$invalid && !angular.equals($scope.result, $scope.originalResult)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Check controls of the condForm area to Enable/Disable SearchEdit button.
     * 
     * @return true:enable false:disable
     */
    $scope.canSearchEdit = function() {
        if ($scope.form.condForm == null) {
            return;
        }

        if ($scope.isEditable || $scope.form.condForm.$invalid) {
            return false;
        } else {
            return true;
        }
    }
});