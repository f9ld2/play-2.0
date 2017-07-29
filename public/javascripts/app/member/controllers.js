///////////////////////////////////////////////////////////////////////
// Copyright(C) 2015 NEC Solution Innovators, Ltd. All Rights Reserved.
///////////////////////////////////////////////////////////////////////

/* ====================================================================
 * 機能名称 : メインコントローラ
 * 改版履歴
 * Rev. 改版年月日   改版者名       内容
 * 1.0   2014.04.07   HaiNP      新規作成
 * =================================================================== */
var app = angular.module('chasecore', [
    'gnmn0000','xxjp0000',
    'rootServices', 'ngRoute', 'ngCookies', 'pascalprecht.translate', 'filters', 'ngGrid', 'ui.bootstrap', 'mgo-mousetrap', 'triSearch',
    'kkkSearch', 'janSearch', 'santiSearch', 'binInfo',
    'hajp0020','hajp0030','hajp1010','hajp1020','hajp1040','hajp1090','hajp1170','hajp7000','hajp7020','hajp7030','hajp7040','kkjp0010','kkjp0080','kkjp1040',
    'kkjp1110','kkjp1180','kkjp1220','kkjp3000','sijp0010','sijp0040','sijp0110','sijp0130','sijp0170','sijp0190','sijp1140',
    'sijp1150','sijp1180','sijp1190','sijp1230','sijp1280','sijp7000','sijp7060','sijp7040','sijp7050','sijp7020','sijp7080','sijp7100','tkjp1060','urjp0010','urjp0040','urjp1140','urjp1180','urjp7030','zijp0030',
    'zijp1020','zijp1080','zijp7010','zijp7040', 'sijp7110', 'sijp7090', 'zijp7000', 'zijp7070', 'zijp7060', 'gnjp7000', 'gnjp7030'
]);


app.factory('myInterceptor', function($q, $rootScope) {
    $rootScope.pendingRequests = -1;
    return {
        'request': function(config) {
            if ($rootScope.pendingRequests == -1) {
                $rootScope.pendingRequests = 1;
            } else {
                $rootScope.pendingRequests++;
            }
            return config || $q.when(config);
        },

        'requestError': function(rejection) {
            $rootScope.pendingRequests--;
            return $q.reject(rejection);
        },

        'response': function(response) {
            $rootScope.pendingRequests--;
            return response || $q.when(response);
        },

        'responseError': function(rejection) {
            $rootScope.pendingRequests--;
            return $q.reject(rejection);
        }
    }
});

app.config(function($httpProvider) {
    $httpProvider.interceptors.push('myInterceptor');
});

app.config(function($routeProvider, $sceDelegateProvider) {
    $routeProvider.
    when('/', {
        templateUrl: '/assets/templates/cm/portal.html'
    }).
    when('/:gamenId', {
        templateUrl: function(params) {
            var gamenId = "";
            var paramsStr = "";
            if (params.gamenId.indexOf("?") != -1) {
                gamenId = params.gamenId.substring(0, params.gamenId.indexOf("?"));
                paramsStr = params.gamenId.substring(params.gamenId.indexOf("?"))
            } else {
                gamenId = params.gamenId;
            }

            var ss = angular.lowercase(params.gamenId.substr(0, 2));
            if (ss == 'xx') {
                ss = 'cm';
            }
            return '/assets/templates/' + ss + '/' + gamenId + '.html' + paramsStr;
        },
        resolve: {
            MessageInit: function(Message) {
                return Message.promise.then(function() {}, function() {});
            },
            MenuInit: function(Menu) {
                return Menu.promise.then(function() {
                    Menu.setMenu();
                }, function() {});
            },
            GamenInit: function(Gamen) {
                return Gamen.promise.then(function() {
                    Gamen.setGamen();
                }, function() {});
            },
            X007KbnMasterInit: function(X007KbnMaster) {
                return X007KbnMaster.promise.then(function() {}, function() {});
            }
        }
    }).
    otherwise({
        redirectTo: '/'
    });
    $sceDelegateProvider.resourceUrlWhitelist(['**']);
});

