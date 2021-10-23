package com.bjpowernode.crm.workbench.service;

import cn.hutool.core.util.StrUtil;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import cn.hutool.poi.excel.StyleSet;
import com.bjpowernode.crm.base.bean.Resultvo;
import com.bjpowernode.crm.base.exception.CrmEnum;
import com.bjpowernode.crm.base.exception.CrmException;
import com.bjpowernode.crm.base.util.DateTimeUtil;
import com.bjpowernode.crm.base.util.UUIDUtil;
import com.bjpowernode.crm.settings.bean.User;
import com.bjpowernode.crm.settings.mapper.UserMapper;
import com.bjpowernode.crm.workbench.bean.Activity;
import com.bjpowernode.crm.workbench.bean.ActivityRemark;
import com.bjpowernode.crm.workbench.mapper.ActivityMapper;
import com.bjpowernode.crm.workbench.mapper.ActivityRemarkeMapper;
import com.github.pagehelper.PageHelper;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.EntityColumn;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Service
public class ActivityServiceImpl implements ActivityService{
    @Autowired
    ActivityMapper activityMapper;
    @Autowired
    UserMapper userMapper;
    @Autowired
    ActivityRemarkeMapper activityRemarkeMapper;
    @Override
    public List<Activity> list(int page,int pageSize,Activity activitis) {
        Example example = new Example(Activity.class);
            Example.Criteria criteria = example.createCriteria();
        if (StrUtil.isNotEmpty(activitis.getName())){
            criteria.andLike("name", "%" + activitis.getName() + "%");
        }
        if (StrUtil.isNotEmpty(activitis.getStartDate())){
            criteria.andGreaterThan("startDate", activitis.getStartDate());
        }
        if (StrUtil.isNotEmpty(activitis.getEndDate())){
            criteria.andLessThan("endDate", activitis.getEndDate());
        }


        if (StrUtil.isNotEmpty(activitis.getOwner())){

            Example example1 = new Example(User.class);
            Example.Criteria criteria1 = example1.createCriteria();
            criteria1.andLike("name", "%" +activitis.getOwner()+ "%");
            List<User> users = userMapper.selectByExample(example1);
            ArrayList<String> ids = new ArrayList<>();
            for (User us : users){
                ids.add(us.getId());
            }
            criteria.andIn("owner", ids);
        }
        PageHelper.startPage(page, pageSize);
        List<Activity> activities = activityMapper.selectByExample(example);

        for (Activity activity : activities) {
            String owner = activity.getOwner();
            User user = userMapper.selectByPrimaryKey(owner);
            activity.setOwner(user.getName());
        }



        /*if(StrUtil.isNotEmpty(activitis.getOwner())){
            Example example1 = new Example(User.class);
            example1.createCriteria().andLike("name", "%" + activitis.getOwner() + "%");
            List<User> users = userMapper.selectByExample(example1);
            List<String> ids = new ArrayList<>();
            for(User user : users){
                ids.add(user.getId());
            }
            criteria.andIn("id", ids);
        }

        PageHelper.startPage(page, pageSize);
        List<Activity> activities = activityMapper.selectByExample(example);
        for (Activity activity1 : activities) {
            //获取用户的外键
            String owner = activity1.getOwner();

            //查询用户
            User user = userMapper.selectByPrimaryKey(owner);

            activitis.setOwner(user.getName());
        }
*/

        return activities;
    }

    @Override
    public List<User> queryUesrs() {
        return userMapper.selectAll();
    }

    @Override
    public Resultvo saveOrUpdate(Activity activity, User user) {
        Resultvo resultvo = new Resultvo();

        if (activity.getId() == null) {
            activity.setId(UUIDUtil.getUUID());
            activity.setCreateBy(user.getName());
            activity.setCreateTime(DateTimeUtil.getSysTime());
            /*activity.setDescription(activity.getDescription());*/
            int i = activityMapper.insertSelective(activity);
            if (i == 0) {
                throw new CrmException(CrmEnum.ACTIVITY_ADD);
            }
        }else {
            activity.setEditBy(user.getName());
            activity.setEditTime(DateTimeUtil.getSysTime());
            int i = activityMapper.updateByPrimaryKeySelective(activity);
            if (i == 0){
                throw new CrmException(CrmEnum.ACTIVITY_UPDATE);
            }

        }
        resultvo.setOk(true);
        return resultvo;
    }

    @Override
    public Activity queryById(String id) {
        return activityMapper.selectByPrimaryKey(id);
    }

    @Override
    public void deleteBatch(String ids) {
         List<String> aids = Arrays.asList(ids.split(","));

        //delete from 表 where id in(1,2,3)
        Example example = new Example(Activity.class);
        example.createCriteria().andIn("id",aids);

       int count =  activityMapper.deleteByExample(example);
       if(count == 0){
           throw new CrmException(CrmEnum.ACTIVITY_DELETE);
       }
    }

    @Override
    public Activity toDetail(String id) {
        Activity activity = activityMapper.selectByPrimaryKey(id);


        activity.setOwner(userMapper.selectByPrimaryKey(activity.getOwner()).getName());



        ActivityRemark activityRemarke = new ActivityRemark();
        activityRemarke.setActivityId(activity.getId());

        List<ActivityRemark> activityRemarks = activityRemarkeMapper.select(activityRemarke);

        for (ActivityRemark remark : activityRemarks) {
            User user = userMapper.selectByPrimaryKey(remark.getOwner());
            remark.setImg(user.getImg());
        }
        activity.setActivityRemarks(activityRemarks);
        return activity;
    }

    @Override
    public void saveRemark(ActivityRemark activityRemark, User user) {
        activityRemark.setId(UUIDUtil.getUUID());
        activityRemark.setCreateBy(user.getName());
        activityRemark.setCreateTime(DateTimeUtil.getSysTime());
        activityRemark.setEditFlag("0");
        activityRemark.setImg(user.getImg());
        activityRemark.setOwner(user.getId());
        int i = activityRemarkeMapper.insertSelective(activityRemark);
        if(i == 0){
            throw new CrmException(CrmEnum.ACTIVITY_REMARK_INSERT);
        }
    }

    @Override
    public void updateRemark(ActivityRemark activityRemark, User user) {
        activityRemark.setEditFlag("1");
        activityRemark.setEditBy(user.getName());
        activityRemark.setEditTime(DateTimeUtil.getSysTime());
        int i = activityRemarkeMapper.updateByPrimaryKeySelective(activityRemark);
        if(i == 0){
            throw new CrmException(CrmEnum.ACTIVITY_REMARK_UPDATE);
        }
    }

    @Override
    public void deleteRemark(String id) {
        int i = activityRemarkeMapper.deleteByPrimaryKey(id);
        if(i == 0){
            throw new CrmException(CrmEnum.CUSTOMER_REMARK_DELETE);
        }
    }

    @Override
    public ExcelWriter exportExcel() {
        Example example = new Example(Activity.class);
        Map<String, EntityColumn> propertyMap = example.getPropertyMap();
        List<Activity> activities = activityMapper.selectByExample(example);

        //获取不到长度size
        List<Activity> propertyMap1 = activityMapper.selectAll();

        ExcelWriter writer = ExcelUtil.getWriter(true);
        //定义背景颜色
        StyleSet styleSet = writer.getStyleSet();
        styleSet.setBackgroundColor(IndexedColors.PINK1, false);
        writer.merge(propertyMap.size() - 1 ,"市场活动统计数据");
        writer.write(activities,true);
        return writer;
    }
}
