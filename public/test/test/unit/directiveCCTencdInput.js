describe('Test cc-tencd-input', function() {
    var element, scope, $compile, $httpBackend, changeInputValue, compile, templateHtml;

    var record1 = {
            code : '0090001',
            name : '○急○レ○シ○エ○ル○本○）',
            shortName : '○鮮○量○',
            kubun : '2'
        };
        
    var record2 = {
            code : '0090002',
            name : '○急○レ○シ○エ○ル○本○）',
            shortName : '○鮮○量○',
            kubun : '9'
        };

    var record3 = {
            code : '0090001',
            name : '○急○レ○シ○エ○ル○本○1）',
            shortName : '○鮮○量○',
            kubun : '9'
    };

    beforeEach(module('directives'));

    beforeEach(inject(function($injector, $sniffer) {
        // inject $compile & $roorScope
        $compile = $injector.get('$compile');
        scope = $injector.get('$rootScope');
        
        $httpBackend = $injector.get('$httpBackend');
        
        
        $httpBackend.when('GET', '/core/codename/m006tenm/001/00/90?hakkoDay=19010101').respond([record1]);
        $httpBackend.when('GET', '/core/codename/m006tenm/001/00/90?hakkoDay=20140101').respond([record3]);
        $httpBackend.when('GET', '/core/codename/m006tenm/002/00/90?hakkoDay=19010101').respond([record2]);
        $httpBackend.when('GET', '/core/codename/m006tenm/111/00/90?hakkoDay=19010101').respond([]);

        $httpBackend.when('GET', '/core/codemaster/getUnyoDate').respond("19010101");

        scope.result = {
            tenCd : "",
            tenNm : "",
            kaisyaCd : "00",
            jigyobuCd : "90",
            hakkoDay : ""
        };

        // call browser change input value
        changeInputValue = function(elm, value) {
            elm.val(value);
            browserTrigger(elm, $sniffer.hasEvent('input') ? 'input' : 'change');
        };

        // compile directive need to test.
        compile = function(label, partition, id, name, ngModel, required, readOnly, notExist, delexist, shortName,
                para01, para02, para03, id2, name2, ngModel2) {

            var htmlText = '<cc-tencd-input' + ' cc-label="' + label + '" cc-partition="' + partition + '" id="'
                    + id + '" name="' + name + '" ng-model="' + ngModel
                    + ((required != null) ? ('" cc-required="' + required) : "")
                    + ((readOnly != null && readOnly == "true") ? '" cc-readonly="true' : "")
                    + ((notExist != null) ? ('" cc-notexist="' + notExist) : "") + '" cc-delexist="' + delexist
                    + '" cc-shortname="' + shortName + '" cc-parameter01="' + para01 + '" cc-parameter02="' + para02
                    + '" cc-parameter03="' + para03 + '" id2="' + id2 + '" name2="' + name2 + '" ng-model2="'
                    + ngModel2 + '" />';

            element = $compile(htmlText)(scope);
            scope.$digest();
            
        };
    }));

    describe('display html correctly cases', function() {

        describe('1st div cases', function() {
            it("should render html of 1st div correctly with required set to true", function() {
                compile("店舗", "3", "tenCd", "tenCd", "result.tenCd", "true", null, "true", "false", "false", "result.kaisyaCd",
                        "result.jigyobuCd", "result.hakkoDay", "tenNm", "tenNm", "result.tenNm");
                expect(element.find('div').length).toEqual(1);
                expect(element.find('div').eq(0).hasClass("cs-label-large-required cs-label")).toEqual(true);
                expect(element.find('div').eq(0).clone().children().remove().end().text()).toEqual("店舗");
                expect(element.find('div span').length).toEqual(1);
                expect(element.find('div span').eq(0).hasClass("cs-txt-required")).toEqual(true);
                expect(element.find('div span').eq(0).text()).toEqual(" *");
            });

            it("should render html of 1st div correctly with required set to false", function() {
                compile("店舗", "3", "tenCd", "tenCd", "result.tenCd", 'false', null, "true", "false", "false", "result.kaisyaCd",
                        "result.jigyobuCd", "result.hakkoDay", "tenNm", "tenNm", "result.tenNm");
                expect(element.find('div').length).toEqual(1);
                expect(element.find('div').eq(0).hasClass("cs-label-large cs-label")).toEqual(true);
                expect(element.find('div').eq(0).clone().children().remove().end().text()).toEqual("店舗");
                expect(element.find('div span').length).not.toEqual(1);
            });

            it("should render html of 1st div correctly with readonly set to true", function() {
                compile("店舗", "3", "tenCd", "tenCd", "result.tenCd", null, "true", "true", "false", "false", "result.kaisyaCd",
                        "result.jigyobuCd", "result.hakkoDay", "tenNm", "result.tenNm");
                expect(element.find('div').length).toEqual(1);
                expect(element.find('div').eq(0).hasClass("cs-label-large cs-label")).toEqual(true);
                expect(element.find('div').eq(0).clone().children().remove().end().text()).toEqual("店舗");
                expect(element.find('div span').length).not.toEqual(1);
            });
            
            it("should render html of 1st div correctly with partition set to empty", function() {
                compile("店舗", "", "tenCd", "tenCd", "result.tenCd", "true", null, "true", "false", "false", "result.kaisyaCd",
                        "result.jigyobuCd", "result.hakkoDay", "tenNm", "tenNm", "result.tenNm");
                expect(element.find('div').length).toEqual(1);
                expect(element.find('div').eq(0).hasClass("cs-label-divide-zero cs-label")).toEqual(true);
                expect(element.find('div').eq(0).clone().children().remove().end().text()).toEqual("店舗");
                expect(element.find('div span').length).not.toEqual(1);                
            });
        });

        describe('1st input field', function() {

            it("should render html correctly when required", function() {
                compile("店舗", "3", "tenCd", "tenCd", "result.tenCd", "true", null, "true", "false", "false", "result.kaisyaCd",
                        "result.jigyobuCd", "result.hakkoDay", "tenNm", "tenNm", "result.tenNm");

                expect(element.find('input').length).toEqual(2);
                expect(element.find('input').eq(0).attr('type')).toEqual("text");
                expect(element.find('input').eq(0).attr('cc-type')).toEqual("code");
                expect(element.find('input').eq(0).attr('id')).toEqual("tenCd");
                expect(element.find('input').eq(0).attr('name')).toEqual("tenCd");
                expect(element.find('input').eq(0).attr('ng-model')).toEqual("ngModel");
                expect(element.find('input').eq(0).attr('required')).not.toEqual(undefined);
                expect(element.find('input').eq(0).attr('readonly')).toEqual(undefined);
                expect(element.find('input').eq(0).attr('ng-minlength')).toEqual("3");
                expect(element.find('input').eq(0).attr('cc-blur-validation')).not.toEqual(undefined);
                expect(element.find('input').eq(0).attr('ui-event')).toEqual("{ blur : 'blurCallback(ngModel)' }");
                expect(element.find('input').eq(0).attr('maxlength')).toEqual("3");
            });
            
            it("should render html correctly when readonly", function() {
                compile("店舗", "3", "tenCd", "tenCd", "result.tenCd", null, "true", null, "false", "false", "result.kaisyaCd",
                        "result.jigyobuCd", "result.hakkoDay", "tenNm", "tenNm", "result.tenNm");
                expect(element.find('input').eq(0).attr('required')).toEqual(undefined);
                expect(element.find('input').eq(0).attr('readonly')).not.toEqual(undefined);
                expect(element.find('input').eq(0).attr('ng-minlength')).toEqual(undefined);
                expect(element.find('input').eq(0).attr('cc-blur-validation')).toEqual(undefined);
                expect(element.find('input').eq(0).attr('ui-event')).toEqual(undefined);

            });
        });

        describe('2nd input field', function() {

            it("should render html correctly", function() {
                compile("店舗", "3", "tenCd", "tenCd", "result.tenCd", "true", null, "true", "false", "false", "result.kaisyaCd",
                        "result.jigyobuCd", "result.hakkoDay", "tenNm", "tenNm", "result.tenNm");

                expect(element.find('input').eq(1).attr('type')).toEqual("text");
                expect(element.find('input').eq(1).attr('cc-type')).toEqual("zenkaku");
                expect(element.find('input').eq(1).attr('id')).toEqual("tenNm");
                expect(element.find('input').eq(1).attr('name')).toEqual("tenNm");
                expect(element.find('input').eq(1).attr('ng-model')).toEqual("ngModel2");
                expect(element.find('input').eq(1).attr('readonly')).not.toEqual(undefined);
                expect(element.find('input').eq(1).attr('maxlength')).toEqual("20");
                expect(element.find('input').eq(1).hasClass('ng-pristine ng-valid cs-ime-on cs-left cs-char-20'))
                        .toEqual(true);
            
                compile("店舗", "3", "tenCd", "tenCd", "result.tenCd", "true", null, "true", "false", "true", "result.kaisyaCd",
                        "result.jigyobuCd", "result.hakkoDay", "tenNm", "tenNm", "result.tenNm");
                expect(element.find('input').eq(1).attr('maxlength')).toEqual("5");

            });
        });
    });

     describe("logic cases", function() {
        it('it should bind value and view value correctly', function() {
            compile("店舗", "3", "tenCd", "tenCd", "result.tenCd", "true", null, "true", "true", "true", "result.kaisyaCd",
                    "result.jigyobuCd", "result.hakkoDay", "tenNm", "tenNm", "result.tenNm");
            inputElement = element.find('input').eq(0);
            var modelCtrl = inputElement.controller('ngModel');
            
            inputElement.triggerHandler('focus');
            changeInputValue(inputElement, '002');
            $httpBackend.flush();
            
            inputElement.triggerHandler('blur');
            expect(inputElement.val()).toEqual('002');
            expect(scope.result.tenCd).toEqual('002');
            expect(modelCtrl.$valid).toBe(true);
            
            inputElement.triggerHandler('focus');
            expect(inputElement.val()).toEqual('002');
            expect(scope.result.tenCd).toEqual('002');
        
        });
        
        it("it should be catch required validation", function() {
            compile("店舗", "3", "tenCd", "tenCd", "result.tenCd", "true", null, "true", "true", "true", "result.kaisyaCd",
                    "result.jigyobuCd", "result.hakkoDay", "tenNm", "tenNm", "result.tenNm");
            inputElement = element.find('input').eq(0);
            var modelCtrl = inputElement.controller('ngModel');

            inputElement.triggerHandler('focus');
            changeInputValue(inputElement, '');

            inputElement.triggerHandler('blur');
            expect(scope.result.tenCd).toEqual(undefined);
            expect(modelCtrl.$invalid).toBe(true);
            expect(modelCtrl.$error.required).toBe(true);
        
        });
        
        it('it should be catch minlength validation ', function() {
            compile("店舗", "3", "tenCd", "tenCd", "result.tenCd", "true", null, "true", "true", "true", "result.kaisyaCd",
                    "result.jigyobuCd", "result.hakkoDay", "tenNm", "tenNm", "result.tenNm");
             inputElement = element.find('input').eq(0);
             var modelCtrl = inputElement.controller('ngModel');

             inputElement.triggerHandler('focus');
             changeInputValue(inputElement, '00');

             inputElement.triggerHandler('blur');
             expect(scope.result.tenCd).toEqual(undefined);
             expect(modelCtrl.$invalid).toBe(true);
             expect(modelCtrl.$error.minlength).toBe(true);
        
        });

        it('it should populate input no2 with data , hakkoday exists', function() {
            scope.result.hakkoDay = "20140101";
            compile("店舗", "3", "tenCd", "tenCd", "result.tenCd", "true", null, "true", "true", "false", "result.kaisyaCd",
                    "result.jigyobuCd", "result.hakkoDay", "tenNm", "tenNm", "result.tenNm");
            inputElement = element.find('input').eq(0);
            inputElement2 = element.find('input').eq(1);
            var modelCtrl = inputElement.controller('ngModel');

            inputElement.triggerHandler('focus');
            changeInputValue(inputElement, '001');
            $httpBackend.flush()

            inputElement.triggerHandler('blur');
            expect(inputElement2.val()).toEqual('○急○レ○シ○エ○ル○本○1）');
            expect(scope.result.tenNm).toEqual('○急○レ○シ○エ○ル○本○1）');
        
        });

        it('it should populate input no2 with data , hakkoday doesnot exist', function() {
            compile("店舗", "3", "tenCd", "tenCd", "result.tenCd", "true", null, "true", "true", "false", "result.kaisyaCd",
                    "result.jigyobuCd", "result.hakkoDay", "tenNm", "tenNm", "result.tenNm");
            inputElement = element.find('input').eq(0);
            inputElement2 = element.find('input').eq(1);
            var modelCtrl = inputElement.controller('ngModel');
            
            inputElement.triggerHandler('focus');
            changeInputValue(inputElement, '001');
            $httpBackend.flush()
            
            inputElement.triggerHandler('blur');
            expect(inputElement2.val()).toEqual('○急○レ○シ○エ○ル○本○）');
            expect(scope.result.tenNm).toEqual('○急○レ○シ○エ○ル○本○）');
            
        });
        
        it('it should not populate input no2 with data and display error message when there is no such data in database and notExist set to false', function() {
            compile("店舗", "3", "tenCd", "tenCd", "result.tenCd", "true", null, "false", "false", "false", "result.kaisyaCd",
                    "result.jigyobuCd", "result.hakkoDay", "tenNm", "tenNm", "result.tenNm");
            inputElement = element.find('input').eq(0);
            inputElement2 = element.find('input').eq(1);
            var modelCtrl = inputElement.controller('ngModel');

            inputElement.triggerHandler('focus');
            changeInputValue(inputElement, '111');
            $httpBackend.flush()

            inputElement.triggerHandler('blur');
            expect(inputElement2.val()).toEqual('');
            expect(scope.result.tenNm).toEqual(undefined);
            expect(modelCtrl.$invalid).toBe(true);
            expect(modelCtrl.$error.ccExist).toBe(true);
        
        });

        it('it should not populate input no2 with data and not display message when there is no such data in database and notExist set to true', function() {
            compile("店舗", "3", "tenCd", "tenCd", "result.tenCd", "true", null, "true", "false", "false", "result.kaisyaCd",
                    "result.jigyobuCd", "result.hakkoDay", "tenNm", "tenNm", "result.tenNm");
            inputElement = element.find('input').eq(0);
            inputElement2 = element.find('input').eq(1);
            var modelCtrl = inputElement.controller('ngModel');

            inputElement.triggerHandler('focus');
            changeInputValue(inputElement, '111');
            $httpBackend.flush()

            inputElement.triggerHandler('blur');
            expect(inputElement2.val()).toEqual('');
            expect(scope.result.tenNm).toEqual(undefined);
            expect(modelCtrl.$invalid).toBe(false);
            expect(modelCtrl.$error.ccExist).toBe(false);
        
        });

        it('it should not populate input no2 with data and display message when there is such data with kubun 9 and notDelexist set to false', function() {
            compile("店舗", "3", "tenCd", "tenCd", "result.tenCd", "true", null, "true", "false", "false", "result.kaisyaCd",
                    "result.jigyobuCd", "result.hakkoDay", "tenNm", "tenNm", "result.tenNm");
            inputElement = element.find('input').eq(0);
            inputElement2 = element.find('input').eq(1);
            var modelCtrl = inputElement.controller('ngModel');
            
            inputElement.triggerHandler('focus');
            changeInputValue(inputElement, '002');
            $httpBackend.flush()

            inputElement.triggerHandler('blur');
            expect(inputElement2.val()).toEqual('');
            expect(scope.result.tenNm).toEqual(undefined);
            expect(modelCtrl.$invalid).toBe(true);
            expect(modelCtrl.$error.ccExist).toBe(true);

        });
    });
});
