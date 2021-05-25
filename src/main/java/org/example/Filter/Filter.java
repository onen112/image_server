package org.example.Filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebFilter("/*")
public class Filter implements javax.servlet.Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {

        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;
        HttpSession session = request.getSession();
        String url = request.getRequestURI();
        Object user = session.getAttribute("user");
        if(user == null){
            if(url.contains("/login.html")){
                chain.doFilter(req,resp);
            }
            if(url.contains("/register.html")){
                chain.doFilter(req,resp);
            }
            Cookie cookie = new Cookie("username", null);
            cookie.setMaxAge(0);
            cookie.setPath("/");
            response.addCookie(cookie);
        }else{
            Map<String,String> map = (HashMap<String,String>) user;
            Cookie cookie = new Cookie("username", map.get("username"));
            response.addCookie(cookie);
        }
        if(!url.contains("/login.html") && !url.contains("/register.html")){
            System.out.println(url);
            chain.doFilter(req,resp);
        }

    }

    public void init(FilterConfig config) throws ServletException {

    }

}
