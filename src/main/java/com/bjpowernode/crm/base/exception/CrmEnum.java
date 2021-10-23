package com.bjpowernode.crm.base.exception;

public enum CrmEnum {
    USER_LOGIN_USERNAME_PASSWORD("001-001-001","用户名或密码错误！"),
    USER_LOGIN_CODE("001-001-002","验证码错误"),
    USER_LOGIN_EXPIRED("001-001-003","账号已失效"),
    USER_LOGIN_LOCKED("001-001-004","账号被锁！"),
    USER_LOGIN_ALLOWDIP("001-001-005","不允许登录的ip"),
    USER_UPDATE_SUFFIX("001-002-001","只允许上传后缀名为jpg,png,webp,gif图片"),
    USER_UPDATE_SIZE("001-002-002","上传头像不能超过2M"),
    USER_UPDATE_OLDPWD("001-002-003","原始密码错误！"),
    USER_UPDATE_INFO("001-002-004","更新失败！"),
    ACTIVITY_ADD("002-001","添加市场活动失败"),
    ACTIVITY_UPDATE("002-002","修改市场活动失败"),
    ACTIVITY_DELETE("002-003","删除市场活动失败"),

    ACTIVITY_REMARK_INSERT("002-001-001","添加备注失败！"),
    ACTIVITY_REMARK_UPDATE("002-001-001","添加备注失败！"),
    ACTIVITY_REMARK_DELETE("002-001-001","删除备注失败！"),

    CUSTOMER_INDEX_INSERT("003-001-001","创建失败！"),
    CUSTOMER_INDEX_UPDATE("003-001-002","修改失败！"),
    CUSTOMER_INDEX_DELETE("003-001-003","删除失败！"),
    CUSTOMER_REMARK_INSERT("002-001-001","添加备注失败！"),
    CUSTOMER_REMARK_UPDATE("002-001-001","修改备注失败！"),
    CUSTOMER_REMARK_DELETE("002-001-001","删除备注失败！"),
    TRANSACTION_SAVE_INSERT("004-001-001","创建失败！"),
    TRANSACTION_SAVE_UPDATE("004-001-002","修改失败！");

    private String code;
    private String message;

    CrmEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
