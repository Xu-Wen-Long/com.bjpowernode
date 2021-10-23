package com.bjpowernode.crm.base.exception;

import com.bjpowernode.crm.base.exception.CrmEnum;

public class CrmException extends RuntimeException {
    private CrmEnum crmEnum;

    public CrmException(CrmEnum crmEnum) {
        super(crmEnum.getMessage());
        this.crmEnum = crmEnum;
    }
}
