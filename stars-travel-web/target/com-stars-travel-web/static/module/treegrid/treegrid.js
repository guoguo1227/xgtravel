/*[[
    /module/treegrid/treegrid.css
]]*/

angular.module('lg.treegrid', [])
    .directive('lgTreeGrid', [function() {
        return {
            scope: {
                gridData: '='
            },
            restrict: 'E',
            replace: true,
            template:
                '<div class="lg-tree-grid">' +
                    '<table class="table table-{{gridData.style || \'bordered\'}}">' +
                    '<thead>' +
                        '<tr ng-if="$head" ng-click="_onSelectRow($event, $head)" ng-class="{selected: _isSelected($head, -1)}">' +
                            '<th class="title" lg-html-code="{{$head.title}}"></th>' +
                            '<th ng-repeat="$code in $head.items track by $index" lg-html-code="{{$code}}" ng-class="{selected: _isSelected($head, $index)}" ng-click="_onSelectCell($event, $head, $index)"></th>' +
                        '</tr>' +
                    '</thead>' +
                    '<tbody>' +
                        '<tr ng-repeat="$row in _filteredBody track by $index " ng-mouseenter="_hover = true" ng-mouseleave="_hover=false" ng-if="_isExpanded($row)" ng-click="_onSelectRow($event, $row)" ng-class="{selected: _isSelected($row, -1)}">' +
                            '<td class="title" ng-style="{\'padding-left\': ($row.level * 18 - 10) + \'px\'}">' +
                                '<i ng-if="!$row.leaf" ng-click="_onToggle($event, $row)" class="fa {{_getIcon($row)}}"></i>' +
                                '<span lg-html-code="{{$row.title}}"></span>' +
                            '</td>' +
                            '<td ng-repeat="$code in $row.items track by $index" ng-class="{selected: _isSelected($row, $index)}" ng-click="_onSelectCell($event, $row, $index)">' +
                                '<span lg-html-code="{{$code}}"></span>' +
                                '<div style="position: absolute; font-size: .5em; left: 10px; top: 60%; width: 100%; height: 100%;" ng-if="$row.root.tips" ng-style="{display: _hover ? \'block\' : \'none\'}">{{$row.root.tips[$index]}}</div>' +
                            '</td>' +
                        '</tr>' +
                    '</tbody>' +
                    '<tfoot>' +
                        '<tr ng-if="$foot" ng-click="_onSelectRow($event, $foot)" ng-class="{selected: _isSelected($foot, -1)}">' +
                            '<th class="title" lg-html-code="{{$foot.title}}"></th>' +
                            '<th ng-repeat="$code in $foot.items track by $index" ng-click="_onSelectCell($event, $foot, $index)" ng-class="{selected: _isSelected($foot, $index)}" lg-html-code="{{$code}}"></th>' +
                        '</tr>' +
                    '</tfoot>' +
                    '</table>' +
                '</div>',
            link: function(scope) {
                function init(node) {
                    _.forEach(node.children, function(child) {
                        child.parent = node;
                        child.root = node.root;
                        child.level = node.level + 1;
                        scope._tbody.push(child);

                        init(child);
                    });
                }

                function filterBody() {
                    if(!scope.gridData.searchKey) return scope._tbody;

                    return _.filter(scope._tbody, function(row) {
                        return row.title.indexOf(scope.gridData.searchKey) !== -1;
                    });
                }
                scope.$watch('_tbody', function(newVal) {
                    if(angular.isUndefined(newVal)) return;
                    scope._filteredBody = filterBody();
                });
                scope.$watch('gridData.searchKey', function(newVal) {
                    if(angular.isUndefined(newVal)) return;
                    scope._filteredBody = filterBody();
                });

                scope._getIcon = function(row) {
                    if(row.leaf) return '';
                    else if(!row.expand) return 'fa-plus-square-o';
                    else if(!row.children || !row.children.length) return 'fa-spin fa-spinner';
                    else return 'fa-minus-square-o';
                };

                scope._isExpanded = function(row) {
                    var parent = row.parent;
                    while(parent !== row.root) {
                        if(!parent.expand) return false;
                        parent = parent.parent;
                    }
                    return true;
                };

                scope._onToggle = function(event, row) {
                    row.expand = !row.expand;
                    scope.gridData.toggle && scope.gridData.toggle(row.expand, row);
                };

                scope._selected = {};

                scope._onSelectRow = function(event, row) {
                    if(!scope.gridData.selectRow || !scope.gridData.selectRow(row)) return;

                    scope._selected.row = row;
                    scope._selected.col = -1;
                };

                scope._onSelectCell = function(event, row, col) {
                    if(!scope.gridData.selectCell || !scope.gridData.selectCell(row, col)) return;

                    scope._selected.row = row;
                    scope._selected.col = col;
                };

                scope._isSelected = function(row, col) {
                    return scope._selected.row === row && scope._selected.col === col;
                };

                scope.$watch('gridData', function(newVal) {
                    if(angular.isUndefined(newVal)) return;

                    scope._tbody = [];

                    scope.gridData.level = 0;
                    scope.gridData.expand = true;
                    scope.gridData.root = scope.gridData;
                    scope.gridData.parent = scope.gridData;

                    /**
                     * 常用的表格操作函数, 提供给用户使用
                     */
                    scope.gridData.appendRows = function(parentRow, subrows) {
                        var row = parentRow;
                        var prevNode = row;
                        while(prevNode.children) {
                            prevNode = prevNode.children[prevNode.children.length - 1];
                        }

                        var tails = scope._tbody.splice(scope._tbody.indexOf(prevNode) + 1);

                        _.forEach(subrows, function(row) {
                            row.parent = parentRow;
                            row.level = parentRow.level + 1;
                            row.root = parentRow.root;

                            parentRow.children = parentRow.children || [];
                            parentRow.children.push(row);

                            init(row);
                        });

                        scope._tbody = scope._tbody.concat(subrows).concat(tails);
                    };

                    scope.gridData.deleteRow = function(row) {
                        var coll = row.parent.children;
                        var pos = coll.indexOf(row);
                        coll.splice(pos, 1);

                        var lastRow = row;
                        while(lastRow.children) {
                            lastRow = lastRow.children[lastRow.children.length - 1];
                        }
                        var lastPos = scope._tbody.indexOf(lastRow);
                        pos = scope._tbody.indexOf(row);
                        scope._tbody.splice(pos, lastPos - pos + 1);
                    };

                    scope.gridData.appendColumn = function(head, body, foot) {
                        head && scope.gridData.thead && scope.gridData.thead.items.push(head);
                        body && scope._tbody && _.forEach(scope._tbody, function(row) {
                            row.items.push(body);
                        });
                        foot && scope.gridData.tfoot && scope.gridData.tfoot.items.push(foot);
                    };

                    scope.gridData.deleteColumn = function(col) {
                        scope.gridData.thead && scope.gridData.thead.items.splice(col, 1);
                        scope._tbody && _.forEach(scope._tbody, function(row) {
                            row.items.splice(col, 1);
                        });
                        scope.gridData.tfoot && scope.gridData.tfoot.items.splice(col, 1);
                    }
                });

                scope.$watch('gridData.thead', function(newVal) {
                    if(angular.isUndefined(newVal)) return;
                    scope.$head = newVal;
                    newVal.root = scope.gridData;
                    newVal.parent = scope.gridData;
                });

                scope.$watch('gridData.tfoot', function(newVal) {
                    if(angular.isUndefined(newVal)) return;
                    scope.$foot = newVal;
                    newVal.root = scope.gridData;
                    newVal.parent = scope.gridData;
                });

                scope.$watch('gridData.tbody', function(newVal) {
                    if(angular.isUndefined(newVal)) return;

                    scope._tbody = [];

                    scope.gridData.children = newVal;
                    init(scope.gridData);
                });
            }
        };
    }]);
