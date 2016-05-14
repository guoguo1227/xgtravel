/*[[
    /module/paginator/paginator.css
]]*/

angular.module('lg.paginator', [])
    .directive('lgPaginator', [function() {
        return {
            scope: {
                totalPages: '=',
                visiblePages: '=',
                totalEntries: '=',
                onChange: '&'
            },
            restrict: 'E',
            replace: true,
            template:
                '<div class="lg-paginator clearfix">' +
                    '<div class="pull-left page-info">' +
                        '共&nbsp;{{totalPages}}&nbsp;页&nbsp;/&nbsp;{{totalEntries}}&nbsp;项' +
                    '</div>' +
                    '<ul class="pagination pull-right">' +
                        '<li><a ng-click="first()"><i class="fa fa-angle-double-left"></i></a></li>' +
                        '<li><a ng-click="prev()"><i class="fa fa-angle-left"></i></a></li>' +
                        '<li ng-class="{active: page == current}" ng-repeat="page in currentPages"><a ng-click="go(page)">{{page+1}}</a></li>' +
                        '<li><a ng-click="next()"><i class="fa fa-angle-right"></i></a></li>' +
                        '<li><a ng-click="last()"><i class="fa fa-angle-double-right"></i></a></li>' +
                    '</ul>' +
                '</div>',
            link: function(scope) {
                scope.current = 0;

                function adjust() {
                    var start = Math.ceil(scope.current - scope.visiblePages/2);
                    if(start >= 0) {
                        scope.currentPages = _.range(start, start+scope.visiblePages);
                    } else {
                        scope.currentPages = _.range(0, scope.visiblePages);
                    }
                }
                function init() {
                    if(scope.visiblePages > scope.totalPages) {
                        scope.visiblePages = scope.totalPages;
                    }
                    adjust();
                }

                scope.$watchCollection('[totalPages, visiblePages, totalEntries]', function(newVal) {
                    if(_.any(newVal, function(v) { return angular.isUndefined(v); })) return;
                    init();
                });

                scope.first = function() {
                    if(scope.current == 0) return;

                    scope.currentPages = _.range(0, scope.visiblePages);
                    scope.current = 0;
                    scope.onChange({page: scope.current});
                };

                scope.last = function() {
                    if(scope.current == scope.totalPages-1) return;

                    scope.currentPages = _.range(scope.totalPages - scope.visiblePages, scope.totalPages);
                    scope.current = scope.totalPages - 1;
                    scope.onChange({page: scope.current});
                };

                scope.prev = function() {
                    if(scope.current <= 0) return;

                    scope.current -= 1;
                    if(scope.currentPages[0] != 0) {
                        scope.currentPages.pop();
                        scope.currentPages.unshift(scope.currentPages[0]-1);
                    }
                    scope.onChange({page: scope.current});
                };

                scope.next = function() {
                    if(scope.current >= scope.totalPages-1) return;

                    scope.current += 1;
                    if(scope.currentPages[scope.visiblePages-1] != scope.totalPages-1) {
                        scope.currentPages.push(scope.currentPages[scope.visiblePages-1]+1);
                        scope.currentPages.shift();
                    }
                    scope.onChange({page: scope.current});
                };

                scope.go = function(page) {
                    if(page <= 0) return;
                    else if(page >= scope.totalPages-1) return;
                    else if(page == scope.current) return;

                    scope.current = page;
                    adjust();
                    scope.onChange({page: scope.current});
                };
            }
        };
    }]);
