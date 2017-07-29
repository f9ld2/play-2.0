/**
 * @ngdoc directive
 * @name chasecore.cc-dpykbn-combobox
 * @restrict E
 * @function
 *
 * @description
 * (部品生成時)区分マスタから『コード＋名称』を取得・設定する
 *
 * @example
    <doc:example>
      <doc:source>
        <cc-dpykbn-combobox
            cc-label="伝票区分"
            cc-partition="3"
            id="denKbn"
            name="denKbn"
            ng-model="cond.denKbn"
            ng-maxlength="2"
            cc-readonly=false
            cc-key1="14,24">
        </cc-dpykbn-combobox>
      </doc:source>
      <doc:scenario>
      </doc:scenario>
    </doc:example>
 */

diretiveApp.directive('ccDpykbnCombobox', function(CSS_CLASS, $http, $compile, $timeout, $rootScope, ClearComboboxOptions) {
    
    // call server get data.
    function getDpyKbnData(dpyKbn, selectElement, attrs, element, scope) {
        $http({
            method: 'GET',
            url: '/core/codename/m708dpkm/' + dpyKbn
        }).success(function(data) {
            // 3) 『DataList(仮Ver.)』からComboBoxのDataList(本Ver.)を生成
            ClearComboboxOptions(scope, attrs);

            angular.forEach(data, function(value, key) {
                if (value.code.length == attrs.ngMaxlength) {
                    scope.optionArr.push({
                        value : value.code,
                        text : value.code + ':' + value.name
                    });
                }
            });
        }).error(function(data) {});
    }

    return {
        restrict: 'E',
        require: 'ngModel',
        scope: {
            ngModel: '=',
            ccReadonly: '=',
            ccError: '=',
            ccComboboxFocus: '=',
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

            if (angular.isDefined(attrs.ccReadonly)) {
                readOnlyRequired += ' ng-disabled="ccReadonly" ng-class="{\'' + CSS_CLASS.disable + '\': ccReadonly, \'\': !ccReadonly}"';
                uiSelect2 = 'ui-select2 = "{allowClear: false}"';
            }

            if (angular.isDefined(attrs.ccRequired) && attrs.ccRequired == 'true') {
                readOnlyRequired += ' required ';
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
            var countByte = 10;
            var selectClass = '';
            countByte += parseInt(attrs.ngMaxlength) + 1;
            if (countByte <= 15) {
                selectClass += CSS_CLASS.char_15 + CSS_CLASS.BLANK + CSS_CLASS.left;
            } else {
                selectClass += CSS_CLASS.char_15 + CSS_CLASS.BLANK + CSS_CLASS.left;
            }
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
                htmlText += ' ng-style="{width: ccWidth}" ';
            }
            htmlText += ' >';
            htmlText += (attrs.ccRequired == 'false' ? '<option value=""></option>' : '');
            htmlText += '<option ng-repeat="option in optionArr" value="{{option.value}}">{{option.text}}</option>';
            htmlText += '</select>';

            return htmlText;
        },
        link: function(scope, element, attrs, ctrl) {
            var dataList = [];
            var selectCtrl = angular.element(element.find('select')[0]).controller('ngModel');
            var selectElement = angular.element(element.find('select')[0]);

            //Blur event
            selectElement.on("select2-blur", function() {
                scope.$emit(this.id + "Blur");
            });


            // formatter only if validity for the first time combobox is loaded
            // must using timeout to get current apply or digest
            var validate = function(dpyKbn, modelValue) {
                selectCtrl.$setValidity('ccSelect', true);
                if (!isEmpty(modelValue)) {
                    if (isEmpty(element.attr('cc-key1'))) {
                        selectCtrl.$setValidity('ccSelect', false);
                        scope.$broadcast(element.id + 'Error');
                        scope.ngModel = undefined;
                        return;
                    }

                    var url = '/core/codename/m708dpkm/' + dpyKbn;
                    return $http.get(
                        url, {}).then(function(response) {
                        flg = false;
                        angular.forEach(response.data, function(value, key) {
                            if (modelValue == value.code) {
                                flg = true;
                            }
                        });
                        if (!flg) {
                            selectCtrl.$setValidity('ccSelect', false);
                            scope.$broadcast(element.id + 'Error');
                            scope.ngModel = undefined;
                        } else {
                            selectCtrl.$setValidity('ccSelect', true);
                        }
                    });
                }
            }

            scope.$watch('ngModel', function(newValue, oldValue) {
                if (!isEmpty(scope.ngModel)) {
                    validate(element.attr('cc-key1'), scope.ngModel);
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
                	getDpyKbnData(element.attr('cc-key1'), selectElement, attrs, element, scope);
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