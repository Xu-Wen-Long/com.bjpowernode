package com.bjpowernode.crm.workbench.service;

import com.bjpowernode.crm.base.bean.Resultvo;
import com.bjpowernode.crm.settings.bean.User;
import com.bjpowernode.crm.workbench.bean.Customer;
import com.bjpowernode.crm.workbench.bean.CustomerRemark;

import javax.servlet.http.HttpSession;
import java.util.List;

public interface CustomerService {
    List<Customer> list(int page, int pageSize, Customer customer);
    Customer queryById(String id);

    List<User> queryUsers();

    Resultvo saveOrUpdate(Customer customer, User user);

    void deleteBatch(String ids);

    Customer toDetail(String id, User user);

    void delete(String id);

    void saveRemark(CustomerRemark customerRemark, User user);

    void updateRemark(CustomerRemark customerRemark, User user);

    void deleteRemark(String id);
}
