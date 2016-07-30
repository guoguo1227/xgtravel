angular.module('angular-constants', []).service('angularMeta', function($http){

    //配置$http.post使用jquery请求格式提交
    /*
     默认情况下，jQuery传输数据使用Content-Type: x-www-form-urlencodedand和类似于"foo=bar&baz=moe"的序列，
     然而AngularJS，传输数据使用Content-Type: application/json和{ "foo": "bar", "baz": "moe" }这样的json序列。

     其中Angular的post和put都是application/json，
     而jquery的post请求的"Content-Type"默认为" application/x-www-form-urlencoded"，
     于是我更改了angular的默认Content-Type.
     */
    var transFn = function(data) {
        return $.param(data);
    };
    var postCfg = {
        headers: { 'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8'},
        transformRequest: transFn
    };
    this.postCfg = postCfg;

    var postCfgJSON = {
        headers: { 'Content-Type': 'application/json; charset=UTF-8'},
        transformRequest: transFn
    };
    this.postCfgJSON = postCfgJSON;

});