app.config(['$translateProvider',
    function($translateProvider) {
        $translateProvider.useStaticFilesLoader({
            prefix: 'assets/i18n/messages-',
            suffix: '.json'
        });
        $translateProvider.preferredLanguage('ja');
        //  $translateProvider.fallbackLanguage('en');
        $translateProvider.useMissingTranslationHandlerLog();
        //  $translateProvider.useLocalStorage();
    }
]);

app.run(function($rootScope, $location, session, $window, $cookies) {
    $rootScope.$on("$locationChangeStart", function(event, next, current) {
        if (isEmpty(session.tantoCd)) {
            delete $cookies['PLAY_SESSION'];
            $window.location.href = '/login';
        }
    });
});


app.config(["$httpProvider",
    function($httpProvider) {
        var interceptor = ["$q", "$window", "HttpConst", "$cookies",
            function($q, $window, HttpConst, $cookies) {
                return function(promise) {
                    return promise.then(
                        function(response) {
                            return response;
                        },
                        function(response) {
                            if (response.status === HttpConst.CODE_UNAUTHORIZED) {
                                delete $cookies['PLAY_SESSION'];
                                $window.location.href = '/login';
                            }
                            return $q.reject(response);
                        }
                    );
                };
            }
        ];
        $httpProvider.responseInterceptors.push(interceptor);
    }
]);

