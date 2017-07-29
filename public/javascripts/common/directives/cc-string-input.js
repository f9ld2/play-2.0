/**
 * @ngdoc directive
 * @name chasecore.directive:cc-string-input
 * @restrict A
 * @function
 *
 * @description
 * Format value for type : 全角 (S1) ,半角  (S2) ,郵便 (S3) ,電話 (S4) , MAIL (S5) ,URL (S6)
 * 
 * @example
    <doc:example>
      <doc:source>
         <cc-string-input cc-label="店舗名称" cc-partition="3" id="storeName" 
             name="storeName" ng-model="cond.storeName" cc-required=true 
             ng-minlength="1" ng-maxlength="40" cc-chartype="S1"/>
      </doc:source>
      <doc:scenario>
      </doc:scenario>
    </doc:example>
 */
diretiveApp.directive('ccStringInput', function(CSS_CLASS, $timeout, $rootScope) {
    return {
        restrict: 'E',
        require: 'ngModel',
        scope: {
            ngModel: '=',
            ccReadonly: '=',
            ccFocus: '=', // ThanhDH - 22/03/2014
            ccError: '=',
            ccWidth: '='
        },
        template: function(element, attrs) {
            // either readOnly or required
            var inputClass = CSS_CLASS.BLANK; //CSS_CLASS.ime_auto;
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
                htmlText += '<div class="' + CSS_CLASS.label_divide_zero + CSS_CLASS.BLANK + CSS_CLASS.label + '" >' +
                    attrs.ccLabel + '</div>';
            } else {
                htmlText += '<div class="' + labelClass + '">' + attrs.ccLabel;
                if (angular.isDefined(attrs.ccRequired) && attrs.ccRequired == 'true') {
                    htmlText += '<span class="' + CSS_CLASS.txt_required + '"> *</span>';
                }
            }
            htmlText += '</div>';

            //パラメータから最大桁数を加工する。
            var maxLength = '';

            // 入力属性の内容に従って以下を設定する。
            var ccType = '';
            var type = 'text';
            if (angular.isDefined(attrs.ccChartype)) {
                if (attrs.ccChartype == 'S1') { //全角
                    ccType = 'zenkaku';

                    //ng-maxlength := ng-maxlength / 2
                    maxLength = Number(attrs.ngMaxlength) / 2;
                    inputClass += CSS_CLASS.BLANK + CSS_CLASS.ime_on;
                } else if (attrs.ccChartype == 'S2') { //半角
                    ccType = 'hankaku';
                    maxLength = attrs.ngMaxlength;
                    inputClass += CSS_CLASS.BLANK + CSS_CLASS.ime_on;
                } else if (attrs.ccChartype == 'S3') { //郵便
                    ccType = 'postal';
                    maxLength = attrs.ngMaxlength;
                    inputClass += CSS_CLASS.BLANK + CSS_CLASS.ime_off;
                } else if (attrs.ccChartype == 'S4') { //電話
                    ccType = 'phone';
                    maxLength = attrs.ngMaxlength;
                    inputClass += CSS_CLASS.BLANK + CSS_CLASS.ime_off;
                } else if (attrs.ccChartype == 'S5') { //MAIL
                    type = 'email';
                    ccType = 'email';
                    maxLength = attrs.ngMaxlength;
                    inputClass += CSS_CLASS.BLANK + CSS_CLASS.ime_off;
                } else if (attrs.ccChartype == 'S6') { //URL
                    type = 'url';
                    ccType = 'url';
                    maxLength = attrs.ngMaxlength;
                    inputClass += CSS_CLASS.BLANK + CSS_CLASS.ime_off;
                } else {
                    type = 'text';
                    ccType = '';
                    maxLength = attrs.ngMaxlength;
                    inputClass += CSS_CLASS.BLANK;
                }
            }

            htmlText += '<input onKeyDown="return setNextFocus(this);" type="' + type + '" class="' + inputClass + '" cc-type="' + ccType + '" id="' + attrs.id +
                '" name="' + attrs.name + '" ng-model="ngModel" ' + readOnly_required;

            if (angular.isDefined(attrs.ccChartype)) {
                if (attrs.ccChartype == 'S1') { //全角
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
            var control = angular.element(element.find('input')).controller('ngModel');

            //init
            if (!isEmpty(scope.ngModel)) {
                //remove error when init
                element.removeClass(CSS_CLASS.error);
                angular.element(element).popover('destroy');

                control.$setViewValue(scope.ngModel);
                control.$render();
            }

            //SetFocus時の書式
            element.find('input').bind('focus', function(evt) {
                var input = element.find('input')[0];
                setLastCaret(input, angular);
            });

            element.find('input').bind('blur', function(evt) { //画面表示時の書式
                if (attrs.ccChartype == 'S3') {
                    var inputVal = scope.ngModel;
                    if (!isEmpty(inputVal) && (inputVal.indexOf('-') < 0 && inputVal.length > 3)) {
                        var preVal = inputVal.substring(0, 3);
                        var subVal = inputVal.substring(3);
                        var result = preVal + '-' + subVal;
                        scope.ngModel = result;

                        control.$setViewValue(result);
                        control.$render();
                    }
                }
                scope.$emit(this.id + "Blur");
            });

            scope.$on('$destroy', function() {
                element.find('input').unbind();
                element.find('input').off();
            })

            control.$parsers.push(function(viewValue) {
                if (attrs.ccChartype == 'S1') { //全角
                    control.$setValidity('ccZenkaku', true);
                    if (isEmpty(viewValue)) {
                        return viewValue;
                    }
                    if (!isZenkakuOrHankaku(viewValue, true)) {
                        control.$setValidity('ccZenkaku', false);
                    }
                } else if (attrs.ccChartype == 'S2') { //半角
                    control.$setValidity('ccHankaku', true);
                    if (isEmpty(viewValue)) {
                        return viewValue;
                    }
                    if (!isZenkakuOrHankaku(viewValue, false)) {
                        control.$setValidity('ccHankaku', false);
                    }
                } else if (attrs.ccChartype == 'S3') { //郵便
                    control.$setValidity('ccPostal', true);
                    if (isEmpty(viewValue)) {
                        return viewValue;
                    }
                    //[000-0000]の形式(半角数字３桁 + "-" + 半角数字４桁)
                    var reg = /^\d{3}(\-)?\d{4}$/;
                    if (!reg.test(viewValue)) {
                        control.$setValidity('ccPostal', false);
                    }
                } else if (attrs.ccChartype == 'S4') { //電話
                    control.$setValidity('ccPhone', true);
                    if (isEmpty(viewValue)) {
                        return viewValue;
                    }
                    //[0-9]で始まって[0-9]で終わり、途中に0～n個の[-]を許可するが、[-]の連続入力は禁止
                    var indexHyphun = viewValue.indexOf('-');
                    var indexDoubleHyphun = viewValue.indexOf('--');
                    var lastHyphun = viewValue.lastIndexOf('-');
                    if (0 == indexHyphun || indexDoubleHyphun >= 0 || lastHyphun == (viewValue.length - 1)) {
                        control.$setValidity('ccPhone', false);
                    }
                }

                // バインド変数内容
                return viewValue;
            });

            control.$formatters.push(function(viewValue) {
                //remove error when init again data
                angular.element('#' + attrs.id + ' #' + attrs.id).removeClass(CSS_CLASS.error);
                angular.element('#' + attrs.id + ' #' + attrs.id).popover('destroy');

                if (attrs.ccChartype == 'S1') { //全角
                    control.$setValidity('ccZenkaku', true);
                    if (isEmpty(viewValue)) {
                        return viewValue;
                    }
                    if (!isZenkakuOrHankaku(viewValue, true)) {
                        control.$setValidity('ccZenkaku', false);
                    }
                } else if (attrs.ccChartype == 'S2') { //半角
                    control.$setValidity('ccHankaku', true);
                    if (isEmpty(viewValue)) {
                        return viewValue;
                    }
                    if (!isZenkakuOrHankaku(viewValue, false)) {
                        control.$setValidity('ccHankaku', false);
                    }
                } else if (attrs.ccChartype == 'S3') { //郵便
                    control.$setValidity('ccPostal', true);
                    if (isEmpty(viewValue)) {
                        return viewValue;
                    }
                    //[000-0000]の形式(半角数字３桁 + "-" + 半角数字４桁)
                    var reg = /^\d{3}(\-)?\d{4}$/;
                    if (!reg.test(viewValue)) {
                        control.$setValidity('ccPostal', false);
                    }
                } else if (attrs.ccChartype == 'S4') { //電話
                    control.$setValidity('ccPhone', true);
                    if (isEmpty(viewValue)) {
                        return viewValue;
                    }
                    //[0-9]で始まって[0-9]で終わり、途中に0～n個の[-]を許可するが、[-]の連続入力は禁止
                    var indexHyphun = viewValue.indexOf('-');
                    var indexDoubleHyphun = viewValue.indexOf('--');
                    var lastHyphun = viewValue.lastIndexOf('-');
                    if (0 == indexHyphun || indexDoubleHyphun >= 0 || lastHyphun == (viewValue.length - 1)) {
                        control.$setValidity('ccPhone', false);
                    }
                }

                return viewValue;
            });
            // lochv start 13/3/2014 : show server error message
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
                $rootScope.$broadcast('ccResetServerClientError', attrs.name);
                return viewValue;
            });
            // lochv end 13/3/2014
        }
    }
});