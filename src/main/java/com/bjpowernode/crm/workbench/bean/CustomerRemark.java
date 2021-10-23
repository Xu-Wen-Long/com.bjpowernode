package com.bjpowernode.crm.workbench.bean;

import com.bjpowernode.crm.settings.bean.User;
import lombok.Data;
import tk.mybatis.mapper.annotation.NameStyle;
import tk.mybatis.mapper.code.Style;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.List;

@Data
@Table(name = "tbl_customer_remark")
@NameStyle(Style.normal)
public class CustomerRemark {
    @Id
    private String id;
    private String noteContent;//备注内容
    private String createBy;
    private String createTime;
    private String editBy;
    private String editTime;
    private String editFlag;
    private String customerId;
    private String img;

    private String fullname;
    private String appellation;
    private String job;
    //fullname,appellation,job,createTime,createTime

//    private User user;

    private Contacts contacts;
  /*  private List<Contacts> contacts;*/
};
