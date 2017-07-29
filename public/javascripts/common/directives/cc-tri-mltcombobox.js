/**
 * @ngdoc directive
 * @name chasecore.cc-tri-mltcombobox
 * @restrict E
 * @function
 *
 * @description
 * (部品生成時)取引先マスタから『取引先コード＋取引先名称(or略称)』を取得・設定する
 *
 * @example
    <doc:example>
      <doc:source>
         <cc-tri-mltcombobox 
            cc-label="取引先" cc-partition="3" id="triCd" name="triCd"
            ng-model="result.triCd" cc-required=false cc-max="10" cc-delexist=false
            cc-shortname=true cc-parameter01="result.hakkoDay" >
        </cc-tri-mltcombobox>
      </doc:source>
      <doc:scenario>
      </doc:scenario>
    </doc:example>
 */
diretiveApp.directive('ccTriMltcombobox', function(CSS_CLASS, $http, $compile, UserInfo, $rootScope) {
	var unyoDate = UserInfo.unyoDate;
    function getTriDataList(hakkoDay, selectElement, attrs, element, scope) {
        //未設定の場合は運用日付を取得して代用する。
        if (isEmpty(hakkoDay)) {
        	getDataList(unyoDate, selectElement, attrs, element, scope);
        } else {
            getDataList(hakkoDay, selectElement, attrs, element, scope);
        }
    };

    function getDataList(hakkoDay, selectElement, attrs, element, scope) {
        var url = '/core/codename/m011trim4?hakkoDay=' + hakkoDay;

        $http({
            method: 'GET',
            url: url
        }).success(function(data) {
            if (data.length == 0) {
                return;
            }
            
            var log = [];
            //『DataList(仮Ver.)』からComboBoxのDataList(本Ver.)を生成
            angular.forEach(data, function(value, key) {
                if(!(value.kubun == '9' && attrs.ccDelexist=='false')) {
                    // if IE 9
                    //名称項目の後ろにセットされている全角空白を除去する。
                    if(attrs.ccShortname == 'true') {
                        selectElement.append(angular.element("<option></option>").attr("value",value.code).text(
                                value.code +'-'+ value.shortName.trim()));
                    } else if(attrs.ccShortname == 'false') {
                        selectElement.append(angular.element("<option></option>").attr("value",value.code).text(
                                value.code +'-'+ value.name.trim()));
                    }
                }
            }, log);

            $compile(element.contents())(scope);
        }).error(function(data) {
            //deferred.reject('Failed to load data');
        });
    };
    
    return {
        restrict : 'E',
        require : 'ngModel',
        scope : {
            ngModel : '=',
            ccParameter01 : '=',
            ccParameter02 : '=',
            ccParameter03 : '=',
            ccReadonly : '=',
            ccWidth: '=',
            ccError: '='
        },
        template : function(element, attrs) {
            // ui-select2
            var uiSelect2 = '';

            var maximumSelectionSize = '';
            if (!isEmpty(attrs.ccMax)) {
                maximumSelectionSize = ', maximumSelectionSize: ' + attrs.ccMax ;
            }
            
            // either readOnly or required
            var readOnlyRequired = '';
            if (angular.isDefined(attrs.ccReadonly)) {
                readOnlyRequired += ' ng-disabled="ccReadonly" ng-class="{\''+ CSS_CLASS.disable + '\': ccReadonly, \'\': !ccReadonly}"';
                uiSelect2 = ' multiple ui-select2 = "{allowClear: false' + maximumSelectionSize + '}"';
            }
            
            if (angular.isDefined(attrs.ccRequired) && attrs.ccRequired == 'true') {
                readOnlyRequired += ' required ';
                uiSelect2 = ' multiple ui-select2 = "{allowClear: false' + maximumSelectionSize + '}" error-message="{{error}}"';
            } else if (angular.isDefined(attrs.ccRequired) && attrs.ccRequired == 'false') {
                uiSelect2 = ' multiple ui-select2 = "{allowClear: true' + maximumSelectionSize + '}" error-message="{{error}}"';
            }

            // 1) 項目タイトル：パラメータの内容に従う。
            var labelClass = '';
            if (angular.isDefined(attrs.ccPartition) && attrs.ccPartition == '') {
                labelClass += CSS_CLASS.label_divide_zero;
            } else {
                if (angular.isDefined(attrs.ccRequired) && attrs.ccRequired == 'true') {
                    labelClass += CSS_CLASS.cs_label_large_required;
                } else {
                    labelClass += CSS_CLASS.cs_label_large;
                }
            }
            labelClass += CSS_CLASS.BLANK + CSS_CLASS.label;
            
            // 2) コンボボックス：パラメータの内容に従う。
            var boxWidth = 0;
            if (angular.isDefined(attrs.ccMax) && attrs.ccMax != '') {
                boxWidth = attrs.ccMax * 12;
            }
            if (angular.isDefined(attrs.ccShortname) && attrs.ccShortname == 'false') {
                boxWidth = (boxWidth > 50)? boxWidth : 50;
            } else {
                boxWidth = (boxWidth > 20)? boxWidth : 20;
            }

            function geCharCssClass(maxlength) {
                var result = 5;
                if (maxlength >= 1 && maxlength <= 5) {
                    result = CSS_CLASS.char_5;
                } else if (maxlength >= 6 && maxlength <= 10) {
                    result = CSS_CLASS.char_10;
                } else if (maxlength >= 11 && maxlength <= 15) {
                    result = CSS_CLASS.char_15;
                } else if (maxlength >= 16 && maxlength <= 20) {
                    result = CSS_CLASS.char_20;
                } else if (maxlength >= 21 && maxlength <= 30) {
                    result = CSS_CLASS.char_30;
                } else if (maxlength >= 31 && maxlength <= 40) {
                    result = CSS_CLASS.char_40;
                } else if (maxlength >= 41 && maxlength <= 50) {
                    result = CSS_CLASS.char_50;
                } else if (maxlength >= 51 && maxlength <= 60) {
                    result = CSS_CLASS.char_60;
                } else if (maxlength >= 61 && maxlength <= 70) {
                    result = CSS_CLASS.char_70;
                } else if (maxlength >= 71) {
                    result = CSS_CLASS.char_80;
                }
                return result;
            }

            var selectClass = '';
            if (attrs.ccShortname == 'true') {
                selectClass += geCharCssClass(boxWidth) + CSS_CLASS.BLANK + CSS_CLASS.left;
            } else if (attrs.ccShortname == 'false') {
                selectClass += geCharCssClass(boxWidth) + CSS_CLASS.BLANK + CSS_CLASS.left;
            }

            var htmlText = '';

            if (angular.isDefined(attrs.ccPartition) && attrs.ccPartition == '') {
                htmlText += '<div class="'+ labelClass +'" >'+attrs.ccLabel;
            } else {
                htmlText += '<div class="'+labelClass+'">'+attrs.ccLabel;
                if (angular.isDefined(attrs.ccRequired) && attrs.ccRequired == 'true') {
                    htmlText += '<span class="'+ CSS_CLASS.txt_required +'"> *</span>';
                }
            }
            htmlText += '</div>';

            htmlText += '<select '+ uiSelect2 +' class="'+ selectClass +'" id="' + attrs.id + '" name="' + attrs.name
                    + '" ng-model="ngModel" ' + readOnlyRequired;
            if (!isEmpty(attrs.ccWidth)) {
                htmlText += ' ng-style="{width: ccWidth}"';
            }
            if (attrs.ccReadonly == 'true') {
                htmlText += ' />';
            } else {
                htmlText += ' cc-blur-validation />';
            }
            return htmlText;
        },
        link : function(scope, element, attrs, ctrl) {
            var selectElement = angular.element(element.find('select')[0]);
            var selectCtrl = angular.element(element.find('select')[0]).controller('ngModel');

            //Blur event
            selectElement.on("select2-blur", function() {
                scope.$emit(this.id + "Blur");
                attrs.$observe('errorMessage', function(value) {
                    $rootScope.$broadcast('errorMessage', value);
                });
            });
            
            var loadData = function() {
                // init combobox
                selectElement.children().remove();
                //selectElement.append($("<option>&nbsp</option>"));
                if (!ctrl.$pristine) {
                    //消去する当該部品のバインド変数
                    scope.ngModel = undefined;
                }
                //1)パラメータチェック
                var param1 = scope.ccParameter01; // hakkoDay
                getTriDataList(param1, selectElement, attrs, element, scope);
            }

            selectCtrl.$parsers.push(function(value) {
                //console.log('select parser: ' + value);
                return value;
            });
            selectCtrl.$formatters.push(function(value) {
                //console.log('select formatter: ' + value);
                return value;
            });

            // 変更を監視するバインド変数
            //※ cc-parameter01が指定されている場合
            scope.$watchCollection('[ccParameter01]', function(value) {
                loadData();
            });

            scope.error = null;
            scope.$on("ccError", function(event, value) {
                scope.error = null;
                selectCtrl.$setValidity('ccClientError', true);
                if (!isEmpty(value)) {
                    angular.forEach(value, function(error, key) {
                        if (angular.equals(error.name, attrs.name) && 
                            (angular.equals(error.level, 'E')||angular.equals(error.level, 'W'))) {
                            scope.error = error;
                            if (error.clientErrorFlag == true) {
                                selectCtrl.$setValidity('ccClientError', false);
                            } else {
                                selectCtrl.$setValidity('ccClientError', true);
                            }
                        }
                    });
                }
            });

            selectCtrl.$parsers.push(function(viewValue) {
                scope.error = null;
                scope.$emit('ccResetServerClientError', attrs.name);
                return viewValue;
            });

            scope.$on("pressF2Key", function(event, data) {
                selectElement.select2("close");
            });
            scope.$on("pressF3Key", function(event, data) {
                selectElement.select2("close");
            });
            //open event
            selectElement.on("select2-open", function() {
                scope.$parent.$broadcast("select2-open", attrs.id);
            });
        }
    }
});