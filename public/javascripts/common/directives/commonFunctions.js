/**
 * @ngdoc function
 * @name isUndefined
 * @function
 *
 * @description Determines if a reference is undifined.
 * @param {string} string String to be checked.
 * @returns {boolean} true or false.
 */
function isUndefined(value) {
    return typeof value == 'undefined' || null === value || '' === value || "" === value;
}

/**
 * @ngdoc function
 * @name isEmpty
 * @function
 *
 * @description Determines if a reference is empty.
 * @param {string} string String to be checked.
 * @returns {boolean} true or false.
 */
function isEmpty(value) {
    return null === value || '' === value || undefined === value;
}


/**
 * @ngdoc function
 * @name removeSymbol
 * @function
 *
 * @description remove symbol contain value.
 * @param {string} value String to be removed.
 * @param {string} symbol String value.
 * @returns {string} value to be removed symbol.
 */
function removeSymbol(value, symbol) {
    if (isEmpty(value)) {
        return '';
    }
    var strText = value;
    try {
        var intIndexOfMatch = strText.indexOf(symbol);
        while (intIndexOfMatch != -1) {
            strText = strText.replace(symbol, '')
            intIndexOfMatch = strText.indexOf(symbol);
        }

        return strText;
    } catch (e) {
        return '';
    }
}

/**
 * @ngdoc function
 * @name checkDate
 * @function
 *
 * @description check value date.
 * @param {string} value String to be check.
 * @returns {string} value to be checked.
 */
function checkDate(value) {
    var results = '-1';
    if (isEmpty(value)) {
        return value;
    }

    var symbol = '/';
    //current date
    var d = new Date();
    var year = d.getFullYear();
    var month = d.getMonth() + 1;
    var day = d.getDate();
    var output = year + symbol + (month < 10 ? '0' : '') + month + symbol + (day < 10 ? '0' : '') + day;

    value = value.trim();
    //    value = $.trim(value);
    // case : 0 
    if ('/' == value) {
        results = output;
    } else {
        if (value.indexOf(symbol) >= 0) {
            var result = value.split(symbol);

            if (result.length == 2) {
                month = result[0];
                day = result[1];
            } else if (result.length == 3) {
                year = result[0];
                month = result[1];
                day = result[2];
            }
        } else {
            var lengthDate = value.length;
            // case : 1-1, 1-2, 2-1, 3-1, 3-2, 3-4
            if (lengthDate <= 2) {
                day = (Number(value) < 10 ? '0' : '') + value;
            } else if (lengthDate <= 4) {
                month = value.substring(0, lengthDate - 2);
                day = value.substring(lengthDate - 2);
            } else if (lengthDate <= 8) {
                year = value.substring(0, lengthDate - 4);
                month = value.substring(lengthDate - 4,
                    lengthDate - 2);
                day = value.substring(lengthDate - 2);
            } else if (lengthDate > 8) {
                return results;
            }
        }

        // case 4 not format a number.
        try {
            year = parseInt(year);
            month = parseInt(month);
            day = parseInt(day);

            year = (year < 10 ? '200' : (year > 1000 ? '' : '20')) + year;
            //b.    入力の範囲    ：    1900/01/01～2999/12/31
            if (Number(year) < 1900 || Number(year) > 2999) {
                return results;
            }

            if (month > 0 && month < 13 && day > 0 && day <= (new Date(year, month, 0)).getDate()) {
                results = year + symbol +
                    (month < 10 ? '0' : '') + month + symbol +
                    (day < 10 ? '0' : '') + day;
            }

        } catch (e) {
            return results;
        }
    }

    return results;
}

/**
 * @ngdoc function
 * @name getFormatDate
 * @function
 *
 * @description get format string date.
 * @param {string} value String to be check.
 * @returns {string} value to be got format.
 */
