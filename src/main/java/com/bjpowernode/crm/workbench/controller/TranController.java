package com.bjpowernode.crm.workbench.controller;


import com.bjpowernode.crm.base.bean.Resultvo;
import com.bjpowernode.crm.base.exception.CrmException;
import com.bjpowernode.crm.base.util.CommonUtil;
import com.bjpowernode.crm.settings.bean.User;
import com.bjpowernode.crm.workbench.bean.StageVo;
import com.bjpowernode.crm.workbench.bean.Tran;
import com.bjpowernode.crm.workbench.service.TranService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import tk.mybatis.mapper.common.Mapper;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

@RestController
public class TranController {
    @Autowired
    TranService tranService;


    @RequestMapping("/workbench/transaction/list")
    public PageInfo list(@RequestParam(defaultValue = "1")int page , int pageSize , Tran tran){
        List<Tran> transactions = tranService.list(page,pageSize,tran);
        PageInfo pageInfo = new PageInfo(transactions);
        return pageInfo;
    }
    @RequestMapping("/workbench/tran/stage2Possibility")
    public String stage2Possibility(String stage, HttpSession session){
        Map<String,String> stage2PossibilityMap = (Map<String, String>) session.getServletContext().getAttribute("stage2PossibilityMap");
        return stage2PossibilityMap.get(stage);
    }
    @RequestMapping("/workbench/transaction/saveOrUpdate")
    public Resultvo saveOrUpdate(Tran tran,HttpSession session){
        Resultvo resultvo = new Resultvo();
        try {
            User user = CommonUtil.getCurrentUser(session);
            resultvo= tranService.saveOrUpdate(tran,user);
        } catch (CrmException e) {
            e.printStackTrace();
            resultvo.setMessage(e.getMessage());
        }
        return resultvo;
    }

    @RequestMapping("/workbench/tran/queryStages")
    public Map<String, Object> queryStages(String id, Integer index, HttpSession session){
        Map<String,String> stage2PossibilityMap =
                (Map<String, String>) session.getServletContext().getAttribute("stage2PossibilityMap");
        User user = CommonUtil.getCurrentUser(session);
        Map<String,Object> map = tranService.queryStages(id,index,stage2PossibilityMap,user);
            return map;
    }
   /* @RequestMapping("/workbench/tran/select")
    public Tran select(String id){
        Tran tran = tranService.select(id);
        return tran;
    }*/
}
