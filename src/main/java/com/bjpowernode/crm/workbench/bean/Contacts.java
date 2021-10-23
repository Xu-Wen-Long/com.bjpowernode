package com.bjpowernode.crm.workbench.bean;


import lombok.Data;
import tk.mybatis.mapper.annotation.NameStyle;
import tk.mybatis.mapper.code.Style;

import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Table(name = "tbl_contacts")
@NameStyle(Style.normal)
public class Contacts {

    @Id
    private String id;
    private String owner;
    private String source;
    private String customerId;
    private String fullname;//客户名
    private String appellation;//称呼
    private String email;
    private String mphone;
    private String job;//职位
    private String birth;
    private String createBy;//创建者
    private String createTime;//创建时间
    private String editBy;
    private String editTime;
    private String description;
    private String contactSummary;
    private String nextContactTime;
    private String address;

}
