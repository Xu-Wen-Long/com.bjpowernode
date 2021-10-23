package com.bjpowernode.crm.workbench.service;

import cn.hutool.core.util.StrUtil;
import cn.hutool.socket.aio.AioServer;
import com.bjpowernode.crm.base.bean.Resultvo;
import com.bjpowernode.crm.base.util.UUIDUtil;
import com.bjpowernode.crm.settings.bean.User;
import com.bjpowernode.crm.settings.mapper.UserMapper;
import com.bjpowernode.crm.workbench.bean.Activity;
import com.bjpowernode.crm.workbench.bean.Clue;
import com.bjpowernode.crm.workbench.bean.ClueActivityRelation;
import com.bjpowernode.crm.workbench.mapper.ActivityMapper;
import com.bjpowernode.crm.workbench.mapper.ClueActivityRelationMapper;
import com.bjpowernode.crm.workbench.mapper.ClueMapper;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.List;

@Service
public class ClueServicempl implements ClueService{
    @Autowired
    ClueMapper clueMapper;
    @Autowired
    ActivityMapper activityMapper;
    @Autowired
    ClueActivityRelationMapper clueActivityRelationMapper;
    @Autowired
    UserMapper userMapper;
    @Override
    public List<Clue> list(int page, int pageSize, Clue clue) {

        Example example = new Example(Clue.class);
        Example.Criteria criteria = example.createCriteria();

        if (StrUtil.isNotEmpty(clue.getFullname())){
            criteria.andLike("fullname", "%"+clue.getFullname()+"%");
        }
        if (StrUtil.isNotEmpty(clue.getCompany())){
            criteria.andLike("company", "%"+clue.getCompany()+"%");
        }
        if (StrUtil.isNotEmpty(clue.getPhone())){
            criteria.andLike("phone", "%"+clue.getPhone()+"%");
        }
        if (StrUtil.isNotEmpty(clue.getMphone())){
            criteria.andLike("mphone", "%"+clue.getMphone()+"%");
        }
        if (StrUtil.isNotEmpty(clue.getSource())){
            criteria.andLike("source", "%"+clue.getSource()+"%");
        }
        if (StrUtil.isNotEmpty(clue.getState())){
            criteria.andLike("state", "%"+clue.getState()+"%");
        }

        if (StrUtil.isNotEmpty(clue.getOwner())){
            Example example1 = new Example(Activity.class);
            Example.Criteria criteria1 = example1.createCriteria();
            criteria1.andLike("createBy", "%" +clue.getOwner()+ "%");
            List<Activity> activities = activityMapper.selectByExample(example1);
            ArrayList<String> ids = new ArrayList<>();
            for (Activity activity : activities){
                ids.add(activity.getId());
            }
            criteria.andIn("owner", ids);
        }

        PageHelper.startPage(page, pageSize);
        List<Clue> clues = clueMapper.selectByExample(example);

        for (Clue clue1 : clues) {
            String owner = clue1.getOwner();
            Activity activity = activityMapper.selectByPrimaryKey(owner);
            clue1.setOwner(activity.getCreateBy());
        }

        return clues;
    }

    @Override
    public Clue toDetail(String id) {
        //1、线索自己的信息
        Clue clue = clueMapper.selectByPrimaryKey(id);

        ClueActivityRelation clueActivityRelation = new ClueActivityRelation();
        clueActivityRelation.setClueId(id);

        List<ClueActivityRelation> select = clueActivityRelationMapper.select(clueActivityRelation);

        //定义一个集合存储中间表的市场活动主键
        ArrayList<String> aids = new ArrayList<>();

        for (ClueActivityRelation activityRelation : select) {
            aids.add(activityRelation.getActivityId());

        }
        Example example = new Example(Activity.class);
        example.createCriteria().andIn("id", aids);


        List<Activity> activities = activityMapper.selectByExample(example);


        for (Activity activity : activities) {
            activity.setOwner(userMapper.selectByPrimaryKey(activity.getOwner()).getName());
        }


        clue.setActivities(activities);


        return clue;
    }

    @Override
    public List<Activity> queryActivities(String id ,String name){
        ClueActivityRelation clueActivityRelation = new ClueActivityRelation();
        clueActivityRelation.setClueId(id);
        List<ClueActivityRelation> select = clueActivityRelationMapper.select(clueActivityRelation);

        List<String> adis = new ArrayList<>();

        for (ClueActivityRelation activityRelation : select) {
            adis.add(activityRelation.getActivityId());
        }
        Example example = new Example(Activity.class);
        example.createCriteria().andLike("name", "%"+name+"%").andNotIn("id", adis);

        List<Activity> activities = activityMapper.selectByExample(example);

        return  activities;

    }

    @Override
    public Resultvo bind(String ids, String id) {
        Resultvo resultvo = new Resultvo();
        String[] split = ids.split(",");
        ArrayList<Activity> activities = new ArrayList<>();
        for (String aid : split) {
            Activity activity = activityMapper.selectByPrimaryKey(aid);

            activity.setOwner(userMapper.selectByPrimaryKey(activity.getOwner()).getName());

            activities.add(activity);
            ClueActivityRelation clueActivityRelation = new ClueActivityRelation();
            clueActivityRelation.setId(UUIDUtil.getUUID());
            clueActivityRelation.setClueId(id);
            clueActivityRelation.setActivityId(aid);

            clueActivityRelationMapper.insertSelective(clueActivityRelation);
        }
            resultvo.setOk(true);
            resultvo.setMessage("添加成功！");
            resultvo.setT(activities);

        return resultvo;
    }

    @Override
    public void unbind(ClueActivityRelation clueActivityRelation) {
        clueActivityRelationMapper.delete(clueActivityRelation);
    }
}