function getFormatDate(value) {
    var results = '';

    var symbol = '/';
    //current date
    var d = new Date();
    var year = d.getFullYear();
    var month = d.getMonth() + 1;
    var day = d.getDate();
    var output = year + symbol + (month < 10 ? '0' : '') + month + symbol + (day < 10 ? '0' : '') + day;

    var lengthDate = value.length;

    if (lengthDate <= 2) {
        day = (Number(value) < 10 ? '0' : '') + value;
    } else if (lengthDate <= 4) {
        month = value.substring(0, lengthDate - 2);
        day = value.substring(lengthDate - 2);
    } else if (lengthDate <= 8) {
        year = value.substring(0, lengthDate - 4);
        month = value.substring(lengthDate - 4,
            lengthDate - 2);
        day = value.substring(lengthDate - 2);
    } else if (lengthDate > 8) {
        return value;
    }

    try {
        year = parseInt(year);
        month = parseInt(month);
        day = parseInt(day);

        results = year + symbol + (month < 10 ? '0' : '') + month + symbol + (day < 10 ? '0' : '') + day;
    } catch (e) {
        return value;
    }
    return results;
}

/**
 * @ngdoc function
 * @name checkTime
 * @function
 *
 * @description check value time.
 * @param {string} value String to be check.
 * @returns {string} value to be checked.
 */
function checkTime(value) {
    var results = '-1';
    if (isEmpty(value)) {
        return;
    }

    var symbol = ':';
    var hour = '',
        minute = '';
    //current date
    var d = new Date();
    hour = d.getHours();
    minute = d.getMinutes();
    var output = (hour < 10 ? '0' : '') + hour + symbol + (minute < 10 ? '0' : '') + minute;
    if (':' == value) {
        results = output;
    } else {
        if (value.indexOf(symbol) >= 0) {
            var result = value.split(symbol);
            if (result.length == 2) {
                hour = result[0];
                minute = result[1];
            } else {
                return results;
            }
        } else {
            var lengthDate = value.length;
            if (lengthDate <= 2) {
                minute = (Number(value) < 10 ? '0' : '') + value;
            } else if (lengthDate <= 4) {
                hour = value.substring(0, lengthDate - 2);
                minute = value.substring(lengthDate - 2);
            } else if (lengthDate > 4) {
                return results;
            }
        }

        try {
            hour = parseInt(hour);
            minute = parseInt(minute);

            if (hour >= 0 && hour < 24 && minute >= 0 && minute < 60) {
                results = (hour < 10 ? '0' : '') + hour + symbol +
                    (minute < 10 ? '0' : '') + minute;
            }

        } catch (e) {
            return results;
        }
    }
    return results;
}

/**
 * @ngdoc function
 * @name checkFormatTime
 * @function
 *
 * @description get format time.
 * @param {string} value String to be check.
 * @returns {string} value to be got format.
 */
function checkFormatTime(value) {
    var results = '';
    var symbol = ':';
    var hour = '',
        minute = '';

    var lengthDate = value.length;
    if (lengthDate <= 2) {
        minute = (Number(value) < 10 ? '0' : '') + value;
    } else if (lengthDate <= 4) {
        hour = value.substring(0, lengthDate - 2);
        minute = value.substring(lengthDate - 2);
    } else if (lengthDate > 4) {
        return value;
    }

    try {
        hour = parseInt(hour);
        minute = parseInt(minute);

        results = (hour < 10 ? '0' : '') + hour + symbol + (minute < 10 ? '0' : '') + minute;
    } catch (e) {
        return value;
    }

    return results;
}

/**
 * check maximum, minimum, required of item
 * @param element
 * @param attrName
 * @param itemCheck
 * @param errorCss
 */
function checkErrorWhenChangeValue(element, attrName, itemCheck, errorCss) {
    if (itemCheck.$dirty && itemCheck.$invalid) {
        var title = '';
        if (itemCheck.$error.maxlength) {
            title = "Too long .....";
        } else if (itemCheck.$error.minlength) {
            title = "Too short ....";
        } else if (itemCheck.$error.required) {
            title = "Required ....";
        }

        if (title != '') {
            var inputs = element.find('input');
            angular.forEach(inputs, function(key, value) {
                if (attrName == this.name) {
                    $(this).attr("title", title);
                    $(this).addClass(errorCss);
                    $('.error_tip').tooltip();
                    return;
                }
            });

        }
    } else {
        $(this).removeAttr("title");
        $(this).removeClass(errorCss);
    }
}

