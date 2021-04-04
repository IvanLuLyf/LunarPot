package cn.twimi.live.controller;

import cn.twimi.live.annotation.Permission;
import cn.twimi.live.common.ApiResponse;
import cn.twimi.live.model.FileInfo;
import cn.twimi.live.model.Live;
import cn.twimi.live.model.Message;
import cn.twimi.live.model.User;
import cn.twimi.live.service.FileService;
import cn.twimi.live.service.LiveService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Controller
@RequestMapping("/api/live")
public class LiveController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private final SimpMessageSendingOperations operations;
    private final FileService fileService;
    private final LiveService liveService;

    public LiveController(SimpMessageSendingOperations operations, FileService fileService, LiveService liveService) {
        this.operations = operations;
        this.fileService = fileService;
        this.liveService = liveService;
    }

    @Permission("host")
    @PostMapping("/create")
    @ResponseBody
    public ApiResponse<Live> createLive(
            HttpServletRequest request,
            @RequestParam(value = "title") String title
    ) {
        User user = (User) request.getAttribute("curUser");
        return liveService.create(new Live(user.getId(), title));
    }

    @Permission("host")
    @PostMapping("/push/{room}")
    @ResponseBody
    public ApiResponse<Message> push(
            @PathVariable String room,
            @RequestParam(value = "content", required = false, defaultValue = "") String content,
            @RequestParam(value = "file", required = false) MultipartFile file) {
        Message message = new Message();
        message.setId(System.currentTimeMillis());
        message.setContent(content);
        message.setTimestamp(new Date());
        if (file != null) {
            message.setType(2);
            FileInfo fileInfo = fileService.upload(file, "image");
            message.setExtra(fileService.pathToUrl(fileInfo.getPath()));
        } else {
            message.setType(1);
        }
        this.operations.convertAndSend("/topic/channel/" + room, message);
        return ApiResponse.<Message>builder().data(message).msg("ok").status(0).build();
    }
}