app.controller("RootCtrl", function($scope, $location, $window, ComInfo, Menu, Gamen, $cookies, $cookieStore, session,
    $http, Dialog, DialogInfo, DirectiveMsg, MsgConst, $rootScope, logoutService, UserInfo, X007KbnMaster) {
    $scope.comInfo = ComInfo;
    $scope.tantoNm = decodeURIComponent(escape(session.tantoNm)).replace(/\+/g, ' ');
    $scope.tantoCd = decodeURIComponent(escape(session.tantoCd)).replace(/\+/g, ' ');
    $scope.isOpen = false;
    $scope.memberName = "";
    $rootScope.flagOpenDialog = false;
    $rootScope.dialogTmp = null;
    if ($cookieStore.get("WINDOWS_OPENED") == null) {
        $cookieStore.put("WINDOWS_OPENED", 1);
    }
    var direcMsg = DirectiveMsg.getAllMessages(MsgConst.MSG_KEY_ALL_DIRECTIVE_MSG);
    var $menu = angular.element("#menu");


    $scope.activeIndex = -1;
    $scope.activePageId = -1;

    //get current date
    var d = new Date();
    $scope.year = d.getFullYear();
    $scope.month = d.getMonth() + 1;
    if ($scope.month < 10) {
        $scope.month = "0" + $scope.month;
    }
    $scope.day = d.getDate();
    if ($scope.day < 10) {
        $scope.day = "0" + $scope.day;
    }

    $scope.handleKeyBackSpace = function(e) {
        var nodeName = e.target.nodeName.toLowerCase();
        var nodeType = e.target.type == undefined ? undefined : e.target.type.toLowerCase();

        if (!((nodeName == 'input' &&
            (nodeType == 'text' || nodeType == 'email' || nodeType == 'button' || nodeType == 'url' || nodeType == 'number')) || nodeName == 'textarea')) {

            e.preventDefault();
        }
    }

    $scope.handleKeyF2 = function(e) {
        e.preventDefault();
        $scope.$broadcast('pressF2Key');
        if ($rootScope.flagOpenDialog == true) {
            return;
        }

        $scope.closeWindow();
    }

    $scope.handleKeyAltM = function(e) {
        e.preventDefault();
        if ($rootScope.flagOpenDialog == true) {
            return;
        }

        angular.element("#tab-focus-chosen a").focusout();
        angular.element("#tab-focus").focusout();
        var slideArgs = {
            left: '298px'
        };
        $menu.animate(slideArgs, 350);
        angular.element(this).hide();
        angular.element("#menu-open").hide();
        angular.element("#menu-close").show();
        angular.element("#menu-content a").attr("tabindex", " ");
        angular.element("#menu-content .activepage a").focus();
    }

    $scope.handleKeyESC = function(e) {
        e.preventDefault();
        var slideArgs = {
            left: '0'
        };
        $menu.animate(slideArgs, 350);
        angular.element("#menu-close").hide();
        angular.element("#menu-open").show();
        angular.element("#menu-content a").attr("tabindex", "-1");
        angular.element("#tab-focus-chosen a").focus();
        angular.element("#tab-focus").focus();

        $scope.$broadcast("ESC");
    }

    $scope.handleKeyDoNothing = function(e) {
        e.preventDefault();
    };

    $scope.doTransit = function(sid) {
        if (sid == $scope.activePageId) {
            return;
        }
        var msg = Dialog.confirm($scope.gamenTitle[sid] + "画面に遷移します。 よろしいですか?");
        if (msg == null) {
            return;
        }
        msg.result.then(function() {
            $location.url($location.path());
            $scope.comInfo.sid = sid;
            $scope.comInfo.title = $scope.gamenTitle[sid];
            $location.path('/' + sid);
            $scope.activePageId = sid;
            var slideArgs = {
                left: '0'
            };
            $menu.animate(slideArgs, 350);
            angular.element("#menu-close").hide();
            angular.element("#menu-open").show();
            angular.element("#menu-content a").attr("tabindex", " ");
            angular.element("#menu-content .cs-activepage a").focus();
        }, function() {});
    }

    $scope.popitup = function(url) {
        params = 'width=1004';
        params += ', height=667';
        params += ', left=0';
        params += ', top=0';
        params += ', toolbar=no';
        params += ', location=no';
        params += ', status=yes';
        params += ', resizable=no';
        params += ', menubar=no';
        params += ', channelmode=no';
        params += ', scrollbars=no';
        params += ', directories=no';

        var opened = $cookieStore.get("WINDOWS_OPENED");
        opened += 1;
        $cookieStore.put("WINDOWS_OPENED", opened);
        newwindow = $window.open(url, '_blank', params);
        if (window.focus) {
            newwindow.focus()
        }

        return false;
    }

    $scope.getCurrentDate = function() {
        var now = new Date();
        var month = (now.getMonth() + 1).toString();
        var day = (now.getDate()).toString();
        if (month.length == 1) {
            month = "0" + month;
        }
        if (day.length == 1) {
            day = "0" + day;
        }
        return now.getFullYear().toString() + month + day;
    }

    /**
     * Get Current Year
     */
    $scope.getCurrentYear = function() {
        return UserInfo.sysCurrentYear;
    }

    /**
     * Get Current Month
     */
    $scope.getCurrentMonth = function() {
        return UserInfo.sysCurrentMonth;
    }

    //Incase of click close button of browser, App will clear session
    $window.onbeforeunload = function() {
        $http.get("/logout").then(function(response) { // success
        }, function(response) { // error
        });
        delete $cookies['PLAY_SESSION'];
    }

    $scope.closeWindow = function() {
        var msg = Dialog.confirm("画面をクローズしますか？");
        if (msg == null) {
            return;
        }
        msg.result.then(function() {
            var opened = $cookieStore.get("WINDOWS_OPENED");
            if (opened == 1) {
                logoutService.waitFor().then(function() {
                    delete $cookies['PLAY_SESSION'];
                    $cookieStore.remove("WINDOWS_OPENED");
                    $window.open('', '_self', '');
                    $window.close();
                });
            } else {
                opened -= 1;
                $cookieStore.put("WINDOWS_OPENED", opened);
                $window.open('', '_self', '');
                $window.close();
            }
        }, function() {
            return false;
        });
    }

    $scope.hasItem = function(group) {
        return group.items.length > 0;
    }

    $scope.clickGroup = function(index) {
        if (index == $scope.activeIndex) {
            $scope.activeIndex = -1;
        } else {
            $scope.activeIndex = index;
        }
        return true;
    }

    /**
     * Get Max Record Number
     */
    $scope.getMaxRecordNumber = function(cd) {
        var cdKbn = "N0054";
        var X007Value = X007KbnMaster.getX007Value(cdKbn, cd);
        return X007Value.nameA || "";
    }

    // var menu = Menu.query({}, function() {
    //     $scope.menu = menu;
    //   }, function(response) {
    //     if (response.status === 404) {
    //     } else {
    //       ComInfo.message = "サーバー処理でシステムエラーが発生しました。"
    //     }
    //   });

    // var gamen = Gamen.query({}, function() {
    //     $scope.gamenTitle = new Object();
    //     for (var i = 0; i < gamen.length; i++) {
    //       $scope.gamenTitle[gamen[i].gamenId] = gamen[i].gamenTitle;
    //     }

    //     var sid = $location.path().replace("/", "");
    //     if (sid != null && sid != '' && sid != undefined) {
    //       $scope.comInfo.sid = sid;
    //         $scope.comInfo.title = $scope.gamenTitle[sid];
    //         $scope.activePageId = sid;
    //     } else {
    //         $scope.comInfo.sid = $location.path().substring(1);
    //         $scope.comInfo.title =  $location.path().substring(1);
    //     }
    //   }, function(response) {
    //     if (response.status === 404) {
    //     } else {
    //       ComInfo.message = "サーバー処理でシステムエラーが発生しました。"
    //     }
    //   });

    angular.element(document).ready(function() {
        angular.element("#menu-open").show();
        angular.element("#menu-close").hide();

        angular.element('#menu-content > ul > li:has(ul)').addClass("cs-has-sub");

        // angular.element('#menu-content > ul > li > a').click(function() {
        //     var checkElement = angular.element(this).next();
        //     angular.element('#menu-content li').removeClass('cs-active');
        //     angular.element(this).closest('li').addClass('cs-active');
        //     if((checkElement.is('ul')) && (checkElement.is(':visible'))) {
        //         angular.element(this).closest('li').removeClass('cs-active');
        //         checkElement.slideUp('normal');
        //     }
        //     if((checkElement.is('ul')) && (!checkElement.is(':visible'))) {
        //         angular.element('#menu-content ul ul:visible').slideUp('normal');
        //         checkElement.slideDown('normal');
        //     }
        //     if (checkElement.is('ul')) {
        //         return false;
        //     } else {
        //         return true;
        //     }
        // });

        // angular.element('#menu-content li a').hover(function() {
        //     angular.element(this).parent().addClass('cs-hover');
        // },
        // function() {
        //     angular.element(this).parent().removeClass('cs-hover');
        // });

        angular.element("#menu-content li a").focus(function() {
            angular.element(this).parent().addClass("cs-focus");
        });
        angular.element("#menu-content a").focusout(function() {
            angular.element(this).parent().removeClass("cs-focus");
        });

        //      var $menu = angular.element("#menu");
        //      angular.element("#menu-open").show();
        //      angular.element("#menu-close").hide();
        angular.element("#menu-content a").attr("tabindex", "-1");
        /*Click to open menu*/
        angular.element("#menu-open").on("click", function() {
            var slideArgs = {
                left: '298px'
            };
            $menu.animate(slideArgs, 350);
            angular.element(this).hide();
            angular.element("#menu-close").show();
            angular.element("#menu-content a").attr("tabindex", " ");
            angular.element("#menu-content .cs-activepage a").focus();
            return false;
        });

        angular.element("html").on("click", function(event) {
            if (angular.element(event.target).is('#menu-content *')) {
                return;
            }
            var slideArgs = {
                left: '0'
            };
            $menu.animate(slideArgs, 350);
            angular.element("#menu-close").hide();
            angular.element("#menu-open").show();
            angular.element("#menu-content a").attr("tabindex", "-1");
            angular.element('#menu-content li').removeClass('cs-hover');
            return true;
        });

        angular.element("#menu-close").on("click", function() {
            var slideArgs = {
                left: '0'
            };
            $menu.animate(slideArgs, 350);
            angular.element(this).hide();
            angular.element("#menu-open").show();
            angular.element("#menu-content a").attr("tabindex", "-1");
            angular.element("#tab-focus-chosen a").focus();
            angular.element("#tab-focus").focus();
            angular.element('#menu-content li').removeClass('cs-hover');
            return false;
        });
        angular.element("#tab_focus_chosen .chosen-search input").focus(function() {
            var slideArgs = {
                left: '0'
            };
            $menu.animate(slideArgs, 350);
            angular.element("#menu-close").hide();
            angular.element("#menu-open").show();
            angular.element("#menu-content a").attr("tabindex", "-1");
            angular.element('#menu-content li').removeClass('cs-hover');
            return false;
        });

        angular.element(document).bind('drop dragover', function(e) {
            e.preventDefault();
        });
    });

});

