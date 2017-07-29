'use strict';

/* jasmine specs for directives go here */

describe ('Test directive time input ', function() {
    var doc, control, scope, $compile, changeInputValue, 
    compile, templateHtml, inputElement, inputHtml,element;    
    beforeEach (module('directives'));
    beforeEach (inject(function ($injector, $sniffer) {
        $compile = $injector.get('$compile');
        scope = $injector.get('$rootScope');
        scope.result = {
                openTime: ''
            };
        changeInputValue = function (elm, value) {
          elm.val(value);
          browserTrigger(elm, $sniffer.hasEvent('input') ? 'input' : 'change');
        };
        
        compile = function(label, partition, id, name, ngModel, required, readOnly) {
            var htmlText = '';
            htmlText += '<cc-time-input';
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
            htmlText += '/>';
            
            element = $compile(htmlText)(scope);
            templateHtml = element.html();
        };
  }));
  
  
  describe ('cc-time-input', function() {
      // test template
      it('it should render right html', function() {
          // Test partition = 2, required = true
          compile('開店時間','2','openTime','openTime','result.openTime','true');
          expect(element.find('input').length).toEqual(1);
          expect(element.find('div').length).toEqual(1);
          expect(element.find('span').length).toEqual(1);
          inputElement = element.find('input')[0];
          inputHtml = angular.element('<div>').append(angular.element(inputElement).clone()).html();
          expect(templateHtml).toContain('<div class="cs-label-large-required cs-label">開店時間<span class="cs-txt-required"> *</span></div>');
          expect(inputHtml).toContain('type="text"');
          expect(inputHtml).toContain('cc-type="time"');
          expect(inputHtml).toContain('id="openTime"');
          expect(inputHtml).toContain('name="openTime"');
          expect(inputHtml).toContain('ng-model="ngModel"');
          expect(inputHtml).toContain('ng-minlength="1"');
          expect(inputHtml).toContain('maxlength="5"');
          expect(inputHtml).toContain('cc-blur-validation');
          expect(inputHtml).toContain('required');
          expect(inputHtml).not.toContain('readonly');
          
          // partition = 3
          compile('開店時間','3','openTime','openTime','result.openTime','true');
          expect(templateHtml).toContain('<div class="cs-label-large-required cs-label">開店時間<span class="cs-txt-required"> *</span></div>');
          
          // reuiqred = false
          compile('開店時間','2','openTime','openTime','result.openTime','false');
          inputElement = element.find('input')[0];
          inputHtml = angular.element('<div>').append(angular.element(inputElement).clone()).html();
          expect(templateHtml).toContain('<div class="cs-label-large cs-label">開店時間</div>');
          expect(inputHtml).toContain('cc-blur-validation');
          expect(inputHtml).not.toContain('required');
          expect(inputHtml).not.toContain('readonly');
          
          compile('開店時間','3','openTime','openTime','result.openTime','false');
          inputElement = element.find('input')[0];
          inputHtml = angular.element('<div>').append(angular.element(inputElement).clone()).html();
          expect(templateHtml).toContain('<div class="cs-label-large cs-label">開店時間</div>');
          expect(inputHtml).toContain('cc-blur-validation');
          expect(inputHtml).not.toContain('required');
          expect(inputHtml).not.toContain('readonly');
          
          // readonly = true
          compile('開店時間','2','openTime','openTime','result.openTime',null,true);
          inputElement = element.find('input')[0];
          inputHtml = angular.element('<div>').append(angular.element(inputElement).clone()).html();
          expect(templateHtml).toContain('<div class="cs-label-large cs-label">開店時間</div>');
          expect(inputHtml).not.toContain('cc-blur-validation');
          expect(inputHtml).not.toContain('required');
          expect(inputHtml).toContain('readonly');
          
          compile('開店時間','3','openTime','openTime','result.openTime',null,true);
          inputElement = element.find('input')[0];
          inputHtml = angular.element('<div>').append(angular.element(inputElement).clone()).html();
          expect(templateHtml).toContain('<div class="cs-label-large cs-label">開店時間</div>');
          expect(inputHtml).not.toContain('cc-blur-validation');
          expect(inputHtml).not.toContain('required');
          expect(inputHtml).toContain('readonly');
          
          compile('～','3','openTime','openTime','result.openTime',null,false);
          inputElement = element.find('input')[0];
          inputHtml = angular.element('<div>').append(angular.element(inputElement).clone()).html();
          expect(templateHtml).toContain('<div class="cs-label-large cs-label">～</div>');
          expect(inputHtml).not.toContain('cc-blur-validation');
          expect(inputHtml).not.toContain('required');
          expect(inputHtml).not.toContain('readonly');
          /*          */
      });
      
      it ('it should check logic ...', function() {
        var symbol = ":";
        //current date
        var d = new Date();
        var hour = d.getHours();
        var minute = d.getMinutes();
        var output = (hour < 10 ? '0' : '') + hour + symbol + (minute < 10 ? '0' : '') + minute;
        
        var expectNoSymbol = removeSymbol(output, symbol);
        
        compile('開店時間','2','openTime','openTime','result.openTime','true');
        
        var inputElement = angular.element(element).find('input')[0];
        var modelCtrl = angular.element(inputElement).controller('ngModel');
        
        // test case 0
        angular.element(inputElement).triggerHandler('focus');
        changeInputValue(angular.element(inputElement),'/');
        angular.element(inputElement).triggerHandler('blur');
        expect(angular.element(inputElement).val()).toEqual('');
        expect(scope.result.openTime).toEqual('');
        expect(modelCtrl.$valid).toBe(false);

        angular.element(inputElement).triggerHandler('focus');
        changeInputValue(angular.element(inputElement),':');
        angular.element(inputElement).triggerHandler('blur');
        expect(angular.element(inputElement).val()).toEqual(':');
        expect(scope.result.openTime).toEqual(':');
        expect(modelCtrl.$valid).toBe(false);
        
        
        // test case 1-1
        angular.element(inputElement).triggerHandler('focus');
        changeInputValue(angular.element(inputElement),'2');
        angular.element(inputElement).triggerHandler('blur');
        // time current
//        expect(angular.element(inputElement).val()).toEqual('19:02');
//        expect(scope.result.openTime).toEqual('1902');
        expect(modelCtrl.$valid).toBe(true);
        
        // test case 1-2
        angular.element(inputElement).triggerHandler('focus');
        changeInputValue(angular.element(inputElement),'02');
        angular.element(inputElement).triggerHandler('blur');
        // time current
//        expect(angular.element(inputElement).val()).toEqual('19:02');
//        expect(scope.result.openTime).toEqual('1902');
        expect(modelCtrl.$valid).toBe(true);
        
        // test case 2-1
        angular.element(inputElement).triggerHandler('focus');
        changeInputValue(angular.element(inputElement),'102');
        angular.element(inputElement).triggerHandler('blur');
        expect(angular.element(inputElement).val()).toEqual('01:02');
        expect(scope.result.openTime).toEqual('0102');
        expect(modelCtrl.$valid).toBe(true);
        
        // test case 2-2
        angular.element(inputElement).triggerHandler('focus');
        changeInputValue(angular.element(inputElement),'0102');
        angular.element(inputElement).triggerHandler('blur');
        expect(angular.element(inputElement).val()).toEqual('01:02');
        expect(scope.result.openTime).toEqual('0102');
        expect(modelCtrl.$valid).toBe(true);
        
        // test case 2-3
        angular.element(inputElement).triggerHandler('focus');
        changeInputValue(angular.element(inputElement),'1:2');
        angular.element(inputElement).triggerHandler('blur');
        expect(angular.element(inputElement).val()).toEqual('01:02');
        expect(scope.result.openTime).toEqual('0102');
        expect(modelCtrl.$valid).toBe(true);
        
        // test case 2-4
        angular.element(inputElement).triggerHandler('focus');
        changeInputValue(angular.element(inputElement),'1:02');
        angular.element(inputElement).triggerHandler('blur');
        expect(angular.element(inputElement).val()).toEqual('01:02');
        expect(scope.result.openTime).toEqual('0102');
        expect(modelCtrl.$valid).toBe(true);
        
        // test case 2-5
        angular.element(inputElement).triggerHandler('focus');
        changeInputValue(angular.element(inputElement),'01:2');
        angular.element(inputElement).triggerHandler('blur');
        expect(angular.element(inputElement).val()).toEqual('01:02');
        expect(scope.result.openTime).toEqual('0102');
        expect(modelCtrl.$valid).toBe(true);
        
        // test case 2-6
        angular.element(inputElement).triggerHandler('focus');
        changeInputValue(angular.element(inputElement),'01:02');
        angular.element(inputElement).triggerHandler('blur');
        expect(angular.element(inputElement).val()).toEqual('01:02');
        expect(scope.result.openTime).toEqual('0102');
        expect(modelCtrl.$valid).toBe(true);
        
        // test case 2-7
        angular.element(inputElement).triggerHandler('focus');
        changeInputValue(angular.element(inputElement),'01:0');
        angular.element(inputElement).triggerHandler('blur');
        expect(angular.element(inputElement).val()).toEqual('01:00');
        expect(scope.result.openTime).toEqual('0100');
        expect(modelCtrl.$valid).toBe(true);
        
        // test case 2-8
        angular.element(inputElement).triggerHandler('focus');
        changeInputValue(angular.element(inputElement),'01:');
        angular.element(inputElement).triggerHandler('blur');
        expect(angular.element(inputElement).val()).toEqual('01:');
        expect(scope.result.openTime).toEqual('01:');
        expect(modelCtrl.$valid).toBe(false);
        
        // test case 2-9
        angular.element(inputElement).triggerHandler('focus');
        changeInputValue(angular.element(inputElement),'1:');
        angular.element(inputElement).triggerHandler('blur');
        expect(angular.element(inputElement).val()).toEqual('1:');
        expect(scope.result.openTime).toEqual('1:');
        expect(modelCtrl.$valid).toBe(false);
        
        // test case 2-10
        angular.element(inputElement).triggerHandler('focus');
        changeInputValue(angular.element(inputElement),':');
        angular.element(inputElement).triggerHandler('blur');
        expect(angular.element(inputElement).val()).toEqual(':');
        expect(scope.result.openTime).toEqual(':');
        expect(modelCtrl.$valid).toBe(false);
        
        // test case 2-11
        angular.element(inputElement).triggerHandler('focus');
        changeInputValue(angular.element(inputElement),'::');
        angular.element(inputElement).triggerHandler('blur');
        expect(angular.element(inputElement).val()).toEqual('::');
        expect(scope.result.openTime).toEqual('::');
        expect(modelCtrl.$valid).toBe(false);

        /*
*/    });
  });
 
 
  
});
