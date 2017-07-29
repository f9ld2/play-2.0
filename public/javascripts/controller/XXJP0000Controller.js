///////////////////////////////////////////////////////////////////////
// Copyright(C) 2015 NEC Solution Innovators, Ltd. All Rights Reserved.
///////////////////////////////////////////////////////////////////////

/* ====================================================================
 * 機能名称 : ログイン
 * 改版履歴
 * Rev. 改版年月日   改版者名       内容
 * 1.0   2014.04.01   HaiNP      新規作成
 * =================================================================== */
var app = angular.module('xxjp0000', ['xxjp0000Services', 'ui','ui.select2','directives', 'ngCookies', 'pascalprecht.translate', 'filters', 'mgo-mousetrap']);
app.config([ '$translateProvider', function($translateProvider) {
    $translateProvider.useStaticFilesLoader({
        prefix : 'assets/i18n/messages-',
        suffix : '.json'
    });
    $translateProvider.preferredLanguage('ja');
    $translateProvider.useMissingTranslationHandlerLog();
} ]);

app.controller("XXJP0000Ctrl",function($scope, $window, ComInfo, $cookies, $http, $q, $timeout, $location, $translate, Gamen) {
    $scope.comInfo = ComInfo;
    $scope.comInfo.message = 'ユーザー名とパスワードを入力してください。';
    $scope.comInfo.level = 'I';
    var gmn = Gamen.get({gamenId: 'XXJP0000'}, function() {
        $scope.comInfo.title = gmn.gamenTitle;
    }, function(response) {
    });

    $scope.cond = {};
    $scope.buttonDisable = false;
    
    /**
     * Action for login button
     */
    $scope.doLogin = function() {
        $scope.result = {};
        $scope.buttonDisable = true;
        $http.post("/login", $scope.cond)
        .then(
                function(response) { // success
                    if ($window.innerWidth == '1004') {
                        $window.location.href = '/core#/GNMN0000';
                    } else {
                        $scope.popitup('/core#/GNMN0000');
                    }
                }, function(response) { // error
                    if (response.status === 400) {
                        $scope.buttonDisable = false;
                        // エラー処理
                        $scope.comInfo.message = response.data.errors[0].message;
                        $scope.comInfo.level = response.data.errors[0].level;
                    }
                }
        );
    }

    /**
     * Enable/disable login button
     */
    $scope.canLogin = function() {
        if ($scope.form.$invalid || $scope.buttonDisable) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * Popup screen
     */
    $scope.popitup = function (url) {
        params = 'width=1004, height=667';
        params += ', left=0, top=0';
        params += ', toolbar=no';
        params += ', location=no';  
        params += ', status=yes';
        params += ', resizable=no';
        params += ', menubar=no';
        params += 'channelmode=yes, scrollbars=no';
        params += ', directories=no';

        newwindow = $window.open(url, '_blank', params);
        if (window.focus) {
            newwindow.focus()
        }

        $window.open('', '_self', '');
        $window.close();

        return false;
    }

    /**
     * Handle Key Enter
     */
    $scope.handleKeyEnter = function(e) {
        e.preventDefault();
        if($scope.canLogin()){
            $scope.doLogin();
        }
    }	 
});