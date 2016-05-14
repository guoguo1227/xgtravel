/**
 * Created by samuel on 15-12-25.
 */
var app = angular.module('userApp',['angular-constants','ngFileUpload']);
app.controller('userCtrl',userCtrl);

function userCtrl($scope,$http,angularMeta,lgDataTableService,Upload){

    //提交
    $scope.uploadImage = function (userImage,flag) {
        $scope.fileInfo = userImage;
        Upload.upload({
            //服务端接收
            url: '/image/upload.json',
            //上传的同时带的参数
            data: { 'imageType': 1 },
            file: userImage
        }).progress(function (evt) {
            //进度条
            var progressPercentage = parseInt(100.0 * evt.loaded / evt.total);
            console.log('progess:' + progressPercentage + '%' + evt.config.file.name);
        }).success(function (data, status, headers, config) {
            //上传成功
            console.log('file ' + config.file.name + 'uploaded. Response: ' + data);
            if(flag == 1){
                $scope.userInfo.portrait = data.data.uploadPath;
            }else if(flag == 2){
                $scope.localuserInfo.introduceImage = data.data.uploadPath;
            }
        }).error(function (data, status, headers, config) {
            //上传失败
            console.log('error status: ' + status);
        });
    };

    $scope.onChangePage = function(page) {
        $scope.searchLoad(page);
    };
    //初始化table
    $scope.init = function() {
        ready();
        $scope.searchLoad();
    };

    //初始化变量
    function ready(){
        $scope.search = {limit:15, currentPage:1,searchContent:''};
        $scope.userFlag = {localuserOpen:false,userLoginOpen:false,userRegisterOpen:false,showAddCommentFlag:false}
        $scope.userInfo = {phone:'',password:'',portrait:'upload/user/201603/13001322e8fx.jpg'};
        $scope.journey = {};
        $scope.imageType = 1;
    };

    //按条件查询
    $scope.searchLoad = function(page){
        if(page != undefined){
            $scope.search.currentPage = page ;
        }
        $http.post("/user/user-page.json",$scope.search,angularMeta.postCfg)
            .success(function(data){
                if(data.success){
                    $scope.pagesNumber = data.data.totalPage;
                    $scope.totalEntries = data.data.totalCount;
                    $scope.initTableData(data.data.pageData);

                }else{
                    $scope.pagesNumber = 0;
                    $scope.totalEntries = 0;
                }
            });
    };
    //初始化表格数据
    $scope.initTableData = function(pageData){

        $scope.tableData = {

            //删除用户
            deleteUser : function(row){
                $http.post("/user/delete.json",{userId:row.id},angularMeta.postCfg)
                    .success(function(data){
                        if(data.success){
                            $scope.searchLoad(1);
                            return toastr.success("删除成功");
                        }else{
                            return toastr.error("删除失败");
                        }
                    });
            },
            //认证为当地人
            localUser : function(row){
                $scope.localuserInfo = {id:row.id,phone:row.phone,type:1};
                $scope.userFlag.localuserOpen = true;
            },
            addComment : function(row){
                $scope.addCommentInfo = {relateId:row.id,type:3};
                $scope.userFlag.showAddCommentFlag = true;
            }
        };

        var headerArray = ['用户ID','用户姓名','头像','注册时间','手机号','邮箱','标题图','介绍','所在地','职业','注册账号激活状态','操作'];
        lgDataTableService.setWidth($scope.tableData, undefined, [4,8],true);
        lgDataTableService.setHeadWithArrays($scope.tableData, [headerArray]);

        pageData = $scope.formatUserPageData(pageData);
        lgDataTableService.setBodyWithObjects($scope.tableData, _.map(pageData, function(pg) {
            pg.userPortrait = "<img src={{$row.portrait}} style='width:100px;height: 100px;'></img>";
            pg.userIntroduceImage = "<img src={{$row.introduceImage}} style='width:100px;height: 100px;'></img>";

            pg.action = '<a title="删除" class="btn bg-green btn-xs lagou-margin-left-3 lagou-margin-top-3" ng-click="$table.deleteUser($row)">删除</a>'+
                '<a title="认证为当地人" class="btn bg-orange btn-xs lagou-margin-left-3 lagou-margin-top-3" ng-click="$table.localUser($row)">认证为当地人</a>'+
                '<a title="添加评论" class="btn bg-info btn-xs lagou-margin-left-3 lagou-margin-top-3" ng-click="$table.addComment($row)">添加评论</a>';

            return pg;
        }), ['id', 'nikename','userPortrait','createTime','phone','email','userIntroduceImage','introduction','address','profession','isActive','action']);
    };

    //打开上传图片窗口
    $scope.uploadFileForm = function(){
        $scope.openUploadFileForm = true;
    }

    //用户注册窗口点击事件
    $scope.openRegisterForm = function(){
        $scope.userFlag.userRegisterOpen = true;
    }

    //取消用户注册设置
    $scope.registerCancle = function(){
        $scope.userFlag.userRegisterOpen = false;
    }

    //上传用户头像
    $scope.uploadUserImage = function(){
        $http.get("/image/upload.json",$scope.userInfo,angularMeta.postCfg)
            .success(function(data){
                if(data.success){
                    $scope.forbidUserOpen = false;
                    $scope.searchLoad();
                    return toastr.success("注册成功");
                }else{
                    return toastr.error("注册失败");
                }
            });
    }
    //用户注册
    $scope.register = function(){
        $http.post("/register/register.json",$scope.userInfo,angularMeta.postCfg)
            .success(function(data){
                if(data.success){
                    $scope.userFlag.userRegisterOpen = false;
                    $scope.searchLoad();
                    return toastr.success("注册成功");
                }else{
                    return toastr.error("注册失败");
                }
            });
    }

    //用户登录窗口点击事件
    $scope.openLoginForm = function(){
        $scope.userFlag.userLoginOpen = true;
        $scope.userInfo = {};
    }
    //关闭登录窗口
    $scope.loginFormCancle = function(){
        $scope.userFlag.userLoginOpen = false;
    }
    //用户登录
    $scope.longin = function () {
        $http.post("/login/login.json",$scope.userInfo,angularMeta.postCfg)
            .success(function(data){
                if(data.success){
                    $scope.userFlag.userLoginOpen = false;
                    $scope.searchLoad();
                    return toastr.success("登录成功");
                }else{
                    return toastr.error("登录失败");
                }
            });
    }

    //格式化表格数据
    $scope.formatUserPageData = function(pageData){

        if(pageData != undefined && pageData != "" && pageData.length>0){
            for(var i in pageData){
                //注册账号激活状态
                if(pageData[i].type == 1){
                    pageData[i].isActive = '<font color="green">激活</font>';
                }else{
                    pageData[i].isActive = "未激活";
                }

            }
        }
        return pageData;
    }
    //取消认证为当地人
    $scope.localuserCancle = function(){
        $scope.userFlag.localuserOpen = false;
    }
    //保存认证为当地人
    $scope.localuserSave = function(){
        $http.post("/user/update.json",$scope.localuserInfo,angularMeta.postCfg)
            .success(function(data){
                if(data.success){
                    $scope.userFlag.localuserOpen = false;
                    $scope.searchLoad();
                    return toastr.success("修改成功");
                }else{
                    return toastr.error("修改失败");
                }
            });
    }
    //添加评论
    $scope.saveComment = function(){
        $http.post("/comment/add.json",$scope.addCommentInfo,angularMeta.postCfg)
            .success(function(data){
                if(data.success){
                    $scope.userFlag.showAddCommentFlag = false;
                    toastr.success("添加成功");
                }else{
                    toastr.error("添加失败");
                }
            });
    }
}