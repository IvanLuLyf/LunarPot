package cn.twimi.live.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

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
}
