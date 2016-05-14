/*[[
    /module/module.css,
    /module/router/router.js,
    /urlconfig.jsonp?callback=lgGetUrlConfig,
    /module/breadcrumb/breadcrumb.js,
    /module/echart/echart.js,
    /module/listtable/listtable.js,
    /module/dropdown/dropdown.js,
    /module/layout/layout.js,
    /module/sidebar/sidebar.js,
    /module/calendar/calendar.js,
    /module/modal/modal.js,
    /module/tabs/tabs.js,
    /module/tree/tree.js,
    /module/treegrid/treegrid.js,
    /module/datatable/datatable.js,
    /module/uploader/uploader.js,
    /module/paginator/paginator.js
]]*/

angular.module('lg.platform',
    [
        'lg.router',
        'lg.breadcrumb',
        'lg.echart',
        'lg.listtable',
        'lg.dropdown',
        'lg.layout',
        'lg.sidebar',
        'lg.calendar',
        'lg.modal',
        'lg.tabs',
        'lg.tree',
        'lg.treegrid',
        'lg.datatable',
        'lg.uploader',
        'lg.paginator'
    ])
    .directive('lgHtmlCode', ['$compile', function($compile) {
        return {
            restrict: 'A',
            link: function(scope, element, attrs) {
                scope.code = function() {
                    return attrs.lgHtmlCode;
                };

                scope.$watch('code()', function(newVal) {
                    if(angular.isUndefined(newVal)) return;

                    element.empty();
                    if(/^\s*<[\s\S]+>\s*$/.test(newVal)) {
                        element.append($compile(newVal)(scope));
                    } else {
                        element.text(newVal);
                    }
                });
            }
        };
    }])
    .directive('lgCheckbox', [function() {
        return {
            scope: {
                checked: '=',
                label: '='
            },
            restrict: 'E',
            replace: true,
            template:
            '<span class="lg-checkbox">' +
                '<span ng-click="checked = !checked" class="lg-checkbox-check">' +
                    '<i class="fa fa-check" ng-if="checked"></i>' +
                '</span>' +
                '<span ng-if="label" class="lg-checkbox-label">{{label}}</span>' +
            '</span>'
        };
    }])
    .directive('lgLink', ['lgRouterService', function(rtService) {
        return {
            scope: {
                id: '@',
                params: '='
            },
            restrict: 'E',
            replace: true,
            transclude: true,
            template:
                '<a class="lg-link" ng-click="onClick($event)" ng-transclude></a>',
            link: function(scope) {
                scope.onClick = function(event) {
                    event.preventDefault();
                    event.stopPropagation();

                    rtService.transferById(scope.id, scope.params);
                };
            }
        };
    }]);
