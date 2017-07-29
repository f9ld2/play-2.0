'use strict';

/* jasmine specs for directives go here */

describe('Test cc-number-input directive', function() {
    var element, scope, $compile, changeInputValue, compile, templateHtml, inputElement, inputHtml;
    beforeEach(module('directives'));
    beforeEach(inject(function($injector, $sniffer) {
        $compile = $injector.get('$compile');
        scope = $injector.get('$rootScope');
        scope.result = {
                genka : ''
        };

        //change value
        changeInputValue = function(elm, value) {
            elm.val(value);
            browserTrigger(elm, $sniffer.hasEvent('input') ? 'input' : 'change');
        };

        //compile template
        compile = function(label, partition, id, name, ngModel, required, readOnly, minValue, maxValue) {
            var htmlText = '';
            htmlText += '<cc-number-input';
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
            htmlText += ' cc-min="' + minValue + '"';
            htmlText += ' cc-max="' + maxValue + '"';
            htmlText += ' />';
            element = $compile(htmlText)(scope);
            templateHtml = element.html();
        };

    }));

    describe('cc-number-input', function() {
        // test template
        it('it should render right html', function() {
            // Test partition = 2, required = true
            compile('原単価','2','genka','genka','result.genka','true', null, '-9999.9', '9999.9');
            expect(element.find('input').length).toEqual(1);
            expect(element.find('div').length).toEqual(1);
            expect(element.find('span').length).toEqual(1);
            inputElement = element.find('input')[0];
            inputHtml = angular.element('<div>').append(angular.element(inputElement).clone()).html();
            expect(templateHtml).toContain('<div class="label-xxxx-required label">原単価<span class="txt-required">*</span></div>');
            
            expect(inputHtml).toContain('type="text"');
            expect(inputHtml).toContain('cc-type="number"');
            expect(inputHtml).toContain('id="genka"');
            expect(inputHtml).toContain('name="genka"');
            expect(inputHtml).toContain('ng-model="ngModel"');
            expect(inputHtml).toContain('ng-minlength="1"');
            expect(inputHtml).toContain('maxlength="8"');
            expect(inputHtml).toContain('cc-blur-validation');
            expect(inputHtml).toContain('required');
            expect(inputHtml).toContain('cc-min="-9999.9"');
            expect(inputHtml).toContain('cc-max="9999.9"');
            expect(inputHtml).toContain('cc-digits-integer="4"');
            expect(inputHtml).toContain('cc-digits-franction="1"');
            expect(inputHtml).not.toContain('readonly');
        });
        
        it('it should render right html incase of readonly', function() {

            compile('原単価','3','genka','genka','result.genka', null, 'true', '-9999.9', '9999.9');
            expect(element.find('input').length).toEqual(1);
            expect(element.find('div').length).toEqual(1);
            inputElement = element.find('input')[0];
            inputHtml = angular.element('<div>').append(angular.element(inputElement).clone()).html();
            expect(templateHtml).toContain('<div class="label-yyyy label">原単価</div>');
            
            expect(inputHtml).toContain('type="text"');
            expect(inputHtml).toContain('cc-type="number"');
            expect(inputHtml).toContain('id="genka"');
            expect(inputHtml).toContain('name="genka"');
            expect(inputHtml).toContain('ng-model="ngModel"');
            expect(inputHtml).not.toContain('ng-minlength="1"');
            expect(inputHtml).toContain('maxlength="8"');
            expect(inputHtml).not.toContain('cc-blur-validation');
            expect(inputHtml).not.toContain('required');
            expect(inputHtml).not.toContain('cc-min="-9999.9"');
            expect(inputHtml).not.toContain('cc-max="9999.9"');
            expect(inputHtml).not.toContain('cc-digits-integer="4"');
            expect(inputHtml).not.toContain('cc-digits-franction="1"');
            expect(inputHtml).toContain('readonly');
        });
        
        it('it should render right html incase of label divide zero', function() {

            compile('原単価','','genka','genka','result.genka', null, 'true', '-9999.9', '9999.9');
            expect(element.find('input').length).toEqual(1);
            expect(element.find('div').length).toEqual(1);
            inputElement = element.find('input')[0];
            inputHtml = angular.element('<div>').append(angular.element(inputElement).clone()).html();
            expect(templateHtml).toContain('<div class="label-divide-zero label">原単価</div>');
        });
        
        it('it should render right html incase of have minus symbol', function() {

            compile('原単価','3','genka','genka','result.genka', 'true', null, '-9999.9', '9999.9');
            expect(element.find('input').length).toEqual(1);
            expect(element.find('div').length).toEqual(1);
            inputElement = element.find('input')[0];
            inputHtml = angular.element('<div>').append(angular.element(inputElement).clone()).html();
            expect(templateHtml).toContain('cc-signed');
        });
        
        it('it should render right html incase of have no minus symbol', function() {

            compile('原単価','3','genka','genka','result.genka', 'true', null, '9999.9', '9999.9');
            expect(element.find('input').length).toEqual(1);
            expect(element.find('div').length).toEqual(1);
            inputElement = element.find('input')[0];
            inputHtml = angular.element('<div>').append(angular.element(inputElement).clone()).html();
            expect(templateHtml).not.toContain('cc-signed');
        });
        
        it('it should render right html incase of have 3 digist in int part', function() {

            compile('原単価','3','genka','genka','result.genka', 'true', null, '-999.9', '99.9');
            expect(element.find('input').length).toEqual(1);
            expect(element.find('div').length).toEqual(1);
            inputElement = element.find('input')[0];
            inputHtml = angular.element('<div>').append(angular.element(inputElement).clone()).html();
            expect(templateHtml).toContain('cc-digits-integer="3"');
        });
        
        it('it should render right html incase of have 1 digist in fraction part', function() {

            compile('原単価','3','genka','genka','result.genka', 'true', null, '-9999.9', '9999.9');
            expect(element.find('input').length).toEqual(1);
            expect(element.find('div').length).toEqual(1);
            inputElement = element.find('input')[0];
            inputHtml = angular.element('<div>').append(angular.element(inputElement).clone()).html();
            expect(templateHtml).toContain('cc-digits-franction="1"');
        });
        
        
        // test logic
        it('it should be right binding value and view value', function() {
            compile('原単価','3','genka','genka','result.genka', 'true', null, '-9999.9', '9999.9');
            inputElement = element.find('input')[0];
            var modelCtrl = angular.element(inputElement).controller('ngModel');
            changeInputValue(angular.element(inputElement), '1234');
            angular.element(inputElement).triggerHandler('blur');
            expect(angular.element(inputElement).val()).toEqual('1,234');
            expect(scope.result.genka).toEqual('1,234');
            angular.element(inputElement).triggerHandler('focus');
            expect(angular.element(inputElement).val()).toEqual('1234');
        });
        
        it('it should be catch required validation ', function() {
            compile('原単価','3','genka','genka','result.genka', 'true', null, '-9999.9', '9999.9');
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
        
        it('it should be catch ccMax validation ', function() {
            compile('原単価','3','genka','genka','result.genka', 'true', null, '-9999.9', '9999.9');
            inputElement = element.find('input')[0];
             var modelCtrl = angular.element(inputElement).controller('ngModel');
             // change input value (invalid value: '123')
             changeInputValue(angular.element(inputElement), '99999.9');
             // lost focus
             angular.element(inputElement).triggerHandler('blur');
             // view value should be '123'.
             expect(angular.element(inputElement).val()).toEqual('99999.9');
             // it can't bind value to result.genka 
             expect(scope.result.genka).toEqual(undefined);
             // it should catch minlength validity
             expect(modelCtrl.$invalid).toBe(true);
             expect(modelCtrl.$error.ccMax).toBe(true);
             // focus, view value should be '123'.
             angular.element(inputElement).triggerHandler('focus');
             expect(angular.element(inputElement).val()).toEqual('99999.9');
        });
        
        it('it should be catch ccMin validation ', function() {
            compile('原単価','3','genka','genka','result.genka', 'true', null, '-9999.9', '9999.9');
            inputElement = element.find('input')[0];
             var modelCtrl = angular.element(inputElement).controller('ngModel');
             // change input value (invalid value: '123')
             changeInputValue(angular.element(inputElement), '-10000.9');
             // lost focus
             angular.element(inputElement).triggerHandler('blur');
             // view value should be '123'.
             expect(angular.element(inputElement).val()).toEqual('-10000.9');
             // it can't bind value to result.genka 
             expect(scope.result.genka).toEqual('');
             // it should catch minlength validity
             expect(modelCtrl.$invalid).toBe(true);
             expect(modelCtrl.$error.ccMin).toBe(true);
             // focus, view value should be '123'.
             angular.element(inputElement).triggerHandler('focus');
             expect(angular.element(inputElement).val()).toEqual('-10000.9');
        });
    });
});
