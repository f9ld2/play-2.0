'use strict';

/* jasmine specs for directives go here */

describe('Test directive', function() {
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
            htmlText += ' cc-min="' + minValue + '"';
            htmlText +=  '/>';
            element = $compile(htmlText)(scope);
            // Render the template as a string
            templateHtml = element.html();
        };
    }));

    describe('cc-min', function() {
        // Case 1
        it('Test case 1: Input value is less than min value', function() {
            compile('result.genka', '10');
            var modelCtrl = element.controller('ngModel');
            //value input
            changeInputValue(element, '9');
            expect(modelCtrl.$error.ccMin).toBe(true);
        });
        
        it('Test case 2: The input value is greater than the min value ', function() {
            compile('result.genka', '10');
            var modelCtrl = element.controller('ngModel');
            changeInputValue(element, '11');
            expect(modelCtrl.$error.ccMin).toBe(false);
        });
        
        it('Test case 3: The input value is equal the min value', function() {
            compile('result.genka', '10');
            var modelCtrl = element.controller('ngModel');
            changeInputValue(element, '10');
            expect(modelCtrl.$error.ccMin).toBe(false);
        });
        
        it('Test case 4: The input value is less than the min value', function() {
            compile('result.genka', '-1,234.5');
            var modelCtrl = element.controller('ngModel');
            changeInputValue(element, '-2,222.2'); //input value
            expect(modelCtrl.$error.ccMin).toBe(true);
        });
    });
});
