///////////////////////////////////////////////////////////////////////
// Copyright(C) 2015 NEC Solution Innovators, Ltd. All Rights Reserved.
///////////////////////////////////////////////////////////////////////

/* ====================================================================
 * 機能名称 : 日別店別部門別売上修正
 * 改版履歴
 * Rev. 改版年月日   改版者名       内容
 * 1.0   2014.04.6   PhucLT      新規作成
 * =================================================================== */

var app = angular.module('urjp0040', ['urjp0040Services', 'ui', 'ui.select2', 'directives', 'ngGrid']);
app.controller("URJP0040Ctrl", function($scope, $interval, FocusConst, Dialog, Message, MsgConst, HttpConst,
    URJP0040, URJP0040DEL, AppConst, Client) {
    Message.showMessage(MsgConst.MSG_KEY_COMMON_INFO_MESSAGE);

    $scope.queryResult = [];
    $scope.grid2 = [];
    $scope.megaQueryResult = {};
    $scope.cloneMegaQueryResult = {};
    $scope.initMode = 1;
    $scope.searchMode = 2;
    $scope.editMode = 3;
    $scope.postEditMode = 4;
    $scope.mode = 1;
    $scope.focusCond = FocusConst.COMBOBOX_INIT_FOCUS;
    $scope.focusResult = FocusConst.NOT_FOCUS;
    $scope.focusResult2 = FocusConst.NOT_FOCUS;
    $scope.disCond = false;
    $scope.isInsert = false;
    $scope.errorNo = -1;

    $scope.cond = {
        kaisyaCd: AppConst.KAISYA_CODE
    };

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
    $scope.getFocusResult = function(index) {
        return (index == 0) ? $scope.focusResult : 0;
    }

    /**
     * focus getter for result area 2
     *
     * @param
     * @return integer 0: not focus, 1, 2: focus
     */
    $scope.getFocusResult2 = function(index) {
        return (index == 3) ? $scope.focusResult2 : 0;
    }

    /**
     * getter for disabling condition area
     *
     * @param
     * @return boolean, false: not disable condition area, true: disable condition area
     */
    $scope.disableCond = function() {
        return $scope.disCond;
    }

    /**
     * getter for disabling result area
     *
     * @param
     * @return boolean, false: not disable result area, true: disable result area
     */
    $scope.disableResult = function() {
        return !$scope.disCond;
    }

    /**
     * function to disable selected cell in grid2
     *
     * @param x: the collumn index, y: the row index
     * @return boolean, true: disable, false: enable
     */
    $scope.disableGrid2 = function(x, y) {
        if (x == 0) {
            var arr = [3, 13, 14];
            if (arr.indexOf(y + 1) != -1) {
                return true;
            } else {
                if (y + 1 == 1 || y + 1 == 9) {
                    return $scope.getKakuteFlag();
                }
            }
        } else if (x == 1) {
            var arr = [1, 2, 3, 12, 13, 14];
            if (arr.indexOf(y + 1) != -1) {
                return true;
            }
        }
        return false;
    }

    /**
     * getter for get kakuteiFlag
     *
     * @param
     * @return kakuteiFlag
     */
    $scope.getKakuteFlag = function() {
        return $scope.megaQueryResult.kakuteiFlag;
    }

    /**
     * function to reset condition form
     *
     * @param
     * @return
     */
    $scope.resetCondForm = function() {
        $scope.condForm.$setPristine();
        if ($scope.cond != undefined) {
            $scope.cond.uriDate = "";
            $scope.cond.jigyobuCd = "";
            $scope.cond.tenCd = "";
        }
    }

    /**
     * function to reset result form
     *
     * @param
     * @return
     */
    $scope.resetResult = function() {
        $scope.queryResult = [];
        $scope.grid2 = [];
        $scope.megaQueryResult = {};
        $scope.cloneMegaQueryResult = {};
        $scope.resetError();
    }

    /**
     * function to reset errors
     *
     * @param
     * @return
     */
    $scope.resetError = function() {
        $scope.errorNo = -1;
        $scope.error = Client.clearErrors();
    }

    /**
     * set focus for condition or result area
     *
     * @param
     * @return
     */
    $scope.setFocus = function() {
        if ($scope.disCond) {
            if (!$scope.getKakuteFlag()) {
                setFocusResult($scope, $interval, FocusConst.TEXTINPUT_FOCUS);
            } else {
                setFocusCustom($scope, $interval, 'focusResult2', FocusConst.TEXTINPUT_FOCUS);
            }
        } else {
            setFocusCond($scope, $interval, FocusConst.COMBOBOX_NOT_INIT_FOCUS);
        }
    }

    /**
     * function to set selected fields' empty value to zero
     *
     * @param
     * @return
     */
    $scope.setEmptyToZero = function() {
        for (var i = 0; i < $scope.queryResult.length; i++) {
            if (isEmpty($scope.queryResult[i].uriKin)) {
                $scope.queryResult[i].uriKin = 0;
            }

            if (isEmpty($scope.queryResult[i].tax)) {
                $scope.queryResult[i].tax = 0;
            }

            if (isEmpty($scope.queryResult[i].utiTax)) {
                $scope.queryResult[i].utiTax = 0;
            }

            if (isEmpty($scope.queryResult[i].kyaksu)) {
                $scope.queryResult[i].kyaksu = 0;
            }

            if (isEmpty($scope.queryResult[i].uriSu)) {
                $scope.queryResult[i].uriSu = 0;
            }
        }

        for (var i = 0; i < $scope.grid2.length; i++) {
            if (isEmpty($scope.grid2[i].kingaku) && !$scope.disableGrid2(0, i)) {
                $scope.grid2[i].kingaku = 0;
            }

            if (isEmpty($scope.grid2[i].maiSu) && !$scope.disableGrid2(1, i)) {
                $scope.grid2[i].maiSu = 0;
            }
        }

        if (isEmpty($scope.megaQueryResult.tenKyakSu)) {
            $scope.megaQueryResult.tenKyakSu = 0;
        }
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
                for (var i = 0; i < response.data.errors.length; i++) {
                    if (response.data.errors[i].name == 'uriKin') {
                        $scope.errorNo = 1;
                        Message.showMessageWithContent(response.data.errors[i].level, response.data.errors[i].message);
                        return;
                    }
                    if (response.data.errors[i].name == 'tax') {
                        $scope.errorNo = 2;
                        Message.showMessageWithContent(response.data.errors[i].level, response.data.errors[i].message);
                        return;
                    }
                    if (response.data.errors[i].name == 'utiTax') {
                        $scope.errorNo = 3;
                        Message.showMessageWithContent(response.data.errors[i].level, response.data.errors[i].message);
                        return;
                    }
                    if (response.data.errors[i].name == 'kyaksu') {
                        $scope.errorNo = 4;
                        Message.showMessageWithContent(response.data.errors[i].level, response.data.errors[i].message);
                        return;
                    }
                    if (response.data.errors[i].name == 'uriTenSu') {
                        $scope.errorNo = 5;
                        Message.showMessageWithContent(response.data.errors[i].level, response.data.errors[i].message);
                        return;
                    }
                }

                Message.showMessageWithContent(response.data.errors[0].level, response.data.errors[0].message);
                $scope.error = Client.showErrorFromServer($scope.error, response.data.errors);
                if (response.data.errors[1] && response.data.errors[1].code == MsgConst.MSG_KEY_SALE_NOT_MATCH) {
                    $scope.errorNo = 1;
                    $scope.error = Client.showErrorClientWithFlag($scope.error, 'kingaku0', MsgConst.MSG_KEY_SALE_NOT_MATCH, false);
                } else {
                    $scope.error = Client.removeErrorClient($scope.error, 'kingaku0', MsgConst.MSG_KEY_SALE_NOT_MATCH);
                }
            }
        } else {
            Message.showMessage(MsgConst.MSG_KEY_SYSTEM_ERROR);
        }
        $scope.setFocus();
    }

    /**
     * function to calculate grid2
     *
     * @param
     * @return
     */
    $scope.calculateGrid2 = function() {
        if ($scope.grid2.length <= 0) {
            return;
        }

        var sum2kingaku = isEmpty($scope.grid2[3].kingaku) ? 0 : parseInt($scope.grid2[3].kingaku);
        sum2kingaku += isEmpty($scope.grid2[4].kingaku) ? 0 : parseInt($scope.grid2[4].kingaku);
        sum2kingaku += isEmpty($scope.grid2[5].kingaku) ? 0 : parseInt($scope.grid2[5].kingaku);
        sum2kingaku += isEmpty($scope.grid2[6].kingaku) ? 0 : parseInt($scope.grid2[6].kingaku);
        sum2kingaku += isEmpty($scope.grid2[7].kingaku) ? 0 : parseInt($scope.grid2[7].kingaku);
        sum2kingaku += isEmpty($scope.grid2[8].kingaku) ? 0 : parseInt($scope.grid2[8].kingaku);
        sum2kingaku += isEmpty($scope.grid2[9].kingaku) ? 0 : parseInt($scope.grid2[9].kingaku);
        sum2kingaku += isEmpty($scope.grid2[10].kingaku) ? 0 : parseInt($scope.grid2[10].kingaku);
        $scope.grid2[2].kingaku = isNaN(sum2kingaku) ? $scope.grid2[2].kingaku : sum2kingaku;

        // var sum11kingaku = isEmpty($scope.grid3[0].tax) ? 0 : parseInt($scope.grid3[0].tax);
        // sum11kingaku += isEmpty($scope.grid3[0].utiTax) ? 0 : parseInt($scope.grid3[0].utiTax);
        // $scope.grid2[11].kingaku = isNaN(sum11kingaku) ? $scope.grid2[11].kingaku : sum11kingaku;


        var sum12kingaku = (isEmpty($scope.grid2[0].kingaku) ? 0 : parseInt($scope.grid2[0].kingaku))
                        + (isEmpty($scope.grid2[11].kingaku) ? 0 : parseInt($scope.grid2[11].kingaku));
        $scope.grid2[12].kingaku = isNaN(sum12kingaku) ? $scope.grid2[12].kingaku : sum12kingaku;


        var sum13kingaku = (isEmpty($scope.grid2[1].kingaku) ? 0 : parseInt($scope.grid2[1].kingaku))
                         - (isEmpty($scope.grid2[12].kingaku) ? 0 : parseInt($scope.grid2[12].kingaku))
                         + (isEmpty($scope.grid2[2].kingaku) ? 0 : parseInt($scope.grid2[2].kingaku));
        $scope.grid2[13].kingaku = isNaN(sum13kingaku) ? $scope.grid2[1].kingaku : sum13kingaku;

    }

    $scope.$on('kingakuBlur', function(event) {
        $scope.calculateGrid2();
    })

    $scope.$on('maiSuBlur', function(event) {
        $scope.calculateGrid2();
    })

    $scope.$watch('grid2[0].kingaku', function() {
        if ($scope.error != undefined && $scope.error[1] != undefined && $scope.error[1].code == 'U1036') {
            $scope.errorNo = -1;
        }
    }, false);

    /**
     * function to calculate grid1
     *
     * @param
     * @return
     */
    $scope.calculateGrid1UriKin = function() {
        var sum1 = 0;
        for (var i = 0; i < $scope.queryResult.length; i++) {
            sum1 += isEmpty($scope.queryResult[i].uriKin) ? 0 : parseInt($scope.queryResult[i].uriKin);
        }
        //TODO
        if ($scope.grid3[0].uriKin != sum1 && $scope.errorNo == 1) {
            $scope.resetError();
        }
        $scope.grid3[0].uriKin = sum1;
    }

    /**
     * function to calculate grid1
     *
     * @param
     * @return
     */
    $scope.calculateGrid1Tax = function() {
        var sum2 = 0;
        for (var i = 0; i < $scope.queryResult.length; i++) {
            sum2 += isEmpty($scope.queryResult[i].tax) ? 0 : parseInt($scope.queryResult[i].tax);
        }

        if ($scope.grid3[0].tax != sum2 && $scope.errorNo == 2) {
            $scope.resetError();
        }

        $scope.grid3[0].tax = sum2;
    }
    /**
     * function to calculate grid1
     *
     * @param
     * @return
     */
    $scope.calculateGrid1UtiTax = function() {
        var sum3 = 0;
        for (var i = 0; i < $scope.queryResult.length; i++) {
            sum3 += isEmpty($scope.queryResult[i].utiTax) ? 0 : parseInt($scope.queryResult[i].utiTax);
        }

        if ($scope.grid3[0].utiTax != sum3 && $scope.errorNo == 3) {
            $scope.resetError();
        }

        $scope.grid3[0].utiTax = sum3;
    }

    $scope.$on("uriKinBlur", function(event) {
        $scope.calculateGrid1UriKin();
    });

    $scope.$on("taxBlur", function(event) {
        $scope.calculateGrid1Tax();
        $scope.calculateGrid2();
    });

    $scope.$on("utiTaxBlur", function(event) {
        $scope.calculateGrid1UtiTax();
        $scope.calculateGrid2();
    });


    var rowTemplate = '<div ng-repeat="col in renderedColumns" ng-class="col.colIndex()" class="ngCell {{col.cellClass}}"><div class="ngVerticalBar" ng-style="{height: rowHeight}" ng-class="{ ngVerticalBarVisible: !$last }">&nbsp;</div><div ng-cell-custom></div></div>';
    $scope.gridOptionsURJP0040BmnBaiyoArea = {
        data: 'queryResult',
        headerRowHeight: 19.0,
        rowHeight: 19.0,
        enableRowSelection: false,
        enableCellSelection: true,
        rowTemplate: rowTemplate,
        enableSorting: false,
        enableColumnResize: true,
        columnDefs: [{
            field: 'bmnCd',
            displayName: '部門コード',
            width: 65,
            cellTemplate: '<div class="cellPadding " ng-class="col.colIndex()"><span ng-cell-text>{{row.getProperty(col.field)}}</span></div>'
        }, {
            field: 'bmnNm',
            displayName: '部門名称',
            width: 156.0,
            cellTemplate: '<div class="cellPadding " ng-class="col.colIndex()"><span ng-cell-text>{{row.getProperty(col.field)}}</span></div>'
        }, {
            field: 'uriKin',
            displayName: '売上高（税抜き）',
            width: 125,
            cellTemplate: '<cc-number-input id="uriKin" cc-focus="getFocusResult(row.rowIndex)" cc-label="" cc-partition="3" ng-model="queryResult[row.rowIndex].uriKin" cc-required=false cc-min="0" cc-max="999999999" cc-width="122" cc-readonly="disableResult()||getKakuteFlag()"/>'
        }, {
            field: 'tax',
            displayName: '消費税(外)',
            width: 100,
            cellTemplate: '<cc-number-input id="tax" cc-label="" cc-partition="3" ng-model="queryResult[row.rowIndex].tax" cc-required=false cc-min="0" cc-max="999999999" cc-width="97" cc-readonly="disableResult()||getKakuteFlag()"/>'
        }, {
            field: 'utiTax',
            displayName: '消費税(内)',
            width: 100,
            cellTemplate: '<cc-number-input id="utiTax" cc-label="" cc-partition="3" ng-model="queryResult[row.rowIndex].utiTax" cc-required=false cc-min="0" cc-max="999999999" cc-width="97" cc-readonly="disableResult()||getKakuteFlag()"/>'
        }, {
            field: 'kyaksu',
            displayName: '客数',
            width: 60,
            cellTemplate: '<cc-number-input id="kyaksu" cc-label="" cc-partition="3" ng-model="queryResult[row.rowIndex].kyaksu" cc-required=false cc-min="0" cc-max="999999" cc-readonly="disableResult()||getKakuteFlag()" cc-width="57"/>'
        }, {
            field: 'uriSu',
            displayName: '点数',
            width: 60,
            cellTemplate: '<cc-number-input id="uriSu" cc-label="" cc-partition="3" ng-model="queryResult[row.rowIndex].uriSu" cc-required=false cc-min="0" cc-max="999999" cc-readonly="disableResult()||getKakuteFlag()" cc-width="57"/>'
        }]
    };

    $scope.grid2Title = ['売上金額（税抜）', '現金在高', '現金以外の売上', '商品券500', '商品券1000', '社内売上伝票', '売掛伝票', 'クレジット', 'ギフト券', '値引券','その他', '消費税', '売上総計', '現金過不足'];

    /**
     * function to get padding css classes
     *
     * @param
     * @return padding-css classes
     */
    $scope.getPadding = function(rowIndex) {
        var arr1 = [3, 4, 5, 6, 7, 8, 9, 10];
        if (rowIndex == 0) {
            return 'cellBorderTop';
        }

        if (arr1.indexOf(rowIndex) != -1) {
            return 'padding-left-medium';
        }
        return '';
    }

    $scope.gridOptionsURJP0040BaiyoSokeiArea = {
        data: 'grid2',
        headerRowHeight: 19.0,
        rowHeight: 19.0,
        enableRowSelection: false,
        enableCellSelection: true,
        rowTemplate: rowTemplate,
        enableSorting: false,
        enableColumnResize: false,
        columnDefs: [{
            field: 'komokuTitle',
            displayName: '',
            width: 100,
            cellClass: 'cellAsHeader',
            cellTemplate: '<div class="" ng-class="getPadding(row.rowIndex)"><span ng-cell-text>{{grid2Title[row.rowIndex]}}</span></div>'
        }, {
            field: 'kingaku',
            displayName: '金額',
            width: 95,
            cellTemplate: '<cc-number-input id="kingaku" cc-label="" name="{{\'kingaku\' + row.rowIndex}}"" cc-partition="3" ng-model="grid2[row.rowIndex].kingaku" cc-required=false cc-min="-999999999" cc-max="999999999" cc-width="92" cc-readonly="disableResult() || disableGrid2(0, row.rowIndex)" cc-error="error" cc-focus="getFocusResult2(row.rowIndex)"/>'
        }, {
            field: 'maiSu',
            displayName: '枚数',
            width: 60,
            cellTemplate: '<cc-number-input id="maiSu" cc-label="" cc-partition="3" ng-model="grid2[row.rowIndex].maiSu" cc-required=false cc-min="-999999" cc-max="999999" cc-width="57" cc-readonly="disableResult() || disableGrid2(1, row.rowIndex)" />'
        }]
    };

    $scope.grid3 = [{
        bmnBetuSokei: '部門別総計',
        uriKin: '',
        tax: '',
        utiTax: ''
    }];

    $scope.gridOptionsURJP0040BmnBetuSokeiArea = {
        data: 'grid3',
        headerRowHeight: 0,
        rowHeight: 19.0,
        enableRowSelection: false,
        enableCellSelection: true,
        rowTemplate: rowTemplate,
        enableSorting: false,
        enableColumnResize: false,
        columnDefs: [{
            field: 'bmnBetuSokei',
            width: 100,
            cellTemplate: '<div class="cellPadding cellAsHeader text-center" ng-class="col.colIndex()"><span ng-cell-text>{{row.getProperty(col.field)}}</span></div>'
        }, {
            field: 'uriKin',
            displayName: '売上高',
            width: 125,
            cellTemplate: '<div class="cellPadding text-right" ng-class="{errorCell18: (errorNo == 1)}"><span ng-cell-text>{{row.getProperty(col.field) | number:0}}</span></div>'
        }, {
            field: 'tax',
            displayName: '消費税（外）',
            width: 100,
            cellTemplate: '<div class="cellPadding text-right" ng-class="{errorCell18: (errorNo == 2)}"><span ng-cell-text>{{row.getProperty(col.field) | number:0}}</span></div>'
        }, {
            field: 'utiTax',
            displayName: '消費税（内）',
            width: 100,
            cellTemplate: '<div class="cellPadding text-right" ng-class="{errorCell18: (errorNo == 3)}"><span ng-cell-text>{{row.getProperty(col.field) | number:0}}</span></div>'
        }]
    };

    /**
     * function to enable button search
     *
     * @param
     * @return boolean, true: enable, false: disable
     */
    $scope.canSearch = function() {
        return ($scope.mode != $scope.editMode && $scope.form.condForm.$valid);
    }

    /**
     * function to enable button searchEdit
     *
     * @param
     * @return boolean, true: enable, false: disable
     */
    $scope.canSearchEdit = function() {
        return ($scope.mode != $scope.editMode && $scope.form.condForm.$valid);
    }

    /**
     * function to enable button save
     *
     * @param
     * @return boolean, true: enable, false: disable
     */
    $scope.canSave = function() {
        if ($scope.mode != $scope.editMode) {
            return;
        }

        var modified = false;
        for (var i = 0; i < $scope.cloneMegaQueryResult.grid2Area.length; i++) {
            if (modified) {
                break;
            }
            if (!isEmpty($scope.cloneMegaQueryResult.grid2Area[i].kingaku) || !isEmpty($scope.megaQueryResult.grid2Area[i].kingaku)) {
                if ($scope.cloneMegaQueryResult.grid2Area[i].kingaku != $scope.megaQueryResult.grid2Area[i].kingaku) {
                    modified = true;
                }
            }

            if (!isEmpty($scope.cloneMegaQueryResult.grid2Area[i].maiSu) || !isEmpty($scope.megaQueryResult.grid2Area[i].maiSu)) {
                if ($scope.cloneMegaQueryResult.grid2Area[i].maiSu != $scope.megaQueryResult.grid2Area[i].maiSu) {
                    modified = true;
                }
            }
        }

        for (var i = 0; i < $scope.cloneMegaQueryResult.bmnBaiyoArea.length; i++) {
            if (modified) {
                break;
            }

            if (!isEmpty($scope.cloneMegaQueryResult.bmnBaiyoArea[i].uriKin) || !isEmpty($scope.megaQueryResult.bmnBaiyoArea[i].uriKin)) {
                if ($scope.cloneMegaQueryResult.bmnBaiyoArea[i].uriKin != $scope.megaQueryResult.bmnBaiyoArea[i].uriKin) {
                    modified = true;
                }
            }

            if (!isEmpty($scope.cloneMegaQueryResult.bmnBaiyoArea[i].tax) || !isEmpty($scope.megaQueryResult.bmnBaiyoArea[i].tax)) {
                if ($scope.cloneMegaQueryResult.bmnBaiyoArea[i].tax != $scope.megaQueryResult.bmnBaiyoArea[i].tax) {
                    modified = true;
                }
            }

            if (!isEmpty($scope.cloneMegaQueryResult.bmnBaiyoArea[i].utiTax) || !isEmpty($scope.megaQueryResult.bmnBaiyoArea[i].utiTax)) {
                if ($scope.cloneMegaQueryResult.bmnBaiyoArea[i].utiTax != $scope.megaQueryResult.bmnBaiyoArea[i].utiTax) {
                    modified = true;
                }
            }

            if (!isEmpty($scope.cloneMegaQueryResult.bmnBaiyoArea[i].kyaksu) || !isEmpty($scope.megaQueryResult.bmnBaiyoArea[i].kyaksu)) {
                if ($scope.cloneMegaQueryResult.bmnBaiyoArea[i].kyaksu != $scope.megaQueryResult.bmnBaiyoArea[i].kyaksu) {
                    modified = true;
                }
            }

            if (!isEmpty($scope.cloneMegaQueryResult.bmnBaiyoArea[i].uriSu) || !isEmpty($scope.megaQueryResult.bmnBaiyoArea[i].uriSu)) {
                if ($scope.cloneMegaQueryResult.bmnBaiyoArea[i].uriSu != $scope.megaQueryResult.bmnBaiyoArea[i].uriSu) {
                    modified = true;
                }
            }
        }

        if ($scope.cloneMegaQueryResult.tenKyakSu != $scope.megaQueryResult.tenKyakSu || $scope.cloneMegaQueryResult.weather != $scope.megaQueryResult.weather) {
            modified = true;
        }

        return ($scope.mode == $scope.editMode && modified);
    }

    /**
     * function to enable button delete
     *
     * @param
     * @return boolean, true: enable, false: disable
     */
    $scope.canDelete = function() {
        return ($scope.mode == $scope.editMode && !$scope.isInsert);
    }

    /**
     * function to handle button search
     *
     * @param
     * @return
     */
    $scope.doSearch = function() {
        $scope.resetResult();

        var showResult = URJP0040.query($scope.cond, function() {

            $scope.megaQueryResult = showResult;
            $scope.queryResult = $scope.megaQueryResult.bmnBaiyoArea;
            $scope.grid2 = $scope.megaQueryResult.grid2Area;

            $scope.grid3[0].uriKin = showResult.uriKin;
            $scope.grid3[0].kyaksu = showResult.kyaksu;
            $scope.grid3[0].uriTenSu = showResult.uriTenSu;

            $scope.mode = $scope.searchMode;
            $scope.calculateGrid2();
            $scope.disCond = false;
            $scope.setFocus();
            Message.showMessage(MsgConst.MSG_KEY_DISPLAY_CORRECT_DATA);
        }, responseHandling);
    }

    /**
     * function to handle button searchEdit
     *
     * @param
     * @return
     */
    $scope.doSearchEdit = function() {
        $scope.resetResult();

        var showResult = URJP0040.query($scope.cond, function() {
            $scope.isInsert = showResult.insert;
            if ($scope.isInsert) {
                var diag = Dialog.confirm(Message.getMessage(MsgConst.MSG_KEY_CONFIRM_NEW_REGISTERED));
                if (diag == null) {
                    return;
                }
                diag.result.then(function() {
                    $scope.megaQueryResult = showResult;
                    $scope.queryResult = $scope.megaQueryResult.bmnBaiyoArea;
                    $scope.grid2 = $scope.megaQueryResult.grid2Area;

                    $scope.calculateGrid2();
                    $scope.cloneMegaQueryResult = angular.copy($scope.megaQueryResult);

                    $scope.grid3[0].uriKin = $scope.megaQueryResult.uriKin;
                    $scope.grid3[0].tax = $scope.megaQueryResult.tax;
                    $scope.grid3[0].utiTax = $scope.megaQueryResult.utiTax;

                    $scope.mode = $scope.editMode;
                    $scope.disCond = true;
                    $scope.setFocus();
                    Message.showMessage(MsgConst.MSG_KEY_INFO_BUTTON_SAVE);
                }, function() {
                    $scope.mode = $scope.initMode;
                    $scope.disCond = false;
                    $scope.setFocus();
                    Message.showMessage(MsgConst.MSG_KEY_COMMON_INFO_MESSAGE);
                });
            } else {
                $scope.megaQueryResult = showResult;
                $scope.queryResult = $scope.megaQueryResult.bmnBaiyoArea;
                $scope.grid2 = $scope.megaQueryResult.grid2Area;

                $scope.calculateGrid2();
                $scope.cloneMegaQueryResult = angular.copy($scope.megaQueryResult);

                $scope.grid3[0].uriKin = $scope.megaQueryResult.uriKin;
                $scope.grid3[0].tax = $scope.megaQueryResult.tax;
                $scope.grid3[0].utiTax = $scope.megaQueryResult.utiTax;

                $scope.mode = $scope.editMode;
                $scope.disCond = true;
                $scope.setFocus();
                Message.showMessage(MsgConst.MSG_KEY_INFO_BUTTON_DELETE_SAVE);
            }
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
    }

    /**
     *  function to prepare data to post to server
     *
     * @param
     * @return object: data to post to server
     */
    $scope.prepareSaveList = function() {
        var modifiedData = $scope.megaQueryResult;
        modifiedData.uriDate = $scope.cond.uriDate;
        modifiedData.kaisyaCd = $scope.cond.kaisyaCd;
        modifiedData.jigyobuCd = $scope.cond.jigyobuCd;
        modifiedData.tenCd = $scope.cond.tenCd;
        modifiedData.uriKin = $scope.grid3[0].uriKin;
        modifiedData.tax = $scope.grid3[0].tax;
        modifiedData.utiTax = $scope.grid3[0].utiTax;

        return modifiedData;
    }

    /**
     * function to handle button save
     *
     * @param
     * @return
     */
    $scope.doSave = function() {

        $scope.setEmptyToZero();

        if ($scope.isInsert) {
            var diag = Dialog.confirm(Message.getMessage(MsgConst.MSG_KEY_CONFIRM_BEFORE_INSERT));
            if (diag == null) {
                return;
            }
            diag.result.then(function() {
                $scope.resetError();

                var control = URJP0040.save($scope.prepareSaveList(), function() {
                    //success
                    $scope.mode = $scope.postEditMode;
                    $scope.disCond = false;
                    $scope.setFocus();
                    Message.showMessage(MsgConst.MSG_KEY_INSERT_SUCCESS);
                }, responseHandling);
            }, function() {
                $scope.setFocus();
            });
        } else {
            var diag = Dialog.confirm(Message.getMessage(MsgConst.MSG_KEY_CONFIRM_BEFORE_UPDATE));
            if (diag == null) {
                return;
            }
            diag.result.then(function() {
                $scope.resetError();

                var control = URJP0040.update($scope.prepareSaveList(), function() {
                    //success
                    $scope.mode = $scope.postEditMode;
                    $scope.disCond = false;
                    $scope.setFocus();
                    Message.showMessage(MsgConst.MSG_KEY_UPDATE_SUCCESS);
                }, responseHandling);
            }, function() {
                $scope.setFocus();

            });
        }
    }

    /**
     * function to handle button delete
     *
     * @param
     * @return
     */
    $scope.doDelete = function() {
        var diag = Dialog.confirm(Message.getMessage(MsgConst.MSG_KEY_CONFIRM_BEFORE_DELETE));
        if (diag == null) {
            return;
        }
        diag.result.then(function() {
            var control = URJP0040DEL.delete($scope.prepareSaveList(), function() {
                //success
                $scope.mode = $scope.postEditMode;
                $scope.disCond = false;
                $scope.setFocus();
                Message.showMessage(MsgConst.MSG_KEY_DELETE_SUCCESS);
            }, responseHandling);
        }, function() {
            $scope.resetError();
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
            if ($scope.mode == $scope.initMode) {
                $scope.resetCondForm();
            }

            $scope.form.$setPristine();
            $scope.resetResult();

            Message.showMessage(MsgConst.MSG_KEY_COMMON_INFO_MESSAGE);

            $scope.mode = $scope.initMode;
            $scope.disCond = false;
            $scope.setFocus();
        }, function() {
            $scope.setFocus();
        });
    }
});