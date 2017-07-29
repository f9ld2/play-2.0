'use strict';

/* jasmine specs for directives go here */

describe('Test directive', function() {
    var element, scope, $compile, $httpBackend, changeSelectValue, compile;
    var templateHtml, selectElement, divElement, spanElement;
    beforeEach(module('directives'));
    beforeEach(inject(function($injector, $sniffer) {
        // inject $compile & $roorScope
        $compile = $injector.get('$compile');
        scope = $injector.get('$rootScope');
        $httpBackend = $injector.get('$httpBackend');
        
        var listRecord1 = [{code:'0000001',name:'メーカー名称（漢字）1',shortName:'メーカー名略称1',kubun:'1'},
                           {code:'0000009',name:'メーカー名称（漢字）9',shortName:'メーカー名略称9',kubun:'9'}];
        var listRecord2 = [{code:'0000001',name:'メーカー名称（漢字）1',shortName:'メーカー名略称1',kubun:'1'},
                           {code:'0000002',name:'メーカー名称（漢字）2',shortName:'メーカー名略称2',kubun:'2'},
                           {code:'0000003',name:'メーカー名称（漢字）3',shortName:'メーカー名略称3',kubun:'3'}];
        $httpBackend.when('GET', '/core/codemaster/m017meim/MA/AREA_CD?hakkoDay=19010101').respond(listRecord1);
        $httpBackend.when('GET', '/core/codemaster/m017meim/MA/AREA_CD?hakkoDay=20140101').respond(listRecord2);
        $httpBackend.when('GET', '/core/codemaster/m017meim/MA/AREA_CD?hakkoDay=19010102').respond(listRecord1);
        $httpBackend.when('GET', '/core/codemaster/m017meim/MA/AREA_CD?hakkoDay=20140102').respond([]);
        // emulates clicking to select option
        changeSelectValue = function(elm, index) {
            angular.element(elm.children()[index]).attr('selected','selected');
            browserTrigger(selectElement, 'change');
        };
        
        scope.cond = {
            hakkoDay:'',
            areaCd:''
        }
        
        // compile directive need to test: label, partition, id,name, ngModel, required, readOnly,
        //                                  maxlength, delexist, key1, key2, shortName, para01 
        compile = function(label, partition, id,name, ngModel, required, readOnly,
                maxlength, delexist, key1, key2, shortName, para01 ) {
            var htmlText = '';
            htmlText += '<cc-mei-combobox';
            htmlText += ' cc-label="' + label + '"';
            htmlText += ' cc-partition="' + partition + '"';
            htmlText += ' id="' + id + '"';
            htmlText += ' name="' + name + '"';
            htmlText += ' ng-model="' + ngModel + '"';
            if (required != null) {
                htmlText += ' cc-required='+ required;
            }
            if (readOnly != null) {
                htmlText += ' cc-readonly='+ readOnly ;
            }
            htmlText += ' ng-maxlength="' + maxlength + '"';
            htmlText += ' cc-delexist="' + delexist + '"';
            htmlText += ' cc-key1="' + key1 + '"';
            htmlText += ' cc-key2="' + key2 + '"';
            htmlText += ' cc-shortname="' + shortName + '"';
            htmlText += ' cc-parameter01="' + para01 + '" />';

            element = $compile(htmlText)(scope);
//            scope.$digest();
            templateHtml = angular.element('<div>').append(angular.element(element).clone()).html();
        };
    }));
    describe('cc-kaisya-combobox', function() {

        // test template
        it('it should render right html', function() {

            // Test partition != '', required = true, shortName = false, delexist = false
            compile('エリア','3','areaCd','areaCd','cond.areaCd','true',null,'2','true','MA','AREA_CD','false', 
                    'cond.hakkoDay');
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
            expect(selectElement.attr('ui-select2')).toEqual("{allowClear: false}");
            expect(selectElement.attr('cc-blur-validation')).toEqual("");
            expect(selectElement.attr('required')).toEqual("required");
            expect(selectElement.attr('readonly')).toEqual(undefined);
            expect(selectElement.attr('ng-model')).toEqual("ngModel");
            expect(selectElement.attr('id')).toEqual("areaCd");
            expect(selectElement.attr('name')).toEqual("areaCd");
            
//            expect(selectElement.hasClass('cs-char-25')).toBe(true);
            expect(selectElement.hasClass('cs-char-15')).toBe(true);
            expect(selectElement.hasClass('cs-left')).toBe(true);
            
            // Test partition == ''
            compile('エリア','','areaCd','areaCd','cond.areaCd','true',null,'2','true','MA','AREA_CD','false', 
                    'cond.hakkoDay');
            divElement = angular.element(element.children()[0]);
            expect(divElement.children().length).toEqual(0);
            expect(divElement.hasClass('cs-label')).toBe(true);
            expect(divElement.hasClass('cs-label-divide-zero')).toBe(true);
            expect(divElement.hasClass('cs-label-large-required')).toBe(false);
            expect(divElement.hasClass('cs-label-large')).toBe(false);
            
            // Test required == 'false'
            compile('エリア','3','areaCd','areaCd','cond.areaCd','false',null,'2','true','MA','AREA_CD','false', 
                    'cond.hakkoDay');
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
            expect(selectElement.attr('ui-select2')).toEqual("{allowClear: true}");
            
            // Test readonly = true
            compile('エリア','','areaCd','areaCd','cond.areaCd',null,'true','2','true','MA','AREA_CD','false', 
                    'cond.hakkoDay');
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
            expect(selectElement.attr('disabled')).toEqual('disabled');
            expect(selectElement.attr('ui-select2')).toEqual("{allowClear: false}");
            expect(selectElement.attr('cc-blur-validation')).toEqual(undefined);
            
            // Test shortName = true
            compile('エリア','3','areaCd','areaCd','cond.areaCd',null,'true','2','true','MA','AREA_CD','true', 
                    'cond.hakkoDay');
            selectElement = angular.element(element.children()[1]);
            expect(selectElement.hasClass('cs-char-25')).toBe(false);
            expect(selectElement.hasClass('cs-char-15')).toBe(true);
            expect(selectElement.hasClass('cs-left')).toBe(true);
        });

        // test logic
        it('it should be right binding value and view value', function() {
            // shortName = false, delexist = false, default hakkoDay
            compile('エリア','3','areaCd','areaCd','cond.areaCd','true',null,'2','false','MA','AREA_CD','false', 
                    'cond.hakkoDay');
            $httpBackend.flush();
            selectElement = angular.element(element.children()[1]);
            var children = selectElement.children();
            //-----------It should has 2 <option/>
            expect(children.length).toEqual(2);
            expect(angular.element(children[0]).attr('value')).toEqual('');
            expect(angular.element(children[0]).text()).toEqual('');
            expect(angular.element(children[1]).attr('value')).toEqual('0000001');
            expect(angular.element(children[1]).attr('value')).toEqual('0000001');
            expect(angular.element(children[1]).text()).toEqual('0000001:メーカー名称（漢字）1');
            //-----------first option should is selected after initialize
            expect(angular.element(children[0]).prop('selected')).toBeTruthy();
            //-----------binding value should is empty after initialize
            expect(scope.cond.areaCd).toEqual('');
            //-----------view value should is empty after initialize
//            expect(selectElement.text()).toEqual('');
            //-----------select second option
            changeSelectValue(selectElement, 1);
            //-----------binding value should is 0000001 after selecting
            expect(scope.cond.areaCd).toEqual('0000001');
            //-----------view value should is '0000001:メーカー名称（漢字）1' after selecting
            expect(selectElement.text()).toEqual('0000001:メーカー名称（漢字）1');
            
            // shortName = true
            compile('エリア','3','areaCd','areaCd','cond.areaCd','true',null,'2','false','MA','AREA_CD','true', 
                    'cond.hakkoDay');
            $httpBackend.flush();
            selectElement = angular.element(element.children()[1]);
            //-----------select second option
            changeSelectValue(selectElement, 1);
            //-----------binding value should is 0000001 after selecting
            expect(scope.cond.areaCd).toEqual('0000001');
            //-----------view value should is '0000001:メーカー名略称1' after selecting
            expect(selectElement.text()).toEqual('0000001:メーカー名称（漢字）1');
            
            // delexist = false
            compile('エリア','3','areaCd','areaCd','cond.areaCd','true',null,'2','false','MA','AREA_CD','false', 
                    'cond.hakkoDay');
            $httpBackend.flush();
            selectElement = angular.element(element.children()[1]);
            var children = selectElement.children();
            //-----------It should has 2 <option/>
            expect(children.length).toEqual(2);
            expect(angular.element(children[0]).attr('value')).toEqual('');
            expect(angular.element(children[0]).text()).toEqual('');
            expect(angular.element(children[1]).attr('value')).toEqual('0000001');
            expect(angular.element(children[1]).text()).toEqual('0000001:メーカー名称（漢字）1');
            
            // hakkoDay = 20140101
            scope.cond.hakkoDay = '20140101'
            compile('エリア','3','areaCd','areaCd','cond.areaCd','true',null,'2','false','MA','AREA_CD','false',
                    'cond.hakkoDay');
            $httpBackend.flush();
            selectElement = angular.element(element.children()[1]);
            var children = selectElement.children();
            //-----------It should has 4 <option/>
            expect(children.length).toEqual(4);
            expect(angular.element(children[0]).attr('value')).toEqual('');
            expect(angular.element(children[0]).text()).toEqual('');
            expect(angular.element(children[1]).attr('value')).toEqual('0000001');
            expect(angular.element(children[1]).text()).toEqual('0000001:メーカー名称（漢字）1');
            expect(angular.element(children[2]).attr('value')).toEqual('0000002');
            expect(angular.element(children[2]).text()).toEqual('0000002:メーカー名称（漢字）2');
            expect(angular.element(children[3]).attr('value')).toEqual('0000003');
            expect(angular.element(children[3]).text()).toEqual('0000003:メーカー名称（漢字）3');
            
            // hakkoDay = 19010102
            scope.cond.hakkoDay = '19010102'
            compile('エリア','3','areaCd','areaCd','cond.areaCd','true',null,'2','false','MA','AREA_CD','false',
                    'cond.hakkoDay');
            $httpBackend.flush();
            selectElement = angular.element(element.children()[1]);
            var children = selectElement.children();
            //-----------It should has 4 <option/>
            expect(children.length).toEqual(2);
            expect(angular.element(children[0]).attr('value')).toEqual('');
            expect(angular.element(children[0]).text()).toEqual('');
            expect(angular.element(children[1]).attr('value')).toEqual('0000001');
            expect(angular.element(children[1]).text()).toEqual('0000001:メーカー名称（漢字）1');
            
            // hakkoDay = 20140102
            scope.cond.hakkoDay = '20140102'
            compile('エリア','3','areaCd','areaCd','cond.areaCd','true',null,'2','false','MA','AREA_CD','false',
                    'cond.hakkoDay');
            $httpBackend.flush();
            selectElement = angular.element(element.children()[1]);
            var children = selectElement.children();
            //-----------It should has 4 <option/>
            expect(children.length).toEqual(0);
        });
        it('it should be catch ccSelect and required validation ', function() {
            compile('エリア','3','areaCd','areaCd','cond.areaCd','true',null,'2','false','MA','AREA_CD','false',
                    'cond.hakkoDay');
            $httpBackend.flush();
            selectElement = angular.element(element.children()[1]);
            var modelCtrl = selectElement.controller('ngModel');
            // value of scope.cond.areaCd out of listRecord
            scope.cond.areaCd = '0000000';
//            scope.$digest();
            expect(modelCtrl.$invalid).toBe(true);
            expect(modelCtrl.$error.required).toBe(true);
//            expect(modelCtrl.$error.ccSelect).toBe(true);
        });
    });
});
