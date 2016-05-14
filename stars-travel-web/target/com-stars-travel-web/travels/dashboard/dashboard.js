/**
 * Created by samuel on 15-12-25.
 */

var app = angular.module('dashboardApp',['angular-constants']);
app.controller('dashboardCtrl',dashboardCtrl);


function dashboardCtrl($scope,$http,angularMeta){
    //初始化table
    $scope.init = function() {
        ready();

        $http.post("/log/count.json",{typeId:2},angularMeta.postCfg)
            .success(function(data){
                if(data.success){
                    $scope.dashboard.registerUserCount = data.data;
                }
            });
        $http.post("/log/count.json",{typeId:1},angularMeta.postCfg)
            .success(function(data){
                if(data.success){
                    $scope.dashboard.logUserCount = data.data;
                }
            });
        $http.post("/microblog/count.json",{typeId:2},angularMeta.postCfg)
            .success(function(data){
                if(data.success){
                    $scope.dashboard.microblogCount = data.data;
                }
            });
        $http.post("/journey/count.json",{typeId:2},angularMeta.postCfg)
            .success(function(data){
                if(data.success){
                    $scope.dashboard.journeyCount = data.data;
                }
            });
        $http.post("/comment/list.json",$scope.search,angularMeta.postCfg)
            .success(function(data){
                if(data.success){
                    $scope.dashboard.commentList = data.data.pageData;
                }
            });
        $http.post("/user/user-list.json",$scope.search,angularMeta.postCfg)
            .success(function(data){
                if(data.success){
                    $scope.dashboard.userList = data.data.pageData;
                }
            });
        $scope.createChart();
    };

    //初始化变量
    function ready(){
        $scope.search = {limit:8, currentPage:0,searchContent:''};
        $scope.dashboard = {logUserCount : 0,registerUserCount : 0};
    };

    $scope.createChart = function(){
        // 基于准备好的dom，初始化echarts实例
        var myChart = echarts.init(document.getElementById('main'));

        option = {
            tooltip : {
                trigger: 'axis',
                axisPointer : {            // 坐标轴指示器，坐标轴触发有效
                    type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
                }
            },
            legend: {
                data: ['微游记', '行程','定制','用户','评论']
            },
            grid: {
                left: '3%',
                right: '4%',
                bottom: '3%',
                containLabel: true
            },
            xAxis:  {
                type: 'value'
            },
            yAxis: {
                type: 'category',
                data: ['周一','周二','周三','周四','周五','周六','周日']
            },
            series: [
                {
                    name: '微游记',
                    type: 'bar',
                    stack: '总量',
                    label: {
                        normal: {
                            show: true,
                            position: 'insideRight'
                        }
                    },
                    data: [1, 6, 2, 4, 1, 3, 1]
                },
                {
                    name: '行程',
                    type: 'bar',
                    stack: '总量',
                    label: {
                        normal: {
                            show: true,
                            position: 'insideRight'
                        }
                    },
                    data: [11, 2, 3, 1, 2, 5, 1]
                },
                {
                    name: '定制',
                    type: 'bar',
                    stack: '总量',
                    label: {
                        normal: {
                            show: true,
                            position: 'insideRight'
                        }
                    },
                    data: [4, 5, 2, 1, 3, 3, 3]
                },
                {
                    name: '用户',
                    type: 'bar',
                    stack: '总量',
                    label: {
                        normal: {
                            show: true,
                            position: 'insideRight'
                        }
                    },
                    data: [11, 1, 3, 3, 2, 2, 1]
                },
                {
                    name: '评论',
                    type: 'bar',
                    stack: '总量',
                    label: {
                        normal: {
                            show: true,
                            position: 'insideRight'
                        }
                    },
                    data: [4, 5, 2, 1, 3, 4, 5]
                }
            ]
        };
        // 使用刚指定的配置项和数据显示图表。
        myChart.setOption(option);
    }

}