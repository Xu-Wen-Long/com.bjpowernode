package com.bjpowernode.crm.workbench.controller;

import com.bjpowernode.crm.base.bean.Resultvo;
import com.bjpowernode.crm.base.exception.CrmException;
import com.bjpowernode.crm.base.util.CommonUtil;
import com.bjpowernode.crm.settings.bean.User;
import com.bjpowernode.crm.workbench.bean.Activity;
import com.bjpowernode.crm.workbench.bean.ActivityRemark;
import com.bjpowernode.crm.workbench.service.ActivityService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
public class ActivtityController {
    @Autowired
    ActivityService activityService;


    @RequestMapping("/workbench/activity/list")
    public PageInfo list(@RequestParam(defaultValue = "1")int page , int pageSize , Activity activity){

        /*PageHelper.startPage(page, pageSize);*/
        List<Activity> list = activityService.list(page,pageSize,activity);
        PageInfo pageInfo = new PageInfo(list);
        return pageInfo;
    }

    @RequestMapping("/workbench/activity/queryUsers")
    public List<User> queryUesrs(){
        return activityService.queryUesrs();
    }


    @RequestMapping("/workbench/activity/saveOrUpdate")
    public Resultvo saveOrUpdate(Activity activity , HttpSession session){
        Resultvo resultvo = new Resultvo();
        try {
            User user = CommonUtil.getCurrentUser(session);
            resultvo = activityService.saveOrUpdate(activity,user);


        } catch (CrmException e) {
            resultvo.setMessage(e.getMessage());
        }
        return resultvo;
    }
    @RequestMapping("/workbench/activity/queryById")
    public Activity queryById(String id){
        return activityService.queryById(id);
    }

    @RequestMapping("/workbench/activity/deleteBatch")
    public Resultvo deleteBatch(String ids){
        Resultvo resultvo = new Resultvo();

        try {
            activityService.deleteBatch(ids);
            resultvo.setOk(true);
            resultvo.setMessage("删除市场活动成功!");
        } catch (CrmException e) {
            e.printStackTrace();
            resultvo.setMessage(e.getMessage());
        }

        return resultvo;
    }

    @RequestMapping("/workbench/activity/toDetail")
    public Activity toDetail(String id){
        return activityService.toDetail(id);
    }

    @RequestMapping("/workbench/activity/saveRemark")
    public Resultvo saveRemark(ActivityRemark activityRemark,HttpSession session){
        Resultvo resultvo = new Resultvo();
        try {
            User user = CommonUtil.getCurrentUser(session);
            activityService.saveRemark(activityRemark,user);
            resultvo.setOk(true);
            resultvo.setMessage("添加成功！");
        } catch (CrmException e) {

            e.printStackTrace();
            resultvo.setMessage(e.getMessage());
        }
        return resultvo;
    }
    @RequestMapping("/workbench/activity/updateRemark")
    public Resultvo updateRemark(ActivityRemark activityRemark,HttpSession session){
        Resultvo resultvo = new Resultvo();
        try {
            User user = CommonUtil.getCurrentUser(session);
            activityService.updateRemark(activityRemark,user);
            resultvo.setOk(true);
            resultvo.setMessage("修改成功！");
        } catch (CrmException e) {

            e.printStackTrace();
            resultvo.setMessage(e.getMessage());
        }
        return resultvo;
    }

    @RequestMapping("/workbench/activity/deleteRemark")
    public Resultvo deleteRemark(String id){
        Resultvo resultvo = new Resultvo();
        try {
            activityService.deleteRemark(id);
            resultvo.setOk(true);
            resultvo.setMessage("删除成功！");
        } catch (CrmException e) {

            e.printStackTrace();
            resultvo.setMessage(e.getMessage());
        }
        return resultvo;
    }
}