// lochv add 5/4/2014 : http://jsfiddle.net/jupiter/7jwZR/1/
(function(angular) {
    // See discussion on
    // http://stackoverflow.com/questions/12603914/reset-form-to-pristine-state-angularjs-1-0-x and
    // https://github.com/angular/angular.js/pull/1127 and
    // https://github.com/angular/angular.js/commit/733a97adf87bf8f7ec6be22b37c4676cf7b5fc2b
    // http://jsfiddle.net/Z9s3T/

    // Copied from AngluarJS
    function indexOf(array, obj) {
        if (array.indexOf) return array.indexOf(obj);

        for (var i = 0; i < array.length; i++) {
            if (obj === array[i]) return i;
        }
        return -1;
    }

    // Copied from AngularJS
    function arrayRemove(array, value) {
        var index = indexOf(array, value);
        if (index >= 0)
            array.splice(index, 1);
        return value;
    }

    // Copied from AngularJS
    var PRISTINE_CLASS = 'ng-pristine';
    var DIRTY_CLASS = 'ng-dirty';

    var formDirectiveFactory = function(isNgForm) {
        return function() {
            var formDirective = {
                restrict: 'E',
                require: ['form'],
                compile: function() {
                    return {
                        pre: function(scope, element, attrs, ctrls) {
                            var form = ctrls[0];
                            var $addControl = form.$addControl;
                            var $removeControl = form.$removeControl;
                            var controls = [];
                            form.$addControl = function(control) {
                                controls.push(control);
                                $addControl.apply(this, arguments);
                            }
                            form.$removeControl = function(control) {
                                arrayRemove(controls, control);
                                $removeControl.apply(this, arguments);
                            }
                            form.$setPristine = function() {
                                element.removeClass(DIRTY_CLASS).addClass(PRISTINE_CLASS);
                                form.$dirty  =  false;
                                form.$pristine  =  true;
                                angular.forEach(controls,  function(control)  {
                                    control.$setPristine();
                                });
                            }
                        },
                    };
                },
            };
            return isNgForm ? angular.extend(angular.copy(formDirective), {
                restrict: 'EAC'
            }) : formDirective;
        };
    }
    var ngFormDirective = formDirectiveFactory(true);
    var formDirective = formDirectiveFactory();
    angular.module('resettableForm', []).
    directive('ngForm', ngFormDirective).
    directive('form', formDirective).
    directive('ngModel', function() {
        return {
            require: ['ngModel'],
            link: function(scope, element, attrs, ctrls) {
                var control = ctrls[0];
                control.$setPristine = function() {
                    this.$dirty  =  false;
                    this.$pristine  =  true;
                    element.removeClass(DIRTY_CLASS).addClass(PRISTINE_CLASS);
                }
            },
        };
    });
})(angular);
// lochv add 5/4/2014 : http://jsfiddle.net/jupiter/7jwZR/1/

//Dialog Start
var ConfirmDialogCtrl = function($scope, $modalInstance, DialogInfo, $document) {
    DialogInfo.opened = '1';
    $document.bind('keydown', function(evt) {
        if (evt.which === 27) {
            DialogInfo.opened = '0';
        }
    });
    $scope.dialogInfo = DialogInfo;
    $scope.ok = function() {
        DialogInfo.opened = '0';
        $modalInstance.close('1');
    };

    $scope.cancel = function() {
        DialogInfo.opened = '0';
        $modalInstance.dismiss('0');
    };
};

var AlertDialogCtrl = function($scope, $modalInstance, DialogInfo) {
    $scope.dialogInfo = DialogInfo;
    $scope.ok = function() {
        $modalInstance.close('1');
    };
};
//Dialog End