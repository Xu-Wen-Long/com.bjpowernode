package com.bjpowernode.crm.workbench.controller;

import com.bjpowernode.crm.base.bean.Resultvo;
import com.bjpowernode.crm.base.exception.CrmException;
import com.bjpowernode.crm.base.util.CommonUtil;
import com.bjpowernode.crm.settings.bean.User;
import com.bjpowernode.crm.workbench.bean.Clue;
import com.bjpowernode.crm.workbench.bean.Customer;
import com.bjpowernode.crm.workbench.bean.CustomerRemark;
import com.bjpowernode.crm.workbench.service.CustomerRemarkService;
import com.bjpowernode.crm.workbench.service.CustomerService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
public class CustomerController {
    @Autowired
    CustomerService customerService;

    @RequestMapping("/workbench/customer/list")
    public PageInfo list(int page , int pageSize, Customer customer){
        List<Customer> list = customerService.list(page,pageSize,customer);
        PageInfo pageInfo = new PageInfo(list);
        return pageInfo;
    }
   @RequestMapping("/workbench/customer/queryById")
    public Customer queryById(String id){
        return customerService.queryById(id);
    }

    @RequestMapping("/workbench/customer/queryUsers")
    public List<User> queryUsers(){
        return customerService.queryUsers();
    }


    @RequestMapping("/workbench/customer/saveOrUpdate")
    public Resultvo saveOrUpdate(Customer customer, HttpSession session){
        Resultvo resultvo = new Resultvo();
        try {
            User user = CommonUtil.getCurrentUser(session);
            resultvo= customerService.saveOrUpdate(customer,user);
        } catch (CrmException e) {
            e.printStackTrace();
            resultvo.setMessage(e.getMessage());
        }
        return resultvo;
    }

    @RequestMapping("/workbench/customer/deleteBatch")
    public Resultvo deleteBatch(String ids){
        Resultvo resultvo = new Resultvo();

        try {
            customerService.deleteBatch(ids);
            resultvo.setOk(true);
            resultvo.setMessage("删除市场活动成功!");
        } catch (CrmException e) {
            e.printStackTrace();
            resultvo.setMessage(e.getMessage());
        }

        return resultvo;
    }

    @RequestMapping("/workbench/customer/delete")
    public Resultvo delete(String id){
        Resultvo resultvo = new Resultvo();
        try {
            customerService.delete(id);
            resultvo.setOk(true);
            resultvo.setMessage("删除市场活动成功!");

        } catch (CrmException e) {
            e.printStackTrace();
            resultvo.setMessage(e.getMessage());
        }
        return resultvo;
    }



    @RequestMapping("/workbench/customer/toDetail")
    public Customer toDetail(String id,HttpSession session){
        User user = CommonUtil.getCurrentUser(session);
        return customerService.toDetail(id,user);
    }

    @RequestMapping("/workbench/customer/saveRemark")
    public Resultvo saveRemark(CustomerRemark customerRemark , HttpSession session){
        Resultvo resultvo = new Resultvo();
        try {
            User user = CommonUtil.getCurrentUser(session);
            customerService.saveRemark(customerRemark,user);
            resultvo.setOk(true);
            resultvo.setMessage("添加成功！");

        } catch (CrmException e) {
            e.printStackTrace();
            resultvo.setMessage(e.getMessage());
        }
        return resultvo;
    }

    @RequestMapping("/workbench/customer/updateRemark")
    public Resultvo updateRemark(CustomerRemark customerRemark,HttpSession session){
        Resultvo resultvo = new Resultvo();
        try {
            //
            User user = CommonUtil.getCurrentUser(session);
            customerService.updateRemark(customerRemark,user);
            resultvo.setOk(true);
            resultvo.setMessage("修改成功！");
        } catch (CrmException e) {
            e.printStackTrace();
            resultvo.setMessage(e.getMessage());
        }
        return resultvo;
    }
    @RequestMapping("/workbench/customer/deleteRemark")
    public Resultvo deleteRemark(String id){

        Resultvo resultvo = new Resultvo();
        try {
            customerService.deleteRemark(id);
            resultvo.setOk(true);
            resultvo.setMessage("删除成功！");
        } catch (CrmException e) {

            e.printStackTrace();
            resultvo.setMessage(e.getMessage());
        }
        return resultvo;

    }
}
