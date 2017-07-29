/**
 *  コマンドボタン(業務共通) : cc-back-button 
 */

diretiveApp.directive('ccBackButton', function(CSS_CLASS){
  return {
    restrict: 'E',
    template: function (element, attrs) {
        // html text
        var htmlText = '';
        htmlText += '<button class="btnBack" id="btnBack" name="btnBack" ng-click="doBack()" > <div>戻る <span> (F4) </span> <div> </button> ';
        
        return htmlText; 
    },
    scope : {
        ngModel: '='
    },
    link: function(scope, element, attrs, ctrl) {

    }
    
  }
});

