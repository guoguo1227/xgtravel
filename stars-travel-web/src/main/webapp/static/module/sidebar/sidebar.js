/*[[
    /module/sidebar/sidebar.css,
]]*/

angular.module('lg.sidebar', ['ui.router', 'lg.router']).
    service('lgSidebarService', [function() {
        var isSidebarShrink;

        return {
            slide: function() {
                isSidebarShrink = !isSidebarShrink;
            },
            isSidebarShrink: function() {
                return isSidebarShrink;
            }
        };
    }]).
    directive('lgSidebar', ['lgSidebarService', function(sbService) {
        return {
            scope: true,
            restrict: 'E',
            replace: true,
            transclude: true,
            template: '<aside class="lg-sidebar lg-sidebar-{{getState()}}" ng-transclude></aside>',
            link: function(scope) {
                scope.getState = function() {
                    var isSidebarShrink = sbService.isSidebarShrink();
                    return !angular.isUndefined(isSidebarShrink) && (isSidebarShrink ? 'shrink' : 'extend') || '';
                }
            }
        };
    }]).
    directive('lgSidebarMenu', ['lgRouterService', function(rtService) {
        return {
            scope: {
                navData: '=',
                odd: '='
            },
            restrict: 'E',
            replace: true,
            template:
                '<ul class="lg-sidebar-menu" ng-class="{even: !odd, odd: odd}">' +
                    '<li ng-repeat="item in navData" lg-sidebar-menu-item odd="!!odd" item-data="item" class="lg-sidebar-menu-item"></li>' +
                '</ul>',
            link: function(scope) {
                if(!scope.navData) {
                    scope.navData = rtService.getNavConfig();
                }
            }
        };
    }]).
    directive('lgSidebarMenuItem', ['$compile', 'lgRouterService', function($compile, rtService) {
        return {
            scope: {
                itemData: '=',
                odd: '='
            },
            restrict: 'A',
            template:
                '<div ng-switch on="type" class=item-label-wrapper>' +
                    '<div ng-switch-when="menu">' +
                        '<a ng-click="onClick($event)" class="item-label" ng-disabled="itemData.abstract">' +
                            '<i class="fa fa-{{itemData.icon}} left"></i>' +
                            '<span class="middle">{{itemData.name}}</span>' +
                            '<i class="fa fa-angle-right right"></i>' +
                        '</a>' +
                    '</div>' +
                    '<a ng-switch-default="link" class="item-label" ng-disabled="itemData.abstract" ng-click="onClick($event)">' +
                        '<i class="fa fa-{{itemData.icon}} left"></i>' +
                        '<span class="middle">{{itemData.name}}</span>' +
                    '</a>' +
                '</div>',
            link: function(scope, element, attrs) {
                scope.onClick = function(event) {
                    event.stopPropagation();
                    event.preventDefault();

                    rtService.transferById(scope.itemData.id);
                };

                if(scope.itemData.children) {
                    scope.type = 'menu';
                    element.append($compile('<lg-sidebar-menu odd="!odd" nav-data="itemData.children"></lg-sidebar-menu>')(scope));

                    if(scope.itemData.idx >= scope.itemData.children.length) {
                        attrs.$addClass('adjust'); //调整子列表的对齐方式
                    }
                } else {
                    scope.type = 'link';
                }
            }
        };
    }]);
