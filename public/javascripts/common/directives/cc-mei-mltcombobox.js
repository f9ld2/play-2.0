/**
 * @ngdoc directive
 * @name chasecore.cc-mei-mltcombobox
 * @restrict E
 * @function
 *
 * @description
 * (部品生成時)名称マスタから『コード＋名称』を取得・設定する
 *
 * @example
    <doc:example>
      <doc:source>
         <cc-mei-mltcombobox
            cc-label="エリア"
            cc-partition="3"
            id="areaCd"
            name="areaCd"
            ng-model="cond.areaCd"
            cc-required=true
            ng-maxlength="2"
            cc-delexist=true
            cc-key1="MA"
            cc-key2="TEN"
            cc-shortname=false
            cc-parameter01="cond.hakkoDay">
        </cc-mei-mltcombobox>
      </doc:source>
      <doc:scenario>
      </doc:scenario>
    </doc:example>
 */

diretiveApp.directive('ccMeiMltcombobox', function(CSS_CLASS, $http, $compile, UserInfo) {
	var unyoDate = UserInfo.unyoDate;
    function getListMei(cdKbn, cd, hakkoDay, selectElement, shortName, delexist, element, scope) {
        //未設定の場合は運用日付を取得して代用する。
          if (isEmpty(hakkoDay)) {
              getDataListMei(cdKbn, cd, unyoDate, selectElement, shortName, delexist, element, scope);
          } else {
              getDataListMei(cdKbn, cd, hakkoDay, selectElement, shortName, delexist, element, scope);
          }
      }
    
    // call server get data.
    function getDataListMei(cdKbn, cd, hakkoDay, selectElement, shortName, delexist, element, scope) {
        $http({
            method : 'GET',
            url : '/core/codemaster/m017meim/' + cdKbn + '/' + cd + '?hakkoDay=' + hakkoDay
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
                    selectElement.append(angular.element("<option></option>").attr("value", value.code.trim()).text(
                                    value.code.trim() + ':' + value.name.trim()));
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
            ccReadonly : '=',
            ccWidth: '='
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
                uiSelect2 = 'ui-select2 = "{allowClear: false' + maximumSelectionSize + '}"';
            } else if (angular.isDefined(attrs.ccRequired) && attrs.ccRequired == 'false') {
                // パラメータから[allowClear]の値を設定する。
                uiSelect2 = 'ui-select2 = "{allowClear: true' + maximumSelectionSize + '}"';
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
            //ボタンサイズ      ( 2 byte + 桁数[最大] ) * 範囲[最大値]
            //桁数[最大] = 0
            var fieldCss = 2 * maxsize;
            
            //②リストフィールドの幅
            //パラメータの内容によって計算する。
            var paraCss = 0;
            if (attrs.ccShortname == 'true') {
                paraCss += 10 ;
            } else if (attrs.ccShortname == 'false') {
                paraCss += 40 ;
            }
            paraCss += parseInt(attrs.ngMaxlength) + 1;
            
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
            // コンボボックスのリストを生成する時の注意事項
            
            var loadData = function() {
                selectElement.children().remove();
                if (!firstLoad) {
                    scope.ngModel = undefined;
                } else {
                    firstLoad = false;
                }
                // 1) パラメータチェック
                //未設定の場合は処理を終了する。(エラーは設定しない)
                if (isEmpty(attrs.ccKey1) || isEmpty(attrs.ccKey2)) {
                    return;
                }
                getListMei(attrs.ccKey1, attrs.ccKey2, scope.ccParameter01,
                        selectElement, attrs.ccShortname, attrs.ccDelexist, element, scope);
            }

            // パラメータ(cc-parameterxx)に指定されているバインド変数が変更された場合は当該部品のバインド変数を消去する。
            scope.$watch('ccParameter01', function(value) {
                loadData();
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
