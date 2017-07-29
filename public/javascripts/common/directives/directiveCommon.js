/**
 * all directive that can re-use.
 */

var diretiveCommon = angular.module('directiveCommons', []);

/**
 * @ngdoc directive
 * @name chasecore.directive:cc-type
 * @restrict A
 *
 * @description
 * input要素において指定値に応じた入力制限およびValidation（リアルタイム）を行う
 * @example
    <doc:example>
      <doc:source>
        <cc-code-input cc-label="店長コード" cc-partition="3" id="storeMgtCd" name="storeMgrCd" ng-model="cond.storeMgrCd" 
        cc-required=trueng-minlength="9" ng-maxlength="9" cc-chartype="C1"/>
      </doc:source>
    </doc:example>
 */
diretiveCommon.directive('ccType', function(CSS_CLASS, $filter) {
    function getCharCssClass(maxlength) {
        var result = 5;
        if (maxlength >= 1 && maxlength <= 5) {
            result = CSS_CLASS.char_5;
        } else if (maxlength >= 6 && maxlength <= 10) {
            result = CSS_CLASS.char_10;
        } else if (maxlength >= 11 && maxlength <= 15) {
            result = CSS_CLASS.char_15;
        } else if (maxlength >= 16 && maxlength <= 20) {
            result = CSS_CLASS.char_20;
        } else if (maxlength >= 21 && maxlength <= 30) {
            result = CSS_CLASS.char_30;
        } else if (maxlength >= 31 && maxlength <= 40) {
            result = CSS_CLASS.char_40;
        } else if (maxlength >= 41 && maxlength <= 50) {
            result = CSS_CLASS.char_50;
        }
        return result;
    }

    function getNumCssClass(maxlength) {
        var result = 5;
        if (maxlength >= 1 && maxlength <= 5) {
            result = CSS_CLASS.num_5;
        } else if (maxlength >= 6 && maxlength <= 10) {
            result = CSS_CLASS.num_10;
        } else if (maxlength >= 11 && maxlength <= 15) {
            result = CSS_CLASS.num_15;
        } else if (maxlength >= 16 && maxlength <= 20) {
            result = CSS_CLASS.num_20;
        } else if (maxlength >= 21 && maxlength <= 30) {
            result = CSS_CLASS.num_30;
        } else if (maxlength >= 31 && maxlength <= 40) {
            result = CSS_CLASS.num_40;
        } else if (maxlength >= 41 && maxlength <= 50) {
            result = CSS_CLASS.num_50;
        }
        return result;
    }

    return {
        restrict: 'A',
        require: 'ngModel',
        link: function(scope, element, attrs, ctrl) {
            //For number
            var ccFrac = angular.isUndefined(attrs.ccDigitsFranction) ? '0' : attrs.ccDigitsFranction;
            var ccInt = angular.isUndefined(attrs.ccDigitsInteger) ? '0' : attrs.ccDigitsInteger;
            var ccSigned = angular.isUndefined(attrs.ccSigned) ? '0' : '1';

            // Style（ime-mode、text-align、width）
            if (attrs.ccType == 'code') {
                var inputClass = CSS_CLASS.ime_off;
                inputClass += CSS_CLASS.BLANK + CSS_CLASS.left;
                inputClass += CSS_CLASS.BLANK + getNumCssClass(attrs.maxlength);
                element.addClass(inputClass);
            } else if (attrs.ccType == 'alphabet') {
                var inputClass = CSS_CLASS.ime_off;
                inputClass += CSS_CLASS.BLANK + CSS_CLASS.left;
                inputClass += CSS_CLASS.BLANK + getCharCssClass(attrs.maxlength);
                element.addClass(inputClass);
            } else if (attrs.ccType == 'alphanum') {
                var inputClass = CSS_CLASS.ime_off;
                inputClass += CSS_CLASS.BLANK + CSS_CLASS.left;
                inputClass += CSS_CLASS.BLANK + getCharCssClass(attrs.maxlength);
                element.addClass(inputClass);
            } else if (attrs.ccType == 'date') {

            } else if (attrs.ccType == 'number') {

            } else if (attrs.ccType == 'zenkaku') { //全角
                var inputClass = CSS_CLASS.ime_on;
                inputClass += CSS_CLASS.BLANK + CSS_CLASS.left;
                // inputClass += CSS_CLASS.BLANK + getCharCssClass(attrs.maxlength);
                inputClass += CSS_CLASS.BLANK + CSS_CLASS.char_15;
                element.addClass(inputClass);
            } else if (attrs.ccType == 'hankaku') { //半角
                var inputClass = '';
                inputClass += CSS_CLASS.BLANK + CSS_CLASS.char_15;
                element.addClass(inputClass);
            } else if (attrs.ccType == 'postal') { //郵便

            } else if (attrs.ccType == 'phone') { //電話

            } else if (attrs.ccType == 'email') { //MAIL

            } else if (attrs.ccType == 'url') { //URL

            } else if (attrs.ccType == 'time') { //time

            } else if (attrs.ccType == 'tricd') { //tricd
                var inputClass = CSS_CLASS.BLANK + getNumCssClass(attrs.maxlength);
                element.addClass(inputClass);
            }

            // restrict char type
            ctrl.$parsers.unshift(function(inputValue) {
                if (inputValue == undefined) {
                    return '';
                }

                var transformedInput = '';
                if (attrs.ccType == 'code') {
                    transformedInput = inputValue.replace(/[^0-9]/g, '');
                } else if (attrs.ccType == 'alphabet') {
                    transformedInput = inputValue.replace(/[^A-Z a-z]/g, '');
                } else if (attrs.ccType == 'alphanum') {
                    //                    transformedInput = inputValue.replace(/[^A-Z a-z 0-9]/g, '');
                    transformedInput = inputValue.replace(/['"&;<=>?\\]/g, '');
                } else if (attrs.ccType == 'date') {
                    transformedInput = inputValue.replace(/[^0-9/]/g, '');
                } else if (attrs.ccType == 'number') {
                    //keep last input value
                    var old = ctrl.$modelValue;
                    transformedInput = inputValue.replace(/[^\d,.-]/g, '');
                    var intPart = transformedInput != '' ? transformedInput.split('.')[0].replace(/[-,]/g, "") : '0';
                    var intPartLength = intPart.replace(/[-,]/g, "").length;
                    var fracPart = getFrac(transformedInput);

                    //check can input dot and minus
                    var canInputDot = true;
                    var numOfMinus = transformedInput.split('-').length - 1;
                    var numOfDot = transformedInput.split('.').length - 1;
                    if (ccFrac == '0' && numOfDot > 0) {
                        transformedInput = inputValue.replace(/[.]/g, '');
                    }

                    if (ccSigned == '0' && numOfMinus > 0) {
                        transformedInput = inputValue.replace(/[-]/g, '');
                    }

                    var fracLength = 0;
                    if (!isEmpty(fracPart)) {
                        fracLength = fracPart[0].replace(/[.]/g, "").length;
                        transformedInput = transformedInput.split('.')[0] + fracPart[0].replace(/[,]/g, "");
                    }

                    if (intPartLength > ccInt || numOfDot > 1 || (numOfMinus > 1 || (numOfMinus == 1 && transformedInput[0] != '-')) || fracLength > ccFrac) {
                        if (fracLength >= ccFrac && ccFrac != '0' && fracLength != '0') {
                            old = $filter('number')(old, ccFrac);
                        } else if (fracLength < ccFrac && ccFrac != '0' && fracLength != '0') {
                            old = $filter('number')(old, fracLength);
                        }
                        transformedInput = old + '';
                    }
                } else if (attrs.ccType == 'postal' || attrs.ccType == 'tricd') { //郵便
                    //（入力属性：S3(電話)の場合）
                    transformedInput = inputValue.replace(/[^\d-]/g, '');
                } else if (attrs.ccType == 'phone') { //電話
                    transformedInput = inputValue.replace(/[^\d-]/g, '');
                } else if (attrs.ccType == 'time') {
                    transformedInput = inputValue.replace(/[^0-9,:]/g, '');
                } else {
                    transformedInput = inputValue;
                }

                if (transformedInput != inputValue) {
                    ctrl.$setViewValue(transformedInput);
                    //                    if (attrs.ccType == 'number') {
                    //                        ctrl.$render(1);
                    //                    } else {
                    //                        ctrl.$render();
                    //                    }
                    ctrl.$render();
                }
                return transformedInput;
            });

            // custom validity
            ctrl.$parsers.push(function(value) {
                if (attrs.ccType == 'code') {
                    if (ctrl.$error.maxlength) {
                        ctrl.$setValidity('ccNumber', false);
                        return undefined;
                    } else {
                        ctrl.$setValidity('ccNumber', true);
                        return value;
                    }
                } else if (attrs.ccType == 'alphabet') {
                    return value;
                } else if (attrs.ccType == 'alphanum') {
                    return value;
                } else if (attrs.ccType == 'date') {
                    return value;
                } else if (attrs.ccType == 'time') {
                    return value;
                } else if (attrs.ccType == 'number') {
                    return value;
                }
                return value;
            });
        }
    };
});

/**
 * @ngdoc directive
 * @name chasecore.directive:cc-blur-validation
 * @restrict A
 *
 * @description
 * input要素においてblurイベント発生時にValidationの結果をチェックしそれを表示する
 */
diretiveCommon.directive('ccBlurValidation', function(CSS_CLASS, $translate, $rootScope, DirectiveMsg, MsgConst, $filter, $timeout) {
    return {
        restrict: 'A',
        require: 'ngModel',
        link: function compile(scope, element, attrs, ctrl) {

            // lochv start 13/3/2014 : show server error message
            var title = '';
            var pop;

            scope.$on('uploaderError', function(event, data) {
                if (!isEmpty(data)) {
                    title = data.message;
                    if (angular.equals(data.level, 'E')) {
                        element.removeClass(CSS_CLASS.warning);
                        angular.element(element).addClass(CSS_CLASS.error);
                    } else if (angular.equals(data.level, 'W')) {
                        element.removeClass(CSS_CLASS.error);
                        angular.element(element).addClass(CSS_CLASS.warning);
                    }
                } else {
                    element.removeClass(CSS_CLASS.error);
                    element.removeClass(CSS_CLASS.warning);
                }
            })

            attrs.$observe('disabled', function(value) {
                element.removeClass(CSS_CLASS.error);
                angular.element('.popover').remove();
                title = '';
            });
            attrs.$observe('errorMessage', function(value) {
                if (attrs.ignoredError != undefined) {
                    return;
                }

                $rootScope.$broadcast('errorMessage', value);
                if (!isEmpty(value)) {
                    title = angular.fromJson(attrs.errorMessage).message;
                    if (angular.equals(angular.fromJson(attrs.errorMessage).level, 'E')) {
                        element.removeClass(CSS_CLASS.warning);
                        angular.element(element).addClass(CSS_CLASS.error);
                    } else if (angular.equals(angular.fromJson(attrs.errorMessage).level, 'W')) {
                        element.removeClass(CSS_CLASS.error);
                        angular.element(element).addClass(CSS_CLASS.warning);
                    }
                } else {
                    if (ctrl.$valid) {
                        element.removeClass(CSS_CLASS.error);
                        element.removeClass(CSS_CLASS.warning);
                    }
                }
            });
            // lochv end 13/3/2014

            var promise;
            element.bind('blur', function(evt) {
                var symbol = '<%S%>';
                if (attrs.ccType == 'number') {
                    var regularStr = "";
                    if (!angular.isUndefined(attrs.ccSigned)) {
                        regularStr = "^[-+]?([\\d\\,]+(\\.\\d{0,1})?)$";
                    } else {
                        regularStr = "^([\\d\\,]+(\\.\\d{0,1})?)$";
                    }
                    var franction = angular.isUndefined(attrs.ccDigitsFranction) ? '0' : attrs.ccDigitsFranction;
                    regularStr = regularStr.replace('1', franction);
                    var regularObj = new RegExp(regularStr);

                    var inputValue = ctrl.$modelValue;

                    var viewValue = ctrl.$viewValue;
                    //check valid commas
                    var numValidCommas = '0';
                    var viewInt = '0';
                    ctrl.$setValidity('ccNumber', true);
                    if (!angular.isDefined(viewValue) || viewValue == null) {
                        viewValue = '0';
                    }

                    if (viewValue.split('.').length > 1) {
                        viewInt = viewValue.split('.')[0];
                    } else {
                        viewInt = viewValue;
                    }

                    numValidCommas = $filter('number')(viewInt.replace(/[,]/g, ""), 0);
                    if (numValidCommas != viewInt && viewInt.split(',').length - 1 > 0) {
                        ctrl.$setValidity('ccNumber', false);
                    } else {
                        //get Int part and Frac part of number
                        var intPart = getInt(viewValue);
                        var fracPart = getFrac(viewValue);
                        if (!isUndefined(viewValue) && viewValue != null) {
                            //to convert .2 -> 0.2
                            var dot = viewValue.indexOf('.');
                            if (dot != -1) {
                                if (fracPart.length === 0) {
                                    viewValue += '0';
                                }
                                if (intPart == 'NaN') {
                                    viewValue = '0' + viewValue;
                                }
                            }

                            if (!(regularObj.test(viewValue) || viewValue === '')) {
                                ctrl.$setValidity('ccNumber', false);
                            } else {
                                ctrl.$setValidity('ccNumber', true);
                            }
                        }
                    }
                }

                promise = $timeout(function() {
                    if (!isEmpty(pop)) {
                        pop.popover('destroy');
                    }
                    if (ctrl.$invalid && !(ctrl.$pristine && ctrl.$error.required)) {
                        element.addClass(CSS_CLASS.error);
                        title = '';

                        if (ctrl.$error.ccDate) {
                            title = DirectiveMsg.getMessage(MsgConst.MSG_KEY_DATE_FAILED_VALUE);
                        }

                        if (ctrl.$error.ccMax) {
                            title = DirectiveMsg.getMessage(MsgConst.MSG_KEY_VALUE_GREATER_THAN).replace(symbol, attrs.ccMax);
                        }

                        if (ctrl.$error.ccMin) {
                            var message = DirectiveMsg.getMessage(MsgConst.MSG_KEY_VALUE_LESS_THAN);
                            title = message.replace(symbol, attrs.ccMin);
                        }


                        if (ctrl.$error.ccValidateGreater) {
                            title = DirectiveMsg.getMessage(MsgConst.MSG_KEY_VALIDATE_GREATER);
                        }
                        if (ctrl.$error.ccValidateEqual) {
                            title = DirectiveMsg.getMessage(MsgConst.MSG_KEY_VALIDATE_LESS);
                        }

                        if (ctrl.$error.ccValidateLessEqual) {
                            title = DirectiveMsg.getMessage(MsgConst.MSG_KEY_GREATER_TO);
                        }

                        if (ctrl.$error.ccValidateGreaterEqual) {
                            title = DirectiveMsg.getMessage(MsgConst.MSG_KEY_GREATER_EQUAL);
                        }

                        if (ctrl.$error.ccNumber) {
                            title = DirectiveMsg.getMessage(MsgConst.MSG_KEY_NOT_NUMBER);
                        }

                        if (ctrl.$error.ccZenkaku) { //全角 
                            title = DirectiveMsg.getMessage(MsgConst.MSG_KEY_ZENKAKU_FAILED);
                        }

                        if (ctrl.$error.ccHankaku) { //半角
                            title = DirectiveMsg.getMessage(MsgConst.MSG_KEY_HANKAKU_FAILED);
                        }

                        if (ctrl.$error.ccPostal) { //郵便
                            title = DirectiveMsg.getMessage(MsgConst.MSG_KEY_POST_FAILED);
                        }

                        if (ctrl.$error.ccPhone) { //電話
                            title = DirectiveMsg.getMessage(MsgConst.MSG_KEY_PHONE_FAILED);
                        }

                        if (ctrl.$error.email) { //MAIL
                            title = DirectiveMsg.getMessage(MsgConst.MSG_KEY_MAIL_FAILED);
                        }

                        if (ctrl.$error.url) { //URL
                            title = DirectiveMsg.getMessage(MsgConst.MSG_KEY_URL_FAILED);
                        }

                        if (ctrl.$error.ccTime) { //TIME
                            title = DirectiveMsg.getMessage(MsgConst.MSG_KEY_TIME_FAILED);
                        }

                        if (ctrl.$error.ccExist) { //CODE NOT EXIST
                            title = DirectiveMsg.getMessage(MsgConst.MSG_KEY_CODE_NOT_EXIST);
                        }

                        if (ctrl.$error.ccCode) { //CODE_ERROR
                            title = DirectiveMsg.getMessage(MsgConst.MSG_KEY_CODE_ERROR);
                        }

                        if (ctrl.$error.ccCheckdigit) { //Check digit
                            title = DirectiveMsg.getMessage(MsgConst.MSG_KEY_CHECK_DIGIT);
                        }

                        if (ctrl.$error.ccTricd) { //ccTricd
                            title = DirectiveMsg.getMessage(MsgConst.MSG_KEY_CD_FORMAT);
                        }

                        if (ctrl.$error.ccTantocd) { //ccTantocd
                            title = DirectiveMsg.getMessage(MsgConst.MSG_KEY_CD_FORMAT);
                        }

                        if (ctrl.$error.ccServerError) { //ccTantocd
                            title = angular.fromJson(attrs.errorMessage).message;
                        }

                        if (ctrl.$error.ccClientError && !isEmpty(attrs.errorMessage)) {
                            title = angular.fromJson(attrs.errorMessage).message;
                        }

                        if (ctrl.$error.required) {
                            title = DirectiveMsg.getMessage(MsgConst.MSG_KEY_REQUIRED_ITEM); //'xxx is required.'; // REQUIRED_ITEM
                        }

                        if (ctrl.$error.minlength) {
                            title = DirectiveMsg.getMessage(MsgConst.MSG_KEY_MIN_LENGTH).replace(symbol, attrs.ngMinlength);
                        }

                        if (ctrl.$error.maxlength) {
                            title = DirectiveMsg.getMessage(MsgConst.MSG_KEY_MAX_LENGTH).replace(symbol, attrs.ngMaxlength);
                        }
                    } else if (isEmpty(attrs.errorMessage)) {
                        element.removeClass(CSS_CLASS.error);
                        element.removeClass(CSS_CLASS.warning);
                    }
                }, 50);
            }).bind('focus', function(evt) {
                if (element.hasClass(CSS_CLASS.error) || element.hasClass(CSS_CLASS.warning)) {
                    if (!isEmpty(pop)) {
                        pop.popover('destroy');
                    }
                    pop = $(element).popover({
                        content: title,
                        container: "body",
                        trigger: 'manual',
                        viewport: {
                            selector: '#biz',
                            padding: 10
                        },
                        placement: 'auto'
                    });
                    pop.popover('show');
                }
            }).bind('mouseover', function(evt) {
                if (element.hasClass(CSS_CLASS.error) || element.hasClass(CSS_CLASS.warning)) {
                    if (!isEmpty(pop)) {
                        pop.popover('destroy');
                    }
                    pop = $(element).popover({
                        content: title,
                        container: "body",
                        trigger: 'manual',
                        viewport: {
                            selector: '#biz',
                            padding: 10
                        },
                        placement: 'auto'
                    });
                    pop.popover('show');
                }
            }).bind('mouseleave', function(evt) {
                if (!isEmpty(pop)) {
                    pop.popover('destroy');
                }
            });

            scope.$on('$destroy', function() {
                element.unbind();
                element.off();
                $timeout.cancel(promise);
            })

            ctrl.$formatters.push(function(modelValue) {
                element.removeClass(CSS_CLASS.error);
                element.removeClass(CSS_CLASS.warning);
                if (!isEmpty(pop)) {
                    pop.popover('destroy');
                }
                return modelValue;
            });

            // for directive nested ng-grid : to validate
            scope.$watch(function() {
                return ctrl.$valid;
            }, function(value) {
                $rootScope.$broadcast('valid', ctrl.$valid);
            });
            // for directive nested ng-grid : to validate
        }
    };
});

/**
 * @ngdoc directive
 * @name chasecore.directive:cc-blur-validation-combobox
 * @restrict A
 *
 * @description
 * input要素においてblurイベント発生時にValidationの結果をチェックしそれを表示する
 */
/**
 * @ngdoc directive
 * @name chasecore.directive:cc-blur-validation-combobox
 * @restrict A
 *
 * @description
 * input要素においてblurイベント発生時にValidationの結果をチェックしそれを表示する
 */
diretiveCommon.directive('ccBlurValidationCombobox', function(CSS_CLASS, $translate, $rootScope, DirectiveMsg, MsgConst, $filter, $timeout) {
    return {
        restrict: 'A',
        require: 'ngModel',
        link: function compile(scope, element, attrs, ctrl) {
            var commonContent = '';
            var clientPopoverContent = '';
            var serverPopoverContent = '';
            var pop;
            var showErrorCSS;
            var select2Container = element.prev('.select2-container');
            var select2Parent = element.parent();

            attrs.$observe('disabled', function(value) {
                select2Parent.removeClass(CSS_CLASS.parent_common_error);
                select2Parent.removeClass(CSS_CLASS.parent_client_error);
                select2Parent.removeClass(CSS_CLASS.parent_server_error);
                commonContent = '';
                clientPopoverContent = '';
                serverPopoverContent = '';
                if (pop != undefined) {
                    pop.popover('destroy');
                }
            });

            var promise;

            var checkCommonError = function () {
                var symbol = '<%S%>';

                if (!isEmpty(pop)) {
                    pop.popover('destroy');
                }

                var noError = true;

                if (ctrl.$invalid) {
                    select2Parent.addClass(CSS_CLASS.parent_common_error);
                    commonContent = '';

                    if (ctrl.$error.ccExist) {
                        commonContent = DirectiveMsg.getMessage(MsgConst.MSG_KEY_CODE_NOT_EXIST);
                        noError = false;
                    }

                    if (ctrl.$error.ccCode) {
                        commonContent = DirectiveMsg.getMessage(MsgConst.MSG_KEY_CODE_ERROR);
                        noError = false;
                    }

                    if (ctrl.$error.ccSelect) {
                        commonContent = DirectiveMsg.getMessage(MsgConst.MSG_KEY_INVALID_SELECTION);
                        noError = false;
                    }
                }

                if (noError) {
                    commonContent = '';
                    select2Parent.removeClass(CSS_CLASS.parent_common_error);
                    select2Parent.removeClass(CSS_CLASS.parent_common_warning);
                }

                hideErrorPopover();
            }

            var showErrorPopover = function(mode) {
                if (!isEmpty(pop)) {
                    pop.popover('destroy');
                }
                var message = commonContent || clientPopoverContent || serverPopoverContent;
                if (!isEmpty(message)) {
                    pop = select2Parent.popover({
                        content: message,
                        container: "body",
                        trigger: 'manual',
                        viewport: {
                            selector: '#biz',
                            padding: 35
                        },
                        placement: 'auto'
                    });
                    pop.popover('show');
                }
            }

            var hideErrorPopover = function() {
                if (!isEmpty(pop)) {
                    pop.popover('destroy');
                }
            }

            showErrorCSS = function(error, isClientError) {
               var message = undefined;
                if (!isEmpty(error)) {
                    message = error.message;
                    level = error.level;
                } else {
                    message = undefined;
                    level = undefined;
                }

                var errorCSS = (isClientError ? CSS_CLASS.parent_client_error : CSS_CLASS.parent_server_error);
                var warningCSS = (isClientError ? CSS_CLASS.parent_client_warning : CSS_CLASS.parent_server_warning);

                if (!attrs.disabled && !isEmpty(message)) {
                    if (isClientError) {
                        clientPopoverContent = message;
                    } else {
                        serverPopoverContent = message;
                    }

                    if (level == 'W') {
                        select2Parent.addClass(warningCSS);
                    } else if (level == 'E' || level == "undefined") {
                        select2Parent.addClass(errorCSS);
                    }
                } else {
                    if (isClientError) {
                        clientPopoverContent = '';
                    } else {
                        serverPopoverContent = '';
                    }
                    hideErrorPopover();
                    select2Parent.removeClass(errorCSS);
                    select2Parent.removeClass(warningCSS);
                }
            }

            select2Parent.bind('select2-finish-render', function(evt) {
                // $rootScope.comboCounter = $rootScope.comboCounter + 1;
                scope.$emit('select2-finish-render');
                checkCommonError();
            });
            var evt = select2Parent.id + 'Error'
            scope.$on(evt, function() {
                checkCommonError();
            })

            select2Parent.bind('select2-blur', function(evt) {
                checkCommonError();
            }).bind('select2-focus', function(evt) {
                showErrorPopover();
            }).bind('select2-mouseover', function(evt) {
                showErrorPopover();
            }).bind('select2-mouseleave', function(evt) {
                hideErrorPopover();
            });

            scope.$on('$destroy', function() {
                element.unbind();
                $timeout.cancel(promise);
            })

            // for directive nested ng-grid : to validate
            scope.$watch(function() {
                return ctrl.$valid;
            }, function(value) {
                $rootScope.$broadcast('valid', ctrl.$valid);
            });

            scope.$on("ccError", function(event, errors) {
                scope.error = null;
                if (!isEmpty(errors)) {
                    var isClientError = undefined;
                    var finalError = undefined;
                    angular.forEach(errors, function(error, key) {
                        if (angular.equals(error.name, select2Parent.attr('name')) && (angular.equals(error.level, 'E') || angular.equals(error.level, 'W'))) {
                            isClientError = (error.clientErrorFlag === true ? true : false);
                            finalError = error;
                        }
                    });

                    promise = $timeout(function() {
                        if (isClientError !== undefined) {
                            // error, server or client
                            ctrl.$setValidity('ccClientError', !isClientError);
                            showErrorCSS(finalError, isClientError);
                        } else {
                            // no error
                            ctrl.$setValidity('ccClientError', true);
                            showErrorCSS(undefined, false);
                            showErrorCSS(undefined, true);
                        }
                    });
                } else {
                        // no error
                        ctrl.$setValidity('ccClientError', true);
                        showErrorCSS(undefined, false);
                        showErrorCSS(undefined, true);
                }
            });

            scope.$on("resetServerError", function() {
                showErrorCSS(undefined, false);
            });
        }
    };
});


diretiveCommon.directive('ccBlurValidationTextbox', function(CSS_CLASS, $translate, $rootScope, DirectiveMsg, MsgConst, $filter, $timeout) {
    return {
        restrict: 'A',
        require: 'ngModel',
        link: function compile(scope, element, attrs, ctrl) {
            var commonContent = '';
            var clientPopoverContent = '';
            var serverPopoverContent = '';
            var pop;
            var showErrorCSS;
            var input = angular.element(element);

            attrs.$observe('disabled', function(value) {
                input.removeClass(CSS_CLASS.input_common_error);
                input.removeClass(CSS_CLASS.input_client_error);
                input.removeClass(CSS_CLASS.input_server_error);
                commonContent = '';
                clientPopoverContent = '';
                serverPopoverContent = '';
                if (pop != undefined) {
                    pop.popover('destroy');
                }
            });

            var promise;

            var showErrorPopover = function(mode) {
                if (!isEmpty(pop)) {
                    pop.popover('destroy');
                }
                var message = commonContent || clientPopoverContent || serverPopoverContent;
                if (!isEmpty(message)) {
                    pop = input.popover({
                        content: message,
                        container: "body",
                        trigger: 'manual',
                        viewport: {
                            selector: '#biz',
                            padding: 35
                        },
                        placement: 'auto'
                    });
                    pop.popover('show');
                }
            }

            var hideErrorPopover = function() {
                if (!isEmpty(pop)) {
                    pop.popover('destroy');
                }
            }

            showErrorCSS = function(error, isClientError) {
                var message = undefined;
                if (!isEmpty(error)) {
                    message = error.message;
                    level = error.level;
                } else {
                    message = undefined;
                    level = undefined;
                }

                var errorCSS = (isClientError ? CSS_CLASS.input_client_error : CSS_CLASS.input_server_error);
                var warningCSS = (isClientError ? CSS_CLASS.input_client_warning : CSS_CLASS.input_server_warning);

                if (!attrs.disabled && !isEmpty(message)) {
                    if (isClientError) {
                        clientPopoverContent = message;
                    } else {
                        serverPopoverContent = message;
                    }

                    if (level == 'W') {
                        input.addClass(warningCSS);
                    } else if (level == 'E' || level == "undefined") {
                        input.addClass(errorCSS);
                    }
                } else {
                    if (isClientError) {
                        clientPopoverContent = '';
                    } else {
                        serverPopoverContent = '';
                    }
                    hideErrorPopover();
                    input.removeClass(errorCSS);
                    input.removeClass(warningCSS);
                }
            }


            input.bind('blur', function(evt) {
                var symbol = '<%S%>';
                if (attrs.ccType == 'number') {
                    var regularStr = "";
                    if (!angular.isUndefined(attrs.ccSigned)) {
                        regularStr = "^[-+]?([\\d\\,]+(\\.\\d{0,1})?)$";
                    } else {
                        regularStr = "^([\\d\\,]+(\\.\\d{0,1})?)$";
                    }
                    var franction = angular.isUndefined(attrs.ccDigitsFranction) ? '0' : attrs.ccDigitsFranction;
                    regularStr = regularStr.replace('1', franction);
                    var regularObj = new RegExp(regularStr);

                    var inputValue = ctrl.$modelValue;

                    var viewValue = ctrl.$viewValue;
                    //check valid commas
                    var numValidCommas = '0';
                    var viewInt = '0';
                    ctrl.$setValidity('ccNumber', true);
                    if (!angular.isDefined(viewValue) || viewValue == null) {
                        viewValue = '0';
                    }

                    if (viewValue.split('.').length > 1) {
                        viewInt = viewValue.split('.')[0];
                    } else {
                        viewInt = viewValue;
                    }

                    numValidCommas = $filter('number')(viewInt.replace(/[,]/g, ""), 0);
                    if (numValidCommas != viewInt && viewInt.split(',').length - 1 > 0) {
                        ctrl.$setValidity('ccNumber', false);
                    } else {
                        //get Int part and Frac part of number
                        var intPart = getInt(viewValue);
                        var fracPart = getFrac(viewValue);
                        if (!isUndefined(viewValue) && viewValue != null) {
                            //to convert .2 -> 0.2
                            var dot = viewValue.indexOf('.');
                            if (dot != -1) {
                                if (fracPart.length === 0) {
                                    viewValue += '0';
                                }
                                if (intPart == 'NaN') {
                                    viewValue = '0' + viewValue;
                                }
                            }

                            if (!(regularObj.test(viewValue) || viewValue === '')) {
                                ctrl.$setValidity('ccNumber', false);
                            } else {
                                ctrl.$setValidity('ccNumber', true);
                            }
                        }
                    }
                }

                if (!isEmpty(pop)) {
                    pop.popover('destroy');
                }

                var noError = true;

                if (ctrl.$invalid && !(ctrl.$pristine && ctrl.$error.required)) {
                    input.addClass(CSS_CLASS.input_common_error);
                    commonContent = '';

                    if (ctrl.$error.ccDate) {
                        commonContent = DirectiveMsg.getMessage(MsgConst.MSG_KEY_DATE_FAILED_VALUE);
                        noError = false;
                    }

                    if (ctrl.$error.ccMax) {
                        commonContent = DirectiveMsg.getMessage(MsgConst.MSG_KEY_VALUE_GREATER_THAN).replace(symbol, attrs.ccMax);
                        noError = false;
                    }

                    if (ctrl.$error.ccMin) {
                        var message = DirectiveMsg.getMessage(MsgConst.MSG_KEY_VALUE_LESS_THAN);
                        commonContent = message.replace(symbol, attrs.ccMin);
                        noError = false;
                    }


                    if (ctrl.$error.ccValidateGreater) {
                        commonContent = DirectiveMsg.getMessage(MsgConst.MSG_KEY_VALIDATE_GREATER);
                        noError = false;
                    }
                    if (ctrl.$error.ccValidateEqual) {
                        commonContent = DirectiveMsg.getMessage(MsgConst.MSG_KEY_VALIDATE_LESS);
                        noError = false;
                    }

                    if (ctrl.$error.ccValidateLessEqual) {
                        commonContent = DirectiveMsg.getMessage(MsgConst.MSG_KEY_GREATER_TO);
                        noError = false;
                    }

                    if (ctrl.$error.ccValidateGreaterEqual) {
                        commonContent = DirectiveMsg.getMessage(MsgConst.MSG_KEY_GREATER_EQUAL);
                        noError = false;
                    }

                    if (ctrl.$error.ccNumber) {
                        commonContent = DirectiveMsg.getMessage(MsgConst.MSG_KEY_NOT_NUMBER);
                        noError = false;
                    }

                    if (ctrl.$error.ccZenkaku) { //全角 
                        commonContent = DirectiveMsg.getMessage(MsgConst.MSG_KEY_ZENKAKU_FAILED);
                        noError = false;
                    }

                    if (ctrl.$error.ccHankaku) { //半角
                        commonContent = DirectiveMsg.getMessage(MsgConst.MSG_KEY_HANKAKU_FAILED);
                        noError = false;
                    }

                    if (ctrl.$error.ccPostal) { //郵便
                        commonContent = DirectiveMsg.getMessage(MsgConst.MSG_KEY_POST_FAILED);
                        noError = false;
                    }

                    if (ctrl.$error.ccPhone) { //電話
                        commonContent = DirectiveMsg.getMessage(MsgConst.MSG_KEY_PHONE_FAILED);
                        noError = false;
                    }

                    if (ctrl.$error.email) { //MAIL
                        commonContent = DirectiveMsg.getMessage(MsgConst.MSG_KEY_MAIL_FAILED);
                        noError = false;
                    }

                    if (ctrl.$error.url) { //URL
                        commonContent = DirectiveMsg.getMessage(MsgConst.MSG_KEY_URL_FAILED);
                        noError = false;
                    }

                    if (ctrl.$error.ccTime) { //TIME
                        commonContent = DirectiveMsg.getMessage(MsgConst.MSG_KEY_TIME_FAILED);
                        noError = false;
                    }

                    if (ctrl.$error.ccExist) { //CODE NOT EXIST
                        commonContent = DirectiveMsg.getMessage(MsgConst.MSG_KEY_CODE_NOT_EXIST);
                        noError = false;
                    }

                    if (ctrl.$error.ccCode) { //CODE_ERROR
                        commonContent = DirectiveMsg.getMessage(MsgConst.MSG_KEY_CODE_ERROR);
                        noError = false;
                    }

                    if (ctrl.$error.ccCheckdigit) { //Check digit
                        commonContent = DirectiveMsg.getMessage(MsgConst.MSG_KEY_CHECK_DIGIT);
                        noError = false;
                    }

                    if (ctrl.$error.ccTricd) { //ccTricd
                        commonContent = DirectiveMsg.getMessage(MsgConst.MSG_KEY_CD_FORMAT);
                        noError = false;
                    }

                    if (ctrl.$error.ccTantocd) { //ccTantocd
                        commonContent = DirectiveMsg.getMessage(MsgConst.MSG_KEY_CD_FORMAT);
                        noError = false;
                    }

                    if (ctrl.$error.ccServerError) { //ccTantocd
                        commonContent = angular.fromJson(attrs.errorMessage).message;
                        noError = false;
                    }

                    if (ctrl.$error.ccClientError && !isEmpty(attrs.errorMessage)) {
                        commonContent = angular.fromJson(attrs.errorMessage).message;
                        noError = false;
                    }

                    if (ctrl.$error.required) {
                        commonContent = DirectiveMsg.getMessage(MsgConst.MSG_KEY_REQUIRED_ITEM); //'xxx is required.'; // REQUIRED_ITEM
                        noError = false;
                    }

                    if (ctrl.$error.minlength) {
                        commonContent = DirectiveMsg.getMessage(MsgConst.MSG_KEY_MIN_LENGTH).replace(symbol, attrs.ngMinlength);
                        noError = false;
                    }

                    if (ctrl.$error.maxlength) {
                        commonContent = DirectiveMsg.getMessage(MsgConst.MSG_KEY_MAX_LENGTH).replace(symbol, attrs.ngMaxlength);
                        noError = false;
                    }
                }

                if (noError) {
                    commonContent = '';
                    input.removeClass(CSS_CLASS.input_common_error);
                    input.removeClass(CSS_CLASS.input_common_warning);
                }

                hideErrorPopover();
            }).bind('focus', function(evt) {
                showErrorPopover();
            }).bind('mouseover', function(evt) {
                showErrorPopover();
            }).bind('mouseleave', function(evt) {
                hideErrorPopover();
            });

            scope.$on('$destroy', function() {
                element.unbind();
                element.off();
                $timeout.cancel(promise);
            })

            // for directive nested ng-grid : to validate
            scope.$watch(function() {
                return ctrl.$valid;
            }, function(value) {
                $rootScope.$broadcast('valid', ctrl.$valid);
            });

            ctrl.$formatters.push(function(modelValue) {
                commonContent = '';
                showErrorCSS(undefined, false);
                return modelValue;
            });

            scope.$on("ccError", function(event, errors) {
                scope.error = null;
                if (!isEmpty(errors)) {
                    var isClientError = undefined;
                    var finalError = undefined;
                    angular.forEach(errors, function(error, key) {
                        if (angular.equals(error.name, input.attr('name')) && (angular.equals(error.level, 'E') || angular.equals(error.level, 'W'))) {
                            isClientError = (error.clientErrorFlag === true ? true : false);
                            finalError = error;
                        }
                    });

                    promise = $timeout(function() {
                        if (isClientError !== undefined) {
                            // ctrl.$setValidity('ccClientError', !isClientError);
                            if (angular.equals(finalError.level, 'E')) {
                                // error, server or client
                                ctrl.$setValidity('ccClientError', !isClientError);
                            } else if (angular.equals(finalError.level, 'W')) {
                                // client warning
                                ctrl.$setValidity('ccClientError', true);
                            }
                            showErrorCSS(finalError, isClientError);
                        } else {
                            // no error
                            ctrl.$setValidity('ccClientError', true);
                            showErrorCSS(undefined, false);
                            showErrorCSS(undefined, true);
                        }
                    });
                } else {
                        // no error
                        ctrl.$setValidity('ccClientError', true);
                        showErrorCSS(undefined, false);
                        showErrorCSS(undefined, true);
                }
            });

            scope.$on("resetServerError", function() {
                showErrorCSS(undefined, false);
            });
        }
    };
});


/**
 * @ngdoc directive
 * @name chasecore.directive:cc-max
 * @restrict A
 *
 * @description
 * データ＜＝指定値であるかValidateする
 * @example
    <doc:example>
      <doc:source>
        <cc-number-input cc-label="原単価" cc-partition="3" id="genka" name="genka" ng-model="result.genka" cc-required=true cc-min="-9999.9" cc-max="9999.9"/>
      </doc:source>
    </doc:example>
 */
diretiveCommon.directive('ccMax', function() {
    return {
        restrict: 'A',
        require: '^ngModel',
        link: function(scope, element, attrs, ctrl) {
            ctrl.$parsers.push(function(inputValue) {
                if (isEmpty(attrs.ccType)) {
                    return inputValue;
                }
                ctrl.$setValidity('ccMax', true);
                if (isEmpty(inputValue)) {
                    return inputValue;
                }
                var floatInput = parseFloat(inputValue);

                var valid = (floatInput <= parseFloat(attrs.ccMax));
                ctrl.$setValidity('ccMax', valid);
                //                return valid ? inputValue : undefined;
                return inputValue;
            });
        }
    };
});

/**
 * @ngdoc directive
 * @name chasecore.directive:cc-min
 * @restrict A
 *
 * @description
 * データ＞＝指定値であるかValidateする
 * @example
    <doc:example>
      <doc:source>
        <cc-number-input cc-label="原単価" cc-partition="3" id="genka" name="genka" ng-model="result.genka" cc-required=true cc-min="-9999.9" cc-max="9999.9"/>
      </doc:source>
    </doc:example>
 */
diretiveCommon.directive('ccMin', function() {
    return {
        restrict: 'A',
        require: '^ngModel',
        link: function(scope, element, attrs, ctrl) {
            ctrl.$parsers.push(function(inputValue) {
                if (isEmpty(attrs.ccType)) {
                    return inputValue;
                }
                ctrl.$setValidity('ccMin', true);
                if (isEmpty(inputValue)) {
                    return inputValue;
                }
                var floatInput = parseFloat(inputValue);

                var valid = (floatInput >= parseFloat(attrs.ccMin));
                ctrl.$setValidity('ccMin', valid);
                //                return valid ? inputValue : undefined;
                return inputValue;
            });
        }
    };
});

/**
 * @ngdoc directive
 * @name chasecore.directive: cc-validate-equal
 * @restrict A
 *
 * @description
 * データ＝指定値であるかValidateする
 * @example
    <doc:example>
      <doc:source>
        <cc-number-input cc-label="原単価" cc-partition="3" id="genka" name="genka" ng-model="result.genka" cc-required=true cc-validate-equal="2000" />
      </doc:source>
    </doc:example>
 */
diretiveCommon.directive('ccValidateEqual', function() {
    return {
        restrict: 'A',
        require: '^ngModel',
        link: function(scope, element, attrs, ctrl) {
            ctrl.$parsers.push(function(inputValue) {
                if (inputValue == undefined) {
                    return '';
                }

                var valid = (inputValue == attrs.ccValidateEqual);
                ctrl.$setValidity('ccValidateEqual', valid);
                return valid ? inputValue : undefined;
            });
        }
    };
});


/**
 * @ngdoc directive
 * @name chasecore.directive:ccValidateGreater
 * @restrict A
 *
 * @description
 * データ＞指定値かValidateする
 * @example
    <doc:example>
      <doc:source>
        <cc-number-input cc-label="原単価" cc-partition="3" id="genka" name="genka" ng-model="result.genka" cc-validate-greater="100" cc-required=true/>
      </doc:source>
    </doc:example>
 */
diretiveCommon.directive('ccValidateGreater', function() {
    return {
        restrict: 'A',
        require: '^ngModel',
        link: function(scope, element, attrs, ctrl) {
            ctrl.$parsers.push(function(inputValue) {
                if (isEmpty(inputValue)) {
                    return '';
                }
                var floatInput = parseFloat(inputValue);

                var valid = (floatInput > parseFloat(attrs.ccValidateGreater));
                ctrl.$setValidity('ccValidateGreater', valid);
                return valid ? inputValue : undefined;
            });
        }
    };
});

/**
 * @ngdoc directive
 * @name chasecore.directive:ccValidateGreater
 * @restrict A
 *
 * @description
 * データ＞指定値かValidateする
 * @example
    <doc:example>
      <doc:source>
        <cc-number-input cc-label="原単価" cc-partition="3" id="genka" name="genka" ng-model="result.genka" cc-validate-greater-equal="100" cc-required=true/>
      </doc:source>
    </doc:example>
 */
diretiveCommon.directive('ccValidateGreaterEqual', function() {
    return {
        restrict: 'A',
        require: '^ngModel',
        link: function(scope, element, attrs, ctrl) {
            ctrl.$parsers.push(function(inputValue) {
                if (isEmpty(inputValue)) {
                    return '';
                }
                if (attrs.ccType == 'date') {
                    var check = checkDate(inputValue);
                    if ('-1' != check && !isEmpty(attrs.ccValidateGreaterEqual)) {
                        var floatInput = parseFloat(removeSymbol(check, "/"));

                        var valid = (floatInput >= parseFloat(removeSymbol(attrs.ccValidateGreaterEqual, "/")));
                        ctrl.$setValidity('ccValidateGreaterEqual', valid);
                    }
                    return inputValue;
                } else if (attrs.ccType == 'time') {
                    var check = checkTime(inputValue);
                    if ('-1' != check && !isEmpty(attrs.ccValidateGreaterEqual)) {
                        var floatInput = parseFloat(removeSymbol(check, ":"));

                        var valid = (floatInput >= parseFloat(removeSymbol(attrs.ccValidateGreaterEqual, ":")));
                        ctrl.$setValidity('ccValidateGreaterEqual', valid);
                    }
                    return inputValue;
                } else if (attrs.ccType == 'number') {
                    if (isEmpty(attrs.ccValidateGreaterEqual)) {
                        ctrl.$setValidity('ccValidateGreaterEqual', true);
                        return inputValue;
                    }
                    var floatInput = parseFloat(removeSymbol(inputValue, ","));

                    var valid = (floatInput >= parseFloat(attrs.ccValidateGreaterEqual));
                    ctrl.$setValidity('ccValidateGreaterEqual', valid);

                    return inputValue;
                }
            });

            ctrl.$formatters.push(function(inputValue) {
                if (isEmpty(inputValue)) {
                    return '';
                }
                if (attrs.ccType == 'date') {
                    var check = checkDate(inputValue);
                    if ('-1' != check && !isEmpty(attrs.ccValidateGreaterEqual)) {
                        var floatInput = parseFloat(removeSymbol(check, "/"));

                        var valid = (floatInput >= parseFloat(removeSymbol(attrs.ccValidateGreaterEqual, "/")));
                        ctrl.$setValidity('ccValidateGreaterEqual', valid);
                    }
                    return inputValue;
                }
                if (attrs.ccType == 'time') {
                    var check = checkTime(inputValue);
                    if ('-1' != check && !isEmpty(attrs.ccValidateGreaterEqual)) {
                        var floatInput = parseFloat(removeSymbol(check, ":"));
                        var valid = (floatInput <= parseFloat(removeSymbol(attrs.ccValidateGreaterEqual, ":")));
                        ctrl.$setValidity('ccValidateGreaterEqual', valid);
                        return check;
                    }
                    return inputValue;
                }
            });
        }
    };
});

/**
 * @ngdoc directive
 * @name chasecore.directive:ccValidateGreater
 * @restrict A
 *
 * @description
 * データ＞指定値かValidateする
 * @example
    <doc:example>
      <doc:source>
        <cc-number-input cc-label="原単価" cc-partition="3" id="genka" name="genka" ng-model="result.genka" cc-validate-less-equal="100" cc-required=true/>
      </doc:source>
    </doc:example>
 */
diretiveCommon.directive('ccValidateLessEqual', function() {
    return {
        restrict: 'A',
        require: '^ngModel',
        link: function(scope, element, attrs, ctrl) {
            ctrl.$parsers.push(function(inputValue) {
                if (isEmpty(inputValue)) {
                    return '';
                }

                if (attrs.ccType == 'date') {
                    var check = checkDate(inputValue);
                    if ('-1' != check && !isEmpty(attrs.ccValidateLessEqual)) {
                        var floatInput = parseFloat(removeSymbol(check, "/"));
                        var valid = (floatInput <= parseFloat(removeSymbol(attrs.ccValidateLessEqual, "/")));
                        ctrl.$setValidity('ccValidateLessEqual', valid);
                    }
                    return inputValue;
                } else if (attrs.ccType == 'time') {
                    var check = checkTime(inputValue);
                    if ('-1' != check && !isEmpty(attrs.ccValidateLessEqual)) {
                        var floatInput = parseFloat(removeSymbol(check, ":"));
                        var valid = (floatInput <= parseFloat(removeSymbol(attrs.ccValidateLessEqual, ":")));
                        ctrl.$setValidity('ccValidateLessEqual', valid);
                    }
                    return inputValue;
                } else if (attrs.ccType == 'number') {
                    if (isEmpty(attrs.ccValidateLessEqual)) {
                        ctrl.$setValidity('ccValidateLessEqual', true);
                        return inputValue;
                    }
                    var floatInput = parseFloat(inputValue);

                    var valid = (floatInput <= parseFloat(attrs.ccValidateLessEqual));
                    ctrl.$setValidity('ccValidateLessEqual', valid);
                    return inputValue;
                }
            });

            ctrl.$formatters.push(function(inputValue) {
                if (isEmpty(inputValue)) {
                    return '';
                }

                if (attrs.ccType == 'date') {
                    var check = checkDate(inputValue);
                    if ('-1' != check && !isEmpty(attrs.ccValidateLessEqual)) {
                        var floatInput = parseFloat(removeSymbol(check, "/"));
                        var valid = (floatInput <= parseFloat(removeSymbol(attrs.ccValidateLessEqual, "/")));
                        ctrl.$setValidity('ccValidateLessEqual', valid);
                    }
                    return inputValue;
                }
                if (attrs.ccType == 'time') {
                    var check = checkTime(inputValue);
                    if ('-1' != check && !isEmpty(attrs.ccValidateLessEqual)) {
                        var floatInput = parseFloat(removeSymbol(check, ":"));
                        var valid = (floatInput <= parseFloat(removeSymbol(attrs.ccValidateLessEqual, ":")));
                        ctrl.$setValidity('ccValidateLessEqual', valid);
                        return check;
                    }
                    return inputValue;
                }
            });
        }
    };
});

diretiveCommon.directive('ccFocus', function($timeout) {
    return {
        link: function(scope, element, attrs) {
            var promise;
            scope.$watch(attrs.ccFocus, function(val) {
                if (angular.isDefined(val) && val) {
                    promise = $timeout(function() {
                        element[0].focus();
                    });
                }
            }, true);

            scope.$on('$destroy', function() {
                $timeout.cancel(promise);
            });
        }
    };
});

diretiveCommon.directive('ccComboboxFocus', function($parse, $timeout, $rootScope, $interval) {
    return {
        link: function(scope, element, attrs) {
            var sel2Id = '#s2id_' + attrs.id;
            var sel2Id2 = '#' + attrs.id;
            var flag = attrs.id.substr(0, 5) == 'grid_';
            var promise;
            var stopToken;
            scope.$watch(attrs.ccComboboxFocus, function(val) {
                if (val === true) {
                    stopToken = $interval(function() {
                        if ($rootScope.pendingRequests == 0) {
                            promise = $timeout(function() {
                                element.find(sel2Id).select2('focus', true);
                                $rootScope.pendingRequests = -1;
                            }, 200);
                            $interval.cancel(stopToken);
                        }
                    }, 250, 0, false);
                } else if (val === 1) {
                    stopToken = $interval(function() {
                        if ($rootScope.pendingRequests == 0) {
                            promise = $timeout(function() {
                                element.find(sel2Id).select2('focus', true);
                                $rootScope.pendingRequests = -1;
                            }, 200);
                            $interval.cancel(stopToken);
                        }
                    }, 250, 0, false);
                } else if (val === 2) {
                    if (!flag) {
                        stopToken = $interval(function() {
                            element.find(sel2Id).select2('focus', true);
                            $interval.cancel(stopToken);
                        }, 100, 0, false);
                    } else {
                        stopToken = $interval(function() {
                            if (element.find(sel2Id2).is('select')) {
                                element.find(sel2Id2).select2('focus', true);
                                $interval.cancel(stopToken);
                            }
                        }, 100, 0, false);
                    }
                }
            }, false);

            scope.$on('$destroy', function() {
                if (promise != undefined) {
                    $timeout.cancel(promise);
                }
                if (stopToken != undefined) {
                    $interval.cancel(stopToken);
                }
            });
        }
    };
});

diretiveCommon.directive('ccTabs', function($timeout) {
    return {
        link: function(scope, element, attrs) {
            var promise = $timeout(function() {
                element.tabs();
            });
            scope.$on('$destroy', function() {
                $timeout.cancel(promise);
            })
        }
    };
});

diretiveCommon.directive('ccProgressBar', function(Message, MsgConst) {
    return {
        restrict: 'E',
        scope: {},
        template: function(element, attrs) {
            var htmlText = '<div id="progressBarText">' + Message.getMessage(MsgConst.MSG_KEY_REPORT_LOADING) + '</div>'
            htmlText += '<div class="progressBar"></div>';
            return htmlText;
        },

        link: function(scope, element, attrs, ctrl) {
            var idInterval = setInterval(function() {
                if (scope.position == undefined) {
                    scope.position = 0;
                }
                angular.element('.progressBar').css('background-position', scope.position + '% 0%');
                scope.position += 0.125;
                scope.position = scope.position % 100;
            }, 10);

            scope.$on('$destroy', function() {
                clearInterval(idInterval);
            });
        }
    };
});

diretiveCommon.directive('ccMousetrap', function() {
    return {
        restrict: 'E',
        require: "^ngController",
        controller: ['$scope', '$element', '$attrs', '$rootScope', '$window',
            function($scope, $element, $attrs, $rootScope, $window) {

                var miceScope = $scope;

                Mousetrap.unbind('enter');
                Mousetrap.bindGlobal('enter', function(e) {
                    var nodeName = e.target.nodeName.toLowerCase();
                    if (nodeName != 'button') {
                        $window.event.keyCode = 9;
                        $window.event.which = 9;
                    }
                });

                if ($attrs.isScreen == "true") {

                    Mousetrap.unbind('f9');
                    Mousetrap.bindGlobal('f9', function(e) {
                        e.preventDefault();
                        if ($rootScope.flagOpenDialog == true) {
                            return;
                        }

                        miceScope.$apply(function() {
                            if (miceScope.canSearchEdit != undefined && miceScope.doSearchEdit != undefined) {
                                if (miceScope.canSearchEdit()) {
                                    miceScope.doSearchEdit();
                                }
                            }
                        });
                    });

                    Mousetrap.unbind('f10');
                    Mousetrap.bindGlobal('f10', function(e) {
                        e.preventDefault();
                        if ($rootScope.flagOpenDialog == true) {
                            return;
                        }

                        miceScope.$apply(function() {
                            if (miceScope.canSearch != undefined && miceScope.doSearch != undefined) {
                                if (miceScope.canSearch()) {
                                    miceScope.doSearch();
                                }
                            }
                        });
                    });

                    Mousetrap.unbind('f3');
                    Mousetrap.bindGlobal('f3', function(e) {
                        e.preventDefault();
                        miceScope.$broadcast("pressF3Key");
                        if ($rootScope.flagOpenDialog == true) {
                            return;
                        }

                        miceScope.$apply(function() {
                            if (miceScope.doClear != undefined) {
                                miceScope.doClear();
                            }
                        });
                    });

                    Mousetrap.unbind('f12');
                    Mousetrap.bindGlobal('f12', function(e) {
                        e.preventDefault();
                        if ($rootScope.flagOpenDialog == true) {
                            return;
                        }

                        miceScope.$apply(function() {
                            if (miceScope.canSave != undefined && miceScope.canSave()) {
                                if (miceScope.doSave != undefined) {
                                    miceScope.doSave();
                                } else {
                                    if (miceScope.doInsertUpdate != undefined) {
                                        miceScope.doInsertUpdate();
                                    }
                                }
                            }
                        });
                    });

                    Mousetrap.unbind('alt+del');
                    Mousetrap.bindGlobal('alt+del', function(e) {
                        e.preventDefault();
                        if ($rootScope.flagOpenDialog == true) {
                            return;
                        }

                        miceScope.$apply(function() {
                            if (miceScope.canDelete != undefined && miceScope.doDelete != undefined) {
                                if (miceScope.canDelete()) {
                                    miceScope.doDelete();
                                };
                            }
                        });
                    });

                    $element.bind('$destroy', function() {
                        Mousetrap.unbind('f9');
                        Mousetrap.unbind('f10');
                        Mousetrap.unbind('f3');
                        Mousetrap.unbind('f12');
                        Mousetrap.unbind('alt+del');
                        Mousetrap.unbind('enter');
                    });
                } else if ($attrs.isScreen == "false") {
                    Mousetrap.unbind('f12');
                    Mousetrap.bindGlobal('f12', function(e) {
                        e.preventDefault();
                        if ($rootScope.flagOpenDialog == true) {
                            return;
                        }

                        miceScope.$apply(function() {
                            if (miceScope.canExecute != undefined && miceScope.doExecute != undefined) {
                                if (miceScope.canExecute()) {
                                    miceScope.doExecute();
                                }
                            }
                        });

                    });

                    Mousetrap.unbind('f3');
                    Mousetrap.bindGlobal('f3', function(e) {
                        e.preventDefault();
                        miceScope.$broadcast("pressF3Key");
                        if ($rootScope.flagOpenDialog == true) {
                            return;
                        }

                        miceScope.$apply(function() {
                            if (miceScope.doClear != undefined) {
                                miceScope.doClear();
                            }
                        });
                    });

                    $element.bind('$destroy', function() {
                        Mousetrap.unbind('f3');
                        Mousetrap.unbind('f12');
                        Mousetrap.unbind('enter');
                    });
                }
            }
        ]
    }
});

diretiveCommon.directive('ccTriSearch', function($compile, $timeout) {
    return {
        restrict: 'A',
        link: function($scope, $element, $attrs, $window) {
            var template = '<div class="searchPopover" role="tooltip"><div class="arrow"></div><div class="popover-content"></div></div>';
            var html = '<div ng-controller="TRISEARCHCtrl" class="triSearchContainer" parent-id="' + $attrs.id + '"><div class="searchPopover-title-container"><h3 class="searchPopover-title" ng-bind="title"></h3><div class="errorContainer" ng-class="{\'cs-search-message-error\':level==\'E\',\'cs-search-message-warning\':level==\'W\',\'cs-search-message-success\':level==\'S\',\'cs-search-message-info\':level==\'I\'}" ng-bind="message"></div></h3></div><div id="infoBlock"><div class="search-box-header grid-header-row"><div class="align-middle"><cc-code-input cc-label="取引先コード" cc-partition="3" id="search-triCd" ng-model="search.triCd" cc-required="false" cc-readonly="false" ng-minlength="1" ng-maxlength="9" cc-chartype="C1" cc-width="100" cc-focus="getFocusCond()"/></div><div class="align-middle"><cc-date-input cc-label="発効日" cc-partition="3" ng-model="search.hakkoDay" cc-required="false" cc-readonly="false"cc-width="100"/></div></div><div class="search-box-header grid-header-row"><div class="align-middle"><cc-string-input cc-label="名称(漢字)" cc-partition="3" ng-model="search.triNm" cc-required="false" cc-readonly="false" ng-minlength="1" ng-maxlength="40" cc-chartype="S1" cc-width="267"/></div><div class="align-middle"><button class="cs-btn-search" id="gridSearch" name="gridSearch" ng-click="gridSearch()"><div>検索<span></span></div></button></div></div><div class="gridStyle" ng-grid="gridOptions"><cc-spinner/></div></div></div>';

            var popover;
            var promise;
            var compiledContent;
            var closePopover = function() {
                angular.element('#gridSearch').off();
                promise = $timeout(function() {
                    angular.element(compiledContent).scope().$destroy();
                });
                popover.popover('searchDestroy');
                popover = undefined;
                $element.find('input').eq(0).focus();
            }

            $scope.$on('$destroy', function() {
                $timeout.cancel(promise);
            });

            $scope.$on($attrs.id + "Click", function() {
                if (popover != undefined) {
                    closePopover();
                } else {
                    compiledContent = $compile(html)($scope.$new());
                    popover = angular.element($element[0]).popover({
                        html: true,
                        animation: false,
                        template: template,
                        trigger: 'manual',
                        content: compiledContent,
                        viewport: {
                            selector: '#biz',
                            padding: 35
                        },
                        placement: 'auto'
                    });
                    popover.popover('show');
                }
                angular.element(".searchPopover").detach().appendTo('body');
                angular.element('#gridSearch').on('keydown', function(e) { 
                  var keyCode = e.keyCode || e.which;
                  if (keyCode == 9) { 
                    e.preventDefault();
                    angular.element("input[id='search-triCd']").focus();
                  } 
                });
            });

            $scope.$on($attrs.id + "triSearchClicked", function(event, data) {
                $scope.$broadcast($attrs.id + "searchBoxClickedOutter", data);
                if (popover != undefined) {
                    closePopover();
                }
            });

            $scope.$on("ESC", function(event, data) {
                if (popover != undefined) {
                    closePopover();
                }
            });
            $scope.$on("pressF2Key", function(event, data) {
                if (popover != undefined) {
                    closePopover();
                }
            });
            $scope.$on("pressF3Key", function(event, data) {
                if (popover != undefined) {
                    closePopover();
                }
            });
            $scope.$on("select2-open", function(event, data) {
                var parent = angular.element($element)[0];
                var child = angular.element('#' +  data)[0];
                if (!$.contains(parent, child)) {
                    if (popover != undefined) {
                        closePopover();
                    }
                }
            });


            angular.element('body').on('click', function(event) {
                if (popover != undefined) {
                    var parent = angular.element($element)[0];
                    var child = angular.element(event.target)[0];

                    if (!$.contains(parent, child) && !angular.element(event.target).is('body') && !angular.element(event.target).is('.searchPopover *') && !angular.element(event.target).is('#ui-datepicker-div')) {

                        // get datepicker child base on datepicker target class and id
                        var selectorChild = getSelector(child);

                        var datepickerChild = angular.element('#ui-datepicker-div ' + selectorChild)[0];
                        var datepickerParent = angular.element('#ui-datepicker-div')[0];

                        // Check whether event's target is inside datepicker
                        if (!$.contains(datepickerParent, datepickerChild)) {
                            closePopover();
                        }
                    }
                }
            });
        }
    }
});

diretiveCommon.directive('ccKkkSearch', function($compile, $timeout) {
    return {
        restrict: 'A',
        link: function($scope, $element, $attrs, $window) {
            var template = '<div class="searchPopover" role="tooltip"><div class="arrow"></div><div class="popover-content"></div></div>';
            var html = '<div ng-controller="KKKSEARCHCtrl" class="kkkSearchContainer" parent-id="' + $attrs.id + '"><div class="searchPopover-title-container"><h3 class="searchPopover-title" ng-bind="title"></h3><div class="errorContainer" ng-class="{\'cs-search-message-error\':level==\'E\',\'cs-search-message-warning\':level==\'W\',\'cs-search-message-success\':level==\'S\',\'cs-search-message-info\':level==\'I\'}" ng-bind="message"></div></h3></div><div id="infoBlock"><div class="search-box-header grid-header-row"><div class="align-middle"><cc-jigyobu-combobox cc-label="事業部" cc-partition="3" id="search-jigyobuCd" name="jigyobuCd" ng-model="search.jigyobuCd" cc-required="false" cc-delexist=true cc-shortname=true cc-parameter01="search.kaisyaCd" cc-parameter02="sysDate" cc-readonly="false" cc-combobox-focus="getFocusCond()"/></div><div class="align-middle"><cc-kbn2-combobox cc-label="年度" id="search-nendo" cc-partition="3" ng-model="search.nendo" cc-key1="X0001" cc-required="false" cc-readonly="false" cc-width="95"/></div><div class="align-middle"><cc-code-input cc-label="企画コード" cc-partition="3" id="search-kikakuCd" ng-model="search.kikakuCd" cc-required="false" cc-readonly="false" ng-minlength="1" ng-maxlength="9" cc-chartype="C1" cc-chartype="C1"/></div><div class="align-middle"><cc-string-input cc-label="企画名" id="search-kikakuNm" cc-partition="3" ng-model="search.kikakuNm" cc-required="false" cc-readonly="false" ng-minlength="1" ng-maxlength="40"/></div><div class="align-middle"><cc-code-input cc-label="企画種類" cc-partition="3" id="search-kikakuSyu" ng-model="search.kikakuSyu" cc-required="false" cc-readonly="false" ng-minlength="1" ng-maxlength="4" cc-chartype="C1"/></div></div><div class="search-box-header grid-header-row"><div class="align-middle"><cc-bmn-combobox cc-label="部門" cc-partition="3" id="search-bmnCd" name="bmnCd" ng-model="search.bmnCd" cc-required="false" cc-delexist=true cc-shortname=true cc-parameter01="search.jigyobuCd" cc-parameter02="sysDate" cc-readonly="disableCond()" cc-error="error"/></div><div class="align-middle"><cc-date-input cc-label="発注日" cc-partition="3" id="search-kakuteiDay" ng-model="search.kakuteiDay" cc-required="false" cc-readonly="false"/></div><div class="align-middle"><cc-date-input cc-label="販売可能日" cc-partition="3" id="search-hanbaiFrdd" ng-model="search.hanbaiFrdd" cc-required="false" cc-readonly="false"/></div><div class="align-middle"><cc-date-input cc-label="納品可能日" cc-partition="3"id="search-siireFrdd" ng-model="search.siireFrdd" cc-required="false" cc-readonly="false"/></div><div class="align-middle"><cc-kbn2-combobox cc-label="ﾁﾗｼ区分" cc-partition="3" id="search-tirasiKbn" ng-model="search.tirasiKbn" cc-key1="T0003" cc-required="false" cc-readonly="false" ng-maxlength="1" ng-minlength="1"/></div><div class="align-middle"><button class="cs-btn-search" id="gridSearch" name="gridSearch" ng-click="gridSearch()"><div>検索<span></span></div></button></div></div><div class="gridStyle" ng-grid="gridOptions"><cc-spinner/></div></div></div>';

            var popover;
            var promise;
            var compiledContent;
            var closePopover = function() {
                angular.element('#gridSearch').off();
                promise = $timeout(function() {
                    angular.element(compiledContent).scope().$destroy();
                });
                popover.popover('searchDestroy');
                popover = undefined;
                $element.find('input').eq(0).focus();
            }

            $scope.$on('$destroy', function() {
                $timeout.cancel(promise);
            });

            $scope.$on($attrs.id + "Click", function() {
                if (popover != undefined) {
                    closePopover();
                } else {
                    compiledContent = $compile(html)($scope.$new());
                    popover = angular.element($element[0]).popover({
                        html: true,
                        animation: false,
                        template: template,
                        trigger: 'manual',
                        content: compiledContent,
                        viewport: {
                            selector: '#biz',
                            padding: 35
                        },
                        placement: 'auto'
                    });
                    popover.popover('show');
                    angular.element(".searchPopover").detach().appendTo('body');
                    angular.element('#gridSearch').on('keydown', function(e) { 
                      var keyCode = e.keyCode || e.which;
                      if (keyCode == 9) { 
                        e.preventDefault();
                        angular.element('#s2id_search-jigyobuCd').select2('focus', true);
                      } 
                    });
                }
            });

            $scope.$on($attrs.id + "kkkSearchClicked", function(event, data) {
                $scope.$broadcast($attrs.id + "searchBoxClickedOutter", data);
                if (popover != undefined) {
                    closePopover();
                }
            });

            $scope.$on("ESC", function(event, data) {
                if (popover != undefined) {
                    closePopover();
                }
            });
            $scope.$on("pressF2Key", function(event, data) {
                if (popover != undefined) {
                    closePopover();
                }
            });
            $scope.$on("pressF3Key", function(event, data) {
                if (popover != undefined) {
                    closePopover();
                }
            });
            $scope.$on("select2-open", function(event, data) {
                var parent = angular.element($element)[0];
                var child = angular.element('#' +  data)[0];
                if (!$.contains(parent, child)) {
                    if (popover != undefined) {
                        closePopover();
                    }
                }
            });


            angular.element('body').on('click', function(event) {
                if (popover != undefined) {
                    var parent = angular.element($element)[0];
                    var child = angular.element(event.target)[0];

                    if (!$.contains(parent, child) && !angular.element(event.target).is('body') && !angular.element(event.target).is('.searchPopover *') && !angular.element(event.target).is('#ui-datepicker-div')) {

                        // get datepicker child base on datepicker target class and id
                        var selectorChild = getSelector(child);

                        var datepickerChild = angular.element('#ui-datepicker-div ' + selectorChild)[0];
                        var datepickerParent = angular.element('#ui-datepicker-div')[0];

                        // Check whether event's target is inside datepicker
                        if (!$.contains(datepickerParent, datepickerChild)) {
                            closePopover();
                        }
                    }
                }
            });
        }
    }
});

diretiveCommon.directive('ccJanSearch', function($compile, $timeout) {
    return {
        restrict: 'A',
        link: function($scope, $element, $attrs, $window) {
            var template = '<div class="searchPopover" role="tooltip"><div class="arrow"></div><div class="popover-content"></div></div>';
            var html = '<div ng-controller="JANSEARCHCtrl" class="janSearchContainer" parent-id="' + $attrs.id + '"><div class="searchPopover-title-container"><h3 class="searchPopover-title" ng-bind="title"></h3><div class="errorContainer" ng-class="{\'cs-search-message-error\':level==\'E\',\'cs-search-message-warning\':level==\'W\',\'cs-search-message-success\':level==\'S\',\'cs-search-message-info\':level==\'I\'}" ng-bind="message"></div></h3></div><div id="infoBlock"><div class="search-box-header grid-header-row"><div class="align-middle"><cc-code-input cc-partition="3" id="search-janCd" cc-label="JANコード" ng-model="search.janCd" cc-required="false" cc-required="true" cc-focus="getFocusCond()" ng-minlength="1" ng-maxlength="13" cc-chartype="C1"/></div><div class="align-middle"><cc-date-input cc-partition="3" id="search-hakkoDay" cc-label="発効日(FR)" ng-model="search.hakkoDay" cc-required="false"/></div><div class="align-middle"><cc-date-input cc-partition="3" id="search-hakkoDayTo" cc-label="発効日(TO)" ng-model="search.hakkoDayTo" cc-required="false"/></div><div class="align-middle"><cc-jigyobu-combobox cc-label="事業部" cc-partition="3" id="search-jigyobuCd" name="jigyobuCd" ng-model="search.jigyobuCd" cc-required="false" cc-delexist=true cc-shortname=true cc-parameter01="search.kaisyaCd" cc-parameter02="sysDate"/></div></div><div class="search-box-header grid-header-row"><div class="align-middle"><cc-bmn-combobox cc-label="部門" cc-partition="3" id="search-bmnCd" name="bmnCd" ng-model="search.bmnCd" cc-required="false" cc-delexist=true cc-shortname=true cc-parameter01="search.jigyobuCd" cc-parameter02="sysDate" cc-error="error"/></div><div class="align-middle"><cc-chubnrcd-input cc-partition="3" id="search-chuBnrCd" cc-label="ライン" ng-model="search.chuBnrCd" cc-required="false"/></div><div class="align-middle"><cc-shobnrcd-input cc-partition="3" id="search-shoBnrCd" cc-label="クラス" ng-model="search.shoBnrCd" cc-required="false"/></div><div class="align-middle"><cc-mkrcd-input cc-partition="3" id="search-mkrCd" cc-label="メーカーコード" ng-model="search.mkrCd" cc-required="false"/></div><div class="align-middle"><cc-tricd-input cc-partition="3" id="search-triCd" cc-label="取引先コード" ng-model="search.triCd" cc-required="false"/></div></div><div class="search-box-header grid-header-row"><div class="align-middle"><cc-string-input cc-partition="3" id="search-shnNm" cc-label="商品名(漢字)" ng-model="search.shnNm" cc-required="false" ng-minlength="1" ng-maxlength="40" cc-chartype="S1"/></div><div class="align-middle"><cc-string-input cc-partition="3" id="search-shnNmA" cc-label="商品名(ｶﾅ)" ng-model="search.shnNmA" cc-required="false" ng-minlength="1" ng-maxlength="30" cc-chartype="S2"/></div><div class="align-middle"><cc-string-input cc-partition="3" id="search-mkrNm" cc-label="メーカー名(漢字)" ng-model="search.mkrNm" cc-required="false" ng-minlength="1" ng-maxlength="40" cc-chartype="S1"/></div></div><div class="search-box-header grid-header-row"><div class="align-middle"><cc-string-input cc-partition="3" id="search-kikakuNm" cc-label="規格名(漢字)" ng-model="search.kikakuNm" cc-required="false" ng-minlength="1" ng-maxlength="20" cc-chartype="S1"/></div><div class="align-middle"><cc-string-input cc-partition="3" id="search-kikakuNmA" cc-label="規格名(ｶﾅ)" ng-model="search.kikakuNmA" cc-required="false" ng-minlength="1" ng-maxlength="10" cc-chartype="S2"/></div><div class="align-middle"><button class="cs-btn-search" id="gridSearch" name="gridSearch" ng-click="gridSearch()"><div>検索<span></span></div></button></div></div><div class="gridStyle" ng-grid="gridOptions"><cc-spinner/></div></div></div>';

            var popover;
            var promise;
            var compiledContent;
            var closePopover = function() {
                angular.element('#gridSearch').off();
                promise = $timeout(function() {
                    angular.element(compiledContent).scope().$destroy();
                });
                popover.popover('searchDestroy');
                popover = undefined;
                $element.find('input').eq(0).focus();
            }

            $scope.$on('$destroy', function() {
                $timeout.cancel(promise);
            });

            $scope.$on($attrs.id + "Click", function() {
                if (popover != undefined) {
                    closePopover();
                } else {
                    compiledContent = $compile(html)($scope.$new());
                    popover = angular.element($element[0]).popover({
                        html: true,
                        animation: false,
                        template: template,
                        trigger: 'manual',
                        content: compiledContent,
                        viewport: {
                            selector: '#biz',
                            padding: 35
                        },
                        placement: 'auto'
                    });
                    popover.popover('show');
                    angular.element(".searchPopover").detach().appendTo('body');
                    angular.element('#gridSearch').on('keydown', function(e) { 
                      var keyCode = e.keyCode || e.which;
                      if (keyCode == 9) { 
                        e.preventDefault();
                        angular.element("input[id='search-janCd']").focus();
                      } 
                    });
                }
            });

            $scope.$on($attrs.id + "janSearchClicked", function(event, data) {
                if (popover != undefined) {
                    $scope.$broadcast($attrs.id + "searchBoxClickedOutter", data);
                    closePopover();
                }
            });

            $scope.$on("ESC", function(event, data) {
                if (popover != undefined) {
                    closePopover();
                }
            });
            $scope.$on("pressF2Key", function(event, data) {
                if (popover != undefined) {
                    closePopover();
                }
            });
            $scope.$on("pressF3Key", function(event, data) {
                if (popover != undefined) {
                    closePopover();
                }
            });
            $scope.$on("select2-open", function(event, data) {
                var parent = angular.element($element)[0];
                var child = angular.element('#' +  data)[0];
                if (!$.contains(parent, child)) {
                    if (popover != undefined) {
                        closePopover();
                    }
                }
            });


            angular.element('body').on('click', function(event) {
                if (popover != undefined) {
                    var parent = angular.element($element)[0];
                    var child = angular.element(event.target)[0];

                    if (!$.contains(parent, child) && !angular.element(event.target).is('body') && !angular.element(event.target).is('.searchPopover *') && !angular.element(event.target).is('#ui-datepicker-div')) {

                        // get datepicker child base on datepicker target class and id
                        var selectorChild = getSelector(child);

                        var datepickerChild = angular.element('#ui-datepicker-div ' + selectorChild)[0];
                        var datepickerParent = angular.element('#ui-datepicker-div')[0];

                        // Check whether event's target is inside datepicker
                        if (!$.contains(datepickerParent, datepickerChild)) {
                            closePopover();
                        }
                    }
                }
            });
        }
    }
});

diretiveCommon.directive('ccSantiSearch', function($compile, $timeout) {
    return {
        restrict: 'A',
        link: function($scope, $element, $attrs, $window) {
            var template = '<div class="searchPopover" role="tooltip"><div class="arrow"></div><div class="popover-content"></div></div>';
            var html = '<div ng-controller="SANTISEARCHCtrl" class="santiSearchContainer" parent-id="' + $attrs.id + '"><div class="searchPopover-title-container"><h3 class="searchPopover-title" ng-bind="title"></h3><div class="errorContainer" ng-class="{\'cs-search-message-error\':level==\'E\',\'cs-search-message-warning\':level==\'W\',\'cs-search-message-success\':level==\'S\',\'cs-search-message-info\':level==\'I\'}" ng-bind="message"></div></h3></div><div id="infoBlock"><div class="search-box-header grid-header-row"><div class="align-middle"><cc-code-input cc-partition="3" id="search-santiCd" cc-label="産地コード" ng-model="search.santiCd" cc-required="false" cc-required="true" cc-focus="getFocusCond()" ng-minlength="1" ng-maxlength="3" cc-chartype="C1"/></div><div class="align-middle"><cc-string-input cc-partition="3" id="search-santiNm" cc-label="産地名" ng-model="search.santiNm" cc-required="false" ng-minlength="1" ng-maxlength="20" cc-chartype="S1"/></div></div><div class="search-box-header grid-header-row"><div class="align-middle"><cc-string-input cc-partition="3" id="search-santiNmR" cc-label="産地名略称（漢字）" ng-model="search.santiNmR" cc-required="false" ng-minlength="1" ng-maxlength="20" cc-chartype="S1"/></div><div class="align-middle"><cc-string-input cc-partition="3" id="search-santiNmA" cc-label="産地名（カナ）" ng-model="search.santiNmA" cc-required="false" ng-minlength="1" ng-maxlength="10" cc-chartype="S2"/></div></div><div class="search-box-header grid-header-row"><div class="align-middle"><button class="cs-btn-search" id="gridSearch" name="gridSearch" ng-click="gridSearch()"><div>検索<span></span></div></button></div></div><div class="gridStyle" ng-grid="gridOptions"><cc-spinner/></div></div></div>';

            var popover;
            var promise;
            var compiledContent;
            var closePopover = function() {
                angular.element('#gridSearch').off();
                promise = $timeout(function() {
                    angular.element(compiledContent).scope().$destroy();
                });
                popover.popover('searchDestroy');
                popover = undefined;
                $element.find('input').eq(0).focus();
            }

            $scope.$on('$destroy', function() {
                $timeout.cancel(promise);
            });

            $scope.$on($attrs.id + "Click", function() {
                if (popover != undefined) {
                    closePopover();
                } else {
                    compiledContent = $compile(html)($scope.$new());
                    popover = angular.element($element[0]).popover({
                        html: true,
                        animation: false,
                        template: template,
                        trigger: 'manual',
                        content: compiledContent,
                        viewport: {
                            selector: '#biz',
                            padding: 35
                        },
                        placement: 'auto'
                    });
                    popover.popover('show');
                    angular.element(".searchPopover").detach().appendTo('body');
                    angular.element('#gridSearch').on('keydown', function(e) { 
                      var keyCode = e.keyCode || e.which;
                      if (keyCode == 9) { 
                        e.preventDefault();
                        angular.element("input[id='search-santiCd']").focus();
                      } 
                    });
                }
            });

            $scope.$on($attrs.id + "santiSearchClicked", function(event, data) {
                if (popover != undefined) {
                    $scope.$broadcast($attrs.id + "searchBoxClickedOutter", data);
                    closePopover();
                }
            });

            $scope.$on("ESC", function(event, data) {
                if (popover != undefined) {
                    closePopover();
                }
            });
            $scope.$on("pressF2Key", function(event, data) {
                if (popover != undefined) {
                    closePopover();
                }
            });
            $scope.$on("pressF3Key", function(event, data) {
                if (popover != undefined) {
                    closePopover();
                }
            });
            $scope.$on("select2-open", function(event, data) {
                var parent = angular.element($element)[0];
                var child = angular.element('#' +  data)[0];
                if (!$.contains(parent, child)) {
                    if (popover != undefined) {
                        closePopover();
                    }
                }
            });


            angular.element('body').on('click', function(event) {
                if (popover != undefined) {
                    var parent = angular.element($element)[0];
                    var child = angular.element(event.target)[0];

                    if (!$.contains(parent, child) && !angular.element(event.target).is('body') && !angular.element(event.target).is('.searchPopover *') && !angular.element(event.target).is('#ui-datepicker-div')) {

                        // get datepicker child base on datepicker target class and id
                        var selectorChild = getSelector(child);

                        var datepickerChild = angular.element('#ui-datepicker-div ' + selectorChild)[0];
                        var datepickerParent = angular.element('#ui-datepicker-div')[0];

                        // Check whether event's target is inside datepicker
                        if (!$.contains(datepickerParent, datepickerChild)) {
                            closePopover();
                        }
                    }
                }
            });
        }
    }
});

diretiveCommon.directive('ccSpinner', function() {
    return {
        restrict: 'E',
        template: function($scope, $element, $attrs) {
            return '<div id="spinner-container"></div>';
        },
        link: function($scope, $element, $attrs) {
            var opts = {
                // The number of lines to draw
                lines: 13,
                // The length of each line
                length: 10,
                // The line thickness
                width: 2,
                // The radius of the inner circle
                radius: 5,
                // Corner roundness (0..1)
                corners: 1,
                // The rotation offset
                rotate: 0,
                // 1: clockwise, -1: counterclockwise
                direction: 1,
                // #rgb or #rrggbb or array of colors
                color: '#000',
                // Rounds per second
                speed: 1,
                // Afterglow percentage
                trail: 60,
                // Whether to render a shadow
                shadow: false,
                // Whether to use hardware acceleration
                hwaccel: false,
                // The CSS class to assign to the spinner
                className: 'spinner',
                // The z-index (defaults to 2000000000)
                zIndex: 1000,
                // Top position relative to parent
                top: '50%',
                // Left position relative to parent
                left: '50%',
            };
            var spinner = new Spinner(opts).spin();
            $element.find('#spinner-container').append(spinner.el);
            $element.find('.spinner').addClass('displayNone');


            $scope.$on('spinnerStop', function() {
                $element.find('.spinner').addClass('displayNone');
            });

            $scope.$on('spinnerResume', function() {
                $element.find('.spinner').removeClass('displayNone');
            });

            $scope.$on('$destroy', function() {
                spinner.stop();
                spinner = null;
            });
        }
    }
});

diretiveCommon.directive('ccBinInfo', function($compile, $timeout) {
    return {
        restrict: 'E',
        require: 'ngModel',
        scope: {
            ngModel: '=',
            ccParameter01: '='
        },
        template: function(element, attrs) {
            // html text
            var htmlText = '<img ng-src="/assets/images/icon_bin_info.png"' + '" id="' + attrs.id + '" name="' + attrs.name + '" ng-model="ngModel" ' + '/>';
            return htmlText;
        },
        link: function(scope, element, attrs, ctrl) {
            var template = '<div class="infoPopover" role="tooltip"><div class="arrow"></div><div class="popover-content"></div></div>';
            var html = '<div ng-controller="BININFOCtrl" ng-init="doSearch(ngModel, ccParameter01)" parent-id="' + attrs.id + '"><div id="infoBlock"><div class="align-middle"><div class="cs-header-bininfo-hacchyu">１便</div><div class="gridStyleOfHacchyuArea1 gridStyleOfHacchyuArea" ng-grid="gridOptionsOfHacchyuArea1"></div></div><div class="align-middle"><div class="cs-header-bininfo-hacchyu">２便</div><div class="gridStyleOfHacchyuArea2 gridStyleOfHacchyuArea" ng-grid="gridOptionsOfHacchyuArea2"></div></div><div class="align-middle"><div class="cs-header-bininfo-hacchyu">３便</div><div class="gridStyleOfHacchyuArea3 gridStyleOfHacchyuArea" ng-grid="gridOptionsOfHacchyuArea3"></div></div></div></div>';

            var popover;
            var promise;
            var compiledContent;
            var closePopover = function() {
                promise = $timeout(function() {
                    angular.element(compiledContent).scope().$destroy();
                });
                popover.popover('infoDestroy');
                popover = undefined;
            }

            scope.$on('$destroy', function() {
                $timeout.cancel(promise);
                element.find('img').unbind();
                element.find('img').off();
            });

            element.bind('mouseover', function(evt) {
                if (popover != undefined) {
                    closePopover();
                } else {
                    compiledContent = $compile(html)(scope.$new());
                    popover = angular.element(element[0]).popover({
                        html: true,
                        animation: false,
                        template: template,
                        trigger: 'manual',
                        content: compiledContent,
                        viewport: {
                            selector: '#biz',
                            padding: 35
                        },
                        placement: 'auto'
                    });
                    popover.popover('show');
                    angular.element(".infoPopover").detach().appendTo('body');
                }
            }).bind('mouseleave', function(evt) {
                if (popover != undefined) {
                    closePopover();
                }
            });
        }
    }
});



diretiveCommon.directive('ccNextFocus', function($timeout) {
    return {
        link: function(scope, element, attrs) {
            var promise;
            if (attrs.ccNextFocus) {
                element.on('keydown', function(e) { 
                    var keyCode = e.keyCode || e.which;
                    if (keyCode == 9) { 
                      e.preventDefault();
                      var nextElement = angular.element(attrs.ccNextFocus + ':enabled');
                      if (nextElement.length) {
                          nextElement.focus();
                      } else {
                          angular.element('[tabindex=0],form :input:visible:enabled').first().focus();
                      }
                    } 
                  });
            }

            scope.$on('$destroy', function() {
                $timeout.cancel(promise);
            });
        }
    };
});