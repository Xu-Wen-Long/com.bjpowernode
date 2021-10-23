package com.bjpowernode.crm.settings.service;


import com.bjpowernode.crm.settings.bean.User;
import tk.mybatis.mapper.common.Mapper;

public interface IUserService{
    User login(User user,String code,String correctCode);

    void updateUser(User user);

    void verifyoldPwd(String loginPwd, String oldPwd);


}
