var app = angular.module('test', ['ui']);

app.directive('focus', function() {
  return {
    restrict: 'A',
    link: function($scope,elem,attrs) {

      elem.bind('keydown', function(e) {
        var code = e.keyCode || e.which;
        if (code === 13) {
          e.preventDefault();
          elem.next().focus();
        }
      });
    }
  }
});

app.directive('test', function($filter) {
  return {
    restrict: 'A',
    require: 'ngModel',
    link: function(scope, element, attrs, controller) {

      element.bind('blur', function() {
//        controller.$viewValue = controller.$modelValue + "A";
//        controller.$viewValue = $filter('number')(controller.$modelValue);
        var date = controller.$modelValue;
        controller.$viewValue = date.substr(0, 4) + "/" + date.substr(4, 2) + "/" + date.substr(6, 2);
        controller.$render();
 });
//        controller.$formatters.push(function (modelValue) {
//        var value = modelValue + "A";
//            return value;
//        });

//        controller.$parsers.push(function (viewValue) {
//        var value = viewValue + "B";
//            return value;
//        });          
    }
  }
});

app.directive('blurValidation', function($compile) {
  return {
    restrict: 'A',
    replace: true,
    link: function(scope, element, attrs, ctrl, $sniffer, $browser) {
      element.bind('blur', function () {

        parent = element.parents("form");
        form = "";
        console.log("------- " + parent.size());
        inputname = "";
        if (parent.size() > 0) {
          inputname = parent.attr("name") + "."
        }
        inputname = inputname + attrs.name;
        console.log("======= " + inputname);
        console.log("**** " + attrs.ngName);
        if (scope.$eval(inputname).$invalid) {
          console.log("=-=-= ERROR!!");
          element.addClass('error');
          message = "AAAAAAAAABBBBBBBBCCCCCC";
          element.popover('destroy');
          element.popover({
            content: message,
            html: false,
            trigger: "focus",
            placement: "bottom"
          });
//          element.focus();
        } else {
          element.popover('destroy');
          element.removeClass('error');
        }
      });
    }
  }
});

app.directive("ccTest", function() {
  return {
    restrict: 'E',
    require: '^ngModel',
    replace: true,
    scope: {
      ngModel: '=',
      ngMaxlength: '@',
      label: '@',
      id: '@',
      formname: '@',
      ccDate: '='
    },
    template: '<span name="t"><label id="{{id}}-label" for="{{id}}-input">{{label}}</label><input type="text" id="{{id}}-input" name="{{id}}input" ng-model="ngModel" ng-maxlength="ngMaxlength" ng-class="{error : {{formname}}.{{id}}input.$invalid}" ui-event="{ blur : \'getMemberName(ngModel)\' }"><div id="{{id}}-name">{{memberName}}</div><button>参照</button></span>',
    controller: ['$scope', '$element', '$http', function($scope, $element, $http) {
      $scope.memberNo = "";
      console.log("date1 = " + $scope.date);
      $scope.getMemberName = function(memberNo) {
          console.log("======= memberNo old = " + $scope.memberNo);
          console.log("======= memberNo new = " + memberNo);
        if (memberNo == "") {
          $scope.memberName = "";
        } else
        if (memberNo != $scope.memberNo) {
          console.log("======= memberNo = " + memberNo);
          $http.get('/member/' + memberNo).
            success(function(data) {
              $scope.memberName = data.memberName;
            }).
            error(function() {
               $element.addClass("error");
              $scope.memberName = "";
            });
        }
        $scope.memberNo = memberNo;
      };
      $scope.isInvalid = function(invalid) {
        console.log("invalid = " + invalid);
        return invalid;
      };
    }],
    link: function(scope, elem, attrs) {
      console.log("elem.text()=" + elem.text());
      console.log("attrs.formname = " + attrs.formname);
      console.log("attrs.id = " + attrs.id);
      scope.formname = attrs.formname;
      
      console.log("attrs.ccDate = " + attrs.ccDate);
      
      console.log("date = " + scope.date);

      scope.$parent.$watch(attrs.ccDate, function(newValue, oldValue){
        console.log("newValue = " + newValue);
        console.log("oldValue = " + oldValue);
      })
    }
  };
});
