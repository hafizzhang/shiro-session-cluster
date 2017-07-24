package com.hafiz.www.controller;

import com.hafiz.www.shiro.SessionUtils;
import com.hafiz.www.vo.JsonResult;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Desc:用户登陆登出控制器
 * Created by hafiz.zhang on 2017/7/21.
 */
@Controller
public class LoginController {


    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String root() {
        return "redirect:/index.html";
    }

    @RequestMapping(value = "/index.html", method = RequestMethod.GET)
    public String index() {
        return "index";
    }

    @RequestMapping(value = "/login.html", method = RequestMethod.GET)
    public String loginPage() {
        return "login";
    }

    @RequestMapping(value = "/login.json", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult login(String userName, String password) {
        if (userName == null) {
            return JsonResult.newInstanceFail("用户名不能为空");
        }
        if (password == null) {
            return JsonResult.newInstanceFail("密码不能为空");
        }
        if (!SessionUtils.isLoggedIn()) {
            JsonResult result = SessionUtils.login(userName, password);
            return result;
        }
        return JsonResult.newInstanceSuccess();
    }

    @RequestMapping(value = "/logout.json", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult logout() {
        SessionUtils.logout();
        return JsonResult.newInstanceSuccess();
    }

    @RequestMapping(value = "/unauthorized.html", method = RequestMethod.GET)
    public String unauthorized() {
        return "unauthorized";
    }
}
