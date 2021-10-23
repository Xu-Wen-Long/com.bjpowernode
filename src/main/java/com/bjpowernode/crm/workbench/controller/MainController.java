package com.bjpowernode.crm.workbench.controller;

import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MainController {

    @RequestMapping("main/index")
    public String index(){
        return "workbench/main/index";
    }
}
