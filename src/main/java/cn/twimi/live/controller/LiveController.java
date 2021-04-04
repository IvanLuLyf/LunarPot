package cn.twimi.live.controller;

import cn.twimi.live.annotation.Permission;
import cn.twimi.live.common.ApiResponse;
import cn.twimi.live.model.Live;
import cn.twimi.live.model.Message;
import cn.twimi.live.model.User;
import cn.twimi.live.service.LiveService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/api/live")
public class LiveController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private final LiveService liveService;

    public LiveController(LiveService liveService) {
        this.liveService = liveService;
    }

    @Permission("host")
    @PostMapping("/create")
    @ResponseBody
    public ApiResponse<Live> createLive(
            HttpServletRequest request,
            @RequestParam("title") String title
    ) {
        User user = (User) request.getAttribute("curUser");
        return liveService.create(new Live(user.getId(), title));
    }

    @Permission("host")
    @PostMapping("/stop")
    @ResponseBody
    public ApiResponse<Message> stopLive(
            HttpServletRequest request,
            @RequestParam("liveId") String liveId
    ) {
        User user = (User) request.getAttribute("curUser");
        return liveService.stop(user.getId(), liveId);
    }

    @Permission("host")
    @PostMapping("/push/{liveId}")
    @ResponseBody
    public ApiResponse<Message> push(
            HttpServletRequest request,
            @PathVariable String liveId,
            @RequestParam(value = "content", required = false, defaultValue = "") String content,
            @RequestParam(value = "file", required = false) MultipartFile file) {
        User user = (User) request.getAttribute("curUser");
        return liveService.push(user.getId(), liveId, content, file);
    }
}
