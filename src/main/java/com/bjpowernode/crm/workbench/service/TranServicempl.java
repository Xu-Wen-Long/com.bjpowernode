package com.bjpowernode.crm.workbench.service;

import cn.hutool.core.util.StrUtil;
import com.bjpowernode.crm.base.bean.Resultvo;
import com.bjpowernode.crm.base.exception.CrmEnum;
import com.bjpowernode.crm.base.exception.CrmException;
import com.bjpowernode.crm.base.util.DateTimeUtil;
import com.bjpowernode.crm.base.util.UUIDUtil;
import com.bjpowernode.crm.settings.bean.User;
import com.bjpowernode.crm.settings.mapper.UserMapper;
import com.bjpowernode.crm.workbench.bean.*;
import com.bjpowernode.crm.workbench.mapper.*;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.*;

@Service
public class TranServicempl implements TranService {
    @Autowired
    TranMapper tranMapper;
    @Autowired
    UserMapper userMapper;
    @Autowired
    CustomerMapper customerMapper;
    @Autowired
    ContactsMapper contactsMapper;
    @Autowired
    TranHistoryMapper tranHistoryMapper;
    @Autowired
    TranRemarkMapper tranRemarkMapper;

    @Override
    public List<Tran> list(int page, int pageSize, Tran transaction) {
        Example example = new Example(Tran.class);
        Example.Criteria criteria = example.createCriteria();

        if (StrUtil.isNotEmpty(transaction.getOwner())){
            Example example1 = new Example(User.class);
            Example.Criteria criteria1 = example1.createCriteria();
            criteria1.andLike("name", "%" +transaction.getOwner()+ "%");
            List<User> users = userMapper.selectByExample(example1);

            ArrayList<String> ids = new ArrayList<>();
            for (User us : users){
                ids.add(us.getId());
            }
            criteria.andIn("owner", ids);
        }

        if (StrUtil.isNotEmpty(transaction.getName())){
            criteria.andLike("name", "%"+transaction.getName()+"%");
        }

        if (StrUtil.isNotEmpty(transaction.getCustomerId())){
            Example example1 = new Example(CustomerMapper.class);
            Example.Criteria criteria1 = example1.createCriteria();

            criteria1.andLike("name", "%" +transaction.getCustomerId()+ "%");
            List<Customer> users = customerMapper.selectByExample(example1);

            ArrayList<String> ids = new ArrayList<>();
            for (Customer us : users){
                ids.add(us.getId());
            }
            criteria.andIn("customerId", ids);
        }
        if (StrUtil.isNotEmpty(transaction.getStage())){
            criteria.andLike("stage", "%"+transaction.getStage()+"%");
        }
        if (StrUtil.isNotEmpty(transaction.getType())){
            criteria.andLike("type", "%"+transaction.getType()+"%");
        }
        if (StrUtil.isNotEmpty(transaction.getSource())){
            criteria.andLike("source", "%"+transaction.getSource()+"%");
        }

        if (StrUtil.isNotEmpty(transaction.getContactsId())){
            Example example1 = new Example(Contacts.class);
            Example.Criteria criteria1 = example1.createCriteria();
            criteria1.andLike("fullname", "%" +transaction.getContactsId()+ "%");
            List<Contacts> users = contactsMapper.selectByExample(example1);

            ArrayList<String> ids = new ArrayList<>();
            for (Contacts us : users){
                ids.add(us.getId());
            }
            criteria.andIn("contactsId", ids);
        }

        PageHelper.startPage(page, pageSize);
        List<Tran> transactions = tranMapper.selectByExample(example);
        for (Tran transacty : transactions) {
            //转换所有者
            transacty.setOwner(userMapper.selectByPrimaryKey(transacty.getOwner()).getName());
            //转换客户名称
            transacty.setCustomerId(customerMapper.selectByPrimaryKey(transacty.getCustomerId()).getName());
            //转换联系人名称
            transacty.setContactsId(contactsMapper.selectByPrimaryKey(transacty.getContactsId()).getFullname());
        }


        return transactions;
    }

