/**
 * @ngdoc directive
 * @name chasecore.directive:cc-string-textarea
 * @restrict A
 * @function
 *
 * @description
 * Format value for type : 全角 (S1) ,半角  (S2) 
 * 
 * @example
    <doc:example>
      <doc:source>
         <cc-string-textarea cc-label="店舗名称" cc-partition="3" id="storeName" 
             name="storeName" ng-model="cond.storeName" cc-required=true 
             ng-minlength="1" ng-maxlength="40" cc-chartype="S1"/>
      </doc:source>
      <doc:scenario>
      </doc:scenario>
    </doc:example>
 */
diretiveApp.directive('ccStringTextarea', function(CSS_CLASS, $timeout) {
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

            var inputClass = CSS_CLASS.BLANK;
            var readOnly_required = '';


            if (angular.isDefined(attrs.ccFocus)) {
                readOnly_required += ' cc-focus="ccFocus"';
            }


            if (angular.isDefined(attrs.ccReadonly)) {
                readOnly_required += ' ng-disabled="ccReadonly" ng-class="{\'' + CSS_CLASS.disable + '\': ccReadonly, \'\': !ccReadonly}" ';
            }

            if (angular.isDefined(attrs.ccReadonly) && attrs.ccReadonly == 'true') {
                readOnly_required += ' readonly ';
                inputClass += CSS_CLASS.disable;
            }

            if (angular.isDefined(attrs.ccRequired) && attrs.ccRequired == 'true') {
                readOnly_required += ' required ';
            }


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


            var htmlText = '';
            if (angular.isDefined(attrs.ccPartition) && attrs.ccPartition == '') {
                htmlText += '<div class="' + CSS_CLASS.label_divide_zero + CSS_CLASS.BLANK + CSS_CLASS.label + '" >' +
                    attrs.ccLabel + '</div>';
            } else {
                htmlText += '<div class="' + labelClass + '">' + attrs.ccLabel;
                if (angular.isDefined(attrs.ccRequired) && attrs.ccRequired == 'true') {
                    htmlText += '<span class="' + CSS_CLASS.txt_required + '"> *</span>';
                }
            }
            htmlText += '</div>';


            var maxLength = '';


            var ccType = '';
            if (angular.isDefined(attrs.ccChartype)) {
                if (attrs.ccChartype == 'S1') {
                    ccType = 'zenkaku';
                    maxLength = Number(attrs.ngMaxlength) / 2;
                    inputClass += CSS_CLASS.BLANK + CSS_CLASS.ime_on;
                } else if (attrs.ccChartype == 'S2') {
                    ccType = 'hankaku';
                    maxLength = attrs.ngMaxlength;
                    inputClass += CSS_CLASS.BLANK + CSS_CLASS.ime_on;
                } else {
                    ccType = '';
                    maxLength = attrs.ngMaxlength;
                    inputClass += CSS_CLASS.BLANK;
                }
            }

            htmlText += '<textarea ' + '" class="' + inputClass + '" cc-type="' + ccType + '" id="' + attrs.id +
                '" name="' + attrs.name + '" ng-model="ngModel" ' + readOnly_required;

            if (angular.isDefined(attrs.ccChartype)) {
                if (attrs.ccChartype == 'S1') {
                    htmlText += ' ng-maxlength="' + maxLength + '" ';
                } else {
                    htmlText += ' maxlength="' + maxLength + '" ';
                }
            }

            if (!isEmpty(attrs.ccWidth)) {
                htmlText += ' ng-style="{width: ccWidth}"';
            }

            if (attrs.ccReadonly == 'true') {
                htmlText += ' />';
            } else {
                htmlText += ' ng-minlength="' + attrs.ngMinlength + '" ';
                htmlText += ' cc-blur-validation error-message="{{error}}"/>';
            }

            return htmlText;
        },
        link: function(scope, element, attrs, ctrl) {
            var control = angular.element(element.find('textarea')).controller('ngModel');


            if (!isEmpty(scope.ngModel)) {

                element.removeClass(CSS_CLASS.error);
                angular.element(element).popover('destroy');

                control.$setViewValue(scope.ngModel);
                control.$render();
            }


            element.find('textarea').bind('focus', function(evt) {

                var input = element.find('textarea')[0];
                setLastCaret(input, angular);

            });

            element.find('textarea').bind('blur', function(evt) {
                if (attrs.ccChartype == 'S3') {}
            });

            control.$parsers.push(function(viewValue) {
                if (attrs.ccChartype == 'S1') {
                    control.$setValidity('ccZenkaku', true);
                    if (isEmpty(viewValue)) {
                        return viewValue;
                    }
                    if (!isZenkakuOrHankaku(viewValue, true)) {
                        control.$setValidity('ccZenkaku', false);
                    }
                } else if (attrs.ccChartype == 'S2') {
                    control.$setValidity('ccHankaku', true);
                    if (isEmpty(viewValue)) {
                        return viewValue;
                    }
                    if (!isZenkakuOrHankaku(viewValue, false)) {
                        control.$setValidity('ccHankaku', false);
                    }
                }

                return viewValue;
            });

            control.$formatters.push(function(viewValue) {

                angular.element('#' + attrs.id + ' #' + attrs.id).removeClass(CSS_CLASS.error);
                angular.element('#' + attrs.id + ' #' + attrs.id).popover('destroy');

                if (attrs.ccChartype == 'S1') {
                    control.$setValidity('ccZenkaku', true);
                    if (isEmpty(viewValue)) {
                        return viewValue;
                    }
                    if (!isZenkakuOrHankaku(viewValue, true)) {
                        control.$setValidity('ccZenkaku', false);
                    }
                } else if (attrs.ccChartype == 'S2') {
                    control.$setValidity('ccHankaku', true);
                    if (isEmpty(viewValue)) {
                        return viewValue;
                    }
                    if (!isZenkakuOrHankaku(viewValue, false)) {
                        control.$setValidity('ccHankaku', false);
                    }
                }

                return viewValue;
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