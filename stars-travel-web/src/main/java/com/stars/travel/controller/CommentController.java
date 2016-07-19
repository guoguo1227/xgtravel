package com.stars.travel.controller;

import com.stars.common.utils.Page;
import com.stars.common.enums.CommentTypeEnum;
import com.stars.travel.model.base.Comment;
import com.stars.travel.model.condition.SearchCondition;
import com.stars.travel.model.ext.CommentObj;
import com.stars.travel.model.ext.CommentVo;
import com.stars.travel.model.ext.RequestResult;
import com.stars.travel.service.CommentService;
import com.stars.travel.service.UserService;
import com.stars.travel.web.HttpSessionProvider;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.util.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Description : 评论rest层接口
 * Author : guo
 * Date : 2016/3/20 13:32
 */
@Controller
@RequestMapping("comment")
public class CommentController extends BaseController{

    @Autowired
    private CommentService commentService;

    @Autowired
    private UserService userService;
    /**
     * @Deescription : 查询评论列表
     * @param condition
     * @return
     */
    @RequestMapping("page")
    @ResponseBody
    public String queryCommentList(SearchCondition condition){

        RequestResult result = new RequestResult();
        result.setSuccess(false);
        if(null != condition){
            Page<CommentVo> page =  commentService.queryCommentPage(condition);
            if(null != page && null != page.getPageData() && page.getPageData().size()>0){
                result.setSuccess(true);
                result.setData(page);
            }
        }
        return gson.toJson(result);
    }

    /**
     * @Description : 删除评论
     * @param condition
     * @return
     */
    @RequestMapping("delete")
    @ResponseBody
    public String deleteComment(SearchCondition condition){

        RequestResult result = new RequestResult();
        result.setSuccess(false);
        if(null != condition){
            String phone = HttpSessionProvider.getSessionUserPhone();
            //获取APP的token
            if(!StringUtils.isBlank(condition.getToken())){
                phone = userService.queryPhoneByToken(condition.getToken());
            }
            if(StringUtils.isBlank(phone)){
                result.setMessage("请先登录。");
                return gson.toJson(result);
            }

            boolean success = commentService.deleteComment(condition.getId());
            result.setSuccess(success);

        }
        return gson.toJson(result);
    }

    /**
     * @Deescription : 查询评论列表-移动端
     * @param condition
     * @return
     */
    @RequestMapping("list")
    @ResponseBody
    public String queryCommentListApp(SearchCondition condition){

        RequestResult result = new RequestResult();
        result.setSuccess(false);
        CommentObj commentObj = new CommentObj();
        if(null != condition){
            if(!StringUtils.isBlank(condition.getType())){
                try{
                    Integer typeId = Integer.parseInt(condition.getType());
                    if(null != typeId){
                        String typeEnum = CommentTypeEnum.getCommentTypeByCode(typeId);
                        condition.setType(typeEnum);
                    }
                }catch (IllegalArgumentException e){
                    result.setMessage("评论类型不存在。");
                }
            }
            List<CommentVo> list =  commentService.queryCommentListApp(condition);
            int count = commentService.queryCommentCount(condition);
            if(null != list &&  list.size()>0){
                commentObj.setCommentVoList(list);
                commentObj.setCount(count);
                result.setSuccess(true);
                result.setData(commentObj);
            }
        }
        return gson.toJson(result);
    }

    /**
     * @Deescription : 查询我发布的评论列表
     * @param condition
     * @return
     */
    @RequestMapping("mylist")
    @ResponseBody
    public String queryMyCommentListApp(SearchCondition condition){

        RequestResult result = new RequestResult();
        result.setSuccess(false);

        if(null != condition){
            String userPhone = HttpSessionProvider.getSessionUserPhone();
            //获取APP的token
            if(!StringUtils.isBlank(condition.getToken())){
                userPhone = userService.queryPhoneByToken(condition.getToken());
            }
            if(StringUtils.isBlank(userPhone)){
                result.setMessage("请先登录。");
                return gson.toJson(result);
            }
            condition.setPhone(userPhone);//当前用户
            List<CommentVo> commentVos = commentService.queryMyCommentList(condition);
            if(!CollectionUtils.isEmpty(commentVos)){
                result.setSuccess(true);
                result.setData(commentVos);
            }

        }else{
            result.setMessage("参数不可为空");
        }

        return gson.toJson(result);
    }

    /**
     * @Description : 添加评论
     * @param comment
     * @return
     */
    @RequestMapping("add")
    @ResponseBody
    public String addComment(Comment comment, Integer type, String token){
        RequestResult result = new RequestResult();
        result.setSuccess(false);
        if(null != comment && null != type){
            if(null != comment.getRelateId()){
                String userPhone = HttpSessionProvider.getSessionUserPhone();
                //获取APP的token
                if(!StringUtils.isBlank(token)){
                    userPhone = userService.queryPhoneByToken(token);
                }
                if(!StringUtils.isBlank(userPhone)){
                    comment.setPhone(userPhone);
                    try{
                        String typeEnum = CommentTypeEnum.getCommentTypeByCode(type);
                        comment.setType(typeEnum);
                    }catch (IllegalArgumentException e){
                        result.setMessage("评论类型不存在。");
                        return gson.toJson(result);
                    }
                    boolean success = commentService.addComment(comment);
                    if(success){
                        result.setSuccess(true);
                    }else{
                        result.setMessage("添加失败。");
                    }
                }else {
                    result.setMessage("用户ID不可为空，请先登录。");
                }
            }else{
                result.setMessage("评论对象不可以为空。");
            }
        }else{
            result.setMessage("评论内容和类型不可为空");
        }
        return gson.toJson(result);
    }
}
