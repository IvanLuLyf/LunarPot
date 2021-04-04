package cn.twimi.live.service.impl;

import cn.twimi.live.common.ApiResponse;
import cn.twimi.live.dao.LiveDao;
import cn.twimi.live.dao.MessageDao;
import cn.twimi.live.model.FileInfo;
import cn.twimi.live.model.Live;
import cn.twimi.live.model.Message;
import cn.twimi.live.service.FileService;
import cn.twimi.live.service.LiveService;
import cn.twimi.live.util.IdUtil;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class LiveServiceImpl implements LiveService {
    private final LiveDao liveDao;
    private final SimpMessageSendingOperations operations;
    private final FileService fileService;
    private final MessageDao messageDao;

    public LiveServiceImpl(LiveDao liveDao, FileService fileService, SimpMessageSendingOperations operations, MessageDao messageDao) {
        this.liveDao = liveDao;
        this.fileService = fileService;
        this.operations = operations;
        this.messageDao = messageDao;
    }

    @Override
    public ApiResponse<Live> create(Live live) {
        List<Live> lives = liveDao.getLivesByUserIdAndState(live.getUserId(), Live.STATE_STARTED);
        if (lives.size() > 0) {
            return ApiResponse.<Live>builder().status(1).msg("已有正在进行的直播").build();
        }
        int row = liveDao.create(live);
        if (row <= 0) {
            return ApiResponse.<Live>builder().status(-6).msg("数据库出错").build();
        }
        return ApiResponse.<Live>builder().status(0).msg("ok").data(live).build();
    }

    @Override
    public ApiResponse<Message> push(String liveId, String content) {
        return this.push(liveId, content, null);
    }

    @Override
    public ApiResponse<Message> push(String liveId, String content, MultipartFile file) {
        Message message = new Message(IdUtil.decode(liveId), content);
        if (file != null) {
            message.setType(2);
            FileInfo fileInfo = fileService.upload(file, "image");
            message.setExtra(fileService.pathToUrl(fileInfo.getPath()));
        }
        int row = messageDao.create(message);
        if (row <= 0) {
            return ApiResponse.<Message>builder().status(-6).msg("数据库出错").build();
        }
        this.operations.convertAndSend("/topic/channel/" + liveId, message);
        return ApiResponse.<Message>builder().data(message).msg("ok").status(0).build();
    }

    @Override
    public ApiResponse<Message> close(String liveId) {
        return null;
    }
}