/**
 * @ngdoc function
 * @name errorFromToDate
 * @function
 *
 * @description check date from lager than date to.
 * @param {string} startDate String start date time.
 * @param {string} endDate String start date end.
 * @returns {boolean} true or false.
 */
function errorFromToDate(startDate, endDate) {
    return new Date(startDate) >= new Date(endDate);
}

/**
 * @ngdoc function
 * @name checkSymbolsTwoBytes
 * @function
 *
 * @description check symbol is 2 bytes.
 * @param {string} value String value to check.
 * @returns {boolean} true or false.
 */
function checkSymbolsTwoBytes(value) {
    var result = true;
    if (isEmpty(value)) {
        return result;
    }
    var temp = '';

    for (var int = 0; int < value.length; int++) {
        temp = value.substring(int, int + 1);

        var encodeLength = encodeURI(temp).split(/%..|./).length - 1;
        var normalLength = temp.length;
        if (encodeLength == normalLength) {
            return false;
        }
    }
    return true;
}



/**
 * @ngdoc function
 * @name isUndefined
 * @function
 *
 * @description Determines if a reference is undifined.
 * @param {string} string String to be checked.
 * @returns {boolean} true or false.
 */
function isUndefined(value) {
    return typeof value == 'undefined';
}

/**
 * @ngdoc function
 * @name isEmpty
 * @function
 *
 * @description Determines if a reference is empty.
 * @param {string} string String to be checked.
 * @returns {boolean} true or false.
 */
function isEmpty(value) {
    return isUndefined(value) || value === '' || value === null || value !== value;
}

/**
 * @ngdoc function
 * @name stripCommas
 * @function
 *
 * @description Replace commas symbol.
 * @param {string} string String to be replaced.
 * @returns {string} String after replace commas.
 */
function stripCommas(numString) {
    var re = /,/g;
    return numString.replace(re, "");
}

/**
 * @ngdoc function
 * @name getInt
 * @function
 *
 * @description Get Integer part in floating number.
 * @param {string} string String to be divided.
 * @returns {string} String after divided integer part.
 */
function getInt(numString) {
    // divide integer portion into three-digit groups
    var int = parseInt(numString, 10).toString();
    re = /(-?\d+)(\d{3})/;
    while (re.test(int)) {
        int = int.replace(re, "$1,$2");
    }
    return int;
}


/**
 * @ngdoc function
 * @name getFrac
 * @function
 *
 * @description Get franction part in floating number.
 * @param {string} string String to be divided.
 * @returns {string} String after dived franction part.
 */
function getFrac(numString) {
    // extract decimal and digits to right (if any)
    var re = /\.\d{1,}/;
    var frac = (re.test(numString)) ? re.exec(numString) : "";
    return frac;
}

/**
 * @ngdoc function
 * @name countCharacter
 * @function
 *
 * @description Count character.
 * @param {string} string String to be divided.
 * @returns {string} Number character in string
 */
function countCharacter(value) {
    var retLength = 0;
    var intLength = 0;
    var fracLength = 0;
    var intValue = getInt(value);
    var intWithoutCommas = stripCommas(intValue);
    var fracValue = getFrac(value);

    intLength = intLength + intValue.replace(/[^,]/g, "").length + intWithoutCommas.length;

    if (!isEmpty(fracValue)) {
        if (fracValue[0].indexOf(".") != -1) {
            fracLength = fracValue[0].length;
        }
    }

    return intLength + fracLength;
}

/**
 * @ngdoc function
 * @name getMaxLength
 * @function
 *
 * @description Get max length of two string.
 * @param {string} first value.
 * @param {string} second value
 * @returns {string} Maxlength of two value.
 */
function getMaxLength(minValue, maxValue) {
    var maxLengthOfMin = countCharacter(minValue);
    var maxLengthOfMax = countCharacter(maxValue);
    var retLength = -1;
    if (maxLengthOfMax < maxLengthOfMin) {
        retLength = maxLengthOfMin;
    } else {
        retLength = maxLengthOfMax;
    }
    return retLength;
}

/**
 * @ngdoc function
 * @name getMaxIntLength
 * @function
 *
 * @description Get max length of two integer part of two floating number.
 * @param {string} first floating number.
 * @param {string} second floating number.
 * @returns {string} Maxlength of two value.
 */
