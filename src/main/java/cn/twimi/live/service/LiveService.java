package cn.twimi.live.service;

import cn.twimi.live.common.ApiResponse;
import cn.twimi.live.model.Live;
import cn.twimi.live.model.Message;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public interface LiveService {
    List<Live> listByPage(int page, int limit);

    List<Live> listByUserId(long userId, int page, int limit);

    List<Live> listBySearch(String keyword, int page, int limit);

    List<Message> listHistory(String liveId, int page, int limit);

    ApiResponse<Live> create(Live live);

    ApiResponse<Live> get(String liveId);

    ApiResponse<Message> updateExtra(long userId, String liveId, String extra);

    ApiResponse<Message> push(long userId, String liveId, String content);

    ApiResponse<Message> push(long userId, String liveId, String content, MultipartFile file);

    ApiResponse<Message> stop(long userId, String liveId);
}
