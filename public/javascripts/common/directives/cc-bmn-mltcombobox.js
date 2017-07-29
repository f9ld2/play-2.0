/**
 * @ngdoc directive
 * @name chasecore.directive:cc-bmn-input
 * @restrict A
 * @function
 *
 * @description
 * 002:部門００２
 *
 * @example
    <doc:example>
      <doc:source>
         <cc-bmn-mltcombobox cc-label="部門" cc-partition="3" id="bmnCd" name="bmnCd" ng-model="result.bmnCd" 
             cc-required=true cc-max="10" cc-delexist=true cc-shortname=false
            cc-parameter01="result.jgyobuCd" cc-parameter02="result.hakkoDay" />
      </doc:source>
      <doc:scenario>
      </doc:scenario>
    </doc:example>
 */

diretiveApp.directive('ccBmnMltcombobox', function(CSS_CLASS, $http, $compile, UserInfo) {
	var unyoDate = UserInfo.unyoDate;
    function getListDepartment( jgyobuCd, hakkoDay, selectElement, attrs, element, scope) {
      //未設定の場合は運用日付を取得して代用する。
        if(isEmpty(hakkoDay)) {
        	getDataListDepartment(jgyobuCd, unyoDate, selectElement, attrs, element, scope);
        } else {
            getDataListDepartment(jgyobuCd, hakkoDay, selectElement, attrs, element, scope);
        }
    }
    
    function getDataListDepartment(jgyobuCd, hakkoDay, selectElement, attrs, element, scope){
        var url = '';
        if(isEmpty(jgyobuCd)) {
            url = '/core/codename/m000depars?hakkoDay=' + hakkoDay + '&ccDelexist=' + attrs.ccDelexist
        } else {
            url = '/core/codename/m000depar/' + jgyobuCd + '?hakkoDay=' + hakkoDay + '&ccDelexist=' + attrs.ccDelexist
        }

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
                // if IE 9
                //名称項目の後ろにセットされている全角空白を除去する。
                if(attrs.ccShortname == 'true') {
                    selectElement.append(angular.element("<option></option>").attr("value",value.code).text(
                            value.code +':'+ value.shortName.trim()));
                } else if(attrs.ccShortname == 'false') {
                    selectElement.append(angular.element("<option></option>").attr("value",value.code).text(
                            value.code +':'+ value.name.trim()));
                }
                
            }, log);
            $compile(element.contents())(scope);
        }).error(function(data) {
            deferred.reject('Failed to load data');
        });
    }
    
    return {
        restrict: 'E',
        require: 'ngModel',
        scope : {
            ngModel : '=',
            ccParameter01 : '=',
            ccParameter02 : '=',
            ccReadonly: '=',
            ccWidth: '=',
            ccError: '='
        },
        template: function (element, attrs) {
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
                uiSelect2 = 'ui-select2 = "{ allowClear: false ' + maximumSelectionSize + '}"';
            }

            if (angular.isDefined(attrs.ccRequired) && attrs.ccRequired == 'true') {
                readOnlyRequired += ' required ';
                uiSelect2 = 'ui-select2 = "{ allowClear: false' + maximumSelectionSize + '}" error-message="{{error}}"';
            } else if (angular.isDefined(attrs.ccRequired) && attrs.ccRequired == 'false') {
                uiSelect2 = 'ui-select2 = "{ allowClear: true' + maximumSelectionSize + '}" error-message="{{error}}"';
            }

            // style for label
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

            // class for select
            var selectClass = '';
            
            //範囲[最大値](cc-max)
            var maxsize = isEmpty(attrs.ccMax) ? 1 : parseInt(attrs.ccMax);
            
            //①選択済フィールドの幅
            //パラメータの内容によって計算する。
            //ボタンサイズ      ( 2 byte + 3 byte ) * 範囲[最大値]
            var fieldCss = 5 * maxsize;
            
            //②リストフィールドの幅
            //パラメータの内容によって計算する。
            var paraCss = 0;
            if (attrs.ccShortname == 'true') {
                paraCss += 15 ;
            } else if (attrs.ccShortname == 'false') {
                paraCss += 50 ;
            }
            
            //※   『①選択済フィールドの幅』と『②リストフィールドの幅』をそれぞれ計算し、大きい方の幅を採用する。
            //  if  ①選択済フィールドの幅      >   ②リストフィールドの幅
            //        コンボボックスの幅      :=      ①選択済フィールドの幅
            //  else
            //        コンボボックスの幅      :=      ②リストフィールドの幅
            //  end if  
            if (fieldCss > paraCss) {
                selectClass = getCharCssClass( CSS_CLASS, fieldCss) + CSS_CLASS.BLANK + CSS_CLASS.left;
            } else {
                selectClass = getCharCssClass( CSS_CLASS, paraCss) + CSS_CLASS.BLANK + CSS_CLASS.left;
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

            htmlText += '<select '+uiSelect2+' multiple class="'+ selectClass +'" id="' + attrs.id + '" name="' + attrs.name
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
        link: function(scope, element, attrs, ctrl) {
            var firstLoad = true;
            var selectCtrl = angular.element(element.find('select')[0]).controller('ngModel');
            var selectElement = angular.element(element.find('select')[0]);

            //コンボボックスのリストを生成する。
            var loadData = function() {
                // init combobox
                selectElement.children().remove();
                if (!firstLoad) {
                    //消去する当該部品のバインド変数
                    scope.ngModel = undefined;
                } else {
                    firstLoad = false;
                }
                
                getListDepartment(scope.ccParameter01, scope.ccParameter02, selectElement, attrs,
                                element, scope);
            }
            //変更を監視するバインド変数
            //※ cc-parameter01が指定されている場合
            //※ cc-parameter02が指定されている場合
            scope.$watchCollection('[ccParameter01, ccParameter02]', function(value) {
                loadData();
            });

            scope.error = null;
            scope.$on("ccError", function(event, value) {
                scope.error = null;
                selectCtrl.$setValidity('ccSelect', true);
                if (!isEmpty(value)) {
                    angular.forEach(value, function(error, key) {
                        if (angular.equals(error.name, attrs.name) && 
                            (angular.equals(error.level, 'E')||angular.equals(error.level, 'W'))) {
                            scope.error = error;
                            if (error.clientErrorFlag == true) {
                                selectCtrl.$setValidity('ccSelect', false);
                            } else {
                                selectCtrl.$setValidity('ccSelect', true);
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