function getMaxIntLength(minValue, maxValue) {
    var intValue = getInt(minValue);
    var minIntLength = intValue.replace(/[-,]/g, "").length;

    //max int length
    intValue = getInt(maxValue);
    var maxIntLength = intValue.replace(/[-,]/g, "").length;

    var retLength = -1;
    if (maxIntLength < minIntLength) {
        retLength = minIntLength;
    } else {
        retLength = maxIntLength;
    }
    return retLength;
}

/**
 * @ngdoc function
 * @name getMaxFracLength
 * @function
 *
 * @description Get max length of two frac part of two floating number.
 * @param {string} first floating number.
 * @param {string} second floating number.
 * @returns {string} Maxlength of two value.
 */
function getMaxFracLength(minValue, maxValue) {
    var fracValue = getFrac(minValue);
    var minFracLength = -1;
    if (!isEmpty(fracValue)) {
        if (fracValue[0].indexOf(".") != -1) {
            minFracLength = fracValue[0].replace(/[.]/g, "").length;
        }
    }

    //max frac length of max value.
    fracValue = getFrac(maxValue);
    var maxFracLength = -1;
    if (!isEmpty(fracValue)) {
        if (fracValue[0].indexOf(".") != -1) {
            maxFracLength = fracValue[0].replace(/[.]/g, "").length;
        }
    }

    var retLength = -1;
    if (maxFracLength < minFracLength) {
        retLength = minFracLength;
    } else {
        retLength = maxFracLength;
    }
    return retLength;
}

/**
 * @ngdoc function
 * @name getSigned
 * @function
 *
 * @description Get sign in number (minus or plus).
 * @param {string} first floating number.
 * @param {string} second floating number.
 * @returns {string} Sign or not.
 */
function getSigned(minValue, maxValue) {
    if (angular.isUndefined(minValue) || angular.isUndefined(maxValue)) {
        return '';
    }

    if (minValue.indexOf("-") == -1 && maxValue.indexOf("-") == -1) {
        return '';
    } else if (minValue.indexOf("-") != -1 || maxValue.indexOf("-") != -1) {
        return 'cc-signed';
    }

    return '';
}

/**
 * @ngdoc function
 * @name zenkakuReplace
 * @function
 *
 * @description Replace symbols 2 bytes to BLANK
 * @param {inputString} first string value.
 * @returns {string} Value haven't symbols 2 bytes.
 */
function zenkakuReplace(inputString) {
    var str = inputString;
    var myChars = new Array();
    for (var i = 0; i < str.length; i++) {
        var code = str.charCodeAt(i);
        if ((0 <= code && code <= 255) ||
            (65382 <= code && code <= 65439)) {
            myChars[i] = str.charAt(i);
        }
    }
    for (var j = 0; j < myChars.length; j++) {
        str = str.replace(myChars[j], '');
    }
    return str;
}

/**
 * @ngdoc function
 * @name isZenkakuOrHankaku
 * @function
 *
 * @description check validate symbols
 *                  flg : false :check 半角文字
 *                        true  :check 全角文字
 * @param {str} first string value.
 * @param {flg} flg to check 半角 or 全角.
 * @returns {Boolean} true:含まれている、false:含まれていない
 */
function isZenkakuOrHankaku(str, flg) {
    for (var i = 0; i < str.length; i++) {
        var c = str.charCodeAt(i);
        // Shift_JIS: 0x0 ～ 0x80, 0xa0 , 0xa1 ～ 0xdf , 0xfd ～ 0xff
        // Unicode : 0x0 ～ 0x80, 0xf8f0, 0xff61 ～ 0xff9f, 0xf8f1 ～ 0xf8f3
        if ((c >= 0x0 && c < 0x81) || (c == 0xf8f0) || (c >= 0xff61 && c < 0xffa0) || (c >= 0xf8f1 && c < 0xf8f4)) {
            if (flg) return false;
        } else {
            if (!flg) return false;
        }
    }
    return true;
}

/**
 * @ngdoc function
 * @name getCharCssClass
 * @function
 *
 * @description Get width for input text
 * @param {string} max length
 * @returns {string} css class
 */
