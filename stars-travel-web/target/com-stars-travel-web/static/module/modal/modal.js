/*[[
    /module/modal/modal.css
]]*/

angular.module('lg.modal', []).
    directive('lgModal', ['$timeout', function($timeout) {
        return {
            scope: {
                open: '=',
                bgClickClose: '='
            },
            restrict: 'E',
            replace: true,
            transclude: true,
            template:
                '<div class="modal lg-modal lg-modal-{{state}}" ng-click="open = !bgClickClose;" ng-style="{display: !closed ? \'block\' : \'none\' }">' +
                    '<div class="modal-dialog lg-modal-dialog" ng-click="$event.stopPropagation()">' +
                        '<div class="dialog-content">' +
                            '<div ng-transclude></div>' +
                            '<a class="close" ng-click="open = false;"><i class="fa fa-times-circle-o"></i></a>' +
                        '</div>' +
                    '</div>' +
                '</div>',
            link: function(scope) {
                scope.closed = true;

                scope.$watch('open', function(newVal) {
                    if(angular.isUndefined(newVal)) return;

                    if(newVal) {
                        scope.closed = false;
                        scope.state = 'in';
                    } else {
                        scope.state = 'out';
                        $timeout(function() {
                            scope.closed = true;
                        }, 500);
                    }
                });
            }
        };
    }]);
