package com.bjpowernode;

import com.bjpowernode.crm.base.util.MD5Util;
import com.bjpowernode.crm.settings.mapper.UserMapper;
import org.junit.Test;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Service;


public class TestCrm {
    BeanFactory beanFactory
            = new ClassPathXmlApplicationContext("Spring/applicationContext.xml");
    UserMapper bean = (UserMapper) beanFactory.getBean("userMapper");

    @Test
    public void test01(){
        String admin = MD5Util.getMD5("123");
        System.out.println(admin);

    }
}
