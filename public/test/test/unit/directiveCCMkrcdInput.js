'use strict';

/* jasmine specs for directives go here */

describe('Test directive', function() {
    var element, scope, $compile, $httpBackend, changeInputValue, compile;
    var templateHtml, inputElement0, inputElement1, inputHtml0,inputHtml1;
    var record, empty;
    
    beforeEach(module('directives'));
    beforeEach(inject(function($injector, $sniffer) {
        // inject $compile & $roorScope
        $compile = $injector.get('$compile');
        scope = $injector.get('$rootScope');
        $httpBackend = $injector.get('$httpBackend');
        
        empty = {};

        record = {
            code : '305000000',
            kubun : '1',
            name : '三喜青果（有）',
            shortName : '三喜青果・'
        };
        // declare result to bind data.
        scope.result = {
            mkrCd : '',
            mkrNm : '',
            hakkoDay: ''
        };
        
        // response have data
        $httpBackend.when('GET', /\/core\/codename\/m013makm\/7(\d{6})\?hakkoDay=\d{8}/).respond([record], {checkData : true});
        // response have nodata
        $httpBackend.when('GET', /\/core\/codename\/m013makm\/0(\d{6})\?hakkoDay=\d{8}/).respond([empty], {checkData : true});
        $httpBackend.when('GET', /\/core\/codename\/m013makm\/1(\d{6})\?hakkoDay=\d{8}/).respond([empty], {checkData : true});

        // call browser change input value
        changeInputValue = function(elm, value) {
            elm.val(value);
            browserTrigger(elm, $sniffer.hasEvent('input') ? 'input' : 'change');
        };

        // compile directive need to test.
        compile = function(label, partition, id, name, ngModel, required, readOnly,
        		notExist, delexist, shortName, para01, id2, name2, ngModel2) {
            var htmlText = '';
            htmlText += '<cc-mkrcd-input';
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
            htmlText += ' cc-notexist="' + notExist + '"';
            htmlText += ' cc-delexist="' + delexist + '"';
            htmlText += ' cc-shortname="' + shortName + '"';
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
    }));

    /* Test render html */
    xdescribe('cc-mkrcd-input: test template partition "3"', function() {
    	it('test render html: partition="3", cc-required=true, cc-shortname=false, have model2 ', function() {
            compile('メーカー', '3', 'mkrCd', 'mkrCd', 'result.mkrCd', true, null, false, true, false,
                    'result.hakkoDay', 'mkrNm', 'mkrNm', 'result.mkrNm');
            
            var e = angular.element(element);
            var children = element.children();
            expect(children.length).toEqual(3);

            var divLabel = children[0];
            expect(divLabel.hasAttribute('class')).toBe(true);

            var divElement = angular.element(e.children()[0]);
            expect(divElement.is('div')).toBe(true);
            expect(divElement.hasClass('cs-label')).toBe(true);
            expect(divElement.hasClass('cs-label-large-required')).toBe(true);
            expect(divElement.text()).toEqual('メーカー *');

            expect(divElement.has('span').length).toEqual(1);
            expect(angular.element(divElement.children('span')[0]).hasClass('cs-txt-required')).toBe(true);

            var input1 = children[1];
            expect(input1.getAttribute('type')).toEqual('text');
            expect(input1.getAttribute('cc-type')).toEqual('code');
            expect(input1.getAttribute('id')).toEqual('mkrCd');
            expect(input1.getAttribute('name')).toEqual('mkrCd');
            expect(input1.getAttribute('ng-model')).toEqual('ngModel');
            expect(input1.hasAttribute('required')).toBe(true);
            expect(input1.getAttribute('ng-minlength')).toEqual('7');
            expect(input1.getAttribute('maxlength')).toEqual('7');
            expect(input1.hasAttribute('cc-blur-validation')).toBe(true);
            expect(input1.hasAttribute('ui-event')).toBe(true);

            var input1Elt = angular.element(e.children()[1]);
            expect(input1Elt.is('input')).toBe(true);
            expect(input1Elt.hasClass('ng-pristine')).toBe(true);
            expect(input1Elt.hasClass('cs-ime-off')).toBe(true);
            expect(input1Elt.hasClass('cs-num-10')).toBe(true);
            expect(input1Elt.hasClass('ng-invalid')).toBe(true);
            expect(input1Elt.hasClass('ng-invalid-required')).toBe(true);
            expect(input1Elt.hasClass('ng-valid-minlength')).toBe(true);

            var input2 = children[2];
            expect(input2.getAttribute('type')).toEqual('text');
            expect(input2.getAttribute('cc-type')).toEqual('zenkaku');
            expect(input2.getAttribute('id')).toEqual('mkrNm');
            expect(input2.getAttribute('name')).toEqual('mkrNm');
            expect(input2.getAttribute('ng-model')).toEqual('ngModel2');
            expect(input2.hasAttribute('readonly')).toBe(true);
            expect(input2.getAttribute('maxlength')).toEqual('20');
            
            var input2Elt = angular.element(e.children()[2]);
            expect(input2Elt.hasClass('ng-pristine')).toBe(true);
            expect(input2Elt.hasClass('ng-valid')).toBe(true);
            expect(input2Elt.hasClass('cs-ime-on')).toBe(true);
            expect(input2Elt.hasClass('cs-left')).toBe(true);
            expect(input2Elt.hasClass('cs-char-20')).toBe(true);
        });
    	
    	it('test render html: partition="3", cc-required=false, cc-shortname=false, have model2 ', function() {
            compile('メーカー', '3', 'mkrCd', 'mkrCd', 'result.mkrCd', false, null, false, true, false,
                    'result.hakkoDay', 'mkrNm', 'mkrNm', 'result.mkrNm');
            
            var e = angular.element(element);
            var children = element.children();
            expect(children.length).toEqual(3);
            
            var divLabel = children[0];
            expect(divLabel.hasAttribute('class')).toBe(true);

            var divElement = angular.element(e.children()[0]);
            expect(divElement.is('div')).toBe(true);
            expect(divElement.hasClass('cs-label')).toBe(true);
            expect(divElement.hasClass('cs-label-large')).toBe(true);
            expect(divElement.text()).toEqual('メーカー');

            expect(divElement.has('span').length).toEqual(0);
            
            var input1 = children[1];
            expect(input1.hasAttribute('required')).toBe(false);
            
            var input1Elt = angular.element(e.children()[1]);

            expect(input1Elt.is('input')).toBe(true);
            expect(input1Elt.hasClass('ng-valid')).toBe(true);
    	});
    	
    	it('test render html: partition="3", cc-required=true, cc-shortname=true, have model2 ', function() {
            compile('メーカー', '3', 'mkrCd', 'mkrCd', 'result.mkrCd', true, null, false, true, true,
                    'result.hakkoDay', 'mkrNm', 'mkrNm', 'result.mkrNm');
            
            var e = angular.element(element);
            var children = element.children();
            expect(children.length).toEqual(3);
            
            var input2 = children[2];
            expect(input2.getAttribute('maxlength')).toEqual('5');
            
            var input2Elt = angular.element(e.children()[2]);
            expect(input2Elt.hasClass('cs-char-5')).toBe(true);
    	});
    	
    	it('test render html: partition="3", cc-required=false, cc-shortname=true, have model2 ', function() {
            compile('メーカー', '3', 'mkrCd', 'mkrCd', 'result.mkrCd', false, null, false, true, false,
                    'result.hakkoDay', 'mkrNm', 'mkrNm', 'result.mkrNm');
            
            var e = angular.element(element);
            var children = element.children();
            expect(children.length).toEqual(3);
            
            var divLabel = children[0];
            expect(divLabel.hasAttribute('class')).toBe(true);

            var divElement = angular.element(e.children()[0]);
            expect(divElement.is('div')).toBe(true);
            expect(divElement.hasClass('cs-label')).toBe(true);
            expect(divElement.hasClass('cs-label-large')).toBe(true);
            expect(divElement.text()).toEqual('メーカー');

            expect(divElement.has('span').length).toEqual(0);
            
            var input1 = children[1];
            expect(input1.hasAttribute('required')).toBe(false);
            
            var input2 = children[2];
            expect(input2.getAttribute('maxlength')).toEqual('5');
            
            var input2Elt = angular.element(e.children()[2]);
            expect(input2Elt.hasClass('cs-char-5')).toBe(true);
            
            var input1Elt = angular.element(e.children()[1]);

            expect(input1Elt.is('input')).toBe(true);
            expect(input1Elt.hasClass('ng-valid')).toBe(true);
        });
    	
    	it('test render html: partition="3", cc-required=true, cc-shortname=true, have no model2 ', function() {
            compile('メーカー', '3', 'mkrCd', 'mkrCd', 'result.mkrCd', true, null, false, true, true,
                    'result.hakkoDay', null, null, null);
            
            var e = angular.element(element);
            var children = element.children();
            expect(children.length).toEqual(2);
            
            var divLabel = children[0];
            expect(divLabel.hasAttribute('class')).toBe(true);
            
            var divElement = angular.element(e.children()[0]);
            expect(divElement.is('div')).toBe(true);
            expect(divElement.hasClass('cs-label')).toBe(true);
            expect(divElement.hasClass('cs-label-large-required')).toBe(true);
            expect(divElement.text()).toEqual('メーカー *');
            
            expect(divElement.has('span').length).toEqual(1);
            expect(angular.element(divElement.children('span')[0]).hasClass('cs-txt-required')).toBe(true);

            var input1 = children[1];
            var input1Elt = angular.element(e.children()[1]);
            expect(input1.getAttribute('type')).toEqual('text');
            expect(input1.getAttribute('cc-type')).toEqual('code');
            expect(input1.getAttribute('id')).toEqual('mkrCd');
            expect(input1.getAttribute('name')).toEqual('mkrCd');
            expect(input1.getAttribute('ng-model')).toEqual('ngModel');
            expect(input1.hasAttribute('required')).toBe(true);
            expect(input1.getAttribute('ng-minlength')).toEqual('7');
            expect(input1.getAttribute('maxlength')).toEqual('7');
            expect(input1.hasAttribute('cc-blur-validation')).toBe(true);
            expect(input1.hasAttribute('ui-event')).toBe(false);
    	});
    	
    	it('test render html: partition="3", cc-required=false, cc-shortname=true, have no model2 ', function() {
            compile('メーカー', '3', 'mkrCd', 'mkrCd', 'result.mkrCd', false, null, false, true, true,
                    'result.hakkoDay', null, null, null);
            
            var e = angular.element(element);
            var children = element.children();
            expect(children.length).toEqual(2);
            
            var divLabel = children[0];
            expect(divLabel.hasAttribute('class')).toBe(true);

            var divElement = angular.element(e.children()[0]);
            expect(divElement.is('div')).toBe(true);
            expect(divElement.hasClass('cs-label')).toBe(true);
            expect(divElement.hasClass('cs-label-large')).toBe(true);
            expect(divElement.text()).toEqual('メーカー');

            expect(divElement.has('span').length).toEqual(0);
        });
    	
    	it('test render html: partition="3", cc-required=true, cc-shortname=false, have no model2 ', function() {
    	    compile('メーカー', '3', 'mkrCd', 'mkrCd', 'result.mkrCd', true, null, false, true, true,
    	            'result.hakkoDay', null, null, null);
    	    
    	    var e = angular.element(element);
    	    var children = element.children();
    	    expect(children.length).toEqual(2);
    	    
    	    var divLabel = children[0];
            expect(divLabel.hasAttribute('class')).toBe(true);
            
            var divElement = angular.element(e.children()[0]);
            expect(divElement.is('div')).toBe(true);
            expect(divElement.hasClass('cs-label')).toBe(true);
            expect(divElement.hasClass('cs-label-large-required')).toBe(true);
            expect(divElement.text()).toEqual('メーカー *');
    	});
    	
    	it('test render html: partition="3", cc-required=false, cc-shortname=false, have no model2 ', function() {
    	    compile('メーカー', '3', 'mkrCd', 'mkrCd', 'result.mkrCd', false, null, false, true, true,
    	            'result.hakkoDay', null, null, null);
    	    
    	    var e = angular.element(element);
    	    var children = element.children();
    	    expect(children.length).toEqual(2);
    	    
    	    var divLabel = children[0];
            expect(divLabel.hasAttribute('class')).toBe(true);

            var divElement = angular.element(e.children()[0]);
            expect(divElement.is('div')).toBe(true);
            expect(divElement.hasClass('cs-label')).toBe(true);
            expect(divElement.hasClass('cs-label-large')).toBe(true);
            expect(divElement.text()).toEqual('メーカー');

            expect(divElement.has('span').length).toEqual(0);
            
            var input1 = children[1];
            expect(input1.hasAttribute('required')).toBe(false);
            
            var input1Elt = angular.element(e.children()[1]);

            expect(input1Elt.is('input')).toBe(true);
            expect(input1Elt.hasClass('ng-valid')).toBe(true);
    	});
    	
    	it('test render html: partition="3", cc-readonly=true, cc-shortname=false, have model2 ', function() {
            compile('メーカー', '3', 'mkrCd', 'mkrCd', 'result.mkrCd', null, true, false, true, false,
                    'result.hakkoDay', 'mkrNm', 'mkrNm', 'result.mkrNm');
            
            var e = angular.element(element);
            var children = element.children();
            expect(children.length).toEqual(3);

            var divLabel = children[0];
            expect(divLabel.hasAttribute('class')).toBe(true);

            var divElement = angular.element(e.children()[0]);
            expect(divElement.is('div')).toBe(true);
            expect(divElement.hasClass('cs-label')).toBe(true);
            expect(divElement.hasClass('cs-label-large')).toBe(true);
            expect(divElement.text()).toEqual('メーカー');

            expect(divElement.has('span').length).toEqual(0);

            var input1 = children[1];
            expect(input1.getAttribute('type')).toEqual('text');
            expect(input1.getAttribute('cc-type')).toEqual('code');
            expect(input1.getAttribute('id')).toEqual('mkrCd');
            expect(input1.getAttribute('name')).toEqual('mkrCd');
            expect(input1.getAttribute('ng-model')).toEqual('ngModel');
            expect(input1.hasAttribute('readonly')).toBe(true);
            expect(input1.getAttribute('ng-minlength')).toEqual(undefined);
            expect(input1.getAttribute('maxlength')).toEqual('7');
            expect(input1.hasAttribute('cc-blur-validation')).toBe(false);
            expect(input1.hasAttribute('ui-event')).toBe(false);

            var input1Elt = angular.element(e.children()[1]);

            expect(input1Elt.is('input')).toBe(true);
            expect(input1Elt.hasClass('ng-pristine')).toBe(true);
            expect(input1Elt.hasClass('cs-ime-off')).toBe(true);
            expect(input1Elt.hasClass('cs-num-10')).toBe(true);
            expect(input1Elt.hasClass('ng-valid')).toBe(true);

            var input2 = children[2];
            expect(input2.getAttribute('type')).toEqual('text');
            expect(input2.getAttribute('cc-type')).toEqual('zenkaku');
            expect(input2.getAttribute('id')).toEqual('mkrNm');
            expect(input2.getAttribute('name')).toEqual('mkrNm');
            expect(input2.getAttribute('ng-model')).toEqual('ngModel2');
            expect(input2.hasAttribute('readonly')).toBe(true);
            expect(input2.getAttribute('maxlength')).toEqual('20');
            
            var input2Elt = angular.element(e.children()[2]);
            expect(input2Elt.hasClass('ng-pristine')).toBe(true);
            expect(input2Elt.hasClass('ng-valid')).toBe(true);
            expect(input2Elt.hasClass('cs-ime-on')).toBe(true);
            expect(input2Elt.hasClass('cs-left')).toBe(true);
            expect(input2Elt.hasClass('cs-char-20')).toBe(true);
        });
    	
    	it('test render html: partition="3", cc-readonly=true, cc-shortname=true, have model2 ', function() {
            compile('メーカー', '3', 'mkrCd', 'mkrCd', 'result.mkrCd', null, true, false, true, true,
                    'result.hakkoDay', 'mkrNm', 'mkrNm', 'result.mkrNm');
            
            var e = angular.element(element);
            var children = element.children();
            expect(children.length).toEqual(3);
            
            var input2 = children[2];
            expect(input2.getAttribute('type')).toEqual('text');
            expect(input2.getAttribute('cc-type')).toEqual('zenkaku');
            expect(input2.getAttribute('id')).toEqual('mkrNm');
            expect(input2.getAttribute('name')).toEqual('mkrNm');
            expect(input2.getAttribute('ng-model')).toEqual('ngModel2');
            expect(input2.hasAttribute('readonly')).toBe(true);
            expect(input2.getAttribute('maxlength')).toEqual('5');
            
            var input2Elt = angular.element(e.children()[2]);
            expect(input2Elt.hasClass('ng-pristine')).toBe(true);
            expect(input2Elt.hasClass('ng-valid')).toBe(true);
            expect(input2Elt.hasClass('cs-ime-on')).toBe(true);
            expect(input2Elt.hasClass('cs-left')).toBe(true);
            expect(input2Elt.hasClass('cs-char-5')).toBe(true);
    	});
    });
    
	xdescribe('cc-mkrcd-input: test template partition "2"', function() {
    	it('test render html: partition="2", cc-required=true, cc-shortname=false, have model2 ', function() {
            compile('メーカー', '2', 'mkrCd', 'mkrCd', 'result.mkrCd', true, null, false, true, false,
                    'result.hakkoDay', 'mkrNm', 'mkrNm', 'result.mkrNm');
            
            var e = angular.element(element);
            var children = element.children();
            expect(children.length).toEqual(3);

            var divLabel = children[0];
            expect(divLabel.hasAttribute('class')).toBe(true);

            var divElement = angular.element(e.children()[0]);
            expect(divElement.is('div')).toBe(true);
            expect(divElement.hasClass('cs-label')).toBe(true);
            expect(divElement.hasClass('cs-label-large-required')).toBe(true);
            expect(divElement.text()).toEqual('メーカー *');

            expect(divElement.has('span').length).toEqual(1);
            expect(angular.element(divElement.children('span')[0]).hasClass('cs-txt-required')).toBe(true);

            var input1 = children[1];
            expect(input1.getAttribute('type')).toEqual('text');
            expect(input1.getAttribute('cc-type')).toEqual('code');
            expect(input1.getAttribute('id')).toEqual('mkrCd');
            expect(input1.getAttribute('name')).toEqual('mkrCd');
            expect(input1.getAttribute('ng-model')).toEqual('ngModel');
            expect(input1.hasAttribute('required')).toBe(true);
            expect(input1.getAttribute('ng-minlength')).toEqual('7');
            expect(input1.getAttribute('maxlength')).toEqual('7');
            expect(input1.hasAttribute('cc-blur-validation')).toBe(true);
            expect(input1.hasAttribute('ui-event')).toBe(true);

            var input1Elt = angular.element(e.children()[1]);

            expect(input1Elt.is('input')).toBe(true);
            expect(input1Elt.hasClass('ng-pristine')).toBe(true);
            expect(input1Elt.hasClass('cs-ime-off')).toBe(true);
            expect(input1Elt.hasClass('cs-num-10')).toBe(true);
            expect(input1Elt.hasClass('ng-invalid')).toBe(true);
            expect(input1Elt.hasClass('ng-invalid-required')).toBe(true);
            expect(input1Elt.hasClass('ng-valid-minlength')).toBe(true);

            var input2 = children[2];
            expect(input2.getAttribute('type')).toEqual('text');
            expect(input2.getAttribute('cc-type')).toEqual('zenkaku');
            expect(input2.getAttribute('id')).toEqual('mkrNm');
            expect(input2.getAttribute('name')).toEqual('mkrNm');
            expect(input2.getAttribute('ng-model')).toEqual('ngModel2');
            expect(input2.hasAttribute('readonly')).toBe(true);
            expect(input2.getAttribute('maxlength')).toEqual('20');
            
            var input2Elt = angular.element(e.children()[2]);
            expect(input2Elt.hasClass('ng-pristine')).toBe(true);
            expect(input2Elt.hasClass('ng-valid')).toBe(true);
            expect(input2Elt.hasClass('cs-ime-on')).toBe(true);
            expect(input2Elt.hasClass('cs-left')).toBe(true);
            expect(input2Elt.hasClass('cs-char-20')).toBe(true);
        });
    	
    	it('test render html: partition="2", cc-required=false, cc-shortname=false, have model2 ', function() {
            compile('メーカー', '2', 'mkrCd', 'mkrCd', 'result.mkrCd', false, null, false, true, false,
                    'result.hakkoDay', 'mkrNm', 'mkrNm', 'result.mkrNm');
            
            var e = angular.element(element);
            var children = element.children();
            expect(children.length).toEqual(3);

            var divLabel = children[0];
            expect(divLabel.hasAttribute('class')).toBe(true);

            var divElement = angular.element(e.children()[0]);
            expect(divElement.is('div')).toBe(true);
            expect(divElement.hasClass('cs-label')).toBe(true);
            expect(divElement.hasClass('cs-label-large')).toBe(true);
            expect(divElement.text()).toEqual('メーカー');

            expect(divElement.has('span').length).toEqual(0);

            var input1 = children[1];
            expect(input1.getAttribute('type')).toEqual('text');
            expect(input1.getAttribute('cc-type')).toEqual('code');
            expect(input1.getAttribute('id')).toEqual('mkrCd');
            expect(input1.getAttribute('name')).toEqual('mkrCd');
            expect(input1.getAttribute('ng-model')).toEqual('ngModel');
            expect(input1.hasAttribute('required')).toBe(false);
            expect(input1.getAttribute('ng-minlength')).toEqual('7');
            expect(input1.getAttribute('maxlength')).toEqual('7');
            expect(input1.hasAttribute('cc-blur-validation')).toBe(true);
            expect(input1.hasAttribute('ui-event')).toBe(true);

            var input1Elt = angular.element(e.children()[1]);

            expect(input1Elt.is('input')).toBe(true);
            expect(input1Elt.hasClass('ng-pristine')).toBe(true);
            expect(input1Elt.hasClass('cs-ime-off')).toBe(true);
            expect(input1Elt.hasClass('cs-num-10')).toBe(true);
            expect(input1Elt.hasClass('ng-valid')).toBe(true);
            expect(input1Elt.hasClass('ng-invalid-required')).toBe(false);
            expect(input1Elt.hasClass('ng-valid-minlength')).toBe(true);
        });
    	
    	it('test render html: partition="2", cc-required=true, cc-shortname=true, have model2 ', function() {
            compile('メーカー', '2', 'mkrCd', 'mkrCd', 'result.mkrCd', true, null, false, true, true,
                    'result.hakkoDay', 'mkrNm', 'mkrNm', 'result.mkrNm');
            
            var e = angular.element(element);
            var children = element.children();
            expect(children.length).toEqual(3);

            var divLabel = children[0];
            expect(divLabel.hasAttribute('class')).toBe(true);

            var input2 = children[2];
            expect(input2.getAttribute('type')).toEqual('text');
            expect(input2.getAttribute('cc-type')).toEqual('zenkaku');
            expect(input2.getAttribute('id')).toEqual('mkrNm');
            expect(input2.getAttribute('name')).toEqual('mkrNm');
            expect(input2.getAttribute('ng-model')).toEqual('ngModel2');
            expect(input2.hasAttribute('readonly')).toBe(true);
            expect(input2.getAttribute('maxlength')).toEqual('5');
            
            var input2Elt = angular.element(e.children()[2]);
            expect(input2Elt.hasClass('ng-pristine')).toBe(true);
            expect(input2Elt.hasClass('ng-valid')).toBe(true);
            expect(input2Elt.hasClass('cs-ime-on')).toBe(true);
            expect(input2Elt.hasClass('cs-left')).toBe(true);
            expect(input2Elt.hasClass('cs-char-5')).toBe(true);
        });

    	it('test render html: partition="2", cc-required=true, cc-shortname=false, have model2 ', function() {
            compile('メーカー', '2', 'mkrCd', 'mkrCd', 'result.mkrCd', true, null, false, true, false,
                    'result.hakkoDay', 'mkrNm', 'mkrNm', 'result.mkrNm');
            
            var e = angular.element(element);
            var children = element.children();
            expect(children.length).toEqual(3);

            var divLabel = children[0];
            expect(divLabel.hasAttribute('class')).toBe(true);

            var input2 = children[2];
            expect(input2.getAttribute('type')).toEqual('text');
            expect(input2.getAttribute('cc-type')).toEqual('zenkaku');
            expect(input2.getAttribute('id')).toEqual('mkrNm');
            expect(input2.getAttribute('name')).toEqual('mkrNm');
            expect(input2.getAttribute('ng-model')).toEqual('ngModel2');
            expect(input2.hasAttribute('readonly')).toBe(true);
            expect(input2.getAttribute('maxlength')).toEqual('20');
            
            var input2Elt = angular.element(e.children()[2]);
            expect(input2Elt.hasClass('ng-pristine')).toBe(true);
            expect(input2Elt.hasClass('ng-valid')).toBe(true);
            expect(input2Elt.hasClass('cs-ime-on')).toBe(true);
            expect(input2Elt.hasClass('cs-left')).toBe(true);
            expect(input2Elt.hasClass('cs-char-20')).toBe(true);
        });
    	
    	it('test render html: partition="2", cc-required=true, cc-shortname=false, have no model2 ', function() {
            compile('メーカー', '2', 'mkrCd', 'mkrCd', 'result.mkrCd', true, null, false, true, false,
                    'result.hakkoDay', null, null, null);
            
            var e = angular.element(element);
            var children = element.children();
            expect(children.length).toEqual(2);

            var divLabel = children[0];
            expect(divLabel.hasAttribute('class')).toBe(true);

            var divElement = angular.element(e.children()[0]);
            expect(divElement.is('div')).toBe(true);
            expect(divElement.hasClass('cs-label')).toBe(true);
            expect(divElement.hasClass('cs-label-large-required')).toBe(true);
            expect(divElement.text()).toEqual('メーカー *');

            expect(divElement.has('span').length).toEqual(1);
            expect(angular.element(divElement.children('span')[0]).hasClass('cs-txt-required')).toBe(true);

            var input1 = children[1];
            expect(input1.getAttribute('type')).toEqual('text');
            expect(input1.getAttribute('cc-type')).toEqual('code');
            expect(input1.getAttribute('id')).toEqual('mkrCd');
            expect(input1.getAttribute('name')).toEqual('mkrCd');
            expect(input1.getAttribute('ng-model')).toEqual('ngModel');
            expect(input1.hasAttribute('required')).toBe(true);
            expect(input1.getAttribute('ng-minlength')).toEqual('7');
            expect(input1.getAttribute('maxlength')).toEqual('7');
            expect(input1.hasAttribute('cc-blur-validation')).toBe(true);
            expect(input1.hasAttribute('ui-event')).toBe(false);

            var input1Elt = angular.element(e.children()[1]);

            expect(input1Elt.is('input')).toBe(true);
            expect(input1Elt.hasClass('ng-pristine')).toBe(true);
            expect(input1Elt.hasClass('cs-ime-off')).toBe(true);
            expect(input1Elt.hasClass('cs-num-10')).toBe(true);
            expect(input1Elt.hasClass('ng-invalid')).toBe(true);
            expect(input1Elt.hasClass('ng-invalid-required')).toBe(true);
            expect(input1Elt.hasClass('ng-valid-minlength')).toBe(true);
       });
    	
    	it('test render html: partition="2", cc-readonly=true, cc-shortname=false, have model2 ', function() {
            compile('メーカー', '2', 'mkrCd', 'mkrCd', 'result.mkrCd', null, true, false, true, false,
                    'result.hakkoDay', 'mkrNm', 'mkrNm', 'result.mkrNm');
            
            var e = angular.element(element);
            var children = element.children();
            expect(children.length).toEqual(3);

            var divLabel = children[0];
            expect(divLabel.hasAttribute('class')).toBe(true);

            var divElement = angular.element(e.children()[0]);
            expect(divElement.is('div')).toBe(true);
            expect(divElement.hasClass('cs-label')).toBe(true);
            expect(divElement.hasClass('cs-label-large')).toBe(true);
            expect(divElement.text()).toEqual('メーカー');

            expect(divElement.has('span').length).toEqual(0);

            var input1 = children[1];
            expect(input1.getAttribute('type')).toEqual('text');
            expect(input1.getAttribute('cc-type')).toEqual('code');
            expect(input1.getAttribute('id')).toEqual('mkrCd');
            expect(input1.getAttribute('name')).toEqual('mkrCd');
            expect(input1.getAttribute('ng-model')).toEqual('ngModel');
            expect(input1.hasAttribute('readonly')).toBe(true);
            expect(input1.getAttribute('ng-minlength')).toEqual(undefined);
            expect(input1.getAttribute('maxlength')).toEqual('7');
            expect(input1.hasAttribute('cc-blur-validation')).toBe(false);
            expect(input1.hasAttribute('ui-event')).toBe(false);

            var input1Elt = angular.element(e.children()[1]);

            expect(input1Elt.is('input')).toBe(true);
            expect(input1Elt.hasClass('ng-pristine')).toBe(true);
            expect(input1Elt.hasClass('cs-ime-off')).toBe(true);
            expect(input1Elt.hasClass('cs-num-10')).toBe(true);
            expect(input1Elt.hasClass('ng-valid')).toBe(true);

            var input2 = children[2];
            expect(input2.getAttribute('type')).toEqual('text');
            expect(input2.getAttribute('cc-type')).toEqual('zenkaku');
            expect(input2.getAttribute('id')).toEqual('mkrNm');
            expect(input2.getAttribute('name')).toEqual('mkrNm');
            expect(input2.getAttribute('ng-model')).toEqual('ngModel2');
            expect(input2.hasAttribute('readonly')).toBe(true);
            expect(input2.getAttribute('maxlength')).toEqual('20');
            
            var input2Elt = angular.element(e.children()[2]);
            expect(input2Elt.hasClass('ng-pristine')).toBe(true);
            expect(input2Elt.hasClass('ng-valid')).toBe(true);
            expect(input2Elt.hasClass('cs-ime-on')).toBe(true);
            expect(input2Elt.hasClass('cs-left')).toBe(true);
            expect(input2Elt.hasClass('cs-char-20')).toBe(true);
        });
        
        it('test render html: partition="2", cc-readonly=true, cc-shortname=true, have model2 ', function() {
            compile('メーカー', '2', 'mkrCd', 'mkrCd', 'result.mkrCd', null, true, false, true, true,
                    'result.hakkoDay', 'mkrNm', 'mkrNm', 'result.mkrNm');
            
            var e = angular.element(element);
            var children = element.children();
            expect(children.length).toEqual(3);
            
            var input2 = children[2];
            expect(input2.getAttribute('type')).toEqual('text');
            expect(input2.getAttribute('cc-type')).toEqual('zenkaku');
            expect(input2.getAttribute('id')).toEqual('mkrNm');
            expect(input2.getAttribute('name')).toEqual('mkrNm');
            expect(input2.getAttribute('ng-model')).toEqual('ngModel2');
            expect(input2.hasAttribute('readonly')).toBe(true);
            expect(input2.getAttribute('maxlength')).toEqual('5');
            
            var input2Elt = angular.element(e.children()[2]);
            expect(input2Elt.hasClass('ng-pristine')).toBe(true);
            expect(input2Elt.hasClass('ng-valid')).toBe(true);
            expect(input2Elt.hasClass('cs-ime-on')).toBe(true);
            expect(input2Elt.hasClass('cs-left')).toBe(true);
            expect(input2Elt.hasClass('cs-char-5')).toBe(true);
        });
        
        it('test render html: partition="2", cc-readonly=true, cc-shortname=true, have no model2 ', function() {
            compile('メーカー', '2', 'mkrCd', 'mkrCd', 'result.mkrCd', null, true, false, true, true,
                    'result.hakkoDay', null, null, null);
            
            var e = angular.element(element);
            var children = element.children();
            console.log(children[2]);
            expect(children.length).toEqual(2);
            
            var divLabel = children[0];
            expect(divLabel.hasAttribute('class')).toBe(true);

            var divElement = angular.element(e.children()[0]);
            expect(divElement.is('div')).toBe(true);
            expect(divElement.hasClass('cs-label')).toBe(true);
            expect(divElement.hasClass('cs-label-large')).toBe(true);
            expect(divElement.text()).toEqual('メーカー');

            expect(divElement.has('span').length).toEqual(0);
        });
	});
    xdescribe('cc-mkrcd-input: test template partition ""', function() {
        it('test render html: partition="", cc-required=true, cc-shortname=false, have model2 ', function() {
            compile('メーカー', '', 'mkrCd', 'mkrCd', 'result.mkrCd', true, null, false, true, false,
                    'result.hakkoDay', 'mkrNm', 'mkrNm', 'result.mkrNm');
            
            var e = angular.element(element);
            var children = element.children();
            expect(children.length).toEqual(3);

            var divLabel = children[0];
            expect(divLabel.hasAttribute('class')).toBe(true);

            var divElement = angular.element(e.children()[0]);
            expect(divElement.is('div')).toBe(true);
            expect(divElement.hasClass('cs-label')).toBe(true);
            expect(divElement.hasClass('cs-label-divide-zero')).toBe(true);
            expect(divElement.text()).toEqual('メーカー');

            expect(divElement.has('span').length).toEqual(0);

            var input1 = children[1];
            expect(input1.getAttribute('type')).toEqual('text');
            expect(input1.getAttribute('cc-type')).toEqual('code');
            expect(input1.getAttribute('id')).toEqual('mkrCd');
            expect(input1.getAttribute('name')).toEqual('mkrCd');
            expect(input1.getAttribute('ng-model')).toEqual('ngModel');
            expect(input1.hasAttribute('required')).toBe(true);
            expect(input1.getAttribute('ng-minlength')).toEqual('7');
            expect(input1.getAttribute('maxlength')).toEqual('7');
            expect(input1.hasAttribute('cc-blur-validation')).toBe(true);
            expect(input1.hasAttribute('ui-event')).toBe(true);

            var input1Elt = angular.element(e.children()[1]);

            expect(input1Elt.is('input')).toBe(true);
            expect(input1Elt.hasClass('ng-pristine')).toBe(true);
            expect(input1Elt.hasClass('cs-ime-off')).toBe(true);
            expect(input1Elt.hasClass('cs-num-10')).toBe(true);
            expect(input1Elt.hasClass('ng-invalid')).toBe(true);
            expect(input1Elt.hasClass('ng-invalid-required')).toBe(true);
            expect(input1Elt.hasClass('ng-valid-minlength')).toBe(true);

            var input2 = children[2];
            expect(input2.getAttribute('type')).toEqual('text');
            expect(input2.getAttribute('cc-type')).toEqual('zenkaku');
            expect(input2.getAttribute('id')).toEqual('mkrNm');
            expect(input2.getAttribute('name')).toEqual('mkrNm');
            expect(input2.getAttribute('ng-model')).toEqual('ngModel2');
            expect(input2.hasAttribute('readonly')).toBe(true);
            expect(input2.getAttribute('maxlength')).toEqual('20');
            
            var input2Elt = angular.element(e.children()[2]);
            expect(input2Elt.hasClass('ng-pristine')).toBe(true);
            expect(input2Elt.hasClass('ng-valid')).toBe(true);
            expect(input2Elt.hasClass('cs-ime-on')).toBe(true);
            expect(input2Elt.hasClass('cs-left')).toBe(true);
            expect(input2Elt.hasClass('cs-char-20')).toBe(true);
        });
        
        it('test render html: partition="", cc-required=false, cc-shortname=false, have model2 ', function() {
            compile('メーカー', '', 'mkrCd', 'mkrCd', 'result.mkrCd', false, null, false, true, false,
                    'result.hakkoDay', 'mkrNm', 'mkrNm', 'result.mkrNm');
            
            var e = angular.element(element);
            var children = element.children();
            expect(children.length).toEqual(3);

            var divLabel = children[0];
            expect(divLabel.hasAttribute('class')).toBe(true);

            var divElement = angular.element(e.children()[0]);
            expect(divElement.is('div')).toBe(true);
            expect(divElement.hasClass('cs-label')).toBe(true);
            expect(divElement.hasClass('cs-label-divide-zero')).toBe(true);
            expect(divElement.text()).toEqual('メーカー');

            expect(divElement.has('span').length).toEqual(0);
        });
        
        it('test render html: partition="", cc-readonly=true, cc-shortname=false, have model2 ', function() {
            compile('メーカー', '', 'mkrCd', 'mkrCd', 'result.mkrCd', null, true, false, true, false,
                    'result.hakkoDay', 'mkrNm', 'mkrNm', 'result.mkrNm');
            
            var e = angular.element(element);
            var children = element.children();
            expect(children.length).toEqual(3);

            var divLabel = children[0];
            expect(divLabel.hasAttribute('class')).toBe(true);

            var divElement = angular.element(e.children()[0]);
            expect(divElement.is('div')).toBe(true);
            expect(divElement.hasClass('cs-label')).toBe(true);
            expect(divElement.hasClass('cs-label-divide-zero')).toBe(true);
            expect(divElement.text()).toEqual('メーカー');

            expect(divElement.has('span').length).toEqual(0);
        });
        
        it('test render html: partition="", cc-required=false, cc-shortname=false, have model2 ', function() {
            compile('メーカー', '', 'mkrCd', 'mkrCd', 'result.mkrCd', null, false, false, true, false,
                    'result.hakkoDay', 'mkrNm', 'mkrNm', 'result.mkrNm');
            
            var e = angular.element(element);
            var children = element.children();
            expect(children.length).toEqual(3);

            var divLabel = children[0];
            expect(divLabel.hasAttribute('class')).toBe(true);

            var divElement = angular.element(e.children()[0]);
            expect(divElement.is('div')).toBe(true);
            expect(divElement.hasClass('cs-label')).toBe(true);
            expect(divElement.hasClass('cs-label-divide-zero')).toBe(true);
            expect(divElement.text()).toEqual('メーカー');

            expect(divElement.has('span').length).toEqual(0);
        });
        
        it('test render html: partition="", cc-required=true, cc-shortname=true, have model2 ', function() {
            compile('メーカー', '', 'mkrCd', 'mkrCd', 'result.mkrCd', true, null, false, true, true,
                    'result.hakkoDay', 'mkrNm', 'mkrNm', 'result.mkrNm');
            
            var e = angular.element(element);
            var children = element.children();
            expect(children.length).toEqual(3);

            var divLabel = children[0];
            expect(divLabel.hasAttribute('class')).toBe(true);

            var divElement = angular.element(e.children()[0]);
            expect(divElement.is('div')).toBe(true);
            expect(divElement.hasClass('cs-label')).toBe(true);
            expect(divElement.hasClass('cs-label-divide-zero')).toBe(true);
            expect(divElement.text()).toEqual('メーカー');

            expect(divElement.has('span').length).toEqual(0);
            
            var input2 = children[2];
            expect(input2.getAttribute('type')).toEqual('text');
            expect(input2.getAttribute('cc-type')).toEqual('zenkaku');
            expect(input2.getAttribute('id')).toEqual('mkrNm');
            expect(input2.getAttribute('name')).toEqual('mkrNm');
            expect(input2.getAttribute('ng-model')).toEqual('ngModel2');
            expect(input2.hasAttribute('readonly')).toBe(true);
            expect(input2.getAttribute('maxlength')).toEqual('5');
            
            var input2Elt = angular.element(e.children()[2]);
            expect(input2Elt.hasClass('ng-pristine')).toBe(true);
            expect(input2Elt.hasClass('ng-valid')).toBe(true);
            expect(input2Elt.hasClass('cs-ime-on')).toBe(true);
            expect(input2Elt.hasClass('cs-left')).toBe(true);
            expect(input2Elt.hasClass('cs-char-5')).toBe(true);
        });
        
        it('test render html: partition="", cc-required=true, cc-shortname=false, have no model2 ', function() {
            compile('メーカー', '', 'mkrCd', 'mkrCd', 'result.mkrCd', true, null, false, true, false,
                    'result.hakkoDay', null, null, null);
            
            var e = angular.element(element);
            var children = element.children();
            expect(children.length).toEqual(2);

            var divLabel = children[0];
            expect(divLabel.hasAttribute('class')).toBe(true);

            var divElement = angular.element(e.children()[0]);
            expect(divElement.is('div')).toBe(true);
            expect(divElement.hasClass('cs-label')).toBe(true);
            expect(divElement.hasClass('cs-label-divide-zero')).toBe(true);
            expect(divElement.text()).toEqual('メーカー');

            expect(divElement.has('span').length).toEqual(0);

            var input1 = children[1];
            expect(input1.getAttribute('type')).toEqual('text');
            expect(input1.getAttribute('cc-type')).toEqual('code');
            expect(input1.getAttribute('id')).toEqual('mkrCd');
            expect(input1.getAttribute('name')).toEqual('mkrCd');
            expect(input1.getAttribute('ng-model')).toEqual('ngModel');
            expect(input1.hasAttribute('required')).toBe(true);
            expect(input1.getAttribute('ng-minlength')).toEqual('7');
            expect(input1.getAttribute('maxlength')).toEqual('7');
            expect(input1.hasAttribute('cc-blur-validation')).toBe(true);
            expect(input1.hasAttribute('ui-event')).toBe(false);

            var input1Elt = angular.element(e.children()[1]);

            expect(input1Elt.is('input')).toBe(true);
            expect(input1Elt.hasClass('ng-pristine')).toBe(true);
            expect(input1Elt.hasClass('cs-ime-off')).toBe(true);
            expect(input1Elt.hasClass('cs-num-10')).toBe(true);
            expect(input1Elt.hasClass('ng-invalid')).toBe(true);
            expect(input1Elt.hasClass('ng-invalid-required')).toBe(true);
            expect(input1Elt.hasClass('ng-valid-minlength')).toBe(true);
        });
    });
    
    xdescribe('cc-mkrcd-input: test logic', function() {
        it('it should catch required error', function() {
            compile('メーカー', '3', 'mkrCd', 'mkrCd', 'result.mkrCd', true, null, false, true, false,
                    'result.hakkoDay', 'mkrNm', 'mkrNm', 'result.mkrNm');
            
            var input1 = angular.element(element.children()[1]);
            var inputCtrl1 = input1.controller('ngModel');
            expect(inputCtrl1.$invalid).toBe(true);
            expect(inputCtrl1.$error.required).toBe(true);

            input1.triggerHandler('focus');
            changeInputValue(input1, '');
            input1.triggerHandler('blur');
            expect(input1.val()).toEqual('');
            expect(scope.result.mkrCd).toEqual(undefined);
            // it should catch required validity
            expect(inputCtrl1.$invalid).toBe(true);
            expect(inputCtrl1.$error.required).toBe(true);

            input1.triggerHandler('focus');
            expect(input1.val()).toEqual('');
        });

        it('it should catch invalid input,minlength error, no required error', function() {
            compile('メーカー', '3', 'mkrCd', 'mkrCd', 'result.mkrCd', true, null, false, true, false,
                    'result.hakkoDay', 'mkrNm', 'mkrNm', 'result.mkrNm');
            
            var input1 = angular.element(element.children()[1]);
            var inputCtrl1 = input1.controller('ngModel');
            expect(inputCtrl1.$invalid).toBe(true);
            expect(inputCtrl1.$error.required).toBe(true);
            
            input1.triggerHandler('focus');
            changeInputValue(input1, ' 1-,a%');
            input1.triggerHandler('blur');
            expect(input1.val()).toEqual('1');
            expect(scope.result.mkrCd).toEqual(undefined);
            // it should catch required validity
            expect(inputCtrl1.$invalid).toBe(true);
            expect(inputCtrl1.$error.required).toBe(false);
            expect(inputCtrl1.$error.minlength).toBe(true);
            
            input1.triggerHandler('focus');
            expect(input1.val()).toEqual('1');
        });
        
        it('it should bind valid input no error', function() {
            compile('メーカー', '3', 'mkrCd', 'mkrCd', 'result.mkrCd', true, null, false, true, false,
                    'result.hakkoDay', 'mkrNm', 'mkrNm', 'result.mkrNm');
            
            var input1 = angular.element(element.children()[1]);
            var inputCtrl1 = input1.controller('ngModel');
            expect(inputCtrl1.$invalid).toBe(true);
            expect(inputCtrl1.$error.required).toBe(true);
            
            input1.triggerHandler('focus');
            changeInputValue(input1, '1234567');
            input1.triggerHandler('blur');
            expect(input1.val()).toEqual('1234567');
            expect(scope.result.mkrCd).toEqual('1234567');
            // it should catch required validity
            expect(inputCtrl1.$invalid).toBe(false);
            expect(inputCtrl1.$error.required).toBe(false);
            expect(inputCtrl1.$error.minlength).toBe(false);
            
            input1.triggerHandler('focus');
            expect(input1.val()).toEqual('1234567');
        });

        it('it should bind valid input no error', function() {
            compile('メーカー', '3', 'mkrCd', 'mkrCd', 'result.mkrCd', true, null, false, true, false,
                    'result.hakkoDay', 'mkrNm', 'mkrNm', 'result.mkrNm');
            
            var input1 = angular.element(element.children()[1]);
            var inputCtrl1 = input1.controller('ngModel');
            
            expect(inputCtrl1.$invalid).toBe(true);
            expect(inputCtrl1.$error.required).toBe(true);
            
            changeInputValue(input1, '1234567');
            expect(input1.val()).toEqual('1234567');
            expect(scope.result.mkrCd).toEqual('1234567');
            // it should catch required validity
            expect(inputCtrl1.$invalid).toBe(false);
            expect(inputCtrl1.$error.required).toBe(false);
            expect(inputCtrl1.$error.minlength).toBe(false);
            
            input1.triggerHandler('focus');
            expect(input1.val()).toEqual('1234567');
        });
    });
    
    describe('cc-mkrcd-input: test request server', function() {
        it('it should get empty data - ccExist false', function() {
            compile('メーカー', '3', 'mkrCd', 'mkrCd', 'result.mkrCd', true, null, false, true, false,
                    'result.hakkoDay', 'mkrNm', 'mkrNm', 'result.mkrNm');
            
            var input1 = angular.element(element.children()[1]);
            var inputCtrl1 = input1.controller('ngModel');
            var input2 = angular.element(element.children()[2]);
            var inputCtrl2 = input2.controller('ngModel');

            changeInputValue(input1, '0000000');
            $httpBackend.flush();
            input1.triggerHandler('blur');
            expect(input1.val()).toEqual('0000000');
            expect(scope.result.mkrCd).toEqual('0000000');

            expect(input2.val()).toEqual('');
            expect(scope.result.mkrNm).toEqual(undefined);

            console.log(input1);
            expect(input1.hasClass('ng-invalid-cc-exist')).toBe(true);
            expect(inputCtrl1.$invalid).toBe(true);
            expect(inputCtrl1.$error.ccExist).toBe(true);
            
        });
        
        it('it should get empty data - ccExist true', function() {
            compile('メーカー', '3', 'mkrCd', 'mkrCd', 'result.mkrCd', true, null, true, true, false,
                    'result.hakkoDay', 'mkrNm', 'mkrNm', 'result.mkrNm');

            var input1 = angular.element(element.children()[1]);
            var inputCtrl1 = input1.controller('ngModel');
            var input2 = angular.element(element.children()[2]);
            var inputCtrl2 = input2.controller('ngModel');

            changeInputValue(input1, '0000000');
            $httpBackend.flush();
            input1.triggerHandler('blur');
            expect(input1.val()).toEqual('0000000');
            expect(scope.result.mkrCd).toEqual('0000000');

            expect(input2.val()).toEqual('');
            expect(scope.result.mkrNm).toEqual(undefined);

            input1.triggerHandler('focus');
            input1.triggerHandler('blur');
            expect(input1.hasClass('ng-valid-cc-exist')).toBe(true);
            expect(inputCtrl1.$invalid).toBe(false);
            expect(inputCtrl1.$error.ccExist).toBe(false);
            
        });
        
        it('it should get data - ccExist false, shortname=false', function() {
            compile('メーカー', '3', 'mkrCd', 'mkrCd', 'result.mkrCd', true, null, false, true, false,
                    'result.hakkoDay', 'mkrNm', 'mkrNm', 'result.mkrNm');
            
            var input1 = angular.element(element.children()[1]);
            var inputCtrl1 = input1.controller('ngModel');
            var input2 = angular.element(element.children()[2]);
            var inputCtrl2 = input2.controller('ngModel');
            
            changeInputValue(input1, '7654321');
            $httpBackend.flush();
            input1.triggerHandler('blur');
            expect(input1.val()).toEqual('7654321');
            expect(scope.result.mkrCd).toEqual('7654321');
            
            expect(inputCtrl1.$invalid).toBe(false);
            expect(inputCtrl1.$error.ccExist).toBe(false);
            
            expect(input2.val()).toEqual(record.name);
            expect(scope.result.mkrNm).toEqual(record.name);
            
        });
        
        it('it should get data - ccExist true, shortname=true', function() {
            compile('メーカー', '3', 'mkrCd', 'mkrCd', 'result.mkrCd', true, null, true, true, true,
                    'result.hakkoDay', 'mkrNm', 'mkrNm', 'result.mkrNm');
            
            var input1 = angular.element(element.children()[1]);
            var inputCtrl1 = input1.controller('ngModel');
            var input2 = angular.element(element.children()[2]);
            var inputCtrl2 = input2.controller('ngModel');
            
            changeInputValue(input1, '7654321');
            $httpBackend.flush();
            input1.triggerHandler('blur');
            expect(input1.val()).toEqual('7654321');
            expect(scope.result.mkrCd).toEqual('7654321');
            
            expect(inputCtrl1.$invalid).toBe(false);
            expect(inputCtrl1.$error.ccExist).toBe(false);
            
            expect(input2.val()).toEqual(record.shortName);
            expect(scope.result.mkrNm).toEqual(record.shortName);
        });
    });
});
