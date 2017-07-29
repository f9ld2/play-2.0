/**
 * @ngdoc directive
 * @name chasecore.cc-mtori-mltcombobox
 * @restrict E
 * @function
 *
 * @description
 * (部品生成時)名称マスタから『コード＋名称』を取得・設定する
 *
 * @example
    <doc:example>
      <doc:source>
         <cc-mtori-mltcombobox
            cc-label="エリア"
            cc-partition="3"
            id="areaCd"
            name="areaCd"
            ng-model="cond.areaCd"
            cc-required=true
            cc-max="10"
            cc-delexist=true
            cc-shortname=false
            cc-parameter01="cond.hakkoDay">
        </cc-mtori-mltcombobox>
      </doc:source>
      <doc:scenario>
      </doc:scenario>
    </doc:example>
 */

diretiveApp.directive('ccMtoriMltcombobox', function(CSS_CLASS, $http, $compile, UserInfo, $rootScope) {
	var unyoDate = UserInfo.unyoDate;
    function getListMtori(hakkoDay, selectElement, delexist, shortName, element, scope) {
          //未設定の場合は運用日付を取得して代用する。
          if (isEmpty(hakkoDay)) {
              getDataListMtori(unyoDate, selectElement, delexist, shortName, element, scope);
          } else {
              getDataListMtori(hakkoDay, selectElement, delexist, shortName, element, scope);
          }
      }
    
    // call server get data.
    function getDataListMtori(hakkoDay, selectElement, delexist, shortName, element, scope) {
        $http({
            method : 'GET',
            url : '/core/codename/m011trim3?hakkoDay=' + hakkoDay
        }).success(function(data) {
            if (data.length == 0) {
                selectElement.children().remove();
                return;
            }
            // 3) 『DataList(仮Ver.)』からComboBoxのDataList(本Ver.)を生成
            var log = [];
            angular.forEach(data, function(value, key) {
                if (!(value.kubun == '9' && delexist == 'false')) {
                    // if IE 9
                    if (shortName == "true") {
                        selectElement.append(angular.element("<option></option>").attr("value", value.code.trim()).text(
                                value.code.trim() + ':' + value.shortName.trim()));
                    }
                    else {
                        selectElement.append(angular.element("<option></option>").attr("value", value.code.trim()).text(
                                value.code.trim() + ':' + value.name.trim()));
                    }
                    
                }
            }, log);
            $compile(element.contents())(scope);
        }).error(function(data) {
        });
    }
    return {
        restrict : 'E',
        require : 'ngModel',
        scope : {
            ngModel : '=',
            ccParameter01 : '=',
            ccReadonly: '=',
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
                uiSelect2 = 'ui-select2 = "{allowClear: false' + maximumSelectionSize + '}"';
            }
            
            if (angular.isDefined(attrs.ccRequired) && attrs.ccRequired == 'true') {
                readOnlyRequired += ' required ';
                // パラメータから[allowClear]の値を設定する。
                uiSelect2 = 'ui-select2 = "{allowClear: false' + maximumSelectionSize + '}" error-message="{{error}}"';
            } else if (angular.isDefined(attrs.ccRequired) && attrs.ccRequired == 'false') {
                // パラメータから[allowClear]の値を設定する。
                uiSelect2 = 'ui-select2 = "{allowClear: true' + maximumSelectionSize + '}" error-message="{{error}}"';
            }

            // style for label
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

            // class for select
            var selectClass = '';
            
            //範囲[最大値](cc-max)
            var maxsize = isEmpty(attrs.ccMax) ? 1 : parseInt(attrs.ccMax);
            
            //①選択済フィールドの幅
            //パラメータの内容によって計算する。
            //ボタンサイズ      ( 2 byte + 6  byte ) * 範囲[最大値]
            var fieldCss = 8 * maxsize;
            
            //②リストフィールドの幅
            //パラメータの内容によって計算する。
            var paraCss = 0;
            if (attrs.ccShortname == 'true') {
                paraCss += 10 ;
            } else if (attrs.ccShortname == 'false') {
                paraCss += 40 ;
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
                htmlText += '<div class="' + labelClass + '" >' + attrs.ccLabel;
            } else {
                htmlText += '<div class="' + labelClass + '">' + attrs.ccLabel;
                if (angular.isDefined(attrs.ccRequired) && attrs.ccRequired == 'true') {
                    htmlText += '<span class="' + CSS_CLASS.txt_required + '"> *</span>';
                }
            }
            htmlText += '</div>';

            htmlText += '<select ' + uiSelect2 + ' multiple class="' + selectClass + '" id="' + attrs.id + '" name="'
                    + attrs.name + '" ng-model="ngModel" ' + readOnlyRequired;
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

            var firstLoad = true;
            var selectCtrl = angular.element(element.find('select')[0]).controller('ngModel');
            var selectElement = angular.element(element.find('select')[0]);
            
            //Blur event
            selectElement.on("select2-blur", function() {
                scope.$emit(this.id + "Blur");
                attrs.$observe('errorMessage', function(value) {
                    $rootScope.$broadcast('errorMessage', value);
                });
            });
            
            var loadData = function() {
                selectElement.children().remove();
                if (!firstLoad) {
                    scope.ngModel = undefined;
                } else {
                    firstLoad = false;
                }

                getListMtori(scope.ccParameter01, selectElement, attrs.ccDelexist, attrs.ccShortname, element, scope);
            }

            // パラメータ(cc-parameterxx)に指定されているバインド変数が変更された場合は当該部品のバインド変数を消去する。
            scope.$watch('ccParameter01', function(value) {
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
