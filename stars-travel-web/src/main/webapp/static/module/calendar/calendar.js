/*[[
    /module/calendar/calendar.css
]]*/

angular.module('lg.calendar', []).
    directive('lgCalendar', [function() {
        return {
            scope: {
                day: '=',
                earlymost: '=',
                latemost: '=',
                modeFixed: '='
            },
            restrict: 'E',
            replace: true,
            template:
                '<div class="lg-calendar">' +
                '<table class="table-condensed">' +
                    '<thead>' +
                        '<tr>' +
                            '<th><span ng-click="goPrev()"><i class="fa fa-arrow-left"></i></span></th>' +
                            '<th colspan="{{colspan}}">' +
                                '<span ng-click="changeYear()">{{year}}</span>' +
                                '&nbsp;年' +
                                '&nbsp;&nbsp;&nbsp;' +
                                '<span ng-click="changeMonth()">{{month + 1}}</span>' +
                                '&nbsp;月' +
                            '</th>' +
                            '<th><span ng-click="goNext()"><i class="fa fa-arrow-right"></i></span></th>' +
                        '</tr>' +
                        '<tr ng-if="mode === DAY_MODE">' +
                            '<th>日</th><th>一</th><th>二</th><th>三</th><th>四</th><th>五</th><th>六</th>' +
                        '</tr>' +
                    '</thead>' +
                    '<tbody class="{{mode}}">' +
                        '<tr ng-if="mode === YEAR_MODE" ng-repeat="group in yearGroups">' +
                            '<td ng-repeat="y in group" ng-class="{active: y === year}" ng-click="choose(mode, y)">{{y}}</td>' +
                        '</tr>' +
                        '<tr ng-if="mode === MONTH_MODE" ng-repeat="group in monthGroups">' +
                            '<td ng-repeat="m in group" ng-class="{active: m - 1 === month}" ng-click="choose(mode, m - 1)">{{m}}<span ng-if="$index !== 0">&nbsp;月</span></td>' +
                        '</tr>' +
                        '<tr ng-if="mode === DAY_MODE" ng-repeat="week in monthWeeks">' +
                            '<td ng-repeat="day in week" ng-class="{range: isInRange(day.year, day.month, day.date), off: day.off, active: isActive(day)}" ng-click="choose(mode, day)">{{day.date}}</td>' +
                        '</tr>' +
                    '</tbody>' +
                '</table>' +
                '</div>',
            link: function(scope) {
                function getMonthWeeks(year, month) {
                    var mm = moment([year, month, 1]);

                    var endOfPrevMonthMm, endOfPrevMonth;
                    var startOfMonth, endOfMonth;
                    var startOfNextMonthMm, startOfNextMonth;
                    var monthDays, curWeekday, curDate;

                    endOfPrevMonthMm = moment([year, month ,1]).subtract(1, 'months').endOf('month');
                    endOfPrevMonth = {year: endOfPrevMonthMm.year(), month: endOfPrevMonthMm.month(), date: endOfPrevMonthMm.date(), weekday: endOfPrevMonthMm.day()};
                    startOfNextMonthMm = moment([year, month, 1]).add(1, 'months').startOf('month');
                    startOfNextMonth = {year: startOfNextMonthMm.year(), month: startOfNextMonthMm.month(), date: startOfNextMonthMm.date(), weekday: startOfNextMonthMm.day()};

                    mm.startOf('month');
                    startOfMonth = {date: mm.date(), weekday: mm.day()};

                    mm.endOf('month');
                    endOfMonth = {date: mm.date(), weekday: mm.day()};

                    monthDays = [];
                    curWeekday = endOfPrevMonth.weekday;
                    if(curWeekday !== 6) {
                        curDate = endOfPrevMonth.date;
                        monthDays.push({date: endOfPrevMonth.date, off: true, year: endOfPrevMonth.year, month: endOfPrevMonth.month});

                        while(curWeekday > 0) {
                            curWeekday -= 1;
                            curDate -= 1;
                            monthDays.unshift({date: curDate, off: true, year: endOfPrevMonth.year, month: endOfPrevMonth.month});
                        }
                    }

                    curDate = startOfMonth.date;
                    while(curDate <= endOfMonth.date) {
                        monthDays.push({date: curDate, month: month, year: year});
                        curDate += 1;
                    }

                    curDate = startOfNextMonth.date;
                    while(monthDays.length < 6 * 7) {
                        monthDays.push({date: curDate, off: true, year: startOfNextMonth.year, month: startOfNextMonth.month});
                        curDate += 1;
                    }

                    var monthWeeks = [];
                    while(monthDays.length) {
                        monthWeeks.push(monthDays.splice(0, 7));
                    }

                    return monthWeeks;
                }

                function getYearGroups(year, yearStep) {
                    var i = 0, num = 4, half = Math.floor(num / 2);
                    var years = [], yearGroups = [];
                    year += yearStep * num * num;

                    while(i < num * num) {
                        years.push(year - half * (num + 1) + i);
                        i++;
                    }

                    while(years.length) {
                        yearGroups.push(years.splice(0, num));
                    }
                    return yearGroups;
                }

                function changeMode(mode) {
                    if(mode === scope.mode) return;

                    scope.mode = mode;
                    switch(mode) {
                        case scope.YEAR_MODE:
                            scope.colspan = 2;
                            scope.yearGroups = getYearGroups(scope.year, 0);
                            break;
                        case scope.MONTH_MODE:
                            scope.colspan = 2;
                            break;
                        default:
                            scope.colspan = 5;
                            scope.monthWeeks = getMonthWeeks(scope.year, scope.month);
                            break;
                    }
                }

                function isTooEarly(year, month, date) {
                    if(scope.earlymost) {
                        if(scope.earlymost.year > year) return true;
                        else if(month && scope.earlymost.year === year) {
                            if(scope.earlymost.month > month) return true;
                            else if(date && scope.earlymost.month === month) {
                                if(scope.earlymost.date > date) return true;
                            }
                        }
                    }

                    return false;
                }

                function isTooLate(year, month, date) {
                    if(scope.latemost) {
                        if(scope.latemost.year < year) return true;
                        else if(month && scope.latemost.year === year) {
                            if(scope.latemost.month < month) return true;
                            else if(date && scope.latemost.month === month) {
                                if(scope.latemost.date < date) return true;
                            }
                        }
                    }

                    return false;
                }

                scope.isInRange = function(year, month, date) {
                    if(scope.earlymost || scope.latemost) {
                        var earlymost = scope.earlymost || scope.day;
                        var latemost = scope.latemost || scope.day;
                        var mm = moment([year, month, date]);

                        return mm.isBefore(latemost) && mm.isAfter(earlymost) || mm.isSame(latemost) || mm.isSame(earlymost);
                    }
                    return false;
                };

                scope.isActive = function(day) {
                    return (scope.day.date === day.date) && (scope.day.month === day.month) && (scope.day.year === day.year);
                };

                scope.DAY_MODE = 'day';
                scope.YEAR_MODE = 'year';
                scope.MONTH_MODE = 'month';

                scope.mode = 'day';
                scope.colspan = 5;

                var today = moment();

                scope.day.year = scope.year = scope.day.year || today.year();
                scope.day.month = scope.month = scope.day.month || today.month();
                scope.day.date = scope.date = scope.day.date || today.date();
                scope.monthWeeks = getMonthWeeks(scope.year, scope.month);
                scope.monthGroups = [['冬', 12, 1, 2], ['春', 3, 4, 5], ['夏', 6, 7, 8], ['秋', 9, 10 ,11] ];

                scope.changeYear = function() {
                    if(scope.modeFixed) return;
                    changeMode('year');
                };
                scope.changeMonth = function() {
                    if(scope.modeFixed) return;
                    changeMode('month');
                };

                var yearStep = 0;
                var year, month, date;
                scope.goPrev = function() {
                    switch(scope.mode) {
                        case scope.DAY_MODE:
                            scope.year = (scope.month === 0) ? (scope.year - 1) : scope.year;
                            scope.month = (scope.month === 0) ? 11 : (scope.month - 1);
                            if(isTooEarly(scope.year, scope.month)) return;

                            scope.monthWeeks = getMonthWeeks(scope.year, scope.month);
                            break;
                        case scope.YEAR_MODE:
                            yearStep--;
                            scope.yearGroups = getYearGroups(scope.year, yearStep);
                            break;
                        default:
                            break;
                    }
                };

                scope.goNext = function() {
                    switch(scope.mode) {
                        case scope.DAY_MODE:
                            scope.year = (scope.month === 11) ? (scope.year + 1) : scope.year;
                            scope.month = (scope.month === 11) ? 1 : (scope.month + 1);
                            if(isTooLate(scope.year, scope.month)) return;

                            scope.monthWeeks = getMonthWeeks(scope.year, scope.month);
                            break;
                        case scope.YEAR_MODE:
                            yearStep++;
                            scope.yearGroups = getYearGroups(scope.year, yearStep);
                            break;
                        default:
                            break;
                    }
                };

                scope.choose = function(mode, val) {
                    switch(mode) {
                        case scope.YEAR_MODE:
                            if(isTooEarly(val) || isTooLate(val)) return;

                            scope.year = val;
                            changeMode(scope.DAY_MODE);
                            break;
                        case scope.MONTH_MODE:
                            if(_.isNaN(val)) return;
                            if(isTooEarly(scope.year, val) || isTooLate(scope.year, val)) return;

                            scope.month = val;
                            changeMode(scope.DAY_MODE);
                            break;
                        default:
                            if(val.off) return;
                            date = val.date;
                            if(isTooEarly(scope.year, scope.month, date) || isTooLate(scope.year, scope.month, date)) return;

                            scope.date = date;
                            break;
                    }

                    scope.day.date = scope.date;
                    scope.day.month = scope.month;
                    scope.day.year = scope.year;
                };
            }
        };
    }]).
    directive('lgDatePicker', ['$document', function($document) {
        return {
            scope: {
                day: '='
            },
            restrict: 'E',
            replace: 'true',
            template:
            '<div class="lg-datepicker" ng-class="{active: active}" ng-click="toggleCalendar($event)">' +
                '<div class="input-group">' +
                    '<div class="input-group-addon">' +
                        '<i class="fa fa-calendar"></i>' +
                    '</div>' +
                    '<input readonly class="form-control" type="text" placeholder="{{format(day)}}"/>' +
                '</div>' +
                '<lg-calendar day="day" ng-click="clickCalendar($event)"></lg-calendar>' +
            '</div>',
            link: function(scope) {
                $document.bind('click', function() {
                    scope.$apply(function() {
                        scope.active = false;
                    });
                });
                scope.$on('$destroy', function() {
                    $document.unbind('click');
                });

                scope.format = function(day) {
                    return [day.year, day.month + 1, day.date].join('/');
                };

                scope.toggleCalendar = function(event) {
                    event.preventDefault();
                    event.stopPropagation();
                    scope.active = !scope.active;
                };

                scope.clickCalendar = function(event) {
                    event.preventDefault();
                    event.stopPropagation();
                };
            }
        };
    }]).
    directive('lgCalendarRange', [function() {
        return {
            scope: {
                start: '=',
                end: '='
            },
            restrict: 'E',
            replace: true,
            template:
                '<div class="lg-calendar-range">' +
                    '<lg-calendar day="start" latemost="latemost" mode-fixed="true" class="start"></lg-calendar>' +
                    '<lg-calendar day="end" earlymost="earlymost" mode-fixed="true" class="end"></lg-calendar>' +
                '</div>',
            link: function(scope) {
                var startMm = moment([scope.start.year, scope.start.month, scope.start.date]);
                var endMm = moment([scope.end.year, scope.end.month, scope.end.date]);
                var tmpMm = endMm;
                if(startMm.isAfter(endMm)) {
                    endMm = startMm;
                    startMm = tmpMm;

                    scope.start.year = startMm.year();
                    scope.start.month = startMm.month();
                    scope.start.date = startMm.date();

                    scope.end.year = endMm.year();
                    scope.end.month = endMm.month();
                    scope.end.date = endMm.date();
                }

                scope.latemost = scope.end;
                scope.earlymost = scope.start;
            }
        };
    }]).
    directive('lgDateRangePicker', ['$document', function($document) {
        return {
            scope: {
                start: '=',
                end: '='
            },
            restrict: 'E',
            replace: true,
            template:
                '<div class="lg-date-range-picker" ng-class="{active: active}" ng-click="toggleCalendars($event)">' +
                    '<div class="input-group">' +
                        '<div class="input-group-addon">' +
                            '<i class="fa fa-calendar"></i>' +
                        '</div>' +
                        '<input readonly class="form-control" type="text" placeholder="{{format(start)}} - {{format(end)}}"/>' +
                    '</div>' +
                    '<lg-calendar-range start="start" end="end" ng-click="clickCalendars($event)"></lg-calendar-range>' +
                '</div>',
            link: function(scope) {
                $document.bind('click', function() {
                    scope.$apply(function() {
                        scope.active = false;
                    });
                });
                scope.$on('$destroy', function() {
                    $document.unbind('click');
                });

                scope.format = function(day) {
                    return [day.year, day.month + 1, day.date].join('/');
                };

                scope.toggleCalendars = function(event) {
                    event.preventDefault();
                    event.stopPropagation();
                    scope.active = !scope.active;
                };
                scope.clickCalendars = function(event) {
                    event.preventDefault();
                    event.stopPropagation();
                };
            }
        };
    }]);
