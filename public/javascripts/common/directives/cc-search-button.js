/**
 *  コマンドボタン(業務共通) : cc-search-button 
 */

diretiveApp.directive('ccSearchButton', function(CSS_CLASS){
  return {
    restrict: 'E',
    template: function (element, attrs) {
        // html text
        var htmlText = '';
        htmlText += '<button class="btnSearch" id="btnSearch" name="btnSearch" ng-click="doSearch()" > <div>検索 <span> (F10) </span></div> </button> ';
        
        return htmlText; 
    },
    scope : {
        ngModel: '='
    },
    link: function(scope, element, attrs, ctrl) {

    }
    
  }
});

