package cn.twimi.live.service;

import cn.twimi.live.common.ApiResponse;
import cn.twimi.live.model.Live;
import org.springframework.stereotype.Service;

@Service
public interface LiveService {
    ApiResponse<Live> create(Live live);
}
