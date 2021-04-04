package cn.twimi.live.service;

import cn.twimi.live.common.ApiResponse;
import cn.twimi.live.model.Live;

public interface LiveService {
    ApiResponse<Live> create(Live live);
}
