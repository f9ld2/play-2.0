/**
 * @ngdoc directive
 * @name chasecore.cc-mei-combobox
 * @restrict E
 * @function
 *
 * @description
 * (部品生成時)名称マスタから『コード＋名称』を取得・設定する
 *
 * @example
    <doc:example>
      <doc:source>
         <cc-mei-combobox
            cc-label="エリア"
            cc-partition="3"
            id="areaCd"
            name="areaCd"
            ng-model="cond.areaCd"
            cc-required=true
            ng-maxlength="1"
            cc-delexist=true
            cc-key1="MA"
            cc-key2="TEN_KBN"
            cc-shortname=false
            cc-parameter01="cond.hakkoDay">
        </cc-mei-combobox>
      </doc:source>
      <doc:scenario>
      </doc:scenario>
    </doc:example>
 */

diretiveApp.directive('ccMeiCombobox', function(CSS_CLASS, $http, $compile, $timeout, UserInfo, $rootScope, ClearComboboxOptions) {
    var unyoDate = UserInfo.unyoDate;

    function getListMei(cdKbn, cd, hakkoDay, selectElement, attrs, shortName, delexist, element, scope) {
        //未設定の場合は運用日付を取得して代用する。
        if (isEmpty(hakkoDay)) {
            getDataListMei(cdKbn, cd, unyoDate, selectElement, attrs, shortName, delexist, element, scope);
        } else {
            getDataListMei(cdKbn, cd, hakkoDay, selectElement, attrs, shortName, delexist, element, scope);
        }
    }
    // call server get data.
    function getDataListMei(cdKbn, cd, hakkoDay, selectElement, attrs, shortName, delexist, element, scope) {
        $http({
            method: 'GET',
            url: '/core/codemaster/m017meim/' + cdKbn + '/' + cd + '?hakkoDay=' + hakkoDay
        }).success(function(data) {
            // 3) 『DataList(仮Ver.)』からComboBoxのDataList(本Ver.)を生成
            ClearComboboxOptions(scope, attrs);

            angular.forEach(data, function(value, key) {
                if (!(value.kubun == '9' && delexist == 'false')) {
                    var keyCode = value.code.substr(cd.length - value.code.length);
                    if (keyCode.length == attrs.ngMaxlength) {
                        scope.optionArr.push({
                            value: keyCode,
                            text: keyCode + ':' + value.name
                        });
                    }
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
            var countByte = 0;
            var selectClass = '';
            if (attrs.ccShortname == 'true') {
                countByte += 10;
            } else if (attrs.ccShortname == 'false') {
                countByte += 40;
            }
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
            // コンボボックスのリストを生成する時の注意事項

            //Blur event
            selectElement.on("select2-blur", function() {
                scope.$emit(this.id + "Blur");
            });

            // validate cc-select for the first load
            var validate = function(cdKbn, cd, hakkoDay, modelValue) {
                if (isEmpty(cdKbn) || isEmpty(cd)) {
                    selectCtrl.$setValidity('ccSelect', false);
                    scope.$broadcast(element.id + 'Error');
                    scope.ngModel = undefined;
                    return;
                }
                var url = '/core/codemaster/m017meim/' + cdKbn + '/' + cd + '?hakkoDay=' + hakkoDay;
                return $http.get(
                    url, {}).then(function(response) {
                    flg = false;
                    angular.forEach(response.data, function(value, key) {
                        var keyCode = value.code.substr(cd.length - value.code.length).trim();
                        if (modelValue == keyCode) {
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
                        validate(element.attr('cc-key1'), element.attr('cc-key2'), unyoDate, scope.ngModel);
                    } else {
                        validate(element.attr('cc-key1'), element.attr('cc-key2'), scope.ccParameter01, scope.ngModel);
                    }
                }
                scope.$emit('ccResetServerClientError', attrs.name);
                scope.$broadcast("resetServerError");
            });

            // load data for combobox
            var loadData = function(asd) {
                if (isEmpty(element.attr('cc-key1')) || isEmpty(element.attr('cc-key2'))) {
                    return;
                }
                getListMei(element.attr('cc-key1'), element.attr('cc-key2'), scope.ccParameter01, selectElement, attrs, attrs.ccShortname, attrs.ccDelexist, element, scope);
            }

            // パラメータ(cc-parameterxx)に指定されているバインド変数が変更された場合は当該部品のバインド変数を消去する。
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