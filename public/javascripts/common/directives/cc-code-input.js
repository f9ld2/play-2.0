/**
 * @ngdoc directive
 * @name chasecore.directive:cc-code-input
 * @restrict A
 * @function
 *
 * @description
 * [数字][英字][英数]
 *
 * @example
    <doc:example>
      <doc:source>
         <cc-code-input cc-label="店長コード" cc-partition="3" id="storeMgtCd" name="storeMgrCd" ng-model="result.storeMgrCd" cc-required=true ng-minlength="9" ng-maxlength="9" cc-chartype="C1"/>
      </doc:source>
      <doc:scenario>
      </doc:scenario>
    </doc:example>
 */
diretiveApp.directive('ccCodeInput', function(CSS_CLASS, $compile, $timeout) {
    return {
        restrict: 'E',
        require: ['ngModel'],
        scope: {
            ngModel: '=',
            ccFocus: '=',
            ccReadonly: '=',
            ccError: '=',
            ccWidth: '='
        },
        template: function(element, attrs) {
            // either readOnly or required
            var inputClass = '';
            var readOnlyRequired = '';
            if (angular.isDefined(attrs.ccFocus)) {
                readOnlyRequired += ' cc-focus="ccFocus"';
            }
            if (angular.isDefined(attrs.ccReadonly)) {
                readOnlyRequired += ' ng-disabled="ccReadonly" ng-class="{\'' + CSS_CLASS.disable + '\': ccReadonly, \'\': !ccReadonly}" ';
            }

            if (attrs.ccReadonly != undefined && attrs.ccReadonly == 'true') {
                readOnlyRequired += 'readonly';
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
                if (angular.isDefined(attrs.ccRequired) && attrs.ccRequired == 'true') {
                    labelClass += CSS_CLASS.cs_label_large_required;
                    //                    readOnlyRequired = 'required';
                } else {
                    labelClass += CSS_CLASS.cs_label_large;
                }
            }
            labelClass += CSS_CLASS.BLANK + CSS_CLASS.label;

            // char type
            var ccType = '';
            if (attrs.ccChartype != undefined) {
                if (attrs.ccChartype == 'C1') {
                    ccType = 'code';
                } else if (attrs.ccChartype == 'C2') {
                    ccType = 'alphabet';
                } else if (attrs.ccChartype == 'C3') {
                    ccType = 'alphanum';
                } else {
                    // throw exception
                    throw new TypeError('\'' + attrs.ccChartype + '\' not char type of cc-code-input');
                }
            }
            // html text
            var htmlText = '';
            if (attrs.ccPartition == "") {
                htmlText += '<div class="' + labelClass + '">' + attrs.ccLabel;
            } else {
                htmlText += '<div class="' + labelClass + '">' + attrs.ccLabel;
                if (attrs.ccRequired != undefined && attrs.ccRequired == 'true') {
                    htmlText += '<span class="' + CSS_CLASS.txt_required + '"> *</span>';
                }
            }
            htmlText += '</div>';

            htmlText += '<input onKeyDown="return setNextFocus(this);" type="text" class="' + inputClass + '" cc-type="' + ccType + '" id="' + attrs.id + '" name="' + attrs.name + '" ng-model="ngModel" ' + readOnlyRequired + ' ng-minlength="' + attrs.ngMinlength + '" maxlength="' + attrs.ngMaxlength + '"';
            htmlText += ' cc-blur-validation error-message="{{error}}" ';
            if (!isEmpty(attrs.ccWidth)) {
                htmlText += ' ng-style="{width: ccWidth}" ';
            }
            htmlText += ' />';

            return htmlText;
        },
        link: function(scope, element, attrs, ctrl) {
            var inputCtrl = angular.element(element.find('input')).controller('ngModel');
            if (ctrl == undefined) {
                // throw exception
                throw new TypeError('need ng-model to bind data');
            }

            //SetFocus時の書式
            element.find('input').bind('focus', function(evt) {
                var input = element.find('input')[0];
                setLastCaret(input, angular);
            }).bind('blur', function(evt) {
                scope.$emit(this.id + "Blur");
            }).bind('click', function() {
                scope.$emit(attrs.id + "Click");
            });

            scope.$on('$destroy', function() {
                element.find('input').unbind();
                element.find('input').off();
            })

            inputCtrl.$formatters.push(function(viewValue) {
                //remove error when init again data
                angular.element('#' + attrs.id + ' #' + attrs.id).removeClass(CSS_CLASS.error);
                angular.element('#' + attrs.id + ' #' + attrs.id).popover('destroy');
                return viewValue;
            });

            scope.$on(attrs.id + "searchBoxClickedOutter", function(event, data) {
                inputCtrl.$setViewValue(data);
                inputCtrl.$render();
                element.find('input').eq(0).focus();
            });

            // lochv start 13/3/2014 : show server error message
            scope.error = null;
            scope.$on("ccError", function(event, value) {
                scope.error = null;
                inputCtrl.$setValidity('ccCodeInput', true);
                if (!isEmpty(value)) {
                    angular.forEach(value, function(error, key) {
                        if (angular.equals(error.name, attrs.name) &&
                            angular.equals(error.level, 'E')) {
                            scope.error = error;
                            if (error.clientErrorFlag == true) {
                                inputCtrl.$setValidity('ccCodeInput', false);
                            } else {
                                inputCtrl.$setValidity('ccCodeInput', true);
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
            // lochv end 13/3/2014
        }
    };
});