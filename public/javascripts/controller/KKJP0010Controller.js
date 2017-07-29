// /////////////////////////////////////////////////////////////////////
// Copyright(C) 2015 NEC Solution Innovators, Ltd. All Rights Reserved.
// /////////////////////////////////////////////////////////////////////

/* ====================================================================
 * 機能名称 : KKJP0010
 * 改版履歴
 * Rev. 改版年月日   改版者名       内容
 * 1.0   2014.04.05   Hungtb      新規作成
 * =================================================================== */
var app = angular.module('kkjp0010', ['kkjp0010Services', 'ui', 'ui.select2', 'directives', 'ngGrid']);
app.controller("KKJP0010Ctrl", function($scope, $filter, Message, MsgConst, HttpConst, FocusConst,
    KKJP0010Resource, KKJP0010ResourceExt, Dialog, DialogInfo, $interval, $rootScope, $timeout, AppConst, GetJotaiNm, Client) {
    Message.showMessage(MsgConst.MSG_KEY_COMMON_INFO_MESSAGE_F9);
    $scope.cond = {
        kaisyaCd: AppConst.KAISYA_CODE
    };
    $scope.result = {};
    $scope.result.triArea = [];
    $scope.isEditable = false;
    $scope.hasResult = false;
    $scope.isNewData = false;
    $scope.errorsTable = [];
    $scope.rowNoIn = '';
    $scope.mainToriCdIn = '';
    $scope.error = null;

    // for grid
    $scope.totalServerItems = 0;
    $scope.currentRow = undefined;

    /* Grid definition */
    $scope.pagingOptions = {
        pageSizes: [20],
        pageSize: 20,
        currentPage: 1
    };
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
            setFocusResult($scope, $interval, FocusConst.COMBOBOX_NOT_INIT_FOCUS);
        } else {
            setFocusCond($scope, $interval, FocusConst.TEXTINPUT_FOCUS);
        }
    }

    /**
     * Reset data of condition form.
     */
    $scope.resetCondForm = function() {
        if ($scope.cond != undefined) {
            $scope.cond.shrSyoriNo = "";
            $scope.cond.shrDate = "";
            $scope.cond.simeDate = "";
            $scope.cond.kaisyaCd = AppConst.KAISYA_CODE;
            $scope.cond.kakToriKmk = "";
            $scope.cond.toriNm = "";
            $scope.cond.toriNm = "";
            $scope.cond.tekiyo = "";
        }
    }

    /**
     * Reset data of result form.
     */
    $scope.resetResultForm = function() {
        if ($scope.result != undefined) {
            $scope.rowNoIn = "";
            $scope.mainToriCdIn = "";
            $scope.result.mainToriCd = "";
            $scope.result.shrKin = "";
            $scope.result.sotoTaxKin = "";
            $scope.result.jotaiKbn = "";
            $scope.result.shrKinAll = "";
            $scope.result.triArea = [];
        }
        $scope.resultOld = [];
        $scope.condOld = {};
        $scope.pagingOptions.currentPage = 1;
        $scope.currentRow = undefined;
        $scope.totalServerItems = 0;
        $scope.errorsTable = [];
    }

    /**
     * Set page of grid.
     */
    $scope.setPagingData = function() {
        if (isEmptyObject($scope.result.triArea)) {
            $scope.pagingResult = $scope.result.triArea;
        } else {
            var page = $scope.pagingOptions.currentPage;
            var pageSize = $scope.pagingOptions.pageSize;
            $scope.pagingResult = $scope.result.triArea.slice((page - 1) * pageSize, page * pageSize);
        }
        // if there is no apply or digest going on.
        if (!$scope.$$phase) {
            $scope.$apply();
        }
    };

    /**
     * Auto trace change of data and refresh grid.
     */
    $scope.$watchCollection('[pagingOptions.currentPage, result.triArea]', function() {
        $scope.setPagingData();
    });

    /**
     * Template of grid's row.
     */
    var rowTemplate = '<div ng-repeat="col in renderedColumns" ng-class="col.colIndex()" class="ngCell {{col.cellClass}} ' +
        '{{errorColoring((row.rowIndex + ((pagingOptions.pageSize)*(pagingOptions.currentPage - 1))), col.index)}}">' +
        '<div class="ngVerticalBar" ng-style="{height: rowHeight}" ng-class="{ ngVerticalBarVisible: !$last }">&nbsp;</div>' +
        '<div ng-cell-custom></div></div>';

    /**
     * Defined grid area.
     */
    $scope.gridOptionsKKJP0010TriArea = {
        data: 'pagingResult',
        enablePaging: true,
        showFooter: true,
        footerRowHeight: 30,
        headerRowHeight: 20,
        rowHeight: 23,
        enableRowSelection: false,
        enableCellSelection: true,
        rowTemplate: rowTemplate,
        enableSorting: false,
        enableColumnResize: false,
        pagingOptions: $scope.pagingOptions,
        totalServerItems: 'totalServerItems',
        footerTemplate: '/assets/templates/footerTemplate.html',
        columnDefs: [{
            field: 'rowDisplay',
            displayName: '行',
            cellClass: "text-center",
            width: 30,
            cellTemplate: '<div class="ngCellText" ng-class="{errorCell: (errorsTable.length > 0 && errorsTable[row.rowIndex + ' +
                '(pagingOptions.currentPage - 1)*pagingOptions.pageSize].rowNo == true)}"><span ng-cell-text>{{row.rowIndex + (pagingOptions.currentPage - 1)*pagingOptions.pageSize+ 1}}</span></div>'
        }, {
            field: 'delFlag',
            displayName: '削',
            width: 30,
            cellClass: "text-center",
            cellTemplate: '<input class="ngCellText checkbox" type="checkbox" disabled id="delFlag" name="delFlag" ng-model="queryResult[row.rowIndex + pagingOptions.pageSize*(pagingOptions.currentPage - 1)].delFlag" />'
        }, {
            field: 'jotaiKbn',
            displayName: '確定区分',
            cellClass: "text-center",
            width: 60,
            cellTemplate: '<div class="ngCellText" ng-class="{errorCell: (errorsTable.length > 0 && errorsTable[row.rowIndex + ' +
                '(pagingOptions.currentPage - 1)*pagingOptions.pageSize].jotaiKbn == true)}"><span ng-cell-text>{{row.getProperty(col.field)}}</span></div>'
        }, {
            field: 'nm',
            displayName: '状態',
            cellClass: "text-center",
            width: 60,
            cellTemplate: '<div class="ngCellText" ng-class="{errorCell: (errorsTable.length > 0 && errorsTable[row.rowIndex + ' +
                '(pagingOptions.currentPage - 1)*pagingOptions.pageSize].nm == true)}"><span ng-cell-text>{{row.getProperty(col.field)}}</span></div>'
        }, {
            field: 'mainToriCd',
            displayName: '代表取引先コード',
            cellClass: "text-center",
            width: 90,
            cellTemplate: '<div class="ngCellText" ng-class="{errorCell: (errorsTable.length > 0 && errorsTable[row.rowIndex + ' +
                '(pagingOptions.currentPage - 1)*pagingOptions.pageSize].mainToriCd == true)}"><span ng-cell-text>{{row.getProperty(col.field)}}</span></div>'
        }, {
            field: 'toriNm',
            displayName: '代表取引先名称',
            cellClass: "text-left",
            width: 250,
            cellTemplate: '<div class="ngCellText" ng-class="{errorCell: (errorsTable.length > 0 && errorsTable[row.rowIndex + ' +
                '(pagingOptions.currentPage - 1)*pagingOptions.pageSize].toriNm == true)}"><span ng-cell-text>{{row.getProperty(col.field)}}</span></div>'
        }, {
            field: 'shrKin',
            displayName: '金額',
            cellClass: "text-right",
            width: 115,
            cellTemplate: '<div class="ngCellText" ng-class="{errorCell: (errorsTable.length > 0 && errorsTable[row.rowIndex + ' +
                '(pagingOptions.currentPage - 1)*pagingOptions.pageSize].shrKin == true)}"><span ng-cell-text>{{row.getProperty(col.field) | number:0}}</span></div>'
        }, {
            field: 'sotoTaxKin',
            displayName: '消費税',
            cellClass: "text-right",
            width: 115,
            cellTemplate: '<div class="ngCellText" ng-class="{errorCell: (errorsTable.length > 0 && errorsTable[row.rowIndex + ' +
                '(pagingOptions.currentPage - 1)*pagingOptions.pageSize].sotoTaxKin == true)}"><span ng-cell-text>{{row.getProperty(col.field) | number:0}}</span></div>'
        }, {
            field: 'shrKinAll',
            displayName: '支払金額',
            cellClass: "text-right",
            width: 115,
            cellTemplate: '<div class="ngCellText" ng-class="{errorCell: (errorsTable.length > 0 && errorsTable[row.rowIndex + ' +
                '(pagingOptions.currentPage - 1)*pagingOptions.pageSize].shrKinAll == true)}"><span ng-cell-text>{{row.getProperty(col.field) | number:0}}</span></div>'
        }, {
            field: 'status',
            displayName: '更新',
            cellClass: "text-center",
            width: 83,
            cellTemplate: '<div class="ngCellText"><span class="{{result.triArea[row.rowIndex + pagingOptions.pageSize*(pagingOptions.currentPage - 1)].status}}">&nbsp;</span></div>'
        }]
    };

    /**
     * Color error row.
     * @return class css of row if it is error row.
     */
    $scope.errorColoring = function(rowIndex, colIndex) {
        if ($scope.errorsTable != null && $scope.errorsTable != undefined && $scope.errorsTable.length > 0) {
            var last = $scope.gridOptionsKKJP0010TriArea.columnDefs.length - 1;
            if ($scope.errorsTable[rowIndex] != undefined) {
                var rowError = $scope.errorsTable[rowIndex].rowError;
                if (!isEmpty(rowError) && rowError) {
                    switch (colIndex) {
                        case 0:
                            return 'errorRowLeft';
                        case last:
                            return 'errorRowRight';
                        default:
                            return 'errorRowCenter';
                    }
                }
            }
        }
        return '';
    }

    /**
     * Get status of screen: can edit or not.
     * @return true or false
     */
    $scope.changeDisable = function() {
        return $scope.isEditable;
    }

    /**
     * Search and fill found data in edit area or clear if found no data.
     */
    $scope.gridSearch = function() {
        var searchSuccess = false;
        if (!isEmptyObject($scope.result.triArea)) {
            if ($scope.result != undefined) {
                var mainToriCd = $scope.mainToriCdIn;
                // filter data
                var filteredResult = [];
                if (!isEmpty($scope.rowNoIn)) {
                    var index = isNaN(parseInt($scope.rowNoIn)) ? -1 : parseInt($scope.rowNoIn);
                    if (index > 0 && index <= $scope.result.triArea.length) {
                        filteredResult.push($scope.result.triArea[index - 1]);
                    }
                } else {
                    filteredResult = $scope.result.triArea;
                }
                if (!isEmpty(mainToriCd)) {
                    filteredResult = filteredResult.filter(function(element) {
                        if (element.mainToriCd == mainToriCd) {
                            return element;
                        }
                    });
                }

                if (filteredResult.length > 0) {
                    var record = filteredResult[0];
                    // if there is a record matching filter
                    if ($scope.result.triArea.indexOf(record) != -1) {
                        $scope.currentRow = angular.copy(record);
                        // binding value (jotai <-> kakubun)
                        $scope.setEditElement(record);
                        searchSuccess = true;
                    }
                }
            }
        }

        if (!searchSuccess) {
            $scope.setEditElement({
                mainToriCd: "",
                mainToriNm: "",
                jotaiKbn: "",
                shrKin: "",
                sotoTaxKin: "",
                shrKinAll: ""
            });
            $scope.setFocus();
        } else {
            $scope.setFocus();
        }
    }

    /**
     * Save or update data in edit area to grid.
     */
    $scope.gridSave = function() {
        if ($scope.result != undefined && !isEmpty($scope.result.mainToriCd)) {
            if ($scope.result.triArea == undefined) {
                $scope.result.triArea = [];
            }

            if ($scope.checkCondForm()) {
                var n = $scope.result.triArea.length;
                var elem = undefined;
                // find element
                for (var i = 0; i < n; i++) {
                    if ($scope.result.triArea[i].mainToriCd == $scope.result.mainToriCd) {
                        elem = $scope.result.triArea[i];
                        break;
                    }
                }

                if (elem != undefined) {
                    var result = $scope.result;

                    if (elem.jotaiKbn != result.jotaiKbn || elem.shrKin != result.shrKin || elem.sotoTaxKin != result.sotoTaxKin) {
                        var index = $scope.result.triArea.indexOf(elem);
                        if (index != -1 && $scope.errorsTable != undefined) {
                            // Reset row error
                            $scope.errorsTable[index] = {};
                        }
                        // update old data; keep actKbn if insert or update
                        elem.jotaiKbn = $scope.result.jotaiKbn;
                        elem.nm = $scope.getJotaiNm();
                        elem.toriNm = $scope.result.mainToriNm;
                        elem.shrKin = $scope.result.shrKin;
                        elem.sotoTaxKin = $scope.result.sotoTaxKin;
                        elem.shrKinAll = $scope.result.shrKinAll;

                        if (elem.actKbn == 0) {
                            elem.actKbn = 2;
                            elem.status = "gridStatusUpdate";
                        } else if (elem.actKbn == 2 && $scope.resultOld != undefined && $scope.resultOld.length > 0) {
                            var oldElem = undefined;
                            for (var i = 0; i < $scope.resultOld.length; i++) {
                                if ($scope.resultOld[i].mainToriCd == $scope.result.mainToriCd) {
                                    oldElem = $scope.resultOld[i];
                                    break;
                                }
                            }
                            if (oldElem != undefined) {
                                if (elem.jotaiKbn == oldElem.jotaiKbn && elem.shrKin == oldElem.shrKin && elem.sotoTaxKin == oldElem.sotoTaxKin) {
                                    elem.actKbn = 0;
                                    elem.status = "";
                                }
                            }
                        }
                        if (isEmpty(elem.sotoTaxKin)) {
                            $scope.validTax(elem);
                        }
                    }
                    $scope.setFocus();
                } else {
                    // create new data
                    var data = {
                        actKbn: 1,
                        rowNo: ($scope.result.triArea.length + 1),
                        // for view
                        mainToriCd: $scope.result.mainToriCd,
                        toriNm: $scope.result.mainToriNm,
                        jotaiKbn: $scope.result.jotaiKbn,
                        nm: $scope.getJotaiNm(),
                        shrKin: $scope.result.shrKin,
                        sotoTaxKin: $scope.result.sotoTaxKin,
                        shrKinAll: $scope.result.shrKinAll,
                        status: "gridStatusInsert"
                    };

                    $scope.result.triArea.unshift(data);
                    if ($scope.errorsTable != undefined) {
                        $scope.errorsTable.unshift([]);
                    }
                    // for grid
                    $scope.totalServerItems = $scope.result.triArea.length;
                    var currentPage = 1;
                    if ($scope.pagingOptions.currentPage != currentPage && currentPage != 0) {
                        $scope.pagingOptions.currentPage = currentPage;
                    } else {
                        $scope.setPagingData();
                    }

                    if (isEmpty(data.sotoTaxKin)) {
                        $scope.validTax(data);
                    }
                    $scope.setFocus();
                }
            } else {
                if ($scope.error != undefined && $scope.error != null && $scope.error.length > 0) {
                    // Message.showMessage($scope.error[0].code);
                } else {
                    if ($scope.isEditable && $scope.isNewData) {
                        Message.showMessage(MsgConst.MSG_KEY_INFO_BUTTON_DELETE_SAVE);
                    } else if ($scope.sEditable && $scope.hasResult) {
                        Message.showMessage(MsgConst.MSG_KEY_INFO_BUTTON_SAVE);
                    }
                }
                $scope.setFocus();
            }
        }
    }

    /**
     * Search for edit old data.
     */
    $scope.doSearchEdit = function() {
        $scope.resetResultForm();
        $scope.isEditable = false;
        $scope.hasResult = false;
        $scope.isNewData = false;

        var control = KKJP0010Resource.query($scope.cond, function() {
            var resultForm = control[0];
            // format data
            resultForm.shrKin = toString(resultForm.shrKin);
            resultForm.sotoTaxKin = toString(resultForm.sotoTaxKin);

            if (resultForm.triArea == undefined)
                resultForm.triArea = [];

            var n = resultForm.triArea.length;
            for (var i = 0; i < n; i++) {
                resultForm.triArea[i].shrKin = toString(resultForm.triArea[i].shrKin);
                resultForm.triArea[i].sotoTaxKin = toString(resultForm.triArea[i].sotoTaxKin);
                resultForm.triArea[i].shrKinAll = toString(resultForm.triArea[i].shrKinAll);
                resultForm.triArea[i].status = "";
            }

            // return data
            // for header form
            if (resultForm.triArea.length > 0) {
                $scope.cond.shrSyoriNo = resultForm.shrSyoriNo;
                $scope.cond.shrDate = resultForm.shrDate;
                $scope.cond.simeDate = resultForm.simeDate;
                $scope.cond.kaisyaCd = resultForm.kaisyaCd;
                $scope.cond.kakToriKmk = resultForm.kakToriKmk;
                $scope.cond.toriNm = resultForm.toriNm;
                $scope.cond.tekiyo = resultForm.tekiyo;

                $scope.isEditable = true;
                $scope.hasResult = true;
                $scope.isNewData = false;
                Message.showMessage(MsgConst.MSG_KEY_INFO_BUTTON_DELETE_SAVE);
                $scope.setFocus();
            } else {
                if (resultForm.errRes != undefined && resultForm.errRes.errors != undefined &&
                    resultForm.errRes.errors.length > 0) {
                    $scope.cond.shrSyoriNo = resultForm.shrSyoriNo;

                    Message.showMessageWithContent(resultForm.errRes.errors[0].level, resultForm.errRes.errors[0].message);
                    $scope.error = Client.showErrorFromServer($scope.error, resultForm.errRes.errors);

                    $scope.setFocus();
                } else {
                    var diag = Dialog.confirm(Message.getMessage(MsgConst.MSG_KEY_CONFIRM_NEW_REGISTERED));
                    if (diag == null) {
                        return;
                    }
                    diag.result.then(
                        function() {
                            $scope.cond.shrSyoriNo = resultForm.shrSyoriNo;
                            $scope.result = angular.copy($scope.cond);
                            $scope.result.triArea = [];
                            $scope.isEditable = true;
                            $scope.hasResult = false;
                            $scope.isNewData = true;
                            Message.showMessage(MsgConst.MSG_KEY_INFO_BUTTON_SAVE);

                            $scope.setFocus();
                        }, function() {
                            Message.showMessage(MsgConst.MSG_KEY_COMMON_INFO_MESSAGE_F9);

                            $scope.setFocus();
                        }
                    );
                }
            }
            // for grid
            $scope.result.triArea = angular.copy(resultForm.triArea);
            $scope.totalServerItems = $scope.result.triArea.length;
            // for edit
            $scope.condOld = angular.copy($scope.cond);
            $scope.resultOld = angular.copy(resultForm.triArea);
        }, function(response) {
            if (response.status === HttpConst.CODE_BAD_REQUEST) {
                // エラー処理
                Message.showMessageWithContent(response.data.errors[0].level, response.data.errors[0].message);
                $scope.error = Client.showErrorFromServer($scope.error, response.data.errors);
            } else {
                Message.showMessage(MsgConst.MSG_KEY_SYSTEM_ERROR);
            }
            $scope.setFocus();
        });
    }

    /**
     * Change current page of grid to page have error data.
     */
    $scope.jumpPage = function() {
        if ($scope.errorsTable != null && $scope.errorsTable != undefined && $scope.errorsTable.length > 0) {
            for (var i = 0; i < $scope.errorsTable.length; i++) {
                if ($scope.errorsTable[i] != undefined) {
                    var rowError = $scope.errorsTable[i].rowError;
                    if (!isEmpty(rowError) && rowError) {
                        var currentPage = Math.ceil((i + 1) / $scope.pagingOptions.pageSize);
                        if (currentPage != 0 && $scope.pagingOptions.currentPage != currentPage) {
                            $scope.pagingOptions.currentPage = currentPage;
                        } else if (currentPage == 0) {
                            $scope.pagingOptions.currentPage = 1;
                        }
                        var row = $scope.result.triArea[i];
                        $scope.currentRow = angular.copy(row);
                        // binding value (jotai <-> kakubun)
                        $scope.result.mainToriCd = row.mainToriCd;
                        $scope.result.jotaiKbn = row.jotaiKbn;
                        $scope.result.shrKin = row.shrKin;
                        $scope.result.sotoTaxKin = row.sotoTaxKin;
                        $scope.result.shrKinAll = row.shrKinAll;
                    }
                }
            }
        }
    }

    /**
     * Get jotai name of jotaiKbn that select in jotai combobox.
     * @return String
     */
    $scope.getJotaiNm = function() {
        return GetJotaiNm();
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
                $scope.errorsTable = [];

                /* Copy for pass validate */
                var data = {
                    kaisyaCd: $scope.cond.kaisyaCd,
                    shrSyoriNo: $scope.cond.shrSyoriNo,
                    kakToriKmk: $scope.cond.kakToriKmk,
                    toriNm: $scope.cond.toriNm,
                    shrDate: $scope.cond.shrDate,
                    simeDate: $scope.cond.simeDate,
                    tekiyo: $scope.cond.tekiyo
                }
                data.triArea = $scope.getChangeData($scope.result.triArea);
                /* end copy */

                var resData = KKJP0010Resource.save(data, function(response) {
                    if (resData != undefined && resData.errors != undefined && resData.errors.length > 0) {
                        var msg = $scope.getErrorMsg(resData.errors);
                        var diag = Dialog.confirm(msg);
                        if (diag == null) {
                            return;
                        }
                        diag.result.then(
                            function() {
                                $scope.result.skipWarning = true;
                                KKJP0010Resource.save($scope.result, function(response) {
                                    $scope.result.skipWarning = false;
                                    $scope.isEditable = false;
                                    $scope.hasResult = true;
                                    $scope.isNewData = false;

                                    Message.showMessage(MsgConst.MSG_KEY_INSERT_SUCCESS);
                                    $scope.setFocus();
                                }, responseHandling);
                            }, function() {
                                $scope.error = null;
                                $scope.errorsTable = resData.gridErrors;
                                Message.showMessageWithContent(resData.errors[0].level, resData.errors[0].message);
                                $scope.setFocus();
                            });
                    } else {
                        $scope.isEditable = false;
                        $scope.hasResult = true;
                        $scope.isNewData = false;

                        Message.showMessage(MsgConst.MSG_KEY_INSERT_SUCCESS);
                        $scope.setFocus();
                    }
                }, responseHandling);
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
                $scope.errorsTable = [];

                /* Copy for pass validate */
                var data = {
                    kaisyaCd: $scope.cond.kaisyaCd,
                    shrSyoriNo: $scope.cond.shrSyoriNo,
                    kakToriKmk: $scope.cond.kakToriKmk,
                    toriNm: $scope.cond.toriNm,
                    shrDate: $scope.cond.shrDate,
                    simeDate: $scope.cond.simeDate,
                    tekiyo: $scope.cond.tekiyo
                }
                data.triArea = $scope.getChangeData($scope.result.triArea);
                /* end copy */

                var resData = KKJP0010Resource.update(data, function(response) {
                    if (resData != undefined && resData.errors != undefined && resData.errors.length > 0) {
                        var msg = $scope.getErrorMsg(resData.errors);
                        var diag = Dialog.confirm(msg);
                        if (diag == null) {
                            return;
                        }
                        diag.result.then(
                            function() {
                                data.skipWarning = true;
                                KKJP0010Resource.update(data, function(response) {
                                    $scope.result.skipWarning = false;
                                    $scope.isEditable = false;
                                    $scope.hasResult = true;
                                    $scope.isNewData = false;

                                    Message.showMessage(MsgConst.MSG_KEY_UPDATE_SUCCESS);
                                    $scope.setFocus();
                                }, responseHandling);
                            }, function() {
                                $scope.error = null;
                                $scope.errorsTable = resData.gridErrors;
                                Message.showMessageWithContent(resData.errors[0].level, resData.errors[0].message);
                                $scope.setFocus();
                            });
                    } else {
                        $scope.isEditable = false;
                        $scope.hasResult = true;
                        $scope.isNewData = false;

                        Message.showMessage(MsgConst.MSG_KEY_UPDATE_SUCCESS);
                        $scope.setFocus();
                    }
                }, responseHandling);
            }, function() {
                $scope.setFocus();
            }
        );
    }

    /**
     * Delete data in database.
     */
    $scope.doDelete = function() {
        var diag = Dialog.confirm(Message.getMessage(MsgConst.MSG_KEY_CONFIRM_BEFORE_DELETE));
        if (diag == null) {
            return;
        }
        diag.result.then(
            function() {
                $scope.error = null;
                $scope.errorsTable = [];

                var data = angular.copy($scope.cond);
                KKJP0010Resource.delete(data, function() {
                    $scope.isEditable = false;
                    $scope.hasResult = true;
                    $scope.isNewData = false;

                    $scope.resultOld = [];
                    $scope.condOld = {};
                    Message.showMessage(MsgConst.MSG_KEY_DELETE_SUCCESS);
                    $scope.setFocus();
                }, responseHandling);
            }, function() {
                $scope.setFocus();
            }
        );
    }

    /**
     * Clear result or condition area.
     */
    $scope.doClear = function() {
        var diag = Dialog.confirm(Message.getMessage(MsgConst.MSG_KEY_CONFIRM_BEFORE_CLEAR));
        if (diag == null) {
            return;
        }
        diag.result.then(
            function() {
                if ($scope.hasResult == false && $scope.isEditable == false) {
                    $scope.resetCondForm();
                }

                $scope.resetResultForm();

                $scope.hasResult = false;
                $scope.isEditable = false;
                $scope.isNewData = false;
                $scope.form.$setPristine();
                $scope.error = Client.clearErrors();
                Message.showMessage(MsgConst.MSG_KEY_COMMON_INFO_MESSAGE_F9);
                $scope.setFocus();
            }, function() {
                $scope.setFocus();
            }
        );
    }

    /**
     * Join all error (warning) message to one message.
     * @return String
     */
    $scope.getErrorMsg = function(errors) {
        if (errors == undefined || !angular.isArray(errors)) {
            return undefined;
        }
        var msg = "";
        for (var i = 0; i < errors.length; i++) {
            if (i == errors.length - 1) {
                msg += errors[i].message;
            } else {
                msg += errors[i].message + "\n";
            }
        }
        return msg;
    }

    /**
     * Handler error response.
     */
    var responseHandling = function(response) {
        $scope.result.skipWarning = false;
        if (response.status === HttpConst.CODE_BAD_REQUEST) {
            // エラー処理
            Message.showMessageWithContent(response.data.errors[0].level, response.data.errors[0].message);
            $scope.error = Client.showErrorFromServer($scope.error, response.data.errors);
            $scope.errorsTable = response.data.gridErrors;
            $scope.jumpPage();
        } else {
            Message.showMessage(MsgConst.MSG_KEY_SYSTEM_ERROR);
        }
        $scope.setFocus();
    }

    /**
     * Check controls of the scope to Enable/Disable Delete button.
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

    var isvalid = false;
    $scope.$on('valid', function(evt, agr) {
        isvalid = agr;
    });

    /**
     * Check controls of the result area to Enable/Disable Save button.
     *
     * @return true:enable false:disable
     */
    $scope.canSave = function() {
        if ($scope.isEditable && isvalid) {
            if (!angular.equals($scope.result.triArea, $scope.resultOld)) {
                return true;
            }
            if ($scope.hasResult && $scope.condOld != undefined) {
                if (!angular.equals($scope.cond.shrDate, $scope.condOld.shrDate) || !angular.equals($scope.cond.simeDate, $scope.condOld
                    .simeDate) || !angular.equals($scope.cond.kakToriKmk, $scope.condOld.kakToriKmk) || !angular.equals($scope.cond
                    .tekiyo, $scope.condOld.tekiyo)) {
                    return true;
                }
                if ($scope.isNewData && !angular.equals($scope.cond.shrSyoriNo, $scope.condOld.shrSyoriNo)) {
                    return true;
                }
            }
            return false;
        } else {
            return false;
        }
    }

    /**
     * Check controls of the condForm area to Enable/Disable SearchEdit button.
     *
     * @return true:enable false:disable
     */
    $scope.canSearchGrid = function() {
        if ($scope.isEditable && $scope.resultFormSearch.$valid && (!isEmpty($scope.mainToriCdIn) || !isEmpty($scope.rowNoIn))) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Check data of the grid area to Enable/Disable grid controls.
     *
     * @return true:enable false:disable
     */
    $scope.canEditGrid = function() {
        if ($scope.isEditable) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Check data of the grid area to Enable/Disable grid controls.
     *
     * @return true:enable false:disable
     */
    $scope.canSaveGrid = function() {
        if ($scope.isEditable && $scope.resultForm.$valid) {
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
        if ($scope.isEditable || $scope.form.condForm.$invalid) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * Auto trace to calculate grid data.
     */
    $scope.$watchCollection('[result.shrKin, result.sotoTaxKin]', function() {
        if ($scope.result != undefined) {
            if (isEmpty($scope.result.shrKin) && isEmpty($scope.result.sotoTaxKin)) {
                $scope.result.shrKinAll = "";
            } else {
                var shrKin = (isEmpty($scope.result.shrKin)) ? "0" : $scope.result.shrKin;
                var sotoTaxKin = (isEmpty($scope.result.sotoTaxKin)) ? "0" : $scope.result.sotoTaxKin;

                $scope.result.shrKinAll = (parseInt(shrKin) + parseInt(sotoTaxKin)).toString();
            }
        }
    });

    /**
     * Filter change data in grid (insert or update).
     *
     * @return grid data
     */
    $scope.getChangeData = function(triArea) {
        if (triArea == undefined || triArea.length == 0) {
            return [];
        }
        var filteredResult = triArea.filter(function(element) {
            if (element.actKbn == 1 || element.actKbn == 2) {
                return element;
            }
        });
        if (filteredResult.length > 0) {
            return angular.copy(filteredResult);
        } else {
            return [];
        }
    }

    /**
     * Calculate tax if user not input tax.
     */
    $scope.validTax = function(rowData) {
        if (!isEmpty($scope.cond.simeDate) && !isEmpty($scope.cond.kakToriKmk) && isEmpty($scope.result.sotoTaxKin)) {
            var data = {
                kaisyaCd: $scope.cond.kaisyaCd,
                shrSyoriNo: $scope.cond.shrSyoriNo,
                kakToriKmk: $scope.cond.kakToriKmk,
                toriNm: $scope.cond.toriNm,
                shrDate: $scope.cond.shrDate,
                simeDate: $scope.cond.simeDate,
                tekiyo: $scope.cond.tekiyo
            };
            data.triArea = [];
            data.triArea.push(angular.copy(rowData));
            /* end copy */

            var resData = KKJP0010ResourceExt.valid(data, function(response) {
                if (resData != undefined && resData.triArea != undefined && resData.triArea.length > 0) {
                    var row = resData.triArea[0];
                    row.sotoTaxKin = toString(row.sotoTaxKin);
                    row.shrKin = toString(row.shrKin);

                    var filteredResult = $scope.result.triArea.filter(function(element) {
                        if (element.mainToriCd == row.mainToriCd) {
                            return element;
                        }
                    });
                    if (filteredResult.length > 0) {
                        var elem = filteredResult[0];
                        elem.sotoTaxKin = row.sotoTaxKin;
                        $scope.result.sotoTaxKin = row.sotoTaxKin;
                        $scope.result.shrKinAll = row.shrKinAll;
                        if (row.shrKin != elem.shrKin) {
                            $scope.result.shrKin = row.shrKin;
                            elem.shrKin = row.shrKin;
                        }

                        if (elem.actKbn == 2 && $scope.resultOld != undefined && $scope.resultOld.length > 0) {
                            var oldElem = undefined;
                            for (var i = 0; i < $scope.resultOld.length; i++) {
                                if ($scope.resultOld[i].mainToriCd == $scope.result.mainToriCd) {
                                    oldElem = $scope.resultOld[i];
                                    break;
                                }
                            }
                            if (oldElem != undefined) {
                                if (elem.jotaiKbn == oldElem.jotaiKbn && elem.shrKin == oldElem.shrKin && elem.sotoTaxKin == oldElem.sotoTaxKin) {
                                    elem.actKbn = 0;
                                    elem.status = "";
                                }
                            }
                        }
                    }
                }
            }, function(response) {});
        }
    }

    /**
     * Check condtion area for insert/update grid data.
     *
     * @return true:enable false:disable
     */
    $scope.checkCondForm = function() {
        // check cond form
        var checkFlg = true;
        var messageList = [];
        var removeMessageList = [];
        if (isEmpty($scope.cond.simeDate)) {
            var messageInfo = {
                    name: 'simeDate',
                    messageId: MsgConst.MSG_KEY_INPUT_REQUIRED_ENTER
            };
            messageList.push(messageInfo);
        } else {
            var removeMessageInfo = {
                    name: 'simeDate',
                    messageId: MsgConst.MSG_KEY_INPUT_REQUIRED_ENTER
            };
            removeMessageList.push(removeMessageInfo);
        }
        
        if (isEmpty($scope.cond.kakToriKmk)) {
            var messageInfo = {
                    name: 'kakToriKmk',
                    messageId: MsgConst.MSG_KEY_INPUT_REQUIRED_ENTER
            };
            messageList.push(messageInfo);
        } else {
            var removeMessageInfo = {
                    name: 'kakToriKmk',
                    messageId: MsgConst.MSG_KEY_INPUT_REQUIRED_ENTER
            };
            removeMessageList.push(removeMessageInfo);
        }
        
        if (messageList.length != 0) {
            Message.showMessage(MsgConst.MSG_KEY_VALIDATION_ERROR);
            $scope.error = Client.showErrorArrayClientWithFlag($scope.error, messageList, false);
            checkFlg = false;
        }
        
        if (removeMessageList != 0) {
            $scope.error = Client.removeErrorArrayClient($scope.error, removeMessageList);
        }
        return checkFlg;
    }

    /**
     * Set element to edit area.
     */
    $scope.setEditElement = function(element) {
        if (element != undefined) {
            $scope.result.mainToriCd = element.mainToriCd;
            var stopToken1 = $interval(function() {
                if ($rootScope.pendingRequests == 0) {
                    $timeout(function() {
                        $scope.result.mainToriNm = element.toriNm;
                        $rootScope.pendingRequests = -1;
                    }, 100);
                    $interval.cancel(stopToken1);
                }
            }, 150, 0, false);
            $scope.result.jotaiKbn = element.jotaiKbn;
            $scope.result.shrKin = element.shrKin;
            $scope.result.sotoTaxKin = element.sotoTaxKin;
            $scope.result.shrKinAll = element.shrKinAll;
        }
    }

    /**
     * control reset error event
     */
    $scope.$on('ccResetServerClientError', function(event, controlName) {
        $scope.error = Client.removeAllErrorByName($scope.error, controlName);
    });
});