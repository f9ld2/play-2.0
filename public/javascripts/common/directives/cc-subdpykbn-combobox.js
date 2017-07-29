/**
 * @ngdoc directive
 * @name chasecore.directive:cc-subdpykbn-combobox
 * @restrict A
 * @function
 *
 * @description
 * 002:部門００２
 *
 * @example
    <doc:example>
      <doc:source>
         <cc-subdpykbn-combobox cc-label="手書入力区分" cc-partition="3" id="dnpSubKbn" name="dnpSubKbn" ng-model="cond.dnpSubKbn" 
             cc-required=true cc-parameter01="cond.dpyKbn" />
      </doc:source>
      <doc:scenario>
      </doc:scenario>
    </doc:example>
 */

diretiveApp.directive('ccSubdpykbnCombobox', function(CSS_CLASS, $http, $compile, $timeout, $interval, $rootScope, ClearComboboxOptions) {
    function getSubDpyKbnDataList(denKbn, selectElement, attrs, element, scope) {
        var url = '/core/codename/m708dpkm1/' + denKbn;
        $http({
            method: 'GET',
            url: url
        }).success(function(data) {
            ClearComboboxOptions(scope, attrs);
            //create options
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
            ccParameter01: '=',
            ccReadonly: '=',
            ccError: '=',
            ccWidth: '=',
            ccComboboxFocus: '=',
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
                uiSelect2 = 'ui-select2 = "{ allowClear: false}"';
            }

            if (angular.isDefined(attrs.ccRequired) && attrs.ccRequired == 'true') {
                readOnlyRequired += ' required ';
                uiSelect2 = 'ui-select2 = "{ allowClear: false}"';
            } else if (angular.isDefined(attrs.ccRequired) && attrs.ccRequired == 'false') {
                uiSelect2 = 'ui-select2 = "{ allowClear: true}"';
            }

            // style for label
            var labelClass = '';
            if (angular.isUndefined(attrs.ccPartition) && attrs.ccPartition == '') {
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
            htmlText += ' cc-blur-validation-combobox error-message="{{error}}" ';
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
            var selectCtrl = angular.element(element.find('select')[0]).controller('ngModel');
            var selectElement = angular.element(element.find('select')[0]);

            //Blur event
            selectElement.on("select2-blur", function() {
                scope.$emit(this.id + "Blur");
                attrs.$observe('errorMessage', function(value) {
                    $rootScope.$broadcast('errorMessage', value);
                });
            });
            //open event
            selectElement.on("select2-open", function() {
                scope.$parent.$broadcast("select2-open", attrs.id);
            });
            scope.$on('$destroy', function() {
                selectElement.unbind();
                selectElement.off();
            })

            // validate cc-select for the first load
            var validate = function(denKbn, modelValue) {
                var url = '/core/codename/m708dpkm1/' + denKbn;
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

            //コンボボックスのリストを生成する。
            var loadData = function() {
                if (isEmpty(scope.ccParameter01)) {
                    ClearComboboxOptions(scope, attrs);
                } else {
                    getSubDpyKbnDataList(scope.ccParameter01, selectElement, attrs, element, scope);
                }
            }

            scope.$watch('ngModel', function(newValue, oldValue) {
                if (!isEmpty(scope.ngModel)) {
                    validate(scope.ccParameter01, scope.ngModel);
                }
                scope.$emit('ccResetServerClientError', attrs.name);
                scope.$broadcast("resetServerError");
            });

            scope.$watch('ccParameter01', function(newValue, oldValue) {
                loadData();
            });

            scope.$on("pressF2Key", function(event, data) {
                selectElement.select2("close");
            });
            scope.$on("pressF3Key", function(event, data) {
                selectElement.select2("close");
            });
        }
    }
});