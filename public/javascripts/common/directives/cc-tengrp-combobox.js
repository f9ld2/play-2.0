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
         <cc-tengrp-combobox 
            cc-label="aaa"
            cc-partition="3"
            id="tengrpCd"
            name="tengrpCd"
            ng-model="cond.tengrpCd"
            cc-required=false
            cc-delexist=false
            cc-key1="MA"
            cc-parameter01="result.jigyobuCd"
            cc-parameter02="result.bmnCd" 
            cc-parameter03="result.hakkoDay" >
        </cc-tengrp-combobox>
      </doc:source>
      <doc:scenario>
      </doc:scenario>
    </doc:example>
 */
diretiveApp.directive('ccTengrpCombobox', function(CSS_CLASS, $http, $compile, $timeout, UserInfo, $rootScope, ClearComboboxOptions) {
    var unyoDate = UserInfo.unyoDate;

    function getListTengrp(key, jigyobuCd, bmnCd, hakkoDay, selectElement, element, attrs, scope) {
        //未設定の場合は運用日付を取得して代用する。
        if (isEmpty(hakkoDay)) {
            getDataListTengrp(key, jigyobuCd, bmnCd, unyoDate, selectElement, element, attrs, scope);
        } else {
            getDataListTengrp(key, jigyobuCd, bmnCd, hakkoDay, selectElement, element, attrs, scope);
        }
    }
    // call server get data.
    function getDataListTengrp(key, jigyobuCd, bmnCd, hakkoDay, selectElement, element, attrs, scope) {
        if (isEmpty(jigyobuCd)) {
            jigyobuCd = '';
        }

        if (isEmpty(bmnCd)) {
            bmnCd = '';
        }

        if (isEmpty(jigyobuCd) && isEmpty(bmnCd)) {
            ClearComboboxOptions(scope, attrs);
            return;
        }

        $http({
            method: 'GET',
            url: '/core/codename/m012tngm/' + key + '?hakkoDay=' + hakkoDay + '&jigyobuCd=' + jigyobuCd + '&bmnCd=' + bmnCd
        }).success(function(data) {
            ClearComboboxOptions(scope, attrs);
            angular.forEach(data, function(value, key) {
                if (!(value.kubun == '9' && attrs.ccDelexist == 'false')) {
                    scope.optionArr.push({
                        value: value.code.substr(-4),
                        text: value.code.substr(-4) + ':' + value.name.trim()
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
            ccParameter02: '=',
            ccParameter03: '=',
            ccReadonly: '=',
            ccComboboxFocus: '=',
            ccError: '=',
            ccWidth: '=',
            ccInit: '='
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
            selectClass += CSS_CLASS.char_15 + CSS_CLASS.BLANK + CSS_CLASS.left;

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
            var firstLoad = true;
            var selectCtrl = angular.element(element.find('select')[0]).controller('ngModel');
            var selectElement = angular.element(element.find('select')[0]);

            //Blur event
            selectElement.on("select2-blur", function() {
                scope.$emit(this.id + "Blur");
            });

            // validate cc-select for the first load
            var validate = function(key, jigyobuCd, bmnCd, hakkoDay, modelValue) {
                if (isEmpty(jigyobuCd)) {
                    jigyobuCd = '';
                }

                if (isEmpty(bmnCd)) {
                    bmnCd = '';
                }

                var url = '/core/codename/m012tngm/' + key + '?hakkoDay=' + hakkoDay + '&jigyobuCd=' + jigyobuCd + '&bmnCd=' + bmnCd;
                return $http.get(
                    url, {}).then(function(response) {
                    flg = false;
                    angular.forEach(response.data, function(value, key) {
                        if (modelValue == value.code.substr(-4)) {
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
                    if (isEmpty(scope.ccParameter03)) {
                        validate(attrs.ccKey1, scope.ccParameter01, scope.ccParameter02, unyoDate, scope.ngModel);
                    } else {
                        validate(attrs.ccKey1, scope.ccParameter01, scope.ccParameter02, scope.ccParameter03, scope.ngModel);
                    }
                }
                scope.$emit('ccResetServerClientError', attrs.name);
                scope.$broadcast("resetServerError");
            });

            //パラメータ(cc-parameterxx)に指定されているバインド変数が変更された場合は当該部品のバインド変数を消去する。
            var loadData = function() {
                getListTengrp(attrs.ccKey1, scope.ccParameter01,
                    scope.ccParameter02, scope.ccParameter03,
                    selectElement, element, attrs, scope);
            }

            scope.$watch('ccParameter01', function(newValue, oldValue) {
                loadData();
            });
            scope.$watch('ccParameter02', function(newValue, oldValue) {
                loadData();
            });
            scope.$watch('ccParameter03', function(newValue, oldValue) {
                loadData();
            });

            scope.$watch('', function(newValue, oldValue) {
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