/**
 * コード入力(取引先) : cc-file-uploder
 * @ngdo directives
 * @name chasecore.cc-file-uploder
 * @restrict A
 * @function
 *
 * @description
 * (コード入力時)取引先マスタから『取引先名称(or略称)』を取得・表示する
 *
 * @example
    <doc:example>
      <doc:source>
         <cc-file-uploader cc-label="取引ファイル名" cc-url="/core/HAJP3010/upload" ng-model="cond.file1"/>
      </doc:source>
      <doc:scenario>
      </doc:scenario>
    </doc:example>
 */

diretiveApp.directive('ccFileUploader',
    function(CSS_CLASS, $http, $interval, $timeout, UserInfo, HttpConst, Message, MsgConst) {
        return {
            restrict: 'E',
            required: 'ngModel',
            scope: {
                ngModel: '=',
                ccFocus: '='
            },
            template: function(element, attrs) {
                var htmlText = '';

                htmlText += '<div class="file-updoader-label cs-f-left">' + attrs.ccLabel + '</div>'

                htmlText += '<div class="file-uploader-container cs-f-left">';
                htmlText += '<div id="nano-progress" class="nano-progress"></div>';

                htmlText += '<input id="filename" class="filename" ng-model="ngModel" readonly cc-blur-validation error-message="{{error}}" ignored-error=true/>';
                htmlText += '<div ng-show="isFileSelected()" id="clearFilename" class="clearFilename"></div>';
                htmlText += '</div>';

                htmlText += '<span class="fileinput-button cs-f-left">';
                htmlText += '<span>参照...</span>';
                htmlText += '<input id="fileupload" type="file" name="files[]" cc-focus="ccFocus">';
                htmlText += '</span>';

                return htmlText;
            },
            link: function(scope, element, attrs) {
                var filesData;
                scope.isFileSelected = function() {
                    return filesData != undefined && filesData.files.length > 0;
                }

                scope.error = null;
                var inputCtrl = angular.element(element.find('input')[0]).controller('ngModel');


                var options = {
                    bg: '#ccc',
                    target: element.find('#nano-progress'),
                    id: 'mynano'
                };

                var nanobar = new Nanobar(options);

                element.find('#fileupload').keydown(function(event) {
                    if (event.keyCode == 13) {
                        element.find('#fileupload').click();
                        event.stopPropagation()
                    }
                });

                element.find('#filename').keydown(function(event) {
                    if (event.keyCode == 13) {
                        element.find('#fileupload').click();
                        event.stopPropagation()
                    }
                });

                element.find('#clearFilename').click(function(event) {
                    if (filesData != undefined) {
                        scope.$apply(function() {
                            filesData = undefined;
                            scope.ngModel = undefined;
                            inputCtrl.$setValidity('ccClientError', true);
                        });
                    }
                });

                var handleAddingFile = function(size) {
                    scope.$apply(function() {
                        scope.ngModel = filesData.files[0].name;

                        if (size == 0) {
                            inputCtrl.$setValidity('ccClientError', false);
                        } else {
                            inputCtrl.$setValidity('ccClientError', true);
                        }
                    });

                    if (size == 0) {
                        scope.$emit("uploaderError", HttpConst.CODE_BAD_REQUEST);

                        var message = Message.getMessageInfo(MsgConst.MSG_KEY_UPLOAD_EMPTY_FILE);
                        scope.error = {
                            name: '',
                            code: MsgConst.MSG_KEY_UPLOAD_EMPTY_FILE,
                            level: message.lvl,
                            message: message.msg
                        };

                        scope.$broadcast("uploaderError", scope.error);
                    }
                }

                var isIE9 = false;
                var stopToken;
                element.find('#fileupload').fileupload({
                    dataType: 'json',
                    multipart: true,
                    url: attrs.ccUrl,
                    dropZone: element.find('.file-uploader-container'),
                    add: function(e, data) {
                        filesData = data;
                        isIE9 = data.files[0].size == undefined;
                        if (!isIE9) {
                            handleAddingFile(data.files[0].size);
                        } else {
                            // check filesize in case IE9
                            element.find('#fileupload').fileupload({
                                url: '/core/checkEmptyFile'
                            });
                            filesData.submit();
                        }
                    },
                    start: function(e, data) {
                        var emitData = {};
                        emitData[attrs.id] = 'waiting';
                        scope.$emit("uploading", emitData);

                        if (isIE9 && element.find('#fileupload').fileupload('option').url != '/core/checkEmptyFile') {
                            var percentage = 0;
                            stopToken = $interval(function() {
                                percentage += 1.8;
                                nanobar.go(percentage);
                            }, 100, 53, false);
                        }
                    },
                    done: function(e, data) {
                        if (data.jqXHR.responseJSON.errors != undefined && data.jqXHR.responseJSON.errors.length > 0) {
                            if (data.jqXHR.responseJSON.errors[0].name == 'success') {
                                var emitData = {};
                                emitData[attrs.id] = 'succeeded';
                                scope.$emit("doneUpload", emitData);
                                scope.error = null;
                                scope.$broadcast("uploaderError", scope.error);
                            } else {
                                scope.$emit("uploaderError", HttpConst.CODE_BAD_REQUEST);
                                scope.error = data.jqXHR.responseJSON.errors[0];
                                scope.$broadcast("uploaderError", scope.error);
                            }
                        }
                    },
                    fail: function(e, data) {
                        if (data.jqXHR.status == HttpConst.CODE_INTERNAL_SERVER_ERROR) {
                            scope.$emit("uploaderError", HttpConst.CODE_INTERNAL_SERVER_ERROR);
                            return;
                        }

                        if (isIE9) {
                            if (element.find('#fileupload').fileupload('option').url == '/core/checkEmptyFile') {
                                if (data.errorThrown != undefined && data.errorThrown.length > 0) {
                                    if (data.errorThrown[0].name == 'success') {
                                        handleAddingFile(data.errorThrown[0].message);
                                    }
                                }
                                element.find('#fileupload').fileupload({
                                    url: attrs.ccUrl
                                });
                            } else {
                                $interval.cancel(stopToken);
                                $timeout(function() {
                                    nanobar.go(100);
                                }, 200);

                                $timeout(function() {
                                    if (data.errorThrown != undefined && data.errorThrown.length > 0) {
                                        if (data.errorThrown[0].name == 'success') {
                                            var emitData = {};
                                            emitData[attrs.id] = 'succeeded';
                                            scope.$emit("doneUpload", emitData);
                                            scope.error = null;
                                            scope.$broadcast("uploaderError", scope.error);
                                        } else {
                                            scope.$emit("uploaderError", HttpConst.CODE_BAD_REQUEST);
                                            scope.error = data.errorThrown[0];
                                            var params = scope.error.name.split(" ");
                                            scope.error.message = Message.getMessageWithParams(scope.error.code, params);
                                            scope.$broadcast("uploaderError", scope.error);
                                        }
                                    } else {
                                        scope.$emit("uploaderError", HttpConst.CODE_INTERNAL_SERVER_ERROR);
                                    }
                                }, 500);
                            }
                        }
                    },
                    progress: function(e, data) {
                        if (!isIE9) {
                            var progress = parseInt(data.loaded / data.total * 100, 10);
                            nanobar.go(progress);
                        }
                    }
                });

                scope.$on('startUpload', function() {
                    if (filesData != undefined) {
                        filesData.submit();
                        scope.error = null;
                        scope.$broadcast("uploaderError", scope.error);
                    }
                });

                scope.$on('clearFile', function() {
                    if (filesData != undefined) {
                        filesData = undefined;
                        scope.ngModel = undefined;
                        inputCtrl.$setValidity('ccClientError', true);
                    }
                });
            }
        }
    }
);