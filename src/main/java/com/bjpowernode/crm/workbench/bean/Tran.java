package com.bjpowernode.crm.workbench.bean;

import lombok.Data;
import tk.mybatis.mapper.annotation.NameStyle;
import tk.mybatis.mapper.code.Style;

import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Table(name = "tbl_tran")
@NameStyle(Style.normal)
public class Tran {
    @Id
    private String id;
    private String owner;//所有者
    private String money;//金额
    private String name;//名称
    private String expectedDate;//预计成交日期
    private String customerId;//客户id拿到客户名称
    private String stage;       //阶段
    private String possibility;//可能性
    private String type;        //类型
    private String source;      //来源
    private String activityId;//市场活动源
    private String contactsId;     //联系人
    private String createBy;
    private String createTime;
    private String editBy;
    private String editTime;
    private String description;//描述
    private String contactSummary;//联系纪要
    private String nextContactTime;//下次联系时间

};
