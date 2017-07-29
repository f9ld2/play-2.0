'use strict';

/* jasmine specs for directives go here */

describe('Test directive', function() {
    var element, scope, $compile, $httpBackend, changeSelectValue, compile;
    var templateHtml, selectElement, divElement, spanElement;
    var list1, list2, empty;
    beforeEach(module('directives'));
    beforeEach(inject(function($injector, $sniffer) {
        // inject $compile & $roorScope
        $compile = $injector.get('$compile');
        scope = $injector.get('$rootScope');
        $httpBackend = $injector.get('$httpBackend');
        list1 = [{code:'0001',name:'リカー（ＳＭ）1',shortName:'リカー1',kubun:'1'},
                 {code:'0009',name:'リカー（ＳＭ）9',shortName:'リカー9',kubun:'9'}];
        list2 = [{code:'1001',name:'催事（ＳＭ）1',shortName:'催事1',kubun:'1'},
                 {code:'1002',name:'催事（ＳＭ）2',shortName:'催事2',kubun:'2'},
                 {code:'1003',name:'催事（ＳＭ）3',shortName:'催事3',kubun:'3'}];
        empty = [];

        $httpBackend.when('GET', '/core/codename/m002dbum2?hakkoDay=19010101').respond(list1);
        $httpBackend.when('GET', '/core/codename/m002dbum2?hakkoDay=20140101').respond(list2);

        // emulates clicking to select option
        changeSelectValue = function(elm, index) {
            angular.element(elm.children()[index]).attr('selected','selected');
            browserTrigger(selectElement, 'change');
        };

        scope.cond = {
            dbuCd : '',
            hakkoDay : '',
        }
        // compile directive need to test: label, partition, id,name, ngModel, required, readOnly,
        //                                  delexist, shortName, para01 
        compile = function(label, partition, id,name, ngModel, required, readOnly,
                 delexist, shortName, para01) {
            var htmlText = '';
            htmlText += '<cc-dbu-combobox';
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
            htmlText += ' cc-parameter01="' + para01 + '" />';

            element = $compile(htmlText)(scope);
            scope.$digest();
            templateHtml = angular.element('<div>').append(angular.element(element).clone()).html();
        };
    }));
    describe('cc-dbu-combobox', function() {

        // test template
        it('it should render right html', function() {

            // Test partition != '', required = true, shortName = false, delexist = false
            compile('大部門','3','dbuCd','dbuCd','cond.dbuCd','true',null,'false','false', 'cond.hakkoDay');
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
            expect(selectElement.attr('id')).toEqual("dbuCd");
            expect(selectElement.attr('name')).toEqual("dbuCd");
            console.log(selectElement);
            expect(selectElement.hasClass('cs-char-25')).toBe(false);
            expect(selectElement.hasClass('cs-char-15')).toBe(true);
            expect(selectElement.hasClass('cs-left')).toBe(true);
            
            // Test partition == ''
            compile('大部門','','dbuCd','dbuCd','cond.dbuCd','true',null,'false','false', 'cond.hakkoDay');
            divElement = angular.element(element.children()[0]);
            expect(divElement.children().length).toEqual(0);
            expect(divElement.hasClass('cs-label')).toBe(true);
            expect(divElement.hasClass('cs-label-divide-zero')).toBe(true);
            expect(divElement.hasClass('cs-label-large-required')).toBe(false);
            expect(divElement.hasClass('cs-label-large')).toBe(false);
            
            // Test required == 'false'
            compile('大部門','3','dbuCd','dbuCd','cond.dbuCd','false',null,'false','false', 'cond.hakkoDay');
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
            compile('大部門','','dbuCd','dbuCd','cond.dbuCd',null,'true','false','false', 'cond.hakkoDay');
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
            expect(selectElement.attr('ui-select2')).toEqual("{ allowClear: false}");
            expect(selectElement.attr('cc-blur-validation')).toEqual(undefined);
            
            // Test shortName = true
            compile('大部門','3','dbuCd','dbuCd','cond.dbuCd','true',null,'false','true', 'cond.hakkoDay');
            selectElement = angular.element(element.children()[1]);
            expect(selectElement.hasClass('cs-char-25')).toBe(false);
            expect(selectElement.hasClass('cs-char-15')).toBe(true);
            expect(selectElement.hasClass('cs-left')).toBe(true);
        });

        // test logic
        it('it should be right binding value and view value', function() {
            // shortName = false, delexist = false, default hakkoDay
            compile('大部門','3','dbuCd','dbuCd','cond.dbuCd','false',null,'false','false', 'cond.hakkoDay');
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
            expect(scope.cond.dbuCd).toEqual('');
            //-----------view value should is empty after initialize
//            expect(selectElement.text()).toEqual('');
            //-----------select second option
            changeSelectValue(selectElement, 1);
            //-----------binding value should is 0000001 after selecting
            expect(scope.cond.dbuCd).toEqual(list1[0].code);
            //-----------view value should is list1[0].code + ':' + list1[0].name after selecting
            expect(selectElement.text()).toEqual(list1[0].code + ':' + list1[0].name);
            
            // shortName = true
            compile('大部門','3','dbuCd','dbuCd','cond.dbuCd','true',null,'false','true', 'cond.hakkoDay');
            $httpBackend.flush();
            selectElement = angular.element(element.children()[1]);
            //-----------select second option
            changeSelectValue(selectElement, 1);
            //-----------binding value should is 0000001 after selecting
            expect(scope.cond.dbuCd).toEqual(list1[0].code);
            //-----------view value should is '0000001:メーカー名略称1' after selecting
            expect(selectElement.text()).toEqual(list1[0].code + ':' + list1[0].shortName);
            
            // delexist = false
            compile('大部門','3','dbuCd','dbuCd','cond.dbuCd','true',null,'true','false', 'cond.hakkoDay');
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
            compile('大部門','3','dbuCd','dbuCd','cond.dbuCd','true',null,'false','false', 'cond.hakkoDay');
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
            compile('大部門','3','dbuCd','dbuCd','cond.dbuCd','true',null,'false','false', 'cond.hakkoDay');
            $httpBackend.flush();
            selectElement = angular.element(element.children()[1]);
            var modelCtrl = selectElement.controller('ngModel');
            // value of scope.cond.dbuCd out of listRecord
            scope.cond.dbuCd = '0000000';
            //scope.$digest();
            expect(modelCtrl.$invalid).toBe(true);
            expect(modelCtrl.$error.required).toBe(true);
            //expect(modelCtrl.$error.ccSelect).toBe(true);
        });
    });
});
