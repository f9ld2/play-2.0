/**
 * @ngdoc directive
 * @name chasecore.directive:cc-number-input
 * @restrict A
 * @function
 *
 * @description
 * [-,--9]、[Z,ZZ9]、[-,--9.9]、[Z,ZZ9.9]
 *
 * @example
    <doc:example>
      <doc:source>
         <cc-number-input cc-label="原単価" cc-partition="3" id="genka" name="genka" ng-model="result.genka" cc-required=true cc-min="-9999.9" cc-max="9999.9"/>
      </doc:source>
      <doc:scenario>
      </doc:scenario>
    </doc:example>
 */
diretiveApp.directive ('ccNumberInput', ['$filter', 'CSS_CLASS', '$timeout', '$rootScope', function ($filter, CSS_CLASS, $timeout, $rootScope) {

    /**
     * Get cc-sign attribute
     */
    function getSigned(minValue, maxValue) {
        if (angular.isUndefined(minValue) || angular.isUndefined(maxValue)) {
            return '';
        }

        if (minValue.indexOf("-") == -1 && maxValue.indexOf("-") == -1) {
            return '';
        } else if (minValue.indexOf("-") != -1 || maxValue.indexOf("-") != -1) {
            return 'cc-signed';
        }

        return '';
    }

    return {
        restrict : 'E',
        require : 'ngModel',
        scope : {
            ngModel : '=',
            ccReadonly: '=',
            ccError: '=',
            ccFocus: '=',
            ccWidth: '='
        },
        template : function(element, attrs) {
            var inputClass = CSS_CLASS.ime_off + CSS_CLASS.BLANK;
            inputClass += CSS_CLASS.right + CSS_CLASS.BLANK; 
            // either readOnly or required
            var readOnlyOrRequired = '';
            if (angular.isDefined(attrs.ccFocus)) {
            	readOnlyOrRequired += ' cc-focus="ccFocus"';
            }
            if (angular.isDefined(attrs.ccReadonly)) {
            	readOnlyOrRequired += ' ng-disabled="ccReadonly" ng-class="{\''+ CSS_CLASS.disable + '\': ccReadonly, \'\': !ccReadonly}" ';
            }
            
            if (attrs.ccReadonly != undefined && attrs.ccReadonly == 'true') {
                readOnlyOrRequired += ' readonly' ;
                inputClass += CSS_CLASS.BLANK + CSS_CLASS.disable;
            }
    
            if (angular.isDefined(attrs.ccRequired) && attrs.ccRequired == 'true') {
                readOnlyOrRequired += ' required ';
            }
            
            // style for label
            var labelClass = '';
            if (attrs.ccPartition != undefined && attrs.ccPartition == '3') {
                if (attrs.ccRequired != undefined && attrs.ccRequired == 'true') {
                    labelClass += CSS_CLASS.cs_label_large_required;
                } else {
                    labelClass += CSS_CLASS.cs_label_large;
                }
            } else if (attrs.ccPartition != undefined && attrs.ccPartition == '') {
                labelClass += CSS_CLASS.label_divide_zero;
            }
            labelClass += ' ' + CSS_CLASS.label;

            // 符号要否(cc-signed)
            var signedStr = getSigned(attrs.ccMin, attrs.ccMax);

            // 整数部の桁数(cc-digits-integer)
            var digitsInteger = getMaxIntLength(attrs.ccMin, attrs.ccMax);

            // 小数部の桁数(cc-digits-franction)
            var digitsFranction = getMaxFracLength(attrs.ccMin, attrs.ccMax);

            // 最大桁数(ng-maxlength)
            var maxlength = getMaxLength(attrs.ccMin, attrs.ccMax);
   
            // html text
            var htmlText = '';
            htmlText += '<div class="' + labelClass + '">' + attrs.ccLabel;
            if (attrs.ccRequired != undefined && attrs.ccRequired == 'true' && !isEmpty(attrs.ccPartition)) {
                htmlText += '<span class="' + CSS_CLASS.txt_required + '"> *</span>';
            }

            htmlText += '</div>';
            htmlText += '<input onKeyDown="return setNextFocus(this);" error-message="{{error}}" ' 
                    + 'type="text" cc-type="number" id="' + attrs.id + '" name="' + attrs.name
                    + '" ng-model="ngModel" ' + readOnlyOrRequired + ' maxlength="' + maxlength + '"';
            htmlText += ' cc-blur-validation ';
            if (!isEmpty(attrs.ccWidth)) {
                htmlText += ' ng-style="{width: ccWidth}"';
            }
            if (digitsInteger != -1) {
                htmlText += ' cc-digits-integer="' + digitsInteger + '"';
            }
            
            if (digitsFranction != -1) {
                htmlText += ' cc-digits-franction="' + digitsFranction + '"';
            }

            if (signedStr != '') {
                htmlText += ' ' + signedStr;
            }

            var width = '';
            if (!isEmpty(attrs.ccWidth)) {
                width = 'ng-style="{width:' + attrs.ccWidth + '}"';
            } else {
                // caculate width
                if (maxlength >= 1 && maxlength <= 5) {
                    inputClass += CSS_CLASS.num_5;
                } else if (maxlength >= 6 && maxlength <= 10) {
                    inputClass += CSS_CLASS.num_10;
                } else if (maxlength >= 11 && maxlength <= 15) {
                    inputClass += CSS_CLASS.num_15;
                } else if (maxlength >= 16 && maxlength <= 20) {
                    inputClass += CSS_CLASS.num_20;
                } else if (maxlength >= 21 && maxlength <= 30) {
                    inputClass += CSS_CLASS.num_30;
                } else if (maxlength >= 31 && maxlength <= 40) {
                    inputClass += CSS_CLASS.num_40;
                } else if (maxlength >= 41 && maxlength <= 50) {
                    inputClass += CSS_CLASS.num_50;
                }
            }
            
            htmlText += ' class="' + inputClass + '" ' + width;

            if (attrs.ccReadonly == 'true') {
                htmlText += ' />';
            } else {
                if (!angular.isUndefined(attrs.ccMin)) {
                    htmlText += ' cc-min="' + attrs.ccMin + '"';
                }
                
                if (!angular.isUndefined(attrs.ccMax)) {
                    htmlText += '  cc-max="' + attrs.ccMax + '"';
                }
                htmlText += ' ng-minlength="1" />';
            }
   
            return htmlText;
        },
        link: function (scope, element, attrs, ctrl) {
            var control = angular.element(element.find('input')).controller('ngModel');
            var signedStr = getSigned(attrs.ccMin, attrs.ccMax);
            var digitsFranction = getMaxFracLength(attrs.ccMin, attrs.ccMax);
            var FLOAT_REGEXP = new RegExp("^[-+]?([\\d\\,]+(\\.\\d{0,1})?)$");
            var regularStr = "";
            if (!isEmpty(signedStr)) {
                regularStr = "^[-+]?([\\d\\,]+(\\.\\d{0,1})?)$";
            } else {
                regularStr = "^([\\d\\,]+(\\.\\d{0,1})?)$";
            }

            var franction = digitsFranction != -1 ? digitsFranction : '0';
            regularStr = regularStr.replace('1', franction);
            var regularObj = new RegExp(regularStr);

            // Focus in change from -1,234.5 to -1234.5
            element.find('input').bind('focus', function() {
                var empty = isEmpty(control.$viewValue);
                if (empty) {
                    var input = element.find('input')[0];
                    setLastCaret(input, angular);
                    return;
                }

                scope.$apply(function() {
                    var viewValue = control.$viewValue;
                    viewValue = viewValue.replace(/[^\d.-]/g, '');
                    control.$viewValue = viewValue;
                    control.$render();
                });

                var input = element.find('input')[0];
                setLastCaret(input, angular);
            }).bind('blur', function() {

                scope.$emit(this.id + "Blur");
                if (angular.isUndefined(control.$modelValue) || control.$invalid) {
                    return;
                }
                // Focus out change from -1,234.5 to -1234.5
                scope.$apply(function() {
                    var inputValue = control.$viewValue;
                    //get Int part and Frac part of number
                    var intPart = getInt(inputValue);
                    var fracPart = getFrac(inputValue);
                    var dot = -1; 
                    if (inputValue != null) {
                        inputValue.indexOf('.');
                    }

                    if (dot != -1) {
                        if (fracPart.length === 0) {
                            inputValue += '0';
                        }
                        if (intPart == 'NaN') {
                            inputValue = '0' + inputValue;
                        }
                    }
                    
                    if (!regularObj.test(inputValue)) {
                        //Fix bug begin: lost value when input wrong format
                        return inputValue;
                    }
                    
                    var decimalLength = franction;

                    inputValue = inputValue.replace(/[^\d.-]/g, '');
                    viewValue = $filter('number')(inputValue, decimalLength);
                    control.$viewValue = viewValue;
                    control.$render();
                });
            });
            
            control.$formatters.push(function(viewValue) {
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
                if (isEmpty(inputValue)) {
                    return inputValue;
                }
                
                var decimalLength = franction;
                var viewValue = inputValue + '';
                viewValue = viewValue.replace(/[^\d.-]/g, '');
                viewValue = $filter('number')(viewValue, decimalLength);
                return viewValue;
            });


            scope.error = null;
            scope.$on("ccError", function(event, value) {
                scope.error = null;
                control.$setValidity('ccClientError', true);
                if (!isEmpty(value)) {
                    angular.forEach(value, function(error, key) {
                        if (angular.equals(error.name, attrs.name) && 
                            (angular.equals(error.level, 'E')||angular.equals(error.level, 'W'))) {
                            scope.error = error;
                            if (error.clientErrorFlag == true) {
                                control.$setValidity('ccClientError', false);
                            } else {
                                control.$setValidity('ccClientError', true);
                            }
                        }
                    });
                }
            });

            control.$parsers.push(function(viewValue) {
                scope.error = null;
                $rootScope.$broadcast('ccResetServerClientError', attrs.name);
                return viewValue;
            });
        }
    };
}]);