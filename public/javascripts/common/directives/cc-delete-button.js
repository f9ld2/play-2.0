/**
 *  コマンドボタン(業務共通) : cc-delete-button 
 */

diretiveApp.directive('ccDeleteButton', function(CSS_CLASS){
  return {
    restrict: 'E',
    template: function (element, attrs) {
        // html text
        var htmlText = '';
        htmlText += '<button class="btnDelete" id="btnDelete" name="btnDelete" ng-click="doDelete()" > <div>削除 <span> (Alt+Del) </span> </div></button> ';
        
        return htmlText; 
    },
    scope : {
        ngModel: '='
    },
    link: function(scope, element, attrs, ctrl) {

    }
    
  }
});

