'use strict';

/* jasmine specs for directives go here */

describe('Test directive', function() {
    var element, scope, $compile, $httpBackend, changeInputValue, compile;
    var templateHtml, inputElement0, inputElement1, divElement, spanElement;
    var list1, list2;
    beforeEach(module('directives'));
    beforeEach(inject(function($injector, $sniffer) {
        // inject $compile & $roorScope
        $compile = $injector.get('$compile');
        scope = $injector.get('$rootScope');
        $httpBackend = $injector.get('$httpBackend');
        $httpBackend.when('GET', '/core/codemaster/getUnyoDate').respond('20140206');
        list1 = [{name:'概要設計書（漢字）',shortName:'概要計書１',kubun:'1'},
                 {name:'概要設計書（漢字）',shortName:'概要計書１',kubun:'2'},
                 {name:'概要設計書（漢字）',shortName:'概要計書１',kubun:'3'}];
        list2 = [{name:'概要設計書（漢字）',shortName:'概要計書１',kubun:'9'}];

        // default hakkoDay
        $httpBackend.when('GET', /\/core\/codemaster\/m007kijm\/ (\d{13})\?hakkoDay=20140206/).respond(list1);
        
        $httpBackend.when('GET', /\/core\/codemaster\/m007kijm\/ (\d{13})\?hakkoDay=201400101/).respond(list2);
        $httpBackend.when('GET', /\/core\/codemaster\/m007kijm\/ (\d{13})\?hakkoDay=20140201/).respond([]);

        // call browser change input value
        changeInputValue = function(elm, value) {
            elm.val(value);
            browserTrigger(elm, $sniffer.hasEvent('input') ? 'input' : 'change');
        };

        // compile directive need to test.
        compile = function(label, partition, id, name, ngModel, required, readOnly,
                notExist, delexist, shortName, para01, id2, name2, ngModel2) {
            var htmlText = '';
            htmlText += '<cc-jancd-input';
            htmlText += ' cc-label="' + label + '"';
            htmlText += ' cc-partition="' + partition + '"';
            htmlText += ' id="' + id + '"';
            htmlText += ' name="' + name + '"';
            htmlText += ' ng-model="' + ngModel + '"';
            if (required != null) {
                htmlText += ' cc-required='+ required;
            }
            if (readOnly != null && readOnly== 'true') {
                htmlText += ' cc-readonly='+ readOnly ;
            }
            if (notExist!= null) {
                htmlText += ' cc-notexist="' + notExist + '"';
            }
            if (delexist!= null) {
                htmlText += ' cc-delexist="' + delexist + '"';
            }
            if (shortName!= null) {
                htmlText += ' cc-shortname="' + shortName + '"';
            }
            if (para01 != null) {
                htmlText += ' cc-parameter01="' + para01 + '"';
            }
            if (id2 != null) {
                htmlText += ' id2="' + id2 + '"';
            }
            if (name2 != null) {
                htmlText += ' name2="' + name2 + '"';
            }
            if (ngModel2 != null) {
                htmlText += ' ng-model2="' + ngModel2 + '"';
            }
            htmlText += ' />';
            
            element = $compile(htmlText)(scope);
            scope.$digest();
            templateHtml = element.html();
        };
        scope.cond = {
        }
    }));
    describe('cc-jancd-input', function() {
        // test template
        it('it should render right html', function() {
            // Test partition != '', required = true, shortName = false, ngModel2 != ''
            compile('ＪＡＮ','3','janCd','janCd','cond.janCd','true',null,'true','true','false', 'cond.hakkoDay',
                    'shnNm', 'shnNm', 'cond.shnNm');
            //-----------it should has only div, span and 2 input element
            expect(element.find('div').length).toEqual(1);
            expect(element.find('span').length).toEqual(1);
            expect(element.find('input').length).toEqual(2);
            var children = element.children();
            expect(children.length).toEqual(3);
            
            //-----------div should has only span element
            divElement = angular.element(children[0]);
            var divChildren = divElement.children();
            expect(divElement.find('span').length).toEqual(1);
            expect(divElement.text()).toEqual('ＪＡＮ *');
            expect(divChildren.length).toEqual(1);
            expect(divElement.hasClass('cs-label')).toBe(true);
            expect(divElement.hasClass('cs-label-large-required')).toBe(true);

            //-----------text of spanElement must be ' *'
            spanElement = angular.element(divChildren[0]);
            expect(spanElement.text()).toEqual(' *');
            expect(spanElement.hasClass('cs-txt-required')).toBe(true);

            //-----------test inputElement0
            inputElement0 = angular.element(children[1]);
            expect(inputElement0.attr('cc-blur-validation')).toEqual("");
            expect(inputElement0.attr('ui-event')).toEqual("{ blur : 'blurCallback(ngModel)' }");
            expect(inputElement0.attr('required')).toEqual("required");
            expect(inputElement0.attr('readonly')).toEqual(undefined);
            expect(inputElement0.attr('cc-type')).toEqual('code');
            expect(inputElement0.attr('ng-model')).toEqual("ngModel");
            expect(inputElement0.attr('id')).toEqual("janCd");
            expect(inputElement0.attr('name')).toEqual("janCd");
            expect(inputElement0.hasClass('cs-num-15')).toBe(true);
            expect(inputElement0.hasClass('cs-left')).toBe(true);

            //-----------test inputElement1
            inputElement1 = angular.element(children[2]);
            expect(inputElement1.attr('required')).toEqual(undefined);
            expect(inputElement1.attr('readonly')).toEqual('readonly');
            expect(inputElement1.attr('ng-model')).toEqual("ngModel2");
            expect(inputElement1.attr('id')).toEqual("shnNm");
            expect(inputElement1.attr('name')).toEqual("shnNm");
            expect(inputElement1.attr('cc-type')).toEqual('zenkaku');
            expect(inputElement1.hasClass('cs-char-20')).toBe(true);
            expect(inputElement1.hasClass('cs-left')).toBe(true);
            expect(inputElement1.hasClass('cs-disable')).toBe(true);

            // Test partition == ''
            compile('','','janCd','janCd','cond.janCd','true',null,'true','true','false', 'cond.hakkoDay',
                    'shnNm', 'shnNm', 'cond.shnNm');
            divElement = angular.element(element.children()[0]);
            expect(divElement.text()).toEqual('');
            expect(divElement.children().length).toEqual(0);
            expect(divElement.hasClass('cs-label')).toBe(true);
            expect(divElement.hasClass('cs-label-divide-zero')).toBe(true);
            expect(divElement.hasClass('cs-label-large-required')).toBe(false);
            expect(divElement.hasClass('cs-label-large')).toBe(false);

            // Test required == 'false'
            compile('ＪＡＮ','3','janCd','janCd','cond.janCd','false',null,'true','true','false', 'cond.hakkoDay',
                    'shnNm', 'shnNm', 'cond.shnNm');
            //-----------it should hasn't span element
            expect(element.find('div').length).toEqual(1);
            expect(element.find('span').length).toEqual(0);
            expect(element.find('input').length).toEqual(2);
            var children = element.children();
            expect(children.length).toEqual(3);

            //-----------div should hasn't span element
            divElement = angular.element(children[0]);
            var divChildren = divElement.children();
            expect(divElement.text()).toEqual('ＪＡＮ');
            expect(divElement.find('span').length).toEqual(0);
            expect(divChildren.length).toEqual(0);
            expect(divElement.hasClass('cs-label-large')).toBe(true);
            expect(divElement.hasClass('cs-label')).toBe(true);
            expect(divElement.hasClass('cs-label-large-required')).toBe(false);
            expect(divElement.hasClass('cs-label-divide-zero')).toBe(false);

            //-----------test inputElement0
            inputElement0 = angular.element(children[1]);
            expect(inputElement0.attr('cc-blur-validation')).toEqual("");
            expect(inputElement0.attr('ui-event')).toEqual("{ blur : 'blurCallback(ngModel)' }");
            expect(inputElement0.attr('required')).toEqual(undefined);
            expect(inputElement0.attr('readonly')).toEqual(undefined);

            // Test readonly = true
            compile('ＪＡＮ','3','janCd','janCd','cond.janCd',null,'true','true','true','false', 'cond.hakkoDay',
                    'shnNm', 'shnNm', 'cond.shnNm');
            //-----------it should hasn't span element
            expect(element.find('div').length).toEqual(1);
            expect(element.find('span').length).toEqual(0);
            expect(element.find('input').length).toEqual(2);
            var children = element.children();
            expect(children.length).toEqual(3);

            //-----------div should hasn't span element
            divElement = angular.element(children[0]);
            var divChildren = divElement.children();
            expect(divElement.text()).toEqual('ＪＡＮ');
            expect(divElement.find('span').length).toEqual(0);
            expect(divChildren.length).toEqual(0);

            //-----------test inputElement0
            inputElement0 = angular.element(children[1]);
            expect(inputElement0.attr('required')).toEqual(undefined);
            expect(inputElement0.attr('readonly')).toEqual('readonly');
            expect(inputElement0.attr('cc-blur-validation')).toEqual(undefined);
            expect(inputElement0.attr('ui-event')).toEqual(undefined);
            expect(inputElement0.hasClass('cs-disable')).toBe(true);

            // Test shortName = true
            compile('ＪＡＮ','3','janCd','janCd','cond.janCd','true',null,'true','true','true', 'cond.hakkoDay',
                    'shnNm', 'shnNm', 'cond.shnNm');
            inputElement1 = angular.element(element.children()[2]);
            expect(inputElement1.hasClass('cs-char-20')).toBe(false);
            expect(inputElement1.hasClass('cs-char-5')).toBe(true);
            expect(inputElement1.hasClass('cs-left')).toBe(true);
            
            // test ngModel2 ='' (element has only one input)
            compile('ＪＡＮ','3','janCd','janCd','cond.janCd','true',null,null,null,null,null,
                    null,null,null);
            //-----------it should has only div, span and input element
            expect(element.find('div').length).toEqual(1);
            expect(element.find('span').length).toEqual(1);
            expect(element.find('input').length).toEqual(1);
            var children = element.children();
            expect(children.length).toEqual(2);

            //-----------test inputElement0
            inputElement0 = angular.element(children[1]);
            expect(inputElement0.attr('cc-blur-validation')).toEqual("");
            expect(inputElement0.attr('ui-event')).toEqual("{ blur : 'blurCallback(ngModel)' }");
            expect(inputElement0.attr('required')).toEqual("required");
            expect(inputElement0.attr('readonly')).toEqual(undefined);
            expect(inputElement0.attr('cc-type')).toEqual('code');
            expect(inputElement0.attr('ng-model')).toEqual("ngModel");
            expect(inputElement0.attr('id')).toEqual("janCd");
            expect(inputElement0.attr('name')).toEqual("janCd");
            expect(inputElement0.hasClass('cs-num-15')).toBe(true);
            expect(inputElement0.hasClass('cs-left')).toBe(true);
        });

        // test logic
        it('it should be right binding value and view value', function() {
            // required = true, shortName = false, default hakkoDay, noExit = false, delexist = false
            compile('ＪＡＮ','3','janCd','janCd','cond.janCd','true',null,'false','false','false', 'cond.hakkoDay',
                    'shnNm', 'shnNm', 'cond.shnNm');
            inputElement0 = angular.element(element.children()[1]);
            inputElement1 = angular.element(element.children()[2]);
            var inputCtrl0 = inputElement0.controller('ngModel');
            expect(inputCtrl0.$invalid).toBe(true);
            expect(inputCtrl0.$error.required).toBe(true);
            
            // JAN-13
            inputElement0.triggerHandler('focus');
            changeInputValue(inputElement0, '4510049400250');
            $httpBackend.flush();
            inputElement0.triggerHandler('blur');
            // bind value and view value for inputElement0
            expect(inputElement0.val()).toEqual('4510049400250');
            expect(scope.cond.janCd).toEqual('4510049400250');
            expect(inputCtrl0.$valid).toBe(true);
            // bind value and view value for inputElement1
            expect(inputElement1.val()).toEqual('概要設計書（漢字）');
            expect(scope.cond.shnNm).toEqual('概要設計書（漢字）');
            
            // ENA-13
            //----------[0,1] :3
            inputElement0.triggerHandler('focus');
            changeInputValue(inputElement0, '3000000000007');
            $httpBackend.flush();
            inputElement0.triggerHandler('blur');
            // bind value and view value for inputElement0
            expect(inputElement0.val()).toEqual('3000000000007');
            expect(scope.cond.janCd).toEqual('3000000000007');
            expect(inputCtrl0.$valid).toBe(true);
            // bind value and view value for inputElement1
            expect(inputElement1.val()).toEqual('概要設計書（漢字）');
            expect(scope.cond.shnNm).toEqual('概要設計書（漢字）');
            
            //----------[0,1] :9
            inputElement0.triggerHandler('focus');
            changeInputValue(inputElement0, '9000000000001');
            $httpBackend.flush();
            inputElement0.triggerHandler('blur');
            // bind value and view value for inputElement0
            expect(inputElement0.val()).toEqual('9000000000001');
            expect(scope.cond.janCd).toEqual('9000000000001');
            expect(inputCtrl0.$valid).toBe(true);
            // bind value and view value for inputElement1
            expect(inputElement1.val()).toEqual('概要設計書（漢字）');
            expect(scope.cond.shnNm).toEqual('概要設計書（漢字）');
            
            // ｲﾝｽﾄｱﾏｰｷﾝｸﾞ(02) digit: 13
            inputElement0.triggerHandler('focus');
            changeInputValue(inputElement0, '0200000000004');
            $httpBackend.flush();
            inputElement0.triggerHandler('blur');
            // bind value and view value for inputElement0
            expect(inputElement0.val()).toEqual('0200000000004');
            expect(scope.cond.janCd).toEqual('0200000000004');
            expect(inputCtrl0.$valid).toBe(true);
            // bind value and view value for inputElement1
            expect(inputElement1.val()).toEqual('概要設計書（漢字）');
            expect(scope.cond.shnNm).toEqual('概要設計書（漢字）');
            
            // ｲﾝｽﾄｱﾏｰｷﾝｸﾞ(2x)
            // -----------[0,1]:20
            inputElement0.triggerHandler('focus');
            changeInputValue(inputElement0, '2000000000008');
            $httpBackend.flush();
            inputElement0.triggerHandler('blur');
            // bind value and view value for inputElement0
            expect(inputElement0.val()).toEqual('2000000000008');
            expect(scope.cond.janCd).toEqual('2000000000008');
            expect(inputCtrl0.$valid).toBe(true);
            // bind value and view value for inputElement1
            expect(inputElement1.val()).toEqual('概要設計書（漢字）');
            expect(scope.cond.shnNm).toEqual('概要設計書（漢字）');
            
            // -----------[0,1]:21
            inputElement0.triggerHandler('focus');
            changeInputValue(inputElement0, '2100000000005');
            $httpBackend.flush();
            inputElement0.triggerHandler('blur');
            // bind value and view value for inputElement0
            expect(inputElement0.val()).toEqual('2100000000005');
            expect(scope.cond.janCd).toEqual('2100000000005');
            expect(inputCtrl0.$valid).toBe(true);
            // bind value and view value for inputElement1
            expect(inputElement1.val()).toEqual('概要設計書（漢字）');
            expect(scope.cond.shnNm).toEqual('概要設計書（漢字）');
            
            // -----------[0,1]:22
            inputElement0.triggerHandler('focus');
            changeInputValue(inputElement0, '2200000000002');
            $httpBackend.flush();
            inputElement0.triggerHandler('blur');
            // bind value and view value for inputElement0
            expect(inputElement0.val()).toEqual('2200000000002');
            expect(scope.cond.janCd).toEqual('2200000000002');
            expect(inputCtrl0.$valid).toBe(true);
            // bind value and view value for inputElement1
            expect(inputElement1.val()).toEqual('概要設計書（漢字）');
            expect(scope.cond.shnNm).toEqual('概要設計書（漢字）');
            
            // -----------[0,1]:23
            inputElement0.triggerHandler('focus');
            changeInputValue(inputElement0, '2300000000009');
            $httpBackend.flush();
            inputElement0.triggerHandler('blur');
            // bind value and view value for inputElement0
            expect(inputElement0.val()).toEqual('2300000000009');
            expect(scope.cond.janCd).toEqual('2300000000009');
            expect(inputCtrl0.$valid).toBe(true);
            // bind value and view value for inputElement1
            expect(inputElement1.val()).toEqual('概要設計書（漢字）');
            expect(scope.cond.shnNm).toEqual('概要設計書（漢字）');
            
            // -----------[0,1]:24
            inputElement0.triggerHandler('focus');
            changeInputValue(inputElement0, '2400000000006');
            $httpBackend.flush();
            inputElement0.triggerHandler('blur');
            // bind value and view value for inputElement0
            expect(inputElement0.val()).toEqual('2400000000006');
            expect(scope.cond.janCd).toEqual('2400000000006');
            expect(inputCtrl0.$valid).toBe(true);
            // bind value and view value for inputElement1
            expect(inputElement1.val()).toEqual('概要設計書（漢字）');
            expect(scope.cond.shnNm).toEqual('概要設計書（漢字）');
            
            // -----------[0,1]:25
            inputElement0.triggerHandler('focus');
            changeInputValue(inputElement0, '2500000000003');
            $httpBackend.flush();
            inputElement0.triggerHandler('blur');
            // bind value and view value for inputElement0
            expect(inputElement0.val()).toEqual('2500000000003');
            expect(scope.cond.janCd).toEqual('2500000000003');
            expect(inputCtrl0.$valid).toBe(true);
            // bind value and view value for inputElement1
            expect(inputElement1.val()).toEqual('概要設計書（漢字）');
            expect(scope.cond.shnNm).toEqual('概要設計書（漢字）');
            
            // -----------[0,1]:26
            inputElement0.triggerHandler('focus');
            changeInputValue(inputElement0, '2600000000000');
            $httpBackend.flush();
            inputElement0.triggerHandler('blur');
            // bind value and view value for inputElement0
            expect(inputElement0.val()).toEqual('2600000000000');
            expect(scope.cond.janCd).toEqual('2600000000000');
            expect(inputCtrl0.$valid).toBe(true);
            // bind value and view value for inputElement1
            expect(inputElement1.val()).toEqual('概要設計書（漢字）');
            expect(scope.cond.shnNm).toEqual('概要設計書（漢字）');
            
            // -----------[0,1]:27
            inputElement0.triggerHandler('focus');
            changeInputValue(inputElement0, '2700000000007');
            $httpBackend.flush();
            inputElement0.triggerHandler('blur');
            // bind value and view value for inputElement0
            expect(inputElement0.val()).toEqual('2700000000007');
            expect(scope.cond.janCd).toEqual('2700000000007');
            expect(inputCtrl0.$valid).toBe(true);
            // bind value and view value for inputElement1
            expect(inputElement1.val()).toEqual('概要設計書（漢字）');
            expect(scope.cond.shnNm).toEqual('概要設計書（漢字）');
            
            // -----------[0,1]:28
            inputElement0.triggerHandler('focus');
            changeInputValue(inputElement0, '2800000000004');
            $httpBackend.flush();
            inputElement0.triggerHandler('blur');
            // bind value and view value for inputElement0
            expect(inputElement0.val()).toEqual('2800000000004');
            expect(scope.cond.janCd).toEqual('2800000000004');
            expect(inputCtrl0.$valid).toBe(true);
            // bind value and view value for inputElement1
            expect(inputElement1.val()).toEqual('概要設計書（漢字）');
            expect(scope.cond.shnNm).toEqual('概要設計書（漢字）');
            
            // -----------[0,1]:29
            inputElement0.triggerHandler('focus');
            changeInputValue(inputElement0, '2900000000001');
            $httpBackend.flush();
            inputElement0.triggerHandler('blur');
            // bind value and view value for inputElement0
            expect(inputElement0.val()).toEqual('2900000000001');
            expect(scope.cond.janCd).toEqual('2900000000001');
            expect(inputCtrl0.$valid).toBe(true);
            // bind value and view value for inputElement1
            expect(inputElement1.val()).toEqual('概要設計書（漢字）');
            expect(scope.cond.shnNm).toEqual('概要設計書（漢字）');
            
            // UPC-A : 
            //------------digit:12
            inputElement0.triggerHandler('focus');
            changeInputValue(inputElement0, '010000000000');
            $httpBackend.flush();
            inputElement0.triggerHandler('blur');
            // bind value and view value for inputElement0
            expect(inputElement0.val()).toEqual('0100000000007');
            expect(scope.cond.janCd).toEqual('0100000000007');
            expect(inputCtrl0.$valid).toBe(true);
            // bind value and view value for inputElement1
            expect(inputElement1.val()).toEqual('概要設計書（漢字）');
            expect(scope.cond.shnNm).toEqual('概要設計書（漢字）');
            
            //------------digit:11
            inputElement0.triggerHandler('focus');
            changeInputValue(inputElement0, '10000000000');
            $httpBackend.flush();
            inputElement0.triggerHandler('blur');
            // bind value and view value for inputElement0
            expect(inputElement0.val()).toEqual('0100000000007');
            expect(scope.cond.janCd).toEqual('0100000000007');
            expect(inputCtrl0.$valid).toBe(true);
            // bind value and view value for inputElement1
            expect(inputElement1.val()).toEqual('概要設計書（漢字）');
            expect(scope.cond.shnNm).toEqual('概要設計書（漢字）');
            
            // JAN-8
            inputElement0.triggerHandler('focus');
            changeInputValue(inputElement0, '49400250');
            $httpBackend.flush();
            inputElement0.triggerHandler('blur');
            // bind value and view value for inputElement0
            expect(inputElement0.val()).toEqual('0000049400250');
            expect(scope.cond.janCd).toEqual('0000049400250');
            expect(inputCtrl0.$valid).toBe(true);
            // bind value and view value for inputElement1
            expect(inputElement1.val()).toEqual('概要設計書（漢字）');
            expect(scope.cond.shnNm).toEqual('概要設計書（漢字）');
            
            // EAN-8
            //----------[0,1] :3
            inputElement0.triggerHandler('focus');
            changeInputValue(inputElement0, '30000001');
            $httpBackend.flush();
            inputElement0.triggerHandler('blur');
            // bind value and view value for inputElement0
            expect(inputElement0.val()).toEqual('0000030000001');
            expect(scope.cond.janCd).toEqual('0000030000001');
            expect(inputCtrl0.$valid).toBe(true);
            // bind value and view value for inputElement1
            expect(inputElement1.val()).toEqual('概要設計書（漢字）');
            expect(scope.cond.shnNm).toEqual('概要設計書（漢字）');
            
            //----------[0,1] :9
            inputElement0.triggerHandler('focus');
            changeInputValue(inputElement0, '90000003');
            $httpBackend.flush();
            inputElement0.triggerHandler('blur');
            // bind value and view value for inputElement0
            expect(inputElement0.val()).toEqual('0000090000003');
            expect(scope.cond.janCd).toEqual('0000090000003');
            expect(inputCtrl0.$valid).toBe(true);
            // bind value and view value for inputElement1
            expect(inputElement1.val()).toEqual('概要設計書（漢字）');
            expect(scope.cond.shnNm).toEqual('概要設計書（漢字）');
            
            // ｲﾝｽﾄｱﾏｰｷﾝｸﾞ(02) digit: 8
            inputElement0.triggerHandler('focus');
            changeInputValue(inputElement0, '02000008');
            $httpBackend.flush();
            inputElement0.triggerHandler('blur');
            // bind value and view value for inputElement0
            expect(inputElement0.val()).toEqual('0000002000008');
            expect(scope.cond.janCd).toEqual('0000002000008');
            expect(inputCtrl0.$valid).toBe(true);
            // bind value and view value for inputElement1
            expect(inputElement1.val()).toEqual('概要設計書（漢字）');
            expect(scope.cond.shnNm).toEqual('概要設計書（漢字）');
            
            // ｲﾝｽﾄｱﾏｰｷﾝｸﾞ(2x) digit: 8
            // -----------[0,1]:20
            inputElement0.triggerHandler('focus');
            changeInputValue(inputElement0, '20000004');
            $httpBackend.flush();
            inputElement0.triggerHandler('blur');
            // bind value and view value for inputElement0
            expect(inputElement0.val()).toEqual('0000020000004');
            expect(scope.cond.janCd).toEqual('0000020000004');
            expect(inputCtrl0.$valid).toBe(true);
            // bind value and view value for inputElement1
            expect(inputElement1.val()).toEqual('概要設計書（漢字）');
            expect(scope.cond.shnNm).toEqual('概要設計書（漢字）');
            
            // -----------[0,1]:21
            inputElement0.triggerHandler('focus');
            changeInputValue(inputElement0, '21000003');
            $httpBackend.flush();
            inputElement0.triggerHandler('blur');
            // bind value and view value for inputElement0
            expect(inputElement0.val()).toEqual('0000021000003');
            expect(scope.cond.janCd).toEqual('0000021000003');
            expect(inputCtrl0.$valid).toBe(true);
            // bind value and view value for inputElement1
            expect(inputElement1.val()).toEqual('概要設計書（漢字）');
            expect(scope.cond.shnNm).toEqual('概要設計書（漢字）');
            
            // -----------[0,1]:22
            inputElement0.triggerHandler('focus');
            changeInputValue(inputElement0, '22000002');
            $httpBackend.flush();
            inputElement0.triggerHandler('blur');
            // bind value and view value for inputElement0
            expect(inputElement0.val()).toEqual('0000022000002');
            expect(scope.cond.janCd).toEqual('0000022000002');
            expect(inputCtrl0.$valid).toBe(true);
            // bind value and view value for inputElement1
            expect(inputElement1.val()).toEqual('概要設計書（漢字）');
            expect(scope.cond.shnNm).toEqual('概要設計書（漢字）');
            
            // -----------[0,1]:23
            inputElement0.triggerHandler('focus');
            changeInputValue(inputElement0, '23000001');
            $httpBackend.flush();
            inputElement0.triggerHandler('blur');
            // bind value and view value for inputElement0
            expect(inputElement0.val()).toEqual('0000023000001');
            expect(scope.cond.janCd).toEqual('0000023000001');
            expect(inputCtrl0.$valid).toBe(true);
            // bind value and view value for inputElement1
            expect(inputElement1.val()).toEqual('概要設計書（漢字）');
            expect(scope.cond.shnNm).toEqual('概要設計書（漢字）');
            
            // -----------[0,1]:24
            inputElement0.triggerHandler('focus');
            changeInputValue(inputElement0, '24000000');
            $httpBackend.flush();
            inputElement0.triggerHandler('blur');
            // bind value and view value for inputElement0
            expect(inputElement0.val()).toEqual('0000024000000');
            expect(scope.cond.janCd).toEqual('0000024000000');
            expect(inputCtrl0.$valid).toBe(true);
            // bind value and view value for inputElement1
            expect(inputElement1.val()).toEqual('概要設計書（漢字）');
            expect(scope.cond.shnNm).toEqual('概要設計書（漢字）');
            
            // -----------[0,1]:25
            inputElement0.triggerHandler('focus');
            changeInputValue(inputElement0, '25000009');
            $httpBackend.flush();
            inputElement0.triggerHandler('blur');
            // bind value and view value for inputElement0
            expect(inputElement0.val()).toEqual('0000025000009');
            expect(scope.cond.janCd).toEqual('0000025000009');
            expect(inputCtrl0.$valid).toBe(true);
            // bind value and view value for inputElement1
            expect(inputElement1.val()).toEqual('概要設計書（漢字）');
            expect(scope.cond.shnNm).toEqual('概要設計書（漢字）');
            
            // -----------[0,1]:26
            inputElement0.triggerHandler('focus');
            changeInputValue(inputElement0, '26000008');
            $httpBackend.flush();
            inputElement0.triggerHandler('blur');
            // bind value and view value for inputElement0
            expect(inputElement0.val()).toEqual('0000026000008');
            expect(scope.cond.janCd).toEqual('0000026000008');
            expect(inputCtrl0.$valid).toBe(true);
            // bind value and view value for inputElement1
            expect(inputElement1.val()).toEqual('概要設計書（漢字）');
            expect(scope.cond.shnNm).toEqual('概要設計書（漢字）');
            
            // -----------[0,1]:27
            inputElement0.triggerHandler('focus');
            changeInputValue(inputElement0, '27000007');
            $httpBackend.flush();
            inputElement0.triggerHandler('blur');
            // bind value and view value for inputElement0
            expect(inputElement0.val()).toEqual('0000027000007');
            expect(scope.cond.janCd).toEqual('0000027000007');
            expect(inputCtrl0.$valid).toBe(true);
            // bind value and view value for inputElement1
            expect(inputElement1.val()).toEqual('概要設計書（漢字）');
            expect(scope.cond.shnNm).toEqual('概要設計書（漢字）');
            
            // -----------[0,1]:28
            inputElement0.triggerHandler('focus');
            changeInputValue(inputElement0, '28000006');
            $httpBackend.flush();
            inputElement0.triggerHandler('blur');
            // bind value and view value for inputElement0
            expect(inputElement0.val()).toEqual('0000028000006');
            expect(scope.cond.janCd).toEqual('0000028000006');
            expect(inputCtrl0.$valid).toBe(true);
            // bind value and view value for inputElement1
            expect(inputElement1.val()).toEqual('概要設計書（漢字）');
            expect(scope.cond.shnNm).toEqual('概要設計書（漢字）');
            
            // -----------[0,1]:29
            inputElement0.triggerHandler('focus');
            changeInputValue(inputElement0, '29000005');
            $httpBackend.flush();
            inputElement0.triggerHandler('blur');
            // bind value and view value for inputElement0
            expect(inputElement0.val()).toEqual('0000029000005');
            expect(scope.cond.janCd).toEqual('0000029000005');
            expect(inputCtrl0.$valid).toBe(true);
            // bind value and view value for inputElement1
            expect(inputElement1.val()).toEqual('概要設計書（漢字）');
            expect(scope.cond.shnNm).toEqual('概要設計書（漢字）');
            
            // UPC-E digit:7
            inputElement0.triggerHandler('focus');
            changeInputValue(inputElement0, '0000017');
            $httpBackend.flush();
            inputElement0.triggerHandler('blur');
            // bind value and view value for inputElement0
            expect(inputElement0.val()).toEqual('0000000000017');
            expect(scope.cond.janCd).toEqual('0000000000017');
            expect(inputCtrl0.$valid).toBe(true);
            // bind value and view value for inputElement1
            expect(inputElement1.val()).toEqual('概要設計書（漢字）');
            expect(scope.cond.shnNm).toEqual('概要設計書（漢字）');
            
            // UPC-E digit:6
            inputElement0.triggerHandler('focus');
            changeInputValue(inputElement0, '000017');
            $httpBackend.flush();
            inputElement0.triggerHandler('blur');
            // bind value and view value for inputElement0
            expect(inputElement0.val()).toEqual('0000000000017');
            expect(scope.cond.janCd).toEqual('0000000000017');
            expect(inputCtrl0.$valid).toBe(true);
            // bind value and view value for inputElement1
            expect(inputElement1.val()).toEqual('概要設計書（漢字）');
            expect(scope.cond.shnNm).toEqual('概要設計書（漢字）');
            
            // cc-shortname = true
            compile('ＪＡＮ','3','janCd','janCd','cond.janCd','true',null,'false','false','true', '',
                    'shnNm', 'shnNm', 'cond.shnNm');
            inputElement0 = angular.element(element.children()[1]);
            inputElement1 = angular.element(element.children()[2]);
            var inputCtrl0 = inputElement0.controller('ngModel');
            inputElement0.triggerHandler('focus');
            changeInputValue(inputElement0, '4510049400250');
            $httpBackend.flush();
            inputElement0.triggerHandler('blur');
            // bind value and view value for inputElement0
            expect(inputElement0.val()).toEqual('4510049400250');
            expect(scope.cond.janCd).toEqual('4510049400250');
            expect(inputCtrl0.$valid).toBe(true);
            // bind value and view value for inputElement1
            expect(inputElement1.val()).toEqual('概要計書１');
            expect(scope.cond.shnNm).toEqual('概要計書１');

            // cc-notexist = true
            //hakkoDay = '20140201' : no data
            scope.cond.hakkoDay = '20140201';
            compile('ＪＡＮ','3','janCd','janCd','cond.janCd','true',null,'true','false','false', 'cond.hakkoDay',
                    'shnNm', 'shnNm', 'cond.shnNm');
            inputElement0 = angular.element(element.children()[1]);
            inputElement1 = angular.element(element.children()[2]);
            var inputCtrl0 = inputElement0.controller('ngModel');
            inputElement0.triggerHandler('focus');
            changeInputValue(inputElement0, '4510049400250');
            $httpBackend.flush();
            inputElement0.triggerHandler('blur');
            // bind value and view value for inputElement0
            expect(inputElement0.val()).toEqual('4510049400250');
            expect(scope.cond.janCd).toEqual('4510049400250');
            expect(inputCtrl0.$valid).toBe(true);
            // bind value and view value for inputElement1
            expect(inputElement1.val()).toEqual('');
            expect(scope.cond.shnNm).toEqual(undefined);
            
            // cc-delexist = true
            //hakkoDay = '201400101' : data has act_kubun = 9
            scope.cond.hakkoDay = '201400101';
            compile('ＪＡＮ','3','janCd','janCd','cond.janCd','true',null,'false','true','false', 'cond.hakkoDay',
                    'shnNm', 'shnNm', 'cond.shnNm');
            inputElement0 = angular.element(element.children()[1]);
            inputElement1 = angular.element(element.children()[2]);
            var inputCtrl0 = inputElement0.controller('ngModel');
            inputElement0.triggerHandler('focus');
            changeInputValue(inputElement0, '4510049400250');
            $httpBackend.flush();
            inputElement0.triggerHandler('blur');
            // bind value and view value for inputElement0
            expect(inputElement0.val()).toEqual('4510049400250');
            expect(scope.cond.janCd).toEqual('4510049400250');
            expect(inputCtrl0.$valid).toBe(true);
            // bind value and view value for inputElement1
            expect(inputElement1.val()).toEqual('概要設計書（漢字）');
            expect(scope.cond.shnNm).toEqual('概要設計書（漢字）');
            
            // ngModel2 = ''
            compile('ＪＡＮ','3','janCd','janCd','cond.janCd','true',null,null,null,null, null,
                    null, null, null);
            inputElement0 = angular.element(element.children()[1]);
            var inputCtrl0 = inputElement0.controller('ngModel');
            expect(inputCtrl0.$valid).toBe(true);
            inputElement0.triggerHandler('focus');
            changeInputValue(inputElement0, '45000003');
            inputElement0.triggerHandler('blur');
            // bind value and view value for inputElement0
            expect(inputElement0.val()).toEqual('0000045000003');
            expect(scope.cond.janCd).toEqual('0000045000003');
            expect(inputCtrl0.$valid).toBe(true);
        });
        
        it('it should be catch ccExit validation ', function() {
            // cc-notexist = false
            //hakkoDay = '20140201' : no data
            scope.cond.hakkoDay = '20140201';
            compile('ＪＡＮ','3','janCd','janCd','cond.janCd','true',null,'false','true','false', 'cond.hakkoDay',
                    'shnNm', 'shnNm', 'cond.shnNm');
            inputElement0 = angular.element(element.children()[1]);
            inputElement1 = angular.element(element.children()[2]);
            var inputCtrl0 = inputElement0.controller('ngModel');
            inputElement0.triggerHandler('focus');
            changeInputValue(inputElement0, '4510049400250');
            $httpBackend.flush();
            inputElement0.triggerHandler('blur');
            // bind value and view value for inputElement0
            expect(inputElement0.val()).toEqual('4510049400250');
            expect(scope.cond.janCd).toEqual('4510049400250');
            expect(inputCtrl0.$invalid).toBe(true);
            expect(inputCtrl0.$error.ccExist).toBe(true);
            // bind value and view value for inputElement1
            expect(inputElement1.val()).toEqual('');
            expect(scope.cond.shnNm).toEqual(undefined);
            
            // cc-delexist = false
            //hakkoDay = '201400101' : data has act_kubun = 9
            scope.cond.hakkoDay = '201400101';
            compile('ＪＡＮ','3','janCd','janCd','cond.janCd','true',null,'true','false','false', 'cond.hakkoDay',
                    'shnNm', 'shnNm', 'cond.shnNm');
            inputElement0 = angular.element(element.children()[1]);
            inputElement1 = angular.element(element.children()[2]);
            var inputCtrl0 = inputElement0.controller('ngModel');
            inputElement0.triggerHandler('focus');
            changeInputValue(inputElement0, '4510049400250');
            $httpBackend.flush();
            inputElement0.triggerHandler('blur');
            // bind value and view value for inputElement0
            expect(inputElement0.val()).toEqual('4510049400250');
            expect(scope.cond.janCd).toEqual('4510049400250');
            expect(inputCtrl0.$invalid).toBe(true);
            expect(inputCtrl0.$error.ccExist).toBe(true);
            // bind value and view value for inputElement1
            expect(inputElement1.val()).toEqual('');
            expect(scope.cond.shnNm).toEqual(undefined);
        });
        
        it('it should be catch ccCode validation ', function() {
            compile('ＪＡＮ','3','janCd','janCd','cond.janCd','true',null,'true','true','false', 'cond.hakkoDay',
                    'shnNm', 'shnNm', 'cond.shnNm');
            inputElement0 = angular.element(element.children()[1]);
            inputElement1 = angular.element(element.children()[2]);
            var inputCtrl0 = inputElement0.controller('ngModel');
            inputElement0.triggerHandler('focus');
            // digit:9
            changeInputValue(inputElement0, '123456789');
            inputElement0.triggerHandler('blur');
            // bind value and view value for inputElement0
            expect(inputElement0.val()).toEqual('123456789');
            expect(scope.cond.janCd).toEqual('123456789');
            expect(inputCtrl0.$invalid).toBe(true);
            expect(inputCtrl0.$error.ccCode).toBe(true);
            // bind value and view value for inputElement1
            expect(inputElement1.val()).toEqual('');
            expect(scope.cond.shnNm).toEqual(undefined);
            
            // digit:10
            changeInputValue(inputElement0, '1234567890');
            inputElement0.triggerHandler('blur');
            // bind value and view value for inputElement0
            expect(inputElement0.val()).toEqual('1234567890');
            expect(scope.cond.janCd).toEqual('1234567890');
            expect(inputCtrl0.$invalid).toBe(true);
            expect(inputCtrl0.$error.ccCode).toBe(true);
            // bind value and view value for inputElement1
            expect(inputElement1.val()).toEqual('');
            expect(scope.cond.shnNm).toEqual(undefined);
            
            // [0,1] : 1
            changeInputValue(inputElement0, '1234567890123');
            inputElement0.triggerHandler('blur');
            // bind value and view value for inputElement0
            expect(inputElement0.val()).toEqual('1234567890123');
            expect(scope.cond.janCd).toEqual('1234567890123');
            expect(inputCtrl0.$invalid).toBe(true);
            expect(inputCtrl0.$error.ccCode).toBe(true);
            // bind value and view value for inputElement1
            expect(inputElement1.val()).toEqual('');
            expect(scope.cond.shnNm).toEqual(undefined);
            
            // [0,1]: #0, digit 12
            changeInputValue(inputElement0, '1034567890123');
            inputElement0.triggerHandler('blur');
            // bind value and view value for inputElement0
            expect(inputElement0.val()).toEqual('1034567890123');
            expect(scope.cond.janCd).toEqual('1034567890123');
            expect(inputCtrl0.$invalid).toBe(true);
            expect(inputCtrl0.$error.ccCode).toBe(true);
            // bind value and view value for inputElement1
            expect(inputElement1.val()).toEqual('');
            expect(scope.cond.shnNm).toEqual(undefined);
            
            // [1,2]: 2, digit 12
            changeInputValue(inputElement0, '0234567890123');
            inputElement0.triggerHandler('blur');
            // bind value and view value for inputElement0
            expect(inputElement0.val()).toEqual('0234567890123');
            expect(scope.cond.janCd).toEqual('0234567890123');
            expect(inputCtrl0.$invalid).toBe(true);
            expect(inputCtrl0.$error.ccCode).toBe(true);
            // bind value and view value for inputElement1
            expect(inputElement1.val()).toEqual('');
            expect(scope.cond.shnNm).toEqual(undefined);
            
            // [1,2]: 4, digit 12
            changeInputValue(inputElement0, '0434567890123');
            inputElement0.triggerHandler('blur');
            // bind value and view value for inputElement0
            expect(inputElement0.val()).toEqual('0434567890123');
            expect(scope.cond.janCd).toEqual('0434567890123');
            expect(inputCtrl0.$invalid).toBe(true);
            expect(inputCtrl0.$error.ccCode).toBe(true);
            // bind value and view value for inputElement1
            expect(inputElement1.val()).toEqual('');
            expect(scope.cond.shnNm).toEqual(undefined);
            
            // [0,1]: #0, digit 7
            changeInputValue(inputElement0, '1000000');
            inputElement0.triggerHandler('blur');
            // bind value and view value for inputElement0
            expect(inputElement0.val()).toEqual('1000000');
            expect(scope.cond.janCd).toEqual('1000000');
            expect(inputCtrl0.$invalid).toBe(true);
            expect(inputCtrl0.$error.ccCode).toBe(true);
            // bind value and view value for inputElement1
            expect(inputElement1.val()).toEqual('');
            expect(scope.cond.shnNm).toEqual(undefined);
            
            // UPC-A : 0-0[11]
            changeInputValue(inputElement0, '00000000000');
            inputElement0.triggerHandler('blur');
            // bind value and view value for inputElement0
            expect(inputElement0.val()).toEqual('00000000000');
            expect(scope.cond.janCd).toEqual('00000000000');
            expect(inputCtrl0.$invalid).toBe(true);
            expect(inputCtrl0.$error.ccCode).toBe(true);
            // bind value and view value for inputElement1
            expect(inputElement1.val()).toEqual('');
            expect(scope.cond.shnNm).toEqual(undefined);
            
            // UPC-A : 0-0[12]
            changeInputValue(inputElement0, '000000000000');
            inputElement0.triggerHandler('blur');
            // bind value and view value for inputElement0
            expect(inputElement0.val()).toEqual('000000000000');
            expect(scope.cond.janCd).toEqual('000000000000');
            expect(inputCtrl0.$invalid).toBe(true);
            expect(inputCtrl0.$error.ccCode).toBe(true);
            // bind value and view value for inputElement1
            expect(inputElement1.val()).toEqual('');
            expect(scope.cond.shnNm).toEqual(undefined);
            
            // UPC-E : 0-0[7]
            changeInputValue(inputElement0, '0000000');
            inputElement0.triggerHandler('blur');
            // bind value and view value for inputElement0
            expect(inputElement0.val()).toEqual('0000000');
            expect(scope.cond.janCd).toEqual('0000000');
            expect(inputCtrl0.$invalid).toBe(true);
            expect(inputCtrl0.$error.ccCode).toBe(true);
            // bind value and view value for inputElement1
            expect(inputElement1.val()).toEqual('');
            expect(scope.cond.shnNm).toEqual(undefined);
            
            // UPC-E : 0-0[6]
            changeInputValue(inputElement0, '000000');
            inputElement0.triggerHandler('blur');
            // bind value and view value for inputElement0
            expect(inputElement0.val()).toEqual('000000');
            expect(scope.cond.janCd).toEqual('000000');
            expect(inputCtrl0.$invalid).toBe(true);
            expect(inputCtrl0.$error.ccCode).toBe(true);
            // bind value and view value for inputElement1
            expect(inputElement1.val()).toEqual('');
            expect(scope.cond.shnNm).toEqual(undefined);
        });
        
        it('it should be catch ccCheckdigit validation ', function() {
            compile('ＪＡＮ','3','janCd','janCd','cond.janCd','true',null,'true','true','false', 'cond.hakkoDay',
                    'shnNm', 'shnNm', 'cond.shnNm');
            inputElement0 = angular.element(element.children()[1]);
            inputElement1 = angular.element(element.children()[2]);
            var inputCtrl0 = inputElement0.controller('ngModel');
            inputElement0.triggerHandler('focus');
            // CHECK JAN-13
            changeInputValue(inputElement0, '4510049400251');
            inputElement0.triggerHandler('blur');
            // bind value and view value for inputElement0
            expect(inputElement0.val()).toEqual('4510049400251');
            expect(scope.cond.janCd).toEqual('4510049400251');
            expect(inputCtrl0.$invalid).toBe(true);
            expect(inputCtrl0.$error.ccCheckdigit).toBe(true);
            // bind value and view value for inputElement1
            expect(inputElement1.val()).toEqual('');
            expect(scope.cond.shnNm).toEqual(undefined);
            
            // CHECK JAN-8
            changeInputValue(inputElement0, '49400251');
            inputElement0.triggerHandler('blur');
            // bind value and view value for inputElement0
            expect(inputElement0.val()).toEqual('49400251');
            expect(scope.cond.janCd).toEqual('49400251');
            expect(inputCtrl0.$invalid).toBe(true);
            expect(inputCtrl0.$error.ccCheckdigit).toBe(true);
            // bind value and view value for inputElement1
            expect(inputElement1.val()).toEqual('');
            expect(scope.cond.shnNm).toEqual(undefined);
            
            // CHECK EAN-13
            changeInputValue(inputElement0, '3000000000001');
            inputElement0.triggerHandler('blur');
            // bind value and view value for inputElement0
            expect(inputElement0.val()).toEqual('3000000000001');
            expect(scope.cond.janCd).toEqual('3000000000001');
            expect(inputCtrl0.$invalid).toBe(true);
            expect(inputCtrl0.$error.ccCheckdigit).toBe(true);
            // bind value and view value for inputElement1
            expect(inputElement1.val()).toEqual('');
            expect(scope.cond.shnNm).toEqual(undefined);
            
            // CHECK EAN-8
            changeInputValue(inputElement0, '30000002');
            inputElement0.triggerHandler('blur');
            // bind value and view value for inputElement0
            expect(inputElement0.val()).toEqual('30000002');
            expect(scope.cond.janCd).toEqual('30000002');
            expect(inputCtrl0.$invalid).toBe(true);
            expect(inputCtrl0.$error.ccCheckdigit).toBe(true);
            // bind value and view value for inputElement1
            expect(inputElement1.val()).toEqual('');
            expect(scope.cond.shnNm).toEqual(undefined);
            
            // CHECK ｲﾝｽﾄｱﾏｰｷﾝｸﾞ(02) : 13
            changeInputValue(inputElement0, '0200000000000');
            inputElement0.triggerHandler('blur');
            // bind value and view value for inputElement0
            expect(inputElement0.val()).toEqual('0200000000000');
            expect(scope.cond.janCd).toEqual('0200000000000');
            expect(inputCtrl0.$invalid).toBe(true);
            expect(inputCtrl0.$error.ccCheckdigit).toBe(true);
            // bind value and view value for inputElement1
            expect(inputElement1.val()).toEqual('');
            expect(scope.cond.shnNm).toEqual(undefined);
            
            // CHECK ｲﾝｽﾄｱﾏｰｷﾝｸﾞ(02) : 8
            changeInputValue(inputElement0, '02000000');
            inputElement0.triggerHandler('blur');
            // bind value and view value for inputElement0
            expect(inputElement0.val()).toEqual('02000000');
            expect(scope.cond.janCd).toEqual('02000000');
            expect(inputCtrl0.$invalid).toBe(true);
            expect(inputCtrl0.$error.ccCheckdigit).toBe(true);
            // bind value and view value for inputElement1
            expect(inputElement1.val()).toEqual('');
            expect(scope.cond.shnNm).toEqual(undefined);
            
            // CHECK ｲﾝｽﾄｱﾏｰｷﾝｸﾞ(2x) : 13
            changeInputValue(inputElement0, '2000000000000');
            inputElement0.triggerHandler('blur');
            // bind value and view value for inputElement0
            expect(inputElement0.val()).toEqual('2000000000000');
            expect(scope.cond.janCd).toEqual('2000000000000');
            expect(inputCtrl0.$invalid).toBe(true);
            expect(inputCtrl0.$error.ccCheckdigit).toBe(true);
            // bind value and view value for inputElement1
            expect(inputElement1.val()).toEqual('');
            expect(scope.cond.shnNm).toEqual(undefined);
            
            // CHECK ｲﾝｽﾄｱﾏｰｷﾝｸﾞ(2x) : 8
            changeInputValue(inputElement0, '20000000');
            inputElement0.triggerHandler('blur');
            // bind value and view value for inputElement0
            expect(inputElement0.val()).toEqual('20000000');
            expect(scope.cond.janCd).toEqual('20000000');
            expect(inputCtrl0.$invalid).toBe(true);
            expect(inputCtrl0.$error.ccCheckdigit).toBe(true);
            // bind value and view value for inputElement1
            expect(inputElement1.val()).toEqual('');
            expect(scope.cond.shnNm).toEqual(undefined);
            
            // CHECK 'UPC-E : 7
            changeInputValue(inputElement0, '0100000');
            inputElement0.triggerHandler('blur');
            // bind value and view value for inputElement0
            expect(inputElement0.val()).toEqual('0100000');
            expect(scope.cond.janCd).toEqual('0100000');
            expect(inputCtrl0.$invalid).toBe(true);
            expect(inputCtrl0.$error.ccCheckdigit).toBe(true);
            // bind value and view value for inputElement1
            expect(inputElement1.val()).toEqual('');
            expect(scope.cond.shnNm).toEqual(undefined);
        });
        
        it('it should be catch minlength validation ', function() {
            compile('ＪＡＮ','3','janCd','janCd','cond.janCd','true',null,'true','true','false', 'cond.hakkoDay',
                    'shnNm', 'shnNm', 'cond.shnNm');
            inputElement0 = angular.element(element.children()[1]);
            inputElement1 = angular.element(element.children()[2]);
            var inputCtrl0 = inputElement0.controller('ngModel');
            inputElement0.triggerHandler('focus');
            changeInputValue(inputElement0, '12345');
            inputElement0.triggerHandler('blur');
            // bind value and view value for inputElement0
            expect(inputElement0.val()).toEqual('12345');
            expect(scope.cond.janCd).toEqual(undefined);
            expect(inputCtrl0.$invalid).toBe(true);
            expect(inputCtrl0.$error.minlength).toBe(true);
            // bind value and view value for inputElement1
            expect(inputElement1.val()).toEqual('');
            expect(scope.cond.shnNm).toEqual(undefined);
        });
    });
});
