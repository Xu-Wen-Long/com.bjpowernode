package com.bjpowernode.crm.settings.service;


import com.bjpowernode.crm.base.exception.CrmEnum;
import com.bjpowernode.crm.base.exception.CrmException;
import com.bjpowernode.crm.base.util.DateTimeUtil;
import com.bjpowernode.crm.base.util.MD5Util;
import com.bjpowernode.crm.settings.bean.User;
import com.bjpowernode.crm.settings.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;


import java.util.List;

@Service
public class UserService  implements IUserService {
    @Autowired
    UserMapper userMapper;
    @Override
    public User login(User user,String code,String correctCode) {

        String allowIps = user.getAllowIps();
        if(!correctCode.equalsIgnoreCase(code)){
            throw new CrmException(CrmEnum.USER_LOGIN_CODE);
        }

        user.setLoginPwd(MD5Util.getMD5(user.getLoginPwd()));
        /*user.setAllowIps(null);
        List<User> users = userMapper.select(user);*/
        Example example = new Example(User.class);
        example.createCriteria().andEqualTo("loginAct" , user.getLoginAct())
                .andEqualTo("loginPwd" , user.getLoginPwd());
        List<User> users = userMapper.selectByExample(example);
        if (users.size() == 0 ){
            throw new CrmException(CrmEnum.USER_LOGIN_USERNAME_PASSWORD);
        }

        user = users.get(0);
        //账号是否失效
        String nowTime = DateTimeUtil.getSysTime();
        String editTime = user.getExpireTime();
        if (editTime.compareTo(nowTime) < 0 ){
            throw new CrmException(CrmEnum.USER_LOGIN_EXPIRED);
        }

        if ("0".equals(user.getLockState())){
            throw new CrmException(CrmEnum.USER_LOGIN_LOCKED);
        }

        String allowIps1 = user.getAllowIps();
        if (!allowIps1.contains(allowIps)){
            throw new CrmException(CrmEnum.USER_LOGIN_ALLOWDIP);
        }
        return user;
    }

    @Override
    public void updateUser(User user) {

        user.setLoginPwd(MD5Util.getMD5(user.getLoginPwd()));

        int i = userMapper.updateByPrimaryKeySelective(user);

        if (i == 0){
            throw new CrmException(CrmEnum.USER_UPDATE_INFO);
        }
    }

    @Override
    public void verifyoldPwd(String loginPwd, String oldPwd) {
        oldPwd= MD5Util.getMD5(oldPwd);
        if(!loginPwd.equals(oldPwd)){
            throw new CrmException(CrmEnum.USER_UPDATE_OLDPWD);
        }
    }


}
