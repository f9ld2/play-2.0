/**
 * @ngdoc directive
 * @name chasecore.cc-jancd-input
 * @restrict E
 * @function
 * 
 * @description
 * (コード入力時)基本情報マスタから『商品名称(略称を含む)』を取得・表示する
 * 
 * @example <doc:example> <doc:source>
 * <cc-jancd-input 
        cc-label="ＪＡＮ"
        cc-partition="3"
        id="janCd"
        name="janCd"
        ng-model="cond.janCd"
        cc-readonly=true
        cc-notexist=false
        cc-delexist=true
        cc-shortname=false
        cc-parameter01="cond.hakkoDay"
        id2="shnNm"
        name2="shnNm"
        ng-model2="cond.shnNm" >
    </cc-jancd-input>
 * </doc:source> <doc:scenario> </doc:scenario> </doc:example>
 */

diretiveApp.directive('ccJancdInput', function(CSS_CLASS, $http, $timeout, UserInfo, $interval) {
    var unyoDate = UserInfo.unyoDate;

    // STS_NORMAL : 正常終了
    var STS_NORMAL = 0;
    // STS_ERROR_JANCD : ＪＡＮコードエラー (ER01)
    var STS_ERROR_JANCD = -1;
    // STS_ERROR_CHECKDIGIT : チェックディジットエラー (ER02)
    var STS_ERROR_CHECKDIGIT = -2;

    /**
     * CheckDigitのチェック
     *
     * @param jan_cd
     *            チェックディジット付き13桁JANコード
     * @return true:正常、false:異常
     */
    function isCheckDigit(jan_cd) {
        if (jan_cd.substr(-1) != getCheckDigit13(jan_cd.substr(0, 12) + "0")) {
            return false; // checkDigitError
        }
        return true;
    }

    /**
     * 13桁商品コードチェックデジット返却関数(モジュラス１０)
     *
     * @param inNumber
     *            13桁の商品コード
     * @return CheckDigit
     */
    function getCheckDigit13(inNumber) {
        // チェックディジットの計算方法
        var intsum = 0;
        var strmoji = "";
        for (var i = 0; i < 13; i++) {
            strmoji = inNumber.substr(i, 1);
            var intculc = 0;
            if ((i % 2) == 0) {
                intculc = parseInt(strmoji) * 1;
            } else {
                intculc = parseInt(strmoji) * 3;
            }
            intsum += intculc;
        }

        var strCD = intsum.toString().substr(-1);
        if (strCD != "0") {
            strCD = (10 - parseInt(strCD)).toString();
        }
        return strCD;
    }

    /**
     * 引数に指定されたコードを変換した値を格納します。
     *
     * @param strJanCode
     *            変換したいJANコード
     * @return 実行ステータス<BR>
     *         STS_NORMAL : 正常終了 STS_ERROR_JANCD : ＪＡＮコードエラー (ER01)
     *         STS_ERROR_CHECKDIGIT : チェックディジットエラー (ER02)
     */
    return {
        restrict: 'E',
        require: 'ngModel',
        scope: {
            ngModel: '=',
            ngModel2: '=',
            ccParameter01: '=',
            ccReadonly: '=',
            ccError: '=',
            ccWidth1: '=',
            ccWidth2: '=',
            ccShnKbn: '=',
            ccFocus: '='
        },
        template: function(element, attrs) {
            // either readOnly or required
            var readOnlyRequired = '';
            var inputClass = " ";
            var inputClass2 = CSS_CLASS.BLANK + CSS_CLASS.disable;
            if (angular.isDefined(attrs.ccFocus)) {
                readOnlyRequired += ' cc-focus="ccFocus"';
            }
            if (angular.isDefined(attrs.ccReadonly)) {
                readOnlyRequired += ' ng-disabled="ccReadonly" ng-class="{\'' + CSS_CLASS.disable + '\': ccReadonly, \'\': !ccReadonly}" ';
            }
            if (angular.isDefined(attrs.ccReadonly) && attrs.ccReadonly == 'true') {
                readOnlyRequired += ' readonly ';
                inputClass += CSS_CLASS.BLANK + CSS_CLASS.disable;
            }
            if (angular.isDefined(attrs.ccRequired) && attrs.ccRequired == 'true') {
                readOnlyRequired += ' required ';
            }

            // style for label
            var labelClass = '';
            if (angular.isDefined(attrs.ccPartition) && attrs.ccPartition == '') {
                labelClass += CSS_CLASS.label_divide_zero;
            } else {
                if (angular.isDefined(attrs.ccPartition) && attrs.ccRequired == 'true') {
                    labelClass += CSS_CLASS.cs_label_large_required;
                } else {
                    labelClass += CSS_CLASS.cs_label_large;
                }
            }
            labelClass += CSS_CLASS.BLANK + CSS_CLASS.label;

            // maxlength for nameInput
            var maxlength = '';
            if (attrs.ccShortname == 'true') {
                maxlength = '5';
            } else if (attrs.ccShortname == 'false') {
                maxlength = '20';
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

            htmlText += '<input type="text" class="' + inputClass + '" cc-type="code" id="' + attrs.id + '" name="' + attrs.name + '" ng-model="ngModel" ' + readOnlyRequired + '  maxlength="13" onKeyDown="return setNextFocus(this);" ';
            if (!isEmpty(attrs.ccWidth1)) {
                htmlText += ' ng-style="{width: ccWidth1}"';
            }
            if (attrs.ccReadonly == 'true') {
                htmlText += ' />';
            } else {
                htmlText += ' ng-minlength="6" cc-blur-validation error-message="{{error}}" ui-event="{ blur : \'blurCallback(ngModel)\' }"';
                htmlText += ' />';
            }

            if (angular.isUndefined(attrs.ngModel2)) {
                return htmlText;
            }

            htmlText += '&nbsp';
            htmlText += '<input type="text" class=' + inputClass2 + ' cc-type="zenkaku" id="' + attrs.id2 + '" name="' + attrs.name2 + '" ng-model="ngModel2" ng-disabled="true" maxlength="' + maxlength + '" ';
            if (!isEmpty(attrs.ccWidth2)) {
                htmlText += ' ng-style="{width: ccWidth2}" />';
            } else {
                htmlText += ' />';
            }
            return htmlText;
        },
        link: function(scope, element, attrs, ctrl) {
            //***************************
            // janCode after converting
            var strJanConvert = '';
            var strShnPluKbn = '';
            var promise;
            var stopToken;

            function getJanCodeConvert() {
                return strJanConvert;
            }

            function setJanCodeConvert(value) {
                strJanConvert = value;
            }

            function checkControl(strJanCode) {

				//入力値をtrim
                var strInCode = strJanCode.trim();

				//入力桁数で判定 13桁
                if (strInCode.length == 13) {

					// 0パテはエラー
                    if (strInCode == "0000000000000") {
                        return STS_ERROR_JANCD;
                    }

/*
					// 先頭7桁が0パテ 丸合にはこの体系はない
                    if (strInCode.substr(0, 7) == "0000000") {
                        return checkControl(strInCode.substr(7, 6));
                    }

					// 先頭5桁が0パテ 丸合にはこの体系はない
                    if (strInCode.substr(0, 5) == "00000") {
                        return checkControl(strInCode.substr(5, 8));
                    }
*/

                    // JAN-13
                    if (strInCode.substr(0, 2) == "45" || strInCode.substr(0, 2) == "49") {
						// チェックデジットの確認
                        if (!isCheckDigit(strInCode)) {
                            return STS_ERROR_CHECKDIGIT;
                        }
                        strJanConvert = strInCode;
                        strShnPluKbn = '00';
                        return STS_NORMAL;

                    // NON-PLU-13
                    } else if (strInCode.substr(0, 2) == "21" || 
                               strInCode.substr(0, 2) == "25" || 
                               strInCode.substr(0, 2) == "26" || 
                               strInCode.substr(0, 2) == "02"
							  ) {
/*
						// チェックデジットの確認
                        if (!isCheckDigit(strInCode)) {
                            return STS_ERROR_CHECKDIGIT;
                        }
*/
                        strJanConvert = strInCode;
                        strShnPluKbn = '05';
                        return STS_NORMAL;

                    // 代表部門コード（部門）、代表部門コード（中分類）
					// ※本来は、EANのアメリカに該当するのでよろしくないが、現行仕様に従う。
                    } else if (strInCode.substr(0, 2) == "00" ) {
/*
						// チェックデジットの確認
                        if (!isCheckDigit(strInCode)) {
                            return STS_ERROR_CHECKDIGIT;
                        }
*/
                        strJanConvert = strInCode;
                        strShnPluKbn = '06';
                        return STS_NORMAL;

                    } else {

                        topNo = parseInt(strInCode.substr(0, 1));
                        // EAN-13
                        if (topNo >= 3 && topNo <= 9) {
							// チェックデジットの確認
                            if (!isCheckDigit(strInCode)) {
                                return STS_ERROR_CHECKDIGIT;
                            }
                            strJanConvert = strInCode;
                            strShnPluKbn = '03';
                            return STS_NORMAL;
                        }
                    }
                } 
				//入力桁数で判定 10,11桁
				// UPC-Aはチェックディジットを入力させない
				else if (strInCode.length == 10 || strInCode.length == 11) {

					// 0パテかどうか確認
                    if (strInCode.length == 10) {
                        if (strInCode == "0000000000") {
                            return STS_ERROR_JANCD;
                        }
						// 入力が10桁だったら、11桁に揃える
                        strInCode = "0" + strInCode;
                    } else {
                        if (strInCode == "00000000000" ||
                            strInCode.substr(0, 1) != "0") {
                            return STS_ERROR_JANCD;
                        }
                    }

					// 11桁の2文字目が2,4の場合、入力を許さずエラーとする
					// ※UPCは本来CD無しの11桁。12桁2文字目＝11桁の1桁目＝NS(ナンバーシステムキャラクタ)
					//   2,4はUPCの規格上インストアにあたる
                    if (strInCode.substr(1, 1) == "2" || strInCode.substr(1, 1) == "4") {
                        return STS_ERROR_JANCD;
                    }

                    // チェックデジットを計算、CD付きの13桁を返す
                    var strZeroPd = strInCode + "00";
                    strInCode = strInCode + getCheckDigit13(strZeroPd);
                    strJanConvert = "0" + strInCode;
                    strShnPluKbn = '01';
                    return STS_NORMAL;
                } 
				//入力桁数で判定 8桁
				else if (strInCode.length == 8) {

                    // JAN-8
                    if (strInCode.substr(0, 2) == "45" || strInCode.substr(0, 2) == "49") {

						// 13桁とみなした上でチェックデジットの確認
                        strInCode = "00000" + strInCode;
                        if (!isCheckDigit(strInCode)) {
                            return STS_ERROR_CHECKDIGIT;
                        }
                        strJanConvert = strInCode;
                        strShnPluKbn = '00';
                        return STS_NORMAL;

                    // PLU-8
                    } else if (strInCode.substr(0, 2) == "00") {

						// 13桁とみなした上でチェックデジットの確認
                        strInCode = "00000" + strInCode;
                        if (!isCheckDigit(strInCode)) {
                            return STS_ERROR_CHECKDIGIT;
                        }
                        strJanConvert = strInCode;
                        strShnPluKbn = '02';
                        return STS_NORMAL;

                    } else {

                        // EAN-8
                        topNo = parseInt(strInCode.substr(0, 1));
                        if (topNo >= 3 && topNo <= 9) {

                            strInCode = "00000" + strInCode;
                            if (!isCheckDigit(strInCode)) {
                                return STS_ERROR_CHECKDIGIT; // checkDigitError
                            }
                            strJanConvert = strInCode;
                            strShnPluKbn = '03';
                            return STS_NORMAL;
                        }
                    }
                }
				//入力桁数で判定 7,6桁
				else if (strInCode.length == 7 || strInCode.length == 6) {

                    // 0パテか確認
                    if (strInCode.length == 6) {
                        if (strInCode == "000000") {
                            return STS_ERROR_JANCD;
                        }
						// 入力が6桁だったら、7桁に揃える
                        strInCode = "0" + strInCode;
                    } else {
                        if (strInCode == "0000000" ||
                            strInCode.substr(0, 1) != "0") {
                            return STS_ERROR_JANCD;
                        }
                    }

/* UPC-Eはチェックディジットを入力させるがチェックしない
					// 13桁とみなして、チェックデジットを確認する（UPC-EからAに変換してAのCDを計算するのが正しいが、既存仕様を採用する）
                    strInCode = "000000" + strInCode;
                    if (!isCheckDigit(strInCode)) {
                        return STS_ERROR_CHECKDIGIT;
                    }
 */
                    strJanConvert = strInCode;
                    strShnPluKbn = '01';
                    return STS_NORMAL;

                }
				//入力桁数で判定に該当しない場合エラーとする
				else {
	                return STS_ERROR_JANCD;
                }

				// ここには到達しないが念のため
                return STS_ERROR_JANCD;

            }

            //***************************
            var inputCtrl = angular.element(element.find('input')[0]).controller('ngModel');
            // check render second input
            var isZenDefined = angular.isDefined(attrs.id2) && angular.isDefined(attrs.name2) && angular.isDefined(attrs.ngModel2) && attrs.ngModel2 != '';
            var record = null;
            var checkData = false;
            var sts_result = STS_NORMAL;

            // 3) 基本情報Ｍアクセス
            var requestWithHakkoDay = function(value, hakkoDay) {
                $http({
                    method: 'GET',
                    url: '/core/codemaster/m007kijm/' + value + '?hakkoDay=' + hakkoDay
                }).success(function(data) {
                    record = data;
                    // 4) アクセス結果の判定
                    if (record.length == 0) {
                        // 無し
                        if (attrs.ccNotexist == 'true') {
                            checkData = true;
                        } else if (attrs.ccNotexist == 'false') {
                            checkData = false;
                        }
                        // 稼動区分[9]であるレコード
                    } else if (record[0].kubun == '9') {
                        if (attrs.ccDelexist == 'true') {
                            checkData = true;
                        } else if (attrs.ccDelexist == 'false') {
                            checkData = false;
                        }
                    } else {
                        // 稼動区分[9]でないレコード
                        checkData = true;
                    }

                    if (!checkData) {
                        inputCtrl.$setValidity('ccExist', false);
                        scope.ngModel2 = '';
                    } else {
                        inputCtrl.$setValidity('ccExist', true);
                    }
                });
            };

            function validation(value) {
                // init data
                record = null;
                checkData = false;
                sts_result = STS_NORMAL;
                setJanCodeConvert('');
                if (angular.isDefined(attrs.ngModel2)) {
                    scope.ngModel2 = '';
                }
                // reset error
                inputCtrl.$setValidity('ccCode', true);
                inputCtrl.$setValidity('ccCheckdigit', true);
                inputCtrl.$setValidity('ccExist', true);

                // 1) パラメータチェック
                if (isEmpty(value)) {
                    return value;
                }

                // 2) ＪＡＮコードの変換
                // sts_result : 変換結果
                sts_result = checkControl(value);
                if (sts_result == STS_NORMAL) {
                    // 変換エラー無し

                    if (inputCtrl.$viewValue.length !== 13) {
                        inputCtrl.$setValidity('ccCode', false);
                    }

                    if (isZenDefined) {
                        // 変換ＪＡＮコードを使って『3)基本情報Ｍアクセス』
                        var hakkoDay = scope.ccParameter01;
                        if (isEmpty(hakkoDay)) {
                            requestWithHakkoDay(" " + getJanCodeConvert(), unyoDate);
                        } else {
                            requestWithHakkoDay(" " + getJanCodeConvert(), hakkoDay);
                        }
                    }
                } else if (sts_result == STS_ERROR_JANCD) {
                    // エラー種類[ER01]
                    inputCtrl.$setValidity('ccCode', false);
                } else if (sts_result == STS_ERROR_CHECKDIGIT) {
                    // エラー種類[ER02]
                    inputCtrl.$setValidity('ccCheckdigit', false);
                }
                return value;
            }

            //SetFocus時の書式
            element.find('input').bind('focus', function(evt) {
                var input = element.find('input')[0];
                setLastCaret(input, angular);
            }).bind('blur', function(evt) {
                scope.$emit(this.id + "Blur", this.parentNode.id);
            }).bind('click', function() {
                scope.$emit(attrs.id + "Click");
            });

            inputCtrl.$parsers.push(validation);

            inputCtrl.$formatters.push(function(modelValue) {
                //remove error when init again data
                angular.element('#' + attrs.id + ' #' + attrs.id).removeClass(CSS_CLASS.error);
                angular.element('#' + attrs.id + ' #' + attrs.id).popover('destroy');
                // reset error
                inputCtrl.$setValidity('ccExist', true);
                inputCtrl.$setValidity('ccCode', true);
                inputCtrl.$setValidity('ccCheckdigit', true);
                setJanCodeConvert(modelValue);
                if (isZenDefined) {
                    if (!isEmpty(modelValue)) {
                        setJanCodeConvert(modelValue);
                    } else {
                        scope.ngModel2 = '';
                        return modelValue;
                    }
                    // 変換ＪＡＮコードを使って『3)基本情報Ｍアクセス』
                    var hakkoDay = scope.ccParameter01;
                    if (isEmpty(hakkoDay)) {
                        requestWithHakkoDay(" " + getJanCodeConvert(), unyoDate);
                    } else {
                        requestWithHakkoDay(" " + getJanCodeConvert(), hakkoDay);
                    }
                }
                return modelValue;
            });

            // listener blur event
            scope.blurCallback = function(value) {
                if (isEmpty(value)) {
                    return;
                }
                // 5) バインド変数の設定
                if (sts_result == STS_NORMAL) {
                    // 変換ＪＡＮコードを設定
                    scope.ngModel = getJanCodeConvert();
                    if (angular.isDefined(attrs.ccShnKbn)) {
                        scope.ccShnKbn = strShnPluKbn;
                    }
                }

                stopToken = $interval(function() {
                    if (isZenDefined) {
                        // 上記4)が『エラーで無い』 and 取得レコード = 無し and cc-notexist  = true
                        // || 上記4)が『エラーである』
                        if (!checkData || checkData && record.length == 0 && attrs.ccNotexist == 'true') {
                            scope.ngModel2 = '';
                        } else if (checkData && record.length != 0) {
                            // 上記4)が『エラーで無い』 and cc-shortname = true
                            if (attrs.ccShortname == 'true') {
                                scope.ngModel2 = record[0].shortName;
                                // 上記4)が『エラーで無い』 and cc-shortname = false
                            } else if (attrs.ccShortname == 'false') {
                                scope.ngModel2 = record[0].name;
                            }
                            $interval.cancel(stopToken);
                        }
                    }
                }, 100, 0, false);
            }

            // パラメータ(cc-parameterxx)に指定されているバインド変数が変更された場合は当該部品のバインド変数を消去する。
            // 1) 変更を監視するバインド変数
            scope.$watch('ccParameter01', function(value) {
                if (!inputCtrl.$pristine) {
                    // 消去する当該部品のバインド変数
                    scope.ngModel = '';
                    // ng-model2が指定されている場合
                    if (isZenDefined) {
                        scope.ngModel2 = '';
                    }
                }
            });

            scope.$on(attrs.id + "searchBoxClickedOutter", function(event, data) {
                inputCtrl.$setViewValue(data);
                inputCtrl.$render();
                element.find('input').eq(0).focus();
            });

            // Show server error message
            scope.error = null;
            scope.$on("ccError", function(event, value) {
                scope.error = null;
                inputCtrl.$setValidity('ccClientError', true);
                if (!isEmpty(value)) {
                    angular.forEach(value, function(error, key) {
                        if (angular.equals(error.name, attrs.name) &&
                            (angular.equals(error.level, 'E') || angular.equals(error.level, 'W'))) {
                            scope.error = error;
                            if (error.clientErrorFlag == true) {
                                inputCtrl.$setValidity('ccClientError', false);
                            } else {
                                inputCtrl.$setValidity('ccClientError', true);
                            }
                        }
                    });
                }
            });

            scope.$on('$destroy', function() {
                if (promise != undefined) {
                    $timeout.cancel(promise);
                }
                if (stopToken != undefined) {
                    $interval.cancel(stopToken);
                }
            });

            inputCtrl.$parsers.push(function(viewValue) {
                scope.error = null;
                scope.$emit('ccResetServerClientError', attrs.name);
                return viewValue;
            });
        }
    }
});