    @Override
    public Resultvo saveOrUpdate(Tran tran, User user) {
        Resultvo resultvo = new Resultvo();
        if (tran.getId() == null){

         /* Example example = new Example(User.class);
            Example.Criteria criteria = example.createCriteria();

            criteria.andEqualTo("id",tran.getOwner());
            ArrayList<String> ids = new ArrayList<>();

            List<User> users = userMapper.selectByExample(example);
            for (User user1 : users) {
                ids.add(user1.getId());
            }
            tran.setOwner(ids.get(0));*/



            Customer customer = new Customer();
            customer.setId(UUIDUtil.getUUID());
            customer.setCreateTime(DateTimeUtil.getSysTime());
            customer.setCreateBy(user.getName());
            customer.setOwner(tran.getOwner());
            customer.setName(tran.getCustomerId());
            customerMapper.insertSelective(customer);


            Contacts contacts = new Contacts();
            contacts.setId(UUIDUtil.getUUID());
            contacts.setOwner(tran.getOwner());
            contacts.setFullname(tran.getContactsId());
            contactsMapper.insertSelective(contacts);


            Example example1 = new Example(Contacts.class);
            Example.Criteria criteria1 = example1.createCriteria();
            criteria1.andEqualTo("fullname", tran.getContactsId());
            tran.setContactsId(contactsMapper.selectByExample(example1).get(0).getId());


            Example example2 = new Example(Customer.class);
            Example.Criteria criteria2 = example2.createCriteria();
            criteria2.andEqualTo("name", tran.getCustomerId());

            tran.setCustomerId(customerMapper.selectByExample(example2).get(0).getId());
            tran.setId(UUIDUtil.getUUID());
            tran.setCreateTime(DateTimeUtil.getSysTime());
            tran.setCreateBy(user.getName());



            int i = tranMapper.insertSelective(tran);

            if (i == 0) {
                throw new CrmException(CrmEnum.TRANSACTION_SAVE_INSERT);
            }
            resultvo.setMessage("添加成功！");

        }else {

            tran.setEditTime(DateTimeUtil.getSysTime());
            tran.setEditBy(user.getName());
            int i = tranMapper.updateByPrimaryKeySelective(tran);
            if (i == 0) {
                throw new CrmException(CrmEnum.TRANSACTION_SAVE_UPDATE);
            }
            resultvo.setMessage("修改成功！");

        }
        resultvo.setOk(true);
        return resultvo;
    }

