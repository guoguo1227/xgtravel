/**
 * Created by guo on 2016/3/18.
 */
/**
 * Created by samuel on 15-12-25.
 */
var app = angular.module('userLoginApp',['angular-constants']);
app.controller('userLoginCtrl',userLoginCtrl);

function userLoginCtrl($scope,$http,angularMeta){
    //初始化table
    $scope.init = function() {
        $scope.user = {};
    };

    $scope.login = function(){
        $http.post("/login/login.json",$scope.user,angularMeta.postCfg)
            .success(function(data){
                if(data.success){
                    window.location.href="admin.html";
                }else{
                    toastr.info(data.msg);
                }
            });
    }
}
