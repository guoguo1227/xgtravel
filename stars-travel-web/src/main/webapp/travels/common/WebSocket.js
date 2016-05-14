/**
 * @author : samuel
 * @Description :
 * @Date : 16-3-7
 */
var app = angular.module('webSocketApp', []);
app.controller('webSocketCtrl', webSocketCtrl);

//app service服务
app.service('WebSocketService',
    ['$q', '$rootScope', function ($q, $rootScope) {
        var Service = {};
        if (window.location.protocol == 'http:') {
            url = 'ws://' + window.location.host + "/websocket";
        } else {
            url = 'wss://' + window.location.host + "/websocket";
        }
        var ws = new WebSocket(url);

        ws.onopen = function () {
            console.log(url);
            console.log("连接到了服务器!");
        };

        ws.onclose = function () {
            console.log(url);
            console.log('连接被关闭.');
        };

        //接受推送消息
        ws.onmessage = function (message) {
            //console.log('receied message...');
            console.log(angular.fromJson(message.data));
            listener(angular.fromJson(message.data));
        };

        function listener(messageObj){
            $rootScope.$broadcast('mySocketNews', messageObj);
        };

        return Service;
    }]);

//控制器
function webSocketCtrl($scope, $http, WebSocketService) {

    $scope.init = function () {
        $scope.socketNewsSize = 19;
        $scope.socketNewsArray = [{newsType: '业务消息', newsdetail: "this is you message.",createTime:'2016-12-11'}, {
            newsType: '业务消息',
            newsdetail: "this is my message",createTime:'2016-12-11'
        }];
    }
    //接受wesocketServer推送的消息
    $scope.$on('mySocketNews',function(event,data){
        $scope.socketNewsArray.push(data);
        console.log($scope.socketNewsArray);
        //手动告诉angular 变量需更新
        $scope.$apply();
    })

}















