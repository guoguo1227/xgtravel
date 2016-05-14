/*[[
    /module/tabs/tabs.css
]]*/

angular.module('lg.tabs', []).
    directive('lgTabs', [function() {
        return {
            scope: {
                active: '='
            },
            restrict: 'E',
            replace: true,
            transclude: true,
            template:
                '<div class="lg-tabs">' +
                    '<ul class="nav nav-tabs">' +
                        '<li class="lg-tab-title" ng-click="onClick($index)" ng-repeat="title in titles track by $index" ng-class="{active: activeTab == $index}">' +
                            '<a>{{title}}</a>' +
                        '</li>' +
                    '</ul>' +
                    '<div class="lg-tab-wrapper" ng-transclude></div>' +
                '</div>',
            controller: function($scope) {
                $scope.titles = [];
                this.register = function(title) {
                    $scope.titles.push(title);
                    return $scope.titles.length - 1;
                };
                this.getActiveTab = function() {
                    return $scope.activeTab;
                };
            },
            link: function(scope, element, attrs) {
                scope.activeTab = scope.active || 0;
                scope.onClick = function(index) {
                    scope.activeTab = index;
                    scope.$emit('lg.tabs.click', index);
                }
            }
        };
    }]).
    directive('lgTab', [function() {
        return {
            scope: {
                title: '@'
            },
            restrict: 'E',
            transclude: true,
            require: '^lgTabs',
            replace: true,
            template:
                '<div class="lg-tab" ng-transclude ng-class="{active: isActive()}"></div>',
            link: function(scope, element, attrs, tabsCtrl) {
                scope.idx = tabsCtrl.register(scope.title);
                scope.isActive = function() {
                    return tabsCtrl.getActiveTab() == scope.idx;
                };
            }
        };
    }]);
