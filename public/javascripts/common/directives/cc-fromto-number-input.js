/**
 * @ngdoc directive
 * @name chasecore.directive:cc-fomto-number-input
 * @restrict A
 * @function
 *
 * @description
 * 棚卸番号*       -1,234.5    ～         1,234.5
 *
 * @example
    <doc:example>
      <doc:source>
         <cc-fromto-number-input cc-label="休店期間" cc-partition="3" 
               id="tanaNoSt" name="tanaNoSt" ng-model="result.tanaNoSt" cc-required=true
               id2="tanaNoEd" name2="tanaNoEd" ng-model2="result.tanaNoEd" cc-required2=true />
      </doc:source>
      <doc:scenario>
      </doc:scenario>
    </doc:example>
 */
diretiveApp.directive('ccFromtoNumberInput', ['$filter', 'CSS_CLASS', '$timeout', function ($filter, CSS_CLASS, $timeout) {
    
    /**
     * Get css class suitable
     */
    function geNumCssClass(maxlength) {
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
        restrict: 'E',
        require: '?^form',
        scope : {
            ngModel: '=',
            ngModel2: '=',
            ccReadonly : '=',
            ccReadonly2 : '=',
            ccFocus: '=',
            ccError: '=',
            ccWidth1: '=',
            ccWidth2: '='
        },
        template: function (element, attrs) {
            // 符号要否(cc-signed)
            var signedStr = getSigned(attrs.ccMin, attrs.ccMax);
            var signedStr2 = getSigned(attrs.ccMin2, attrs.ccMax2);
            // 整数部の桁数(cc-digits-integer)
            var digitsInteger = getMaxIntLength(attrs.ccMin, attrs.ccMax);
            var digitsInteger2 = getMaxIntLength(attrs.ccMin2, attrs.ccMax2);
            // 小数部の桁数(cc-digits-franction)
            var digitsFranction = getMaxFracLength(attrs.ccMin, attrs.ccMax);
            var digitsFranction2 = getMaxFracLength(attrs.ccMin2, attrs.ccMax2);
            // 最大桁数(ng-maxlength)
            var maxlength = getMaxLength(attrs.ccMin, attrs.ccMax);
            var maxlength2 = getMaxLength(attrs.ccMin2, attrs.ccMax2);

            // either readOnly or required
            var readOnlyRequired = '';
            var readOnlyRequired2 = '';
            var inputClass = CSS_CLASS.ime_off;
            var inputClass2 = CSS_CLASS.ime_off;
            if (angular.isDefined(attrs.ccFocus)) {
                readOnlyRequired += ' cc-focus="ccFocus"';
            }

            if (angular.isDefined(attrs.ccReadonly)) {
                readOnlyRequired += ' ng-disabled="ccReadonly" ng-class="{\''+ CSS_CLASS.disable + '\': ccReadonly, \'\': !ccReadonly}" ';
            }
            
            if (angular.isDefined(attrs.ccReadonly2)) {
                readOnlyRequired2 += ' ng-disabled="ccReadonly2" ng-class="{\''+ CSS_CLASS.disable + '\': ccReadonly2, \'\': !ccReadonly2}" ';
            }
            
            if (angular.isDefined(attrs.ccReadonly) && attrs.ccReadonly == 'true') {
                readOnlyRequired += ' readonly ';
                inputClass += CSS_CLASS.BLANK + CSS_CLASS.disable;
            }
            if (angular.isDefined(attrs.ccReadonly2) && attrs.ccReadonly2 == 'true') {
                readOnlyRequired2 += ' readonly ';
                inputClass2 += CSS_CLASS.BLANK + CSS_CLASS.disable;
            }
            
            if (angular.isDefined(attrs.ccRequired) && attrs.ccRequired == 'true') {
                readOnlyRequired += ' required ';
            }
            if (angular.isDefined(attrs.ccRequired2) && attrs.ccRequired2 == 'true') {
                readOnlyRequired2 += ' required ';
            }

            inputClass += CSS_CLASS.BLANK + geNumCssClass(maxlength);
            inputClass2 += CSS_CLASS.BLANK + geNumCssClass(maxlength2);

            // style for label
            var labelClass = '';
            if (angular.isDefined(attrs.ccPartition) && attrs.ccPartition == '') {
                labelClass += CSS_CLASS.label_divide_zero;
            } else {
                if ((angular.isDefined(attrs.ccRequired) && attrs.ccRequired == 'true') ||
                        (angular.isDefined(attrs.ccRequired2) && attrs.ccRequired2 == 'true')) {
                    labelClass += CSS_CLASS.cs_label_large_required;
                } else {
                    labelClass += CSS_CLASS.cs_label_large;
                }
            }
            labelClass += CSS_CLASS.BLANK + CSS_CLASS.label;
            
            // html text
            var htmlText = '';

            if (angular.isDefined(attrs.ccPartition) && attrs.ccPartition == '') {
                if (angular.isDefined(attrs.ccReadonly)) {
                    readOnlyRequired += ' ng-disabled="ccReadonly" ng-class="{\'' + CSS_CLASS.disable
                            + '\': ccReadonly, \'\': !ccReadonly}" ';
                }

                if (angular.isDefined(attrs.ccReadonly2)) {
                    readOnlyRequired2 += ' ng-disabled="ccReadonly2" ng-class="{\'' + CSS_CLASS.disable
                            + '\': ccReadonly2, \'\': !ccReadonly2}" ';
                }
                htmlText += '<div class="' + CSS_CLASS.label_divide_zero + 
                    CSS_CLASS.BLANK + CSS_CLASS.label + '" >' + attrs.ccLabel;
                
                /*  ※    当該部品は入力テキストが２つ存在するので『⑥IO』の内容に従って判断する。
                    FROM項目                                      TO項目                                          項目タイトル
                    cc-required="true"      cc-required="true"     cc-required="true"
                    cc-required="true"      cc-required="false"    cc-required="true"
                    cc-required="true"      cc-readonly="true"     cc-required="true"
                    cc-required="false"     cc-required="true"     cc-required="true"
                    cc-required="false"     cc-required="false"    cc-required="false"
                    cc-required="false"     cc-readonly="true"     cc-required="false"
                    cc-readonly="true"      cc-required="true"     cc-required="true"
                    cc-readonly="true"      cc-required="false"    cc-required="false"
                    cc-readonly="true"      cc-readonly="true"     cc-readonly="true"
                */
                if ((angular.isDefined(attrs.ccRequired) && attrs.ccRequired == 'true') ||
                        (angular.isDefined(attrs.ccRequired2) && attrs.ccRequired2 == 'true')) {
                    readOnlyRequired += ' required ';
                    readOnlyRequired2 += ' required ';
                    
                } else if (((angular.isDefined(attrs.ccReadonly) && attrs.ccReadonly == 'true') &&
                                (angular.isDefined(attrs.ccRequired2) && attrs.ccRequired2 == 'false')) ||
                            ((angular.isDefined(attrs.ccReadonly2) && attrs.ccReadonly2 == 'true') &&
                                (angular.isDefined(attrs.ccRequired) && attrs.ccRequired == 'false')) ||
                            ((angular.isDefined(attrs.ccReadonly2) && attrs.ccReadonly2 == 'false') &&
                                (angular.isDefined(attrs.ccRequired) && attrs.ccRequired == 'false'))) {
                    //cc-required="false" ~ not required.
                    readOnlyRequired += '';
                    readOnlyRequired2 += '';
                } else if ((angular.isDefined(attrs.ccReadonly) && attrs.ccReadonly == 'true') &&
                        (angular.isDefined(attrs.ccReadonly2) && attrs.ccReadonly2 == 'true')){
                    readOnlyRequired += ' readonly ';
                    readOnlyRequired2 += ' readonly ';
                }
                
                htmlText += '</div>';
            } else {
                htmlText += '<div class="' + labelClass+ '">' + attrs.ccLabel;
                if ((angular.isDefined(attrs.ccRequired) && attrs.ccRequired == 'true') ||
                        (angular.isDefined(attrs.ccRequired2) && attrs.ccRequired2 == 'true')) {
                    htmlText += '<span class="' + CSS_CLASS.txt_required + '"> *</span>';
                }
            }

            htmlText += '</div>';
            htmlText += '<div style="float: left;" ><input onKeyDown="return setNextFocus(this);" error-message="{{errorSt}}" type="text" class="' + inputClass +
                    '" cc-type="number" id="' + attrs.id + '" name="' + attrs.name + 
                    '" ng-model="ngModel" ' + readOnlyRequired + ' maxlength="' + maxlength + '" ';
            if (!isEmpty(attrs.ccWidth1)) {
                htmlText += ' ng-style="{width: ccWidth1}"';
            }
            if (angular.isDefined(attrs.ccRequired) || 
                    (angular.isDefined(attrs.ccPartition) && 
                            attrs.ccPartition == '' && 
                            angular.isDefined(attrs.ccReadonly))) {
                htmlText += 'cc-blur-validation ';
                htmlText += 'ng-minlength="1" ';
                if (!angular.isUndefined(attrs.ccMin)) {
                    htmlText += ' cc-min="' + attrs.ccMin + '"';
                }
                
                if (!angular.isUndefined(attrs.ccMax)) {
                    htmlText += ' cc-max="' + attrs.ccMax + '"';
                }
            }
            
            //※ readonlyを設定する項目を[O]とし、readonlyを設定しない項目を[I]と解釈する。
            //FROM項目                  TO項目                    FROM項目の属性                                                          TO項目の属性
            //I               0             cc-validate-less-equal="TO項目"      -        
            //O               0                                                  -        
            if (((angular.isDefined(attrs.ccReadonly2) || attrs.ccReadonly2 == "true")
                    && (angular.isUndefined(attrs.ccReadonly) || attrs.ccReadonly == "false"))
                || angular.isDefined(attrs.ccRequired)) {
                htmlText += ' cc-validate-less-equal="{{ngModel2}}" ';
            }

            if (digitsInteger != -1) {
                htmlText += ' cc-digits-integer="' + digitsInteger + '"';
            }
            
            if (digitsFranction != -1) {
                htmlText += ' cc-digits-franction="' + digitsFranction + '"';
            }
            
            if (signedStr != '') {
                htmlText += CSS_CLASS.BLANK + signedStr;
            }
            
            htmlText += ' /></div>';

            htmlText += '<div class="' + CSS_CLASS.label_divide_zero + 
                        CSS_CLASS.BLANK + CSS_CLASS.label + '" >～</div>';
            htmlText += '<input onKeyDown="return setNextFocus(this);" error-message="{{errorEd}}" type="text" class="' + inputClass2 + 
                        '" cc-type="number" id="' + attrs.id2 + '" name="' + attrs.name2 + 
                        '" ng-model="ngModel2" ' + readOnlyRequired2 + ' maxlength="' + maxlength2 + '" ';
            if (!isEmpty(attrs.ccWidth2)) {
                htmlText += ' ng-style="{width: ccWidth2}"';
            }
            if (angular.isDefined(attrs.ccRequired2) || 
                    (angular.isDefined(attrs.ccPartition) && 
                            attrs.ccPartition == '' && 
                            angular.isDefined(attrs.ccReadonly2))) {
                htmlText += ' cc-blur-validation ';
                htmlText += ' ng-minlength="1" ';
                if (!angular.isUndefined(attrs.ccMin2)) {
                    htmlText += ' cc-min="' + attrs.ccMin2 + '"';
                }
                
                if (!angular.isUndefined(attrs.ccMax2)) {
                    htmlText += ' cc-max="' + attrs.ccMax2 + '"';
                }
            }

            //FROM項目                  TO項目                    FROM項目の属性                         TO項目の属性
            //I               I             -                      cc-validate-greater-equal="FROM項目"
            //O               I             -                      cc-validate-greater-equal="FROM項目"
            if ((angular.isDefined(attrs.ccReadonly) && angular.isDefined(attrs.ccReadonly2))
                    || (attrs.ccReadonly == "true" && angular.isUndefined(attrs.ccReadonly2))
                    || (angular.isDefined(attrs.ccRequired) && angular.isDefined(attrs.ccRequired2))) {
                htmlText += ' cc-validate-greater-equal="{{ngModel}}" ';
            }

            if (digitsInteger2 != -1) {
                htmlText += ' cc-digits-integer="' + digitsInteger2 + '"';
            }
            
            if (digitsFranction2 != -1) {
                htmlText += ' cc-digits-franction="' + digitsFranction2 + '"';
            }
            
            if (signedStr2 != '') {
                htmlText += CSS_CLASS.BLANK + signedStr2;
            }
            
            htmlText += ' />';
            return htmlText; 
        },
        //replace : true,
        link: function(scope, element, attrs, ctrl) {
            var control = angular.element(element.find('input')[0]).controller('ngModel');
            var controlSecond = angular.element(element.find('input')[1]).controller('ngModel');
            
            //Create regular expression to check validate number
            var signedStr = getSigned(attrs.ccMin, attrs.ccMax);
            var signedStr2 = getSigned(attrs.ccMin2, attrs.ccMax2);
            var digitsFranction = getMaxFracLength(attrs.ccMin, attrs.ccMax);
            var digitsFranction2 = getMaxFracLength(attrs.ccMin2, attrs.ccMax2);
            var regularStr = "";
            var regularStr2 = "";
            
            if (!isEmpty(signedStr)) {
                regularStr = "^[-+]?([\\d\\,]+(\\.\\d{0,1})?)$";
            } else {
                regularStr = "^([\\d\\,]+(\\.\\d{0,1})?)$";
            }
            
            if (!isEmpty(signedStr2)) {
                regularStr = "^[-+]?([\\d\\,]+(\\.\\d{0,1})?)$";
            } else {
                regularStr = "^([\\d\\,]+(\\.\\d{0,1})?)$";
            }
            
            
            var franction = digitsFranction != -1 ? digitsFranction : '0';
            var franction2 = digitsFranction2 != -1 ? digitsFranction2 : '0';
            regularStr = regularStr.replace('1', franction);
            regularStr2 = regularStr2.replace('1', franction2);
            
            var regularObj = new RegExp(regularStr);
            var regularObj2 = new RegExp(regularStr2);
            
            var inputs = element.find('input');
            //SetFocus時の書式
            inputs.bind('focus', function(evt) {
                var empty = isEmpty(control.$viewValue);
                var id = this.id;
                if (attrs.id2 === this.id) {
                    empty = isEmpty(controlSecond.$viewValue);
                }

                if (empty) {
                    var input;
                    if (attrs.id === id) {
                        input = element.find('input')[0];
                    } else if (attrs.id2 === id) {
                        input = element.find('input')[1];
                    }
                    setLastCaret(input, angular);
                    return;
                }

                if (attrs.id === id) {
                    var viewValue = control.$viewValue;
                    viewValue = viewValue.replace(/[^\d.-]/g, '');
                    control.$viewValue = viewValue;
                    control.$render();

                    var input = element.find('input')[0];
                    setLastCaret(input, angular);
                } else if (attrs.id2 === id) {
                    var viewValue = controlSecond.$viewValue;
                    viewValue = viewValue.replace(/[^\d.-]/g, '');
                    controlSecond.$viewValue = viewValue;
                    controlSecond.$render();

                    var input = element.find('input')[1];
                    setLastCaret(input, angular);
                }
            }).bind('blur', function(evt) {
                scope.$emit(this.id + "Blur");
                if (attrs.id === this.id) {
                    if (angular.isUndefined(control.$modelValue)  || control.$invalid) {
                        return;
                    }
                    // Focus out change from -1,234.5 to -1234.5
                    scope.$apply(function() {
                        var inputValue = control.$viewValue;

                        //get Int part and Frac part of number
                        var intPart = getInt(inputValue);
                        var fracPart = getFrac(inputValue);
                        var dot = inputValue.indexOf('.');
                        if (dot != -1) {
                            if (fracPart.length === 0) {
                                inputValue += '0';
                            }
                            if (intPart == 'NaN') {
                                inputValue = '0' + inputValue;
                            }
                        }
                        
                        if (!regularObj.test(inputValue)) {
                            return inputValue;
                        }
                        
                        var decimalLength = franction;
                        inputValue = inputValue.replace(/[^\d.-]/g, '');
                        viewValue = $filter('number')(inputValue, decimalLength);
                        control.$viewValue = viewValue;
                        control.$render();
                    });
                } else if (attrs.id2 === this.id) {
                    if (angular.isUndefined(controlSecond.$modelValue) || controlSecond.$invalid) {
                        return;
                    }
                    // Focus out change from -1,234.5 to -1234.5
                    scope.$apply(function() {
                        var inputValue = controlSecond.$viewValue;

                        //get Int part and Frac part of number
                        var intPart = getInt(inputValue);
                        var fracPart = getFrac(inputValue);
                        var dot = inputValue.indexOf('.');
                        if (dot != -1) {
                            if (fracPart.length === 0) {
                                inputValue += '0';
                            }
                            if (intPart == 'NaN') {
                                inputValue = '0' + inputValue;
                            }
                        }
                        
                        if (!regularObj.test(inputValue)) {
                            return inputValue;
                        }

                        decimalLength = franction2;
                        inputValue = inputValue.replace(/[^\d.-]/g, '');
                        viewValue = $filter('number')(inputValue, decimalLength);
                        controlSecond.$viewValue = viewValue;
                        controlSecond.$render();
                    });
                }
            });
            
            control.$formatters.push(function(viewValue) {
                //remove error when init again data
                angular.element('#'+attrs.id+' #'+attrs.id).removeClass(CSS_CLASS.error);
                angular.element('#'+attrs.id+' #'+attrs.id).popover('destroy');
                
                return viewValue;
            });
            
            controlSecond.$formatters.push(function(viewValue) {
                //remove error when init again data
                angular.element('#'+attrs.id+' #'+attrs.id2).removeClass(CSS_CLASS.error);
                angular.element('#'+attrs.id+' #'+attrs.id2).popover('destroy');
                
                return viewValue;
            });
            
            // view -> model
            control.$parsers.push(function(viewValue) {
                if (isEmpty(viewValue)) {
                    return viewValue;
                }

                //if viewValue is number
                if (regularObj.test(viewValue)) {
                    //convert string to int for modelValue
                    control.$modelValue = parseFloat(removeSymbol(viewValue, ','));
                    scope.ngModel = parseFloat(removeSymbol(viewValue, ','));
                    return control.$modelValue;
                }
            });

            // model -> view
            control.$formatters.push(function(inputValue) {
                //remove error when init again data
                angular.element('#'+attrs.id+' #'+attrs.id2).removeClass(CSS_CLASS.error);
                angular.element('#'+attrs.id+' #'+attrs.id2).popover('destroy');
                if (isEmpty(inputValue)) {
                    return inputValue;
                }
                
                var decimalLength = franction;
                var viewValue = inputValue + '';
                viewValue = viewValue.replace(/[^\d.-]/g, '');
                viewValue = $filter('number')(viewValue, decimalLength);
                return viewValue;
            });
            
            
            // view -> model
            controlSecond.$parsers.push(function(viewValue) {
                if (isEmpty(viewValue)) {
                    return viewValue;
                }

                //if viewValue is number
                if (regularObj.test(viewValue) && !isEmpty(viewValue)) {
                    //convert string to int for modelValue
                    controlSecond.$modelValue = parseFloat(removeSymbol(viewValue, ','));
                    scope.ngModel2 = parseFloat(removeSymbol(viewValue, ','));
                    return controlSecond.$modelValue;
                }
            });

            // model -> view
            controlSecond.$formatters.push(function(inputValue) {
                //remove error when init again data
                angular.element('#'+attrs.id+' #'+attrs.id2).removeClass(CSS_CLASS.error);
                angular.element('#'+attrs.id+' #'+attrs.id2).popover('destroy');
                if (isEmpty(inputValue)) {
                    return inputValue;
                }
                
                var decimalLength = franction;
                var viewValue = inputValue + '';
                viewValue = viewValue.replace(/[^\d.-]/g, '');
                viewValue = $filter('number')(viewValue, decimalLength);
                return viewValue;
            });

            // show server error message
            scope.errorSt = null;
            scope.errorEd = null;
            scope.$on("ccError", function(event, value) {
                scope.errorSt = null;
                scope.errorEd = null;
                control.$setValidity('ccClientError', true);
                controlSecond.$setValidity('ccClientError', true);
                if (!isEmpty(value)) {
                    angular.forEach(value, function(error, key) {
                        if (angular.equals(error.name, attrs.name) && 
                            angular.equals(error.level, 'E')) {
                            scope.errorSt = error;
                            if (error.clientErrorFlag == true) {
                                control.$setValidity('ccClientError', false);
                            } else {
                                control.$setValidity('ccClientError', true);
                            }
                        }
                        if (angular.equals(error.name, attrs.name2) && 
                            angular.equals(error.level, 'E')) {
                            scope.errorEd = error;
                            if (error.clientErrorFlag == true) {
                                controlSecond.$setValidity('ccClientError', false);
                            } else {
                                controlSecond.$setValidity('ccClientError', true);
                            }
                        }
                    });
                }
            });


            scope.$watch('ngModel', function (value) {
                $timeout(function() {
                    scope.$apply(function() {
                        if (parseFloat(control.$modelValue) <= parseFloat(controlSecond.$modelValue)) {
                            //incase of input right value then format other number.
                            var decimalLength = franction2;
                            controlSecond.$viewValue = $filter('number')(controlSecond.$modelValue, decimalLength);
                            controlSecond.$render(0);
                            controlSecond.$setValidity('number', true);
                            controlSecond.$setValidity('ccValidateGreaterEqual', true);
                            if (!(isEmpty(controlSecond.$viewValue) && 'true' == attrs.ccRequired2)) {
                                angular.element('#'+attrs.id+' #'+attrs.id2).removeClass(CSS_CLASS.error);
                                angular.element('#'+attrs.id+' #'+attrs.id2).popover('destroy');
                            }

                            control.$setValidity('ccValidateLessEqual', true);
                            control.$setValidity('number', true);
                            if (!(isEmpty(control.$viewValue) && 'true' == attrs.ccRequired)) {
                                angular.element('#'+attrs.id+' #'+attrs.id).removeClass(CSS_CLASS.error);
                                angular.element('#'+attrs.id+' #'+attrs.id).popover('destroy');
                            }
                        }
                    });
                }, 10);
            });

            scope.$watch('ngModel2', function (value) {
                $timeout(function() {
                    scope.$apply(function() {
                        if (parseFloat(control.$modelValue) <= parseFloat(controlSecond.$modelValue)) {
                            var decimalLength = franction;
                            control.$viewValue = $filter('number')(control.$modelValue, decimalLength);
                            control.$render(0);
                            
                            controlSecond.$setValidity('number', true);
                            controlSecond.$setValidity('ccValidateGreaterEqual', true);
                            if (!(isEmpty(controlSecond.$viewValue) && 'true' == attrs.ccRequired2)) {
                                angular.element('#'+attrs.id+' #'+attrs.id2).removeClass(CSS_CLASS.error);
                                angular.element('#'+attrs.id+' #'+attrs.id2).popover('destroy');
                            }

                            control.$setValidity('ccValidateLessEqual', true);
                            control.$setValidity('number', true);
                            if (!(isEmpty(control.$viewValue) && 'true' == attrs.ccRequired)) {
                                angular.element('#'+attrs.id+' #'+attrs.id).removeClass(CSS_CLASS.error);
                                angular.element('#'+attrs.id+' #'+attrs.id).popover('destroy');
                            }
                        }
                    });
                }, 10);
            });


            control.$parsers.push(function(viewValue) {
                scope.errorSt = null;
                scope.$emit('ccResetServerClientError', attrs.name);
                return viewValue;
            });
            controlSecond.$parsers.push(function(viewValue) {
                scope.errorEd = null;
                scope.$emit('ccResetServerClientError', attrs.name2);
                return viewValue;
            });
        }
    };
}]);

