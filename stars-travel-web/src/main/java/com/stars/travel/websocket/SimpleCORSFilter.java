package com.stars.travel.websocket;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * @author : samuel
 * @Description : 跨域访问
 * @Date : 16-3-14
 */
public class SimpleCORSFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpServletResponse response = (HttpServletResponse) res;
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers", "x-requested-with");

        HttpServletRequest request = (HttpServletRequest) req;
        Cookie cookie = new Cookie("JSESSIONID", request.getSession().getId());
        cookie.setPath("");
        response.addCookie(cookie);
        chain.doFilter(req, res);
    }

    @Override
    public void destroy() {

    }
}
