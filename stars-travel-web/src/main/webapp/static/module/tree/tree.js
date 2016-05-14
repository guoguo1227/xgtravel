/*[[
     /module/tree/tree.css
 ]]*/

angular.module('lg.tree', [])
    .directive('lgTree', [function() {
        return {
            scope: {
                treeData: '=',
                subtree: '='
            },
            restrict: 'E',
            replace: true,
            template:
            '<ul class="lg-tree" ng-class="{root: !subtree}" ng-style="{\'min-width\': (!subtree ? getMinWidth() : 0)}">' +
                '<lg-tree-node ng-repeat="node in treeData.children track by $index" node-data="node"></lg-tree-node>' +
            '</ul>',
            controller: function($scope) {
                if (!$scope.subtree) {
                    $scope.$watch('treeData', function (newVal) {
                        if (angular.isUndefined(newVal)) return;

                        $scope.treeData.expand = true;
                        $scope.treeData.root = $scope.treeData;
                        $scope.treeData.parent = $scope.treeData;

                        $scope.treeData.appendNodes = function(parentNode, childNodes) {
                            _.forEach(childNodes, function(child) {
                                child.root = $scope.treeData;
                                child.parent = parentNode;
                                if(!child.leaf && !child.children) {
                                    child.children = [];
                                }
                                parentNode.children.push(child);
                            });
                        };

                        $scope.treeData.removeNode = function(node) {
                            var coll = node.parent.children;
                            var pos = coll.indexOf(node);
                            coll.splice(pos, 1);
                        };

                        $scope.treeData.insertNodes = function(targetNode, newNodes) {
                            var coll = targetNode.parent.children;
                            var pos = coll.indexOf(targetNode);
                            var tails = coll.splice(pos);

                            _.forEach(newNodes, function(nn) {
                                if(!nn.leaf && nn.children) {
                                    nn.children = [];
                                }
                            });

                            tails = newNodes.concat(tails);
                            _.forEach(tails, function(child) {
                                coll.push(child);
                            });
                        };
                    });

                    $scope.$watchCollection('treeData.children', function (newVal) {
                        if (angular.isUndefined(newVal)) return;
                        _.forEach($scope.treeData.children, function (child) {
                            child.root = $scope.treeData;
                            child.parent = $scope.treeData;
                            if(!child.leaf && !child.children) {
                                child.children = [];
                            }
                        });
                    });
                }
            }
        };
    }]).
    directive('lgTreeNode', ['$compile', function($compile) {
        return {
            scope: {
                nodeData: '='
            },
            restrict: 'E',
            replace: true,
            template:
            '<li class="lg-tree-node">' +
                '<div class="node-row" ng-if="nodeData.title">' +
                    '<div class="row-hborder" ng-class="{long: nodeData.leaf}"></div>' +
                    '<ul class="clearfix">' +
                        '<li class="row-toggler" ng-if="!nodeData.leaf" ng-click="onToggle()"><i class="fa {{getIcon()}}"></i></li>' +
                        '<li class="row-title">' +
                            '<span lg-html-code="{{$code}}"></span>' +
                        '</li>' +
                    '</ul>' +
                '</div>' +
                '<div class="node-subtree" ng-class="{collapse: !nodeData.expand}">' +
                '</div>' +
            '</li>',
            link: function(scope, element) {
                scope.$node = scope.nodeData;

                scope.getIcon = function() {
                    if(scope.nodeData.expand) {
                        if(scope.nodeData.children && scope.nodeData.children.length) return 'fa-minus-square-o';
                        else return 'fa-spin fa-spinner';
                    } else {
                        return 'fa-plus-square-o';
                    }
                };

                scope.onToggle = function() {
                    if(scope.nodeData.leaf) return;

                    scope.nodeData.expand = !scope.nodeData.expand;
                    scope.nodeData.root.toggle && scope.nodeData.root.toggle(scope.nodeData.expand, scope.nodeData);
                };

                scope.$watch('nodeData.title', function(newVal) {
                    if(angular.isUndefined(newVal)) return;

                    scope.$code = newVal;
                });

                scope.$watch('nodeData.children', function(newVal) {
                    if(angular.isUndefined(newVal)) return;

                    _.forEach(scope.nodeData.children, function(child) {
                        child.root = scope.nodeData.root;
                        child.parent = scope.nodeData;
                        if(!child.leaf && !child.children) {
                            child.children = [];
                        }
                    });

                    var subtree = angular.element(element.children()[1]);
                    subtree.empty();
                    subtree.append($compile(
                        '<div class="subtree-vborder"></div>' +
                        '<div class="subtree-vborder-overlay"></div>' +
                        '<lg-tree subtree="true" tree-data="nodeData"></lg-tree>')(scope));
                });
            }
        };
    }]);
