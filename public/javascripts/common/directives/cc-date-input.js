/**
 * @ngdoc directive
 * @name chasecore.directive:cc-date-input
 * @restrict A
 * @function
 *
 * @description
 * [YYYY/MM/DD]
 *
 * @example
    <doc:example>
      <doc:source>
         <cc-date-input cc-label="開店日" cc-partition="3" id="openDate" 
             name="openDate" ng-model="result.openDate" cc-required=true/>
      </doc:source>
      <doc:scenario>
      </doc:scenario>
    </doc:example>
 */
diretiveApp.directive('ccDateInput', function(CSS_CLASS, $timeout, $compile) {
    return {
        restrict: 'E',
        require: 'ngModel',
        scope: {
            ngModel: '=',
            ccReadonly: '=',
            ccFocus: '=',
            ccError: '=',
            ccWidth: '='
        },
        template: function(element, attrs) {
            // either readOnly or required
            var inputClass = CSS_CLASS.ime_off;
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

            inputClass += CSS_CLASS.BLANK + CSS_CLASS.center + CSS_CLASS.BLANK +
                CSS_CLASS.num_10;

            // style for label
            var labelClass = '';
            if (angular.isDefined(attrs.ccPartition) && attrs.ccPartition == '') {
                labelClass += CSS_CLASS.label_divide_zero;
            } else {
                if (angular.isDefined(attrs.ccRequired) && attrs.ccRequired == 'true') {
                    labelClass += CSS_CLASS.cs_label_large_required;
                    // readOnly_required += ' required '; // Lochv edit
                } else {
                    labelClass += CSS_CLASS.cs_label_large;
                }
            }
            labelClass += CSS_CLASS.BLANK + CSS_CLASS.label;

            // html text
            var htmlText = '';

            if (angular.isDefined(attrs.ccPartition) && attrs.ccPartition == '') {
                htmlText += '<div class="' + CSS_CLASS.label_divide_zero +
                    CSS_CLASS.BLANK + CSS_CLASS.label + '" >' + attrs.ccLabel + '</div>';
            } else {
                htmlText += '<div class="' + labelClass + '">' + attrs.ccLabel;
                if (angular.isDefined(attrs.ccRequired) && attrs.ccRequired == 'true') {
                    htmlText += '<span class="' + CSS_CLASS.txt_required + '"> *</span>';
                }
            }

            htmlText += '</div>';
            htmlText += '<input error-message="{{error}}" type="text" class="' + inputClass +
                '" cc-type="date" id="' + attrs.id + '" name="' + attrs.name +
                '" ng-model="ngModel" ' + readOnly_required +
                ' maxlength="10" ';
            if (!isEmpty(attrs.ccWidth)) {
                htmlText += ' ng-style="{width: ccWidth}"';
            }
            if (attrs.ccReadonly == 'true') {
                htmlText += ' />';
            } else {
                htmlText += ' ng-minlength="1" cc-blur-validation-textbox />';
            }

            return htmlText;
        },
        link: function(scope, element, attrs, ctrl) {
            var control = angular.element(element.find('input')).controller('ngModel');
            var input = element.find('input');
            var promise;

            promise = $timeout(function() {
                if (!angular.equals(input.attr('id'), attrs.id)) {
                    input.attr('id', attrs.id);
                }
                if (!angular.equals(input.attr('name'), attrs.name)) {
                    input.attr('name', attrs.name);
                }
                $(function() {
                    input.datepicker({
                        changeYear: true,
                        changeMonth: true,
                        dateFormat: 'yy/mm/dd',
                        maxDate: new Date("2999/12/31"),
                        yearRange: '1900:2999',
                        onSelect: function(dateText, inst) {
                            // $timeout(function() {
                                scope.ngModel = removeSymbol(dateText, "/");
                                input.focus();
                                input.select();
                            // });
                        }
                    });
                });
            });



            //SetFocus時の書式
            input.bind('focus', function(evt) {
                if (!isEmpty(scope.ngModel) && '-1' != checkDate(control.$viewValue)) {
                    scope.$apply(function() {
                        var value = checkDate(control.$viewValue);
                        value = removeSymbol(value, '/');
                        control.$viewValue = value;
                        control.$render();
                    });
                }
                setLastCaret(element.find('input')[0], angular);
            });

            //画面表示時の書式
            input.bind('blur', function(evt) {
                var check = checkDate(control.$viewValue);
                //画面表示時の書式
                scope.$apply(function() {
                    // There is a error
                    if ('-1' === check || '' === check || angular.isUndefined(check)) {
                        return;
                    } else {
                        // model is valid
                        var value = checkDate(control.$modelValue);
                        control.$viewValue = value;
                        control.$render();
                    }
                });
                scope.$emit(this.id + "Blur");

            });

            scope.$on('$destroy', function() {
                input.datepicker("destroy");
                $timeout.cancel(promise);
                element.find('input').unbind();
                element.find('input').off();
            })

            scope.$on("pressF2Key", function(event, data) {
                input.datepicker( "hide" );
            });
            scope.$on("pressF3Key", function(event, data) {
                input.datepicker( "hide" );
            });
            scope.$on("select2-open", function(event, data) {
                input.datepicker( "hide" );
            });


            // view -> model
            control.$parsers.push(function(viewValue) {
                scope.$emit('ccResetServerClientError', attrs.name);
                scope.$broadcast("resetServerError");
                control.$setValidity('ccDate', true);
                if (isEmpty(viewValue)) {
                    return viewValue;
                }

                var check = checkDate(viewValue);
                if ('-1' === check) {
                    control.$setValidity('ccDate', false);
                    return undefined;
                } else {
                    return removeSymbol(check, "/");
                }
            });

            // model -> view
            control.$formatters.push(function(inputValue) {
                //remove error when init again data
                scope.$broadcast("resetServerError");
                control.$setValidity('ccDate', true);

                if (isEmpty(inputValue)) {
                    return inputValue;
                }

                var check = checkDate(inputValue);
                if ('-1' === check) {
                    control.$setValidity('ccDate', false);
                    return getFormatDate(inputValue);
                } else {
                    angular.element('#' + attrs.id + ' #' + attrs.id).removeClass(CSS_CLASS.input_common_error);
                    return check;
                }
            });
        }
    }
});