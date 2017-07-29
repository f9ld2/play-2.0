/**
 * @ngdoc directive
 * @name chasecore.directive:cc-bmn-input
 * @restrict A
 * @function
 *
 * @description
 * 002:部門００２
 *
 * @example
    <doc:example>
      <doc:source>
         <cc-bmn-combobox cc-label="部門" cc-partition="3" id="bmnCd" name="bmnCd" ng-model="result.bmnCd" 
             cc-required=true cc-delexist=true cc-shortname=false 
            cc-parameter01="result.jgyobuCd" cc-parameter02="result.hakkoDay" />
      </doc:source>
      <doc:scenario>
      </doc:scenario>
    </doc:example>
 */

diretiveApp.directive('ccBmnCombobox', function(CSS_CLASS, $http, $compile, $timeout, $interval, UserInfo, $rootScope, ClearComboboxOptions, AddComboboxOptions) {
    var unyoDate = UserInfo.unyoDate;

    function getListDepartment(jgyobuCd, hakkoDay, selectElement, attrs, element, scope) {
        //未設定の場合は運用日付を取得して代用する。
        if (isEmpty(hakkoDay)) {
            getDataListDepartment(jgyobuCd, unyoDate, selectElement, attrs, element, scope);
        } else {
            getDataListDepartment(jgyobuCd, hakkoDay, selectElement, attrs, element, scope);
        }
    }

    function getDataListDepartment(jgyobuCd, hakkoDay, selectElement, attrs, element, scope) {
        var url = '';
        if (isEmpty(jgyobuCd)) {
            url = '/core/codename/m000depars?hakkoDay=' + hakkoDay + '&ccDelexist=' + attrs.ccDelexist
        } else {
            url = '/core/codename/m000depar/' + jgyobuCd + '?hakkoDay=' + hakkoDay + '&ccDelexist=' + attrs.ccDelexist
        }

        $http({
            method: 'GET',
            url: url
        }).success(function(data) {
            ClearComboboxOptions(scope, attrs);
            AddComboboxOptions(scope, data, undefined, attrs.ccShortname);
        }).error(function(data) {});
    }

    return {
        restrict: 'E',
        require: 'ngModel',
        scope: {
            ngModel: '=',
            ccParameter01: '=',
            ccParameter02: '=',
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
            var selectClass = '';
            if (attrs.ccShortname == 'true') {
                selectClass += CSS_CLASS.char_15 + CSS_CLASS.BLANK + CSS_CLASS.left;
            } else if (attrs.ccShortname == 'false') {
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
            var validate = function(jgyobuCd, hakkoDay, modelValue) {
                var url = '';
                if (isEmpty(jgyobuCd)) {
                    url = '/core/codename/m000depars?hakkoDay=' + hakkoDay + '&ccDelexist=' + attrs.ccDelexist
                } else {
                    url = '/core/codename/m000depar/' + jgyobuCd + '?hakkoDay=' + hakkoDay + '&ccDelexist=' + attrs.ccDelexist
                }
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
                getListDepartment(scope.ccParameter01, scope.ccParameter02,
                    selectElement, attrs, element, scope);
            }

            scope.$watch('ngModel', function(newValue, oldValue) {
                if (!isEmpty(scope.ngModel)) {
                    if (isEmpty(scope.ccParameter02)) {
                        validate(scope.ccParameter01, unyoDate, scope.ngModel);
                    } else {
                        validate(scope.ccParameter01, scope.ccParameter02, scope.ngModel);
                    }
                }
                scope.$emit('ccResetServerClientError', attrs.name);
                scope.$broadcast("resetServerError");
            });

            scope.$watch('ccParameter01', function(newValue, oldValue) {
                loadData();
            });

            scope.$watch('ccParameter02', function(newValue, oldValue) {
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