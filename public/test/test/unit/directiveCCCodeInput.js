'use strict';

/* jasmine specs for directives go here */

describe('Test directive', function() {
    var element, scope, $compile, changeInputValue, compile, templateHtml, inputElement, inputHtml;
    beforeEach(module('directives'));
    beforeEach(inject(function($injector, $sniffer) {
        // inject $compile & $roorScope
        $compile = $injector.get('$compile');
        scope = $injector.get('$rootScope');
        
        // declare result to bind data.
        scope.result = {
            storeMgrCd : ''
        };
        
        // call browser change input value
        changeInputValue = function(elm, value) {
            elm.val(value);
            browserTrigger(elm, $sniffer.hasEvent('input') ? 'input' : 'change');
        };

        // compile directive need to test.
        compile = function(label,partition,id,name,ngModel,required,readOnly,minLength,maxLength,charType) {
            var htmlText = '';
            htmlText += '<cc-code-input';
            htmlText += ' cc-label="' + label + '"';
            htmlText += ' cc-partition="' + partition + '"';
            htmlText += ' id="' + id + '"';
            htmlText += ' name="' + name + '"';
            htmlText += ' ng-model="' + ngModel + '"';
            if (required != null) {
                htmlText += ' cc-required='+ required;
            }
            if (readOnly != null) {
                htmlText += ' cc-readonly='+ readOnly;
            }
            htmlText += ' ng-minlength="' + minLength + '"';
            htmlText += ' ng-maxlength="' + maxLength + '"';
            htmlText += ' cc-chartype="' + charType + '"/>';
            element = $compile(htmlText)(scope);
            templateHtml = element.html();
        };
    }));
    describe('cc-code-input', function() {
        // test template
        it('it should render right html', function() {

            // Test partition = 2, required = true, cc-chartype= C1
            compile('店長コード','2','storeMgrCd','storeMgrCd','result.storeMgrCd','true',null,'9','9','C1');
            // it should have only one input, div, span element
            expect(element.find('input').length).toEqual(1);
            expect(element.find('div').length).toEqual(1);
            expect(element.find('span').length).toEqual(1);

            // it should contain all text below, except 'readonly'
            inputElement = element.find('input')[0];
            inputHtml = angular.element('<div>').append(angular.element(inputElement).clone()).html();
            expect(templateHtml).toContain('<div class="label-xxxx-required label">店長コード<span class="txt-required">*</span></div>');
            expect(inputHtml).toContain('type="text"');
            expect(inputHtml).toContain('cc-type="code"');
            expect(inputHtml).toContain('id="storeMgrCd"');
            expect(inputHtml).toContain('name="storeMgrCd"');
            expect(inputHtml).toContain('ng-model="ngModel"');
            expect(inputHtml).toContain('ng-minlength="9"');
            expect(inputHtml).toContain('maxlength="9"');
            expect(inputHtml).toContain('cc-blur-validation');
            expect(inputHtml).toContain('required');
            expect(inputHtml).not.toContain('readonly');
            
            // partition = 3, it should render label class is label-yyyy-required 
            compile('店長コード','3','storeMgrCd','storeMgrCd','result.storeMgrCd','true',null,'9','9','C1');
            expect(templateHtml).toContain('<div class="label-yyyy-required label">店長コード<span class="txt-required">*</span></div>');
            
            // required = false
            compile('店長コード','2','storeMgrCd','storeMgrCd','result.storeMgrCd','false',null,'9','9','C1');
            inputElement = element.find('input')[0];
            inputHtml = angular.element('<div>').append(angular.element(inputElement).clone()).html();
            // it should 
            expect(templateHtml).toContain('<div class="label-xxxx label">店長コード</div>');
            expect(inputHtml).toContain('cc-blur-validation');
            // it should not contain 'required' & 'readonly'.v
            expect(inputHtml).not.toContain('required');
            expect(inputHtml).not.toContain('readonly');
            
            // readonly = true, it should contain 'readonly' and shouldn't contain 'required' & 'cc-blur-validation'.
            compile('店長コード','2','storeMgrCd','storeMgrCd','result.storeMgrCd',null,true,'9','9','C1');
            inputElement = element.find('input')[0];
            inputHtml = angular.element('<div>').append(angular.element(inputElement).clone()).html();
            expect(templateHtml).toContain('<div class="label-xxxx label">店長コード</div>');
            expect(inputHtml).not.toContain('cc-blur-validation');
            expect(inputHtml).not.toContain('required');
            expect(inputHtml).toContain('readonly');
            
            // cc-chartype = C2, it should contain 'cc-type="alphabet'.
            compile('店長コード','2','storeMgrCd','storeMgrCd','result.storeMgrCd','true',null,'9','9','C2');
            inputElement = element.find('input')[0];
            inputHtml = angular.element('<div>').append(angular.element(inputElement).clone()).html();
            expect(inputHtml).toContain('cc-type="alphabet"');
            
            // cc-chartype = C3, it should contain 'cc-type="alphanum"'.
            compile('店長コード','2','storeMgrCd','storeMgrCd','result.storeMgrCd','true',null,'9','9','C3');
            inputElement = element.find('input')[0];
            inputHtml = angular.element('<div>').append(angular.element(inputElement).clone()).html();
            expect(inputHtml).toContain('cc-type="alphanum"');
            
            // cc-chartype = C7 : out of scope, it should throw exception.
            function errorFunctionWrapper() {
                compile('店長コード','3','storeMgrCd','storeMgrCd','result.storeMgrCd','true',null,'9','9','C7');
            }
            expect(errorFunctionWrapper).toThrow();
        });
        
        // test logic
        it('it should be right binding value and view value', function() {
            compile('店長コード','3','storeMgrCd','storeMgrCd','result.storeMgrCd','true',null,'9','9','C1');
            inputElement = element.find('input')[0];
             var modelCtrl = angular.element(inputElement).controller('ngModel');
             // change input value (valid value: 123456789)
             changeInputValue(angular.element(inputElement), '123456789');
             // lost focus
             angular.element(inputElement).triggerHandler('blur');
             // it should show view value: 123456789 and bind value to result.storeMgrCd.
             expect(angular.element(inputElement).val()).toEqual('123456789');
             expect(scope.result.storeMgrCd).toEqual('123456789');
             expect(modelCtrl.$valid).toBe(true);
             
             // view value shouldn't change when focus on.
             angular.element(inputElement).triggerHandler('focus');
             expect(angular.element(inputElement).val()).toEqual('123456789');
        });
        
        it('it should be catch required validation ', function() {
            compile('店長コード','3','storeMgrCd','storeMgrCd','result.storeMgrCd','true',null,'9','9','C1');
            inputElement = element.find('input')[0];
             var modelCtrl = angular.element(inputElement).controller('ngModel');
             // change input value (invalid value: '')
             changeInputValue(angular.element(inputElement), '');
             // lost focus
             angular.element(inputElement).triggerHandler('blur');
             // view value should be ''.
             expect(angular.element(inputElement).val()).toEqual('');
             // it can't bind value to result.storeMgrCd 
             expect(scope.result.storeMgrCd).toEqual(undefined);
             // it should catch required validity
             expect(modelCtrl.$invalid).toBe(true);
             expect(modelCtrl.$error.required).toBe(true);
             // focus, view value should be ''.
             angular.element(inputElement).triggerHandler('focus');
             expect(angular.element(inputElement).val()).toEqual('');
        });
        
        it('it should be catch minlength validation ', function() {
            compile('店長コード','3','storeMgrCd','storeMgrCd','result.storeMgrCd','true',null,'9','9','C1');
            inputElement = element.find('input')[0];
             var modelCtrl = angular.element(inputElement).controller('ngModel');
             // change input value (invalid value: '123')
             changeInputValue(angular.element(inputElement), '123');
             // lost focus
             angular.element(inputElement).triggerHandler('blur');
             // view value should be '123'.
             expect(angular.element(inputElement).val()).toEqual('123');
             // it can't bind value to result.storeMgrCd 
             expect(scope.result.storeMgrCd).toEqual(undefined);
             // it should catch minlength validity
             expect(modelCtrl.$invalid).toBe(true);
             expect(modelCtrl.$error.minlength).toBe(true);
             // focus, view value should be '123'.
             angular.element(inputElement).triggerHandler('focus');
             expect(angular.element(inputElement).val()).toEqual('123');
        });
    });
});
