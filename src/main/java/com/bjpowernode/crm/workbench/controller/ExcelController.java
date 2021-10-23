package com.bjpowernode.crm.workbench.controller;

import cn.hutool.core.io.IoUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.bjpowernode.crm.workbench.service.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
public class ExcelController {
    @Autowired
    ActivityService activityService;

    @RequestMapping("/workbench/activity/exportExcel")
    public void exportExcel(HttpServletResponse response) throws IOException {
       ExcelWriter writer = activityService.exportExcel();

        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
        //告诉客户端需要下载文件
        response.setHeader("Content-Disposition","attachment;filename=activity.xlsx");
        ServletOutputStream outputStream = response.getOutputStream();
        writer.flush(outputStream, true);
        writer.close();
        IoUtil.close(outputStream);

    }
}
