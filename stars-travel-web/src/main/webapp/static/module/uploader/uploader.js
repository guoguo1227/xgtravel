/*[[
    /module/uploader/uploader.css
]]*/

/**
 * TODO: 上传错误处理
 */
angular.module('lg.uploader', ['lg.modal'])
    .directive('lgUploader', ['$compile', '$timeout', function($compile, $timeout) {
        return {
            scope: {
                open: '=',
                url: '@',
                name: '@',
                accept: '=',
                keyValues: '=',
                onComplete: '&'
            },
            restrict: 'E',
            replace: true,
            template:
                '<div class="lg-uploader">' +
                    '<lg-modal open="open">' +
                        '<div style="padding-bottom: 100px;position: relative;">' +
                            '<div class="uploader-btn">' +
                                '<div class="btn-group">' +
                                    '<div class="btn btn-large btn-add" ng-disabled="uploading.length">' +
                                        '<span>添加文件&nbsp;&nbsp;<i class="fa fa-plus-circle fa-large"></i></span>' +
                                        '<input type="file" accept="{{acceptedTypes}}" multiple="multiple" ng-disabled="uploading.length">' +
                                    '</div>' +
                                    '<a class="btn btn-large btn-upload" ng-disabled="!canUpload()" ng-click="upload()"><i class="fa fa-upload fa-large"></i></a>' +
                                '</div>' +
                                '<h5 class="btn-tip">按住&nbsp;Ctrl&nbsp;可多选</h5>' +
                            '</div>' +
                            '<div class="uploader-header">' +
                                '<span>文件上传&nbsp; <small>关闭中断上传</small></span>' +
                            '</div>' +
                            '<ul class="uploader-list">' +
                                '<li class="list-item" ng-repeat="file in files track by $index">' +
                                    '<ul class="clearfix">' +
                                        '<li class="item-icon"><i class="fa fa-file-o"></i></li>' +
                                        '<li class="item-name">{{file.name}}</li>' +
                                        '<li class="item-size">{{formatSize(file.size)}}&nbsp;</li>' +
                                        '<li class="item-action" ng-click="onAction(file, $index)"><i class="fa {{file.uploadInfo.action}}"></i></li>' +
                                    '</ul>' +
                                    '<div class="progress">' +
                                        '<div class="progress-bar" ng-style="{width: file.uploadInfo.progress}"></div>' +
                                    '</div>' +
                                '</li>' +
                            '</ul>' +
                        '</div>' +
                    '</lg-modal>' +
                '</div>',
            link: function(scope, element) {
                var xhr = new XMLHttpRequest();

                var GB = 1024 * 1024 * 1024;
                var MB = 1024 * 1024;
                var KB = 1024;

                var INIT_STATE = 'init';
                var UPLOAD_STATE = 'upload';
                var LOAD_STATE = 'load';
                var ERROR_STATE = 'error';
                var ABORT_STATE = 'abort';

                var inputElem = element.find('input');

                scope.acceptedTypes = _.map(scope.accept, function(type) { return '.' + type; }).join(',');

                scope.$watch('open', function(newVal) {
                    if(angular.isUndefined(newVal)) {
                        scope.uploading = [];
                        scope.files = [];
                    }

                    if(!newVal) {
                        _.forEachRight(scope.files, function(file) {
                            if(file.uploadInfo.state === 'upload') {
                                xhr.abort();
                                return false;
                            }
                        });

                        delete scope.open;
                    }
                });

                scope.formatSize = function(size) {
                    if(size >= GB) {
                        var str = size / GB + '';
                        var idx = str.indexOf('.');
                        if(idx !== -1) {
                            str = str.substring(0, idx + 2);
                        }
                        return str + 'GB';
                    } else if (size >= MB) {
                        var str = size / MB + '';
                        var idx = str.indexOf('.');
                        if(idx !== -1) {
                            str = str.substring(0, idx + 2);
                        }
                        return str + 'MB';
                    } else if (size > KB) {
                        var str = size / KB + '';
                        var idx = str.indexOf('.');
                        if(idx !== -1) {
                            str = str.substring(0, idx + 2);
                        }
                        return str + 'KB';
                    } else {
                        return size + ' B';
                    }
                };

                function onComplete() {
                    //var btnAdd = angular.element(element[0].querySelector('.btn-add'));
                    //inputElem.remove();
                    //btnAdd.append($compile('<input type="file" accept="{{acceptedTypes}}" multiple="multiple" ng-disabled="uploading.length">')(scope));

                    $timeout(function() {
                        _.forEachRight(scope.files, function(file, i) {
                            if(file.uploadInfo.state !== ERROR_STATE) {
                                scope.files.splice(i, 1);
                            }
                        });

                        if(!scope.files.length) {
                                scope.open = false;
                        }
                    }, 1000);
                }

                scope.onAction = function(file, idx) {
                    switch(file.uploadInfo.state) {
                        case INIT_STATE:
                            scope.files.splice(idx, 1);
                        case UPLOAD_STATE:
                            xhr.abort();
                            break;
                        case ABORT_STATE:
                        case ERROR_STATE:
                        default:
                            break;
                    }
                };

                inputElem.bind('change', function() {
                    scope.$apply(function() {
                        if(scope.files.length
                            && !angular.isUndefined(scope.files[0].uploadInfo.progress)) scope.files = [];

                        scope.files = scope.files.concat(_.map([].slice.call(inputElem[0].files, 0), function(file) {
                            file.uploadInfo = {state: INIT_STATE, action: 'fa-times'};
                            return file;
                        }));
                    });
                });
                scope.$on('$destroy', function() {
                    inputElem.unbind('change');
                });

                function send() {
                    scope.uploading[0].uploadInfo.state = UPLOAD_STATE;
                    scope.uploading[0].uploadInfo.action = 'fa-spin fa-spinner';

                    var formData = new FormData();
                    formData.append(scope.name || 'file', scope.uploading[0]);
                    _.forEach(scope.keyValues, function(val, key) {
                        formData.append(key, val);
                    });

                    xhr.open('POST', scope.url);
                    xhr.send(formData);
                }

                xhr.upload.addEventListener('progress', function(event) {
                    $timeout(function() {
                        if (event.lengthComputable) {
                            if (!scope.uploading.length) return;
                            scope.uploading[0].uploadInfo.progress = event.loaded / event.total * 100 + '%';
                        }
                    });
                });

                xhr.addEventListener('load', function(event) {
                    $timeout(function() {
                        var file = scope.uploading.shift();
                        if(!file) return;

                        var param = {};
                        var status = parseInt(event.target.status);
                        param.name = file.name;
                        param.response = event.target.response;
                        (status == 200) && scope.onComplete({param: param});

                        file.uploadInfo.state = (status == 200 ? LOAD_STATE : ERROR_STATE);
                        file.uploadInfo.action = (status == 200 ? 'fa-check' : 'fa-warning');

                        if(scope.uploading.length) {
                            send();
                        } else {
                            onComplete();
                        }
                    });
                });

                xhr.addEventListener('abort', function() {
                    $timeout(function() {
                        var file = scope.uploading[0];
                        if(!file) return;

                        file.uploadInfo.state = ABORT_STATE;
                        file.uploadInfo.action = 'fa-ban';

                        scope.uploading.shift();
                        if(scope.uploading.length) {
                            send();
                        } else {
                            onComplete();
                        }
                    });
                });

                xhr.addEventListener('error', function() {
                    $timeout(function() {
                        var file = scope.uploading.shift();
                        if(!file) return;

                        file.uploadInfo.state = ERROR_STATE;
                        file.uploadInfo.action = 'fa-warning';

                        if(scope.uploading.length) {
                            send();
                        } else {
                            onComplete();
                        }
                    });
                });

                scope.canUpload = function() {
                    return scope.files.length
                        && !scope.uploading.length
                        && (!scope.files[0].uploadInfo || angular.isUndefined(scope.files[0].uploadInfo.progress));
                };

                scope.upload = function() {
                    if(!scope.canUpload()) return;
                    _.forEach(scope.files, function(file) {
                        scope.uploading.push(file);
                    });

                    send();
                };
            }
        };
    }]);