package com.stars.common.service;


import java.io.IOException;
import java.util.Map;

/**
 * Description :
 * Author : guo
 * Date : 2016/2/2 22:19
 */
public interface HttpRequestService {

    HttpResponse sendGet(String var1) throws IOException;

    HttpResponse sendGet(String var1, Map<String, String> var2) throws IOException;

    HttpResponse sendGet(String var1, Map<String, String> var2, Map<String, String> var3) throws IOException;

    HttpResponse sendPost(String var1) throws IOException;

    HttpResponse sendPost(String var1, Map<String, String> var2) throws IOException;

    HttpResponse sendPost(String var1, Map<String, String> var2, Map<String, String> var3) throws IOException;

}
