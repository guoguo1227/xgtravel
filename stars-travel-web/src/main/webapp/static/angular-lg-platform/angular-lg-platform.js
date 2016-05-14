angular.module('lg.platform',
    [
        'lg.router',
        'lg.breadcrumb',
        'lg.echart',
        'lg.layout',
        'lg.sidebar',
        'lg.modal',
        'lg.tabs',
        'lg.tree',
        'lg.treegrid',
        'lg.datatable',
        'lg.uploader',
        'lg.paginator',
        'lg.time',
        'lg.graph',
        'lg.table'
    ])
    .service('lgWsService', [function () {
        function create(option) {
            if (!option.url) {
                throw '未设置url';
            } else if (!option.onmessage) {
                throw '未设置消息处理函数: onmessage';
            }

            var wsTime = {
                connect: function () {
                    try {
                        this.ws = new WebSocket(option.url);
                        this.ws.onopen = this.onopen;
                        this.ws.onmessage = this.onmessage;
                        this.ws.onclose = this.onclose;
                        this.ws.onerror = this.onerror;
                    } catch (exception) {
                        console.log(exception);
                    }
                },

                onopen: function () {
                    option.onopen && option.onopen();
                },

                onerror: function (evt) {
                    option.onerror && option.onerror(evt);
                },

                onmessage: function (m) {
                    option.onmessage && option.onmessage(m);
                },

                onclose: function (closeEvent) {
                    var codeMap = {};
                    codeMap[1000] = "(NORMAL)";
                    codeMap[1001] = "(ENDPOINT_GOING_AWAY)";
                    codeMap[1002] = "(PROTOCOL_ERROR)";
                    codeMap[1003] = "(UNSUPPORTED_DATA)";
                    codeMap[1004] = "(UNUSED/RESERVED)";
                    codeMap[1005] = "(INTERNAL/NO_CODE_PRESENT)";
                    codeMap[1006] = "(INTERNAL/ABNORMAL_CLOSE)";
                    codeMap[1007] = "(BAD_DATA)";
                    codeMap[1008] = "(POLICY_VIOLATION)";
                    codeMap[1009] = "(MESSAGE_TOO_BIG)";
                    codeMap[1010] = "(HANDSHAKE/EXT_FAILURE)";
                    codeMap[1011] = "(SERVER/UNEXPECTED_CONDITION)";
                    codeMap[1015] = "(INTERNAL/TLS_ERROR)";
                    var codeStr = codeMap[closeEvent.code];

                    option.onclose && option.onclose(codeStr);

                    if (closeEvent.code != 1000) {
                        wsTime.connect();
                    }
                }
            };

            wsTime.connect();
        }

        return {
            createSocket: create
        };
    }])
    .directive('lgHtmlCode', ['$compile', function ($compile) {
        return {
            restrict: 'A',
            link: function (scope, element, attrs) {
                attrs.$addClass('lg-html-code');
                scope.code = function () {
                    return attrs.lgHtmlCode;
                };

                scope.$watch('code()', function (newVal) {
                    if (angular.isUndefined(newVal)) return;

                    element.empty();
                    if (/^\s*<[\s\S]+>\s*$/.test(newVal)) {
                        element.append($compile(newVal)(scope));
                    } else {
                        element.text(newVal);
                    }
                });
            }
        };
    }])
    .directive('lgCheckbox', [function () {
        return {
            scope: {
                checked: '=',
                label: '='
            },
            restrict: 'E',
            replace: true,
            template: '<span class="lg-checkbox">' +
            '<span ng-click="checked = !checked" class="lg-checkbox-check">' +
            '<i class="fa fa-check" ng-if="checked"></i>' +
            '</span>' +
            '<span ng-if="label" class="lg-checkbox-label">{{label}}</span>' +
            '</span>'
        };
    }])
    .directive('lgLink', ['lgRouterService', function (rtService) {
        return {
            scope: {
                pageId: '@',
                params: '='
            },
            restrict: 'E',
            replace: true,
            transclude: true,
            template: '<a class="lg-link" ng-click="onClick($event)" ng-transclude></a>',
            link: function (scope) {
                scope.onClick = function (event) {
                    event.preventDefault();
                    event.stopPropagation();

                    rtService.transferById(scope.pageId, scope.params);
                };
            }
        };
    }])
    .directive('lgBmap', [function () {
        return {
            scope: {
                width: '=',
                height: '=',
                init: '&'
            },
            restrict: 'E',
            replace: true,
            template: '<div class="lg-bmap" ng-style="{width: width, height: height}">' +
            '<div class="map" style="height: 100%;width: 100%"></div>' +
            '</div>',
            link: function (scope, element) {
                var map = new BMap.Map(element[0].querySelector('.map'));
                scope.init({map: map});
            }
        };
    }])
    .directive('lgEchart', ['$document', function ($document) {
        return {
            scope: {
                width: '=',
                height: '=',
                init: '&'
            },
            restrict: 'E',
            replace: true,
            template: '<div class="lg-echart">' +
            '<div ng-style="{width: width, height: height}"></div>' +
            '</div>',
            link: function (scope, element) {
                $document.ready(function () {
                    var chartElem = element.children()[0];
                    scope.init({chart: echarts.init(chartElem)});
                });
            }
        };
    }])
    .directive('lgPrivUrl', ['lgRouterService', function (rtService) {
        return {
            restrict: 'A',
            link: function (scope, element, attrs) {
                var urls = rtService.getUserInfo().urls || [];
                if (urls.indexOf(attrs.lgPrivUrl) == -1) {
                    element.css({display: 'none'});
                }
            }
        };
    }])
    .directive('lgAutoUpload', ['$http', function ($http) {
        return {
            scope: {
                url: '@',
                fieldname: '@',
                onComplete: '&'
            },
            restrict: 'A',
            link: function (scope, element, attrs) {
                var url = attrs.url;
                var name = attrs.fieldname || 'file';

                element.bind('change', function () {
                    var formData = new FormData();
                    var file = element[0].files[0];
                    formData.append(name, file);

                    scope.$apply(function () {
                        $http({
                            method: 'POST',
                            url: url,
                            headers: {
                                'Content-Type': undefined
                            },
                            data: formData,
                            transformRequest: angular.identity
                        }).then(function (data) {
                            scope.onComplete({
                                param: {
                                    name: file.name,
                                    response: data.data
                                }
                            });
                        });
                    });
                });

                scope.$on('$destroy', function () {
                    element.unbind('change');
                });
            }
        };
    }]);


angular.module('lg.breadcrumb', ['ui.router', 'lg.router']).
    directive('lgBreadcrumb', ['$state', 'lgRouterService', function ($state, rtService) {
        return {
            restrict: 'E',
            replace: true,
            template: '<ul class="lg-breadcrumb">' +
            '<li ng-repeat="seg in paths track by $index">' +
            '<i ng-if="!$index" class="fa fa-dashboard"></i>' +
            '<i ng-if="$index" class="fa fa-angle-right"></i>' +
            '<a ng-disabled="seg.abstract" ng-click="onClick($event, $index)">{{seg.name}}</a>' +
            '</li>' +
            '</ul>',
            link: function (scope) {
                scope.$state = $state;

                scope.onClick = function (event, idx) {
                    event.preventDefault();
                    event.stopPropagation();

                    rtService.transferById(scope.paths[idx].id);
                };

                scope.$watch('$state.current.name', function (newVal) {
                    if (newVal) {
                        scope.paths = [];
                        var cfgs = rtService.getNavPathOfState(newVal);
                        _.forEach(cfgs, function (cfg) {
                            scope.paths.push(cfg);
                        });
                    }
                });
            }
        };
    }]);


