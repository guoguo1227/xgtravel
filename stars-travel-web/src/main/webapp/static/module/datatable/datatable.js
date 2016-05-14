/*[[
    /module/datatable/datatable.css
]]*/

angular.module('lg.datatable', [])
    .directive('lgDataTable', [function() {
        return {
            scope: {
                tableData: '='
            },
            restrict: 'E',
            replace: true,
            template:
                '<div class="lg-data-table">' +
                '<table class="table table-{{tableData.style || \'bordered\'}}">' +
                    '<thead>' +
                        '<tr ng-repeat="($ridx, $row) in $head.rows">' +
                            '<th class="clearfix" ng-repeat="($cidx, $cell) in $row.items track by $index" ng-if="$cell.rowspan != 0" rowspan="{{$cell.rowspan || 1}}" colspan="{{$cell.colspan || 1}}">' +
                                '<span lg-html-code="{{$cell.data}}"></span>' +
                            '</th>' +
                        '</tr>' +
                    '</thead>' +
                    '<tbody>' +
                        '<tr ng-repeat="($ridx, $row) in $body.rows track by $index">' +
                            '<td ng-repeat="($cidx, $cell) in $row.items track by $index" ng-if="$cell.rowspan != 0" rowspan="{{$cell.rowspan || 1}}" colspan="{{$cell.colspan || 1}}">' +
                                '<span lg-html-code="{{$cell.data}}"></span>' +
                            '</td>' +
                        '</tr>' +
                    '</tbody>' +
                    '<tfoot>' +
                        '<tr ng-repeat="($ridx, $row) in $foot.rows">' +
                            '<th ng-repeat="($cidx, $cell) in $row.items track by $index" rowspan="{{$cell.rowspan || 1}}" colspan="{{$cell.colspan || 1}}">' +
                                '<span lg-html-code="{{$cell.data}}"></span>' +
                            '</th>' +
                        '</tr>' +
                    '</tfoot>' +
                '</table>' +
                '</div>',
            link: function(scope) {
                function mergeFn(indices, eq, rows) {
                    if(rows && indices) {
                        eq = eq || function(c1, c2) { return c1.data === c2.data; };
                        _.forEach(indices, function(idx) {
                            var firstRow;
                            _.forEach(rows, function(row) {
                                if(!firstRow) {
                                    firstRow = row;
                                    delete firstRow.items[idx].rowspan;
                                    return;
                                }

                                if(eq(firstRow.items[idx], row.items[idx])) {
                                    firstRow.items[idx].rowspan = firstRow.items[idx].rowspan || 1;
                                    firstRow.items[idx].rowspan += 1;
                                    row.items[idx].rowspan = 0;
                                } else {
                                    firstRow = row;
                                    delete firstRow.items[idx].rowspan;
                                }
                            });
                        });
                    }
                }

                scope.$watchCollection('[tableData.thead.merge.indices, tableData.thead.merge.eq, tableData.thead.rows]', function(newVal) {
                    if(angular.isUndefined(newVal[2])) return;

                    var indices = newVal[0];
                    var eq = newVal[1];
                    var rows = newVal[2];

                    mergeFn(indices, eq, rows);
                    scope.$head = scope.tableData.thead;
                });

                scope.$watchCollection('[tableData.tfoot.merge.indices, tableData.tfoot.merge.eq, tableData.tfoot.rows]', function(newVal) {
                    if(angular.isUndefined(newVal[2])) return;

                    var indices = newVal[0];
                    var eq = newVal[1];
                    var rows = newVal[2];

                    mergeFn(indices, eq, rows);
                    scope.$foot = scope.tableData.tfoot;
                });

                scope.$watchCollection('[tableData.tbody.merge.indices, tableData.tbody.merge.eq, tableData.tbody.rows]', function(newVal) {
                    if(angular.isUndefined(newVal[2])) return;

                    var indices = newVal[0];
                    var eq = newVal[1];
                    var rows = newVal[2];

                    mergeFn(indices, eq, rows);
                    scope.$body = scope.tableData.tbody;
                    scope.$table = scope.tableData;
                });
            }
        };
    }]);
