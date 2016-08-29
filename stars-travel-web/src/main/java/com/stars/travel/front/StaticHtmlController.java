package com.stars.travel.front;

import com.stars.common.enums.CommentTypeEnum;
import com.stars.common.utils.Page;
import com.stars.travel.controller.BaseController;
import com.stars.travel.model.base.Journey;
import com.stars.travel.model.condition.SearchCondition;
import com.stars.travel.model.ext.CommentVo;
import com.stars.travel.model.ext.JourneyVo;
import com.stars.travel.model.ext.MicroblogVo;
import com.stars.travel.service.CommentService;
import com.stars.travel.service.JourneyService;
import com.stars.travel.service.MicroblogVoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * Description : 分享静态页控制器
 * Author : guo
 * Date : 2016/8/22 22:10
 */
@RequestMapping("static")
@Controller
public class StaticHtmlController extends BaseController {

    private final static Logger logger = LoggerFactory.getLogger(StaticHtmlController.class);

    @Autowired
    private MicroblogVoService microblogVoService;

    @Autowired
    private JourneyService journeyService;

    @Autowired
    private CommentService commentService;
    /**
     * @Description: 行程分享静态页
     * @param id
     * @param modelMap
     * @return
     */
    @RequestMapping("{id}/microblog/share")
    public String shareJourneyHtml(@PathVariable Integer id, ModelMap modelMap){
        if(null != id){
            SearchCondition condition = new SearchCondition();
            condition.setId(id);
            Page<MicroblogVo> page = microblogVoService.querySharedMicroblogVoPage(condition,"");
            if(null != page && !CollectionUtils.isEmpty(page.getPageData())){
                MicroblogVo microblogVo = page.getPageData().get(0);
                modelMap.addAttribute("obj",microblogVo);
            }
        }
        return "content/microblog";
    }

    /**
     * @Description: 微游记静态页
     * @param id
     * @param modelMap
     * @return
     */
    @RequestMapping("{id}/journey/share")
    public String shareMicroblogHtml(@PathVariable Integer id, ModelMap modelMap){
        if(null != id){
            SearchCondition searchCondition = new SearchCondition();
            searchCondition.setId(id);
            Page<JourneyVo> page = journeyService.queryJourneyVos(searchCondition,"");
            //行程分享详情
            if(null != page && !CollectionUtils.isEmpty(page.getPageData())){
                JourneyVo vo = page.getPageData().get(0);
                modelMap.addAttribute("obj",vo);
            }
            //评论
            SearchCondition condition = new SearchCondition();
            String typeEnum = CommentTypeEnum.getCommentTypeByCode(1);
            condition.setType(typeEnum);
            condition.setId(id);
            condition.setIfNew(true);
            List<CommentVo> list =  commentService.queryCommentListApp(condition);
            modelMap.addAttribute("comments",list);
            //推荐行程
            List<Journey> journeyList = journeyService.queryRecommendJourney();
            modelMap.addAttribute("recommendJourney",journeyList);
        }
        return "content/journey";
    }
}
