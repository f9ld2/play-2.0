'use strict';

/* jasmine specs for directives go here */

describe('Test directive', function() {
    var element, scope, $compile, $httpBackend, changeSelectValue, compile, templateHtml, inputElement0, inputElement1,
    inputHtml0,inputHtml1,selectElement,selectHtml,divElement,spanElement;
    beforeEach(module('directives'));
    beforeEach(inject(function($injector, $sniffer) {
        // inject $compile & $roorScope
        $compile = $injector.get('$compile');
        scope = $injector.get('$rootScope');
        $httpBackend = $injector.get('$httpBackend');
        var record1 = [
                      {code : '011', name :'部門1', shortName :'部門部門1'},
                      {code : '012', name :'部門2', shortName :'部門部門2'},
                      {code : '013', name :'部門3', shortName :'部門部門3'},
                      ];
        var record2 = [
                       {code : '011', name :'部門1', shortName :'部門部門1'},
                       {code : '012', name :'部門2', shortName :'部門部門2'},
                       {code : '013', name :'部門3', shortName :'部門部門3'},
                       {code : '014', name :'部門4', shortName :'部門部門4'},
                       ];
        $httpBackend.when('GET', '/core/codename/m000depars?hakkoDay=19010101').respond(record1, {checkData : true});
        $httpBackend.when('GET', '/core/codename/m000depar/01?hakkoDay=19010101').respond(record2, {checkData : true});
        $httpBackend.when('GET', '/core/codename/m000depar/02?hakkoDay=19010101').respond([], {checkData : true});
        
        // declare result to bind data.
        scope.result = {
            bmnCd : '',
            mkrNm : '',
            hakkoDay: '',
            jigyobuCd:''
        };
        changeSelectValue = function(elm, index) {
            angular.element(elm.children()[index]).attr('selected','selected');
            browserTrigger(selectElement, 'change');
        };
        // compile directive need to test.
        compile = function(label, partition, id,name, ngModel, required, readOnly,
                delexist, shortName, para01, para02, id2, name2, ngModel2) {
            var htmlText = '';
            htmlText += '<cc-bmn-combobox';
            htmlText += ' cc-label="' + label + '"';
            htmlText += ' cc-partition="' + partition + '"';
            htmlText += ' id="' + id + '"';
            htmlText += ' name="' + name + '"';
            htmlText += ' ng-model="' + ngModel + '"';
            if (required != null) {
                htmlText += ' cc-required='+ required;
            }
            if (readOnly != null && readOnly== true) {
                htmlText += ' cc-readonly='+ readOnly ;
            }
            htmlText += ' cc-delexist="' + delexist + '"';
            htmlText += ' cc-shortname="' + shortName + '"';
            htmlText += ' cc-parameter01="' + para01 + '"';
            htmlText += ' cc-parameter02="' + para02 + '"';
            htmlText += ' " />'; //use to test
            
            var input = ' <input ng-model="' + ngModel + '"  />';
            
            element = $compile(htmlText)(scope);
            templateHtml = angular.element('<div>').append(angular.element(element).clone()).html();
        };
    }));
    
    describe('cc-bmnCd-combobox', function() {
        it('it should be right binding value and view value', function() {
            scope.result.jigyobuCd = '';
            compile('部門','3','bmnCd','bmnCd','result.bmnCd','true',null,'true','false',
                    'result.jigyobuCd','result.hakkoDay');
            $httpBackend.flush();
            
            //-----------it should has only div, span and select element
            expect(element.has('div').length).toEqual(1);
            expect(element.has('span').length).toEqual(1);
            expect(element.has('select').length).toEqual(1);
            var children = element.children();
            expect(children.length).toEqual(2);
            
            //-----------div should has only span element
            divElement = angular.element(children[0]);
            var divChildren = divElement.children();
            expect(divElement.has('span').length).toEqual(1);
            expect(divChildren.length).toEqual(1);
            expect(divElement.hasClass('cs-label')).toBe(true);
            expect(divElement.hasClass('cs-label-large-required')).toBe(true);
            
            //-----------text of spanElement must be ' *'
            spanElement = angular.element(divChildren[0]);
            expect(spanElement.text()).toEqual(' *');
            expect(spanElement.hasClass('cs-txt-required')).toBe(true);
            
            //-----------test selectElement
            selectElement = angular.element(children[1]);
            expect(selectElement.attr('ui-select2')).toEqual("{ allowClear: false}");
            expect(selectElement.attr('cc-blur-validation')).toEqual("");
            expect(selectElement.attr('required')).toEqual("required");
            expect(selectElement.attr('readonly')).toEqual(undefined);
            expect(selectElement.attr('ng-model')).toEqual("ngModel");
            expect(selectElement.attr('id')).toEqual("bmnCd");
            expect(selectElement.attr('name')).toEqual("bmnCd");
            
            expect(selectElement.hasClass('cs-char-25')).toBe(true);
            expect(selectElement.hasClass('cs-char-15')).toBe(false);
            expect(selectElement.hasClass('cs-left')).toBe(true);
            
            // Test partition == ''
            compile('部門','','bmnCd','bmnCd','cond.bmnCd','true',null,'false','false', 'result.jigyobuCd','result.hakkoDay');
            divElement = angular.element(element.children()[0]);
            expect(divElement.children().length).toEqual(0);
            expect(divElement.hasClass('cs-label')).toBe(true);
            expect(divElement.hasClass('cs-label-divide-zero')).toBe(true);
            expect(divElement.hasClass('cs-label-large-required')).toBe(false);
            expect(divElement.hasClass('cs-label-large')).toBe(false);
            
            // Test required == 'false'
            compile('部門','3','bmnCd','bmnCd','cond.bmnCd','false',null,'false','false', 'result.jigyobuCd','result.hakkoDay');
            //-----------it should hasn't span element
            expect(element.has('div').length).toEqual(1);
            expect(element.has('span').length).toEqual(0);
            expect(element.has('select').length).toEqual(1);
            var children = element.children();
            expect(children.length).toEqual(2);
            
            //-----------div should hasn't span element
            divElement = angular.element(children[0]);
            var divChildren = divElement.children();
            expect(divElement.has('span').length).toEqual(0);
            expect(divChildren.length).toEqual(0);
            expect(divElement.hasClass('cs-label-large')).toBe(true);
            expect(divElement.hasClass('cs-label')).toBe(true);
            expect(divElement.hasClass('cs-label-large-required')).toBe(false);
            expect(divElement.hasClass('cs-label-divide-zero')).toBe(false);

            //-----------test selectElement
            selectElement = angular.element(children[1]);
            expect(selectElement.attr('required')).toEqual(undefined);
            expect(selectElement.attr('readonly')).toEqual(undefined);
            expect(selectElement.attr('ui-select2')).toEqual("{ allowClear: true}");
            
            // Test readonly = true
            compile('部門','','bmnCd','bmnCd','cond.bmnCd',null,'true','false','false', 'result.jigyobuCd','result.hakkoDay');
            //-----------it should hasn't span element
            expect(element.has('div').length).toEqual(1);
            expect(element.has('span').length).toEqual(0);
            expect(element.has('select').length).toEqual(1);
            var children = element.children();
            expect(children.length).toEqual(2);
            
            //-----------div should hasn't span element
            divElement = angular.element(children[0]);
            var divChildren = divElement.children();
            expect(divElement.has('span').length).toEqual(0);
            expect(divChildren.length).toEqual(0);

            //-----------test selectElement
            selectElement = angular.element(children[1]);
            expect(selectElement.attr('required')).toEqual(undefined);
            expect(selectElement.attr('disabled')).toEqual(undefined);
            expect(selectElement.attr('ui-select2')).toEqual(undefined);
            expect(selectElement.attr('cc-blur-validation')).toEqual('');
            
            // Test shortName = true
            compile('部門','3','bmnCd','bmnCd','cond.bmnCd','true',null,'false','true', 'result.jigyobuCd','result.hakkoDay');
            selectElement = angular.element(element.children()[1]);
            expect(selectElement.hasClass('cs-char-25')).toBe(false);
            expect(selectElement.hasClass('cs-char-15')).toBe(true);
            expect(selectElement.hasClass('cs-left')).toBe(true);
        });

        it('it should be right binding value and view value load data', function() {
            scope.result.jigyobuCd = '';
            compile('部門','3','bmnCd','bmnCd','result.bmnCd','true',null,'true','false',
                    'result.jigyobuCd','result.hakkoDay');
            $httpBackend.flush();
            selectElement = element.find('select')[0];
            selectHtml = angular.element('<div>').append(angular.element(selectElement).clone()).html();
            
            expect(selectHtml).toContain('<option value="011">011:部門1</option>');
            expect(selectHtml).toContain('<option value="012">012:部門2</option>');
            expect(selectHtml).toContain('<option value="013">013:部門3</option>');
            expect(selectHtml).not.toContain('<option value="014">014:部門4</option>');
        });

        it('it should be right binding value and view value', function() {
            scope.result.jigyobuCd = '01';
            compile('部門','3','bmnCd','bmnCd','result.bmnCd','true',null,'true','false',
                    'result.jigyobuCd','result.hakkoDay');
            $httpBackend.flush();
            selectElement = element.find('select')[0];
            selectHtml = angular.element('<div>').append(angular.element(selectElement).clone()).html();
            
            expect(element.find('select').length).toEqual(1);
            expect(element.find('option').length).toEqual(5);
            expect(selectHtml).toContain('<option value="011">011:部門1</option>');
            expect(selectHtml).toContain('<option value="012">012:部門2</option>');
            expect(selectHtml).toContain('<option value="013">013:部門3</option>');
            expect(selectHtml).toContain('<option value="014">014:部門4</option>');
            
            var input = $(element.find('option')[2]).prop('selected',true);
            browserTrigger(selectElement, 'change');
            expect('012').toEqual(scope.result.bmnCd);
            
            selectElement = angular.element(element.children()[1]);
            changeSelectValue(selectElement, 2);
            expect(scope.result.bmnCd).toEqual('012');
        });
        
        it('it should be right binding value and view value', function() {
            scope.result.jigyobuCd = '02';
            compile('部門','3','bmnCd','bmnCd','result.bmnCd','true',null,'true','false',
                    'result.jigyobuCd','result.hakkoDay');
            $httpBackend.flush();
            selectElement = element.find('select')[0];
            selectHtml = angular.element('<div>').append(angular.element(selectElement).clone()).html();
            
            expect(element.find('select').length).toEqual(1);
            expect(element.find('option').length).toEqual(1);
            expect(selectHtml).not.toContain('<option value="011">011:部門1</option>');
            expect(selectHtml).not.toContain('<option value="012">012:部門2</option>');
            expect(selectHtml).not.toContain('<option value="013">013:部門3</option>');
            expect(selectHtml).not.toContain('<option value="014">014:部門4</option>');
        });
        
    });
});
