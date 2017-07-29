'use strict';

/* jasmine specs for directives go here */

describe('Test directive cc-tricd-input', function() {
    var element, scope, $compile, $httpBackend, changeInputValue, compile, templateHtml, record, empty;
    beforeEach(module('directives'));
    beforeEach(inject(function($injector, $sniffer) {
        // inject $compile & $roorScope
        $compile = $injector.get('$compile');
        scope = $injector.get('$rootScope');

        $httpBackend = $injector.get('$httpBackend');
        // declare data response
        record = {
            code : '305000000',
            kubun : '1',
            name : '三喜青果（有）',
            shortName : '三喜青果・'
        };
        empty = {};

        $httpBackend.when('GET', /\/core\/codename\/m011trim\/000000000\?hakkoDay=(\d{8})/).respond([ empty ], {
            checkData : true
        });
        $httpBackend.whenGET(/\/core\/codename\/m011trim\/123456789\?hakkoDay=(\d{8})/).respond([ empty ], {
            checkData : true
        });
        $httpBackend.whenGET(/\/core\/codename\/m011trim\/000000001\?hakkoDay=(\d{8})/).respond([ record ], {
            checkData : true
        });

        // call browser change input value
        changeInputValue = function(elm, value) {
            elm.val(value);
            browserTrigger(elm, $sniffer.hasEvent('input') ? 'input' : 'change');
        };

        // compile directive need to test.
        compile = function(label, partition, id, name, ngModel, required, notExist, delExist, shortName, parameter01,
                id2, name2, ngModel2, readOnly) {
            var htmlText = '';
            htmlText += '<cc-tricd-input cc-label="' + label + '"';
            htmlText += ' cc-partition="' + partition + '"';
            htmlText += ' id="' + id + '"';
            htmlText += ' name="' + name + '"';
            htmlText += ' ng-model="' + ngModel + '"';
            if (required != null) {
                htmlText += ' cc-required=' + required;
            }
            if (readOnly != null) {
                htmlText += ' cc-readonly=' + readOnly;
            }
            htmlText += ' cc-notexist="' + notExist + '"';
            htmlText += ' cc-delexist="' + delExist + '"';
            htmlText += ' cc-shortname="' + shortName + '"';
            htmlText += ' cc-parameter01="' + parameter01 + '"';
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

            // declare result to bind data.
            scope.result = {
                triCd : '',
                hakkoDay : '',
                triNm : ''
            };

            templateHtml = element.html();
        };
    }));
    describe('cc-tricd-input: test template', function() {
        // test template
        it('render html with partition="", required=true, not model2', function() {

            // commpile(label, partition, id, name, ngModel, required, notExist,
            // delExist, shortName, parameter01, id2, name2, ngModel2,
            // readOnly);
            compile('取引先', '', 'triCd', 'triCd', 'result.triCd', true, true, true, true, 'result.hakkoDay', null, null,
                    null, null);
            var e = angular.element(element);
            var children = element.children();
            expect(children.length).toEqual(2);

            // Test label
            var divLabel = children[0];
            expect(divLabel.hasAttribute('class')).toBe(true);
            expect(divLabel.getAttribute('class')).toBe(true);

            var divElement = angular.element(e.children()[0]);
            expect(divElement.is('div')).toBe(true);
            expect(divElement.hasClass('cs-label')).toBe(true);
            expect(divElement.hasClass('cs-label-divide-zero')).toBe(true);

            expect(divElement.has('span').length).toEqual(0);

            // Test input1
            var input1 = children[1];
            expect(input1.hasAttribute('type')).toBe(true);
            expect(input1.hasAttribute('type')).toBe(true);
            expect(input1.getAttribute('cc-type')).toEqual('tricd');
            expect(input1.getAttribute('id')).toEqual('triCd');
            expect(input1.getAttribute('name')).toEqual('triCd');
            expect(input1.getAttribute('ng-model')).toEqual('ngModel');
            expect(input1.hasAttribute('required')).toBe(true);
            expect(input1.getAttribute('ng-minlength')).toEqual('9');
            expect(input1.getAttribute('maxlength')).toEqual('10');
            expect(input1.hasAttribute('cc-blur-validation')).toBe(true);
            expect(input1.hasAttribute('ui-event')).toBe(false);

            var input1Elt = angular.element(e.children()[1]);
            console.log(input1Elt);
            expect(input1Elt.is('input')).toBe(true);
            expect(input1Elt.hasClass('cs-num-10')).toBe(true);
            expect(input1Elt.hasClass('ng-valid')).toBe(true);
            expect(input1Elt.hasClass('ng-pristine')).toBe(true);
            //expect(input1Elt.hasClass('ng-invalid-required')).toBe(true);
            //expect(input1Elt.hasClass('ng-valid-minlength')).toBe(true);
            
            // Test input2

        });

        it('render html with partition="", required=false, not model2', function() {
            // commpile(label, partition, id, name, ngModel, required, notExist,
            // delExist, shortName, parameter01, id2, name2, ngModel2,
            // readOnly);
            compile('取引先', '', 'triCd', 'triCd', 'result.triCd', false, true, true, true, 'result.hakkoDay', null,
                    null, null, null);

            var children = element.children();
            expect(children.length).toEqual(2);

            var divLabel = children[0];
            var input1 = children[1];
            expect(input1.hasAttribute('required')).toBe(false);

            var e = angular.element(element);
            var input1Elt = angular.element(e.children()[1]);
            console.log(input1Elt);
            //expect(input1Elt.hasClass('ng-invalid-required')).toBe(false);
            //expect(input1Elt.hasClass('ng-valid-minlength')).toBe(true);
        });

        it('render html with partition="", readonly=true, not model2', function() {

            compile('取引先', '', 'triCd', 'triCd', 'result.triCd', null, true, true, true, 'result.hakkoDay', null, null,
                    null, true);

            var children = element.children();
            expect(children.length).toEqual(2);

            var divLabel = children[0];
            var input1 = children[1];
            expect(input1.hasAttribute('readonly')).toBe(true);
        });

        it('render html with partition="", readonly=false, not model2', function() {

            compile('取引先', '', 'triCd', 'triCd', 'result.triCd', null, true, true, true, 'result.hakkoDay', null, null,
                    null, false);

            var children = element.children();
            expect(children.length).toEqual(2);

            var divLabel = children[0];
            var input1 = children[1];
            expect(input1.hasAttribute('readonly')).toBe(false);
        });

        it('render html with partition="2", required=true, no model2', function() {
            // commpile(label, partition, id, name, ngModel, required, notExist,
            // delExist, shortName, parameter01, id2, name2, ngModel2,
            // readOnly);
            compile('取引先', '2', 'triCd', 'triCd', 'result.triCd', true, true, true, true, 'result.hakkoDay', null,
                    null, null, null);

            var e = angular.element(element);
            var divElement = angular.element(e.children()[0]);
            expect(divElement.is('div')).toBe(true);
            expect(divElement.hasClass('cs-label')).toBe(true);
            expect(divElement.hasClass('cs-label-large-required')).toBe(true);

            expect(divElement.has('span').length).toEqual(1);
            expect(angular.element(divElement.children()[0]).hasClass('cs-txt-required')).toBe(true);

        });

        it('render html with partition="2", required=false, no model2', function() {
            // commpile(label, partition, id, name, ngModel, required, notExist,
            // delExist, shortName, parameter01, id2, name2, ngModel2,
            // readOnly);
            compile('取引先', '2', 'triCd', 'triCd', 'result.triCd', false, true, true, true, 'result.hakkoDay', null,
                    null, null, null);

            var e = angular.element(element);
            var divElement = angular.element(e.children()[0]);
            expect(divElement.is('div')).toBe(true);
            expect(divElement.hasClass('cs-label')).toBe(true);
            expect(divElement.hasClass('cs-label-large')).toBe(true);

            expect(divElement.has('span').length).toEqual(0);

        });

        it('render html with partition="2", required=true, no model2', function() {
            // commpile(label, partition, id, name, ngModel, required, notExist,
            // delExist, shortName, parameter01, id2, name2, ngModel2,
            // readOnly);
            compile('取引先', '2', 'triCd', 'triCd', 'result.triCd', true, true, true, true, 'result.hakkoDay', null,
                    null, null, null);

            var e = angular.element(element);
            var divElement = angular.element(e.children()[0]);
            expect(divElement.is('div')).toBe(true);
            expect(divElement.hasClass('cs-label')).toBe(true);
            expect(divElement.hasClass('cs-label-large-required')).toBe(true);

            expect(divElement.has('span').length).toEqual(1);

        });

        it('render html with partition="2", required=true, model2', function() {
            // commpile(label, partition, id, name, ngModel, required, notExist,
            // delExist, shortName, parameter01, id2, name2, ngModel2,
            // readOnly);
            compile('取引先', '2', 'triCd', 'triCd', 'result.triCd', true, true, true, true, 'result.hakkoDay', 'triNm',
                    'triNm', 'result.triNm', null);

            var children = element.children();
            expect(children.length).toEqual(3);
            
            var e = angular.element(element);

            var divElement = angular.element(e.children()[0]);
            expect(divElement.is('div')).toBe(true);
            expect(divElement.hasClass('cs-label')).toBe(true);
            expect(divElement.hasClass('cs-label-large-required')).toBe(true);

            expect(divElement.has('span').length).toEqual(1);

            var input2 = children[2];
            expect(input2.getAttribute('id')).toEqual('triNm');
            expect(input2.getAttribute('name')).toEqual('triNm');
            expect(input2.getAttribute('ng-model')).toEqual('ngModel2');
            expect(input2.hasAttribute('readonly')).toBe(true);
            expect(input2.getAttribute('maxlength')).toEqual('5');
        });

        it('render html with partition="2", required=false, model2', function() {
            // commpile(label, partition, id, name, ngModel, required, notExist,
            // delExist, shortName, parameter01, id2, name2, ngModel2,
            // readOnly);
            compile('取引先', '2', 'triCd', 'triCd', 'result.triCd', false, true, true, true, 'result.hakkoDay', 'triNm',
                    'triNm', 'result.triNm', null);

            var children = element.children();
            expect(children.length).toEqual(3);
            
            var e = angular.element(element);

            var divElement = angular.element(e.children()[0]);
            expect(divElement.is('div')).toBe(true);
            expect(divElement.hasClass('cs-label')).toBe(true);
            expect(divElement.hasClass('cs-label-large')).toBe(true);

            expect(divElement.has('span').length).toEqual(0);
        });

        it('render html with partition="3", required=true, model2', function() {
            // commpile(label, partition, id, name, ngModel, required, notExist,
            // delExist, shortName, parameter01, id2, name2, ngModel2,
            // readOnly);
            compile('取引先', '3', 'triCd', 'triCd', 'result.triCd', true, true, true, true, 'result.hakkoDay', 'triNm',
                    'triNm', 'result.triNm', null);

            var children = element.children();
            expect(children.length).toEqual(3);

            var divLabel = children[0];
            var input1 = children[1];
            expect(input1.getAttribute('ng-minlength')).toEqual('9');
            expect(input1.getAttribute('maxlength')).toEqual('10');
            expect(input1.hasAttribute('ui-event')).toBe(true);
            expect(input1.hasAttribute('required')).toBe(true);

            var e = angular.element(element);
            var divElement = angular.element(e.children()[0]);
            expect(divElement.is('div')).toBe(true);
            expect(divElement.hasClass('cs-label')).toBe(true);
            expect(divElement.hasClass('cs-label-large-required')).toBe(true);

            expect(divElement.has('span').length).toEqual(1);

            var input2 = children[2];
            expect(input2.getAttribute('id')).toEqual('triNm');
            expect(input2.getAttribute('name')).toEqual('triNm');
            expect(input2.getAttribute('ng-model')).toEqual('ngModel2');
            expect(input2.hasAttribute('readonly')).toBe(true);
            expect(input2.getAttribute('maxlength')).toEqual('5'); // shortname=true
        });

        it('render html with partition="3", required=true, model2', function() {
            // commpile(label, partition, id, name, ngModel, required, notExist,
            // delExist, shortName, parameter01, id2, name2, ngModel2,
            // readOnly);
            compile('取引先', '3', 'triCd', 'triCd', 'result.triCd', true, true, true, true, 'result.hakkoDay', 'triNm',
                    'triNm', 'result.triNm', null);

            var children = element.children();
            expect(children.length).toEqual(3);

            var divLabel = children[0];
            var input1 = children[1];
            expect(input1.getAttribute('ng-minlength')).toEqual('9');
            expect(input1.getAttribute('maxlength')).toEqual('10');
            expect(input1.hasAttribute('ui-event')).toBe(true);
            expect(input1.hasAttribute('required')).toBe(true);

            var e = angular.element(element);
            var divElement = angular.element(e.children()[0]);
            expect(divElement.is('div')).toBe(true);
            expect(divElement.hasClass('cs-label')).toBe(true);
            expect(divElement.hasClass('cs-label-large-required')).toBe(true);

            expect(divElement.has('span').length).toEqual(1);
        });

        it('render html with partition="3", required=false, model2', function() {
            // commpile(label, partition, id, name, ngModel, required, notExist,
            // delExist, shortName, parameter01, id2, name2, ngModel2,
            // readOnly);
            compile('取引先', '3', 'triCd', 'triCd', 'result.triCd', false, true, true, true, 'result.hakkoDay', 'triNm',
                    'triNm', 'result.triNm', null);
            var children = element.children();
            expect(children.length).toEqual(3);

            var divLabel = children[0];
            var input1 = children[1];
            expect(input1.hasAttribute('required')).toBe(false);
            
            var e = angular.element(element);
            var divElement = angular.element(e.children()[0]);
            expect(divElement.has('span').length).toEqual(0);
            
        });

        it('render html with partition="3", readonly=true, model2, shortname=false', function() {

            compile('取引先', '3', 'triCd', 'triCd', 'result.triCd', null, true, true, false, 'result.hakkoDay', 'triNm',
                    'triNm', 'result.triNm', true);

            var children = element.children();
            expect(children.length).toEqual(3);

            var divLabel = children[0];
            var e = angular.element(element);
            var divElement = angular.element(e.children()[0]);
            expect(divElement.has('span').length).toEqual(0);
            
            var input1 = children[1];
            expect(input1.hasAttribute('ng-minlength')).toBe(false);
            expect(input1.getAttribute('maxlength')).toEqual('10');
            expect(input1.hasAttribute('ui-event')).toBe(false);
            expect(input1.hasAttribute('readonly')).toBe(true);

            var input2 = children[2];
            expect(input2.hasAttribute('readonly')).toBe(true);
            expect(input2.hasAttribute('ng-minlength')).toBe(false);
            expect(input2.getAttribute('maxlength')).toEqual('20'); // shortname=true

            var input2Ele = angular.element(input2);
            expect(input2Ele.hasClass('cs-char-20')).toBe(true);
        });
    });
    describe('cc-tricd-input: test logic validate', function() {
        /* Test logic code */
        it('it should catch required error', function() {
            compile('取引先', '3', 'triCd', 'triCd', 'result.triCd', true, true, true, true, 'result.hakkoDay', 'triNm',
                    'triNm', 'result.triNm', false);

            var input1 = angular.element(element.children()[1]);
            var inputCtrl1 = input1.controller('ngModel');

            input1.triggerHandler('focus');
            changeInputValue(input1, '');
            input1.triggerHandler('blur');
            expect(input1.val()).toEqual('');
            expect(scope.result.triCd).toEqual(undefined);
            // it should catch required validity
            expect(inputCtrl1.$invalid).toBe(true);
            expect(inputCtrl1.$error.required).toBe(true);

            // focus, view value should be ''.
            input1.triggerHandler('focus');
            expect(input1.val()).toEqual('');
        });

        it('it should catch minlength error', function() {
            compile('取引先', '3', 'triCd', 'triCd', 'result.triCd', true, true, true, true, 'result.hakkoDay', 'triNm',
                    'triNm', 'result.triNm', false);

            var input1 = angular.element(element.children()[1]);
            var inputCtrl1 = input1.controller('ngModel');

            input1.triggerHandler('focus');
            changeInputValue(input1, '1234');
            input1.triggerHandler('blur');
            expect(input1.val()).toEqual('1234');
            expect(scope.result.triCd).toEqual(undefined);
            // it should catch minlength validity
            expect(inputCtrl1.$invalid).toBe(true);
            expect(inputCtrl1.$error.required).toBe(false);
            expect(inputCtrl1.$error.minlength).toBe(true);

            // focus, view value should be ''.
            input1.triggerHandler('focus');
            expect(input1.val()).toEqual('1234');
        });
        
        it('it should catch invalid input, minlength error', function() {
            compile('取引先', '3', 'triCd', 'triCd', 'result.triCd', true, true, true, true, 'result.hakkoDay', 'triNm',
                    'triNm', 'result.triNm', false);
            
            var input1 = angular.element(element.children()[1]);
            var inputCtrl1 = input1.controller('ngModel');
            
            input1.triggerHandler('focus');
            changeInputValue(input1, '1234d,-');
            input1.triggerHandler('blur');
            expect(input1.val()).toEqual('1234-');
            expect(scope.result.triCd).toEqual(undefined);
            // it should catch minlength validity
            expect(inputCtrl1.$invalid).toBe(true);
            expect(inputCtrl1.$error.required).toBe(false);
            expect(inputCtrl1.$error.minlength).toBe(true);
            
            // focus, view value should be ''.
            input1.triggerHandler('focus');
            expect(input1.val()).toEqual('1234-');
        });

        it('it should catch, length valid but format error', function() {
            compile('取引先', '3', 'triCd', 'triCd', 'result.triCd', true, true, true, true, 'result.hakkoDay', 'triNm',
                    'triNm', 'result.triNm', false);

            var input1 = angular.element(element.children()[1]);
            var inputCtrl1 = input1.controller('ngModel');

            input1.triggerHandler('focus');
            changeInputValue(input1, '12345678-');
            input1.triggerHandler('blur');
            expect(input1.val()).toEqual('12345678-');
            expect(scope.result.triCd).toEqual(undefined);
            // it should catch ccTricd validity
            expect(inputCtrl1.$invalid).toBe(true);
            expect(inputCtrl1.$error.required).toBe(false);
            expect(inputCtrl1.$error.ccTricd).toBe(true);

            // focus, view value should
            input1.triggerHandler('focus');
            expect(input1.val()).toEqual('12345678-');
        });

        it('it should catch format error', function() {
            compile('取引先', '3', 'triCd', 'triCd', 'result.triCd', true, true, true, true, 'result.hakkoDay', 'triNm',
                    'triNm', 'result.triNm', false);

            var input1 = angular.element(element.children()[1]);
            var inputCtrl1 = input1.controller('ngModel');

            input1.triggerHandler('focus');
            changeInputValue(input1, '1234567890');
            input1.triggerHandler('blur');
            expect(input1.val()).toEqual('1234567890');
            expect(scope.result.triCd).toEqual(undefined);
            // it should catch tricd validity
            expect(inputCtrl1.$invalid).toBe(true);
            expect(inputCtrl1.$error.required).toBe(false);
            expect(inputCtrl1.$error.ccTricd).toBe(true);

            // focus, view value should be ''
            input1.triggerHandler('focus');
            expect(input1.val()).toEqual('1234567890');
        });

        it('it should catch wrong format', function() {
            compile('取引先', '3', 'triCd', 'triCd', 'result.triCd', true, true, true, true, 'result.hakkoDay', 'triNm',
                    'triNm', 'result.triNm', false);

            var input1 = angular.element(element.children()[1]);
            var input1 = angular.element(element.find('input')[0]);
            var inputSec = angular.element(element.find('input')[1]);

            var inputCtrl1 = input1.controller('ngModel');
            var inputCtrlSec = inputSec.controller('ngModel');

            input1.triggerHandler('focus');
            changeInputValue(input1, '123-456789');
            input1.triggerHandler('blur');
            expect(input1.val()).toEqual('123-456789');
            expect(scope.result.triCd).toEqual(undefined);
            // it should not catch by any validity
            expect(inputCtrl1.$invalid).toBe(true);
            expect(inputCtrl1.$error.ccTricd).toBe(true);

            // focus, view value should be valid
            input1.triggerHandler('focus');
            expect(input1.val()).toEqual('123-456789');
        });

        it('it should bind valid input', function() {
            compile('取引先', '3', 'triCd', 'triCd', 'result.triCd', true, true, true, true, 'result.hakkoDay', 'triNm',
                    'triNm', 'result.triNm', false);

            var input1 = angular.element(element.children()[1]);
            var inputCtrl1 = input1.controller('ngModel');

            input1.triggerHandler('focus');
            changeInputValue(input1, '123456789');
            input1.triggerHandler('blur');
            expect(input1.val()).toEqual('123456-789');
            expect(scope.result.triCd).toEqual('123456789');
            // it should not catch by any validity
            expect(inputCtrl1.$invalid).toBe(false);
            expect(inputCtrl1.$error.required).toBe(false);

            // focus, view value should be valid
            input1.triggerHandler('focus');
            expect(input1.val()).toEqual('123456789');
        });

        it('it should bind valid input have right format', function() {
            compile('取引先', '3', 'triCd', 'triCd', 'result.triCd', true, true, true, true, 'result.hakkoDay', 'triNm',
                    'triNm', 'result.triNm', false);

            var input1 = angular.element(element.children()[1]);
            var inputCtrl1 = input1.controller('ngModel');

            input1.triggerHandler('focus');
            changeInputValue(input1, '123456-789');
            input1.triggerHandler('blur');
            expect(input1.val()).toEqual('123456-789');
            expect(scope.result.triCd).toEqual('123456789');
            // it should not catch by any validity
            expect(inputCtrl1.$invalid).toBe(false);
            expect(inputCtrl1.$error.required).toBe(false);

            // focus, view value should be valid
            input1.triggerHandler('focus');
            expect(input1.val()).toEqual('123456789');
        });
    });
    describe('cc-tricd-input: test request server', function() {
        /** Test request server call */
        it('it should get empty when valid input but no data', function() {
            compile('取引先', '3', 'triCd', 'triCd', 'result.triCd', true, true, true, true, 'result.hakkoDay', 'triNm',
                    'triNm', 'result.triNm', false);

            var input1 = angular.element(element.children()[1]);
            var inputCtrl1 = input1.controller('ngModel');
            var input2 = angular.element(element.children()[2]);
            var inputCtrl2 = input2.controller('ngModel');

            // console.log(scope.blurCallback);
            // spyOn(scope, 'blurCallback');
            input1.triggerHandler('focus');
            changeInputValue(input1, '000000000');
            $httpBackend.flush();
            input1.triggerHandler('blur');

            // expect(scope.blurCallback).toHaveBeenCalled();
            expect(input2.val()).toEqual('');
            expect(scope.result.triNm).toEqual('');
        });

        it('it should get data when valid input have data', function() {
            /* shortName = true */
            compile('取引先', '3', 'triCd', 'triCd', 'result.triCd', true, true, true, true, 'result.hakkoDay', 'triNm',
                    'triNm', 'result.triNm', false);

            var input1 = angular.element(element.children()[1]);
            var inputCtrl1 = input1.controller('ngModel');
            var input2 = angular.element(element.children()[2]);
            var inputCtrl2 = input2.controller('ngModel');

            input1.triggerHandler('focus');
            changeInputValue(input1, '000000001');
            $httpBackend.flush();
            input1.triggerHandler('blur');

            expect(input2.val()).toEqual(record.shortName);
            expect(scope.result.triNm).toEqual(record.shortName);
        });

        it('it should get data when valid input have data', function() {
            /* shortName = false */
            compile('取引先', '3', 'triCd', 'triCd', 'result.triCd', true, true, true, false, 'result.hakkoDay', 'triNm',
                    'triNm', 'result.triNm', false);

            var input1 = angular.element(element.children()[1]);
            var inputCtrl1 = input1.controller('ngModel');
            var input2 = angular.element(element.children()[2]);
            var inputCtrl2 = input2.controller('ngModel');

            input1.triggerHandler('focus');
            changeInputValue(input1, '000000001');
            $httpBackend.flush();
            input1.triggerHandler('blur');

            expect(input2.val()).toEqual(record.name);
            expect(scope.result.triNm).toEqual(record.name);
        });
    });
});
