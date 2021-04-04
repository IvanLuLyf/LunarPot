package cn.twimi.live.service.impl;

import cn.twimi.live.common.ApiResponse;
import cn.twimi.live.dao.LiveDao;
import cn.twimi.live.model.Live;
import cn.twimi.live.service.LiveService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LiveServiceImpl implements LiveService {
    private final LiveDao liveDao;

    public LiveServiceImpl(LiveDao liveDao) {
        this.liveDao = liveDao;
    }

    @Override
    public ApiResponse<Live> create(Live live) {
        List<Live> lives = liveDao.getLivesByUserId(live.getUserId(), Live.STATE_STARTED);
        if (lives.size() > 0) {
            return ApiResponse.<Live>builder().status(1).msg("已有正在进行的直播").build();
        }
        long liveId = liveDao.create(live);
        if (liveId <= 0) {
            return ApiResponse.<Live>builder().status(-6).msg("数据库出错").build();
        }
        live.setId(liveId);
        return ApiResponse.<Live>builder().status(0).msg("ok").data(live).build();
    }
}
