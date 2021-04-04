package cn.twimi.live.service;

import cn.twimi.live.common.ApiResponse;
import cn.twimi.live.model.Live;
import cn.twimi.live.model.Message;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public interface LiveService {
    ApiResponse<Live> create(Live live);

    ApiResponse<Message> push(long userId, String liveId, String content);

    ApiResponse<Message> push(long userId, String liveId, String content, MultipartFile file);

    ApiResponse<Message> stop(long userId, String liveId);
}
