/*[[
    /module/listtable/listtable.css
]]*/

angular.module('lg.listtable', []).
    directive('lgCodeTransformer', [function() {
        return {
            scope: {
                code: '@'
            },
            restrict: 'A',
            link: function(scope, element) {
                element.html(scope.code);
            }
        };
    }]).
    directive('lgListTable', [function() {
        return {
            scope: {
                colMeta: '=',
                rowsData: '='
            },
            restrict: 'E',
            replace: true,
            template:
                '<div class="lg-list-table">' +
                '<table class="table table-bordered table-striped">' +
                    '<thead>' +
                        '<tr>' +
                            '<th ng-repeat="(idx, colm) in colMeta">{{colm.header}}' +
                                '<span ng-if="sortable[idx]">' +
                                    '<span class="sort pull-right" ng-if="sortCol === idx" ng-click="changeSortType()"><i class="fa fa-sort-amount-{{sortType}}"></i></span>' +
                                    '<span class="pull-right" ng-if="sortCol !== idx" ng-click="changeSortCol(idx)">' +
                                        '<i class="fa fa-sort"></i>' +
                                    '</span>' +
                                '</span>' +
                            '</th>' +
                        '</tr>' +
                    '</thead>' +
                    '<colgroup>' +
                        '<col ng-repeat="col in colMeta" ng-style="{width: col.width}"/>' +
                    '</colgroup>' +
                    '<tbody>' +
                        '<tr ng-repeat="(ridx, row) in rowsData">' +
                            '<td ng-repeat="(cidx, col) in row" ng-style="{color: colMeta[cidx].color}">' +
                                '<div class="wrapper" ng-switch="colMeta[cidx].type">' +
                                    '<img ng-switch-when="img" ng-src="{{col.src}}" class="img"/>' +
                                    '<a ng-switch-when="link" ng-href="{{col.href}}" ng-click="!col.href && onLink($event, ridx, cidx)" class="link">{{col.label}}</a>' +
                                    '<div ng-switch-when="action" class="action">' +
                                        '<a ng-repeat="action in col.actions" ng-click="onAction(action, ridx)">{{colMeta[cidx].actions[action]}}</a>' +
                                    '</div>' +
                                    '<div ng-switch-when="html" class="html" lg-code-transformer code="{{col.code}}"></div>' +
                                    '<span ng-switch-default class="text">{{col.label}}</span>' +
                                '</div>' +
                            '</td>' +
                        '</tr>' +
                    '</tbody>' +
                '</table>' +
                '</div>',
            link: function(scope) {
                scope.sortable = _.map(scope.colMeta, function(meta) {
                    return angular.isUndefined(meta.type) || meta.type === 'text' || meta.type === 'link';
                });
                scope.sortCol = _.findIndex(scope.sortable, function(sortable) { return sortable; });
                scope.sortType = 'asc';

                scope.onAction = function(action, rowId) {
                    scope.$emit('lg.listtable.action', action, rowId);
                };
                scope.onLink = function(event, rowIdx, colIdx) {
                    scope.$emit('lg.listtable.link', rowIdx, colIdx);
                };

                scope.changeSortCol = function(col) {
                    scope.sortCol = col;
                };
                scope.changeSortType = function() {
                    scope.sortType = (scope.sortType === 'asc') ? 'desc' : 'asc';
                };
            }
        };
    }]);
