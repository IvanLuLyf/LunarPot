package cn.twimi.live.model;

import cn.twimi.util.IdUtil;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.util.Date;

@Data
public class Live {
    @JsonIgnore
    private long id;
    private String title;
    private String extra;
    private long userId;
    private Date createTime;
    private int state;

    public static final int STATE_STARTED = 0;
    public static final int STATE_ENDED = 1;
    public static final int STATE_HIDDEN = 2;

    public Live() {

    }

    public Live(long userId, String title) {
        this.userId = userId;
        this.title = title;
        this.createTime = new Date();
        this.state = STATE_STARTED;
    }

    @JsonGetter
    public String getLiveId() {
        return IdUtil.encode(this.id);
    }
}
