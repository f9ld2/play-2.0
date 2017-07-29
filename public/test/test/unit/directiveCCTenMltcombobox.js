'use strict';

/* jasmine specs for directives go here */

describe('Test cc-ten-mltcombobox directive', function() {
    var element, scope, $compile, $httpBackend, changeSelectValue, compile;
    var templateHtml, selectElement, divElement, spanElement;
    
    beforeEach(module('directives'));
    
    beforeEach(inject(function($injector, $sniffer) {
        // inject $compile & $roorScope
        $compile = $injector.get('$compile');
        scope = $injector.get('$rootScope');
        $httpBackend = $injector.get('$httpBackend');
        
        var listRecord1 = [{code:'021',name:'○ァ○リ○ス○ア○部1　　　　　　　　　　　　　',shortName:'○部1　',kubun:'1'},
                           {code:'022',name:'○ァ○リ○ス○ア○部2　　　　　　　　　　　　　',shortName:'○部2　',kubun:'1'},
                           {code:'023',name:'○ァ○リ○ス○ア○部3　　　　　　　　　　　　　',shortName:'○部3　',kubun:'1'}];

        var listRecord2 = [{code:'021',name:'○ァ○リ○ス○ア○部11　　　　　　　　　　　　　',shortName:'○部11　',kubun:'1'},
                           {code:'022',name:'○ァ○リ○ス○ア○部21　　　　　　　　　　　　　',shortName:'○部21　',kubun:'1'},
                           {code:'023',name:'○ァ○リ○ス○ア○部31　　　　　　　　　　　　　',shortName:'○部31　',kubun:'1'}];

        var listRecord3 = [{code:'021',name:'○ァ○リ○ス○ア○部12　　　　　　　　　　　　　',shortName:'○部12　',kubun:'9'},
                           {code:'022',name:'○ァ○リ○ス○ア○部22　　　　　　　　　　　　　',shortName:'○部22　',kubun:'9'},
                           {code:'023',name:'○ァ○リ○ス○ア○部32　　　　　　　　　　　　　',shortName:'○部32　',kubun:'9'}];

        $httpBackend.when('GET', '/core/codename/m006tenm2/00/01?hakkoDay=20140101').respond(listRecord1);
        $httpBackend.when('GET', '/core/codename/m006tenm2/00/01?hakkoDay=19010101').respond(listRecord2);
        $httpBackend.when('GET', '/core/codename/m006tenm2/00/01?hakkoDay=19010102').respond(listRecord3);

        $httpBackend.when('GET', '/core/codemaster/getUnyoDate').respond("19010101");
      
        // emulates clicking to select option
        changeSelectValue = function(elm, index) {
            angular.element(elm.children()[index]).attr('selected','selected');
            browserTrigger(elm, 'change');
        };
        
        scope.cond = { 
            tenCd: '',
            kaisyaCd: '',
            igyobuCd: '',
            hakkoDay:''
        }
        
        compile = function(label, partition, id, name, ngModel, required, ccMax, delexist, shortname, para01, para02, para03) {
            
            var htmlText = '<cc-ten-mltcombobox' 
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
            + '" cc-parameter02="' + para02 
            + '" cc-parameter03="' + para03 
            + '" />';

            element = $compile(htmlText)(scope);
            scope.$digest();
        };
    }));
    
    describe('display html correctly cases', function() {
        describe('1st div cases', function() {
            it("should display html correctly with required set to true", function() {
                // label, partition, id, name, ngModel, required, ccMax, delexist, shortname, para01
                compile("店舗", "3", "tenCd", "tenCd", "cond.tenCd", "true", "10", "false", "false", 
                        "cond.kaisyaCd", "cond.igyobuCd", "cond.hakkoDay" );
                expect(element.find("div").length).toEqual(1);
                expect(element.find('div').eq(0).hasClass("cs-label-large-required cs-label")).toEqual(true);
                expect(element.find('div').eq(0).clone().children().remove().end().text()).toEqual("店舗");
                expect(element.find('div span').length).toEqual(1);
                expect(element.find('div span').eq(0).hasClass("cs-txt-required")).toEqual(true);
                expect(element.find('div span').eq(0).text()).toEqual(" *");
            });

            it("should display html correctly with required set to false", function() {
                // label, partition, id, name, ngModel, required, ccMax, delexist, shortname, para01, para02
                compile("店舗", "3", "tenCd", "tenCd", "cond.tenCd", "false", "10", "false", "false", 
                        "cond.kaisyaCd", "cond.igyobuCd", "cond.hakkoDay" );
                expect(element.find("div").length).toEqual(1);
                expect(element.find('div').eq(0).hasClass("cs-label-large cs-label")).toEqual(true);
                expect(element.find('div').eq(0).clone().children().remove().end().text()).toEqual("店舗");
                expect(element.find('div span').length).not.toEqual(1);
            });

            it("should display html correctly with partition set to empty", function() {
                // label, partition, id, name, ngModel, required, ccMax, delexist, shortname, para01, para02
                compile("店舗", "", "tenCd", "tenCd", "cond.tenCd", "false", "10", "false", "false", 
                        "cond.kaisyaCd", "cond.igyobuCd", "cond.hakkoDay" );
                expect(element.find("div").length).toEqual(1);
                expect(element.find('div').eq(0).hasClass("cs-label-divide-zero cs-label")).toEqual(true);
                expect(element.find('div').eq(0).clone().children().remove().end().text()).toEqual("店舗");
                expect(element.find('div span').length).not.toEqual(1);
            });
        });
        
        describe('select tag cases', function() {
            it("should display html correctly with ccMax larger", function() {
                // label, partition, id, name, ngModel, required, ccMax, delexist, shortname, para01, para02
                compile("店舗", "3", "tenCd", "tenCd", "cond.tenCd", "false", "10", "false", "true", 
                        "cond.kaisyaCd", "cond.igyobuCd", "cond.hakkoDay" );
                expect(element.find("select").length).toEqual(1);
                expect(element.find("select").eq(0).attr("multiple")).toBeDefined();
                expect(element.find("select").eq(0).hasClass("cs-char-50 cs-left")).toEqual(true);
                expect(element.find("select").eq(0).attr("id")).toEqual("tenCd");
                expect(element.find("select").eq(0).attr("name")).toEqual("tenCd");
                expect(element.find("select").eq(0).attr("ng-model")).toEqual("ngModel");
                expect(element.find("select").eq(0).attr("cc-blur-validation")).toBeDefined();
            });

            it("should display html correctly with ccMax smaller", function() {
                // label, partition, id, name, ngModel, required, ccMax, delexist, shortname, para01, para02
                compile("店舗", "3", "tenCd", "tenCd", "cond.tenCd", "false", "0", "false", "true", 
                        "cond.kaisyaCd", "cond.igyobuCd", "cond.hakkoDay" );
                expect(element.find("select").length).toEqual(1);
                expect(element.find("select").eq(0).hasClass("cs-char-15 cs-left")).toEqual(true);
            });
            
            it("should display html correctly with shortname larger", function() {
                // label, partition, id, name, ngModel, required, ccMax, delexist, shortname, para01, para02
                compile("店舗", "3", "tenCd", "tenCd", "cond.tenCd", "false", "2", "false", "false", 
                        "cond.kaisyaCd", "cond.igyobuCd", "cond.hakkoDay" );
                expect(element.find("select").length).toEqual(1);
                expect(element.find("select").eq(0).hasClass("cs-char-50 cs-left")).toEqual(true);
            });

            it("should display html correctly with shortname smaller", function() {
                // label, partition, id, name, ngModel, required, ccMax, delexist, shortname, para01, para02
                compile("店舗", "3", "tenCd", "tenCd", "cond.tenCd", "false", "5", "false", "true", 
                        "cond.kaisyaCd", "cond.igyobuCd", "cond.hakkoDay" );
                expect(element.find("select").length).toEqual(1);
                expect(element.find("select").eq(0).hasClass("cs-char-30 cs-left")).toEqual(true);
            });

            it("should display html correctly with required set to true", function() {
                // label, partition, id, name, ngModel, required, ccMax, delexist, shortname, para01, para02
                compile("店舗", "3", "tenCd", "tenCd", "cond.tenCd", "true", "5", "false", "true", 
                        "cond.kaisyaCd", "cond.igyobuCd", "cond.hakkoDay" );
                expect(element.find("select").eq(0).attr("required")).toBeDefined();
                expect(element.find("select").eq(0).attr("ui-select2")).toEqual("{allowClear: false}");
            });

            it("should display html correctly with required set to false", function() {
                // label, partition, id, name, ngModel, required, ccMax, delexist, shortname, para01, para02
                compile("店舗", "3", "tenCd", "tenCd", "cond.tenCd", "false", "5", "false", "true", 
                        "cond.kaisyaCd", "cond.igyobuCd", "cond.hakkoDay" );
                expect(element.find("select").eq(0).attr("required")).toBeUndefined();
                expect(element.find("select").eq(0).attr("ui-select2")).toEqual("{allowClear: true}");
            });
        });
    });
    
    describe("logic cases", function() {
        it("should trim and populate name data to select tag", function() {
            // label, partition, id, name, ngModel, required, ccMax, delexist, shortname, para01
            compile("店舗", "3", "tenCd", "tenCd", "cond.tenCd", "false", "5", "false", "false", 
                    "cond.kaisyaCd", "cond.igyobuCd", "cond.hakkoDay" );
            scope.cond.kaisyaCd = '00';
            scope.cond.igyobuCd = '01';

            $httpBackend.flush();
            expect(element.find("select option").length).toEqual(3);
            expect(element.find("select option").eq(0).text()).toEqual("021:○ァ○リ○ス○ア○部11");
            expect(element.find("select option").eq(1).text()).toEqual("022:○ァ○リ○ス○ア○部21");
            expect(element.find("select option").eq(2).text()).toEqual("023:○ァ○リ○ス○ア○部31");
        });

        it("should trim and populate shortname data to select tag", function() {
            // label, partition, id, name, ngModel, required, ccMax, delexist, shortname, para01
            compile("店舗", "3", "tenCd", "tenCd", "cond.tenCd", "false", "5", "false", "true", 
                    "cond.kaisyaCd", "cond.igyobuCd", "cond.hakkoDay" );
            scope.cond.kaisyaCd = '00';
            scope.cond.igyobuCd = '01';
            
            $httpBackend.flush();
            expect(element.find("select option").length).toEqual(3);
            expect(element.find("select option").eq(0).text()).toEqual("021:○部11");
            expect(element.find("select option").eq(1).text()).toEqual("022:○部21");
            expect(element.find("select option").eq(2).text()).toEqual("023:○部31");
        });

        it("should trim and populate data to select tag when hakkoday exists", function() {
            scope.cond.hakkoDay = "20140101";
            // label, partition, id, name, ngModel, required, ccMax, delexist, shortname, para01
            compile("店舗", "3", "tenCd", "tenCd", "cond.tenCd", "false", "5", "false", "false", 
                    "cond.kaisyaCd", "cond.igyobuCd", "cond.hakkoDay" );
            scope.cond.kaisyaCd = '00';
            scope.cond.igyobuCd = '01';
         
            $httpBackend.flush();
            expect(element.find("select option").length).toEqual(3);
            expect(element.find("select option").eq(0).text()).toEqual("021:○ァ○リ○ス○ア○部1");
            expect(element.find("select option").eq(1).text()).toEqual("022:○ァ○リ○ス○ア○部2");
            expect(element.find("select option").eq(2).text()).toEqual("023:○ァ○リ○ス○ア○部3");
        });

        it("should not populate data with kubun = 9 to select tag when delexist is false", function() {
            scope.cond.hakkoDay = "19010102";
            // label, partition, id, name, ngModel, required, ccMax, delexist, shortname, para01
            compile("店舗", "3", "tenCd", "tenCd", "cond.tenCd", "false", "5", "false", "false", 
                    "cond.kaisyaCd", "cond.igyobuCd", "cond.hakkoDay" );
            scope.cond.kaisyaCd = '00';
            scope.cond.igyobuCd = '01';

            $httpBackend.flush();
            expect(element.find("select option").length).toEqual(0);
        });

        it("should populate data with kubun = 9 to select tag when delexist is true", function() {
            scope.cond.hakkoDay = "19010102";
            // label, partition, id, name, ngModel, required, ccMax, delexist, shortname, para01
            compile("店舗", "3", "tenCd", "tenCd", "cond.tenCd", "false", "5", "true", "false", 
                    "cond.kaisyaCd", "cond.igyobuCd", "cond.hakkoDay" );
            scope.cond.kaisyaCd = '00';
            scope.cond.igyobuCd = '01';
           
            $httpBackend.flush();
            expect(element.find("select option").length).toEqual(3);
            expect(element.find("select option").eq(0).text()).toEqual("021:○ァ○リ○ス○ア○部12");
            expect(element.find("select option").eq(1).text()).toEqual("022:○ァ○リ○ス○ア○部22");
            expect(element.find("select option").eq(2).text()).toEqual("023:○ァ○リ○ス○ア○部32");
        });
        
        it("should bind data to model when select an option", function() {
            scope.cond.hakkoDay = "20140101";
            // label, partition, id, name, ngModel, required, ccMax, delexist, shortname, para01
            compile("店舗", "3", "tenCd", "tenCd", "cond.tenCd", "false", "5", "false", "false", 
                    "cond.kaisyaCd", "cond.igyobuCd", "cond.hakkoDay" );
            scope.cond.kaisyaCd = '00';
            scope.cond.igyobuCd = '01';
           
            $httpBackend.flush();
            
            var selectTag = element.find("select");
            
            changeSelectValue(selectTag, 2);

            expect(scope.cond.tenCd).toEqual(['023']);
        });

        it("should bind 2 or more data to model when select an option", function() {
            scope.cond.hakkoDay = "20140101";
            // label, partition, id, name, ngModel, required, ccMax, delexist, shortname, para01
            compile("店舗", "3", "tenCd", "tenCd", "cond.tenCd", "false", "5", "false", "false", 
                    "cond.kaisyaCd", "cond.igyobuCd", "cond.hakkoDay" );
            scope.cond.kaisyaCd = '00';
            scope.cond.igyobuCd = '01';
            
            $httpBackend.flush();
            
            var selectTag = element.find("select");
            
            changeSelectValue(selectTag, 0);
            changeSelectValue(selectTag, 1);
            changeSelectValue(selectTag, 2);
            
            expect(scope.cond.tenCd).toEqual(['021','022','023']);
        });
    });
});
