/**
 * Created by samuel on 15-12-25.
 */
var app = angular.module('commentApp',['angular-constants']);
app.controller('commentCtrl',commentCtrl);

function commentCtrl($scope,$http,angularMeta,lgDataTableService,Upload){
    //初始化table
    $scope.init = function() {
        $scope.ready();
    };

    $scope.ready = function(){
        $scope.search = {limit:15, currentPage:0,searchContent:''};
        $http.post("/comment/page.json",$scope.search,angularMeta.postCfg)
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
    }

    //初始化表格数据
    $scope.initTableData = function(pageData){

        $scope.tableData = {
            //查看详情
            openDetail : function(row){
            }
        };

        var headerArray = ['评论ID','赞','评论详情','是否审核通过','关联ID','评论用户ID','用户昵称','类型','操作'];
        lgDataTableService.setWidth($scope.tableData, undefined, [4,8],true);
        lgDataTableService.setHeadWithArrays($scope.tableData, [headerArray]);
        pageData = $scope.formatUserPageData(pageData);

        lgDataTableService.setBodyWithObjects($scope.tableData, _.map(pageData, function(pg) {
            pg.action =  '<a title="查看记录" class="btn bg-blue btn-xs lagou-margin-top-3" ng-click="$table.openDetail($row)">查看详情</a>';
            return pg;
        }), ['id','ups','recommend','checked','relateId','userInfo.id','userInfo.name','type','action']);
    };
    //格式化表格数据
    $scope.formatUserPageData = function(pageData){

        if(pageData != undefined && pageData != "" && pageData.length>0){
            for(var i in pageData){
                //注册账号激活状态
                if(pageData[i].checked == 1){
                    pageData[i].checked = '<font color="green">通过</font>';
                }else{
                    pageData[i].checked = "未通过";
                }
            }
        }
        return pageData;
    }
}