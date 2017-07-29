///////////////////////////////////////////////////////////////////////
// Copyright(C) 2015 NEC Solution Innovators, Ltd. All Rights Reserved.
///////////////////////////////////////////////////////////////////////

/* ====================================================================
 * 機能名称 : メッセージ情報取得
 * 改版履歴
 * Rev.  改版年月日   改版者名       内容
 * 1.0   2014.04.04   H.Okuhara      新規作成
 * =================================================================== */
app = angular.module('rootServices', []);

app.constant('MsgConst', {
	MSG_KEY_TENANT_15DATE_NOT_EXISTED : 'Y0011',
	MSG_KEY_TENANT_30DATE_NOT_EXISTED : 'Y0012',
	MSG_KEY_TENANT_15_30DATE_NOT_EXISTED : 'Y0013',
    KEY_SUCCESS_SAVE_DATA: 'C1001',
    KEY_SUCCESS_UPDATE_DATA: 'C1002',
    KEY_SUCCESS_DELETE_DATA: 'C1003',
    MSG_KEY_CONFIRM_NEW_REGISTERED: 'C8001',
    MSG_KEY_COMMON_INFO_MESSAGE: 'C8002',
    MSG_KEY_COMMON_INFO_MESSAGE_F9: 'C8003',
    MSG_KEY_COMMON_INFO_MESSAGE_F10: 'C8004',
    MSG_KEY_COMMON_INFO_MESSAGE_REPORT: 'C8005',
    MSG_KEY_COMMON_REPORT_SUCCESS: 'C8006',
    MSG_KEY_COMMON_INFO_NOF9MESSAGE: 'C8007',
    MSG_KEY_NO_FOUND_DATA: 'C8008',
    MSG_KEY_INFO_BUTTON_DELETE_SAVE: 'C8009',
    MSG_KEY_INFO_BUTTON_SAVE: 'C8010',
    MSG_KEY_INFO_BUTTON_UPDATE: 'C8011',
    MSG_KEY_DISPLAY_CORRECT_DATA: 'C8012',
    MSG_KEY_REGISTERED: 'C8014',
    MSG_KEY_SYSTEM_ERROR: 'C8015',
    MSG_KEY_CONFIRM_BEFORE_INSERT: 'C8016',
    MSG_KEY_INSERT_SUCCESS: 'C8017',
    MSG_KEY_CONFIRM_BEFORE_CLEAR: 'C8018',
    MSG_KEY_CONFIRM_BEFORE_MAKE_PDF: 'C8019',
    MSG_KEY_CONFIRM_BEFORE_UPDATE: 'C8020',
    MSG_KEY_CONFIRM_BEFORE_CHANGE: 'C9127',
    MSG_KEY_UPDATE_SUCCESS: 'C8021',
    MSG_KEY_CONFIRM_BEFORE_DELETE: 'C8022',
    MSG_KEY_DELETE_SUCCESS: 'C8023',
    MSG_KEY_CHANGE_SUCCESS: 'C8024',
    MSG_KEY_PROCESS_SUCCESS: 'C8025',
    MSG_KEY_LOGIN_INFO: 'C8026',
    MSG_KEY_REPORT_LOADING: 'C8027',
    MSG_KEY_INFO_ACTION_EDIT_DATA: 'T2107',
    MSG_KEY_MIN_LENGTH: 'C8028',
    MSG_KEY_MAX_LENGTH: 'C8029',
    MSG_KEY_NOT_NUMBER: 'C8030',
    MSG_KEY_DATE_FAILED_VALUE: 'C8031',
    MSG_KEY_VALUE_GREATER_THAN: 'C8032',
    MSG_KEY_VALUE_LESS_THAN: 'C8033',
    MSG_KEY_VALIDATE_GREATER: 'C8034',
    MSG_KEY_VALIDATE_LESS: 'C8035',
    MSG_KEY_GREATER_TO: 'C8036',
    MSG_KEY_GREATER_EQUAL: 'C8037',
    MSG_KEY_ZENKAKU_FAILED: 'C8038',
    MSG_KEY_HANKAKU_FAILED: 'C8039',
    MSG_KEY_POST_FAILED: 'C8040',
    MSG_KEY_PHONE_FAILED: 'C8041',
    MSG_KEY_MAIL_FAILED: 'C8042',
    MSG_KEY_URL_FAILED: 'C8043',
    MSG_KEY_TIME_FAILED: 'C8044',
    MSG_KEY_CODE_NOT_EXIST: 'C8045',
    MSG_KEY_CODE_ERROR: 'C8046',
    MSG_KEY_CHECK_DIGIT: 'C8047',
    MSG_KEY_CD_FORMAT: 'C8048',
    MSG_KEY_INVALID_SELECTION: 'C8049',
    MSG_KEY_REQUIRED_ITEM: 'C8050',
    MSG_KEY_ALL_DIRECTIVE_MSG: 'C8',
    MSG_KEY_REPORT_LINK_DOWNLOAD: 'C0206',
    MSG_KEY_CONFIRM_BEFORE_UNDO: 'C8051',
    MSG_KEY_UNDO_SUCCESS: 'C8052',
    MSG_KEY_INFO_BUTTON_DELETE: 'C8053',
    MSG_KEY_DATA_NOT_EXIST: 'C8054',
    MSG_KEY_INFO_BUTTON_UNDO_SAVE: 'C8055',
    MSG_KEY_PROCESS_COPY_SUCCESS: 'T2130',
    MSG_WARNING_DEFAULT_VALUE: 'T0062',
    MSG_KEY_PROCESS_COPY_SUCCESS: 'T2130',
    MSG_KEY_NOT_SKIP_ITEM: 'C0012',
    MSG_KEY_VALIDATION_ERROR: 'C1005',
    MSG_KEY_CONFIRM_BEFORE_CANCEL: 'C8056',
    MSG_KEY_CANCEL_SUCCESS: 'C8057',
    MSG_KEY_INFO_BUTTON_CANCEL_SAVE: 'C8058',
    MSG_KEY_DATE_SALE_PERIOD: 'T0050',
    MSG_KEY_DATE_SALE_AFTER_PERIOD_END_DATE: 'T0084',
    MSG_KEY_DATE_PLAN_ORDER_PERIOD: 'T0091',
    MSG_KEY_DATE_EARLIER_FIXED_DATE: 'T0081',
    MSG_KEY_STATUS_LIST: 'C8059',
    MSG_KEY_JAN_CD_ALREADY_REGISTERED_TO: 'C9131',
    MSG_KEY_CONFIRM_BEFORE_START_JOB: 'C9133',
    MSG_KEY_COMMON_INFO_F12_NO_CONDITION: 'C9135',
    MSG_KEY_REPORT_LOADING: 'C9136',
    MSG_KEY_INPUT_BEFORE_TODAY: 'Z0030',
    MSG_KEY_BEFORE_UNYOBI_DAY: 'T0022',
    MSG_KEY_INPUT_TENCD_INVALID: 'C9134',
    MSG_KEY_INPUT_NOT_MATCH_TYPE_POS_TYP_KBN: 'M0081',
    MSG_KEY_INPUT_NOT_MATCH_TYPE: 'C0257',
    MSG_KEY_INPUT_REQUIRED_ENTER: 'C9002',
    MSG_KEY_INPUT_NOT_MATCH_TYPE_GRP_EQUAL_GRPCP: 'M0072',
    MSG_KEY_INPUT_NOT_MATCH_TYPE_VALUE_1459: 'C9022',
    MSG_KEY_INPUT_FAX_REQURIED: 'M0010',
    MSG_KEY_INPUT_NOT_MATCH_TYPE_VALUE_IKATUFLG: 'M0038',
    MSG_KEY_INPUT_NOT_MATCH_TYPE_VALUE_13: 'C9024',
    MSG_KEY_INPUT_NOT_MATCH_TYPE_VALUE_19: 'C9023',
    MSG_KEY_INPUT_NOT_MATCH_TYPE_VALUE_1249: 'C9025',
    MSG_KEY_INPUT_NOT_MATCH_TYPE_VALUE_1530: 'C9026',
    MSG_KEY_INPUT_NOT_MATCH_TYPE_VALUE_12345: 'C9027',
    MSG_KEY_INPUT_NOT_MATCH_TYPE_VALUE_123: 'C9028',
    MSG_KEY_INPUT_NOT_MATCH_TYPE_VALUE_129: 'C9032',
    MSG_KEY_INPUT_NOT_MATCH_TYPE_VALUE_101530: 'C9033',
    MSG_KEY_INPUT_NOT_MATCH_TYPE_VALUE_12: 'C9034',
    MSG_KEY_INPUT_NOT_MATCH_TYPE_VALUE_12_2: 'C9035',
    MSG_KEY_INPUT_NOT_MATCH_TYPE_VALUE_ASN_FLG: 'C9038',
    MSG_KEY_INPUT_NOT_MATCH_TYPE_VALUE_DIRECT_KBN: 'C9039',
    MSG_KEY_INPUT_NOT_MATCH_TYPE_VALUE_FEE_KBN: 'C9040',
    MSG_KEY_INPUT_REQUIRED_15: 'C9036',
    MSG_KEY_INPUT_REQUIRED_30: 'C9037',
    MSG_KEY_PAST_ORDER_DATE: "T2127",
    MSG_KEY_SPECIFY_EARLIER_THAN_ORDER_DAY: "T0106",
    MSG_KEY_PAST_SALE_DATE: "T2128",
    MSG_KEY_HANBAI_LESS_THAN_SIIRE: "T0084",
    MSG_KEY_INPUT_FUTURE_DATE_ORDER: "H0214",
    MSG_KEY_SALE_NOT_MATCH: "U1036",
    MSG_KEY_SEARCHED_RECORDS_NUMBER: "S0261",
    MSG_KEY_SEARCH_NOT_MATCH_TYPE: "C9008",
    MSG_KEY_NOT_MATCH_TYPE_FROM_GREATER: "H0250",
    MSG_KEY_ERROR_DEPARMENT_CODE_FR_GREATER: "H0206",
    MSG_KEY_ERROR_TORIHIKI_FR_GREATER: "H0202",
    MSG_KEY_REQUIRED_TORIHIKI_SAME_ENTER: "H0124",
    MSG_KEY_ERROR_BRANCH_FR_GREATER: "H0203",
    MSG_KEY_TENCD_ERROR_COMPARE: 'C9020',
    MSG_KEY_DATE_ERROR: 'C0011',
    MSG_KEY_RANGE_ERROR: 'K1001',
    MSG_KEY_RANGE_SUPPLIER_CODE_ERROR: 'K1019',
    MSG_KEY_RANGE_SPECIFICATION_MANAGEMENT_NUMBER_ERROR: 'K1010',
    MSG_KEY_ERROR_BMNCD_COMPARE: 'H0206',
    MSG_KEY_ITEM_INPUT_IS_ERROR: 'C0062',
    MSG_KEY_TENFROM_TENTO_COM_ERROR: 'H0244',
    MSG_KEY_TEN_START_GREATE_THAN_TEN_END: 'U1025',
    MSG_KEY_INPUT_PREVIOUS_YEARS_MONTH: 'C9114',
    MSG_KEY_STARTDATE_IS_GREATER_ENDDATE: 'A0001',
    MSG_KEY_DATE_START_LARGER_DATE_END: 'S1067',
    MSG_KEY_NOT_EXISTS_IN_MASTER: 'C2001',
    MSG_KEY_ALREADY_REGISTERED: 'C0252',
    MSG_KEY_WARNING_MAX_RECORDS: 'C9129',
    MSG_KEY_UPLOAD_SUCCEED: 'H0253',
    MSG_KEY_CONFIRM_BEFORE_UPLOAD: 'H0257',
    MSG_KEY_UPLOAD_EMPTY_FILE: 'H0259',
    MSG_KEY_PAST_HAKKODAY_WARNING: 'M0117',
    MSG_KEY_CHANGED_PRODUCT_INPUT: 'M0118',
    MSG_KEY_KENZAN_NOT_ZERO_TENANT: 'Y0017',
    MSG_KEY_KENZAN_NOT_ZERO_POS: 'Y0018',
    MSG_KEY_TNT_MINUS_URIKIN_TOTAL: 'Y0020',
    MSG_KEY_TNT_MINUS_TAXURI_TOTAL: 'Y0021',
    MSG_KEY_TNT_MINUS_URIALL_TOTAL: 'Y0022',
    MSG_KEY_POS_MINUS_URIKIN_TOTAL: 'Y0023',
    MSG_KEY_POS_MINUS_TAXURI_TOTAL: 'Y0024',
    MSG_KEY_POS_MINUS_URIALL_TOTAL: 'Y0025',
    MSG_KEY_HONSEISAN_ALREADY: 'Y0008',
    MSG_KEY_DEPOSIT_TYPE_INVALID: 'Y0003',
    MSG_KEY_INFO_SEARCH_RESULT_BUTTON_UPDATE: 'SE100',
    MSG_KEY_CONFIRM_BEFORE_INVENTORY_KAKUTEI: 'SE101',
    MSG_KEY_CONFIRM_BEFORE_INVENTORY_KAKUTEI_RESET: 'SE102',
    MSG_KEY_VALUE_NO_INPUT: "C0072",
    MSG_KEY_ERROR_SINAFUSU_HACCHUSU_GREATER: "NM020",
    MSG_KEY_ERROR_SINAFUSU_ZAIKOSU_GREATER: "NM021"
});

