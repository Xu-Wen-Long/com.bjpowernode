package com.bjpowernode.crm.base.bean;

import lombok.Data;

@Data
public class Resultvo<T> {
    private boolean isOk;
    private String message;
    private T t;
}
