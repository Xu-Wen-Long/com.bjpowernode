package com.bjpowernode.crm.workbench.service;

import com.bjpowernode.crm.base.bean.Resultvo;
import com.bjpowernode.crm.workbench.bean.Activity;
import com.bjpowernode.crm.workbench.bean.Clue;
import com.bjpowernode.crm.workbench.bean.ClueActivityRelation;

import java.util.List;

public interface ClueService {
    List<Clue> list(int page, int pageSize, Clue clue);

    Clue toDetail(String id);

    List<Activity> queryActivities(String id, String name);

    Resultvo bind(String ids, String id);

    void unbind(ClueActivityRelation clueActivityRelation);
}
