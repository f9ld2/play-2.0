'use strict';

/* jasmine specs for directives go here */

describe('Test directive cc-ten-combobox', function() {
    var element, scope, $compile, $httpBackend, changeSelectValue, compile;
    var templateHtml, selectElement, divElement, spanElement;
    var list1, list2, empty;
    beforeEach(module('directives'));
    beforeEach(inject(function($injector, $sniffer) {
        // inject $compile & $roorScope
        $compile = $injector.get('$compile');
        scope = $injector.get('$rootScope');
        $httpBackend = $injector.get('$httpBackend');
        list1 = [{code:'001',name:'○ァ○リ○ス○ア○部1',shortName:'○部1',kubun:'1'},
                 {code:'009',name:'○ァ○リ○ス○ア○部9',shortName:'○部9',kubun:'9'}];
        list2 = [{code:'101',name:'○ァ○リ○ス○ア○六1',shortName:'○六1',kubun:'1'},
                 {code:'102',name:'○ァ○リ○ス○ア○六2',shortName:'○六2',kubun:'2'},
                 {code:'103',name:'○ァ○リ○ス○ア○六3',shortName:'○六3',kubun:'3'}];
        empty = [];

        $httpBackend.when('GET', '/core/codename/m006tenm2/00/01?hakkoDay=19010101').respond(list1);
        $httpBackend.when('GET', '/core/codename/m006tenm2/00/01?hakkoDay=20140101').respond(list2);

        $httpBackend.when('GET', '/core/codemaster/getUnyoDate').respond("19010101");
        
        // emulates clicking to select option
        changeSelectValue = function(elm, index) {
            angular.element(elm.children()[index]).attr('selected','selected');
            browserTrigger(selectElement, 'change');
        };

        scope.cond = {
            tenCd : '',
            kaisyaCd: '',
            igyobuCd: '',
            hakkoDay : '',
        }
        // compile directive need to test: label, partition, id,name, ngModel, required, readOnly,
        //                                  delexist, shortName, para01 
        compile = function(label, partition, id,name, ngModel, required, readOnly,
                 delexist, shortName, para01, para02, para03) {
            var htmlText = '';
            htmlText += '<cc-ten-combobox';
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
            htmlText += ' cc-delexist="' + delexist + '"';
            htmlText += ' cc-shortname="' + shortName + '"';
            htmlText += ' cc-parameter01="' + para01 + '"';
            htmlText += ' cc-parameter02="' + para02 + '"';
            htmlText += ' cc-parameter03="' + para03 + '" />';

            element = $compile(htmlText)(scope);
            scope.$digest();
            templateHtml = angular.element('<div>').append(angular.element(element).clone()).html();
        };
    }));
    describe('cc-ten-combobox', function() {
        // test template
        it('it should render right html', function() {
            // Test partition != '', required = true, shortName = false, delexist = false
            compile('店舗','3','tenCd','tenCd','cond.tenCd','true',null,'false','false', 
                    'cond.kaisyaCd', 'cond.igyobuCd', 'cond.hakkoDay');
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
            expect(selectElement.attr('id')).toEqual("tenCd");
            expect(selectElement.attr('name')).toEqual("tenCd");

            expect(selectElement.hasClass('cs-char-25')).toBe(false);
            expect(selectElement.hasClass('cs-char-15')).toBe(true);
            expect(selectElement.hasClass('cs-left')).toBe(true);

            // Test partition == ''
            compile('店舗','','tenCd','tenCd','cond.tenCd','true',null,'false','false', 
                    'cond.kaisyaCd', 'cond.igyobuCd', 'cond.hakkoDay');
            divElement = angular.element(element.children()[0]);
            expect(divElement.children().length).toEqual(0);
            expect(divElement.hasClass('cs-label')).toBe(true);
            expect(divElement.hasClass('cs-label-divide-zero')).toBe(true);
            expect(divElement.hasClass('cs-label-large-required')).toBe(false);
            expect(divElement.hasClass('cs-label-large')).toBe(false);

            // Test required == 'false'
            compile('店舗','3','tenCd','tenCd','cond.tenCd','false',null,'false','false', 
                    'cond.kaisyaCd', 'cond.igyobuCd', 'cond.hakkoDay');
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
            compile('店舗','','tenCd','tenCd','cond.tenCd',null,'true','false','false', 
                    'cond.kaisyaCd', 'cond.igyobuCd', 'cond.hakkoDay');
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
            expect(selectElement.attr('ui-select2')).toEqual("{allowClear: false}");
            expect(selectElement.attr('cc-blur-validation')).toEqual(undefined);

            // Test shortName = true
            compile('店舗','3','tenCd','tenCd','cond.tenCd','true',null,'false','true', 
                    'cond.kaisyaCd', 'cond.igyobuCd', 'cond.hakkoDay');
            selectElement = angular.element(element.children()[1]);
            expect(selectElement.hasClass('cs-char-25')).toBe(false);
            expect(selectElement.hasClass('cs-char-15')).toBe(true);
            expect(selectElement.hasClass('cs-left')).toBe(true);
        });

        // test logic
        it('it should be right binding value and view value', function() {
            // shortName = false, delexist = false, default hakkoDay
            compile('店舗','3','tenCd','tenCd','cond.tenCd','false',null,'false','false', 
                    'cond.kaisyaCd', 'cond.igyobuCd', 'cond.hakkoDay');
            scope.cond.kaisyaCd = '00';
            scope.cond.igyobuCd = '01';

            $httpBackend.flush();
            selectElement = angular.element(element.children()[1]);
            var children = selectElement.children();
            //-----------It should has 2 <option/>
            expect(children.length).toEqual(2);
            expect(angular.element(children[0]).attr('value')).toEqual('');
            expect(angular.element(children[0]).text()).toEqual('');
            expect(angular.element(children[1]).attr('value')).toEqual(list1[0].code);
            expect(angular.element(children[1]).attr('value')).toEqual(list1[0].code);
            expect(angular.element(children[1]).text()).toEqual(list1[0].code + ':' + list1[0].name);
            //-----------first option should is selected after initialize
            expect(angular.element(children[0]).prop('selected')).toBeTruthy();
            //-----------binding value should is empty after initialize
            expect(scope.cond.tenCd).toEqual('');
            //-----------view value should is empty after initialize
//            expect(selectElement.text()).toEqual('');
            //-----------select second option
            changeSelectValue(selectElement, 1);
            //-----------binding value should is 0000001 after selecting
            expect(scope.cond.tenCd).toEqual(list1[0].code);
            //-----------view value should is list1[0].code + ':' + list1[0].name after selecting
            expect(selectElement.text()).toEqual(list1[0].code + ':' + list1[0].name);

            // shortName = true
            compile('店舗','3','tenCd','tenCd','cond.tenCd','true',null,'false','true', 
                    'cond.kaisyaCd', 'cond.igyobuCd', 'cond.hakkoDay');
            $httpBackend.flush();
            selectElement = angular.element(element.children()[1]);
            //-----------select second option
            changeSelectValue(selectElement, 1);
            //-----------binding value should is 0000001 after selecting
            expect(scope.cond.tenCd).toEqual(list1[0].code);
            //-----------view value should is '0000001:メーカー名略称1' after selecting
            expect(selectElement.text()).toEqual(list1[0].code + ':' + list1[0].shortName);

            // delexist = false
            compile('店舗','3','tenCd','tenCd','cond.tenCd','true',null,'true','false', 
                    'cond.kaisyaCd', 'cond.igyobuCd', 'cond.hakkoDay');
            $httpBackend.flush();
            selectElement = angular.element(element.children()[1]);
            var children = selectElement.children();
            //-----------It should has 3 <option/>
            expect(children.length).toEqual(3);
            expect(angular.element(children[0]).attr('value')).toEqual('');
            expect(angular.element(children[0]).text()).toEqual('');
            expect(angular.element(children[1]).attr('value')).toEqual(list1[0].code);
            expect(angular.element(children[1]).text()).toEqual(list1[0].code + ':' + list1[0].name);
            expect(angular.element(children[2]).attr('value')).toEqual(list1[1].code);
            expect(angular.element(children[2]).text()).toEqual(list1[1].code + ':' + list1[1].name);

            // hakkoDay = 20140101
            scope.cond.hakkoDay = '20140101'
            compile('店舗','3','tenCd','tenCd','cond.tenCd','true',null,'false','false', 
                    'cond.kaisyaCd', 'cond.igyobuCd', 'cond.hakkoDay');
            $httpBackend.flush();
            selectElement = angular.element(element.children()[1]);
            var children = selectElement.children();
            //-----------It should has 4 <option/>
            expect(children.length).toEqual(4);
            expect(angular.element(children[0]).attr('value')).toEqual('');
            expect(angular.element(children[0]).text()).toEqual('');
            expect(angular.element(children[1]).attr('value')).toEqual(list2[0].code);
            expect(angular.element(children[1]).text()).toEqual(list2[0].code + ':' + list2[0].name);
            expect(angular.element(children[2]).attr('value')).toEqual(list2[1].code);
            expect(angular.element(children[2]).text()).toEqual(list2[1].code + ':' + list2[1].name);
            expect(angular.element(children[3]).attr('value')).toEqual(list2[2].code);
            expect(angular.element(children[3]).text()).toEqual(list2[2].code + ':' + list2[2].name);
        });
        it('it should be catch ccSelect and required validation ', function() {
            compile('店舗','3','tenCd','tenCd','cond.tenCd','true',null,'false','false', 
                    'cond.kaisyaCd', 'cond.igyobuCd', 'cond.hakkoDay');

            scope.cond.hakkoDay = '20140101';
            scope.cond.kaisyaCd = '00';
            scope.cond.igyobuCd = '01';

            $httpBackend.flush();
            selectElement = angular.element(element.children()[1]);
            var modelCtrl = selectElement.controller('ngModel');
            // value of scope.cond.tenCd out of listRecord
            scope.cond.tenCd = '000';
            //scope.$digest();
            expect(modelCtrl.$invalid).toBe(true);
            expect(modelCtrl.$error.required).toBe(true);
            //expect(modelCtrl.$error.ccSelect).toBe(true);
        });
    });
});