function getCharCssClass(CSS_CLASS, maxlength) {
    var result = CSS_CLASS.char_5;
    if (maxlength >= 1 && maxlength <= 10) {
        result = CSS_CLASS.char_10;
    } else if (maxlength <= 15) {
        result = CSS_CLASS.char_15;
    } else if (maxlength <= 20) {
        result = CSS_CLASS.char_20;
    } else if (maxlength <= 30) {
        result = CSS_CLASS.char_30;
    } else if (maxlength <= 40) {
        result = CSS_CLASS.char_40;
    } else if (maxlength > 41) {
        result = CSS_CLASS.char_50;
    }
    return result;
}

/**
 * @ngdoc function
 * @name toString
 * @function
 *
 * @description Get string of object
 * @param {string} obj value need to string
 * @returns {string}
 */
function toString(obj) {
    if (!isEmpty(obj)) {
        return obj + '';
    }

    return obj;
}

/**
 * @ngdoc function
 * @name toString
 * @function
 *
 * @description convert null , undiefine to ""
 * @param {string} obj value need to string
 * @returns {string}
 */
function convertNullToString(obj) {
    if (!isEmpty(obj)) {
        return obj + '';
    } else {
        return '';
    }
}



/**
 * @ngdoc function
 * @name disableSelectItems
 * @function
 *
 * @description set disable (or enable) select
 * @param {angular} obj angular
 * @param {area} area id need to disable (or enable)
 * @param {value} true (disable) or false (enable)
 * @param {idItems} items id need to disable (or enable)
 */
function disableSelectItems(angular, area, value, idItems) {
    if (!isEmpty(idItems)) {
        var arrayidItems = idItems.split(' ');
        for (var int = 0; int < arrayidItems.length; int++) {
            var idItem = arrayidItems[int];

            angular.element('#' + area + ' #' + idItem + ' #' + idItem).attr('disabled', 'disabled');
            try {
                angular.element('#' + area + ' #' + idItem + ' #' + idItem).prop('disabled', value);
                angular.element('#' + area + ' #' + idItem + ' #' + idItem).prop('disabled', value);
            } catch (e) {}
        }
    }
}

/**
 * @ngdoc function
 * @name getIdArray
 * @function
 *
 * @description get array item id
 * @param {angular} obj angular
 * @param {area} area id need to disable
 * @param {idItem} item id need to disable
 */
function getIdArray(expect) {
    var idExpect = '';
    if (!isEmpty(expect)) {
        var arrayExpect = expect.split(' ');
        for (var int = 0; int < arrayExpect.length; int++) {
            if (int == (arrayExpect.length - 1)) {
                idExpect += ('#' + arrayExpect[int]);
            } else {
                idExpect += ('#' + arrayExpect[int] + ',');
            }
        }
    }
    return idExpect;
}

/**
 * @ngdoc function
 * @name disableSelects
 * @function
 *
 * @description set disable (or enable) selects
 * @param {angular} obj angular
 * @param {area} area id need to disable (or enable)
 * @param {value} true (disable) or false (enable)
 * @param {init} status init or not init
 * @param {expect} array items id that need not value.
 */
function disableSelects(angular, area, value, init, expect) {
    var idExpect = getIdArray(expect);

    if (init) {
        angular.element('#' + area + ' select').not(idExpect).attr('disabled', value);
        angular.element('#' + area + ' select').filter(idExpect).prop('disabled', !value);
    } else {
        angular.element('#' + area + ' select').not(idExpect).select2().prop('disabled', value);
        angular.element('#' + area + ' select').not(idExpect).select2().prop('disabled', value);

        angular.element('#' + area + ' select').filter(idExpect).select2().prop('disabled', !value);
        angular.element('#' + area + ' select').filter(idExpect).select2().prop('disabled', !value);
    }
}

/**
 * @ngdoc function
 * @name disableInputItems
 * @function
 *
 * @description set disable (or enable) area input
 * @param {angular} obj angular
 * @param {area} area id need to disable (or enable)
 * @param {value} true (disable) or false (enable)
 * @param {expect} array item id that need disable (or enable)
 */
