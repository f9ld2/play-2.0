/**
 * @ngdoc directive
 * @name chasecore.cc-ten-mltcombobox
 * @restrict E
 * @function
 *
 * @description
 * (部品生成時)店舗マスタから『店舗コード＋店舗名称(or略称)』を取得・設定する
 *
 * @example
    <doc:example>
      <doc:source>
         <cc-ten-mltcombobox 
            cc-label="店舗" cc-partition="3" id="tenCd" name="tenCd"
            ng-model="result.tenCd" cc-required=false cc-max="10" cc-delexist=false
            cc-shortname=true cc-parameter01="result.kaisyaCd" 
            cc-parameter02="result.jigyobuCd" cc-parameter03="result.hakkoDay" >
        </cc-ten-mltcombobox>
      </doc:source>
      <doc:scenario>
      </doc:scenario>
    </doc:example>
 */
diretiveApp.directive('ccTenMltcombobox', function(CSS_CLASS, $http, $compile, UserInfo, $rootScope) {
	var unyoDate = UserInfo.unyoDate;
    function getTenDataList(kaisyaCd, jigyobuCd, hakkoDay, selectElement, attrs, element, dataList, scope) {
        //未設定の場合は運用日付を取得して代用する。
        if (isEmpty(hakkoDay)) {
        	getDataList(kaisyaCd, jigyobuCd, unyoDate, selectElement, attrs, element, dataList, scope);
        } else {
            getDataList(kaisyaCd, jigyobuCd, hakkoDay, selectElement, attrs, element, dataList, scope);
        }
    };

    function getDataList(kaisyaCd, jigyobuCd, hakkoDay, selectElement, attrs, element, dataList, scope) {
        var url = '/core/codename/m006tenm2/'+ kaisyaCd + '/' + jigyobuCd + '?hakkoDay=' + hakkoDay;

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
                        selectElement.append($("<option></option>").attr("value",value.code).text(
                                value.code +':'+ value.shortName.trim()));
                    } else if(attrs.ccShortname == 'false') {
                        selectElement.append($("<option></option>").attr("value",value.code).text(
                                value.code +':'+ value.name.trim()));
                    }

                    dataList.push(value);
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
                boxWidth = attrs.ccMax * 5;
            }
            if (angular.isDefined(attrs.ccShortname) && attrs.ccShortname == 'false') {
                boxWidth = (boxWidth > 50)? boxWidth : 50;
            } else {
                boxWidth = (boxWidth > 15)? boxWidth : 15;
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
                } else if (maxlength >= 41) {
                    result = CSS_CLASS.char_50;
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
                htmlText += ' cc-blur-validation-combobox />';
            }
            return htmlText;
        },
        link : function(scope, element, attrs, ctrl) {
            var dataList = [];
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
                var param1 = scope.ccParameter01; // kaisyaCd
                var param2 = scope.ccParameter02; // jigyobuCd
                var param3 = scope.ccParameter03; // hakkoDay
                if (isEmpty(param1) || isEmpty(param2)) {
                    return;
                } else {
                    getTenDataList(param1, param2, param3, selectElement, attrs, element, dataList, scope);
                }
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
            //※ cc-parameter02が指定されている場合
            //※ cc-parameter03が指定されている場合
            scope.$watchCollection('[ccParameter01, ccParameter02, ccParameter03]', function(value) {
                loadData();
            });

            selectCtrl.$parsers.push(function(viewValue) {
                scope.error = null;
                scope.$emit('ccResetServerClientError', attrs.name);
                scope.$broadcast("resetServerError");
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