app.constant('HttpConst', {
    CODE_OK: 200,
    CODE_CREATED: 201,
    CODE_BAD_REQUEST: 400,
    CODE_UNAUTHORIZED: 401,
    CODE_FORBIDDEN: 403,
    CODE_NOT_FOUND: 404,
    CODE_INTERNAL_SERVER_ERROR: 500
});

app.constant('AppConst', {
    KAISYA_CODE: '00'
});

app.constant('FocusConst', {
    NOT_FOCUS: 0,
    COMBOBOX_INIT_FOCUS: 1,
    COMBOBOX_NOT_INIT_FOCUS: 2,
    TEXTINPUT_FOCUS: 1
});

app.constant('KbnConst', {
    KBN_VAL_M_POS_TYP_KBN_TEC: '2',
    KBN_VAL_M_URI_KBN_YES: '1',
    KBN_VAL_M_URI_KBN_HANDWRITTEN_SLIP: '2',
    KBN_VAL_M_URI_KBN_NO: '9',
    KBN_VAL_M_EOS_KBN_EOS_FIP: '1',
    KBN_VAL_M_EOS_KBN_AUTO_FAX: '4',
    KBN_VAL_M_EOS_KBN_SLIP: '5',
    KBN_VAL_M_EOS_KBN_NONE: '9',
    KBN_VAL_M_IKATU_FLG_YES: '1',
    KBN_VAL_M_IKATU_FLG_NO: '9',
    KBN_VAL_M_HAT_TRI_KBN_GENERAL: '1',
    KBN_VAL_M_HAT_TRI_KBN_INTERNAL: '3',
    KBN_VAL_M_TORI_STOP_KBN_YES: '1',
    KBN_VAL_M_TORI_STOP_KBN_NO: '9',
    KBN_VAL_M_YOKIN_SYU_ORDINARY: '1',
    KBN_VAL_M_YOKIN_SYU_CURRENT: '2',
    KBN_VAL_M_YOKIN_SYU_SAVING: '4',
    KBN_VAL_M_YOKIN_SYU_OTHER: '9',
    KBN_VAL_M_SIME_KBN_EVERY_15_DAYS: '15',
    KBN_VAL_M_SIME_KBN_END_OF_MONTH: '30',
    KBN_VAL_M_KAI_SEISAN_KBN_TRANSFER_ONLINE: '1',
    KBN_VAL_M_KAI_SEISAN_KBN_TRANSFER: '2',
    KBN_VAL_M_KAI_SEISAN_KBN_HEADQUARTERS_CASH: '3',
    KBN_VAL_M_KAI_SEISAN_KBN_CASH_ON_HAND: '4',
    KBN_VAL_M_KAI_SEISAN_KBN_BILL: '5',
    KBN_VAL_M_PAY_DTL_ONL_KBN_NONE: '1',
    KBN_VAL_M_PAY_DTL_ONL_KBN_MIDDLE: '2',
    KBN_VAL_M_PAY_DTL_ONL_KBN_END: '3',
    KBN_VAL_M_BT_KY_KBN_TC: '1',
    KBN_VAL_M_BT_KY_KBN_DC: '2',
    KBN_VAL_M_BT_KY_KBN_NONE: '9',
    KBN_VAL_M_PAY_KBN_AFTER_10_DAYS: '10',
    KBN_VAL_M_PAY_KBN_AFTER_15_DAYS: '15',
    KBN_VAL_M_PAY_KBN_END_OF_NEXT_MONTH: '30',
    KBN_VAL_M_SIH_DATA_SAKUSEI_KBN_SUPPLIER: '1',
    KBN_VAL_M_SIH_DATA_SAKUSEI_KBN_COMPANY: '2',
    KBN_VAL_M_PAY_DTL_PRT_KBN_NONE: '1',
    KBN_VAL_M_PAY_DTL_PRT_KBN_MIDDLE: '2',
    KBN_VAL_M_PAY_DTL_PRT_KBN_END: '3',
    KBN_VAL_M_ASN_FLG_YES: '1',
    KBN_VAL_M_ASN_FLG_NO: '2',
    KBN_VAL_M_FEE_KBN_YES: '1',
    KBN_VAL_M_FEE_KBN_NO: '9',
    KBN_VAL_M_SYORI_KBN_CHUKAN: '1',
    KBN_VAL_M_SYORI_KBN_GETSU_MATSU: '2',
    KBN_VAL_M_SIME_KBN_MIDDLE_MONTH: '15',
    KBN_VAL_M_SIME_KBN_END_MONTH: '30'

});

