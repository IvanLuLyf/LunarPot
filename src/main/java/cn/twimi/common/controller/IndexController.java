package cn.twimi.common.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@Controller
public class IndexController {
    @RequestMapping({"/", "/index"})
    @ResponseBody
    public Map<String, Object> index() {
        return new HashMap<String, Object>() {{
            put("name", "LunarPot");
            put("version", "20211007");
        }};
    }
}