function disableInputItems(angular, area, value, expect) {
    var idExpect = getIdArray(expect);
    if (isEmpty(idExpect)) {
        angular.element('#' + area + ' input').prop('disabled', value);
    } else {
        angular.element('#' + area + ' input').not(idExpect).prop('disabled', value);
        angular.element('#' + area + ' input').filter(idExpect).prop('disabled', !value);
    }
}

/**
 * @ngdoc function
 * @name clearDisableCondAndResult
 * @function
 *
 * @description clear disable of item in condition and result
 * @param {angular} obj angular
 */
function clearDisableCondAndResult(angular) {
    angular.element('input').removeAttr('disabled');
    angular.element('select').select2().prop('disabled', false);
    angular.element('select').select2().prop('disabled', false);
}

/**
 * @ngdoc function
 * @name setLastCaret
 * @function
 *
 * @description set the caret position to last input.
 */
function setLastCaret(inputEle, angular) {
    var nAgt = navigator.userAgent;
    if (nAgt.indexOf("Chrome") != -1) {
        return;
    }

    if (!isEmpty(inputEle)) {
        // var input = inputEle[0];
        if (!isEmpty(inputEle.value)) {
            var length = inputEle.value.length;
            var r = inputEle.createTextRange();
            r.moveStart("character", length);
            r.moveEnd("character", length);
            r.select();
        }
        angular.element(inputEle).select();
    }
}

/**
 * @ngdoc function
 * @name setNextFocus
 * @function
 *
 * @description focus to next input when entering
 */
function setNextFocus(curObj) {}

/**
 * @ngdoc function
 * @name setNextFocus
 * @function
 *
 * @description focus to next input when entering
 */
function setNextFocusLogin(curObj) {
    switch (event.keyCode) {

        case 13:
            var inputs = $(curObj).closest('form').find('button:not([disabled]:visible):not(\'div.ng-hide button\'), input[type=\'button\']:not([disabled]:visible), input:not([disabled]):visible').not('.ngColListCheckbox ');
            var curIndex = inputs.index(curObj);
            if (curIndex == inputs.length - 1) {
                curIndex = -1
            }
            var fwdObj = inputs.eq(curIndex + 1);
            if (fwdObj) {
                setTimeout(function() {
                    fwdObj.focus();
                    fwdObj.select();
                }, 50);
            }
            return false;
    }

    return true;
}


function getDistinctDataList(listData) {
    var resultList = [];
    angular.forEach(listData, function(index, value) {
        if (!isExist(value, resultList)) {
            resultList.push(value);
        }
    });
    return resultList;
}

/**
 * @ngdoc function
 * @name isExist
 * @function
 *
 * @description check code exist in listData.
 */
function isExist(code, listData) {
    var exist = false;
    angular.forEach(listData, function(index, value) {
        if (value.code == code) {
            exist = true;
            return false;
        }
    });
    return exist;
}

/**
 * @ngdoc function
 * @name cloneData
 * @function
 *
 * @description copy data from object input.
 */
function cloneData(d) {
    if (isEmpty(d)) {
        return '';
    }
    switch (d.constructor) {
        case Object:
            var o = {};
            for (var i in d) {
                o[i] = cloneData(d[i]);
            }
            return o;
        case Array:
            var a = [];
            for (var i = 0, l = d.length; i < l; ++i) {
                a[i] = cloneData(d[i]);
            }
            return a;
        case String:
            if (isEmpty(d)) {
                return '';
            } else {
                return d;
            }
    }
    return d;
};

/**
 * @ngdoc function
 * @name setFlagFocus
 * @function
 *
 * @description delay set value for isEditable.
 */
function setFlagFocus($scope, $interval, bol) {
    $scope.isFocus = bol;
    var stopToken = $interval(function() {
        $scope.isFocus = !bol;
        $interval.cancel(stopToken);
    }, 100, 1, false);
}

function setFocusCond($scope, $interval, value) {
    $scope.focusCond = 0;
    var stopToken = $interval(function() {
        $scope.focusCond = value;
        $interval.cancel(stopToken);
    }, 10, 1, false);
}