app.factory('DirectiveMsg', function($resource, ComInfo) {
    var message = [];
    var privateGetAllMessages = function(key) {
        var rsc = $resource('/core/message/directive/:key', {
            key: '@key'
        }, {});
        var msg = rsc.query({
            key: key
        }, function() {
            for (var i = 0; i < msg.length; i++) {
                message[msg[i].key] = msg[i];
            }
        }, function(response) {
            ComInfo.message = "サーバー処理でシステムエラーが発生しました。"
        });
    };

    return {
        getAllMessages: function(key) {
            privateGetAllMessages(key);
        },
        getMessage: function(key) {
            if (key in message) {
                return message[key].msg;
            }
        },
    };

});

app.factory('ComInfo', function() {
    return {
        sid: '',
        title: '',
        level: '',
        message: ''
    };
});

app.factory('ErrorInfo', function() {
    return {
        name: '',
        code: '',
        level: '',
        messageId: '',
        message: '',
        clientErrorFlag: false, //need or not disable search or save button when error occur 
    };
});

app.factory('session', ['$cookies',
    function($cookies) {
        // read Play session cookie
        var session = {};
        var rawCookie = $cookies['PLAY_SESSION'];
        if (rawCookie == null) {
            return session;
        }
        var rawData = rawCookie.substring(rawCookie.indexOf('-') + 1, rawCookie.length - 1);

        angular.forEach(rawData.split("&"), function(rawPair) {
            var pair = rawPair.split('=');
            session[pair[0]] = pair[1];
        });
        return session;
    }
]);


