package com.stars.travel.websocket;

import com.stars.travel.common.util.DateUtil;
import com.stars.travel.model.ext.UserInfo;
import com.stars.travel.web.UserManager;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

import java.util.Date;
import java.util.Map;

/**
 * @author : samuel
 * @Description :创建握手（handshake）接口
 * @Date : 16-3-3
 */

public class HandshakeInterceptor extends HttpSessionHandshakeInterceptor{
    private static final Logger logger = LoggerFactory.getLogger(HandshakeInterceptor.class);

    @Override
    public boolean beforeHandshake(ServerHttpRequest request,
                                   ServerHttpResponse response, WebSocketHandler wsHandler,
                                   Map<String, Object> attributes) throws Exception {
        logger.info("建立握手前...");
        ServletRequestAttributes attrs = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        UserInfo currUser = UserManager.getSessionUser(attrs.getRequest());
        UserSocketVo userSocketVo = new UserSocketVo();
        String email= "";

        if(null != currUser){
            email = currUser.getEmail();
        }
        if(StringUtils.isBlank(email)){
            email = DateUtil.date2stringFormate(new Date());
        }
        userSocketVo.setUserEmail(email);
        attributes.put(WSSessionLocalCache.WEBSOCKET_SESSION_USERID, userSocketVo);

        return super.beforeHandshake(request, response, wsHandler, attributes);
    }

    @Override
    public void afterHandshake(ServerHttpRequest request,
                               ServerHttpResponse response, WebSocketHandler wsHandler,
                               Exception ex) {
        logger.info("建立握手后...");
        super.afterHandshake(request, response, wsHandler, ex);
    }
}
