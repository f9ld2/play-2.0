'use strict';

/* jasmine specs for directives go here */

describe ('Test directive date input ', function() {
    var doc, control, scope, $compile, changeInputValue, 
    compile, templateHtml, inputElement, inputHtml,element;    
    beforeEach (module('directives'));
    beforeEach (inject(function ($injector, $sniffer) {
        $compile = $injector.get('$compile');
        scope = $injector.get('$rootScope');
        scope.result = {
                openDate: ''
            };
        changeInputValue = function (elm, value) {
          elm.val(value);
          browserTrigger(elm, $sniffer.hasEvent('input') ? 'input' : 'change');
        };
        
        compile = function(label, partition, id, name, ngModel, required,
                readOnly) {
            var htmlText = '';
            htmlText += '<cc-date-input';
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
  
  
  describe ('cc-date-input', function() {
      // test template
      it('it should render right html', function() {
          // Test partition = 2, required = true
          compile('開店日','2','openDate','openDate','result.openDate','true',null);
          expect(element.find('input').length).toEqual(1);
          expect(element.find('div').length).toEqual(1);
          expect(element.find('span').length).toEqual(1);
          inputElement = element.find('input')[0];
          inputHtml = angular.element('<div>').append(angular.element(inputElement).clone()).html();
          expect(templateHtml).toContain('<div class="cs-label-large-required cs-label">開店日<span class="cs-txt-required"> *</span></div>');
          expect(inputHtml).toContain('type="text"');
          expect(inputHtml).toContain('cc-type="date"');
          expect(inputHtml).toContain('id="openDate"');
          expect(inputHtml).toContain('name="openDate"');
          expect(inputHtml).toContain('ng-model="ngModel"');
          expect(inputHtml).toContain('ng-minlength="1"');
          expect(inputHtml).toContain('maxlength="10"');
          expect(inputHtml).toContain('cc-blur-validation');
          expect(inputHtml).toContain('required');
          expect(inputHtml).not.toContain('readonly');
          
          // partition = 3
          compile('開店日','3','openDate','openDate','result.openDate','true',null);
          expect(templateHtml).toContain('<div class="cs-label-large-required cs-label">開店日<span class="cs-txt-required"> *</span></div>');
          
          // reuiqred = false
          compile('開店日','2','openDate','openDate','result.openDate','false',null);
          inputElement = element.find('input')[0];
          inputHtml = angular.element('<div>').append(angular.element(inputElement).clone()).html();
          expect(templateHtml).toContain('<div class="cs-label-large cs-label">開店日</div>');
          expect(inputHtml).toContain('cc-blur-validation');
          expect(inputHtml).not.toContain('required');
          expect(inputHtml).not.toContain('readonly');
          
          compile('開店日','3','openDate','openDate','result.openDate','false',null);
          inputElement = element.find('input')[0];
          inputHtml = angular.element('<div>').append(angular.element(inputElement).clone()).html();
          expect(templateHtml).toContain('<div class="cs-label-large cs-label">開店日</div>');
          expect(inputHtml).toContain('cc-blur-validation');
          expect(inputHtml).not.toContain('required');
          expect(inputHtml).not.toContain('readonly');
          
          // readonly = true
          compile('開店日','2','openDate','openDate','result.openDate',null,true);
          inputElement = element.find('input')[0];
          inputHtml = angular.element('<div>').append(angular.element(inputElement).clone()).html();
          expect(templateHtml).toContain('<div class="cs-label-large cs-label">開店日</div>');
          expect(inputHtml).not.toContain('cc-blur-validation');
          expect(inputHtml).not.toContain('required');
          expect(inputHtml).toContain('readonly');
          
          compile('開店日','3','openDate','openDate','result.openDate',null,true);
          inputElement = element.find('input')[0];
          inputHtml = angular.element('<div>').append(angular.element(inputElement).clone()).html();
          expect(templateHtml).toContain('<div class="cs-label-large cs-label">開店日</div>');
          expect(inputHtml).not.toContain('cc-blur-validation');
          expect(inputHtml).not.toContain('required');
          expect(inputHtml).toContain('readonly');
          
          compile('開店日','3','openDate','openDate','result.openDate',null,false);
          inputElement = element.find('input')[0];
          inputHtml = angular.element('<div>').append(angular.element(inputElement).clone()).html();
          expect(templateHtml).toContain('<div class="cs-label-large cs-label">開店日</div>');
          expect(inputHtml).not.toContain('cc-blur-validation');
          expect(inputHtml).not.toContain('required');
          expect(inputHtml).not.toContain('readonly');
      });
      
      it ('it should check logic ...', function() {
        var symbol = "/";
        //current date
        var d = new Date();
        var year = d.getFullYear();
        var month = d.getMonth()+1;
        var day = d.getDate();
        var output = year + symbol + (month < 10 ? '0' : '') + month + symbol + (day < 10 ? '0' : '') + day;
        
        var expectNoSymbol = removeSymbol(output, symbol);
        
        var htmlText = '';
        htmlText += '<cc-date-input';
        htmlText += ' cc-label="開店日"';
        htmlText += ' cc-partition="3"'; 
        htmlText += ' id="openDate"'; 
        htmlText += ' name="openDate"';
        htmlText += ' ng-model="result.openDate"';
        htmlText += ' ng-minlength="1"'; 
        htmlText += ' ng-maxlength="10"';
        htmlText += ' cc-required=true />';
        
        var element = $compile(htmlText)(scope);
        var inputElement = angular.element(element).find('input')[0];
        var modelCtrl = angular.element(inputElement).controller('ngModel');
        
        // test case 0
        angular.element(inputElement).triggerHandler('focus');
        changeInputValue(angular.element(inputElement),'/');
        angular.element(inputElement).triggerHandler('blur');
        expect(angular.element(inputElement).val()).toEqual(output);
        expect(scope.result.openDate).toEqual(expectNoSymbol);
        expect(modelCtrl.$valid).toBe(true);

        
        // test case 0-1
        angular.element(inputElement).triggerHandler('focus');
        expect(angular.element(inputElement).val()).toEqual(expectNoSymbol);
        expect(scope.result.openDate).toEqual(expectNoSymbol);

        // test case 0-2
        angular.element(inputElement).triggerHandler('blur');
        expect(angular.element(inputElement).val()).toEqual(output);
        
        // test case 1-1
        angular.element(inputElement).triggerHandler('focus');
        changeInputValue(angular.element(inputElement),'2');
        angular.element(inputElement).triggerHandler('blur');
        // month current
        expect(angular.element(inputElement).val()).toEqual('2014/02/02');
        expect(scope.result.openDate).toEqual('20140202');
        
        // test case 1-2
        angular.element(inputElement).triggerHandler('focus');
        changeInputValue(angular.element(inputElement),'02');
        angular.element(inputElement).triggerHandler('blur');
        // month current
        expect(angular.element(inputElement).val()).toEqual('2014/02/02');
        expect(scope.result.openDate).toEqual('20140202');
        
        // test case 2-1
        angular.element(inputElement).triggerHandler('focus');
        changeInputValue(angular.element(inputElement),'102');
        angular.element(inputElement).triggerHandler('blur');
        expect(angular.element(inputElement).val()).toEqual('2014/01/02');
        expect(scope.result.openDate).toEqual('20140102');
        
        // test case 2-2
        angular.element(inputElement).triggerHandler('focus');
        changeInputValue(angular.element(inputElement),'0102');
        angular.element(inputElement).triggerHandler('blur');
        expect(angular.element(inputElement).val()).toEqual('2014/01/02');
        expect(scope.result.openDate).toEqual('20140102');
        
        // test case 2-3
        angular.element(inputElement).triggerHandler('focus');
        changeInputValue(angular.element(inputElement),'1/2');
        angular.element(inputElement).triggerHandler('blur');
        expect(angular.element(inputElement).val()).toEqual('2014/01/02');
        expect(scope.result.openDate).toEqual('20140102');
        
        // test case 2-4
        angular.element(inputElement).triggerHandler('focus');
        changeInputValue(angular.element(inputElement),'1/02');
        angular.element(inputElement).triggerHandler('blur');
        expect(angular.element(inputElement).val()).toEqual('2014/01/02');
        expect(scope.result.openDate).toEqual('20140102');
        
        // test case 2-5
        angular.element(inputElement).triggerHandler('focus');
        changeInputValue(angular.element(inputElement),'01/2');
        angular.element(inputElement).triggerHandler('blur');
        expect(angular.element(inputElement).val()).toEqual('2014/01/02');
        expect(scope.result.openDate).toEqual('20140102');
        
        // test case 2-6
        angular.element(inputElement).triggerHandler('focus');
        changeInputValue(angular.element(inputElement),'01/02');
        angular.element(inputElement).triggerHandler('blur');
        expect(angular.element(inputElement).val()).toEqual('2014/01/02');
        expect(scope.result.openDate).toEqual('20140102');
        
        // test case 3-1
        angular.element(inputElement).triggerHandler('focus');
        changeInputValue(angular.element(inputElement),'30102');
        angular.element(inputElement).triggerHandler('blur');
        expect(angular.element(inputElement).val()).toEqual('2003/01/02');
        expect(scope.result.openDate).toEqual('20030102');
        
        // test case 3-2
        angular.element(inputElement).triggerHandler('focus');
        changeInputValue(angular.element(inputElement),'030102');
        angular.element(inputElement).triggerHandler('blur');
        expect(angular.element(inputElement).val()).toEqual('2003/01/02');
        expect(scope.result.openDate).toEqual('20030102');
        
        // test case 3-3
        angular.element(inputElement).triggerHandler('focus');
        changeInputValue(angular.element(inputElement),'0030102');
        angular.element(inputElement).triggerHandler('blur');
        expect(angular.element(inputElement).val()).toEqual('2003/01/02');
        expect(scope.result.openDate).toEqual('20030102');
        //    エラー
//        expect(angular.element(inputElement).attr('title')).toEqual('Faile value');
        
        // test case 3-4
        angular.element(inputElement).triggerHandler('focus');
        changeInputValue(angular.element(inputElement),'20030102');
        angular.element(inputElement).triggerHandler('blur');
        expect(angular.element(inputElement).val()).toEqual('2003/01/02');
        expect(scope.result.openDate).toEqual('20030102');
        
        // test case 3-5
        angular.element(inputElement).triggerHandler('focus');
        changeInputValue(angular.element(inputElement),'3/1/2');
        angular.element(inputElement).triggerHandler('blur');
        expect(angular.element(inputElement).val()).toEqual('2003/01/02');
        expect(scope.result.openDate).toEqual('20030102');
        
        // test case 3-6
        angular.element(inputElement).triggerHandler('focus');
        changeInputValue(angular.element(inputElement),'3/1/02');
        angular.element(inputElement).triggerHandler('blur');
        expect(angular.element(inputElement).val()).toEqual('2003/01/02');
        expect(scope.result.openDate).toEqual('20030102');
        
        // test case 3-7
        angular.element(inputElement).triggerHandler('focus');
        changeInputValue(angular.element(inputElement),'3/01/2');
        angular.element(inputElement).triggerHandler('blur');
        expect(angular.element(inputElement).val()).toEqual('2003/01/02');
        expect(scope.result.openDate).toEqual('20030102');
        
        // test case 3-8
        angular.element(inputElement).triggerHandler('focus');
        changeInputValue(angular.element(inputElement),'3/01/02');
        angular.element(inputElement).triggerHandler('blur');
        expect(angular.element(inputElement).val()).toEqual('2003/01/02');
        expect(scope.result.openDate).toEqual('20030102');
        
        // test case 3-9
        angular.element(inputElement).triggerHandler('focus');
        changeInputValue(angular.element(inputElement),'03/1/2');
        angular.element(inputElement).triggerHandler('blur');
        expect(angular.element(inputElement).val()).toEqual('2003/01/02');
        expect(scope.result.openDate).toEqual('20030102');
        
        // test case 3-10
        angular.element(inputElement).triggerHandler('focus');
        changeInputValue(angular.element(inputElement),'03/1/02');
        angular.element(inputElement).triggerHandler('blur');
        expect(angular.element(inputElement).val()).toEqual('2003/01/02');
        expect(scope.result.openDate).toEqual('20030102');
        
        // test case 3-11
        angular.element(inputElement).triggerHandler('focus');
        changeInputValue(angular.element(inputElement),'03/01/2');
        angular.element(inputElement).triggerHandler('blur');
        expect(angular.element(inputElement).val()).toEqual('2003/01/02');
        expect(scope.result.openDate).toEqual('20030102');
        
        // test case 3-12
        angular.element(inputElement).triggerHandler('focus');
        changeInputValue(angular.element(inputElement),'03/01/02');
        angular.element(inputElement).triggerHandler('blur');
        expect(angular.element(inputElement).val()).toEqual('2003/01/02');
        expect(scope.result.openDate).toEqual('20030102');
        
        // test case 3-13
        angular.element(inputElement).triggerHandler('focus');
        changeInputValue(angular.element(inputElement),'003/1/2');
        angular.element(inputElement).triggerHandler('blur');
        expect(angular.element(inputElement).val()).toEqual('2003/01/02');
        expect(scope.result.openDate).toEqual('20030102');
        
        // test case 3-14
        angular.element(inputElement).triggerHandler('focus');
        changeInputValue(angular.element(inputElement),'003/1/02');
        angular.element(inputElement).triggerHandler('blur');
        expect(angular.element(inputElement).val()).toEqual('2003/01/02');
        expect(scope.result.openDate).toEqual('20030102');
        
        // test case 3-15
        angular.element(inputElement).triggerHandler('focus');
        changeInputValue(angular.element(inputElement),'003/01/2');
        angular.element(inputElement).triggerHandler('blur');
        expect(angular.element(inputElement).val()).toEqual('2003/01/02');
        expect(scope.result.openDate).toEqual('20030102');
        
        // test case 3-16
        angular.element(inputElement).triggerHandler('focus');
        changeInputValue(angular.element(inputElement),'003/01/02');
        angular.element(inputElement).triggerHandler('blur');
        expect(angular.element(inputElement).val()).toEqual('2003/01/02');
        expect(scope.result.openDate).toEqual('20030102');
        
        // test case 3-17
        angular.element(inputElement).triggerHandler('focus');
        changeInputValue(angular.element(inputElement),'2003/1/2');
        angular.element(inputElement).triggerHandler('blur');
        expect(angular.element(inputElement).val()).toEqual('2003/01/02');
        expect(scope.result.openDate).toEqual('20030102');
        
        // test case 3-18
        angular.element(inputElement).triggerHandler('focus');
        changeInputValue(angular.element(inputElement),'2003/1/02');
        angular.element(inputElement).triggerHandler('blur');
        expect(angular.element(inputElement).val()).toEqual('2003/01/02');
        expect(scope.result.openDate).toEqual('20030102');
        
        // test case 3-19
        angular.element(inputElement).triggerHandler('focus');
        changeInputValue(angular.element(inputElement),'2003/01/2');
        angular.element(inputElement).triggerHandler('blur');
        expect(angular.element(inputElement).val()).toEqual('2003/01/02');
        expect(scope.result.openDate).toEqual('20030102');
        
        // test case 3-20
        angular.element(inputElement).triggerHandler('focus');
        changeInputValue(angular.element(inputElement),'2003/01/02');
        angular.element(inputElement).triggerHandler('blur');
        expect(angular.element(inputElement).val()).toEqual('2003/01/02');
        expect(scope.result.openDate).toEqual('20030102');
        
        // test case 4-1
        angular.element(inputElement).triggerHandler('focus');
        changeInputValue(angular.element(inputElement),'42');
        angular.element(inputElement).triggerHandler('blur');
        expect(angular.element(inputElement).val()).toEqual('42');
        expect(scope.result.openDate).toEqual('42');
        //    エラー
//        expect(angular.element(inputElement).attr('title')).toEqual('Faile value');
        
        // test case 4-1
        angular.element(inputElement).triggerHandler('focus');
        changeInputValue(angular.element(inputElement),'1321');
        angular.element(inputElement).triggerHandler('blur');
        expect(angular.element(inputElement).val()).toEqual('1321');
        expect(scope.result.openDate).toEqual('1321');
        //    エラー
//        expect(angular.element(inputElement).attr('title')).toEqual('Faile value');
        
        // test case 4-2
        angular.element(inputElement).triggerHandler('focus');
        changeInputValue(angular.element(inputElement),'18991231');
        angular.element(inputElement).triggerHandler('blur');
        expect(angular.element(inputElement).val()).toEqual('18991231');
        expect(scope.result.openDate).toEqual('18991231');
        //    エラー
//        expect(angular.element(inputElement).attr('title')).toEqual('Faile value');
        
        // test case 4-3
        angular.element(inputElement).triggerHandler('focus');
        changeInputValue(angular.element(inputElement),'21000101');
        angular.element(inputElement).triggerHandler('blur');
        expect(angular.element(inputElement).val()).toEqual('21000101');
        expect(scope.result.openDate).toEqual('21000101');
        //    エラー
//        expect(angular.element(inputElement).attr('title')).toEqual('Faile value');
        
        // test case 4-4
        angular.element(inputElement).triggerHandler('focus');
        changeInputValue(angular.element(inputElement),'2100010');
        angular.element(inputElement).triggerHandler('blur');
        expect(angular.element(inputElement).val()).toEqual('2100010');
        expect(scope.result.openDate).toEqual('2100010');
        //    エラー
//        expect(angular.element(inputElement).attr('title')).toEqual('Faile value');
        
        // test case 4-5
        angular.element(inputElement).triggerHandler('focus');
        changeInputValue(angular.element(inputElement),'1000101');
        angular.element(inputElement).triggerHandler('blur');
        expect(angular.element(inputElement).val()).toEqual('1000101');
        expect(scope.result.openDate).toEqual('1000101');
        //    エラー
//        expect(angular.element(inputElement).attr('title')).toEqual('Faile value');
        
        // test case 4-6
        angular.element(inputElement).triggerHandler('focus');
        changeInputValue(angular.element(inputElement),'');
        angular.element(inputElement).triggerHandler('blur');
        expect(angular.element(inputElement).val()).toEqual('');
//        expect(scope.result.openDate).toEqual('');
        //    エラー
        expect(modelCtrl.$invalid).toBe(true);
//        expect(angular.element(inputElement).attr('title')).toEqual('Required');
    });
  });
 
 
  
});
