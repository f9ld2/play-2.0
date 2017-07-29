/**
 *  コマンドボタン(業務共通) : cc-save-button 
 */

diretiveApp.directive('ccSaveButton', function(CSS_CLASS){
  return {
    restrict: 'E',
    template: function (element, attrs) {
        // html text
        var htmlText = '';
        htmlText += '<button class="btnSave" id="btnSave" name="btnSave" ng-click="doSave()" > <div>保存 <span> (F12) </span> </div></button> ';
        
        return htmlText; 
    },
    scope : {
        ngModel: '='
    },
    link: function(scope, element, attrs, ctrl) {

    }
    
  }
});

