describe('Test cc-bnkstncd-input', function() {
    var element, scope, $compile, $httpBackend, changeInputValue, compile, templateHtml;

    var record1 = {
            code : '001',
            name : '１２３４５）',
            kubun : '2'
        };
        
    var record2 = {
            code : '002',
            name : '１２３４５）',
            kubun : '9'
        };

    beforeEach(module('directives'));

    beforeEach(inject(function($injector, $sniffer) {
        // inject $compile & $roorScope
        $compile = $injector.get('$compile');
        scope = $injector.get('$rootScope');
        
        $httpBackend = $injector.get('$httpBackend');
        
        
        $httpBackend.when('GET', '/core/codename/m018bnkm/001/0001?hakkoDay=19010101').respond([record1]);
        $httpBackend.when('GET', '/core/codename/m018bnkm/002/0001?hakkoDay=19010101').respond([record2]);
        $httpBackend.when('GET', '/core/codename/m018bnkm/101/0001?hakkoDay=19010101').respond([]);

        scope.result = {
            bnkStnCd : "",
            bnkBoNmR : "",
            bnkCd : "0001",
            hakkoDay : ""
        };

        // call browser change input value
        changeInputValue = function(elm, value) {
            elm.val(value);
            browserTrigger(elm, $sniffer.hasEvent('input') ? 'input' : 'change');
        };

        // compile directive need to test.
        compile = function(label, partition, id, name, ngModel, required, readOnly, notExist, delexist, 
                para01, para02, id2, name2, ngModel2) {

            var htmlText = '<cc-bnkstncd-input' + ' cc-label="' + label + '" cc-partition="' + partition 
                    + '" id="' + id + '" name="' + name + '" ng-model="' + ngModel
                    + ((required != null) ? ('" cc-required="' + required) : "")
                    + ((readOnly != null && readOnly == "true") ? '" cc-readonly="true' : "")
                    + ((notExist != null) ? ('" cc-notexist="' + notExist) : "") + '" cc-delexist="' + delexist
                    + '" cc-parameter01="' + para01 + '" cc-parameter02="' + para02
                    + '" id2="' + id2 + '" name2="' + name2 + '" ng-model2="' + ngModel2 + '" />';

            element = $compile(htmlText)(scope);
            scope.$digest();
            
        };
    }));

    describe('display html correctly cases', function() {

        describe('1st div cases', function() {
            it("should render html of 1st div correctly with required set to true", function() {
                compile("支店", "3", "bnkStnCd", "bnkStnCd", "result.bnkStnCd", "true", null, "true", "false", 
                        "result.bnkCd", "result.hakkoDay", "bnkBoNmR", "bnkBoNmR", "result.bnkBoNmR");
                expect(element.find('div').length).toEqual(1);
                expect(element.find('div').eq(0).hasClass("cs-label-large-required cs-label")).toEqual(true);
                expect(element.find('div').eq(0).clone().children().remove().end().text()).toEqual("支店");
                expect(element.find('div span').length).toEqual(1);
                expect(element.find('div span').eq(0).hasClass("cs-txt-required")).toEqual(true);
                expect(element.find('div span').eq(0).text()).toEqual(" *");
            });

            it("should render html of 1st div correctly with required set to false", function() {
                compile("支店", "3", "bnkStnCd", "bnkStnCd", "result.bnkStnCd", 'false', null, "true", "false", 
                        "result.bnkCd", "result.hakkoDay", "bnkBoNmR", "bnkBoNmR", "result.bnkBoNmR");
                expect(element.find('div').length).toEqual(1);
                expect(element.find('div').eq(0).hasClass("cs-label-large cs-label")).toEqual(true);
                expect(element.find('div').eq(0).clone().children().remove().end().text()).toEqual("支店");
                expect(element.find('div span').length).not.toEqual(1);
            });

            it("should render html of 1st div correctly with readonly set to true", function() {
                compile("支店", "3", "bnkStnCd", "bnkStnCd", "result.bnkStnCd", null, "true", "true", "false", 
                        "result.bnkCd", "result.hakkoDay", "bnkBoNmR", "result.bnkBoNmR");
                expect(element.find('div').length).toEqual(1);
                expect(element.find('div').eq(0).hasClass("cs-label-large cs-label")).toEqual(true);
                expect(element.find('div').eq(0).clone().children().remove().end().text()).toEqual("支店");
                expect(element.find('div span').length).not.toEqual(1);
            });

            it("should render html of 1st div correctly with partition set to empty", function() {
                compile("支店", "", "bnkStnCd", "bnkStnCd", "result.bnkStnCd", "true", null, "true", "false", 
                        "result.bnkCd", "result.hakkoDay", "bnkBoNmR", "bnkBoNmR", "result.bnkBoNmR");
                expect(element.find('div').length).toEqual(1);
                expect(element.find('div').eq(0).hasClass("cs-label-divide-zero cs-label")).toEqual(true);
                expect(element.find('div').eq(0).clone().children().remove().end().text()).not.toEqual("支店");
                expect(element.find('div span').length).not.toEqual(1);
            });

        });

        describe('1st input field', function() {

            it("should render html correctly when required", function() {
                compile("支店", "3", "bnkStnCd", "bnkStnCd", "result.bnkStnCd", "true", null, "true", "false", 
                        "result.bnkCd", "result.hakkoDay", "bnkBoNmR", "bnkBoNmR", "result.bnkBoNmR");
                expect(element.find('input').length).toEqual(2);
                expect(element.find('input').eq(0).attr('type')).toEqual("text");
                expect(element.find('input').eq(0).attr('cc-type')).toEqual("code");
                expect(element.find('input').eq(0).attr('id')).toEqual("bnkStnCd");
                expect(element.find('input').eq(0).attr('name')).toEqual("bnkStnCd");
                expect(element.find('input').eq(0).attr('ng-model')).toEqual("ngModel");
                expect(element.find('input').eq(0).attr('required')).not.toEqual(undefined);
                expect(element.find('input').eq(0).attr('readonly')).toEqual(undefined);
                expect(element.find('input').eq(0).attr('ng-minlength')).toEqual("3");
                expect(element.find('input').eq(0).attr('cc-blur-validation')).not.toEqual(undefined);
                expect(element.find('input').eq(0).attr('ui-event')).toEqual("{ blur : 'blurCallback(ngModel)' }");
                expect(element.find('input').eq(0).attr('maxlength')).toEqual("3");
            });

            it("should render html correctly when readonly", function() {
                compile("支店", "3", "bnkStnCd", "bnkStnCd", "result.bnkStnCd", null, "true", null, "false", 
                        "result.bnkCd", "result.hakkoDay", "bnkBoNmR", "bnkBoNmR", "result.bnkBoNmR");
                expect(element.find('input').eq(0).attr('required')).toEqual(undefined);
                expect(element.find('input').eq(0).attr('readonly')).not.toEqual(undefined);
                expect(element.find('input').eq(0).attr('ng-minlength')).toEqual(undefined);
                expect(element.find('input').eq(0).attr('cc-blur-validation')).toEqual(undefined);
                expect(element.find('input').eq(0).attr('ui-event')).toEqual(undefined);
            });

        });

        describe('2nd input field', function() {

            it("should render html correctly", function() {
                compile("支店", "3", "bnkStnCd", "bnkStnCd", "result.bnkStnCd", "true", null, "true", "false", 
                        "result.bnkCd", "result.hakkoDay", "bnkBoNmR", "bnkBoNmR", "result.bnkBoNmR");
                expect(element.find('input').eq(1).attr('type')).toEqual("text");
                expect(element.find('input').eq(1).attr('cc-type')).toEqual("zenkaku");
                expect(element.find('input').eq(1).attr('id')).toEqual("bnkBoNmR");
                expect(element.find('input').eq(1).attr('name')).toEqual("bnkBoNmR");
                expect(element.find('input').eq(1).attr('ng-model')).toEqual("ngModel2");
                expect(element.find('input').eq(1).attr('readonly')).not.toEqual(undefined);
                expect(element.find('input').eq(1).attr('maxlength')).toEqual("5");
                expect(element.find('input').eq(1).hasClass('ng-pristine ng-valid cs-ime-on cs-left cs-char-5')).toEqual(true);
            });

        });
    });

     describe("logic cases", function() {
        it('it should bind value and view value correctly', function() {
            compile("支店", "3", "bnkStnCd", "bnkStnCd", "result.bnkStnCd", "true", null, "true", "true", 
                    "result.bnkCd", "result.hakkoDay", "bnkBoNmR", "bnkBoNmR", "result.bnkBoNmR");
            inputElement = element.find('input').eq(0);
            var modelCtrl = inputElement.controller('ngModel');

            inputElement.triggerHandler('focus');
            changeInputValue(inputElement, '002');
            $httpBackend.flush();

            inputElement.triggerHandler('blur');
            expect(inputElement.val()).toEqual('002');
            expect(scope.result.bnkStnCd).toEqual('002');
            expect(modelCtrl.$valid).toBe(true);

            inputElement.triggerHandler('focus');
            expect(inputElement.val()).toEqual('002');
            expect(scope.result.bnkStnCd).toEqual('002');
        });

        it("it should be catch required validation", function() {
            compile("支店", "3", "bnkStnCd", "bnkStnCd", "result.bnkStnCd", "true", null, "true", "true", 
                    "result.bnkCd", "result.hakkoDay", "bnkBoNmR", "bnkBoNmR", "result.bnkBoNmR");
            inputElement = element.find('input').eq(0);
            var modelCtrl = inputElement.controller('ngModel');

            inputElement.triggerHandler('focus');
            changeInputValue(inputElement, '');
            inputElement.triggerHandler('blur');
            expect(scope.result.bnkStnCd).toEqual(undefined);
            expect(modelCtrl.$invalid).toBe(true);
            expect(modelCtrl.$error.required).toBe(true);
        });

        it('it should be catch minlength validation ', function() {
            compile("支店", "3", "bnkStnCd", "bnkStnCd", "result.bnkStnCd", "true", null, "true", "true", 
                    "result.bnkCd", "result.hakkoDay", "bnkBoNmR", "bnkBoNmR", "result.bnkBoNmR");
             inputElement = element.find('input').eq(0);
             var modelCtrl = inputElement.controller('ngModel');

             inputElement.triggerHandler('focus');
             changeInputValue(inputElement, '00');

             inputElement.triggerHandler('blur');
             expect(scope.result.bnkStnCd).toEqual(undefined);
             expect(modelCtrl.$invalid).toBe(true);
             expect(modelCtrl.$error.minlength).toBe(true);
        });

        it('it should populate input no2 with data ', function() {
            compile("支店", "3", "bnkStnCd", "bnkStnCd", "result.bnkStnCd", "true", null, "true", "true", 
                    "result.bnkCd", "result.hakkoDay", "bnkBoNmR", "bnkBoNmR", "result.bnkBoNmR");
            inputElement = element.find('input').eq(0);
            inputElement2 = element.find('input').eq(1);
            var modelCtrl = inputElement.controller('ngModel');

            inputElement.triggerHandler('focus');
            changeInputValue(inputElement, '001');
            $httpBackend.flush()

            inputElement.triggerHandler('blur');
            expect(inputElement2.val()).toEqual('１２３４５）');
            expect(scope.result.bnkBoNmR).toEqual('１２３４５）');
        });

        it('it should not populate input no2 with data and display error message when there is no such data in database and notExist set to false', function() {
            compile("支店", "3", "bnkStnCd", "bnkStnCd", "result.bnkStnCd", "true", null, "false", "false", 
                    "result.bnkCd", "result.hakkoDay", "bnkBoNmR", "bnkBoNmR", "result.bnkBoNmR");
            inputElement = element.find('input').eq(0);
            inputElement2 = element.find('input').eq(1);
            var modelCtrl = inputElement.controller('ngModel');
            inputElement.triggerHandler('focus');
            changeInputValue(inputElement, '101');
            $httpBackend.flush()
            inputElement.triggerHandler('blur');
            expect(inputElement2.val()).toEqual('');
            expect(scope.result.bnkBoNmR).toEqual(undefined);
            expect(modelCtrl.$invalid).toBe(true);
            expect(modelCtrl.$error.ccExist).toBe(true);
        });

        it('it should not populate input no2 with data and not display message when there is no such data in database and notExist set to true', function() {
            compile("支店", "3", "bnkStnCd", "bnkStnCd", "result.bnkStnCd", "true", null, "true", "false", 
                    "result.bnkCd", "result.hakkoDay", "bnkBoNmR", "bnkBoNmR", "result.bnkBoNmR");
            inputElement = element.find('input').eq(0);
            inputElement2 = element.find('input').eq(1);
            var modelCtrl = inputElement.controller('ngModel');
            inputElement.triggerHandler('focus');
            changeInputValue(inputElement, '101');
            $httpBackend.flush()
            inputElement.triggerHandler('blur');
            expect(inputElement2.val()).toEqual('');
            expect(scope.result.bnkBoNmR).toEqual(undefined);
            expect(modelCtrl.$invalid).toBe(false);
            expect(modelCtrl.$error.ccExist).toBe(false);
        });

        it('it should not populate input no2 with data and display message when there is such data with kubun 9 and notDelexist set to false', function() {
            compile("支店", "3", "bnkStnCd", "bnkStnCd", "result.bnkStnCd", "true", null, "true", "false", 
                    "result.bnkCd", "result.hakkoDay", "bnkBoNmR", "bnkBoNmR", "result.bnkBoNmR");
            inputElement = element.find('input').eq(0);
            inputElement2 = element.find('input').eq(1);
            var modelCtrl = inputElement.controller('ngModel');
            inputElement.triggerHandler('focus');
            changeInputValue(inputElement, '002');
            $httpBackend.flush()
            inputElement.triggerHandler('blur');
            expect(inputElement2.val()).toEqual('');
            expect(scope.result.bnkBoNmR).toEqual(undefined);
            expect(modelCtrl.$invalid).toBe(true);
            expect(modelCtrl.$error.ccExist).toBe(true);
        });

    });
});
