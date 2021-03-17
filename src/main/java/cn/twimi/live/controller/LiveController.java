package cn.twimi.live.controller;

import cn.twimi.live.annotation.Permission;
import cn.twimi.live.common.ApiResponse;
import cn.twimi.live.model.FileInfo;
import cn.twimi.live.model.Message;
import cn.twimi.live.service.FileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

@Controller
@RequestMapping("/api/channel")
public class LiveController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private final SimpMessageSendingOperations operations;
    private final FileService fileService;

    public LiveController(SimpMessageSendingOperations operations, FileService fileService) {
        this.operations = operations;
        this.fileService = fileService;
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
