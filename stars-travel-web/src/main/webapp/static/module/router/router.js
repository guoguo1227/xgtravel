function lgGetUrlConfig(config) {
    var urlConfig = config.url;
    var navConfig = config.nav;
    var stateIdMap = {};
    var idUrlMap = {};
    var idNavMap = {};

    angular.module('lg.router', ['ui.router'])
        .config(['$stateProvider', '$urlRouterProvider', function($stateProvider, $urlRouterProvider) {
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
                    url: '/' + cfg.dir + (cfg.params ? '?'+ _.reduce(cfg.params, function(res, param) { return res + '&' + param; }) : ''),
                    templateUrl: cfg.state.replace(/\./g, '/') + '/' + cfg.html
                });

                stateIdMap[cfg.state] = cfg.id;
                idUrlMap[cfg.id] = cfg;

                if(cfg.children) {
                    _.forEach(cfg.children, function(child) { child.parent = cfg; });
                    _.forEach(cfg.children, recurFn);
                }
            });

            //处理导航参数
            _.forEach(navConfig, function recurFn(cfg, idx) {
                cfg.idx = idx;
                cfg.abstract = idUrlMap[cfg.id].abstract;
                idNavMap[cfg.id] = cfg;

                if(cfg.children) {
                    _.forEach(cfg.children, function(child) { child.parent = cfg; });
                    _.forEach(cfg.children, recurFn);
                }
            });
        }])
        .service('lgRouterService', ['$state', function($state) {
            return {
                getNavPathOfState: function(state) {
                    var configs = [];
                    var cfg = idNavMap[stateIdMap[state]];
                    while(cfg) {
                        configs.unshift(cfg);
                        cfg = cfg.parent;
                    }
                    return configs;
                },
                getNavConfig: function() {
                    return navConfig;
                },
                transferById: function(id, params) {
                    var cfg = idUrlMap[id];
                    if(cfg.abstract) return;

                    $state.go(cfg.state, params);
                }
            };
        }]);
}