app.factory('UserInfo', function(session) {
    return {
        tantoCd: decodeURIComponent(escape(session.tantoCd)).replace(/\+/g, ' '),
        tantoNm: decodeURIComponent(escape(session.tantoNm)).replace(/\+/g, ' '),
        tantoLvl: decodeURIComponent(escape(session.tantoLvl)).replace(/\+/g, ' '),
        tenpoCd: decodeURIComponent(escape(session.tenpoCd)).replace(/\+/g, ' '),
        tenpoNm: decodeURIComponent(escape(session.tenpoNm)).replace(/\+/g, ' '),
        unyoDate: session.unyoDate,
        hatUnyoDate: session.hatUnyoDate,
        sysCurrentYear: session.sysCurrentYear,
        sysCurrentMonth: session.sysCurrentMonth,
        sysCurrentDate: session.sysCurrentDate,
        chukanSimeDate: session.chukanSimeDate,
        getsuMatsuSimeDate: session.getsuMatsuSimeDate
    };
});

app.factory('Dialog', ['$modal', 'DialogInfo', '$rootScope',
    function($modal, DialogInfo, $rootScope) {
        return {
            confirm: function(content) {
                if (!isEmpty($rootScope)) {
                    if (!$rootScope.flagOpenDialog) {
                        $rootScope.flagOpenDialog = true;
                    } else {
                        return $rootScope.dialogTmp;
                    }
                }

                DialogInfo.content = content;
                var dialogInstance = $modal.open({
                    templateUrl: 'confirmDialog.html',
                    backdrop: 'static',
                    controller: ConfirmDialogCtrl,
                    keyboard: true
                });

                if (!isEmpty($rootScope)) {
                    dialogInstance.result.then(function() {
                        $rootScope.flagOpenDialog = false;
                    }, function() {
                        $rootScope.flagOpenDialog = false;
                    });
                }

                $rootScope.dialogTmp = dialogInstance;

                return dialogInstance;
            },

            alert: function(content) {
                if (!isEmpty($rootScope)) {
                    if (!$rootScope.flagOpenDialog) {
                        $rootScope.flagOpenDialog = true;
                    } else {
                        return $rootScope.dialogTmp;
                    }
                }

                DialogInfo.content = content;
                var dialogInstance = $modal.open({
                    templateUrl: 'alertDialog.html',
                    controller: AlertDialogCtrl,
                    keyboard: true
                });

                if (!isEmpty($rootScope)) {
                    dialogInstance.result.then(function() {
                        $rootScope.flagOpenDialog = false;
                    }, function() {
                        $rootScope.flagOpenDialog = false;
                    });
                }

                $rootScope.dialogTmp = dialogInstance;
                return dialogInstance;
            }
        };
    }
]);

