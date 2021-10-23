package com.bjpowernode.crm.base.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.Enumeration;

@Controller
public class ViewController {

    @RequestMapping("/login/{abc}/{def}")
    public void login(@PathVariable("abc") String username,@PathVariable("def") String password){
        System.out.println(username + File.separator + password);
    }


    @RequestMapping(value = {"/toView/{firstView}/{secondView}",
                            "/toView/{firstView}/{secondView}/{thirdView}",
                             "/toView/{firstView}/{secondView}/{thirdView}/{foreView}"})
    public String toView(@PathVariable(value = "firstView" , required = false) String firstView,
                         @PathVariable(value = "secondView" , required = false) String secondView,
                         @PathVariable(value = "thirdView"  , required = false) String thirdView,
                         @PathVariable(value = "foreView"  , required = false) String foreView,
                         HttpServletRequest request) {
        Enumeration<String> parameterNames = request.getParameterNames();
        for (;parameterNames.hasMoreElements();) {
            String name = parameterNames.nextElement();

            String value = request.getParameter(name);
            request.setAttribute(name, value);
        }

        if (thirdView != null) {
            if (foreView != null){
                return firstView + File.separator + secondView + File.separator + thirdView + File.separator + foreView;
            }else {
                return firstView + File.separator + secondView + File.separator + thirdView;
            }
        }
       return firstView + File.separator + secondView;
    }
}
