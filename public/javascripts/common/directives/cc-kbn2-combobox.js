/**
 * @ngdoc directive
 * @name chasecore.cc-kbn2-combobox
 * @restrict E
 * @function
 *
 * @description
 * (部品生成時)区分マスタから『コード』を取得・設定する(年/月/日/時/分/秒)
 *
 * @example
    <doc:example>
      <doc:source>
        <cc-kbn2-combobox
            cc-label="棚卸月"
            cc-partition="3"
            id="tanaMm"
            name="tanaMm"
            ng-model="cond.tanaMm"
            cc-required=true
            ng-maxlength="5"
            cc-key1="X001" >
        </cc-kbn2-combobox>
      </doc:source>
      <doc:scenario>
      </doc:scenario>
    </doc:example>
 */

diretiveApp.directive('ccKbn2Combobox', function(CSS_CLASS, $http, $compile, $timeout, X007KbnMaster, UserInfo, $rootScope, ClearComboboxOptions, AddKbn2ComboboxOptions) {
    /**
     * Get Current Year
     */
    function getCurrentYear() {
        return UserInfo.sysCurrentYear;
    };

    /**
     * Get Current Month
     */
    function getCurrentMonth() {
        return UserInfo.sysCurrentMonth;
    };

    /**
     * call server get data.
     */
    function getListKbn2(cdKbn, selectElement, element, attrs, scope) {
        var listKbn = X007KbnMaster.getX007Kbn(cdKbn);
        // 3) 『DataList(仮Ver.)』からComboBoxのDataList(本Ver.)を生成
        ClearComboboxOptions(scope, attrs);
        AddKbn2ComboboxOptions(scope, listKbn);

        if (cdKbn == CSS_CLASS.yearKbn) {
        	if (scope.ngModel == undefined) {
        		scope.ngModel = getCurrentYear();
        	}
        }
        if (cdKbn == CSS_CLASS.monthKbn) {
        	if (scope.ngModel == undefined) {
        		scope.ngModel = getCurrentMonth();
        	}
        }
    };

    return {
        restrict: 'E',
        require: 'ngModel',
        scope: {
            ngModel: '=',
            ccReadonly: '=',
            ccComboboxFocus: '=',
            ccFocus: '=',
            ccError: '=',
            ccWidth: '='
        },
        template: function(element, attrs) {
            // ui-select2
            var uiSelect2 = '';

            // either readOnly or required
            var readOnlyRequired = '';
            if (angular.isDefined(attrs.ccComboboxFocus)) {
                readOnlyRequired += ' cc-combobox-focus="ccComboboxFocus" ';
            }
            if (angular.isDefined(attrs.ccFocus)) {
                readOnlyRequired += ' cc-focus="ccFocus"';
            }

            if (angular.isDefined(attrs.ccReadonly)) {
                readOnlyRequired += ' ng-disabled="ccReadonly" ng-class="{\'' + CSS_CLASS.disable + '\': ccReadonly, \'\': !ccReadonly}"';
                uiSelect2 = 'ui-select2 = "{allowClear: false}"';
            }

            if (angular.isDefined(attrs.ccRequired) && attrs.ccRequired == 'true') {
                readOnlyRequired += 'required';
                // パラメータから[allowClear]の値を設定する。
                uiSelect2 = 'ui-select2 = "{allowClear: false}"';
            } else if (angular.isDefined(attrs.ccRequired) && attrs.ccRequired == 'false') {
                // パラメータから[allowClear]の値を設定する。
                uiSelect2 = 'ui-select2 = "{allowClear: true}"';
            }

            // style for label
            // 1) 項目タイトル：パラメータの内容に従う。
            var labelClass = '';
            if (angular.isDefined(attrs.ccPartition) && attrs.ccPartition == '') {
                labelClass += CSS_CLASS.label_divide_zero;
            } else {
                if (angular.isDefined(attrs.ccRequired) && attrs.ccRequired == 'true') {
                    labelClass += CSS_CLASS.cs_label_large_required;
                } else {
                    labelClass += CSS_CLASS.cs_label_large;
                }
            }
            labelClass += CSS_CLASS.BLANK + CSS_CLASS.label;

            // class for select
            // 2) コンボボックス：パラメータの内容に従う。
            var selectClass = '';
            switch (attrs.ngMaxlength) {
                case '1':
                    selectClass += CSS_CLASS.char_5;
                    break;
                case '2':
                    selectClass += CSS_CLASS.char_6;
                    break;
                case '3':
                    selectClass += CSS_CLASS.char_7;
                    break;
                case '4':
                    selectClass += CSS_CLASS.char_8;
                    break;
                case '5':
                    selectClass += CSS_CLASS.char_9;
                    break;
                case '6':
                    selectClass += CSS_CLASS.char_10;
                    break;
                case '7':
                    selectClass += CSS_CLASS.char_11;
                    break;
                case '8':
                    selectClass += CSS_CLASS.char_12;
                    break;
                case '9':
                    selectClass += CSS_CLASS.char_13;
                    break;
                case '10':
                    selectClass += CSS_CLASS.char_14;
                    break;
                case '11':
                    selectClass += CSS_CLASS.char_15;
                    break;
                case '12':
                    selectClass += CSS_CLASS.char_16;
                    break;
                case '13':
                    selectClass += CSS_CLASS.char_17;
                    break;
                case '14':
                    selectClass += CSS_CLASS.char_18;
                    break;
                case '15':
                    selectClass += CSS_CLASS.char_19;
                    break;
                case '16':
                    selectClass += CSS_CLASS.char_20;
                    break;
            }

            selectClass += CSS_CLASS.BLANK + CSS_CLASS.left;

            var htmlText = '';

            if (angular.isDefined(attrs.ccPartition) && attrs.ccPartition == '') {
                htmlText += '<div class="' + labelClass + '" > ' + attrs.ccLabel;
            } else {
                htmlText += '<div class="' + labelClass + '">' + attrs.ccLabel;
                if (angular.isDefined(attrs.ccRequired) && attrs.ccRequired == 'true') {
                    htmlText += '<span class="' + CSS_CLASS.txt_required + '"> *</span>';
                }
            }
            htmlText += '</div>';

            htmlText += '<select ' + uiSelect2 + ' class="' + selectClass + '" id="' + attrs.id + '" name="' + attrs.name + '" ng-model="ngModel" ' + readOnlyRequired;
            htmlText += ' error-message="{{error}}" cc-blur-validation-combobox ';
            if (!isEmpty(attrs.ccWidth)) {
                htmlText += ' ng-style="{width: ccWidth}"';
            }
            htmlText += ' >';
            htmlText += (attrs.ccRequired == 'false' ? '<option value=""></option>' : '');
            htmlText += '<option ng-repeat="option in optionArr" value="{{option.value}}">{{option.text}}</option>';
            htmlText += '</select>';

            return htmlText;
        },
        link: function(scope, element, attrs, ctrl) {
            var selectCtrl = angular.element(element.find('select')[0]).controller('ngModel');
            var selectElement = angular.element(element.find('select')[0]);

            //Blur event
            selectElement.on("select2-blur", function() {
                scope.$emit(this.id + "Blur");
            });

            scope.$on('$destroy', function() {
                selectElement.unbind();
                selectElement.off();
            })

            // // formatter only if validity for the first time combobox is loaded
            // // must using timeout to get current apply or digest
            var validate = function(modelValue) {
                selectCtrl.$setValidity('ccSelect', true);
                if (!isEmpty(modelValue)) {
                    if (isEmpty(element.attr('cc-key1'))) {
                        selectCtrl.$setValidity('ccSelect', false);
                        scope.$broadcast(element.id + 'Error');
                        scope.ngModel = undefined;
                        return;
                    }

                    var listKbn = X007KbnMaster.getX007Kbn(element.attr('cc-key1'));
                    for (var i = 0; i < listKbn.length; i++) {
                        if (angular.equals(listKbn[i].code, modelValue)) {
                            flg = true;
                            break;
                        }
                    }
                    if (!flg) {
                        selectCtrl.$setValidity('ccSelect', false);
                        scope.$broadcast(element.id + 'Error');
                        scope.ngModel = undefined;
                    } else {
                        selectCtrl.$setValidity('ccSelect', true);
                    }
                }
            }

            scope.$watch('ngModel', function(newValue, oldValue) {
                if (!isEmpty(scope.ngModel)) {
                    validate(scope.ngModel);
                }
                scope.$emit('ccResetServerClientError', attrs.name);
                scope.$broadcast("resetServerError");
            });


            // load data for combobox
            var loadData = function() {
                // 1) パラメータチェック
                if (isEmpty(element.attr('cc-key1'))) {
                    ClearComboboxOptions(scope, attrs);
                } else {
                    getListKbn2(element.attr('cc-key1'), selectElement, element, attrs, scope);
                }
            }
            loadData();

            scope.$on("pressF2Key", function(event, data) {
                selectElement.select2("close");
            });
            scope.$on("pressF3Key", function(event, data) {
                selectElement.select2("close");
            });
            //open event
            selectElement.on("select2-open", function() {
                scope.$parent.$broadcast("select2-open", attrs.id);
            });
        }
    }
});