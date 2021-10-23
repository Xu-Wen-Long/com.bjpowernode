package com.bjpowernode.crm.workbench.bean;

import com.bjpowernode.crm.settings.bean.User;
import lombok.Data;
import tk.mybatis.mapper.annotation.NameStyle;
import tk.mybatis.mapper.code.Style;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.List;

@Data
@Table(name = "tbl_customer")
@NameStyle(Style.normal)
public class Customer {
    @Id
    private String id;
    private String owner;//所有者
    private String name;//名称
    private String website;//公司的网站
    private String phone;//座机
    private String createBy;//创造者
    private String createTime;
    private String editBy;//修改者
    private String editTime;
    private String contactSummary;//联系纪要
    private String nextContactTime;//下次联系时间
    private String description;//描述
    private String address;//详细地址

    private List<CustomerRemark> customerRemarks;




};