app.factory('abc', ['$rootScope',
    function($rootScope) {
        return {
            testabc: function(pr) {
                $rootScope.$broadcast(pr);
            }
        };
    }
]);

app.factory('DialogInfo', function() {
    return {
        content: '',
        opened: ''
    };
});

app.factory('logoutService', ['$http', '$q',
    function($http, $q) {
        return {
            waitFor: function() {
                return $q.all([$http.get('/logout')]);
            }
        }
    }
]);

app.factory('Message', function($http, ComInfo) {
    var messages = [];
    var messagePromise = $http.get('/core/message').success(function(data) {
        for (var i = 0; i < data.length; i++) {
            messages[data[i].key] = data[i];
        }
    });

    return {
        promise: messagePromise,
        getMessage: function(key) {
            var message = '';
            if (key in messages) {
                message = messages[key].msg;
            }
            return message;
        },
        getMessageInfo: function(key) {
            var message = {};
            if (key in messages) {
                message = messages[key];
            }
            return message;
        },
        getMessageWithParams: function(key, paramsArr) {
            var message = '';
            if (key in messages) {
                message = messages[key].msg;
            }

            for (var i = 0; i < paramsArr.length; i++) {
                message = message.replace("<%S%>", paramsArr[i]);
            }

            return message;
        },
        getMessageInfoWithParams: function(key, paramsArr) {
            var message = '';
            if (key in messages) {
                message = messages[key].msg;
            }

            for (var i = 0; i < paramsArr.length; i++) {
                message = message.replace("<%S%>", paramsArr[i]);
            }

            var result = {};
            if (message != '') {
                result.msg = message;
                result.lvl = messages[key].lvl
            }

            return result;
        },
        showMessage: function(key) {
            if (key in messages) {
                ComInfo.level = messages[key].lvl;
                ComInfo.message = messages[key].msg;
            } else {
                ComInfo.level = 'E';
                ComInfo.message = 'メッセージマスタに該当するメッセージ(' + key + ')が存在しません。';
            }
        },
        getAllMessages: function() {
            return messages;
        },
        showMessageWithContent: function(lvl, msg) {
            ComInfo.level = lvl;
            ComInfo.message = msg;
        },
        clearMessage: function() {
            ComInfo.level = '';
            ComInfo.message = '';
        }
    };
});

