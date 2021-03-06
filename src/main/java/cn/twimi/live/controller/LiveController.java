package cn.twimi.live.controller;

import cn.twimi.live.annotation.Permission;
import cn.twimi.live.common.ApiResponse;
import cn.twimi.live.model.Message;
import cn.twimi.live.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@Controller
@RequestMapping("/api/channel")
public class LiveController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private final SimpMessageSendingOperations operations;

    public LiveController(SimpMessageSendingOperations operations) {
        this.operations = operations;
    }


    @Permission("host")
    @PostMapping("/push/{room}")
    @ResponseBody
    public ApiResponse<Message> push(
            @PathVariable String room,
            @RequestParam(value = "msg", required = false, defaultValue = "") String msg) {
        Message message = new Message();
        message.setId(System.currentTimeMillis());
        message.setContent(msg);
        message.setTimestamp(new Date());
        this.operations.convertAndSend("/topic/channel/" + room, message);
        return ApiResponse.<Message>builder().data(message).msg("ok").status(0).build();
    }
}