    @Override
    public Map<String, Object> queryStages(String id, Integer index1, Map<String, String> stage2PossibilityMap,User user) {
        HashMap<String, Object> map = new HashMap<>();

        Tran tran = tranMapper.selectByPrimaryKey(id);
        //获取交易阶段
        String stage = "";
        String possibility = "";

        ArrayList<Map.Entry<String, String>> entries = new ArrayList<>(stage2PossibilityMap.entrySet());

        if (index1 != null){
            stage = entries.get(index1).getKey();
            possibility = entries.get(index1).getValue();

            tran.setStage(stage);
            tran.setPossibility(possibility);

            tranMapper.updateByPrimaryKeySelective(tran);
            //添加一条交易历史
            TranHistory tranHistory = new TranHistory();
            tranHistory.setId(UUIDUtil.getUUID());
            tranHistory.setStage(stage);
            tranHistory.setMoney(tran.getMoney());
            tranHistory.setExpectedDate(tran.getExpectedDate());
            tranHistory.setCreateTime(DateTimeUtil.getSysTime());
            tranHistory.setCreateBy(user.getName());
            tranHistory.setTranId(id);
            tranHistory.setPossibility(possibility);
            tranHistoryMapper.insertSelective(tranHistory);
            map.put("tranHistory", tranHistory);
            //如果页面进行更改交易就会有index传入
            map.put("index", index1);



        } else {
            stage = tran.getStage();
            possibility = tran.getPossibility();

        }

        int index = 0;
        int position = 0;

        int count = 0;

        for (int i = 0 ; i < entries.size() ; i++){
            String key = entries.get(i).getKey();
            String value = entries.get(i).getValue();
            if (key.equals(stage)){
                    index = i;
            }
            if ("0".equals(value)){
                count += 1;
                if (count ==1){
                position = i;
                }
            }
        }
        ArrayList<StageVo> stageVos = new ArrayList<>();

        if ("0".equals(possibility)){

            for (int i = 0 ; i < entries.size() ; i++){
                StageVo stageVo = new StageVo();
                String key = entries.get(i).getKey();
                String value = entries.get(i).getValue();
                if("0".equals(value)){
                    if(stage.equals(stage)){
                        System.out.println("红x");
                        stageVo.setType("remove-circle");//图形
                        stageVo.setContent(key+":"+value);//文字
                        stageVo.setColor("deeppink");//颜色

                    }else{
                        System.out.println("黑x");
                        stageVo.setType("remove-circle");//图形
                        stageVo.setContent(key+":"+value);//文字
                        stageVo.setColor("#0f0f0f");//颜色
                    }
                }else{
                    System.out.println("黑圈");
                    stageVo.setType("record");//图形
                    stageVo.setContent(key+":"+value);//文字
                    stageVo.setColor("#0f0f0f");//颜色
                }
                stageVos.add(stageVo);
            }

        }else {
            for (int i = 0 ; i < entries.size(); i++){
                StageVo stageVo = new StageVo();
                String key = entries.get(i).getKey();
                String value = entries.get(i).getValue();
                if(i < index){
                    stageVo.setType("绿圈");
                    stageVo.setType("ok-circle");//图形
                    stageVo.setContent(key+":"+value);//文字
                    stageVo.setColor("#90F790");//颜色
                }else if(i == index){
                    stageVo.setType("锚点");
                    stageVo.setType("map-marker");//图形
                    stageVo.setContent(key+":"+value);//文字
                    stageVo.setColor("#90F790");//颜色
                }else if(i > index && i < position){
                    stageVo.setType("黑圈");
                    stageVo.setType("record");//图形
                    stageVo.setContent(key+":"+value);//文字
                    stageVo.setColor("#0f0f0f");//颜色
                }else{
                    stageVo.setType("黑x");
                    /* icon-remove-circle*/
                    stageVo.setType("remove-circle");//图形
                    stageVo.setContent(key+":"+value);//文字
                    stageVo.setColor("#0f0f0f");//颜色
                }
                stageVos.add(stageVo);

            }
        }

        tran.setOwner(userMapper.selectByPrimaryKey(tran.getOwner()).getName());
        tran.setCustomerId(customerMapper.selectByPrimaryKey(tran.getCustomerId()).getName());
        tran.setContactsId(contactsMapper.selectByPrimaryKey(tran.getContactsId()).getFullname());


        Example example = new Example(TranHistory.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("tranId", id);
        List<TranHistory> tranHistories = tranHistoryMapper.selectByExample(example);


        map.put("tran", tran);
        map.put("stageVos", stageVos);
        map.put("tranHistories", tranHistories);
        return map;
    }


 /*   @Override
    public Tran select(String id) {
        Tran tran = tranMapper.selectByPrimaryKey(id);
        tran.setOwner(userMapper.selectByPrimaryKey(tran.getOwner()).getName());
        return tran;
    }*/

    @Override
    public BarVo barVoEcharts() {

        List<Map<String, Long>> maps = tranMapper.queryStages();
        BarVo barVo = new BarVo();
        //标题集合
        ArrayList<String> titles = new ArrayList<>();
        //数据集合
        ArrayList<Long> values = new ArrayList<>();
        for (Map<String, Long> map : maps) {
            titles.add(map.get("stage") + "");
            values.add(map.get("num"));
        }
        barVo.setTitles(titles);
        barVo.setValues(values);
        return barVo;
    }

    @Override
    public List<PieVo> pieVoEcharts() {
        return tranMapper.queryPieVoStages();
    }
}