angular.module('lg.datatable', [])
    .service('lgDataTableService', [function () {
        function splitKey(row, key) {
            if (_.isNumber(key)) return row[key];

            var ks = key.split('.');
            return _.reduce(ks, function (r, k) {
                return r && r[k];
            }, row);
        }

        function mergeRowsFn(indices, eq, rows) {
            if (rows && indices) {
                eq = eq || function (row1, row2, key) {
                    return splitKey(row1, key) === splitKey(row2, key);
                };

                _.forEach(indices, function (idx) {
                    var firstRow;
                    _.forEach(rows, function (row) {
                        if (!firstRow) {
                            firstRow = row;
                            firstRow.$$_rowspans = firstRow.$$_rowspans || {};
                            firstRow.$$_rowspans[idx] = 1;
                            return;
                        }

                        if (eq(firstRow, row, idx)) {
                            firstRow.$$_rowspans[idx] += 1;

                            row.$$_rowspans = row.$$_rowspans || {};
                            row.$$_rowspans[idx] = 0;
                        } else {
                            firstRow = row;

                            firstRow.$$_rowspans = firstRow.$$_rowspans || {};
                            firstRow.$$_rowspans[idx] = 1;
                        }
                    });
                });
            }
        }

        function setColKeys(tableData, part, colKeys) {
            if (colKeys) {
                tableData['$$_' + part + 'Keys'] = colKeys;
            }
        }

        function siftFields(row, keys, sift) {
            var hk = keys[0];
            var tk = keys.slice(1);

            if (angular.isArray(row[hk])) {
                sift[hk] = sift[hk] || [];

                row[hk].forEach(function (elem, i) {
                    if (angular.isArray(elem)) {
                        throw '不支持数组元素为数组的情形';
                    }

                    if (angular.isObject(elem)) {
                        sift[hk][i] = sift[hk][i] || {};
                        siftFields(elem, tk, sift[hk][i]);
                    } else {
                        sift[hk][i] = 1;
                    }
                });
            } else if (angular.isObject(row[hk])) {
                sift[hk] = sift[hk] || {};
                siftFields(row[hk], tk, sift[hk]);
            } else {
                sift[hk] = 1;
            }

            return sift;
        }

        function siftRow(row, colKeys) {
            var sift = {};
            colKeys.forEach(function (ck) {
                siftFields(row, ck.split('.'), sift);
            });

            return sift;
        }

        function flattenRow(row, key) {
            var rows = [];
            if (angular.isArray(row)) {
                rows = row.reduce(function (res, elem, i) {
                    if (angular.isArray(elem)) {
                        throw '不支持数组元素也为数组的情形';
                    }
                    res = res.concat(flattenRow(elem, '[' + i + ']').map(function (t) {
                        return t.map(function (tk) {
                            return key && (key + tk) || tk;
                        });
                    }));
                    return res;
                }, []);
            } else if (angular.isObject(row)) {
                var keyCols = [];
                var rowNum = 0;
                angular.forEach(row, function (val, k) {
                    var flatten = flattenRow(val, k).map(function (t) {
                        return t.map(function (tk) {
                            return key && (key + '.' + tk) || tk;
                        });
                    });
                    keyCols.push(flatten);

                    if (rowNum < flatten.length) {
                        rowNum = flatten.length;
                    }
                });
                keyCols.forEach(function (col) {
                    while (col.length < rowNum) {
                        col.push(col[0]);
                    }
                });
                while (--rowNum >= 0) {
                    rows.unshift(keyCols.reduce(function (res, col) {
                        res = res.concat(col[rowNum]);
                        return res;
                    }, []));
                }
            } else {
                rows.push([key]);
            }
            return rows;
        }

        function setPartWithArrays(tableData, part, arrays) {
            if (!arrays[0]) return;
            tableData['$$_t' + part] = arrays;
            tableData['$$_' + part + 'Keys'] = _.range(0, arrays[0].length);
        }

        function setPartWithObjects(tableData, part, objects, colKeys) {
            tableData['$$_t' + part] = objects;
            if (colKeys) {
                tableData['$$_' + part + 'Keys'] = colKeys;
            }
        }

        function iteratePart(tableData, part, cb) {
            _.forEach(tableData['$$_t' + part], function (row) {
                cb(row);
            });
        }

        function mergeCol(tableData, part, eq) {
            eq = eq || function (row, col1, col2) {
                return row[col1] === row[col2];
            };

            iteratePart(tableData, part, function (row) {
                row.$$_colspans = [];
                var prevKey;
                var keys = tableData['$$_' + part + 'Keys'];

                _.forEach(keys, function (key) {
                    if (eq(row, key, prevKey)) {
                        row.$$_colspans[prevKey]++;
                        row.$$_colspans[key] = 0;
                    } else {
                        row.$$_colspans[key] = 1;
                        prevKey = key;
                    }
                });
            });
        }

        var service = {
            /**
             @doc lgDataTableService {
                "name": "config(tableData, cfg)",
                "desc": "配置表格的显示格式",
                "params": {
                    "tableData": "传入lgDataTable标签的对象",
                    "cfg": "配置参数.其键值有以下几个",
                    "cfg.noscroll": "是否允许左右滚动",
                    "cfg.nowrap": "是否允许单元格内容自动折行",
                    "cfg.noexpand": "当单元格内容过长时,会以省略号显示.当鼠标悬停在它们上面时会将隐藏的内容显示出来.
                                    如果某列在noexpand数组中,则即使该列内容过长,其内容也不会在鼠标悬停其上时显示出来.
                                    如果noexpand为true,则所有列都不会展开",
                    "cfg.width": "设置某列的宽度"
                }
             }@end
             */
            config: function (tableData, cfg) {
                tableData.$$_fixed = cfg.noscroll;
                tableData.$$_widthMap = cfg.width;

                if (cfg.nowrap == true) {
                    tableData.$$_nowrap = true;
                } else if (_.isArray(cfg.nowrap)) {
                    tableData.$$_nowrapMap = _.reduce(cfg.nowrap, function (res, col) {
                        res[col] = true;
                        return res;
                    }, {});
                }

                if (cfg.noexpand == true) {
                    tableData.$$_noexpand = true;
                } else if (_.isArray(cfg.noexpand)) {
                    tableData.$$_noexpandMap = _.reduce(cfg.noexpand, function (res, col) {
                        res[col] = true;
                        return res;
                    }, {});
                }
            },

            setWidth: function (tableData, widthMap, expandCols, wrap) {
                tableData.$$_widthMap = widthMap;
                tableData.$$_nowrap = !wrap;
                tableData.$$_expandMap = _.reduce(expandCols, function (res, col) {
                    res[col] = true;
                    return res;
                }, {});
            },
            /**
             @doc lgDataTableService {
                "name": "mergeHeadRow(tableData, indices, eq)",
                "desc": "合并头部行,如果相邻行同列单元格内容相同,则将这两个单元格合并",
                "params": {
                    "tableData": "传入lgDataTable标签的对象",
                    "indices": "如果通过arrays来设置数据,则是列索引数组;如果通过对象来设置数据,则是键名数组",
                    "eq": "比较函数,两个单元格内容是否相同由它决定.
                         其原型是function(row1, row2, col).row1,row2是初始化或更新数据时传入的对象(参考),col是列索引或键名"
                }
            }@end
             */
            mergeHeadRow: function (tableData, indices, eq) {
                mergeRowsFn(indices, eq, tableData.$$_thead);
            },
            /**
             @doc lgDataTableService {
                "name": "mergeBodyRow(tableData, indices, eq)",
                "desc": "合并表体行,如果相邻行同列单元格内容相同,则将这两个单元格合并",
                "params": {
                    "tableData": "传入lgDataTable标签的对象",
                    "indices": "如果通过arrays来设置数据,则是列索引数组;如果通过对象来设置数据,则是键名数组",
                    "eq": "比较函数,两个单元格内容是否相同由它决定.
                         其原型是function(row1, row2, col).row1,row2是初始化或更新数据时传入的对象(参考),col是列索引或键名"
                }
            }@end
             */
            mergeBodyRow: function (tableData, indices, eq) {
                mergeRowsFn(indices, eq, tableData.$$_tbody);
            },
            /**
             @doc lgDataTableService {
                "name": "mergeFootRow(tableData, indices, eq)",
                "desc": "合并尾部行,如果相邻行同列单元格内容相同,则将这两个单元格合并",
                "params": {
                    "tableData": "传入lgDataTable标签的对象",
                    "indices": "如果通过arrays来设置数据,则是列索引数组;如果通过对象来设置数据,则是键名数组",
                    "eq": "比较函数,两个单元格内容是否相同由它决定.
                         其原型是function(row1, row2, col).row1,row2是初始化或更新数据时传入的对象(参考),col是列索引或键名"
                }
            }@end
             */
            mergeFootRow: function (tableData, indices, eq) {
                mergeRowsFn(indices, eq, tableData.$$_tfoot);
            },

            /**
             @doc lgDataTableService {
                "name": "setHeadColKeys(tableData, colKeys)",
                "desc": "设置表头的列键",
                "params": {
                    "tableData": "传入lgDataTable标签的对象",
                    "colKeys": "表头每行的单元格内容与该数组中的键名一一对应,行对象或行数组根据这些键取出对应的键值"
                }
            }@end
             */
            setHeadColKeys: function (tableData, colKeys) {
                setColKeys(tableData, 'head', colKeys);
            },
            /**
             @doc lgDataTableService {
                "name": "setHeadWithArrays(tableData, arrays)",
                "desc": "用矩阵(数组的数组)设置表头",
                "params": {
                    "tableData": "传入lgDataTable标签的对象",
                    "arrays": "数组中每一项都是一个数组, 代表表头中一行的列数据."
                }
            }@end
             */
            setHeadWithArrays: function (tableData, arrays) {
                setPartWithArrays(tableData, 'head', arrays);
            },
            /**
             @doc lgDataTableService {
                "name": "setHeadWithObjects(tableData, objects, colKeys)",
                "desc": "用对象数组设置表头",
                "params": {
                    "tableData": "传入lgDataTable标签的对象",
                    "objects": "数组中每一项都是一个JS对象, 代表表头中一行的数据",
                    "colKeys": "是一个字符串数组, 每项是objects中对象的键名, 表格中显示的内容就是键名对应的键值.
                                如果该参数存在就会将setHeadColKeys的事做了"
                }
            }@end
             */
            setHeadWithObjects: function (tableData, objects, colKeys) {
                setPartWithObjects(tableData, 'head', objects, colKeys);
            },

            /**
             @doc lgDataTableService {
                "name": "setBodyColKeys(tableData, colKeys)",
                "desc": "设置表体的列键",
                "params": {
                    "tableData": "传入lgDataTable标签的对象",
                    "colKeys": "表体每行的单元格内容与该数组中的键名或索引一一对应,行对象或行数组根据它们取出对应的键值"
                }
           }@end
             */
            setBodyColKeys: function (tableData, colKeys) {
                setColKeys(tableData, 'body', colKeys);
            },
            /**
             @doc lgDataTableService {
                "name": "setBodyWithArrays(tableData, arrays)",
                "desc": "用矩阵(数组的数组)设置表体",
                "params": {
                    "tableData": "传入lgDataTable标签的对象",
                    "arrays": "数组中每一项都是一个数组, 代表表头中一行的列数据."
                }
            }@end
             */
            setBodyWithArrays: function (tableData, arrays) {
                setPartWithArrays(tableData, 'body', arrays);
            },
            /**
             @doc lgDataTableService {
                "name": "setBodyWithObjects(tableData, objects, colKeys)",
                "desc": "用对象数组设置表体",
                "params": {
                    "tableData": "传入lgDataTable标签的对象",
                    "objects": "数组中每一项都是一个JS对象, 代表表头中一行的数据",
                    "colKeys": "是一个字符串数组, 每项是objects中对象的键名, 表格中显示的内容就是键名对应的键值.
                                如果该参数存在就会将setBodyColKeys的事做了"
                }
            }@end
             */
            setBodyWithObjects: function (tableData, objects, colKeys) {
                setPartWithObjects(tableData, 'body', objects, colKeys);
            },

            /**
             @doc lgDataTableService {
               "name": "setFootColKeys(tableData, colKeys)",
               "desc": "设置表尾的列键",
               "params": {
                   "tableData": "传入lgDataTable标签的对象",
                   "colKeys": "表尾每行的单元格内容与该数组中的键名或索引一一对应,行对象或行数组根据它们取出对应的键值"
               }
            }@end
             */
            setFootColKeys: function (tableData, colKeys) {
                setColKeys(tableData, 'foot', colKeys);
            },
            /**
             @doc lgDataTableService {
                "name": "setFootWithArrays(tableData, arrays)",
                "desc": "用矩阵(数组的数组)设置表尾",
                "params": {
                    "tableData": "传入lgDataTable标签的对象",
                    "arrays": "数组中每一项都是一个数组, 代表表头中一行的列数据."
                }
            }@end
             */
            setFootWithArrays: function (tableData, arrays) {
                setPartWithArrays(tableData, 'foot', arrays);
            },
            /**
             @doc lgDataTableService {
                "name": "setFootWithObjects(tableData, objects, colKeys)",
                "desc": "用对象数组设置表尾",
                "params": {
                    "tableData": "传入lgDataTable标签的对象",
                    "objects": "数组中每一项都是一个JS对象, 代表表头中一行的数据",
                    "colKeys": "是一个字符串数组, 每项是objects中对象的键名, 表格中显示的内容就是键名对应的键值.
                                如果该参数存在就会将setFootColKeys的事做了"
                }
            }@end
             */
            setFootWithObjects: function (tableData, objects, colKeys) {
                setPartWithObjects(tableData, 'foot', objects, colKeys);
            },

            /**
             @doc lgDataTableService {
                "name": "iterateHead(tableData, cb)",
                "desc": "遍历表头",
                "params": {
                    "tableData": "传入lgDataTable标签的对象",
                    "cb": "回调函数, 其原型是 function(row) {}, row就是设置表体时传入的行对象"
                }
             }@end
             */
            iterateHead: function (tableData, cb) {
                iteratePart(tableData, 'head', cb);
            },
            /**
             @doc lgDataTableService {
                "name": "iterateBody(tableData, cb)",
                "desc": "遍历表体",
                "params": {
                    "tableData": "传入lgDataTable标签的对象",
                    "cb": "回调函数, 其原型是 function(row) {}, row就是设置表体时传入的行对象"
                }
             }@end
             */
            iterateBody: function (tableData, cb) {
                iteratePart(tableData, 'body', cb);
            },
            /**
             @doc lgDataTableService {
                "name": "iterateFoot(tableData, cb)",
                "desc": "遍历表尾",
                "params": {
                    "tableData": "传入lgDataTable标签的对象",
                    "cb": "回调函数, 其原型是 function(row) {}, row就是设置表体时传入的行对象"
                }
             }@end
             */
            iterateFoot: function (tableData, cb) {
                iteratePart(tableData, 'foot', cb);
            },
            prependRow: function (tableData, row) {
                tableData.$$_tbody.unshift(row);
            },
            appendRow: function (tableData, row) {
                tableData.$$_tbody.push(row);
            },
            deleteRow: function (tableData, row) {
                var idx = tableData.$$_tbody.indexOf(row);
                tableData.$$_tbody.splice(idx, 1);
            },
            mergeHeadCol: function (tableData, eq) {
                mergeCol(tableData, 'head', eq);
            },
            mergeBodyCol: function (tableData, eq) {
                mergeCol(tableData, 'body', eq);
            },
            mergeFootCol: function (tableData, eq) {
                mergeCol(tableData, 'foot', eq);
            }
        };

        return service;
    }])
    .directive('lgDataTable', [function () {
        return {
            scope: {
                tableData: '='
            },
            restrict: 'E',
            replace: true,
            template: '<div class="lg-data-table">' +
            '<table class="table table-bordered" ng-class="{fixed: tableData.$$_fixed, nowrap: tableData.$$_nowrap}">' +
            '<thead>' +
            '<tr ng-repeat="$row in tableData.$$_thead">' +
            '<th ng-repeat="($col, $$_key) in tableData.$$_headKeys track by $index" ' +
            'ng-if="$row.$$_rowspans[$$_key] != 0 && $row.$$_colspans[$$_key] != 0" ' +
            'rowspan="{{$row.$$_rowspans[$$_key] || 1}}" ' +
            'colspan="{{$row.$$_colspans[$$_key] || 1}}">' +

            '<span lg-html-code="{{$row[$$_key]}}"></span>' +
            '</th>' +
            '</tr>' +
            '</thead>' +
            '<col ng-repeat="$$_key in tableData.$$_bodyKeys track by $index" ng-style="{width: tableData.$$_widthMap[$$_key]}"/>' +
            '<tbody>' +
            '<tr ng-repeat="$row in tableData.$$_tbody track by $index">' +
            '<td ng-repeat="($col, $$_key) in tableData.$$_bodyKeys track by $index" ' +
            'ng-class="{noexpand: tableData.$$_noexpand || tableData.$$_noexpandMap[$$_key], ' +
            'nowrap: tableData.$$_nowrap || tableData.$$_nowrapMap[$$_key]}" ' +
            'ng-if="$row.$$_rowspans[$$_key] != 0 && $row.$$_colspans[$$_key] != 0" ' +
            'rowspan="{{$row.$$_rowspans[$$_key] || 1}}" ' +
            'colspan="{{$row.$$_colspans[$$_key] || 1}}">' +

            '<span lg-html-code="{{splitKey($row, $$_key)}}"></span>' +
            '</td>' +
            '</tr>' +
            '</tbody>' +
            '<tfoot>' +
            '<tr ng-repeat="$row in tableData.$$_tfoot track by $index">' +
            '<th ng-repeat="($col, $$_key) in tableData.$$_footKeys track by $index" ' +
            'ng-if="$row.$$_rowspans[$$_key] != 0 && $row.$$_colspans[$$_key] != 0" ' +
            'rowspan="{{$row.$$_rowspans[$$_key] || 1}}" ' +
            'colspan="{{$row.$$_colspans[$$_key] || 1}}">' +

            '<span lg-html-code="{{$row[$$_key]}}"></span>' +
            '</th>' +
            '</tr>' +
            '</tfoot>' +
            '</table>' +
            '</div>',
            link: function (scope) {
                scope.$watch('tableData', function (newVal) {
                    if (angular.isUndefined(newVal)) return;
                    scope.$table = newVal;
                });

                scope.splitKey = function (row, key) {
                    if (_.isNumber(key)) return row[key];

                    var ks = key.split('.');
                    return _.reduce(ks, function (r, k) {
                        return r && r[k];
                    }, row);
                };
            }
        };
    }]);


