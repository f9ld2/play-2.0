/**
 * @ngdoc directive
 * @name chasecore.cc-oyakkkcd-input
 * @restrict E
 * @function
 *
 * @description
 * (コード入力時)親企画マスタから『親企画名称』を取得・表示する
 *
 * @example
    <doc:example>
      <doc:source>
         <cc-oyakkkcd-input 
             cc-label="親企画" 
             cc-partition="3" 
             id="oyaKikakuCd" 
             name="oyaKikakuCd" 
             ng-model="cond.oyaKikakuCd" 
             cc-required=true 
             cc-notexist=false 
             cc-delexist=true 
             cc-parameter01="cond.kaisyaCd" 
             cc-parameter02="cond.jigyobuCd" 
             cc-parameter03="cond.nendo"
             id2="oyaKikakuNm" 
             name2="oyaKikakuNm" 
             ng-model2="cond.oyaKikakuNm">
         </cc-oyakkkcd-input>
      </doc:source>
      <doc:scenario>
      </doc:scenario>
    </doc:example>
 */
diretiveApp.directive('ccOyakkkcdInput', function(CSS_CLASS, $http) {
  return {
    restrict: 'E',
    require: 'ngModel',
    scope : {
        ngModel : '=',
        ngModel2 : '=',
        ccParameter01 : '=',
        ccParameter02 : '=',
        ccParameter03 : '=',
        ccReadonly: '=',
        ccWidth1: '=',
        ccWidth2: '=',
// taivd edit code 12/06/2014 start
        ccFocus: '=',
// taivd edit code 12/06/2014 end
        ccInit: '='
    },
    template: function (element, attrs) {
        // either readOnly or required
        var readOnlyRequired = '';
        var inputClass = '';
        var inputClass2 = CSS_CLASS.BLANK + CSS_CLASS.disable;
// taivd edit code 12/06/2014 start
        if (angular.isDefined(attrs.ccFocus)) {
            readOnlyRequired += ' cc-focus="ccFocus"';
        }
// taivd edit code 12/06/2014 end
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

        htmlText += '<input type="text" cc-type="code" id="' + attrs.id + '" name="' + attrs.name + '" class="' + inputClass
        + '" ng-model="ngModel" ' + readOnlyRequired + '  maxlength="9" onKeyDown="return setNextFocus(this);" ';
        if (!isEmpty(attrs.ccWidth1)) {
            htmlText += ' ng-style="{width: ccWidth1}"';
        }
        if (attrs.ccReadonly == 'true') {
            htmlText += ' />';
        } else {
            htmlText += ' ng-minlength="5" cc-blur-validation';
            if (angular.isUndefined(attrs.id2) &&
                    angular.isUndefined(attrs.name2) &&
                    angular.isUndefined(attrs.ngModel2)) {
                htmlText += ' />';
                return htmlText;
            }
            htmlText += ' ui-event="{ blur : \'blurCallback(ngModel)\' }"/>';
        }
        htmlText += ' &nbsp';
        htmlText += '<input type="text" cc-type="zenkaku"';
        if (!isEmpty(attrs.ccWidth2)) {
            htmlText += ' ng-style="{width: ccWidth2}"';
        }
        htmlText += ' id="' + attrs.id2 + '" name="' + attrs.name2
            + '" class="' + inputClass2
            + '" ng-model="ngModel2" ng-disabled="true" maxlength="15" />';
        return htmlText; 
    },

    link: function(scope, element, attrs, ctrl) {
        var inputCtrl = angular.element(element.find('input')[0]).controller('ngModel');
        // 親企画名称取得処理
        var record = null;
        var checkData = false;

        var input = element.find('input');
        //SetFocus時の書式
        input.bind('focus', function(evt) {
            setLastCaret(element.find('input')[0], angular);
        });
        
        function validation(value) {
            // init data
            inputCtrl.$setValidity('ccExist', true);
            record = null;
            checkData = false;
            scope.ngModel2 = undefined;
            // 1)パラメータチェック
            // 未設定の場合は処理を終了する。(エラーを設定しない)
            if (isEmpty(value) || isEmpty(scope.ccParameter01) || 
                    isEmpty(scope.ccParameter02) || isEmpty(scope.ccParameter03)) {
                return value;
            }

            // 2)親企画名称取得
            $http({
                method : 'GET',
                url : '/core/codename/t005okkm/' + value + '/' + scope.ccParameter01 + '/' + scope.ccParameter02 + 
                '/' + scope.ccParameter03
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
            return value;
        }

        if (angular.isDefined(attrs.id2) &&
                angular.isDefined(attrs.name2) &&
                angular.isDefined(attrs.ngModel2)) {
            inputCtrl.$parsers.push(validation);

            var input = element.find('input')[0];
            setLastCaret(input, angular);
        }

        inputCtrl.$formatters.push(function(viewValue) {
            //remove error when init again data
            angular.element('#'+attrs.id+' #'+attrs.id).removeClass(CSS_CLASS.error);
            angular.element('#'+attrs.id+' #'+attrs.id).popover('destroy');
            
            return viewValue;
        });
        
        // override render of inputCtrl
        inputCtrl.$render = function() {
            angular.element(element.find('input')[0]).val(inputCtrl.$viewValue);
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
                // 上記3)が『エラーで無い』 and 取得レコード = 無し and cc-notexist = true (not error)
                if (!checkData || checkData && record.length == 0 && attrs.ccNotexist == 'true') {
                    scope.ngModel2 = undefined;
                } else if (checkData && record.length != 0) {
                    // 上記3)が『エラーで無い』
                    scope.ngModel2 = record[0].name;
                }
            }
        }

        // 入力値に対するエラーチェック (check in ccType)

        // パラメータ(cc-parameterxx)に指定されているバインド変数が変更された場合は当該部品のバインド変数を消去する。
        // scope.$watchCollection('[ccParameter01, ccParameter02, ccParameter03]', function(value) {
        //     scope.ngModel = undefined;
        //     scope.ngModel2 = undefined;
        // });

        scope.$watch('ccParameter01', function(newValue, oldValue) {
            scope.resetData(newValue, oldValue);
        });

        scope.$watch('ccParameter02', function(newValue, oldValue) {
            scope.resetData(newValue, oldValue);
        });

        scope.$watch('ccParameter03', function(newValue, oldValue) {
            scope.resetData(newValue, oldValue);
        });

        scope.resetData = function(newValue, oldValue) {
            if (angular.equals(cloneData(newValue), cloneData(oldValue))) {
                return;
            }
            if (angular.isUndefined(attrs.ccInit) || !scope.ccInit) {
                scope.ngModel = undefined;
                scope.ngModel2 = undefined;
            } else {
                scope.ccInit = false;
            }
        }
    }
    
  }
});

