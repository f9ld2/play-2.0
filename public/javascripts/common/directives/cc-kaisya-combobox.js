/**
 * @ngdoc directive
 * @name chasecore.cc-kaisya-combobox
 * @restrict E
 * @function
 *
 * @description
 * (部品生成時)会社マスタから『会社コード＋会社名称(or略称)』を取得・設定する
 *
 * @example
    <doc:example>
      <doc:source>
         <cc-kaisya-combobox 
            cc-label="aaa"
            cc-partition="3"
            id="kaisyaCd"
            name="kaisyaCd"
            ng-model="cond.kaisyaCd"
            cc-required=false
            cc-delexist=false
            cc-shortname=true
            cc-parameter01="cond.hakkoDay" >
        </cc-kaisya-combobox>
      </doc:source>
      <doc:scenario>
      </doc:scenario>
    </doc:example>
 */
diretiveApp.directive('ccKaisyaCombobox', function(CSS_CLASS, $http, $compile, $timeout, UserInfo, $rootScope, ClearComboboxOptions, AddComboboxOptions) {
    var unyoDate = UserInfo.unyoDate;

    function getListKaisya(hakkoDay, selectElement, shortName, delexist, element, attrs, scope) {
        //未設定の場合は運用日付を取得して代用する。
        if (isEmpty(hakkoDay)) {
            getDataListKaisya(unyoDate, selectElement, shortName, delexist, element, attrs, scope);
        } else {
            getDataListKaisya(hakkoDay, selectElement, shortName, delexist, element, attrs, scope);
        }
    }
    // call server get data.
    function getDataListKaisya(hakkoDay, selectElement, shortName, delexist, element, attrs, scope) {
        $http({
            method: 'GET',
            url: '/core/codemaster/m000kaim' + '?hakkoDay=' + hakkoDay
        }).success(function(data) {
            ClearComboboxOptions(scope, attrs);
            AddComboboxOptions(scope, data, delexist, shortName);
        }).error(function(data) {});
    }

    return {
        restrict: 'E',
        require: 'ngModel',
        scope: {
            ngModel: '=',
            ccParameter01: '=',
            ccReadonly: '=',
            ccComboboxFocus: '=',
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

            // 2) コンボボックス：パラメータの内容に従う。
            var selectClass = '';
            if (attrs.ccShortname == 'true') {
                selectClass += CSS_CLASS.char_10 + CSS_CLASS.BLANK + CSS_CLASS.left;
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

            htmlText += '<select ' + uiSelect2 + ' class="' + selectClass + '" id="' + attrs.id + '" name="' + attrs.name + '" error-message="{{error}}" ng-model="ngModel" ' + readOnlyRequired;
            htmlText += ' cc-blur-validation-combobox ';
            if (!isEmpty(attrs.ccWidth)) {
                htmlText += ' ng-style="{width: ccWidth}" >';
            } else {
                htmlText += ' >';
            }

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

            // validate cc-select for the first load
            var validate = function(hakkoDay, modelValue) {
                var url = '/core/codemaster/m000kaim' + '?hakkoDay=' + hakkoDay;
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


            scope.$watch('ngModel', function(newValue, oldValue) {
                if (!isEmpty(scope.ngModel)) {
                    if (isEmpty(scope.ccParameter01)) {
                        validate(unyoDate, scope.ngModel);
                    } else {
                        validate(scope.ccParameter01, scope.ngModel);
                    }
                }
                scope.$emit('ccResetServerClientError', attrs.name);
                scope.$broadcast("resetServerError");
            });

            // load data for combobox
            var loadData = function() {
                // init combobox
                getListKaisya(scope.ccParameter01,
                    selectElement, attrs.ccShortname,
                    attrs.ccDelexist, element, attrs, scope);
            }

            //パラメータ(cc-parameterxx)に指定されているバインド変数が変更された場合は当該部品のバインド変数を消去する。
            scope.$watch('ccParameter01', function(value) {
                loadData();
            });

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