app.factory('X007KbnMaster', function($http) {
    var x007Kbns = [];
    var kbnPromise = $http.get('/core/codemaster/x007kbnm').success(function(data) {
        x007Kbns = data;
    });

    return {
        promise: kbnPromise,
        getX007Kbn: function(cdKbn) {
            var kbnList = [];
            var j = 0;
            for (var i = 0; i < x007Kbns.length; i++) {
                if (!isEmpty(x007Kbns[i].kubun) && angular.equals(cdKbn, x007Kbns[i].kubun)) {
                    kbnList[j] = x007Kbns[i];
                    j++;
                }
            }
            return kbnList;
        },
        getAllX007Kbn: function() {
            return x007Kbns;
        },
        getX007Value: function(cdKbn, cd) {
            for (var i = 0; i < x007Kbns.length; i++) {
                if (!isEmpty(x007Kbns[i].kubun) && angular.equals(cdKbn, x007Kbns[i].kubun)
                        && angular.equals(cd, x007Kbns[i].code)) {
                    return x007Kbns[i];
                }
            }
            return {};
        }
    };
});

app.factory('Menu', function($http, $rootScope) {
    var menu = [];
    var menuPromise = $http.get('/core/menu').success(function(data) {
        menu = data;
    });

    return {
        promise: menuPromise,
        setMenu: function() {
            $rootScope.menu = menu;
        }
    };
});

app.factory('Gamen', function($http, $rootScope, ComInfo, $location) {
    $rootScope.comInfo = ComInfo;
    var gamens = [];
    var gamenPromise = $http.get('/core/gamen').success(function(data) {
        gamens = data;
    });

    return {
        promise: gamenPromise,
        setGamen: function() {
            $rootScope.gamenTitle = new Object();
            for (var i = 0; i < gamens.length; i++) {
                $rootScope.gamenTitle[gamens[i].gamenId] = gamens[i].gamenTitle;
            }

            var sid = $location.path().replace("/", "");
            if (sid != null && sid != '' && sid != undefined) {
                $rootScope.comInfo.sid = sid;
                $rootScope.comInfo.title = $rootScope.gamenTitle[sid];
                $rootScope.activePageId = sid;
            } else {
                $rootScope.comInfo.sid = $location.path().substring(1);
                $rootScope.comInfo.title = $location.path().substring(1);
            }
        }
    };
});
app.factory('GridValidator', function() {
    return function($scope, isEditable) {
        validator($scope, isEditable);
        return $scope.isValid;
    }
});

app.factory('TabValidator', function() {
    return function($scope, isEditable) {
        if ($scope.tabIsLoaded) {
            validator($scope, isEditable);
        }
        return $scope.isValid;
    }
});

