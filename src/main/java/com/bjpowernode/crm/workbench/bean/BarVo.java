package com.bjpowernode.crm.workbench.bean;

import lombok.Data;

import java.util.List;

@Data
public class BarVo {
    private List<String> titles;//横坐标标题
    private List<Long> values;//值
}
