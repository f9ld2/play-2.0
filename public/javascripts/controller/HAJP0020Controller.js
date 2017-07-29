///////////////////////////////////////////////////////////////////////
//Copyright(C) 2015 NEC Solution Innovators, Ltd. All Rights Reserved.
///////////////////////////////////////////////////////////////////////
/*
 * ====================================================================
 * 機能名称 : 本部発注入力（店舗）
 * 改版履歴 Rev. 改版年月日 改版者名 内容
 * 1.0 2014.04.16 TuanTQ 新規作成
 * ===================================================================
 */
var app = angular.module('hajp0020', ['hajp0020Services', 'ui', 'ui.select2', 'directives']);
app.controller("HAJP0020Ctrl", function($scope, Message, $interval, MsgConst, HttpConst, Hajp0020, $http, $filter,
    Dialog, DialogInfo, AppConst, FocusConst, Client, UserInfo, Hajp0020Info, Hajp0020CheckInfo) {
    Message.showMessage(MsgConst.MSG_KEY_COMMON_INFO_MESSAGE);
    $scope.cond = {
        kaisyaCd: AppConst.KAISYA_CODE
    };
    $scope.result = {};
    $scope.queryResult = [];
    $scope.cloneQueryResult = [];
    $scope.currentRow = -1;
    $scope.mode = 1;
    $scope.initMode = 1;
    $scope.searchMode = 2;
    $scope.editMode = 3;
    $scope.postEditMode = 4;
    $scope.disCon = false;
    $scope.errorsTable = [];
    $scope.totalServerItems = 0;
    $scope.isInsert = false;
    $scope.rowError = null;
    $scope.responseData = null;
    $scope.focusCond = FocusConst.TEXTINPUT_FOCUS;
    $scope.focusResult = FocusConst.NOT_FOCUS;
    $scope.preShnCd = '';
    $scope.preBin = '';
    /**
     * Get focus of condition form.
     */
    $scope.getFocusCond = function() {
        return $scope.focusCond;
    }

    /**
     * Get focus of result form.
     */
    $scope.getFocusResult = function() {
        return $scope.focusResult;
    }

    /**
     * Show AlertDialog.
     */
    $scope.showAlert = function(message) {
        var diag = Dialog.alert(message);
        if (diag == null) {
            return;
        }
        diag.result.then(function() {
            $scope.setFocus();
        }, function() {
            $scope.setFocus();
        });
    }

    /**
     * Switch focus between condition and result form.
     */
    $scope.setFocus = function() {
        if ($scope.disCon) {
            setFocusResult($scope, $interval, FocusConst.TEXTINPUT_FOCUS);
        } else {
            setFocusCond($scope, $interval, FocusConst.TEXTINPUT_FOCUS);
        }
    }

    /**
     * Change read-only status of controls.
     *
     * @return true:read-only false:not read-only
     */
    $scope.disableCond = function() {
        return $scope.disCon;
    }

    /**
     * Change read-only status of controls.
     *
     * @return true:read-only false:not read-only
     */
    $scope.disableResult = function() {
        return !$scope.disCon;
    }

    /**
     * Change read-only status of controls.
     *
     * @return true:read-only false:not read-only
     */
    $scope.disableByHatSruiKbn = function() {
        if (angular.equals($scope.cond.hatSruiKbn, '01')) {
            return true;
        }
        return false;
    }

    /**
     * Define paging options.
     */
    $scope.pagingOptions = {
        pageSizes: [20],
        pageSize: 20,
        currentPage: 1
    };

    /**
     * Set paging data.
     */
    $scope.setPagingData = function() {
        if (isEmptyObject($scope.queryResult)) {
            $scope.pagingResult = $scope.queryResult;
        } else {
            var page = $scope.pagingOptions.currentPage;
            var pageSize = $scope.pagingOptions.pageSize;
            $scope.pagingResult = $scope.queryResult.slice((page - 1) * pageSize, page * pageSize);
        }
        if (!$scope.$$phase) {
            $scope.$apply();
        }
    };

    /**
     * Get the row error type.
     *
     * @return row error type
     */
    $scope.errorColoring = function(rowIndex, colIndex) {
        if ($scope.rowError != null) {
            if (rowIndex == $scope.rowError) {
                switch (colIndex) {
                    case 0:
                        return 'errorRowLeft';
                    case 14:
                        return 'errorRowRight';
                    default:
                        return 'errorRowCenter';
                }
            }
        }
    }

    var responseHandling = function(response) {
        if (response.status === HttpConst.CODE_NOT_FOUND) {
            Message.showMessage(MsgConst.MSG_KEY_DATA_NOT_EXIST);
        } else if (response.status === HttpConst.CODE_BAD_REQUEST) {
            // エラー処理
            for (var i = 0; i < response.data.errors.length; i++) {
                if (response.data.errors[i].message === "indexRowError") {
                    $scope.rowError = response.data.errors[i].level;
                    $scope.pagingOptions.currentPage = Math.ceil((parseFloat($scope.rowError) + 1) / 20);
                    if ($scope.currentRow !== $scope.rowError) {
                        $scope.setupDataForError(angular.copy($scope.queryResult[$scope.rowError]));
                    }
                    break;
                }
            }
            Message.showMessageWithContent(response.data.errors[0].level, response.data.errors[0].message);
            $scope.error = Client.showErrorFromServer($scope.error, response.data.errors);
            $scope.errorsTable = response.data.gridErrors;
        } else {
            Message.showMessage(MsgConst.MSG_KEY_SYSTEM_ERROR);
        }
        $scope.setFocus();
    }

    $scope.resetError = function() {
        $scope.errorsTable = [];
        $scope.error = Client.clearErrors();
        $scope.rowError = null;
    }

    var getMessageFromArray = function(arr, length) {
        if (arr == undefined) {
            return '';
        }

        var message = '';
        for (var i = 0; i < length; i++) {
            if (i > 0) {
                message += '\n';
            }
            message += arr[i].message;
        }
        return message;
    }

    $scope.checkBoxCell = function(index) {
        if ($scope.errorsTable != undefined && index < $scope.errorsTable.length) {
            $scope.errorsTable[i] == {};
        }
        $scope.rowError = null;
    }

    $scope.checkBoxAllClickHandler = checkBoxAllClickHandler;
    $scope.getCheckAllValue = getCheckAllValue;

    /**
     * Define cell template.
     *
     */
    var headerCellTemplate =
        '<div>{{col.displayName}}</div><input name="headerCheckBoxInGrid" class="checkbox" type="checkbox" ng-checked="getCheckAllValue(queryResult, pagingOptions, \'chkFlg\')" ng-click="checkBoxAllClickHandler(queryResult, pagingOptions, \'chkFlg\')" ng-disabled="disableResult()||pagingResult.length == 0"/>';
    var statusCellTemplate = '<div> <span class="{{statusDefine(row.rowIndex + (pagingOptions.currentPage - 1)*20}}"></span></div>';
    var checkboxCellTemplate = '<div  ng-class="{errorCellCheckbox: (errorsTable.length > 0 && errorsTable[row.rowIndex + (pagingOptions.currentPage - 1)*20].chkFlg == true)}">' + '<input  name="cellCheckBoxInGrid"  id="chkFlg_{{row.rowIndex}}"  class="checkbox" type="checkbox" ng-click="checkBoxCell(row.rowIndex)" ng-disabled="disableResult()" ' + ' ng-model="queryResult[row.rowIndex + (pagingOptions.currentPage - 1)*20].chkFlg"  style="cursor: default;"  cc-error="error"/> </div>';
    var rowTemplate = '<div ng-style="{ \'cursor\': row.cursor }" ng-repeat="col in renderedColumns" ng-class="col.colIndex()" class="ngCell {{col.cellClass}} {{errorColoring((row.rowIndex + ((pagingOptions.pageSize)*(pagingOptions.currentPage - 1))), col.index)}}"><div class="ngVerticalBar" ng-style="{height: rowHeight}" ng-class="{ ngVerticalBarVisible: !$last }">&nbsp;</div><div ng-cell-custom></div></div>';

    /**
     * Set paging data whenever having new data.
     *
     */
    $scope.$watchCollection('[pagingOptions.currentPage, queryResult]', function() {
        $scope.setPagingData();
    });

    /**
     * Initialize the grid options.
     */
    $scope.gridOptionsHAJP0020HachuArea = {
        data: 'pagingResult',
        enablePaging: true,
        showFooter: true,
        footerRowHeight: 37,
        headerRowHeight: 40,
        rowHeight: 25,
        enableRowSelection: false,
        enableSorting: false,
        enableColumnResize: true,
        enableCellSelection: true,
        rowTemplate: rowTemplate,
        pagingOptions: $scope.pagingOptions,
        totalServerItems: 'totalServerItems',
        footerTemplate: '/assets/templates/footerTemplate.html',
        columnDefs: [{
            field: 'rowNo',
            displayName: '行',
            width: 20,
            headerClass: "header_cell_label2",
            cellClass: 'hajp0010-text-padding',
            cellTemplate: '<div>{{row.rowIndex + 1}}</div>'
        }, {
            field: 'chkFlg',
            displayName: '削',
            width: 25,
            headerCellTemplate: headerCellTemplate,
            cellTemplate: checkboxCellTemplate
        }, {
            field: 'shnCd',
            displayName: '商品[GTIN]',
            width: 110,
            cellClass: "text-center",
            cellTemplate: '<div class="ngCellText" ng-class="{errorCell: (errorsTable.length > 0 && errorsTable[row.rowIndex + (pagingOptions.currentPage - 1)*20].shnCd == true)}"><span ng-cell-text>{{row.getProperty(col.field)}}</span></div>'
        }, {
            field: 'mkrNmR',
            displayName: 'メーカー名称',
            width: 66,
            cellTemplate: '<div class="ngCellText" ng-class="{errorCell: (errorsTable.length > 0 && errorsTable[row.rowIndex + (pagingOptions.currentPage - 1)*20].mkrNmR == true)}"><span ng-cell-text>{{row.getProperty(col.field)}}</span></div>'
        }, {
            field: 'shnNm',
            displayName: '商品名称',
            width: 62,
            cellTemplate: '<div class="ngCellText" ng-class="{errorCell: (errorsTable.length > 0 && errorsTable[row.rowIndex + (pagingOptions.currentPage - 1)*20].shnNm == true)}"><span ng-cell-text>{{row.getProperty(col.field)}}</span></div>'
        }, {
            field: 'kikakuNmA',
            displayName: '規格名称',
            width: 57,
            cellTemplate: '<div class="ngCellText" ng-class="{errorCell: (errorsTable.length > 0 && errorsTable[row.rowIndex + (pagingOptions.currentPage - 1)*20].kikakuNmA == true)}"><span ng-cell-text>{{row.getProperty(col.field)}}</span></div>'
        }, {
            field: 'bin',
            displayName: '便NO',
            width: 57,
            cellTemplate: '<div class="ngCellText" ng-class="{errorCell: (errorsTable.length > 0 && errorsTable[row.rowIndex + (pagingOptions.currentPage - 1)*20].bin == true)}"><span ng-cell-text>{{isEmpty(row.getProperty(col.field)) ? \'&nbsp;\' : row.getProperty(col.field)}}</span></div>'
        }, {
            field: 'hatSu',
            displayName: '発注数',
            width: 60,
            cellClass: "text-right",
            cellTemplate: '<div class="ngCellText" ng-class="{errorCell: (errorsTable.length > 0 && errorsTable[row.rowIndex + (pagingOptions.currentPage - 1)*20].hatSu == true)}"><span ng-cell-text>{{isEmpty(row.getProperty(col.field)) ? \'&nbsp;\' : (row.getProperty(col.field) | number:0)}}</span></div>'
        }, {
            field: 'hatDd',
            displayName: '発注日',
            width: 93,
            cellTemplate: '<div class="ngCellText" ng-class="{errorCell: (errorsTable.length > 0 && errorsTable[row.rowIndex + (pagingOptions.currentPage - 1)*20].hatSu == true)}"><span ng-cell-text>{{isEmpty(row.getProperty(col.field)) ? \'&nbsp;\' : (row.getProperty(col.field) | ccStrDateFilter)}}</span></div>',
            headerCellTemplate: '/assets/templates/headerCellTemplate.html'
        }, {
            field: 'triCd',
            displayName: '取引先コード',
            width: 85,
            cellTemplate: '<div class="ngCellText" ng-class="{errorCell: (errorsTable.length > 0 && errorsTable[row.rowIndex + (pagingOptions.currentPage - 1)*20].triCd == true)}"><span ng-cell-text>{{row.getProperty(col.field) | ccTriCdFilter}}</span></div>'
        }, {
            field: 'hattyuIrisu',
            displayName: '入数',
            width: 35,
            cellClass: "text-right",
            cellTemplate: '<div class="ngCellText" ng-class="{errorCell: (errorsTable.length > 0 && errorsTable[row.rowIndex + (pagingOptions.currentPage - 1)*20].hattyuIrisu == true)}"><span ng-cell-text>{{row.getProperty(col.field) | number:0}}</span></div>'
        }, {
            field: 'genk',
            displayName: '原単価',
            width: 93,
            cellClass: "text-right",
            cellTemplate: '<div class="ngCellText" ng-class="{errorCell: (errorsTable.length > 0 && errorsTable[row.rowIndex + (pagingOptions.currentPage - 1)*20].genk == true)}"><span ng-cell-text>{{isEmpty(row.getProperty(col.field)) ? \'&nbsp;\' : (row.getProperty(col.field) | number:2)}}</span></div>'
        }, {
            field: 'baik',
            displayName: '売単価',
            width: 74,
            cellClass: "text-right",
            cellTemplate: '<div class="ngCellText" ng-class="{errorCell: (errorsTable.length > 0 && errorsTable[row.rowIndex + (pagingOptions.currentPage - 1)*20].baik == true)}"><span ng-cell-text>{{isEmpty(row.getProperty(col.field)) ? \'&nbsp;\' : (row.getProperty(col.field) | number:0)}}</span></div>'
        }, {
            field: 'teiseiKbn',
            displayName: '店訂正',
            width: 58,
            cellTemplate: '<div class="ngCellText" ng-class="{errorCell: (errorsTable.length > 0 && errorsTable[row.rowIndex + (pagingOptions.currentPage - 1)*20].baik == true)}"><span ng-cell-text>{{isEmpty(row.getProperty(col.field)) ? \'&nbsp;\' : (row.getProperty(col.field) | number:0)}}</span></div>',
            headerCellTemplate: '/assets/templates/headerCellTemplate.html'
        }, {
            field: 'status',
            displayName: '更新',
            width: 35,
            cellClass: "text-center",
            cellTemplate: '<div> <span class="{{getIcon(row.entity.shnCd, row.entity.bin)}}"></span></div>'
        }]
    };


    /**
     * function to check whether a record is modified or not
     *
     * @param record the record to check
     * @return boolean, true: the record is modified, false: the record is not modified
     */
    var isModified = function(record) {
        if (isEmpty(record)) {
            return -1;
        }

        var i = 0;
        var cloneRecord = undefined;

        for (i = 0; i < $scope.cloneQueryResult.length; i++) {
            if (record.shnCd == $scope.cloneQueryResult[i].shnCd && record.bin == $scope.cloneQueryResult[i].bin) {
                cloneRecord = $scope.cloneQueryResult[i];
                break;
            }
        }

        // new record
        if (cloneRecord == undefined) {
            return 2;
        }

        // [2015/06/16 WebMD_SS_V000.001対応 UPD START]
        if (record.chkFlg == $scope.cloneQueryResult[i].chkFlg && record.shnCd == $scope.cloneQueryResult[i].shnCd && record.bin == $scope.cloneQueryResult[i].bin && record.hatSu == $scope.cloneQueryResult[i].hatSu && record.triCd == $scope.cloneQueryResult[i].triCd && record.hattyuIrisu == $scope.cloneQueryResult[i].hattyuIrisu && record.genk == $scope.cloneQueryResult[i].genk && record.baik == $scope.cloneQueryResult[i].baik && record.biko == $scope.cloneQueryResult[i].biko) {
        // [2015/06/16 WebMD_SS_V000.001対応 UPD END]
            return -1;
        } else {
            return 1;
        }
    }

    /**
     * function to get icon css from shnCd
     *
     * @param shnCd: a shnCd of a row in grid
     * @return
     */
    $scope.getIcon = function(shnCd, binNo) {
        var index = $scope.searchRecordWithInput(shnCd, binNo, $scope.cloneQueryResult);
        var index2 = $scope.searchRecordWithInput(shnCd, binNo, $scope.queryResult);
        if (index == -1) {
            return 'gridStatusInsert';
        } else {
            if (isModified($scope.queryResult[index2]) == 1) {
                return 'gridStatusUpdate';
            }
        }

        return '';
    }

    /**
     * Reset filter form.
     */
    $scope.resetFilterForm = function() {
        if ($scope.result != undefined) {
            $scope.result.shnCd = undefined;
            $scope.result.shnNm = "";
            $scope.result.kikakuNmA = "";
            $scope.result.mkrCd = "";
            $scope.result.mkrNmR = "";
            $scope.result.mstIriSu = null;
            $scope.result.bin = "";
            $scope.result.hatSu = null;
            $scope.result.triCd = "";
            $scope.result.triNm = "";
            $scope.result.hattyuIrisu = null;
            $scope.result.genk = null;
            $scope.result.baik = null;
            $scope.result.hatDd = "";
            $scope.result.teiseiKbn = "";
            $scope.currentRow = -1;
            //[2015/06/16 WebMD_SS_V000.001対応 INS START]
            $scope.result.remarks = "";
            //[2015/06/16 WebMD_SS_V000.001対応 INS END]
        }
    };

    /**
     * Reset condition form.
     *
     */
    $scope.resetCondForm = function() {
        if ($scope.cond != undefined) {
            $scope.cond.hatSruiKbn = "";
            $scope.cond.nhnDd = "";
            $scope.cond.kaisyaCd = AppConst.KAISYA_CODE;
            $scope.cond.jigyobuCd = "";
            $scope.cond.tenCd = "";
            $scope.cond.bmnCd = "";
        }
    };

    /**
     * Reset result form.
     *
     */
    $scope.resetResultForm = function() {
        if ($scope.result != undefined) {
            $scope.result.rowNo = "";
            $scope.result.shnCd2 = "";
            $scope.result.shnCd = undefined;
            $scope.result.shnNm = "";
            $scope.result.kikakuNmA = "";
            $scope.result.mkrCd = "";
            $scope.result.mkrNmR = "";
            $scope.result.mstIriSu = null;
            $scope.result.bin = "";
            $scope.result.hatSu = null;
            $scope.result.triCd = "";
            $scope.result.triNm = "";
            $scope.result.hattyuIrisu = null;
            $scope.result.genk = null;
            $scope.result.baik = null;
            $scope.result.hatDd = "";
            $scope.result.teiseiKbn = "";
            $scope.currentRow = -1;
          //[2015/06/16 WebMD_SS_V000.001対応 INS START]
            $scope.result.remarks = "";
            //[2015/06/16 WebMD_SS_V000.001対応 INS END]
            $scope.form.$setPristine();
        }
    }

    $scope.$on("shnCdBlur", function(event) {
        $scope.handleJanCdBlur();
    });

    $scope.$on("binBlur", function(event) {
        $scope.handleJanCdBlur();
    });

    $scope.searchRecordWithJanCd = function() {
        for (var i = 0; i < $scope.queryResult.length; i++) {
            if ($scope.queryResult[i].shnCd == $scope.result.shnCd) {
                return i;
            }
        }
        return -1;
    }


    /**
     * Get data from HAJP0020 table based on hatDd.
     *
     */
    $scope.handleJanCdBlur = function() {
        if (isEmpty($scope.result.shnCd) || isEmpty($scope.result.bin)) {
            $scope.preShnCd = $scope.result.shnCd;
            $scope.preBin = $scope.result.bin;
            return;
        }
        if (!angular.equals($scope.result.shnCd, $scope.preShnCd)
            || !angular.equals($scope.result.bin, $scope.preBin)) {
            $scope.error = Client.clearErrors();
            var bmnCd = isEmpty($scope.cond.bmnCd) ? 'DUMY_STRING' : $scope.cond.bmnCd;
            var param = {'kaisyaCd': $scope.cond.kaisyaCd,
                        'jigyobuCd': $scope.cond.jigyobuCd,
                        'tenCd': $scope.cond.tenCd,
                        'nhnDd': $scope.cond.nhnDd,
                        'hatSruiKbn': $scope.cond.hatSruiKbn,
                        'janCd': $scope.result.shnCd,
                        'bin': $scope.result.bin,
                        'bmnCd': bmnCd};
            var showResult = Hajp0020Info.query(param, function() {
                $scope.result.mkrNmR = showResult.mkrNmR;
                $scope.result.shnNm = showResult.shnNm;
                $scope.result.kikakuNmA = showResult.kikakuNmA;
                $scope.result.hatDd = showResult.hatDd;
                $scope.result.triCd = showResult.triCd;
                $scope.result.hattyuIrisu = showResult.hattyuIrisu;
                $scope.result.genk = showResult.genk;
                $scope.result.baik = showResult.baik;
                $scope.result.kikakuCd = showResult.kikakuCd;
                $scope.result.nendo = showResult.nendo;
                $scope.result.bmnCd = showResult.bmnCd;
                $scope.preShnCd = $scope.result.shnCd;
                $scope.preBin = $scope.result.bin;
                //[2015/06/16 WebMD_SS_V000.001対応 INS START]
                $scope.result.remarks = showResult.biko;
                //[2015/06/16 WebMD_SS_V000.001対応 INS END]
                Message.showMessage(MsgConst.MSG_KEY_INFO_BUTTON_SAVE);
            }, function(response) {
                if (response.status === HttpConst.CODE_NOT_FOUND) {
                    Message.showMessage(MsgConst.MSG_KEY_DATA_NOT_EXIST);
                } else if (response.status === HttpConst.CODE_BAD_REQUEST) {
                    Message.showMessageWithContent(response.data.errors[0].level, response.data.errors[0].message);
                    $scope.error = Client.showErrorFromServer($scope.error, response.data.errors);
                } else {
                    Message.showMessage(MsgConst.MSG_KEY_SYSTEM_ERROR);
                }
                $scope.result.mkrNmR = '';
                $scope.result.shnNm = '';
                $scope.result.kikakuNmA = '';
                $scope.result.hatSu = '';
                $scope.result.hatDd = '';
                $scope.result.triCd = '';
                $scope.result.hattyuIrisu = '';
                $scope.result.genk = '';
                $scope.result.baik = '';
                $scope.result.teiseiKbn = '';
                $scope.result.kikakuCd = '';
                $scope.result.nendo = '';
                $scope.result.bmnCd = '';
                //[2015/06/16 WebMD_SS_V000.001対応 INS START]
                $scope.result.remarks = '';
                //[2015/06/16 WebMD_SS_V000.001対応 INS END]

                $scope.preShnCd = $scope.result.shnCd;
                $scope.preBin = $scope.result.bin;
            });
        }
    };

    /**
     * Setup data of filter area.
     *
     */
    $scope.setupData = function() {
        var flag1 = false;
        var record1 = null;
        if (!isEmpty($scope.result.rowNo) && $scope.form.noForm.$valid) {
            if ($scope.result.rowNo - 1 <= $scope.queryResult.length) {
                record1 = $scope.queryResult[$scope.result.rowNo - 1];
            }
            flag1 = true;
        }

        var filteredResult = [];
        var flag2 = false;
        if (!isEmpty($scope.result.shnCd2) && $scope.form.shnCdForm.$valid) {
            filteredResult = $scope.queryResult.filter(function(element) {
                if (element.shnCd == $scope.result.shnCd2) {
                    return element;
                }
            });
            flag2 = true;
        }

        var record = null
        if (flag1 && flag2) {
            if (record1 != null && filteredResult.length != 0) {
                if (angular.equals(record1, filteredResult[0])) {
                    record = record1;
                }
            }
        } else if (flag1 && !flag2) {
            if (record1 != null) {
                record = record1;
            }
        } else if (!flag1 && flag2) {
            if (filteredResult.length != 0) {
                record = filteredResult[0];
            }
        }

        // if there is a record matching filter
        if (record != null) {
            $scope.result.shnCd = isEmpty(record.shnCd) ? "" : record.shnCd.trim();
            $scope.result.shnNm = isEmpty(record.shnNm) ? "＊＊＊＊＊＊＊＊＊＊＊＊＊＊" : record.shnNm;
            $scope.result.kikakuNmA = isEmpty(record.kikakuNmA) ? "**********" : record.kikakuNmA;
            $scope.result.mkrNmR = isEmpty(record.mkrNmR) ? "＊＊＊＊＊" : record.mkrNmR;
            $scope.result.mstIriSu = isEmpty(record.mstJinSu) ? null : record.mstJinSu;
            $scope.result.hatSu = isEmpty(record.hatSu) ? 0 : record.hatSu;
            $scope.result.triCd = isEmpty(record.triCd) ? "" : record.triCd;
            $scope.result.triNm = isEmpty(record.triNmR) ? "＊＊＊＊＊" : record.triNmR;
            $scope.result.hattyuIrisu = isEmpty(record.hattyuIrisu) ? 0 : record.hattyuIrisu;
            $scope.result.genk = isEmpty(record.genk) ? 0 : record.genk;
            $scope.result.baik = isEmpty(record.baik) ? 0 : record.baik;
            $scope.result.hatDd = isEmpty(record.hatDd) ? "" : record.hatDd;
            $scope.result.teiseiKbn = isEmpty(record.teiseiKbn) ? "" : record.teiseiKbn;
            $scope.result.bin = isEmpty(record.bin) ? "" : record.bin.toString();
            //[2015/06/16 WebMD_SS_V000.001対応 INS START]
            $scope.result.remarks = isEmpty(record.biko) ? "" : record.biko;
            //[2015/06/16 WebMD_SS_V000.001対応 INS END]
        } else {
            $scope.resetFilterForm();
        }
    };

    $scope.setupDataForError = function(data) {
        // if there is a record matching filter
        if (data !== undefined) {
            $scope.result.shnCd = data.shnCd.trim();
            $scope.result.hatSu = isEmpty(data.hatSu) ? "" : data.hatSu;
            $scope.result.triCd = isEmpty(data.triCd) ? "" : data.triCd;
            $scope.result.triNm = isEmpty(data.triNmR) ? "" : data.triNmR;
            $scope.result.hattyuIrisu = isEmpty(data.hattyuIrisu) ? 0 : data.hattyuIrisu;
            $scope.result.genk = isEmpty(data.genk) ? 0 : data.genk;
            $scope.result.baik = isEmpty(data.baik) ? 0 : data.baik;
            $scope.result.hatDd = isEmpty(data.hatDd) ? 0 : data.hatDd;
            $scope.result.teiseiKbn = isEmpty(data.teiseiKbn) ? 0 : data.teiseiKbn;
            $scope.result.bin = isEmpty(data.bin) ? "" : data.bin.toString();
            //[2015/06/16 WebMD_SS_V000.001対応 INS START]
            $scope.result.remarks = isEmpty(record.biko) ? "" : record.biko;
            //[2015/06/16 WebMD_SS_V000.001対応 INS END]
        } else {
            $scope.resetFilterForm();
        }
    };

    /**
     * Save data at filter area.
     *
     */
    $scope.gridSave = function() {
        var param = {   'tenCd': $scope.cond.kaisyaCd + $scope.cond.jigyobuCd + $scope.cond.tenCd,
                        'janCd': $scope.result.shnCd,
                        'bin': $scope.result.bin,
                        'hatDd': $scope.result.hatDd,
                        'nhnDd': $scope.cond.nhnDd,
                        'hatSruiKbn': $scope.cond.hatSruiKbn,
                        //[2015/06/16 WebMD_SS_V000.001対応 UPD START]
                        'triCd': $scope.result.triCd,
                        'remarks': $scope.result.remarks};
                        //[2015/06/16 WebMD_SS_V000.001対応 UPD END]
        var showResult = Hajp0020CheckInfo.query(param, function() {
            $scope.resetError();
            var currentRow = $scope.searchRecord();
            if (isEmpty($scope.result.bin)) {
                $scope.result.bin = '1';
            }
            if (currentRow == -1) {
                $scope.queryResult.unshift({});
                $scope.queryResult[0].shnCd = $scope.result.shnCd;
                $scope.queryResult[0].shnNm = isEmpty($scope.result.shnNm) ? "＊＊＊＊＊" : $scope.result.shnNm;
                $scope.queryResult[0].kikakuNmA = isEmpty($scope.result.kikakuNmA) ? "**********" : $scope.result.kikakuNmA;
                $scope.queryResult[0].mkrNmR = isEmpty($scope.result.mkrNmR) ? "＊＊＊＊＊" : $scope.result.mkrNmR;
                $scope.queryResult[0].bin = isEmpty($scope.result.bin) ? "1" : $scope.result.bin;
                $scope.queryResult[0].hatSu = $scope.result.hatSu;
                $scope.queryResult[0].triCd = isEmpty($scope.result.triCd) ? "" : $scope.result.triCd;
                $scope.queryResult[0].triNmR = isEmpty($scope.result.triNmR) ? "＊＊＊＊＊" : $scope.result.triNmR;
                $scope.queryResult[0].mstJinSu = $scope.result.mstIriSu;
                $scope.queryResult[0].hattyuIrisu = $scope.result.hattyuIrisu;
                $scope.queryResult[0].genk = isEmpty($scope.result.genk) ? 0 : $scope.result.genk;
                $scope.queryResult[0].baik = isEmpty($scope.result.baik) ? 0 : $scope.result.baik;
                $scope.queryResult[0].teiseiKbn = isEmpty($scope.result.teiseiKbn) ? 1 : $scope.result.teiseiKbn;
                $scope.queryResult[0].hatDd = isEmpty($scope.result.hatDd) ? "" : $scope.result.hatDd;
                $scope.queryResult[0].kikakuCd = isEmpty($scope.result.kikakuCd) ? "" : $scope.result.kikakuCd;
                $scope.queryResult[0].nendo = isEmpty($scope.result.nendo) ? "" : $scope.result.nendo;
                $scope.queryResult[0].closeKbn = isEmpty(showResult.closeKbn) ? "" : showResult.closeKbn;
                $scope.queryResult[0].bmnCd = isEmpty($scope.result.bmnCd) ? "" : $scope.result.bmnCd;

                $scope.queryResult[0].chkFlg = false;
                //[2015/06/16 WebMD_SS_V000.001対応 INS START]
                $scope.queryResult[0].biko = isEmpty($scope.result.remarks) ? "" : $scope.result.remarks;
                //[2015/06/16 WebMD_SS_V000.001対応 INS END]
            } else {
                if ($scope.errorsTable != undefined) {
                    // Reset row error
                    $scope.errorsTable[currentRow] = {};
                }

                $scope.queryResult[currentRow].shnCd = $scope.result.shnCd;
                $scope.queryResult[currentRow].shnNm = isEmpty($scope.result.shnNm) ? "＊＊＊＊＊＊＊＊＊＊＊＊＊＊" : $scope.result.shnNm;
                $scope.queryResult[currentRow].kikakuNmA = isEmpty($scope.result.kikakuNmA) ? "**********" : $scope.result.kikakuNmA;
                $scope.queryResult[currentRow].mkrNmR = isEmpty($scope.result.mkrNmR) ? "＊＊＊＊＊" : $scope.result.mkrNmR;
                $scope.queryResult[currentRow].bin = isEmpty($scope.result.bin) ? "1" : $scope.result.bin;
                $scope.queryResult[currentRow].hatSu = $scope.result.hatSu;
                $scope.queryResult[currentRow].triCd = isEmpty($scope.result.triCd) ? "" : $scope.result.triCd;
                $scope.queryResult[currentRow].triNmR = isEmpty($scope.result.triNmR) ? "＊＊＊＊＊" : $scope.result.triNmR;
                $scope.queryResult[currentRow].mstJinSu = $scope.result.mstIriSu;
                $scope.queryResult[currentRow].hattyuIrisu = $scope.result.hattyuIrisu;
                $scope.queryResult[currentRow].genk = isEmpty($scope.result.genk) ? 0 : $scope.result.genk;
                $scope.queryResult[currentRow].baik = isEmpty($scope.result.baik) ? 0 : $scope.result.baik;
                $scope.queryResult[currentRow].teiseiKbn = isEmpty($scope.result.teiseiKbn) ? 0 : $scope.result.teiseiKbn;
                $scope.queryResult[currentRow].hatDd = isEmpty($scope.result.hatDd) ? "" : $scope.result.hatDd;
                $scope.queryResult[currentRow].kikakuCd = isEmpty($scope.result.kikakuCd) ? "" : $scope.result.kikakuCd;
                $scope.queryResult[currentRow].nendo = isEmpty($scope.result.nendo) ? "" : $scope.result.nendo;
                $scope.queryResult[currentRow].closeKbn = isEmpty(showResult.closeKbn) ? "" : showResult.closeKbn;
                $scope.queryResult[currentRow].bmnCd = isEmpty($scope.result.bmnCd) ? "" : $scope.result.bmnCd;
                $scope.queryResult[currentRow].chkFlg = false;
                //[2015/06/16 WebMD_SS_V000.001対応 INS START]
                $scope.queryResult[currentRow].biko = isEmpty($scope.result.remarks) ? "" : $scope.result.remarks;
                //[2015/06/16 WebMD_SS_V000.001対応 INS END]
            }

            $scope.totalServerItems = $scope.queryResult.length;
            $scope.setPagingData();
            Message.showMessage(MsgConst.MSG_KEY_INFO_BUTTON_SAVE);
            $scope.setFocus();
        }, function(response) {
            if (response.status === HttpConst.CODE_NOT_FOUND) {
                Message.showMessage(MsgConst.MSG_KEY_DATA_NOT_EXIST);
            } else if (response.status === HttpConst.CODE_BAD_REQUEST) {
                Message.showMessageWithContent(response.data.errors[0].level, response.data.errors[0].message);
                $scope.error = Client.showErrorFromServer($scope.error, response.data.errors);
            } else {
                Message.showMessage(MsgConst.MSG_KEY_SYSTEM_ERROR);
            }
            $scope.setFocus();
        });
    }

    /**
     * Search data based on filter area.
     */
    $scope.gridSearch = function() {
        console.log('click')
        $scope.setupData();
        $scope.setFocus();
    };

    /**
     * Check controls of the condForm area to Enable/Disable Search button.
     *
     * @return true:enable false:disable
     */
    $scope.canSearch = function() {
        return ($scope.form.condForm.$valid && $scope.mode != $scope.editMode);
    };

    /**
     * Check controls of the condForm area to Enable/Disable SearchEdit button.
     *
     * @return true:enable false:disable
     */
    $scope.canSearchEdit = function() {
        return ($scope.form.condForm.$valid && $scope.mode != $scope.editMode);
    };

    /**
     * Check if have changing data on screen to Enable/Disable Save button.
     *
     * @return true:enable false:disable
     */
    $scope.canSave = function() {
        var modified = false;
        for (var i = 0; i < $scope.queryResult.length; i++) {
            if (isModified($scope.queryResult[i]) != -1) {
                modified = true;
            }
        }

        return ($scope.mode == $scope.editMode && modified);
    }

    /**
     * Check controls of the filter area to Enable/Disable Save button.
     *
     * @return true:enable false:disable
     */
    $scope.canGridSave = function() {
        //[2015/06/16 WebMD_SS_V000.001対応 UPD START]
        return ($scope.form.resultForm != undefined && $scope.form.resultForm.$valid 
                && $scope.form.remarksForm != undefined  && $scope.form.remarksForm.$valid);
        //[2015/06/16 WebMD_SS_V000.001対応 UPD END]
    }

    $scope.searchRecord = function() {
        var binNo = isEmpty($scope.result.bin) ? "1" : $scope.result.bin;
        for (var i = 0; i < $scope.queryResult.length; i++) {
            if ($scope.queryResult[i].bin == binNo && $scope.queryResult[i].shnCd == $scope.result.shnCd) {
                return i;
            }
        }
        return -1;
    }

    $scope.searchRecordWithInput = function(shnCd, binNo, arr) {
        for (var i = 0; i < arr.length; i++) {
            if (arr[i].bin == binNo && arr[i].shnCd == shnCd) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Search data from database.
     */
    $scope.doSearch = function() {
        $scope.resetResultForm();
        $scope.resetError();
        $scope.info = null;
        $scope.pagingOptions.currentPage = 1;

        var showResult = Hajp0020.query($scope.cond, function() {
            $scope.queryResult = showResult;
            if ($scope.queryResult.length > 0) {
                $scope.info = $scope.queryResult[0];
                if (isEmpty($scope.queryResult[0].shnCd)) {
                    $scope.queryResult = [];
                }
            }
            $scope.totalServerItems = $scope.queryResult.length;

            $scope.resetResultForm();
            if ($scope.queryResult.length == 0) {
                $scope.queryResult = {};
                $scope.info = null;
                $scope.mode = $scope.initMode;

                $scope.disCon = false;
                Message.showMessage(MsgConst.MSG_KEY_DATA_NOT_EXIST);
                $scope.setFocus();
            } else {
                $scope.mode = $scope.searchMode;

                $scope.disCon = false;
                Message.showMessage(MsgConst.MSG_KEY_DISPLAY_CORRECT_DATA);
                $scope.setFocus();
            }
        }, function(response) {
            if (response.status === HttpConst.CODE_NOT_FOUND) {
                Message.showMessage(MsgConst.MSG_KEY_DATA_NOT_EXIST);
            } else if (response.status === HttpConst.CODE_BAD_REQUEST) {
                Message.showMessageWithContent(response.data.errors[0].level, response.data.errors[0].message);
                $scope.error = Client.showErrorFromServer($scope.error, response.data.errors);
            } else {
                Message.showMessage(MsgConst.MSG_KEY_SYSTEM_ERROR);
            }
            $scope.setFocus();
        });
    }

    $scope.showMessageAndDialog = function() {
        if (!isEmpty($scope.info.infoRes.errors) && $scope.info.infoRes.errors.length > 0) {
            var length = $scope.info.infoRes.errors.length;
            if (length >= 2) {
                $scope.showAlert(getMessageFromArray($scope.info.infoRes.errors, length - 1));
            }
            Message.showMessageWithContent($scope.info.infoRes.errors[length - 1].level, $scope.info.infoRes.errors[length - 1].message);
        }
    }

    /**
     * Search/Edit data from database.
     */
    $scope.doSearchEdit = function() {
        var showResult = Hajp0020.query($scope.cond, function() {
            $scope.resetResultForm();
            $scope.info = null;
            $scope.resetError();
            $scope.pagingOptions.currentPage = 1;

            $scope.queryResult = showResult;
            $scope.cloneQueryResult = angular.copy($scope.queryResult);

            if ($scope.queryResult.length > 0) {
                $scope.info = $scope.queryResult[0];
                if (isEmpty($scope.queryResult[0].shnCd)) {
                    $scope.queryResult = [];
                }
            }
            $scope.totalServerItems = $scope.queryResult.length;

            if ($scope.queryResult.length == 0 && $scope.info.readonly === false) {
                var diag = Dialog.confirm(Message.getMessage(MsgConst.MSG_KEY_CONFIRM_NEW_REGISTERED));
                if (diag == null) {
                    return;
                }
                diag.result.then(function() {
                    $scope.showMessageAndDialog();

                    $scope.mode = $scope.editMode;
                    $scope.isInsert = true;
                    $scope.disCon = true;
                    $scope.error = null;
                    $scope.setFocus();
                }, function() {
                    Message.showMessage(MsgConst.MSG_KEY_COMMON_INFO_MESSAGE);
                    $scope.setFocus();
                });
            } else {
                $scope.showMessageAndDialog();

                $scope.resetResultForm();
                $scope.mode = $scope.editMode;
                $scope.isInsert = false;

                $scope.disCon = true;
                $scope.error = null;
                Message.showMessage(MsgConst.MSG_KEY_INFO_BUTTON_SAVE);
                $scope.setFocus();
            }
        }, function(response) {
            if (response.status === HttpConst.CODE_NOT_FOUND) {
                var diag = Dialog.confirm(Message.getMessage(MsgConst.MSG_KEY_CONFIRM_NEW_REGISTERED));
                if (diag == null) {
                    return;
                }
                diag.result.then(function() {
                    $scope.isInsert = true;
                    $scope.mode = $scope.editMode;

                    $scope.disCon = true;
                    Message.showMessage(MsgConst.MSG_KEY_INFO_BUTTON_SAVE);
                    $scope.setFocus();
                }, function() {
                    Message.showMessage(MsgConst.MSG_KEY_COMMON_INFO_MESSAGE);
                    $scope.setFocus();
                });
            } else if (response.status === HttpConst.CODE_BAD_REQUEST) {
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
     * Prepare grid data to update.
     *
     * @return updating data
     */
    $scope.prepareSaveList = function() {
        var modifiedData = {};
        modifiedData.hatSruiKbn = $scope.cond.hatSruiKbn;
        modifiedData.hatDd = $scope.cond.hatDd;
        modifiedData.nhnDd = $scope.cond.nhnDd;
        modifiedData.kaisyaCd = $scope.cond.kaisyaCd;
        modifiedData.jigyobuCd = $scope.cond.jigyobuCd;
        modifiedData.tenCd = $scope.cond.tenCd;
        modifiedData.bmnCd = $scope.cond.bmnCd;
        modifiedData.hachuArea = [];
        for (var i = 0; i < $scope.queryResult.length; i++) {
            if (isModified($scope.queryResult[i]) == 1) {
                $scope.queryResult[i].updateFlag = true;
                $scope.queryResult[i].rowNo = i;
                modifiedData.hachuArea.push($scope.queryResult[i]);

            }
            if (isModified($scope.queryResult[i]) == 2) {
                $scope.queryResult[i].updateFlag = false;
                $scope.queryResult[i].rowNo = i;
                modifiedData.hachuArea.push($scope.queryResult[i]);
            }
        }

        return modifiedData;
    }

    /**
     * Insert data to database.
     */
    $scope.doSave = function() {
        Dialog.confirm(Message.getMessage(($scope.isInsert) ? MsgConst.MSG_KEY_CONFIRM_BEFORE_INSERT : MsgConst.MSG_KEY_CONFIRM_BEFORE_UPDATE)).result.then(function() {
            var control = Hajp0020.update($scope.prepareSaveList(), function(response) {
                //success
                $scope.mode = $scope.postEditMode;

                $scope.disCon = false;
                $scope.resetError();

                Message.showMessage(($scope.isInsert) ? MsgConst.MSG_KEY_INSERT_SUCCESS : MsgConst.MSG_KEY_UPDATE_SUCCESS);
                if (!isEmpty(control.errors)) {
                    Message.showMessageWithContent(control.errors[0].level, control.errors[0].message);
                    Message.showMessageWithContent(control.errors.errors[0].level, control.errors.errors[0].message);
                    $scope.error = Client.showErrorFromServer($scope.error, control.errors.errors);

                    $scope.errorsTable = control.gridErrors;
                }
                $scope.setFocus();
                $scope.form.$setPristine();
            }, responseHandling);
        }, function() {
            $scope.setFocus();
        })
    }

    /**
     * Clear input data of controls.
     */
    $scope.doClear = function() {
        var clearDialog = Dialog.confirm(Message.getMessage(MsgConst.MSG_KEY_CONFIRM_BEFORE_CLEAR));
        if (clearDialog == null) {
            return;
        }
        clearDialog.result.then(function() {
            if ($scope.mode == $scope.initMode) {
                $scope.resetCondForm();
            }

            $scope.resetError();
            $scope.resetResultForm();
            $scope.queryResult = [];
            $scope.form.$setPristine();
            $scope.pagingOptions.currentPage = 1;
            $scope.info = null;
            $scope.isInsert = false;

            Message.showMessage(MsgConst.MSG_KEY_COMMON_INFO_MESSAGE);
            $scope.mode = $scope.initMode;
            $scope.disCon = false;

            $scope.setFocus();
        }, function() {
            $scope.setFocus();
        })
    }

    /**
     * control reset error
     */
    $scope.$on('ccResetServerClientError', function(event, controlName) {
        $scope.error = Client.removeAllErrorByName($scope.error, controlName);
    });
});