var validator = function($scope, isEditable) {
    if (isEditable) {
        if (angular.element('#result div[ng-grid] div.ngViewport input')
            .not('.cs-disable')
            .hasClass('ng-invalid')) {
            $scope.isValid = $scope.form.$valid && false;
        } else {
            $scope.isValid = $scope.form.$valid && true;
        }
    }

}
app.factory('GetTabError', function() {
    return function(index) {
        var elements = angular.element('#box0' + (index + 1) + ' *[error-message]:not([disabled]), #box0' + (index + 1) + ' *[cc-error]:not([disabled])');
        var bcheck = false;
        angular.forEach(elements, function(value, key) {
            if (angular.element(value).hasClass('cs-error')
                || angular.element(value).hasClass('cs-parent-client-error')
                || angular.element(value).hasClass('cs-parent-server-error')
                || angular.element(value).hasClass('cs-parent-common-error')
                || angular.element(value).hasClass('cs-input-client-error')
                || angular.element(value).hasClass('cs-input-server-error')
                || angular.element(value).hasClass('cs-input-common-error')) {
                bcheck = true;
                return;
            }
        });
        return bcheck;
    }
});
app.factory('GetNgModelController', function() {
    return function(id) {
        var inputElement = angular.element(id);
        var modelCtrl = inputElement.controller('ngModel');
        return modelCtrl;
    }
})

/**
 * Client Service
 * To show message at client
 */
app.factory('Client', function(Message, $rootScope, ErrorInfo) {
    var isExistArray = function(errorArr, name, messageId) {
        for (var i = 0, len = errorArr.length; i < len; i++) {
            if (errorArr[i].name === name && errorArr[i].messageId === messageId) {
                return true;
            }
        }
        return false;
    };

    var createErrors = function(errorArr, messageList, clientFlag) {
        if (isEmpty(errorArr)) {
            errorArr = [];
        }

        for (var i = 0; i < messageList.length; i++) {
            var name = messageList[i].name;
            var messageId = messageList[i].messageId;
            if (!isExistArray(errorArr, name, messageId)) {
                var messageInfo = Message.getMessageInfo(messageId);
                var ErrorInfo = {};
                ErrorInfo.name = name;
                ErrorInfo.level = messageInfo.lvl;
                ErrorInfo.messageId = messageId;
                ErrorInfo.message = messageInfo.msg;
                ErrorInfo.clientErrorFlag = clientFlag;

                errorArr.push(ErrorInfo);
            }
        }
        return errorArr;
    };

    var createError = function(errorArr, name, messageId, clientErrorFlag) {
        if (isEmpty(errorArr)) {
            errorArr = [];
        }

        if (!isExistArray(errorArr, name, messageId)) {
            var messageInfo = Message.getMessageInfo(messageId);
            var ErrorInfo = {};
            ErrorInfo.name = name;
            ErrorInfo.level = messageInfo.lvl;
            ErrorInfo.messageId = messageId;
            ErrorInfo.message = messageInfo.msg;
            ErrorInfo.clientErrorFlag = clientErrorFlag;

            errorArr.push(ErrorInfo);
        }
        //        $rootScope.$broadcast("ccError", errorArr);
        return errorArr;
    }

    return {
        showErrorClient: function(errorArr, name, messageId) {
            errorArr = createError(errorArr, name, messageId, true);
            $rootScope.$broadcast("ccError", errorArr);
            return errorArr;
        },
        showErrorClientWithFlag: function(errorArr, name, messageId, clientErrorFlag) {
            errorArr = createError(errorArr, name, messageId, clientErrorFlag);
            $rootScope.$broadcast("ccError", errorArr);
            return errorArr;
        },
        showErrorArrayClient: function(errorArr, messageList) {
            errorArr = createErrors(errorArr, messageList, true);
            $rootScope.$broadcast("ccError", errorArr);
            return errorArr;
        },
        showErrorArrayClientWithFlag: function(errorArr, messageList, clientErrorFlg) {
            errorArr = createErrors(errorArr, messageList, clientErrorFlg);
            $rootScope.$broadcast("ccError", errorArr);
            return errorArr;
        },
        showErrorClientWithParam: function(errorArr, name, messageId, paramsArr) {
            if (isEmpty(errorArr)) {
                errorArr = [];
            }

            if (!isExistArray(errorArr, name, messageId)) {
                var messageInfo = Message.getMessageInfo(messageId, paramsArr);
                var message = messageInfo.msg;
                for (var i = 0; i < paramsArr.length; i++) {
                    message = message.replace("<%S%>", paramsArr[i]);
                }
                var ErrorInfo = {};
                ErrorInfo.name = name;
                ErrorInfo.level = messageInfo.lvl;
                ErrorInfo.messageId = messageId;
                ErrorInfo.message = message;
                ErrorInfo.clientErrorFlag = true;

                errorArr.push(ErrorInfo);
            }
            $rootScope.$broadcast("ccError", errorArr);
            return errorArr;
        },
        removeErrorClient: function(errorArr, name, messageId) {
            if (isEmpty(errorArr) || errorArr.length === 0) {
                $rootScope.$broadcast("ccError", errorArr);
                return errorArr;
            }

            for (var i = 0, len = errorArr.length; i < len; i++) {
                if (!angular.isUndefined(errorArr[i]) && errorArr[i].name === name && errorArr[i].messageId === messageId && errorArr[i].clientErrorFlag == true) {
                    errorArr.splice(i, 1);
                }
            }
            $rootScope.$broadcast("ccError", errorArr);
            return errorArr;
        },
        removeErrorArrayClient: function(errorArr, removeList) {
            if (isEmpty(errorArr) || errorArr.length === 0) {
                $rootScope.$broadcast("ccError", errorArr);
                return errorArr;
            }

            for (var i = 0; i < removeList.length; i++) {
                var name = removeList[i].name;
                var messageId = removeList[i].messageId;
                for (var j = 0, len = errorArr.length; j < len; j++) {
                    if (!angular.isUndefined(errorArr[j]) && errorArr[j].name === name && errorArr[j].messageId === messageId) {
                        errorArr.splice(j, 1);
                    }
                }
            }
            $rootScope.$broadcast("ccError", errorArr);
            return errorArr;
        },
        removeAllErrorByName: function(errorArr, name) {
            if (isEmpty(errorArr) || errorArr.length === 0) {
                return errorArr;
            }

            for (var i = 0, len = errorArr.length; i < len; i++) {
                if (angular.isUndefined(errorArr[i].name)) {
                    continue;
                }

                if (errorArr[i].name === name) {
                    errorArr.splice(i, 1);
                }
            }
            return errorArr;
        },

        removeAllErrorBroadcast: function(errorArr, name) {
            if (isEmpty(errorArr) || errorArr.length === 0) {
                return errorArr;
            }

            for (var i = 0, len = errorArr.length; i < len; i++) {
                if (errorArr[i] == undefined || angular.isUndefined(errorArr[i].name)) {
                    continue;
                }

                if (errorArr[i].name === name) {
                    errorArr.splice(i, 1);
                }
            }
            $rootScope.$broadcast("ccError", errorArr);
            return errorArr;
        },
        showErrorFromServer: function(errorArr, serverErr) {
            errorArr = serverErr;
            $rootScope.$broadcast("ccError", errorArr);
            return errorArr;
        },
        hasServerError: function(name, type) {
            var elementNm = '#' + name + ' ' + type;
            if (angular.element(elementNm).hasClass('cs-error')) {
                return true;
            }
            return false;
        },
        clearErrors: function() {
            error = null;
            $rootScope.$broadcast("ccError", error);
            return error;
        }
    };
});

