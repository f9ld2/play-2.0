'use strict';

/* jasmine specs for directives go here */

describe ('Test directive fromto time input ', function() {
    var doc, control, scope, $compile, changeInputValue, 
    compile, templateHtml, inputElement, inputHtml, element;    
    beforeEach (module('directives'));
    beforeEach (inject(function ($injector, $sniffer) {
        $compile = $injector.get('$compile');
        scope = $injector.get('$rootScope');
        scope.result = {
                openSt: '',
                openEd: ''
            };
        changeInputValue = function (elm, value) {
          elm.val(value);
          browserTrigger(elm, $sniffer.hasEvent('input') ? 'input' : 'change');
        };
        
        compile = function(label, partition, 
                id, name, ngModel, required, readOnly, 
                id2, name2, ngModel2, required2, readOnly2) {
            var htmlText = '<form>';
            htmlText += '<cc-fromto-time-input';
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

            htmlText += ' id2="' + id2 + '"';
            htmlText += ' name2="' + name2 + '"';
            htmlText += ' ng-model2="' + ngModel2 + '"';
            if (required2 != null) {
                htmlText += ' cc-required2='+ required2;
            }
            if (readOnly2 != null) {
                htmlText += ' cc-readonly2='+ readOnly2;
            }
            htmlText += '/></form>';
            
            element = $compile(htmlText)(scope);
            templateHtml = element.html();
        };
  }));
  
  
  describe ('cc-fromto-time-input', function() {
      // test template
      it('it should render right html partition != null', function() {
          // Test partition = 2, required = true, readonly = null, required2 = true, readonly2 = null
          compile('休店期間', '2', 'openSt', 'openSt', 'result.openSt', 'true', null,
                  'openEd', 'openEd', 'result.openEd', 'true', null);
          expect(element.find('input').length).toEqual(2);
          expect(element.find('div').length).toEqual(3);
          expect(element.find('span').length).toEqual(1);
          
          inputElement = element.find('input')[0];
          inputHtml = angular.element('<div>').append(angular.element(inputElement).clone()).html();
          expect(templateHtml).toContain('<div class="cs-label-large-required cs-label">休店期間<span class="cs-txt-required"> *</span></div>');
          expect(inputHtml).toContain('type="text"');
          expect(inputHtml).toContain('cc-type="time"');
          expect(inputHtml).toContain('id="openSt"');
          expect(inputHtml).toContain('name="openSt"');
          expect(inputHtml).toContain('ng-model="ngModel"');
          expect(inputHtml).toContain('ng-minlength="1"');
          expect(inputHtml).toContain('cc-blur-validation');
          expect(inputHtml).toContain('maxlength="5"');
          expect(inputHtml).toContain('required');
          expect(inputHtml).not.toContain('readonly');
          
          inputElement = element.find('input')[1];
          inputHtml = angular.element('<div>').append(angular.element(inputElement).clone()).html();
          expect(templateHtml).toContain('<div class="cs-label-divide-zero cs-label">～</div>');
          expect(inputHtml).toContain('type="text"');
          expect(inputHtml).toContain('cc-type="time"');
          expect(inputHtml).toContain('id="openEd"');
          expect(inputHtml).toContain('name="openEd"');
          expect(inputHtml).toContain('ng-model="ngModel2"');
          expect(inputHtml).toContain('ng-minlength="1"');
          expect(inputHtml).toContain('cc-blur-validation');
          expect(inputHtml).toContain('maxlength="5"');
          expect(inputHtml).toContain('required');
          expect(inputHtml).not.toContain('readonly');
          
          compile('休店期間', '2', 'openSt', 'openSt', 'result.openSt', 'false', null,
                  'openEd', 'openEd', 'result.openEd', 'false', null);
          inputElement = element.find('input')[0];
          inputHtml = angular.element('<div>').append(angular.element(inputElement).clone()).html();
          expect(templateHtml).toContain('<div class="cs-label-large cs-label">休店期間</div>');
          expect(inputHtml).toContain('cc-blur-validation');
          expect(inputHtml).not.toContain('required');
          expect(inputHtml).not.toContain('readonly');
          
          inputElement = element.find('input')[1];
          inputHtml = angular.element('<div>').append(angular.element(inputElement).clone()).html();
          expect(templateHtml).toContain('<div class="cs-label-divide-zero cs-label">～</div>');
          expect(inputHtml).toContain('cc-blur-validation');
          expect(inputHtml).not.toContain('required');
          expect(inputHtml).not.toContain('readonly');
          
          // partition = 3
          compile('休店期間', '3', 'openSt', 'openSt', 'result.openSt', 'true', null,
                  'openEd', 'openEd', 'result.openEd', 'true', null);
          expect(templateHtml).toContain('<div class="cs-label-large-required cs-label">休店期間<span class="cs-txt-required"> *</span></div>');

          compile('休店期間', '3', 'openSt', 'openSt', 'result.openSt', null, null,
                  'openEd', 'openEd', 'result.openEd', null, null);
          inputElement = element.find('input')[0];
          inputHtml = angular.element('<div>').append(angular.element(inputElement).clone()).html();
          expect(templateHtml).toContain('<div class="cs-label-large cs-label">休店期間</div>');
          expect(inputHtml).not.toContain('required');
          expect(inputHtml).not.toContain('readonly');
          
          inputElement = element.find('input')[1];
          inputHtml = angular.element('<div>').append(angular.element(inputElement).clone()).html();
          expect(templateHtml).toContain('<div class="cs-label-divide-zero cs-label">～</div>');
          expect(inputHtml).not.toContain('required');
          expect(inputHtml).not.toContain('readonly');
          
          // readonly = true
          compile('休店期間', '3', 'openSt', 'openSt', 'result.openSt', null, true,
                  'openEd', 'openEd', 'result.openEd', null, true);
          inputElement = element.find('input')[0];
          inputHtml = angular.element('<div>').append(angular.element(inputElement).clone()).html();
          expect(templateHtml).toContain('<div class="cs-label-large cs-label">休店期間</div>');
          expect(inputHtml).not.toContain('cc-blur-validation');
          expect(inputHtml).not.toContain('required');
          expect(inputHtml).toContain('readonly');
          
          inputElement = element.find('input')[1];
          inputHtml = angular.element('<div>').append(angular.element(inputElement).clone()).html();
          expect(templateHtml).toContain('<div class="cs-label-divide-zero cs-label">～</div>');
          expect(inputHtml).not.toContain('cc-blur-validation');
          expect(inputHtml).not.toContain('required');
          expect(inputHtml).toContain('readonly');
          
          compile('休店期間', '3', 'openSt', 'openSt', 'result.openSt', null, false,
                  'openEd', 'openEd', 'result.openEd', null, false);
          inputElement = element.find('input')[0];
          inputHtml = angular.element('<div>').append(angular.element(inputElement).clone()).html();
          expect(templateHtml).toContain('<div class="cs-label-large cs-label">休店期間</div>');
          expect(inputHtml).not.toContain('cc-blur-validation');
          expect(inputHtml).not.toContain('required');
          expect(inputHtml).not.toContain('readonly');
          
          inputElement = element.find('input')[1];
          inputHtml = angular.element('<div>').append(angular.element(inputElement).clone()).html();
          expect(templateHtml).toContain('<div class="cs-label-divide-zero cs-label">～</div>');
          expect(inputHtml).not.toContain('cc-blur-validation');
          expect(inputHtml).not.toContain('required');
          expect(inputHtml).not.toContain('readonly');
          
          compile('休店期間', '3', 'openSt', 'openSt', 'result.openSt', true, null,
                  'openEd', 'openEd', 'result.openEd', true, null);
          inputElement = element.find('input')[0];
          inputHtml = angular.element('<div>').append(angular.element(inputElement).clone()).html();
          expect(inputHtml).not.toContain('cc-validate-greater-equal="{{ngModel}}"');
          inputElement = element.find('input')[1];
          inputHtml = angular.element('<div>').append(angular.element(inputElement).clone()).html();
          expect(inputHtml).toContain('cc-validate-greater-equal="{{ngModel}}"');
          
          compile('休店期間', '3', 'openSt', 'openSt', 'result.openSt', true, null,
                  'openEd', 'openEd', 'result.openEd', false, null);
          inputElement = element.find('input')[0];
          inputHtml = angular.element('<div>').append(angular.element(inputElement).clone()).html();
          expect(inputHtml).not.toContain('cc-validate-greater-equal="{{ngModel}}"');
          inputElement = element.find('input')[1];
          inputHtml = angular.element('<div>').append(angular.element(inputElement).clone()).html();
          expect(inputHtml).toContain('cc-validate-greater-equal="{{ngModel}}"');
          
          compile('休店期間', '3', 'openSt', 'openSt', 'result.openSt', false, null,
                  'openEd', 'openEd', 'result.openEd', true, null);
          inputElement = element.find('input')[0];
          inputHtml = angular.element('<div>').append(angular.element(inputElement).clone()).html();
          expect(inputHtml).not.toContain('cc-validate-greater-equal="{{ngModel}}"');
          inputElement = element.find('input')[1];
          inputHtml = angular.element('<div>').append(angular.element(inputElement).clone()).html();
          expect(inputHtml).toContain('cc-validate-greater-equal="{{ngModel}}"');
          
          compile('休店期間', '3', 'openSt', 'openSt', 'result.openSt', null, true,
                  'openEd', 'openEd', 'result.openEd', null, true);
          inputElement = element.find('input')[0];
          inputHtml = angular.element('<div>').append(angular.element(inputElement).clone()).html();
          expect(inputHtml).not.toContain('cc-validate-greater-equal="{{ngModel2}}"');
          inputElement = element.find('input')[1];
          inputHtml = angular.element('<div>').append(angular.element(inputElement).clone()).html();
          expect(inputHtml).not.toContain('cc-validate-greater-equal="{{ngModel}}"');

          compile('休店期間', '3', 'openSt', 'openSt', 'result.openSt', null, null,
                  'openEd', 'openEd', 'result.openEd', null, true);
          inputElement = element.find('input')[0];
          inputHtml = angular.element('<div>').append(angular.element(inputElement).clone()).html();
          expect(inputHtml).toContain('cc-validate-less-equal="{{ngModel2}}"');
          inputElement = element.find('input')[1];
          inputHtml = angular.element('<div>').append(angular.element(inputElement).clone()).html();
          expect(inputHtml).not.toContain('cc-validate-greater-equal="{{ngModel}}"');
      });
      it ('it should render right html partition == "" ', function() {
          // Test partition = "", required = true, readonly = null, required2 = true, readonly2 = null
          compile('休店期間', '', 'openSt', 'openSt', 'result.openSt', 'true', null,
                  'openEd', 'openEd', 'result.openEd', 'true', null);
          expect(element.find('input').length).toEqual(2);
          expect(element.find('div').length).toEqual(3);
          expect(element.find('span').length).toEqual(0);
          
          inputElement = element.find('input')[0];
          inputHtml = angular.element('<div>').append(angular.element(inputElement).clone()).html();
          expect(templateHtml).toContain('<div class="cs-label-divide-zero cs-label"> 項目タイトル </div>');
          expect(inputHtml).toContain('type="text"');
          expect(inputHtml).toContain('cc-type="time"');
          expect(inputHtml).toContain('id="openSt"');
          expect(inputHtml).toContain('name="openSt"');
          expect(inputHtml).toContain('ng-model="ngModel"');
          expect(inputHtml).toContain('ng-minlength="1"');
          expect(inputHtml).toContain('cc-blur-validation');
          expect(inputHtml).toContain('maxlength="5"');
          expect(inputHtml).toContain('required');
          expect(inputHtml).not.toContain('readonly');
          
          inputElement = element.find('input')[1];
          inputHtml = angular.element('<div>').append(angular.element(inputElement).clone()).html();
          expect(templateHtml).toContain('<div class="cs-label-divide-zero cs-label">～</div>');
          expect(inputHtml).toContain('type="text"');
          expect(inputHtml).toContain('cc-type="time"');
          expect(inputHtml).toContain('id="openEd"');
          expect(inputHtml).toContain('name="openEd"');
          expect(inputHtml).toContain('ng-model="ngModel2"');
          expect(inputHtml).toContain('ng-minlength="1"');
          expect(inputHtml).toContain('cc-blur-validation');
          expect(inputHtml).toContain('maxlength="5"');
          expect(inputHtml).toContain('required');
          expect(inputHtml).not.toContain('readonly');
          
          // Test partition = "", required = false, readonly = null, required2 = true, readonly2 = null
          compile('休店期間', '', 'openSt', 'openSt', 'result.openSt', 'false', null,
                  'openEd', 'openEd', 'result.openEd', 'true', null);
          expect(element.find('input').length).toEqual(2);
          expect(element.find('div').length).toEqual(3);
          expect(element.find('span').length).toEqual(0);
          
          inputElement = element.find('input')[0];
          inputHtml = angular.element('<div>').append(angular.element(inputElement).clone()).html();
          expect(templateHtml).toContain('<div class="cs-label-divide-zero cs-label"> 項目タイトル </div>');
          expect(inputHtml).toContain('type="text"');
          expect(inputHtml).toContain('cc-type="time"');
          expect(inputHtml).toContain('id="openSt"');
          expect(inputHtml).toContain('name="openSt"');
          expect(inputHtml).toContain('ng-model="ngModel"');
          expect(inputHtml).toContain('ng-minlength="1"');
          expect(inputHtml).toContain('cc-blur-validation');
          expect(inputHtml).toContain('maxlength="5"');
          expect(inputHtml).toContain('required');
          expect(inputHtml).not.toContain('readonly');
          
          inputElement = element.find('input')[1];
          inputHtml = angular.element('<div>').append(angular.element(inputElement).clone()).html();
          expect(templateHtml).toContain('<div class="cs-label-divide-zero cs-label">～</div>');
          expect(inputHtml).toContain('type="text"');
          expect(inputHtml).toContain('cc-type="time"');
          expect(inputHtml).toContain('id="openEd"');
          expect(inputHtml).toContain('name="openEd"');
          expect(inputHtml).toContain('ng-model="ngModel2"');
          expect(inputHtml).toContain('ng-minlength="1"');
          expect(inputHtml).toContain('cc-blur-validation');
          expect(inputHtml).toContain('maxlength="5"');
          expect(inputHtml).toContain('required');
          expect(inputHtml).not.toContain('readonly');
          
          // Test partition = "", required = true, readonly = null, required2 = false, readonly2 = null
          compile('休店期間', '', 'openSt', 'openSt', 'result.openSt', 'true', null,
                  'openEd', 'openEd', 'result.openEd', 'false', null);
          expect(element.find('input').length).toEqual(2);
          expect(element.find('div').length).toEqual(3);
          expect(element.find('span').length).toEqual(0);
          
          inputElement = element.find('input')[0];
          inputHtml = angular.element('<div>').append(angular.element(inputElement).clone()).html();
          expect(templateHtml).toContain('<div class="cs-label-divide-zero cs-label"> 項目タイトル </div>');
          expect(inputHtml).toContain('type="text"');
          expect(inputHtml).toContain('cc-type="time"');
          expect(inputHtml).toContain('id="openSt"');
          expect(inputHtml).toContain('name="openSt"');
          expect(inputHtml).toContain('ng-model="ngModel"');
          expect(inputHtml).toContain('ng-minlength="1"');
          expect(inputHtml).toContain('cc-blur-validation');
          expect(inputHtml).toContain('maxlength="5"');
          expect(inputHtml).toContain('required');
          expect(inputHtml).not.toContain('readonly');
          
          inputElement = element.find('input')[1];
          inputHtml = angular.element('<div>').append(angular.element(inputElement).clone()).html();
          expect(templateHtml).toContain('<div class="cs-label-divide-zero cs-label">～</div>');
          expect(inputHtml).toContain('type="text"');
          expect(inputHtml).toContain('cc-type="time"');
          expect(inputHtml).toContain('id="openEd"');
          expect(inputHtml).toContain('name="openEd"');
          expect(inputHtml).toContain('ng-model="ngModel2"');
          expect(inputHtml).toContain('ng-minlength="1"');
          expect(inputHtml).toContain('cc-blur-validation');
          expect(inputHtml).toContain('maxlength="5"');
          expect(inputHtml).toContain('required');
          expect(inputHtml).not.toContain('readonly');
          
          // Test partition = "", required = false, readonly = null, required2 = false, readonly2 = null
          compile('休店期間', '', 'openSt', 'openSt', 'result.openSt', 'false', null,
                  'openEd', 'openEd', 'result.openEd', 'false', null);
          expect(element.find('input').length).toEqual(2);
          expect(element.find('div').length).toEqual(3);
          expect(element.find('span').length).toEqual(0);
          
          inputElement = element.find('input')[0];
          inputHtml = angular.element('<div>').append(angular.element(inputElement).clone()).html();
          expect(templateHtml).toContain('<div class="cs-label-divide-zero cs-label"> 項目タイトル </div>');
          expect(inputHtml).toContain('type="text"');
          expect(inputHtml).toContain('cc-type="time"');
          expect(inputHtml).toContain('id="openSt"');
          expect(inputHtml).toContain('name="openSt"');
          expect(inputHtml).toContain('ng-model="ngModel"');
          expect(inputHtml).toContain('ng-minlength="1"');
          expect(inputHtml).toContain('cc-blur-validation');
          expect(inputHtml).toContain('maxlength="5"');
          expect(inputHtml).not.toContain('required');
          expect(inputHtml).not.toContain('readonly');
          
          inputElement = element.find('input')[1];
          inputHtml = angular.element('<div>').append(angular.element(inputElement).clone()).html();
          expect(templateHtml).toContain('<div class="cs-label-divide-zero cs-label">～</div>');
          expect(inputHtml).toContain('type="text"');
          expect(inputHtml).toContain('cc-type="time"');
          expect(inputHtml).toContain('id="openEd"');
          expect(inputHtml).toContain('name="openEd"');
          expect(inputHtml).toContain('ng-model="ngModel2"');
          expect(inputHtml).toContain('ng-minlength="1"');
          expect(inputHtml).toContain('cc-blur-validation');
          expect(inputHtml).toContain('maxlength="5"');
          expect(inputHtml).not.toContain('required');
          expect(inputHtml).not.toContain('readonly');
          
          // Test partition = "", required = false, readonly = null, required2 = false, readonly2 = true
          compile('休店期間', '', 'openSt', 'openSt', 'result.openSt', 'false', null,
                  'openEd', 'openEd', 'result.openEd', 'false', true);
          expect(element.find('input').length).toEqual(2);
          expect(element.find('div').length).toEqual(3);
          expect(element.find('span').length).toEqual(0);
          
          inputElement = element.find('input')[0];
          inputHtml = angular.element('<div>').append(angular.element(inputElement).clone()).html();
          expect(templateHtml).toContain('<div class="cs-label-divide-zero cs-label"> 項目タイトル </div>');
          expect(inputHtml).toContain('type="text"');
          expect(inputHtml).toContain('cc-type="time"');
          expect(inputHtml).toContain('id="openSt"');
          expect(inputHtml).toContain('name="openSt"');
          expect(inputHtml).toContain('ng-model="ngModel"');
          expect(inputHtml).toContain('ng-minlength="1"');
          expect(inputHtml).toContain('cc-blur-validation');
          expect(inputHtml).toContain('maxlength="5"');
          expect(inputHtml).not.toContain('required');
          expect(inputHtml).not.toContain('readonly');
          expect(inputHtml).toContain('cc-validate-less-equal="{{ngModel2}}"');
          
          inputElement = element.find('input')[1];
          inputHtml = angular.element('<div>').append(angular.element(inputElement).clone()).html();
          expect(templateHtml).toContain('<div class="cs-label-divide-zero cs-label">～</div>');
          expect(inputHtml).toContain('type="text"');
          expect(inputHtml).toContain('cc-type="time"');
          expect(inputHtml).toContain('id="openEd"');
          expect(inputHtml).toContain('name="openEd"');
          expect(inputHtml).toContain('ng-model="ngModel2"');
          expect(inputHtml).toContain('ng-minlength="1"');
          expect(inputHtml).toContain('cc-blur-validation');
          expect(inputHtml).toContain('maxlength="5"');
          expect(inputHtml).not.toContain('required');
          expect(inputHtml).not.toContain('readonly');
          expect(inputHtml).not.toContain('cc-validate-greater-equal="{{ngModel}}"');
          
          // Test partition = "", required = false, readonly = null, required2 = false, readonly2 = null
          compile('休店期間', '', 'openSt', 'openSt', 'result.openSt', 'false', null,
                  'openEd', 'openEd', 'result.openEd', 'false', null);
          expect(element.find('input').length).toEqual(2);
          expect(element.find('div').length).toEqual(3);
          expect(element.find('span').length).toEqual(0);
          
          inputElement = element.find('input')[0];
          inputHtml = angular.element('<div>').append(angular.element(inputElement).clone()).html();
          expect(templateHtml).toContain('<div class="cs-label-divide-zero cs-label"> 項目タイトル </div>');
          expect(inputHtml).toContain('type="text"');
          expect(inputHtml).toContain('cc-type="time"');
          expect(inputHtml).toContain('id="openSt"');
          expect(inputHtml).toContain('name="openSt"');
          expect(inputHtml).toContain('ng-model="ngModel"');
          expect(inputHtml).toContain('ng-minlength="1"');
          expect(inputHtml).toContain('cc-blur-validation');
          expect(inputHtml).toContain('maxlength="5"');
          expect(inputHtml).not.toContain('required');
          expect(inputHtml).not.toContain('readonly');
          expect(inputHtml).not.toContain('cc-validate-less-equal="{{ngModel2}}"');
          
          inputElement = element.find('input')[1];
          inputHtml = angular.element('<div>').append(angular.element(inputElement).clone()).html();
          expect(templateHtml).toContain('<div class="cs-label-divide-zero cs-label">～</div>');
          expect(inputHtml).toContain('type="text"');
          expect(inputHtml).toContain('cc-type="time"');
          expect(inputHtml).toContain('id="openEd"');
          expect(inputHtml).toContain('name="openEd"');
          expect(inputHtml).toContain('ng-model="ngModel2"');
          expect(inputHtml).toContain('ng-minlength="1"');
          expect(inputHtml).toContain('cc-blur-validation');
          expect(inputHtml).toContain('maxlength="5"');
          expect(inputHtml).not.toContain('required');
          expect(inputHtml).not.toContain('readonly');
          expect(inputHtml).toContain('cc-validate-greater-equal="{{ngModel}}"');
          
          // Test partition = "", required = false, readonly = true, required2 = false, readonly2 = null
          compile('休店期間', '', 'openSt', 'openSt', 'result.openSt', 'false', true,
                  'openEd', 'openEd', 'result.openEd', 'false', null);
          expect(element.find('input').length).toEqual(2);
          expect(element.find('div').length).toEqual(3);
          expect(element.find('span').length).toEqual(0);
          
          inputElement = element.find('input')[0];
          inputHtml = angular.element('<div>').append(angular.element(inputElement).clone()).html();
          expect(templateHtml).toContain('<div class="cs-label-divide-zero cs-label"> 項目タイトル </div>');
          expect(inputHtml).toContain('type="text"');
          expect(inputHtml).toContain('cc-type="time"');
          expect(inputHtml).toContain('id="openSt"');
          expect(inputHtml).toContain('name="openSt"');
          expect(inputHtml).toContain('ng-model="ngModel"');
          expect(inputHtml).toContain('ng-minlength="1"');
          expect(inputHtml).toContain('cc-blur-validation');
          expect(inputHtml).toContain('maxlength="5"');
          expect(inputHtml).not.toContain('required');
          expect(inputHtml).not.toContain('readonly');
          expect(inputHtml).not.toContain('cc-validate-less-equal="{{ngModel2}}"');
          
          inputElement = element.find('input')[1];
          inputHtml = angular.element('<div>').append(angular.element(inputElement).clone()).html();
          expect(templateHtml).toContain('<div class="cs-label-divide-zero cs-label">～</div>');
          expect(inputHtml).toContain('type="text"');
          expect(inputHtml).toContain('cc-type="time"');
          expect(inputHtml).toContain('id="openEd"');
          expect(inputHtml).toContain('name="openEd"');
          expect(inputHtml).toContain('ng-model="ngModel2"');
          expect(inputHtml).toContain('ng-minlength="1"');
          expect(inputHtml).toContain('cc-blur-validation');
          expect(inputHtml).toContain('maxlength="5"');
          expect(inputHtml).not.toContain('required');
          expect(inputHtml).not.toContain('readonly');
          expect(inputHtml).toContain('cc-validate-greater-equal="{{ngModel}}"');
      });

      it ('it should check logic ...', function() {
          
        var symbol = ":"
        //current date
        var d = new Date();
        var hour = d.getHours();
        var minute = d.getMinutes();
        var output = (hour < 10 ? '0' : '') + hour + symbol + (minute < 10 ? '0' : '') + minute;
          
        var expectNoSymbol = removeSymbol(output, symbol);

     // Test partition = "", required = false, readonly = true, required2 = false, readonly2 = null
        compile('休店期間', '3', 'openSt', 'openSt', 'result.openSt', 'true', null,
                'openEd', 'openEd', 'result.openEd', 'true', null);
        expect(element.find('input').length).toEqual(2);
        var inputElement = angular.element(element).find('input')[0];
        var modelCtrl = angular.element(inputElement).controller('ngModel');
        var inputElementSecond = angular.element(element).find('input')[1];
        var modelCtrlSecond = angular.element(inputElementSecond).controller('ngModel');

        // test case 0
        angular.element(inputElement).triggerHandler('focus');
        changeInputValue(angular.element(inputElement),'/');
        angular.element(inputElement).triggerHandler('blur');
        expect(angular.element(inputElement).val()).toEqual('');
        expect(scope.result.openSt).toEqual('');
        expect(modelCtrl.$valid).toBe(false);
        
        angular.element(inputElementSecond).triggerHandler('focus');
        changeInputValue(angular.element(inputElementSecond),'/');
        angular.element(inputElementSecond).triggerHandler('blur');
        expect(angular.element(inputElementSecond).val()).toEqual('');
        expect(scope.result.openEd).toEqual('');
        expect(modelCtrl.$valid).toBe(false);

        // test case 0-1
        angular.element(inputElement).triggerHandler('focus');
        expect(angular.element(inputElement).val()).toEqual('');
        expect(scope.result.openSt).toEqual('');

        // test case 0-2
        angular.element(inputElement).triggerHandler('blur');
        expect(angular.element(inputElement).val()).toEqual('');
        
        // test case 0-3
        angular.element(inputElementSecond).triggerHandler('focus');
        expect(angular.element(inputElementSecond).val()).toEqual('');
        expect(scope.result.openSt).toEqual('');

        // test case 0-4
        angular.element(inputElementSecond).triggerHandler('blur');
        expect(angular.element(inputElementSecond).val()).toEqual('');
        
        // test case 1-1
        angular.element(inputElement).triggerHandler('focus');
        changeInputValue(angular.element(inputElement),'2');
        angular.element(inputElement).triggerHandler('blur');
//        expect(angular.element(inputElement).val()).toEqual('13:02');
//        expect(scope.result.openSt).toEqual('1302');
        expect(modelCtrl.$valid).toBe(true);
        
        angular.element(inputElementSecond).triggerHandler('focus');
        changeInputValue(angular.element(inputElementSecond),'3');
        angular.element(inputElementSecond).triggerHandler('blur');
//        expect(angular.element(inputElementSecond).val()).toEqual('13:03');
//        expect(scope.result.openEd).toEqual('1303');
        expect(modelCtrl.$valid).toBe(true);
        
        // test case 1-2
        angular.element(inputElement).triggerHandler('focus');
        changeInputValue(angular.element(inputElement),'02');
        angular.element(inputElement).triggerHandler('blur');
//        expect(angular.element(inputElement).val()).toEqual('13:02');
//        expect(scope.result.openSt).toEqual('1302');
        expect(modelCtrl.$valid).toBe(true);
        
        angular.element(inputElementSecond).triggerHandler('focus');
        changeInputValue(angular.element(inputElementSecond),'03');
        angular.element(inputElementSecond).triggerHandler('blur');
//        expect(angular.element(inputElementSecond).val()).toEqual('13:03');
//        expect(scope.result.openEd).toEqual('1303');
        expect(modelCtrlSecond.$valid).toBe(true);
        
        // test case 2-1
        angular.element(inputElement).triggerHandler('focus');
        changeInputValue(angular.element(inputElement),'102');
        angular.element(inputElement).triggerHandler('blur');
        expect(angular.element(inputElement).val()).toEqual('01:02');
        expect(scope.result.openSt).toEqual('0102');
        expect(modelCtrl.$valid).toBe(true);
        
        angular.element(inputElementSecond).triggerHandler('focus');
        changeInputValue(angular.element(inputElementSecond),'103');
        angular.element(inputElementSecond).triggerHandler('blur');
        expect(angular.element(inputElementSecond).val()).toEqual('01:03');
        expect(scope.result.openEd).toEqual('0103');
        expect(modelCtrlSecond.$valid).toBe(true);
        
        // test case 2-2
        angular.element(inputElement).triggerHandler('focus');
        changeInputValue(angular.element(inputElement),'0102');
        angular.element(inputElement).triggerHandler('blur');
        expect(angular.element(inputElement).val()).toEqual('01:02');
        expect(scope.result.openSt).toEqual('0102');
        expect(modelCtrl.$valid).toBe(true);
        
        angular.element(inputElementSecond).triggerHandler('focus');
        changeInputValue(angular.element(inputElementSecond),'0103');
        angular.element(inputElementSecond).triggerHandler('blur');
        expect(angular.element(inputElementSecond).val()).toEqual('01:03');
        expect(scope.result.openEd).toEqual('0103');
        expect(modelCtrlSecond.$valid).toBe(true);
        
        // test case 2-3
        angular.element(inputElement).triggerHandler('focus');
        changeInputValue(angular.element(inputElement),'1:2');
        angular.element(inputElement).triggerHandler('blur');
        expect(angular.element(inputElement).val()).toEqual('01:02');
        expect(scope.result.openSt).toEqual('0102');
        expect(modelCtrl.$valid).toBe(true);
        
        angular.element(inputElementSecond).triggerHandler('focus');
        changeInputValue(angular.element(inputElementSecond),'1:3');
        angular.element(inputElementSecond).triggerHandler('blur');
        expect(angular.element(inputElementSecond).val()).toEqual('01:03');
        expect(scope.result.openEd).toEqual('0103');
        expect(modelCtrlSecond.$valid).toBe(true);
        
        // test case 2-4
        angular.element(inputElement).triggerHandler('focus');
        changeInputValue(angular.element(inputElement),'1:02');
        angular.element(inputElement).triggerHandler('blur');
        expect(angular.element(inputElement).val()).toEqual('01:02');
        expect(scope.result.openSt).toEqual('0102');
        expect(modelCtrl.$valid).toBe(true);
        
        angular.element(inputElementSecond).triggerHandler('focus');
        changeInputValue(angular.element(inputElementSecond),'1:03');
        angular.element(inputElementSecond).triggerHandler('blur');
        expect(angular.element(inputElementSecond).val()).toEqual('01:03');
        expect(scope.result.openEd).toEqual('0103');
        expect(modelCtrlSecond.$valid).toBe(true);
        
        // test case 2-5
        angular.element(inputElement).triggerHandler('focus');
        changeInputValue(angular.element(inputElement),'01:2');
        angular.element(inputElement).triggerHandler('blur');
        expect(angular.element(inputElement).val()).toEqual('01:02');
        expect(scope.result.openSt).toEqual('0102');
        expect(modelCtrl.$valid).toBe(true);
        
        angular.element(inputElementSecond).triggerHandler('focus');
        changeInputValue(angular.element(inputElementSecond),'01:3');
        angular.element(inputElementSecond).triggerHandler('blur');
        expect(angular.element(inputElementSecond).val()).toEqual('01:03');
        expect(scope.result.openEd).toEqual('0103');
        expect(modelCtrlSecond.$valid).toBe(true);
        
        // test case 2-6
        angular.element(inputElement).triggerHandler('focus');
        changeInputValue(angular.element(inputElement),'01:02');
        angular.element(inputElement).triggerHandler('blur');
        expect(angular.element(inputElement).val()).toEqual('01:02');
        expect(scope.result.openSt).toEqual('0102');
        expect(modelCtrl.$valid).toBe(true);
        
        angular.element(inputElementSecond).triggerHandler('focus');
        changeInputValue(angular.element(inputElementSecond),'01:03');
        angular.element(inputElementSecond).triggerHandler('blur');
        expect(angular.element(inputElementSecond).val()).toEqual('01:03');
        expect(scope.result.openEd).toEqual('0103');
        expect(modelCtrlSecond.$valid).toBe(true);
        
        // test case 3-1
        angular.element(inputElementSecond).triggerHandler('focus');
        changeInputValue(angular.element(inputElementSecond),'01103');
        angular.element(inputElementSecond).triggerHandler('blur');
        expect(angular.element(inputElementSecond).val()).toEqual('01103');
        expect(scope.result.openEd).toEqual('01103');
        expect(modelCtrlSecond.$valid).toBe(false);
    });
  });
 
 
  
});
