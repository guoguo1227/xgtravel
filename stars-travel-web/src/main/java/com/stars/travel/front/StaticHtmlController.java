package com.stars.travel.front;

import com.stars.common.utils.Page;
import com.stars.travel.controller.BaseController;
import com.stars.travel.dao.ext.mapper.JourneyVoMapper;
import com.stars.travel.model.base.User;
import com.stars.travel.model.condition.SearchCondition;
import com.stars.travel.model.ext.MicroblogVo;
import com.stars.travel.service.MicroblogVoService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

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
    private JourneyVoMapper journeyVoMapper;

    @RequestMapping("{id}/share")
    public String shareHtml(Integer id, String type, ModelMap modelMap){
        if(null != id && !StringUtils.isBlank(type)){
            SearchCondition condition = new SearchCondition();
            condition.setId(id);
            Page<MicroblogVo> page = microblogVoService.querySharedMicroblogVoPage(condition,"");
            if(null != page && !CollectionUtils.isEmpty(page.getPageData())){
                MicroblogVo microblogVo = page.getPageData().get(0);
                modelMap.addAttribute("obj",microblogVo);
            }
        }
        return "content/detail";
    }
}
