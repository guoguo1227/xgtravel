package com.stars.common.service;

import java.util.Vector;

/**
 * Description :
 * Author : guo
 * Date : 2016/2/2 22:21
 */
public class HttpResponse {
    private String urlString;
    private int defaultPort;
    private String file;
    private String host;
    private String path;
    private int port;
    private String protocol;
    private String query;
    private String ref;
    private String userInfo;
    private String contentEncoding;
    private String content;
    private String contentType;
    private int code;
    private String message;
    private String method;
    private int connectTimeout;
    private int readTimeout;
    private Vector<String> contentCollection = new Vector();

    public HttpResponse() {
    }

    public String getUrlString() {
        return this.urlString;
    }

    public void setUrlString(String urlString) {
        this.urlString = urlString;
    }

    public int getDefaultPort() {
        return this.defaultPort;
    }

    public void setDefaultPort(int defaultPort) {
        this.defaultPort = defaultPort;
    }

    public String getFile() {
        return this.file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public String getHost() {
        return this.host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getPath() {
        return this.path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public int getPort() {
        return this.port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getProtocol() {
        return this.protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public String getQuery() {
        return this.query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public String getRef() {
        return this.ref;
    }

    public void setRef(String ref) {
        this.ref = ref;
    }

    public String getUserInfo() {
        return this.userInfo;
    }

    public void setUserInfo(String userInfo) {
        this.userInfo = userInfo;
    }

    public String getContentEncoding() {
        return this.contentEncoding;
    }

    public void setContentEncoding(String contentEncoding) {
        this.contentEncoding = contentEncoding;
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContentType() {
        return this.contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public int getCode() {
        return this.code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMethod() {
        return this.method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public int getConnectTimeout() {
        return this.connectTimeout;
    }

    public void setConnectTimeout(int connectTimeout) {
        this.connectTimeout = connectTimeout;
    }

    public int getReadTimeout() {
        return this.readTimeout;
    }

    public void setReadTimeout(int readTimeout) {
        this.readTimeout = readTimeout;
    }

    public Vector<String> getContentCollection() {
        return this.contentCollection;
    }

    public void setContentCollection(Vector<String> contentCollection) {
        this.contentCollection = contentCollection;
    }

}
