/**
 * @ngdoc directive
 * @name chasecore.cc-jigyobu-combobox
 * @restrict E
 * @function
 *
 * @description
 * (部品生成時)事業部マスタから『事業部コード＋事業部名称(or略称)』を取得・設定する
 *
 * @example
    <doc:example>
      <doc:source>
         <cc-jigyobu-combobox 
            cc-label="事業部"
            cc-partition="3"
            id="jigyobuCd"
            name="jigyobuCd"
            ng-model="cond.jigyobuCd"
            cc-required=false
            cc-delexist=false
            cc-shortname=true
            cc-parameter01="cond.kaisyaCd"
            cc-parameter02="cond.hakkoDay" >
        </cc-jigyobu-combobox>
      </doc:source>
      <doc:scenario>
      </doc:scenario>
    </doc:example>
 */

diretiveApp.directive('ccJigyobuCombobox', function(CSS_CLASS, $http, $compile, $timeout, UserInfo, $rootScope, ClearComboboxOptions, AddComboboxOptions) {
    var unyoDate = UserInfo.unyoDate;

    function getListJigyobus(kaisyaCd, hakkoDay, selectElement, shortName, delexist, element, attrs, scope) {
        //未設定の場合は運用日付を取得して代用する。
        if (isEmpty(hakkoDay)) {
            getDataListJigyobus(kaisyaCd, unyoDate, selectElement, shortName, delexist, element, attrs, scope);
        } else {
            getDataListJigyobus(kaisyaCd, hakkoDay, selectElement, shortName, delexist, element, attrs, scope);
        }
    }
    // call server get data.
    function getDataListJigyobus(kaisyaCd, hakkoDay, selectElement, shortName, delexist, element, attrs, scope) {
        $http({
            method: 'GET',
            url: '/core/codemaster/m001jgym/' + kaisyaCd + '?hakkoDay=' + hakkoDay
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
            ccParameter02: '=',
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
                // パラメータから[allowClear]の値を設定する。
                uiSelect2 = 'ui-select2 = "{ allowClear: false}"';
            } else if (angular.isDefined(attrs.ccRequired) && attrs.ccRequired == 'false') {
                // パラメータから[allowClear]の値を設定する。
                uiSelect2 = 'ui-select2 = "{ allowClear: true}"';
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

            htmlText += '<select ' + uiSelect2 + ' class="' + selectClass + '" id="' + attrs.id + '" name="' + attrs.name + '" ng-model="ngModel" ' + readOnlyRequired;


            if (!isEmpty(attrs.ccWidth)) {
                htmlText += ' ng-style="{width: ccWidth}"';
            }
            if (attrs.ccReadonly == 'true') {
                htmlText += ' >';
            } else {
                htmlText += ' cc-blur-validation-combobox error-message="{{error}}">';
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

            //open event
            selectElement.on("select2-open", function() {
                scope.$parent.$broadcast("select2-open", attrs.id);
            });

            scope.$on('$destroy', function() {
                selectElement.unbind();
                selectElement.off();
            })

            // validate cc-select for the first load
            var validate = function(kaisyaCd, hakkoDay, modelValue) {
                if (isEmpty(kaisyaCd)) {
                    selectCtrl.$setValidity('ccSelect', false);
                    scope.$broadcast(element.id + 'Error');
                    return undefined;
                }
                var url = '/core/codemaster/m001jgym/' + kaisyaCd + '?hakkoDay=' + hakkoDay;
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
                    if (isEmpty(scope.ccParameter02)) {
                        validate(scope.ccParameter01, unyoDate, scope.ngModel);
                    } else {
                        validate(scope.ccParameter01, scope.ccParameter02, scope.ngModel);
                    }
                }
                scope.$emit('ccResetServerClientError', attrs.name);
                scope.$broadcast("resetServerError");
            });

            // load data for combobox
            var loadData = function() {
                // 1) パラメータチェック
                if (isEmpty(scope.ccParameter01)) {
                    ClearComboboxOptions(scope, attrs);
                }
                else {
                    getListJigyobus(scope.ccParameter01,
                        scope.ccParameter02, selectElement,
                        attrs.ccShortname, attrs.ccDelexist, element, attrs, scope);
                }
            }

            // パラメータ(cc-parameterxx)に指定されているバインド変数が変更された場合は当該部品のバインド変数を消去する。
            scope.$watchCollection('[ccParameter01, ccParameter02]', function(value) {
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