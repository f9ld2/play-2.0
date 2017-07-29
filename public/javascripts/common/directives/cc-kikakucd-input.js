/**
 * @ngdoc directive
 * @name chasecore.cc-kikakucd-input
 * @restrict E
 * @function
 *
 * @description
 * (コード入力時)企画マスタから『企画名称』を取得・表示する
 *
 * @example
    <doc:example>
      <doc:source>
         <cc-kikakucd-input cc-label="企画" cc-partition="3" 
         id="kikakuCd" name="kikakuCd" ng-model="cond.kikakuCd" 
         cc-required=true cc-notexist=false cc-delexist=true 
         cc-parameter01="cond.kaisyaCd" cc-parameter02="cond.jigyobuCd" cc-parameter03="cond.nendo" cc-parameter04="result.bmnCd"
         id2="kikakuNm" name2="kikakuNm" ng-model2="kikakuNm"></cc-kikakucd-input>
      </doc:source>
      <doc:scenario>
      </doc:scenario>
    </doc:example>
 */
diretiveApp.directive('ccKikakucdInput', function(CSS_CLASS, $http, $timeout) {
    return {
        restrict: 'E',
        require: 'ngModel',
        scope: {
            ngModel: '=',
            ngModel2: '=',
            ccParameter01: '=',
            ccParameter02: '=',
            ccParameter03: '=',
            ccParameter04: '=',
            ccReadonly: '=',
            ccError: '=',
            ccWidth: '=',
            ccWidth2: '=',
            ccFocus: '=',
            ccInit: '='
        },
        template: function(element, attrs) {
            // either readOnly or required
            var readOnlyRequired = '';
            var inputClass = '';
            var inputClass2 = CSS_CLASS.BLANK + CSS_CLASS.disable;
            if (angular.isDefined(attrs.ccFocus)) {
                readOnlyRequired += ' cc-focus="ccFocus"';
            }
            if (angular.isDefined(attrs.ccReadonly)) {
                readOnlyRequired += ' ng-disabled="ccReadonly" ng-class="{\'' + CSS_CLASS.disable + '\': ccReadonly, \'\': !ccReadonly}" ';
            }

            if (angular.isDefined(attrs.ccReadonly) && attrs.ccReadonly == 'true') {
                readOnlyRequired += ' readonly ';
                inputClass += CSS_CLASS.BLANK + CSS_CLASS.disable;
            }
            if (angular.isDefined(attrs.ccRequired) && attrs.ccRequired == 'true') {
                readOnlyRequired += ' required ';
            }

            // style for label
            var labelClass = '';
            if (angular.isDefined(attrs.ccPartition) && attrs.ccPartition == '') {
                labelClass += CSS_CLASS.label_divide_zero;
            } else {
                if (angular.isDefined(attrs.ccPartition) && attrs.ccRequired == 'true') {
                    labelClass += CSS_CLASS.cs_label_large_required;
                } else {
                    labelClass += CSS_CLASS.cs_label_large;
                }
            }
            labelClass += CSS_CLASS.BLANK + CSS_CLASS.label;

            // html text
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

            htmlText += '<input type="text" cc-type="code" id="' + attrs.id + '" name="' + attrs.name + '" class="' + inputClass + '" ng-model="ngModel" ' + readOnlyRequired + '  maxlength="9" onKeyDown="return setNextFocus(this);" ';
            if (!isEmpty(attrs.ccWidth)) {
                htmlText += ' ng-style="{width: ccWidth}"';
            }
            if (attrs.ccReadonly == 'true') {
                htmlText += ' />';
            } else {
                htmlText += ' ng-minlength="9" cc-blur-validation error-message="{{error}}"';
                if (angular.isUndefined(attrs.id2) &&
                    angular.isUndefined(attrs.name2) &&
                    angular.isUndefined(attrs.ngModel2)) {
                    htmlText += ' />';
                    return htmlText;
                }
                htmlText += ' ui-event="{ blur : \'blurCallback(ngModel)\' }"/>';
            }
            htmlText += ' &nbsp';
            htmlText += '<input type="text" cc-type="zenkaku"';
            if (!isEmpty(attrs.ccWidth2)) {
                htmlText += ' ng-style="{width: ccWidth2}"';
            }
            htmlText += ' id="' + attrs.id2 + '" name="' + attrs.name2 + '" class="' + inputClass2 + '" ng-model="ngModel2" ng-disabled="true" maxlength="15" />';
            return htmlText;
        },

        link: function(scope, element, attrs, ctrl) {
            var inputCtrl = angular.element(element.find('input')[0]).controller('ngModel');
            // 企画名称取得処理
            var record = null;
            var checkData = false;
            var bmnCd = 'empty';

            var input = element.find('input');

            input.bind('click', function() {
                scope.$emit(attrs.id + "Click");
            }).bind('blur', function() {
                scope.$emit(attrs.id + "Blur");
            });

            //SetFocus時の書式
            input.bind('focus', function(evt) {
                setLastCaret(element.find('input')[0], angular);
            });

            function validation(value) {
                // init data
                inputCtrl.$setValidity('ccExist', true);
                record = null;
                checkData = false;
                if (angular.isUndefined(attrs.ccInit) || !scope.ccInit) {
                    scope.ngModel2 = undefined;
                } else {
                    // scope.ccInit = false;
                }
                // scope.ngModel2 = undefined;
                // 1)パラメータチェック
                if (!isEmpty(scope.ccParameter04)) {
                    bmnCd = scope.ccParameter04;
                }
                // 未設定の場合は処理を終了する。(エラーを設定しない)
                if (isEmpty(value) || isEmpty(scope.ccParameter01) ||
                    isEmpty(scope.ccParameter02) || isEmpty(scope.ccParameter03)) {
                    return value;
                }

                // 2)企画名称取得
                $http({
                    method: 'GET',
                    url: '/core/codename/t000kkkm/' + value + '/' + scope.ccParameter01 + '/' + scope.ccParameter02 + '/' + scope.ccParameter03 + '/' + bmnCd
                }).success(function(data) {
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
                return value;
            }

            if (angular.isDefined(attrs.id2) &&
                angular.isDefined(attrs.name2) &&
                angular.isDefined(attrs.ngModel2)) {
                inputCtrl.$parsers.push(validation);
                inputCtrl.$formatters.push(validation);

                var input = element.find('input')[0];
                setLastCaret(input, angular);
            }

            // listener blur event
            scope.blurCallback = function(value) {
                if (isEmpty(value)) {
                    return;
                }
                if (angular.isDefined(attrs.id2) &&
                    angular.isDefined(attrs.name2) &&
                    angular.isDefined(attrs.ngModel2)) {
                    // 4)メーカー名称設定
                    // 上記3)が『エラーである』 (error)
                    // 上記3)が『エラーで無い』 and 取得レコード = 無し and cc-notexist = true (not error)
                    if (!checkData || checkData && record.length == 0 && attrs.ccNotexist == 'true') {
                        scope.ngModel2 = undefined;
                    } else if (checkData && record.length != 0) {
                        // 上記3)が『エラーで無い』
                        scope.ngModel2 = record[0].name;
                    }
                }
            }

            // 入力値に対するエラーチェック (check in ccType)

            // パラメータ(cc-parameterxx)に指定されているバインド変数が変更された場合は当該部品のバインド変数を消去する。
            scope.$watch('ccParameter01', function(newValue, oldValue) {
                scope.resetData(newValue, oldValue);
            });

            scope.$watch('ccParameter02', function(newValue, oldValue) {
                scope.resetData(newValue, oldValue);
            });

            scope.$watch('ccParameter03', function(newValue, oldValue) {
                scope.resetData(newValue, oldValue);
            });

            scope.resetData = function(newValue, oldValue) {
                if (angular.equals(cloneData(newValue), cloneData(oldValue))) {
                    return;
                }
                if (angular.isUndefined(attrs.ccInit) || !scope.ccInit) {
                    scope.ngModel = undefined;
                    scope.ngModel2 = undefined;
                } else {
                    scope.ccInit = false;
                }
            }

            scope.$watch('ccParameter04', function(newValue, oldValue) {
                if (angular.equals(cloneData(newValue), cloneData(oldValue))) {
                    return;
                }
                // can't reuse code
                $timeout(function() {
                    scope.$apply(function() {
                        if (angular.isDefined(attrs.id2) &&
                            angular.isDefined(attrs.name2) &&
                            angular.isDefined(attrs.ngModel2)) {
                            inputCtrl.$setValidity('ccExist', true);
                            angular.element(element.find('input')[0]).remove(CSS_CLASS.error);
                            record = null;
                            checkData = false;
                            if (angular.isUndefined(attrs.ccInit) || !scope.ccInit) {
                                scope.ngModel2 = undefined;
                            }
                            // 1)パラメータチェック
                            if (!isEmpty(scope.ccParameter04)) {
                                bmnCd = scope.ccParameter04;
                            } else {
                                bmnCd = 'empty';
                            }
                            // 未設定の場合は処理を終了する。(エラーを設定しない)
                            if (isEmpty(scope.ngModel) || isEmpty(scope.ccParameter01) ||
                                isEmpty(scope.ccParameter02) || isEmpty(scope.ccParameter03)) {
                                return newValue;
                            }

                            // 2)企画名称取得
                            $http({
                                method: 'GET',
                                url: '/core/codename/t000kkkm/' + scope.ngModel + '/' + scope.ccParameter01 + '/' + scope.ccParameter02 + '/' + scope.ccParameter03 + '/' + bmnCd
                            }).success(function(data) {
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
                                    angular.element(element.find('input')[0]).addClass(CSS_CLASS.error);
                                } else {
                                    inputCtrl.$setValidity('ccExist', true);
                                    angular.element(element.find('input')[0]).removeClass(CSS_CLASS.error);
                                }

                                // 4)メーカー名称設定
                                // 上記3)が『エラーである』 (error)
                                // 上記3)が『エラーで無い』 and 取得レコード = 無し and cc-notexist = true (not error)
                                if (!checkData || checkData && record.length == 0 && attrs.ccNotexist == 'true') {
                                    scope.ngModel2 = undefined;
                                } else if (checkData && record.length != 0) {
                                    // 上記3)が『エラーで無い』
                                    scope.ngModel2 = record[0].name;
                                }
                            });
                        }
                    });
                });
            });

            scope.$on(attrs.id + "searchBoxClickedOutter", function(event, data) {
                inputCtrl.$setViewValue(data);
                inputCtrl.$render();
                element.find('input').eq(0).focus();
            });

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