package com.stars.common.service.impl;

import com.stars.common.service.HttpRequestService;
import com.stars.common.service.HttpResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Iterator;
import java.util.Map;

/**
 * Description :
 * Author : guo
 * Date : 2016/2/2 22:22
 */
public class HttpRequestServiceImpl implements HttpRequestService {
    private static HttpRequestServiceImpl ins = new HttpRequestServiceImpl();
    private String defaultContentEncoding = "UTF-8";

    public static HttpRequestServiceImpl getIns() {
        return ins;
    }

    private HttpRequestServiceImpl() {
    }

    public HttpResponse sendGet(String urlString) throws IOException {
        return this.send(urlString, "GET", (Map)null, (Map)null);
    }

    public HttpResponse sendGet(String urlString, Map<String, String> params) throws IOException {
        return this.send(urlString, "GET", params, (Map)null);
    }

    public HttpResponse sendGet(String urlString, Map<String, String> params, Map<String, String> propertys) throws IOException {
        return this.send(urlString, "GET", params, propertys);
    }

    public HttpResponse sendPost(String urlString) throws IOException {
        return this.send(urlString, "POST", (Map)null, (Map)null);
    }

    public HttpResponse sendPost(String urlString, Map<String, String> params) throws IOException {
        return this.send(urlString, "POST", params, (Map)null);
    }

    public HttpResponse sendPost(String urlString, Map<String, String> params, Map<String, String> propertys) throws IOException {
        return this.send(urlString, "POST", params, propertys);
    }

    private HttpResponse send(String urlString, String method, Map<String, String> parameters, Map<String, String> propertys) throws IOException {
        HttpURLConnection urlConnection = null;
        Iterator key;
        String key1;
        if(method.equalsIgnoreCase("GET") && parameters != null) {
            StringBuffer url = new StringBuffer();
            int param = 0;

            for(key = parameters.keySet().iterator(); key.hasNext(); ++param) {
                key1 = (String)key.next();
                if(param == 0) {
                    url.append("?");
                } else {
                    url.append("&");
                }

                url.append(key1).append("=").append((String)parameters.get(key1));
            }

            urlString = urlString + url;
        }

        URL var10 = new URL(urlString);
        urlConnection = (HttpURLConnection)var10.openConnection();
        urlConnection.setRequestMethod(method);
        urlConnection.setDoOutput(true);
        urlConnection.setDoInput(true);
        urlConnection.setUseCaches(false);
        if(propertys != null) {
            Iterator var11 = propertys.keySet().iterator();

            while(var11.hasNext()) {
                String var13 = (String)var11.next();
                urlConnection.addRequestProperty(var13, (String)propertys.get(var13));
            }
        }

        if(method.equalsIgnoreCase("POST") && parameters != null) {
            StringBuffer var12 = new StringBuffer();
            key = parameters.keySet().iterator();

            while(key.hasNext()) {
                key1 = (String)key.next();
                var12.append("&");
                var12.append(key1).append("=").append((String)parameters.get(key1));
            }

            urlConnection.getOutputStream().write(var12.toString().getBytes());
            urlConnection.getOutputStream().flush();
            urlConnection.getOutputStream().close();
        }

        return this.makeContent(urlString, urlConnection);
    }

    private HttpResponse makeContent(String urlString, HttpURLConnection urlConnection) throws IOException {
        HttpResponse httpResponse = new HttpResponse();

        HttpResponse var9;
        try {
            InputStream e = urlConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(e));
            StringBuffer temp = new StringBuffer();

            for(String line = bufferedReader.readLine(); line != null; line = bufferedReader.readLine()) {
                httpResponse.getContentCollection().add(line);
                temp.append(line).append("\r\n");
            }

            bufferedReader.close();
            String ecod = urlConnection.getContentEncoding();
            if(ecod == null) {
                ecod = this.defaultContentEncoding;
            }

            httpResponse.setUrlString(urlString);
            httpResponse.setDefaultPort(urlConnection.getURL().getDefaultPort());
            httpResponse.setFile(urlConnection.getURL().getFile());
            httpResponse.setHost(urlConnection.getURL().getHost());
            httpResponse.setPath(urlConnection.getURL().getPath());
            httpResponse.setPort(urlConnection.getURL().getPort());
            httpResponse.setProtocol(urlConnection.getURL().getProtocol());
            httpResponse.setQuery(urlConnection.getURL().getQuery());
            httpResponse.setRef(urlConnection.getURL().getRef());
            httpResponse.setUserInfo(urlConnection.getURL().getUserInfo());
            httpResponse.setContent(new String(temp.toString().getBytes(), ecod));
            httpResponse.setContentEncoding(ecod);
            httpResponse.setCode(urlConnection.getResponseCode());
            httpResponse.setMessage(urlConnection.getResponseMessage());
            httpResponse.setContentType(urlConnection.getContentType());
            httpResponse.setMethod(urlConnection.getRequestMethod());
            httpResponse.setConnectTimeout(urlConnection.getConnectTimeout());
            httpResponse.setReadTimeout(urlConnection.getReadTimeout());
            var9 = httpResponse;
        } catch (IOException var13) {
            throw var13;
        } finally {
            if(urlConnection != null) {
                urlConnection.disconnect();
            }

        }

        return var9;
    }

    public String getDefaultContentEncoding() {
        return this.defaultContentEncoding;
    }

    public void setDefaultContentEncoding(String defaultContentEncoding) {
        this.defaultContentEncoding = defaultContentEncoding;
    }
}
