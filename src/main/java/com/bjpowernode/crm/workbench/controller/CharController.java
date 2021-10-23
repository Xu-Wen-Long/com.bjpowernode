package com.bjpowernode.crm.workbench.controller;

import com.bjpowernode.crm.workbench.bean.BarVo;
import com.bjpowernode.crm.workbench.bean.PieVo;
import com.bjpowernode.crm.workbench.service.TranService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CharController {
    @Autowired
    private TranService tranService;

    @RequestMapping("/workbench/chart/barVoEcharts")
    public BarVo barVoEcharts(){
        return  tranService.barVoEcharts();
    }
    @RequestMapping("/workbench/chart/pieVoEcharts")
    public List<PieVo> pieVoEcharts(){
        return tranService.pieVoEcharts();
    }
}
