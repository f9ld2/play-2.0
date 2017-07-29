'use strict';

/* jasmine specs for directives go here */

describe('Test cc-mtori-mltcombobox directive', function() {
    var element, scope, $compile, $httpBackend, changeSelectValue, compile;
    var templateHtml, selectElement, divElement, spanElement;
    
    beforeEach(module('directives'));
    
    beforeEach(inject(function($injector, $sniffer) {
        // inject $compile & $roorScope
        $compile = $injector.get('$compile');
        scope = $injector.get('$rootScope');
        $httpBackend = $injector.get('$httpBackend');
        
        var listRecord1 = [{code:'4022001',name:'大急水産（株）1　　　　　　　　　　　　　',shortName:'大急水産1　',kubun:'1'},
                           {code:'4022002',name:'大急水産（株）2　　　　　　　　　　　　　',shortName:'大急水産2　',kubun:'1'},
                           {code:'4022003',name:'大急水産（株）3　　　　　　　　　　　　　',shortName:'大急水産3　',kubun:'1'}];

        var listRecord2 = [{code:'4022001',name:'大急水産（株）11　　　　　　　　　　　　　',shortName:'大急水産11　',kubun:'1'},
                           {code:'4022002',name:'大急水産（株）21　　　　　　　　　　　　　',shortName:'大急水産21　',kubun:'1'},
                           {code:'4022003',name:'大急水産（株）31　　　　　　　　　　　　　',shortName:'大急水産31　',kubun:'1'}];

        var listRecord3 = [{code:'4022001',name:'大急水産（株）12　　　　　　　　　　　　　',shortName:'大急水産11　',kubun:'9'},
                           {code:'4022002',name:'大急水産（株）22　　　　　　　　　　　　　',shortName:'大急水産21　',kubun:'9'},
                           {code:'4022003',name:'大急水産（株）32　　　　　　　　　　　　　',shortName:'大急水産31　',kubun:'9'}];
        
        
        $httpBackend.when('GET', '/core/codename/m011trim3?hakkoDay=20140101').respond(listRecord1);
        $httpBackend.when('GET', '/core/codename/m011trim3?hakkoDay=19010101').respond(listRecord2);
        $httpBackend.when('GET', '/core/codename/m011trim3?hakkoDay=19010102').respond(listRecord3);

        $httpBackend.when('GET', '/core/codemaster/getUnyoDate').respond("19010101");
      
        // emulates clicking to select option
        changeSelectValue = function(elm, index) {
            angular.element(elm.children()[index]).attr('selected','selected');
            browserTrigger(elm, 'change');
        };
        
        scope.cond = { 
            mainToriCd: '',
            hakkoDay:''
        }
        
        compile = function(label, partition, id, name, ngModel, required, ccMax, delexist, shortname, para01) {
            
            var htmlText = '<cc-mtori-mltcombobox' 
            + ' cc-label="' + label
            + '" cc-partition="' + partition
            + '" id="' + id
            + '" name="' + name
            + '" ng-model="' + ngModel
            + ((required != null) ? ('" cc-required="' + required) : "")
            + '" cc-max="' + ccMax
            + '" cc-delexist="' + delexist
            + '" cc-shortname="' + shortname
            + '" cc-parameter01="' + para01 + '" />';

            element = $compile(htmlText)(scope);
            scope.$digest();
        };
    }));
    
    describe('display html correctly cases', function() {
        describe('1st div cases', function() {
            it("should display html correctly with required set to true", function() {
                // label, partition, id, name, ngModel, required, ccMax, delexist, shortname, para01
                compile("代表取引先", "3", "mainToriCd", "mainToriCd", "cond.mainToriCd", "true", "10", "false", "false", "cond.hakkoDay" );
                expect(element.find("div").length).toEqual(1);
                expect(element.find('div').eq(0).hasClass("cs-label-large-required cs-label")).toEqual(true);
                expect(element.find('div').eq(0).clone().children().remove().end().text()).toEqual("代表取引先");
                expect(element.find('div span').length).toEqual(1);
                expect(element.find('div span').eq(0).hasClass("cs-txt-required")).toEqual(true);
                expect(element.find('div span').eq(0).text()).toEqual(" *");
            });

            it("should display html correctly with required set to false", function() {
                compile("代表取引先", "3", "mainToriCd", "mainToriCd", "cond.mainToriCd", "false", "10", "false", "false", "cond.hakkoDay" );
                expect(element.find("div").length).toEqual(1);
                expect(element.find('div').eq(0).hasClass("cs-label-large cs-label")).toEqual(true);
                expect(element.find('div').eq(0).clone().children().remove().end().text()).toEqual("代表取引先");
                expect(element.find('div span').length).not.toEqual(1);
            });

            it("should display html correctly with partition set to empty", function() {
                compile("代表取引先", "", "mainToriCd", "mainToriCd", "cond.mainToriCd", "false", "10", "false", "false", "cond.hakkoDay" );
                expect(element.find("div").length).toEqual(1);
                expect(element.find('div').eq(0).hasClass("cs-label-divide-zero cs-label")).toEqual(true);
                expect(element.find('div').eq(0).clone().children().remove().end().text()).toEqual("代表取引先");
                expect(element.find('div span').length).not.toEqual(1);
            });
        });
        
        describe('select tag cases', function() {
            it("should display html correctly with ccMax larger", function() {
                compile("代表取引先", "3", "mainToriCd", "mainToriCd", "cond.mainToriCd", "true", "5", "false", "false", "cond.hakkoDay" );
                expect(element.find("select").length).toEqual(1);
                expect(element.find("select").eq(0).attr("multiple")).toBeDefined();
                expect(element.find("select").eq(0).hasClass("cs-char-40 cs-left")).toEqual(true);
                expect(element.find("select").eq(0).attr("id")).toEqual("mainToriCd");
                expect(element.find("select").eq(0).attr("name")).toEqual("mainToriCd");
                expect(element.find("select").eq(0).attr("ng-model")).toEqual("ngModel");
                expect(element.find("select").eq(0).attr("cc-blur-validation")).toBeDefined();
            });

            it("should display html correctly with ccMax smaller", function() {
                compile("代表取引先", "3", "mainToriCd", "mainToriCd", "cond.mainToriCd", "true", "0", "false", "true", "cond.hakkoDay" );
                expect(element.find("select").length).toEqual(1);
                expect(element.find("select").eq(0).hasClass("cs-char-10 cs-left")).toEqual(true);
            });
            
            it("should display html correctly with shortname larger", function() {
                compile("代表取引先", "3", "mainToriCd", "mainToriCd", "cond.mainToriCd", "true", "1", "true", "true", "cond.hakkoDay" );
                expect(element.find("select").length).toEqual(1);
                expect(element.find("select").eq(0).hasClass("cs-char-10 cs-left")).toEqual(true);
            });

            it("should display html correctly with shortname smaller", function() {
                compile("代表取引先", "3", "mainToriCd", "mainToriCd", "cond.mainToriCd", "true", "10", "false", "false", "cond.hakkoDay" );
                expect(element.find("select").length).toEqual(1);
                expect(element.find("select").eq(0).hasClass("cs-char-50 cs-left")).toEqual(true);
            });

            it("should display html correctly with required set to true", function() {
                compile("代表取引先", "3", "mainToriCd", "mainToriCd", "cond.mainToriCd", "true", "10", "false", "false", "cond.hakkoDay" );
                expect(element.find("select").eq(0).attr("required")).toBeDefined();
                expect(element.find("select").eq(0).attr("ui-select2")).toEqual("{allowClear: false}");
            });

            it("should display html correctly with required set to false", function() {
                compile("代表取引先", "3", "mainToriCd", "mainToriCd", "cond.mainToriCd", "false", "10", "false", "false", "cond.hakkoDay" );
                expect(element.find("select").eq(0).attr("required")).toBeUndefined();
                expect(element.find("select").eq(0).attr("ui-select2")).toEqual("{allowClear: true}");
            });
        });
    });
    
    describe("logic cases", function() {
        it("should trim and populate name data to select tag", function() {
            compile("代表取引先", "3", "mainToriCd", "mainToriCd", "cond.mainToriCd", "true", "10", "true", "false", "cond.hakkoDay" );
            $httpBackend.flush();
            expect(element.find("select option").length).toEqual(3);
            expect(element.find("select option").eq(0).text()).toEqual("4022001:大急水産（株）11");
            expect(element.find("select option").eq(1).text()).toEqual("4022002:大急水産（株）21");
            expect(element.find("select option").eq(2).text()).toEqual("4022003:大急水産（株）31");
        });

        it("should trim and populate shortname data to select tag", function() {
            compile("代表取引先", "3", "mainToriCd", "mainToriCd", "cond.mainToriCd", "true", "10", "true", "true", "cond.hakkoDay" );
            $httpBackend.flush();
            expect(element.find("select option").length).toEqual(3);
            expect(element.find("select option").eq(0).text()).toEqual("4022001:大急水産11");
            expect(element.find("select option").eq(1).text()).toEqual("4022002:大急水産21");
            expect(element.find("select option").eq(2).text()).toEqual("4022003:大急水産31");
        });
        
        it("should trim and populate data to select tag when hakkoday exists", function() {
            scope.cond.hakkoDay = "20140101";
            compile("代表取引先", "3", "mainToriCd", "mainToriCd", "cond.mainToriCd", "true", "10", "true", "true", "cond.hakkoDay" );
            $httpBackend.flush();
            expect(element.find("select option").length).toEqual(3);
            expect(element.find("select option").eq(0).text()).toEqual("4022001:大急水産1");
            expect(element.find("select option").eq(1).text()).toEqual("4022002:大急水産2");
            expect(element.find("select option").eq(2).text()).toEqual("4022003:大急水産3");
        });

        it("should not populate data with kubun = 9 to select tag when delexist is false", function() {
            scope.cond.hakkoDay = "19010102";
            compile("代表取引先", "3", "mainToriCd", "mainToriCd", "cond.mainToriCd", "true", "10", "false", "false", "cond.hakkoDay" );
            $httpBackend.flush();
            expect(element.find("select option").length).toEqual(0);
        });

        it("should populate data with kubun = 9 to select tag when delexist is true", function() {
            scope.cond.hakkoDay = "19010102";
            compile("代表取引先", "3", "mainToriCd", "mainToriCd", "cond.mainToriCd", "true", "10", "true", "false", "cond.hakkoDay" );
            $httpBackend.flush();
            expect(element.find("select option").length).toEqual(3);
            expect(element.find("select option").eq(0).text()).toEqual("4022001:大急水産（株）12");
            expect(element.find("select option").eq(1).text()).toEqual("4022002:大急水産（株）22");
            expect(element.find("select option").eq(2).text()).toEqual("4022003:大急水産（株）32");
        });
        
        it("should bind data to model when select an option", function() {
            scope.cond.hakkoDay = "20140101";
            compile("代表取引先", "3", "mainToriCd", "mainToriCd", "cond.mainToriCd", "true", "10", "true", "true", "cond.hakkoDay" );
            $httpBackend.flush();
            
            var selectTag = element.find("select");
            
            changeSelectValue(selectTag, 2);

            expect(scope.cond.mainToriCd).toEqual(['4022003']);
        });

        it("should bind 2 or more data to model when select an option", function() {
            scope.cond.hakkoDay = "20140101";
            compile("代表取引先", "3", "mainToriCd", "mainToriCd", "cond.mainToriCd", "true", "10", "true", "true", "cond.hakkoDay" );
            $httpBackend.flush();
            
            var selectTag = element.find("select");
            
            changeSelectValue(selectTag, 0);
            changeSelectValue(selectTag, 1);
            changeSelectValue(selectTag, 2);
            
            expect(scope.cond.mainToriCd).toEqual(['4022001','4022002','4022003']);
        });
    });
});
















