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
        
        var listRecord1 = [{code:'011',name:'シーズニング1　　　　　　　　　　　　　',shortName:'シーズニン1　',kubun:'1'},
                           {code:'012',name:'シーズニング2　　　　　　　　　　　　　',shortName:'シーズニン2　',kubun:'1'},
                           {code:'013',name:'シーズニング3　　　　　　　　　　　　　',shortName:'シーズニン3　',kubun:'1'}];

        var listRecord2 = [{code:'011',name:'シーズニング11　　　　　　　　　　　　　',shortName:'シーズニン11　',kubun:'1'},
                           {code:'012',name:'シーズニング21　　　　　　　　　　　　　',shortName:'シーズニン21　',kubun:'1'},
                           {code:'013',name:'シーズニング31　　　　　　　　　　　　　',shortName:'シーズニン31　',kubun:'1'}];

        var listRecord3 = [{code:'011',name:'シーズニング12　　　　　　　　　　　　　',shortName:'シーズニン12　',kubun:'9'},
                           {code:'012',name:'シーズニング22　　　　　　　　　　　　　',shortName:'シーズニン22　',kubun:'9'},
                           {code:'013',name:'シーズニング32　　　　　　　　　　　　　',shortName:'シーズニン32　',kubun:'9'}];
        
        var listRecord4 = [{code:'011',name:'シーズニング13　　　　　　　　　　　　　',shortName:'シーズニン13　',kubun:'1'},
                           {code:'012',name:'シーズニング23　　　　　　　　　　　　　',shortName:'シーズニン23　',kubun:'1'},
                           {code:'013',name:'シーズニング33　　　　　　　　　　　　　',shortName:'シーズニン33　',kubun:'1'}];

        $httpBackend.when('GET', '/core/codename/m000depars?hakkoDay=20140101&ccDelexist=true').respond(listRecord1);
        $httpBackend.when('GET', '/core/codename/m000depars?hakkoDay=19010101&ccDelexist=true').respond(listRecord2);
        $httpBackend.when('GET', '/core/codename/m000depars?hakkoDay=19010102&ccDelexist=true').respond(listRecord3);
        $httpBackend.when('GET', '/core/codename/m000depars?hakkoDay=19010102&ccDelexist=false').respond([]);
        $httpBackend.when('GET', '/core/codename/m000depar/01?hakkoDay=20140101&ccDelexist=true').respond(listRecord4);

        $httpBackend.when('GET', '/core/codemaster/getUnyoDate').respond("19010101");
      
        // emulates clicking to select option
        changeSelectValue = function(elm, index) {
            angular.element(elm.children()[index]).attr('selected','selected');
            browserTrigger(elm, 'change');
        };
        
        scope.cond = { 
            bmnCd: '',
            jgyobuCd: '',
            hakkoDay:''
        }
        
        compile = function(label, partition, id, name, ngModel, required, ccMax, delexist, shortname, para01, para02) {
            
            var htmlText = '<cc-bmn-mltcombobox' 
            + ' cc-label="' + label
            + '" cc-partition="' + partition
            + '" id="' + id
            + '" name="' + name
            + '" ng-model="' + ngModel
            + ((required != null) ? ('" cc-required="' + required) : "")
            + '" cc-max="' + ccMax
            + '" cc-delexist="' + delexist
            + '" cc-shortname="' + shortname
            + '" cc-parameter01="' + para01
            + '" cc-parameter02="' + para02 + '" />';

            element = $compile(htmlText)(scope);
            scope.$digest();
        };
    }));
    
    describe('display html correctly cases', function() {
        describe('1st div cases', function() {
            it("should display html correctly with required set to true", function() {
                // label, partition, id, name, ngModel, required, ccMax, delexist, shortname, para01, para02
                compile("部門", "3", "bmnCd", "bmnCd", "cond.bmnCd", "true", "10", "false", "false", "cond.jgyobuCd","cond.hakkoDay" );
                expect(element.find("div").length).toEqual(1);
                expect(element.find('div').eq(0).hasClass("cs-label-large-required cs-label")).toEqual(true);
                expect(element.find('div').eq(0).clone().children().remove().end().text()).toEqual("部門");
                expect(element.find('div span').length).toEqual(1);
                expect(element.find('div span').eq(0).hasClass("cs-txt-required")).toEqual(true);
                expect(element.find('div span').eq(0).text()).toEqual(" *");
            });

            it("should display html correctly with required set to false", function() {
                // label, partition, id, name, ngModel, required, ccMax, delexist, shortname, para01, para02
                compile("部門", "3", "bmnCd", "bmnCd", "cond.bmnCd", "false", "10", "false", "false", "cond.jgyobuCd","cond.hakkoDay" );
                expect(element.find("div").length).toEqual(1);
                expect(element.find('div').eq(0).hasClass("cs-label-large cs-label")).toEqual(true);
                expect(element.find('div').eq(0).clone().children().remove().end().text()).toEqual("部門");
                expect(element.find('div span').length).not.toEqual(1);
            });

            it("should display html correctly with partition set to empty", function() {
                // label, partition, id, name, ngModel, required, ccMax, delexist, shortname, para01, para02
                compile("部門", "", "bmnCd", "bmnCd", "cond.bmnCd", "false", "10", "false", "false", "cond.jgyobuCd","cond.hakkoDay" );
                expect(element.find("div").length).toEqual(1);
                expect(element.find('div').eq(0).hasClass("cs-label-divide-zero cs-label")).toEqual(true);
                expect(element.find('div').eq(0).clone().children().remove().end().text()).toEqual("部門");
                expect(element.find('div span').length).not.toEqual(1);
            });
        });
        
        describe('select tag cases', function() {
            it("should display html correctly with ccMax larger", function() {
                // label, partition, id, name, ngModel, required, ccMax, delexist, shortname, para01, para02
                compile("部門", "3", "bmnCd", "bmnCd", "cond.bmnCd", "false", "7", "false", "true", "cond.jgyobuCd","cond.hakkoDay" );
                expect(element.find("select").length).toEqual(1);
                expect(element.find("select").eq(0).attr("multiple")).toBeDefined();
                expect(element.find("select").eq(0).hasClass("cs-char-40 cs-left")).toEqual(true);
                expect(element.find("select").eq(0).attr("id")).toEqual("bmnCd");
                expect(element.find("select").eq(0).attr("name")).toEqual("bmnCd");
                expect(element.find("select").eq(0).attr("ng-model")).toEqual("ngModel");
                expect(element.find("select").eq(0).attr("cc-blur-validation")).toBeDefined();
            });

            it("should display html correctly with ccMax smaller", function() {
                // label, partition, id, name, ngModel, required, ccMax, delexist, shortname, para01, para02
                compile("部門", "3", "bmnCd", "bmnCd", "cond.bmnCd", "false", "2", "false", "true", "cond.jgyobuCd","cond.hakkoDay" );
                expect(element.find("select").length).toEqual(1);
                expect(element.find("select").eq(0).hasClass("cs-char-15 cs-left")).toEqual(true);
            });
            
            it("should display html correctly with shortname larger", function() {
                // label, partition, id, name, ngModel, required, ccMax, delexist, shortname, para01, para02
                compile("部門", "3", "bmnCd", "bmnCd", "cond.bmnCd", "false", "2", "false", "false", "cond.jgyobuCd","cond.hakkoDay" );
                expect(element.find("select").length).toEqual(1);
                expect(element.find("select").eq(0).hasClass("cs-char-50 cs-left")).toEqual(true);
            });

            it("should display html correctly with shortname smaller", function() {
                // label, partition, id, name, ngModel, required, ccMax, delexist, shortname, para01, para02
                compile("部門", "3", "bmnCd", "bmnCd", "cond.bmnCd", "false", "5", "false", "true", "cond.jgyobuCd","cond.hakkoDay" );
                expect(element.find("select").length).toEqual(1);
                expect(element.find("select").eq(0).hasClass("cs-char-30 cs-left")).toEqual(true);
            });

            it("should display html correctly with required set to true", function() {
                // label, partition, id, name, ngModel, required, ccMax, delexist, shortname, para01, para02
                compile("部門", "3", "bmnCd", "bmnCd", "cond.bmnCd", "true", "5", "false", "true", "cond.jgyobuCd","cond.hakkoDay" );
                expect(element.find("select").eq(0).attr("required")).toBeDefined();
                expect(element.find("select").eq(0).attr("ui-select2")).toEqual("{allowClear: false}");
            });

            it("should display html correctly with required set to false", function() {
                // label, partition, id, name, ngModel, required, ccMax, delexist, shortname, para01, para02
                compile("部門", "3", "bmnCd", "bmnCd", "cond.bmnCd", "false", "5", "false", "true", "cond.jgyobuCd","cond.hakkoDay" );
                expect(element.find("select").eq(0).attr("required")).toBeUndefined();
                expect(element.find("select").eq(0).attr("ui-select2")).toEqual("{allowClear: true}");
            });
        });
    });
    
    describe("logic cases", function() {
        it("should trim and populate name data to select tag", function() {
            // label, partition, id, name, ngModel, required, ccMax, delexist, shortname, para01, para02
            compile("部門", "3", "bmnCd", "bmnCd", "cond.bmnCd", "false", "5", "true", "false", "cond.jgyobuCd","cond.hakkoDay" );

            $httpBackend.flush();
            expect(element.find("select option").length).toEqual(3);
            expect(element.find("select option").eq(0).text()).toEqual("011:シーズニング11");
            expect(element.find("select option").eq(1).text()).toEqual("012:シーズニング21");
            expect(element.find("select option").eq(2).text()).toEqual("013:シーズニング31");
        });

        it("should trim and populate shortname data to select tag", function() {
            // label, partition, id, name, ngModel, required, ccMax, delexist, shortname, para01, para02
            compile("部門", "3", "bmnCd", "bmnCd", "cond.bmnCd", "false", "5", "true", "true", "cond.jgyobuCd","cond.hakkoDay" );

            $httpBackend.flush();
            expect(element.find("select option").length).toEqual(3);
            expect(element.find("select option").eq(0).text()).toEqual("011:シーズニン11");
            expect(element.find("select option").eq(1).text()).toEqual("012:シーズニン21");
            expect(element.find("select option").eq(2).text()).toEqual("013:シーズニン31");
        });
        
        it("should trim and populate data to select tag when hakkoday exists", function() {
            scope.cond.hakkoDay = "20140101";
            // label, partition, id, name, ngModel, required, ccMax, delexist, shortname, para01, para02
            compile("部門", "3", "bmnCd", "bmnCd", "cond.bmnCd", "false", "5", "true", "true", "cond.jgyobuCd","cond.hakkoDay" );

            $httpBackend.flush();
            expect(element.find("select option").length).toEqual(3);
            expect(element.find("select option").eq(0).text()).toEqual("011:シーズニン1");
            expect(element.find("select option").eq(1).text()).toEqual("012:シーズニン2");
            expect(element.find("select option").eq(2).text()).toEqual("013:シーズニン3");
        });

        it("should trim and populate data to select tag when jigyobyCd exists", function() {
            scope.cond.hakkoDay = "20140101";
            scope.cond.jgyobuCd = "01";
            // label, partition, id, name, ngModel, required, ccMax, delexist, shortname, para01, para02
            compile("部門", "3", "bmnCd", "bmnCd", "cond.bmnCd", "false", "5", "true", "true", "cond.jgyobuCd","cond.hakkoDay" );
            
            $httpBackend.flush();
            expect(element.find("select option").length).toEqual(3);
            expect(element.find("select option").eq(0).text()).toEqual("011:シーズニン13");
            expect(element.find("select option").eq(1).text()).toEqual("012:シーズニン23");
            expect(element.find("select option").eq(2).text()).toEqual("013:シーズニン33");
        });

        it("should not populate data with kubun = 9 to select tag when delexist is false", function() {
            scope.cond.hakkoDay = "19010102";
            // label, partition, id, name, ngModel, required, ccMax, delexist, shortname, para01, para02
            compile("部門", "3", "bmnCd", "bmnCd", "cond.bmnCd", "false", "5", "false", "false", "cond.jgyobuCd","cond.hakkoDay" );

            $httpBackend.flush();
            expect(element.find("select option").length).toEqual(0);
        });

        it("should populate data with kubun = 9 to select tag when delexist is true", function() {
            scope.cond.hakkoDay = "19010102";
            // label, partition, id, name, ngModel, required, ccMax, delexist, shortname, para01, para02
            compile("部門", "3", "bmnCd", "bmnCd", "cond.bmnCd", "false", "5", "true", "false", "cond.jgyobuCd","cond.hakkoDay" );

            $httpBackend.flush();
            expect(element.find("select option").length).toEqual(3);
            expect(element.find("select option").eq(0).text()).toEqual("011:シーズニング12");
            expect(element.find("select option").eq(1).text()).toEqual("012:シーズニング22");
            expect(element.find("select option").eq(2).text()).toEqual("013:シーズニング32");
        });
        
        it("should bind data to model when select an option", function() {
            scope.cond.hakkoDay = "20140101";
            // label, partition, id, name, ngModel, required, ccMax, delexist, shortname, para01, para02
            compile("部門", "3", "bmnCd", "bmnCd", "cond.bmnCd", "false", "5", "true", "false", "cond.jgyobuCd","cond.hakkoDay" );
            
            $httpBackend.flush();
            
            var selectTag = element.find("select");
            
            changeSelectValue(selectTag, 2);

            expect(scope.cond.bmnCd).toEqual(['013']);
        });

        it("should bind 2 or more data to model when select an option", function() {
            scope.cond.hakkoDay = "20140101";
            // label, partition, id, name, ngModel, required, ccMax, delexist, shortname, para01, para02
            compile("部門", "3", "bmnCd", "bmnCd", "cond.bmnCd", "false", "5", "true", "false", "cond.jgyobuCd","cond.hakkoDay" );

            $httpBackend.flush();
            
            var selectTag = element.find("select");
            
            changeSelectValue(selectTag, 0);
            changeSelectValue(selectTag, 1);
            changeSelectValue(selectTag, 2);
            
            expect(scope.cond.bmnCd).toEqual(['011','012','013']);
        });
    });
});