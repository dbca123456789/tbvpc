package com.gxu.tbvp.controller;

import com.gxu.tbvp.domain.User;
import com.gxu.tbvp.service.UserService;
import com.gxu.tbvp.utils.HttpServletRequestUtil;
import com.gxu.tbvp.utils.PasswordHelper;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;

import static sun.misc.Version.println;

@Controller
@RequestMapping("/MMManager")
public class UserPageManageController {


    @Resource
    private UserService userService;

    @GetMapping("/userPageManage")
    public ModelAndView userPageManage(@RequestParam String info, Model model){
       // String name = user.getUsername();
        User user = userService.selectByUsername(info);
        model.addAttribute("user",user);
      //  model.addAttribute("user",user);

        return new ModelAndView("MMManager/userPageManage","userPageManage",model);
    }

/*
    @GetMapping("/{userName}")
    public ModelAndView userPageManage(@PathVariable("userName") String userName, Model model){
        User user = userService.selectByUsername(userName);
        model.addAttribute("user",user);
        model.addAttribute("title","have a try查看用户");
        return new ModelAndView("MMManager/userPageManage","userPageManage",model);

    }
*/
    // // get/post /user/index?a=10&b=100
    /*
    @GetMapping("/aaaaaa")
    public ModelAndView aaaaaa(@RequestParam String a, @RequestParam String b){

        return null;
    }

    @GetMapping("/aaaaaa")
    public ModelAndView bbbbbb(User user){
        return null;
    }

*/

    @Override
    public String toString() {
        return  " name="  ;
    }
}
