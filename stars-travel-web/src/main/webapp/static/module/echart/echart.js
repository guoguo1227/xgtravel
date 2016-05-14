/*[[
    /module/echart/echart.css
]]*/

angular.module('lg.echart', [])
    .directive('lgLineChart', ['$document', function($document) {
        return {
            scope: {
                chartData: '='
            },
            restrict: 'E',
            replace: true,
            template:
            '<div class="lg-line-chart">' +
                '<div ng-style="{height: tall}"></div>' +
            '</div>',
            link: function(scope, element) {
                scope.$watch('chartData.height', function(newVal) {
                    scope.tall = (newVal || 400) + 'px';
                });

                var option = {
                    tooltip: {trigger: 'axis'},
                    legend: {},
                    toolbox: {
                        show: true,
                        feature: {
                            mark: {show: false},
                            dataView: {show: false},
                            magicType: {show: true, type: ['line', 'bar', 'stack', 'tiled']},
                            restore: {show: true},
                            saveAsImage: {show: true}
                        }
                    },
                    calculable: true,
                    xAxis: {
                        type: 'category',
                        boundaryGap: false
                    },
                    yAxis: {type: 'value'}
                };

                var lineNameMap = scope.chartData && scope.chartData.lineNameMap || function(line) { return line; };

                function render() {
                    var data = scope.chartData.data;

                    var lines = [];
                    _.forEach(data, function(value) {
                        lines = _.uniq(lines.concat(Object.keys(value)).sort());
                    });

                    var xAxis = Object.keys(data).sort();
                    var series = [];
                    _.forEach(lines, function(line) {
                        var lineData = _.map(xAxis, function(x) {
                            return data[x][line];
                        });

                        series.push({
                            name: lineNameMap(line),
                            type: 'line',
                            data: lineData
                        });
                    });

                    option.series = series;
                    option.legend.data = _.map(lines, lineNameMap);
                    option.xAxis.data = xAxis;

                    var elem = element.children()[0];
                    $document.ready(function() {
                        var chart = echarts.init(elem);
                        chart.setOption(option);
                    });
                }

                /**
                 * {'2015-11-20': {'name1': 1234, 'name2': 2345}}
                 */
                scope.$watchCollection('chartData.data', function(newVal) {
                    if(angular.isUndefined(newVal)) return;

                    lineNameMap = scope.chartData.lineNameMap || function(line) { return line; };
                    render();
                });
            }
        }
    }])
    .directive('lgDateChart', ['$document', function($document) {
        return {
            scope: {
                chartData: '='
            },
            restrict: 'E',
            replace: true,
            template:
                '<div class="lg-date-chart">' +
                    '<div class="chart-units btn-group">' +
                        '<a class="btn" ng-click="unit = DAY_UNIT">日</a>' +
                        '<a class="btn" ng-click="unit = WEEK_UNIT">周</a>' +
                        '<a class="btn" ng-click="unit = MONTH_UNIT">月</a>' +
                    '</div>' +
                    '<div ng-style="{height: tall}"></div>' +
                '</div>',
            link: function(scope, element) {
                scope.$watch('chartData.height', function(newVal) {
                    scope.tall = (newVal || 400) + 'px';
                });

                var option = {
                    tooltip: {trigger: 'axis'},
                    legend: {},
                    toolbox: {
                        show: true,
                        feature: {
                            mark: {show: false},
                            dataView: {show: false},
                            magicType: {show: true, type: ['line', 'bar', 'stack', 'tiled']},
                            restore: {show: true},
                            saveAsImage: {show: true}
                        }
                    },
                    calculable: true,
                    xAxis: {
                        type: 'category'
                    },
                    yAxis: {type: 'value'}
                };

                scope.DAY_UNIT = 'day';
                scope.WEEK_UNIT = 'week';
                scope.MONTH_UNIT = 'month';

                var lineNameMap = scope.chartData && scope.chartData.lineNameMap || function(line) { return line; };

                function isInSameUint(unit, day1, day2) {
                    return moment(day1, scope.chartData.format || 'YYYY-MM-DD').startOf(unit).isSame(moment(day2, scope.chartData.format || 'YYYY-MM-DD').startOf(unit));
                }
                function splitAsUnit(days) {
                    var groups = [];

                    var curGroup = [];
                    groups.push(curGroup);
                    _.forEach(days, function(day) {
                        if(!curGroup.length) {
                            curGroup.push(day);
                            return;
                        }

                        if(isInSameUint(scope.unit, day, curGroup[0])) curGroup.push(day);
                        else {
                            curGroup = [day];
                            groups.push(curGroup);
                        }
                    });

                    return groups;
                }
                function render() {
                    var data = scope.chartData.data;

                    var lines = [];
                    _.forEach(data, function(value) {
                        lines = _.uniq(lines.concat(Object.keys(value)).sort());
                    });

                    var dayGrps = splitAsUnit(Object.keys(data).sort());
                    var series = [];
                    _.forEach(lines, function(line) {
                        var lineData = [];
                        _.forEach(dayGrps, function(group) {
                            var res = 0;
                            _.forEach(group, function(date) {
                                res += data[date] && data[date][line] || 0;
                            });
                            lineData.push(res);
                        });

                        series.push({
                            name: lineNameMap(line),
                            type: 'bar',
                            data: lineData
                        });
                    });

                    option.series = series;
                    option.legend.data = _.map(lines, lineNameMap);
                    option.xAxis.data = _.map(dayGrps, function(group) {
                        if(group.length == 1) return group[0];
                        else return group[0] + '-' + group[group.length-1];
                    });

                    var elem = element.children()[1];
                    $document.ready(function() {
                        var chart = echarts.init(elem);
                        chart.setOption(option);
                    });
                }

                scope.unit = scope.DAY_UNIT;
                scope.$watch('unit', function(newVal) {
                    if(angular.isUndefined(newVal)) return;
                    if(!scope.chartData || !scope.chartData.data) return;

                    render();
                });

                /**
                 * {'2015-11-20': {'name1': 1234, 'name2': 2345}}
                 */
                scope.$watchCollection('chartData.data', function(newVal) {
                    if(angular.isUndefined(newVal)) return;

                    lineNameMap = scope.chartData.lineNameMap || function(line) { return line; };
                    render();
                });
            }
        };
    }]);
