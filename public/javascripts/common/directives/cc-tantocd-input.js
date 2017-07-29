/**
 * @ngdoc directive
 * @name chasecore.cc-tantocd-input
 * @restrict E
 * @function
 *
 * @description (コード入力時)担当者マスタから『担当者名称』を取得・表示する
 *
 * @example
 *      <doc:example>
 *          <doc:source>
 *              <cc-tantocd-input cc-label="担当者" cc-partition="3" id="tantoCd" name="tantoCd" ng-model="result.tantoCd" cc-required="true"
 *                  cc-notexist="false" cc-delexist="true" cc-parameter01="result.hakkoDay"
 *                  id2="tantoNm" name2="tantoNm" ng-model2="result.tantoNm"></cc-tantocd-input>
 *          </doc:source>
 *          <doc:scenario>
 *          </doc:scenario>
 *      </doc:example>
 */

diretiveApp.directive('ccTantocdInput', function(CSS_CLASS, $http, $timeout, UserInfo) {
    var unyoDate = UserInfo.unyoDate;
    return {
        restrict: 'E',
        require: 'ngModel',
        scope: {
            ngModel: '=',
            ngModel2: '=',
            ccParameter01: '=',
            ccReadonly: '=',
            ccError: '=',
            ccFocus: '=',
            ccWidth1: '=',
            ccWidth2: '='
        },
        template: function(element, attrs) {

            var inputClass = '';
            var inputClass2 = CSS_CLASS.BLANK + CSS_CLASS.disable;
            var labelClass = '';

            // either readOnly or required
            var readOnly_required = '';
            if (angular.isDefined(attrs.ccFocus)) {
                readOnly_required += ' cc-focus="ccFocus"';
            }
            if (angular.isDefined(attrs.ccReadonly)) {
                readOnly_required += ' ng-disabled="ccReadonly" ng-class="{\'' + CSS_CLASS.disable + '\': ccReadonly, \'\': !ccReadonly}" ';
            }

            if (angular.isDefined(attrs.ccReadonly) && attrs.ccReadonly == 'true') {
                readOnly_required += ' readonly ';
                inputClass += CSS_CLASS.BLANK + CSS_CLASS.disable;
            }

            if (angular.isDefined(attrs.ccRequired) && attrs.ccRequired == 'true') {
                readOnly_required += ' required ';
            }

            // style for label
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

            // html text
            var htmlText = '';

            // div no1
            if (angular.isDefined(attrs.ccPartition) && attrs.ccPartition == '') {
                htmlText += '<div class="' + CSS_CLASS.label_divide_zero + CSS_CLASS.BLANK + CSS_CLASS.label + '" >' + attrs.ccLabel + '</div>';
            } else {
                htmlText += '<div class="' + labelClass + '">' + attrs.ccLabel;
                if (angular.isDefined(attrs.ccRequired) && attrs.ccRequired == 'true') {
                    htmlText += '<span class="' + CSS_CLASS.txt_required + '"> *</span>';
                }
            }
            htmlText += '</div>';

            // input no1
            htmlText += '<input type="text" cc-type="alphanum" id="' + attrs.id + '" name="' + attrs.name + '" ng-model="ngModel" onKeyDown="return setNextFocus(this);" ' + readOnly_required + (angular.isDefined(attrs.ccRequired) ? 'ui-event="{ blur : \'blurCallback(ngModel)\' }"' : '') + ' maxlength="9" ';

            if (!isEmpty(attrs.ccWidth1)) {
                htmlText += ' ng-style="{width: ccWidth1}"';
            }
            if (attrs.ccReadonly == 'true') {
                htmlText += ' >';
            } else {
                htmlText += ' ng-minlength="1" cc-blur-validation error-message="{{error}}" >';
            }

            // input no2
            if (angular.isDefined(attrs.ngModel2)) {
                htmlText += ' &nbsp ';
                htmlText += '<input type="text" cc-type="zenkaku"';
                if (!isEmpty(attrs.ccWidth2)) {
                    htmlText += ' ng-style="{width: ccWidth2}"';
                }
                htmlText += ' id="' + attrs.id2 + '" name="' + attrs.name2 + '" class="' + inputClass2 + '" ng-model="ngModel2" ng-disabled="true" maxlength="20">';
            }

            return htmlText;
        },
        link: function(scope, element, attrs) {
            var inputElement = element.find('input').eq(0);
            var inputCtrl = inputElement.controller('ngModel');

            // validate input number => common done

            // 取引先名称取得処理
            // メーカー名称取得処理
            var record = null;
            var checkData = false;
            var input = element.find('input');

            //SetFocus時の書式
            input.bind('focus', function(evt) {
                setLastCaret(element.find('input')[0], angular);
            }).bind('blur', function(evt) {
                scope.$emit(this.id + "Blur");
            });

            function validation(value) {
                // init data
                inputCtrl.$setValidity('ccExist', true);
                record = null;
                scope.ngModel2 = undefined;

                // 1)パラメータチェック
                // 未設定の場合は運用日付を取得して代用する。
                // 未設定の場合は処理を終了する。(エラーを設定しない)
                if (isEmpty(value) || attrs.ngModel2 == undefined) {
                    return value;
                }

                // 2)取引先名称取得
                var requestWithHakkoDay = function(hakkoDay) {
                    $http({
                        method: 'GET',
                        url: '/core/codename/m016tanm/' + value + '?hakkoDay=' + hakkoDay
                    }).success(
                        function(data) {
                            record = data;
                            // 3)結果チェック
                            // 無し
                            if (record.length == 0) {
                                if (attrs.ccNotexist == 'true') {
                                    checkData = true;
                                } else if (attrs.ccNotexist == 'false') {
                                    checkData = false;
                                }
                                // 稼動区分[9]であるレコード
                            } else if (record[0].kubun == '9') {
                                if (attrs.ccDelexist == 'true') {
                                    checkData = true;
                                } else if (attrs.ccDelexist == 'false') {
                                    checkData = false;
                                }
                            } else {
                                // 稼動区分[9]でないレコード
                                checkData = true;
                            }

                            if (!checkData) {
                                inputCtrl.$setValidity('ccExist', false);
                            } else {
                                inputCtrl.$setValidity('ccExist', true);
                            }
                        });
                };

                var hakkoDay = scope.ccParameter01;
                if (!isEmpty(hakkoDay)) {
                    requestWithHakkoDay(hakkoDay);
                } else {
                    requestWithHakkoDay(unyoDate);
                }

                return value;
            }

            // listener blur event
            scope.blurCallback = function(value) {
                if (isEmpty(value)) {
                    return;
                }

                if (angular.isDefined(attrs.id2) && angular.isDefined(attrs.name2) && angular.isDefined(attrs.ngModel2)) {
                    // 4)メーカー名称設定
                    // 上記3)が『エラーである』 (error)
                    // 上記3)が『エラーで無い』 and 取得レコード = 無し and cc-notexist =
                    // true (not error)
                    if (!checkData || checkData && record.length == 0 && attrs.ccNotexist == 'true') {
                        scope.ngModel2 = undefined;
                    } else if (checkData && record.length != 0) {
                        scope.ngModel2 = record[0].name;
                    }
                }
            }

            if (angular.isDefined(attrs.id2) && angular.isDefined(attrs.name2) && angular.isDefined(attrs.ngModel2)) {
                inputCtrl.$parsers.push(validation);
                inputCtrl.$formatters.push(validation);
                var input = element.find('input')[0];
                setLastCaret(input, angular);
            }

            // パラメータ(cc-parameterxx)に指定されているバインド変数が変更された場合は当該部品のバインド変数を消去する。
            scope.$watchCollection("[ccParameter01]", function(value) {
                if (!inputCtrl.$pristine) {
                    scope.ngModel = undefined;
                    scope.ngModel2 = undefined;
                }
            });

            // Show server error message
            scope.error = null;
            scope.$on("ccError", function(event, value) {
                scope.error = null;
                inputCtrl.$setValidity('ccClientError', true);
                if (!isEmpty(value)) {
                    angular.forEach(value, function(error, key) {
                        if (angular.equals(error.name, attrs.name) &&
                            angular.equals(error.level, 'E')) {
                            scope.error = error;
                            if (error.clientErrorFlag == true) {
                                inputCtrl.$setValidity('ccClientError', false);
                            } else {
                                inputCtrl.$setValidity('ccClientError', true);
                            }
                        }
                    });
                }
            });

            inputCtrl.$parsers.push(function(viewValue) {
                scope.error = null;
                scope.$emit('ccResetServerClientError', attrs.name);
                return viewValue;
            });
        }
    }
});