'use strict';

/* jasmine specs for directives go here */

describe('Test cc-mei-mltcombobox directive', function() {
    var element, scope, $compile, $httpBackend, changeSelectValue, compile;
    var templateHtml, selectElement, divElement, spanElement;
    
    beforeEach(module('directives'));
    
    beforeEach(inject(function($injector, $sniffer) {
        // inject $compile & $roorScope
        $compile = $injector.get('$compile');
        scope = $injector.get('$rootScope');
        $httpBackend = $injector.get('$httpBackend');
        
        var listRecord1 = [{code:'123456780',name:'name1                     ',kubun:'1'},
                           {code:'123456781',name:'name2　　　　　　　　　　　              　　',kubun:'1'},
                           {code:'123456782',name:'name3                 　　　　　　　　　　　　',kubun:'1'}];

        var listRecord2 = [{code:'123456780',name:'name11　　　　　　　　　　 　              　　',kubun:'1'},
                           {code:'123456781',name:'name21　　　　　　　　　　　              　　 ',kubun:'1'},
                           {code:'123456782',name:'name31　　　　　　　　　　　              　　 ',kubun:'1'}];

        var listRecord3 = [{code:'123456780',name:'name12　　　　　　　　　　　              　　 ',kubun:'9'},
                           {code:'123456781',name:'name22　　　　　　　　　　　              　　 ',kubun:'9'},
                           {code:'123456782',name:'name32　　　　　　　　　　　              　　 ',kubun:'9'}];
        
        
        $httpBackend.when('GET', '/core/codemaster/m017meim/MAA/12345678?hakkoDay=20140101').respond(listRecord1);
        $httpBackend.when('GET', '/core/codemaster/m017meim/MAB/12345678?hakkoDay=19010101').respond(listRecord2);
        $httpBackend.when('GET', '/core/codemaster/m017meim/MAC/12345678?hakkoDay=19010102').respond(listRecord3);

        $httpBackend.when('GET', '/core/codemaster/getUnyoDate').respond("19010101");
      
        // emulates clicking to select option
        changeSelectValue = function(elm, index) {
            angular.element(elm.children()[index]).attr('selected','selected');
            browserTrigger(elm, 'change');
        };
        
        scope.cond = { 
            areaCd: '',
            hakkoDay:''
        }
        
        compile = function(label, partition, id, name, ngModel, required, maxlength, ccMax, delexist, key1, key2, shortname, para01) {
            
            var htmlText = '<cc-mei-mltcombobox' 
            + ' cc-label="' + label
            + '" cc-partition="' + partition
            + '" id="' + id
            + '" name="' + name
            + '" ng-model="' + ngModel
            + ((required != null) ? ('" cc-required="' + required) : "")
            + '" ng-maxlength="' + maxlength
            + '" cc-max="' + ccMax
            + '" cc-delexist="' + delexist
            + '" cc-key1="' + key1
            + '" cc-key2="' + key2
            + '" cc-shortname="' + shortname
            + '" cc-parameter01="' + para01 + '" />';

            element = $compile(htmlText)(scope);
            scope.$digest();
        };
    }));
    
    describe('display html correctly cases', function() {
        describe('1st div cases', function() {
            it("should display html correctly with required set to true", function() {
                // label, partition, id, name, ngModel, required, ngmaxlength, ccmax, delexist, key1, key2, shortname, para01
                compile("エリア", "3", "areaCd", "areaCd", "cond.areaCd", "true", "2", "10", "false", "MAA", "12345678", "false", "cond.hakkoDay" );
                expect(element.find("div").length).toEqual(1);
                expect(element.find('div').eq(0).hasClass("cs-label-large-required cs-label")).toEqual(true);
                expect(element.find('div').eq(0).clone().children().remove().end().text()).toEqual("エリア");
                expect(element.find('div span').length).toEqual(1);
                expect(element.find('div span').eq(0).hasClass("cs-txt-required")).toEqual(true);
                expect(element.find('div span').eq(0).text()).toEqual(" *");
            });

            it("should display html correctly with required set to false", function() {
                // label, partition, id, name, ngModel, required, ngmaxlength, ccmax, delexist, key1, key2, shortname, para01
                compile("エリア", "3", "areaCd", "areaCd", "cond.areaCd", "false", "2", "10", "false", "MAA", "12345678", "false", "cond.hakkoDay" );
                expect(element.find("div").length).toEqual(1);
                expect(element.find('div').eq(0).hasClass("cs-label-large cs-label")).toEqual(true);
                expect(element.find('div').eq(0).clone().children().remove().end().text()).toEqual("エリア");
                expect(element.find('div span').length).not.toEqual(1);
            });

            it("should display html correctly with partition set to empty", function() {
                // label, partition, id, name, ngModel, required, ngmaxlength, ccmax, delexist, key1, key2, shortname, para01
                compile("エリア", "", "areaCd", "areaCd", "cond.areaCd", "false", "2", "10", "false", "MAA", "12345678", "false", "cond.hakkoDay" );
                expect(element.find("div").length).toEqual(1);
                expect(element.find('div').eq(0).hasClass("cs-label-divide-zero cs-label")).toEqual(true);
                expect(element.find('div').eq(0).clone().children().remove().end().text()).toEqual("エリア");
                expect(element.find('div span').length).not.toEqual(1);
            });
        });
        
        describe('select tag cases', function() {
            it("should display html correctly with ccMax larger", function() {
                // label, partition, id, name, ngModel, required, ngmaxlength, ccmax, delexist, key1, key2, shortname, para01
                compile("エリア", "3", "areaCd", "areaCd", "cond.areaCd", "false", "2", "15", "false", "MAA", "12345678", "true", "cond.hakkoDay" );
                expect(element.find("select").length).toEqual(1);
                expect(element.find("select").eq(0).attr("multiple")).toBeDefined();
                expect(element.find("select").eq(0).hasClass("cs-char-30 cs-left")).toEqual(true);
                expect(element.find("select").eq(0).attr("id")).toEqual("areaCd");
                expect(element.find("select").eq(0).attr("name")).toEqual("areaCd");
                expect(element.find("select").eq(0).attr("ng-model")).toEqual("ngModel");
                expect(element.find("select").eq(0).attr("cc-blur-validation")).toBeDefined();
            });

            it("should display html correctly with ccMax smaller", function() {
                // label, partition, id, name, ngModel, required, ngmaxlength, ccmax, delexist, key1, key2, shortname, para01
                compile("エリア", "3", "areaCd", "areaCd", "cond.areaCd", "false", "2", "0", "false", "MAA", "12345678", "false", "cond.hakkoDay" );
                expect(element.find("select").length).toEqual(1);
                expect(element.find("select").eq(0).hasClass("cs-char-50 cs-left")).toEqual(true);
            });
            
            it("should display html correctly with shortname larger", function() {
                // label, partition, id, name, ngModel, required, ngmaxlength, ccmax, delexist, key1, key2, shortname, para01
                compile("エリア", "3", "areaCd", "areaCd", "cond.areaCd", "false", "2", "10", "false", "MAA", "12345678", "false", "cond.hakkoDay" );
                expect(element.find("select").length).toEqual(1);
                expect(element.find("select").eq(0).hasClass("cs-char-50 cs-left")).toEqual(true);
            });

            it("should display html correctly with shortname smaller", function() {
                // label, partition, id, name, ngModel, required, ngmaxlength, ccmax, delexist, key1, key2, shortname, para01
                compile("エリア", "3", "areaCd", "areaCd", "cond.areaCd", "false", "2", "10", "false", "MAA", "12345678", "true", "cond.hakkoDay" );
                expect(element.find("select").length).toEqual(1);
                expect(element.find("select").eq(0).hasClass("cs-char-20 cs-left")).toEqual(true);
            });

            it("should display html correctly with required set to true", function() {
                // label, partition, id, name, ngModel, required, ngmaxlength, ccmax, delexist, key1, key2, shortname, para01
                compile("エリア", "3", "areaCd", "areaCd", "cond.areaCd", "true", "2", "10", "false", "MAA", "12345678", "true", "cond.hakkoDay" );
                expect(element.find("select").eq(0).attr("required")).toBeDefined();
                expect(element.find("select").eq(0).attr("ui-select2")).toEqual("{allowClear: false}");
            });

            it("should display html correctly with required set to false", function() {
                // label, partition, id, name, ngModel, required, ngmaxlength, ccmax, delexist, key1, key2, shortname, para01
                compile("エリア", "3", "areaCd", "areaCd", "cond.areaCd", "false", "2", "10", "false", "MAA", "12345678", "true", "cond.hakkoDay" );
                expect(element.find("select").eq(0).attr("required")).toBeUndefined();
                expect(element.find("select").eq(0).attr("ui-select2")).toEqual("{allowClear: true}");
            });
        });
    });
    
    describe("logic cases", function() {
        it("should trim and populate name data to select tag", function() {
            // label, partition, id, name, ngModel, required, ngmaxlength, ccmax, delexist, key1, key2, shortname, para01
            compile("エリア", "3", "areaCd", "areaCd", "cond.areaCd", "false", "2", "10", "false", "MAB", "12345678", "true", "cond.hakkoDay" );

            $httpBackend.flush();
            expect(element.find("select option").length).toEqual(3);
            expect(element.find("select option").eq(0).text()).toEqual("123456780:name11");
            expect(element.find("select option").eq(1).text()).toEqual("123456781:name21");
            expect(element.find("select option").eq(2).text()).toEqual("123456782:name31");
        });

        it("should trim and populate data to select tag when hakkoday exists", function() {
            scope.cond.hakkoDay = "20140101";
            // label, partition, id, name, ngModel, required, ngmaxlength, ccmax, delexist, key1, key2, shortname, para01
            compile("エリア", "3", "areaCd", "areaCd", "cond.areaCd", "false", "2", "15", "false", "MAA", "12345678", "true", "cond.hakkoDay" );
         
            $httpBackend.flush();
            expect(element.find("select option").length).toEqual(3);
            expect(element.find("select option").eq(0).text()).toEqual("123456780:name1");
            expect(element.find("select option").eq(1).text()).toEqual("123456781:name2");
            expect(element.find("select option").eq(2).text()).toEqual("123456782:name3");
        });

        it("should not populate data with kubun = 9 to select tag when delexist is false", function() {
            scope.cond.hakkoDay = "19010102";
            // label, partition, id, name, ngModel, required, ngmaxlength, ccmax, delexist, key1, key2, shortname, para01
            compile("エリア", "3", "areaCd", "areaCd", "cond.areaCd", "false", "2", "15", "false", "MAC", "12345678", "true", "cond.hakkoDay" );
            $httpBackend.flush();
            expect(element.find("select option").length).toEqual(0);
        });

        it("should populate data with kubun = 9 to select tag when delexist is true", function() {
            scope.cond.hakkoDay = "19010102";
            // label, partition, id, name, ngModel, required, ngmaxlength, ccmax, delexist, key1, key2, shortname, para01
            compile("エリア", "3", "areaCd", "areaCd", "cond.areaCd", "false", "2", "15", "true", "MAC", "12345678", "true", "cond.hakkoDay" );
           
            $httpBackend.flush();
            expect(element.find("select option").length).toEqual(3);
            expect(element.find("select option").eq(0).text()).toEqual("123456780:name12");
            expect(element.find("select option").eq(1).text()).toEqual("123456781:name22");
            expect(element.find("select option").eq(2).text()).toEqual("123456782:name32");
        });
        
        it("should bind data to model when select an option", function() {
            scope.cond.hakkoDay = "20140101";
            // label, partition, id, name, ngModel, required, ngmaxlength, ccmax, delexist, key1, key2, shortname, para01
            compile("エリア", "3", "areaCd", "areaCd", "cond.areaCd", "false", "2", "15", "true", "MAA", "12345678", "true", "cond.hakkoDay" );
           
            $httpBackend.flush();
            
            var selectTag = element.find("select");
            
            changeSelectValue(selectTag, 2);

            expect(scope.cond.areaCd).toEqual(['123456782']);
        });

        it("should bind 2 or more data to model when select an option", function() {
            scope.cond.hakkoDay = "20140101";
            // label, partition, id, name, ngModel, required, ngmaxlength, ccmax, delexist, key1, key2, shortname, para01
            compile("エリア", "3", "areaCd", "areaCd", "cond.areaCd", "false", "2", "15", "true", "MAA", "12345678", "true", "cond.hakkoDay" );
            
            $httpBackend.flush();
            
            var selectTag = element.find("select");
            
            changeSelectValue(selectTag, 0);
            changeSelectValue(selectTag, 1);
            changeSelectValue(selectTag, 2);
            
            expect(scope.cond.areaCd).toEqual(['123456780','123456781','123456782']);
        });
    });
});
