angular.module('lg.echart', [])
    .directive('lgLineChart', ['$document', function ($document) {
        return {
            scope: {
                chartData: '='
            },
            restrict: 'E',
            replace: true,
            template: '<div class="lg-line-chart">' +
            '<div ng-style="{height: tall}"></div>' +
            '</div>',
            link: function (scope, element) {
                scope.$watch('chartData.height', function (newVal) {
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

                var lineNameMap = scope.chartData && scope.chartData.lineNameMap || function (line) {
                        return line;
                    };

                function render() {
                    var data = scope.chartData.data;

                    var lines = [];
                    _.forEach(data, function (value) {
                        lines = _.uniq(lines.concat(Object.keys(value)).sort());
                    });

                    var xAxis = Object.keys(data).sort();
                    var series = [];
                    _.forEach(lines, function (line) {
                        var lineData = _.map(xAxis, function (x) {
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
                    $document.ready(function () {
                        var chart = echarts.init(elem);
                        chart.setOption(option);
                    });
                }

                /**
                 * {'2015-11-20': {'name1': 1234, 'name2': 2345}}
                 */
                scope.$watchCollection('chartData.data', function (newVal) {
                    if (angular.isUndefined(newVal)) return;

                    lineNameMap = scope.chartData.lineNameMap || function (line) {
                        return line;
                    };
                    render();
                });
            }
        }
    }])
    .directive('lgDateChart', ['$document', function ($document) {
        return {
            scope: {
                chartData: '='
            },
            restrict: 'E',
            replace: true,
            template: '<div class="lg-date-chart">' +
            '<div class="chart-units btn-group">' +
            '<a class="btn" ng-click="unit = DAY_UNIT">日</a>' +
            '<a class="btn" ng-click="unit = WEEK_UNIT">周</a>' +
            '<a class="btn" ng-click="unit = MONTH_UNIT">月</a>' +
            '</div>' +
            '<div ng-style="{height: tall}"></div>' +
            '</div>',
            link: function (scope, element) {
                scope.$watch('chartData.height', function (newVal) {
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

                var lineNameMap = scope.chartData && scope.chartData.lineNameMap || function (line) {
                        return line;
                    };

                function isInSameUint(unit, day1, day2) {
                    return moment(day1, scope.chartData.format || 'YYYY-MM-DD').startOf(unit).isSame(moment(day2, scope.chartData.format || 'YYYY-MM-DD').startOf(unit));
                }

                function splitAsUnit(days) {
                    var groups = [];

                    var curGroup = [];
                    groups.push(curGroup);
                    _.forEach(days, function (day) {
                        if (!curGroup.length) {
                            curGroup.push(day);
                            return;
                        }

                        if (isInSameUint(scope.unit, day, curGroup[0])) curGroup.push(day);
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
                    _.forEach(data, function (value) {
                        lines = _.uniq(lines.concat(Object.keys(value)).sort());
                    });

                    var dayGrps = splitAsUnit(Object.keys(data).sort());
                    var series = [];
                    _.forEach(lines, function (line) {
                        var lineData = [];
                        _.forEach(dayGrps, function (group) {
                            var res = 0;
                            _.forEach(group, function (date) {
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
                    option.xAxis.data = _.map(dayGrps, function (group) {
                        if (group.length == 1) return group[0];
                        else return group[0] + '-' + group[group.length - 1];
                    });

                    var elem = element.children()[1];
                    $document.ready(function () {
                        var chart = echarts.init(elem);
                        chart.setOption(option);
                    });
                }

                scope.unit = scope.DAY_UNIT;
                scope.$watch('unit', function (newVal) {
                    if (angular.isUndefined(newVal)) return;
                    if (!scope.chartData || !scope.chartData.data) return;

                    render();
                });

                /**
                 * {'2015-11-20': {'name1': 1234, 'name2': 2345}}
                 */
                scope.$watchCollection('chartData.data', function (newVal) {
                    if (angular.isUndefined(newVal)) return;

                    lineNameMap = scope.chartData.lineNameMap || function (line) {
                        return line;
                    };
                    render();
                });
            }
        };
    }]);


angular.module('lg.graph', [])
    .directive('lgGraph', [function () {
        return {
            scope: {
                graphData: '=',
                height: '=',
                width: '=',
                radius: '=',
                color: '@',
                nodeColor: '@',
                lineColor: '@',
                textColor: '@'
            },
            restrict: 'E',
            replace: true,
            template: '<div class="lg-graph" style="margin: 0 auto" ng-style="{width: width+\'px\', height: height+\'px\'}">' +
            '<svg width="100%" height="100%">' +
            '<defs>' +
            '<marker id="lg-graph-arrow" ' +
            'markerUnits="strokeWidth" ' +
            'markerWidth="12" ' +
            'markerHeight="12" ' +
            'viewBox="0 0 12 12" ' +
            'refX="6" ' +
            'refY="6" ' +
            'orient="auto">' +

            '<path d="M2,2 L10,6 L2,10 L6,6 L2,2" style="fill: {{lineColor}};" />' +
            '</marker>' +
            '<radialGradient id="lg-graph-node-fill">' +
            '<stop offset="0%" stop-color="white"/>' +
            '<stop offset="100%" stop-color="{{nodeColor}}"/>' +
            '<radialGradient/>' +
            '</defs>' +
            '</svg>' +
            '</div>',
            link: function (scope, element) {
                scope.$watch('graphData', function (newVal) {
                    if (angular.isUndefined(newVal)) return;

                    var map = {};

                    var startLevel = 100;
                    var minLevel = 1000;
                    var nodes = scope.graphData;

                    var stop = false;
                    while (!stop) {
                        stop = true;
                        _.forEach(nodes, function (node) {
                            var level = node.$$_level;
                            _.forEach(map, function (n, id) {
                                if (node.inIds.indexOf(Number(id)) !== -1) {
                                    if (_.isUndefined(level) || n.$$_level >= level) {
                                        level = n.$$_level + 1;
                                        stop = false;
                                    }
                                }
                            });

                            if (_.isUndefined(level)) stop = false;

                            node.$$_level = level || startLevel;
                            map[node.id] = node;
                            if (node.$$_level < minLevel) minLevel = node.$$_level;
                        });
                    }

                    var lines = _.reduce(nodes, function (res, n) {
                        res = res.concat(_.map(n.outIds, function (outId) {
                            return [n, map[outId]];
                        }));
                        return res;
                    }, []);

                    map = [];
                    _.forEach(nodes, function (node) {
                        var level = node.$$_level - minLevel;
                        map[level] = map[level] || [];
                        map[level].push(node);
                        node.$$_level = level;
                    });

                    _.forEach(nodes, function (node) {
                        var step = scope.width / (map[node.$$_level].length + 1);
                        node.cx = (map[node.$$_level].indexOf(node) + 1) * step;
                        node.cy = (node.$$_level + 1) / (map.length + 1) * scope.height;
                    });

                    var svg = d3.select(element.find('svg')[0]);

                    var updateCircle = svg.selectAll('circle').data(nodes);
                    var enterCircle = updateCircle.enter();
                    var exitCircle = updateCircle.exit();

                    updateCircle
                        .attr('cx', function (node) {
                            return node.cx;
                        })
                        .attr('cy', function (node) {
                            return node.cy;
                        })
                        .attr('r', scope.radius)
                        .attr('fill', 'url(#lg-graph-node-fill)')
                        .attr('stroke', scope.nodeColor || scope.color);

                    enterCircle.append('circle')
                        .attr('cx', function (node) {
                            return node.cx;
                        })
                        .attr('cy', function (node) {
                            return node.cy;
                        })
                        .attr('r', scope.radius)
                        .attr('fill', 'url(#lg-graph-node-fill)')
                        .attr('stroke', scope.nodeColor || scope.color);

                    exitCircle.remove();

                    var updateText = svg.selectAll('text').data(nodes);
                    var enterText = updateText.enter();
                    var exitText = updateText.exit();

                    updateText
                        .attr('stroke', scope.textColor || scope.color)
                        .attr('x', function (node) {
                            return node.cx + scope.radius + 10;
                        })
                        .attr('y', function (node) {
                            return node.cy;
                        })
                        .text(function (node) {
                            return node.name;
                        });

                    enterText.append('text')
                        .attr('stroke', scope.textColor || scope.color)
                        .attr('x', function (node) {
                            return node.cx + scope.radius + 10;
                        })
                        .attr('y', function (node) {
                            return node.cy;
                        })
                        .text(function (node) {
                            return node.name;
                        });

                    exitText.remove();

                    var updateLine = svg.selectAll('line').data(lines);
                    var enterLine = updateLine.enter();
                    var exitLine = updateLine.exit();

                    updateLine.attr('stroke', scope.lineColor || scope.color)
                        .attr('x1', function (line) {
                            return line[0].cx;
                        })
                        .attr('y1', function (line) {
                            return line[0].cy + scope.radius + 2;
                        })
                        .attr('x2', function (line) {
                            return line[1].cx;
                        })
                        .attr('y2', function (line) {
                            return line[1].cy - scope.radius - 2;
                        })
                        .attr('marker-end', 'url(#lg-graph-arrow)');

                    enterLine.append('line')
                        .attr('stroke', scope.lineColor || scope.color)
                        .attr('x1', function (line) {
                            return line[0].cx;
                        })
                        .attr('y1', function (line) {
                            return line[0].cy + scope.radius + 2;
                        })
                        .attr('x2', function (line) {
                            var offset = 0;
                            if (line[0].cx < line[1].cx) offset = -6;
                            else if (line[0].cx > line[1].cx) offset = 6;
                            return line[1].cx + offset;
                        })
                        .attr('y2', function (line) {
                            return line[1].cy - scope.radius - 2;
                        })
                        .attr('marker-end', 'url(#lg-graph-arrow)');

                    exitLine.remove();
                });
            }
        };
    }]);


angular.module('lg.layout', ['lg.sidebar', 'lg.router']).
    service('lgContentService', [function () {
        var isContentExtend;
        return {
            slide: function () {
                isContentExtend = !isContentExtend;
            },
            isContentExtend: function () {
                return isContentExtend;
            }
        };
    }]).
    directive('lgSlider', [
        'lgHeadbarService',
        'lgSidebarService',
        'lgContentService',
        'lgFootbarService',
        function (headbarService, sidebarService, contentService, footbarService) {
            return {
                restrict: 'A',
                link: function (scope, element) {
                    function slide() {
                        headbarService.slide();
                        contentService.slide();
                        sidebarService.slide();
                        footbarService.slide();
                    }

                    element.bind('click', function () {
                        scope.$apply(function () {
                            slide();
                        });
                    });

                    scope.$on('$destroy', function () {
                        element.unbind('click');
                    });
                }
            };
        }
    ]).
    service('lgHeadbarService', [function () {
        var isLogoShrink;
        return {
            slide: function () {
                isLogoShrink = !isLogoShrink;
            },
            isLogoShrink: function () {
                return isLogoShrink;
            }
        };
    }]).
    directive('lgHeadbar', ['lgRouterService', 'lgHeadbarService', function (rtService, hbService) {
        return {
            scope: {
                logo: '@',
                onLogout: '&'
            },
            restrict: 'E',
            replace: true,
            template: '<header class="lg-headbar">' +
            '<a class="logo logo-{{getState()}}"></a>' +
            '<nav class="navbar">' +
            '<a lg-slider><i class="fa fa-bars"></i></a>' +

            '<ul class="nav navbar-nav">' +
            '<li>' +
            '<a><i class="fa fa-user"></i>{{user.email}}</a>' +
            '<ul class="drop">' +
            '<li><a ng-click="onLogout({user: user})">退出登录</a></li>' +
            '</ul>' +
            '</li>' +

            '<li class="dropdown tasks-menu"  ng-app="webSocketApp" ng-controller="webSocketCtrl" ng-init="init()">' +
            '<a href="#" class="dropdown-toggle" data-toggle="dropdown" aria-expanded="false">' +
            '<i class="fa fa-flag-o"></i>' +
            '<span class="label label-danger">{{socketNewsArray.length}}</span>' +
            '</a>' +
            '<ul class="dropdown-menu" style="position: absolute;right: 0;left: auto;">' +
            '<li class="header">您有 {{socketNewsArray.length}} 条新消息</li>' +
            '<li>' +
            '<div class="slimScrollDiv" style="position: relative; overflow: hidden; width: auto; height: 200px;"><ul class="menu" style="overflow: hidden; width: 100%; height: 200px;">' +
            '<li ng-if="socketNewsArray != null && socketNewsArray.length>0" ng-repeat="socketNews in socketNewsArray">' +
            '<a href="#">' +
            '<h3>' +
            '{{socketNews.newsType}}:{{socketNews.newsdetail}}' +
            '<small class="pull-right">{{socketNews.createTime}}</small>' +
            '</h3>' +
            '</a>' +
            '</li><!-- end task item -->' +
            '</ul><div class="slimScrollBar" style="width: 3px; position: absolute; top: 11px; opacity: 0.4; display: none; border-radius: 7px; z-index: 99; right: 1px; height: 188.679px; background: rgb(0, 0, 0);"></div><div class="slimScrollRail" style="width: 3px; height: 100%; position: absolute; top: 0px; display: none; border-radius: 7px; opacity: 0.2; z-index: 90; right: 1px; background: rgb(51, 51, 51);"></div></div>' +
            '</li>' +
            '<li class="footer">' +
            '<a href="#">查看更多</a>' +
            '</li>' +
            '</ul>' +
            '</li>' +

            '</ul>' +
            '</nav>' +
            '</header>',
            link: function (scope, element) {
                scope.getState = function () {
                    var isLogoShrink = hbService.isLogoShrink();
                    return !angular.isUndefined(isLogoShrink) && (isLogoShrink ? 'shrink' : 'extend') || '';
                };

                hbService.headbarHeight = element[0].offsetHeight;

                scope.user = rtService.getUserInfo();
            }
        };
    }]).
    directive('lgContent', ['$window', 'lgContentService', 'lgHeadbarService', 'lgFootbarService', function ($window, contentService, headbarService, footbarService) {
        return {
            restrict: 'E',
            scope: true,
            transclude: true,
            replace: true,
            template: '<div class="lg-content lg-content-{{getState()}}" ng-style="{\'min-height\': getContentHeight()}">' +
            '<div ng-transclude></div>' +
            '<br class="clearfix"/>' +
            '</div>',
            link: function (scope) {
                scope.getContentHeight = function () {
                    var fbHeight = footbarService.footbarHeight || 0;
                    var hbHeight = headbarService.headbarHeight || 0;
                    var clientHeight = $window.document.documentElement.clientHeight || 0;
                    return (clientHeight - fbHeight - hbHeight) + 'px';
                };

                angular.element($window).bind('resize', function () {
                    scope.$apply();
                });

                scope.getState = function () {
                    var isContentExtend = contentService.isContentExtend();
                    return !angular.isUndefined(isContentExtend) && (isContentExtend ? 'extend' : 'shrink') || '';
                };
            }
        };
    }]).
    service('lgFootbarService', [function () {
        var isFootbarShrink;
        return {
            slide: function () {
                isFootbarShrink = !isFootbarShrink;
            },
            isFootbarShrink: function () {
                return isFootbarShrink;
            },
            footbarHeight: undefined
        };
    }]).
    directive('lgFootbar', ['lgFootbarService', function (fbService) {
        return {
            restrict: 'E',
            replace: true,
            template: '<footer class="lg-footbar lg-footbar-{{getState()}}">' +
            '<div>' +
            '<div class="pull-right">' +
            '<b>Version</b>&nbsp;0.0.1' +
            '</div>' +
            '<strong>Copyright&nbsp;<i class="fa fa-copyright"></i>&nbsp;2015.11' +
            '<a href="https://www.xgtravels.com">&nbsp;xgtravels.com</a>' +
            '</strong>' +
            '.&nbsp;All rights reserved' +
            '</div>' +
            '</footer>',
            link: function (scope, element) {
                scope.getState = function () {
                    var isShrink = fbService.isFootbarShrink();
                    return !angular.isUndefined(isShrink) && (isShrink ? 'shrink' : 'extend') || '';
                };

                fbService.footbarHeight = element[0].offsetHeight;
            }
        };
    }]);


angular.module('lg.modal', []).
    directive('lgModal', ['$timeout', function ($timeout) {
        return {
            scope: {
                open: '=',
                bgClickClose: '=',
                width: '@'
            },
            restrict: 'E',
            replace: true,
            transclude: true,
            template: '<div class="modal lg-modal lg-modal-{{state}}" ng-click="open = !bgClickClose;" ng-style="{display: !closed ? \'block\' : \'none\' }">' +
            '<div class="modal-dialog lg-modal-dialog" ng-style="{width: width}" ng-click="$event.stopPropagation()">' +
            '<div class="dialog-content">' +
            '<div ng-transclude></div>' +
            '<a class="close" ng-click="open = false;"><i class="fa fa-times-circle-o"></i></a>' +
            '</div>' +
            '</div>' +
            '</div>',
            link: function (scope) {
                scope.closed = true;

                scope.$watch('open', function (newVal) {
                    if (angular.isUndefined(newVal)) return;

                    if (newVal) {
                        scope.closed = false;
                        scope.state = 'in';
                    } else {
                        scope.state = 'out';
                        $timeout(function () {
                            scope.closed = true;
                        }, 500);
                    }
                });
            }
        };
    }]);


angular.module('lg.paginator', [])
    .directive('lgPaginator', [function () {
        return {
            scope: {
                totalPages: '=',
                visiblePages: '=',
                totalEntries: '=',
                current: '=',
                onChange: '&'
            },
            restrict: 'E',
            replace: true,
            template: '<div class="lg-paginator clearfix">' +
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
            link: function (scope) {
                scope.current = scope.current || 0;

                function adjust() {
                    var start = Math.ceil(scope.current - scope.rVisiblePages / 2);
                    if (start < 0) {
                        scope.currentPages = _.range(0, scope.rVisiblePages);
                    } else if (start + scope.rVisiblePages < scope.totalPages) {
                        scope.currentPages = _.range(start, start + scope.rVisiblePages);
                    } else {
                        scope.currentPages = _.range(scope.totalPages - scope.rVisiblePages, scope.totalPages);
                    }
                }

                function init() {
                    scope.rVisiblePages = scope.visiblePages;
                    if (scope.rVisiblePages > scope.totalPages) {
                        scope.rVisiblePages = scope.totalPages;
                    }
                    adjust();
                }

                scope.$watchCollection('[totalPages, visiblePages, totalEntries]', function (newVal) {
                    if (_.any(newVal, function (v) {
                            return angular.isUndefined(v);
                        })) return;
                    init();
                });

                scope.first = function () {
                    if (scope.current == 0) return;

                    scope.currentPages = _.range(0, scope.rVisiblePages);
                    scope.current = 0;
                    scope.onChange({page: scope.current});
                };

                scope.last = function () {
                    if (scope.current == scope.totalPages - 1) return;

                    scope.currentPages = _.range(scope.totalPages - scope.rVisiblePages, scope.totalPages);
                    scope.current = scope.totalPages - 1;
                    scope.onChange({page: scope.current});
                };

                scope.prev = function () {
                    if (scope.current <= 0) return;

                    scope.current -= 1;
                    if (scope.currentPages[0] != 0) {
                        scope.currentPages.pop();
                        scope.currentPages.unshift(scope.currentPages[0] - 1);
                    }
                    scope.onChange({page: scope.current});
                };

                scope.next = function () {
                    if (scope.current >= scope.totalPages - 1) return;

                    scope.current += 1;
                    if (scope.currentPages[scope.rVisiblePages - 1] != scope.totalPages - 1) {
                        scope.currentPages.push(scope.currentPages[scope.rVisiblePages - 1] + 1);
                        scope.currentPages.shift();
                    }
                    scope.onChange({page: scope.current});
                };

                scope.go = function (page) {
                    if (page < 0) return;
                    else if (page >= scope.totalPages) return;
                    else if (page == scope.current) return;

                    scope.current = page;
                    adjust();
                    scope.onChange({page: scope.current});
                };
            }
        };
    }]);

function lgGetUrlConfig(config) {
    var urlConfig = config.url;
    var navConfig = config.nav;
    var userInfo = config.user;
    var stateIdMap = {};
    var idUrlMap = {};
    var idNavMap = {};

    angular.module('lg.router', ['ui.router'])
        .config(['$stateProvider', '$urlRouterProvider', function ($stateProvider, $urlRouterProvider) {
            $urlRouterProvider.otherwise('');

            //处理路由参数
            _.forEach(urlConfig, function recurFn(cfg) {
                var parentCfg = cfg.parent;
                var state = parentCfg && parentCfg.state || '';
                state = state && (state + '.' + cfg.dir) || cfg.dir;
                cfg.state = state;
                cfg.abstract = !!cfg.children;

                $stateProvider.state(state, {
                    abstract: cfg.abstract,
                    url: '/' + cfg.dir + (cfg.params ? '?' + _.reduce(cfg.params, function (res, param) {
                        return res + '&' + param;
                    }) : ''),
                    templateUrl: cfg.state.replace(/\./g, '/') + '/' + cfg.html
                });

                stateIdMap[cfg.state] = cfg.id;
                idUrlMap[cfg.id] = cfg;

                if (cfg.children) {
                    _.forEach(cfg.children, function (child) {
                        child.parent = cfg;
                    });
                    _.forEach(cfg.children, recurFn);
                }
            });

            //处理导航参数
            _.forEach(navConfig, function recurFn(cfg, idx) {
                cfg.idx = idx;
                cfg.abstract = idUrlMap[cfg.id].abstract;
                idNavMap[cfg.id] = cfg;

                if (cfg.children) {
                    _.forEach(cfg.children, function (child) {
                        child.parent = cfg;
                    });
                    _.forEach(cfg.children, recurFn);
                }
            });
        }])
        .service('lgRouterService', ['$state', function ($state) {
            return {
                getNavPathOfState: function (state) {
                    var configs = [];
                    var cfg = idNavMap[stateIdMap[state]];
                    while (cfg) {
                        configs.unshift(cfg);
                        cfg = cfg.parent;
                    }
                    return configs;
                },
                getNavConfig: function () {
                    return navConfig;
                },
                transferById: function (id, params) {
                    var cfg = idUrlMap[id];
                    if (cfg.abstract) return;

                    $state.go(cfg.state, params);
                },
                getUserInfo: function () {
                    return userInfo;
                }
            };
        }]);
}


angular.module('lg.sidebar', ['ui.router', 'lg.router']).
    service('lgSidebarService', [function () {
        var isSidebarShrink;

        return {
            slide: function () {
                isSidebarShrink = !isSidebarShrink;
            },
            isSidebarShrink: function () {
                return isSidebarShrink;
            }
        };
    }]).
    directive('lgSidebar', ['lgSidebarService', function (sbService) {
        return {
            scope: true,
            restrict: 'E',
            replace: true,
            transclude: true,
            template: '<aside class="lg-sidebar lg-sidebar-{{getState()}}" ng-transclude></aside>',
            link: function (scope) {
                scope.getState = function () {
                    var isSidebarShrink = sbService.isSidebarShrink();
                    return !angular.isUndefined(isSidebarShrink) && (isSidebarShrink ? 'shrink' : 'extend') || '';
                }
            }
        };
    }]).
    directive('lgSidebarMenu', ['lgRouterService', function (rtService) {
        return {
            scope: {
                navData: '=',
                odd: '='
            },
            restrict: 'E',
            replace: true,
            template: '<ul class="lg-sidebar-menu" ng-class="{even: !odd, odd: odd}">' +
            '<li ng-repeat="item in navData" lg-sidebar-menu-item odd="!!odd" item-data="item" class="lg-sidebar-menu-item"></li>' +
            '</ul>',
            link: function (scope) {
                if (!scope.navData) {
                    scope.navData = rtService.getNavConfig();
                }
            }
        };
    }]).
    directive('lgSidebarMenuItem', ['$compile', 'lgRouterService', function ($compile, rtService) {
        return {
            scope: {
                itemData: '=',
                odd: '='
            },
            restrict: 'A',
            template: '<div ng-switch on="type" class=item-label-wrapper>' +
            '<div ng-switch-when="menu">' +
            '<a ng-click="onClick($event)" class="item-label" ng-disabled="itemData.abstract">' +
            '<i class="fa fa-{{itemData.icon}} left"></i>' +
            '<span class="middle">{{itemData.name}}</span>' +
            '<i class="fa fa-angle-right right"></i>' +
            '</a>' +
            '</div>' +
            '<a ng-switch-default="link" class="item-label" ng-disabled="itemData.abstract" ng-click="onClick($event)">' +
            '<i class="fa fa-{{itemData.icon}} left"></i>' +
            '<span class="middle">{{itemData.name}}</span>' +
            '</a>' +
            '</div>',
            link: function (scope, element, attrs) {
                scope.onClick = function (event) {
                    event.stopPropagation();
                    event.preventDefault();

                    rtService.transferById(scope.itemData.id, scope.itemData.params);
                };

                if (scope.itemData.children) {
                    scope.type = 'menu';
                    element.append($compile('<lg-sidebar-menu odd="!odd" nav-data="itemData.children"></lg-sidebar-menu>')(scope));

                    if (scope.itemData.idx >= scope.itemData.children.length) {
                        attrs.$addClass('adjust'); //调整子列表的对齐方式
                    }
                } else {
                    scope.type = 'link';
                }
            }
        };
    }]);

angular.module('lg.table', [])
    .service('lgTableService', [function () {
        function parseArrayCols(array, cols) {
            var nrow = 0;

            array.forEach(function (elem) {
                if (!angular.isArray(elem)) {
                    cols.push([elem]);
                    return;
                }

                var elemRows = [];
                var elemCols = [];
                var nElemCols = 0;
                elem.forEach(function (el) {
                    if (angular.isArray(el)) {
                        var elCols = parseArrayCols(el, []);
                        if (elCols.length > nElemCols) nElemCols = elCols.length;
                        elemRows.push(elCols);
                    } else {
                        if (nElemCols < 1) nElemCols = 1;
                        elemRows.push([[el]]);
                    }
                });
                elemRows.forEach(function (elRow) {
                    while (elRow.length < nElemCols) {
                        elRow.push(elRow[elRow.length - 1]);
                    }
                });

                while (--nElemCols >= 0) {
                    elemCols.unshift(elemRows.reduce(function (res, elRow) {
                        res = res.concat(elRow[nElemCols]);
                        return res;
                    }, []));
                }

                if (elemCols[0].length > nrow) nrow = elemCols[0].length;
                cols = cols.concat(elemCols);
            });

            cols.forEach(function (col) {
                while (col.length < nrow) {
                    col.push(col[col.length - 1]);
                }
            });

            return cols;
        }

        function mergeArrayRows(cols) {
            var spans = [];
            cols.forEach(function (col) {
                spans.push(col.reduce(function (res, el, r) {
                    if (r == 0) res.push(1);
                    else if (el == col[r - 1]) {
                        var cr = r;
                        while (col[--cr] == el);
                        res.push(0);
                        res[cr + 1] += 1;
                    } else {
                        res.push(1);
                    }

                    return res;
                }, []));
            });

            return spans;
        }

        function mergeArrayCols(rows) {
            var spans = [];
            rows.forEach(function (row) {
                spans.push(row.reduce(function (res, el, c) {
                    if (c == 0) res.push(1);
                    else if (el == row[c - 1]) {
                        var cc = c;
                        while (row[--cc] == el);
                        res.push(0);
                        res[cc + 1] += 1;
                    } else {
                        res.push(1);
                    }

                    return res;
                }, []));
            });

            return spans;
        }

        function getArrayRows(cols) {
            var rows = [];
            cols.forEach(function (col) {
                col.forEach(function (el, r) {
                    rows[r] = rows[r] || [];
                    rows[r].push(el);
                });
            });

            return rows;
        }

        return {
            setPartWithArray: function (tableData, part, array) {
                var cols = parseArrayCols(array, []);
                tableData['$$_t' + part] = getArrayRows(cols);
                tableData['$$_' + part + 'Rowspan'] = mergeArrayRows(cols);
                tableData['$$_' + part + 'Colspan'] = mergeArrayCols(tableData['$$_t' + part]);
            }
        };
    }])
    .service('lgMtableService', ['lgTableService', function (tblService) {
        function fixObjectFields(obj, segs) {
            var k = segs[0];
            var tk = segs.slice(1);
            var m = k.match(/^(\w+)\[]$/);
            if (m) {
                obj[m[1]] = obj[m[1]] || [];
                if (!angular.isArray(obj[m[1]])) {
                    throw '对象成员: ' + m[1] + '不是一个数组';
                }

                if (segs.length > 1) {
                    if (obj[m[1]].length == 0) {
                        obj[m[1]].push({});
                    }
                    obj[m[1]].forEach(function (mo) {
                        fixObjectFields(mo, tk);
                    });
                }
            } else {
                if (segs.length > 1) {
                    obj[k] = obj[k] || {};
                    if (!angular.isObject(obj[k])) {
                        throw '对象成员: ' + k + '不是一个对象';
                    }

                    fixObjectFields(obj[k], tk);
                }
            }
        }

        function fixObject(obj, keys) {
            keys.forEach(function (key) {
                fixObjectFields(obj, key.split('.'));
            });
        }

        function siftFields(row, keys, sift) {
            var hk = keys[0];
            var m = hk.match(/^(\w+)\[]$/);
            var tk = keys.slice(1);

            if (m) {
                hk = m[1];
                if (!angular.isArray(row[hk])) {
                    throw '对象成员: ' + hk + '不是数组';
                }
            }
            if (keys.length > 1 && !angular.isObject(row[hk])) {
                throw '对象成员: ' + hk + '不是对象';
            }

            if (angular.isArray(row[hk])) {
                sift[hk] = sift[hk] || [];

                row[hk].forEach(function (elem, i) {
                    if (angular.isArray(elem)) {
                        throw '不支持数组元素为数组的情形';
                    }

                    if (angular.isObject(elem)) {
                        sift[hk][i] = sift[hk][i] || {};
                        siftFields(elem, tk, sift[hk][i]);
                    } else {
                        sift[hk][i] = 1;
                    }
                });
            } else if (angular.isObject(row[hk])) {
                sift[hk] = sift[hk] || {};
                siftFields(row[hk], tk, sift[hk]);
            } else {
                sift[hk] = 1;
            }

            return sift;
        }

        function siftRow(row, colKeys) {
            var sift = {};
            colKeys.forEach(function (ck) {
                siftFields(row, ck.split('.'), sift);
            });

            return sift;
        }

        function flattenRow(row, key) {
            var rows = [];
            if (angular.isArray(row)) {
                rows = row.reduce(function (res, elem, i) {
                    if (angular.isArray(elem)) {
                        throw '不支持数组元素也为数组的情形';
                    }
                    res = res.concat(flattenRow(elem, '[' + i + ']').map(function (t) {
                        return t.map(function (tk) {
                            return key && (key + tk) || tk;
                        });
                    }));
                    return res;
                }, []);
            } else if (angular.isObject(row)) {
                var keyCols = [];
                var rowNum = 0;
                angular.forEach(row, function (val, k) {
                    var flatten = flattenRow(val, k).map(function (t) {
                        return t.map(function (tk) {
                            return key && (key + '.' + tk) || tk;
                        });
                    });
                    keyCols.push(flatten);

                    if (rowNum < flatten.length) {
                        rowNum = flatten.length;
                    }
                });
                keyCols.forEach(function (col) {
                    while (col.length < rowNum) {
                        col.push(col[col.length - 1]);
                    }
                });
                while (--rowNum >= 0) {
                    rows.unshift(keyCols.reduce(function (res, col) {
                        return res.concat(col[rowNum]);
                    }, []));
                }
            } else {
                rows.push([key]);
            }
            return rows;
        }

        function parseObjectRows(objs, keys) {
            objs.forEach(function (obj) {
                fixObject(obj, keys);

                obj.$$_keyRows = flattenRow(siftRow(obj, keys));
                obj.$$_keyMap = obj.$$_keyRows[0].reduce(function (res, key, i) {
                    key = key.replace(/(\w+)\[\d+]/g, function (s0, s1) {
                        return s1 + '[]';
                    });
                    res[key] = i;

                    return res;
                }, {});
                obj.$$_rowspan = mergeObjectRows(obj.$$_keyRows);
            });
        }

        function mergeObjectRows(keyRows) {
            return keyRows.reduce(function (res, kr, r) {
                if (r == 0) {
                    res.push(kr.map(function () {
                        return 1;
                    }));
                } else {
                    res.push(kr.reduce(function (rres, k, c) {
                        if (k == keyRows[r - 1][c]) {
                            var sr = r;
                            while (res[--sr][c] == 0);
                            res[sr][c] += 1;

                            rres.push(0);
                        } else {
                            rres.push(1);
                        }

                        return rres;
                    }, []));
                }

                return res;
            }, []);
        }

        return {
            /**
             @doc lgMtableService {
                "name": "setHeadWithArray(tableData, array)",
                "desc": "用数组数据设置表头",
                "params": {
                    "tableData": "传入lgDataTable标签的对象",
                    "array": "代表表头的列数据数组."
                }
            }@end
             */
            setHeadWithArray: function (tableData, array) {
                tblService.setPartWithArray(tableData, 'head', array);
            },

            /**
             @doc lgMtableService {
                "name": "setBodyWithObjects(tableData, objects, colKeys)",
                "desc": "用对象数组设置表体",
                "params": {
                    "tableData": "传入lgDataTable标签的对象",
                    "objects": "数组中每一项都是一个JS对象, 代表表头中一行的数据",
                    "colKeys": "表体每行的单元格内容与该数组中的键名或索引一一对应,行对象或行数组根据它们取出对应的键值"
                }
            }@end
             */
            setBodyWithObjects: function (tableData, objects, colKeys) {
                if (colKeys) {
                    objects.forEach(function (obj) {
                        obj.$$_colKeys = colKeys;
                    });
                }

                tableData.$$_tbody = objects || [];
                parseObjectRows(tableData.$$_tbody, colKeys);
            },

            /**
             @doc lgMtableService {
                "name": "setFootWithArray(tableData, array)",
                "desc": "用数组数据设置表尾",
                "params": {
                    "tableData": "传入lgDataTable标签的对象",
                    "array": "代表表尾的列数据数组."
                }
            }@end
             */
            setFootWithArray: function (tableData, array) {
                tblService.setPartWithArray(tableData, 'foot', array);
            }
        };
    }])
    .directive('lgMtable', [function () {
        return {
            scope: {
                tableData: '='
            },
            restrict: 'E',
            replace: true,
            template: '<table class="lg-mtable table table-bordered">' +
            '<thead>' +
            '<tr ng-repeat="($rnum, $row) in tableData.$$_thead track by $index">' +
            '<th ng-if="tableData.$$_headRowspan[$cnum][$rnum] && tableData.$$_headColspan[$rnum][$cnum]" ' +
            'rowspan="{{tableData.$$_headRowspan[$cnum][$rnum]}}" ' +
            'colspan="{{tableData.$$_headColspan[$rnum][$cnum]}}" ' +
            'ng-repeat="($cnum, $col) in $row track by $index">' +
            '<span lg-html-code="{{$col}}"></span>' +
            '</th>' +
            '</tr>' +
            '</thead>' +
            '<col ng-repeat="$$_key in tableData.$$_bodyColKeys track by $index" class="col{{$index}}"/>' +
            '<tbody ng-repeat="$object in tableData.$$_tbody track by $index">' +
            '<tr ng-repeat="($rnum, $keyrow) in $object.$$_keyRows track by $index">' +
            '<td ng-if="$object.$$_rowspan[$rnum][$cnum]" ' +
            'rowspan="{{$object.$$_rowspan[$rnum][$cnum]}}" ' +
            'ng-repeat="($cnum, $$_key) in $object.$$_colKeys track by $index">' +
            '<span lg-html-code="{{splitKey($object, $keyrow[$object.$$_keyMap[$$_key]])}}"></span>' +
            '</td>' +
            '</tr>' +
            '</tbody>' +
            '<tfoot>' +
            '<tr ng-repeat="($rnum, $row) in tableData.$$_tfoot track by $index">' +
            '<th ng-if="tableData.$$_footRowspan[$cnum][$rnum] && tableData.$$_footColspan[$rnum][$cnum]" ' +
            'rowspan="{{tableData.$$_footRowspan[$cnum][$rnum]}}" ' +
            'colspan="{{tableData.$$_footColspan[$rnum][$cnum]}}" ' +
            'ng-repeat="($cnum, $col) in $row track by $index">' +
            '<span lg-html-code="{{$col}}"></span>' +
            '</th>' +
            '</tr>' +
            '</tfoot>' +
            '</table>',
            link: function (scope) {
                scope.$watch('tableData', function (newVal) {
                    if (angular.isUndefined(newVal)) return;
                    scope.$table = newVal;
                });

                scope.splitKey = function (obj, key) {
                    if (!key) return;

                    var ks = key.split('.');
                    return ks.reduce(function (r, k) {
                        var m = k.match(/^(\w+)\[(\d+)]$/);
                        if (m) {
                            return r[m[1]] && r[m[1]][m[2]];
                        } else {
                            return r && r[k];
                        }
                    }, obj);
                };
            }
        };
    }])
    .service('lgStableService', ['lgTableService', function (tblService) {
        return {
            /**
             @doc lgStableService {
                "name": "setHeadWithArray(tableData, array)",
                "desc": "用数组数据设置表头",
                "params": {
                    "tableData": "传入lgDataTable标签的对象",
                    "array": "代表表头的列数据数组."
                }
            }@end
             */
            setHeadWithArray: function (tableData, array) {
                tblService.setPartWithArray(tableData, 'head', array);
            },

            /**
             @doc lgStableService {
                "name": "setBodyWithObjects(tableData, objects, colKeys)",
                "desc": "用对象数组设置表体",
                "params": {
                    "tableData": "传入lgDataTable标签的对象",
                    "objects": "数组中每一项都是一个JS对象, 代表表头中一行的数据",
                    "colKeys": "表体每行的单元格内容与该数组中的键名或索引一一对应,行对象或行数组根据它们取出对应的键值"
                }
            }@end
             */
            setBodyWithObjects: function (tableData, objects, colKeys) {
                if (colKeys) {
                    tableData.$$_bodyColKeys = colKeys;
                }
                tableData.$$_tbody = objects;
            },

            /**
             @doc lgStableService {
                "name": "setFootWithArray(tableData, array)",
                "desc": "用数组数据设置表尾",
                "params": {
                    "tableData": "传入lgDataTable标签的对象",
                    "array": "代表表尾的列数据数组."
                }
            }@end
             */
            setFootWithArray: function (tableData, array) {
                tblService.setPartWithArray(tableData, 'foot', array);
            }
        };
    }])
    .directive('lgStable', [function () {
        return {
            scope: {
                tableData: '='
            },
            restrict: 'E',
            replace: true,
            template: '<table class="lg-stable table table-bordered">' +
            '<thead>' +
            '<tr ng-repeat="($rnum, $row) in tableData.$$_thead track by $index">' +
            '<th ng-if="tableData.$$_headRowspan[$cnum][$rnum] && tableData.$$_headColspan[$rnum][$cnum]" ' +
            'rowspan="{{tableData.$$_headRowspan[$cnum][$rnum]}}" ' +
            'colspan="{{tableData.$$_headColspan[$rnum][$cnum]}}" ' +
            'ng-repeat="($cnum, $col) in $row track by $index">' +
            '<span lg-html-code="{{$col}}"></span>' +
            '</th>' +
            '</tr>' +
            '</thead>' +
            '<col ng-repeat="$$_key in tableData.$$_bodyKeys track by $index"/>' +
            '<tbody>' +
            '<tr ng-repeat="$row in tableData.$$_tbody track by $index">' +
            '<td ng-repeat="($col, $$_key) in tableData.$$_bodyColKeys track by $index">' +
            '<span lg-html-code="{{splitKey($row, $$_key)}}"></span>' +
            '</td>' +
            '</tr>' +
            '</tbody>' +
            '<tfoot>' +
            '<tr ng-repeat="($rnum, $row) in tableData.$$_tfoot track by $index">' +
            '<th ng-if="tableData.$$_footRowspan[$cnum][$rnum] && tableData.$$_footColspan[$rnum][$cnum]" ' +
            'rowspan="{{tableData.$$_footRowspan[$cnum][$rnum]}}" ' +
            'colspan="{{tableData.$$_footColspan[$rnum][$cnum]}}" ' +
            'ng-repeat="($cnum, $col) in $row track by $index">' +
            '<span lg-html-code="{{$col}}"></span>' +
            '</th>' +
            '</tr>' +
            '</tfoot>' +
            '</table>',
            link: function (scope) {
                scope.$watch('tableData', function (newVal) {
                    if (angular.isUndefined(newVal)) return;
                    scope.$table = newVal;
                });

                scope.splitKey = function (row, key) {
                    var ks = key.split('.');
                    return ks.reduce(function (r, k) {
                        return r && r[k];
                    }, row);
                };
            }
        };
    }])
    .service('lgTtableService', ['lgTableService', function (tblService) {
        function setBodyWithObjects(tableData, objects, colKeys) {
            tableData.children = objects;
            if (colKeys) {
                tableData.$$_titleKey = colKeys[0];
                tableData.$$_bodyKeys = colKeys.slice(1);
            }

            tableData.$$_tbody = [];
            tableData.$$_root = tableData;
            tableData.$$_parent = tableData;
            tableData.$$_level = 0;
            tableData.$$_expand = true;

            function init(node) {
                _.forEach(node.children, function (child) {
                    child.$$_parent = node;
                    child.$$_root = tableData;
                    child.$$_level = node.$$_level + 1;
                    tableData.$$_tbody.push(child);

                    init(child);
                });
            }

            init(tableData);
        }

        function iterateNode(node, cb) {
            (node !== node.$$_root) && cb && cb(node);
            _.forEach(node.children, function (child) {
                iterateNode(child, cb);
            });
        }

        function appendNodes(parent, children) {
            var prevRow = parent;
            while (prevRow.children && prevRow.children.length) {
                prevRow = prevRow.children[prevRow.children.length - 1];
            }

            var tableData = parent.$$_root;
            var tbody = tableData.$$_tbody;
            var tails = tbody.splice(tbody.indexOf(prevRow) + 1);
            var childRows = [];

            var oldChildren = parent.children || [];
            parent.children = children;

            function initNode(node) {
                _.forEach(node.children, function (child) {
                    child.$$_parent = node;
                    child.$$_root = node.$$_root;
                    child.$$_level = node.$$_level + 1;
                    childRows.push(child);

                    initNode(child);
                });
            }

            initNode(parent);

            parent.children = oldChildren.concat(parent.children);
            tableData.$$_tbody = tbody.concat(childRows).concat(tails);
        }

        function deleteNode(node) {
            var parentNode = node.$$_parent;
            var parentIdx = parentNode.children.indexOf(node);
            parentNode.children.splice(parentIdx, 1);

            var table = node.$$_root;
            var firstRowIdx = table.$$_tbody.indexOf(node);
            var lastNode = node;
            while (lastNode.children) {
                lastNode = lastNode.children[lastNode.children.length - 1];
            }
            var lastRowIdx = table.$$_tbody.indexOf(lastNode);
            table.$$_tbody.splice(firstRowIdx, lastRowIdx - firstRowIdx + 1);
        }

        return {
            /**
             @doc lgTtableService {
                "name": "setHeadWithArray(tableData, array)",
                "desc": "用矩阵(数组的数组)设置表头",
                "params": {
                    "tableData": "通过html标签传入lgTreeTable的对象",
                    "array": "数组中每个成员都是一个数组,代表表头/表尾"
                }
             }@end
             */
            setHeadWithArray: function (tableData, array) {
                tblService.setPartWithArray(tableData, 'head', array);
            },

            /**
             @doc lgTtableService {
                "name": "setFootWithArray(tableData, array)",
                "desc": "用矩阵(数组的数组)设置表尾",
                "params": {
                    "tableData": "通过html标签传入lgTreeTable的对象",
                    "array": "数组中每个成员都是一个数组,代表表头/表尾"
                }
             }@end
             */
            setFootWithArray: function (tableData, array) {
                tblService.setPartWithArray(tableData, 'foot', array);
            },

            /**
             @doc lgTtableService {
                "name": "setBodyWithObjects(tableData, objects)",
                "desc": "设置表体数据",
                "params": {
                    "tableData": "通过html标签传给lgTreeTable的对象",
                    "objects": "对象数组,每个元素代表每一行的数据。
                                如果是设置表体数据,则需要注意以下两点:
                                第一,如果与某行对应的行对象是row,则它的子行通过row.children数组指定。
                                第二,如果某行没有子行,则通过row.leaf=true来指定。"
                }
            }@end
             */
            setBodyWithObjects: function (tableData, objects, colKeys) {
                setBodyWithObjects(tableData, objects, colKeys);
            },

            /**
             @doc lgTtableService {
                "name": "appendNodes(parent, children)",
                "desc": "向某节点添加子节点",
                "params": {
                    "parent": "目标节点",
                    "children": "一个对象数组,将要成为parent的子节点"
                }
             }@end
             */
            appendNodes: appendNodes,
            /**
             @doc lgTtableService {
                "name": "deleteNode(row)",
                "desc": "删除某一行及其所有子行",
                "params": {
                    "row": "目标行对象"
                }
             }@end
             */
            deleteNode: deleteNode,

            /**
             @doc lgTtableService {
                "name": "iterateNode(node, cb)",
                "desc": "遍历树表中某个子树,针对每一个子节点调用回调函数",
                "params": {
                    "node": "某层自节点",
                    "cb": "回调函数,原型为function(node) {}."
                }
             }@end
             */
            iterateNode: iterateNode,
            /**
             @doc lgTtableService {
                "name": "iterateBody(tableData, cb)",
                "desc": "遍历表体,针对每一行调用回调函数",
                "params": {
                    "tableData": "通过html标签传入lgTreeTable的对象",
                    "cb": "回调函数,原型为function(row) {}."
                }
             }@end
             */
            iterateBody: function (tableData, cb) {
                iterateNode(tableData, cb);
            }
        };
    }])
    .directive('lgTtable', [function () {
        return {
            scope: {
                /**
                 @doc lgTtable {
                    "name": "tableData",
                    "desc": "传入lgDataTable标签的对象",
                    "params": {
                        "children": "子行数组,每个元素代表一个子节点,其内部成员和tableData类似",
                        "leaf": "是否为叶节点",
                        "toggle": "回调函数,见下面的toggle项"
                    }
                 }@end
                 */
                tableData: '='
            },
            restrict: 'E',
            replace: true,
            template: '<table class="lg-ttable table table-bordered">' +
            '<thead>' +
            '<tr ng-repeat="($rnum, $row) in tableData.$$_thead track by $index">' +
            '<th ng-if="tableData.$$_headRowspan[$cnum][$rnum] && tableData.$$_headColspan[$rnum][$cnum]" ' +
            'rowspan="{{tableData.$$_headRowspan[$cnum][$rnum]}}" ' +
            'colspan="{{tableData.$$_headColspan[$rnum][$cnum]}}" ' +
            'ng-repeat="($cnum, $col) in $row track by $index">' +
            '<span lg-html-code="{{$col}}"></span>' +
            '</th>' +
            '</tr>' +
            '</thead>' +
            '<tbody>' +
            '<tr ng-repeat="$row in tableData.$$_tbody track by $index" ng-if="isExpanded($row)">' +
            '<td class="title" ng-style="{\'padding-left\': ($row.$$_level * 18 - 10) + \'px\'}">' +
            '<i ng-if="!$row.leaf" class="fa {{getIcon($row)}}" style="width: 18px; cursor: pointer;" ng-click="toggle($row)"></i>' +
            '<span lg-html-code="{{$row[tableData.$$_titleKey]}}"></span>' +
            '</td>' +
            '<td ng-repeat="$$_key in tableData.$$_bodyKeys track by $index">' +
            '<span lg-html-code="{{$row[$$_key]}}"></span>' +
            '</td>' +
            '</tr>' +
            '</tbody>' +
            '<tfoot>' +
            '<tr ng-repeat="($rnum, $row) in tableData.$$_tfoot track by $index">' +
            '<th ng-if="tableData.$$_footRowspan[$cnum][$rnum] && tableData.$$_footColspan[$rnum][$cnum]" ' +
            'rowspan="{{tableData.$$_footRowspan[$cnum][$rnum]}}" ' +
            'colspan="{{tableData.$$_footColspan[$rnum][$cnum]}}" ' +
            'ng-repeat="($cnum, $col) in $row track by $index">' +
            '<span lg-html-code="{{$col}}"></span>' +
            '</th>' +
            '</tr>' +
            '</tfoot>' +
            '</table>',
            link: function (scope) {
                scope.$watch('tableData', function (newVal) {
                    if (angular.isUndefined(newVal)) return;
                    scope.$table = newVal;
                });
                scope.getIcon = function (row) {
                    if (row.leaf) return '';
                    else if (!row.$$_expand) return 'fa-plus-square-o';
                    else if (!row.children || !row.children.length) return 'fa-spin fa-spinner';
                    else return 'fa-minus-square-o';
                };

                scope.isExpanded = function (row) {
                    var parent = row.$$_parent;
                    while (parent !== row.$$_root) {
                        if (!parent.$$_expand) return false;
                        parent = parent.$$_parent;
                    }
                    return true;
                };

                scope.toggle = function (row) {
                    row.$$_expand = !row.$$_expand;
                    /**
                     @doc lgTtable {
                        "name": "toggle(expand, row)",
                        "desc": "展开某行的子行时被调用",
                        "params": {
                            "expand": "若为true表示是展开,否则为收起",
                            "row": "目标行"
                        }
                     }@end
                     */
                    scope.tableData.toggle && scope.tableData.toggle(row.$$_expand, row);
                };
            }
        };
    }]);


angular.module('lg.time', [])
    .directive('lgDate', [function () {
        return {
            scope: {
                day: '='
            },
            restrict: 'E',
            replace: true,
            template: '<div class="lg-date" ng-click="clicked=true">' +
            '<table class="table-condensed">' +
            '<thead>' +
            '<tr>' +
            '<th><span ng-click="goPrev()"><i class="fa fa-arrow-left"></i></span></th>' +
            '<th colspan="5">' +
            '<span>' +
            '{{year}}&nbsp;年&nbsp;<i class="fa fa-caret-down"></i>' +
            '<select ng-model="year" ng-options="y for y in years"></select>' +
            '</span>' +
            '&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;' +
            '<span>' +
            '{{month}}&nbsp;月&nbsp;<i class="fa fa-caret-down"></i>' +
            '<select ng-model="month" ng-options="m for m in monthes"></select>' +
            '</span>' +
            '<th><span ng-click="goNext()"><i class="fa fa-arrow-right"></i></span></th>' +
            '</tr>' +
            '<tr>' +
            '<th>日</th><th>一</th><th>二</th><th>三</th><th>四</th><th>五</th><th>六</th>' +
            '</tr>' +
            '</thead>' +
            '<tbody>' +
            '<tr ng-repeat="week in monthWeeks">' +
            '<td ng-repeat="day in week" ng-class="{off: day.off, active: isActive(day)}" ng-click="choose(day)">{{day.date}}</td>' +
            '</tr>' +
            '</tbody>' +
            '</table>' +
            '</div>',
            link: function (scope) {
                function getMonthWeeks(year, month) {
                    var mm = moment([year, month, 1]);

                    var endOfPrevMonthMm, endOfPrevMonth;
                    var startOfMonth, endOfMonth;
                    var startOfNextMonthMm, startOfNextMonth;
                    var monthDays, curWeekday, curDate;

                    endOfPrevMonthMm = moment([year, month, 1]).subtract(1, 'months').endOf('month');
                    endOfPrevMonth = {
                        year: endOfPrevMonthMm.year(),
                        month: endOfPrevMonthMm.month(),
                        date: endOfPrevMonthMm.date(),
                        weekday: endOfPrevMonthMm.day()
                    };
                    startOfNextMonthMm = moment([year, month, 1]).add(1, 'months').startOf('month');
                    startOfNextMonth = {
                        year: startOfNextMonthMm.year(),
                        month: startOfNextMonthMm.month(),
                        date: startOfNextMonthMm.date(),
                        weekday: startOfNextMonthMm.day()
                    };

                    mm.startOf('month');
                    startOfMonth = {date: mm.date(), weekday: mm.day()};

                    mm.endOf('month');
                    endOfMonth = {date: mm.date(), weekday: mm.day()};

                    monthDays = [];
                    curWeekday = endOfPrevMonth.weekday;
                    if (curWeekday !== 6) {
                        curDate = endOfPrevMonth.date;
                        monthDays.push({
                            date: endOfPrevMonth.date,
                            off: true,
                            year: endOfPrevMonth.year,
                            month: endOfPrevMonth.month
                        });

                        while (curWeekday > 0) {
                            curWeekday -= 1;
                            curDate -= 1;
                            monthDays.unshift({
                                date: curDate,
                                off: true,
                                year: endOfPrevMonth.year,
                                month: endOfPrevMonth.month
                            });
                        }
                    }

                    curDate = startOfMonth.date;
                    while (curDate <= endOfMonth.date) {
                        monthDays.push({date: curDate, month: month, year: year});
                        curDate += 1;
                    }

                    curDate = startOfNextMonth.date;
                    while (monthDays.length < 6 * 7) {
                        monthDays.push({
                            date: curDate,
                            off: true,
                            year: startOfNextMonth.year,
                            month: startOfNextMonth.month
                        });
                        curDate += 1;
                    }

                    var monthWeeks = [];
                    while (monthDays.length) {
                        monthWeeks.push(monthDays.splice(0, 7));
                    }

                    return monthWeeks;
                }


                scope.isActive = function (day) {
                    return (scope.day.date === day.date) && (scope.day.month === day.month) && (scope.day.year === day.year);
                };

                var today = moment();

                scope.monthes = _.range(1, 13);
                scope.years = _.range(today.year() - 5, today.year() + 5);

                scope.$watch('day', function (newVal) {
                    if (_.isUndefined(newVal)) return;

                    scope.clicked = false;
                    scope.year = scope.day.year || today.year();
                    scope.month = (scope.day.month || today.month()) + 1;
                    scope.date = scope.day.date || today.date();
                    scope.monthWeeks = getMonthWeeks(scope.year, scope.month - 1);
                });

                scope.$watchCollection('[clicked, year, month]', function (newVal) {
                    if (!newVal[0]
                        || _.isUndefined(newVal[1])
                        || _.isUndefined(newVal[2])) return;

                    scope.day.year = scope.year;
                    scope.day.month = scope.month - 1;
                    scope.day.date = scope.date;
                    scope.monthWeeks = getMonthWeeks(scope.year, scope.month - 1);
                });

                scope.goPrev = function () {
                    scope.year = (scope.month === 1) ? (scope.year - 1) : scope.year;
                    scope.month = (scope.month === 1) ? 12 : (scope.month - 1);

                    scope.monthWeeks = getMonthWeeks(scope.year, scope.month - 1);
                };

                scope.goNext = function () {
                    scope.year = (scope.month === 12) ? (scope.year + 1) : scope.year;
                    scope.month = (scope.month === 12) ? 1 : (scope.month + 1);

                    scope.monthWeeks = getMonthWeeks(scope.year, scope.month - 1);
                };

                scope.choose = function (val) {
                    if (val.off) return;
                    scope.date = val.date;

                    scope.day.date = scope.date;
                    scope.day.month = scope.month - 1;
                    scope.day.year = scope.year;
                };
            }
        };
    }])
    .directive('lgDatePicker', [function () {
        return {
            scope: {
                day: '=',
                flip: '=',
                clear: '=',
                width: '@'
            },
            restrict: 'E',
            replace: 'true',
            template: '<div class="lg-date-picker" ng-style="{width: width}">' +
            '<input readonly class="form-control" type="text" value="{{day}}"/>' +
            '<lg-date ng-class="{\'flip-y\': flip.y, \'flip-x\': flip.x}" day="innerDay"></lg-date>' +
            '<i ng-if="clear" class="clear fa fa-times" ng-click="clearDate()"></i>' +
            '</div>',
            link: function (scope) {
                var dt = scope.day && moment(scope.day, 'YYYY-MM-DD');
                scope.innerDay = scope.day && {year: dt.year(), month: dt.month(), date: dt.date()} || {};
                scope.$watch('innerDay', function (newVal) {
                    if (_.isUndefined(newVal)
                        || _.isUndefined(newVal.year)) return;

                    scope.day = moment([scope.innerDay.year, scope.innerDay.month, scope.innerDay.date]).format('YYYY-MM-DD');
                }, true);

                //scope.$watch('day', function(newVal) {
                //    if(angular.isUndefined(newVal)) return;
                //
                //    var dt = scope.day && moment(scope.day, 'YYYY-MM-DD');
                //    scope.innerDay = scope.day && {year: dt.year(), month: dt.month(), date: dt.date()} || {};
                //});

                scope.clearDate = function () {
                    scope.day = '';
                    scope.innerDay = {};
                };
            }
        };
    }])
    .directive('lgDateRanger', [function () {
        return {
            scope: {
                start: '=',
                end: '=',
                flip: '=',
                clear: '=',
                width: '@'
            },
            restrict: 'E',
            replace: true,
            template: '<div class="lg-date-ranger clearfix">' +
            '<lg-date-picker clear="clear" width="{{width}}" class="pull-left" flip="flip.start" day="start"></lg-date-picker>' +
            '<div class="pull-left to">&nbsp;至&nbsp;</div>' +
            '<lg-date-picker clear="clear" width="{{width}}" class="pull-left" flip="flip.end" day="end"></lg-date-picker>' +
            '</div>'
        };
    }])
    .directive('lgDateTime', [function () {
        return {
            scope: {
                day: '=',
                time: '=',
                hasSec: '=',
                minStep: '=',
                secStep: '='
            },
            restrict: 'E',
            replace: true,
            template: '<div class="lg-date-time" ng-click="clicked=true">' +
            '<lg-date day="day"></lg-date>' +
            '<div class="time"><ul class="clearfix" ng-class="{\'has-sec\': hasSec}">' +
            '<li><i class="fa fa-clock-o"></i></li>' +
            '<li>' +
            '<span>{{hour > 9 ? hour : \'0\' + hour}}' +
            '<i class="fa fa-caret-down"></i>' +
            '<select ng-model="hour" ng-options="h for h in hours"></select>' +
            '</span>' +
            '</li>' +
            '<li>' +
            '&nbsp;:&nbsp;&nbsp;' +
            '<span>{{min > 9 ? min : \'0\' + min}}' +
            '<i class="fa fa-caret-down"></i>' +
            '<select ng-model="min" ng-options="m for m in minutes"></select>' +
            '</span>' +
            '</li>' +
            '<li ng-show="hasSec">' +
            '&nbsp;:&nbsp;&nbsp;' +
            '<span>{{sec > 9 ? sec : \'0\' + sec}}' +
            '<i class="fa fa-caret-down"></i>' +
            '<select ng-model="sec" ng-options="s for s in seconds"></select>' +
            '</span>' +
            '</li>' +
            '</ul></div>' +
            '</div>',
            link: function (scope) {
                scope.hours = _.range(0, 24);
                scope.minutes = _.range(0, 60, scope.minStep || 1);
                scope.seconds = _.range(0, 60, scope.secStep || 1);

                scope.$watch('time', function (newVal) {
                    if (_.isUndefined(newVal)) return;

                    scope.clicked = false;
                    scope.hour = scope.time && scope.time.hour || 0;
                    scope.min = scope.time && scope.time.min || 0;
                    scope.sec = scope.time && scope.time.sec || 0;
                });

                scope.$watchCollection('[clicked, hour, min, sec]', function (newVal) {
                    if (!newVal[0]
                        || _.isUndefined(newVal[1])
                        || _.isUndefined(newVal[2])
                        || _.isUndefined(newVal[3])) return;

                    scope.time.hour = scope.hour;
                    scope.time.min = scope.min;
                    scope.time.sec = scope.sec;
                });
            }
        };
    }])
    .directive('lgDateTimePicker', [function () {
        return {
            scope: {
                day: '=',
                time: '=',
                hasSec: '=',
                minStep: '=',
                secStep: '=',
                flip: '=',
                clear: '=',
                width: '@'
            },
            restrict: 'E',
            replace: true,
            template: '<div class="lg-date-time-picker" ng-style="{width: width}">' +
            '<input readonly class="form-control" type="text" value="{{day}}&nbsp;&nbsp;{{time}}"/>' +
            '<lg-date-time ng-class="{\'flip-y\': flip.y, \'flip-x\': flip.x}" min-step="minStep" sec-step="secStep" has-sec="hasSec" day="innerDay" time="innerTime"></lg-date-time>' +
            '<i ng-if="clear" class="clear fa fa-times" ng-click="clearDateTime()"></i>' +
            '</div>',
            link: function (scope) {
                var dt = scope.day && moment(scope.day) || moment();
                scope.innerDay = scope.day && {year: dt.year(), month: dt.month(), date: dt.date()} || {};
                scope.$watch('innerDay', function (newVal) {
                    if (_.isUndefined(newVal)
                        || _.isUndefined(newVal.year)) return;

                    scope.day = moment([scope.innerDay.year, scope.innerDay.month, scope.innerDay.date]).format('YYYY-MM-DD');
                }, true);

                function formatTime(time) {
                    var tm = [
                        time.hour > 9 ? time.hour : '0' + time.hour,
                        time.min > 9 ? time.min : '0' + time.min
                    ];
                    if (scope.hasSec) {
                        tm.push(time.sec > 9 ? time.sec : '0' + time.sec);
                    }
                    return tm.join(':');
                }

                var tm = scope.time && scope.time.split(':') || [];
                scope.innerTime = scope.time && {hour: parseInt(tm[0] || 0), min: parseInt(tm[1] || 0)} || {};
                if (scope.time && scope.hasSec) {
                    scope.innerTime.sec = parseInt(tm[2] || 0);
                }

                scope.$watch('innerTime', function (newVal) {
                    if (!_.isUndefined(newVal) && !_.isUndefined(newVal.hour)) {
                        scope.time = formatTime(scope.innerTime);
                    }
                }, true);

                scope.clearDateTime = function () {
                    scope.day = '';
                    scope.time = '';
                    scope.innerDay = {};
                    scope.innerTime = {};
                };
            }
        };
    }])
    .directive('lgDateTimeRanger', [function () {
        return {
            scope: {
                start: '=',
                end: '=',
                hasSec: '=',
                minStep: '=',
                secStep: '=',
                flip: '=',
                clear: '=',
                width: '@'
            },
            restrict: 'E',
            replace: true,
            template: '<div class="lg-date-time-ranger" ng-class="{active: active}">' +
            '<lg-date-time-picker clear="clear" width="{{width}}" class="pull-left" flip="flip.start" min-step="minStep" sec-step="secStep" day="start.day" has-sec="hasSec" time="start.time"></lg-date-time-picker>' +
            '<div class="pull-left to">&nbsp;至&nbsp;</div>' +
            '<lg-date-time-picker clear="clear" width="{{width}}" class="pull-left" flip="flip.end" min-step="minStep" sec-step="secStep" day="end.day" has-sec="hasSec" time="end.time"></lg-date-time-picker>' +
            '</div>'
        };
    }]);


angular.module('lg.tabs', []).
    directive('lgTabs', [function () {
        return {
            scope: {
                active: '=',
                onSelect: '&'
            },
            restrict: 'E',
            replace: true,
            transclude: true,
            template: '<div class="lg-tabs">' +
            '<ul class="nav nav-tabs">' +
            '<li class="lg-tab-title" ng-click="onClick(child)" ng-repeat="(index, child) in children | orderBy: index" ng-if="!child.hide" ng-class="{active: active === child.index}">' +
            '<a>{{child.label}}</a>' +
            '</li>' +
            '</ul>' +
            '<div class="lg-tab-wrapper" ng-transclude></div>' +
            '</div>',
            controller: function ($scope) {
                $scope.children = {};

                this.add = function (child) {
                    $scope.children[child.index] = child;
                };

                this.remove = function (child) {
                    delete $scope.children[child.index];
                };

                this.isActive = function (child) {
                    return child === $scope.children[$scope.active];
                };
            },
            link: function (scope) {
                scope.active = scope.active || 0;

                scope.onClick = function (child) {
                    scope.active = child.index;
                    scope.onSelect({index: child.index});
                };
            }
        };
    }]).
    directive('lgTab', [function () {
        return {
            scope: {
                index: '=',
                label: '=',
                hide: '='
            },
            restrict: 'E',
            transclude: true,
            require: '^lgTabs',
            replace: true,
            template: '<div class="lg-tab" ng-class="{active: isActive()}">' +
            '<div ng-transclude></div>' +
            '</div>',
            link: function (scope, element, attrs, tabsCtrl) {
                scope.isActive = function () {
                    return tabsCtrl.isActive(scope);
                };

                scope.$watch('hide', function (newVal) {
                    if (newVal) {
                        tabsCtrl.remove(scope);
                    } else {
                        tabsCtrl.add(scope);
                    }
                });

                scope.$on('$destroy', function () {
                    tabsCtrl.remove(scope);
                });
            }
        };
    }]);


angular.module('lg.tree', [])
    .service('lgTreeService', [function () {
        function iterateNode(node, cb) {
            (node !== node.$$_root) && cb(node);
            _.forEach(node.children, function (child) {
                iterateNode(child, cb);
            });
        }

        return {
            /**
             @doc lgTreeService {
                "name": "iterateNode(node, cb)",
                "desc": "遍历子树",
                "params": {
                    "node": "目标节点",
                    "cb": "原型为function(node),遍历子树时针对每个节点调用一次"
                }
             }@end
             */
            iterateNode: iterateNode,
            /**
             @doc lgTreeService {
                "name": "appendNodes(node, subnodes)",
                "desc": "添加子节点",
                "params": {
                    "node": "添加的节点作为node的子节点",
                    "subnodes": "子节点数组,其中每个成员是一个对象,其成员意义和传入lgTree标签的treeData对象一样"
                }
             }@end
             */
            appendNodes: function (node, subnodes) {
                _.forEach(subnodes, function (child) {
                    child.$$_root = node.$$_root;
                    child.$$_parent = node;
                    if (!child.leaf && !child.children) {
                        child.children = [];
                    }
                    node.children.push(child);
                });
            },
            /**
             @doc lgTreeService {
                "name": "deleteNode(node)",
                "desc": "删除节点",
                "params": {
                    "node": "待删除的节点"
                }
             }@end
             */
            deleteNode: function (node) {
                var coll = node.$$_parent.children;
                var pos = coll.indexOf(node);
                coll.splice(pos, 1);
            },
            /**
             @doc lgTreeService {
                "name": "insertNodes(node, prevNodes)",
                "desc": "在某节点前插入一些新节点",
                "params": {
                    "node": "新节点插在node之前",
                    "prevNodes": "待插入的节点数组"
                }
             }@end
             */
            insertNodes: function (node, prevNodes) {
                var coll = node.$$_parent.children;
                var pos = coll.indexOf(node);
                var tails = coll.splice(pos);

                _.forEach(prevNodes, function (nn) {
                    nn.$$_root = node.$$_root;
                    nn.$$_parent = node.$$_parent;
                    if (!nn.leaf && nn.children) {
                        nn.children = [];
                    }
                });

                tails = prevNodes.concat(tails);
                _.forEach(tails, function (child) {
                    coll.push(child);
                });
            }
        };
    }])
    .directive('lgTree', [function () {
        return {
            scope: {
                /**
                 @doc lgTree {
                    "name": "treeData",
                    "desc": "传入lgTree标签的对象",
                    "params": {
                        "title": "节点标题",
                        "children": "子节点数组,其中每个元素都是一个对象,其成员和treeData类似",
                        "leaf": "表明子节点是否为一个叶节点",
                        "toggle": "回调函数,参见下面的toggle项"
                    }
                 }@end
                 */
                treeData: '=',
                subtree: '='
            },
            restrict: 'E',
            replace: true,
            template: '<ul class="lg-tree" ng-class="{root: !subtree}">' +
            '<lg-tree-node ng-repeat="node in treeData.children track by $index" node-data="node"></lg-tree-node>' +
            '</ul>',
            controller: function ($scope) {
                if (!$scope.subtree) {
                    $scope.$watch('treeData', function (newVal) {
                        if (angular.isUndefined(newVal)) return;

                        $scope.treeData.$$_expand = true;
                        $scope.treeData.$$_root = $scope.treeData;
                        $scope.treeData.$$_parent = $scope.treeData;

                        _.forEach($scope.treeData.children, function (child) {
                            child.$$_root = $scope.treeData;
                            child.$$_parent = $scope.treeData;
                            if (!child.leaf && !child.children) {
                                child.children = [];
                            }
                        });
                    });
                }
            }
        };
    }]).
    directive('lgTreeNode', ['$compile', function ($compile) {
        return {
            scope: {
                nodeData: '='
            },
            restrict: 'E',
            replace: true,
            template: '<li class="lg-tree-node">' +
            '<div class="node-row" ng-if="nodeData.title">' +
            '<div class="row-hborder" ng-class="{long: nodeData.leaf}"></div>' +
            '<ul class="clearfix">' +
            '<li class="row-toggler" ng-if="!nodeData.leaf" ng-click="onToggle()"><i class="fa {{getIcon()}}"></i></li>' +
            '<li class="row-title" ng-class="{\'lg-tree-leaf\': nodeData.leaf}">' +
            '<span lg-html-code="{{nodeData.title}}"></span>' +
            '</li>' +
            '</ul>' +
            '</div>' +
            '<div class="node-subtree" ng-class="{collapse: !nodeData.$$_expand}">' +
            '</div>' +
            '</li>',
            link: function (scope, element) {
                scope.$node = scope.nodeData;
                scope.$root = scope.nodeData.$$_root;

                scope.getIcon = function () {
                    if (scope.nodeData.$$_expand) {
                        if (scope.nodeData.children && scope.nodeData.children.length) return 'fa-minus-square-o';
                        else return 'fa-spin fa-spinner';
                    } else {
                        return 'fa-plus-square-o';
                    }
                };

                /**
                 @doc lgTree {
                    "name": "toggle(isExpanded, node)",
                    "desc": "当展开某个节点时被调用",
                    "params": {
                        "isExpanded": "表明此次点击是展开还是收起",
                        "node": "被点击的节点"
                    }
                 }@end
                 */
                scope.onToggle = function () {
                    if (scope.nodeData.leaf) return;

                    scope.nodeData.$$_expand = !scope.nodeData.$$_expand;
                    scope.nodeData.$$_root.toggle && scope.nodeData.$$_root.toggle(scope.nodeData.$$_expand, scope.nodeData);
                };

                scope.$watch('nodeData.children', function (newVal) {
                    if (angular.isUndefined(newVal)) return;

                    _.forEach(scope.nodeData.children, function (child) {
                        child.$$_root = scope.nodeData.$$_root;
                        child.$$_parent = scope.nodeData;
                        if (!child.leaf && !child.children) {
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


angular.module('lg.treegrid', [])
    .directive('lgTreeGrid', [function () {
        return {
            scope: {
                gridData: '='
            },
            restrict: 'E',
            replace: true,
            template: '<div class="lg-tree-grid">' +
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
            link: function (scope) {
                function init(node) {
                    _.forEach(node.children, function (child) {
                        child.parent = node;
                        child.root = node.root;
                        child.level = node.level + 1;
                        scope._tbody.push(child);

                        init(child);
                    });
                }

                function filterBody() {
                    if (!scope.gridData) return;
                    if (!scope.gridData.searchKey) return scope._tbody;

                    return _.filter(scope._tbody, function (row) {
                        return row.title.indexOf(scope.gridData.searchKey) !== -1;
                    });
                }

                scope.$watch('_tbody', function (newVal) {
                    if (angular.isUndefined(newVal)) return;
                    scope._filteredBody = filterBody();
                });
                scope.$watch('gridData.searchKey', function (newVal) {
                    if (angular.isUndefined(newVal)) return;
                    scope._filteredBody = filterBody();
                });

                scope._getIcon = function (row) {
                    if (row.leaf) return '';
                    else if (!row.expand) return 'fa-plus-square-o';
                    else if (!row.children || !row.children.length) return 'fa-spin fa-spinner';
                    else return 'fa-minus-square-o';
                };

                scope._isExpanded = function (row) {
                    var parent = row.parent;
                    while (parent !== row.root) {
                        if (!parent.expand) return false;
                        parent = parent.parent;
                    }
                    return true;
                };

                scope._onToggle = function (event, row) {
                    row.expand = !row.expand;
                    scope.gridData.toggle && scope.gridData.toggle(row.expand, row);
                };

                scope._selected = {};

                scope._onSelectRow = function (event, row) {
                    if (!scope.gridData.selectRow || !scope.gridData.selectRow(row)) return;

                    scope._selected.row = row;
                    scope._selected.col = -1;
                };

                scope._onSelectCell = function (event, row, col) {
                    if (!scope.gridData.selectCell || !scope.gridData.selectCell(row, col)) return;

                    scope._selected.row = row;
                    scope._selected.col = col;
                };

                scope._isSelected = function (row, col) {
                    return scope._selected.row === row && scope._selected.col === col;
                };

                scope.$watch('gridData', function (newVal) {
                    if (angular.isUndefined(newVal)) return;

                    scope._tbody = [];

                    scope.gridData.level = 0;
                    scope.gridData.expand = true;
                    scope.gridData.root = scope.gridData;
                    scope.gridData.parent = scope.gridData;

                    /**
                     * 常用的表格操作函数, 提供给用户使用
                     */
                    scope.gridData.appendRows = function (parentRow, subrows) {
                        var row = parentRow;
                        var prevNode = row;
                        while (prevNode.children) {
                            prevNode = prevNode.children[prevNode.children.length - 1];
                        }

                        var tails = scope._tbody.splice(scope._tbody.indexOf(prevNode) + 1);

                        _.forEach(subrows, function (row) {
                            row.parent = parentRow;
                            row.level = parentRow.level + 1;
                            row.root = parentRow.root;

                            parentRow.children = parentRow.children || [];
                            parentRow.children.push(row);

                            init(row);
                        });

                        scope._tbody = scope._tbody.concat(subrows).concat(tails);
                    };

                    scope.gridData.deleteRow = function (row) {
                        var coll = row.parent.children;
                        var pos = coll.indexOf(row);
                        coll.splice(pos, 1);

                        var lastRow = row;
                        while (lastRow.children) {
                            lastRow = lastRow.children[lastRow.children.length - 1];
                        }
                        var lastPos = scope._tbody.indexOf(lastRow);
                        pos = scope._tbody.indexOf(row);
                        scope._tbody.splice(pos, lastPos - pos + 1);
                    };

                    scope.gridData.appendColumn = function (head, body, foot) {
                        head && scope.gridData.thead && scope.gridData.thead.items.push(head);
                        body && scope._tbody && _.forEach(scope._tbody, function (row) {
                            row.items.push(body);
                        });
                        foot && scope.gridData.tfoot && scope.gridData.tfoot.items.push(foot);
                    };

                    scope.gridData.deleteColumn = function (col) {
                        scope.gridData.thead && scope.gridData.thead.items.splice(col, 1);
                        scope._tbody && _.forEach(scope._tbody, function (row) {
                            row.items.splice(col, 1);
                        });
                        scope.gridData.tfoot && scope.gridData.tfoot.items.splice(col, 1);
                    }
                });

                scope.$watch('gridData.thead', function (newVal) {
                    if (angular.isUndefined(newVal)) return;
                    scope.$head = newVal;
                    newVal.root = scope.gridData;
                    newVal.parent = scope.gridData;
                });

                scope.$watch('gridData.tfoot', function (newVal) {
                    if (angular.isUndefined(newVal)) return;
                    scope.$foot = newVal;
                    newVal.root = scope.gridData;
                    newVal.parent = scope.gridData;
                });

                scope.$watch('gridData.tbody', function (newVal) {
                    if (angular.isUndefined(newVal)) return;

                    scope._tbody = [];

                    scope.gridData.children = newVal;
                    init(scope.gridData);
                });
            }
        };
    }]);


angular.module('lg.treetable', ['lg.table'])
    .service('lgTreeTableService', ['lgTableService', function (tblService) {
        function setBodyWithObjects(tableData, objects, colKeys) {
            tableData.children = objects;
            if (colKeys) {
                tableData.$$_titleKey = colKeys[0];
                tableData.$$_bodyKeys = colKeys.slice(1);
            }

            tableData.$$_tbody = [];
            tableData.$$_root = tableData;
            tableData.$$_parent = tableData;
            tableData.$$_level = 0;
            tableData.$$_expand = true;

            function init(node) {
                _.forEach(node.children, function (child) {
                    child.$$_parent = node;
                    child.$$_root = tableData;
                    child.$$_level = node.$$_level + 1;
                    tableData.$$_tbody.push(child);

                    init(child);
                });
            }

            init(tableData);
        }

        function iterateNode(node, cb) {
            (node !== node.$$_root) && cb && cb(node);
            _.forEach(node.children, function (child) {
                iterateNode(child, cb);
            });
        }

        function appendNodes(parent, children) {
            var prevRow = parent;
            while (prevRow.children && prevRow.children.length) {
                prevRow = prevRow.children[prevRow.children.length - 1];
            }

            var tableData = parent.$$_root;
            var tbody = tableData.$$_tbody;
            var tails = tbody.splice(tbody.indexOf(prevRow) + 1);
            var childRows = [];

            var oldChildren = parent.children || [];
            parent.children = children;

            function initNode(node) {
                _.forEach(node.children, function (child) {
                    child.$$_parent = node;
                    child.$$_root = node.$$_root;
                    child.$$_level = node.$$_level + 1;
                    childRows.push(child);

                    initNode(child);
                });
            }

            initNode(parent);

            parent.children = oldChildren.concat(parent.children);
            tableData.$$_tbody = tbody.concat(childRows).concat(tails);
        }

        function deleteNode(node) {
            var parentNode = node.$$_parent;
            var parentIdx = parentNode.children.indexOf(node);
            parentNode.children.splice(parentIdx, 1);

            var table = node.$$_root;
            var firstRowIdx = table.$$_tbody.indexOf(node);
            var lastNode = node;
            while (lastNode.children) {
                lastNode = lastNode.children[lastNode.children.length - 1];
            }
            var lastRowIdx = table.$$_tbody.indexOf(lastNode);
            table.$$_tbody.splice(firstRowIdx, lastRowIdx - firstRowIdx + 1);
        }

        return {
            /**
             @doc lgTreeTableService {
                "name": "setHeadWithArray(tableData, array)",
                "desc": "用矩阵(数组的数组)设置表头",
                "params": {
                    "tableData": "通过html标签传入lgTreeTable的对象",
                    "array": "数组中每个成员都是一个数组,代表表头/表尾"
                }
             }@end
             */
            setHeadWithArray: function (tableData, array) {
                tblService.setPartWithArray(tableData, 'head', array);
            },

            /**
             @doc lgTreeTableService {
                "name": "setFootWithArray(tableData, array)",
                "desc": "用矩阵(数组的数组)设置表尾",
                "params": {
                    "tableData": "通过html标签传入lgTreeTable的对象",
                    "array": "数组中每个成员都是一个数组,代表表头/表尾"
                }
             }@end
             */
            setFootWithArray: function (tableData, array) {
                tblService.setPartWithArray(tableData, 'foot', array);
            },

            /**
             @doc lgTreeTableService {
                "name": "setBodyWithObjects(tableData, objects)",
                "desc": "设置表体数据",
                "params": {
                    "tableData": "通过html标签传给lgTreeTable的对象",
                    "objects": "对象数组,每个元素代表每一行的数据。
                                如果是设置表体数据,则需要注意以下两点:
                                第一,如果与某行对应的行对象是row,则它的子行通过row.children数组指定。
                                第二,如果某行没有子行,则通过row.leaf=true来指定。"
                }
            }@end
             */
            setBodyWithObjects: function (tableData, objects, colKeys) {
                setBodyWithObjects(tableData, objects, colKeys);
            },

            /**
             @doc lgTreeTableService {
                "name": "appendNodes(parent, children)",
                "desc": "向某节点添加子节点",
                "params": {
                    "parent": "目标节点",
                    "children": "一个对象数组,将要成为parent的子节点"
                }
             }@end
             */
            appendNodes: appendNodes,
            /**
             @doc lgTreeTableService {
                "name": "deleteNode(row)",
                "desc": "删除某一行及其所有子行",
                "params": {
                    "row": "目标行对象"
                }
             }@end
             */
            deleteNode: deleteNode,

            /**
             @doc lgTreeTableService {
                "name": "iterateNode(node, cb)",
                "desc": "遍历树表中某个子树,针对每一个子节点调用回调函数",
                "params": {
                    "node": "某层自节点",
                    "cb": "回调函数,原型为function(node) {}."
                }
             }@end
             */
            iterateNode: iterateNode,
            /**
             @doc lgTreeTableService {
                "name": "iterateBody(tableData, cb)",
                "desc": "遍历表体,针对每一行调用回调函数",
                "params": {
                    "tableData": "通过html标签传入lgTreeTable的对象",
                    "cb": "回调函数,原型为function(row) {}."
                }
             }@end
             */
            iterateBody: function (tableData, cb) {
                iterateNode(tableData, cb);
            }
        };
    }])
    .directive('lgTreeTable', [function () {
        return {
            scope: {
                /**
                 @doc lgTreeTable {
                    "name": "tableData",
                    "desc": "传入lgDataTable标签的对象",
                    "params": {
                        "children": "子行数组,每个元素代表一个子节点,其内部成员和tableData类似",
                        "leaf": "是否为叶节点",
                        "toggle": "回调函数,见下面的toggle项"
                    }
                 }@end
                 */
                tableData: '='
            },
            restrict: 'E',
            replace: true,
            template: '<div class="lg-tree-table">' +
            '<table class="table table-bordered">' +
            '<thead>' +
            '<tr ng-repeat="($rnum, $row) in tableData.$$_thead track by $index">' +
            '<th ng-if="tableData.$$_headRowspan[$cnum][$rnum] && tableData.$$_headColspan[$rnum][$cnum]" ' +
            'rowspan="{{tableData.$$_headRowspan[$cnum][$rnum]}}" ' +
            'colspan="{{tableData.$$_headColspan[$rnum][$cnum]}}" ' +
            'ng-repeat="($cnum, $col) in $row track by $index">' +
            '<span lg-html-code="{{$col}}"></span>' +
            '</th>' +
            '</tr>' +
            '</thead>' +
            '<tbody>' +
            '<tr ng-repeat="$row in tableData.$$_tbody track by $index" ng-if="isExpanded($row)">' +

            '<td class="title" ng-style="{\'padding-left\': ($row.$$_level * 18 - 10) + \'px\'}">' +
            '<i ng-if="!$row.leaf" class="fa {{getIcon($row)}}" ng-click="toggle($row)"></i>' +
            '<span lg-html-code="{{$row[tableData.$$_titleKey]}}"></span>' +
            '</td>' +

            '<td ng-repeat="$$_key in tableData.$$_bodyKeys track by $index">' +
            '<span lg-html-code="{{$row[$$_key]}}"></span>' +
            '</td>' +

            '</tr>' +
            '</tbody>' +
            '<tfoot>' +
            '<tr ng-repeat="($rnum, $row) in tableData.$$_tfoot track by $index">' +
            '<th ng-if="tableData.$$_footRowspan[$cnum][$rnum] && tableData.$$_footColspan[$rnum][$cnum]" ' +
            'rowspan="{{tableData.$$_footRowspan[$cnum][$rnum]}}" ' +
            'colspan="{{tableData.$$_footColspan[$rnum][$cnum]}}" ' +
            'ng-repeat="($cnum, $col) in $row track by $index">' +
            '<span lg-html-code="{{$col}}"></span>' +
            '</th>' +
            '</tr>' +
            '</tfoot>' +
            '</table>' +
            '</div>',
            link: function (scope) {
                scope.$watch('tableData', function (newVal) {
                    if (angular.isUndefined(newVal)) return;
                    scope.$table = newVal;
                });
                scope.getIcon = function (row) {
                    if (row.leaf) return '';
                    else if (!row.$$_expand) return 'fa-plus-square-o';
                    else if (!row.children || !row.children.length) return 'fa-spin fa-spinner';
                    else return 'fa-minus-square-o';
                };

                scope.isExpanded = function (row) {
                    var parent = row.$$_parent;
                    while (parent !== row.$$_root) {
                        if (!parent.$$_expand) return false;
                        parent = parent.$$_parent;
                    }
                    return true;
                };

                scope.toggle = function (row) {
                    row.$$_expand = !row.$$_expand;
                    /**
                     @doc lgTreeTable {
                        "name": "toggle(expand, row)",
                        "desc": "展开某行的子行时被调用",
                        "params": {
                            "expand": "若为true表示是展开,否则为收起",
                            "row": "目标行"
                        }
                     }@end
                     */
                    scope.tableData.toggle && scope.tableData.toggle(row.$$_expand, row);
                };
            }
        };
    }]);


angular.module('lg.uploader', ['lg.modal'])
    .directive('lgUploader', ['$compile', '$timeout', function ($compile, $timeout) {
        return {
            scope: {
                open: '=',
                url: '@',
                name: '@',
                accept: '=',
                keyValues: '=',
                onComplete: '&'
            },
            restrict: 'E',
            replace: true,
            template: '<div class="lg-uploader">' +
            '<lg-modal open="open">' +
            '<div style="padding-bottom: 100px;position: relative;">' +
            '<div class="uploader-btn">' +
            '<div class="btn-group">' +
            '<div class="btn btn-large btn-add" ng-disabled="uploading.length">' +
            '<span>添加文件&nbsp;&nbsp;<i class="fa fa-plus-circle fa-large"></i></span>' +
            '<input type="file" accept="{{acceptedTypes}}" multiple="multiple" ng-disabled="uploading.length">' +
            '</div>' +
            '<a class="btn btn-large btn-upload" ng-disabled="!canUpload()" ng-click="upload()"><i class="fa fa-upload fa-large"></i></a>' +
            '</div>' +
            '<h5 class="btn-tip">按住&nbsp;Ctrl&nbsp;可多选</h5>' +
            '</div>' +
            '<div class="uploader-header">' +
            '<span>文件上传&nbsp; <small>关闭中断上传</small></span>' +
            '</div>' +
            '<ul class="uploader-list">' +
            '<li class="list-item" ng-repeat="file in files track by $index">' +
            '<ul class="clearfix">' +
            '<li class="item-icon"><i class="fa fa-file-o"></i></li>' +
            '<li class="item-name">{{file.name}}</li>' +
            '<li class="item-size">{{formatSize(file.size)}}&nbsp;</li>' +
            '<li class="item-action" ng-click="onClick(file, $index)"><i class="fa {{file.uploadInfo.action}}"></i></li>' +
            '</ul>' +
            '<div class="progress">' +
            '<div class="progress-bar" ng-style="{width: file.uploadInfo.progress}"></div>' +
            '</div>' +
            '</li>' +
            '</ul>' +
            '</div>' +
            '</lg-modal>' +
            '</div>',
            link: function (scope, element) {
                var xhr = new XMLHttpRequest();

                var GB = 1024 * 1024 * 1024;
                var MB = 1024 * 1024;
                var KB = 1024;

                var INIT_STATE = 'init';
                var UPLOAD_STATE = 'upload';
                var LOAD_STATE = 'load';
                var ERROR_STATE = 'error';
                var ABORT_STATE = 'abort';

                var inputElem = element.find('input');

                scope.acceptedTypes = _.map(scope.accept, function (type) {
                    return '.' + type;
                }).join(',');

                scope.$watch('open', function (newVal) {
                    if (angular.isUndefined(newVal)) {
                        scope.uploading = [];
                        scope.files = [];
                    }

                    if (!newVal) {
                        _.forEachRight(scope.files, function (file) {
                            if (file.uploadInfo.state === UPLOAD_STATE) {
                                xhr.abort();
                                return false;
                            }
                        });

                        delete scope.open;
                    }
                });

                scope.formatSize = function (size) {
                    if (size >= GB) {
                        var str = size / GB + '';
                        var idx = str.indexOf('.');
                        if (idx !== -1) {
                            str = str.substring(0, idx + 2);
                        }
                        return str + 'GB';
                    } else if (size >= MB) {
                        var str = size / MB + '';
                        var idx = str.indexOf('.');
                        if (idx !== -1) {
                            str = str.substring(0, idx + 2);
                        }
                        return str + 'MB';
                    } else if (size > KB) {
                        var str = size / KB + '';
                        var idx = str.indexOf('.');
                        if (idx !== -1) {
                            str = str.substring(0, idx + 2);
                        }
                        return str + 'KB';
                    } else {
                        return size + ' B';
                    }
                };

                function onComplete() {
                    $timeout(function () {
                        _.forEachRight(scope.files, function (file, i) {
                            if (file.uploadInfo.state === LOAD_STATE) {
                                scope.files.splice(i, 1);
                            }
                        });

                        if (!scope.files.length) {
                            scope.open = false;
                        }
                    }, 1000);
                }

                function resend(file) {
                    if (scope.uploading && scope.uploading.length) return;
                    delete file.uploadInfo.progress;
                    scope.uploading = [file];
                    send();
                }

                scope.onClick = function (file, idx) {
                    switch (file.uploadInfo.state) {
                        case INIT_STATE:
                            scope.files.splice(idx, 1);
                        case UPLOAD_STATE:
                            xhr.abort();
                            break;
                        case ABORT_STATE:
                        case ERROR_STATE:
                            resend(file);
                        default:
                            break;
                    }
                };

                inputElem.bind('change', function () {
                    scope.$apply(function () {
                        if (scope.files.length
                            && !angular.isUndefined(scope.files[0].uploadInfo.progress)) scope.files = [];

                        scope.files = scope.files.concat(_.map([].slice.call(inputElem[0].files, 0), function (file) {
                            file.uploadInfo = {state: INIT_STATE, action: 'fa-times'};
                            return file;
                        }));
                    });
                });
                scope.$on('$destroy', function () {
                    inputElem.unbind('change');
                });

                function send() {
                    var file = scope.uploading[0];
                    file.uploadInfo.state = UPLOAD_STATE;
                    file.uploadInfo.action = 'fa-spin fa-spinner';

                    var formData = new FormData();
                    formData.append(scope.name || 'file', file);
                    _.forEach(scope.keyValues, function (val, key) {
                        formData.append(key, val);
                    });

                    xhr.open('POST', scope.url);
                    xhr.send(formData);
                }

                xhr.upload.addEventListener('progress', function (event) {
                    $timeout(function () {
                        if (event.lengthComputable) {
                            if (!scope.uploading.length) return;
                            scope.uploading[0].uploadInfo.progress = event.loaded / event.total * 100 + '%';
                        }
                    });
                });

                xhr.addEventListener('load', function (event) {
                    $timeout(function () {
                        var file = scope.uploading.shift();
                        if (!file) return;

                        var param = {};
                        var status = parseInt(event.target.status);
                        param.name = file.name;
                        param.response = event.target.response;
                        (status == 200) && scope.onComplete({param: param});

                        file.uploadInfo.state = (status == 200 ? LOAD_STATE : ERROR_STATE);
                        file.uploadInfo.action = (status == 200 ? 'fa-check' : 'fa-warning');

                        if (scope.uploading.length) {
                            send();
                        } else {
                            onComplete();
                        }
                    });
                });

                xhr.addEventListener('abort', function () {
                    $timeout(function () {
                        var file = scope.uploading[0];
                        if (!file) return;

                        file.uploadInfo.state = ABORT_STATE;
                        file.uploadInfo.action = 'fa-ban';

                        scope.uploading.shift();
                        if (scope.uploading.length) {
                            send();
                        } else {
                            onComplete();
                        }
                    });
                });

                xhr.addEventListener('error', function () {
                    $timeout(function () {
                        var file = scope.uploading.shift();
                        if (!file) return;

                        file.uploadInfo.state = ERROR_STATE;
                        file.uploadInfo.action = 'fa-warning';

                        if (scope.uploading.length) {
                            send();
                        } else {
                            onComplete();
                        }
                    });
                });

                scope.canUpload = function () {
                    return scope.files.length
                        && !scope.uploading.length
                        && (!scope.files[0].uploadInfo || angular.isUndefined(scope.files[0].uploadInfo.progress));
                };

                scope.upload = function () {
                    if (!scope.canUpload()) return;
                    _.forEach(scope.files, function (file) {
                        scope.uploading.push(file);
                    });

                    send();
                };
            }
        };
    }]);