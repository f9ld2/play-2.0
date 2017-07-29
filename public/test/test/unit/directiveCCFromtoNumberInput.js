'use strict';

/* jasmine specs for directives go here */

describe ('Test directive fromto number input ', function() {
    var doc, control, scope, $compile, changeInputValue, 
    compile, templateHtml, inputElement, inputHtml, element;    
    beforeEach (module('directives'));
    beforeEach (inject(function ($injector, $sniffer) {
        $compile = $injector.get('$compile');
        scope = $injector.get('$rootScope');
        scope.result = {
            tanaNoSt: '',
            tanaNoEd: ''
        };
        changeInputValue = function (elm, value) {
          elm.val(value);
          browserTrigger(elm, $sniffer.hasEvent('input') ? 'input' : 'change');
        };
        
        compile = function(label, partition, 
                id, name, ngModel, required, readOnly, cMin, cMax, 
                id2, name2, ngModel2, required2, readOnly2, cMin2, cMax2) {
            
            var htmlText = '<cc-fromto-number-input'
            + ' cc-label="' + label
            + '" cc-partition="' + partition
            + '" id="' + id
            + '" name="' + name
            + '" ng-model="' + ngModel
            + ((required != null) ? ('" cc-required="'+ required) : "")
            + ((readOnly != null) ? ('" cc-readonly="'+ readOnly) : "")
            + '" cc-min="' + cMin
            + '" cc-max="' + cMax
            + '" id2="' + id2
            + '" name2="' + name2
            + '" ng-model2="' + ngModel2
            + ((required2 != null) ? ('" cc-required2="'+ required2) : "")
            + ((readOnly2 != null) ? ('" cc-readonly2="'+ readOnly2) : "")
            + '" cc-min2="' + cMin2
            + '" cc-max2="' + cMax2
            + '"/>';
            
            element = $compile(htmlText)(scope);
    
            templateHtml = element.html();
            
            scope.$digest();
        };
    }));
    
    describe('display html correctly cases', function() {
        describe('1st div cases with required and readonly in 2 inputs', function() {
            var itShouldHaveRequiredCSS = function() {
                expect(element.find('div').length).toEqual(3);
                expect(element.find('div').eq(0).hasClass("cs-label-large-required cs-label")).toEqual(true);
                expect(element.find('div').eq(0).clone().children().remove().end().text()).toEqual("棚卸番号");
                expect(element.find('div span').length).toEqual(1);
                expect(element.find('div span').eq(0).hasClass("cs-txt-required")).toEqual(true);
                expect(element.find('div span').eq(0).text()).toEqual(" *");
            }
            
            var itShouldNotHaveRequiredCSS = function() {
                expect(element.find('div').length).toEqual(3);
                expect(element.find('div').eq(0).hasClass("cs-label-large cs-label")).toEqual(true);
                expect(element.find('div').eq(0).clone().children().remove().end().text()).toEqual("棚卸番号");
                expect(element.find('div span').length).not.toEqual(1);
            }
            
            //1
            it("should render html of 1st div correctly with required set to true, true", function() {
                compile("棚卸番号", "3", "tanaNoSt", "tanaNoSt", "result.tanaNoSt", "true", null, "-9999.9", "9999",
                        "tanaNoEd", "tanaNoEd", "result.tanaNoEd", "true", null, "-9999.9", "9999");
                itShouldHaveRequiredCSS();
            });

            //2
            it("should render html of 1st div correctly with required set to true, false", function() {
                compile("棚卸番号", "3", "tanaNoSt", "tanaNoSt", "result.tanaNoSt", "true", null, "-9999.9", "9999",
                        "tanaNoEd", "tanaNoEd", "result.tanaNoEd", "false", null, "-9999.9", "9999");
                itShouldHaveRequiredCSS();
            });

            //3
            it("should render html of 1st div correctly with required set to true, readonly2 set to true", function() {
                compile("棚卸番号", "3", "tanaNoSt", "tanaNoSt", "result.tanaNoSt", "true", null, "-9999.9", "9999",
                        "tanaNoEd", "tanaNoEd", "result.tanaNoEd", null, "true", "-9999.9", "9999");
                itShouldHaveRequiredCSS();
            });
             
            //4
            it("should render html of 1st div correctly with required set to false, true", function() {
                compile("棚卸番号", "3", "tanaNoSt", "tanaNoSt", "result.tanaNoSt", "false", null, "-9999.9", "9999",
                        "tanaNoEd", "tanaNoEd", "result.tanaNoEd", "true", null, "-9999.9", "9999");
                itShouldHaveRequiredCSS();
            });

            //5
            it("should render html of 1st div correctly with required set to false, false", function() {
                compile("棚卸番号", "3", "tanaNoSt", "tanaNoSt", "result.tanaNoSt", "false", null, "-9999.9", "9999",
                        "tanaNoEd", "tanaNoEd", "result.tanaNoEd", "false", null, "-9999.9", "9999");
                itShouldNotHaveRequiredCSS();
            });
            
            //6
            it("should render html of 1st div correctly with required set to false, readonly2 set to true", function() {
                compile("棚卸番号", "3", "tanaNoSt", "tanaNoSt", "result.tanaNoSt", "false", null, "-9999.9", "9999",
                        "tanaNoEd", "tanaNoEd", "result.tanaNoEd", null, "true", "-9999.9", "9999");
                itShouldNotHaveRequiredCSS();
            });

            //7
            it("should render html of 1st div correctly with readonly set to true, required2 set to true", function() {
                compile("棚卸番号", "3", "tanaNoSt", "tanaNoSt", "result.tanaNoSt", null, "true", "-9999.9", "9999",
                        "tanaNoEd", "tanaNoEd", "result.tanaNoEd", "true", null, "-9999.9", "9999");
                itShouldHaveRequiredCSS();
            });
            
            //8
            it("should render html of 1st div correctly with readonly set to true, required2 set to false", function() {
                compile("棚卸番号", "3", "tanaNoSt", "tanaNoSt", "result.tanaNoSt", null, "true", "-9999.9", "9999",
                        "tanaNoEd", "tanaNoEd", "result.tanaNoEd", "false", null, "-9999.9", "9999");
                itShouldNotHaveRequiredCSS()
            });

            //9
            it("should render html of 1st div correctly with readonly set to true, readonly2 set to true", function() {
                compile("棚卸番号", "3", "tanaNoSt", "tanaNoSt", "result.tanaNoSt", null, "true", "-9999.9", "9999",
                        "tanaNoEd", "tanaNoEd", "result.tanaNoEd", null, "true", "-9999.9", "9999");
                itShouldNotHaveRequiredCSS()
            });
            
            it("should render html of 1st div correctly with partition set to empty", function() {
                compile("棚卸番号", "", "tanaNoSt", "tanaNoSt", "result.tanaNoSt", "true", null, "-9999.9", "9999",
                        "tanaNoEd", "tanaNoEd", "result.tanaNoEd", "true", null, "-9999.9", "9999");
                expect(element.find('div').length).toEqual(3);
                expect(element.find('div').eq(0).hasClass("cs-label-divide-zero cs-label")).toEqual(true);
                expect(element.find('div').eq(0).clone().children().remove().end().text()).toEqual("棚卸番号");
                expect(element.find('div span').length).not.toEqual(1);                
            });
        });
        
        describe("2nd div cases", function() {
            it("should render html correctly", function() {
                compile("棚卸番号", "3", "tanaNoSt", "tanaNoSt", "result.tanaNoSt", "true", null, "-9999.9", "9999",
                        "tanaNoEd", "tanaNoEd", "result.tanaNoEd", "true", null, "-9999.9", "9999");
                expect(element.find('div').length).toEqual(3);
                expect(element.find('div').eq(2).hasClass("cs-label-divide-zero cs-label")).toEqual(true);
                expect(element.find('div').eq(2).clone().children().remove().end().text()).toEqual("～");
            });
        });
        
        describe("1st input cases", function() {
            it("should render html correctly", function() {
                compile("棚卸番号", "3", "tanaNoSt", "tanaNoSt", "result.tanaNoSt", "true", null, "-9999.9", "9999",
                        "tanaNoEd", "tanaNoEd", "result.tanaNoEd", null, "true", "-9999.9", "9999");
                expect(element.find('input').length).toEqual(2);
                expect(element.find('input').eq(0).attr('type')).toEqual("text");
                expect(element.find('input').eq(0).attr('cc-type')).toEqual("number");
                expect(element.find('input').eq(0).attr('id')).toEqual("tanaNoSt");
                expect(element.find('input').eq(0).attr('name')).toEqual("tanaNoSt");
                expect(element.find('input').eq(0).attr('ng-model')).toEqual("ngModel");
                expect(element.find('input').eq(0).attr('ng-minlength')).toEqual("1");                
                expect(element.find('input').eq(0).attr('cc-blur-validation')).not.toEqual(undefined);
                expect(element.find('input').eq(0).attr('cc-validate-less-equal')).toEqual("");
            });
            
            it("should render cc-digit and cc-signed correctly, case 1", function() {
                compile("棚卸番号", "3", "tanaNoSt", "tanaNoSt", "result.tanaNoSt", "true", null, "-9999999.99999", "9999",
                        "tanaNoEd", "tanaNoEd", "result.tanaNoEd", "true", null, "-9999.99", "9999");
                expect(element.find('input').length).toEqual(2);
                expect(element.find('input').eq(0).attr('cc-min')).toEqual("-9999999.99999");
                expect(element.find('input').eq(0).attr('cc-max')).toEqual("9999");
                expect(element.find('input').eq(0).attr('cc-digits-integer')).toEqual("7");
                expect(element.find('input').eq(0).attr('cc-digits-franction')).toEqual("5");
                expect(element.find('input').eq(0).attr('cc-signed')).not.toEqual(undefined);
            });
            
            it("should render cc-digit and cc-signed correctly, case 2", function() {
                compile("棚卸番号", "3", "tanaNoSt", "tanaNoSt", "result.tanaNoSt", "true", null, "999.99", "99999999",
                        "tanaNoEd", "tanaNoEd", "result.tanaNoEd", "true", null, "-9999.99", "9999");
                expect(element.find('input').length).toEqual(2);
                expect(element.find('input').eq(0).attr('cc-min')).toEqual("999.99");
                expect(element.find('input').eq(0).attr('cc-max')).toEqual("99999999");
                expect(element.find('input').eq(0).attr('cc-digits-integer')).toEqual("8");
                expect(element.find('input').eq(0).attr('cc-digits-franction')).toEqual("2");
                expect(element.find('input').eq(0).attr('cc-signed')).toEqual(undefined);
            });
            
            it("should render html correctly when required", function() {
                compile("棚卸番号", "3", "tanaNoSt", "tanaNoSt", "result.tanaNoSt", "true", null, "-9999.9", "9999",
                        "tanaNoEd", "tanaNoEd", "result.tanaNoEd", "true", null, "-9999.9", "9999");
                expect(element.find('input').eq(0).attr('required')).not.toEqual(undefined);
                expect(element.find('input').eq(0).attr('cc-validate-less-equal')).toEqual(undefined);
            });
            
            it("should render html correctly when readonly", function() {
                compile("棚卸番号", "3", "tanaNoSt", "tanaNoSt", "result.tanaNoSt", null, "true", "-9999.9", "9999",
                        "tanaNoEd", "tanaNoEd", "result.tanaNoEd", "false", null, "-9999.9", "9999");
                expect(element.find('input').eq(0).attr('readonly')).not.toEqual(undefined);
                expect(element.find('input').eq(0).attr('cc-validate-less-equal')).toEqual(undefined);
            });
            
            it("should render html correctly when required2 = false and readonly = true and partition is empty", function() {
                compile("棚卸番号", "", "tanaNoSt", "tanaNoSt", "result.tanaNoSt", null, "true", "-9999.9", "9999",
                        "tanaNoEd", "tanaNoEd", "result.tanaNoEd", "false", null, "-9999.9", "9999");
                expect(element.find('input').eq(0).attr('readonly')).toEqual(undefined);
                expect(element.find('input').eq(0).attr('required')).toEqual(undefined);
            });

            it("should render html correctly when required = false and readonly = true and partition is empty", function() {
                compile("棚卸番号", "", "tanaNoSt", "tanaNoSt", "result.tanaNoSt", "false", null, "-9999.9", "9999",
                        "tanaNoEd", "tanaNoEd", "result.tanaNoEd", null, "true", "-9999.9", "9999");
                expect(element.find('input').eq(0).attr('readonly')).toEqual(undefined);
                expect(element.find('input').eq(0).attr('required')).toEqual(undefined);
            });
            it("should render html correctly when required2 = false and readonly = false and partition is empty", function() {
                compile("棚卸番号", "", "tanaNoSt", "tanaNoSt", "result.tanaNoSt", "false", null, "-9999.9", "9999",
                        "tanaNoEd", "tanaNoEd", "result.tanaNoEd", null, "false", "-9999.9", "9999");
                expect(element.find('input').eq(0).attr('readonly')).toEqual(undefined);
                expect(element.find('input').eq(0).attr('required')).toEqual(undefined);
            });
        });
        
        
        describe("2nd input cases", function() {
            it("should render html correctly", function() {
                compile("棚卸番号", "3", "tanaNoSt", "tanaNoSt", "result.tanaNoSt", "true", null, "-9999.9", "9999",
                        "tanaNoEd", "tanaNoEd", "result.tanaNoEd", "true", null, "-9999.9", "9999");
                expect(element.find('input').length).toEqual(2);
                expect(element.find('input').eq(1).attr('type')).toEqual("text");
                expect(element.find('input').eq(1).attr('cc-type')).toEqual("number");
                expect(element.find('input').eq(1).attr('id')).toEqual("tanaNoEd");
                expect(element.find('input').eq(1).attr('name')).toEqual("tanaNoEd");
                expect(element.find('input').eq(1).attr('ng-model')).toEqual("ngModel2");
                expect(element.find('input').eq(1).attr('ng-minlength')).toEqual("1");
                expect(element.find('input').eq(1).attr('cc-blur-validation')).not.toEqual(undefined);
            });
            
            it("should render cc-digit and cc-signed correctly, case 1", function() {
                compile("棚卸番号", "3", "tanaNoSt", "tanaNoSt", "result.tanaNoSt", "true", null, "-9999.99", "9999",
                        "tanaNoEd", "tanaNoEd", "result.tanaNoEd", "true", null, "-9999999.99999", "9999");
                expect(element.find('input').length).toEqual(2);
                expect(element.find('input').eq(1).attr('cc-min')).toEqual("-9999999.99999");
                expect(element.find('input').eq(1).attr('cc-max')).toEqual("9999");
                expect(element.find('input').eq(1).attr('cc-digits-integer')).toEqual("7");
                expect(element.find('input').eq(1).attr('cc-digits-franction')).toEqual("5");
                expect(element.find('input').eq(1).attr('cc-signed')).not.toEqual(undefined);
            });
            
            it("should render cc-digit and cc-signed correctly, case 2", function() {
                compile("棚卸番号", "3", "tanaNoSt", "tanaNoSt", "result.tanaNoSt", "true", null, "-9999.99", "9999",
                        "tanaNoEd", "tanaNoEd", "result.tanaNoEd", "true", null, "999.99", "99999999");
                expect(element.find('input').length).toEqual(2);
                expect(element.find('input').eq(1).attr('cc-min')).toEqual("999.99");
                expect(element.find('input').eq(1).attr('cc-max')).toEqual("99999999");
                expect(element.find('input').eq(1).attr('cc-digits-integer')).toEqual("8");
                expect(element.find('input').eq(1).attr('cc-digits-franction')).toEqual("2");
                expect(element.find('input').eq(1).attr('cc-signed')).toEqual(undefined);
            });
            
            it("should render html correctly when required", function() {
                compile("棚卸番号", "3", "tanaNoSt", "tanaNoSt", "result.tanaNoSt", "true", null, "-9999.9", "9999",
                        "tanaNoEd", "tanaNoEd", "result.tanaNoEd", "true", null, "-9999.9", "9999");
                expect(element.find('input').eq(1).attr('required')).not.toEqual(undefined);
                expect(element.find('input').eq(1).attr('cc-validate-greater-equal')).toEqual("");
            });
            
            it("should render html correctly when readonly", function() {
                compile("棚卸番号", "3", "tanaNoSt", "tanaNoSt", "result.tanaNoSt", null, "true", "-9999.9", "9999",
                        "tanaNoEd", "tanaNoEd", "result.tanaNoEd", null, "true", "-9999.9", "9999");
                expect(element.find('input').eq(1).attr('readonly')).not.toEqual(undefined);
                expect(element.find('input').eq(1).attr('cc-validate-greater-equal')).toEqual(undefined);
            });
            
            it("should render html correctly when required2 = false and readonly = true and partition is empty", function() {
                compile("棚卸番号", "", "tanaNoSt", "tanaNoSt", "result.tanaNoSt", null, "true", "-9999.9", "9999",
                        "tanaNoEd", "tanaNoEd", "result.tanaNoEd", "false", null, "-9999.9", "9999");
                expect(element.find('input').eq(1).attr('readonly')).toEqual(undefined);
                expect(element.find('input').eq(1).attr('required')).toEqual(undefined);
            });

            it("should render html correctly when required = false and readonly = true and partition is empty", function() {
                compile("棚卸番号", "", "tanaNoSt", "tanaNoSt", "result.tanaNoSt", "false", null, "-9999.9", "9999",
                        "tanaNoEd", "tanaNoEd", "result.tanaNoEd", null, "true", "-9999.9", "9999");
                expect(element.find('input').eq(1).attr('readonly')).toEqual(undefined);
                expect(element.find('input').eq(1).attr('required')).toEqual(undefined);
            });
            it("should render html correctly when required2 = false and readonly = false and partition is empty", function() {
                compile("棚卸番号", "", "tanaNoSt", "tanaNoSt", "result.tanaNoSt", "false", null, "-9999.9", "9999",
                        "tanaNoEd", "tanaNoEd", "result.tanaNoEd", null, "false", "-9999.9", "9999");
                expect(element.find('input').eq(1).attr('readonly')).toEqual(undefined);
                expect(element.find('input').eq(1).attr('required')).toEqual(undefined);
            });
        });
    });
    
    describe("logic cases", function() {
       describe("bind valid view values to models and format value", function() {
           it("should bind valid view values to first model", function(){
               compile("棚卸番号", "3", "tanaNoSt", "tanaNoSt", "result.tanaNoSt", "true", null, "-9999.9", "9999",
                       "tanaNoEd", "tanaNoEd", "result.tanaNoEd", "true", null, "-9999.9", "9999");
               var inputElement = element.find('input').eq(0);
               var inputElement2 = element.find('input').eq(1);
               var modelCtrl = inputElement.controller('ngModel');
               
               changeInputValue(inputElement2, '2000.9');
               inputElement.triggerHandler('focus');
               changeInputValue(inputElement, '1000.9');
               
               // model is not in right format
               inputElement.triggerHandler('blur');
               expect(inputElement.val()).toEqual('1,000.9');
               expect(scope.result.tanaNoSt).toEqual('1000.9');
               expect(modelCtrl.$valid).toBe(true);
               
               inputElement.triggerHandler('focus');
               expect(inputElement.val()).toEqual('1000.9');
               expect(scope.result.tanaNoSt).toEqual('1000.9');
               
           });

           it("should bind valid view values to second model", function(){
               compile("棚卸番号", "3", "tanaNoSt", "tanaNoSt", "result.tanaNoSt", "true", null, "-9999.9", "9999",
                       "tanaNoEd", "tanaNoEd", "result.tanaNoEd", "true", null, "-9999.9", "9999");
               var inputElement = element.find('input').eq(0);
               var inputElement2 = element.find('input').eq(1);
               var modelCtrl = inputElement2.controller('ngModel');
               
               changeInputValue(inputElement, '100.9');
               inputElement2.triggerHandler('focus');
               changeInputValue(inputElement2, '1000.9');
               
               // model is not in right format
               inputElement2.triggerHandler('blur');
               expect(inputElement2.val()).toEqual('1,000.9');
               expect(scope.result.tanaNoEd).toEqual('1000.9');
               expect(modelCtrl.$valid).toBe(true);
               
               inputElement2.triggerHandler('focus');
               expect(inputElement2.val()).toEqual('1000.9');
               expect(scope.result.tanaNoEd).toEqual('1000.9');
               
           });
           
           it("should not bind wrong format data to first model", function() {
               compile("棚卸番号", "3", "tanaNoSt", "tanaNoSt", "result.tanaNoSt", "true", null, "-9999.9", "9999",
                       "tanaNoEd", "tanaNoEd", "result.tanaNoEd", "true", null, "-9999.9", "9999");
               var inputElement = element.find('input').eq(0);
               var modelCtrl = inputElement.controller('ngModel');
               
               inputElement.triggerHandler('focus');
               changeInputValue(inputElement, '1.0.00.9');
               
               inputElement.triggerHandler('blur');
               expect(scope.result.tanaNoSt).toEqual(undefined);
               expect(modelCtrl.$valid).toBe(false);
           });

           it("should not bind wrong format data to second model", function() {
               compile("棚卸番号", "3", "tanaNoSt", "tanaNoSt", "result.tanaNoSt", "true", null, "-9999.9", "9999",
                       "tanaNoEd", "tanaNoEd", "result.tanaNoEd", "true", null, "-9999.9", "9999");
               var inputElement2 = element.find('input').eq(1);
               var modelCtrl = inputElement2.controller('ngModel');
               
               inputElement2.triggerHandler('focus');
               changeInputValue(inputElement2, '1.0.00.9');
               
               inputElement2.triggerHandler('blur');
               expect(scope.result.tanaNoEd).toEqual(undefined);
               expect(modelCtrl.$valid).toBe(false);
           });
           
           
           it("should not bind out of range data to first model", function() {
               compile("棚卸番号", "3", "tanaNoSt", "tanaNoSt", "result.tanaNoSt", "true", null, "-9999.9", "9999",
                       "tanaNoEd", "tanaNoEd", "result.tanaNoEd", "true", null, "-9999.9", "9999");
               var inputElement = element.find('input').eq(0);
               var modelCtrl = inputElement.controller('ngModel');
               
               inputElement.triggerHandler('focus');
               changeInputValue(inputElement, '11111111');
               
               inputElement.triggerHandler('blur');
               expect(scope.result.tanaNoSt).toEqual(undefined);
               expect(modelCtrl.$valid).toBe(false);
           });

           it("should not bind out of range data to second model", function() {
               compile("棚卸番号", "3", "tanaNoSt", "tanaNoSt", "result.tanaNoSt", "true", null, "-9999.9", "9999",
                       "tanaNoEd", "tanaNoEd", "result.tanaNoEd", "true", null, "-9999.9", "9999");
               var inputElement2 = element.find('input').eq(1);
               var modelCtrl = inputElement2.controller('ngModel');
               
               inputElement2.triggerHandler('focus');
               changeInputValue(inputElement2, '11111111');
               
               inputElement2.triggerHandler('blur');
               expect(scope.result.tanaNoEd).toEqual(undefined);
               expect(modelCtrl.$valid).toBe(false);
           });
           
           // order cases
           it("should bind in-order data to first model", function() {
               compile("棚卸番号", "3", "tanaNoSt", "tanaNoSt", "result.tanaNoSt", "true", null, "-9999.9", "9999",
                       "tanaNoEd", "tanaNoEd", "result.tanaNoEd", null, "true", "-9999.9", "9999");
               var inputElement1 = element.find('input').eq(0);
               var inputElement2 = element.find('input').eq(1);
               var modelCtrl = inputElement1.controller('ngModel');
               
               changeInputValue(inputElement2, '1111');
               inputElement1.triggerHandler('focus');
               changeInputValue(inputElement1, '111');
               
               inputElement1.triggerHandler('blur');
               expect(scope.result.tanaNoSt).toEqual('111');
               expect(modelCtrl.$valid).toBe(true);
           });
           
           it("should bind in-order data to second model", function() {
               compile("棚卸番号", "3", "tanaNoSt", "tanaNoSt", "result.tanaNoSt", null, "true", "-9999.9", "9999",
                       "tanaNoEd", "tanaNoEd", "result.tanaNoEd", "true", null, "-9999.9", "9999");
               var inputElement1 = element.find('input').eq(0);
               var inputElement2 = element.find('input').eq(1);
               var modelCtrl = inputElement2.controller('ngModel');
               
               changeInputValue(inputElement1, '111');
               inputElement2.triggerHandler('focus');
               changeInputValue(inputElement2, '1111');
               
               inputElement2.triggerHandler('blur');
               expect(scope.result.tanaNoEd).toEqual('1111');
               expect(modelCtrl.$valid).toBe(true);
           });
           
           it("should not bind out of order data to first model", function() {
               compile("棚卸番号", "3", "tanaNoSt", "tanaNoSt", "result.tanaNoSt", "true", null, "-9999.9", "9999",
                       "tanaNoEd", "tanaNoEd", "result.tanaNoEd", null, "true", "-9999.9", "9999");
               var inputElement1 = element.find('input').eq(0);
               var inputElement2 = element.find('input').eq(1);
               var modelCtrl = inputElement1.controller('ngModel');
               
               changeInputValue(inputElement2, '111');
               inputElement1.triggerHandler('focus');
               changeInputValue(inputElement1, '1111');
               
               inputElement1.triggerHandler('blur');
               expect(scope.result.tanaNoSt).toEqual(undefined);
               expect(modelCtrl.$valid).toBe(false);
           });

           it("should not bind out of order data to second model", function() {
               compile("棚卸番号", "3", "tanaNoSt", "tanaNoSt", "result.tanaNoSt", "true", null, "-9999.9", "9999",
                       "tanaNoEd", "tanaNoEd", "result.tanaNoEd", "true", null, "-9999.9", "9999");
               var inputElement1 = element.find('input').eq(0);
               var inputElement2 = element.find('input').eq(1);
               var modelCtrl = inputElement2.controller('ngModel');
               
               changeInputValue(inputElement1, '1111');
               inputElement2.triggerHandler('focus');
               changeInputValue(inputElement2, '111');
               
               inputElement2.triggerHandler('blur');
               expect(scope.result.tanaNoEd).toEqual(undefined);
               expect(modelCtrl.$valid).toBe(false);
           });
       });
    });
});