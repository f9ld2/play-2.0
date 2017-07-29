/**
 * @ngdoc directive
 * @name chasecore.cc-bnkstncd-input
 * @restrict E
 * @function
 *
 * @description
 * (コード入力時)銀行マスタから『銀行支店略称』を取得・表示する
 *
 * @example
    <doc:example>
      <doc:source>
        <cc-bnkstncd-input 
            cc-label="支店" 
            cc-partition="3" 
            id="bnkStnCd" 
            name="bnkStnCd" 
            ng-model="cond.bnkStnCd" 
            cc-required=true 
            cc-notexist=false 
            cc-delexist=true 
            cc-parameter01="cond.bnkCd" 
            cc-parameter02="cond.hakkoDay"
            id2="bnkBoNmR" 
            name2="bnkBoNmR" 
            ng-model2="cond.bnkBoNmR">
         </cc-bnkstncd-input>
      </doc:source>
      <doc:scenario>
      </doc:scenario>
    </doc:example>
 */

diretiveApp.directive('ccBnkstncdInput', function(CSS_CLASS, $http, UserInfo) {
	var unyoDate = UserInfo.unyoDate;
                return {
                    restrict: 'E',
                    require: 'ngModel',
                    scope : {
                        ngModel : '=',
                        ngModel2 : '=',
                        ccFocus: '=',
                        ccParameter01 : '=',
                        ccParameter02 : '=',
                        ccReadonly: '='
                    },
                    template: function (element, attrs) {
                        // either readOnly or required
                        var inputClass = '';
                        var inputClass2 = CSS_CLASS.BLANK + CSS_CLASS.disable;
                        var readOnlyRequired = '';
                        if (angular.isDefined(attrs.ccFocus)) {
                            readOnlyRequired += ' cc-focus="ccFocus"';
                        }
                        if (angular.isDefined(attrs.ccReadonly)) {
                            readOnlyRequired += ' ng-disabled="ccReadonly" ng-class="{\''+ CSS_CLASS.disable + '\': ccReadonly, \'\': !ccReadonly}" ';
                        }
                        if (angular.isDefined(attrs.ccReadonly) && attrs.ccReadonly == 'true') {
                            readOnlyRequired += ' readonly ';
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
                            if (angular.isDefined(attrs.ccPartition) && attrs.ccRequired == 'true') {
                                labelClass += CSS_CLASS.cs_label_large_required;
                            } else {
                                labelClass += CSS_CLASS.cs_label_large;
                            }
                        }
                        labelClass += CSS_CLASS.BLANK + CSS_CLASS.label;

                        // html text
                        var htmlText = '';
                        if (angular.isDefined(attrs.ccPartition) && attrs.ccPartition == '') {
                            htmlText += '<div class="'+ labelClass +'" > '+attrs.ccLabel;
                        } else {
                            htmlText += '<div class="'+labelClass+'">'+attrs.ccLabel;
                            if (angular.isDefined(attrs.ccRequired) && attrs.ccRequired == 'true') {
                                htmlText += '<span class="'+ CSS_CLASS.txt_required +'"> *</span>';
                            }
                        }
                        htmlText += '</div>';

                        htmlText += '<input onKeyDown="return setNextFocus(this);" type="text" class="' + inputClass
                        + '" cc-type="code" id="' + attrs.id + '" name="' + attrs.name
                        + '" ng-model="ngModel" ' + readOnlyRequired + '  maxlength="3" ';
                        if (attrs.ccReadonly == 'true') {
                            htmlText += ' />';
                        } else {
                            htmlText += ' ng-minlength="3" cc-blur-validation';
                            if (angular.isUndefined(attrs.id2) &&
                                    angular.isUndefined(attrs.name2) &&
                                    angular.isUndefined(attrs.ngModel2)) {
                                htmlText += ' />';
                                return htmlText;
                            }
                            htmlText += ' ui-event="{ blur : \'blurCallback(ngModel)\' }"/>';
                        }
                        htmlText += ' &nbsp';
                        htmlText += '<input type="text" cc-type="zenkaku" id="' + attrs.id2 
                            + '" name="' + attrs.name2 + '" class="' + inputClass2
                            + '" ng-model="ngModel2" ng-disabled="true" maxlength="5" />';
                        return htmlText; 
                    },

                    link: function(scope, element, attrs, ctrl) {
                        var inputCtrl = element.find('input').eq(0).controller('ngModel');
                        // 銀行支店略称取得処理
                        var checkData = false;
                        var record = undefined;
                        var input = element.find('input');
                        //SetFocus時の書式
                        input.bind('focus', function(evt) {
                            setLastCaret(element.find('input')[0], angular);
                        });

                        inputCtrl.$formatters.push(function(viewValue) {
                            //remove error when init again data
                            angular.element('#'+attrs.id+' #'+attrs.id).removeClass(CSS_CLASS.error);
                            angular.element('#'+attrs.id+' #'+attrs.id).popover('destroy');
                            
                            return viewValue;
                        });
                        
                        function validation(value) {
                            // init data
                            inputCtrl.$setValidity('ccExist', true);
                            scope.ngModel2 = undefined;
                            // 1)パラメータチェック
                            // 未設定の場合は処理を終了する。(エラーを設定しない)
                            if (isEmpty(value) || isEmpty(scope.ccParameter01) || attrs.ngModel2 == undefined ) {
                                return;
                            }

                            // 2)銀行支店略称取得
                            var requestWithHakkoDay = function(hakkoDay) {
                                $http({
                                    method : 'GET',
                                    url : '/core/codename/m018bnkm/' + value + '/' + scope.ccParameter01 
                                        + '?hakkoDay=' + hakkoDay
                                }).success(
                                        function(data) {
                                            record = data[0];
                                            // 3)結果チェック
                                            // 無し
                                            if (!angular.isDefined(record)) {
                                                if (attrs.ccNotexist == 'true') {
                                                    checkData = true;
                                                } else if (attrs.ccNotexist == 'false') {
                                                    checkData = false;
                                                }
                                                // 稼動区分[9]であるレコード
                                            } else if (record.kubun == '9') {
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

                            var hakkoDay = scope.ccParameter02;
                            if(!isEmpty(hakkoDay)) {
                                requestWithHakkoDay(hakkoDay);
                            } else {
                            	requestWithHakkoDay(unyoDate);
                            }
                        
                            return value;
                        }

                        if (angular.isDefined(attrs.id2) &&
                                angular.isDefined(attrs.name2) &&
                                angular.isDefined(attrs.ngModel2)) {
                            inputCtrl.$parsers.push(validation);
                        }

                        // override render of inputCtrl
                        inputCtrl.$render = function() {
                            angular.element(element.find('input')[0]).val(inputCtrl.$viewValue);
                        }
 
                        scope.blurCallback = function(value) {
                            if (isEmpty(value)) {
                                return;
                            }
                            if (angular.isDefined(attrs.id2) && angular.isDefined(attrs.name2) 
                                    && angular.isDefined(attrs.ngModel2)) {
                                // 4)銀行支店略称名称設定
                                // 上記3)が『エラーである』 (error)
                                // 上記3)が『エラーで無い』 and 取得レコード = 無し and cc-notexist = true (not error)
                                if (!checkData || checkData && !angular.isDefined(record) 
                                        && attrs.ccNotexist == 'true') {
                                    scope.ngModel2 = undefined;
                                } else if (checkData && angular.isDefined(record)) {
                                    // 上記3)が『エラーで無い』
                                    scope.ngModel2 = record.name;
                                }
                            }
                        }

                        // 入力値に対するエラーチェック (check in ccType)
                        // パラメータ(cc-parameterxx)に指定されているバインド変数が変更された場合は当該部品のバインド変数を消去する。
                        scope.$watchCollection('[ccParameter01, ccParameter02]', function(value) {
                            if (!inputCtrl.$pristine) {
                                scope.ngModel = undefined;
                                scope.ngModel2 = undefined;
                            }
                        });
            }
        }
    });