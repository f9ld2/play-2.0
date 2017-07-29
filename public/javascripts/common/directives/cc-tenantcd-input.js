/**
 * @ngdoc directive
 * @name chasecore.cc-tenantcd-input
 * @restrict E
 * @function
 * 
 * @description (コード入力時)店舗マスタから『店舗名称(or略称)』を取得・表示する
 * 
 * @example 
 *      <doc:example> 
 *          <doc:source> 
 *              <cc-tenantcd-input
 *               cc-label="テナント"
 *               cc-partition="3"
 *               id="tenantCd"
 *               name="tenantCd"
 *               ng-model="cond.tenantCd"
 *               cc-required="true"
 *               cc-notexist="false"
 *               cc-delexist=true
 *               cc-parameter01="result.kaisyaCd"
 *               cc-parameter02="result.jigyobuCd"
 *               cc-parameter03="result.tenCd"
 *               cc-parameter04="result.hakkoDay"
 *               id2="tenantNm"
 *               name2="tenantNm"
 *               ng-model2="result.tenantNm">
 *               </cc-tenantcd-input>
 *          </doc:source> 
 *          <doc:scenario> 
 *          </doc:scenario> 
 *      </doc:example>
 */

diretiveApp
        .directive(
                'ccTenantcdInput',
                function(CSS_CLASS, $http, $timeout, UserInfo) {
                	var unyoDate = UserInfo.unyoDate;
                    return {
                        restrict : 'E',
                        require : 'ngModel',
                        scope : {
                            ngModel : '=',
                            ngModel2 : '=',
                            ccParameter01 : '=',
                            ccParameter02 : '=',
                            ccParameter03 : '=',
                            ccParameter04 : '=',
                            ccReadonly : '=',
                            ccError: '=',
                            ccFocus: '=',
                            ccWidth1: '=',
                            ccWidth2: '='
                        },
                        template : function(element, attrs) {

                            var inputClass = '';
                            var inputClass2 = CSS_CLASS.BLANK + CSS_CLASS.disable;
                            var labelClass = '';

                            // either readOnly or required
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

                            // div no1
                            if (angular.isDefined(attrs.ccPartition) && attrs.ccPartition == '') {
                                htmlText += '<div class="' + CSS_CLASS.label_divide_zero + CSS_CLASS.BLANK
                                        + CSS_CLASS.label + '" >' + attrs.ccLabel + '</div>';
                            } else {
                                htmlText += '<div class="' + labelClass + '">' + attrs.ccLabel;
                                if (angular.isDefined(attrs.ccRequired) && attrs.ccRequired == 'true') {
                                    htmlText += '<span class="' + CSS_CLASS.txt_required + '"> *</span>';
                                }
                            }
                            htmlText += '</div>';

                            // input no1
                            htmlText += '<input type="text" cc-type="code" id="' + attrs.id
                                    + '" name="' + attrs.name
                                    + '" ng-model="ngModel" onKeyDown="return setNextFocus(this);" '
                                    + readOnly_required
                                    + (angular.isDefined(attrs.ccRequired) ? 'ui-event="{ blur : \'blurCallback(ngModel)\' }"' : '')
                                    + ' maxlength="5" ';
                            if (!isEmpty(attrs.ccWidth1)) {
                                htmlText += ' ng-style="{width: ccWidth1}"';
                            }
                            if (attrs.ccReadonly == 'true') {
                                htmlText += ' >';
                            } else {
                                htmlText += ' ng-minlength="5" cc-blur-validation error-message="{{error}}" >';
                            }
                            
                            // input no2
                            if (angular.isDefined(attrs.ngModel2)) {
                                htmlText += ' &nbsp ';
                                htmlText += '<input type="text" cc-type="zenkaku" ';
                                if (!isEmpty(attrs.ccWidth2)) {
                                    htmlText += ' ng-style="{width: ccWidth2}"';
                                }
                                htmlText += ' id="' + attrs.id2 + '" name="'
                                        + attrs.name2 + '" class="' + inputClass2 + '" ng-model="ngModel2" ng-disabled="true" maxlength="'
                                        + (attrs.ccShortname == "true" ? '5">' : '20">');
                            }

                            return htmlText;
                        },

                        link : function(scope, element, attrs, ctrl) {
                            var inputCtrl = element.find('input').eq(0).controller('ngModel');
                            // 店舗名称取得処理

                            var checkData = false;
                            var record = undefined;
                            var input = element.find('input');
                            //SetFocus時の書式
                            input.bind('focus', function(evt) {
                                setLastCaret(element.find('input')[0], angular);
                            });
                            
                            input.bind('blur', function(evt) {
                                scope.$emit(this.id + "Blur");
                            });
                            
                            function validation(value) {
                                // init data
                                inputCtrl.$setValidity('ccExist', true);
                                record = null;
                                if (angular.isDefined(attrs.ngModel2)) {
                                    scope.ngModel2 = '';
                                }

                                // 1)パラメータチェック
                                // set default parameter04
                                if (isEmpty(scope.ccParameter01) || 
                                    isEmpty(scope.ccParameter02) || 
                                    isEmpty(scope.ccParameter03) || 
                                    isEmpty(value)) {
                                    return;
                                }

                                // 2)店舗名称取得
                                var requestWithHakkoDay = function(hakkoDay) {
                                    $http(
                                            {
                                                method : 'GET',
                                                url : '/core/codename/y001tntm/' + value + 
                                                '/' + scope.ccParameter01 +
                                                '/' + scope.ccParameter02 +
                                                '/' + scope.ccParameter03 + '?hakkoDay=' + hakkoDay
                                            }).success(
                                                    function(data) {
                                                        record = data;
                                                        // 3)結果チェック
                                                        // 無し
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
                                };
                                
                                var hakkoDay = scope.ccParameter04;
                                if(!isEmpty(hakkoDay)) {
                                    requestWithHakkoDay(hakkoDay);
                                }
                                else {
                                	requestWithHakkoDay(unyoDate);
                                }
                                
                                return value;
                            }

                            inputCtrl.$parsers.push(validation);
                            
                            if (angular.isDefined(attrs.id2) && angular.isDefined(attrs.name2)
                                    && angular.isDefined(attrs.ngModel2)) {

                                var input = element.find('input')[0];
                                setLastCaret(input, angular);
                            }

                            inputCtrl.$formatters.push(function(viewValue) {
                                //remove error when init again data
                                angular.element('#'+attrs.id+' #'+attrs.id).removeClass(CSS_CLASS.error);
                                angular.element('#'+attrs.id+' #'+attrs.id).popover('destroy');
                                
                                return viewValue;
                            });
                            
                            scope.blurCallback = function(value) {
                                if (isEmpty(value)) {
                                    return;
                                }
                                if (angular.isDefined(attrs.id2) && angular.isDefined(attrs.name2)
                                        && angular.isDefined(attrs.ngModel2)) {
                                    // 4)店舗名称設定
                                    // 上記3)が『エラーである』 (error)
                                    // 上記3)が『エラーで無い』 and 取得レコード = 無し and
                                    // cc-notexist = true (not
                                    // error)
                                    if (!checkData || checkData && record.length == 0
                                            && attrs.ccNotexist == 'true') {
                                        scope.ngModel2 = undefined;
                                    } else if (checkData && record.length != 0) {
                                        scope.ngModel2 = record[0].name;
                                    }
                                }
                            }

                            // 入力値に対するエラーチェック (check in ccType)

                            // パラメータ(cc-parameterxx)に指定されているバインド変数が変更された場合は当該部品のバインド変数を消去する。
                            scope.$watchCollection('[ccParameter01, ccParameter02, ccParameter03, ccParameter04]', function(value) {
                                if (!inputCtrl.$pristine) {
                                    scope.ngModel = undefined;
                                    scope.ngModel2 = undefined;
                                }
                            });

                            // Show server error message
                            scope.error = null;
                            scope.$on("ccError", function(event, value) {
                                scope.error = null;
                                inputCtrl.$setValidity('ccClientError', true);
                                if (!isEmpty(value)) {
                                    angular.forEach(value, function(error, key) {
                                        if (angular.equals(error.name, attrs.name) && 
                                            angular.equals(error.level, 'E')) {
                                            scope.error = error;
                                            if (error.clientErrorFlag == true) {
                                                inputCtrl.$setValidity('ccClientError', false);
                                            } else {
                                                inputCtrl.$setValidity('ccClientError', true);
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
                        }
                    }
                });
