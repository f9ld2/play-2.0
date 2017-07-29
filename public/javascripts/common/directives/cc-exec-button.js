/**
 *  コマンドボタン(業務共通) : cc-exec-button 
 */

diretiveApp.directive('ccExecButton', function(CSS_CLASS){
  return {
    restrict: 'E',
    template: function (element, attrs) {
        // html text
        var htmlText = '';
        htmlText += '<button class="btnExec" id="btnExec" name="btnExec" ng-click="doExec()" > 実行 <span> (F12) </span> </button> ';
        
        return htmlText; 
    },
    scope : {
        ngModel: '='
    },
    link: function(scope, element, attrs, ctrl) {

    }
    
  }
});

