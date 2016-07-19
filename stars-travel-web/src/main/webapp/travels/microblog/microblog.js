/**
 * Created by guo on 2016/3/18.
 */
/**
 * Created by samuel on 15-12-25.
 */
var app = angular.module('microblogApp',['angular-constants','ngFileUpload']);
app.controller('microblogCtrl',microblogCtrl);

function microblogCtrl($scope,$http,angularMeta,lgDataTableService,Upload){
    //初始化table
    $scope.init = function() {
        $scope.ready();
        $scope.microblogFlagObj = {showAddMicroblog:false,showAddJourneyFlag : true, showJourneyFlag : true, showJourneyDayFlag : false,showAddJourneyDetail:false,showAddCommentFlag:false};
    };

    $scope.ready = function(){
        $scope.startTime = "";
        $scope.search = {limit:15, currentPage:0,searchContent:''};
        $http.post("/microblog/page.json",$scope.search,angularMeta.postCfg)
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
            //删除微游记
            deleteDetail : function(row){
                $http.post("/microblog/delete.json",{id:row.id},angularMeta.postCfg)
                    .success(function(data){
                        if(data.success){
                            $scope.searchLoad();
                            return toastr.success("删除成功");
                        }else{
                            return toastr.error("删除失败");
                        }
                    });
            },
            //添加评论
            addComment : function(row){
                $scope.microblogFlagObj.showAddCommentFlag = true;
                $scope.addComment = {relateId: row.id,type:2};
            },
            //收藏
            collect : function(row){
                $http.post("/microblog/collection.json",{id:row.id},angularMeta.postCfg)
                    .success(function(data){
                        if(data.success){
                            $scope.searchLoad();
                            return toastr.success("收藏成功");
                        }else{
                            return toastr.error(data.message);
                        }
                    });
            },
            //取消收藏
            uncollect : function(row){
                $http.post("/microblog/uncollection.json",{id:row.id},angularMeta.postCfg)
                    .success(function(data){
                        if(data.success){
                            $scope.searchLoad();
                            return toastr.success("取消收藏成功");
                        }else{
                            return toastr.error(data.message);
                        }
                    });
            },
            //顶赞
            top : function(row){
                $http.post("/microblog/top.json",{id:row.id},angularMeta.postCfg)
                    .success(function(data){
                        if(data.success){
                            $scope.searchLoad();
                            return toastr.success("顶赞成功");
                        }else{
                            return toastr.error(data.message);
                        }
                    });
            },
            //取消顶赞
            untop : function(row){
                $http.post("/microblog/untop.json",{id:row.id},angularMeta.postCfg)
                    .success(function(data){
                        if(data.success){
                            $scope.searchLoad();
                            return toastr.success("取消顶赞成功");
                        }else{
                            return toastr.error(data.message);
                        }
                    });
            },
            //编辑微游记
            updateMicroblog : function(row){
                $scope.microblogInfo = row;
                $scope.microblogFlagObj.showAddMicroblog = true;
            }
        };

        var headerArray = ['微游记ID','标题','封面图','玩趣图','美食图','风景图','新奇图','创建时间','内容','描述','目的地','目的地描述','味道描述','结尾描述','封面图描述','玩趣图描述','美食图描述','风景图描述','新奇图描述','是否分享','点赞次数','是否可用','操作'];
        lgDataTableService.setWidth($scope.tableData, undefined, [4,8],true);
        lgDataTableService.setHeadWithArrays($scope.tableData, [headerArray]);
        pageData = $scope.formatUserPageData(pageData);

        lgDataTableService.setBodyWithObjects($scope.tableData, _.map(pageData, function(pg) {
            pg.microblogpicture = "<img src={{$row.picture}} style='width:100px;height: 100px;'></img>";
            pg.microblogfunPicture = "<img src={{$row.funPicture}} style='width:100px;height: 100px;'></img>";
            pg.microblogfoodPicture = "<img src={{$row.foodPicture}} style='width:100px;height: 100px;'></img>";
            pg.microblogsceneryPicture = "<img src={{$row.sceneryPicture}} style='width:100px;height: 100px;'></img>";
            pg.microblognewnessPicture = "<img src={{$row.newnessPicture}} style='width:100px;height: 100px;'></img>";

            pg.action =  '<a title="" class="btn bg-blue btn-xs lagou-margin-top-3" ng-click="$table.deleteDetail($row)">删除</a>'+
                '<a title="添加评论" class="btn bg-green btn-xs lagou-margin-top-3" ng-click="$table.addComment($row)">添加评论</a>'+
/*
                '<a title="修改" class="btn bg-green btn-xs lagou-margin-top-3" ng-click="$table.updateMicroblog($row)">修改</a>'+
*/
                '<a title="收藏" ng-if="!$row.isCollection" class="btn bg-green btn-xs lagou-margin-top-3" ng-click="$table.collect($row)">收藏</a>'+
                '<a title="取消收藏" ng-if="$row.isCollection" class="btn bg-green btn-xs lagou-margin-top-3" ng-click="$table.uncollect($row)">取消收藏</a>'+
                '<a title="顶" ng-if="!$row.isTop" class="btn bg-blue btn-xs lagou-margin-top-3" ng-click="$table.top($row)">顶</a>'+
                '<a title="踩" ng-if="$row.isTop" class="btn bg-blue btn-xs lagou-margin-top-3" ng-click="$table.untop($row)">踩</a>';

            pg.titleStr = '<span style="word-wrap:break-word;">'+pg.title+'</span>';
            pg.destDescriptionStr = '<span style="word-wrap:break-word;">'+pg.destDescription+'</span>';
            pg.novelDescriptionStr = '<span style="word-wrap:break-word;">'+pg.novelDescription+'</span>';
            pg.endDescriptionStr = '<span style="word-wrap:break-word;">'+pg.endDescription+'</span>';
            pg.sceneryDescriptionStr = '<span style="word-wrap:break-word;">'+pg.sceneryDescription+'</span>';
            return pg;
        }), ['id','titleStr','microblogpicture','microblogfunPicture','microblogfoodPicture','microblogsceneryPicture','microblognewnessPicture','createtime','content','description','destination','destDescriptionStr','novelDescriptionStr','endDescriptionStr','pictureDescription','funPictureDescription','foodPictureDescription','sceneryDescription','newnessPictureDescription','sharetimes','topCount','isEnable','action']);
    };

    //按条件查询
    $scope.searchLoad = function(page){
        if(page != undefined){
            $scope.search.currentPage = page ;
        }
        $http.post("/microblog/page.json",$scope.search,angularMeta.postCfg)
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

    //添加评论
    $scope.saveComment = function(){
        $http.post("/comment/add.json",$scope.addComment,angularMeta.postCfg)
            .success(function(data){
                if(data.success){
                    $scope.microblogFlagObj.showAddCommentFlag = false;
                    return toastr.success("添加成功");
                }else{
                    return toastr.error("添加失败");
                }
            });
    }

    //添加微游记
    $scope.addMicroblog = function(){
        $scope.microblogInfo = {};
        $scope.microblogFlagObj.showAddMicroblog = true;
    }

    $scope.saveMicroblog = function(){

        $http.post("/microblog/add.json",$scope.microblogInfo,angularMeta.postCfg)
            .success(function(data){
                if(data.success){
                    $scope.microblogFlagObj.showAddMicroblog = false;
                    $scope.searchLoad();
                    return toastr.success("添加成功");
                }else{
                    return toastr.error("添加失败");
                }
            });
    }
    //上传
    $scope.uploadPicture = function (userImage,flag) {
        $scope.fileInfo = userImage;
        Upload.upload({
            //服务端接收
            url: '/image/upload.json',
            //上传的同时带的参数
            data: { 'imageType': 4 },
            file: userImage
        }).progress(function (evt) {
            //进度条
            var progressPercentage = parseInt(100.0 * evt.loaded / evt.total);
            console.log('progess:' + progressPercentage + '%' + evt.config.file.name);
        }).success(function (data, status, headers, config) {
            //上传成功
            console.log('file ' + config.file.name + 'uploaded. Response: ' + data);
            switch (flag){
                case 1:
                    $scope.microblogInfo.picture = data.data.uploadPath;
                    break;
                case 2:
                    $scope.microblogInfo.funPicture = data.data.uploadPath;
                    break;
                case 3:
                    $scope.microblogInfo.foodPicture = data.data.uploadPath;
                    break;
                case 4:
                    $scope.microblogInfo.sceneryPicture = data.data.uploadPath;
                    break;
                case 5:
                    $scope.microblogInfo.newnessPicture = data.data.uploadPath;
                    break;
            }
        }).error(function (data, status, headers, config) {
            //上传失败
            console.log('error status: ' + status);
        });
    };

    //格式化表格数据
    $scope.formatUserPageData = function(pageData){

        if(pageData != undefined && pageData != "" && pageData.length>0){
            for(var i in pageData){
                //注册账号激活状态
                if(pageData[i].isShared == 1){
                    pageData[i].isShared = '<font color="green">分享</font>';
                }else{
                    pageData[i].isShared = "不分享";
                }
                if(pageData[i].isEnable == 1){
                    pageData[i].isEnable = '<font color="green">可用</font>';
                }else{
                    pageData[i].isEnable = '<font color="red">已删除</font>';
                }
            }
        }
        return pageData;
    }
}
