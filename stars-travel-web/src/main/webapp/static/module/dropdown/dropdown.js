/*[[
    /module/dropdown/dropdown.css,
    /module/scrollable/scrollable.js
]]*/

angular.module('lg.dropdown', ['lg.scrollable']).
    directive('lgDropdown', ['$compile', function($compile) {
        return {
            restrict: 'A',
            scope: {
                struct: '='
            },
            link: function(scope, element) {
                element.addClass('lg-dropdown');
                element.addClass(scope.struct.type);
                element.append($compile('<lg-dropdown-content struct="struct"></lg-dropdown-content>')(scope));
            }
        };
    }]).
    directive('lgDropdownContent', [function() {
        return {
            restrict: 'E',
            scope: {
                struct: '='
            },
            replace: true,
            template:
                '<ul class="lg-dropdown-content">' +
                    '<li class="header">你有 <strong>{{struct.items.length}}</strong> 条{{typeMap[struct.type]}}</li>' +
                    '<li class="body" lg-scrollable>' +
                        '<ul class="lg-dropdown-menu">' +
                            '<li ng-repeat="item in struct.items" lg-dropdown-item type="{{struct.type}}" data="item"></li>' +
                        '</ul>' +
                    '</li>' +
                    '<li class="footer"><i class="fa fa-angle-down"></i></li>' +
                '</ul>',
            link: function(scope, element) {
                scope.typeMap = {'message': '信息'};
                if(scope.struct.align === 'right') {
                    element.css({right: 0, left: 'auto'});
                }
            }
        };
    }]).
    directive('lgDropdownItem', [function() {
        return {
            restrict: 'A',
            scope: {
                type: '@',
                data: '='
            },
            template:
                '<a>' +
                    '<img ng-src="{{data.img}}" class="img-circle pull-left"/>' +
                    '<h4>{{data.name}}' +
                        '<small class="pull-right">' +
                            '<i class="fa fa-clock-o"></i>' +
                            '{{data.time}}' +
                        '</small>' +
                    '</h4>' +
                    '<p>{{data.text}}</p>' +
                '</a>'
        };
    }]);
