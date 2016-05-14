/**
 * Created by guo on 2016/3/25.
 */
/**
 * Created by samuel on 15-12-25.
 */
var app = angular.module('customizationApp',['angular-constants']);
app.controller('customizationCtrl',customizationCtrl);

function customizationCtrl($scope,$http,angularMeta,lgDataTableService){
    //初始化table
    $scope.init = function() {
        $scope.ready();
        $scope.searchLoad(0);
    };

    $scope.ready = function(){
        $scope.customFlagObj = {addCustomOpen:false};
        $scope.search = {limit:15, currentPage:0,searchContent:'',isEnable:true,isShared:true};
    }

    //初始化表格数据
    $scope.initTableData = function(pageData){

        $scope.tableData = {
            //删除
            delete : function(row){
                $http.post("/customization/delete.json",{id:row.id},angularMeta.postCfg)
                    .success(function(data){
                        if(data.success){
                            $scope.searchLoad();
                            return toastr.success("删除成功");
                        }else{
                            return toastr.error("删除失败");
                        }
                    });
            }
        };

        var headerArray = ['定制ID','旅行时间','往返城市','外部交通','内部交通','酒店类型','酒店选择因素','景点偏好','美食偏好','备注','单人预算','人数','目的地','创建时间','操作'];
        lgDataTableService.setWidth($scope.tableData, undefined, [4,8],true);
        lgDataTableService.setHeadWithArrays($scope.tableData, [headerArray]);

        lgDataTableService.setBodyWithObjects($scope.tableData, _.map(pageData, function(pg) {
            pg.action =  '<a title="删除记录" class="btn bg-blue btn-xs lagou-margin-top-3" ng-click="$table.delete($row)">删除</a>';
            return pg;
        }), ['id', 'traveltime','city','outTraffic','innerTraffic','hotel','hotelFactors','featureHobby','foodHobby','remark','budget','peopleday','destination','createtime','action']);
    };

    //按条件查询
    $scope.searchLoad = function(page){
        if(page != undefined){
            $scope.search.currentPage = page ;
        }
        $http.post("/customization/page.json",$scope.search,angularMeta.postCfg)
            .success(function(data){
                if(data.success){
                    $scope.pagesNumber = data.data.totalPage;
                    $scope.totalEntries = data.data.totalCount;
                    $scope.initTableData(data.data.pageData);
                }else{
                    $scope.pagesNumber = 0;
                    $scope.totalEntries = 0;
                    $scope.initTableData(null);
                }
            });
    };

    //添加定制
    $scope.addCustom = function(){
        $scope.addCustomInfo = {};
        $scope.customFlagObj.addCustomOpen = true;
    }
    //保存定制
    $scope.saveCustom = function(){
        $http.post("/customization/add.json",$scope.addCustomInfo,angularMeta.postCfg)
            .success(function(data){
                if(data.success){
                    $scope.customFlagObj.addCustomOpen = false;
                    $scope.searchLoad(0);
                }
            });
    }

}