package com.bjpowernode.crm.base.interceptor;

import com.bjpowernode.crm.settings.bean.User;
import org.apache.xmlbeans.impl.xb.xsdschema.Public;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//登录拦截器
public class LoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //绝对路径
        StringBuffer requestURL = request.getRequestURL();
        //相对路径
        String requestURI = request.getRequestURI();
        User user = (User) request.getSession().getAttribute("user");
        if (user == null){
            response.sendRedirect("/crm");
        return false;
        }
        return true;
    }
}
