package com.stars.travel.websocket;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author : samuel
 * @Description :本地缓存WebSocketSession实例
 * @Date : 16-3-7
 */
public class WSSessionLocalCache implements Serializable {

    public final static String  WEBSOCKET_SESSION_USERID ="xgtravels_webSocket_currUserId";

    //本地缓存WebSocketSession实例
    private static Map<String, UserSocketVo> wsSessionCache = new HashMap<>();

    /**
     * userId对应ws session是否存在
     * @param userEmail
     * @return
     */
    public static boolean exists(String userEmail){
        if(!wsSessionCache.containsKey(userEmail)){
            return false;
        }else{
            return true;
        }
    }

    /**
     * 将新用户session存入本地缓存
     * @param userEmail
     * @param UserSocketVo
     */
    public static void put(String userEmail, UserSocketVo UserSocketVo){
        wsSessionCache.put(userEmail, UserSocketVo);
    }

    /**
     * 根据userId key获取对应ws session
     * @param userEmail
     * @return
     */
    public static UserSocketVo get(String userEmail){
        return wsSessionCache.get(userEmail);
    }

    /**
     * 从缓存移出指定session
     * @param userEmail
     */
    public static void remove(String userEmail){
        wsSessionCache.remove(userEmail);
    }

    /**
     * 获取缓存的所有session
     * @return
     */
    public static List<UserSocketVo> getAllSessions(){

        return new ArrayList<>(wsSessionCache.values());
    }
}
