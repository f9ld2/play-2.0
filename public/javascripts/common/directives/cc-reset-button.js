/**
 *  コマンドボタン(業務共通) : cc-reset-button 
 */

diretiveApp.directive('ccResetButton', function(CSS_CLASS){
  return {
    restrict: 'E',
    template: function (element, attrs) {
        // html text
        var htmlText = '';
        htmlText += '<button class="btnReset" id="btnReset" name="btnReset" ng-click="doReset()" > <div>クリア <span> (F3) </span> </div></button> ';
        
        return htmlText; 
    },
    scope : {
        ngModel: '='
    },
    link: function(scope, element, attrs, ctrl) {

    }
    
  }
});

