/**
 * @ngdoc directive
 * @name chasecore.cc-ten-combobox
 * @restrict E
 * @function
 *
 * @description
 * (部品生成時)店舗マスタから『店舗コード＋店舗名称(or略称)』を取得・設定する
 *
 * @example
    <doc:example>
      <doc:source>
         <cc-ten-combobox 
            cc-label="店舗" cc-partition="3" id="tenCd" name="tenCd"
            ng-model="result.tenCd" cc-required=false cc-delexist=false
            cc-shortname=true cc-parameter01="result.kaisyaCd" 
            cc-parameter02="result.jigyobuCd" cc-parameter03="result.hakkoDay" >
        </cc-ten-combobox>
      </doc:source>
      <doc:scenario>
      </doc:scenario>
    </doc:example>
 */
diretiveApp.directive('ccTenCombobox', function(CSS_CLASS, $http, $compile, $timeout, UserInfo, $rootScope, ClearComboboxOptions, AddComboboxOptions) {
    var unyoDate = UserInfo.unyoDate;

    function getTenDataList(kaisyaCd, jigyobuCd, hakkoDay, selectElement, attrs, element, scope) {
        //未設定の場合は運用日付を取得して代用する。
        if (isEmpty(hakkoDay)) {
            getDataList(kaisyaCd, jigyobuCd, unyoDate, selectElement, attrs, element, scope);
        } else {
            getDataList(kaisyaCd, jigyobuCd, hakkoDay, selectElement, attrs, element, scope);
        }
    };

    function getDataList(kaisyaCd, jigyobuCd, hakkoDay, selectElement, attrs, element, scope) {
        var url = '/core/codename/m006tenm2/' + kaisyaCd + '/' + jigyobuCd + '?hakkoDay=' + hakkoDay;

        $http({
            method: 'GET',
            url: url
        }).success(function(data) {
            ClearComboboxOptions(scope, attrs);
            AddComboboxOptions(scope, data, attrs.ccDelexist, attrs.ccShortname);
        }).error(function(data) {});
    };

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
                uiSelect2 = 'ui-select2 = "{allowClear: false}"';
            } else if (angular.isDefined(attrs.ccRequired) && attrs.ccRequired == 'false') {
                uiSelect2 = 'ui-select2 = "{allowClear: true}"';
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
                htmlText += '<div class="' + labelClass + '" >' + attrs.ccLabel;
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
            var selectElement = angular.element(element.find('select')[0]);
            var selectCtrl = angular.element(element.find('select')[0]).controller('ngModel');

            //Blur event
            selectElement.on("select2-blur", function() {
                scope.$emit(this.id + "Blur");
            });

            // validate cc-select for the first load
            var validate = function(kaisyaCd, jigyobuCd, hakkoDay, modelValue) {
                if (isEmpty(kaisyaCd) || isEmpty(jigyobuCd)) {
                    selectCtrl.$setValidity('ccSelect', false);
                    scope.$broadcast(element.id + 'Error');
                    scope.ngModel = undefined;
                    return;
                }

                var url = '/core/codename/m006tenm2/' + kaisyaCd + '/' + jigyobuCd + '?hakkoDay=' + hakkoDay;
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
                    if (isEmpty(scope.ccParameter03)) {
                        //validate here because http is unsynchronized;
                        validate(scope.ccParameter01, scope.ccParameter02, unyoDate, scope.ngModel);
                    } else {
                        validate(scope.ccParameter01, scope.ccParameter02, scope.ccParameter03, scope.ngModel);
                    }
                }
                scope.$emit('ccResetServerClientError', attrs.name);
                scope.$broadcast("resetServerError");
            });

            var loadData = function() {
                // init combobox
                //1)パラメータチェック
                var param1 = scope.ccParameter01; // kaisyaCd
                var param2 = scope.ccParameter02; // jigyobuCd
                var param3 = scope.ccParameter03; // hakkoDay
                if (isEmpty(param1) || isEmpty(param2)) {
                    ClearComboboxOptions(scope, attrs);
                } else {
                    getTenDataList(param1, param2, param3, selectElement, attrs, element, scope);
                }
            }

            // 変更を監視するバインド変数
            //※ cc-parameter01が指定されている場合
            //※ cc-parameter02が指定されている場合
            //※ cc-parameter03が指定されている場合
            scope.$watchCollection('[ccParameter01, ccParameter02, ccParameter03]', function(value) {
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