/**
 * @ngdoc directive
 * @name chasecore.cc-combobox
 * @restrict E
 * @function
 *
 * @description
 * (部品生成時)○○テーブルから『○○コード＋○○名称』を取得・設定する(業務ＡＰ)
 *
 * @example
    <doc:example>
      <doc:source>
        <cc-combobox 
            cc-label="汎用" 
            cc-partition="3" 
            id="stdCd" 
            name="stdCd" 
            ng-model="result.stdCd" 
            cc-required=true 
            ng-maxlength="2" 
            cc-shortname=false 
            cc-datalist="stdCdList" >
        </cc-combobox>
      </doc:source>
      <doc:scenario>
      </doc:scenario>
    </doc:example>
 */

diretiveApp.directive('ccCombobox', function(CSS_CLASS, $http, $compile, ClearComboboxOptions, AddKbnComboboxOptions) {
    return {
        restrict: 'E',
        require: 'ngModel',
        scope: {
            ngModel: '=',
            ccDatalist: '=',
            ccReadonly: '=',
            ccWidth: '='
        },
        template: function(element, attrs) {
            // ui-select2
            var uiSelect2 = '';

            // either readOnly or required
            var readOnlyRequired = '';
            if (angular.isDefined(attrs.ccReadonly)) {
                readOnlyRequired += ' ng-disabled="ccReadonly" ng-class="{\'' + CSS_CLASS.disable + '\': ccReadonly, \'\': !ccReadonly}"';
                uiSelect2 = 'ui-select2 = "{ allowClear: false}"';
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
            if (countByte <= 15 && countByte >= 12) {
                selectClass += CSS_CLASS.char_15 + CSS_CLASS.BLANK + CSS_CLASS.left;
            } else if (countByte <= 20) {
                selectClass += CSS_CLASS.char_20 + CSS_CLASS.BLANK + CSS_CLASS.left;
            } else if (countByte <= 25) {
                selectClass += CSS_CLASS.char_25 + CSS_CLASS.BLANK + CSS_CLASS.left;
            } else if (countByte <= 30) {
                selectClass += CSS_CLASS.char_30 + CSS_CLASS.BLANK + CSS_CLASS.left;
            } else if (countByte <= 35) {
                selectClass += CSS_CLASS.char_35 + CSS_CLASS.BLANK + CSS_CLASS.left;
            } else if (countByte <= 40) {
                selectClass += CSS_CLASS.char_40 + CSS_CLASS.BLANK + CSS_CLASS.left;
            } else if (countByte <= 45) {
                selectClass += CSS_CLASS.char_45 + CSS_CLASS.BLANK + CSS_CLASS.left;
            } else if (countByte <= 50) {
                selectClass += CSS_CLASS.char_50 + CSS_CLASS.BLANK + CSS_CLASS.left;
            } else if (countByte <= 55) {
                selectClass += CSS_CLASS.char_55 + CSS_CLASS.BLANK + CSS_CLASS.left;
            } else if (countByte >= 56) {
                selectClass += CSS_CLASS.char_60 + CSS_CLASS.BLANK + CSS_CLASS.left;
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
                htmlText += ' cc-blur-validation-combobox >';
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
            // 1)   cc-datalist(上記の例ではstdCdList)からComboBoxのDataList(本Ver.)を生成
            var dataList = scope.ccDatalist;

            // コンボボックスのリストを生成する時の注意事項
            // view value change only if user select new value
            selectCtrl.$parsers.push(function(value) {
                selectCtrl.$setValidity('ccSelect', true);
                return value;
            });

            scope.$watch('ngModel', function(newValue, oldValue) {
                if (!isEmpty(scope.ngModel)) {
                    flg = false;
                    angular.forEach(scope.ccDatalist, function(value, key) {
                        if (scope.ngModel == value.code) {
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
                }
                scope.$emit('ccResetServerClientError', attrs.name);
                scope.$broadcast("resetServerError");
            });

            ClearComboboxOptions(scope, attrs);
            AddKbnComboboxOptions(scope, dataList);
        }
    }
});