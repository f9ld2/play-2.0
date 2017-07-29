describe('Test cc-oyakkkcd-input', function() {
    var element, scope, $compile, $httpBackend, changeInputValue, compile, templateHtml;

    var record1 = {
            code : '00001',
            name : '１２３４５６７８９０１２３４５）',
            kubun : '2'
        };
        
    var record2 = {
            code : '00002',
            name : '１２３４５６７８９０１２３４５）',
            kubun : '9'
        };

    beforeEach(module('directives'));

    beforeEach(inject(function($injector, $sniffer) {
        // inject $compile & $roorScope
        $compile = $injector.get('$compile');
        scope = $injector.get('$rootScope');
        
        $httpBackend = $injector.get('$httpBackend');
        
        
        $httpBackend.when('GET', '/core/codename/t005okkm/00001/00/90/2014').respond([record1]);
        $httpBackend.when('GET', '/core/codename/t005okkm/00002/00/90/2014').respond([record2]);
        $httpBackend.when('GET', '/core/codename/t005okkm/10001/00/90/2014').respond([]);

        scope.result = {
            oyaKikakuCd : "",
            oyaKikakuNm : "",
            kaisyaCd : "00",
            jigyobuCd : "90",
            nendo : "2014"
        };

        // call browser change input value
        changeInputValue = function(elm, value) {
            elm.val(value);
            browserTrigger(elm, $sniffer.hasEvent('input') ? 'input' : 'change');
        };

        // compile directive need to test.
        compile = function(label, partition, id, name, ngModel, required, readOnly, notExist, delexist, 
                para01, para02, para03, id2, name2, ngModel2) {

            var htmlText = '<cc-oyakkkcd-input' + ' cc-label="' + label + '" cc-partition="' + partition 
                    + '" id="' + id + '" name="' + name + '" ng-model="' + ngModel
                    + ((required != null) ? ('" cc-required="' + required) : "")
                    + ((readOnly != null && readOnly == "true") ? '" cc-readonly="true' : "")
                    + ((notExist != null) ? ('" cc-notexist="' + notExist) : "") + '" cc-delexist="' + delexist
                    + '" cc-parameter01="' + para01 + '" cc-parameter02="' + para02 + '" cc-parameter03="' + para03 
                    + '" id2="' + id2 + '" name2="' + name2 + '" ng-model2="' + ngModel2 + '" />';

            element = $compile(htmlText)(scope);
            scope.$digest();
            
        };
    }));

    describe('display html correctly cases', function() {

        describe('1st div cases', function() {
            it("should render html of 1st div correctly with required set to true", function() {
                compile("親企画", "3", "oyaKikakuCd", "oyaKikakuCd", "result.oyaKikakuCd", "true", null, "true", "false", 
                        "result.kaisyaCd", "result.jigyobuCd", "result.nendo", "oyaKikakuNm", "oyaKikakuNm", "result.oyaKikakuNm");
                expect(element.find('div').length).toEqual(1);
                expect(element.find('div').eq(0).hasClass("cs-label-large-required cs-label")).toEqual(true);
                expect(element.find('div').eq(0).clone().children().remove().end().text()).toEqual("親企画");
                expect(element.find('div span').length).toEqual(1);
                expect(element.find('div span').eq(0).hasClass("cs-txt-required")).toEqual(true);
                expect(element.find('div span').eq(0).text()).toEqual(" *");
            });

            it("should render html of 1st div correctly with required set to false", function() {
                compile("親企画", "3", "oyaKikakuCd", "oyaKikakuCd", "result.oyaKikakuCd", 'false', null, "true", "false", 
                        "result.kaisyaCd", "result.jigyobuCd", "result.nendo", "oyaKikakuNm", "oyaKikakuNm", "result.oyaKikakuNm");
                expect(element.find('div').length).toEqual(1);
                expect(element.find('div').eq(0).hasClass("cs-label-large cs-label")).toEqual(true);
                expect(element.find('div').eq(0).clone().children().remove().end().text()).toEqual("親企画");
                expect(element.find('div span').length).not.toEqual(1);
            });

            it("should render html of 1st div correctly with readonly set to true", function() {
                compile("親企画", "3", "oyaKikakuCd", "oyaKikakuCd", "result.oyaKikakuCd", null, "true", "true", "false", 
                        "result.kaisyaCd", "result.jigyobuCd", "result.nendo", "oyaKikakuNm", "result.oyaKikakuNm");
                expect(element.find('div').length).toEqual(1);
                expect(element.find('div').eq(0).hasClass("cs-label-large cs-label")).toEqual(true);
                expect(element.find('div').eq(0).clone().children().remove().end().text()).toEqual("親企画");
                expect(element.find('div span').length).not.toEqual(1);
            });

            it("should render html of 1st div correctly with partition set to empty", function() {
                compile("親企画", "", "oyaKikakuCd", "oyaKikakuCd", "result.oyaKikakuCd", "true", null, "true", "false", 
                        "result.kaisyaCd", "result.jigyobuCd", "result.nendo", "oyaKikakuNm", "oyaKikakuNm", "result.oyaKikakuNm");
                expect(element.find('div').length).toEqual(1);
                expect(element.find('div').eq(0).hasClass("cs-label-divide-zero cs-label")).toEqual(true);
                expect(element.find('div').eq(0).clone().children().remove().end().text()).not.toEqual("親企画");
                expect(element.find('div span').length).not.toEqual(1);
            });

        });

        describe('1st input field', function() {

            it("should render html correctly when required", function() {
                compile("親企画", "3", "oyaKikakuCd", "oyaKikakuCd", "result.oyaKikakuCd", "true", null, "true", "false", 
                        "result.kaisyaCd", "result.jigyobuCd", "result.nendo", "oyaKikakuNm", "oyaKikakuNm", "result.oyaKikakuNm");
                expect(element.find('input').length).toEqual(2);
                expect(element.find('input').eq(0).attr('type')).toEqual("text");
                expect(element.find('input').eq(0).attr('cc-type')).toEqual("code");
                expect(element.find('input').eq(0).attr('id')).toEqual("oyaKikakuCd");
                expect(element.find('input').eq(0).attr('name')).toEqual("oyaKikakuCd");
                expect(element.find('input').eq(0).attr('ng-model')).toEqual("ngModel");
                expect(element.find('input').eq(0).attr('required')).not.toEqual(undefined);
                expect(element.find('input').eq(0).attr('readonly')).toEqual(undefined);
                expect(element.find('input').eq(0).attr('ng-minlength')).toEqual("5");
                expect(element.find('input').eq(0).attr('cc-blur-validation')).not.toEqual(undefined);
                expect(element.find('input').eq(0).attr('ui-event')).toEqual("{ blur : 'blurCallback(ngModel)' }");
                expect(element.find('input').eq(0).attr('maxlength')).toEqual("5");
            });

            it("should render html correctly when readonly", function() {
                compile("親企画", "3", "oyaKikakuCd", "oyaKikakuCd", "result.oyaKikakuCd", null, "true", null, "false", 
                        "result.kaisyaCd", "result.jigyobuCd", "result.nendo", "oyaKikakuNm", "oyaKikakuNm", "result.oyaKikakuNm");
                expect(element.find('input').eq(0).attr('required')).toEqual(undefined);
                expect(element.find('input').eq(0).attr('readonly')).not.toEqual(undefined);
                expect(element.find('input').eq(0).attr('ng-minlength')).toEqual(undefined);
                expect(element.find('input').eq(0).attr('cc-blur-validation')).toEqual(undefined);
                expect(element.find('input').eq(0).attr('ui-event')).toEqual(undefined);
            });

        });

        describe('2nd input field', function() {

            it("should render html correctly", function() {
                compile("親企画", "3", "oyaKikakuCd", "oyaKikakuCd", "result.oyaKikakuCd", "true", null, "true", "false", 
                        "result.kaisyaCd", "result.jigyobuCd", "result.nendo", "oyaKikakuNm", "oyaKikakuNm", "result.oyaKikakuNm");
                expect(element.find('input').eq(1).attr('type')).toEqual("text");
                expect(element.find('input').eq(1).attr('cc-type')).toEqual("zenkaku");
                expect(element.find('input').eq(1).attr('id')).toEqual("oyaKikakuNm");
                expect(element.find('input').eq(1).attr('name')).toEqual("oyaKikakuNm");
                expect(element.find('input').eq(1).attr('ng-model')).toEqual("ngModel2");
                expect(element.find('input').eq(1).attr('readonly')).not.toEqual(undefined);
                expect(element.find('input').eq(1).attr('maxlength')).toEqual("15");
                expect(element.find('input').eq(1).hasClass('ng-pristine ng-valid cs-ime-on cs-left cs-char-15')).toEqual(true);
            });

        });
    });

     describe("logic cases", function() {
        it('it should bind value and view value correctly', function() {
            compile("親企画", "3", "oyaKikakuCd", "oyaKikakuCd", "result.oyaKikakuCd", "true", null, "true", "true", 
                    "result.kaisyaCd", "result.jigyobuCd", "result.nendo", "oyaKikakuNm", "oyaKikakuNm", "result.oyaKikakuNm");
            inputElement = element.find('input').eq(0);
            var modelCtrl = inputElement.controller('ngModel');

            inputElement.triggerHandler('focus');
            changeInputValue(inputElement, '00002');
            $httpBackend.flush();

            inputElement.triggerHandler('blur');
            expect(inputElement.val()).toEqual('00002');
            expect(scope.result.oyaKikakuCd).toEqual('00002');
            expect(modelCtrl.$valid).toBe(true);

            inputElement.triggerHandler('focus');
            expect(inputElement.val()).toEqual('00002');
            expect(scope.result.oyaKikakuCd).toEqual('00002');
        });

        it("it should be catch required validation", function() {
            compile("親企画", "3", "oyaKikakuCd", "oyaKikakuCd", "result.oyaKikakuCd", "true", null, "true", "true", 
                    "result.kaisyaCd", "result.jigyobuCd", "result.nendo", "oyaKikakuNm", "oyaKikakuNm", "result.oyaKikakuNm");
            inputElement = element.find('input').eq(0);
            var modelCtrl = inputElement.controller('ngModel');

            inputElement.triggerHandler('focus');
            changeInputValue(inputElement, '');
            inputElement.triggerHandler('blur');
            expect(scope.result.oyaKikakuCd).toEqual(undefined);
            expect(modelCtrl.$invalid).toBe(true);
            expect(modelCtrl.$error.required).toBe(true);
        });

        it('it should be catch minlength validation ', function() {
            compile("親企画", "3", "oyaKikakuCd", "oyaKikakuCd", "result.oyaKikakuCd", "true", null, "true", "true", 
                    "result.kaisyaCd", "result.jigyobuCd", "result.nendo", "oyaKikakuNm", "oyaKikakuNm", "result.oyaKikakuNm");
             inputElement = element.find('input').eq(0);
             var modelCtrl = inputElement.controller('ngModel');

             inputElement.triggerHandler('focus');
             changeInputValue(inputElement, '0000');

             inputElement.triggerHandler('blur');
             expect(scope.result.oyaKikakuCd).toEqual(undefined);
             expect(modelCtrl.$invalid).toBe(true);
             expect(modelCtrl.$error.minlength).toBe(true);
        });

        it('it should populate input no2 with data ', function() {
            compile("親企画", "3", "oyaKikakuCd", "oyaKikakuCd", "result.oyaKikakuCd", "true", null, "true", "true", 
                    "result.kaisyaCd", "result.jigyobuCd", "result.nendo", "oyaKikakuNm", "oyaKikakuNm", "result.oyaKikakuNm");
            inputElement = element.find('input').eq(0);
            inputElement2 = element.find('input').eq(1);
            var modelCtrl = inputElement.controller('ngModel');

            inputElement.triggerHandler('focus');
            changeInputValue(inputElement, '00001');
            $httpBackend.flush()

            inputElement.triggerHandler('blur');
            expect(inputElement2.val()).toEqual('１２３４５６７８９０１２３４５）');
            expect(scope.result.oyaKikakuNm).toEqual('１２３４５６７８９０１２３４５）');
        });

        it('it should not populate input no2 with data and display error message when there is no such data in database and notExist set to false', function() {
            compile("親企画", "3", "oyaKikakuCd", "oyaKikakuCd", "result.oyaKikakuCd", "true", null, "false", "false", 
                    "result.kaisyaCd", "result.jigyobuCd", "result.nendo", "oyaKikakuNm", "oyaKikakuNm", "result.oyaKikakuNm");
            inputElement = element.find('input').eq(0);
            inputElement2 = element.find('input').eq(1);
            var modelCtrl = inputElement.controller('ngModel');
            inputElement.triggerHandler('focus');
            changeInputValue(inputElement, '10001');
            $httpBackend.flush()
            inputElement.triggerHandler('blur');
            expect(inputElement2.val()).toEqual('');
            expect(scope.result.oyaKikakuNm).toEqual(undefined);
            expect(modelCtrl.$invalid).toBe(true);
            expect(modelCtrl.$error.ccExist).toBe(true);
        });

        it('it should not populate input no2 with data and not display message when there is no such data in database and notExist set to true', function() {
            compile("親企画", "3", "oyaKikakuCd", "oyaKikakuCd", "result.oyaKikakuCd", "true", null, "true", "false", 
                    "result.kaisyaCd", "result.jigyobuCd", "result.nendo", "oyaKikakuNm", "oyaKikakuNm", "result.oyaKikakuNm");
            inputElement = element.find('input').eq(0);
            inputElement2 = element.find('input').eq(1);
            var modelCtrl = inputElement.controller('ngModel');
            inputElement.triggerHandler('focus');
            changeInputValue(inputElement, '10001');
            $httpBackend.flush()
            inputElement.triggerHandler('blur');
            expect(inputElement2.val()).toEqual('');
            expect(scope.result.oyaKikakuNm).toEqual(undefined);
            expect(modelCtrl.$invalid).toBe(false);
            expect(modelCtrl.$error.ccExist).toBe(false);
        });

        it('it should not populate input no2 with data and display message when there is such data with kubun 9 and notDelexist set to false', function() {
            compile("親企画", "3", "oyaKikakuCd", "oyaKikakuCd", "result.oyaKikakuCd", "true", null, "true", "false", 
                    "result.kaisyaCd", "result.jigyobuCd", "result.nendo", "oyaKikakuNm", "oyaKikakuNm", "result.oyaKikakuNm");
            inputElement = element.find('input').eq(0);
            inputElement2 = element.find('input').eq(1);
            var modelCtrl = inputElement.controller('ngModel');
            inputElement.triggerHandler('focus');
            changeInputValue(inputElement, '00002');
            $httpBackend.flush()
            inputElement.triggerHandler('blur');
            expect(inputElement2.val()).toEqual('');
            expect(scope.result.oyaKikakuNm).toEqual(undefined);
            expect(modelCtrl.$invalid).toBe(true);
            expect(modelCtrl.$error.ccExist).toBe(true);
        });

    });
});
