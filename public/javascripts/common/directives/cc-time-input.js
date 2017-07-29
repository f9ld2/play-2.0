/**
 * @ngdoc directive
 * @name chasecore.directive:cc-time-input
 * @restrict A
 * @function
 *
 * @description
 * [HH:MM]
 *
 * @example
    <doc:example>
      <doc:source>
         <cc-time-input cc-label="開店時間" cc-partition="3" id="openDate" 
             name="openDate" ng-model="result.openDate" cc-required=true/>
      </doc:source>
      <doc:scenario>
      </doc:scenario>
    </doc:example>
 */
diretiveApp.directive('ccTimeInput', function(CSS_CLASS, $timeout){
  return {
    restrict: 'E',
    require: 'ngModel',
    scope : {
        ngModel: '=',
        ccReadonly : '=',
        ccFocus: '=',
        ccError: '=',
        ccWidth: '='
    },
    template: function (element, attrs) {
        // either readOnly or required
        var inputClass = CSS_CLASS.ime_off;
        var readOnly_required = '';

        if (angular.isDefined(attrs.ccFocus)) {
            readOnly_required += ' cc-focus="ccFocus"';
        }
        if (angular.isDefined(attrs.ccReadonly)) {
            readOnly_required += ' ng-disabled="ccReadonly" ng-class="{\''+ CSS_CLASS.disable + '\': ccReadonly, \'\': !ccReadonly}" ';
        }
        
        if (angular.isDefined(attrs.ccReadonly) && attrs.ccReadonly == 'true') {
            readOnly_required += ' readonly ';
            inputClass += CSS_CLASS.BLANK + CSS_CLASS.disable;
        }

        inputClass += CSS_CLASS.BLANK + CSS_CLASS.center + CSS_CLASS.BLANK + 
            CSS_CLASS.num_5; 

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
        
        if (angular.isDefined(attrs.ccPartition) && attrs.ccPartition == '') {
            htmlText += '<div class="' + CSS_CLASS.label_divide_zero + 
                CSS_CLASS.BLANK + CSS_CLASS.label + '" > ' + attrs.ccLabel + ' </div>';
        } else {
            htmlText += '<div class="'+labelClass+'">'+attrs.ccLabel;
            if (angular.isDefined(attrs.ccRequired) && attrs.ccRequired == 'true') {
                htmlText += '<span class="' + CSS_CLASS.txt_required + '"> *</span>';
            }
        }

        htmlText += '</div>';
        htmlText += '<input onKeyDown="return setNextFocus(this);" type="text" class="' + inputClass + 
            '" cc-type="time" id="' + attrs.id + '" name="' + attrs.name + 
            '" ng-model="ngModel" ' + readOnly_required + ' maxlength="5" ';
        if (!isEmpty(attrs.ccWidth)) {
            htmlText += ' ng-style="{width: ccWidth}"';
        }
        if (attrs.ccReadonly == 'true') {
            htmlText += ' />';
        } else {
            htmlText += ' ng-minlength="1" cc-blur-validation error-message="{{error}}"/>';
        }

        return htmlText; 
    },
    link: function(scope, element, attrs, ctrl) {
        var control = angular.element(element.find('input')).controller('ngModel');
        var input = element.find('input');
        
        //SetFocus時の書式
        input.bind('focus', function(evt) {
            if (!isEmpty(scope.ngModel) && '-1' != checkTime(control.$viewValue)) {
                scope.$apply(function() {
                    var value = checkTime(control.$viewValue);
                    value = removeSymbol(value, ':');
                    control.$viewValue = value;
                    control.$render();
                    
                });
            }
            setLastCaret(element.find('input')[0], angular);
        });
        
        //画面表示時の書式
        input.bind('blur', function(evt) {
            var check = checkTime(control.$viewValue);
            //画面表示時の書式
            scope.$apply(function() {
                // There is a error
                if ('-1' === check || '' === check || angular.isUndefined(check)) {
                    return;
                } else {
                    // model is valid
                    var value = checkTime(control.$modelValue);
                    control.$viewValue = value;
                    control.$render();
                }
            });
            scope.$emit(this.id + "Blur");
        });
        
        //when changed data then remove symbol "/" out viewValue
        control.$parsers.push(function(viewValue) {
            control.$setValidity('ccTime', true);
            if(isEmpty(viewValue)){
                return viewValue;
            }
            var check = checkTime(viewValue);
            if ('-1' === check) {
                control.$setValidity('ccTime', false);
                return viewValue;
            }

            // バインド変数内容
            return removeSymbol(checkTime(viewValue), ":");
        });
        
        // view -> model
        control.$parsers.push(function(viewValue) {
            control.$setValidity('ccTime', true);
            if (isEmpty(viewValue)) {
                return viewValue;
            }

            var check = checkTime(viewValue);
            if ('-1' === check) {
                control.$setValidity('ccTime', false);
                return viewValue;
            } else {
                return removeSymbol(check, ":");
            }
        });

        // model -> view
        control.$formatters.push(function(inputValue) {
            //remove error when init again data
            angular.element('#'+attrs.id+' #'+attrs.id).removeClass(CSS_CLASS.error);
            angular.element('#'+attrs.id+' #'+attrs.id).popover('destroy');
            control.$setValidity('ccTime', true);
            
            if (isEmpty(inputValue)) {
                return inputValue;
            }

            var check = checkTime(inputValue);
            if ('-1' === check) {
                control.$setValidity('ccTime', false);
                return checkFormatTime(inputValue);
            } else {
                return check;
            }
        });

        scope.error = null;
        scope.$on("ccError", function(event, value) {
            scope.error = null;
            control.$setValidity('ccClientError', true);
            if (!isEmpty(value)) {
                angular.forEach(value, function(error, key) {
                    if (angular.equals(error.name, attrs.name) && 
                        angular.equals(error.level, 'E')) {
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
            scope.$emit('ccResetServerClientError', attrs.name);
            return viewValue;
        });
    }
  }
});

