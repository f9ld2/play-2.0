/**
 *  コマンドボタン(業務共通) : cc-edit-button
 */

diretiveApp.directive('ccEditButton', function(CSS_CLASS){
  return {
    restrict: 'E',
    template: function (element, attrs) {
        // html text
        var htmlText = '';
        htmlText += '<button class="btnEdit" id="btnEdit" name="btnEdit" ng-click="doEdit()" ><div> 編集 <span> (F9) </span></div> </button> ';
        
        return htmlText; 
    },
    scope : {
        ngModel: '='
    },
    link: function(scope, element, attrs, ctrl) {

    }
    
  }
});

