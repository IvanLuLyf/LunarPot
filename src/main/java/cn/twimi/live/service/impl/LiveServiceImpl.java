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
    public List<Live> listByUserId(long userId, int page, int limit) {
        return liveDao.getLivesByUserIdWithPage(userId, (page - 1) * limit, limit);
    }

    @Override
    public List<Message> listHistory(String liveId, int page, int limit) {
        long realId = IdUtil.decode(liveId);
        return messageDao.getMessagesByLiveIdWithPage(realId, (page - 1) * limit, limit);
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
    public ApiResponse<Live> get(String liveId) {
        long realId = IdUtil.decode(liveId);
        Live live = liveDao.getLiveById(realId);
        return ApiResponse.<Live>builder().status(0).msg("ok").data(live).build();
    }

    @Override
    public ApiResponse<Message> updateExtra(long userId, String liveId, String extra) {
        long realId = IdUtil.decode(liveId);
        boolean checkHost = realId > 0 && liveDao.checkLiveAvailable(userId, realId, Live.STATE_STARTED);
        if (!checkHost) {
            return ApiResponse.<Message>builder().status(2).msg("直播不可用").build();
        }
        Message message = new Message();
        message.setLiveId(realId);
        message.setContent("update");
        message.setType(Message.TYPE_SYSTEM);
        message.setExtra(extra);
        int row = liveDao.updateExtra(realId, extra);
        if (row <= 0) {
            return ApiResponse.<Message>builder().status(-6).msg("数据库出错").build();
        }
        this.operations.convertAndSend("/topic/channel/" + liveId, message);
        return ApiResponse.<Message>builder().data(message).msg("ok").status(0).build();
    }

    @Override
    public ApiResponse<Message> push(long userId, String liveId, String content) {
        return this.push(userId, liveId, content, null);
    }

    @Override
    public ApiResponse<Message> push(long userId, String liveId, String content, MultipartFile file) {
        long realId = IdUtil.decode(liveId);
        boolean checkHost = realId > 0 && liveDao.checkLiveAvailable(userId, realId, Live.STATE_STARTED);
        if (!checkHost) {
            return ApiResponse.<Message>builder().status(2).msg("直播不可用").build();
        }
        Message message = new Message(realId, content);
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
    public ApiResponse<Message> stop(long userId, String liveId) {
        long realId = IdUtil.decode(liveId);
        boolean checkHost = realId > 0 && liveDao.checkLiveAvailable(userId, realId, Live.STATE_STARTED);
        if (!checkHost) {
            return ApiResponse.<Message>builder().status(2).msg("直播不可用").build();
        }
        int row = liveDao.updateState(realId, Live.STATE_ENDED);
        if (row <= 0) {
            return ApiResponse.<Message>builder().status(-6).msg("数据库出错").build();
        }
        Message message = new Message();
        message.setType(Message.TYPE_SYSTEM);
        message.setContent("stop");
        this.operations.convertAndSend("/topic/channel/" + liveId, message);
        return ApiResponse.<Message>builder().data(message).msg("ok").status(0).build();
    }
}
