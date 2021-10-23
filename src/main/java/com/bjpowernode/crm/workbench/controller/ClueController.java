package com.bjpowernode.crm.workbench.controller;


import com.bjpowernode.crm.base.bean.Resultvo;
import com.bjpowernode.crm.base.exception.CrmException;
import com.bjpowernode.crm.workbench.bean.Activity;
import com.bjpowernode.crm.workbench.bean.Clue;
import com.bjpowernode.crm.workbench.bean.ClueActivityRelation;
import com.bjpowernode.crm.workbench.mapper.ClueMapper;
import com.bjpowernode.crm.workbench.service.ClueService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class ClueController {
    @Autowired
    ClueService clueService;

    @RequestMapping("/workbench/clue/list")
    public PageInfo list(int page , int pageSize,Clue clue){
        List<Clue> list = clueService.list(page,pageSize,clue);
        PageInfo pageInfo = new PageInfo(list);
        return pageInfo;
    }
    @RequestMapping("/workbench/clue/toDetail")
    public Clue toDetail(String id){
        return clueService.toDetail(id);
    }

    @RequestMapping("/workbench/clue/queryActivities")
    public List<Activity> queryActivities(String id , String name){
        List<Activity> list = clueService.queryActivities(id,name);
        return list;
    }

    @RequestMapping("/workbench/clue/bind")
    public Resultvo bind(String ids ,String id){
        Resultvo resultvo = new Resultvo();
        try {

            resultvo =clueService.bind(ids,id);
        } catch (CrmException e) {
            e.printStackTrace();
            resultvo.setMessage(e.getMessage());
        }
        return resultvo;
    }
    @RequestMapping("/workbench/clue/unbind")
    public Resultvo unbind(ClueActivityRelation clueActivityRelation){
        Resultvo resultvo = new Resultvo();
        try {

            clueService.unbind(clueActivityRelation);
            resultvo.setMessage("解绑成功");
            resultvo.setOk(true);
        } catch (CrmException e) {
            e.printStackTrace();
            resultvo.setMessage(e.getMessage());
        }
        return resultvo;
    }

}
