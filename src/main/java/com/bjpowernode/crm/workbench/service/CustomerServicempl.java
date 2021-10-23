package com.bjpowernode.crm.workbench.service;

import cn.hutool.core.util.StrUtil;
import com.bjpowernode.crm.base.bean.Resultvo;
import com.bjpowernode.crm.base.exception.CrmEnum;
import com.bjpowernode.crm.base.exception.CrmException;
import com.bjpowernode.crm.base.util.DateTimeUtil;
import com.bjpowernode.crm.base.util.UUIDUtil;
import com.bjpowernode.crm.settings.bean.User;
import com.bjpowernode.crm.settings.mapper.UserMapper;
import com.bjpowernode.crm.workbench.bean.Contacts;
import com.bjpowernode.crm.workbench.bean.Customer;
import com.bjpowernode.crm.workbench.bean.CustomerRemark;
import com.bjpowernode.crm.workbench.mapper.*;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.naming.Name;
import javax.persistence.Id;
import javax.servlet.http.HttpSession;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class CustomerServicempl implements CustomerService {
    @Autowired
    CustomerMapper customerMapper;
    @Autowired
    ActivityMapper activityMapper;
    @Autowired
    UserMapper userMapper;
    @Autowired
    CustomerRemarkMapper customerRemarkMapper;
    @Autowired
    ClueMapper clueMapper;
    @Autowired
    ContactsMapper contactsMapper;
    @Override
    public List<Customer> list(int page, int pageSize, Customer customer) {
        Example example = new Example(Customer.class);
        Example.Criteria criteria = example.createCriteria();

        //准备查询条件
        if (StrUtil.isNotEmpty(customer.getName())){
            criteria.andLike("name", "%"+ customer.getName() +"%");
        }
        if (StrUtil.isNotEmpty(customer.getOwner())){
            //因为客户输入进来的是所有着中文，但是customer表里面的owner是外键没办法xustomer查询，所以用user进行查询
            Example example1 = new Example(User.class);
            Example.Criteria criteria1 = example1.createCriteria();
            criteria1.andLike("name", "%"+ customer.getOwner() +"%");
            List<User> users = userMapper.selectByExample(example1);
            ArrayList<String> ids = new ArrayList<>();//搜集客户输入的owner对应的所以id
            for (User user : users) {
                ids.add(user.getId());
            }
            //通过user表对应的主键id进行对customer表的外键进行查询
            criteria.andIn("owner", ids);
        }
        if (StrUtil.isNotEmpty(customer.getPhone())){
            criteria.andLike("phone", "%"+ customer.getPhone() +"%");
        }
        if (StrUtil.isNotEmpty(customer.getWebsite())){
            criteria.andLike("website", "%"+ customer.getWebsite() +"%");
        }
        PageHelper.startPage(page, pageSize);
        //查询条件全部准备结束后进行查询

        List<Customer> customers = customerMapper.selectByExample(example);
        for (Customer customer1 : customers) {
            //设置owner               （查询user表）       （通过这个外键进行查询）   （拿到对应的name值给owner）
            customer1.setOwner(userMapper.selectByPrimaryKey(customer1.getOwner()).getName());
        }
        return customers;
    }

    @Override
    public Customer queryById(String id) {
        return customerMapper.selectByPrimaryKey(id);
    }
    @Override
    public List<User> queryUsers() {
        return userMapper.selectAll();
    }

    @Override
    public Resultvo saveOrUpdate(Customer customer, User user) {
        Resultvo resultvo = new Resultvo();
        //无参数表示添加
        if (customer.getId() == null){
            //开始添加数据
            customer.setId(UUIDUtil.getUUID());
            customer.setCreateTime(DateTimeUtil.getSysTime());
            customer.setCreateBy(user.getName());
            int i = customerMapper.insertSelective(customer);
            resultvo.setMessage("添加成功！");
            if (i == 0) {
                throw new CrmException(CrmEnum.CUSTOMER_INDEX_INSERT);
            }
        }else {//有参数表示修改
            customer.setCreateTime(DateTimeUtil.getSysTime());

            customer.setCreateBy(user.getName());
            int i = customerMapper.updateByPrimaryKeySelective(customer);
            resultvo.setMessage("修改成功！");
            if (i == 0) {
                throw new CrmException(CrmEnum.CUSTOMER_INDEX_UPDATE);
            }
        }
        resultvo.setOk(true);
        return resultvo;
    }

    @Override
    public void deleteBatch(String ids) {
        List<String> aids = Arrays.asList(ids.split(","));
        Example example = new Example(Customer.class);
        example.createCriteria().andIn("id", aids);
        int i = customerMapper.deleteByExample(example);
        if(i == 0){
            throw new CrmException(CrmEnum.ACTIVITY_DELETE);
        }

    }

    @Override
    public void delete(String id) {
        int i = customerMapper.deleteByPrimaryKey(id);
        if(i == 0){
            throw new CrmException(CrmEnum.CUSTOMER_INDEX_DELETE);
        }
    }

    @Override
    public Customer toDetail(String id, User user) {

        Customer customer = customerMapper.selectByPrimaryKey(id);


        //赋值修改者
        customer.setEditBy(user.getName());
        //赋值修改时间
        customer.setEditTime(DateTimeUtil.getSysTime());


        //备注表
        CustomerRemark customerRemark = new CustomerRemark();
        customerRemark.setCustomerId(customer.getId());
              //客户表
    /* Contacts contacts = new Contacts();
        contacts.setCustomerId(customer.getId());*/

        //传图像

        List<CustomerRemark> customerRemarks = customerRemarkMapper.select(customerRemark);


        Example example = new Example(Contacts.class);
        Example.Criteria criteria = example.createCriteria();
        for (CustomerRemark remark : customerRemarks) {
            //User user1 = userMapper.selectByPrimaryKey(customerMapper.selectByPrimaryKey(remark.getCustomerId()).getOwner());
          //  remark.setUser(user1);
            User user1 = userMapper.selectByPrimaryKey(customerMapper.selectByPrimaryKey(remark.getCustomerId()).getOwner());
            remark.setImg(user1.getImg());


            criteria.andEqualTo("customerId", remark.getCustomerId());
            Contacts contacts1 = contactsMapper.selectByExample(example).get(0);

            remark.setFullname(contacts1.getFullname());
            remark.setAppellation(contacts1.getAppellation());
            remark.setCreateTime(contacts1.getCreateTime());
            remark.setCreateBy(contacts1.getCreateBy());
            remark.setJob(contacts1.getJob());



           /* Example example = new Example(Contacts.class);
            Example.Criteria criteria = example.createCriteria();
            criteria.andEqualTo("customerId", remark.getCustomerId());
            Contacts contacts1 = contactsMapper.selectByExample(example).get(0);
            remark.setContacts(contacts1);
*/
        }
        customer.setCustomerRemarks(customerRemarks);

        //转换所有者

        customer.setOwner(userMapper.selectByPrimaryKey(customer.getOwner()).getName());
        return customer;
    }


    //添加备注

    @Override
    public void saveRemark(CustomerRemark customerRemark, User user) {
        customerRemark.setId(UUIDUtil.getUUID());

        customerRemark.setCreateBy(user.getName());

        customerRemark.setCreateTime(DateTimeUtil.getSysTime());

        int i = customerRemarkMapper.insertSelective(customerRemark);
        if(i == 0){
            throw new CrmException(CrmEnum.CUSTOMER_REMARK_INSERT);
        }
    }


    @Override
    public void updateRemark(CustomerRemark customerRemark, User user) {
        customerRemark.setEditBy(user.getName());
        customerRemark.setEditTime(DateTimeUtil.getSysTime());

        int i = customerRemarkMapper.updateByPrimaryKeySelective(customerRemark);
        if(i == 0){
            throw new CrmException(CrmEnum.CUSTOMER_REMARK_UPDATE);
        }
    }

    @Override
    public void deleteRemark(String id) {
        int i = customerRemarkMapper.deleteByPrimaryKey(id);
        if(i == 0){
            throw new CrmException(CrmEnum.CUSTOMER_REMARK_UPDATE);
        }
    }
}
