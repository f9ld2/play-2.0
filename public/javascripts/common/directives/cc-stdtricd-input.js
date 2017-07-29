/**
 *  コード入力(代表取引先) : cc-stdtricd-input
 */

diretiveApp.directive('ccStdtricdInput', function(CSS_CLASS){
  return {
    restrict: 'E',
    require: 'ngModel',
    template: function (element, attrs) {
        // html text
        var htmlText = '';
        htmlText += '<div class="label-xxxx-required label">取引先<span class="txt-required"> *</span> </div>';
        htmlText += '<input type="text" cc-type="code" id="triCd" name="triCd" ng-model="result.triCd" required ng-minlength="6" maxlength="6" cc-blur-validation ';
        htmlText += 'ui-event="{ blur : \'methodCall(parameter)\' }" />';        //"代表取引先名称取得処理(パラメータ)"
        htmlText += '&nbsp';
        htmlText += '<input type="text" cc-type="zenkaku" id="triNm" name="triNm" ng-model="result.triNm" readonly maxlength="20" />';
        
        return htmlText; 
    },
    scope : {
        ngModel: '='
    },
    link: function(scope, element, attrs, ctrl) {
        scope.methodCall = function(value){}
    }
    
  }
});