app.factory('ClearComboboxOptions', function() {
    return function clear(scope, attrs) {
        if (attrs.ccRequired == 'true') {
            scope.optionArr = [{
                value: '',
                text: '＜選択してください＞'
            }];
        } else if (attrs.ccRequired == 'false') {
            scope.optionArr = [];
        }
    }
});

app.factory('ClearComboboxOptions', function() {
    return function(scope, attrs) {
        if (attrs.ccRequired == 'true') {
            scope.optionArr = [{
                value: '',
                text: '＜選択してください＞'
            }];
        } else {
            scope.optionArr = [];
        }
    }
});

app.factory('AddComboboxOptions', function() {
    return function(scope, data, delexist, shortName) {
        angular.forEach(data, function(value, key) {
            if (delexist == undefined || !(value.kubun == '9' && delexist == 'false')) {
                if (shortName == 'true') {
                    scope.optionArr.push({
                        value: value.code,
                        text: value.code + ':' + value.shortName
                    });
                } else if (shortName == 'false') {
                    scope.optionArr.push({
                        value: value.code,
                        text: value.code + ':' + value.name
                    });
                }
            }
        });
    }
});

app.factory('AddKbnComboboxOptions', function() {
    return function(scope, data) {
        angular.forEach(data, function(value, key) {
            scope.optionArr.push({
                value: value.code,
                text: value.code + ':' + value.name
            });
        });
    }
});

app.factory('AddComboboxOptionsName', function() {
    return function(scope, data) {
        angular.forEach(data, function(value, key) {
            scope.optionArr.push({
                value: value.code,
                text: value.name
            });
        });
    }
});

app.factory('AddKbn2ComboboxOptions', function() {
    return function(scope, data) {
        angular.forEach(data, function(value, key) {
            scope.optionArr.push({
                value: value.code,
                text: value.code
            });
        });
    }
});