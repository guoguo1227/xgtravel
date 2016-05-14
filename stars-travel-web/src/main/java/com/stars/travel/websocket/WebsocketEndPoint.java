package com.stars.travel.websocket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

/**
 * @author : samuel
 * @Description : websocket处理类
 * @Date : 16-3-3
 */

public class WebsocketEndPoint extends TextWebSocketHandler{
    private static final Logger logger = LoggerFactory.getLogger(WebsocketEndPoint.class);

    @Override
    protected void handleTextMessage(WebSocketSession session,
                                     TextMessage message) throws Exception {
        super.handleTextMessage(session, message);
        TextMessage returnMessage = new TextMessage(message.getPayload()+" received at server");
        session.sendMessage(returnMessage);
    }

    /**
     * @Description :　建立连接后
     * @param session
     * @throws Exception
     */
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception{
        UserSocketVo userSocketVo = (UserSocketVo)session.getAttributes().get(WSSessionLocalCache.WEBSOCKET_SESSION_USERID);
        if(null != userSocketVo){
            userSocketVo.setWebSocketSession(session);
            if(WSSessionLocalCache.exists(userSocketVo.getUserEmail())){
                WSSessionLocalCache.remove(userSocketVo.getUserEmail());
            }
            WSSessionLocalCache.put(userSocketVo.getUserEmail(), userSocketVo);
        }
        logger.info("socket成功建立连接...");
        super.afterConnectionEstablished(session);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session,CloseStatus status) throws Exception{
        UserSocketVo userSocketVo = (UserSocketVo)session.getAttributes().get(WSSessionLocalCache.WEBSOCKET_SESSION_USERID);
        if(null != userSocketVo){
            WSSessionLocalCache.remove(userSocketVo.getUserEmail());
        }
        logger.info("socket成功关闭连接...");
        super.afterConnectionClosed(session, status);
    }
}
