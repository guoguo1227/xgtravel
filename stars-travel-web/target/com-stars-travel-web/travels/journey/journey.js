/**
 * Created by samuel on 15-12-25.
 */
var app = angular.module('journeyApp',['angular-constants']);
app.controller('journeyCtrl',journeyCtrl);

function journeyCtrl($scope,$http,angularMeta,lgDataTableService){
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
        $scope.search = {limit:15, currentPage:0,searchContent:'',userId:1,isEnable:true,isShared:true};

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
            }
        };

        var headerArray = ['行程ID','标题','内容','预算','天数','目的地','分享次数','被赞次数','创建时间','操作'];
        lgDataTableService.setWidth($scope.tableData, undefined, [4,8],true);
        lgDataTableService.setHeadWithArrays($scope.tableData, [headerArray]);

        lgDataTableService.setBodyWithObjects($scope.tableData, _.map(pageData, function(pg) {
            pg.action =  '<a title="查看记录" class="btn bg-blue btn-xs lagou-margin-top-3" ng-click="$table.openDetail($row)">查看详情</a>'+
                '<a title="收藏" ng-if="$row.isCollection == false" class="btn bg-green btn-xs lagou-margin-top-3" ng-click="$table.collect($row)">收藏</a>'+
                '<a title="取消收藏" ng-if="$row.isCollection == true" class="btn bg-green btn-xs lagou-margin-top-3" ng-click="$table.uncollect($row)">取消收藏</a>'+
                '<a title="顶" ng-if="$row.isTop == false" class="btn bg-green btn-xs lagou-margin-top-3" ng-click="$table.top($row)">顶</a>'+
                '<a title="踩" ng-if="$row.isTop == true" class="btn bg-green btn-xs lagou-margin-top-3" ng-click="$table.untop($row)">踩</a>';

            return pg;
        }), ['id', 'title','content','budget','totalday','destination','sharetimes','top','createtime','action']);
    };

    //按条件查询
    $scope.searchLoad = function(page){
        if(page != undefined){
            $scope.search.currentPage = page ;
        }
        $http.post("/journey/list.json",$scope.search)
            .success(function(data){
                if(data.success){
                    $scope.initTableData(data.data.pageData);
                }
            });
    };

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
        $http.post("/journey/add-journeyVo.json",$scope.journey,angularMeta.postCfg)
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
        }
    }
}