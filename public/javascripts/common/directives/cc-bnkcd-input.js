/**
 *  コード入力(銀行) : cc-bnkcd-input
 */

diretiveApp.directive('ccBnkcdInput', function(CSS_CLASS){
  return {
    restrict: 'E',
    require: 'ngModel',
    template: function (element, attrs) {
        // html text
        var htmlText = '';
        htmlText += '<div class="label-xxxx-required label">銀行<span class="txt-required"> *</span> </div>';
        htmlText += '<input type="text" cc-type="code" id="bnkCd" name="bnkCd" ng-model="result.bnkCd" required ng-minlength="4" ng-maxlength="4" cc-blur-validation />';
        
        return htmlText; 
    },
    scope : {
        ngModel: '='
    },
    link: function(scope, element, attrs, ctrl) {

    }
    
  }
});

