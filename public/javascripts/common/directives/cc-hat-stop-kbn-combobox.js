/**
 * @ngdoc directive
 * @name chasecore.cc-clean-kbn-combobox
 * @restrict E
 * @function
 *
 * @description
 * (部品生成時)区分マスタから『コード＋名称』を取得・設定する
 *
 * @example
    <doc:example>
      <doc:source>
        <cc-hat-stop-kbn-combobox
            cc-label="店舗区分"
            cc-partition="3"
            id="tenKbn"
            name="tenKbn"
            ng-model="result.tenKbn"
            ng-maxlength="1"
            ng-model="cond.tenKbn"
            cc-readonly=true
            cc-key1="M001"
            cc-shortname=false >
        </cc-hat-stop-kbn-combobox>
      </doc:source>
      <doc:scenario>
      </doc:scenario>
    </doc:example>
 */

diretiveApp.directive('ccHatStopKbnCombobox', function(CSS_CLASS, $http, $compile, $timeout, X007KbnMaster, $rootScope, ClearComboboxOptions, AddKbnComboboxOptions) {
    // call server get data.
    function getListKbn(cdKbn, selectElement, element, scope, attrs) {
        var listKbn = X007KbnMaster.getX007Kbn(cdKbn);
        // 3) 『DataList(仮Ver.)』からComboBoxのDataList(本Ver.)を生成
        ClearComboboxOptions(scope, attrs);
        AddKbnComboboxOptions(scope, listKbn);
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

            // either readOnly or required
            var readOnlyRequired = '';

            if (angular.isDefined(attrs.ccComboboxFocus)) {
                readOnlyRequired += ' cc-combobox-focus="ccComboboxFocus" ';
            }

            var htmlText = '';
            htmlText += '<select class="" id="' + attrs.id + '" name="' + attrs.name + '" ng-model="ngModel" ' + readOnlyRequired;
            htmlText += ' error-message="{{error}}" cc-blur-validation-combobox ';
            htmlText += 'ng-options="option.value as option.text for option in optionArr">';
            htmlText += (attrs.ccRequired == 'false' ? '<option value=""></option>' : '');
            htmlText += '</select>';

            return htmlText;
        },
        link: function(scope, element, attrs, ctrl) {
            var selectCtrl = angular.element(element.find('select')[0]).controller('ngModel');
            var selectElement = angular.element(element.find('select')[0]);
            var w = '107';
            if (!isEmpty(attrs.ccWidth)) {
                w = attrs.ccWidth;
            }

            selectElement.select2({
                width: w,
                allowClear: true
            });

            scope.$watch('ccReadonly', function(value) {
                selectElement.select2('enable', !value);
            }, false);

            //Blur event
            selectElement.on("select2-blur", function() {
                scope.$emit(this.id + "Blur");
            });

            // formatter only if validity for the first time combobox is loaded
            // must using timeout to get current apply or digest
            var validate = function(modelValue) {
                selectCtrl.$setValidity('ccSelect', true);
                if (!isEmpty(modelValue)) {
                    if (isEmpty(element.attr('cc-key1'))) {
                        selectCtrl.$setValidity('ccSelect', false);
                        scope.$broadcast(element.id + 'Error');
                        scope.ngModel = undefined;
                        return;
                    }

                    var flg = false;
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

            var firstLoad = true;
            scope.$watch('ngModel', function(newValue, oldValue) {
                // if (firstLoad) {
                    var indx = -1;
                    if (scope.optionArr != undefined) {
                        for (var i = 0; i < scope.optionArr.length; i++) {
                            if (scope.optionArr[i].value == selectCtrl.$viewValue) {
                                indx = i;
                            }
                        }
                    }
                    selectElement.select2('val', indx);
                    firstLoad = false;
                // }

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
                    getListKbn(element.attr('cc-key1'), selectElement, element, scope, attrs);
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