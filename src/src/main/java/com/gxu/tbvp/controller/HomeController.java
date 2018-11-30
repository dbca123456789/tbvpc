package com.gxu.tbvp.controller;



import com.gxu.tbvp.domain.Manager;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import com.gxu.tbvp.utils.HttpServletRequestUtil;
import javax.servlet.http.HttpServletRequest;


/**
 * Created by zqw.
 * twodog.
 */
@Controller
public class HomeController {
    @RequestMapping(value="/login",method= RequestMethod.GET)
    public String login(){
        return "login";
    }

    //管理员登陆
    @RequestMapping(value="/login",method=RequestMethod.POST)
    public String login(HttpServletRequest request, Manager manager, Model model){
        if (StringUtils.isEmpty(manager.getUsername()) || StringUtils.isEmpty(manager.getPassword())) {
            request.setAttribute("msg", "用户名或密码不能为空！");
            return "login";
        }
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token=new UsernamePasswordToken(manager.getUsername(),manager.getPassword());
        try {
            subject.login(token);
            return "redirect:managersPage";
        }catch (LockedAccountException lae) {
            token.clear();
            request.setAttribute("msg", "用户已经被锁定不能登录，请与管理员联系！");
            return "login";
        } catch (AuthenticationException e) {
            token.clear();
            request.setAttribute("msg", "用户或密码不正确！");
            return "login";
        }
    }


/*
    @RequestMapping(value={"/search"})
    public ModelAndView index(@PathVariable int p, String keyword){

        ModelAndView view = new ModelAndView();
        view.setViewName("index");
        //因为用了spring boot缓存,sb是用返回值做缓存,所以service再次返回了pageQuery以缓存查询结果
        List<Topic> findTopicsByPage = topicService.findTopicsByPage(p, Const.TOPIC_PAGE_SIZE);
        view.addObject("topicPage", findTopicsByPage);
        view.addObject("pagename", "首页综合");
        return view;
    }*/

    //管理员登陆
    @RequestMapping("/MMManager/managerLogin")
    public String managerLogin(){
        return "MMManager/managerLogin";
    }



    //大数据平台首页
    @RequestMapping(value={"/managersPage",""})
    public String managersPage(){
        return "manager/managers";
    }

    //更多信息（多个列表）
    @RequestMapping(value={"/list"})
    public String list(){
        return "list";
    }

    //更多信息（单个列表）
    @RequestMapping(value={"/morelist"})
    public String morelist(){
        return "morelist";
    }

    //关键词分析
    @RequestMapping(value="/keywords", method = RequestMethod.GET)
    public String keywords(){
        return "keywords";
    }

    //组合路线推荐
    @RequestMapping("/allroutes")
    public String allRoutes(){
        return "combine_route/allroutes";
    }

    //查找
    @RequestMapping("/searchUser")
    public String searchUser(){
        return "MMManager/searchUser";
    }


    //组合路线推荐内景点分析
    @RequestMapping("/routeScenic")
    public String routeScenic(){
        return "combine_route/routeScenic";
    }


    @RequestMapping(value = "/searchResult", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView searchResult(Model model,HttpServletRequest request){
        // 查询数据库
        String Info = HttpServletRequestUtil.getString(request,"info");
        model.addAttribute("aaaa",Info);
        return new ModelAndView("searchResult");
    }

/*
    //搜索结果分析
    @RequestMapping(value="/searchResult", method = RequestMethod.GET)
    public String searchResult(){
        return "searchResult";
    }
*/
    //用户大数据
    @RequestMapping(value = "/visitors", method = RequestMethod.GET)
    public String visitors(){
        return "user/visitors";
    }



    //产品大数据
    @RequestMapping(value = "/products", method = RequestMethod.GET)
    public String products() {
        return "product/products";
    }

    //代理大数据
    @RequestMapping(value = "/agent", method = RequestMethod.GET)
    public String agent() { return "agent"; }

    @RequestMapping("/rolesPage")
    public String rolesPage(){
        return "role/roles";
    }

    @RequestMapping("/resourcesPage")
    public String resourcesPage(){
        return "resources/resources";
    }

    @RequestMapping("/scenicsManagement")
    public String scenicsManagement(){
        return "admin/scenic";
    }

    @RequestMapping("/403")
    public String forbidden(){
        return "403";
    }
}
