'use strict';

/* jasmine specs for directives go here */

describe ('Test directive fromto date input ', function() {
    var doc, control, scope, $compile, changeInputValue, 
    compile, templateHtml, inputElement, inputHtml, element;    
    beforeEach (module('directives'));
    beforeEach (inject(function ($injector, $sniffer) {
        $compile = $injector.get('$compile');
        scope = $injector.get('$rootScope');
        scope.result = {
            closeSt: '',
            closeEd: ''
        };
        changeInputValue = function (elm, value) {
          elm.val(value);
          browserTrigger(elm, $sniffer.hasEvent('input') ? 'input' : 'change');
        };
        
        compile = function(label, partition, 
                id, name, ngModel, required, readOnly, 
                id2, name2, ngModel2, required2, readOnly2) {
            var htmlText = '<form>';
            htmlText += '<cc-fromto-date-input';
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
  
  
  describe ('cc-fromto-date-input', function() {
      // test template
      it('it should render right html partition != null', function() {
          // Test partition = 2, required = true, readonly = null, required2 = true, readonly2 = null
          compile('休店期間', '2', 'closeSt', 'closeSt', 'result.closeSt', 'true', null,
                  'closeEd', 'closeEd', 'result.closeEd', 'true', null);
          expect(element.find('input').length).toEqual(2);
          expect(element.find('div').length).toEqual(3);
          expect(element.find('span').length).toEqual(1);
          
          inputElement = element.find('input')[0];
          inputHtml = angular.element('<div>').append(angular.element(inputElement).clone()).html();
          expect(templateHtml).toContain('<div class="cs-label-large-required cs-label">休店期間<span class="cs-txt-required"> *</span></div>');
          expect(inputHtml).toContain('type="text"');
          expect(inputHtml).toContain('cc-type="date"');
          expect(inputHtml).toContain('id="closeSt"');
          expect(inputHtml).toContain('name="closeSt"');
          expect(inputHtml).toContain('ng-model="ngModel"');
          expect(inputHtml).toContain('ng-minlength="1"');
          expect(inputHtml).toContain('cc-blur-validation');
          expect(inputHtml).toContain('maxlength="10"');
          expect(inputHtml).toContain('required');
          expect(inputHtml).not.toContain('readonly');
          
          inputElement = element.find('input')[1];
          inputHtml = angular.element('<div>').append(angular.element(inputElement).clone()).html();
          expect(templateHtml).toContain('<div class="cs-label-divide-zero cs-label">～</div>');
          expect(inputHtml).toContain('type="text"');
          expect(inputHtml).toContain('cc-type="date"');
          expect(inputHtml).toContain('id="closeEd"');
          expect(inputHtml).toContain('name="closeEd"');
          expect(inputHtml).toContain('ng-model="ngModel2"');
          expect(inputHtml).toContain('ng-minlength="1"');
          expect(inputHtml).toContain('cc-blur-validation');
          expect(inputHtml).toContain('maxlength="10"');
          expect(inputHtml).toContain('required');
          expect(inputHtml).not.toContain('readonly');
          
          compile('休店期間', '2', 'closeSt', 'closeSt', 'result.closeSt', 'false', null,
                  'closeEd', 'closeEd', 'result.closeEd', 'false', null);
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
          compile('休店期間', '3', 'closeSt', 'closeSt', 'result.closeSt', 'true', null,
                  'closeEd', 'closeEd', 'result.closeEd', 'true', null);
          expect(templateHtml).toContain('<div class="cs-label-large-required cs-label">休店期間<span class="cs-txt-required"> *</span></div>');

          compile('休店期間', '3', 'closeSt', 'closeSt', 'result.closeSt', null, null,
                  'closeEd', 'closeEd', 'result.closeEd', null, null);
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
          compile('休店期間', '3', 'closeSt', 'closeSt', 'result.closeSt', null, true,
                  'closeEd', 'closeEd', 'result.closeEd', null, true);
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
          
          compile('休店期間', '3', 'closeSt', 'closeSt', 'result.closeSt', null, false,
                  'closeEd', 'closeEd', 'result.closeEd', null, false);
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
          
          compile('休店期間', '3', 'closeSt', 'closeSt', 'result.closeSt', true, null,
                  'closeEd', 'closeEd', 'result.closeEd', true, null);
          inputElement = element.find('input')[0];
          inputHtml = angular.element('<div>').append(angular.element(inputElement).clone()).html();
          expect(inputHtml).not.toContain('cc-validate-greater-equal="{{ngModel}}"');
          inputElement = element.find('input')[1];
          inputHtml = angular.element('<div>').append(angular.element(inputElement).clone()).html();
          expect(inputHtml).toContain('cc-validate-greater-equal="{{ngModel}}"');
          
          compile('休店期間', '3', 'closeSt', 'closeSt', 'result.closeSt', true, null,
                  'closeEd', 'closeEd', 'result.closeEd', false, null);
          inputElement = element.find('input')[0];
          inputHtml = angular.element('<div>').append(angular.element(inputElement).clone()).html();
          expect(inputHtml).not.toContain('cc-validate-greater-equal="{{ngModel}}"');
          inputElement = element.find('input')[1];
          inputHtml = angular.element('<div>').append(angular.element(inputElement).clone()).html();
          expect(inputHtml).toContain('cc-validate-greater-equal="{{ngModel}}"');
          
          compile('休店期間', '3', 'closeSt', 'closeSt', 'result.closeSt', false, null,
                  'closeEd', 'closeEd', 'result.closeEd', true, null);
          inputElement = element.find('input')[0];
          inputHtml = angular.element('<div>').append(angular.element(inputElement).clone()).html();
          expect(inputHtml).not.toContain('cc-validate-greater-equal="{{ngModel}}"');
          inputElement = element.find('input')[1];
          inputHtml = angular.element('<div>').append(angular.element(inputElement).clone()).html();
          expect(inputHtml).toContain('cc-validate-greater-equal="{{ngModel}}"');
          
          compile('休店期間', '3', 'closeSt', 'closeSt', 'result.closeSt', null, true,
                  'closeEd', 'closeEd', 'result.closeEd', null, true);
          inputElement = element.find('input')[0];
          inputHtml = angular.element('<div>').append(angular.element(inputElement).clone()).html();
          expect(inputHtml).not.toContain('cc-validate-greater-equal="{{ngModel2}}"');
          inputElement = element.find('input')[1];
          inputHtml = angular.element('<div>').append(angular.element(inputElement).clone()).html();
          expect(inputHtml).not.toContain('cc-validate-greater-equal="{{ngModel}}"');

          compile('休店期間', '3', 'closeSt', 'closeSt', 'result.closeSt', null, null,
                  'closeEd', 'closeEd', 'result.closeEd', null, true);
          inputElement = element.find('input')[0];
          inputHtml = angular.element('<div>').append(angular.element(inputElement).clone()).html();
          expect(inputHtml).toContain('cc-validate-less-equal="{{ngModel2}}"');
          inputElement = element.find('input')[1];
          inputHtml = angular.element('<div>').append(angular.element(inputElement).clone()).html();
          expect(inputHtml).not.toContain('cc-validate-greater-equal="{{ngModel}}"');
      });
      it ('it should render right html partition == "" ', function() {
          // Test partition = "", required = true, readonly = null, required2 = true, readonly2 = null
          compile('休店期間', '', 'closeSt', 'closeSt', 'result.closeSt', 'true', null,
                  'closeEd', 'closeEd', 'result.closeEd', 'true', null);
          expect(element.find('input').length).toEqual(2);
          expect(element.find('div').length).toEqual(3);
          expect(element.find('span').length).toEqual(0);
          
          inputElement = element.find('input')[0];
          inputHtml = angular.element('<div>').append(angular.element(inputElement).clone()).html();
          expect(templateHtml).toContain('<div class="cs-label-divide-zero cs-label"> 項目タイトル </div>');
          expect(inputHtml).toContain('type="text"');
          expect(inputHtml).toContain('cc-type="date"');
          expect(inputHtml).toContain('id="closeSt"');
          expect(inputHtml).toContain('name="closeSt"');
          expect(inputHtml).toContain('ng-model="ngModel"');
          expect(inputHtml).toContain('ng-minlength="1"');
          expect(inputHtml).toContain('cc-blur-validation');
          expect(inputHtml).toContain('maxlength="10"');
          expect(inputHtml).toContain('required');
          expect(inputHtml).not.toContain('readonly');
          
          inputElement = element.find('input')[1];
          inputHtml = angular.element('<div>').append(angular.element(inputElement).clone()).html();
          expect(templateHtml).toContain('<div class="cs-label-divide-zero cs-label">～</div>');
          expect(inputHtml).toContain('type="text"');
          expect(inputHtml).toContain('cc-type="date"');
          expect(inputHtml).toContain('id="closeEd"');
          expect(inputHtml).toContain('name="closeEd"');
          expect(inputHtml).toContain('ng-model="ngModel2"');
          expect(inputHtml).toContain('ng-minlength="1"');
          expect(inputHtml).toContain('cc-blur-validation');
          expect(inputHtml).toContain('maxlength="10"');
          expect(inputHtml).toContain('required');
          expect(inputHtml).not.toContain('readonly');
          
          // Test partition = "", required = false, readonly = null, required2 = true, readonly2 = null
          compile('休店期間', '', 'closeSt', 'closeSt', 'result.closeSt', 'false', null,
                  'closeEd', 'closeEd', 'result.closeEd', 'true', null);
          expect(element.find('input').length).toEqual(2);
          expect(element.find('div').length).toEqual(3);
          expect(element.find('span').length).toEqual(0);
          
          inputElement = element.find('input')[0];
          inputHtml = angular.element('<div>').append(angular.element(inputElement).clone()).html();
          expect(templateHtml).toContain('<div class="cs-label-divide-zero cs-label"> 項目タイトル </div>');
          expect(inputHtml).toContain('type="text"');
          expect(inputHtml).toContain('cc-type="date"');
          expect(inputHtml).toContain('id="closeSt"');
          expect(inputHtml).toContain('name="closeSt"');
          expect(inputHtml).toContain('ng-model="ngModel"');
          expect(inputHtml).toContain('ng-minlength="1"');
          expect(inputHtml).toContain('cc-blur-validation');
          expect(inputHtml).toContain('maxlength="10"');
          expect(inputHtml).toContain('required');
          expect(inputHtml).not.toContain('readonly');
          
          inputElement = element.find('input')[1];
          inputHtml = angular.element('<div>').append(angular.element(inputElement).clone()).html();
          expect(templateHtml).toContain('<div class="cs-label-divide-zero cs-label">～</div>');
          expect(inputHtml).toContain('type="text"');
          expect(inputHtml).toContain('cc-type="date"');
          expect(inputHtml).toContain('id="closeEd"');
          expect(inputHtml).toContain('name="closeEd"');
          expect(inputHtml).toContain('ng-model="ngModel2"');
          expect(inputHtml).toContain('ng-minlength="1"');
          expect(inputHtml).toContain('cc-blur-validation');
          expect(inputHtml).toContain('maxlength="10"');
          expect(inputHtml).toContain('required');
          expect(inputHtml).not.toContain('readonly');
          
          // Test partition = "", required = true, readonly = null, required2 = false, readonly2 = null
          compile('休店期間', '', 'closeSt', 'closeSt', 'result.closeSt', 'true', null,
                  'closeEd', 'closeEd', 'result.closeEd', 'false', null);
          expect(element.find('input').length).toEqual(2);
          expect(element.find('div').length).toEqual(3);
          expect(element.find('span').length).toEqual(0);
          
          inputElement = element.find('input')[0];
          inputHtml = angular.element('<div>').append(angular.element(inputElement).clone()).html();
          expect(templateHtml).toContain('<div class="cs-label-divide-zero cs-label"> 項目タイトル </div>');
          expect(inputHtml).toContain('type="text"');
          expect(inputHtml).toContain('cc-type="date"');
          expect(inputHtml).toContain('id="closeSt"');
          expect(inputHtml).toContain('name="closeSt"');
          expect(inputHtml).toContain('ng-model="ngModel"');
          expect(inputHtml).toContain('ng-minlength="1"');
          expect(inputHtml).toContain('cc-blur-validation');
          expect(inputHtml).toContain('maxlength="10"');
          expect(inputHtml).toContain('required');
          expect(inputHtml).not.toContain('readonly');
          
          inputElement = element.find('input')[1];
          inputHtml = angular.element('<div>').append(angular.element(inputElement).clone()).html();
          expect(templateHtml).toContain('<div class="cs-label-divide-zero cs-label">～</div>');
          expect(inputHtml).toContain('type="text"');
          expect(inputHtml).toContain('cc-type="date"');
          expect(inputHtml).toContain('id="closeEd"');
          expect(inputHtml).toContain('name="closeEd"');
          expect(inputHtml).toContain('ng-model="ngModel2"');
          expect(inputHtml).toContain('ng-minlength="1"');
          expect(inputHtml).toContain('cc-blur-validation');
          expect(inputHtml).toContain('maxlength="10"');
          expect(inputHtml).toContain('required');
          expect(inputHtml).not.toContain('readonly');
          
          // Test partition = "", required = false, readonly = null, required2 = false, readonly2 = null
          compile('休店期間', '', 'closeSt', 'closeSt', 'result.closeSt', 'false', null,
                  'closeEd', 'closeEd', 'result.closeEd', 'false', null);
          expect(element.find('input').length).toEqual(2);
          expect(element.find('div').length).toEqual(3);
          expect(element.find('span').length).toEqual(0);
          
          inputElement = element.find('input')[0];
          inputHtml = angular.element('<div>').append(angular.element(inputElement).clone()).html();
          expect(templateHtml).toContain('<div class="cs-label-divide-zero cs-label"> 項目タイトル </div>');
          expect(inputHtml).toContain('type="text"');
          expect(inputHtml).toContain('cc-type="date"');
          expect(inputHtml).toContain('id="closeSt"');
          expect(inputHtml).toContain('name="closeSt"');
          expect(inputHtml).toContain('ng-model="ngModel"');
          expect(inputHtml).toContain('ng-minlength="1"');
          expect(inputHtml).toContain('cc-blur-validation');
          expect(inputHtml).toContain('maxlength="10"');
          expect(inputHtml).not.toContain('required');
          expect(inputHtml).not.toContain('readonly');
          
          inputElement = element.find('input')[1];
          inputHtml = angular.element('<div>').append(angular.element(inputElement).clone()).html();
          expect(templateHtml).toContain('<div class="cs-label-divide-zero cs-label">～</div>');
          expect(inputHtml).toContain('type="text"');
          expect(inputHtml).toContain('cc-type="date"');
          expect(inputHtml).toContain('id="closeEd"');
          expect(inputHtml).toContain('name="closeEd"');
          expect(inputHtml).toContain('ng-model="ngModel2"');
          expect(inputHtml).toContain('ng-minlength="1"');
          expect(inputHtml).toContain('cc-blur-validation');
          expect(inputHtml).toContain('maxlength="10"');
          expect(inputHtml).not.toContain('required');
          expect(inputHtml).not.toContain('readonly');
          
          // Test partition = "", required = false, readonly = null, required2 = false, readonly2 = true
          compile('休店期間', '', 'closeSt', 'closeSt', 'result.closeSt', 'false', null,
                  'closeEd', 'closeEd', 'result.closeEd', 'false', true);
          expect(element.find('input').length).toEqual(2);
          expect(element.find('div').length).toEqual(3);
          expect(element.find('span').length).toEqual(0);
          
          inputElement = element.find('input')[0];
          inputHtml = angular.element('<div>').append(angular.element(inputElement).clone()).html();
          expect(templateHtml).toContain('<div class="cs-label-divide-zero cs-label"> 項目タイトル </div>');
          expect(inputHtml).toContain('type="text"');
          expect(inputHtml).toContain('cc-type="date"');
          expect(inputHtml).toContain('id="closeSt"');
          expect(inputHtml).toContain('name="closeSt"');
          expect(inputHtml).toContain('ng-model="ngModel"');
          expect(inputHtml).toContain('ng-minlength="1"');
          expect(inputHtml).toContain('cc-blur-validation');
          expect(inputHtml).toContain('maxlength="10"');
          expect(inputHtml).not.toContain('required');
          expect(inputHtml).not.toContain('readonly');
          expect(inputHtml).toContain('cc-validate-less-equal="{{ngModel2}}"');
          
          inputElement = element.find('input')[1];
          inputHtml = angular.element('<div>').append(angular.element(inputElement).clone()).html();
          expect(templateHtml).toContain('<div class="cs-label-divide-zero cs-label">～</div>');
          expect(inputHtml).toContain('type="text"');
          expect(inputHtml).toContain('cc-type="date"');
          expect(inputHtml).toContain('id="closeEd"');
          expect(inputHtml).toContain('name="closeEd"');
          expect(inputHtml).toContain('ng-model="ngModel2"');
          expect(inputHtml).toContain('ng-minlength="1"');
          expect(inputHtml).toContain('cc-blur-validation');
          expect(inputHtml).toContain('maxlength="10"');
          expect(inputHtml).not.toContain('required');
          expect(inputHtml).not.toContain('readonly');
          expect(inputHtml).not.toContain('cc-validate-greater-equal="{{ngModel}}"');
          
          // Test partition = "", required = false, readonly = null, required2 = false, readonly2 = null
          compile('休店期間', '', 'closeSt', 'closeSt', 'result.closeSt', 'false', null,
                  'closeEd', 'closeEd', 'result.closeEd', 'false', null);
          expect(element.find('input').length).toEqual(2);
          expect(element.find('div').length).toEqual(3);
          expect(element.find('span').length).toEqual(0);
          
          inputElement = element.find('input')[0];
          inputHtml = angular.element('<div>').append(angular.element(inputElement).clone()).html();
          expect(templateHtml).toContain('<div class="cs-label-divide-zero cs-label"> 項目タイトル </div>');
          expect(inputHtml).toContain('type="text"');
          expect(inputHtml).toContain('cc-type="date"');
          expect(inputHtml).toContain('id="closeSt"');
          expect(inputHtml).toContain('name="closeSt"');
          expect(inputHtml).toContain('ng-model="ngModel"');
          expect(inputHtml).toContain('ng-minlength="1"');
          expect(inputHtml).toContain('cc-blur-validation');
          expect(inputHtml).toContain('maxlength="10"');
          expect(inputHtml).not.toContain('required');
          expect(inputHtml).not.toContain('readonly');
          expect(inputHtml).not.toContain('cc-validate-less-equal="{{ngModel2}}"');
          
          inputElement = element.find('input')[1];
          inputHtml = angular.element('<div>').append(angular.element(inputElement).clone()).html();
          expect(templateHtml).toContain('<div class="cs-label-divide-zero cs-label">～</div>');
          expect(inputHtml).toContain('type="text"');
          expect(inputHtml).toContain('cc-type="date"');
          expect(inputHtml).toContain('id="closeEd"');
          expect(inputHtml).toContain('name="closeEd"');
          expect(inputHtml).toContain('ng-model="ngModel2"');
          expect(inputHtml).toContain('ng-minlength="1"');
          expect(inputHtml).toContain('cc-blur-validation');
          expect(inputHtml).toContain('maxlength="10"');
          expect(inputHtml).not.toContain('required');
          expect(inputHtml).not.toContain('readonly');
          expect(inputHtml).toContain('cc-validate-greater-equal="{{ngModel}}"');
          
          // Test partition = "", required = false, readonly = true, required2 = false, readonly2 = null
          compile('休店期間', '', 'closeSt', 'closeSt', 'result.closeSt', 'false', true,
                  'closeEd', 'closeEd', 'result.closeEd', 'false', null);
          expect(element.find('input').length).toEqual(2);
          expect(element.find('div').length).toEqual(3);
          expect(element.find('span').length).toEqual(0);
          
          inputElement = element.find('input')[0];
          inputHtml = angular.element('<div>').append(angular.element(inputElement).clone()).html();
          expect(templateHtml).toContain('<div class="cs-label-divide-zero cs-label"> 項目タイトル </div>');
          expect(inputHtml).toContain('type="text"');
          expect(inputHtml).toContain('cc-type="date"');
          expect(inputHtml).toContain('id="closeSt"');
          expect(inputHtml).toContain('name="closeSt"');
          expect(inputHtml).toContain('ng-model="ngModel"');
          expect(inputHtml).toContain('ng-minlength="1"');
          expect(inputHtml).toContain('cc-blur-validation');
          expect(inputHtml).toContain('maxlength="10"');
          expect(inputHtml).not.toContain('required');
          expect(inputHtml).not.toContain('readonly');
          expect(inputHtml).not.toContain('cc-validate-less-equal="{{ngModel2}}"');
          
          inputElement = element.find('input')[1];
          inputHtml = angular.element('<div>').append(angular.element(inputElement).clone()).html();
          expect(templateHtml).toContain('<div class="cs-label-divide-zero cs-label">～</div>');
          expect(inputHtml).toContain('type="text"');
          expect(inputHtml).toContain('cc-type="date"');
          expect(inputHtml).toContain('id="closeEd"');
          expect(inputHtml).toContain('name="closeEd"');
          expect(inputHtml).toContain('ng-model="ngModel2"');
          expect(inputHtml).toContain('ng-minlength="1"');
          expect(inputHtml).toContain('cc-blur-validation');
          expect(inputHtml).toContain('maxlength="10"');
          expect(inputHtml).not.toContain('required');
          expect(inputHtml).not.toContain('readonly');
          expect(inputHtml).toContain('cc-validate-greater-equal="{{ngModel}}"');
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

     // Test partition = "", required = false, readonly = true, required2 = false, readonly2 = null
        compile('休店期間', '3', 'closeSt', 'closeSt', 'result.closeSt', 'true', null,
                'closeEd', 'closeEd', 'result.closeEd', 'true', null);
        expect(element.find('input').length).toEqual(2);
        var inputElement = angular.element(element).find('input')[0];
        var modelCtrl = angular.element(inputElement).controller('ngModel');
        var inputElementSecond = angular.element(element).find('input')[1];
        var modelCtrlSecond = angular.element(inputElement).controller('ngModel');
        // test case 0
        angular.element(inputElement).triggerHandler('focus');
        changeInputValue(angular.element(inputElement),'/');
        angular.element(inputElement).triggerHandler('blur');
        expect(angular.element(inputElement).val()).toEqual(output);
        expect(scope.result.closeSt).toEqual(expectNoSymbol);
        expect(modelCtrl.$valid).toBe(true);
        
        angular.element(inputElementSecond).triggerHandler('focus');
        changeInputValue(angular.element(inputElementSecond),'/');
        angular.element(inputElementSecond).triggerHandler('blur');
        expect(angular.element(inputElementSecond).val()).toEqual(output);
        expect(scope.result.closeEd).toEqual(expectNoSymbol);
//        expect(modelCtrl.$valid).toBe(true);

        // test case 0-1
        angular.element(inputElement).triggerHandler('focus');
        expect(angular.element(inputElement).val()).toEqual(expectNoSymbol);
        expect(scope.result.closeSt).toEqual(expectNoSymbol);

        // test case 0-2
        angular.element(inputElement).triggerHandler('blur');
        expect(angular.element(inputElement).val()).toEqual(output);
        
        // test case 0-3
        angular.element(inputElementSecond).triggerHandler('focus');
        expect(angular.element(inputElementSecond).val()).toEqual(expectNoSymbol);
        expect(scope.result.closeSt).toEqual(expectNoSymbol);

        // test case 0-4
        angular.element(inputElementSecond).triggerHandler('blur');
        expect(angular.element(inputElementSecond).val()).toEqual(output);
        
        // test case 1-1
        angular.element(inputElement).triggerHandler('focus');
        changeInputValue(angular.element(inputElement),'2');
        angular.element(inputElement).triggerHandler('blur');
        expect(angular.element(inputElement).val()).toEqual('2014/02/02');
        expect(scope.result.closeSt).toEqual('20140202');
        expect(modelCtrl.$valid).toBe(true);
        
        angular.element(inputElementSecond).triggerHandler('focus');
        changeInputValue(angular.element(inputElementSecond),'3');
        angular.element(inputElementSecond).triggerHandler('blur');
        expect(angular.element(inputElementSecond).val()).toEqual('2014/02/03');
        expect(scope.result.closeEd).toEqual('20140203');
        expect(modelCtrl.$valid).toBe(true);
        
        // test case 1-2
        angular.element(inputElement).triggerHandler('focus');
        changeInputValue(angular.element(inputElement),'02');
        angular.element(inputElement).triggerHandler('blur');
        expect(angular.element(inputElement).val()).toEqual('2014/02/02');
        expect(scope.result.closeSt).toEqual('20140202');
        expect(modelCtrl.$valid).toBe(true);
        
        angular.element(inputElementSecond).triggerHandler('focus');
        changeInputValue(angular.element(inputElementSecond),'03');
        angular.element(inputElementSecond).triggerHandler('blur');
        expect(angular.element(inputElementSecond).val()).toEqual('2014/02/03');
        expect(scope.result.closeEd).toEqual('20140203');
        expect(modelCtrlSecond.$valid).toBe(true);
        
        // test case 2-1
        angular.element(inputElement).triggerHandler('focus');
        changeInputValue(angular.element(inputElement),'102');
        angular.element(inputElement).triggerHandler('blur');
        expect(angular.element(inputElement).val()).toEqual('2014/01/02');
        expect(scope.result.closeSt).toEqual('20140102');
        expect(modelCtrl.$valid).toBe(true);
        
        angular.element(inputElementSecond).triggerHandler('focus');
        changeInputValue(angular.element(inputElementSecond),'103');
        angular.element(inputElementSecond).triggerHandler('blur');
        expect(angular.element(inputElementSecond).val()).toEqual('2014/01/03');
        expect(scope.result.closeEd).toEqual('20140103');
        expect(modelCtrlSecond.$valid).toBe(true);
        
        // test case 2-2
        angular.element(inputElement).triggerHandler('focus');
        changeInputValue(angular.element(inputElement),'0102');
        angular.element(inputElement).triggerHandler('blur');
        expect(angular.element(inputElement).val()).toEqual('2014/01/02');
        expect(scope.result.closeSt).toEqual('20140102');
        expect(modelCtrl.$valid).toBe(true);
        
        angular.element(inputElementSecond).triggerHandler('focus');
        changeInputValue(angular.element(inputElementSecond),'0103');
        angular.element(inputElementSecond).triggerHandler('blur');
        expect(angular.element(inputElementSecond).val()).toEqual('2014/01/03');
        expect(scope.result.closeEd).toEqual('20140103');
        expect(modelCtrlSecond.$valid).toBe(true);
        
        // test case 2-3
        angular.element(inputElement).triggerHandler('focus');
        changeInputValue(angular.element(inputElement),'1/2');
        angular.element(inputElement).triggerHandler('blur');
        expect(angular.element(inputElement).val()).toEqual('2014/01/02');
        expect(scope.result.closeSt).toEqual('20140102');
        expect(modelCtrl.$valid).toBe(true);
        
        angular.element(inputElementSecond).triggerHandler('focus');
        changeInputValue(angular.element(inputElementSecond),'1/3');
        angular.element(inputElementSecond).triggerHandler('blur');
        expect(angular.element(inputElementSecond).val()).toEqual('2014/01/03');
        expect(scope.result.closeEd).toEqual('20140103');
        expect(modelCtrlSecond.$valid).toBe(true);
        
        // test case 2-4
        angular.element(inputElement).triggerHandler('focus');
        changeInputValue(angular.element(inputElement),'1/02');
        angular.element(inputElement).triggerHandler('blur');
        expect(angular.element(inputElement).val()).toEqual('2014/01/02');
        expect(scope.result.closeSt).toEqual('20140102');
        expect(modelCtrl.$valid).toBe(true);
        
        angular.element(inputElementSecond).triggerHandler('focus');
        changeInputValue(angular.element(inputElementSecond),'1/03');
        angular.element(inputElementSecond).triggerHandler('blur');
        expect(angular.element(inputElementSecond).val()).toEqual('2014/01/03');
        expect(scope.result.closeEd).toEqual('20140103');
        expect(modelCtrlSecond.$valid).toBe(true);
        
        // test case 2-5
        angular.element(inputElement).triggerHandler('focus');
        changeInputValue(angular.element(inputElement),'01/2');
        angular.element(inputElement).triggerHandler('blur');
        expect(angular.element(inputElement).val()).toEqual('2014/01/02');
        expect(scope.result.closeSt).toEqual('20140102');
        expect(modelCtrl.$valid).toBe(true);
        
        angular.element(inputElementSecond).triggerHandler('focus');
        changeInputValue(angular.element(inputElementSecond),'01/3');
        angular.element(inputElementSecond).triggerHandler('blur');
        expect(angular.element(inputElementSecond).val()).toEqual('2014/01/03');
        expect(scope.result.closeEd).toEqual('20140103');
        expect(modelCtrlSecond.$valid).toBe(true);
        
        // test case 2-6
        angular.element(inputElement).triggerHandler('focus');
        changeInputValue(angular.element(inputElement),'01/02');
        angular.element(inputElement).triggerHandler('blur');
        expect(angular.element(inputElement).val()).toEqual('2014/01/02');
        expect(scope.result.closeSt).toEqual('20140102');
        expect(modelCtrl.$valid).toBe(true);
        
        angular.element(inputElementSecond).triggerHandler('focus');
        changeInputValue(angular.element(inputElementSecond),'01/03');
        angular.element(inputElementSecond).triggerHandler('blur');
        expect(angular.element(inputElementSecond).val()).toEqual('2014/01/03');
        expect(scope.result.closeEd).toEqual('20140103');
        expect(modelCtrlSecond.$valid).toBe(true);
        
        // test case 3-1
        angular.element(inputElement).triggerHandler('focus');
        changeInputValue(angular.element(inputElement),'30102');
        angular.element(inputElement).triggerHandler('blur');
        expect(angular.element(inputElement).val()).toEqual('2003/01/02');
        expect(scope.result.closeSt).toEqual('20030102');
        expect(modelCtrl.$valid).toBe(true);
        
        angular.element(inputElementSecond).triggerHandler('focus');
        changeInputValue(angular.element(inputElementSecond),'30103');
        angular.element(inputElementSecond).triggerHandler('blur');
        expect(angular.element(inputElementSecond).val()).toEqual('2003/01/03');
        expect(scope.result.closeEd).toEqual('20030103');
        expect(modelCtrlSecond.$valid).toBe(true);

        // test case 3-2
        angular.element(inputElement).triggerHandler('focus');
        changeInputValue(angular.element(inputElement),'030102');
        angular.element(inputElement).triggerHandler('blur');
        expect(angular.element(inputElement).val()).toEqual('2003/01/02');
        expect(scope.result.closeSt).toEqual('20030102');
        expect(modelCtrl.$valid).toBe(true);
        
        angular.element(inputElementSecond).triggerHandler('focus');
        changeInputValue(angular.element(inputElementSecond),'030103');
        angular.element(inputElementSecond).triggerHandler('blur');
        expect(angular.element(inputElementSecond).val()).toEqual('2003/01/03');
        expect(scope.result.closeEd).toEqual('20030103');
        expect(modelCtrlSecond.$valid).toBe(true);
        
        // test case 3-4
        angular.element(inputElement).triggerHandler('focus');
        changeInputValue(angular.element(inputElement),'20030102');
        angular.element(inputElement).triggerHandler('blur');
        expect(angular.element(inputElement).val()).toEqual('2003/01/02');
        expect(scope.result.closeSt).toEqual('20030102');
        expect(modelCtrl.$valid).toBe(true);
        
        angular.element(inputElementSecond).triggerHandler('focus');
        changeInputValue(angular.element(inputElementSecond),'20030103');
        angular.element(inputElementSecond).triggerHandler('blur');
        expect(angular.element(inputElementSecond).val()).toEqual('2003/01/03');
        expect(scope.result.closeEd).toEqual('20030103');
        expect(modelCtrlSecond.$valid).toBe(true);
        
        // test case 3-5
        angular.element(inputElement).triggerHandler('focus');
        changeInputValue(angular.element(inputElement),'3/1/2');
        angular.element(inputElement).triggerHandler('blur');
        expect(angular.element(inputElement).val()).toEqual('2003/01/02');
        expect(scope.result.closeSt).toEqual('20030102');
        expect(modelCtrl.$valid).toBe(true);
        
        angular.element(inputElementSecond).triggerHandler('focus');
        changeInputValue(angular.element(inputElementSecond),'3/1/3');
        angular.element(inputElementSecond).triggerHandler('blur');
        expect(angular.element(inputElementSecond).val()).toEqual('2003/01/03');
        expect(scope.result.closeEd).toEqual('20030103');
        expect(modelCtrlSecond.$valid).toBe(true);

        // test case 3-6
        angular.element(inputElement).triggerHandler('focus');
        changeInputValue(angular.element(inputElement),'3/1/02');
        angular.element(inputElement).triggerHandler('blur');
        expect(angular.element(inputElement).val()).toEqual('2003/01/02');
        expect(scope.result.closeSt).toEqual('20030102');
        expect(modelCtrl.$valid).toBe(true);
        
        angular.element(inputElementSecond).triggerHandler('focus');
        changeInputValue(angular.element(inputElementSecond),'3/1/03');
        angular.element(inputElementSecond).triggerHandler('blur');
        expect(angular.element(inputElementSecond).val()).toEqual('2003/01/03');
        expect(scope.result.closeEd).toEqual('20030103');
        expect(modelCtrlSecond.$valid).toBe(true);

        // test case 3-7
        angular.element(inputElement).triggerHandler('focus');
        changeInputValue(angular.element(inputElement),'3/01/2');
        angular.element(inputElement).triggerHandler('blur');
        expect(angular.element(inputElement).val()).toEqual('2003/01/02');
        expect(scope.result.closeSt).toEqual('20030102');
        expect(modelCtrl.$valid).toBe(true);
        
        angular.element(inputElementSecond).triggerHandler('focus');
        changeInputValue(angular.element(inputElementSecond),'3/01/3');
        angular.element(inputElementSecond).triggerHandler('blur');
        expect(angular.element(inputElementSecond).val()).toEqual('2003/01/03');
        expect(scope.result.closeEd).toEqual('20030103');
        expect(modelCtrlSecond.$valid).toBe(true);

        // test case 3-8
        angular.element(inputElement).triggerHandler('focus');
        changeInputValue(angular.element(inputElement),'3/01/02');
        angular.element(inputElement).triggerHandler('blur');
        expect(angular.element(inputElement).val()).toEqual('2003/01/02');
        expect(scope.result.closeSt).toEqual('20030102');
        expect(modelCtrl.$valid).toBe(true);
        
        angular.element(inputElementSecond).triggerHandler('focus');
        changeInputValue(angular.element(inputElementSecond),'3/01/03');
        angular.element(inputElementSecond).triggerHandler('blur');
        expect(angular.element(inputElementSecond).val()).toEqual('2003/01/03');
        expect(scope.result.closeEd).toEqual('20030103');
        expect(modelCtrlSecond.$valid).toBe(true);
        
        // test case 3-9
        angular.element(inputElement).triggerHandler('focus');
        changeInputValue(angular.element(inputElement),'03/1/2');
        angular.element(inputElement).triggerHandler('blur');
        expect(angular.element(inputElement).val()).toEqual('2003/01/02');
        expect(scope.result.closeSt).toEqual('20030102');
        expect(modelCtrl.$valid).toBe(true);
        
        angular.element(inputElementSecond).triggerHandler('focus');
        changeInputValue(angular.element(inputElementSecond),'03/1/3');
        angular.element(inputElementSecond).triggerHandler('blur');
        expect(angular.element(inputElementSecond).val()).toEqual('2003/01/03');
        expect(scope.result.closeEd).toEqual('20030103');
        expect(modelCtrlSecond.$valid).toBe(true);

        // test case 3-10
        angular.element(inputElement).triggerHandler('focus');
        changeInputValue(angular.element(inputElement),'03/1/02');
        angular.element(inputElement).triggerHandler('blur');
        expect(angular.element(inputElement).val()).toEqual('2003/01/02');
        expect(scope.result.closeSt).toEqual('20030102');
        expect(modelCtrl.$valid).toBe(true);
        
        angular.element(inputElementSecond).triggerHandler('focus');
        changeInputValue(angular.element(inputElementSecond),'03/1/03');
        angular.element(inputElementSecond).triggerHandler('blur');
        expect(angular.element(inputElementSecond).val()).toEqual('2003/01/03');
        expect(scope.result.closeEd).toEqual('20030103');
        expect(modelCtrlSecond.$valid).toBe(true);

        // test case 3-11
        angular.element(inputElement).triggerHandler('focus');
        changeInputValue(angular.element(inputElement),'03/01/2');
        angular.element(inputElement).triggerHandler('blur');
        expect(angular.element(inputElement).val()).toEqual('2003/01/02');
        expect(scope.result.closeSt).toEqual('20030102');
        expect(modelCtrl.$valid).toBe(true);
        
        angular.element(inputElementSecond).triggerHandler('focus');
        changeInputValue(angular.element(inputElementSecond),'03/01/3');
        angular.element(inputElementSecond).triggerHandler('blur');
        expect(angular.element(inputElementSecond).val()).toEqual('2003/01/03');
        expect(scope.result.closeEd).toEqual('20030103');
        expect(modelCtrlSecond.$valid).toBe(true);

        // test case 3-12
        angular.element(inputElement).triggerHandler('focus');
        changeInputValue(angular.element(inputElement),'03/01/02');
        angular.element(inputElement).triggerHandler('blur');
        expect(angular.element(inputElement).val()).toEqual('2003/01/02');
        expect(scope.result.closeSt).toEqual('20030102');
        expect(modelCtrl.$valid).toBe(true);
        
        angular.element(inputElementSecond).triggerHandler('focus');
        changeInputValue(angular.element(inputElementSecond),'03/01/03');
        angular.element(inputElementSecond).triggerHandler('blur');
        expect(angular.element(inputElementSecond).val()).toEqual('2003/01/03');
        expect(scope.result.closeEd).toEqual('20030103');
        expect(modelCtrlSecond.$valid).toBe(true);
        
        // test case 3-13
        angular.element(inputElement).triggerHandler('focus');
        changeInputValue(angular.element(inputElement),'003/01/02');
        angular.element(inputElement).triggerHandler('blur');
        expect(angular.element(inputElement).val()).toEqual('2003/01/02');
        expect(scope.result.closeSt).toEqual('20030102');
        expect(modelCtrl.$valid).toBe(true);
        
        angular.element(inputElementSecond).triggerHandler('focus');
        changeInputValue(angular.element(inputElementSecond),'003/01/03');
        angular.element(inputElementSecond).triggerHandler('blur');
        expect(angular.element(inputElementSecond).val()).toEqual('2003/01/03');
        expect(scope.result.closeEd).toEqual('20030103');
        expect(modelCtrlSecond.$valid).toBe(true);

        // test case 3-17
        angular.element(inputElement).triggerHandler('focus');
        changeInputValue(angular.element(inputElement),'2003/1/2');
        angular.element(inputElement).triggerHandler('blur');
        expect(angular.element(inputElement).val()).toEqual('2003/01/02');
        expect(scope.result.closeSt).toEqual('20030102');
        expect(modelCtrl.$valid).toBe(true);
        
        angular.element(inputElementSecond).triggerHandler('focus');
        changeInputValue(angular.element(inputElementSecond),'2003/1/3');
        angular.element(inputElementSecond).triggerHandler('blur');
        expect(angular.element(inputElementSecond).val()).toEqual('2003/01/03');
        expect(scope.result.closeEd).toEqual('20030103');
        expect(modelCtrlSecond.$valid).toBe(true);
        
        // test case 3-18
        angular.element(inputElement).triggerHandler('focus');
        changeInputValue(angular.element(inputElement),'2003/1/02');
        angular.element(inputElement).triggerHandler('blur');
        expect(angular.element(inputElement).val()).toEqual('2003/01/02');
        expect(scope.result.closeSt).toEqual('20030102');
        expect(modelCtrl.$valid).toBe(true);
        
        angular.element(inputElementSecond).triggerHandler('focus');
        changeInputValue(angular.element(inputElementSecond),'2003/1/03');
        angular.element(inputElementSecond).triggerHandler('blur');
        expect(angular.element(inputElementSecond).val()).toEqual('2003/01/03');
        expect(scope.result.closeEd).toEqual('20030103');
        expect(modelCtrlSecond.$valid).toBe(true);
        
        // test case 3-19
        angular.element(inputElement).triggerHandler('focus');
        changeInputValue(angular.element(inputElement),'2003/01/2');
        angular.element(inputElement).triggerHandler('blur');
        expect(angular.element(inputElement).val()).toEqual('2003/01/02');
        expect(scope.result.closeSt).toEqual('20030102');
        expect(modelCtrl.$valid).toBe(true);
        
        angular.element(inputElementSecond).triggerHandler('focus');
        changeInputValue(angular.element(inputElementSecond),'2003/01/3');
        angular.element(inputElementSecond).triggerHandler('blur');
        expect(angular.element(inputElementSecond).val()).toEqual('2003/01/03');
        expect(scope.result.closeEd).toEqual('20030103');
        expect(modelCtrlSecond.$valid).toBe(true);
        
        // test case 3-20
        angular.element(inputElement).triggerHandler('focus');
        changeInputValue(angular.element(inputElement),'2003/01/02');
        angular.element(inputElement).triggerHandler('blur');
        expect(angular.element(inputElement).val()).toEqual('2003/01/02');
        expect(scope.result.closeSt).toEqual('20030102');
        expect(modelCtrl.$valid).toBe(true);
        
        angular.element(inputElementSecond).triggerHandler('focus');
        changeInputValue(angular.element(inputElementSecond),'2003/01/03');
        angular.element(inputElementSecond).triggerHandler('blur');
        expect(angular.element(inputElementSecond).val()).toEqual('2003/01/03');
        expect(scope.result.closeEd).toEqual('20030103');
        expect(modelCtrlSecond.$valid).toBe(true);

        // test case 4-1
        angular.element(inputElement).triggerHandler('focus');
        changeInputValue(angular.element(inputElement),'42');
        angular.element(inputElement).triggerHandler('blur');
        expect(angular.element(inputElement).val()).toEqual('42');
        expect(scope.result.closeSt).toEqual('42');
        //    エラー
        expect(modelCtrl.$valid).toBe(false);
        
        angular.element(inputElementSecond).triggerHandler('focus');
        changeInputValue(angular.element(inputElementSecond),'32');
        angular.element(inputElementSecond).triggerHandler('blur');
        expect(angular.element(inputElementSecond).val()).toEqual('32');
        expect(scope.result.closeEd).toEqual('32');
        //    エラー
        expect(modelCtrlSecond.$valid).toBe(false);
        
        // test case 4-1
        angular.element(inputElement).triggerHandler('focus');
        changeInputValue(angular.element(inputElement),'1321');
        angular.element(inputElement).triggerHandler('blur');
        expect(angular.element(inputElement).val()).toEqual('1321');
        expect(scope.result.closeSt).toEqual('1321');
        //    エラー
        expect(modelCtrl.$valid).toBe(false);
        
        angular.element(inputElementSecond).triggerHandler('focus');
        changeInputValue(angular.element(inputElementSecond),'1421');
        angular.element(inputElementSecond).triggerHandler('blur');
        expect(angular.element(inputElementSecond).val()).toEqual('1421');
        expect(scope.result.closeEd).toEqual('1421');
        //    エラー
        expect(modelCtrlSecond.$valid).toBe(false);
        
        // test case 4-2
        angular.element(inputElement).triggerHandler('focus');
        changeInputValue(angular.element(inputElement),'18991231');
        angular.element(inputElement).triggerHandler('blur');
        expect(angular.element(inputElement).val()).toEqual('18991231');
        expect(scope.result.closeSt).toEqual('18991231');
        //    エラー
        expect(modelCtrl.$valid).toBe(false);
        
        angular.element(inputElementSecond).triggerHandler('focus');
        changeInputValue(angular.element(inputElementSecond),'21000101');
        angular.element(inputElementSecond).triggerHandler('blur');
        expect(angular.element(inputElementSecond).val()).toEqual('21000101');
        expect(scope.result.closeEd).toEqual('21000101');
        //    エラー
        expect(modelCtrlSecond.$valid).toBe(false);
        
        // test case 4-3
        angular.element(inputElement).triggerHandler('focus');
        changeInputValue(angular.element(inputElement),'21000101');
        angular.element(inputElement).triggerHandler('blur');
        expect(angular.element(inputElement).val()).toEqual('21000101');
        expect(scope.result.closeSt).toEqual('21000101');
        //    エラー
        expect(modelCtrl.$valid).toBe(false);
        
        angular.element(inputElementSecond).triggerHandler('focus');
        changeInputValue(angular.element(inputElementSecond),'21000301');
        angular.element(inputElementSecond).triggerHandler('blur');
        expect(angular.element(inputElementSecond).val()).toEqual('21000301');
        expect(scope.result.closeEd).toEqual('21000301');
        //    エラー
        expect(modelCtrlSecond.$valid).toBe(false);
        
        // test case 4-4
        angular.element(inputElement).triggerHandler('focus');
        changeInputValue(angular.element(inputElement),'2100010');
        angular.element(inputElement).triggerHandler('blur');
        expect(angular.element(inputElement).val()).toEqual('2100010');
        expect(scope.result.closeSt).toEqual('2100010');
        //    エラー
        expect(modelCtrl.$valid).toBe(false);
        
        angular.element(inputElementSecond).triggerHandler('focus');
        changeInputValue(angular.element(inputElementSecond),'210001');
        angular.element(inputElementSecond).triggerHandler('blur');
        expect(angular.element(inputElementSecond).val()).toEqual('210001');
        expect(scope.result.closeEd).toEqual('210001');
        //    エラー
        expect(modelCtrlSecond.$valid).toBe(false);
        
        // test case 4-5
        angular.element(inputElement).triggerHandler('focus');
        changeInputValue(angular.element(inputElement),'990101');
        angular.element(inputElement).triggerHandler('blur');
        expect(angular.element(inputElement).val()).toEqual('2099/01/01');
        expect(scope.result.closeSt).toEqual('20990101');
        expect(modelCtrl.$valid).toBe(true);
        
        angular.element(inputElementSecond).triggerHandler('focus');
        changeInputValue(angular.element(inputElementSecond),'210001');
        angular.element(inputElementSecond).triggerHandler('blur');
        expect(angular.element(inputElementSecond).val()).toEqual('210001');
        expect(scope.result.closeEd).toEqual('210001');
        //    エラー
        expect(modelCtrlSecond.$error.ccDate).toBe(false);
        
        // test case 4-6
        angular.element(inputElement).triggerHandler('focus');
        changeInputValue(angular.element(inputElement),'');
        angular.element(inputElement).triggerHandler('blur');
        expect(angular.element(inputElement).val()).toEqual('');
        expect(scope.result.closeSt).toEqual('');
        expect(modelCtrl.$invalid).toBe(true);
        
        angular.element(inputElementSecond).triggerHandler('focus');
        changeInputValue(angular.element(inputElementSecond),'');
        angular.element(inputElementSecond).triggerHandler('blur');
        expect(angular.element(inputElementSecond).val()).toEqual('');
        expect(scope.result.closeEd).toEqual('');
        expect(modelCtrlSecond.$invalid).toBe(true);
        /*
        */
    });
  });
 
 
  
});
