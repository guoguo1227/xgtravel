/*[[
    /module/layout/layout.css,
    /module/sidebar/sidebar.js
]]*/

angular.module('lg.layout', ['lg.sidebar']).
    service('lgContentService', ['$rootScope', function($rootScope) {
        var isContentExtend;
        return {
            slide: function() {
                isContentExtend = !isContentExtend;
            },
            isContentExtend: function() {
                return isContentExtend;
            }
        };
    }]).
    directive('lgSlider', ['lgHeadbarService', 'lgSidebarService', 'lgContentService', 'lgFootbarService', function(headbarService, sidebarService, contentService, footbarService) {
        return {
            restrict: 'A',
            link: function(scope, element) {
                function slide() {
                    headbarService.slide();
                    contentService.slide();
                    sidebarService.slide();
                    footbarService.slide();
                }

                element.bind('click', function() {
                    scope.$apply(function() {
                        slide();
                    });
                });
            }
        };
    }]).
    service('lgHeadbarService', [function() {
        var isLogoShrink;
        return {
            slide: function() {
                isLogoShrink = !isLogoShrink;
            },
            isLogoShrink: function() {
                return isLogoShrink;
            }
        };
    }]).
    directive('lgHeadbar', ['lgHeadbarService', function(hbService) {
        return {
            scope: {
                struct: '='
            },
            restrict: 'E',
            replace: true,
            template:
            '<header class="lg-headbar">' +
                '<a class="logo logo-{{getState()}}"></a>' +
                '<nav class="navbar">' +
                    '<a lg-slider><i class="fa fa-bars"></i></a>' +
                    '<ul class="nav navbar-nav">' +
                        '<li ng-repeat="item in struct.items" lg-dropdown struct="item.dropdown">' +
                            '<a>' +
                                '<i class="fa fa-{{item.icon}}"></i>' +
                                '<span ng-if="item.labelColor" class="label label-{{item.labelColor}}">{{item.dropdown.items.length}}</span>' +
                            '</a>' +
                        '</li>' +
                    '</ul>' +
                '</nav>' +
            '</header>',
            link: function(scope, element) {
                scope.getState = function() {
                    var isLogoShrink = hbService.isLogoShrink();
                    return !angular.isUndefined(isLogoShrink) && (isLogoShrink ? 'shrink' : 'extend') || '';
                };

                hbService.headbarHeight = element[0].offsetHeight;
            }
        };
    }]).
    directive('lgContent', ['$window', 'lgContentService', 'lgHeadbarService', 'lgFootbarService', function($window, contentService, headbarService, footbarService) {
        return {
            restrict: 'E',
            scope: true,
            transclude: true,
            replace: true,
            template:
                '<div class="lg-content lg-content-{{getState()}}" ng-style="{\'min-height\': getContentHeight()}">' +
                    '<div ng-transclude></div>' +
                    '<br class="clearfix"/>' +
                '</div>',
            link: function(scope) {
                scope.getContentHeight = function() {
                    var fbHeight = footbarService.footbarHeight || 0;
                    var hbHeight = headbarService.headbarHeight || 0;
                    var clientHeight = $window.document.documentElement.clientHeight || 0;
                    return (clientHeight - fbHeight - hbHeight) + 'px';
                };

                angular.element($window).bind('resize', function() {
                    scope.$apply();
                });

                scope.getState = function() {
                    var isContentExtend = contentService.isContentExtend();
                    return !angular.isUndefined(isContentExtend) && (isContentExtend ? 'extend' : 'shrink') || '';
                };
            }
        };
    }]).
    service('lgFootbarService', [function() {
        var isFootbarShrink;
        return {
            slide: function() {
                isFootbarShrink = !isFootbarShrink;
            },
            isFootbarShrink: function() {
                return isFootbarShrink;
            },
            footbarHeight: undefined
        };
    }]).
    directive('lgFootbar', ['lgFootbarService', function(fbService) {
        return {
            restrict: 'E',
            replace: true,
            template:
                '<footer class="lg-footbar lg-footbar-{{getState()}}">' +
                '<div>' +
                    '<div class="pull-right">' +
                        '<b>Version</b>&nbsp;0.0.1' +
                    '</div>' +
                    '<strong>Copyright&nbsp;<i class="fa fa-copyright"></i>&nbsp;2015.11' +
                    '<a href="http://www.lagou.com">&nbsp;Lagou.com</a>' +
                    '</strong>' +
                    '.&nbsp;All rights reserved' +
                '</div>' +
                '</footer>',
            link: function(scope, element) {
                scope.getState = function() {
                    var isShrink = fbService.isFootbarShrink();
                    return !angular.isUndefined(isShrink) && (isShrink ? 'shrink' : 'extend') || '';
                };

                fbService.footbarHeight = element[0].offsetHeight;
            }
        };
    }]);

