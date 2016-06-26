package com.stars.travel.quartz.impl;

import com.stars.common.utils.EncryptUtil;
import com.stars.travel.model.base.User;
import com.stars.travel.quartz.UserTaskService;
import com.stars.travel.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Description : 用户定时任务
 * Author : guo
 * Date : 2016/6/23 0:42
 */
@Service
public class UserTaskServiceImpl implements UserTaskService{

    private static final Logger logger= LoggerFactory.getLogger(UserTaskServiceImpl.class);
    @Autowired
    private UserService userService;

    private static  String[] names = {"熹微","梦洁","欢锦","楠璇","薇紫","梦琛","婷敏","珊琪","初娜","雪华","惠梅","桂采","怡月","紫彩","栀香","香欣","茜林","芙彩","蕾婷","正玲","雪柏","桂弦",
            "怡梅","雅雅","可馨","心诺","心远","新梅","欣美","欣然","欣悦","欣欣","秀丽","秀美","秀逸","秀雅","秀华","秀兰","秀颖","秀隽","秀曼","秀媛","秀筠","秀慧","秀媚","秀婉","秀艾","秀敏",
            "英媛","盈秀","迎秋","莹玉","莹华","莹琇","颖慧","颖馨","颖然","颖秀","颖初","映波","映寒","映秋","幼仪","幼怡"};

    private static String phone = "1860791";

    public static final Short  USER_ENABLE = 1; //用户状态 不受限

    @Override
    public void initUser() {

        logger.info("初始化用户");
        for(int i=0;i<64;i++){
            int ran=(int)(Math.random()*9000)+1000;

            User user = new User();
            user.setCreatetime(new Date());
            user.setName(names[i]);
            user.setPhone(phone+ran);

            user.setPassword(EncryptUtil.md5("123456"));
            user.setCreatetime(new Date());
            user.setIsEnable(true);
            user.setState(USER_ENABLE);

            boolean ifExist = userService.phoneExits(user.getPhone());
            if(!ifExist){
               // userService.saveUser(user);
                logger.info("添加用户:"+user.getPhone()+"成功");
            }

        }
    }

}
