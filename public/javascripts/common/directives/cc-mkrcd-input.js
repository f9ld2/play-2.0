/**
 * @ngdoc directive
 * @name chasecore.cc-mkrcd-input
 * @restrict A
 * @function
 *
 * @description
 * (コード入力時)メーカーマスタから『メーカー名称(略称を含む)』を取得・表示する
 *
 * @example
    <doc:example>
      <doc:source>
         <cc-mkrcd-input cc-label="メーカー" cc-partition="3" 
         id="mkrCd" name="mkrCd" ng-model="cond.mkrCd" 
         cc-required=true cc-notexist=false cc-delexist=true 
         cc-shortname=true cc-parameter01="cond.hakkoDay" 
         id2="mkrNm" name2="mkrNm" ng-model2="cond.mkrNm"></cc-mkrcd-input>
      </doc:source>
      <doc:scenario>
      </doc:scenario>
    </doc:example>
 */

diretiveApp.directive('ccMkrcdInput', function(CSS_CLASS, $http, $timeout, UserInfo) {
    var unyoDate = UserInfo.unyoDate;

    return {
        restrict: 'E',
        require: 'ngModel',
        scope: {
            ngModel: '=',
            ngModel2: '=',
            ccParameter01: '=',
            ccReadonly: '=',
            ccError: '=',
            ccFocus: '=',
            ccWidth1: '=',
            ccWidth2: '='
        },
        template: function(element, attrs) {
            // either readOnly or required
            var inputClass = '';
            var inputClass2 = CSS_CLASS.BLANK + CSS_CLASS.disable;
            var readOnlyRequired = '';
            if (angular.isDefined(attrs.ccFocus)) {
                readOnlyRequired += ' cc-focus="ccFocus"';
            }
            if (angular.isDefined(attrs.ccReadonly)) {
                readOnlyRequired += ' ng-disabled="ccReadonly" ng-class="{\'' + CSS_CLASS.disable + '\': ccReadonly, \'\': !ccReadonly}" ';
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

            // maxlength for nameInput
            var maxlength = '';
            if (attrs.ccShortname == 'true') {
                maxlength = '5';
            } else if (attrs.ccShortname == 'false') {
                maxlength = '20';
            }

            var htmlText = '';

            if (angular.isDefined(attrs.ccPartition) && attrs.ccPartition == '') {
                htmlText += '<div class="' + labelClass + '" >' + attrs.ccLabel;
            } else {
                htmlText += '<div class="' + labelClass + '">' + attrs.ccLabel;
                if (angular.isDefined(attrs.ccRequired) && attrs.ccRequired == 'true') {
                    htmlText += '<span class="' + CSS_CLASS.txt_required + '"> *</span>';
                }
            }
            htmlText += '</div>';

            htmlText += '<input type="text" class="' + inputClass + '" cc-type="code" id="' + attrs.id + '" name="' + attrs.name + '" ng-model="ngModel" ' + readOnlyRequired + '  maxlength="7" onKeyDown="return setNextFocus(this);" ';
            if (!isEmpty(attrs.ccWidth1)) {
                htmlText += ' ng-style="{width: ccWidth1}"';
            }
            if (attrs.ccReadonly == 'true') {
                htmlText += ' />';
            } else {
                htmlText += ' ng-minlength="7" cc-blur-validation error-message="{{error}}" ui-event="{ blur : \'blurCallback(ngModel)\' }"';
                htmlText += ' />';
            }

            if (angular.isUndefined(attrs.ngModel2)) {
                return htmlText;
            }

            htmlText += '&nbsp';
            htmlText += '<input type="text" class=' + inputClass2 + ' cc-type="zenkaku" id="' + attrs.id2 + '" name="' + attrs.name2 + '" ng-model="ngModel2" ng-disabled="true" maxlength="' + maxlength + '" ';
            if (!isEmpty(attrs.ccWidth2)) {
                htmlText += ' ng-style="{width: ccWidth2}" />';
            } else {
                htmlText += ' />';
            }

            return htmlText;
        },
        link: function(scope, element, attrs, ctrl) {
            var inputCtrl = angular.element(element.find('input')[0]).controller('ngModel');
            // メーカー名称取得処理
            var record = null;
            var checkData = false;
            var input = element.find('input');
            //SetFocus時の書式
            input.bind('focus', function(evt) {
                setLastCaret(element.find('input')[0], angular);
            }).bind('blur', function(evt) {
                scope.$emit(this.id + "Blur");
            }).bind('click', function() {
                scope.$emit(attrs.id + "Click");
            });;

            scope.$on('$destroy', function() {
                element.find('input').unbind();
                element.find('input').off();
            })

            function validation(value) {
                // init data
                inputCtrl.$setValidity('ccExist', true);
                record = null;
                checkData = false;

                // 未設定の場合は処理を終了する。(エラーを設定しない)
                if (isEmpty(value)) {
                    if (angular.isDefined(attrs.id2) && angular.isDefined(attrs.name2) && angular.isDefined(attrs.ngModel2)) {
                        scope.ngModel2 = '';
                    }
                    return value;
                }

                var requestWithHakkoDay = function(value, hakkoDay) {
                    // 2)メーカー名称取得
                    $http({
                        method: 'GET',
                        url: '/core/codename/m013makm/' + value + '?hakkoDay=' + hakkoDay
                    }).success(function(data) {
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
                }

                // 1)パラメータチェック
                // 未設定の場合は運用日付を取得して代用する。
                var hakkoDay = scope.ccParameter01;
                if (isEmpty(hakkoDay)) {
                    requestWithHakkoDay(value, unyoDate);
                } else {
                    requestWithHakkoDay(value, hakkoDay);
                }
                return value;
            }

            if (angular.isDefined(attrs.id2) &&
                angular.isDefined(attrs.name2) &&
                angular.isDefined(attrs.ngModel2)) {
                inputCtrl.$parsers.push(validation);
                inputCtrl.$formatters.push(validation);

                var input = element.find('input')[0];
                setLastCaret(input, angular);
            }

            // listener blur event
            scope.blurCallback = function(value) {
                if (isEmpty(value)) {
                    return;
                }
                if (angular.isDefined(attrs.id2) &&
                    angular.isDefined(attrs.name2) &&
                    angular.isDefined(attrs.ngModel2)) {
                    // 4)メーカー名称設定
                    // 上記3)が『エラーである』 (error)
                    // 上記3)が『エラーで無い』 and 取得レコード = 無し and cc-notexist = true (not
                    // error)
                    if (!checkData || checkData && record.length == 0 && attrs.ccNotexist == 'true') {
                        scope.ngModel2 = '';
                    } else if (checkData && record.length != 0) {
                        // 上記3)が『エラーで無い』 and cc-shortname = true
                        if (attrs.ccShortname == 'true') {
                            scope.ngModel2 = record[0].shortName;
                            // 上記3)が『エラーで無い』 and cc-shortname = false
                        } else if (attrs.ccShortname == 'false') {
                            scope.ngModel2 = record[0].name;
                        }
                    }
                }
            }

            // 入力値に対するエラーチェック (check in ccType])
            // パラメータ(cc-parameterxx)に指定されているバインド変数が変更された場合は当該部品のバインド変数を消去する。
            scope.$watch('ccParameter01', function(value) {
                if (!inputCtrl.$pristine) {
                    scope.ngModel = '';
                }
                if (angular.isDefined(attrs.ngModel2)) {
                    scope.ngModel2 = '';
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