package com.stars.travel.web;

import com.stars.common.utils.EncryptUtil;
import com.stars.travel.model.base.User;
import com.stars.travel.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.Date;

/**
 * Description :
 * Author : guo
 * Date : 2016/6/23 23:37
 */
public class UserInitListener implements ServletContextListener {

    private static final Logger logger= LoggerFactory.getLogger(UserInitListener.class);

    @Override
    public void contextInitialized(ServletContextEvent sce) {

    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        sce.getServletContext().setAttribute("contextPath", sce.getServletContext().getContextPath());

    }
}
