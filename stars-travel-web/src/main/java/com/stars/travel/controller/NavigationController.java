package com.stars.travel.controller;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author : samuel
 * @Description :获取导航栏
 * @Date : 15-12-25 下午3:04
 */
@Controller
@RequestMapping("navigation")
public class NavigationController extends BaseController{

    @RequestMapping(value = "getNavData.json",produces = "application/json;charset=utf-8")
    @ResponseBody
    public String getNavData(String callback){
        String json = getNavJson();
        if(callback != null){
            json = callback + "(" +json + ")";
        }
        return json;
    }

    /**
     * @ Description : 获取导航列表数据栏
     * @return
     */
    private String getNavJson(){
        //测试用
        JSONArray urlArray  = new JSONArray();
        JSONArray childrenArray  = new JSONArray();

        JSONObject object3 = new JSONObject();
        object3.put("id","1");
        object3.put("dir","dashboard");
        object3.put("html","dashboard.html");

        JSONObject object1 = new JSONObject();
        object1.put("id","2");
        object1.put("dir","user");
        object1.put("html","user.html");

        JSONObject object2 = new JSONObject();
        object2.put("id","3");
        object2.put("dir","journey");
        object2.put("html","journey.html");

        JSONObject  object4 = new JSONObject();
        object4.put("id","4");
        object4.put("dir","microblog");
        object4.put("html","microblog.html");

        JSONObject  object5 = new JSONObject();
        object5.put("id","5");
        object5.put("dir","comment");
        object5.put("html","comment.html");

        JSONObject  object16 = new JSONObject();
        object16.put("id","6");
        object16.put("dir","customization");
        object16.put("html","customization.html");

        childrenArray.add(object3);
        childrenArray.add(object1);
        childrenArray.add(object2);
        childrenArray.add(object4);
        childrenArray.add(object5);
        childrenArray.add(object16);

        JSONObject parent = new JSONObject();
        parent.put("id", "0");
        parent.put("dir","travels");
        parent.put("html","travels.html");
        parent.put("children",childrenArray);

        urlArray.add(parent);

        JSONArray navArray  = new JSONArray();
        JSONObject object8 = new JSONObject();
        object8.put("id","1");
        object8.put("name","控制台");
        object8.put("icon","");

        JSONObject object6 = new JSONObject();
        object6.put("id","2");
        object6.put("name","用户列表");
        object6.put("icon","");
        JSONObject object7 = new JSONObject();
        object7.put("id","3");
        object7.put("name","行程分享");
        object7.put("icon","");

        JSONObject object9 = new JSONObject();
        object9.put("id","4");
        object9.put("name","微游记");
        object9.put("icon","");

        JSONObject object10 = new JSONObject();
        object10.put("id","5");
        object10.put("name","评论");
        object10.put("icon","");

        JSONObject object11 = new JSONObject();
        object11.put("id","6");
        object11.put("name","定制");
        object11.put("icon","");

        navArray.add(object8);
        navArray.add(object6);
        navArray.add(object7);
        navArray.add(object9);
        navArray.add(object10);
        navArray.add(object11);

        JSONObject object = new JSONObject();
        object.put("url",urlArray);
        object.put("nav",navArray);

        return gson.toJson(object);
    }
}
