/**
 * @ngdoc directive
 * @name chasecore.directive:cc-fomto-time-input
 * @restrict A
 * @function
 *
 * @description
 * [HH:MM]
 *
 * @example
    <doc:example>
      <doc:source>
         <cc-fromto-time-input cc-label="営業時間" cc-partition="3" 
               id="openSt" name="openSt" ng-model="result.openSt" cc-required=true
               id2="openEd" name2="openEd" ng-model2="result.openEd" cc-required2=true />
      </doc:source>
      <doc:scenario>
      </doc:scenario>
    </doc:example>
 */
diretiveApp.directive('ccFromtoTimeInput', function(CSS_CLASS, $timeout){
    return {
        restrict: 'E',
        require: '?^form',
        scope : {
            ngModel: '=',
            ngModel2: '=',
            ccReadonly: '=',
            ccReadonly2: '=',
            ccWidth1: '=',
            ccWidth2: '=',
            ccFocus: '=',
            ccError: '='
        },
        template: function (element, attrs) {
            // either readOnly or required
            var readOnly_required = '';
            var readOnly_required2 = '';
            var inputClass = CSS_CLASS.ime_off;
            var inputClass2 = CSS_CLASS.ime_off;
            if (angular.isDefined(attrs.ccFocus)) {
                readOnly_required += ' cc-focus="ccFocus"';
            }
            if (angular.isDefined(attrs.ccReadonly)) {
                readOnly_required += ' ng-disabled="ccReadonly" ng-class="{\''+ CSS_CLASS.disable + '\': ccReadonly, \'\': !ccReadonly}" ';
            }
            
            if (angular.isDefined(attrs.ccReadonly2)) {
                readOnly_required2 += ' ng-disabled="ccReadonly2" ng-class="{\''+ CSS_CLASS.disable + '\': ccReadonly2, \'\': !ccReadonly2}" ';
            }
            
            if (angular.isDefined(attrs.ccReadonly) && attrs.ccReadonly == 'true') {
                readOnly_required += ' readonly ';
                inputClass += CSS_CLASS.BLANK + CSS_CLASS.disable;
            }
            if (angular.isDefined(attrs.ccReadonly2) && attrs.ccReadonly2 == 'true') {
                readOnly_required2 += ' readonly ';
                inputClass2 += CSS_CLASS.BLANK + CSS_CLASS.disable;
            }
            if (angular.isDefined(attrs.ccRequired) && attrs.ccRequired == 'true') {
                readOnly_required += ' required ';
            }
            if (angular.isDefined(attrs.ccRequired2) && attrs.ccRequired2 == 'true') {
                readOnly_required2 += ' required ';
            }

            inputClass += CSS_CLASS.BLANK + CSS_CLASS.center + CSS_CLASS.BLANK + CSS_CLASS.num_5;
            inputClass2 += CSS_CLASS.BLANK + CSS_CLASS.center + CSS_CLASS.BLANK + CSS_CLASS.num_5;

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
                htmlText += '<div class="' + CSS_CLASS.label_divide_zero + CSS_CLASS.BLANK + CSS_CLASS.label + 
                    '" > ' + attrs.ccLabel;
                if (angular.isDefined(attrs.ccReadonly)) {
                    readOnly_required += ' ng-disabled="ccReadonly" ng-class="{\''+ CSS_CLASS.disable + '\': ccReadonly, \'\': !ccReadonly}" ';
                }
                
                if (angular.isDefined(attrs.ccReadonly2)) {
                    readOnly_required2 += ' ng-disabled="ccReadonly2" ng-class="{\''+ CSS_CLASS.disable + '\': ccReadonly2, \'\': !ccReadonly2}" ';
                }
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
                    readOnly_required += ' required ';
                    readOnly_required2 += ' required ';
                    
                } else if (((angular.isDefined(attrs.ccReadonly) && attrs.ccReadonly == 'true') &&
                                (angular.isDefined(attrs.ccRequired2) && attrs.ccRequired2 == 'false')) ||
                            ((angular.isDefined(attrs.ccReadonly2) && attrs.ccReadonly2 == 'true') &&
                                (angular.isDefined(attrs.ccRequired) && attrs.ccRequired == 'false')) ||
                            ((angular.isDefined(attrs.ccReadonly2) && attrs.ccReadonly2 == 'false') &&
                                (angular.isDefined(attrs.ccRequired) && attrs.ccRequired == 'false'))) {
                    //cc-required="false" ~ not required.
                    readOnly_required += '';
                    readOnly_required2 += '';
                } else if ((angular.isDefined(attrs.ccReadonly) && attrs.ccReadonly == 'true') &&
                        (angular.isDefined(attrs.ccReadonly2) && attrs.ccReadonly2 == 'true')){
                    readOnly_required += ' readonly ';
                    readOnly_required2 += ' readonly ';
                }
                
                htmlText += '</div>';
            } else {
                htmlText += '<div class="'+labelClass+'">'+attrs.ccLabel;
                if ((angular.isDefined(attrs.ccRequired) && attrs.ccRequired == 'true') ||
                        (angular.isDefined(attrs.ccRequired2) && attrs.ccRequired2 == 'true')) {
                    htmlText += '<span class="' + CSS_CLASS.txt_required + '"> *</span>';
                }
            }

            htmlText += '</div>';
            htmlText += '<div style="float: left;" ><input onKeyDown="return setNextFocus(this);" type="text" class="' + inputClass + 
                '" error-message="{{errorSt}}" cc-type="time" id="' + attrs.id + '" name="' + attrs.name + 
                '" ng-model="ngModel" ' + readOnly_required + ' maxlength="5" ';
            if (!isEmpty(attrs.ccWidth1)) {
                htmlText += ' ng-style="{width: ccWidth1}"';
            }
            if (angular.isDefined(attrs.ccRequired) || 
                    (angular.isDefined(attrs.ccPartition) && 
                            attrs.ccPartition == '' && 
                            angular.isDefined(attrs.ccReadonly))) {
                htmlText += 'cc-blur-validation ';
                htmlText += 'ng-minlength="1" ';
            }
            
            //※ readonlyを設定する項目を[O]とし、readonlyを設定しない項目を[I]と解釈する。
            //FROM項目                  TO項目                    FROM項目の属性                                                          TO項目の属性
            //I               0             cc-validate-less-equal="TO項目"      -        
            //O               0                                                  -        
            if (((angular.isDefined(attrs.ccReadonly2) || attrs.ccReadonly2 == "true")
                    && (angular.isUndefined(attrs.ccReadonly) || attrs.ccReadonly == "false"))
                  || angular.isDefined(attrs.ccRequired)) {
                htmlText += 'cc-validate-less-equal="{{ngModel2}}" ';
            }
            htmlText += ' /></div>';

            htmlText += '<div class="' + CSS_CLASS.label_divide_zero + CSS_CLASS.BLANK + CSS_CLASS.label + 
                '" >～</div>';
            htmlText += '<input onKeyDown="return setNextFocus(this);" type="text" class="' + inputClass2 + '" cc-type="time" id="' + attrs.id2 + 
                '" name="' + attrs.name2 + '" ng-model="ngModel2" ' + readOnly_required2 + ' maxlength="5" error-message="{{errorEd}}" ';

            if (!isEmpty(attrs.ccWidth2)) {
                htmlText += ' ng-style="{width: ccWidth2}"';
            }
            if (angular.isDefined(attrs.ccRequired2) || 
                    (angular.isDefined(attrs.ccPartition) && 
                            attrs.ccPartition == '' && 
                            angular.isDefined(attrs.ccReadonly2))) {
                htmlText += 'cc-blur-validation ';
                htmlText += 'ng-minlength="1" ';
            }

            //FROM項目                  TO項目                    FROM項目の属性                         TO項目の属性
            //I               I             -                      cc-validate-greater-equal="FROM項目"
            //O               I             -                      cc-validate-greater-equal="FROM項目"
            if ((angular.isDefined(attrs.ccReadonly) && angular.isDefined(attrs.ccReadonly2))
                    || (attrs.ccReadonly == "true" && angular.isUndefined(attrs.ccReadonly2))
                    || (angular.isDefined(attrs.ccRequired) && angular.isDefined(attrs.ccRequired2))) {
                htmlText += 'cc-validate-greater-equal="{{ngModel}}" ';
            }

            htmlText += ' />';
            return htmlText; 
        },
        link: function(scope, element, attrs, ctrl) {
            var control = angular.element(element.find('input')[0]).controller('ngModel');
            var controlSecond = angular.element(element.find('input')[1]).controller('ngModel');

              var inputs = element.find('input');
              //SetFocus時の書式
              inputs.bind('focus', function(evt) {
                  if (attrs.id === this.id) {
                      if (!isEmpty(control.$viewValue) && 
                              '-1' != checkTime(control.$viewValue)) {
                          scope.$apply(function() {
                              var value = checkTime(control.$viewValue);
                              value = removeSymbol(value, ':');
                              control.$viewValue = value;
                              control.$render();

                          });
                      }
                      setLastCaret(element.find('input')[0], angular);
                  } else if (attrs.id2 === this.id) {
                      if (!isEmpty(controlSecond.$viewValue) && 
                              '-1' != checkTime(controlSecond.$viewValue)) {
                          scope.$apply(function() {
                              var value = checkTime(controlSecond.$viewValue);
                              value = removeSymbol(value, ':');
                              controlSecond.$viewValue = value;
                              controlSecond.$render();

                          });
                      }
                      setLastCaret(element.find('input')[1], angular);
                  }

              }).bind('blur', function(evt) {
                  var check = '';
                  var thisId = this.id;

                  check = checkTime(ctrl[thisId].$viewValue);
                  //画面表示時の書式
                  scope.$apply(function() {
                      // There is a error
                      if ('-1' === check || '' === check || angular.isUndefined(check)) {
                          return;
                      }
                      
                      if (attrs.id === thisId) {
                          //画面表示時の書式
                          // model is valid
                          var value = checkTime(control.$modelValue);
                          control.$viewValue = value;
                          control.$render();
                      } else if (attrs.id2 === thisId) {
                          //画面表示時の書式
                          // model is valid
                          var value = checkTime(controlSecond.$modelValue);
                          controlSecond.$viewValue = value;
                          controlSecond.$render();
                      }
                  });
                  scope.$emit(this.id + "Blur");
              });
              
              // view -> model
              control.$parsers.push(function(viewValue) {
                  control.$setValidity('ccTime', true);
                  if (isEmpty(control.$viewValue)) {
                      control.$setValidity('ccValidateLessEqual', true);
                      angular.element('#'+attrs.id+' #'+attrs.id).removeClass(CSS_CLASS.error);
                      angular.element('#'+attrs.id+' #'+attrs.id).popover('destroy');
                      return undefined;
                  }
                  
                  var check = checkTime(control.$viewValue);
                  if ('-1' === check) {
                      control.$setValidity('ccTime', false);
                      control.$setValidity('ccValidateLessEqual', true);
                      // control.$viewValue = viewValue;
                      return undefined;
                  } else {
                      return removeSymbol(check, ':');
                  }
              });
              
              control.$formatters.push(function(viewValue) {
                  //remove error when init again data
                  angular.element('#'+attrs.id+' #'+attrs.id).removeClass(CSS_CLASS.error);
                  angular.element('#'+attrs.id+' #'+attrs.id).popover('destroy');
                  control.$setValidity('ccTime', true);
                  
                  if (isEmpty(viewValue)) {
                      return viewValue;
                  }

                  var check = checkTime(viewValue);
                  if ('-1' === check) {
                      control.$setValidity('ccTime', false);
                      return checkFormatTime(viewValue);
                  } else {
                      return check;
                  }
              });
              
              // view -> model
              controlSecond.$parsers.push(function(viewValue) {
                  controlSecond.$setValidity('ccTime', true);
                  if (isEmpty(controlSecond.$viewValue)) {
                      controlSecond.$setValidity('ccValidateGreaterEqual', true);
                      angular.element('#'+attrs.id+' #'+attrs.id2).removeClass(CSS_CLASS.error);
                      angular.element('#'+attrs.id+' #'+attrs.id2).popover('destroy');
                      return undefined;
                  }
                  
                  var check = checkTime(controlSecond.$viewValue);
                  if ('-1' === check) {
                      controlSecond.$setValidity('ccValidateGreaterEqual', true);
                      controlSecond.$setValidity('ccTime', false);
                      // controlSecond.$viewValue = viewValue;
                      return undefined;
                  } else {
                      return removeSymbol(check, ':');
                  }
              });

              controlSecond.$formatters.push(function(viewValue) {
                  //remove error when init again data
                  angular.element('#'+attrs.id+' #'+attrs.id2).removeClass(CSS_CLASS.error);
                  angular.element('#'+attrs.id+' #'+attrs.id2).popover('destroy');
                  controlSecond.$setValidity('ccTime', true);
                  
                  if (isEmpty(viewValue)) {
                      return viewValue;
                  }

                  var check = checkTime(viewValue);
                  if ('-1' === check) {
                      controlSecond.$setValidity('ccTime', false);
                      return checkFormatTime(viewValue);
                  } else {
                      return check;
                  }
              });

              // lochv start 13/3/2014 : show server error message
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
                              if ((parseFloat(control.$modelValue) <= parseFloat(controlSecond.$modelValue)) 
                                  || (isEmpty(control.$viewValue) && ('-1' != checkTime(controlSecond.$viewValue)))) {
                                controlSecond.$setValidity('ccTime', true);
                                controlSecond.$setValidity('ccValidateGreaterEqual', true);
                                if (!(isEmpty(controlSecond.$viewValue) && 'true' == attrs.ccRequired2)) {
                                  angular.element('#'+attrs.id+' #'+attrs.id2).removeClass(CSS_CLASS.error);
                                  angular.element('#'+attrs.id+' #'+attrs.id2).popover('destroy');
                                }
                                control.$setValidity('ccValidateLessEqual', true);
                                control.$setValidity('ccTime', true);
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
                              if ((parseFloat(control.$modelValue) <= parseFloat(controlSecond.$modelValue)) 
                                  || (isEmpty(control.$viewValue) && ('-1' != checkTime(control.$viewValue)))) {
                                controlSecond.$setValidity('ccTime', true);
                                controlSecond.$setValidity('ccValidateGreaterEqual', true);
                                if (!(isEmpty(controlSecond.$viewValue) && 'true' == attrs.ccRequired2)) {
                                  angular.element('#'+attrs.id+' #'+attrs.id2).removeClass(CSS_CLASS.error);
                                  angular.element('#'+attrs.id+' #'+attrs.id2).popover('destroy');
                                }

                                control.$setValidity('ccValidateLessEqual', true);
                                control.$setValidity('ccTime', true);
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
                // lochv end 13/3/2014
            }
        }
    });
