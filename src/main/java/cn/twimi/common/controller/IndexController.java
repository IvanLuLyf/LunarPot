package cn.twimi.common.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class IndexController {
    @RequestMapping({"/", "/index"})
    public String index() {
        return "index.html";
    }

    @RequestMapping({"/admin"})
    public String admin() {
        return "admin.html";
    }

    @RequestMapping({"/version"})
    @ResponseBody
    public String version() {
        return "OneLive 20210529";
    }
}
