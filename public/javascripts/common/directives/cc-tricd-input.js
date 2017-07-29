/**
 * コード入力(取引先) : cc-tricd-input
 * @ngdo directives
 * @name chasecore.cc-tricd-input
 * @restrict A
 * @function
 *
 * @description
 * (コード入力時)取引先マスタから『取引先名称(or略称)』を取得・表示する
 *
 * @example
    <doc:example>
      <doc:source>
         <cc-tricd-input cc-label="取引先" cc-partition="3" id="triCd" 
             name="triCd" ng-model="result.triCd" cc-required=true
             cc-notexist=true cc-delexist=true cc-shortname=true 
             cc-parameter01="result.hakkoDay" id2="triNm" name2="triNm"
             ng-model2="result.triNm" />
      </doc:source>
      <doc:scenario>
      </doc:scenario>
    </doc:example>
 */

diretiveApp.directive('ccTricdInput',
    function(CSS_CLASS, $http, $timeout, UserInfo, $interval) {
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
                ccWidth: '='
            },
            template: function(element, attrs) {
                // either readOnly or required
                var inputClass = '' + CSS_CLASS.ime_off;
                var inputClass2 = CSS_CLASS.BLANK + CSS_CLASS.disable;
                var readOnlyRequired = '';
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

                // check render second input
                var isZenDefined = angular.isDefined(attrs.id2) && angular.isDefined(attrs.name) && angular.isDefined(attrs.ngModel2);

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

                // maxlength for nameInput
                var maxlength = '';
                if (angular.isDefined(attrs.ccShortname)) {
                    if (attrs.ccShortname == 'true') {
                        maxlength = '5';
                    } else if (attrs.ccShortname == 'false') {
                        maxlength = '20';
                    }
                }

                // html text
                var htmlText = '';
                // render label
                if (attrs.ccPartition == "") {
                    htmlText += '<div class="' + labelClass + '">' + attrs.ccLabel + '</div>';
                } else {
                    htmlText += '<div class="' + labelClass + '">' + attrs.ccLabel;
                    if (angular.isDefined(attrs.ccRequired) && attrs.ccRequired == 'true') {
                        htmlText += '<span class="' + CSS_CLASS.txt_required + '"> *</span>';
                    }
                    htmlText += '</div>';
                }

                // render input triCd
                htmlText += '<input onKeyDown="return setNextFocus(this);" type="text" class="' + inputClass + '" cc-type="tricd" id="' + attrs.id;
                htmlText += '" name="' + attrs.name + '" ng-model="ngModel" ' + readOnlyRequired;
                if (attrs.ccReadonly == 'true') {
                    htmlText += ' maxlength="10" />';
                } else {
                    htmlText += ' ng-minlength="9" maxlength="10" cc-blur-validation error-message="{{error}}"';
                    if (isZenDefined) {
                        htmlText += ' ui-event="{ blur : \'blurCallback(ngModel)\' }" />'; // "取引先名称取得処理(パラメータ)"
                    } else {
                        htmlText += ' />';
                    }
                }

                if (isZenDefined) {
                    htmlText += '&nbsp';
                    // render input triNm
                    htmlText += '<input type="text" cc-type="zenkaku" id="' + attrs.id2;
                    htmlText += '" name="' + attrs.name2 + '" ng-model="ngModel2';
                    htmlText += '" ng-disabled="true" maxlength="' + maxlength;
                    if (!isEmpty(attrs.ccWidth)) {
                        htmlText += '" ng-style="{width: ccWidth}';
                    }
                    htmlText += '" class="' + inputClass2 + '" />';
                }

                return htmlText;
            },
            link: function(scope, element, attrs) {

                var inputCtrl = angular.element(element.find('input')[0]).controller('ngModel');
                var inputElement = angular.element(element.find('input')[0]);
                var stopToken;
                var promise;

                inputElement.bind('click', function() {
                    scope.$emit(attrs.id + "Click");
                });

                // 取り扱うデータの書式は以下とする。
                inputElement.bind('focus', function() {
                    scope.$emit(this.id + "Focus");

                    var empty = isEmpty(inputCtrl.$viewValue);

                    if (empty) {
                        var input = element.find('input')[0];
                        setLastCaret(input, angular);
                        return;
                    } else {
                        var template = /^\d{6}-\d{3}$|^\d{9}$/;

                        if (template.test(inputCtrl.$viewValue)) {
                            var value = inputCtrl.$viewValue;
                            value = removeSymbol(value, '-');
                            inputCtrl.$viewValue = value;
                            inputCtrl.$render();
                            // fix pointer jump from end to begin of input.
                            var input = element.find('input')[0];
                            setLastCaret(input, angular);
                        }
                    }
                });

                inputElement.bind('blur', function() {
                    scope.$emit(this.id + "Blur");
                    if (isEmpty(inputCtrl.$modelValue)) {
                        return;
                    }

                    var template = /^\d{9}$/;
                    if (template.test(inputCtrl.$modelValue)) {
                        // model is valid
                        var value = inputCtrl.$modelValue;
                        var first = '',
                            res = '';
                        first = value.substring(0, 6);
                        res = value.substring(6, 9);

                        value = first + '-' + res;
                        inputCtrl.$viewValue = value;
                        inputCtrl.$render();
                    } else {
                        inputCtrl.$viewValue = inputCtrl.$modelValue;
                        inputCtrl.$render();
                    }
                });

                scope.$on('$destroy', function() {
                    element.find('input').unbind();
                    element.find('input').off();
                })

                // view -> model
                inputCtrl.$parsers.push(function(viewValue) {
                    if (isEmpty(viewValue)) {
                        inputCtrl.$setValidity('ccTricd', true);
                        return viewValue;
                    }

                    var template = /^\d{6}-\d{3}$|^\d{9}$/;
                    var inputValue = viewValue;

                    if (template.test(inputValue)) {
                        inputCtrl.$setValidity('ccTricd', true);
                        // remove special char, allow [0-9]
                        inputValue = inputValue.replace(/[^\d]/g, '');
                        return inputValue;
                    } else {
                        inputCtrl.$setValidity('ccTricd', false);
                        return inputValue;
                    }
                });

                // model -> view
                inputCtrl.$formatters.push(function(inputValue) {
                    //remove error when init again data
                    angular.element('#' + attrs.id + ' #' + attrs.id).removeClass(CSS_CLASS.error);
                    angular.element('#' + attrs.id + ' #' + attrs.id).popover('destroy');

                    if (isEmpty(inputValue)) {
                        inputCtrl.$setValidity('ccTricd', true);
                        return inputValue;
                    }

                    var template = /^\d{9}$/;
                    var temp = inputValue.replace(/[^\d]/g, '');

                    if (template.test(inputValue)) {
                        var first, res;
                        first = inputValue.substring(0, 6);
                        res = inputValue.substring(6, 9);
                        inputCtrl.$setValidity('ccTricd', true);
                        return first + '-' + res;
                    } else {
                        inputCtrl.$setValidity('ccTricd', false);
                        return inputValue;
                    }
                });

                // 取引先名称取得処理
                // メーカー名称取得処理
                var record = null;
                var checkData = false;

                function validation(value) {
                    // init data
                    inputCtrl.$setValidity('ccExist', true);
                    record = null;
                    checkData = false;

                    // 未設定の場合は処理を終了する。(エラーを設定しない)
                    if (isEmpty(value)) {
                        if (angular.isDefined(attrs.id2) && angular.isDefined(attrs.name2) && angular.isDefined(attrs.ngModel2)) {
                            scope.ngModel2 = undefined;
                        }
                        return value;
                    }

                    var requestWithHakkoDay = function(value, hakkoDay) {
                        // 2)取引先名称取得
                        $http({
                            method: 'GET',
                            url: '/core/codename/m011trim/' + value + '?hakkoDay=' + hakkoDay
                        }).success(function(data) {
                            record = data;
                            // 3)結果チェック
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
                    }

                    // 1)パラメータチェック
                    // 未設定の場合は運用日付を取得して代用する。
                    var hakkoDay = scope.ccParameter01;
                    if (isEmpty(hakkoDay)) {
                        requestWithHakkoDay(value, unyoDate);
                    } else {
                        requestWithHakkoDay(value, hakkoDay);
                    }

                    return value;
                }

                // listener blur event
                scope.blurCallback = function(value) {
                    if (isEmpty(value)) {
                        return;
                    }

                    stopToken = $interval(function() {
                        if (angular.isDefined(attrs.id2) && angular.isDefined(attrs.name2) && angular.isDefined(attrs.ngModel2)) {
                            // 4)メーカー名称設定
                            // 上記3)が『エラーである』 (error)
                            // 上記3)が『エラーで無い』 and 取得レコード = 無し and cc-notexist =
                            // true (not error)
                            if (!checkData || checkData && record.length == 0 && attrs.ccNotexist == 'true') {
                                if (attrs.ccTrigerBlur != undefined) {
                                    // just for hajp0010
                                    scope.ngModel2 = '';
                                } else {
                                    scope.ngModel2 = undefined;
                                }
                                $interval.cancel(stopToken);
                            } else if (checkData && record.length != 0) {
                                // 上記3)が『エラーで無い』 and cc-shortname = true
                                if (attrs.ccShortname == 'true') {
                                    scope.ngModel2 = record[0].shortName;
                                    // 上記3)が『エラーで無い』 and cc-shortname = false
                                } else if (attrs.ccShortname == 'false') {
                                    scope.ngModel2 = record[0].name;
                                }
                                $interval.cancel(stopToken);
                            }
                        }

                    }, 100, 0, false);

                }

                if (angular.isDefined(attrs.id2) && angular.isDefined(attrs.name2) && angular.isDefined(attrs.ngModel2)) {
                    inputCtrl.$parsers.push(validation);
                    inputCtrl.$formatters.push(function(modelValue) {
                        //remove error when init again data
                        validation(modelValue);
                        if (attrs.ccTrigerBlur != undefined) {
                            promise = $timeout(function() {
                                // We must reevaluate the value in case it was changed by a subsequent
                                // watch handler in the digest.
                                inputElement.blur();
                              }, 0, false);
                        }
                        return modelValue;
                    });
                }

                // パラメータ(cc-parameterxx)に指定されているバインド変数が変更された場合は当該部品のバインド変数を消去する。
                scope.$watch('ccParameter01', function(value) {
                    if (!inputCtrl.$pristine) {
                        scope.ngModel = undefined;
                        scope.ngModel2 = undefined;
                    }
                });


                scope.$on(attrs.id + "searchBoxClickedOutter", function(event, data) {
                    inputCtrl.$setViewValue(data);
                    inputCtrl.$render();
                    element.find('input').eq(0).focus();
                });

                // Show server error message
                scope.error = null;
                scope.$on("ccError", function(event, value) {
                    scope.error = null;
                    inputCtrl.$setValidity('ccClientError', true);
                    if (!isEmpty(value)) {
                        angular.forEach(value, function(error, key) {
                            if (angular.equals(error.name, attrs.name)) {
                                scope.error = error;
                                if (angular.equals(error.level, 'E')) {
                                    if (error.clientErrorFlag == true) {
                                        inputCtrl.$setValidity('ccClientError', false);
                                    } else {
                                        inputCtrl.$setValidity('ccClientError', true);
                                    }
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

                scope.$on('$destroy', function() {
                    if (promise != undefined) {
                        $timeout.cancel(promise);
                    }
                    if (stopToken != undefined) {
                        $interval.cancel(stopToken);
                    }
                });
            }
        }
    });