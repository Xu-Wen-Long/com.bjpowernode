package com.bjpowernode.crm.settings.controller;


import com.bjpowernode.crm.base.bean.Resultvo;
import com.bjpowernode.crm.base.exception.CrmEnum;
import com.bjpowernode.crm.base.exception.CrmException;
import com.bjpowernode.crm.base.util.CommonUtil;
import com.bjpowernode.crm.base.util.MD5Util;
import com.bjpowernode.crm.base.util.UUIDUtil;
import com.bjpowernode.crm.settings.bean.User;
import com.bjpowernode.crm.settings.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.crypto.dsig.Transform;
import java.awt.*;
import java.awt.font.MultipleMaster;
import java.io.File;
import java.io.IOException;

@Controller
public class USerController {
    @Autowired
    IUserService userService;

    @RequestMapping("/settings/user/login")
    @ResponseBody
    public Resultvo login(User user, String code, HttpSession session, HttpServletRequest request){
        Resultvo resultvo = new Resultvo();
            try {
            String correctCode =  (String) session.getAttribute("correctCode");
            //    获取ip地址
            String remoteAddr = request.getRemoteAddr();
            user.setAllowIps(remoteAddr);
            user = userService.login(user,code,correctCode);
            resultvo.setOk(true);
            session.setAttribute("user", user);
        } catch (CrmException e) {

            resultvo.setMessage(e.getMessage());

        }
        return resultvo;
    }
    @RequestMapping("/settings/user/toIndex")
    public String toIndex(){
        return "workbench/index";
    }

    @RequestMapping("/settings/user/logOut")
    public String logOut(HttpSession session){
        session.removeAttribute("user");
        return "redirect:/index.jsp";
    }
    @RequestMapping("/settings/user/fileUpload")
    @ResponseBody
    public Resultvo fileUpload(HttpServletRequest request,HttpSession session,MultipartFile img){

        Resultvo resultvo = new Resultvo();

        try {
            //创建一个接受包
            String realPath = session.getServletContext().getRealPath("/upload");
            File file = new File(realPath);
            if (!file.exists()){
                file.mkdir();
            }
            //获取照片名称
            String filename = img.getOriginalFilename();
            //防止名称一样
            filename = UUIDUtil.getUUID() + filename;
            //传入文件名进行判断是否是照片
            suffix(filename);
            //传入照片字节的长度进行判断文件大小
            mxsixe(img.getSize());

            //照片写入那个目录下面
            img.transferTo(new File(realPath + File.separator + filename));

            String contextPath = request.getContextPath();
            String photoPath = contextPath + File.separator  +  "upload" +File.separator +filename;




            resultvo.setMessage("上传图片成功！");
            resultvo.setT(photoPath);
        } catch (CrmException e) {
            resultvo.setMessage(e.getMessage());

        } catch (IOException e){
            e.printStackTrace();
        }
        return resultvo;
    }

    private void mxsixe(long size) {
        long maxSize = 2 * 1024 * 1024;
        if (size > maxSize){
            throw new CrmException(CrmEnum.USER_UPDATE_SIZE);
        }

    }

    private void suffix(String filename) {
        String suffixs = "jpg,png,webp,gif";
        //截取，取第二段
        String suffix = filename.substring(filename.lastIndexOf("." ) + 1);
        if(!suffixs.contains(suffix)){
            throw new CrmException(CrmEnum.USER_UPDATE_SUFFIX);
        }
    }
    @RequestMapping("/settrings/user/verifyoldPwd")
    @ResponseBody
    public Resultvo verifyoldPwd(HttpSession session , String oldPwd){
        Resultvo resultvo = new Resultvo();
        try {
            User currentUser = CommonUtil.getCurrentUser(session);
            String loginPwd = currentUser.getLoginPwd();
            userService.verifyoldPwd(loginPwd,oldPwd);
            resultvo.setOk(true);
        } catch (CrmException e) {
            resultvo.setMessage(e.getMessage());
        }
        return resultvo;
    }


    @RequestMapping("/settings/user/updateUser")
    @ResponseBody
    public Resultvo updateUser(HttpSession session,User user){
        Resultvo resultvo = new Resultvo();
        try {
            User user1 = CommonUtil.getCurrentUser(session);

            user.setId(user1.getId());

            userService.updateUser(user);
            resultvo.setOk(true);
            resultvo.setMessage("更新成功！");
        } catch (CrmException e) {
            resultvo.setMessage(e.getMessage());
        }
        return resultvo;
    }

}
