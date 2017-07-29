'use strict';

/* jasmine specs for directives go here */

describe('Test directive ccValidateEqual', function() {
    var element, scope, $compile, changeInputValue, compile, templateHtml, inputElement, inputHtml;
    beforeEach(module('directives'));
    beforeEach(inject(function($injector, $sniffer) {
        $compile = $injector.get('$compile');
        scope = $injector.get('$rootScope');

        scope.result = {
            genka : ''
        };

        changeInputValue = function(elm, value) {
            elm.val(value);
            browserTrigger(elm, $sniffer.hasEvent('input') ? 'input' : 'change');
        };
        
        compile = function(ngModel, minValue) {
            
            var htmlText = '<';
            htmlText += 'input name="testInput" ';
            htmlText += ' ng-model="' + ngModel + '"';
            htmlText += ' cc-validate-equal="' + minValue + '"';
            htmlText +=  '/>';
            element = $compile(htmlText)(scope);
            // Render the template as a string
            templateHtml = element.html();
        };
    }));

    describe('cc-validate-equal', function() {
        // Case 1
        it('Test case 1: Input value is less than validate value', function() {
            compile('result.genka', '2000');
            var modelCtrl = element.controller('ngModel');
            //value input
            changeInputValue(element, '9');
            expect(modelCtrl.$error.ccValidateEqual).toBe(true);
        });
        
        it('Test case 2: The input value is greater than validate value ', function() {
            compile('result.genka', '2000');
            var modelCtrl = element.controller('ngModel');
            changeInputValue(element, '2011');
            expect(modelCtrl.$error.ccValidateEqual).toBe(true);
        });
        
        it('Test case 3: The input value is equal the validate value', function() {
            compile('result.genka', '2000');
            var modelCtrl = element.controller('ngModel');
            changeInputValue(element, '2000');
            expect(modelCtrl.$error.ccValidateEqual).toBe(false);
        });
    });
});
