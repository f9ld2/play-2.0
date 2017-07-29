'use strict';

/* jasmine specs for directives go here */

describe('Test directive', function() {
    var element, scope, $compile, $httpBackend, changeSelectValue, compile;
    var templateHtml, selectElement, divElement, spanElement,valueTextBeChoosen, textChoosen;
    beforeEach(module('directives'));
    beforeEach(inject(function($injector, $sniffer) {
        // inject $compile & $roorScope
        $compile = $injector.get('$compile');
        scope = $injector.get('$rootScope');
        $httpBackend = $injector.get('$httpBackend');

        scope.stdCdList1 = [{code:'0000001',name:'メーカー名称（漢字）1'}];
        scope.stdCdList2 = [{code:'0000001',name:'メーカー名称（漢字）1'},{code:'0000002',name:'メーカー名称（漢字）2'}];
        scope.stdCdList3 = [{code:'0000001',name:'メーカー名称（漢字）1'},{code:'0000002',name:'メーカー名称（漢字）2'},{code:'0000003',name:'メーカー名称（漢字）3'}];
        // emulates clicking to select option
        changeSelectValue = function(elm, index) {
            angular.element(elm.children()[index]).attr('selected',true);
            browserTrigger(selectElement, 'change');
        };
        
        valueTextBeChoosen = function(elm){
            var log = [];
            angular.forEach(elm, function(value, key) {
                if(angular.element(value).attr('selected') == 'selected'){
                    textChoosen = value.value;
                    return;
                }
            }, log);
        }
        
        scope.cond = {
            stdCd:''
        }
        
        // compile directive need to test: label, partition, id,name, ngModel, required, readOnly,
        //                                  maxLength, shortName, dataList
        compile = function(label, partition, id,name, ngModel, required, readOnly,
                maxLength, shortName, dataList) {
            var htmlText = '';
            htmlText += '<cc-combobox';
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
            htmlText += ' ng-maxlength="' + maxLength + '"';
            htmlText += ' cc-shortname="' + shortName + '"';
            htmlText += ' cc-datalist="' + dataList + '"';
            htmlText += '" />';

            element = $compile(htmlText)(scope);
            templateHtml = angular.element('<div>').append(angular.element(element).clone()).html();
        };
    }));
    describe('cc-combobox', function() {

        // test template
        it('it should render right html', function() {

            // Test partition != '', required = true, ng-maxlength="2" , cc-key1="X001"
            compile('棚卸月','3','stdCd','stdCd','cond.stdCd','true', null,'2','false','stdCdList1');
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
            expect(selectElement.attr('id')).toEqual("stdCd");
            expect(selectElement.attr('name')).toEqual("stdCd");
            
//            expect(selectElement.hasClass('cs-char-25')).toBe(true);
            expect(selectElement.hasClass('cs-char-15')).toBe(false);
            expect(selectElement.hasClass('cs-left')).toBe(true);
            
            // Test partition == ''
            compile('棚卸月','','stdCd','stdCd','cond.stdCd','true',null,'2','false','stdCdList1');
            divElement = angular.element(element.children()[0]);
            expect(divElement.children().length).toEqual(0);
            expect(divElement.hasClass('cs-label')).toBe(true);
            expect(divElement.hasClass('cs-label-divide-zero')).toBe(true);
            expect(divElement.hasClass('cs-label-large-required')).toBe(false);
            expect(divElement.hasClass('cs-label-large')).toBe(false);
            
            // Test required == 'false'
            compile('棚卸月','3','stdCd','stdCd','cond.stdCd','false',null,'2','false','stdCdList1');
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
            compile('棚卸月','','stdCd','stdCd','cond.stdCd',null,'true','2','false','stdCdList1');
            //-----------it should hasn't span element
            expect(element.has('div').length).toEqual(1);
            expect(element.has('span').length).toEqual(0);
            expect(element.has('select').length).toEqual(1);
            var children = element.children();
            expect(children.length).toEqual(2);
            
            //-----------div should hasn't span element
            divElement = angular.element(children[0]);
            var divChildren = divElement.children();
//            expect(divElement.has('span').length).toEqual(0);
            expect(divChildren.length).toEqual(0);

            //-----------test selectElement
            selectElement = angular.element(children[1]);
            expect(selectElement.attr('required')).toEqual(undefined);
            expect(selectElement.attr('disabled')).toEqual('disabled');
            expect(selectElement.attr('ui-select2')).toEqual("{allowClear: false}");
            expect(selectElement.attr('cc-blur-validation')).toEqual(undefined);
            
            // Test shortName = true
            compile('棚卸月','3','stdCd','stdCd','cond.stdCd','true',null,'2','false','stdCdList1');
            selectElement = angular.element(element.children()[1]);
            expect(selectElement.hasClass('cs-char-25')).toBe(false);
            expect(selectElement.hasClass('cs-char-15')).toBe(false);
            expect(selectElement.hasClass('cs-left')).toBe(true);
        });

        // test logic
        it('it should be right binding value and view value', function() {
            // shortName = false
            compile('棚卸月','3','stdCd','stdCd','cond.stdCd','false',null,'3','false','stdCdList1');
//            $httpBackend.flush();
            selectElement = angular.element(element.children()[1]);
            var children = selectElement.children();
            //-----------It should has 2 <option/>
            expect(children.length).toEqual(2);
            expect(angular.element(children[0]).attr('value')).toEqual('');
            expect(angular.element(children[0]).text()).toEqual('');
            expect(angular.element(children[1]).attr('value')).toEqual('0000001');
            expect(angular.element(children[1]).text()).toEqual('メーカー名称（漢字）1');
            //-----------first option should is selected after initialize
            expect(angular.element(children[0]).prop('selected')).toBeTruthy();
            //-----------binding value should is empty after initialize
            expect(scope.cond.stdCd).toEqual('');
            //-----------select second option
            changeSelectValue(selectElement, 1);
            //-----------binding value should is 0000001 after selecting
            expect(scope.cond.stdCd).toEqual('0000001');
            //-----------view value should is '0000001:メーカー名称（漢字）1' after selecting
            valueTextBeChoosen(selectElement.children());
            expect(textChoosen).toEqual('0000001');
            
            // shortName = true
            compile('棚卸月','3','stdCd','stdCd','cond.stdCd','true',null,'3','true','stdCdList1');
            selectElement = angular.element(element.children()[1]);
            //-----------select second option
            changeSelectValue(selectElement, 1);
            //-----------binding value should is 0000001 after selecting
            expect(scope.cond.stdCd).toEqual('0000001');
            //-----------view value should is '0000001:メーカー名略称1' after selecting
            valueTextBeChoosen(selectElement.children());
            expect(textChoosen).toEqual('0000001');
            

            compile('棚卸月','2','stdCd','stdCd','cond.stdCd',null,'true','2','false','stdCdList2');
            //-----------it should hasn't span element
            selectElement = angular.element(element.children()[1]);
            children = selectElement.children();
            expect(children.length).toEqual(3);
            expect(angular.element(children[0]).attr('value')).toEqual('');
            expect(angular.element(children[0]).text()).toEqual('');
            expect(angular.element(children[1]).attr('value')).toEqual('0000001');
            expect(angular.element(children[1]).text()).toEqual('メーカー名称（漢字）1');
            expect(angular.element(children[2]).attr('value')).toEqual('0000002');
            expect(angular.element(children[2]).text()).toEqual('メーカー名称（漢字）2');
            
            compile('棚卸月','','stdCd','stdCd','cond.stdCd',null,'true','2','false','stdCdList3');
            //-----------it should hasn't span element
            selectElement = angular.element(element.children()[1]);
            children = selectElement.children();
            expect(children.length).toEqual(4);
            expect(angular.element(children[0]).attr('value')).toEqual('');
            expect(angular.element(children[0]).text()).toEqual('');
            expect(angular.element(children[1]).attr('value')).toEqual('0000001');
            expect(angular.element(children[1]).text()).toEqual('メーカー名称（漢字）1');
            expect(angular.element(children[2]).attr('value')).toEqual('0000002');
            expect(angular.element(children[2]).text()).toEqual('メーカー名称（漢字）2');
            expect(angular.element(children[3]).attr('value')).toEqual('0000003');
            expect(angular.element(children[3]).text()).toEqual('メーカー名称（漢字）3');
        });
        it('it should be catch ccSelect and required validation ', function() {
            compile('棚卸月','3','stdCd','stdCd','cond.stdCd','true',null,'3','false','stdCdList3');
            selectElement = angular.element(element.children()[1]);
            var modelCtrl = selectElement.controller('ngModel');
            // value of scope.cond.stdCd out of listRecord
            scope.cond.stdCd = '0000001123';

//            expect(modelCtrl.$invalid).toBe(true);
//            expect(modelCtrl.$error.required).toBe(true);
//            expect(modelCtrl.$error.ccSelect).toBe(true);
        });
    });
});
