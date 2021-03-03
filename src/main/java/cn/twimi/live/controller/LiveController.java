package cn.twimi.live.controller;

import cn.twimi.live.model.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/api/channel")
public class LiveController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private final SimpMessageSendingOperations operations;

    public LiveController(SimpMessageSendingOperations operations) {
        this.operations = operations;
    }


    @RequestMapping("/push/{room}")
    @ResponseBody
    public Message push(
            @PathVariable String room,
            @RequestParam(value = "msg", required = false, defaultValue = "") String msg) {
        Message message = new Message();
        message.setId(System.currentTimeMillis());
        message.setContent(msg);
        this.operations.convertAndSend("/topic/channel/" + room, message);
        return message;
    }

    @MessageMapping("/channel/{room}")
    public Message subscribeChannel(@DestinationVariable String room, Message m) {
        logger.info(m.toString());
        Message message = new Message();
        message.setContent("Welcome to " + room);
        logger.info("room: " + room);
        return message;
    }
}