function setFocusResult($scope, $interval, value) {
    $scope.focusResult = 0;
    var stopToken = $interval(function() {
        $scope.focusResult = value;
        $interval.cancel(stopToken);
    }, 10, 1, false);
}

function setFocusCustom($scope, $interval, attrString, value) {
    $scope[attrString] = 0;
    var stopToken = $interval(function() {
        $scope[attrString] = value;
        $interval.cancel(stopToken);
    }, 10, 1, false);
}

function setFocusCondReport($scope, $interval, value) {
    $scope.isFocus = 0;
    var stopToken = $interval(function() {
        $scope.isFocus = value;
        $interval.cancel(stopToken);
    }, 5, 1, false);
}

/**
 * @ngdoc function
 * @name checkBoxAllClickHandler
 * @function
 *
 * @description handler function of event click on grid header checkboxes
 */
var checkBoxAllClickHandler = function(resultList, pagingOptions, delFlagAttr) {
    var currentPage = pagingOptions.currentPage;
    var pageSize = pagingOptions.pageSize;
    if (resultList != undefined && resultList.length > 0) {
        var value = !getCheckAllValue(resultList, pagingOptions, delFlagAttr);
        for (var i = (currentPage - 1) * pageSize; i < currentPage * pageSize; i++) {
            if (i >= resultList.length) {
                break;
            }
            resultList[i][delFlagAttr] = value;
        }
    }
}

/**
 * @ngdoc function
 * @name getCheckAllValue
 * @function
 *
 * @description function to get value of grid header checkboxes
 */
var getCheckAllValue = function(resultList, pagingOptions, delFlagAttr) {
    var currentPage = pagingOptions.currentPage;
    var pageSize = pagingOptions.pageSize;
    if (resultList != undefined && resultList.length > 0) {
        var minRow = (currentPage - 1) * pageSize;
        var maxRow = currentPage * pageSize - 1;

        // page has no record
        if (minRow >= resultList.length) {
            return false;
        }

        for (var i = minRow; i <= maxRow; i++) {
            if (i >= resultList.length) {
                break;
            }
            if (!resultList[i][delFlagAttr]) {
                return false;
            }
        }
        return true;
    } else {
        return false;
    }
}


/**
 * @ngdoc function
 * @name checkBoxAllClickHandlerNoPaging
 * @function
 *
 * @description handler function of event click on grid header checkboxes
 */
var checkBoxAllClickHandlerNoPaging = function(resultList, delFlagAttr) {
    if (resultList != undefined && resultList.length > 0) {
        var value = !getCheckAllValueNoPaging(resultList, delFlagAttr);
        for (var i = 0; i < resultList.length; i++) {
            resultList[i][delFlagAttr] = value;
        }
    }
}

/**
 * @ngdoc function
 * @name getCheckAllValueNoPaging
 * @function
 *
 * @description function to get value of grid header checkboxes
 */
var getCheckAllValueNoPaging = function(resultList, delFlagAttr) {
    if (resultList != undefined && resultList.length > 0) {
        for (var i = 0; i < resultList.length; i++) {
            if (!resultList[i][delFlagAttr]) {
                return false;
            }
        }
        return true;
    } else {
        return false;
    }
}

/**
 * @ngdoc function
 * @name isEmptyObject
 * @function
 *
 * @description wrapper function for angular.element.isEmptyObject
 */
var isEmptyObject = function(target) {
    return angular.element.isEmptyObject(target);
}

/**
 * @ngdoc function
 * @name getSelector
 * @function
 *
 * @description get jquery selector
 */
var getSelector = function(child) {
    var selectorChild = ''
    while (isEmpty(selectorChild)) {
        var className = child.className;
        var classArray = className.split(" ");
        var selectorChild = '' + (isEmpty(child.id) ? "" : '#' + child.id) + (isEmpty(classArray[0]) ? "" : '.' + classArray[0]);
        if (isEmpty(selectorChild)) {
            child = child.parentNode;
        }
    }
    return selectorChild;
}

var customEqual = function(a, b) {
    if (isEmpty(a) && isEmpty(b)) {
        return true;
    } else {
        return a == b;
    }
}

var daysInMonth = function(year, month) {
    return new Date(year, month, 0).getDate();
}