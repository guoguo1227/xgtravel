/*[[
    /lib/font-awesome/css/font-awesome.css,
    /lib/bootstrap/css/bootstrap.css,

    /lib/lodash.js,
    /lib/angular/angular.js,
    /lib/angular/angular-ui-router.js,
    /lib/echarts-all.js,
    /lib/moment.js,

    /module/module.js,

    /guide/guide.js,
]]*/

var app = angular.module('MainApp', ['lg.platform','lg.datatable','userApp','journeyApp','dashboardApp','webSocketApp','microblogApp','commentApp','customizationApp','userLoginApp']).
    controller('MainCtrl', ['$scope', '$location','lgDataTableService', function($scope, $location,dtService) {
    }]);

