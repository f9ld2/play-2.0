'use strict';

/* jasmine specs for directives go here */

describe ('Test directive string input ', function() {
    var doc, control, scope, $compile, changeInputValue, 
    compile, templateHtml, inputElement, inputHtml,element;
    beforeEach (module('directives'));
    beforeEach (inject(function ($injector, $sniffer) {
        $compile = $injector.get('$compile');
        scope = $injector.get('$rootScope');
        scope.result = {
                storeName: ''
        };
        changeInputValue = function (elm, value) {
          elm.val(value);
          browserTrigger(elm, $sniffer.hasEvent('input') ? 'input' : 'change');
        };
        
        compile = function(label, partition, id, name, ngModel, required,
                readOnly, minLength, maxlength, chartype) {
            var htmlText = '';
            htmlText += '<cc-string-input';
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
            htmlText += ' ng-maxlength="' + maxlength + '"';
            htmlText += ' cc-chartype="' + chartype + '"';
            htmlText += '></cc-string-input>';

            element = $compile(htmlText)(scope);
            templateHtml = element.html();
        };
  }));
  
  
  describe ('cc-string-input', function() {
      // test template
      it('it should render right html', function() {
          // Test partition = 2, required = true
          compile('店舗名称','2','storeName','storeName','result.storeName',true ,null,'1','12','S1');
          expect(element.find('input').length).toEqual(1);
          expect(element.find('div').length).toEqual(1);
          expect(element.find('span').length).toEqual(1);
          inputElement = element.find('input')[0];
          inputHtml = angular.element('<div>').append(angular.element(inputElement).clone()).html();
          expect(templateHtml).toContain('<div class="cs-label-large-required cs-label">店舗名称<span class="cs-txt-required"> *</span></div>');
          expect(inputHtml).toContain('type="text"');
          expect(inputHtml).toContain('cc-type="zenkaku"');
          expect(inputHtml).toContain('id="storeName"');
          expect(inputHtml).toContain('name="storeName"');
          expect(inputHtml).toContain('ng-model="ngModel"');
          expect(inputHtml).toContain('ng-minlength="1"');
          expect(inputHtml).toContain('ng-maxlength="6"');
          expect(inputHtml).toContain('cc-blur-validation');
          expect(inputHtml).toContain('required');
          expect(inputHtml).not.toContain('readonly');

          compile('店舗名称','2','storeName','storeName','result.storeName',true ,null,'1','12','S2');
          inputElement = element.find('input')[0];
          inputHtml = angular.element('<div>').append(angular.element(inputElement).clone()).html();
          expect(inputHtml).toContain('cc-type="hankaku"');
          
          compile('店舗名称','2','storeName','storeName','result.storeName',true ,null,'1','12','S3');
          inputElement = element.find('input')[0];
          inputHtml = angular.element('<div>').append(angular.element(inputElement).clone()).html();
          expect(inputHtml).toContain('cc-type="postal"');
          
          compile('店舗名称','2','storeName','storeName','result.storeName',true ,null,'1','12','S4');
          inputElement = element.find('input')[0];
          inputHtml = angular.element('<div>').append(angular.element(inputElement).clone()).html();
          expect(inputHtml).toContain('cc-type="phone"');
          
          compile('店舗名称','2','storeName','storeName','result.storeName',true ,null,'1','12','S5');
          inputElement = element.find('input')[0];
          inputHtml = angular.element('<div>').append(angular.element(inputElement).clone()).html();
          expect(inputHtml).toContain('cc-type="email"');
          
          compile('店舗名称','2','storeName','storeName','result.storeName',true ,null,'1','12','S6');
          inputElement = element.find('input')[0];
          inputHtml = angular.element('<div>').append(angular.element(inputElement).clone()).html();
          expect(inputHtml).toContain('cc-type="url"');
          
          // partition = 3
          compile('店舗名称','3','storeName','storeName','result.storeName',true,null,'1','10','S1');
          expect(templateHtml).toContain('<div class="cs-label-large-required cs-label">店舗名称<span class="cs-txt-required"> *</span></div>');

          // reuiqred = false
          compile('店舗名称','2','storeName','storeName','result.storeName',false,null,'1','10','S1');
          inputElement = element.find('input')[0];
          inputHtml = angular.element('<div>').append(angular.element(inputElement).clone()).html();
          expect(templateHtml).toContain('<div class="cs-label-large cs-label">店舗名称</div>');
          expect(inputHtml).toContain('cc-blur-validation');
          expect(inputHtml).not.toContain('required');
          expect(inputHtml).not.toContain('readonly');
          
          compile('店舗名称','3','storeName','storeName','result.storeName',false,null,'1','10','S1');
          inputElement = element.find('input')[0];
          inputHtml = angular.element('<div>').append(angular.element(inputElement).clone()).html();
          expect(templateHtml).toContain('<div class="cs-label-large cs-label">店舗名称</div>');
          expect(inputHtml).toContain('cc-blur-validation');
          expect(inputHtml).not.toContain('required');
          expect(inputHtml).not.toContain('readonly');
          
          // readonly = true
          compile('店舗名称','2','storeName','storeName','result.storeName',null,true,'1','10','S1');
          inputElement = element.find('input')[0];
          inputHtml = angular.element('<div>').append(angular.element(inputElement).clone()).html();
          expect(templateHtml).toContain('<div class="cs-label-large cs-label">店舗名称</div>');
          expect(inputHtml).not.toContain('cc-blur-validation');
          expect(inputHtml).not.toContain('required');
          expect(inputHtml).toContain('readonly');
          
          compile('店舗名称','3','storeName','storeName','result.storeName',null,true,'1','10','S1');
          inputElement = element.find('input')[0];
          inputHtml = angular.element('<div>').append(angular.element(inputElement).clone()).html();
          expect(templateHtml).toContain('<div class="cs-label-large cs-label">店舗名称</div>');
          expect(inputHtml).not.toContain('cc-blur-validation');
          expect(inputHtml).not.toContain('required');
          expect(inputHtml).toContain('readonly');
          
          compile('～','','storeName','storeName','result.storeName',null,false,'1','10','S1');
          inputElement = element.find('input')[0];
          inputHtml = angular.element('<div>').append(angular.element(inputElement).clone()).html();
          expect(templateHtml).toContain('<div class="cs-label-divide-zero cs-label">～</div>');
          expect(inputHtml).not.toContain('cc-blur-validation');
          expect(inputHtml).not.toContain('required');
          expect(inputHtml).not.toContain('readonly');
          
          compile('店舗名称','','storeName','storeName','result.storeName',null,false,'1','10','S1');
          inputElement = element.find('input')[0];
          inputHtml = angular.element('<div>').append(angular.element(inputElement).clone()).html();
          expect(templateHtml).toContain('<div class="cs-label-divide-zero cs-label">店舗名称</div>');
          expect(inputHtml).not.toContain('cc-blur-validation');
          expect(inputHtml).not.toContain('required');
          expect(inputHtml).not.toContain('readonly');
      });
      
      it ('it should check logic S1 ...', function() {
          compile('店舗名称','2','storeName','storeName','result.storeName',true ,null,'1','12','S1');
          var inputElement = angular.element(element).find('input')[0];
          var modelCtrl = angular.element(inputElement).controller('ngModel');
          
          // test case 0
          angular.element(inputElement).triggerHandler('focus');
          changeInputValue(angular.element(inputElement),'1');
          angular.element(inputElement).triggerHandler('blur');
          expect(angular.element(inputElement).val()).toEqual('1');
          expect(scope.result.storeName).toEqual('1');
          expect(modelCtrl.$valid).toBe(false);
          
          // test case 1
          angular.element(inputElement).triggerHandler('focus');
          changeInputValue(angular.element(inputElement),'あ');
          angular.element(inputElement).triggerHandler('blur');
          expect(angular.element(inputElement).val()).toEqual('あ');
          expect(scope.result.storeName).toEqual('あ');
          expect(modelCtrl.$valid).toBe(true);
          
          // test case 2
          angular.element(inputElement).triggerHandler('focus');
          changeInputValue(angular.element(inputElement),'　');
          angular.element(inputElement).triggerHandler('blur');
          expect(angular.element(inputElement).val()).toEqual('　');
          expect(scope.result.storeName).toEqual(undefined);
          expect(modelCtrl.$valid).toBe(false);
          
          // test case 3
          angular.element(inputElement).triggerHandler('focus');
          changeInputValue(angular.element(inputElement),'あ123');
          angular.element(inputElement).triggerHandler('blur');
          expect(angular.element(inputElement).val()).toEqual('あ123');
          expect(scope.result.storeName).toEqual('あ123');
          expect(modelCtrl.$valid).toBe(false);
          
          // test case 4
          angular.element(inputElement).triggerHandler('focus');
          changeInputValue(angular.element(inputElement),'ああああ');
          angular.element(inputElement).triggerHandler('blur');
          expect(angular.element(inputElement).val()).toEqual('ああああ');
          expect(scope.result.storeName).toEqual('ああああ');
          expect(modelCtrl.$valid).toBe(true);
          
          // test case 5
          angular.element(inputElement).triggerHandler('focus');
          changeInputValue(angular.element(inputElement),'ああああああああ');
          angular.element(inputElement).triggerHandler('blur');
          expect(angular.element(inputElement).val()).toEqual('ああああああああ');
          expect(scope.result.storeName).toEqual(undefined);
          expect(modelCtrl.$valid).toBe(false);
      });
      
      it ('it should check logic S2 ...', function() {
          compile('店舗名称','2','storeName','storeName','result.storeName',true ,null,'1','12','S2');
          var inputElement = angular.element(element).find('input')[0];
          var modelCtrl = angular.element(inputElement).controller('ngModel');
          
          // test case 0
          angular.element(inputElement).triggerHandler('focus');
          changeInputValue(angular.element(inputElement),'1');
          angular.element(inputElement).triggerHandler('blur');
          expect(angular.element(inputElement).val()).toEqual('1');
          expect(scope.result.storeName).toEqual('1');
          expect(modelCtrl.$valid).toBe(true);
          
          // test case 1
          angular.element(inputElement).triggerHandler('focus');
          changeInputValue(angular.element(inputElement),'ｶﾅ');
          angular.element(inputElement).triggerHandler('blur');
          expect(angular.element(inputElement).val()).toEqual('ｶﾅ');
          expect(scope.result.storeName).toEqual('ｶﾅ');
          expect(modelCtrl.$valid).toBe(true);
          
          // test case 2
          angular.element(inputElement).triggerHandler('focus');
          changeInputValue(angular.element(inputElement),'AAA');
          angular.element(inputElement).triggerHandler('blur');
          expect(angular.element(inputElement).val()).toEqual('AAA');
          expect(scope.result.storeName).toEqual('AAA');
          expect(modelCtrl.$valid).toBe(true);
          
          // test case 3
          angular.element(inputElement).triggerHandler('focus');
          changeInputValue(angular.element(inputElement),'aaa');
          angular.element(inputElement).triggerHandler('blur');
          expect(angular.element(inputElement).val()).toEqual('aaa');
          expect(scope.result.storeName).toEqual('aaa');
          expect(modelCtrl.$valid).toBe(true);
          
          // test case 4
          angular.element(inputElement).triggerHandler('focus');
          changeInputValue(angular.element(inputElement),'Aaｱ');
          angular.element(inputElement).triggerHandler('blur');
          expect(angular.element(inputElement).val()).toEqual('Aaｱ');
          expect(scope.result.storeName).toEqual('Aaｱ');
          expect(modelCtrl.$valid).toBe(true);
          
          // test case 4
          angular.element(inputElement).triggerHandler('focus');
          changeInputValue(angular.element(inputElement),',｡/\\');
          angular.element(inputElement).triggerHandler('blur');
          expect(angular.element(inputElement).val()).toEqual(',｡/\\');
          expect(scope.result.storeName).toEqual(',｡/\\');
          expect(modelCtrl.$valid).toBe(true);
          
          // test case 5
          angular.element(inputElement).triggerHandler('focus');
          changeInputValue(angular.element(inputElement),'12345');
          angular.element(inputElement).triggerHandler('blur');
          expect(angular.element(inputElement).val()).toEqual('12345');
          expect(scope.result.storeName).toEqual('12345');
          expect(modelCtrl.$valid).toBe(true);
          
          // test case 6
          angular.element(inputElement).triggerHandler('focus');
          changeInputValue(angular.element(inputElement),'Aaｱ345');
          angular.element(inputElement).triggerHandler('blur');
          expect(angular.element(inputElement).val()).toEqual('Aaｱ345');
          expect(scope.result.storeName).toEqual('Aaｱ345');
          expect(modelCtrl.$valid).toBe(true);
          
          // test case 7
          angular.element(inputElement).triggerHandler('focus');
          changeInputValue(angular.element(inputElement),'Aaあ345');
          angular.element(inputElement).triggerHandler('blur');
          expect(angular.element(inputElement).val()).toEqual('Aaあ345');
          expect(scope.result.storeName).toEqual('Aaあ345');
          expect(modelCtrl.$valid).toBe(false);
          
          // test case 8
          angular.element(inputElement).triggerHandler('focus');
          changeInputValue(angular.element(inputElement),'Aa１２345');
          angular.element(inputElement).triggerHandler('blur');
          expect(angular.element(inputElement).val()).toEqual('Aa１２345');
          expect(scope.result.storeName).toEqual('Aa１２345');
          expect(modelCtrl.$valid).toBe(false);
          
      });
      it ('it should check logic S3 ...', function() {
          compile('店舗名称','2','storeName','storeName','result.storeName',true ,null,'1','12','S3');
          var inputElement = angular.element(element).find('input')[0];
          var modelCtrl = angular.element(inputElement).controller('ngModel');
          
          // test case 0
          angular.element(inputElement).triggerHandler('focus');
          changeInputValue(angular.element(inputElement),'1');
          angular.element(inputElement).triggerHandler('blur');
          expect(angular.element(inputElement).val()).toEqual('1');
          expect(scope.result.storeName).toEqual('1');
          expect(modelCtrl.$valid).toBe(false);
          
          // test case 1
          angular.element(inputElement).triggerHandler('focus');
          changeInputValue(angular.element(inputElement),'１２３');
          angular.element(inputElement).triggerHandler('blur');
          expect(angular.element(inputElement).val()).toEqual('');
          expect(scope.result.storeName).toEqual(undefined);
          expect(modelCtrl.$valid).toBe(false);
          
          // test case 2
          angular.element(inputElement).triggerHandler('focus');
          changeInputValue(angular.element(inputElement),'qqwe');
          angular.element(inputElement).triggerHandler('blur');
          expect(angular.element(inputElement).val()).toEqual('');
          expect(scope.result.storeName).toEqual(undefined);
          expect(modelCtrl.$valid).toBe(false);
          
          // test case 3
          angular.element(inputElement).triggerHandler('focus');
          changeInputValue(angular.element(inputElement),'1234');
          angular.element(inputElement).triggerHandler('blur');
          expect(angular.element(inputElement).val()).toEqual('123-4');
          expect(scope.result.storeName).toEqual('1234');
          expect(modelCtrl.$valid).toBe(false);
          
          // test case 4
          angular.element(inputElement).triggerHandler('focus');
          changeInputValue(angular.element(inputElement),'1234567');
          angular.element(inputElement).triggerHandler('blur');
          expect(angular.element(inputElement).val()).toEqual('123-4567');
          expect(scope.result.storeName).toEqual('1234567');
          expect(modelCtrl.$valid).toBe(true);
          
          // test case 5
          angular.element(inputElement).triggerHandler('focus');
          changeInputValue(angular.element(inputElement),'1234-');
          angular.element(inputElement).triggerHandler('blur');
          expect(angular.element(inputElement).val()).toEqual('1234-');
          expect(scope.result.storeName).toEqual('1234-');
          expect(modelCtrl.$valid).toBe(false);
          
          // test case 6
          angular.element(inputElement).triggerHandler('focus');
          changeInputValue(angular.element(inputElement),'1234--');
          angular.element(inputElement).triggerHandler('blur');
          expect(angular.element(inputElement).val()).toEqual('1234--');
          expect(scope.result.storeName).toEqual('1234--');
          expect(modelCtrl.$valid).toBe(false);
          
          // test case 7
          angular.element(inputElement).triggerHandler('focus');
          changeInputValue(angular.element(inputElement),'1234-5');
          angular.element(inputElement).triggerHandler('blur');
          expect(angular.element(inputElement).val()).toEqual('1234-5');
          expect(scope.result.storeName).toEqual('1234-5');
          expect(modelCtrl.$valid).toBe(false);
          
          // test case 8
          angular.element(inputElement).triggerHandler('focus');
          changeInputValue(angular.element(inputElement),'123-4567');
          angular.element(inputElement).triggerHandler('blur');
          expect(angular.element(inputElement).val()).toEqual('123-4567');
          expect(scope.result.storeName).toEqual('123-4567');
          expect(modelCtrl.$valid).toBe(true);
          
          
          // test case 9
          angular.element(inputElement).triggerHandler('focus');
          changeInputValue(angular.element(inputElement),'-1234');
          angular.element(inputElement).triggerHandler('blur');
          expect(angular.element(inputElement).val()).toEqual('-1234');
          expect(scope.result.storeName).toEqual('-1234');
          expect(modelCtrl.$valid).toBe(false);
          
          // test case 10
          angular.element(inputElement).triggerHandler('focus');
          changeInputValue(angular.element(inputElement),'-1234qa');
          angular.element(inputElement).triggerHandler('blur');
          expect(angular.element(inputElement).val()).toEqual('-1234');
          expect(scope.result.storeName).toEqual('-1234');
          expect(modelCtrl.$valid).toBe(false);
          
          // test case 11
          angular.element(inputElement).triggerHandler('focus');
          changeInputValue(angular.element(inputElement),'-1234さあ');
          angular.element(inputElement).triggerHandler('blur');
          expect(angular.element(inputElement).val()).toEqual('-1234');
          expect(scope.result.storeName).toEqual('-1234');
          expect(modelCtrl.$valid).toBe(false);
          
          // test case 12
          angular.element(inputElement).triggerHandler('focus');
          changeInputValue(angular.element(inputElement),'1234さあ');
          angular.element(inputElement).triggerHandler('blur');
          expect(angular.element(inputElement).val()).toEqual('123-4');
          expect(scope.result.storeName).toEqual('1234');
          expect(modelCtrl.$valid).toBe(false);
          
          // test case 13
          angular.element(inputElement).triggerHandler('focus');
          changeInputValue(angular.element(inputElement),'0000000');
          angular.element(inputElement).triggerHandler('blur');
          expect(angular.element(inputElement).val()).toEqual('000-0000');
          expect(scope.result.storeName).toEqual('0000000');
          expect(modelCtrl.$valid).toBe(true);
      });
      
      it ('it should check logic S4 ...', function() {
          compile('店舗名称','2','storeName','storeName','result.storeName',true ,null,'1','12','S4');
          var inputElement = angular.element(element).find('input')[0];
          var modelCtrl = angular.element(inputElement).controller('ngModel');
          
          // test case 0
          angular.element(inputElement).triggerHandler('focus');
          changeInputValue(angular.element(inputElement),'1');
          angular.element(inputElement).triggerHandler('blur');
          expect(angular.element(inputElement).val()).toEqual('1');
          expect(scope.result.storeName).toEqual('1');
          expect(modelCtrl.$valid).toBe(true);
          
          // test case 1
          angular.element(inputElement).triggerHandler('focus');
          changeInputValue(angular.element(inputElement),'1123');
          angular.element(inputElement).triggerHandler('blur');
          expect(angular.element(inputElement).val()).toEqual('1123');
          expect(scope.result.storeName).toEqual('1123');
          expect(modelCtrl.$valid).toBe(true);
          
          // test case 2
          angular.element(inputElement).triggerHandler('focus');
          changeInputValue(angular.element(inputElement),'1123@');
          angular.element(inputElement).triggerHandler('blur');
          expect(angular.element(inputElement).val()).toEqual('1123');
          expect(scope.result.storeName).toEqual('1123');
          expect(modelCtrl.$valid).toBe(true);
          
          // test case 3
          angular.element(inputElement).triggerHandler('focus');
          changeInputValue(angular.element(inputElement),'1123あ');
          angular.element(inputElement).triggerHandler('blur');
          expect(angular.element(inputElement).val()).toEqual('1123');
          expect(scope.result.storeName).toEqual('1123');
          expect(modelCtrl.$valid).toBe(true);
          
          angular.element(inputElement).triggerHandler('focus');
          changeInputValue(angular.element(inputElement),'1123a');
          angular.element(inputElement).triggerHandler('blur');
          expect(angular.element(inputElement).val()).toEqual('1123');
          expect(scope.result.storeName).toEqual('1123');
          expect(modelCtrl.$valid).toBe(true);
          
          angular.element(inputElement).triggerHandler('focus');
          changeInputValue(angular.element(inputElement),'1123ｱ');
          angular.element(inputElement).triggerHandler('blur');
          expect(angular.element(inputElement).val()).toEqual('1123');
          expect(scope.result.storeName).toEqual('1123');
          expect(modelCtrl.$valid).toBe(true);
          
          // test case 4
          angular.element(inputElement).triggerHandler('focus');
          changeInputValue(angular.element(inputElement),'1123-123');
          angular.element(inputElement).triggerHandler('blur');
          expect(angular.element(inputElement).val()).toEqual('1123-123');
          expect(scope.result.storeName).toEqual('1123-123');
          expect(modelCtrl.$valid).toBe(true);
          
          // test case 5
          angular.element(inputElement).triggerHandler('focus');
          changeInputValue(angular.element(inputElement),'1123-123-123-123');
          angular.element(inputElement).triggerHandler('blur');
          expect(angular.element(inputElement).val()).toEqual('1123-123-123-123');
          expect(scope.result.storeName).toEqual(undefined);
          expect(modelCtrl.$valid).toBe(false);
          
          // test case 6
          angular.element(inputElement).triggerHandler('focus');
          changeInputValue(angular.element(inputElement),'1123-123-123');
          angular.element(inputElement).triggerHandler('blur');
          expect(angular.element(inputElement).val()).toEqual('1123-123-123');
          expect(scope.result.storeName).toEqual('1123-123-123');
          expect(modelCtrl.$valid).toBe(true);
          
          // test case 7
          angular.element(inputElement).triggerHandler('focus');
          changeInputValue(angular.element(inputElement),'1123123123');
          angular.element(inputElement).triggerHandler('blur');
          expect(angular.element(inputElement).val()).toEqual('1123123123');
          expect(scope.result.storeName).toEqual('1123123123');
          expect(modelCtrl.$valid).toBe(true);
          
          // test case 8
          angular.element(inputElement).triggerHandler('focus');
          changeInputValue(angular.element(inputElement),'1123123123-');
          angular.element(inputElement).triggerHandler('blur');
          expect(angular.element(inputElement).val()).toEqual('1123123123-');
          expect(scope.result.storeName).toEqual('1123123123-');
          expect(modelCtrl.$valid).toBe(false);
          
          // test case 9
          angular.element(inputElement).triggerHandler('focus');
          changeInputValue(angular.element(inputElement),'1123123123--');
          angular.element(inputElement).triggerHandler('blur');
          expect(angular.element(inputElement).val()).toEqual('1123123123--');
          expect(scope.result.storeName).toEqual('1123123123--');
          expect(modelCtrl.$valid).toBe(false);
          
          // test case 10
          angular.element(inputElement).triggerHandler('focus');
          changeInputValue(angular.element(inputElement),'11231--23123');
          angular.element(inputElement).triggerHandler('blur');
          expect(angular.element(inputElement).val()).toEqual('11231--23123');
          expect(scope.result.storeName).toEqual('11231--23123');
          expect(modelCtrl.$valid).toBe(false);
          
          // test case 11
          angular.element(inputElement).triggerHandler('focus');
          changeInputValue(angular.element(inputElement),'-123');
          angular.element(inputElement).triggerHandler('blur');
          expect(angular.element(inputElement).val()).toEqual('-123');
          expect(scope.result.storeName).toEqual('-123');
          expect(modelCtrl.$valid).toBe(false);
          
          // test case 12
          angular.element(inputElement).triggerHandler('focus');
          changeInputValue(angular.element(inputElement),'--123');
          angular.element(inputElement).triggerHandler('blur');
          expect(angular.element(inputElement).val()).toEqual('--123');
          expect(scope.result.storeName).toEqual('--123');
          expect(modelCtrl.$valid).toBe(false);
          
          // test case 13
          angular.element(inputElement).triggerHandler('focus');
          changeInputValue(angular.element(inputElement),'1-2-3-4-5-6');
          angular.element(inputElement).triggerHandler('blur');
          expect(angular.element(inputElement).val()).toEqual('1-2-3-4-5-6');
          expect(scope.result.storeName).toEqual('1-2-3-4-5-6');
          expect(modelCtrl.$valid).toBe(true);
          
          // test case 14
          angular.element(inputElement).triggerHandler('focus');
          changeInputValue(angular.element(inputElement),'一覧');
          angular.element(inputElement).triggerHandler('blur');
          expect(angular.element(inputElement).val()).toEqual('');
          expect(scope.result.storeName).toEqual(undefined);
          expect(modelCtrl.$valid).toBe(false);
          
          // test case 14
          angular.element(inputElement).triggerHandler('focus');
          changeInputValue(angular.element(inputElement),'--123--');
          angular.element(inputElement).triggerHandler('blur');
          expect(angular.element(inputElement).val()).toEqual('--123--');
          expect(scope.result.storeName).toEqual('--123--');
          expect(modelCtrl.$valid).toBe(false);
      });
      
  });
 
 
  
});
