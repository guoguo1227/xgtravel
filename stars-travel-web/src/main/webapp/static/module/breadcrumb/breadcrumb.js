/*[[
    /lib/font-awesome/css/font-awesome.css,
    /lib/bootstrap/css/bootstrap.css,
    /module/breadcrumb/breadcrumb.css,
]]*/

angular.module('lg.breadcrumb', ['ui.router', 'lg.router']).
    directive('lgBreadcrumb', ['$state', 'lgRouterService', function($state, rtService) {
        return {
            restrict: 'E',
            replace: true,
            template:
                '<ul class="lg-breadcrumb">' +
                    '<li ng-repeat="seg in paths track by $index">' +
                        '<i ng-if="!$index" class="fa fa-dashboard"></i>' +
                        '<i ng-if="$index" class="fa fa-angle-right"></i>' +
                        '<a ng-disabled="seg.abstract" ng-click="onClick($event, $index)">{{seg.name}}</a>' +
                    '</li>' +
                '</ul>',
            link: function(scope) {
                scope.$state = $state;

                scope.onClick = function(event, idx) {
                    event.preventDefault();
                    event.stopPropagation();

                    rtService.transferById(scope.paths[idx].id);
                };

                scope.$watch('$state.current.name', function(newVal) {
                    if(newVal) {
                        scope.paths = [];
                        var cfgs = rtService.getNavPathOfState(newVal);
                        _.forEach(cfgs, function(cfg) {
                            scope.paths.push(cfg);
                        });
                    }
                });
            }
        };
    }]);
