package com.bjpowernode.crm.workbench.service;

import com.bjpowernode.crm.base.bean.Resultvo;
import com.bjpowernode.crm.settings.bean.User;
import com.bjpowernode.crm.workbench.bean.BarVo;
import com.bjpowernode.crm.workbench.bean.PieVo;
import com.bjpowernode.crm.workbench.bean.Tran;

import java.util.List;
import java.util.Map;

public interface TranService {
    List<Tran> list(int page, int pageSize, Tran transaction);

    Resultvo saveOrUpdate(Tran tran, User user);


    Map<String, Object> queryStages(String id, Integer index1, Map<String, String> stage2PossibilityMap,User user);

    BarVo barVoEcharts();

    List<PieVo> pieVoEcharts();



    /*  Tran select(String id);*/
}
