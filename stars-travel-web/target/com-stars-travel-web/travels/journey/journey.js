/**
 * Created by samuel on 15-12-25.
 */
var app = angular.module('journeyApp',['angular-constants','ngFileUpload']);
app.controller('journeyCtrl',journeyCtrl);

function journeyCtrl($scope,$http,angularMeta,lgDataTableService,Upload){
    //提交
    $scope.uploadImage = function (userImage) {
        $scope.fileInfo = userImage;
        Upload.upload({
            //服务端接收
            url: '/image/upload.json',
            //上传的同时带的参数
            data: { 'imageType': 3 },
            file: userImage
        }).progress(function (evt) {
            //进度条
            var progressPercentage = parseInt(100.0 * evt.loaded / evt.total);
            console.log('progess:' + progressPercentage + '%' + evt.config.file.name);
        }).success(function (data, status, headers, config) {
            //上传成功
            console.log('file ' + config.file.name + 'uploaded. Response: ' + data);
            $scope.journey.coverImage = data.data.uploadPath;

        }).error(function (data, status, headers, config) {
            //上传失败
            console.log('error status: ' + status);
        });

    };
    //初始化table
    $scope.init = function() {
        $scope.ready();

        $('#journeyTimeRange').find(".date-picker").daterangepicker({
            autoUpdateInput: false,
            locale: {
                format: 'YYYY-MM-DD',
                cancelLabel: 'Clear'
            }
        }).on('apply.daterangepicker', function(ev, picker) {
            $scope.search.platformTimeRange = picker.startDate.format('YYYY-MM-DD') + ' - ' + picker.endDate.format('YYYY-MM-DD');
            $(this).val(picker.startDate.format('YYYY-MM-DD') + ' - ' + picker.endDate.format('YYYY-MM-DD'));
        }).on('cancel.daterangepicker', function(){
            $scope.search.platformTimeRange = "";
            $(this).val("");
        });
        $scope.journeyFlagObj = {showAddJourneyPannel:false,showAddJourneyFlag : false, showJourneyFlag : false, showJourneyDayFlag : false,showAddJourneyDetail:false};
    };

    $scope.ready = function(){
        $scope.startTime = "";
        $scope.search = {limit:15, currentPage:0,searchContent:'',userId:1,isShared:true};

        $http.post("/journey/page.json",$scope.search,angularMeta.postCfg)
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
                $http.post("/journey/detail.json",{id : row.id},angularMeta.postCfg)
                    .success(function(data){
                        if(data.success){
                            $scope.journeyDetail = data.data;
                            $scope.journeyFlagObj.showAddJourneyDetail = true;
                        }
                    });
            },
            //收藏
            collect : function(row){
                $http.post("/journey/collection.json",{id:row.id},angularMeta.postCfg)
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
                $http.post("/journey/uncollection.json",{id:row.id},angularMeta.postCfg)
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
                $http.post("/journey/top.json",{id:row.id},angularMeta.postCfg)
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
                $http.post("/journey/untop.json",{id:row.id},angularMeta.postCfg)
                    .success(function(data){
                        if(data.success){
                            $scope.searchLoad();
                            return toastr.success("取消顶赞成功");
                        }else{
                            return toastr.error(data.message);
                        }
                    });
            },
            //添加评论
            addComment : function(row){
                $scope.journeyFlagObj.showAddCommentFlag = true;
                $scope.addComment = {relateId: row.id,type:1};
            },
            //删除
            deleteJourney : function (row) {
                $http.post("/journey/delete.json",{id:row.id},angularMeta.postCfg)
                    .success(function(data){
                        if(data.success){
                            $scope.searchLoad();
                            return toastr.success("删除成功");
                        }else{
                            return toastr.error(data.message);
                        }
                    });
            },
            //更新
            updateJourney : function(row){

            }
        };

        var headerArray = ['行程ID','标题','封面图','内容','预算','天数','目的地','分享次数','被赞次数','创建时间','是否删除','操作'];
        lgDataTableService.setWidth($scope.tableData, undefined, [4,8],true);
        lgDataTableService.setHeadWithArrays($scope.tableData, [headerArray]);

        pageData = $scope.formatUserPageData(pageData);
        lgDataTableService.setBodyWithObjects($scope.tableData, _.map(pageData, function(pg) {
            pg.action =  '<a title="查看记录" class="btn bg-blue btn-xs lagou-margin-top-3" ng-click="$table.openDetail($row)">查看详情</a>'+
                '<a title="收藏" ng-if="$row.isCollection == false" class="btn bg-green btn-xs lagou-margin-top-3" ng-click="$table.collect($row)">收藏</a>'+
                '<a title="取消收藏" ng-if="$row.isCollection == true" class="btn bg-green btn-xs lagou-margin-top-3" ng-click="$table.uncollect($row)">取消收藏</a>'+
                '<a title="顶" ng-if="$row.isTop == false" class="btn bg-green btn-xs lagou-margin-top-3" ng-click="$table.top($row)">顶</a>'+
                '<a title="踩" ng-if="$row.isTop == true" class="btn bg-green btn-xs lagou-margin-top-3" ng-click="$table.untop($row)">踩</a>'+
                '<a title="删除" class="btn bg-red btn-xs lagou-margin-top-3" ng-click="$table.deleteJourney($row)">删除</a>'+
                '<a title="添加评论" class="btn bg-green btn-xs lagou-margin-top-3" ng-click="$table.addComment($row)">添加评论</a>';
            pg.img = "<a class='fancybox' rel='group' href={{$row.coverImage}}><img src={{$row.coverImage}} style='width:100px;height: 100px;' /></a>";

            return pg;
        }), ['id', 'title','img','content','budget','totalday','destination','sharetimes','top','createtime','isEnableStr','action']);
    };

    //按条件查询
    $scope.searchLoad = function(page){
        if(page != undefined){
            $scope.search.currentPage = page ;
        }

        $http.post("/journey/page.json",$scope.search,angularMeta.postCfg)
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
                    $scope.journeyFlagObj.showAddCommentFlag = false;
                    return toastr.success("添加成功");
                }else{
                    return toastr.error("添加失败");
                }
            });
    }
    $scope.test = function(){
        $scope.journey = {title:"这是的我的行程ss",starttime:"2015-01-09",endtime:"2016-05-09",description:"蔚蓝之旅sss",totalday:3,budget:3000,journeyDayVoList:[]};

        for(var i=1;i<3;i++){
            var obj = {
                currentDay :i,
                journeyItemVoList:[{journeyId:i,currentDay:i,title:"标题",description:"故宫",budget:33}]
            };
            $scope.journey.journeyDayVoList.push(obj);
        }

        //var list = JSON.stringify($scope.journey);

        var list = '{  "journeyDayVoList" : [    {      "journeyItemVoList" : [        {          "title" : "目的地",          "time" : "time",          "budget" : 200,          "description" : "feel\n"        }      ],      "currentDay" : 1    },    {      "journeyItemVoList" : [      ],      "currentDay" : 2    },    {      "journeyItemVoList" : [      ],      "currentDay" : 3    }  ],  "destination" : "目的地",  "starttime" : "2016-08-03",  "content" : "feel",  "endtime" : "2016-08-05",  "coverImage" : "\/upload\/journey\/201608\/03135521gk33.jpg",  "budget" : 2000,  "title" : "title"}';

        //var list = JSON.stringify(list);
        $.ajax({
            url:"/journey/add-journeyDatail.json",
            type:'post',
            data:{journeyVo:list,token:"294641541185f7652bafe87f40f4cfc4"},
            dataType:'json'
        }).done(function(result){

         });
    }
    //添加行程
    $scope.addJourney = function(){
        $scope.journeyFlagObj.showAddJourneyPannel = true;
        $scope.journeyFlagObj.showAddJourneyFlag = true;

        //$scope.journeyFlagObj.showJourneyFlag = true;
        //$scope.journeyFlagObj.showJourneyDayFlag = true;

        $scope.journey = {};
        $scope.addJourneyVo = {journeyDays : []}
        $scope.dayFlag = 0;
    }

    $scope.saveJourneyVo = function(){
        $http.post("/journey/add-journeyVo.json",{journey:$scope.journey},angularMeta.postCfg)
            .success(function(data){
                if(data.success){
                    $scope.journeyVo = data.data;
                    $scope.journeyFlagObj.showAddJourneyFlag = false;
                    $scope.journeyFlagObj.showJourneyFlag = true;
                    return toastr.success("添加成功");
                }else{
                    return toastr.error("添加失败");
                }
            });
    }
    //格式化表格数据
    $scope.formatUserPageData = function(pageData){

        if(pageData != undefined && pageData != "" && pageData.length>0){
            for(var i in pageData){
                //是否删除
                if(pageData[i].isEnable == 0){
                    pageData[i].isEnableStr = '<font color="red">已删除</font>';
                }else{
                    pageData[i].isEnableStr = '<font color="green">未删除</font>';
                }

            }
        }
        return pageData;
    }
    //添加每天行程
    $scope.addJourneyDayItem = function(){
        $scope.journeyFlagObj.showJourneyDayFlag = true;
        $scope.dayFlag += 1;
        var obj = {
            journeyId:$scope.journeyVo.id,
            currentDay :$scope.dayFlag,
            journeyItemList:[{journeyId:$scope.journeyVo.id,currentDay:$scope.dayFlag}]
        };
        $scope.addJourneyVo.journeyDays.push(obj);

    }
    //添加行程事件表单
    $scope.addJourneyItemForm = function (currentDay) {
        var obj = {time:'',title:'',content:'',budget:''};
        $scope.addJourneyVo.journeyDays[currentDay-1].journeyItemList.push(obj);
    }
    //保存每天事件
    $scope.saveJourneyItem = function(){
        console.log($scope.addJourneyVo )

        if($scope.addJourneyVo.journeyDays && Array.isArray($scope.addJourneyVo.journeyDays)){
            for(var i in $scope.addJourneyVo.journeyDays){
                $http.post("/journey/add-journeyDay.json",$scope.addJourneyVo.journeyDays[i].journeyItemList)
                    .success(function(data){
                        if(data.success){
                            $scope.journeyFlagObj.showAddJourneyPannel = false;
                            $scope.journeyFlagObj.showAddJourneyFlag = false;
                            $scope.journeyFlagObj.showJourneyFlag = false;
                            $scope.journeyFlagObj.showJourneyDayFlag = false;
                            return toastr.success("添加成功");
                        }else{
                            return toastr.error("添加失败");
                        }
                    });
            }
            $scope.searchLoad();
        }else{
            return toastr.error("行程不可为空");
        }
    }
}